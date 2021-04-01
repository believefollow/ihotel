package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Classreport;
import ihotel.app.repository.ClassreportRepository;
import ihotel.app.repository.search.ClassreportSearchRepository;
import ihotel.app.service.criteria.ClassreportCriteria;
import ihotel.app.service.dto.ClassreportDTO;
import ihotel.app.service.mapper.ClassreportMapper;
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
 * Integration tests for the {@link ClassreportResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClassreportResourceIT {

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final Instant DEFAULT_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_XJ_UP = new BigDecimal(1);
    private static final BigDecimal UPDATED_XJ_UP = new BigDecimal(2);
    private static final BigDecimal SMALLER_XJ_UP = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_YFJ_A = new BigDecimal(1);
    private static final BigDecimal UPDATED_YFJ_A = new BigDecimal(2);
    private static final BigDecimal SMALLER_YFJ_A = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_YFJ_D = new BigDecimal(1);
    private static final BigDecimal UPDATED_YFJ_D = new BigDecimal(2);
    private static final BigDecimal SMALLER_YFJ_D = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_GZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_GZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_GZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ZZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_ZZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_ZZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ZZ_YJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_ZZ_YJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_ZZ_YJ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ZZ_JS = new BigDecimal(1);
    private static final BigDecimal UPDATED_ZZ_JS = new BigDecimal(2);
    private static final BigDecimal SMALLER_ZZ_JS = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ZZ_TC = new BigDecimal(1);
    private static final BigDecimal UPDATED_ZZ_TC = new BigDecimal(2);
    private static final BigDecimal SMALLER_ZZ_TC = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FF = new BigDecimal(1);
    private static final BigDecimal UPDATED_FF = new BigDecimal(2);
    private static final BigDecimal SMALLER_FF = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_MINIBAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_MINIBAR = new BigDecimal(2);
    private static final BigDecimal SMALLER_MINIBAR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PHONE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PHONE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PHONE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_OTHER = new BigDecimal(1);
    private static final BigDecimal UPDATED_OTHER = new BigDecimal(2);
    private static final BigDecimal SMALLER_OTHER = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PC = new BigDecimal(1);
    private static final BigDecimal UPDATED_PC = new BigDecimal(2);
    private static final BigDecimal SMALLER_PC = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_CZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_CZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CY = new BigDecimal(2);
    private static final BigDecimal SMALLER_CY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_MD = new BigDecimal(1);
    private static final BigDecimal UPDATED_MD = new BigDecimal(2);
    private static final BigDecimal SMALLER_MD = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HUIY = new BigDecimal(1);
    private static final BigDecimal UPDATED_HUIY = new BigDecimal(2);
    private static final BigDecimal SMALLER_HUIY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DTB = new BigDecimal(1);
    private static final BigDecimal UPDATED_DTB = new BigDecimal(2);
    private static final BigDecimal SMALLER_DTB = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SSZX = new BigDecimal(1);
    private static final BigDecimal UPDATED_SSZX = new BigDecimal(2);
    private static final BigDecimal SMALLER_SSZX = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CYZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_CYZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_CYZ = new BigDecimal(1 - 1);

    private static final String DEFAULT_HOTELDM = "AAAAAAAAAA";
    private static final String UPDATED_HOTELDM = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_GZXJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_GZXJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_GZXJ = new BigDecimal(1 - 1);

    private static final Long DEFAULT_ISNEW = 1L;
    private static final Long UPDATED_ISNEW = 2L;
    private static final Long SMALLER_ISNEW = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/classreports";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/classreports";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClassreportRepository classreportRepository;

    @Autowired
    private ClassreportMapper classreportMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.ClassreportSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClassreportSearchRepository mockClassreportSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassreportMockMvc;

    private Classreport classreport;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classreport createEntity(EntityManager em) {
        Classreport classreport = new Classreport()
            .empn(DEFAULT_EMPN)
            .dt(DEFAULT_DT)
            .xjUp(DEFAULT_XJ_UP)
            .yfjA(DEFAULT_YFJ_A)
            .yfjD(DEFAULT_YFJ_D)
            .gz(DEFAULT_GZ)
            .zz(DEFAULT_ZZ)
            .zzYj(DEFAULT_ZZ_YJ)
            .zzJs(DEFAULT_ZZ_JS)
            .zzTc(DEFAULT_ZZ_TC)
            .ff(DEFAULT_FF)
            .minibar(DEFAULT_MINIBAR)
            .phone(DEFAULT_PHONE)
            .other(DEFAULT_OTHER)
            .pc(DEFAULT_PC)
            .cz(DEFAULT_CZ)
            .cy(DEFAULT_CY)
            .md(DEFAULT_MD)
            .huiy(DEFAULT_HUIY)
            .dtb(DEFAULT_DTB)
            .sszx(DEFAULT_SSZX)
            .cyz(DEFAULT_CYZ)
            .hoteldm(DEFAULT_HOTELDM)
            .gzxj(DEFAULT_GZXJ)
            .isnew(DEFAULT_ISNEW);
        return classreport;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Classreport createUpdatedEntity(EntityManager em) {
        Classreport classreport = new Classreport()
            .empn(UPDATED_EMPN)
            .dt(UPDATED_DT)
            .xjUp(UPDATED_XJ_UP)
            .yfjA(UPDATED_YFJ_A)
            .yfjD(UPDATED_YFJ_D)
            .gz(UPDATED_GZ)
            .zz(UPDATED_ZZ)
            .zzYj(UPDATED_ZZ_YJ)
            .zzJs(UPDATED_ZZ_JS)
            .zzTc(UPDATED_ZZ_TC)
            .ff(UPDATED_FF)
            .minibar(UPDATED_MINIBAR)
            .phone(UPDATED_PHONE)
            .other(UPDATED_OTHER)
            .pc(UPDATED_PC)
            .cz(UPDATED_CZ)
            .cy(UPDATED_CY)
            .md(UPDATED_MD)
            .huiy(UPDATED_HUIY)
            .dtb(UPDATED_DTB)
            .sszx(UPDATED_SSZX)
            .cyz(UPDATED_CYZ)
            .hoteldm(UPDATED_HOTELDM)
            .gzxj(UPDATED_GZXJ)
            .isnew(UPDATED_ISNEW);
        return classreport;
    }

    @BeforeEach
    public void initTest() {
        classreport = createEntity(em);
    }

    @Test
    @Transactional
    void createClassreport() throws Exception {
        int databaseSizeBeforeCreate = classreportRepository.findAll().size();
        // Create the Classreport
        ClassreportDTO classreportDTO = classreportMapper.toDto(classreport);
        restClassreportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classreportDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Classreport in the database
        List<Classreport> classreportList = classreportRepository.findAll();
        assertThat(classreportList).hasSize(databaseSizeBeforeCreate + 1);
        Classreport testClassreport = classreportList.get(classreportList.size() - 1);
        assertThat(testClassreport.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testClassreport.getDt()).isEqualTo(DEFAULT_DT);
        assertThat(testClassreport.getXjUp()).isEqualByComparingTo(DEFAULT_XJ_UP);
        assertThat(testClassreport.getYfjA()).isEqualByComparingTo(DEFAULT_YFJ_A);
        assertThat(testClassreport.getYfjD()).isEqualByComparingTo(DEFAULT_YFJ_D);
        assertThat(testClassreport.getGz()).isEqualByComparingTo(DEFAULT_GZ);
        assertThat(testClassreport.getZz()).isEqualByComparingTo(DEFAULT_ZZ);
        assertThat(testClassreport.getZzYj()).isEqualByComparingTo(DEFAULT_ZZ_YJ);
        assertThat(testClassreport.getZzJs()).isEqualByComparingTo(DEFAULT_ZZ_JS);
        assertThat(testClassreport.getZzTc()).isEqualByComparingTo(DEFAULT_ZZ_TC);
        assertThat(testClassreport.getFf()).isEqualByComparingTo(DEFAULT_FF);
        assertThat(testClassreport.getMinibar()).isEqualByComparingTo(DEFAULT_MINIBAR);
        assertThat(testClassreport.getPhone()).isEqualByComparingTo(DEFAULT_PHONE);
        assertThat(testClassreport.getOther()).isEqualByComparingTo(DEFAULT_OTHER);
        assertThat(testClassreport.getPc()).isEqualByComparingTo(DEFAULT_PC);
        assertThat(testClassreport.getCz()).isEqualByComparingTo(DEFAULT_CZ);
        assertThat(testClassreport.getCy()).isEqualByComparingTo(DEFAULT_CY);
        assertThat(testClassreport.getMd()).isEqualByComparingTo(DEFAULT_MD);
        assertThat(testClassreport.getHuiy()).isEqualByComparingTo(DEFAULT_HUIY);
        assertThat(testClassreport.getDtb()).isEqualByComparingTo(DEFAULT_DTB);
        assertThat(testClassreport.getSszx()).isEqualByComparingTo(DEFAULT_SSZX);
        assertThat(testClassreport.getCyz()).isEqualByComparingTo(DEFAULT_CYZ);
        assertThat(testClassreport.getHoteldm()).isEqualTo(DEFAULT_HOTELDM);
        assertThat(testClassreport.getGzxj()).isEqualByComparingTo(DEFAULT_GZXJ);
        assertThat(testClassreport.getIsnew()).isEqualTo(DEFAULT_ISNEW);

        // Validate the Classreport in Elasticsearch
        verify(mockClassreportSearchRepository, times(1)).save(testClassreport);
    }

    @Test
    @Transactional
    void createClassreportWithExistingId() throws Exception {
        // Create the Classreport with an existing ID
        classreport.setId(1L);
        ClassreportDTO classreportDTO = classreportMapper.toDto(classreport);

        int databaseSizeBeforeCreate = classreportRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassreportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classreportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classreport in the database
        List<Classreport> classreportList = classreportRepository.findAll();
        assertThat(classreportList).hasSize(databaseSizeBeforeCreate);

        // Validate the Classreport in Elasticsearch
        verify(mockClassreportSearchRepository, times(0)).save(classreport);
    }

    @Test
    @Transactional
    void checkEmpnIsRequired() throws Exception {
        int databaseSizeBeforeTest = classreportRepository.findAll().size();
        // set the field null
        classreport.setEmpn(null);

        // Create the Classreport, which fails.
        ClassreportDTO classreportDTO = classreportMapper.toDto(classreport);

        restClassreportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classreportDTO))
            )
            .andExpect(status().isBadRequest());

        List<Classreport> classreportList = classreportRepository.findAll();
        assertThat(classreportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDtIsRequired() throws Exception {
        int databaseSizeBeforeTest = classreportRepository.findAll().size();
        // set the field null
        classreport.setDt(null);

        // Create the Classreport, which fails.
        ClassreportDTO classreportDTO = classreportMapper.toDto(classreport);

        restClassreportMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classreportDTO))
            )
            .andExpect(status().isBadRequest());

        List<Classreport> classreportList = classreportRepository.findAll();
        assertThat(classreportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClassreports() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList
        restClassreportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classreport.getId().intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].dt").value(hasItem(DEFAULT_DT.toString())))
            .andExpect(jsonPath("$.[*].xjUp").value(hasItem(sameNumber(DEFAULT_XJ_UP))))
            .andExpect(jsonPath("$.[*].yfjA").value(hasItem(sameNumber(DEFAULT_YFJ_A))))
            .andExpect(jsonPath("$.[*].yfjD").value(hasItem(sameNumber(DEFAULT_YFJ_D))))
            .andExpect(jsonPath("$.[*].gz").value(hasItem(sameNumber(DEFAULT_GZ))))
            .andExpect(jsonPath("$.[*].zz").value(hasItem(sameNumber(DEFAULT_ZZ))))
            .andExpect(jsonPath("$.[*].zzYj").value(hasItem(sameNumber(DEFAULT_ZZ_YJ))))
            .andExpect(jsonPath("$.[*].zzJs").value(hasItem(sameNumber(DEFAULT_ZZ_JS))))
            .andExpect(jsonPath("$.[*].zzTc").value(hasItem(sameNumber(DEFAULT_ZZ_TC))))
            .andExpect(jsonPath("$.[*].ff").value(hasItem(sameNumber(DEFAULT_FF))))
            .andExpect(jsonPath("$.[*].minibar").value(hasItem(sameNumber(DEFAULT_MINIBAR))))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(sameNumber(DEFAULT_PHONE))))
            .andExpect(jsonPath("$.[*].other").value(hasItem(sameNumber(DEFAULT_OTHER))))
            .andExpect(jsonPath("$.[*].pc").value(hasItem(sameNumber(DEFAULT_PC))))
            .andExpect(jsonPath("$.[*].cz").value(hasItem(sameNumber(DEFAULT_CZ))))
            .andExpect(jsonPath("$.[*].cy").value(hasItem(sameNumber(DEFAULT_CY))))
            .andExpect(jsonPath("$.[*].md").value(hasItem(sameNumber(DEFAULT_MD))))
            .andExpect(jsonPath("$.[*].huiy").value(hasItem(sameNumber(DEFAULT_HUIY))))
            .andExpect(jsonPath("$.[*].dtb").value(hasItem(sameNumber(DEFAULT_DTB))))
            .andExpect(jsonPath("$.[*].sszx").value(hasItem(sameNumber(DEFAULT_SSZX))))
            .andExpect(jsonPath("$.[*].cyz").value(hasItem(sameNumber(DEFAULT_CYZ))))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].gzxj").value(hasItem(sameNumber(DEFAULT_GZXJ))))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())));
    }

    @Test
    @Transactional
    void getClassreport() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get the classreport
        restClassreportMockMvc
            .perform(get(ENTITY_API_URL_ID, classreport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classreport.getId().intValue()))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.dt").value(DEFAULT_DT.toString()))
            .andExpect(jsonPath("$.xjUp").value(sameNumber(DEFAULT_XJ_UP)))
            .andExpect(jsonPath("$.yfjA").value(sameNumber(DEFAULT_YFJ_A)))
            .andExpect(jsonPath("$.yfjD").value(sameNumber(DEFAULT_YFJ_D)))
            .andExpect(jsonPath("$.gz").value(sameNumber(DEFAULT_GZ)))
            .andExpect(jsonPath("$.zz").value(sameNumber(DEFAULT_ZZ)))
            .andExpect(jsonPath("$.zzYj").value(sameNumber(DEFAULT_ZZ_YJ)))
            .andExpect(jsonPath("$.zzJs").value(sameNumber(DEFAULT_ZZ_JS)))
            .andExpect(jsonPath("$.zzTc").value(sameNumber(DEFAULT_ZZ_TC)))
            .andExpect(jsonPath("$.ff").value(sameNumber(DEFAULT_FF)))
            .andExpect(jsonPath("$.minibar").value(sameNumber(DEFAULT_MINIBAR)))
            .andExpect(jsonPath("$.phone").value(sameNumber(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.other").value(sameNumber(DEFAULT_OTHER)))
            .andExpect(jsonPath("$.pc").value(sameNumber(DEFAULT_PC)))
            .andExpect(jsonPath("$.cz").value(sameNumber(DEFAULT_CZ)))
            .andExpect(jsonPath("$.cy").value(sameNumber(DEFAULT_CY)))
            .andExpect(jsonPath("$.md").value(sameNumber(DEFAULT_MD)))
            .andExpect(jsonPath("$.huiy").value(sameNumber(DEFAULT_HUIY)))
            .andExpect(jsonPath("$.dtb").value(sameNumber(DEFAULT_DTB)))
            .andExpect(jsonPath("$.sszx").value(sameNumber(DEFAULT_SSZX)))
            .andExpect(jsonPath("$.cyz").value(sameNumber(DEFAULT_CYZ)))
            .andExpect(jsonPath("$.hoteldm").value(DEFAULT_HOTELDM))
            .andExpect(jsonPath("$.gzxj").value(sameNumber(DEFAULT_GZXJ)))
            .andExpect(jsonPath("$.isnew").value(DEFAULT_ISNEW.intValue()));
    }

    @Test
    @Transactional
    void getClassreportsByIdFiltering() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        Long id = classreport.getId();

        defaultClassreportShouldBeFound("id.equals=" + id);
        defaultClassreportShouldNotBeFound("id.notEquals=" + id);

        defaultClassreportShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClassreportShouldNotBeFound("id.greaterThan=" + id);

        defaultClassreportShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClassreportShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClassreportsByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where empn equals to DEFAULT_EMPN
        defaultClassreportShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the classreportList where empn equals to UPDATED_EMPN
        defaultClassreportShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassreportsByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where empn not equals to DEFAULT_EMPN
        defaultClassreportShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the classreportList where empn not equals to UPDATED_EMPN
        defaultClassreportShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassreportsByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultClassreportShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the classreportList where empn equals to UPDATED_EMPN
        defaultClassreportShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassreportsByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where empn is not null
        defaultClassreportShouldBeFound("empn.specified=true");

        // Get all the classreportList where empn is null
        defaultClassreportShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByEmpnContainsSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where empn contains DEFAULT_EMPN
        defaultClassreportShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the classreportList where empn contains UPDATED_EMPN
        defaultClassreportShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassreportsByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where empn does not contain DEFAULT_EMPN
        defaultClassreportShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the classreportList where empn does not contain UPDATED_EMPN
        defaultClassreportShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassreportsByDtIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where dt equals to DEFAULT_DT
        defaultClassreportShouldBeFound("dt.equals=" + DEFAULT_DT);

        // Get all the classreportList where dt equals to UPDATED_DT
        defaultClassreportShouldNotBeFound("dt.equals=" + UPDATED_DT);
    }

    @Test
    @Transactional
    void getAllClassreportsByDtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where dt not equals to DEFAULT_DT
        defaultClassreportShouldNotBeFound("dt.notEquals=" + DEFAULT_DT);

        // Get all the classreportList where dt not equals to UPDATED_DT
        defaultClassreportShouldBeFound("dt.notEquals=" + UPDATED_DT);
    }

    @Test
    @Transactional
    void getAllClassreportsByDtIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where dt in DEFAULT_DT or UPDATED_DT
        defaultClassreportShouldBeFound("dt.in=" + DEFAULT_DT + "," + UPDATED_DT);

        // Get all the classreportList where dt equals to UPDATED_DT
        defaultClassreportShouldNotBeFound("dt.in=" + UPDATED_DT);
    }

    @Test
    @Transactional
    void getAllClassreportsByDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where dt is not null
        defaultClassreportShouldBeFound("dt.specified=true");

        // Get all the classreportList where dt is null
        defaultClassreportShouldNotBeFound("dt.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByXjUpIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where xjUp equals to DEFAULT_XJ_UP
        defaultClassreportShouldBeFound("xjUp.equals=" + DEFAULT_XJ_UP);

        // Get all the classreportList where xjUp equals to UPDATED_XJ_UP
        defaultClassreportShouldNotBeFound("xjUp.equals=" + UPDATED_XJ_UP);
    }

    @Test
    @Transactional
    void getAllClassreportsByXjUpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where xjUp not equals to DEFAULT_XJ_UP
        defaultClassreportShouldNotBeFound("xjUp.notEquals=" + DEFAULT_XJ_UP);

        // Get all the classreportList where xjUp not equals to UPDATED_XJ_UP
        defaultClassreportShouldBeFound("xjUp.notEquals=" + UPDATED_XJ_UP);
    }

    @Test
    @Transactional
    void getAllClassreportsByXjUpIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where xjUp in DEFAULT_XJ_UP or UPDATED_XJ_UP
        defaultClassreportShouldBeFound("xjUp.in=" + DEFAULT_XJ_UP + "," + UPDATED_XJ_UP);

        // Get all the classreportList where xjUp equals to UPDATED_XJ_UP
        defaultClassreportShouldNotBeFound("xjUp.in=" + UPDATED_XJ_UP);
    }

    @Test
    @Transactional
    void getAllClassreportsByXjUpIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where xjUp is not null
        defaultClassreportShouldBeFound("xjUp.specified=true");

        // Get all the classreportList where xjUp is null
        defaultClassreportShouldNotBeFound("xjUp.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByXjUpIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where xjUp is greater than or equal to DEFAULT_XJ_UP
        defaultClassreportShouldBeFound("xjUp.greaterThanOrEqual=" + DEFAULT_XJ_UP);

        // Get all the classreportList where xjUp is greater than or equal to UPDATED_XJ_UP
        defaultClassreportShouldNotBeFound("xjUp.greaterThanOrEqual=" + UPDATED_XJ_UP);
    }

    @Test
    @Transactional
    void getAllClassreportsByXjUpIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where xjUp is less than or equal to DEFAULT_XJ_UP
        defaultClassreportShouldBeFound("xjUp.lessThanOrEqual=" + DEFAULT_XJ_UP);

        // Get all the classreportList where xjUp is less than or equal to SMALLER_XJ_UP
        defaultClassreportShouldNotBeFound("xjUp.lessThanOrEqual=" + SMALLER_XJ_UP);
    }

    @Test
    @Transactional
    void getAllClassreportsByXjUpIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where xjUp is less than DEFAULT_XJ_UP
        defaultClassreportShouldNotBeFound("xjUp.lessThan=" + DEFAULT_XJ_UP);

        // Get all the classreportList where xjUp is less than UPDATED_XJ_UP
        defaultClassreportShouldBeFound("xjUp.lessThan=" + UPDATED_XJ_UP);
    }

    @Test
    @Transactional
    void getAllClassreportsByXjUpIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where xjUp is greater than DEFAULT_XJ_UP
        defaultClassreportShouldNotBeFound("xjUp.greaterThan=" + DEFAULT_XJ_UP);

        // Get all the classreportList where xjUp is greater than SMALLER_XJ_UP
        defaultClassreportShouldBeFound("xjUp.greaterThan=" + SMALLER_XJ_UP);
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjAIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjA equals to DEFAULT_YFJ_A
        defaultClassreportShouldBeFound("yfjA.equals=" + DEFAULT_YFJ_A);

        // Get all the classreportList where yfjA equals to UPDATED_YFJ_A
        defaultClassreportShouldNotBeFound("yfjA.equals=" + UPDATED_YFJ_A);
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjAIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjA not equals to DEFAULT_YFJ_A
        defaultClassreportShouldNotBeFound("yfjA.notEquals=" + DEFAULT_YFJ_A);

        // Get all the classreportList where yfjA not equals to UPDATED_YFJ_A
        defaultClassreportShouldBeFound("yfjA.notEquals=" + UPDATED_YFJ_A);
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjAIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjA in DEFAULT_YFJ_A or UPDATED_YFJ_A
        defaultClassreportShouldBeFound("yfjA.in=" + DEFAULT_YFJ_A + "," + UPDATED_YFJ_A);

        // Get all the classreportList where yfjA equals to UPDATED_YFJ_A
        defaultClassreportShouldNotBeFound("yfjA.in=" + UPDATED_YFJ_A);
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjAIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjA is not null
        defaultClassreportShouldBeFound("yfjA.specified=true");

        // Get all the classreportList where yfjA is null
        defaultClassreportShouldNotBeFound("yfjA.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjAIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjA is greater than or equal to DEFAULT_YFJ_A
        defaultClassreportShouldBeFound("yfjA.greaterThanOrEqual=" + DEFAULT_YFJ_A);

        // Get all the classreportList where yfjA is greater than or equal to UPDATED_YFJ_A
        defaultClassreportShouldNotBeFound("yfjA.greaterThanOrEqual=" + UPDATED_YFJ_A);
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjAIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjA is less than or equal to DEFAULT_YFJ_A
        defaultClassreportShouldBeFound("yfjA.lessThanOrEqual=" + DEFAULT_YFJ_A);

        // Get all the classreportList where yfjA is less than or equal to SMALLER_YFJ_A
        defaultClassreportShouldNotBeFound("yfjA.lessThanOrEqual=" + SMALLER_YFJ_A);
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjAIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjA is less than DEFAULT_YFJ_A
        defaultClassreportShouldNotBeFound("yfjA.lessThan=" + DEFAULT_YFJ_A);

        // Get all the classreportList where yfjA is less than UPDATED_YFJ_A
        defaultClassreportShouldBeFound("yfjA.lessThan=" + UPDATED_YFJ_A);
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjAIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjA is greater than DEFAULT_YFJ_A
        defaultClassreportShouldNotBeFound("yfjA.greaterThan=" + DEFAULT_YFJ_A);

        // Get all the classreportList where yfjA is greater than SMALLER_YFJ_A
        defaultClassreportShouldBeFound("yfjA.greaterThan=" + SMALLER_YFJ_A);
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjDIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjD equals to DEFAULT_YFJ_D
        defaultClassreportShouldBeFound("yfjD.equals=" + DEFAULT_YFJ_D);

        // Get all the classreportList where yfjD equals to UPDATED_YFJ_D
        defaultClassreportShouldNotBeFound("yfjD.equals=" + UPDATED_YFJ_D);
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjDIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjD not equals to DEFAULT_YFJ_D
        defaultClassreportShouldNotBeFound("yfjD.notEquals=" + DEFAULT_YFJ_D);

        // Get all the classreportList where yfjD not equals to UPDATED_YFJ_D
        defaultClassreportShouldBeFound("yfjD.notEquals=" + UPDATED_YFJ_D);
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjDIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjD in DEFAULT_YFJ_D or UPDATED_YFJ_D
        defaultClassreportShouldBeFound("yfjD.in=" + DEFAULT_YFJ_D + "," + UPDATED_YFJ_D);

        // Get all the classreportList where yfjD equals to UPDATED_YFJ_D
        defaultClassreportShouldNotBeFound("yfjD.in=" + UPDATED_YFJ_D);
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjDIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjD is not null
        defaultClassreportShouldBeFound("yfjD.specified=true");

        // Get all the classreportList where yfjD is null
        defaultClassreportShouldNotBeFound("yfjD.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjDIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjD is greater than or equal to DEFAULT_YFJ_D
        defaultClassreportShouldBeFound("yfjD.greaterThanOrEqual=" + DEFAULT_YFJ_D);

        // Get all the classreportList where yfjD is greater than or equal to UPDATED_YFJ_D
        defaultClassreportShouldNotBeFound("yfjD.greaterThanOrEqual=" + UPDATED_YFJ_D);
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjDIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjD is less than or equal to DEFAULT_YFJ_D
        defaultClassreportShouldBeFound("yfjD.lessThanOrEqual=" + DEFAULT_YFJ_D);

        // Get all the classreportList where yfjD is less than or equal to SMALLER_YFJ_D
        defaultClassreportShouldNotBeFound("yfjD.lessThanOrEqual=" + SMALLER_YFJ_D);
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjDIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjD is less than DEFAULT_YFJ_D
        defaultClassreportShouldNotBeFound("yfjD.lessThan=" + DEFAULT_YFJ_D);

        // Get all the classreportList where yfjD is less than UPDATED_YFJ_D
        defaultClassreportShouldBeFound("yfjD.lessThan=" + UPDATED_YFJ_D);
    }

    @Test
    @Transactional
    void getAllClassreportsByYfjDIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where yfjD is greater than DEFAULT_YFJ_D
        defaultClassreportShouldNotBeFound("yfjD.greaterThan=" + DEFAULT_YFJ_D);

        // Get all the classreportList where yfjD is greater than SMALLER_YFJ_D
        defaultClassreportShouldBeFound("yfjD.greaterThan=" + SMALLER_YFJ_D);
    }

    @Test
    @Transactional
    void getAllClassreportsByGzIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gz equals to DEFAULT_GZ
        defaultClassreportShouldBeFound("gz.equals=" + DEFAULT_GZ);

        // Get all the classreportList where gz equals to UPDATED_GZ
        defaultClassreportShouldNotBeFound("gz.equals=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByGzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gz not equals to DEFAULT_GZ
        defaultClassreportShouldNotBeFound("gz.notEquals=" + DEFAULT_GZ);

        // Get all the classreportList where gz not equals to UPDATED_GZ
        defaultClassreportShouldBeFound("gz.notEquals=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByGzIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gz in DEFAULT_GZ or UPDATED_GZ
        defaultClassreportShouldBeFound("gz.in=" + DEFAULT_GZ + "," + UPDATED_GZ);

        // Get all the classreportList where gz equals to UPDATED_GZ
        defaultClassreportShouldNotBeFound("gz.in=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByGzIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gz is not null
        defaultClassreportShouldBeFound("gz.specified=true");

        // Get all the classreportList where gz is null
        defaultClassreportShouldNotBeFound("gz.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByGzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gz is greater than or equal to DEFAULT_GZ
        defaultClassreportShouldBeFound("gz.greaterThanOrEqual=" + DEFAULT_GZ);

        // Get all the classreportList where gz is greater than or equal to UPDATED_GZ
        defaultClassreportShouldNotBeFound("gz.greaterThanOrEqual=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByGzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gz is less than or equal to DEFAULT_GZ
        defaultClassreportShouldBeFound("gz.lessThanOrEqual=" + DEFAULT_GZ);

        // Get all the classreportList where gz is less than or equal to SMALLER_GZ
        defaultClassreportShouldNotBeFound("gz.lessThanOrEqual=" + SMALLER_GZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByGzIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gz is less than DEFAULT_GZ
        defaultClassreportShouldNotBeFound("gz.lessThan=" + DEFAULT_GZ);

        // Get all the classreportList where gz is less than UPDATED_GZ
        defaultClassreportShouldBeFound("gz.lessThan=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByGzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gz is greater than DEFAULT_GZ
        defaultClassreportShouldNotBeFound("gz.greaterThan=" + DEFAULT_GZ);

        // Get all the classreportList where gz is greater than SMALLER_GZ
        defaultClassreportShouldBeFound("gz.greaterThan=" + SMALLER_GZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zz equals to DEFAULT_ZZ
        defaultClassreportShouldBeFound("zz.equals=" + DEFAULT_ZZ);

        // Get all the classreportList where zz equals to UPDATED_ZZ
        defaultClassreportShouldNotBeFound("zz.equals=" + UPDATED_ZZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zz not equals to DEFAULT_ZZ
        defaultClassreportShouldNotBeFound("zz.notEquals=" + DEFAULT_ZZ);

        // Get all the classreportList where zz not equals to UPDATED_ZZ
        defaultClassreportShouldBeFound("zz.notEquals=" + UPDATED_ZZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zz in DEFAULT_ZZ or UPDATED_ZZ
        defaultClassreportShouldBeFound("zz.in=" + DEFAULT_ZZ + "," + UPDATED_ZZ);

        // Get all the classreportList where zz equals to UPDATED_ZZ
        defaultClassreportShouldNotBeFound("zz.in=" + UPDATED_ZZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zz is not null
        defaultClassreportShouldBeFound("zz.specified=true");

        // Get all the classreportList where zz is null
        defaultClassreportShouldNotBeFound("zz.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByZzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zz is greater than or equal to DEFAULT_ZZ
        defaultClassreportShouldBeFound("zz.greaterThanOrEqual=" + DEFAULT_ZZ);

        // Get all the classreportList where zz is greater than or equal to UPDATED_ZZ
        defaultClassreportShouldNotBeFound("zz.greaterThanOrEqual=" + UPDATED_ZZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zz is less than or equal to DEFAULT_ZZ
        defaultClassreportShouldBeFound("zz.lessThanOrEqual=" + DEFAULT_ZZ);

        // Get all the classreportList where zz is less than or equal to SMALLER_ZZ
        defaultClassreportShouldNotBeFound("zz.lessThanOrEqual=" + SMALLER_ZZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zz is less than DEFAULT_ZZ
        defaultClassreportShouldNotBeFound("zz.lessThan=" + DEFAULT_ZZ);

        // Get all the classreportList where zz is less than UPDATED_ZZ
        defaultClassreportShouldBeFound("zz.lessThan=" + UPDATED_ZZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zz is greater than DEFAULT_ZZ
        defaultClassreportShouldNotBeFound("zz.greaterThan=" + DEFAULT_ZZ);

        // Get all the classreportList where zz is greater than SMALLER_ZZ
        defaultClassreportShouldBeFound("zz.greaterThan=" + SMALLER_ZZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzYjIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzYj equals to DEFAULT_ZZ_YJ
        defaultClassreportShouldBeFound("zzYj.equals=" + DEFAULT_ZZ_YJ);

        // Get all the classreportList where zzYj equals to UPDATED_ZZ_YJ
        defaultClassreportShouldNotBeFound("zzYj.equals=" + UPDATED_ZZ_YJ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzYjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzYj not equals to DEFAULT_ZZ_YJ
        defaultClassreportShouldNotBeFound("zzYj.notEquals=" + DEFAULT_ZZ_YJ);

        // Get all the classreportList where zzYj not equals to UPDATED_ZZ_YJ
        defaultClassreportShouldBeFound("zzYj.notEquals=" + UPDATED_ZZ_YJ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzYjIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzYj in DEFAULT_ZZ_YJ or UPDATED_ZZ_YJ
        defaultClassreportShouldBeFound("zzYj.in=" + DEFAULT_ZZ_YJ + "," + UPDATED_ZZ_YJ);

        // Get all the classreportList where zzYj equals to UPDATED_ZZ_YJ
        defaultClassreportShouldNotBeFound("zzYj.in=" + UPDATED_ZZ_YJ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzYjIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzYj is not null
        defaultClassreportShouldBeFound("zzYj.specified=true");

        // Get all the classreportList where zzYj is null
        defaultClassreportShouldNotBeFound("zzYj.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByZzYjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzYj is greater than or equal to DEFAULT_ZZ_YJ
        defaultClassreportShouldBeFound("zzYj.greaterThanOrEqual=" + DEFAULT_ZZ_YJ);

        // Get all the classreportList where zzYj is greater than or equal to UPDATED_ZZ_YJ
        defaultClassreportShouldNotBeFound("zzYj.greaterThanOrEqual=" + UPDATED_ZZ_YJ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzYjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzYj is less than or equal to DEFAULT_ZZ_YJ
        defaultClassreportShouldBeFound("zzYj.lessThanOrEqual=" + DEFAULT_ZZ_YJ);

        // Get all the classreportList where zzYj is less than or equal to SMALLER_ZZ_YJ
        defaultClassreportShouldNotBeFound("zzYj.lessThanOrEqual=" + SMALLER_ZZ_YJ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzYjIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzYj is less than DEFAULT_ZZ_YJ
        defaultClassreportShouldNotBeFound("zzYj.lessThan=" + DEFAULT_ZZ_YJ);

        // Get all the classreportList where zzYj is less than UPDATED_ZZ_YJ
        defaultClassreportShouldBeFound("zzYj.lessThan=" + UPDATED_ZZ_YJ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzYjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzYj is greater than DEFAULT_ZZ_YJ
        defaultClassreportShouldNotBeFound("zzYj.greaterThan=" + DEFAULT_ZZ_YJ);

        // Get all the classreportList where zzYj is greater than SMALLER_ZZ_YJ
        defaultClassreportShouldBeFound("zzYj.greaterThan=" + SMALLER_ZZ_YJ);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzJsIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzJs equals to DEFAULT_ZZ_JS
        defaultClassreportShouldBeFound("zzJs.equals=" + DEFAULT_ZZ_JS);

        // Get all the classreportList where zzJs equals to UPDATED_ZZ_JS
        defaultClassreportShouldNotBeFound("zzJs.equals=" + UPDATED_ZZ_JS);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzJsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzJs not equals to DEFAULT_ZZ_JS
        defaultClassreportShouldNotBeFound("zzJs.notEquals=" + DEFAULT_ZZ_JS);

        // Get all the classreportList where zzJs not equals to UPDATED_ZZ_JS
        defaultClassreportShouldBeFound("zzJs.notEquals=" + UPDATED_ZZ_JS);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzJsIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzJs in DEFAULT_ZZ_JS or UPDATED_ZZ_JS
        defaultClassreportShouldBeFound("zzJs.in=" + DEFAULT_ZZ_JS + "," + UPDATED_ZZ_JS);

        // Get all the classreportList where zzJs equals to UPDATED_ZZ_JS
        defaultClassreportShouldNotBeFound("zzJs.in=" + UPDATED_ZZ_JS);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzJsIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzJs is not null
        defaultClassreportShouldBeFound("zzJs.specified=true");

        // Get all the classreportList where zzJs is null
        defaultClassreportShouldNotBeFound("zzJs.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByZzJsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzJs is greater than or equal to DEFAULT_ZZ_JS
        defaultClassreportShouldBeFound("zzJs.greaterThanOrEqual=" + DEFAULT_ZZ_JS);

        // Get all the classreportList where zzJs is greater than or equal to UPDATED_ZZ_JS
        defaultClassreportShouldNotBeFound("zzJs.greaterThanOrEqual=" + UPDATED_ZZ_JS);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzJsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzJs is less than or equal to DEFAULT_ZZ_JS
        defaultClassreportShouldBeFound("zzJs.lessThanOrEqual=" + DEFAULT_ZZ_JS);

        // Get all the classreportList where zzJs is less than or equal to SMALLER_ZZ_JS
        defaultClassreportShouldNotBeFound("zzJs.lessThanOrEqual=" + SMALLER_ZZ_JS);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzJsIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzJs is less than DEFAULT_ZZ_JS
        defaultClassreportShouldNotBeFound("zzJs.lessThan=" + DEFAULT_ZZ_JS);

        // Get all the classreportList where zzJs is less than UPDATED_ZZ_JS
        defaultClassreportShouldBeFound("zzJs.lessThan=" + UPDATED_ZZ_JS);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzJsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzJs is greater than DEFAULT_ZZ_JS
        defaultClassreportShouldNotBeFound("zzJs.greaterThan=" + DEFAULT_ZZ_JS);

        // Get all the classreportList where zzJs is greater than SMALLER_ZZ_JS
        defaultClassreportShouldBeFound("zzJs.greaterThan=" + SMALLER_ZZ_JS);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzTcIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzTc equals to DEFAULT_ZZ_TC
        defaultClassreportShouldBeFound("zzTc.equals=" + DEFAULT_ZZ_TC);

        // Get all the classreportList where zzTc equals to UPDATED_ZZ_TC
        defaultClassreportShouldNotBeFound("zzTc.equals=" + UPDATED_ZZ_TC);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzTcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzTc not equals to DEFAULT_ZZ_TC
        defaultClassreportShouldNotBeFound("zzTc.notEquals=" + DEFAULT_ZZ_TC);

        // Get all the classreportList where zzTc not equals to UPDATED_ZZ_TC
        defaultClassreportShouldBeFound("zzTc.notEquals=" + UPDATED_ZZ_TC);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzTcIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzTc in DEFAULT_ZZ_TC or UPDATED_ZZ_TC
        defaultClassreportShouldBeFound("zzTc.in=" + DEFAULT_ZZ_TC + "," + UPDATED_ZZ_TC);

        // Get all the classreportList where zzTc equals to UPDATED_ZZ_TC
        defaultClassreportShouldNotBeFound("zzTc.in=" + UPDATED_ZZ_TC);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzTcIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzTc is not null
        defaultClassreportShouldBeFound("zzTc.specified=true");

        // Get all the classreportList where zzTc is null
        defaultClassreportShouldNotBeFound("zzTc.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByZzTcIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzTc is greater than or equal to DEFAULT_ZZ_TC
        defaultClassreportShouldBeFound("zzTc.greaterThanOrEqual=" + DEFAULT_ZZ_TC);

        // Get all the classreportList where zzTc is greater than or equal to UPDATED_ZZ_TC
        defaultClassreportShouldNotBeFound("zzTc.greaterThanOrEqual=" + UPDATED_ZZ_TC);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzTcIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzTc is less than or equal to DEFAULT_ZZ_TC
        defaultClassreportShouldBeFound("zzTc.lessThanOrEqual=" + DEFAULT_ZZ_TC);

        // Get all the classreportList where zzTc is less than or equal to SMALLER_ZZ_TC
        defaultClassreportShouldNotBeFound("zzTc.lessThanOrEqual=" + SMALLER_ZZ_TC);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzTcIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzTc is less than DEFAULT_ZZ_TC
        defaultClassreportShouldNotBeFound("zzTc.lessThan=" + DEFAULT_ZZ_TC);

        // Get all the classreportList where zzTc is less than UPDATED_ZZ_TC
        defaultClassreportShouldBeFound("zzTc.lessThan=" + UPDATED_ZZ_TC);
    }

    @Test
    @Transactional
    void getAllClassreportsByZzTcIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where zzTc is greater than DEFAULT_ZZ_TC
        defaultClassreportShouldNotBeFound("zzTc.greaterThan=" + DEFAULT_ZZ_TC);

        // Get all the classreportList where zzTc is greater than SMALLER_ZZ_TC
        defaultClassreportShouldBeFound("zzTc.greaterThan=" + SMALLER_ZZ_TC);
    }

    @Test
    @Transactional
    void getAllClassreportsByFfIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where ff equals to DEFAULT_FF
        defaultClassreportShouldBeFound("ff.equals=" + DEFAULT_FF);

        // Get all the classreportList where ff equals to UPDATED_FF
        defaultClassreportShouldNotBeFound("ff.equals=" + UPDATED_FF);
    }

    @Test
    @Transactional
    void getAllClassreportsByFfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where ff not equals to DEFAULT_FF
        defaultClassreportShouldNotBeFound("ff.notEquals=" + DEFAULT_FF);

        // Get all the classreportList where ff not equals to UPDATED_FF
        defaultClassreportShouldBeFound("ff.notEquals=" + UPDATED_FF);
    }

    @Test
    @Transactional
    void getAllClassreportsByFfIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where ff in DEFAULT_FF or UPDATED_FF
        defaultClassreportShouldBeFound("ff.in=" + DEFAULT_FF + "," + UPDATED_FF);

        // Get all the classreportList where ff equals to UPDATED_FF
        defaultClassreportShouldNotBeFound("ff.in=" + UPDATED_FF);
    }

    @Test
    @Transactional
    void getAllClassreportsByFfIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where ff is not null
        defaultClassreportShouldBeFound("ff.specified=true");

        // Get all the classreportList where ff is null
        defaultClassreportShouldNotBeFound("ff.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByFfIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where ff is greater than or equal to DEFAULT_FF
        defaultClassreportShouldBeFound("ff.greaterThanOrEqual=" + DEFAULT_FF);

        // Get all the classreportList where ff is greater than or equal to UPDATED_FF
        defaultClassreportShouldNotBeFound("ff.greaterThanOrEqual=" + UPDATED_FF);
    }

    @Test
    @Transactional
    void getAllClassreportsByFfIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where ff is less than or equal to DEFAULT_FF
        defaultClassreportShouldBeFound("ff.lessThanOrEqual=" + DEFAULT_FF);

        // Get all the classreportList where ff is less than or equal to SMALLER_FF
        defaultClassreportShouldNotBeFound("ff.lessThanOrEqual=" + SMALLER_FF);
    }

    @Test
    @Transactional
    void getAllClassreportsByFfIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where ff is less than DEFAULT_FF
        defaultClassreportShouldNotBeFound("ff.lessThan=" + DEFAULT_FF);

        // Get all the classreportList where ff is less than UPDATED_FF
        defaultClassreportShouldBeFound("ff.lessThan=" + UPDATED_FF);
    }

    @Test
    @Transactional
    void getAllClassreportsByFfIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where ff is greater than DEFAULT_FF
        defaultClassreportShouldNotBeFound("ff.greaterThan=" + DEFAULT_FF);

        // Get all the classreportList where ff is greater than SMALLER_FF
        defaultClassreportShouldBeFound("ff.greaterThan=" + SMALLER_FF);
    }

    @Test
    @Transactional
    void getAllClassreportsByMinibarIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where minibar equals to DEFAULT_MINIBAR
        defaultClassreportShouldBeFound("minibar.equals=" + DEFAULT_MINIBAR);

        // Get all the classreportList where minibar equals to UPDATED_MINIBAR
        defaultClassreportShouldNotBeFound("minibar.equals=" + UPDATED_MINIBAR);
    }

    @Test
    @Transactional
    void getAllClassreportsByMinibarIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where minibar not equals to DEFAULT_MINIBAR
        defaultClassreportShouldNotBeFound("minibar.notEquals=" + DEFAULT_MINIBAR);

        // Get all the classreportList where minibar not equals to UPDATED_MINIBAR
        defaultClassreportShouldBeFound("minibar.notEquals=" + UPDATED_MINIBAR);
    }

    @Test
    @Transactional
    void getAllClassreportsByMinibarIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where minibar in DEFAULT_MINIBAR or UPDATED_MINIBAR
        defaultClassreportShouldBeFound("minibar.in=" + DEFAULT_MINIBAR + "," + UPDATED_MINIBAR);

        // Get all the classreportList where minibar equals to UPDATED_MINIBAR
        defaultClassreportShouldNotBeFound("minibar.in=" + UPDATED_MINIBAR);
    }

    @Test
    @Transactional
    void getAllClassreportsByMinibarIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where minibar is not null
        defaultClassreportShouldBeFound("minibar.specified=true");

        // Get all the classreportList where minibar is null
        defaultClassreportShouldNotBeFound("minibar.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByMinibarIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where minibar is greater than or equal to DEFAULT_MINIBAR
        defaultClassreportShouldBeFound("minibar.greaterThanOrEqual=" + DEFAULT_MINIBAR);

        // Get all the classreportList where minibar is greater than or equal to UPDATED_MINIBAR
        defaultClassreportShouldNotBeFound("minibar.greaterThanOrEqual=" + UPDATED_MINIBAR);
    }

    @Test
    @Transactional
    void getAllClassreportsByMinibarIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where minibar is less than or equal to DEFAULT_MINIBAR
        defaultClassreportShouldBeFound("minibar.lessThanOrEqual=" + DEFAULT_MINIBAR);

        // Get all the classreportList where minibar is less than or equal to SMALLER_MINIBAR
        defaultClassreportShouldNotBeFound("minibar.lessThanOrEqual=" + SMALLER_MINIBAR);
    }

    @Test
    @Transactional
    void getAllClassreportsByMinibarIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where minibar is less than DEFAULT_MINIBAR
        defaultClassreportShouldNotBeFound("minibar.lessThan=" + DEFAULT_MINIBAR);

        // Get all the classreportList where minibar is less than UPDATED_MINIBAR
        defaultClassreportShouldBeFound("minibar.lessThan=" + UPDATED_MINIBAR);
    }

    @Test
    @Transactional
    void getAllClassreportsByMinibarIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where minibar is greater than DEFAULT_MINIBAR
        defaultClassreportShouldNotBeFound("minibar.greaterThan=" + DEFAULT_MINIBAR);

        // Get all the classreportList where minibar is greater than SMALLER_MINIBAR
        defaultClassreportShouldBeFound("minibar.greaterThan=" + SMALLER_MINIBAR);
    }

    @Test
    @Transactional
    void getAllClassreportsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where phone equals to DEFAULT_PHONE
        defaultClassreportShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the classreportList where phone equals to UPDATED_PHONE
        defaultClassreportShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllClassreportsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where phone not equals to DEFAULT_PHONE
        defaultClassreportShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the classreportList where phone not equals to UPDATED_PHONE
        defaultClassreportShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllClassreportsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultClassreportShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the classreportList where phone equals to UPDATED_PHONE
        defaultClassreportShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllClassreportsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where phone is not null
        defaultClassreportShouldBeFound("phone.specified=true");

        // Get all the classreportList where phone is null
        defaultClassreportShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByPhoneIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where phone is greater than or equal to DEFAULT_PHONE
        defaultClassreportShouldBeFound("phone.greaterThanOrEqual=" + DEFAULT_PHONE);

        // Get all the classreportList where phone is greater than or equal to UPDATED_PHONE
        defaultClassreportShouldNotBeFound("phone.greaterThanOrEqual=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllClassreportsByPhoneIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where phone is less than or equal to DEFAULT_PHONE
        defaultClassreportShouldBeFound("phone.lessThanOrEqual=" + DEFAULT_PHONE);

        // Get all the classreportList where phone is less than or equal to SMALLER_PHONE
        defaultClassreportShouldNotBeFound("phone.lessThanOrEqual=" + SMALLER_PHONE);
    }

    @Test
    @Transactional
    void getAllClassreportsByPhoneIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where phone is less than DEFAULT_PHONE
        defaultClassreportShouldNotBeFound("phone.lessThan=" + DEFAULT_PHONE);

        // Get all the classreportList where phone is less than UPDATED_PHONE
        defaultClassreportShouldBeFound("phone.lessThan=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllClassreportsByPhoneIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where phone is greater than DEFAULT_PHONE
        defaultClassreportShouldNotBeFound("phone.greaterThan=" + DEFAULT_PHONE);

        // Get all the classreportList where phone is greater than SMALLER_PHONE
        defaultClassreportShouldBeFound("phone.greaterThan=" + SMALLER_PHONE);
    }

    @Test
    @Transactional
    void getAllClassreportsByOtherIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where other equals to DEFAULT_OTHER
        defaultClassreportShouldBeFound("other.equals=" + DEFAULT_OTHER);

        // Get all the classreportList where other equals to UPDATED_OTHER
        defaultClassreportShouldNotBeFound("other.equals=" + UPDATED_OTHER);
    }

    @Test
    @Transactional
    void getAllClassreportsByOtherIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where other not equals to DEFAULT_OTHER
        defaultClassreportShouldNotBeFound("other.notEquals=" + DEFAULT_OTHER);

        // Get all the classreportList where other not equals to UPDATED_OTHER
        defaultClassreportShouldBeFound("other.notEquals=" + UPDATED_OTHER);
    }

    @Test
    @Transactional
    void getAllClassreportsByOtherIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where other in DEFAULT_OTHER or UPDATED_OTHER
        defaultClassreportShouldBeFound("other.in=" + DEFAULT_OTHER + "," + UPDATED_OTHER);

        // Get all the classreportList where other equals to UPDATED_OTHER
        defaultClassreportShouldNotBeFound("other.in=" + UPDATED_OTHER);
    }

    @Test
    @Transactional
    void getAllClassreportsByOtherIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where other is not null
        defaultClassreportShouldBeFound("other.specified=true");

        // Get all the classreportList where other is null
        defaultClassreportShouldNotBeFound("other.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByOtherIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where other is greater than or equal to DEFAULT_OTHER
        defaultClassreportShouldBeFound("other.greaterThanOrEqual=" + DEFAULT_OTHER);

        // Get all the classreportList where other is greater than or equal to UPDATED_OTHER
        defaultClassreportShouldNotBeFound("other.greaterThanOrEqual=" + UPDATED_OTHER);
    }

    @Test
    @Transactional
    void getAllClassreportsByOtherIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where other is less than or equal to DEFAULT_OTHER
        defaultClassreportShouldBeFound("other.lessThanOrEqual=" + DEFAULT_OTHER);

        // Get all the classreportList where other is less than or equal to SMALLER_OTHER
        defaultClassreportShouldNotBeFound("other.lessThanOrEqual=" + SMALLER_OTHER);
    }

    @Test
    @Transactional
    void getAllClassreportsByOtherIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where other is less than DEFAULT_OTHER
        defaultClassreportShouldNotBeFound("other.lessThan=" + DEFAULT_OTHER);

        // Get all the classreportList where other is less than UPDATED_OTHER
        defaultClassreportShouldBeFound("other.lessThan=" + UPDATED_OTHER);
    }

    @Test
    @Transactional
    void getAllClassreportsByOtherIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where other is greater than DEFAULT_OTHER
        defaultClassreportShouldNotBeFound("other.greaterThan=" + DEFAULT_OTHER);

        // Get all the classreportList where other is greater than SMALLER_OTHER
        defaultClassreportShouldBeFound("other.greaterThan=" + SMALLER_OTHER);
    }

    @Test
    @Transactional
    void getAllClassreportsByPcIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where pc equals to DEFAULT_PC
        defaultClassreportShouldBeFound("pc.equals=" + DEFAULT_PC);

        // Get all the classreportList where pc equals to UPDATED_PC
        defaultClassreportShouldNotBeFound("pc.equals=" + UPDATED_PC);
    }

    @Test
    @Transactional
    void getAllClassreportsByPcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where pc not equals to DEFAULT_PC
        defaultClassreportShouldNotBeFound("pc.notEquals=" + DEFAULT_PC);

        // Get all the classreportList where pc not equals to UPDATED_PC
        defaultClassreportShouldBeFound("pc.notEquals=" + UPDATED_PC);
    }

    @Test
    @Transactional
    void getAllClassreportsByPcIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where pc in DEFAULT_PC or UPDATED_PC
        defaultClassreportShouldBeFound("pc.in=" + DEFAULT_PC + "," + UPDATED_PC);

        // Get all the classreportList where pc equals to UPDATED_PC
        defaultClassreportShouldNotBeFound("pc.in=" + UPDATED_PC);
    }

    @Test
    @Transactional
    void getAllClassreportsByPcIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where pc is not null
        defaultClassreportShouldBeFound("pc.specified=true");

        // Get all the classreportList where pc is null
        defaultClassreportShouldNotBeFound("pc.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByPcIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where pc is greater than or equal to DEFAULT_PC
        defaultClassreportShouldBeFound("pc.greaterThanOrEqual=" + DEFAULT_PC);

        // Get all the classreportList where pc is greater than or equal to UPDATED_PC
        defaultClassreportShouldNotBeFound("pc.greaterThanOrEqual=" + UPDATED_PC);
    }

    @Test
    @Transactional
    void getAllClassreportsByPcIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where pc is less than or equal to DEFAULT_PC
        defaultClassreportShouldBeFound("pc.lessThanOrEqual=" + DEFAULT_PC);

        // Get all the classreportList where pc is less than or equal to SMALLER_PC
        defaultClassreportShouldNotBeFound("pc.lessThanOrEqual=" + SMALLER_PC);
    }

    @Test
    @Transactional
    void getAllClassreportsByPcIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where pc is less than DEFAULT_PC
        defaultClassreportShouldNotBeFound("pc.lessThan=" + DEFAULT_PC);

        // Get all the classreportList where pc is less than UPDATED_PC
        defaultClassreportShouldBeFound("pc.lessThan=" + UPDATED_PC);
    }

    @Test
    @Transactional
    void getAllClassreportsByPcIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where pc is greater than DEFAULT_PC
        defaultClassreportShouldNotBeFound("pc.greaterThan=" + DEFAULT_PC);

        // Get all the classreportList where pc is greater than SMALLER_PC
        defaultClassreportShouldBeFound("pc.greaterThan=" + SMALLER_PC);
    }

    @Test
    @Transactional
    void getAllClassreportsByCzIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cz equals to DEFAULT_CZ
        defaultClassreportShouldBeFound("cz.equals=" + DEFAULT_CZ);

        // Get all the classreportList where cz equals to UPDATED_CZ
        defaultClassreportShouldNotBeFound("cz.equals=" + UPDATED_CZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByCzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cz not equals to DEFAULT_CZ
        defaultClassreportShouldNotBeFound("cz.notEquals=" + DEFAULT_CZ);

        // Get all the classreportList where cz not equals to UPDATED_CZ
        defaultClassreportShouldBeFound("cz.notEquals=" + UPDATED_CZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByCzIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cz in DEFAULT_CZ or UPDATED_CZ
        defaultClassreportShouldBeFound("cz.in=" + DEFAULT_CZ + "," + UPDATED_CZ);

        // Get all the classreportList where cz equals to UPDATED_CZ
        defaultClassreportShouldNotBeFound("cz.in=" + UPDATED_CZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByCzIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cz is not null
        defaultClassreportShouldBeFound("cz.specified=true");

        // Get all the classreportList where cz is null
        defaultClassreportShouldNotBeFound("cz.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByCzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cz is greater than or equal to DEFAULT_CZ
        defaultClassreportShouldBeFound("cz.greaterThanOrEqual=" + DEFAULT_CZ);

        // Get all the classreportList where cz is greater than or equal to UPDATED_CZ
        defaultClassreportShouldNotBeFound("cz.greaterThanOrEqual=" + UPDATED_CZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByCzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cz is less than or equal to DEFAULT_CZ
        defaultClassreportShouldBeFound("cz.lessThanOrEqual=" + DEFAULT_CZ);

        // Get all the classreportList where cz is less than or equal to SMALLER_CZ
        defaultClassreportShouldNotBeFound("cz.lessThanOrEqual=" + SMALLER_CZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByCzIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cz is less than DEFAULT_CZ
        defaultClassreportShouldNotBeFound("cz.lessThan=" + DEFAULT_CZ);

        // Get all the classreportList where cz is less than UPDATED_CZ
        defaultClassreportShouldBeFound("cz.lessThan=" + UPDATED_CZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByCzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cz is greater than DEFAULT_CZ
        defaultClassreportShouldNotBeFound("cz.greaterThan=" + DEFAULT_CZ);

        // Get all the classreportList where cz is greater than SMALLER_CZ
        defaultClassreportShouldBeFound("cz.greaterThan=" + SMALLER_CZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByCyIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cy equals to DEFAULT_CY
        defaultClassreportShouldBeFound("cy.equals=" + DEFAULT_CY);

        // Get all the classreportList where cy equals to UPDATED_CY
        defaultClassreportShouldNotBeFound("cy.equals=" + UPDATED_CY);
    }

    @Test
    @Transactional
    void getAllClassreportsByCyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cy not equals to DEFAULT_CY
        defaultClassreportShouldNotBeFound("cy.notEquals=" + DEFAULT_CY);

        // Get all the classreportList where cy not equals to UPDATED_CY
        defaultClassreportShouldBeFound("cy.notEquals=" + UPDATED_CY);
    }

    @Test
    @Transactional
    void getAllClassreportsByCyIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cy in DEFAULT_CY or UPDATED_CY
        defaultClassreportShouldBeFound("cy.in=" + DEFAULT_CY + "," + UPDATED_CY);

        // Get all the classreportList where cy equals to UPDATED_CY
        defaultClassreportShouldNotBeFound("cy.in=" + UPDATED_CY);
    }

    @Test
    @Transactional
    void getAllClassreportsByCyIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cy is not null
        defaultClassreportShouldBeFound("cy.specified=true");

        // Get all the classreportList where cy is null
        defaultClassreportShouldNotBeFound("cy.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByCyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cy is greater than or equal to DEFAULT_CY
        defaultClassreportShouldBeFound("cy.greaterThanOrEqual=" + DEFAULT_CY);

        // Get all the classreportList where cy is greater than or equal to UPDATED_CY
        defaultClassreportShouldNotBeFound("cy.greaterThanOrEqual=" + UPDATED_CY);
    }

    @Test
    @Transactional
    void getAllClassreportsByCyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cy is less than or equal to DEFAULT_CY
        defaultClassreportShouldBeFound("cy.lessThanOrEqual=" + DEFAULT_CY);

        // Get all the classreportList where cy is less than or equal to SMALLER_CY
        defaultClassreportShouldNotBeFound("cy.lessThanOrEqual=" + SMALLER_CY);
    }

    @Test
    @Transactional
    void getAllClassreportsByCyIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cy is less than DEFAULT_CY
        defaultClassreportShouldNotBeFound("cy.lessThan=" + DEFAULT_CY);

        // Get all the classreportList where cy is less than UPDATED_CY
        defaultClassreportShouldBeFound("cy.lessThan=" + UPDATED_CY);
    }

    @Test
    @Transactional
    void getAllClassreportsByCyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cy is greater than DEFAULT_CY
        defaultClassreportShouldNotBeFound("cy.greaterThan=" + DEFAULT_CY);

        // Get all the classreportList where cy is greater than SMALLER_CY
        defaultClassreportShouldBeFound("cy.greaterThan=" + SMALLER_CY);
    }

    @Test
    @Transactional
    void getAllClassreportsByMdIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where md equals to DEFAULT_MD
        defaultClassreportShouldBeFound("md.equals=" + DEFAULT_MD);

        // Get all the classreportList where md equals to UPDATED_MD
        defaultClassreportShouldNotBeFound("md.equals=" + UPDATED_MD);
    }

    @Test
    @Transactional
    void getAllClassreportsByMdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where md not equals to DEFAULT_MD
        defaultClassreportShouldNotBeFound("md.notEquals=" + DEFAULT_MD);

        // Get all the classreportList where md not equals to UPDATED_MD
        defaultClassreportShouldBeFound("md.notEquals=" + UPDATED_MD);
    }

    @Test
    @Transactional
    void getAllClassreportsByMdIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where md in DEFAULT_MD or UPDATED_MD
        defaultClassreportShouldBeFound("md.in=" + DEFAULT_MD + "," + UPDATED_MD);

        // Get all the classreportList where md equals to UPDATED_MD
        defaultClassreportShouldNotBeFound("md.in=" + UPDATED_MD);
    }

    @Test
    @Transactional
    void getAllClassreportsByMdIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where md is not null
        defaultClassreportShouldBeFound("md.specified=true");

        // Get all the classreportList where md is null
        defaultClassreportShouldNotBeFound("md.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByMdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where md is greater than or equal to DEFAULT_MD
        defaultClassreportShouldBeFound("md.greaterThanOrEqual=" + DEFAULT_MD);

        // Get all the classreportList where md is greater than or equal to UPDATED_MD
        defaultClassreportShouldNotBeFound("md.greaterThanOrEqual=" + UPDATED_MD);
    }

    @Test
    @Transactional
    void getAllClassreportsByMdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where md is less than or equal to DEFAULT_MD
        defaultClassreportShouldBeFound("md.lessThanOrEqual=" + DEFAULT_MD);

        // Get all the classreportList where md is less than or equal to SMALLER_MD
        defaultClassreportShouldNotBeFound("md.lessThanOrEqual=" + SMALLER_MD);
    }

    @Test
    @Transactional
    void getAllClassreportsByMdIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where md is less than DEFAULT_MD
        defaultClassreportShouldNotBeFound("md.lessThan=" + DEFAULT_MD);

        // Get all the classreportList where md is less than UPDATED_MD
        defaultClassreportShouldBeFound("md.lessThan=" + UPDATED_MD);
    }

    @Test
    @Transactional
    void getAllClassreportsByMdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where md is greater than DEFAULT_MD
        defaultClassreportShouldNotBeFound("md.greaterThan=" + DEFAULT_MD);

        // Get all the classreportList where md is greater than SMALLER_MD
        defaultClassreportShouldBeFound("md.greaterThan=" + SMALLER_MD);
    }

    @Test
    @Transactional
    void getAllClassreportsByHuiyIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where huiy equals to DEFAULT_HUIY
        defaultClassreportShouldBeFound("huiy.equals=" + DEFAULT_HUIY);

        // Get all the classreportList where huiy equals to UPDATED_HUIY
        defaultClassreportShouldNotBeFound("huiy.equals=" + UPDATED_HUIY);
    }

    @Test
    @Transactional
    void getAllClassreportsByHuiyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where huiy not equals to DEFAULT_HUIY
        defaultClassreportShouldNotBeFound("huiy.notEquals=" + DEFAULT_HUIY);

        // Get all the classreportList where huiy not equals to UPDATED_HUIY
        defaultClassreportShouldBeFound("huiy.notEquals=" + UPDATED_HUIY);
    }

    @Test
    @Transactional
    void getAllClassreportsByHuiyIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where huiy in DEFAULT_HUIY or UPDATED_HUIY
        defaultClassreportShouldBeFound("huiy.in=" + DEFAULT_HUIY + "," + UPDATED_HUIY);

        // Get all the classreportList where huiy equals to UPDATED_HUIY
        defaultClassreportShouldNotBeFound("huiy.in=" + UPDATED_HUIY);
    }

    @Test
    @Transactional
    void getAllClassreportsByHuiyIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where huiy is not null
        defaultClassreportShouldBeFound("huiy.specified=true");

        // Get all the classreportList where huiy is null
        defaultClassreportShouldNotBeFound("huiy.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByHuiyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where huiy is greater than or equal to DEFAULT_HUIY
        defaultClassreportShouldBeFound("huiy.greaterThanOrEqual=" + DEFAULT_HUIY);

        // Get all the classreportList where huiy is greater than or equal to UPDATED_HUIY
        defaultClassreportShouldNotBeFound("huiy.greaterThanOrEqual=" + UPDATED_HUIY);
    }

    @Test
    @Transactional
    void getAllClassreportsByHuiyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where huiy is less than or equal to DEFAULT_HUIY
        defaultClassreportShouldBeFound("huiy.lessThanOrEqual=" + DEFAULT_HUIY);

        // Get all the classreportList where huiy is less than or equal to SMALLER_HUIY
        defaultClassreportShouldNotBeFound("huiy.lessThanOrEqual=" + SMALLER_HUIY);
    }

    @Test
    @Transactional
    void getAllClassreportsByHuiyIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where huiy is less than DEFAULT_HUIY
        defaultClassreportShouldNotBeFound("huiy.lessThan=" + DEFAULT_HUIY);

        // Get all the classreportList where huiy is less than UPDATED_HUIY
        defaultClassreportShouldBeFound("huiy.lessThan=" + UPDATED_HUIY);
    }

    @Test
    @Transactional
    void getAllClassreportsByHuiyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where huiy is greater than DEFAULT_HUIY
        defaultClassreportShouldNotBeFound("huiy.greaterThan=" + DEFAULT_HUIY);

        // Get all the classreportList where huiy is greater than SMALLER_HUIY
        defaultClassreportShouldBeFound("huiy.greaterThan=" + SMALLER_HUIY);
    }

    @Test
    @Transactional
    void getAllClassreportsByDtbIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where dtb equals to DEFAULT_DTB
        defaultClassreportShouldBeFound("dtb.equals=" + DEFAULT_DTB);

        // Get all the classreportList where dtb equals to UPDATED_DTB
        defaultClassreportShouldNotBeFound("dtb.equals=" + UPDATED_DTB);
    }

    @Test
    @Transactional
    void getAllClassreportsByDtbIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where dtb not equals to DEFAULT_DTB
        defaultClassreportShouldNotBeFound("dtb.notEquals=" + DEFAULT_DTB);

        // Get all the classreportList where dtb not equals to UPDATED_DTB
        defaultClassreportShouldBeFound("dtb.notEquals=" + UPDATED_DTB);
    }

    @Test
    @Transactional
    void getAllClassreportsByDtbIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where dtb in DEFAULT_DTB or UPDATED_DTB
        defaultClassreportShouldBeFound("dtb.in=" + DEFAULT_DTB + "," + UPDATED_DTB);

        // Get all the classreportList where dtb equals to UPDATED_DTB
        defaultClassreportShouldNotBeFound("dtb.in=" + UPDATED_DTB);
    }

    @Test
    @Transactional
    void getAllClassreportsByDtbIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where dtb is not null
        defaultClassreportShouldBeFound("dtb.specified=true");

        // Get all the classreportList where dtb is null
        defaultClassreportShouldNotBeFound("dtb.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByDtbIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where dtb is greater than or equal to DEFAULT_DTB
        defaultClassreportShouldBeFound("dtb.greaterThanOrEqual=" + DEFAULT_DTB);

        // Get all the classreportList where dtb is greater than or equal to UPDATED_DTB
        defaultClassreportShouldNotBeFound("dtb.greaterThanOrEqual=" + UPDATED_DTB);
    }

    @Test
    @Transactional
    void getAllClassreportsByDtbIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where dtb is less than or equal to DEFAULT_DTB
        defaultClassreportShouldBeFound("dtb.lessThanOrEqual=" + DEFAULT_DTB);

        // Get all the classreportList where dtb is less than or equal to SMALLER_DTB
        defaultClassreportShouldNotBeFound("dtb.lessThanOrEqual=" + SMALLER_DTB);
    }

    @Test
    @Transactional
    void getAllClassreportsByDtbIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where dtb is less than DEFAULT_DTB
        defaultClassreportShouldNotBeFound("dtb.lessThan=" + DEFAULT_DTB);

        // Get all the classreportList where dtb is less than UPDATED_DTB
        defaultClassreportShouldBeFound("dtb.lessThan=" + UPDATED_DTB);
    }

    @Test
    @Transactional
    void getAllClassreportsByDtbIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where dtb is greater than DEFAULT_DTB
        defaultClassreportShouldNotBeFound("dtb.greaterThan=" + DEFAULT_DTB);

        // Get all the classreportList where dtb is greater than SMALLER_DTB
        defaultClassreportShouldBeFound("dtb.greaterThan=" + SMALLER_DTB);
    }

    @Test
    @Transactional
    void getAllClassreportsBySszxIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where sszx equals to DEFAULT_SSZX
        defaultClassreportShouldBeFound("sszx.equals=" + DEFAULT_SSZX);

        // Get all the classreportList where sszx equals to UPDATED_SSZX
        defaultClassreportShouldNotBeFound("sszx.equals=" + UPDATED_SSZX);
    }

    @Test
    @Transactional
    void getAllClassreportsBySszxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where sszx not equals to DEFAULT_SSZX
        defaultClassreportShouldNotBeFound("sszx.notEquals=" + DEFAULT_SSZX);

        // Get all the classreportList where sszx not equals to UPDATED_SSZX
        defaultClassreportShouldBeFound("sszx.notEquals=" + UPDATED_SSZX);
    }

    @Test
    @Transactional
    void getAllClassreportsBySszxIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where sszx in DEFAULT_SSZX or UPDATED_SSZX
        defaultClassreportShouldBeFound("sszx.in=" + DEFAULT_SSZX + "," + UPDATED_SSZX);

        // Get all the classreportList where sszx equals to UPDATED_SSZX
        defaultClassreportShouldNotBeFound("sszx.in=" + UPDATED_SSZX);
    }

    @Test
    @Transactional
    void getAllClassreportsBySszxIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where sszx is not null
        defaultClassreportShouldBeFound("sszx.specified=true");

        // Get all the classreportList where sszx is null
        defaultClassreportShouldNotBeFound("sszx.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsBySszxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where sszx is greater than or equal to DEFAULT_SSZX
        defaultClassreportShouldBeFound("sszx.greaterThanOrEqual=" + DEFAULT_SSZX);

        // Get all the classreportList where sszx is greater than or equal to UPDATED_SSZX
        defaultClassreportShouldNotBeFound("sszx.greaterThanOrEqual=" + UPDATED_SSZX);
    }

    @Test
    @Transactional
    void getAllClassreportsBySszxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where sszx is less than or equal to DEFAULT_SSZX
        defaultClassreportShouldBeFound("sszx.lessThanOrEqual=" + DEFAULT_SSZX);

        // Get all the classreportList where sszx is less than or equal to SMALLER_SSZX
        defaultClassreportShouldNotBeFound("sszx.lessThanOrEqual=" + SMALLER_SSZX);
    }

    @Test
    @Transactional
    void getAllClassreportsBySszxIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where sszx is less than DEFAULT_SSZX
        defaultClassreportShouldNotBeFound("sszx.lessThan=" + DEFAULT_SSZX);

        // Get all the classreportList where sszx is less than UPDATED_SSZX
        defaultClassreportShouldBeFound("sszx.lessThan=" + UPDATED_SSZX);
    }

    @Test
    @Transactional
    void getAllClassreportsBySszxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where sszx is greater than DEFAULT_SSZX
        defaultClassreportShouldNotBeFound("sszx.greaterThan=" + DEFAULT_SSZX);

        // Get all the classreportList where sszx is greater than SMALLER_SSZX
        defaultClassreportShouldBeFound("sszx.greaterThan=" + SMALLER_SSZX);
    }

    @Test
    @Transactional
    void getAllClassreportsByCyzIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cyz equals to DEFAULT_CYZ
        defaultClassreportShouldBeFound("cyz.equals=" + DEFAULT_CYZ);

        // Get all the classreportList where cyz equals to UPDATED_CYZ
        defaultClassreportShouldNotBeFound("cyz.equals=" + UPDATED_CYZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByCyzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cyz not equals to DEFAULT_CYZ
        defaultClassreportShouldNotBeFound("cyz.notEquals=" + DEFAULT_CYZ);

        // Get all the classreportList where cyz not equals to UPDATED_CYZ
        defaultClassreportShouldBeFound("cyz.notEquals=" + UPDATED_CYZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByCyzIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cyz in DEFAULT_CYZ or UPDATED_CYZ
        defaultClassreportShouldBeFound("cyz.in=" + DEFAULT_CYZ + "," + UPDATED_CYZ);

        // Get all the classreportList where cyz equals to UPDATED_CYZ
        defaultClassreportShouldNotBeFound("cyz.in=" + UPDATED_CYZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByCyzIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cyz is not null
        defaultClassreportShouldBeFound("cyz.specified=true");

        // Get all the classreportList where cyz is null
        defaultClassreportShouldNotBeFound("cyz.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByCyzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cyz is greater than or equal to DEFAULT_CYZ
        defaultClassreportShouldBeFound("cyz.greaterThanOrEqual=" + DEFAULT_CYZ);

        // Get all the classreportList where cyz is greater than or equal to UPDATED_CYZ
        defaultClassreportShouldNotBeFound("cyz.greaterThanOrEqual=" + UPDATED_CYZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByCyzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cyz is less than or equal to DEFAULT_CYZ
        defaultClassreportShouldBeFound("cyz.lessThanOrEqual=" + DEFAULT_CYZ);

        // Get all the classreportList where cyz is less than or equal to SMALLER_CYZ
        defaultClassreportShouldNotBeFound("cyz.lessThanOrEqual=" + SMALLER_CYZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByCyzIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cyz is less than DEFAULT_CYZ
        defaultClassreportShouldNotBeFound("cyz.lessThan=" + DEFAULT_CYZ);

        // Get all the classreportList where cyz is less than UPDATED_CYZ
        defaultClassreportShouldBeFound("cyz.lessThan=" + UPDATED_CYZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByCyzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where cyz is greater than DEFAULT_CYZ
        defaultClassreportShouldNotBeFound("cyz.greaterThan=" + DEFAULT_CYZ);

        // Get all the classreportList where cyz is greater than SMALLER_CYZ
        defaultClassreportShouldBeFound("cyz.greaterThan=" + SMALLER_CYZ);
    }

    @Test
    @Transactional
    void getAllClassreportsByHoteldmIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where hoteldm equals to DEFAULT_HOTELDM
        defaultClassreportShouldBeFound("hoteldm.equals=" + DEFAULT_HOTELDM);

        // Get all the classreportList where hoteldm equals to UPDATED_HOTELDM
        defaultClassreportShouldNotBeFound("hoteldm.equals=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllClassreportsByHoteldmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where hoteldm not equals to DEFAULT_HOTELDM
        defaultClassreportShouldNotBeFound("hoteldm.notEquals=" + DEFAULT_HOTELDM);

        // Get all the classreportList where hoteldm not equals to UPDATED_HOTELDM
        defaultClassreportShouldBeFound("hoteldm.notEquals=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllClassreportsByHoteldmIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where hoteldm in DEFAULT_HOTELDM or UPDATED_HOTELDM
        defaultClassreportShouldBeFound("hoteldm.in=" + DEFAULT_HOTELDM + "," + UPDATED_HOTELDM);

        // Get all the classreportList where hoteldm equals to UPDATED_HOTELDM
        defaultClassreportShouldNotBeFound("hoteldm.in=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllClassreportsByHoteldmIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where hoteldm is not null
        defaultClassreportShouldBeFound("hoteldm.specified=true");

        // Get all the classreportList where hoteldm is null
        defaultClassreportShouldNotBeFound("hoteldm.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByHoteldmContainsSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where hoteldm contains DEFAULT_HOTELDM
        defaultClassreportShouldBeFound("hoteldm.contains=" + DEFAULT_HOTELDM);

        // Get all the classreportList where hoteldm contains UPDATED_HOTELDM
        defaultClassreportShouldNotBeFound("hoteldm.contains=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllClassreportsByHoteldmNotContainsSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where hoteldm does not contain DEFAULT_HOTELDM
        defaultClassreportShouldNotBeFound("hoteldm.doesNotContain=" + DEFAULT_HOTELDM);

        // Get all the classreportList where hoteldm does not contain UPDATED_HOTELDM
        defaultClassreportShouldBeFound("hoteldm.doesNotContain=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllClassreportsByGzxjIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gzxj equals to DEFAULT_GZXJ
        defaultClassreportShouldBeFound("gzxj.equals=" + DEFAULT_GZXJ);

        // Get all the classreportList where gzxj equals to UPDATED_GZXJ
        defaultClassreportShouldNotBeFound("gzxj.equals=" + UPDATED_GZXJ);
    }

    @Test
    @Transactional
    void getAllClassreportsByGzxjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gzxj not equals to DEFAULT_GZXJ
        defaultClassreportShouldNotBeFound("gzxj.notEquals=" + DEFAULT_GZXJ);

        // Get all the classreportList where gzxj not equals to UPDATED_GZXJ
        defaultClassreportShouldBeFound("gzxj.notEquals=" + UPDATED_GZXJ);
    }

    @Test
    @Transactional
    void getAllClassreportsByGzxjIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gzxj in DEFAULT_GZXJ or UPDATED_GZXJ
        defaultClassreportShouldBeFound("gzxj.in=" + DEFAULT_GZXJ + "," + UPDATED_GZXJ);

        // Get all the classreportList where gzxj equals to UPDATED_GZXJ
        defaultClassreportShouldNotBeFound("gzxj.in=" + UPDATED_GZXJ);
    }

    @Test
    @Transactional
    void getAllClassreportsByGzxjIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gzxj is not null
        defaultClassreportShouldBeFound("gzxj.specified=true");

        // Get all the classreportList where gzxj is null
        defaultClassreportShouldNotBeFound("gzxj.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByGzxjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gzxj is greater than or equal to DEFAULT_GZXJ
        defaultClassreportShouldBeFound("gzxj.greaterThanOrEqual=" + DEFAULT_GZXJ);

        // Get all the classreportList where gzxj is greater than or equal to UPDATED_GZXJ
        defaultClassreportShouldNotBeFound("gzxj.greaterThanOrEqual=" + UPDATED_GZXJ);
    }

    @Test
    @Transactional
    void getAllClassreportsByGzxjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gzxj is less than or equal to DEFAULT_GZXJ
        defaultClassreportShouldBeFound("gzxj.lessThanOrEqual=" + DEFAULT_GZXJ);

        // Get all the classreportList where gzxj is less than or equal to SMALLER_GZXJ
        defaultClassreportShouldNotBeFound("gzxj.lessThanOrEqual=" + SMALLER_GZXJ);
    }

    @Test
    @Transactional
    void getAllClassreportsByGzxjIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gzxj is less than DEFAULT_GZXJ
        defaultClassreportShouldNotBeFound("gzxj.lessThan=" + DEFAULT_GZXJ);

        // Get all the classreportList where gzxj is less than UPDATED_GZXJ
        defaultClassreportShouldBeFound("gzxj.lessThan=" + UPDATED_GZXJ);
    }

    @Test
    @Transactional
    void getAllClassreportsByGzxjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where gzxj is greater than DEFAULT_GZXJ
        defaultClassreportShouldNotBeFound("gzxj.greaterThan=" + DEFAULT_GZXJ);

        // Get all the classreportList where gzxj is greater than SMALLER_GZXJ
        defaultClassreportShouldBeFound("gzxj.greaterThan=" + SMALLER_GZXJ);
    }

    @Test
    @Transactional
    void getAllClassreportsByIsnewIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where isnew equals to DEFAULT_ISNEW
        defaultClassreportShouldBeFound("isnew.equals=" + DEFAULT_ISNEW);

        // Get all the classreportList where isnew equals to UPDATED_ISNEW
        defaultClassreportShouldNotBeFound("isnew.equals=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllClassreportsByIsnewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where isnew not equals to DEFAULT_ISNEW
        defaultClassreportShouldNotBeFound("isnew.notEquals=" + DEFAULT_ISNEW);

        // Get all the classreportList where isnew not equals to UPDATED_ISNEW
        defaultClassreportShouldBeFound("isnew.notEquals=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllClassreportsByIsnewIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where isnew in DEFAULT_ISNEW or UPDATED_ISNEW
        defaultClassreportShouldBeFound("isnew.in=" + DEFAULT_ISNEW + "," + UPDATED_ISNEW);

        // Get all the classreportList where isnew equals to UPDATED_ISNEW
        defaultClassreportShouldNotBeFound("isnew.in=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllClassreportsByIsnewIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where isnew is not null
        defaultClassreportShouldBeFound("isnew.specified=true");

        // Get all the classreportList where isnew is null
        defaultClassreportShouldNotBeFound("isnew.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportsByIsnewIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where isnew is greater than or equal to DEFAULT_ISNEW
        defaultClassreportShouldBeFound("isnew.greaterThanOrEqual=" + DEFAULT_ISNEW);

        // Get all the classreportList where isnew is greater than or equal to UPDATED_ISNEW
        defaultClassreportShouldNotBeFound("isnew.greaterThanOrEqual=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllClassreportsByIsnewIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where isnew is less than or equal to DEFAULT_ISNEW
        defaultClassreportShouldBeFound("isnew.lessThanOrEqual=" + DEFAULT_ISNEW);

        // Get all the classreportList where isnew is less than or equal to SMALLER_ISNEW
        defaultClassreportShouldNotBeFound("isnew.lessThanOrEqual=" + SMALLER_ISNEW);
    }

    @Test
    @Transactional
    void getAllClassreportsByIsnewIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where isnew is less than DEFAULT_ISNEW
        defaultClassreportShouldNotBeFound("isnew.lessThan=" + DEFAULT_ISNEW);

        // Get all the classreportList where isnew is less than UPDATED_ISNEW
        defaultClassreportShouldBeFound("isnew.lessThan=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllClassreportsByIsnewIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        // Get all the classreportList where isnew is greater than DEFAULT_ISNEW
        defaultClassreportShouldNotBeFound("isnew.greaterThan=" + DEFAULT_ISNEW);

        // Get all the classreportList where isnew is greater than SMALLER_ISNEW
        defaultClassreportShouldBeFound("isnew.greaterThan=" + SMALLER_ISNEW);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClassreportShouldBeFound(String filter) throws Exception {
        restClassreportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classreport.getId().intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].dt").value(hasItem(DEFAULT_DT.toString())))
            .andExpect(jsonPath("$.[*].xjUp").value(hasItem(sameNumber(DEFAULT_XJ_UP))))
            .andExpect(jsonPath("$.[*].yfjA").value(hasItem(sameNumber(DEFAULT_YFJ_A))))
            .andExpect(jsonPath("$.[*].yfjD").value(hasItem(sameNumber(DEFAULT_YFJ_D))))
            .andExpect(jsonPath("$.[*].gz").value(hasItem(sameNumber(DEFAULT_GZ))))
            .andExpect(jsonPath("$.[*].zz").value(hasItem(sameNumber(DEFAULT_ZZ))))
            .andExpect(jsonPath("$.[*].zzYj").value(hasItem(sameNumber(DEFAULT_ZZ_YJ))))
            .andExpect(jsonPath("$.[*].zzJs").value(hasItem(sameNumber(DEFAULT_ZZ_JS))))
            .andExpect(jsonPath("$.[*].zzTc").value(hasItem(sameNumber(DEFAULT_ZZ_TC))))
            .andExpect(jsonPath("$.[*].ff").value(hasItem(sameNumber(DEFAULT_FF))))
            .andExpect(jsonPath("$.[*].minibar").value(hasItem(sameNumber(DEFAULT_MINIBAR))))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(sameNumber(DEFAULT_PHONE))))
            .andExpect(jsonPath("$.[*].other").value(hasItem(sameNumber(DEFAULT_OTHER))))
            .andExpect(jsonPath("$.[*].pc").value(hasItem(sameNumber(DEFAULT_PC))))
            .andExpect(jsonPath("$.[*].cz").value(hasItem(sameNumber(DEFAULT_CZ))))
            .andExpect(jsonPath("$.[*].cy").value(hasItem(sameNumber(DEFAULT_CY))))
            .andExpect(jsonPath("$.[*].md").value(hasItem(sameNumber(DEFAULT_MD))))
            .andExpect(jsonPath("$.[*].huiy").value(hasItem(sameNumber(DEFAULT_HUIY))))
            .andExpect(jsonPath("$.[*].dtb").value(hasItem(sameNumber(DEFAULT_DTB))))
            .andExpect(jsonPath("$.[*].sszx").value(hasItem(sameNumber(DEFAULT_SSZX))))
            .andExpect(jsonPath("$.[*].cyz").value(hasItem(sameNumber(DEFAULT_CYZ))))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].gzxj").value(hasItem(sameNumber(DEFAULT_GZXJ))))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())));

        // Check, that the count call also returns 1
        restClassreportMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClassreportShouldNotBeFound(String filter) throws Exception {
        restClassreportMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClassreportMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClassreport() throws Exception {
        // Get the classreport
        restClassreportMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClassreport() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        int databaseSizeBeforeUpdate = classreportRepository.findAll().size();

        // Update the classreport
        Classreport updatedClassreport = classreportRepository.findById(classreport.getId()).get();
        // Disconnect from session so that the updates on updatedClassreport are not directly saved in db
        em.detach(updatedClassreport);
        updatedClassreport
            .empn(UPDATED_EMPN)
            .dt(UPDATED_DT)
            .xjUp(UPDATED_XJ_UP)
            .yfjA(UPDATED_YFJ_A)
            .yfjD(UPDATED_YFJ_D)
            .gz(UPDATED_GZ)
            .zz(UPDATED_ZZ)
            .zzYj(UPDATED_ZZ_YJ)
            .zzJs(UPDATED_ZZ_JS)
            .zzTc(UPDATED_ZZ_TC)
            .ff(UPDATED_FF)
            .minibar(UPDATED_MINIBAR)
            .phone(UPDATED_PHONE)
            .other(UPDATED_OTHER)
            .pc(UPDATED_PC)
            .cz(UPDATED_CZ)
            .cy(UPDATED_CY)
            .md(UPDATED_MD)
            .huiy(UPDATED_HUIY)
            .dtb(UPDATED_DTB)
            .sszx(UPDATED_SSZX)
            .cyz(UPDATED_CYZ)
            .hoteldm(UPDATED_HOTELDM)
            .gzxj(UPDATED_GZXJ)
            .isnew(UPDATED_ISNEW);
        ClassreportDTO classreportDTO = classreportMapper.toDto(updatedClassreport);

        restClassreportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classreportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classreportDTO))
            )
            .andExpect(status().isOk());

        // Validate the Classreport in the database
        List<Classreport> classreportList = classreportRepository.findAll();
        assertThat(classreportList).hasSize(databaseSizeBeforeUpdate);
        Classreport testClassreport = classreportList.get(classreportList.size() - 1);
        assertThat(testClassreport.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testClassreport.getDt()).isEqualTo(UPDATED_DT);
        assertThat(testClassreport.getXjUp()).isEqualTo(UPDATED_XJ_UP);
        assertThat(testClassreport.getYfjA()).isEqualTo(UPDATED_YFJ_A);
        assertThat(testClassreport.getYfjD()).isEqualTo(UPDATED_YFJ_D);
        assertThat(testClassreport.getGz()).isEqualTo(UPDATED_GZ);
        assertThat(testClassreport.getZz()).isEqualTo(UPDATED_ZZ);
        assertThat(testClassreport.getZzYj()).isEqualTo(UPDATED_ZZ_YJ);
        assertThat(testClassreport.getZzJs()).isEqualTo(UPDATED_ZZ_JS);
        assertThat(testClassreport.getZzTc()).isEqualTo(UPDATED_ZZ_TC);
        assertThat(testClassreport.getFf()).isEqualTo(UPDATED_FF);
        assertThat(testClassreport.getMinibar()).isEqualTo(UPDATED_MINIBAR);
        assertThat(testClassreport.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testClassreport.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testClassreport.getPc()).isEqualTo(UPDATED_PC);
        assertThat(testClassreport.getCz()).isEqualTo(UPDATED_CZ);
        assertThat(testClassreport.getCy()).isEqualTo(UPDATED_CY);
        assertThat(testClassreport.getMd()).isEqualTo(UPDATED_MD);
        assertThat(testClassreport.getHuiy()).isEqualTo(UPDATED_HUIY);
        assertThat(testClassreport.getDtb()).isEqualTo(UPDATED_DTB);
        assertThat(testClassreport.getSszx()).isEqualTo(UPDATED_SSZX);
        assertThat(testClassreport.getCyz()).isEqualTo(UPDATED_CYZ);
        assertThat(testClassreport.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testClassreport.getGzxj()).isEqualTo(UPDATED_GZXJ);
        assertThat(testClassreport.getIsnew()).isEqualTo(UPDATED_ISNEW);

        // Validate the Classreport in Elasticsearch
        verify(mockClassreportSearchRepository).save(testClassreport);
    }

    @Test
    @Transactional
    void putNonExistingClassreport() throws Exception {
        int databaseSizeBeforeUpdate = classreportRepository.findAll().size();
        classreport.setId(count.incrementAndGet());

        // Create the Classreport
        ClassreportDTO classreportDTO = classreportMapper.toDto(classreport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassreportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classreportDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classreportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classreport in the database
        List<Classreport> classreportList = classreportRepository.findAll();
        assertThat(classreportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Classreport in Elasticsearch
        verify(mockClassreportSearchRepository, times(0)).save(classreport);
    }

    @Test
    @Transactional
    void putWithIdMismatchClassreport() throws Exception {
        int databaseSizeBeforeUpdate = classreportRepository.findAll().size();
        classreport.setId(count.incrementAndGet());

        // Create the Classreport
        ClassreportDTO classreportDTO = classreportMapper.toDto(classreport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassreportMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classreportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classreport in the database
        List<Classreport> classreportList = classreportRepository.findAll();
        assertThat(classreportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Classreport in Elasticsearch
        verify(mockClassreportSearchRepository, times(0)).save(classreport);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClassreport() throws Exception {
        int databaseSizeBeforeUpdate = classreportRepository.findAll().size();
        classreport.setId(count.incrementAndGet());

        // Create the Classreport
        ClassreportDTO classreportDTO = classreportMapper.toDto(classreport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassreportMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classreportDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Classreport in the database
        List<Classreport> classreportList = classreportRepository.findAll();
        assertThat(classreportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Classreport in Elasticsearch
        verify(mockClassreportSearchRepository, times(0)).save(classreport);
    }

    @Test
    @Transactional
    void partialUpdateClassreportWithPatch() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        int databaseSizeBeforeUpdate = classreportRepository.findAll().size();

        // Update the classreport using partial update
        Classreport partialUpdatedClassreport = new Classreport();
        partialUpdatedClassreport.setId(classreport.getId());

        partialUpdatedClassreport
            .dt(UPDATED_DT)
            .xjUp(UPDATED_XJ_UP)
            .yfjA(UPDATED_YFJ_A)
            .zz(UPDATED_ZZ)
            .zzTc(UPDATED_ZZ_TC)
            .phone(UPDATED_PHONE)
            .other(UPDATED_OTHER)
            .pc(UPDATED_PC)
            .md(UPDATED_MD)
            .huiy(UPDATED_HUIY)
            .dtb(UPDATED_DTB)
            .sszx(UPDATED_SSZX)
            .cyz(UPDATED_CYZ)
            .isnew(UPDATED_ISNEW);

        restClassreportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassreport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClassreport))
            )
            .andExpect(status().isOk());

        // Validate the Classreport in the database
        List<Classreport> classreportList = classreportRepository.findAll();
        assertThat(classreportList).hasSize(databaseSizeBeforeUpdate);
        Classreport testClassreport = classreportList.get(classreportList.size() - 1);
        assertThat(testClassreport.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testClassreport.getDt()).isEqualTo(UPDATED_DT);
        assertThat(testClassreport.getXjUp()).isEqualByComparingTo(UPDATED_XJ_UP);
        assertThat(testClassreport.getYfjA()).isEqualByComparingTo(UPDATED_YFJ_A);
        assertThat(testClassreport.getYfjD()).isEqualByComparingTo(DEFAULT_YFJ_D);
        assertThat(testClassreport.getGz()).isEqualByComparingTo(DEFAULT_GZ);
        assertThat(testClassreport.getZz()).isEqualByComparingTo(UPDATED_ZZ);
        assertThat(testClassreport.getZzYj()).isEqualByComparingTo(DEFAULT_ZZ_YJ);
        assertThat(testClassreport.getZzJs()).isEqualByComparingTo(DEFAULT_ZZ_JS);
        assertThat(testClassreport.getZzTc()).isEqualByComparingTo(UPDATED_ZZ_TC);
        assertThat(testClassreport.getFf()).isEqualByComparingTo(DEFAULT_FF);
        assertThat(testClassreport.getMinibar()).isEqualByComparingTo(DEFAULT_MINIBAR);
        assertThat(testClassreport.getPhone()).isEqualByComparingTo(UPDATED_PHONE);
        assertThat(testClassreport.getOther()).isEqualByComparingTo(UPDATED_OTHER);
        assertThat(testClassreport.getPc()).isEqualByComparingTo(UPDATED_PC);
        assertThat(testClassreport.getCz()).isEqualByComparingTo(DEFAULT_CZ);
        assertThat(testClassreport.getCy()).isEqualByComparingTo(DEFAULT_CY);
        assertThat(testClassreport.getMd()).isEqualByComparingTo(UPDATED_MD);
        assertThat(testClassreport.getHuiy()).isEqualByComparingTo(UPDATED_HUIY);
        assertThat(testClassreport.getDtb()).isEqualByComparingTo(UPDATED_DTB);
        assertThat(testClassreport.getSszx()).isEqualByComparingTo(UPDATED_SSZX);
        assertThat(testClassreport.getCyz()).isEqualByComparingTo(UPDATED_CYZ);
        assertThat(testClassreport.getHoteldm()).isEqualTo(DEFAULT_HOTELDM);
        assertThat(testClassreport.getGzxj()).isEqualByComparingTo(DEFAULT_GZXJ);
        assertThat(testClassreport.getIsnew()).isEqualTo(UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void fullUpdateClassreportWithPatch() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        int databaseSizeBeforeUpdate = classreportRepository.findAll().size();

        // Update the classreport using partial update
        Classreport partialUpdatedClassreport = new Classreport();
        partialUpdatedClassreport.setId(classreport.getId());

        partialUpdatedClassreport
            .empn(UPDATED_EMPN)
            .dt(UPDATED_DT)
            .xjUp(UPDATED_XJ_UP)
            .yfjA(UPDATED_YFJ_A)
            .yfjD(UPDATED_YFJ_D)
            .gz(UPDATED_GZ)
            .zz(UPDATED_ZZ)
            .zzYj(UPDATED_ZZ_YJ)
            .zzJs(UPDATED_ZZ_JS)
            .zzTc(UPDATED_ZZ_TC)
            .ff(UPDATED_FF)
            .minibar(UPDATED_MINIBAR)
            .phone(UPDATED_PHONE)
            .other(UPDATED_OTHER)
            .pc(UPDATED_PC)
            .cz(UPDATED_CZ)
            .cy(UPDATED_CY)
            .md(UPDATED_MD)
            .huiy(UPDATED_HUIY)
            .dtb(UPDATED_DTB)
            .sszx(UPDATED_SSZX)
            .cyz(UPDATED_CYZ)
            .hoteldm(UPDATED_HOTELDM)
            .gzxj(UPDATED_GZXJ)
            .isnew(UPDATED_ISNEW);

        restClassreportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassreport.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClassreport))
            )
            .andExpect(status().isOk());

        // Validate the Classreport in the database
        List<Classreport> classreportList = classreportRepository.findAll();
        assertThat(classreportList).hasSize(databaseSizeBeforeUpdate);
        Classreport testClassreport = classreportList.get(classreportList.size() - 1);
        assertThat(testClassreport.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testClassreport.getDt()).isEqualTo(UPDATED_DT);
        assertThat(testClassreport.getXjUp()).isEqualByComparingTo(UPDATED_XJ_UP);
        assertThat(testClassreport.getYfjA()).isEqualByComparingTo(UPDATED_YFJ_A);
        assertThat(testClassreport.getYfjD()).isEqualByComparingTo(UPDATED_YFJ_D);
        assertThat(testClassreport.getGz()).isEqualByComparingTo(UPDATED_GZ);
        assertThat(testClassreport.getZz()).isEqualByComparingTo(UPDATED_ZZ);
        assertThat(testClassreport.getZzYj()).isEqualByComparingTo(UPDATED_ZZ_YJ);
        assertThat(testClassreport.getZzJs()).isEqualByComparingTo(UPDATED_ZZ_JS);
        assertThat(testClassreport.getZzTc()).isEqualByComparingTo(UPDATED_ZZ_TC);
        assertThat(testClassreport.getFf()).isEqualByComparingTo(UPDATED_FF);
        assertThat(testClassreport.getMinibar()).isEqualByComparingTo(UPDATED_MINIBAR);
        assertThat(testClassreport.getPhone()).isEqualByComparingTo(UPDATED_PHONE);
        assertThat(testClassreport.getOther()).isEqualByComparingTo(UPDATED_OTHER);
        assertThat(testClassreport.getPc()).isEqualByComparingTo(UPDATED_PC);
        assertThat(testClassreport.getCz()).isEqualByComparingTo(UPDATED_CZ);
        assertThat(testClassreport.getCy()).isEqualByComparingTo(UPDATED_CY);
        assertThat(testClassreport.getMd()).isEqualByComparingTo(UPDATED_MD);
        assertThat(testClassreport.getHuiy()).isEqualByComparingTo(UPDATED_HUIY);
        assertThat(testClassreport.getDtb()).isEqualByComparingTo(UPDATED_DTB);
        assertThat(testClassreport.getSszx()).isEqualByComparingTo(UPDATED_SSZX);
        assertThat(testClassreport.getCyz()).isEqualByComparingTo(UPDATED_CYZ);
        assertThat(testClassreport.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testClassreport.getGzxj()).isEqualByComparingTo(UPDATED_GZXJ);
        assertThat(testClassreport.getIsnew()).isEqualTo(UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void patchNonExistingClassreport() throws Exception {
        int databaseSizeBeforeUpdate = classreportRepository.findAll().size();
        classreport.setId(count.incrementAndGet());

        // Create the Classreport
        ClassreportDTO classreportDTO = classreportMapper.toDto(classreport);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassreportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classreportDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classreportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classreport in the database
        List<Classreport> classreportList = classreportRepository.findAll();
        assertThat(classreportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Classreport in Elasticsearch
        verify(mockClassreportSearchRepository, times(0)).save(classreport);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClassreport() throws Exception {
        int databaseSizeBeforeUpdate = classreportRepository.findAll().size();
        classreport.setId(count.incrementAndGet());

        // Create the Classreport
        ClassreportDTO classreportDTO = classreportMapper.toDto(classreport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassreportMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classreportDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Classreport in the database
        List<Classreport> classreportList = classreportRepository.findAll();
        assertThat(classreportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Classreport in Elasticsearch
        verify(mockClassreportSearchRepository, times(0)).save(classreport);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClassreport() throws Exception {
        int databaseSizeBeforeUpdate = classreportRepository.findAll().size();
        classreport.setId(count.incrementAndGet());

        // Create the Classreport
        ClassreportDTO classreportDTO = classreportMapper.toDto(classreport);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassreportMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(classreportDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Classreport in the database
        List<Classreport> classreportList = classreportRepository.findAll();
        assertThat(classreportList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Classreport in Elasticsearch
        verify(mockClassreportSearchRepository, times(0)).save(classreport);
    }

    @Test
    @Transactional
    void deleteClassreport() throws Exception {
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);

        int databaseSizeBeforeDelete = classreportRepository.findAll().size();

        // Delete the classreport
        restClassreportMockMvc
            .perform(delete(ENTITY_API_URL_ID, classreport.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Classreport> classreportList = classreportRepository.findAll();
        assertThat(classreportList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Classreport in Elasticsearch
        verify(mockClassreportSearchRepository, times(1)).deleteById(classreport.getId());
    }

    @Test
    @Transactional
    void searchClassreport() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        classreportRepository.saveAndFlush(classreport);
        when(mockClassreportSearchRepository.search(queryStringQuery("id:" + classreport.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(classreport), PageRequest.of(0, 1), 1));

        // Search the classreport
        restClassreportMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + classreport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classreport.getId().intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].dt").value(hasItem(DEFAULT_DT.toString())))
            .andExpect(jsonPath("$.[*].xjUp").value(hasItem(sameNumber(DEFAULT_XJ_UP))))
            .andExpect(jsonPath("$.[*].yfjA").value(hasItem(sameNumber(DEFAULT_YFJ_A))))
            .andExpect(jsonPath("$.[*].yfjD").value(hasItem(sameNumber(DEFAULT_YFJ_D))))
            .andExpect(jsonPath("$.[*].gz").value(hasItem(sameNumber(DEFAULT_GZ))))
            .andExpect(jsonPath("$.[*].zz").value(hasItem(sameNumber(DEFAULT_ZZ))))
            .andExpect(jsonPath("$.[*].zzYj").value(hasItem(sameNumber(DEFAULT_ZZ_YJ))))
            .andExpect(jsonPath("$.[*].zzJs").value(hasItem(sameNumber(DEFAULT_ZZ_JS))))
            .andExpect(jsonPath("$.[*].zzTc").value(hasItem(sameNumber(DEFAULT_ZZ_TC))))
            .andExpect(jsonPath("$.[*].ff").value(hasItem(sameNumber(DEFAULT_FF))))
            .andExpect(jsonPath("$.[*].minibar").value(hasItem(sameNumber(DEFAULT_MINIBAR))))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(sameNumber(DEFAULT_PHONE))))
            .andExpect(jsonPath("$.[*].other").value(hasItem(sameNumber(DEFAULT_OTHER))))
            .andExpect(jsonPath("$.[*].pc").value(hasItem(sameNumber(DEFAULT_PC))))
            .andExpect(jsonPath("$.[*].cz").value(hasItem(sameNumber(DEFAULT_CZ))))
            .andExpect(jsonPath("$.[*].cy").value(hasItem(sameNumber(DEFAULT_CY))))
            .andExpect(jsonPath("$.[*].md").value(hasItem(sameNumber(DEFAULT_MD))))
            .andExpect(jsonPath("$.[*].huiy").value(hasItem(sameNumber(DEFAULT_HUIY))))
            .andExpect(jsonPath("$.[*].dtb").value(hasItem(sameNumber(DEFAULT_DTB))))
            .andExpect(jsonPath("$.[*].sszx").value(hasItem(sameNumber(DEFAULT_SSZX))))
            .andExpect(jsonPath("$.[*].cyz").value(hasItem(sameNumber(DEFAULT_CYZ))))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].gzxj").value(hasItem(sameNumber(DEFAULT_GZXJ))))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())));
    }
}
