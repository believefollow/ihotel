package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Cardysq;
import ihotel.app.repository.CardysqRepository;
import ihotel.app.repository.search.CardysqSearchRepository;
import ihotel.app.service.criteria.CardysqCriteria;
import ihotel.app.service.dto.CardysqDTO;
import ihotel.app.service.mapper.CardysqMapper;
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
 * Integration tests for the {@link CardysqResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CardysqResourceIT {

    private static final String DEFAULT_ROOMN = "AAAAAAAAAA";
    private static final String UPDATED_ROOMN = "BBBBBBBBBB";

    private static final String DEFAULT_GUESTNAME = "AAAAAAAAAA";
    private static final String UPDATED_GUESTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT = "BBBBBBBBBB";

    private static final Instant DEFAULT_RQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CARDID = "AAAAAAAAAA";
    private static final String UPDATED_CARDID = "BBBBBBBBBB";

    private static final String DEFAULT_DJH = "AAAAAAAAAA";
    private static final String UPDATED_DJH = "BBBBBBBBBB";

    private static final String DEFAULT_SQH = "AAAAAAAAAA";
    private static final String UPDATED_SQH = "BBBBBBBBBB";

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final String DEFAULT_SIGN = "AA";
    private static final String UPDATED_SIGN = "BB";

    private static final Instant DEFAULT_HOTELTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOTELTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_YXRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_YXRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_JE = new BigDecimal(1);
    private static final BigDecimal UPDATED_JE = new BigDecimal(2);
    private static final BigDecimal SMALLER_JE = new BigDecimal(1 - 1);

    private static final String DEFAULT_YSQMEMO = "AAAAAAAAAA";
    private static final String UPDATED_YSQMEMO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cardysqs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/cardysqs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CardysqRepository cardysqRepository;

    @Autowired
    private CardysqMapper cardysqMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CardysqSearchRepositoryMockConfiguration
     */
    @Autowired
    private CardysqSearchRepository mockCardysqSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCardysqMockMvc;

    private Cardysq cardysq;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cardysq createEntity(EntityManager em) {
        Cardysq cardysq = new Cardysq()
            .roomn(DEFAULT_ROOMN)
            .guestname(DEFAULT_GUESTNAME)
            .account(DEFAULT_ACCOUNT)
            .rq(DEFAULT_RQ)
            .cardid(DEFAULT_CARDID)
            .djh(DEFAULT_DJH)
            .sqh(DEFAULT_SQH)
            .empn(DEFAULT_EMPN)
            .sign(DEFAULT_SIGN)
            .hoteltime(DEFAULT_HOTELTIME)
            .yxrq(DEFAULT_YXRQ)
            .je(DEFAULT_JE)
            .ysqmemo(DEFAULT_YSQMEMO);
        return cardysq;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cardysq createUpdatedEntity(EntityManager em) {
        Cardysq cardysq = new Cardysq()
            .roomn(UPDATED_ROOMN)
            .guestname(UPDATED_GUESTNAME)
            .account(UPDATED_ACCOUNT)
            .rq(UPDATED_RQ)
            .cardid(UPDATED_CARDID)
            .djh(UPDATED_DJH)
            .sqh(UPDATED_SQH)
            .empn(UPDATED_EMPN)
            .sign(UPDATED_SIGN)
            .hoteltime(UPDATED_HOTELTIME)
            .yxrq(UPDATED_YXRQ)
            .je(UPDATED_JE)
            .ysqmemo(UPDATED_YSQMEMO);
        return cardysq;
    }

    @BeforeEach
    public void initTest() {
        cardysq = createEntity(em);
    }

    @Test
    @Transactional
    void createCardysq() throws Exception {
        int databaseSizeBeforeCreate = cardysqRepository.findAll().size();
        // Create the Cardysq
        CardysqDTO cardysqDTO = cardysqMapper.toDto(cardysq);
        restCardysqMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cardysqDTO)))
            .andExpect(status().isCreated());

        // Validate the Cardysq in the database
        List<Cardysq> cardysqList = cardysqRepository.findAll();
        assertThat(cardysqList).hasSize(databaseSizeBeforeCreate + 1);
        Cardysq testCardysq = cardysqList.get(cardysqList.size() - 1);
        assertThat(testCardysq.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testCardysq.getGuestname()).isEqualTo(DEFAULT_GUESTNAME);
        assertThat(testCardysq.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testCardysq.getRq()).isEqualTo(DEFAULT_RQ);
        assertThat(testCardysq.getCardid()).isEqualTo(DEFAULT_CARDID);
        assertThat(testCardysq.getDjh()).isEqualTo(DEFAULT_DJH);
        assertThat(testCardysq.getSqh()).isEqualTo(DEFAULT_SQH);
        assertThat(testCardysq.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testCardysq.getSign()).isEqualTo(DEFAULT_SIGN);
        assertThat(testCardysq.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testCardysq.getYxrq()).isEqualTo(DEFAULT_YXRQ);
        assertThat(testCardysq.getJe()).isEqualByComparingTo(DEFAULT_JE);
        assertThat(testCardysq.getYsqmemo()).isEqualTo(DEFAULT_YSQMEMO);

        // Validate the Cardysq in Elasticsearch
        verify(mockCardysqSearchRepository, times(1)).save(testCardysq);
    }

    @Test
    @Transactional
    void createCardysqWithExistingId() throws Exception {
        // Create the Cardysq with an existing ID
        cardysq.setId(1L);
        CardysqDTO cardysqDTO = cardysqMapper.toDto(cardysq);

        int databaseSizeBeforeCreate = cardysqRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCardysqMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cardysqDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cardysq in the database
        List<Cardysq> cardysqList = cardysqRepository.findAll();
        assertThat(cardysqList).hasSize(databaseSizeBeforeCreate);

        // Validate the Cardysq in Elasticsearch
        verify(mockCardysqSearchRepository, times(0)).save(cardysq);
    }

    @Test
    @Transactional
    void getAllCardysqs() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList
        restCardysqMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cardysq.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].cardid").value(hasItem(DEFAULT_CARDID)))
            .andExpect(jsonPath("$.[*].djh").value(hasItem(DEFAULT_DJH)))
            .andExpect(jsonPath("$.[*].sqh").value(hasItem(DEFAULT_SQH)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN)))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].yxrq").value(hasItem(DEFAULT_YXRQ.toString())))
            .andExpect(jsonPath("$.[*].je").value(hasItem(sameNumber(DEFAULT_JE))))
            .andExpect(jsonPath("$.[*].ysqmemo").value(hasItem(DEFAULT_YSQMEMO)));
    }

    @Test
    @Transactional
    void getCardysq() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get the cardysq
        restCardysqMockMvc
            .perform(get(ENTITY_API_URL_ID, cardysq.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cardysq.getId().intValue()))
            .andExpect(jsonPath("$.roomn").value(DEFAULT_ROOMN))
            .andExpect(jsonPath("$.guestname").value(DEFAULT_GUESTNAME))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT))
            .andExpect(jsonPath("$.rq").value(DEFAULT_RQ.toString()))
            .andExpect(jsonPath("$.cardid").value(DEFAULT_CARDID))
            .andExpect(jsonPath("$.djh").value(DEFAULT_DJH))
            .andExpect(jsonPath("$.sqh").value(DEFAULT_SQH))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.sign").value(DEFAULT_SIGN))
            .andExpect(jsonPath("$.hoteltime").value(DEFAULT_HOTELTIME.toString()))
            .andExpect(jsonPath("$.yxrq").value(DEFAULT_YXRQ.toString()))
            .andExpect(jsonPath("$.je").value(sameNumber(DEFAULT_JE)))
            .andExpect(jsonPath("$.ysqmemo").value(DEFAULT_YSQMEMO));
    }

    @Test
    @Transactional
    void getCardysqsByIdFiltering() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        Long id = cardysq.getId();

        defaultCardysqShouldBeFound("id.equals=" + id);
        defaultCardysqShouldNotBeFound("id.notEquals=" + id);

        defaultCardysqShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCardysqShouldNotBeFound("id.greaterThan=" + id);

        defaultCardysqShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCardysqShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCardysqsByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where roomn equals to DEFAULT_ROOMN
        defaultCardysqShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the cardysqList where roomn equals to UPDATED_ROOMN
        defaultCardysqShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCardysqsByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where roomn not equals to DEFAULT_ROOMN
        defaultCardysqShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the cardysqList where roomn not equals to UPDATED_ROOMN
        defaultCardysqShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCardysqsByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultCardysqShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the cardysqList where roomn equals to UPDATED_ROOMN
        defaultCardysqShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCardysqsByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where roomn is not null
        defaultCardysqShouldBeFound("roomn.specified=true");

        // Get all the cardysqList where roomn is null
        defaultCardysqShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllCardysqsByRoomnContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where roomn contains DEFAULT_ROOMN
        defaultCardysqShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the cardysqList where roomn contains UPDATED_ROOMN
        defaultCardysqShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCardysqsByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where roomn does not contain DEFAULT_ROOMN
        defaultCardysqShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the cardysqList where roomn does not contain UPDATED_ROOMN
        defaultCardysqShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCardysqsByGuestnameIsEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where guestname equals to DEFAULT_GUESTNAME
        defaultCardysqShouldBeFound("guestname.equals=" + DEFAULT_GUESTNAME);

        // Get all the cardysqList where guestname equals to UPDATED_GUESTNAME
        defaultCardysqShouldNotBeFound("guestname.equals=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCardysqsByGuestnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where guestname not equals to DEFAULT_GUESTNAME
        defaultCardysqShouldNotBeFound("guestname.notEquals=" + DEFAULT_GUESTNAME);

        // Get all the cardysqList where guestname not equals to UPDATED_GUESTNAME
        defaultCardysqShouldBeFound("guestname.notEquals=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCardysqsByGuestnameIsInShouldWork() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where guestname in DEFAULT_GUESTNAME or UPDATED_GUESTNAME
        defaultCardysqShouldBeFound("guestname.in=" + DEFAULT_GUESTNAME + "," + UPDATED_GUESTNAME);

        // Get all the cardysqList where guestname equals to UPDATED_GUESTNAME
        defaultCardysqShouldNotBeFound("guestname.in=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCardysqsByGuestnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where guestname is not null
        defaultCardysqShouldBeFound("guestname.specified=true");

        // Get all the cardysqList where guestname is null
        defaultCardysqShouldNotBeFound("guestname.specified=false");
    }

    @Test
    @Transactional
    void getAllCardysqsByGuestnameContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where guestname contains DEFAULT_GUESTNAME
        defaultCardysqShouldBeFound("guestname.contains=" + DEFAULT_GUESTNAME);

        // Get all the cardysqList where guestname contains UPDATED_GUESTNAME
        defaultCardysqShouldNotBeFound("guestname.contains=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCardysqsByGuestnameNotContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where guestname does not contain DEFAULT_GUESTNAME
        defaultCardysqShouldNotBeFound("guestname.doesNotContain=" + DEFAULT_GUESTNAME);

        // Get all the cardysqList where guestname does not contain UPDATED_GUESTNAME
        defaultCardysqShouldBeFound("guestname.doesNotContain=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCardysqsByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where account equals to DEFAULT_ACCOUNT
        defaultCardysqShouldBeFound("account.equals=" + DEFAULT_ACCOUNT);

        // Get all the cardysqList where account equals to UPDATED_ACCOUNT
        defaultCardysqShouldNotBeFound("account.equals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCardysqsByAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where account not equals to DEFAULT_ACCOUNT
        defaultCardysqShouldNotBeFound("account.notEquals=" + DEFAULT_ACCOUNT);

        // Get all the cardysqList where account not equals to UPDATED_ACCOUNT
        defaultCardysqShouldBeFound("account.notEquals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCardysqsByAccountIsInShouldWork() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where account in DEFAULT_ACCOUNT or UPDATED_ACCOUNT
        defaultCardysqShouldBeFound("account.in=" + DEFAULT_ACCOUNT + "," + UPDATED_ACCOUNT);

        // Get all the cardysqList where account equals to UPDATED_ACCOUNT
        defaultCardysqShouldNotBeFound("account.in=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCardysqsByAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where account is not null
        defaultCardysqShouldBeFound("account.specified=true");

        // Get all the cardysqList where account is null
        defaultCardysqShouldNotBeFound("account.specified=false");
    }

    @Test
    @Transactional
    void getAllCardysqsByAccountContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where account contains DEFAULT_ACCOUNT
        defaultCardysqShouldBeFound("account.contains=" + DEFAULT_ACCOUNT);

        // Get all the cardysqList where account contains UPDATED_ACCOUNT
        defaultCardysqShouldNotBeFound("account.contains=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCardysqsByAccountNotContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where account does not contain DEFAULT_ACCOUNT
        defaultCardysqShouldNotBeFound("account.doesNotContain=" + DEFAULT_ACCOUNT);

        // Get all the cardysqList where account does not contain UPDATED_ACCOUNT
        defaultCardysqShouldBeFound("account.doesNotContain=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCardysqsByRqIsEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where rq equals to DEFAULT_RQ
        defaultCardysqShouldBeFound("rq.equals=" + DEFAULT_RQ);

        // Get all the cardysqList where rq equals to UPDATED_RQ
        defaultCardysqShouldNotBeFound("rq.equals=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllCardysqsByRqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where rq not equals to DEFAULT_RQ
        defaultCardysqShouldNotBeFound("rq.notEquals=" + DEFAULT_RQ);

        // Get all the cardysqList where rq not equals to UPDATED_RQ
        defaultCardysqShouldBeFound("rq.notEquals=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllCardysqsByRqIsInShouldWork() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where rq in DEFAULT_RQ or UPDATED_RQ
        defaultCardysqShouldBeFound("rq.in=" + DEFAULT_RQ + "," + UPDATED_RQ);

        // Get all the cardysqList where rq equals to UPDATED_RQ
        defaultCardysqShouldNotBeFound("rq.in=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllCardysqsByRqIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where rq is not null
        defaultCardysqShouldBeFound("rq.specified=true");

        // Get all the cardysqList where rq is null
        defaultCardysqShouldNotBeFound("rq.specified=false");
    }

    @Test
    @Transactional
    void getAllCardysqsByCardidIsEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where cardid equals to DEFAULT_CARDID
        defaultCardysqShouldBeFound("cardid.equals=" + DEFAULT_CARDID);

        // Get all the cardysqList where cardid equals to UPDATED_CARDID
        defaultCardysqShouldNotBeFound("cardid.equals=" + UPDATED_CARDID);
    }

    @Test
    @Transactional
    void getAllCardysqsByCardidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where cardid not equals to DEFAULT_CARDID
        defaultCardysqShouldNotBeFound("cardid.notEquals=" + DEFAULT_CARDID);

        // Get all the cardysqList where cardid not equals to UPDATED_CARDID
        defaultCardysqShouldBeFound("cardid.notEquals=" + UPDATED_CARDID);
    }

    @Test
    @Transactional
    void getAllCardysqsByCardidIsInShouldWork() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where cardid in DEFAULT_CARDID or UPDATED_CARDID
        defaultCardysqShouldBeFound("cardid.in=" + DEFAULT_CARDID + "," + UPDATED_CARDID);

        // Get all the cardysqList where cardid equals to UPDATED_CARDID
        defaultCardysqShouldNotBeFound("cardid.in=" + UPDATED_CARDID);
    }

    @Test
    @Transactional
    void getAllCardysqsByCardidIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where cardid is not null
        defaultCardysqShouldBeFound("cardid.specified=true");

        // Get all the cardysqList where cardid is null
        defaultCardysqShouldNotBeFound("cardid.specified=false");
    }

    @Test
    @Transactional
    void getAllCardysqsByCardidContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where cardid contains DEFAULT_CARDID
        defaultCardysqShouldBeFound("cardid.contains=" + DEFAULT_CARDID);

        // Get all the cardysqList where cardid contains UPDATED_CARDID
        defaultCardysqShouldNotBeFound("cardid.contains=" + UPDATED_CARDID);
    }

    @Test
    @Transactional
    void getAllCardysqsByCardidNotContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where cardid does not contain DEFAULT_CARDID
        defaultCardysqShouldNotBeFound("cardid.doesNotContain=" + DEFAULT_CARDID);

        // Get all the cardysqList where cardid does not contain UPDATED_CARDID
        defaultCardysqShouldBeFound("cardid.doesNotContain=" + UPDATED_CARDID);
    }

    @Test
    @Transactional
    void getAllCardysqsByDjhIsEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where djh equals to DEFAULT_DJH
        defaultCardysqShouldBeFound("djh.equals=" + DEFAULT_DJH);

        // Get all the cardysqList where djh equals to UPDATED_DJH
        defaultCardysqShouldNotBeFound("djh.equals=" + UPDATED_DJH);
    }

    @Test
    @Transactional
    void getAllCardysqsByDjhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where djh not equals to DEFAULT_DJH
        defaultCardysqShouldNotBeFound("djh.notEquals=" + DEFAULT_DJH);

        // Get all the cardysqList where djh not equals to UPDATED_DJH
        defaultCardysqShouldBeFound("djh.notEquals=" + UPDATED_DJH);
    }

    @Test
    @Transactional
    void getAllCardysqsByDjhIsInShouldWork() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where djh in DEFAULT_DJH or UPDATED_DJH
        defaultCardysqShouldBeFound("djh.in=" + DEFAULT_DJH + "," + UPDATED_DJH);

        // Get all the cardysqList where djh equals to UPDATED_DJH
        defaultCardysqShouldNotBeFound("djh.in=" + UPDATED_DJH);
    }

    @Test
    @Transactional
    void getAllCardysqsByDjhIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where djh is not null
        defaultCardysqShouldBeFound("djh.specified=true");

        // Get all the cardysqList where djh is null
        defaultCardysqShouldNotBeFound("djh.specified=false");
    }

    @Test
    @Transactional
    void getAllCardysqsByDjhContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where djh contains DEFAULT_DJH
        defaultCardysqShouldBeFound("djh.contains=" + DEFAULT_DJH);

        // Get all the cardysqList where djh contains UPDATED_DJH
        defaultCardysqShouldNotBeFound("djh.contains=" + UPDATED_DJH);
    }

    @Test
    @Transactional
    void getAllCardysqsByDjhNotContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where djh does not contain DEFAULT_DJH
        defaultCardysqShouldNotBeFound("djh.doesNotContain=" + DEFAULT_DJH);

        // Get all the cardysqList where djh does not contain UPDATED_DJH
        defaultCardysqShouldBeFound("djh.doesNotContain=" + UPDATED_DJH);
    }

    @Test
    @Transactional
    void getAllCardysqsBySqhIsEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where sqh equals to DEFAULT_SQH
        defaultCardysqShouldBeFound("sqh.equals=" + DEFAULT_SQH);

        // Get all the cardysqList where sqh equals to UPDATED_SQH
        defaultCardysqShouldNotBeFound("sqh.equals=" + UPDATED_SQH);
    }

    @Test
    @Transactional
    void getAllCardysqsBySqhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where sqh not equals to DEFAULT_SQH
        defaultCardysqShouldNotBeFound("sqh.notEquals=" + DEFAULT_SQH);

        // Get all the cardysqList where sqh not equals to UPDATED_SQH
        defaultCardysqShouldBeFound("sqh.notEquals=" + UPDATED_SQH);
    }

    @Test
    @Transactional
    void getAllCardysqsBySqhIsInShouldWork() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where sqh in DEFAULT_SQH or UPDATED_SQH
        defaultCardysqShouldBeFound("sqh.in=" + DEFAULT_SQH + "," + UPDATED_SQH);

        // Get all the cardysqList where sqh equals to UPDATED_SQH
        defaultCardysqShouldNotBeFound("sqh.in=" + UPDATED_SQH);
    }

    @Test
    @Transactional
    void getAllCardysqsBySqhIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where sqh is not null
        defaultCardysqShouldBeFound("sqh.specified=true");

        // Get all the cardysqList where sqh is null
        defaultCardysqShouldNotBeFound("sqh.specified=false");
    }

    @Test
    @Transactional
    void getAllCardysqsBySqhContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where sqh contains DEFAULT_SQH
        defaultCardysqShouldBeFound("sqh.contains=" + DEFAULT_SQH);

        // Get all the cardysqList where sqh contains UPDATED_SQH
        defaultCardysqShouldNotBeFound("sqh.contains=" + UPDATED_SQH);
    }

    @Test
    @Transactional
    void getAllCardysqsBySqhNotContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where sqh does not contain DEFAULT_SQH
        defaultCardysqShouldNotBeFound("sqh.doesNotContain=" + DEFAULT_SQH);

        // Get all the cardysqList where sqh does not contain UPDATED_SQH
        defaultCardysqShouldBeFound("sqh.doesNotContain=" + UPDATED_SQH);
    }

    @Test
    @Transactional
    void getAllCardysqsByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where empn equals to DEFAULT_EMPN
        defaultCardysqShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the cardysqList where empn equals to UPDATED_EMPN
        defaultCardysqShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCardysqsByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where empn not equals to DEFAULT_EMPN
        defaultCardysqShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the cardysqList where empn not equals to UPDATED_EMPN
        defaultCardysqShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCardysqsByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultCardysqShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the cardysqList where empn equals to UPDATED_EMPN
        defaultCardysqShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCardysqsByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where empn is not null
        defaultCardysqShouldBeFound("empn.specified=true");

        // Get all the cardysqList where empn is null
        defaultCardysqShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllCardysqsByEmpnContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where empn contains DEFAULT_EMPN
        defaultCardysqShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the cardysqList where empn contains UPDATED_EMPN
        defaultCardysqShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCardysqsByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where empn does not contain DEFAULT_EMPN
        defaultCardysqShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the cardysqList where empn does not contain UPDATED_EMPN
        defaultCardysqShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCardysqsBySignIsEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where sign equals to DEFAULT_SIGN
        defaultCardysqShouldBeFound("sign.equals=" + DEFAULT_SIGN);

        // Get all the cardysqList where sign equals to UPDATED_SIGN
        defaultCardysqShouldNotBeFound("sign.equals=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllCardysqsBySignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where sign not equals to DEFAULT_SIGN
        defaultCardysqShouldNotBeFound("sign.notEquals=" + DEFAULT_SIGN);

        // Get all the cardysqList where sign not equals to UPDATED_SIGN
        defaultCardysqShouldBeFound("sign.notEquals=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllCardysqsBySignIsInShouldWork() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where sign in DEFAULT_SIGN or UPDATED_SIGN
        defaultCardysqShouldBeFound("sign.in=" + DEFAULT_SIGN + "," + UPDATED_SIGN);

        // Get all the cardysqList where sign equals to UPDATED_SIGN
        defaultCardysqShouldNotBeFound("sign.in=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllCardysqsBySignIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where sign is not null
        defaultCardysqShouldBeFound("sign.specified=true");

        // Get all the cardysqList where sign is null
        defaultCardysqShouldNotBeFound("sign.specified=false");
    }

    @Test
    @Transactional
    void getAllCardysqsBySignContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where sign contains DEFAULT_SIGN
        defaultCardysqShouldBeFound("sign.contains=" + DEFAULT_SIGN);

        // Get all the cardysqList where sign contains UPDATED_SIGN
        defaultCardysqShouldNotBeFound("sign.contains=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllCardysqsBySignNotContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where sign does not contain DEFAULT_SIGN
        defaultCardysqShouldNotBeFound("sign.doesNotContain=" + DEFAULT_SIGN);

        // Get all the cardysqList where sign does not contain UPDATED_SIGN
        defaultCardysqShouldBeFound("sign.doesNotContain=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllCardysqsByHoteltimeIsEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where hoteltime equals to DEFAULT_HOTELTIME
        defaultCardysqShouldBeFound("hoteltime.equals=" + DEFAULT_HOTELTIME);

        // Get all the cardysqList where hoteltime equals to UPDATED_HOTELTIME
        defaultCardysqShouldNotBeFound("hoteltime.equals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCardysqsByHoteltimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where hoteltime not equals to DEFAULT_HOTELTIME
        defaultCardysqShouldNotBeFound("hoteltime.notEquals=" + DEFAULT_HOTELTIME);

        // Get all the cardysqList where hoteltime not equals to UPDATED_HOTELTIME
        defaultCardysqShouldBeFound("hoteltime.notEquals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCardysqsByHoteltimeIsInShouldWork() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where hoteltime in DEFAULT_HOTELTIME or UPDATED_HOTELTIME
        defaultCardysqShouldBeFound("hoteltime.in=" + DEFAULT_HOTELTIME + "," + UPDATED_HOTELTIME);

        // Get all the cardysqList where hoteltime equals to UPDATED_HOTELTIME
        defaultCardysqShouldNotBeFound("hoteltime.in=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCardysqsByHoteltimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where hoteltime is not null
        defaultCardysqShouldBeFound("hoteltime.specified=true");

        // Get all the cardysqList where hoteltime is null
        defaultCardysqShouldNotBeFound("hoteltime.specified=false");
    }

    @Test
    @Transactional
    void getAllCardysqsByYxrqIsEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where yxrq equals to DEFAULT_YXRQ
        defaultCardysqShouldBeFound("yxrq.equals=" + DEFAULT_YXRQ);

        // Get all the cardysqList where yxrq equals to UPDATED_YXRQ
        defaultCardysqShouldNotBeFound("yxrq.equals=" + UPDATED_YXRQ);
    }

    @Test
    @Transactional
    void getAllCardysqsByYxrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where yxrq not equals to DEFAULT_YXRQ
        defaultCardysqShouldNotBeFound("yxrq.notEquals=" + DEFAULT_YXRQ);

        // Get all the cardysqList where yxrq not equals to UPDATED_YXRQ
        defaultCardysqShouldBeFound("yxrq.notEquals=" + UPDATED_YXRQ);
    }

    @Test
    @Transactional
    void getAllCardysqsByYxrqIsInShouldWork() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where yxrq in DEFAULT_YXRQ or UPDATED_YXRQ
        defaultCardysqShouldBeFound("yxrq.in=" + DEFAULT_YXRQ + "," + UPDATED_YXRQ);

        // Get all the cardysqList where yxrq equals to UPDATED_YXRQ
        defaultCardysqShouldNotBeFound("yxrq.in=" + UPDATED_YXRQ);
    }

    @Test
    @Transactional
    void getAllCardysqsByYxrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where yxrq is not null
        defaultCardysqShouldBeFound("yxrq.specified=true");

        // Get all the cardysqList where yxrq is null
        defaultCardysqShouldNotBeFound("yxrq.specified=false");
    }

    @Test
    @Transactional
    void getAllCardysqsByJeIsEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where je equals to DEFAULT_JE
        defaultCardysqShouldBeFound("je.equals=" + DEFAULT_JE);

        // Get all the cardysqList where je equals to UPDATED_JE
        defaultCardysqShouldNotBeFound("je.equals=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllCardysqsByJeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where je not equals to DEFAULT_JE
        defaultCardysqShouldNotBeFound("je.notEquals=" + DEFAULT_JE);

        // Get all the cardysqList where je not equals to UPDATED_JE
        defaultCardysqShouldBeFound("je.notEquals=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllCardysqsByJeIsInShouldWork() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where je in DEFAULT_JE or UPDATED_JE
        defaultCardysqShouldBeFound("je.in=" + DEFAULT_JE + "," + UPDATED_JE);

        // Get all the cardysqList where je equals to UPDATED_JE
        defaultCardysqShouldNotBeFound("je.in=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllCardysqsByJeIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where je is not null
        defaultCardysqShouldBeFound("je.specified=true");

        // Get all the cardysqList where je is null
        defaultCardysqShouldNotBeFound("je.specified=false");
    }

    @Test
    @Transactional
    void getAllCardysqsByJeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where je is greater than or equal to DEFAULT_JE
        defaultCardysqShouldBeFound("je.greaterThanOrEqual=" + DEFAULT_JE);

        // Get all the cardysqList where je is greater than or equal to UPDATED_JE
        defaultCardysqShouldNotBeFound("je.greaterThanOrEqual=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllCardysqsByJeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where je is less than or equal to DEFAULT_JE
        defaultCardysqShouldBeFound("je.lessThanOrEqual=" + DEFAULT_JE);

        // Get all the cardysqList where je is less than or equal to SMALLER_JE
        defaultCardysqShouldNotBeFound("je.lessThanOrEqual=" + SMALLER_JE);
    }

    @Test
    @Transactional
    void getAllCardysqsByJeIsLessThanSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where je is less than DEFAULT_JE
        defaultCardysqShouldNotBeFound("je.lessThan=" + DEFAULT_JE);

        // Get all the cardysqList where je is less than UPDATED_JE
        defaultCardysqShouldBeFound("je.lessThan=" + UPDATED_JE);
    }

    @Test
    @Transactional
    void getAllCardysqsByJeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where je is greater than DEFAULT_JE
        defaultCardysqShouldNotBeFound("je.greaterThan=" + DEFAULT_JE);

        // Get all the cardysqList where je is greater than SMALLER_JE
        defaultCardysqShouldBeFound("je.greaterThan=" + SMALLER_JE);
    }

    @Test
    @Transactional
    void getAllCardysqsByYsqmemoIsEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where ysqmemo equals to DEFAULT_YSQMEMO
        defaultCardysqShouldBeFound("ysqmemo.equals=" + DEFAULT_YSQMEMO);

        // Get all the cardysqList where ysqmemo equals to UPDATED_YSQMEMO
        defaultCardysqShouldNotBeFound("ysqmemo.equals=" + UPDATED_YSQMEMO);
    }

    @Test
    @Transactional
    void getAllCardysqsByYsqmemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where ysqmemo not equals to DEFAULT_YSQMEMO
        defaultCardysqShouldNotBeFound("ysqmemo.notEquals=" + DEFAULT_YSQMEMO);

        // Get all the cardysqList where ysqmemo not equals to UPDATED_YSQMEMO
        defaultCardysqShouldBeFound("ysqmemo.notEquals=" + UPDATED_YSQMEMO);
    }

    @Test
    @Transactional
    void getAllCardysqsByYsqmemoIsInShouldWork() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where ysqmemo in DEFAULT_YSQMEMO or UPDATED_YSQMEMO
        defaultCardysqShouldBeFound("ysqmemo.in=" + DEFAULT_YSQMEMO + "," + UPDATED_YSQMEMO);

        // Get all the cardysqList where ysqmemo equals to UPDATED_YSQMEMO
        defaultCardysqShouldNotBeFound("ysqmemo.in=" + UPDATED_YSQMEMO);
    }

    @Test
    @Transactional
    void getAllCardysqsByYsqmemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where ysqmemo is not null
        defaultCardysqShouldBeFound("ysqmemo.specified=true");

        // Get all the cardysqList where ysqmemo is null
        defaultCardysqShouldNotBeFound("ysqmemo.specified=false");
    }

    @Test
    @Transactional
    void getAllCardysqsByYsqmemoContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where ysqmemo contains DEFAULT_YSQMEMO
        defaultCardysqShouldBeFound("ysqmemo.contains=" + DEFAULT_YSQMEMO);

        // Get all the cardysqList where ysqmemo contains UPDATED_YSQMEMO
        defaultCardysqShouldNotBeFound("ysqmemo.contains=" + UPDATED_YSQMEMO);
    }

    @Test
    @Transactional
    void getAllCardysqsByYsqmemoNotContainsSomething() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        // Get all the cardysqList where ysqmemo does not contain DEFAULT_YSQMEMO
        defaultCardysqShouldNotBeFound("ysqmemo.doesNotContain=" + DEFAULT_YSQMEMO);

        // Get all the cardysqList where ysqmemo does not contain UPDATED_YSQMEMO
        defaultCardysqShouldBeFound("ysqmemo.doesNotContain=" + UPDATED_YSQMEMO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCardysqShouldBeFound(String filter) throws Exception {
        restCardysqMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cardysq.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].cardid").value(hasItem(DEFAULT_CARDID)))
            .andExpect(jsonPath("$.[*].djh").value(hasItem(DEFAULT_DJH)))
            .andExpect(jsonPath("$.[*].sqh").value(hasItem(DEFAULT_SQH)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN)))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].yxrq").value(hasItem(DEFAULT_YXRQ.toString())))
            .andExpect(jsonPath("$.[*].je").value(hasItem(sameNumber(DEFAULT_JE))))
            .andExpect(jsonPath("$.[*].ysqmemo").value(hasItem(DEFAULT_YSQMEMO)));

        // Check, that the count call also returns 1
        restCardysqMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCardysqShouldNotBeFound(String filter) throws Exception {
        restCardysqMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCardysqMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCardysq() throws Exception {
        // Get the cardysq
        restCardysqMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCardysq() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        int databaseSizeBeforeUpdate = cardysqRepository.findAll().size();

        // Update the cardysq
        Cardysq updatedCardysq = cardysqRepository.findById(cardysq.getId()).get();
        // Disconnect from session so that the updates on updatedCardysq are not directly saved in db
        em.detach(updatedCardysq);
        updatedCardysq
            .roomn(UPDATED_ROOMN)
            .guestname(UPDATED_GUESTNAME)
            .account(UPDATED_ACCOUNT)
            .rq(UPDATED_RQ)
            .cardid(UPDATED_CARDID)
            .djh(UPDATED_DJH)
            .sqh(UPDATED_SQH)
            .empn(UPDATED_EMPN)
            .sign(UPDATED_SIGN)
            .hoteltime(UPDATED_HOTELTIME)
            .yxrq(UPDATED_YXRQ)
            .je(UPDATED_JE)
            .ysqmemo(UPDATED_YSQMEMO);
        CardysqDTO cardysqDTO = cardysqMapper.toDto(updatedCardysq);

        restCardysqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cardysqDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cardysqDTO))
            )
            .andExpect(status().isOk());

        // Validate the Cardysq in the database
        List<Cardysq> cardysqList = cardysqRepository.findAll();
        assertThat(cardysqList).hasSize(databaseSizeBeforeUpdate);
        Cardysq testCardysq = cardysqList.get(cardysqList.size() - 1);
        assertThat(testCardysq.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCardysq.getGuestname()).isEqualTo(UPDATED_GUESTNAME);
        assertThat(testCardysq.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCardysq.getRq()).isEqualTo(UPDATED_RQ);
        assertThat(testCardysq.getCardid()).isEqualTo(UPDATED_CARDID);
        assertThat(testCardysq.getDjh()).isEqualTo(UPDATED_DJH);
        assertThat(testCardysq.getSqh()).isEqualTo(UPDATED_SQH);
        assertThat(testCardysq.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCardysq.getSign()).isEqualTo(UPDATED_SIGN);
        assertThat(testCardysq.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCardysq.getYxrq()).isEqualTo(UPDATED_YXRQ);
        assertThat(testCardysq.getJe()).isEqualTo(UPDATED_JE);
        assertThat(testCardysq.getYsqmemo()).isEqualTo(UPDATED_YSQMEMO);

        // Validate the Cardysq in Elasticsearch
        verify(mockCardysqSearchRepository).save(testCardysq);
    }

    @Test
    @Transactional
    void putNonExistingCardysq() throws Exception {
        int databaseSizeBeforeUpdate = cardysqRepository.findAll().size();
        cardysq.setId(count.incrementAndGet());

        // Create the Cardysq
        CardysqDTO cardysqDTO = cardysqMapper.toDto(cardysq);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardysqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cardysqDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cardysqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cardysq in the database
        List<Cardysq> cardysqList = cardysqRepository.findAll();
        assertThat(cardysqList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cardysq in Elasticsearch
        verify(mockCardysqSearchRepository, times(0)).save(cardysq);
    }

    @Test
    @Transactional
    void putWithIdMismatchCardysq() throws Exception {
        int databaseSizeBeforeUpdate = cardysqRepository.findAll().size();
        cardysq.setId(count.incrementAndGet());

        // Create the Cardysq
        CardysqDTO cardysqDTO = cardysqMapper.toDto(cardysq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardysqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cardysqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cardysq in the database
        List<Cardysq> cardysqList = cardysqRepository.findAll();
        assertThat(cardysqList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cardysq in Elasticsearch
        verify(mockCardysqSearchRepository, times(0)).save(cardysq);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCardysq() throws Exception {
        int databaseSizeBeforeUpdate = cardysqRepository.findAll().size();
        cardysq.setId(count.incrementAndGet());

        // Create the Cardysq
        CardysqDTO cardysqDTO = cardysqMapper.toDto(cardysq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardysqMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cardysqDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cardysq in the database
        List<Cardysq> cardysqList = cardysqRepository.findAll();
        assertThat(cardysqList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cardysq in Elasticsearch
        verify(mockCardysqSearchRepository, times(0)).save(cardysq);
    }

    @Test
    @Transactional
    void partialUpdateCardysqWithPatch() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        int databaseSizeBeforeUpdate = cardysqRepository.findAll().size();

        // Update the cardysq using partial update
        Cardysq partialUpdatedCardysq = new Cardysq();
        partialUpdatedCardysq.setId(cardysq.getId());

        partialUpdatedCardysq
            .roomn(UPDATED_ROOMN)
            .guestname(UPDATED_GUESTNAME)
            .account(UPDATED_ACCOUNT)
            .cardid(UPDATED_CARDID)
            .djh(UPDATED_DJH)
            .sqh(UPDATED_SQH)
            .sign(UPDATED_SIGN)
            .hoteltime(UPDATED_HOTELTIME)
            .yxrq(UPDATED_YXRQ)
            .ysqmemo(UPDATED_YSQMEMO);

        restCardysqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCardysq.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCardysq))
            )
            .andExpect(status().isOk());

        // Validate the Cardysq in the database
        List<Cardysq> cardysqList = cardysqRepository.findAll();
        assertThat(cardysqList).hasSize(databaseSizeBeforeUpdate);
        Cardysq testCardysq = cardysqList.get(cardysqList.size() - 1);
        assertThat(testCardysq.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCardysq.getGuestname()).isEqualTo(UPDATED_GUESTNAME);
        assertThat(testCardysq.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCardysq.getRq()).isEqualTo(DEFAULT_RQ);
        assertThat(testCardysq.getCardid()).isEqualTo(UPDATED_CARDID);
        assertThat(testCardysq.getDjh()).isEqualTo(UPDATED_DJH);
        assertThat(testCardysq.getSqh()).isEqualTo(UPDATED_SQH);
        assertThat(testCardysq.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testCardysq.getSign()).isEqualTo(UPDATED_SIGN);
        assertThat(testCardysq.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCardysq.getYxrq()).isEqualTo(UPDATED_YXRQ);
        assertThat(testCardysq.getJe()).isEqualByComparingTo(DEFAULT_JE);
        assertThat(testCardysq.getYsqmemo()).isEqualTo(UPDATED_YSQMEMO);
    }

    @Test
    @Transactional
    void fullUpdateCardysqWithPatch() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        int databaseSizeBeforeUpdate = cardysqRepository.findAll().size();

        // Update the cardysq using partial update
        Cardysq partialUpdatedCardysq = new Cardysq();
        partialUpdatedCardysq.setId(cardysq.getId());

        partialUpdatedCardysq
            .roomn(UPDATED_ROOMN)
            .guestname(UPDATED_GUESTNAME)
            .account(UPDATED_ACCOUNT)
            .rq(UPDATED_RQ)
            .cardid(UPDATED_CARDID)
            .djh(UPDATED_DJH)
            .sqh(UPDATED_SQH)
            .empn(UPDATED_EMPN)
            .sign(UPDATED_SIGN)
            .hoteltime(UPDATED_HOTELTIME)
            .yxrq(UPDATED_YXRQ)
            .je(UPDATED_JE)
            .ysqmemo(UPDATED_YSQMEMO);

        restCardysqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCardysq.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCardysq))
            )
            .andExpect(status().isOk());

        // Validate the Cardysq in the database
        List<Cardysq> cardysqList = cardysqRepository.findAll();
        assertThat(cardysqList).hasSize(databaseSizeBeforeUpdate);
        Cardysq testCardysq = cardysqList.get(cardysqList.size() - 1);
        assertThat(testCardysq.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCardysq.getGuestname()).isEqualTo(UPDATED_GUESTNAME);
        assertThat(testCardysq.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCardysq.getRq()).isEqualTo(UPDATED_RQ);
        assertThat(testCardysq.getCardid()).isEqualTo(UPDATED_CARDID);
        assertThat(testCardysq.getDjh()).isEqualTo(UPDATED_DJH);
        assertThat(testCardysq.getSqh()).isEqualTo(UPDATED_SQH);
        assertThat(testCardysq.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCardysq.getSign()).isEqualTo(UPDATED_SIGN);
        assertThat(testCardysq.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCardysq.getYxrq()).isEqualTo(UPDATED_YXRQ);
        assertThat(testCardysq.getJe()).isEqualByComparingTo(UPDATED_JE);
        assertThat(testCardysq.getYsqmemo()).isEqualTo(UPDATED_YSQMEMO);
    }

    @Test
    @Transactional
    void patchNonExistingCardysq() throws Exception {
        int databaseSizeBeforeUpdate = cardysqRepository.findAll().size();
        cardysq.setId(count.incrementAndGet());

        // Create the Cardysq
        CardysqDTO cardysqDTO = cardysqMapper.toDto(cardysq);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCardysqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cardysqDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cardysqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cardysq in the database
        List<Cardysq> cardysqList = cardysqRepository.findAll();
        assertThat(cardysqList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cardysq in Elasticsearch
        verify(mockCardysqSearchRepository, times(0)).save(cardysq);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCardysq() throws Exception {
        int databaseSizeBeforeUpdate = cardysqRepository.findAll().size();
        cardysq.setId(count.incrementAndGet());

        // Create the Cardysq
        CardysqDTO cardysqDTO = cardysqMapper.toDto(cardysq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardysqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cardysqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Cardysq in the database
        List<Cardysq> cardysqList = cardysqRepository.findAll();
        assertThat(cardysqList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cardysq in Elasticsearch
        verify(mockCardysqSearchRepository, times(0)).save(cardysq);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCardysq() throws Exception {
        int databaseSizeBeforeUpdate = cardysqRepository.findAll().size();
        cardysq.setId(count.incrementAndGet());

        // Create the Cardysq
        CardysqDTO cardysqDTO = cardysqMapper.toDto(cardysq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCardysqMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cardysqDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Cardysq in the database
        List<Cardysq> cardysqList = cardysqRepository.findAll();
        assertThat(cardysqList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cardysq in Elasticsearch
        verify(mockCardysqSearchRepository, times(0)).save(cardysq);
    }

    @Test
    @Transactional
    void deleteCardysq() throws Exception {
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);

        int databaseSizeBeforeDelete = cardysqRepository.findAll().size();

        // Delete the cardysq
        restCardysqMockMvc
            .perform(delete(ENTITY_API_URL_ID, cardysq.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cardysq> cardysqList = cardysqRepository.findAll();
        assertThat(cardysqList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Cardysq in Elasticsearch
        verify(mockCardysqSearchRepository, times(1)).deleteById(cardysq.getId());
    }

    @Test
    @Transactional
    void searchCardysq() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        cardysqRepository.saveAndFlush(cardysq);
        when(mockCardysqSearchRepository.search(queryStringQuery("id:" + cardysq.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cardysq), PageRequest.of(0, 1), 1));

        // Search the cardysq
        restCardysqMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + cardysq.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cardysq.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].cardid").value(hasItem(DEFAULT_CARDID)))
            .andExpect(jsonPath("$.[*].djh").value(hasItem(DEFAULT_DJH)))
            .andExpect(jsonPath("$.[*].sqh").value(hasItem(DEFAULT_SQH)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN)))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].yxrq").value(hasItem(DEFAULT_YXRQ.toString())))
            .andExpect(jsonPath("$.[*].je").value(hasItem(sameNumber(DEFAULT_JE))))
            .andExpect(jsonPath("$.[*].ysqmemo").value(hasItem(DEFAULT_YSQMEMO)));
    }
}
