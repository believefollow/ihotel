package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DXs;
import ihotel.app.repository.DXsRepository;
import ihotel.app.repository.search.DXsSearchRepository;
import ihotel.app.service.criteria.DXsCriteria;
import ihotel.app.service.dto.DXsDTO;
import ihotel.app.service.mapper.DXsMapper;
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
 * Integration tests for the {@link DXsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DXsResourceIT {

    private static final Instant DEFAULT_BEGINTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BEGINTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CKBILLNO = "AAAAAAAAAA";
    private static final String UPDATED_CKBILLNO = "BBBBBBBBBB";

    private static final String DEFAULT_DEPOT = "AAAAAAAAAA";
    private static final String UPDATED_DEPOT = "BBBBBBBBBB";

    private static final Long DEFAULT_KCID = 1L;
    private static final Long UPDATED_KCID = 2L;
    private static final Long SMALLER_KCID = 1L - 1L;

    private static final Long DEFAULT_CKID = 1L;
    private static final Long UPDATED_CKID = 2L;
    private static final Long SMALLER_CKID = 1L - 1L;

    private static final String DEFAULT_SPBM = "AAAAAAAAAA";
    private static final String UPDATED_SPBM = "BBBBBBBBBB";

    private static final String DEFAULT_SPMC = "AAAAAAAAAA";
    private static final String UPDATED_SPMC = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_RKPRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RKPRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_RKPRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_XSPRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_XSPRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_XSPRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SL = new BigDecimal(2);
    private static final BigDecimal SMALLER_SL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_RKJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RKJE = new BigDecimal(2);
    private static final BigDecimal SMALLER_RKJE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_XSJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_XSJE = new BigDecimal(2);
    private static final BigDecimal SMALLER_XSJE = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/d-xs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-xs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DXsRepository dXsRepository;

    @Autowired
    private DXsMapper dXsMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DXsSearchRepositoryMockConfiguration
     */
    @Autowired
    private DXsSearchRepository mockDXsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDXsMockMvc;

    private DXs dXs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DXs createEntity(EntityManager em) {
        DXs dXs = new DXs()
            .begintime(DEFAULT_BEGINTIME)
            .endtime(DEFAULT_ENDTIME)
            .ckbillno(DEFAULT_CKBILLNO)
            .depot(DEFAULT_DEPOT)
            .kcid(DEFAULT_KCID)
            .ckid(DEFAULT_CKID)
            .spbm(DEFAULT_SPBM)
            .spmc(DEFAULT_SPMC)
            .unit(DEFAULT_UNIT)
            .rkprice(DEFAULT_RKPRICE)
            .xsprice(DEFAULT_XSPRICE)
            .sl(DEFAULT_SL)
            .rkje(DEFAULT_RKJE)
            .xsje(DEFAULT_XSJE);
        return dXs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DXs createUpdatedEntity(EntityManager em) {
        DXs dXs = new DXs()
            .begintime(UPDATED_BEGINTIME)
            .endtime(UPDATED_ENDTIME)
            .ckbillno(UPDATED_CKBILLNO)
            .depot(UPDATED_DEPOT)
            .kcid(UPDATED_KCID)
            .ckid(UPDATED_CKID)
            .spbm(UPDATED_SPBM)
            .spmc(UPDATED_SPMC)
            .unit(UPDATED_UNIT)
            .rkprice(UPDATED_RKPRICE)
            .xsprice(UPDATED_XSPRICE)
            .sl(UPDATED_SL)
            .rkje(UPDATED_RKJE)
            .xsje(UPDATED_XSJE);
        return dXs;
    }

    @BeforeEach
    public void initTest() {
        dXs = createEntity(em);
    }

    @Test
    @Transactional
    void createDXs() throws Exception {
        int databaseSizeBeforeCreate = dXsRepository.findAll().size();
        // Create the DXs
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);
        restDXsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dXsDTO)))
            .andExpect(status().isCreated());

        // Validate the DXs in the database
        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeCreate + 1);
        DXs testDXs = dXsList.get(dXsList.size() - 1);
        assertThat(testDXs.getBegintime()).isEqualTo(DEFAULT_BEGINTIME);
        assertThat(testDXs.getEndtime()).isEqualTo(DEFAULT_ENDTIME);
        assertThat(testDXs.getCkbillno()).isEqualTo(DEFAULT_CKBILLNO);
        assertThat(testDXs.getDepot()).isEqualTo(DEFAULT_DEPOT);
        assertThat(testDXs.getKcid()).isEqualTo(DEFAULT_KCID);
        assertThat(testDXs.getCkid()).isEqualTo(DEFAULT_CKID);
        assertThat(testDXs.getSpbm()).isEqualTo(DEFAULT_SPBM);
        assertThat(testDXs.getSpmc()).isEqualTo(DEFAULT_SPMC);
        assertThat(testDXs.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testDXs.getRkprice()).isEqualByComparingTo(DEFAULT_RKPRICE);
        assertThat(testDXs.getXsprice()).isEqualByComparingTo(DEFAULT_XSPRICE);
        assertThat(testDXs.getSl()).isEqualByComparingTo(DEFAULT_SL);
        assertThat(testDXs.getRkje()).isEqualByComparingTo(DEFAULT_RKJE);
        assertThat(testDXs.getXsje()).isEqualByComparingTo(DEFAULT_XSJE);

        // Validate the DXs in Elasticsearch
        verify(mockDXsSearchRepository, times(1)).save(testDXs);
    }

    @Test
    @Transactional
    void createDXsWithExistingId() throws Exception {
        // Create the DXs with an existing ID
        dXs.setId(1L);
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);

        int databaseSizeBeforeCreate = dXsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDXsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dXsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DXs in the database
        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeCreate);

        // Validate the DXs in Elasticsearch
        verify(mockDXsSearchRepository, times(0)).save(dXs);
    }

    @Test
    @Transactional
    void checkBegintimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dXsRepository.findAll().size();
        // set the field null
        dXs.setBegintime(null);

        // Create the DXs, which fails.
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);

        restDXsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dXsDTO)))
            .andExpect(status().isBadRequest());

        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndtimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dXsRepository.findAll().size();
        // set the field null
        dXs.setEndtime(null);

        // Create the DXs, which fails.
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);

        restDXsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dXsDTO)))
            .andExpect(status().isBadRequest());

        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCkbillnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dXsRepository.findAll().size();
        // set the field null
        dXs.setCkbillno(null);

        // Create the DXs, which fails.
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);

        restDXsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dXsDTO)))
            .andExpect(status().isBadRequest());

        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkKcidIsRequired() throws Exception {
        int databaseSizeBeforeTest = dXsRepository.findAll().size();
        // set the field null
        dXs.setKcid(null);

        // Create the DXs, which fails.
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);

        restDXsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dXsDTO)))
            .andExpect(status().isBadRequest());

        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCkidIsRequired() throws Exception {
        int databaseSizeBeforeTest = dXsRepository.findAll().size();
        // set the field null
        dXs.setCkid(null);

        // Create the DXs, which fails.
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);

        restDXsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dXsDTO)))
            .andExpect(status().isBadRequest());

        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpbmIsRequired() throws Exception {
        int databaseSizeBeforeTest = dXsRepository.findAll().size();
        // set the field null
        dXs.setSpbm(null);

        // Create the DXs, which fails.
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);

        restDXsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dXsDTO)))
            .andExpect(status().isBadRequest());

        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpmcIsRequired() throws Exception {
        int databaseSizeBeforeTest = dXsRepository.findAll().size();
        // set the field null
        dXs.setSpmc(null);

        // Create the DXs, which fails.
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);

        restDXsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dXsDTO)))
            .andExpect(status().isBadRequest());

        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDXs() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList
        restDXsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dXs.getId().intValue())))
            .andExpect(jsonPath("$.[*].begintime").value(hasItem(DEFAULT_BEGINTIME.toString())))
            .andExpect(jsonPath("$.[*].endtime").value(hasItem(DEFAULT_ENDTIME.toString())))
            .andExpect(jsonPath("$.[*].ckbillno").value(hasItem(DEFAULT_CKBILLNO)))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].kcid").value(hasItem(DEFAULT_KCID.intValue())))
            .andExpect(jsonPath("$.[*].ckid").value(hasItem(DEFAULT_CKID.intValue())))
            .andExpect(jsonPath("$.[*].spbm").value(hasItem(DEFAULT_SPBM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].rkprice").value(hasItem(sameNumber(DEFAULT_RKPRICE))))
            .andExpect(jsonPath("$.[*].xsprice").value(hasItem(sameNumber(DEFAULT_XSPRICE))))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].rkje").value(hasItem(sameNumber(DEFAULT_RKJE))))
            .andExpect(jsonPath("$.[*].xsje").value(hasItem(sameNumber(DEFAULT_XSJE))));
    }

    @Test
    @Transactional
    void getDXs() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get the dXs
        restDXsMockMvc
            .perform(get(ENTITY_API_URL_ID, dXs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dXs.getId().intValue()))
            .andExpect(jsonPath("$.begintime").value(DEFAULT_BEGINTIME.toString()))
            .andExpect(jsonPath("$.endtime").value(DEFAULT_ENDTIME.toString()))
            .andExpect(jsonPath("$.ckbillno").value(DEFAULT_CKBILLNO))
            .andExpect(jsonPath("$.depot").value(DEFAULT_DEPOT))
            .andExpect(jsonPath("$.kcid").value(DEFAULT_KCID.intValue()))
            .andExpect(jsonPath("$.ckid").value(DEFAULT_CKID.intValue()))
            .andExpect(jsonPath("$.spbm").value(DEFAULT_SPBM))
            .andExpect(jsonPath("$.spmc").value(DEFAULT_SPMC))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.rkprice").value(sameNumber(DEFAULT_RKPRICE)))
            .andExpect(jsonPath("$.xsprice").value(sameNumber(DEFAULT_XSPRICE)))
            .andExpect(jsonPath("$.sl").value(sameNumber(DEFAULT_SL)))
            .andExpect(jsonPath("$.rkje").value(sameNumber(DEFAULT_RKJE)))
            .andExpect(jsonPath("$.xsje").value(sameNumber(DEFAULT_XSJE)));
    }

    @Test
    @Transactional
    void getDXsByIdFiltering() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        Long id = dXs.getId();

        defaultDXsShouldBeFound("id.equals=" + id);
        defaultDXsShouldNotBeFound("id.notEquals=" + id);

        defaultDXsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDXsShouldNotBeFound("id.greaterThan=" + id);

        defaultDXsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDXsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDXsByBegintimeIsEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where begintime equals to DEFAULT_BEGINTIME
        defaultDXsShouldBeFound("begintime.equals=" + DEFAULT_BEGINTIME);

        // Get all the dXsList where begintime equals to UPDATED_BEGINTIME
        defaultDXsShouldNotBeFound("begintime.equals=" + UPDATED_BEGINTIME);
    }

    @Test
    @Transactional
    void getAllDXsByBegintimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where begintime not equals to DEFAULT_BEGINTIME
        defaultDXsShouldNotBeFound("begintime.notEquals=" + DEFAULT_BEGINTIME);

        // Get all the dXsList where begintime not equals to UPDATED_BEGINTIME
        defaultDXsShouldBeFound("begintime.notEquals=" + UPDATED_BEGINTIME);
    }

    @Test
    @Transactional
    void getAllDXsByBegintimeIsInShouldWork() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where begintime in DEFAULT_BEGINTIME or UPDATED_BEGINTIME
        defaultDXsShouldBeFound("begintime.in=" + DEFAULT_BEGINTIME + "," + UPDATED_BEGINTIME);

        // Get all the dXsList where begintime equals to UPDATED_BEGINTIME
        defaultDXsShouldNotBeFound("begintime.in=" + UPDATED_BEGINTIME);
    }

    @Test
    @Transactional
    void getAllDXsByBegintimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where begintime is not null
        defaultDXsShouldBeFound("begintime.specified=true");

        // Get all the dXsList where begintime is null
        defaultDXsShouldNotBeFound("begintime.specified=false");
    }

    @Test
    @Transactional
    void getAllDXsByEndtimeIsEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where endtime equals to DEFAULT_ENDTIME
        defaultDXsShouldBeFound("endtime.equals=" + DEFAULT_ENDTIME);

        // Get all the dXsList where endtime equals to UPDATED_ENDTIME
        defaultDXsShouldNotBeFound("endtime.equals=" + UPDATED_ENDTIME);
    }

    @Test
    @Transactional
    void getAllDXsByEndtimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where endtime not equals to DEFAULT_ENDTIME
        defaultDXsShouldNotBeFound("endtime.notEquals=" + DEFAULT_ENDTIME);

        // Get all the dXsList where endtime not equals to UPDATED_ENDTIME
        defaultDXsShouldBeFound("endtime.notEquals=" + UPDATED_ENDTIME);
    }

    @Test
    @Transactional
    void getAllDXsByEndtimeIsInShouldWork() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where endtime in DEFAULT_ENDTIME or UPDATED_ENDTIME
        defaultDXsShouldBeFound("endtime.in=" + DEFAULT_ENDTIME + "," + UPDATED_ENDTIME);

        // Get all the dXsList where endtime equals to UPDATED_ENDTIME
        defaultDXsShouldNotBeFound("endtime.in=" + UPDATED_ENDTIME);
    }

    @Test
    @Transactional
    void getAllDXsByEndtimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where endtime is not null
        defaultDXsShouldBeFound("endtime.specified=true");

        // Get all the dXsList where endtime is null
        defaultDXsShouldNotBeFound("endtime.specified=false");
    }

    @Test
    @Transactional
    void getAllDXsByCkbillnoIsEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where ckbillno equals to DEFAULT_CKBILLNO
        defaultDXsShouldBeFound("ckbillno.equals=" + DEFAULT_CKBILLNO);

        // Get all the dXsList where ckbillno equals to UPDATED_CKBILLNO
        defaultDXsShouldNotBeFound("ckbillno.equals=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDXsByCkbillnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where ckbillno not equals to DEFAULT_CKBILLNO
        defaultDXsShouldNotBeFound("ckbillno.notEquals=" + DEFAULT_CKBILLNO);

        // Get all the dXsList where ckbillno not equals to UPDATED_CKBILLNO
        defaultDXsShouldBeFound("ckbillno.notEquals=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDXsByCkbillnoIsInShouldWork() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where ckbillno in DEFAULT_CKBILLNO or UPDATED_CKBILLNO
        defaultDXsShouldBeFound("ckbillno.in=" + DEFAULT_CKBILLNO + "," + UPDATED_CKBILLNO);

        // Get all the dXsList where ckbillno equals to UPDATED_CKBILLNO
        defaultDXsShouldNotBeFound("ckbillno.in=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDXsByCkbillnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where ckbillno is not null
        defaultDXsShouldBeFound("ckbillno.specified=true");

        // Get all the dXsList where ckbillno is null
        defaultDXsShouldNotBeFound("ckbillno.specified=false");
    }

    @Test
    @Transactional
    void getAllDXsByCkbillnoContainsSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where ckbillno contains DEFAULT_CKBILLNO
        defaultDXsShouldBeFound("ckbillno.contains=" + DEFAULT_CKBILLNO);

        // Get all the dXsList where ckbillno contains UPDATED_CKBILLNO
        defaultDXsShouldNotBeFound("ckbillno.contains=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDXsByCkbillnoNotContainsSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where ckbillno does not contain DEFAULT_CKBILLNO
        defaultDXsShouldNotBeFound("ckbillno.doesNotContain=" + DEFAULT_CKBILLNO);

        // Get all the dXsList where ckbillno does not contain UPDATED_CKBILLNO
        defaultDXsShouldBeFound("ckbillno.doesNotContain=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDXsByDepotIsEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where depot equals to DEFAULT_DEPOT
        defaultDXsShouldBeFound("depot.equals=" + DEFAULT_DEPOT);

        // Get all the dXsList where depot equals to UPDATED_DEPOT
        defaultDXsShouldNotBeFound("depot.equals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDXsByDepotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where depot not equals to DEFAULT_DEPOT
        defaultDXsShouldNotBeFound("depot.notEquals=" + DEFAULT_DEPOT);

        // Get all the dXsList where depot not equals to UPDATED_DEPOT
        defaultDXsShouldBeFound("depot.notEquals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDXsByDepotIsInShouldWork() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where depot in DEFAULT_DEPOT or UPDATED_DEPOT
        defaultDXsShouldBeFound("depot.in=" + DEFAULT_DEPOT + "," + UPDATED_DEPOT);

        // Get all the dXsList where depot equals to UPDATED_DEPOT
        defaultDXsShouldNotBeFound("depot.in=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDXsByDepotIsNullOrNotNull() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where depot is not null
        defaultDXsShouldBeFound("depot.specified=true");

        // Get all the dXsList where depot is null
        defaultDXsShouldNotBeFound("depot.specified=false");
    }

    @Test
    @Transactional
    void getAllDXsByDepotContainsSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where depot contains DEFAULT_DEPOT
        defaultDXsShouldBeFound("depot.contains=" + DEFAULT_DEPOT);

        // Get all the dXsList where depot contains UPDATED_DEPOT
        defaultDXsShouldNotBeFound("depot.contains=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDXsByDepotNotContainsSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where depot does not contain DEFAULT_DEPOT
        defaultDXsShouldNotBeFound("depot.doesNotContain=" + DEFAULT_DEPOT);

        // Get all the dXsList where depot does not contain UPDATED_DEPOT
        defaultDXsShouldBeFound("depot.doesNotContain=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDXsByKcidIsEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where kcid equals to DEFAULT_KCID
        defaultDXsShouldBeFound("kcid.equals=" + DEFAULT_KCID);

        // Get all the dXsList where kcid equals to UPDATED_KCID
        defaultDXsShouldNotBeFound("kcid.equals=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDXsByKcidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where kcid not equals to DEFAULT_KCID
        defaultDXsShouldNotBeFound("kcid.notEquals=" + DEFAULT_KCID);

        // Get all the dXsList where kcid not equals to UPDATED_KCID
        defaultDXsShouldBeFound("kcid.notEquals=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDXsByKcidIsInShouldWork() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where kcid in DEFAULT_KCID or UPDATED_KCID
        defaultDXsShouldBeFound("kcid.in=" + DEFAULT_KCID + "," + UPDATED_KCID);

        // Get all the dXsList where kcid equals to UPDATED_KCID
        defaultDXsShouldNotBeFound("kcid.in=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDXsByKcidIsNullOrNotNull() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where kcid is not null
        defaultDXsShouldBeFound("kcid.specified=true");

        // Get all the dXsList where kcid is null
        defaultDXsShouldNotBeFound("kcid.specified=false");
    }

    @Test
    @Transactional
    void getAllDXsByKcidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where kcid is greater than or equal to DEFAULT_KCID
        defaultDXsShouldBeFound("kcid.greaterThanOrEqual=" + DEFAULT_KCID);

        // Get all the dXsList where kcid is greater than or equal to UPDATED_KCID
        defaultDXsShouldNotBeFound("kcid.greaterThanOrEqual=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDXsByKcidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where kcid is less than or equal to DEFAULT_KCID
        defaultDXsShouldBeFound("kcid.lessThanOrEqual=" + DEFAULT_KCID);

        // Get all the dXsList where kcid is less than or equal to SMALLER_KCID
        defaultDXsShouldNotBeFound("kcid.lessThanOrEqual=" + SMALLER_KCID);
    }

    @Test
    @Transactional
    void getAllDXsByKcidIsLessThanSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where kcid is less than DEFAULT_KCID
        defaultDXsShouldNotBeFound("kcid.lessThan=" + DEFAULT_KCID);

        // Get all the dXsList where kcid is less than UPDATED_KCID
        defaultDXsShouldBeFound("kcid.lessThan=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDXsByKcidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where kcid is greater than DEFAULT_KCID
        defaultDXsShouldNotBeFound("kcid.greaterThan=" + DEFAULT_KCID);

        // Get all the dXsList where kcid is greater than SMALLER_KCID
        defaultDXsShouldBeFound("kcid.greaterThan=" + SMALLER_KCID);
    }

    @Test
    @Transactional
    void getAllDXsByCkidIsEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where ckid equals to DEFAULT_CKID
        defaultDXsShouldBeFound("ckid.equals=" + DEFAULT_CKID);

        // Get all the dXsList where ckid equals to UPDATED_CKID
        defaultDXsShouldNotBeFound("ckid.equals=" + UPDATED_CKID);
    }

    @Test
    @Transactional
    void getAllDXsByCkidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where ckid not equals to DEFAULT_CKID
        defaultDXsShouldNotBeFound("ckid.notEquals=" + DEFAULT_CKID);

        // Get all the dXsList where ckid not equals to UPDATED_CKID
        defaultDXsShouldBeFound("ckid.notEquals=" + UPDATED_CKID);
    }

    @Test
    @Transactional
    void getAllDXsByCkidIsInShouldWork() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where ckid in DEFAULT_CKID or UPDATED_CKID
        defaultDXsShouldBeFound("ckid.in=" + DEFAULT_CKID + "," + UPDATED_CKID);

        // Get all the dXsList where ckid equals to UPDATED_CKID
        defaultDXsShouldNotBeFound("ckid.in=" + UPDATED_CKID);
    }

    @Test
    @Transactional
    void getAllDXsByCkidIsNullOrNotNull() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where ckid is not null
        defaultDXsShouldBeFound("ckid.specified=true");

        // Get all the dXsList where ckid is null
        defaultDXsShouldNotBeFound("ckid.specified=false");
    }

    @Test
    @Transactional
    void getAllDXsByCkidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where ckid is greater than or equal to DEFAULT_CKID
        defaultDXsShouldBeFound("ckid.greaterThanOrEqual=" + DEFAULT_CKID);

        // Get all the dXsList where ckid is greater than or equal to UPDATED_CKID
        defaultDXsShouldNotBeFound("ckid.greaterThanOrEqual=" + UPDATED_CKID);
    }

    @Test
    @Transactional
    void getAllDXsByCkidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where ckid is less than or equal to DEFAULT_CKID
        defaultDXsShouldBeFound("ckid.lessThanOrEqual=" + DEFAULT_CKID);

        // Get all the dXsList where ckid is less than or equal to SMALLER_CKID
        defaultDXsShouldNotBeFound("ckid.lessThanOrEqual=" + SMALLER_CKID);
    }

    @Test
    @Transactional
    void getAllDXsByCkidIsLessThanSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where ckid is less than DEFAULT_CKID
        defaultDXsShouldNotBeFound("ckid.lessThan=" + DEFAULT_CKID);

        // Get all the dXsList where ckid is less than UPDATED_CKID
        defaultDXsShouldBeFound("ckid.lessThan=" + UPDATED_CKID);
    }

    @Test
    @Transactional
    void getAllDXsByCkidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where ckid is greater than DEFAULT_CKID
        defaultDXsShouldNotBeFound("ckid.greaterThan=" + DEFAULT_CKID);

        // Get all the dXsList where ckid is greater than SMALLER_CKID
        defaultDXsShouldBeFound("ckid.greaterThan=" + SMALLER_CKID);
    }

    @Test
    @Transactional
    void getAllDXsBySpbmIsEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where spbm equals to DEFAULT_SPBM
        defaultDXsShouldBeFound("spbm.equals=" + DEFAULT_SPBM);

        // Get all the dXsList where spbm equals to UPDATED_SPBM
        defaultDXsShouldNotBeFound("spbm.equals=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDXsBySpbmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where spbm not equals to DEFAULT_SPBM
        defaultDXsShouldNotBeFound("spbm.notEquals=" + DEFAULT_SPBM);

        // Get all the dXsList where spbm not equals to UPDATED_SPBM
        defaultDXsShouldBeFound("spbm.notEquals=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDXsBySpbmIsInShouldWork() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where spbm in DEFAULT_SPBM or UPDATED_SPBM
        defaultDXsShouldBeFound("spbm.in=" + DEFAULT_SPBM + "," + UPDATED_SPBM);

        // Get all the dXsList where spbm equals to UPDATED_SPBM
        defaultDXsShouldNotBeFound("spbm.in=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDXsBySpbmIsNullOrNotNull() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where spbm is not null
        defaultDXsShouldBeFound("spbm.specified=true");

        // Get all the dXsList where spbm is null
        defaultDXsShouldNotBeFound("spbm.specified=false");
    }

    @Test
    @Transactional
    void getAllDXsBySpbmContainsSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where spbm contains DEFAULT_SPBM
        defaultDXsShouldBeFound("spbm.contains=" + DEFAULT_SPBM);

        // Get all the dXsList where spbm contains UPDATED_SPBM
        defaultDXsShouldNotBeFound("spbm.contains=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDXsBySpbmNotContainsSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where spbm does not contain DEFAULT_SPBM
        defaultDXsShouldNotBeFound("spbm.doesNotContain=" + DEFAULT_SPBM);

        // Get all the dXsList where spbm does not contain UPDATED_SPBM
        defaultDXsShouldBeFound("spbm.doesNotContain=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDXsBySpmcIsEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where spmc equals to DEFAULT_SPMC
        defaultDXsShouldBeFound("spmc.equals=" + DEFAULT_SPMC);

        // Get all the dXsList where spmc equals to UPDATED_SPMC
        defaultDXsShouldNotBeFound("spmc.equals=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDXsBySpmcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where spmc not equals to DEFAULT_SPMC
        defaultDXsShouldNotBeFound("spmc.notEquals=" + DEFAULT_SPMC);

        // Get all the dXsList where spmc not equals to UPDATED_SPMC
        defaultDXsShouldBeFound("spmc.notEquals=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDXsBySpmcIsInShouldWork() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where spmc in DEFAULT_SPMC or UPDATED_SPMC
        defaultDXsShouldBeFound("spmc.in=" + DEFAULT_SPMC + "," + UPDATED_SPMC);

        // Get all the dXsList where spmc equals to UPDATED_SPMC
        defaultDXsShouldNotBeFound("spmc.in=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDXsBySpmcIsNullOrNotNull() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where spmc is not null
        defaultDXsShouldBeFound("spmc.specified=true");

        // Get all the dXsList where spmc is null
        defaultDXsShouldNotBeFound("spmc.specified=false");
    }

    @Test
    @Transactional
    void getAllDXsBySpmcContainsSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where spmc contains DEFAULT_SPMC
        defaultDXsShouldBeFound("spmc.contains=" + DEFAULT_SPMC);

        // Get all the dXsList where spmc contains UPDATED_SPMC
        defaultDXsShouldNotBeFound("spmc.contains=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDXsBySpmcNotContainsSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where spmc does not contain DEFAULT_SPMC
        defaultDXsShouldNotBeFound("spmc.doesNotContain=" + DEFAULT_SPMC);

        // Get all the dXsList where spmc does not contain UPDATED_SPMC
        defaultDXsShouldBeFound("spmc.doesNotContain=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDXsByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where unit equals to DEFAULT_UNIT
        defaultDXsShouldBeFound("unit.equals=" + DEFAULT_UNIT);

        // Get all the dXsList where unit equals to UPDATED_UNIT
        defaultDXsShouldNotBeFound("unit.equals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDXsByUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where unit not equals to DEFAULT_UNIT
        defaultDXsShouldNotBeFound("unit.notEquals=" + DEFAULT_UNIT);

        // Get all the dXsList where unit not equals to UPDATED_UNIT
        defaultDXsShouldBeFound("unit.notEquals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDXsByUnitIsInShouldWork() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where unit in DEFAULT_UNIT or UPDATED_UNIT
        defaultDXsShouldBeFound("unit.in=" + DEFAULT_UNIT + "," + UPDATED_UNIT);

        // Get all the dXsList where unit equals to UPDATED_UNIT
        defaultDXsShouldNotBeFound("unit.in=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDXsByUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where unit is not null
        defaultDXsShouldBeFound("unit.specified=true");

        // Get all the dXsList where unit is null
        defaultDXsShouldNotBeFound("unit.specified=false");
    }

    @Test
    @Transactional
    void getAllDXsByUnitContainsSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where unit contains DEFAULT_UNIT
        defaultDXsShouldBeFound("unit.contains=" + DEFAULT_UNIT);

        // Get all the dXsList where unit contains UPDATED_UNIT
        defaultDXsShouldNotBeFound("unit.contains=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDXsByUnitNotContainsSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where unit does not contain DEFAULT_UNIT
        defaultDXsShouldNotBeFound("unit.doesNotContain=" + DEFAULT_UNIT);

        // Get all the dXsList where unit does not contain UPDATED_UNIT
        defaultDXsShouldBeFound("unit.doesNotContain=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDXsByRkpriceIsEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkprice equals to DEFAULT_RKPRICE
        defaultDXsShouldBeFound("rkprice.equals=" + DEFAULT_RKPRICE);

        // Get all the dXsList where rkprice equals to UPDATED_RKPRICE
        defaultDXsShouldNotBeFound("rkprice.equals=" + UPDATED_RKPRICE);
    }

    @Test
    @Transactional
    void getAllDXsByRkpriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkprice not equals to DEFAULT_RKPRICE
        defaultDXsShouldNotBeFound("rkprice.notEquals=" + DEFAULT_RKPRICE);

        // Get all the dXsList where rkprice not equals to UPDATED_RKPRICE
        defaultDXsShouldBeFound("rkprice.notEquals=" + UPDATED_RKPRICE);
    }

    @Test
    @Transactional
    void getAllDXsByRkpriceIsInShouldWork() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkprice in DEFAULT_RKPRICE or UPDATED_RKPRICE
        defaultDXsShouldBeFound("rkprice.in=" + DEFAULT_RKPRICE + "," + UPDATED_RKPRICE);

        // Get all the dXsList where rkprice equals to UPDATED_RKPRICE
        defaultDXsShouldNotBeFound("rkprice.in=" + UPDATED_RKPRICE);
    }

    @Test
    @Transactional
    void getAllDXsByRkpriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkprice is not null
        defaultDXsShouldBeFound("rkprice.specified=true");

        // Get all the dXsList where rkprice is null
        defaultDXsShouldNotBeFound("rkprice.specified=false");
    }

    @Test
    @Transactional
    void getAllDXsByRkpriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkprice is greater than or equal to DEFAULT_RKPRICE
        defaultDXsShouldBeFound("rkprice.greaterThanOrEqual=" + DEFAULT_RKPRICE);

        // Get all the dXsList where rkprice is greater than or equal to UPDATED_RKPRICE
        defaultDXsShouldNotBeFound("rkprice.greaterThanOrEqual=" + UPDATED_RKPRICE);
    }

    @Test
    @Transactional
    void getAllDXsByRkpriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkprice is less than or equal to DEFAULT_RKPRICE
        defaultDXsShouldBeFound("rkprice.lessThanOrEqual=" + DEFAULT_RKPRICE);

        // Get all the dXsList where rkprice is less than or equal to SMALLER_RKPRICE
        defaultDXsShouldNotBeFound("rkprice.lessThanOrEqual=" + SMALLER_RKPRICE);
    }

    @Test
    @Transactional
    void getAllDXsByRkpriceIsLessThanSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkprice is less than DEFAULT_RKPRICE
        defaultDXsShouldNotBeFound("rkprice.lessThan=" + DEFAULT_RKPRICE);

        // Get all the dXsList where rkprice is less than UPDATED_RKPRICE
        defaultDXsShouldBeFound("rkprice.lessThan=" + UPDATED_RKPRICE);
    }

    @Test
    @Transactional
    void getAllDXsByRkpriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkprice is greater than DEFAULT_RKPRICE
        defaultDXsShouldNotBeFound("rkprice.greaterThan=" + DEFAULT_RKPRICE);

        // Get all the dXsList where rkprice is greater than SMALLER_RKPRICE
        defaultDXsShouldBeFound("rkprice.greaterThan=" + SMALLER_RKPRICE);
    }

    @Test
    @Transactional
    void getAllDXsByXspriceIsEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsprice equals to DEFAULT_XSPRICE
        defaultDXsShouldBeFound("xsprice.equals=" + DEFAULT_XSPRICE);

        // Get all the dXsList where xsprice equals to UPDATED_XSPRICE
        defaultDXsShouldNotBeFound("xsprice.equals=" + UPDATED_XSPRICE);
    }

    @Test
    @Transactional
    void getAllDXsByXspriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsprice not equals to DEFAULT_XSPRICE
        defaultDXsShouldNotBeFound("xsprice.notEquals=" + DEFAULT_XSPRICE);

        // Get all the dXsList where xsprice not equals to UPDATED_XSPRICE
        defaultDXsShouldBeFound("xsprice.notEquals=" + UPDATED_XSPRICE);
    }

    @Test
    @Transactional
    void getAllDXsByXspriceIsInShouldWork() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsprice in DEFAULT_XSPRICE or UPDATED_XSPRICE
        defaultDXsShouldBeFound("xsprice.in=" + DEFAULT_XSPRICE + "," + UPDATED_XSPRICE);

        // Get all the dXsList where xsprice equals to UPDATED_XSPRICE
        defaultDXsShouldNotBeFound("xsprice.in=" + UPDATED_XSPRICE);
    }

    @Test
    @Transactional
    void getAllDXsByXspriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsprice is not null
        defaultDXsShouldBeFound("xsprice.specified=true");

        // Get all the dXsList where xsprice is null
        defaultDXsShouldNotBeFound("xsprice.specified=false");
    }

    @Test
    @Transactional
    void getAllDXsByXspriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsprice is greater than or equal to DEFAULT_XSPRICE
        defaultDXsShouldBeFound("xsprice.greaterThanOrEqual=" + DEFAULT_XSPRICE);

        // Get all the dXsList where xsprice is greater than or equal to UPDATED_XSPRICE
        defaultDXsShouldNotBeFound("xsprice.greaterThanOrEqual=" + UPDATED_XSPRICE);
    }

    @Test
    @Transactional
    void getAllDXsByXspriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsprice is less than or equal to DEFAULT_XSPRICE
        defaultDXsShouldBeFound("xsprice.lessThanOrEqual=" + DEFAULT_XSPRICE);

        // Get all the dXsList where xsprice is less than or equal to SMALLER_XSPRICE
        defaultDXsShouldNotBeFound("xsprice.lessThanOrEqual=" + SMALLER_XSPRICE);
    }

    @Test
    @Transactional
    void getAllDXsByXspriceIsLessThanSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsprice is less than DEFAULT_XSPRICE
        defaultDXsShouldNotBeFound("xsprice.lessThan=" + DEFAULT_XSPRICE);

        // Get all the dXsList where xsprice is less than UPDATED_XSPRICE
        defaultDXsShouldBeFound("xsprice.lessThan=" + UPDATED_XSPRICE);
    }

    @Test
    @Transactional
    void getAllDXsByXspriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsprice is greater than DEFAULT_XSPRICE
        defaultDXsShouldNotBeFound("xsprice.greaterThan=" + DEFAULT_XSPRICE);

        // Get all the dXsList where xsprice is greater than SMALLER_XSPRICE
        defaultDXsShouldBeFound("xsprice.greaterThan=" + SMALLER_XSPRICE);
    }

    @Test
    @Transactional
    void getAllDXsBySlIsEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where sl equals to DEFAULT_SL
        defaultDXsShouldBeFound("sl.equals=" + DEFAULT_SL);

        // Get all the dXsList where sl equals to UPDATED_SL
        defaultDXsShouldNotBeFound("sl.equals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDXsBySlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where sl not equals to DEFAULT_SL
        defaultDXsShouldNotBeFound("sl.notEquals=" + DEFAULT_SL);

        // Get all the dXsList where sl not equals to UPDATED_SL
        defaultDXsShouldBeFound("sl.notEquals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDXsBySlIsInShouldWork() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where sl in DEFAULT_SL or UPDATED_SL
        defaultDXsShouldBeFound("sl.in=" + DEFAULT_SL + "," + UPDATED_SL);

        // Get all the dXsList where sl equals to UPDATED_SL
        defaultDXsShouldNotBeFound("sl.in=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDXsBySlIsNullOrNotNull() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where sl is not null
        defaultDXsShouldBeFound("sl.specified=true");

        // Get all the dXsList where sl is null
        defaultDXsShouldNotBeFound("sl.specified=false");
    }

    @Test
    @Transactional
    void getAllDXsBySlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where sl is greater than or equal to DEFAULT_SL
        defaultDXsShouldBeFound("sl.greaterThanOrEqual=" + DEFAULT_SL);

        // Get all the dXsList where sl is greater than or equal to UPDATED_SL
        defaultDXsShouldNotBeFound("sl.greaterThanOrEqual=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDXsBySlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where sl is less than or equal to DEFAULT_SL
        defaultDXsShouldBeFound("sl.lessThanOrEqual=" + DEFAULT_SL);

        // Get all the dXsList where sl is less than or equal to SMALLER_SL
        defaultDXsShouldNotBeFound("sl.lessThanOrEqual=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllDXsBySlIsLessThanSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where sl is less than DEFAULT_SL
        defaultDXsShouldNotBeFound("sl.lessThan=" + DEFAULT_SL);

        // Get all the dXsList where sl is less than UPDATED_SL
        defaultDXsShouldBeFound("sl.lessThan=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDXsBySlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where sl is greater than DEFAULT_SL
        defaultDXsShouldNotBeFound("sl.greaterThan=" + DEFAULT_SL);

        // Get all the dXsList where sl is greater than SMALLER_SL
        defaultDXsShouldBeFound("sl.greaterThan=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllDXsByRkjeIsEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkje equals to DEFAULT_RKJE
        defaultDXsShouldBeFound("rkje.equals=" + DEFAULT_RKJE);

        // Get all the dXsList where rkje equals to UPDATED_RKJE
        defaultDXsShouldNotBeFound("rkje.equals=" + UPDATED_RKJE);
    }

    @Test
    @Transactional
    void getAllDXsByRkjeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkje not equals to DEFAULT_RKJE
        defaultDXsShouldNotBeFound("rkje.notEquals=" + DEFAULT_RKJE);

        // Get all the dXsList where rkje not equals to UPDATED_RKJE
        defaultDXsShouldBeFound("rkje.notEquals=" + UPDATED_RKJE);
    }

    @Test
    @Transactional
    void getAllDXsByRkjeIsInShouldWork() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkje in DEFAULT_RKJE or UPDATED_RKJE
        defaultDXsShouldBeFound("rkje.in=" + DEFAULT_RKJE + "," + UPDATED_RKJE);

        // Get all the dXsList where rkje equals to UPDATED_RKJE
        defaultDXsShouldNotBeFound("rkje.in=" + UPDATED_RKJE);
    }

    @Test
    @Transactional
    void getAllDXsByRkjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkje is not null
        defaultDXsShouldBeFound("rkje.specified=true");

        // Get all the dXsList where rkje is null
        defaultDXsShouldNotBeFound("rkje.specified=false");
    }

    @Test
    @Transactional
    void getAllDXsByRkjeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkje is greater than or equal to DEFAULT_RKJE
        defaultDXsShouldBeFound("rkje.greaterThanOrEqual=" + DEFAULT_RKJE);

        // Get all the dXsList where rkje is greater than or equal to UPDATED_RKJE
        defaultDXsShouldNotBeFound("rkje.greaterThanOrEqual=" + UPDATED_RKJE);
    }

    @Test
    @Transactional
    void getAllDXsByRkjeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkje is less than or equal to DEFAULT_RKJE
        defaultDXsShouldBeFound("rkje.lessThanOrEqual=" + DEFAULT_RKJE);

        // Get all the dXsList where rkje is less than or equal to SMALLER_RKJE
        defaultDXsShouldNotBeFound("rkje.lessThanOrEqual=" + SMALLER_RKJE);
    }

    @Test
    @Transactional
    void getAllDXsByRkjeIsLessThanSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkje is less than DEFAULT_RKJE
        defaultDXsShouldNotBeFound("rkje.lessThan=" + DEFAULT_RKJE);

        // Get all the dXsList where rkje is less than UPDATED_RKJE
        defaultDXsShouldBeFound("rkje.lessThan=" + UPDATED_RKJE);
    }

    @Test
    @Transactional
    void getAllDXsByRkjeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where rkje is greater than DEFAULT_RKJE
        defaultDXsShouldNotBeFound("rkje.greaterThan=" + DEFAULT_RKJE);

        // Get all the dXsList where rkje is greater than SMALLER_RKJE
        defaultDXsShouldBeFound("rkje.greaterThan=" + SMALLER_RKJE);
    }

    @Test
    @Transactional
    void getAllDXsByXsjeIsEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsje equals to DEFAULT_XSJE
        defaultDXsShouldBeFound("xsje.equals=" + DEFAULT_XSJE);

        // Get all the dXsList where xsje equals to UPDATED_XSJE
        defaultDXsShouldNotBeFound("xsje.equals=" + UPDATED_XSJE);
    }

    @Test
    @Transactional
    void getAllDXsByXsjeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsje not equals to DEFAULT_XSJE
        defaultDXsShouldNotBeFound("xsje.notEquals=" + DEFAULT_XSJE);

        // Get all the dXsList where xsje not equals to UPDATED_XSJE
        defaultDXsShouldBeFound("xsje.notEquals=" + UPDATED_XSJE);
    }

    @Test
    @Transactional
    void getAllDXsByXsjeIsInShouldWork() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsje in DEFAULT_XSJE or UPDATED_XSJE
        defaultDXsShouldBeFound("xsje.in=" + DEFAULT_XSJE + "," + UPDATED_XSJE);

        // Get all the dXsList where xsje equals to UPDATED_XSJE
        defaultDXsShouldNotBeFound("xsje.in=" + UPDATED_XSJE);
    }

    @Test
    @Transactional
    void getAllDXsByXsjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsje is not null
        defaultDXsShouldBeFound("xsje.specified=true");

        // Get all the dXsList where xsje is null
        defaultDXsShouldNotBeFound("xsje.specified=false");
    }

    @Test
    @Transactional
    void getAllDXsByXsjeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsje is greater than or equal to DEFAULT_XSJE
        defaultDXsShouldBeFound("xsje.greaterThanOrEqual=" + DEFAULT_XSJE);

        // Get all the dXsList where xsje is greater than or equal to UPDATED_XSJE
        defaultDXsShouldNotBeFound("xsje.greaterThanOrEqual=" + UPDATED_XSJE);
    }

    @Test
    @Transactional
    void getAllDXsByXsjeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsje is less than or equal to DEFAULT_XSJE
        defaultDXsShouldBeFound("xsje.lessThanOrEqual=" + DEFAULT_XSJE);

        // Get all the dXsList where xsje is less than or equal to SMALLER_XSJE
        defaultDXsShouldNotBeFound("xsje.lessThanOrEqual=" + SMALLER_XSJE);
    }

    @Test
    @Transactional
    void getAllDXsByXsjeIsLessThanSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsje is less than DEFAULT_XSJE
        defaultDXsShouldNotBeFound("xsje.lessThan=" + DEFAULT_XSJE);

        // Get all the dXsList where xsje is less than UPDATED_XSJE
        defaultDXsShouldBeFound("xsje.lessThan=" + UPDATED_XSJE);
    }

    @Test
    @Transactional
    void getAllDXsByXsjeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        // Get all the dXsList where xsje is greater than DEFAULT_XSJE
        defaultDXsShouldNotBeFound("xsje.greaterThan=" + DEFAULT_XSJE);

        // Get all the dXsList where xsje is greater than SMALLER_XSJE
        defaultDXsShouldBeFound("xsje.greaterThan=" + SMALLER_XSJE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDXsShouldBeFound(String filter) throws Exception {
        restDXsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dXs.getId().intValue())))
            .andExpect(jsonPath("$.[*].begintime").value(hasItem(DEFAULT_BEGINTIME.toString())))
            .andExpect(jsonPath("$.[*].endtime").value(hasItem(DEFAULT_ENDTIME.toString())))
            .andExpect(jsonPath("$.[*].ckbillno").value(hasItem(DEFAULT_CKBILLNO)))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].kcid").value(hasItem(DEFAULT_KCID.intValue())))
            .andExpect(jsonPath("$.[*].ckid").value(hasItem(DEFAULT_CKID.intValue())))
            .andExpect(jsonPath("$.[*].spbm").value(hasItem(DEFAULT_SPBM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].rkprice").value(hasItem(sameNumber(DEFAULT_RKPRICE))))
            .andExpect(jsonPath("$.[*].xsprice").value(hasItem(sameNumber(DEFAULT_XSPRICE))))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].rkje").value(hasItem(sameNumber(DEFAULT_RKJE))))
            .andExpect(jsonPath("$.[*].xsje").value(hasItem(sameNumber(DEFAULT_XSJE))));

        // Check, that the count call also returns 1
        restDXsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDXsShouldNotBeFound(String filter) throws Exception {
        restDXsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDXsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDXs() throws Exception {
        // Get the dXs
        restDXsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDXs() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        int databaseSizeBeforeUpdate = dXsRepository.findAll().size();

        // Update the dXs
        DXs updatedDXs = dXsRepository.findById(dXs.getId()).get();
        // Disconnect from session so that the updates on updatedDXs are not directly saved in db
        em.detach(updatedDXs);
        updatedDXs
            .begintime(UPDATED_BEGINTIME)
            .endtime(UPDATED_ENDTIME)
            .ckbillno(UPDATED_CKBILLNO)
            .depot(UPDATED_DEPOT)
            .kcid(UPDATED_KCID)
            .ckid(UPDATED_CKID)
            .spbm(UPDATED_SPBM)
            .spmc(UPDATED_SPMC)
            .unit(UPDATED_UNIT)
            .rkprice(UPDATED_RKPRICE)
            .xsprice(UPDATED_XSPRICE)
            .sl(UPDATED_SL)
            .rkje(UPDATED_RKJE)
            .xsje(UPDATED_XSJE);
        DXsDTO dXsDTO = dXsMapper.toDto(updatedDXs);

        restDXsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dXsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dXsDTO))
            )
            .andExpect(status().isOk());

        // Validate the DXs in the database
        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeUpdate);
        DXs testDXs = dXsList.get(dXsList.size() - 1);
        assertThat(testDXs.getBegintime()).isEqualTo(UPDATED_BEGINTIME);
        assertThat(testDXs.getEndtime()).isEqualTo(UPDATED_ENDTIME);
        assertThat(testDXs.getCkbillno()).isEqualTo(UPDATED_CKBILLNO);
        assertThat(testDXs.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testDXs.getKcid()).isEqualTo(UPDATED_KCID);
        assertThat(testDXs.getCkid()).isEqualTo(UPDATED_CKID);
        assertThat(testDXs.getSpbm()).isEqualTo(UPDATED_SPBM);
        assertThat(testDXs.getSpmc()).isEqualTo(UPDATED_SPMC);
        assertThat(testDXs.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testDXs.getRkprice()).isEqualTo(UPDATED_RKPRICE);
        assertThat(testDXs.getXsprice()).isEqualTo(UPDATED_XSPRICE);
        assertThat(testDXs.getSl()).isEqualTo(UPDATED_SL);
        assertThat(testDXs.getRkje()).isEqualTo(UPDATED_RKJE);
        assertThat(testDXs.getXsje()).isEqualTo(UPDATED_XSJE);

        // Validate the DXs in Elasticsearch
        verify(mockDXsSearchRepository).save(testDXs);
    }

    @Test
    @Transactional
    void putNonExistingDXs() throws Exception {
        int databaseSizeBeforeUpdate = dXsRepository.findAll().size();
        dXs.setId(count.incrementAndGet());

        // Create the DXs
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDXsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dXsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dXsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DXs in the database
        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DXs in Elasticsearch
        verify(mockDXsSearchRepository, times(0)).save(dXs);
    }

    @Test
    @Transactional
    void putWithIdMismatchDXs() throws Exception {
        int databaseSizeBeforeUpdate = dXsRepository.findAll().size();
        dXs.setId(count.incrementAndGet());

        // Create the DXs
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDXsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dXsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DXs in the database
        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DXs in Elasticsearch
        verify(mockDXsSearchRepository, times(0)).save(dXs);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDXs() throws Exception {
        int databaseSizeBeforeUpdate = dXsRepository.findAll().size();
        dXs.setId(count.incrementAndGet());

        // Create the DXs
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDXsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dXsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DXs in the database
        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DXs in Elasticsearch
        verify(mockDXsSearchRepository, times(0)).save(dXs);
    }

    @Test
    @Transactional
    void partialUpdateDXsWithPatch() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        int databaseSizeBeforeUpdate = dXsRepository.findAll().size();

        // Update the dXs using partial update
        DXs partialUpdatedDXs = new DXs();
        partialUpdatedDXs.setId(dXs.getId());

        partialUpdatedDXs.begintime(UPDATED_BEGINTIME).unit(UPDATED_UNIT).xsprice(UPDATED_XSPRICE).rkje(UPDATED_RKJE).xsje(UPDATED_XSJE);

        restDXsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDXs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDXs))
            )
            .andExpect(status().isOk());

        // Validate the DXs in the database
        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeUpdate);
        DXs testDXs = dXsList.get(dXsList.size() - 1);
        assertThat(testDXs.getBegintime()).isEqualTo(UPDATED_BEGINTIME);
        assertThat(testDXs.getEndtime()).isEqualTo(DEFAULT_ENDTIME);
        assertThat(testDXs.getCkbillno()).isEqualTo(DEFAULT_CKBILLNO);
        assertThat(testDXs.getDepot()).isEqualTo(DEFAULT_DEPOT);
        assertThat(testDXs.getKcid()).isEqualTo(DEFAULT_KCID);
        assertThat(testDXs.getCkid()).isEqualTo(DEFAULT_CKID);
        assertThat(testDXs.getSpbm()).isEqualTo(DEFAULT_SPBM);
        assertThat(testDXs.getSpmc()).isEqualTo(DEFAULT_SPMC);
        assertThat(testDXs.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testDXs.getRkprice()).isEqualByComparingTo(DEFAULT_RKPRICE);
        assertThat(testDXs.getXsprice()).isEqualByComparingTo(UPDATED_XSPRICE);
        assertThat(testDXs.getSl()).isEqualByComparingTo(DEFAULT_SL);
        assertThat(testDXs.getRkje()).isEqualByComparingTo(UPDATED_RKJE);
        assertThat(testDXs.getXsje()).isEqualByComparingTo(UPDATED_XSJE);
    }

    @Test
    @Transactional
    void fullUpdateDXsWithPatch() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        int databaseSizeBeforeUpdate = dXsRepository.findAll().size();

        // Update the dXs using partial update
        DXs partialUpdatedDXs = new DXs();
        partialUpdatedDXs.setId(dXs.getId());

        partialUpdatedDXs
            .begintime(UPDATED_BEGINTIME)
            .endtime(UPDATED_ENDTIME)
            .ckbillno(UPDATED_CKBILLNO)
            .depot(UPDATED_DEPOT)
            .kcid(UPDATED_KCID)
            .ckid(UPDATED_CKID)
            .spbm(UPDATED_SPBM)
            .spmc(UPDATED_SPMC)
            .unit(UPDATED_UNIT)
            .rkprice(UPDATED_RKPRICE)
            .xsprice(UPDATED_XSPRICE)
            .sl(UPDATED_SL)
            .rkje(UPDATED_RKJE)
            .xsje(UPDATED_XSJE);

        restDXsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDXs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDXs))
            )
            .andExpect(status().isOk());

        // Validate the DXs in the database
        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeUpdate);
        DXs testDXs = dXsList.get(dXsList.size() - 1);
        assertThat(testDXs.getBegintime()).isEqualTo(UPDATED_BEGINTIME);
        assertThat(testDXs.getEndtime()).isEqualTo(UPDATED_ENDTIME);
        assertThat(testDXs.getCkbillno()).isEqualTo(UPDATED_CKBILLNO);
        assertThat(testDXs.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testDXs.getKcid()).isEqualTo(UPDATED_KCID);
        assertThat(testDXs.getCkid()).isEqualTo(UPDATED_CKID);
        assertThat(testDXs.getSpbm()).isEqualTo(UPDATED_SPBM);
        assertThat(testDXs.getSpmc()).isEqualTo(UPDATED_SPMC);
        assertThat(testDXs.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testDXs.getRkprice()).isEqualByComparingTo(UPDATED_RKPRICE);
        assertThat(testDXs.getXsprice()).isEqualByComparingTo(UPDATED_XSPRICE);
        assertThat(testDXs.getSl()).isEqualByComparingTo(UPDATED_SL);
        assertThat(testDXs.getRkje()).isEqualByComparingTo(UPDATED_RKJE);
        assertThat(testDXs.getXsje()).isEqualByComparingTo(UPDATED_XSJE);
    }

    @Test
    @Transactional
    void patchNonExistingDXs() throws Exception {
        int databaseSizeBeforeUpdate = dXsRepository.findAll().size();
        dXs.setId(count.incrementAndGet());

        // Create the DXs
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDXsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dXsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dXsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DXs in the database
        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DXs in Elasticsearch
        verify(mockDXsSearchRepository, times(0)).save(dXs);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDXs() throws Exception {
        int databaseSizeBeforeUpdate = dXsRepository.findAll().size();
        dXs.setId(count.incrementAndGet());

        // Create the DXs
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDXsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dXsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DXs in the database
        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DXs in Elasticsearch
        verify(mockDXsSearchRepository, times(0)).save(dXs);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDXs() throws Exception {
        int databaseSizeBeforeUpdate = dXsRepository.findAll().size();
        dXs.setId(count.incrementAndGet());

        // Create the DXs
        DXsDTO dXsDTO = dXsMapper.toDto(dXs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDXsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dXsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DXs in the database
        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DXs in Elasticsearch
        verify(mockDXsSearchRepository, times(0)).save(dXs);
    }

    @Test
    @Transactional
    void deleteDXs() throws Exception {
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);

        int databaseSizeBeforeDelete = dXsRepository.findAll().size();

        // Delete the dXs
        restDXsMockMvc.perform(delete(ENTITY_API_URL_ID, dXs.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DXs> dXsList = dXsRepository.findAll();
        assertThat(dXsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DXs in Elasticsearch
        verify(mockDXsSearchRepository, times(1)).deleteById(dXs.getId());
    }

    @Test
    @Transactional
    void searchDXs() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dXsRepository.saveAndFlush(dXs);
        when(mockDXsSearchRepository.search(queryStringQuery("id:" + dXs.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dXs), PageRequest.of(0, 1), 1));

        // Search the dXs
        restDXsMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dXs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dXs.getId().intValue())))
            .andExpect(jsonPath("$.[*].begintime").value(hasItem(DEFAULT_BEGINTIME.toString())))
            .andExpect(jsonPath("$.[*].endtime").value(hasItem(DEFAULT_ENDTIME.toString())))
            .andExpect(jsonPath("$.[*].ckbillno").value(hasItem(DEFAULT_CKBILLNO)))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].kcid").value(hasItem(DEFAULT_KCID.intValue())))
            .andExpect(jsonPath("$.[*].ckid").value(hasItem(DEFAULT_CKID.intValue())))
            .andExpect(jsonPath("$.[*].spbm").value(hasItem(DEFAULT_SPBM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].rkprice").value(hasItem(sameNumber(DEFAULT_RKPRICE))))
            .andExpect(jsonPath("$.[*].xsprice").value(hasItem(sameNumber(DEFAULT_XSPRICE))))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].rkje").value(hasItem(sameNumber(DEFAULT_RKJE))))
            .andExpect(jsonPath("$.[*].xsje").value(hasItem(sameNumber(DEFAULT_XSJE))));
    }
}
