package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.FtXz;
import ihotel.app.repository.FtXzRepository;
import ihotel.app.repository.search.FtXzSearchRepository;
import ihotel.app.service.criteria.FtXzCriteria;
import ihotel.app.service.dto.FtXzDTO;
import ihotel.app.service.mapper.FtXzMapper;
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
 * Integration tests for the {@link FtXzResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FtXzResourceIT {

    private static final String DEFAULT_ROOMN = "AAAAAAAAAA";
    private static final String UPDATED_ROOMN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ft-xzs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/ft-xzs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FtXzRepository ftXzRepository;

    @Autowired
    private FtXzMapper ftXzMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.FtXzSearchRepositoryMockConfiguration
     */
    @Autowired
    private FtXzSearchRepository mockFtXzSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFtXzMockMvc;

    private FtXz ftXz;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FtXz createEntity(EntityManager em) {
        FtXz ftXz = new FtXz().roomn(DEFAULT_ROOMN);
        return ftXz;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FtXz createUpdatedEntity(EntityManager em) {
        FtXz ftXz = new FtXz().roomn(UPDATED_ROOMN);
        return ftXz;
    }

    @BeforeEach
    public void initTest() {
        ftXz = createEntity(em);
    }

    @Test
    @Transactional
    void createFtXz() throws Exception {
        int databaseSizeBeforeCreate = ftXzRepository.findAll().size();
        // Create the FtXz
        FtXzDTO ftXzDTO = ftXzMapper.toDto(ftXz);
        restFtXzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ftXzDTO)))
            .andExpect(status().isCreated());

        // Validate the FtXz in the database
        List<FtXz> ftXzList = ftXzRepository.findAll();
        assertThat(ftXzList).hasSize(databaseSizeBeforeCreate + 1);
        FtXz testFtXz = ftXzList.get(ftXzList.size() - 1);
        assertThat(testFtXz.getRoomn()).isEqualTo(DEFAULT_ROOMN);

        // Validate the FtXz in Elasticsearch
        verify(mockFtXzSearchRepository, times(1)).save(testFtXz);
    }

    @Test
    @Transactional
    void createFtXzWithExistingId() throws Exception {
        // Create the FtXz with an existing ID
        ftXz.setId(1L);
        FtXzDTO ftXzDTO = ftXzMapper.toDto(ftXz);

        int databaseSizeBeforeCreate = ftXzRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFtXzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ftXzDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FtXz in the database
        List<FtXz> ftXzList = ftXzRepository.findAll();
        assertThat(ftXzList).hasSize(databaseSizeBeforeCreate);

        // Validate the FtXz in Elasticsearch
        verify(mockFtXzSearchRepository, times(0)).save(ftXz);
    }

    @Test
    @Transactional
    void getAllFtXzs() throws Exception {
        // Initialize the database
        ftXzRepository.saveAndFlush(ftXz);

        // Get all the ftXzList
        restFtXzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ftXz.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)));
    }

    @Test
    @Transactional
    void getFtXz() throws Exception {
        // Initialize the database
        ftXzRepository.saveAndFlush(ftXz);

        // Get the ftXz
        restFtXzMockMvc
            .perform(get(ENTITY_API_URL_ID, ftXz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ftXz.getId().intValue()))
            .andExpect(jsonPath("$.roomn").value(DEFAULT_ROOMN));
    }

    @Test
    @Transactional
    void getFtXzsByIdFiltering() throws Exception {
        // Initialize the database
        ftXzRepository.saveAndFlush(ftXz);

        Long id = ftXz.getId();

        defaultFtXzShouldBeFound("id.equals=" + id);
        defaultFtXzShouldNotBeFound("id.notEquals=" + id);

        defaultFtXzShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFtXzShouldNotBeFound("id.greaterThan=" + id);

        defaultFtXzShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFtXzShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFtXzsByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        ftXzRepository.saveAndFlush(ftXz);

        // Get all the ftXzList where roomn equals to DEFAULT_ROOMN
        defaultFtXzShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the ftXzList where roomn equals to UPDATED_ROOMN
        defaultFtXzShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFtXzsByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ftXzRepository.saveAndFlush(ftXz);

        // Get all the ftXzList where roomn not equals to DEFAULT_ROOMN
        defaultFtXzShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the ftXzList where roomn not equals to UPDATED_ROOMN
        defaultFtXzShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFtXzsByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        ftXzRepository.saveAndFlush(ftXz);

        // Get all the ftXzList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultFtXzShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the ftXzList where roomn equals to UPDATED_ROOMN
        defaultFtXzShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFtXzsByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        ftXzRepository.saveAndFlush(ftXz);

        // Get all the ftXzList where roomn is not null
        defaultFtXzShouldBeFound("roomn.specified=true");

        // Get all the ftXzList where roomn is null
        defaultFtXzShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllFtXzsByRoomnContainsSomething() throws Exception {
        // Initialize the database
        ftXzRepository.saveAndFlush(ftXz);

        // Get all the ftXzList where roomn contains DEFAULT_ROOMN
        defaultFtXzShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the ftXzList where roomn contains UPDATED_ROOMN
        defaultFtXzShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFtXzsByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        ftXzRepository.saveAndFlush(ftXz);

        // Get all the ftXzList where roomn does not contain DEFAULT_ROOMN
        defaultFtXzShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the ftXzList where roomn does not contain UPDATED_ROOMN
        defaultFtXzShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFtXzShouldBeFound(String filter) throws Exception {
        restFtXzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ftXz.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)));

        // Check, that the count call also returns 1
        restFtXzMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFtXzShouldNotBeFound(String filter) throws Exception {
        restFtXzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFtXzMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFtXz() throws Exception {
        // Get the ftXz
        restFtXzMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFtXz() throws Exception {
        // Initialize the database
        ftXzRepository.saveAndFlush(ftXz);

        int databaseSizeBeforeUpdate = ftXzRepository.findAll().size();

        // Update the ftXz
        FtXz updatedFtXz = ftXzRepository.findById(ftXz.getId()).get();
        // Disconnect from session so that the updates on updatedFtXz are not directly saved in db
        em.detach(updatedFtXz);
        updatedFtXz.roomn(UPDATED_ROOMN);
        FtXzDTO ftXzDTO = ftXzMapper.toDto(updatedFtXz);

        restFtXzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ftXzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ftXzDTO))
            )
            .andExpect(status().isOk());

        // Validate the FtXz in the database
        List<FtXz> ftXzList = ftXzRepository.findAll();
        assertThat(ftXzList).hasSize(databaseSizeBeforeUpdate);
        FtXz testFtXz = ftXzList.get(ftXzList.size() - 1);
        assertThat(testFtXz.getRoomn()).isEqualTo(UPDATED_ROOMN);

        // Validate the FtXz in Elasticsearch
        verify(mockFtXzSearchRepository).save(testFtXz);
    }

    @Test
    @Transactional
    void putNonExistingFtXz() throws Exception {
        int databaseSizeBeforeUpdate = ftXzRepository.findAll().size();
        ftXz.setId(count.incrementAndGet());

        // Create the FtXz
        FtXzDTO ftXzDTO = ftXzMapper.toDto(ftXz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFtXzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ftXzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ftXzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FtXz in the database
        List<FtXz> ftXzList = ftXzRepository.findAll();
        assertThat(ftXzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FtXz in Elasticsearch
        verify(mockFtXzSearchRepository, times(0)).save(ftXz);
    }

    @Test
    @Transactional
    void putWithIdMismatchFtXz() throws Exception {
        int databaseSizeBeforeUpdate = ftXzRepository.findAll().size();
        ftXz.setId(count.incrementAndGet());

        // Create the FtXz
        FtXzDTO ftXzDTO = ftXzMapper.toDto(ftXz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFtXzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ftXzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FtXz in the database
        List<FtXz> ftXzList = ftXzRepository.findAll();
        assertThat(ftXzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FtXz in Elasticsearch
        verify(mockFtXzSearchRepository, times(0)).save(ftXz);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFtXz() throws Exception {
        int databaseSizeBeforeUpdate = ftXzRepository.findAll().size();
        ftXz.setId(count.incrementAndGet());

        // Create the FtXz
        FtXzDTO ftXzDTO = ftXzMapper.toDto(ftXz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFtXzMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ftXzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FtXz in the database
        List<FtXz> ftXzList = ftXzRepository.findAll();
        assertThat(ftXzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FtXz in Elasticsearch
        verify(mockFtXzSearchRepository, times(0)).save(ftXz);
    }

    @Test
    @Transactional
    void partialUpdateFtXzWithPatch() throws Exception {
        // Initialize the database
        ftXzRepository.saveAndFlush(ftXz);

        int databaseSizeBeforeUpdate = ftXzRepository.findAll().size();

        // Update the ftXz using partial update
        FtXz partialUpdatedFtXz = new FtXz();
        partialUpdatedFtXz.setId(ftXz.getId());

        partialUpdatedFtXz.roomn(UPDATED_ROOMN);

        restFtXzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFtXz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFtXz))
            )
            .andExpect(status().isOk());

        // Validate the FtXz in the database
        List<FtXz> ftXzList = ftXzRepository.findAll();
        assertThat(ftXzList).hasSize(databaseSizeBeforeUpdate);
        FtXz testFtXz = ftXzList.get(ftXzList.size() - 1);
        assertThat(testFtXz.getRoomn()).isEqualTo(UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void fullUpdateFtXzWithPatch() throws Exception {
        // Initialize the database
        ftXzRepository.saveAndFlush(ftXz);

        int databaseSizeBeforeUpdate = ftXzRepository.findAll().size();

        // Update the ftXz using partial update
        FtXz partialUpdatedFtXz = new FtXz();
        partialUpdatedFtXz.setId(ftXz.getId());

        partialUpdatedFtXz.roomn(UPDATED_ROOMN);

        restFtXzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFtXz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFtXz))
            )
            .andExpect(status().isOk());

        // Validate the FtXz in the database
        List<FtXz> ftXzList = ftXzRepository.findAll();
        assertThat(ftXzList).hasSize(databaseSizeBeforeUpdate);
        FtXz testFtXz = ftXzList.get(ftXzList.size() - 1);
        assertThat(testFtXz.getRoomn()).isEqualTo(UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void patchNonExistingFtXz() throws Exception {
        int databaseSizeBeforeUpdate = ftXzRepository.findAll().size();
        ftXz.setId(count.incrementAndGet());

        // Create the FtXz
        FtXzDTO ftXzDTO = ftXzMapper.toDto(ftXz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFtXzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ftXzDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ftXzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FtXz in the database
        List<FtXz> ftXzList = ftXzRepository.findAll();
        assertThat(ftXzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FtXz in Elasticsearch
        verify(mockFtXzSearchRepository, times(0)).save(ftXz);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFtXz() throws Exception {
        int databaseSizeBeforeUpdate = ftXzRepository.findAll().size();
        ftXz.setId(count.incrementAndGet());

        // Create the FtXz
        FtXzDTO ftXzDTO = ftXzMapper.toDto(ftXz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFtXzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ftXzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FtXz in the database
        List<FtXz> ftXzList = ftXzRepository.findAll();
        assertThat(ftXzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FtXz in Elasticsearch
        verify(mockFtXzSearchRepository, times(0)).save(ftXz);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFtXz() throws Exception {
        int databaseSizeBeforeUpdate = ftXzRepository.findAll().size();
        ftXz.setId(count.incrementAndGet());

        // Create the FtXz
        FtXzDTO ftXzDTO = ftXzMapper.toDto(ftXz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFtXzMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ftXzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FtXz in the database
        List<FtXz> ftXzList = ftXzRepository.findAll();
        assertThat(ftXzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FtXz in Elasticsearch
        verify(mockFtXzSearchRepository, times(0)).save(ftXz);
    }

    @Test
    @Transactional
    void deleteFtXz() throws Exception {
        // Initialize the database
        ftXzRepository.saveAndFlush(ftXz);

        int databaseSizeBeforeDelete = ftXzRepository.findAll().size();

        // Delete the ftXz
        restFtXzMockMvc
            .perform(delete(ENTITY_API_URL_ID, ftXz.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FtXz> ftXzList = ftXzRepository.findAll();
        assertThat(ftXzList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FtXz in Elasticsearch
        verify(mockFtXzSearchRepository, times(1)).deleteById(ftXz.getId());
    }

    @Test
    @Transactional
    void searchFtXz() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        ftXzRepository.saveAndFlush(ftXz);
        when(mockFtXzSearchRepository.search(queryStringQuery("id:" + ftXz.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ftXz), PageRequest.of(0, 1), 1));

        // Search the ftXz
        restFtXzMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ftXz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ftXz.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)));
    }
}
