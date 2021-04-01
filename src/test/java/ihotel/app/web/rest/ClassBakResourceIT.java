package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.ClassBak;
import ihotel.app.repository.ClassBakRepository;
import ihotel.app.repository.search.ClassBakSearchRepository;
import ihotel.app.service.criteria.ClassBakCriteria;
import ihotel.app.service.dto.ClassBakDTO;
import ihotel.app.service.mapper.ClassBakMapper;
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
 * Integration tests for the {@link ClassBakResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClassBakResourceIT {

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final Instant DEFAULT_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_GHNAME = "AAAAAAAAAA";
    private static final String UPDATED_GHNAME = "BBBBBBBBBB";

    private static final String DEFAULT_BAK = "AAAAAAAAAA";
    private static final String UPDATED_BAK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/class-baks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/class-baks";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClassBakRepository classBakRepository;

    @Autowired
    private ClassBakMapper classBakMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.ClassBakSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClassBakSearchRepository mockClassBakSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassBakMockMvc;

    private ClassBak classBak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassBak createEntity(EntityManager em) {
        ClassBak classBak = new ClassBak().empn(DEFAULT_EMPN).dt(DEFAULT_DT).rq(DEFAULT_RQ).ghname(DEFAULT_GHNAME).bak(DEFAULT_BAK);
        return classBak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassBak createUpdatedEntity(EntityManager em) {
        ClassBak classBak = new ClassBak().empn(UPDATED_EMPN).dt(UPDATED_DT).rq(UPDATED_RQ).ghname(UPDATED_GHNAME).bak(UPDATED_BAK);
        return classBak;
    }

    @BeforeEach
    public void initTest() {
        classBak = createEntity(em);
    }

    @Test
    @Transactional
    void createClassBak() throws Exception {
        int databaseSizeBeforeCreate = classBakRepository.findAll().size();
        // Create the ClassBak
        ClassBakDTO classBakDTO = classBakMapper.toDto(classBak);
        restClassBakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classBakDTO)))
            .andExpect(status().isCreated());

        // Validate the ClassBak in the database
        List<ClassBak> classBakList = classBakRepository.findAll();
        assertThat(classBakList).hasSize(databaseSizeBeforeCreate + 1);
        ClassBak testClassBak = classBakList.get(classBakList.size() - 1);
        assertThat(testClassBak.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testClassBak.getDt()).isEqualTo(DEFAULT_DT);
        assertThat(testClassBak.getRq()).isEqualTo(DEFAULT_RQ);
        assertThat(testClassBak.getGhname()).isEqualTo(DEFAULT_GHNAME);
        assertThat(testClassBak.getBak()).isEqualTo(DEFAULT_BAK);

        // Validate the ClassBak in Elasticsearch
        verify(mockClassBakSearchRepository, times(1)).save(testClassBak);
    }

    @Test
    @Transactional
    void createClassBakWithExistingId() throws Exception {
        // Create the ClassBak with an existing ID
        classBak.setId(1L);
        ClassBakDTO classBakDTO = classBakMapper.toDto(classBak);

        int databaseSizeBeforeCreate = classBakRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassBakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classBakDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ClassBak in the database
        List<ClassBak> classBakList = classBakRepository.findAll();
        assertThat(classBakList).hasSize(databaseSizeBeforeCreate);

        // Validate the ClassBak in Elasticsearch
        verify(mockClassBakSearchRepository, times(0)).save(classBak);
    }

    @Test
    @Transactional
    void getAllClassBaks() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList
        restClassBakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classBak.getId().intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].dt").value(hasItem(DEFAULT_DT.toString())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].ghname").value(hasItem(DEFAULT_GHNAME)))
            .andExpect(jsonPath("$.[*].bak").value(hasItem(DEFAULT_BAK)));
    }

    @Test
    @Transactional
    void getClassBak() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get the classBak
        restClassBakMockMvc
            .perform(get(ENTITY_API_URL_ID, classBak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classBak.getId().intValue()))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.dt").value(DEFAULT_DT.toString()))
            .andExpect(jsonPath("$.rq").value(DEFAULT_RQ.toString()))
            .andExpect(jsonPath("$.ghname").value(DEFAULT_GHNAME))
            .andExpect(jsonPath("$.bak").value(DEFAULT_BAK));
    }

    @Test
    @Transactional
    void getClassBaksByIdFiltering() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        Long id = classBak.getId();

        defaultClassBakShouldBeFound("id.equals=" + id);
        defaultClassBakShouldNotBeFound("id.notEquals=" + id);

        defaultClassBakShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClassBakShouldNotBeFound("id.greaterThan=" + id);

        defaultClassBakShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClassBakShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClassBaksByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where empn equals to DEFAULT_EMPN
        defaultClassBakShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the classBakList where empn equals to UPDATED_EMPN
        defaultClassBakShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassBaksByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where empn not equals to DEFAULT_EMPN
        defaultClassBakShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the classBakList where empn not equals to UPDATED_EMPN
        defaultClassBakShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassBaksByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultClassBakShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the classBakList where empn equals to UPDATED_EMPN
        defaultClassBakShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassBaksByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where empn is not null
        defaultClassBakShouldBeFound("empn.specified=true");

        // Get all the classBakList where empn is null
        defaultClassBakShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllClassBaksByEmpnContainsSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where empn contains DEFAULT_EMPN
        defaultClassBakShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the classBakList where empn contains UPDATED_EMPN
        defaultClassBakShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassBaksByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where empn does not contain DEFAULT_EMPN
        defaultClassBakShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the classBakList where empn does not contain UPDATED_EMPN
        defaultClassBakShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassBaksByDtIsEqualToSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where dt equals to DEFAULT_DT
        defaultClassBakShouldBeFound("dt.equals=" + DEFAULT_DT);

        // Get all the classBakList where dt equals to UPDATED_DT
        defaultClassBakShouldNotBeFound("dt.equals=" + UPDATED_DT);
    }

    @Test
    @Transactional
    void getAllClassBaksByDtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where dt not equals to DEFAULT_DT
        defaultClassBakShouldNotBeFound("dt.notEquals=" + DEFAULT_DT);

        // Get all the classBakList where dt not equals to UPDATED_DT
        defaultClassBakShouldBeFound("dt.notEquals=" + UPDATED_DT);
    }

    @Test
    @Transactional
    void getAllClassBaksByDtIsInShouldWork() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where dt in DEFAULT_DT or UPDATED_DT
        defaultClassBakShouldBeFound("dt.in=" + DEFAULT_DT + "," + UPDATED_DT);

        // Get all the classBakList where dt equals to UPDATED_DT
        defaultClassBakShouldNotBeFound("dt.in=" + UPDATED_DT);
    }

    @Test
    @Transactional
    void getAllClassBaksByDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where dt is not null
        defaultClassBakShouldBeFound("dt.specified=true");

        // Get all the classBakList where dt is null
        defaultClassBakShouldNotBeFound("dt.specified=false");
    }

    @Test
    @Transactional
    void getAllClassBaksByRqIsEqualToSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where rq equals to DEFAULT_RQ
        defaultClassBakShouldBeFound("rq.equals=" + DEFAULT_RQ);

        // Get all the classBakList where rq equals to UPDATED_RQ
        defaultClassBakShouldNotBeFound("rq.equals=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllClassBaksByRqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where rq not equals to DEFAULT_RQ
        defaultClassBakShouldNotBeFound("rq.notEquals=" + DEFAULT_RQ);

        // Get all the classBakList where rq not equals to UPDATED_RQ
        defaultClassBakShouldBeFound("rq.notEquals=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllClassBaksByRqIsInShouldWork() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where rq in DEFAULT_RQ or UPDATED_RQ
        defaultClassBakShouldBeFound("rq.in=" + DEFAULT_RQ + "," + UPDATED_RQ);

        // Get all the classBakList where rq equals to UPDATED_RQ
        defaultClassBakShouldNotBeFound("rq.in=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllClassBaksByRqIsNullOrNotNull() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where rq is not null
        defaultClassBakShouldBeFound("rq.specified=true");

        // Get all the classBakList where rq is null
        defaultClassBakShouldNotBeFound("rq.specified=false");
    }

    @Test
    @Transactional
    void getAllClassBaksByGhnameIsEqualToSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where ghname equals to DEFAULT_GHNAME
        defaultClassBakShouldBeFound("ghname.equals=" + DEFAULT_GHNAME);

        // Get all the classBakList where ghname equals to UPDATED_GHNAME
        defaultClassBakShouldNotBeFound("ghname.equals=" + UPDATED_GHNAME);
    }

    @Test
    @Transactional
    void getAllClassBaksByGhnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where ghname not equals to DEFAULT_GHNAME
        defaultClassBakShouldNotBeFound("ghname.notEquals=" + DEFAULT_GHNAME);

        // Get all the classBakList where ghname not equals to UPDATED_GHNAME
        defaultClassBakShouldBeFound("ghname.notEquals=" + UPDATED_GHNAME);
    }

    @Test
    @Transactional
    void getAllClassBaksByGhnameIsInShouldWork() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where ghname in DEFAULT_GHNAME or UPDATED_GHNAME
        defaultClassBakShouldBeFound("ghname.in=" + DEFAULT_GHNAME + "," + UPDATED_GHNAME);

        // Get all the classBakList where ghname equals to UPDATED_GHNAME
        defaultClassBakShouldNotBeFound("ghname.in=" + UPDATED_GHNAME);
    }

    @Test
    @Transactional
    void getAllClassBaksByGhnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where ghname is not null
        defaultClassBakShouldBeFound("ghname.specified=true");

        // Get all the classBakList where ghname is null
        defaultClassBakShouldNotBeFound("ghname.specified=false");
    }

    @Test
    @Transactional
    void getAllClassBaksByGhnameContainsSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where ghname contains DEFAULT_GHNAME
        defaultClassBakShouldBeFound("ghname.contains=" + DEFAULT_GHNAME);

        // Get all the classBakList where ghname contains UPDATED_GHNAME
        defaultClassBakShouldNotBeFound("ghname.contains=" + UPDATED_GHNAME);
    }

    @Test
    @Transactional
    void getAllClassBaksByGhnameNotContainsSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where ghname does not contain DEFAULT_GHNAME
        defaultClassBakShouldNotBeFound("ghname.doesNotContain=" + DEFAULT_GHNAME);

        // Get all the classBakList where ghname does not contain UPDATED_GHNAME
        defaultClassBakShouldBeFound("ghname.doesNotContain=" + UPDATED_GHNAME);
    }

    @Test
    @Transactional
    void getAllClassBaksByBakIsEqualToSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where bak equals to DEFAULT_BAK
        defaultClassBakShouldBeFound("bak.equals=" + DEFAULT_BAK);

        // Get all the classBakList where bak equals to UPDATED_BAK
        defaultClassBakShouldNotBeFound("bak.equals=" + UPDATED_BAK);
    }

    @Test
    @Transactional
    void getAllClassBaksByBakIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where bak not equals to DEFAULT_BAK
        defaultClassBakShouldNotBeFound("bak.notEquals=" + DEFAULT_BAK);

        // Get all the classBakList where bak not equals to UPDATED_BAK
        defaultClassBakShouldBeFound("bak.notEquals=" + UPDATED_BAK);
    }

    @Test
    @Transactional
    void getAllClassBaksByBakIsInShouldWork() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where bak in DEFAULT_BAK or UPDATED_BAK
        defaultClassBakShouldBeFound("bak.in=" + DEFAULT_BAK + "," + UPDATED_BAK);

        // Get all the classBakList where bak equals to UPDATED_BAK
        defaultClassBakShouldNotBeFound("bak.in=" + UPDATED_BAK);
    }

    @Test
    @Transactional
    void getAllClassBaksByBakIsNullOrNotNull() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where bak is not null
        defaultClassBakShouldBeFound("bak.specified=true");

        // Get all the classBakList where bak is null
        defaultClassBakShouldNotBeFound("bak.specified=false");
    }

    @Test
    @Transactional
    void getAllClassBaksByBakContainsSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where bak contains DEFAULT_BAK
        defaultClassBakShouldBeFound("bak.contains=" + DEFAULT_BAK);

        // Get all the classBakList where bak contains UPDATED_BAK
        defaultClassBakShouldNotBeFound("bak.contains=" + UPDATED_BAK);
    }

    @Test
    @Transactional
    void getAllClassBaksByBakNotContainsSomething() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        // Get all the classBakList where bak does not contain DEFAULT_BAK
        defaultClassBakShouldNotBeFound("bak.doesNotContain=" + DEFAULT_BAK);

        // Get all the classBakList where bak does not contain UPDATED_BAK
        defaultClassBakShouldBeFound("bak.doesNotContain=" + UPDATED_BAK);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClassBakShouldBeFound(String filter) throws Exception {
        restClassBakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classBak.getId().intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].dt").value(hasItem(DEFAULT_DT.toString())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].ghname").value(hasItem(DEFAULT_GHNAME)))
            .andExpect(jsonPath("$.[*].bak").value(hasItem(DEFAULT_BAK)));

        // Check, that the count call also returns 1
        restClassBakMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClassBakShouldNotBeFound(String filter) throws Exception {
        restClassBakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClassBakMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClassBak() throws Exception {
        // Get the classBak
        restClassBakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClassBak() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        int databaseSizeBeforeUpdate = classBakRepository.findAll().size();

        // Update the classBak
        ClassBak updatedClassBak = classBakRepository.findById(classBak.getId()).get();
        // Disconnect from session so that the updates on updatedClassBak are not directly saved in db
        em.detach(updatedClassBak);
        updatedClassBak.empn(UPDATED_EMPN).dt(UPDATED_DT).rq(UPDATED_RQ).ghname(UPDATED_GHNAME).bak(UPDATED_BAK);
        ClassBakDTO classBakDTO = classBakMapper.toDto(updatedClassBak);

        restClassBakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classBakDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classBakDTO))
            )
            .andExpect(status().isOk());

        // Validate the ClassBak in the database
        List<ClassBak> classBakList = classBakRepository.findAll();
        assertThat(classBakList).hasSize(databaseSizeBeforeUpdate);
        ClassBak testClassBak = classBakList.get(classBakList.size() - 1);
        assertThat(testClassBak.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testClassBak.getDt()).isEqualTo(UPDATED_DT);
        assertThat(testClassBak.getRq()).isEqualTo(UPDATED_RQ);
        assertThat(testClassBak.getGhname()).isEqualTo(UPDATED_GHNAME);
        assertThat(testClassBak.getBak()).isEqualTo(UPDATED_BAK);

        // Validate the ClassBak in Elasticsearch
        verify(mockClassBakSearchRepository).save(testClassBak);
    }

    @Test
    @Transactional
    void putNonExistingClassBak() throws Exception {
        int databaseSizeBeforeUpdate = classBakRepository.findAll().size();
        classBak.setId(count.incrementAndGet());

        // Create the ClassBak
        ClassBakDTO classBakDTO = classBakMapper.toDto(classBak);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassBakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classBakDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classBakDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassBak in the database
        List<ClassBak> classBakList = classBakRepository.findAll();
        assertThat(classBakList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassBak in Elasticsearch
        verify(mockClassBakSearchRepository, times(0)).save(classBak);
    }

    @Test
    @Transactional
    void putWithIdMismatchClassBak() throws Exception {
        int databaseSizeBeforeUpdate = classBakRepository.findAll().size();
        classBak.setId(count.incrementAndGet());

        // Create the ClassBak
        ClassBakDTO classBakDTO = classBakMapper.toDto(classBak);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassBakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classBakDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassBak in the database
        List<ClassBak> classBakList = classBakRepository.findAll();
        assertThat(classBakList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassBak in Elasticsearch
        verify(mockClassBakSearchRepository, times(0)).save(classBak);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClassBak() throws Exception {
        int databaseSizeBeforeUpdate = classBakRepository.findAll().size();
        classBak.setId(count.incrementAndGet());

        // Create the ClassBak
        ClassBakDTO classBakDTO = classBakMapper.toDto(classBak);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassBakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classBakDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClassBak in the database
        List<ClassBak> classBakList = classBakRepository.findAll();
        assertThat(classBakList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassBak in Elasticsearch
        verify(mockClassBakSearchRepository, times(0)).save(classBak);
    }

    @Test
    @Transactional
    void partialUpdateClassBakWithPatch() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        int databaseSizeBeforeUpdate = classBakRepository.findAll().size();

        // Update the classBak using partial update
        ClassBak partialUpdatedClassBak = new ClassBak();
        partialUpdatedClassBak.setId(classBak.getId());

        partialUpdatedClassBak.empn(UPDATED_EMPN).dt(UPDATED_DT).bak(UPDATED_BAK);

        restClassBakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassBak.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClassBak))
            )
            .andExpect(status().isOk());

        // Validate the ClassBak in the database
        List<ClassBak> classBakList = classBakRepository.findAll();
        assertThat(classBakList).hasSize(databaseSizeBeforeUpdate);
        ClassBak testClassBak = classBakList.get(classBakList.size() - 1);
        assertThat(testClassBak.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testClassBak.getDt()).isEqualTo(UPDATED_DT);
        assertThat(testClassBak.getRq()).isEqualTo(DEFAULT_RQ);
        assertThat(testClassBak.getGhname()).isEqualTo(DEFAULT_GHNAME);
        assertThat(testClassBak.getBak()).isEqualTo(UPDATED_BAK);
    }

    @Test
    @Transactional
    void fullUpdateClassBakWithPatch() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        int databaseSizeBeforeUpdate = classBakRepository.findAll().size();

        // Update the classBak using partial update
        ClassBak partialUpdatedClassBak = new ClassBak();
        partialUpdatedClassBak.setId(classBak.getId());

        partialUpdatedClassBak.empn(UPDATED_EMPN).dt(UPDATED_DT).rq(UPDATED_RQ).ghname(UPDATED_GHNAME).bak(UPDATED_BAK);

        restClassBakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassBak.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClassBak))
            )
            .andExpect(status().isOk());

        // Validate the ClassBak in the database
        List<ClassBak> classBakList = classBakRepository.findAll();
        assertThat(classBakList).hasSize(databaseSizeBeforeUpdate);
        ClassBak testClassBak = classBakList.get(classBakList.size() - 1);
        assertThat(testClassBak.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testClassBak.getDt()).isEqualTo(UPDATED_DT);
        assertThat(testClassBak.getRq()).isEqualTo(UPDATED_RQ);
        assertThat(testClassBak.getGhname()).isEqualTo(UPDATED_GHNAME);
        assertThat(testClassBak.getBak()).isEqualTo(UPDATED_BAK);
    }

    @Test
    @Transactional
    void patchNonExistingClassBak() throws Exception {
        int databaseSizeBeforeUpdate = classBakRepository.findAll().size();
        classBak.setId(count.incrementAndGet());

        // Create the ClassBak
        ClassBakDTO classBakDTO = classBakMapper.toDto(classBak);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassBakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classBakDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classBakDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassBak in the database
        List<ClassBak> classBakList = classBakRepository.findAll();
        assertThat(classBakList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassBak in Elasticsearch
        verify(mockClassBakSearchRepository, times(0)).save(classBak);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClassBak() throws Exception {
        int databaseSizeBeforeUpdate = classBakRepository.findAll().size();
        classBak.setId(count.incrementAndGet());

        // Create the ClassBak
        ClassBakDTO classBakDTO = classBakMapper.toDto(classBak);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassBakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classBakDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassBak in the database
        List<ClassBak> classBakList = classBakRepository.findAll();
        assertThat(classBakList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassBak in Elasticsearch
        verify(mockClassBakSearchRepository, times(0)).save(classBak);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClassBak() throws Exception {
        int databaseSizeBeforeUpdate = classBakRepository.findAll().size();
        classBak.setId(count.incrementAndGet());

        // Create the ClassBak
        ClassBakDTO classBakDTO = classBakMapper.toDto(classBak);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassBakMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(classBakDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClassBak in the database
        List<ClassBak> classBakList = classBakRepository.findAll();
        assertThat(classBakList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassBak in Elasticsearch
        verify(mockClassBakSearchRepository, times(0)).save(classBak);
    }

    @Test
    @Transactional
    void deleteClassBak() throws Exception {
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);

        int databaseSizeBeforeDelete = classBakRepository.findAll().size();

        // Delete the classBak
        restClassBakMockMvc
            .perform(delete(ENTITY_API_URL_ID, classBak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassBak> classBakList = classBakRepository.findAll();
        assertThat(classBakList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ClassBak in Elasticsearch
        verify(mockClassBakSearchRepository, times(1)).deleteById(classBak.getId());
    }

    @Test
    @Transactional
    void searchClassBak() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        classBakRepository.saveAndFlush(classBak);
        when(mockClassBakSearchRepository.search(queryStringQuery("id:" + classBak.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(classBak), PageRequest.of(0, 1), 1));

        // Search the classBak
        restClassBakMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + classBak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classBak.getId().intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].dt").value(hasItem(DEFAULT_DT.toString())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].ghname").value(hasItem(DEFAULT_GHNAME)))
            .andExpect(jsonPath("$.[*].bak").value(hasItem(DEFAULT_BAK)));
    }
}
