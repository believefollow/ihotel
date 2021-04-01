package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.FwEmpn;
import ihotel.app.repository.FwEmpnRepository;
import ihotel.app.repository.search.FwEmpnSearchRepository;
import ihotel.app.service.criteria.FwEmpnCriteria;
import ihotel.app.service.dto.FwEmpnDTO;
import ihotel.app.service.mapper.FwEmpnMapper;
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
 * Integration tests for the {@link FwEmpnResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FwEmpnResourceIT {

    private static final String DEFAULT_EMPNID = "AAAAAAAAAA";
    private static final String UPDATED_EMPNID = "BBBBBBBBBB";

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final Long DEFAULT_DEPTID = 1L;
    private static final Long UPDATED_DEPTID = 2L;
    private static final Long SMALLER_DEPTID = 1L - 1L;

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fw-empns";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/fw-empns";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FwEmpnRepository fwEmpnRepository;

    @Autowired
    private FwEmpnMapper fwEmpnMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.FwEmpnSearchRepositoryMockConfiguration
     */
    @Autowired
    private FwEmpnSearchRepository mockFwEmpnSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFwEmpnMockMvc;

    private FwEmpn fwEmpn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FwEmpn createEntity(EntityManager em) {
        FwEmpn fwEmpn = new FwEmpn().empnid(DEFAULT_EMPNID).empn(DEFAULT_EMPN).deptid(DEFAULT_DEPTID).phone(DEFAULT_PHONE);
        return fwEmpn;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FwEmpn createUpdatedEntity(EntityManager em) {
        FwEmpn fwEmpn = new FwEmpn().empnid(UPDATED_EMPNID).empn(UPDATED_EMPN).deptid(UPDATED_DEPTID).phone(UPDATED_PHONE);
        return fwEmpn;
    }

    @BeforeEach
    public void initTest() {
        fwEmpn = createEntity(em);
    }

    @Test
    @Transactional
    void createFwEmpn() throws Exception {
        int databaseSizeBeforeCreate = fwEmpnRepository.findAll().size();
        // Create the FwEmpn
        FwEmpnDTO fwEmpnDTO = fwEmpnMapper.toDto(fwEmpn);
        restFwEmpnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwEmpnDTO)))
            .andExpect(status().isCreated());

        // Validate the FwEmpn in the database
        List<FwEmpn> fwEmpnList = fwEmpnRepository.findAll();
        assertThat(fwEmpnList).hasSize(databaseSizeBeforeCreate + 1);
        FwEmpn testFwEmpn = fwEmpnList.get(fwEmpnList.size() - 1);
        assertThat(testFwEmpn.getEmpnid()).isEqualTo(DEFAULT_EMPNID);
        assertThat(testFwEmpn.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testFwEmpn.getDeptid()).isEqualTo(DEFAULT_DEPTID);
        assertThat(testFwEmpn.getPhone()).isEqualTo(DEFAULT_PHONE);

        // Validate the FwEmpn in Elasticsearch
        verify(mockFwEmpnSearchRepository, times(1)).save(testFwEmpn);
    }

    @Test
    @Transactional
    void createFwEmpnWithExistingId() throws Exception {
        // Create the FwEmpn with an existing ID
        fwEmpn.setId(1L);
        FwEmpnDTO fwEmpnDTO = fwEmpnMapper.toDto(fwEmpn);

        int databaseSizeBeforeCreate = fwEmpnRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFwEmpnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwEmpnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FwEmpn in the database
        List<FwEmpn> fwEmpnList = fwEmpnRepository.findAll();
        assertThat(fwEmpnList).hasSize(databaseSizeBeforeCreate);

        // Validate the FwEmpn in Elasticsearch
        verify(mockFwEmpnSearchRepository, times(0)).save(fwEmpn);
    }

    @Test
    @Transactional
    void checkEmpnidIsRequired() throws Exception {
        int databaseSizeBeforeTest = fwEmpnRepository.findAll().size();
        // set the field null
        fwEmpn.setEmpnid(null);

        // Create the FwEmpn, which fails.
        FwEmpnDTO fwEmpnDTO = fwEmpnMapper.toDto(fwEmpn);

        restFwEmpnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwEmpnDTO)))
            .andExpect(status().isBadRequest());

        List<FwEmpn> fwEmpnList = fwEmpnRepository.findAll();
        assertThat(fwEmpnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmpnIsRequired() throws Exception {
        int databaseSizeBeforeTest = fwEmpnRepository.findAll().size();
        // set the field null
        fwEmpn.setEmpn(null);

        // Create the FwEmpn, which fails.
        FwEmpnDTO fwEmpnDTO = fwEmpnMapper.toDto(fwEmpn);

        restFwEmpnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwEmpnDTO)))
            .andExpect(status().isBadRequest());

        List<FwEmpn> fwEmpnList = fwEmpnRepository.findAll();
        assertThat(fwEmpnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFwEmpns() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList
        restFwEmpnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwEmpn.getId().intValue())))
            .andExpect(jsonPath("$.[*].empnid").value(hasItem(DEFAULT_EMPNID)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].deptid").value(hasItem(DEFAULT_DEPTID.intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    @Transactional
    void getFwEmpn() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get the fwEmpn
        restFwEmpnMockMvc
            .perform(get(ENTITY_API_URL_ID, fwEmpn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fwEmpn.getId().intValue()))
            .andExpect(jsonPath("$.empnid").value(DEFAULT_EMPNID))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.deptid").value(DEFAULT_DEPTID.intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    void getFwEmpnsByIdFiltering() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        Long id = fwEmpn.getId();

        defaultFwEmpnShouldBeFound("id.equals=" + id);
        defaultFwEmpnShouldNotBeFound("id.notEquals=" + id);

        defaultFwEmpnShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFwEmpnShouldNotBeFound("id.greaterThan=" + id);

        defaultFwEmpnShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFwEmpnShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByEmpnidIsEqualToSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where empnid equals to DEFAULT_EMPNID
        defaultFwEmpnShouldBeFound("empnid.equals=" + DEFAULT_EMPNID);

        // Get all the fwEmpnList where empnid equals to UPDATED_EMPNID
        defaultFwEmpnShouldNotBeFound("empnid.equals=" + UPDATED_EMPNID);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByEmpnidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where empnid not equals to DEFAULT_EMPNID
        defaultFwEmpnShouldNotBeFound("empnid.notEquals=" + DEFAULT_EMPNID);

        // Get all the fwEmpnList where empnid not equals to UPDATED_EMPNID
        defaultFwEmpnShouldBeFound("empnid.notEquals=" + UPDATED_EMPNID);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByEmpnidIsInShouldWork() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where empnid in DEFAULT_EMPNID or UPDATED_EMPNID
        defaultFwEmpnShouldBeFound("empnid.in=" + DEFAULT_EMPNID + "," + UPDATED_EMPNID);

        // Get all the fwEmpnList where empnid equals to UPDATED_EMPNID
        defaultFwEmpnShouldNotBeFound("empnid.in=" + UPDATED_EMPNID);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByEmpnidIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where empnid is not null
        defaultFwEmpnShouldBeFound("empnid.specified=true");

        // Get all the fwEmpnList where empnid is null
        defaultFwEmpnShouldNotBeFound("empnid.specified=false");
    }

    @Test
    @Transactional
    void getAllFwEmpnsByEmpnidContainsSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where empnid contains DEFAULT_EMPNID
        defaultFwEmpnShouldBeFound("empnid.contains=" + DEFAULT_EMPNID);

        // Get all the fwEmpnList where empnid contains UPDATED_EMPNID
        defaultFwEmpnShouldNotBeFound("empnid.contains=" + UPDATED_EMPNID);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByEmpnidNotContainsSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where empnid does not contain DEFAULT_EMPNID
        defaultFwEmpnShouldNotBeFound("empnid.doesNotContain=" + DEFAULT_EMPNID);

        // Get all the fwEmpnList where empnid does not contain UPDATED_EMPNID
        defaultFwEmpnShouldBeFound("empnid.doesNotContain=" + UPDATED_EMPNID);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where empn equals to DEFAULT_EMPN
        defaultFwEmpnShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the fwEmpnList where empn equals to UPDATED_EMPN
        defaultFwEmpnShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where empn not equals to DEFAULT_EMPN
        defaultFwEmpnShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the fwEmpnList where empn not equals to UPDATED_EMPN
        defaultFwEmpnShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultFwEmpnShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the fwEmpnList where empn equals to UPDATED_EMPN
        defaultFwEmpnShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where empn is not null
        defaultFwEmpnShouldBeFound("empn.specified=true");

        // Get all the fwEmpnList where empn is null
        defaultFwEmpnShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllFwEmpnsByEmpnContainsSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where empn contains DEFAULT_EMPN
        defaultFwEmpnShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the fwEmpnList where empn contains UPDATED_EMPN
        defaultFwEmpnShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where empn does not contain DEFAULT_EMPN
        defaultFwEmpnShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the fwEmpnList where empn does not contain UPDATED_EMPN
        defaultFwEmpnShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByDeptidIsEqualToSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where deptid equals to DEFAULT_DEPTID
        defaultFwEmpnShouldBeFound("deptid.equals=" + DEFAULT_DEPTID);

        // Get all the fwEmpnList where deptid equals to UPDATED_DEPTID
        defaultFwEmpnShouldNotBeFound("deptid.equals=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByDeptidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where deptid not equals to DEFAULT_DEPTID
        defaultFwEmpnShouldNotBeFound("deptid.notEquals=" + DEFAULT_DEPTID);

        // Get all the fwEmpnList where deptid not equals to UPDATED_DEPTID
        defaultFwEmpnShouldBeFound("deptid.notEquals=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByDeptidIsInShouldWork() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where deptid in DEFAULT_DEPTID or UPDATED_DEPTID
        defaultFwEmpnShouldBeFound("deptid.in=" + DEFAULT_DEPTID + "," + UPDATED_DEPTID);

        // Get all the fwEmpnList where deptid equals to UPDATED_DEPTID
        defaultFwEmpnShouldNotBeFound("deptid.in=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByDeptidIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where deptid is not null
        defaultFwEmpnShouldBeFound("deptid.specified=true");

        // Get all the fwEmpnList where deptid is null
        defaultFwEmpnShouldNotBeFound("deptid.specified=false");
    }

    @Test
    @Transactional
    void getAllFwEmpnsByDeptidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where deptid is greater than or equal to DEFAULT_DEPTID
        defaultFwEmpnShouldBeFound("deptid.greaterThanOrEqual=" + DEFAULT_DEPTID);

        // Get all the fwEmpnList where deptid is greater than or equal to UPDATED_DEPTID
        defaultFwEmpnShouldNotBeFound("deptid.greaterThanOrEqual=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByDeptidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where deptid is less than or equal to DEFAULT_DEPTID
        defaultFwEmpnShouldBeFound("deptid.lessThanOrEqual=" + DEFAULT_DEPTID);

        // Get all the fwEmpnList where deptid is less than or equal to SMALLER_DEPTID
        defaultFwEmpnShouldNotBeFound("deptid.lessThanOrEqual=" + SMALLER_DEPTID);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByDeptidIsLessThanSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where deptid is less than DEFAULT_DEPTID
        defaultFwEmpnShouldNotBeFound("deptid.lessThan=" + DEFAULT_DEPTID);

        // Get all the fwEmpnList where deptid is less than UPDATED_DEPTID
        defaultFwEmpnShouldBeFound("deptid.lessThan=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByDeptidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where deptid is greater than DEFAULT_DEPTID
        defaultFwEmpnShouldNotBeFound("deptid.greaterThan=" + DEFAULT_DEPTID);

        // Get all the fwEmpnList where deptid is greater than SMALLER_DEPTID
        defaultFwEmpnShouldBeFound("deptid.greaterThan=" + SMALLER_DEPTID);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where phone equals to DEFAULT_PHONE
        defaultFwEmpnShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the fwEmpnList where phone equals to UPDATED_PHONE
        defaultFwEmpnShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where phone not equals to DEFAULT_PHONE
        defaultFwEmpnShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the fwEmpnList where phone not equals to UPDATED_PHONE
        defaultFwEmpnShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultFwEmpnShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the fwEmpnList where phone equals to UPDATED_PHONE
        defaultFwEmpnShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where phone is not null
        defaultFwEmpnShouldBeFound("phone.specified=true");

        // Get all the fwEmpnList where phone is null
        defaultFwEmpnShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllFwEmpnsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where phone contains DEFAULT_PHONE
        defaultFwEmpnShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the fwEmpnList where phone contains UPDATED_PHONE
        defaultFwEmpnShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllFwEmpnsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        // Get all the fwEmpnList where phone does not contain DEFAULT_PHONE
        defaultFwEmpnShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the fwEmpnList where phone does not contain UPDATED_PHONE
        defaultFwEmpnShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFwEmpnShouldBeFound(String filter) throws Exception {
        restFwEmpnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwEmpn.getId().intValue())))
            .andExpect(jsonPath("$.[*].empnid").value(hasItem(DEFAULT_EMPNID)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].deptid").value(hasItem(DEFAULT_DEPTID.intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));

        // Check, that the count call also returns 1
        restFwEmpnMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFwEmpnShouldNotBeFound(String filter) throws Exception {
        restFwEmpnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFwEmpnMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFwEmpn() throws Exception {
        // Get the fwEmpn
        restFwEmpnMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFwEmpn() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        int databaseSizeBeforeUpdate = fwEmpnRepository.findAll().size();

        // Update the fwEmpn
        FwEmpn updatedFwEmpn = fwEmpnRepository.findById(fwEmpn.getId()).get();
        // Disconnect from session so that the updates on updatedFwEmpn are not directly saved in db
        em.detach(updatedFwEmpn);
        updatedFwEmpn.empnid(UPDATED_EMPNID).empn(UPDATED_EMPN).deptid(UPDATED_DEPTID).phone(UPDATED_PHONE);
        FwEmpnDTO fwEmpnDTO = fwEmpnMapper.toDto(updatedFwEmpn);

        restFwEmpnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fwEmpnDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwEmpnDTO))
            )
            .andExpect(status().isOk());

        // Validate the FwEmpn in the database
        List<FwEmpn> fwEmpnList = fwEmpnRepository.findAll();
        assertThat(fwEmpnList).hasSize(databaseSizeBeforeUpdate);
        FwEmpn testFwEmpn = fwEmpnList.get(fwEmpnList.size() - 1);
        assertThat(testFwEmpn.getEmpnid()).isEqualTo(UPDATED_EMPNID);
        assertThat(testFwEmpn.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testFwEmpn.getDeptid()).isEqualTo(UPDATED_DEPTID);
        assertThat(testFwEmpn.getPhone()).isEqualTo(UPDATED_PHONE);

        // Validate the FwEmpn in Elasticsearch
        verify(mockFwEmpnSearchRepository).save(testFwEmpn);
    }

    @Test
    @Transactional
    void putNonExistingFwEmpn() throws Exception {
        int databaseSizeBeforeUpdate = fwEmpnRepository.findAll().size();
        fwEmpn.setId(count.incrementAndGet());

        // Create the FwEmpn
        FwEmpnDTO fwEmpnDTO = fwEmpnMapper.toDto(fwEmpn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFwEmpnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fwEmpnDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwEmpnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwEmpn in the database
        List<FwEmpn> fwEmpnList = fwEmpnRepository.findAll();
        assertThat(fwEmpnList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwEmpn in Elasticsearch
        verify(mockFwEmpnSearchRepository, times(0)).save(fwEmpn);
    }

    @Test
    @Transactional
    void putWithIdMismatchFwEmpn() throws Exception {
        int databaseSizeBeforeUpdate = fwEmpnRepository.findAll().size();
        fwEmpn.setId(count.incrementAndGet());

        // Create the FwEmpn
        FwEmpnDTO fwEmpnDTO = fwEmpnMapper.toDto(fwEmpn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwEmpnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwEmpnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwEmpn in the database
        List<FwEmpn> fwEmpnList = fwEmpnRepository.findAll();
        assertThat(fwEmpnList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwEmpn in Elasticsearch
        verify(mockFwEmpnSearchRepository, times(0)).save(fwEmpn);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFwEmpn() throws Exception {
        int databaseSizeBeforeUpdate = fwEmpnRepository.findAll().size();
        fwEmpn.setId(count.incrementAndGet());

        // Create the FwEmpn
        FwEmpnDTO fwEmpnDTO = fwEmpnMapper.toDto(fwEmpn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwEmpnMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwEmpnDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FwEmpn in the database
        List<FwEmpn> fwEmpnList = fwEmpnRepository.findAll();
        assertThat(fwEmpnList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwEmpn in Elasticsearch
        verify(mockFwEmpnSearchRepository, times(0)).save(fwEmpn);
    }

    @Test
    @Transactional
    void partialUpdateFwEmpnWithPatch() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        int databaseSizeBeforeUpdate = fwEmpnRepository.findAll().size();

        // Update the fwEmpn using partial update
        FwEmpn partialUpdatedFwEmpn = new FwEmpn();
        partialUpdatedFwEmpn.setId(fwEmpn.getId());

        partialUpdatedFwEmpn.phone(UPDATED_PHONE);

        restFwEmpnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFwEmpn.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFwEmpn))
            )
            .andExpect(status().isOk());

        // Validate the FwEmpn in the database
        List<FwEmpn> fwEmpnList = fwEmpnRepository.findAll();
        assertThat(fwEmpnList).hasSize(databaseSizeBeforeUpdate);
        FwEmpn testFwEmpn = fwEmpnList.get(fwEmpnList.size() - 1);
        assertThat(testFwEmpn.getEmpnid()).isEqualTo(DEFAULT_EMPNID);
        assertThat(testFwEmpn.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testFwEmpn.getDeptid()).isEqualTo(DEFAULT_DEPTID);
        assertThat(testFwEmpn.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void fullUpdateFwEmpnWithPatch() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        int databaseSizeBeforeUpdate = fwEmpnRepository.findAll().size();

        // Update the fwEmpn using partial update
        FwEmpn partialUpdatedFwEmpn = new FwEmpn();
        partialUpdatedFwEmpn.setId(fwEmpn.getId());

        partialUpdatedFwEmpn.empnid(UPDATED_EMPNID).empn(UPDATED_EMPN).deptid(UPDATED_DEPTID).phone(UPDATED_PHONE);

        restFwEmpnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFwEmpn.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFwEmpn))
            )
            .andExpect(status().isOk());

        // Validate the FwEmpn in the database
        List<FwEmpn> fwEmpnList = fwEmpnRepository.findAll();
        assertThat(fwEmpnList).hasSize(databaseSizeBeforeUpdate);
        FwEmpn testFwEmpn = fwEmpnList.get(fwEmpnList.size() - 1);
        assertThat(testFwEmpn.getEmpnid()).isEqualTo(UPDATED_EMPNID);
        assertThat(testFwEmpn.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testFwEmpn.getDeptid()).isEqualTo(UPDATED_DEPTID);
        assertThat(testFwEmpn.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingFwEmpn() throws Exception {
        int databaseSizeBeforeUpdate = fwEmpnRepository.findAll().size();
        fwEmpn.setId(count.incrementAndGet());

        // Create the FwEmpn
        FwEmpnDTO fwEmpnDTO = fwEmpnMapper.toDto(fwEmpn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFwEmpnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fwEmpnDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fwEmpnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwEmpn in the database
        List<FwEmpn> fwEmpnList = fwEmpnRepository.findAll();
        assertThat(fwEmpnList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwEmpn in Elasticsearch
        verify(mockFwEmpnSearchRepository, times(0)).save(fwEmpn);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFwEmpn() throws Exception {
        int databaseSizeBeforeUpdate = fwEmpnRepository.findAll().size();
        fwEmpn.setId(count.incrementAndGet());

        // Create the FwEmpn
        FwEmpnDTO fwEmpnDTO = fwEmpnMapper.toDto(fwEmpn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwEmpnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fwEmpnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwEmpn in the database
        List<FwEmpn> fwEmpnList = fwEmpnRepository.findAll();
        assertThat(fwEmpnList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwEmpn in Elasticsearch
        verify(mockFwEmpnSearchRepository, times(0)).save(fwEmpn);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFwEmpn() throws Exception {
        int databaseSizeBeforeUpdate = fwEmpnRepository.findAll().size();
        fwEmpn.setId(count.incrementAndGet());

        // Create the FwEmpn
        FwEmpnDTO fwEmpnDTO = fwEmpnMapper.toDto(fwEmpn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwEmpnMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fwEmpnDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FwEmpn in the database
        List<FwEmpn> fwEmpnList = fwEmpnRepository.findAll();
        assertThat(fwEmpnList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwEmpn in Elasticsearch
        verify(mockFwEmpnSearchRepository, times(0)).save(fwEmpn);
    }

    @Test
    @Transactional
    void deleteFwEmpn() throws Exception {
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);

        int databaseSizeBeforeDelete = fwEmpnRepository.findAll().size();

        // Delete the fwEmpn
        restFwEmpnMockMvc
            .perform(delete(ENTITY_API_URL_ID, fwEmpn.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FwEmpn> fwEmpnList = fwEmpnRepository.findAll();
        assertThat(fwEmpnList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FwEmpn in Elasticsearch
        verify(mockFwEmpnSearchRepository, times(1)).deleteById(fwEmpn.getId());
    }

    @Test
    @Transactional
    void searchFwEmpn() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        fwEmpnRepository.saveAndFlush(fwEmpn);
        when(mockFwEmpnSearchRepository.search(queryStringQuery("id:" + fwEmpn.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fwEmpn), PageRequest.of(0, 1), 1));

        // Search the fwEmpn
        restFwEmpnMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + fwEmpn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwEmpn.getId().intValue())))
            .andExpect(jsonPath("$.[*].empnid").value(hasItem(DEFAULT_EMPNID)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].deptid").value(hasItem(DEFAULT_DEPTID.intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
}
