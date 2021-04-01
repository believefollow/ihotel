package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.AccP;
import ihotel.app.repository.AccPRepository;
import ihotel.app.repository.search.AccPSearchRepository;
import ihotel.app.service.criteria.AccPCriteria;
import ihotel.app.service.dto.AccPDTO;
import ihotel.app.service.mapper.AccPMapper;
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
 * Integration tests for the {@link AccPResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AccPResourceIT {

    private static final String DEFAULT_ACC = "AAAAAAAAAA";
    private static final String UPDATED_ACC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/acc-ps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/acc-ps";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccPRepository accPRepository;

    @Autowired
    private AccPMapper accPMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.AccPSearchRepositoryMockConfiguration
     */
    @Autowired
    private AccPSearchRepository mockAccPSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccPMockMvc;

    private AccP accP;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccP createEntity(EntityManager em) {
        AccP accP = new AccP().acc(DEFAULT_ACC);
        return accP;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccP createUpdatedEntity(EntityManager em) {
        AccP accP = new AccP().acc(UPDATED_ACC);
        return accP;
    }

    @BeforeEach
    public void initTest() {
        accP = createEntity(em);
    }

    @Test
    @Transactional
    void createAccP() throws Exception {
        int databaseSizeBeforeCreate = accPRepository.findAll().size();
        // Create the AccP
        AccPDTO accPDTO = accPMapper.toDto(accP);
        restAccPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accPDTO)))
            .andExpect(status().isCreated());

        // Validate the AccP in the database
        List<AccP> accPList = accPRepository.findAll();
        assertThat(accPList).hasSize(databaseSizeBeforeCreate + 1);
        AccP testAccP = accPList.get(accPList.size() - 1);
        assertThat(testAccP.getAcc()).isEqualTo(DEFAULT_ACC);

        // Validate the AccP in Elasticsearch
        verify(mockAccPSearchRepository, times(1)).save(testAccP);
    }

    @Test
    @Transactional
    void createAccPWithExistingId() throws Exception {
        // Create the AccP with an existing ID
        accP.setId(1L);
        AccPDTO accPDTO = accPMapper.toDto(accP);

        int databaseSizeBeforeCreate = accPRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accPDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccP in the database
        List<AccP> accPList = accPRepository.findAll();
        assertThat(accPList).hasSize(databaseSizeBeforeCreate);

        // Validate the AccP in Elasticsearch
        verify(mockAccPSearchRepository, times(0)).save(accP);
    }

    @Test
    @Transactional
    void checkAccIsRequired() throws Exception {
        int databaseSizeBeforeTest = accPRepository.findAll().size();
        // set the field null
        accP.setAcc(null);

        // Create the AccP, which fails.
        AccPDTO accPDTO = accPMapper.toDto(accP);

        restAccPMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accPDTO)))
            .andExpect(status().isBadRequest());

        List<AccP> accPList = accPRepository.findAll();
        assertThat(accPList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAccPS() throws Exception {
        // Initialize the database
        accPRepository.saveAndFlush(accP);

        // Get all the accPList
        restAccPMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accP.getId().intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)));
    }

    @Test
    @Transactional
    void getAccP() throws Exception {
        // Initialize the database
        accPRepository.saveAndFlush(accP);

        // Get the accP
        restAccPMockMvc
            .perform(get(ENTITY_API_URL_ID, accP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accP.getId().intValue()))
            .andExpect(jsonPath("$.acc").value(DEFAULT_ACC));
    }

    @Test
    @Transactional
    void getAccPSByIdFiltering() throws Exception {
        // Initialize the database
        accPRepository.saveAndFlush(accP);

        Long id = accP.getId();

        defaultAccPShouldBeFound("id.equals=" + id);
        defaultAccPShouldNotBeFound("id.notEquals=" + id);

        defaultAccPShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAccPShouldNotBeFound("id.greaterThan=" + id);

        defaultAccPShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAccPShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAccPSByAccIsEqualToSomething() throws Exception {
        // Initialize the database
        accPRepository.saveAndFlush(accP);

        // Get all the accPList where acc equals to DEFAULT_ACC
        defaultAccPShouldBeFound("acc.equals=" + DEFAULT_ACC);

        // Get all the accPList where acc equals to UPDATED_ACC
        defaultAccPShouldNotBeFound("acc.equals=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccPSByAccIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accPRepository.saveAndFlush(accP);

        // Get all the accPList where acc not equals to DEFAULT_ACC
        defaultAccPShouldNotBeFound("acc.notEquals=" + DEFAULT_ACC);

        // Get all the accPList where acc not equals to UPDATED_ACC
        defaultAccPShouldBeFound("acc.notEquals=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccPSByAccIsInShouldWork() throws Exception {
        // Initialize the database
        accPRepository.saveAndFlush(accP);

        // Get all the accPList where acc in DEFAULT_ACC or UPDATED_ACC
        defaultAccPShouldBeFound("acc.in=" + DEFAULT_ACC + "," + UPDATED_ACC);

        // Get all the accPList where acc equals to UPDATED_ACC
        defaultAccPShouldNotBeFound("acc.in=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccPSByAccIsNullOrNotNull() throws Exception {
        // Initialize the database
        accPRepository.saveAndFlush(accP);

        // Get all the accPList where acc is not null
        defaultAccPShouldBeFound("acc.specified=true");

        // Get all the accPList where acc is null
        defaultAccPShouldNotBeFound("acc.specified=false");
    }

    @Test
    @Transactional
    void getAllAccPSByAccContainsSomething() throws Exception {
        // Initialize the database
        accPRepository.saveAndFlush(accP);

        // Get all the accPList where acc contains DEFAULT_ACC
        defaultAccPShouldBeFound("acc.contains=" + DEFAULT_ACC);

        // Get all the accPList where acc contains UPDATED_ACC
        defaultAccPShouldNotBeFound("acc.contains=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccPSByAccNotContainsSomething() throws Exception {
        // Initialize the database
        accPRepository.saveAndFlush(accP);

        // Get all the accPList where acc does not contain DEFAULT_ACC
        defaultAccPShouldNotBeFound("acc.doesNotContain=" + DEFAULT_ACC);

        // Get all the accPList where acc does not contain UPDATED_ACC
        defaultAccPShouldBeFound("acc.doesNotContain=" + UPDATED_ACC);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccPShouldBeFound(String filter) throws Exception {
        restAccPMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accP.getId().intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)));

        // Check, that the count call also returns 1
        restAccPMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccPShouldNotBeFound(String filter) throws Exception {
        restAccPMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccPMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAccP() throws Exception {
        // Get the accP
        restAccPMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAccP() throws Exception {
        // Initialize the database
        accPRepository.saveAndFlush(accP);

        int databaseSizeBeforeUpdate = accPRepository.findAll().size();

        // Update the accP
        AccP updatedAccP = accPRepository.findById(accP.getId()).get();
        // Disconnect from session so that the updates on updatedAccP are not directly saved in db
        em.detach(updatedAccP);
        updatedAccP.acc(UPDATED_ACC);
        AccPDTO accPDTO = accPMapper.toDto(updatedAccP);

        restAccPMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accPDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accPDTO))
            )
            .andExpect(status().isOk());

        // Validate the AccP in the database
        List<AccP> accPList = accPRepository.findAll();
        assertThat(accPList).hasSize(databaseSizeBeforeUpdate);
        AccP testAccP = accPList.get(accPList.size() - 1);
        assertThat(testAccP.getAcc()).isEqualTo(UPDATED_ACC);

        // Validate the AccP in Elasticsearch
        verify(mockAccPSearchRepository).save(testAccP);
    }

    @Test
    @Transactional
    void putNonExistingAccP() throws Exception {
        int databaseSizeBeforeUpdate = accPRepository.findAll().size();
        accP.setId(count.incrementAndGet());

        // Create the AccP
        AccPDTO accPDTO = accPMapper.toDto(accP);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccPMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accPDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accPDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccP in the database
        List<AccP> accPList = accPRepository.findAll();
        assertThat(accPList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccP in Elasticsearch
        verify(mockAccPSearchRepository, times(0)).save(accP);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccP() throws Exception {
        int databaseSizeBeforeUpdate = accPRepository.findAll().size();
        accP.setId(count.incrementAndGet());

        // Create the AccP
        AccPDTO accPDTO = accPMapper.toDto(accP);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccPMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accPDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccP in the database
        List<AccP> accPList = accPRepository.findAll();
        assertThat(accPList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccP in Elasticsearch
        verify(mockAccPSearchRepository, times(0)).save(accP);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccP() throws Exception {
        int databaseSizeBeforeUpdate = accPRepository.findAll().size();
        accP.setId(count.incrementAndGet());

        // Create the AccP
        AccPDTO accPDTO = accPMapper.toDto(accP);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccPMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accPDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccP in the database
        List<AccP> accPList = accPRepository.findAll();
        assertThat(accPList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccP in Elasticsearch
        verify(mockAccPSearchRepository, times(0)).save(accP);
    }

    @Test
    @Transactional
    void partialUpdateAccPWithPatch() throws Exception {
        // Initialize the database
        accPRepository.saveAndFlush(accP);

        int databaseSizeBeforeUpdate = accPRepository.findAll().size();

        // Update the accP using partial update
        AccP partialUpdatedAccP = new AccP();
        partialUpdatedAccP.setId(accP.getId());

        partialUpdatedAccP.acc(UPDATED_ACC);

        restAccPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccP.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccP))
            )
            .andExpect(status().isOk());

        // Validate the AccP in the database
        List<AccP> accPList = accPRepository.findAll();
        assertThat(accPList).hasSize(databaseSizeBeforeUpdate);
        AccP testAccP = accPList.get(accPList.size() - 1);
        assertThat(testAccP.getAcc()).isEqualTo(UPDATED_ACC);
    }

    @Test
    @Transactional
    void fullUpdateAccPWithPatch() throws Exception {
        // Initialize the database
        accPRepository.saveAndFlush(accP);

        int databaseSizeBeforeUpdate = accPRepository.findAll().size();

        // Update the accP using partial update
        AccP partialUpdatedAccP = new AccP();
        partialUpdatedAccP.setId(accP.getId());

        partialUpdatedAccP.acc(UPDATED_ACC);

        restAccPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccP.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccP))
            )
            .andExpect(status().isOk());

        // Validate the AccP in the database
        List<AccP> accPList = accPRepository.findAll();
        assertThat(accPList).hasSize(databaseSizeBeforeUpdate);
        AccP testAccP = accPList.get(accPList.size() - 1);
        assertThat(testAccP.getAcc()).isEqualTo(UPDATED_ACC);
    }

    @Test
    @Transactional
    void patchNonExistingAccP() throws Exception {
        int databaseSizeBeforeUpdate = accPRepository.findAll().size();
        accP.setId(count.incrementAndGet());

        // Create the AccP
        AccPDTO accPDTO = accPMapper.toDto(accP);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accPDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accPDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccP in the database
        List<AccP> accPList = accPRepository.findAll();
        assertThat(accPList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccP in Elasticsearch
        verify(mockAccPSearchRepository, times(0)).save(accP);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccP() throws Exception {
        int databaseSizeBeforeUpdate = accPRepository.findAll().size();
        accP.setId(count.incrementAndGet());

        // Create the AccP
        AccPDTO accPDTO = accPMapper.toDto(accP);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccPMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accPDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccP in the database
        List<AccP> accPList = accPRepository.findAll();
        assertThat(accPList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccP in Elasticsearch
        verify(mockAccPSearchRepository, times(0)).save(accP);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccP() throws Exception {
        int databaseSizeBeforeUpdate = accPRepository.findAll().size();
        accP.setId(count.incrementAndGet());

        // Create the AccP
        AccPDTO accPDTO = accPMapper.toDto(accP);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccPMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(accPDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccP in the database
        List<AccP> accPList = accPRepository.findAll();
        assertThat(accPList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccP in Elasticsearch
        verify(mockAccPSearchRepository, times(0)).save(accP);
    }

    @Test
    @Transactional
    void deleteAccP() throws Exception {
        // Initialize the database
        accPRepository.saveAndFlush(accP);

        int databaseSizeBeforeDelete = accPRepository.findAll().size();

        // Delete the accP
        restAccPMockMvc
            .perform(delete(ENTITY_API_URL_ID, accP.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccP> accPList = accPRepository.findAll();
        assertThat(accPList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AccP in Elasticsearch
        verify(mockAccPSearchRepository, times(1)).deleteById(accP.getId());
    }

    @Test
    @Transactional
    void searchAccP() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        accPRepository.saveAndFlush(accP);
        when(mockAccPSearchRepository.search(queryStringQuery("id:" + accP.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(accP), PageRequest.of(0, 1), 1));

        // Search the accP
        restAccPMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + accP.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accP.getId().intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)));
    }
}
