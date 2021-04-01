package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DRk;
import ihotel.app.repository.DRkRepository;
import ihotel.app.repository.search.DRkSearchRepository;
import ihotel.app.service.criteria.DRkCriteria;
import ihotel.app.service.dto.DRkDTO;
import ihotel.app.service.mapper.DRkMapper;
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
 * Integration tests for the {@link DRkResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DRkResourceIT {

    private static final Instant DEFAULT_RKDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RKDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DEPOT = "AAAAAAAAAA";
    private static final String UPDATED_DEPOT = "BBBBBBBBBB";

    private static final String DEFAULT_RKLX = "AAAAAAAAAA";
    private static final String UPDATED_RKLX = "BBBBBBBBBB";

    private static final String DEFAULT_RKBILLNO = "AAAAAAAAAA";
    private static final String UPDATED_RKBILLNO = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY = 1L;
    private static final Long UPDATED_COMPANY = 2L;
    private static final Long SMALLER_COMPANY = 1L - 1L;

    private static final String DEFAULT_DEPTNAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_JBR = "AAAAAAAAAA";
    private static final String UPDATED_JBR = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final Instant DEFAULT_LRDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LRDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SPBM = "AAAAAAAAAA";
    private static final String UPDATED_SPBM = "BBBBBBBBBB";

    private static final String DEFAULT_SPMC = "AAAAAAAAAA";
    private static final String UPDATED_SPMC = "BBBBBBBBBB";

    private static final String DEFAULT_GGXH = "AAAAAAAAAA";
    private static final String UPDATED_GGXH = "BBBBBBBBBB";

    private static final String DEFAULT_DW = "AAAAAAAAAA";
    private static final String UPDATED_DW = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SL = new BigDecimal(2);
    private static final BigDecimal SMALLER_SL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_JE = new BigDecimal(1);
    private static final BigDecimal UPDATED_JE = new BigDecimal(2);
    private static final BigDecimal SMALLER_JE = new BigDecimal(1 - 1);

    private static final Long DEFAULT_SIGN = 1L;
    private static final Long UPDATED_SIGN = 2L;
    private static final Long SMALLER_SIGN = 1L - 1L;

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final Long DEFAULT_FLAG = 1L;
    private static final Long UPDATED_FLAG = 2L;
    private static final Long SMALLER_FLAG = 1L - 1L;

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

    private static final String ENTITY_API_URL = "/api/d-rks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-rks";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DRkRepository dRkRepository;

    @Autowired
    private DRkMapper dRkMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DRkSearchRepositoryMockConfiguration
     */
    @Autowired
    private DRkSearchRepository mockDRkSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDRkMockMvc;

    private DRk dRk;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DRk createEntity(EntityManager em) {
        DRk dRk = new DRk()
            .rkdate(DEFAULT_RKDATE)
            .depot(DEFAULT_DEPOT)
            .rklx(DEFAULT_RKLX)
            .rkbillno(DEFAULT_RKBILLNO)
            .company(DEFAULT_COMPANY)
            .deptname(DEFAULT_DEPTNAME)
            .jbr(DEFAULT_JBR)
            .remark(DEFAULT_REMARK)
            .empn(DEFAULT_EMPN)
            .lrdate(DEFAULT_LRDATE)
            .spbm(DEFAULT_SPBM)
            .spmc(DEFAULT_SPMC)
            .ggxh(DEFAULT_GGXH)
            .dw(DEFAULT_DW)
            .price(DEFAULT_PRICE)
            .sl(DEFAULT_SL)
            .je(DEFAULT_JE)
            .sign(DEFAULT_SIGN)
            .memo(DEFAULT_MEMO)
            .flag(DEFAULT_FLAG)
            .f1(DEFAULT_F_1)
            .f2(DEFAULT_F_2)
            .f1empn(DEFAULT_F_1_EMPN)
            .f2empn(DEFAULT_F_2_EMPN)
            .f1sj(DEFAULT_F_1_SJ)
            .f2sj(DEFAULT_F_2_SJ);
        return dRk;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DRk createUpdatedEntity(EntityManager em) {
        DRk dRk = new DRk()
            .rkdate(UPDATED_RKDATE)
            .depot(UPDATED_DEPOT)
            .rklx(UPDATED_RKLX)
            .rkbillno(UPDATED_RKBILLNO)
            .company(UPDATED_COMPANY)
            .deptname(UPDATED_DEPTNAME)
            .jbr(UPDATED_JBR)
            .remark(UPDATED_REMARK)
            .empn(UPDATED_EMPN)
            .lrdate(UPDATED_LRDATE)
            .spbm(UPDATED_SPBM)
            .spmc(UPDATED_SPMC)
            .ggxh(UPDATED_GGXH)
            .dw(UPDATED_DW)
            .price(UPDATED_PRICE)
            .sl(UPDATED_SL)
            .je(UPDATED_JE)
            .sign(UPDATED_SIGN)
            .memo(UPDATED_MEMO)
            .flag(UPDATED_FLAG)
            .f1(UPDATED_F_1)
            .f2(UPDATED_F_2)
            .f1empn(UPDATED_F_1_EMPN)
            .f2empn(UPDATED_F_2_EMPN)
            .f1sj(UPDATED_F_1_SJ)
            .f2sj(UPDATED_F_2_SJ);
        return dRk;
    }

    @BeforeEach
    public void initTest() {
        dRk = createEntity(em);
    }

    @Test
    @Transactional
    void createDRk() throws Exception {
        int databaseSizeBeforeCreate = dRkRepository.findAll().size();
        // Create the DRk
        DRkDTO dRkDTO = dRkMapper.toDto(dRk);
        restDRkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dRkDTO)))
            .andExpect(status().isCreated());

        // Validate the DRk in the database
        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeCreate + 1);
        DRk testDRk = dRkList.get(dRkList.size() - 1);
        assertThat(testDRk.getRkdate()).isEqualTo(DEFAULT_RKDATE);
        assertThat(testDRk.getDepot()).isEqualTo(DEFAULT_DEPOT);
        assertThat(testDRk.getRklx()).isEqualTo(DEFAULT_RKLX);
        assertThat(testDRk.getRkbillno()).isEqualTo(DEFAULT_RKBILLNO);
        assertThat(testDRk.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testDRk.getDeptname()).isEqualTo(DEFAULT_DEPTNAME);
        assertThat(testDRk.getJbr()).isEqualTo(DEFAULT_JBR);
        assertThat(testDRk.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testDRk.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testDRk.getLrdate()).isEqualTo(DEFAULT_LRDATE);
        assertThat(testDRk.getSpbm()).isEqualTo(DEFAULT_SPBM);
        assertThat(testDRk.getSpmc()).isEqualTo(DEFAULT_SPMC);
        assertThat(testDRk.getGgxh()).isEqualTo(DEFAULT_GGXH);
        assertThat(testDRk.getDw()).isEqualTo(DEFAULT_DW);
        assertThat(testDRk.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testDRk.getSl()).isEqualByComparingTo(DEFAULT_SL);
        assertThat(testDRk.getJe()).isEqualByComparingTo(DEFAULT_JE);
        assertThat(testDRk.getSign()).isEqualTo(DEFAULT_SIGN);
        assertThat(testDRk.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testDRk.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testDRk.getf1()).isEqualTo(DEFAULT_F_1);
        assertThat(testDRk.getf2()).isEqualTo(DEFAULT_F_2);
        assertThat(testDRk.getf1empn()).isEqualTo(DEFAULT_F_1_EMPN);
        assertThat(testDRk.getf2empn()).isEqualTo(DEFAULT_F_2_EMPN);
        assertThat(testDRk.getf1sj()).isEqualTo(DEFAULT_F_1_SJ);
        assertThat(testDRk.getf2sj()).isEqualTo(DEFAULT_F_2_SJ);

        // Validate the DRk in Elasticsearch
        verify(mockDRkSearchRepository, times(1)).save(testDRk);
    }

    @Test
    @Transactional
    void createDRkWithExistingId() throws Exception {
        // Create the DRk with an existing ID
        dRk.setId(1L);
        DRkDTO dRkDTO = dRkMapper.toDto(dRk);

        int databaseSizeBeforeCreate = dRkRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDRkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dRkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DRk in the database
        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeCreate);

        // Validate the DRk in Elasticsearch
        verify(mockDRkSearchRepository, times(0)).save(dRk);
    }

    @Test
    @Transactional
    void checkRkdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dRkRepository.findAll().size();
        // set the field null
        dRk.setRkdate(null);

        // Create the DRk, which fails.
        DRkDTO dRkDTO = dRkMapper.toDto(dRk);

        restDRkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dRkDTO)))
            .andExpect(status().isBadRequest());

        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepotIsRequired() throws Exception {
        int databaseSizeBeforeTest = dRkRepository.findAll().size();
        // set the field null
        dRk.setDepot(null);

        // Create the DRk, which fails.
        DRkDTO dRkDTO = dRkMapper.toDto(dRk);

        restDRkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dRkDTO)))
            .andExpect(status().isBadRequest());

        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRkbillnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = dRkRepository.findAll().size();
        // set the field null
        dRk.setRkbillno(null);

        // Create the DRk, which fails.
        DRkDTO dRkDTO = dRkMapper.toDto(dRk);

        restDRkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dRkDTO)))
            .andExpect(status().isBadRequest());

        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpbmIsRequired() throws Exception {
        int databaseSizeBeforeTest = dRkRepository.findAll().size();
        // set the field null
        dRk.setSpbm(null);

        // Create the DRk, which fails.
        DRkDTO dRkDTO = dRkMapper.toDto(dRk);

        restDRkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dRkDTO)))
            .andExpect(status().isBadRequest());

        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpmcIsRequired() throws Exception {
        int databaseSizeBeforeTest = dRkRepository.findAll().size();
        // set the field null
        dRk.setSpmc(null);

        // Create the DRk, which fails.
        DRkDTO dRkDTO = dRkMapper.toDto(dRk);

        restDRkMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dRkDTO)))
            .andExpect(status().isBadRequest());

        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDRks() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList
        restDRkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dRk.getId().intValue())))
            .andExpect(jsonPath("$.[*].rkdate").value(hasItem(DEFAULT_RKDATE.toString())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].rklx").value(hasItem(DEFAULT_RKLX)))
            .andExpect(jsonPath("$.[*].rkbillno").value(hasItem(DEFAULT_RKBILLNO)))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.intValue())))
            .andExpect(jsonPath("$.[*].deptname").value(hasItem(DEFAULT_DEPTNAME)))
            .andExpect(jsonPath("$.[*].jbr").value(hasItem(DEFAULT_JBR)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].lrdate").value(hasItem(DEFAULT_LRDATE.toString())))
            .andExpect(jsonPath("$.[*].spbm").value(hasItem(DEFAULT_SPBM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].ggxh").value(hasItem(DEFAULT_GGXH)))
            .andExpect(jsonPath("$.[*].dw").value(hasItem(DEFAULT_DW)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].je").value(hasItem(sameNumber(DEFAULT_JE))))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].f1").value(hasItem(DEFAULT_F_1)))
            .andExpect(jsonPath("$.[*].f2").value(hasItem(DEFAULT_F_2)))
            .andExpect(jsonPath("$.[*].f1empn").value(hasItem(DEFAULT_F_1_EMPN)))
            .andExpect(jsonPath("$.[*].f2empn").value(hasItem(DEFAULT_F_2_EMPN)))
            .andExpect(jsonPath("$.[*].f1sj").value(hasItem(DEFAULT_F_1_SJ.toString())))
            .andExpect(jsonPath("$.[*].f2sj").value(hasItem(DEFAULT_F_2_SJ.toString())));
    }

    @Test
    @Transactional
    void getDRk() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get the dRk
        restDRkMockMvc
            .perform(get(ENTITY_API_URL_ID, dRk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dRk.getId().intValue()))
            .andExpect(jsonPath("$.rkdate").value(DEFAULT_RKDATE.toString()))
            .andExpect(jsonPath("$.depot").value(DEFAULT_DEPOT))
            .andExpect(jsonPath("$.rklx").value(DEFAULT_RKLX))
            .andExpect(jsonPath("$.rkbillno").value(DEFAULT_RKBILLNO))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY.intValue()))
            .andExpect(jsonPath("$.deptname").value(DEFAULT_DEPTNAME))
            .andExpect(jsonPath("$.jbr").value(DEFAULT_JBR))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.lrdate").value(DEFAULT_LRDATE.toString()))
            .andExpect(jsonPath("$.spbm").value(DEFAULT_SPBM))
            .andExpect(jsonPath("$.spmc").value(DEFAULT_SPMC))
            .andExpect(jsonPath("$.ggxh").value(DEFAULT_GGXH))
            .andExpect(jsonPath("$.dw").value(DEFAULT_DW))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.sl").value(sameNumber(DEFAULT_SL)))
            .andExpect(jsonPath("$.je").value(sameNumber(DEFAULT_JE)))
            .andExpect(jsonPath("$.sign").value(DEFAULT_SIGN.intValue()))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG.intValue()))
            .andExpect(jsonPath("$.f1").value(DEFAULT_F_1))
            .andExpect(jsonPath("$.f2").value(DEFAULT_F_2))
            .andExpect(jsonPath("$.f1empn").value(DEFAULT_F_1_EMPN))
            .andExpect(jsonPath("$.f2empn").value(DEFAULT_F_2_EMPN))
            .andExpect(jsonPath("$.f1sj").value(DEFAULT_F_1_SJ.toString()))
            .andExpect(jsonPath("$.f2sj").value(DEFAULT_F_2_SJ.toString()));
    }

    @Test
    @Transactional
    void getDRksByIdFiltering() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        Long id = dRk.getId();

        defaultDRkShouldBeFound("id.equals=" + id);
        defaultDRkShouldNotBeFound("id.notEquals=" + id);

        defaultDRkShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDRkShouldNotBeFound("id.greaterThan=" + id);

        defaultDRkShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDRkShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDRksByRkdateIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rkdate equals to DEFAULT_RKDATE
        defaultDRkShouldBeFound("rkdate.equals=" + DEFAULT_RKDATE);

        // Get all the dRkList where rkdate equals to UPDATED_RKDATE
        defaultDRkShouldNotBeFound("rkdate.equals=" + UPDATED_RKDATE);
    }

    @Test
    @Transactional
    void getAllDRksByRkdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rkdate not equals to DEFAULT_RKDATE
        defaultDRkShouldNotBeFound("rkdate.notEquals=" + DEFAULT_RKDATE);

        // Get all the dRkList where rkdate not equals to UPDATED_RKDATE
        defaultDRkShouldBeFound("rkdate.notEquals=" + UPDATED_RKDATE);
    }

    @Test
    @Transactional
    void getAllDRksByRkdateIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rkdate in DEFAULT_RKDATE or UPDATED_RKDATE
        defaultDRkShouldBeFound("rkdate.in=" + DEFAULT_RKDATE + "," + UPDATED_RKDATE);

        // Get all the dRkList where rkdate equals to UPDATED_RKDATE
        defaultDRkShouldNotBeFound("rkdate.in=" + UPDATED_RKDATE);
    }

    @Test
    @Transactional
    void getAllDRksByRkdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rkdate is not null
        defaultDRkShouldBeFound("rkdate.specified=true");

        // Get all the dRkList where rkdate is null
        defaultDRkShouldNotBeFound("rkdate.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByDepotIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where depot equals to DEFAULT_DEPOT
        defaultDRkShouldBeFound("depot.equals=" + DEFAULT_DEPOT);

        // Get all the dRkList where depot equals to UPDATED_DEPOT
        defaultDRkShouldNotBeFound("depot.equals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDRksByDepotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where depot not equals to DEFAULT_DEPOT
        defaultDRkShouldNotBeFound("depot.notEquals=" + DEFAULT_DEPOT);

        // Get all the dRkList where depot not equals to UPDATED_DEPOT
        defaultDRkShouldBeFound("depot.notEquals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDRksByDepotIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where depot in DEFAULT_DEPOT or UPDATED_DEPOT
        defaultDRkShouldBeFound("depot.in=" + DEFAULT_DEPOT + "," + UPDATED_DEPOT);

        // Get all the dRkList where depot equals to UPDATED_DEPOT
        defaultDRkShouldNotBeFound("depot.in=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDRksByDepotIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where depot is not null
        defaultDRkShouldBeFound("depot.specified=true");

        // Get all the dRkList where depot is null
        defaultDRkShouldNotBeFound("depot.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByDepotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where depot contains DEFAULT_DEPOT
        defaultDRkShouldBeFound("depot.contains=" + DEFAULT_DEPOT);

        // Get all the dRkList where depot contains UPDATED_DEPOT
        defaultDRkShouldNotBeFound("depot.contains=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDRksByDepotNotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where depot does not contain DEFAULT_DEPOT
        defaultDRkShouldNotBeFound("depot.doesNotContain=" + DEFAULT_DEPOT);

        // Get all the dRkList where depot does not contain UPDATED_DEPOT
        defaultDRkShouldBeFound("depot.doesNotContain=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDRksByRklxIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rklx equals to DEFAULT_RKLX
        defaultDRkShouldBeFound("rklx.equals=" + DEFAULT_RKLX);

        // Get all the dRkList where rklx equals to UPDATED_RKLX
        defaultDRkShouldNotBeFound("rklx.equals=" + UPDATED_RKLX);
    }

    @Test
    @Transactional
    void getAllDRksByRklxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rklx not equals to DEFAULT_RKLX
        defaultDRkShouldNotBeFound("rklx.notEquals=" + DEFAULT_RKLX);

        // Get all the dRkList where rklx not equals to UPDATED_RKLX
        defaultDRkShouldBeFound("rklx.notEquals=" + UPDATED_RKLX);
    }

    @Test
    @Transactional
    void getAllDRksByRklxIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rklx in DEFAULT_RKLX or UPDATED_RKLX
        defaultDRkShouldBeFound("rklx.in=" + DEFAULT_RKLX + "," + UPDATED_RKLX);

        // Get all the dRkList where rklx equals to UPDATED_RKLX
        defaultDRkShouldNotBeFound("rklx.in=" + UPDATED_RKLX);
    }

    @Test
    @Transactional
    void getAllDRksByRklxIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rklx is not null
        defaultDRkShouldBeFound("rklx.specified=true");

        // Get all the dRkList where rklx is null
        defaultDRkShouldNotBeFound("rklx.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByRklxContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rklx contains DEFAULT_RKLX
        defaultDRkShouldBeFound("rklx.contains=" + DEFAULT_RKLX);

        // Get all the dRkList where rklx contains UPDATED_RKLX
        defaultDRkShouldNotBeFound("rklx.contains=" + UPDATED_RKLX);
    }

    @Test
    @Transactional
    void getAllDRksByRklxNotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rklx does not contain DEFAULT_RKLX
        defaultDRkShouldNotBeFound("rklx.doesNotContain=" + DEFAULT_RKLX);

        // Get all the dRkList where rklx does not contain UPDATED_RKLX
        defaultDRkShouldBeFound("rklx.doesNotContain=" + UPDATED_RKLX);
    }

    @Test
    @Transactional
    void getAllDRksByRkbillnoIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rkbillno equals to DEFAULT_RKBILLNO
        defaultDRkShouldBeFound("rkbillno.equals=" + DEFAULT_RKBILLNO);

        // Get all the dRkList where rkbillno equals to UPDATED_RKBILLNO
        defaultDRkShouldNotBeFound("rkbillno.equals=" + UPDATED_RKBILLNO);
    }

    @Test
    @Transactional
    void getAllDRksByRkbillnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rkbillno not equals to DEFAULT_RKBILLNO
        defaultDRkShouldNotBeFound("rkbillno.notEquals=" + DEFAULT_RKBILLNO);

        // Get all the dRkList where rkbillno not equals to UPDATED_RKBILLNO
        defaultDRkShouldBeFound("rkbillno.notEquals=" + UPDATED_RKBILLNO);
    }

    @Test
    @Transactional
    void getAllDRksByRkbillnoIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rkbillno in DEFAULT_RKBILLNO or UPDATED_RKBILLNO
        defaultDRkShouldBeFound("rkbillno.in=" + DEFAULT_RKBILLNO + "," + UPDATED_RKBILLNO);

        // Get all the dRkList where rkbillno equals to UPDATED_RKBILLNO
        defaultDRkShouldNotBeFound("rkbillno.in=" + UPDATED_RKBILLNO);
    }

    @Test
    @Transactional
    void getAllDRksByRkbillnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rkbillno is not null
        defaultDRkShouldBeFound("rkbillno.specified=true");

        // Get all the dRkList where rkbillno is null
        defaultDRkShouldNotBeFound("rkbillno.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByRkbillnoContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rkbillno contains DEFAULT_RKBILLNO
        defaultDRkShouldBeFound("rkbillno.contains=" + DEFAULT_RKBILLNO);

        // Get all the dRkList where rkbillno contains UPDATED_RKBILLNO
        defaultDRkShouldNotBeFound("rkbillno.contains=" + UPDATED_RKBILLNO);
    }

    @Test
    @Transactional
    void getAllDRksByRkbillnoNotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where rkbillno does not contain DEFAULT_RKBILLNO
        defaultDRkShouldNotBeFound("rkbillno.doesNotContain=" + DEFAULT_RKBILLNO);

        // Get all the dRkList where rkbillno does not contain UPDATED_RKBILLNO
        defaultDRkShouldBeFound("rkbillno.doesNotContain=" + UPDATED_RKBILLNO);
    }

    @Test
    @Transactional
    void getAllDRksByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where company equals to DEFAULT_COMPANY
        defaultDRkShouldBeFound("company.equals=" + DEFAULT_COMPANY);

        // Get all the dRkList where company equals to UPDATED_COMPANY
        defaultDRkShouldNotBeFound("company.equals=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllDRksByCompanyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where company not equals to DEFAULT_COMPANY
        defaultDRkShouldNotBeFound("company.notEquals=" + DEFAULT_COMPANY);

        // Get all the dRkList where company not equals to UPDATED_COMPANY
        defaultDRkShouldBeFound("company.notEquals=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllDRksByCompanyIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where company in DEFAULT_COMPANY or UPDATED_COMPANY
        defaultDRkShouldBeFound("company.in=" + DEFAULT_COMPANY + "," + UPDATED_COMPANY);

        // Get all the dRkList where company equals to UPDATED_COMPANY
        defaultDRkShouldNotBeFound("company.in=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllDRksByCompanyIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where company is not null
        defaultDRkShouldBeFound("company.specified=true");

        // Get all the dRkList where company is null
        defaultDRkShouldNotBeFound("company.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByCompanyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where company is greater than or equal to DEFAULT_COMPANY
        defaultDRkShouldBeFound("company.greaterThanOrEqual=" + DEFAULT_COMPANY);

        // Get all the dRkList where company is greater than or equal to UPDATED_COMPANY
        defaultDRkShouldNotBeFound("company.greaterThanOrEqual=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllDRksByCompanyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where company is less than or equal to DEFAULT_COMPANY
        defaultDRkShouldBeFound("company.lessThanOrEqual=" + DEFAULT_COMPANY);

        // Get all the dRkList where company is less than or equal to SMALLER_COMPANY
        defaultDRkShouldNotBeFound("company.lessThanOrEqual=" + SMALLER_COMPANY);
    }

    @Test
    @Transactional
    void getAllDRksByCompanyIsLessThanSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where company is less than DEFAULT_COMPANY
        defaultDRkShouldNotBeFound("company.lessThan=" + DEFAULT_COMPANY);

        // Get all the dRkList where company is less than UPDATED_COMPANY
        defaultDRkShouldBeFound("company.lessThan=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllDRksByCompanyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where company is greater than DEFAULT_COMPANY
        defaultDRkShouldNotBeFound("company.greaterThan=" + DEFAULT_COMPANY);

        // Get all the dRkList where company is greater than SMALLER_COMPANY
        defaultDRkShouldBeFound("company.greaterThan=" + SMALLER_COMPANY);
    }

    @Test
    @Transactional
    void getAllDRksByDeptnameIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where deptname equals to DEFAULT_DEPTNAME
        defaultDRkShouldBeFound("deptname.equals=" + DEFAULT_DEPTNAME);

        // Get all the dRkList where deptname equals to UPDATED_DEPTNAME
        defaultDRkShouldNotBeFound("deptname.equals=" + UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void getAllDRksByDeptnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where deptname not equals to DEFAULT_DEPTNAME
        defaultDRkShouldNotBeFound("deptname.notEquals=" + DEFAULT_DEPTNAME);

        // Get all the dRkList where deptname not equals to UPDATED_DEPTNAME
        defaultDRkShouldBeFound("deptname.notEquals=" + UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void getAllDRksByDeptnameIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where deptname in DEFAULT_DEPTNAME or UPDATED_DEPTNAME
        defaultDRkShouldBeFound("deptname.in=" + DEFAULT_DEPTNAME + "," + UPDATED_DEPTNAME);

        // Get all the dRkList where deptname equals to UPDATED_DEPTNAME
        defaultDRkShouldNotBeFound("deptname.in=" + UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void getAllDRksByDeptnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where deptname is not null
        defaultDRkShouldBeFound("deptname.specified=true");

        // Get all the dRkList where deptname is null
        defaultDRkShouldNotBeFound("deptname.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByDeptnameContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where deptname contains DEFAULT_DEPTNAME
        defaultDRkShouldBeFound("deptname.contains=" + DEFAULT_DEPTNAME);

        // Get all the dRkList where deptname contains UPDATED_DEPTNAME
        defaultDRkShouldNotBeFound("deptname.contains=" + UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void getAllDRksByDeptnameNotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where deptname does not contain DEFAULT_DEPTNAME
        defaultDRkShouldNotBeFound("deptname.doesNotContain=" + DEFAULT_DEPTNAME);

        // Get all the dRkList where deptname does not contain UPDATED_DEPTNAME
        defaultDRkShouldBeFound("deptname.doesNotContain=" + UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void getAllDRksByJbrIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where jbr equals to DEFAULT_JBR
        defaultDRkShouldBeFound("jbr.equals=" + DEFAULT_JBR);

        // Get all the dRkList where jbr equals to UPDATED_JBR
        defaultDRkShouldNotBeFound("jbr.equals=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDRksByJbrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where jbr not equals to DEFAULT_JBR
        defaultDRkShouldNotBeFound("jbr.notEquals=" + DEFAULT_JBR);

        // Get all the dRkList where jbr not equals to UPDATED_JBR
        defaultDRkShouldBeFound("jbr.notEquals=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDRksByJbrIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where jbr in DEFAULT_JBR or UPDATED_JBR
        defaultDRkShouldBeFound("jbr.in=" + DEFAULT_JBR + "," + UPDATED_JBR);

        // Get all the dRkList where jbr equals to UPDATED_JBR
        defaultDRkShouldNotBeFound("jbr.in=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDRksByJbrIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where jbr is not null
        defaultDRkShouldBeFound("jbr.specified=true");

        // Get all the dRkList where jbr is null
        defaultDRkShouldNotBeFound("jbr.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByJbrContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where jbr contains DEFAULT_JBR
        defaultDRkShouldBeFound("jbr.contains=" + DEFAULT_JBR);

        // Get all the dRkList where jbr contains UPDATED_JBR
        defaultDRkShouldNotBeFound("jbr.contains=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDRksByJbrNotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where jbr does not contain DEFAULT_JBR
        defaultDRkShouldNotBeFound("jbr.doesNotContain=" + DEFAULT_JBR);

        // Get all the dRkList where jbr does not contain UPDATED_JBR
        defaultDRkShouldBeFound("jbr.doesNotContain=" + UPDATED_JBR);
    }

    @Test
    @Transactional
    void getAllDRksByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where remark equals to DEFAULT_REMARK
        defaultDRkShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the dRkList where remark equals to UPDATED_REMARK
        defaultDRkShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDRksByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where remark not equals to DEFAULT_REMARK
        defaultDRkShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the dRkList where remark not equals to UPDATED_REMARK
        defaultDRkShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDRksByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultDRkShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the dRkList where remark equals to UPDATED_REMARK
        defaultDRkShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDRksByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where remark is not null
        defaultDRkShouldBeFound("remark.specified=true");

        // Get all the dRkList where remark is null
        defaultDRkShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByRemarkContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where remark contains DEFAULT_REMARK
        defaultDRkShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the dRkList where remark contains UPDATED_REMARK
        defaultDRkShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDRksByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where remark does not contain DEFAULT_REMARK
        defaultDRkShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the dRkList where remark does not contain UPDATED_REMARK
        defaultDRkShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDRksByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where empn equals to DEFAULT_EMPN
        defaultDRkShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the dRkList where empn equals to UPDATED_EMPN
        defaultDRkShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where empn not equals to DEFAULT_EMPN
        defaultDRkShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the dRkList where empn not equals to UPDATED_EMPN
        defaultDRkShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultDRkShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the dRkList where empn equals to UPDATED_EMPN
        defaultDRkShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where empn is not null
        defaultDRkShouldBeFound("empn.specified=true");

        // Get all the dRkList where empn is null
        defaultDRkShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByEmpnContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where empn contains DEFAULT_EMPN
        defaultDRkShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the dRkList where empn contains UPDATED_EMPN
        defaultDRkShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where empn does not contain DEFAULT_EMPN
        defaultDRkShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the dRkList where empn does not contain UPDATED_EMPN
        defaultDRkShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByLrdateIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where lrdate equals to DEFAULT_LRDATE
        defaultDRkShouldBeFound("lrdate.equals=" + DEFAULT_LRDATE);

        // Get all the dRkList where lrdate equals to UPDATED_LRDATE
        defaultDRkShouldNotBeFound("lrdate.equals=" + UPDATED_LRDATE);
    }

    @Test
    @Transactional
    void getAllDRksByLrdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where lrdate not equals to DEFAULT_LRDATE
        defaultDRkShouldNotBeFound("lrdate.notEquals=" + DEFAULT_LRDATE);

        // Get all the dRkList where lrdate not equals to UPDATED_LRDATE
        defaultDRkShouldBeFound("lrdate.notEquals=" + UPDATED_LRDATE);
    }

    @Test
    @Transactional
    void getAllDRksByLrdateIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where lrdate in DEFAULT_LRDATE or UPDATED_LRDATE
        defaultDRkShouldBeFound("lrdate.in=" + DEFAULT_LRDATE + "," + UPDATED_LRDATE);

        // Get all the dRkList where lrdate equals to UPDATED_LRDATE
        defaultDRkShouldNotBeFound("lrdate.in=" + UPDATED_LRDATE);
    }

    @Test
    @Transactional
    void getAllDRksByLrdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where lrdate is not null
        defaultDRkShouldBeFound("lrdate.specified=true");

        // Get all the dRkList where lrdate is null
        defaultDRkShouldNotBeFound("lrdate.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksBySpbmIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where spbm equals to DEFAULT_SPBM
        defaultDRkShouldBeFound("spbm.equals=" + DEFAULT_SPBM);

        // Get all the dRkList where spbm equals to UPDATED_SPBM
        defaultDRkShouldNotBeFound("spbm.equals=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDRksBySpbmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where spbm not equals to DEFAULT_SPBM
        defaultDRkShouldNotBeFound("spbm.notEquals=" + DEFAULT_SPBM);

        // Get all the dRkList where spbm not equals to UPDATED_SPBM
        defaultDRkShouldBeFound("spbm.notEquals=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDRksBySpbmIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where spbm in DEFAULT_SPBM or UPDATED_SPBM
        defaultDRkShouldBeFound("spbm.in=" + DEFAULT_SPBM + "," + UPDATED_SPBM);

        // Get all the dRkList where spbm equals to UPDATED_SPBM
        defaultDRkShouldNotBeFound("spbm.in=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDRksBySpbmIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where spbm is not null
        defaultDRkShouldBeFound("spbm.specified=true");

        // Get all the dRkList where spbm is null
        defaultDRkShouldNotBeFound("spbm.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksBySpbmContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where spbm contains DEFAULT_SPBM
        defaultDRkShouldBeFound("spbm.contains=" + DEFAULT_SPBM);

        // Get all the dRkList where spbm contains UPDATED_SPBM
        defaultDRkShouldNotBeFound("spbm.contains=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDRksBySpbmNotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where spbm does not contain DEFAULT_SPBM
        defaultDRkShouldNotBeFound("spbm.doesNotContain=" + DEFAULT_SPBM);

        // Get all the dRkList where spbm does not contain UPDATED_SPBM
        defaultDRkShouldBeFound("spbm.doesNotContain=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDRksBySpmcIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where spmc equals to DEFAULT_SPMC
        defaultDRkShouldBeFound("spmc.equals=" + DEFAULT_SPMC);

        // Get all the dRkList where spmc equals to UPDATED_SPMC
        defaultDRkShouldNotBeFound("spmc.equals=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDRksBySpmcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where spmc not equals to DEFAULT_SPMC
        defaultDRkShouldNotBeFound("spmc.notEquals=" + DEFAULT_SPMC);

        // Get all the dRkList where spmc not equals to UPDATED_SPMC
        defaultDRkShouldBeFound("spmc.notEquals=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDRksBySpmcIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where spmc in DEFAULT_SPMC or UPDATED_SPMC
        defaultDRkShouldBeFound("spmc.in=" + DEFAULT_SPMC + "," + UPDATED_SPMC);

        // Get all the dRkList where spmc equals to UPDATED_SPMC
        defaultDRkShouldNotBeFound("spmc.in=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDRksBySpmcIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where spmc is not null
        defaultDRkShouldBeFound("spmc.specified=true");

        // Get all the dRkList where spmc is null
        defaultDRkShouldNotBeFound("spmc.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksBySpmcContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where spmc contains DEFAULT_SPMC
        defaultDRkShouldBeFound("spmc.contains=" + DEFAULT_SPMC);

        // Get all the dRkList where spmc contains UPDATED_SPMC
        defaultDRkShouldNotBeFound("spmc.contains=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDRksBySpmcNotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where spmc does not contain DEFAULT_SPMC
        defaultDRkShouldNotBeFound("spmc.doesNotContain=" + DEFAULT_SPMC);

        // Get all the dRkList where spmc does not contain UPDATED_SPMC
        defaultDRkShouldBeFound("spmc.doesNotContain=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDRksByGgxhIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where ggxh equals to DEFAULT_GGXH
        defaultDRkShouldBeFound("ggxh.equals=" + DEFAULT_GGXH);

        // Get all the dRkList where ggxh equals to UPDATED_GGXH
        defaultDRkShouldNotBeFound("ggxh.equals=" + UPDATED_GGXH);
    }

    @Test
    @Transactional
    void getAllDRksByGgxhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where ggxh not equals to DEFAULT_GGXH
        defaultDRkShouldNotBeFound("ggxh.notEquals=" + DEFAULT_GGXH);

        // Get all the dRkList where ggxh not equals to UPDATED_GGXH
        defaultDRkShouldBeFound("ggxh.notEquals=" + UPDATED_GGXH);
    }

    @Test
    @Transactional
    void getAllDRksByGgxhIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where ggxh in DEFAULT_GGXH or UPDATED_GGXH
        defaultDRkShouldBeFound("ggxh.in=" + DEFAULT_GGXH + "," + UPDATED_GGXH);

        // Get all the dRkList where ggxh equals to UPDATED_GGXH
        defaultDRkShouldNotBeFound("ggxh.in=" + UPDATED_GGXH);
    }

    @Test
    @Transactional
    void getAllDRksByGgxhIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where ggxh is not null
        defaultDRkShouldBeFound("ggxh.specified=true");

        // Get all the dRkList where ggxh is null
        defaultDRkShouldNotBeFound("ggxh.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByGgxhContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where ggxh contains DEFAULT_GGXH
        defaultDRkShouldBeFound("ggxh.contains=" + DEFAULT_GGXH);

        // Get all the dRkList where ggxh contains UPDATED_GGXH
        defaultDRkShouldNotBeFound("ggxh.contains=" + UPDATED_GGXH);
    }

    @Test
    @Transactional
    void getAllDRksByGgxhNotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where ggxh does not contain DEFAULT_GGXH
        defaultDRkShouldNotBeFound("ggxh.doesNotContain=" + DEFAULT_GGXH);

        // Get all the dRkList where ggxh does not contain UPDATED_GGXH
        defaultDRkShouldBeFound("ggxh.doesNotContain=" + UPDATED_GGXH);
    }

    @Test
    @Transactional
    void getAllDRksByDwIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where dw equals to DEFAULT_DW
        defaultDRkShouldBeFound("dw.equals=" + DEFAULT_DW);

        // Get all the dRkList where dw equals to UPDATED_DW
        defaultDRkShouldNotBeFound("dw.equals=" + UPDATED_DW);
    }

    @Test
    @Transactional
    void getAllDRksByDwIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where dw not equals to DEFAULT_DW
        defaultDRkShouldNotBeFound("dw.notEquals=" + DEFAULT_DW);

        // Get all the dRkList where dw not equals to UPDATED_DW
        defaultDRkShouldBeFound("dw.notEquals=" + UPDATED_DW);
    }

    @Test
    @Transactional
    void getAllDRksByDwIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where dw in DEFAULT_DW or UPDATED_DW
        defaultDRkShouldBeFound("dw.in=" + DEFAULT_DW + "," + UPDATED_DW);

        // Get all the dRkList where dw equals to UPDATED_DW
        defaultDRkShouldNotBeFound("dw.in=" + UPDATED_DW);
    }

    @Test
    @Transactional
    void getAllDRksByDwIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where dw is not null
        defaultDRkShouldBeFound("dw.specified=true");

        // Get all the dRkList where dw is null
        defaultDRkShouldNotBeFound("dw.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByDwContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where dw contains DEFAULT_DW
        defaultDRkShouldBeFound("dw.contains=" + DEFAULT_DW);

        // Get all the dRkList where dw contains UPDATED_DW
        defaultDRkShouldNotBeFound("dw.contains=" + UPDATED_DW);
    }

    @Test
    @Transactional
    void getAllDRksByDwNotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where dw does not contain DEFAULT_DW
        defaultDRkShouldNotBeFound("dw.doesNotContain=" + DEFAULT_DW);

        // Get all the dRkList where dw does not contain UPDATED_DW
        defaultDRkShouldBeFound("dw.doesNotContain=" + UPDATED_DW);
    }

    @Test
    @Transactional
    void getAllDRksByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where price equals to DEFAULT_PRICE
        defaultDRkShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the dRkList where price equals to UPDATED_PRICE
        defaultDRkShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDRksByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where price not equals to DEFAULT_PRICE
        defaultDRkShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the dRkList where price not equals to UPDATED_PRICE
        defaultDRkShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDRksByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultDRkShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the dRkList where price equals to UPDATED_PRICE
        defaultDRkShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDRksByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where price is not null
        defaultDRkShouldBeFound("price.specified=true");

        // Get all the dRkList where price is null
        defaultDRkShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where price is greater than or equal to DEFAULT_PRICE
        defaultDRkShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the dRkList where price is greater than or equal to UPDATED_PRICE
        defaultDRkShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDRksByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where price is less than or equal to DEFAULT_PRICE
        defaultDRkShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the dRkList where price is less than or equal to SMALLER_PRICE
        defaultDRkShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllDRksByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where price is less than DEFAULT_PRICE
        defaultDRkShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the dRkList where price is less than UPDATED_PRICE
        defaultDRkShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDRksByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where price is greater than DEFAULT_PRICE
        defaultDRkShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the dRkList where price is greater than SMALLER_PRICE
        defaultDRkShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllDRksBySlIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sl equals to DEFAULT_SL
        defaultDRkShouldBeFound("sl.equals=" + DEFAULT_SL);

        // Get all the dRkList where sl equals to UPDATED_SL
        defaultDRkShouldNotBeFound("sl.equals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDRksBySlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sl not equals to DEFAULT_SL
        defaultDRkShouldNotBeFound("sl.notEquals=" + DEFAULT_SL);

        // Get all the dRkList where sl not equals to UPDATED_SL
        defaultDRkShouldBeFound("sl.notEquals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDRksBySlIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sl in DEFAULT_SL or UPDATED_SL
        defaultDRkShouldBeFound("sl.in=" + DEFAULT_SL + "," + UPDATED_SL);

        // Get all the dRkList where sl equals to UPDATED_SL
        defaultDRkShouldNotBeFound("sl.in=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDRksBySlIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sl is not null
        defaultDRkShouldBeFound("sl.specified=true");

        // Get all the dRkList where sl is null
        defaultDRkShouldNotBeFound("sl.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksBySlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sl is greater than or equal to DEFAULT_SL
        defaultDRkShouldBeFound("sl.greaterThanOrEqual=" + DEFAULT_SL);

        // Get all the dRkList where sl is greater than or equal to UPDATED_SL
        defaultDRkShouldNotBeFound("sl.greaterThanOrEqual=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDRksBySlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sl is less than or equal to DEFAULT_SL
        defaultDRkShouldBeFound("sl.lessThanOrEqual=" + DEFAULT_SL);

        // Get all the dRkList where sl is less than or equal to SMALLER_SL
        defaultDRkShouldNotBeFound("sl.lessThanOrEqual=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllDRksBySlIsLessThanSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sl is less than DEFAULT_SL
        defaultDRkShouldNotBeFound("sl.lessThan=" + DEFAULT_SL);

        // Get all the dRkList where sl is less than UPDATED_SL
        defaultDRkShouldBeFound("sl.lessThan=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDRksBySlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sl is greater than DEFAULT_SL
        defaultDRkShouldNotBeFound("sl.greaterThan=" + DEFAULT_SL);

        // Get all the dRkList where sl is greater than SMALLER_SL
        defaultDRkShouldBeFound("sl.greaterThan=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllDRksByJeIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where je equals to DEFAULT_JE
        defaultDRkShouldBeFound("je.equals=" + DEFAULT_JE);

        // Get all the dRkList where je equals to UPDATED_JE
        defaultDRkShouldNotBeFound("je.equals=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDRksByJeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where je not equals to DEFAULT_JE
        defaultDRkShouldNotBeFound("je.notEquals=" + DEFAULT_JE);

        // Get all the dRkList where je not equals to UPDATED_JE
        defaultDRkShouldBeFound("je.notEquals=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDRksByJeIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where je in DEFAULT_JE or UPDATED_JE
        defaultDRkShouldBeFound("je.in=" + DEFAULT_JE + "," + UPDATED_JE);

        // Get all the dRkList where je equals to UPDATED_JE
        defaultDRkShouldNotBeFound("je.in=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDRksByJeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where je is not null
        defaultDRkShouldBeFound("je.specified=true");

        // Get all the dRkList where je is null
        defaultDRkShouldNotBeFound("je.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByJeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where je is greater than or equal to DEFAULT_JE
        defaultDRkShouldBeFound("je.greaterThanOrEqual=" + DEFAULT_JE);

        // Get all the dRkList where je is greater than or equal to UPDATED_JE
        defaultDRkShouldNotBeFound("je.greaterThanOrEqual=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDRksByJeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where je is less than or equal to DEFAULT_JE
        defaultDRkShouldBeFound("je.lessThanOrEqual=" + DEFAULT_JE);

        // Get all the dRkList where je is less than or equal to SMALLER_JE
        defaultDRkShouldNotBeFound("je.lessThanOrEqual=" + SMALLER_JE);
    }

    @Test
    @Transactional
    void getAllDRksByJeIsLessThanSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where je is less than DEFAULT_JE
        defaultDRkShouldNotBeFound("je.lessThan=" + DEFAULT_JE);

        // Get all the dRkList where je is less than UPDATED_JE
        defaultDRkShouldBeFound("je.lessThan=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDRksByJeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where je is greater than DEFAULT_JE
        defaultDRkShouldNotBeFound("je.greaterThan=" + DEFAULT_JE);

        // Get all the dRkList where je is greater than SMALLER_JE
        defaultDRkShouldBeFound("je.greaterThan=" + SMALLER_JE);
    }

    @Test
    @Transactional
    void getAllDRksBySignIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sign equals to DEFAULT_SIGN
        defaultDRkShouldBeFound("sign.equals=" + DEFAULT_SIGN);

        // Get all the dRkList where sign equals to UPDATED_SIGN
        defaultDRkShouldNotBeFound("sign.equals=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllDRksBySignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sign not equals to DEFAULT_SIGN
        defaultDRkShouldNotBeFound("sign.notEquals=" + DEFAULT_SIGN);

        // Get all the dRkList where sign not equals to UPDATED_SIGN
        defaultDRkShouldBeFound("sign.notEquals=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllDRksBySignIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sign in DEFAULT_SIGN or UPDATED_SIGN
        defaultDRkShouldBeFound("sign.in=" + DEFAULT_SIGN + "," + UPDATED_SIGN);

        // Get all the dRkList where sign equals to UPDATED_SIGN
        defaultDRkShouldNotBeFound("sign.in=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllDRksBySignIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sign is not null
        defaultDRkShouldBeFound("sign.specified=true");

        // Get all the dRkList where sign is null
        defaultDRkShouldNotBeFound("sign.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksBySignIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sign is greater than or equal to DEFAULT_SIGN
        defaultDRkShouldBeFound("sign.greaterThanOrEqual=" + DEFAULT_SIGN);

        // Get all the dRkList where sign is greater than or equal to UPDATED_SIGN
        defaultDRkShouldNotBeFound("sign.greaterThanOrEqual=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllDRksBySignIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sign is less than or equal to DEFAULT_SIGN
        defaultDRkShouldBeFound("sign.lessThanOrEqual=" + DEFAULT_SIGN);

        // Get all the dRkList where sign is less than or equal to SMALLER_SIGN
        defaultDRkShouldNotBeFound("sign.lessThanOrEqual=" + SMALLER_SIGN);
    }

    @Test
    @Transactional
    void getAllDRksBySignIsLessThanSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sign is less than DEFAULT_SIGN
        defaultDRkShouldNotBeFound("sign.lessThan=" + DEFAULT_SIGN);

        // Get all the dRkList where sign is less than UPDATED_SIGN
        defaultDRkShouldBeFound("sign.lessThan=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllDRksBySignIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where sign is greater than DEFAULT_SIGN
        defaultDRkShouldNotBeFound("sign.greaterThan=" + DEFAULT_SIGN);

        // Get all the dRkList where sign is greater than SMALLER_SIGN
        defaultDRkShouldBeFound("sign.greaterThan=" + SMALLER_SIGN);
    }

    @Test
    @Transactional
    void getAllDRksByMemoIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where memo equals to DEFAULT_MEMO
        defaultDRkShouldBeFound("memo.equals=" + DEFAULT_MEMO);

        // Get all the dRkList where memo equals to UPDATED_MEMO
        defaultDRkShouldNotBeFound("memo.equals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDRksByMemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where memo not equals to DEFAULT_MEMO
        defaultDRkShouldNotBeFound("memo.notEquals=" + DEFAULT_MEMO);

        // Get all the dRkList where memo not equals to UPDATED_MEMO
        defaultDRkShouldBeFound("memo.notEquals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDRksByMemoIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where memo in DEFAULT_MEMO or UPDATED_MEMO
        defaultDRkShouldBeFound("memo.in=" + DEFAULT_MEMO + "," + UPDATED_MEMO);

        // Get all the dRkList where memo equals to UPDATED_MEMO
        defaultDRkShouldNotBeFound("memo.in=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDRksByMemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where memo is not null
        defaultDRkShouldBeFound("memo.specified=true");

        // Get all the dRkList where memo is null
        defaultDRkShouldNotBeFound("memo.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByMemoContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where memo contains DEFAULT_MEMO
        defaultDRkShouldBeFound("memo.contains=" + DEFAULT_MEMO);

        // Get all the dRkList where memo contains UPDATED_MEMO
        defaultDRkShouldNotBeFound("memo.contains=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDRksByMemoNotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where memo does not contain DEFAULT_MEMO
        defaultDRkShouldNotBeFound("memo.doesNotContain=" + DEFAULT_MEMO);

        // Get all the dRkList where memo does not contain UPDATED_MEMO
        defaultDRkShouldBeFound("memo.doesNotContain=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDRksByFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where flag equals to DEFAULT_FLAG
        defaultDRkShouldBeFound("flag.equals=" + DEFAULT_FLAG);

        // Get all the dRkList where flag equals to UPDATED_FLAG
        defaultDRkShouldNotBeFound("flag.equals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDRksByFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where flag not equals to DEFAULT_FLAG
        defaultDRkShouldNotBeFound("flag.notEquals=" + DEFAULT_FLAG);

        // Get all the dRkList where flag not equals to UPDATED_FLAG
        defaultDRkShouldBeFound("flag.notEquals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDRksByFlagIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where flag in DEFAULT_FLAG or UPDATED_FLAG
        defaultDRkShouldBeFound("flag.in=" + DEFAULT_FLAG + "," + UPDATED_FLAG);

        // Get all the dRkList where flag equals to UPDATED_FLAG
        defaultDRkShouldNotBeFound("flag.in=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDRksByFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where flag is not null
        defaultDRkShouldBeFound("flag.specified=true");

        // Get all the dRkList where flag is null
        defaultDRkShouldNotBeFound("flag.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByFlagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where flag is greater than or equal to DEFAULT_FLAG
        defaultDRkShouldBeFound("flag.greaterThanOrEqual=" + DEFAULT_FLAG);

        // Get all the dRkList where flag is greater than or equal to UPDATED_FLAG
        defaultDRkShouldNotBeFound("flag.greaterThanOrEqual=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDRksByFlagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where flag is less than or equal to DEFAULT_FLAG
        defaultDRkShouldBeFound("flag.lessThanOrEqual=" + DEFAULT_FLAG);

        // Get all the dRkList where flag is less than or equal to SMALLER_FLAG
        defaultDRkShouldNotBeFound("flag.lessThanOrEqual=" + SMALLER_FLAG);
    }

    @Test
    @Transactional
    void getAllDRksByFlagIsLessThanSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where flag is less than DEFAULT_FLAG
        defaultDRkShouldNotBeFound("flag.lessThan=" + DEFAULT_FLAG);

        // Get all the dRkList where flag is less than UPDATED_FLAG
        defaultDRkShouldBeFound("flag.lessThan=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllDRksByFlagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where flag is greater than DEFAULT_FLAG
        defaultDRkShouldNotBeFound("flag.greaterThan=" + DEFAULT_FLAG);

        // Get all the dRkList where flag is greater than SMALLER_FLAG
        defaultDRkShouldBeFound("flag.greaterThan=" + SMALLER_FLAG);
    }

    @Test
    @Transactional
    void getAllDRksByf1IsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1 equals to DEFAULT_F_1
        defaultDRkShouldBeFound("f1.equals=" + DEFAULT_F_1);

        // Get all the dRkList where f1 equals to UPDATED_F_1
        defaultDRkShouldNotBeFound("f1.equals=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDRksByf1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1 not equals to DEFAULT_F_1
        defaultDRkShouldNotBeFound("f1.notEquals=" + DEFAULT_F_1);

        // Get all the dRkList where f1 not equals to UPDATED_F_1
        defaultDRkShouldBeFound("f1.notEquals=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDRksByf1IsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1 in DEFAULT_F_1 or UPDATED_F_1
        defaultDRkShouldBeFound("f1.in=" + DEFAULT_F_1 + "," + UPDATED_F_1);

        // Get all the dRkList where f1 equals to UPDATED_F_1
        defaultDRkShouldNotBeFound("f1.in=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDRksByf1IsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1 is not null
        defaultDRkShouldBeFound("f1.specified=true");

        // Get all the dRkList where f1 is null
        defaultDRkShouldNotBeFound("f1.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByf1ContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1 contains DEFAULT_F_1
        defaultDRkShouldBeFound("f1.contains=" + DEFAULT_F_1);

        // Get all the dRkList where f1 contains UPDATED_F_1
        defaultDRkShouldNotBeFound("f1.contains=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDRksByf1NotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1 does not contain DEFAULT_F_1
        defaultDRkShouldNotBeFound("f1.doesNotContain=" + DEFAULT_F_1);

        // Get all the dRkList where f1 does not contain UPDATED_F_1
        defaultDRkShouldBeFound("f1.doesNotContain=" + UPDATED_F_1);
    }

    @Test
    @Transactional
    void getAllDRksByf2IsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2 equals to DEFAULT_F_2
        defaultDRkShouldBeFound("f2.equals=" + DEFAULT_F_2);

        // Get all the dRkList where f2 equals to UPDATED_F_2
        defaultDRkShouldNotBeFound("f2.equals=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDRksByf2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2 not equals to DEFAULT_F_2
        defaultDRkShouldNotBeFound("f2.notEquals=" + DEFAULT_F_2);

        // Get all the dRkList where f2 not equals to UPDATED_F_2
        defaultDRkShouldBeFound("f2.notEquals=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDRksByf2IsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2 in DEFAULT_F_2 or UPDATED_F_2
        defaultDRkShouldBeFound("f2.in=" + DEFAULT_F_2 + "," + UPDATED_F_2);

        // Get all the dRkList where f2 equals to UPDATED_F_2
        defaultDRkShouldNotBeFound("f2.in=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDRksByf2IsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2 is not null
        defaultDRkShouldBeFound("f2.specified=true");

        // Get all the dRkList where f2 is null
        defaultDRkShouldNotBeFound("f2.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByf2ContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2 contains DEFAULT_F_2
        defaultDRkShouldBeFound("f2.contains=" + DEFAULT_F_2);

        // Get all the dRkList where f2 contains UPDATED_F_2
        defaultDRkShouldNotBeFound("f2.contains=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDRksByf2NotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2 does not contain DEFAULT_F_2
        defaultDRkShouldNotBeFound("f2.doesNotContain=" + DEFAULT_F_2);

        // Get all the dRkList where f2 does not contain UPDATED_F_2
        defaultDRkShouldBeFound("f2.doesNotContain=" + UPDATED_F_2);
    }

    @Test
    @Transactional
    void getAllDRksByf1empnIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1empn equals to DEFAULT_F_1_EMPN
        defaultDRkShouldBeFound("f1empn.equals=" + DEFAULT_F_1_EMPN);

        // Get all the dRkList where f1empn equals to UPDATED_F_1_EMPN
        defaultDRkShouldNotBeFound("f1empn.equals=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByf1empnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1empn not equals to DEFAULT_F_1_EMPN
        defaultDRkShouldNotBeFound("f1empn.notEquals=" + DEFAULT_F_1_EMPN);

        // Get all the dRkList where f1empn not equals to UPDATED_F_1_EMPN
        defaultDRkShouldBeFound("f1empn.notEquals=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByf1empnIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1empn in DEFAULT_F_1_EMPN or UPDATED_F_1_EMPN
        defaultDRkShouldBeFound("f1empn.in=" + DEFAULT_F_1_EMPN + "," + UPDATED_F_1_EMPN);

        // Get all the dRkList where f1empn equals to UPDATED_F_1_EMPN
        defaultDRkShouldNotBeFound("f1empn.in=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByf1empnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1empn is not null
        defaultDRkShouldBeFound("f1empn.specified=true");

        // Get all the dRkList where f1empn is null
        defaultDRkShouldNotBeFound("f1empn.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByf1empnContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1empn contains DEFAULT_F_1_EMPN
        defaultDRkShouldBeFound("f1empn.contains=" + DEFAULT_F_1_EMPN);

        // Get all the dRkList where f1empn contains UPDATED_F_1_EMPN
        defaultDRkShouldNotBeFound("f1empn.contains=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByf1empnNotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1empn does not contain DEFAULT_F_1_EMPN
        defaultDRkShouldNotBeFound("f1empn.doesNotContain=" + DEFAULT_F_1_EMPN);

        // Get all the dRkList where f1empn does not contain UPDATED_F_1_EMPN
        defaultDRkShouldBeFound("f1empn.doesNotContain=" + UPDATED_F_1_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByf2empnIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2empn equals to DEFAULT_F_2_EMPN
        defaultDRkShouldBeFound("f2empn.equals=" + DEFAULT_F_2_EMPN);

        // Get all the dRkList where f2empn equals to UPDATED_F_2_EMPN
        defaultDRkShouldNotBeFound("f2empn.equals=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByf2empnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2empn not equals to DEFAULT_F_2_EMPN
        defaultDRkShouldNotBeFound("f2empn.notEquals=" + DEFAULT_F_2_EMPN);

        // Get all the dRkList where f2empn not equals to UPDATED_F_2_EMPN
        defaultDRkShouldBeFound("f2empn.notEquals=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByf2empnIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2empn in DEFAULT_F_2_EMPN or UPDATED_F_2_EMPN
        defaultDRkShouldBeFound("f2empn.in=" + DEFAULT_F_2_EMPN + "," + UPDATED_F_2_EMPN);

        // Get all the dRkList where f2empn equals to UPDATED_F_2_EMPN
        defaultDRkShouldNotBeFound("f2empn.in=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByf2empnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2empn is not null
        defaultDRkShouldBeFound("f2empn.specified=true");

        // Get all the dRkList where f2empn is null
        defaultDRkShouldNotBeFound("f2empn.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByf2empnContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2empn contains DEFAULT_F_2_EMPN
        defaultDRkShouldBeFound("f2empn.contains=" + DEFAULT_F_2_EMPN);

        // Get all the dRkList where f2empn contains UPDATED_F_2_EMPN
        defaultDRkShouldNotBeFound("f2empn.contains=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByf2empnNotContainsSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2empn does not contain DEFAULT_F_2_EMPN
        defaultDRkShouldNotBeFound("f2empn.doesNotContain=" + DEFAULT_F_2_EMPN);

        // Get all the dRkList where f2empn does not contain UPDATED_F_2_EMPN
        defaultDRkShouldBeFound("f2empn.doesNotContain=" + UPDATED_F_2_EMPN);
    }

    @Test
    @Transactional
    void getAllDRksByf1sjIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1sj equals to DEFAULT_F_1_SJ
        defaultDRkShouldBeFound("f1sj.equals=" + DEFAULT_F_1_SJ);

        // Get all the dRkList where f1sj equals to UPDATED_F_1_SJ
        defaultDRkShouldNotBeFound("f1sj.equals=" + UPDATED_F_1_SJ);
    }

    @Test
    @Transactional
    void getAllDRksByf1sjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1sj not equals to DEFAULT_F_1_SJ
        defaultDRkShouldNotBeFound("f1sj.notEquals=" + DEFAULT_F_1_SJ);

        // Get all the dRkList where f1sj not equals to UPDATED_F_1_SJ
        defaultDRkShouldBeFound("f1sj.notEquals=" + UPDATED_F_1_SJ);
    }

    @Test
    @Transactional
    void getAllDRksByf1sjIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1sj in DEFAULT_F_1_SJ or UPDATED_F_1_SJ
        defaultDRkShouldBeFound("f1sj.in=" + DEFAULT_F_1_SJ + "," + UPDATED_F_1_SJ);

        // Get all the dRkList where f1sj equals to UPDATED_F_1_SJ
        defaultDRkShouldNotBeFound("f1sj.in=" + UPDATED_F_1_SJ);
    }

    @Test
    @Transactional
    void getAllDRksByf1sjIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f1sj is not null
        defaultDRkShouldBeFound("f1sj.specified=true");

        // Get all the dRkList where f1sj is null
        defaultDRkShouldNotBeFound("f1sj.specified=false");
    }

    @Test
    @Transactional
    void getAllDRksByf2sjIsEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2sj equals to DEFAULT_F_2_SJ
        defaultDRkShouldBeFound("f2sj.equals=" + DEFAULT_F_2_SJ);

        // Get all the dRkList where f2sj equals to UPDATED_F_2_SJ
        defaultDRkShouldNotBeFound("f2sj.equals=" + UPDATED_F_2_SJ);
    }

    @Test
    @Transactional
    void getAllDRksByf2sjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2sj not equals to DEFAULT_F_2_SJ
        defaultDRkShouldNotBeFound("f2sj.notEquals=" + DEFAULT_F_2_SJ);

        // Get all the dRkList where f2sj not equals to UPDATED_F_2_SJ
        defaultDRkShouldBeFound("f2sj.notEquals=" + UPDATED_F_2_SJ);
    }

    @Test
    @Transactional
    void getAllDRksByf2sjIsInShouldWork() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2sj in DEFAULT_F_2_SJ or UPDATED_F_2_SJ
        defaultDRkShouldBeFound("f2sj.in=" + DEFAULT_F_2_SJ + "," + UPDATED_F_2_SJ);

        // Get all the dRkList where f2sj equals to UPDATED_F_2_SJ
        defaultDRkShouldNotBeFound("f2sj.in=" + UPDATED_F_2_SJ);
    }

    @Test
    @Transactional
    void getAllDRksByf2sjIsNullOrNotNull() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        // Get all the dRkList where f2sj is not null
        defaultDRkShouldBeFound("f2sj.specified=true");

        // Get all the dRkList where f2sj is null
        defaultDRkShouldNotBeFound("f2sj.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDRkShouldBeFound(String filter) throws Exception {
        restDRkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dRk.getId().intValue())))
            .andExpect(jsonPath("$.[*].rkdate").value(hasItem(DEFAULT_RKDATE.toString())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].rklx").value(hasItem(DEFAULT_RKLX)))
            .andExpect(jsonPath("$.[*].rkbillno").value(hasItem(DEFAULT_RKBILLNO)))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.intValue())))
            .andExpect(jsonPath("$.[*].deptname").value(hasItem(DEFAULT_DEPTNAME)))
            .andExpect(jsonPath("$.[*].jbr").value(hasItem(DEFAULT_JBR)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].lrdate").value(hasItem(DEFAULT_LRDATE.toString())))
            .andExpect(jsonPath("$.[*].spbm").value(hasItem(DEFAULT_SPBM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].ggxh").value(hasItem(DEFAULT_GGXH)))
            .andExpect(jsonPath("$.[*].dw").value(hasItem(DEFAULT_DW)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].je").value(hasItem(sameNumber(DEFAULT_JE))))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].f1").value(hasItem(DEFAULT_F_1)))
            .andExpect(jsonPath("$.[*].f2").value(hasItem(DEFAULT_F_2)))
            .andExpect(jsonPath("$.[*].f1empn").value(hasItem(DEFAULT_F_1_EMPN)))
            .andExpect(jsonPath("$.[*].f2empn").value(hasItem(DEFAULT_F_2_EMPN)))
            .andExpect(jsonPath("$.[*].f1sj").value(hasItem(DEFAULT_F_1_SJ.toString())))
            .andExpect(jsonPath("$.[*].f2sj").value(hasItem(DEFAULT_F_2_SJ.toString())));

        // Check, that the count call also returns 1
        restDRkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDRkShouldNotBeFound(String filter) throws Exception {
        restDRkMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDRkMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDRk() throws Exception {
        // Get the dRk
        restDRkMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDRk() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        int databaseSizeBeforeUpdate = dRkRepository.findAll().size();

        // Update the dRk
        DRk updatedDRk = dRkRepository.findById(dRk.getId()).get();
        // Disconnect from session so that the updates on updatedDRk are not directly saved in db
        em.detach(updatedDRk);
        updatedDRk
            .rkdate(UPDATED_RKDATE)
            .depot(UPDATED_DEPOT)
            .rklx(UPDATED_RKLX)
            .rkbillno(UPDATED_RKBILLNO)
            .company(UPDATED_COMPANY)
            .deptname(UPDATED_DEPTNAME)
            .jbr(UPDATED_JBR)
            .remark(UPDATED_REMARK)
            .empn(UPDATED_EMPN)
            .lrdate(UPDATED_LRDATE)
            .spbm(UPDATED_SPBM)
            .spmc(UPDATED_SPMC)
            .ggxh(UPDATED_GGXH)
            .dw(UPDATED_DW)
            .price(UPDATED_PRICE)
            .sl(UPDATED_SL)
            .je(UPDATED_JE)
            .sign(UPDATED_SIGN)
            .memo(UPDATED_MEMO)
            .flag(UPDATED_FLAG)
            .f1(UPDATED_F_1)
            .f2(UPDATED_F_2)
            .f1empn(UPDATED_F_1_EMPN)
            .f2empn(UPDATED_F_2_EMPN)
            .f1sj(UPDATED_F_1_SJ)
            .f2sj(UPDATED_F_2_SJ);
        DRkDTO dRkDTO = dRkMapper.toDto(updatedDRk);

        restDRkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dRkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dRkDTO))
            )
            .andExpect(status().isOk());

        // Validate the DRk in the database
        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeUpdate);
        DRk testDRk = dRkList.get(dRkList.size() - 1);
        assertThat(testDRk.getRkdate()).isEqualTo(UPDATED_RKDATE);
        assertThat(testDRk.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testDRk.getRklx()).isEqualTo(UPDATED_RKLX);
        assertThat(testDRk.getRkbillno()).isEqualTo(UPDATED_RKBILLNO);
        assertThat(testDRk.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testDRk.getDeptname()).isEqualTo(UPDATED_DEPTNAME);
        assertThat(testDRk.getJbr()).isEqualTo(UPDATED_JBR);
        assertThat(testDRk.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testDRk.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testDRk.getLrdate()).isEqualTo(UPDATED_LRDATE);
        assertThat(testDRk.getSpbm()).isEqualTo(UPDATED_SPBM);
        assertThat(testDRk.getSpmc()).isEqualTo(UPDATED_SPMC);
        assertThat(testDRk.getGgxh()).isEqualTo(UPDATED_GGXH);
        assertThat(testDRk.getDw()).isEqualTo(UPDATED_DW);
        assertThat(testDRk.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDRk.getSl()).isEqualTo(UPDATED_SL);
        assertThat(testDRk.getJe()).isEqualTo(UPDATED_JE);
        assertThat(testDRk.getSign()).isEqualTo(UPDATED_SIGN);
        assertThat(testDRk.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testDRk.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testDRk.getf1()).isEqualTo(UPDATED_F_1);
        assertThat(testDRk.getf2()).isEqualTo(UPDATED_F_2);
        assertThat(testDRk.getf1empn()).isEqualTo(UPDATED_F_1_EMPN);
        assertThat(testDRk.getf2empn()).isEqualTo(UPDATED_F_2_EMPN);
        assertThat(testDRk.getf1sj()).isEqualTo(UPDATED_F_1_SJ);
        assertThat(testDRk.getf2sj()).isEqualTo(UPDATED_F_2_SJ);

        // Validate the DRk in Elasticsearch
        verify(mockDRkSearchRepository).save(testDRk);
    }

    @Test
    @Transactional
    void putNonExistingDRk() throws Exception {
        int databaseSizeBeforeUpdate = dRkRepository.findAll().size();
        dRk.setId(count.incrementAndGet());

        // Create the DRk
        DRkDTO dRkDTO = dRkMapper.toDto(dRk);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDRkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dRkDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dRkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DRk in the database
        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DRk in Elasticsearch
        verify(mockDRkSearchRepository, times(0)).save(dRk);
    }

    @Test
    @Transactional
    void putWithIdMismatchDRk() throws Exception {
        int databaseSizeBeforeUpdate = dRkRepository.findAll().size();
        dRk.setId(count.incrementAndGet());

        // Create the DRk
        DRkDTO dRkDTO = dRkMapper.toDto(dRk);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDRkMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dRkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DRk in the database
        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DRk in Elasticsearch
        verify(mockDRkSearchRepository, times(0)).save(dRk);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDRk() throws Exception {
        int databaseSizeBeforeUpdate = dRkRepository.findAll().size();
        dRk.setId(count.incrementAndGet());

        // Create the DRk
        DRkDTO dRkDTO = dRkMapper.toDto(dRk);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDRkMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dRkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DRk in the database
        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DRk in Elasticsearch
        verify(mockDRkSearchRepository, times(0)).save(dRk);
    }

    @Test
    @Transactional
    void partialUpdateDRkWithPatch() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        int databaseSizeBeforeUpdate = dRkRepository.findAll().size();

        // Update the dRk using partial update
        DRk partialUpdatedDRk = new DRk();
        partialUpdatedDRk.setId(dRk.getId());

        partialUpdatedDRk
            .rkbillno(UPDATED_RKBILLNO)
            .deptname(UPDATED_DEPTNAME)
            .jbr(UPDATED_JBR)
            .remark(UPDATED_REMARK)
            .lrdate(UPDATED_LRDATE)
            .dw(UPDATED_DW)
            .memo(UPDATED_MEMO)
            .f1(UPDATED_F_1)
            .f2(UPDATED_F_2)
            .f2empn(UPDATED_F_2_EMPN);

        restDRkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDRk.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDRk))
            )
            .andExpect(status().isOk());

        // Validate the DRk in the database
        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeUpdate);
        DRk testDRk = dRkList.get(dRkList.size() - 1);
        assertThat(testDRk.getRkdate()).isEqualTo(DEFAULT_RKDATE);
        assertThat(testDRk.getDepot()).isEqualTo(DEFAULT_DEPOT);
        assertThat(testDRk.getRklx()).isEqualTo(DEFAULT_RKLX);
        assertThat(testDRk.getRkbillno()).isEqualTo(UPDATED_RKBILLNO);
        assertThat(testDRk.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testDRk.getDeptname()).isEqualTo(UPDATED_DEPTNAME);
        assertThat(testDRk.getJbr()).isEqualTo(UPDATED_JBR);
        assertThat(testDRk.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testDRk.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testDRk.getLrdate()).isEqualTo(UPDATED_LRDATE);
        assertThat(testDRk.getSpbm()).isEqualTo(DEFAULT_SPBM);
        assertThat(testDRk.getSpmc()).isEqualTo(DEFAULT_SPMC);
        assertThat(testDRk.getGgxh()).isEqualTo(DEFAULT_GGXH);
        assertThat(testDRk.getDw()).isEqualTo(UPDATED_DW);
        assertThat(testDRk.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testDRk.getSl()).isEqualByComparingTo(DEFAULT_SL);
        assertThat(testDRk.getJe()).isEqualByComparingTo(DEFAULT_JE);
        assertThat(testDRk.getSign()).isEqualTo(DEFAULT_SIGN);
        assertThat(testDRk.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testDRk.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testDRk.getf1()).isEqualTo(UPDATED_F_1);
        assertThat(testDRk.getf2()).isEqualTo(UPDATED_F_2);
        assertThat(testDRk.getf1empn()).isEqualTo(DEFAULT_F_1_EMPN);
        assertThat(testDRk.getf2empn()).isEqualTo(UPDATED_F_2_EMPN);
        assertThat(testDRk.getf1sj()).isEqualTo(DEFAULT_F_1_SJ);
        assertThat(testDRk.getf2sj()).isEqualTo(DEFAULT_F_2_SJ);
    }

    @Test
    @Transactional
    void fullUpdateDRkWithPatch() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        int databaseSizeBeforeUpdate = dRkRepository.findAll().size();

        // Update the dRk using partial update
        DRk partialUpdatedDRk = new DRk();
        partialUpdatedDRk.setId(dRk.getId());

        partialUpdatedDRk
            .rkdate(UPDATED_RKDATE)
            .depot(UPDATED_DEPOT)
            .rklx(UPDATED_RKLX)
            .rkbillno(UPDATED_RKBILLNO)
            .company(UPDATED_COMPANY)
            .deptname(UPDATED_DEPTNAME)
            .jbr(UPDATED_JBR)
            .remark(UPDATED_REMARK)
            .empn(UPDATED_EMPN)
            .lrdate(UPDATED_LRDATE)
            .spbm(UPDATED_SPBM)
            .spmc(UPDATED_SPMC)
            .ggxh(UPDATED_GGXH)
            .dw(UPDATED_DW)
            .price(UPDATED_PRICE)
            .sl(UPDATED_SL)
            .je(UPDATED_JE)
            .sign(UPDATED_SIGN)
            .memo(UPDATED_MEMO)
            .flag(UPDATED_FLAG)
            .f1(UPDATED_F_1)
            .f2(UPDATED_F_2)
            .f1empn(UPDATED_F_1_EMPN)
            .f2empn(UPDATED_F_2_EMPN)
            .f1sj(UPDATED_F_1_SJ)
            .f2sj(UPDATED_F_2_SJ);

        restDRkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDRk.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDRk))
            )
            .andExpect(status().isOk());

        // Validate the DRk in the database
        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeUpdate);
        DRk testDRk = dRkList.get(dRkList.size() - 1);
        assertThat(testDRk.getRkdate()).isEqualTo(UPDATED_RKDATE);
        assertThat(testDRk.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testDRk.getRklx()).isEqualTo(UPDATED_RKLX);
        assertThat(testDRk.getRkbillno()).isEqualTo(UPDATED_RKBILLNO);
        assertThat(testDRk.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testDRk.getDeptname()).isEqualTo(UPDATED_DEPTNAME);
        assertThat(testDRk.getJbr()).isEqualTo(UPDATED_JBR);
        assertThat(testDRk.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testDRk.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testDRk.getLrdate()).isEqualTo(UPDATED_LRDATE);
        assertThat(testDRk.getSpbm()).isEqualTo(UPDATED_SPBM);
        assertThat(testDRk.getSpmc()).isEqualTo(UPDATED_SPMC);
        assertThat(testDRk.getGgxh()).isEqualTo(UPDATED_GGXH);
        assertThat(testDRk.getDw()).isEqualTo(UPDATED_DW);
        assertThat(testDRk.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testDRk.getSl()).isEqualByComparingTo(UPDATED_SL);
        assertThat(testDRk.getJe()).isEqualByComparingTo(UPDATED_JE);
        assertThat(testDRk.getSign()).isEqualTo(UPDATED_SIGN);
        assertThat(testDRk.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testDRk.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testDRk.getf1()).isEqualTo(UPDATED_F_1);
        assertThat(testDRk.getf2()).isEqualTo(UPDATED_F_2);
        assertThat(testDRk.getf1empn()).isEqualTo(UPDATED_F_1_EMPN);
        assertThat(testDRk.getf2empn()).isEqualTo(UPDATED_F_2_EMPN);
        assertThat(testDRk.getf1sj()).isEqualTo(UPDATED_F_1_SJ);
        assertThat(testDRk.getf2sj()).isEqualTo(UPDATED_F_2_SJ);
    }

    @Test
    @Transactional
    void patchNonExistingDRk() throws Exception {
        int databaseSizeBeforeUpdate = dRkRepository.findAll().size();
        dRk.setId(count.incrementAndGet());

        // Create the DRk
        DRkDTO dRkDTO = dRkMapper.toDto(dRk);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDRkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dRkDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dRkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DRk in the database
        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DRk in Elasticsearch
        verify(mockDRkSearchRepository, times(0)).save(dRk);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDRk() throws Exception {
        int databaseSizeBeforeUpdate = dRkRepository.findAll().size();
        dRk.setId(count.incrementAndGet());

        // Create the DRk
        DRkDTO dRkDTO = dRkMapper.toDto(dRk);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDRkMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dRkDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DRk in the database
        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DRk in Elasticsearch
        verify(mockDRkSearchRepository, times(0)).save(dRk);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDRk() throws Exception {
        int databaseSizeBeforeUpdate = dRkRepository.findAll().size();
        dRk.setId(count.incrementAndGet());

        // Create the DRk
        DRkDTO dRkDTO = dRkMapper.toDto(dRk);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDRkMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dRkDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DRk in the database
        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DRk in Elasticsearch
        verify(mockDRkSearchRepository, times(0)).save(dRk);
    }

    @Test
    @Transactional
    void deleteDRk() throws Exception {
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);

        int databaseSizeBeforeDelete = dRkRepository.findAll().size();

        // Delete the dRk
        restDRkMockMvc.perform(delete(ENTITY_API_URL_ID, dRk.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DRk> dRkList = dRkRepository.findAll();
        assertThat(dRkList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DRk in Elasticsearch
        verify(mockDRkSearchRepository, times(1)).deleteById(dRk.getId());
    }

    @Test
    @Transactional
    void searchDRk() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dRkRepository.saveAndFlush(dRk);
        when(mockDRkSearchRepository.search(queryStringQuery("id:" + dRk.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dRk), PageRequest.of(0, 1), 1));

        // Search the dRk
        restDRkMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dRk.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dRk.getId().intValue())))
            .andExpect(jsonPath("$.[*].rkdate").value(hasItem(DEFAULT_RKDATE.toString())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].rklx").value(hasItem(DEFAULT_RKLX)))
            .andExpect(jsonPath("$.[*].rkbillno").value(hasItem(DEFAULT_RKBILLNO)))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY.intValue())))
            .andExpect(jsonPath("$.[*].deptname").value(hasItem(DEFAULT_DEPTNAME)))
            .andExpect(jsonPath("$.[*].jbr").value(hasItem(DEFAULT_JBR)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].lrdate").value(hasItem(DEFAULT_LRDATE.toString())))
            .andExpect(jsonPath("$.[*].spbm").value(hasItem(DEFAULT_SPBM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].ggxh").value(hasItem(DEFAULT_GGXH)))
            .andExpect(jsonPath("$.[*].dw").value(hasItem(DEFAULT_DW)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].je").value(hasItem(sameNumber(DEFAULT_JE))))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].f1").value(hasItem(DEFAULT_F_1)))
            .andExpect(jsonPath("$.[*].f2").value(hasItem(DEFAULT_F_2)))
            .andExpect(jsonPath("$.[*].f1empn").value(hasItem(DEFAULT_F_1_EMPN)))
            .andExpect(jsonPath("$.[*].f2empn").value(hasItem(DEFAULT_F_2_EMPN)))
            .andExpect(jsonPath("$.[*].f1sj").value(hasItem(DEFAULT_F_1_SJ.toString())))
            .andExpect(jsonPath("$.[*].f2sj").value(hasItem(DEFAULT_F_2_SJ.toString())));
    }
}
