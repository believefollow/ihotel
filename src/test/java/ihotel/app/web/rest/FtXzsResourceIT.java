package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.FtXzs;
import ihotel.app.repository.FtXzsRepository;
import ihotel.app.repository.search.FtXzsSearchRepository;
import ihotel.app.service.criteria.FtXzsCriteria;
import ihotel.app.service.dto.FtXzsDTO;
import ihotel.app.service.mapper.FtXzsMapper;
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
 * Integration tests for the {@link FtXzsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FtXzsResourceIT {

    private static final String DEFAULT_ROOMN = "AAAAAAAAAA";
    private static final String UPDATED_ROOMN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/ft-xzs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/ft-xzs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FtXzsRepository ftXzsRepository;

    @Autowired
    private FtXzsMapper ftXzsMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.FtXzsSearchRepositoryMockConfiguration
     */
    @Autowired
    private FtXzsSearchRepository mockFtXzsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFtXzsMockMvc;

    private FtXzs ftXzs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FtXzs createEntity(EntityManager em) {
        FtXzs ftXzs = new FtXzs().roomn(DEFAULT_ROOMN);
        return ftXzs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FtXzs createUpdatedEntity(EntityManager em) {
        FtXzs ftXzs = new FtXzs().roomn(UPDATED_ROOMN);
        return ftXzs;
    }

    @BeforeEach
    public void initTest() {
        ftXzs = createEntity(em);
    }

    @Test
    @Transactional
    void createFtXzs() throws Exception {
        int databaseSizeBeforeCreate = ftXzsRepository.findAll().size();
        // Create the FtXzs
        FtXzsDTO ftXzsDTO = ftXzsMapper.toDto(ftXzs);
        restFtXzsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ftXzsDTO)))
            .andExpect(status().isCreated());

        // Validate the FtXzs in the database
        List<FtXzs> ftXzsList = ftXzsRepository.findAll();
        assertThat(ftXzsList).hasSize(databaseSizeBeforeCreate + 1);
        FtXzs testFtXzs = ftXzsList.get(ftXzsList.size() - 1);
        assertThat(testFtXzs.getRoomn()).isEqualTo(DEFAULT_ROOMN);

        // Validate the FtXzs in Elasticsearch
        verify(mockFtXzsSearchRepository, times(1)).save(testFtXzs);
    }

    @Test
    @Transactional
    void createFtXzsWithExistingId() throws Exception {
        // Create the FtXzs with an existing ID
        ftXzs.setId(1L);
        FtXzsDTO ftXzsDTO = ftXzsMapper.toDto(ftXzs);

        int databaseSizeBeforeCreate = ftXzsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFtXzsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ftXzsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FtXzs in the database
        List<FtXzs> ftXzsList = ftXzsRepository.findAll();
        assertThat(ftXzsList).hasSize(databaseSizeBeforeCreate);

        // Validate the FtXzs in Elasticsearch
        verify(mockFtXzsSearchRepository, times(0)).save(ftXzs);
    }

    @Test
    @Transactional
    void getAllFtXzs() throws Exception {
        // Initialize the database
        ftXzsRepository.saveAndFlush(ftXzs);

        // Get all the ftXzsList
        restFtXzsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ftXzs.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)));
    }

    @Test
    @Transactional
    void getFtXzs() throws Exception {
        // Initialize the database
        ftXzsRepository.saveAndFlush(ftXzs);

        // Get the ftXzs
        restFtXzsMockMvc
            .perform(get(ENTITY_API_URL_ID, ftXzs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(ftXzs.getId().intValue()))
            .andExpect(jsonPath("$.roomn").value(DEFAULT_ROOMN));
    }

    @Test
    @Transactional
    void getFtXzsByIdFiltering() throws Exception {
        // Initialize the database
        ftXzsRepository.saveAndFlush(ftXzs);

        Long id = ftXzs.getId();

        defaultFtXzsShouldBeFound("id.equals=" + id);
        defaultFtXzsShouldNotBeFound("id.notEquals=" + id);

        defaultFtXzsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFtXzsShouldNotBeFound("id.greaterThan=" + id);

        defaultFtXzsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFtXzsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFtXzsByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        ftXzsRepository.saveAndFlush(ftXzs);

        // Get all the ftXzsList where roomn equals to DEFAULT_ROOMN
        defaultFtXzsShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the ftXzsList where roomn equals to UPDATED_ROOMN
        defaultFtXzsShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFtXzsByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        ftXzsRepository.saveAndFlush(ftXzs);

        // Get all the ftXzsList where roomn not equals to DEFAULT_ROOMN
        defaultFtXzsShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the ftXzsList where roomn not equals to UPDATED_ROOMN
        defaultFtXzsShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFtXzsByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        ftXzsRepository.saveAndFlush(ftXzs);

        // Get all the ftXzsList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultFtXzsShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the ftXzsList where roomn equals to UPDATED_ROOMN
        defaultFtXzsShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFtXzsByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        ftXzsRepository.saveAndFlush(ftXzs);

        // Get all the ftXzsList where roomn is not null
        defaultFtXzsShouldBeFound("roomn.specified=true");

        // Get all the ftXzsList where roomn is null
        defaultFtXzsShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllFtXzsByRoomnContainsSomething() throws Exception {
        // Initialize the database
        ftXzsRepository.saveAndFlush(ftXzs);

        // Get all the ftXzsList where roomn contains DEFAULT_ROOMN
        defaultFtXzsShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the ftXzsList where roomn contains UPDATED_ROOMN
        defaultFtXzsShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFtXzsByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        ftXzsRepository.saveAndFlush(ftXzs);

        // Get all the ftXzsList where roomn does not contain DEFAULT_ROOMN
        defaultFtXzsShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the ftXzsList where roomn does not contain UPDATED_ROOMN
        defaultFtXzsShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFtXzsShouldBeFound(String filter) throws Exception {
        restFtXzsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ftXzs.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)));

        // Check, that the count call also returns 1
        restFtXzsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFtXzsShouldNotBeFound(String filter) throws Exception {
        restFtXzsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFtXzsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFtXzs() throws Exception {
        // Get the ftXzs
        restFtXzsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFtXzs() throws Exception {
        // Initialize the database
        ftXzsRepository.saveAndFlush(ftXzs);

        int databaseSizeBeforeUpdate = ftXzsRepository.findAll().size();

        // Update the ftXzs
        FtXzs updatedFtXzs = ftXzsRepository.findById(ftXzs.getId()).get();
        // Disconnect from session so that the updates on updatedFtXzs are not directly saved in db
        em.detach(updatedFtXzs);
        updatedFtXzs.roomn(UPDATED_ROOMN);
        FtXzsDTO ftXzsDTO = ftXzsMapper.toDto(updatedFtXzs);

        restFtXzsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ftXzsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ftXzsDTO))
            )
            .andExpect(status().isOk());

        // Validate the FtXzs in the database
        List<FtXzs> ftXzsList = ftXzsRepository.findAll();
        assertThat(ftXzsList).hasSize(databaseSizeBeforeUpdate);
        FtXzs testFtXzs = ftXzsList.get(ftXzsList.size() - 1);
        assertThat(testFtXzs.getRoomn()).isEqualTo(UPDATED_ROOMN);

        // Validate the FtXzs in Elasticsearch
        verify(mockFtXzsSearchRepository).save(testFtXzs);
    }

    @Test
    @Transactional
    void putNonExistingFtXzs() throws Exception {
        int databaseSizeBeforeUpdate = ftXzsRepository.findAll().size();
        ftXzs.setId(count.incrementAndGet());

        // Create the FtXzs
        FtXzsDTO ftXzsDTO = ftXzsMapper.toDto(ftXzs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFtXzsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, ftXzsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ftXzsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FtXzs in the database
        List<FtXzs> ftXzsList = ftXzsRepository.findAll();
        assertThat(ftXzsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FtXzs in Elasticsearch
        verify(mockFtXzsSearchRepository, times(0)).save(ftXzs);
    }

    @Test
    @Transactional
    void putWithIdMismatchFtXzs() throws Exception {
        int databaseSizeBeforeUpdate = ftXzsRepository.findAll().size();
        ftXzs.setId(count.incrementAndGet());

        // Create the FtXzs
        FtXzsDTO ftXzsDTO = ftXzsMapper.toDto(ftXzs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFtXzsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(ftXzsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FtXzs in the database
        List<FtXzs> ftXzsList = ftXzsRepository.findAll();
        assertThat(ftXzsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FtXzs in Elasticsearch
        verify(mockFtXzsSearchRepository, times(0)).save(ftXzs);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFtXzs() throws Exception {
        int databaseSizeBeforeUpdate = ftXzsRepository.findAll().size();
        ftXzs.setId(count.incrementAndGet());

        // Create the FtXzs
        FtXzsDTO ftXzsDTO = ftXzsMapper.toDto(ftXzs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFtXzsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(ftXzsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FtXzs in the database
        List<FtXzs> ftXzsList = ftXzsRepository.findAll();
        assertThat(ftXzsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FtXzs in Elasticsearch
        verify(mockFtXzsSearchRepository, times(0)).save(ftXzs);
    }

    @Test
    @Transactional
    void partialUpdateFtXzsWithPatch() throws Exception {
        // Initialize the database
        ftXzsRepository.saveAndFlush(ftXzs);

        int databaseSizeBeforeUpdate = ftXzsRepository.findAll().size();

        // Update the ftXzs using partial update
        FtXzs partialUpdatedFtXzs = new FtXzs();
        partialUpdatedFtXzs.setId(ftXzs.getId());

        restFtXzsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFtXzs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFtXzs))
            )
            .andExpect(status().isOk());

        // Validate the FtXzs in the database
        List<FtXzs> ftXzsList = ftXzsRepository.findAll();
        assertThat(ftXzsList).hasSize(databaseSizeBeforeUpdate);
        FtXzs testFtXzs = ftXzsList.get(ftXzsList.size() - 1);
        assertThat(testFtXzs.getRoomn()).isEqualTo(DEFAULT_ROOMN);
    }

    @Test
    @Transactional
    void fullUpdateFtXzsWithPatch() throws Exception {
        // Initialize the database
        ftXzsRepository.saveAndFlush(ftXzs);

        int databaseSizeBeforeUpdate = ftXzsRepository.findAll().size();

        // Update the ftXzs using partial update
        FtXzs partialUpdatedFtXzs = new FtXzs();
        partialUpdatedFtXzs.setId(ftXzs.getId());

        partialUpdatedFtXzs.roomn(UPDATED_ROOMN);

        restFtXzsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFtXzs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFtXzs))
            )
            .andExpect(status().isOk());

        // Validate the FtXzs in the database
        List<FtXzs> ftXzsList = ftXzsRepository.findAll();
        assertThat(ftXzsList).hasSize(databaseSizeBeforeUpdate);
        FtXzs testFtXzs = ftXzsList.get(ftXzsList.size() - 1);
        assertThat(testFtXzs.getRoomn()).isEqualTo(UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void patchNonExistingFtXzs() throws Exception {
        int databaseSizeBeforeUpdate = ftXzsRepository.findAll().size();
        ftXzs.setId(count.incrementAndGet());

        // Create the FtXzs
        FtXzsDTO ftXzsDTO = ftXzsMapper.toDto(ftXzs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFtXzsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, ftXzsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ftXzsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FtXzs in the database
        List<FtXzs> ftXzsList = ftXzsRepository.findAll();
        assertThat(ftXzsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FtXzs in Elasticsearch
        verify(mockFtXzsSearchRepository, times(0)).save(ftXzs);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFtXzs() throws Exception {
        int databaseSizeBeforeUpdate = ftXzsRepository.findAll().size();
        ftXzs.setId(count.incrementAndGet());

        // Create the FtXzs
        FtXzsDTO ftXzsDTO = ftXzsMapper.toDto(ftXzs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFtXzsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(ftXzsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FtXzs in the database
        List<FtXzs> ftXzsList = ftXzsRepository.findAll();
        assertThat(ftXzsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FtXzs in Elasticsearch
        verify(mockFtXzsSearchRepository, times(0)).save(ftXzs);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFtXzs() throws Exception {
        int databaseSizeBeforeUpdate = ftXzsRepository.findAll().size();
        ftXzs.setId(count.incrementAndGet());

        // Create the FtXzs
        FtXzsDTO ftXzsDTO = ftXzsMapper.toDto(ftXzs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFtXzsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(ftXzsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FtXzs in the database
        List<FtXzs> ftXzsList = ftXzsRepository.findAll();
        assertThat(ftXzsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FtXzs in Elasticsearch
        verify(mockFtXzsSearchRepository, times(0)).save(ftXzs);
    }

    @Test
    @Transactional
    void deleteFtXzs() throws Exception {
        // Initialize the database
        ftXzsRepository.saveAndFlush(ftXzs);

        int databaseSizeBeforeDelete = ftXzsRepository.findAll().size();

        // Delete the ftXzs
        restFtXzsMockMvc
            .perform(delete(ENTITY_API_URL_ID, ftXzs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FtXzs> ftXzsList = ftXzsRepository.findAll();
        assertThat(ftXzsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FtXzs in Elasticsearch
        verify(mockFtXzsSearchRepository, times(1)).deleteById(ftXzs.getId());
    }

    @Test
    @Transactional
    void searchFtXzs() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        ftXzsRepository.saveAndFlush(ftXzs);
        when(mockFtXzsSearchRepository.search(queryStringQuery("id:" + ftXzs.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(ftXzs), PageRequest.of(0, 1), 1));

        // Search the ftXzs
        restFtXzsMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + ftXzs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ftXzs.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)));
    }
}
