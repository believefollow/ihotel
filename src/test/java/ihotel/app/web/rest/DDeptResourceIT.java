package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DDept;
import ihotel.app.repository.DDeptRepository;
import ihotel.app.repository.search.DDeptSearchRepository;
import ihotel.app.service.criteria.DDeptCriteria;
import ihotel.app.service.dto.DDeptDTO;
import ihotel.app.service.mapper.DDeptMapper;
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
 * Integration tests for the {@link DDeptResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DDeptResourceIT {

    private static final Long DEFAULT_DEPTID = 1L;
    private static final Long UPDATED_DEPTID = 2L;
    private static final Long SMALLER_DEPTID = 1L - 1L;

    private static final String DEFAULT_DEPTNAME = "AAAAAAAAAA";
    private static final String UPDATED_DEPTNAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/d-depts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-depts";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DDeptRepository dDeptRepository;

    @Autowired
    private DDeptMapper dDeptMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DDeptSearchRepositoryMockConfiguration
     */
    @Autowired
    private DDeptSearchRepository mockDDeptSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDDeptMockMvc;

    private DDept dDept;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DDept createEntity(EntityManager em) {
        DDept dDept = new DDept().deptid(DEFAULT_DEPTID).deptname(DEFAULT_DEPTNAME);
        return dDept;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DDept createUpdatedEntity(EntityManager em) {
        DDept dDept = new DDept().deptid(UPDATED_DEPTID).deptname(UPDATED_DEPTNAME);
        return dDept;
    }

    @BeforeEach
    public void initTest() {
        dDept = createEntity(em);
    }

    @Test
    @Transactional
    void createDDept() throws Exception {
        int databaseSizeBeforeCreate = dDeptRepository.findAll().size();
        // Create the DDept
        DDeptDTO dDeptDTO = dDeptMapper.toDto(dDept);
        restDDeptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDeptDTO)))
            .andExpect(status().isCreated());

        // Validate the DDept in the database
        List<DDept> dDeptList = dDeptRepository.findAll();
        assertThat(dDeptList).hasSize(databaseSizeBeforeCreate + 1);
        DDept testDDept = dDeptList.get(dDeptList.size() - 1);
        assertThat(testDDept.getDeptid()).isEqualTo(DEFAULT_DEPTID);
        assertThat(testDDept.getDeptname()).isEqualTo(DEFAULT_DEPTNAME);

        // Validate the DDept in Elasticsearch
        verify(mockDDeptSearchRepository, times(1)).save(testDDept);
    }

    @Test
    @Transactional
    void createDDeptWithExistingId() throws Exception {
        // Create the DDept with an existing ID
        dDept.setId(1L);
        DDeptDTO dDeptDTO = dDeptMapper.toDto(dDept);

        int databaseSizeBeforeCreate = dDeptRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDDeptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDeptDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DDept in the database
        List<DDept> dDeptList = dDeptRepository.findAll();
        assertThat(dDeptList).hasSize(databaseSizeBeforeCreate);

        // Validate the DDept in Elasticsearch
        verify(mockDDeptSearchRepository, times(0)).save(dDept);
    }

    @Test
    @Transactional
    void checkDeptidIsRequired() throws Exception {
        int databaseSizeBeforeTest = dDeptRepository.findAll().size();
        // set the field null
        dDept.setDeptid(null);

        // Create the DDept, which fails.
        DDeptDTO dDeptDTO = dDeptMapper.toDto(dDept);

        restDDeptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDeptDTO)))
            .andExpect(status().isBadRequest());

        List<DDept> dDeptList = dDeptRepository.findAll();
        assertThat(dDeptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDeptnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dDeptRepository.findAll().size();
        // set the field null
        dDept.setDeptname(null);

        // Create the DDept, which fails.
        DDeptDTO dDeptDTO = dDeptMapper.toDto(dDept);

        restDDeptMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDeptDTO)))
            .andExpect(status().isBadRequest());

        List<DDept> dDeptList = dDeptRepository.findAll();
        assertThat(dDeptList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDDepts() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList
        restDDeptMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dDept.getId().intValue())))
            .andExpect(jsonPath("$.[*].deptid").value(hasItem(DEFAULT_DEPTID.intValue())))
            .andExpect(jsonPath("$.[*].deptname").value(hasItem(DEFAULT_DEPTNAME)));
    }

    @Test
    @Transactional
    void getDDept() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get the dDept
        restDDeptMockMvc
            .perform(get(ENTITY_API_URL_ID, dDept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dDept.getId().intValue()))
            .andExpect(jsonPath("$.deptid").value(DEFAULT_DEPTID.intValue()))
            .andExpect(jsonPath("$.deptname").value(DEFAULT_DEPTNAME));
    }

    @Test
    @Transactional
    void getDDeptsByIdFiltering() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        Long id = dDept.getId();

        defaultDDeptShouldBeFound("id.equals=" + id);
        defaultDDeptShouldNotBeFound("id.notEquals=" + id);

        defaultDDeptShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDDeptShouldNotBeFound("id.greaterThan=" + id);

        defaultDDeptShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDDeptShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDDeptsByDeptidIsEqualToSomething() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList where deptid equals to DEFAULT_DEPTID
        defaultDDeptShouldBeFound("deptid.equals=" + DEFAULT_DEPTID);

        // Get all the dDeptList where deptid equals to UPDATED_DEPTID
        defaultDDeptShouldNotBeFound("deptid.equals=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllDDeptsByDeptidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList where deptid not equals to DEFAULT_DEPTID
        defaultDDeptShouldNotBeFound("deptid.notEquals=" + DEFAULT_DEPTID);

        // Get all the dDeptList where deptid not equals to UPDATED_DEPTID
        defaultDDeptShouldBeFound("deptid.notEquals=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllDDeptsByDeptidIsInShouldWork() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList where deptid in DEFAULT_DEPTID or UPDATED_DEPTID
        defaultDDeptShouldBeFound("deptid.in=" + DEFAULT_DEPTID + "," + UPDATED_DEPTID);

        // Get all the dDeptList where deptid equals to UPDATED_DEPTID
        defaultDDeptShouldNotBeFound("deptid.in=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllDDeptsByDeptidIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList where deptid is not null
        defaultDDeptShouldBeFound("deptid.specified=true");

        // Get all the dDeptList where deptid is null
        defaultDDeptShouldNotBeFound("deptid.specified=false");
    }

    @Test
    @Transactional
    void getAllDDeptsByDeptidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList where deptid is greater than or equal to DEFAULT_DEPTID
        defaultDDeptShouldBeFound("deptid.greaterThanOrEqual=" + DEFAULT_DEPTID);

        // Get all the dDeptList where deptid is greater than or equal to UPDATED_DEPTID
        defaultDDeptShouldNotBeFound("deptid.greaterThanOrEqual=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllDDeptsByDeptidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList where deptid is less than or equal to DEFAULT_DEPTID
        defaultDDeptShouldBeFound("deptid.lessThanOrEqual=" + DEFAULT_DEPTID);

        // Get all the dDeptList where deptid is less than or equal to SMALLER_DEPTID
        defaultDDeptShouldNotBeFound("deptid.lessThanOrEqual=" + SMALLER_DEPTID);
    }

    @Test
    @Transactional
    void getAllDDeptsByDeptidIsLessThanSomething() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList where deptid is less than DEFAULT_DEPTID
        defaultDDeptShouldNotBeFound("deptid.lessThan=" + DEFAULT_DEPTID);

        // Get all the dDeptList where deptid is less than UPDATED_DEPTID
        defaultDDeptShouldBeFound("deptid.lessThan=" + UPDATED_DEPTID);
    }

    @Test
    @Transactional
    void getAllDDeptsByDeptidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList where deptid is greater than DEFAULT_DEPTID
        defaultDDeptShouldNotBeFound("deptid.greaterThan=" + DEFAULT_DEPTID);

        // Get all the dDeptList where deptid is greater than SMALLER_DEPTID
        defaultDDeptShouldBeFound("deptid.greaterThan=" + SMALLER_DEPTID);
    }

    @Test
    @Transactional
    void getAllDDeptsByDeptnameIsEqualToSomething() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList where deptname equals to DEFAULT_DEPTNAME
        defaultDDeptShouldBeFound("deptname.equals=" + DEFAULT_DEPTNAME);

        // Get all the dDeptList where deptname equals to UPDATED_DEPTNAME
        defaultDDeptShouldNotBeFound("deptname.equals=" + UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void getAllDDeptsByDeptnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList where deptname not equals to DEFAULT_DEPTNAME
        defaultDDeptShouldNotBeFound("deptname.notEquals=" + DEFAULT_DEPTNAME);

        // Get all the dDeptList where deptname not equals to UPDATED_DEPTNAME
        defaultDDeptShouldBeFound("deptname.notEquals=" + UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void getAllDDeptsByDeptnameIsInShouldWork() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList where deptname in DEFAULT_DEPTNAME or UPDATED_DEPTNAME
        defaultDDeptShouldBeFound("deptname.in=" + DEFAULT_DEPTNAME + "," + UPDATED_DEPTNAME);

        // Get all the dDeptList where deptname equals to UPDATED_DEPTNAME
        defaultDDeptShouldNotBeFound("deptname.in=" + UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void getAllDDeptsByDeptnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList where deptname is not null
        defaultDDeptShouldBeFound("deptname.specified=true");

        // Get all the dDeptList where deptname is null
        defaultDDeptShouldNotBeFound("deptname.specified=false");
    }

    @Test
    @Transactional
    void getAllDDeptsByDeptnameContainsSomething() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList where deptname contains DEFAULT_DEPTNAME
        defaultDDeptShouldBeFound("deptname.contains=" + DEFAULT_DEPTNAME);

        // Get all the dDeptList where deptname contains UPDATED_DEPTNAME
        defaultDDeptShouldNotBeFound("deptname.contains=" + UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void getAllDDeptsByDeptnameNotContainsSomething() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        // Get all the dDeptList where deptname does not contain DEFAULT_DEPTNAME
        defaultDDeptShouldNotBeFound("deptname.doesNotContain=" + DEFAULT_DEPTNAME);

        // Get all the dDeptList where deptname does not contain UPDATED_DEPTNAME
        defaultDDeptShouldBeFound("deptname.doesNotContain=" + UPDATED_DEPTNAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDDeptShouldBeFound(String filter) throws Exception {
        restDDeptMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dDept.getId().intValue())))
            .andExpect(jsonPath("$.[*].deptid").value(hasItem(DEFAULT_DEPTID.intValue())))
            .andExpect(jsonPath("$.[*].deptname").value(hasItem(DEFAULT_DEPTNAME)));

        // Check, that the count call also returns 1
        restDDeptMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDDeptShouldNotBeFound(String filter) throws Exception {
        restDDeptMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDDeptMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDDept() throws Exception {
        // Get the dDept
        restDDeptMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDDept() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        int databaseSizeBeforeUpdate = dDeptRepository.findAll().size();

        // Update the dDept
        DDept updatedDDept = dDeptRepository.findById(dDept.getId()).get();
        // Disconnect from session so that the updates on updatedDDept are not directly saved in db
        em.detach(updatedDDept);
        updatedDDept.deptid(UPDATED_DEPTID).deptname(UPDATED_DEPTNAME);
        DDeptDTO dDeptDTO = dDeptMapper.toDto(updatedDDept);

        restDDeptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dDeptDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dDeptDTO))
            )
            .andExpect(status().isOk());

        // Validate the DDept in the database
        List<DDept> dDeptList = dDeptRepository.findAll();
        assertThat(dDeptList).hasSize(databaseSizeBeforeUpdate);
        DDept testDDept = dDeptList.get(dDeptList.size() - 1);
        assertThat(testDDept.getDeptid()).isEqualTo(UPDATED_DEPTID);
        assertThat(testDDept.getDeptname()).isEqualTo(UPDATED_DEPTNAME);

        // Validate the DDept in Elasticsearch
        verify(mockDDeptSearchRepository).save(testDDept);
    }

    @Test
    @Transactional
    void putNonExistingDDept() throws Exception {
        int databaseSizeBeforeUpdate = dDeptRepository.findAll().size();
        dDept.setId(count.incrementAndGet());

        // Create the DDept
        DDeptDTO dDeptDTO = dDeptMapper.toDto(dDept);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDDeptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dDeptDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dDeptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DDept in the database
        List<DDept> dDeptList = dDeptRepository.findAll();
        assertThat(dDeptList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDept in Elasticsearch
        verify(mockDDeptSearchRepository, times(0)).save(dDept);
    }

    @Test
    @Transactional
    void putWithIdMismatchDDept() throws Exception {
        int databaseSizeBeforeUpdate = dDeptRepository.findAll().size();
        dDept.setId(count.incrementAndGet());

        // Create the DDept
        DDeptDTO dDeptDTO = dDeptMapper.toDto(dDept);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDDeptMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dDeptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DDept in the database
        List<DDept> dDeptList = dDeptRepository.findAll();
        assertThat(dDeptList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDept in Elasticsearch
        verify(mockDDeptSearchRepository, times(0)).save(dDept);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDDept() throws Exception {
        int databaseSizeBeforeUpdate = dDeptRepository.findAll().size();
        dDept.setId(count.incrementAndGet());

        // Create the DDept
        DDeptDTO dDeptDTO = dDeptMapper.toDto(dDept);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDDeptMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dDeptDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DDept in the database
        List<DDept> dDeptList = dDeptRepository.findAll();
        assertThat(dDeptList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDept in Elasticsearch
        verify(mockDDeptSearchRepository, times(0)).save(dDept);
    }

    @Test
    @Transactional
    void partialUpdateDDeptWithPatch() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        int databaseSizeBeforeUpdate = dDeptRepository.findAll().size();

        // Update the dDept using partial update
        DDept partialUpdatedDDept = new DDept();
        partialUpdatedDDept.setId(dDept.getId());

        partialUpdatedDDept.deptid(UPDATED_DEPTID).deptname(UPDATED_DEPTNAME);

        restDDeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDDept.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDDept))
            )
            .andExpect(status().isOk());

        // Validate the DDept in the database
        List<DDept> dDeptList = dDeptRepository.findAll();
        assertThat(dDeptList).hasSize(databaseSizeBeforeUpdate);
        DDept testDDept = dDeptList.get(dDeptList.size() - 1);
        assertThat(testDDept.getDeptid()).isEqualTo(UPDATED_DEPTID);
        assertThat(testDDept.getDeptname()).isEqualTo(UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void fullUpdateDDeptWithPatch() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        int databaseSizeBeforeUpdate = dDeptRepository.findAll().size();

        // Update the dDept using partial update
        DDept partialUpdatedDDept = new DDept();
        partialUpdatedDDept.setId(dDept.getId());

        partialUpdatedDDept.deptid(UPDATED_DEPTID).deptname(UPDATED_DEPTNAME);

        restDDeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDDept.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDDept))
            )
            .andExpect(status().isOk());

        // Validate the DDept in the database
        List<DDept> dDeptList = dDeptRepository.findAll();
        assertThat(dDeptList).hasSize(databaseSizeBeforeUpdate);
        DDept testDDept = dDeptList.get(dDeptList.size() - 1);
        assertThat(testDDept.getDeptid()).isEqualTo(UPDATED_DEPTID);
        assertThat(testDDept.getDeptname()).isEqualTo(UPDATED_DEPTNAME);
    }

    @Test
    @Transactional
    void patchNonExistingDDept() throws Exception {
        int databaseSizeBeforeUpdate = dDeptRepository.findAll().size();
        dDept.setId(count.incrementAndGet());

        // Create the DDept
        DDeptDTO dDeptDTO = dDeptMapper.toDto(dDept);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDDeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dDeptDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dDeptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DDept in the database
        List<DDept> dDeptList = dDeptRepository.findAll();
        assertThat(dDeptList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDept in Elasticsearch
        verify(mockDDeptSearchRepository, times(0)).save(dDept);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDDept() throws Exception {
        int databaseSizeBeforeUpdate = dDeptRepository.findAll().size();
        dDept.setId(count.incrementAndGet());

        // Create the DDept
        DDeptDTO dDeptDTO = dDeptMapper.toDto(dDept);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDDeptMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dDeptDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DDept in the database
        List<DDept> dDeptList = dDeptRepository.findAll();
        assertThat(dDeptList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDept in Elasticsearch
        verify(mockDDeptSearchRepository, times(0)).save(dDept);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDDept() throws Exception {
        int databaseSizeBeforeUpdate = dDeptRepository.findAll().size();
        dDept.setId(count.incrementAndGet());

        // Create the DDept
        DDeptDTO dDeptDTO = dDeptMapper.toDto(dDept);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDDeptMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dDeptDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DDept in the database
        List<DDept> dDeptList = dDeptRepository.findAll();
        assertThat(dDeptList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DDept in Elasticsearch
        verify(mockDDeptSearchRepository, times(0)).save(dDept);
    }

    @Test
    @Transactional
    void deleteDDept() throws Exception {
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);

        int databaseSizeBeforeDelete = dDeptRepository.findAll().size();

        // Delete the dDept
        restDDeptMockMvc
            .perform(delete(ENTITY_API_URL_ID, dDept.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DDept> dDeptList = dDeptRepository.findAll();
        assertThat(dDeptList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DDept in Elasticsearch
        verify(mockDDeptSearchRepository, times(1)).deleteById(dDept.getId());
    }

    @Test
    @Transactional
    void searchDDept() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dDeptRepository.saveAndFlush(dDept);
        when(mockDDeptSearchRepository.search(queryStringQuery("id:" + dDept.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dDept), PageRequest.of(0, 1), 1));

        // Search the dDept
        restDDeptMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dDept.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dDept.getId().intValue())))
            .andExpect(jsonPath("$.[*].deptid").value(hasItem(DEFAULT_DEPTID.intValue())))
            .andExpect(jsonPath("$.[*].deptname").value(hasItem(DEFAULT_DEPTNAME)));
    }
}
