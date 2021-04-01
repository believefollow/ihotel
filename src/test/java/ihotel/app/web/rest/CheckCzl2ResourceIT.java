package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.CheckCzl2;
import ihotel.app.repository.CheckCzl2Repository;
import ihotel.app.repository.search.CheckCzl2SearchRepository;
import ihotel.app.service.criteria.CheckCzl2Criteria;
import ihotel.app.service.dto.CheckCzl2DTO;
import ihotel.app.service.mapper.CheckCzl2Mapper;
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
 * Integration tests for the {@link CheckCzl2Resource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CheckCzl2ResourceIT {

    private static final Instant DEFAULT_HOTELTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOTELTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PROTOCOL = "AAAAAAAAAA";
    private static final String UPDATED_PROTOCOL = "BBBBBBBBBB";

    private static final Long DEFAULT_RNUM = 1L;
    private static final Long UPDATED_RNUM = 2L;
    private static final Long SMALLER_RNUM = 1L - 1L;

    private static final BigDecimal DEFAULT_CZL = new BigDecimal(1);
    private static final BigDecimal UPDATED_CZL = new BigDecimal(2);
    private static final BigDecimal SMALLER_CZL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CHAGRGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CHAGRGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_CHAGRGE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CHAGRGE_AVG = new BigDecimal(1);
    private static final BigDecimal UPDATED_CHAGRGE_AVG = new BigDecimal(2);
    private static final BigDecimal SMALLER_CHAGRGE_AVG = new BigDecimal(1 - 1);

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final Instant DEFAULT_ENTERTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ENTERTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/check-czl-2-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/check-czl-2-s";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CheckCzl2Repository checkCzl2Repository;

    @Autowired
    private CheckCzl2Mapper checkCzl2Mapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CheckCzl2SearchRepositoryMockConfiguration
     */
    @Autowired
    private CheckCzl2SearchRepository mockCheckCzl2SearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckCzl2MockMvc;

    private CheckCzl2 checkCzl2;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckCzl2 createEntity(EntityManager em) {
        CheckCzl2 checkCzl2 = new CheckCzl2()
            .hoteltime(DEFAULT_HOTELTIME)
            .protocol(DEFAULT_PROTOCOL)
            .rnum(DEFAULT_RNUM)
            .czl(DEFAULT_CZL)
            .chagrge(DEFAULT_CHAGRGE)
            .chagrgeAvg(DEFAULT_CHAGRGE_AVG)
            .empn(DEFAULT_EMPN)
            .entertime(DEFAULT_ENTERTIME);
        return checkCzl2;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckCzl2 createUpdatedEntity(EntityManager em) {
        CheckCzl2 checkCzl2 = new CheckCzl2()
            .hoteltime(UPDATED_HOTELTIME)
            .protocol(UPDATED_PROTOCOL)
            .rnum(UPDATED_RNUM)
            .czl(UPDATED_CZL)
            .chagrge(UPDATED_CHAGRGE)
            .chagrgeAvg(UPDATED_CHAGRGE_AVG)
            .empn(UPDATED_EMPN)
            .entertime(UPDATED_ENTERTIME);
        return checkCzl2;
    }

    @BeforeEach
    public void initTest() {
        checkCzl2 = createEntity(em);
    }

    @Test
    @Transactional
    void createCheckCzl2() throws Exception {
        int databaseSizeBeforeCreate = checkCzl2Repository.findAll().size();
        // Create the CheckCzl2
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);
        restCheckCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO)))
            .andExpect(status().isCreated());

        // Validate the CheckCzl2 in the database
        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeCreate + 1);
        CheckCzl2 testCheckCzl2 = checkCzl2List.get(checkCzl2List.size() - 1);
        assertThat(testCheckCzl2.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testCheckCzl2.getProtocol()).isEqualTo(DEFAULT_PROTOCOL);
        assertThat(testCheckCzl2.getRnum()).isEqualTo(DEFAULT_RNUM);
        assertThat(testCheckCzl2.getCzl()).isEqualByComparingTo(DEFAULT_CZL);
        assertThat(testCheckCzl2.getChagrge()).isEqualByComparingTo(DEFAULT_CHAGRGE);
        assertThat(testCheckCzl2.getChagrgeAvg()).isEqualByComparingTo(DEFAULT_CHAGRGE_AVG);
        assertThat(testCheckCzl2.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testCheckCzl2.getEntertime()).isEqualTo(DEFAULT_ENTERTIME);

        // Validate the CheckCzl2 in Elasticsearch
        verify(mockCheckCzl2SearchRepository, times(1)).save(testCheckCzl2);
    }

    @Test
    @Transactional
    void createCheckCzl2WithExistingId() throws Exception {
        // Create the CheckCzl2 with an existing ID
        checkCzl2.setId(1L);
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        int databaseSizeBeforeCreate = checkCzl2Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckCzl2 in the database
        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeCreate);

        // Validate the CheckCzl2 in Elasticsearch
        verify(mockCheckCzl2SearchRepository, times(0)).save(checkCzl2);
    }

    @Test
    @Transactional
    void checkHoteltimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzl2Repository.findAll().size();
        // set the field null
        checkCzl2.setHoteltime(null);

        // Create the CheckCzl2, which fails.
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        restCheckCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkProtocolIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzl2Repository.findAll().size();
        // set the field null
        checkCzl2.setProtocol(null);

        // Create the CheckCzl2, which fails.
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        restCheckCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRnumIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzl2Repository.findAll().size();
        // set the field null
        checkCzl2.setRnum(null);

        // Create the CheckCzl2, which fails.
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        restCheckCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCzlIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzl2Repository.findAll().size();
        // set the field null
        checkCzl2.setCzl(null);

        // Create the CheckCzl2, which fails.
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        restCheckCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChagrgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzl2Repository.findAll().size();
        // set the field null
        checkCzl2.setChagrge(null);

        // Create the CheckCzl2, which fails.
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        restCheckCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChagrgeAvgIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzl2Repository.findAll().size();
        // set the field null
        checkCzl2.setChagrgeAvg(null);

        // Create the CheckCzl2, which fails.
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        restCheckCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmpnIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzl2Repository.findAll().size();
        // set the field null
        checkCzl2.setEmpn(null);

        // Create the CheckCzl2, which fails.
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        restCheckCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntertimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzl2Repository.findAll().size();
        // set the field null
        checkCzl2.setEntertime(null);

        // Create the CheckCzl2, which fails.
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        restCheckCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCheckCzl2s() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List
        restCheckCzl2MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkCzl2.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].protocol").value(hasItem(DEFAULT_PROTOCOL)))
            .andExpect(jsonPath("$.[*].rnum").value(hasItem(DEFAULT_RNUM.intValue())))
            .andExpect(jsonPath("$.[*].czl").value(hasItem(sameNumber(DEFAULT_CZL))))
            .andExpect(jsonPath("$.[*].chagrge").value(hasItem(sameNumber(DEFAULT_CHAGRGE))))
            .andExpect(jsonPath("$.[*].chagrgeAvg").value(hasItem(sameNumber(DEFAULT_CHAGRGE_AVG))))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].entertime").value(hasItem(DEFAULT_ENTERTIME.toString())));
    }

    @Test
    @Transactional
    void getCheckCzl2() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get the checkCzl2
        restCheckCzl2MockMvc
            .perform(get(ENTITY_API_URL_ID, checkCzl2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkCzl2.getId().intValue()))
            .andExpect(jsonPath("$.hoteltime").value(DEFAULT_HOTELTIME.toString()))
            .andExpect(jsonPath("$.protocol").value(DEFAULT_PROTOCOL))
            .andExpect(jsonPath("$.rnum").value(DEFAULT_RNUM.intValue()))
            .andExpect(jsonPath("$.czl").value(sameNumber(DEFAULT_CZL)))
            .andExpect(jsonPath("$.chagrge").value(sameNumber(DEFAULT_CHAGRGE)))
            .andExpect(jsonPath("$.chagrgeAvg").value(sameNumber(DEFAULT_CHAGRGE_AVG)))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.entertime").value(DEFAULT_ENTERTIME.toString()));
    }

    @Test
    @Transactional
    void getCheckCzl2sByIdFiltering() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        Long id = checkCzl2.getId();

        defaultCheckCzl2ShouldBeFound("id.equals=" + id);
        defaultCheckCzl2ShouldNotBeFound("id.notEquals=" + id);

        defaultCheckCzl2ShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCheckCzl2ShouldNotBeFound("id.greaterThan=" + id);

        defaultCheckCzl2ShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCheckCzl2ShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByHoteltimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where hoteltime equals to DEFAULT_HOTELTIME
        defaultCheckCzl2ShouldBeFound("hoteltime.equals=" + DEFAULT_HOTELTIME);

        // Get all the checkCzl2List where hoteltime equals to UPDATED_HOTELTIME
        defaultCheckCzl2ShouldNotBeFound("hoteltime.equals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByHoteltimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where hoteltime not equals to DEFAULT_HOTELTIME
        defaultCheckCzl2ShouldNotBeFound("hoteltime.notEquals=" + DEFAULT_HOTELTIME);

        // Get all the checkCzl2List where hoteltime not equals to UPDATED_HOTELTIME
        defaultCheckCzl2ShouldBeFound("hoteltime.notEquals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByHoteltimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where hoteltime in DEFAULT_HOTELTIME or UPDATED_HOTELTIME
        defaultCheckCzl2ShouldBeFound("hoteltime.in=" + DEFAULT_HOTELTIME + "," + UPDATED_HOTELTIME);

        // Get all the checkCzl2List where hoteltime equals to UPDATED_HOTELTIME
        defaultCheckCzl2ShouldNotBeFound("hoteltime.in=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByHoteltimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where hoteltime is not null
        defaultCheckCzl2ShouldBeFound("hoteltime.specified=true");

        // Get all the checkCzl2List where hoteltime is null
        defaultCheckCzl2ShouldNotBeFound("hoteltime.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByProtocolIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where protocol equals to DEFAULT_PROTOCOL
        defaultCheckCzl2ShouldBeFound("protocol.equals=" + DEFAULT_PROTOCOL);

        // Get all the checkCzl2List where protocol equals to UPDATED_PROTOCOL
        defaultCheckCzl2ShouldNotBeFound("protocol.equals=" + UPDATED_PROTOCOL);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByProtocolIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where protocol not equals to DEFAULT_PROTOCOL
        defaultCheckCzl2ShouldNotBeFound("protocol.notEquals=" + DEFAULT_PROTOCOL);

        // Get all the checkCzl2List where protocol not equals to UPDATED_PROTOCOL
        defaultCheckCzl2ShouldBeFound("protocol.notEquals=" + UPDATED_PROTOCOL);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByProtocolIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where protocol in DEFAULT_PROTOCOL or UPDATED_PROTOCOL
        defaultCheckCzl2ShouldBeFound("protocol.in=" + DEFAULT_PROTOCOL + "," + UPDATED_PROTOCOL);

        // Get all the checkCzl2List where protocol equals to UPDATED_PROTOCOL
        defaultCheckCzl2ShouldNotBeFound("protocol.in=" + UPDATED_PROTOCOL);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByProtocolIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where protocol is not null
        defaultCheckCzl2ShouldBeFound("protocol.specified=true");

        // Get all the checkCzl2List where protocol is null
        defaultCheckCzl2ShouldNotBeFound("protocol.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByProtocolContainsSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where protocol contains DEFAULT_PROTOCOL
        defaultCheckCzl2ShouldBeFound("protocol.contains=" + DEFAULT_PROTOCOL);

        // Get all the checkCzl2List where protocol contains UPDATED_PROTOCOL
        defaultCheckCzl2ShouldNotBeFound("protocol.contains=" + UPDATED_PROTOCOL);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByProtocolNotContainsSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where protocol does not contain DEFAULT_PROTOCOL
        defaultCheckCzl2ShouldNotBeFound("protocol.doesNotContain=" + DEFAULT_PROTOCOL);

        // Get all the checkCzl2List where protocol does not contain UPDATED_PROTOCOL
        defaultCheckCzl2ShouldBeFound("protocol.doesNotContain=" + UPDATED_PROTOCOL);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByRnumIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where rnum equals to DEFAULT_RNUM
        defaultCheckCzl2ShouldBeFound("rnum.equals=" + DEFAULT_RNUM);

        // Get all the checkCzl2List where rnum equals to UPDATED_RNUM
        defaultCheckCzl2ShouldNotBeFound("rnum.equals=" + UPDATED_RNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByRnumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where rnum not equals to DEFAULT_RNUM
        defaultCheckCzl2ShouldNotBeFound("rnum.notEquals=" + DEFAULT_RNUM);

        // Get all the checkCzl2List where rnum not equals to UPDATED_RNUM
        defaultCheckCzl2ShouldBeFound("rnum.notEquals=" + UPDATED_RNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByRnumIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where rnum in DEFAULT_RNUM or UPDATED_RNUM
        defaultCheckCzl2ShouldBeFound("rnum.in=" + DEFAULT_RNUM + "," + UPDATED_RNUM);

        // Get all the checkCzl2List where rnum equals to UPDATED_RNUM
        defaultCheckCzl2ShouldNotBeFound("rnum.in=" + UPDATED_RNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByRnumIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where rnum is not null
        defaultCheckCzl2ShouldBeFound("rnum.specified=true");

        // Get all the checkCzl2List where rnum is null
        defaultCheckCzl2ShouldNotBeFound("rnum.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByRnumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where rnum is greater than or equal to DEFAULT_RNUM
        defaultCheckCzl2ShouldBeFound("rnum.greaterThanOrEqual=" + DEFAULT_RNUM);

        // Get all the checkCzl2List where rnum is greater than or equal to UPDATED_RNUM
        defaultCheckCzl2ShouldNotBeFound("rnum.greaterThanOrEqual=" + UPDATED_RNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByRnumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where rnum is less than or equal to DEFAULT_RNUM
        defaultCheckCzl2ShouldBeFound("rnum.lessThanOrEqual=" + DEFAULT_RNUM);

        // Get all the checkCzl2List where rnum is less than or equal to SMALLER_RNUM
        defaultCheckCzl2ShouldNotBeFound("rnum.lessThanOrEqual=" + SMALLER_RNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByRnumIsLessThanSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where rnum is less than DEFAULT_RNUM
        defaultCheckCzl2ShouldNotBeFound("rnum.lessThan=" + DEFAULT_RNUM);

        // Get all the checkCzl2List where rnum is less than UPDATED_RNUM
        defaultCheckCzl2ShouldBeFound("rnum.lessThan=" + UPDATED_RNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByRnumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where rnum is greater than DEFAULT_RNUM
        defaultCheckCzl2ShouldNotBeFound("rnum.greaterThan=" + DEFAULT_RNUM);

        // Get all the checkCzl2List where rnum is greater than SMALLER_RNUM
        defaultCheckCzl2ShouldBeFound("rnum.greaterThan=" + SMALLER_RNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByCzlIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where czl equals to DEFAULT_CZL
        defaultCheckCzl2ShouldBeFound("czl.equals=" + DEFAULT_CZL);

        // Get all the checkCzl2List where czl equals to UPDATED_CZL
        defaultCheckCzl2ShouldNotBeFound("czl.equals=" + UPDATED_CZL);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByCzlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where czl not equals to DEFAULT_CZL
        defaultCheckCzl2ShouldNotBeFound("czl.notEquals=" + DEFAULT_CZL);

        // Get all the checkCzl2List where czl not equals to UPDATED_CZL
        defaultCheckCzl2ShouldBeFound("czl.notEquals=" + UPDATED_CZL);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByCzlIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where czl in DEFAULT_CZL or UPDATED_CZL
        defaultCheckCzl2ShouldBeFound("czl.in=" + DEFAULT_CZL + "," + UPDATED_CZL);

        // Get all the checkCzl2List where czl equals to UPDATED_CZL
        defaultCheckCzl2ShouldNotBeFound("czl.in=" + UPDATED_CZL);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByCzlIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where czl is not null
        defaultCheckCzl2ShouldBeFound("czl.specified=true");

        // Get all the checkCzl2List where czl is null
        defaultCheckCzl2ShouldNotBeFound("czl.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByCzlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where czl is greater than or equal to DEFAULT_CZL
        defaultCheckCzl2ShouldBeFound("czl.greaterThanOrEqual=" + DEFAULT_CZL);

        // Get all the checkCzl2List where czl is greater than or equal to UPDATED_CZL
        defaultCheckCzl2ShouldNotBeFound("czl.greaterThanOrEqual=" + UPDATED_CZL);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByCzlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where czl is less than or equal to DEFAULT_CZL
        defaultCheckCzl2ShouldBeFound("czl.lessThanOrEqual=" + DEFAULT_CZL);

        // Get all the checkCzl2List where czl is less than or equal to SMALLER_CZL
        defaultCheckCzl2ShouldNotBeFound("czl.lessThanOrEqual=" + SMALLER_CZL);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByCzlIsLessThanSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where czl is less than DEFAULT_CZL
        defaultCheckCzl2ShouldNotBeFound("czl.lessThan=" + DEFAULT_CZL);

        // Get all the checkCzl2List where czl is less than UPDATED_CZL
        defaultCheckCzl2ShouldBeFound("czl.lessThan=" + UPDATED_CZL);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByCzlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where czl is greater than DEFAULT_CZL
        defaultCheckCzl2ShouldNotBeFound("czl.greaterThan=" + DEFAULT_CZL);

        // Get all the checkCzl2List where czl is greater than SMALLER_CZL
        defaultCheckCzl2ShouldBeFound("czl.greaterThan=" + SMALLER_CZL);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrge equals to DEFAULT_CHAGRGE
        defaultCheckCzl2ShouldBeFound("chagrge.equals=" + DEFAULT_CHAGRGE);

        // Get all the checkCzl2List where chagrge equals to UPDATED_CHAGRGE
        defaultCheckCzl2ShouldNotBeFound("chagrge.equals=" + UPDATED_CHAGRGE);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrge not equals to DEFAULT_CHAGRGE
        defaultCheckCzl2ShouldNotBeFound("chagrge.notEquals=" + DEFAULT_CHAGRGE);

        // Get all the checkCzl2List where chagrge not equals to UPDATED_CHAGRGE
        defaultCheckCzl2ShouldBeFound("chagrge.notEquals=" + UPDATED_CHAGRGE);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrge in DEFAULT_CHAGRGE or UPDATED_CHAGRGE
        defaultCheckCzl2ShouldBeFound("chagrge.in=" + DEFAULT_CHAGRGE + "," + UPDATED_CHAGRGE);

        // Get all the checkCzl2List where chagrge equals to UPDATED_CHAGRGE
        defaultCheckCzl2ShouldNotBeFound("chagrge.in=" + UPDATED_CHAGRGE);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrge is not null
        defaultCheckCzl2ShouldBeFound("chagrge.specified=true");

        // Get all the checkCzl2List where chagrge is null
        defaultCheckCzl2ShouldNotBeFound("chagrge.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrge is greater than or equal to DEFAULT_CHAGRGE
        defaultCheckCzl2ShouldBeFound("chagrge.greaterThanOrEqual=" + DEFAULT_CHAGRGE);

        // Get all the checkCzl2List where chagrge is greater than or equal to UPDATED_CHAGRGE
        defaultCheckCzl2ShouldNotBeFound("chagrge.greaterThanOrEqual=" + UPDATED_CHAGRGE);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrge is less than or equal to DEFAULT_CHAGRGE
        defaultCheckCzl2ShouldBeFound("chagrge.lessThanOrEqual=" + DEFAULT_CHAGRGE);

        // Get all the checkCzl2List where chagrge is less than or equal to SMALLER_CHAGRGE
        defaultCheckCzl2ShouldNotBeFound("chagrge.lessThanOrEqual=" + SMALLER_CHAGRGE);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeIsLessThanSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrge is less than DEFAULT_CHAGRGE
        defaultCheckCzl2ShouldNotBeFound("chagrge.lessThan=" + DEFAULT_CHAGRGE);

        // Get all the checkCzl2List where chagrge is less than UPDATED_CHAGRGE
        defaultCheckCzl2ShouldBeFound("chagrge.lessThan=" + UPDATED_CHAGRGE);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrge is greater than DEFAULT_CHAGRGE
        defaultCheckCzl2ShouldNotBeFound("chagrge.greaterThan=" + DEFAULT_CHAGRGE);

        // Get all the checkCzl2List where chagrge is greater than SMALLER_CHAGRGE
        defaultCheckCzl2ShouldBeFound("chagrge.greaterThan=" + SMALLER_CHAGRGE);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeAvgIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrgeAvg equals to DEFAULT_CHAGRGE_AVG
        defaultCheckCzl2ShouldBeFound("chagrgeAvg.equals=" + DEFAULT_CHAGRGE_AVG);

        // Get all the checkCzl2List where chagrgeAvg equals to UPDATED_CHAGRGE_AVG
        defaultCheckCzl2ShouldNotBeFound("chagrgeAvg.equals=" + UPDATED_CHAGRGE_AVG);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeAvgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrgeAvg not equals to DEFAULT_CHAGRGE_AVG
        defaultCheckCzl2ShouldNotBeFound("chagrgeAvg.notEquals=" + DEFAULT_CHAGRGE_AVG);

        // Get all the checkCzl2List where chagrgeAvg not equals to UPDATED_CHAGRGE_AVG
        defaultCheckCzl2ShouldBeFound("chagrgeAvg.notEquals=" + UPDATED_CHAGRGE_AVG);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeAvgIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrgeAvg in DEFAULT_CHAGRGE_AVG or UPDATED_CHAGRGE_AVG
        defaultCheckCzl2ShouldBeFound("chagrgeAvg.in=" + DEFAULT_CHAGRGE_AVG + "," + UPDATED_CHAGRGE_AVG);

        // Get all the checkCzl2List where chagrgeAvg equals to UPDATED_CHAGRGE_AVG
        defaultCheckCzl2ShouldNotBeFound("chagrgeAvg.in=" + UPDATED_CHAGRGE_AVG);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeAvgIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrgeAvg is not null
        defaultCheckCzl2ShouldBeFound("chagrgeAvg.specified=true");

        // Get all the checkCzl2List where chagrgeAvg is null
        defaultCheckCzl2ShouldNotBeFound("chagrgeAvg.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeAvgIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrgeAvg is greater than or equal to DEFAULT_CHAGRGE_AVG
        defaultCheckCzl2ShouldBeFound("chagrgeAvg.greaterThanOrEqual=" + DEFAULT_CHAGRGE_AVG);

        // Get all the checkCzl2List where chagrgeAvg is greater than or equal to UPDATED_CHAGRGE_AVG
        defaultCheckCzl2ShouldNotBeFound("chagrgeAvg.greaterThanOrEqual=" + UPDATED_CHAGRGE_AVG);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeAvgIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrgeAvg is less than or equal to DEFAULT_CHAGRGE_AVG
        defaultCheckCzl2ShouldBeFound("chagrgeAvg.lessThanOrEqual=" + DEFAULT_CHAGRGE_AVG);

        // Get all the checkCzl2List where chagrgeAvg is less than or equal to SMALLER_CHAGRGE_AVG
        defaultCheckCzl2ShouldNotBeFound("chagrgeAvg.lessThanOrEqual=" + SMALLER_CHAGRGE_AVG);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeAvgIsLessThanSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrgeAvg is less than DEFAULT_CHAGRGE_AVG
        defaultCheckCzl2ShouldNotBeFound("chagrgeAvg.lessThan=" + DEFAULT_CHAGRGE_AVG);

        // Get all the checkCzl2List where chagrgeAvg is less than UPDATED_CHAGRGE_AVG
        defaultCheckCzl2ShouldBeFound("chagrgeAvg.lessThan=" + UPDATED_CHAGRGE_AVG);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByChagrgeAvgIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where chagrgeAvg is greater than DEFAULT_CHAGRGE_AVG
        defaultCheckCzl2ShouldNotBeFound("chagrgeAvg.greaterThan=" + DEFAULT_CHAGRGE_AVG);

        // Get all the checkCzl2List where chagrgeAvg is greater than SMALLER_CHAGRGE_AVG
        defaultCheckCzl2ShouldBeFound("chagrgeAvg.greaterThan=" + SMALLER_CHAGRGE_AVG);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where empn equals to DEFAULT_EMPN
        defaultCheckCzl2ShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the checkCzl2List where empn equals to UPDATED_EMPN
        defaultCheckCzl2ShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where empn not equals to DEFAULT_EMPN
        defaultCheckCzl2ShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the checkCzl2List where empn not equals to UPDATED_EMPN
        defaultCheckCzl2ShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultCheckCzl2ShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the checkCzl2List where empn equals to UPDATED_EMPN
        defaultCheckCzl2ShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where empn is not null
        defaultCheckCzl2ShouldBeFound("empn.specified=true");

        // Get all the checkCzl2List where empn is null
        defaultCheckCzl2ShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByEmpnContainsSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where empn contains DEFAULT_EMPN
        defaultCheckCzl2ShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the checkCzl2List where empn contains UPDATED_EMPN
        defaultCheckCzl2ShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where empn does not contain DEFAULT_EMPN
        defaultCheckCzl2ShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the checkCzl2List where empn does not contain UPDATED_EMPN
        defaultCheckCzl2ShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByEntertimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where entertime equals to DEFAULT_ENTERTIME
        defaultCheckCzl2ShouldBeFound("entertime.equals=" + DEFAULT_ENTERTIME);

        // Get all the checkCzl2List where entertime equals to UPDATED_ENTERTIME
        defaultCheckCzl2ShouldNotBeFound("entertime.equals=" + UPDATED_ENTERTIME);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByEntertimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where entertime not equals to DEFAULT_ENTERTIME
        defaultCheckCzl2ShouldNotBeFound("entertime.notEquals=" + DEFAULT_ENTERTIME);

        // Get all the checkCzl2List where entertime not equals to UPDATED_ENTERTIME
        defaultCheckCzl2ShouldBeFound("entertime.notEquals=" + UPDATED_ENTERTIME);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByEntertimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where entertime in DEFAULT_ENTERTIME or UPDATED_ENTERTIME
        defaultCheckCzl2ShouldBeFound("entertime.in=" + DEFAULT_ENTERTIME + "," + UPDATED_ENTERTIME);

        // Get all the checkCzl2List where entertime equals to UPDATED_ENTERTIME
        defaultCheckCzl2ShouldNotBeFound("entertime.in=" + UPDATED_ENTERTIME);
    }

    @Test
    @Transactional
    void getAllCheckCzl2sByEntertimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        // Get all the checkCzl2List where entertime is not null
        defaultCheckCzl2ShouldBeFound("entertime.specified=true");

        // Get all the checkCzl2List where entertime is null
        defaultCheckCzl2ShouldNotBeFound("entertime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckCzl2ShouldBeFound(String filter) throws Exception {
        restCheckCzl2MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkCzl2.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].protocol").value(hasItem(DEFAULT_PROTOCOL)))
            .andExpect(jsonPath("$.[*].rnum").value(hasItem(DEFAULT_RNUM.intValue())))
            .andExpect(jsonPath("$.[*].czl").value(hasItem(sameNumber(DEFAULT_CZL))))
            .andExpect(jsonPath("$.[*].chagrge").value(hasItem(sameNumber(DEFAULT_CHAGRGE))))
            .andExpect(jsonPath("$.[*].chagrgeAvg").value(hasItem(sameNumber(DEFAULT_CHAGRGE_AVG))))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].entertime").value(hasItem(DEFAULT_ENTERTIME.toString())));

        // Check, that the count call also returns 1
        restCheckCzl2MockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckCzl2ShouldNotBeFound(String filter) throws Exception {
        restCheckCzl2MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckCzl2MockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCheckCzl2() throws Exception {
        // Get the checkCzl2
        restCheckCzl2MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCheckCzl2() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        int databaseSizeBeforeUpdate = checkCzl2Repository.findAll().size();

        // Update the checkCzl2
        CheckCzl2 updatedCheckCzl2 = checkCzl2Repository.findById(checkCzl2.getId()).get();
        // Disconnect from session so that the updates on updatedCheckCzl2 are not directly saved in db
        em.detach(updatedCheckCzl2);
        updatedCheckCzl2
            .hoteltime(UPDATED_HOTELTIME)
            .protocol(UPDATED_PROTOCOL)
            .rnum(UPDATED_RNUM)
            .czl(UPDATED_CZL)
            .chagrge(UPDATED_CHAGRGE)
            .chagrgeAvg(UPDATED_CHAGRGE_AVG)
            .empn(UPDATED_EMPN)
            .entertime(UPDATED_ENTERTIME);
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(updatedCheckCzl2);

        restCheckCzl2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkCzl2DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO))
            )
            .andExpect(status().isOk());

        // Validate the CheckCzl2 in the database
        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeUpdate);
        CheckCzl2 testCheckCzl2 = checkCzl2List.get(checkCzl2List.size() - 1);
        assertThat(testCheckCzl2.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCheckCzl2.getProtocol()).isEqualTo(UPDATED_PROTOCOL);
        assertThat(testCheckCzl2.getRnum()).isEqualTo(UPDATED_RNUM);
        assertThat(testCheckCzl2.getCzl()).isEqualTo(UPDATED_CZL);
        assertThat(testCheckCzl2.getChagrge()).isEqualTo(UPDATED_CHAGRGE);
        assertThat(testCheckCzl2.getChagrgeAvg()).isEqualTo(UPDATED_CHAGRGE_AVG);
        assertThat(testCheckCzl2.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckCzl2.getEntertime()).isEqualTo(UPDATED_ENTERTIME);

        // Validate the CheckCzl2 in Elasticsearch
        verify(mockCheckCzl2SearchRepository).save(testCheckCzl2);
    }

    @Test
    @Transactional
    void putNonExistingCheckCzl2() throws Exception {
        int databaseSizeBeforeUpdate = checkCzl2Repository.findAll().size();
        checkCzl2.setId(count.incrementAndGet());

        // Create the CheckCzl2
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckCzl2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkCzl2DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckCzl2 in the database
        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckCzl2 in Elasticsearch
        verify(mockCheckCzl2SearchRepository, times(0)).save(checkCzl2);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckCzl2() throws Exception {
        int databaseSizeBeforeUpdate = checkCzl2Repository.findAll().size();
        checkCzl2.setId(count.incrementAndGet());

        // Create the CheckCzl2
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckCzl2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckCzl2 in the database
        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckCzl2 in Elasticsearch
        verify(mockCheckCzl2SearchRepository, times(0)).save(checkCzl2);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckCzl2() throws Exception {
        int databaseSizeBeforeUpdate = checkCzl2Repository.findAll().size();
        checkCzl2.setId(count.incrementAndGet());

        // Create the CheckCzl2
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckCzl2MockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckCzl2 in the database
        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckCzl2 in Elasticsearch
        verify(mockCheckCzl2SearchRepository, times(0)).save(checkCzl2);
    }

    @Test
    @Transactional
    void partialUpdateCheckCzl2WithPatch() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        int databaseSizeBeforeUpdate = checkCzl2Repository.findAll().size();

        // Update the checkCzl2 using partial update
        CheckCzl2 partialUpdatedCheckCzl2 = new CheckCzl2();
        partialUpdatedCheckCzl2.setId(checkCzl2.getId());

        partialUpdatedCheckCzl2.hoteltime(UPDATED_HOTELTIME).rnum(UPDATED_RNUM).empn(UPDATED_EMPN).entertime(UPDATED_ENTERTIME);

        restCheckCzl2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckCzl2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckCzl2))
            )
            .andExpect(status().isOk());

        // Validate the CheckCzl2 in the database
        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeUpdate);
        CheckCzl2 testCheckCzl2 = checkCzl2List.get(checkCzl2List.size() - 1);
        assertThat(testCheckCzl2.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCheckCzl2.getProtocol()).isEqualTo(DEFAULT_PROTOCOL);
        assertThat(testCheckCzl2.getRnum()).isEqualTo(UPDATED_RNUM);
        assertThat(testCheckCzl2.getCzl()).isEqualByComparingTo(DEFAULT_CZL);
        assertThat(testCheckCzl2.getChagrge()).isEqualByComparingTo(DEFAULT_CHAGRGE);
        assertThat(testCheckCzl2.getChagrgeAvg()).isEqualByComparingTo(DEFAULT_CHAGRGE_AVG);
        assertThat(testCheckCzl2.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckCzl2.getEntertime()).isEqualTo(UPDATED_ENTERTIME);
    }

    @Test
    @Transactional
    void fullUpdateCheckCzl2WithPatch() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        int databaseSizeBeforeUpdate = checkCzl2Repository.findAll().size();

        // Update the checkCzl2 using partial update
        CheckCzl2 partialUpdatedCheckCzl2 = new CheckCzl2();
        partialUpdatedCheckCzl2.setId(checkCzl2.getId());

        partialUpdatedCheckCzl2
            .hoteltime(UPDATED_HOTELTIME)
            .protocol(UPDATED_PROTOCOL)
            .rnum(UPDATED_RNUM)
            .czl(UPDATED_CZL)
            .chagrge(UPDATED_CHAGRGE)
            .chagrgeAvg(UPDATED_CHAGRGE_AVG)
            .empn(UPDATED_EMPN)
            .entertime(UPDATED_ENTERTIME);

        restCheckCzl2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckCzl2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckCzl2))
            )
            .andExpect(status().isOk());

        // Validate the CheckCzl2 in the database
        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeUpdate);
        CheckCzl2 testCheckCzl2 = checkCzl2List.get(checkCzl2List.size() - 1);
        assertThat(testCheckCzl2.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCheckCzl2.getProtocol()).isEqualTo(UPDATED_PROTOCOL);
        assertThat(testCheckCzl2.getRnum()).isEqualTo(UPDATED_RNUM);
        assertThat(testCheckCzl2.getCzl()).isEqualByComparingTo(UPDATED_CZL);
        assertThat(testCheckCzl2.getChagrge()).isEqualByComparingTo(UPDATED_CHAGRGE);
        assertThat(testCheckCzl2.getChagrgeAvg()).isEqualByComparingTo(UPDATED_CHAGRGE_AVG);
        assertThat(testCheckCzl2.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckCzl2.getEntertime()).isEqualTo(UPDATED_ENTERTIME);
    }

    @Test
    @Transactional
    void patchNonExistingCheckCzl2() throws Exception {
        int databaseSizeBeforeUpdate = checkCzl2Repository.findAll().size();
        checkCzl2.setId(count.incrementAndGet());

        // Create the CheckCzl2
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckCzl2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkCzl2DTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckCzl2 in the database
        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckCzl2 in Elasticsearch
        verify(mockCheckCzl2SearchRepository, times(0)).save(checkCzl2);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckCzl2() throws Exception {
        int databaseSizeBeforeUpdate = checkCzl2Repository.findAll().size();
        checkCzl2.setId(count.incrementAndGet());

        // Create the CheckCzl2
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckCzl2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckCzl2 in the database
        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckCzl2 in Elasticsearch
        verify(mockCheckCzl2SearchRepository, times(0)).save(checkCzl2);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckCzl2() throws Exception {
        int databaseSizeBeforeUpdate = checkCzl2Repository.findAll().size();
        checkCzl2.setId(count.incrementAndGet());

        // Create the CheckCzl2
        CheckCzl2DTO checkCzl2DTO = checkCzl2Mapper.toDto(checkCzl2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckCzl2MockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(checkCzl2DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckCzl2 in the database
        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckCzl2 in Elasticsearch
        verify(mockCheckCzl2SearchRepository, times(0)).save(checkCzl2);
    }

    @Test
    @Transactional
    void deleteCheckCzl2() throws Exception {
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);

        int databaseSizeBeforeDelete = checkCzl2Repository.findAll().size();

        // Delete the checkCzl2
        restCheckCzl2MockMvc
            .perform(delete(ENTITY_API_URL_ID, checkCzl2.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckCzl2> checkCzl2List = checkCzl2Repository.findAll();
        assertThat(checkCzl2List).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CheckCzl2 in Elasticsearch
        verify(mockCheckCzl2SearchRepository, times(1)).deleteById(checkCzl2.getId());
    }

    @Test
    @Transactional
    void searchCheckCzl2() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        checkCzl2Repository.saveAndFlush(checkCzl2);
        when(mockCheckCzl2SearchRepository.search(queryStringQuery("id:" + checkCzl2.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(checkCzl2), PageRequest.of(0, 1), 1));

        // Search the checkCzl2
        restCheckCzl2MockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + checkCzl2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkCzl2.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].protocol").value(hasItem(DEFAULT_PROTOCOL)))
            .andExpect(jsonPath("$.[*].rnum").value(hasItem(DEFAULT_RNUM.intValue())))
            .andExpect(jsonPath("$.[*].czl").value(hasItem(sameNumber(DEFAULT_CZL))))
            .andExpect(jsonPath("$.[*].chagrge").value(hasItem(sameNumber(DEFAULT_CHAGRGE))))
            .andExpect(jsonPath("$.[*].chagrgeAvg").value(hasItem(sameNumber(DEFAULT_CHAGRGE_AVG))))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].entertime").value(hasItem(DEFAULT_ENTERTIME.toString())));
    }
}
