package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DDepot;
import ihotel.app.repository.DDepotRepository;
import ihotel.app.repository.search.DDepotSearchRepository;
import ihotel.app.service.criteria.DDepotCriteria;
import ihotel.app.service.dto.DDepotDTO;
import ihotel.app.service.mapper.DDepotMapper;
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
 * Integration tests for the {@link DDepotResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DDepotResourceIT {

    private static final Boolean DEFAULT_DEPOTID = false;
    private static final Boolean UPDATED_DEPOTID = true;

    private static final String DEFAULT_DEPOT = "AAAAAAAAAA";
    private static final String UPDATED_DEPOT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/d-depots";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-depots";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DDepotRepository dDepotRepository;

    @Autowired
    private DDepotMapper dDepotMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DDepotSearchRepositoryMockConfiguration
     */
    @Autowired
    private DDepotSearchRepository mockDDepotSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDDepotMockMvc;

    private DDepot dDepot;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DDepot createEntity(EntityManager em) {
        DDepot dDepot = new DDepot().depotid(DEFAULT_DEPOTID).depot(DEFAULT_DEPOT);
        return dDepot;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DDepot createUpdatedEntity(EntityManager em) {
        DDepot dDepot = new DDepot().depotid(UPDATED_DEPOTID).depot(UPDATED_DEPOT);
        return dDepot;
    }

    @BeforeEach
    public void initTest() {
        dDepot = createEntity(em);
    }

    @Test
    @Transactional
    void createDDepot() throws Exception {
        int databaseSizeBeforeCreate = dDepotRepository.findAll().size();
        // Create the DDepot
        DDepotDTO dDepotDTO = dDepotMapper.toDto(dDepot);
        restDDepotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDepotDTO)))
            .andExpect(status().isCreated());

        // Validate the DDepot in the database
        List<DDepot> dDepotList = dDepotRepository.findAll();
        assertThat(dDepotList).hasSize(databaseSizeBeforeCreate + 1);
        DDepot testDDepot = dDepotList.get(dDepotList.size() - 1);
        assertThat(testDDepot.getDepotid()).isEqualTo(DEFAULT_DEPOTID);
        assertThat(testDDepot.getDepot()).isEqualTo(DEFAULT_DEPOT);

        // Validate the DDepot in Elasticsearch
        verify(mockDDepotSearchRepository, times(1)).save(testDDepot);
    }

    @Test
    @Transactional
    void createDDepotWithExistingId() throws Exception {
        // Create the DDepot with an existing ID
        dDepot.setId(1L);
        DDepotDTO dDepotDTO = dDepotMapper.toDto(dDepot);

        int databaseSizeBeforeCreate = dDepotRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDDepotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDepotDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DDepot in the database
        List<DDepot> dDepotList = dDepotRepository.findAll();
        assertThat(dDepotList).hasSize(databaseSizeBeforeCreate);

        // Validate the DDepot in Elasticsearch
        verify(mockDDepotSearchRepository, times(0)).save(dDepot);
    }

    @Test
    @Transactional
    void checkDepotidIsRequired() throws Exception {
        int databaseSizeBeforeTest = dDepotRepository.findAll().size();
        // set the field null
        dDepot.setDepotid(null);

        // Create the DDepot, which fails.
        DDepotDTO dDepotDTO = dDepotMapper.toDto(dDepot);

        restDDepotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDepotDTO)))
            .andExpect(status().isBadRequest());

        List<DDepot> dDepotList = dDepotRepository.findAll();
        assertThat(dDepotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDepotIsRequired() throws Exception {
        int databaseSizeBeforeTest = dDepotRepository.findAll().size();
        // set the field null
        dDepot.setDepot(null);

        // Create the DDepot, which fails.
        DDepotDTO dDepotDTO = dDepotMapper.toDto(dDepot);

        restDDepotMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDepotDTO)))
            .andExpect(status().isBadRequest());

        List<DDepot> dDepotList = dDepotRepository.findAll();
        assertThat(dDepotList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDDepots() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        // Get all the dDepotList
        restDDepotMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dDepot.getId().intValue())))
            .andExpect(jsonPath("$.[*].depotid").value(hasItem(DEFAULT_DEPOTID.booleanValue())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)));
    }

    @Test
    @Transactional
    void getDDepot() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        // Get the dDepot
        restDDepotMockMvc
            .perform(get(ENTITY_API_URL_ID, dDepot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dDepot.getId().intValue()))
            .andExpect(jsonPath("$.depotid").value(DEFAULT_DEPOTID.booleanValue()))
            .andExpect(jsonPath("$.depot").value(DEFAULT_DEPOT));
    }

    @Test
    @Transactional
    void getDDepotsByIdFiltering() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        Long id = dDepot.getId();

        defaultDDepotShouldBeFound("id.equals=" + id);
        defaultDDepotShouldNotBeFound("id.notEquals=" + id);

        defaultDDepotShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDDepotShouldNotBeFound("id.greaterThan=" + id);

        defaultDDepotShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDDepotShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDDepotsByDepotidIsEqualToSomething() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        // Get all the dDepotList where depotid equals to DEFAULT_DEPOTID
        defaultDDepotShouldBeFound("depotid.equals=" + DEFAULT_DEPOTID);

        // Get all the dDepotList where depotid equals to UPDATED_DEPOTID
        defaultDDepotShouldNotBeFound("depotid.equals=" + UPDATED_DEPOTID);
    }

    @Test
    @Transactional
    void getAllDDepotsByDepotidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        // Get all the dDepotList where depotid not equals to DEFAULT_DEPOTID
        defaultDDepotShouldNotBeFound("depotid.notEquals=" + DEFAULT_DEPOTID);

        // Get all the dDepotList where depotid not equals to UPDATED_DEPOTID
        defaultDDepotShouldBeFound("depotid.notEquals=" + UPDATED_DEPOTID);
    }

    @Test
    @Transactional
    void getAllDDepotsByDepotidIsInShouldWork() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        // Get all the dDepotList where depotid in DEFAULT_DEPOTID or UPDATED_DEPOTID
        defaultDDepotShouldBeFound("depotid.in=" + DEFAULT_DEPOTID + "," + UPDATED_DEPOTID);

        // Get all the dDepotList where depotid equals to UPDATED_DEPOTID
        defaultDDepotShouldNotBeFound("depotid.in=" + UPDATED_DEPOTID);
    }

    @Test
    @Transactional
    void getAllDDepotsByDepotidIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        // Get all the dDepotList where depotid is not null
        defaultDDepotShouldBeFound("depotid.specified=true");

        // Get all the dDepotList where depotid is null
        defaultDDepotShouldNotBeFound("depotid.specified=false");
    }

    @Test
    @Transactional
    void getAllDDepotsByDepotIsEqualToSomething() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        // Get all the dDepotList where depot equals to DEFAULT_DEPOT
        defaultDDepotShouldBeFound("depot.equals=" + DEFAULT_DEPOT);

        // Get all the dDepotList where depot equals to UPDATED_DEPOT
        defaultDDepotShouldNotBeFound("depot.equals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDDepotsByDepotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        // Get all the dDepotList where depot not equals to DEFAULT_DEPOT
        defaultDDepotShouldNotBeFound("depot.notEquals=" + DEFAULT_DEPOT);

        // Get all the dDepotList where depot not equals to UPDATED_DEPOT
        defaultDDepotShouldBeFound("depot.notEquals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDDepotsByDepotIsInShouldWork() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        // Get all the dDepotList where depot in DEFAULT_DEPOT or UPDATED_DEPOT
        defaultDDepotShouldBeFound("depot.in=" + DEFAULT_DEPOT + "," + UPDATED_DEPOT);

        // Get all the dDepotList where depot equals to UPDATED_DEPOT
        defaultDDepotShouldNotBeFound("depot.in=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDDepotsByDepotIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        // Get all the dDepotList where depot is not null
        defaultDDepotShouldBeFound("depot.specified=true");

        // Get all the dDepotList where depot is null
        defaultDDepotShouldNotBeFound("depot.specified=false");
    }

    @Test
    @Transactional
    void getAllDDepotsByDepotContainsSomething() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        // Get all the dDepotList where depot contains DEFAULT_DEPOT
        defaultDDepotShouldBeFound("depot.contains=" + DEFAULT_DEPOT);

        // Get all the dDepotList where depot contains UPDATED_DEPOT
        defaultDDepotShouldNotBeFound("depot.contains=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllDDepotsByDepotNotContainsSomething() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        // Get all the dDepotList where depot does not contain DEFAULT_DEPOT
        defaultDDepotShouldNotBeFound("depot.doesNotContain=" + DEFAULT_DEPOT);

        // Get all the dDepotList where depot does not contain UPDATED_DEPOT
        defaultDDepotShouldBeFound("depot.doesNotContain=" + UPDATED_DEPOT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDDepotShouldBeFound(String filter) throws Exception {
        restDDepotMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dDepot.getId().intValue())))
            .andExpect(jsonPath("$.[*].depotid").value(hasItem(DEFAULT_DEPOTID.booleanValue())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)));

        // Check, that the count call also returns 1
        restDDepotMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDDepotShouldNotBeFound(String filter) throws Exception {
        restDDepotMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDDepotMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDDepot() throws Exception {
        // Get the dDepot
        restDDepotMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDDepot() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        int databaseSizeBeforeUpdate = dDepotRepository.findAll().size();

        // Update the dDepot
        DDepot updatedDDepot = dDepotRepository.findById(dDepot.getId()).get();
        // Disconnect from session so that the updates on updatedDDepot are not directly saved in db
        em.detach(updatedDDepot);
        updatedDDepot.depotid(UPDATED_DEPOTID).depot(UPDATED_DEPOT);
        DDepotDTO dDepotDTO = dDepotMapper.toDto(updatedDDepot);

        restDDepotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dDepotDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dDepotDTO))
            )
            .andExpect(status().isOk());

        // Validate the DDepot in the database
        List<DDepot> dDepotList = dDepotRepository.findAll();
        assertThat(dDepotList).hasSize(databaseSizeBeforeUpdate);
        DDepot testDDepot = dDepotList.get(dDepotList.size() - 1);
        assertThat(testDDepot.getDepotid()).isEqualTo(UPDATED_DEPOTID);
        assertThat(testDDepot.getDepot()).isEqualTo(UPDATED_DEPOT);

        // Validate the DDepot in Elasticsearch
        verify(mockDDepotSearchRepository).save(testDDepot);
    }

    @Test
    @Transactional
    void putNonExistingDDepot() throws Exception {
        int databaseSizeBeforeUpdate = dDepotRepository.findAll().size();
        dDepot.setId(count.incrementAndGet());

        // Create the DDepot
        DDepotDTO dDepotDTO = dDepotMapper.toDto(dDepot);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDDepotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dDepotDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dDepotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DDepot in the database
        List<DDepot> dDepotList = dDepotRepository.findAll();
        assertThat(dDepotList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDepot in Elasticsearch
        verify(mockDDepotSearchRepository, times(0)).save(dDepot);
    }

    @Test
    @Transactional
    void putWithIdMismatchDDepot() throws Exception {
        int databaseSizeBeforeUpdate = dDepotRepository.findAll().size();
        dDepot.setId(count.incrementAndGet());

        // Create the DDepot
        DDepotDTO dDepotDTO = dDepotMapper.toDto(dDepot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDDepotMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dDepotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DDepot in the database
        List<DDepot> dDepotList = dDepotRepository.findAll();
        assertThat(dDepotList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDepot in Elasticsearch
        verify(mockDDepotSearchRepository, times(0)).save(dDepot);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDDepot() throws Exception {
        int databaseSizeBeforeUpdate = dDepotRepository.findAll().size();
        dDepot.setId(count.incrementAndGet());

        // Create the DDepot
        DDepotDTO dDepotDTO = dDepotMapper.toDto(dDepot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDDepotMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDepotDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DDepot in the database
        List<DDepot> dDepotList = dDepotRepository.findAll();
        assertThat(dDepotList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDepot in Elasticsearch
        verify(mockDDepotSearchRepository, times(0)).save(dDepot);
    }

    @Test
    @Transactional
    void partialUpdateDDepotWithPatch() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        int databaseSizeBeforeUpdate = dDepotRepository.findAll().size();

        // Update the dDepot using partial update
        DDepot partialUpdatedDDepot = new DDepot();
        partialUpdatedDDepot.setId(dDepot.getId());

        restDDepotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDDepot.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDDepot))
            )
            .andExpect(status().isOk());

        // Validate the DDepot in the database
        List<DDepot> dDepotList = dDepotRepository.findAll();
        assertThat(dDepotList).hasSize(databaseSizeBeforeUpdate);
        DDepot testDDepot = dDepotList.get(dDepotList.size() - 1);
        assertThat(testDDepot.getDepotid()).isEqualTo(DEFAULT_DEPOTID);
        assertThat(testDDepot.getDepot()).isEqualTo(DEFAULT_DEPOT);
    }

    @Test
    @Transactional
    void fullUpdateDDepotWithPatch() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        int databaseSizeBeforeUpdate = dDepotRepository.findAll().size();

        // Update the dDepot using partial update
        DDepot partialUpdatedDDepot = new DDepot();
        partialUpdatedDDepot.setId(dDepot.getId());

        partialUpdatedDDepot.depotid(UPDATED_DEPOTID).depot(UPDATED_DEPOT);

        restDDepotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDDepot.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDDepot))
            )
            .andExpect(status().isOk());

        // Validate the DDepot in the database
        List<DDepot> dDepotList = dDepotRepository.findAll();
        assertThat(dDepotList).hasSize(databaseSizeBeforeUpdate);
        DDepot testDDepot = dDepotList.get(dDepotList.size() - 1);
        assertThat(testDDepot.getDepotid()).isEqualTo(UPDATED_DEPOTID);
        assertThat(testDDepot.getDepot()).isEqualTo(UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void patchNonExistingDDepot() throws Exception {
        int databaseSizeBeforeUpdate = dDepotRepository.findAll().size();
        dDepot.setId(count.incrementAndGet());

        // Create the DDepot
        DDepotDTO dDepotDTO = dDepotMapper.toDto(dDepot);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDDepotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dDepotDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dDepotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DDepot in the database
        List<DDepot> dDepotList = dDepotRepository.findAll();
        assertThat(dDepotList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDepot in Elasticsearch
        verify(mockDDepotSearchRepository, times(0)).save(dDepot);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDDepot() throws Exception {
        int databaseSizeBeforeUpdate = dDepotRepository.findAll().size();
        dDepot.setId(count.incrementAndGet());

        // Create the DDepot
        DDepotDTO dDepotDTO = dDepotMapper.toDto(dDepot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDDepotMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dDepotDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DDepot in the database
        List<DDepot> dDepotList = dDepotRepository.findAll();
        assertThat(dDepotList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDepot in Elasticsearch
        verify(mockDDepotSearchRepository, times(0)).save(dDepot);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDDepot() throws Exception {
        int databaseSizeBeforeUpdate = dDepotRepository.findAll().size();
        dDepot.setId(count.incrementAndGet());

        // Create the DDepot
        DDepotDTO dDepotDTO = dDepotMapper.toDto(dDepot);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDDepotMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dDepotDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DDepot in the database
        List<DDepot> dDepotList = dDepotRepository.findAll();
        assertThat(dDepotList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDepot in Elasticsearch
        verify(mockDDepotSearchRepository, times(0)).save(dDepot);
    }

    @Test
    @Transactional
    void deleteDDepot() throws Exception {
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);

        int databaseSizeBeforeDelete = dDepotRepository.findAll().size();

        // Delete the dDepot
        restDDepotMockMvc
            .perform(delete(ENTITY_API_URL_ID, dDepot.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DDepot> dDepotList = dDepotRepository.findAll();
        assertThat(dDepotList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DDepot in Elasticsearch
        verify(mockDDepotSearchRepository, times(1)).deleteById(dDepot.getId());
    }

    @Test
    @Transactional
    void searchDDepot() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dDepotRepository.saveAndFlush(dDepot);
        when(mockDDepotSearchRepository.search(queryStringQuery("id:" + dDepot.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dDepot), PageRequest.of(0, 1), 1));

        // Search the dDepot
        restDDepotMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dDepot.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dDepot.getId().intValue())))
            .andExpect(jsonPath("$.[*].depotid").value(hasItem(DEFAULT_DEPOTID.booleanValue())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)));
    }
}
