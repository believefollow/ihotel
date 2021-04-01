package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DKc;
import ihotel.app.repository.DKcRepository;
import ihotel.app.repository.search.DKcSearchRepository;
import ihotel.app.service.criteria.DKcCriteria;
import ihotel.app.service.dto.DKcDTO;
import ihotel.app.service.mapper.DKcMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link DKcResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DKcResourceIT {

    private static final String DEFAULT_DEPOT = "AAAAAAAAAA";
    private static final String UPDATED_DEPOT = "BBBBBBBBBB";

    private static final String DEFAULT_SPBM = "AAAAAAAAAA";
    private static final String UPDATED_SPBM = "BBBBBBBBBB";

    private static final String DEFAULT_SPMC = "AAAAAAAAAA";
    private static final String UPDATED_SPMC = "BBBBBBBBBB";

    private static final String DEFAULT_XH = "AAAAAAAAAA";
    private static final String UPDATED_XH = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/d-kcs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-kcs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DKcRepository dKcRepository;

    @Autowired
    private DKcMapper dKcMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DKcSearchRepositoryMockConfiguration
     */
    @Autowired
    private DKcSearchRepository mockDKcSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDKcMockMvc;

    private DKc dKc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DKc createEntity(EntityManager em) {
        DKc dKc = new DKc()
            .depot(DEFAULT_DEPOT)
            .spbm(DEFAULT_SPBM)
            .spmc(DEFAULT_SPMC)
            .xh(DEFAULT_XH)
            .dw(DEFAULT_DW)
            .price(DEFAULT_PRICE)
            .sl(DEFAULT_SL)
            .je(DEFAULT_JE);
        return dKc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DKc createUpdatedEntity(EntityManager em) {
        DKc dKc = new DKc()
            .depot(UPDATED_DEPOT)
            .spbm(UPDATED_SPBM)
            .spmc(UPDATED_SPMC)
            .xh(UPDATED_XH)
            .dw(UPDATED_DW)
            .price(UPDATED_PRICE)
            .sl(UPDATED_SL)
            .je(UPDATED_JE);
        return dKc;
    }

    @BeforeEach
    public void initTest() {
        dKc = createEntity(em);
    }

    @Test
    @Transactional
    void createDKc() throws Exception {
        int databaseSizeBeforeCreate = dKcRepository.findAll().size();
        // Create the DKc
        DKcDTO dKcDTO = dKcMapper.toDto(dKc);
        restDKcMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dKcDTO)))
            .andExpect(status().isCreated());

        // Validate the DKc in the database
        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeCreate + 1);
        DKc testDKc = dKcList.get(dKcList.size() - 1);
        assertThat(testDKc.getDepot()).isEqualTo(DEFAULT_DEPOT);
        assertThat(testDKc.getSpbm()).isEqualTo(DEFAULT_SPBM);
        assertThat(testDKc.getSpmc()).isEqualTo(DEFAULT_SPMC);
        assertThat(testDKc.getXh()).isEqualTo(DEFAULT_XH);
        assertThat(testDKc.getDw()).isEqualTo(DEFAULT_DW);
        assertThat(testDKc.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testDKc.getSl()).isEqualByComparingTo(DEFAULT_SL);
        assertThat(testDKc.getJe()).isEqualByComparingTo(DEFAULT_JE);

        // Validate the DKc in Elasticsearch
        verify(mockDKcSearchRepository, times(1)).save(testDKc);
    }

    @Test
    @Transactional
    void createDKcWithExistingId() throws Exception {
        // Create the DKc with an existing ID
        dKc.setId(1L);
        DKcDTO dKcDTO = dKcMapper.toDto(dKc);

        int databaseSizeBeforeCreate = dKcRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDKcMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dKcDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DKc in the database
        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeCreate);

        // Validate the DKc in Elasticsearch
        verify(mockDKcSearchRepository, times(0)).save(dKc);
    }

    @Test
    @Transactional
    void checkDepotIsRequired() throws Exception {
        int databaseSizeBeforeTest = dKcRepository.findAll().size();
        // set the field null
        dKc.setDepot(null);

        // Create the DKc, which fails.
        DKcDTO dKcDTO = dKcMapper.toDto(dKc);

        restDKcMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dKcDTO)))
            .andExpect(status().isBadRequest());

        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpbmIsRequired() throws Exception {
        int databaseSizeBeforeTest = dKcRepository.findAll().size();
        // set the field null
        dKc.setSpbm(null);

        // Create the DKc, which fails.
        DKcDTO dKcDTO = dKcMapper.toDto(dKc);

        restDKcMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dKcDTO)))
            .andExpect(status().isBadRequest());

        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSpmcIsRequired() throws Exception {
        int databaseSizeBeforeTest = dKcRepository.findAll().size();
        // set the field null
        dKc.setSpmc(null);

        // Create the DKc, which fails.
        DKcDTO dKcDTO = dKcMapper.toDto(dKc);

        restDKcMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dKcDTO)))
            .andExpect(status().isBadRequest());

        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDKcs() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList
        restDKcMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dKc.getId().intValue())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].spbm").value(hasItem(DEFAULT_SPBM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].xh").value(hasItem(DEFAULT_XH)))
            .andExpect(jsonPath("$.[*].dw").value(hasItem(DEFAULT_DW)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].je").value(hasItem(sameNumber(DEFAULT_JE))));
    }

    @Test
    @Transactional
    void getDKc() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get the dKc
        restDKcMockMvc
            .perform(get(ENTITY_API_URL_ID, dKc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dKc.getId().intValue()))
            .andExpect(jsonPath("$.depot").value(DEFAULT_DEPOT))
            .andExpect(jsonPath("$.spbm").value(DEFAULT_SPBM))
            .andExpect(jsonPath("$.spmc").value(DEFAULT_SPMC))
            .andExpect(jsonPath("$.xh").value(DEFAULT_XH))
            .andExpect(jsonPath("$.dw").value(DEFAULT_DW))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.sl").value(sameNumber(DEFAULT_SL)))
            .andExpect(jsonPath("$.je").value(sameNumber(DEFAULT_JE)));
    }

    @Test
    @Transactional
    void getDKcsByIdFiltering() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        Long id = dKc.getId();

        defaultDKcShouldBeFound("id.equals=" + id);
        defaultDKcShouldNotBeFound("id.notEquals=" + id);

        defaultDKcShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDKcShouldNotBeFound("id.greaterThan=" + id);

        defaultDKcShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDKcShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDKcsByDepotIsEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where depot equals to DEFAULT_DEPOT
        defaultDKcShouldBeFound("depot.equals=" + DEFAULT_DEPOT);

        // Get all the dKcList where depot equals to UPDATED_DEPOT
        defaultDKcShouldNotBeFound("depot.equals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDKcsByDepotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where depot not equals to DEFAULT_DEPOT
        defaultDKcShouldNotBeFound("depot.notEquals=" + DEFAULT_DEPOT);

        // Get all the dKcList where depot not equals to UPDATED_DEPOT
        defaultDKcShouldBeFound("depot.notEquals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDKcsByDepotIsInShouldWork() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where depot in DEFAULT_DEPOT or UPDATED_DEPOT
        defaultDKcShouldBeFound("depot.in=" + DEFAULT_DEPOT + "," + UPDATED_DEPOT);

        // Get all the dKcList where depot equals to UPDATED_DEPOT
        defaultDKcShouldNotBeFound("depot.in=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDKcsByDepotIsNullOrNotNull() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where depot is not null
        defaultDKcShouldBeFound("depot.specified=true");

        // Get all the dKcList where depot is null
        defaultDKcShouldNotBeFound("depot.specified=false");
    }

    @Test
    @Transactional
    void getAllDKcsByDepotContainsSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where depot contains DEFAULT_DEPOT
        defaultDKcShouldBeFound("depot.contains=" + DEFAULT_DEPOT);

        // Get all the dKcList where depot contains UPDATED_DEPOT
        defaultDKcShouldNotBeFound("depot.contains=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDKcsByDepotNotContainsSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where depot does not contain DEFAULT_DEPOT
        defaultDKcShouldNotBeFound("depot.doesNotContain=" + DEFAULT_DEPOT);

        // Get all the dKcList where depot does not contain UPDATED_DEPOT
        defaultDKcShouldBeFound("depot.doesNotContain=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDKcsBySpbmIsEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where spbm equals to DEFAULT_SPBM
        defaultDKcShouldBeFound("spbm.equals=" + DEFAULT_SPBM);

        // Get all the dKcList where spbm equals to UPDATED_SPBM
        defaultDKcShouldNotBeFound("spbm.equals=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDKcsBySpbmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where spbm not equals to DEFAULT_SPBM
        defaultDKcShouldNotBeFound("spbm.notEquals=" + DEFAULT_SPBM);

        // Get all the dKcList where spbm not equals to UPDATED_SPBM
        defaultDKcShouldBeFound("spbm.notEquals=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDKcsBySpbmIsInShouldWork() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where spbm in DEFAULT_SPBM or UPDATED_SPBM
        defaultDKcShouldBeFound("spbm.in=" + DEFAULT_SPBM + "," + UPDATED_SPBM);

        // Get all the dKcList where spbm equals to UPDATED_SPBM
        defaultDKcShouldNotBeFound("spbm.in=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDKcsBySpbmIsNullOrNotNull() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where spbm is not null
        defaultDKcShouldBeFound("spbm.specified=true");

        // Get all the dKcList where spbm is null
        defaultDKcShouldNotBeFound("spbm.specified=false");
    }

    @Test
    @Transactional
    void getAllDKcsBySpbmContainsSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where spbm contains DEFAULT_SPBM
        defaultDKcShouldBeFound("spbm.contains=" + DEFAULT_SPBM);

        // Get all the dKcList where spbm contains UPDATED_SPBM
        defaultDKcShouldNotBeFound("spbm.contains=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDKcsBySpbmNotContainsSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where spbm does not contain DEFAULT_SPBM
        defaultDKcShouldNotBeFound("spbm.doesNotContain=" + DEFAULT_SPBM);

        // Get all the dKcList where spbm does not contain UPDATED_SPBM
        defaultDKcShouldBeFound("spbm.doesNotContain=" + UPDATED_SPBM);
    }

    @Test
    @Transactional
    void getAllDKcsBySpmcIsEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where spmc equals to DEFAULT_SPMC
        defaultDKcShouldBeFound("spmc.equals=" + DEFAULT_SPMC);

        // Get all the dKcList where spmc equals to UPDATED_SPMC
        defaultDKcShouldNotBeFound("spmc.equals=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDKcsBySpmcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where spmc not equals to DEFAULT_SPMC
        defaultDKcShouldNotBeFound("spmc.notEquals=" + DEFAULT_SPMC);

        // Get all the dKcList where spmc not equals to UPDATED_SPMC
        defaultDKcShouldBeFound("spmc.notEquals=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDKcsBySpmcIsInShouldWork() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where spmc in DEFAULT_SPMC or UPDATED_SPMC
        defaultDKcShouldBeFound("spmc.in=" + DEFAULT_SPMC + "," + UPDATED_SPMC);

        // Get all the dKcList where spmc equals to UPDATED_SPMC
        defaultDKcShouldNotBeFound("spmc.in=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDKcsBySpmcIsNullOrNotNull() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where spmc is not null
        defaultDKcShouldBeFound("spmc.specified=true");

        // Get all the dKcList where spmc is null
        defaultDKcShouldNotBeFound("spmc.specified=false");
    }

    @Test
    @Transactional
    void getAllDKcsBySpmcContainsSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where spmc contains DEFAULT_SPMC
        defaultDKcShouldBeFound("spmc.contains=" + DEFAULT_SPMC);

        // Get all the dKcList where spmc contains UPDATED_SPMC
        defaultDKcShouldNotBeFound("spmc.contains=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDKcsBySpmcNotContainsSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where spmc does not contain DEFAULT_SPMC
        defaultDKcShouldNotBeFound("spmc.doesNotContain=" + DEFAULT_SPMC);

        // Get all the dKcList where spmc does not contain UPDATED_SPMC
        defaultDKcShouldBeFound("spmc.doesNotContain=" + UPDATED_SPMC);
    }

    @Test
    @Transactional
    void getAllDKcsByXhIsEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where xh equals to DEFAULT_XH
        defaultDKcShouldBeFound("xh.equals=" + DEFAULT_XH);

        // Get all the dKcList where xh equals to UPDATED_XH
        defaultDKcShouldNotBeFound("xh.equals=" + UPDATED_XH);
    }

    @Test
    @Transactional
    void getAllDKcsByXhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where xh not equals to DEFAULT_XH
        defaultDKcShouldNotBeFound("xh.notEquals=" + DEFAULT_XH);

        // Get all the dKcList where xh not equals to UPDATED_XH
        defaultDKcShouldBeFound("xh.notEquals=" + UPDATED_XH);
    }

    @Test
    @Transactional
    void getAllDKcsByXhIsInShouldWork() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where xh in DEFAULT_XH or UPDATED_XH
        defaultDKcShouldBeFound("xh.in=" + DEFAULT_XH + "," + UPDATED_XH);

        // Get all the dKcList where xh equals to UPDATED_XH
        defaultDKcShouldNotBeFound("xh.in=" + UPDATED_XH);
    }

    @Test
    @Transactional
    void getAllDKcsByXhIsNullOrNotNull() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where xh is not null
        defaultDKcShouldBeFound("xh.specified=true");

        // Get all the dKcList where xh is null
        defaultDKcShouldNotBeFound("xh.specified=false");
    }

    @Test
    @Transactional
    void getAllDKcsByXhContainsSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where xh contains DEFAULT_XH
        defaultDKcShouldBeFound("xh.contains=" + DEFAULT_XH);

        // Get all the dKcList where xh contains UPDATED_XH
        defaultDKcShouldNotBeFound("xh.contains=" + UPDATED_XH);
    }

    @Test
    @Transactional
    void getAllDKcsByXhNotContainsSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where xh does not contain DEFAULT_XH
        defaultDKcShouldNotBeFound("xh.doesNotContain=" + DEFAULT_XH);

        // Get all the dKcList where xh does not contain UPDATED_XH
        defaultDKcShouldBeFound("xh.doesNotContain=" + UPDATED_XH);
    }

    @Test
    @Transactional
    void getAllDKcsByDwIsEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where dw equals to DEFAULT_DW
        defaultDKcShouldBeFound("dw.equals=" + DEFAULT_DW);

        // Get all the dKcList where dw equals to UPDATED_DW
        defaultDKcShouldNotBeFound("dw.equals=" + UPDATED_DW);
    }

    @Test
    @Transactional
    void getAllDKcsByDwIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where dw not equals to DEFAULT_DW
        defaultDKcShouldNotBeFound("dw.notEquals=" + DEFAULT_DW);

        // Get all the dKcList where dw not equals to UPDATED_DW
        defaultDKcShouldBeFound("dw.notEquals=" + UPDATED_DW);
    }

    @Test
    @Transactional
    void getAllDKcsByDwIsInShouldWork() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where dw in DEFAULT_DW or UPDATED_DW
        defaultDKcShouldBeFound("dw.in=" + DEFAULT_DW + "," + UPDATED_DW);

        // Get all the dKcList where dw equals to UPDATED_DW
        defaultDKcShouldNotBeFound("dw.in=" + UPDATED_DW);
    }

    @Test
    @Transactional
    void getAllDKcsByDwIsNullOrNotNull() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where dw is not null
        defaultDKcShouldBeFound("dw.specified=true");

        // Get all the dKcList where dw is null
        defaultDKcShouldNotBeFound("dw.specified=false");
    }

    @Test
    @Transactional
    void getAllDKcsByDwContainsSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where dw contains DEFAULT_DW
        defaultDKcShouldBeFound("dw.contains=" + DEFAULT_DW);

        // Get all the dKcList where dw contains UPDATED_DW
        defaultDKcShouldNotBeFound("dw.contains=" + UPDATED_DW);
    }

    @Test
    @Transactional
    void getAllDKcsByDwNotContainsSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where dw does not contain DEFAULT_DW
        defaultDKcShouldNotBeFound("dw.doesNotContain=" + DEFAULT_DW);

        // Get all the dKcList where dw does not contain UPDATED_DW
        defaultDKcShouldBeFound("dw.doesNotContain=" + UPDATED_DW);
    }

    @Test
    @Transactional
    void getAllDKcsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where price equals to DEFAULT_PRICE
        defaultDKcShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the dKcList where price equals to UPDATED_PRICE
        defaultDKcShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDKcsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where price not equals to DEFAULT_PRICE
        defaultDKcShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the dKcList where price not equals to UPDATED_PRICE
        defaultDKcShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDKcsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultDKcShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the dKcList where price equals to UPDATED_PRICE
        defaultDKcShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDKcsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where price is not null
        defaultDKcShouldBeFound("price.specified=true");

        // Get all the dKcList where price is null
        defaultDKcShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllDKcsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where price is greater than or equal to DEFAULT_PRICE
        defaultDKcShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the dKcList where price is greater than or equal to UPDATED_PRICE
        defaultDKcShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDKcsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where price is less than or equal to DEFAULT_PRICE
        defaultDKcShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the dKcList where price is less than or equal to SMALLER_PRICE
        defaultDKcShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllDKcsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where price is less than DEFAULT_PRICE
        defaultDKcShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the dKcList where price is less than UPDATED_PRICE
        defaultDKcShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllDKcsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where price is greater than DEFAULT_PRICE
        defaultDKcShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the dKcList where price is greater than SMALLER_PRICE
        defaultDKcShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllDKcsBySlIsEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where sl equals to DEFAULT_SL
        defaultDKcShouldBeFound("sl.equals=" + DEFAULT_SL);

        // Get all the dKcList where sl equals to UPDATED_SL
        defaultDKcShouldNotBeFound("sl.equals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDKcsBySlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where sl not equals to DEFAULT_SL
        defaultDKcShouldNotBeFound("sl.notEquals=" + DEFAULT_SL);

        // Get all the dKcList where sl not equals to UPDATED_SL
        defaultDKcShouldBeFound("sl.notEquals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDKcsBySlIsInShouldWork() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where sl in DEFAULT_SL or UPDATED_SL
        defaultDKcShouldBeFound("sl.in=" + DEFAULT_SL + "," + UPDATED_SL);

        // Get all the dKcList where sl equals to UPDATED_SL
        defaultDKcShouldNotBeFound("sl.in=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDKcsBySlIsNullOrNotNull() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where sl is not null
        defaultDKcShouldBeFound("sl.specified=true");

        // Get all the dKcList where sl is null
        defaultDKcShouldNotBeFound("sl.specified=false");
    }

    @Test
    @Transactional
    void getAllDKcsBySlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where sl is greater than or equal to DEFAULT_SL
        defaultDKcShouldBeFound("sl.greaterThanOrEqual=" + DEFAULT_SL);

        // Get all the dKcList where sl is greater than or equal to UPDATED_SL
        defaultDKcShouldNotBeFound("sl.greaterThanOrEqual=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDKcsBySlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where sl is less than or equal to DEFAULT_SL
        defaultDKcShouldBeFound("sl.lessThanOrEqual=" + DEFAULT_SL);

        // Get all the dKcList where sl is less than or equal to SMALLER_SL
        defaultDKcShouldNotBeFound("sl.lessThanOrEqual=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllDKcsBySlIsLessThanSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where sl is less than DEFAULT_SL
        defaultDKcShouldNotBeFound("sl.lessThan=" + DEFAULT_SL);

        // Get all the dKcList where sl is less than UPDATED_SL
        defaultDKcShouldBeFound("sl.lessThan=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllDKcsBySlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where sl is greater than DEFAULT_SL
        defaultDKcShouldNotBeFound("sl.greaterThan=" + DEFAULT_SL);

        // Get all the dKcList where sl is greater than SMALLER_SL
        defaultDKcShouldBeFound("sl.greaterThan=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllDKcsByJeIsEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where je equals to DEFAULT_JE
        defaultDKcShouldBeFound("je.equals=" + DEFAULT_JE);

        // Get all the dKcList where je equals to UPDATED_JE
        defaultDKcShouldNotBeFound("je.equals=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDKcsByJeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where je not equals to DEFAULT_JE
        defaultDKcShouldNotBeFound("je.notEquals=" + DEFAULT_JE);

        // Get all the dKcList where je not equals to UPDATED_JE
        defaultDKcShouldBeFound("je.notEquals=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDKcsByJeIsInShouldWork() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where je in DEFAULT_JE or UPDATED_JE
        defaultDKcShouldBeFound("je.in=" + DEFAULT_JE + "," + UPDATED_JE);

        // Get all the dKcList where je equals to UPDATED_JE
        defaultDKcShouldNotBeFound("je.in=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDKcsByJeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where je is not null
        defaultDKcShouldBeFound("je.specified=true");

        // Get all the dKcList where je is null
        defaultDKcShouldNotBeFound("je.specified=false");
    }

    @Test
    @Transactional
    void getAllDKcsByJeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where je is greater than or equal to DEFAULT_JE
        defaultDKcShouldBeFound("je.greaterThanOrEqual=" + DEFAULT_JE);

        // Get all the dKcList where je is greater than or equal to UPDATED_JE
        defaultDKcShouldNotBeFound("je.greaterThanOrEqual=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDKcsByJeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where je is less than or equal to DEFAULT_JE
        defaultDKcShouldBeFound("je.lessThanOrEqual=" + DEFAULT_JE);

        // Get all the dKcList where je is less than or equal to SMALLER_JE
        defaultDKcShouldNotBeFound("je.lessThanOrEqual=" + SMALLER_JE);
    }

    @Test
    @Transactional
    void getAllDKcsByJeIsLessThanSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where je is less than DEFAULT_JE
        defaultDKcShouldNotBeFound("je.lessThan=" + DEFAULT_JE);

        // Get all the dKcList where je is less than UPDATED_JE
        defaultDKcShouldBeFound("je.lessThan=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllDKcsByJeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        // Get all the dKcList where je is greater than DEFAULT_JE
        defaultDKcShouldNotBeFound("je.greaterThan=" + DEFAULT_JE);

        // Get all the dKcList where je is greater than SMALLER_JE
        defaultDKcShouldBeFound("je.greaterThan=" + SMALLER_JE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDKcShouldBeFound(String filter) throws Exception {
        restDKcMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dKc.getId().intValue())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].spbm").value(hasItem(DEFAULT_SPBM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].xh").value(hasItem(DEFAULT_XH)))
            .andExpect(jsonPath("$.[*].dw").value(hasItem(DEFAULT_DW)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].je").value(hasItem(sameNumber(DEFAULT_JE))));

        // Check, that the count call also returns 1
        restDKcMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDKcShouldNotBeFound(String filter) throws Exception {
        restDKcMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDKcMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDKc() throws Exception {
        // Get the dKc
        restDKcMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDKc() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        int databaseSizeBeforeUpdate = dKcRepository.findAll().size();

        // Update the dKc
        DKc updatedDKc = dKcRepository.findById(dKc.getId()).get();
        // Disconnect from session so that the updates on updatedDKc are not directly saved in db
        em.detach(updatedDKc);
        updatedDKc
            .depot(UPDATED_DEPOT)
            .spbm(UPDATED_SPBM)
            .spmc(UPDATED_SPMC)
            .xh(UPDATED_XH)
            .dw(UPDATED_DW)
            .price(UPDATED_PRICE)
            .sl(UPDATED_SL)
            .je(UPDATED_JE);
        DKcDTO dKcDTO = dKcMapper.toDto(updatedDKc);

        restDKcMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dKcDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dKcDTO))
            )
            .andExpect(status().isOk());

        // Validate the DKc in the database
        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeUpdate);
        DKc testDKc = dKcList.get(dKcList.size() - 1);
        assertThat(testDKc.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testDKc.getSpbm()).isEqualTo(UPDATED_SPBM);
        assertThat(testDKc.getSpmc()).isEqualTo(UPDATED_SPMC);
        assertThat(testDKc.getXh()).isEqualTo(UPDATED_XH);
        assertThat(testDKc.getDw()).isEqualTo(UPDATED_DW);
        assertThat(testDKc.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDKc.getSl()).isEqualTo(UPDATED_SL);
        assertThat(testDKc.getJe()).isEqualTo(UPDATED_JE);

        // Validate the DKc in Elasticsearch
        verify(mockDKcSearchRepository).save(testDKc);
    }

    @Test
    @Transactional
    void putNonExistingDKc() throws Exception {
        int databaseSizeBeforeUpdate = dKcRepository.findAll().size();
        dKc.setId(count.incrementAndGet());

        // Create the DKc
        DKcDTO dKcDTO = dKcMapper.toDto(dKc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDKcMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dKcDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dKcDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DKc in the database
        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DKc in Elasticsearch
        verify(mockDKcSearchRepository, times(0)).save(dKc);
    }

    @Test
    @Transactional
    void putWithIdMismatchDKc() throws Exception {
        int databaseSizeBeforeUpdate = dKcRepository.findAll().size();
        dKc.setId(count.incrementAndGet());

        // Create the DKc
        DKcDTO dKcDTO = dKcMapper.toDto(dKc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDKcMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dKcDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DKc in the database
        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DKc in Elasticsearch
        verify(mockDKcSearchRepository, times(0)).save(dKc);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDKc() throws Exception {
        int databaseSizeBeforeUpdate = dKcRepository.findAll().size();
        dKc.setId(count.incrementAndGet());

        // Create the DKc
        DKcDTO dKcDTO = dKcMapper.toDto(dKc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDKcMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dKcDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DKc in the database
        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DKc in Elasticsearch
        verify(mockDKcSearchRepository, times(0)).save(dKc);
    }

    @Test
    @Transactional
    void partialUpdateDKcWithPatch() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        int databaseSizeBeforeUpdate = dKcRepository.findAll().size();

        // Update the dKc using partial update
        DKc partialUpdatedDKc = new DKc();
        partialUpdatedDKc.setId(dKc.getId());

        partialUpdatedDKc.price(UPDATED_PRICE).sl(UPDATED_SL).je(UPDATED_JE);

        restDKcMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDKc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDKc))
            )
            .andExpect(status().isOk());

        // Validate the DKc in the database
        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeUpdate);
        DKc testDKc = dKcList.get(dKcList.size() - 1);
        assertThat(testDKc.getDepot()).isEqualTo(DEFAULT_DEPOT);
        assertThat(testDKc.getSpbm()).isEqualTo(DEFAULT_SPBM);
        assertThat(testDKc.getSpmc()).isEqualTo(DEFAULT_SPMC);
        assertThat(testDKc.getXh()).isEqualTo(DEFAULT_XH);
        assertThat(testDKc.getDw()).isEqualTo(DEFAULT_DW);
        assertThat(testDKc.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testDKc.getSl()).isEqualByComparingTo(UPDATED_SL);
        assertThat(testDKc.getJe()).isEqualByComparingTo(UPDATED_JE);
    }

    @Test
    @Transactional
    void fullUpdateDKcWithPatch() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        int databaseSizeBeforeUpdate = dKcRepository.findAll().size();

        // Update the dKc using partial update
        DKc partialUpdatedDKc = new DKc();
        partialUpdatedDKc.setId(dKc.getId());

        partialUpdatedDKc
            .depot(UPDATED_DEPOT)
            .spbm(UPDATED_SPBM)
            .spmc(UPDATED_SPMC)
            .xh(UPDATED_XH)
            .dw(UPDATED_DW)
            .price(UPDATED_PRICE)
            .sl(UPDATED_SL)
            .je(UPDATED_JE);

        restDKcMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDKc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDKc))
            )
            .andExpect(status().isOk());

        // Validate the DKc in the database
        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeUpdate);
        DKc testDKc = dKcList.get(dKcList.size() - 1);
        assertThat(testDKc.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testDKc.getSpbm()).isEqualTo(UPDATED_SPBM);
        assertThat(testDKc.getSpmc()).isEqualTo(UPDATED_SPMC);
        assertThat(testDKc.getXh()).isEqualTo(UPDATED_XH);
        assertThat(testDKc.getDw()).isEqualTo(UPDATED_DW);
        assertThat(testDKc.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testDKc.getSl()).isEqualByComparingTo(UPDATED_SL);
        assertThat(testDKc.getJe()).isEqualByComparingTo(UPDATED_JE);
    }

    @Test
    @Transactional
    void patchNonExistingDKc() throws Exception {
        int databaseSizeBeforeUpdate = dKcRepository.findAll().size();
        dKc.setId(count.incrementAndGet());

        // Create the DKc
        DKcDTO dKcDTO = dKcMapper.toDto(dKc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDKcMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dKcDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dKcDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DKc in the database
        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DKc in Elasticsearch
        verify(mockDKcSearchRepository, times(0)).save(dKc);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDKc() throws Exception {
        int databaseSizeBeforeUpdate = dKcRepository.findAll().size();
        dKc.setId(count.incrementAndGet());

        // Create the DKc
        DKcDTO dKcDTO = dKcMapper.toDto(dKc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDKcMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dKcDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DKc in the database
        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DKc in Elasticsearch
        verify(mockDKcSearchRepository, times(0)).save(dKc);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDKc() throws Exception {
        int databaseSizeBeforeUpdate = dKcRepository.findAll().size();
        dKc.setId(count.incrementAndGet());

        // Create the DKc
        DKcDTO dKcDTO = dKcMapper.toDto(dKc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDKcMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dKcDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DKc in the database
        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DKc in Elasticsearch
        verify(mockDKcSearchRepository, times(0)).save(dKc);
    }

    @Test
    @Transactional
    void deleteDKc() throws Exception {
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);

        int databaseSizeBeforeDelete = dKcRepository.findAll().size();

        // Delete the dKc
        restDKcMockMvc.perform(delete(ENTITY_API_URL_ID, dKc.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DKc> dKcList = dKcRepository.findAll();
        assertThat(dKcList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DKc in Elasticsearch
        verify(mockDKcSearchRepository, times(1)).deleteById(dKc.getId());
    }

    @Test
    @Transactional
    void searchDKc() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dKcRepository.saveAndFlush(dKc);
        when(mockDKcSearchRepository.search(queryStringQuery("id:" + dKc.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dKc), PageRequest.of(0, 1), 1));

        // Search the dKc
        restDKcMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dKc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dKc.getId().intValue())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].spbm").value(hasItem(DEFAULT_SPBM)))
            .andExpect(jsonPath("$.[*].spmc").value(hasItem(DEFAULT_SPMC)))
            .andExpect(jsonPath("$.[*].xh").value(hasItem(DEFAULT_XH)))
            .andExpect(jsonPath("$.[*].dw").value(hasItem(DEFAULT_DW)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].je").value(hasItem(sameNumber(DEFAULT_JE))));
    }
}
