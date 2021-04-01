package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.CheckinTz;
import ihotel.app.repository.CheckinTzRepository;
import ihotel.app.repository.search.CheckinTzSearchRepository;
import ihotel.app.service.criteria.CheckinTzCriteria;
import ihotel.app.service.dto.CheckinTzDTO;
import ihotel.app.service.mapper.CheckinTzMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link CheckinTzResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CheckinTzResourceIT {

    private static final Long DEFAULT_GUEST_ID = 1L;
    private static final Long UPDATED_GUEST_ID = 2L;
    private static final Long SMALLER_GUEST_ID = 1L - 1L;

    private static final String DEFAULT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT = "BBBBBBBBBB";

    private static final Instant DEFAULT_HOTELTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOTELTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_INDATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INDATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_RESIDEFATE = 1L;
    private static final Long UPDATED_RESIDEFATE = 2L;
    private static final Long SMALLER_RESIDEFATE = 1L - 1L;

    private static final Instant DEFAULT_GOTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GOTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMN = "AAAAAAAAAA";
    private static final String UPDATED_ROOMN = "BBBBBBBBBB";

    private static final String DEFAULT_RENTP = "AAAAAAAAAA";
    private static final String UPDATED_RENTP = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PROTOCOLRENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROTOCOLRENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_PROTOCOLRENT = new BigDecimal(1 - 1);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_PHONEN = "AAAAAAAAAA";
    private static final String UPDATED_PHONEN = "BBBBBBBBBB";

    private static final String DEFAULT_EMPN_2 = "AAAAAAAAAA";
    private static final String UPDATED_EMPN_2 = "BBBBBBBBBB";

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final String DEFAULT_LF_SIGN = "A";
    private static final String UPDATED_LF_SIGN = "B";

    private static final String DEFAULT_GUESTNAME = "AAAAAAAAAA";
    private static final String UPDATED_GUESTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_BC = "AAAAAAAAAA";
    private static final String UPDATED_BC = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMTYPE = "AAAAAAAAAA";
    private static final String UPDATED_ROOMTYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/checkin-tzs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/checkin-tzs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CheckinTzRepository checkinTzRepository;

    @Autowired
    private CheckinTzMapper checkinTzMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CheckinTzSearchRepositoryMockConfiguration
     */
    @Autowired
    private CheckinTzSearchRepository mockCheckinTzSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckinTzMockMvc;

    private CheckinTz checkinTz;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckinTz createEntity(EntityManager em) {
        CheckinTz checkinTz = new CheckinTz()
            .guestId(DEFAULT_GUEST_ID)
            .account(DEFAULT_ACCOUNT)
            .hoteltime(DEFAULT_HOTELTIME)
            .indatetime(DEFAULT_INDATETIME)
            .residefate(DEFAULT_RESIDEFATE)
            .gotime(DEFAULT_GOTIME)
            .empn(DEFAULT_EMPN)
            .roomn(DEFAULT_ROOMN)
            .rentp(DEFAULT_RENTP)
            .protocolrent(DEFAULT_PROTOCOLRENT)
            .remark(DEFAULT_REMARK)
            .phonen(DEFAULT_PHONEN)
            .empn2(DEFAULT_EMPN_2)
            .memo(DEFAULT_MEMO)
            .lfSign(DEFAULT_LF_SIGN)
            .guestname(DEFAULT_GUESTNAME)
            .bc(DEFAULT_BC)
            .roomtype(DEFAULT_ROOMTYPE);
        return checkinTz;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckinTz createUpdatedEntity(EntityManager em) {
        CheckinTz checkinTz = new CheckinTz()
            .guestId(UPDATED_GUEST_ID)
            .account(UPDATED_ACCOUNT)
            .hoteltime(UPDATED_HOTELTIME)
            .indatetime(UPDATED_INDATETIME)
            .residefate(UPDATED_RESIDEFATE)
            .gotime(UPDATED_GOTIME)
            .empn(UPDATED_EMPN)
            .roomn(UPDATED_ROOMN)
            .rentp(UPDATED_RENTP)
            .protocolrent(UPDATED_PROTOCOLRENT)
            .remark(UPDATED_REMARK)
            .phonen(UPDATED_PHONEN)
            .empn2(UPDATED_EMPN_2)
            .memo(UPDATED_MEMO)
            .lfSign(UPDATED_LF_SIGN)
            .guestname(UPDATED_GUESTNAME)
            .bc(UPDATED_BC)
            .roomtype(UPDATED_ROOMTYPE);
        return checkinTz;
    }

    @BeforeEach
    public void initTest() {
        checkinTz = createEntity(em);
    }

    @Test
    @Transactional
    void createCheckinTz() throws Exception {
        int databaseSizeBeforeCreate = checkinTzRepository.findAll().size();
        // Create the CheckinTz
        CheckinTzDTO checkinTzDTO = checkinTzMapper.toDto(checkinTz);
        restCheckinTzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinTzDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckinTz in the database
        List<CheckinTz> checkinTzList = checkinTzRepository.findAll();
        assertThat(checkinTzList).hasSize(databaseSizeBeforeCreate + 1);
        CheckinTz testCheckinTz = checkinTzList.get(checkinTzList.size() - 1);
        assertThat(testCheckinTz.getGuestId()).isEqualTo(DEFAULT_GUEST_ID);
        assertThat(testCheckinTz.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testCheckinTz.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testCheckinTz.getIndatetime()).isEqualTo(DEFAULT_INDATETIME);
        assertThat(testCheckinTz.getResidefate()).isEqualTo(DEFAULT_RESIDEFATE);
        assertThat(testCheckinTz.getGotime()).isEqualTo(DEFAULT_GOTIME);
        assertThat(testCheckinTz.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testCheckinTz.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testCheckinTz.getRentp()).isEqualTo(DEFAULT_RENTP);
        assertThat(testCheckinTz.getProtocolrent()).isEqualByComparingTo(DEFAULT_PROTOCOLRENT);
        assertThat(testCheckinTz.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testCheckinTz.getPhonen()).isEqualTo(DEFAULT_PHONEN);
        assertThat(testCheckinTz.getEmpn2()).isEqualTo(DEFAULT_EMPN_2);
        assertThat(testCheckinTz.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testCheckinTz.getLfSign()).isEqualTo(DEFAULT_LF_SIGN);
        assertThat(testCheckinTz.getGuestname()).isEqualTo(DEFAULT_GUESTNAME);
        assertThat(testCheckinTz.getBc()).isEqualTo(DEFAULT_BC);
        assertThat(testCheckinTz.getRoomtype()).isEqualTo(DEFAULT_ROOMTYPE);

        // Validate the CheckinTz in Elasticsearch
        verify(mockCheckinTzSearchRepository, times(1)).save(testCheckinTz);
    }

    @Test
    @Transactional
    void createCheckinTzWithExistingId() throws Exception {
        // Create the CheckinTz with an existing ID
        checkinTz.setId(1L);
        CheckinTzDTO checkinTzDTO = checkinTzMapper.toDto(checkinTz);

        int databaseSizeBeforeCreate = checkinTzRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckinTzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinTzDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckinTz in the database
        List<CheckinTz> checkinTzList = checkinTzRepository.findAll();
        assertThat(checkinTzList).hasSize(databaseSizeBeforeCreate);

        // Validate the CheckinTz in Elasticsearch
        verify(mockCheckinTzSearchRepository, times(0)).save(checkinTz);
    }

    @Test
    @Transactional
    void checkRoomnIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkinTzRepository.findAll().size();
        // set the field null
        checkinTz.setRoomn(null);

        // Create the CheckinTz, which fails.
        CheckinTzDTO checkinTzDTO = checkinTzMapper.toDto(checkinTz);

        restCheckinTzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinTzDTO)))
            .andExpect(status().isBadRequest());

        List<CheckinTz> checkinTzList = checkinTzRepository.findAll();
        assertThat(checkinTzList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRentpIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkinTzRepository.findAll().size();
        // set the field null
        checkinTz.setRentp(null);

        // Create the CheckinTz, which fails.
        CheckinTzDTO checkinTzDTO = checkinTzMapper.toDto(checkinTz);

        restCheckinTzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinTzDTO)))
            .andExpect(status().isBadRequest());

        List<CheckinTz> checkinTzList = checkinTzRepository.findAll();
        assertThat(checkinTzList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCheckinTzs() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList
        restCheckinTzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkinTz.getId().intValue())))
            .andExpect(jsonPath("$.[*].guestId").value(hasItem(DEFAULT_GUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].indatetime").value(hasItem(DEFAULT_INDATETIME.toString())))
            .andExpect(jsonPath("$.[*].residefate").value(hasItem(DEFAULT_RESIDEFATE.intValue())))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].rentp").value(hasItem(DEFAULT_RENTP)))
            .andExpect(jsonPath("$.[*].protocolrent").value(hasItem(sameNumber(DEFAULT_PROTOCOLRENT))))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].phonen").value(hasItem(DEFAULT_PHONEN)))
            .andExpect(jsonPath("$.[*].empn2").value(hasItem(DEFAULT_EMPN_2)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].lfSign").value(hasItem(DEFAULT_LF_SIGN)))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].bc").value(hasItem(DEFAULT_BC)))
            .andExpect(jsonPath("$.[*].roomtype").value(hasItem(DEFAULT_ROOMTYPE)));
    }

    @Test
    @Transactional
    void getCheckinTz() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get the checkinTz
        restCheckinTzMockMvc
            .perform(get(ENTITY_API_URL_ID, checkinTz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkinTz.getId().intValue()))
            .andExpect(jsonPath("$.guestId").value(DEFAULT_GUEST_ID.intValue()))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT))
            .andExpect(jsonPath("$.hoteltime").value(DEFAULT_HOTELTIME.toString()))
            .andExpect(jsonPath("$.indatetime").value(DEFAULT_INDATETIME.toString()))
            .andExpect(jsonPath("$.residefate").value(DEFAULT_RESIDEFATE.intValue()))
            .andExpect(jsonPath("$.gotime").value(DEFAULT_GOTIME.toString()))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.roomn").value(DEFAULT_ROOMN))
            .andExpect(jsonPath("$.rentp").value(DEFAULT_RENTP))
            .andExpect(jsonPath("$.protocolrent").value(sameNumber(DEFAULT_PROTOCOLRENT)))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.phonen").value(DEFAULT_PHONEN))
            .andExpect(jsonPath("$.empn2").value(DEFAULT_EMPN_2))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO))
            .andExpect(jsonPath("$.lfSign").value(DEFAULT_LF_SIGN))
            .andExpect(jsonPath("$.guestname").value(DEFAULT_GUESTNAME))
            .andExpect(jsonPath("$.bc").value(DEFAULT_BC))
            .andExpect(jsonPath("$.roomtype").value(DEFAULT_ROOMTYPE));
    }

    @Test
    @Transactional
    void getCheckinTzsByIdFiltering() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        Long id = checkinTz.getId();

        defaultCheckinTzShouldBeFound("id.equals=" + id);
        defaultCheckinTzShouldNotBeFound("id.notEquals=" + id);

        defaultCheckinTzShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCheckinTzShouldNotBeFound("id.greaterThan=" + id);

        defaultCheckinTzShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCheckinTzShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGuestIdIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where guestId equals to DEFAULT_GUEST_ID
        defaultCheckinTzShouldBeFound("guestId.equals=" + DEFAULT_GUEST_ID);

        // Get all the checkinTzList where guestId equals to UPDATED_GUEST_ID
        defaultCheckinTzShouldNotBeFound("guestId.equals=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGuestIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where guestId not equals to DEFAULT_GUEST_ID
        defaultCheckinTzShouldNotBeFound("guestId.notEquals=" + DEFAULT_GUEST_ID);

        // Get all the checkinTzList where guestId not equals to UPDATED_GUEST_ID
        defaultCheckinTzShouldBeFound("guestId.notEquals=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGuestIdIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where guestId in DEFAULT_GUEST_ID or UPDATED_GUEST_ID
        defaultCheckinTzShouldBeFound("guestId.in=" + DEFAULT_GUEST_ID + "," + UPDATED_GUEST_ID);

        // Get all the checkinTzList where guestId equals to UPDATED_GUEST_ID
        defaultCheckinTzShouldNotBeFound("guestId.in=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGuestIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where guestId is not null
        defaultCheckinTzShouldBeFound("guestId.specified=true");

        // Get all the checkinTzList where guestId is null
        defaultCheckinTzShouldNotBeFound("guestId.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGuestIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where guestId is greater than or equal to DEFAULT_GUEST_ID
        defaultCheckinTzShouldBeFound("guestId.greaterThanOrEqual=" + DEFAULT_GUEST_ID);

        // Get all the checkinTzList where guestId is greater than or equal to UPDATED_GUEST_ID
        defaultCheckinTzShouldNotBeFound("guestId.greaterThanOrEqual=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGuestIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where guestId is less than or equal to DEFAULT_GUEST_ID
        defaultCheckinTzShouldBeFound("guestId.lessThanOrEqual=" + DEFAULT_GUEST_ID);

        // Get all the checkinTzList where guestId is less than or equal to SMALLER_GUEST_ID
        defaultCheckinTzShouldNotBeFound("guestId.lessThanOrEqual=" + SMALLER_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGuestIdIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where guestId is less than DEFAULT_GUEST_ID
        defaultCheckinTzShouldNotBeFound("guestId.lessThan=" + DEFAULT_GUEST_ID);

        // Get all the checkinTzList where guestId is less than UPDATED_GUEST_ID
        defaultCheckinTzShouldBeFound("guestId.lessThan=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGuestIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where guestId is greater than DEFAULT_GUEST_ID
        defaultCheckinTzShouldNotBeFound("guestId.greaterThan=" + DEFAULT_GUEST_ID);

        // Get all the checkinTzList where guestId is greater than SMALLER_GUEST_ID
        defaultCheckinTzShouldBeFound("guestId.greaterThan=" + SMALLER_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where account equals to DEFAULT_ACCOUNT
        defaultCheckinTzShouldBeFound("account.equals=" + DEFAULT_ACCOUNT);

        // Get all the checkinTzList where account equals to UPDATED_ACCOUNT
        defaultCheckinTzShouldNotBeFound("account.equals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where account not equals to DEFAULT_ACCOUNT
        defaultCheckinTzShouldNotBeFound("account.notEquals=" + DEFAULT_ACCOUNT);

        // Get all the checkinTzList where account not equals to UPDATED_ACCOUNT
        defaultCheckinTzShouldBeFound("account.notEquals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByAccountIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where account in DEFAULT_ACCOUNT or UPDATED_ACCOUNT
        defaultCheckinTzShouldBeFound("account.in=" + DEFAULT_ACCOUNT + "," + UPDATED_ACCOUNT);

        // Get all the checkinTzList where account equals to UPDATED_ACCOUNT
        defaultCheckinTzShouldNotBeFound("account.in=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where account is not null
        defaultCheckinTzShouldBeFound("account.specified=true");

        // Get all the checkinTzList where account is null
        defaultCheckinTzShouldNotBeFound("account.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByAccountContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where account contains DEFAULT_ACCOUNT
        defaultCheckinTzShouldBeFound("account.contains=" + DEFAULT_ACCOUNT);

        // Get all the checkinTzList where account contains UPDATED_ACCOUNT
        defaultCheckinTzShouldNotBeFound("account.contains=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByAccountNotContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where account does not contain DEFAULT_ACCOUNT
        defaultCheckinTzShouldNotBeFound("account.doesNotContain=" + DEFAULT_ACCOUNT);

        // Get all the checkinTzList where account does not contain UPDATED_ACCOUNT
        defaultCheckinTzShouldBeFound("account.doesNotContain=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByHoteltimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where hoteltime equals to DEFAULT_HOTELTIME
        defaultCheckinTzShouldBeFound("hoteltime.equals=" + DEFAULT_HOTELTIME);

        // Get all the checkinTzList where hoteltime equals to UPDATED_HOTELTIME
        defaultCheckinTzShouldNotBeFound("hoteltime.equals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByHoteltimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where hoteltime not equals to DEFAULT_HOTELTIME
        defaultCheckinTzShouldNotBeFound("hoteltime.notEquals=" + DEFAULT_HOTELTIME);

        // Get all the checkinTzList where hoteltime not equals to UPDATED_HOTELTIME
        defaultCheckinTzShouldBeFound("hoteltime.notEquals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByHoteltimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where hoteltime in DEFAULT_HOTELTIME or UPDATED_HOTELTIME
        defaultCheckinTzShouldBeFound("hoteltime.in=" + DEFAULT_HOTELTIME + "," + UPDATED_HOTELTIME);

        // Get all the checkinTzList where hoteltime equals to UPDATED_HOTELTIME
        defaultCheckinTzShouldNotBeFound("hoteltime.in=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByHoteltimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where hoteltime is not null
        defaultCheckinTzShouldBeFound("hoteltime.specified=true");

        // Get all the checkinTzList where hoteltime is null
        defaultCheckinTzShouldNotBeFound("hoteltime.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByIndatetimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where indatetime equals to DEFAULT_INDATETIME
        defaultCheckinTzShouldBeFound("indatetime.equals=" + DEFAULT_INDATETIME);

        // Get all the checkinTzList where indatetime equals to UPDATED_INDATETIME
        defaultCheckinTzShouldNotBeFound("indatetime.equals=" + UPDATED_INDATETIME);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByIndatetimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where indatetime not equals to DEFAULT_INDATETIME
        defaultCheckinTzShouldNotBeFound("indatetime.notEquals=" + DEFAULT_INDATETIME);

        // Get all the checkinTzList where indatetime not equals to UPDATED_INDATETIME
        defaultCheckinTzShouldBeFound("indatetime.notEquals=" + UPDATED_INDATETIME);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByIndatetimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where indatetime in DEFAULT_INDATETIME or UPDATED_INDATETIME
        defaultCheckinTzShouldBeFound("indatetime.in=" + DEFAULT_INDATETIME + "," + UPDATED_INDATETIME);

        // Get all the checkinTzList where indatetime equals to UPDATED_INDATETIME
        defaultCheckinTzShouldNotBeFound("indatetime.in=" + UPDATED_INDATETIME);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByIndatetimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where indatetime is not null
        defaultCheckinTzShouldBeFound("indatetime.specified=true");

        // Get all the checkinTzList where indatetime is null
        defaultCheckinTzShouldNotBeFound("indatetime.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByResidefateIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where residefate equals to DEFAULT_RESIDEFATE
        defaultCheckinTzShouldBeFound("residefate.equals=" + DEFAULT_RESIDEFATE);

        // Get all the checkinTzList where residefate equals to UPDATED_RESIDEFATE
        defaultCheckinTzShouldNotBeFound("residefate.equals=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByResidefateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where residefate not equals to DEFAULT_RESIDEFATE
        defaultCheckinTzShouldNotBeFound("residefate.notEquals=" + DEFAULT_RESIDEFATE);

        // Get all the checkinTzList where residefate not equals to UPDATED_RESIDEFATE
        defaultCheckinTzShouldBeFound("residefate.notEquals=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByResidefateIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where residefate in DEFAULT_RESIDEFATE or UPDATED_RESIDEFATE
        defaultCheckinTzShouldBeFound("residefate.in=" + DEFAULT_RESIDEFATE + "," + UPDATED_RESIDEFATE);

        // Get all the checkinTzList where residefate equals to UPDATED_RESIDEFATE
        defaultCheckinTzShouldNotBeFound("residefate.in=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByResidefateIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where residefate is not null
        defaultCheckinTzShouldBeFound("residefate.specified=true");

        // Get all the checkinTzList where residefate is null
        defaultCheckinTzShouldNotBeFound("residefate.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByResidefateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where residefate is greater than or equal to DEFAULT_RESIDEFATE
        defaultCheckinTzShouldBeFound("residefate.greaterThanOrEqual=" + DEFAULT_RESIDEFATE);

        // Get all the checkinTzList where residefate is greater than or equal to UPDATED_RESIDEFATE
        defaultCheckinTzShouldNotBeFound("residefate.greaterThanOrEqual=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByResidefateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where residefate is less than or equal to DEFAULT_RESIDEFATE
        defaultCheckinTzShouldBeFound("residefate.lessThanOrEqual=" + DEFAULT_RESIDEFATE);

        // Get all the checkinTzList where residefate is less than or equal to SMALLER_RESIDEFATE
        defaultCheckinTzShouldNotBeFound("residefate.lessThanOrEqual=" + SMALLER_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByResidefateIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where residefate is less than DEFAULT_RESIDEFATE
        defaultCheckinTzShouldNotBeFound("residefate.lessThan=" + DEFAULT_RESIDEFATE);

        // Get all the checkinTzList where residefate is less than UPDATED_RESIDEFATE
        defaultCheckinTzShouldBeFound("residefate.lessThan=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByResidefateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where residefate is greater than DEFAULT_RESIDEFATE
        defaultCheckinTzShouldNotBeFound("residefate.greaterThan=" + DEFAULT_RESIDEFATE);

        // Get all the checkinTzList where residefate is greater than SMALLER_RESIDEFATE
        defaultCheckinTzShouldBeFound("residefate.greaterThan=" + SMALLER_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGotimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where gotime equals to DEFAULT_GOTIME
        defaultCheckinTzShouldBeFound("gotime.equals=" + DEFAULT_GOTIME);

        // Get all the checkinTzList where gotime equals to UPDATED_GOTIME
        defaultCheckinTzShouldNotBeFound("gotime.equals=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGotimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where gotime not equals to DEFAULT_GOTIME
        defaultCheckinTzShouldNotBeFound("gotime.notEquals=" + DEFAULT_GOTIME);

        // Get all the checkinTzList where gotime not equals to UPDATED_GOTIME
        defaultCheckinTzShouldBeFound("gotime.notEquals=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGotimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where gotime in DEFAULT_GOTIME or UPDATED_GOTIME
        defaultCheckinTzShouldBeFound("gotime.in=" + DEFAULT_GOTIME + "," + UPDATED_GOTIME);

        // Get all the checkinTzList where gotime equals to UPDATED_GOTIME
        defaultCheckinTzShouldNotBeFound("gotime.in=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGotimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where gotime is not null
        defaultCheckinTzShouldBeFound("gotime.specified=true");

        // Get all the checkinTzList where gotime is null
        defaultCheckinTzShouldNotBeFound("gotime.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where empn equals to DEFAULT_EMPN
        defaultCheckinTzShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the checkinTzList where empn equals to UPDATED_EMPN
        defaultCheckinTzShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where empn not equals to DEFAULT_EMPN
        defaultCheckinTzShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the checkinTzList where empn not equals to UPDATED_EMPN
        defaultCheckinTzShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultCheckinTzShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the checkinTzList where empn equals to UPDATED_EMPN
        defaultCheckinTzShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where empn is not null
        defaultCheckinTzShouldBeFound("empn.specified=true");

        // Get all the checkinTzList where empn is null
        defaultCheckinTzShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByEmpnContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where empn contains DEFAULT_EMPN
        defaultCheckinTzShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the checkinTzList where empn contains UPDATED_EMPN
        defaultCheckinTzShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where empn does not contain DEFAULT_EMPN
        defaultCheckinTzShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the checkinTzList where empn does not contain UPDATED_EMPN
        defaultCheckinTzShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where roomn equals to DEFAULT_ROOMN
        defaultCheckinTzShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the checkinTzList where roomn equals to UPDATED_ROOMN
        defaultCheckinTzShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where roomn not equals to DEFAULT_ROOMN
        defaultCheckinTzShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the checkinTzList where roomn not equals to UPDATED_ROOMN
        defaultCheckinTzShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultCheckinTzShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the checkinTzList where roomn equals to UPDATED_ROOMN
        defaultCheckinTzShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where roomn is not null
        defaultCheckinTzShouldBeFound("roomn.specified=true");

        // Get all the checkinTzList where roomn is null
        defaultCheckinTzShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRoomnContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where roomn contains DEFAULT_ROOMN
        defaultCheckinTzShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the checkinTzList where roomn contains UPDATED_ROOMN
        defaultCheckinTzShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where roomn does not contain DEFAULT_ROOMN
        defaultCheckinTzShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the checkinTzList where roomn does not contain UPDATED_ROOMN
        defaultCheckinTzShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRentpIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where rentp equals to DEFAULT_RENTP
        defaultCheckinTzShouldBeFound("rentp.equals=" + DEFAULT_RENTP);

        // Get all the checkinTzList where rentp equals to UPDATED_RENTP
        defaultCheckinTzShouldNotBeFound("rentp.equals=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRentpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where rentp not equals to DEFAULT_RENTP
        defaultCheckinTzShouldNotBeFound("rentp.notEquals=" + DEFAULT_RENTP);

        // Get all the checkinTzList where rentp not equals to UPDATED_RENTP
        defaultCheckinTzShouldBeFound("rentp.notEquals=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRentpIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where rentp in DEFAULT_RENTP or UPDATED_RENTP
        defaultCheckinTzShouldBeFound("rentp.in=" + DEFAULT_RENTP + "," + UPDATED_RENTP);

        // Get all the checkinTzList where rentp equals to UPDATED_RENTP
        defaultCheckinTzShouldNotBeFound("rentp.in=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRentpIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where rentp is not null
        defaultCheckinTzShouldBeFound("rentp.specified=true");

        // Get all the checkinTzList where rentp is null
        defaultCheckinTzShouldNotBeFound("rentp.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRentpContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where rentp contains DEFAULT_RENTP
        defaultCheckinTzShouldBeFound("rentp.contains=" + DEFAULT_RENTP);

        // Get all the checkinTzList where rentp contains UPDATED_RENTP
        defaultCheckinTzShouldNotBeFound("rentp.contains=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRentpNotContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where rentp does not contain DEFAULT_RENTP
        defaultCheckinTzShouldNotBeFound("rentp.doesNotContain=" + DEFAULT_RENTP);

        // Get all the checkinTzList where rentp does not contain UPDATED_RENTP
        defaultCheckinTzShouldBeFound("rentp.doesNotContain=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByProtocolrentIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where protocolrent equals to DEFAULT_PROTOCOLRENT
        defaultCheckinTzShouldBeFound("protocolrent.equals=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinTzList where protocolrent equals to UPDATED_PROTOCOLRENT
        defaultCheckinTzShouldNotBeFound("protocolrent.equals=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByProtocolrentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where protocolrent not equals to DEFAULT_PROTOCOLRENT
        defaultCheckinTzShouldNotBeFound("protocolrent.notEquals=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinTzList where protocolrent not equals to UPDATED_PROTOCOLRENT
        defaultCheckinTzShouldBeFound("protocolrent.notEquals=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByProtocolrentIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where protocolrent in DEFAULT_PROTOCOLRENT or UPDATED_PROTOCOLRENT
        defaultCheckinTzShouldBeFound("protocolrent.in=" + DEFAULT_PROTOCOLRENT + "," + UPDATED_PROTOCOLRENT);

        // Get all the checkinTzList where protocolrent equals to UPDATED_PROTOCOLRENT
        defaultCheckinTzShouldNotBeFound("protocolrent.in=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByProtocolrentIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where protocolrent is not null
        defaultCheckinTzShouldBeFound("protocolrent.specified=true");

        // Get all the checkinTzList where protocolrent is null
        defaultCheckinTzShouldNotBeFound("protocolrent.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByProtocolrentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where protocolrent is greater than or equal to DEFAULT_PROTOCOLRENT
        defaultCheckinTzShouldBeFound("protocolrent.greaterThanOrEqual=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinTzList where protocolrent is greater than or equal to UPDATED_PROTOCOLRENT
        defaultCheckinTzShouldNotBeFound("protocolrent.greaterThanOrEqual=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByProtocolrentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where protocolrent is less than or equal to DEFAULT_PROTOCOLRENT
        defaultCheckinTzShouldBeFound("protocolrent.lessThanOrEqual=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinTzList where protocolrent is less than or equal to SMALLER_PROTOCOLRENT
        defaultCheckinTzShouldNotBeFound("protocolrent.lessThanOrEqual=" + SMALLER_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByProtocolrentIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where protocolrent is less than DEFAULT_PROTOCOLRENT
        defaultCheckinTzShouldNotBeFound("protocolrent.lessThan=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinTzList where protocolrent is less than UPDATED_PROTOCOLRENT
        defaultCheckinTzShouldBeFound("protocolrent.lessThan=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByProtocolrentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where protocolrent is greater than DEFAULT_PROTOCOLRENT
        defaultCheckinTzShouldNotBeFound("protocolrent.greaterThan=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinTzList where protocolrent is greater than SMALLER_PROTOCOLRENT
        defaultCheckinTzShouldBeFound("protocolrent.greaterThan=" + SMALLER_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where remark equals to DEFAULT_REMARK
        defaultCheckinTzShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the checkinTzList where remark equals to UPDATED_REMARK
        defaultCheckinTzShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where remark not equals to DEFAULT_REMARK
        defaultCheckinTzShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the checkinTzList where remark not equals to UPDATED_REMARK
        defaultCheckinTzShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultCheckinTzShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the checkinTzList where remark equals to UPDATED_REMARK
        defaultCheckinTzShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where remark is not null
        defaultCheckinTzShouldBeFound("remark.specified=true");

        // Get all the checkinTzList where remark is null
        defaultCheckinTzShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRemarkContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where remark contains DEFAULT_REMARK
        defaultCheckinTzShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the checkinTzList where remark contains UPDATED_REMARK
        defaultCheckinTzShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where remark does not contain DEFAULT_REMARK
        defaultCheckinTzShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the checkinTzList where remark does not contain UPDATED_REMARK
        defaultCheckinTzShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByPhonenIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where phonen equals to DEFAULT_PHONEN
        defaultCheckinTzShouldBeFound("phonen.equals=" + DEFAULT_PHONEN);

        // Get all the checkinTzList where phonen equals to UPDATED_PHONEN
        defaultCheckinTzShouldNotBeFound("phonen.equals=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByPhonenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where phonen not equals to DEFAULT_PHONEN
        defaultCheckinTzShouldNotBeFound("phonen.notEquals=" + DEFAULT_PHONEN);

        // Get all the checkinTzList where phonen not equals to UPDATED_PHONEN
        defaultCheckinTzShouldBeFound("phonen.notEquals=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByPhonenIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where phonen in DEFAULT_PHONEN or UPDATED_PHONEN
        defaultCheckinTzShouldBeFound("phonen.in=" + DEFAULT_PHONEN + "," + UPDATED_PHONEN);

        // Get all the checkinTzList where phonen equals to UPDATED_PHONEN
        defaultCheckinTzShouldNotBeFound("phonen.in=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByPhonenIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where phonen is not null
        defaultCheckinTzShouldBeFound("phonen.specified=true");

        // Get all the checkinTzList where phonen is null
        defaultCheckinTzShouldNotBeFound("phonen.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByPhonenContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where phonen contains DEFAULT_PHONEN
        defaultCheckinTzShouldBeFound("phonen.contains=" + DEFAULT_PHONEN);

        // Get all the checkinTzList where phonen contains UPDATED_PHONEN
        defaultCheckinTzShouldNotBeFound("phonen.contains=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByPhonenNotContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where phonen does not contain DEFAULT_PHONEN
        defaultCheckinTzShouldNotBeFound("phonen.doesNotContain=" + DEFAULT_PHONEN);

        // Get all the checkinTzList where phonen does not contain UPDATED_PHONEN
        defaultCheckinTzShouldBeFound("phonen.doesNotContain=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByEmpn2IsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where empn2 equals to DEFAULT_EMPN_2
        defaultCheckinTzShouldBeFound("empn2.equals=" + DEFAULT_EMPN_2);

        // Get all the checkinTzList where empn2 equals to UPDATED_EMPN_2
        defaultCheckinTzShouldNotBeFound("empn2.equals=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByEmpn2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where empn2 not equals to DEFAULT_EMPN_2
        defaultCheckinTzShouldNotBeFound("empn2.notEquals=" + DEFAULT_EMPN_2);

        // Get all the checkinTzList where empn2 not equals to UPDATED_EMPN_2
        defaultCheckinTzShouldBeFound("empn2.notEquals=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByEmpn2IsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where empn2 in DEFAULT_EMPN_2 or UPDATED_EMPN_2
        defaultCheckinTzShouldBeFound("empn2.in=" + DEFAULT_EMPN_2 + "," + UPDATED_EMPN_2);

        // Get all the checkinTzList where empn2 equals to UPDATED_EMPN_2
        defaultCheckinTzShouldNotBeFound("empn2.in=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByEmpn2IsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where empn2 is not null
        defaultCheckinTzShouldBeFound("empn2.specified=true");

        // Get all the checkinTzList where empn2 is null
        defaultCheckinTzShouldNotBeFound("empn2.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByEmpn2ContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where empn2 contains DEFAULT_EMPN_2
        defaultCheckinTzShouldBeFound("empn2.contains=" + DEFAULT_EMPN_2);

        // Get all the checkinTzList where empn2 contains UPDATED_EMPN_2
        defaultCheckinTzShouldNotBeFound("empn2.contains=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByEmpn2NotContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where empn2 does not contain DEFAULT_EMPN_2
        defaultCheckinTzShouldNotBeFound("empn2.doesNotContain=" + DEFAULT_EMPN_2);

        // Get all the checkinTzList where empn2 does not contain UPDATED_EMPN_2
        defaultCheckinTzShouldBeFound("empn2.doesNotContain=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByMemoIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where memo equals to DEFAULT_MEMO
        defaultCheckinTzShouldBeFound("memo.equals=" + DEFAULT_MEMO);

        // Get all the checkinTzList where memo equals to UPDATED_MEMO
        defaultCheckinTzShouldNotBeFound("memo.equals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByMemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where memo not equals to DEFAULT_MEMO
        defaultCheckinTzShouldNotBeFound("memo.notEquals=" + DEFAULT_MEMO);

        // Get all the checkinTzList where memo not equals to UPDATED_MEMO
        defaultCheckinTzShouldBeFound("memo.notEquals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByMemoIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where memo in DEFAULT_MEMO or UPDATED_MEMO
        defaultCheckinTzShouldBeFound("memo.in=" + DEFAULT_MEMO + "," + UPDATED_MEMO);

        // Get all the checkinTzList where memo equals to UPDATED_MEMO
        defaultCheckinTzShouldNotBeFound("memo.in=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByMemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where memo is not null
        defaultCheckinTzShouldBeFound("memo.specified=true");

        // Get all the checkinTzList where memo is null
        defaultCheckinTzShouldNotBeFound("memo.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByMemoContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where memo contains DEFAULT_MEMO
        defaultCheckinTzShouldBeFound("memo.contains=" + DEFAULT_MEMO);

        // Get all the checkinTzList where memo contains UPDATED_MEMO
        defaultCheckinTzShouldNotBeFound("memo.contains=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByMemoNotContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where memo does not contain DEFAULT_MEMO
        defaultCheckinTzShouldNotBeFound("memo.doesNotContain=" + DEFAULT_MEMO);

        // Get all the checkinTzList where memo does not contain UPDATED_MEMO
        defaultCheckinTzShouldBeFound("memo.doesNotContain=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByLfSignIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where lfSign equals to DEFAULT_LF_SIGN
        defaultCheckinTzShouldBeFound("lfSign.equals=" + DEFAULT_LF_SIGN);

        // Get all the checkinTzList where lfSign equals to UPDATED_LF_SIGN
        defaultCheckinTzShouldNotBeFound("lfSign.equals=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByLfSignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where lfSign not equals to DEFAULT_LF_SIGN
        defaultCheckinTzShouldNotBeFound("lfSign.notEquals=" + DEFAULT_LF_SIGN);

        // Get all the checkinTzList where lfSign not equals to UPDATED_LF_SIGN
        defaultCheckinTzShouldBeFound("lfSign.notEquals=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByLfSignIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where lfSign in DEFAULT_LF_SIGN or UPDATED_LF_SIGN
        defaultCheckinTzShouldBeFound("lfSign.in=" + DEFAULT_LF_SIGN + "," + UPDATED_LF_SIGN);

        // Get all the checkinTzList where lfSign equals to UPDATED_LF_SIGN
        defaultCheckinTzShouldNotBeFound("lfSign.in=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByLfSignIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where lfSign is not null
        defaultCheckinTzShouldBeFound("lfSign.specified=true");

        // Get all the checkinTzList where lfSign is null
        defaultCheckinTzShouldNotBeFound("lfSign.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByLfSignContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where lfSign contains DEFAULT_LF_SIGN
        defaultCheckinTzShouldBeFound("lfSign.contains=" + DEFAULT_LF_SIGN);

        // Get all the checkinTzList where lfSign contains UPDATED_LF_SIGN
        defaultCheckinTzShouldNotBeFound("lfSign.contains=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByLfSignNotContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where lfSign does not contain DEFAULT_LF_SIGN
        defaultCheckinTzShouldNotBeFound("lfSign.doesNotContain=" + DEFAULT_LF_SIGN);

        // Get all the checkinTzList where lfSign does not contain UPDATED_LF_SIGN
        defaultCheckinTzShouldBeFound("lfSign.doesNotContain=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGuestnameIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where guestname equals to DEFAULT_GUESTNAME
        defaultCheckinTzShouldBeFound("guestname.equals=" + DEFAULT_GUESTNAME);

        // Get all the checkinTzList where guestname equals to UPDATED_GUESTNAME
        defaultCheckinTzShouldNotBeFound("guestname.equals=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGuestnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where guestname not equals to DEFAULT_GUESTNAME
        defaultCheckinTzShouldNotBeFound("guestname.notEquals=" + DEFAULT_GUESTNAME);

        // Get all the checkinTzList where guestname not equals to UPDATED_GUESTNAME
        defaultCheckinTzShouldBeFound("guestname.notEquals=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGuestnameIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where guestname in DEFAULT_GUESTNAME or UPDATED_GUESTNAME
        defaultCheckinTzShouldBeFound("guestname.in=" + DEFAULT_GUESTNAME + "," + UPDATED_GUESTNAME);

        // Get all the checkinTzList where guestname equals to UPDATED_GUESTNAME
        defaultCheckinTzShouldNotBeFound("guestname.in=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGuestnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where guestname is not null
        defaultCheckinTzShouldBeFound("guestname.specified=true");

        // Get all the checkinTzList where guestname is null
        defaultCheckinTzShouldNotBeFound("guestname.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGuestnameContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where guestname contains DEFAULT_GUESTNAME
        defaultCheckinTzShouldBeFound("guestname.contains=" + DEFAULT_GUESTNAME);

        // Get all the checkinTzList where guestname contains UPDATED_GUESTNAME
        defaultCheckinTzShouldNotBeFound("guestname.contains=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByGuestnameNotContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where guestname does not contain DEFAULT_GUESTNAME
        defaultCheckinTzShouldNotBeFound("guestname.doesNotContain=" + DEFAULT_GUESTNAME);

        // Get all the checkinTzList where guestname does not contain UPDATED_GUESTNAME
        defaultCheckinTzShouldBeFound("guestname.doesNotContain=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByBcIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where bc equals to DEFAULT_BC
        defaultCheckinTzShouldBeFound("bc.equals=" + DEFAULT_BC);

        // Get all the checkinTzList where bc equals to UPDATED_BC
        defaultCheckinTzShouldNotBeFound("bc.equals=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByBcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where bc not equals to DEFAULT_BC
        defaultCheckinTzShouldNotBeFound("bc.notEquals=" + DEFAULT_BC);

        // Get all the checkinTzList where bc not equals to UPDATED_BC
        defaultCheckinTzShouldBeFound("bc.notEquals=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByBcIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where bc in DEFAULT_BC or UPDATED_BC
        defaultCheckinTzShouldBeFound("bc.in=" + DEFAULT_BC + "," + UPDATED_BC);

        // Get all the checkinTzList where bc equals to UPDATED_BC
        defaultCheckinTzShouldNotBeFound("bc.in=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByBcIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where bc is not null
        defaultCheckinTzShouldBeFound("bc.specified=true");

        // Get all the checkinTzList where bc is null
        defaultCheckinTzShouldNotBeFound("bc.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByBcContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where bc contains DEFAULT_BC
        defaultCheckinTzShouldBeFound("bc.contains=" + DEFAULT_BC);

        // Get all the checkinTzList where bc contains UPDATED_BC
        defaultCheckinTzShouldNotBeFound("bc.contains=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByBcNotContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where bc does not contain DEFAULT_BC
        defaultCheckinTzShouldNotBeFound("bc.doesNotContain=" + DEFAULT_BC);

        // Get all the checkinTzList where bc does not contain UPDATED_BC
        defaultCheckinTzShouldBeFound("bc.doesNotContain=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRoomtypeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where roomtype equals to DEFAULT_ROOMTYPE
        defaultCheckinTzShouldBeFound("roomtype.equals=" + DEFAULT_ROOMTYPE);

        // Get all the checkinTzList where roomtype equals to UPDATED_ROOMTYPE
        defaultCheckinTzShouldNotBeFound("roomtype.equals=" + UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRoomtypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where roomtype not equals to DEFAULT_ROOMTYPE
        defaultCheckinTzShouldNotBeFound("roomtype.notEquals=" + DEFAULT_ROOMTYPE);

        // Get all the checkinTzList where roomtype not equals to UPDATED_ROOMTYPE
        defaultCheckinTzShouldBeFound("roomtype.notEquals=" + UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRoomtypeIsInShouldWork() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where roomtype in DEFAULT_ROOMTYPE or UPDATED_ROOMTYPE
        defaultCheckinTzShouldBeFound("roomtype.in=" + DEFAULT_ROOMTYPE + "," + UPDATED_ROOMTYPE);

        // Get all the checkinTzList where roomtype equals to UPDATED_ROOMTYPE
        defaultCheckinTzShouldNotBeFound("roomtype.in=" + UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRoomtypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where roomtype is not null
        defaultCheckinTzShouldBeFound("roomtype.specified=true");

        // Get all the checkinTzList where roomtype is null
        defaultCheckinTzShouldNotBeFound("roomtype.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRoomtypeContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where roomtype contains DEFAULT_ROOMTYPE
        defaultCheckinTzShouldBeFound("roomtype.contains=" + DEFAULT_ROOMTYPE);

        // Get all the checkinTzList where roomtype contains UPDATED_ROOMTYPE
        defaultCheckinTzShouldNotBeFound("roomtype.contains=" + UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void getAllCheckinTzsByRoomtypeNotContainsSomething() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        // Get all the checkinTzList where roomtype does not contain DEFAULT_ROOMTYPE
        defaultCheckinTzShouldNotBeFound("roomtype.doesNotContain=" + DEFAULT_ROOMTYPE);

        // Get all the checkinTzList where roomtype does not contain UPDATED_ROOMTYPE
        defaultCheckinTzShouldBeFound("roomtype.doesNotContain=" + UPDATED_ROOMTYPE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckinTzShouldBeFound(String filter) throws Exception {
        restCheckinTzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkinTz.getId().intValue())))
            .andExpect(jsonPath("$.[*].guestId").value(hasItem(DEFAULT_GUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].indatetime").value(hasItem(DEFAULT_INDATETIME.toString())))
            .andExpect(jsonPath("$.[*].residefate").value(hasItem(DEFAULT_RESIDEFATE.intValue())))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].rentp").value(hasItem(DEFAULT_RENTP)))
            .andExpect(jsonPath("$.[*].protocolrent").value(hasItem(sameNumber(DEFAULT_PROTOCOLRENT))))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].phonen").value(hasItem(DEFAULT_PHONEN)))
            .andExpect(jsonPath("$.[*].empn2").value(hasItem(DEFAULT_EMPN_2)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].lfSign").value(hasItem(DEFAULT_LF_SIGN)))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].bc").value(hasItem(DEFAULT_BC)))
            .andExpect(jsonPath("$.[*].roomtype").value(hasItem(DEFAULT_ROOMTYPE)));

        // Check, that the count call also returns 1
        restCheckinTzMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckinTzShouldNotBeFound(String filter) throws Exception {
        restCheckinTzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckinTzMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCheckinTz() throws Exception {
        // Get the checkinTz
        restCheckinTzMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCheckinTz() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        int databaseSizeBeforeUpdate = checkinTzRepository.findAll().size();

        // Update the checkinTz
        CheckinTz updatedCheckinTz = checkinTzRepository.findById(checkinTz.getId()).get();
        // Disconnect from session so that the updates on updatedCheckinTz are not directly saved in db
        em.detach(updatedCheckinTz);
        updatedCheckinTz
            .guestId(UPDATED_GUEST_ID)
            .account(UPDATED_ACCOUNT)
            .hoteltime(UPDATED_HOTELTIME)
            .indatetime(UPDATED_INDATETIME)
            .residefate(UPDATED_RESIDEFATE)
            .gotime(UPDATED_GOTIME)
            .empn(UPDATED_EMPN)
            .roomn(UPDATED_ROOMN)
            .rentp(UPDATED_RENTP)
            .protocolrent(UPDATED_PROTOCOLRENT)
            .remark(UPDATED_REMARK)
            .phonen(UPDATED_PHONEN)
            .empn2(UPDATED_EMPN_2)
            .memo(UPDATED_MEMO)
            .lfSign(UPDATED_LF_SIGN)
            .guestname(UPDATED_GUESTNAME)
            .bc(UPDATED_BC)
            .roomtype(UPDATED_ROOMTYPE);
        CheckinTzDTO checkinTzDTO = checkinTzMapper.toDto(updatedCheckinTz);

        restCheckinTzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkinTzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkinTzDTO))
            )
            .andExpect(status().isOk());

        // Validate the CheckinTz in the database
        List<CheckinTz> checkinTzList = checkinTzRepository.findAll();
        assertThat(checkinTzList).hasSize(databaseSizeBeforeUpdate);
        CheckinTz testCheckinTz = checkinTzList.get(checkinTzList.size() - 1);
        assertThat(testCheckinTz.getGuestId()).isEqualTo(UPDATED_GUEST_ID);
        assertThat(testCheckinTz.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCheckinTz.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCheckinTz.getIndatetime()).isEqualTo(UPDATED_INDATETIME);
        assertThat(testCheckinTz.getResidefate()).isEqualTo(UPDATED_RESIDEFATE);
        assertThat(testCheckinTz.getGotime()).isEqualTo(UPDATED_GOTIME);
        assertThat(testCheckinTz.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckinTz.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCheckinTz.getRentp()).isEqualTo(UPDATED_RENTP);
        assertThat(testCheckinTz.getProtocolrent()).isEqualTo(UPDATED_PROTOCOLRENT);
        assertThat(testCheckinTz.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testCheckinTz.getPhonen()).isEqualTo(UPDATED_PHONEN);
        assertThat(testCheckinTz.getEmpn2()).isEqualTo(UPDATED_EMPN_2);
        assertThat(testCheckinTz.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testCheckinTz.getLfSign()).isEqualTo(UPDATED_LF_SIGN);
        assertThat(testCheckinTz.getGuestname()).isEqualTo(UPDATED_GUESTNAME);
        assertThat(testCheckinTz.getBc()).isEqualTo(UPDATED_BC);
        assertThat(testCheckinTz.getRoomtype()).isEqualTo(UPDATED_ROOMTYPE);

        // Validate the CheckinTz in Elasticsearch
        verify(mockCheckinTzSearchRepository).save(testCheckinTz);
    }

    @Test
    @Transactional
    void putNonExistingCheckinTz() throws Exception {
        int databaseSizeBeforeUpdate = checkinTzRepository.findAll().size();
        checkinTz.setId(count.incrementAndGet());

        // Create the CheckinTz
        CheckinTzDTO checkinTzDTO = checkinTzMapper.toDto(checkinTz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckinTzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkinTzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkinTzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckinTz in the database
        List<CheckinTz> checkinTzList = checkinTzRepository.findAll();
        assertThat(checkinTzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinTz in Elasticsearch
        verify(mockCheckinTzSearchRepository, times(0)).save(checkinTz);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckinTz() throws Exception {
        int databaseSizeBeforeUpdate = checkinTzRepository.findAll().size();
        checkinTz.setId(count.incrementAndGet());

        // Create the CheckinTz
        CheckinTzDTO checkinTzDTO = checkinTzMapper.toDto(checkinTz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinTzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkinTzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckinTz in the database
        List<CheckinTz> checkinTzList = checkinTzRepository.findAll();
        assertThat(checkinTzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinTz in Elasticsearch
        verify(mockCheckinTzSearchRepository, times(0)).save(checkinTz);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckinTz() throws Exception {
        int databaseSizeBeforeUpdate = checkinTzRepository.findAll().size();
        checkinTz.setId(count.incrementAndGet());

        // Create the CheckinTz
        CheckinTzDTO checkinTzDTO = checkinTzMapper.toDto(checkinTz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinTzMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinTzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckinTz in the database
        List<CheckinTz> checkinTzList = checkinTzRepository.findAll();
        assertThat(checkinTzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinTz in Elasticsearch
        verify(mockCheckinTzSearchRepository, times(0)).save(checkinTz);
    }

    @Test
    @Transactional
    void partialUpdateCheckinTzWithPatch() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        int databaseSizeBeforeUpdate = checkinTzRepository.findAll().size();

        // Update the checkinTz using partial update
        CheckinTz partialUpdatedCheckinTz = new CheckinTz();
        partialUpdatedCheckinTz.setId(checkinTz.getId());

        partialUpdatedCheckinTz
            .hoteltime(UPDATED_HOTELTIME)
            .residefate(UPDATED_RESIDEFATE)
            .empn(UPDATED_EMPN)
            .roomn(UPDATED_ROOMN)
            .rentp(UPDATED_RENTP)
            .remark(UPDATED_REMARK)
            .phonen(UPDATED_PHONEN)
            .memo(UPDATED_MEMO)
            .lfSign(UPDATED_LF_SIGN)
            .bc(UPDATED_BC)
            .roomtype(UPDATED_ROOMTYPE);

        restCheckinTzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckinTz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckinTz))
            )
            .andExpect(status().isOk());

        // Validate the CheckinTz in the database
        List<CheckinTz> checkinTzList = checkinTzRepository.findAll();
        assertThat(checkinTzList).hasSize(databaseSizeBeforeUpdate);
        CheckinTz testCheckinTz = checkinTzList.get(checkinTzList.size() - 1);
        assertThat(testCheckinTz.getGuestId()).isEqualTo(DEFAULT_GUEST_ID);
        assertThat(testCheckinTz.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testCheckinTz.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCheckinTz.getIndatetime()).isEqualTo(DEFAULT_INDATETIME);
        assertThat(testCheckinTz.getResidefate()).isEqualTo(UPDATED_RESIDEFATE);
        assertThat(testCheckinTz.getGotime()).isEqualTo(DEFAULT_GOTIME);
        assertThat(testCheckinTz.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckinTz.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCheckinTz.getRentp()).isEqualTo(UPDATED_RENTP);
        assertThat(testCheckinTz.getProtocolrent()).isEqualByComparingTo(DEFAULT_PROTOCOLRENT);
        assertThat(testCheckinTz.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testCheckinTz.getPhonen()).isEqualTo(UPDATED_PHONEN);
        assertThat(testCheckinTz.getEmpn2()).isEqualTo(DEFAULT_EMPN_2);
        assertThat(testCheckinTz.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testCheckinTz.getLfSign()).isEqualTo(UPDATED_LF_SIGN);
        assertThat(testCheckinTz.getGuestname()).isEqualTo(DEFAULT_GUESTNAME);
        assertThat(testCheckinTz.getBc()).isEqualTo(UPDATED_BC);
        assertThat(testCheckinTz.getRoomtype()).isEqualTo(UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void fullUpdateCheckinTzWithPatch() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        int databaseSizeBeforeUpdate = checkinTzRepository.findAll().size();

        // Update the checkinTz using partial update
        CheckinTz partialUpdatedCheckinTz = new CheckinTz();
        partialUpdatedCheckinTz.setId(checkinTz.getId());

        partialUpdatedCheckinTz
            .guestId(UPDATED_GUEST_ID)
            .account(UPDATED_ACCOUNT)
            .hoteltime(UPDATED_HOTELTIME)
            .indatetime(UPDATED_INDATETIME)
            .residefate(UPDATED_RESIDEFATE)
            .gotime(UPDATED_GOTIME)
            .empn(UPDATED_EMPN)
            .roomn(UPDATED_ROOMN)
            .rentp(UPDATED_RENTP)
            .protocolrent(UPDATED_PROTOCOLRENT)
            .remark(UPDATED_REMARK)
            .phonen(UPDATED_PHONEN)
            .empn2(UPDATED_EMPN_2)
            .memo(UPDATED_MEMO)
            .lfSign(UPDATED_LF_SIGN)
            .guestname(UPDATED_GUESTNAME)
            .bc(UPDATED_BC)
            .roomtype(UPDATED_ROOMTYPE);

        restCheckinTzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckinTz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckinTz))
            )
            .andExpect(status().isOk());

        // Validate the CheckinTz in the database
        List<CheckinTz> checkinTzList = checkinTzRepository.findAll();
        assertThat(checkinTzList).hasSize(databaseSizeBeforeUpdate);
        CheckinTz testCheckinTz = checkinTzList.get(checkinTzList.size() - 1);
        assertThat(testCheckinTz.getGuestId()).isEqualTo(UPDATED_GUEST_ID);
        assertThat(testCheckinTz.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCheckinTz.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCheckinTz.getIndatetime()).isEqualTo(UPDATED_INDATETIME);
        assertThat(testCheckinTz.getResidefate()).isEqualTo(UPDATED_RESIDEFATE);
        assertThat(testCheckinTz.getGotime()).isEqualTo(UPDATED_GOTIME);
        assertThat(testCheckinTz.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckinTz.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCheckinTz.getRentp()).isEqualTo(UPDATED_RENTP);
        assertThat(testCheckinTz.getProtocolrent()).isEqualByComparingTo(UPDATED_PROTOCOLRENT);
        assertThat(testCheckinTz.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testCheckinTz.getPhonen()).isEqualTo(UPDATED_PHONEN);
        assertThat(testCheckinTz.getEmpn2()).isEqualTo(UPDATED_EMPN_2);
        assertThat(testCheckinTz.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testCheckinTz.getLfSign()).isEqualTo(UPDATED_LF_SIGN);
        assertThat(testCheckinTz.getGuestname()).isEqualTo(UPDATED_GUESTNAME);
        assertThat(testCheckinTz.getBc()).isEqualTo(UPDATED_BC);
        assertThat(testCheckinTz.getRoomtype()).isEqualTo(UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCheckinTz() throws Exception {
        int databaseSizeBeforeUpdate = checkinTzRepository.findAll().size();
        checkinTz.setId(count.incrementAndGet());

        // Create the CheckinTz
        CheckinTzDTO checkinTzDTO = checkinTzMapper.toDto(checkinTz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckinTzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkinTzDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkinTzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckinTz in the database
        List<CheckinTz> checkinTzList = checkinTzRepository.findAll();
        assertThat(checkinTzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinTz in Elasticsearch
        verify(mockCheckinTzSearchRepository, times(0)).save(checkinTz);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckinTz() throws Exception {
        int databaseSizeBeforeUpdate = checkinTzRepository.findAll().size();
        checkinTz.setId(count.incrementAndGet());

        // Create the CheckinTz
        CheckinTzDTO checkinTzDTO = checkinTzMapper.toDto(checkinTz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinTzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkinTzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckinTz in the database
        List<CheckinTz> checkinTzList = checkinTzRepository.findAll();
        assertThat(checkinTzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinTz in Elasticsearch
        verify(mockCheckinTzSearchRepository, times(0)).save(checkinTz);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckinTz() throws Exception {
        int databaseSizeBeforeUpdate = checkinTzRepository.findAll().size();
        checkinTz.setId(count.incrementAndGet());

        // Create the CheckinTz
        CheckinTzDTO checkinTzDTO = checkinTzMapper.toDto(checkinTz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinTzMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(checkinTzDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckinTz in the database
        List<CheckinTz> checkinTzList = checkinTzRepository.findAll();
        assertThat(checkinTzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinTz in Elasticsearch
        verify(mockCheckinTzSearchRepository, times(0)).save(checkinTz);
    }

    @Test
    @Transactional
    void deleteCheckinTz() throws Exception {
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);

        int databaseSizeBeforeDelete = checkinTzRepository.findAll().size();

        // Delete the checkinTz
        restCheckinTzMockMvc
            .perform(delete(ENTITY_API_URL_ID, checkinTz.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckinTz> checkinTzList = checkinTzRepository.findAll();
        assertThat(checkinTzList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CheckinTz in Elasticsearch
        verify(mockCheckinTzSearchRepository, times(1)).deleteById(checkinTz.getId());
    }

    @Test
    @Transactional
    void searchCheckinTz() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        checkinTzRepository.saveAndFlush(checkinTz);
        when(mockCheckinTzSearchRepository.search(queryStringQuery("id:" + checkinTz.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(checkinTz), PageRequest.of(0, 1), 1));

        // Search the checkinTz
        restCheckinTzMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + checkinTz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkinTz.getId().intValue())))
            .andExpect(jsonPath("$.[*].guestId").value(hasItem(DEFAULT_GUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].indatetime").value(hasItem(DEFAULT_INDATETIME.toString())))
            .andExpect(jsonPath("$.[*].residefate").value(hasItem(DEFAULT_RESIDEFATE.intValue())))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].rentp").value(hasItem(DEFAULT_RENTP)))
            .andExpect(jsonPath("$.[*].protocolrent").value(hasItem(sameNumber(DEFAULT_PROTOCOLRENT))))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].phonen").value(hasItem(DEFAULT_PHONEN)))
            .andExpect(jsonPath("$.[*].empn2").value(hasItem(DEFAULT_EMPN_2)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].lfSign").value(hasItem(DEFAULT_LF_SIGN)))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].bc").value(hasItem(DEFAULT_BC)))
            .andExpect(jsonPath("$.[*].roomtype").value(hasItem(DEFAULT_ROOMTYPE)));
    }
}
