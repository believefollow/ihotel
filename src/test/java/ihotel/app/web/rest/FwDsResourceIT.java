package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.FwDs;
import ihotel.app.repository.FwDsRepository;
import ihotel.app.repository.search.FwDsSearchRepository;
import ihotel.app.service.criteria.FwDsCriteria;
import ihotel.app.service.dto.FwDsDTO;
import ihotel.app.service.mapper.FwDsMapper;
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
 * Integration tests for the {@link FwDsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FwDsResourceIT {

    private static final Instant DEFAULT_HOTELTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOTELTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_RQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_XZ = 1L;
    private static final Long UPDATED_XZ = 2L;
    private static final Long SMALLER_XZ = 1L - 1L;

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final String DEFAULT_FWY = "AAAAAAAAAA";
    private static final String UPDATED_FWY = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMN = "AAAAAAAAAA";
    private static final String UPDATED_ROOMN = "BBBBBBBBBB";

    private static final String DEFAULT_RTYPE = "AAAAAAAAAA";
    private static final String UPDATED_RTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final Long DEFAULT_SL = 1L;
    private static final Long UPDATED_SL = 2L;
    private static final Long SMALLER_SL = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/fw-ds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/fw-ds";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FwDsRepository fwDsRepository;

    @Autowired
    private FwDsMapper fwDsMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.FwDsSearchRepositoryMockConfiguration
     */
    @Autowired
    private FwDsSearchRepository mockFwDsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFwDsMockMvc;

    private FwDs fwDs;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FwDs createEntity(EntityManager em) {
        FwDs fwDs = new FwDs()
            .hoteltime(DEFAULT_HOTELTIME)
            .rq(DEFAULT_RQ)
            .xz(DEFAULT_XZ)
            .memo(DEFAULT_MEMO)
            .fwy(DEFAULT_FWY)
            .roomn(DEFAULT_ROOMN)
            .rtype(DEFAULT_RTYPE)
            .empn(DEFAULT_EMPN)
            .sl(DEFAULT_SL);
        return fwDs;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FwDs createUpdatedEntity(EntityManager em) {
        FwDs fwDs = new FwDs()
            .hoteltime(UPDATED_HOTELTIME)
            .rq(UPDATED_RQ)
            .xz(UPDATED_XZ)
            .memo(UPDATED_MEMO)
            .fwy(UPDATED_FWY)
            .roomn(UPDATED_ROOMN)
            .rtype(UPDATED_RTYPE)
            .empn(UPDATED_EMPN)
            .sl(UPDATED_SL);
        return fwDs;
    }

    @BeforeEach
    public void initTest() {
        fwDs = createEntity(em);
    }

    @Test
    @Transactional
    void createFwDs() throws Exception {
        int databaseSizeBeforeCreate = fwDsRepository.findAll().size();
        // Create the FwDs
        FwDsDTO fwDsDTO = fwDsMapper.toDto(fwDs);
        restFwDsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwDsDTO)))
            .andExpect(status().isCreated());

        // Validate the FwDs in the database
        List<FwDs> fwDsList = fwDsRepository.findAll();
        assertThat(fwDsList).hasSize(databaseSizeBeforeCreate + 1);
        FwDs testFwDs = fwDsList.get(fwDsList.size() - 1);
        assertThat(testFwDs.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testFwDs.getRq()).isEqualTo(DEFAULT_RQ);
        assertThat(testFwDs.getXz()).isEqualTo(DEFAULT_XZ);
        assertThat(testFwDs.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testFwDs.getFwy()).isEqualTo(DEFAULT_FWY);
        assertThat(testFwDs.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testFwDs.getRtype()).isEqualTo(DEFAULT_RTYPE);
        assertThat(testFwDs.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testFwDs.getSl()).isEqualTo(DEFAULT_SL);

        // Validate the FwDs in Elasticsearch
        verify(mockFwDsSearchRepository, times(1)).save(testFwDs);
    }

    @Test
    @Transactional
    void createFwDsWithExistingId() throws Exception {
        // Create the FwDs with an existing ID
        fwDs.setId(1L);
        FwDsDTO fwDsDTO = fwDsMapper.toDto(fwDs);

        int databaseSizeBeforeCreate = fwDsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFwDsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwDsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FwDs in the database
        List<FwDs> fwDsList = fwDsRepository.findAll();
        assertThat(fwDsList).hasSize(databaseSizeBeforeCreate);

        // Validate the FwDs in Elasticsearch
        verify(mockFwDsSearchRepository, times(0)).save(fwDs);
    }

    @Test
    @Transactional
    void getAllFwDs() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList
        restFwDsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwDs.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].xz").value(hasItem(DEFAULT_XZ.intValue())))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].fwy").value(hasItem(DEFAULT_FWY)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].rtype").value(hasItem(DEFAULT_RTYPE)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(DEFAULT_SL.intValue())));
    }

    @Test
    @Transactional
    void getFwDs() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get the fwDs
        restFwDsMockMvc
            .perform(get(ENTITY_API_URL_ID, fwDs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fwDs.getId().intValue()))
            .andExpect(jsonPath("$.hoteltime").value(DEFAULT_HOTELTIME.toString()))
            .andExpect(jsonPath("$.rq").value(DEFAULT_RQ.toString()))
            .andExpect(jsonPath("$.xz").value(DEFAULT_XZ.intValue()))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO))
            .andExpect(jsonPath("$.fwy").value(DEFAULT_FWY))
            .andExpect(jsonPath("$.roomn").value(DEFAULT_ROOMN))
            .andExpect(jsonPath("$.rtype").value(DEFAULT_RTYPE))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.sl").value(DEFAULT_SL.intValue()));
    }

    @Test
    @Transactional
    void getFwDsByIdFiltering() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        Long id = fwDs.getId();

        defaultFwDsShouldBeFound("id.equals=" + id);
        defaultFwDsShouldNotBeFound("id.notEquals=" + id);

        defaultFwDsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFwDsShouldNotBeFound("id.greaterThan=" + id);

        defaultFwDsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFwDsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFwDsByHoteltimeIsEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where hoteltime equals to DEFAULT_HOTELTIME
        defaultFwDsShouldBeFound("hoteltime.equals=" + DEFAULT_HOTELTIME);

        // Get all the fwDsList where hoteltime equals to UPDATED_HOTELTIME
        defaultFwDsShouldNotBeFound("hoteltime.equals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllFwDsByHoteltimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where hoteltime not equals to DEFAULT_HOTELTIME
        defaultFwDsShouldNotBeFound("hoteltime.notEquals=" + DEFAULT_HOTELTIME);

        // Get all the fwDsList where hoteltime not equals to UPDATED_HOTELTIME
        defaultFwDsShouldBeFound("hoteltime.notEquals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllFwDsByHoteltimeIsInShouldWork() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where hoteltime in DEFAULT_HOTELTIME or UPDATED_HOTELTIME
        defaultFwDsShouldBeFound("hoteltime.in=" + DEFAULT_HOTELTIME + "," + UPDATED_HOTELTIME);

        // Get all the fwDsList where hoteltime equals to UPDATED_HOTELTIME
        defaultFwDsShouldNotBeFound("hoteltime.in=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllFwDsByHoteltimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where hoteltime is not null
        defaultFwDsShouldBeFound("hoteltime.specified=true");

        // Get all the fwDsList where hoteltime is null
        defaultFwDsShouldNotBeFound("hoteltime.specified=false");
    }

    @Test
    @Transactional
    void getAllFwDsByRqIsEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where rq equals to DEFAULT_RQ
        defaultFwDsShouldBeFound("rq.equals=" + DEFAULT_RQ);

        // Get all the fwDsList where rq equals to UPDATED_RQ
        defaultFwDsShouldNotBeFound("rq.equals=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllFwDsByRqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where rq not equals to DEFAULT_RQ
        defaultFwDsShouldNotBeFound("rq.notEquals=" + DEFAULT_RQ);

        // Get all the fwDsList where rq not equals to UPDATED_RQ
        defaultFwDsShouldBeFound("rq.notEquals=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllFwDsByRqIsInShouldWork() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where rq in DEFAULT_RQ or UPDATED_RQ
        defaultFwDsShouldBeFound("rq.in=" + DEFAULT_RQ + "," + UPDATED_RQ);

        // Get all the fwDsList where rq equals to UPDATED_RQ
        defaultFwDsShouldNotBeFound("rq.in=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllFwDsByRqIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where rq is not null
        defaultFwDsShouldBeFound("rq.specified=true");

        // Get all the fwDsList where rq is null
        defaultFwDsShouldNotBeFound("rq.specified=false");
    }

    @Test
    @Transactional
    void getAllFwDsByXzIsEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where xz equals to DEFAULT_XZ
        defaultFwDsShouldBeFound("xz.equals=" + DEFAULT_XZ);

        // Get all the fwDsList where xz equals to UPDATED_XZ
        defaultFwDsShouldNotBeFound("xz.equals=" + UPDATED_XZ);
    }

    @Test
    @Transactional
    void getAllFwDsByXzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where xz not equals to DEFAULT_XZ
        defaultFwDsShouldNotBeFound("xz.notEquals=" + DEFAULT_XZ);

        // Get all the fwDsList where xz not equals to UPDATED_XZ
        defaultFwDsShouldBeFound("xz.notEquals=" + UPDATED_XZ);
    }

    @Test
    @Transactional
    void getAllFwDsByXzIsInShouldWork() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where xz in DEFAULT_XZ or UPDATED_XZ
        defaultFwDsShouldBeFound("xz.in=" + DEFAULT_XZ + "," + UPDATED_XZ);

        // Get all the fwDsList where xz equals to UPDATED_XZ
        defaultFwDsShouldNotBeFound("xz.in=" + UPDATED_XZ);
    }

    @Test
    @Transactional
    void getAllFwDsByXzIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where xz is not null
        defaultFwDsShouldBeFound("xz.specified=true");

        // Get all the fwDsList where xz is null
        defaultFwDsShouldNotBeFound("xz.specified=false");
    }

    @Test
    @Transactional
    void getAllFwDsByXzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where xz is greater than or equal to DEFAULT_XZ
        defaultFwDsShouldBeFound("xz.greaterThanOrEqual=" + DEFAULT_XZ);

        // Get all the fwDsList where xz is greater than or equal to UPDATED_XZ
        defaultFwDsShouldNotBeFound("xz.greaterThanOrEqual=" + UPDATED_XZ);
    }

    @Test
    @Transactional
    void getAllFwDsByXzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where xz is less than or equal to DEFAULT_XZ
        defaultFwDsShouldBeFound("xz.lessThanOrEqual=" + DEFAULT_XZ);

        // Get all the fwDsList where xz is less than or equal to SMALLER_XZ
        defaultFwDsShouldNotBeFound("xz.lessThanOrEqual=" + SMALLER_XZ);
    }

    @Test
    @Transactional
    void getAllFwDsByXzIsLessThanSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where xz is less than DEFAULT_XZ
        defaultFwDsShouldNotBeFound("xz.lessThan=" + DEFAULT_XZ);

        // Get all the fwDsList where xz is less than UPDATED_XZ
        defaultFwDsShouldBeFound("xz.lessThan=" + UPDATED_XZ);
    }

    @Test
    @Transactional
    void getAllFwDsByXzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where xz is greater than DEFAULT_XZ
        defaultFwDsShouldNotBeFound("xz.greaterThan=" + DEFAULT_XZ);

        // Get all the fwDsList where xz is greater than SMALLER_XZ
        defaultFwDsShouldBeFound("xz.greaterThan=" + SMALLER_XZ);
    }

    @Test
    @Transactional
    void getAllFwDsByMemoIsEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where memo equals to DEFAULT_MEMO
        defaultFwDsShouldBeFound("memo.equals=" + DEFAULT_MEMO);

        // Get all the fwDsList where memo equals to UPDATED_MEMO
        defaultFwDsShouldNotBeFound("memo.equals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwDsByMemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where memo not equals to DEFAULT_MEMO
        defaultFwDsShouldNotBeFound("memo.notEquals=" + DEFAULT_MEMO);

        // Get all the fwDsList where memo not equals to UPDATED_MEMO
        defaultFwDsShouldBeFound("memo.notEquals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwDsByMemoIsInShouldWork() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where memo in DEFAULT_MEMO or UPDATED_MEMO
        defaultFwDsShouldBeFound("memo.in=" + DEFAULT_MEMO + "," + UPDATED_MEMO);

        // Get all the fwDsList where memo equals to UPDATED_MEMO
        defaultFwDsShouldNotBeFound("memo.in=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwDsByMemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where memo is not null
        defaultFwDsShouldBeFound("memo.specified=true");

        // Get all the fwDsList where memo is null
        defaultFwDsShouldNotBeFound("memo.specified=false");
    }

    @Test
    @Transactional
    void getAllFwDsByMemoContainsSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where memo contains DEFAULT_MEMO
        defaultFwDsShouldBeFound("memo.contains=" + DEFAULT_MEMO);

        // Get all the fwDsList where memo contains UPDATED_MEMO
        defaultFwDsShouldNotBeFound("memo.contains=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwDsByMemoNotContainsSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where memo does not contain DEFAULT_MEMO
        defaultFwDsShouldNotBeFound("memo.doesNotContain=" + DEFAULT_MEMO);

        // Get all the fwDsList where memo does not contain UPDATED_MEMO
        defaultFwDsShouldBeFound("memo.doesNotContain=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllFwDsByFwyIsEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where fwy equals to DEFAULT_FWY
        defaultFwDsShouldBeFound("fwy.equals=" + DEFAULT_FWY);

        // Get all the fwDsList where fwy equals to UPDATED_FWY
        defaultFwDsShouldNotBeFound("fwy.equals=" + UPDATED_FWY);
    }

    @Test
    @Transactional
    void getAllFwDsByFwyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where fwy not equals to DEFAULT_FWY
        defaultFwDsShouldNotBeFound("fwy.notEquals=" + DEFAULT_FWY);

        // Get all the fwDsList where fwy not equals to UPDATED_FWY
        defaultFwDsShouldBeFound("fwy.notEquals=" + UPDATED_FWY);
    }

    @Test
    @Transactional
    void getAllFwDsByFwyIsInShouldWork() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where fwy in DEFAULT_FWY or UPDATED_FWY
        defaultFwDsShouldBeFound("fwy.in=" + DEFAULT_FWY + "," + UPDATED_FWY);

        // Get all the fwDsList where fwy equals to UPDATED_FWY
        defaultFwDsShouldNotBeFound("fwy.in=" + UPDATED_FWY);
    }

    @Test
    @Transactional
    void getAllFwDsByFwyIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where fwy is not null
        defaultFwDsShouldBeFound("fwy.specified=true");

        // Get all the fwDsList where fwy is null
        defaultFwDsShouldNotBeFound("fwy.specified=false");
    }

    @Test
    @Transactional
    void getAllFwDsByFwyContainsSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where fwy contains DEFAULT_FWY
        defaultFwDsShouldBeFound("fwy.contains=" + DEFAULT_FWY);

        // Get all the fwDsList where fwy contains UPDATED_FWY
        defaultFwDsShouldNotBeFound("fwy.contains=" + UPDATED_FWY);
    }

    @Test
    @Transactional
    void getAllFwDsByFwyNotContainsSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where fwy does not contain DEFAULT_FWY
        defaultFwDsShouldNotBeFound("fwy.doesNotContain=" + DEFAULT_FWY);

        // Get all the fwDsList where fwy does not contain UPDATED_FWY
        defaultFwDsShouldBeFound("fwy.doesNotContain=" + UPDATED_FWY);
    }

    @Test
    @Transactional
    void getAllFwDsByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where roomn equals to DEFAULT_ROOMN
        defaultFwDsShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the fwDsList where roomn equals to UPDATED_ROOMN
        defaultFwDsShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwDsByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where roomn not equals to DEFAULT_ROOMN
        defaultFwDsShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the fwDsList where roomn not equals to UPDATED_ROOMN
        defaultFwDsShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwDsByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultFwDsShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the fwDsList where roomn equals to UPDATED_ROOMN
        defaultFwDsShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwDsByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where roomn is not null
        defaultFwDsShouldBeFound("roomn.specified=true");

        // Get all the fwDsList where roomn is null
        defaultFwDsShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllFwDsByRoomnContainsSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where roomn contains DEFAULT_ROOMN
        defaultFwDsShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the fwDsList where roomn contains UPDATED_ROOMN
        defaultFwDsShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwDsByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where roomn does not contain DEFAULT_ROOMN
        defaultFwDsShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the fwDsList where roomn does not contain UPDATED_ROOMN
        defaultFwDsShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwDsByRtypeIsEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where rtype equals to DEFAULT_RTYPE
        defaultFwDsShouldBeFound("rtype.equals=" + DEFAULT_RTYPE);

        // Get all the fwDsList where rtype equals to UPDATED_RTYPE
        defaultFwDsShouldNotBeFound("rtype.equals=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllFwDsByRtypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where rtype not equals to DEFAULT_RTYPE
        defaultFwDsShouldNotBeFound("rtype.notEquals=" + DEFAULT_RTYPE);

        // Get all the fwDsList where rtype not equals to UPDATED_RTYPE
        defaultFwDsShouldBeFound("rtype.notEquals=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllFwDsByRtypeIsInShouldWork() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where rtype in DEFAULT_RTYPE or UPDATED_RTYPE
        defaultFwDsShouldBeFound("rtype.in=" + DEFAULT_RTYPE + "," + UPDATED_RTYPE);

        // Get all the fwDsList where rtype equals to UPDATED_RTYPE
        defaultFwDsShouldNotBeFound("rtype.in=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllFwDsByRtypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where rtype is not null
        defaultFwDsShouldBeFound("rtype.specified=true");

        // Get all the fwDsList where rtype is null
        defaultFwDsShouldNotBeFound("rtype.specified=false");
    }

    @Test
    @Transactional
    void getAllFwDsByRtypeContainsSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where rtype contains DEFAULT_RTYPE
        defaultFwDsShouldBeFound("rtype.contains=" + DEFAULT_RTYPE);

        // Get all the fwDsList where rtype contains UPDATED_RTYPE
        defaultFwDsShouldNotBeFound("rtype.contains=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllFwDsByRtypeNotContainsSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where rtype does not contain DEFAULT_RTYPE
        defaultFwDsShouldNotBeFound("rtype.doesNotContain=" + DEFAULT_RTYPE);

        // Get all the fwDsList where rtype does not contain UPDATED_RTYPE
        defaultFwDsShouldBeFound("rtype.doesNotContain=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllFwDsByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where empn equals to DEFAULT_EMPN
        defaultFwDsShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the fwDsList where empn equals to UPDATED_EMPN
        defaultFwDsShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwDsByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where empn not equals to DEFAULT_EMPN
        defaultFwDsShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the fwDsList where empn not equals to UPDATED_EMPN
        defaultFwDsShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwDsByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultFwDsShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the fwDsList where empn equals to UPDATED_EMPN
        defaultFwDsShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwDsByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where empn is not null
        defaultFwDsShouldBeFound("empn.specified=true");

        // Get all the fwDsList where empn is null
        defaultFwDsShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllFwDsByEmpnContainsSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where empn contains DEFAULT_EMPN
        defaultFwDsShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the fwDsList where empn contains UPDATED_EMPN
        defaultFwDsShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwDsByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where empn does not contain DEFAULT_EMPN
        defaultFwDsShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the fwDsList where empn does not contain UPDATED_EMPN
        defaultFwDsShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllFwDsBySlIsEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where sl equals to DEFAULT_SL
        defaultFwDsShouldBeFound("sl.equals=" + DEFAULT_SL);

        // Get all the fwDsList where sl equals to UPDATED_SL
        defaultFwDsShouldNotBeFound("sl.equals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllFwDsBySlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where sl not equals to DEFAULT_SL
        defaultFwDsShouldNotBeFound("sl.notEquals=" + DEFAULT_SL);

        // Get all the fwDsList where sl not equals to UPDATED_SL
        defaultFwDsShouldBeFound("sl.notEquals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllFwDsBySlIsInShouldWork() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where sl in DEFAULT_SL or UPDATED_SL
        defaultFwDsShouldBeFound("sl.in=" + DEFAULT_SL + "," + UPDATED_SL);

        // Get all the fwDsList where sl equals to UPDATED_SL
        defaultFwDsShouldNotBeFound("sl.in=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllFwDsBySlIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where sl is not null
        defaultFwDsShouldBeFound("sl.specified=true");

        // Get all the fwDsList where sl is null
        defaultFwDsShouldNotBeFound("sl.specified=false");
    }

    @Test
    @Transactional
    void getAllFwDsBySlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where sl is greater than or equal to DEFAULT_SL
        defaultFwDsShouldBeFound("sl.greaterThanOrEqual=" + DEFAULT_SL);

        // Get all the fwDsList where sl is greater than or equal to UPDATED_SL
        defaultFwDsShouldNotBeFound("sl.greaterThanOrEqual=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllFwDsBySlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where sl is less than or equal to DEFAULT_SL
        defaultFwDsShouldBeFound("sl.lessThanOrEqual=" + DEFAULT_SL);

        // Get all the fwDsList where sl is less than or equal to SMALLER_SL
        defaultFwDsShouldNotBeFound("sl.lessThanOrEqual=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllFwDsBySlIsLessThanSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where sl is less than DEFAULT_SL
        defaultFwDsShouldNotBeFound("sl.lessThan=" + DEFAULT_SL);

        // Get all the fwDsList where sl is less than UPDATED_SL
        defaultFwDsShouldBeFound("sl.lessThan=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllFwDsBySlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        // Get all the fwDsList where sl is greater than DEFAULT_SL
        defaultFwDsShouldNotBeFound("sl.greaterThan=" + DEFAULT_SL);

        // Get all the fwDsList where sl is greater than SMALLER_SL
        defaultFwDsShouldBeFound("sl.greaterThan=" + SMALLER_SL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFwDsShouldBeFound(String filter) throws Exception {
        restFwDsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwDs.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].xz").value(hasItem(DEFAULT_XZ.intValue())))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].fwy").value(hasItem(DEFAULT_FWY)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].rtype").value(hasItem(DEFAULT_RTYPE)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(DEFAULT_SL.intValue())));

        // Check, that the count call also returns 1
        restFwDsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFwDsShouldNotBeFound(String filter) throws Exception {
        restFwDsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFwDsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFwDs() throws Exception {
        // Get the fwDs
        restFwDsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFwDs() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        int databaseSizeBeforeUpdate = fwDsRepository.findAll().size();

        // Update the fwDs
        FwDs updatedFwDs = fwDsRepository.findById(fwDs.getId()).get();
        // Disconnect from session so that the updates on updatedFwDs are not directly saved in db
        em.detach(updatedFwDs);
        updatedFwDs
            .hoteltime(UPDATED_HOTELTIME)
            .rq(UPDATED_RQ)
            .xz(UPDATED_XZ)
            .memo(UPDATED_MEMO)
            .fwy(UPDATED_FWY)
            .roomn(UPDATED_ROOMN)
            .rtype(UPDATED_RTYPE)
            .empn(UPDATED_EMPN)
            .sl(UPDATED_SL);
        FwDsDTO fwDsDTO = fwDsMapper.toDto(updatedFwDs);

        restFwDsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fwDsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwDsDTO))
            )
            .andExpect(status().isOk());

        // Validate the FwDs in the database
        List<FwDs> fwDsList = fwDsRepository.findAll();
        assertThat(fwDsList).hasSize(databaseSizeBeforeUpdate);
        FwDs testFwDs = fwDsList.get(fwDsList.size() - 1);
        assertThat(testFwDs.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testFwDs.getRq()).isEqualTo(UPDATED_RQ);
        assertThat(testFwDs.getXz()).isEqualTo(UPDATED_XZ);
        assertThat(testFwDs.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testFwDs.getFwy()).isEqualTo(UPDATED_FWY);
        assertThat(testFwDs.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testFwDs.getRtype()).isEqualTo(UPDATED_RTYPE);
        assertThat(testFwDs.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testFwDs.getSl()).isEqualTo(UPDATED_SL);

        // Validate the FwDs in Elasticsearch
        verify(mockFwDsSearchRepository).save(testFwDs);
    }

    @Test
    @Transactional
    void putNonExistingFwDs() throws Exception {
        int databaseSizeBeforeUpdate = fwDsRepository.findAll().size();
        fwDs.setId(count.incrementAndGet());

        // Create the FwDs
        FwDsDTO fwDsDTO = fwDsMapper.toDto(fwDs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFwDsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fwDsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwDsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwDs in the database
        List<FwDs> fwDsList = fwDsRepository.findAll();
        assertThat(fwDsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwDs in Elasticsearch
        verify(mockFwDsSearchRepository, times(0)).save(fwDs);
    }

    @Test
    @Transactional
    void putWithIdMismatchFwDs() throws Exception {
        int databaseSizeBeforeUpdate = fwDsRepository.findAll().size();
        fwDs.setId(count.incrementAndGet());

        // Create the FwDs
        FwDsDTO fwDsDTO = fwDsMapper.toDto(fwDs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwDsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwDsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwDs in the database
        List<FwDs> fwDsList = fwDsRepository.findAll();
        assertThat(fwDsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwDs in Elasticsearch
        verify(mockFwDsSearchRepository, times(0)).save(fwDs);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFwDs() throws Exception {
        int databaseSizeBeforeUpdate = fwDsRepository.findAll().size();
        fwDs.setId(count.incrementAndGet());

        // Create the FwDs
        FwDsDTO fwDsDTO = fwDsMapper.toDto(fwDs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwDsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwDsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FwDs in the database
        List<FwDs> fwDsList = fwDsRepository.findAll();
        assertThat(fwDsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwDs in Elasticsearch
        verify(mockFwDsSearchRepository, times(0)).save(fwDs);
    }

    @Test
    @Transactional
    void partialUpdateFwDsWithPatch() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        int databaseSizeBeforeUpdate = fwDsRepository.findAll().size();

        // Update the fwDs using partial update
        FwDs partialUpdatedFwDs = new FwDs();
        partialUpdatedFwDs.setId(fwDs.getId());

        partialUpdatedFwDs.xz(UPDATED_XZ).memo(UPDATED_MEMO).rtype(UPDATED_RTYPE).empn(UPDATED_EMPN);

        restFwDsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFwDs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFwDs))
            )
            .andExpect(status().isOk());

        // Validate the FwDs in the database
        List<FwDs> fwDsList = fwDsRepository.findAll();
        assertThat(fwDsList).hasSize(databaseSizeBeforeUpdate);
        FwDs testFwDs = fwDsList.get(fwDsList.size() - 1);
        assertThat(testFwDs.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testFwDs.getRq()).isEqualTo(DEFAULT_RQ);
        assertThat(testFwDs.getXz()).isEqualTo(UPDATED_XZ);
        assertThat(testFwDs.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testFwDs.getFwy()).isEqualTo(DEFAULT_FWY);
        assertThat(testFwDs.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testFwDs.getRtype()).isEqualTo(UPDATED_RTYPE);
        assertThat(testFwDs.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testFwDs.getSl()).isEqualTo(DEFAULT_SL);
    }

    @Test
    @Transactional
    void fullUpdateFwDsWithPatch() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        int databaseSizeBeforeUpdate = fwDsRepository.findAll().size();

        // Update the fwDs using partial update
        FwDs partialUpdatedFwDs = new FwDs();
        partialUpdatedFwDs.setId(fwDs.getId());

        partialUpdatedFwDs
            .hoteltime(UPDATED_HOTELTIME)
            .rq(UPDATED_RQ)
            .xz(UPDATED_XZ)
            .memo(UPDATED_MEMO)
            .fwy(UPDATED_FWY)
            .roomn(UPDATED_ROOMN)
            .rtype(UPDATED_RTYPE)
            .empn(UPDATED_EMPN)
            .sl(UPDATED_SL);

        restFwDsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFwDs.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFwDs))
            )
            .andExpect(status().isOk());

        // Validate the FwDs in the database
        List<FwDs> fwDsList = fwDsRepository.findAll();
        assertThat(fwDsList).hasSize(databaseSizeBeforeUpdate);
        FwDs testFwDs = fwDsList.get(fwDsList.size() - 1);
        assertThat(testFwDs.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testFwDs.getRq()).isEqualTo(UPDATED_RQ);
        assertThat(testFwDs.getXz()).isEqualTo(UPDATED_XZ);
        assertThat(testFwDs.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testFwDs.getFwy()).isEqualTo(UPDATED_FWY);
        assertThat(testFwDs.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testFwDs.getRtype()).isEqualTo(UPDATED_RTYPE);
        assertThat(testFwDs.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testFwDs.getSl()).isEqualTo(UPDATED_SL);
    }

    @Test
    @Transactional
    void patchNonExistingFwDs() throws Exception {
        int databaseSizeBeforeUpdate = fwDsRepository.findAll().size();
        fwDs.setId(count.incrementAndGet());

        // Create the FwDs
        FwDsDTO fwDsDTO = fwDsMapper.toDto(fwDs);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFwDsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fwDsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fwDsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwDs in the database
        List<FwDs> fwDsList = fwDsRepository.findAll();
        assertThat(fwDsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwDs in Elasticsearch
        verify(mockFwDsSearchRepository, times(0)).save(fwDs);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFwDs() throws Exception {
        int databaseSizeBeforeUpdate = fwDsRepository.findAll().size();
        fwDs.setId(count.incrementAndGet());

        // Create the FwDs
        FwDsDTO fwDsDTO = fwDsMapper.toDto(fwDs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwDsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fwDsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwDs in the database
        List<FwDs> fwDsList = fwDsRepository.findAll();
        assertThat(fwDsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwDs in Elasticsearch
        verify(mockFwDsSearchRepository, times(0)).save(fwDs);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFwDs() throws Exception {
        int databaseSizeBeforeUpdate = fwDsRepository.findAll().size();
        fwDs.setId(count.incrementAndGet());

        // Create the FwDs
        FwDsDTO fwDsDTO = fwDsMapper.toDto(fwDs);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwDsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fwDsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FwDs in the database
        List<FwDs> fwDsList = fwDsRepository.findAll();
        assertThat(fwDsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwDs in Elasticsearch
        verify(mockFwDsSearchRepository, times(0)).save(fwDs);
    }

    @Test
    @Transactional
    void deleteFwDs() throws Exception {
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);

        int databaseSizeBeforeDelete = fwDsRepository.findAll().size();

        // Delete the fwDs
        restFwDsMockMvc
            .perform(delete(ENTITY_API_URL_ID, fwDs.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FwDs> fwDsList = fwDsRepository.findAll();
        assertThat(fwDsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FwDs in Elasticsearch
        verify(mockFwDsSearchRepository, times(1)).deleteById(fwDs.getId());
    }

    @Test
    @Transactional
    void searchFwDs() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        fwDsRepository.saveAndFlush(fwDs);
        when(mockFwDsSearchRepository.search(queryStringQuery("id:" + fwDs.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fwDs), PageRequest.of(0, 1), 1));

        // Search the fwDs
        restFwDsMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + fwDs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwDs.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].xz").value(hasItem(DEFAULT_XZ.intValue())))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].fwy").value(hasItem(DEFAULT_FWY)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].rtype").value(hasItem(DEFAULT_RTYPE)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(DEFAULT_SL.intValue())));
    }
}
