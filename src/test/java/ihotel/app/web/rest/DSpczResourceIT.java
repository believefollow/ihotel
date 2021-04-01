package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DSpcz;
import ihotel.app.repository.DSpczRepository;
import ihotel.app.repository.search.DSpczSearchRepository;
import ihotel.app.service.criteria.DSpczCriteria;
import ihotel.app.service.dto.DSpczDTO;
import ihotel.app.service.mapper.DSpczMapper;
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
 * Integration tests for the {@link DSpczResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DSpczResourceIT {

    private static final Instant DEFAULT_RQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CZRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CZRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/d-spczs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-spczs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DSpczRepository dSpczRepository;

    @Autowired
    private DSpczMapper dSpczMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DSpczSearchRepositoryMockConfiguration
     */
    @Autowired
    private DSpczSearchRepository mockDSpczSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDSpczMockMvc;

    private DSpcz dSpcz;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DSpcz createEntity(EntityManager em) {
        DSpcz dSpcz = new DSpcz().rq(DEFAULT_RQ).czrq(DEFAULT_CZRQ);
        return dSpcz;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DSpcz createUpdatedEntity(EntityManager em) {
        DSpcz dSpcz = new DSpcz().rq(UPDATED_RQ).czrq(UPDATED_CZRQ);
        return dSpcz;
    }

    @BeforeEach
    public void initTest() {
        dSpcz = createEntity(em);
    }

    @Test
    @Transactional
    void createDSpcz() throws Exception {
        int databaseSizeBeforeCreate = dSpczRepository.findAll().size();
        // Create the DSpcz
        DSpczDTO dSpczDTO = dSpczMapper.toDto(dSpcz);
        restDSpczMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dSpczDTO)))
            .andExpect(status().isCreated());

        // Validate the DSpcz in the database
        List<DSpcz> dSpczList = dSpczRepository.findAll();
        assertThat(dSpczList).hasSize(databaseSizeBeforeCreate + 1);
        DSpcz testDSpcz = dSpczList.get(dSpczList.size() - 1);
        assertThat(testDSpcz.getRq()).isEqualTo(DEFAULT_RQ);
        assertThat(testDSpcz.getCzrq()).isEqualTo(DEFAULT_CZRQ);

        // Validate the DSpcz in Elasticsearch
        verify(mockDSpczSearchRepository, times(1)).save(testDSpcz);
    }

    @Test
    @Transactional
    void createDSpczWithExistingId() throws Exception {
        // Create the DSpcz with an existing ID
        dSpcz.setId(1L);
        DSpczDTO dSpczDTO = dSpczMapper.toDto(dSpcz);

        int databaseSizeBeforeCreate = dSpczRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDSpczMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dSpczDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DSpcz in the database
        List<DSpcz> dSpczList = dSpczRepository.findAll();
        assertThat(dSpczList).hasSize(databaseSizeBeforeCreate);

        // Validate the DSpcz in Elasticsearch
        verify(mockDSpczSearchRepository, times(0)).save(dSpcz);
    }

    @Test
    @Transactional
    void getAllDSpczs() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        // Get all the dSpczList
        restDSpczMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dSpcz.getId().intValue())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].czrq").value(hasItem(DEFAULT_CZRQ.toString())));
    }

    @Test
    @Transactional
    void getDSpcz() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        // Get the dSpcz
        restDSpczMockMvc
            .perform(get(ENTITY_API_URL_ID, dSpcz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dSpcz.getId().intValue()))
            .andExpect(jsonPath("$.rq").value(DEFAULT_RQ.toString()))
            .andExpect(jsonPath("$.czrq").value(DEFAULT_CZRQ.toString()));
    }

    @Test
    @Transactional
    void getDSpczsByIdFiltering() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        Long id = dSpcz.getId();

        defaultDSpczShouldBeFound("id.equals=" + id);
        defaultDSpczShouldNotBeFound("id.notEquals=" + id);

        defaultDSpczShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDSpczShouldNotBeFound("id.greaterThan=" + id);

        defaultDSpczShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDSpczShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDSpczsByRqIsEqualToSomething() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        // Get all the dSpczList where rq equals to DEFAULT_RQ
        defaultDSpczShouldBeFound("rq.equals=" + DEFAULT_RQ);

        // Get all the dSpczList where rq equals to UPDATED_RQ
        defaultDSpczShouldNotBeFound("rq.equals=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllDSpczsByRqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        // Get all the dSpczList where rq not equals to DEFAULT_RQ
        defaultDSpczShouldNotBeFound("rq.notEquals=" + DEFAULT_RQ);

        // Get all the dSpczList where rq not equals to UPDATED_RQ
        defaultDSpczShouldBeFound("rq.notEquals=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllDSpczsByRqIsInShouldWork() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        // Get all the dSpczList where rq in DEFAULT_RQ or UPDATED_RQ
        defaultDSpczShouldBeFound("rq.in=" + DEFAULT_RQ + "," + UPDATED_RQ);

        // Get all the dSpczList where rq equals to UPDATED_RQ
        defaultDSpczShouldNotBeFound("rq.in=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllDSpczsByRqIsNullOrNotNull() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        // Get all the dSpczList where rq is not null
        defaultDSpczShouldBeFound("rq.specified=true");

        // Get all the dSpczList where rq is null
        defaultDSpczShouldNotBeFound("rq.specified=false");
    }

    @Test
    @Transactional
    void getAllDSpczsByCzrqIsEqualToSomething() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        // Get all the dSpczList where czrq equals to DEFAULT_CZRQ
        defaultDSpczShouldBeFound("czrq.equals=" + DEFAULT_CZRQ);

        // Get all the dSpczList where czrq equals to UPDATED_CZRQ
        defaultDSpczShouldNotBeFound("czrq.equals=" + UPDATED_CZRQ);
    }

    @Test
    @Transactional
    void getAllDSpczsByCzrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        // Get all the dSpczList where czrq not equals to DEFAULT_CZRQ
        defaultDSpczShouldNotBeFound("czrq.notEquals=" + DEFAULT_CZRQ);

        // Get all the dSpczList where czrq not equals to UPDATED_CZRQ
        defaultDSpczShouldBeFound("czrq.notEquals=" + UPDATED_CZRQ);
    }

    @Test
    @Transactional
    void getAllDSpczsByCzrqIsInShouldWork() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        // Get all the dSpczList where czrq in DEFAULT_CZRQ or UPDATED_CZRQ
        defaultDSpczShouldBeFound("czrq.in=" + DEFAULT_CZRQ + "," + UPDATED_CZRQ);

        // Get all the dSpczList where czrq equals to UPDATED_CZRQ
        defaultDSpczShouldNotBeFound("czrq.in=" + UPDATED_CZRQ);
    }

    @Test
    @Transactional
    void getAllDSpczsByCzrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        // Get all the dSpczList where czrq is not null
        defaultDSpczShouldBeFound("czrq.specified=true");

        // Get all the dSpczList where czrq is null
        defaultDSpczShouldNotBeFound("czrq.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDSpczShouldBeFound(String filter) throws Exception {
        restDSpczMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dSpcz.getId().intValue())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].czrq").value(hasItem(DEFAULT_CZRQ.toString())));

        // Check, that the count call also returns 1
        restDSpczMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDSpczShouldNotBeFound(String filter) throws Exception {
        restDSpczMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDSpczMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDSpcz() throws Exception {
        // Get the dSpcz
        restDSpczMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDSpcz() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        int databaseSizeBeforeUpdate = dSpczRepository.findAll().size();

        // Update the dSpcz
        DSpcz updatedDSpcz = dSpczRepository.findById(dSpcz.getId()).get();
        // Disconnect from session so that the updates on updatedDSpcz are not directly saved in db
        em.detach(updatedDSpcz);
        updatedDSpcz.rq(UPDATED_RQ).czrq(UPDATED_CZRQ);
        DSpczDTO dSpczDTO = dSpczMapper.toDto(updatedDSpcz);

        restDSpczMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dSpczDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dSpczDTO))
            )
            .andExpect(status().isOk());

        // Validate the DSpcz in the database
        List<DSpcz> dSpczList = dSpczRepository.findAll();
        assertThat(dSpczList).hasSize(databaseSizeBeforeUpdate);
        DSpcz testDSpcz = dSpczList.get(dSpczList.size() - 1);
        assertThat(testDSpcz.getRq()).isEqualTo(UPDATED_RQ);
        assertThat(testDSpcz.getCzrq()).isEqualTo(UPDATED_CZRQ);

        // Validate the DSpcz in Elasticsearch
        verify(mockDSpczSearchRepository).save(testDSpcz);
    }

    @Test
    @Transactional
    void putNonExistingDSpcz() throws Exception {
        int databaseSizeBeforeUpdate = dSpczRepository.findAll().size();
        dSpcz.setId(count.incrementAndGet());

        // Create the DSpcz
        DSpczDTO dSpczDTO = dSpczMapper.toDto(dSpcz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDSpczMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dSpczDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dSpczDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DSpcz in the database
        List<DSpcz> dSpczList = dSpczRepository.findAll();
        assertThat(dSpczList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DSpcz in Elasticsearch
        verify(mockDSpczSearchRepository, times(0)).save(dSpcz);
    }

    @Test
    @Transactional
    void putWithIdMismatchDSpcz() throws Exception {
        int databaseSizeBeforeUpdate = dSpczRepository.findAll().size();
        dSpcz.setId(count.incrementAndGet());

        // Create the DSpcz
        DSpczDTO dSpczDTO = dSpczMapper.toDto(dSpcz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDSpczMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dSpczDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DSpcz in the database
        List<DSpcz> dSpczList = dSpczRepository.findAll();
        assertThat(dSpczList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DSpcz in Elasticsearch
        verify(mockDSpczSearchRepository, times(0)).save(dSpcz);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDSpcz() throws Exception {
        int databaseSizeBeforeUpdate = dSpczRepository.findAll().size();
        dSpcz.setId(count.incrementAndGet());

        // Create the DSpcz
        DSpczDTO dSpczDTO = dSpczMapper.toDto(dSpcz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDSpczMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dSpczDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DSpcz in the database
        List<DSpcz> dSpczList = dSpczRepository.findAll();
        assertThat(dSpczList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DSpcz in Elasticsearch
        verify(mockDSpczSearchRepository, times(0)).save(dSpcz);
    }

    @Test
    @Transactional
    void partialUpdateDSpczWithPatch() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        int databaseSizeBeforeUpdate = dSpczRepository.findAll().size();

        // Update the dSpcz using partial update
        DSpcz partialUpdatedDSpcz = new DSpcz();
        partialUpdatedDSpcz.setId(dSpcz.getId());

        partialUpdatedDSpcz.rq(UPDATED_RQ).czrq(UPDATED_CZRQ);

        restDSpczMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDSpcz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDSpcz))
            )
            .andExpect(status().isOk());

        // Validate the DSpcz in the database
        List<DSpcz> dSpczList = dSpczRepository.findAll();
        assertThat(dSpczList).hasSize(databaseSizeBeforeUpdate);
        DSpcz testDSpcz = dSpczList.get(dSpczList.size() - 1);
        assertThat(testDSpcz.getRq()).isEqualTo(UPDATED_RQ);
        assertThat(testDSpcz.getCzrq()).isEqualTo(UPDATED_CZRQ);
    }

    @Test
    @Transactional
    void fullUpdateDSpczWithPatch() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        int databaseSizeBeforeUpdate = dSpczRepository.findAll().size();

        // Update the dSpcz using partial update
        DSpcz partialUpdatedDSpcz = new DSpcz();
        partialUpdatedDSpcz.setId(dSpcz.getId());

        partialUpdatedDSpcz.rq(UPDATED_RQ).czrq(UPDATED_CZRQ);

        restDSpczMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDSpcz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDSpcz))
            )
            .andExpect(status().isOk());

        // Validate the DSpcz in the database
        List<DSpcz> dSpczList = dSpczRepository.findAll();
        assertThat(dSpczList).hasSize(databaseSizeBeforeUpdate);
        DSpcz testDSpcz = dSpczList.get(dSpczList.size() - 1);
        assertThat(testDSpcz.getRq()).isEqualTo(UPDATED_RQ);
        assertThat(testDSpcz.getCzrq()).isEqualTo(UPDATED_CZRQ);
    }

    @Test
    @Transactional
    void patchNonExistingDSpcz() throws Exception {
        int databaseSizeBeforeUpdate = dSpczRepository.findAll().size();
        dSpcz.setId(count.incrementAndGet());

        // Create the DSpcz
        DSpczDTO dSpczDTO = dSpczMapper.toDto(dSpcz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDSpczMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dSpczDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dSpczDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DSpcz in the database
        List<DSpcz> dSpczList = dSpczRepository.findAll();
        assertThat(dSpczList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DSpcz in Elasticsearch
        verify(mockDSpczSearchRepository, times(0)).save(dSpcz);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDSpcz() throws Exception {
        int databaseSizeBeforeUpdate = dSpczRepository.findAll().size();
        dSpcz.setId(count.incrementAndGet());

        // Create the DSpcz
        DSpczDTO dSpczDTO = dSpczMapper.toDto(dSpcz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDSpczMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dSpczDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DSpcz in the database
        List<DSpcz> dSpczList = dSpczRepository.findAll();
        assertThat(dSpczList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DSpcz in Elasticsearch
        verify(mockDSpczSearchRepository, times(0)).save(dSpcz);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDSpcz() throws Exception {
        int databaseSizeBeforeUpdate = dSpczRepository.findAll().size();
        dSpcz.setId(count.incrementAndGet());

        // Create the DSpcz
        DSpczDTO dSpczDTO = dSpczMapper.toDto(dSpcz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDSpczMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dSpczDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DSpcz in the database
        List<DSpcz> dSpczList = dSpczRepository.findAll();
        assertThat(dSpczList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DSpcz in Elasticsearch
        verify(mockDSpczSearchRepository, times(0)).save(dSpcz);
    }

    @Test
    @Transactional
    void deleteDSpcz() throws Exception {
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);

        int databaseSizeBeforeDelete = dSpczRepository.findAll().size();

        // Delete the dSpcz
        restDSpczMockMvc
            .perform(delete(ENTITY_API_URL_ID, dSpcz.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DSpcz> dSpczList = dSpczRepository.findAll();
        assertThat(dSpczList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DSpcz in Elasticsearch
        verify(mockDSpczSearchRepository, times(1)).deleteById(dSpcz.getId());
    }

    @Test
    @Transactional
    void searchDSpcz() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dSpczRepository.saveAndFlush(dSpcz);
        when(mockDSpczSearchRepository.search(queryStringQuery("id:" + dSpcz.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dSpcz), PageRequest.of(0, 1), 1));

        // Search the dSpcz
        restDSpczMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dSpcz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dSpcz.getId().intValue())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].czrq").value(hasItem(DEFAULT_CZRQ.toString())));
    }
}
