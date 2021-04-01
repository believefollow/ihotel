package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.FwYlwp;
import ihotel.app.repository.FwYlwpRepository;
import ihotel.app.repository.search.FwYlwpSearchRepository;
import ihotel.app.service.criteria.FwYlwpCriteria;
import ihotel.app.service.dto.FwYlwpDTO;
import ihotel.app.service.mapper.FwYlwpMapper;
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
 * Integration tests for the {@link FwYlwpResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FwYlwpResourceIT {

    private static final String DEFAULT_ROOMN = "AAAAAAAAAA";
    private static final String UPDATED_ROOMN = "BBBBBBBBBB";

    private static final String DEFAULT_GUESTNAME = "AAAAAAAAAA";
    private static final String UPDATED_GUESTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final String DEFAULT_SDR = "AAAAAAAAAA";
    private static final String UPDATED_SDR = "BBBBBBBBBB";

    private static final Instant DEFAULT_SDRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SDRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RLR = "AAAAAAAAAA";
    private static final String UPDATED_RLR = "BBBBBBBBBB";

    private static final Instant DEFAULT_RLRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RLRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final Instant DEFAULT_CZRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CZRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_FLAG = "AA";
    private static final String UPDATED_FLAG = "BB";

    private static final String ENTITY_API_URL = "/api/fw-ylwps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/fw-ylwps";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FwYlwpRepository fwYlwpRepository;

    @Autowired
    private FwYlwpMapper fwYlwpMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.FwYlwpSearchRepositoryMockConfiguration
     */
    @Autowired
    private FwYlwpSearchRepository mockFwYlwpSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFwYlwpMockMvc;

    private FwYlwp fwYlwp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FwYlwp createEntity(EntityManager em) {
        FwYlwp fwYlwp = new FwYlwp()
            .roomn(DEFAULT_ROOMN)
            .guestname(DEFAULT_GUESTNAME)
            .memo(DEFAULT_MEMO)
            .sdr(DEFAULT_SDR)
            .sdrq(DEFAULT_SDRQ)
            .rlr(DEFAULT_RLR)
            .rlrq(DEFAULT_RLRQ)
            .remark(DEFAULT_REMARK)
            .empn(DEFAULT_EMPN)
            .czrq(DEFAULT_CZRQ)
            .flag(DEFAULT_FLAG);
        return fwYlwp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FwYlwp createUpdatedEntity(EntityManager em) {
        FwYlwp fwYlwp = new FwYlwp()
            .roomn(UPDATED_ROOMN)
            .guestname(UPDATED_GUESTNAME)
            .memo(UPDATED_MEMO)
            .sdr(UPDATED_SDR)
            .sdrq(UPDATED_SDRQ)
            .rlr(UPDATED_RLR)
            .rlrq(UPDATED_RLRQ)
            .remark(UPDATED_REMARK)
            .empn(UPDATED_EMPN)
            .czrq(UPDATED_CZRQ)
            .flag(UPDATED_FLAG);
        return fwYlwp;
    }

    @BeforeEach
    public void initTest() {
        fwYlwp = createEntity(em);
    }

    @Test
    @Transactional
    void createFwYlwp() throws Exception {
        int databaseSizeBeforeCreate = fwYlwpRepository.findAll().size();
        // Create the FwYlwp
        FwYlwpDTO fwYlwpDTO = fwYlwpMapper.toDto(fwYlwp);
        restFwYlwpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwYlwpDTO)))
            .andExpect(status().isCreated());

        // Validate the FwYlwp in the database
        List<FwYlwp> fwYlwpList = fwYlwpRepository.findAll();
        assertThat(fwYlwpList).hasSize(databaseSizeBeforeCreate + 1);
        FwYlwp testFwYlwp = fwYlwpList.get(fwYlwpList.size() - 1);
        assertThat(testFwYlwp.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testFwYlwp.getGuestname()).isEqualTo(DEFAULT_GUESTNAME);
        assertThat(testFwYlwp.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testFwYlwp.getSdr()).isEqualTo(DEFAULT_SDR);
        assertThat(testFwYlwp.getSdrq()).isEqualTo(DEFAULT_SDRQ);
        assertThat(testFwYlwp.getRlr()).isEqualTo(DEFAULT_RLR);
        assertThat(testFwYlwp.getRlrq()).isEqualTo(DEFAULT_RLRQ);
        assertThat(testFwYlwp.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testFwYlwp.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testFwYlwp.getCzrq()).isEqualTo(DEFAULT_CZRQ);
        assertThat(testFwYlwp.getFlag()).isEqualTo(DEFAULT_FLAG);

        // Validate the FwYlwp in Elasticsearch
        verify(mockFwYlwpSearchRepository, times(1)).save(testFwYlwp);
    }

    @Test
    @Transactional
    void createFwYlwpWithExistingId() throws Exception {
        // Create the FwYlwp with an existing ID
        fwYlwp.setId(1L);
        FwYlwpDTO fwYlwpDTO = fwYlwpMapper.toDto(fwYlwp);

        int databaseSizeBeforeCreate = fwYlwpRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFwYlwpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwYlwpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FwYlwp in the database
        List<FwYlwp> fwYlwpList = fwYlwpRepository.findAll();
        assertThat(fwYlwpList).hasSize(databaseSizeBeforeCreate);

        // Validate the FwYlwp in Elasticsearch
        verify(mockFwYlwpSearchRepository, times(0)).save(fwYlwp);
    }

    @Test
    @Transactional
    void getAllFwYlwps() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList
        restFwYlwpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwYlwp.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].sdr").value(hasItem(DEFAULT_SDR)))
            .andExpect(jsonPath("$.[*].sdrq").value(hasItem(DEFAULT_SDRQ.toString())))
            .andExpect(jsonPath("$.[*].rlr").value(hasItem(DEFAULT_RLR)))
            .andExpect(jsonPath("$.[*].rlrq").value(hasItem(DEFAULT_RLRQ.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].czrq").value(hasItem(DEFAULT_CZRQ.toString())))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG)));
    }

    @Test
    @Transactional
    void getFwYlwp() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get the fwYlwp
        restFwYlwpMockMvc
            .perform(get(ENTITY_API_URL_ID, fwYlwp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fwYlwp.getId().intValue()))
            .andExpect(jsonPath("$.roomn").value(DEFAULT_ROOMN))
            .andExpect(jsonPath("$.guestname").value(DEFAULT_GUESTNAME))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO))
            .andExpect(jsonPath("$.sdr").value(DEFAULT_SDR))
            .andExpect(jsonPath("$.sdrq").value(DEFAULT_SDRQ.toString()))
            .andExpect(jsonPath("$.rlr").value(DEFAULT_RLR))
            .andExpect(jsonPath("$.rlrq").value(DEFAULT_RLRQ.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.czrq").value(DEFAULT_CZRQ.toString()))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG));
    }

    @Test
    @Transactional
    void getFwYlwpsByIdFiltering() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        Long id = fwYlwp.getId();

        defaultFwYlwpShouldBeFound("id.equals=" + id);
        defaultFwYlwpShouldNotBeFound("id.notEquals=" + id);

        defaultFwYlwpShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFwYlwpShouldNotBeFound("id.greaterThan=" + id);

        defaultFwYlwpShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFwYlwpShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where roomn equals to DEFAULT_ROOMN
        defaultFwYlwpShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the fwYlwpList where roomn equals to UPDATED_ROOMN
        defaultFwYlwpShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where roomn not equals to DEFAULT_ROOMN
        defaultFwYlwpShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the fwYlwpList where roomn not equals to UPDATED_ROOMN
        defaultFwYlwpShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultFwYlwpShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the fwYlwpList where roomn equals to UPDATED_ROOMN
        defaultFwYlwpShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where roomn is not null
        defaultFwYlwpShouldBeFound("roomn.specified=true");

        // Get all the fwYlwpList where roomn is null
        defaultFwYlwpShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRoomnContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where roomn contains DEFAULT_ROOMN
        defaultFwYlwpShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the fwYlwpList where roomn contains UPDATED_ROOMN
        defaultFwYlwpShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where roomn does not contain DEFAULT_ROOMN
        defaultFwYlwpShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the fwYlwpList where roomn does not contain UPDATED_ROOMN
        defaultFwYlwpShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByGuestnameIsEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where guestname equals to DEFAULT_GUESTNAME
        defaultFwYlwpShouldBeFound("guestname.equals=" + DEFAULT_GUESTNAME);

        // Get all the fwYlwpList where guestname equals to UPDATED_GUESTNAME
        defaultFwYlwpShouldNotBeFound("guestname.equals=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByGuestnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where guestname not equals to DEFAULT_GUESTNAME
        defaultFwYlwpShouldNotBeFound("guestname.notEquals=" + DEFAULT_GUESTNAME);

        // Get all the fwYlwpList where guestname not equals to UPDATED_GUESTNAME
        defaultFwYlwpShouldBeFound("guestname.notEquals=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByGuestnameIsInShouldWork() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where guestname in DEFAULT_GUESTNAME or UPDATED_GUESTNAME
        defaultFwYlwpShouldBeFound("guestname.in=" + DEFAULT_GUESTNAME + "," + UPDATED_GUESTNAME);

        // Get all the fwYlwpList where guestname equals to UPDATED_GUESTNAME
        defaultFwYlwpShouldNotBeFound("guestname.in=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByGuestnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where guestname is not null
        defaultFwYlwpShouldBeFound("guestname.specified=true");

        // Get all the fwYlwpList where guestname is null
        defaultFwYlwpShouldNotBeFound("guestname.specified=false");
    }

    @Test
    @Transactional
    void getAllFwYlwpsByGuestnameContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where guestname contains DEFAULT_GUESTNAME
        defaultFwYlwpShouldBeFound("guestname.contains=" + DEFAULT_GUESTNAME);

        // Get all the fwYlwpList where guestname contains UPDATED_GUESTNAME
        defaultFwYlwpShouldNotBeFound("guestname.contains=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByGuestnameNotContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where guestname does not contain DEFAULT_GUESTNAME
        defaultFwYlwpShouldNotBeFound("guestname.doesNotContain=" + DEFAULT_GUESTNAME);

        // Get all the fwYlwpList where guestname does not contain UPDATED_GUESTNAME
        defaultFwYlwpShouldBeFound("guestname.doesNotContain=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByMemoIsEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where memo equals to DEFAULT_MEMO
        defaultFwYlwpShouldBeFound("memo.equals=" + DEFAULT_MEMO);

        // Get all the fwYlwpList where memo equals to UPDATED_MEMO
        defaultFwYlwpShouldNotBeFound("memo.equals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByMemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where memo not equals to DEFAULT_MEMO
        defaultFwYlwpShouldNotBeFound("memo.notEquals=" + DEFAULT_MEMO);

        // Get all the fwYlwpList where memo not equals to UPDATED_MEMO
        defaultFwYlwpShouldBeFound("memo.notEquals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByMemoIsInShouldWork() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where memo in DEFAULT_MEMO or UPDATED_MEMO
        defaultFwYlwpShouldBeFound("memo.in=" + DEFAULT_MEMO + "," + UPDATED_MEMO);

        // Get all the fwYlwpList where memo equals to UPDATED_MEMO
        defaultFwYlwpShouldNotBeFound("memo.in=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByMemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where memo is not null
        defaultFwYlwpShouldBeFound("memo.specified=true");

        // Get all the fwYlwpList where memo is null
        defaultFwYlwpShouldNotBeFound("memo.specified=false");
    }

    @Test
    @Transactional
    void getAllFwYlwpsByMemoContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where memo contains DEFAULT_MEMO
        defaultFwYlwpShouldBeFound("memo.contains=" + DEFAULT_MEMO);

        // Get all the fwYlwpList where memo contains UPDATED_MEMO
        defaultFwYlwpShouldNotBeFound("memo.contains=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByMemoNotContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where memo does not contain DEFAULT_MEMO
        defaultFwYlwpShouldNotBeFound("memo.doesNotContain=" + DEFAULT_MEMO);

        // Get all the fwYlwpList where memo does not contain UPDATED_MEMO
        defaultFwYlwpShouldBeFound("memo.doesNotContain=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwYlwpsBySdrIsEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where sdr equals to DEFAULT_SDR
        defaultFwYlwpShouldBeFound("sdr.equals=" + DEFAULT_SDR);

        // Get all the fwYlwpList where sdr equals to UPDATED_SDR
        defaultFwYlwpShouldNotBeFound("sdr.equals=" + UPDATED_SDR);
    }

    @Test
    @Transactional
    void getAllFwYlwpsBySdrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where sdr not equals to DEFAULT_SDR
        defaultFwYlwpShouldNotBeFound("sdr.notEquals=" + DEFAULT_SDR);

        // Get all the fwYlwpList where sdr not equals to UPDATED_SDR
        defaultFwYlwpShouldBeFound("sdr.notEquals=" + UPDATED_SDR);
    }

    @Test
    @Transactional
    void getAllFwYlwpsBySdrIsInShouldWork() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where sdr in DEFAULT_SDR or UPDATED_SDR
        defaultFwYlwpShouldBeFound("sdr.in=" + DEFAULT_SDR + "," + UPDATED_SDR);

        // Get all the fwYlwpList where sdr equals to UPDATED_SDR
        defaultFwYlwpShouldNotBeFound("sdr.in=" + UPDATED_SDR);
    }

    @Test
    @Transactional
    void getAllFwYlwpsBySdrIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where sdr is not null
        defaultFwYlwpShouldBeFound("sdr.specified=true");

        // Get all the fwYlwpList where sdr is null
        defaultFwYlwpShouldNotBeFound("sdr.specified=false");
    }

    @Test
    @Transactional
    void getAllFwYlwpsBySdrContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where sdr contains DEFAULT_SDR
        defaultFwYlwpShouldBeFound("sdr.contains=" + DEFAULT_SDR);

        // Get all the fwYlwpList where sdr contains UPDATED_SDR
        defaultFwYlwpShouldNotBeFound("sdr.contains=" + UPDATED_SDR);
    }

    @Test
    @Transactional
    void getAllFwYlwpsBySdrNotContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where sdr does not contain DEFAULT_SDR
        defaultFwYlwpShouldNotBeFound("sdr.doesNotContain=" + DEFAULT_SDR);

        // Get all the fwYlwpList where sdr does not contain UPDATED_SDR
        defaultFwYlwpShouldBeFound("sdr.doesNotContain=" + UPDATED_SDR);
    }

    @Test
    @Transactional
    void getAllFwYlwpsBySdrqIsEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where sdrq equals to DEFAULT_SDRQ
        defaultFwYlwpShouldBeFound("sdrq.equals=" + DEFAULT_SDRQ);

        // Get all the fwYlwpList where sdrq equals to UPDATED_SDRQ
        defaultFwYlwpShouldNotBeFound("sdrq.equals=" + UPDATED_SDRQ);
    }

    @Test
    @Transactional
    void getAllFwYlwpsBySdrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where sdrq not equals to DEFAULT_SDRQ
        defaultFwYlwpShouldNotBeFound("sdrq.notEquals=" + DEFAULT_SDRQ);

        // Get all the fwYlwpList where sdrq not equals to UPDATED_SDRQ
        defaultFwYlwpShouldBeFound("sdrq.notEquals=" + UPDATED_SDRQ);
    }

    @Test
    @Transactional
    void getAllFwYlwpsBySdrqIsInShouldWork() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where sdrq in DEFAULT_SDRQ or UPDATED_SDRQ
        defaultFwYlwpShouldBeFound("sdrq.in=" + DEFAULT_SDRQ + "," + UPDATED_SDRQ);

        // Get all the fwYlwpList where sdrq equals to UPDATED_SDRQ
        defaultFwYlwpShouldNotBeFound("sdrq.in=" + UPDATED_SDRQ);
    }

    @Test
    @Transactional
    void getAllFwYlwpsBySdrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where sdrq is not null
        defaultFwYlwpShouldBeFound("sdrq.specified=true");

        // Get all the fwYlwpList where sdrq is null
        defaultFwYlwpShouldNotBeFound("sdrq.specified=false");
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRlrIsEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where rlr equals to DEFAULT_RLR
        defaultFwYlwpShouldBeFound("rlr.equals=" + DEFAULT_RLR);

        // Get all the fwYlwpList where rlr equals to UPDATED_RLR
        defaultFwYlwpShouldNotBeFound("rlr.equals=" + UPDATED_RLR);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRlrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where rlr not equals to DEFAULT_RLR
        defaultFwYlwpShouldNotBeFound("rlr.notEquals=" + DEFAULT_RLR);

        // Get all the fwYlwpList where rlr not equals to UPDATED_RLR
        defaultFwYlwpShouldBeFound("rlr.notEquals=" + UPDATED_RLR);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRlrIsInShouldWork() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where rlr in DEFAULT_RLR or UPDATED_RLR
        defaultFwYlwpShouldBeFound("rlr.in=" + DEFAULT_RLR + "," + UPDATED_RLR);

        // Get all the fwYlwpList where rlr equals to UPDATED_RLR
        defaultFwYlwpShouldNotBeFound("rlr.in=" + UPDATED_RLR);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRlrIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where rlr is not null
        defaultFwYlwpShouldBeFound("rlr.specified=true");

        // Get all the fwYlwpList where rlr is null
        defaultFwYlwpShouldNotBeFound("rlr.specified=false");
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRlrContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where rlr contains DEFAULT_RLR
        defaultFwYlwpShouldBeFound("rlr.contains=" + DEFAULT_RLR);

        // Get all the fwYlwpList where rlr contains UPDATED_RLR
        defaultFwYlwpShouldNotBeFound("rlr.contains=" + UPDATED_RLR);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRlrNotContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where rlr does not contain DEFAULT_RLR
        defaultFwYlwpShouldNotBeFound("rlr.doesNotContain=" + DEFAULT_RLR);

        // Get all the fwYlwpList where rlr does not contain UPDATED_RLR
        defaultFwYlwpShouldBeFound("rlr.doesNotContain=" + UPDATED_RLR);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRlrqIsEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where rlrq equals to DEFAULT_RLRQ
        defaultFwYlwpShouldBeFound("rlrq.equals=" + DEFAULT_RLRQ);

        // Get all the fwYlwpList where rlrq equals to UPDATED_RLRQ
        defaultFwYlwpShouldNotBeFound("rlrq.equals=" + UPDATED_RLRQ);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRlrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where rlrq not equals to DEFAULT_RLRQ
        defaultFwYlwpShouldNotBeFound("rlrq.notEquals=" + DEFAULT_RLRQ);

        // Get all the fwYlwpList where rlrq not equals to UPDATED_RLRQ
        defaultFwYlwpShouldBeFound("rlrq.notEquals=" + UPDATED_RLRQ);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRlrqIsInShouldWork() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where rlrq in DEFAULT_RLRQ or UPDATED_RLRQ
        defaultFwYlwpShouldBeFound("rlrq.in=" + DEFAULT_RLRQ + "," + UPDATED_RLRQ);

        // Get all the fwYlwpList where rlrq equals to UPDATED_RLRQ
        defaultFwYlwpShouldNotBeFound("rlrq.in=" + UPDATED_RLRQ);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRlrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where rlrq is not null
        defaultFwYlwpShouldBeFound("rlrq.specified=true");

        // Get all the fwYlwpList where rlrq is null
        defaultFwYlwpShouldNotBeFound("rlrq.specified=false");
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where remark equals to DEFAULT_REMARK
        defaultFwYlwpShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the fwYlwpList where remark equals to UPDATED_REMARK
        defaultFwYlwpShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where remark not equals to DEFAULT_REMARK
        defaultFwYlwpShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the fwYlwpList where remark not equals to UPDATED_REMARK
        defaultFwYlwpShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultFwYlwpShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the fwYlwpList where remark equals to UPDATED_REMARK
        defaultFwYlwpShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where remark is not null
        defaultFwYlwpShouldBeFound("remark.specified=true");

        // Get all the fwYlwpList where remark is null
        defaultFwYlwpShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRemarkContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where remark contains DEFAULT_REMARK
        defaultFwYlwpShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the fwYlwpList where remark contains UPDATED_REMARK
        defaultFwYlwpShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where remark does not contain DEFAULT_REMARK
        defaultFwYlwpShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the fwYlwpList where remark does not contain UPDATED_REMARK
        defaultFwYlwpShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where empn equals to DEFAULT_EMPN
        defaultFwYlwpShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the fwYlwpList where empn equals to UPDATED_EMPN
        defaultFwYlwpShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where empn not equals to DEFAULT_EMPN
        defaultFwYlwpShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the fwYlwpList where empn not equals to UPDATED_EMPN
        defaultFwYlwpShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultFwYlwpShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the fwYlwpList where empn equals to UPDATED_EMPN
        defaultFwYlwpShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where empn is not null
        defaultFwYlwpShouldBeFound("empn.specified=true");

        // Get all the fwYlwpList where empn is null
        defaultFwYlwpShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllFwYlwpsByEmpnContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where empn contains DEFAULT_EMPN
        defaultFwYlwpShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the fwYlwpList where empn contains UPDATED_EMPN
        defaultFwYlwpShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where empn does not contain DEFAULT_EMPN
        defaultFwYlwpShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the fwYlwpList where empn does not contain UPDATED_EMPN
        defaultFwYlwpShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByCzrqIsEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where czrq equals to DEFAULT_CZRQ
        defaultFwYlwpShouldBeFound("czrq.equals=" + DEFAULT_CZRQ);

        // Get all the fwYlwpList where czrq equals to UPDATED_CZRQ
        defaultFwYlwpShouldNotBeFound("czrq.equals=" + UPDATED_CZRQ);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByCzrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where czrq not equals to DEFAULT_CZRQ
        defaultFwYlwpShouldNotBeFound("czrq.notEquals=" + DEFAULT_CZRQ);

        // Get all the fwYlwpList where czrq not equals to UPDATED_CZRQ
        defaultFwYlwpShouldBeFound("czrq.notEquals=" + UPDATED_CZRQ);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByCzrqIsInShouldWork() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where czrq in DEFAULT_CZRQ or UPDATED_CZRQ
        defaultFwYlwpShouldBeFound("czrq.in=" + DEFAULT_CZRQ + "," + UPDATED_CZRQ);

        // Get all the fwYlwpList where czrq equals to UPDATED_CZRQ
        defaultFwYlwpShouldNotBeFound("czrq.in=" + UPDATED_CZRQ);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByCzrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where czrq is not null
        defaultFwYlwpShouldBeFound("czrq.specified=true");

        // Get all the fwYlwpList where czrq is null
        defaultFwYlwpShouldNotBeFound("czrq.specified=false");
    }

    @Test
    @Transactional
    void getAllFwYlwpsByFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where flag equals to DEFAULT_FLAG
        defaultFwYlwpShouldBeFound("flag.equals=" + DEFAULT_FLAG);

        // Get all the fwYlwpList where flag equals to UPDATED_FLAG
        defaultFwYlwpShouldNotBeFound("flag.equals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where flag not equals to DEFAULT_FLAG
        defaultFwYlwpShouldNotBeFound("flag.notEquals=" + DEFAULT_FLAG);

        // Get all the fwYlwpList where flag not equals to UPDATED_FLAG
        defaultFwYlwpShouldBeFound("flag.notEquals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByFlagIsInShouldWork() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where flag in DEFAULT_FLAG or UPDATED_FLAG
        defaultFwYlwpShouldBeFound("flag.in=" + DEFAULT_FLAG + "," + UPDATED_FLAG);

        // Get all the fwYlwpList where flag equals to UPDATED_FLAG
        defaultFwYlwpShouldNotBeFound("flag.in=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where flag is not null
        defaultFwYlwpShouldBeFound("flag.specified=true");

        // Get all the fwYlwpList where flag is null
        defaultFwYlwpShouldNotBeFound("flag.specified=false");
    }

    @Test
    @Transactional
    void getAllFwYlwpsByFlagContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where flag contains DEFAULT_FLAG
        defaultFwYlwpShouldBeFound("flag.contains=" + DEFAULT_FLAG);

        // Get all the fwYlwpList where flag contains UPDATED_FLAG
        defaultFwYlwpShouldNotBeFound("flag.contains=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllFwYlwpsByFlagNotContainsSomething() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        // Get all the fwYlwpList where flag does not contain DEFAULT_FLAG
        defaultFwYlwpShouldNotBeFound("flag.doesNotContain=" + DEFAULT_FLAG);

        // Get all the fwYlwpList where flag does not contain UPDATED_FLAG
        defaultFwYlwpShouldBeFound("flag.doesNotContain=" + UPDATED_FLAG);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFwYlwpShouldBeFound(String filter) throws Exception {
        restFwYlwpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwYlwp.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].sdr").value(hasItem(DEFAULT_SDR)))
            .andExpect(jsonPath("$.[*].sdrq").value(hasItem(DEFAULT_SDRQ.toString())))
            .andExpect(jsonPath("$.[*].rlr").value(hasItem(DEFAULT_RLR)))
            .andExpect(jsonPath("$.[*].rlrq").value(hasItem(DEFAULT_RLRQ.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].czrq").value(hasItem(DEFAULT_CZRQ.toString())))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG)));

        // Check, that the count call also returns 1
        restFwYlwpMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFwYlwpShouldNotBeFound(String filter) throws Exception {
        restFwYlwpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFwYlwpMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFwYlwp() throws Exception {
        // Get the fwYlwp
        restFwYlwpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFwYlwp() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        int databaseSizeBeforeUpdate = fwYlwpRepository.findAll().size();

        // Update the fwYlwp
        FwYlwp updatedFwYlwp = fwYlwpRepository.findById(fwYlwp.getId()).get();
        // Disconnect from session so that the updates on updatedFwYlwp are not directly saved in db
        em.detach(updatedFwYlwp);
        updatedFwYlwp
            .roomn(UPDATED_ROOMN)
            .guestname(UPDATED_GUESTNAME)
            .memo(UPDATED_MEMO)
            .sdr(UPDATED_SDR)
            .sdrq(UPDATED_SDRQ)
            .rlr(UPDATED_RLR)
            .rlrq(UPDATED_RLRQ)
            .remark(UPDATED_REMARK)
            .empn(UPDATED_EMPN)
            .czrq(UPDATED_CZRQ)
            .flag(UPDATED_FLAG);
        FwYlwpDTO fwYlwpDTO = fwYlwpMapper.toDto(updatedFwYlwp);

        restFwYlwpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fwYlwpDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwYlwpDTO))
            )
            .andExpect(status().isOk());

        // Validate the FwYlwp in the database
        List<FwYlwp> fwYlwpList = fwYlwpRepository.findAll();
        assertThat(fwYlwpList).hasSize(databaseSizeBeforeUpdate);
        FwYlwp testFwYlwp = fwYlwpList.get(fwYlwpList.size() - 1);
        assertThat(testFwYlwp.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testFwYlwp.getGuestname()).isEqualTo(UPDATED_GUESTNAME);
        assertThat(testFwYlwp.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testFwYlwp.getSdr()).isEqualTo(UPDATED_SDR);
        assertThat(testFwYlwp.getSdrq()).isEqualTo(UPDATED_SDRQ);
        assertThat(testFwYlwp.getRlr()).isEqualTo(UPDATED_RLR);
        assertThat(testFwYlwp.getRlrq()).isEqualTo(UPDATED_RLRQ);
        assertThat(testFwYlwp.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testFwYlwp.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testFwYlwp.getCzrq()).isEqualTo(UPDATED_CZRQ);
        assertThat(testFwYlwp.getFlag()).isEqualTo(UPDATED_FLAG);

        // Validate the FwYlwp in Elasticsearch
        verify(mockFwYlwpSearchRepository).save(testFwYlwp);
    }

    @Test
    @Transactional
    void putNonExistingFwYlwp() throws Exception {
        int databaseSizeBeforeUpdate = fwYlwpRepository.findAll().size();
        fwYlwp.setId(count.incrementAndGet());

        // Create the FwYlwp
        FwYlwpDTO fwYlwpDTO = fwYlwpMapper.toDto(fwYlwp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFwYlwpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fwYlwpDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwYlwpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwYlwp in the database
        List<FwYlwp> fwYlwpList = fwYlwpRepository.findAll();
        assertThat(fwYlwpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwYlwp in Elasticsearch
        verify(mockFwYlwpSearchRepository, times(0)).save(fwYlwp);
    }

    @Test
    @Transactional
    void putWithIdMismatchFwYlwp() throws Exception {
        int databaseSizeBeforeUpdate = fwYlwpRepository.findAll().size();
        fwYlwp.setId(count.incrementAndGet());

        // Create the FwYlwp
        FwYlwpDTO fwYlwpDTO = fwYlwpMapper.toDto(fwYlwp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwYlwpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwYlwpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwYlwp in the database
        List<FwYlwp> fwYlwpList = fwYlwpRepository.findAll();
        assertThat(fwYlwpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwYlwp in Elasticsearch
        verify(mockFwYlwpSearchRepository, times(0)).save(fwYlwp);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFwYlwp() throws Exception {
        int databaseSizeBeforeUpdate = fwYlwpRepository.findAll().size();
        fwYlwp.setId(count.incrementAndGet());

        // Create the FwYlwp
        FwYlwpDTO fwYlwpDTO = fwYlwpMapper.toDto(fwYlwp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwYlwpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwYlwpDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FwYlwp in the database
        List<FwYlwp> fwYlwpList = fwYlwpRepository.findAll();
        assertThat(fwYlwpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwYlwp in Elasticsearch
        verify(mockFwYlwpSearchRepository, times(0)).save(fwYlwp);
    }

    @Test
    @Transactional
    void partialUpdateFwYlwpWithPatch() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        int databaseSizeBeforeUpdate = fwYlwpRepository.findAll().size();

        // Update the fwYlwp using partial update
        FwYlwp partialUpdatedFwYlwp = new FwYlwp();
        partialUpdatedFwYlwp.setId(fwYlwp.getId());

        partialUpdatedFwYlwp.roomn(UPDATED_ROOMN).guestname(UPDATED_GUESTNAME);

        restFwYlwpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFwYlwp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFwYlwp))
            )
            .andExpect(status().isOk());

        // Validate the FwYlwp in the database
        List<FwYlwp> fwYlwpList = fwYlwpRepository.findAll();
        assertThat(fwYlwpList).hasSize(databaseSizeBeforeUpdate);
        FwYlwp testFwYlwp = fwYlwpList.get(fwYlwpList.size() - 1);
        assertThat(testFwYlwp.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testFwYlwp.getGuestname()).isEqualTo(UPDATED_GUESTNAME);
        assertThat(testFwYlwp.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testFwYlwp.getSdr()).isEqualTo(DEFAULT_SDR);
        assertThat(testFwYlwp.getSdrq()).isEqualTo(DEFAULT_SDRQ);
        assertThat(testFwYlwp.getRlr()).isEqualTo(DEFAULT_RLR);
        assertThat(testFwYlwp.getRlrq()).isEqualTo(DEFAULT_RLRQ);
        assertThat(testFwYlwp.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testFwYlwp.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testFwYlwp.getCzrq()).isEqualTo(DEFAULT_CZRQ);
        assertThat(testFwYlwp.getFlag()).isEqualTo(DEFAULT_FLAG);
    }

    @Test
    @Transactional
    void fullUpdateFwYlwpWithPatch() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        int databaseSizeBeforeUpdate = fwYlwpRepository.findAll().size();

        // Update the fwYlwp using partial update
        FwYlwp partialUpdatedFwYlwp = new FwYlwp();
        partialUpdatedFwYlwp.setId(fwYlwp.getId());

        partialUpdatedFwYlwp
            .roomn(UPDATED_ROOMN)
            .guestname(UPDATED_GUESTNAME)
            .memo(UPDATED_MEMO)
            .sdr(UPDATED_SDR)
            .sdrq(UPDATED_SDRQ)
            .rlr(UPDATED_RLR)
            .rlrq(UPDATED_RLRQ)
            .remark(UPDATED_REMARK)
            .empn(UPDATED_EMPN)
            .czrq(UPDATED_CZRQ)
            .flag(UPDATED_FLAG);

        restFwYlwpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFwYlwp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFwYlwp))
            )
            .andExpect(status().isOk());

        // Validate the FwYlwp in the database
        List<FwYlwp> fwYlwpList = fwYlwpRepository.findAll();
        assertThat(fwYlwpList).hasSize(databaseSizeBeforeUpdate);
        FwYlwp testFwYlwp = fwYlwpList.get(fwYlwpList.size() - 1);
        assertThat(testFwYlwp.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testFwYlwp.getGuestname()).isEqualTo(UPDATED_GUESTNAME);
        assertThat(testFwYlwp.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testFwYlwp.getSdr()).isEqualTo(UPDATED_SDR);
        assertThat(testFwYlwp.getSdrq()).isEqualTo(UPDATED_SDRQ);
        assertThat(testFwYlwp.getRlr()).isEqualTo(UPDATED_RLR);
        assertThat(testFwYlwp.getRlrq()).isEqualTo(UPDATED_RLRQ);
        assertThat(testFwYlwp.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testFwYlwp.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testFwYlwp.getCzrq()).isEqualTo(UPDATED_CZRQ);
        assertThat(testFwYlwp.getFlag()).isEqualTo(UPDATED_FLAG);
    }

    @Test
    @Transactional
    void patchNonExistingFwYlwp() throws Exception {
        int databaseSizeBeforeUpdate = fwYlwpRepository.findAll().size();
        fwYlwp.setId(count.incrementAndGet());

        // Create the FwYlwp
        FwYlwpDTO fwYlwpDTO = fwYlwpMapper.toDto(fwYlwp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFwYlwpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fwYlwpDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fwYlwpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwYlwp in the database
        List<FwYlwp> fwYlwpList = fwYlwpRepository.findAll();
        assertThat(fwYlwpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwYlwp in Elasticsearch
        verify(mockFwYlwpSearchRepository, times(0)).save(fwYlwp);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFwYlwp() throws Exception {
        int databaseSizeBeforeUpdate = fwYlwpRepository.findAll().size();
        fwYlwp.setId(count.incrementAndGet());

        // Create the FwYlwp
        FwYlwpDTO fwYlwpDTO = fwYlwpMapper.toDto(fwYlwp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwYlwpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fwYlwpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwYlwp in the database
        List<FwYlwp> fwYlwpList = fwYlwpRepository.findAll();
        assertThat(fwYlwpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwYlwp in Elasticsearch
        verify(mockFwYlwpSearchRepository, times(0)).save(fwYlwp);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFwYlwp() throws Exception {
        int databaseSizeBeforeUpdate = fwYlwpRepository.findAll().size();
        fwYlwp.setId(count.incrementAndGet());

        // Create the FwYlwp
        FwYlwpDTO fwYlwpDTO = fwYlwpMapper.toDto(fwYlwp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwYlwpMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fwYlwpDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FwYlwp in the database
        List<FwYlwp> fwYlwpList = fwYlwpRepository.findAll();
        assertThat(fwYlwpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwYlwp in Elasticsearch
        verify(mockFwYlwpSearchRepository, times(0)).save(fwYlwp);
    }

    @Test
    @Transactional
    void deleteFwYlwp() throws Exception {
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);

        int databaseSizeBeforeDelete = fwYlwpRepository.findAll().size();

        // Delete the fwYlwp
        restFwYlwpMockMvc
            .perform(delete(ENTITY_API_URL_ID, fwYlwp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FwYlwp> fwYlwpList = fwYlwpRepository.findAll();
        assertThat(fwYlwpList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FwYlwp in Elasticsearch
        verify(mockFwYlwpSearchRepository, times(1)).deleteById(fwYlwp.getId());
    }

    @Test
    @Transactional
    void searchFwYlwp() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        fwYlwpRepository.saveAndFlush(fwYlwp);
        when(mockFwYlwpSearchRepository.search(queryStringQuery("id:" + fwYlwp.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fwYlwp), PageRequest.of(0, 1), 1));

        // Search the fwYlwp
        restFwYlwpMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + fwYlwp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwYlwp.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].sdr").value(hasItem(DEFAULT_SDR)))
            .andExpect(jsonPath("$.[*].sdrq").value(hasItem(DEFAULT_SDRQ.toString())))
            .andExpect(jsonPath("$.[*].rlr").value(hasItem(DEFAULT_RLR)))
            .andExpect(jsonPath("$.[*].rlrq").value(hasItem(DEFAULT_RLRQ.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].czrq").value(hasItem(DEFAULT_CZRQ.toString())))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG)));
    }
}
