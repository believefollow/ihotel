package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.FwJywp;
import ihotel.app.repository.FwJywpRepository;
import ihotel.app.repository.search.FwJywpSearchRepository;
import ihotel.app.service.criteria.FwJywpCriteria;
import ihotel.app.service.dto.FwJywpDTO;
import ihotel.app.service.mapper.FwJywpMapper;
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
 * Integration tests for the {@link FwJywpResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FwJywpResourceIT {

    private static final Instant DEFAULT_JYRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JYRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_ROOMN = "AAAAAAAAAA";
    private static final String UPDATED_ROOMN = "BBBBBBBBBB";

    private static final String DEFAULT_GUESTNAME = "AAAAAAAAAA";
    private static final String UPDATED_GUESTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_JYWP = "AAAAAAAAAA";
    private static final String UPDATED_JYWP = "BBBBBBBBBB";

    private static final String DEFAULT_FWY = "AAAAAAAAAA";
    private static final String UPDATED_FWY = "BBBBBBBBBB";

    private static final String DEFAULT_DJR = "AAAAAAAAAA";
    private static final String UPDATED_DJR = "BBBBBBBBBB";

    private static final String DEFAULT_FLAG = "AA";
    private static final String UPDATED_FLAG = "BB";

    private static final Instant DEFAULT_GHRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GHRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DJRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DJRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fw-jywps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/fw-jywps";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FwJywpRepository fwJywpRepository;

    @Autowired
    private FwJywpMapper fwJywpMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.FwJywpSearchRepositoryMockConfiguration
     */
    @Autowired
    private FwJywpSearchRepository mockFwJywpSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFwJywpMockMvc;

    private FwJywp fwJywp;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FwJywp createEntity(EntityManager em) {
        FwJywp fwJywp = new FwJywp()
            .jyrq(DEFAULT_JYRQ)
            .roomn(DEFAULT_ROOMN)
            .guestname(DEFAULT_GUESTNAME)
            .jywp(DEFAULT_JYWP)
            .fwy(DEFAULT_FWY)
            .djr(DEFAULT_DJR)
            .flag(DEFAULT_FLAG)
            .ghrq(DEFAULT_GHRQ)
            .djrq(DEFAULT_DJRQ)
            .remark(DEFAULT_REMARK);
        return fwJywp;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FwJywp createUpdatedEntity(EntityManager em) {
        FwJywp fwJywp = new FwJywp()
            .jyrq(UPDATED_JYRQ)
            .roomn(UPDATED_ROOMN)
            .guestname(UPDATED_GUESTNAME)
            .jywp(UPDATED_JYWP)
            .fwy(UPDATED_FWY)
            .djr(UPDATED_DJR)
            .flag(UPDATED_FLAG)
            .ghrq(UPDATED_GHRQ)
            .djrq(UPDATED_DJRQ)
            .remark(UPDATED_REMARK);
        return fwJywp;
    }

    @BeforeEach
    public void initTest() {
        fwJywp = createEntity(em);
    }

    @Test
    @Transactional
    void createFwJywp() throws Exception {
        int databaseSizeBeforeCreate = fwJywpRepository.findAll().size();
        // Create the FwJywp
        FwJywpDTO fwJywpDTO = fwJywpMapper.toDto(fwJywp);
        restFwJywpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwJywpDTO)))
            .andExpect(status().isCreated());

        // Validate the FwJywp in the database
        List<FwJywp> fwJywpList = fwJywpRepository.findAll();
        assertThat(fwJywpList).hasSize(databaseSizeBeforeCreate + 1);
        FwJywp testFwJywp = fwJywpList.get(fwJywpList.size() - 1);
        assertThat(testFwJywp.getJyrq()).isEqualTo(DEFAULT_JYRQ);
        assertThat(testFwJywp.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testFwJywp.getGuestname()).isEqualTo(DEFAULT_GUESTNAME);
        assertThat(testFwJywp.getJywp()).isEqualTo(DEFAULT_JYWP);
        assertThat(testFwJywp.getFwy()).isEqualTo(DEFAULT_FWY);
        assertThat(testFwJywp.getDjr()).isEqualTo(DEFAULT_DJR);
        assertThat(testFwJywp.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testFwJywp.getGhrq()).isEqualTo(DEFAULT_GHRQ);
        assertThat(testFwJywp.getDjrq()).isEqualTo(DEFAULT_DJRQ);
        assertThat(testFwJywp.getRemark()).isEqualTo(DEFAULT_REMARK);

        // Validate the FwJywp in Elasticsearch
        verify(mockFwJywpSearchRepository, times(1)).save(testFwJywp);
    }

    @Test
    @Transactional
    void createFwJywpWithExistingId() throws Exception {
        // Create the FwJywp with an existing ID
        fwJywp.setId(1L);
        FwJywpDTO fwJywpDTO = fwJywpMapper.toDto(fwJywp);

        int databaseSizeBeforeCreate = fwJywpRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFwJywpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwJywpDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FwJywp in the database
        List<FwJywp> fwJywpList = fwJywpRepository.findAll();
        assertThat(fwJywpList).hasSize(databaseSizeBeforeCreate);

        // Validate the FwJywp in Elasticsearch
        verify(mockFwJywpSearchRepository, times(0)).save(fwJywp);
    }

    @Test
    @Transactional
    void getAllFwJywps() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList
        restFwJywpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwJywp.getId().intValue())))
            .andExpect(jsonPath("$.[*].jyrq").value(hasItem(DEFAULT_JYRQ.toString())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].jywp").value(hasItem(DEFAULT_JYWP)))
            .andExpect(jsonPath("$.[*].fwy").value(hasItem(DEFAULT_FWY)))
            .andExpect(jsonPath("$.[*].djr").value(hasItem(DEFAULT_DJR)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG)))
            .andExpect(jsonPath("$.[*].ghrq").value(hasItem(DEFAULT_GHRQ.toString())))
            .andExpect(jsonPath("$.[*].djrq").value(hasItem(DEFAULT_DJRQ.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));
    }

    @Test
    @Transactional
    void getFwJywp() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get the fwJywp
        restFwJywpMockMvc
            .perform(get(ENTITY_API_URL_ID, fwJywp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fwJywp.getId().intValue()))
            .andExpect(jsonPath("$.jyrq").value(DEFAULT_JYRQ.toString()))
            .andExpect(jsonPath("$.roomn").value(DEFAULT_ROOMN))
            .andExpect(jsonPath("$.guestname").value(DEFAULT_GUESTNAME))
            .andExpect(jsonPath("$.jywp").value(DEFAULT_JYWP))
            .andExpect(jsonPath("$.fwy").value(DEFAULT_FWY))
            .andExpect(jsonPath("$.djr").value(DEFAULT_DJR))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG))
            .andExpect(jsonPath("$.ghrq").value(DEFAULT_GHRQ.toString()))
            .andExpect(jsonPath("$.djrq").value(DEFAULT_DJRQ.toString()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK));
    }

    @Test
    @Transactional
    void getFwJywpsByIdFiltering() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        Long id = fwJywp.getId();

        defaultFwJywpShouldBeFound("id.equals=" + id);
        defaultFwJywpShouldNotBeFound("id.notEquals=" + id);

        defaultFwJywpShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFwJywpShouldNotBeFound("id.greaterThan=" + id);

        defaultFwJywpShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFwJywpShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFwJywpsByJyrqIsEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where jyrq equals to DEFAULT_JYRQ
        defaultFwJywpShouldBeFound("jyrq.equals=" + DEFAULT_JYRQ);

        // Get all the fwJywpList where jyrq equals to UPDATED_JYRQ
        defaultFwJywpShouldNotBeFound("jyrq.equals=" + UPDATED_JYRQ);
    }

    @Test
    @Transactional
    void getAllFwJywpsByJyrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where jyrq not equals to DEFAULT_JYRQ
        defaultFwJywpShouldNotBeFound("jyrq.notEquals=" + DEFAULT_JYRQ);

        // Get all the fwJywpList where jyrq not equals to UPDATED_JYRQ
        defaultFwJywpShouldBeFound("jyrq.notEquals=" + UPDATED_JYRQ);
    }

    @Test
    @Transactional
    void getAllFwJywpsByJyrqIsInShouldWork() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where jyrq in DEFAULT_JYRQ or UPDATED_JYRQ
        defaultFwJywpShouldBeFound("jyrq.in=" + DEFAULT_JYRQ + "," + UPDATED_JYRQ);

        // Get all the fwJywpList where jyrq equals to UPDATED_JYRQ
        defaultFwJywpShouldNotBeFound("jyrq.in=" + UPDATED_JYRQ);
    }

    @Test
    @Transactional
    void getAllFwJywpsByJyrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where jyrq is not null
        defaultFwJywpShouldBeFound("jyrq.specified=true");

        // Get all the fwJywpList where jyrq is null
        defaultFwJywpShouldNotBeFound("jyrq.specified=false");
    }

    @Test
    @Transactional
    void getAllFwJywpsByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where roomn equals to DEFAULT_ROOMN
        defaultFwJywpShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the fwJywpList where roomn equals to UPDATED_ROOMN
        defaultFwJywpShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwJywpsByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where roomn not equals to DEFAULT_ROOMN
        defaultFwJywpShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the fwJywpList where roomn not equals to UPDATED_ROOMN
        defaultFwJywpShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwJywpsByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultFwJywpShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the fwJywpList where roomn equals to UPDATED_ROOMN
        defaultFwJywpShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwJywpsByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where roomn is not null
        defaultFwJywpShouldBeFound("roomn.specified=true");

        // Get all the fwJywpList where roomn is null
        defaultFwJywpShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllFwJywpsByRoomnContainsSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where roomn contains DEFAULT_ROOMN
        defaultFwJywpShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the fwJywpList where roomn contains UPDATED_ROOMN
        defaultFwJywpShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwJywpsByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where roomn does not contain DEFAULT_ROOMN
        defaultFwJywpShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the fwJywpList where roomn does not contain UPDATED_ROOMN
        defaultFwJywpShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllFwJywpsByGuestnameIsEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where guestname equals to DEFAULT_GUESTNAME
        defaultFwJywpShouldBeFound("guestname.equals=" + DEFAULT_GUESTNAME);

        // Get all the fwJywpList where guestname equals to UPDATED_GUESTNAME
        defaultFwJywpShouldNotBeFound("guestname.equals=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllFwJywpsByGuestnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where guestname not equals to DEFAULT_GUESTNAME
        defaultFwJywpShouldNotBeFound("guestname.notEquals=" + DEFAULT_GUESTNAME);

        // Get all the fwJywpList where guestname not equals to UPDATED_GUESTNAME
        defaultFwJywpShouldBeFound("guestname.notEquals=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllFwJywpsByGuestnameIsInShouldWork() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where guestname in DEFAULT_GUESTNAME or UPDATED_GUESTNAME
        defaultFwJywpShouldBeFound("guestname.in=" + DEFAULT_GUESTNAME + "," + UPDATED_GUESTNAME);

        // Get all the fwJywpList where guestname equals to UPDATED_GUESTNAME
        defaultFwJywpShouldNotBeFound("guestname.in=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllFwJywpsByGuestnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where guestname is not null
        defaultFwJywpShouldBeFound("guestname.specified=true");

        // Get all the fwJywpList where guestname is null
        defaultFwJywpShouldNotBeFound("guestname.specified=false");
    }

    @Test
    @Transactional
    void getAllFwJywpsByGuestnameContainsSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where guestname contains DEFAULT_GUESTNAME
        defaultFwJywpShouldBeFound("guestname.contains=" + DEFAULT_GUESTNAME);

        // Get all the fwJywpList where guestname contains UPDATED_GUESTNAME
        defaultFwJywpShouldNotBeFound("guestname.contains=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllFwJywpsByGuestnameNotContainsSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where guestname does not contain DEFAULT_GUESTNAME
        defaultFwJywpShouldNotBeFound("guestname.doesNotContain=" + DEFAULT_GUESTNAME);

        // Get all the fwJywpList where guestname does not contain UPDATED_GUESTNAME
        defaultFwJywpShouldBeFound("guestname.doesNotContain=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllFwJywpsByJywpIsEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where jywp equals to DEFAULT_JYWP
        defaultFwJywpShouldBeFound("jywp.equals=" + DEFAULT_JYWP);

        // Get all the fwJywpList where jywp equals to UPDATED_JYWP
        defaultFwJywpShouldNotBeFound("jywp.equals=" + UPDATED_JYWP);
    }

    @Test
    @Transactional
    void getAllFwJywpsByJywpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where jywp not equals to DEFAULT_JYWP
        defaultFwJywpShouldNotBeFound("jywp.notEquals=" + DEFAULT_JYWP);

        // Get all the fwJywpList where jywp not equals to UPDATED_JYWP
        defaultFwJywpShouldBeFound("jywp.notEquals=" + UPDATED_JYWP);
    }

    @Test
    @Transactional
    void getAllFwJywpsByJywpIsInShouldWork() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where jywp in DEFAULT_JYWP or UPDATED_JYWP
        defaultFwJywpShouldBeFound("jywp.in=" + DEFAULT_JYWP + "," + UPDATED_JYWP);

        // Get all the fwJywpList where jywp equals to UPDATED_JYWP
        defaultFwJywpShouldNotBeFound("jywp.in=" + UPDATED_JYWP);
    }

    @Test
    @Transactional
    void getAllFwJywpsByJywpIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where jywp is not null
        defaultFwJywpShouldBeFound("jywp.specified=true");

        // Get all the fwJywpList where jywp is null
        defaultFwJywpShouldNotBeFound("jywp.specified=false");
    }

    @Test
    @Transactional
    void getAllFwJywpsByJywpContainsSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where jywp contains DEFAULT_JYWP
        defaultFwJywpShouldBeFound("jywp.contains=" + DEFAULT_JYWP);

        // Get all the fwJywpList where jywp contains UPDATED_JYWP
        defaultFwJywpShouldNotBeFound("jywp.contains=" + UPDATED_JYWP);
    }

    @Test
    @Transactional
    void getAllFwJywpsByJywpNotContainsSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where jywp does not contain DEFAULT_JYWP
        defaultFwJywpShouldNotBeFound("jywp.doesNotContain=" + DEFAULT_JYWP);

        // Get all the fwJywpList where jywp does not contain UPDATED_JYWP
        defaultFwJywpShouldBeFound("jywp.doesNotContain=" + UPDATED_JYWP);
    }

    @Test
    @Transactional
    void getAllFwJywpsByFwyIsEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where fwy equals to DEFAULT_FWY
        defaultFwJywpShouldBeFound("fwy.equals=" + DEFAULT_FWY);

        // Get all the fwJywpList where fwy equals to UPDATED_FWY
        defaultFwJywpShouldNotBeFound("fwy.equals=" + UPDATED_FWY);
    }

    @Test
    @Transactional
    void getAllFwJywpsByFwyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where fwy not equals to DEFAULT_FWY
        defaultFwJywpShouldNotBeFound("fwy.notEquals=" + DEFAULT_FWY);

        // Get all the fwJywpList where fwy not equals to UPDATED_FWY
        defaultFwJywpShouldBeFound("fwy.notEquals=" + UPDATED_FWY);
    }

    @Test
    @Transactional
    void getAllFwJywpsByFwyIsInShouldWork() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where fwy in DEFAULT_FWY or UPDATED_FWY
        defaultFwJywpShouldBeFound("fwy.in=" + DEFAULT_FWY + "," + UPDATED_FWY);

        // Get all the fwJywpList where fwy equals to UPDATED_FWY
        defaultFwJywpShouldNotBeFound("fwy.in=" + UPDATED_FWY);
    }

    @Test
    @Transactional
    void getAllFwJywpsByFwyIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where fwy is not null
        defaultFwJywpShouldBeFound("fwy.specified=true");

        // Get all the fwJywpList where fwy is null
        defaultFwJywpShouldNotBeFound("fwy.specified=false");
    }

    @Test
    @Transactional
    void getAllFwJywpsByFwyContainsSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where fwy contains DEFAULT_FWY
        defaultFwJywpShouldBeFound("fwy.contains=" + DEFAULT_FWY);

        // Get all the fwJywpList where fwy contains UPDATED_FWY
        defaultFwJywpShouldNotBeFound("fwy.contains=" + UPDATED_FWY);
    }

    @Test
    @Transactional
    void getAllFwJywpsByFwyNotContainsSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where fwy does not contain DEFAULT_FWY
        defaultFwJywpShouldNotBeFound("fwy.doesNotContain=" + DEFAULT_FWY);

        // Get all the fwJywpList where fwy does not contain UPDATED_FWY
        defaultFwJywpShouldBeFound("fwy.doesNotContain=" + UPDATED_FWY);
    }

    @Test
    @Transactional
    void getAllFwJywpsByDjrIsEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where djr equals to DEFAULT_DJR
        defaultFwJywpShouldBeFound("djr.equals=" + DEFAULT_DJR);

        // Get all the fwJywpList where djr equals to UPDATED_DJR
        defaultFwJywpShouldNotBeFound("djr.equals=" + UPDATED_DJR);
    }

    @Test
    @Transactional
    void getAllFwJywpsByDjrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where djr not equals to DEFAULT_DJR
        defaultFwJywpShouldNotBeFound("djr.notEquals=" + DEFAULT_DJR);

        // Get all the fwJywpList where djr not equals to UPDATED_DJR
        defaultFwJywpShouldBeFound("djr.notEquals=" + UPDATED_DJR);
    }

    @Test
    @Transactional
    void getAllFwJywpsByDjrIsInShouldWork() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where djr in DEFAULT_DJR or UPDATED_DJR
        defaultFwJywpShouldBeFound("djr.in=" + DEFAULT_DJR + "," + UPDATED_DJR);

        // Get all the fwJywpList where djr equals to UPDATED_DJR
        defaultFwJywpShouldNotBeFound("djr.in=" + UPDATED_DJR);
    }

    @Test
    @Transactional
    void getAllFwJywpsByDjrIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where djr is not null
        defaultFwJywpShouldBeFound("djr.specified=true");

        // Get all the fwJywpList where djr is null
        defaultFwJywpShouldNotBeFound("djr.specified=false");
    }

    @Test
    @Transactional
    void getAllFwJywpsByDjrContainsSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where djr contains DEFAULT_DJR
        defaultFwJywpShouldBeFound("djr.contains=" + DEFAULT_DJR);

        // Get all the fwJywpList where djr contains UPDATED_DJR
        defaultFwJywpShouldNotBeFound("djr.contains=" + UPDATED_DJR);
    }

    @Test
    @Transactional
    void getAllFwJywpsByDjrNotContainsSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where djr does not contain DEFAULT_DJR
        defaultFwJywpShouldNotBeFound("djr.doesNotContain=" + DEFAULT_DJR);

        // Get all the fwJywpList where djr does not contain UPDATED_DJR
        defaultFwJywpShouldBeFound("djr.doesNotContain=" + UPDATED_DJR);
    }

    @Test
    @Transactional
    void getAllFwJywpsByFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where flag equals to DEFAULT_FLAG
        defaultFwJywpShouldBeFound("flag.equals=" + DEFAULT_FLAG);

        // Get all the fwJywpList where flag equals to UPDATED_FLAG
        defaultFwJywpShouldNotBeFound("flag.equals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllFwJywpsByFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where flag not equals to DEFAULT_FLAG
        defaultFwJywpShouldNotBeFound("flag.notEquals=" + DEFAULT_FLAG);

        // Get all the fwJywpList where flag not equals to UPDATED_FLAG
        defaultFwJywpShouldBeFound("flag.notEquals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllFwJywpsByFlagIsInShouldWork() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where flag in DEFAULT_FLAG or UPDATED_FLAG
        defaultFwJywpShouldBeFound("flag.in=" + DEFAULT_FLAG + "," + UPDATED_FLAG);

        // Get all the fwJywpList where flag equals to UPDATED_FLAG
        defaultFwJywpShouldNotBeFound("flag.in=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllFwJywpsByFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where flag is not null
        defaultFwJywpShouldBeFound("flag.specified=true");

        // Get all the fwJywpList where flag is null
        defaultFwJywpShouldNotBeFound("flag.specified=false");
    }

    @Test
    @Transactional
    void getAllFwJywpsByFlagContainsSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where flag contains DEFAULT_FLAG
        defaultFwJywpShouldBeFound("flag.contains=" + DEFAULT_FLAG);

        // Get all the fwJywpList where flag contains UPDATED_FLAG
        defaultFwJywpShouldNotBeFound("flag.contains=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllFwJywpsByFlagNotContainsSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where flag does not contain DEFAULT_FLAG
        defaultFwJywpShouldNotBeFound("flag.doesNotContain=" + DEFAULT_FLAG);

        // Get all the fwJywpList where flag does not contain UPDATED_FLAG
        defaultFwJywpShouldBeFound("flag.doesNotContain=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllFwJywpsByGhrqIsEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where ghrq equals to DEFAULT_GHRQ
        defaultFwJywpShouldBeFound("ghrq.equals=" + DEFAULT_GHRQ);

        // Get all the fwJywpList where ghrq equals to UPDATED_GHRQ
        defaultFwJywpShouldNotBeFound("ghrq.equals=" + UPDATED_GHRQ);
    }

    @Test
    @Transactional
    void getAllFwJywpsByGhrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where ghrq not equals to DEFAULT_GHRQ
        defaultFwJywpShouldNotBeFound("ghrq.notEquals=" + DEFAULT_GHRQ);

        // Get all the fwJywpList where ghrq not equals to UPDATED_GHRQ
        defaultFwJywpShouldBeFound("ghrq.notEquals=" + UPDATED_GHRQ);
    }

    @Test
    @Transactional
    void getAllFwJywpsByGhrqIsInShouldWork() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where ghrq in DEFAULT_GHRQ or UPDATED_GHRQ
        defaultFwJywpShouldBeFound("ghrq.in=" + DEFAULT_GHRQ + "," + UPDATED_GHRQ);

        // Get all the fwJywpList where ghrq equals to UPDATED_GHRQ
        defaultFwJywpShouldNotBeFound("ghrq.in=" + UPDATED_GHRQ);
    }

    @Test
    @Transactional
    void getAllFwJywpsByGhrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where ghrq is not null
        defaultFwJywpShouldBeFound("ghrq.specified=true");

        // Get all the fwJywpList where ghrq is null
        defaultFwJywpShouldNotBeFound("ghrq.specified=false");
    }

    @Test
    @Transactional
    void getAllFwJywpsByDjrqIsEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where djrq equals to DEFAULT_DJRQ
        defaultFwJywpShouldBeFound("djrq.equals=" + DEFAULT_DJRQ);

        // Get all the fwJywpList where djrq equals to UPDATED_DJRQ
        defaultFwJywpShouldNotBeFound("djrq.equals=" + UPDATED_DJRQ);
    }

    @Test
    @Transactional
    void getAllFwJywpsByDjrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where djrq not equals to DEFAULT_DJRQ
        defaultFwJywpShouldNotBeFound("djrq.notEquals=" + DEFAULT_DJRQ);

        // Get all the fwJywpList where djrq not equals to UPDATED_DJRQ
        defaultFwJywpShouldBeFound("djrq.notEquals=" + UPDATED_DJRQ);
    }

    @Test
    @Transactional
    void getAllFwJywpsByDjrqIsInShouldWork() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where djrq in DEFAULT_DJRQ or UPDATED_DJRQ
        defaultFwJywpShouldBeFound("djrq.in=" + DEFAULT_DJRQ + "," + UPDATED_DJRQ);

        // Get all the fwJywpList where djrq equals to UPDATED_DJRQ
        defaultFwJywpShouldNotBeFound("djrq.in=" + UPDATED_DJRQ);
    }

    @Test
    @Transactional
    void getAllFwJywpsByDjrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where djrq is not null
        defaultFwJywpShouldBeFound("djrq.specified=true");

        // Get all the fwJywpList where djrq is null
        defaultFwJywpShouldNotBeFound("djrq.specified=false");
    }

    @Test
    @Transactional
    void getAllFwJywpsByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where remark equals to DEFAULT_REMARK
        defaultFwJywpShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the fwJywpList where remark equals to UPDATED_REMARK
        defaultFwJywpShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllFwJywpsByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where remark not equals to DEFAULT_REMARK
        defaultFwJywpShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the fwJywpList where remark not equals to UPDATED_REMARK
        defaultFwJywpShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllFwJywpsByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultFwJywpShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the fwJywpList where remark equals to UPDATED_REMARK
        defaultFwJywpShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllFwJywpsByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where remark is not null
        defaultFwJywpShouldBeFound("remark.specified=true");

        // Get all the fwJywpList where remark is null
        defaultFwJywpShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllFwJywpsByRemarkContainsSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where remark contains DEFAULT_REMARK
        defaultFwJywpShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the fwJywpList where remark contains UPDATED_REMARK
        defaultFwJywpShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllFwJywpsByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        // Get all the fwJywpList where remark does not contain DEFAULT_REMARK
        defaultFwJywpShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the fwJywpList where remark does not contain UPDATED_REMARK
        defaultFwJywpShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFwJywpShouldBeFound(String filter) throws Exception {
        restFwJywpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwJywp.getId().intValue())))
            .andExpect(jsonPath("$.[*].jyrq").value(hasItem(DEFAULT_JYRQ.toString())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].jywp").value(hasItem(DEFAULT_JYWP)))
            .andExpect(jsonPath("$.[*].fwy").value(hasItem(DEFAULT_FWY)))
            .andExpect(jsonPath("$.[*].djr").value(hasItem(DEFAULT_DJR)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG)))
            .andExpect(jsonPath("$.[*].ghrq").value(hasItem(DEFAULT_GHRQ.toString())))
            .andExpect(jsonPath("$.[*].djrq").value(hasItem(DEFAULT_DJRQ.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));

        // Check, that the count call also returns 1
        restFwJywpMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFwJywpShouldNotBeFound(String filter) throws Exception {
        restFwJywpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFwJywpMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFwJywp() throws Exception {
        // Get the fwJywp
        restFwJywpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFwJywp() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        int databaseSizeBeforeUpdate = fwJywpRepository.findAll().size();

        // Update the fwJywp
        FwJywp updatedFwJywp = fwJywpRepository.findById(fwJywp.getId()).get();
        // Disconnect from session so that the updates on updatedFwJywp are not directly saved in db
        em.detach(updatedFwJywp);
        updatedFwJywp
            .jyrq(UPDATED_JYRQ)
            .roomn(UPDATED_ROOMN)
            .guestname(UPDATED_GUESTNAME)
            .jywp(UPDATED_JYWP)
            .fwy(UPDATED_FWY)
            .djr(UPDATED_DJR)
            .flag(UPDATED_FLAG)
            .ghrq(UPDATED_GHRQ)
            .djrq(UPDATED_DJRQ)
            .remark(UPDATED_REMARK);
        FwJywpDTO fwJywpDTO = fwJywpMapper.toDto(updatedFwJywp);

        restFwJywpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fwJywpDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwJywpDTO))
            )
            .andExpect(status().isOk());

        // Validate the FwJywp in the database
        List<FwJywp> fwJywpList = fwJywpRepository.findAll();
        assertThat(fwJywpList).hasSize(databaseSizeBeforeUpdate);
        FwJywp testFwJywp = fwJywpList.get(fwJywpList.size() - 1);
        assertThat(testFwJywp.getJyrq()).isEqualTo(UPDATED_JYRQ);
        assertThat(testFwJywp.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testFwJywp.getGuestname()).isEqualTo(UPDATED_GUESTNAME);
        assertThat(testFwJywp.getJywp()).isEqualTo(UPDATED_JYWP);
        assertThat(testFwJywp.getFwy()).isEqualTo(UPDATED_FWY);
        assertThat(testFwJywp.getDjr()).isEqualTo(UPDATED_DJR);
        assertThat(testFwJywp.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testFwJywp.getGhrq()).isEqualTo(UPDATED_GHRQ);
        assertThat(testFwJywp.getDjrq()).isEqualTo(UPDATED_DJRQ);
        assertThat(testFwJywp.getRemark()).isEqualTo(UPDATED_REMARK);

        // Validate the FwJywp in Elasticsearch
        verify(mockFwJywpSearchRepository).save(testFwJywp);
    }

    @Test
    @Transactional
    void putNonExistingFwJywp() throws Exception {
        int databaseSizeBeforeUpdate = fwJywpRepository.findAll().size();
        fwJywp.setId(count.incrementAndGet());

        // Create the FwJywp
        FwJywpDTO fwJywpDTO = fwJywpMapper.toDto(fwJywp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFwJywpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fwJywpDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwJywpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwJywp in the database
        List<FwJywp> fwJywpList = fwJywpRepository.findAll();
        assertThat(fwJywpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwJywp in Elasticsearch
        verify(mockFwJywpSearchRepository, times(0)).save(fwJywp);
    }

    @Test
    @Transactional
    void putWithIdMismatchFwJywp() throws Exception {
        int databaseSizeBeforeUpdate = fwJywpRepository.findAll().size();
        fwJywp.setId(count.incrementAndGet());

        // Create the FwJywp
        FwJywpDTO fwJywpDTO = fwJywpMapper.toDto(fwJywp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwJywpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fwJywpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwJywp in the database
        List<FwJywp> fwJywpList = fwJywpRepository.findAll();
        assertThat(fwJywpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwJywp in Elasticsearch
        verify(mockFwJywpSearchRepository, times(0)).save(fwJywp);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFwJywp() throws Exception {
        int databaseSizeBeforeUpdate = fwJywpRepository.findAll().size();
        fwJywp.setId(count.incrementAndGet());

        // Create the FwJywp
        FwJywpDTO fwJywpDTO = fwJywpMapper.toDto(fwJywp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwJywpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fwJywpDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FwJywp in the database
        List<FwJywp> fwJywpList = fwJywpRepository.findAll();
        assertThat(fwJywpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwJywp in Elasticsearch
        verify(mockFwJywpSearchRepository, times(0)).save(fwJywp);
    }

    @Test
    @Transactional
    void partialUpdateFwJywpWithPatch() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        int databaseSizeBeforeUpdate = fwJywpRepository.findAll().size();

        // Update the fwJywp using partial update
        FwJywp partialUpdatedFwJywp = new FwJywp();
        partialUpdatedFwJywp.setId(fwJywp.getId());

        partialUpdatedFwJywp
            .jyrq(UPDATED_JYRQ)
            .roomn(UPDATED_ROOMN)
            .fwy(UPDATED_FWY)
            .djr(UPDATED_DJR)
            .flag(UPDATED_FLAG)
            .djrq(UPDATED_DJRQ);

        restFwJywpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFwJywp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFwJywp))
            )
            .andExpect(status().isOk());

        // Validate the FwJywp in the database
        List<FwJywp> fwJywpList = fwJywpRepository.findAll();
        assertThat(fwJywpList).hasSize(databaseSizeBeforeUpdate);
        FwJywp testFwJywp = fwJywpList.get(fwJywpList.size() - 1);
        assertThat(testFwJywp.getJyrq()).isEqualTo(UPDATED_JYRQ);
        assertThat(testFwJywp.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testFwJywp.getGuestname()).isEqualTo(DEFAULT_GUESTNAME);
        assertThat(testFwJywp.getJywp()).isEqualTo(DEFAULT_JYWP);
        assertThat(testFwJywp.getFwy()).isEqualTo(UPDATED_FWY);
        assertThat(testFwJywp.getDjr()).isEqualTo(UPDATED_DJR);
        assertThat(testFwJywp.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testFwJywp.getGhrq()).isEqualTo(DEFAULT_GHRQ);
        assertThat(testFwJywp.getDjrq()).isEqualTo(UPDATED_DJRQ);
        assertThat(testFwJywp.getRemark()).isEqualTo(DEFAULT_REMARK);
    }

    @Test
    @Transactional
    void fullUpdateFwJywpWithPatch() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        int databaseSizeBeforeUpdate = fwJywpRepository.findAll().size();

        // Update the fwJywp using partial update
        FwJywp partialUpdatedFwJywp = new FwJywp();
        partialUpdatedFwJywp.setId(fwJywp.getId());

        partialUpdatedFwJywp
            .jyrq(UPDATED_JYRQ)
            .roomn(UPDATED_ROOMN)
            .guestname(UPDATED_GUESTNAME)
            .jywp(UPDATED_JYWP)
            .fwy(UPDATED_FWY)
            .djr(UPDATED_DJR)
            .flag(UPDATED_FLAG)
            .ghrq(UPDATED_GHRQ)
            .djrq(UPDATED_DJRQ)
            .remark(UPDATED_REMARK);

        restFwJywpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFwJywp.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFwJywp))
            )
            .andExpect(status().isOk());

        // Validate the FwJywp in the database
        List<FwJywp> fwJywpList = fwJywpRepository.findAll();
        assertThat(fwJywpList).hasSize(databaseSizeBeforeUpdate);
        FwJywp testFwJywp = fwJywpList.get(fwJywpList.size() - 1);
        assertThat(testFwJywp.getJyrq()).isEqualTo(UPDATED_JYRQ);
        assertThat(testFwJywp.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testFwJywp.getGuestname()).isEqualTo(UPDATED_GUESTNAME);
        assertThat(testFwJywp.getJywp()).isEqualTo(UPDATED_JYWP);
        assertThat(testFwJywp.getFwy()).isEqualTo(UPDATED_FWY);
        assertThat(testFwJywp.getDjr()).isEqualTo(UPDATED_DJR);
        assertThat(testFwJywp.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testFwJywp.getGhrq()).isEqualTo(UPDATED_GHRQ);
        assertThat(testFwJywp.getDjrq()).isEqualTo(UPDATED_DJRQ);
        assertThat(testFwJywp.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    void patchNonExistingFwJywp() throws Exception {
        int databaseSizeBeforeUpdate = fwJywpRepository.findAll().size();
        fwJywp.setId(count.incrementAndGet());

        // Create the FwJywp
        FwJywpDTO fwJywpDTO = fwJywpMapper.toDto(fwJywp);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFwJywpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fwJywpDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fwJywpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwJywp in the database
        List<FwJywp> fwJywpList = fwJywpRepository.findAll();
        assertThat(fwJywpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwJywp in Elasticsearch
        verify(mockFwJywpSearchRepository, times(0)).save(fwJywp);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFwJywp() throws Exception {
        int databaseSizeBeforeUpdate = fwJywpRepository.findAll().size();
        fwJywp.setId(count.incrementAndGet());

        // Create the FwJywp
        FwJywpDTO fwJywpDTO = fwJywpMapper.toDto(fwJywp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwJywpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fwJywpDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FwJywp in the database
        List<FwJywp> fwJywpList = fwJywpRepository.findAll();
        assertThat(fwJywpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwJywp in Elasticsearch
        verify(mockFwJywpSearchRepository, times(0)).save(fwJywp);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFwJywp() throws Exception {
        int databaseSizeBeforeUpdate = fwJywpRepository.findAll().size();
        fwJywp.setId(count.incrementAndGet());

        // Create the FwJywp
        FwJywpDTO fwJywpDTO = fwJywpMapper.toDto(fwJywp);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFwJywpMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fwJywpDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FwJywp in the database
        List<FwJywp> fwJywpList = fwJywpRepository.findAll();
        assertThat(fwJywpList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FwJywp in Elasticsearch
        verify(mockFwJywpSearchRepository, times(0)).save(fwJywp);
    }

    @Test
    @Transactional
    void deleteFwJywp() throws Exception {
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);

        int databaseSizeBeforeDelete = fwJywpRepository.findAll().size();

        // Delete the fwJywp
        restFwJywpMockMvc
            .perform(delete(ENTITY_API_URL_ID, fwJywp.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FwJywp> fwJywpList = fwJywpRepository.findAll();
        assertThat(fwJywpList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FwJywp in Elasticsearch
        verify(mockFwJywpSearchRepository, times(1)).deleteById(fwJywp.getId());
    }

    @Test
    @Transactional
    void searchFwJywp() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        fwJywpRepository.saveAndFlush(fwJywp);
        when(mockFwJywpSearchRepository.search(queryStringQuery("id:" + fwJywp.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fwJywp), PageRequest.of(0, 1), 1));

        // Search the fwJywp
        restFwJywpMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + fwJywp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fwJywp.getId().intValue())))
            .andExpect(jsonPath("$.[*].jyrq").value(hasItem(DEFAULT_JYRQ.toString())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].jywp").value(hasItem(DEFAULT_JYWP)))
            .andExpect(jsonPath("$.[*].fwy").value(hasItem(DEFAULT_FWY)))
            .andExpect(jsonPath("$.[*].djr").value(hasItem(DEFAULT_DJR)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG)))
            .andExpect(jsonPath("$.[*].ghrq").value(hasItem(DEFAULT_GHRQ.toString())))
            .andExpect(jsonPath("$.[*].djrq").value(hasItem(DEFAULT_DJRQ.toString())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));
    }
}
