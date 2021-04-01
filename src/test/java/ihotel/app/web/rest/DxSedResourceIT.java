package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DxSed;
import ihotel.app.repository.DxSedRepository;
import ihotel.app.repository.search.DxSedSearchRepository;
import ihotel.app.service.criteria.DxSedCriteria;
import ihotel.app.service.dto.DxSedDTO;
import ihotel.app.service.mapper.DxSedMapper;
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
 * Integration tests for the {@link DxSedResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DxSedResourceIT {

    private static final Instant DEFAULT_DX_RQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DX_RQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DX_ZT = "AA";
    private static final String UPDATED_DX_ZT = "BB";

    private static final Instant DEFAULT_FS_SJ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FS_SJ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/dx-seds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/dx-seds";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DxSedRepository dxSedRepository;

    @Autowired
    private DxSedMapper dxSedMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DxSedSearchRepositoryMockConfiguration
     */
    @Autowired
    private DxSedSearchRepository mockDxSedSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDxSedMockMvc;

    private DxSed dxSed;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DxSed createEntity(EntityManager em) {
        DxSed dxSed = new DxSed().dxRq(DEFAULT_DX_RQ).dxZt(DEFAULT_DX_ZT).fsSj(DEFAULT_FS_SJ);
        return dxSed;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DxSed createUpdatedEntity(EntityManager em) {
        DxSed dxSed = new DxSed().dxRq(UPDATED_DX_RQ).dxZt(UPDATED_DX_ZT).fsSj(UPDATED_FS_SJ);
        return dxSed;
    }

    @BeforeEach
    public void initTest() {
        dxSed = createEntity(em);
    }

    @Test
    @Transactional
    void createDxSed() throws Exception {
        int databaseSizeBeforeCreate = dxSedRepository.findAll().size();
        // Create the DxSed
        DxSedDTO dxSedDTO = dxSedMapper.toDto(dxSed);
        restDxSedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dxSedDTO)))
            .andExpect(status().isCreated());

        // Validate the DxSed in the database
        List<DxSed> dxSedList = dxSedRepository.findAll();
        assertThat(dxSedList).hasSize(databaseSizeBeforeCreate + 1);
        DxSed testDxSed = dxSedList.get(dxSedList.size() - 1);
        assertThat(testDxSed.getDxRq()).isEqualTo(DEFAULT_DX_RQ);
        assertThat(testDxSed.getDxZt()).isEqualTo(DEFAULT_DX_ZT);
        assertThat(testDxSed.getFsSj()).isEqualTo(DEFAULT_FS_SJ);

        // Validate the DxSed in Elasticsearch
        verify(mockDxSedSearchRepository, times(1)).save(testDxSed);
    }

    @Test
    @Transactional
    void createDxSedWithExistingId() throws Exception {
        // Create the DxSed with an existing ID
        dxSed.setId(1L);
        DxSedDTO dxSedDTO = dxSedMapper.toDto(dxSed);

        int databaseSizeBeforeCreate = dxSedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDxSedMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dxSedDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DxSed in the database
        List<DxSed> dxSedList = dxSedRepository.findAll();
        assertThat(dxSedList).hasSize(databaseSizeBeforeCreate);

        // Validate the DxSed in Elasticsearch
        verify(mockDxSedSearchRepository, times(0)).save(dxSed);
    }

    @Test
    @Transactional
    void getAllDxSeds() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList
        restDxSedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dxSed.getId().intValue())))
            .andExpect(jsonPath("$.[*].dxRq").value(hasItem(DEFAULT_DX_RQ.toString())))
            .andExpect(jsonPath("$.[*].dxZt").value(hasItem(DEFAULT_DX_ZT)))
            .andExpect(jsonPath("$.[*].fsSj").value(hasItem(DEFAULT_FS_SJ.toString())));
    }

    @Test
    @Transactional
    void getDxSed() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get the dxSed
        restDxSedMockMvc
            .perform(get(ENTITY_API_URL_ID, dxSed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dxSed.getId().intValue()))
            .andExpect(jsonPath("$.dxRq").value(DEFAULT_DX_RQ.toString()))
            .andExpect(jsonPath("$.dxZt").value(DEFAULT_DX_ZT))
            .andExpect(jsonPath("$.fsSj").value(DEFAULT_FS_SJ.toString()));
    }

    @Test
    @Transactional
    void getDxSedsByIdFiltering() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        Long id = dxSed.getId();

        defaultDxSedShouldBeFound("id.equals=" + id);
        defaultDxSedShouldNotBeFound("id.notEquals=" + id);

        defaultDxSedShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDxSedShouldNotBeFound("id.greaterThan=" + id);

        defaultDxSedShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDxSedShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDxSedsByDxRqIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList where dxRq equals to DEFAULT_DX_RQ
        defaultDxSedShouldBeFound("dxRq.equals=" + DEFAULT_DX_RQ);

        // Get all the dxSedList where dxRq equals to UPDATED_DX_RQ
        defaultDxSedShouldNotBeFound("dxRq.equals=" + UPDATED_DX_RQ);
    }

    @Test
    @Transactional
    void getAllDxSedsByDxRqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList where dxRq not equals to DEFAULT_DX_RQ
        defaultDxSedShouldNotBeFound("dxRq.notEquals=" + DEFAULT_DX_RQ);

        // Get all the dxSedList where dxRq not equals to UPDATED_DX_RQ
        defaultDxSedShouldBeFound("dxRq.notEquals=" + UPDATED_DX_RQ);
    }

    @Test
    @Transactional
    void getAllDxSedsByDxRqIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList where dxRq in DEFAULT_DX_RQ or UPDATED_DX_RQ
        defaultDxSedShouldBeFound("dxRq.in=" + DEFAULT_DX_RQ + "," + UPDATED_DX_RQ);

        // Get all the dxSedList where dxRq equals to UPDATED_DX_RQ
        defaultDxSedShouldNotBeFound("dxRq.in=" + UPDATED_DX_RQ);
    }

    @Test
    @Transactional
    void getAllDxSedsByDxRqIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList where dxRq is not null
        defaultDxSedShouldBeFound("dxRq.specified=true");

        // Get all the dxSedList where dxRq is null
        defaultDxSedShouldNotBeFound("dxRq.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedsByDxZtIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList where dxZt equals to DEFAULT_DX_ZT
        defaultDxSedShouldBeFound("dxZt.equals=" + DEFAULT_DX_ZT);

        // Get all the dxSedList where dxZt equals to UPDATED_DX_ZT
        defaultDxSedShouldNotBeFound("dxZt.equals=" + UPDATED_DX_ZT);
    }

    @Test
    @Transactional
    void getAllDxSedsByDxZtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList where dxZt not equals to DEFAULT_DX_ZT
        defaultDxSedShouldNotBeFound("dxZt.notEquals=" + DEFAULT_DX_ZT);

        // Get all the dxSedList where dxZt not equals to UPDATED_DX_ZT
        defaultDxSedShouldBeFound("dxZt.notEquals=" + UPDATED_DX_ZT);
    }

    @Test
    @Transactional
    void getAllDxSedsByDxZtIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList where dxZt in DEFAULT_DX_ZT or UPDATED_DX_ZT
        defaultDxSedShouldBeFound("dxZt.in=" + DEFAULT_DX_ZT + "," + UPDATED_DX_ZT);

        // Get all the dxSedList where dxZt equals to UPDATED_DX_ZT
        defaultDxSedShouldNotBeFound("dxZt.in=" + UPDATED_DX_ZT);
    }

    @Test
    @Transactional
    void getAllDxSedsByDxZtIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList where dxZt is not null
        defaultDxSedShouldBeFound("dxZt.specified=true");

        // Get all the dxSedList where dxZt is null
        defaultDxSedShouldNotBeFound("dxZt.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedsByDxZtContainsSomething() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList where dxZt contains DEFAULT_DX_ZT
        defaultDxSedShouldBeFound("dxZt.contains=" + DEFAULT_DX_ZT);

        // Get all the dxSedList where dxZt contains UPDATED_DX_ZT
        defaultDxSedShouldNotBeFound("dxZt.contains=" + UPDATED_DX_ZT);
    }

    @Test
    @Transactional
    void getAllDxSedsByDxZtNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList where dxZt does not contain DEFAULT_DX_ZT
        defaultDxSedShouldNotBeFound("dxZt.doesNotContain=" + DEFAULT_DX_ZT);

        // Get all the dxSedList where dxZt does not contain UPDATED_DX_ZT
        defaultDxSedShouldBeFound("dxZt.doesNotContain=" + UPDATED_DX_ZT);
    }

    @Test
    @Transactional
    void getAllDxSedsByFsSjIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList where fsSj equals to DEFAULT_FS_SJ
        defaultDxSedShouldBeFound("fsSj.equals=" + DEFAULT_FS_SJ);

        // Get all the dxSedList where fsSj equals to UPDATED_FS_SJ
        defaultDxSedShouldNotBeFound("fsSj.equals=" + UPDATED_FS_SJ);
    }

    @Test
    @Transactional
    void getAllDxSedsByFsSjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList where fsSj not equals to DEFAULT_FS_SJ
        defaultDxSedShouldNotBeFound("fsSj.notEquals=" + DEFAULT_FS_SJ);

        // Get all the dxSedList where fsSj not equals to UPDATED_FS_SJ
        defaultDxSedShouldBeFound("fsSj.notEquals=" + UPDATED_FS_SJ);
    }

    @Test
    @Transactional
    void getAllDxSedsByFsSjIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList where fsSj in DEFAULT_FS_SJ or UPDATED_FS_SJ
        defaultDxSedShouldBeFound("fsSj.in=" + DEFAULT_FS_SJ + "," + UPDATED_FS_SJ);

        // Get all the dxSedList where fsSj equals to UPDATED_FS_SJ
        defaultDxSedShouldNotBeFound("fsSj.in=" + UPDATED_FS_SJ);
    }

    @Test
    @Transactional
    void getAllDxSedsByFsSjIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        // Get all the dxSedList where fsSj is not null
        defaultDxSedShouldBeFound("fsSj.specified=true");

        // Get all the dxSedList where fsSj is null
        defaultDxSedShouldNotBeFound("fsSj.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDxSedShouldBeFound(String filter) throws Exception {
        restDxSedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dxSed.getId().intValue())))
            .andExpect(jsonPath("$.[*].dxRq").value(hasItem(DEFAULT_DX_RQ.toString())))
            .andExpect(jsonPath("$.[*].dxZt").value(hasItem(DEFAULT_DX_ZT)))
            .andExpect(jsonPath("$.[*].fsSj").value(hasItem(DEFAULT_FS_SJ.toString())));

        // Check, that the count call also returns 1
        restDxSedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDxSedShouldNotBeFound(String filter) throws Exception {
        restDxSedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDxSedMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDxSed() throws Exception {
        // Get the dxSed
        restDxSedMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDxSed() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        int databaseSizeBeforeUpdate = dxSedRepository.findAll().size();

        // Update the dxSed
        DxSed updatedDxSed = dxSedRepository.findById(dxSed.getId()).get();
        // Disconnect from session so that the updates on updatedDxSed are not directly saved in db
        em.detach(updatedDxSed);
        updatedDxSed.dxRq(UPDATED_DX_RQ).dxZt(UPDATED_DX_ZT).fsSj(UPDATED_FS_SJ);
        DxSedDTO dxSedDTO = dxSedMapper.toDto(updatedDxSed);

        restDxSedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dxSedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dxSedDTO))
            )
            .andExpect(status().isOk());

        // Validate the DxSed in the database
        List<DxSed> dxSedList = dxSedRepository.findAll();
        assertThat(dxSedList).hasSize(databaseSizeBeforeUpdate);
        DxSed testDxSed = dxSedList.get(dxSedList.size() - 1);
        assertThat(testDxSed.getDxRq()).isEqualTo(UPDATED_DX_RQ);
        assertThat(testDxSed.getDxZt()).isEqualTo(UPDATED_DX_ZT);
        assertThat(testDxSed.getFsSj()).isEqualTo(UPDATED_FS_SJ);

        // Validate the DxSed in Elasticsearch
        verify(mockDxSedSearchRepository).save(testDxSed);
    }

    @Test
    @Transactional
    void putNonExistingDxSed() throws Exception {
        int databaseSizeBeforeUpdate = dxSedRepository.findAll().size();
        dxSed.setId(count.incrementAndGet());

        // Create the DxSed
        DxSedDTO dxSedDTO = dxSedMapper.toDto(dxSed);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDxSedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dxSedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dxSedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DxSed in the database
        List<DxSed> dxSedList = dxSedRepository.findAll();
        assertThat(dxSedList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DxSed in Elasticsearch
        verify(mockDxSedSearchRepository, times(0)).save(dxSed);
    }

    @Test
    @Transactional
    void putWithIdMismatchDxSed() throws Exception {
        int databaseSizeBeforeUpdate = dxSedRepository.findAll().size();
        dxSed.setId(count.incrementAndGet());

        // Create the DxSed
        DxSedDTO dxSedDTO = dxSedMapper.toDto(dxSed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDxSedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dxSedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DxSed in the database
        List<DxSed> dxSedList = dxSedRepository.findAll();
        assertThat(dxSedList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DxSed in Elasticsearch
        verify(mockDxSedSearchRepository, times(0)).save(dxSed);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDxSed() throws Exception {
        int databaseSizeBeforeUpdate = dxSedRepository.findAll().size();
        dxSed.setId(count.incrementAndGet());

        // Create the DxSed
        DxSedDTO dxSedDTO = dxSedMapper.toDto(dxSed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDxSedMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dxSedDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DxSed in the database
        List<DxSed> dxSedList = dxSedRepository.findAll();
        assertThat(dxSedList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DxSed in Elasticsearch
        verify(mockDxSedSearchRepository, times(0)).save(dxSed);
    }

    @Test
    @Transactional
    void partialUpdateDxSedWithPatch() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        int databaseSizeBeforeUpdate = dxSedRepository.findAll().size();

        // Update the dxSed using partial update
        DxSed partialUpdatedDxSed = new DxSed();
        partialUpdatedDxSed.setId(dxSed.getId());

        partialUpdatedDxSed.dxZt(UPDATED_DX_ZT);

        restDxSedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDxSed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDxSed))
            )
            .andExpect(status().isOk());

        // Validate the DxSed in the database
        List<DxSed> dxSedList = dxSedRepository.findAll();
        assertThat(dxSedList).hasSize(databaseSizeBeforeUpdate);
        DxSed testDxSed = dxSedList.get(dxSedList.size() - 1);
        assertThat(testDxSed.getDxRq()).isEqualTo(DEFAULT_DX_RQ);
        assertThat(testDxSed.getDxZt()).isEqualTo(UPDATED_DX_ZT);
        assertThat(testDxSed.getFsSj()).isEqualTo(DEFAULT_FS_SJ);
    }

    @Test
    @Transactional
    void fullUpdateDxSedWithPatch() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        int databaseSizeBeforeUpdate = dxSedRepository.findAll().size();

        // Update the dxSed using partial update
        DxSed partialUpdatedDxSed = new DxSed();
        partialUpdatedDxSed.setId(dxSed.getId());

        partialUpdatedDxSed.dxRq(UPDATED_DX_RQ).dxZt(UPDATED_DX_ZT).fsSj(UPDATED_FS_SJ);

        restDxSedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDxSed.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDxSed))
            )
            .andExpect(status().isOk());

        // Validate the DxSed in the database
        List<DxSed> dxSedList = dxSedRepository.findAll();
        assertThat(dxSedList).hasSize(databaseSizeBeforeUpdate);
        DxSed testDxSed = dxSedList.get(dxSedList.size() - 1);
        assertThat(testDxSed.getDxRq()).isEqualTo(UPDATED_DX_RQ);
        assertThat(testDxSed.getDxZt()).isEqualTo(UPDATED_DX_ZT);
        assertThat(testDxSed.getFsSj()).isEqualTo(UPDATED_FS_SJ);
    }

    @Test
    @Transactional
    void patchNonExistingDxSed() throws Exception {
        int databaseSizeBeforeUpdate = dxSedRepository.findAll().size();
        dxSed.setId(count.incrementAndGet());

        // Create the DxSed
        DxSedDTO dxSedDTO = dxSedMapper.toDto(dxSed);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDxSedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dxSedDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dxSedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DxSed in the database
        List<DxSed> dxSedList = dxSedRepository.findAll();
        assertThat(dxSedList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DxSed in Elasticsearch
        verify(mockDxSedSearchRepository, times(0)).save(dxSed);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDxSed() throws Exception {
        int databaseSizeBeforeUpdate = dxSedRepository.findAll().size();
        dxSed.setId(count.incrementAndGet());

        // Create the DxSed
        DxSedDTO dxSedDTO = dxSedMapper.toDto(dxSed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDxSedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dxSedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DxSed in the database
        List<DxSed> dxSedList = dxSedRepository.findAll();
        assertThat(dxSedList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DxSed in Elasticsearch
        verify(mockDxSedSearchRepository, times(0)).save(dxSed);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDxSed() throws Exception {
        int databaseSizeBeforeUpdate = dxSedRepository.findAll().size();
        dxSed.setId(count.incrementAndGet());

        // Create the DxSed
        DxSedDTO dxSedDTO = dxSedMapper.toDto(dxSed);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDxSedMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dxSedDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DxSed in the database
        List<DxSed> dxSedList = dxSedRepository.findAll();
        assertThat(dxSedList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DxSed in Elasticsearch
        verify(mockDxSedSearchRepository, times(0)).save(dxSed);
    }

    @Test
    @Transactional
    void deleteDxSed() throws Exception {
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);

        int databaseSizeBeforeDelete = dxSedRepository.findAll().size();

        // Delete the dxSed
        restDxSedMockMvc
            .perform(delete(ENTITY_API_URL_ID, dxSed.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DxSed> dxSedList = dxSedRepository.findAll();
        assertThat(dxSedList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DxSed in Elasticsearch
        verify(mockDxSedSearchRepository, times(1)).deleteById(dxSed.getId());
    }

    @Test
    @Transactional
    void searchDxSed() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dxSedRepository.saveAndFlush(dxSed);
        when(mockDxSedSearchRepository.search(queryStringQuery("id:" + dxSed.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dxSed), PageRequest.of(0, 1), 1));

        // Search the dxSed
        restDxSedMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dxSed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dxSed.getId().intValue())))
            .andExpect(jsonPath("$.[*].dxRq").value(hasItem(DEFAULT_DX_RQ.toString())))
            .andExpect(jsonPath("$.[*].dxZt").value(hasItem(DEFAULT_DX_ZT)))
            .andExpect(jsonPath("$.[*].fsSj").value(hasItem(DEFAULT_FS_SJ.toString())));
    }
}
