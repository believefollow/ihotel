package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Comset;
import ihotel.app.repository.ComsetRepository;
import ihotel.app.repository.search.ComsetSearchRepository;
import ihotel.app.service.criteria.ComsetCriteria;
import ihotel.app.service.dto.ComsetDTO;
import ihotel.app.service.mapper.ComsetMapper;
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
 * Integration tests for the {@link ComsetResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ComsetResourceIT {

    private static final String DEFAULT_COM_NUM = "AAAAAAAAAA";
    private static final String UPDATED_COM_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_COM_BYTES = "AAAAAAAAAA";
    private static final String UPDATED_COM_BYTES = "BBBBBBBBBB";

    private static final String DEFAULT_COM_DATABIT = "AAAAAAAAAA";
    private static final String UPDATED_COM_DATABIT = "BBBBBBBBBB";

    private static final String DEFAULT_COM_PARITYCHECK = "AAAAAAAAAA";
    private static final String UPDATED_COM_PARITYCHECK = "BBBBBBBBBB";

    private static final String DEFAULT_COM_STOPBIT = "AAAAAAAAAA";
    private static final String UPDATED_COM_STOPBIT = "BBBBBBBBBB";

    private static final Long DEFAULT_COM_FUNCTION = 1L;
    private static final Long UPDATED_COM_FUNCTION = 2L;
    private static final Long SMALLER_COM_FUNCTION = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/comsets";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/comsets";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ComsetRepository comsetRepository;

    @Autowired
    private ComsetMapper comsetMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.ComsetSearchRepositoryMockConfiguration
     */
    @Autowired
    private ComsetSearchRepository mockComsetSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restComsetMockMvc;

    private Comset comset;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comset createEntity(EntityManager em) {
        Comset comset = new Comset()
            .comNum(DEFAULT_COM_NUM)
            .comBytes(DEFAULT_COM_BYTES)
            .comDatabit(DEFAULT_COM_DATABIT)
            .comParitycheck(DEFAULT_COM_PARITYCHECK)
            .comStopbit(DEFAULT_COM_STOPBIT)
            .comFunction(DEFAULT_COM_FUNCTION);
        return comset;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comset createUpdatedEntity(EntityManager em) {
        Comset comset = new Comset()
            .comNum(UPDATED_COM_NUM)
            .comBytes(UPDATED_COM_BYTES)
            .comDatabit(UPDATED_COM_DATABIT)
            .comParitycheck(UPDATED_COM_PARITYCHECK)
            .comStopbit(UPDATED_COM_STOPBIT)
            .comFunction(UPDATED_COM_FUNCTION);
        return comset;
    }

    @BeforeEach
    public void initTest() {
        comset = createEntity(em);
    }

    @Test
    @Transactional
    void createComset() throws Exception {
        int databaseSizeBeforeCreate = comsetRepository.findAll().size();
        // Create the Comset
        ComsetDTO comsetDTO = comsetMapper.toDto(comset);
        restComsetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comsetDTO)))
            .andExpect(status().isCreated());

        // Validate the Comset in the database
        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeCreate + 1);
        Comset testComset = comsetList.get(comsetList.size() - 1);
        assertThat(testComset.getComNum()).isEqualTo(DEFAULT_COM_NUM);
        assertThat(testComset.getComBytes()).isEqualTo(DEFAULT_COM_BYTES);
        assertThat(testComset.getComDatabit()).isEqualTo(DEFAULT_COM_DATABIT);
        assertThat(testComset.getComParitycheck()).isEqualTo(DEFAULT_COM_PARITYCHECK);
        assertThat(testComset.getComStopbit()).isEqualTo(DEFAULT_COM_STOPBIT);
        assertThat(testComset.getComFunction()).isEqualTo(DEFAULT_COM_FUNCTION);

        // Validate the Comset in Elasticsearch
        verify(mockComsetSearchRepository, times(1)).save(testComset);
    }

    @Test
    @Transactional
    void createComsetWithExistingId() throws Exception {
        // Create the Comset with an existing ID
        comset.setId(1L);
        ComsetDTO comsetDTO = comsetMapper.toDto(comset);

        int databaseSizeBeforeCreate = comsetRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restComsetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comsetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Comset in the database
        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeCreate);

        // Validate the Comset in Elasticsearch
        verify(mockComsetSearchRepository, times(0)).save(comset);
    }

    @Test
    @Transactional
    void checkComNumIsRequired() throws Exception {
        int databaseSizeBeforeTest = comsetRepository.findAll().size();
        // set the field null
        comset.setComNum(null);

        // Create the Comset, which fails.
        ComsetDTO comsetDTO = comsetMapper.toDto(comset);

        restComsetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comsetDTO)))
            .andExpect(status().isBadRequest());

        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkComBytesIsRequired() throws Exception {
        int databaseSizeBeforeTest = comsetRepository.findAll().size();
        // set the field null
        comset.setComBytes(null);

        // Create the Comset, which fails.
        ComsetDTO comsetDTO = comsetMapper.toDto(comset);

        restComsetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comsetDTO)))
            .andExpect(status().isBadRequest());

        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkComDatabitIsRequired() throws Exception {
        int databaseSizeBeforeTest = comsetRepository.findAll().size();
        // set the field null
        comset.setComDatabit(null);

        // Create the Comset, which fails.
        ComsetDTO comsetDTO = comsetMapper.toDto(comset);

        restComsetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comsetDTO)))
            .andExpect(status().isBadRequest());

        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkComParitycheckIsRequired() throws Exception {
        int databaseSizeBeforeTest = comsetRepository.findAll().size();
        // set the field null
        comset.setComParitycheck(null);

        // Create the Comset, which fails.
        ComsetDTO comsetDTO = comsetMapper.toDto(comset);

        restComsetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comsetDTO)))
            .andExpect(status().isBadRequest());

        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkComStopbitIsRequired() throws Exception {
        int databaseSizeBeforeTest = comsetRepository.findAll().size();
        // set the field null
        comset.setComStopbit(null);

        // Create the Comset, which fails.
        ComsetDTO comsetDTO = comsetMapper.toDto(comset);

        restComsetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comsetDTO)))
            .andExpect(status().isBadRequest());

        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkComFunctionIsRequired() throws Exception {
        int databaseSizeBeforeTest = comsetRepository.findAll().size();
        // set the field null
        comset.setComFunction(null);

        // Create the Comset, which fails.
        ComsetDTO comsetDTO = comsetMapper.toDto(comset);

        restComsetMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comsetDTO)))
            .andExpect(status().isBadRequest());

        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllComsets() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList
        restComsetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comset.getId().intValue())))
            .andExpect(jsonPath("$.[*].comNum").value(hasItem(DEFAULT_COM_NUM)))
            .andExpect(jsonPath("$.[*].comBytes").value(hasItem(DEFAULT_COM_BYTES)))
            .andExpect(jsonPath("$.[*].comDatabit").value(hasItem(DEFAULT_COM_DATABIT)))
            .andExpect(jsonPath("$.[*].comParitycheck").value(hasItem(DEFAULT_COM_PARITYCHECK)))
            .andExpect(jsonPath("$.[*].comStopbit").value(hasItem(DEFAULT_COM_STOPBIT)))
            .andExpect(jsonPath("$.[*].comFunction").value(hasItem(DEFAULT_COM_FUNCTION.intValue())));
    }

    @Test
    @Transactional
    void getComset() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get the comset
        restComsetMockMvc
            .perform(get(ENTITY_API_URL_ID, comset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(comset.getId().intValue()))
            .andExpect(jsonPath("$.comNum").value(DEFAULT_COM_NUM))
            .andExpect(jsonPath("$.comBytes").value(DEFAULT_COM_BYTES))
            .andExpect(jsonPath("$.comDatabit").value(DEFAULT_COM_DATABIT))
            .andExpect(jsonPath("$.comParitycheck").value(DEFAULT_COM_PARITYCHECK))
            .andExpect(jsonPath("$.comStopbit").value(DEFAULT_COM_STOPBIT))
            .andExpect(jsonPath("$.comFunction").value(DEFAULT_COM_FUNCTION.intValue()));
    }

    @Test
    @Transactional
    void getComsetsByIdFiltering() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        Long id = comset.getId();

        defaultComsetShouldBeFound("id.equals=" + id);
        defaultComsetShouldNotBeFound("id.notEquals=" + id);

        defaultComsetShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultComsetShouldNotBeFound("id.greaterThan=" + id);

        defaultComsetShouldBeFound("id.lessThanOrEqual=" + id);
        defaultComsetShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllComsetsByComNumIsEqualToSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comNum equals to DEFAULT_COM_NUM
        defaultComsetShouldBeFound("comNum.equals=" + DEFAULT_COM_NUM);

        // Get all the comsetList where comNum equals to UPDATED_COM_NUM
        defaultComsetShouldNotBeFound("comNum.equals=" + UPDATED_COM_NUM);
    }

    @Test
    @Transactional
    void getAllComsetsByComNumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comNum not equals to DEFAULT_COM_NUM
        defaultComsetShouldNotBeFound("comNum.notEquals=" + DEFAULT_COM_NUM);

        // Get all the comsetList where comNum not equals to UPDATED_COM_NUM
        defaultComsetShouldBeFound("comNum.notEquals=" + UPDATED_COM_NUM);
    }

    @Test
    @Transactional
    void getAllComsetsByComNumIsInShouldWork() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comNum in DEFAULT_COM_NUM or UPDATED_COM_NUM
        defaultComsetShouldBeFound("comNum.in=" + DEFAULT_COM_NUM + "," + UPDATED_COM_NUM);

        // Get all the comsetList where comNum equals to UPDATED_COM_NUM
        defaultComsetShouldNotBeFound("comNum.in=" + UPDATED_COM_NUM);
    }

    @Test
    @Transactional
    void getAllComsetsByComNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comNum is not null
        defaultComsetShouldBeFound("comNum.specified=true");

        // Get all the comsetList where comNum is null
        defaultComsetShouldNotBeFound("comNum.specified=false");
    }

    @Test
    @Transactional
    void getAllComsetsByComNumContainsSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comNum contains DEFAULT_COM_NUM
        defaultComsetShouldBeFound("comNum.contains=" + DEFAULT_COM_NUM);

        // Get all the comsetList where comNum contains UPDATED_COM_NUM
        defaultComsetShouldNotBeFound("comNum.contains=" + UPDATED_COM_NUM);
    }

    @Test
    @Transactional
    void getAllComsetsByComNumNotContainsSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comNum does not contain DEFAULT_COM_NUM
        defaultComsetShouldNotBeFound("comNum.doesNotContain=" + DEFAULT_COM_NUM);

        // Get all the comsetList where comNum does not contain UPDATED_COM_NUM
        defaultComsetShouldBeFound("comNum.doesNotContain=" + UPDATED_COM_NUM);
    }

    @Test
    @Transactional
    void getAllComsetsByComBytesIsEqualToSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comBytes equals to DEFAULT_COM_BYTES
        defaultComsetShouldBeFound("comBytes.equals=" + DEFAULT_COM_BYTES);

        // Get all the comsetList where comBytes equals to UPDATED_COM_BYTES
        defaultComsetShouldNotBeFound("comBytes.equals=" + UPDATED_COM_BYTES);
    }

    @Test
    @Transactional
    void getAllComsetsByComBytesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comBytes not equals to DEFAULT_COM_BYTES
        defaultComsetShouldNotBeFound("comBytes.notEquals=" + DEFAULT_COM_BYTES);

        // Get all the comsetList where comBytes not equals to UPDATED_COM_BYTES
        defaultComsetShouldBeFound("comBytes.notEquals=" + UPDATED_COM_BYTES);
    }

    @Test
    @Transactional
    void getAllComsetsByComBytesIsInShouldWork() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comBytes in DEFAULT_COM_BYTES or UPDATED_COM_BYTES
        defaultComsetShouldBeFound("comBytes.in=" + DEFAULT_COM_BYTES + "," + UPDATED_COM_BYTES);

        // Get all the comsetList where comBytes equals to UPDATED_COM_BYTES
        defaultComsetShouldNotBeFound("comBytes.in=" + UPDATED_COM_BYTES);
    }

    @Test
    @Transactional
    void getAllComsetsByComBytesIsNullOrNotNull() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comBytes is not null
        defaultComsetShouldBeFound("comBytes.specified=true");

        // Get all the comsetList where comBytes is null
        defaultComsetShouldNotBeFound("comBytes.specified=false");
    }

    @Test
    @Transactional
    void getAllComsetsByComBytesContainsSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comBytes contains DEFAULT_COM_BYTES
        defaultComsetShouldBeFound("comBytes.contains=" + DEFAULT_COM_BYTES);

        // Get all the comsetList where comBytes contains UPDATED_COM_BYTES
        defaultComsetShouldNotBeFound("comBytes.contains=" + UPDATED_COM_BYTES);
    }

    @Test
    @Transactional
    void getAllComsetsByComBytesNotContainsSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comBytes does not contain DEFAULT_COM_BYTES
        defaultComsetShouldNotBeFound("comBytes.doesNotContain=" + DEFAULT_COM_BYTES);

        // Get all the comsetList where comBytes does not contain UPDATED_COM_BYTES
        defaultComsetShouldBeFound("comBytes.doesNotContain=" + UPDATED_COM_BYTES);
    }

    @Test
    @Transactional
    void getAllComsetsByComDatabitIsEqualToSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comDatabit equals to DEFAULT_COM_DATABIT
        defaultComsetShouldBeFound("comDatabit.equals=" + DEFAULT_COM_DATABIT);

        // Get all the comsetList where comDatabit equals to UPDATED_COM_DATABIT
        defaultComsetShouldNotBeFound("comDatabit.equals=" + UPDATED_COM_DATABIT);
    }

    @Test
    @Transactional
    void getAllComsetsByComDatabitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comDatabit not equals to DEFAULT_COM_DATABIT
        defaultComsetShouldNotBeFound("comDatabit.notEquals=" + DEFAULT_COM_DATABIT);

        // Get all the comsetList where comDatabit not equals to UPDATED_COM_DATABIT
        defaultComsetShouldBeFound("comDatabit.notEquals=" + UPDATED_COM_DATABIT);
    }

    @Test
    @Transactional
    void getAllComsetsByComDatabitIsInShouldWork() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comDatabit in DEFAULT_COM_DATABIT or UPDATED_COM_DATABIT
        defaultComsetShouldBeFound("comDatabit.in=" + DEFAULT_COM_DATABIT + "," + UPDATED_COM_DATABIT);

        // Get all the comsetList where comDatabit equals to UPDATED_COM_DATABIT
        defaultComsetShouldNotBeFound("comDatabit.in=" + UPDATED_COM_DATABIT);
    }

    @Test
    @Transactional
    void getAllComsetsByComDatabitIsNullOrNotNull() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comDatabit is not null
        defaultComsetShouldBeFound("comDatabit.specified=true");

        // Get all the comsetList where comDatabit is null
        defaultComsetShouldNotBeFound("comDatabit.specified=false");
    }

    @Test
    @Transactional
    void getAllComsetsByComDatabitContainsSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comDatabit contains DEFAULT_COM_DATABIT
        defaultComsetShouldBeFound("comDatabit.contains=" + DEFAULT_COM_DATABIT);

        // Get all the comsetList where comDatabit contains UPDATED_COM_DATABIT
        defaultComsetShouldNotBeFound("comDatabit.contains=" + UPDATED_COM_DATABIT);
    }

    @Test
    @Transactional
    void getAllComsetsByComDatabitNotContainsSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comDatabit does not contain DEFAULT_COM_DATABIT
        defaultComsetShouldNotBeFound("comDatabit.doesNotContain=" + DEFAULT_COM_DATABIT);

        // Get all the comsetList where comDatabit does not contain UPDATED_COM_DATABIT
        defaultComsetShouldBeFound("comDatabit.doesNotContain=" + UPDATED_COM_DATABIT);
    }

    @Test
    @Transactional
    void getAllComsetsByComParitycheckIsEqualToSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comParitycheck equals to DEFAULT_COM_PARITYCHECK
        defaultComsetShouldBeFound("comParitycheck.equals=" + DEFAULT_COM_PARITYCHECK);

        // Get all the comsetList where comParitycheck equals to UPDATED_COM_PARITYCHECK
        defaultComsetShouldNotBeFound("comParitycheck.equals=" + UPDATED_COM_PARITYCHECK);
    }

    @Test
    @Transactional
    void getAllComsetsByComParitycheckIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comParitycheck not equals to DEFAULT_COM_PARITYCHECK
        defaultComsetShouldNotBeFound("comParitycheck.notEquals=" + DEFAULT_COM_PARITYCHECK);

        // Get all the comsetList where comParitycheck not equals to UPDATED_COM_PARITYCHECK
        defaultComsetShouldBeFound("comParitycheck.notEquals=" + UPDATED_COM_PARITYCHECK);
    }

    @Test
    @Transactional
    void getAllComsetsByComParitycheckIsInShouldWork() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comParitycheck in DEFAULT_COM_PARITYCHECK or UPDATED_COM_PARITYCHECK
        defaultComsetShouldBeFound("comParitycheck.in=" + DEFAULT_COM_PARITYCHECK + "," + UPDATED_COM_PARITYCHECK);

        // Get all the comsetList where comParitycheck equals to UPDATED_COM_PARITYCHECK
        defaultComsetShouldNotBeFound("comParitycheck.in=" + UPDATED_COM_PARITYCHECK);
    }

    @Test
    @Transactional
    void getAllComsetsByComParitycheckIsNullOrNotNull() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comParitycheck is not null
        defaultComsetShouldBeFound("comParitycheck.specified=true");

        // Get all the comsetList where comParitycheck is null
        defaultComsetShouldNotBeFound("comParitycheck.specified=false");
    }

    @Test
    @Transactional
    void getAllComsetsByComParitycheckContainsSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comParitycheck contains DEFAULT_COM_PARITYCHECK
        defaultComsetShouldBeFound("comParitycheck.contains=" + DEFAULT_COM_PARITYCHECK);

        // Get all the comsetList where comParitycheck contains UPDATED_COM_PARITYCHECK
        defaultComsetShouldNotBeFound("comParitycheck.contains=" + UPDATED_COM_PARITYCHECK);
    }

    @Test
    @Transactional
    void getAllComsetsByComParitycheckNotContainsSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comParitycheck does not contain DEFAULT_COM_PARITYCHECK
        defaultComsetShouldNotBeFound("comParitycheck.doesNotContain=" + DEFAULT_COM_PARITYCHECK);

        // Get all the comsetList where comParitycheck does not contain UPDATED_COM_PARITYCHECK
        defaultComsetShouldBeFound("comParitycheck.doesNotContain=" + UPDATED_COM_PARITYCHECK);
    }

    @Test
    @Transactional
    void getAllComsetsByComStopbitIsEqualToSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comStopbit equals to DEFAULT_COM_STOPBIT
        defaultComsetShouldBeFound("comStopbit.equals=" + DEFAULT_COM_STOPBIT);

        // Get all the comsetList where comStopbit equals to UPDATED_COM_STOPBIT
        defaultComsetShouldNotBeFound("comStopbit.equals=" + UPDATED_COM_STOPBIT);
    }

    @Test
    @Transactional
    void getAllComsetsByComStopbitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comStopbit not equals to DEFAULT_COM_STOPBIT
        defaultComsetShouldNotBeFound("comStopbit.notEquals=" + DEFAULT_COM_STOPBIT);

        // Get all the comsetList where comStopbit not equals to UPDATED_COM_STOPBIT
        defaultComsetShouldBeFound("comStopbit.notEquals=" + UPDATED_COM_STOPBIT);
    }

    @Test
    @Transactional
    void getAllComsetsByComStopbitIsInShouldWork() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comStopbit in DEFAULT_COM_STOPBIT or UPDATED_COM_STOPBIT
        defaultComsetShouldBeFound("comStopbit.in=" + DEFAULT_COM_STOPBIT + "," + UPDATED_COM_STOPBIT);

        // Get all the comsetList where comStopbit equals to UPDATED_COM_STOPBIT
        defaultComsetShouldNotBeFound("comStopbit.in=" + UPDATED_COM_STOPBIT);
    }

    @Test
    @Transactional
    void getAllComsetsByComStopbitIsNullOrNotNull() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comStopbit is not null
        defaultComsetShouldBeFound("comStopbit.specified=true");

        // Get all the comsetList where comStopbit is null
        defaultComsetShouldNotBeFound("comStopbit.specified=false");
    }

    @Test
    @Transactional
    void getAllComsetsByComStopbitContainsSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comStopbit contains DEFAULT_COM_STOPBIT
        defaultComsetShouldBeFound("comStopbit.contains=" + DEFAULT_COM_STOPBIT);

        // Get all the comsetList where comStopbit contains UPDATED_COM_STOPBIT
        defaultComsetShouldNotBeFound("comStopbit.contains=" + UPDATED_COM_STOPBIT);
    }

    @Test
    @Transactional
    void getAllComsetsByComStopbitNotContainsSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comStopbit does not contain DEFAULT_COM_STOPBIT
        defaultComsetShouldNotBeFound("comStopbit.doesNotContain=" + DEFAULT_COM_STOPBIT);

        // Get all the comsetList where comStopbit does not contain UPDATED_COM_STOPBIT
        defaultComsetShouldBeFound("comStopbit.doesNotContain=" + UPDATED_COM_STOPBIT);
    }

    @Test
    @Transactional
    void getAllComsetsByComFunctionIsEqualToSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comFunction equals to DEFAULT_COM_FUNCTION
        defaultComsetShouldBeFound("comFunction.equals=" + DEFAULT_COM_FUNCTION);

        // Get all the comsetList where comFunction equals to UPDATED_COM_FUNCTION
        defaultComsetShouldNotBeFound("comFunction.equals=" + UPDATED_COM_FUNCTION);
    }

    @Test
    @Transactional
    void getAllComsetsByComFunctionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comFunction not equals to DEFAULT_COM_FUNCTION
        defaultComsetShouldNotBeFound("comFunction.notEquals=" + DEFAULT_COM_FUNCTION);

        // Get all the comsetList where comFunction not equals to UPDATED_COM_FUNCTION
        defaultComsetShouldBeFound("comFunction.notEquals=" + UPDATED_COM_FUNCTION);
    }

    @Test
    @Transactional
    void getAllComsetsByComFunctionIsInShouldWork() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comFunction in DEFAULT_COM_FUNCTION or UPDATED_COM_FUNCTION
        defaultComsetShouldBeFound("comFunction.in=" + DEFAULT_COM_FUNCTION + "," + UPDATED_COM_FUNCTION);

        // Get all the comsetList where comFunction equals to UPDATED_COM_FUNCTION
        defaultComsetShouldNotBeFound("comFunction.in=" + UPDATED_COM_FUNCTION);
    }

    @Test
    @Transactional
    void getAllComsetsByComFunctionIsNullOrNotNull() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comFunction is not null
        defaultComsetShouldBeFound("comFunction.specified=true");

        // Get all the comsetList where comFunction is null
        defaultComsetShouldNotBeFound("comFunction.specified=false");
    }

    @Test
    @Transactional
    void getAllComsetsByComFunctionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comFunction is greater than or equal to DEFAULT_COM_FUNCTION
        defaultComsetShouldBeFound("comFunction.greaterThanOrEqual=" + DEFAULT_COM_FUNCTION);

        // Get all the comsetList where comFunction is greater than or equal to UPDATED_COM_FUNCTION
        defaultComsetShouldNotBeFound("comFunction.greaterThanOrEqual=" + UPDATED_COM_FUNCTION);
    }

    @Test
    @Transactional
    void getAllComsetsByComFunctionIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comFunction is less than or equal to DEFAULT_COM_FUNCTION
        defaultComsetShouldBeFound("comFunction.lessThanOrEqual=" + DEFAULT_COM_FUNCTION);

        // Get all the comsetList where comFunction is less than or equal to SMALLER_COM_FUNCTION
        defaultComsetShouldNotBeFound("comFunction.lessThanOrEqual=" + SMALLER_COM_FUNCTION);
    }

    @Test
    @Transactional
    void getAllComsetsByComFunctionIsLessThanSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comFunction is less than DEFAULT_COM_FUNCTION
        defaultComsetShouldNotBeFound("comFunction.lessThan=" + DEFAULT_COM_FUNCTION);

        // Get all the comsetList where comFunction is less than UPDATED_COM_FUNCTION
        defaultComsetShouldBeFound("comFunction.lessThan=" + UPDATED_COM_FUNCTION);
    }

    @Test
    @Transactional
    void getAllComsetsByComFunctionIsGreaterThanSomething() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        // Get all the comsetList where comFunction is greater than DEFAULT_COM_FUNCTION
        defaultComsetShouldNotBeFound("comFunction.greaterThan=" + DEFAULT_COM_FUNCTION);

        // Get all the comsetList where comFunction is greater than SMALLER_COM_FUNCTION
        defaultComsetShouldBeFound("comFunction.greaterThan=" + SMALLER_COM_FUNCTION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultComsetShouldBeFound(String filter) throws Exception {
        restComsetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comset.getId().intValue())))
            .andExpect(jsonPath("$.[*].comNum").value(hasItem(DEFAULT_COM_NUM)))
            .andExpect(jsonPath("$.[*].comBytes").value(hasItem(DEFAULT_COM_BYTES)))
            .andExpect(jsonPath("$.[*].comDatabit").value(hasItem(DEFAULT_COM_DATABIT)))
            .andExpect(jsonPath("$.[*].comParitycheck").value(hasItem(DEFAULT_COM_PARITYCHECK)))
            .andExpect(jsonPath("$.[*].comStopbit").value(hasItem(DEFAULT_COM_STOPBIT)))
            .andExpect(jsonPath("$.[*].comFunction").value(hasItem(DEFAULT_COM_FUNCTION.intValue())));

        // Check, that the count call also returns 1
        restComsetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultComsetShouldNotBeFound(String filter) throws Exception {
        restComsetMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restComsetMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingComset() throws Exception {
        // Get the comset
        restComsetMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewComset() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        int databaseSizeBeforeUpdate = comsetRepository.findAll().size();

        // Update the comset
        Comset updatedComset = comsetRepository.findById(comset.getId()).get();
        // Disconnect from session so that the updates on updatedComset are not directly saved in db
        em.detach(updatedComset);
        updatedComset
            .comNum(UPDATED_COM_NUM)
            .comBytes(UPDATED_COM_BYTES)
            .comDatabit(UPDATED_COM_DATABIT)
            .comParitycheck(UPDATED_COM_PARITYCHECK)
            .comStopbit(UPDATED_COM_STOPBIT)
            .comFunction(UPDATED_COM_FUNCTION);
        ComsetDTO comsetDTO = comsetMapper.toDto(updatedComset);

        restComsetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, comsetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comsetDTO))
            )
            .andExpect(status().isOk());

        // Validate the Comset in the database
        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeUpdate);
        Comset testComset = comsetList.get(comsetList.size() - 1);
        assertThat(testComset.getComNum()).isEqualTo(UPDATED_COM_NUM);
        assertThat(testComset.getComBytes()).isEqualTo(UPDATED_COM_BYTES);
        assertThat(testComset.getComDatabit()).isEqualTo(UPDATED_COM_DATABIT);
        assertThat(testComset.getComParitycheck()).isEqualTo(UPDATED_COM_PARITYCHECK);
        assertThat(testComset.getComStopbit()).isEqualTo(UPDATED_COM_STOPBIT);
        assertThat(testComset.getComFunction()).isEqualTo(UPDATED_COM_FUNCTION);

        // Validate the Comset in Elasticsearch
        verify(mockComsetSearchRepository).save(testComset);
    }

    @Test
    @Transactional
    void putNonExistingComset() throws Exception {
        int databaseSizeBeforeUpdate = comsetRepository.findAll().size();
        comset.setId(count.incrementAndGet());

        // Create the Comset
        ComsetDTO comsetDTO = comsetMapper.toDto(comset);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComsetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, comsetDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comsetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comset in the database
        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Comset in Elasticsearch
        verify(mockComsetSearchRepository, times(0)).save(comset);
    }

    @Test
    @Transactional
    void putWithIdMismatchComset() throws Exception {
        int databaseSizeBeforeUpdate = comsetRepository.findAll().size();
        comset.setId(count.incrementAndGet());

        // Create the Comset
        ComsetDTO comsetDTO = comsetMapper.toDto(comset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComsetMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(comsetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comset in the database
        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Comset in Elasticsearch
        verify(mockComsetSearchRepository, times(0)).save(comset);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamComset() throws Exception {
        int databaseSizeBeforeUpdate = comsetRepository.findAll().size();
        comset.setId(count.incrementAndGet());

        // Create the Comset
        ComsetDTO comsetDTO = comsetMapper.toDto(comset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComsetMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(comsetDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Comset in the database
        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Comset in Elasticsearch
        verify(mockComsetSearchRepository, times(0)).save(comset);
    }

    @Test
    @Transactional
    void partialUpdateComsetWithPatch() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        int databaseSizeBeforeUpdate = comsetRepository.findAll().size();

        // Update the comset using partial update
        Comset partialUpdatedComset = new Comset();
        partialUpdatedComset.setId(comset.getId());

        partialUpdatedComset
            .comNum(UPDATED_COM_NUM)
            .comParitycheck(UPDATED_COM_PARITYCHECK)
            .comStopbit(UPDATED_COM_STOPBIT)
            .comFunction(UPDATED_COM_FUNCTION);

        restComsetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComset.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComset))
            )
            .andExpect(status().isOk());

        // Validate the Comset in the database
        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeUpdate);
        Comset testComset = comsetList.get(comsetList.size() - 1);
        assertThat(testComset.getComNum()).isEqualTo(UPDATED_COM_NUM);
        assertThat(testComset.getComBytes()).isEqualTo(DEFAULT_COM_BYTES);
        assertThat(testComset.getComDatabit()).isEqualTo(DEFAULT_COM_DATABIT);
        assertThat(testComset.getComParitycheck()).isEqualTo(UPDATED_COM_PARITYCHECK);
        assertThat(testComset.getComStopbit()).isEqualTo(UPDATED_COM_STOPBIT);
        assertThat(testComset.getComFunction()).isEqualTo(UPDATED_COM_FUNCTION);
    }

    @Test
    @Transactional
    void fullUpdateComsetWithPatch() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        int databaseSizeBeforeUpdate = comsetRepository.findAll().size();

        // Update the comset using partial update
        Comset partialUpdatedComset = new Comset();
        partialUpdatedComset.setId(comset.getId());

        partialUpdatedComset
            .comNum(UPDATED_COM_NUM)
            .comBytes(UPDATED_COM_BYTES)
            .comDatabit(UPDATED_COM_DATABIT)
            .comParitycheck(UPDATED_COM_PARITYCHECK)
            .comStopbit(UPDATED_COM_STOPBIT)
            .comFunction(UPDATED_COM_FUNCTION);

        restComsetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedComset.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedComset))
            )
            .andExpect(status().isOk());

        // Validate the Comset in the database
        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeUpdate);
        Comset testComset = comsetList.get(comsetList.size() - 1);
        assertThat(testComset.getComNum()).isEqualTo(UPDATED_COM_NUM);
        assertThat(testComset.getComBytes()).isEqualTo(UPDATED_COM_BYTES);
        assertThat(testComset.getComDatabit()).isEqualTo(UPDATED_COM_DATABIT);
        assertThat(testComset.getComParitycheck()).isEqualTo(UPDATED_COM_PARITYCHECK);
        assertThat(testComset.getComStopbit()).isEqualTo(UPDATED_COM_STOPBIT);
        assertThat(testComset.getComFunction()).isEqualTo(UPDATED_COM_FUNCTION);
    }

    @Test
    @Transactional
    void patchNonExistingComset() throws Exception {
        int databaseSizeBeforeUpdate = comsetRepository.findAll().size();
        comset.setId(count.incrementAndGet());

        // Create the Comset
        ComsetDTO comsetDTO = comsetMapper.toDto(comset);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restComsetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, comsetDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comsetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comset in the database
        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Comset in Elasticsearch
        verify(mockComsetSearchRepository, times(0)).save(comset);
    }

    @Test
    @Transactional
    void patchWithIdMismatchComset() throws Exception {
        int databaseSizeBeforeUpdate = comsetRepository.findAll().size();
        comset.setId(count.incrementAndGet());

        // Create the Comset
        ComsetDTO comsetDTO = comsetMapper.toDto(comset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComsetMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(comsetDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Comset in the database
        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Comset in Elasticsearch
        verify(mockComsetSearchRepository, times(0)).save(comset);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamComset() throws Exception {
        int databaseSizeBeforeUpdate = comsetRepository.findAll().size();
        comset.setId(count.incrementAndGet());

        // Create the Comset
        ComsetDTO comsetDTO = comsetMapper.toDto(comset);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restComsetMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(comsetDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Comset in the database
        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Comset in Elasticsearch
        verify(mockComsetSearchRepository, times(0)).save(comset);
    }

    @Test
    @Transactional
    void deleteComset() throws Exception {
        // Initialize the database
        comsetRepository.saveAndFlush(comset);

        int databaseSizeBeforeDelete = comsetRepository.findAll().size();

        // Delete the comset
        restComsetMockMvc
            .perform(delete(ENTITY_API_URL_ID, comset.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Comset> comsetList = comsetRepository.findAll();
        assertThat(comsetList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Comset in Elasticsearch
        verify(mockComsetSearchRepository, times(1)).deleteById(comset.getId());
    }

    @Test
    @Transactional
    void searchComset() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        comsetRepository.saveAndFlush(comset);
        when(mockComsetSearchRepository.search(queryStringQuery("id:" + comset.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(comset), PageRequest.of(0, 1), 1));

        // Search the comset
        restComsetMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + comset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(comset.getId().intValue())))
            .andExpect(jsonPath("$.[*].comNum").value(hasItem(DEFAULT_COM_NUM)))
            .andExpect(jsonPath("$.[*].comBytes").value(hasItem(DEFAULT_COM_BYTES)))
            .andExpect(jsonPath("$.[*].comDatabit").value(hasItem(DEFAULT_COM_DATABIT)))
            .andExpect(jsonPath("$.[*].comParitycheck").value(hasItem(DEFAULT_COM_PARITYCHECK)))
            .andExpect(jsonPath("$.[*].comStopbit").value(hasItem(DEFAULT_COM_STOPBIT)))
            .andExpect(jsonPath("$.[*].comFunction").value(hasItem(DEFAULT_COM_FUNCTION.intValue())));
    }
}
