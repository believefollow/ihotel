package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DCktype;
import ihotel.app.repository.DCktypeRepository;
import ihotel.app.repository.search.DCktypeSearchRepository;
import ihotel.app.service.criteria.DCktypeCriteria;
import ihotel.app.service.dto.DCktypeDTO;
import ihotel.app.service.mapper.DCktypeMapper;
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
 * Integration tests for the {@link DCktypeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DCktypeResourceIT {

    private static final String DEFAULT_CKTYPE = "AAAAAAAAAA";
    private static final String UPDATED_CKTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final String DEFAULT_SIGN = "AAAAAAAAAA";
    private static final String UPDATED_SIGN = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/d-cktypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-cktypes";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DCktypeRepository dCktypeRepository;

    @Autowired
    private DCktypeMapper dCktypeMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DCktypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private DCktypeSearchRepository mockDCktypeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDCktypeMockMvc;

    private DCktype dCktype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DCktype createEntity(EntityManager em) {
        DCktype dCktype = new DCktype().cktype(DEFAULT_CKTYPE).memo(DEFAULT_MEMO).sign(DEFAULT_SIGN);
        return dCktype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DCktype createUpdatedEntity(EntityManager em) {
        DCktype dCktype = new DCktype().cktype(UPDATED_CKTYPE).memo(UPDATED_MEMO).sign(UPDATED_SIGN);
        return dCktype;
    }

    @BeforeEach
    public void initTest() {
        dCktype = createEntity(em);
    }

    @Test
    @Transactional
    void createDCktype() throws Exception {
        int databaseSizeBeforeCreate = dCktypeRepository.findAll().size();
        // Create the DCktype
        DCktypeDTO dCktypeDTO = dCktypeMapper.toDto(dCktype);
        restDCktypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCktypeDTO)))
            .andExpect(status().isCreated());

        // Validate the DCktype in the database
        List<DCktype> dCktypeList = dCktypeRepository.findAll();
        assertThat(dCktypeList).hasSize(databaseSizeBeforeCreate + 1);
        DCktype testDCktype = dCktypeList.get(dCktypeList.size() - 1);
        assertThat(testDCktype.getCktype()).isEqualTo(DEFAULT_CKTYPE);
        assertThat(testDCktype.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testDCktype.getSign()).isEqualTo(DEFAULT_SIGN);

        // Validate the DCktype in Elasticsearch
        verify(mockDCktypeSearchRepository, times(1)).save(testDCktype);
    }

    @Test
    @Transactional
    void createDCktypeWithExistingId() throws Exception {
        // Create the DCktype with an existing ID
        dCktype.setId(1L);
        DCktypeDTO dCktypeDTO = dCktypeMapper.toDto(dCktype);

        int databaseSizeBeforeCreate = dCktypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDCktypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCktypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DCktype in the database
        List<DCktype> dCktypeList = dCktypeRepository.findAll();
        assertThat(dCktypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the DCktype in Elasticsearch
        verify(mockDCktypeSearchRepository, times(0)).save(dCktype);
    }

    @Test
    @Transactional
    void checkCktypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = dCktypeRepository.findAll().size();
        // set the field null
        dCktype.setCktype(null);

        // Create the DCktype, which fails.
        DCktypeDTO dCktypeDTO = dCktypeMapper.toDto(dCktype);

        restDCktypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCktypeDTO)))
            .andExpect(status().isBadRequest());

        List<DCktype> dCktypeList = dCktypeRepository.findAll();
        assertThat(dCktypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSignIsRequired() throws Exception {
        int databaseSizeBeforeTest = dCktypeRepository.findAll().size();
        // set the field null
        dCktype.setSign(null);

        // Create the DCktype, which fails.
        DCktypeDTO dCktypeDTO = dCktypeMapper.toDto(dCktype);

        restDCktypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCktypeDTO)))
            .andExpect(status().isBadRequest());

        List<DCktype> dCktypeList = dCktypeRepository.findAll();
        assertThat(dCktypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDCktypes() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList
        restDCktypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dCktype.getId().intValue())))
            .andExpect(jsonPath("$.[*].cktype").value(hasItem(DEFAULT_CKTYPE)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN)));
    }

    @Test
    @Transactional
    void getDCktype() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get the dCktype
        restDCktypeMockMvc
            .perform(get(ENTITY_API_URL_ID, dCktype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dCktype.getId().intValue()))
            .andExpect(jsonPath("$.cktype").value(DEFAULT_CKTYPE))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO))
            .andExpect(jsonPath("$.sign").value(DEFAULT_SIGN));
    }

    @Test
    @Transactional
    void getDCktypesByIdFiltering() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        Long id = dCktype.getId();

        defaultDCktypeShouldBeFound("id.equals=" + id);
        defaultDCktypeShouldNotBeFound("id.notEquals=" + id);

        defaultDCktypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDCktypeShouldNotBeFound("id.greaterThan=" + id);

        defaultDCktypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDCktypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDCktypesByCktypeIsEqualToSomething() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where cktype equals to DEFAULT_CKTYPE
        defaultDCktypeShouldBeFound("cktype.equals=" + DEFAULT_CKTYPE);

        // Get all the dCktypeList where cktype equals to UPDATED_CKTYPE
        defaultDCktypeShouldNotBeFound("cktype.equals=" + UPDATED_CKTYPE);
    }

    @Test
    @Transactional
    void getAllDCktypesByCktypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where cktype not equals to DEFAULT_CKTYPE
        defaultDCktypeShouldNotBeFound("cktype.notEquals=" + DEFAULT_CKTYPE);

        // Get all the dCktypeList where cktype not equals to UPDATED_CKTYPE
        defaultDCktypeShouldBeFound("cktype.notEquals=" + UPDATED_CKTYPE);
    }

    @Test
    @Transactional
    void getAllDCktypesByCktypeIsInShouldWork() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where cktype in DEFAULT_CKTYPE or UPDATED_CKTYPE
        defaultDCktypeShouldBeFound("cktype.in=" + DEFAULT_CKTYPE + "," + UPDATED_CKTYPE);

        // Get all the dCktypeList where cktype equals to UPDATED_CKTYPE
        defaultDCktypeShouldNotBeFound("cktype.in=" + UPDATED_CKTYPE);
    }

    @Test
    @Transactional
    void getAllDCktypesByCktypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where cktype is not null
        defaultDCktypeShouldBeFound("cktype.specified=true");

        // Get all the dCktypeList where cktype is null
        defaultDCktypeShouldNotBeFound("cktype.specified=false");
    }

    @Test
    @Transactional
    void getAllDCktypesByCktypeContainsSomething() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where cktype contains DEFAULT_CKTYPE
        defaultDCktypeShouldBeFound("cktype.contains=" + DEFAULT_CKTYPE);

        // Get all the dCktypeList where cktype contains UPDATED_CKTYPE
        defaultDCktypeShouldNotBeFound("cktype.contains=" + UPDATED_CKTYPE);
    }

    @Test
    @Transactional
    void getAllDCktypesByCktypeNotContainsSomething() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where cktype does not contain DEFAULT_CKTYPE
        defaultDCktypeShouldNotBeFound("cktype.doesNotContain=" + DEFAULT_CKTYPE);

        // Get all the dCktypeList where cktype does not contain UPDATED_CKTYPE
        defaultDCktypeShouldBeFound("cktype.doesNotContain=" + UPDATED_CKTYPE);
    }

    @Test
    @Transactional
    void getAllDCktypesByMemoIsEqualToSomething() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where memo equals to DEFAULT_MEMO
        defaultDCktypeShouldBeFound("memo.equals=" + DEFAULT_MEMO);

        // Get all the dCktypeList where memo equals to UPDATED_MEMO
        defaultDCktypeShouldNotBeFound("memo.equals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDCktypesByMemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where memo not equals to DEFAULT_MEMO
        defaultDCktypeShouldNotBeFound("memo.notEquals=" + DEFAULT_MEMO);

        // Get all the dCktypeList where memo not equals to UPDATED_MEMO
        defaultDCktypeShouldBeFound("memo.notEquals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDCktypesByMemoIsInShouldWork() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where memo in DEFAULT_MEMO or UPDATED_MEMO
        defaultDCktypeShouldBeFound("memo.in=" + DEFAULT_MEMO + "," + UPDATED_MEMO);

        // Get all the dCktypeList where memo equals to UPDATED_MEMO
        defaultDCktypeShouldNotBeFound("memo.in=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDCktypesByMemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where memo is not null
        defaultDCktypeShouldBeFound("memo.specified=true");

        // Get all the dCktypeList where memo is null
        defaultDCktypeShouldNotBeFound("memo.specified=false");
    }

    @Test
    @Transactional
    void getAllDCktypesByMemoContainsSomething() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where memo contains DEFAULT_MEMO
        defaultDCktypeShouldBeFound("memo.contains=" + DEFAULT_MEMO);

        // Get all the dCktypeList where memo contains UPDATED_MEMO
        defaultDCktypeShouldNotBeFound("memo.contains=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDCktypesByMemoNotContainsSomething() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where memo does not contain DEFAULT_MEMO
        defaultDCktypeShouldNotBeFound("memo.doesNotContain=" + DEFAULT_MEMO);

        // Get all the dCktypeList where memo does not contain UPDATED_MEMO
        defaultDCktypeShouldBeFound("memo.doesNotContain=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllDCktypesBySignIsEqualToSomething() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where sign equals to DEFAULT_SIGN
        defaultDCktypeShouldBeFound("sign.equals=" + DEFAULT_SIGN);

        // Get all the dCktypeList where sign equals to UPDATED_SIGN
        defaultDCktypeShouldNotBeFound("sign.equals=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllDCktypesBySignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where sign not equals to DEFAULT_SIGN
        defaultDCktypeShouldNotBeFound("sign.notEquals=" + DEFAULT_SIGN);

        // Get all the dCktypeList where sign not equals to UPDATED_SIGN
        defaultDCktypeShouldBeFound("sign.notEquals=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllDCktypesBySignIsInShouldWork() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where sign in DEFAULT_SIGN or UPDATED_SIGN
        defaultDCktypeShouldBeFound("sign.in=" + DEFAULT_SIGN + "," + UPDATED_SIGN);

        // Get all the dCktypeList where sign equals to UPDATED_SIGN
        defaultDCktypeShouldNotBeFound("sign.in=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllDCktypesBySignIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where sign is not null
        defaultDCktypeShouldBeFound("sign.specified=true");

        // Get all the dCktypeList where sign is null
        defaultDCktypeShouldNotBeFound("sign.specified=false");
    }

    @Test
    @Transactional
    void getAllDCktypesBySignContainsSomething() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where sign contains DEFAULT_SIGN
        defaultDCktypeShouldBeFound("sign.contains=" + DEFAULT_SIGN);

        // Get all the dCktypeList where sign contains UPDATED_SIGN
        defaultDCktypeShouldNotBeFound("sign.contains=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllDCktypesBySignNotContainsSomething() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        // Get all the dCktypeList where sign does not contain DEFAULT_SIGN
        defaultDCktypeShouldNotBeFound("sign.doesNotContain=" + DEFAULT_SIGN);

        // Get all the dCktypeList where sign does not contain UPDATED_SIGN
        defaultDCktypeShouldBeFound("sign.doesNotContain=" + UPDATED_SIGN);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDCktypeShouldBeFound(String filter) throws Exception {
        restDCktypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dCktype.getId().intValue())))
            .andExpect(jsonPath("$.[*].cktype").value(hasItem(DEFAULT_CKTYPE)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN)));

        // Check, that the count call also returns 1
        restDCktypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDCktypeShouldNotBeFound(String filter) throws Exception {
        restDCktypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDCktypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDCktype() throws Exception {
        // Get the dCktype
        restDCktypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDCktype() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        int databaseSizeBeforeUpdate = dCktypeRepository.findAll().size();

        // Update the dCktype
        DCktype updatedDCktype = dCktypeRepository.findById(dCktype.getId()).get();
        // Disconnect from session so that the updates on updatedDCktype are not directly saved in db
        em.detach(updatedDCktype);
        updatedDCktype.cktype(UPDATED_CKTYPE).memo(UPDATED_MEMO).sign(UPDATED_SIGN);
        DCktypeDTO dCktypeDTO = dCktypeMapper.toDto(updatedDCktype);

        restDCktypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dCktypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dCktypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the DCktype in the database
        List<DCktype> dCktypeList = dCktypeRepository.findAll();
        assertThat(dCktypeList).hasSize(databaseSizeBeforeUpdate);
        DCktype testDCktype = dCktypeList.get(dCktypeList.size() - 1);
        assertThat(testDCktype.getCktype()).isEqualTo(UPDATED_CKTYPE);
        assertThat(testDCktype.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testDCktype.getSign()).isEqualTo(UPDATED_SIGN);

        // Validate the DCktype in Elasticsearch
        verify(mockDCktypeSearchRepository).save(testDCktype);
    }

    @Test
    @Transactional
    void putNonExistingDCktype() throws Exception {
        int databaseSizeBeforeUpdate = dCktypeRepository.findAll().size();
        dCktype.setId(count.incrementAndGet());

        // Create the DCktype
        DCktypeDTO dCktypeDTO = dCktypeMapper.toDto(dCktype);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDCktypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dCktypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dCktypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCktype in the database
        List<DCktype> dCktypeList = dCktypeRepository.findAll();
        assertThat(dCktypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCktype in Elasticsearch
        verify(mockDCktypeSearchRepository, times(0)).save(dCktype);
    }

    @Test
    @Transactional
    void putWithIdMismatchDCktype() throws Exception {
        int databaseSizeBeforeUpdate = dCktypeRepository.findAll().size();
        dCktype.setId(count.incrementAndGet());

        // Create the DCktype
        DCktypeDTO dCktypeDTO = dCktypeMapper.toDto(dCktype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCktypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dCktypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCktype in the database
        List<DCktype> dCktypeList = dCktypeRepository.findAll();
        assertThat(dCktypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCktype in Elasticsearch
        verify(mockDCktypeSearchRepository, times(0)).save(dCktype);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDCktype() throws Exception {
        int databaseSizeBeforeUpdate = dCktypeRepository.findAll().size();
        dCktype.setId(count.incrementAndGet());

        // Create the DCktype
        DCktypeDTO dCktypeDTO = dCktypeMapper.toDto(dCktype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCktypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCktypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DCktype in the database
        List<DCktype> dCktypeList = dCktypeRepository.findAll();
        assertThat(dCktypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCktype in Elasticsearch
        verify(mockDCktypeSearchRepository, times(0)).save(dCktype);
    }

    @Test
    @Transactional
    void partialUpdateDCktypeWithPatch() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        int databaseSizeBeforeUpdate = dCktypeRepository.findAll().size();

        // Update the dCktype using partial update
        DCktype partialUpdatedDCktype = new DCktype();
        partialUpdatedDCktype.setId(dCktype.getId());

        partialUpdatedDCktype.sign(UPDATED_SIGN);

        restDCktypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDCktype.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDCktype))
            )
            .andExpect(status().isOk());

        // Validate the DCktype in the database
        List<DCktype> dCktypeList = dCktypeRepository.findAll();
        assertThat(dCktypeList).hasSize(databaseSizeBeforeUpdate);
        DCktype testDCktype = dCktypeList.get(dCktypeList.size() - 1);
        assertThat(testDCktype.getCktype()).isEqualTo(DEFAULT_CKTYPE);
        assertThat(testDCktype.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testDCktype.getSign()).isEqualTo(UPDATED_SIGN);
    }

    @Test
    @Transactional
    void fullUpdateDCktypeWithPatch() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        int databaseSizeBeforeUpdate = dCktypeRepository.findAll().size();

        // Update the dCktype using partial update
        DCktype partialUpdatedDCktype = new DCktype();
        partialUpdatedDCktype.setId(dCktype.getId());

        partialUpdatedDCktype.cktype(UPDATED_CKTYPE).memo(UPDATED_MEMO).sign(UPDATED_SIGN);

        restDCktypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDCktype.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDCktype))
            )
            .andExpect(status().isOk());

        // Validate the DCktype in the database
        List<DCktype> dCktypeList = dCktypeRepository.findAll();
        assertThat(dCktypeList).hasSize(databaseSizeBeforeUpdate);
        DCktype testDCktype = dCktypeList.get(dCktypeList.size() - 1);
        assertThat(testDCktype.getCktype()).isEqualTo(UPDATED_CKTYPE);
        assertThat(testDCktype.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testDCktype.getSign()).isEqualTo(UPDATED_SIGN);
    }

    @Test
    @Transactional
    void patchNonExistingDCktype() throws Exception {
        int databaseSizeBeforeUpdate = dCktypeRepository.findAll().size();
        dCktype.setId(count.incrementAndGet());

        // Create the DCktype
        DCktypeDTO dCktypeDTO = dCktypeMapper.toDto(dCktype);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDCktypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dCktypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dCktypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCktype in the database
        List<DCktype> dCktypeList = dCktypeRepository.findAll();
        assertThat(dCktypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCktype in Elasticsearch
        verify(mockDCktypeSearchRepository, times(0)).save(dCktype);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDCktype() throws Exception {
        int databaseSizeBeforeUpdate = dCktypeRepository.findAll().size();
        dCktype.setId(count.incrementAndGet());

        // Create the DCktype
        DCktypeDTO dCktypeDTO = dCktypeMapper.toDto(dCktype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCktypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dCktypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCktype in the database
        List<DCktype> dCktypeList = dCktypeRepository.findAll();
        assertThat(dCktypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCktype in Elasticsearch
        verify(mockDCktypeSearchRepository, times(0)).save(dCktype);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDCktype() throws Exception {
        int databaseSizeBeforeUpdate = dCktypeRepository.findAll().size();
        dCktype.setId(count.incrementAndGet());

        // Create the DCktype
        DCktypeDTO dCktypeDTO = dCktypeMapper.toDto(dCktype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCktypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dCktypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DCktype in the database
        List<DCktype> dCktypeList = dCktypeRepository.findAll();
        assertThat(dCktypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCktype in Elasticsearch
        verify(mockDCktypeSearchRepository, times(0)).save(dCktype);
    }

    @Test
    @Transactional
    void deleteDCktype() throws Exception {
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);

        int databaseSizeBeforeDelete = dCktypeRepository.findAll().size();

        // Delete the dCktype
        restDCktypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, dCktype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DCktype> dCktypeList = dCktypeRepository.findAll();
        assertThat(dCktypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DCktype in Elasticsearch
        verify(mockDCktypeSearchRepository, times(1)).deleteById(dCktype.getId());
    }

    @Test
    @Transactional
    void searchDCktype() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dCktypeRepository.saveAndFlush(dCktype);
        when(mockDCktypeSearchRepository.search(queryStringQuery("id:" + dCktype.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dCktype), PageRequest.of(0, 1), 1));

        // Search the dCktype
        restDCktypeMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dCktype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dCktype.getId().intValue())))
            .andExpect(jsonPath("$.[*].cktype").value(hasItem(DEFAULT_CKTYPE)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN)));
    }
}
