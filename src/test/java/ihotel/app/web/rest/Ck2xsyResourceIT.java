package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Ck2xsy;
import ihotel.app.repository.Ck2xsyRepository;
import ihotel.app.repository.search.Ck2xsySearchRepository;
import ihotel.app.service.criteria.Ck2xsyCriteria;
import ihotel.app.service.dto.Ck2xsyDTO;
import ihotel.app.service.mapper.Ck2xsyMapper;
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
 * Integration tests for the {@link Ck2xsyResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class Ck2xsyResourceIT {

    private static final Instant DEFAULT_RQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CPBH = "AAAAAAAAAA";
    private static final String UPDATED_CPBH = "BBBBBBBBBB";

    private static final Long DEFAULT_SL = 1L;
    private static final Long UPDATED_SL = 2L;
    private static final Long SMALLER_SL = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/ck-2-xsies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/ck-2-xsies";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private Ck2xsyRepository ck2xsyRepository;

    @Autowired
    private Ck2xsyMapper ck2xsyMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.Ck2xsySearchRepositoryMockConfiguration
     */
    @Autowired
    private Ck2xsySearchRepository mockCk2xsySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCk2xsyMockMvc;

    private Ck2xsy ck2xsy;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ck2xsy createEntity(EntityManager em) {
        Ck2xsy ck2xsy = new Ck2xsy().rq(DEFAULT_RQ).cpbh(DEFAULT_CPBH).sl(DEFAULT_SL);
        return ck2xsy;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ck2xsy createUpdatedEntity(EntityManager em) {
        Ck2xsy ck2xsy = new Ck2xsy().rq(UPDATED_RQ).cpbh(UPDATED_CPBH).sl(UPDATED_SL);
        return ck2xsy;
    }

    @BeforeEach
    public void initTest() {
        ck2xsy = createEntity(em);
    }

    @Test
    @Transactional
    void createCk2xsy() throws Exception {
        int databaseSizeBeforeCreate = ck2xsyRepository.findAll().size();
        // Create the Ck2xsy
        Ck2xsyDTO ck2xsyDTO = ck2xsyMapper.toDto(ck2xsy);
        restCk2xsyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ck2xsyDTO)))
            .andExpect(status().isCreated());

        // Validate the Ck2xsy in the database
        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeCreate + 1);
        Ck2xsy testCk2xsy = ck2xsyList.get(ck2xsyList.size() - 1);
        assertThat(testCk2xsy.getRq()).isEqualTo(DEFAULT_RQ);
        assertThat(testCk2xsy.getCpbh()).isEqualTo(DEFAULT_CPBH);
        assertThat(testCk2xsy.getSl()).isEqualTo(DEFAULT_SL);

        // Validate the Ck2xsy in Elasticsearch
        verify(mockCk2xsySearchRepository, times(1)).save(testCk2xsy);
    }

    @Test
    @Transactional
    void createCk2xsyWithExistingId() throws Exception {
        // Create the Ck2xsy with an existing ID
        ck2xsy.setId(1L);
        Ck2xsyDTO ck2xsyDTO = ck2xsyMapper.toDto(ck2xsy);

        int databaseSizeBeforeCreate = ck2xsyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCk2xsyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ck2xsyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ck2xsy in the database
        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeCreate);

        // Validate the Ck2xsy in Elasticsearch
        verify(mockCk2xsySearchRepository, times(0)).save(ck2xsy);
    }

    @Test
    @Transactional
    void checkRqIsRequired() throws Exception {
        int databaseSizeBeforeTest = ck2xsyRepository.findAll().size();
        // set the field null
        ck2xsy.setRq(null);

        // Create the Ck2xsy, which fails.
        Ck2xsyDTO ck2xsyDTO = ck2xsyMapper.toDto(ck2xsy);

        restCk2xsyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ck2xsyDTO)))
            .andExpect(status().isBadRequest());

        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCpbhIsRequired() throws Exception {
        int databaseSizeBeforeTest = ck2xsyRepository.findAll().size();
        // set the field null
        ck2xsy.setCpbh(null);

        // Create the Ck2xsy, which fails.
        Ck2xsyDTO ck2xsyDTO = ck2xsyMapper.toDto(ck2xsy);

        restCk2xsyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ck2xsyDTO)))
            .andExpect(status().isBadRequest());

        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSlIsRequired() throws Exception {
        int databaseSizeBeforeTest = ck2xsyRepository.findAll().size();
        // set the field null
        ck2xsy.setSl(null);

        // Create the Ck2xsy, which fails.
        Ck2xsyDTO ck2xsyDTO = ck2xsyMapper.toDto(ck2xsy);

        restCk2xsyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ck2xsyDTO)))
            .andExpect(status().isBadRequest());

        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCk2xsies() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList
        restCk2xsyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ck2xsy.getId().intValue())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].cpbh").value(hasItem(DEFAULT_CPBH)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(DEFAULT_SL.intValue())));
    }

    @Test
    @Transactional
    void getCk2xsy() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get the ck2xsy
        restCk2xsyMockMvc
            .perform(get(ENTITY_API_URL_ID, ck2xsy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ck2xsy.getId().intValue()))
            .andExpect(jsonPath("$.rq").value(DEFAULT_RQ.toString()))
            .andExpect(jsonPath("$.cpbh").value(DEFAULT_CPBH))
            .andExpect(jsonPath("$.sl").value(DEFAULT_SL.intValue()));
    }

    @Test
    @Transactional
    void getCk2xsiesByIdFiltering() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        Long id = ck2xsy.getId();

        defaultCk2xsyShouldBeFound("id.equals=" + id);
        defaultCk2xsyShouldNotBeFound("id.notEquals=" + id);

        defaultCk2xsyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCk2xsyShouldNotBeFound("id.greaterThan=" + id);

        defaultCk2xsyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCk2xsyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCk2xsiesByRqIsEqualToSomething() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where rq equals to DEFAULT_RQ
        defaultCk2xsyShouldBeFound("rq.equals=" + DEFAULT_RQ);

        // Get all the ck2xsyList where rq equals to UPDATED_RQ
        defaultCk2xsyShouldNotBeFound("rq.equals=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllCk2xsiesByRqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where rq not equals to DEFAULT_RQ
        defaultCk2xsyShouldNotBeFound("rq.notEquals=" + DEFAULT_RQ);

        // Get all the ck2xsyList where rq not equals to UPDATED_RQ
        defaultCk2xsyShouldBeFound("rq.notEquals=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllCk2xsiesByRqIsInShouldWork() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where rq in DEFAULT_RQ or UPDATED_RQ
        defaultCk2xsyShouldBeFound("rq.in=" + DEFAULT_RQ + "," + UPDATED_RQ);

        // Get all the ck2xsyList where rq equals to UPDATED_RQ
        defaultCk2xsyShouldNotBeFound("rq.in=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllCk2xsiesByRqIsNullOrNotNull() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where rq is not null
        defaultCk2xsyShouldBeFound("rq.specified=true");

        // Get all the ck2xsyList where rq is null
        defaultCk2xsyShouldNotBeFound("rq.specified=false");
    }

    @Test
    @Transactional
    void getAllCk2xsiesByCpbhIsEqualToSomething() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where cpbh equals to DEFAULT_CPBH
        defaultCk2xsyShouldBeFound("cpbh.equals=" + DEFAULT_CPBH);

        // Get all the ck2xsyList where cpbh equals to UPDATED_CPBH
        defaultCk2xsyShouldNotBeFound("cpbh.equals=" + UPDATED_CPBH);
    }

    @Test
    @Transactional
    void getAllCk2xsiesByCpbhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where cpbh not equals to DEFAULT_CPBH
        defaultCk2xsyShouldNotBeFound("cpbh.notEquals=" + DEFAULT_CPBH);

        // Get all the ck2xsyList where cpbh not equals to UPDATED_CPBH
        defaultCk2xsyShouldBeFound("cpbh.notEquals=" + UPDATED_CPBH);
    }

    @Test
    @Transactional
    void getAllCk2xsiesByCpbhIsInShouldWork() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where cpbh in DEFAULT_CPBH or UPDATED_CPBH
        defaultCk2xsyShouldBeFound("cpbh.in=" + DEFAULT_CPBH + "," + UPDATED_CPBH);

        // Get all the ck2xsyList where cpbh equals to UPDATED_CPBH
        defaultCk2xsyShouldNotBeFound("cpbh.in=" + UPDATED_CPBH);
    }

    @Test
    @Transactional
    void getAllCk2xsiesByCpbhIsNullOrNotNull() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where cpbh is not null
        defaultCk2xsyShouldBeFound("cpbh.specified=true");

        // Get all the ck2xsyList where cpbh is null
        defaultCk2xsyShouldNotBeFound("cpbh.specified=false");
    }

    @Test
    @Transactional
    void getAllCk2xsiesByCpbhContainsSomething() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where cpbh contains DEFAULT_CPBH
        defaultCk2xsyShouldBeFound("cpbh.contains=" + DEFAULT_CPBH);

        // Get all the ck2xsyList where cpbh contains UPDATED_CPBH
        defaultCk2xsyShouldNotBeFound("cpbh.contains=" + UPDATED_CPBH);
    }

    @Test
    @Transactional
    void getAllCk2xsiesByCpbhNotContainsSomething() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where cpbh does not contain DEFAULT_CPBH
        defaultCk2xsyShouldNotBeFound("cpbh.doesNotContain=" + DEFAULT_CPBH);

        // Get all the ck2xsyList where cpbh does not contain UPDATED_CPBH
        defaultCk2xsyShouldBeFound("cpbh.doesNotContain=" + UPDATED_CPBH);
    }

    @Test
    @Transactional
    void getAllCk2xsiesBySlIsEqualToSomething() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where sl equals to DEFAULT_SL
        defaultCk2xsyShouldBeFound("sl.equals=" + DEFAULT_SL);

        // Get all the ck2xsyList where sl equals to UPDATED_SL
        defaultCk2xsyShouldNotBeFound("sl.equals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllCk2xsiesBySlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where sl not equals to DEFAULT_SL
        defaultCk2xsyShouldNotBeFound("sl.notEquals=" + DEFAULT_SL);

        // Get all the ck2xsyList where sl not equals to UPDATED_SL
        defaultCk2xsyShouldBeFound("sl.notEquals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllCk2xsiesBySlIsInShouldWork() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where sl in DEFAULT_SL or UPDATED_SL
        defaultCk2xsyShouldBeFound("sl.in=" + DEFAULT_SL + "," + UPDATED_SL);

        // Get all the ck2xsyList where sl equals to UPDATED_SL
        defaultCk2xsyShouldNotBeFound("sl.in=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllCk2xsiesBySlIsNullOrNotNull() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where sl is not null
        defaultCk2xsyShouldBeFound("sl.specified=true");

        // Get all the ck2xsyList where sl is null
        defaultCk2xsyShouldNotBeFound("sl.specified=false");
    }

    @Test
    @Transactional
    void getAllCk2xsiesBySlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where sl is greater than or equal to DEFAULT_SL
        defaultCk2xsyShouldBeFound("sl.greaterThanOrEqual=" + DEFAULT_SL);

        // Get all the ck2xsyList where sl is greater than or equal to UPDATED_SL
        defaultCk2xsyShouldNotBeFound("sl.greaterThanOrEqual=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllCk2xsiesBySlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where sl is less than or equal to DEFAULT_SL
        defaultCk2xsyShouldBeFound("sl.lessThanOrEqual=" + DEFAULT_SL);

        // Get all the ck2xsyList where sl is less than or equal to SMALLER_SL
        defaultCk2xsyShouldNotBeFound("sl.lessThanOrEqual=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllCk2xsiesBySlIsLessThanSomething() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where sl is less than DEFAULT_SL
        defaultCk2xsyShouldNotBeFound("sl.lessThan=" + DEFAULT_SL);

        // Get all the ck2xsyList where sl is less than UPDATED_SL
        defaultCk2xsyShouldBeFound("sl.lessThan=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllCk2xsiesBySlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        // Get all the ck2xsyList where sl is greater than DEFAULT_SL
        defaultCk2xsyShouldNotBeFound("sl.greaterThan=" + DEFAULT_SL);

        // Get all the ck2xsyList where sl is greater than SMALLER_SL
        defaultCk2xsyShouldBeFound("sl.greaterThan=" + SMALLER_SL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCk2xsyShouldBeFound(String filter) throws Exception {
        restCk2xsyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ck2xsy.getId().intValue())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].cpbh").value(hasItem(DEFAULT_CPBH)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(DEFAULT_SL.intValue())));

        // Check, that the count call also returns 1
        restCk2xsyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCk2xsyShouldNotBeFound(String filter) throws Exception {
        restCk2xsyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCk2xsyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCk2xsy() throws Exception {
        // Get the ck2xsy
        restCk2xsyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCk2xsy() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        int databaseSizeBeforeUpdate = ck2xsyRepository.findAll().size();

        // Update the ck2xsy
        Ck2xsy updatedCk2xsy = ck2xsyRepository.findById(ck2xsy.getId()).get();
        // Disconnect from session so that the updates on updatedCk2xsy are not directly saved in db
        em.detach(updatedCk2xsy);
        updatedCk2xsy.rq(UPDATED_RQ).cpbh(UPDATED_CPBH).sl(UPDATED_SL);
        Ck2xsyDTO ck2xsyDTO = ck2xsyMapper.toDto(updatedCk2xsy);

        restCk2xsyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ck2xsyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ck2xsyDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ck2xsy in the database
        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeUpdate);
        Ck2xsy testCk2xsy = ck2xsyList.get(ck2xsyList.size() - 1);
        assertThat(testCk2xsy.getRq()).isEqualTo(UPDATED_RQ);
        assertThat(testCk2xsy.getCpbh()).isEqualTo(UPDATED_CPBH);
        assertThat(testCk2xsy.getSl()).isEqualTo(UPDATED_SL);

        // Validate the Ck2xsy in Elasticsearch
        verify(mockCk2xsySearchRepository).save(testCk2xsy);
    }

    @Test
    @Transactional
    void putNonExistingCk2xsy() throws Exception {
        int databaseSizeBeforeUpdate = ck2xsyRepository.findAll().size();
        ck2xsy.setId(count.incrementAndGet());

        // Create the Ck2xsy
        Ck2xsyDTO ck2xsyDTO = ck2xsyMapper.toDto(ck2xsy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCk2xsyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ck2xsyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ck2xsyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ck2xsy in the database
        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ck2xsy in Elasticsearch
        verify(mockCk2xsySearchRepository, times(0)).save(ck2xsy);
    }

    @Test
    @Transactional
    void putWithIdMismatchCk2xsy() throws Exception {
        int databaseSizeBeforeUpdate = ck2xsyRepository.findAll().size();
        ck2xsy.setId(count.incrementAndGet());

        // Create the Ck2xsy
        Ck2xsyDTO ck2xsyDTO = ck2xsyMapper.toDto(ck2xsy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCk2xsyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ck2xsyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ck2xsy in the database
        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ck2xsy in Elasticsearch
        verify(mockCk2xsySearchRepository, times(0)).save(ck2xsy);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCk2xsy() throws Exception {
        int databaseSizeBeforeUpdate = ck2xsyRepository.findAll().size();
        ck2xsy.setId(count.incrementAndGet());

        // Create the Ck2xsy
        Ck2xsyDTO ck2xsyDTO = ck2xsyMapper.toDto(ck2xsy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCk2xsyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ck2xsyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ck2xsy in the database
        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ck2xsy in Elasticsearch
        verify(mockCk2xsySearchRepository, times(0)).save(ck2xsy);
    }

    @Test
    @Transactional
    void partialUpdateCk2xsyWithPatch() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        int databaseSizeBeforeUpdate = ck2xsyRepository.findAll().size();

        // Update the ck2xsy using partial update
        Ck2xsy partialUpdatedCk2xsy = new Ck2xsy();
        partialUpdatedCk2xsy.setId(ck2xsy.getId());

        restCk2xsyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCk2xsy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCk2xsy))
            )
            .andExpect(status().isOk());

        // Validate the Ck2xsy in the database
        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeUpdate);
        Ck2xsy testCk2xsy = ck2xsyList.get(ck2xsyList.size() - 1);
        assertThat(testCk2xsy.getRq()).isEqualTo(DEFAULT_RQ);
        assertThat(testCk2xsy.getCpbh()).isEqualTo(DEFAULT_CPBH);
        assertThat(testCk2xsy.getSl()).isEqualTo(DEFAULT_SL);
    }

    @Test
    @Transactional
    void fullUpdateCk2xsyWithPatch() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        int databaseSizeBeforeUpdate = ck2xsyRepository.findAll().size();

        // Update the ck2xsy using partial update
        Ck2xsy partialUpdatedCk2xsy = new Ck2xsy();
        partialUpdatedCk2xsy.setId(ck2xsy.getId());

        partialUpdatedCk2xsy.rq(UPDATED_RQ).cpbh(UPDATED_CPBH).sl(UPDATED_SL);

        restCk2xsyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCk2xsy.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCk2xsy))
            )
            .andExpect(status().isOk());

        // Validate the Ck2xsy in the database
        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeUpdate);
        Ck2xsy testCk2xsy = ck2xsyList.get(ck2xsyList.size() - 1);
        assertThat(testCk2xsy.getRq()).isEqualTo(UPDATED_RQ);
        assertThat(testCk2xsy.getCpbh()).isEqualTo(UPDATED_CPBH);
        assertThat(testCk2xsy.getSl()).isEqualTo(UPDATED_SL);
    }

    @Test
    @Transactional
    void patchNonExistingCk2xsy() throws Exception {
        int databaseSizeBeforeUpdate = ck2xsyRepository.findAll().size();
        ck2xsy.setId(count.incrementAndGet());

        // Create the Ck2xsy
        Ck2xsyDTO ck2xsyDTO = ck2xsyMapper.toDto(ck2xsy);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCk2xsyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ck2xsyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ck2xsyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ck2xsy in the database
        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ck2xsy in Elasticsearch
        verify(mockCk2xsySearchRepository, times(0)).save(ck2xsy);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCk2xsy() throws Exception {
        int databaseSizeBeforeUpdate = ck2xsyRepository.findAll().size();
        ck2xsy.setId(count.incrementAndGet());

        // Create the Ck2xsy
        Ck2xsyDTO ck2xsyDTO = ck2xsyMapper.toDto(ck2xsy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCk2xsyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ck2xsyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ck2xsy in the database
        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ck2xsy in Elasticsearch
        verify(mockCk2xsySearchRepository, times(0)).save(ck2xsy);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCk2xsy() throws Exception {
        int databaseSizeBeforeUpdate = ck2xsyRepository.findAll().size();
        ck2xsy.setId(count.incrementAndGet());

        // Create the Ck2xsy
        Ck2xsyDTO ck2xsyDTO = ck2xsyMapper.toDto(ck2xsy);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCk2xsyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ck2xsyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ck2xsy in the database
        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ck2xsy in Elasticsearch
        verify(mockCk2xsySearchRepository, times(0)).save(ck2xsy);
    }

    @Test
    @Transactional
    void deleteCk2xsy() throws Exception {
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);

        int databaseSizeBeforeDelete = ck2xsyRepository.findAll().size();

        // Delete the ck2xsy
        restCk2xsyMockMvc
            .perform(delete(ENTITY_API_URL_ID, ck2xsy.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ck2xsy> ck2xsyList = ck2xsyRepository.findAll();
        assertThat(ck2xsyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Ck2xsy in Elasticsearch
        verify(mockCk2xsySearchRepository, times(1)).deleteById(ck2xsy.getId());
    }

    @Test
    @Transactional
    void searchCk2xsy() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        ck2xsyRepository.saveAndFlush(ck2xsy);
        when(mockCk2xsySearchRepository.search(queryStringQuery("id:" + ck2xsy.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ck2xsy), PageRequest.of(0, 1), 1));

        // Search the ck2xsy
        restCk2xsyMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ck2xsy.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ck2xsy.getId().intValue())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].cpbh").value(hasItem(DEFAULT_CPBH)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(DEFAULT_SL.intValue())));
    }
}
