package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Adhoc;
import ihotel.app.repository.AdhocRepository;
import ihotel.app.repository.search.AdhocSearchRepository;
import ihotel.app.service.criteria.AdhocCriteria;
import ihotel.app.service.dto.AdhocDTO;
import ihotel.app.service.mapper.AdhocMapper;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
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
 * Integration tests for the {@link AdhocResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AdhocResourceIT {

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/adhocs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/adhocs";

    @Autowired
    private AdhocRepository adhocRepository;

    @Autowired
    private AdhocMapper adhocMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.AdhocSearchRepositoryMockConfiguration
     */
    @Autowired
    private AdhocSearchRepository mockAdhocSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdhocMockMvc;

    private Adhoc adhoc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adhoc createEntity(EntityManager em) {
        Adhoc adhoc = new Adhoc().remark(DEFAULT_REMARK);
        return adhoc;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Adhoc createUpdatedEntity(EntityManager em) {
        Adhoc adhoc = new Adhoc().remark(UPDATED_REMARK);
        return adhoc;
    }

    @BeforeEach
    public void initTest() {
        adhoc = createEntity(em);
    }

    @Test
    @Transactional
    void createAdhoc() throws Exception {
        int databaseSizeBeforeCreate = adhocRepository.findAll().size();
        // Create the Adhoc
        AdhocDTO adhocDTO = adhocMapper.toDto(adhoc);
        restAdhocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adhocDTO)))
            .andExpect(status().isCreated());

        // Validate the Adhoc in the database
        List<Adhoc> adhocList = adhocRepository.findAll();
        assertThat(adhocList).hasSize(databaseSizeBeforeCreate + 1);
        Adhoc testAdhoc = adhocList.get(adhocList.size() - 1);
        assertThat(testAdhoc.getRemark()).isEqualTo(DEFAULT_REMARK);

        // Validate the Adhoc in Elasticsearch
        verify(mockAdhocSearchRepository, times(1)).save(testAdhoc);
    }

    @Test
    @Transactional
    void createAdhocWithExistingId() throws Exception {
        // Create the Adhoc with an existing ID
        adhoc.setId("existing_id");
        AdhocDTO adhocDTO = adhocMapper.toDto(adhoc);

        int databaseSizeBeforeCreate = adhocRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdhocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adhocDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Adhoc in the database
        List<Adhoc> adhocList = adhocRepository.findAll();
        assertThat(adhocList).hasSize(databaseSizeBeforeCreate);

        // Validate the Adhoc in Elasticsearch
        verify(mockAdhocSearchRepository, times(0)).save(adhoc);
    }

    @Test
    @Transactional
    void checkRemarkIsRequired() throws Exception {
        int databaseSizeBeforeTest = adhocRepository.findAll().size();
        // set the field null
        adhoc.setRemark(null);

        // Create the Adhoc, which fails.
        AdhocDTO adhocDTO = adhocMapper.toDto(adhoc);

        restAdhocMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adhocDTO)))
            .andExpect(status().isBadRequest());

        List<Adhoc> adhocList = adhocRepository.findAll();
        assertThat(adhocList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAdhocs() throws Exception {
        // Initialize the database
        adhocRepository.saveAndFlush(adhoc);

        // Get all the adhocList
        restAdhocMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adhoc.getId())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));
    }

    @Test
    @Transactional
    void getAdhoc() throws Exception {
        // Initialize the database
        adhocRepository.saveAndFlush(adhoc);

        // Get the adhoc
        restAdhocMockMvc
            .perform(get(ENTITY_API_URL_ID, adhoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(adhoc.getId()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK));
    }

    @Test
    @Transactional
    void getAdhocsByIdFiltering() throws Exception {
        // Initialize the database
        adhocRepository.saveAndFlush(adhoc);

        String id = adhoc.getId();

        defaultAdhocShouldBeFound("id.equals=" + id);
        defaultAdhocShouldNotBeFound("id.notEquals=" + id);
    }

    @Test
    @Transactional
    void getAllAdhocsByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        adhocRepository.saveAndFlush(adhoc);

        // Get all the adhocList where remark equals to DEFAULT_REMARK
        defaultAdhocShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the adhocList where remark equals to UPDATED_REMARK
        defaultAdhocShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllAdhocsByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adhocRepository.saveAndFlush(adhoc);

        // Get all the adhocList where remark not equals to DEFAULT_REMARK
        defaultAdhocShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the adhocList where remark not equals to UPDATED_REMARK
        defaultAdhocShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllAdhocsByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        adhocRepository.saveAndFlush(adhoc);

        // Get all the adhocList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultAdhocShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the adhocList where remark equals to UPDATED_REMARK
        defaultAdhocShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllAdhocsByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        adhocRepository.saveAndFlush(adhoc);

        // Get all the adhocList where remark is not null
        defaultAdhocShouldBeFound("remark.specified=true");

        // Get all the adhocList where remark is null
        defaultAdhocShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllAdhocsByRemarkContainsSomething() throws Exception {
        // Initialize the database
        adhocRepository.saveAndFlush(adhoc);

        // Get all the adhocList where remark contains DEFAULT_REMARK
        defaultAdhocShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the adhocList where remark contains UPDATED_REMARK
        defaultAdhocShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllAdhocsByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        adhocRepository.saveAndFlush(adhoc);

        // Get all the adhocList where remark does not contain DEFAULT_REMARK
        defaultAdhocShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the adhocList where remark does not contain UPDATED_REMARK
        defaultAdhocShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdhocShouldBeFound(String filter) throws Exception {
        restAdhocMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adhoc.getId())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));

        // Check, that the count call also returns 1
        restAdhocMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdhocShouldNotBeFound(String filter) throws Exception {
        restAdhocMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdhocMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAdhoc() throws Exception {
        // Get the adhoc
        restAdhocMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAdhoc() throws Exception {
        // Initialize the database
        adhocRepository.saveAndFlush(adhoc);

        int databaseSizeBeforeUpdate = adhocRepository.findAll().size();

        // Update the adhoc
        Adhoc updatedAdhoc = adhocRepository.findById(adhoc.getId()).get();
        // Disconnect from session so that the updates on updatedAdhoc are not directly saved in db
        em.detach(updatedAdhoc);
        updatedAdhoc.remark(UPDATED_REMARK);
        AdhocDTO adhocDTO = adhocMapper.toDto(updatedAdhoc);

        restAdhocMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adhocDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adhocDTO))
            )
            .andExpect(status().isOk());

        // Validate the Adhoc in the database
        List<Adhoc> adhocList = adhocRepository.findAll();
        assertThat(adhocList).hasSize(databaseSizeBeforeUpdate);
        Adhoc testAdhoc = adhocList.get(adhocList.size() - 1);
        assertThat(testAdhoc.getRemark()).isEqualTo(UPDATED_REMARK);

        // Validate the Adhoc in Elasticsearch
        verify(mockAdhocSearchRepository).save(testAdhoc);
    }

    @Test
    @Transactional
    void putNonExistingAdhoc() throws Exception {
        int databaseSizeBeforeUpdate = adhocRepository.findAll().size();
        adhoc.setId(UUID.randomUUID().toString());

        // Create the Adhoc
        AdhocDTO adhocDTO = adhocMapper.toDto(adhoc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdhocMockMvc
            .perform(
                put(ENTITY_API_URL_ID, adhocDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adhocDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adhoc in the database
        List<Adhoc> adhocList = adhocRepository.findAll();
        assertThat(adhocList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Adhoc in Elasticsearch
        verify(mockAdhocSearchRepository, times(0)).save(adhoc);
    }

    @Test
    @Transactional
    void putWithIdMismatchAdhoc() throws Exception {
        int databaseSizeBeforeUpdate = adhocRepository.findAll().size();
        adhoc.setId(UUID.randomUUID().toString());

        // Create the Adhoc
        AdhocDTO adhocDTO = adhocMapper.toDto(adhoc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdhocMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(adhocDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adhoc in the database
        List<Adhoc> adhocList = adhocRepository.findAll();
        assertThat(adhocList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Adhoc in Elasticsearch
        verify(mockAdhocSearchRepository, times(0)).save(adhoc);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAdhoc() throws Exception {
        int databaseSizeBeforeUpdate = adhocRepository.findAll().size();
        adhoc.setId(UUID.randomUUID().toString());

        // Create the Adhoc
        AdhocDTO adhocDTO = adhocMapper.toDto(adhoc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdhocMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(adhocDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adhoc in the database
        List<Adhoc> adhocList = adhocRepository.findAll();
        assertThat(adhocList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Adhoc in Elasticsearch
        verify(mockAdhocSearchRepository, times(0)).save(adhoc);
    }

    @Test
    @Transactional
    void partialUpdateAdhocWithPatch() throws Exception {
        // Initialize the database
        adhocRepository.saveAndFlush(adhoc);

        int databaseSizeBeforeUpdate = adhocRepository.findAll().size();

        // Update the adhoc using partial update
        Adhoc partialUpdatedAdhoc = new Adhoc();
        partialUpdatedAdhoc.setId(adhoc.getId());

        partialUpdatedAdhoc.remark(UPDATED_REMARK);

        restAdhocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdhoc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdhoc))
            )
            .andExpect(status().isOk());

        // Validate the Adhoc in the database
        List<Adhoc> adhocList = adhocRepository.findAll();
        assertThat(adhocList).hasSize(databaseSizeBeforeUpdate);
        Adhoc testAdhoc = adhocList.get(adhocList.size() - 1);
        assertThat(testAdhoc.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    void fullUpdateAdhocWithPatch() throws Exception {
        // Initialize the database
        adhocRepository.saveAndFlush(adhoc);

        int databaseSizeBeforeUpdate = adhocRepository.findAll().size();

        // Update the adhoc using partial update
        Adhoc partialUpdatedAdhoc = new Adhoc();
        partialUpdatedAdhoc.setId(adhoc.getId());

        partialUpdatedAdhoc.remark(UPDATED_REMARK);

        restAdhocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAdhoc.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAdhoc))
            )
            .andExpect(status().isOk());

        // Validate the Adhoc in the database
        List<Adhoc> adhocList = adhocRepository.findAll();
        assertThat(adhocList).hasSize(databaseSizeBeforeUpdate);
        Adhoc testAdhoc = adhocList.get(adhocList.size() - 1);
        assertThat(testAdhoc.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    void patchNonExistingAdhoc() throws Exception {
        int databaseSizeBeforeUpdate = adhocRepository.findAll().size();
        adhoc.setId(UUID.randomUUID().toString());

        // Create the Adhoc
        AdhocDTO adhocDTO = adhocMapper.toDto(adhoc);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdhocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, adhocDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adhocDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adhoc in the database
        List<Adhoc> adhocList = adhocRepository.findAll();
        assertThat(adhocList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Adhoc in Elasticsearch
        verify(mockAdhocSearchRepository, times(0)).save(adhoc);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAdhoc() throws Exception {
        int databaseSizeBeforeUpdate = adhocRepository.findAll().size();
        adhoc.setId(UUID.randomUUID().toString());

        // Create the Adhoc
        AdhocDTO adhocDTO = adhocMapper.toDto(adhoc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdhocMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(adhocDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Adhoc in the database
        List<Adhoc> adhocList = adhocRepository.findAll();
        assertThat(adhocList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Adhoc in Elasticsearch
        verify(mockAdhocSearchRepository, times(0)).save(adhoc);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAdhoc() throws Exception {
        int databaseSizeBeforeUpdate = adhocRepository.findAll().size();
        adhoc.setId(UUID.randomUUID().toString());

        // Create the Adhoc
        AdhocDTO adhocDTO = adhocMapper.toDto(adhoc);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAdhocMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(adhocDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Adhoc in the database
        List<Adhoc> adhocList = adhocRepository.findAll();
        assertThat(adhocList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Adhoc in Elasticsearch
        verify(mockAdhocSearchRepository, times(0)).save(adhoc);
    }

    @Test
    @Transactional
    void deleteAdhoc() throws Exception {
        // Initialize the database
        adhocRepository.saveAndFlush(adhoc);

        int databaseSizeBeforeDelete = adhocRepository.findAll().size();

        // Delete the adhoc
        restAdhocMockMvc
            .perform(delete(ENTITY_API_URL_ID, adhoc.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Adhoc> adhocList = adhocRepository.findAll();
        assertThat(adhocList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Adhoc in Elasticsearch
        verify(mockAdhocSearchRepository, times(1)).deleteById(adhoc.getId());
    }

    @Test
    @Transactional
    void searchAdhoc() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        adhocRepository.saveAndFlush(adhoc);
        when(mockAdhocSearchRepository.search(queryStringQuery("id:" + adhoc.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(adhoc), PageRequest.of(0, 1), 1));

        // Search the adhoc
        restAdhocMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + adhoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(adhoc.getId())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));
    }
}
