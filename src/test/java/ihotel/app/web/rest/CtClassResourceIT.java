package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.CtClass;
import ihotel.app.repository.CtClassRepository;
import ihotel.app.repository.search.CtClassSearchRepository;
import ihotel.app.service.criteria.CtClassCriteria;
import ihotel.app.service.dto.CtClassDTO;
import ihotel.app.service.mapper.CtClassMapper;
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
 * Integration tests for the {@link CtClassResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CtClassResourceIT {

    private static final Instant DEFAULT_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final Long DEFAULT_FLAG = 1L;
    private static final Long UPDATED_FLAG = 2L;
    private static final Long SMALLER_FLAG = 1L - 1L;

    private static final String DEFAULT_JBEMPN = "AAAAAAAAAA";
    private static final String UPDATED_JBEMPN = "BBBBBBBBBB";

    private static final Instant DEFAULT_GOTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GOTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_XJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_XJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_XJ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ZP = new BigDecimal(1);
    private static final BigDecimal UPDATED_ZP = new BigDecimal(2);
    private static final BigDecimal SMALLER_ZP = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SK = new BigDecimal(1);
    private static final BigDecimal UPDATED_SK = new BigDecimal(2);
    private static final BigDecimal SMALLER_SK = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HYK = new BigDecimal(1);
    private static final BigDecimal UPDATED_HYK = new BigDecimal(2);
    private static final BigDecimal SMALLER_HYK = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CQ = new BigDecimal(1);
    private static final BigDecimal UPDATED_CQ = new BigDecimal(2);
    private static final BigDecimal SMALLER_CQ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_GZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_GZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_GZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_GFZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_GFZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_GFZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_YQ = new BigDecimal(1);
    private static final BigDecimal UPDATED_YQ = new BigDecimal(2);
    private static final BigDecimal SMALLER_YQ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_YH = new BigDecimal(1);
    private static final BigDecimal UPDATED_YH = new BigDecimal(2);
    private static final BigDecimal SMALLER_YH = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ZZH = new BigDecimal(1);
    private static final BigDecimal UPDATED_ZZH = new BigDecimal(2);
    private static final BigDecimal SMALLER_ZZH = new BigDecimal(1 - 1);

    private static final String DEFAULT_CHECK_SIGN = "AA";
    private static final String UPDATED_CHECK_SIGN = "BB";

    private static final String ENTITY_API_URL = "/api/ct-classes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/ct-classes";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CtClassRepository ctClassRepository;

    @Autowired
    private CtClassMapper ctClassMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CtClassSearchRepositoryMockConfiguration
     */
    @Autowired
    private CtClassSearchRepository mockCtClassSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCtClassMockMvc;

    private CtClass ctClass;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CtClass createEntity(EntityManager em) {
        CtClass ctClass = new CtClass()
            .dt(DEFAULT_DT)
            .empn(DEFAULT_EMPN)
            .flag(DEFAULT_FLAG)
            .jbempn(DEFAULT_JBEMPN)
            .gotime(DEFAULT_GOTIME)
            .xj(DEFAULT_XJ)
            .zp(DEFAULT_ZP)
            .sk(DEFAULT_SK)
            .hyk(DEFAULT_HYK)
            .cq(DEFAULT_CQ)
            .gz(DEFAULT_GZ)
            .gfz(DEFAULT_GFZ)
            .yq(DEFAULT_YQ)
            .yh(DEFAULT_YH)
            .zzh(DEFAULT_ZZH)
            .checkSign(DEFAULT_CHECK_SIGN);
        return ctClass;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CtClass createUpdatedEntity(EntityManager em) {
        CtClass ctClass = new CtClass()
            .dt(UPDATED_DT)
            .empn(UPDATED_EMPN)
            .flag(UPDATED_FLAG)
            .jbempn(UPDATED_JBEMPN)
            .gotime(UPDATED_GOTIME)
            .xj(UPDATED_XJ)
            .zp(UPDATED_ZP)
            .sk(UPDATED_SK)
            .hyk(UPDATED_HYK)
            .cq(UPDATED_CQ)
            .gz(UPDATED_GZ)
            .gfz(UPDATED_GFZ)
            .yq(UPDATED_YQ)
            .yh(UPDATED_YH)
            .zzh(UPDATED_ZZH)
            .checkSign(UPDATED_CHECK_SIGN);
        return ctClass;
    }

    @BeforeEach
    public void initTest() {
        ctClass = createEntity(em);
    }

    @Test
    @Transactional
    void createCtClass() throws Exception {
        int databaseSizeBeforeCreate = ctClassRepository.findAll().size();
        // Create the CtClass
        CtClassDTO ctClassDTO = ctClassMapper.toDto(ctClass);
        restCtClassMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ctClassDTO)))
            .andExpect(status().isCreated());

        // Validate the CtClass in the database
        List<CtClass> ctClassList = ctClassRepository.findAll();
        assertThat(ctClassList).hasSize(databaseSizeBeforeCreate + 1);
        CtClass testCtClass = ctClassList.get(ctClassList.size() - 1);
        assertThat(testCtClass.getDt()).isEqualTo(DEFAULT_DT);
        assertThat(testCtClass.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testCtClass.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testCtClass.getJbempn()).isEqualTo(DEFAULT_JBEMPN);
        assertThat(testCtClass.getGotime()).isEqualTo(DEFAULT_GOTIME);
        assertThat(testCtClass.getXj()).isEqualByComparingTo(DEFAULT_XJ);
        assertThat(testCtClass.getZp()).isEqualByComparingTo(DEFAULT_ZP);
        assertThat(testCtClass.getSk()).isEqualByComparingTo(DEFAULT_SK);
        assertThat(testCtClass.getHyk()).isEqualByComparingTo(DEFAULT_HYK);
        assertThat(testCtClass.getCq()).isEqualByComparingTo(DEFAULT_CQ);
        assertThat(testCtClass.getGz()).isEqualByComparingTo(DEFAULT_GZ);
        assertThat(testCtClass.getGfz()).isEqualByComparingTo(DEFAULT_GFZ);
        assertThat(testCtClass.getYq()).isEqualByComparingTo(DEFAULT_YQ);
        assertThat(testCtClass.getYh()).isEqualByComparingTo(DEFAULT_YH);
        assertThat(testCtClass.getZzh()).isEqualByComparingTo(DEFAULT_ZZH);
        assertThat(testCtClass.getCheckSign()).isEqualTo(DEFAULT_CHECK_SIGN);

        // Validate the CtClass in Elasticsearch
        verify(mockCtClassSearchRepository, times(1)).save(testCtClass);
    }

    @Test
    @Transactional
    void createCtClassWithExistingId() throws Exception {
        // Create the CtClass with an existing ID
        ctClass.setId(1L);
        CtClassDTO ctClassDTO = ctClassMapper.toDto(ctClass);

        int databaseSizeBeforeCreate = ctClassRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCtClassMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ctClassDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CtClass in the database
        List<CtClass> ctClassList = ctClassRepository.findAll();
        assertThat(ctClassList).hasSize(databaseSizeBeforeCreate);

        // Validate the CtClass in Elasticsearch
        verify(mockCtClassSearchRepository, times(0)).save(ctClass);
    }

    @Test
    @Transactional
    void checkDtIsRequired() throws Exception {
        int databaseSizeBeforeTest = ctClassRepository.findAll().size();
        // set the field null
        ctClass.setDt(null);

        // Create the CtClass, which fails.
        CtClassDTO ctClassDTO = ctClassMapper.toDto(ctClass);

        restCtClassMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ctClassDTO)))
            .andExpect(status().isBadRequest());

        List<CtClass> ctClassList = ctClassRepository.findAll();
        assertThat(ctClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmpnIsRequired() throws Exception {
        int databaseSizeBeforeTest = ctClassRepository.findAll().size();
        // set the field null
        ctClass.setEmpn(null);

        // Create the CtClass, which fails.
        CtClassDTO ctClassDTO = ctClassMapper.toDto(ctClass);

        restCtClassMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ctClassDTO)))
            .andExpect(status().isBadRequest());

        List<CtClass> ctClassList = ctClassRepository.findAll();
        assertThat(ctClassList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCtClasses() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList
        restCtClassMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ctClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].dt").value(hasItem(DEFAULT_DT.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].jbempn").value(hasItem(DEFAULT_JBEMPN)))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].xj").value(hasItem(sameNumber(DEFAULT_XJ))))
            .andExpect(jsonPath("$.[*].zp").value(hasItem(sameNumber(DEFAULT_ZP))))
            .andExpect(jsonPath("$.[*].sk").value(hasItem(sameNumber(DEFAULT_SK))))
            .andExpect(jsonPath("$.[*].hyk").value(hasItem(sameNumber(DEFAULT_HYK))))
            .andExpect(jsonPath("$.[*].cq").value(hasItem(sameNumber(DEFAULT_CQ))))
            .andExpect(jsonPath("$.[*].gz").value(hasItem(sameNumber(DEFAULT_GZ))))
            .andExpect(jsonPath("$.[*].gfz").value(hasItem(sameNumber(DEFAULT_GFZ))))
            .andExpect(jsonPath("$.[*].yq").value(hasItem(sameNumber(DEFAULT_YQ))))
            .andExpect(jsonPath("$.[*].yh").value(hasItem(sameNumber(DEFAULT_YH))))
            .andExpect(jsonPath("$.[*].zzh").value(hasItem(sameNumber(DEFAULT_ZZH))))
            .andExpect(jsonPath("$.[*].checkSign").value(hasItem(DEFAULT_CHECK_SIGN)));
    }

    @Test
    @Transactional
    void getCtClass() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get the ctClass
        restCtClassMockMvc
            .perform(get(ENTITY_API_URL_ID, ctClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ctClass.getId().intValue()))
            .andExpect(jsonPath("$.dt").value(DEFAULT_DT.toString()))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG.intValue()))
            .andExpect(jsonPath("$.jbempn").value(DEFAULT_JBEMPN))
            .andExpect(jsonPath("$.gotime").value(DEFAULT_GOTIME.toString()))
            .andExpect(jsonPath("$.xj").value(sameNumber(DEFAULT_XJ)))
            .andExpect(jsonPath("$.zp").value(sameNumber(DEFAULT_ZP)))
            .andExpect(jsonPath("$.sk").value(sameNumber(DEFAULT_SK)))
            .andExpect(jsonPath("$.hyk").value(sameNumber(DEFAULT_HYK)))
            .andExpect(jsonPath("$.cq").value(sameNumber(DEFAULT_CQ)))
            .andExpect(jsonPath("$.gz").value(sameNumber(DEFAULT_GZ)))
            .andExpect(jsonPath("$.gfz").value(sameNumber(DEFAULT_GFZ)))
            .andExpect(jsonPath("$.yq").value(sameNumber(DEFAULT_YQ)))
            .andExpect(jsonPath("$.yh").value(sameNumber(DEFAULT_YH)))
            .andExpect(jsonPath("$.zzh").value(sameNumber(DEFAULT_ZZH)))
            .andExpect(jsonPath("$.checkSign").value(DEFAULT_CHECK_SIGN));
    }

    @Test
    @Transactional
    void getCtClassesByIdFiltering() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        Long id = ctClass.getId();

        defaultCtClassShouldBeFound("id.equals=" + id);
        defaultCtClassShouldNotBeFound("id.notEquals=" + id);

        defaultCtClassShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCtClassShouldNotBeFound("id.greaterThan=" + id);

        defaultCtClassShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCtClassShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCtClassesByDtIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where dt equals to DEFAULT_DT
        defaultCtClassShouldBeFound("dt.equals=" + DEFAULT_DT);

        // Get all the ctClassList where dt equals to UPDATED_DT
        defaultCtClassShouldNotBeFound("dt.equals=" + UPDATED_DT);
    }

    @Test
    @Transactional
    void getAllCtClassesByDtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where dt not equals to DEFAULT_DT
        defaultCtClassShouldNotBeFound("dt.notEquals=" + DEFAULT_DT);

        // Get all the ctClassList where dt not equals to UPDATED_DT
        defaultCtClassShouldBeFound("dt.notEquals=" + UPDATED_DT);
    }

    @Test
    @Transactional
    void getAllCtClassesByDtIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where dt in DEFAULT_DT or UPDATED_DT
        defaultCtClassShouldBeFound("dt.in=" + DEFAULT_DT + "," + UPDATED_DT);

        // Get all the ctClassList where dt equals to UPDATED_DT
        defaultCtClassShouldNotBeFound("dt.in=" + UPDATED_DT);
    }

    @Test
    @Transactional
    void getAllCtClassesByDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where dt is not null
        defaultCtClassShouldBeFound("dt.specified=true");

        // Get all the ctClassList where dt is null
        defaultCtClassShouldNotBeFound("dt.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where empn equals to DEFAULT_EMPN
        defaultCtClassShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the ctClassList where empn equals to UPDATED_EMPN
        defaultCtClassShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCtClassesByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where empn not equals to DEFAULT_EMPN
        defaultCtClassShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the ctClassList where empn not equals to UPDATED_EMPN
        defaultCtClassShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCtClassesByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultCtClassShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the ctClassList where empn equals to UPDATED_EMPN
        defaultCtClassShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCtClassesByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where empn is not null
        defaultCtClassShouldBeFound("empn.specified=true");

        // Get all the ctClassList where empn is null
        defaultCtClassShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByEmpnContainsSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where empn contains DEFAULT_EMPN
        defaultCtClassShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the ctClassList where empn contains UPDATED_EMPN
        defaultCtClassShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCtClassesByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where empn does not contain DEFAULT_EMPN
        defaultCtClassShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the ctClassList where empn does not contain UPDATED_EMPN
        defaultCtClassShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCtClassesByFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where flag equals to DEFAULT_FLAG
        defaultCtClassShouldBeFound("flag.equals=" + DEFAULT_FLAG);

        // Get all the ctClassList where flag equals to UPDATED_FLAG
        defaultCtClassShouldNotBeFound("flag.equals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCtClassesByFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where flag not equals to DEFAULT_FLAG
        defaultCtClassShouldNotBeFound("flag.notEquals=" + DEFAULT_FLAG);

        // Get all the ctClassList where flag not equals to UPDATED_FLAG
        defaultCtClassShouldBeFound("flag.notEquals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCtClassesByFlagIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where flag in DEFAULT_FLAG or UPDATED_FLAG
        defaultCtClassShouldBeFound("flag.in=" + DEFAULT_FLAG + "," + UPDATED_FLAG);

        // Get all the ctClassList where flag equals to UPDATED_FLAG
        defaultCtClassShouldNotBeFound("flag.in=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCtClassesByFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where flag is not null
        defaultCtClassShouldBeFound("flag.specified=true");

        // Get all the ctClassList where flag is null
        defaultCtClassShouldNotBeFound("flag.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByFlagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where flag is greater than or equal to DEFAULT_FLAG
        defaultCtClassShouldBeFound("flag.greaterThanOrEqual=" + DEFAULT_FLAG);

        // Get all the ctClassList where flag is greater than or equal to UPDATED_FLAG
        defaultCtClassShouldNotBeFound("flag.greaterThanOrEqual=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCtClassesByFlagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where flag is less than or equal to DEFAULT_FLAG
        defaultCtClassShouldBeFound("flag.lessThanOrEqual=" + DEFAULT_FLAG);

        // Get all the ctClassList where flag is less than or equal to SMALLER_FLAG
        defaultCtClassShouldNotBeFound("flag.lessThanOrEqual=" + SMALLER_FLAG);
    }

    @Test
    @Transactional
    void getAllCtClassesByFlagIsLessThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where flag is less than DEFAULT_FLAG
        defaultCtClassShouldNotBeFound("flag.lessThan=" + DEFAULT_FLAG);

        // Get all the ctClassList where flag is less than UPDATED_FLAG
        defaultCtClassShouldBeFound("flag.lessThan=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCtClassesByFlagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where flag is greater than DEFAULT_FLAG
        defaultCtClassShouldNotBeFound("flag.greaterThan=" + DEFAULT_FLAG);

        // Get all the ctClassList where flag is greater than SMALLER_FLAG
        defaultCtClassShouldBeFound("flag.greaterThan=" + SMALLER_FLAG);
    }

    @Test
    @Transactional
    void getAllCtClassesByJbempnIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where jbempn equals to DEFAULT_JBEMPN
        defaultCtClassShouldBeFound("jbempn.equals=" + DEFAULT_JBEMPN);

        // Get all the ctClassList where jbempn equals to UPDATED_JBEMPN
        defaultCtClassShouldNotBeFound("jbempn.equals=" + UPDATED_JBEMPN);
    }

    @Test
    @Transactional
    void getAllCtClassesByJbempnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where jbempn not equals to DEFAULT_JBEMPN
        defaultCtClassShouldNotBeFound("jbempn.notEquals=" + DEFAULT_JBEMPN);

        // Get all the ctClassList where jbempn not equals to UPDATED_JBEMPN
        defaultCtClassShouldBeFound("jbempn.notEquals=" + UPDATED_JBEMPN);
    }

    @Test
    @Transactional
    void getAllCtClassesByJbempnIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where jbempn in DEFAULT_JBEMPN or UPDATED_JBEMPN
        defaultCtClassShouldBeFound("jbempn.in=" + DEFAULT_JBEMPN + "," + UPDATED_JBEMPN);

        // Get all the ctClassList where jbempn equals to UPDATED_JBEMPN
        defaultCtClassShouldNotBeFound("jbempn.in=" + UPDATED_JBEMPN);
    }

    @Test
    @Transactional
    void getAllCtClassesByJbempnIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where jbempn is not null
        defaultCtClassShouldBeFound("jbempn.specified=true");

        // Get all the ctClassList where jbempn is null
        defaultCtClassShouldNotBeFound("jbempn.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByJbempnContainsSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where jbempn contains DEFAULT_JBEMPN
        defaultCtClassShouldBeFound("jbempn.contains=" + DEFAULT_JBEMPN);

        // Get all the ctClassList where jbempn contains UPDATED_JBEMPN
        defaultCtClassShouldNotBeFound("jbempn.contains=" + UPDATED_JBEMPN);
    }

    @Test
    @Transactional
    void getAllCtClassesByJbempnNotContainsSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where jbempn does not contain DEFAULT_JBEMPN
        defaultCtClassShouldNotBeFound("jbempn.doesNotContain=" + DEFAULT_JBEMPN);

        // Get all the ctClassList where jbempn does not contain UPDATED_JBEMPN
        defaultCtClassShouldBeFound("jbempn.doesNotContain=" + UPDATED_JBEMPN);
    }

    @Test
    @Transactional
    void getAllCtClassesByGotimeIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gotime equals to DEFAULT_GOTIME
        defaultCtClassShouldBeFound("gotime.equals=" + DEFAULT_GOTIME);

        // Get all the ctClassList where gotime equals to UPDATED_GOTIME
        defaultCtClassShouldNotBeFound("gotime.equals=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCtClassesByGotimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gotime not equals to DEFAULT_GOTIME
        defaultCtClassShouldNotBeFound("gotime.notEquals=" + DEFAULT_GOTIME);

        // Get all the ctClassList where gotime not equals to UPDATED_GOTIME
        defaultCtClassShouldBeFound("gotime.notEquals=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCtClassesByGotimeIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gotime in DEFAULT_GOTIME or UPDATED_GOTIME
        defaultCtClassShouldBeFound("gotime.in=" + DEFAULT_GOTIME + "," + UPDATED_GOTIME);

        // Get all the ctClassList where gotime equals to UPDATED_GOTIME
        defaultCtClassShouldNotBeFound("gotime.in=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCtClassesByGotimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gotime is not null
        defaultCtClassShouldBeFound("gotime.specified=true");

        // Get all the ctClassList where gotime is null
        defaultCtClassShouldNotBeFound("gotime.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByXjIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where xj equals to DEFAULT_XJ
        defaultCtClassShouldBeFound("xj.equals=" + DEFAULT_XJ);

        // Get all the ctClassList where xj equals to UPDATED_XJ
        defaultCtClassShouldNotBeFound("xj.equals=" + UPDATED_XJ);
    }

    @Test
    @Transactional
    void getAllCtClassesByXjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where xj not equals to DEFAULT_XJ
        defaultCtClassShouldNotBeFound("xj.notEquals=" + DEFAULT_XJ);

        // Get all the ctClassList where xj not equals to UPDATED_XJ
        defaultCtClassShouldBeFound("xj.notEquals=" + UPDATED_XJ);
    }

    @Test
    @Transactional
    void getAllCtClassesByXjIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where xj in DEFAULT_XJ or UPDATED_XJ
        defaultCtClassShouldBeFound("xj.in=" + DEFAULT_XJ + "," + UPDATED_XJ);

        // Get all the ctClassList where xj equals to UPDATED_XJ
        defaultCtClassShouldNotBeFound("xj.in=" + UPDATED_XJ);
    }

    @Test
    @Transactional
    void getAllCtClassesByXjIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where xj is not null
        defaultCtClassShouldBeFound("xj.specified=true");

        // Get all the ctClassList where xj is null
        defaultCtClassShouldNotBeFound("xj.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByXjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where xj is greater than or equal to DEFAULT_XJ
        defaultCtClassShouldBeFound("xj.greaterThanOrEqual=" + DEFAULT_XJ);

        // Get all the ctClassList where xj is greater than or equal to UPDATED_XJ
        defaultCtClassShouldNotBeFound("xj.greaterThanOrEqual=" + UPDATED_XJ);
    }

    @Test
    @Transactional
    void getAllCtClassesByXjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where xj is less than or equal to DEFAULT_XJ
        defaultCtClassShouldBeFound("xj.lessThanOrEqual=" + DEFAULT_XJ);

        // Get all the ctClassList where xj is less than or equal to SMALLER_XJ
        defaultCtClassShouldNotBeFound("xj.lessThanOrEqual=" + SMALLER_XJ);
    }

    @Test
    @Transactional
    void getAllCtClassesByXjIsLessThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where xj is less than DEFAULT_XJ
        defaultCtClassShouldNotBeFound("xj.lessThan=" + DEFAULT_XJ);

        // Get all the ctClassList where xj is less than UPDATED_XJ
        defaultCtClassShouldBeFound("xj.lessThan=" + UPDATED_XJ);
    }

    @Test
    @Transactional
    void getAllCtClassesByXjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where xj is greater than DEFAULT_XJ
        defaultCtClassShouldNotBeFound("xj.greaterThan=" + DEFAULT_XJ);

        // Get all the ctClassList where xj is greater than SMALLER_XJ
        defaultCtClassShouldBeFound("xj.greaterThan=" + SMALLER_XJ);
    }

    @Test
    @Transactional
    void getAllCtClassesByZpIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zp equals to DEFAULT_ZP
        defaultCtClassShouldBeFound("zp.equals=" + DEFAULT_ZP);

        // Get all the ctClassList where zp equals to UPDATED_ZP
        defaultCtClassShouldNotBeFound("zp.equals=" + UPDATED_ZP);
    }

    @Test
    @Transactional
    void getAllCtClassesByZpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zp not equals to DEFAULT_ZP
        defaultCtClassShouldNotBeFound("zp.notEquals=" + DEFAULT_ZP);

        // Get all the ctClassList where zp not equals to UPDATED_ZP
        defaultCtClassShouldBeFound("zp.notEquals=" + UPDATED_ZP);
    }

    @Test
    @Transactional
    void getAllCtClassesByZpIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zp in DEFAULT_ZP or UPDATED_ZP
        defaultCtClassShouldBeFound("zp.in=" + DEFAULT_ZP + "," + UPDATED_ZP);

        // Get all the ctClassList where zp equals to UPDATED_ZP
        defaultCtClassShouldNotBeFound("zp.in=" + UPDATED_ZP);
    }

    @Test
    @Transactional
    void getAllCtClassesByZpIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zp is not null
        defaultCtClassShouldBeFound("zp.specified=true");

        // Get all the ctClassList where zp is null
        defaultCtClassShouldNotBeFound("zp.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByZpIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zp is greater than or equal to DEFAULT_ZP
        defaultCtClassShouldBeFound("zp.greaterThanOrEqual=" + DEFAULT_ZP);

        // Get all the ctClassList where zp is greater than or equal to UPDATED_ZP
        defaultCtClassShouldNotBeFound("zp.greaterThanOrEqual=" + UPDATED_ZP);
    }

    @Test
    @Transactional
    void getAllCtClassesByZpIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zp is less than or equal to DEFAULT_ZP
        defaultCtClassShouldBeFound("zp.lessThanOrEqual=" + DEFAULT_ZP);

        // Get all the ctClassList where zp is less than or equal to SMALLER_ZP
        defaultCtClassShouldNotBeFound("zp.lessThanOrEqual=" + SMALLER_ZP);
    }

    @Test
    @Transactional
    void getAllCtClassesByZpIsLessThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zp is less than DEFAULT_ZP
        defaultCtClassShouldNotBeFound("zp.lessThan=" + DEFAULT_ZP);

        // Get all the ctClassList where zp is less than UPDATED_ZP
        defaultCtClassShouldBeFound("zp.lessThan=" + UPDATED_ZP);
    }

    @Test
    @Transactional
    void getAllCtClassesByZpIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zp is greater than DEFAULT_ZP
        defaultCtClassShouldNotBeFound("zp.greaterThan=" + DEFAULT_ZP);

        // Get all the ctClassList where zp is greater than SMALLER_ZP
        defaultCtClassShouldBeFound("zp.greaterThan=" + SMALLER_ZP);
    }

    @Test
    @Transactional
    void getAllCtClassesBySkIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where sk equals to DEFAULT_SK
        defaultCtClassShouldBeFound("sk.equals=" + DEFAULT_SK);

        // Get all the ctClassList where sk equals to UPDATED_SK
        defaultCtClassShouldNotBeFound("sk.equals=" + UPDATED_SK);
    }

    @Test
    @Transactional
    void getAllCtClassesBySkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where sk not equals to DEFAULT_SK
        defaultCtClassShouldNotBeFound("sk.notEquals=" + DEFAULT_SK);

        // Get all the ctClassList where sk not equals to UPDATED_SK
        defaultCtClassShouldBeFound("sk.notEquals=" + UPDATED_SK);
    }

    @Test
    @Transactional
    void getAllCtClassesBySkIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where sk in DEFAULT_SK or UPDATED_SK
        defaultCtClassShouldBeFound("sk.in=" + DEFAULT_SK + "," + UPDATED_SK);

        // Get all the ctClassList where sk equals to UPDATED_SK
        defaultCtClassShouldNotBeFound("sk.in=" + UPDATED_SK);
    }

    @Test
    @Transactional
    void getAllCtClassesBySkIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where sk is not null
        defaultCtClassShouldBeFound("sk.specified=true");

        // Get all the ctClassList where sk is null
        defaultCtClassShouldNotBeFound("sk.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesBySkIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where sk is greater than or equal to DEFAULT_SK
        defaultCtClassShouldBeFound("sk.greaterThanOrEqual=" + DEFAULT_SK);

        // Get all the ctClassList where sk is greater than or equal to UPDATED_SK
        defaultCtClassShouldNotBeFound("sk.greaterThanOrEqual=" + UPDATED_SK);
    }

    @Test
    @Transactional
    void getAllCtClassesBySkIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where sk is less than or equal to DEFAULT_SK
        defaultCtClassShouldBeFound("sk.lessThanOrEqual=" + DEFAULT_SK);

        // Get all the ctClassList where sk is less than or equal to SMALLER_SK
        defaultCtClassShouldNotBeFound("sk.lessThanOrEqual=" + SMALLER_SK);
    }

    @Test
    @Transactional
    void getAllCtClassesBySkIsLessThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where sk is less than DEFAULT_SK
        defaultCtClassShouldNotBeFound("sk.lessThan=" + DEFAULT_SK);

        // Get all the ctClassList where sk is less than UPDATED_SK
        defaultCtClassShouldBeFound("sk.lessThan=" + UPDATED_SK);
    }

    @Test
    @Transactional
    void getAllCtClassesBySkIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where sk is greater than DEFAULT_SK
        defaultCtClassShouldNotBeFound("sk.greaterThan=" + DEFAULT_SK);

        // Get all the ctClassList where sk is greater than SMALLER_SK
        defaultCtClassShouldBeFound("sk.greaterThan=" + SMALLER_SK);
    }

    @Test
    @Transactional
    void getAllCtClassesByHykIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where hyk equals to DEFAULT_HYK
        defaultCtClassShouldBeFound("hyk.equals=" + DEFAULT_HYK);

        // Get all the ctClassList where hyk equals to UPDATED_HYK
        defaultCtClassShouldNotBeFound("hyk.equals=" + UPDATED_HYK);
    }

    @Test
    @Transactional
    void getAllCtClassesByHykIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where hyk not equals to DEFAULT_HYK
        defaultCtClassShouldNotBeFound("hyk.notEquals=" + DEFAULT_HYK);

        // Get all the ctClassList where hyk not equals to UPDATED_HYK
        defaultCtClassShouldBeFound("hyk.notEquals=" + UPDATED_HYK);
    }

    @Test
    @Transactional
    void getAllCtClassesByHykIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where hyk in DEFAULT_HYK or UPDATED_HYK
        defaultCtClassShouldBeFound("hyk.in=" + DEFAULT_HYK + "," + UPDATED_HYK);

        // Get all the ctClassList where hyk equals to UPDATED_HYK
        defaultCtClassShouldNotBeFound("hyk.in=" + UPDATED_HYK);
    }

    @Test
    @Transactional
    void getAllCtClassesByHykIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where hyk is not null
        defaultCtClassShouldBeFound("hyk.specified=true");

        // Get all the ctClassList where hyk is null
        defaultCtClassShouldNotBeFound("hyk.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByHykIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where hyk is greater than or equal to DEFAULT_HYK
        defaultCtClassShouldBeFound("hyk.greaterThanOrEqual=" + DEFAULT_HYK);

        // Get all the ctClassList where hyk is greater than or equal to UPDATED_HYK
        defaultCtClassShouldNotBeFound("hyk.greaterThanOrEqual=" + UPDATED_HYK);
    }

    @Test
    @Transactional
    void getAllCtClassesByHykIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where hyk is less than or equal to DEFAULT_HYK
        defaultCtClassShouldBeFound("hyk.lessThanOrEqual=" + DEFAULT_HYK);

        // Get all the ctClassList where hyk is less than or equal to SMALLER_HYK
        defaultCtClassShouldNotBeFound("hyk.lessThanOrEqual=" + SMALLER_HYK);
    }

    @Test
    @Transactional
    void getAllCtClassesByHykIsLessThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where hyk is less than DEFAULT_HYK
        defaultCtClassShouldNotBeFound("hyk.lessThan=" + DEFAULT_HYK);

        // Get all the ctClassList where hyk is less than UPDATED_HYK
        defaultCtClassShouldBeFound("hyk.lessThan=" + UPDATED_HYK);
    }

    @Test
    @Transactional
    void getAllCtClassesByHykIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where hyk is greater than DEFAULT_HYK
        defaultCtClassShouldNotBeFound("hyk.greaterThan=" + DEFAULT_HYK);

        // Get all the ctClassList where hyk is greater than SMALLER_HYK
        defaultCtClassShouldBeFound("hyk.greaterThan=" + SMALLER_HYK);
    }

    @Test
    @Transactional
    void getAllCtClassesByCqIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where cq equals to DEFAULT_CQ
        defaultCtClassShouldBeFound("cq.equals=" + DEFAULT_CQ);

        // Get all the ctClassList where cq equals to UPDATED_CQ
        defaultCtClassShouldNotBeFound("cq.equals=" + UPDATED_CQ);
    }

    @Test
    @Transactional
    void getAllCtClassesByCqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where cq not equals to DEFAULT_CQ
        defaultCtClassShouldNotBeFound("cq.notEquals=" + DEFAULT_CQ);

        // Get all the ctClassList where cq not equals to UPDATED_CQ
        defaultCtClassShouldBeFound("cq.notEquals=" + UPDATED_CQ);
    }

    @Test
    @Transactional
    void getAllCtClassesByCqIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where cq in DEFAULT_CQ or UPDATED_CQ
        defaultCtClassShouldBeFound("cq.in=" + DEFAULT_CQ + "," + UPDATED_CQ);

        // Get all the ctClassList where cq equals to UPDATED_CQ
        defaultCtClassShouldNotBeFound("cq.in=" + UPDATED_CQ);
    }

    @Test
    @Transactional
    void getAllCtClassesByCqIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where cq is not null
        defaultCtClassShouldBeFound("cq.specified=true");

        // Get all the ctClassList where cq is null
        defaultCtClassShouldNotBeFound("cq.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByCqIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where cq is greater than or equal to DEFAULT_CQ
        defaultCtClassShouldBeFound("cq.greaterThanOrEqual=" + DEFAULT_CQ);

        // Get all the ctClassList where cq is greater than or equal to UPDATED_CQ
        defaultCtClassShouldNotBeFound("cq.greaterThanOrEqual=" + UPDATED_CQ);
    }

    @Test
    @Transactional
    void getAllCtClassesByCqIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where cq is less than or equal to DEFAULT_CQ
        defaultCtClassShouldBeFound("cq.lessThanOrEqual=" + DEFAULT_CQ);

        // Get all the ctClassList where cq is less than or equal to SMALLER_CQ
        defaultCtClassShouldNotBeFound("cq.lessThanOrEqual=" + SMALLER_CQ);
    }

    @Test
    @Transactional
    void getAllCtClassesByCqIsLessThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where cq is less than DEFAULT_CQ
        defaultCtClassShouldNotBeFound("cq.lessThan=" + DEFAULT_CQ);

        // Get all the ctClassList where cq is less than UPDATED_CQ
        defaultCtClassShouldBeFound("cq.lessThan=" + UPDATED_CQ);
    }

    @Test
    @Transactional
    void getAllCtClassesByCqIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where cq is greater than DEFAULT_CQ
        defaultCtClassShouldNotBeFound("cq.greaterThan=" + DEFAULT_CQ);

        // Get all the ctClassList where cq is greater than SMALLER_CQ
        defaultCtClassShouldBeFound("cq.greaterThan=" + SMALLER_CQ);
    }

    @Test
    @Transactional
    void getAllCtClassesByGzIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gz equals to DEFAULT_GZ
        defaultCtClassShouldBeFound("gz.equals=" + DEFAULT_GZ);

        // Get all the ctClassList where gz equals to UPDATED_GZ
        defaultCtClassShouldNotBeFound("gz.equals=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllCtClassesByGzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gz not equals to DEFAULT_GZ
        defaultCtClassShouldNotBeFound("gz.notEquals=" + DEFAULT_GZ);

        // Get all the ctClassList where gz not equals to UPDATED_GZ
        defaultCtClassShouldBeFound("gz.notEquals=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllCtClassesByGzIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gz in DEFAULT_GZ or UPDATED_GZ
        defaultCtClassShouldBeFound("gz.in=" + DEFAULT_GZ + "," + UPDATED_GZ);

        // Get all the ctClassList where gz equals to UPDATED_GZ
        defaultCtClassShouldNotBeFound("gz.in=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllCtClassesByGzIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gz is not null
        defaultCtClassShouldBeFound("gz.specified=true");

        // Get all the ctClassList where gz is null
        defaultCtClassShouldNotBeFound("gz.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByGzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gz is greater than or equal to DEFAULT_GZ
        defaultCtClassShouldBeFound("gz.greaterThanOrEqual=" + DEFAULT_GZ);

        // Get all the ctClassList where gz is greater than or equal to UPDATED_GZ
        defaultCtClassShouldNotBeFound("gz.greaterThanOrEqual=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllCtClassesByGzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gz is less than or equal to DEFAULT_GZ
        defaultCtClassShouldBeFound("gz.lessThanOrEqual=" + DEFAULT_GZ);

        // Get all the ctClassList where gz is less than or equal to SMALLER_GZ
        defaultCtClassShouldNotBeFound("gz.lessThanOrEqual=" + SMALLER_GZ);
    }

    @Test
    @Transactional
    void getAllCtClassesByGzIsLessThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gz is less than DEFAULT_GZ
        defaultCtClassShouldNotBeFound("gz.lessThan=" + DEFAULT_GZ);

        // Get all the ctClassList where gz is less than UPDATED_GZ
        defaultCtClassShouldBeFound("gz.lessThan=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllCtClassesByGzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gz is greater than DEFAULT_GZ
        defaultCtClassShouldNotBeFound("gz.greaterThan=" + DEFAULT_GZ);

        // Get all the ctClassList where gz is greater than SMALLER_GZ
        defaultCtClassShouldBeFound("gz.greaterThan=" + SMALLER_GZ);
    }

    @Test
    @Transactional
    void getAllCtClassesByGfzIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gfz equals to DEFAULT_GFZ
        defaultCtClassShouldBeFound("gfz.equals=" + DEFAULT_GFZ);

        // Get all the ctClassList where gfz equals to UPDATED_GFZ
        defaultCtClassShouldNotBeFound("gfz.equals=" + UPDATED_GFZ);
    }

    @Test
    @Transactional
    void getAllCtClassesByGfzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gfz not equals to DEFAULT_GFZ
        defaultCtClassShouldNotBeFound("gfz.notEquals=" + DEFAULT_GFZ);

        // Get all the ctClassList where gfz not equals to UPDATED_GFZ
        defaultCtClassShouldBeFound("gfz.notEquals=" + UPDATED_GFZ);
    }

    @Test
    @Transactional
    void getAllCtClassesByGfzIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gfz in DEFAULT_GFZ or UPDATED_GFZ
        defaultCtClassShouldBeFound("gfz.in=" + DEFAULT_GFZ + "," + UPDATED_GFZ);

        // Get all the ctClassList where gfz equals to UPDATED_GFZ
        defaultCtClassShouldNotBeFound("gfz.in=" + UPDATED_GFZ);
    }

    @Test
    @Transactional
    void getAllCtClassesByGfzIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gfz is not null
        defaultCtClassShouldBeFound("gfz.specified=true");

        // Get all the ctClassList where gfz is null
        defaultCtClassShouldNotBeFound("gfz.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByGfzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gfz is greater than or equal to DEFAULT_GFZ
        defaultCtClassShouldBeFound("gfz.greaterThanOrEqual=" + DEFAULT_GFZ);

        // Get all the ctClassList where gfz is greater than or equal to UPDATED_GFZ
        defaultCtClassShouldNotBeFound("gfz.greaterThanOrEqual=" + UPDATED_GFZ);
    }

    @Test
    @Transactional
    void getAllCtClassesByGfzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gfz is less than or equal to DEFAULT_GFZ
        defaultCtClassShouldBeFound("gfz.lessThanOrEqual=" + DEFAULT_GFZ);

        // Get all the ctClassList where gfz is less than or equal to SMALLER_GFZ
        defaultCtClassShouldNotBeFound("gfz.lessThanOrEqual=" + SMALLER_GFZ);
    }

    @Test
    @Transactional
    void getAllCtClassesByGfzIsLessThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gfz is less than DEFAULT_GFZ
        defaultCtClassShouldNotBeFound("gfz.lessThan=" + DEFAULT_GFZ);

        // Get all the ctClassList where gfz is less than UPDATED_GFZ
        defaultCtClassShouldBeFound("gfz.lessThan=" + UPDATED_GFZ);
    }

    @Test
    @Transactional
    void getAllCtClassesByGfzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where gfz is greater than DEFAULT_GFZ
        defaultCtClassShouldNotBeFound("gfz.greaterThan=" + DEFAULT_GFZ);

        // Get all the ctClassList where gfz is greater than SMALLER_GFZ
        defaultCtClassShouldBeFound("gfz.greaterThan=" + SMALLER_GFZ);
    }

    @Test
    @Transactional
    void getAllCtClassesByYqIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yq equals to DEFAULT_YQ
        defaultCtClassShouldBeFound("yq.equals=" + DEFAULT_YQ);

        // Get all the ctClassList where yq equals to UPDATED_YQ
        defaultCtClassShouldNotBeFound("yq.equals=" + UPDATED_YQ);
    }

    @Test
    @Transactional
    void getAllCtClassesByYqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yq not equals to DEFAULT_YQ
        defaultCtClassShouldNotBeFound("yq.notEquals=" + DEFAULT_YQ);

        // Get all the ctClassList where yq not equals to UPDATED_YQ
        defaultCtClassShouldBeFound("yq.notEquals=" + UPDATED_YQ);
    }

    @Test
    @Transactional
    void getAllCtClassesByYqIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yq in DEFAULT_YQ or UPDATED_YQ
        defaultCtClassShouldBeFound("yq.in=" + DEFAULT_YQ + "," + UPDATED_YQ);

        // Get all the ctClassList where yq equals to UPDATED_YQ
        defaultCtClassShouldNotBeFound("yq.in=" + UPDATED_YQ);
    }

    @Test
    @Transactional
    void getAllCtClassesByYqIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yq is not null
        defaultCtClassShouldBeFound("yq.specified=true");

        // Get all the ctClassList where yq is null
        defaultCtClassShouldNotBeFound("yq.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByYqIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yq is greater than or equal to DEFAULT_YQ
        defaultCtClassShouldBeFound("yq.greaterThanOrEqual=" + DEFAULT_YQ);

        // Get all the ctClassList where yq is greater than or equal to UPDATED_YQ
        defaultCtClassShouldNotBeFound("yq.greaterThanOrEqual=" + UPDATED_YQ);
    }

    @Test
    @Transactional
    void getAllCtClassesByYqIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yq is less than or equal to DEFAULT_YQ
        defaultCtClassShouldBeFound("yq.lessThanOrEqual=" + DEFAULT_YQ);

        // Get all the ctClassList where yq is less than or equal to SMALLER_YQ
        defaultCtClassShouldNotBeFound("yq.lessThanOrEqual=" + SMALLER_YQ);
    }

    @Test
    @Transactional
    void getAllCtClassesByYqIsLessThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yq is less than DEFAULT_YQ
        defaultCtClassShouldNotBeFound("yq.lessThan=" + DEFAULT_YQ);

        // Get all the ctClassList where yq is less than UPDATED_YQ
        defaultCtClassShouldBeFound("yq.lessThan=" + UPDATED_YQ);
    }

    @Test
    @Transactional
    void getAllCtClassesByYqIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yq is greater than DEFAULT_YQ
        defaultCtClassShouldNotBeFound("yq.greaterThan=" + DEFAULT_YQ);

        // Get all the ctClassList where yq is greater than SMALLER_YQ
        defaultCtClassShouldBeFound("yq.greaterThan=" + SMALLER_YQ);
    }

    @Test
    @Transactional
    void getAllCtClassesByYhIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yh equals to DEFAULT_YH
        defaultCtClassShouldBeFound("yh.equals=" + DEFAULT_YH);

        // Get all the ctClassList where yh equals to UPDATED_YH
        defaultCtClassShouldNotBeFound("yh.equals=" + UPDATED_YH);
    }

    @Test
    @Transactional
    void getAllCtClassesByYhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yh not equals to DEFAULT_YH
        defaultCtClassShouldNotBeFound("yh.notEquals=" + DEFAULT_YH);

        // Get all the ctClassList where yh not equals to UPDATED_YH
        defaultCtClassShouldBeFound("yh.notEquals=" + UPDATED_YH);
    }

    @Test
    @Transactional
    void getAllCtClassesByYhIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yh in DEFAULT_YH or UPDATED_YH
        defaultCtClassShouldBeFound("yh.in=" + DEFAULT_YH + "," + UPDATED_YH);

        // Get all the ctClassList where yh equals to UPDATED_YH
        defaultCtClassShouldNotBeFound("yh.in=" + UPDATED_YH);
    }

    @Test
    @Transactional
    void getAllCtClassesByYhIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yh is not null
        defaultCtClassShouldBeFound("yh.specified=true");

        // Get all the ctClassList where yh is null
        defaultCtClassShouldNotBeFound("yh.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByYhIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yh is greater than or equal to DEFAULT_YH
        defaultCtClassShouldBeFound("yh.greaterThanOrEqual=" + DEFAULT_YH);

        // Get all the ctClassList where yh is greater than or equal to UPDATED_YH
        defaultCtClassShouldNotBeFound("yh.greaterThanOrEqual=" + UPDATED_YH);
    }

    @Test
    @Transactional
    void getAllCtClassesByYhIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yh is less than or equal to DEFAULT_YH
        defaultCtClassShouldBeFound("yh.lessThanOrEqual=" + DEFAULT_YH);

        // Get all the ctClassList where yh is less than or equal to SMALLER_YH
        defaultCtClassShouldNotBeFound("yh.lessThanOrEqual=" + SMALLER_YH);
    }

    @Test
    @Transactional
    void getAllCtClassesByYhIsLessThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yh is less than DEFAULT_YH
        defaultCtClassShouldNotBeFound("yh.lessThan=" + DEFAULT_YH);

        // Get all the ctClassList where yh is less than UPDATED_YH
        defaultCtClassShouldBeFound("yh.lessThan=" + UPDATED_YH);
    }

    @Test
    @Transactional
    void getAllCtClassesByYhIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where yh is greater than DEFAULT_YH
        defaultCtClassShouldNotBeFound("yh.greaterThan=" + DEFAULT_YH);

        // Get all the ctClassList where yh is greater than SMALLER_YH
        defaultCtClassShouldBeFound("yh.greaterThan=" + SMALLER_YH);
    }

    @Test
    @Transactional
    void getAllCtClassesByZzhIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zzh equals to DEFAULT_ZZH
        defaultCtClassShouldBeFound("zzh.equals=" + DEFAULT_ZZH);

        // Get all the ctClassList where zzh equals to UPDATED_ZZH
        defaultCtClassShouldNotBeFound("zzh.equals=" + UPDATED_ZZH);
    }

    @Test
    @Transactional
    void getAllCtClassesByZzhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zzh not equals to DEFAULT_ZZH
        defaultCtClassShouldNotBeFound("zzh.notEquals=" + DEFAULT_ZZH);

        // Get all the ctClassList where zzh not equals to UPDATED_ZZH
        defaultCtClassShouldBeFound("zzh.notEquals=" + UPDATED_ZZH);
    }

    @Test
    @Transactional
    void getAllCtClassesByZzhIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zzh in DEFAULT_ZZH or UPDATED_ZZH
        defaultCtClassShouldBeFound("zzh.in=" + DEFAULT_ZZH + "," + UPDATED_ZZH);

        // Get all the ctClassList where zzh equals to UPDATED_ZZH
        defaultCtClassShouldNotBeFound("zzh.in=" + UPDATED_ZZH);
    }

    @Test
    @Transactional
    void getAllCtClassesByZzhIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zzh is not null
        defaultCtClassShouldBeFound("zzh.specified=true");

        // Get all the ctClassList where zzh is null
        defaultCtClassShouldNotBeFound("zzh.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByZzhIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zzh is greater than or equal to DEFAULT_ZZH
        defaultCtClassShouldBeFound("zzh.greaterThanOrEqual=" + DEFAULT_ZZH);

        // Get all the ctClassList where zzh is greater than or equal to UPDATED_ZZH
        defaultCtClassShouldNotBeFound("zzh.greaterThanOrEqual=" + UPDATED_ZZH);
    }

    @Test
    @Transactional
    void getAllCtClassesByZzhIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zzh is less than or equal to DEFAULT_ZZH
        defaultCtClassShouldBeFound("zzh.lessThanOrEqual=" + DEFAULT_ZZH);

        // Get all the ctClassList where zzh is less than or equal to SMALLER_ZZH
        defaultCtClassShouldNotBeFound("zzh.lessThanOrEqual=" + SMALLER_ZZH);
    }

    @Test
    @Transactional
    void getAllCtClassesByZzhIsLessThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zzh is less than DEFAULT_ZZH
        defaultCtClassShouldNotBeFound("zzh.lessThan=" + DEFAULT_ZZH);

        // Get all the ctClassList where zzh is less than UPDATED_ZZH
        defaultCtClassShouldBeFound("zzh.lessThan=" + UPDATED_ZZH);
    }

    @Test
    @Transactional
    void getAllCtClassesByZzhIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where zzh is greater than DEFAULT_ZZH
        defaultCtClassShouldNotBeFound("zzh.greaterThan=" + DEFAULT_ZZH);

        // Get all the ctClassList where zzh is greater than SMALLER_ZZH
        defaultCtClassShouldBeFound("zzh.greaterThan=" + SMALLER_ZZH);
    }

    @Test
    @Transactional
    void getAllCtClassesByCheckSignIsEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where checkSign equals to DEFAULT_CHECK_SIGN
        defaultCtClassShouldBeFound("checkSign.equals=" + DEFAULT_CHECK_SIGN);

        // Get all the ctClassList where checkSign equals to UPDATED_CHECK_SIGN
        defaultCtClassShouldNotBeFound("checkSign.equals=" + UPDATED_CHECK_SIGN);
    }

    @Test
    @Transactional
    void getAllCtClassesByCheckSignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where checkSign not equals to DEFAULT_CHECK_SIGN
        defaultCtClassShouldNotBeFound("checkSign.notEquals=" + DEFAULT_CHECK_SIGN);

        // Get all the ctClassList where checkSign not equals to UPDATED_CHECK_SIGN
        defaultCtClassShouldBeFound("checkSign.notEquals=" + UPDATED_CHECK_SIGN);
    }

    @Test
    @Transactional
    void getAllCtClassesByCheckSignIsInShouldWork() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where checkSign in DEFAULT_CHECK_SIGN or UPDATED_CHECK_SIGN
        defaultCtClassShouldBeFound("checkSign.in=" + DEFAULT_CHECK_SIGN + "," + UPDATED_CHECK_SIGN);

        // Get all the ctClassList where checkSign equals to UPDATED_CHECK_SIGN
        defaultCtClassShouldNotBeFound("checkSign.in=" + UPDATED_CHECK_SIGN);
    }

    @Test
    @Transactional
    void getAllCtClassesByCheckSignIsNullOrNotNull() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where checkSign is not null
        defaultCtClassShouldBeFound("checkSign.specified=true");

        // Get all the ctClassList where checkSign is null
        defaultCtClassShouldNotBeFound("checkSign.specified=false");
    }

    @Test
    @Transactional
    void getAllCtClassesByCheckSignContainsSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where checkSign contains DEFAULT_CHECK_SIGN
        defaultCtClassShouldBeFound("checkSign.contains=" + DEFAULT_CHECK_SIGN);

        // Get all the ctClassList where checkSign contains UPDATED_CHECK_SIGN
        defaultCtClassShouldNotBeFound("checkSign.contains=" + UPDATED_CHECK_SIGN);
    }

    @Test
    @Transactional
    void getAllCtClassesByCheckSignNotContainsSomething() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        // Get all the ctClassList where checkSign does not contain DEFAULT_CHECK_SIGN
        defaultCtClassShouldNotBeFound("checkSign.doesNotContain=" + DEFAULT_CHECK_SIGN);

        // Get all the ctClassList where checkSign does not contain UPDATED_CHECK_SIGN
        defaultCtClassShouldBeFound("checkSign.doesNotContain=" + UPDATED_CHECK_SIGN);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCtClassShouldBeFound(String filter) throws Exception {
        restCtClassMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ctClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].dt").value(hasItem(DEFAULT_DT.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].jbempn").value(hasItem(DEFAULT_JBEMPN)))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].xj").value(hasItem(sameNumber(DEFAULT_XJ))))
            .andExpect(jsonPath("$.[*].zp").value(hasItem(sameNumber(DEFAULT_ZP))))
            .andExpect(jsonPath("$.[*].sk").value(hasItem(sameNumber(DEFAULT_SK))))
            .andExpect(jsonPath("$.[*].hyk").value(hasItem(sameNumber(DEFAULT_HYK))))
            .andExpect(jsonPath("$.[*].cq").value(hasItem(sameNumber(DEFAULT_CQ))))
            .andExpect(jsonPath("$.[*].gz").value(hasItem(sameNumber(DEFAULT_GZ))))
            .andExpect(jsonPath("$.[*].gfz").value(hasItem(sameNumber(DEFAULT_GFZ))))
            .andExpect(jsonPath("$.[*].yq").value(hasItem(sameNumber(DEFAULT_YQ))))
            .andExpect(jsonPath("$.[*].yh").value(hasItem(sameNumber(DEFAULT_YH))))
            .andExpect(jsonPath("$.[*].zzh").value(hasItem(sameNumber(DEFAULT_ZZH))))
            .andExpect(jsonPath("$.[*].checkSign").value(hasItem(DEFAULT_CHECK_SIGN)));

        // Check, that the count call also returns 1
        restCtClassMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCtClassShouldNotBeFound(String filter) throws Exception {
        restCtClassMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCtClassMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCtClass() throws Exception {
        // Get the ctClass
        restCtClassMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCtClass() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        int databaseSizeBeforeUpdate = ctClassRepository.findAll().size();

        // Update the ctClass
        CtClass updatedCtClass = ctClassRepository.findById(ctClass.getId()).get();
        // Disconnect from session so that the updates on updatedCtClass are not directly saved in db
        em.detach(updatedCtClass);
        updatedCtClass
            .dt(UPDATED_DT)
            .empn(UPDATED_EMPN)
            .flag(UPDATED_FLAG)
            .jbempn(UPDATED_JBEMPN)
            .gotime(UPDATED_GOTIME)
            .xj(UPDATED_XJ)
            .zp(UPDATED_ZP)
            .sk(UPDATED_SK)
            .hyk(UPDATED_HYK)
            .cq(UPDATED_CQ)
            .gz(UPDATED_GZ)
            .gfz(UPDATED_GFZ)
            .yq(UPDATED_YQ)
            .yh(UPDATED_YH)
            .zzh(UPDATED_ZZH)
            .checkSign(UPDATED_CHECK_SIGN);
        CtClassDTO ctClassDTO = ctClassMapper.toDto(updatedCtClass);

        restCtClassMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ctClassDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ctClassDTO))
            )
            .andExpect(status().isOk());

        // Validate the CtClass in the database
        List<CtClass> ctClassList = ctClassRepository.findAll();
        assertThat(ctClassList).hasSize(databaseSizeBeforeUpdate);
        CtClass testCtClass = ctClassList.get(ctClassList.size() - 1);
        assertThat(testCtClass.getDt()).isEqualTo(UPDATED_DT);
        assertThat(testCtClass.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCtClass.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testCtClass.getJbempn()).isEqualTo(UPDATED_JBEMPN);
        assertThat(testCtClass.getGotime()).isEqualTo(UPDATED_GOTIME);
        assertThat(testCtClass.getXj()).isEqualTo(UPDATED_XJ);
        assertThat(testCtClass.getZp()).isEqualTo(UPDATED_ZP);
        assertThat(testCtClass.getSk()).isEqualTo(UPDATED_SK);
        assertThat(testCtClass.getHyk()).isEqualTo(UPDATED_HYK);
        assertThat(testCtClass.getCq()).isEqualTo(UPDATED_CQ);
        assertThat(testCtClass.getGz()).isEqualTo(UPDATED_GZ);
        assertThat(testCtClass.getGfz()).isEqualTo(UPDATED_GFZ);
        assertThat(testCtClass.getYq()).isEqualTo(UPDATED_YQ);
        assertThat(testCtClass.getYh()).isEqualTo(UPDATED_YH);
        assertThat(testCtClass.getZzh()).isEqualTo(UPDATED_ZZH);
        assertThat(testCtClass.getCheckSign()).isEqualTo(UPDATED_CHECK_SIGN);

        // Validate the CtClass in Elasticsearch
        verify(mockCtClassSearchRepository).save(testCtClass);
    }

    @Test
    @Transactional
    void putNonExistingCtClass() throws Exception {
        int databaseSizeBeforeUpdate = ctClassRepository.findAll().size();
        ctClass.setId(count.incrementAndGet());

        // Create the CtClass
        CtClassDTO ctClassDTO = ctClassMapper.toDto(ctClass);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCtClassMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ctClassDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ctClassDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CtClass in the database
        List<CtClass> ctClassList = ctClassRepository.findAll();
        assertThat(ctClassList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CtClass in Elasticsearch
        verify(mockCtClassSearchRepository, times(0)).save(ctClass);
    }

    @Test
    @Transactional
    void putWithIdMismatchCtClass() throws Exception {
        int databaseSizeBeforeUpdate = ctClassRepository.findAll().size();
        ctClass.setId(count.incrementAndGet());

        // Create the CtClass
        CtClassDTO ctClassDTO = ctClassMapper.toDto(ctClass);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCtClassMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ctClassDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CtClass in the database
        List<CtClass> ctClassList = ctClassRepository.findAll();
        assertThat(ctClassList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CtClass in Elasticsearch
        verify(mockCtClassSearchRepository, times(0)).save(ctClass);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCtClass() throws Exception {
        int databaseSizeBeforeUpdate = ctClassRepository.findAll().size();
        ctClass.setId(count.incrementAndGet());

        // Create the CtClass
        CtClassDTO ctClassDTO = ctClassMapper.toDto(ctClass);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCtClassMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ctClassDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CtClass in the database
        List<CtClass> ctClassList = ctClassRepository.findAll();
        assertThat(ctClassList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CtClass in Elasticsearch
        verify(mockCtClassSearchRepository, times(0)).save(ctClass);
    }

    @Test
    @Transactional
    void partialUpdateCtClassWithPatch() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        int databaseSizeBeforeUpdate = ctClassRepository.findAll().size();

        // Update the ctClass using partial update
        CtClass partialUpdatedCtClass = new CtClass();
        partialUpdatedCtClass.setId(ctClass.getId());

        partialUpdatedCtClass.dt(UPDATED_DT).jbempn(UPDATED_JBEMPN).sk(UPDATED_SK).gz(UPDATED_GZ).yh(UPDATED_YH).zzh(UPDATED_ZZH);

        restCtClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCtClass.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCtClass))
            )
            .andExpect(status().isOk());

        // Validate the CtClass in the database
        List<CtClass> ctClassList = ctClassRepository.findAll();
        assertThat(ctClassList).hasSize(databaseSizeBeforeUpdate);
        CtClass testCtClass = ctClassList.get(ctClassList.size() - 1);
        assertThat(testCtClass.getDt()).isEqualTo(UPDATED_DT);
        assertThat(testCtClass.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testCtClass.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testCtClass.getJbempn()).isEqualTo(UPDATED_JBEMPN);
        assertThat(testCtClass.getGotime()).isEqualTo(DEFAULT_GOTIME);
        assertThat(testCtClass.getXj()).isEqualByComparingTo(DEFAULT_XJ);
        assertThat(testCtClass.getZp()).isEqualByComparingTo(DEFAULT_ZP);
        assertThat(testCtClass.getSk()).isEqualByComparingTo(UPDATED_SK);
        assertThat(testCtClass.getHyk()).isEqualByComparingTo(DEFAULT_HYK);
        assertThat(testCtClass.getCq()).isEqualByComparingTo(DEFAULT_CQ);
        assertThat(testCtClass.getGz()).isEqualByComparingTo(UPDATED_GZ);
        assertThat(testCtClass.getGfz()).isEqualByComparingTo(DEFAULT_GFZ);
        assertThat(testCtClass.getYq()).isEqualByComparingTo(DEFAULT_YQ);
        assertThat(testCtClass.getYh()).isEqualByComparingTo(UPDATED_YH);
        assertThat(testCtClass.getZzh()).isEqualByComparingTo(UPDATED_ZZH);
        assertThat(testCtClass.getCheckSign()).isEqualTo(DEFAULT_CHECK_SIGN);
    }

    @Test
    @Transactional
    void fullUpdateCtClassWithPatch() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        int databaseSizeBeforeUpdate = ctClassRepository.findAll().size();

        // Update the ctClass using partial update
        CtClass partialUpdatedCtClass = new CtClass();
        partialUpdatedCtClass.setId(ctClass.getId());

        partialUpdatedCtClass
            .dt(UPDATED_DT)
            .empn(UPDATED_EMPN)
            .flag(UPDATED_FLAG)
            .jbempn(UPDATED_JBEMPN)
            .gotime(UPDATED_GOTIME)
            .xj(UPDATED_XJ)
            .zp(UPDATED_ZP)
            .sk(UPDATED_SK)
            .hyk(UPDATED_HYK)
            .cq(UPDATED_CQ)
            .gz(UPDATED_GZ)
            .gfz(UPDATED_GFZ)
            .yq(UPDATED_YQ)
            .yh(UPDATED_YH)
            .zzh(UPDATED_ZZH)
            .checkSign(UPDATED_CHECK_SIGN);

        restCtClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCtClass.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCtClass))
            )
            .andExpect(status().isOk());

        // Validate the CtClass in the database
        List<CtClass> ctClassList = ctClassRepository.findAll();
        assertThat(ctClassList).hasSize(databaseSizeBeforeUpdate);
        CtClass testCtClass = ctClassList.get(ctClassList.size() - 1);
        assertThat(testCtClass.getDt()).isEqualTo(UPDATED_DT);
        assertThat(testCtClass.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCtClass.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testCtClass.getJbempn()).isEqualTo(UPDATED_JBEMPN);
        assertThat(testCtClass.getGotime()).isEqualTo(UPDATED_GOTIME);
        assertThat(testCtClass.getXj()).isEqualByComparingTo(UPDATED_XJ);
        assertThat(testCtClass.getZp()).isEqualByComparingTo(UPDATED_ZP);
        assertThat(testCtClass.getSk()).isEqualByComparingTo(UPDATED_SK);
        assertThat(testCtClass.getHyk()).isEqualByComparingTo(UPDATED_HYK);
        assertThat(testCtClass.getCq()).isEqualByComparingTo(UPDATED_CQ);
        assertThat(testCtClass.getGz()).isEqualByComparingTo(UPDATED_GZ);
        assertThat(testCtClass.getGfz()).isEqualByComparingTo(UPDATED_GFZ);
        assertThat(testCtClass.getYq()).isEqualByComparingTo(UPDATED_YQ);
        assertThat(testCtClass.getYh()).isEqualByComparingTo(UPDATED_YH);
        assertThat(testCtClass.getZzh()).isEqualByComparingTo(UPDATED_ZZH);
        assertThat(testCtClass.getCheckSign()).isEqualTo(UPDATED_CHECK_SIGN);
    }

    @Test
    @Transactional
    void patchNonExistingCtClass() throws Exception {
        int databaseSizeBeforeUpdate = ctClassRepository.findAll().size();
        ctClass.setId(count.incrementAndGet());

        // Create the CtClass
        CtClassDTO ctClassDTO = ctClassMapper.toDto(ctClass);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCtClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ctClassDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ctClassDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CtClass in the database
        List<CtClass> ctClassList = ctClassRepository.findAll();
        assertThat(ctClassList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CtClass in Elasticsearch
        verify(mockCtClassSearchRepository, times(0)).save(ctClass);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCtClass() throws Exception {
        int databaseSizeBeforeUpdate = ctClassRepository.findAll().size();
        ctClass.setId(count.incrementAndGet());

        // Create the CtClass
        CtClassDTO ctClassDTO = ctClassMapper.toDto(ctClass);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCtClassMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ctClassDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CtClass in the database
        List<CtClass> ctClassList = ctClassRepository.findAll();
        assertThat(ctClassList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CtClass in Elasticsearch
        verify(mockCtClassSearchRepository, times(0)).save(ctClass);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCtClass() throws Exception {
        int databaseSizeBeforeUpdate = ctClassRepository.findAll().size();
        ctClass.setId(count.incrementAndGet());

        // Create the CtClass
        CtClassDTO ctClassDTO = ctClassMapper.toDto(ctClass);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCtClassMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ctClassDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CtClass in the database
        List<CtClass> ctClassList = ctClassRepository.findAll();
        assertThat(ctClassList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CtClass in Elasticsearch
        verify(mockCtClassSearchRepository, times(0)).save(ctClass);
    }

    @Test
    @Transactional
    void deleteCtClass() throws Exception {
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);

        int databaseSizeBeforeDelete = ctClassRepository.findAll().size();

        // Delete the ctClass
        restCtClassMockMvc
            .perform(delete(ENTITY_API_URL_ID, ctClass.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CtClass> ctClassList = ctClassRepository.findAll();
        assertThat(ctClassList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CtClass in Elasticsearch
        verify(mockCtClassSearchRepository, times(1)).deleteById(ctClass.getId());
    }

    @Test
    @Transactional
    void searchCtClass() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        ctClassRepository.saveAndFlush(ctClass);
        when(mockCtClassSearchRepository.search(queryStringQuery("id:" + ctClass.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ctClass), PageRequest.of(0, 1), 1));

        // Search the ctClass
        restCtClassMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ctClass.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ctClass.getId().intValue())))
            .andExpect(jsonPath("$.[*].dt").value(hasItem(DEFAULT_DT.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].jbempn").value(hasItem(DEFAULT_JBEMPN)))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].xj").value(hasItem(sameNumber(DEFAULT_XJ))))
            .andExpect(jsonPath("$.[*].zp").value(hasItem(sameNumber(DEFAULT_ZP))))
            .andExpect(jsonPath("$.[*].sk").value(hasItem(sameNumber(DEFAULT_SK))))
            .andExpect(jsonPath("$.[*].hyk").value(hasItem(sameNumber(DEFAULT_HYK))))
            .andExpect(jsonPath("$.[*].cq").value(hasItem(sameNumber(DEFAULT_CQ))))
            .andExpect(jsonPath("$.[*].gz").value(hasItem(sameNumber(DEFAULT_GZ))))
            .andExpect(jsonPath("$.[*].gfz").value(hasItem(sameNumber(DEFAULT_GFZ))))
            .andExpect(jsonPath("$.[*].yq").value(hasItem(sameNumber(DEFAULT_YQ))))
            .andExpect(jsonPath("$.[*].yh").value(hasItem(sameNumber(DEFAULT_YH))))
            .andExpect(jsonPath("$.[*].zzh").value(hasItem(sameNumber(DEFAULT_ZZH))))
            .andExpect(jsonPath("$.[*].checkSign").value(hasItem(DEFAULT_CHECK_SIGN)));
    }
}
