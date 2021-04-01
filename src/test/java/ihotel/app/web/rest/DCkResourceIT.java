package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DCk;
import ihotel.app.repository.DCkRepository;
import ihotel.app.repository.search.DCkSearchRepository;
import ihotel.app.service.criteria.DCkCriteria;
import ihotel.app.service.dto.DCkDTO;
import ihotel.app.service.mapper.DCkMapper;
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
 * Integration tests for the {@link DCkResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DCkResourceIT {

    private static final String DEFAULT_DEPOT = "AAAAAAAAAA";
    private static final String UPDATED_DEPOT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CKDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CKDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CKBILLNO = "AAAAAAAAAA";
    private static final String UPDATED_CKBILLNO = "BBBBBBBBBB";

    private static final String DEFAULT_DEPTNAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPTNAME = "BBBBBBBBBB";

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

    private static final Long DEFAULT_DB_SIGN = 1L;
    private static final Long UPDATED_DB_SIGN = 2L;
    private static final Long SMALLER_DB_SIGN = 1L - 1L;

    private static final String DEFAULT_CKTYPE = "AAAAAAAAAA";
    private static final String UPDATED_CKTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final Instant DEFAULT_LRDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LRDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_KCID = 1D;
    private static final Double UPDATED_KCID = 2D;
    private static final Double SMALLER_KCID = 1D - 1D;

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

    private static final String ENTITY_API_URL = "/api/d-cks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-cks";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DCkRepository dCkRepository;

    @Autowired
    private DCkMapper dCkMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DCkSearchRepositoryMockConfiguration
     */
    @Autowired
    private DCkSearchRepository mockDCkSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDCkMockMvc;

    private DCk dCk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DCk createEntity(EntityManager em) {
        DCk dCk = new DCk()
            .depot(DEFAULT_DEPOT)
            .ckdate(DEFAULT_CKDATE)
            .ckbillno(DEFAULT_CKBILLNO)
            .deptname(DEFAULT_DEPTNAME)
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
            .dbSign(DEFAULT_DB_SIGN)
            .cktype(DEFAULT_CKTYPE)
            .empn(DEFAULT_EMPN)
            .lrdate(DEFAULT_LRDATE)
            .kcid(DEFAULT_KCID)
            .f1(DEFAULT_F_1)
            .f2(DEFAULT_F_2)
            .f1empn(DEFAULT_F_1_EMPN)
            .f2empn(DEFAULT_F_2_EMPN)
            .f1sj(DEFAULT_F_1_SJ)
            .f2sj(DEFAULT_F_2_SJ);
        return dCk;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DCk createUpdatedEntity(EntityManager em) {
        DCk dCk = new DCk()
            .depot(UPDATED_DEPOT)
            .ckdate(UPDATED_CKDATE)
            .ckbillno(UPDATED_CKBILLNO)
            .deptname(UPDATED_DEPTNAME)
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
            .dbSign(UPDATED_DB_SIGN)
            .cktype(UPDATED_CKTYPE)
            .empn(UPDATED_EMPN)
            .lrdate(UPDATED_LRDATE)
            .kcid(UPDATED_KCID)
            .f1(UPDATED_F_1)
            .f2(UPDATED_F_2)
            .f1empn(UPDATED_F_1_EMPN)
            .f2empn(UPDATED_F_2_EMPN)
            .f1sj(UPDATED_F_1_SJ)
            .f2sj(UPDATED_F_2_SJ);
        return dCk;
    }

    @BeforeEach
    public void initTest() {
        dCk = createEntity(em);
    }

    @Test
    @Transactional
    void createDCk() throws Exception {
        int databaseSizeBeforeCreate = dCkRepository.findAll().size();
        // Create the DCk
        DCkDTO dCkDTO = dCkMapper.toDto(dCk);
        restDCkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCkDTO)))
            .andExpect(status().isCreated());

        // Validate the DCk in the database
        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeCreate + 1);
        DCk testDCk = dCkList.get(dCkList.size() - 1);
        assertThat(testDCk.getDepot()).isEqualTo(DEFAULT_DEPOT);
        assertThat(testDCk.getCkdate()).isEqualTo(DEFAULT_CKDATE);
        assertThat(testDCk.getCkbillno()).isEqualTo(DEFAULT_CKBILLNO);
        assertThat(testDCk.getDeptname()).isEqualTo(DEFAULT_DEPTNAME);
        assertThat(testDCk.getJbr()).isEqualTo(DEFAULT_JBR);
        assertThat(testDCk.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testDCk.getSpbm()).isEqualTo(DEFAULT_SPBM);
        assertThat(testDCk.getSpmc()).isEqualTo(DEFAULT_SPMC);
        assertThat(testDCk.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testDCk.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testDCk.getSl()).isEqualByComparingTo(DEFAULT_SL);
        assertThat(testDCk.getJe()).isEqualByComparingTo(DEFAULT_JE);
        assertThat(testDCk.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testDCk.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testDCk.getDbSign()).isEqualTo(DEFAULT_DB_SIGN);
        assertThat(testDCk.getCktype()).isEqualTo(DEFAULT_CKTYPE);
        assertThat(testDCk.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testDCk.getLrdate()).isEqualTo(DEFAULT_LRDATE);
        assertThat(testDCk.getKcid()).isEqualTo(DEFAULT_KCID);
        assertThat(testDCk.getf1()).isEqualTo(DEFAULT_F_1);
        assertThat(testDCk.getf2()).isEqualTo(DEFAULT_F_2);
        assertThat(testDCk.getf1empn()).isEqualTo(DEFAULT_F_1_EMPN);
        assertThat(testDCk.getf2empn()).isEqualTo(DEFAULT_F_2_EMPN);
        assertThat(testDCk.getf1sj()).isEqualTo(DEFAULT_F_1_SJ);
        assertThat(testDCk.getf2sj()).isEqualTo(DEFAULT_F_2_SJ);

        // Validate the DCk in Elasticsearch
        verify(mockDCkSearchRepository, times(1)).save(testDCk);
    }

    @Test
    @Transactional
    void createDCkWithExistingId() throws Exception {
        // Create the DCk with an existing ID
        dCk.setId(1L);
        DCkDTO dCkDTO = dCkMapper.toDto(dCk);

        int databaseSizeBeforeCreate = dCkRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDCkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DCk in the database
        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeCreate);

        // Validate the DCk in Elasticsearch
        verify(mockDCkSearchRepository, times(0)).save(dCk);
    }

    @Test
    @Transactional
    void checkDepotIsRequired() throws Exception {
        int databaseSizeBeforeTest = dCkRepository.findAll().size();
        // set the field null
        dCk.setDepot(null);

        // Create the DCk, which fails.
        DCkDTO dCkDTO = dCkMapper.toDto(dCk);

        restDCkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCkDTO)))
            .andExpect(status().isBadRequest());

        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCkdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dCkRepository.findAll().size();
        // set the field null
        dCk.setCkdate(null);

        // Create the DCk, which fails.
        DCkDTO dCkDTO = dCkMapper.toDto(dCk);

        restDCkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCkDTO)))
            .andExpect(status().isBadRequest());

        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCkbillnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dCkRepository.findAll().size();
        // set the field null
        dCk.setCkbillno(null);

        // Create the DCk, which fails.
        DCkDTO dCkDTO = dCkMapper.toDto(dCk);

        restDCkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCkDTO)))
            .andExpect(status().isBadRequest());

        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpbmIsRequired() throws Exception {
        int databaseSizeBeforeTest = dCkRepository.findAll().size();
        // set the field null
        dCk.setSpbm(null);

        // Create the DCk, which fails.
        DCkDTO dCkDTO = dCkMapper.toDto(dCk);

        restDCkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCkDTO)))
            .andExpect(status().isBadRequest());

        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpmcIsRequired() throws Exception {
        int databaseSizeBeforeTest = dCkRepository.findAll().size();
        // set the field null
        dCk.setSpmc(null);

        // Create the DCk, which fails.
        DCkDTO dCkDTO = dCkMapper.toDto(dCk);

        restDCkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCkDTO)))
            .andExpect(status().isBadRequest());

        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDCks() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList
        restDCkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dCk.getId().intValue())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].ckdate").value(hasItem(DEFAULT_CKDATE.toString())))
            .andExpect(jsonPath("$.[*].ckbillno").value(hasItem(DEFAULT_CKBILLNO)))
            .andExpect(jsonPath("$.[*].deptname").value(hasItem(DEFAULT_DEPTNAME)))
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
            .andExpect(jsonPath("$.[*].dbSign").value(hasItem(DEFAULT_DB_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].cktype").value(hasItem(DEFAULT_CKTYPE)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].lrdate").value(hasItem(DEFAULT_LRDATE.toString())))
            .andExpect(jsonPath("$.[*].kcid").value(hasItem(DEFAULT_KCID.doubleValue())))
            .andExpect(jsonPath("$.[*].f1").value(hasItem(DEFAULT_F_1)))
            .andExpect(jsonPath("$.[*].f2").value(hasItem(DEFAULT_F_2)))
            .andExpect(jsonPath("$.[*].f1empn").value(hasItem(DEFAULT_F_1_EMPN)))
            .andExpect(jsonPath("$.[*].f2empn").value(hasItem(DEFAULT_F_2_EMPN)))
            .andExpect(jsonPath("$.[*].f1sj").value(hasItem(DEFAULT_F_1_SJ.toString())))
            .andExpect(jsonPath("$.[*].f2sj").value(hasItem(DEFAULT_F_2_SJ.toString())));
    }

    @Test
    @Transactional
    void getDCk() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get the dCk
        restDCkMockMvc
            .perform(get(ENTITY_API_URL_ID, dCk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dCk.getId().intValue()))
            .andExpect(jsonPath("$.depot").value(DEFAULT_DEPOT))
            .andExpect(jsonPath("$.ckdate").value(DEFAULT_CKDATE.toString()))
            .andExpect(jsonPath("$.ckbillno").value(DEFAULT_CKBILLNO))
            .andExpect(jsonPath("$.deptname").value(DEFAULT_DEPTNAME))
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
            .andExpect(jsonPath("$.dbSign").value(DEFAULT_DB_SIGN.intValue()))
            .andExpect(jsonPath("$.cktype").value(DEFAULT_CKTYPE))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.lrdate").value(DEFAULT_LRDATE.toString()))
            .andExpect(jsonPath("$.kcid").value(DEFAULT_KCID.doubleValue()))
            .andExpect(jsonPath("$.f1").value(DEFAULT_F_1))
            .andExpect(jsonPath("$.f2").value(DEFAULT_F_2))
            .andExpect(jsonPath("$.f1empn").value(DEFAULT_F_1_EMPN))
            .andExpect(jsonPath("$.f2empn").value(DEFAULT_F_2_EMPN))
            .andExpect(jsonPath("$.f1sj").value(DEFAULT_F_1_SJ.toString()))
            .andExpect(jsonPath("$.f2sj").value(DEFAULT_F_2_SJ.toString()));
    }

    @Test
    @Transactional
    void getDCksByIdFiltering() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        Long id = dCk.getId();

        defaultDCkShouldBeFound("id.equals=" + id);
        defaultDCkShouldNotBeFound("id.notEquals=" + id);

        defaultDCkShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDCkShouldNotBeFound("id.greaterThan=" + id);

        defaultDCkShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDCkShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDCksByDepotIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where depot equals to DEFAULT_DEPOT
        defaultDCkShouldBeFound("depot.equals=" + DEFAULT_DEPOT);

        // Get all the dCkList where depot equals to UPDATED_DEPOT
        defaultDCkShouldNotBeFound("depot.equals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDCksByDepotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where depot not equals to DEFAULT_DEPOT
        defaultDCkShouldNotBeFound("depot.notEquals=" + DEFAULT_DEPOT);

        // Get all the dCkList where depot not equals to UPDATED_DEPOT
        defaultDCkShouldBeFound("depot.notEquals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDCksByDepotIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where depot in DEFAULT_DEPOT or UPDATED_DEPOT
        defaultDCkShouldBeFound("depot.in=" + DEFAULT_DEPOT + "," + UPDATED_DEPOT);

        // Get all the dCkList where depot equals to UPDATED_DEPOT
        defaultDCkShouldNotBeFound("depot.in=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDCksByDepotIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where depot is not null
        defaultDCkShouldBeFound("depot.specified=true");

        // Get all the dCkList where depot is null
        defaultDCkShouldNotBeFound("depot.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByDepotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where depot contains DEFAULT_DEPOT
        defaultDCkShouldBeFound("depot.contains=" + DEFAULT_DEPOT);

        // Get all the dCkList where depot contains UPDATED_DEPOT
        defaultDCkShouldNotBeFound("depot.contains=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDCksByDepotNotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where depot does not contain DEFAULT_DEPOT
        defaultDCkShouldNotBeFound("depot.doesNotContain=" + DEFAULT_DEPOT);

        // Get all the dCkList where depot does not contain UPDATED_DEPOT
        defaultDCkShouldBeFound("depot.doesNotContain=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDCksByCkdateIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where ckdate equals to DEFAULT_CKDATE
        defaultDCkShouldBeFound("ckdate.equals=" + DEFAULT_CKDATE);

        // Get all the dCkList where ckdate equals to UPDATED_CKDATE
        defaultDCkShouldNotBeFound("ckdate.equals=" + UPDATED_CKDATE);
    }

    @Test
    @Transactional
    void getAllDCksByCkdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where ckdate not equals to DEFAULT_CKDATE
        defaultDCkShouldNotBeFound("ckdate.notEquals=" + DEFAULT_CKDATE);

        // Get all the dCkList where ckdate not equals to UPDATED_CKDATE
        defaultDCkShouldBeFound("ckdate.notEquals=" + UPDATED_CKDATE);
    }

    @Test
    @Transactional
    void getAllDCksByCkdateIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where ckdate in DEFAULT_CKDATE or UPDATED_CKDATE
        defaultDCkShouldBeFound("ckdate.in=" + DEFAULT_CKDATE + "," + UPDATED_CKDATE);

        // Get all the dCkList where ckdate equals to UPDATED_CKDATE
        defaultDCkShouldNotBeFound("ckdate.in=" + UPDATED_CKDATE);
    }

    @Test
    @Transactional
    void getAllDCksByCkdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where ckdate is not null
        defaultDCkShouldBeFound("ckdate.specified=true");

        // Get all the dCkList where ckdate is null
        defaultDCkShouldNotBeFound("ckdate.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByCkbillnoIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where ckbillno equals to DEFAULT_CKBILLNO
        defaultDCkShouldBeFound("ckbillno.equals=" + DEFAULT_CKBILLNO);

        // Get all the dCkList where ckbillno equals to UPDATED_CKBILLNO
        defaultDCkShouldNotBeFound("ckbillno.equals=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDCksByCkbillnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where ckbillno not equals to DEFAULT_CKBILLNO
        defaultDCkShouldNotBeFound("ckbillno.notEquals=" + DEFAULT_CKBILLNO);

        // Get all the dCkList where ckbillno not equals to UPDATED_CKBILLNO
        defaultDCkShouldBeFound("ckbillno.notEquals=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDCksByCkbillnoIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where ckbillno in DEFAULT_CKBILLNO or UPDATED_CKBILLNO
        defaultDCkShouldBeFound("ckbillno.in=" + DEFAULT_CKBILLNO + "," + UPDATED_CKBILLNO);

        // Get all the dCkList where ckbillno equals to UPDATED_CKBILLNO
        defaultDCkShouldNotBeFound("ckbillno.in=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDCksByCkbillnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where ckbillno is not null
        defaultDCkShouldBeFound("ckbillno.specified=true");

        // Get all the dCkList where ckbillno is null
        defaultDCkShouldNotBeFound("ckbillno.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByCkbillnoContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where ckbillno contains DEFAULT_CKBILLNO
        defaultDCkShouldBeFound("ckbillno.contains=" + DEFAULT_CKBILLNO);

        // Get all the dCkList where ckbillno contains UPDATED_CKBILLNO
        defaultDCkShouldNotBeFound("ckbillno.contains=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDCksByCkbillnoNotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where ckbillno does not contain DEFAULT_CKBILLNO
        defaultDCkShouldNotBeFound("ckbillno.doesNotContain=" + DEFAULT_CKBILLNO);

        // Get all the dCkList where ckbillno does not contain UPDATED_CKBILLNO
        defaultDCkShouldBeFound("ckbillno.doesNotContain=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDCksByDeptnameIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where deptname equals to DEFAULT_DEPTNAME
        defaultDCkShouldBeFound("deptname.equals=" + DEFAULT_DEPTNAME);

        // Get all the dCkList where deptname equals to UPDATED_DEPTNAME
        defaultDCkShouldNotBeFound("deptname.equals=" + UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void getAllDCksByDeptnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where deptname not equals to DEFAULT_DEPTNAME
        defaultDCkShouldNotBeFound("deptname.notEquals=" + DEFAULT_DEPTNAME);

        // Get all the dCkList where deptname not equals to UPDATED_DEPTNAME
        defaultDCkShouldBeFound("deptname.notEquals=" + UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void getAllDCksByDeptnameIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where deptname in DEFAULT_DEPTNAME or UPDATED_DEPTNAME
        defaultDCkShouldBeFound("deptname.in=" + DEFAULT_DEPTNAME + "," + UPDATED_DEPTNAME);

        // Get all the dCkList where deptname equals to UPDATED_DEPTNAME
        defaultDCkShouldNotBeFound("deptname.in=" + UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void getAllDCksByDeptnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where deptname is not null
        defaultDCkShouldBeFound("deptname.specified=true");

        // Get all the dCkList where deptname is null
        defaultDCkShouldNotBeFound("deptname.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByDeptnameContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where deptname contains DEFAULT_DEPTNAME
        defaultDCkShouldBeFound("deptname.contains=" + DEFAULT_DEPTNAME);

        // Get all the dCkList where deptname contains UPDATED_DEPTNAME
        defaultDCkShouldNotBeFound("deptname.contains=" + UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void getAllDCksByDeptnameNotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where deptname does not contain DEFAULT_DEPTNAME
        defaultDCkShouldNotBeFound("deptname.doesNotContain=" + DEFAULT_DEPTNAME);

        // Get all the dCkList where deptname does not contain UPDATED_DEPTNAME
        defaultDCkShouldBeFound("deptname.doesNotContain=" + UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void getAllDCksByJbrIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where jbr equals to DEFAULT_JBR
        defaultDCkShouldBeFound("jbr.equals=" + DEFAULT_JBR);

        // Get all the dCkList where jbr equals to UPDATED_JBR
        defaultDCkShouldNotBeFound("jbr.equals=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDCksByJbrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where jbr not equals to DEFAULT_JBR
        defaultDCkShouldNotBeFound("jbr.notEquals=" + DEFAULT_JBR);

        // Get all the dCkList where jbr not equals to UPDATED_JBR
        defaultDCkShouldBeFound("jbr.notEquals=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDCksByJbrIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where jbr in DEFAULT_JBR or UPDATED_JBR
        defaultDCkShouldBeFound("jbr.in=" + DEFAULT_JBR + "," + UPDATED_JBR);

        // Get all the dCkList where jbr equals to UPDATED_JBR
        defaultDCkShouldNotBeFound("jbr.in=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDCksByJbrIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where jbr is not null
        defaultDCkShouldBeFound("jbr.specified=true");

        // Get all the dCkList where jbr is null
        defaultDCkShouldNotBeFound("jbr.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByJbrContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where jbr contains DEFAULT_JBR
        defaultDCkShouldBeFound("jbr.contains=" + DEFAULT_JBR);

        // Get all the dCkList where jbr contains UPDATED_JBR
        defaultDCkShouldNotBeFound("jbr.contains=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDCksByJbrNotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where jbr does not contain DEFAULT_JBR
        defaultDCkShouldNotBeFound("jbr.doesNotContain=" + DEFAULT_JBR);

        // Get all the dCkList where jbr does not contain UPDATED_JBR
        defaultDCkShouldBeFound("jbr.doesNotContain=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDCksByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where remark equals to DEFAULT_REMARK
        defaultDCkShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the dCkList where remark equals to UPDATED_REMARK
        defaultDCkShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDCksByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where remark not equals to DEFAULT_REMARK
        defaultDCkShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the dCkList where remark not equals to UPDATED_REMARK
        defaultDCkShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDCksByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultDCkShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the dCkList where remark equals to UPDATED_REMARK
        defaultDCkShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDCksByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where remark is not null
        defaultDCkShouldBeFound("remark.specified=true");

        // Get all the dCkList where remark is null
        defaultDCkShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByRemarkContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where remark contains DEFAULT_REMARK
        defaultDCkShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the dCkList where remark contains UPDATED_REMARK
        defaultDCkShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDCksByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where remark does not contain DEFAULT_REMARK
        defaultDCkShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the dCkList where remark does not contain UPDATED_REMARK
        defaultDCkShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDCksBySpbmIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where spbm equals to DEFAULT_SPBM
        defaultDCkShouldBeFound("spbm.equals=" + DEFAULT_SPBM);

        // Get all the dCkList where spbm equals to UPDATED_SPBM
        defaultDCkShouldNotBeFound("spbm.equals=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDCksBySpbmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where spbm not equals to DEFAULT_SPBM
        defaultDCkShouldNotBeFound("spbm.notEquals=" + DEFAULT_SPBM);

        // Get all the dCkList where spbm not equals to UPDATED_SPBM
        defaultDCkShouldBeFound("spbm.notEquals=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDCksBySpbmIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where spbm in DEFAULT_SPBM or UPDATED_SPBM
        defaultDCkShouldBeFound("spbm.in=" + DEFAULT_SPBM + "," + UPDATED_SPBM);

        // Get all the dCkList where spbm equals to UPDATED_SPBM
        defaultDCkShouldNotBeFound("spbm.in=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDCksBySpbmIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where spbm is not null
        defaultDCkShouldBeFound("spbm.specified=true");

        // Get all the dCkList where spbm is null
        defaultDCkShouldNotBeFound("spbm.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksBySpbmContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where spbm contains DEFAULT_SPBM
        defaultDCkShouldBeFound("spbm.contains=" + DEFAULT_SPBM);

        // Get all the dCkList where spbm contains UPDATED_SPBM
        defaultDCkShouldNotBeFound("spbm.contains=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDCksBySpbmNotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where spbm does not contain DEFAULT_SPBM
        defaultDCkShouldNotBeFound("spbm.doesNotContain=" + DEFAULT_SPBM);

        // Get all the dCkList where spbm does not contain UPDATED_SPBM
        defaultDCkShouldBeFound("spbm.doesNotContain=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDCksBySpmcIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where spmc equals to DEFAULT_SPMC
        defaultDCkShouldBeFound("spmc.equals=" + DEFAULT_SPMC);

        // Get all the dCkList where spmc equals to UPDATED_SPMC
        defaultDCkShouldNotBeFound("spmc.equals=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDCksBySpmcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where spmc not equals to DEFAULT_SPMC
        defaultDCkShouldNotBeFound("spmc.notEquals=" + DEFAULT_SPMC);

        // Get all the dCkList where spmc not equals to UPDATED_SPMC
        defaultDCkShouldBeFound("spmc.notEquals=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDCksBySpmcIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where spmc in DEFAULT_SPMC or UPDATED_SPMC
        defaultDCkShouldBeFound("spmc.in=" + DEFAULT_SPMC + "," + UPDATED_SPMC);

        // Get all the dCkList where spmc equals to UPDATED_SPMC
        defaultDCkShouldNotBeFound("spmc.in=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDCksBySpmcIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where spmc is not null
        defaultDCkShouldBeFound("spmc.specified=true");

        // Get all the dCkList where spmc is null
        defaultDCkShouldNotBeFound("spmc.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksBySpmcContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where spmc contains DEFAULT_SPMC
        defaultDCkShouldBeFound("spmc.contains=" + DEFAULT_SPMC);

        // Get all the dCkList where spmc contains UPDATED_SPMC
        defaultDCkShouldNotBeFound("spmc.contains=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDCksBySpmcNotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where spmc does not contain DEFAULT_SPMC
        defaultDCkShouldNotBeFound("spmc.doesNotContain=" + DEFAULT_SPMC);

        // Get all the dCkList where spmc does not contain UPDATED_SPMC
        defaultDCkShouldBeFound("spmc.doesNotContain=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDCksByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where unit equals to DEFAULT_UNIT
        defaultDCkShouldBeFound("unit.equals=" + DEFAULT_UNIT);

        // Get all the dCkList where unit equals to UPDATED_UNIT
        defaultDCkShouldNotBeFound("unit.equals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDCksByUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where unit not equals to DEFAULT_UNIT
        defaultDCkShouldNotBeFound("unit.notEquals=" + DEFAULT_UNIT);

        // Get all the dCkList where unit not equals to UPDATED_UNIT
        defaultDCkShouldBeFound("unit.notEquals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDCksByUnitIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where unit in DEFAULT_UNIT or UPDATED_UNIT
        defaultDCkShouldBeFound("unit.in=" + DEFAULT_UNIT + "," + UPDATED_UNIT);

        // Get all the dCkList where unit equals to UPDATED_UNIT
        defaultDCkShouldNotBeFound("unit.in=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDCksByUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where unit is not null
        defaultDCkShouldBeFound("unit.specified=true");

        // Get all the dCkList where unit is null
        defaultDCkShouldNotBeFound("unit.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByUnitContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where unit contains DEFAULT_UNIT
        defaultDCkShouldBeFound("unit.contains=" + DEFAULT_UNIT);

        // Get all the dCkList where unit contains UPDATED_UNIT
        defaultDCkShouldNotBeFound("unit.contains=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDCksByUnitNotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where unit does not contain DEFAULT_UNIT
        defaultDCkShouldNotBeFound("unit.doesNotContain=" + DEFAULT_UNIT);

        // Get all the dCkList where unit does not contain UPDATED_UNIT
        defaultDCkShouldBeFound("unit.doesNotContain=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDCksByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where price equals to DEFAULT_PRICE
        defaultDCkShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the dCkList where price equals to UPDATED_PRICE
        defaultDCkShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDCksByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where price not equals to DEFAULT_PRICE
        defaultDCkShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the dCkList where price not equals to UPDATED_PRICE
        defaultDCkShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDCksByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultDCkShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the dCkList where price equals to UPDATED_PRICE
        defaultDCkShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDCksByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where price is not null
        defaultDCkShouldBeFound("price.specified=true");

        // Get all the dCkList where price is null
        defaultDCkShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where price is greater than or equal to DEFAULT_PRICE
        defaultDCkShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the dCkList where price is greater than or equal to UPDATED_PRICE
        defaultDCkShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDCksByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where price is less than or equal to DEFAULT_PRICE
        defaultDCkShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the dCkList where price is less than or equal to SMALLER_PRICE
        defaultDCkShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllDCksByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where price is less than DEFAULT_PRICE
        defaultDCkShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the dCkList where price is less than UPDATED_PRICE
        defaultDCkShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDCksByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where price is greater than DEFAULT_PRICE
        defaultDCkShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the dCkList where price is greater than SMALLER_PRICE
        defaultDCkShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllDCksBySlIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where sl equals to DEFAULT_SL
        defaultDCkShouldBeFound("sl.equals=" + DEFAULT_SL);

        // Get all the dCkList where sl equals to UPDATED_SL
        defaultDCkShouldNotBeFound("sl.equals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDCksBySlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where sl not equals to DEFAULT_SL
        defaultDCkShouldNotBeFound("sl.notEquals=" + DEFAULT_SL);

        // Get all the dCkList where sl not equals to UPDATED_SL
        defaultDCkShouldBeFound("sl.notEquals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDCksBySlIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where sl in DEFAULT_SL or UPDATED_SL
        defaultDCkShouldBeFound("sl.in=" + DEFAULT_SL + "," + UPDATED_SL);

        // Get all the dCkList where sl equals to UPDATED_SL
        defaultDCkShouldNotBeFound("sl.in=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDCksBySlIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where sl is not null
        defaultDCkShouldBeFound("sl.specified=true");

        // Get all the dCkList where sl is null
        defaultDCkShouldNotBeFound("sl.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksBySlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where sl is greater than or equal to DEFAULT_SL
        defaultDCkShouldBeFound("sl.greaterThanOrEqual=" + DEFAULT_SL);

        // Get all the dCkList where sl is greater than or equal to UPDATED_SL
        defaultDCkShouldNotBeFound("sl.greaterThanOrEqual=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDCksBySlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where sl is less than or equal to DEFAULT_SL
        defaultDCkShouldBeFound("sl.lessThanOrEqual=" + DEFAULT_SL);

        // Get all the dCkList where sl is less than or equal to SMALLER_SL
        defaultDCkShouldNotBeFound("sl.lessThanOrEqual=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllDCksBySlIsLessThanSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where sl is less than DEFAULT_SL
        defaultDCkShouldNotBeFound("sl.lessThan=" + DEFAULT_SL);

        // Get all the dCkList where sl is less than UPDATED_SL
        defaultDCkShouldBeFound("sl.lessThan=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDCksBySlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where sl is greater than DEFAULT_SL
        defaultDCkShouldNotBeFound("sl.greaterThan=" + DEFAULT_SL);

        // Get all the dCkList where sl is greater than SMALLER_SL
        defaultDCkShouldBeFound("sl.greaterThan=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllDCksByJeIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where je equals to DEFAULT_JE
        defaultDCkShouldBeFound("je.equals=" + DEFAULT_JE);

        // Get all the dCkList where je equals to UPDATED_JE
        defaultDCkShouldNotBeFound("je.equals=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDCksByJeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where je not equals to DEFAULT_JE
        defaultDCkShouldNotBeFound("je.notEquals=" + DEFAULT_JE);

        // Get all the dCkList where je not equals to UPDATED_JE
        defaultDCkShouldBeFound("je.notEquals=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDCksByJeIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where je in DEFAULT_JE or UPDATED_JE
        defaultDCkShouldBeFound("je.in=" + DEFAULT_JE + "," + UPDATED_JE);

        // Get all the dCkList where je equals to UPDATED_JE
        defaultDCkShouldNotBeFound("je.in=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDCksByJeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where je is not null
        defaultDCkShouldBeFound("je.specified=true");

        // Get all the dCkList where je is null
        defaultDCkShouldNotBeFound("je.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByJeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where je is greater than or equal to DEFAULT_JE
        defaultDCkShouldBeFound("je.greaterThanOrEqual=" + DEFAULT_JE);

        // Get all the dCkList where je is greater than or equal to UPDATED_JE
        defaultDCkShouldNotBeFound("je.greaterThanOrEqual=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDCksByJeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where je is less than or equal to DEFAULT_JE
        defaultDCkShouldBeFound("je.lessThanOrEqual=" + DEFAULT_JE);

        // Get all the dCkList where je is less than or equal to SMALLER_JE
        defaultDCkShouldNotBeFound("je.lessThanOrEqual=" + SMALLER_JE);
    }

    @Test
    @Transactional
    void getAllDCksByJeIsLessThanSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where je is less than DEFAULT_JE
        defaultDCkShouldNotBeFound("je.lessThan=" + DEFAULT_JE);

        // Get all the dCkList where je is less than UPDATED_JE
        defaultDCkShouldBeFound("je.lessThan=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDCksByJeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where je is greater than DEFAULT_JE
        defaultDCkShouldNotBeFound("je.greaterThan=" + DEFAULT_JE);

        // Get all the dCkList where je is greater than SMALLER_JE
        defaultDCkShouldBeFound("je.greaterThan=" + SMALLER_JE);
    }

    @Test
    @Transactional
    void getAllDCksByMemoIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where memo equals to DEFAULT_MEMO
        defaultDCkShouldBeFound("memo.equals=" + DEFAULT_MEMO);

        // Get all the dCkList where memo equals to UPDATED_MEMO
        defaultDCkShouldNotBeFound("memo.equals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDCksByMemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where memo not equals to DEFAULT_MEMO
        defaultDCkShouldNotBeFound("memo.notEquals=" + DEFAULT_MEMO);

        // Get all the dCkList where memo not equals to UPDATED_MEMO
        defaultDCkShouldBeFound("memo.notEquals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDCksByMemoIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where memo in DEFAULT_MEMO or UPDATED_MEMO
        defaultDCkShouldBeFound("memo.in=" + DEFAULT_MEMO + "," + UPDATED_MEMO);

        // Get all the dCkList where memo equals to UPDATED_MEMO
        defaultDCkShouldNotBeFound("memo.in=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDCksByMemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where memo is not null
        defaultDCkShouldBeFound("memo.specified=true");

        // Get all the dCkList where memo is null
        defaultDCkShouldNotBeFound("memo.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByMemoContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where memo contains DEFAULT_MEMO
        defaultDCkShouldBeFound("memo.contains=" + DEFAULT_MEMO);

        // Get all the dCkList where memo contains UPDATED_MEMO
        defaultDCkShouldNotBeFound("memo.contains=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDCksByMemoNotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where memo does not contain DEFAULT_MEMO
        defaultDCkShouldNotBeFound("memo.doesNotContain=" + DEFAULT_MEMO);

        // Get all the dCkList where memo does not contain UPDATED_MEMO
        defaultDCkShouldBeFound("memo.doesNotContain=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDCksByFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where flag equals to DEFAULT_FLAG
        defaultDCkShouldBeFound("flag.equals=" + DEFAULT_FLAG);

        // Get all the dCkList where flag equals to UPDATED_FLAG
        defaultDCkShouldNotBeFound("flag.equals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDCksByFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where flag not equals to DEFAULT_FLAG
        defaultDCkShouldNotBeFound("flag.notEquals=" + DEFAULT_FLAG);

        // Get all the dCkList where flag not equals to UPDATED_FLAG
        defaultDCkShouldBeFound("flag.notEquals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDCksByFlagIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where flag in DEFAULT_FLAG or UPDATED_FLAG
        defaultDCkShouldBeFound("flag.in=" + DEFAULT_FLAG + "," + UPDATED_FLAG);

        // Get all the dCkList where flag equals to UPDATED_FLAG
        defaultDCkShouldNotBeFound("flag.in=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDCksByFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where flag is not null
        defaultDCkShouldBeFound("flag.specified=true");

        // Get all the dCkList where flag is null
        defaultDCkShouldNotBeFound("flag.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByFlagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where flag is greater than or equal to DEFAULT_FLAG
        defaultDCkShouldBeFound("flag.greaterThanOrEqual=" + DEFAULT_FLAG);

        // Get all the dCkList where flag is greater than or equal to UPDATED_FLAG
        defaultDCkShouldNotBeFound("flag.greaterThanOrEqual=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDCksByFlagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where flag is less than or equal to DEFAULT_FLAG
        defaultDCkShouldBeFound("flag.lessThanOrEqual=" + DEFAULT_FLAG);

        // Get all the dCkList where flag is less than or equal to SMALLER_FLAG
        defaultDCkShouldNotBeFound("flag.lessThanOrEqual=" + SMALLER_FLAG);
    }

    @Test
    @Transactional
    void getAllDCksByFlagIsLessThanSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where flag is less than DEFAULT_FLAG
        defaultDCkShouldNotBeFound("flag.lessThan=" + DEFAULT_FLAG);

        // Get all the dCkList where flag is less than UPDATED_FLAG
        defaultDCkShouldBeFound("flag.lessThan=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDCksByFlagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where flag is greater than DEFAULT_FLAG
        defaultDCkShouldNotBeFound("flag.greaterThan=" + DEFAULT_FLAG);

        // Get all the dCkList where flag is greater than SMALLER_FLAG
        defaultDCkShouldBeFound("flag.greaterThan=" + SMALLER_FLAG);
    }

    @Test
    @Transactional
    void getAllDCksByDbSignIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where dbSign equals to DEFAULT_DB_SIGN
        defaultDCkShouldBeFound("dbSign.equals=" + DEFAULT_DB_SIGN);

        // Get all the dCkList where dbSign equals to UPDATED_DB_SIGN
        defaultDCkShouldNotBeFound("dbSign.equals=" + UPDATED_DB_SIGN);
    }

    @Test
    @Transactional
    void getAllDCksByDbSignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where dbSign not equals to DEFAULT_DB_SIGN
        defaultDCkShouldNotBeFound("dbSign.notEquals=" + DEFAULT_DB_SIGN);

        // Get all the dCkList where dbSign not equals to UPDATED_DB_SIGN
        defaultDCkShouldBeFound("dbSign.notEquals=" + UPDATED_DB_SIGN);
    }

    @Test
    @Transactional
    void getAllDCksByDbSignIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where dbSign in DEFAULT_DB_SIGN or UPDATED_DB_SIGN
        defaultDCkShouldBeFound("dbSign.in=" + DEFAULT_DB_SIGN + "," + UPDATED_DB_SIGN);

        // Get all the dCkList where dbSign equals to UPDATED_DB_SIGN
        defaultDCkShouldNotBeFound("dbSign.in=" + UPDATED_DB_SIGN);
    }

    @Test
    @Transactional
    void getAllDCksByDbSignIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where dbSign is not null
        defaultDCkShouldBeFound("dbSign.specified=true");

        // Get all the dCkList where dbSign is null
        defaultDCkShouldNotBeFound("dbSign.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByDbSignIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where dbSign is greater than or equal to DEFAULT_DB_SIGN
        defaultDCkShouldBeFound("dbSign.greaterThanOrEqual=" + DEFAULT_DB_SIGN);

        // Get all the dCkList where dbSign is greater than or equal to UPDATED_DB_SIGN
        defaultDCkShouldNotBeFound("dbSign.greaterThanOrEqual=" + UPDATED_DB_SIGN);
    }

    @Test
    @Transactional
    void getAllDCksByDbSignIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where dbSign is less than or equal to DEFAULT_DB_SIGN
        defaultDCkShouldBeFound("dbSign.lessThanOrEqual=" + DEFAULT_DB_SIGN);

        // Get all the dCkList where dbSign is less than or equal to SMALLER_DB_SIGN
        defaultDCkShouldNotBeFound("dbSign.lessThanOrEqual=" + SMALLER_DB_SIGN);
    }

    @Test
    @Transactional
    void getAllDCksByDbSignIsLessThanSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where dbSign is less than DEFAULT_DB_SIGN
        defaultDCkShouldNotBeFound("dbSign.lessThan=" + DEFAULT_DB_SIGN);

        // Get all the dCkList where dbSign is less than UPDATED_DB_SIGN
        defaultDCkShouldBeFound("dbSign.lessThan=" + UPDATED_DB_SIGN);
    }

    @Test
    @Transactional
    void getAllDCksByDbSignIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where dbSign is greater than DEFAULT_DB_SIGN
        defaultDCkShouldNotBeFound("dbSign.greaterThan=" + DEFAULT_DB_SIGN);

        // Get all the dCkList where dbSign is greater than SMALLER_DB_SIGN
        defaultDCkShouldBeFound("dbSign.greaterThan=" + SMALLER_DB_SIGN);
    }

    @Test
    @Transactional
    void getAllDCksByCktypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where cktype equals to DEFAULT_CKTYPE
        defaultDCkShouldBeFound("cktype.equals=" + DEFAULT_CKTYPE);

        // Get all the dCkList where cktype equals to UPDATED_CKTYPE
        defaultDCkShouldNotBeFound("cktype.equals=" + UPDATED_CKTYPE);
    }

    @Test
    @Transactional
    void getAllDCksByCktypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where cktype not equals to DEFAULT_CKTYPE
        defaultDCkShouldNotBeFound("cktype.notEquals=" + DEFAULT_CKTYPE);

        // Get all the dCkList where cktype not equals to UPDATED_CKTYPE
        defaultDCkShouldBeFound("cktype.notEquals=" + UPDATED_CKTYPE);
    }

    @Test
    @Transactional
    void getAllDCksByCktypeIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where cktype in DEFAULT_CKTYPE or UPDATED_CKTYPE
        defaultDCkShouldBeFound("cktype.in=" + DEFAULT_CKTYPE + "," + UPDATED_CKTYPE);

        // Get all the dCkList where cktype equals to UPDATED_CKTYPE
        defaultDCkShouldNotBeFound("cktype.in=" + UPDATED_CKTYPE);
    }

    @Test
    @Transactional
    void getAllDCksByCktypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where cktype is not null
        defaultDCkShouldBeFound("cktype.specified=true");

        // Get all the dCkList where cktype is null
        defaultDCkShouldNotBeFound("cktype.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByCktypeContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where cktype contains DEFAULT_CKTYPE
        defaultDCkShouldBeFound("cktype.contains=" + DEFAULT_CKTYPE);

        // Get all the dCkList where cktype contains UPDATED_CKTYPE
        defaultDCkShouldNotBeFound("cktype.contains=" + UPDATED_CKTYPE);
    }

    @Test
    @Transactional
    void getAllDCksByCktypeNotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where cktype does not contain DEFAULT_CKTYPE
        defaultDCkShouldNotBeFound("cktype.doesNotContain=" + DEFAULT_CKTYPE);

        // Get all the dCkList where cktype does not contain UPDATED_CKTYPE
        defaultDCkShouldBeFound("cktype.doesNotContain=" + UPDATED_CKTYPE);
    }

    @Test
    @Transactional
    void getAllDCksByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where empn equals to DEFAULT_EMPN
        defaultDCkShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the dCkList where empn equals to UPDATED_EMPN
        defaultDCkShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where empn not equals to DEFAULT_EMPN
        defaultDCkShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the dCkList where empn not equals to UPDATED_EMPN
        defaultDCkShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultDCkShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the dCkList where empn equals to UPDATED_EMPN
        defaultDCkShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where empn is not null
        defaultDCkShouldBeFound("empn.specified=true");

        // Get all the dCkList where empn is null
        defaultDCkShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByEmpnContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where empn contains DEFAULT_EMPN
        defaultDCkShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the dCkList where empn contains UPDATED_EMPN
        defaultDCkShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where empn does not contain DEFAULT_EMPN
        defaultDCkShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the dCkList where empn does not contain UPDATED_EMPN
        defaultDCkShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByLrdateIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where lrdate equals to DEFAULT_LRDATE
        defaultDCkShouldBeFound("lrdate.equals=" + DEFAULT_LRDATE);

        // Get all the dCkList where lrdate equals to UPDATED_LRDATE
        defaultDCkShouldNotBeFound("lrdate.equals=" + UPDATED_LRDATE);
    }

    @Test
    @Transactional
    void getAllDCksByLrdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where lrdate not equals to DEFAULT_LRDATE
        defaultDCkShouldNotBeFound("lrdate.notEquals=" + DEFAULT_LRDATE);

        // Get all the dCkList where lrdate not equals to UPDATED_LRDATE
        defaultDCkShouldBeFound("lrdate.notEquals=" + UPDATED_LRDATE);
    }

    @Test
    @Transactional
    void getAllDCksByLrdateIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where lrdate in DEFAULT_LRDATE or UPDATED_LRDATE
        defaultDCkShouldBeFound("lrdate.in=" + DEFAULT_LRDATE + "," + UPDATED_LRDATE);

        // Get all the dCkList where lrdate equals to UPDATED_LRDATE
        defaultDCkShouldNotBeFound("lrdate.in=" + UPDATED_LRDATE);
    }

    @Test
    @Transactional
    void getAllDCksByLrdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where lrdate is not null
        defaultDCkShouldBeFound("lrdate.specified=true");

        // Get all the dCkList where lrdate is null
        defaultDCkShouldNotBeFound("lrdate.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByKcidIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where kcid equals to DEFAULT_KCID
        defaultDCkShouldBeFound("kcid.equals=" + DEFAULT_KCID);

        // Get all the dCkList where kcid equals to UPDATED_KCID
        defaultDCkShouldNotBeFound("kcid.equals=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDCksByKcidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where kcid not equals to DEFAULT_KCID
        defaultDCkShouldNotBeFound("kcid.notEquals=" + DEFAULT_KCID);

        // Get all the dCkList where kcid not equals to UPDATED_KCID
        defaultDCkShouldBeFound("kcid.notEquals=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDCksByKcidIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where kcid in DEFAULT_KCID or UPDATED_KCID
        defaultDCkShouldBeFound("kcid.in=" + DEFAULT_KCID + "," + UPDATED_KCID);

        // Get all the dCkList where kcid equals to UPDATED_KCID
        defaultDCkShouldNotBeFound("kcid.in=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDCksByKcidIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where kcid is not null
        defaultDCkShouldBeFound("kcid.specified=true");

        // Get all the dCkList where kcid is null
        defaultDCkShouldNotBeFound("kcid.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByKcidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where kcid is greater than or equal to DEFAULT_KCID
        defaultDCkShouldBeFound("kcid.greaterThanOrEqual=" + DEFAULT_KCID);

        // Get all the dCkList where kcid is greater than or equal to UPDATED_KCID
        defaultDCkShouldNotBeFound("kcid.greaterThanOrEqual=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDCksByKcidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where kcid is less than or equal to DEFAULT_KCID
        defaultDCkShouldBeFound("kcid.lessThanOrEqual=" + DEFAULT_KCID);

        // Get all the dCkList where kcid is less than or equal to SMALLER_KCID
        defaultDCkShouldNotBeFound("kcid.lessThanOrEqual=" + SMALLER_KCID);
    }

    @Test
    @Transactional
    void getAllDCksByKcidIsLessThanSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where kcid is less than DEFAULT_KCID
        defaultDCkShouldNotBeFound("kcid.lessThan=" + DEFAULT_KCID);

        // Get all the dCkList where kcid is less than UPDATED_KCID
        defaultDCkShouldBeFound("kcid.lessThan=" + UPDATED_KCID);
    }

    @Test
    @Transactional
    void getAllDCksByKcidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where kcid is greater than DEFAULT_KCID
        defaultDCkShouldNotBeFound("kcid.greaterThan=" + DEFAULT_KCID);

        // Get all the dCkList where kcid is greater than SMALLER_KCID
        defaultDCkShouldBeFound("kcid.greaterThan=" + SMALLER_KCID);
    }

    @Test
    @Transactional
    void getAllDCksByf1IsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1 equals to DEFAULT_F_1
        defaultDCkShouldBeFound("f1.equals=" + DEFAULT_F_1);

        // Get all the dCkList where f1 equals to UPDATED_F_1
        defaultDCkShouldNotBeFound("f1.equals=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDCksByf1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1 not equals to DEFAULT_F_1
        defaultDCkShouldNotBeFound("f1.notEquals=" + DEFAULT_F_1);

        // Get all the dCkList where f1 not equals to UPDATED_F_1
        defaultDCkShouldBeFound("f1.notEquals=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDCksByf1IsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1 in DEFAULT_F_1 or UPDATED_F_1
        defaultDCkShouldBeFound("f1.in=" + DEFAULT_F_1 + "," + UPDATED_F_1);

        // Get all the dCkList where f1 equals to UPDATED_F_1
        defaultDCkShouldNotBeFound("f1.in=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDCksByf1IsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1 is not null
        defaultDCkShouldBeFound("f1.specified=true");

        // Get all the dCkList where f1 is null
        defaultDCkShouldNotBeFound("f1.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByf1ContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1 contains DEFAULT_F_1
        defaultDCkShouldBeFound("f1.contains=" + DEFAULT_F_1);

        // Get all the dCkList where f1 contains UPDATED_F_1
        defaultDCkShouldNotBeFound("f1.contains=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDCksByf1NotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1 does not contain DEFAULT_F_1
        defaultDCkShouldNotBeFound("f1.doesNotContain=" + DEFAULT_F_1);

        // Get all the dCkList where f1 does not contain UPDATED_F_1
        defaultDCkShouldBeFound("f1.doesNotContain=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDCksByf2IsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2 equals to DEFAULT_F_2
        defaultDCkShouldBeFound("f2.equals=" + DEFAULT_F_2);

        // Get all the dCkList where f2 equals to UPDATED_F_2
        defaultDCkShouldNotBeFound("f2.equals=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDCksByf2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2 not equals to DEFAULT_F_2
        defaultDCkShouldNotBeFound("f2.notEquals=" + DEFAULT_F_2);

        // Get all the dCkList where f2 not equals to UPDATED_F_2
        defaultDCkShouldBeFound("f2.notEquals=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDCksByf2IsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2 in DEFAULT_F_2 or UPDATED_F_2
        defaultDCkShouldBeFound("f2.in=" + DEFAULT_F_2 + "," + UPDATED_F_2);

        // Get all the dCkList where f2 equals to UPDATED_F_2
        defaultDCkShouldNotBeFound("f2.in=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDCksByf2IsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2 is not null
        defaultDCkShouldBeFound("f2.specified=true");

        // Get all the dCkList where f2 is null
        defaultDCkShouldNotBeFound("f2.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByf2ContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2 contains DEFAULT_F_2
        defaultDCkShouldBeFound("f2.contains=" + DEFAULT_F_2);

        // Get all the dCkList where f2 contains UPDATED_F_2
        defaultDCkShouldNotBeFound("f2.contains=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDCksByf2NotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2 does not contain DEFAULT_F_2
        defaultDCkShouldNotBeFound("f2.doesNotContain=" + DEFAULT_F_2);

        // Get all the dCkList where f2 does not contain UPDATED_F_2
        defaultDCkShouldBeFound("f2.doesNotContain=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDCksByf1empnIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1empn equals to DEFAULT_F_1_EMPN
        defaultDCkShouldBeFound("f1empn.equals=" + DEFAULT_F_1_EMPN);

        // Get all the dCkList where f1empn equals to UPDATED_F_1_EMPN
        defaultDCkShouldNotBeFound("f1empn.equals=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByf1empnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1empn not equals to DEFAULT_F_1_EMPN
        defaultDCkShouldNotBeFound("f1empn.notEquals=" + DEFAULT_F_1_EMPN);

        // Get all the dCkList where f1empn not equals to UPDATED_F_1_EMPN
        defaultDCkShouldBeFound("f1empn.notEquals=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByf1empnIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1empn in DEFAULT_F_1_EMPN or UPDATED_F_1_EMPN
        defaultDCkShouldBeFound("f1empn.in=" + DEFAULT_F_1_EMPN + "," + UPDATED_F_1_EMPN);

        // Get all the dCkList where f1empn equals to UPDATED_F_1_EMPN
        defaultDCkShouldNotBeFound("f1empn.in=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByf1empnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1empn is not null
        defaultDCkShouldBeFound("f1empn.specified=true");

        // Get all the dCkList where f1empn is null
        defaultDCkShouldNotBeFound("f1empn.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByf1empnContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1empn contains DEFAULT_F_1_EMPN
        defaultDCkShouldBeFound("f1empn.contains=" + DEFAULT_F_1_EMPN);

        // Get all the dCkList where f1empn contains UPDATED_F_1_EMPN
        defaultDCkShouldNotBeFound("f1empn.contains=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByf1empnNotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1empn does not contain DEFAULT_F_1_EMPN
        defaultDCkShouldNotBeFound("f1empn.doesNotContain=" + DEFAULT_F_1_EMPN);

        // Get all the dCkList where f1empn does not contain UPDATED_F_1_EMPN
        defaultDCkShouldBeFound("f1empn.doesNotContain=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByf2empnIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2empn equals to DEFAULT_F_2_EMPN
        defaultDCkShouldBeFound("f2empn.equals=" + DEFAULT_F_2_EMPN);

        // Get all the dCkList where f2empn equals to UPDATED_F_2_EMPN
        defaultDCkShouldNotBeFound("f2empn.equals=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByf2empnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2empn not equals to DEFAULT_F_2_EMPN
        defaultDCkShouldNotBeFound("f2empn.notEquals=" + DEFAULT_F_2_EMPN);

        // Get all the dCkList where f2empn not equals to UPDATED_F_2_EMPN
        defaultDCkShouldBeFound("f2empn.notEquals=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByf2empnIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2empn in DEFAULT_F_2_EMPN or UPDATED_F_2_EMPN
        defaultDCkShouldBeFound("f2empn.in=" + DEFAULT_F_2_EMPN + "," + UPDATED_F_2_EMPN);

        // Get all the dCkList where f2empn equals to UPDATED_F_2_EMPN
        defaultDCkShouldNotBeFound("f2empn.in=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByf2empnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2empn is not null
        defaultDCkShouldBeFound("f2empn.specified=true");

        // Get all the dCkList where f2empn is null
        defaultDCkShouldNotBeFound("f2empn.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByf2empnContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2empn contains DEFAULT_F_2_EMPN
        defaultDCkShouldBeFound("f2empn.contains=" + DEFAULT_F_2_EMPN);

        // Get all the dCkList where f2empn contains UPDATED_F_2_EMPN
        defaultDCkShouldNotBeFound("f2empn.contains=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByf2empnNotContainsSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2empn does not contain DEFAULT_F_2_EMPN
        defaultDCkShouldNotBeFound("f2empn.doesNotContain=" + DEFAULT_F_2_EMPN);

        // Get all the dCkList where f2empn does not contain UPDATED_F_2_EMPN
        defaultDCkShouldBeFound("f2empn.doesNotContain=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDCksByf1sjIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1sj equals to DEFAULT_F_1_SJ
        defaultDCkShouldBeFound("f1sj.equals=" + DEFAULT_F_1_SJ);

        // Get all the dCkList where f1sj equals to UPDATED_F_1_SJ
        defaultDCkShouldNotBeFound("f1sj.equals=" + UPDATED_F_1_SJ);
    }

    @Test
    @Transactional
    void getAllDCksByf1sjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1sj not equals to DEFAULT_F_1_SJ
        defaultDCkShouldNotBeFound("f1sj.notEquals=" + DEFAULT_F_1_SJ);

        // Get all the dCkList where f1sj not equals to UPDATED_F_1_SJ
        defaultDCkShouldBeFound("f1sj.notEquals=" + UPDATED_F_1_SJ);
    }

    @Test
    @Transactional
    void getAllDCksByf1sjIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1sj in DEFAULT_F_1_SJ or UPDATED_F_1_SJ
        defaultDCkShouldBeFound("f1sj.in=" + DEFAULT_F_1_SJ + "," + UPDATED_F_1_SJ);

        // Get all the dCkList where f1sj equals to UPDATED_F_1_SJ
        defaultDCkShouldNotBeFound("f1sj.in=" + UPDATED_F_1_SJ);
    }

    @Test
    @Transactional
    void getAllDCksByf1sjIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f1sj is not null
        defaultDCkShouldBeFound("f1sj.specified=true");

        // Get all the dCkList where f1sj is null
        defaultDCkShouldNotBeFound("f1sj.specified=false");
    }

    @Test
    @Transactional
    void getAllDCksByf2sjIsEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2sj equals to DEFAULT_F_2_SJ
        defaultDCkShouldBeFound("f2sj.equals=" + DEFAULT_F_2_SJ);

        // Get all the dCkList where f2sj equals to UPDATED_F_2_SJ
        defaultDCkShouldNotBeFound("f2sj.equals=" + UPDATED_F_2_SJ);
    }

    @Test
    @Transactional
    void getAllDCksByf2sjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2sj not equals to DEFAULT_F_2_SJ
        defaultDCkShouldNotBeFound("f2sj.notEquals=" + DEFAULT_F_2_SJ);

        // Get all the dCkList where f2sj not equals to UPDATED_F_2_SJ
        defaultDCkShouldBeFound("f2sj.notEquals=" + UPDATED_F_2_SJ);
    }

    @Test
    @Transactional
    void getAllDCksByf2sjIsInShouldWork() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2sj in DEFAULT_F_2_SJ or UPDATED_F_2_SJ
        defaultDCkShouldBeFound("f2sj.in=" + DEFAULT_F_2_SJ + "," + UPDATED_F_2_SJ);

        // Get all the dCkList where f2sj equals to UPDATED_F_2_SJ
        defaultDCkShouldNotBeFound("f2sj.in=" + UPDATED_F_2_SJ);
    }

    @Test
    @Transactional
    void getAllDCksByf2sjIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        // Get all the dCkList where f2sj is not null
        defaultDCkShouldBeFound("f2sj.specified=true");

        // Get all the dCkList where f2sj is null
        defaultDCkShouldNotBeFound("f2sj.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDCkShouldBeFound(String filter) throws Exception {
        restDCkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dCk.getId().intValue())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].ckdate").value(hasItem(DEFAULT_CKDATE.toString())))
            .andExpect(jsonPath("$.[*].ckbillno").value(hasItem(DEFAULT_CKBILLNO)))
            .andExpect(jsonPath("$.[*].deptname").value(hasItem(DEFAULT_DEPTNAME)))
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
            .andExpect(jsonPath("$.[*].dbSign").value(hasItem(DEFAULT_DB_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].cktype").value(hasItem(DEFAULT_CKTYPE)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].lrdate").value(hasItem(DEFAULT_LRDATE.toString())))
            .andExpect(jsonPath("$.[*].kcid").value(hasItem(DEFAULT_KCID.doubleValue())))
            .andExpect(jsonPath("$.[*].f1").value(hasItem(DEFAULT_F_1)))
            .andExpect(jsonPath("$.[*].f2").value(hasItem(DEFAULT_F_2)))
            .andExpect(jsonPath("$.[*].f1empn").value(hasItem(DEFAULT_F_1_EMPN)))
            .andExpect(jsonPath("$.[*].f2empn").value(hasItem(DEFAULT_F_2_EMPN)))
            .andExpect(jsonPath("$.[*].f1sj").value(hasItem(DEFAULT_F_1_SJ.toString())))
            .andExpect(jsonPath("$.[*].f2sj").value(hasItem(DEFAULT_F_2_SJ.toString())));

        // Check, that the count call also returns 1
        restDCkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDCkShouldNotBeFound(String filter) throws Exception {
        restDCkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDCkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDCk() throws Exception {
        // Get the dCk
        restDCkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDCk() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        int databaseSizeBeforeUpdate = dCkRepository.findAll().size();

        // Update the dCk
        DCk updatedDCk = dCkRepository.findById(dCk.getId()).get();
        // Disconnect from session so that the updates on updatedDCk are not directly saved in db
        em.detach(updatedDCk);
        updatedDCk
            .depot(UPDATED_DEPOT)
            .ckdate(UPDATED_CKDATE)
            .ckbillno(UPDATED_CKBILLNO)
            .deptname(UPDATED_DEPTNAME)
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
            .dbSign(UPDATED_DB_SIGN)
            .cktype(UPDATED_CKTYPE)
            .empn(UPDATED_EMPN)
            .lrdate(UPDATED_LRDATE)
            .kcid(UPDATED_KCID)
            .f1(UPDATED_F_1)
            .f2(UPDATED_F_2)
            .f1empn(UPDATED_F_1_EMPN)
            .f2empn(UPDATED_F_2_EMPN)
            .f1sj(UPDATED_F_1_SJ)
            .f2sj(UPDATED_F_2_SJ);
        DCkDTO dCkDTO = dCkMapper.toDto(updatedDCk);

        restDCkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dCkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dCkDTO))
            )
            .andExpect(status().isOk());

        // Validate the DCk in the database
        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeUpdate);
        DCk testDCk = dCkList.get(dCkList.size() - 1);
        assertThat(testDCk.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testDCk.getCkdate()).isEqualTo(UPDATED_CKDATE);
        assertThat(testDCk.getCkbillno()).isEqualTo(UPDATED_CKBILLNO);
        assertThat(testDCk.getDeptname()).isEqualTo(UPDATED_DEPTNAME);
        assertThat(testDCk.getJbr()).isEqualTo(UPDATED_JBR);
        assertThat(testDCk.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testDCk.getSpbm()).isEqualTo(UPDATED_SPBM);
        assertThat(testDCk.getSpmc()).isEqualTo(UPDATED_SPMC);
        assertThat(testDCk.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testDCk.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDCk.getSl()).isEqualTo(UPDATED_SL);
        assertThat(testDCk.getJe()).isEqualTo(UPDATED_JE);
        assertThat(testDCk.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testDCk.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testDCk.getDbSign()).isEqualTo(UPDATED_DB_SIGN);
        assertThat(testDCk.getCktype()).isEqualTo(UPDATED_CKTYPE);
        assertThat(testDCk.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testDCk.getLrdate()).isEqualTo(UPDATED_LRDATE);
        assertThat(testDCk.getKcid()).isEqualTo(UPDATED_KCID);
        assertThat(testDCk.getf1()).isEqualTo(UPDATED_F_1);
        assertThat(testDCk.getf2()).isEqualTo(UPDATED_F_2);
        assertThat(testDCk.getf1empn()).isEqualTo(UPDATED_F_1_EMPN);
        assertThat(testDCk.getf2empn()).isEqualTo(UPDATED_F_2_EMPN);
        assertThat(testDCk.getf1sj()).isEqualTo(UPDATED_F_1_SJ);
        assertThat(testDCk.getf2sj()).isEqualTo(UPDATED_F_2_SJ);

        // Validate the DCk in Elasticsearch
        verify(mockDCkSearchRepository).save(testDCk);
    }

    @Test
    @Transactional
    void putNonExistingDCk() throws Exception {
        int databaseSizeBeforeUpdate = dCkRepository.findAll().size();
        dCk.setId(count.incrementAndGet());

        // Create the DCk
        DCkDTO dCkDTO = dCkMapper.toDto(dCk);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDCkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dCkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dCkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCk in the database
        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCk in Elasticsearch
        verify(mockDCkSearchRepository, times(0)).save(dCk);
    }

    @Test
    @Transactional
    void putWithIdMismatchDCk() throws Exception {
        int databaseSizeBeforeUpdate = dCkRepository.findAll().size();
        dCk.setId(count.incrementAndGet());

        // Create the DCk
        DCkDTO dCkDTO = dCkMapper.toDto(dCk);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dCkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCk in the database
        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCk in Elasticsearch
        verify(mockDCkSearchRepository, times(0)).save(dCk);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDCk() throws Exception {
        int databaseSizeBeforeUpdate = dCkRepository.findAll().size();
        dCk.setId(count.incrementAndGet());

        // Create the DCk
        DCkDTO dCkDTO = dCkMapper.toDto(dCk);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DCk in the database
        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCk in Elasticsearch
        verify(mockDCkSearchRepository, times(0)).save(dCk);
    }

    @Test
    @Transactional
    void partialUpdateDCkWithPatch() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        int databaseSizeBeforeUpdate = dCkRepository.findAll().size();

        // Update the dCk using partial update
        DCk partialUpdatedDCk = new DCk();
        partialUpdatedDCk.setId(dCk.getId());

        partialUpdatedDCk
            .ckdate(UPDATED_CKDATE)
            .deptname(UPDATED_DEPTNAME)
            .jbr(UPDATED_JBR)
            .remark(UPDATED_REMARK)
            .spbm(UPDATED_SPBM)
            .memo(UPDATED_MEMO)
            .flag(UPDATED_FLAG)
            .cktype(UPDATED_CKTYPE)
            .empn(UPDATED_EMPN)
            .kcid(UPDATED_KCID)
            .f1(UPDATED_F_1)
            .f1empn(UPDATED_F_1_EMPN)
            .f2empn(UPDATED_F_2_EMPN)
            .f1sj(UPDATED_F_1_SJ)
            .f2sj(UPDATED_F_2_SJ);

        restDCkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDCk.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDCk))
            )
            .andExpect(status().isOk());

        // Validate the DCk in the database
        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeUpdate);
        DCk testDCk = dCkList.get(dCkList.size() - 1);
        assertThat(testDCk.getDepot()).isEqualTo(DEFAULT_DEPOT);
        assertThat(testDCk.getCkdate()).isEqualTo(UPDATED_CKDATE);
        assertThat(testDCk.getCkbillno()).isEqualTo(DEFAULT_CKBILLNO);
        assertThat(testDCk.getDeptname()).isEqualTo(UPDATED_DEPTNAME);
        assertThat(testDCk.getJbr()).isEqualTo(UPDATED_JBR);
        assertThat(testDCk.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testDCk.getSpbm()).isEqualTo(UPDATED_SPBM);
        assertThat(testDCk.getSpmc()).isEqualTo(DEFAULT_SPMC);
        assertThat(testDCk.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testDCk.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testDCk.getSl()).isEqualByComparingTo(DEFAULT_SL);
        assertThat(testDCk.getJe()).isEqualByComparingTo(DEFAULT_JE);
        assertThat(testDCk.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testDCk.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testDCk.getDbSign()).isEqualTo(DEFAULT_DB_SIGN);
        assertThat(testDCk.getCktype()).isEqualTo(UPDATED_CKTYPE);
        assertThat(testDCk.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testDCk.getLrdate()).isEqualTo(DEFAULT_LRDATE);
        assertThat(testDCk.getKcid()).isEqualTo(UPDATED_KCID);
        assertThat(testDCk.getf1()).isEqualTo(UPDATED_F_1);
        assertThat(testDCk.getf2()).isEqualTo(DEFAULT_F_2);
        assertThat(testDCk.getf1empn()).isEqualTo(UPDATED_F_1_EMPN);
        assertThat(testDCk.getf2empn()).isEqualTo(UPDATED_F_2_EMPN);
        assertThat(testDCk.getf1sj()).isEqualTo(UPDATED_F_1_SJ);
        assertThat(testDCk.getf2sj()).isEqualTo(UPDATED_F_2_SJ);
    }

    @Test
    @Transactional
    void fullUpdateDCkWithPatch() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        int databaseSizeBeforeUpdate = dCkRepository.findAll().size();

        // Update the dCk using partial update
        DCk partialUpdatedDCk = new DCk();
        partialUpdatedDCk.setId(dCk.getId());

        partialUpdatedDCk
            .depot(UPDATED_DEPOT)
            .ckdate(UPDATED_CKDATE)
            .ckbillno(UPDATED_CKBILLNO)
            .deptname(UPDATED_DEPTNAME)
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
            .dbSign(UPDATED_DB_SIGN)
            .cktype(UPDATED_CKTYPE)
            .empn(UPDATED_EMPN)
            .lrdate(UPDATED_LRDATE)
            .kcid(UPDATED_KCID)
            .f1(UPDATED_F_1)
            .f2(UPDATED_F_2)
            .f1empn(UPDATED_F_1_EMPN)
            .f2empn(UPDATED_F_2_EMPN)
            .f1sj(UPDATED_F_1_SJ)
            .f2sj(UPDATED_F_2_SJ);

        restDCkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDCk.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDCk))
            )
            .andExpect(status().isOk());

        // Validate the DCk in the database
        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeUpdate);
        DCk testDCk = dCkList.get(dCkList.size() - 1);
        assertThat(testDCk.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testDCk.getCkdate()).isEqualTo(UPDATED_CKDATE);
        assertThat(testDCk.getCkbillno()).isEqualTo(UPDATED_CKBILLNO);
        assertThat(testDCk.getDeptname()).isEqualTo(UPDATED_DEPTNAME);
        assertThat(testDCk.getJbr()).isEqualTo(UPDATED_JBR);
        assertThat(testDCk.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testDCk.getSpbm()).isEqualTo(UPDATED_SPBM);
        assertThat(testDCk.getSpmc()).isEqualTo(UPDATED_SPMC);
        assertThat(testDCk.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testDCk.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testDCk.getSl()).isEqualByComparingTo(UPDATED_SL);
        assertThat(testDCk.getJe()).isEqualByComparingTo(UPDATED_JE);
        assertThat(testDCk.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testDCk.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testDCk.getDbSign()).isEqualTo(UPDATED_DB_SIGN);
        assertThat(testDCk.getCktype()).isEqualTo(UPDATED_CKTYPE);
        assertThat(testDCk.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testDCk.getLrdate()).isEqualTo(UPDATED_LRDATE);
        assertThat(testDCk.getKcid()).isEqualTo(UPDATED_KCID);
        assertThat(testDCk.getf1()).isEqualTo(UPDATED_F_1);
        assertThat(testDCk.getf2()).isEqualTo(UPDATED_F_2);
        assertThat(testDCk.getf1empn()).isEqualTo(UPDATED_F_1_EMPN);
        assertThat(testDCk.getf2empn()).isEqualTo(UPDATED_F_2_EMPN);
        assertThat(testDCk.getf1sj()).isEqualTo(UPDATED_F_1_SJ);
        assertThat(testDCk.getf2sj()).isEqualTo(UPDATED_F_2_SJ);
    }

    @Test
    @Transactional
    void patchNonExistingDCk() throws Exception {
        int databaseSizeBeforeUpdate = dCkRepository.findAll().size();
        dCk.setId(count.incrementAndGet());

        // Create the DCk
        DCkDTO dCkDTO = dCkMapper.toDto(dCk);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDCkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dCkDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dCkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCk in the database
        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCk in Elasticsearch
        verify(mockDCkSearchRepository, times(0)).save(dCk);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDCk() throws Exception {
        int databaseSizeBeforeUpdate = dCkRepository.findAll().size();
        dCk.setId(count.incrementAndGet());

        // Create the DCk
        DCkDTO dCkDTO = dCkMapper.toDto(dCk);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dCkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCk in the database
        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCk in Elasticsearch
        verify(mockDCkSearchRepository, times(0)).save(dCk);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDCk() throws Exception {
        int databaseSizeBeforeUpdate = dCkRepository.findAll().size();
        dCk.setId(count.incrementAndGet());

        // Create the DCk
        DCkDTO dCkDTO = dCkMapper.toDto(dCk);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dCkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DCk in the database
        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCk in Elasticsearch
        verify(mockDCkSearchRepository, times(0)).save(dCk);
    }

    @Test
    @Transactional
    void deleteDCk() throws Exception {
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);

        int databaseSizeBeforeDelete = dCkRepository.findAll().size();

        // Delete the dCk
        restDCkMockMvc.perform(delete(ENTITY_API_URL_ID, dCk.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DCk> dCkList = dCkRepository.findAll();
        assertThat(dCkList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DCk in Elasticsearch
        verify(mockDCkSearchRepository, times(1)).deleteById(dCk.getId());
    }

    @Test
    @Transactional
    void searchDCk() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dCkRepository.saveAndFlush(dCk);
        when(mockDCkSearchRepository.search(queryStringQuery("id:" + dCk.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dCk), PageRequest.of(0, 1), 1));

        // Search the dCk
        restDCkMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dCk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dCk.getId().intValue())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].ckdate").value(hasItem(DEFAULT_CKDATE.toString())))
            .andExpect(jsonPath("$.[*].ckbillno").value(hasItem(DEFAULT_CKBILLNO)))
            .andExpect(jsonPath("$.[*].deptname").value(hasItem(DEFAULT_DEPTNAME)))
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
            .andExpect(jsonPath("$.[*].dbSign").value(hasItem(DEFAULT_DB_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].cktype").value(hasItem(DEFAULT_CKTYPE)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].lrdate").value(hasItem(DEFAULT_LRDATE.toString())))
            .andExpect(jsonPath("$.[*].kcid").value(hasItem(DEFAULT_KCID.doubleValue())))
            .andExpect(jsonPath("$.[*].f1").value(hasItem(DEFAULT_F_1)))
            .andExpect(jsonPath("$.[*].f2").value(hasItem(DEFAULT_F_2)))
            .andExpect(jsonPath("$.[*].f1empn").value(hasItem(DEFAULT_F_1_EMPN)))
            .andExpect(jsonPath("$.[*].f2empn").value(hasItem(DEFAULT_F_2_EMPN)))
            .andExpect(jsonPath("$.[*].f1sj").value(hasItem(DEFAULT_F_1_SJ.toString())))
            .andExpect(jsonPath("$.[*].f2sj").value(hasItem(DEFAULT_F_2_SJ.toString())));
    }
}
