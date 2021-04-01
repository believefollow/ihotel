package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Code1;
import ihotel.app.repository.Code1Repository;
import ihotel.app.repository.search.Code1SearchRepository;
import ihotel.app.service.criteria.Code1Criteria;
import ihotel.app.service.dto.Code1DTO;
import ihotel.app.service.mapper.Code1Mapper;
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
 * Integration tests for the {@link Code1Resource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class Code1ResourceIT {

    private static final String DEFAULT_CODE_1 = "AAAAAAAAAA";
    private static final String UPDATED_CODE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_2 = "AAAAAAAAAA";
    private static final String UPDATED_CODE_2 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/code-1-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/code-1-s";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private Code1Repository code1Repository;

    @Autowired
    private Code1Mapper code1Mapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.Code1SearchRepositoryMockConfiguration
     */
    @Autowired
    private Code1SearchRepository mockCode1SearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCode1MockMvc;

    private Code1 code1;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Code1 createEntity(EntityManager em) {
        Code1 code1 = new Code1().code1(DEFAULT_CODE_1).code2(DEFAULT_CODE_2);
        return code1;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Code1 createUpdatedEntity(EntityManager em) {
        Code1 code1 = new Code1().code1(UPDATED_CODE_1).code2(UPDATED_CODE_2);
        return code1;
    }

    @BeforeEach
    public void initTest() {
        code1 = createEntity(em);
    }

    @Test
    @Transactional
    void createCode1() throws Exception {
        int databaseSizeBeforeCreate = code1Repository.findAll().size();
        // Create the Code1
        Code1DTO code1DTO = code1Mapper.toDto(code1);
        restCode1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(code1DTO)))
            .andExpect(status().isCreated());

        // Validate the Code1 in the database
        List<Code1> code1List = code1Repository.findAll();
        assertThat(code1List).hasSize(databaseSizeBeforeCreate + 1);
        Code1 testCode1 = code1List.get(code1List.size() - 1);
        assertThat(testCode1.getCode1()).isEqualTo(DEFAULT_CODE_1);
        assertThat(testCode1.getCode2()).isEqualTo(DEFAULT_CODE_2);

        // Validate the Code1 in Elasticsearch
        verify(mockCode1SearchRepository, times(1)).save(testCode1);
    }

    @Test
    @Transactional
    void createCode1WithExistingId() throws Exception {
        // Create the Code1 with an existing ID
        code1.setId(1L);
        Code1DTO code1DTO = code1Mapper.toDto(code1);

        int databaseSizeBeforeCreate = code1Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCode1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(code1DTO)))
            .andExpect(status().isBadRequest());

        // Validate the Code1 in the database
        List<Code1> code1List = code1Repository.findAll();
        assertThat(code1List).hasSize(databaseSizeBeforeCreate);

        // Validate the Code1 in Elasticsearch
        verify(mockCode1SearchRepository, times(0)).save(code1);
    }

    @Test
    @Transactional
    void checkCode1IsRequired() throws Exception {
        int databaseSizeBeforeTest = code1Repository.findAll().size();
        // set the field null
        code1.setCode1(null);

        // Create the Code1, which fails.
        Code1DTO code1DTO = code1Mapper.toDto(code1);

        restCode1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(code1DTO)))
            .andExpect(status().isBadRequest());

        List<Code1> code1List = code1Repository.findAll();
        assertThat(code1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCode2IsRequired() throws Exception {
        int databaseSizeBeforeTest = code1Repository.findAll().size();
        // set the field null
        code1.setCode2(null);

        // Create the Code1, which fails.
        Code1DTO code1DTO = code1Mapper.toDto(code1);

        restCode1MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(code1DTO)))
            .andExpect(status().isBadRequest());

        List<Code1> code1List = code1Repository.findAll();
        assertThat(code1List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCode1s() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        // Get all the code1List
        restCode1MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(code1.getId().intValue())))
            .andExpect(jsonPath("$.[*].code1").value(hasItem(DEFAULT_CODE_1)))
            .andExpect(jsonPath("$.[*].code2").value(hasItem(DEFAULT_CODE_2)));
    }

    @Test
    @Transactional
    void getCode1() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        // Get the code1
        restCode1MockMvc
            .perform(get(ENTITY_API_URL_ID, code1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(code1.getId().intValue()))
            .andExpect(jsonPath("$.code1").value(DEFAULT_CODE_1))
            .andExpect(jsonPath("$.code2").value(DEFAULT_CODE_2));
    }

    @Test
    @Transactional
    void getCode1sByIdFiltering() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        Long id = code1.getId();

        defaultCode1ShouldBeFound("id.equals=" + id);
        defaultCode1ShouldNotBeFound("id.notEquals=" + id);

        defaultCode1ShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCode1ShouldNotBeFound("id.greaterThan=" + id);

        defaultCode1ShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCode1ShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCode1sByCode1IsEqualToSomething() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        // Get all the code1List where code1 equals to DEFAULT_CODE_1
        defaultCode1ShouldBeFound("code1.equals=" + DEFAULT_CODE_1);

        // Get all the code1List where code1 equals to UPDATED_CODE_1
        defaultCode1ShouldNotBeFound("code1.equals=" + UPDATED_CODE_1);
    }

    @Test
    @Transactional
    void getAllCode1sByCode1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        // Get all the code1List where code1 not equals to DEFAULT_CODE_1
        defaultCode1ShouldNotBeFound("code1.notEquals=" + DEFAULT_CODE_1);

        // Get all the code1List where code1 not equals to UPDATED_CODE_1
        defaultCode1ShouldBeFound("code1.notEquals=" + UPDATED_CODE_1);
    }

    @Test
    @Transactional
    void getAllCode1sByCode1IsInShouldWork() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        // Get all the code1List where code1 in DEFAULT_CODE_1 or UPDATED_CODE_1
        defaultCode1ShouldBeFound("code1.in=" + DEFAULT_CODE_1 + "," + UPDATED_CODE_1);

        // Get all the code1List where code1 equals to UPDATED_CODE_1
        defaultCode1ShouldNotBeFound("code1.in=" + UPDATED_CODE_1);
    }

    @Test
    @Transactional
    void getAllCode1sByCode1IsNullOrNotNull() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        // Get all the code1List where code1 is not null
        defaultCode1ShouldBeFound("code1.specified=true");

        // Get all the code1List where code1 is null
        defaultCode1ShouldNotBeFound("code1.specified=false");
    }

    @Test
    @Transactional
    void getAllCode1sByCode1ContainsSomething() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        // Get all the code1List where code1 contains DEFAULT_CODE_1
        defaultCode1ShouldBeFound("code1.contains=" + DEFAULT_CODE_1);

        // Get all the code1List where code1 contains UPDATED_CODE_1
        defaultCode1ShouldNotBeFound("code1.contains=" + UPDATED_CODE_1);
    }

    @Test
    @Transactional
    void getAllCode1sByCode1NotContainsSomething() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        // Get all the code1List where code1 does not contain DEFAULT_CODE_1
        defaultCode1ShouldNotBeFound("code1.doesNotContain=" + DEFAULT_CODE_1);

        // Get all the code1List where code1 does not contain UPDATED_CODE_1
        defaultCode1ShouldBeFound("code1.doesNotContain=" + UPDATED_CODE_1);
    }

    @Test
    @Transactional
    void getAllCode1sByCode2IsEqualToSomething() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        // Get all the code1List where code2 equals to DEFAULT_CODE_2
        defaultCode1ShouldBeFound("code2.equals=" + DEFAULT_CODE_2);

        // Get all the code1List where code2 equals to UPDATED_CODE_2
        defaultCode1ShouldNotBeFound("code2.equals=" + UPDATED_CODE_2);
    }

    @Test
    @Transactional
    void getAllCode1sByCode2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        // Get all the code1List where code2 not equals to DEFAULT_CODE_2
        defaultCode1ShouldNotBeFound("code2.notEquals=" + DEFAULT_CODE_2);

        // Get all the code1List where code2 not equals to UPDATED_CODE_2
        defaultCode1ShouldBeFound("code2.notEquals=" + UPDATED_CODE_2);
    }

    @Test
    @Transactional
    void getAllCode1sByCode2IsInShouldWork() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        // Get all the code1List where code2 in DEFAULT_CODE_2 or UPDATED_CODE_2
        defaultCode1ShouldBeFound("code2.in=" + DEFAULT_CODE_2 + "," + UPDATED_CODE_2);

        // Get all the code1List where code2 equals to UPDATED_CODE_2
        defaultCode1ShouldNotBeFound("code2.in=" + UPDATED_CODE_2);
    }

    @Test
    @Transactional
    void getAllCode1sByCode2IsNullOrNotNull() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        // Get all the code1List where code2 is not null
        defaultCode1ShouldBeFound("code2.specified=true");

        // Get all the code1List where code2 is null
        defaultCode1ShouldNotBeFound("code2.specified=false");
    }

    @Test
    @Transactional
    void getAllCode1sByCode2ContainsSomething() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        // Get all the code1List where code2 contains DEFAULT_CODE_2
        defaultCode1ShouldBeFound("code2.contains=" + DEFAULT_CODE_2);

        // Get all the code1List where code2 contains UPDATED_CODE_2
        defaultCode1ShouldNotBeFound("code2.contains=" + UPDATED_CODE_2);
    }

    @Test
    @Transactional
    void getAllCode1sByCode2NotContainsSomething() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        // Get all the code1List where code2 does not contain DEFAULT_CODE_2
        defaultCode1ShouldNotBeFound("code2.doesNotContain=" + DEFAULT_CODE_2);

        // Get all the code1List where code2 does not contain UPDATED_CODE_2
        defaultCode1ShouldBeFound("code2.doesNotContain=" + UPDATED_CODE_2);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCode1ShouldBeFound(String filter) throws Exception {
        restCode1MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(code1.getId().intValue())))
            .andExpect(jsonPath("$.[*].code1").value(hasItem(DEFAULT_CODE_1)))
            .andExpect(jsonPath("$.[*].code2").value(hasItem(DEFAULT_CODE_2)));

        // Check, that the count call also returns 1
        restCode1MockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCode1ShouldNotBeFound(String filter) throws Exception {
        restCode1MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCode1MockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCode1() throws Exception {
        // Get the code1
        restCode1MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCode1() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        int databaseSizeBeforeUpdate = code1Repository.findAll().size();

        // Update the code1
        Code1 updatedCode1 = code1Repository.findById(code1.getId()).get();
        // Disconnect from session so that the updates on updatedCode1 are not directly saved in db
        em.detach(updatedCode1);
        updatedCode1.code1(UPDATED_CODE_1).code2(UPDATED_CODE_2);
        Code1DTO code1DTO = code1Mapper.toDto(updatedCode1);

        restCode1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, code1DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(code1DTO))
            )
            .andExpect(status().isOk());

        // Validate the Code1 in the database
        List<Code1> code1List = code1Repository.findAll();
        assertThat(code1List).hasSize(databaseSizeBeforeUpdate);
        Code1 testCode1 = code1List.get(code1List.size() - 1);
        assertThat(testCode1.getCode1()).isEqualTo(UPDATED_CODE_1);
        assertThat(testCode1.getCode2()).isEqualTo(UPDATED_CODE_2);

        // Validate the Code1 in Elasticsearch
        verify(mockCode1SearchRepository).save(testCode1);
    }

    @Test
    @Transactional
    void putNonExistingCode1() throws Exception {
        int databaseSizeBeforeUpdate = code1Repository.findAll().size();
        code1.setId(count.incrementAndGet());

        // Create the Code1
        Code1DTO code1DTO = code1Mapper.toDto(code1);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCode1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, code1DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(code1DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Code1 in the database
        List<Code1> code1List = code1Repository.findAll();
        assertThat(code1List).hasSize(databaseSizeBeforeUpdate);

        // Validate the Code1 in Elasticsearch
        verify(mockCode1SearchRepository, times(0)).save(code1);
    }

    @Test
    @Transactional
    void putWithIdMismatchCode1() throws Exception {
        int databaseSizeBeforeUpdate = code1Repository.findAll().size();
        code1.setId(count.incrementAndGet());

        // Create the Code1
        Code1DTO code1DTO = code1Mapper.toDto(code1);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCode1MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(code1DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Code1 in the database
        List<Code1> code1List = code1Repository.findAll();
        assertThat(code1List).hasSize(databaseSizeBeforeUpdate);

        // Validate the Code1 in Elasticsearch
        verify(mockCode1SearchRepository, times(0)).save(code1);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCode1() throws Exception {
        int databaseSizeBeforeUpdate = code1Repository.findAll().size();
        code1.setId(count.incrementAndGet());

        // Create the Code1
        Code1DTO code1DTO = code1Mapper.toDto(code1);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCode1MockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(code1DTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Code1 in the database
        List<Code1> code1List = code1Repository.findAll();
        assertThat(code1List).hasSize(databaseSizeBeforeUpdate);

        // Validate the Code1 in Elasticsearch
        verify(mockCode1SearchRepository, times(0)).save(code1);
    }

    @Test
    @Transactional
    void partialUpdateCode1WithPatch() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        int databaseSizeBeforeUpdate = code1Repository.findAll().size();

        // Update the code1 using partial update
        Code1 partialUpdatedCode1 = new Code1();
        partialUpdatedCode1.setId(code1.getId());

        partialUpdatedCode1.code1(UPDATED_CODE_1);

        restCode1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCode1.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCode1))
            )
            .andExpect(status().isOk());

        // Validate the Code1 in the database
        List<Code1> code1List = code1Repository.findAll();
        assertThat(code1List).hasSize(databaseSizeBeforeUpdate);
        Code1 testCode1 = code1List.get(code1List.size() - 1);
        assertThat(testCode1.getCode1()).isEqualTo(UPDATED_CODE_1);
        assertThat(testCode1.getCode2()).isEqualTo(DEFAULT_CODE_2);
    }

    @Test
    @Transactional
    void fullUpdateCode1WithPatch() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        int databaseSizeBeforeUpdate = code1Repository.findAll().size();

        // Update the code1 using partial update
        Code1 partialUpdatedCode1 = new Code1();
        partialUpdatedCode1.setId(code1.getId());

        partialUpdatedCode1.code1(UPDATED_CODE_1).code2(UPDATED_CODE_2);

        restCode1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCode1.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCode1))
            )
            .andExpect(status().isOk());

        // Validate the Code1 in the database
        List<Code1> code1List = code1Repository.findAll();
        assertThat(code1List).hasSize(databaseSizeBeforeUpdate);
        Code1 testCode1 = code1List.get(code1List.size() - 1);
        assertThat(testCode1.getCode1()).isEqualTo(UPDATED_CODE_1);
        assertThat(testCode1.getCode2()).isEqualTo(UPDATED_CODE_2);
    }

    @Test
    @Transactional
    void patchNonExistingCode1() throws Exception {
        int databaseSizeBeforeUpdate = code1Repository.findAll().size();
        code1.setId(count.incrementAndGet());

        // Create the Code1
        Code1DTO code1DTO = code1Mapper.toDto(code1);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCode1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, code1DTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(code1DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Code1 in the database
        List<Code1> code1List = code1Repository.findAll();
        assertThat(code1List).hasSize(databaseSizeBeforeUpdate);

        // Validate the Code1 in Elasticsearch
        verify(mockCode1SearchRepository, times(0)).save(code1);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCode1() throws Exception {
        int databaseSizeBeforeUpdate = code1Repository.findAll().size();
        code1.setId(count.incrementAndGet());

        // Create the Code1
        Code1DTO code1DTO = code1Mapper.toDto(code1);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCode1MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(code1DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Code1 in the database
        List<Code1> code1List = code1Repository.findAll();
        assertThat(code1List).hasSize(databaseSizeBeforeUpdate);

        // Validate the Code1 in Elasticsearch
        verify(mockCode1SearchRepository, times(0)).save(code1);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCode1() throws Exception {
        int databaseSizeBeforeUpdate = code1Repository.findAll().size();
        code1.setId(count.incrementAndGet());

        // Create the Code1
        Code1DTO code1DTO = code1Mapper.toDto(code1);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCode1MockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(code1DTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Code1 in the database
        List<Code1> code1List = code1Repository.findAll();
        assertThat(code1List).hasSize(databaseSizeBeforeUpdate);

        // Validate the Code1 in Elasticsearch
        verify(mockCode1SearchRepository, times(0)).save(code1);
    }

    @Test
    @Transactional
    void deleteCode1() throws Exception {
        // Initialize the database
        code1Repository.saveAndFlush(code1);

        int databaseSizeBeforeDelete = code1Repository.findAll().size();

        // Delete the code1
        restCode1MockMvc
            .perform(delete(ENTITY_API_URL_ID, code1.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Code1> code1List = code1Repository.findAll();
        assertThat(code1List).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Code1 in Elasticsearch
        verify(mockCode1SearchRepository, times(1)).deleteById(code1.getId());
    }

    @Test
    @Transactional
    void searchCode1() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        code1Repository.saveAndFlush(code1);
        when(mockCode1SearchRepository.search(queryStringQuery("id:" + code1.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(code1), PageRequest.of(0, 1), 1));

        // Search the code1
        restCode1MockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + code1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(code1.getId().intValue())))
            .andExpect(jsonPath("$.[*].code1").value(hasItem(DEFAULT_CODE_1)))
            .andExpect(jsonPath("$.[*].code2").value(hasItem(DEFAULT_CODE_2)));
    }
}
