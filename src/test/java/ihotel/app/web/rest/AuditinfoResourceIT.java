package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Auditinfo;
import ihotel.app.repository.AuditinfoRepository;
import ihotel.app.repository.search.AuditinfoSearchRepository;
import ihotel.app.service.criteria.AuditinfoCriteria;
import ihotel.app.service.dto.AuditinfoDTO;
import ihotel.app.service.mapper.AuditinfoMapper;
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
 * Integration tests for the {@link AuditinfoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AuditinfoResourceIT {

    private static final Instant DEFAULT_AUDITDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AUDITDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_AUDITTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_AUDITTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final String DEFAULT_AIDENTIFY = "AAAAAAAAAA";
    private static final String UPDATED_AIDENTIFY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/auditinfos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/auditinfos";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuditinfoRepository auditinfoRepository;

    @Autowired
    private AuditinfoMapper auditinfoMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.AuditinfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private AuditinfoSearchRepository mockAuditinfoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuditinfoMockMvc;

    private Auditinfo auditinfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auditinfo createEntity(EntityManager em) {
        Auditinfo auditinfo = new Auditinfo()
            .auditdate(DEFAULT_AUDITDATE)
            .audittime(DEFAULT_AUDITTIME)
            .empn(DEFAULT_EMPN)
            .aidentify(DEFAULT_AIDENTIFY);
        return auditinfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Auditinfo createUpdatedEntity(EntityManager em) {
        Auditinfo auditinfo = new Auditinfo()
            .auditdate(UPDATED_AUDITDATE)
            .audittime(UPDATED_AUDITTIME)
            .empn(UPDATED_EMPN)
            .aidentify(UPDATED_AIDENTIFY);
        return auditinfo;
    }

    @BeforeEach
    public void initTest() {
        auditinfo = createEntity(em);
    }

    @Test
    @Transactional
    void createAuditinfo() throws Exception {
        int databaseSizeBeforeCreate = auditinfoRepository.findAll().size();
        // Create the Auditinfo
        AuditinfoDTO auditinfoDTO = auditinfoMapper.toDto(auditinfo);
        restAuditinfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditinfoDTO)))
            .andExpect(status().isCreated());

        // Validate the Auditinfo in the database
        List<Auditinfo> auditinfoList = auditinfoRepository.findAll();
        assertThat(auditinfoList).hasSize(databaseSizeBeforeCreate + 1);
        Auditinfo testAuditinfo = auditinfoList.get(auditinfoList.size() - 1);
        assertThat(testAuditinfo.getAuditdate()).isEqualTo(DEFAULT_AUDITDATE);
        assertThat(testAuditinfo.getAudittime()).isEqualTo(DEFAULT_AUDITTIME);
        assertThat(testAuditinfo.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testAuditinfo.getAidentify()).isEqualTo(DEFAULT_AIDENTIFY);

        // Validate the Auditinfo in Elasticsearch
        verify(mockAuditinfoSearchRepository, times(1)).save(testAuditinfo);
    }

    @Test
    @Transactional
    void createAuditinfoWithExistingId() throws Exception {
        // Create the Auditinfo with an existing ID
        auditinfo.setId(1L);
        AuditinfoDTO auditinfoDTO = auditinfoMapper.toDto(auditinfo);

        int databaseSizeBeforeCreate = auditinfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuditinfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditinfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Auditinfo in the database
        List<Auditinfo> auditinfoList = auditinfoRepository.findAll();
        assertThat(auditinfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Auditinfo in Elasticsearch
        verify(mockAuditinfoSearchRepository, times(0)).save(auditinfo);
    }

    @Test
    @Transactional
    void checkAuditdateIsRequired() throws Exception {
        int databaseSizeBeforeTest = auditinfoRepository.findAll().size();
        // set the field null
        auditinfo.setAuditdate(null);

        // Create the Auditinfo, which fails.
        AuditinfoDTO auditinfoDTO = auditinfoMapper.toDto(auditinfo);

        restAuditinfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditinfoDTO)))
            .andExpect(status().isBadRequest());

        List<Auditinfo> auditinfoList = auditinfoRepository.findAll();
        assertThat(auditinfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAuditinfos() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList
        restAuditinfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditinfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].auditdate").value(hasItem(DEFAULT_AUDITDATE.toString())))
            .andExpect(jsonPath("$.[*].audittime").value(hasItem(DEFAULT_AUDITTIME.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].aidentify").value(hasItem(DEFAULT_AIDENTIFY)));
    }

    @Test
    @Transactional
    void getAuditinfo() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get the auditinfo
        restAuditinfoMockMvc
            .perform(get(ENTITY_API_URL_ID, auditinfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(auditinfo.getId().intValue()))
            .andExpect(jsonPath("$.auditdate").value(DEFAULT_AUDITDATE.toString()))
            .andExpect(jsonPath("$.audittime").value(DEFAULT_AUDITTIME.toString()))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.aidentify").value(DEFAULT_AIDENTIFY));
    }

    @Test
    @Transactional
    void getAuditinfosByIdFiltering() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        Long id = auditinfo.getId();

        defaultAuditinfoShouldBeFound("id.equals=" + id);
        defaultAuditinfoShouldNotBeFound("id.notEquals=" + id);

        defaultAuditinfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAuditinfoShouldNotBeFound("id.greaterThan=" + id);

        defaultAuditinfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAuditinfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAuditinfosByAuditdateIsEqualToSomething() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where auditdate equals to DEFAULT_AUDITDATE
        defaultAuditinfoShouldBeFound("auditdate.equals=" + DEFAULT_AUDITDATE);

        // Get all the auditinfoList where auditdate equals to UPDATED_AUDITDATE
        defaultAuditinfoShouldNotBeFound("auditdate.equals=" + UPDATED_AUDITDATE);
    }

    @Test
    @Transactional
    void getAllAuditinfosByAuditdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where auditdate not equals to DEFAULT_AUDITDATE
        defaultAuditinfoShouldNotBeFound("auditdate.notEquals=" + DEFAULT_AUDITDATE);

        // Get all the auditinfoList where auditdate not equals to UPDATED_AUDITDATE
        defaultAuditinfoShouldBeFound("auditdate.notEquals=" + UPDATED_AUDITDATE);
    }

    @Test
    @Transactional
    void getAllAuditinfosByAuditdateIsInShouldWork() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where auditdate in DEFAULT_AUDITDATE or UPDATED_AUDITDATE
        defaultAuditinfoShouldBeFound("auditdate.in=" + DEFAULT_AUDITDATE + "," + UPDATED_AUDITDATE);

        // Get all the auditinfoList where auditdate equals to UPDATED_AUDITDATE
        defaultAuditinfoShouldNotBeFound("auditdate.in=" + UPDATED_AUDITDATE);
    }

    @Test
    @Transactional
    void getAllAuditinfosByAuditdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where auditdate is not null
        defaultAuditinfoShouldBeFound("auditdate.specified=true");

        // Get all the auditinfoList where auditdate is null
        defaultAuditinfoShouldNotBeFound("auditdate.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditinfosByAudittimeIsEqualToSomething() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where audittime equals to DEFAULT_AUDITTIME
        defaultAuditinfoShouldBeFound("audittime.equals=" + DEFAULT_AUDITTIME);

        // Get all the auditinfoList where audittime equals to UPDATED_AUDITTIME
        defaultAuditinfoShouldNotBeFound("audittime.equals=" + UPDATED_AUDITTIME);
    }

    @Test
    @Transactional
    void getAllAuditinfosByAudittimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where audittime not equals to DEFAULT_AUDITTIME
        defaultAuditinfoShouldNotBeFound("audittime.notEquals=" + DEFAULT_AUDITTIME);

        // Get all the auditinfoList where audittime not equals to UPDATED_AUDITTIME
        defaultAuditinfoShouldBeFound("audittime.notEquals=" + UPDATED_AUDITTIME);
    }

    @Test
    @Transactional
    void getAllAuditinfosByAudittimeIsInShouldWork() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where audittime in DEFAULT_AUDITTIME or UPDATED_AUDITTIME
        defaultAuditinfoShouldBeFound("audittime.in=" + DEFAULT_AUDITTIME + "," + UPDATED_AUDITTIME);

        // Get all the auditinfoList where audittime equals to UPDATED_AUDITTIME
        defaultAuditinfoShouldNotBeFound("audittime.in=" + UPDATED_AUDITTIME);
    }

    @Test
    @Transactional
    void getAllAuditinfosByAudittimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where audittime is not null
        defaultAuditinfoShouldBeFound("audittime.specified=true");

        // Get all the auditinfoList where audittime is null
        defaultAuditinfoShouldNotBeFound("audittime.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditinfosByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where empn equals to DEFAULT_EMPN
        defaultAuditinfoShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the auditinfoList where empn equals to UPDATED_EMPN
        defaultAuditinfoShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllAuditinfosByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where empn not equals to DEFAULT_EMPN
        defaultAuditinfoShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the auditinfoList where empn not equals to UPDATED_EMPN
        defaultAuditinfoShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllAuditinfosByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultAuditinfoShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the auditinfoList where empn equals to UPDATED_EMPN
        defaultAuditinfoShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllAuditinfosByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where empn is not null
        defaultAuditinfoShouldBeFound("empn.specified=true");

        // Get all the auditinfoList where empn is null
        defaultAuditinfoShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditinfosByEmpnContainsSomething() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where empn contains DEFAULT_EMPN
        defaultAuditinfoShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the auditinfoList where empn contains UPDATED_EMPN
        defaultAuditinfoShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllAuditinfosByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where empn does not contain DEFAULT_EMPN
        defaultAuditinfoShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the auditinfoList where empn does not contain UPDATED_EMPN
        defaultAuditinfoShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllAuditinfosByAidentifyIsEqualToSomething() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where aidentify equals to DEFAULT_AIDENTIFY
        defaultAuditinfoShouldBeFound("aidentify.equals=" + DEFAULT_AIDENTIFY);

        // Get all the auditinfoList where aidentify equals to UPDATED_AIDENTIFY
        defaultAuditinfoShouldNotBeFound("aidentify.equals=" + UPDATED_AIDENTIFY);
    }

    @Test
    @Transactional
    void getAllAuditinfosByAidentifyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where aidentify not equals to DEFAULT_AIDENTIFY
        defaultAuditinfoShouldNotBeFound("aidentify.notEquals=" + DEFAULT_AIDENTIFY);

        // Get all the auditinfoList where aidentify not equals to UPDATED_AIDENTIFY
        defaultAuditinfoShouldBeFound("aidentify.notEquals=" + UPDATED_AIDENTIFY);
    }

    @Test
    @Transactional
    void getAllAuditinfosByAidentifyIsInShouldWork() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where aidentify in DEFAULT_AIDENTIFY or UPDATED_AIDENTIFY
        defaultAuditinfoShouldBeFound("aidentify.in=" + DEFAULT_AIDENTIFY + "," + UPDATED_AIDENTIFY);

        // Get all the auditinfoList where aidentify equals to UPDATED_AIDENTIFY
        defaultAuditinfoShouldNotBeFound("aidentify.in=" + UPDATED_AIDENTIFY);
    }

    @Test
    @Transactional
    void getAllAuditinfosByAidentifyIsNullOrNotNull() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where aidentify is not null
        defaultAuditinfoShouldBeFound("aidentify.specified=true");

        // Get all the auditinfoList where aidentify is null
        defaultAuditinfoShouldNotBeFound("aidentify.specified=false");
    }

    @Test
    @Transactional
    void getAllAuditinfosByAidentifyContainsSomething() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where aidentify contains DEFAULT_AIDENTIFY
        defaultAuditinfoShouldBeFound("aidentify.contains=" + DEFAULT_AIDENTIFY);

        // Get all the auditinfoList where aidentify contains UPDATED_AIDENTIFY
        defaultAuditinfoShouldNotBeFound("aidentify.contains=" + UPDATED_AIDENTIFY);
    }

    @Test
    @Transactional
    void getAllAuditinfosByAidentifyNotContainsSomething() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        // Get all the auditinfoList where aidentify does not contain DEFAULT_AIDENTIFY
        defaultAuditinfoShouldNotBeFound("aidentify.doesNotContain=" + DEFAULT_AIDENTIFY);

        // Get all the auditinfoList where aidentify does not contain UPDATED_AIDENTIFY
        defaultAuditinfoShouldBeFound("aidentify.doesNotContain=" + UPDATED_AIDENTIFY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAuditinfoShouldBeFound(String filter) throws Exception {
        restAuditinfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditinfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].auditdate").value(hasItem(DEFAULT_AUDITDATE.toString())))
            .andExpect(jsonPath("$.[*].audittime").value(hasItem(DEFAULT_AUDITTIME.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].aidentify").value(hasItem(DEFAULT_AIDENTIFY)));

        // Check, that the count call also returns 1
        restAuditinfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAuditinfoShouldNotBeFound(String filter) throws Exception {
        restAuditinfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAuditinfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAuditinfo() throws Exception {
        // Get the auditinfo
        restAuditinfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAuditinfo() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        int databaseSizeBeforeUpdate = auditinfoRepository.findAll().size();

        // Update the auditinfo
        Auditinfo updatedAuditinfo = auditinfoRepository.findById(auditinfo.getId()).get();
        // Disconnect from session so that the updates on updatedAuditinfo are not directly saved in db
        em.detach(updatedAuditinfo);
        updatedAuditinfo.auditdate(UPDATED_AUDITDATE).audittime(UPDATED_AUDITTIME).empn(UPDATED_EMPN).aidentify(UPDATED_AIDENTIFY);
        AuditinfoDTO auditinfoDTO = auditinfoMapper.toDto(updatedAuditinfo);

        restAuditinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auditinfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditinfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Auditinfo in the database
        List<Auditinfo> auditinfoList = auditinfoRepository.findAll();
        assertThat(auditinfoList).hasSize(databaseSizeBeforeUpdate);
        Auditinfo testAuditinfo = auditinfoList.get(auditinfoList.size() - 1);
        assertThat(testAuditinfo.getAuditdate()).isEqualTo(UPDATED_AUDITDATE);
        assertThat(testAuditinfo.getAudittime()).isEqualTo(UPDATED_AUDITTIME);
        assertThat(testAuditinfo.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testAuditinfo.getAidentify()).isEqualTo(UPDATED_AIDENTIFY);

        // Validate the Auditinfo in Elasticsearch
        verify(mockAuditinfoSearchRepository).save(testAuditinfo);
    }

    @Test
    @Transactional
    void putNonExistingAuditinfo() throws Exception {
        int databaseSizeBeforeUpdate = auditinfoRepository.findAll().size();
        auditinfo.setId(count.incrementAndGet());

        // Create the Auditinfo
        AuditinfoDTO auditinfoDTO = auditinfoMapper.toDto(auditinfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, auditinfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auditinfo in the database
        List<Auditinfo> auditinfoList = auditinfoRepository.findAll();
        assertThat(auditinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Auditinfo in Elasticsearch
        verify(mockAuditinfoSearchRepository, times(0)).save(auditinfo);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuditinfo() throws Exception {
        int databaseSizeBeforeUpdate = auditinfoRepository.findAll().size();
        auditinfo.setId(count.incrementAndGet());

        // Create the Auditinfo
        AuditinfoDTO auditinfoDTO = auditinfoMapper.toDto(auditinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(auditinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auditinfo in the database
        List<Auditinfo> auditinfoList = auditinfoRepository.findAll();
        assertThat(auditinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Auditinfo in Elasticsearch
        verify(mockAuditinfoSearchRepository, times(0)).save(auditinfo);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuditinfo() throws Exception {
        int databaseSizeBeforeUpdate = auditinfoRepository.findAll().size();
        auditinfo.setId(count.incrementAndGet());

        // Create the Auditinfo
        AuditinfoDTO auditinfoDTO = auditinfoMapper.toDto(auditinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditinfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(auditinfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Auditinfo in the database
        List<Auditinfo> auditinfoList = auditinfoRepository.findAll();
        assertThat(auditinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Auditinfo in Elasticsearch
        verify(mockAuditinfoSearchRepository, times(0)).save(auditinfo);
    }

    @Test
    @Transactional
    void partialUpdateAuditinfoWithPatch() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        int databaseSizeBeforeUpdate = auditinfoRepository.findAll().size();

        // Update the auditinfo using partial update
        Auditinfo partialUpdatedAuditinfo = new Auditinfo();
        partialUpdatedAuditinfo.setId(auditinfo.getId());

        partialUpdatedAuditinfo.empn(UPDATED_EMPN);

        restAuditinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditinfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuditinfo))
            )
            .andExpect(status().isOk());

        // Validate the Auditinfo in the database
        List<Auditinfo> auditinfoList = auditinfoRepository.findAll();
        assertThat(auditinfoList).hasSize(databaseSizeBeforeUpdate);
        Auditinfo testAuditinfo = auditinfoList.get(auditinfoList.size() - 1);
        assertThat(testAuditinfo.getAuditdate()).isEqualTo(DEFAULT_AUDITDATE);
        assertThat(testAuditinfo.getAudittime()).isEqualTo(DEFAULT_AUDITTIME);
        assertThat(testAuditinfo.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testAuditinfo.getAidentify()).isEqualTo(DEFAULT_AIDENTIFY);
    }

    @Test
    @Transactional
    void fullUpdateAuditinfoWithPatch() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        int databaseSizeBeforeUpdate = auditinfoRepository.findAll().size();

        // Update the auditinfo using partial update
        Auditinfo partialUpdatedAuditinfo = new Auditinfo();
        partialUpdatedAuditinfo.setId(auditinfo.getId());

        partialUpdatedAuditinfo.auditdate(UPDATED_AUDITDATE).audittime(UPDATED_AUDITTIME).empn(UPDATED_EMPN).aidentify(UPDATED_AIDENTIFY);

        restAuditinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuditinfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuditinfo))
            )
            .andExpect(status().isOk());

        // Validate the Auditinfo in the database
        List<Auditinfo> auditinfoList = auditinfoRepository.findAll();
        assertThat(auditinfoList).hasSize(databaseSizeBeforeUpdate);
        Auditinfo testAuditinfo = auditinfoList.get(auditinfoList.size() - 1);
        assertThat(testAuditinfo.getAuditdate()).isEqualTo(UPDATED_AUDITDATE);
        assertThat(testAuditinfo.getAudittime()).isEqualTo(UPDATED_AUDITTIME);
        assertThat(testAuditinfo.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testAuditinfo.getAidentify()).isEqualTo(UPDATED_AIDENTIFY);
    }

    @Test
    @Transactional
    void patchNonExistingAuditinfo() throws Exception {
        int databaseSizeBeforeUpdate = auditinfoRepository.findAll().size();
        auditinfo.setId(count.incrementAndGet());

        // Create the Auditinfo
        AuditinfoDTO auditinfoDTO = auditinfoMapper.toDto(auditinfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuditinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, auditinfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auditinfo in the database
        List<Auditinfo> auditinfoList = auditinfoRepository.findAll();
        assertThat(auditinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Auditinfo in Elasticsearch
        verify(mockAuditinfoSearchRepository, times(0)).save(auditinfo);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuditinfo() throws Exception {
        int databaseSizeBeforeUpdate = auditinfoRepository.findAll().size();
        auditinfo.setId(count.incrementAndGet());

        // Create the Auditinfo
        AuditinfoDTO auditinfoDTO = auditinfoMapper.toDto(auditinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(auditinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Auditinfo in the database
        List<Auditinfo> auditinfoList = auditinfoRepository.findAll();
        assertThat(auditinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Auditinfo in Elasticsearch
        verify(mockAuditinfoSearchRepository, times(0)).save(auditinfo);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuditinfo() throws Exception {
        int databaseSizeBeforeUpdate = auditinfoRepository.findAll().size();
        auditinfo.setId(count.incrementAndGet());

        // Create the Auditinfo
        AuditinfoDTO auditinfoDTO = auditinfoMapper.toDto(auditinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuditinfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(auditinfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Auditinfo in the database
        List<Auditinfo> auditinfoList = auditinfoRepository.findAll();
        assertThat(auditinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Auditinfo in Elasticsearch
        verify(mockAuditinfoSearchRepository, times(0)).save(auditinfo);
    }

    @Test
    @Transactional
    void deleteAuditinfo() throws Exception {
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);

        int databaseSizeBeforeDelete = auditinfoRepository.findAll().size();

        // Delete the auditinfo
        restAuditinfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, auditinfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Auditinfo> auditinfoList = auditinfoRepository.findAll();
        assertThat(auditinfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Auditinfo in Elasticsearch
        verify(mockAuditinfoSearchRepository, times(1)).deleteById(auditinfo.getId());
    }

    @Test
    @Transactional
    void searchAuditinfo() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        auditinfoRepository.saveAndFlush(auditinfo);
        when(mockAuditinfoSearchRepository.search(queryStringQuery("id:" + auditinfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(auditinfo), PageRequest.of(0, 1), 1));

        // Search the auditinfo
        restAuditinfoMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + auditinfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auditinfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].auditdate").value(hasItem(DEFAULT_AUDITDATE.toString())))
            .andExpect(jsonPath("$.[*].audittime").value(hasItem(DEFAULT_AUDITTIME.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].aidentify").value(hasItem(DEFAULT_AIDENTIFY)));
    }
}
