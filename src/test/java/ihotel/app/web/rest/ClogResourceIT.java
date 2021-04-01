package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Clog;
import ihotel.app.repository.ClogRepository;
import ihotel.app.repository.search.ClogSearchRepository;
import ihotel.app.service.criteria.ClogCriteria;
import ihotel.app.service.dto.ClogDTO;
import ihotel.app.service.mapper.ClogMapper;
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
 * Integration tests for the {@link ClogResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClogResourceIT {

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final Instant DEFAULT_BEGINDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BEGINDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DQRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DQRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/clogs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/clogs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClogRepository clogRepository;

    @Autowired
    private ClogMapper clogMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.ClogSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClogSearchRepository mockClogSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClogMockMvc;

    private Clog clog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clog createEntity(EntityManager em) {
        Clog clog = new Clog().empn(DEFAULT_EMPN).begindate(DEFAULT_BEGINDATE).enddate(DEFAULT_ENDDATE).dqrq(DEFAULT_DQRQ);
        return clog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clog createUpdatedEntity(EntityManager em) {
        Clog clog = new Clog().empn(UPDATED_EMPN).begindate(UPDATED_BEGINDATE).enddate(UPDATED_ENDDATE).dqrq(UPDATED_DQRQ);
        return clog;
    }

    @BeforeEach
    public void initTest() {
        clog = createEntity(em);
    }

    @Test
    @Transactional
    void createClog() throws Exception {
        int databaseSizeBeforeCreate = clogRepository.findAll().size();
        // Create the Clog
        ClogDTO clogDTO = clogMapper.toDto(clog);
        restClogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clogDTO)))
            .andExpect(status().isCreated());

        // Validate the Clog in the database
        List<Clog> clogList = clogRepository.findAll();
        assertThat(clogList).hasSize(databaseSizeBeforeCreate + 1);
        Clog testClog = clogList.get(clogList.size() - 1);
        assertThat(testClog.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testClog.getBegindate()).isEqualTo(DEFAULT_BEGINDATE);
        assertThat(testClog.getEnddate()).isEqualTo(DEFAULT_ENDDATE);
        assertThat(testClog.getDqrq()).isEqualTo(DEFAULT_DQRQ);

        // Validate the Clog in Elasticsearch
        verify(mockClogSearchRepository, times(1)).save(testClog);
    }

    @Test
    @Transactional
    void createClogWithExistingId() throws Exception {
        // Create the Clog with an existing ID
        clog.setId(1L);
        ClogDTO clogDTO = clogMapper.toDto(clog);

        int databaseSizeBeforeCreate = clogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clog in the database
        List<Clog> clogList = clogRepository.findAll();
        assertThat(clogList).hasSize(databaseSizeBeforeCreate);

        // Validate the Clog in Elasticsearch
        verify(mockClogSearchRepository, times(0)).save(clog);
    }

    @Test
    @Transactional
    void getAllClogs() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList
        restClogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clog.getId().intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].begindate").value(hasItem(DEFAULT_BEGINDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].dqrq").value(hasItem(DEFAULT_DQRQ.toString())));
    }

    @Test
    @Transactional
    void getClog() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get the clog
        restClogMockMvc
            .perform(get(ENTITY_API_URL_ID, clog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clog.getId().intValue()))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.begindate").value(DEFAULT_BEGINDATE.toString()))
            .andExpect(jsonPath("$.enddate").value(DEFAULT_ENDDATE.toString()))
            .andExpect(jsonPath("$.dqrq").value(DEFAULT_DQRQ.toString()));
    }

    @Test
    @Transactional
    void getClogsByIdFiltering() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        Long id = clog.getId();

        defaultClogShouldBeFound("id.equals=" + id);
        defaultClogShouldNotBeFound("id.notEquals=" + id);

        defaultClogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClogShouldNotBeFound("id.greaterThan=" + id);

        defaultClogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClogShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClogsByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where empn equals to DEFAULT_EMPN
        defaultClogShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the clogList where empn equals to UPDATED_EMPN
        defaultClogShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClogsByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where empn not equals to DEFAULT_EMPN
        defaultClogShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the clogList where empn not equals to UPDATED_EMPN
        defaultClogShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClogsByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultClogShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the clogList where empn equals to UPDATED_EMPN
        defaultClogShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClogsByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where empn is not null
        defaultClogShouldBeFound("empn.specified=true");

        // Get all the clogList where empn is null
        defaultClogShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllClogsByEmpnContainsSomething() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where empn contains DEFAULT_EMPN
        defaultClogShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the clogList where empn contains UPDATED_EMPN
        defaultClogShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClogsByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where empn does not contain DEFAULT_EMPN
        defaultClogShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the clogList where empn does not contain UPDATED_EMPN
        defaultClogShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClogsByBegindateIsEqualToSomething() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where begindate equals to DEFAULT_BEGINDATE
        defaultClogShouldBeFound("begindate.equals=" + DEFAULT_BEGINDATE);

        // Get all the clogList where begindate equals to UPDATED_BEGINDATE
        defaultClogShouldNotBeFound("begindate.equals=" + UPDATED_BEGINDATE);
    }

    @Test
    @Transactional
    void getAllClogsByBegindateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where begindate not equals to DEFAULT_BEGINDATE
        defaultClogShouldNotBeFound("begindate.notEquals=" + DEFAULT_BEGINDATE);

        // Get all the clogList where begindate not equals to UPDATED_BEGINDATE
        defaultClogShouldBeFound("begindate.notEquals=" + UPDATED_BEGINDATE);
    }

    @Test
    @Transactional
    void getAllClogsByBegindateIsInShouldWork() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where begindate in DEFAULT_BEGINDATE or UPDATED_BEGINDATE
        defaultClogShouldBeFound("begindate.in=" + DEFAULT_BEGINDATE + "," + UPDATED_BEGINDATE);

        // Get all the clogList where begindate equals to UPDATED_BEGINDATE
        defaultClogShouldNotBeFound("begindate.in=" + UPDATED_BEGINDATE);
    }

    @Test
    @Transactional
    void getAllClogsByBegindateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where begindate is not null
        defaultClogShouldBeFound("begindate.specified=true");

        // Get all the clogList where begindate is null
        defaultClogShouldNotBeFound("begindate.specified=false");
    }

    @Test
    @Transactional
    void getAllClogsByEnddateIsEqualToSomething() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where enddate equals to DEFAULT_ENDDATE
        defaultClogShouldBeFound("enddate.equals=" + DEFAULT_ENDDATE);

        // Get all the clogList where enddate equals to UPDATED_ENDDATE
        defaultClogShouldNotBeFound("enddate.equals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllClogsByEnddateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where enddate not equals to DEFAULT_ENDDATE
        defaultClogShouldNotBeFound("enddate.notEquals=" + DEFAULT_ENDDATE);

        // Get all the clogList where enddate not equals to UPDATED_ENDDATE
        defaultClogShouldBeFound("enddate.notEquals=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllClogsByEnddateIsInShouldWork() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where enddate in DEFAULT_ENDDATE or UPDATED_ENDDATE
        defaultClogShouldBeFound("enddate.in=" + DEFAULT_ENDDATE + "," + UPDATED_ENDDATE);

        // Get all the clogList where enddate equals to UPDATED_ENDDATE
        defaultClogShouldNotBeFound("enddate.in=" + UPDATED_ENDDATE);
    }

    @Test
    @Transactional
    void getAllClogsByEnddateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where enddate is not null
        defaultClogShouldBeFound("enddate.specified=true");

        // Get all the clogList where enddate is null
        defaultClogShouldNotBeFound("enddate.specified=false");
    }

    @Test
    @Transactional
    void getAllClogsByDqrqIsEqualToSomething() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where dqrq equals to DEFAULT_DQRQ
        defaultClogShouldBeFound("dqrq.equals=" + DEFAULT_DQRQ);

        // Get all the clogList where dqrq equals to UPDATED_DQRQ
        defaultClogShouldNotBeFound("dqrq.equals=" + UPDATED_DQRQ);
    }

    @Test
    @Transactional
    void getAllClogsByDqrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where dqrq not equals to DEFAULT_DQRQ
        defaultClogShouldNotBeFound("dqrq.notEquals=" + DEFAULT_DQRQ);

        // Get all the clogList where dqrq not equals to UPDATED_DQRQ
        defaultClogShouldBeFound("dqrq.notEquals=" + UPDATED_DQRQ);
    }

    @Test
    @Transactional
    void getAllClogsByDqrqIsInShouldWork() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where dqrq in DEFAULT_DQRQ or UPDATED_DQRQ
        defaultClogShouldBeFound("dqrq.in=" + DEFAULT_DQRQ + "," + UPDATED_DQRQ);

        // Get all the clogList where dqrq equals to UPDATED_DQRQ
        defaultClogShouldNotBeFound("dqrq.in=" + UPDATED_DQRQ);
    }

    @Test
    @Transactional
    void getAllClogsByDqrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        // Get all the clogList where dqrq is not null
        defaultClogShouldBeFound("dqrq.specified=true");

        // Get all the clogList where dqrq is null
        defaultClogShouldNotBeFound("dqrq.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClogShouldBeFound(String filter) throws Exception {
        restClogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clog.getId().intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].begindate").value(hasItem(DEFAULT_BEGINDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].dqrq").value(hasItem(DEFAULT_DQRQ.toString())));

        // Check, that the count call also returns 1
        restClogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClogShouldNotBeFound(String filter) throws Exception {
        restClogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClog() throws Exception {
        // Get the clog
        restClogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClog() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        int databaseSizeBeforeUpdate = clogRepository.findAll().size();

        // Update the clog
        Clog updatedClog = clogRepository.findById(clog.getId()).get();
        // Disconnect from session so that the updates on updatedClog are not directly saved in db
        em.detach(updatedClog);
        updatedClog.empn(UPDATED_EMPN).begindate(UPDATED_BEGINDATE).enddate(UPDATED_ENDDATE).dqrq(UPDATED_DQRQ);
        ClogDTO clogDTO = clogMapper.toDto(updatedClog);

        restClogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clogDTO))
            )
            .andExpect(status().isOk());

        // Validate the Clog in the database
        List<Clog> clogList = clogRepository.findAll();
        assertThat(clogList).hasSize(databaseSizeBeforeUpdate);
        Clog testClog = clogList.get(clogList.size() - 1);
        assertThat(testClog.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testClog.getBegindate()).isEqualTo(UPDATED_BEGINDATE);
        assertThat(testClog.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testClog.getDqrq()).isEqualTo(UPDATED_DQRQ);

        // Validate the Clog in Elasticsearch
        verify(mockClogSearchRepository).save(testClog);
    }

    @Test
    @Transactional
    void putNonExistingClog() throws Exception {
        int databaseSizeBeforeUpdate = clogRepository.findAll().size();
        clog.setId(count.incrementAndGet());

        // Create the Clog
        ClogDTO clogDTO = clogMapper.toDto(clog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clog in the database
        List<Clog> clogList = clogRepository.findAll();
        assertThat(clogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Clog in Elasticsearch
        verify(mockClogSearchRepository, times(0)).save(clog);
    }

    @Test
    @Transactional
    void putWithIdMismatchClog() throws Exception {
        int databaseSizeBeforeUpdate = clogRepository.findAll().size();
        clog.setId(count.incrementAndGet());

        // Create the Clog
        ClogDTO clogDTO = clogMapper.toDto(clog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clog in the database
        List<Clog> clogList = clogRepository.findAll();
        assertThat(clogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Clog in Elasticsearch
        verify(mockClogSearchRepository, times(0)).save(clog);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClog() throws Exception {
        int databaseSizeBeforeUpdate = clogRepository.findAll().size();
        clog.setId(count.incrementAndGet());

        // Create the Clog
        ClogDTO clogDTO = clogMapper.toDto(clog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clog in the database
        List<Clog> clogList = clogRepository.findAll();
        assertThat(clogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Clog in Elasticsearch
        verify(mockClogSearchRepository, times(0)).save(clog);
    }

    @Test
    @Transactional
    void partialUpdateClogWithPatch() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        int databaseSizeBeforeUpdate = clogRepository.findAll().size();

        // Update the clog using partial update
        Clog partialUpdatedClog = new Clog();
        partialUpdatedClog.setId(clog.getId());

        partialUpdatedClog.empn(UPDATED_EMPN).enddate(UPDATED_ENDDATE).dqrq(UPDATED_DQRQ);

        restClogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClog))
            )
            .andExpect(status().isOk());

        // Validate the Clog in the database
        List<Clog> clogList = clogRepository.findAll();
        assertThat(clogList).hasSize(databaseSizeBeforeUpdate);
        Clog testClog = clogList.get(clogList.size() - 1);
        assertThat(testClog.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testClog.getBegindate()).isEqualTo(DEFAULT_BEGINDATE);
        assertThat(testClog.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testClog.getDqrq()).isEqualTo(UPDATED_DQRQ);
    }

    @Test
    @Transactional
    void fullUpdateClogWithPatch() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        int databaseSizeBeforeUpdate = clogRepository.findAll().size();

        // Update the clog using partial update
        Clog partialUpdatedClog = new Clog();
        partialUpdatedClog.setId(clog.getId());

        partialUpdatedClog.empn(UPDATED_EMPN).begindate(UPDATED_BEGINDATE).enddate(UPDATED_ENDDATE).dqrq(UPDATED_DQRQ);

        restClogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClog))
            )
            .andExpect(status().isOk());

        // Validate the Clog in the database
        List<Clog> clogList = clogRepository.findAll();
        assertThat(clogList).hasSize(databaseSizeBeforeUpdate);
        Clog testClog = clogList.get(clogList.size() - 1);
        assertThat(testClog.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testClog.getBegindate()).isEqualTo(UPDATED_BEGINDATE);
        assertThat(testClog.getEnddate()).isEqualTo(UPDATED_ENDDATE);
        assertThat(testClog.getDqrq()).isEqualTo(UPDATED_DQRQ);
    }

    @Test
    @Transactional
    void patchNonExistingClog() throws Exception {
        int databaseSizeBeforeUpdate = clogRepository.findAll().size();
        clog.setId(count.incrementAndGet());

        // Create the Clog
        ClogDTO clogDTO = clogMapper.toDto(clog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clog in the database
        List<Clog> clogList = clogRepository.findAll();
        assertThat(clogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Clog in Elasticsearch
        verify(mockClogSearchRepository, times(0)).save(clog);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClog() throws Exception {
        int databaseSizeBeforeUpdate = clogRepository.findAll().size();
        clog.setId(count.incrementAndGet());

        // Create the Clog
        ClogDTO clogDTO = clogMapper.toDto(clog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clog in the database
        List<Clog> clogList = clogRepository.findAll();
        assertThat(clogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Clog in Elasticsearch
        verify(mockClogSearchRepository, times(0)).save(clog);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClog() throws Exception {
        int databaseSizeBeforeUpdate = clogRepository.findAll().size();
        clog.setId(count.incrementAndGet());

        // Create the Clog
        ClogDTO clogDTO = clogMapper.toDto(clog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClogMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clog in the database
        List<Clog> clogList = clogRepository.findAll();
        assertThat(clogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Clog in Elasticsearch
        verify(mockClogSearchRepository, times(0)).save(clog);
    }

    @Test
    @Transactional
    void deleteClog() throws Exception {
        // Initialize the database
        clogRepository.saveAndFlush(clog);

        int databaseSizeBeforeDelete = clogRepository.findAll().size();

        // Delete the clog
        restClogMockMvc
            .perform(delete(ENTITY_API_URL_ID, clog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Clog> clogList = clogRepository.findAll();
        assertThat(clogList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Clog in Elasticsearch
        verify(mockClogSearchRepository, times(1)).deleteById(clog.getId());
    }

    @Test
    @Transactional
    void searchClog() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        clogRepository.saveAndFlush(clog);
        when(mockClogSearchRepository.search(queryStringQuery("id:" + clog.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(clog), PageRequest.of(0, 1), 1));

        // Search the clog
        restClogMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + clog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clog.getId().intValue())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].begindate").value(hasItem(DEFAULT_BEGINDATE.toString())))
            .andExpect(jsonPath("$.[*].enddate").value(hasItem(DEFAULT_ENDDATE.toString())))
            .andExpect(jsonPath("$.[*].dqrq").value(hasItem(DEFAULT_DQRQ.toString())));
    }
}
