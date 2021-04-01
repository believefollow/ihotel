package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DType;
import ihotel.app.repository.DTypeRepository;
import ihotel.app.repository.search.DTypeSearchRepository;
import ihotel.app.service.criteria.DTypeCriteria;
import ihotel.app.service.dto.DTypeDTO;
import ihotel.app.service.mapper.DTypeMapper;
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
 * Integration tests for the {@link DTypeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DTypeResourceIT {

    private static final Long DEFAULT_TYPEID = 1L;
    private static final Long UPDATED_TYPEID = 2L;
    private static final Long SMALLER_TYPEID = 1L - 1L;

    private static final String DEFAULT_TYPENAME = "AAAAAAAAAA";
    private static final String UPDATED_TYPENAME = "BBBBBBBBBB";

    private static final Long DEFAULT_FATHERID = 1L;
    private static final Long UPDATED_FATHERID = 2L;
    private static final Long SMALLER_FATHERID = 1L - 1L;

    private static final Long DEFAULT_DISABLED = 1L;
    private static final Long UPDATED_DISABLED = 2L;
    private static final Long SMALLER_DISABLED = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/d-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-types";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DTypeRepository dTypeRepository;

    @Autowired
    private DTypeMapper dTypeMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private DTypeSearchRepository mockDTypeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDTypeMockMvc;

    private DType dType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DType createEntity(EntityManager em) {
        DType dType = new DType().typeid(DEFAULT_TYPEID).typename(DEFAULT_TYPENAME).fatherid(DEFAULT_FATHERID).disabled(DEFAULT_DISABLED);
        return dType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DType createUpdatedEntity(EntityManager em) {
        DType dType = new DType().typeid(UPDATED_TYPEID).typename(UPDATED_TYPENAME).fatherid(UPDATED_FATHERID).disabled(UPDATED_DISABLED);
        return dType;
    }

    @BeforeEach
    public void initTest() {
        dType = createEntity(em);
    }

    @Test
    @Transactional
    void createDType() throws Exception {
        int databaseSizeBeforeCreate = dTypeRepository.findAll().size();
        // Create the DType
        DTypeDTO dTypeDTO = dTypeMapper.toDto(dType);
        restDTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dTypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DType in the database
        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeCreate + 1);
        DType testDType = dTypeList.get(dTypeList.size() - 1);
        assertThat(testDType.getTypeid()).isEqualTo(DEFAULT_TYPEID);
        assertThat(testDType.getTypename()).isEqualTo(DEFAULT_TYPENAME);
        assertThat(testDType.getFatherid()).isEqualTo(DEFAULT_FATHERID);
        assertThat(testDType.getDisabled()).isEqualTo(DEFAULT_DISABLED);

        // Validate the DType in Elasticsearch
        verify(mockDTypeSearchRepository, times(1)).save(testDType);
    }

    @Test
    @Transactional
    void createDTypeWithExistingId() throws Exception {
        // Create the DType with an existing ID
        dType.setId(1L);
        DTypeDTO dTypeDTO = dTypeMapper.toDto(dType);

        int databaseSizeBeforeCreate = dTypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dTypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DType in the database
        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the DType in Elasticsearch
        verify(mockDTypeSearchRepository, times(0)).save(dType);
    }

    @Test
    @Transactional
    void checkTypeidIsRequired() throws Exception {
        int databaseSizeBeforeTest = dTypeRepository.findAll().size();
        // set the field null
        dType.setTypeid(null);

        // Create the DType, which fails.
        DTypeDTO dTypeDTO = dTypeMapper.toDto(dType);

        restDTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dTypeDTO)))
            .andExpect(status().isBadRequest());

        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkTypenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dTypeRepository.findAll().size();
        // set the field null
        dType.setTypename(null);

        // Create the DType, which fails.
        DTypeDTO dTypeDTO = dTypeMapper.toDto(dType);

        restDTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dTypeDTO)))
            .andExpect(status().isBadRequest());

        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFatheridIsRequired() throws Exception {
        int databaseSizeBeforeTest = dTypeRepository.findAll().size();
        // set the field null
        dType.setFatherid(null);

        // Create the DType, which fails.
        DTypeDTO dTypeDTO = dTypeMapper.toDto(dType);

        restDTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dTypeDTO)))
            .andExpect(status().isBadRequest());

        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDTypes() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList
        restDTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dType.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeid").value(hasItem(DEFAULT_TYPEID.intValue())))
            .andExpect(jsonPath("$.[*].typename").value(hasItem(DEFAULT_TYPENAME)))
            .andExpect(jsonPath("$.[*].fatherid").value(hasItem(DEFAULT_FATHERID.intValue())))
            .andExpect(jsonPath("$.[*].disabled").value(hasItem(DEFAULT_DISABLED.intValue())));
    }

    @Test
    @Transactional
    void getDType() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get the dType
        restDTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, dType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dType.getId().intValue()))
            .andExpect(jsonPath("$.typeid").value(DEFAULT_TYPEID.intValue()))
            .andExpect(jsonPath("$.typename").value(DEFAULT_TYPENAME))
            .andExpect(jsonPath("$.fatherid").value(DEFAULT_FATHERID.intValue()))
            .andExpect(jsonPath("$.disabled").value(DEFAULT_DISABLED.intValue()));
    }

    @Test
    @Transactional
    void getDTypesByIdFiltering() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        Long id = dType.getId();

        defaultDTypeShouldBeFound("id.equals=" + id);
        defaultDTypeShouldNotBeFound("id.notEquals=" + id);

        defaultDTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultDTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDTypesByTypeidIsEqualToSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where typeid equals to DEFAULT_TYPEID
        defaultDTypeShouldBeFound("typeid.equals=" + DEFAULT_TYPEID);

        // Get all the dTypeList where typeid equals to UPDATED_TYPEID
        defaultDTypeShouldNotBeFound("typeid.equals=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllDTypesByTypeidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where typeid not equals to DEFAULT_TYPEID
        defaultDTypeShouldNotBeFound("typeid.notEquals=" + DEFAULT_TYPEID);

        // Get all the dTypeList where typeid not equals to UPDATED_TYPEID
        defaultDTypeShouldBeFound("typeid.notEquals=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllDTypesByTypeidIsInShouldWork() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where typeid in DEFAULT_TYPEID or UPDATED_TYPEID
        defaultDTypeShouldBeFound("typeid.in=" + DEFAULT_TYPEID + "," + UPDATED_TYPEID);

        // Get all the dTypeList where typeid equals to UPDATED_TYPEID
        defaultDTypeShouldNotBeFound("typeid.in=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllDTypesByTypeidIsNullOrNotNull() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where typeid is not null
        defaultDTypeShouldBeFound("typeid.specified=true");

        // Get all the dTypeList where typeid is null
        defaultDTypeShouldNotBeFound("typeid.specified=false");
    }

    @Test
    @Transactional
    void getAllDTypesByTypeidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where typeid is greater than or equal to DEFAULT_TYPEID
        defaultDTypeShouldBeFound("typeid.greaterThanOrEqual=" + DEFAULT_TYPEID);

        // Get all the dTypeList where typeid is greater than or equal to UPDATED_TYPEID
        defaultDTypeShouldNotBeFound("typeid.greaterThanOrEqual=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllDTypesByTypeidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where typeid is less than or equal to DEFAULT_TYPEID
        defaultDTypeShouldBeFound("typeid.lessThanOrEqual=" + DEFAULT_TYPEID);

        // Get all the dTypeList where typeid is less than or equal to SMALLER_TYPEID
        defaultDTypeShouldNotBeFound("typeid.lessThanOrEqual=" + SMALLER_TYPEID);
    }

    @Test
    @Transactional
    void getAllDTypesByTypeidIsLessThanSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where typeid is less than DEFAULT_TYPEID
        defaultDTypeShouldNotBeFound("typeid.lessThan=" + DEFAULT_TYPEID);

        // Get all the dTypeList where typeid is less than UPDATED_TYPEID
        defaultDTypeShouldBeFound("typeid.lessThan=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllDTypesByTypeidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where typeid is greater than DEFAULT_TYPEID
        defaultDTypeShouldNotBeFound("typeid.greaterThan=" + DEFAULT_TYPEID);

        // Get all the dTypeList where typeid is greater than SMALLER_TYPEID
        defaultDTypeShouldBeFound("typeid.greaterThan=" + SMALLER_TYPEID);
    }

    @Test
    @Transactional
    void getAllDTypesByTypenameIsEqualToSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where typename equals to DEFAULT_TYPENAME
        defaultDTypeShouldBeFound("typename.equals=" + DEFAULT_TYPENAME);

        // Get all the dTypeList where typename equals to UPDATED_TYPENAME
        defaultDTypeShouldNotBeFound("typename.equals=" + UPDATED_TYPENAME);
    }

    @Test
    @Transactional
    void getAllDTypesByTypenameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where typename not equals to DEFAULT_TYPENAME
        defaultDTypeShouldNotBeFound("typename.notEquals=" + DEFAULT_TYPENAME);

        // Get all the dTypeList where typename not equals to UPDATED_TYPENAME
        defaultDTypeShouldBeFound("typename.notEquals=" + UPDATED_TYPENAME);
    }

    @Test
    @Transactional
    void getAllDTypesByTypenameIsInShouldWork() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where typename in DEFAULT_TYPENAME or UPDATED_TYPENAME
        defaultDTypeShouldBeFound("typename.in=" + DEFAULT_TYPENAME + "," + UPDATED_TYPENAME);

        // Get all the dTypeList where typename equals to UPDATED_TYPENAME
        defaultDTypeShouldNotBeFound("typename.in=" + UPDATED_TYPENAME);
    }

    @Test
    @Transactional
    void getAllDTypesByTypenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where typename is not null
        defaultDTypeShouldBeFound("typename.specified=true");

        // Get all the dTypeList where typename is null
        defaultDTypeShouldNotBeFound("typename.specified=false");
    }

    @Test
    @Transactional
    void getAllDTypesByTypenameContainsSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where typename contains DEFAULT_TYPENAME
        defaultDTypeShouldBeFound("typename.contains=" + DEFAULT_TYPENAME);

        // Get all the dTypeList where typename contains UPDATED_TYPENAME
        defaultDTypeShouldNotBeFound("typename.contains=" + UPDATED_TYPENAME);
    }

    @Test
    @Transactional
    void getAllDTypesByTypenameNotContainsSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where typename does not contain DEFAULT_TYPENAME
        defaultDTypeShouldNotBeFound("typename.doesNotContain=" + DEFAULT_TYPENAME);

        // Get all the dTypeList where typename does not contain UPDATED_TYPENAME
        defaultDTypeShouldBeFound("typename.doesNotContain=" + UPDATED_TYPENAME);
    }

    @Test
    @Transactional
    void getAllDTypesByFatheridIsEqualToSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where fatherid equals to DEFAULT_FATHERID
        defaultDTypeShouldBeFound("fatherid.equals=" + DEFAULT_FATHERID);

        // Get all the dTypeList where fatherid equals to UPDATED_FATHERID
        defaultDTypeShouldNotBeFound("fatherid.equals=" + UPDATED_FATHERID);
    }

    @Test
    @Transactional
    void getAllDTypesByFatheridIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where fatherid not equals to DEFAULT_FATHERID
        defaultDTypeShouldNotBeFound("fatherid.notEquals=" + DEFAULT_FATHERID);

        // Get all the dTypeList where fatherid not equals to UPDATED_FATHERID
        defaultDTypeShouldBeFound("fatherid.notEquals=" + UPDATED_FATHERID);
    }

    @Test
    @Transactional
    void getAllDTypesByFatheridIsInShouldWork() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where fatherid in DEFAULT_FATHERID or UPDATED_FATHERID
        defaultDTypeShouldBeFound("fatherid.in=" + DEFAULT_FATHERID + "," + UPDATED_FATHERID);

        // Get all the dTypeList where fatherid equals to UPDATED_FATHERID
        defaultDTypeShouldNotBeFound("fatherid.in=" + UPDATED_FATHERID);
    }

    @Test
    @Transactional
    void getAllDTypesByFatheridIsNullOrNotNull() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where fatherid is not null
        defaultDTypeShouldBeFound("fatherid.specified=true");

        // Get all the dTypeList where fatherid is null
        defaultDTypeShouldNotBeFound("fatherid.specified=false");
    }

    @Test
    @Transactional
    void getAllDTypesByFatheridIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where fatherid is greater than or equal to DEFAULT_FATHERID
        defaultDTypeShouldBeFound("fatherid.greaterThanOrEqual=" + DEFAULT_FATHERID);

        // Get all the dTypeList where fatherid is greater than or equal to UPDATED_FATHERID
        defaultDTypeShouldNotBeFound("fatherid.greaterThanOrEqual=" + UPDATED_FATHERID);
    }

    @Test
    @Transactional
    void getAllDTypesByFatheridIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where fatherid is less than or equal to DEFAULT_FATHERID
        defaultDTypeShouldBeFound("fatherid.lessThanOrEqual=" + DEFAULT_FATHERID);

        // Get all the dTypeList where fatherid is less than or equal to SMALLER_FATHERID
        defaultDTypeShouldNotBeFound("fatherid.lessThanOrEqual=" + SMALLER_FATHERID);
    }

    @Test
    @Transactional
    void getAllDTypesByFatheridIsLessThanSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where fatherid is less than DEFAULT_FATHERID
        defaultDTypeShouldNotBeFound("fatherid.lessThan=" + DEFAULT_FATHERID);

        // Get all the dTypeList where fatherid is less than UPDATED_FATHERID
        defaultDTypeShouldBeFound("fatherid.lessThan=" + UPDATED_FATHERID);
    }

    @Test
    @Transactional
    void getAllDTypesByFatheridIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where fatherid is greater than DEFAULT_FATHERID
        defaultDTypeShouldNotBeFound("fatherid.greaterThan=" + DEFAULT_FATHERID);

        // Get all the dTypeList where fatherid is greater than SMALLER_FATHERID
        defaultDTypeShouldBeFound("fatherid.greaterThan=" + SMALLER_FATHERID);
    }

    @Test
    @Transactional
    void getAllDTypesByDisabledIsEqualToSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where disabled equals to DEFAULT_DISABLED
        defaultDTypeShouldBeFound("disabled.equals=" + DEFAULT_DISABLED);

        // Get all the dTypeList where disabled equals to UPDATED_DISABLED
        defaultDTypeShouldNotBeFound("disabled.equals=" + UPDATED_DISABLED);
    }

    @Test
    @Transactional
    void getAllDTypesByDisabledIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where disabled not equals to DEFAULT_DISABLED
        defaultDTypeShouldNotBeFound("disabled.notEquals=" + DEFAULT_DISABLED);

        // Get all the dTypeList where disabled not equals to UPDATED_DISABLED
        defaultDTypeShouldBeFound("disabled.notEquals=" + UPDATED_DISABLED);
    }

    @Test
    @Transactional
    void getAllDTypesByDisabledIsInShouldWork() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where disabled in DEFAULT_DISABLED or UPDATED_DISABLED
        defaultDTypeShouldBeFound("disabled.in=" + DEFAULT_DISABLED + "," + UPDATED_DISABLED);

        // Get all the dTypeList where disabled equals to UPDATED_DISABLED
        defaultDTypeShouldNotBeFound("disabled.in=" + UPDATED_DISABLED);
    }

    @Test
    @Transactional
    void getAllDTypesByDisabledIsNullOrNotNull() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where disabled is not null
        defaultDTypeShouldBeFound("disabled.specified=true");

        // Get all the dTypeList where disabled is null
        defaultDTypeShouldNotBeFound("disabled.specified=false");
    }

    @Test
    @Transactional
    void getAllDTypesByDisabledIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where disabled is greater than or equal to DEFAULT_DISABLED
        defaultDTypeShouldBeFound("disabled.greaterThanOrEqual=" + DEFAULT_DISABLED);

        // Get all the dTypeList where disabled is greater than or equal to UPDATED_DISABLED
        defaultDTypeShouldNotBeFound("disabled.greaterThanOrEqual=" + UPDATED_DISABLED);
    }

    @Test
    @Transactional
    void getAllDTypesByDisabledIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where disabled is less than or equal to DEFAULT_DISABLED
        defaultDTypeShouldBeFound("disabled.lessThanOrEqual=" + DEFAULT_DISABLED);

        // Get all the dTypeList where disabled is less than or equal to SMALLER_DISABLED
        defaultDTypeShouldNotBeFound("disabled.lessThanOrEqual=" + SMALLER_DISABLED);
    }

    @Test
    @Transactional
    void getAllDTypesByDisabledIsLessThanSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where disabled is less than DEFAULT_DISABLED
        defaultDTypeShouldNotBeFound("disabled.lessThan=" + DEFAULT_DISABLED);

        // Get all the dTypeList where disabled is less than UPDATED_DISABLED
        defaultDTypeShouldBeFound("disabled.lessThan=" + UPDATED_DISABLED);
    }

    @Test
    @Transactional
    void getAllDTypesByDisabledIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        // Get all the dTypeList where disabled is greater than DEFAULT_DISABLED
        defaultDTypeShouldNotBeFound("disabled.greaterThan=" + DEFAULT_DISABLED);

        // Get all the dTypeList where disabled is greater than SMALLER_DISABLED
        defaultDTypeShouldBeFound("disabled.greaterThan=" + SMALLER_DISABLED);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDTypeShouldBeFound(String filter) throws Exception {
        restDTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dType.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeid").value(hasItem(DEFAULT_TYPEID.intValue())))
            .andExpect(jsonPath("$.[*].typename").value(hasItem(DEFAULT_TYPENAME)))
            .andExpect(jsonPath("$.[*].fatherid").value(hasItem(DEFAULT_FATHERID.intValue())))
            .andExpect(jsonPath("$.[*].disabled").value(hasItem(DEFAULT_DISABLED.intValue())));

        // Check, that the count call also returns 1
        restDTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDTypeShouldNotBeFound(String filter) throws Exception {
        restDTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDTypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDType() throws Exception {
        // Get the dType
        restDTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDType() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        int databaseSizeBeforeUpdate = dTypeRepository.findAll().size();

        // Update the dType
        DType updatedDType = dTypeRepository.findById(dType.getId()).get();
        // Disconnect from session so that the updates on updatedDType are not directly saved in db
        em.detach(updatedDType);
        updatedDType.typeid(UPDATED_TYPEID).typename(UPDATED_TYPENAME).fatherid(UPDATED_FATHERID).disabled(UPDATED_DISABLED);
        DTypeDTO dTypeDTO = dTypeMapper.toDto(updatedDType);

        restDTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the DType in the database
        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeUpdate);
        DType testDType = dTypeList.get(dTypeList.size() - 1);
        assertThat(testDType.getTypeid()).isEqualTo(UPDATED_TYPEID);
        assertThat(testDType.getTypename()).isEqualTo(UPDATED_TYPENAME);
        assertThat(testDType.getFatherid()).isEqualTo(UPDATED_FATHERID);
        assertThat(testDType.getDisabled()).isEqualTo(UPDATED_DISABLED);

        // Validate the DType in Elasticsearch
        verify(mockDTypeSearchRepository).save(testDType);
    }

    @Test
    @Transactional
    void putNonExistingDType() throws Exception {
        int databaseSizeBeforeUpdate = dTypeRepository.findAll().size();
        dType.setId(count.incrementAndGet());

        // Create the DType
        DTypeDTO dTypeDTO = dTypeMapper.toDto(dType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dTypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DType in the database
        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DType in Elasticsearch
        verify(mockDTypeSearchRepository, times(0)).save(dType);
    }

    @Test
    @Transactional
    void putWithIdMismatchDType() throws Exception {
        int databaseSizeBeforeUpdate = dTypeRepository.findAll().size();
        dType.setId(count.incrementAndGet());

        // Create the DType
        DTypeDTO dTypeDTO = dTypeMapper.toDto(dType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DType in the database
        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DType in Elasticsearch
        verify(mockDTypeSearchRepository, times(0)).save(dType);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDType() throws Exception {
        int databaseSizeBeforeUpdate = dTypeRepository.findAll().size();
        dType.setId(count.incrementAndGet());

        // Create the DType
        DTypeDTO dTypeDTO = dTypeMapper.toDto(dType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DType in the database
        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DType in Elasticsearch
        verify(mockDTypeSearchRepository, times(0)).save(dType);
    }

    @Test
    @Transactional
    void partialUpdateDTypeWithPatch() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        int databaseSizeBeforeUpdate = dTypeRepository.findAll().size();

        // Update the dType using partial update
        DType partialUpdatedDType = new DType();
        partialUpdatedDType.setId(dType.getId());

        partialUpdatedDType.typename(UPDATED_TYPENAME).disabled(UPDATED_DISABLED);

        restDTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDType))
            )
            .andExpect(status().isOk());

        // Validate the DType in the database
        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeUpdate);
        DType testDType = dTypeList.get(dTypeList.size() - 1);
        assertThat(testDType.getTypeid()).isEqualTo(DEFAULT_TYPEID);
        assertThat(testDType.getTypename()).isEqualTo(UPDATED_TYPENAME);
        assertThat(testDType.getFatherid()).isEqualTo(DEFAULT_FATHERID);
        assertThat(testDType.getDisabled()).isEqualTo(UPDATED_DISABLED);
    }

    @Test
    @Transactional
    void fullUpdateDTypeWithPatch() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        int databaseSizeBeforeUpdate = dTypeRepository.findAll().size();

        // Update the dType using partial update
        DType partialUpdatedDType = new DType();
        partialUpdatedDType.setId(dType.getId());

        partialUpdatedDType.typeid(UPDATED_TYPEID).typename(UPDATED_TYPENAME).fatherid(UPDATED_FATHERID).disabled(UPDATED_DISABLED);

        restDTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDType.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDType))
            )
            .andExpect(status().isOk());

        // Validate the DType in the database
        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeUpdate);
        DType testDType = dTypeList.get(dTypeList.size() - 1);
        assertThat(testDType.getTypeid()).isEqualTo(UPDATED_TYPEID);
        assertThat(testDType.getTypename()).isEqualTo(UPDATED_TYPENAME);
        assertThat(testDType.getFatherid()).isEqualTo(UPDATED_FATHERID);
        assertThat(testDType.getDisabled()).isEqualTo(UPDATED_DISABLED);
    }

    @Test
    @Transactional
    void patchNonExistingDType() throws Exception {
        int databaseSizeBeforeUpdate = dTypeRepository.findAll().size();
        dType.setId(count.incrementAndGet());

        // Create the DType
        DTypeDTO dTypeDTO = dTypeMapper.toDto(dType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dTypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DType in the database
        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DType in Elasticsearch
        verify(mockDTypeSearchRepository, times(0)).save(dType);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDType() throws Exception {
        int databaseSizeBeforeUpdate = dTypeRepository.findAll().size();
        dType.setId(count.incrementAndGet());

        // Create the DType
        DTypeDTO dTypeDTO = dTypeMapper.toDto(dType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DType in the database
        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DType in Elasticsearch
        verify(mockDTypeSearchRepository, times(0)).save(dType);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDType() throws Exception {
        int databaseSizeBeforeUpdate = dTypeRepository.findAll().size();
        dType.setId(count.incrementAndGet());

        // Create the DType
        DTypeDTO dTypeDTO = dTypeMapper.toDto(dType);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dTypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DType in the database
        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DType in Elasticsearch
        verify(mockDTypeSearchRepository, times(0)).save(dType);
    }

    @Test
    @Transactional
    void deleteDType() throws Exception {
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);

        int databaseSizeBeforeDelete = dTypeRepository.findAll().size();

        // Delete the dType
        restDTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, dType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DType> dTypeList = dTypeRepository.findAll();
        assertThat(dTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DType in Elasticsearch
        verify(mockDTypeSearchRepository, times(1)).deleteById(dType.getId());
    }

    @Test
    @Transactional
    void searchDType() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dTypeRepository.saveAndFlush(dType);
        when(mockDTypeSearchRepository.search(queryStringQuery("id:" + dType.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dType), PageRequest.of(0, 1), 1));

        // Search the dType
        restDTypeMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dType.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeid").value(hasItem(DEFAULT_TYPEID.intValue())))
            .andExpect(jsonPath("$.[*].typename").value(hasItem(DEFAULT_TYPENAME)))
            .andExpect(jsonPath("$.[*].fatherid").value(hasItem(DEFAULT_FATHERID.intValue())))
            .andExpect(jsonPath("$.[*].disabled").value(hasItem(DEFAULT_DISABLED.intValue())));
    }
}
