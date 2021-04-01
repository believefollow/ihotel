package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Dayearndetail;
import ihotel.app.repository.DayearndetailRepository;
import ihotel.app.repository.search.DayearndetailSearchRepository;
import ihotel.app.service.criteria.DayearndetailCriteria;
import ihotel.app.service.dto.DayearndetailDTO;
import ihotel.app.service.mapper.DayearndetailMapper;
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
 * Integration tests for the {@link DayearndetailResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DayearndetailResourceIT {

    private static final Instant DEFAULT_EARNDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EARNDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_SALESPOTN = 1L;
    private static final Long UPDATED_SALESPOTN = 2L;
    private static final Long SMALLER_SALESPOTN = 1L - 1L;

    private static final BigDecimal DEFAULT_MONEY = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONEY = new BigDecimal(2);
    private static final BigDecimal SMALLER_MONEY = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/dayearndetails";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/dayearndetails";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DayearndetailRepository dayearndetailRepository;

    @Autowired
    private DayearndetailMapper dayearndetailMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DayearndetailSearchRepositoryMockConfiguration
     */
    @Autowired
    private DayearndetailSearchRepository mockDayearndetailSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDayearndetailMockMvc;

    private Dayearndetail dayearndetail;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dayearndetail createEntity(EntityManager em) {
        Dayearndetail dayearndetail = new Dayearndetail().earndate(DEFAULT_EARNDATE).salespotn(DEFAULT_SALESPOTN).money(DEFAULT_MONEY);
        return dayearndetail;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dayearndetail createUpdatedEntity(EntityManager em) {
        Dayearndetail dayearndetail = new Dayearndetail().earndate(UPDATED_EARNDATE).salespotn(UPDATED_SALESPOTN).money(UPDATED_MONEY);
        return dayearndetail;
    }

    @BeforeEach
    public void initTest() {
        dayearndetail = createEntity(em);
    }

    @Test
    @Transactional
    void createDayearndetail() throws Exception {
        int databaseSizeBeforeCreate = dayearndetailRepository.findAll().size();
        // Create the Dayearndetail
        DayearndetailDTO dayearndetailDTO = dayearndetailMapper.toDto(dayearndetail);
        restDayearndetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dayearndetailDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Dayearndetail in the database
        List<Dayearndetail> dayearndetailList = dayearndetailRepository.findAll();
        assertThat(dayearndetailList).hasSize(databaseSizeBeforeCreate + 1);
        Dayearndetail testDayearndetail = dayearndetailList.get(dayearndetailList.size() - 1);
        assertThat(testDayearndetail.getEarndate()).isEqualTo(DEFAULT_EARNDATE);
        assertThat(testDayearndetail.getSalespotn()).isEqualTo(DEFAULT_SALESPOTN);
        assertThat(testDayearndetail.getMoney()).isEqualByComparingTo(DEFAULT_MONEY);

        // Validate the Dayearndetail in Elasticsearch
        verify(mockDayearndetailSearchRepository, times(1)).save(testDayearndetail);
    }

    @Test
    @Transactional
    void createDayearndetailWithExistingId() throws Exception {
        // Create the Dayearndetail with an existing ID
        dayearndetail.setId(1L);
        DayearndetailDTO dayearndetailDTO = dayearndetailMapper.toDto(dayearndetail);

        int databaseSizeBeforeCreate = dayearndetailRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDayearndetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dayearndetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dayearndetail in the database
        List<Dayearndetail> dayearndetailList = dayearndetailRepository.findAll();
        assertThat(dayearndetailList).hasSize(databaseSizeBeforeCreate);

        // Validate the Dayearndetail in Elasticsearch
        verify(mockDayearndetailSearchRepository, times(0)).save(dayearndetail);
    }

    @Test
    @Transactional
    void checkEarndateIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayearndetailRepository.findAll().size();
        // set the field null
        dayearndetail.setEarndate(null);

        // Create the Dayearndetail, which fails.
        DayearndetailDTO dayearndetailDTO = dayearndetailMapper.toDto(dayearndetail);

        restDayearndetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dayearndetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<Dayearndetail> dayearndetailList = dayearndetailRepository.findAll();
        assertThat(dayearndetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSalespotnIsRequired() throws Exception {
        int databaseSizeBeforeTest = dayearndetailRepository.findAll().size();
        // set the field null
        dayearndetail.setSalespotn(null);

        // Create the Dayearndetail, which fails.
        DayearndetailDTO dayearndetailDTO = dayearndetailMapper.toDto(dayearndetail);

        restDayearndetailMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dayearndetailDTO))
            )
            .andExpect(status().isBadRequest());

        List<Dayearndetail> dayearndetailList = dayearndetailRepository.findAll();
        assertThat(dayearndetailList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDayearndetails() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList
        restDayearndetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dayearndetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].earndate").value(hasItem(DEFAULT_EARNDATE.toString())))
            .andExpect(jsonPath("$.[*].salespotn").value(hasItem(DEFAULT_SALESPOTN.intValue())))
            .andExpect(jsonPath("$.[*].money").value(hasItem(sameNumber(DEFAULT_MONEY))));
    }

    @Test
    @Transactional
    void getDayearndetail() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get the dayearndetail
        restDayearndetailMockMvc
            .perform(get(ENTITY_API_URL_ID, dayearndetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dayearndetail.getId().intValue()))
            .andExpect(jsonPath("$.earndate").value(DEFAULT_EARNDATE.toString()))
            .andExpect(jsonPath("$.salespotn").value(DEFAULT_SALESPOTN.intValue()))
            .andExpect(jsonPath("$.money").value(sameNumber(DEFAULT_MONEY)));
    }

    @Test
    @Transactional
    void getDayearndetailsByIdFiltering() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        Long id = dayearndetail.getId();

        defaultDayearndetailShouldBeFound("id.equals=" + id);
        defaultDayearndetailShouldNotBeFound("id.notEquals=" + id);

        defaultDayearndetailShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDayearndetailShouldNotBeFound("id.greaterThan=" + id);

        defaultDayearndetailShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDayearndetailShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDayearndetailsByEarndateIsEqualToSomething() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where earndate equals to DEFAULT_EARNDATE
        defaultDayearndetailShouldBeFound("earndate.equals=" + DEFAULT_EARNDATE);

        // Get all the dayearndetailList where earndate equals to UPDATED_EARNDATE
        defaultDayearndetailShouldNotBeFound("earndate.equals=" + UPDATED_EARNDATE);
    }

    @Test
    @Transactional
    void getAllDayearndetailsByEarndateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where earndate not equals to DEFAULT_EARNDATE
        defaultDayearndetailShouldNotBeFound("earndate.notEquals=" + DEFAULT_EARNDATE);

        // Get all the dayearndetailList where earndate not equals to UPDATED_EARNDATE
        defaultDayearndetailShouldBeFound("earndate.notEquals=" + UPDATED_EARNDATE);
    }

    @Test
    @Transactional
    void getAllDayearndetailsByEarndateIsInShouldWork() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where earndate in DEFAULT_EARNDATE or UPDATED_EARNDATE
        defaultDayearndetailShouldBeFound("earndate.in=" + DEFAULT_EARNDATE + "," + UPDATED_EARNDATE);

        // Get all the dayearndetailList where earndate equals to UPDATED_EARNDATE
        defaultDayearndetailShouldNotBeFound("earndate.in=" + UPDATED_EARNDATE);
    }

    @Test
    @Transactional
    void getAllDayearndetailsByEarndateIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where earndate is not null
        defaultDayearndetailShouldBeFound("earndate.specified=true");

        // Get all the dayearndetailList where earndate is null
        defaultDayearndetailShouldNotBeFound("earndate.specified=false");
    }

    @Test
    @Transactional
    void getAllDayearndetailsBySalespotnIsEqualToSomething() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where salespotn equals to DEFAULT_SALESPOTN
        defaultDayearndetailShouldBeFound("salespotn.equals=" + DEFAULT_SALESPOTN);

        // Get all the dayearndetailList where salespotn equals to UPDATED_SALESPOTN
        defaultDayearndetailShouldNotBeFound("salespotn.equals=" + UPDATED_SALESPOTN);
    }

    @Test
    @Transactional
    void getAllDayearndetailsBySalespotnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where salespotn not equals to DEFAULT_SALESPOTN
        defaultDayearndetailShouldNotBeFound("salespotn.notEquals=" + DEFAULT_SALESPOTN);

        // Get all the dayearndetailList where salespotn not equals to UPDATED_SALESPOTN
        defaultDayearndetailShouldBeFound("salespotn.notEquals=" + UPDATED_SALESPOTN);
    }

    @Test
    @Transactional
    void getAllDayearndetailsBySalespotnIsInShouldWork() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where salespotn in DEFAULT_SALESPOTN or UPDATED_SALESPOTN
        defaultDayearndetailShouldBeFound("salespotn.in=" + DEFAULT_SALESPOTN + "," + UPDATED_SALESPOTN);

        // Get all the dayearndetailList where salespotn equals to UPDATED_SALESPOTN
        defaultDayearndetailShouldNotBeFound("salespotn.in=" + UPDATED_SALESPOTN);
    }

    @Test
    @Transactional
    void getAllDayearndetailsBySalespotnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where salespotn is not null
        defaultDayearndetailShouldBeFound("salespotn.specified=true");

        // Get all the dayearndetailList where salespotn is null
        defaultDayearndetailShouldNotBeFound("salespotn.specified=false");
    }

    @Test
    @Transactional
    void getAllDayearndetailsBySalespotnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where salespotn is greater than or equal to DEFAULT_SALESPOTN
        defaultDayearndetailShouldBeFound("salespotn.greaterThanOrEqual=" + DEFAULT_SALESPOTN);

        // Get all the dayearndetailList where salespotn is greater than or equal to UPDATED_SALESPOTN
        defaultDayearndetailShouldNotBeFound("salespotn.greaterThanOrEqual=" + UPDATED_SALESPOTN);
    }

    @Test
    @Transactional
    void getAllDayearndetailsBySalespotnIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where salespotn is less than or equal to DEFAULT_SALESPOTN
        defaultDayearndetailShouldBeFound("salespotn.lessThanOrEqual=" + DEFAULT_SALESPOTN);

        // Get all the dayearndetailList where salespotn is less than or equal to SMALLER_SALESPOTN
        defaultDayearndetailShouldNotBeFound("salespotn.lessThanOrEqual=" + SMALLER_SALESPOTN);
    }

    @Test
    @Transactional
    void getAllDayearndetailsBySalespotnIsLessThanSomething() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where salespotn is less than DEFAULT_SALESPOTN
        defaultDayearndetailShouldNotBeFound("salespotn.lessThan=" + DEFAULT_SALESPOTN);

        // Get all the dayearndetailList where salespotn is less than UPDATED_SALESPOTN
        defaultDayearndetailShouldBeFound("salespotn.lessThan=" + UPDATED_SALESPOTN);
    }

    @Test
    @Transactional
    void getAllDayearndetailsBySalespotnIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where salespotn is greater than DEFAULT_SALESPOTN
        defaultDayearndetailShouldNotBeFound("salespotn.greaterThan=" + DEFAULT_SALESPOTN);

        // Get all the dayearndetailList where salespotn is greater than SMALLER_SALESPOTN
        defaultDayearndetailShouldBeFound("salespotn.greaterThan=" + SMALLER_SALESPOTN);
    }

    @Test
    @Transactional
    void getAllDayearndetailsByMoneyIsEqualToSomething() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where money equals to DEFAULT_MONEY
        defaultDayearndetailShouldBeFound("money.equals=" + DEFAULT_MONEY);

        // Get all the dayearndetailList where money equals to UPDATED_MONEY
        defaultDayearndetailShouldNotBeFound("money.equals=" + UPDATED_MONEY);
    }

    @Test
    @Transactional
    void getAllDayearndetailsByMoneyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where money not equals to DEFAULT_MONEY
        defaultDayearndetailShouldNotBeFound("money.notEquals=" + DEFAULT_MONEY);

        // Get all the dayearndetailList where money not equals to UPDATED_MONEY
        defaultDayearndetailShouldBeFound("money.notEquals=" + UPDATED_MONEY);
    }

    @Test
    @Transactional
    void getAllDayearndetailsByMoneyIsInShouldWork() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where money in DEFAULT_MONEY or UPDATED_MONEY
        defaultDayearndetailShouldBeFound("money.in=" + DEFAULT_MONEY + "," + UPDATED_MONEY);

        // Get all the dayearndetailList where money equals to UPDATED_MONEY
        defaultDayearndetailShouldNotBeFound("money.in=" + UPDATED_MONEY);
    }

    @Test
    @Transactional
    void getAllDayearndetailsByMoneyIsNullOrNotNull() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where money is not null
        defaultDayearndetailShouldBeFound("money.specified=true");

        // Get all the dayearndetailList where money is null
        defaultDayearndetailShouldNotBeFound("money.specified=false");
    }

    @Test
    @Transactional
    void getAllDayearndetailsByMoneyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where money is greater than or equal to DEFAULT_MONEY
        defaultDayearndetailShouldBeFound("money.greaterThanOrEqual=" + DEFAULT_MONEY);

        // Get all the dayearndetailList where money is greater than or equal to UPDATED_MONEY
        defaultDayearndetailShouldNotBeFound("money.greaterThanOrEqual=" + UPDATED_MONEY);
    }

    @Test
    @Transactional
    void getAllDayearndetailsByMoneyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where money is less than or equal to DEFAULT_MONEY
        defaultDayearndetailShouldBeFound("money.lessThanOrEqual=" + DEFAULT_MONEY);

        // Get all the dayearndetailList where money is less than or equal to SMALLER_MONEY
        defaultDayearndetailShouldNotBeFound("money.lessThanOrEqual=" + SMALLER_MONEY);
    }

    @Test
    @Transactional
    void getAllDayearndetailsByMoneyIsLessThanSomething() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where money is less than DEFAULT_MONEY
        defaultDayearndetailShouldNotBeFound("money.lessThan=" + DEFAULT_MONEY);

        // Get all the dayearndetailList where money is less than UPDATED_MONEY
        defaultDayearndetailShouldBeFound("money.lessThan=" + UPDATED_MONEY);
    }

    @Test
    @Transactional
    void getAllDayearndetailsByMoneyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        // Get all the dayearndetailList where money is greater than DEFAULT_MONEY
        defaultDayearndetailShouldNotBeFound("money.greaterThan=" + DEFAULT_MONEY);

        // Get all the dayearndetailList where money is greater than SMALLER_MONEY
        defaultDayearndetailShouldBeFound("money.greaterThan=" + SMALLER_MONEY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDayearndetailShouldBeFound(String filter) throws Exception {
        restDayearndetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dayearndetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].earndate").value(hasItem(DEFAULT_EARNDATE.toString())))
            .andExpect(jsonPath("$.[*].salespotn").value(hasItem(DEFAULT_SALESPOTN.intValue())))
            .andExpect(jsonPath("$.[*].money").value(hasItem(sameNumber(DEFAULT_MONEY))));

        // Check, that the count call also returns 1
        restDayearndetailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDayearndetailShouldNotBeFound(String filter) throws Exception {
        restDayearndetailMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDayearndetailMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDayearndetail() throws Exception {
        // Get the dayearndetail
        restDayearndetailMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDayearndetail() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        int databaseSizeBeforeUpdate = dayearndetailRepository.findAll().size();

        // Update the dayearndetail
        Dayearndetail updatedDayearndetail = dayearndetailRepository.findById(dayearndetail.getId()).get();
        // Disconnect from session so that the updates on updatedDayearndetail are not directly saved in db
        em.detach(updatedDayearndetail);
        updatedDayearndetail.earndate(UPDATED_EARNDATE).salespotn(UPDATED_SALESPOTN).money(UPDATED_MONEY);
        DayearndetailDTO dayearndetailDTO = dayearndetailMapper.toDto(updatedDayearndetail);

        restDayearndetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dayearndetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dayearndetailDTO))
            )
            .andExpect(status().isOk());

        // Validate the Dayearndetail in the database
        List<Dayearndetail> dayearndetailList = dayearndetailRepository.findAll();
        assertThat(dayearndetailList).hasSize(databaseSizeBeforeUpdate);
        Dayearndetail testDayearndetail = dayearndetailList.get(dayearndetailList.size() - 1);
        assertThat(testDayearndetail.getEarndate()).isEqualTo(UPDATED_EARNDATE);
        assertThat(testDayearndetail.getSalespotn()).isEqualTo(UPDATED_SALESPOTN);
        assertThat(testDayearndetail.getMoney()).isEqualTo(UPDATED_MONEY);

        // Validate the Dayearndetail in Elasticsearch
        verify(mockDayearndetailSearchRepository).save(testDayearndetail);
    }

    @Test
    @Transactional
    void putNonExistingDayearndetail() throws Exception {
        int databaseSizeBeforeUpdate = dayearndetailRepository.findAll().size();
        dayearndetail.setId(count.incrementAndGet());

        // Create the Dayearndetail
        DayearndetailDTO dayearndetailDTO = dayearndetailMapper.toDto(dayearndetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDayearndetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dayearndetailDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dayearndetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dayearndetail in the database
        List<Dayearndetail> dayearndetailList = dayearndetailRepository.findAll();
        assertThat(dayearndetailList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Dayearndetail in Elasticsearch
        verify(mockDayearndetailSearchRepository, times(0)).save(dayearndetail);
    }

    @Test
    @Transactional
    void putWithIdMismatchDayearndetail() throws Exception {
        int databaseSizeBeforeUpdate = dayearndetailRepository.findAll().size();
        dayearndetail.setId(count.incrementAndGet());

        // Create the Dayearndetail
        DayearndetailDTO dayearndetailDTO = dayearndetailMapper.toDto(dayearndetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDayearndetailMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dayearndetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dayearndetail in the database
        List<Dayearndetail> dayearndetailList = dayearndetailRepository.findAll();
        assertThat(dayearndetailList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Dayearndetail in Elasticsearch
        verify(mockDayearndetailSearchRepository, times(0)).save(dayearndetail);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDayearndetail() throws Exception {
        int databaseSizeBeforeUpdate = dayearndetailRepository.findAll().size();
        dayearndetail.setId(count.incrementAndGet());

        // Create the Dayearndetail
        DayearndetailDTO dayearndetailDTO = dayearndetailMapper.toDto(dayearndetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDayearndetailMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dayearndetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dayearndetail in the database
        List<Dayearndetail> dayearndetailList = dayearndetailRepository.findAll();
        assertThat(dayearndetailList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Dayearndetail in Elasticsearch
        verify(mockDayearndetailSearchRepository, times(0)).save(dayearndetail);
    }

    @Test
    @Transactional
    void partialUpdateDayearndetailWithPatch() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        int databaseSizeBeforeUpdate = dayearndetailRepository.findAll().size();

        // Update the dayearndetail using partial update
        Dayearndetail partialUpdatedDayearndetail = new Dayearndetail();
        partialUpdatedDayearndetail.setId(dayearndetail.getId());

        partialUpdatedDayearndetail.salespotn(UPDATED_SALESPOTN);

        restDayearndetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDayearndetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDayearndetail))
            )
            .andExpect(status().isOk());

        // Validate the Dayearndetail in the database
        List<Dayearndetail> dayearndetailList = dayearndetailRepository.findAll();
        assertThat(dayearndetailList).hasSize(databaseSizeBeforeUpdate);
        Dayearndetail testDayearndetail = dayearndetailList.get(dayearndetailList.size() - 1);
        assertThat(testDayearndetail.getEarndate()).isEqualTo(DEFAULT_EARNDATE);
        assertThat(testDayearndetail.getSalespotn()).isEqualTo(UPDATED_SALESPOTN);
        assertThat(testDayearndetail.getMoney()).isEqualByComparingTo(DEFAULT_MONEY);
    }

    @Test
    @Transactional
    void fullUpdateDayearndetailWithPatch() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        int databaseSizeBeforeUpdate = dayearndetailRepository.findAll().size();

        // Update the dayearndetail using partial update
        Dayearndetail partialUpdatedDayearndetail = new Dayearndetail();
        partialUpdatedDayearndetail.setId(dayearndetail.getId());

        partialUpdatedDayearndetail.earndate(UPDATED_EARNDATE).salespotn(UPDATED_SALESPOTN).money(UPDATED_MONEY);

        restDayearndetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDayearndetail.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDayearndetail))
            )
            .andExpect(status().isOk());

        // Validate the Dayearndetail in the database
        List<Dayearndetail> dayearndetailList = dayearndetailRepository.findAll();
        assertThat(dayearndetailList).hasSize(databaseSizeBeforeUpdate);
        Dayearndetail testDayearndetail = dayearndetailList.get(dayearndetailList.size() - 1);
        assertThat(testDayearndetail.getEarndate()).isEqualTo(UPDATED_EARNDATE);
        assertThat(testDayearndetail.getSalespotn()).isEqualTo(UPDATED_SALESPOTN);
        assertThat(testDayearndetail.getMoney()).isEqualByComparingTo(UPDATED_MONEY);
    }

    @Test
    @Transactional
    void patchNonExistingDayearndetail() throws Exception {
        int databaseSizeBeforeUpdate = dayearndetailRepository.findAll().size();
        dayearndetail.setId(count.incrementAndGet());

        // Create the Dayearndetail
        DayearndetailDTO dayearndetailDTO = dayearndetailMapper.toDto(dayearndetail);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDayearndetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dayearndetailDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dayearndetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dayearndetail in the database
        List<Dayearndetail> dayearndetailList = dayearndetailRepository.findAll();
        assertThat(dayearndetailList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Dayearndetail in Elasticsearch
        verify(mockDayearndetailSearchRepository, times(0)).save(dayearndetail);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDayearndetail() throws Exception {
        int databaseSizeBeforeUpdate = dayearndetailRepository.findAll().size();
        dayearndetail.setId(count.incrementAndGet());

        // Create the Dayearndetail
        DayearndetailDTO dayearndetailDTO = dayearndetailMapper.toDto(dayearndetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDayearndetailMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dayearndetailDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Dayearndetail in the database
        List<Dayearndetail> dayearndetailList = dayearndetailRepository.findAll();
        assertThat(dayearndetailList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Dayearndetail in Elasticsearch
        verify(mockDayearndetailSearchRepository, times(0)).save(dayearndetail);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDayearndetail() throws Exception {
        int databaseSizeBeforeUpdate = dayearndetailRepository.findAll().size();
        dayearndetail.setId(count.incrementAndGet());

        // Create the Dayearndetail
        DayearndetailDTO dayearndetailDTO = dayearndetailMapper.toDto(dayearndetail);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDayearndetailMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dayearndetailDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Dayearndetail in the database
        List<Dayearndetail> dayearndetailList = dayearndetailRepository.findAll();
        assertThat(dayearndetailList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Dayearndetail in Elasticsearch
        verify(mockDayearndetailSearchRepository, times(0)).save(dayearndetail);
    }

    @Test
    @Transactional
    void deleteDayearndetail() throws Exception {
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);

        int databaseSizeBeforeDelete = dayearndetailRepository.findAll().size();

        // Delete the dayearndetail
        restDayearndetailMockMvc
            .perform(delete(ENTITY_API_URL_ID, dayearndetail.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dayearndetail> dayearndetailList = dayearndetailRepository.findAll();
        assertThat(dayearndetailList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Dayearndetail in Elasticsearch
        verify(mockDayearndetailSearchRepository, times(1)).deleteById(dayearndetail.getId());
    }

    @Test
    @Transactional
    void searchDayearndetail() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dayearndetailRepository.saveAndFlush(dayearndetail);
        when(mockDayearndetailSearchRepository.search(queryStringQuery("id:" + dayearndetail.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dayearndetail), PageRequest.of(0, 1), 1));

        // Search the dayearndetail
        restDayearndetailMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dayearndetail.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dayearndetail.getId().intValue())))
            .andExpect(jsonPath("$.[*].earndate").value(hasItem(DEFAULT_EARNDATE.toString())))
            .andExpect(jsonPath("$.[*].salespotn").value(hasItem(DEFAULT_SALESPOTN.intValue())))
            .andExpect(jsonPath("$.[*].money").value(hasItem(sameNumber(DEFAULT_MONEY))));
    }
}
