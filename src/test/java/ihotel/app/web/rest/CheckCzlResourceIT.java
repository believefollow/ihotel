package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.CheckCzl;
import ihotel.app.repository.CheckCzlRepository;
import ihotel.app.repository.search.CheckCzlSearchRepository;
import ihotel.app.service.criteria.CheckCzlCriteria;
import ihotel.app.service.dto.CheckCzlDTO;
import ihotel.app.service.mapper.CheckCzlMapper;
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
 * Integration tests for the {@link CheckCzlResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CheckCzlResourceIT {

    private static final Instant DEFAULT_HOTELTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOTELTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RTYPE = "AAAAAAAAAA";
    private static final String UPDATED_RTYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_RNUM = 1L;
    private static final Long UPDATED_RNUM = 2L;
    private static final Long SMALLER_RNUM = 1L - 1L;

    private static final Long DEFAULT_R_OUTNUM = 1L;
    private static final Long UPDATED_R_OUTNUM = 2L;
    private static final Long SMALLER_R_OUTNUM = 1L - 1L;

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

    private static final String ENTITY_API_URL = "/api/check-czls";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/check-czls";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CheckCzlRepository checkCzlRepository;

    @Autowired
    private CheckCzlMapper checkCzlMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CheckCzlSearchRepositoryMockConfiguration
     */
    @Autowired
    private CheckCzlSearchRepository mockCheckCzlSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckCzlMockMvc;

    private CheckCzl checkCzl;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckCzl createEntity(EntityManager em) {
        CheckCzl checkCzl = new CheckCzl()
            .hoteltime(DEFAULT_HOTELTIME)
            .rtype(DEFAULT_RTYPE)
            .rnum(DEFAULT_RNUM)
            .rOutnum(DEFAULT_R_OUTNUM)
            .czl(DEFAULT_CZL)
            .chagrge(DEFAULT_CHAGRGE)
            .chagrgeAvg(DEFAULT_CHAGRGE_AVG)
            .empn(DEFAULT_EMPN)
            .entertime(DEFAULT_ENTERTIME);
        return checkCzl;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckCzl createUpdatedEntity(EntityManager em) {
        CheckCzl checkCzl = new CheckCzl()
            .hoteltime(UPDATED_HOTELTIME)
            .rtype(UPDATED_RTYPE)
            .rnum(UPDATED_RNUM)
            .rOutnum(UPDATED_R_OUTNUM)
            .czl(UPDATED_CZL)
            .chagrge(UPDATED_CHAGRGE)
            .chagrgeAvg(UPDATED_CHAGRGE_AVG)
            .empn(UPDATED_EMPN)
            .entertime(UPDATED_ENTERTIME);
        return checkCzl;
    }

    @BeforeEach
    public void initTest() {
        checkCzl = createEntity(em);
    }

    @Test
    @Transactional
    void createCheckCzl() throws Exception {
        int databaseSizeBeforeCreate = checkCzlRepository.findAll().size();
        // Create the CheckCzl
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);
        restCheckCzlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzlDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckCzl in the database
        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeCreate + 1);
        CheckCzl testCheckCzl = checkCzlList.get(checkCzlList.size() - 1);
        assertThat(testCheckCzl.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testCheckCzl.getRtype()).isEqualTo(DEFAULT_RTYPE);
        assertThat(testCheckCzl.getRnum()).isEqualTo(DEFAULT_RNUM);
        assertThat(testCheckCzl.getrOutnum()).isEqualTo(DEFAULT_R_OUTNUM);
        assertThat(testCheckCzl.getCzl()).isEqualByComparingTo(DEFAULT_CZL);
        assertThat(testCheckCzl.getChagrge()).isEqualByComparingTo(DEFAULT_CHAGRGE);
        assertThat(testCheckCzl.getChagrgeAvg()).isEqualByComparingTo(DEFAULT_CHAGRGE_AVG);
        assertThat(testCheckCzl.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testCheckCzl.getEntertime()).isEqualTo(DEFAULT_ENTERTIME);

        // Validate the CheckCzl in Elasticsearch
        verify(mockCheckCzlSearchRepository, times(1)).save(testCheckCzl);
    }

    @Test
    @Transactional
    void createCheckCzlWithExistingId() throws Exception {
        // Create the CheckCzl with an existing ID
        checkCzl.setId(1L);
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        int databaseSizeBeforeCreate = checkCzlRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckCzlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzlDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckCzl in the database
        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeCreate);

        // Validate the CheckCzl in Elasticsearch
        verify(mockCheckCzlSearchRepository, times(0)).save(checkCzl);
    }

    @Test
    @Transactional
    void checkHoteltimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzlRepository.findAll().size();
        // set the field null
        checkCzl.setHoteltime(null);

        // Create the CheckCzl, which fails.
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        restCheckCzlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzlDTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRtypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzlRepository.findAll().size();
        // set the field null
        checkCzl.setRtype(null);

        // Create the CheckCzl, which fails.
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        restCheckCzlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzlDTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRnumIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzlRepository.findAll().size();
        // set the field null
        checkCzl.setRnum(null);

        // Create the CheckCzl, which fails.
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        restCheckCzlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzlDTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkrOutnumIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzlRepository.findAll().size();
        // set the field null
        checkCzl.setrOutnum(null);

        // Create the CheckCzl, which fails.
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        restCheckCzlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzlDTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCzlIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzlRepository.findAll().size();
        // set the field null
        checkCzl.setCzl(null);

        // Create the CheckCzl, which fails.
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        restCheckCzlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzlDTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChagrgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzlRepository.findAll().size();
        // set the field null
        checkCzl.setChagrge(null);

        // Create the CheckCzl, which fails.
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        restCheckCzlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzlDTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChagrgeAvgIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzlRepository.findAll().size();
        // set the field null
        checkCzl.setChagrgeAvg(null);

        // Create the CheckCzl, which fails.
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        restCheckCzlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzlDTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmpnIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzlRepository.findAll().size();
        // set the field null
        checkCzl.setEmpn(null);

        // Create the CheckCzl, which fails.
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        restCheckCzlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzlDTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEntertimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkCzlRepository.findAll().size();
        // set the field null
        checkCzl.setEntertime(null);

        // Create the CheckCzl, which fails.
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        restCheckCzlMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzlDTO)))
            .andExpect(status().isBadRequest());

        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCheckCzls() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList
        restCheckCzlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkCzl.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].rtype").value(hasItem(DEFAULT_RTYPE)))
            .andExpect(jsonPath("$.[*].rnum").value(hasItem(DEFAULT_RNUM.intValue())))
            .andExpect(jsonPath("$.[*].rOutnum").value(hasItem(DEFAULT_R_OUTNUM.intValue())))
            .andExpect(jsonPath("$.[*].czl").value(hasItem(sameNumber(DEFAULT_CZL))))
            .andExpect(jsonPath("$.[*].chagrge").value(hasItem(sameNumber(DEFAULT_CHAGRGE))))
            .andExpect(jsonPath("$.[*].chagrgeAvg").value(hasItem(sameNumber(DEFAULT_CHAGRGE_AVG))))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].entertime").value(hasItem(DEFAULT_ENTERTIME.toString())));
    }

    @Test
    @Transactional
    void getCheckCzl() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get the checkCzl
        restCheckCzlMockMvc
            .perform(get(ENTITY_API_URL_ID, checkCzl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkCzl.getId().intValue()))
            .andExpect(jsonPath("$.hoteltime").value(DEFAULT_HOTELTIME.toString()))
            .andExpect(jsonPath("$.rtype").value(DEFAULT_RTYPE))
            .andExpect(jsonPath("$.rnum").value(DEFAULT_RNUM.intValue()))
            .andExpect(jsonPath("$.rOutnum").value(DEFAULT_R_OUTNUM.intValue()))
            .andExpect(jsonPath("$.czl").value(sameNumber(DEFAULT_CZL)))
            .andExpect(jsonPath("$.chagrge").value(sameNumber(DEFAULT_CHAGRGE)))
            .andExpect(jsonPath("$.chagrgeAvg").value(sameNumber(DEFAULT_CHAGRGE_AVG)))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.entertime").value(DEFAULT_ENTERTIME.toString()));
    }

    @Test
    @Transactional
    void getCheckCzlsByIdFiltering() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        Long id = checkCzl.getId();

        defaultCheckCzlShouldBeFound("id.equals=" + id);
        defaultCheckCzlShouldNotBeFound("id.notEquals=" + id);

        defaultCheckCzlShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCheckCzlShouldNotBeFound("id.greaterThan=" + id);

        defaultCheckCzlShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCheckCzlShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByHoteltimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where hoteltime equals to DEFAULT_HOTELTIME
        defaultCheckCzlShouldBeFound("hoteltime.equals=" + DEFAULT_HOTELTIME);

        // Get all the checkCzlList where hoteltime equals to UPDATED_HOTELTIME
        defaultCheckCzlShouldNotBeFound("hoteltime.equals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByHoteltimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where hoteltime not equals to DEFAULT_HOTELTIME
        defaultCheckCzlShouldNotBeFound("hoteltime.notEquals=" + DEFAULT_HOTELTIME);

        // Get all the checkCzlList where hoteltime not equals to UPDATED_HOTELTIME
        defaultCheckCzlShouldBeFound("hoteltime.notEquals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByHoteltimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where hoteltime in DEFAULT_HOTELTIME or UPDATED_HOTELTIME
        defaultCheckCzlShouldBeFound("hoteltime.in=" + DEFAULT_HOTELTIME + "," + UPDATED_HOTELTIME);

        // Get all the checkCzlList where hoteltime equals to UPDATED_HOTELTIME
        defaultCheckCzlShouldNotBeFound("hoteltime.in=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByHoteltimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where hoteltime is not null
        defaultCheckCzlShouldBeFound("hoteltime.specified=true");

        // Get all the checkCzlList where hoteltime is null
        defaultCheckCzlShouldNotBeFound("hoteltime.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzlsByRtypeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rtype equals to DEFAULT_RTYPE
        defaultCheckCzlShouldBeFound("rtype.equals=" + DEFAULT_RTYPE);

        // Get all the checkCzlList where rtype equals to UPDATED_RTYPE
        defaultCheckCzlShouldNotBeFound("rtype.equals=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByRtypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rtype not equals to DEFAULT_RTYPE
        defaultCheckCzlShouldNotBeFound("rtype.notEquals=" + DEFAULT_RTYPE);

        // Get all the checkCzlList where rtype not equals to UPDATED_RTYPE
        defaultCheckCzlShouldBeFound("rtype.notEquals=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByRtypeIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rtype in DEFAULT_RTYPE or UPDATED_RTYPE
        defaultCheckCzlShouldBeFound("rtype.in=" + DEFAULT_RTYPE + "," + UPDATED_RTYPE);

        // Get all the checkCzlList where rtype equals to UPDATED_RTYPE
        defaultCheckCzlShouldNotBeFound("rtype.in=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByRtypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rtype is not null
        defaultCheckCzlShouldBeFound("rtype.specified=true");

        // Get all the checkCzlList where rtype is null
        defaultCheckCzlShouldNotBeFound("rtype.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzlsByRtypeContainsSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rtype contains DEFAULT_RTYPE
        defaultCheckCzlShouldBeFound("rtype.contains=" + DEFAULT_RTYPE);

        // Get all the checkCzlList where rtype contains UPDATED_RTYPE
        defaultCheckCzlShouldNotBeFound("rtype.contains=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByRtypeNotContainsSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rtype does not contain DEFAULT_RTYPE
        defaultCheckCzlShouldNotBeFound("rtype.doesNotContain=" + DEFAULT_RTYPE);

        // Get all the checkCzlList where rtype does not contain UPDATED_RTYPE
        defaultCheckCzlShouldBeFound("rtype.doesNotContain=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByRnumIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rnum equals to DEFAULT_RNUM
        defaultCheckCzlShouldBeFound("rnum.equals=" + DEFAULT_RNUM);

        // Get all the checkCzlList where rnum equals to UPDATED_RNUM
        defaultCheckCzlShouldNotBeFound("rnum.equals=" + UPDATED_RNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByRnumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rnum not equals to DEFAULT_RNUM
        defaultCheckCzlShouldNotBeFound("rnum.notEquals=" + DEFAULT_RNUM);

        // Get all the checkCzlList where rnum not equals to UPDATED_RNUM
        defaultCheckCzlShouldBeFound("rnum.notEquals=" + UPDATED_RNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByRnumIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rnum in DEFAULT_RNUM or UPDATED_RNUM
        defaultCheckCzlShouldBeFound("rnum.in=" + DEFAULT_RNUM + "," + UPDATED_RNUM);

        // Get all the checkCzlList where rnum equals to UPDATED_RNUM
        defaultCheckCzlShouldNotBeFound("rnum.in=" + UPDATED_RNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByRnumIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rnum is not null
        defaultCheckCzlShouldBeFound("rnum.specified=true");

        // Get all the checkCzlList where rnum is null
        defaultCheckCzlShouldNotBeFound("rnum.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzlsByRnumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rnum is greater than or equal to DEFAULT_RNUM
        defaultCheckCzlShouldBeFound("rnum.greaterThanOrEqual=" + DEFAULT_RNUM);

        // Get all the checkCzlList where rnum is greater than or equal to UPDATED_RNUM
        defaultCheckCzlShouldNotBeFound("rnum.greaterThanOrEqual=" + UPDATED_RNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByRnumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rnum is less than or equal to DEFAULT_RNUM
        defaultCheckCzlShouldBeFound("rnum.lessThanOrEqual=" + DEFAULT_RNUM);

        // Get all the checkCzlList where rnum is less than or equal to SMALLER_RNUM
        defaultCheckCzlShouldNotBeFound("rnum.lessThanOrEqual=" + SMALLER_RNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByRnumIsLessThanSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rnum is less than DEFAULT_RNUM
        defaultCheckCzlShouldNotBeFound("rnum.lessThan=" + DEFAULT_RNUM);

        // Get all the checkCzlList where rnum is less than UPDATED_RNUM
        defaultCheckCzlShouldBeFound("rnum.lessThan=" + UPDATED_RNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByRnumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rnum is greater than DEFAULT_RNUM
        defaultCheckCzlShouldNotBeFound("rnum.greaterThan=" + DEFAULT_RNUM);

        // Get all the checkCzlList where rnum is greater than SMALLER_RNUM
        defaultCheckCzlShouldBeFound("rnum.greaterThan=" + SMALLER_RNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByrOutnumIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rOutnum equals to DEFAULT_R_OUTNUM
        defaultCheckCzlShouldBeFound("rOutnum.equals=" + DEFAULT_R_OUTNUM);

        // Get all the checkCzlList where rOutnum equals to UPDATED_R_OUTNUM
        defaultCheckCzlShouldNotBeFound("rOutnum.equals=" + UPDATED_R_OUTNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByrOutnumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rOutnum not equals to DEFAULT_R_OUTNUM
        defaultCheckCzlShouldNotBeFound("rOutnum.notEquals=" + DEFAULT_R_OUTNUM);

        // Get all the checkCzlList where rOutnum not equals to UPDATED_R_OUTNUM
        defaultCheckCzlShouldBeFound("rOutnum.notEquals=" + UPDATED_R_OUTNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByrOutnumIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rOutnum in DEFAULT_R_OUTNUM or UPDATED_R_OUTNUM
        defaultCheckCzlShouldBeFound("rOutnum.in=" + DEFAULT_R_OUTNUM + "," + UPDATED_R_OUTNUM);

        // Get all the checkCzlList where rOutnum equals to UPDATED_R_OUTNUM
        defaultCheckCzlShouldNotBeFound("rOutnum.in=" + UPDATED_R_OUTNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByrOutnumIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rOutnum is not null
        defaultCheckCzlShouldBeFound("rOutnum.specified=true");

        // Get all the checkCzlList where rOutnum is null
        defaultCheckCzlShouldNotBeFound("rOutnum.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzlsByrOutnumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rOutnum is greater than or equal to DEFAULT_R_OUTNUM
        defaultCheckCzlShouldBeFound("rOutnum.greaterThanOrEqual=" + DEFAULT_R_OUTNUM);

        // Get all the checkCzlList where rOutnum is greater than or equal to UPDATED_R_OUTNUM
        defaultCheckCzlShouldNotBeFound("rOutnum.greaterThanOrEqual=" + UPDATED_R_OUTNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByrOutnumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rOutnum is less than or equal to DEFAULT_R_OUTNUM
        defaultCheckCzlShouldBeFound("rOutnum.lessThanOrEqual=" + DEFAULT_R_OUTNUM);

        // Get all the checkCzlList where rOutnum is less than or equal to SMALLER_R_OUTNUM
        defaultCheckCzlShouldNotBeFound("rOutnum.lessThanOrEqual=" + SMALLER_R_OUTNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByrOutnumIsLessThanSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rOutnum is less than DEFAULT_R_OUTNUM
        defaultCheckCzlShouldNotBeFound("rOutnum.lessThan=" + DEFAULT_R_OUTNUM);

        // Get all the checkCzlList where rOutnum is less than UPDATED_R_OUTNUM
        defaultCheckCzlShouldBeFound("rOutnum.lessThan=" + UPDATED_R_OUTNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByrOutnumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where rOutnum is greater than DEFAULT_R_OUTNUM
        defaultCheckCzlShouldNotBeFound("rOutnum.greaterThan=" + DEFAULT_R_OUTNUM);

        // Get all the checkCzlList where rOutnum is greater than SMALLER_R_OUTNUM
        defaultCheckCzlShouldBeFound("rOutnum.greaterThan=" + SMALLER_R_OUTNUM);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByCzlIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where czl equals to DEFAULT_CZL
        defaultCheckCzlShouldBeFound("czl.equals=" + DEFAULT_CZL);

        // Get all the checkCzlList where czl equals to UPDATED_CZL
        defaultCheckCzlShouldNotBeFound("czl.equals=" + UPDATED_CZL);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByCzlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where czl not equals to DEFAULT_CZL
        defaultCheckCzlShouldNotBeFound("czl.notEquals=" + DEFAULT_CZL);

        // Get all the checkCzlList where czl not equals to UPDATED_CZL
        defaultCheckCzlShouldBeFound("czl.notEquals=" + UPDATED_CZL);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByCzlIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where czl in DEFAULT_CZL or UPDATED_CZL
        defaultCheckCzlShouldBeFound("czl.in=" + DEFAULT_CZL + "," + UPDATED_CZL);

        // Get all the checkCzlList where czl equals to UPDATED_CZL
        defaultCheckCzlShouldNotBeFound("czl.in=" + UPDATED_CZL);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByCzlIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where czl is not null
        defaultCheckCzlShouldBeFound("czl.specified=true");

        // Get all the checkCzlList where czl is null
        defaultCheckCzlShouldNotBeFound("czl.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzlsByCzlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where czl is greater than or equal to DEFAULT_CZL
        defaultCheckCzlShouldBeFound("czl.greaterThanOrEqual=" + DEFAULT_CZL);

        // Get all the checkCzlList where czl is greater than or equal to UPDATED_CZL
        defaultCheckCzlShouldNotBeFound("czl.greaterThanOrEqual=" + UPDATED_CZL);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByCzlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where czl is less than or equal to DEFAULT_CZL
        defaultCheckCzlShouldBeFound("czl.lessThanOrEqual=" + DEFAULT_CZL);

        // Get all the checkCzlList where czl is less than or equal to SMALLER_CZL
        defaultCheckCzlShouldNotBeFound("czl.lessThanOrEqual=" + SMALLER_CZL);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByCzlIsLessThanSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where czl is less than DEFAULT_CZL
        defaultCheckCzlShouldNotBeFound("czl.lessThan=" + DEFAULT_CZL);

        // Get all the checkCzlList where czl is less than UPDATED_CZL
        defaultCheckCzlShouldBeFound("czl.lessThan=" + UPDATED_CZL);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByCzlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where czl is greater than DEFAULT_CZL
        defaultCheckCzlShouldNotBeFound("czl.greaterThan=" + DEFAULT_CZL);

        // Get all the checkCzlList where czl is greater than SMALLER_CZL
        defaultCheckCzlShouldBeFound("czl.greaterThan=" + SMALLER_CZL);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrge equals to DEFAULT_CHAGRGE
        defaultCheckCzlShouldBeFound("chagrge.equals=" + DEFAULT_CHAGRGE);

        // Get all the checkCzlList where chagrge equals to UPDATED_CHAGRGE
        defaultCheckCzlShouldNotBeFound("chagrge.equals=" + UPDATED_CHAGRGE);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrge not equals to DEFAULT_CHAGRGE
        defaultCheckCzlShouldNotBeFound("chagrge.notEquals=" + DEFAULT_CHAGRGE);

        // Get all the checkCzlList where chagrge not equals to UPDATED_CHAGRGE
        defaultCheckCzlShouldBeFound("chagrge.notEquals=" + UPDATED_CHAGRGE);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrge in DEFAULT_CHAGRGE or UPDATED_CHAGRGE
        defaultCheckCzlShouldBeFound("chagrge.in=" + DEFAULT_CHAGRGE + "," + UPDATED_CHAGRGE);

        // Get all the checkCzlList where chagrge equals to UPDATED_CHAGRGE
        defaultCheckCzlShouldNotBeFound("chagrge.in=" + UPDATED_CHAGRGE);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrge is not null
        defaultCheckCzlShouldBeFound("chagrge.specified=true");

        // Get all the checkCzlList where chagrge is null
        defaultCheckCzlShouldNotBeFound("chagrge.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrge is greater than or equal to DEFAULT_CHAGRGE
        defaultCheckCzlShouldBeFound("chagrge.greaterThanOrEqual=" + DEFAULT_CHAGRGE);

        // Get all the checkCzlList where chagrge is greater than or equal to UPDATED_CHAGRGE
        defaultCheckCzlShouldNotBeFound("chagrge.greaterThanOrEqual=" + UPDATED_CHAGRGE);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrge is less than or equal to DEFAULT_CHAGRGE
        defaultCheckCzlShouldBeFound("chagrge.lessThanOrEqual=" + DEFAULT_CHAGRGE);

        // Get all the checkCzlList where chagrge is less than or equal to SMALLER_CHAGRGE
        defaultCheckCzlShouldNotBeFound("chagrge.lessThanOrEqual=" + SMALLER_CHAGRGE);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeIsLessThanSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrge is less than DEFAULT_CHAGRGE
        defaultCheckCzlShouldNotBeFound("chagrge.lessThan=" + DEFAULT_CHAGRGE);

        // Get all the checkCzlList where chagrge is less than UPDATED_CHAGRGE
        defaultCheckCzlShouldBeFound("chagrge.lessThan=" + UPDATED_CHAGRGE);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrge is greater than DEFAULT_CHAGRGE
        defaultCheckCzlShouldNotBeFound("chagrge.greaterThan=" + DEFAULT_CHAGRGE);

        // Get all the checkCzlList where chagrge is greater than SMALLER_CHAGRGE
        defaultCheckCzlShouldBeFound("chagrge.greaterThan=" + SMALLER_CHAGRGE);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeAvgIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrgeAvg equals to DEFAULT_CHAGRGE_AVG
        defaultCheckCzlShouldBeFound("chagrgeAvg.equals=" + DEFAULT_CHAGRGE_AVG);

        // Get all the checkCzlList where chagrgeAvg equals to UPDATED_CHAGRGE_AVG
        defaultCheckCzlShouldNotBeFound("chagrgeAvg.equals=" + UPDATED_CHAGRGE_AVG);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeAvgIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrgeAvg not equals to DEFAULT_CHAGRGE_AVG
        defaultCheckCzlShouldNotBeFound("chagrgeAvg.notEquals=" + DEFAULT_CHAGRGE_AVG);

        // Get all the checkCzlList where chagrgeAvg not equals to UPDATED_CHAGRGE_AVG
        defaultCheckCzlShouldBeFound("chagrgeAvg.notEquals=" + UPDATED_CHAGRGE_AVG);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeAvgIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrgeAvg in DEFAULT_CHAGRGE_AVG or UPDATED_CHAGRGE_AVG
        defaultCheckCzlShouldBeFound("chagrgeAvg.in=" + DEFAULT_CHAGRGE_AVG + "," + UPDATED_CHAGRGE_AVG);

        // Get all the checkCzlList where chagrgeAvg equals to UPDATED_CHAGRGE_AVG
        defaultCheckCzlShouldNotBeFound("chagrgeAvg.in=" + UPDATED_CHAGRGE_AVG);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeAvgIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrgeAvg is not null
        defaultCheckCzlShouldBeFound("chagrgeAvg.specified=true");

        // Get all the checkCzlList where chagrgeAvg is null
        defaultCheckCzlShouldNotBeFound("chagrgeAvg.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeAvgIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrgeAvg is greater than or equal to DEFAULT_CHAGRGE_AVG
        defaultCheckCzlShouldBeFound("chagrgeAvg.greaterThanOrEqual=" + DEFAULT_CHAGRGE_AVG);

        // Get all the checkCzlList where chagrgeAvg is greater than or equal to UPDATED_CHAGRGE_AVG
        defaultCheckCzlShouldNotBeFound("chagrgeAvg.greaterThanOrEqual=" + UPDATED_CHAGRGE_AVG);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeAvgIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrgeAvg is less than or equal to DEFAULT_CHAGRGE_AVG
        defaultCheckCzlShouldBeFound("chagrgeAvg.lessThanOrEqual=" + DEFAULT_CHAGRGE_AVG);

        // Get all the checkCzlList where chagrgeAvg is less than or equal to SMALLER_CHAGRGE_AVG
        defaultCheckCzlShouldNotBeFound("chagrgeAvg.lessThanOrEqual=" + SMALLER_CHAGRGE_AVG);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeAvgIsLessThanSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrgeAvg is less than DEFAULT_CHAGRGE_AVG
        defaultCheckCzlShouldNotBeFound("chagrgeAvg.lessThan=" + DEFAULT_CHAGRGE_AVG);

        // Get all the checkCzlList where chagrgeAvg is less than UPDATED_CHAGRGE_AVG
        defaultCheckCzlShouldBeFound("chagrgeAvg.lessThan=" + UPDATED_CHAGRGE_AVG);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByChagrgeAvgIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where chagrgeAvg is greater than DEFAULT_CHAGRGE_AVG
        defaultCheckCzlShouldNotBeFound("chagrgeAvg.greaterThan=" + DEFAULT_CHAGRGE_AVG);

        // Get all the checkCzlList where chagrgeAvg is greater than SMALLER_CHAGRGE_AVG
        defaultCheckCzlShouldBeFound("chagrgeAvg.greaterThan=" + SMALLER_CHAGRGE_AVG);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where empn equals to DEFAULT_EMPN
        defaultCheckCzlShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the checkCzlList where empn equals to UPDATED_EMPN
        defaultCheckCzlShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where empn not equals to DEFAULT_EMPN
        defaultCheckCzlShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the checkCzlList where empn not equals to UPDATED_EMPN
        defaultCheckCzlShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultCheckCzlShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the checkCzlList where empn equals to UPDATED_EMPN
        defaultCheckCzlShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where empn is not null
        defaultCheckCzlShouldBeFound("empn.specified=true");

        // Get all the checkCzlList where empn is null
        defaultCheckCzlShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckCzlsByEmpnContainsSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where empn contains DEFAULT_EMPN
        defaultCheckCzlShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the checkCzlList where empn contains UPDATED_EMPN
        defaultCheckCzlShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where empn does not contain DEFAULT_EMPN
        defaultCheckCzlShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the checkCzlList where empn does not contain UPDATED_EMPN
        defaultCheckCzlShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByEntertimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where entertime equals to DEFAULT_ENTERTIME
        defaultCheckCzlShouldBeFound("entertime.equals=" + DEFAULT_ENTERTIME);

        // Get all the checkCzlList where entertime equals to UPDATED_ENTERTIME
        defaultCheckCzlShouldNotBeFound("entertime.equals=" + UPDATED_ENTERTIME);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByEntertimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where entertime not equals to DEFAULT_ENTERTIME
        defaultCheckCzlShouldNotBeFound("entertime.notEquals=" + DEFAULT_ENTERTIME);

        // Get all the checkCzlList where entertime not equals to UPDATED_ENTERTIME
        defaultCheckCzlShouldBeFound("entertime.notEquals=" + UPDATED_ENTERTIME);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByEntertimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where entertime in DEFAULT_ENTERTIME or UPDATED_ENTERTIME
        defaultCheckCzlShouldBeFound("entertime.in=" + DEFAULT_ENTERTIME + "," + UPDATED_ENTERTIME);

        // Get all the checkCzlList where entertime equals to UPDATED_ENTERTIME
        defaultCheckCzlShouldNotBeFound("entertime.in=" + UPDATED_ENTERTIME);
    }

    @Test
    @Transactional
    void getAllCheckCzlsByEntertimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        // Get all the checkCzlList where entertime is not null
        defaultCheckCzlShouldBeFound("entertime.specified=true");

        // Get all the checkCzlList where entertime is null
        defaultCheckCzlShouldNotBeFound("entertime.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckCzlShouldBeFound(String filter) throws Exception {
        restCheckCzlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkCzl.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].rtype").value(hasItem(DEFAULT_RTYPE)))
            .andExpect(jsonPath("$.[*].rnum").value(hasItem(DEFAULT_RNUM.intValue())))
            .andExpect(jsonPath("$.[*].rOutnum").value(hasItem(DEFAULT_R_OUTNUM.intValue())))
            .andExpect(jsonPath("$.[*].czl").value(hasItem(sameNumber(DEFAULT_CZL))))
            .andExpect(jsonPath("$.[*].chagrge").value(hasItem(sameNumber(DEFAULT_CHAGRGE))))
            .andExpect(jsonPath("$.[*].chagrgeAvg").value(hasItem(sameNumber(DEFAULT_CHAGRGE_AVG))))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].entertime").value(hasItem(DEFAULT_ENTERTIME.toString())));

        // Check, that the count call also returns 1
        restCheckCzlMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckCzlShouldNotBeFound(String filter) throws Exception {
        restCheckCzlMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckCzlMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCheckCzl() throws Exception {
        // Get the checkCzl
        restCheckCzlMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCheckCzl() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        int databaseSizeBeforeUpdate = checkCzlRepository.findAll().size();

        // Update the checkCzl
        CheckCzl updatedCheckCzl = checkCzlRepository.findById(checkCzl.getId()).get();
        // Disconnect from session so that the updates on updatedCheckCzl are not directly saved in db
        em.detach(updatedCheckCzl);
        updatedCheckCzl
            .hoteltime(UPDATED_HOTELTIME)
            .rtype(UPDATED_RTYPE)
            .rnum(UPDATED_RNUM)
            .rOutnum(UPDATED_R_OUTNUM)
            .czl(UPDATED_CZL)
            .chagrge(UPDATED_CHAGRGE)
            .chagrgeAvg(UPDATED_CHAGRGE_AVG)
            .empn(UPDATED_EMPN)
            .entertime(UPDATED_ENTERTIME);
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(updatedCheckCzl);

        restCheckCzlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkCzlDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkCzlDTO))
            )
            .andExpect(status().isOk());

        // Validate the CheckCzl in the database
        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeUpdate);
        CheckCzl testCheckCzl = checkCzlList.get(checkCzlList.size() - 1);
        assertThat(testCheckCzl.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCheckCzl.getRtype()).isEqualTo(UPDATED_RTYPE);
        assertThat(testCheckCzl.getRnum()).isEqualTo(UPDATED_RNUM);
        assertThat(testCheckCzl.getrOutnum()).isEqualTo(UPDATED_R_OUTNUM);
        assertThat(testCheckCzl.getCzl()).isEqualTo(UPDATED_CZL);
        assertThat(testCheckCzl.getChagrge()).isEqualTo(UPDATED_CHAGRGE);
        assertThat(testCheckCzl.getChagrgeAvg()).isEqualTo(UPDATED_CHAGRGE_AVG);
        assertThat(testCheckCzl.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckCzl.getEntertime()).isEqualTo(UPDATED_ENTERTIME);

        // Validate the CheckCzl in Elasticsearch
        verify(mockCheckCzlSearchRepository).save(testCheckCzl);
    }

    @Test
    @Transactional
    void putNonExistingCheckCzl() throws Exception {
        int databaseSizeBeforeUpdate = checkCzlRepository.findAll().size();
        checkCzl.setId(count.incrementAndGet());

        // Create the CheckCzl
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckCzlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkCzlDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkCzlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckCzl in the database
        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckCzl in Elasticsearch
        verify(mockCheckCzlSearchRepository, times(0)).save(checkCzl);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckCzl() throws Exception {
        int databaseSizeBeforeUpdate = checkCzlRepository.findAll().size();
        checkCzl.setId(count.incrementAndGet());

        // Create the CheckCzl
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckCzlMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkCzlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckCzl in the database
        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckCzl in Elasticsearch
        verify(mockCheckCzlSearchRepository, times(0)).save(checkCzl);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckCzl() throws Exception {
        int databaseSizeBeforeUpdate = checkCzlRepository.findAll().size();
        checkCzl.setId(count.incrementAndGet());

        // Create the CheckCzl
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckCzlMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkCzlDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckCzl in the database
        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckCzl in Elasticsearch
        verify(mockCheckCzlSearchRepository, times(0)).save(checkCzl);
    }

    @Test
    @Transactional
    void partialUpdateCheckCzlWithPatch() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        int databaseSizeBeforeUpdate = checkCzlRepository.findAll().size();

        // Update the checkCzl using partial update
        CheckCzl partialUpdatedCheckCzl = new CheckCzl();
        partialUpdatedCheckCzl.setId(checkCzl.getId());

        partialUpdatedCheckCzl.czl(UPDATED_CZL).chagrgeAvg(UPDATED_CHAGRGE_AVG).empn(UPDATED_EMPN).entertime(UPDATED_ENTERTIME);

        restCheckCzlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckCzl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckCzl))
            )
            .andExpect(status().isOk());

        // Validate the CheckCzl in the database
        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeUpdate);
        CheckCzl testCheckCzl = checkCzlList.get(checkCzlList.size() - 1);
        assertThat(testCheckCzl.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testCheckCzl.getRtype()).isEqualTo(DEFAULT_RTYPE);
        assertThat(testCheckCzl.getRnum()).isEqualTo(DEFAULT_RNUM);
        assertThat(testCheckCzl.getrOutnum()).isEqualTo(DEFAULT_R_OUTNUM);
        assertThat(testCheckCzl.getCzl()).isEqualByComparingTo(UPDATED_CZL);
        assertThat(testCheckCzl.getChagrge()).isEqualByComparingTo(DEFAULT_CHAGRGE);
        assertThat(testCheckCzl.getChagrgeAvg()).isEqualByComparingTo(UPDATED_CHAGRGE_AVG);
        assertThat(testCheckCzl.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckCzl.getEntertime()).isEqualTo(UPDATED_ENTERTIME);
    }

    @Test
    @Transactional
    void fullUpdateCheckCzlWithPatch() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        int databaseSizeBeforeUpdate = checkCzlRepository.findAll().size();

        // Update the checkCzl using partial update
        CheckCzl partialUpdatedCheckCzl = new CheckCzl();
        partialUpdatedCheckCzl.setId(checkCzl.getId());

        partialUpdatedCheckCzl
            .hoteltime(UPDATED_HOTELTIME)
            .rtype(UPDATED_RTYPE)
            .rnum(UPDATED_RNUM)
            .rOutnum(UPDATED_R_OUTNUM)
            .czl(UPDATED_CZL)
            .chagrge(UPDATED_CHAGRGE)
            .chagrgeAvg(UPDATED_CHAGRGE_AVG)
            .empn(UPDATED_EMPN)
            .entertime(UPDATED_ENTERTIME);

        restCheckCzlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckCzl.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckCzl))
            )
            .andExpect(status().isOk());

        // Validate the CheckCzl in the database
        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeUpdate);
        CheckCzl testCheckCzl = checkCzlList.get(checkCzlList.size() - 1);
        assertThat(testCheckCzl.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCheckCzl.getRtype()).isEqualTo(UPDATED_RTYPE);
        assertThat(testCheckCzl.getRnum()).isEqualTo(UPDATED_RNUM);
        assertThat(testCheckCzl.getrOutnum()).isEqualTo(UPDATED_R_OUTNUM);
        assertThat(testCheckCzl.getCzl()).isEqualByComparingTo(UPDATED_CZL);
        assertThat(testCheckCzl.getChagrge()).isEqualByComparingTo(UPDATED_CHAGRGE);
        assertThat(testCheckCzl.getChagrgeAvg()).isEqualByComparingTo(UPDATED_CHAGRGE_AVG);
        assertThat(testCheckCzl.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckCzl.getEntertime()).isEqualTo(UPDATED_ENTERTIME);
    }

    @Test
    @Transactional
    void patchNonExistingCheckCzl() throws Exception {
        int databaseSizeBeforeUpdate = checkCzlRepository.findAll().size();
        checkCzl.setId(count.incrementAndGet());

        // Create the CheckCzl
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckCzlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkCzlDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkCzlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckCzl in the database
        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckCzl in Elasticsearch
        verify(mockCheckCzlSearchRepository, times(0)).save(checkCzl);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckCzl() throws Exception {
        int databaseSizeBeforeUpdate = checkCzlRepository.findAll().size();
        checkCzl.setId(count.incrementAndGet());

        // Create the CheckCzl
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckCzlMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkCzlDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckCzl in the database
        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckCzl in Elasticsearch
        verify(mockCheckCzlSearchRepository, times(0)).save(checkCzl);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckCzl() throws Exception {
        int databaseSizeBeforeUpdate = checkCzlRepository.findAll().size();
        checkCzl.setId(count.incrementAndGet());

        // Create the CheckCzl
        CheckCzlDTO checkCzlDTO = checkCzlMapper.toDto(checkCzl);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckCzlMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(checkCzlDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckCzl in the database
        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckCzl in Elasticsearch
        verify(mockCheckCzlSearchRepository, times(0)).save(checkCzl);
    }

    @Test
    @Transactional
    void deleteCheckCzl() throws Exception {
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);

        int databaseSizeBeforeDelete = checkCzlRepository.findAll().size();

        // Delete the checkCzl
        restCheckCzlMockMvc
            .perform(delete(ENTITY_API_URL_ID, checkCzl.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckCzl> checkCzlList = checkCzlRepository.findAll();
        assertThat(checkCzlList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CheckCzl in Elasticsearch
        verify(mockCheckCzlSearchRepository, times(1)).deleteById(checkCzl.getId());
    }

    @Test
    @Transactional
    void searchCheckCzl() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        checkCzlRepository.saveAndFlush(checkCzl);
        when(mockCheckCzlSearchRepository.search(queryStringQuery("id:" + checkCzl.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(checkCzl), PageRequest.of(0, 1), 1));

        // Search the checkCzl
        restCheckCzlMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + checkCzl.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkCzl.getId().intValue())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].rtype").value(hasItem(DEFAULT_RTYPE)))
            .andExpect(jsonPath("$.[*].rnum").value(hasItem(DEFAULT_RNUM.intValue())))
            .andExpect(jsonPath("$.[*].rOutnum").value(hasItem(DEFAULT_R_OUTNUM.intValue())))
            .andExpect(jsonPath("$.[*].czl").value(hasItem(sameNumber(DEFAULT_CZL))))
            .andExpect(jsonPath("$.[*].chagrge").value(hasItem(sameNumber(DEFAULT_CHAGRGE))))
            .andExpect(jsonPath("$.[*].chagrgeAvg").value(hasItem(sameNumber(DEFAULT_CHAGRGE_AVG))))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].entertime").value(hasItem(DEFAULT_ENTERTIME.toString())));
    }
}
