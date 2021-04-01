package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DUnit;
import ihotel.app.repository.DUnitRepository;
import ihotel.app.repository.search.DUnitSearchRepository;
import ihotel.app.service.criteria.DUnitCriteria;
import ihotel.app.service.dto.DUnitDTO;
import ihotel.app.service.mapper.DUnitMapper;
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
 * Integration tests for the {@link DUnitResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DUnitResourceIT {

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/d-units";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-units";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DUnitRepository dUnitRepository;

    @Autowired
    private DUnitMapper dUnitMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DUnitSearchRepositoryMockConfiguration
     */
    @Autowired
    private DUnitSearchRepository mockDUnitSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDUnitMockMvc;

    private DUnit dUnit;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DUnit createEntity(EntityManager em) {
        DUnit dUnit = new DUnit().unit(DEFAULT_UNIT);
        return dUnit;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DUnit createUpdatedEntity(EntityManager em) {
        DUnit dUnit = new DUnit().unit(UPDATED_UNIT);
        return dUnit;
    }

    @BeforeEach
    public void initTest() {
        dUnit = createEntity(em);
    }

    @Test
    @Transactional
    void createDUnit() throws Exception {
        int databaseSizeBeforeCreate = dUnitRepository.findAll().size();
        // Create the DUnit
        DUnitDTO dUnitDTO = dUnitMapper.toDto(dUnit);
        restDUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dUnitDTO)))
            .andExpect(status().isCreated());

        // Validate the DUnit in the database
        List<DUnit> dUnitList = dUnitRepository.findAll();
        assertThat(dUnitList).hasSize(databaseSizeBeforeCreate + 1);
        DUnit testDUnit = dUnitList.get(dUnitList.size() - 1);
        assertThat(testDUnit.getUnit()).isEqualTo(DEFAULT_UNIT);

        // Validate the DUnit in Elasticsearch
        verify(mockDUnitSearchRepository, times(1)).save(testDUnit);
    }

    @Test
    @Transactional
    void createDUnitWithExistingId() throws Exception {
        // Create the DUnit with an existing ID
        dUnit.setId(1L);
        DUnitDTO dUnitDTO = dUnitMapper.toDto(dUnit);

        int databaseSizeBeforeCreate = dUnitRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dUnitDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DUnit in the database
        List<DUnit> dUnitList = dUnitRepository.findAll();
        assertThat(dUnitList).hasSize(databaseSizeBeforeCreate);

        // Validate the DUnit in Elasticsearch
        verify(mockDUnitSearchRepository, times(0)).save(dUnit);
    }

    @Test
    @Transactional
    void checkUnitIsRequired() throws Exception {
        int databaseSizeBeforeTest = dUnitRepository.findAll().size();
        // set the field null
        dUnit.setUnit(null);

        // Create the DUnit, which fails.
        DUnitDTO dUnitDTO = dUnitMapper.toDto(dUnit);

        restDUnitMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dUnitDTO)))
            .andExpect(status().isBadRequest());

        List<DUnit> dUnitList = dUnitRepository.findAll();
        assertThat(dUnitList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDUnits() throws Exception {
        // Initialize the database
        dUnitRepository.saveAndFlush(dUnit);

        // Get all the dUnitList
        restDUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)));
    }

    @Test
    @Transactional
    void getDUnit() throws Exception {
        // Initialize the database
        dUnitRepository.saveAndFlush(dUnit);

        // Get the dUnit
        restDUnitMockMvc
            .perform(get(ENTITY_API_URL_ID, dUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dUnit.getId().intValue()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT));
    }

    @Test
    @Transactional
    void getDUnitsByIdFiltering() throws Exception {
        // Initialize the database
        dUnitRepository.saveAndFlush(dUnit);

        Long id = dUnit.getId();

        defaultDUnitShouldBeFound("id.equals=" + id);
        defaultDUnitShouldNotBeFound("id.notEquals=" + id);

        defaultDUnitShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDUnitShouldNotBeFound("id.greaterThan=" + id);

        defaultDUnitShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDUnitShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDUnitsByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        dUnitRepository.saveAndFlush(dUnit);

        // Get all the dUnitList where unit equals to DEFAULT_UNIT
        defaultDUnitShouldBeFound("unit.equals=" + DEFAULT_UNIT);

        // Get all the dUnitList where unit equals to UPDATED_UNIT
        defaultDUnitShouldNotBeFound("unit.equals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDUnitsByUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dUnitRepository.saveAndFlush(dUnit);

        // Get all the dUnitList where unit not equals to DEFAULT_UNIT
        defaultDUnitShouldNotBeFound("unit.notEquals=" + DEFAULT_UNIT);

        // Get all the dUnitList where unit not equals to UPDATED_UNIT
        defaultDUnitShouldBeFound("unit.notEquals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDUnitsByUnitIsInShouldWork() throws Exception {
        // Initialize the database
        dUnitRepository.saveAndFlush(dUnit);

        // Get all the dUnitList where unit in DEFAULT_UNIT or UPDATED_UNIT
        defaultDUnitShouldBeFound("unit.in=" + DEFAULT_UNIT + "," + UPDATED_UNIT);

        // Get all the dUnitList where unit equals to UPDATED_UNIT
        defaultDUnitShouldNotBeFound("unit.in=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDUnitsByUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        dUnitRepository.saveAndFlush(dUnit);

        // Get all the dUnitList where unit is not null
        defaultDUnitShouldBeFound("unit.specified=true");

        // Get all the dUnitList where unit is null
        defaultDUnitShouldNotBeFound("unit.specified=false");
    }

    @Test
    @Transactional
    void getAllDUnitsByUnitContainsSomething() throws Exception {
        // Initialize the database
        dUnitRepository.saveAndFlush(dUnit);

        // Get all the dUnitList where unit contains DEFAULT_UNIT
        defaultDUnitShouldBeFound("unit.contains=" + DEFAULT_UNIT);

        // Get all the dUnitList where unit contains UPDATED_UNIT
        defaultDUnitShouldNotBeFound("unit.contains=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDUnitsByUnitNotContainsSomething() throws Exception {
        // Initialize the database
        dUnitRepository.saveAndFlush(dUnit);

        // Get all the dUnitList where unit does not contain DEFAULT_UNIT
        defaultDUnitShouldNotBeFound("unit.doesNotContain=" + DEFAULT_UNIT);

        // Get all the dUnitList where unit does not contain UPDATED_UNIT
        defaultDUnitShouldBeFound("unit.doesNotContain=" + UPDATED_UNIT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDUnitShouldBeFound(String filter) throws Exception {
        restDUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)));

        // Check, that the count call also returns 1
        restDUnitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDUnitShouldNotBeFound(String filter) throws Exception {
        restDUnitMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDUnitMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDUnit() throws Exception {
        // Get the dUnit
        restDUnitMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDUnit() throws Exception {
        // Initialize the database
        dUnitRepository.saveAndFlush(dUnit);

        int databaseSizeBeforeUpdate = dUnitRepository.findAll().size();

        // Update the dUnit
        DUnit updatedDUnit = dUnitRepository.findById(dUnit.getId()).get();
        // Disconnect from session so that the updates on updatedDUnit are not directly saved in db
        em.detach(updatedDUnit);
        updatedDUnit.unit(UPDATED_UNIT);
        DUnitDTO dUnitDTO = dUnitMapper.toDto(updatedDUnit);

        restDUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dUnitDTO))
            )
            .andExpect(status().isOk());

        // Validate the DUnit in the database
        List<DUnit> dUnitList = dUnitRepository.findAll();
        assertThat(dUnitList).hasSize(databaseSizeBeforeUpdate);
        DUnit testDUnit = dUnitList.get(dUnitList.size() - 1);
        assertThat(testDUnit.getUnit()).isEqualTo(UPDATED_UNIT);

        // Validate the DUnit in Elasticsearch
        verify(mockDUnitSearchRepository).save(testDUnit);
    }

    @Test
    @Transactional
    void putNonExistingDUnit() throws Exception {
        int databaseSizeBeforeUpdate = dUnitRepository.findAll().size();
        dUnit.setId(count.incrementAndGet());

        // Create the DUnit
        DUnitDTO dUnitDTO = dUnitMapper.toDto(dUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dUnitDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DUnit in the database
        List<DUnit> dUnitList = dUnitRepository.findAll();
        assertThat(dUnitList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DUnit in Elasticsearch
        verify(mockDUnitSearchRepository, times(0)).save(dUnit);
    }

    @Test
    @Transactional
    void putWithIdMismatchDUnit() throws Exception {
        int databaseSizeBeforeUpdate = dUnitRepository.findAll().size();
        dUnit.setId(count.incrementAndGet());

        // Create the DUnit
        DUnitDTO dUnitDTO = dUnitMapper.toDto(dUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDUnitMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DUnit in the database
        List<DUnit> dUnitList = dUnitRepository.findAll();
        assertThat(dUnitList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DUnit in Elasticsearch
        verify(mockDUnitSearchRepository, times(0)).save(dUnit);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDUnit() throws Exception {
        int databaseSizeBeforeUpdate = dUnitRepository.findAll().size();
        dUnit.setId(count.incrementAndGet());

        // Create the DUnit
        DUnitDTO dUnitDTO = dUnitMapper.toDto(dUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDUnitMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dUnitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DUnit in the database
        List<DUnit> dUnitList = dUnitRepository.findAll();
        assertThat(dUnitList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DUnit in Elasticsearch
        verify(mockDUnitSearchRepository, times(0)).save(dUnit);
    }

    @Test
    @Transactional
    void partialUpdateDUnitWithPatch() throws Exception {
        // Initialize the database
        dUnitRepository.saveAndFlush(dUnit);

        int databaseSizeBeforeUpdate = dUnitRepository.findAll().size();

        // Update the dUnit using partial update
        DUnit partialUpdatedDUnit = new DUnit();
        partialUpdatedDUnit.setId(dUnit.getId());

        partialUpdatedDUnit.unit(UPDATED_UNIT);

        restDUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDUnit))
            )
            .andExpect(status().isOk());

        // Validate the DUnit in the database
        List<DUnit> dUnitList = dUnitRepository.findAll();
        assertThat(dUnitList).hasSize(databaseSizeBeforeUpdate);
        DUnit testDUnit = dUnitList.get(dUnitList.size() - 1);
        assertThat(testDUnit.getUnit()).isEqualTo(UPDATED_UNIT);
    }

    @Test
    @Transactional
    void fullUpdateDUnitWithPatch() throws Exception {
        // Initialize the database
        dUnitRepository.saveAndFlush(dUnit);

        int databaseSizeBeforeUpdate = dUnitRepository.findAll().size();

        // Update the dUnit using partial update
        DUnit partialUpdatedDUnit = new DUnit();
        partialUpdatedDUnit.setId(dUnit.getId());

        partialUpdatedDUnit.unit(UPDATED_UNIT);

        restDUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDUnit.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDUnit))
            )
            .andExpect(status().isOk());

        // Validate the DUnit in the database
        List<DUnit> dUnitList = dUnitRepository.findAll();
        assertThat(dUnitList).hasSize(databaseSizeBeforeUpdate);
        DUnit testDUnit = dUnitList.get(dUnitList.size() - 1);
        assertThat(testDUnit.getUnit()).isEqualTo(UPDATED_UNIT);
    }

    @Test
    @Transactional
    void patchNonExistingDUnit() throws Exception {
        int databaseSizeBeforeUpdate = dUnitRepository.findAll().size();
        dUnit.setId(count.incrementAndGet());

        // Create the DUnit
        DUnitDTO dUnitDTO = dUnitMapper.toDto(dUnit);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dUnitDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DUnit in the database
        List<DUnit> dUnitList = dUnitRepository.findAll();
        assertThat(dUnitList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DUnit in Elasticsearch
        verify(mockDUnitSearchRepository, times(0)).save(dUnit);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDUnit() throws Exception {
        int databaseSizeBeforeUpdate = dUnitRepository.findAll().size();
        dUnit.setId(count.incrementAndGet());

        // Create the DUnit
        DUnitDTO dUnitDTO = dUnitMapper.toDto(dUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDUnitMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dUnitDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DUnit in the database
        List<DUnit> dUnitList = dUnitRepository.findAll();
        assertThat(dUnitList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DUnit in Elasticsearch
        verify(mockDUnitSearchRepository, times(0)).save(dUnit);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDUnit() throws Exception {
        int databaseSizeBeforeUpdate = dUnitRepository.findAll().size();
        dUnit.setId(count.incrementAndGet());

        // Create the DUnit
        DUnitDTO dUnitDTO = dUnitMapper.toDto(dUnit);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDUnitMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dUnitDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DUnit in the database
        List<DUnit> dUnitList = dUnitRepository.findAll();
        assertThat(dUnitList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DUnit in Elasticsearch
        verify(mockDUnitSearchRepository, times(0)).save(dUnit);
    }

    @Test
    @Transactional
    void deleteDUnit() throws Exception {
        // Initialize the database
        dUnitRepository.saveAndFlush(dUnit);

        int databaseSizeBeforeDelete = dUnitRepository.findAll().size();

        // Delete the dUnit
        restDUnitMockMvc
            .perform(delete(ENTITY_API_URL_ID, dUnit.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DUnit> dUnitList = dUnitRepository.findAll();
        assertThat(dUnitList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DUnit in Elasticsearch
        verify(mockDUnitSearchRepository, times(1)).deleteById(dUnit.getId());
    }

    @Test
    @Transactional
    void searchDUnit() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dUnitRepository.saveAndFlush(dUnit);
        when(mockDUnitSearchRepository.search(queryStringQuery("id:" + dUnit.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dUnit), PageRequest.of(0, 1), 1));

        // Search the dUnit
        restDUnitMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dUnit.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dUnit.getId().intValue())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)));
    }
}
