package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Accbillno;
import ihotel.app.repository.AccbillnoRepository;
import ihotel.app.repository.search.AccbillnoSearchRepository;
import ihotel.app.service.criteria.AccbillnoCriteria;
import ihotel.app.service.dto.AccbillnoDTO;
import ihotel.app.service.mapper.AccbillnoMapper;
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
 * Integration tests for the {@link AccbillnoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AccbillnoResourceIT {

    private static final String DEFAULT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_ACCBILLNO = "AAAAAAAAAA";
    private static final String UPDATED_ACCBILLNO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/accbillnos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/accbillnos";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccbillnoRepository accbillnoRepository;

    @Autowired
    private AccbillnoMapper accbillnoMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.AccbillnoSearchRepositoryMockConfiguration
     */
    @Autowired
    private AccbillnoSearchRepository mockAccbillnoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccbillnoMockMvc;

    private Accbillno accbillno;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accbillno createEntity(EntityManager em) {
        Accbillno accbillno = new Accbillno().account(DEFAULT_ACCOUNT).accbillno(DEFAULT_ACCBILLNO);
        return accbillno;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accbillno createUpdatedEntity(EntityManager em) {
        Accbillno accbillno = new Accbillno().account(UPDATED_ACCOUNT).accbillno(UPDATED_ACCBILLNO);
        return accbillno;
    }

    @BeforeEach
    public void initTest() {
        accbillno = createEntity(em);
    }

    @Test
    @Transactional
    void createAccbillno() throws Exception {
        int databaseSizeBeforeCreate = accbillnoRepository.findAll().size();
        // Create the Accbillno
        AccbillnoDTO accbillnoDTO = accbillnoMapper.toDto(accbillno);
        restAccbillnoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accbillnoDTO)))
            .andExpect(status().isCreated());

        // Validate the Accbillno in the database
        List<Accbillno> accbillnoList = accbillnoRepository.findAll();
        assertThat(accbillnoList).hasSize(databaseSizeBeforeCreate + 1);
        Accbillno testAccbillno = accbillnoList.get(accbillnoList.size() - 1);
        assertThat(testAccbillno.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testAccbillno.getAccbillno()).isEqualTo(DEFAULT_ACCBILLNO);

        // Validate the Accbillno in Elasticsearch
        verify(mockAccbillnoSearchRepository, times(1)).save(testAccbillno);
    }

    @Test
    @Transactional
    void createAccbillnoWithExistingId() throws Exception {
        // Create the Accbillno with an existing ID
        accbillno.setId(1L);
        AccbillnoDTO accbillnoDTO = accbillnoMapper.toDto(accbillno);

        int databaseSizeBeforeCreate = accbillnoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccbillnoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accbillnoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Accbillno in the database
        List<Accbillno> accbillnoList = accbillnoRepository.findAll();
        assertThat(accbillnoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Accbillno in Elasticsearch
        verify(mockAccbillnoSearchRepository, times(0)).save(accbillno);
    }

    @Test
    @Transactional
    void checkAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = accbillnoRepository.findAll().size();
        // set the field null
        accbillno.setAccount(null);

        // Create the Accbillno, which fails.
        AccbillnoDTO accbillnoDTO = accbillnoMapper.toDto(accbillno);

        restAccbillnoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accbillnoDTO)))
            .andExpect(status().isBadRequest());

        List<Accbillno> accbillnoList = accbillnoRepository.findAll();
        assertThat(accbillnoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAccbillnos() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        // Get all the accbillnoList
        restAccbillnoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accbillno.getId().intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].accbillno").value(hasItem(DEFAULT_ACCBILLNO)));
    }

    @Test
    @Transactional
    void getAccbillno() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        // Get the accbillno
        restAccbillnoMockMvc
            .perform(get(ENTITY_API_URL_ID, accbillno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accbillno.getId().intValue()))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT))
            .andExpect(jsonPath("$.accbillno").value(DEFAULT_ACCBILLNO));
    }

    @Test
    @Transactional
    void getAccbillnosByIdFiltering() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        Long id = accbillno.getId();

        defaultAccbillnoShouldBeFound("id.equals=" + id);
        defaultAccbillnoShouldNotBeFound("id.notEquals=" + id);

        defaultAccbillnoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAccbillnoShouldNotBeFound("id.greaterThan=" + id);

        defaultAccbillnoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAccbillnoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAccbillnosByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        // Get all the accbillnoList where account equals to DEFAULT_ACCOUNT
        defaultAccbillnoShouldBeFound("account.equals=" + DEFAULT_ACCOUNT);

        // Get all the accbillnoList where account equals to UPDATED_ACCOUNT
        defaultAccbillnoShouldNotBeFound("account.equals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllAccbillnosByAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        // Get all the accbillnoList where account not equals to DEFAULT_ACCOUNT
        defaultAccbillnoShouldNotBeFound("account.notEquals=" + DEFAULT_ACCOUNT);

        // Get all the accbillnoList where account not equals to UPDATED_ACCOUNT
        defaultAccbillnoShouldBeFound("account.notEquals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllAccbillnosByAccountIsInShouldWork() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        // Get all the accbillnoList where account in DEFAULT_ACCOUNT or UPDATED_ACCOUNT
        defaultAccbillnoShouldBeFound("account.in=" + DEFAULT_ACCOUNT + "," + UPDATED_ACCOUNT);

        // Get all the accbillnoList where account equals to UPDATED_ACCOUNT
        defaultAccbillnoShouldNotBeFound("account.in=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllAccbillnosByAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        // Get all the accbillnoList where account is not null
        defaultAccbillnoShouldBeFound("account.specified=true");

        // Get all the accbillnoList where account is null
        defaultAccbillnoShouldNotBeFound("account.specified=false");
    }

    @Test
    @Transactional
    void getAllAccbillnosByAccountContainsSomething() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        // Get all the accbillnoList where account contains DEFAULT_ACCOUNT
        defaultAccbillnoShouldBeFound("account.contains=" + DEFAULT_ACCOUNT);

        // Get all the accbillnoList where account contains UPDATED_ACCOUNT
        defaultAccbillnoShouldNotBeFound("account.contains=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllAccbillnosByAccountNotContainsSomething() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        // Get all the accbillnoList where account does not contain DEFAULT_ACCOUNT
        defaultAccbillnoShouldNotBeFound("account.doesNotContain=" + DEFAULT_ACCOUNT);

        // Get all the accbillnoList where account does not contain UPDATED_ACCOUNT
        defaultAccbillnoShouldBeFound("account.doesNotContain=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllAccbillnosByAccbillnoIsEqualToSomething() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        // Get all the accbillnoList where accbillno equals to DEFAULT_ACCBILLNO
        defaultAccbillnoShouldBeFound("accbillno.equals=" + DEFAULT_ACCBILLNO);

        // Get all the accbillnoList where accbillno equals to UPDATED_ACCBILLNO
        defaultAccbillnoShouldNotBeFound("accbillno.equals=" + UPDATED_ACCBILLNO);
    }

    @Test
    @Transactional
    void getAllAccbillnosByAccbillnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        // Get all the accbillnoList where accbillno not equals to DEFAULT_ACCBILLNO
        defaultAccbillnoShouldNotBeFound("accbillno.notEquals=" + DEFAULT_ACCBILLNO);

        // Get all the accbillnoList where accbillno not equals to UPDATED_ACCBILLNO
        defaultAccbillnoShouldBeFound("accbillno.notEquals=" + UPDATED_ACCBILLNO);
    }

    @Test
    @Transactional
    void getAllAccbillnosByAccbillnoIsInShouldWork() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        // Get all the accbillnoList where accbillno in DEFAULT_ACCBILLNO or UPDATED_ACCBILLNO
        defaultAccbillnoShouldBeFound("accbillno.in=" + DEFAULT_ACCBILLNO + "," + UPDATED_ACCBILLNO);

        // Get all the accbillnoList where accbillno equals to UPDATED_ACCBILLNO
        defaultAccbillnoShouldNotBeFound("accbillno.in=" + UPDATED_ACCBILLNO);
    }

    @Test
    @Transactional
    void getAllAccbillnosByAccbillnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        // Get all the accbillnoList where accbillno is not null
        defaultAccbillnoShouldBeFound("accbillno.specified=true");

        // Get all the accbillnoList where accbillno is null
        defaultAccbillnoShouldNotBeFound("accbillno.specified=false");
    }

    @Test
    @Transactional
    void getAllAccbillnosByAccbillnoContainsSomething() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        // Get all the accbillnoList where accbillno contains DEFAULT_ACCBILLNO
        defaultAccbillnoShouldBeFound("accbillno.contains=" + DEFAULT_ACCBILLNO);

        // Get all the accbillnoList where accbillno contains UPDATED_ACCBILLNO
        defaultAccbillnoShouldNotBeFound("accbillno.contains=" + UPDATED_ACCBILLNO);
    }

    @Test
    @Transactional
    void getAllAccbillnosByAccbillnoNotContainsSomething() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        // Get all the accbillnoList where accbillno does not contain DEFAULT_ACCBILLNO
        defaultAccbillnoShouldNotBeFound("accbillno.doesNotContain=" + DEFAULT_ACCBILLNO);

        // Get all the accbillnoList where accbillno does not contain UPDATED_ACCBILLNO
        defaultAccbillnoShouldBeFound("accbillno.doesNotContain=" + UPDATED_ACCBILLNO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccbillnoShouldBeFound(String filter) throws Exception {
        restAccbillnoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accbillno.getId().intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].accbillno").value(hasItem(DEFAULT_ACCBILLNO)));

        // Check, that the count call also returns 1
        restAccbillnoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccbillnoShouldNotBeFound(String filter) throws Exception {
        restAccbillnoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccbillnoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAccbillno() throws Exception {
        // Get the accbillno
        restAccbillnoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAccbillno() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        int databaseSizeBeforeUpdate = accbillnoRepository.findAll().size();

        // Update the accbillno
        Accbillno updatedAccbillno = accbillnoRepository.findById(accbillno.getId()).get();
        // Disconnect from session so that the updates on updatedAccbillno are not directly saved in db
        em.detach(updatedAccbillno);
        updatedAccbillno.account(UPDATED_ACCOUNT).accbillno(UPDATED_ACCBILLNO);
        AccbillnoDTO accbillnoDTO = accbillnoMapper.toDto(updatedAccbillno);

        restAccbillnoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accbillnoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accbillnoDTO))
            )
            .andExpect(status().isOk());

        // Validate the Accbillno in the database
        List<Accbillno> accbillnoList = accbillnoRepository.findAll();
        assertThat(accbillnoList).hasSize(databaseSizeBeforeUpdate);
        Accbillno testAccbillno = accbillnoList.get(accbillnoList.size() - 1);
        assertThat(testAccbillno.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testAccbillno.getAccbillno()).isEqualTo(UPDATED_ACCBILLNO);

        // Validate the Accbillno in Elasticsearch
        verify(mockAccbillnoSearchRepository).save(testAccbillno);
    }

    @Test
    @Transactional
    void putNonExistingAccbillno() throws Exception {
        int databaseSizeBeforeUpdate = accbillnoRepository.findAll().size();
        accbillno.setId(count.incrementAndGet());

        // Create the Accbillno
        AccbillnoDTO accbillnoDTO = accbillnoMapper.toDto(accbillno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccbillnoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accbillnoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accbillnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accbillno in the database
        List<Accbillno> accbillnoList = accbillnoRepository.findAll();
        assertThat(accbillnoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Accbillno in Elasticsearch
        verify(mockAccbillnoSearchRepository, times(0)).save(accbillno);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccbillno() throws Exception {
        int databaseSizeBeforeUpdate = accbillnoRepository.findAll().size();
        accbillno.setId(count.incrementAndGet());

        // Create the Accbillno
        AccbillnoDTO accbillnoDTO = accbillnoMapper.toDto(accbillno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccbillnoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accbillnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accbillno in the database
        List<Accbillno> accbillnoList = accbillnoRepository.findAll();
        assertThat(accbillnoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Accbillno in Elasticsearch
        verify(mockAccbillnoSearchRepository, times(0)).save(accbillno);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccbillno() throws Exception {
        int databaseSizeBeforeUpdate = accbillnoRepository.findAll().size();
        accbillno.setId(count.incrementAndGet());

        // Create the Accbillno
        AccbillnoDTO accbillnoDTO = accbillnoMapper.toDto(accbillno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccbillnoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accbillnoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accbillno in the database
        List<Accbillno> accbillnoList = accbillnoRepository.findAll();
        assertThat(accbillnoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Accbillno in Elasticsearch
        verify(mockAccbillnoSearchRepository, times(0)).save(accbillno);
    }

    @Test
    @Transactional
    void partialUpdateAccbillnoWithPatch() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        int databaseSizeBeforeUpdate = accbillnoRepository.findAll().size();

        // Update the accbillno using partial update
        Accbillno partialUpdatedAccbillno = new Accbillno();
        partialUpdatedAccbillno.setId(accbillno.getId());

        partialUpdatedAccbillno.accbillno(UPDATED_ACCBILLNO);

        restAccbillnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccbillno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccbillno))
            )
            .andExpect(status().isOk());

        // Validate the Accbillno in the database
        List<Accbillno> accbillnoList = accbillnoRepository.findAll();
        assertThat(accbillnoList).hasSize(databaseSizeBeforeUpdate);
        Accbillno testAccbillno = accbillnoList.get(accbillnoList.size() - 1);
        assertThat(testAccbillno.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testAccbillno.getAccbillno()).isEqualTo(UPDATED_ACCBILLNO);
    }

    @Test
    @Transactional
    void fullUpdateAccbillnoWithPatch() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        int databaseSizeBeforeUpdate = accbillnoRepository.findAll().size();

        // Update the accbillno using partial update
        Accbillno partialUpdatedAccbillno = new Accbillno();
        partialUpdatedAccbillno.setId(accbillno.getId());

        partialUpdatedAccbillno.account(UPDATED_ACCOUNT).accbillno(UPDATED_ACCBILLNO);

        restAccbillnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccbillno.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccbillno))
            )
            .andExpect(status().isOk());

        // Validate the Accbillno in the database
        List<Accbillno> accbillnoList = accbillnoRepository.findAll();
        assertThat(accbillnoList).hasSize(databaseSizeBeforeUpdate);
        Accbillno testAccbillno = accbillnoList.get(accbillnoList.size() - 1);
        assertThat(testAccbillno.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testAccbillno.getAccbillno()).isEqualTo(UPDATED_ACCBILLNO);
    }

    @Test
    @Transactional
    void patchNonExistingAccbillno() throws Exception {
        int databaseSizeBeforeUpdate = accbillnoRepository.findAll().size();
        accbillno.setId(count.incrementAndGet());

        // Create the Accbillno
        AccbillnoDTO accbillnoDTO = accbillnoMapper.toDto(accbillno);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccbillnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accbillnoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accbillnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accbillno in the database
        List<Accbillno> accbillnoList = accbillnoRepository.findAll();
        assertThat(accbillnoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Accbillno in Elasticsearch
        verify(mockAccbillnoSearchRepository, times(0)).save(accbillno);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccbillno() throws Exception {
        int databaseSizeBeforeUpdate = accbillnoRepository.findAll().size();
        accbillno.setId(count.incrementAndGet());

        // Create the Accbillno
        AccbillnoDTO accbillnoDTO = accbillnoMapper.toDto(accbillno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccbillnoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accbillnoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accbillno in the database
        List<Accbillno> accbillnoList = accbillnoRepository.findAll();
        assertThat(accbillnoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Accbillno in Elasticsearch
        verify(mockAccbillnoSearchRepository, times(0)).save(accbillno);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccbillno() throws Exception {
        int databaseSizeBeforeUpdate = accbillnoRepository.findAll().size();
        accbillno.setId(count.incrementAndGet());

        // Create the Accbillno
        AccbillnoDTO accbillnoDTO = accbillnoMapper.toDto(accbillno);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccbillnoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(accbillnoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accbillno in the database
        List<Accbillno> accbillnoList = accbillnoRepository.findAll();
        assertThat(accbillnoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Accbillno in Elasticsearch
        verify(mockAccbillnoSearchRepository, times(0)).save(accbillno);
    }

    @Test
    @Transactional
    void deleteAccbillno() throws Exception {
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);

        int databaseSizeBeforeDelete = accbillnoRepository.findAll().size();

        // Delete the accbillno
        restAccbillnoMockMvc
            .perform(delete(ENTITY_API_URL_ID, accbillno.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accbillno> accbillnoList = accbillnoRepository.findAll();
        assertThat(accbillnoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Accbillno in Elasticsearch
        verify(mockAccbillnoSearchRepository, times(1)).deleteById(accbillno.getId());
    }

    @Test
    @Transactional
    void searchAccbillno() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        accbillnoRepository.saveAndFlush(accbillno);
        when(mockAccbillnoSearchRepository.search(queryStringQuery("id:" + accbillno.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(accbillno), PageRequest.of(0, 1), 1));

        // Search the accbillno
        restAccbillnoMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + accbillno.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accbillno.getId().intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].accbillno").value(hasItem(DEFAULT_ACCBILLNO)));
    }
}
