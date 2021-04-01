package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Crinfo;
import ihotel.app.repository.CrinfoRepository;
import ihotel.app.repository.search.CrinfoSearchRepository;
import ihotel.app.service.criteria.CrinfoCriteria;
import ihotel.app.service.dto.CrinfoDTO;
import ihotel.app.service.mapper.CrinfoMapper;
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
 * Integration tests for the {@link CrinfoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CrinfoResourceIT {

    private static final Instant DEFAULT_OPERATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_OPERATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_OLDRENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_OLDRENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_OLDRENT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_NEWRENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_NEWRENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_NEWRENT = new BigDecimal(1 - 1);

    private static final String DEFAULT_OLDROOMN = "AAAAAAAAAA";
    private static final String UPDATED_OLDROOMN = "BBBBBBBBBB";

    private static final String DEFAULT_NEWROOMN = "AAAAAAAAAA";
    private static final String UPDATED_NEWROOMN = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final Long DEFAULT_OLDDAY = 1L;
    private static final Long UPDATED_OLDDAY = 2L;
    private static final Long SMALLER_OLDDAY = 1L - 1L;

    private static final Long DEFAULT_NEWDAY = 1L;
    private static final Long UPDATED_NEWDAY = 2L;
    private static final Long SMALLER_NEWDAY = 1L - 1L;

    private static final Instant DEFAULT_HOTELTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOTELTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ROOMN = "AAAAAAAAAA";
    private static final String UPDATED_ROOMN = "BBBBBBBBBB";

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final String DEFAULT_REALNAME = "AAAAAAAAAA";
    private static final String UPDATED_REALNAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ISUP = 1L;
    private static final Long UPDATED_ISUP = 2L;
    private static final Long SMALLER_ISUP = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/crinfos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/crinfos";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CrinfoRepository crinfoRepository;

    @Autowired
    private CrinfoMapper crinfoMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CrinfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private CrinfoSearchRepository mockCrinfoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCrinfoMockMvc;

    private Crinfo crinfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Crinfo createEntity(EntityManager em) {
        Crinfo crinfo = new Crinfo()
            .operatetime(DEFAULT_OPERATETIME)
            .oldrent(DEFAULT_OLDRENT)
            .newrent(DEFAULT_NEWRENT)
            .oldroomn(DEFAULT_OLDROOMN)
            .newroomn(DEFAULT_NEWROOMN)
            .account(DEFAULT_ACCOUNT)
            .empn(DEFAULT_EMPN)
            .oldday(DEFAULT_OLDDAY)
            .newday(DEFAULT_NEWDAY)
            .hoteltime(DEFAULT_HOTELTIME)
            .roomn(DEFAULT_ROOMN)
            .memo(DEFAULT_MEMO)
            .realname(DEFAULT_REALNAME)
            .isup(DEFAULT_ISUP);
        return crinfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Crinfo createUpdatedEntity(EntityManager em) {
        Crinfo crinfo = new Crinfo()
            .operatetime(UPDATED_OPERATETIME)
            .oldrent(UPDATED_OLDRENT)
            .newrent(UPDATED_NEWRENT)
            .oldroomn(UPDATED_OLDROOMN)
            .newroomn(UPDATED_NEWROOMN)
            .account(UPDATED_ACCOUNT)
            .empn(UPDATED_EMPN)
            .oldday(UPDATED_OLDDAY)
            .newday(UPDATED_NEWDAY)
            .hoteltime(UPDATED_HOTELTIME)
            .roomn(UPDATED_ROOMN)
            .memo(UPDATED_MEMO)
            .realname(UPDATED_REALNAME)
            .isup(UPDATED_ISUP);
        return crinfo;
    }

    @BeforeEach
    public void initTest() {
        crinfo = createEntity(em);
    }

    @Test
    @Transactional
    void createCrinfo() throws Exception {
        int databaseSizeBeforeCreate = crinfoRepository.findAll().size();
        // Create the Crinfo
        CrinfoDTO crinfoDTO = crinfoMapper.toDto(crinfo);
        restCrinfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(crinfoDTO)))
            .andExpect(status().isCreated());

        // Validate the Crinfo in the database
        List<Crinfo> crinfoList = crinfoRepository.findAll();
        assertThat(crinfoList).hasSize(databaseSizeBeforeCreate + 1);
        Crinfo testCrinfo = crinfoList.get(crinfoList.size() - 1);
        assertThat(testCrinfo.getOperatetime()).isEqualTo(DEFAULT_OPERATETIME);
        assertThat(testCrinfo.getOldrent()).isEqualByComparingTo(DEFAULT_OLDRENT);
        assertThat(testCrinfo.getNewrent()).isEqualByComparingTo(DEFAULT_NEWRENT);
        assertThat(testCrinfo.getOldroomn()).isEqualTo(DEFAULT_OLDROOMN);
        assertThat(testCrinfo.getNewroomn()).isEqualTo(DEFAULT_NEWROOMN);
        assertThat(testCrinfo.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testCrinfo.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testCrinfo.getOldday()).isEqualTo(DEFAULT_OLDDAY);
        assertThat(testCrinfo.getNewday()).isEqualTo(DEFAULT_NEWDAY);
        assertThat(testCrinfo.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testCrinfo.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testCrinfo.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testCrinfo.getRealname()).isEqualTo(DEFAULT_REALNAME);
        assertThat(testCrinfo.getIsup()).isEqualTo(DEFAULT_ISUP);

        // Validate the Crinfo in Elasticsearch
        verify(mockCrinfoSearchRepository, times(1)).save(testCrinfo);
    }

    @Test
    @Transactional
    void createCrinfoWithExistingId() throws Exception {
        // Create the Crinfo with an existing ID
        crinfo.setId(1L);
        CrinfoDTO crinfoDTO = crinfoMapper.toDto(crinfo);

        int databaseSizeBeforeCreate = crinfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrinfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(crinfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Crinfo in the database
        List<Crinfo> crinfoList = crinfoRepository.findAll();
        assertThat(crinfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Crinfo in Elasticsearch
        verify(mockCrinfoSearchRepository, times(0)).save(crinfo);
    }

    @Test
    @Transactional
    void checkMemoIsRequired() throws Exception {
        int databaseSizeBeforeTest = crinfoRepository.findAll().size();
        // set the field null
        crinfo.setMemo(null);

        // Create the Crinfo, which fails.
        CrinfoDTO crinfoDTO = crinfoMapper.toDto(crinfo);

        restCrinfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(crinfoDTO)))
            .andExpect(status().isBadRequest());

        List<Crinfo> crinfoList = crinfoRepository.findAll();
        assertThat(crinfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCrinfos() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList
        restCrinfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crinfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].operatetime").value(hasItem(DEFAULT_OPERATETIME.toString())))
            .andExpect(jsonPath("$.[*].oldrent").value(hasItem(sameNumber(DEFAULT_OLDRENT))))
            .andExpect(jsonPath("$.[*].newrent").value(hasItem(sameNumber(DEFAULT_NEWRENT))))
            .andExpect(jsonPath("$.[*].oldroomn").value(hasItem(DEFAULT_OLDROOMN)))
            .andExpect(jsonPath("$.[*].newroomn").value(hasItem(DEFAULT_NEWROOMN)))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].oldday").value(hasItem(DEFAULT_OLDDAY.intValue())))
            .andExpect(jsonPath("$.[*].newday").value(hasItem(DEFAULT_NEWDAY.intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].realname").value(hasItem(DEFAULT_REALNAME)))
            .andExpect(jsonPath("$.[*].isup").value(hasItem(DEFAULT_ISUP.intValue())));
    }

    @Test
    @Transactional
    void getCrinfo() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get the crinfo
        restCrinfoMockMvc
            .perform(get(ENTITY_API_URL_ID, crinfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(crinfo.getId().intValue()))
            .andExpect(jsonPath("$.operatetime").value(DEFAULT_OPERATETIME.toString()))
            .andExpect(jsonPath("$.oldrent").value(sameNumber(DEFAULT_OLDRENT)))
            .andExpect(jsonPath("$.newrent").value(sameNumber(DEFAULT_NEWRENT)))
            .andExpect(jsonPath("$.oldroomn").value(DEFAULT_OLDROOMN))
            .andExpect(jsonPath("$.newroomn").value(DEFAULT_NEWROOMN))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.oldday").value(DEFAULT_OLDDAY.intValue()))
            .andExpect(jsonPath("$.newday").value(DEFAULT_NEWDAY.intValue()))
            .andExpect(jsonPath("$.hoteltime").value(DEFAULT_HOTELTIME.toString()))
            .andExpect(jsonPath("$.roomn").value(DEFAULT_ROOMN))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO))
            .andExpect(jsonPath("$.realname").value(DEFAULT_REALNAME))
            .andExpect(jsonPath("$.isup").value(DEFAULT_ISUP.intValue()));
    }

    @Test
    @Transactional
    void getCrinfosByIdFiltering() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        Long id = crinfo.getId();

        defaultCrinfoShouldBeFound("id.equals=" + id);
        defaultCrinfoShouldNotBeFound("id.notEquals=" + id);

        defaultCrinfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCrinfoShouldNotBeFound("id.greaterThan=" + id);

        defaultCrinfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCrinfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCrinfosByOperatetimeIsEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where operatetime equals to DEFAULT_OPERATETIME
        defaultCrinfoShouldBeFound("operatetime.equals=" + DEFAULT_OPERATETIME);

        // Get all the crinfoList where operatetime equals to UPDATED_OPERATETIME
        defaultCrinfoShouldNotBeFound("operatetime.equals=" + UPDATED_OPERATETIME);
    }

    @Test
    @Transactional
    void getAllCrinfosByOperatetimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where operatetime not equals to DEFAULT_OPERATETIME
        defaultCrinfoShouldNotBeFound("operatetime.notEquals=" + DEFAULT_OPERATETIME);

        // Get all the crinfoList where operatetime not equals to UPDATED_OPERATETIME
        defaultCrinfoShouldBeFound("operatetime.notEquals=" + UPDATED_OPERATETIME);
    }

    @Test
    @Transactional
    void getAllCrinfosByOperatetimeIsInShouldWork() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where operatetime in DEFAULT_OPERATETIME or UPDATED_OPERATETIME
        defaultCrinfoShouldBeFound("operatetime.in=" + DEFAULT_OPERATETIME + "," + UPDATED_OPERATETIME);

        // Get all the crinfoList where operatetime equals to UPDATED_OPERATETIME
        defaultCrinfoShouldNotBeFound("operatetime.in=" + UPDATED_OPERATETIME);
    }

    @Test
    @Transactional
    void getAllCrinfosByOperatetimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where operatetime is not null
        defaultCrinfoShouldBeFound("operatetime.specified=true");

        // Get all the crinfoList where operatetime is null
        defaultCrinfoShouldNotBeFound("operatetime.specified=false");
    }

    @Test
    @Transactional
    void getAllCrinfosByOldrentIsEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldrent equals to DEFAULT_OLDRENT
        defaultCrinfoShouldBeFound("oldrent.equals=" + DEFAULT_OLDRENT);

        // Get all the crinfoList where oldrent equals to UPDATED_OLDRENT
        defaultCrinfoShouldNotBeFound("oldrent.equals=" + UPDATED_OLDRENT);
    }

    @Test
    @Transactional
    void getAllCrinfosByOldrentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldrent not equals to DEFAULT_OLDRENT
        defaultCrinfoShouldNotBeFound("oldrent.notEquals=" + DEFAULT_OLDRENT);

        // Get all the crinfoList where oldrent not equals to UPDATED_OLDRENT
        defaultCrinfoShouldBeFound("oldrent.notEquals=" + UPDATED_OLDRENT);
    }

    @Test
    @Transactional
    void getAllCrinfosByOldrentIsInShouldWork() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldrent in DEFAULT_OLDRENT or UPDATED_OLDRENT
        defaultCrinfoShouldBeFound("oldrent.in=" + DEFAULT_OLDRENT + "," + UPDATED_OLDRENT);

        // Get all the crinfoList where oldrent equals to UPDATED_OLDRENT
        defaultCrinfoShouldNotBeFound("oldrent.in=" + UPDATED_OLDRENT);
    }

    @Test
    @Transactional
    void getAllCrinfosByOldrentIsNullOrNotNull() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldrent is not null
        defaultCrinfoShouldBeFound("oldrent.specified=true");

        // Get all the crinfoList where oldrent is null
        defaultCrinfoShouldNotBeFound("oldrent.specified=false");
    }

    @Test
    @Transactional
    void getAllCrinfosByOldrentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldrent is greater than or equal to DEFAULT_OLDRENT
        defaultCrinfoShouldBeFound("oldrent.greaterThanOrEqual=" + DEFAULT_OLDRENT);

        // Get all the crinfoList where oldrent is greater than or equal to UPDATED_OLDRENT
        defaultCrinfoShouldNotBeFound("oldrent.greaterThanOrEqual=" + UPDATED_OLDRENT);
    }

    @Test
    @Transactional
    void getAllCrinfosByOldrentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldrent is less than or equal to DEFAULT_OLDRENT
        defaultCrinfoShouldBeFound("oldrent.lessThanOrEqual=" + DEFAULT_OLDRENT);

        // Get all the crinfoList where oldrent is less than or equal to SMALLER_OLDRENT
        defaultCrinfoShouldNotBeFound("oldrent.lessThanOrEqual=" + SMALLER_OLDRENT);
    }

    @Test
    @Transactional
    void getAllCrinfosByOldrentIsLessThanSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldrent is less than DEFAULT_OLDRENT
        defaultCrinfoShouldNotBeFound("oldrent.lessThan=" + DEFAULT_OLDRENT);

        // Get all the crinfoList where oldrent is less than UPDATED_OLDRENT
        defaultCrinfoShouldBeFound("oldrent.lessThan=" + UPDATED_OLDRENT);
    }

    @Test
    @Transactional
    void getAllCrinfosByOldrentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldrent is greater than DEFAULT_OLDRENT
        defaultCrinfoShouldNotBeFound("oldrent.greaterThan=" + DEFAULT_OLDRENT);

        // Get all the crinfoList where oldrent is greater than SMALLER_OLDRENT
        defaultCrinfoShouldBeFound("oldrent.greaterThan=" + SMALLER_OLDRENT);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewrentIsEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newrent equals to DEFAULT_NEWRENT
        defaultCrinfoShouldBeFound("newrent.equals=" + DEFAULT_NEWRENT);

        // Get all the crinfoList where newrent equals to UPDATED_NEWRENT
        defaultCrinfoShouldNotBeFound("newrent.equals=" + UPDATED_NEWRENT);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewrentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newrent not equals to DEFAULT_NEWRENT
        defaultCrinfoShouldNotBeFound("newrent.notEquals=" + DEFAULT_NEWRENT);

        // Get all the crinfoList where newrent not equals to UPDATED_NEWRENT
        defaultCrinfoShouldBeFound("newrent.notEquals=" + UPDATED_NEWRENT);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewrentIsInShouldWork() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newrent in DEFAULT_NEWRENT or UPDATED_NEWRENT
        defaultCrinfoShouldBeFound("newrent.in=" + DEFAULT_NEWRENT + "," + UPDATED_NEWRENT);

        // Get all the crinfoList where newrent equals to UPDATED_NEWRENT
        defaultCrinfoShouldNotBeFound("newrent.in=" + UPDATED_NEWRENT);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewrentIsNullOrNotNull() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newrent is not null
        defaultCrinfoShouldBeFound("newrent.specified=true");

        // Get all the crinfoList where newrent is null
        defaultCrinfoShouldNotBeFound("newrent.specified=false");
    }

    @Test
    @Transactional
    void getAllCrinfosByNewrentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newrent is greater than or equal to DEFAULT_NEWRENT
        defaultCrinfoShouldBeFound("newrent.greaterThanOrEqual=" + DEFAULT_NEWRENT);

        // Get all the crinfoList where newrent is greater than or equal to UPDATED_NEWRENT
        defaultCrinfoShouldNotBeFound("newrent.greaterThanOrEqual=" + UPDATED_NEWRENT);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewrentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newrent is less than or equal to DEFAULT_NEWRENT
        defaultCrinfoShouldBeFound("newrent.lessThanOrEqual=" + DEFAULT_NEWRENT);

        // Get all the crinfoList where newrent is less than or equal to SMALLER_NEWRENT
        defaultCrinfoShouldNotBeFound("newrent.lessThanOrEqual=" + SMALLER_NEWRENT);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewrentIsLessThanSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newrent is less than DEFAULT_NEWRENT
        defaultCrinfoShouldNotBeFound("newrent.lessThan=" + DEFAULT_NEWRENT);

        // Get all the crinfoList where newrent is less than UPDATED_NEWRENT
        defaultCrinfoShouldBeFound("newrent.lessThan=" + UPDATED_NEWRENT);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewrentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newrent is greater than DEFAULT_NEWRENT
        defaultCrinfoShouldNotBeFound("newrent.greaterThan=" + DEFAULT_NEWRENT);

        // Get all the crinfoList where newrent is greater than SMALLER_NEWRENT
        defaultCrinfoShouldBeFound("newrent.greaterThan=" + SMALLER_NEWRENT);
    }

    @Test
    @Transactional
    void getAllCrinfosByOldroomnIsEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldroomn equals to DEFAULT_OLDROOMN
        defaultCrinfoShouldBeFound("oldroomn.equals=" + DEFAULT_OLDROOMN);

        // Get all the crinfoList where oldroomn equals to UPDATED_OLDROOMN
        defaultCrinfoShouldNotBeFound("oldroomn.equals=" + UPDATED_OLDROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByOldroomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldroomn not equals to DEFAULT_OLDROOMN
        defaultCrinfoShouldNotBeFound("oldroomn.notEquals=" + DEFAULT_OLDROOMN);

        // Get all the crinfoList where oldroomn not equals to UPDATED_OLDROOMN
        defaultCrinfoShouldBeFound("oldroomn.notEquals=" + UPDATED_OLDROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByOldroomnIsInShouldWork() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldroomn in DEFAULT_OLDROOMN or UPDATED_OLDROOMN
        defaultCrinfoShouldBeFound("oldroomn.in=" + DEFAULT_OLDROOMN + "," + UPDATED_OLDROOMN);

        // Get all the crinfoList where oldroomn equals to UPDATED_OLDROOMN
        defaultCrinfoShouldNotBeFound("oldroomn.in=" + UPDATED_OLDROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByOldroomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldroomn is not null
        defaultCrinfoShouldBeFound("oldroomn.specified=true");

        // Get all the crinfoList where oldroomn is null
        defaultCrinfoShouldNotBeFound("oldroomn.specified=false");
    }

    @Test
    @Transactional
    void getAllCrinfosByOldroomnContainsSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldroomn contains DEFAULT_OLDROOMN
        defaultCrinfoShouldBeFound("oldroomn.contains=" + DEFAULT_OLDROOMN);

        // Get all the crinfoList where oldroomn contains UPDATED_OLDROOMN
        defaultCrinfoShouldNotBeFound("oldroomn.contains=" + UPDATED_OLDROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByOldroomnNotContainsSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldroomn does not contain DEFAULT_OLDROOMN
        defaultCrinfoShouldNotBeFound("oldroomn.doesNotContain=" + DEFAULT_OLDROOMN);

        // Get all the crinfoList where oldroomn does not contain UPDATED_OLDROOMN
        defaultCrinfoShouldBeFound("oldroomn.doesNotContain=" + UPDATED_OLDROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewroomnIsEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newroomn equals to DEFAULT_NEWROOMN
        defaultCrinfoShouldBeFound("newroomn.equals=" + DEFAULT_NEWROOMN);

        // Get all the crinfoList where newroomn equals to UPDATED_NEWROOMN
        defaultCrinfoShouldNotBeFound("newroomn.equals=" + UPDATED_NEWROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewroomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newroomn not equals to DEFAULT_NEWROOMN
        defaultCrinfoShouldNotBeFound("newroomn.notEquals=" + DEFAULT_NEWROOMN);

        // Get all the crinfoList where newroomn not equals to UPDATED_NEWROOMN
        defaultCrinfoShouldBeFound("newroomn.notEquals=" + UPDATED_NEWROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewroomnIsInShouldWork() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newroomn in DEFAULT_NEWROOMN or UPDATED_NEWROOMN
        defaultCrinfoShouldBeFound("newroomn.in=" + DEFAULT_NEWROOMN + "," + UPDATED_NEWROOMN);

        // Get all the crinfoList where newroomn equals to UPDATED_NEWROOMN
        defaultCrinfoShouldNotBeFound("newroomn.in=" + UPDATED_NEWROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewroomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newroomn is not null
        defaultCrinfoShouldBeFound("newroomn.specified=true");

        // Get all the crinfoList where newroomn is null
        defaultCrinfoShouldNotBeFound("newroomn.specified=false");
    }

    @Test
    @Transactional
    void getAllCrinfosByNewroomnContainsSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newroomn contains DEFAULT_NEWROOMN
        defaultCrinfoShouldBeFound("newroomn.contains=" + DEFAULT_NEWROOMN);

        // Get all the crinfoList where newroomn contains UPDATED_NEWROOMN
        defaultCrinfoShouldNotBeFound("newroomn.contains=" + UPDATED_NEWROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewroomnNotContainsSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newroomn does not contain DEFAULT_NEWROOMN
        defaultCrinfoShouldNotBeFound("newroomn.doesNotContain=" + DEFAULT_NEWROOMN);

        // Get all the crinfoList where newroomn does not contain UPDATED_NEWROOMN
        defaultCrinfoShouldBeFound("newroomn.doesNotContain=" + UPDATED_NEWROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where account equals to DEFAULT_ACCOUNT
        defaultCrinfoShouldBeFound("account.equals=" + DEFAULT_ACCOUNT);

        // Get all the crinfoList where account equals to UPDATED_ACCOUNT
        defaultCrinfoShouldNotBeFound("account.equals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCrinfosByAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where account not equals to DEFAULT_ACCOUNT
        defaultCrinfoShouldNotBeFound("account.notEquals=" + DEFAULT_ACCOUNT);

        // Get all the crinfoList where account not equals to UPDATED_ACCOUNT
        defaultCrinfoShouldBeFound("account.notEquals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCrinfosByAccountIsInShouldWork() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where account in DEFAULT_ACCOUNT or UPDATED_ACCOUNT
        defaultCrinfoShouldBeFound("account.in=" + DEFAULT_ACCOUNT + "," + UPDATED_ACCOUNT);

        // Get all the crinfoList where account equals to UPDATED_ACCOUNT
        defaultCrinfoShouldNotBeFound("account.in=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCrinfosByAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where account is not null
        defaultCrinfoShouldBeFound("account.specified=true");

        // Get all the crinfoList where account is null
        defaultCrinfoShouldNotBeFound("account.specified=false");
    }

    @Test
    @Transactional
    void getAllCrinfosByAccountContainsSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where account contains DEFAULT_ACCOUNT
        defaultCrinfoShouldBeFound("account.contains=" + DEFAULT_ACCOUNT);

        // Get all the crinfoList where account contains UPDATED_ACCOUNT
        defaultCrinfoShouldNotBeFound("account.contains=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCrinfosByAccountNotContainsSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where account does not contain DEFAULT_ACCOUNT
        defaultCrinfoShouldNotBeFound("account.doesNotContain=" + DEFAULT_ACCOUNT);

        // Get all the crinfoList where account does not contain UPDATED_ACCOUNT
        defaultCrinfoShouldBeFound("account.doesNotContain=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCrinfosByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where empn equals to DEFAULT_EMPN
        defaultCrinfoShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the crinfoList where empn equals to UPDATED_EMPN
        defaultCrinfoShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCrinfosByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where empn not equals to DEFAULT_EMPN
        defaultCrinfoShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the crinfoList where empn not equals to UPDATED_EMPN
        defaultCrinfoShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCrinfosByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultCrinfoShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the crinfoList where empn equals to UPDATED_EMPN
        defaultCrinfoShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCrinfosByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where empn is not null
        defaultCrinfoShouldBeFound("empn.specified=true");

        // Get all the crinfoList where empn is null
        defaultCrinfoShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllCrinfosByEmpnContainsSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where empn contains DEFAULT_EMPN
        defaultCrinfoShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the crinfoList where empn contains UPDATED_EMPN
        defaultCrinfoShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCrinfosByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where empn does not contain DEFAULT_EMPN
        defaultCrinfoShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the crinfoList where empn does not contain UPDATED_EMPN
        defaultCrinfoShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCrinfosByOlddayIsEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldday equals to DEFAULT_OLDDAY
        defaultCrinfoShouldBeFound("oldday.equals=" + DEFAULT_OLDDAY);

        // Get all the crinfoList where oldday equals to UPDATED_OLDDAY
        defaultCrinfoShouldNotBeFound("oldday.equals=" + UPDATED_OLDDAY);
    }

    @Test
    @Transactional
    void getAllCrinfosByOlddayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldday not equals to DEFAULT_OLDDAY
        defaultCrinfoShouldNotBeFound("oldday.notEquals=" + DEFAULT_OLDDAY);

        // Get all the crinfoList where oldday not equals to UPDATED_OLDDAY
        defaultCrinfoShouldBeFound("oldday.notEquals=" + UPDATED_OLDDAY);
    }

    @Test
    @Transactional
    void getAllCrinfosByOlddayIsInShouldWork() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldday in DEFAULT_OLDDAY or UPDATED_OLDDAY
        defaultCrinfoShouldBeFound("oldday.in=" + DEFAULT_OLDDAY + "," + UPDATED_OLDDAY);

        // Get all the crinfoList where oldday equals to UPDATED_OLDDAY
        defaultCrinfoShouldNotBeFound("oldday.in=" + UPDATED_OLDDAY);
    }

    @Test
    @Transactional
    void getAllCrinfosByOlddayIsNullOrNotNull() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldday is not null
        defaultCrinfoShouldBeFound("oldday.specified=true");

        // Get all the crinfoList where oldday is null
        defaultCrinfoShouldNotBeFound("oldday.specified=false");
    }

    @Test
    @Transactional
    void getAllCrinfosByOlddayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldday is greater than or equal to DEFAULT_OLDDAY
        defaultCrinfoShouldBeFound("oldday.greaterThanOrEqual=" + DEFAULT_OLDDAY);

        // Get all the crinfoList where oldday is greater than or equal to UPDATED_OLDDAY
        defaultCrinfoShouldNotBeFound("oldday.greaterThanOrEqual=" + UPDATED_OLDDAY);
    }

    @Test
    @Transactional
    void getAllCrinfosByOlddayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldday is less than or equal to DEFAULT_OLDDAY
        defaultCrinfoShouldBeFound("oldday.lessThanOrEqual=" + DEFAULT_OLDDAY);

        // Get all the crinfoList where oldday is less than or equal to SMALLER_OLDDAY
        defaultCrinfoShouldNotBeFound("oldday.lessThanOrEqual=" + SMALLER_OLDDAY);
    }

    @Test
    @Transactional
    void getAllCrinfosByOlddayIsLessThanSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldday is less than DEFAULT_OLDDAY
        defaultCrinfoShouldNotBeFound("oldday.lessThan=" + DEFAULT_OLDDAY);

        // Get all the crinfoList where oldday is less than UPDATED_OLDDAY
        defaultCrinfoShouldBeFound("oldday.lessThan=" + UPDATED_OLDDAY);
    }

    @Test
    @Transactional
    void getAllCrinfosByOlddayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where oldday is greater than DEFAULT_OLDDAY
        defaultCrinfoShouldNotBeFound("oldday.greaterThan=" + DEFAULT_OLDDAY);

        // Get all the crinfoList where oldday is greater than SMALLER_OLDDAY
        defaultCrinfoShouldBeFound("oldday.greaterThan=" + SMALLER_OLDDAY);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewdayIsEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newday equals to DEFAULT_NEWDAY
        defaultCrinfoShouldBeFound("newday.equals=" + DEFAULT_NEWDAY);

        // Get all the crinfoList where newday equals to UPDATED_NEWDAY
        defaultCrinfoShouldNotBeFound("newday.equals=" + UPDATED_NEWDAY);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewdayIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newday not equals to DEFAULT_NEWDAY
        defaultCrinfoShouldNotBeFound("newday.notEquals=" + DEFAULT_NEWDAY);

        // Get all the crinfoList where newday not equals to UPDATED_NEWDAY
        defaultCrinfoShouldBeFound("newday.notEquals=" + UPDATED_NEWDAY);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewdayIsInShouldWork() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newday in DEFAULT_NEWDAY or UPDATED_NEWDAY
        defaultCrinfoShouldBeFound("newday.in=" + DEFAULT_NEWDAY + "," + UPDATED_NEWDAY);

        // Get all the crinfoList where newday equals to UPDATED_NEWDAY
        defaultCrinfoShouldNotBeFound("newday.in=" + UPDATED_NEWDAY);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newday is not null
        defaultCrinfoShouldBeFound("newday.specified=true");

        // Get all the crinfoList where newday is null
        defaultCrinfoShouldNotBeFound("newday.specified=false");
    }

    @Test
    @Transactional
    void getAllCrinfosByNewdayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newday is greater than or equal to DEFAULT_NEWDAY
        defaultCrinfoShouldBeFound("newday.greaterThanOrEqual=" + DEFAULT_NEWDAY);

        // Get all the crinfoList where newday is greater than or equal to UPDATED_NEWDAY
        defaultCrinfoShouldNotBeFound("newday.greaterThanOrEqual=" + UPDATED_NEWDAY);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewdayIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newday is less than or equal to DEFAULT_NEWDAY
        defaultCrinfoShouldBeFound("newday.lessThanOrEqual=" + DEFAULT_NEWDAY);

        // Get all the crinfoList where newday is less than or equal to SMALLER_NEWDAY
        defaultCrinfoShouldNotBeFound("newday.lessThanOrEqual=" + SMALLER_NEWDAY);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewdayIsLessThanSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newday is less than DEFAULT_NEWDAY
        defaultCrinfoShouldNotBeFound("newday.lessThan=" + DEFAULT_NEWDAY);

        // Get all the crinfoList where newday is less than UPDATED_NEWDAY
        defaultCrinfoShouldBeFound("newday.lessThan=" + UPDATED_NEWDAY);
    }

    @Test
    @Transactional
    void getAllCrinfosByNewdayIsGreaterThanSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where newday is greater than DEFAULT_NEWDAY
        defaultCrinfoShouldNotBeFound("newday.greaterThan=" + DEFAULT_NEWDAY);

        // Get all the crinfoList where newday is greater than SMALLER_NEWDAY
        defaultCrinfoShouldBeFound("newday.greaterThan=" + SMALLER_NEWDAY);
    }

    @Test
    @Transactional
    void getAllCrinfosByHoteltimeIsEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where hoteltime equals to DEFAULT_HOTELTIME
        defaultCrinfoShouldBeFound("hoteltime.equals=" + DEFAULT_HOTELTIME);

        // Get all the crinfoList where hoteltime equals to UPDATED_HOTELTIME
        defaultCrinfoShouldNotBeFound("hoteltime.equals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCrinfosByHoteltimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where hoteltime not equals to DEFAULT_HOTELTIME
        defaultCrinfoShouldNotBeFound("hoteltime.notEquals=" + DEFAULT_HOTELTIME);

        // Get all the crinfoList where hoteltime not equals to UPDATED_HOTELTIME
        defaultCrinfoShouldBeFound("hoteltime.notEquals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCrinfosByHoteltimeIsInShouldWork() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where hoteltime in DEFAULT_HOTELTIME or UPDATED_HOTELTIME
        defaultCrinfoShouldBeFound("hoteltime.in=" + DEFAULT_HOTELTIME + "," + UPDATED_HOTELTIME);

        // Get all the crinfoList where hoteltime equals to UPDATED_HOTELTIME
        defaultCrinfoShouldNotBeFound("hoteltime.in=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCrinfosByHoteltimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where hoteltime is not null
        defaultCrinfoShouldBeFound("hoteltime.specified=true");

        // Get all the crinfoList where hoteltime is null
        defaultCrinfoShouldNotBeFound("hoteltime.specified=false");
    }

    @Test
    @Transactional
    void getAllCrinfosByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where roomn equals to DEFAULT_ROOMN
        defaultCrinfoShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the crinfoList where roomn equals to UPDATED_ROOMN
        defaultCrinfoShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where roomn not equals to DEFAULT_ROOMN
        defaultCrinfoShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the crinfoList where roomn not equals to UPDATED_ROOMN
        defaultCrinfoShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultCrinfoShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the crinfoList where roomn equals to UPDATED_ROOMN
        defaultCrinfoShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where roomn is not null
        defaultCrinfoShouldBeFound("roomn.specified=true");

        // Get all the crinfoList where roomn is null
        defaultCrinfoShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllCrinfosByRoomnContainsSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where roomn contains DEFAULT_ROOMN
        defaultCrinfoShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the crinfoList where roomn contains UPDATED_ROOMN
        defaultCrinfoShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where roomn does not contain DEFAULT_ROOMN
        defaultCrinfoShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the crinfoList where roomn does not contain UPDATED_ROOMN
        defaultCrinfoShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCrinfosByMemoIsEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where memo equals to DEFAULT_MEMO
        defaultCrinfoShouldBeFound("memo.equals=" + DEFAULT_MEMO);

        // Get all the crinfoList where memo equals to UPDATED_MEMO
        defaultCrinfoShouldNotBeFound("memo.equals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCrinfosByMemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where memo not equals to DEFAULT_MEMO
        defaultCrinfoShouldNotBeFound("memo.notEquals=" + DEFAULT_MEMO);

        // Get all the crinfoList where memo not equals to UPDATED_MEMO
        defaultCrinfoShouldBeFound("memo.notEquals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCrinfosByMemoIsInShouldWork() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where memo in DEFAULT_MEMO or UPDATED_MEMO
        defaultCrinfoShouldBeFound("memo.in=" + DEFAULT_MEMO + "," + UPDATED_MEMO);

        // Get all the crinfoList where memo equals to UPDATED_MEMO
        defaultCrinfoShouldNotBeFound("memo.in=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCrinfosByMemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where memo is not null
        defaultCrinfoShouldBeFound("memo.specified=true");

        // Get all the crinfoList where memo is null
        defaultCrinfoShouldNotBeFound("memo.specified=false");
    }

    @Test
    @Transactional
    void getAllCrinfosByMemoContainsSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where memo contains DEFAULT_MEMO
        defaultCrinfoShouldBeFound("memo.contains=" + DEFAULT_MEMO);

        // Get all the crinfoList where memo contains UPDATED_MEMO
        defaultCrinfoShouldNotBeFound("memo.contains=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCrinfosByMemoNotContainsSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where memo does not contain DEFAULT_MEMO
        defaultCrinfoShouldNotBeFound("memo.doesNotContain=" + DEFAULT_MEMO);

        // Get all the crinfoList where memo does not contain UPDATED_MEMO
        defaultCrinfoShouldBeFound("memo.doesNotContain=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCrinfosByRealnameIsEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where realname equals to DEFAULT_REALNAME
        defaultCrinfoShouldBeFound("realname.equals=" + DEFAULT_REALNAME);

        // Get all the crinfoList where realname equals to UPDATED_REALNAME
        defaultCrinfoShouldNotBeFound("realname.equals=" + UPDATED_REALNAME);
    }

    @Test
    @Transactional
    void getAllCrinfosByRealnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where realname not equals to DEFAULT_REALNAME
        defaultCrinfoShouldNotBeFound("realname.notEquals=" + DEFAULT_REALNAME);

        // Get all the crinfoList where realname not equals to UPDATED_REALNAME
        defaultCrinfoShouldBeFound("realname.notEquals=" + UPDATED_REALNAME);
    }

    @Test
    @Transactional
    void getAllCrinfosByRealnameIsInShouldWork() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where realname in DEFAULT_REALNAME or UPDATED_REALNAME
        defaultCrinfoShouldBeFound("realname.in=" + DEFAULT_REALNAME + "," + UPDATED_REALNAME);

        // Get all the crinfoList where realname equals to UPDATED_REALNAME
        defaultCrinfoShouldNotBeFound("realname.in=" + UPDATED_REALNAME);
    }

    @Test
    @Transactional
    void getAllCrinfosByRealnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where realname is not null
        defaultCrinfoShouldBeFound("realname.specified=true");

        // Get all the crinfoList where realname is null
        defaultCrinfoShouldNotBeFound("realname.specified=false");
    }

    @Test
    @Transactional
    void getAllCrinfosByRealnameContainsSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where realname contains DEFAULT_REALNAME
        defaultCrinfoShouldBeFound("realname.contains=" + DEFAULT_REALNAME);

        // Get all the crinfoList where realname contains UPDATED_REALNAME
        defaultCrinfoShouldNotBeFound("realname.contains=" + UPDATED_REALNAME);
    }

    @Test
    @Transactional
    void getAllCrinfosByRealnameNotContainsSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where realname does not contain DEFAULT_REALNAME
        defaultCrinfoShouldNotBeFound("realname.doesNotContain=" + DEFAULT_REALNAME);

        // Get all the crinfoList where realname does not contain UPDATED_REALNAME
        defaultCrinfoShouldBeFound("realname.doesNotContain=" + UPDATED_REALNAME);
    }

    @Test
    @Transactional
    void getAllCrinfosByIsupIsEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where isup equals to DEFAULT_ISUP
        defaultCrinfoShouldBeFound("isup.equals=" + DEFAULT_ISUP);

        // Get all the crinfoList where isup equals to UPDATED_ISUP
        defaultCrinfoShouldNotBeFound("isup.equals=" + UPDATED_ISUP);
    }

    @Test
    @Transactional
    void getAllCrinfosByIsupIsNotEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where isup not equals to DEFAULT_ISUP
        defaultCrinfoShouldNotBeFound("isup.notEquals=" + DEFAULT_ISUP);

        // Get all the crinfoList where isup not equals to UPDATED_ISUP
        defaultCrinfoShouldBeFound("isup.notEquals=" + UPDATED_ISUP);
    }

    @Test
    @Transactional
    void getAllCrinfosByIsupIsInShouldWork() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where isup in DEFAULT_ISUP or UPDATED_ISUP
        defaultCrinfoShouldBeFound("isup.in=" + DEFAULT_ISUP + "," + UPDATED_ISUP);

        // Get all the crinfoList where isup equals to UPDATED_ISUP
        defaultCrinfoShouldNotBeFound("isup.in=" + UPDATED_ISUP);
    }

    @Test
    @Transactional
    void getAllCrinfosByIsupIsNullOrNotNull() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where isup is not null
        defaultCrinfoShouldBeFound("isup.specified=true");

        // Get all the crinfoList where isup is null
        defaultCrinfoShouldNotBeFound("isup.specified=false");
    }

    @Test
    @Transactional
    void getAllCrinfosByIsupIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where isup is greater than or equal to DEFAULT_ISUP
        defaultCrinfoShouldBeFound("isup.greaterThanOrEqual=" + DEFAULT_ISUP);

        // Get all the crinfoList where isup is greater than or equal to UPDATED_ISUP
        defaultCrinfoShouldNotBeFound("isup.greaterThanOrEqual=" + UPDATED_ISUP);
    }

    @Test
    @Transactional
    void getAllCrinfosByIsupIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where isup is less than or equal to DEFAULT_ISUP
        defaultCrinfoShouldBeFound("isup.lessThanOrEqual=" + DEFAULT_ISUP);

        // Get all the crinfoList where isup is less than or equal to SMALLER_ISUP
        defaultCrinfoShouldNotBeFound("isup.lessThanOrEqual=" + SMALLER_ISUP);
    }

    @Test
    @Transactional
    void getAllCrinfosByIsupIsLessThanSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where isup is less than DEFAULT_ISUP
        defaultCrinfoShouldNotBeFound("isup.lessThan=" + DEFAULT_ISUP);

        // Get all the crinfoList where isup is less than UPDATED_ISUP
        defaultCrinfoShouldBeFound("isup.lessThan=" + UPDATED_ISUP);
    }

    @Test
    @Transactional
    void getAllCrinfosByIsupIsGreaterThanSomething() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        // Get all the crinfoList where isup is greater than DEFAULT_ISUP
        defaultCrinfoShouldNotBeFound("isup.greaterThan=" + DEFAULT_ISUP);

        // Get all the crinfoList where isup is greater than SMALLER_ISUP
        defaultCrinfoShouldBeFound("isup.greaterThan=" + SMALLER_ISUP);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCrinfoShouldBeFound(String filter) throws Exception {
        restCrinfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crinfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].operatetime").value(hasItem(DEFAULT_OPERATETIME.toString())))
            .andExpect(jsonPath("$.[*].oldrent").value(hasItem(sameNumber(DEFAULT_OLDRENT))))
            .andExpect(jsonPath("$.[*].newrent").value(hasItem(sameNumber(DEFAULT_NEWRENT))))
            .andExpect(jsonPath("$.[*].oldroomn").value(hasItem(DEFAULT_OLDROOMN)))
            .andExpect(jsonPath("$.[*].newroomn").value(hasItem(DEFAULT_NEWROOMN)))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].oldday").value(hasItem(DEFAULT_OLDDAY.intValue())))
            .andExpect(jsonPath("$.[*].newday").value(hasItem(DEFAULT_NEWDAY.intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].realname").value(hasItem(DEFAULT_REALNAME)))
            .andExpect(jsonPath("$.[*].isup").value(hasItem(DEFAULT_ISUP.intValue())));

        // Check, that the count call also returns 1
        restCrinfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCrinfoShouldNotBeFound(String filter) throws Exception {
        restCrinfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCrinfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCrinfo() throws Exception {
        // Get the crinfo
        restCrinfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCrinfo() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        int databaseSizeBeforeUpdate = crinfoRepository.findAll().size();

        // Update the crinfo
        Crinfo updatedCrinfo = crinfoRepository.findById(crinfo.getId()).get();
        // Disconnect from session so that the updates on updatedCrinfo are not directly saved in db
        em.detach(updatedCrinfo);
        updatedCrinfo
            .operatetime(UPDATED_OPERATETIME)
            .oldrent(UPDATED_OLDRENT)
            .newrent(UPDATED_NEWRENT)
            .oldroomn(UPDATED_OLDROOMN)
            .newroomn(UPDATED_NEWROOMN)
            .account(UPDATED_ACCOUNT)
            .empn(UPDATED_EMPN)
            .oldday(UPDATED_OLDDAY)
            .newday(UPDATED_NEWDAY)
            .hoteltime(UPDATED_HOTELTIME)
            .roomn(UPDATED_ROOMN)
            .memo(UPDATED_MEMO)
            .realname(UPDATED_REALNAME)
            .isup(UPDATED_ISUP);
        CrinfoDTO crinfoDTO = crinfoMapper.toDto(updatedCrinfo);

        restCrinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, crinfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crinfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Crinfo in the database
        List<Crinfo> crinfoList = crinfoRepository.findAll();
        assertThat(crinfoList).hasSize(databaseSizeBeforeUpdate);
        Crinfo testCrinfo = crinfoList.get(crinfoList.size() - 1);
        assertThat(testCrinfo.getOperatetime()).isEqualTo(UPDATED_OPERATETIME);
        assertThat(testCrinfo.getOldrent()).isEqualTo(UPDATED_OLDRENT);
        assertThat(testCrinfo.getNewrent()).isEqualTo(UPDATED_NEWRENT);
        assertThat(testCrinfo.getOldroomn()).isEqualTo(UPDATED_OLDROOMN);
        assertThat(testCrinfo.getNewroomn()).isEqualTo(UPDATED_NEWROOMN);
        assertThat(testCrinfo.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCrinfo.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCrinfo.getOldday()).isEqualTo(UPDATED_OLDDAY);
        assertThat(testCrinfo.getNewday()).isEqualTo(UPDATED_NEWDAY);
        assertThat(testCrinfo.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCrinfo.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCrinfo.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testCrinfo.getRealname()).isEqualTo(UPDATED_REALNAME);
        assertThat(testCrinfo.getIsup()).isEqualTo(UPDATED_ISUP);

        // Validate the Crinfo in Elasticsearch
        verify(mockCrinfoSearchRepository).save(testCrinfo);
    }

    @Test
    @Transactional
    void putNonExistingCrinfo() throws Exception {
        int databaseSizeBeforeUpdate = crinfoRepository.findAll().size();
        crinfo.setId(count.incrementAndGet());

        // Create the Crinfo
        CrinfoDTO crinfoDTO = crinfoMapper.toDto(crinfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, crinfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crinfo in the database
        List<Crinfo> crinfoList = crinfoRepository.findAll();
        assertThat(crinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Crinfo in Elasticsearch
        verify(mockCrinfoSearchRepository, times(0)).save(crinfo);
    }

    @Test
    @Transactional
    void putWithIdMismatchCrinfo() throws Exception {
        int databaseSizeBeforeUpdate = crinfoRepository.findAll().size();
        crinfo.setId(count.incrementAndGet());

        // Create the Crinfo
        CrinfoDTO crinfoDTO = crinfoMapper.toDto(crinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(crinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crinfo in the database
        List<Crinfo> crinfoList = crinfoRepository.findAll();
        assertThat(crinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Crinfo in Elasticsearch
        verify(mockCrinfoSearchRepository, times(0)).save(crinfo);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCrinfo() throws Exception {
        int databaseSizeBeforeUpdate = crinfoRepository.findAll().size();
        crinfo.setId(count.incrementAndGet());

        // Create the Crinfo
        CrinfoDTO crinfoDTO = crinfoMapper.toDto(crinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrinfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(crinfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Crinfo in the database
        List<Crinfo> crinfoList = crinfoRepository.findAll();
        assertThat(crinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Crinfo in Elasticsearch
        verify(mockCrinfoSearchRepository, times(0)).save(crinfo);
    }

    @Test
    @Transactional
    void partialUpdateCrinfoWithPatch() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        int databaseSizeBeforeUpdate = crinfoRepository.findAll().size();

        // Update the crinfo using partial update
        Crinfo partialUpdatedCrinfo = new Crinfo();
        partialUpdatedCrinfo.setId(crinfo.getId());

        partialUpdatedCrinfo
            .oldrent(UPDATED_OLDRENT)
            .newroomn(UPDATED_NEWROOMN)
            .empn(UPDATED_EMPN)
            .oldday(UPDATED_OLDDAY)
            .hoteltime(UPDATED_HOTELTIME)
            .memo(UPDATED_MEMO);

        restCrinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrinfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCrinfo))
            )
            .andExpect(status().isOk());

        // Validate the Crinfo in the database
        List<Crinfo> crinfoList = crinfoRepository.findAll();
        assertThat(crinfoList).hasSize(databaseSizeBeforeUpdate);
        Crinfo testCrinfo = crinfoList.get(crinfoList.size() - 1);
        assertThat(testCrinfo.getOperatetime()).isEqualTo(DEFAULT_OPERATETIME);
        assertThat(testCrinfo.getOldrent()).isEqualByComparingTo(UPDATED_OLDRENT);
        assertThat(testCrinfo.getNewrent()).isEqualByComparingTo(DEFAULT_NEWRENT);
        assertThat(testCrinfo.getOldroomn()).isEqualTo(DEFAULT_OLDROOMN);
        assertThat(testCrinfo.getNewroomn()).isEqualTo(UPDATED_NEWROOMN);
        assertThat(testCrinfo.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testCrinfo.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCrinfo.getOldday()).isEqualTo(UPDATED_OLDDAY);
        assertThat(testCrinfo.getNewday()).isEqualTo(DEFAULT_NEWDAY);
        assertThat(testCrinfo.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCrinfo.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testCrinfo.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testCrinfo.getRealname()).isEqualTo(DEFAULT_REALNAME);
        assertThat(testCrinfo.getIsup()).isEqualTo(DEFAULT_ISUP);
    }

    @Test
    @Transactional
    void fullUpdateCrinfoWithPatch() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        int databaseSizeBeforeUpdate = crinfoRepository.findAll().size();

        // Update the crinfo using partial update
        Crinfo partialUpdatedCrinfo = new Crinfo();
        partialUpdatedCrinfo.setId(crinfo.getId());

        partialUpdatedCrinfo
            .operatetime(UPDATED_OPERATETIME)
            .oldrent(UPDATED_OLDRENT)
            .newrent(UPDATED_NEWRENT)
            .oldroomn(UPDATED_OLDROOMN)
            .newroomn(UPDATED_NEWROOMN)
            .account(UPDATED_ACCOUNT)
            .empn(UPDATED_EMPN)
            .oldday(UPDATED_OLDDAY)
            .newday(UPDATED_NEWDAY)
            .hoteltime(UPDATED_HOTELTIME)
            .roomn(UPDATED_ROOMN)
            .memo(UPDATED_MEMO)
            .realname(UPDATED_REALNAME)
            .isup(UPDATED_ISUP);

        restCrinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrinfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCrinfo))
            )
            .andExpect(status().isOk());

        // Validate the Crinfo in the database
        List<Crinfo> crinfoList = crinfoRepository.findAll();
        assertThat(crinfoList).hasSize(databaseSizeBeforeUpdate);
        Crinfo testCrinfo = crinfoList.get(crinfoList.size() - 1);
        assertThat(testCrinfo.getOperatetime()).isEqualTo(UPDATED_OPERATETIME);
        assertThat(testCrinfo.getOldrent()).isEqualByComparingTo(UPDATED_OLDRENT);
        assertThat(testCrinfo.getNewrent()).isEqualByComparingTo(UPDATED_NEWRENT);
        assertThat(testCrinfo.getOldroomn()).isEqualTo(UPDATED_OLDROOMN);
        assertThat(testCrinfo.getNewroomn()).isEqualTo(UPDATED_NEWROOMN);
        assertThat(testCrinfo.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCrinfo.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCrinfo.getOldday()).isEqualTo(UPDATED_OLDDAY);
        assertThat(testCrinfo.getNewday()).isEqualTo(UPDATED_NEWDAY);
        assertThat(testCrinfo.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCrinfo.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCrinfo.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testCrinfo.getRealname()).isEqualTo(UPDATED_REALNAME);
        assertThat(testCrinfo.getIsup()).isEqualTo(UPDATED_ISUP);
    }

    @Test
    @Transactional
    void patchNonExistingCrinfo() throws Exception {
        int databaseSizeBeforeUpdate = crinfoRepository.findAll().size();
        crinfo.setId(count.incrementAndGet());

        // Create the Crinfo
        CrinfoDTO crinfoDTO = crinfoMapper.toDto(crinfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, crinfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(crinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crinfo in the database
        List<Crinfo> crinfoList = crinfoRepository.findAll();
        assertThat(crinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Crinfo in Elasticsearch
        verify(mockCrinfoSearchRepository, times(0)).save(crinfo);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCrinfo() throws Exception {
        int databaseSizeBeforeUpdate = crinfoRepository.findAll().size();
        crinfo.setId(count.incrementAndGet());

        // Create the Crinfo
        CrinfoDTO crinfoDTO = crinfoMapper.toDto(crinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(crinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Crinfo in the database
        List<Crinfo> crinfoList = crinfoRepository.findAll();
        assertThat(crinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Crinfo in Elasticsearch
        verify(mockCrinfoSearchRepository, times(0)).save(crinfo);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCrinfo() throws Exception {
        int databaseSizeBeforeUpdate = crinfoRepository.findAll().size();
        crinfo.setId(count.incrementAndGet());

        // Create the Crinfo
        CrinfoDTO crinfoDTO = crinfoMapper.toDto(crinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrinfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(crinfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Crinfo in the database
        List<Crinfo> crinfoList = crinfoRepository.findAll();
        assertThat(crinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Crinfo in Elasticsearch
        verify(mockCrinfoSearchRepository, times(0)).save(crinfo);
    }

    @Test
    @Transactional
    void deleteCrinfo() throws Exception {
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);

        int databaseSizeBeforeDelete = crinfoRepository.findAll().size();

        // Delete the crinfo
        restCrinfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, crinfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Crinfo> crinfoList = crinfoRepository.findAll();
        assertThat(crinfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Crinfo in Elasticsearch
        verify(mockCrinfoSearchRepository, times(1)).deleteById(crinfo.getId());
    }

    @Test
    @Transactional
    void searchCrinfo() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        crinfoRepository.saveAndFlush(crinfo);
        when(mockCrinfoSearchRepository.search(queryStringQuery("id:" + crinfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(crinfo), PageRequest.of(0, 1), 1));

        // Search the crinfo
        restCrinfoMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + crinfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crinfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].operatetime").value(hasItem(DEFAULT_OPERATETIME.toString())))
            .andExpect(jsonPath("$.[*].oldrent").value(hasItem(sameNumber(DEFAULT_OLDRENT))))
            .andExpect(jsonPath("$.[*].newrent").value(hasItem(sameNumber(DEFAULT_NEWRENT))))
            .andExpect(jsonPath("$.[*].oldroomn").value(hasItem(DEFAULT_OLDROOMN)))
            .andExpect(jsonPath("$.[*].newroomn").value(hasItem(DEFAULT_NEWROOMN)))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].oldday").value(hasItem(DEFAULT_OLDDAY.intValue())))
            .andExpect(jsonPath("$.[*].newday").value(hasItem(DEFAULT_NEWDAY.intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].realname").value(hasItem(DEFAULT_REALNAME)))
            .andExpect(jsonPath("$.[*].isup").value(hasItem(DEFAULT_ISUP.intValue())));
    }
}
