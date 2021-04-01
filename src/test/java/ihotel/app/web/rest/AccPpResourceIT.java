package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.AccPp;
import ihotel.app.repository.AccPpRepository;
import ihotel.app.repository.search.AccPpSearchRepository;
import ihotel.app.service.criteria.AccPpCriteria;
import ihotel.app.service.dto.AccPpDTO;
import ihotel.app.service.mapper.AccPpMapper;
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
 * Integration tests for the {@link AccPpResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AccPpResourceIT {

    private static final String DEFAULT_ACC = "AAAAAAAAAA";
    private static final String UPDATED_ACC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/acc-pps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/acc-pps";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccPpRepository accPpRepository;

    @Autowired
    private AccPpMapper accPpMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.AccPpSearchRepositoryMockConfiguration
     */
    @Autowired
    private AccPpSearchRepository mockAccPpSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccPpMockMvc;

    private AccPp accPp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccPp createEntity(EntityManager em) {
        AccPp accPp = new AccPp().acc(DEFAULT_ACC);
        return accPp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccPp createUpdatedEntity(EntityManager em) {
        AccPp accPp = new AccPp().acc(UPDATED_ACC);
        return accPp;
    }

    @BeforeEach
    public void initTest() {
        accPp = createEntity(em);
    }

    @Test
    @Transactional
    void createAccPp() throws Exception {
        int databaseSizeBeforeCreate = accPpRepository.findAll().size();
        // Create the AccPp
        AccPpDTO accPpDTO = accPpMapper.toDto(accPp);
        restAccPpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accPpDTO)))
            .andExpect(status().isCreated());

        // Validate the AccPp in the database
        List<AccPp> accPpList = accPpRepository.findAll();
        assertThat(accPpList).hasSize(databaseSizeBeforeCreate + 1);
        AccPp testAccPp = accPpList.get(accPpList.size() - 1);
        assertThat(testAccPp.getAcc()).isEqualTo(DEFAULT_ACC);

        // Validate the AccPp in Elasticsearch
        verify(mockAccPpSearchRepository, times(1)).save(testAccPp);
    }

    @Test
    @Transactional
    void createAccPpWithExistingId() throws Exception {
        // Create the AccPp with an existing ID
        accPp.setId(1L);
        AccPpDTO accPpDTO = accPpMapper.toDto(accPp);

        int databaseSizeBeforeCreate = accPpRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccPpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accPpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AccPp in the database
        List<AccPp> accPpList = accPpRepository.findAll();
        assertThat(accPpList).hasSize(databaseSizeBeforeCreate);

        // Validate the AccPp in Elasticsearch
        verify(mockAccPpSearchRepository, times(0)).save(accPp);
    }

    @Test
    @Transactional
    void getAllAccPps() throws Exception {
        // Initialize the database
        accPpRepository.saveAndFlush(accPp);

        // Get all the accPpList
        restAccPpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accPp.getId().intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)));
    }

    @Test
    @Transactional
    void getAccPp() throws Exception {
        // Initialize the database
        accPpRepository.saveAndFlush(accPp);

        // Get the accPp
        restAccPpMockMvc
            .perform(get(ENTITY_API_URL_ID, accPp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accPp.getId().intValue()))
            .andExpect(jsonPath("$.acc").value(DEFAULT_ACC));
    }

    @Test
    @Transactional
    void getAccPpsByIdFiltering() throws Exception {
        // Initialize the database
        accPpRepository.saveAndFlush(accPp);

        Long id = accPp.getId();

        defaultAccPpShouldBeFound("id.equals=" + id);
        defaultAccPpShouldNotBeFound("id.notEquals=" + id);

        defaultAccPpShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAccPpShouldNotBeFound("id.greaterThan=" + id);

        defaultAccPpShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAccPpShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAccPpsByAccIsEqualToSomething() throws Exception {
        // Initialize the database
        accPpRepository.saveAndFlush(accPp);

        // Get all the accPpList where acc equals to DEFAULT_ACC
        defaultAccPpShouldBeFound("acc.equals=" + DEFAULT_ACC);

        // Get all the accPpList where acc equals to UPDATED_ACC
        defaultAccPpShouldNotBeFound("acc.equals=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccPpsByAccIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accPpRepository.saveAndFlush(accPp);

        // Get all the accPpList where acc not equals to DEFAULT_ACC
        defaultAccPpShouldNotBeFound("acc.notEquals=" + DEFAULT_ACC);

        // Get all the accPpList where acc not equals to UPDATED_ACC
        defaultAccPpShouldBeFound("acc.notEquals=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccPpsByAccIsInShouldWork() throws Exception {
        // Initialize the database
        accPpRepository.saveAndFlush(accPp);

        // Get all the accPpList where acc in DEFAULT_ACC or UPDATED_ACC
        defaultAccPpShouldBeFound("acc.in=" + DEFAULT_ACC + "," + UPDATED_ACC);

        // Get all the accPpList where acc equals to UPDATED_ACC
        defaultAccPpShouldNotBeFound("acc.in=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccPpsByAccIsNullOrNotNull() throws Exception {
        // Initialize the database
        accPpRepository.saveAndFlush(accPp);

        // Get all the accPpList where acc is not null
        defaultAccPpShouldBeFound("acc.specified=true");

        // Get all the accPpList where acc is null
        defaultAccPpShouldNotBeFound("acc.specified=false");
    }

    @Test
    @Transactional
    void getAllAccPpsByAccContainsSomething() throws Exception {
        // Initialize the database
        accPpRepository.saveAndFlush(accPp);

        // Get all the accPpList where acc contains DEFAULT_ACC
        defaultAccPpShouldBeFound("acc.contains=" + DEFAULT_ACC);

        // Get all the accPpList where acc contains UPDATED_ACC
        defaultAccPpShouldNotBeFound("acc.contains=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccPpsByAccNotContainsSomething() throws Exception {
        // Initialize the database
        accPpRepository.saveAndFlush(accPp);

        // Get all the accPpList where acc does not contain DEFAULT_ACC
        defaultAccPpShouldNotBeFound("acc.doesNotContain=" + DEFAULT_ACC);

        // Get all the accPpList where acc does not contain UPDATED_ACC
        defaultAccPpShouldBeFound("acc.doesNotContain=" + UPDATED_ACC);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccPpShouldBeFound(String filter) throws Exception {
        restAccPpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accPp.getId().intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)));

        // Check, that the count call also returns 1
        restAccPpMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccPpShouldNotBeFound(String filter) throws Exception {
        restAccPpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccPpMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAccPp() throws Exception {
        // Get the accPp
        restAccPpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAccPp() throws Exception {
        // Initialize the database
        accPpRepository.saveAndFlush(accPp);

        int databaseSizeBeforeUpdate = accPpRepository.findAll().size();

        // Update the accPp
        AccPp updatedAccPp = accPpRepository.findById(accPp.getId()).get();
        // Disconnect from session so that the updates on updatedAccPp are not directly saved in db
        em.detach(updatedAccPp);
        updatedAccPp.acc(UPDATED_ACC);
        AccPpDTO accPpDTO = accPpMapper.toDto(updatedAccPp);

        restAccPpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accPpDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accPpDTO))
            )
            .andExpect(status().isOk());

        // Validate the AccPp in the database
        List<AccPp> accPpList = accPpRepository.findAll();
        assertThat(accPpList).hasSize(databaseSizeBeforeUpdate);
        AccPp testAccPp = accPpList.get(accPpList.size() - 1);
        assertThat(testAccPp.getAcc()).isEqualTo(UPDATED_ACC);

        // Validate the AccPp in Elasticsearch
        verify(mockAccPpSearchRepository).save(testAccPp);
    }

    @Test
    @Transactional
    void putNonExistingAccPp() throws Exception {
        int databaseSizeBeforeUpdate = accPpRepository.findAll().size();
        accPp.setId(count.incrementAndGet());

        // Create the AccPp
        AccPpDTO accPpDTO = accPpMapper.toDto(accPp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccPpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accPpDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accPpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccPp in the database
        List<AccPp> accPpList = accPpRepository.findAll();
        assertThat(accPpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccPp in Elasticsearch
        verify(mockAccPpSearchRepository, times(0)).save(accPp);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccPp() throws Exception {
        int databaseSizeBeforeUpdate = accPpRepository.findAll().size();
        accPp.setId(count.incrementAndGet());

        // Create the AccPp
        AccPpDTO accPpDTO = accPpMapper.toDto(accPp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccPpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accPpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccPp in the database
        List<AccPp> accPpList = accPpRepository.findAll();
        assertThat(accPpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccPp in Elasticsearch
        verify(mockAccPpSearchRepository, times(0)).save(accPp);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccPp() throws Exception {
        int databaseSizeBeforeUpdate = accPpRepository.findAll().size();
        accPp.setId(count.incrementAndGet());

        // Create the AccPp
        AccPpDTO accPpDTO = accPpMapper.toDto(accPp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccPpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accPpDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccPp in the database
        List<AccPp> accPpList = accPpRepository.findAll();
        assertThat(accPpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccPp in Elasticsearch
        verify(mockAccPpSearchRepository, times(0)).save(accPp);
    }

    @Test
    @Transactional
    void partialUpdateAccPpWithPatch() throws Exception {
        // Initialize the database
        accPpRepository.saveAndFlush(accPp);

        int databaseSizeBeforeUpdate = accPpRepository.findAll().size();

        // Update the accPp using partial update
        AccPp partialUpdatedAccPp = new AccPp();
        partialUpdatedAccPp.setId(accPp.getId());

        restAccPpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccPp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccPp))
            )
            .andExpect(status().isOk());

        // Validate the AccPp in the database
        List<AccPp> accPpList = accPpRepository.findAll();
        assertThat(accPpList).hasSize(databaseSizeBeforeUpdate);
        AccPp testAccPp = accPpList.get(accPpList.size() - 1);
        assertThat(testAccPp.getAcc()).isEqualTo(DEFAULT_ACC);
    }

    @Test
    @Transactional
    void fullUpdateAccPpWithPatch() throws Exception {
        // Initialize the database
        accPpRepository.saveAndFlush(accPp);

        int databaseSizeBeforeUpdate = accPpRepository.findAll().size();

        // Update the accPp using partial update
        AccPp partialUpdatedAccPp = new AccPp();
        partialUpdatedAccPp.setId(accPp.getId());

        partialUpdatedAccPp.acc(UPDATED_ACC);

        restAccPpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccPp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccPp))
            )
            .andExpect(status().isOk());

        // Validate the AccPp in the database
        List<AccPp> accPpList = accPpRepository.findAll();
        assertThat(accPpList).hasSize(databaseSizeBeforeUpdate);
        AccPp testAccPp = accPpList.get(accPpList.size() - 1);
        assertThat(testAccPp.getAcc()).isEqualTo(UPDATED_ACC);
    }

    @Test
    @Transactional
    void patchNonExistingAccPp() throws Exception {
        int databaseSizeBeforeUpdate = accPpRepository.findAll().size();
        accPp.setId(count.incrementAndGet());

        // Create the AccPp
        AccPpDTO accPpDTO = accPpMapper.toDto(accPp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccPpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accPpDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accPpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccPp in the database
        List<AccPp> accPpList = accPpRepository.findAll();
        assertThat(accPpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccPp in Elasticsearch
        verify(mockAccPpSearchRepository, times(0)).save(accPp);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccPp() throws Exception {
        int databaseSizeBeforeUpdate = accPpRepository.findAll().size();
        accPp.setId(count.incrementAndGet());

        // Create the AccPp
        AccPpDTO accPpDTO = accPpMapper.toDto(accPp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccPpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accPpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the AccPp in the database
        List<AccPp> accPpList = accPpRepository.findAll();
        assertThat(accPpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccPp in Elasticsearch
        verify(mockAccPpSearchRepository, times(0)).save(accPp);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccPp() throws Exception {
        int databaseSizeBeforeUpdate = accPpRepository.findAll().size();
        accPp.setId(count.incrementAndGet());

        // Create the AccPp
        AccPpDTO accPpDTO = accPpMapper.toDto(accPp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccPpMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(accPpDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AccPp in the database
        List<AccPp> accPpList = accPpRepository.findAll();
        assertThat(accPpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AccPp in Elasticsearch
        verify(mockAccPpSearchRepository, times(0)).save(accPp);
    }

    @Test
    @Transactional
    void deleteAccPp() throws Exception {
        // Initialize the database
        accPpRepository.saveAndFlush(accPp);

        int databaseSizeBeforeDelete = accPpRepository.findAll().size();

        // Delete the accPp
        restAccPpMockMvc
            .perform(delete(ENTITY_API_URL_ID, accPp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccPp> accPpList = accPpRepository.findAll();
        assertThat(accPpList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AccPp in Elasticsearch
        verify(mockAccPpSearchRepository, times(1)).deleteById(accPp.getId());
    }

    @Test
    @Transactional
    void searchAccPp() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        accPpRepository.saveAndFlush(accPp);
        when(mockAccPpSearchRepository.search(queryStringQuery("id:" + accPp.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(accPp), PageRequest.of(0, 1), 1));

        // Search the accPp
        restAccPpMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + accPp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accPp.getId().intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)));
    }
}
