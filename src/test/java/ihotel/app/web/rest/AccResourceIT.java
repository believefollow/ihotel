package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Acc;
import ihotel.app.repository.AccRepository;
import ihotel.app.repository.search.AccSearchRepository;
import ihotel.app.service.criteria.AccCriteria;
import ihotel.app.service.dto.AccDTO;
import ihotel.app.service.mapper.AccMapper;
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
 * Integration tests for the {@link AccResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AccResourceIT {

    private static final String DEFAULT_ACC = "AAAAAAAAAA";
    private static final String UPDATED_ACC = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/accs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/accs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccRepository accRepository;

    @Autowired
    private AccMapper accMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.AccSearchRepositoryMockConfiguration
     */
    @Autowired
    private AccSearchRepository mockAccSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccMockMvc;

    private Acc acc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acc createEntity(EntityManager em) {
        Acc acc = new Acc().acc(DEFAULT_ACC);
        return acc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acc createUpdatedEntity(EntityManager em) {
        Acc acc = new Acc().acc(UPDATED_ACC);
        return acc;
    }

    @BeforeEach
    public void initTest() {
        acc = createEntity(em);
    }

    @Test
    @Transactional
    void createAcc() throws Exception {
        int databaseSizeBeforeCreate = accRepository.findAll().size();
        // Create the Acc
        AccDTO accDTO = accMapper.toDto(acc);
        restAccMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accDTO)))
            .andExpect(status().isCreated());

        // Validate the Acc in the database
        List<Acc> accList = accRepository.findAll();
        assertThat(accList).hasSize(databaseSizeBeforeCreate + 1);
        Acc testAcc = accList.get(accList.size() - 1);
        assertThat(testAcc.getAcc()).isEqualTo(DEFAULT_ACC);

        // Validate the Acc in Elasticsearch
        verify(mockAccSearchRepository, times(1)).save(testAcc);
    }

    @Test
    @Transactional
    void createAccWithExistingId() throws Exception {
        // Create the Acc with an existing ID
        acc.setId(1L);
        AccDTO accDTO = accMapper.toDto(acc);

        int databaseSizeBeforeCreate = accRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Acc in the database
        List<Acc> accList = accRepository.findAll();
        assertThat(accList).hasSize(databaseSizeBeforeCreate);

        // Validate the Acc in Elasticsearch
        verify(mockAccSearchRepository, times(0)).save(acc);
    }

    @Test
    @Transactional
    void getAllAccs() throws Exception {
        // Initialize the database
        accRepository.saveAndFlush(acc);

        // Get all the accList
        restAccMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acc.getId().intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)));
    }

    @Test
    @Transactional
    void getAcc() throws Exception {
        // Initialize the database
        accRepository.saveAndFlush(acc);

        // Get the acc
        restAccMockMvc
            .perform(get(ENTITY_API_URL_ID, acc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(acc.getId().intValue()))
            .andExpect(jsonPath("$.acc").value(DEFAULT_ACC));
    }

    @Test
    @Transactional
    void getAccsByIdFiltering() throws Exception {
        // Initialize the database
        accRepository.saveAndFlush(acc);

        Long id = acc.getId();

        defaultAccShouldBeFound("id.equals=" + id);
        defaultAccShouldNotBeFound("id.notEquals=" + id);

        defaultAccShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAccShouldNotBeFound("id.greaterThan=" + id);

        defaultAccShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAccShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAccsByAccIsEqualToSomething() throws Exception {
        // Initialize the database
        accRepository.saveAndFlush(acc);

        // Get all the accList where acc equals to DEFAULT_ACC
        defaultAccShouldBeFound("acc.equals=" + DEFAULT_ACC);

        // Get all the accList where acc equals to UPDATED_ACC
        defaultAccShouldNotBeFound("acc.equals=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccsByAccIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accRepository.saveAndFlush(acc);

        // Get all the accList where acc not equals to DEFAULT_ACC
        defaultAccShouldNotBeFound("acc.notEquals=" + DEFAULT_ACC);

        // Get all the accList where acc not equals to UPDATED_ACC
        defaultAccShouldBeFound("acc.notEquals=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccsByAccIsInShouldWork() throws Exception {
        // Initialize the database
        accRepository.saveAndFlush(acc);

        // Get all the accList where acc in DEFAULT_ACC or UPDATED_ACC
        defaultAccShouldBeFound("acc.in=" + DEFAULT_ACC + "," + UPDATED_ACC);

        // Get all the accList where acc equals to UPDATED_ACC
        defaultAccShouldNotBeFound("acc.in=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccsByAccIsNullOrNotNull() throws Exception {
        // Initialize the database
        accRepository.saveAndFlush(acc);

        // Get all the accList where acc is not null
        defaultAccShouldBeFound("acc.specified=true");

        // Get all the accList where acc is null
        defaultAccShouldNotBeFound("acc.specified=false");
    }

    @Test
    @Transactional
    void getAllAccsByAccContainsSomething() throws Exception {
        // Initialize the database
        accRepository.saveAndFlush(acc);

        // Get all the accList where acc contains DEFAULT_ACC
        defaultAccShouldBeFound("acc.contains=" + DEFAULT_ACC);

        // Get all the accList where acc contains UPDATED_ACC
        defaultAccShouldNotBeFound("acc.contains=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccsByAccNotContainsSomething() throws Exception {
        // Initialize the database
        accRepository.saveAndFlush(acc);

        // Get all the accList where acc does not contain DEFAULT_ACC
        defaultAccShouldNotBeFound("acc.doesNotContain=" + DEFAULT_ACC);

        // Get all the accList where acc does not contain UPDATED_ACC
        defaultAccShouldBeFound("acc.doesNotContain=" + UPDATED_ACC);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccShouldBeFound(String filter) throws Exception {
        restAccMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acc.getId().intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)));

        // Check, that the count call also returns 1
        restAccMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccShouldNotBeFound(String filter) throws Exception {
        restAccMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAcc() throws Exception {
        // Get the acc
        restAccMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAcc() throws Exception {
        // Initialize the database
        accRepository.saveAndFlush(acc);

        int databaseSizeBeforeUpdate = accRepository.findAll().size();

        // Update the acc
        Acc updatedAcc = accRepository.findById(acc.getId()).get();
        // Disconnect from session so that the updates on updatedAcc are not directly saved in db
        em.detach(updatedAcc);
        updatedAcc.acc(UPDATED_ACC);
        AccDTO accDTO = accMapper.toDto(updatedAcc);

        restAccMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accDTO))
            )
            .andExpect(status().isOk());

        // Validate the Acc in the database
        List<Acc> accList = accRepository.findAll();
        assertThat(accList).hasSize(databaseSizeBeforeUpdate);
        Acc testAcc = accList.get(accList.size() - 1);
        assertThat(testAcc.getAcc()).isEqualTo(UPDATED_ACC);

        // Validate the Acc in Elasticsearch
        verify(mockAccSearchRepository).save(testAcc);
    }

    @Test
    @Transactional
    void putNonExistingAcc() throws Exception {
        int databaseSizeBeforeUpdate = accRepository.findAll().size();
        acc.setId(count.incrementAndGet());

        // Create the Acc
        AccDTO accDTO = accMapper.toDto(acc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acc in the database
        List<Acc> accList = accRepository.findAll();
        assertThat(accList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Acc in Elasticsearch
        verify(mockAccSearchRepository, times(0)).save(acc);
    }

    @Test
    @Transactional
    void putWithIdMismatchAcc() throws Exception {
        int databaseSizeBeforeUpdate = accRepository.findAll().size();
        acc.setId(count.incrementAndGet());

        // Create the Acc
        AccDTO accDTO = accMapper.toDto(acc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acc in the database
        List<Acc> accList = accRepository.findAll();
        assertThat(accList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Acc in Elasticsearch
        verify(mockAccSearchRepository, times(0)).save(acc);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAcc() throws Exception {
        int databaseSizeBeforeUpdate = accRepository.findAll().size();
        acc.setId(count.incrementAndGet());

        // Create the Acc
        AccDTO accDTO = accMapper.toDto(acc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Acc in the database
        List<Acc> accList = accRepository.findAll();
        assertThat(accList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Acc in Elasticsearch
        verify(mockAccSearchRepository, times(0)).save(acc);
    }

    @Test
    @Transactional
    void partialUpdateAccWithPatch() throws Exception {
        // Initialize the database
        accRepository.saveAndFlush(acc);

        int databaseSizeBeforeUpdate = accRepository.findAll().size();

        // Update the acc using partial update
        Acc partialUpdatedAcc = new Acc();
        partialUpdatedAcc.setId(acc.getId());

        partialUpdatedAcc.acc(UPDATED_ACC);

        restAccMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcc))
            )
            .andExpect(status().isOk());

        // Validate the Acc in the database
        List<Acc> accList = accRepository.findAll();
        assertThat(accList).hasSize(databaseSizeBeforeUpdate);
        Acc testAcc = accList.get(accList.size() - 1);
        assertThat(testAcc.getAcc()).isEqualTo(UPDATED_ACC);
    }

    @Test
    @Transactional
    void fullUpdateAccWithPatch() throws Exception {
        // Initialize the database
        accRepository.saveAndFlush(acc);

        int databaseSizeBeforeUpdate = accRepository.findAll().size();

        // Update the acc using partial update
        Acc partialUpdatedAcc = new Acc();
        partialUpdatedAcc.setId(acc.getId());

        partialUpdatedAcc.acc(UPDATED_ACC);

        restAccMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAcc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAcc))
            )
            .andExpect(status().isOk());

        // Validate the Acc in the database
        List<Acc> accList = accRepository.findAll();
        assertThat(accList).hasSize(databaseSizeBeforeUpdate);
        Acc testAcc = accList.get(accList.size() - 1);
        assertThat(testAcc.getAcc()).isEqualTo(UPDATED_ACC);
    }

    @Test
    @Transactional
    void patchNonExistingAcc() throws Exception {
        int databaseSizeBeforeUpdate = accRepository.findAll().size();
        acc.setId(count.incrementAndGet());

        // Create the Acc
        AccDTO accDTO = accMapper.toDto(acc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acc in the database
        List<Acc> accList = accRepository.findAll();
        assertThat(accList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Acc in Elasticsearch
        verify(mockAccSearchRepository, times(0)).save(acc);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAcc() throws Exception {
        int databaseSizeBeforeUpdate = accRepository.findAll().size();
        acc.setId(count.incrementAndGet());

        // Create the Acc
        AccDTO accDTO = accMapper.toDto(acc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Acc in the database
        List<Acc> accList = accRepository.findAll();
        assertThat(accList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Acc in Elasticsearch
        verify(mockAccSearchRepository, times(0)).save(acc);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAcc() throws Exception {
        int databaseSizeBeforeUpdate = accRepository.findAll().size();
        acc.setId(count.incrementAndGet());

        // Create the Acc
        AccDTO accDTO = accMapper.toDto(acc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(accDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Acc in the database
        List<Acc> accList = accRepository.findAll();
        assertThat(accList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Acc in Elasticsearch
        verify(mockAccSearchRepository, times(0)).save(acc);
    }

    @Test
    @Transactional
    void deleteAcc() throws Exception {
        // Initialize the database
        accRepository.saveAndFlush(acc);

        int databaseSizeBeforeDelete = accRepository.findAll().size();

        // Delete the acc
        restAccMockMvc.perform(delete(ENTITY_API_URL_ID, acc.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Acc> accList = accRepository.findAll();
        assertThat(accList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Acc in Elasticsearch
        verify(mockAccSearchRepository, times(1)).deleteById(acc.getId());
    }

    @Test
    @Transactional
    void searchAcc() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        accRepository.saveAndFlush(acc);
        when(mockAccSearchRepository.search(queryStringQuery("id:" + acc.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(acc), PageRequest.of(0, 1), 1));

        // Search the acc
        restAccMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + acc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acc.getId().intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)));
    }
}
