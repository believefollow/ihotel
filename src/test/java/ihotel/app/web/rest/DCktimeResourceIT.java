package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DCktime;
import ihotel.app.repository.DCktimeRepository;
import ihotel.app.repository.search.DCktimeSearchRepository;
import ihotel.app.service.criteria.DCktimeCriteria;
import ihotel.app.service.dto.DCktimeDTO;
import ihotel.app.service.mapper.DCktimeMapper;
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
 * Integration tests for the {@link DCktimeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DCktimeResourceIT {

    private static final Instant DEFAULT_BEGINTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BEGINTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_ENDTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENDTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DEPOT = "AAAAAAAAAA";
    private static final String UPDATED_DEPOT = "BBBBBBBBBB";

    private static final String DEFAULT_CKBILLNO = "AAAAAAAAAA";
    private static final String UPDATED_CKBILLNO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/d-cktimes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-cktimes";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DCktimeRepository dCktimeRepository;

    @Autowired
    private DCktimeMapper dCktimeMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DCktimeSearchRepositoryMockConfiguration
     */
    @Autowired
    private DCktimeSearchRepository mockDCktimeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDCktimeMockMvc;

    private DCktime dCktime;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DCktime createEntity(EntityManager em) {
        DCktime dCktime = new DCktime()
            .begintime(DEFAULT_BEGINTIME)
            .endtime(DEFAULT_ENDTIME)
            .depot(DEFAULT_DEPOT)
            .ckbillno(DEFAULT_CKBILLNO);
        return dCktime;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DCktime createUpdatedEntity(EntityManager em) {
        DCktime dCktime = new DCktime()
            .begintime(UPDATED_BEGINTIME)
            .endtime(UPDATED_ENDTIME)
            .depot(UPDATED_DEPOT)
            .ckbillno(UPDATED_CKBILLNO);
        return dCktime;
    }

    @BeforeEach
    public void initTest() {
        dCktime = createEntity(em);
    }

    @Test
    @Transactional
    void createDCktime() throws Exception {
        int databaseSizeBeforeCreate = dCktimeRepository.findAll().size();
        // Create the DCktime
        DCktimeDTO dCktimeDTO = dCktimeMapper.toDto(dCktime);
        restDCktimeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCktimeDTO)))
            .andExpect(status().isCreated());

        // Validate the DCktime in the database
        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeCreate + 1);
        DCktime testDCktime = dCktimeList.get(dCktimeList.size() - 1);
        assertThat(testDCktime.getBegintime()).isEqualTo(DEFAULT_BEGINTIME);
        assertThat(testDCktime.getEndtime()).isEqualTo(DEFAULT_ENDTIME);
        assertThat(testDCktime.getDepot()).isEqualTo(DEFAULT_DEPOT);
        assertThat(testDCktime.getCkbillno()).isEqualTo(DEFAULT_CKBILLNO);

        // Validate the DCktime in Elasticsearch
        verify(mockDCktimeSearchRepository, times(1)).save(testDCktime);
    }

    @Test
    @Transactional
    void createDCktimeWithExistingId() throws Exception {
        // Create the DCktime with an existing ID
        dCktime.setId(1L);
        DCktimeDTO dCktimeDTO = dCktimeMapper.toDto(dCktime);

        int databaseSizeBeforeCreate = dCktimeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDCktimeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCktimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DCktime in the database
        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeCreate);

        // Validate the DCktime in Elasticsearch
        verify(mockDCktimeSearchRepository, times(0)).save(dCktime);
    }

    @Test
    @Transactional
    void checkBegintimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dCktimeRepository.findAll().size();
        // set the field null
        dCktime.setBegintime(null);

        // Create the DCktime, which fails.
        DCktimeDTO dCktimeDTO = dCktimeMapper.toDto(dCktime);

        restDCktimeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCktimeDTO)))
            .andExpect(status().isBadRequest());

        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEndtimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dCktimeRepository.findAll().size();
        // set the field null
        dCktime.setEndtime(null);

        // Create the DCktime, which fails.
        DCktimeDTO dCktimeDTO = dCktimeMapper.toDto(dCktime);

        restDCktimeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCktimeDTO)))
            .andExpect(status().isBadRequest());

        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepotIsRequired() throws Exception {
        int databaseSizeBeforeTest = dCktimeRepository.findAll().size();
        // set the field null
        dCktime.setDepot(null);

        // Create the DCktime, which fails.
        DCktimeDTO dCktimeDTO = dCktimeMapper.toDto(dCktime);

        restDCktimeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCktimeDTO)))
            .andExpect(status().isBadRequest());

        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDCktimes() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList
        restDCktimeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dCktime.getId().intValue())))
            .andExpect(jsonPath("$.[*].begintime").value(hasItem(DEFAULT_BEGINTIME.toString())))
            .andExpect(jsonPath("$.[*].endtime").value(hasItem(DEFAULT_ENDTIME.toString())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].ckbillno").value(hasItem(DEFAULT_CKBILLNO)));
    }

    @Test
    @Transactional
    void getDCktime() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get the dCktime
        restDCktimeMockMvc
            .perform(get(ENTITY_API_URL_ID, dCktime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dCktime.getId().intValue()))
            .andExpect(jsonPath("$.begintime").value(DEFAULT_BEGINTIME.toString()))
            .andExpect(jsonPath("$.endtime").value(DEFAULT_ENDTIME.toString()))
            .andExpect(jsonPath("$.depot").value(DEFAULT_DEPOT))
            .andExpect(jsonPath("$.ckbillno").value(DEFAULT_CKBILLNO));
    }

    @Test
    @Transactional
    void getDCktimesByIdFiltering() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        Long id = dCktime.getId();

        defaultDCktimeShouldBeFound("id.equals=" + id);
        defaultDCktimeShouldNotBeFound("id.notEquals=" + id);

        defaultDCktimeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDCktimeShouldNotBeFound("id.greaterThan=" + id);

        defaultDCktimeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDCktimeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDCktimesByBegintimeIsEqualToSomething() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where begintime equals to DEFAULT_BEGINTIME
        defaultDCktimeShouldBeFound("begintime.equals=" + DEFAULT_BEGINTIME);

        // Get all the dCktimeList where begintime equals to UPDATED_BEGINTIME
        defaultDCktimeShouldNotBeFound("begintime.equals=" + UPDATED_BEGINTIME);
    }

    @Test
    @Transactional
    void getAllDCktimesByBegintimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where begintime not equals to DEFAULT_BEGINTIME
        defaultDCktimeShouldNotBeFound("begintime.notEquals=" + DEFAULT_BEGINTIME);

        // Get all the dCktimeList where begintime not equals to UPDATED_BEGINTIME
        defaultDCktimeShouldBeFound("begintime.notEquals=" + UPDATED_BEGINTIME);
    }

    @Test
    @Transactional
    void getAllDCktimesByBegintimeIsInShouldWork() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where begintime in DEFAULT_BEGINTIME or UPDATED_BEGINTIME
        defaultDCktimeShouldBeFound("begintime.in=" + DEFAULT_BEGINTIME + "," + UPDATED_BEGINTIME);

        // Get all the dCktimeList where begintime equals to UPDATED_BEGINTIME
        defaultDCktimeShouldNotBeFound("begintime.in=" + UPDATED_BEGINTIME);
    }

    @Test
    @Transactional
    void getAllDCktimesByBegintimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where begintime is not null
        defaultDCktimeShouldBeFound("begintime.specified=true");

        // Get all the dCktimeList where begintime is null
        defaultDCktimeShouldNotBeFound("begintime.specified=false");
    }

    @Test
    @Transactional
    void getAllDCktimesByEndtimeIsEqualToSomething() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where endtime equals to DEFAULT_ENDTIME
        defaultDCktimeShouldBeFound("endtime.equals=" + DEFAULT_ENDTIME);

        // Get all the dCktimeList where endtime equals to UPDATED_ENDTIME
        defaultDCktimeShouldNotBeFound("endtime.equals=" + UPDATED_ENDTIME);
    }

    @Test
    @Transactional
    void getAllDCktimesByEndtimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where endtime not equals to DEFAULT_ENDTIME
        defaultDCktimeShouldNotBeFound("endtime.notEquals=" + DEFAULT_ENDTIME);

        // Get all the dCktimeList where endtime not equals to UPDATED_ENDTIME
        defaultDCktimeShouldBeFound("endtime.notEquals=" + UPDATED_ENDTIME);
    }

    @Test
    @Transactional
    void getAllDCktimesByEndtimeIsInShouldWork() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where endtime in DEFAULT_ENDTIME or UPDATED_ENDTIME
        defaultDCktimeShouldBeFound("endtime.in=" + DEFAULT_ENDTIME + "," + UPDATED_ENDTIME);

        // Get all the dCktimeList where endtime equals to UPDATED_ENDTIME
        defaultDCktimeShouldNotBeFound("endtime.in=" + UPDATED_ENDTIME);
    }

    @Test
    @Transactional
    void getAllDCktimesByEndtimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where endtime is not null
        defaultDCktimeShouldBeFound("endtime.specified=true");

        // Get all the dCktimeList where endtime is null
        defaultDCktimeShouldNotBeFound("endtime.specified=false");
    }

    @Test
    @Transactional
    void getAllDCktimesByDepotIsEqualToSomething() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where depot equals to DEFAULT_DEPOT
        defaultDCktimeShouldBeFound("depot.equals=" + DEFAULT_DEPOT);

        // Get all the dCktimeList where depot equals to UPDATED_DEPOT
        defaultDCktimeShouldNotBeFound("depot.equals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDCktimesByDepotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where depot not equals to DEFAULT_DEPOT
        defaultDCktimeShouldNotBeFound("depot.notEquals=" + DEFAULT_DEPOT);

        // Get all the dCktimeList where depot not equals to UPDATED_DEPOT
        defaultDCktimeShouldBeFound("depot.notEquals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDCktimesByDepotIsInShouldWork() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where depot in DEFAULT_DEPOT or UPDATED_DEPOT
        defaultDCktimeShouldBeFound("depot.in=" + DEFAULT_DEPOT + "," + UPDATED_DEPOT);

        // Get all the dCktimeList where depot equals to UPDATED_DEPOT
        defaultDCktimeShouldNotBeFound("depot.in=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDCktimesByDepotIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where depot is not null
        defaultDCktimeShouldBeFound("depot.specified=true");

        // Get all the dCktimeList where depot is null
        defaultDCktimeShouldNotBeFound("depot.specified=false");
    }

    @Test
    @Transactional
    void getAllDCktimesByDepotContainsSomething() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where depot contains DEFAULT_DEPOT
        defaultDCktimeShouldBeFound("depot.contains=" + DEFAULT_DEPOT);

        // Get all the dCktimeList where depot contains UPDATED_DEPOT
        defaultDCktimeShouldNotBeFound("depot.contains=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDCktimesByDepotNotContainsSomething() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where depot does not contain DEFAULT_DEPOT
        defaultDCktimeShouldNotBeFound("depot.doesNotContain=" + DEFAULT_DEPOT);

        // Get all the dCktimeList where depot does not contain UPDATED_DEPOT
        defaultDCktimeShouldBeFound("depot.doesNotContain=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDCktimesByCkbillnoIsEqualToSomething() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where ckbillno equals to DEFAULT_CKBILLNO
        defaultDCktimeShouldBeFound("ckbillno.equals=" + DEFAULT_CKBILLNO);

        // Get all the dCktimeList where ckbillno equals to UPDATED_CKBILLNO
        defaultDCktimeShouldNotBeFound("ckbillno.equals=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDCktimesByCkbillnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where ckbillno not equals to DEFAULT_CKBILLNO
        defaultDCktimeShouldNotBeFound("ckbillno.notEquals=" + DEFAULT_CKBILLNO);

        // Get all the dCktimeList where ckbillno not equals to UPDATED_CKBILLNO
        defaultDCktimeShouldBeFound("ckbillno.notEquals=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDCktimesByCkbillnoIsInShouldWork() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where ckbillno in DEFAULT_CKBILLNO or UPDATED_CKBILLNO
        defaultDCktimeShouldBeFound("ckbillno.in=" + DEFAULT_CKBILLNO + "," + UPDATED_CKBILLNO);

        // Get all the dCktimeList where ckbillno equals to UPDATED_CKBILLNO
        defaultDCktimeShouldNotBeFound("ckbillno.in=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDCktimesByCkbillnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where ckbillno is not null
        defaultDCktimeShouldBeFound("ckbillno.specified=true");

        // Get all the dCktimeList where ckbillno is null
        defaultDCktimeShouldNotBeFound("ckbillno.specified=false");
    }

    @Test
    @Transactional
    void getAllDCktimesByCkbillnoContainsSomething() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where ckbillno contains DEFAULT_CKBILLNO
        defaultDCktimeShouldBeFound("ckbillno.contains=" + DEFAULT_CKBILLNO);

        // Get all the dCktimeList where ckbillno contains UPDATED_CKBILLNO
        defaultDCktimeShouldNotBeFound("ckbillno.contains=" + UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void getAllDCktimesByCkbillnoNotContainsSomething() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        // Get all the dCktimeList where ckbillno does not contain DEFAULT_CKBILLNO
        defaultDCktimeShouldNotBeFound("ckbillno.doesNotContain=" + DEFAULT_CKBILLNO);

        // Get all the dCktimeList where ckbillno does not contain UPDATED_CKBILLNO
        defaultDCktimeShouldBeFound("ckbillno.doesNotContain=" + UPDATED_CKBILLNO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDCktimeShouldBeFound(String filter) throws Exception {
        restDCktimeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dCktime.getId().intValue())))
            .andExpect(jsonPath("$.[*].begintime").value(hasItem(DEFAULT_BEGINTIME.toString())))
            .andExpect(jsonPath("$.[*].endtime").value(hasItem(DEFAULT_ENDTIME.toString())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].ckbillno").value(hasItem(DEFAULT_CKBILLNO)));

        // Check, that the count call also returns 1
        restDCktimeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDCktimeShouldNotBeFound(String filter) throws Exception {
        restDCktimeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDCktimeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDCktime() throws Exception {
        // Get the dCktime
        restDCktimeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDCktime() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        int databaseSizeBeforeUpdate = dCktimeRepository.findAll().size();

        // Update the dCktime
        DCktime updatedDCktime = dCktimeRepository.findById(dCktime.getId()).get();
        // Disconnect from session so that the updates on updatedDCktime are not directly saved in db
        em.detach(updatedDCktime);
        updatedDCktime.begintime(UPDATED_BEGINTIME).endtime(UPDATED_ENDTIME).depot(UPDATED_DEPOT).ckbillno(UPDATED_CKBILLNO);
        DCktimeDTO dCktimeDTO = dCktimeMapper.toDto(updatedDCktime);

        restDCktimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dCktimeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dCktimeDTO))
            )
            .andExpect(status().isOk());

        // Validate the DCktime in the database
        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeUpdate);
        DCktime testDCktime = dCktimeList.get(dCktimeList.size() - 1);
        assertThat(testDCktime.getBegintime()).isEqualTo(UPDATED_BEGINTIME);
        assertThat(testDCktime.getEndtime()).isEqualTo(UPDATED_ENDTIME);
        assertThat(testDCktime.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testDCktime.getCkbillno()).isEqualTo(UPDATED_CKBILLNO);

        // Validate the DCktime in Elasticsearch
        verify(mockDCktimeSearchRepository).save(testDCktime);
    }

    @Test
    @Transactional
    void putNonExistingDCktime() throws Exception {
        int databaseSizeBeforeUpdate = dCktimeRepository.findAll().size();
        dCktime.setId(count.incrementAndGet());

        // Create the DCktime
        DCktimeDTO dCktimeDTO = dCktimeMapper.toDto(dCktime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDCktimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dCktimeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dCktimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCktime in the database
        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCktime in Elasticsearch
        verify(mockDCktimeSearchRepository, times(0)).save(dCktime);
    }

    @Test
    @Transactional
    void putWithIdMismatchDCktime() throws Exception {
        int databaseSizeBeforeUpdate = dCktimeRepository.findAll().size();
        dCktime.setId(count.incrementAndGet());

        // Create the DCktime
        DCktimeDTO dCktimeDTO = dCktimeMapper.toDto(dCktime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCktimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dCktimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCktime in the database
        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCktime in Elasticsearch
        verify(mockDCktimeSearchRepository, times(0)).save(dCktime);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDCktime() throws Exception {
        int databaseSizeBeforeUpdate = dCktimeRepository.findAll().size();
        dCktime.setId(count.incrementAndGet());

        // Create the DCktime
        DCktimeDTO dCktimeDTO = dCktimeMapper.toDto(dCktime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCktimeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCktimeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DCktime in the database
        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCktime in Elasticsearch
        verify(mockDCktimeSearchRepository, times(0)).save(dCktime);
    }

    @Test
    @Transactional
    void partialUpdateDCktimeWithPatch() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        int databaseSizeBeforeUpdate = dCktimeRepository.findAll().size();

        // Update the dCktime using partial update
        DCktime partialUpdatedDCktime = new DCktime();
        partialUpdatedDCktime.setId(dCktime.getId());

        partialUpdatedDCktime.begintime(UPDATED_BEGINTIME).ckbillno(UPDATED_CKBILLNO);

        restDCktimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDCktime.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDCktime))
            )
            .andExpect(status().isOk());

        // Validate the DCktime in the database
        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeUpdate);
        DCktime testDCktime = dCktimeList.get(dCktimeList.size() - 1);
        assertThat(testDCktime.getBegintime()).isEqualTo(UPDATED_BEGINTIME);
        assertThat(testDCktime.getEndtime()).isEqualTo(DEFAULT_ENDTIME);
        assertThat(testDCktime.getDepot()).isEqualTo(DEFAULT_DEPOT);
        assertThat(testDCktime.getCkbillno()).isEqualTo(UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void fullUpdateDCktimeWithPatch() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        int databaseSizeBeforeUpdate = dCktimeRepository.findAll().size();

        // Update the dCktime using partial update
        DCktime partialUpdatedDCktime = new DCktime();
        partialUpdatedDCktime.setId(dCktime.getId());

        partialUpdatedDCktime.begintime(UPDATED_BEGINTIME).endtime(UPDATED_ENDTIME).depot(UPDATED_DEPOT).ckbillno(UPDATED_CKBILLNO);

        restDCktimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDCktime.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDCktime))
            )
            .andExpect(status().isOk());

        // Validate the DCktime in the database
        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeUpdate);
        DCktime testDCktime = dCktimeList.get(dCktimeList.size() - 1);
        assertThat(testDCktime.getBegintime()).isEqualTo(UPDATED_BEGINTIME);
        assertThat(testDCktime.getEndtime()).isEqualTo(UPDATED_ENDTIME);
        assertThat(testDCktime.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testDCktime.getCkbillno()).isEqualTo(UPDATED_CKBILLNO);
    }

    @Test
    @Transactional
    void patchNonExistingDCktime() throws Exception {
        int databaseSizeBeforeUpdate = dCktimeRepository.findAll().size();
        dCktime.setId(count.incrementAndGet());

        // Create the DCktime
        DCktimeDTO dCktimeDTO = dCktimeMapper.toDto(dCktime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDCktimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dCktimeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dCktimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCktime in the database
        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCktime in Elasticsearch
        verify(mockDCktimeSearchRepository, times(0)).save(dCktime);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDCktime() throws Exception {
        int databaseSizeBeforeUpdate = dCktimeRepository.findAll().size();
        dCktime.setId(count.incrementAndGet());

        // Create the DCktime
        DCktimeDTO dCktimeDTO = dCktimeMapper.toDto(dCktime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCktimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dCktimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCktime in the database
        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCktime in Elasticsearch
        verify(mockDCktimeSearchRepository, times(0)).save(dCktime);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDCktime() throws Exception {
        int databaseSizeBeforeUpdate = dCktimeRepository.findAll().size();
        dCktime.setId(count.incrementAndGet());

        // Create the DCktime
        DCktimeDTO dCktimeDTO = dCktimeMapper.toDto(dCktime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCktimeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dCktimeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DCktime in the database
        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCktime in Elasticsearch
        verify(mockDCktimeSearchRepository, times(0)).save(dCktime);
    }

    @Test
    @Transactional
    void deleteDCktime() throws Exception {
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);

        int databaseSizeBeforeDelete = dCktimeRepository.findAll().size();

        // Delete the dCktime
        restDCktimeMockMvc
            .perform(delete(ENTITY_API_URL_ID, dCktime.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DCktime> dCktimeList = dCktimeRepository.findAll();
        assertThat(dCktimeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DCktime in Elasticsearch
        verify(mockDCktimeSearchRepository, times(1)).deleteById(dCktime.getId());
    }

    @Test
    @Transactional
    void searchDCktime() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dCktimeRepository.saveAndFlush(dCktime);
        when(mockDCktimeSearchRepository.search(queryStringQuery("id:" + dCktime.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dCktime), PageRequest.of(0, 1), 1));

        // Search the dCktime
        restDCktimeMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dCktime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dCktime.getId().intValue())))
            .andExpect(jsonPath("$.[*].begintime").value(hasItem(DEFAULT_BEGINTIME.toString())))
            .andExpect(jsonPath("$.[*].endtime").value(hasItem(DEFAULT_ENDTIME.toString())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].ckbillno").value(hasItem(DEFAULT_CKBILLNO)));
    }
}
