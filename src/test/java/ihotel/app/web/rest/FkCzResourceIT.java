package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.FkCz;
import ihotel.app.repository.FkCzRepository;
import ihotel.app.repository.search.FkCzSearchRepository;
import ihotel.app.service.criteria.FkCzCriteria;
import ihotel.app.service.dto.FkCzDTO;
import ihotel.app.service.mapper.FkCzMapper;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FkCzResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FkCzResourceIT {

    private static final Instant DEFAULT_HOTELTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOTELTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_WXF = 1L;
    private static final Long UPDATED_WXF = 2L;
    private static final Long SMALLER_WXF = 1L - 1L;

    private static final Long DEFAULT_KSF = 1L;
    private static final Long UPDATED_KSF = 2L;
    private static final Long SMALLER_KSF = 1L - 1L;

    private static final Long DEFAULT_KF = 1L;
    private static final Long UPDATED_KF = 2L;
    private static final Long SMALLER_KF = 1L - 1L;

    private static final Long DEFAULT_ZFS = 1L;
    private static final Long UPDATED_ZFS = 2L;
    private static final Long SMALLER_ZFS = 1L - 1L;

    private static final Long DEFAULT_GROUPYD = 1L;
    private static final Long UPDATED_GROUPYD = 2L;
    private static final Long SMALLER_GROUPYD = 1L - 1L;

    private static final Long DEFAULT_SKYD = 1L;
    private static final Long UPDATED_SKYD = 2L;
    private static final Long SMALLER_SKYD = 1L - 1L;

    private static final Long DEFAULT_YDWD = 1L;
    private static final Long UPDATED_YDWD = 2L;
    private static final Long SMALLER_YDWD = 1L - 1L;

    private static final Long DEFAULT_QXYD = 1L;
    private static final Long UPDATED_QXYD = 2L;
    private static final Long SMALLER_QXYD = 1L - 1L;

    private static final Long DEFAULT_ISNEW = 1L;
    private static final Long UPDATED_ISNEW = 2L;
    private static final Long SMALLER_ISNEW = 1L - 1L;

    private static final String DEFAULT_HOTELDM = "AAAAAAAAAA";
    private static final String UPDATED_HOTELDM = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_HYS = new BigDecimal(1);
    private static final BigDecimal UPDATED_HYS = new BigDecimal(2);
    private static final BigDecimal SMALLER_HYS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_KHYS = new BigDecimal(1);
    private static final BigDecimal UPDATED_KHYS = new BigDecimal(2);
    private static final BigDecimal SMALLER_KHYS = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/fk-czs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/fk-czs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FkCzRepository fkCzRepository;

    @Autowired
    private FkCzMapper fkCzMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.FkCzSearchRepositoryMockConfiguration
     */
    @Autowired
    private FkCzSearchRepository mockFkCzSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFkCzMockMvc;

    private FkCz fkCz;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FkCz createEntity(EntityManager em) {
        FkCz fkCz = new FkCz()
            .hoteltime(DEFAULT_HOTELTIME)
            .wxf(DEFAULT_WXF)
            .ksf(DEFAULT_KSF)
            .kf(DEFAULT_KF)
            .zfs(DEFAULT_ZFS)
            .groupyd(DEFAULT_GROUPYD)
            .skyd(DEFAULT_SKYD)
            .ydwd(DEFAULT_YDWD)
            .qxyd(DEFAULT_QXYD)
            .isnew(DEFAULT_ISNEW)
            .hoteldm(DEFAULT_HOTELDM)
            .hys(DEFAULT_HYS)
            .khys(DEFAULT_KHYS);
        return fkCz;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FkCz createUpdatedEntity(EntityManager em) {
        FkCz fkCz = new FkCz()
            .hoteltime(UPDATED_HOTELTIME)
            .wxf(UPDATED_WXF)
            .ksf(UPDATED_KSF)
            .kf(UPDATED_KF)
            .zfs(UPDATED_ZFS)
            .groupyd(UPDATED_GROUPYD)
            .skyd(UPDATED_SKYD)
            .ydwd(UPDATED_YDWD)
            .qxyd(UPDATED_QXYD)
            .isnew(UPDATED_ISNEW)
            .hoteldm(UPDATED_HOTELDM)
            .hys(UPDATED_HYS)
            .khys(UPDATED_KHYS);
        return fkCz;
    }

    @BeforeEach
    public void initTest() {
        fkCz = createEntity(em);
    }

    @Test
    @Transactional
    void createFkCz() throws Exception {
        int databaseSizeBeforeCreate = fkCzRepository.findAll().size();
        // Create the FkCz
        FkCzDTO fkCzDTO = fkCzMapper.toDto(fkCz);
        restFkCzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fkCzDTO)))
            .andExpect(status().isCreated());

        // Validate the FkCz in the database
        List<FkCz> fkCzList = fkCzRepository.findAll();
        assertThat(fkCzList).hasSize(databaseSizeBeforeCreate + 1);
        FkCz testFkCz = fkCzList.get(fkCzList.size() - 1);
        assertThat(testFkCz.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testFkCz.getWxf()).isEqualTo(DEFAULT_WXF);
        assertThat(testFkCz.getKsf()).isEqualTo(DEFAULT_KSF);
        assertThat(testFkCz.getKf()).isEqualTo(DEFAULT_KF);
        assertThat(testFkCz.getZfs()).isEqualTo(DEFAULT_ZFS);
        assertThat(testFkCz.getGroupyd()).isEqualTo(DEFAULT_GROUPYD);
        assertThat(testFkCz.getSkyd()).isEqualTo(DEFAULT_SKYD);
        assertThat(testFkCz.getYdwd()).isEqualTo(DEFAULT_YDWD);
        assertThat(testFkCz.getQxyd()).isEqualTo(DEFAULT_QXYD);
        assertThat(testFkCz.getIsnew()).isEqualTo(DEFAULT_ISNEW);
        assertThat(testFkCz.getHoteldm()).isEqualTo(DEFAULT_HOTELDM);
        assertThat(testFkCz.getHys()).isEqualByComparingTo(DEFAULT_HYS);
        assertThat(testFkCz.getKhys()).isEqualByComparingTo(DEFAULT_KHYS);

        // Validate the FkCz in Elasticsearch
        verify(mockFkCzSearchRepository, times(1)).save(testFkCz);
    }

    @Test
    @Transactional
    void createFkCzWithExistingId() throws Exception {
        // Create the FkCz with an existing ID
        fkCz.setId(1L);
        FkCzDTO fkCzDTO = fkCzMapper.toDto(fkCz);

        int databaseSizeBeforeCreate = fkCzRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFkCzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fkCzDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FkCz in the database
        List<FkCz> fkCzList = fkCzRepository.findAll();
        assertThat(fkCzList).hasSize(databaseSizeBeforeCreate);

        // Validate the FkCz in Elasticsearch
        verify(mockFkCzSearchRepository, times(0)).save(fkCz);
    }

    @Test
    @Transactional
    void checkHoteltimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fkCzRepository.findAll().size();
        // set the field null
        fkCz.setHoteltime(null);

        // Create the FkCz, which fails.
        FkCzDTO fkCzDTO = fkCzMapper.toDto(fkCz);

        restFkCzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fkCzDTO)))
            .andExpect(status().isBadRequest());

        List<FkCz> fkCzList = fkCzRepository.findAll();
        assertThat(fkCzList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFkCzs() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList
        restFkCzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fkCz.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].wxf").value(hasItem(DEFAULT_WXF.intValue())))
            .andExpect(jsonPath("$.[*].ksf").value(hasItem(DEFAULT_KSF.intValue())))
            .andExpect(jsonPath("$.[*].kf").value(hasItem(DEFAULT_KF.intValue())))
            .andExpect(jsonPath("$.[*].zfs").value(hasItem(DEFAULT_ZFS.intValue())))
            .andExpect(jsonPath("$.[*].groupyd").value(hasItem(DEFAULT_GROUPYD.intValue())))
            .andExpect(jsonPath("$.[*].skyd").value(hasItem(DEFAULT_SKYD.intValue())))
            .andExpect(jsonPath("$.[*].ydwd").value(hasItem(DEFAULT_YDWD.intValue())))
            .andExpect(jsonPath("$.[*].qxyd").value(hasItem(DEFAULT_QXYD.intValue())))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].hys").value(hasItem(sameNumber(DEFAULT_HYS))))
            .andExpect(jsonPath("$.[*].khys").value(hasItem(sameNumber(DEFAULT_KHYS))));
    }

    @Test
    @Transactional
    void getFkCz() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get the fkCz
        restFkCzMockMvc
            .perform(get(ENTITY_API_URL_ID, fkCz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fkCz.getId().intValue()))
            .andExpect(jsonPath("$.hoteltime").value(DEFAULT_HOTELTIME.toString()))
            .andExpect(jsonPath("$.wxf").value(DEFAULT_WXF.intValue()))
            .andExpect(jsonPath("$.ksf").value(DEFAULT_KSF.intValue()))
            .andExpect(jsonPath("$.kf").value(DEFAULT_KF.intValue()))
            .andExpect(jsonPath("$.zfs").value(DEFAULT_ZFS.intValue()))
            .andExpect(jsonPath("$.groupyd").value(DEFAULT_GROUPYD.intValue()))
            .andExpect(jsonPath("$.skyd").value(DEFAULT_SKYD.intValue()))
            .andExpect(jsonPath("$.ydwd").value(DEFAULT_YDWD.intValue()))
            .andExpect(jsonPath("$.qxyd").value(DEFAULT_QXYD.intValue()))
            .andExpect(jsonPath("$.isnew").value(DEFAULT_ISNEW.intValue()))
            .andExpect(jsonPath("$.hoteldm").value(DEFAULT_HOTELDM))
            .andExpect(jsonPath("$.hys").value(sameNumber(DEFAULT_HYS)))
            .andExpect(jsonPath("$.khys").value(sameNumber(DEFAULT_KHYS)));
    }

    @Test
    @Transactional
    void getFkCzsByIdFiltering() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        Long id = fkCz.getId();

        defaultFkCzShouldBeFound("id.equals=" + id);
        defaultFkCzShouldNotBeFound("id.notEquals=" + id);

        defaultFkCzShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFkCzShouldNotBeFound("id.greaterThan=" + id);

        defaultFkCzShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFkCzShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFkCzsByHoteltimeIsEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hoteltime equals to DEFAULT_HOTELTIME
        defaultFkCzShouldBeFound("hoteltime.equals=" + DEFAULT_HOTELTIME);

        // Get all the fkCzList where hoteltime equals to UPDATED_HOTELTIME
        defaultFkCzShouldNotBeFound("hoteltime.equals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllFkCzsByHoteltimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hoteltime not equals to DEFAULT_HOTELTIME
        defaultFkCzShouldNotBeFound("hoteltime.notEquals=" + DEFAULT_HOTELTIME);

        // Get all the fkCzList where hoteltime not equals to UPDATED_HOTELTIME
        defaultFkCzShouldBeFound("hoteltime.notEquals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllFkCzsByHoteltimeIsInShouldWork() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hoteltime in DEFAULT_HOTELTIME or UPDATED_HOTELTIME
        defaultFkCzShouldBeFound("hoteltime.in=" + DEFAULT_HOTELTIME + "," + UPDATED_HOTELTIME);

        // Get all the fkCzList where hoteltime equals to UPDATED_HOTELTIME
        defaultFkCzShouldNotBeFound("hoteltime.in=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllFkCzsByHoteltimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hoteltime is not null
        defaultFkCzShouldBeFound("hoteltime.specified=true");

        // Get all the fkCzList where hoteltime is null
        defaultFkCzShouldNotBeFound("hoteltime.specified=false");
    }

    @Test
    @Transactional
    void getAllFkCzsByWxfIsEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where wxf equals to DEFAULT_WXF
        defaultFkCzShouldBeFound("wxf.equals=" + DEFAULT_WXF);

        // Get all the fkCzList where wxf equals to UPDATED_WXF
        defaultFkCzShouldNotBeFound("wxf.equals=" + UPDATED_WXF);
    }

    @Test
    @Transactional
    void getAllFkCzsByWxfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where wxf not equals to DEFAULT_WXF
        defaultFkCzShouldNotBeFound("wxf.notEquals=" + DEFAULT_WXF);

        // Get all the fkCzList where wxf not equals to UPDATED_WXF
        defaultFkCzShouldBeFound("wxf.notEquals=" + UPDATED_WXF);
    }

    @Test
    @Transactional
    void getAllFkCzsByWxfIsInShouldWork() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where wxf in DEFAULT_WXF or UPDATED_WXF
        defaultFkCzShouldBeFound("wxf.in=" + DEFAULT_WXF + "," + UPDATED_WXF);

        // Get all the fkCzList where wxf equals to UPDATED_WXF
        defaultFkCzShouldNotBeFound("wxf.in=" + UPDATED_WXF);
    }

    @Test
    @Transactional
    void getAllFkCzsByWxfIsNullOrNotNull() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where wxf is not null
        defaultFkCzShouldBeFound("wxf.specified=true");

        // Get all the fkCzList where wxf is null
        defaultFkCzShouldNotBeFound("wxf.specified=false");
    }

    @Test
    @Transactional
    void getAllFkCzsByWxfIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where wxf is greater than or equal to DEFAULT_WXF
        defaultFkCzShouldBeFound("wxf.greaterThanOrEqual=" + DEFAULT_WXF);

        // Get all the fkCzList where wxf is greater than or equal to UPDATED_WXF
        defaultFkCzShouldNotBeFound("wxf.greaterThanOrEqual=" + UPDATED_WXF);
    }

    @Test
    @Transactional
    void getAllFkCzsByWxfIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where wxf is less than or equal to DEFAULT_WXF
        defaultFkCzShouldBeFound("wxf.lessThanOrEqual=" + DEFAULT_WXF);

        // Get all the fkCzList where wxf is less than or equal to SMALLER_WXF
        defaultFkCzShouldNotBeFound("wxf.lessThanOrEqual=" + SMALLER_WXF);
    }

    @Test
    @Transactional
    void getAllFkCzsByWxfIsLessThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where wxf is less than DEFAULT_WXF
        defaultFkCzShouldNotBeFound("wxf.lessThan=" + DEFAULT_WXF);

        // Get all the fkCzList where wxf is less than UPDATED_WXF
        defaultFkCzShouldBeFound("wxf.lessThan=" + UPDATED_WXF);
    }

    @Test
    @Transactional
    void getAllFkCzsByWxfIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where wxf is greater than DEFAULT_WXF
        defaultFkCzShouldNotBeFound("wxf.greaterThan=" + DEFAULT_WXF);

        // Get all the fkCzList where wxf is greater than SMALLER_WXF
        defaultFkCzShouldBeFound("wxf.greaterThan=" + SMALLER_WXF);
    }

    @Test
    @Transactional
    void getAllFkCzsByKsfIsEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ksf equals to DEFAULT_KSF
        defaultFkCzShouldBeFound("ksf.equals=" + DEFAULT_KSF);

        // Get all the fkCzList where ksf equals to UPDATED_KSF
        defaultFkCzShouldNotBeFound("ksf.equals=" + UPDATED_KSF);
    }

    @Test
    @Transactional
    void getAllFkCzsByKsfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ksf not equals to DEFAULT_KSF
        defaultFkCzShouldNotBeFound("ksf.notEquals=" + DEFAULT_KSF);

        // Get all the fkCzList where ksf not equals to UPDATED_KSF
        defaultFkCzShouldBeFound("ksf.notEquals=" + UPDATED_KSF);
    }

    @Test
    @Transactional
    void getAllFkCzsByKsfIsInShouldWork() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ksf in DEFAULT_KSF or UPDATED_KSF
        defaultFkCzShouldBeFound("ksf.in=" + DEFAULT_KSF + "," + UPDATED_KSF);

        // Get all the fkCzList where ksf equals to UPDATED_KSF
        defaultFkCzShouldNotBeFound("ksf.in=" + UPDATED_KSF);
    }

    @Test
    @Transactional
    void getAllFkCzsByKsfIsNullOrNotNull() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ksf is not null
        defaultFkCzShouldBeFound("ksf.specified=true");

        // Get all the fkCzList where ksf is null
        defaultFkCzShouldNotBeFound("ksf.specified=false");
    }

    @Test
    @Transactional
    void getAllFkCzsByKsfIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ksf is greater than or equal to DEFAULT_KSF
        defaultFkCzShouldBeFound("ksf.greaterThanOrEqual=" + DEFAULT_KSF);

        // Get all the fkCzList where ksf is greater than or equal to UPDATED_KSF
        defaultFkCzShouldNotBeFound("ksf.greaterThanOrEqual=" + UPDATED_KSF);
    }

    @Test
    @Transactional
    void getAllFkCzsByKsfIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ksf is less than or equal to DEFAULT_KSF
        defaultFkCzShouldBeFound("ksf.lessThanOrEqual=" + DEFAULT_KSF);

        // Get all the fkCzList where ksf is less than or equal to SMALLER_KSF
        defaultFkCzShouldNotBeFound("ksf.lessThanOrEqual=" + SMALLER_KSF);
    }

    @Test
    @Transactional
    void getAllFkCzsByKsfIsLessThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ksf is less than DEFAULT_KSF
        defaultFkCzShouldNotBeFound("ksf.lessThan=" + DEFAULT_KSF);

        // Get all the fkCzList where ksf is less than UPDATED_KSF
        defaultFkCzShouldBeFound("ksf.lessThan=" + UPDATED_KSF);
    }

    @Test
    @Transactional
    void getAllFkCzsByKsfIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ksf is greater than DEFAULT_KSF
        defaultFkCzShouldNotBeFound("ksf.greaterThan=" + DEFAULT_KSF);

        // Get all the fkCzList where ksf is greater than SMALLER_KSF
        defaultFkCzShouldBeFound("ksf.greaterThan=" + SMALLER_KSF);
    }

    @Test
    @Transactional
    void getAllFkCzsByKfIsEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where kf equals to DEFAULT_KF
        defaultFkCzShouldBeFound("kf.equals=" + DEFAULT_KF);

        // Get all the fkCzList where kf equals to UPDATED_KF
        defaultFkCzShouldNotBeFound("kf.equals=" + UPDATED_KF);
    }

    @Test
    @Transactional
    void getAllFkCzsByKfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where kf not equals to DEFAULT_KF
        defaultFkCzShouldNotBeFound("kf.notEquals=" + DEFAULT_KF);

        // Get all the fkCzList where kf not equals to UPDATED_KF
        defaultFkCzShouldBeFound("kf.notEquals=" + UPDATED_KF);
    }

    @Test
    @Transactional
    void getAllFkCzsByKfIsInShouldWork() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where kf in DEFAULT_KF or UPDATED_KF
        defaultFkCzShouldBeFound("kf.in=" + DEFAULT_KF + "," + UPDATED_KF);

        // Get all the fkCzList where kf equals to UPDATED_KF
        defaultFkCzShouldNotBeFound("kf.in=" + UPDATED_KF);
    }

    @Test
    @Transactional
    void getAllFkCzsByKfIsNullOrNotNull() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where kf is not null
        defaultFkCzShouldBeFound("kf.specified=true");

        // Get all the fkCzList where kf is null
        defaultFkCzShouldNotBeFound("kf.specified=false");
    }

    @Test
    @Transactional
    void getAllFkCzsByKfIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where kf is greater than or equal to DEFAULT_KF
        defaultFkCzShouldBeFound("kf.greaterThanOrEqual=" + DEFAULT_KF);

        // Get all the fkCzList where kf is greater than or equal to UPDATED_KF
        defaultFkCzShouldNotBeFound("kf.greaterThanOrEqual=" + UPDATED_KF);
    }

    @Test
    @Transactional
    void getAllFkCzsByKfIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where kf is less than or equal to DEFAULT_KF
        defaultFkCzShouldBeFound("kf.lessThanOrEqual=" + DEFAULT_KF);

        // Get all the fkCzList where kf is less than or equal to SMALLER_KF
        defaultFkCzShouldNotBeFound("kf.lessThanOrEqual=" + SMALLER_KF);
    }

    @Test
    @Transactional
    void getAllFkCzsByKfIsLessThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where kf is less than DEFAULT_KF
        defaultFkCzShouldNotBeFound("kf.lessThan=" + DEFAULT_KF);

        // Get all the fkCzList where kf is less than UPDATED_KF
        defaultFkCzShouldBeFound("kf.lessThan=" + UPDATED_KF);
    }

    @Test
    @Transactional
    void getAllFkCzsByKfIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where kf is greater than DEFAULT_KF
        defaultFkCzShouldNotBeFound("kf.greaterThan=" + DEFAULT_KF);

        // Get all the fkCzList where kf is greater than SMALLER_KF
        defaultFkCzShouldBeFound("kf.greaterThan=" + SMALLER_KF);
    }

    @Test
    @Transactional
    void getAllFkCzsByZfsIsEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where zfs equals to DEFAULT_ZFS
        defaultFkCzShouldBeFound("zfs.equals=" + DEFAULT_ZFS);

        // Get all the fkCzList where zfs equals to UPDATED_ZFS
        defaultFkCzShouldNotBeFound("zfs.equals=" + UPDATED_ZFS);
    }

    @Test
    @Transactional
    void getAllFkCzsByZfsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where zfs not equals to DEFAULT_ZFS
        defaultFkCzShouldNotBeFound("zfs.notEquals=" + DEFAULT_ZFS);

        // Get all the fkCzList where zfs not equals to UPDATED_ZFS
        defaultFkCzShouldBeFound("zfs.notEquals=" + UPDATED_ZFS);
    }

    @Test
    @Transactional
    void getAllFkCzsByZfsIsInShouldWork() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where zfs in DEFAULT_ZFS or UPDATED_ZFS
        defaultFkCzShouldBeFound("zfs.in=" + DEFAULT_ZFS + "," + UPDATED_ZFS);

        // Get all the fkCzList where zfs equals to UPDATED_ZFS
        defaultFkCzShouldNotBeFound("zfs.in=" + UPDATED_ZFS);
    }

    @Test
    @Transactional
    void getAllFkCzsByZfsIsNullOrNotNull() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where zfs is not null
        defaultFkCzShouldBeFound("zfs.specified=true");

        // Get all the fkCzList where zfs is null
        defaultFkCzShouldNotBeFound("zfs.specified=false");
    }

    @Test
    @Transactional
    void getAllFkCzsByZfsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where zfs is greater than or equal to DEFAULT_ZFS
        defaultFkCzShouldBeFound("zfs.greaterThanOrEqual=" + DEFAULT_ZFS);

        // Get all the fkCzList where zfs is greater than or equal to UPDATED_ZFS
        defaultFkCzShouldNotBeFound("zfs.greaterThanOrEqual=" + UPDATED_ZFS);
    }

    @Test
    @Transactional
    void getAllFkCzsByZfsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where zfs is less than or equal to DEFAULT_ZFS
        defaultFkCzShouldBeFound("zfs.lessThanOrEqual=" + DEFAULT_ZFS);

        // Get all the fkCzList where zfs is less than or equal to SMALLER_ZFS
        defaultFkCzShouldNotBeFound("zfs.lessThanOrEqual=" + SMALLER_ZFS);
    }

    @Test
    @Transactional
    void getAllFkCzsByZfsIsLessThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where zfs is less than DEFAULT_ZFS
        defaultFkCzShouldNotBeFound("zfs.lessThan=" + DEFAULT_ZFS);

        // Get all the fkCzList where zfs is less than UPDATED_ZFS
        defaultFkCzShouldBeFound("zfs.lessThan=" + UPDATED_ZFS);
    }

    @Test
    @Transactional
    void getAllFkCzsByZfsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where zfs is greater than DEFAULT_ZFS
        defaultFkCzShouldNotBeFound("zfs.greaterThan=" + DEFAULT_ZFS);

        // Get all the fkCzList where zfs is greater than SMALLER_ZFS
        defaultFkCzShouldBeFound("zfs.greaterThan=" + SMALLER_ZFS);
    }

    @Test
    @Transactional
    void getAllFkCzsByGroupydIsEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where groupyd equals to DEFAULT_GROUPYD
        defaultFkCzShouldBeFound("groupyd.equals=" + DEFAULT_GROUPYD);

        // Get all the fkCzList where groupyd equals to UPDATED_GROUPYD
        defaultFkCzShouldNotBeFound("groupyd.equals=" + UPDATED_GROUPYD);
    }

    @Test
    @Transactional
    void getAllFkCzsByGroupydIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where groupyd not equals to DEFAULT_GROUPYD
        defaultFkCzShouldNotBeFound("groupyd.notEquals=" + DEFAULT_GROUPYD);

        // Get all the fkCzList where groupyd not equals to UPDATED_GROUPYD
        defaultFkCzShouldBeFound("groupyd.notEquals=" + UPDATED_GROUPYD);
    }

    @Test
    @Transactional
    void getAllFkCzsByGroupydIsInShouldWork() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where groupyd in DEFAULT_GROUPYD or UPDATED_GROUPYD
        defaultFkCzShouldBeFound("groupyd.in=" + DEFAULT_GROUPYD + "," + UPDATED_GROUPYD);

        // Get all the fkCzList where groupyd equals to UPDATED_GROUPYD
        defaultFkCzShouldNotBeFound("groupyd.in=" + UPDATED_GROUPYD);
    }

    @Test
    @Transactional
    void getAllFkCzsByGroupydIsNullOrNotNull() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where groupyd is not null
        defaultFkCzShouldBeFound("groupyd.specified=true");

        // Get all the fkCzList where groupyd is null
        defaultFkCzShouldNotBeFound("groupyd.specified=false");
    }

    @Test
    @Transactional
    void getAllFkCzsByGroupydIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where groupyd is greater than or equal to DEFAULT_GROUPYD
        defaultFkCzShouldBeFound("groupyd.greaterThanOrEqual=" + DEFAULT_GROUPYD);

        // Get all the fkCzList where groupyd is greater than or equal to UPDATED_GROUPYD
        defaultFkCzShouldNotBeFound("groupyd.greaterThanOrEqual=" + UPDATED_GROUPYD);
    }

    @Test
    @Transactional
    void getAllFkCzsByGroupydIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where groupyd is less than or equal to DEFAULT_GROUPYD
        defaultFkCzShouldBeFound("groupyd.lessThanOrEqual=" + DEFAULT_GROUPYD);

        // Get all the fkCzList where groupyd is less than or equal to SMALLER_GROUPYD
        defaultFkCzShouldNotBeFound("groupyd.lessThanOrEqual=" + SMALLER_GROUPYD);
    }

    @Test
    @Transactional
    void getAllFkCzsByGroupydIsLessThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where groupyd is less than DEFAULT_GROUPYD
        defaultFkCzShouldNotBeFound("groupyd.lessThan=" + DEFAULT_GROUPYD);

        // Get all the fkCzList where groupyd is less than UPDATED_GROUPYD
        defaultFkCzShouldBeFound("groupyd.lessThan=" + UPDATED_GROUPYD);
    }

    @Test
    @Transactional
    void getAllFkCzsByGroupydIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where groupyd is greater than DEFAULT_GROUPYD
        defaultFkCzShouldNotBeFound("groupyd.greaterThan=" + DEFAULT_GROUPYD);

        // Get all the fkCzList where groupyd is greater than SMALLER_GROUPYD
        defaultFkCzShouldBeFound("groupyd.greaterThan=" + SMALLER_GROUPYD);
    }

    @Test
    @Transactional
    void getAllFkCzsBySkydIsEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where skyd equals to DEFAULT_SKYD
        defaultFkCzShouldBeFound("skyd.equals=" + DEFAULT_SKYD);

        // Get all the fkCzList where skyd equals to UPDATED_SKYD
        defaultFkCzShouldNotBeFound("skyd.equals=" + UPDATED_SKYD);
    }

    @Test
    @Transactional
    void getAllFkCzsBySkydIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where skyd not equals to DEFAULT_SKYD
        defaultFkCzShouldNotBeFound("skyd.notEquals=" + DEFAULT_SKYD);

        // Get all the fkCzList where skyd not equals to UPDATED_SKYD
        defaultFkCzShouldBeFound("skyd.notEquals=" + UPDATED_SKYD);
    }

    @Test
    @Transactional
    void getAllFkCzsBySkydIsInShouldWork() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where skyd in DEFAULT_SKYD or UPDATED_SKYD
        defaultFkCzShouldBeFound("skyd.in=" + DEFAULT_SKYD + "," + UPDATED_SKYD);

        // Get all the fkCzList where skyd equals to UPDATED_SKYD
        defaultFkCzShouldNotBeFound("skyd.in=" + UPDATED_SKYD);
    }

    @Test
    @Transactional
    void getAllFkCzsBySkydIsNullOrNotNull() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where skyd is not null
        defaultFkCzShouldBeFound("skyd.specified=true");

        // Get all the fkCzList where skyd is null
        defaultFkCzShouldNotBeFound("skyd.specified=false");
    }

    @Test
    @Transactional
    void getAllFkCzsBySkydIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where skyd is greater than or equal to DEFAULT_SKYD
        defaultFkCzShouldBeFound("skyd.greaterThanOrEqual=" + DEFAULT_SKYD);

        // Get all the fkCzList where skyd is greater than or equal to UPDATED_SKYD
        defaultFkCzShouldNotBeFound("skyd.greaterThanOrEqual=" + UPDATED_SKYD);
    }

    @Test
    @Transactional
    void getAllFkCzsBySkydIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where skyd is less than or equal to DEFAULT_SKYD
        defaultFkCzShouldBeFound("skyd.lessThanOrEqual=" + DEFAULT_SKYD);

        // Get all the fkCzList where skyd is less than or equal to SMALLER_SKYD
        defaultFkCzShouldNotBeFound("skyd.lessThanOrEqual=" + SMALLER_SKYD);
    }

    @Test
    @Transactional
    void getAllFkCzsBySkydIsLessThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where skyd is less than DEFAULT_SKYD
        defaultFkCzShouldNotBeFound("skyd.lessThan=" + DEFAULT_SKYD);

        // Get all the fkCzList where skyd is less than UPDATED_SKYD
        defaultFkCzShouldBeFound("skyd.lessThan=" + UPDATED_SKYD);
    }

    @Test
    @Transactional
    void getAllFkCzsBySkydIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where skyd is greater than DEFAULT_SKYD
        defaultFkCzShouldNotBeFound("skyd.greaterThan=" + DEFAULT_SKYD);

        // Get all the fkCzList where skyd is greater than SMALLER_SKYD
        defaultFkCzShouldBeFound("skyd.greaterThan=" + SMALLER_SKYD);
    }

    @Test
    @Transactional
    void getAllFkCzsByYdwdIsEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ydwd equals to DEFAULT_YDWD
        defaultFkCzShouldBeFound("ydwd.equals=" + DEFAULT_YDWD);

        // Get all the fkCzList where ydwd equals to UPDATED_YDWD
        defaultFkCzShouldNotBeFound("ydwd.equals=" + UPDATED_YDWD);
    }

    @Test
    @Transactional
    void getAllFkCzsByYdwdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ydwd not equals to DEFAULT_YDWD
        defaultFkCzShouldNotBeFound("ydwd.notEquals=" + DEFAULT_YDWD);

        // Get all the fkCzList where ydwd not equals to UPDATED_YDWD
        defaultFkCzShouldBeFound("ydwd.notEquals=" + UPDATED_YDWD);
    }

    @Test
    @Transactional
    void getAllFkCzsByYdwdIsInShouldWork() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ydwd in DEFAULT_YDWD or UPDATED_YDWD
        defaultFkCzShouldBeFound("ydwd.in=" + DEFAULT_YDWD + "," + UPDATED_YDWD);

        // Get all the fkCzList where ydwd equals to UPDATED_YDWD
        defaultFkCzShouldNotBeFound("ydwd.in=" + UPDATED_YDWD);
    }

    @Test
    @Transactional
    void getAllFkCzsByYdwdIsNullOrNotNull() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ydwd is not null
        defaultFkCzShouldBeFound("ydwd.specified=true");

        // Get all the fkCzList where ydwd is null
        defaultFkCzShouldNotBeFound("ydwd.specified=false");
    }

    @Test
    @Transactional
    void getAllFkCzsByYdwdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ydwd is greater than or equal to DEFAULT_YDWD
        defaultFkCzShouldBeFound("ydwd.greaterThanOrEqual=" + DEFAULT_YDWD);

        // Get all the fkCzList where ydwd is greater than or equal to UPDATED_YDWD
        defaultFkCzShouldNotBeFound("ydwd.greaterThanOrEqual=" + UPDATED_YDWD);
    }

    @Test
    @Transactional
    void getAllFkCzsByYdwdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ydwd is less than or equal to DEFAULT_YDWD
        defaultFkCzShouldBeFound("ydwd.lessThanOrEqual=" + DEFAULT_YDWD);

        // Get all the fkCzList where ydwd is less than or equal to SMALLER_YDWD
        defaultFkCzShouldNotBeFound("ydwd.lessThanOrEqual=" + SMALLER_YDWD);
    }

    @Test
    @Transactional
    void getAllFkCzsByYdwdIsLessThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ydwd is less than DEFAULT_YDWD
        defaultFkCzShouldNotBeFound("ydwd.lessThan=" + DEFAULT_YDWD);

        // Get all the fkCzList where ydwd is less than UPDATED_YDWD
        defaultFkCzShouldBeFound("ydwd.lessThan=" + UPDATED_YDWD);
    }

    @Test
    @Transactional
    void getAllFkCzsByYdwdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where ydwd is greater than DEFAULT_YDWD
        defaultFkCzShouldNotBeFound("ydwd.greaterThan=" + DEFAULT_YDWD);

        // Get all the fkCzList where ydwd is greater than SMALLER_YDWD
        defaultFkCzShouldBeFound("ydwd.greaterThan=" + SMALLER_YDWD);
    }

    @Test
    @Transactional
    void getAllFkCzsByQxydIsEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where qxyd equals to DEFAULT_QXYD
        defaultFkCzShouldBeFound("qxyd.equals=" + DEFAULT_QXYD);

        // Get all the fkCzList where qxyd equals to UPDATED_QXYD
        defaultFkCzShouldNotBeFound("qxyd.equals=" + UPDATED_QXYD);
    }

    @Test
    @Transactional
    void getAllFkCzsByQxydIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where qxyd not equals to DEFAULT_QXYD
        defaultFkCzShouldNotBeFound("qxyd.notEquals=" + DEFAULT_QXYD);

        // Get all the fkCzList where qxyd not equals to UPDATED_QXYD
        defaultFkCzShouldBeFound("qxyd.notEquals=" + UPDATED_QXYD);
    }

    @Test
    @Transactional
    void getAllFkCzsByQxydIsInShouldWork() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where qxyd in DEFAULT_QXYD or UPDATED_QXYD
        defaultFkCzShouldBeFound("qxyd.in=" + DEFAULT_QXYD + "," + UPDATED_QXYD);

        // Get all the fkCzList where qxyd equals to UPDATED_QXYD
        defaultFkCzShouldNotBeFound("qxyd.in=" + UPDATED_QXYD);
    }

    @Test
    @Transactional
    void getAllFkCzsByQxydIsNullOrNotNull() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where qxyd is not null
        defaultFkCzShouldBeFound("qxyd.specified=true");

        // Get all the fkCzList where qxyd is null
        defaultFkCzShouldNotBeFound("qxyd.specified=false");
    }

    @Test
    @Transactional
    void getAllFkCzsByQxydIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where qxyd is greater than or equal to DEFAULT_QXYD
        defaultFkCzShouldBeFound("qxyd.greaterThanOrEqual=" + DEFAULT_QXYD);

        // Get all the fkCzList where qxyd is greater than or equal to UPDATED_QXYD
        defaultFkCzShouldNotBeFound("qxyd.greaterThanOrEqual=" + UPDATED_QXYD);
    }

    @Test
    @Transactional
    void getAllFkCzsByQxydIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where qxyd is less than or equal to DEFAULT_QXYD
        defaultFkCzShouldBeFound("qxyd.lessThanOrEqual=" + DEFAULT_QXYD);

        // Get all the fkCzList where qxyd is less than or equal to SMALLER_QXYD
        defaultFkCzShouldNotBeFound("qxyd.lessThanOrEqual=" + SMALLER_QXYD);
    }

    @Test
    @Transactional
    void getAllFkCzsByQxydIsLessThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where qxyd is less than DEFAULT_QXYD
        defaultFkCzShouldNotBeFound("qxyd.lessThan=" + DEFAULT_QXYD);

        // Get all the fkCzList where qxyd is less than UPDATED_QXYD
        defaultFkCzShouldBeFound("qxyd.lessThan=" + UPDATED_QXYD);
    }

    @Test
    @Transactional
    void getAllFkCzsByQxydIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where qxyd is greater than DEFAULT_QXYD
        defaultFkCzShouldNotBeFound("qxyd.greaterThan=" + DEFAULT_QXYD);

        // Get all the fkCzList where qxyd is greater than SMALLER_QXYD
        defaultFkCzShouldBeFound("qxyd.greaterThan=" + SMALLER_QXYD);
    }

    @Test
    @Transactional
    void getAllFkCzsByIsnewIsEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where isnew equals to DEFAULT_ISNEW
        defaultFkCzShouldBeFound("isnew.equals=" + DEFAULT_ISNEW);

        // Get all the fkCzList where isnew equals to UPDATED_ISNEW
        defaultFkCzShouldNotBeFound("isnew.equals=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllFkCzsByIsnewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where isnew not equals to DEFAULT_ISNEW
        defaultFkCzShouldNotBeFound("isnew.notEquals=" + DEFAULT_ISNEW);

        // Get all the fkCzList where isnew not equals to UPDATED_ISNEW
        defaultFkCzShouldBeFound("isnew.notEquals=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllFkCzsByIsnewIsInShouldWork() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where isnew in DEFAULT_ISNEW or UPDATED_ISNEW
        defaultFkCzShouldBeFound("isnew.in=" + DEFAULT_ISNEW + "," + UPDATED_ISNEW);

        // Get all the fkCzList where isnew equals to UPDATED_ISNEW
        defaultFkCzShouldNotBeFound("isnew.in=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllFkCzsByIsnewIsNullOrNotNull() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where isnew is not null
        defaultFkCzShouldBeFound("isnew.specified=true");

        // Get all the fkCzList where isnew is null
        defaultFkCzShouldNotBeFound("isnew.specified=false");
    }

    @Test
    @Transactional
    void getAllFkCzsByIsnewIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where isnew is greater than or equal to DEFAULT_ISNEW
        defaultFkCzShouldBeFound("isnew.greaterThanOrEqual=" + DEFAULT_ISNEW);

        // Get all the fkCzList where isnew is greater than or equal to UPDATED_ISNEW
        defaultFkCzShouldNotBeFound("isnew.greaterThanOrEqual=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllFkCzsByIsnewIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where isnew is less than or equal to DEFAULT_ISNEW
        defaultFkCzShouldBeFound("isnew.lessThanOrEqual=" + DEFAULT_ISNEW);

        // Get all the fkCzList where isnew is less than or equal to SMALLER_ISNEW
        defaultFkCzShouldNotBeFound("isnew.lessThanOrEqual=" + SMALLER_ISNEW);
    }

    @Test
    @Transactional
    void getAllFkCzsByIsnewIsLessThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where isnew is less than DEFAULT_ISNEW
        defaultFkCzShouldNotBeFound("isnew.lessThan=" + DEFAULT_ISNEW);

        // Get all the fkCzList where isnew is less than UPDATED_ISNEW
        defaultFkCzShouldBeFound("isnew.lessThan=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllFkCzsByIsnewIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where isnew is greater than DEFAULT_ISNEW
        defaultFkCzShouldNotBeFound("isnew.greaterThan=" + DEFAULT_ISNEW);

        // Get all the fkCzList where isnew is greater than SMALLER_ISNEW
        defaultFkCzShouldBeFound("isnew.greaterThan=" + SMALLER_ISNEW);
    }

    @Test
    @Transactional
    void getAllFkCzsByHoteldmIsEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hoteldm equals to DEFAULT_HOTELDM
        defaultFkCzShouldBeFound("hoteldm.equals=" + DEFAULT_HOTELDM);

        // Get all the fkCzList where hoteldm equals to UPDATED_HOTELDM
        defaultFkCzShouldNotBeFound("hoteldm.equals=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllFkCzsByHoteldmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hoteldm not equals to DEFAULT_HOTELDM
        defaultFkCzShouldNotBeFound("hoteldm.notEquals=" + DEFAULT_HOTELDM);

        // Get all the fkCzList where hoteldm not equals to UPDATED_HOTELDM
        defaultFkCzShouldBeFound("hoteldm.notEquals=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllFkCzsByHoteldmIsInShouldWork() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hoteldm in DEFAULT_HOTELDM or UPDATED_HOTELDM
        defaultFkCzShouldBeFound("hoteldm.in=" + DEFAULT_HOTELDM + "," + UPDATED_HOTELDM);

        // Get all the fkCzList where hoteldm equals to UPDATED_HOTELDM
        defaultFkCzShouldNotBeFound("hoteldm.in=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllFkCzsByHoteldmIsNullOrNotNull() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hoteldm is not null
        defaultFkCzShouldBeFound("hoteldm.specified=true");

        // Get all the fkCzList where hoteldm is null
        defaultFkCzShouldNotBeFound("hoteldm.specified=false");
    }

    @Test
    @Transactional
    void getAllFkCzsByHoteldmContainsSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hoteldm contains DEFAULT_HOTELDM
        defaultFkCzShouldBeFound("hoteldm.contains=" + DEFAULT_HOTELDM);

        // Get all the fkCzList where hoteldm contains UPDATED_HOTELDM
        defaultFkCzShouldNotBeFound("hoteldm.contains=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllFkCzsByHoteldmNotContainsSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hoteldm does not contain DEFAULT_HOTELDM
        defaultFkCzShouldNotBeFound("hoteldm.doesNotContain=" + DEFAULT_HOTELDM);

        // Get all the fkCzList where hoteldm does not contain UPDATED_HOTELDM
        defaultFkCzShouldBeFound("hoteldm.doesNotContain=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllFkCzsByHysIsEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hys equals to DEFAULT_HYS
        defaultFkCzShouldBeFound("hys.equals=" + DEFAULT_HYS);

        // Get all the fkCzList where hys equals to UPDATED_HYS
        defaultFkCzShouldNotBeFound("hys.equals=" + UPDATED_HYS);
    }

    @Test
    @Transactional
    void getAllFkCzsByHysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hys not equals to DEFAULT_HYS
        defaultFkCzShouldNotBeFound("hys.notEquals=" + DEFAULT_HYS);

        // Get all the fkCzList where hys not equals to UPDATED_HYS
        defaultFkCzShouldBeFound("hys.notEquals=" + UPDATED_HYS);
    }

    @Test
    @Transactional
    void getAllFkCzsByHysIsInShouldWork() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hys in DEFAULT_HYS or UPDATED_HYS
        defaultFkCzShouldBeFound("hys.in=" + DEFAULT_HYS + "," + UPDATED_HYS);

        // Get all the fkCzList where hys equals to UPDATED_HYS
        defaultFkCzShouldNotBeFound("hys.in=" + UPDATED_HYS);
    }

    @Test
    @Transactional
    void getAllFkCzsByHysIsNullOrNotNull() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hys is not null
        defaultFkCzShouldBeFound("hys.specified=true");

        // Get all the fkCzList where hys is null
        defaultFkCzShouldNotBeFound("hys.specified=false");
    }

    @Test
    @Transactional
    void getAllFkCzsByHysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hys is greater than or equal to DEFAULT_HYS
        defaultFkCzShouldBeFound("hys.greaterThanOrEqual=" + DEFAULT_HYS);

        // Get all the fkCzList where hys is greater than or equal to UPDATED_HYS
        defaultFkCzShouldNotBeFound("hys.greaterThanOrEqual=" + UPDATED_HYS);
    }

    @Test
    @Transactional
    void getAllFkCzsByHysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hys is less than or equal to DEFAULT_HYS
        defaultFkCzShouldBeFound("hys.lessThanOrEqual=" + DEFAULT_HYS);

        // Get all the fkCzList where hys is less than or equal to SMALLER_HYS
        defaultFkCzShouldNotBeFound("hys.lessThanOrEqual=" + SMALLER_HYS);
    }

    @Test
    @Transactional
    void getAllFkCzsByHysIsLessThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hys is less than DEFAULT_HYS
        defaultFkCzShouldNotBeFound("hys.lessThan=" + DEFAULT_HYS);

        // Get all the fkCzList where hys is less than UPDATED_HYS
        defaultFkCzShouldBeFound("hys.lessThan=" + UPDATED_HYS);
    }

    @Test
    @Transactional
    void getAllFkCzsByHysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where hys is greater than DEFAULT_HYS
        defaultFkCzShouldNotBeFound("hys.greaterThan=" + DEFAULT_HYS);

        // Get all the fkCzList where hys is greater than SMALLER_HYS
        defaultFkCzShouldBeFound("hys.greaterThan=" + SMALLER_HYS);
    }

    @Test
    @Transactional
    void getAllFkCzsByKhysIsEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where khys equals to DEFAULT_KHYS
        defaultFkCzShouldBeFound("khys.equals=" + DEFAULT_KHYS);

        // Get all the fkCzList where khys equals to UPDATED_KHYS
        defaultFkCzShouldNotBeFound("khys.equals=" + UPDATED_KHYS);
    }

    @Test
    @Transactional
    void getAllFkCzsByKhysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where khys not equals to DEFAULT_KHYS
        defaultFkCzShouldNotBeFound("khys.notEquals=" + DEFAULT_KHYS);

        // Get all the fkCzList where khys not equals to UPDATED_KHYS
        defaultFkCzShouldBeFound("khys.notEquals=" + UPDATED_KHYS);
    }

    @Test
    @Transactional
    void getAllFkCzsByKhysIsInShouldWork() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where khys in DEFAULT_KHYS or UPDATED_KHYS
        defaultFkCzShouldBeFound("khys.in=" + DEFAULT_KHYS + "," + UPDATED_KHYS);

        // Get all the fkCzList where khys equals to UPDATED_KHYS
        defaultFkCzShouldNotBeFound("khys.in=" + UPDATED_KHYS);
    }

    @Test
    @Transactional
    void getAllFkCzsByKhysIsNullOrNotNull() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where khys is not null
        defaultFkCzShouldBeFound("khys.specified=true");

        // Get all the fkCzList where khys is null
        defaultFkCzShouldNotBeFound("khys.specified=false");
    }

    @Test
    @Transactional
    void getAllFkCzsByKhysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where khys is greater than or equal to DEFAULT_KHYS
        defaultFkCzShouldBeFound("khys.greaterThanOrEqual=" + DEFAULT_KHYS);

        // Get all the fkCzList where khys is greater than or equal to UPDATED_KHYS
        defaultFkCzShouldNotBeFound("khys.greaterThanOrEqual=" + UPDATED_KHYS);
    }

    @Test
    @Transactional
    void getAllFkCzsByKhysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where khys is less than or equal to DEFAULT_KHYS
        defaultFkCzShouldBeFound("khys.lessThanOrEqual=" + DEFAULT_KHYS);

        // Get all the fkCzList where khys is less than or equal to SMALLER_KHYS
        defaultFkCzShouldNotBeFound("khys.lessThanOrEqual=" + SMALLER_KHYS);
    }

    @Test
    @Transactional
    void getAllFkCzsByKhysIsLessThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where khys is less than DEFAULT_KHYS
        defaultFkCzShouldNotBeFound("khys.lessThan=" + DEFAULT_KHYS);

        // Get all the fkCzList where khys is less than UPDATED_KHYS
        defaultFkCzShouldBeFound("khys.lessThan=" + UPDATED_KHYS);
    }

    @Test
    @Transactional
    void getAllFkCzsByKhysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        // Get all the fkCzList where khys is greater than DEFAULT_KHYS
        defaultFkCzShouldNotBeFound("khys.greaterThan=" + DEFAULT_KHYS);

        // Get all the fkCzList where khys is greater than SMALLER_KHYS
        defaultFkCzShouldBeFound("khys.greaterThan=" + SMALLER_KHYS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFkCzShouldBeFound(String filter) throws Exception {
        restFkCzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fkCz.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].wxf").value(hasItem(DEFAULT_WXF.intValue())))
            .andExpect(jsonPath("$.[*].ksf").value(hasItem(DEFAULT_KSF.intValue())))
            .andExpect(jsonPath("$.[*].kf").value(hasItem(DEFAULT_KF.intValue())))
            .andExpect(jsonPath("$.[*].zfs").value(hasItem(DEFAULT_ZFS.intValue())))
            .andExpect(jsonPath("$.[*].groupyd").value(hasItem(DEFAULT_GROUPYD.intValue())))
            .andExpect(jsonPath("$.[*].skyd").value(hasItem(DEFAULT_SKYD.intValue())))
            .andExpect(jsonPath("$.[*].ydwd").value(hasItem(DEFAULT_YDWD.intValue())))
            .andExpect(jsonPath("$.[*].qxyd").value(hasItem(DEFAULT_QXYD.intValue())))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].hys").value(hasItem(sameNumber(DEFAULT_HYS))))
            .andExpect(jsonPath("$.[*].khys").value(hasItem(sameNumber(DEFAULT_KHYS))));

        // Check, that the count call also returns 1
        restFkCzMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFkCzShouldNotBeFound(String filter) throws Exception {
        restFkCzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFkCzMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFkCz() throws Exception {
        // Get the fkCz
        restFkCzMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFkCz() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        int databaseSizeBeforeUpdate = fkCzRepository.findAll().size();

        // Update the fkCz
        FkCz updatedFkCz = fkCzRepository.findById(fkCz.getId()).get();
        // Disconnect from session so that the updates on updatedFkCz are not directly saved in db
        em.detach(updatedFkCz);
        updatedFkCz
            .hoteltime(UPDATED_HOTELTIME)
            .wxf(UPDATED_WXF)
            .ksf(UPDATED_KSF)
            .kf(UPDATED_KF)
            .zfs(UPDATED_ZFS)
            .groupyd(UPDATED_GROUPYD)
            .skyd(UPDATED_SKYD)
            .ydwd(UPDATED_YDWD)
            .qxyd(UPDATED_QXYD)
            .isnew(UPDATED_ISNEW)
            .hoteldm(UPDATED_HOTELDM)
            .hys(UPDATED_HYS)
            .khys(UPDATED_KHYS);
        FkCzDTO fkCzDTO = fkCzMapper.toDto(updatedFkCz);

        restFkCzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fkCzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fkCzDTO))
            )
            .andExpect(status().isOk());

        // Validate the FkCz in the database
        List<FkCz> fkCzList = fkCzRepository.findAll();
        assertThat(fkCzList).hasSize(databaseSizeBeforeUpdate);
        FkCz testFkCz = fkCzList.get(fkCzList.size() - 1);
        assertThat(testFkCz.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testFkCz.getWxf()).isEqualTo(UPDATED_WXF);
        assertThat(testFkCz.getKsf()).isEqualTo(UPDATED_KSF);
        assertThat(testFkCz.getKf()).isEqualTo(UPDATED_KF);
        assertThat(testFkCz.getZfs()).isEqualTo(UPDATED_ZFS);
        assertThat(testFkCz.getGroupyd()).isEqualTo(UPDATED_GROUPYD);
        assertThat(testFkCz.getSkyd()).isEqualTo(UPDATED_SKYD);
        assertThat(testFkCz.getYdwd()).isEqualTo(UPDATED_YDWD);
        assertThat(testFkCz.getQxyd()).isEqualTo(UPDATED_QXYD);
        assertThat(testFkCz.getIsnew()).isEqualTo(UPDATED_ISNEW);
        assertThat(testFkCz.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testFkCz.getHys()).isEqualTo(UPDATED_HYS);
        assertThat(testFkCz.getKhys()).isEqualTo(UPDATED_KHYS);

        // Validate the FkCz in Elasticsearch
        verify(mockFkCzSearchRepository).save(testFkCz);
    }

    @Test
    @Transactional
    void putNonExistingFkCz() throws Exception {
        int databaseSizeBeforeUpdate = fkCzRepository.findAll().size();
        fkCz.setId(count.incrementAndGet());

        // Create the FkCz
        FkCzDTO fkCzDTO = fkCzMapper.toDto(fkCz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFkCzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fkCzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fkCzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FkCz in the database
        List<FkCz> fkCzList = fkCzRepository.findAll();
        assertThat(fkCzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FkCz in Elasticsearch
        verify(mockFkCzSearchRepository, times(0)).save(fkCz);
    }

    @Test
    @Transactional
    void putWithIdMismatchFkCz() throws Exception {
        int databaseSizeBeforeUpdate = fkCzRepository.findAll().size();
        fkCz.setId(count.incrementAndGet());

        // Create the FkCz
        FkCzDTO fkCzDTO = fkCzMapper.toDto(fkCz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFkCzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fkCzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FkCz in the database
        List<FkCz> fkCzList = fkCzRepository.findAll();
        assertThat(fkCzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FkCz in Elasticsearch
        verify(mockFkCzSearchRepository, times(0)).save(fkCz);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFkCz() throws Exception {
        int databaseSizeBeforeUpdate = fkCzRepository.findAll().size();
        fkCz.setId(count.incrementAndGet());

        // Create the FkCz
        FkCzDTO fkCzDTO = fkCzMapper.toDto(fkCz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFkCzMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fkCzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FkCz in the database
        List<FkCz> fkCzList = fkCzRepository.findAll();
        assertThat(fkCzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FkCz in Elasticsearch
        verify(mockFkCzSearchRepository, times(0)).save(fkCz);
    }

    @Test
    @Transactional
    void partialUpdateFkCzWithPatch() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        int databaseSizeBeforeUpdate = fkCzRepository.findAll().size();

        // Update the fkCz using partial update
        FkCz partialUpdatedFkCz = new FkCz();
        partialUpdatedFkCz.setId(fkCz.getId());

        partialUpdatedFkCz
            .wxf(UPDATED_WXF)
            .ksf(UPDATED_KSF)
            .kf(UPDATED_KF)
            .zfs(UPDATED_ZFS)
            .skyd(UPDATED_SKYD)
            .ydwd(UPDATED_YDWD)
            .isnew(UPDATED_ISNEW)
            .hys(UPDATED_HYS)
            .khys(UPDATED_KHYS);

        restFkCzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFkCz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFkCz))
            )
            .andExpect(status().isOk());

        // Validate the FkCz in the database
        List<FkCz> fkCzList = fkCzRepository.findAll();
        assertThat(fkCzList).hasSize(databaseSizeBeforeUpdate);
        FkCz testFkCz = fkCzList.get(fkCzList.size() - 1);
        assertThat(testFkCz.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testFkCz.getWxf()).isEqualTo(UPDATED_WXF);
        assertThat(testFkCz.getKsf()).isEqualTo(UPDATED_KSF);
        assertThat(testFkCz.getKf()).isEqualTo(UPDATED_KF);
        assertThat(testFkCz.getZfs()).isEqualTo(UPDATED_ZFS);
        assertThat(testFkCz.getGroupyd()).isEqualTo(DEFAULT_GROUPYD);
        assertThat(testFkCz.getSkyd()).isEqualTo(UPDATED_SKYD);
        assertThat(testFkCz.getYdwd()).isEqualTo(UPDATED_YDWD);
        assertThat(testFkCz.getQxyd()).isEqualTo(DEFAULT_QXYD);
        assertThat(testFkCz.getIsnew()).isEqualTo(UPDATED_ISNEW);
        assertThat(testFkCz.getHoteldm()).isEqualTo(DEFAULT_HOTELDM);
        assertThat(testFkCz.getHys()).isEqualByComparingTo(UPDATED_HYS);
        assertThat(testFkCz.getKhys()).isEqualByComparingTo(UPDATED_KHYS);
    }

    @Test
    @Transactional
    void fullUpdateFkCzWithPatch() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        int databaseSizeBeforeUpdate = fkCzRepository.findAll().size();

        // Update the fkCz using partial update
        FkCz partialUpdatedFkCz = new FkCz();
        partialUpdatedFkCz.setId(fkCz.getId());

        partialUpdatedFkCz
            .hoteltime(UPDATED_HOTELTIME)
            .wxf(UPDATED_WXF)
            .ksf(UPDATED_KSF)
            .kf(UPDATED_KF)
            .zfs(UPDATED_ZFS)
            .groupyd(UPDATED_GROUPYD)
            .skyd(UPDATED_SKYD)
            .ydwd(UPDATED_YDWD)
            .qxyd(UPDATED_QXYD)
            .isnew(UPDATED_ISNEW)
            .hoteldm(UPDATED_HOTELDM)
            .hys(UPDATED_HYS)
            .khys(UPDATED_KHYS);

        restFkCzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFkCz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFkCz))
            )
            .andExpect(status().isOk());

        // Validate the FkCz in the database
        List<FkCz> fkCzList = fkCzRepository.findAll();
        assertThat(fkCzList).hasSize(databaseSizeBeforeUpdate);
        FkCz testFkCz = fkCzList.get(fkCzList.size() - 1);
        assertThat(testFkCz.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testFkCz.getWxf()).isEqualTo(UPDATED_WXF);
        assertThat(testFkCz.getKsf()).isEqualTo(UPDATED_KSF);
        assertThat(testFkCz.getKf()).isEqualTo(UPDATED_KF);
        assertThat(testFkCz.getZfs()).isEqualTo(UPDATED_ZFS);
        assertThat(testFkCz.getGroupyd()).isEqualTo(UPDATED_GROUPYD);
        assertThat(testFkCz.getSkyd()).isEqualTo(UPDATED_SKYD);
        assertThat(testFkCz.getYdwd()).isEqualTo(UPDATED_YDWD);
        assertThat(testFkCz.getQxyd()).isEqualTo(UPDATED_QXYD);
        assertThat(testFkCz.getIsnew()).isEqualTo(UPDATED_ISNEW);
        assertThat(testFkCz.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testFkCz.getHys()).isEqualByComparingTo(UPDATED_HYS);
        assertThat(testFkCz.getKhys()).isEqualByComparingTo(UPDATED_KHYS);
    }

    @Test
    @Transactional
    void patchNonExistingFkCz() throws Exception {
        int databaseSizeBeforeUpdate = fkCzRepository.findAll().size();
        fkCz.setId(count.incrementAndGet());

        // Create the FkCz
        FkCzDTO fkCzDTO = fkCzMapper.toDto(fkCz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFkCzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fkCzDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fkCzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FkCz in the database
        List<FkCz> fkCzList = fkCzRepository.findAll();
        assertThat(fkCzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FkCz in Elasticsearch
        verify(mockFkCzSearchRepository, times(0)).save(fkCz);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFkCz() throws Exception {
        int databaseSizeBeforeUpdate = fkCzRepository.findAll().size();
        fkCz.setId(count.incrementAndGet());

        // Create the FkCz
        FkCzDTO fkCzDTO = fkCzMapper.toDto(fkCz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFkCzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fkCzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FkCz in the database
        List<FkCz> fkCzList = fkCzRepository.findAll();
        assertThat(fkCzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FkCz in Elasticsearch
        verify(mockFkCzSearchRepository, times(0)).save(fkCz);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFkCz() throws Exception {
        int databaseSizeBeforeUpdate = fkCzRepository.findAll().size();
        fkCz.setId(count.incrementAndGet());

        // Create the FkCz
        FkCzDTO fkCzDTO = fkCzMapper.toDto(fkCz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFkCzMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fkCzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FkCz in the database
        List<FkCz> fkCzList = fkCzRepository.findAll();
        assertThat(fkCzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FkCz in Elasticsearch
        verify(mockFkCzSearchRepository, times(0)).save(fkCz);
    }

    @Test
    @Transactional
    void deleteFkCz() throws Exception {
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);

        int databaseSizeBeforeDelete = fkCzRepository.findAll().size();

        // Delete the fkCz
        restFkCzMockMvc
            .perform(delete(ENTITY_API_URL_ID, fkCz.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FkCz> fkCzList = fkCzRepository.findAll();
        assertThat(fkCzList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FkCz in Elasticsearch
        verify(mockFkCzSearchRepository, times(1)).deleteById(fkCz.getId());
    }

    @Test
    @Transactional
    void searchFkCz() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        fkCzRepository.saveAndFlush(fkCz);
        when(mockFkCzSearchRepository.search(queryStringQuery("id:" + fkCz.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fkCz), PageRequest.of(0, 1), 1));

        // Search the fkCz
        restFkCzMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + fkCz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fkCz.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].wxf").value(hasItem(DEFAULT_WXF.intValue())))
            .andExpect(jsonPath("$.[*].ksf").value(hasItem(DEFAULT_KSF.intValue())))
            .andExpect(jsonPath("$.[*].kf").value(hasItem(DEFAULT_KF.intValue())))
            .andExpect(jsonPath("$.[*].zfs").value(hasItem(DEFAULT_ZFS.intValue())))
            .andExpect(jsonPath("$.[*].groupyd").value(hasItem(DEFAULT_GROUPYD.intValue())))
            .andExpect(jsonPath("$.[*].skyd").value(hasItem(DEFAULT_SKYD.intValue())))
            .andExpect(jsonPath("$.[*].ydwd").value(hasItem(DEFAULT_YDWD.intValue())))
            .andExpect(jsonPath("$.[*].qxyd").value(hasItem(DEFAULT_QXYD.intValue())))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].hys").value(hasItem(sameNumber(DEFAULT_HYS))))
            .andExpect(jsonPath("$.[*].khys").value(hasItem(sameNumber(DEFAULT_KHYS))));
    }
}
