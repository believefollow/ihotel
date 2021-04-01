package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DPdb;
import ihotel.app.repository.DPdbRepository;
import ihotel.app.repository.search.DPdbSearchRepository;
import ihotel.app.service.criteria.DPdbCriteria;
import ihotel.app.service.dto.DPdbDTO;
import ihotel.app.service.mapper.DPdbMapper;
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
 * Integration tests for the {@link DPdbResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DPdbResourceIT {

    private static final Instant DEFAULT_BEGINDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BEGINDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BM = "AAAAAAAAAA";
    private static final String UPDATED_BM = "BBBBBBBBBB";

    private static final String DEFAULT_SPMC = "AAAAAAAAAA";
    private static final String UPDATED_SPMC = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QCSL = new BigDecimal(1);
    private static final BigDecimal UPDATED_QCSL = new BigDecimal(2);
    private static final BigDecimal SMALLER_QCSL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_RKSL = new BigDecimal(1);
    private static final BigDecimal UPDATED_RKSL = new BigDecimal(2);
    private static final BigDecimal SMALLER_RKSL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_XSSL = new BigDecimal(1);
    private static final BigDecimal UPDATED_XSSL = new BigDecimal(2);
    private static final BigDecimal SMALLER_XSSL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DBSL = new BigDecimal(1);
    private static final BigDecimal UPDATED_DBSL = new BigDecimal(2);
    private static final BigDecimal SMALLER_DBSL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_QTCK = new BigDecimal(1);
    private static final BigDecimal UPDATED_QTCK = new BigDecimal(2);
    private static final BigDecimal SMALLER_QTCK = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_JCSL = new BigDecimal(1);
    private static final BigDecimal UPDATED_JCSL = new BigDecimal(2);
    private static final BigDecimal SMALLER_JCSL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SWSL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SWSL = new BigDecimal(2);
    private static final BigDecimal SMALLER_SWSL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PYK = new BigDecimal(1);
    private static final BigDecimal UPDATED_PYK = new BigDecimal(2);
    private static final BigDecimal SMALLER_PYK = new BigDecimal(1 - 1);

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final String DEFAULT_DEPOT = "AAAAAAAAAA";
    private static final String UPDATED_DEPOT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_RKJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RKJE = new BigDecimal(2);
    private static final BigDecimal SMALLER_RKJE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_XSJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_XSJE = new BigDecimal(2);
    private static final BigDecimal SMALLER_XSJE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DBJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_DBJE = new BigDecimal(2);
    private static final BigDecimal SMALLER_DBJE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_JCJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_JCJE = new BigDecimal(2);
    private static final BigDecimal SMALLER_JCJE = new BigDecimal(1 - 1);

    private static final String DEFAULT_DP = "AAAAAAAAAA";
    private static final String UPDATED_DP = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QCJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_QCJE = new BigDecimal(2);
    private static final BigDecimal SMALLER_QCJE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SWJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SWJE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SWJE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_QTJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_QTJE = new BigDecimal(2);
    private static final BigDecimal SMALLER_QTJE = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/d-pdbs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-pdbs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DPdbRepository dPdbRepository;

    @Autowired
    private DPdbMapper dPdbMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DPdbSearchRepositoryMockConfiguration
     */
    @Autowired
    private DPdbSearchRepository mockDPdbSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDPdbMockMvc;

    private DPdb dPdb;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DPdb createEntity(EntityManager em) {
        DPdb dPdb = new DPdb()
            .begindate(DEFAULT_BEGINDATE)
            .enddate(DEFAULT_ENDDATE)
            .bm(DEFAULT_BM)
            .spmc(DEFAULT_SPMC)
            .qcsl(DEFAULT_QCSL)
            .rksl(DEFAULT_RKSL)
            .xssl(DEFAULT_XSSL)
            .dbsl(DEFAULT_DBSL)
            .qtck(DEFAULT_QTCK)
            .jcsl(DEFAULT_JCSL)
            .swsl(DEFAULT_SWSL)
            .pyk(DEFAULT_PYK)
            .memo(DEFAULT_MEMO)
            .depot(DEFAULT_DEPOT)
            .rkje(DEFAULT_RKJE)
            .xsje(DEFAULT_XSJE)
            .dbje(DEFAULT_DBJE)
            .jcje(DEFAULT_JCJE)
            .dp(DEFAULT_DP)
            .qcje(DEFAULT_QCJE)
            .swje(DEFAULT_SWJE)
            .qtje(DEFAULT_QTJE);
        return dPdb;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DPdb createUpdatedEntity(EntityManager em) {
        DPdb dPdb = new DPdb()
            .begindate(UPDATED_BEGINDATE)
            .enddate(UPDATED_ENDDATE)
            .bm(UPDATED_BM)
            .spmc(UPDATED_SPMC)
            .qcsl(UPDATED_QCSL)
            .rksl(UPDATED_RKSL)
            .xssl(UPDATED_XSSL)
            .dbsl(UPDATED_DBSL)
            .qtck(UPDATED_QTCK)
            .jcsl(UPDATED_JCSL)
            .swsl(UPDATED_SWSL)
            .pyk(UPDATED_PYK)
            .memo(UPDATED_MEMO)
            .depot(UPDATED_DEPOT)
            .rkje(UPDATED_RKJE)
            .xsje(UPDATED_XSJE)
            .dbje(UPDATED_DBJE)
            .jcje(UPDATED_JCJE)
            .dp(UPDATED_DP)
            .qcje(UPDATED_QCJE)
            .swje(UPDATED_SWJE)
            .qtje(UPDATED_QTJE);
        return dPdb;
    }

    @BeforeEach
    public void initTest() {
        dPdb = createEntity(em);
    }

    @Test
    @Transactional
    void createDPdb() throws Exception {
        int databaseSizeBeforeCreate = dPdbRepository.findAll().size();
        // Create the DPdb
        DPdbDTO dPdbDTO = dPdbMapper.toDto(dPdb);
        restDPdbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dPdbDTO)))
            .andExpect(status().isCreated());

        // Validate the DPdb in the database
        List<DPdb> dPdbList = dPdbRepository.findAll();
        assertThat(dPdbList).hasSize(databaseSizeBeforeCreate + 1);
        DPdb testDPdb = dPdbList.get(dPdbList.size() - 1);
        assertThat(testDPdb.getBegindate()).isEqualTo(DEFAULT_BEGINDATE);
        assertThat(testDPdb.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testDPdb.getBm()).isEqualTo(DEFAULT_BM);
        assertThat(testDPdb.getSpmc()).isEqualTo(DEFAULT_SPMC);
        assertThat(testDPdb.getQcsl()).isEqualByComparingTo(DEFAULT_QCSL);
        assertThat(testDPdb.getRksl()).isEqualByComparingTo(DEFAULT_RKSL);
        assertThat(testDPdb.getXssl()).isEqualByComparingTo(DEFAULT_XSSL);
        assertThat(testDPdb.getDbsl()).isEqualByComparingTo(DEFAULT_DBSL);
        assertThat(testDPdb.getQtck()).isEqualByComparingTo(DEFAULT_QTCK);
        assertThat(testDPdb.getJcsl()).isEqualByComparingTo(DEFAULT_JCSL);
        assertThat(testDPdb.getSwsl()).isEqualByComparingTo(DEFAULT_SWSL);
        assertThat(testDPdb.getPyk()).isEqualByComparingTo(DEFAULT_PYK);
        assertThat(testDPdb.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testDPdb.getDepot()).isEqualTo(DEFAULT_DEPOT);
        assertThat(testDPdb.getRkje()).isEqualByComparingTo(DEFAULT_RKJE);
        assertThat(testDPdb.getXsje()).isEqualByComparingTo(DEFAULT_XSJE);
        assertThat(testDPdb.getDbje()).isEqualByComparingTo(DEFAULT_DBJE);
        assertThat(testDPdb.getJcje()).isEqualByComparingTo(DEFAULT_JCJE);
        assertThat(testDPdb.getDp()).isEqualTo(DEFAULT_DP);
        assertThat(testDPdb.getQcje()).isEqualByComparingTo(DEFAULT_QCJE);
        assertThat(testDPdb.getSwje()).isEqualByComparingTo(DEFAULT_SWJE);
        assertThat(testDPdb.getQtje()).isEqualByComparingTo(DEFAULT_QTJE);

        // Validate the DPdb in Elasticsearch
        verify(mockDPdbSearchRepository, times(1)).save(testDPdb);
    }

    @Test
    @Transactional
    void createDPdbWithExistingId() throws Exception {
        // Create the DPdb with an existing ID
        dPdb.setId(1L);
        DPdbDTO dPdbDTO = dPdbMapper.toDto(dPdb);

        int databaseSizeBeforeCreate = dPdbRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDPdbMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dPdbDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DPdb in the database
        List<DPdb> dPdbList = dPdbRepository.findAll();
        assertThat(dPdbList).hasSize(databaseSizeBeforeCreate);

        // Validate the DPdb in Elasticsearch
        verify(mockDPdbSearchRepository, times(0)).save(dPdb);
    }

    @Test
    @Transactional
    void getAllDPdbs() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList
        restDPdbMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dPdb.getId().intValue())))
            .andExpect(jsonPath("$.[*].begindate").value(hasItem(DEFAULT_BEGINDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].bm").value(hasItem(DEFAULT_BM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].qcsl").value(hasItem(sameNumber(DEFAULT_QCSL))))
            .andExpect(jsonPath("$.[*].rksl").value(hasItem(sameNumber(DEFAULT_RKSL))))
            .andExpect(jsonPath("$.[*].xssl").value(hasItem(sameNumber(DEFAULT_XSSL))))
            .andExpect(jsonPath("$.[*].dbsl").value(hasItem(sameNumber(DEFAULT_DBSL))))
            .andExpect(jsonPath("$.[*].qtck").value(hasItem(sameNumber(DEFAULT_QTCK))))
            .andExpect(jsonPath("$.[*].jcsl").value(hasItem(sameNumber(DEFAULT_JCSL))))
            .andExpect(jsonPath("$.[*].swsl").value(hasItem(sameNumber(DEFAULT_SWSL))))
            .andExpect(jsonPath("$.[*].pyk").value(hasItem(sameNumber(DEFAULT_PYK))))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].rkje").value(hasItem(sameNumber(DEFAULT_RKJE))))
            .andExpect(jsonPath("$.[*].xsje").value(hasItem(sameNumber(DEFAULT_XSJE))))
            .andExpect(jsonPath("$.[*].dbje").value(hasItem(sameNumber(DEFAULT_DBJE))))
            .andExpect(jsonPath("$.[*].jcje").value(hasItem(sameNumber(DEFAULT_JCJE))))
            .andExpect(jsonPath("$.[*].dp").value(hasItem(DEFAULT_DP)))
            .andExpect(jsonPath("$.[*].qcje").value(hasItem(sameNumber(DEFAULT_QCJE))))
            .andExpect(jsonPath("$.[*].swje").value(hasItem(sameNumber(DEFAULT_SWJE))))
            .andExpect(jsonPath("$.[*].qtje").value(hasItem(sameNumber(DEFAULT_QTJE))));
    }

    @Test
    @Transactional
    void getDPdb() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get the dPdb
        restDPdbMockMvc
            .perform(get(ENTITY_API_URL_ID, dPdb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dPdb.getId().intValue()))
            .andExpect(jsonPath("$.begindate").value(DEFAULT_BEGINDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.bm").value(DEFAULT_BM))
            .andExpect(jsonPath("$.spmc").value(DEFAULT_SPMC))
            .andExpect(jsonPath("$.qcsl").value(sameNumber(DEFAULT_QCSL)))
            .andExpect(jsonPath("$.rksl").value(sameNumber(DEFAULT_RKSL)))
            .andExpect(jsonPath("$.xssl").value(sameNumber(DEFAULT_XSSL)))
            .andExpect(jsonPath("$.dbsl").value(sameNumber(DEFAULT_DBSL)))
            .andExpect(jsonPath("$.qtck").value(sameNumber(DEFAULT_QTCK)))
            .andExpect(jsonPath("$.jcsl").value(sameNumber(DEFAULT_JCSL)))
            .andExpect(jsonPath("$.swsl").value(sameNumber(DEFAULT_SWSL)))
            .andExpect(jsonPath("$.pyk").value(sameNumber(DEFAULT_PYK)))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO))
            .andExpect(jsonPath("$.depot").value(DEFAULT_DEPOT))
            .andExpect(jsonPath("$.rkje").value(sameNumber(DEFAULT_RKJE)))
            .andExpect(jsonPath("$.xsje").value(sameNumber(DEFAULT_XSJE)))
            .andExpect(jsonPath("$.dbje").value(sameNumber(DEFAULT_DBJE)))
            .andExpect(jsonPath("$.jcje").value(sameNumber(DEFAULT_JCJE)))
            .andExpect(jsonPath("$.dp").value(DEFAULT_DP))
            .andExpect(jsonPath("$.qcje").value(sameNumber(DEFAULT_QCJE)))
            .andExpect(jsonPath("$.swje").value(sameNumber(DEFAULT_SWJE)))
            .andExpect(jsonPath("$.qtje").value(sameNumber(DEFAULT_QTJE)));
    }

    @Test
    @Transactional
    void getDPdbsByIdFiltering() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        Long id = dPdb.getId();

        defaultDPdbShouldBeFound("id.equals=" + id);
        defaultDPdbShouldNotBeFound("id.notEquals=" + id);

        defaultDPdbShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDPdbShouldNotBeFound("id.greaterThan=" + id);

        defaultDPdbShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDPdbShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDPdbsByBegindateIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where begindate equals to DEFAULT_BEGINDATE
        defaultDPdbShouldBeFound("begindate.equals=" + DEFAULT_BEGINDATE);

        // Get all the dPdbList where begindate equals to UPDATED_BEGINDATE
        defaultDPdbShouldNotBeFound("begindate.equals=" + UPDATED_BEGINDATE);
    }

    @Test
    @Transactional
    void getAllDPdbsByBegindateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where begindate not equals to DEFAULT_BEGINDATE
        defaultDPdbShouldNotBeFound("begindate.notEquals=" + DEFAULT_BEGINDATE);

        // Get all the dPdbList where begindate not equals to UPDATED_BEGINDATE
        defaultDPdbShouldBeFound("begindate.notEquals=" + UPDATED_BEGINDATE);
    }

    @Test
    @Transactional
    void getAllDPdbsByBegindateIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where begindate in DEFAULT_BEGINDATE or UPDATED_BEGINDATE
        defaultDPdbShouldBeFound("begindate.in=" + DEFAULT_BEGINDATE + "," + UPDATED_BEGINDATE);

        // Get all the dPdbList where begindate equals to UPDATED_BEGINDATE
        defaultDPdbShouldNotBeFound("begindate.in=" + UPDATED_BEGINDATE);
    }

    @Test
    @Transactional
    void getAllDPdbsByBegindateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where begindate is not null
        defaultDPdbShouldBeFound("begindate.specified=true");

        // Get all the dPdbList where begindate is null
        defaultDPdbShouldNotBeFound("begindate.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where enddate equals to DEFAULT_ENDDATE
        defaultDPdbShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the dPdbList where enddate equals to UPDATED_ENDDATE
        defaultDPdbShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllDPdbsByEnddateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where enddate not equals to DEFAULT_ENDDATE
        defaultDPdbShouldNotBeFound("enddate.notEquals=" + DEFAULT_ENDDATE);

        // Get all the dPdbList where enddate not equals to UPDATED_ENDDATE
        defaultDPdbShouldBeFound("enddate.notEquals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllDPdbsByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultDPdbShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the dPdbList where enddate equals to UPDATED_ENDDATE
        defaultDPdbShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllDPdbsByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where enddate is not null
        defaultDPdbShouldBeFound("enddate.specified=true");

        // Get all the dPdbList where enddate is null
        defaultDPdbShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByBmIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where bm equals to DEFAULT_BM
        defaultDPdbShouldBeFound("bm.equals=" + DEFAULT_BM);

        // Get all the dPdbList where bm equals to UPDATED_BM
        defaultDPdbShouldNotBeFound("bm.equals=" + UPDATED_BM);
    }

    @Test
    @Transactional
    void getAllDPdbsByBmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where bm not equals to DEFAULT_BM
        defaultDPdbShouldNotBeFound("bm.notEquals=" + DEFAULT_BM);

        // Get all the dPdbList where bm not equals to UPDATED_BM
        defaultDPdbShouldBeFound("bm.notEquals=" + UPDATED_BM);
    }

    @Test
    @Transactional
    void getAllDPdbsByBmIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where bm in DEFAULT_BM or UPDATED_BM
        defaultDPdbShouldBeFound("bm.in=" + DEFAULT_BM + "," + UPDATED_BM);

        // Get all the dPdbList where bm equals to UPDATED_BM
        defaultDPdbShouldNotBeFound("bm.in=" + UPDATED_BM);
    }

    @Test
    @Transactional
    void getAllDPdbsByBmIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where bm is not null
        defaultDPdbShouldBeFound("bm.specified=true");

        // Get all the dPdbList where bm is null
        defaultDPdbShouldNotBeFound("bm.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByBmContainsSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where bm contains DEFAULT_BM
        defaultDPdbShouldBeFound("bm.contains=" + DEFAULT_BM);

        // Get all the dPdbList where bm contains UPDATED_BM
        defaultDPdbShouldNotBeFound("bm.contains=" + UPDATED_BM);
    }

    @Test
    @Transactional
    void getAllDPdbsByBmNotContainsSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where bm does not contain DEFAULT_BM
        defaultDPdbShouldNotBeFound("bm.doesNotContain=" + DEFAULT_BM);

        // Get all the dPdbList where bm does not contain UPDATED_BM
        defaultDPdbShouldBeFound("bm.doesNotContain=" + UPDATED_BM);
    }

    @Test
    @Transactional
    void getAllDPdbsBySpmcIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where spmc equals to DEFAULT_SPMC
        defaultDPdbShouldBeFound("spmc.equals=" + DEFAULT_SPMC);

        // Get all the dPdbList where spmc equals to UPDATED_SPMC
        defaultDPdbShouldNotBeFound("spmc.equals=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDPdbsBySpmcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where spmc not equals to DEFAULT_SPMC
        defaultDPdbShouldNotBeFound("spmc.notEquals=" + DEFAULT_SPMC);

        // Get all the dPdbList where spmc not equals to UPDATED_SPMC
        defaultDPdbShouldBeFound("spmc.notEquals=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDPdbsBySpmcIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where spmc in DEFAULT_SPMC or UPDATED_SPMC
        defaultDPdbShouldBeFound("spmc.in=" + DEFAULT_SPMC + "," + UPDATED_SPMC);

        // Get all the dPdbList where spmc equals to UPDATED_SPMC
        defaultDPdbShouldNotBeFound("spmc.in=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDPdbsBySpmcIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where spmc is not null
        defaultDPdbShouldBeFound("spmc.specified=true");

        // Get all the dPdbList where spmc is null
        defaultDPdbShouldNotBeFound("spmc.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsBySpmcContainsSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where spmc contains DEFAULT_SPMC
        defaultDPdbShouldBeFound("spmc.contains=" + DEFAULT_SPMC);

        // Get all the dPdbList where spmc contains UPDATED_SPMC
        defaultDPdbShouldNotBeFound("spmc.contains=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDPdbsBySpmcNotContainsSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where spmc does not contain DEFAULT_SPMC
        defaultDPdbShouldNotBeFound("spmc.doesNotContain=" + DEFAULT_SPMC);

        // Get all the dPdbList where spmc does not contain UPDATED_SPMC
        defaultDPdbShouldBeFound("spmc.doesNotContain=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDPdbsByQcslIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcsl equals to DEFAULT_QCSL
        defaultDPdbShouldBeFound("qcsl.equals=" + DEFAULT_QCSL);

        // Get all the dPdbList where qcsl equals to UPDATED_QCSL
        defaultDPdbShouldNotBeFound("qcsl.equals=" + UPDATED_QCSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByQcslIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcsl not equals to DEFAULT_QCSL
        defaultDPdbShouldNotBeFound("qcsl.notEquals=" + DEFAULT_QCSL);

        // Get all the dPdbList where qcsl not equals to UPDATED_QCSL
        defaultDPdbShouldBeFound("qcsl.notEquals=" + UPDATED_QCSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByQcslIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcsl in DEFAULT_QCSL or UPDATED_QCSL
        defaultDPdbShouldBeFound("qcsl.in=" + DEFAULT_QCSL + "," + UPDATED_QCSL);

        // Get all the dPdbList where qcsl equals to UPDATED_QCSL
        defaultDPdbShouldNotBeFound("qcsl.in=" + UPDATED_QCSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByQcslIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcsl is not null
        defaultDPdbShouldBeFound("qcsl.specified=true");

        // Get all the dPdbList where qcsl is null
        defaultDPdbShouldNotBeFound("qcsl.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByQcslIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcsl is greater than or equal to DEFAULT_QCSL
        defaultDPdbShouldBeFound("qcsl.greaterThanOrEqual=" + DEFAULT_QCSL);

        // Get all the dPdbList where qcsl is greater than or equal to UPDATED_QCSL
        defaultDPdbShouldNotBeFound("qcsl.greaterThanOrEqual=" + UPDATED_QCSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByQcslIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcsl is less than or equal to DEFAULT_QCSL
        defaultDPdbShouldBeFound("qcsl.lessThanOrEqual=" + DEFAULT_QCSL);

        // Get all the dPdbList where qcsl is less than or equal to SMALLER_QCSL
        defaultDPdbShouldNotBeFound("qcsl.lessThanOrEqual=" + SMALLER_QCSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByQcslIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcsl is less than DEFAULT_QCSL
        defaultDPdbShouldNotBeFound("qcsl.lessThan=" + DEFAULT_QCSL);

        // Get all the dPdbList where qcsl is less than UPDATED_QCSL
        defaultDPdbShouldBeFound("qcsl.lessThan=" + UPDATED_QCSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByQcslIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcsl is greater than DEFAULT_QCSL
        defaultDPdbShouldNotBeFound("qcsl.greaterThan=" + DEFAULT_QCSL);

        // Get all the dPdbList where qcsl is greater than SMALLER_QCSL
        defaultDPdbShouldBeFound("qcsl.greaterThan=" + SMALLER_QCSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByRkslIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rksl equals to DEFAULT_RKSL
        defaultDPdbShouldBeFound("rksl.equals=" + DEFAULT_RKSL);

        // Get all the dPdbList where rksl equals to UPDATED_RKSL
        defaultDPdbShouldNotBeFound("rksl.equals=" + UPDATED_RKSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByRkslIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rksl not equals to DEFAULT_RKSL
        defaultDPdbShouldNotBeFound("rksl.notEquals=" + DEFAULT_RKSL);

        // Get all the dPdbList where rksl not equals to UPDATED_RKSL
        defaultDPdbShouldBeFound("rksl.notEquals=" + UPDATED_RKSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByRkslIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rksl in DEFAULT_RKSL or UPDATED_RKSL
        defaultDPdbShouldBeFound("rksl.in=" + DEFAULT_RKSL + "," + UPDATED_RKSL);

        // Get all the dPdbList where rksl equals to UPDATED_RKSL
        defaultDPdbShouldNotBeFound("rksl.in=" + UPDATED_RKSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByRkslIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rksl is not null
        defaultDPdbShouldBeFound("rksl.specified=true");

        // Get all the dPdbList where rksl is null
        defaultDPdbShouldNotBeFound("rksl.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByRkslIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rksl is greater than or equal to DEFAULT_RKSL
        defaultDPdbShouldBeFound("rksl.greaterThanOrEqual=" + DEFAULT_RKSL);

        // Get all the dPdbList where rksl is greater than or equal to UPDATED_RKSL
        defaultDPdbShouldNotBeFound("rksl.greaterThanOrEqual=" + UPDATED_RKSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByRkslIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rksl is less than or equal to DEFAULT_RKSL
        defaultDPdbShouldBeFound("rksl.lessThanOrEqual=" + DEFAULT_RKSL);

        // Get all the dPdbList where rksl is less than or equal to SMALLER_RKSL
        defaultDPdbShouldNotBeFound("rksl.lessThanOrEqual=" + SMALLER_RKSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByRkslIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rksl is less than DEFAULT_RKSL
        defaultDPdbShouldNotBeFound("rksl.lessThan=" + DEFAULT_RKSL);

        // Get all the dPdbList where rksl is less than UPDATED_RKSL
        defaultDPdbShouldBeFound("rksl.lessThan=" + UPDATED_RKSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByRkslIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rksl is greater than DEFAULT_RKSL
        defaultDPdbShouldNotBeFound("rksl.greaterThan=" + DEFAULT_RKSL);

        // Get all the dPdbList where rksl is greater than SMALLER_RKSL
        defaultDPdbShouldBeFound("rksl.greaterThan=" + SMALLER_RKSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByXsslIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xssl equals to DEFAULT_XSSL
        defaultDPdbShouldBeFound("xssl.equals=" + DEFAULT_XSSL);

        // Get all the dPdbList where xssl equals to UPDATED_XSSL
        defaultDPdbShouldNotBeFound("xssl.equals=" + UPDATED_XSSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByXsslIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xssl not equals to DEFAULT_XSSL
        defaultDPdbShouldNotBeFound("xssl.notEquals=" + DEFAULT_XSSL);

        // Get all the dPdbList where xssl not equals to UPDATED_XSSL
        defaultDPdbShouldBeFound("xssl.notEquals=" + UPDATED_XSSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByXsslIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xssl in DEFAULT_XSSL or UPDATED_XSSL
        defaultDPdbShouldBeFound("xssl.in=" + DEFAULT_XSSL + "," + UPDATED_XSSL);

        // Get all the dPdbList where xssl equals to UPDATED_XSSL
        defaultDPdbShouldNotBeFound("xssl.in=" + UPDATED_XSSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByXsslIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xssl is not null
        defaultDPdbShouldBeFound("xssl.specified=true");

        // Get all the dPdbList where xssl is null
        defaultDPdbShouldNotBeFound("xssl.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByXsslIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xssl is greater than or equal to DEFAULT_XSSL
        defaultDPdbShouldBeFound("xssl.greaterThanOrEqual=" + DEFAULT_XSSL);

        // Get all the dPdbList where xssl is greater than or equal to UPDATED_XSSL
        defaultDPdbShouldNotBeFound("xssl.greaterThanOrEqual=" + UPDATED_XSSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByXsslIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xssl is less than or equal to DEFAULT_XSSL
        defaultDPdbShouldBeFound("xssl.lessThanOrEqual=" + DEFAULT_XSSL);

        // Get all the dPdbList where xssl is less than or equal to SMALLER_XSSL
        defaultDPdbShouldNotBeFound("xssl.lessThanOrEqual=" + SMALLER_XSSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByXsslIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xssl is less than DEFAULT_XSSL
        defaultDPdbShouldNotBeFound("xssl.lessThan=" + DEFAULT_XSSL);

        // Get all the dPdbList where xssl is less than UPDATED_XSSL
        defaultDPdbShouldBeFound("xssl.lessThan=" + UPDATED_XSSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByXsslIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xssl is greater than DEFAULT_XSSL
        defaultDPdbShouldNotBeFound("xssl.greaterThan=" + DEFAULT_XSSL);

        // Get all the dPdbList where xssl is greater than SMALLER_XSSL
        defaultDPdbShouldBeFound("xssl.greaterThan=" + SMALLER_XSSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByDbslIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbsl equals to DEFAULT_DBSL
        defaultDPdbShouldBeFound("dbsl.equals=" + DEFAULT_DBSL);

        // Get all the dPdbList where dbsl equals to UPDATED_DBSL
        defaultDPdbShouldNotBeFound("dbsl.equals=" + UPDATED_DBSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByDbslIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbsl not equals to DEFAULT_DBSL
        defaultDPdbShouldNotBeFound("dbsl.notEquals=" + DEFAULT_DBSL);

        // Get all the dPdbList where dbsl not equals to UPDATED_DBSL
        defaultDPdbShouldBeFound("dbsl.notEquals=" + UPDATED_DBSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByDbslIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbsl in DEFAULT_DBSL or UPDATED_DBSL
        defaultDPdbShouldBeFound("dbsl.in=" + DEFAULT_DBSL + "," + UPDATED_DBSL);

        // Get all the dPdbList where dbsl equals to UPDATED_DBSL
        defaultDPdbShouldNotBeFound("dbsl.in=" + UPDATED_DBSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByDbslIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbsl is not null
        defaultDPdbShouldBeFound("dbsl.specified=true");

        // Get all the dPdbList where dbsl is null
        defaultDPdbShouldNotBeFound("dbsl.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByDbslIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbsl is greater than or equal to DEFAULT_DBSL
        defaultDPdbShouldBeFound("dbsl.greaterThanOrEqual=" + DEFAULT_DBSL);

        // Get all the dPdbList where dbsl is greater than or equal to UPDATED_DBSL
        defaultDPdbShouldNotBeFound("dbsl.greaterThanOrEqual=" + UPDATED_DBSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByDbslIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbsl is less than or equal to DEFAULT_DBSL
        defaultDPdbShouldBeFound("dbsl.lessThanOrEqual=" + DEFAULT_DBSL);

        // Get all the dPdbList where dbsl is less than or equal to SMALLER_DBSL
        defaultDPdbShouldNotBeFound("dbsl.lessThanOrEqual=" + SMALLER_DBSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByDbslIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbsl is less than DEFAULT_DBSL
        defaultDPdbShouldNotBeFound("dbsl.lessThan=" + DEFAULT_DBSL);

        // Get all the dPdbList where dbsl is less than UPDATED_DBSL
        defaultDPdbShouldBeFound("dbsl.lessThan=" + UPDATED_DBSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByDbslIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbsl is greater than DEFAULT_DBSL
        defaultDPdbShouldNotBeFound("dbsl.greaterThan=" + DEFAULT_DBSL);

        // Get all the dPdbList where dbsl is greater than SMALLER_DBSL
        defaultDPdbShouldBeFound("dbsl.greaterThan=" + SMALLER_DBSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByQtckIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtck equals to DEFAULT_QTCK
        defaultDPdbShouldBeFound("qtck.equals=" + DEFAULT_QTCK);

        // Get all the dPdbList where qtck equals to UPDATED_QTCK
        defaultDPdbShouldNotBeFound("qtck.equals=" + UPDATED_QTCK);
    }

    @Test
    @Transactional
    void getAllDPdbsByQtckIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtck not equals to DEFAULT_QTCK
        defaultDPdbShouldNotBeFound("qtck.notEquals=" + DEFAULT_QTCK);

        // Get all the dPdbList where qtck not equals to UPDATED_QTCK
        defaultDPdbShouldBeFound("qtck.notEquals=" + UPDATED_QTCK);
    }

    @Test
    @Transactional
    void getAllDPdbsByQtckIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtck in DEFAULT_QTCK or UPDATED_QTCK
        defaultDPdbShouldBeFound("qtck.in=" + DEFAULT_QTCK + "," + UPDATED_QTCK);

        // Get all the dPdbList where qtck equals to UPDATED_QTCK
        defaultDPdbShouldNotBeFound("qtck.in=" + UPDATED_QTCK);
    }

    @Test
    @Transactional
    void getAllDPdbsByQtckIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtck is not null
        defaultDPdbShouldBeFound("qtck.specified=true");

        // Get all the dPdbList where qtck is null
        defaultDPdbShouldNotBeFound("qtck.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByQtckIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtck is greater than or equal to DEFAULT_QTCK
        defaultDPdbShouldBeFound("qtck.greaterThanOrEqual=" + DEFAULT_QTCK);

        // Get all the dPdbList where qtck is greater than or equal to UPDATED_QTCK
        defaultDPdbShouldNotBeFound("qtck.greaterThanOrEqual=" + UPDATED_QTCK);
    }

    @Test
    @Transactional
    void getAllDPdbsByQtckIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtck is less than or equal to DEFAULT_QTCK
        defaultDPdbShouldBeFound("qtck.lessThanOrEqual=" + DEFAULT_QTCK);

        // Get all the dPdbList where qtck is less than or equal to SMALLER_QTCK
        defaultDPdbShouldNotBeFound("qtck.lessThanOrEqual=" + SMALLER_QTCK);
    }

    @Test
    @Transactional
    void getAllDPdbsByQtckIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtck is less than DEFAULT_QTCK
        defaultDPdbShouldNotBeFound("qtck.lessThan=" + DEFAULT_QTCK);

        // Get all the dPdbList where qtck is less than UPDATED_QTCK
        defaultDPdbShouldBeFound("qtck.lessThan=" + UPDATED_QTCK);
    }

    @Test
    @Transactional
    void getAllDPdbsByQtckIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtck is greater than DEFAULT_QTCK
        defaultDPdbShouldNotBeFound("qtck.greaterThan=" + DEFAULT_QTCK);

        // Get all the dPdbList where qtck is greater than SMALLER_QTCK
        defaultDPdbShouldBeFound("qtck.greaterThan=" + SMALLER_QTCK);
    }

    @Test
    @Transactional
    void getAllDPdbsByJcslIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcsl equals to DEFAULT_JCSL
        defaultDPdbShouldBeFound("jcsl.equals=" + DEFAULT_JCSL);

        // Get all the dPdbList where jcsl equals to UPDATED_JCSL
        defaultDPdbShouldNotBeFound("jcsl.equals=" + UPDATED_JCSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByJcslIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcsl not equals to DEFAULT_JCSL
        defaultDPdbShouldNotBeFound("jcsl.notEquals=" + DEFAULT_JCSL);

        // Get all the dPdbList where jcsl not equals to UPDATED_JCSL
        defaultDPdbShouldBeFound("jcsl.notEquals=" + UPDATED_JCSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByJcslIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcsl in DEFAULT_JCSL or UPDATED_JCSL
        defaultDPdbShouldBeFound("jcsl.in=" + DEFAULT_JCSL + "," + UPDATED_JCSL);

        // Get all the dPdbList where jcsl equals to UPDATED_JCSL
        defaultDPdbShouldNotBeFound("jcsl.in=" + UPDATED_JCSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByJcslIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcsl is not null
        defaultDPdbShouldBeFound("jcsl.specified=true");

        // Get all the dPdbList where jcsl is null
        defaultDPdbShouldNotBeFound("jcsl.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByJcslIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcsl is greater than or equal to DEFAULT_JCSL
        defaultDPdbShouldBeFound("jcsl.greaterThanOrEqual=" + DEFAULT_JCSL);

        // Get all the dPdbList where jcsl is greater than or equal to UPDATED_JCSL
        defaultDPdbShouldNotBeFound("jcsl.greaterThanOrEqual=" + UPDATED_JCSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByJcslIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcsl is less than or equal to DEFAULT_JCSL
        defaultDPdbShouldBeFound("jcsl.lessThanOrEqual=" + DEFAULT_JCSL);

        // Get all the dPdbList where jcsl is less than or equal to SMALLER_JCSL
        defaultDPdbShouldNotBeFound("jcsl.lessThanOrEqual=" + SMALLER_JCSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByJcslIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcsl is less than DEFAULT_JCSL
        defaultDPdbShouldNotBeFound("jcsl.lessThan=" + DEFAULT_JCSL);

        // Get all the dPdbList where jcsl is less than UPDATED_JCSL
        defaultDPdbShouldBeFound("jcsl.lessThan=" + UPDATED_JCSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByJcslIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcsl is greater than DEFAULT_JCSL
        defaultDPdbShouldNotBeFound("jcsl.greaterThan=" + DEFAULT_JCSL);

        // Get all the dPdbList where jcsl is greater than SMALLER_JCSL
        defaultDPdbShouldBeFound("jcsl.greaterThan=" + SMALLER_JCSL);
    }

    @Test
    @Transactional
    void getAllDPdbsBySwslIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swsl equals to DEFAULT_SWSL
        defaultDPdbShouldBeFound("swsl.equals=" + DEFAULT_SWSL);

        // Get all the dPdbList where swsl equals to UPDATED_SWSL
        defaultDPdbShouldNotBeFound("swsl.equals=" + UPDATED_SWSL);
    }

    @Test
    @Transactional
    void getAllDPdbsBySwslIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swsl not equals to DEFAULT_SWSL
        defaultDPdbShouldNotBeFound("swsl.notEquals=" + DEFAULT_SWSL);

        // Get all the dPdbList where swsl not equals to UPDATED_SWSL
        defaultDPdbShouldBeFound("swsl.notEquals=" + UPDATED_SWSL);
    }

    @Test
    @Transactional
    void getAllDPdbsBySwslIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swsl in DEFAULT_SWSL or UPDATED_SWSL
        defaultDPdbShouldBeFound("swsl.in=" + DEFAULT_SWSL + "," + UPDATED_SWSL);

        // Get all the dPdbList where swsl equals to UPDATED_SWSL
        defaultDPdbShouldNotBeFound("swsl.in=" + UPDATED_SWSL);
    }

    @Test
    @Transactional
    void getAllDPdbsBySwslIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swsl is not null
        defaultDPdbShouldBeFound("swsl.specified=true");

        // Get all the dPdbList where swsl is null
        defaultDPdbShouldNotBeFound("swsl.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsBySwslIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swsl is greater than or equal to DEFAULT_SWSL
        defaultDPdbShouldBeFound("swsl.greaterThanOrEqual=" + DEFAULT_SWSL);

        // Get all the dPdbList where swsl is greater than or equal to UPDATED_SWSL
        defaultDPdbShouldNotBeFound("swsl.greaterThanOrEqual=" + UPDATED_SWSL);
    }

    @Test
    @Transactional
    void getAllDPdbsBySwslIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swsl is less than or equal to DEFAULT_SWSL
        defaultDPdbShouldBeFound("swsl.lessThanOrEqual=" + DEFAULT_SWSL);

        // Get all the dPdbList where swsl is less than or equal to SMALLER_SWSL
        defaultDPdbShouldNotBeFound("swsl.lessThanOrEqual=" + SMALLER_SWSL);
    }

    @Test
    @Transactional
    void getAllDPdbsBySwslIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swsl is less than DEFAULT_SWSL
        defaultDPdbShouldNotBeFound("swsl.lessThan=" + DEFAULT_SWSL);

        // Get all the dPdbList where swsl is less than UPDATED_SWSL
        defaultDPdbShouldBeFound("swsl.lessThan=" + UPDATED_SWSL);
    }

    @Test
    @Transactional
    void getAllDPdbsBySwslIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swsl is greater than DEFAULT_SWSL
        defaultDPdbShouldNotBeFound("swsl.greaterThan=" + DEFAULT_SWSL);

        // Get all the dPdbList where swsl is greater than SMALLER_SWSL
        defaultDPdbShouldBeFound("swsl.greaterThan=" + SMALLER_SWSL);
    }

    @Test
    @Transactional
    void getAllDPdbsByPykIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where pyk equals to DEFAULT_PYK
        defaultDPdbShouldBeFound("pyk.equals=" + DEFAULT_PYK);

        // Get all the dPdbList where pyk equals to UPDATED_PYK
        defaultDPdbShouldNotBeFound("pyk.equals=" + UPDATED_PYK);
    }

    @Test
    @Transactional
    void getAllDPdbsByPykIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where pyk not equals to DEFAULT_PYK
        defaultDPdbShouldNotBeFound("pyk.notEquals=" + DEFAULT_PYK);

        // Get all the dPdbList where pyk not equals to UPDATED_PYK
        defaultDPdbShouldBeFound("pyk.notEquals=" + UPDATED_PYK);
    }

    @Test
    @Transactional
    void getAllDPdbsByPykIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where pyk in DEFAULT_PYK or UPDATED_PYK
        defaultDPdbShouldBeFound("pyk.in=" + DEFAULT_PYK + "," + UPDATED_PYK);

        // Get all the dPdbList where pyk equals to UPDATED_PYK
        defaultDPdbShouldNotBeFound("pyk.in=" + UPDATED_PYK);
    }

    @Test
    @Transactional
    void getAllDPdbsByPykIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where pyk is not null
        defaultDPdbShouldBeFound("pyk.specified=true");

        // Get all the dPdbList where pyk is null
        defaultDPdbShouldNotBeFound("pyk.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByPykIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where pyk is greater than or equal to DEFAULT_PYK
        defaultDPdbShouldBeFound("pyk.greaterThanOrEqual=" + DEFAULT_PYK);

        // Get all the dPdbList where pyk is greater than or equal to UPDATED_PYK
        defaultDPdbShouldNotBeFound("pyk.greaterThanOrEqual=" + UPDATED_PYK);
    }

    @Test
    @Transactional
    void getAllDPdbsByPykIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where pyk is less than or equal to DEFAULT_PYK
        defaultDPdbShouldBeFound("pyk.lessThanOrEqual=" + DEFAULT_PYK);

        // Get all the dPdbList where pyk is less than or equal to SMALLER_PYK
        defaultDPdbShouldNotBeFound("pyk.lessThanOrEqual=" + SMALLER_PYK);
    }

    @Test
    @Transactional
    void getAllDPdbsByPykIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where pyk is less than DEFAULT_PYK
        defaultDPdbShouldNotBeFound("pyk.lessThan=" + DEFAULT_PYK);

        // Get all the dPdbList where pyk is less than UPDATED_PYK
        defaultDPdbShouldBeFound("pyk.lessThan=" + UPDATED_PYK);
    }

    @Test
    @Transactional
    void getAllDPdbsByPykIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where pyk is greater than DEFAULT_PYK
        defaultDPdbShouldNotBeFound("pyk.greaterThan=" + DEFAULT_PYK);

        // Get all the dPdbList where pyk is greater than SMALLER_PYK
        defaultDPdbShouldBeFound("pyk.greaterThan=" + SMALLER_PYK);
    }

    @Test
    @Transactional
    void getAllDPdbsByMemoIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where memo equals to DEFAULT_MEMO
        defaultDPdbShouldBeFound("memo.equals=" + DEFAULT_MEMO);

        // Get all the dPdbList where memo equals to UPDATED_MEMO
        defaultDPdbShouldNotBeFound("memo.equals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDPdbsByMemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where memo not equals to DEFAULT_MEMO
        defaultDPdbShouldNotBeFound("memo.notEquals=" + DEFAULT_MEMO);

        // Get all the dPdbList where memo not equals to UPDATED_MEMO
        defaultDPdbShouldBeFound("memo.notEquals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDPdbsByMemoIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where memo in DEFAULT_MEMO or UPDATED_MEMO
        defaultDPdbShouldBeFound("memo.in=" + DEFAULT_MEMO + "," + UPDATED_MEMO);

        // Get all the dPdbList where memo equals to UPDATED_MEMO
        defaultDPdbShouldNotBeFound("memo.in=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDPdbsByMemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where memo is not null
        defaultDPdbShouldBeFound("memo.specified=true");

        // Get all the dPdbList where memo is null
        defaultDPdbShouldNotBeFound("memo.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByMemoContainsSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where memo contains DEFAULT_MEMO
        defaultDPdbShouldBeFound("memo.contains=" + DEFAULT_MEMO);

        // Get all the dPdbList where memo contains UPDATED_MEMO
        defaultDPdbShouldNotBeFound("memo.contains=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDPdbsByMemoNotContainsSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where memo does not contain DEFAULT_MEMO
        defaultDPdbShouldNotBeFound("memo.doesNotContain=" + DEFAULT_MEMO);

        // Get all the dPdbList where memo does not contain UPDATED_MEMO
        defaultDPdbShouldBeFound("memo.doesNotContain=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDPdbsByDepotIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where depot equals to DEFAULT_DEPOT
        defaultDPdbShouldBeFound("depot.equals=" + DEFAULT_DEPOT);

        // Get all the dPdbList where depot equals to UPDATED_DEPOT
        defaultDPdbShouldNotBeFound("depot.equals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDPdbsByDepotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where depot not equals to DEFAULT_DEPOT
        defaultDPdbShouldNotBeFound("depot.notEquals=" + DEFAULT_DEPOT);

        // Get all the dPdbList where depot not equals to UPDATED_DEPOT
        defaultDPdbShouldBeFound("depot.notEquals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDPdbsByDepotIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where depot in DEFAULT_DEPOT or UPDATED_DEPOT
        defaultDPdbShouldBeFound("depot.in=" + DEFAULT_DEPOT + "," + UPDATED_DEPOT);

        // Get all the dPdbList where depot equals to UPDATED_DEPOT
        defaultDPdbShouldNotBeFound("depot.in=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDPdbsByDepotIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where depot is not null
        defaultDPdbShouldBeFound("depot.specified=true");

        // Get all the dPdbList where depot is null
        defaultDPdbShouldNotBeFound("depot.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByDepotContainsSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where depot contains DEFAULT_DEPOT
        defaultDPdbShouldBeFound("depot.contains=" + DEFAULT_DEPOT);

        // Get all the dPdbList where depot contains UPDATED_DEPOT
        defaultDPdbShouldNotBeFound("depot.contains=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDPdbsByDepotNotContainsSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where depot does not contain DEFAULT_DEPOT
        defaultDPdbShouldNotBeFound("depot.doesNotContain=" + DEFAULT_DEPOT);

        // Get all the dPdbList where depot does not contain UPDATED_DEPOT
        defaultDPdbShouldBeFound("depot.doesNotContain=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDPdbsByRkjeIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rkje equals to DEFAULT_RKJE
        defaultDPdbShouldBeFound("rkje.equals=" + DEFAULT_RKJE);

        // Get all the dPdbList where rkje equals to UPDATED_RKJE
        defaultDPdbShouldNotBeFound("rkje.equals=" + UPDATED_RKJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByRkjeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rkje not equals to DEFAULT_RKJE
        defaultDPdbShouldNotBeFound("rkje.notEquals=" + DEFAULT_RKJE);

        // Get all the dPdbList where rkje not equals to UPDATED_RKJE
        defaultDPdbShouldBeFound("rkje.notEquals=" + UPDATED_RKJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByRkjeIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rkje in DEFAULT_RKJE or UPDATED_RKJE
        defaultDPdbShouldBeFound("rkje.in=" + DEFAULT_RKJE + "," + UPDATED_RKJE);

        // Get all the dPdbList where rkje equals to UPDATED_RKJE
        defaultDPdbShouldNotBeFound("rkje.in=" + UPDATED_RKJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByRkjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rkje is not null
        defaultDPdbShouldBeFound("rkje.specified=true");

        // Get all the dPdbList where rkje is null
        defaultDPdbShouldNotBeFound("rkje.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByRkjeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rkje is greater than or equal to DEFAULT_RKJE
        defaultDPdbShouldBeFound("rkje.greaterThanOrEqual=" + DEFAULT_RKJE);

        // Get all the dPdbList where rkje is greater than or equal to UPDATED_RKJE
        defaultDPdbShouldNotBeFound("rkje.greaterThanOrEqual=" + UPDATED_RKJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByRkjeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rkje is less than or equal to DEFAULT_RKJE
        defaultDPdbShouldBeFound("rkje.lessThanOrEqual=" + DEFAULT_RKJE);

        // Get all the dPdbList where rkje is less than or equal to SMALLER_RKJE
        defaultDPdbShouldNotBeFound("rkje.lessThanOrEqual=" + SMALLER_RKJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByRkjeIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rkje is less than DEFAULT_RKJE
        defaultDPdbShouldNotBeFound("rkje.lessThan=" + DEFAULT_RKJE);

        // Get all the dPdbList where rkje is less than UPDATED_RKJE
        defaultDPdbShouldBeFound("rkje.lessThan=" + UPDATED_RKJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByRkjeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where rkje is greater than DEFAULT_RKJE
        defaultDPdbShouldNotBeFound("rkje.greaterThan=" + DEFAULT_RKJE);

        // Get all the dPdbList where rkje is greater than SMALLER_RKJE
        defaultDPdbShouldBeFound("rkje.greaterThan=" + SMALLER_RKJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByXsjeIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xsje equals to DEFAULT_XSJE
        defaultDPdbShouldBeFound("xsje.equals=" + DEFAULT_XSJE);

        // Get all the dPdbList where xsje equals to UPDATED_XSJE
        defaultDPdbShouldNotBeFound("xsje.equals=" + UPDATED_XSJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByXsjeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xsje not equals to DEFAULT_XSJE
        defaultDPdbShouldNotBeFound("xsje.notEquals=" + DEFAULT_XSJE);

        // Get all the dPdbList where xsje not equals to UPDATED_XSJE
        defaultDPdbShouldBeFound("xsje.notEquals=" + UPDATED_XSJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByXsjeIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xsje in DEFAULT_XSJE or UPDATED_XSJE
        defaultDPdbShouldBeFound("xsje.in=" + DEFAULT_XSJE + "," + UPDATED_XSJE);

        // Get all the dPdbList where xsje equals to UPDATED_XSJE
        defaultDPdbShouldNotBeFound("xsje.in=" + UPDATED_XSJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByXsjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xsje is not null
        defaultDPdbShouldBeFound("xsje.specified=true");

        // Get all the dPdbList where xsje is null
        defaultDPdbShouldNotBeFound("xsje.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByXsjeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xsje is greater than or equal to DEFAULT_XSJE
        defaultDPdbShouldBeFound("xsje.greaterThanOrEqual=" + DEFAULT_XSJE);

        // Get all the dPdbList where xsje is greater than or equal to UPDATED_XSJE
        defaultDPdbShouldNotBeFound("xsje.greaterThanOrEqual=" + UPDATED_XSJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByXsjeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xsje is less than or equal to DEFAULT_XSJE
        defaultDPdbShouldBeFound("xsje.lessThanOrEqual=" + DEFAULT_XSJE);

        // Get all the dPdbList where xsje is less than or equal to SMALLER_XSJE
        defaultDPdbShouldNotBeFound("xsje.lessThanOrEqual=" + SMALLER_XSJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByXsjeIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xsje is less than DEFAULT_XSJE
        defaultDPdbShouldNotBeFound("xsje.lessThan=" + DEFAULT_XSJE);

        // Get all the dPdbList where xsje is less than UPDATED_XSJE
        defaultDPdbShouldBeFound("xsje.lessThan=" + UPDATED_XSJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByXsjeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where xsje is greater than DEFAULT_XSJE
        defaultDPdbShouldNotBeFound("xsje.greaterThan=" + DEFAULT_XSJE);

        // Get all the dPdbList where xsje is greater than SMALLER_XSJE
        defaultDPdbShouldBeFound("xsje.greaterThan=" + SMALLER_XSJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByDbjeIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbje equals to DEFAULT_DBJE
        defaultDPdbShouldBeFound("dbje.equals=" + DEFAULT_DBJE);

        // Get all the dPdbList where dbje equals to UPDATED_DBJE
        defaultDPdbShouldNotBeFound("dbje.equals=" + UPDATED_DBJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByDbjeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbje not equals to DEFAULT_DBJE
        defaultDPdbShouldNotBeFound("dbje.notEquals=" + DEFAULT_DBJE);

        // Get all the dPdbList where dbje not equals to UPDATED_DBJE
        defaultDPdbShouldBeFound("dbje.notEquals=" + UPDATED_DBJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByDbjeIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbje in DEFAULT_DBJE or UPDATED_DBJE
        defaultDPdbShouldBeFound("dbje.in=" + DEFAULT_DBJE + "," + UPDATED_DBJE);

        // Get all the dPdbList where dbje equals to UPDATED_DBJE
        defaultDPdbShouldNotBeFound("dbje.in=" + UPDATED_DBJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByDbjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbje is not null
        defaultDPdbShouldBeFound("dbje.specified=true");

        // Get all the dPdbList where dbje is null
        defaultDPdbShouldNotBeFound("dbje.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByDbjeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbje is greater than or equal to DEFAULT_DBJE
        defaultDPdbShouldBeFound("dbje.greaterThanOrEqual=" + DEFAULT_DBJE);

        // Get all the dPdbList where dbje is greater than or equal to UPDATED_DBJE
        defaultDPdbShouldNotBeFound("dbje.greaterThanOrEqual=" + UPDATED_DBJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByDbjeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbje is less than or equal to DEFAULT_DBJE
        defaultDPdbShouldBeFound("dbje.lessThanOrEqual=" + DEFAULT_DBJE);

        // Get all the dPdbList where dbje is less than or equal to SMALLER_DBJE
        defaultDPdbShouldNotBeFound("dbje.lessThanOrEqual=" + SMALLER_DBJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByDbjeIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbje is less than DEFAULT_DBJE
        defaultDPdbShouldNotBeFound("dbje.lessThan=" + DEFAULT_DBJE);

        // Get all the dPdbList where dbje is less than UPDATED_DBJE
        defaultDPdbShouldBeFound("dbje.lessThan=" + UPDATED_DBJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByDbjeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dbje is greater than DEFAULT_DBJE
        defaultDPdbShouldNotBeFound("dbje.greaterThan=" + DEFAULT_DBJE);

        // Get all the dPdbList where dbje is greater than SMALLER_DBJE
        defaultDPdbShouldBeFound("dbje.greaterThan=" + SMALLER_DBJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByJcjeIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcje equals to DEFAULT_JCJE
        defaultDPdbShouldBeFound("jcje.equals=" + DEFAULT_JCJE);

        // Get all the dPdbList where jcje equals to UPDATED_JCJE
        defaultDPdbShouldNotBeFound("jcje.equals=" + UPDATED_JCJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByJcjeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcje not equals to DEFAULT_JCJE
        defaultDPdbShouldNotBeFound("jcje.notEquals=" + DEFAULT_JCJE);

        // Get all the dPdbList where jcje not equals to UPDATED_JCJE
        defaultDPdbShouldBeFound("jcje.notEquals=" + UPDATED_JCJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByJcjeIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcje in DEFAULT_JCJE or UPDATED_JCJE
        defaultDPdbShouldBeFound("jcje.in=" + DEFAULT_JCJE + "," + UPDATED_JCJE);

        // Get all the dPdbList where jcje equals to UPDATED_JCJE
        defaultDPdbShouldNotBeFound("jcje.in=" + UPDATED_JCJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByJcjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcje is not null
        defaultDPdbShouldBeFound("jcje.specified=true");

        // Get all the dPdbList where jcje is null
        defaultDPdbShouldNotBeFound("jcje.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByJcjeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcje is greater than or equal to DEFAULT_JCJE
        defaultDPdbShouldBeFound("jcje.greaterThanOrEqual=" + DEFAULT_JCJE);

        // Get all the dPdbList where jcje is greater than or equal to UPDATED_JCJE
        defaultDPdbShouldNotBeFound("jcje.greaterThanOrEqual=" + UPDATED_JCJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByJcjeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcje is less than or equal to DEFAULT_JCJE
        defaultDPdbShouldBeFound("jcje.lessThanOrEqual=" + DEFAULT_JCJE);

        // Get all the dPdbList where jcje is less than or equal to SMALLER_JCJE
        defaultDPdbShouldNotBeFound("jcje.lessThanOrEqual=" + SMALLER_JCJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByJcjeIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcje is less than DEFAULT_JCJE
        defaultDPdbShouldNotBeFound("jcje.lessThan=" + DEFAULT_JCJE);

        // Get all the dPdbList where jcje is less than UPDATED_JCJE
        defaultDPdbShouldBeFound("jcje.lessThan=" + UPDATED_JCJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByJcjeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where jcje is greater than DEFAULT_JCJE
        defaultDPdbShouldNotBeFound("jcje.greaterThan=" + DEFAULT_JCJE);

        // Get all the dPdbList where jcje is greater than SMALLER_JCJE
        defaultDPdbShouldBeFound("jcje.greaterThan=" + SMALLER_JCJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByDpIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dp equals to DEFAULT_DP
        defaultDPdbShouldBeFound("dp.equals=" + DEFAULT_DP);

        // Get all the dPdbList where dp equals to UPDATED_DP
        defaultDPdbShouldNotBeFound("dp.equals=" + UPDATED_DP);
    }

    @Test
    @Transactional
    void getAllDPdbsByDpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dp not equals to DEFAULT_DP
        defaultDPdbShouldNotBeFound("dp.notEquals=" + DEFAULT_DP);

        // Get all the dPdbList where dp not equals to UPDATED_DP
        defaultDPdbShouldBeFound("dp.notEquals=" + UPDATED_DP);
    }

    @Test
    @Transactional
    void getAllDPdbsByDpIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dp in DEFAULT_DP or UPDATED_DP
        defaultDPdbShouldBeFound("dp.in=" + DEFAULT_DP + "," + UPDATED_DP);

        // Get all the dPdbList where dp equals to UPDATED_DP
        defaultDPdbShouldNotBeFound("dp.in=" + UPDATED_DP);
    }

    @Test
    @Transactional
    void getAllDPdbsByDpIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dp is not null
        defaultDPdbShouldBeFound("dp.specified=true");

        // Get all the dPdbList where dp is null
        defaultDPdbShouldNotBeFound("dp.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByDpContainsSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dp contains DEFAULT_DP
        defaultDPdbShouldBeFound("dp.contains=" + DEFAULT_DP);

        // Get all the dPdbList where dp contains UPDATED_DP
        defaultDPdbShouldNotBeFound("dp.contains=" + UPDATED_DP);
    }

    @Test
    @Transactional
    void getAllDPdbsByDpNotContainsSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where dp does not contain DEFAULT_DP
        defaultDPdbShouldNotBeFound("dp.doesNotContain=" + DEFAULT_DP);

        // Get all the dPdbList where dp does not contain UPDATED_DP
        defaultDPdbShouldBeFound("dp.doesNotContain=" + UPDATED_DP);
    }

    @Test
    @Transactional
    void getAllDPdbsByQcjeIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcje equals to DEFAULT_QCJE
        defaultDPdbShouldBeFound("qcje.equals=" + DEFAULT_QCJE);

        // Get all the dPdbList where qcje equals to UPDATED_QCJE
        defaultDPdbShouldNotBeFound("qcje.equals=" + UPDATED_QCJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByQcjeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcje not equals to DEFAULT_QCJE
        defaultDPdbShouldNotBeFound("qcje.notEquals=" + DEFAULT_QCJE);

        // Get all the dPdbList where qcje not equals to UPDATED_QCJE
        defaultDPdbShouldBeFound("qcje.notEquals=" + UPDATED_QCJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByQcjeIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcje in DEFAULT_QCJE or UPDATED_QCJE
        defaultDPdbShouldBeFound("qcje.in=" + DEFAULT_QCJE + "," + UPDATED_QCJE);

        // Get all the dPdbList where qcje equals to UPDATED_QCJE
        defaultDPdbShouldNotBeFound("qcje.in=" + UPDATED_QCJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByQcjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcje is not null
        defaultDPdbShouldBeFound("qcje.specified=true");

        // Get all the dPdbList where qcje is null
        defaultDPdbShouldNotBeFound("qcje.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByQcjeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcje is greater than or equal to DEFAULT_QCJE
        defaultDPdbShouldBeFound("qcje.greaterThanOrEqual=" + DEFAULT_QCJE);

        // Get all the dPdbList where qcje is greater than or equal to UPDATED_QCJE
        defaultDPdbShouldNotBeFound("qcje.greaterThanOrEqual=" + UPDATED_QCJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByQcjeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcje is less than or equal to DEFAULT_QCJE
        defaultDPdbShouldBeFound("qcje.lessThanOrEqual=" + DEFAULT_QCJE);

        // Get all the dPdbList where qcje is less than or equal to SMALLER_QCJE
        defaultDPdbShouldNotBeFound("qcje.lessThanOrEqual=" + SMALLER_QCJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByQcjeIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcje is less than DEFAULT_QCJE
        defaultDPdbShouldNotBeFound("qcje.lessThan=" + DEFAULT_QCJE);

        // Get all the dPdbList where qcje is less than UPDATED_QCJE
        defaultDPdbShouldBeFound("qcje.lessThan=" + UPDATED_QCJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByQcjeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qcje is greater than DEFAULT_QCJE
        defaultDPdbShouldNotBeFound("qcje.greaterThan=" + DEFAULT_QCJE);

        // Get all the dPdbList where qcje is greater than SMALLER_QCJE
        defaultDPdbShouldBeFound("qcje.greaterThan=" + SMALLER_QCJE);
    }

    @Test
    @Transactional
    void getAllDPdbsBySwjeIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swje equals to DEFAULT_SWJE
        defaultDPdbShouldBeFound("swje.equals=" + DEFAULT_SWJE);

        // Get all the dPdbList where swje equals to UPDATED_SWJE
        defaultDPdbShouldNotBeFound("swje.equals=" + UPDATED_SWJE);
    }

    @Test
    @Transactional
    void getAllDPdbsBySwjeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swje not equals to DEFAULT_SWJE
        defaultDPdbShouldNotBeFound("swje.notEquals=" + DEFAULT_SWJE);

        // Get all the dPdbList where swje not equals to UPDATED_SWJE
        defaultDPdbShouldBeFound("swje.notEquals=" + UPDATED_SWJE);
    }

    @Test
    @Transactional
    void getAllDPdbsBySwjeIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swje in DEFAULT_SWJE or UPDATED_SWJE
        defaultDPdbShouldBeFound("swje.in=" + DEFAULT_SWJE + "," + UPDATED_SWJE);

        // Get all the dPdbList where swje equals to UPDATED_SWJE
        defaultDPdbShouldNotBeFound("swje.in=" + UPDATED_SWJE);
    }

    @Test
    @Transactional
    void getAllDPdbsBySwjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swje is not null
        defaultDPdbShouldBeFound("swje.specified=true");

        // Get all the dPdbList where swje is null
        defaultDPdbShouldNotBeFound("swje.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsBySwjeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swje is greater than or equal to DEFAULT_SWJE
        defaultDPdbShouldBeFound("swje.greaterThanOrEqual=" + DEFAULT_SWJE);

        // Get all the dPdbList where swje is greater than or equal to UPDATED_SWJE
        defaultDPdbShouldNotBeFound("swje.greaterThanOrEqual=" + UPDATED_SWJE);
    }

    @Test
    @Transactional
    void getAllDPdbsBySwjeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swje is less than or equal to DEFAULT_SWJE
        defaultDPdbShouldBeFound("swje.lessThanOrEqual=" + DEFAULT_SWJE);

        // Get all the dPdbList where swje is less than or equal to SMALLER_SWJE
        defaultDPdbShouldNotBeFound("swje.lessThanOrEqual=" + SMALLER_SWJE);
    }

    @Test
    @Transactional
    void getAllDPdbsBySwjeIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swje is less than DEFAULT_SWJE
        defaultDPdbShouldNotBeFound("swje.lessThan=" + DEFAULT_SWJE);

        // Get all the dPdbList where swje is less than UPDATED_SWJE
        defaultDPdbShouldBeFound("swje.lessThan=" + UPDATED_SWJE);
    }

    @Test
    @Transactional
    void getAllDPdbsBySwjeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where swje is greater than DEFAULT_SWJE
        defaultDPdbShouldNotBeFound("swje.greaterThan=" + DEFAULT_SWJE);

        // Get all the dPdbList where swje is greater than SMALLER_SWJE
        defaultDPdbShouldBeFound("swje.greaterThan=" + SMALLER_SWJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByQtjeIsEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtje equals to DEFAULT_QTJE
        defaultDPdbShouldBeFound("qtje.equals=" + DEFAULT_QTJE);

        // Get all the dPdbList where qtje equals to UPDATED_QTJE
        defaultDPdbShouldNotBeFound("qtje.equals=" + UPDATED_QTJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByQtjeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtje not equals to DEFAULT_QTJE
        defaultDPdbShouldNotBeFound("qtje.notEquals=" + DEFAULT_QTJE);

        // Get all the dPdbList where qtje not equals to UPDATED_QTJE
        defaultDPdbShouldBeFound("qtje.notEquals=" + UPDATED_QTJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByQtjeIsInShouldWork() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtje in DEFAULT_QTJE or UPDATED_QTJE
        defaultDPdbShouldBeFound("qtje.in=" + DEFAULT_QTJE + "," + UPDATED_QTJE);

        // Get all the dPdbList where qtje equals to UPDATED_QTJE
        defaultDPdbShouldNotBeFound("qtje.in=" + UPDATED_QTJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByQtjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtje is not null
        defaultDPdbShouldBeFound("qtje.specified=true");

        // Get all the dPdbList where qtje is null
        defaultDPdbShouldNotBeFound("qtje.specified=false");
    }

    @Test
    @Transactional
    void getAllDPdbsByQtjeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtje is greater than or equal to DEFAULT_QTJE
        defaultDPdbShouldBeFound("qtje.greaterThanOrEqual=" + DEFAULT_QTJE);

        // Get all the dPdbList where qtje is greater than or equal to UPDATED_QTJE
        defaultDPdbShouldNotBeFound("qtje.greaterThanOrEqual=" + UPDATED_QTJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByQtjeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtje is less than or equal to DEFAULT_QTJE
        defaultDPdbShouldBeFound("qtje.lessThanOrEqual=" + DEFAULT_QTJE);

        // Get all the dPdbList where qtje is less than or equal to SMALLER_QTJE
        defaultDPdbShouldNotBeFound("qtje.lessThanOrEqual=" + SMALLER_QTJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByQtjeIsLessThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtje is less than DEFAULT_QTJE
        defaultDPdbShouldNotBeFound("qtje.lessThan=" + DEFAULT_QTJE);

        // Get all the dPdbList where qtje is less than UPDATED_QTJE
        defaultDPdbShouldBeFound("qtje.lessThan=" + UPDATED_QTJE);
    }

    @Test
    @Transactional
    void getAllDPdbsByQtjeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        // Get all the dPdbList where qtje is greater than DEFAULT_QTJE
        defaultDPdbShouldNotBeFound("qtje.greaterThan=" + DEFAULT_QTJE);

        // Get all the dPdbList where qtje is greater than SMALLER_QTJE
        defaultDPdbShouldBeFound("qtje.greaterThan=" + SMALLER_QTJE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDPdbShouldBeFound(String filter) throws Exception {
        restDPdbMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dPdb.getId().intValue())))
            .andExpect(jsonPath("$.[*].begindate").value(hasItem(DEFAULT_BEGINDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].bm").value(hasItem(DEFAULT_BM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].qcsl").value(hasItem(sameNumber(DEFAULT_QCSL))))
            .andExpect(jsonPath("$.[*].rksl").value(hasItem(sameNumber(DEFAULT_RKSL))))
            .andExpect(jsonPath("$.[*].xssl").value(hasItem(sameNumber(DEFAULT_XSSL))))
            .andExpect(jsonPath("$.[*].dbsl").value(hasItem(sameNumber(DEFAULT_DBSL))))
            .andExpect(jsonPath("$.[*].qtck").value(hasItem(sameNumber(DEFAULT_QTCK))))
            .andExpect(jsonPath("$.[*].jcsl").value(hasItem(sameNumber(DEFAULT_JCSL))))
            .andExpect(jsonPath("$.[*].swsl").value(hasItem(sameNumber(DEFAULT_SWSL))))
            .andExpect(jsonPath("$.[*].pyk").value(hasItem(sameNumber(DEFAULT_PYK))))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].rkje").value(hasItem(sameNumber(DEFAULT_RKJE))))
            .andExpect(jsonPath("$.[*].xsje").value(hasItem(sameNumber(DEFAULT_XSJE))))
            .andExpect(jsonPath("$.[*].dbje").value(hasItem(sameNumber(DEFAULT_DBJE))))
            .andExpect(jsonPath("$.[*].jcje").value(hasItem(sameNumber(DEFAULT_JCJE))))
            .andExpect(jsonPath("$.[*].dp").value(hasItem(DEFAULT_DP)))
            .andExpect(jsonPath("$.[*].qcje").value(hasItem(sameNumber(DEFAULT_QCJE))))
            .andExpect(jsonPath("$.[*].swje").value(hasItem(sameNumber(DEFAULT_SWJE))))
            .andExpect(jsonPath("$.[*].qtje").value(hasItem(sameNumber(DEFAULT_QTJE))));

        // Check, that the count call also returns 1
        restDPdbMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDPdbShouldNotBeFound(String filter) throws Exception {
        restDPdbMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDPdbMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDPdb() throws Exception {
        // Get the dPdb
        restDPdbMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDPdb() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        int databaseSizeBeforeUpdate = dPdbRepository.findAll().size();

        // Update the dPdb
        DPdb updatedDPdb = dPdbRepository.findById(dPdb.getId()).get();
        // Disconnect from session so that the updates on updatedDPdb are not directly saved in db
        em.detach(updatedDPdb);
        updatedDPdb
            .begindate(UPDATED_BEGINDATE)
            .enddate(UPDATED_ENDDATE)
            .bm(UPDATED_BM)
            .spmc(UPDATED_SPMC)
            .qcsl(UPDATED_QCSL)
            .rksl(UPDATED_RKSL)
            .xssl(UPDATED_XSSL)
            .dbsl(UPDATED_DBSL)
            .qtck(UPDATED_QTCK)
            .jcsl(UPDATED_JCSL)
            .swsl(UPDATED_SWSL)
            .pyk(UPDATED_PYK)
            .memo(UPDATED_MEMO)
            .depot(UPDATED_DEPOT)
            .rkje(UPDATED_RKJE)
            .xsje(UPDATED_XSJE)
            .dbje(UPDATED_DBJE)
            .jcje(UPDATED_JCJE)
            .dp(UPDATED_DP)
            .qcje(UPDATED_QCJE)
            .swje(UPDATED_SWJE)
            .qtje(UPDATED_QTJE);
        DPdbDTO dPdbDTO = dPdbMapper.toDto(updatedDPdb);

        restDPdbMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dPdbDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dPdbDTO))
            )
            .andExpect(status().isOk());

        // Validate the DPdb in the database
        List<DPdb> dPdbList = dPdbRepository.findAll();
        assertThat(dPdbList).hasSize(databaseSizeBeforeUpdate);
        DPdb testDPdb = dPdbList.get(dPdbList.size() - 1);
        assertThat(testDPdb.getBegindate()).isEqualTo(UPDATED_BEGINDATE);
        assertThat(testDPdb.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testDPdb.getBm()).isEqualTo(UPDATED_BM);
        assertThat(testDPdb.getSpmc()).isEqualTo(UPDATED_SPMC);
        assertThat(testDPdb.getQcsl()).isEqualTo(UPDATED_QCSL);
        assertThat(testDPdb.getRksl()).isEqualTo(UPDATED_RKSL);
        assertThat(testDPdb.getXssl()).isEqualTo(UPDATED_XSSL);
        assertThat(testDPdb.getDbsl()).isEqualTo(UPDATED_DBSL);
        assertThat(testDPdb.getQtck()).isEqualTo(UPDATED_QTCK);
        assertThat(testDPdb.getJcsl()).isEqualTo(UPDATED_JCSL);
        assertThat(testDPdb.getSwsl()).isEqualTo(UPDATED_SWSL);
        assertThat(testDPdb.getPyk()).isEqualTo(UPDATED_PYK);
        assertThat(testDPdb.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testDPdb.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testDPdb.getRkje()).isEqualTo(UPDATED_RKJE);
        assertThat(testDPdb.getXsje()).isEqualTo(UPDATED_XSJE);
        assertThat(testDPdb.getDbje()).isEqualTo(UPDATED_DBJE);
        assertThat(testDPdb.getJcje()).isEqualTo(UPDATED_JCJE);
        assertThat(testDPdb.getDp()).isEqualTo(UPDATED_DP);
        assertThat(testDPdb.getQcje()).isEqualTo(UPDATED_QCJE);
        assertThat(testDPdb.getSwje()).isEqualTo(UPDATED_SWJE);
        assertThat(testDPdb.getQtje()).isEqualTo(UPDATED_QTJE);

        // Validate the DPdb in Elasticsearch
        verify(mockDPdbSearchRepository).save(testDPdb);
    }

    @Test
    @Transactional
    void putNonExistingDPdb() throws Exception {
        int databaseSizeBeforeUpdate = dPdbRepository.findAll().size();
        dPdb.setId(count.incrementAndGet());

        // Create the DPdb
        DPdbDTO dPdbDTO = dPdbMapper.toDto(dPdb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDPdbMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dPdbDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dPdbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DPdb in the database
        List<DPdb> dPdbList = dPdbRepository.findAll();
        assertThat(dPdbList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DPdb in Elasticsearch
        verify(mockDPdbSearchRepository, times(0)).save(dPdb);
    }

    @Test
    @Transactional
    void putWithIdMismatchDPdb() throws Exception {
        int databaseSizeBeforeUpdate = dPdbRepository.findAll().size();
        dPdb.setId(count.incrementAndGet());

        // Create the DPdb
        DPdbDTO dPdbDTO = dPdbMapper.toDto(dPdb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDPdbMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dPdbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DPdb in the database
        List<DPdb> dPdbList = dPdbRepository.findAll();
        assertThat(dPdbList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DPdb in Elasticsearch
        verify(mockDPdbSearchRepository, times(0)).save(dPdb);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDPdb() throws Exception {
        int databaseSizeBeforeUpdate = dPdbRepository.findAll().size();
        dPdb.setId(count.incrementAndGet());

        // Create the DPdb
        DPdbDTO dPdbDTO = dPdbMapper.toDto(dPdb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDPdbMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dPdbDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DPdb in the database
        List<DPdb> dPdbList = dPdbRepository.findAll();
        assertThat(dPdbList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DPdb in Elasticsearch
        verify(mockDPdbSearchRepository, times(0)).save(dPdb);
    }

    @Test
    @Transactional
    void partialUpdateDPdbWithPatch() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        int databaseSizeBeforeUpdate = dPdbRepository.findAll().size();

        // Update the dPdb using partial update
        DPdb partialUpdatedDPdb = new DPdb();
        partialUpdatedDPdb.setId(dPdb.getId());

        partialUpdatedDPdb
            .begindate(UPDATED_BEGINDATE)
            .enddate(UPDATED_ENDDATE)
            .bm(UPDATED_BM)
            .spmc(UPDATED_SPMC)
            .qcsl(UPDATED_QCSL)
            .rksl(UPDATED_RKSL)
            .pyk(UPDATED_PYK)
            .depot(UPDATED_DEPOT)
            .xsje(UPDATED_XSJE)
            .dbje(UPDATED_DBJE)
            .swje(UPDATED_SWJE);

        restDPdbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDPdb.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDPdb))
            )
            .andExpect(status().isOk());

        // Validate the DPdb in the database
        List<DPdb> dPdbList = dPdbRepository.findAll();
        assertThat(dPdbList).hasSize(databaseSizeBeforeUpdate);
        DPdb testDPdb = dPdbList.get(dPdbList.size() - 1);
        assertThat(testDPdb.getBegindate()).isEqualTo(UPDATED_BEGINDATE);
        assertThat(testDPdb.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testDPdb.getBm()).isEqualTo(UPDATED_BM);
        assertThat(testDPdb.getSpmc()).isEqualTo(UPDATED_SPMC);
        assertThat(testDPdb.getQcsl()).isEqualByComparingTo(UPDATED_QCSL);
        assertThat(testDPdb.getRksl()).isEqualByComparingTo(UPDATED_RKSL);
        assertThat(testDPdb.getXssl()).isEqualByComparingTo(DEFAULT_XSSL);
        assertThat(testDPdb.getDbsl()).isEqualByComparingTo(DEFAULT_DBSL);
        assertThat(testDPdb.getQtck()).isEqualByComparingTo(DEFAULT_QTCK);
        assertThat(testDPdb.getJcsl()).isEqualByComparingTo(DEFAULT_JCSL);
        assertThat(testDPdb.getSwsl()).isEqualByComparingTo(DEFAULT_SWSL);
        assertThat(testDPdb.getPyk()).isEqualByComparingTo(UPDATED_PYK);
        assertThat(testDPdb.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testDPdb.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testDPdb.getRkje()).isEqualByComparingTo(DEFAULT_RKJE);
        assertThat(testDPdb.getXsje()).isEqualByComparingTo(UPDATED_XSJE);
        assertThat(testDPdb.getDbje()).isEqualByComparingTo(UPDATED_DBJE);
        assertThat(testDPdb.getJcje()).isEqualByComparingTo(DEFAULT_JCJE);
        assertThat(testDPdb.getDp()).isEqualTo(DEFAULT_DP);
        assertThat(testDPdb.getQcje()).isEqualByComparingTo(DEFAULT_QCJE);
        assertThat(testDPdb.getSwje()).isEqualByComparingTo(UPDATED_SWJE);
        assertThat(testDPdb.getQtje()).isEqualByComparingTo(DEFAULT_QTJE);
    }

    @Test
    @Transactional
    void fullUpdateDPdbWithPatch() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        int databaseSizeBeforeUpdate = dPdbRepository.findAll().size();

        // Update the dPdb using partial update
        DPdb partialUpdatedDPdb = new DPdb();
        partialUpdatedDPdb.setId(dPdb.getId());

        partialUpdatedDPdb
            .begindate(UPDATED_BEGINDATE)
            .enddate(UPDATED_ENDDATE)
            .bm(UPDATED_BM)
            .spmc(UPDATED_SPMC)
            .qcsl(UPDATED_QCSL)
            .rksl(UPDATED_RKSL)
            .xssl(UPDATED_XSSL)
            .dbsl(UPDATED_DBSL)
            .qtck(UPDATED_QTCK)
            .jcsl(UPDATED_JCSL)
            .swsl(UPDATED_SWSL)
            .pyk(UPDATED_PYK)
            .memo(UPDATED_MEMO)
            .depot(UPDATED_DEPOT)
            .rkje(UPDATED_RKJE)
            .xsje(UPDATED_XSJE)
            .dbje(UPDATED_DBJE)
            .jcje(UPDATED_JCJE)
            .dp(UPDATED_DP)
            .qcje(UPDATED_QCJE)
            .swje(UPDATED_SWJE)
            .qtje(UPDATED_QTJE);

        restDPdbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDPdb.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDPdb))
            )
            .andExpect(status().isOk());

        // Validate the DPdb in the database
        List<DPdb> dPdbList = dPdbRepository.findAll();
        assertThat(dPdbList).hasSize(databaseSizeBeforeUpdate);
        DPdb testDPdb = dPdbList.get(dPdbList.size() - 1);
        assertThat(testDPdb.getBegindate()).isEqualTo(UPDATED_BEGINDATE);
        assertThat(testDPdb.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testDPdb.getBm()).isEqualTo(UPDATED_BM);
        assertThat(testDPdb.getSpmc()).isEqualTo(UPDATED_SPMC);
        assertThat(testDPdb.getQcsl()).isEqualByComparingTo(UPDATED_QCSL);
        assertThat(testDPdb.getRksl()).isEqualByComparingTo(UPDATED_RKSL);
        assertThat(testDPdb.getXssl()).isEqualByComparingTo(UPDATED_XSSL);
        assertThat(testDPdb.getDbsl()).isEqualByComparingTo(UPDATED_DBSL);
        assertThat(testDPdb.getQtck()).isEqualByComparingTo(UPDATED_QTCK);
        assertThat(testDPdb.getJcsl()).isEqualByComparingTo(UPDATED_JCSL);
        assertThat(testDPdb.getSwsl()).isEqualByComparingTo(UPDATED_SWSL);
        assertThat(testDPdb.getPyk()).isEqualByComparingTo(UPDATED_PYK);
        assertThat(testDPdb.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testDPdb.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testDPdb.getRkje()).isEqualByComparingTo(UPDATED_RKJE);
        assertThat(testDPdb.getXsje()).isEqualByComparingTo(UPDATED_XSJE);
        assertThat(testDPdb.getDbje()).isEqualByComparingTo(UPDATED_DBJE);
        assertThat(testDPdb.getJcje()).isEqualByComparingTo(UPDATED_JCJE);
        assertThat(testDPdb.getDp()).isEqualTo(UPDATED_DP);
        assertThat(testDPdb.getQcje()).isEqualByComparingTo(UPDATED_QCJE);
        assertThat(testDPdb.getSwje()).isEqualByComparingTo(UPDATED_SWJE);
        assertThat(testDPdb.getQtje()).isEqualByComparingTo(UPDATED_QTJE);
    }

    @Test
    @Transactional
    void patchNonExistingDPdb() throws Exception {
        int databaseSizeBeforeUpdate = dPdbRepository.findAll().size();
        dPdb.setId(count.incrementAndGet());

        // Create the DPdb
        DPdbDTO dPdbDTO = dPdbMapper.toDto(dPdb);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDPdbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dPdbDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dPdbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DPdb in the database
        List<DPdb> dPdbList = dPdbRepository.findAll();
        assertThat(dPdbList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DPdb in Elasticsearch
        verify(mockDPdbSearchRepository, times(0)).save(dPdb);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDPdb() throws Exception {
        int databaseSizeBeforeUpdate = dPdbRepository.findAll().size();
        dPdb.setId(count.incrementAndGet());

        // Create the DPdb
        DPdbDTO dPdbDTO = dPdbMapper.toDto(dPdb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDPdbMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dPdbDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DPdb in the database
        List<DPdb> dPdbList = dPdbRepository.findAll();
        assertThat(dPdbList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DPdb in Elasticsearch
        verify(mockDPdbSearchRepository, times(0)).save(dPdb);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDPdb() throws Exception {
        int databaseSizeBeforeUpdate = dPdbRepository.findAll().size();
        dPdb.setId(count.incrementAndGet());

        // Create the DPdb
        DPdbDTO dPdbDTO = dPdbMapper.toDto(dPdb);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDPdbMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dPdbDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DPdb in the database
        List<DPdb> dPdbList = dPdbRepository.findAll();
        assertThat(dPdbList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DPdb in Elasticsearch
        verify(mockDPdbSearchRepository, times(0)).save(dPdb);
    }

    @Test
    @Transactional
    void deleteDPdb() throws Exception {
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);

        int databaseSizeBeforeDelete = dPdbRepository.findAll().size();

        // Delete the dPdb
        restDPdbMockMvc
            .perform(delete(ENTITY_API_URL_ID, dPdb.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DPdb> dPdbList = dPdbRepository.findAll();
        assertThat(dPdbList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DPdb in Elasticsearch
        verify(mockDPdbSearchRepository, times(1)).deleteById(dPdb.getId());
    }

    @Test
    @Transactional
    void searchDPdb() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dPdbRepository.saveAndFlush(dPdb);
        when(mockDPdbSearchRepository.search(queryStringQuery("id:" + dPdb.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dPdb), PageRequest.of(0, 1), 1));

        // Search the dPdb
        restDPdbMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dPdb.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dPdb.getId().intValue())))
            .andExpect(jsonPath("$.[*].begindate").value(hasItem(DEFAULT_BEGINDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].bm").value(hasItem(DEFAULT_BM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].qcsl").value(hasItem(sameNumber(DEFAULT_QCSL))))
            .andExpect(jsonPath("$.[*].rksl").value(hasItem(sameNumber(DEFAULT_RKSL))))
            .andExpect(jsonPath("$.[*].xssl").value(hasItem(sameNumber(DEFAULT_XSSL))))
            .andExpect(jsonPath("$.[*].dbsl").value(hasItem(sameNumber(DEFAULT_DBSL))))
            .andExpect(jsonPath("$.[*].qtck").value(hasItem(sameNumber(DEFAULT_QTCK))))
            .andExpect(jsonPath("$.[*].jcsl").value(hasItem(sameNumber(DEFAULT_JCSL))))
            .andExpect(jsonPath("$.[*].swsl").value(hasItem(sameNumber(DEFAULT_SWSL))))
            .andExpect(jsonPath("$.[*].pyk").value(hasItem(sameNumber(DEFAULT_PYK))))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].rkje").value(hasItem(sameNumber(DEFAULT_RKJE))))
            .andExpect(jsonPath("$.[*].xsje").value(hasItem(sameNumber(DEFAULT_XSJE))))
            .andExpect(jsonPath("$.[*].dbje").value(hasItem(sameNumber(DEFAULT_DBJE))))
            .andExpect(jsonPath("$.[*].jcje").value(hasItem(sameNumber(DEFAULT_JCJE))))
            .andExpect(jsonPath("$.[*].dp").value(hasItem(DEFAULT_DP)))
            .andExpect(jsonPath("$.[*].qcje").value(hasItem(sameNumber(DEFAULT_QCJE))))
            .andExpect(jsonPath("$.[*].swje").value(hasItem(sameNumber(DEFAULT_SWJE))))
            .andExpect(jsonPath("$.[*].qtje").value(hasItem(sameNumber(DEFAULT_QTJE))));
    }
}
