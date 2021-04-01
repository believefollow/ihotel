package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.CzlCz;
import ihotel.app.repository.CzlCzRepository;
import ihotel.app.repository.search.CzlCzSearchRepository;
import ihotel.app.service.criteria.CzlCzCriteria;
import ihotel.app.service.dto.CzlCzDTO;
import ihotel.app.service.mapper.CzlCzMapper;
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
 * Integration tests for the {@link CzlCzResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CzlCzResourceIT {

    private static final Instant DEFAULT_TJRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TJRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_TYPEID = 1L;
    private static final Long UPDATED_TYPEID = 2L;
    private static final Long SMALLER_TYPEID = 1L - 1L;

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_FJSL = 1L;
    private static final Long UPDATED_FJSL = 2L;
    private static final Long SMALLER_FJSL = 1L - 1L;

    private static final BigDecimal DEFAULT_KFL = new BigDecimal(1);
    private static final BigDecimal UPDATED_KFL = new BigDecimal(2);
    private static final BigDecimal SMALLER_KFL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PJZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_PJZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_PJZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_YSFZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_YSFZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_YSFZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SJFZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_SJFZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_SJFZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FZCZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_FZCZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_FZCZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PJZCJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_PJZCJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_PJZCJ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_KFS_M = new BigDecimal(1);
    private static final BigDecimal UPDATED_KFS_M = new BigDecimal(2);
    private static final BigDecimal SMALLER_KFS_M = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_KFL_M = new BigDecimal(1);
    private static final BigDecimal UPDATED_KFL_M = new BigDecimal(2);
    private static final BigDecimal SMALLER_KFL_M = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PJZ_M = new BigDecimal(1);
    private static final BigDecimal UPDATED_PJZ_M = new BigDecimal(2);
    private static final BigDecimal SMALLER_PJZ_M = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FZSR = new BigDecimal(1);
    private static final BigDecimal UPDATED_FZSR = new BigDecimal(2);
    private static final BigDecimal SMALLER_FZSR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DAYZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_DAYZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_DAYZ = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_HOTELTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOTELTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MONTHZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONTHZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_MONTHZ = new BigDecimal(1 - 1);

    private static final String DEFAULT_HOTELDM = "AAAAAAAAAA";
    private static final String UPDATED_HOTELDM = "BBBBBBBBBB";

    private static final Long DEFAULT_ISNEW = 1L;
    private static final Long UPDATED_ISNEW = 2L;
    private static final Long SMALLER_ISNEW = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/czl-czs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/czl-czs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CzlCzRepository czlCzRepository;

    @Autowired
    private CzlCzMapper czlCzMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CzlCzSearchRepositoryMockConfiguration
     */
    @Autowired
    private CzlCzSearchRepository mockCzlCzSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCzlCzMockMvc;

    private CzlCz czlCz;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CzlCz createEntity(EntityManager em) {
        CzlCz czlCz = new CzlCz()
            .tjrq(DEFAULT_TJRQ)
            .typeid(DEFAULT_TYPEID)
            .type(DEFAULT_TYPE)
            .fjsl(DEFAULT_FJSL)
            .kfl(DEFAULT_KFL)
            .pjz(DEFAULT_PJZ)
            .ysfz(DEFAULT_YSFZ)
            .sjfz(DEFAULT_SJFZ)
            .fzcz(DEFAULT_FZCZ)
            .pjzcj(DEFAULT_PJZCJ)
            .kfsM(DEFAULT_KFS_M)
            .kflM(DEFAULT_KFL_M)
            .pjzM(DEFAULT_PJZ_M)
            .fzsr(DEFAULT_FZSR)
            .dayz(DEFAULT_DAYZ)
            .hoteltime(DEFAULT_HOTELTIME)
            .empn(DEFAULT_EMPN)
            .monthz(DEFAULT_MONTHZ)
            .hoteldm(DEFAULT_HOTELDM)
            .isnew(DEFAULT_ISNEW);
        return czlCz;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CzlCz createUpdatedEntity(EntityManager em) {
        CzlCz czlCz = new CzlCz()
            .tjrq(UPDATED_TJRQ)
            .typeid(UPDATED_TYPEID)
            .type(UPDATED_TYPE)
            .fjsl(UPDATED_FJSL)
            .kfl(UPDATED_KFL)
            .pjz(UPDATED_PJZ)
            .ysfz(UPDATED_YSFZ)
            .sjfz(UPDATED_SJFZ)
            .fzcz(UPDATED_FZCZ)
            .pjzcj(UPDATED_PJZCJ)
            .kfsM(UPDATED_KFS_M)
            .kflM(UPDATED_KFL_M)
            .pjzM(UPDATED_PJZ_M)
            .fzsr(UPDATED_FZSR)
            .dayz(UPDATED_DAYZ)
            .hoteltime(UPDATED_HOTELTIME)
            .empn(UPDATED_EMPN)
            .monthz(UPDATED_MONTHZ)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW);
        return czlCz;
    }

    @BeforeEach
    public void initTest() {
        czlCz = createEntity(em);
    }

    @Test
    @Transactional
    void createCzlCz() throws Exception {
        int databaseSizeBeforeCreate = czlCzRepository.findAll().size();
        // Create the CzlCz
        CzlCzDTO czlCzDTO = czlCzMapper.toDto(czlCz);
        restCzlCzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czlCzDTO)))
            .andExpect(status().isCreated());

        // Validate the CzlCz in the database
        List<CzlCz> czlCzList = czlCzRepository.findAll();
        assertThat(czlCzList).hasSize(databaseSizeBeforeCreate + 1);
        CzlCz testCzlCz = czlCzList.get(czlCzList.size() - 1);
        assertThat(testCzlCz.getTjrq()).isEqualTo(DEFAULT_TJRQ);
        assertThat(testCzlCz.getTypeid()).isEqualTo(DEFAULT_TYPEID);
        assertThat(testCzlCz.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCzlCz.getFjsl()).isEqualTo(DEFAULT_FJSL);
        assertThat(testCzlCz.getKfl()).isEqualByComparingTo(DEFAULT_KFL);
        assertThat(testCzlCz.getPjz()).isEqualByComparingTo(DEFAULT_PJZ);
        assertThat(testCzlCz.getYsfz()).isEqualByComparingTo(DEFAULT_YSFZ);
        assertThat(testCzlCz.getSjfz()).isEqualByComparingTo(DEFAULT_SJFZ);
        assertThat(testCzlCz.getFzcz()).isEqualByComparingTo(DEFAULT_FZCZ);
        assertThat(testCzlCz.getPjzcj()).isEqualByComparingTo(DEFAULT_PJZCJ);
        assertThat(testCzlCz.getKfsM()).isEqualByComparingTo(DEFAULT_KFS_M);
        assertThat(testCzlCz.getKflM()).isEqualByComparingTo(DEFAULT_KFL_M);
        assertThat(testCzlCz.getPjzM()).isEqualByComparingTo(DEFAULT_PJZ_M);
        assertThat(testCzlCz.getFzsr()).isEqualByComparingTo(DEFAULT_FZSR);
        assertThat(testCzlCz.getDayz()).isEqualByComparingTo(DEFAULT_DAYZ);
        assertThat(testCzlCz.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testCzlCz.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testCzlCz.getMonthz()).isEqualByComparingTo(DEFAULT_MONTHZ);
        assertThat(testCzlCz.getHoteldm()).isEqualTo(DEFAULT_HOTELDM);
        assertThat(testCzlCz.getIsnew()).isEqualTo(DEFAULT_ISNEW);

        // Validate the CzlCz in Elasticsearch
        verify(mockCzlCzSearchRepository, times(1)).save(testCzlCz);
    }

    @Test
    @Transactional
    void createCzlCzWithExistingId() throws Exception {
        // Create the CzlCz with an existing ID
        czlCz.setId(1L);
        CzlCzDTO czlCzDTO = czlCzMapper.toDto(czlCz);

        int databaseSizeBeforeCreate = czlCzRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCzlCzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czlCzDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CzlCz in the database
        List<CzlCz> czlCzList = czlCzRepository.findAll();
        assertThat(czlCzList).hasSize(databaseSizeBeforeCreate);

        // Validate the CzlCz in Elasticsearch
        verify(mockCzlCzSearchRepository, times(0)).save(czlCz);
    }

    @Test
    @Transactional
    void checkTjrqIsRequired() throws Exception {
        int databaseSizeBeforeTest = czlCzRepository.findAll().size();
        // set the field null
        czlCz.setTjrq(null);

        // Create the CzlCz, which fails.
        CzlCzDTO czlCzDTO = czlCzMapper.toDto(czlCz);

        restCzlCzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czlCzDTO)))
            .andExpect(status().isBadRequest());

        List<CzlCz> czlCzList = czlCzRepository.findAll();
        assertThat(czlCzList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = czlCzRepository.findAll().size();
        // set the field null
        czlCz.setType(null);

        // Create the CzlCz, which fails.
        CzlCzDTO czlCzDTO = czlCzMapper.toDto(czlCz);

        restCzlCzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czlCzDTO)))
            .andExpect(status().isBadRequest());

        List<CzlCz> czlCzList = czlCzRepository.findAll();
        assertThat(czlCzList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCzlCzs() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList
        restCzlCzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(czlCz.getId().intValue())))
            .andExpect(jsonPath("$.[*].tjrq").value(hasItem(DEFAULT_TJRQ.toString())))
            .andExpect(jsonPath("$.[*].typeid").value(hasItem(DEFAULT_TYPEID.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].fjsl").value(hasItem(DEFAULT_FJSL.intValue())))
            .andExpect(jsonPath("$.[*].kfl").value(hasItem(sameNumber(DEFAULT_KFL))))
            .andExpect(jsonPath("$.[*].pjz").value(hasItem(sameNumber(DEFAULT_PJZ))))
            .andExpect(jsonPath("$.[*].ysfz").value(hasItem(sameNumber(DEFAULT_YSFZ))))
            .andExpect(jsonPath("$.[*].sjfz").value(hasItem(sameNumber(DEFAULT_SJFZ))))
            .andExpect(jsonPath("$.[*].fzcz").value(hasItem(sameNumber(DEFAULT_FZCZ))))
            .andExpect(jsonPath("$.[*].pjzcj").value(hasItem(sameNumber(DEFAULT_PJZCJ))))
            .andExpect(jsonPath("$.[*].kfsM").value(hasItem(sameNumber(DEFAULT_KFS_M))))
            .andExpect(jsonPath("$.[*].kflM").value(hasItem(sameNumber(DEFAULT_KFL_M))))
            .andExpect(jsonPath("$.[*].pjzM").value(hasItem(sameNumber(DEFAULT_PJZ_M))))
            .andExpect(jsonPath("$.[*].fzsr").value(hasItem(sameNumber(DEFAULT_FZSR))))
            .andExpect(jsonPath("$.[*].dayz").value(hasItem(sameNumber(DEFAULT_DAYZ))))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].monthz").value(hasItem(sameNumber(DEFAULT_MONTHZ))))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())));
    }

    @Test
    @Transactional
    void getCzlCz() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get the czlCz
        restCzlCzMockMvc
            .perform(get(ENTITY_API_URL_ID, czlCz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(czlCz.getId().intValue()))
            .andExpect(jsonPath("$.tjrq").value(DEFAULT_TJRQ.toString()))
            .andExpect(jsonPath("$.typeid").value(DEFAULT_TYPEID.intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.fjsl").value(DEFAULT_FJSL.intValue()))
            .andExpect(jsonPath("$.kfl").value(sameNumber(DEFAULT_KFL)))
            .andExpect(jsonPath("$.pjz").value(sameNumber(DEFAULT_PJZ)))
            .andExpect(jsonPath("$.ysfz").value(sameNumber(DEFAULT_YSFZ)))
            .andExpect(jsonPath("$.sjfz").value(sameNumber(DEFAULT_SJFZ)))
            .andExpect(jsonPath("$.fzcz").value(sameNumber(DEFAULT_FZCZ)))
            .andExpect(jsonPath("$.pjzcj").value(sameNumber(DEFAULT_PJZCJ)))
            .andExpect(jsonPath("$.kfsM").value(sameNumber(DEFAULT_KFS_M)))
            .andExpect(jsonPath("$.kflM").value(sameNumber(DEFAULT_KFL_M)))
            .andExpect(jsonPath("$.pjzM").value(sameNumber(DEFAULT_PJZ_M)))
            .andExpect(jsonPath("$.fzsr").value(sameNumber(DEFAULT_FZSR)))
            .andExpect(jsonPath("$.dayz").value(sameNumber(DEFAULT_DAYZ)))
            .andExpect(jsonPath("$.hoteltime").value(DEFAULT_HOTELTIME.toString()))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.monthz").value(sameNumber(DEFAULT_MONTHZ)))
            .andExpect(jsonPath("$.hoteldm").value(DEFAULT_HOTELDM))
            .andExpect(jsonPath("$.isnew").value(DEFAULT_ISNEW.intValue()));
    }

    @Test
    @Transactional
    void getCzlCzsByIdFiltering() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        Long id = czlCz.getId();

        defaultCzlCzShouldBeFound("id.equals=" + id);
        defaultCzlCzShouldNotBeFound("id.notEquals=" + id);

        defaultCzlCzShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCzlCzShouldNotBeFound("id.greaterThan=" + id);

        defaultCzlCzShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCzlCzShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTjrqIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where tjrq equals to DEFAULT_TJRQ
        defaultCzlCzShouldBeFound("tjrq.equals=" + DEFAULT_TJRQ);

        // Get all the czlCzList where tjrq equals to UPDATED_TJRQ
        defaultCzlCzShouldNotBeFound("tjrq.equals=" + UPDATED_TJRQ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTjrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where tjrq not equals to DEFAULT_TJRQ
        defaultCzlCzShouldNotBeFound("tjrq.notEquals=" + DEFAULT_TJRQ);

        // Get all the czlCzList where tjrq not equals to UPDATED_TJRQ
        defaultCzlCzShouldBeFound("tjrq.notEquals=" + UPDATED_TJRQ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTjrqIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where tjrq in DEFAULT_TJRQ or UPDATED_TJRQ
        defaultCzlCzShouldBeFound("tjrq.in=" + DEFAULT_TJRQ + "," + UPDATED_TJRQ);

        // Get all the czlCzList where tjrq equals to UPDATED_TJRQ
        defaultCzlCzShouldNotBeFound("tjrq.in=" + UPDATED_TJRQ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTjrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where tjrq is not null
        defaultCzlCzShouldBeFound("tjrq.specified=true");

        // Get all the czlCzList where tjrq is null
        defaultCzlCzShouldNotBeFound("tjrq.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByTypeidIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where typeid equals to DEFAULT_TYPEID
        defaultCzlCzShouldBeFound("typeid.equals=" + DEFAULT_TYPEID);

        // Get all the czlCzList where typeid equals to UPDATED_TYPEID
        defaultCzlCzShouldNotBeFound("typeid.equals=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTypeidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where typeid not equals to DEFAULT_TYPEID
        defaultCzlCzShouldNotBeFound("typeid.notEquals=" + DEFAULT_TYPEID);

        // Get all the czlCzList where typeid not equals to UPDATED_TYPEID
        defaultCzlCzShouldBeFound("typeid.notEquals=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTypeidIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where typeid in DEFAULT_TYPEID or UPDATED_TYPEID
        defaultCzlCzShouldBeFound("typeid.in=" + DEFAULT_TYPEID + "," + UPDATED_TYPEID);

        // Get all the czlCzList where typeid equals to UPDATED_TYPEID
        defaultCzlCzShouldNotBeFound("typeid.in=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTypeidIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where typeid is not null
        defaultCzlCzShouldBeFound("typeid.specified=true");

        // Get all the czlCzList where typeid is null
        defaultCzlCzShouldNotBeFound("typeid.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByTypeidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where typeid is greater than or equal to DEFAULT_TYPEID
        defaultCzlCzShouldBeFound("typeid.greaterThanOrEqual=" + DEFAULT_TYPEID);

        // Get all the czlCzList where typeid is greater than or equal to UPDATED_TYPEID
        defaultCzlCzShouldNotBeFound("typeid.greaterThanOrEqual=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTypeidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where typeid is less than or equal to DEFAULT_TYPEID
        defaultCzlCzShouldBeFound("typeid.lessThanOrEqual=" + DEFAULT_TYPEID);

        // Get all the czlCzList where typeid is less than or equal to SMALLER_TYPEID
        defaultCzlCzShouldNotBeFound("typeid.lessThanOrEqual=" + SMALLER_TYPEID);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTypeidIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where typeid is less than DEFAULT_TYPEID
        defaultCzlCzShouldNotBeFound("typeid.lessThan=" + DEFAULT_TYPEID);

        // Get all the czlCzList where typeid is less than UPDATED_TYPEID
        defaultCzlCzShouldBeFound("typeid.lessThan=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTypeidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where typeid is greater than DEFAULT_TYPEID
        defaultCzlCzShouldNotBeFound("typeid.greaterThan=" + DEFAULT_TYPEID);

        // Get all the czlCzList where typeid is greater than SMALLER_TYPEID
        defaultCzlCzShouldBeFound("typeid.greaterThan=" + SMALLER_TYPEID);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where type equals to DEFAULT_TYPE
        defaultCzlCzShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the czlCzList where type equals to UPDATED_TYPE
        defaultCzlCzShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where type not equals to DEFAULT_TYPE
        defaultCzlCzShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the czlCzList where type not equals to UPDATED_TYPE
        defaultCzlCzShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCzlCzShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the czlCzList where type equals to UPDATED_TYPE
        defaultCzlCzShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where type is not null
        defaultCzlCzShouldBeFound("type.specified=true");

        // Get all the czlCzList where type is null
        defaultCzlCzShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByTypeContainsSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where type contains DEFAULT_TYPE
        defaultCzlCzShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the czlCzList where type contains UPDATED_TYPE
        defaultCzlCzShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCzlCzsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where type does not contain DEFAULT_TYPE
        defaultCzlCzShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the czlCzList where type does not contain UPDATED_TYPE
        defaultCzlCzShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFjslIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fjsl equals to DEFAULT_FJSL
        defaultCzlCzShouldBeFound("fjsl.equals=" + DEFAULT_FJSL);

        // Get all the czlCzList where fjsl equals to UPDATED_FJSL
        defaultCzlCzShouldNotBeFound("fjsl.equals=" + UPDATED_FJSL);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFjslIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fjsl not equals to DEFAULT_FJSL
        defaultCzlCzShouldNotBeFound("fjsl.notEquals=" + DEFAULT_FJSL);

        // Get all the czlCzList where fjsl not equals to UPDATED_FJSL
        defaultCzlCzShouldBeFound("fjsl.notEquals=" + UPDATED_FJSL);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFjslIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fjsl in DEFAULT_FJSL or UPDATED_FJSL
        defaultCzlCzShouldBeFound("fjsl.in=" + DEFAULT_FJSL + "," + UPDATED_FJSL);

        // Get all the czlCzList where fjsl equals to UPDATED_FJSL
        defaultCzlCzShouldNotBeFound("fjsl.in=" + UPDATED_FJSL);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFjslIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fjsl is not null
        defaultCzlCzShouldBeFound("fjsl.specified=true");

        // Get all the czlCzList where fjsl is null
        defaultCzlCzShouldNotBeFound("fjsl.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByFjslIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fjsl is greater than or equal to DEFAULT_FJSL
        defaultCzlCzShouldBeFound("fjsl.greaterThanOrEqual=" + DEFAULT_FJSL);

        // Get all the czlCzList where fjsl is greater than or equal to UPDATED_FJSL
        defaultCzlCzShouldNotBeFound("fjsl.greaterThanOrEqual=" + UPDATED_FJSL);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFjslIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fjsl is less than or equal to DEFAULT_FJSL
        defaultCzlCzShouldBeFound("fjsl.lessThanOrEqual=" + DEFAULT_FJSL);

        // Get all the czlCzList where fjsl is less than or equal to SMALLER_FJSL
        defaultCzlCzShouldNotBeFound("fjsl.lessThanOrEqual=" + SMALLER_FJSL);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFjslIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fjsl is less than DEFAULT_FJSL
        defaultCzlCzShouldNotBeFound("fjsl.lessThan=" + DEFAULT_FJSL);

        // Get all the czlCzList where fjsl is less than UPDATED_FJSL
        defaultCzlCzShouldBeFound("fjsl.lessThan=" + UPDATED_FJSL);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFjslIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fjsl is greater than DEFAULT_FJSL
        defaultCzlCzShouldNotBeFound("fjsl.greaterThan=" + DEFAULT_FJSL);

        // Get all the czlCzList where fjsl is greater than SMALLER_FJSL
        defaultCzlCzShouldBeFound("fjsl.greaterThan=" + SMALLER_FJSL);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfl equals to DEFAULT_KFL
        defaultCzlCzShouldBeFound("kfl.equals=" + DEFAULT_KFL);

        // Get all the czlCzList where kfl equals to UPDATED_KFL
        defaultCzlCzShouldNotBeFound("kfl.equals=" + UPDATED_KFL);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfl not equals to DEFAULT_KFL
        defaultCzlCzShouldNotBeFound("kfl.notEquals=" + DEFAULT_KFL);

        // Get all the czlCzList where kfl not equals to UPDATED_KFL
        defaultCzlCzShouldBeFound("kfl.notEquals=" + UPDATED_KFL);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfl in DEFAULT_KFL or UPDATED_KFL
        defaultCzlCzShouldBeFound("kfl.in=" + DEFAULT_KFL + "," + UPDATED_KFL);

        // Get all the czlCzList where kfl equals to UPDATED_KFL
        defaultCzlCzShouldNotBeFound("kfl.in=" + UPDATED_KFL);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfl is not null
        defaultCzlCzShouldBeFound("kfl.specified=true");

        // Get all the czlCzList where kfl is null
        defaultCzlCzShouldNotBeFound("kfl.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfl is greater than or equal to DEFAULT_KFL
        defaultCzlCzShouldBeFound("kfl.greaterThanOrEqual=" + DEFAULT_KFL);

        // Get all the czlCzList where kfl is greater than or equal to UPDATED_KFL
        defaultCzlCzShouldNotBeFound("kfl.greaterThanOrEqual=" + UPDATED_KFL);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfl is less than or equal to DEFAULT_KFL
        defaultCzlCzShouldBeFound("kfl.lessThanOrEqual=" + DEFAULT_KFL);

        // Get all the czlCzList where kfl is less than or equal to SMALLER_KFL
        defaultCzlCzShouldNotBeFound("kfl.lessThanOrEqual=" + SMALLER_KFL);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfl is less than DEFAULT_KFL
        defaultCzlCzShouldNotBeFound("kfl.lessThan=" + DEFAULT_KFL);

        // Get all the czlCzList where kfl is less than UPDATED_KFL
        defaultCzlCzShouldBeFound("kfl.lessThan=" + UPDATED_KFL);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfl is greater than DEFAULT_KFL
        defaultCzlCzShouldNotBeFound("kfl.greaterThan=" + DEFAULT_KFL);

        // Get all the czlCzList where kfl is greater than SMALLER_KFL
        defaultCzlCzShouldBeFound("kfl.greaterThan=" + SMALLER_KFL);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjz equals to DEFAULT_PJZ
        defaultCzlCzShouldBeFound("pjz.equals=" + DEFAULT_PJZ);

        // Get all the czlCzList where pjz equals to UPDATED_PJZ
        defaultCzlCzShouldNotBeFound("pjz.equals=" + UPDATED_PJZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjz not equals to DEFAULT_PJZ
        defaultCzlCzShouldNotBeFound("pjz.notEquals=" + DEFAULT_PJZ);

        // Get all the czlCzList where pjz not equals to UPDATED_PJZ
        defaultCzlCzShouldBeFound("pjz.notEquals=" + UPDATED_PJZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjz in DEFAULT_PJZ or UPDATED_PJZ
        defaultCzlCzShouldBeFound("pjz.in=" + DEFAULT_PJZ + "," + UPDATED_PJZ);

        // Get all the czlCzList where pjz equals to UPDATED_PJZ
        defaultCzlCzShouldNotBeFound("pjz.in=" + UPDATED_PJZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjz is not null
        defaultCzlCzShouldBeFound("pjz.specified=true");

        // Get all the czlCzList where pjz is null
        defaultCzlCzShouldNotBeFound("pjz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjz is greater than or equal to DEFAULT_PJZ
        defaultCzlCzShouldBeFound("pjz.greaterThanOrEqual=" + DEFAULT_PJZ);

        // Get all the czlCzList where pjz is greater than or equal to UPDATED_PJZ
        defaultCzlCzShouldNotBeFound("pjz.greaterThanOrEqual=" + UPDATED_PJZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjz is less than or equal to DEFAULT_PJZ
        defaultCzlCzShouldBeFound("pjz.lessThanOrEqual=" + DEFAULT_PJZ);

        // Get all the czlCzList where pjz is less than or equal to SMALLER_PJZ
        defaultCzlCzShouldNotBeFound("pjz.lessThanOrEqual=" + SMALLER_PJZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjz is less than DEFAULT_PJZ
        defaultCzlCzShouldNotBeFound("pjz.lessThan=" + DEFAULT_PJZ);

        // Get all the czlCzList where pjz is less than UPDATED_PJZ
        defaultCzlCzShouldBeFound("pjz.lessThan=" + UPDATED_PJZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjz is greater than DEFAULT_PJZ
        defaultCzlCzShouldNotBeFound("pjz.greaterThan=" + DEFAULT_PJZ);

        // Get all the czlCzList where pjz is greater than SMALLER_PJZ
        defaultCzlCzShouldBeFound("pjz.greaterThan=" + SMALLER_PJZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByYsfzIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where ysfz equals to DEFAULT_YSFZ
        defaultCzlCzShouldBeFound("ysfz.equals=" + DEFAULT_YSFZ);

        // Get all the czlCzList where ysfz equals to UPDATED_YSFZ
        defaultCzlCzShouldNotBeFound("ysfz.equals=" + UPDATED_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByYsfzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where ysfz not equals to DEFAULT_YSFZ
        defaultCzlCzShouldNotBeFound("ysfz.notEquals=" + DEFAULT_YSFZ);

        // Get all the czlCzList where ysfz not equals to UPDATED_YSFZ
        defaultCzlCzShouldBeFound("ysfz.notEquals=" + UPDATED_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByYsfzIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where ysfz in DEFAULT_YSFZ or UPDATED_YSFZ
        defaultCzlCzShouldBeFound("ysfz.in=" + DEFAULT_YSFZ + "," + UPDATED_YSFZ);

        // Get all the czlCzList where ysfz equals to UPDATED_YSFZ
        defaultCzlCzShouldNotBeFound("ysfz.in=" + UPDATED_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByYsfzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where ysfz is not null
        defaultCzlCzShouldBeFound("ysfz.specified=true");

        // Get all the czlCzList where ysfz is null
        defaultCzlCzShouldNotBeFound("ysfz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByYsfzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where ysfz is greater than or equal to DEFAULT_YSFZ
        defaultCzlCzShouldBeFound("ysfz.greaterThanOrEqual=" + DEFAULT_YSFZ);

        // Get all the czlCzList where ysfz is greater than or equal to UPDATED_YSFZ
        defaultCzlCzShouldNotBeFound("ysfz.greaterThanOrEqual=" + UPDATED_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByYsfzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where ysfz is less than or equal to DEFAULT_YSFZ
        defaultCzlCzShouldBeFound("ysfz.lessThanOrEqual=" + DEFAULT_YSFZ);

        // Get all the czlCzList where ysfz is less than or equal to SMALLER_YSFZ
        defaultCzlCzShouldNotBeFound("ysfz.lessThanOrEqual=" + SMALLER_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByYsfzIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where ysfz is less than DEFAULT_YSFZ
        defaultCzlCzShouldNotBeFound("ysfz.lessThan=" + DEFAULT_YSFZ);

        // Get all the czlCzList where ysfz is less than UPDATED_YSFZ
        defaultCzlCzShouldBeFound("ysfz.lessThan=" + UPDATED_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByYsfzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where ysfz is greater than DEFAULT_YSFZ
        defaultCzlCzShouldNotBeFound("ysfz.greaterThan=" + DEFAULT_YSFZ);

        // Get all the czlCzList where ysfz is greater than SMALLER_YSFZ
        defaultCzlCzShouldBeFound("ysfz.greaterThan=" + SMALLER_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsBySjfzIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where sjfz equals to DEFAULT_SJFZ
        defaultCzlCzShouldBeFound("sjfz.equals=" + DEFAULT_SJFZ);

        // Get all the czlCzList where sjfz equals to UPDATED_SJFZ
        defaultCzlCzShouldNotBeFound("sjfz.equals=" + UPDATED_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsBySjfzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where sjfz not equals to DEFAULT_SJFZ
        defaultCzlCzShouldNotBeFound("sjfz.notEquals=" + DEFAULT_SJFZ);

        // Get all the czlCzList where sjfz not equals to UPDATED_SJFZ
        defaultCzlCzShouldBeFound("sjfz.notEquals=" + UPDATED_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsBySjfzIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where sjfz in DEFAULT_SJFZ or UPDATED_SJFZ
        defaultCzlCzShouldBeFound("sjfz.in=" + DEFAULT_SJFZ + "," + UPDATED_SJFZ);

        // Get all the czlCzList where sjfz equals to UPDATED_SJFZ
        defaultCzlCzShouldNotBeFound("sjfz.in=" + UPDATED_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsBySjfzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where sjfz is not null
        defaultCzlCzShouldBeFound("sjfz.specified=true");

        // Get all the czlCzList where sjfz is null
        defaultCzlCzShouldNotBeFound("sjfz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsBySjfzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where sjfz is greater than or equal to DEFAULT_SJFZ
        defaultCzlCzShouldBeFound("sjfz.greaterThanOrEqual=" + DEFAULT_SJFZ);

        // Get all the czlCzList where sjfz is greater than or equal to UPDATED_SJFZ
        defaultCzlCzShouldNotBeFound("sjfz.greaterThanOrEqual=" + UPDATED_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsBySjfzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where sjfz is less than or equal to DEFAULT_SJFZ
        defaultCzlCzShouldBeFound("sjfz.lessThanOrEqual=" + DEFAULT_SJFZ);

        // Get all the czlCzList where sjfz is less than or equal to SMALLER_SJFZ
        defaultCzlCzShouldNotBeFound("sjfz.lessThanOrEqual=" + SMALLER_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsBySjfzIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where sjfz is less than DEFAULT_SJFZ
        defaultCzlCzShouldNotBeFound("sjfz.lessThan=" + DEFAULT_SJFZ);

        // Get all the czlCzList where sjfz is less than UPDATED_SJFZ
        defaultCzlCzShouldBeFound("sjfz.lessThan=" + UPDATED_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsBySjfzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where sjfz is greater than DEFAULT_SJFZ
        defaultCzlCzShouldNotBeFound("sjfz.greaterThan=" + DEFAULT_SJFZ);

        // Get all the czlCzList where sjfz is greater than SMALLER_SJFZ
        defaultCzlCzShouldBeFound("sjfz.greaterThan=" + SMALLER_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzczIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzcz equals to DEFAULT_FZCZ
        defaultCzlCzShouldBeFound("fzcz.equals=" + DEFAULT_FZCZ);

        // Get all the czlCzList where fzcz equals to UPDATED_FZCZ
        defaultCzlCzShouldNotBeFound("fzcz.equals=" + UPDATED_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzczIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzcz not equals to DEFAULT_FZCZ
        defaultCzlCzShouldNotBeFound("fzcz.notEquals=" + DEFAULT_FZCZ);

        // Get all the czlCzList where fzcz not equals to UPDATED_FZCZ
        defaultCzlCzShouldBeFound("fzcz.notEquals=" + UPDATED_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzczIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzcz in DEFAULT_FZCZ or UPDATED_FZCZ
        defaultCzlCzShouldBeFound("fzcz.in=" + DEFAULT_FZCZ + "," + UPDATED_FZCZ);

        // Get all the czlCzList where fzcz equals to UPDATED_FZCZ
        defaultCzlCzShouldNotBeFound("fzcz.in=" + UPDATED_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzczIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzcz is not null
        defaultCzlCzShouldBeFound("fzcz.specified=true");

        // Get all the czlCzList where fzcz is null
        defaultCzlCzShouldNotBeFound("fzcz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzczIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzcz is greater than or equal to DEFAULT_FZCZ
        defaultCzlCzShouldBeFound("fzcz.greaterThanOrEqual=" + DEFAULT_FZCZ);

        // Get all the czlCzList where fzcz is greater than or equal to UPDATED_FZCZ
        defaultCzlCzShouldNotBeFound("fzcz.greaterThanOrEqual=" + UPDATED_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzczIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzcz is less than or equal to DEFAULT_FZCZ
        defaultCzlCzShouldBeFound("fzcz.lessThanOrEqual=" + DEFAULT_FZCZ);

        // Get all the czlCzList where fzcz is less than or equal to SMALLER_FZCZ
        defaultCzlCzShouldNotBeFound("fzcz.lessThanOrEqual=" + SMALLER_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzczIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzcz is less than DEFAULT_FZCZ
        defaultCzlCzShouldNotBeFound("fzcz.lessThan=" + DEFAULT_FZCZ);

        // Get all the czlCzList where fzcz is less than UPDATED_FZCZ
        defaultCzlCzShouldBeFound("fzcz.lessThan=" + UPDATED_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzczIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzcz is greater than DEFAULT_FZCZ
        defaultCzlCzShouldNotBeFound("fzcz.greaterThan=" + DEFAULT_FZCZ);

        // Get all the czlCzList where fzcz is greater than SMALLER_FZCZ
        defaultCzlCzShouldBeFound("fzcz.greaterThan=" + SMALLER_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzcjIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzcj equals to DEFAULT_PJZCJ
        defaultCzlCzShouldBeFound("pjzcj.equals=" + DEFAULT_PJZCJ);

        // Get all the czlCzList where pjzcj equals to UPDATED_PJZCJ
        defaultCzlCzShouldNotBeFound("pjzcj.equals=" + UPDATED_PJZCJ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzcjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzcj not equals to DEFAULT_PJZCJ
        defaultCzlCzShouldNotBeFound("pjzcj.notEquals=" + DEFAULT_PJZCJ);

        // Get all the czlCzList where pjzcj not equals to UPDATED_PJZCJ
        defaultCzlCzShouldBeFound("pjzcj.notEquals=" + UPDATED_PJZCJ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzcjIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzcj in DEFAULT_PJZCJ or UPDATED_PJZCJ
        defaultCzlCzShouldBeFound("pjzcj.in=" + DEFAULT_PJZCJ + "," + UPDATED_PJZCJ);

        // Get all the czlCzList where pjzcj equals to UPDATED_PJZCJ
        defaultCzlCzShouldNotBeFound("pjzcj.in=" + UPDATED_PJZCJ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzcjIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzcj is not null
        defaultCzlCzShouldBeFound("pjzcj.specified=true");

        // Get all the czlCzList where pjzcj is null
        defaultCzlCzShouldNotBeFound("pjzcj.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzcjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzcj is greater than or equal to DEFAULT_PJZCJ
        defaultCzlCzShouldBeFound("pjzcj.greaterThanOrEqual=" + DEFAULT_PJZCJ);

        // Get all the czlCzList where pjzcj is greater than or equal to UPDATED_PJZCJ
        defaultCzlCzShouldNotBeFound("pjzcj.greaterThanOrEqual=" + UPDATED_PJZCJ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzcjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzcj is less than or equal to DEFAULT_PJZCJ
        defaultCzlCzShouldBeFound("pjzcj.lessThanOrEqual=" + DEFAULT_PJZCJ);

        // Get all the czlCzList where pjzcj is less than or equal to SMALLER_PJZCJ
        defaultCzlCzShouldNotBeFound("pjzcj.lessThanOrEqual=" + SMALLER_PJZCJ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzcjIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzcj is less than DEFAULT_PJZCJ
        defaultCzlCzShouldNotBeFound("pjzcj.lessThan=" + DEFAULT_PJZCJ);

        // Get all the czlCzList where pjzcj is less than UPDATED_PJZCJ
        defaultCzlCzShouldBeFound("pjzcj.lessThan=" + UPDATED_PJZCJ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzcjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzcj is greater than DEFAULT_PJZCJ
        defaultCzlCzShouldNotBeFound("pjzcj.greaterThan=" + DEFAULT_PJZCJ);

        // Get all the czlCzList where pjzcj is greater than SMALLER_PJZCJ
        defaultCzlCzShouldBeFound("pjzcj.greaterThan=" + SMALLER_PJZCJ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKfsMIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfsM equals to DEFAULT_KFS_M
        defaultCzlCzShouldBeFound("kfsM.equals=" + DEFAULT_KFS_M);

        // Get all the czlCzList where kfsM equals to UPDATED_KFS_M
        defaultCzlCzShouldNotBeFound("kfsM.equals=" + UPDATED_KFS_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKfsMIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfsM not equals to DEFAULT_KFS_M
        defaultCzlCzShouldNotBeFound("kfsM.notEquals=" + DEFAULT_KFS_M);

        // Get all the czlCzList where kfsM not equals to UPDATED_KFS_M
        defaultCzlCzShouldBeFound("kfsM.notEquals=" + UPDATED_KFS_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKfsMIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfsM in DEFAULT_KFS_M or UPDATED_KFS_M
        defaultCzlCzShouldBeFound("kfsM.in=" + DEFAULT_KFS_M + "," + UPDATED_KFS_M);

        // Get all the czlCzList where kfsM equals to UPDATED_KFS_M
        defaultCzlCzShouldNotBeFound("kfsM.in=" + UPDATED_KFS_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKfsMIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfsM is not null
        defaultCzlCzShouldBeFound("kfsM.specified=true");

        // Get all the czlCzList where kfsM is null
        defaultCzlCzShouldNotBeFound("kfsM.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByKfsMIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfsM is greater than or equal to DEFAULT_KFS_M
        defaultCzlCzShouldBeFound("kfsM.greaterThanOrEqual=" + DEFAULT_KFS_M);

        // Get all the czlCzList where kfsM is greater than or equal to UPDATED_KFS_M
        defaultCzlCzShouldNotBeFound("kfsM.greaterThanOrEqual=" + UPDATED_KFS_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKfsMIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfsM is less than or equal to DEFAULT_KFS_M
        defaultCzlCzShouldBeFound("kfsM.lessThanOrEqual=" + DEFAULT_KFS_M);

        // Get all the czlCzList where kfsM is less than or equal to SMALLER_KFS_M
        defaultCzlCzShouldNotBeFound("kfsM.lessThanOrEqual=" + SMALLER_KFS_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKfsMIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfsM is less than DEFAULT_KFS_M
        defaultCzlCzShouldNotBeFound("kfsM.lessThan=" + DEFAULT_KFS_M);

        // Get all the czlCzList where kfsM is less than UPDATED_KFS_M
        defaultCzlCzShouldBeFound("kfsM.lessThan=" + UPDATED_KFS_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKfsMIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kfsM is greater than DEFAULT_KFS_M
        defaultCzlCzShouldNotBeFound("kfsM.greaterThan=" + DEFAULT_KFS_M);

        // Get all the czlCzList where kfsM is greater than SMALLER_KFS_M
        defaultCzlCzShouldBeFound("kfsM.greaterThan=" + SMALLER_KFS_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflMIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kflM equals to DEFAULT_KFL_M
        defaultCzlCzShouldBeFound("kflM.equals=" + DEFAULT_KFL_M);

        // Get all the czlCzList where kflM equals to UPDATED_KFL_M
        defaultCzlCzShouldNotBeFound("kflM.equals=" + UPDATED_KFL_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflMIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kflM not equals to DEFAULT_KFL_M
        defaultCzlCzShouldNotBeFound("kflM.notEquals=" + DEFAULT_KFL_M);

        // Get all the czlCzList where kflM not equals to UPDATED_KFL_M
        defaultCzlCzShouldBeFound("kflM.notEquals=" + UPDATED_KFL_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflMIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kflM in DEFAULT_KFL_M or UPDATED_KFL_M
        defaultCzlCzShouldBeFound("kflM.in=" + DEFAULT_KFL_M + "," + UPDATED_KFL_M);

        // Get all the czlCzList where kflM equals to UPDATED_KFL_M
        defaultCzlCzShouldNotBeFound("kflM.in=" + UPDATED_KFL_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflMIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kflM is not null
        defaultCzlCzShouldBeFound("kflM.specified=true");

        // Get all the czlCzList where kflM is null
        defaultCzlCzShouldNotBeFound("kflM.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflMIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kflM is greater than or equal to DEFAULT_KFL_M
        defaultCzlCzShouldBeFound("kflM.greaterThanOrEqual=" + DEFAULT_KFL_M);

        // Get all the czlCzList where kflM is greater than or equal to UPDATED_KFL_M
        defaultCzlCzShouldNotBeFound("kflM.greaterThanOrEqual=" + UPDATED_KFL_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflMIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kflM is less than or equal to DEFAULT_KFL_M
        defaultCzlCzShouldBeFound("kflM.lessThanOrEqual=" + DEFAULT_KFL_M);

        // Get all the czlCzList where kflM is less than or equal to SMALLER_KFL_M
        defaultCzlCzShouldNotBeFound("kflM.lessThanOrEqual=" + SMALLER_KFL_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflMIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kflM is less than DEFAULT_KFL_M
        defaultCzlCzShouldNotBeFound("kflM.lessThan=" + DEFAULT_KFL_M);

        // Get all the czlCzList where kflM is less than UPDATED_KFL_M
        defaultCzlCzShouldBeFound("kflM.lessThan=" + UPDATED_KFL_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByKflMIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where kflM is greater than DEFAULT_KFL_M
        defaultCzlCzShouldNotBeFound("kflM.greaterThan=" + DEFAULT_KFL_M);

        // Get all the czlCzList where kflM is greater than SMALLER_KFL_M
        defaultCzlCzShouldBeFound("kflM.greaterThan=" + SMALLER_KFL_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzMIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzM equals to DEFAULT_PJZ_M
        defaultCzlCzShouldBeFound("pjzM.equals=" + DEFAULT_PJZ_M);

        // Get all the czlCzList where pjzM equals to UPDATED_PJZ_M
        defaultCzlCzShouldNotBeFound("pjzM.equals=" + UPDATED_PJZ_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzMIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzM not equals to DEFAULT_PJZ_M
        defaultCzlCzShouldNotBeFound("pjzM.notEquals=" + DEFAULT_PJZ_M);

        // Get all the czlCzList where pjzM not equals to UPDATED_PJZ_M
        defaultCzlCzShouldBeFound("pjzM.notEquals=" + UPDATED_PJZ_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzMIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzM in DEFAULT_PJZ_M or UPDATED_PJZ_M
        defaultCzlCzShouldBeFound("pjzM.in=" + DEFAULT_PJZ_M + "," + UPDATED_PJZ_M);

        // Get all the czlCzList where pjzM equals to UPDATED_PJZ_M
        defaultCzlCzShouldNotBeFound("pjzM.in=" + UPDATED_PJZ_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzMIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzM is not null
        defaultCzlCzShouldBeFound("pjzM.specified=true");

        // Get all the czlCzList where pjzM is null
        defaultCzlCzShouldNotBeFound("pjzM.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzMIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzM is greater than or equal to DEFAULT_PJZ_M
        defaultCzlCzShouldBeFound("pjzM.greaterThanOrEqual=" + DEFAULT_PJZ_M);

        // Get all the czlCzList where pjzM is greater than or equal to UPDATED_PJZ_M
        defaultCzlCzShouldNotBeFound("pjzM.greaterThanOrEqual=" + UPDATED_PJZ_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzMIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzM is less than or equal to DEFAULT_PJZ_M
        defaultCzlCzShouldBeFound("pjzM.lessThanOrEqual=" + DEFAULT_PJZ_M);

        // Get all the czlCzList where pjzM is less than or equal to SMALLER_PJZ_M
        defaultCzlCzShouldNotBeFound("pjzM.lessThanOrEqual=" + SMALLER_PJZ_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzMIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzM is less than DEFAULT_PJZ_M
        defaultCzlCzShouldNotBeFound("pjzM.lessThan=" + DEFAULT_PJZ_M);

        // Get all the czlCzList where pjzM is less than UPDATED_PJZ_M
        defaultCzlCzShouldBeFound("pjzM.lessThan=" + UPDATED_PJZ_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByPjzMIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where pjzM is greater than DEFAULT_PJZ_M
        defaultCzlCzShouldNotBeFound("pjzM.greaterThan=" + DEFAULT_PJZ_M);

        // Get all the czlCzList where pjzM is greater than SMALLER_PJZ_M
        defaultCzlCzShouldBeFound("pjzM.greaterThan=" + SMALLER_PJZ_M);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzsrIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzsr equals to DEFAULT_FZSR
        defaultCzlCzShouldBeFound("fzsr.equals=" + DEFAULT_FZSR);

        // Get all the czlCzList where fzsr equals to UPDATED_FZSR
        defaultCzlCzShouldNotBeFound("fzsr.equals=" + UPDATED_FZSR);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzsrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzsr not equals to DEFAULT_FZSR
        defaultCzlCzShouldNotBeFound("fzsr.notEquals=" + DEFAULT_FZSR);

        // Get all the czlCzList where fzsr not equals to UPDATED_FZSR
        defaultCzlCzShouldBeFound("fzsr.notEquals=" + UPDATED_FZSR);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzsrIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzsr in DEFAULT_FZSR or UPDATED_FZSR
        defaultCzlCzShouldBeFound("fzsr.in=" + DEFAULT_FZSR + "," + UPDATED_FZSR);

        // Get all the czlCzList where fzsr equals to UPDATED_FZSR
        defaultCzlCzShouldNotBeFound("fzsr.in=" + UPDATED_FZSR);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzsrIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzsr is not null
        defaultCzlCzShouldBeFound("fzsr.specified=true");

        // Get all the czlCzList where fzsr is null
        defaultCzlCzShouldNotBeFound("fzsr.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzsrIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzsr is greater than or equal to DEFAULT_FZSR
        defaultCzlCzShouldBeFound("fzsr.greaterThanOrEqual=" + DEFAULT_FZSR);

        // Get all the czlCzList where fzsr is greater than or equal to UPDATED_FZSR
        defaultCzlCzShouldNotBeFound("fzsr.greaterThanOrEqual=" + UPDATED_FZSR);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzsrIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzsr is less than or equal to DEFAULT_FZSR
        defaultCzlCzShouldBeFound("fzsr.lessThanOrEqual=" + DEFAULT_FZSR);

        // Get all the czlCzList where fzsr is less than or equal to SMALLER_FZSR
        defaultCzlCzShouldNotBeFound("fzsr.lessThanOrEqual=" + SMALLER_FZSR);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzsrIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzsr is less than DEFAULT_FZSR
        defaultCzlCzShouldNotBeFound("fzsr.lessThan=" + DEFAULT_FZSR);

        // Get all the czlCzList where fzsr is less than UPDATED_FZSR
        defaultCzlCzShouldBeFound("fzsr.lessThan=" + UPDATED_FZSR);
    }

    @Test
    @Transactional
    void getAllCzlCzsByFzsrIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where fzsr is greater than DEFAULT_FZSR
        defaultCzlCzShouldNotBeFound("fzsr.greaterThan=" + DEFAULT_FZSR);

        // Get all the czlCzList where fzsr is greater than SMALLER_FZSR
        defaultCzlCzShouldBeFound("fzsr.greaterThan=" + SMALLER_FZSR);
    }

    @Test
    @Transactional
    void getAllCzlCzsByDayzIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where dayz equals to DEFAULT_DAYZ
        defaultCzlCzShouldBeFound("dayz.equals=" + DEFAULT_DAYZ);

        // Get all the czlCzList where dayz equals to UPDATED_DAYZ
        defaultCzlCzShouldNotBeFound("dayz.equals=" + UPDATED_DAYZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByDayzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where dayz not equals to DEFAULT_DAYZ
        defaultCzlCzShouldNotBeFound("dayz.notEquals=" + DEFAULT_DAYZ);

        // Get all the czlCzList where dayz not equals to UPDATED_DAYZ
        defaultCzlCzShouldBeFound("dayz.notEquals=" + UPDATED_DAYZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByDayzIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where dayz in DEFAULT_DAYZ or UPDATED_DAYZ
        defaultCzlCzShouldBeFound("dayz.in=" + DEFAULT_DAYZ + "," + UPDATED_DAYZ);

        // Get all the czlCzList where dayz equals to UPDATED_DAYZ
        defaultCzlCzShouldNotBeFound("dayz.in=" + UPDATED_DAYZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByDayzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where dayz is not null
        defaultCzlCzShouldBeFound("dayz.specified=true");

        // Get all the czlCzList where dayz is null
        defaultCzlCzShouldNotBeFound("dayz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByDayzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where dayz is greater than or equal to DEFAULT_DAYZ
        defaultCzlCzShouldBeFound("dayz.greaterThanOrEqual=" + DEFAULT_DAYZ);

        // Get all the czlCzList where dayz is greater than or equal to UPDATED_DAYZ
        defaultCzlCzShouldNotBeFound("dayz.greaterThanOrEqual=" + UPDATED_DAYZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByDayzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where dayz is less than or equal to DEFAULT_DAYZ
        defaultCzlCzShouldBeFound("dayz.lessThanOrEqual=" + DEFAULT_DAYZ);

        // Get all the czlCzList where dayz is less than or equal to SMALLER_DAYZ
        defaultCzlCzShouldNotBeFound("dayz.lessThanOrEqual=" + SMALLER_DAYZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByDayzIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where dayz is less than DEFAULT_DAYZ
        defaultCzlCzShouldNotBeFound("dayz.lessThan=" + DEFAULT_DAYZ);

        // Get all the czlCzList where dayz is less than UPDATED_DAYZ
        defaultCzlCzShouldBeFound("dayz.lessThan=" + UPDATED_DAYZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByDayzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where dayz is greater than DEFAULT_DAYZ
        defaultCzlCzShouldNotBeFound("dayz.greaterThan=" + DEFAULT_DAYZ);

        // Get all the czlCzList where dayz is greater than SMALLER_DAYZ
        defaultCzlCzShouldBeFound("dayz.greaterThan=" + SMALLER_DAYZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByHoteltimeIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where hoteltime equals to DEFAULT_HOTELTIME
        defaultCzlCzShouldBeFound("hoteltime.equals=" + DEFAULT_HOTELTIME);

        // Get all the czlCzList where hoteltime equals to UPDATED_HOTELTIME
        defaultCzlCzShouldNotBeFound("hoteltime.equals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCzlCzsByHoteltimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where hoteltime not equals to DEFAULT_HOTELTIME
        defaultCzlCzShouldNotBeFound("hoteltime.notEquals=" + DEFAULT_HOTELTIME);

        // Get all the czlCzList where hoteltime not equals to UPDATED_HOTELTIME
        defaultCzlCzShouldBeFound("hoteltime.notEquals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCzlCzsByHoteltimeIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where hoteltime in DEFAULT_HOTELTIME or UPDATED_HOTELTIME
        defaultCzlCzShouldBeFound("hoteltime.in=" + DEFAULT_HOTELTIME + "," + UPDATED_HOTELTIME);

        // Get all the czlCzList where hoteltime equals to UPDATED_HOTELTIME
        defaultCzlCzShouldNotBeFound("hoteltime.in=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCzlCzsByHoteltimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where hoteltime is not null
        defaultCzlCzShouldBeFound("hoteltime.specified=true");

        // Get all the czlCzList where hoteltime is null
        defaultCzlCzShouldNotBeFound("hoteltime.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where empn equals to DEFAULT_EMPN
        defaultCzlCzShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the czlCzList where empn equals to UPDATED_EMPN
        defaultCzlCzShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCzlCzsByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where empn not equals to DEFAULT_EMPN
        defaultCzlCzShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the czlCzList where empn not equals to UPDATED_EMPN
        defaultCzlCzShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCzlCzsByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultCzlCzShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the czlCzList where empn equals to UPDATED_EMPN
        defaultCzlCzShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCzlCzsByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where empn is not null
        defaultCzlCzShouldBeFound("empn.specified=true");

        // Get all the czlCzList where empn is null
        defaultCzlCzShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByEmpnContainsSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where empn contains DEFAULT_EMPN
        defaultCzlCzShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the czlCzList where empn contains UPDATED_EMPN
        defaultCzlCzShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCzlCzsByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where empn does not contain DEFAULT_EMPN
        defaultCzlCzShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the czlCzList where empn does not contain UPDATED_EMPN
        defaultCzlCzShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCzlCzsByMonthzIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where monthz equals to DEFAULT_MONTHZ
        defaultCzlCzShouldBeFound("monthz.equals=" + DEFAULT_MONTHZ);

        // Get all the czlCzList where monthz equals to UPDATED_MONTHZ
        defaultCzlCzShouldNotBeFound("monthz.equals=" + UPDATED_MONTHZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByMonthzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where monthz not equals to DEFAULT_MONTHZ
        defaultCzlCzShouldNotBeFound("monthz.notEquals=" + DEFAULT_MONTHZ);

        // Get all the czlCzList where monthz not equals to UPDATED_MONTHZ
        defaultCzlCzShouldBeFound("monthz.notEquals=" + UPDATED_MONTHZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByMonthzIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where monthz in DEFAULT_MONTHZ or UPDATED_MONTHZ
        defaultCzlCzShouldBeFound("monthz.in=" + DEFAULT_MONTHZ + "," + UPDATED_MONTHZ);

        // Get all the czlCzList where monthz equals to UPDATED_MONTHZ
        defaultCzlCzShouldNotBeFound("monthz.in=" + UPDATED_MONTHZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByMonthzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where monthz is not null
        defaultCzlCzShouldBeFound("monthz.specified=true");

        // Get all the czlCzList where monthz is null
        defaultCzlCzShouldNotBeFound("monthz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByMonthzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where monthz is greater than or equal to DEFAULT_MONTHZ
        defaultCzlCzShouldBeFound("monthz.greaterThanOrEqual=" + DEFAULT_MONTHZ);

        // Get all the czlCzList where monthz is greater than or equal to UPDATED_MONTHZ
        defaultCzlCzShouldNotBeFound("monthz.greaterThanOrEqual=" + UPDATED_MONTHZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByMonthzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where monthz is less than or equal to DEFAULT_MONTHZ
        defaultCzlCzShouldBeFound("monthz.lessThanOrEqual=" + DEFAULT_MONTHZ);

        // Get all the czlCzList where monthz is less than or equal to SMALLER_MONTHZ
        defaultCzlCzShouldNotBeFound("monthz.lessThanOrEqual=" + SMALLER_MONTHZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByMonthzIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where monthz is less than DEFAULT_MONTHZ
        defaultCzlCzShouldNotBeFound("monthz.lessThan=" + DEFAULT_MONTHZ);

        // Get all the czlCzList where monthz is less than UPDATED_MONTHZ
        defaultCzlCzShouldBeFound("monthz.lessThan=" + UPDATED_MONTHZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByMonthzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where monthz is greater than DEFAULT_MONTHZ
        defaultCzlCzShouldNotBeFound("monthz.greaterThan=" + DEFAULT_MONTHZ);

        // Get all the czlCzList where monthz is greater than SMALLER_MONTHZ
        defaultCzlCzShouldBeFound("monthz.greaterThan=" + SMALLER_MONTHZ);
    }

    @Test
    @Transactional
    void getAllCzlCzsByHoteldmIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where hoteldm equals to DEFAULT_HOTELDM
        defaultCzlCzShouldBeFound("hoteldm.equals=" + DEFAULT_HOTELDM);

        // Get all the czlCzList where hoteldm equals to UPDATED_HOTELDM
        defaultCzlCzShouldNotBeFound("hoteldm.equals=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllCzlCzsByHoteldmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where hoteldm not equals to DEFAULT_HOTELDM
        defaultCzlCzShouldNotBeFound("hoteldm.notEquals=" + DEFAULT_HOTELDM);

        // Get all the czlCzList where hoteldm not equals to UPDATED_HOTELDM
        defaultCzlCzShouldBeFound("hoteldm.notEquals=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllCzlCzsByHoteldmIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where hoteldm in DEFAULT_HOTELDM or UPDATED_HOTELDM
        defaultCzlCzShouldBeFound("hoteldm.in=" + DEFAULT_HOTELDM + "," + UPDATED_HOTELDM);

        // Get all the czlCzList where hoteldm equals to UPDATED_HOTELDM
        defaultCzlCzShouldNotBeFound("hoteldm.in=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllCzlCzsByHoteldmIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where hoteldm is not null
        defaultCzlCzShouldBeFound("hoteldm.specified=true");

        // Get all the czlCzList where hoteldm is null
        defaultCzlCzShouldNotBeFound("hoteldm.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByHoteldmContainsSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where hoteldm contains DEFAULT_HOTELDM
        defaultCzlCzShouldBeFound("hoteldm.contains=" + DEFAULT_HOTELDM);

        // Get all the czlCzList where hoteldm contains UPDATED_HOTELDM
        defaultCzlCzShouldNotBeFound("hoteldm.contains=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllCzlCzsByHoteldmNotContainsSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where hoteldm does not contain DEFAULT_HOTELDM
        defaultCzlCzShouldNotBeFound("hoteldm.doesNotContain=" + DEFAULT_HOTELDM);

        // Get all the czlCzList where hoteldm does not contain UPDATED_HOTELDM
        defaultCzlCzShouldBeFound("hoteldm.doesNotContain=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllCzlCzsByIsnewIsEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where isnew equals to DEFAULT_ISNEW
        defaultCzlCzShouldBeFound("isnew.equals=" + DEFAULT_ISNEW);

        // Get all the czlCzList where isnew equals to UPDATED_ISNEW
        defaultCzlCzShouldNotBeFound("isnew.equals=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllCzlCzsByIsnewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where isnew not equals to DEFAULT_ISNEW
        defaultCzlCzShouldNotBeFound("isnew.notEquals=" + DEFAULT_ISNEW);

        // Get all the czlCzList where isnew not equals to UPDATED_ISNEW
        defaultCzlCzShouldBeFound("isnew.notEquals=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllCzlCzsByIsnewIsInShouldWork() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where isnew in DEFAULT_ISNEW or UPDATED_ISNEW
        defaultCzlCzShouldBeFound("isnew.in=" + DEFAULT_ISNEW + "," + UPDATED_ISNEW);

        // Get all the czlCzList where isnew equals to UPDATED_ISNEW
        defaultCzlCzShouldNotBeFound("isnew.in=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllCzlCzsByIsnewIsNullOrNotNull() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where isnew is not null
        defaultCzlCzShouldBeFound("isnew.specified=true");

        // Get all the czlCzList where isnew is null
        defaultCzlCzShouldNotBeFound("isnew.specified=false");
    }

    @Test
    @Transactional
    void getAllCzlCzsByIsnewIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where isnew is greater than or equal to DEFAULT_ISNEW
        defaultCzlCzShouldBeFound("isnew.greaterThanOrEqual=" + DEFAULT_ISNEW);

        // Get all the czlCzList where isnew is greater than or equal to UPDATED_ISNEW
        defaultCzlCzShouldNotBeFound("isnew.greaterThanOrEqual=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllCzlCzsByIsnewIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where isnew is less than or equal to DEFAULT_ISNEW
        defaultCzlCzShouldBeFound("isnew.lessThanOrEqual=" + DEFAULT_ISNEW);

        // Get all the czlCzList where isnew is less than or equal to SMALLER_ISNEW
        defaultCzlCzShouldNotBeFound("isnew.lessThanOrEqual=" + SMALLER_ISNEW);
    }

    @Test
    @Transactional
    void getAllCzlCzsByIsnewIsLessThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where isnew is less than DEFAULT_ISNEW
        defaultCzlCzShouldNotBeFound("isnew.lessThan=" + DEFAULT_ISNEW);

        // Get all the czlCzList where isnew is less than UPDATED_ISNEW
        defaultCzlCzShouldBeFound("isnew.lessThan=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllCzlCzsByIsnewIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        // Get all the czlCzList where isnew is greater than DEFAULT_ISNEW
        defaultCzlCzShouldNotBeFound("isnew.greaterThan=" + DEFAULT_ISNEW);

        // Get all the czlCzList where isnew is greater than SMALLER_ISNEW
        defaultCzlCzShouldBeFound("isnew.greaterThan=" + SMALLER_ISNEW);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCzlCzShouldBeFound(String filter) throws Exception {
        restCzlCzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(czlCz.getId().intValue())))
            .andExpect(jsonPath("$.[*].tjrq").value(hasItem(DEFAULT_TJRQ.toString())))
            .andExpect(jsonPath("$.[*].typeid").value(hasItem(DEFAULT_TYPEID.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].fjsl").value(hasItem(DEFAULT_FJSL.intValue())))
            .andExpect(jsonPath("$.[*].kfl").value(hasItem(sameNumber(DEFAULT_KFL))))
            .andExpect(jsonPath("$.[*].pjz").value(hasItem(sameNumber(DEFAULT_PJZ))))
            .andExpect(jsonPath("$.[*].ysfz").value(hasItem(sameNumber(DEFAULT_YSFZ))))
            .andExpect(jsonPath("$.[*].sjfz").value(hasItem(sameNumber(DEFAULT_SJFZ))))
            .andExpect(jsonPath("$.[*].fzcz").value(hasItem(sameNumber(DEFAULT_FZCZ))))
            .andExpect(jsonPath("$.[*].pjzcj").value(hasItem(sameNumber(DEFAULT_PJZCJ))))
            .andExpect(jsonPath("$.[*].kfsM").value(hasItem(sameNumber(DEFAULT_KFS_M))))
            .andExpect(jsonPath("$.[*].kflM").value(hasItem(sameNumber(DEFAULT_KFL_M))))
            .andExpect(jsonPath("$.[*].pjzM").value(hasItem(sameNumber(DEFAULT_PJZ_M))))
            .andExpect(jsonPath("$.[*].fzsr").value(hasItem(sameNumber(DEFAULT_FZSR))))
            .andExpect(jsonPath("$.[*].dayz").value(hasItem(sameNumber(DEFAULT_DAYZ))))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].monthz").value(hasItem(sameNumber(DEFAULT_MONTHZ))))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())));

        // Check, that the count call also returns 1
        restCzlCzMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCzlCzShouldNotBeFound(String filter) throws Exception {
        restCzlCzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCzlCzMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCzlCz() throws Exception {
        // Get the czlCz
        restCzlCzMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCzlCz() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        int databaseSizeBeforeUpdate = czlCzRepository.findAll().size();

        // Update the czlCz
        CzlCz updatedCzlCz = czlCzRepository.findById(czlCz.getId()).get();
        // Disconnect from session so that the updates on updatedCzlCz are not directly saved in db
        em.detach(updatedCzlCz);
        updatedCzlCz
            .tjrq(UPDATED_TJRQ)
            .typeid(UPDATED_TYPEID)
            .type(UPDATED_TYPE)
            .fjsl(UPDATED_FJSL)
            .kfl(UPDATED_KFL)
            .pjz(UPDATED_PJZ)
            .ysfz(UPDATED_YSFZ)
            .sjfz(UPDATED_SJFZ)
            .fzcz(UPDATED_FZCZ)
            .pjzcj(UPDATED_PJZCJ)
            .kfsM(UPDATED_KFS_M)
            .kflM(UPDATED_KFL_M)
            .pjzM(UPDATED_PJZ_M)
            .fzsr(UPDATED_FZSR)
            .dayz(UPDATED_DAYZ)
            .hoteltime(UPDATED_HOTELTIME)
            .empn(UPDATED_EMPN)
            .monthz(UPDATED_MONTHZ)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW);
        CzlCzDTO czlCzDTO = czlCzMapper.toDto(updatedCzlCz);

        restCzlCzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, czlCzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(czlCzDTO))
            )
            .andExpect(status().isOk());

        // Validate the CzlCz in the database
        List<CzlCz> czlCzList = czlCzRepository.findAll();
        assertThat(czlCzList).hasSize(databaseSizeBeforeUpdate);
        CzlCz testCzlCz = czlCzList.get(czlCzList.size() - 1);
        assertThat(testCzlCz.getTjrq()).isEqualTo(UPDATED_TJRQ);
        assertThat(testCzlCz.getTypeid()).isEqualTo(UPDATED_TYPEID);
        assertThat(testCzlCz.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCzlCz.getFjsl()).isEqualTo(UPDATED_FJSL);
        assertThat(testCzlCz.getKfl()).isEqualTo(UPDATED_KFL);
        assertThat(testCzlCz.getPjz()).isEqualTo(UPDATED_PJZ);
        assertThat(testCzlCz.getYsfz()).isEqualTo(UPDATED_YSFZ);
        assertThat(testCzlCz.getSjfz()).isEqualTo(UPDATED_SJFZ);
        assertThat(testCzlCz.getFzcz()).isEqualTo(UPDATED_FZCZ);
        assertThat(testCzlCz.getPjzcj()).isEqualTo(UPDATED_PJZCJ);
        assertThat(testCzlCz.getKfsM()).isEqualTo(UPDATED_KFS_M);
        assertThat(testCzlCz.getKflM()).isEqualTo(UPDATED_KFL_M);
        assertThat(testCzlCz.getPjzM()).isEqualTo(UPDATED_PJZ_M);
        assertThat(testCzlCz.getFzsr()).isEqualTo(UPDATED_FZSR);
        assertThat(testCzlCz.getDayz()).isEqualTo(UPDATED_DAYZ);
        assertThat(testCzlCz.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCzlCz.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCzlCz.getMonthz()).isEqualTo(UPDATED_MONTHZ);
        assertThat(testCzlCz.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testCzlCz.getIsnew()).isEqualTo(UPDATED_ISNEW);

        // Validate the CzlCz in Elasticsearch
        verify(mockCzlCzSearchRepository).save(testCzlCz);
    }

    @Test
    @Transactional
    void putNonExistingCzlCz() throws Exception {
        int databaseSizeBeforeUpdate = czlCzRepository.findAll().size();
        czlCz.setId(count.incrementAndGet());

        // Create the CzlCz
        CzlCzDTO czlCzDTO = czlCzMapper.toDto(czlCz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCzlCzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, czlCzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(czlCzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzlCz in the database
        List<CzlCz> czlCzList = czlCzRepository.findAll();
        assertThat(czlCzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzlCz in Elasticsearch
        verify(mockCzlCzSearchRepository, times(0)).save(czlCz);
    }

    @Test
    @Transactional
    void putWithIdMismatchCzlCz() throws Exception {
        int databaseSizeBeforeUpdate = czlCzRepository.findAll().size();
        czlCz.setId(count.incrementAndGet());

        // Create the CzlCz
        CzlCzDTO czlCzDTO = czlCzMapper.toDto(czlCz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzlCzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(czlCzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzlCz in the database
        List<CzlCz> czlCzList = czlCzRepository.findAll();
        assertThat(czlCzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzlCz in Elasticsearch
        verify(mockCzlCzSearchRepository, times(0)).save(czlCz);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCzlCz() throws Exception {
        int databaseSizeBeforeUpdate = czlCzRepository.findAll().size();
        czlCz.setId(count.incrementAndGet());

        // Create the CzlCz
        CzlCzDTO czlCzDTO = czlCzMapper.toDto(czlCz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzlCzMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czlCzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CzlCz in the database
        List<CzlCz> czlCzList = czlCzRepository.findAll();
        assertThat(czlCzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzlCz in Elasticsearch
        verify(mockCzlCzSearchRepository, times(0)).save(czlCz);
    }

    @Test
    @Transactional
    void partialUpdateCzlCzWithPatch() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        int databaseSizeBeforeUpdate = czlCzRepository.findAll().size();

        // Update the czlCz using partial update
        CzlCz partialUpdatedCzlCz = new CzlCz();
        partialUpdatedCzlCz.setId(czlCz.getId());

        partialUpdatedCzlCz
            .tjrq(UPDATED_TJRQ)
            .typeid(UPDATED_TYPEID)
            .type(UPDATED_TYPE)
            .kfl(UPDATED_KFL)
            .sjfz(UPDATED_SJFZ)
            .fzcz(UPDATED_FZCZ)
            .pjzM(UPDATED_PJZ_M)
            .monthz(UPDATED_MONTHZ)
            .hoteldm(UPDATED_HOTELDM);

        restCzlCzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCzlCz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCzlCz))
            )
            .andExpect(status().isOk());

        // Validate the CzlCz in the database
        List<CzlCz> czlCzList = czlCzRepository.findAll();
        assertThat(czlCzList).hasSize(databaseSizeBeforeUpdate);
        CzlCz testCzlCz = czlCzList.get(czlCzList.size() - 1);
        assertThat(testCzlCz.getTjrq()).isEqualTo(UPDATED_TJRQ);
        assertThat(testCzlCz.getTypeid()).isEqualTo(UPDATED_TYPEID);
        assertThat(testCzlCz.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCzlCz.getFjsl()).isEqualTo(DEFAULT_FJSL);
        assertThat(testCzlCz.getKfl()).isEqualByComparingTo(UPDATED_KFL);
        assertThat(testCzlCz.getPjz()).isEqualByComparingTo(DEFAULT_PJZ);
        assertThat(testCzlCz.getYsfz()).isEqualByComparingTo(DEFAULT_YSFZ);
        assertThat(testCzlCz.getSjfz()).isEqualByComparingTo(UPDATED_SJFZ);
        assertThat(testCzlCz.getFzcz()).isEqualByComparingTo(UPDATED_FZCZ);
        assertThat(testCzlCz.getPjzcj()).isEqualByComparingTo(DEFAULT_PJZCJ);
        assertThat(testCzlCz.getKfsM()).isEqualByComparingTo(DEFAULT_KFS_M);
        assertThat(testCzlCz.getKflM()).isEqualByComparingTo(DEFAULT_KFL_M);
        assertThat(testCzlCz.getPjzM()).isEqualByComparingTo(UPDATED_PJZ_M);
        assertThat(testCzlCz.getFzsr()).isEqualByComparingTo(DEFAULT_FZSR);
        assertThat(testCzlCz.getDayz()).isEqualByComparingTo(DEFAULT_DAYZ);
        assertThat(testCzlCz.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testCzlCz.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testCzlCz.getMonthz()).isEqualByComparingTo(UPDATED_MONTHZ);
        assertThat(testCzlCz.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testCzlCz.getIsnew()).isEqualTo(DEFAULT_ISNEW);
    }

    @Test
    @Transactional
    void fullUpdateCzlCzWithPatch() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        int databaseSizeBeforeUpdate = czlCzRepository.findAll().size();

        // Update the czlCz using partial update
        CzlCz partialUpdatedCzlCz = new CzlCz();
        partialUpdatedCzlCz.setId(czlCz.getId());

        partialUpdatedCzlCz
            .tjrq(UPDATED_TJRQ)
            .typeid(UPDATED_TYPEID)
            .type(UPDATED_TYPE)
            .fjsl(UPDATED_FJSL)
            .kfl(UPDATED_KFL)
            .pjz(UPDATED_PJZ)
            .ysfz(UPDATED_YSFZ)
            .sjfz(UPDATED_SJFZ)
            .fzcz(UPDATED_FZCZ)
            .pjzcj(UPDATED_PJZCJ)
            .kfsM(UPDATED_KFS_M)
            .kflM(UPDATED_KFL_M)
            .pjzM(UPDATED_PJZ_M)
            .fzsr(UPDATED_FZSR)
            .dayz(UPDATED_DAYZ)
            .hoteltime(UPDATED_HOTELTIME)
            .empn(UPDATED_EMPN)
            .monthz(UPDATED_MONTHZ)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW);

        restCzlCzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCzlCz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCzlCz))
            )
            .andExpect(status().isOk());

        // Validate the CzlCz in the database
        List<CzlCz> czlCzList = czlCzRepository.findAll();
        assertThat(czlCzList).hasSize(databaseSizeBeforeUpdate);
        CzlCz testCzlCz = czlCzList.get(czlCzList.size() - 1);
        assertThat(testCzlCz.getTjrq()).isEqualTo(UPDATED_TJRQ);
        assertThat(testCzlCz.getTypeid()).isEqualTo(UPDATED_TYPEID);
        assertThat(testCzlCz.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCzlCz.getFjsl()).isEqualTo(UPDATED_FJSL);
        assertThat(testCzlCz.getKfl()).isEqualByComparingTo(UPDATED_KFL);
        assertThat(testCzlCz.getPjz()).isEqualByComparingTo(UPDATED_PJZ);
        assertThat(testCzlCz.getYsfz()).isEqualByComparingTo(UPDATED_YSFZ);
        assertThat(testCzlCz.getSjfz()).isEqualByComparingTo(UPDATED_SJFZ);
        assertThat(testCzlCz.getFzcz()).isEqualByComparingTo(UPDATED_FZCZ);
        assertThat(testCzlCz.getPjzcj()).isEqualByComparingTo(UPDATED_PJZCJ);
        assertThat(testCzlCz.getKfsM()).isEqualByComparingTo(UPDATED_KFS_M);
        assertThat(testCzlCz.getKflM()).isEqualByComparingTo(UPDATED_KFL_M);
        assertThat(testCzlCz.getPjzM()).isEqualByComparingTo(UPDATED_PJZ_M);
        assertThat(testCzlCz.getFzsr()).isEqualByComparingTo(UPDATED_FZSR);
        assertThat(testCzlCz.getDayz()).isEqualByComparingTo(UPDATED_DAYZ);
        assertThat(testCzlCz.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCzlCz.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCzlCz.getMonthz()).isEqualByComparingTo(UPDATED_MONTHZ);
        assertThat(testCzlCz.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testCzlCz.getIsnew()).isEqualTo(UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void patchNonExistingCzlCz() throws Exception {
        int databaseSizeBeforeUpdate = czlCzRepository.findAll().size();
        czlCz.setId(count.incrementAndGet());

        // Create the CzlCz
        CzlCzDTO czlCzDTO = czlCzMapper.toDto(czlCz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCzlCzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, czlCzDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(czlCzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzlCz in the database
        List<CzlCz> czlCzList = czlCzRepository.findAll();
        assertThat(czlCzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzlCz in Elasticsearch
        verify(mockCzlCzSearchRepository, times(0)).save(czlCz);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCzlCz() throws Exception {
        int databaseSizeBeforeUpdate = czlCzRepository.findAll().size();
        czlCz.setId(count.incrementAndGet());

        // Create the CzlCz
        CzlCzDTO czlCzDTO = czlCzMapper.toDto(czlCz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzlCzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(czlCzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzlCz in the database
        List<CzlCz> czlCzList = czlCzRepository.findAll();
        assertThat(czlCzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzlCz in Elasticsearch
        verify(mockCzlCzSearchRepository, times(0)).save(czlCz);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCzlCz() throws Exception {
        int databaseSizeBeforeUpdate = czlCzRepository.findAll().size();
        czlCz.setId(count.incrementAndGet());

        // Create the CzlCz
        CzlCzDTO czlCzDTO = czlCzMapper.toDto(czlCz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzlCzMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(czlCzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CzlCz in the database
        List<CzlCz> czlCzList = czlCzRepository.findAll();
        assertThat(czlCzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzlCz in Elasticsearch
        verify(mockCzlCzSearchRepository, times(0)).save(czlCz);
    }

    @Test
    @Transactional
    void deleteCzlCz() throws Exception {
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);

        int databaseSizeBeforeDelete = czlCzRepository.findAll().size();

        // Delete the czlCz
        restCzlCzMockMvc
            .perform(delete(ENTITY_API_URL_ID, czlCz.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CzlCz> czlCzList = czlCzRepository.findAll();
        assertThat(czlCzList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CzlCz in Elasticsearch
        verify(mockCzlCzSearchRepository, times(1)).deleteById(czlCz.getId());
    }

    @Test
    @Transactional
    void searchCzlCz() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        czlCzRepository.saveAndFlush(czlCz);
        when(mockCzlCzSearchRepository.search(queryStringQuery("id:" + czlCz.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(czlCz), PageRequest.of(0, 1), 1));

        // Search the czlCz
        restCzlCzMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + czlCz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(czlCz.getId().intValue())))
            .andExpect(jsonPath("$.[*].tjrq").value(hasItem(DEFAULT_TJRQ.toString())))
            .andExpect(jsonPath("$.[*].typeid").value(hasItem(DEFAULT_TYPEID.intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].fjsl").value(hasItem(DEFAULT_FJSL.intValue())))
            .andExpect(jsonPath("$.[*].kfl").value(hasItem(sameNumber(DEFAULT_KFL))))
            .andExpect(jsonPath("$.[*].pjz").value(hasItem(sameNumber(DEFAULT_PJZ))))
            .andExpect(jsonPath("$.[*].ysfz").value(hasItem(sameNumber(DEFAULT_YSFZ))))
            .andExpect(jsonPath("$.[*].sjfz").value(hasItem(sameNumber(DEFAULT_SJFZ))))
            .andExpect(jsonPath("$.[*].fzcz").value(hasItem(sameNumber(DEFAULT_FZCZ))))
            .andExpect(jsonPath("$.[*].pjzcj").value(hasItem(sameNumber(DEFAULT_PJZCJ))))
            .andExpect(jsonPath("$.[*].kfsM").value(hasItem(sameNumber(DEFAULT_KFS_M))))
            .andExpect(jsonPath("$.[*].kflM").value(hasItem(sameNumber(DEFAULT_KFL_M))))
            .andExpect(jsonPath("$.[*].pjzM").value(hasItem(sameNumber(DEFAULT_PJZ_M))))
            .andExpect(jsonPath("$.[*].fzsr").value(hasItem(sameNumber(DEFAULT_FZSR))))
            .andExpect(jsonPath("$.[*].dayz").value(hasItem(sameNumber(DEFAULT_DAYZ))))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].monthz").value(hasItem(sameNumber(DEFAULT_MONTHZ))))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())));
    }
}
