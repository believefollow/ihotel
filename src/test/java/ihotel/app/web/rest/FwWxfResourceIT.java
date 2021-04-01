package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.FwWxf;
import ihotel.app.repository.FwWxfRepository;
import ihotel.app.repository.search.FwWxfSearchRepository;
import ihotel.app.service.criteria.FwWxfCriteria;
import ihotel.app.service.dto.FwWxfDTO;
import ihotel.app.service.mapper.FwWxfMapper;
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
 * Integration tests for the {@link FwWxfResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FwWxfResourceIT {

    private static final String DEFAULT_ROOMN = "AAAAAAAAAA";
    private static final String UPDATED_ROOMN = "BBBBBBBBBB";

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final Instant DEFAULT_DJRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DJRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_WXR = "AAAAAAAAAA";
    private static final String UPDATED_WXR = "BBBBBBBBBB";

    private static final Instant DEFAULT_WCRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WCRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_DJR = "AAAAAAAAAA";
    private static final String UPDATED_DJR = "BBBBBBBBBB";

    private static final String DEFAULT_FLAG = "AAAAAAAAAA";
    private static final String UPDATED_FLAG = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fw-wxfs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/fw-wxfs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FwWxfRepository fwWxfRepository;

    @Autowired
    private FwWxfMapper fwWxfMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.FwWxfSearchRepositoryMockConfiguration
     */
    @Autowired
    private FwWxfSearchRepository mockFwWxfSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFwWxfMockMvc;

    private FwWxf fwWxf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FwWxf createEntity(EntityManager em) {
        FwWxf fwWxf = new FwWxf()
            .roomn(DEFAULT_ROOMN)
            .memo(DEFAULT_MEMO)
            .djrq(DEFAULT_DJRQ)
            .wxr(DEFAULT_WXR)
            .wcrq(DEFAULT_WCRQ)
            .djr(DEFAULT_DJR)
            .flag(DEFAULT_FLAG);
        return fwWxf;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FwWxf createUpdatedEntity(EntityManager em) {
        FwWxf fwWxf = new FwWxf()
            .roomn(UPDATED_ROOMN)
            .memo(UPDATED_MEMO)
            .djrq(UPDATED_DJRQ)
            .wxr(UPDATED_WXR)
            .wcrq(UPDATED_WCRQ)
            .djr(UPDATED_DJR)
            .flag(UPDATED_FLAG);
        return fwWxf;
    }

    @BeforeEach
    public void initTest() {
        fwWxf = createEntity(em);
    }

    @Test
    @Transactional
    void createFwWxf() throws Exception {
        int databaseSizeBeforeCreate = fwWxfRepository.findAll().size();
        // Create the FwWxf
        FwWxfDTO fwWxfDTO = fwWxfMapper.toDto(fwWxf);
        restFwWxfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwWxfDTO)))
            .andExpect(status().isCreated());

        // Validate the FwWxf in the database
        List<FwWxf> fwWxfList = fwWxfRepository.findAll();
        assertThat(fwWxfList).hasSize(databaseSizeBeforeCreate + 1);
        FwWxf testFwWxf = fwWxfList.get(fwWxfList.size() - 1);
        assertThat(testFwWxf.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testFwWxf.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testFwWxf.getDjrq()).isEqualTo(DEFAULT_DJRQ);
        assertThat(testFwWxf.getWxr()).isEqualTo(DEFAULT_WXR);
        assertThat(testFwWxf.getWcrq()).isEqualTo(DEFAULT_WCRQ);
        assertThat(testFwWxf.getDjr()).isEqualTo(DEFAULT_DJR);
        assertThat(testFwWxf.getFlag()).isEqualTo(DEFAULT_FLAG);

        // Validate the FwWxf in Elasticsearch
        verify(mockFwWxfSearchRepository, times(1)).save(testFwWxf);
    }

    @Test
    @Transactional
    void createFwWxfWithExistingId() throws Exception {
        // Create the FwWxf with an existing ID
        fwWxf.setId(1L);
        FwWxfDTO fwWxfDTO = fwWxfMapper.toDto(fwWxf);

        int databaseSizeBeforeCreate = fwWxfRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFwWxfMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwWxfDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FwWxf in the database
        List<FwWxf> fwWxfList = fwWxfRepository.findAll();
        assertThat(fwWxfList).hasSize(databaseSizeBeforeCreate);

        // Validate the FwWxf in Elasticsearch
        verify(mockFwWxfSearchRepository, times(0)).save(fwWxf);
    }

    @Test
    @Transactional
    void getAllFwWxfs() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList
        restFwWxfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwWxf.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].djrq").value(hasItem(DEFAULT_DJRQ.toString())))
            .andExpect(jsonPath("$.[*].wxr").value(hasItem(DEFAULT_WXR)))
            .andExpect(jsonPath("$.[*].wcrq").value(hasItem(DEFAULT_WCRQ.toString())))
            .andExpect(jsonPath("$.[*].djr").value(hasItem(DEFAULT_DJR)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG)));
    }

    @Test
    @Transactional
    void getFwWxf() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get the fwWxf
        restFwWxfMockMvc
            .perform(get(ENTITY_API_URL_ID, fwWxf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fwWxf.getId().intValue()))
            .andExpect(jsonPath("$.roomn").value(DEFAULT_ROOMN))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO))
            .andExpect(jsonPath("$.djrq").value(DEFAULT_DJRQ.toString()))
            .andExpect(jsonPath("$.wxr").value(DEFAULT_WXR))
            .andExpect(jsonPath("$.wcrq").value(DEFAULT_WCRQ.toString()))
            .andExpect(jsonPath("$.djr").value(DEFAULT_DJR))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG));
    }

    @Test
    @Transactional
    void getFwWxfsByIdFiltering() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        Long id = fwWxf.getId();

        defaultFwWxfShouldBeFound("id.equals=" + id);
        defaultFwWxfShouldNotBeFound("id.notEquals=" + id);

        defaultFwWxfShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFwWxfShouldNotBeFound("id.greaterThan=" + id);

        defaultFwWxfShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFwWxfShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFwWxfsByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where roomn equals to DEFAULT_ROOMN
        defaultFwWxfShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the fwWxfList where roomn equals to UPDATED_ROOMN
        defaultFwWxfShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwWxfsByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where roomn not equals to DEFAULT_ROOMN
        defaultFwWxfShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the fwWxfList where roomn not equals to UPDATED_ROOMN
        defaultFwWxfShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwWxfsByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultFwWxfShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the fwWxfList where roomn equals to UPDATED_ROOMN
        defaultFwWxfShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwWxfsByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where roomn is not null
        defaultFwWxfShouldBeFound("roomn.specified=true");

        // Get all the fwWxfList where roomn is null
        defaultFwWxfShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllFwWxfsByRoomnContainsSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where roomn contains DEFAULT_ROOMN
        defaultFwWxfShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the fwWxfList where roomn contains UPDATED_ROOMN
        defaultFwWxfShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwWxfsByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where roomn does not contain DEFAULT_ROOMN
        defaultFwWxfShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the fwWxfList where roomn does not contain UPDATED_ROOMN
        defaultFwWxfShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwWxfsByMemoIsEqualToSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where memo equals to DEFAULT_MEMO
        defaultFwWxfShouldBeFound("memo.equals=" + DEFAULT_MEMO);

        // Get all the fwWxfList where memo equals to UPDATED_MEMO
        defaultFwWxfShouldNotBeFound("memo.equals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwWxfsByMemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where memo not equals to DEFAULT_MEMO
        defaultFwWxfShouldNotBeFound("memo.notEquals=" + DEFAULT_MEMO);

        // Get all the fwWxfList where memo not equals to UPDATED_MEMO
        defaultFwWxfShouldBeFound("memo.notEquals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwWxfsByMemoIsInShouldWork() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where memo in DEFAULT_MEMO or UPDATED_MEMO
        defaultFwWxfShouldBeFound("memo.in=" + DEFAULT_MEMO + "," + UPDATED_MEMO);

        // Get all the fwWxfList where memo equals to UPDATED_MEMO
        defaultFwWxfShouldNotBeFound("memo.in=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwWxfsByMemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where memo is not null
        defaultFwWxfShouldBeFound("memo.specified=true");

        // Get all the fwWxfList where memo is null
        defaultFwWxfShouldNotBeFound("memo.specified=false");
    }

    @Test
    @Transactional
    void getAllFwWxfsByMemoContainsSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where memo contains DEFAULT_MEMO
        defaultFwWxfShouldBeFound("memo.contains=" + DEFAULT_MEMO);

        // Get all the fwWxfList where memo contains UPDATED_MEMO
        defaultFwWxfShouldNotBeFound("memo.contains=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwWxfsByMemoNotContainsSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where memo does not contain DEFAULT_MEMO
        defaultFwWxfShouldNotBeFound("memo.doesNotContain=" + DEFAULT_MEMO);

        // Get all the fwWxfList where memo does not contain UPDATED_MEMO
        defaultFwWxfShouldBeFound("memo.doesNotContain=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwWxfsByDjrqIsEqualToSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where djrq equals to DEFAULT_DJRQ
        defaultFwWxfShouldBeFound("djrq.equals=" + DEFAULT_DJRQ);

        // Get all the fwWxfList where djrq equals to UPDATED_DJRQ
        defaultFwWxfShouldNotBeFound("djrq.equals=" + UPDATED_DJRQ);
    }

    @Test
    @Transactional
    void getAllFwWxfsByDjrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where djrq not equals to DEFAULT_DJRQ
        defaultFwWxfShouldNotBeFound("djrq.notEquals=" + DEFAULT_DJRQ);

        // Get all the fwWxfList where djrq not equals to UPDATED_DJRQ
        defaultFwWxfShouldBeFound("djrq.notEquals=" + UPDATED_DJRQ);
    }

    @Test
    @Transactional
    void getAllFwWxfsByDjrqIsInShouldWork() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where djrq in DEFAULT_DJRQ or UPDATED_DJRQ
        defaultFwWxfShouldBeFound("djrq.in=" + DEFAULT_DJRQ + "," + UPDATED_DJRQ);

        // Get all the fwWxfList where djrq equals to UPDATED_DJRQ
        defaultFwWxfShouldNotBeFound("djrq.in=" + UPDATED_DJRQ);
    }

    @Test
    @Transactional
    void getAllFwWxfsByDjrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where djrq is not null
        defaultFwWxfShouldBeFound("djrq.specified=true");

        // Get all the fwWxfList where djrq is null
        defaultFwWxfShouldNotBeFound("djrq.specified=false");
    }

    @Test
    @Transactional
    void getAllFwWxfsByWxrIsEqualToSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where wxr equals to DEFAULT_WXR
        defaultFwWxfShouldBeFound("wxr.equals=" + DEFAULT_WXR);

        // Get all the fwWxfList where wxr equals to UPDATED_WXR
        defaultFwWxfShouldNotBeFound("wxr.equals=" + UPDATED_WXR);
    }

    @Test
    @Transactional
    void getAllFwWxfsByWxrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where wxr not equals to DEFAULT_WXR
        defaultFwWxfShouldNotBeFound("wxr.notEquals=" + DEFAULT_WXR);

        // Get all the fwWxfList where wxr not equals to UPDATED_WXR
        defaultFwWxfShouldBeFound("wxr.notEquals=" + UPDATED_WXR);
    }

    @Test
    @Transactional
    void getAllFwWxfsByWxrIsInShouldWork() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where wxr in DEFAULT_WXR or UPDATED_WXR
        defaultFwWxfShouldBeFound("wxr.in=" + DEFAULT_WXR + "," + UPDATED_WXR);

        // Get all the fwWxfList where wxr equals to UPDATED_WXR
        defaultFwWxfShouldNotBeFound("wxr.in=" + UPDATED_WXR);
    }

    @Test
    @Transactional
    void getAllFwWxfsByWxrIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where wxr is not null
        defaultFwWxfShouldBeFound("wxr.specified=true");

        // Get all the fwWxfList where wxr is null
        defaultFwWxfShouldNotBeFound("wxr.specified=false");
    }

    @Test
    @Transactional
    void getAllFwWxfsByWxrContainsSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where wxr contains DEFAULT_WXR
        defaultFwWxfShouldBeFound("wxr.contains=" + DEFAULT_WXR);

        // Get all the fwWxfList where wxr contains UPDATED_WXR
        defaultFwWxfShouldNotBeFound("wxr.contains=" + UPDATED_WXR);
    }

    @Test
    @Transactional
    void getAllFwWxfsByWxrNotContainsSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where wxr does not contain DEFAULT_WXR
        defaultFwWxfShouldNotBeFound("wxr.doesNotContain=" + DEFAULT_WXR);

        // Get all the fwWxfList where wxr does not contain UPDATED_WXR
        defaultFwWxfShouldBeFound("wxr.doesNotContain=" + UPDATED_WXR);
    }

    @Test
    @Transactional
    void getAllFwWxfsByWcrqIsEqualToSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where wcrq equals to DEFAULT_WCRQ
        defaultFwWxfShouldBeFound("wcrq.equals=" + DEFAULT_WCRQ);

        // Get all the fwWxfList where wcrq equals to UPDATED_WCRQ
        defaultFwWxfShouldNotBeFound("wcrq.equals=" + UPDATED_WCRQ);
    }

    @Test
    @Transactional
    void getAllFwWxfsByWcrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where wcrq not equals to DEFAULT_WCRQ
        defaultFwWxfShouldNotBeFound("wcrq.notEquals=" + DEFAULT_WCRQ);

        // Get all the fwWxfList where wcrq not equals to UPDATED_WCRQ
        defaultFwWxfShouldBeFound("wcrq.notEquals=" + UPDATED_WCRQ);
    }

    @Test
    @Transactional
    void getAllFwWxfsByWcrqIsInShouldWork() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where wcrq in DEFAULT_WCRQ or UPDATED_WCRQ
        defaultFwWxfShouldBeFound("wcrq.in=" + DEFAULT_WCRQ + "," + UPDATED_WCRQ);

        // Get all the fwWxfList where wcrq equals to UPDATED_WCRQ
        defaultFwWxfShouldNotBeFound("wcrq.in=" + UPDATED_WCRQ);
    }

    @Test
    @Transactional
    void getAllFwWxfsByWcrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where wcrq is not null
        defaultFwWxfShouldBeFound("wcrq.specified=true");

        // Get all the fwWxfList where wcrq is null
        defaultFwWxfShouldNotBeFound("wcrq.specified=false");
    }

    @Test
    @Transactional
    void getAllFwWxfsByDjrIsEqualToSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where djr equals to DEFAULT_DJR
        defaultFwWxfShouldBeFound("djr.equals=" + DEFAULT_DJR);

        // Get all the fwWxfList where djr equals to UPDATED_DJR
        defaultFwWxfShouldNotBeFound("djr.equals=" + UPDATED_DJR);
    }

    @Test
    @Transactional
    void getAllFwWxfsByDjrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where djr not equals to DEFAULT_DJR
        defaultFwWxfShouldNotBeFound("djr.notEquals=" + DEFAULT_DJR);

        // Get all the fwWxfList where djr not equals to UPDATED_DJR
        defaultFwWxfShouldBeFound("djr.notEquals=" + UPDATED_DJR);
    }

    @Test
    @Transactional
    void getAllFwWxfsByDjrIsInShouldWork() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where djr in DEFAULT_DJR or UPDATED_DJR
        defaultFwWxfShouldBeFound("djr.in=" + DEFAULT_DJR + "," + UPDATED_DJR);

        // Get all the fwWxfList where djr equals to UPDATED_DJR
        defaultFwWxfShouldNotBeFound("djr.in=" + UPDATED_DJR);
    }

    @Test
    @Transactional
    void getAllFwWxfsByDjrIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where djr is not null
        defaultFwWxfShouldBeFound("djr.specified=true");

        // Get all the fwWxfList where djr is null
        defaultFwWxfShouldNotBeFound("djr.specified=false");
    }

    @Test
    @Transactional
    void getAllFwWxfsByDjrContainsSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where djr contains DEFAULT_DJR
        defaultFwWxfShouldBeFound("djr.contains=" + DEFAULT_DJR);

        // Get all the fwWxfList where djr contains UPDATED_DJR
        defaultFwWxfShouldNotBeFound("djr.contains=" + UPDATED_DJR);
    }

    @Test
    @Transactional
    void getAllFwWxfsByDjrNotContainsSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where djr does not contain DEFAULT_DJR
        defaultFwWxfShouldNotBeFound("djr.doesNotContain=" + DEFAULT_DJR);

        // Get all the fwWxfList where djr does not contain UPDATED_DJR
        defaultFwWxfShouldBeFound("djr.doesNotContain=" + UPDATED_DJR);
    }

    @Test
    @Transactional
    void getAllFwWxfsByFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where flag equals to DEFAULT_FLAG
        defaultFwWxfShouldBeFound("flag.equals=" + DEFAULT_FLAG);

        // Get all the fwWxfList where flag equals to UPDATED_FLAG
        defaultFwWxfShouldNotBeFound("flag.equals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllFwWxfsByFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where flag not equals to DEFAULT_FLAG
        defaultFwWxfShouldNotBeFound("flag.notEquals=" + DEFAULT_FLAG);

        // Get all the fwWxfList where flag not equals to UPDATED_FLAG
        defaultFwWxfShouldBeFound("flag.notEquals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllFwWxfsByFlagIsInShouldWork() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where flag in DEFAULT_FLAG or UPDATED_FLAG
        defaultFwWxfShouldBeFound("flag.in=" + DEFAULT_FLAG + "," + UPDATED_FLAG);

        // Get all the fwWxfList where flag equals to UPDATED_FLAG
        defaultFwWxfShouldNotBeFound("flag.in=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllFwWxfsByFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where flag is not null
        defaultFwWxfShouldBeFound("flag.specified=true");

        // Get all the fwWxfList where flag is null
        defaultFwWxfShouldNotBeFound("flag.specified=false");
    }

    @Test
    @Transactional
    void getAllFwWxfsByFlagContainsSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where flag contains DEFAULT_FLAG
        defaultFwWxfShouldBeFound("flag.contains=" + DEFAULT_FLAG);

        // Get all the fwWxfList where flag contains UPDATED_FLAG
        defaultFwWxfShouldNotBeFound("flag.contains=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllFwWxfsByFlagNotContainsSomething() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        // Get all the fwWxfList where flag does not contain DEFAULT_FLAG
        defaultFwWxfShouldNotBeFound("flag.doesNotContain=" + DEFAULT_FLAG);

        // Get all the fwWxfList where flag does not contain UPDATED_FLAG
        defaultFwWxfShouldBeFound("flag.doesNotContain=" + UPDATED_FLAG);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFwWxfShouldBeFound(String filter) throws Exception {
        restFwWxfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwWxf.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].djrq").value(hasItem(DEFAULT_DJRQ.toString())))
            .andExpect(jsonPath("$.[*].wxr").value(hasItem(DEFAULT_WXR)))
            .andExpect(jsonPath("$.[*].wcrq").value(hasItem(DEFAULT_WCRQ.toString())))
            .andExpect(jsonPath("$.[*].djr").value(hasItem(DEFAULT_DJR)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG)));

        // Check, that the count call also returns 1
        restFwWxfMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFwWxfShouldNotBeFound(String filter) throws Exception {
        restFwWxfMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFwWxfMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFwWxf() throws Exception {
        // Get the fwWxf
        restFwWxfMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFwWxf() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        int databaseSizeBeforeUpdate = fwWxfRepository.findAll().size();

        // Update the fwWxf
        FwWxf updatedFwWxf = fwWxfRepository.findById(fwWxf.getId()).get();
        // Disconnect from session so that the updates on updatedFwWxf are not directly saved in db
        em.detach(updatedFwWxf);
        updatedFwWxf
            .roomn(UPDATED_ROOMN)
            .memo(UPDATED_MEMO)
            .djrq(UPDATED_DJRQ)
            .wxr(UPDATED_WXR)
            .wcrq(UPDATED_WCRQ)
            .djr(UPDATED_DJR)
            .flag(UPDATED_FLAG);
        FwWxfDTO fwWxfDTO = fwWxfMapper.toDto(updatedFwWxf);

        restFwWxfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fwWxfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwWxfDTO))
            )
            .andExpect(status().isOk());

        // Validate the FwWxf in the database
        List<FwWxf> fwWxfList = fwWxfRepository.findAll();
        assertThat(fwWxfList).hasSize(databaseSizeBeforeUpdate);
        FwWxf testFwWxf = fwWxfList.get(fwWxfList.size() - 1);
        assertThat(testFwWxf.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testFwWxf.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testFwWxf.getDjrq()).isEqualTo(UPDATED_DJRQ);
        assertThat(testFwWxf.getWxr()).isEqualTo(UPDATED_WXR);
        assertThat(testFwWxf.getWcrq()).isEqualTo(UPDATED_WCRQ);
        assertThat(testFwWxf.getDjr()).isEqualTo(UPDATED_DJR);
        assertThat(testFwWxf.getFlag()).isEqualTo(UPDATED_FLAG);

        // Validate the FwWxf in Elasticsearch
        verify(mockFwWxfSearchRepository).save(testFwWxf);
    }

    @Test
    @Transactional
    void putNonExistingFwWxf() throws Exception {
        int databaseSizeBeforeUpdate = fwWxfRepository.findAll().size();
        fwWxf.setId(count.incrementAndGet());

        // Create the FwWxf
        FwWxfDTO fwWxfDTO = fwWxfMapper.toDto(fwWxf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFwWxfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fwWxfDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwWxfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwWxf in the database
        List<FwWxf> fwWxfList = fwWxfRepository.findAll();
        assertThat(fwWxfList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwWxf in Elasticsearch
        verify(mockFwWxfSearchRepository, times(0)).save(fwWxf);
    }

    @Test
    @Transactional
    void putWithIdMismatchFwWxf() throws Exception {
        int databaseSizeBeforeUpdate = fwWxfRepository.findAll().size();
        fwWxf.setId(count.incrementAndGet());

        // Create the FwWxf
        FwWxfDTO fwWxfDTO = fwWxfMapper.toDto(fwWxf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwWxfMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwWxfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwWxf in the database
        List<FwWxf> fwWxfList = fwWxfRepository.findAll();
        assertThat(fwWxfList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwWxf in Elasticsearch
        verify(mockFwWxfSearchRepository, times(0)).save(fwWxf);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFwWxf() throws Exception {
        int databaseSizeBeforeUpdate = fwWxfRepository.findAll().size();
        fwWxf.setId(count.incrementAndGet());

        // Create the FwWxf
        FwWxfDTO fwWxfDTO = fwWxfMapper.toDto(fwWxf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwWxfMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwWxfDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FwWxf in the database
        List<FwWxf> fwWxfList = fwWxfRepository.findAll();
        assertThat(fwWxfList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwWxf in Elasticsearch
        verify(mockFwWxfSearchRepository, times(0)).save(fwWxf);
    }

    @Test
    @Transactional
    void partialUpdateFwWxfWithPatch() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        int databaseSizeBeforeUpdate = fwWxfRepository.findAll().size();

        // Update the fwWxf using partial update
        FwWxf partialUpdatedFwWxf = new FwWxf();
        partialUpdatedFwWxf.setId(fwWxf.getId());

        partialUpdatedFwWxf.roomn(UPDATED_ROOMN).wxr(UPDATED_WXR).wcrq(UPDATED_WCRQ).djr(UPDATED_DJR).flag(UPDATED_FLAG);

        restFwWxfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFwWxf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFwWxf))
            )
            .andExpect(status().isOk());

        // Validate the FwWxf in the database
        List<FwWxf> fwWxfList = fwWxfRepository.findAll();
        assertThat(fwWxfList).hasSize(databaseSizeBeforeUpdate);
        FwWxf testFwWxf = fwWxfList.get(fwWxfList.size() - 1);
        assertThat(testFwWxf.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testFwWxf.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testFwWxf.getDjrq()).isEqualTo(DEFAULT_DJRQ);
        assertThat(testFwWxf.getWxr()).isEqualTo(UPDATED_WXR);
        assertThat(testFwWxf.getWcrq()).isEqualTo(UPDATED_WCRQ);
        assertThat(testFwWxf.getDjr()).isEqualTo(UPDATED_DJR);
        assertThat(testFwWxf.getFlag()).isEqualTo(UPDATED_FLAG);
    }

    @Test
    @Transactional
    void fullUpdateFwWxfWithPatch() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        int databaseSizeBeforeUpdate = fwWxfRepository.findAll().size();

        // Update the fwWxf using partial update
        FwWxf partialUpdatedFwWxf = new FwWxf();
        partialUpdatedFwWxf.setId(fwWxf.getId());

        partialUpdatedFwWxf
            .roomn(UPDATED_ROOMN)
            .memo(UPDATED_MEMO)
            .djrq(UPDATED_DJRQ)
            .wxr(UPDATED_WXR)
            .wcrq(UPDATED_WCRQ)
            .djr(UPDATED_DJR)
            .flag(UPDATED_FLAG);

        restFwWxfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFwWxf.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFwWxf))
            )
            .andExpect(status().isOk());

        // Validate the FwWxf in the database
        List<FwWxf> fwWxfList = fwWxfRepository.findAll();
        assertThat(fwWxfList).hasSize(databaseSizeBeforeUpdate);
        FwWxf testFwWxf = fwWxfList.get(fwWxfList.size() - 1);
        assertThat(testFwWxf.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testFwWxf.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testFwWxf.getDjrq()).isEqualTo(UPDATED_DJRQ);
        assertThat(testFwWxf.getWxr()).isEqualTo(UPDATED_WXR);
        assertThat(testFwWxf.getWcrq()).isEqualTo(UPDATED_WCRQ);
        assertThat(testFwWxf.getDjr()).isEqualTo(UPDATED_DJR);
        assertThat(testFwWxf.getFlag()).isEqualTo(UPDATED_FLAG);
    }

    @Test
    @Transactional
    void patchNonExistingFwWxf() throws Exception {
        int databaseSizeBeforeUpdate = fwWxfRepository.findAll().size();
        fwWxf.setId(count.incrementAndGet());

        // Create the FwWxf
        FwWxfDTO fwWxfDTO = fwWxfMapper.toDto(fwWxf);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFwWxfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fwWxfDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fwWxfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwWxf in the database
        List<FwWxf> fwWxfList = fwWxfRepository.findAll();
        assertThat(fwWxfList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwWxf in Elasticsearch
        verify(mockFwWxfSearchRepository, times(0)).save(fwWxf);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFwWxf() throws Exception {
        int databaseSizeBeforeUpdate = fwWxfRepository.findAll().size();
        fwWxf.setId(count.incrementAndGet());

        // Create the FwWxf
        FwWxfDTO fwWxfDTO = fwWxfMapper.toDto(fwWxf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwWxfMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fwWxfDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwWxf in the database
        List<FwWxf> fwWxfList = fwWxfRepository.findAll();
        assertThat(fwWxfList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwWxf in Elasticsearch
        verify(mockFwWxfSearchRepository, times(0)).save(fwWxf);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFwWxf() throws Exception {
        int databaseSizeBeforeUpdate = fwWxfRepository.findAll().size();
        fwWxf.setId(count.incrementAndGet());

        // Create the FwWxf
        FwWxfDTO fwWxfDTO = fwWxfMapper.toDto(fwWxf);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwWxfMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fwWxfDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FwWxf in the database
        List<FwWxf> fwWxfList = fwWxfRepository.findAll();
        assertThat(fwWxfList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwWxf in Elasticsearch
        verify(mockFwWxfSearchRepository, times(0)).save(fwWxf);
    }

    @Test
    @Transactional
    void deleteFwWxf() throws Exception {
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);

        int databaseSizeBeforeDelete = fwWxfRepository.findAll().size();

        // Delete the fwWxf
        restFwWxfMockMvc
            .perform(delete(ENTITY_API_URL_ID, fwWxf.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FwWxf> fwWxfList = fwWxfRepository.findAll();
        assertThat(fwWxfList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FwWxf in Elasticsearch
        verify(mockFwWxfSearchRepository, times(1)).deleteById(fwWxf.getId());
    }

    @Test
    @Transactional
    void searchFwWxf() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        fwWxfRepository.saveAndFlush(fwWxf);
        when(mockFwWxfSearchRepository.search(queryStringQuery("id:" + fwWxf.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fwWxf), PageRequest.of(0, 1), 1));

        // Search the fwWxf
        restFwWxfMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + fwWxf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwWxf.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].djrq").value(hasItem(DEFAULT_DJRQ.toString())))
            .andExpect(jsonPath("$.[*].wxr").value(hasItem(DEFAULT_WXR)))
            .andExpect(jsonPath("$.[*].wcrq").value(hasItem(DEFAULT_WCRQ.toString())))
            .andExpect(jsonPath("$.[*].djr").value(hasItem(DEFAULT_DJR)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG)));
    }
}
