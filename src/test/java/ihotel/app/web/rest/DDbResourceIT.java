package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DDb;
import ihotel.app.repository.DDbRepository;
import ihotel.app.repository.search.DDbSearchRepository;
import ihotel.app.service.criteria.DDbCriteria;
import ihotel.app.service.dto.DDbDTO;
import ihotel.app.service.mapper.DDbMapper;
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
 * Integration tests for the {@link DDbResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DDbResourceIT {

    private static final Instant DEFAULT_DBDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DBDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DBBILLNO = "AAAAAAAAAA";
    private static final String UPDATED_DBBILLNO = "BBBBBBBBBB";

    private static final String DEFAULT_RDEPOT = "AAAAAAAAAA";
    private static final String UPDATED_RDEPOT = "BBBBBBBBBB";

    private static final String DEFAULT_CDEPOT = "AAAAAAAAAA";
    private static final String UPDATED_CDEPOT = "BBBBBBBBBB";

    private static final String DEFAULT_JBR = "AAAAAAAAAA";
    private static final String UPDATED_JBR = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_SPBM = "AAAAAAAAAA";
    private static final String UPDATED_SPBM = "BBBBBBBBBB";

    private static final String DEFAULT_SPMC = "AAAAAAAAAA";
    private static final String UPDATED_SPMC = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SL = new BigDecimal(2);
    private static final BigDecimal SMALLER_SL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_JE = new BigDecimal(1);
    private static final BigDecimal UPDATED_JE = new BigDecimal(2);
    private static final BigDecimal SMALLER_JE = new BigDecimal(1 - 1);

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final Long DEFAULT_FLAG = 1L;
    private static final Long UPDATED_FLAG = 2L;
    private static final Long SMALLER_FLAG = 1L - 1L;

    private static final Long DEFAULT_KCID = 1L;
    private static final Long UPDATED_KCID = 2L;
    private static final Long SMALLER_KCID = 1L - 1L;

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final Instant DEFAULT_LRDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LRDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CKBILLNO = "AAAAAAAAAA";
    private static final String UPDATED_CKBILLNO = "BBBBBBBBBB";

    private static final String DEFAULT_F_1 = "AAAAAAAAAA";
    private static final String UPDATED_F_1 = "BBBBBBBBBB";

    private static final String DEFAULT_F_2 = "AAAAAAAAAA";
    private static final String UPDATED_F_2 = "BBBBBBBBBB";

    private static final String DEFAULT_F_1_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_F_1_EMPN = "BBBBBBBBBB";

    private static final String DEFAULT_F_2_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_F_2_EMPN = "BBBBBBBBBB";

    private static final Instant DEFAULT_F_1_SJ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_F_1_SJ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_F_2_SJ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_F_2_SJ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/d-dbs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-dbs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DDbRepository dDbRepository;

    @Autowired
    private DDbMapper dDbMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DDbSearchRepositoryMockConfiguration
     */
    @Autowired
    private DDbSearchRepository mockDDbSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDDbMockMvc;

    private DDb dDb;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DDb createEntity(EntityManager em) {
        DDb dDb = new DDb()
            .dbdate(DEFAULT_DBDATE)
            .dbbillno(DEFAULT_DBBILLNO)
            .rdepot(DEFAULT_RDEPOT)
            .cdepot(DEFAULT_CDEPOT)
            .jbr(DEFAULT_JBR)
            .remark(DEFAULT_REMARK)
            .spbm(DEFAULT_SPBM)
            .spmc(DEFAULT_SPMC)
            .unit(DEFAULT_UNIT)
            .price(DEFAULT_PRICE)
            .sl(DEFAULT_SL)
            .je(DEFAULT_JE)
            .memo(DEFAULT_MEMO)
            .flag(DEFAULT_FLAG)
            .kcid(DEFAULT_KCID)
            .empn(DEFAULT_EMPN)
            .lrdate(DEFAULT_LRDATE)
            .ckbillno(DEFAULT_CKBILLNO)
            .f1(DEFAULT_F_1)
            .f2(DEFAULT_F_2)
            .f1empn(DEFAULT_F_1_EMPN)
            .f2empn(DEFAULT_F_2_EMPN)
            .f1sj(DEFAULT_F_1_SJ)
            .f2sj(DEFAULT_F_2_SJ);
        return dDb;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DDb createUpdatedEntity(EntityManager em) {
        DDb dDb = new DDb()
            .dbdate(UPDATED_DBDATE)
            .dbbillno(UPDATED_DBBILLNO)
            .rdepot(UPDATED_RDEPOT)
            .cdepot(UPDATED_CDEPOT)
            .jbr(UPDATED_JBR)
            .remark(UPDATED_REMARK)
            .spbm(UPDATED_SPBM)
            .spmc(UPDATED_SPMC)
            .unit(UPDATED_UNIT)
            .price(UPDATED_PRICE)
            .sl(UPDATED_SL)
            .je(UPDATED_JE)
            .memo(UPDATED_MEMO)
            .flag(UPDATED_FLAG)
            .kcid(UPDATED_KCID)
            .empn(UPDATED_EMPN)
            .lrdate(UPDATED_LRDATE)
            .ckbillno(UPDATED_CKBILLNO)
            .f1(UPDATED_F_1)
            .f2(UPDATED_F_2)
            .f1empn(UPDATED_F_1_EMPN)
            .f2empn(UPDATED_F_2_EMPN)
            .f1sj(UPDATED_F_1_SJ)
            .f2sj(UPDATED_F_2_SJ);
        return dDb;
    }

    @BeforeEach
    public void initTest() {
        dDb = createEntity(em);
    }

    @Test
    @Transactional
    void createDDb() throws Exception {
        int databaseSizeBeforeCreate = dDbRepository.findAll().size();
        // Create the DDb
        DDbDTO dDbDTO = dDbMapper.toDto(dDb);
        restDDbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDbDTO)))
            .andExpect(status().isCreated());

        // Validate the DDb in the database
        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeCreate + 1);
        DDb testDDb = dDbList.get(dDbList.size() - 1);
        assertThat(testDDb.getDbdate()).isEqualTo(DEFAULT_DBDATE);
        assertThat(testDDb.getDbbillno()).isEqualTo(DEFAULT_DBBILLNO);
        assertThat(testDDb.getRdepot()).isEqualTo(DEFAULT_RDEPOT);
        assertThat(testDDb.getCdepot()).isEqualTo(DEFAULT_CDEPOT);
        assertThat(testDDb.getJbr()).isEqualTo(DEFAULT_JBR);
        assertThat(testDDb.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testDDb.getSpbm()).isEqualTo(DEFAULT_SPBM);
        assertThat(testDDb.getSpmc()).isEqualTo(DEFAULT_SPMC);
        assertThat(testDDb.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testDDb.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testDDb.getSl()).isEqualByComparingTo(DEFAULT_SL);
        assertThat(testDDb.getJe()).isEqualByComparingTo(DEFAULT_JE);
        assertThat(testDDb.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testDDb.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testDDb.getKcid()).isEqualTo(DEFAULT_KCID);
        assertThat(testDDb.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testDDb.getLrdate()).isEqualTo(DEFAULT_LRDATE);
        assertThat(testDDb.getCkbillno()).isEqualTo(DEFAULT_CKBILLNO);
        assertThat(testDDb.getf1()).isEqualTo(DEFAULT_F_1);
        assertThat(testDDb.getf2()).isEqualTo(DEFAULT_F_2);
        assertThat(testDDb.getf1empn()).isEqualTo(DEFAULT_F_1_EMPN);
        assertThat(testDDb.getf2empn()).isEqualTo(DEFAULT_F_2_EMPN);
        assertThat(testDDb.getf1sj()).isEqualTo(DEFAULT_F_1_SJ);
        assertThat(testDDb.getf2sj()).isEqualTo(DEFAULT_F_2_SJ);

        // Validate the DDb in Elasticsearch
        verify(mockDDbSearchRepository, times(1)).save(testDDb);
    }

    @Test
    @Transactional
    void createDDbWithExistingId() throws Exception {
        // Create the DDb with an existing ID
        dDb.setId(1L);
        DDbDTO dDbDTO = dDbMapper.toDto(dDb);

        int databaseSizeBeforeCreate = dDbRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDDbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDbDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DDb in the database
        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeCreate);

        // Validate the DDb in Elasticsearch
        verify(mockDDbSearchRepository, times(0)).save(dDb);
    }

    @Test
    @Transactional
    void checkDbdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dDbRepository.findAll().size();
        // set the field null
        dDb.setDbdate(null);

        // Create the DDb, which fails.
        DDbDTO dDbDTO = dDbMapper.toDto(dDb);

        restDDbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDbDTO)))
            .andExpect(status().isBadRequest());

        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDbbillnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dDbRepository.findAll().size();
        // set the field null
        dDb.setDbbillno(null);

        // Create the DDb, which fails.
        DDbDTO dDbDTO = dDbMapper.toDto(dDb);

        restDDbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDbDTO)))
            .andExpect(status().isBadRequest());

        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRdepotIsRequired() throws Exception {
        int databaseSizeBeforeTest = dDbRepository.findAll().size();
        // set the field null
        dDb.setRdepot(null);

        // Create the DDb, which fails.
        DDbDTO dDbDTO = dDbMapper.toDto(dDb);

        restDDbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDbDTO)))
            .andExpect(status().isBadRequest());

        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCdepotIsRequired() throws Exception {
        int databaseSizeBeforeTest = dDbRepository.findAll().size();
        // set the field null
        dDb.setCdepot(null);

        // Create the DDb, which fails.
        DDbDTO dDbDTO = dDbMapper.toDto(dDb);

        restDDbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDbDTO)))
            .andExpect(status().isBadRequest());

        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpbmIsRequired() throws Exception {
        int databaseSizeBeforeTest = dDbRepository.findAll().size();
        // set the field null
        dDb.setSpbm(null);

        // Create the DDb, which fails.
        DDbDTO dDbDTO = dDbMapper.toDto(dDb);

        restDDbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDbDTO)))
            .andExpect(status().isBadRequest());

        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpmcIsRequired() throws Exception {
        int databaseSizeBeforeTest = dDbRepository.findAll().size();
        // set the field null
        dDb.setSpmc(null);

        // Create the DDb, which fails.
        DDbDTO dDbDTO = dDbMapper.toDto(dDb);

        restDDbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDbDTO)))
            .andExpect(status().isBadRequest());

        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDDbs() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList
        restDDbMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dDb.getId().intValue())))
            .andExpect(jsonPath("$.[*].dbdate").value(hasItem(DEFAULT_DBDATE.toString())))
            .andExpect(jsonPath("$.[*].dbbillno").value(hasItem(DEFAULT_DBBILLNO)))
            .andExpect(jsonPath("$.[*].rdepot").value(hasItem(DEFAULT_RDEPOT)))
            .andExpect(jsonPath("$.[*].cdepot").value(hasItem(DEFAULT_CDEPOT)))
            .andExpect(jsonPath("$.[*].jbr").value(hasItem(DEFAULT_JBR)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].spbm").value(hasItem(DEFAULT_SPBM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].je").value(hasItem(sameNumber(DEFAULT_JE))))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].kcid").value(hasItem(DEFAULT_KCID.intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].lrdate").value(hasItem(DEFAULT_LRDATE.toString())))
            .andExpect(jsonPath("$.[*].ckbillno").value(hasItem(DEFAULT_CKBILLNO)))
            .andExpect(jsonPath("$.[*].f1").value(hasItem(DEFAULT_F_1)))
            .andExpect(jsonPath("$.[*].f2").value(hasItem(DEFAULT_F_2)))
            .andExpect(jsonPath("$.[*].f1empn").value(hasItem(DEFAULT_F_1_EMPN)))
            .andExpect(jsonPath("$.[*].f2empn").value(hasItem(DEFAULT_F_2_EMPN)))
            .andExpect(jsonPath("$.[*].f1sj").value(hasItem(DEFAULT_F_1_SJ.toString())))
            .andExpect(jsonPath("$.[*].f2sj").value(hasItem(DEFAULT_F_2_SJ.toString())));
    }

    @Test
    @Transactional
    void getDDb() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get the dDb
        restDDbMockMvc
            .perform(get(ENTITY_API_URL_ID, dDb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dDb.getId().intValue()))
            .andExpect(jsonPath("$.dbdate").value(DEFAULT_DBDATE.toString()))
            .andExpect(jsonPath("$.dbbillno").value(DEFAULT_DBBILLNO))
            .andExpect(jsonPath("$.rdepot").value(DEFAULT_RDEPOT))
            .andExpect(jsonPath("$.cdepot").value(DEFAULT_CDEPOT))
            .andExpect(jsonPath("$.jbr").value(DEFAULT_JBR))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.spbm").value(DEFAULT_SPBM))
            .andExpect(jsonPath("$.spmc").value(DEFAULT_SPMC))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.sl").value(sameNumber(DEFAULT_SL)))
            .andExpect(jsonPath("$.je").value(sameNumber(DEFAULT_JE)))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG.intValue()))
            .andExpect(jsonPath("$.kcid").value(DEFAULT_KCID.intValue()))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.lrdate").value(DEFAULT_LRDATE.toString()))
            .andExpect(jsonPath("$.ckbillno").value(DEFAULT_CKBILLNO))
            .andExpect(jsonPath("$.f1").value(DEFAULT_F_1))
            .andExpect(jsonPath("$.f2").value(DEFAULT_F_2))
            .andExpect(jsonPath("$.f1empn").value(DEFAULT_F_1_EMPN))
            .andExpect(jsonPath("$.f2empn").value(DEFAULT_F_2_EMPN))
            .andExpect(jsonPath("$.f1sj").value(DEFAULT_F_1_SJ.toString()))
            .andExpect(jsonPath("$.f2sj").value(DEFAULT_F_2_SJ.toString()));
    }

    @Test
    @Transactional
    void getDDbsByIdFiltering() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        Long id = dDb.getId();

        defaultDDbShouldBeFound("id.equals=" + id);
        defaultDDbShouldNotBeFound("id.notEquals=" + id);

        defaultDDbShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDDbShouldNotBeFound("id.greaterThan=" + id);

        defaultDDbShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDDbShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDDbsByDbdateIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where dbdate equals to DEFAULT_DBDATE
        defaultDDbShouldBeFound("dbdate.equals=" + DEFAULT_DBDATE);

        // Get all the dDbList where dbdate equals to UPDATED_DBDATE
        defaultDDbShouldNotBeFound("dbdate.equals=" + UPDATED_DBDATE);
    }

    @Test
    @Transactional
    void getAllDDbsByDbdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where dbdate not equals to DEFAULT_DBDATE
        defaultDDbShouldNotBeFound("dbdate.notEquals=" + DEFAULT_DBDATE);

        // Get all the dDbList where dbdate not equals to UPDATED_DBDATE
        defaultDDbShouldBeFound("dbdate.notEquals=" + UPDATED_DBDATE);
    }

    @Test
    @Transactional
    void getAllDDbsByDbdateIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where dbdate in DEFAULT_DBDATE or UPDATED_DBDATE
        defaultDDbShouldBeFound("dbdate.in=" + DEFAULT_DBDATE + "," + UPDATED_DBDATE);

        // Get all the dDbList where dbdate equals to UPDATED_DBDATE
        defaultDDbShouldNotBeFound("dbdate.in=" + UPDATED_DBDATE);
    }

    @Test
    @Transactional
    void getAllDDbsByDbdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where dbdate is not null
        defaultDDbShouldBeFound("dbdate.specified=true");

        // Get all the dDbList where dbdate is null
        defaultDDbShouldNotBeFound("dbdate.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByDbbillnoIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where dbbillno equals to DEFAULT_DBBILLNO
        defaultDDbShouldBeFound("dbbillno.equals=" + DEFAULT_DBBILLNO);

        // Get all the dDbList where dbbillno equals to UPDATED_DBBILLNO
        defaultDDbShouldNotBeFound("dbbillno.equals=" + UPDATED_DBBILLNO);
    }

    @Test
    @Transactional
    void getAllDDbsByDbbillnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where dbbillno not equals to DEFAULT_DBBILLNO
        defaultDDbShouldNotBeFound("dbbillno.notEquals=" + DEFAULT_DBBILLNO);

        // Get all the dDbList where dbbillno not equals to UPDATED_DBBILLNO
        defaultDDbShouldBeFound("dbbillno.notEquals=" + UPDATED_DBBILLNO);
    }

    @Test
    @Transactional
    void getAllDDbsByDbbillnoIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where dbbillno in DEFAULT_DBBILLNO or UPDATED_DBBILLNO
        defaultDDbShouldBeFound("dbbillno.in=" + DEFAULT_DBBILLNO + "," + UPDATED_DBBILLNO);

        // Get all the dDbList where dbbillno equals to UPDATED_DBBILLNO
        defaultDDbShouldNotBeFound("dbbillno.in=" + UPDATED_DBBILLNO);
    }

    @Test
    @Transactional
    void getAllDDbsByDbbillnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where dbbillno is not null
        defaultDDbShouldBeFound("dbbillno.specified=true");

        // Get all the dDbList where dbbillno is null
        defaultDDbShouldNotBeFound("dbbillno.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByDbbillnoContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where dbbillno contains DEFAULT_DBBILLNO
        defaultDDbShouldBeFound("dbbillno.contains=" + DEFAULT_DBBILLNO);

        // Get all the dDbList where dbbillno contains UPDATED_DBBILLNO
        defaultDDbShouldNotBeFound("dbbillno.contains=" + UPDATED_DBBILLNO);
    }

    @Test
    @Transactional
    void getAllDDbsByDbbillnoNotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where dbbillno does not contain DEFAULT_DBBILLNO
        defaultDDbShouldNotBeFound("dbbillno.doesNotContain=" + DEFAULT_DBBILLNO);

        // Get all the dDbList where dbbillno does not contain UPDATED_DBBILLNO
        defaultDDbShouldBeFound("dbbillno.doesNotContain=" + UPDATED_DBBILLNO);
    }

    @Test
    @Transactional
    void getAllDDbsByRdepotIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where rdepot equals to DEFAULT_RDEPOT
        defaultDDbShouldBeFound("rdepot.equals=" + DEFAULT_RDEPOT);

        // Get all the dDbList where rdepot equals to UPDATED_RDEPOT
        defaultDDbShouldNotBeFound("rdepot.equals=" + UPDATED_RDEPOT);
    }

    @Test
    @Transactional
    void getAllDDbsByRdepotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where rdepot not equals to DEFAULT_RDEPOT
        defaultDDbShouldNotBeFound("rdepot.notEquals=" + DEFAULT_RDEPOT);

        // Get all the dDbList where rdepot not equals to UPDATED_RDEPOT
        defaultDDbShouldBeFound("rdepot.notEquals=" + UPDATED_RDEPOT);
    }

    @Test
    @Transactional
    void getAllDDbsByRdepotIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where rdepot in DEFAULT_RDEPOT or UPDATED_RDEPOT
        defaultDDbShouldBeFound("rdepot.in=" + DEFAULT_RDEPOT + "," + UPDATED_RDEPOT);

        // Get all the dDbList where rdepot equals to UPDATED_RDEPOT
        defaultDDbShouldNotBeFound("rdepot.in=" + UPDATED_RDEPOT);
    }

    @Test
    @Transactional
    void getAllDDbsByRdepotIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where rdepot is not null
        defaultDDbShouldBeFound("rdepot.specified=true");

        // Get all the dDbList where rdepot is null
        defaultDDbShouldNotBeFound("rdepot.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByRdepotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where rdepot contains DEFAULT_RDEPOT
        defaultDDbShouldBeFound("rdepot.contains=" + DEFAULT_RDEPOT);

        // Get all the dDbList where rdepot contains UPDATED_RDEPOT
        defaultDDbShouldNotBeFound("rdepot.contains=" + UPDATED_RDEPOT);
    }

    @Test
    @Transactional
    void getAllDDbsByRdepotNotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where rdepot does not contain DEFAULT_RDEPOT
        defaultDDbShouldNotBeFound("rdepot.doesNotContain=" + DEFAULT_RDEPOT);

        // Get all the dDbList where rdepot does not contain UPDATED_RDEPOT
        defaultDDbShouldBeFound("rdepot.doesNotContain=" + UPDATED_RDEPOT);
    }

    @Test
    @Transactional
    void getAllDDbsByCdepotIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where cdepot equals to DEFAULT_CDEPOT
        defaultDDbShouldBeFound("cdepot.equals=" + DEFAULT_CDEPOT);

        // Get all the dDbList where cdepot equals to UPDATED_CDEPOT
        defaultDDbShouldNotBeFound("cdepot.equals=" + UPDATED_CDEPOT);
    }

    @Test
    @Transactional
    void getAllDDbsByCdepotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where cdepot not equals to DEFAULT_CDEPOT
        defaultDDbShouldNotBeFound("cdepot.notEquals=" + DEFAULT_CDEPOT);

        // Get all the dDbList where cdepot not equals to UPDATED_CDEPOT
        defaultDDbShouldBeFound("cdepot.notEquals=" + UPDATED_CDEPOT);
    }

    @Test
    @Transactional
    void getAllDDbsByCdepotIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where cdepot in DEFAULT_CDEPOT or UPDATED_CDEPOT
        defaultDDbShouldBeFound("cdepot.in=" + DEFAULT_CDEPOT + "," + UPDATED_CDEPOT);

        // Get all the dDbList where cdepot equals to UPDATED_CDEPOT
        defaultDDbShouldNotBeFound("cdepot.in=" + UPDATED_CDEPOT);
    }

    @Test
    @Transactional
    void getAllDDbsByCdepotIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where cdepot is not null
        defaultDDbShouldBeFound("cdepot.specified=true");

        // Get all the dDbList where cdepot is null
        defaultDDbShouldNotBeFound("cdepot.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByCdepotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where cdepot contains DEFAULT_CDEPOT
        defaultDDbShouldBeFound("cdepot.contains=" + DEFAULT_CDEPOT);

        // Get all the dDbList where cdepot contains UPDATED_CDEPOT
        defaultDDbShouldNotBeFound("cdepot.contains=" + UPDATED_CDEPOT);
    }

    @Test
    @Transactional
    void getAllDDbsByCdepotNotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where cdepot does not contain DEFAULT_CDEPOT
        defaultDDbShouldNotBeFound("cdepot.doesNotContain=" + DEFAULT_CDEPOT);

        // Get all the dDbList where cdepot does not contain UPDATED_CDEPOT
        defaultDDbShouldBeFound("cdepot.doesNotContain=" + UPDATED_CDEPOT);
    }

    @Test
    @Transactional
    void getAllDDbsByJbrIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where jbr equals to DEFAULT_JBR
        defaultDDbShouldBeFound("jbr.equals=" + DEFAULT_JBR);

        // Get all the dDbList where jbr equals to UPDATED_JBR
        defaultDDbShouldNotBeFound("jbr.equals=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDDbsByJbrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where jbr not equals to DEFAULT_JBR
        defaultDDbShouldNotBeFound("jbr.notEquals=" + DEFAULT_JBR);

        // Get all the dDbList where jbr not equals to UPDATED_JBR
        defaultDDbShouldBeFound("jbr.notEquals=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDDbsByJbrIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where jbr in DEFAULT_JBR or UPDATED_JBR
        defaultDDbShouldBeFound("jbr.in=" + DEFAULT_JBR + "," + UPDATED_JBR);

        // Get all the dDbList where jbr equals to UPDATED_JBR
        defaultDDbShouldNotBeFound("jbr.in=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDDbsByJbrIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where jbr is not null
        defaultDDbShouldBeFound("jbr.specified=true");

        // Get all the dDbList where jbr is null
        defaultDDbShouldNotBeFound("jbr.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByJbrContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where jbr contains DEFAULT_JBR
        defaultDDbShouldBeFound("jbr.contains=" + DEFAULT_JBR);

        // Get all the dDbList where jbr contains UPDATED_JBR
        defaultDDbShouldNotBeFound("jbr.contains=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDDbsByJbrNotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where jbr does not contain DEFAULT_JBR
        defaultDDbShouldNotBeFound("jbr.doesNotContain=" + DEFAULT_JBR);

        // Get all the dDbList where jbr does not contain UPDATED_JBR
        defaultDDbShouldBeFound("jbr.doesNotContain=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDDbsByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where remark equals to DEFAULT_REMARK
        defaultDDbShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the dDbList where remark equals to UPDATED_REMARK
        defaultDDbShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDDbsByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where remark not equals to DEFAULT_REMARK
        defaultDDbShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the dDbList where remark not equals to UPDATED_REMARK
        defaultDDbShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDDbsByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultDDbShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the dDbList where remark equals to UPDATED_REMARK
        defaultDDbShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDDbsByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where remark is not null
        defaultDDbShouldBeFound("remark.specified=true");

        // Get all the dDbList where remark is null
        defaultDDbShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByRemarkContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where remark contains DEFAULT_REMARK
        defaultDDbShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the dDbList where remark contains UPDATED_REMARK
        defaultDDbShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDDbsByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where remark does not contain DEFAULT_REMARK
        defaultDDbShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the dDbList where remark does not contain UPDATED_REMARK
        defaultDDbShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDDbsBySpbmIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where spbm equals to DEFAULT_SPBM
        defaultDDbShouldBeFound("spbm.equals=" + DEFAULT_SPBM);

        // Get all the dDbList where spbm equals to UPDATED_SPBM
        defaultDDbShouldNotBeFound("spbm.equals=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDDbsBySpbmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where spbm not equals to DEFAULT_SPBM
        defaultDDbShouldNotBeFound("spbm.notEquals=" + DEFAULT_SPBM);

        // Get all the dDbList where spbm not equals to UPDATED_SPBM
        defaultDDbShouldBeFound("spbm.notEquals=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDDbsBySpbmIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where spbm in DEFAULT_SPBM or UPDATED_SPBM
        defaultDDbShouldBeFound("spbm.in=" + DEFAULT_SPBM + "," + UPDATED_SPBM);

        // Get all the dDbList where spbm equals to UPDATED_SPBM
        defaultDDbShouldNotBeFound("spbm.in=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDDbsBySpbmIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where spbm is not null
        defaultDDbShouldBeFound("spbm.specified=true");

        // Get all the dDbList where spbm is null
        defaultDDbShouldNotBeFound("spbm.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsBySpbmContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where spbm contains DEFAULT_SPBM
        defaultDDbShouldBeFound("spbm.contains=" + DEFAULT_SPBM);

        // Get all the dDbList where spbm contains UPDATED_SPBM
        defaultDDbShouldNotBeFound("spbm.contains=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDDbsBySpbmNotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where spbm does not contain DEFAULT_SPBM
        defaultDDbShouldNotBeFound("spbm.doesNotContain=" + DEFAULT_SPBM);

        // Get all the dDbList where spbm does not contain UPDATED_SPBM
        defaultDDbShouldBeFound("spbm.doesNotContain=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDDbsBySpmcIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where spmc equals to DEFAULT_SPMC
        defaultDDbShouldBeFound("spmc.equals=" + DEFAULT_SPMC);

        // Get all the dDbList where spmc equals to UPDATED_SPMC
        defaultDDbShouldNotBeFound("spmc.equals=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDDbsBySpmcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where spmc not equals to DEFAULT_SPMC
        defaultDDbShouldNotBeFound("spmc.notEquals=" + DEFAULT_SPMC);

        // Get all the dDbList where spmc not equals to UPDATED_SPMC
        defaultDDbShouldBeFound("spmc.notEquals=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDDbsBySpmcIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where spmc in DEFAULT_SPMC or UPDATED_SPMC
        defaultDDbShouldBeFound("spmc.in=" + DEFAULT_SPMC + "," + UPDATED_SPMC);

        // Get all the dDbList where spmc equals to UPDATED_SPMC
        defaultDDbShouldNotBeFound("spmc.in=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDDbsBySpmcIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where spmc is not null
        defaultDDbShouldBeFound("spmc.specified=true");

        // Get all the dDbList where spmc is null
        defaultDDbShouldNotBeFound("spmc.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsBySpmcContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where spmc contains DEFAULT_SPMC
        defaultDDbShouldBeFound("spmc.contains=" + DEFAULT_SPMC);

        // Get all the dDbList where spmc contains UPDATED_SPMC
        defaultDDbShouldNotBeFound("spmc.contains=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDDbsBySpmcNotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where spmc does not contain DEFAULT_SPMC
        defaultDDbShouldNotBeFound("spmc.doesNotContain=" + DEFAULT_SPMC);

        // Get all the dDbList where spmc does not contain UPDATED_SPMC
        defaultDDbShouldBeFound("spmc.doesNotContain=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDDbsByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where unit equals to DEFAULT_UNIT
        defaultDDbShouldBeFound("unit.equals=" + DEFAULT_UNIT);

        // Get all the dDbList where unit equals to UPDATED_UNIT
        defaultDDbShouldNotBeFound("unit.equals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDDbsByUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where unit not equals to DEFAULT_UNIT
        defaultDDbShouldNotBeFound("unit.notEquals=" + DEFAULT_UNIT);

        // Get all the dDbList where unit not equals to UPDATED_UNIT
        defaultDDbShouldBeFound("unit.notEquals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDDbsByUnitIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where unit in DEFAULT_UNIT or UPDATED_UNIT
        defaultDDbShouldBeFound("unit.in=" + DEFAULT_UNIT + "," + UPDATED_UNIT);

        // Get all the dDbList where unit equals to UPDATED_UNIT
        defaultDDbShouldNotBeFound("unit.in=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDDbsByUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where unit is not null
        defaultDDbShouldBeFound("unit.specified=true");

        // Get all the dDbList where unit is null
        defaultDDbShouldNotBeFound("unit.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByUnitContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where unit contains DEFAULT_UNIT
        defaultDDbShouldBeFound("unit.contains=" + DEFAULT_UNIT);

        // Get all the dDbList where unit contains UPDATED_UNIT
        defaultDDbShouldNotBeFound("unit.contains=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDDbsByUnitNotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where unit does not contain DEFAULT_UNIT
        defaultDDbShouldNotBeFound("unit.doesNotContain=" + DEFAULT_UNIT);

        // Get all the dDbList where unit does not contain UPDATED_UNIT
        defaultDDbShouldBeFound("unit.doesNotContain=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDDbsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where price equals to DEFAULT_PRICE
        defaultDDbShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the dDbList where price equals to UPDATED_PRICE
        defaultDDbShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDDbsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where price not equals to DEFAULT_PRICE
        defaultDDbShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the dDbList where price not equals to UPDATED_PRICE
        defaultDDbShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDDbsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultDDbShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the dDbList where price equals to UPDATED_PRICE
        defaultDDbShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDDbsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where price is not null
        defaultDDbShouldBeFound("price.specified=true");

        // Get all the dDbList where price is null
        defaultDDbShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where price is greater than or equal to DEFAULT_PRICE
        defaultDDbShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the dDbList where price is greater than or equal to UPDATED_PRICE
        defaultDDbShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDDbsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where price is less than or equal to DEFAULT_PRICE
        defaultDDbShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the dDbList where price is less than or equal to SMALLER_PRICE
        defaultDDbShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllDDbsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where price is less than DEFAULT_PRICE
        defaultDDbShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the dDbList where price is less than UPDATED_PRICE
        defaultDDbShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDDbsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where price is greater than DEFAULT_PRICE
        defaultDDbShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the dDbList where price is greater than SMALLER_PRICE
        defaultDDbShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllDDbsBySlIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where sl equals to DEFAULT_SL
        defaultDDbShouldBeFound("sl.equals=" + DEFAULT_SL);

        // Get all the dDbList where sl equals to UPDATED_SL
        defaultDDbShouldNotBeFound("sl.equals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDDbsBySlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where sl not equals to DEFAULT_SL
        defaultDDbShouldNotBeFound("sl.notEquals=" + DEFAULT_SL);

        // Get all the dDbList where sl not equals to UPDATED_SL
        defaultDDbShouldBeFound("sl.notEquals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDDbsBySlIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where sl in DEFAULT_SL or UPDATED_SL
        defaultDDbShouldBeFound("sl.in=" + DEFAULT_SL + "," + UPDATED_SL);

        // Get all the dDbList where sl equals to UPDATED_SL
        defaultDDbShouldNotBeFound("sl.in=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDDbsBySlIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where sl is not null
        defaultDDbShouldBeFound("sl.specified=true");

        // Get all the dDbList where sl is null
        defaultDDbShouldNotBeFound("sl.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsBySlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where sl is greater than or equal to DEFAULT_SL
        defaultDDbShouldBeFound("sl.greaterThanOrEqual=" + DEFAULT_SL);

        // Get all the dDbList where sl is greater than or equal to UPDATED_SL
        defaultDDbShouldNotBeFound("sl.greaterThanOrEqual=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDDbsBySlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where sl is less than or equal to DEFAULT_SL
        defaultDDbShouldBeFound("sl.lessThanOrEqual=" + DEFAULT_SL);

        // Get all the dDbList where sl is less than or equal to SMALLER_SL
        defaultDDbShouldNotBeFound("sl.lessThanOrEqual=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllDDbsBySlIsLessThanSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where sl is less than DEFAULT_SL
        defaultDDbShouldNotBeFound("sl.lessThan=" + DEFAULT_SL);

        // Get all the dDbList where sl is less than UPDATED_SL
        defaultDDbShouldBeFound("sl.lessThan=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDDbsBySlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where sl is greater than DEFAULT_SL
        defaultDDbShouldNotBeFound("sl.greaterThan=" + DEFAULT_SL);

        // Get all the dDbList where sl is greater than SMALLER_SL
        defaultDDbShouldBeFound("sl.greaterThan=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllDDbsByJeIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where je equals to DEFAULT_JE
        defaultDDbShouldBeFound("je.equals=" + DEFAULT_JE);

        // Get all the dDbList where je equals to UPDATED_JE
        defaultDDbShouldNotBeFound("je.equals=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDDbsByJeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where je not equals to DEFAULT_JE
        defaultDDbShouldNotBeFound("je.notEquals=" + DEFAULT_JE);

        // Get all the dDbList where je not equals to UPDATED_JE
        defaultDDbShouldBeFound("je.notEquals=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDDbsByJeIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where je in DEFAULT_JE or UPDATED_JE
        defaultDDbShouldBeFound("je.in=" + DEFAULT_JE + "," + UPDATED_JE);

        // Get all the dDbList where je equals to UPDATED_JE
        defaultDDbShouldNotBeFound("je.in=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDDbsByJeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where je is not null
        defaultDDbShouldBeFound("je.specified=true");

        // Get all the dDbList where je is null
        defaultDDbShouldNotBeFound("je.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByJeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where je is greater than or equal to DEFAULT_JE
        defaultDDbShouldBeFound("je.greaterThanOrEqual=" + DEFAULT_JE);

        // Get all the dDbList where je is greater than or equal to UPDATED_JE
        defaultDDbShouldNotBeFound("je.greaterThanOrEqual=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDDbsByJeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where je is less than or equal to DEFAULT_JE
        defaultDDbShouldBeFound("je.lessThanOrEqual=" + DEFAULT_JE);

        // Get all the dDbList where je is less than or equal to SMALLER_JE
        defaultDDbShouldNotBeFound("je.lessThanOrEqual=" + SMALLER_JE);
    }

    @Test
    @Transactional
    void getAllDDbsByJeIsLessThanSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where je is less than DEFAULT_JE
        defaultDDbShouldNotBeFound("je.lessThan=" + DEFAULT_JE);

        // Get all the dDbList where je is less than UPDATED_JE
        defaultDDbShouldBeFound("je.lessThan=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDDbsByJeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where je is greater than DEFAULT_JE
        defaultDDbShouldNotBeFound("je.greaterThan=" + DEFAULT_JE);

        // Get all the dDbList where je is greater than SMALLER_JE
        defaultDDbShouldBeFound("je.greaterThan=" + SMALLER_JE);
    }

    @Test
    @Transactional
    void getAllDDbsByMemoIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where memo equals to DEFAULT_MEMO
        defaultDDbShouldBeFound("memo.equals=" + DEFAULT_MEMO);

        // Get all the dDbList where memo equals to UPDATED_MEMO
        defaultDDbShouldNotBeFound("memo.equals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDDbsByMemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where memo not equals to DEFAULT_MEMO
        defaultDDbShouldNotBeFound("memo.notEquals=" + DEFAULT_MEMO);

        // Get all the dDbList where memo not equals to UPDATED_MEMO
        defaultDDbShouldBeFound("memo.notEquals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDDbsByMemoIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where memo in DEFAULT_MEMO or UPDATED_MEMO
        defaultDDbShouldBeFound("memo.in=" + DEFAULT_MEMO + "," + UPDATED_MEMO);

        // Get all the dDbList where memo equals to UPDATED_MEMO
        defaultDDbShouldNotBeFound("memo.in=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDDbsByMemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where memo is not null
        defaultDDbShouldBeFound("memo.specified=true");

        // Get all the dDbList where memo is null
        defaultDDbShouldNotBeFound("memo.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByMemoContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where memo contains DEFAULT_MEMO
        defaultDDbShouldBeFound("memo.contains=" + DEFAULT_MEMO);

        // Get all the dDbList where memo contains UPDATED_MEMO
        defaultDDbShouldNotBeFound("memo.contains=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDDbsByMemoNotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where memo does not contain DEFAULT_MEMO
        defaultDDbShouldNotBeFound("memo.doesNotContain=" + DEFAULT_MEMO);

        // Get all the dDbList where memo does not contain UPDATED_MEMO
        defaultDDbShouldBeFound("memo.doesNotContain=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDDbsByFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where flag equals to DEFAULT_FLAG
        defaultDDbShouldBeFound("flag.equals=" + DEFAULT_FLAG);

        // Get all the dDbList where flag equals to UPDATED_FLAG
        defaultDDbShouldNotBeFound("flag.equals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDDbsByFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where flag not equals to DEFAULT_FLAG
        defaultDDbShouldNotBeFound("flag.notEquals=" + DEFAULT_FLAG);

        // Get all the dDbList where flag not equals to UPDATED_FLAG
        defaultDDbShouldBeFound("flag.notEquals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDDbsByFlagIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where flag in DEFAULT_FLAG or UPDATED_FLAG
        defaultDDbShouldBeFound("flag.in=" + DEFAULT_FLAG + "," + UPDATED_FLAG);

        // Get all the dDbList where flag equals to UPDATED_FLAG
        defaultDDbShouldNotBeFound("flag.in=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDDbsByFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where flag is not null
        defaultDDbShouldBeFound("flag.specified=true");

        // Get all the dDbList where flag is null
        defaultDDbShouldNotBeFound("flag.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByFlagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where flag is greater than or equal to DEFAULT_FLAG
        defaultDDbShouldBeFound("flag.greaterThanOrEqual=" + DEFAULT_FLAG);

        // Get all the dDbList where flag is greater than or equal to UPDATED_FLAG
        defaultDDbShouldNotBeFound("flag.greaterThanOrEqual=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDDbsByFlagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where flag is less than or equal to DEFAULT_FLAG
        defaultDDbShouldBeFound("flag.lessThanOrEqual=" + DEFAULT_FLAG);

        // Get all the dDbList where flag is less than or equal to SMALLER_FLAG
        defaultDDbShouldNotBeFound("flag.lessThanOrEqual=" + SMALLER_FLAG);
    }

    @Test
    @Transactional
    void getAllDDbsByFlagIsLessThanSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where flag is less than DEFAULT_FLAG
        defaultDDbShouldNotBeFound("flag.lessThan=" + DEFAULT_FLAG);

        // Get all the dDbList where flag is less than UPDATED_FLAG
        defaultDDbShouldBeFound("flag.lessThan=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDDbsByFlagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where flag is greater than DEFAULT_FLAG
        defaultDDbShouldNotBeFound("flag.greaterThan=" + DEFAULT_FLAG);

        // Get all the dDbList where flag is greater than SMALLER_FLAG
        defaultDDbShouldBeFound("flag.greaterThan=" + SMALLER_FLAG);
    }

    @Test
    @Transactional
    void getAllDDbsByKcidIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where kcid equals to DEFAULT_KCID
        defaultDDbShouldBeFound("kcid.equals=" + DEFAULT_KCID);

        // Get all the dDbList where kcid equals to UPDATED_KCID
        defaultDDbShouldNotBeFound("kcid.equals=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDDbsByKcidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where kcid not equals to DEFAULT_KCID
        defaultDDbShouldNotBeFound("kcid.notEquals=" + DEFAULT_KCID);

        // Get all the dDbList where kcid not equals to UPDATED_KCID
        defaultDDbShouldBeFound("kcid.notEquals=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDDbsByKcidIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where kcid in DEFAULT_KCID or UPDATED_KCID
        defaultDDbShouldBeFound("kcid.in=" + DEFAULT_KCID + "," + UPDATED_KCID);

        // Get all the dDbList where kcid equals to UPDATED_KCID
        defaultDDbShouldNotBeFound("kcid.in=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDDbsByKcidIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where kcid is not null
        defaultDDbShouldBeFound("kcid.specified=true");

        // Get all the dDbList where kcid is null
        defaultDDbShouldNotBeFound("kcid.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByKcidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where kcid is greater than or equal to DEFAULT_KCID
        defaultDDbShouldBeFound("kcid.greaterThanOrEqual=" + DEFAULT_KCID);

        // Get all the dDbList where kcid is greater than or equal to UPDATED_KCID
        defaultDDbShouldNotBeFound("kcid.greaterThanOrEqual=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDDbsByKcidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where kcid is less than or equal to DEFAULT_KCID
        defaultDDbShouldBeFound("kcid.lessThanOrEqual=" + DEFAULT_KCID);

        // Get all the dDbList where kcid is less than or equal to SMALLER_KCID
        defaultDDbShouldNotBeFound("kcid.lessThanOrEqual=" + SMALLER_KCID);
    }

    @Test
    @Transactional
    void getAllDDbsByKcidIsLessThanSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where kcid is less than DEFAULT_KCID
        defaultDDbShouldNotBeFound("kcid.lessThan=" + DEFAULT_KCID);

        // Get all the dDbList where kcid is less than UPDATED_KCID
        defaultDDbShouldBeFound("kcid.lessThan=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDDbsByKcidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where kcid is greater than DEFAULT_KCID
        defaultDDbShouldNotBeFound("kcid.greaterThan=" + DEFAULT_KCID);

        // Get all the dDbList where kcid is greater than SMALLER_KCID
        defaultDDbShouldBeFound("kcid.greaterThan=" + SMALLER_KCID);
    }

    @Test
    @Transactional
    void getAllDDbsByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where empn equals to DEFAULT_EMPN
        defaultDDbShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the dDbList where empn equals to UPDATED_EMPN
        defaultDDbShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where empn not equals to DEFAULT_EMPN
        defaultDDbShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the dDbList where empn not equals to UPDATED_EMPN
        defaultDDbShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultDDbShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the dDbList where empn equals to UPDATED_EMPN
        defaultDDbShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where empn is not null
        defaultDDbShouldBeFound("empn.specified=true");

        // Get all the dDbList where empn is null
        defaultDDbShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByEmpnContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where empn contains DEFAULT_EMPN
        defaultDDbShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the dDbList where empn contains UPDATED_EMPN
        defaultDDbShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where empn does not contain DEFAULT_EMPN
        defaultDDbShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the dDbList where empn does not contain UPDATED_EMPN
        defaultDDbShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByLrdateIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where lrdate equals to DEFAULT_LRDATE
        defaultDDbShouldBeFound("lrdate.equals=" + DEFAULT_LRDATE);

        // Get all the dDbList where lrdate equals to UPDATED_LRDATE
        defaultDDbShouldNotBeFound("lrdate.equals=" + UPDATED_LRDATE);
    }

    @Test
    @Transactional
    void getAllDDbsByLrdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where lrdate not equals to DEFAULT_LRDATE
        defaultDDbShouldNotBeFound("lrdate.notEquals=" + DEFAULT_LRDATE);

        // Get all the dDbList where lrdate not equals to UPDATED_LRDATE
        defaultDDbShouldBeFound("lrdate.notEquals=" + UPDATED_LRDATE);
    }

    @Test
    @Transactional
    void getAllDDbsByLrdateIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where lrdate in DEFAULT_LRDATE or UPDATED_LRDATE
        defaultDDbShouldBeFound("lrdate.in=" + DEFAULT_LRDATE + "," + UPDATED_LRDATE);

        // Get all the dDbList where lrdate equals to UPDATED_LRDATE
        defaultDDbShouldNotBeFound("lrdate.in=" + UPDATED_LRDATE);
    }

    @Test
    @Transactional
    void getAllDDbsByLrdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where lrdate is not null
        defaultDDbShouldBeFound("lrdate.specified=true");

        // Get all the dDbList where lrdate is null
        defaultDDbShouldNotBeFound("lrdate.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByCkbillnoIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where ckbillno equals to DEFAULT_CKBILLNO
        defaultDDbShouldBeFound("ckbillno.equals=" + DEFAULT_CKBILLNO);

        // Get all the dDbList where ckbillno equals to UPDATED_CKBILLNO
        defaultDDbShouldNotBeFound("ckbillno.equals=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDDbsByCkbillnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where ckbillno not equals to DEFAULT_CKBILLNO
        defaultDDbShouldNotBeFound("ckbillno.notEquals=" + DEFAULT_CKBILLNO);

        // Get all the dDbList where ckbillno not equals to UPDATED_CKBILLNO
        defaultDDbShouldBeFound("ckbillno.notEquals=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDDbsByCkbillnoIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where ckbillno in DEFAULT_CKBILLNO or UPDATED_CKBILLNO
        defaultDDbShouldBeFound("ckbillno.in=" + DEFAULT_CKBILLNO + "," + UPDATED_CKBILLNO);

        // Get all the dDbList where ckbillno equals to UPDATED_CKBILLNO
        defaultDDbShouldNotBeFound("ckbillno.in=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDDbsByCkbillnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where ckbillno is not null
        defaultDDbShouldBeFound("ckbillno.specified=true");

        // Get all the dDbList where ckbillno is null
        defaultDDbShouldNotBeFound("ckbillno.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByCkbillnoContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where ckbillno contains DEFAULT_CKBILLNO
        defaultDDbShouldBeFound("ckbillno.contains=" + DEFAULT_CKBILLNO);

        // Get all the dDbList where ckbillno contains UPDATED_CKBILLNO
        defaultDDbShouldNotBeFound("ckbillno.contains=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDDbsByCkbillnoNotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where ckbillno does not contain DEFAULT_CKBILLNO
        defaultDDbShouldNotBeFound("ckbillno.doesNotContain=" + DEFAULT_CKBILLNO);

        // Get all the dDbList where ckbillno does not contain UPDATED_CKBILLNO
        defaultDDbShouldBeFound("ckbillno.doesNotContain=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDDbsByf1IsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1 equals to DEFAULT_F_1
        defaultDDbShouldBeFound("f1.equals=" + DEFAULT_F_1);

        // Get all the dDbList where f1 equals to UPDATED_F_1
        defaultDDbShouldNotBeFound("f1.equals=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDDbsByf1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1 not equals to DEFAULT_F_1
        defaultDDbShouldNotBeFound("f1.notEquals=" + DEFAULT_F_1);

        // Get all the dDbList where f1 not equals to UPDATED_F_1
        defaultDDbShouldBeFound("f1.notEquals=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDDbsByf1IsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1 in DEFAULT_F_1 or UPDATED_F_1
        defaultDDbShouldBeFound("f1.in=" + DEFAULT_F_1 + "," + UPDATED_F_1);

        // Get all the dDbList where f1 equals to UPDATED_F_1
        defaultDDbShouldNotBeFound("f1.in=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDDbsByf1IsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1 is not null
        defaultDDbShouldBeFound("f1.specified=true");

        // Get all the dDbList where f1 is null
        defaultDDbShouldNotBeFound("f1.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByf1ContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1 contains DEFAULT_F_1
        defaultDDbShouldBeFound("f1.contains=" + DEFAULT_F_1);

        // Get all the dDbList where f1 contains UPDATED_F_1
        defaultDDbShouldNotBeFound("f1.contains=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDDbsByf1NotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1 does not contain DEFAULT_F_1
        defaultDDbShouldNotBeFound("f1.doesNotContain=" + DEFAULT_F_1);

        // Get all the dDbList where f1 does not contain UPDATED_F_1
        defaultDDbShouldBeFound("f1.doesNotContain=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDDbsByf2IsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2 equals to DEFAULT_F_2
        defaultDDbShouldBeFound("f2.equals=" + DEFAULT_F_2);

        // Get all the dDbList where f2 equals to UPDATED_F_2
        defaultDDbShouldNotBeFound("f2.equals=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDDbsByf2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2 not equals to DEFAULT_F_2
        defaultDDbShouldNotBeFound("f2.notEquals=" + DEFAULT_F_2);

        // Get all the dDbList where f2 not equals to UPDATED_F_2
        defaultDDbShouldBeFound("f2.notEquals=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDDbsByf2IsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2 in DEFAULT_F_2 or UPDATED_F_2
        defaultDDbShouldBeFound("f2.in=" + DEFAULT_F_2 + "," + UPDATED_F_2);

        // Get all the dDbList where f2 equals to UPDATED_F_2
        defaultDDbShouldNotBeFound("f2.in=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDDbsByf2IsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2 is not null
        defaultDDbShouldBeFound("f2.specified=true");

        // Get all the dDbList where f2 is null
        defaultDDbShouldNotBeFound("f2.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByf2ContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2 contains DEFAULT_F_2
        defaultDDbShouldBeFound("f2.contains=" + DEFAULT_F_2);

        // Get all the dDbList where f2 contains UPDATED_F_2
        defaultDDbShouldNotBeFound("f2.contains=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDDbsByf2NotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2 does not contain DEFAULT_F_2
        defaultDDbShouldNotBeFound("f2.doesNotContain=" + DEFAULT_F_2);

        // Get all the dDbList where f2 does not contain UPDATED_F_2
        defaultDDbShouldBeFound("f2.doesNotContain=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDDbsByf1empnIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1empn equals to DEFAULT_F_1_EMPN
        defaultDDbShouldBeFound("f1empn.equals=" + DEFAULT_F_1_EMPN);

        // Get all the dDbList where f1empn equals to UPDATED_F_1_EMPN
        defaultDDbShouldNotBeFound("f1empn.equals=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByf1empnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1empn not equals to DEFAULT_F_1_EMPN
        defaultDDbShouldNotBeFound("f1empn.notEquals=" + DEFAULT_F_1_EMPN);

        // Get all the dDbList where f1empn not equals to UPDATED_F_1_EMPN
        defaultDDbShouldBeFound("f1empn.notEquals=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByf1empnIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1empn in DEFAULT_F_1_EMPN or UPDATED_F_1_EMPN
        defaultDDbShouldBeFound("f1empn.in=" + DEFAULT_F_1_EMPN + "," + UPDATED_F_1_EMPN);

        // Get all the dDbList where f1empn equals to UPDATED_F_1_EMPN
        defaultDDbShouldNotBeFound("f1empn.in=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByf1empnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1empn is not null
        defaultDDbShouldBeFound("f1empn.specified=true");

        // Get all the dDbList where f1empn is null
        defaultDDbShouldNotBeFound("f1empn.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByf1empnContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1empn contains DEFAULT_F_1_EMPN
        defaultDDbShouldBeFound("f1empn.contains=" + DEFAULT_F_1_EMPN);

        // Get all the dDbList where f1empn contains UPDATED_F_1_EMPN
        defaultDDbShouldNotBeFound("f1empn.contains=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByf1empnNotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1empn does not contain DEFAULT_F_1_EMPN
        defaultDDbShouldNotBeFound("f1empn.doesNotContain=" + DEFAULT_F_1_EMPN);

        // Get all the dDbList where f1empn does not contain UPDATED_F_1_EMPN
        defaultDDbShouldBeFound("f1empn.doesNotContain=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByf2empnIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2empn equals to DEFAULT_F_2_EMPN
        defaultDDbShouldBeFound("f2empn.equals=" + DEFAULT_F_2_EMPN);

        // Get all the dDbList where f2empn equals to UPDATED_F_2_EMPN
        defaultDDbShouldNotBeFound("f2empn.equals=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByf2empnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2empn not equals to DEFAULT_F_2_EMPN
        defaultDDbShouldNotBeFound("f2empn.notEquals=" + DEFAULT_F_2_EMPN);

        // Get all the dDbList where f2empn not equals to UPDATED_F_2_EMPN
        defaultDDbShouldBeFound("f2empn.notEquals=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByf2empnIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2empn in DEFAULT_F_2_EMPN or UPDATED_F_2_EMPN
        defaultDDbShouldBeFound("f2empn.in=" + DEFAULT_F_2_EMPN + "," + UPDATED_F_2_EMPN);

        // Get all the dDbList where f2empn equals to UPDATED_F_2_EMPN
        defaultDDbShouldNotBeFound("f2empn.in=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByf2empnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2empn is not null
        defaultDDbShouldBeFound("f2empn.specified=true");

        // Get all the dDbList where f2empn is null
        defaultDDbShouldNotBeFound("f2empn.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByf2empnContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2empn contains DEFAULT_F_2_EMPN
        defaultDDbShouldBeFound("f2empn.contains=" + DEFAULT_F_2_EMPN);

        // Get all the dDbList where f2empn contains UPDATED_F_2_EMPN
        defaultDDbShouldNotBeFound("f2empn.contains=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByf2empnNotContainsSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2empn does not contain DEFAULT_F_2_EMPN
        defaultDDbShouldNotBeFound("f2empn.doesNotContain=" + DEFAULT_F_2_EMPN);

        // Get all the dDbList where f2empn does not contain UPDATED_F_2_EMPN
        defaultDDbShouldBeFound("f2empn.doesNotContain=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDDbsByf1sjIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1sj equals to DEFAULT_F_1_SJ
        defaultDDbShouldBeFound("f1sj.equals=" + DEFAULT_F_1_SJ);

        // Get all the dDbList where f1sj equals to UPDATED_F_1_SJ
        defaultDDbShouldNotBeFound("f1sj.equals=" + UPDATED_F_1_SJ);
    }

    @Test
    @Transactional
    void getAllDDbsByf1sjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1sj not equals to DEFAULT_F_1_SJ
        defaultDDbShouldNotBeFound("f1sj.notEquals=" + DEFAULT_F_1_SJ);

        // Get all the dDbList where f1sj not equals to UPDATED_F_1_SJ
        defaultDDbShouldBeFound("f1sj.notEquals=" + UPDATED_F_1_SJ);
    }

    @Test
    @Transactional
    void getAllDDbsByf1sjIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1sj in DEFAULT_F_1_SJ or UPDATED_F_1_SJ
        defaultDDbShouldBeFound("f1sj.in=" + DEFAULT_F_1_SJ + "," + UPDATED_F_1_SJ);

        // Get all the dDbList where f1sj equals to UPDATED_F_1_SJ
        defaultDDbShouldNotBeFound("f1sj.in=" + UPDATED_F_1_SJ);
    }

    @Test
    @Transactional
    void getAllDDbsByf1sjIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f1sj is not null
        defaultDDbShouldBeFound("f1sj.specified=true");

        // Get all the dDbList where f1sj is null
        defaultDDbShouldNotBeFound("f1sj.specified=false");
    }

    @Test
    @Transactional
    void getAllDDbsByf2sjIsEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2sj equals to DEFAULT_F_2_SJ
        defaultDDbShouldBeFound("f2sj.equals=" + DEFAULT_F_2_SJ);

        // Get all the dDbList where f2sj equals to UPDATED_F_2_SJ
        defaultDDbShouldNotBeFound("f2sj.equals=" + UPDATED_F_2_SJ);
    }

    @Test
    @Transactional
    void getAllDDbsByf2sjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2sj not equals to DEFAULT_F_2_SJ
        defaultDDbShouldNotBeFound("f2sj.notEquals=" + DEFAULT_F_2_SJ);

        // Get all the dDbList where f2sj not equals to UPDATED_F_2_SJ
        defaultDDbShouldBeFound("f2sj.notEquals=" + UPDATED_F_2_SJ);
    }

    @Test
    @Transactional
    void getAllDDbsByf2sjIsInShouldWork() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2sj in DEFAULT_F_2_SJ or UPDATED_F_2_SJ
        defaultDDbShouldBeFound("f2sj.in=" + DEFAULT_F_2_SJ + "," + UPDATED_F_2_SJ);

        // Get all the dDbList where f2sj equals to UPDATED_F_2_SJ
        defaultDDbShouldNotBeFound("f2sj.in=" + UPDATED_F_2_SJ);
    }

    @Test
    @Transactional
    void getAllDDbsByf2sjIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        // Get all the dDbList where f2sj is not null
        defaultDDbShouldBeFound("f2sj.specified=true");

        // Get all the dDbList where f2sj is null
        defaultDDbShouldNotBeFound("f2sj.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDDbShouldBeFound(String filter) throws Exception {
        restDDbMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dDb.getId().intValue())))
            .andExpect(jsonPath("$.[*].dbdate").value(hasItem(DEFAULT_DBDATE.toString())))
            .andExpect(jsonPath("$.[*].dbbillno").value(hasItem(DEFAULT_DBBILLNO)))
            .andExpect(jsonPath("$.[*].rdepot").value(hasItem(DEFAULT_RDEPOT)))
            .andExpect(jsonPath("$.[*].cdepot").value(hasItem(DEFAULT_CDEPOT)))
            .andExpect(jsonPath("$.[*].jbr").value(hasItem(DEFAULT_JBR)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].spbm").value(hasItem(DEFAULT_SPBM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].je").value(hasItem(sameNumber(DEFAULT_JE))))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].kcid").value(hasItem(DEFAULT_KCID.intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].lrdate").value(hasItem(DEFAULT_LRDATE.toString())))
            .andExpect(jsonPath("$.[*].ckbillno").value(hasItem(DEFAULT_CKBILLNO)))
            .andExpect(jsonPath("$.[*].f1").value(hasItem(DEFAULT_F_1)))
            .andExpect(jsonPath("$.[*].f2").value(hasItem(DEFAULT_F_2)))
            .andExpect(jsonPath("$.[*].f1empn").value(hasItem(DEFAULT_F_1_EMPN)))
            .andExpect(jsonPath("$.[*].f2empn").value(hasItem(DEFAULT_F_2_EMPN)))
            .andExpect(jsonPath("$.[*].f1sj").value(hasItem(DEFAULT_F_1_SJ.toString())))
            .andExpect(jsonPath("$.[*].f2sj").value(hasItem(DEFAULT_F_2_SJ.toString())));

        // Check, that the count call also returns 1
        restDDbMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDDbShouldNotBeFound(String filter) throws Exception {
        restDDbMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDDbMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDDb() throws Exception {
        // Get the dDb
        restDDbMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDDb() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        int databaseSizeBeforeUpdate = dDbRepository.findAll().size();

        // Update the dDb
        DDb updatedDDb = dDbRepository.findById(dDb.getId()).get();
        // Disconnect from session so that the updates on updatedDDb are not directly saved in db
        em.detach(updatedDDb);
        updatedDDb
            .dbdate(UPDATED_DBDATE)
            .dbbillno(UPDATED_DBBILLNO)
            .rdepot(UPDATED_RDEPOT)
            .cdepot(UPDATED_CDEPOT)
            .jbr(UPDATED_JBR)
            .remark(UPDATED_REMARK)
            .spbm(UPDATED_SPBM)
            .spmc(UPDATED_SPMC)
            .unit(UPDATED_UNIT)
            .price(UPDATED_PRICE)
            .sl(UPDATED_SL)
            .je(UPDATED_JE)
            .memo(UPDATED_MEMO)
            .flag(UPDATED_FLAG)
            .kcid(UPDATED_KCID)
            .empn(UPDATED_EMPN)
            .lrdate(UPDATED_LRDATE)
            .ckbillno(UPDATED_CKBILLNO)
            .f1(UPDATED_F_1)
            .f2(UPDATED_F_2)
            .f1empn(UPDATED_F_1_EMPN)
            .f2empn(UPDATED_F_2_EMPN)
            .f1sj(UPDATED_F_1_SJ)
            .f2sj(UPDATED_F_2_SJ);
        DDbDTO dDbDTO = dDbMapper.toDto(updatedDDb);

        restDDbMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dDbDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dDbDTO))
            )
            .andExpect(status().isOk());

        // Validate the DDb in the database
        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeUpdate);
        DDb testDDb = dDbList.get(dDbList.size() - 1);
        assertThat(testDDb.getDbdate()).isEqualTo(UPDATED_DBDATE);
        assertThat(testDDb.getDbbillno()).isEqualTo(UPDATED_DBBILLNO);
        assertThat(testDDb.getRdepot()).isEqualTo(UPDATED_RDEPOT);
        assertThat(testDDb.getCdepot()).isEqualTo(UPDATED_CDEPOT);
        assertThat(testDDb.getJbr()).isEqualTo(UPDATED_JBR);
        assertThat(testDDb.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testDDb.getSpbm()).isEqualTo(UPDATED_SPBM);
        assertThat(testDDb.getSpmc()).isEqualTo(UPDATED_SPMC);
        assertThat(testDDb.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testDDb.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDDb.getSl()).isEqualTo(UPDATED_SL);
        assertThat(testDDb.getJe()).isEqualTo(UPDATED_JE);
        assertThat(testDDb.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testDDb.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testDDb.getKcid()).isEqualTo(UPDATED_KCID);
        assertThat(testDDb.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testDDb.getLrdate()).isEqualTo(UPDATED_LRDATE);
        assertThat(testDDb.getCkbillno()).isEqualTo(UPDATED_CKBILLNO);
        assertThat(testDDb.getf1()).isEqualTo(UPDATED_F_1);
        assertThat(testDDb.getf2()).isEqualTo(UPDATED_F_2);
        assertThat(testDDb.getf1empn()).isEqualTo(UPDATED_F_1_EMPN);
        assertThat(testDDb.getf2empn()).isEqualTo(UPDATED_F_2_EMPN);
        assertThat(testDDb.getf1sj()).isEqualTo(UPDATED_F_1_SJ);
        assertThat(testDDb.getf2sj()).isEqualTo(UPDATED_F_2_SJ);

        // Validate the DDb in Elasticsearch
        verify(mockDDbSearchRepository).save(testDDb);
    }

    @Test
    @Transactional
    void putNonExistingDDb() throws Exception {
        int databaseSizeBeforeUpdate = dDbRepository.findAll().size();
        dDb.setId(count.incrementAndGet());

        // Create the DDb
        DDbDTO dDbDTO = dDbMapper.toDto(dDb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDDbMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dDbDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dDbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DDb in the database
        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDb in Elasticsearch
        verify(mockDDbSearchRepository, times(0)).save(dDb);
    }

    @Test
    @Transactional
    void putWithIdMismatchDDb() throws Exception {
        int databaseSizeBeforeUpdate = dDbRepository.findAll().size();
        dDb.setId(count.incrementAndGet());

        // Create the DDb
        DDbDTO dDbDTO = dDbMapper.toDto(dDb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDDbMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dDbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DDb in the database
        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDb in Elasticsearch
        verify(mockDDbSearchRepository, times(0)).save(dDb);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDDb() throws Exception {
        int databaseSizeBeforeUpdate = dDbRepository.findAll().size();
        dDb.setId(count.incrementAndGet());

        // Create the DDb
        DDbDTO dDbDTO = dDbMapper.toDto(dDb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDDbMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDbDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DDb in the database
        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDb in Elasticsearch
        verify(mockDDbSearchRepository, times(0)).save(dDb);
    }

    @Test
    @Transactional
    void partialUpdateDDbWithPatch() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        int databaseSizeBeforeUpdate = dDbRepository.findAll().size();

        // Update the dDb using partial update
        DDb partialUpdatedDDb = new DDb();
        partialUpdatedDDb.setId(dDb.getId());

        partialUpdatedDDb
            .dbdate(UPDATED_DBDATE)
            .rdepot(UPDATED_RDEPOT)
            .jbr(UPDATED_JBR)
            .unit(UPDATED_UNIT)
            .je(UPDATED_JE)
            .memo(UPDATED_MEMO)
            .flag(UPDATED_FLAG)
            .empn(UPDATED_EMPN)
            .f2(UPDATED_F_2)
            .f1empn(UPDATED_F_1_EMPN)
            .f1sj(UPDATED_F_1_SJ)
            .f2sj(UPDATED_F_2_SJ);

        restDDbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDDb.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDDb))
            )
            .andExpect(status().isOk());

        // Validate the DDb in the database
        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeUpdate);
        DDb testDDb = dDbList.get(dDbList.size() - 1);
        assertThat(testDDb.getDbdate()).isEqualTo(UPDATED_DBDATE);
        assertThat(testDDb.getDbbillno()).isEqualTo(DEFAULT_DBBILLNO);
        assertThat(testDDb.getRdepot()).isEqualTo(UPDATED_RDEPOT);
        assertThat(testDDb.getCdepot()).isEqualTo(DEFAULT_CDEPOT);
        assertThat(testDDb.getJbr()).isEqualTo(UPDATED_JBR);
        assertThat(testDDb.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testDDb.getSpbm()).isEqualTo(DEFAULT_SPBM);
        assertThat(testDDb.getSpmc()).isEqualTo(DEFAULT_SPMC);
        assertThat(testDDb.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testDDb.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testDDb.getSl()).isEqualByComparingTo(DEFAULT_SL);
        assertThat(testDDb.getJe()).isEqualByComparingTo(UPDATED_JE);
        assertThat(testDDb.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testDDb.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testDDb.getKcid()).isEqualTo(DEFAULT_KCID);
        assertThat(testDDb.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testDDb.getLrdate()).isEqualTo(DEFAULT_LRDATE);
        assertThat(testDDb.getCkbillno()).isEqualTo(DEFAULT_CKBILLNO);
        assertThat(testDDb.getf1()).isEqualTo(DEFAULT_F_1);
        assertThat(testDDb.getf2()).isEqualTo(UPDATED_F_2);
        assertThat(testDDb.getf1empn()).isEqualTo(UPDATED_F_1_EMPN);
        assertThat(testDDb.getf2empn()).isEqualTo(DEFAULT_F_2_EMPN);
        assertThat(testDDb.getf1sj()).isEqualTo(UPDATED_F_1_SJ);
        assertThat(testDDb.getf2sj()).isEqualTo(UPDATED_F_2_SJ);
    }

    @Test
    @Transactional
    void fullUpdateDDbWithPatch() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        int databaseSizeBeforeUpdate = dDbRepository.findAll().size();

        // Update the dDb using partial update
        DDb partialUpdatedDDb = new DDb();
        partialUpdatedDDb.setId(dDb.getId());

        partialUpdatedDDb
            .dbdate(UPDATED_DBDATE)
            .dbbillno(UPDATED_DBBILLNO)
            .rdepot(UPDATED_RDEPOT)
            .cdepot(UPDATED_CDEPOT)
            .jbr(UPDATED_JBR)
            .remark(UPDATED_REMARK)
            .spbm(UPDATED_SPBM)
            .spmc(UPDATED_SPMC)
            .unit(UPDATED_UNIT)
            .price(UPDATED_PRICE)
            .sl(UPDATED_SL)
            .je(UPDATED_JE)
            .memo(UPDATED_MEMO)
            .flag(UPDATED_FLAG)
            .kcid(UPDATED_KCID)
            .empn(UPDATED_EMPN)
            .lrdate(UPDATED_LRDATE)
            .ckbillno(UPDATED_CKBILLNO)
            .f1(UPDATED_F_1)
            .f2(UPDATED_F_2)
            .f1empn(UPDATED_F_1_EMPN)
            .f2empn(UPDATED_F_2_EMPN)
            .f1sj(UPDATED_F_1_SJ)
            .f2sj(UPDATED_F_2_SJ);

        restDDbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDDb.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDDb))
            )
            .andExpect(status().isOk());

        // Validate the DDb in the database
        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeUpdate);
        DDb testDDb = dDbList.get(dDbList.size() - 1);
        assertThat(testDDb.getDbdate()).isEqualTo(UPDATED_DBDATE);
        assertThat(testDDb.getDbbillno()).isEqualTo(UPDATED_DBBILLNO);
        assertThat(testDDb.getRdepot()).isEqualTo(UPDATED_RDEPOT);
        assertThat(testDDb.getCdepot()).isEqualTo(UPDATED_CDEPOT);
        assertThat(testDDb.getJbr()).isEqualTo(UPDATED_JBR);
        assertThat(testDDb.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testDDb.getSpbm()).isEqualTo(UPDATED_SPBM);
        assertThat(testDDb.getSpmc()).isEqualTo(UPDATED_SPMC);
        assertThat(testDDb.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testDDb.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testDDb.getSl()).isEqualByComparingTo(UPDATED_SL);
        assertThat(testDDb.getJe()).isEqualByComparingTo(UPDATED_JE);
        assertThat(testDDb.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testDDb.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testDDb.getKcid()).isEqualTo(UPDATED_KCID);
        assertThat(testDDb.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testDDb.getLrdate()).isEqualTo(UPDATED_LRDATE);
        assertThat(testDDb.getCkbillno()).isEqualTo(UPDATED_CKBILLNO);
        assertThat(testDDb.getf1()).isEqualTo(UPDATED_F_1);
        assertThat(testDDb.getf2()).isEqualTo(UPDATED_F_2);
        assertThat(testDDb.getf1empn()).isEqualTo(UPDATED_F_1_EMPN);
        assertThat(testDDb.getf2empn()).isEqualTo(UPDATED_F_2_EMPN);
        assertThat(testDDb.getf1sj()).isEqualTo(UPDATED_F_1_SJ);
        assertThat(testDDb.getf2sj()).isEqualTo(UPDATED_F_2_SJ);
    }

    @Test
    @Transactional
    void patchNonExistingDDb() throws Exception {
        int databaseSizeBeforeUpdate = dDbRepository.findAll().size();
        dDb.setId(count.incrementAndGet());

        // Create the DDb
        DDbDTO dDbDTO = dDbMapper.toDto(dDb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDDbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dDbDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dDbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DDb in the database
        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDb in Elasticsearch
        verify(mockDDbSearchRepository, times(0)).save(dDb);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDDb() throws Exception {
        int databaseSizeBeforeUpdate = dDbRepository.findAll().size();
        dDb.setId(count.incrementAndGet());

        // Create the DDb
        DDbDTO dDbDTO = dDbMapper.toDto(dDb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDDbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dDbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DDb in the database
        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDb in Elasticsearch
        verify(mockDDbSearchRepository, times(0)).save(dDb);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDDb() throws Exception {
        int databaseSizeBeforeUpdate = dDbRepository.findAll().size();
        dDb.setId(count.incrementAndGet());

        // Create the DDb
        DDbDTO dDbDTO = dDbMapper.toDto(dDb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDDbMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dDbDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DDb in the database
        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDb in Elasticsearch
        verify(mockDDbSearchRepository, times(0)).save(dDb);
    }

    @Test
    @Transactional
    void deleteDDb() throws Exception {
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);

        int databaseSizeBeforeDelete = dDbRepository.findAll().size();

        // Delete the dDb
        restDDbMockMvc.perform(delete(ENTITY_API_URL_ID, dDb.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DDb> dDbList = dDbRepository.findAll();
        assertThat(dDbList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DDb in Elasticsearch
        verify(mockDDbSearchRepository, times(1)).deleteById(dDb.getId());
    }

    @Test
    @Transactional
    void searchDDb() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dDbRepository.saveAndFlush(dDb);
        when(mockDDbSearchRepository.search(queryStringQuery("id:" + dDb.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dDb), PageRequest.of(0, 1), 1));

        // Search the dDb
        restDDbMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dDb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dDb.getId().intValue())))
            .andExpect(jsonPath("$.[*].dbdate").value(hasItem(DEFAULT_DBDATE.toString())))
            .andExpect(jsonPath("$.[*].dbbillno").value(hasItem(DEFAULT_DBBILLNO)))
            .andExpect(jsonPath("$.[*].rdepot").value(hasItem(DEFAULT_RDEPOT)))
            .andExpect(jsonPath("$.[*].cdepot").value(hasItem(DEFAULT_CDEPOT)))
            .andExpect(jsonPath("$.[*].jbr").value(hasItem(DEFAULT_JBR)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].spbm").value(hasItem(DEFAULT_SPBM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].je").value(hasItem(sameNumber(DEFAULT_JE))))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].kcid").value(hasItem(DEFAULT_KCID.intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].lrdate").value(hasItem(DEFAULT_LRDATE.toString())))
            .andExpect(jsonPath("$.[*].ckbillno").value(hasItem(DEFAULT_CKBILLNO)))
            .andExpect(jsonPath("$.[*].f1").value(hasItem(DEFAULT_F_1)))
            .andExpect(jsonPath("$.[*].f2").value(hasItem(DEFAULT_F_2)))
            .andExpect(jsonPath("$.[*].f1empn").value(hasItem(DEFAULT_F_1_EMPN)))
            .andExpect(jsonPath("$.[*].f2empn").value(hasItem(DEFAULT_F_2_EMPN)))
            .andExpect(jsonPath("$.[*].f1sj").value(hasItem(DEFAULT_F_1_SJ.toString())))
            .andExpect(jsonPath("$.[*].f2sj").value(hasItem(DEFAULT_F_2_SJ.toString())));
    }
}
