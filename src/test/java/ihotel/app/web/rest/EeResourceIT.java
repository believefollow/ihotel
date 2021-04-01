package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Ee;
import ihotel.app.repository.EeRepository;
import ihotel.app.repository.search.EeSearchRepository;
import ihotel.app.service.criteria.EeCriteria;
import ihotel.app.service.dto.EeDTO;
import ihotel.app.service.mapper.EeMapper;
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
 * Integration tests for the {@link EeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class EeResourceIT {

    private static final String DEFAULT_ACC = "AAAAAAAAAA";
    private static final String UPDATED_ACC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ees";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/ees";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EeRepository eeRepository;

    @Autowired
    private EeMapper eeMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.EeSearchRepositoryMockConfiguration
     */
    @Autowired
    private EeSearchRepository mockEeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEeMockMvc;

    private Ee ee;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ee createEntity(EntityManager em) {
        Ee ee = new Ee().acc(DEFAULT_ACC);
        return ee;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ee createUpdatedEntity(EntityManager em) {
        Ee ee = new Ee().acc(UPDATED_ACC);
        return ee;
    }

    @BeforeEach
    public void initTest() {
        ee = createEntity(em);
    }

    @Test
    @Transactional
    void createEe() throws Exception {
        int databaseSizeBeforeCreate = eeRepository.findAll().size();
        // Create the Ee
        EeDTO eeDTO = eeMapper.toDto(ee);
        restEeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eeDTO)))
            .andExpect(status().isCreated());

        // Validate the Ee in the database
        List<Ee> eeList = eeRepository.findAll();
        assertThat(eeList).hasSize(databaseSizeBeforeCreate + 1);
        Ee testEe = eeList.get(eeList.size() - 1);
        assertThat(testEe.getAcc()).isEqualTo(DEFAULT_ACC);

        // Validate the Ee in Elasticsearch
        verify(mockEeSearchRepository, times(1)).save(testEe);
    }

    @Test
    @Transactional
    void createEeWithExistingId() throws Exception {
        // Create the Ee with an existing ID
        ee.setId(1L);
        EeDTO eeDTO = eeMapper.toDto(ee);

        int databaseSizeBeforeCreate = eeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Ee in the database
        List<Ee> eeList = eeRepository.findAll();
        assertThat(eeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Ee in Elasticsearch
        verify(mockEeSearchRepository, times(0)).save(ee);
    }

    @Test
    @Transactional
    void getAllEes() throws Exception {
        // Initialize the database
        eeRepository.saveAndFlush(ee);

        // Get all the eeList
        restEeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ee.getId().intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)));
    }

    @Test
    @Transactional
    void getEe() throws Exception {
        // Initialize the database
        eeRepository.saveAndFlush(ee);

        // Get the ee
        restEeMockMvc
            .perform(get(ENTITY_API_URL_ID, ee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ee.getId().intValue()))
            .andExpect(jsonPath("$.acc").value(DEFAULT_ACC));
    }

    @Test
    @Transactional
    void getEesByIdFiltering() throws Exception {
        // Initialize the database
        eeRepository.saveAndFlush(ee);

        Long id = ee.getId();

        defaultEeShouldBeFound("id.equals=" + id);
        defaultEeShouldNotBeFound("id.notEquals=" + id);

        defaultEeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEeShouldNotBeFound("id.greaterThan=" + id);

        defaultEeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllEesByAccIsEqualToSomething() throws Exception {
        // Initialize the database
        eeRepository.saveAndFlush(ee);

        // Get all the eeList where acc equals to DEFAULT_ACC
        defaultEeShouldBeFound("acc.equals=" + DEFAULT_ACC);

        // Get all the eeList where acc equals to UPDATED_ACC
        defaultEeShouldNotBeFound("acc.equals=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllEesByAccIsNotEqualToSomething() throws Exception {
        // Initialize the database
        eeRepository.saveAndFlush(ee);

        // Get all the eeList where acc not equals to DEFAULT_ACC
        defaultEeShouldNotBeFound("acc.notEquals=" + DEFAULT_ACC);

        // Get all the eeList where acc not equals to UPDATED_ACC
        defaultEeShouldBeFound("acc.notEquals=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllEesByAccIsInShouldWork() throws Exception {
        // Initialize the database
        eeRepository.saveAndFlush(ee);

        // Get all the eeList where acc in DEFAULT_ACC or UPDATED_ACC
        defaultEeShouldBeFound("acc.in=" + DEFAULT_ACC + "," + UPDATED_ACC);

        // Get all the eeList where acc equals to UPDATED_ACC
        defaultEeShouldNotBeFound("acc.in=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllEesByAccIsNullOrNotNull() throws Exception {
        // Initialize the database
        eeRepository.saveAndFlush(ee);

        // Get all the eeList where acc is not null
        defaultEeShouldBeFound("acc.specified=true");

        // Get all the eeList where acc is null
        defaultEeShouldNotBeFound("acc.specified=false");
    }

    @Test
    @Transactional
    void getAllEesByAccContainsSomething() throws Exception {
        // Initialize the database
        eeRepository.saveAndFlush(ee);

        // Get all the eeList where acc contains DEFAULT_ACC
        defaultEeShouldBeFound("acc.contains=" + DEFAULT_ACC);

        // Get all the eeList where acc contains UPDATED_ACC
        defaultEeShouldNotBeFound("acc.contains=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllEesByAccNotContainsSomething() throws Exception {
        // Initialize the database
        eeRepository.saveAndFlush(ee);

        // Get all the eeList where acc does not contain DEFAULT_ACC
        defaultEeShouldNotBeFound("acc.doesNotContain=" + DEFAULT_ACC);

        // Get all the eeList where acc does not contain UPDATED_ACC
        defaultEeShouldBeFound("acc.doesNotContain=" + UPDATED_ACC);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEeShouldBeFound(String filter) throws Exception {
        restEeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ee.getId().intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)));

        // Check, that the count call also returns 1
        restEeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEeShouldNotBeFound(String filter) throws Exception {
        restEeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEe() throws Exception {
        // Get the ee
        restEeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEe() throws Exception {
        // Initialize the database
        eeRepository.saveAndFlush(ee);

        int databaseSizeBeforeUpdate = eeRepository.findAll().size();

        // Update the ee
        Ee updatedEe = eeRepository.findById(ee.getId()).get();
        // Disconnect from session so that the updates on updatedEe are not directly saved in db
        em.detach(updatedEe);
        updatedEe.acc(UPDATED_ACC);
        EeDTO eeDTO = eeMapper.toDto(updatedEe);

        restEeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Ee in the database
        List<Ee> eeList = eeRepository.findAll();
        assertThat(eeList).hasSize(databaseSizeBeforeUpdate);
        Ee testEe = eeList.get(eeList.size() - 1);
        assertThat(testEe.getAcc()).isEqualTo(UPDATED_ACC);

        // Validate the Ee in Elasticsearch
        verify(mockEeSearchRepository).save(testEe);
    }

    @Test
    @Transactional
    void putNonExistingEe() throws Exception {
        int databaseSizeBeforeUpdate = eeRepository.findAll().size();
        ee.setId(count.incrementAndGet());

        // Create the Ee
        EeDTO eeDTO = eeMapper.toDto(ee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ee in the database
        List<Ee> eeList = eeRepository.findAll();
        assertThat(eeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ee in Elasticsearch
        verify(mockEeSearchRepository, times(0)).save(ee);
    }

    @Test
    @Transactional
    void putWithIdMismatchEe() throws Exception {
        int databaseSizeBeforeUpdate = eeRepository.findAll().size();
        ee.setId(count.incrementAndGet());

        // Create the Ee
        EeDTO eeDTO = eeMapper.toDto(ee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ee in the database
        List<Ee> eeList = eeRepository.findAll();
        assertThat(eeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ee in Elasticsearch
        verify(mockEeSearchRepository, times(0)).save(ee);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEe() throws Exception {
        int databaseSizeBeforeUpdate = eeRepository.findAll().size();
        ee.setId(count.incrementAndGet());

        // Create the Ee
        EeDTO eeDTO = eeMapper.toDto(ee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ee in the database
        List<Ee> eeList = eeRepository.findAll();
        assertThat(eeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ee in Elasticsearch
        verify(mockEeSearchRepository, times(0)).save(ee);
    }

    @Test
    @Transactional
    void partialUpdateEeWithPatch() throws Exception {
        // Initialize the database
        eeRepository.saveAndFlush(ee);

        int databaseSizeBeforeUpdate = eeRepository.findAll().size();

        // Update the ee using partial update
        Ee partialUpdatedEe = new Ee();
        partialUpdatedEe.setId(ee.getId());

        restEeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEe))
            )
            .andExpect(status().isOk());

        // Validate the Ee in the database
        List<Ee> eeList = eeRepository.findAll();
        assertThat(eeList).hasSize(databaseSizeBeforeUpdate);
        Ee testEe = eeList.get(eeList.size() - 1);
        assertThat(testEe.getAcc()).isEqualTo(DEFAULT_ACC);
    }

    @Test
    @Transactional
    void fullUpdateEeWithPatch() throws Exception {
        // Initialize the database
        eeRepository.saveAndFlush(ee);

        int databaseSizeBeforeUpdate = eeRepository.findAll().size();

        // Update the ee using partial update
        Ee partialUpdatedEe = new Ee();
        partialUpdatedEe.setId(ee.getId());

        partialUpdatedEe.acc(UPDATED_ACC);

        restEeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEe.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEe))
            )
            .andExpect(status().isOk());

        // Validate the Ee in the database
        List<Ee> eeList = eeRepository.findAll();
        assertThat(eeList).hasSize(databaseSizeBeforeUpdate);
        Ee testEe = eeList.get(eeList.size() - 1);
        assertThat(testEe.getAcc()).isEqualTo(UPDATED_ACC);
    }

    @Test
    @Transactional
    void patchNonExistingEe() throws Exception {
        int databaseSizeBeforeUpdate = eeRepository.findAll().size();
        ee.setId(count.incrementAndGet());

        // Create the Ee
        EeDTO eeDTO = eeMapper.toDto(ee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ee in the database
        List<Ee> eeList = eeRepository.findAll();
        assertThat(eeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ee in Elasticsearch
        verify(mockEeSearchRepository, times(0)).save(ee);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEe() throws Exception {
        int databaseSizeBeforeUpdate = eeRepository.findAll().size();
        ee.setId(count.incrementAndGet());

        // Create the Ee
        EeDTO eeDTO = eeMapper.toDto(ee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Ee in the database
        List<Ee> eeList = eeRepository.findAll();
        assertThat(eeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ee in Elasticsearch
        verify(mockEeSearchRepository, times(0)).save(ee);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEe() throws Exception {
        int databaseSizeBeforeUpdate = eeRepository.findAll().size();
        ee.setId(count.incrementAndGet());

        // Create the Ee
        EeDTO eeDTO = eeMapper.toDto(ee);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Ee in the database
        List<Ee> eeList = eeRepository.findAll();
        assertThat(eeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ee in Elasticsearch
        verify(mockEeSearchRepository, times(0)).save(ee);
    }

    @Test
    @Transactional
    void deleteEe() throws Exception {
        // Initialize the database
        eeRepository.saveAndFlush(ee);

        int databaseSizeBeforeDelete = eeRepository.findAll().size();

        // Delete the ee
        restEeMockMvc.perform(delete(ENTITY_API_URL_ID, ee.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ee> eeList = eeRepository.findAll();
        assertThat(eeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Ee in Elasticsearch
        verify(mockEeSearchRepository, times(1)).deleteById(ee.getId());
    }

    @Test
    @Transactional
    void searchEe() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        eeRepository.saveAndFlush(ee);
        when(mockEeSearchRepository.search(queryStringQuery("id:" + ee.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ee), PageRequest.of(0, 1), 1));

        // Search the ee
        restEeMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ee.getId().intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)));
    }
}
