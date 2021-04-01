package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.CheckinAccount;
import ihotel.app.repository.CheckinAccountRepository;
import ihotel.app.repository.search.CheckinAccountSearchRepository;
import ihotel.app.service.criteria.CheckinAccountCriteria;
import ihotel.app.service.dto.CheckinAccountDTO;
import ihotel.app.service.mapper.CheckinAccountMapper;
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
 * Integration tests for the {@link CheckinAccountResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CheckinAccountResourceIT {

    private static final String DEFAULT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMN = "AAAAAAAAAA";
    private static final String UPDATED_ROOMN = "BBBBBBBBBB";

    private static final Instant DEFAULT_INDATETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INDATETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_GOTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GOTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_KFANG = new BigDecimal(1);
    private static final BigDecimal UPDATED_KFANG = new BigDecimal(2);
    private static final BigDecimal SMALLER_KFANG = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DHUA = new BigDecimal(1);
    private static final BigDecimal UPDATED_DHUA = new BigDecimal(2);
    private static final BigDecimal SMALLER_DHUA = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_MININ = new BigDecimal(1);
    private static final BigDecimal UPDATED_MININ = new BigDecimal(2);
    private static final BigDecimal SMALLER_MININ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PEICH = new BigDecimal(1);
    private static final BigDecimal UPDATED_PEICH = new BigDecimal(2);
    private static final BigDecimal SMALLER_PEICH = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_QIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_QIT = new BigDecimal(2);
    private static final BigDecimal SMALLER_QIT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/checkin-accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/checkin-accounts";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CheckinAccountRepository checkinAccountRepository;

    @Autowired
    private CheckinAccountMapper checkinAccountMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CheckinAccountSearchRepositoryMockConfiguration
     */
    @Autowired
    private CheckinAccountSearchRepository mockCheckinAccountSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckinAccountMockMvc;

    private CheckinAccount checkinAccount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckinAccount createEntity(EntityManager em) {
        CheckinAccount checkinAccount = new CheckinAccount()
            .account(DEFAULT_ACCOUNT)
            .roomn(DEFAULT_ROOMN)
            .indatetime(DEFAULT_INDATETIME)
            .gotime(DEFAULT_GOTIME)
            .kfang(DEFAULT_KFANG)
            .dhua(DEFAULT_DHUA)
            .minin(DEFAULT_MININ)
            .peich(DEFAULT_PEICH)
            .qit(DEFAULT_QIT)
            .total(DEFAULT_TOTAL);
        return checkinAccount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckinAccount createUpdatedEntity(EntityManager em) {
        CheckinAccount checkinAccount = new CheckinAccount()
            .account(UPDATED_ACCOUNT)
            .roomn(UPDATED_ROOMN)
            .indatetime(UPDATED_INDATETIME)
            .gotime(UPDATED_GOTIME)
            .kfang(UPDATED_KFANG)
            .dhua(UPDATED_DHUA)
            .minin(UPDATED_MININ)
            .peich(UPDATED_PEICH)
            .qit(UPDATED_QIT)
            .total(UPDATED_TOTAL);
        return checkinAccount;
    }

    @BeforeEach
    public void initTest() {
        checkinAccount = createEntity(em);
    }

    @Test
    @Transactional
    void createCheckinAccount() throws Exception {
        int databaseSizeBeforeCreate = checkinAccountRepository.findAll().size();
        // Create the CheckinAccount
        CheckinAccountDTO checkinAccountDTO = checkinAccountMapper.toDto(checkinAccount);
        restCheckinAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinAccountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CheckinAccount in the database
        List<CheckinAccount> checkinAccountList = checkinAccountRepository.findAll();
        assertThat(checkinAccountList).hasSize(databaseSizeBeforeCreate + 1);
        CheckinAccount testCheckinAccount = checkinAccountList.get(checkinAccountList.size() - 1);
        assertThat(testCheckinAccount.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testCheckinAccount.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testCheckinAccount.getIndatetime()).isEqualTo(DEFAULT_INDATETIME);
        assertThat(testCheckinAccount.getGotime()).isEqualTo(DEFAULT_GOTIME);
        assertThat(testCheckinAccount.getKfang()).isEqualByComparingTo(DEFAULT_KFANG);
        assertThat(testCheckinAccount.getDhua()).isEqualByComparingTo(DEFAULT_DHUA);
        assertThat(testCheckinAccount.getMinin()).isEqualByComparingTo(DEFAULT_MININ);
        assertThat(testCheckinAccount.getPeich()).isEqualByComparingTo(DEFAULT_PEICH);
        assertThat(testCheckinAccount.getQit()).isEqualByComparingTo(DEFAULT_QIT);
        assertThat(testCheckinAccount.getTotal()).isEqualByComparingTo(DEFAULT_TOTAL);

        // Validate the CheckinAccount in Elasticsearch
        verify(mockCheckinAccountSearchRepository, times(1)).save(testCheckinAccount);
    }

    @Test
    @Transactional
    void createCheckinAccountWithExistingId() throws Exception {
        // Create the CheckinAccount with an existing ID
        checkinAccount.setId(1L);
        CheckinAccountDTO checkinAccountDTO = checkinAccountMapper.toDto(checkinAccount);

        int databaseSizeBeforeCreate = checkinAccountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckinAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckinAccount in the database
        List<CheckinAccount> checkinAccountList = checkinAccountRepository.findAll();
        assertThat(checkinAccountList).hasSize(databaseSizeBeforeCreate);

        // Validate the CheckinAccount in Elasticsearch
        verify(mockCheckinAccountSearchRepository, times(0)).save(checkinAccount);
    }

    @Test
    @Transactional
    void checkAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkinAccountRepository.findAll().size();
        // set the field null
        checkinAccount.setAccount(null);

        // Create the CheckinAccount, which fails.
        CheckinAccountDTO checkinAccountDTO = checkinAccountMapper.toDto(checkinAccount);

        restCheckinAccountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinAccountDTO))
            )
            .andExpect(status().isBadRequest());

        List<CheckinAccount> checkinAccountList = checkinAccountRepository.findAll();
        assertThat(checkinAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCheckinAccounts() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList
        restCheckinAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkinAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].indatetime").value(hasItem(DEFAULT_INDATETIME.toString())))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].kfang").value(hasItem(sameNumber(DEFAULT_KFANG))))
            .andExpect(jsonPath("$.[*].dhua").value(hasItem(sameNumber(DEFAULT_DHUA))))
            .andExpect(jsonPath("$.[*].minin").value(hasItem(sameNumber(DEFAULT_MININ))))
            .andExpect(jsonPath("$.[*].peich").value(hasItem(sameNumber(DEFAULT_PEICH))))
            .andExpect(jsonPath("$.[*].qit").value(hasItem(sameNumber(DEFAULT_QIT))))
            .andExpect(jsonPath("$.[*].total").value(hasItem(sameNumber(DEFAULT_TOTAL))));
    }

    @Test
    @Transactional
    void getCheckinAccount() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get the checkinAccount
        restCheckinAccountMockMvc
            .perform(get(ENTITY_API_URL_ID, checkinAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkinAccount.getId().intValue()))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT))
            .andExpect(jsonPath("$.roomn").value(DEFAULT_ROOMN))
            .andExpect(jsonPath("$.indatetime").value(DEFAULT_INDATETIME.toString()))
            .andExpect(jsonPath("$.gotime").value(DEFAULT_GOTIME.toString()))
            .andExpect(jsonPath("$.kfang").value(sameNumber(DEFAULT_KFANG)))
            .andExpect(jsonPath("$.dhua").value(sameNumber(DEFAULT_DHUA)))
            .andExpect(jsonPath("$.minin").value(sameNumber(DEFAULT_MININ)))
            .andExpect(jsonPath("$.peich").value(sameNumber(DEFAULT_PEICH)))
            .andExpect(jsonPath("$.qit").value(sameNumber(DEFAULT_QIT)))
            .andExpect(jsonPath("$.total").value(sameNumber(DEFAULT_TOTAL)));
    }

    @Test
    @Transactional
    void getCheckinAccountsByIdFiltering() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        Long id = checkinAccount.getId();

        defaultCheckinAccountShouldBeFound("id.equals=" + id);
        defaultCheckinAccountShouldNotBeFound("id.notEquals=" + id);

        defaultCheckinAccountShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCheckinAccountShouldNotBeFound("id.greaterThan=" + id);

        defaultCheckinAccountShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCheckinAccountShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where account equals to DEFAULT_ACCOUNT
        defaultCheckinAccountShouldBeFound("account.equals=" + DEFAULT_ACCOUNT);

        // Get all the checkinAccountList where account equals to UPDATED_ACCOUNT
        defaultCheckinAccountShouldNotBeFound("account.equals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where account not equals to DEFAULT_ACCOUNT
        defaultCheckinAccountShouldNotBeFound("account.notEquals=" + DEFAULT_ACCOUNT);

        // Get all the checkinAccountList where account not equals to UPDATED_ACCOUNT
        defaultCheckinAccountShouldBeFound("account.notEquals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByAccountIsInShouldWork() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where account in DEFAULT_ACCOUNT or UPDATED_ACCOUNT
        defaultCheckinAccountShouldBeFound("account.in=" + DEFAULT_ACCOUNT + "," + UPDATED_ACCOUNT);

        // Get all the checkinAccountList where account equals to UPDATED_ACCOUNT
        defaultCheckinAccountShouldNotBeFound("account.in=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where account is not null
        defaultCheckinAccountShouldBeFound("account.specified=true");

        // Get all the checkinAccountList where account is null
        defaultCheckinAccountShouldNotBeFound("account.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByAccountContainsSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where account contains DEFAULT_ACCOUNT
        defaultCheckinAccountShouldBeFound("account.contains=" + DEFAULT_ACCOUNT);

        // Get all the checkinAccountList where account contains UPDATED_ACCOUNT
        defaultCheckinAccountShouldNotBeFound("account.contains=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByAccountNotContainsSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where account does not contain DEFAULT_ACCOUNT
        defaultCheckinAccountShouldNotBeFound("account.doesNotContain=" + DEFAULT_ACCOUNT);

        // Get all the checkinAccountList where account does not contain UPDATED_ACCOUNT
        defaultCheckinAccountShouldBeFound("account.doesNotContain=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where roomn equals to DEFAULT_ROOMN
        defaultCheckinAccountShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the checkinAccountList where roomn equals to UPDATED_ROOMN
        defaultCheckinAccountShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where roomn not equals to DEFAULT_ROOMN
        defaultCheckinAccountShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the checkinAccountList where roomn not equals to UPDATED_ROOMN
        defaultCheckinAccountShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultCheckinAccountShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the checkinAccountList where roomn equals to UPDATED_ROOMN
        defaultCheckinAccountShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where roomn is not null
        defaultCheckinAccountShouldBeFound("roomn.specified=true");

        // Get all the checkinAccountList where roomn is null
        defaultCheckinAccountShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByRoomnContainsSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where roomn contains DEFAULT_ROOMN
        defaultCheckinAccountShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the checkinAccountList where roomn contains UPDATED_ROOMN
        defaultCheckinAccountShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where roomn does not contain DEFAULT_ROOMN
        defaultCheckinAccountShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the checkinAccountList where roomn does not contain UPDATED_ROOMN
        defaultCheckinAccountShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByIndatetimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where indatetime equals to DEFAULT_INDATETIME
        defaultCheckinAccountShouldBeFound("indatetime.equals=" + DEFAULT_INDATETIME);

        // Get all the checkinAccountList where indatetime equals to UPDATED_INDATETIME
        defaultCheckinAccountShouldNotBeFound("indatetime.equals=" + UPDATED_INDATETIME);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByIndatetimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where indatetime not equals to DEFAULT_INDATETIME
        defaultCheckinAccountShouldNotBeFound("indatetime.notEquals=" + DEFAULT_INDATETIME);

        // Get all the checkinAccountList where indatetime not equals to UPDATED_INDATETIME
        defaultCheckinAccountShouldBeFound("indatetime.notEquals=" + UPDATED_INDATETIME);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByIndatetimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where indatetime in DEFAULT_INDATETIME or UPDATED_INDATETIME
        defaultCheckinAccountShouldBeFound("indatetime.in=" + DEFAULT_INDATETIME + "," + UPDATED_INDATETIME);

        // Get all the checkinAccountList where indatetime equals to UPDATED_INDATETIME
        defaultCheckinAccountShouldNotBeFound("indatetime.in=" + UPDATED_INDATETIME);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByIndatetimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where indatetime is not null
        defaultCheckinAccountShouldBeFound("indatetime.specified=true");

        // Get all the checkinAccountList where indatetime is null
        defaultCheckinAccountShouldNotBeFound("indatetime.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByGotimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where gotime equals to DEFAULT_GOTIME
        defaultCheckinAccountShouldBeFound("gotime.equals=" + DEFAULT_GOTIME);

        // Get all the checkinAccountList where gotime equals to UPDATED_GOTIME
        defaultCheckinAccountShouldNotBeFound("gotime.equals=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByGotimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where gotime not equals to DEFAULT_GOTIME
        defaultCheckinAccountShouldNotBeFound("gotime.notEquals=" + DEFAULT_GOTIME);

        // Get all the checkinAccountList where gotime not equals to UPDATED_GOTIME
        defaultCheckinAccountShouldBeFound("gotime.notEquals=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByGotimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where gotime in DEFAULT_GOTIME or UPDATED_GOTIME
        defaultCheckinAccountShouldBeFound("gotime.in=" + DEFAULT_GOTIME + "," + UPDATED_GOTIME);

        // Get all the checkinAccountList where gotime equals to UPDATED_GOTIME
        defaultCheckinAccountShouldNotBeFound("gotime.in=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByGotimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where gotime is not null
        defaultCheckinAccountShouldBeFound("gotime.specified=true");

        // Get all the checkinAccountList where gotime is null
        defaultCheckinAccountShouldNotBeFound("gotime.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByKfangIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where kfang equals to DEFAULT_KFANG
        defaultCheckinAccountShouldBeFound("kfang.equals=" + DEFAULT_KFANG);

        // Get all the checkinAccountList where kfang equals to UPDATED_KFANG
        defaultCheckinAccountShouldNotBeFound("kfang.equals=" + UPDATED_KFANG);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByKfangIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where kfang not equals to DEFAULT_KFANG
        defaultCheckinAccountShouldNotBeFound("kfang.notEquals=" + DEFAULT_KFANG);

        // Get all the checkinAccountList where kfang not equals to UPDATED_KFANG
        defaultCheckinAccountShouldBeFound("kfang.notEquals=" + UPDATED_KFANG);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByKfangIsInShouldWork() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where kfang in DEFAULT_KFANG or UPDATED_KFANG
        defaultCheckinAccountShouldBeFound("kfang.in=" + DEFAULT_KFANG + "," + UPDATED_KFANG);

        // Get all the checkinAccountList where kfang equals to UPDATED_KFANG
        defaultCheckinAccountShouldNotBeFound("kfang.in=" + UPDATED_KFANG);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByKfangIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where kfang is not null
        defaultCheckinAccountShouldBeFound("kfang.specified=true");

        // Get all the checkinAccountList where kfang is null
        defaultCheckinAccountShouldNotBeFound("kfang.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByKfangIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where kfang is greater than or equal to DEFAULT_KFANG
        defaultCheckinAccountShouldBeFound("kfang.greaterThanOrEqual=" + DEFAULT_KFANG);

        // Get all the checkinAccountList where kfang is greater than or equal to UPDATED_KFANG
        defaultCheckinAccountShouldNotBeFound("kfang.greaterThanOrEqual=" + UPDATED_KFANG);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByKfangIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where kfang is less than or equal to DEFAULT_KFANG
        defaultCheckinAccountShouldBeFound("kfang.lessThanOrEqual=" + DEFAULT_KFANG);

        // Get all the checkinAccountList where kfang is less than or equal to SMALLER_KFANG
        defaultCheckinAccountShouldNotBeFound("kfang.lessThanOrEqual=" + SMALLER_KFANG);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByKfangIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where kfang is less than DEFAULT_KFANG
        defaultCheckinAccountShouldNotBeFound("kfang.lessThan=" + DEFAULT_KFANG);

        // Get all the checkinAccountList where kfang is less than UPDATED_KFANG
        defaultCheckinAccountShouldBeFound("kfang.lessThan=" + UPDATED_KFANG);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByKfangIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where kfang is greater than DEFAULT_KFANG
        defaultCheckinAccountShouldNotBeFound("kfang.greaterThan=" + DEFAULT_KFANG);

        // Get all the checkinAccountList where kfang is greater than SMALLER_KFANG
        defaultCheckinAccountShouldBeFound("kfang.greaterThan=" + SMALLER_KFANG);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByDhuaIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where dhua equals to DEFAULT_DHUA
        defaultCheckinAccountShouldBeFound("dhua.equals=" + DEFAULT_DHUA);

        // Get all the checkinAccountList where dhua equals to UPDATED_DHUA
        defaultCheckinAccountShouldNotBeFound("dhua.equals=" + UPDATED_DHUA);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByDhuaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where dhua not equals to DEFAULT_DHUA
        defaultCheckinAccountShouldNotBeFound("dhua.notEquals=" + DEFAULT_DHUA);

        // Get all the checkinAccountList where dhua not equals to UPDATED_DHUA
        defaultCheckinAccountShouldBeFound("dhua.notEquals=" + UPDATED_DHUA);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByDhuaIsInShouldWork() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where dhua in DEFAULT_DHUA or UPDATED_DHUA
        defaultCheckinAccountShouldBeFound("dhua.in=" + DEFAULT_DHUA + "," + UPDATED_DHUA);

        // Get all the checkinAccountList where dhua equals to UPDATED_DHUA
        defaultCheckinAccountShouldNotBeFound("dhua.in=" + UPDATED_DHUA);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByDhuaIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where dhua is not null
        defaultCheckinAccountShouldBeFound("dhua.specified=true");

        // Get all the checkinAccountList where dhua is null
        defaultCheckinAccountShouldNotBeFound("dhua.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByDhuaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where dhua is greater than or equal to DEFAULT_DHUA
        defaultCheckinAccountShouldBeFound("dhua.greaterThanOrEqual=" + DEFAULT_DHUA);

        // Get all the checkinAccountList where dhua is greater than or equal to UPDATED_DHUA
        defaultCheckinAccountShouldNotBeFound("dhua.greaterThanOrEqual=" + UPDATED_DHUA);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByDhuaIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where dhua is less than or equal to DEFAULT_DHUA
        defaultCheckinAccountShouldBeFound("dhua.lessThanOrEqual=" + DEFAULT_DHUA);

        // Get all the checkinAccountList where dhua is less than or equal to SMALLER_DHUA
        defaultCheckinAccountShouldNotBeFound("dhua.lessThanOrEqual=" + SMALLER_DHUA);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByDhuaIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where dhua is less than DEFAULT_DHUA
        defaultCheckinAccountShouldNotBeFound("dhua.lessThan=" + DEFAULT_DHUA);

        // Get all the checkinAccountList where dhua is less than UPDATED_DHUA
        defaultCheckinAccountShouldBeFound("dhua.lessThan=" + UPDATED_DHUA);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByDhuaIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where dhua is greater than DEFAULT_DHUA
        defaultCheckinAccountShouldNotBeFound("dhua.greaterThan=" + DEFAULT_DHUA);

        // Get all the checkinAccountList where dhua is greater than SMALLER_DHUA
        defaultCheckinAccountShouldBeFound("dhua.greaterThan=" + SMALLER_DHUA);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByMininIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where minin equals to DEFAULT_MININ
        defaultCheckinAccountShouldBeFound("minin.equals=" + DEFAULT_MININ);

        // Get all the checkinAccountList where minin equals to UPDATED_MININ
        defaultCheckinAccountShouldNotBeFound("minin.equals=" + UPDATED_MININ);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByMininIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where minin not equals to DEFAULT_MININ
        defaultCheckinAccountShouldNotBeFound("minin.notEquals=" + DEFAULT_MININ);

        // Get all the checkinAccountList where minin not equals to UPDATED_MININ
        defaultCheckinAccountShouldBeFound("minin.notEquals=" + UPDATED_MININ);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByMininIsInShouldWork() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where minin in DEFAULT_MININ or UPDATED_MININ
        defaultCheckinAccountShouldBeFound("minin.in=" + DEFAULT_MININ + "," + UPDATED_MININ);

        // Get all the checkinAccountList where minin equals to UPDATED_MININ
        defaultCheckinAccountShouldNotBeFound("minin.in=" + UPDATED_MININ);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByMininIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where minin is not null
        defaultCheckinAccountShouldBeFound("minin.specified=true");

        // Get all the checkinAccountList where minin is null
        defaultCheckinAccountShouldNotBeFound("minin.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByMininIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where minin is greater than or equal to DEFAULT_MININ
        defaultCheckinAccountShouldBeFound("minin.greaterThanOrEqual=" + DEFAULT_MININ);

        // Get all the checkinAccountList where minin is greater than or equal to UPDATED_MININ
        defaultCheckinAccountShouldNotBeFound("minin.greaterThanOrEqual=" + UPDATED_MININ);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByMininIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where minin is less than or equal to DEFAULT_MININ
        defaultCheckinAccountShouldBeFound("minin.lessThanOrEqual=" + DEFAULT_MININ);

        // Get all the checkinAccountList where minin is less than or equal to SMALLER_MININ
        defaultCheckinAccountShouldNotBeFound("minin.lessThanOrEqual=" + SMALLER_MININ);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByMininIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where minin is less than DEFAULT_MININ
        defaultCheckinAccountShouldNotBeFound("minin.lessThan=" + DEFAULT_MININ);

        // Get all the checkinAccountList where minin is less than UPDATED_MININ
        defaultCheckinAccountShouldBeFound("minin.lessThan=" + UPDATED_MININ);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByMininIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where minin is greater than DEFAULT_MININ
        defaultCheckinAccountShouldNotBeFound("minin.greaterThan=" + DEFAULT_MININ);

        // Get all the checkinAccountList where minin is greater than SMALLER_MININ
        defaultCheckinAccountShouldBeFound("minin.greaterThan=" + SMALLER_MININ);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByPeichIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where peich equals to DEFAULT_PEICH
        defaultCheckinAccountShouldBeFound("peich.equals=" + DEFAULT_PEICH);

        // Get all the checkinAccountList where peich equals to UPDATED_PEICH
        defaultCheckinAccountShouldNotBeFound("peich.equals=" + UPDATED_PEICH);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByPeichIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where peich not equals to DEFAULT_PEICH
        defaultCheckinAccountShouldNotBeFound("peich.notEquals=" + DEFAULT_PEICH);

        // Get all the checkinAccountList where peich not equals to UPDATED_PEICH
        defaultCheckinAccountShouldBeFound("peich.notEquals=" + UPDATED_PEICH);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByPeichIsInShouldWork() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where peich in DEFAULT_PEICH or UPDATED_PEICH
        defaultCheckinAccountShouldBeFound("peich.in=" + DEFAULT_PEICH + "," + UPDATED_PEICH);

        // Get all the checkinAccountList where peich equals to UPDATED_PEICH
        defaultCheckinAccountShouldNotBeFound("peich.in=" + UPDATED_PEICH);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByPeichIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where peich is not null
        defaultCheckinAccountShouldBeFound("peich.specified=true");

        // Get all the checkinAccountList where peich is null
        defaultCheckinAccountShouldNotBeFound("peich.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByPeichIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where peich is greater than or equal to DEFAULT_PEICH
        defaultCheckinAccountShouldBeFound("peich.greaterThanOrEqual=" + DEFAULT_PEICH);

        // Get all the checkinAccountList where peich is greater than or equal to UPDATED_PEICH
        defaultCheckinAccountShouldNotBeFound("peich.greaterThanOrEqual=" + UPDATED_PEICH);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByPeichIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where peich is less than or equal to DEFAULT_PEICH
        defaultCheckinAccountShouldBeFound("peich.lessThanOrEqual=" + DEFAULT_PEICH);

        // Get all the checkinAccountList where peich is less than or equal to SMALLER_PEICH
        defaultCheckinAccountShouldNotBeFound("peich.lessThanOrEqual=" + SMALLER_PEICH);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByPeichIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where peich is less than DEFAULT_PEICH
        defaultCheckinAccountShouldNotBeFound("peich.lessThan=" + DEFAULT_PEICH);

        // Get all the checkinAccountList where peich is less than UPDATED_PEICH
        defaultCheckinAccountShouldBeFound("peich.lessThan=" + UPDATED_PEICH);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByPeichIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where peich is greater than DEFAULT_PEICH
        defaultCheckinAccountShouldNotBeFound("peich.greaterThan=" + DEFAULT_PEICH);

        // Get all the checkinAccountList where peich is greater than SMALLER_PEICH
        defaultCheckinAccountShouldBeFound("peich.greaterThan=" + SMALLER_PEICH);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByQitIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where qit equals to DEFAULT_QIT
        defaultCheckinAccountShouldBeFound("qit.equals=" + DEFAULT_QIT);

        // Get all the checkinAccountList where qit equals to UPDATED_QIT
        defaultCheckinAccountShouldNotBeFound("qit.equals=" + UPDATED_QIT);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByQitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where qit not equals to DEFAULT_QIT
        defaultCheckinAccountShouldNotBeFound("qit.notEquals=" + DEFAULT_QIT);

        // Get all the checkinAccountList where qit not equals to UPDATED_QIT
        defaultCheckinAccountShouldBeFound("qit.notEquals=" + UPDATED_QIT);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByQitIsInShouldWork() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where qit in DEFAULT_QIT or UPDATED_QIT
        defaultCheckinAccountShouldBeFound("qit.in=" + DEFAULT_QIT + "," + UPDATED_QIT);

        // Get all the checkinAccountList where qit equals to UPDATED_QIT
        defaultCheckinAccountShouldNotBeFound("qit.in=" + UPDATED_QIT);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByQitIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where qit is not null
        defaultCheckinAccountShouldBeFound("qit.specified=true");

        // Get all the checkinAccountList where qit is null
        defaultCheckinAccountShouldNotBeFound("qit.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByQitIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where qit is greater than or equal to DEFAULT_QIT
        defaultCheckinAccountShouldBeFound("qit.greaterThanOrEqual=" + DEFAULT_QIT);

        // Get all the checkinAccountList where qit is greater than or equal to UPDATED_QIT
        defaultCheckinAccountShouldNotBeFound("qit.greaterThanOrEqual=" + UPDATED_QIT);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByQitIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where qit is less than or equal to DEFAULT_QIT
        defaultCheckinAccountShouldBeFound("qit.lessThanOrEqual=" + DEFAULT_QIT);

        // Get all the checkinAccountList where qit is less than or equal to SMALLER_QIT
        defaultCheckinAccountShouldNotBeFound("qit.lessThanOrEqual=" + SMALLER_QIT);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByQitIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where qit is less than DEFAULT_QIT
        defaultCheckinAccountShouldNotBeFound("qit.lessThan=" + DEFAULT_QIT);

        // Get all the checkinAccountList where qit is less than UPDATED_QIT
        defaultCheckinAccountShouldBeFound("qit.lessThan=" + UPDATED_QIT);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByQitIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where qit is greater than DEFAULT_QIT
        defaultCheckinAccountShouldNotBeFound("qit.greaterThan=" + DEFAULT_QIT);

        // Get all the checkinAccountList where qit is greater than SMALLER_QIT
        defaultCheckinAccountShouldBeFound("qit.greaterThan=" + SMALLER_QIT);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where total equals to DEFAULT_TOTAL
        defaultCheckinAccountShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the checkinAccountList where total equals to UPDATED_TOTAL
        defaultCheckinAccountShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where total not equals to DEFAULT_TOTAL
        defaultCheckinAccountShouldNotBeFound("total.notEquals=" + DEFAULT_TOTAL);

        // Get all the checkinAccountList where total not equals to UPDATED_TOTAL
        defaultCheckinAccountShouldBeFound("total.notEquals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultCheckinAccountShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the checkinAccountList where total equals to UPDATED_TOTAL
        defaultCheckinAccountShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where total is not null
        defaultCheckinAccountShouldBeFound("total.specified=true");

        // Get all the checkinAccountList where total is null
        defaultCheckinAccountShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where total is greater than or equal to DEFAULT_TOTAL
        defaultCheckinAccountShouldBeFound("total.greaterThanOrEqual=" + DEFAULT_TOTAL);

        // Get all the checkinAccountList where total is greater than or equal to UPDATED_TOTAL
        defaultCheckinAccountShouldNotBeFound("total.greaterThanOrEqual=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where total is less than or equal to DEFAULT_TOTAL
        defaultCheckinAccountShouldBeFound("total.lessThanOrEqual=" + DEFAULT_TOTAL);

        // Get all the checkinAccountList where total is less than or equal to SMALLER_TOTAL
        defaultCheckinAccountShouldNotBeFound("total.lessThanOrEqual=" + SMALLER_TOTAL);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where total is less than DEFAULT_TOTAL
        defaultCheckinAccountShouldNotBeFound("total.lessThan=" + DEFAULT_TOTAL);

        // Get all the checkinAccountList where total is less than UPDATED_TOTAL
        defaultCheckinAccountShouldBeFound("total.lessThan=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void getAllCheckinAccountsByTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        // Get all the checkinAccountList where total is greater than DEFAULT_TOTAL
        defaultCheckinAccountShouldNotBeFound("total.greaterThan=" + DEFAULT_TOTAL);

        // Get all the checkinAccountList where total is greater than SMALLER_TOTAL
        defaultCheckinAccountShouldBeFound("total.greaterThan=" + SMALLER_TOTAL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckinAccountShouldBeFound(String filter) throws Exception {
        restCheckinAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkinAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].indatetime").value(hasItem(DEFAULT_INDATETIME.toString())))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].kfang").value(hasItem(sameNumber(DEFAULT_KFANG))))
            .andExpect(jsonPath("$.[*].dhua").value(hasItem(sameNumber(DEFAULT_DHUA))))
            .andExpect(jsonPath("$.[*].minin").value(hasItem(sameNumber(DEFAULT_MININ))))
            .andExpect(jsonPath("$.[*].peich").value(hasItem(sameNumber(DEFAULT_PEICH))))
            .andExpect(jsonPath("$.[*].qit").value(hasItem(sameNumber(DEFAULT_QIT))))
            .andExpect(jsonPath("$.[*].total").value(hasItem(sameNumber(DEFAULT_TOTAL))));

        // Check, that the count call also returns 1
        restCheckinAccountMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckinAccountShouldNotBeFound(String filter) throws Exception {
        restCheckinAccountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckinAccountMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCheckinAccount() throws Exception {
        // Get the checkinAccount
        restCheckinAccountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCheckinAccount() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        int databaseSizeBeforeUpdate = checkinAccountRepository.findAll().size();

        // Update the checkinAccount
        CheckinAccount updatedCheckinAccount = checkinAccountRepository.findById(checkinAccount.getId()).get();
        // Disconnect from session so that the updates on updatedCheckinAccount are not directly saved in db
        em.detach(updatedCheckinAccount);
        updatedCheckinAccount
            .account(UPDATED_ACCOUNT)
            .roomn(UPDATED_ROOMN)
            .indatetime(UPDATED_INDATETIME)
            .gotime(UPDATED_GOTIME)
            .kfang(UPDATED_KFANG)
            .dhua(UPDATED_DHUA)
            .minin(UPDATED_MININ)
            .peich(UPDATED_PEICH)
            .qit(UPDATED_QIT)
            .total(UPDATED_TOTAL);
        CheckinAccountDTO checkinAccountDTO = checkinAccountMapper.toDto(updatedCheckinAccount);

        restCheckinAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkinAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkinAccountDTO))
            )
            .andExpect(status().isOk());

        // Validate the CheckinAccount in the database
        List<CheckinAccount> checkinAccountList = checkinAccountRepository.findAll();
        assertThat(checkinAccountList).hasSize(databaseSizeBeforeUpdate);
        CheckinAccount testCheckinAccount = checkinAccountList.get(checkinAccountList.size() - 1);
        assertThat(testCheckinAccount.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCheckinAccount.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCheckinAccount.getIndatetime()).isEqualTo(UPDATED_INDATETIME);
        assertThat(testCheckinAccount.getGotime()).isEqualTo(UPDATED_GOTIME);
        assertThat(testCheckinAccount.getKfang()).isEqualTo(UPDATED_KFANG);
        assertThat(testCheckinAccount.getDhua()).isEqualTo(UPDATED_DHUA);
        assertThat(testCheckinAccount.getMinin()).isEqualTo(UPDATED_MININ);
        assertThat(testCheckinAccount.getPeich()).isEqualTo(UPDATED_PEICH);
        assertThat(testCheckinAccount.getQit()).isEqualTo(UPDATED_QIT);
        assertThat(testCheckinAccount.getTotal()).isEqualTo(UPDATED_TOTAL);

        // Validate the CheckinAccount in Elasticsearch
        verify(mockCheckinAccountSearchRepository).save(testCheckinAccount);
    }

    @Test
    @Transactional
    void putNonExistingCheckinAccount() throws Exception {
        int databaseSizeBeforeUpdate = checkinAccountRepository.findAll().size();
        checkinAccount.setId(count.incrementAndGet());

        // Create the CheckinAccount
        CheckinAccountDTO checkinAccountDTO = checkinAccountMapper.toDto(checkinAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckinAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkinAccountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkinAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckinAccount in the database
        List<CheckinAccount> checkinAccountList = checkinAccountRepository.findAll();
        assertThat(checkinAccountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinAccount in Elasticsearch
        verify(mockCheckinAccountSearchRepository, times(0)).save(checkinAccount);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckinAccount() throws Exception {
        int databaseSizeBeforeUpdate = checkinAccountRepository.findAll().size();
        checkinAccount.setId(count.incrementAndGet());

        // Create the CheckinAccount
        CheckinAccountDTO checkinAccountDTO = checkinAccountMapper.toDto(checkinAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinAccountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkinAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckinAccount in the database
        List<CheckinAccount> checkinAccountList = checkinAccountRepository.findAll();
        assertThat(checkinAccountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinAccount in Elasticsearch
        verify(mockCheckinAccountSearchRepository, times(0)).save(checkinAccount);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckinAccount() throws Exception {
        int databaseSizeBeforeUpdate = checkinAccountRepository.findAll().size();
        checkinAccount.setId(count.incrementAndGet());

        // Create the CheckinAccount
        CheckinAccountDTO checkinAccountDTO = checkinAccountMapper.toDto(checkinAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinAccountMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckinAccount in the database
        List<CheckinAccount> checkinAccountList = checkinAccountRepository.findAll();
        assertThat(checkinAccountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinAccount in Elasticsearch
        verify(mockCheckinAccountSearchRepository, times(0)).save(checkinAccount);
    }

    @Test
    @Transactional
    void partialUpdateCheckinAccountWithPatch() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        int databaseSizeBeforeUpdate = checkinAccountRepository.findAll().size();

        // Update the checkinAccount using partial update
        CheckinAccount partialUpdatedCheckinAccount = new CheckinAccount();
        partialUpdatedCheckinAccount.setId(checkinAccount.getId());

        partialUpdatedCheckinAccount
            .account(UPDATED_ACCOUNT)
            .roomn(UPDATED_ROOMN)
            .indatetime(UPDATED_INDATETIME)
            .gotime(UPDATED_GOTIME)
            .qit(UPDATED_QIT)
            .total(UPDATED_TOTAL);

        restCheckinAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckinAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckinAccount))
            )
            .andExpect(status().isOk());

        // Validate the CheckinAccount in the database
        List<CheckinAccount> checkinAccountList = checkinAccountRepository.findAll();
        assertThat(checkinAccountList).hasSize(databaseSizeBeforeUpdate);
        CheckinAccount testCheckinAccount = checkinAccountList.get(checkinAccountList.size() - 1);
        assertThat(testCheckinAccount.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCheckinAccount.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCheckinAccount.getIndatetime()).isEqualTo(UPDATED_INDATETIME);
        assertThat(testCheckinAccount.getGotime()).isEqualTo(UPDATED_GOTIME);
        assertThat(testCheckinAccount.getKfang()).isEqualByComparingTo(DEFAULT_KFANG);
        assertThat(testCheckinAccount.getDhua()).isEqualByComparingTo(DEFAULT_DHUA);
        assertThat(testCheckinAccount.getMinin()).isEqualByComparingTo(DEFAULT_MININ);
        assertThat(testCheckinAccount.getPeich()).isEqualByComparingTo(DEFAULT_PEICH);
        assertThat(testCheckinAccount.getQit()).isEqualByComparingTo(UPDATED_QIT);
        assertThat(testCheckinAccount.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void fullUpdateCheckinAccountWithPatch() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        int databaseSizeBeforeUpdate = checkinAccountRepository.findAll().size();

        // Update the checkinAccount using partial update
        CheckinAccount partialUpdatedCheckinAccount = new CheckinAccount();
        partialUpdatedCheckinAccount.setId(checkinAccount.getId());

        partialUpdatedCheckinAccount
            .account(UPDATED_ACCOUNT)
            .roomn(UPDATED_ROOMN)
            .indatetime(UPDATED_INDATETIME)
            .gotime(UPDATED_GOTIME)
            .kfang(UPDATED_KFANG)
            .dhua(UPDATED_DHUA)
            .minin(UPDATED_MININ)
            .peich(UPDATED_PEICH)
            .qit(UPDATED_QIT)
            .total(UPDATED_TOTAL);

        restCheckinAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckinAccount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckinAccount))
            )
            .andExpect(status().isOk());

        // Validate the CheckinAccount in the database
        List<CheckinAccount> checkinAccountList = checkinAccountRepository.findAll();
        assertThat(checkinAccountList).hasSize(databaseSizeBeforeUpdate);
        CheckinAccount testCheckinAccount = checkinAccountList.get(checkinAccountList.size() - 1);
        assertThat(testCheckinAccount.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCheckinAccount.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCheckinAccount.getIndatetime()).isEqualTo(UPDATED_INDATETIME);
        assertThat(testCheckinAccount.getGotime()).isEqualTo(UPDATED_GOTIME);
        assertThat(testCheckinAccount.getKfang()).isEqualByComparingTo(UPDATED_KFANG);
        assertThat(testCheckinAccount.getDhua()).isEqualByComparingTo(UPDATED_DHUA);
        assertThat(testCheckinAccount.getMinin()).isEqualByComparingTo(UPDATED_MININ);
        assertThat(testCheckinAccount.getPeich()).isEqualByComparingTo(UPDATED_PEICH);
        assertThat(testCheckinAccount.getQit()).isEqualByComparingTo(UPDATED_QIT);
        assertThat(testCheckinAccount.getTotal()).isEqualByComparingTo(UPDATED_TOTAL);
    }

    @Test
    @Transactional
    void patchNonExistingCheckinAccount() throws Exception {
        int databaseSizeBeforeUpdate = checkinAccountRepository.findAll().size();
        checkinAccount.setId(count.incrementAndGet());

        // Create the CheckinAccount
        CheckinAccountDTO checkinAccountDTO = checkinAccountMapper.toDto(checkinAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckinAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkinAccountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkinAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckinAccount in the database
        List<CheckinAccount> checkinAccountList = checkinAccountRepository.findAll();
        assertThat(checkinAccountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinAccount in Elasticsearch
        verify(mockCheckinAccountSearchRepository, times(0)).save(checkinAccount);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckinAccount() throws Exception {
        int databaseSizeBeforeUpdate = checkinAccountRepository.findAll().size();
        checkinAccount.setId(count.incrementAndGet());

        // Create the CheckinAccount
        CheckinAccountDTO checkinAccountDTO = checkinAccountMapper.toDto(checkinAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinAccountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkinAccountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckinAccount in the database
        List<CheckinAccount> checkinAccountList = checkinAccountRepository.findAll();
        assertThat(checkinAccountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinAccount in Elasticsearch
        verify(mockCheckinAccountSearchRepository, times(0)).save(checkinAccount);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckinAccount() throws Exception {
        int databaseSizeBeforeUpdate = checkinAccountRepository.findAll().size();
        checkinAccount.setId(count.incrementAndGet());

        // Create the CheckinAccount
        CheckinAccountDTO checkinAccountDTO = checkinAccountMapper.toDto(checkinAccount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinAccountMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkinAccountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckinAccount in the database
        List<CheckinAccount> checkinAccountList = checkinAccountRepository.findAll();
        assertThat(checkinAccountList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinAccount in Elasticsearch
        verify(mockCheckinAccountSearchRepository, times(0)).save(checkinAccount);
    }

    @Test
    @Transactional
    void deleteCheckinAccount() throws Exception {
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);

        int databaseSizeBeforeDelete = checkinAccountRepository.findAll().size();

        // Delete the checkinAccount
        restCheckinAccountMockMvc
            .perform(delete(ENTITY_API_URL_ID, checkinAccount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckinAccount> checkinAccountList = checkinAccountRepository.findAll();
        assertThat(checkinAccountList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CheckinAccount in Elasticsearch
        verify(mockCheckinAccountSearchRepository, times(1)).deleteById(checkinAccount.getId());
    }

    @Test
    @Transactional
    void searchCheckinAccount() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        checkinAccountRepository.saveAndFlush(checkinAccount);
        when(mockCheckinAccountSearchRepository.search(queryStringQuery("id:" + checkinAccount.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(checkinAccount), PageRequest.of(0, 1), 1));

        // Search the checkinAccount
        restCheckinAccountMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + checkinAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkinAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].indatetime").value(hasItem(DEFAULT_INDATETIME.toString())))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].kfang").value(hasItem(sameNumber(DEFAULT_KFANG))))
            .andExpect(jsonPath("$.[*].dhua").value(hasItem(sameNumber(DEFAULT_DHUA))))
            .andExpect(jsonPath("$.[*].minin").value(hasItem(sameNumber(DEFAULT_MININ))))
            .andExpect(jsonPath("$.[*].peich").value(hasItem(sameNumber(DEFAULT_PEICH))))
            .andExpect(jsonPath("$.[*].qit").value(hasItem(sameNumber(DEFAULT_QIT))))
            .andExpect(jsonPath("$.[*].total").value(hasItem(sameNumber(DEFAULT_TOTAL))));
    }
}
