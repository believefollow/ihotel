package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DEmpn;
import ihotel.app.repository.DEmpnRepository;
import ihotel.app.repository.search.DEmpnSearchRepository;
import ihotel.app.service.criteria.DEmpnCriteria;
import ihotel.app.service.dto.DEmpnDTO;
import ihotel.app.service.mapper.DEmpnMapper;
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
 * Integration tests for the {@link DEmpnResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DEmpnResourceIT {

    private static final Long DEFAULT_EMPNID = 1L;
    private static final Long UPDATED_EMPNID = 2L;
    private static final Long SMALLER_EMPNID = 1L - 1L;

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final Long DEFAULT_DEPTID = 1L;
    private static final Long UPDATED_DEPTID = 2L;
    private static final Long SMALLER_DEPTID = 1L - 1L;

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/d-empns";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-empns";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DEmpnRepository dEmpnRepository;

    @Autowired
    private DEmpnMapper dEmpnMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DEmpnSearchRepositoryMockConfiguration
     */
    @Autowired
    private DEmpnSearchRepository mockDEmpnSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDEmpnMockMvc;

    private DEmpn dEmpn;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DEmpn createEntity(EntityManager em) {
        DEmpn dEmpn = new DEmpn().empnid(DEFAULT_EMPNID).empn(DEFAULT_EMPN).deptid(DEFAULT_DEPTID).phone(DEFAULT_PHONE);
        return dEmpn;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DEmpn createUpdatedEntity(EntityManager em) {
        DEmpn dEmpn = new DEmpn().empnid(UPDATED_EMPNID).empn(UPDATED_EMPN).deptid(UPDATED_DEPTID).phone(UPDATED_PHONE);
        return dEmpn;
    }

    @BeforeEach
    public void initTest() {
        dEmpn = createEntity(em);
    }

    @Test
    @Transactional
    void createDEmpn() throws Exception {
        int databaseSizeBeforeCreate = dEmpnRepository.findAll().size();
        // Create the DEmpn
        DEmpnDTO dEmpnDTO = dEmpnMapper.toDto(dEmpn);
        restDEmpnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dEmpnDTO)))
            .andExpect(status().isCreated());

        // Validate the DEmpn in the database
        List<DEmpn> dEmpnList = dEmpnRepository.findAll();
        assertThat(dEmpnList).hasSize(databaseSizeBeforeCreate + 1);
        DEmpn testDEmpn = dEmpnList.get(dEmpnList.size() - 1);
        assertThat(testDEmpn.getEmpnid()).isEqualTo(DEFAULT_EMPNID);
        assertThat(testDEmpn.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testDEmpn.getDeptid()).isEqualTo(DEFAULT_DEPTID);
        assertThat(testDEmpn.getPhone()).isEqualTo(DEFAULT_PHONE);

        // Validate the DEmpn in Elasticsearch
        verify(mockDEmpnSearchRepository, times(1)).save(testDEmpn);
    }

    @Test
    @Transactional
    void createDEmpnWithExistingId() throws Exception {
        // Create the DEmpn with an existing ID
        dEmpn.setId(1L);
        DEmpnDTO dEmpnDTO = dEmpnMapper.toDto(dEmpn);

        int databaseSizeBeforeCreate = dEmpnRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDEmpnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dEmpnDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DEmpn in the database
        List<DEmpn> dEmpnList = dEmpnRepository.findAll();
        assertThat(dEmpnList).hasSize(databaseSizeBeforeCreate);

        // Validate the DEmpn in Elasticsearch
        verify(mockDEmpnSearchRepository, times(0)).save(dEmpn);
    }

    @Test
    @Transactional
    void checkEmpnidIsRequired() throws Exception {
        int databaseSizeBeforeTest = dEmpnRepository.findAll().size();
        // set the field null
        dEmpn.setEmpnid(null);

        // Create the DEmpn, which fails.
        DEmpnDTO dEmpnDTO = dEmpnMapper.toDto(dEmpn);

        restDEmpnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dEmpnDTO)))
            .andExpect(status().isBadRequest());

        List<DEmpn> dEmpnList = dEmpnRepository.findAll();
        assertThat(dEmpnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmpnIsRequired() throws Exception {
        int databaseSizeBeforeTest = dEmpnRepository.findAll().size();
        // set the field null
        dEmpn.setEmpn(null);

        // Create the DEmpn, which fails.
        DEmpnDTO dEmpnDTO = dEmpnMapper.toDto(dEmpn);

        restDEmpnMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dEmpnDTO)))
            .andExpect(status().isBadRequest());

        List<DEmpn> dEmpnList = dEmpnRepository.findAll();
        assertThat(dEmpnList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDEmpns() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList
        restDEmpnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dEmpn.getId().intValue())))
            .andExpect(jsonPath("$.[*].empnid").value(hasItem(DEFAULT_EMPNID.intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].deptid").value(hasItem(DEFAULT_DEPTID.intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    @Transactional
    void getDEmpn() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get the dEmpn
        restDEmpnMockMvc
            .perform(get(ENTITY_API_URL_ID, dEmpn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dEmpn.getId().intValue()))
            .andExpect(jsonPath("$.empnid").value(DEFAULT_EMPNID.intValue()))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.deptid").value(DEFAULT_DEPTID.intValue()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    void getDEmpnsByIdFiltering() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        Long id = dEmpn.getId();

        defaultDEmpnShouldBeFound("id.equals=" + id);
        defaultDEmpnShouldNotBeFound("id.notEquals=" + id);

        defaultDEmpnShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDEmpnShouldNotBeFound("id.greaterThan=" + id);

        defaultDEmpnShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDEmpnShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDEmpnsByEmpnidIsEqualToSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where empnid equals to DEFAULT_EMPNID
        defaultDEmpnShouldBeFound("empnid.equals=" + DEFAULT_EMPNID);

        // Get all the dEmpnList where empnid equals to UPDATED_EMPNID
        defaultDEmpnShouldNotBeFound("empnid.equals=" + UPDATED_EMPNID);
    }

    @Test
    @Transactional
    void getAllDEmpnsByEmpnidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where empnid not equals to DEFAULT_EMPNID
        defaultDEmpnShouldNotBeFound("empnid.notEquals=" + DEFAULT_EMPNID);

        // Get all the dEmpnList where empnid not equals to UPDATED_EMPNID
        defaultDEmpnShouldBeFound("empnid.notEquals=" + UPDATED_EMPNID);
    }

    @Test
    @Transactional
    void getAllDEmpnsByEmpnidIsInShouldWork() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where empnid in DEFAULT_EMPNID or UPDATED_EMPNID
        defaultDEmpnShouldBeFound("empnid.in=" + DEFAULT_EMPNID + "," + UPDATED_EMPNID);

        // Get all the dEmpnList where empnid equals to UPDATED_EMPNID
        defaultDEmpnShouldNotBeFound("empnid.in=" + UPDATED_EMPNID);
    }

    @Test
    @Transactional
    void getAllDEmpnsByEmpnidIsNullOrNotNull() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where empnid is not null
        defaultDEmpnShouldBeFound("empnid.specified=true");

        // Get all the dEmpnList where empnid is null
        defaultDEmpnShouldNotBeFound("empnid.specified=false");
    }

    @Test
    @Transactional
    void getAllDEmpnsByEmpnidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where empnid is greater than or equal to DEFAULT_EMPNID
        defaultDEmpnShouldBeFound("empnid.greaterThanOrEqual=" + DEFAULT_EMPNID);

        // Get all the dEmpnList where empnid is greater than or equal to UPDATED_EMPNID
        defaultDEmpnShouldNotBeFound("empnid.greaterThanOrEqual=" + UPDATED_EMPNID);
    }

    @Test
    @Transactional
    void getAllDEmpnsByEmpnidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where empnid is less than or equal to DEFAULT_EMPNID
        defaultDEmpnShouldBeFound("empnid.lessThanOrEqual=" + DEFAULT_EMPNID);

        // Get all the dEmpnList where empnid is less than or equal to SMALLER_EMPNID
        defaultDEmpnShouldNotBeFound("empnid.lessThanOrEqual=" + SMALLER_EMPNID);
    }

    @Test
    @Transactional
    void getAllDEmpnsByEmpnidIsLessThanSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where empnid is less than DEFAULT_EMPNID
        defaultDEmpnShouldNotBeFound("empnid.lessThan=" + DEFAULT_EMPNID);

        // Get all the dEmpnList where empnid is less than UPDATED_EMPNID
        defaultDEmpnShouldBeFound("empnid.lessThan=" + UPDATED_EMPNID);
    }

    @Test
    @Transactional
    void getAllDEmpnsByEmpnidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where empnid is greater than DEFAULT_EMPNID
        defaultDEmpnShouldNotBeFound("empnid.greaterThan=" + DEFAULT_EMPNID);

        // Get all the dEmpnList where empnid is greater than SMALLER_EMPNID
        defaultDEmpnShouldBeFound("empnid.greaterThan=" + SMALLER_EMPNID);
    }

    @Test
    @Transactional
    void getAllDEmpnsByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where empn equals to DEFAULT_EMPN
        defaultDEmpnShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the dEmpnList where empn equals to UPDATED_EMPN
        defaultDEmpnShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDEmpnsByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where empn not equals to DEFAULT_EMPN
        defaultDEmpnShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the dEmpnList where empn not equals to UPDATED_EMPN
        defaultDEmpnShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDEmpnsByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultDEmpnShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the dEmpnList where empn equals to UPDATED_EMPN
        defaultDEmpnShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDEmpnsByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where empn is not null
        defaultDEmpnShouldBeFound("empn.specified=true");

        // Get all the dEmpnList where empn is null
        defaultDEmpnShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllDEmpnsByEmpnContainsSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where empn contains DEFAULT_EMPN
        defaultDEmpnShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the dEmpnList where empn contains UPDATED_EMPN
        defaultDEmpnShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDEmpnsByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where empn does not contain DEFAULT_EMPN
        defaultDEmpnShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the dEmpnList where empn does not contain UPDATED_EMPN
        defaultDEmpnShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllDEmpnsByDeptidIsEqualToSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where deptid equals to DEFAULT_DEPTID
        defaultDEmpnShouldBeFound("deptid.equals=" + DEFAULT_DEPTID);

        // Get all the dEmpnList where deptid equals to UPDATED_DEPTID
        defaultDEmpnShouldNotBeFound("deptid.equals=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllDEmpnsByDeptidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where deptid not equals to DEFAULT_DEPTID
        defaultDEmpnShouldNotBeFound("deptid.notEquals=" + DEFAULT_DEPTID);

        // Get all the dEmpnList where deptid not equals to UPDATED_DEPTID
        defaultDEmpnShouldBeFound("deptid.notEquals=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllDEmpnsByDeptidIsInShouldWork() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where deptid in DEFAULT_DEPTID or UPDATED_DEPTID
        defaultDEmpnShouldBeFound("deptid.in=" + DEFAULT_DEPTID + "," + UPDATED_DEPTID);

        // Get all the dEmpnList where deptid equals to UPDATED_DEPTID
        defaultDEmpnShouldNotBeFound("deptid.in=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllDEmpnsByDeptidIsNullOrNotNull() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where deptid is not null
        defaultDEmpnShouldBeFound("deptid.specified=true");

        // Get all the dEmpnList where deptid is null
        defaultDEmpnShouldNotBeFound("deptid.specified=false");
    }

    @Test
    @Transactional
    void getAllDEmpnsByDeptidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where deptid is greater than or equal to DEFAULT_DEPTID
        defaultDEmpnShouldBeFound("deptid.greaterThanOrEqual=" + DEFAULT_DEPTID);

        // Get all the dEmpnList where deptid is greater than or equal to UPDATED_DEPTID
        defaultDEmpnShouldNotBeFound("deptid.greaterThanOrEqual=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllDEmpnsByDeptidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where deptid is less than or equal to DEFAULT_DEPTID
        defaultDEmpnShouldBeFound("deptid.lessThanOrEqual=" + DEFAULT_DEPTID);

        // Get all the dEmpnList where deptid is less than or equal to SMALLER_DEPTID
        defaultDEmpnShouldNotBeFound("deptid.lessThanOrEqual=" + SMALLER_DEPTID);
    }

    @Test
    @Transactional
    void getAllDEmpnsByDeptidIsLessThanSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where deptid is less than DEFAULT_DEPTID
        defaultDEmpnShouldNotBeFound("deptid.lessThan=" + DEFAULT_DEPTID);

        // Get all the dEmpnList where deptid is less than UPDATED_DEPTID
        defaultDEmpnShouldBeFound("deptid.lessThan=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllDEmpnsByDeptidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where deptid is greater than DEFAULT_DEPTID
        defaultDEmpnShouldNotBeFound("deptid.greaterThan=" + DEFAULT_DEPTID);

        // Get all the dEmpnList where deptid is greater than SMALLER_DEPTID
        defaultDEmpnShouldBeFound("deptid.greaterThan=" + SMALLER_DEPTID);
    }

    @Test
    @Transactional
    void getAllDEmpnsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where phone equals to DEFAULT_PHONE
        defaultDEmpnShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the dEmpnList where phone equals to UPDATED_PHONE
        defaultDEmpnShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllDEmpnsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where phone not equals to DEFAULT_PHONE
        defaultDEmpnShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the dEmpnList where phone not equals to UPDATED_PHONE
        defaultDEmpnShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllDEmpnsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultDEmpnShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the dEmpnList where phone equals to UPDATED_PHONE
        defaultDEmpnShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllDEmpnsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where phone is not null
        defaultDEmpnShouldBeFound("phone.specified=true");

        // Get all the dEmpnList where phone is null
        defaultDEmpnShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllDEmpnsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where phone contains DEFAULT_PHONE
        defaultDEmpnShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the dEmpnList where phone contains UPDATED_PHONE
        defaultDEmpnShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllDEmpnsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        // Get all the dEmpnList where phone does not contain DEFAULT_PHONE
        defaultDEmpnShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the dEmpnList where phone does not contain UPDATED_PHONE
        defaultDEmpnShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDEmpnShouldBeFound(String filter) throws Exception {
        restDEmpnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dEmpn.getId().intValue())))
            .andExpect(jsonPath("$.[*].empnid").value(hasItem(DEFAULT_EMPNID.intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].deptid").value(hasItem(DEFAULT_DEPTID.intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));

        // Check, that the count call also returns 1
        restDEmpnMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDEmpnShouldNotBeFound(String filter) throws Exception {
        restDEmpnMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDEmpnMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDEmpn() throws Exception {
        // Get the dEmpn
        restDEmpnMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDEmpn() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        int databaseSizeBeforeUpdate = dEmpnRepository.findAll().size();

        // Update the dEmpn
        DEmpn updatedDEmpn = dEmpnRepository.findById(dEmpn.getId()).get();
        // Disconnect from session so that the updates on updatedDEmpn are not directly saved in db
        em.detach(updatedDEmpn);
        updatedDEmpn.empnid(UPDATED_EMPNID).empn(UPDATED_EMPN).deptid(UPDATED_DEPTID).phone(UPDATED_PHONE);
        DEmpnDTO dEmpnDTO = dEmpnMapper.toDto(updatedDEmpn);

        restDEmpnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dEmpnDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dEmpnDTO))
            )
            .andExpect(status().isOk());

        // Validate the DEmpn in the database
        List<DEmpn> dEmpnList = dEmpnRepository.findAll();
        assertThat(dEmpnList).hasSize(databaseSizeBeforeUpdate);
        DEmpn testDEmpn = dEmpnList.get(dEmpnList.size() - 1);
        assertThat(testDEmpn.getEmpnid()).isEqualTo(UPDATED_EMPNID);
        assertThat(testDEmpn.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testDEmpn.getDeptid()).isEqualTo(UPDATED_DEPTID);
        assertThat(testDEmpn.getPhone()).isEqualTo(UPDATED_PHONE);

        // Validate the DEmpn in Elasticsearch
        verify(mockDEmpnSearchRepository).save(testDEmpn);
    }

    @Test
    @Transactional
    void putNonExistingDEmpn() throws Exception {
        int databaseSizeBeforeUpdate = dEmpnRepository.findAll().size();
        dEmpn.setId(count.incrementAndGet());

        // Create the DEmpn
        DEmpnDTO dEmpnDTO = dEmpnMapper.toDto(dEmpn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDEmpnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dEmpnDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dEmpnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DEmpn in the database
        List<DEmpn> dEmpnList = dEmpnRepository.findAll();
        assertThat(dEmpnList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DEmpn in Elasticsearch
        verify(mockDEmpnSearchRepository, times(0)).save(dEmpn);
    }

    @Test
    @Transactional
    void putWithIdMismatchDEmpn() throws Exception {
        int databaseSizeBeforeUpdate = dEmpnRepository.findAll().size();
        dEmpn.setId(count.incrementAndGet());

        // Create the DEmpn
        DEmpnDTO dEmpnDTO = dEmpnMapper.toDto(dEmpn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDEmpnMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dEmpnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DEmpn in the database
        List<DEmpn> dEmpnList = dEmpnRepository.findAll();
        assertThat(dEmpnList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DEmpn in Elasticsearch
        verify(mockDEmpnSearchRepository, times(0)).save(dEmpn);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDEmpn() throws Exception {
        int databaseSizeBeforeUpdate = dEmpnRepository.findAll().size();
        dEmpn.setId(count.incrementAndGet());

        // Create the DEmpn
        DEmpnDTO dEmpnDTO = dEmpnMapper.toDto(dEmpn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDEmpnMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dEmpnDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DEmpn in the database
        List<DEmpn> dEmpnList = dEmpnRepository.findAll();
        assertThat(dEmpnList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DEmpn in Elasticsearch
        verify(mockDEmpnSearchRepository, times(0)).save(dEmpn);
    }

    @Test
    @Transactional
    void partialUpdateDEmpnWithPatch() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        int databaseSizeBeforeUpdate = dEmpnRepository.findAll().size();

        // Update the dEmpn using partial update
        DEmpn partialUpdatedDEmpn = new DEmpn();
        partialUpdatedDEmpn.setId(dEmpn.getId());

        partialUpdatedDEmpn.deptid(UPDATED_DEPTID);

        restDEmpnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDEmpn.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDEmpn))
            )
            .andExpect(status().isOk());

        // Validate the DEmpn in the database
        List<DEmpn> dEmpnList = dEmpnRepository.findAll();
        assertThat(dEmpnList).hasSize(databaseSizeBeforeUpdate);
        DEmpn testDEmpn = dEmpnList.get(dEmpnList.size() - 1);
        assertThat(testDEmpn.getEmpnid()).isEqualTo(DEFAULT_EMPNID);
        assertThat(testDEmpn.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testDEmpn.getDeptid()).isEqualTo(UPDATED_DEPTID);
        assertThat(testDEmpn.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void fullUpdateDEmpnWithPatch() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        int databaseSizeBeforeUpdate = dEmpnRepository.findAll().size();

        // Update the dEmpn using partial update
        DEmpn partialUpdatedDEmpn = new DEmpn();
        partialUpdatedDEmpn.setId(dEmpn.getId());

        partialUpdatedDEmpn.empnid(UPDATED_EMPNID).empn(UPDATED_EMPN).deptid(UPDATED_DEPTID).phone(UPDATED_PHONE);

        restDEmpnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDEmpn.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDEmpn))
            )
            .andExpect(status().isOk());

        // Validate the DEmpn in the database
        List<DEmpn> dEmpnList = dEmpnRepository.findAll();
        assertThat(dEmpnList).hasSize(databaseSizeBeforeUpdate);
        DEmpn testDEmpn = dEmpnList.get(dEmpnList.size() - 1);
        assertThat(testDEmpn.getEmpnid()).isEqualTo(UPDATED_EMPNID);
        assertThat(testDEmpn.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testDEmpn.getDeptid()).isEqualTo(UPDATED_DEPTID);
        assertThat(testDEmpn.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingDEmpn() throws Exception {
        int databaseSizeBeforeUpdate = dEmpnRepository.findAll().size();
        dEmpn.setId(count.incrementAndGet());

        // Create the DEmpn
        DEmpnDTO dEmpnDTO = dEmpnMapper.toDto(dEmpn);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDEmpnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dEmpnDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dEmpnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DEmpn in the database
        List<DEmpn> dEmpnList = dEmpnRepository.findAll();
        assertThat(dEmpnList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DEmpn in Elasticsearch
        verify(mockDEmpnSearchRepository, times(0)).save(dEmpn);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDEmpn() throws Exception {
        int databaseSizeBeforeUpdate = dEmpnRepository.findAll().size();
        dEmpn.setId(count.incrementAndGet());

        // Create the DEmpn
        DEmpnDTO dEmpnDTO = dEmpnMapper.toDto(dEmpn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDEmpnMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dEmpnDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DEmpn in the database
        List<DEmpn> dEmpnList = dEmpnRepository.findAll();
        assertThat(dEmpnList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DEmpn in Elasticsearch
        verify(mockDEmpnSearchRepository, times(0)).save(dEmpn);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDEmpn() throws Exception {
        int databaseSizeBeforeUpdate = dEmpnRepository.findAll().size();
        dEmpn.setId(count.incrementAndGet());

        // Create the DEmpn
        DEmpnDTO dEmpnDTO = dEmpnMapper.toDto(dEmpn);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDEmpnMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dEmpnDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DEmpn in the database
        List<DEmpn> dEmpnList = dEmpnRepository.findAll();
        assertThat(dEmpnList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DEmpn in Elasticsearch
        verify(mockDEmpnSearchRepository, times(0)).save(dEmpn);
    }

    @Test
    @Transactional
    void deleteDEmpn() throws Exception {
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);

        int databaseSizeBeforeDelete = dEmpnRepository.findAll().size();

        // Delete the dEmpn
        restDEmpnMockMvc
            .perform(delete(ENTITY_API_URL_ID, dEmpn.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DEmpn> dEmpnList = dEmpnRepository.findAll();
        assertThat(dEmpnList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DEmpn in Elasticsearch
        verify(mockDEmpnSearchRepository, times(1)).deleteById(dEmpn.getId());
    }

    @Test
    @Transactional
    void searchDEmpn() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dEmpnRepository.saveAndFlush(dEmpn);
        when(mockDEmpnSearchRepository.search(queryStringQuery("id:" + dEmpn.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dEmpn), PageRequest.of(0, 1), 1));

        // Search the dEmpn
        restDEmpnMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dEmpn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dEmpn.getId().intValue())))
            .andExpect(jsonPath("$.[*].empnid").value(hasItem(DEFAULT_EMPNID.intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].deptid").value(hasItem(DEFAULT_DEPTID.intValue())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
}
