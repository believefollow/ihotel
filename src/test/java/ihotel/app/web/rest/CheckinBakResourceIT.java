package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.CheckinBak;
import ihotel.app.repository.CheckinBakRepository;
import ihotel.app.repository.search.CheckinBakSearchRepository;
import ihotel.app.service.criteria.CheckinBakCriteria;
import ihotel.app.service.dto.CheckinBakDTO;
import ihotel.app.service.mapper.CheckinBakMapper;
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
 * Integration tests for the {@link CheckinBakResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CheckinBakResourceIT {

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

    private static final String DEFAULT_UNAME = "AAAAAAAAAA";
    private static final String UPDATED_UNAME = "BBBBBBBBBB";

    private static final String DEFAULT_RENTP = "AAAAAAAAAA";
    private static final String UPDATED_RENTP = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PROTOCOLRENT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROTOCOLRENT = new BigDecimal(2);
    private static final BigDecimal SMALLER_PROTOCOLRENT = new BigDecimal(1 - 1);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_COMEINFO = "AAAAAAAAAA";
    private static final String UPDATED_COMEINFO = "BBBBBBBBBB";

    private static final String DEFAULT_GOINFO = "AAAAAAAAAA";
    private static final String UPDATED_GOINFO = "BBBBBBBBBB";

    private static final String DEFAULT_PHONEN = "AAAAAAAAAA";
    private static final String UPDATED_PHONEN = "BBBBBBBBBB";

    private static final String DEFAULT_EMPN_2 = "AAAAAAAAAA";
    private static final String UPDATED_EMPN_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADHOC = "AAAAAAAAAA";
    private static final String UPDATED_ADHOC = "BBBBBBBBBB";

    private static final Long DEFAULT_AUDITFLAG = 1L;
    private static final Long UPDATED_AUDITFLAG = 2L;
    private static final Long SMALLER_AUDITFLAG = 1L - 1L;

    private static final String DEFAULT_GROUPN = "AAAAAAAAAA";
    private static final String UPDATED_GROUPN = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT = "BBBBBBBBBB";

    private static final String DEFAULT_MTYPE = "AAAAAAAAAA";
    private static final String UPDATED_MTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final String DEFAULT_FLIGHT = "AAAAAAAAAA";
    private static final String UPDATED_FLIGHT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CREDIT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CREDIT = new BigDecimal(2);
    private static final BigDecimal SMALLER_CREDIT = new BigDecimal(1 - 1);

    private static final String DEFAULT_TALKLEVEL = "AAAAAAAAAA";
    private static final String UPDATED_TALKLEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_LF_SIGN = "A";
    private static final String UPDATED_LF_SIGN = "B";

    private static final String DEFAULT_KEYNUM = "AAA";
    private static final String UPDATED_KEYNUM = "BBB";

    private static final Long DEFAULT_IC_NUM = 1L;
    private static final Long UPDATED_IC_NUM = 2L;
    private static final Long SMALLER_IC_NUM = 1L - 1L;

    private static final Long DEFAULT_BH = 1L;
    private static final Long UPDATED_BH = 2L;
    private static final Long SMALLER_BH = 1L - 1L;

    private static final String DEFAULT_IC_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_IC_OWNER = "BBBBBBBBBB";

    private static final String DEFAULT_MARK_ID = "AAAAAAAAAA";
    private static final String UPDATED_MARK_ID = "BBBBBBBBBB";

    private static final String DEFAULT_GJ = "AAAAAAAAAA";
    private static final String UPDATED_GJ = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_YFJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_YFJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_YFJ = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_HOTELDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOTELDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/checkin-baks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/checkin-baks";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CheckinBakRepository checkinBakRepository;

    @Autowired
    private CheckinBakMapper checkinBakMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CheckinBakSearchRepositoryMockConfiguration
     */
    @Autowired
    private CheckinBakSearchRepository mockCheckinBakSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckinBakMockMvc;

    private CheckinBak checkinBak;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckinBak createEntity(EntityManager em) {
        CheckinBak checkinBak = new CheckinBak()
            .guestId(DEFAULT_GUEST_ID)
            .account(DEFAULT_ACCOUNT)
            .hoteltime(DEFAULT_HOTELTIME)
            .indatetime(DEFAULT_INDATETIME)
            .residefate(DEFAULT_RESIDEFATE)
            .gotime(DEFAULT_GOTIME)
            .empn(DEFAULT_EMPN)
            .roomn(DEFAULT_ROOMN)
            .uname(DEFAULT_UNAME)
            .rentp(DEFAULT_RENTP)
            .protocolrent(DEFAULT_PROTOCOLRENT)
            .remark(DEFAULT_REMARK)
            .comeinfo(DEFAULT_COMEINFO)
            .goinfo(DEFAULT_GOINFO)
            .phonen(DEFAULT_PHONEN)
            .empn2(DEFAULT_EMPN_2)
            .adhoc(DEFAULT_ADHOC)
            .auditflag(DEFAULT_AUDITFLAG)
            .groupn(DEFAULT_GROUPN)
            .payment(DEFAULT_PAYMENT)
            .mtype(DEFAULT_MTYPE)
            .memo(DEFAULT_MEMO)
            .flight(DEFAULT_FLIGHT)
            .credit(DEFAULT_CREDIT)
            .talklevel(DEFAULT_TALKLEVEL)
            .lfSign(DEFAULT_LF_SIGN)
            .keynum(DEFAULT_KEYNUM)
            .icNum(DEFAULT_IC_NUM)
            .bh(DEFAULT_BH)
            .icOwner(DEFAULT_IC_OWNER)
            .markId(DEFAULT_MARK_ID)
            .gj(DEFAULT_GJ)
            .yfj(DEFAULT_YFJ)
            .hoteldate(DEFAULT_HOTELDATE);
        return checkinBak;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CheckinBak createUpdatedEntity(EntityManager em) {
        CheckinBak checkinBak = new CheckinBak()
            .guestId(UPDATED_GUEST_ID)
            .account(UPDATED_ACCOUNT)
            .hoteltime(UPDATED_HOTELTIME)
            .indatetime(UPDATED_INDATETIME)
            .residefate(UPDATED_RESIDEFATE)
            .gotime(UPDATED_GOTIME)
            .empn(UPDATED_EMPN)
            .roomn(UPDATED_ROOMN)
            .uname(UPDATED_UNAME)
            .rentp(UPDATED_RENTP)
            .protocolrent(UPDATED_PROTOCOLRENT)
            .remark(UPDATED_REMARK)
            .comeinfo(UPDATED_COMEINFO)
            .goinfo(UPDATED_GOINFO)
            .phonen(UPDATED_PHONEN)
            .empn2(UPDATED_EMPN_2)
            .adhoc(UPDATED_ADHOC)
            .auditflag(UPDATED_AUDITFLAG)
            .groupn(UPDATED_GROUPN)
            .payment(UPDATED_PAYMENT)
            .mtype(UPDATED_MTYPE)
            .memo(UPDATED_MEMO)
            .flight(UPDATED_FLIGHT)
            .credit(UPDATED_CREDIT)
            .talklevel(UPDATED_TALKLEVEL)
            .lfSign(UPDATED_LF_SIGN)
            .keynum(UPDATED_KEYNUM)
            .icNum(UPDATED_IC_NUM)
            .bh(UPDATED_BH)
            .icOwner(UPDATED_IC_OWNER)
            .markId(UPDATED_MARK_ID)
            .gj(UPDATED_GJ)
            .yfj(UPDATED_YFJ)
            .hoteldate(UPDATED_HOTELDATE);
        return checkinBak;
    }

    @BeforeEach
    public void initTest() {
        checkinBak = createEntity(em);
    }

    @Test
    @Transactional
    void createCheckinBak() throws Exception {
        int databaseSizeBeforeCreate = checkinBakRepository.findAll().size();
        // Create the CheckinBak
        CheckinBakDTO checkinBakDTO = checkinBakMapper.toDto(checkinBak);
        restCheckinBakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinBakDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckinBak in the database
        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeCreate + 1);
        CheckinBak testCheckinBak = checkinBakList.get(checkinBakList.size() - 1);
        assertThat(testCheckinBak.getGuestId()).isEqualTo(DEFAULT_GUEST_ID);
        assertThat(testCheckinBak.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testCheckinBak.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testCheckinBak.getIndatetime()).isEqualTo(DEFAULT_INDATETIME);
        assertThat(testCheckinBak.getResidefate()).isEqualTo(DEFAULT_RESIDEFATE);
        assertThat(testCheckinBak.getGotime()).isEqualTo(DEFAULT_GOTIME);
        assertThat(testCheckinBak.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testCheckinBak.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testCheckinBak.getUname()).isEqualTo(DEFAULT_UNAME);
        assertThat(testCheckinBak.getRentp()).isEqualTo(DEFAULT_RENTP);
        assertThat(testCheckinBak.getProtocolrent()).isEqualByComparingTo(DEFAULT_PROTOCOLRENT);
        assertThat(testCheckinBak.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testCheckinBak.getComeinfo()).isEqualTo(DEFAULT_COMEINFO);
        assertThat(testCheckinBak.getGoinfo()).isEqualTo(DEFAULT_GOINFO);
        assertThat(testCheckinBak.getPhonen()).isEqualTo(DEFAULT_PHONEN);
        assertThat(testCheckinBak.getEmpn2()).isEqualTo(DEFAULT_EMPN_2);
        assertThat(testCheckinBak.getAdhoc()).isEqualTo(DEFAULT_ADHOC);
        assertThat(testCheckinBak.getAuditflag()).isEqualTo(DEFAULT_AUDITFLAG);
        assertThat(testCheckinBak.getGroupn()).isEqualTo(DEFAULT_GROUPN);
        assertThat(testCheckinBak.getPayment()).isEqualTo(DEFAULT_PAYMENT);
        assertThat(testCheckinBak.getMtype()).isEqualTo(DEFAULT_MTYPE);
        assertThat(testCheckinBak.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testCheckinBak.getFlight()).isEqualTo(DEFAULT_FLIGHT);
        assertThat(testCheckinBak.getCredit()).isEqualByComparingTo(DEFAULT_CREDIT);
        assertThat(testCheckinBak.getTalklevel()).isEqualTo(DEFAULT_TALKLEVEL);
        assertThat(testCheckinBak.getLfSign()).isEqualTo(DEFAULT_LF_SIGN);
        assertThat(testCheckinBak.getKeynum()).isEqualTo(DEFAULT_KEYNUM);
        assertThat(testCheckinBak.getIcNum()).isEqualTo(DEFAULT_IC_NUM);
        assertThat(testCheckinBak.getBh()).isEqualTo(DEFAULT_BH);
        assertThat(testCheckinBak.getIcOwner()).isEqualTo(DEFAULT_IC_OWNER);
        assertThat(testCheckinBak.getMarkId()).isEqualTo(DEFAULT_MARK_ID);
        assertThat(testCheckinBak.getGj()).isEqualTo(DEFAULT_GJ);
        assertThat(testCheckinBak.getYfj()).isEqualByComparingTo(DEFAULT_YFJ);
        assertThat(testCheckinBak.getHoteldate()).isEqualTo(DEFAULT_HOTELDATE);

        // Validate the CheckinBak in Elasticsearch
        verify(mockCheckinBakSearchRepository, times(1)).save(testCheckinBak);
    }

    @Test
    @Transactional
    void createCheckinBakWithExistingId() throws Exception {
        // Create the CheckinBak with an existing ID
        checkinBak.setId(1L);
        CheckinBakDTO checkinBakDTO = checkinBakMapper.toDto(checkinBak);

        int databaseSizeBeforeCreate = checkinBakRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckinBakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinBakDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CheckinBak in the database
        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeCreate);

        // Validate the CheckinBak in Elasticsearch
        verify(mockCheckinBakSearchRepository, times(0)).save(checkinBak);
    }

    @Test
    @Transactional
    void checkGuestIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkinBakRepository.findAll().size();
        // set the field null
        checkinBak.setGuestId(null);

        // Create the CheckinBak, which fails.
        CheckinBakDTO checkinBakDTO = checkinBakMapper.toDto(checkinBak);

        restCheckinBakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinBakDTO)))
            .andExpect(status().isBadRequest());

        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkinBakRepository.findAll().size();
        // set the field null
        checkinBak.setAccount(null);

        // Create the CheckinBak, which fails.
        CheckinBakDTO checkinBakDTO = checkinBakMapper.toDto(checkinBak);

        restCheckinBakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinBakDTO)))
            .andExpect(status().isBadRequest());

        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHoteltimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkinBakRepository.findAll().size();
        // set the field null
        checkinBak.setHoteltime(null);

        // Create the CheckinBak, which fails.
        CheckinBakDTO checkinBakDTO = checkinBakMapper.toDto(checkinBak);

        restCheckinBakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinBakDTO)))
            .andExpect(status().isBadRequest());

        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRentpIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkinBakRepository.findAll().size();
        // set the field null
        checkinBak.setRentp(null);

        // Create the CheckinBak, which fails.
        CheckinBakDTO checkinBakDTO = checkinBakMapper.toDto(checkinBak);

        restCheckinBakMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinBakDTO)))
            .andExpect(status().isBadRequest());

        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCheckinBaks() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList
        restCheckinBakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkinBak.getId().intValue())))
            .andExpect(jsonPath("$.[*].guestId").value(hasItem(DEFAULT_GUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].indatetime").value(hasItem(DEFAULT_INDATETIME.toString())))
            .andExpect(jsonPath("$.[*].residefate").value(hasItem(DEFAULT_RESIDEFATE.intValue())))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].uname").value(hasItem(DEFAULT_UNAME)))
            .andExpect(jsonPath("$.[*].rentp").value(hasItem(DEFAULT_RENTP)))
            .andExpect(jsonPath("$.[*].protocolrent").value(hasItem(sameNumber(DEFAULT_PROTOCOLRENT))))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].comeinfo").value(hasItem(DEFAULT_COMEINFO)))
            .andExpect(jsonPath("$.[*].goinfo").value(hasItem(DEFAULT_GOINFO)))
            .andExpect(jsonPath("$.[*].phonen").value(hasItem(DEFAULT_PHONEN)))
            .andExpect(jsonPath("$.[*].empn2").value(hasItem(DEFAULT_EMPN_2)))
            .andExpect(jsonPath("$.[*].adhoc").value(hasItem(DEFAULT_ADHOC)))
            .andExpect(jsonPath("$.[*].auditflag").value(hasItem(DEFAULT_AUDITFLAG.intValue())))
            .andExpect(jsonPath("$.[*].groupn").value(hasItem(DEFAULT_GROUPN)))
            .andExpect(jsonPath("$.[*].payment").value(hasItem(DEFAULT_PAYMENT)))
            .andExpect(jsonPath("$.[*].mtype").value(hasItem(DEFAULT_MTYPE)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].flight").value(hasItem(DEFAULT_FLIGHT)))
            .andExpect(jsonPath("$.[*].credit").value(hasItem(sameNumber(DEFAULT_CREDIT))))
            .andExpect(jsonPath("$.[*].talklevel").value(hasItem(DEFAULT_TALKLEVEL)))
            .andExpect(jsonPath("$.[*].lfSign").value(hasItem(DEFAULT_LF_SIGN)))
            .andExpect(jsonPath("$.[*].keynum").value(hasItem(DEFAULT_KEYNUM)))
            .andExpect(jsonPath("$.[*].icNum").value(hasItem(DEFAULT_IC_NUM.intValue())))
            .andExpect(jsonPath("$.[*].bh").value(hasItem(DEFAULT_BH.intValue())))
            .andExpect(jsonPath("$.[*].icOwner").value(hasItem(DEFAULT_IC_OWNER)))
            .andExpect(jsonPath("$.[*].markId").value(hasItem(DEFAULT_MARK_ID)))
            .andExpect(jsonPath("$.[*].gj").value(hasItem(DEFAULT_GJ)))
            .andExpect(jsonPath("$.[*].yfj").value(hasItem(sameNumber(DEFAULT_YFJ))))
            .andExpect(jsonPath("$.[*].hoteldate").value(hasItem(DEFAULT_HOTELDATE.toString())));
    }

    @Test
    @Transactional
    void getCheckinBak() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get the checkinBak
        restCheckinBakMockMvc
            .perform(get(ENTITY_API_URL_ID, checkinBak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkinBak.getId().intValue()))
            .andExpect(jsonPath("$.guestId").value(DEFAULT_GUEST_ID.intValue()))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT))
            .andExpect(jsonPath("$.hoteltime").value(DEFAULT_HOTELTIME.toString()))
            .andExpect(jsonPath("$.indatetime").value(DEFAULT_INDATETIME.toString()))
            .andExpect(jsonPath("$.residefate").value(DEFAULT_RESIDEFATE.intValue()))
            .andExpect(jsonPath("$.gotime").value(DEFAULT_GOTIME.toString()))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.roomn").value(DEFAULT_ROOMN))
            .andExpect(jsonPath("$.uname").value(DEFAULT_UNAME))
            .andExpect(jsonPath("$.rentp").value(DEFAULT_RENTP))
            .andExpect(jsonPath("$.protocolrent").value(sameNumber(DEFAULT_PROTOCOLRENT)))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.comeinfo").value(DEFAULT_COMEINFO))
            .andExpect(jsonPath("$.goinfo").value(DEFAULT_GOINFO))
            .andExpect(jsonPath("$.phonen").value(DEFAULT_PHONEN))
            .andExpect(jsonPath("$.empn2").value(DEFAULT_EMPN_2))
            .andExpect(jsonPath("$.adhoc").value(DEFAULT_ADHOC))
            .andExpect(jsonPath("$.auditflag").value(DEFAULT_AUDITFLAG.intValue()))
            .andExpect(jsonPath("$.groupn").value(DEFAULT_GROUPN))
            .andExpect(jsonPath("$.payment").value(DEFAULT_PAYMENT))
            .andExpect(jsonPath("$.mtype").value(DEFAULT_MTYPE))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO))
            .andExpect(jsonPath("$.flight").value(DEFAULT_FLIGHT))
            .andExpect(jsonPath("$.credit").value(sameNumber(DEFAULT_CREDIT)))
            .andExpect(jsonPath("$.talklevel").value(DEFAULT_TALKLEVEL))
            .andExpect(jsonPath("$.lfSign").value(DEFAULT_LF_SIGN))
            .andExpect(jsonPath("$.keynum").value(DEFAULT_KEYNUM))
            .andExpect(jsonPath("$.icNum").value(DEFAULT_IC_NUM.intValue()))
            .andExpect(jsonPath("$.bh").value(DEFAULT_BH.intValue()))
            .andExpect(jsonPath("$.icOwner").value(DEFAULT_IC_OWNER))
            .andExpect(jsonPath("$.markId").value(DEFAULT_MARK_ID))
            .andExpect(jsonPath("$.gj").value(DEFAULT_GJ))
            .andExpect(jsonPath("$.yfj").value(sameNumber(DEFAULT_YFJ)))
            .andExpect(jsonPath("$.hoteldate").value(DEFAULT_HOTELDATE.toString()));
    }

    @Test
    @Transactional
    void getCheckinBaksByIdFiltering() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        Long id = checkinBak.getId();

        defaultCheckinBakShouldBeFound("id.equals=" + id);
        defaultCheckinBakShouldNotBeFound("id.notEquals=" + id);

        defaultCheckinBakShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCheckinBakShouldNotBeFound("id.greaterThan=" + id);

        defaultCheckinBakShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCheckinBakShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGuestIdIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where guestId equals to DEFAULT_GUEST_ID
        defaultCheckinBakShouldBeFound("guestId.equals=" + DEFAULT_GUEST_ID);

        // Get all the checkinBakList where guestId equals to UPDATED_GUEST_ID
        defaultCheckinBakShouldNotBeFound("guestId.equals=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGuestIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where guestId not equals to DEFAULT_GUEST_ID
        defaultCheckinBakShouldNotBeFound("guestId.notEquals=" + DEFAULT_GUEST_ID);

        // Get all the checkinBakList where guestId not equals to UPDATED_GUEST_ID
        defaultCheckinBakShouldBeFound("guestId.notEquals=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGuestIdIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where guestId in DEFAULT_GUEST_ID or UPDATED_GUEST_ID
        defaultCheckinBakShouldBeFound("guestId.in=" + DEFAULT_GUEST_ID + "," + UPDATED_GUEST_ID);

        // Get all the checkinBakList where guestId equals to UPDATED_GUEST_ID
        defaultCheckinBakShouldNotBeFound("guestId.in=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGuestIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where guestId is not null
        defaultCheckinBakShouldBeFound("guestId.specified=true");

        // Get all the checkinBakList where guestId is null
        defaultCheckinBakShouldNotBeFound("guestId.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGuestIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where guestId is greater than or equal to DEFAULT_GUEST_ID
        defaultCheckinBakShouldBeFound("guestId.greaterThanOrEqual=" + DEFAULT_GUEST_ID);

        // Get all the checkinBakList where guestId is greater than or equal to UPDATED_GUEST_ID
        defaultCheckinBakShouldNotBeFound("guestId.greaterThanOrEqual=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGuestIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where guestId is less than or equal to DEFAULT_GUEST_ID
        defaultCheckinBakShouldBeFound("guestId.lessThanOrEqual=" + DEFAULT_GUEST_ID);

        // Get all the checkinBakList where guestId is less than or equal to SMALLER_GUEST_ID
        defaultCheckinBakShouldNotBeFound("guestId.lessThanOrEqual=" + SMALLER_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGuestIdIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where guestId is less than DEFAULT_GUEST_ID
        defaultCheckinBakShouldNotBeFound("guestId.lessThan=" + DEFAULT_GUEST_ID);

        // Get all the checkinBakList where guestId is less than UPDATED_GUEST_ID
        defaultCheckinBakShouldBeFound("guestId.lessThan=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGuestIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where guestId is greater than DEFAULT_GUEST_ID
        defaultCheckinBakShouldNotBeFound("guestId.greaterThan=" + DEFAULT_GUEST_ID);

        // Get all the checkinBakList where guestId is greater than SMALLER_GUEST_ID
        defaultCheckinBakShouldBeFound("guestId.greaterThan=" + SMALLER_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where account equals to DEFAULT_ACCOUNT
        defaultCheckinBakShouldBeFound("account.equals=" + DEFAULT_ACCOUNT);

        // Get all the checkinBakList where account equals to UPDATED_ACCOUNT
        defaultCheckinBakShouldNotBeFound("account.equals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where account not equals to DEFAULT_ACCOUNT
        defaultCheckinBakShouldNotBeFound("account.notEquals=" + DEFAULT_ACCOUNT);

        // Get all the checkinBakList where account not equals to UPDATED_ACCOUNT
        defaultCheckinBakShouldBeFound("account.notEquals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAccountIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where account in DEFAULT_ACCOUNT or UPDATED_ACCOUNT
        defaultCheckinBakShouldBeFound("account.in=" + DEFAULT_ACCOUNT + "," + UPDATED_ACCOUNT);

        // Get all the checkinBakList where account equals to UPDATED_ACCOUNT
        defaultCheckinBakShouldNotBeFound("account.in=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where account is not null
        defaultCheckinBakShouldBeFound("account.specified=true");

        // Get all the checkinBakList where account is null
        defaultCheckinBakShouldNotBeFound("account.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAccountContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where account contains DEFAULT_ACCOUNT
        defaultCheckinBakShouldBeFound("account.contains=" + DEFAULT_ACCOUNT);

        // Get all the checkinBakList where account contains UPDATED_ACCOUNT
        defaultCheckinBakShouldNotBeFound("account.contains=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAccountNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where account does not contain DEFAULT_ACCOUNT
        defaultCheckinBakShouldNotBeFound("account.doesNotContain=" + DEFAULT_ACCOUNT);

        // Get all the checkinBakList where account does not contain UPDATED_ACCOUNT
        defaultCheckinBakShouldBeFound("account.doesNotContain=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByHoteltimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where hoteltime equals to DEFAULT_HOTELTIME
        defaultCheckinBakShouldBeFound("hoteltime.equals=" + DEFAULT_HOTELTIME);

        // Get all the checkinBakList where hoteltime equals to UPDATED_HOTELTIME
        defaultCheckinBakShouldNotBeFound("hoteltime.equals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByHoteltimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where hoteltime not equals to DEFAULT_HOTELTIME
        defaultCheckinBakShouldNotBeFound("hoteltime.notEquals=" + DEFAULT_HOTELTIME);

        // Get all the checkinBakList where hoteltime not equals to UPDATED_HOTELTIME
        defaultCheckinBakShouldBeFound("hoteltime.notEquals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByHoteltimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where hoteltime in DEFAULT_HOTELTIME or UPDATED_HOTELTIME
        defaultCheckinBakShouldBeFound("hoteltime.in=" + DEFAULT_HOTELTIME + "," + UPDATED_HOTELTIME);

        // Get all the checkinBakList where hoteltime equals to UPDATED_HOTELTIME
        defaultCheckinBakShouldNotBeFound("hoteltime.in=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByHoteltimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where hoteltime is not null
        defaultCheckinBakShouldBeFound("hoteltime.specified=true");

        // Get all the checkinBakList where hoteltime is null
        defaultCheckinBakShouldNotBeFound("hoteltime.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIndatetimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where indatetime equals to DEFAULT_INDATETIME
        defaultCheckinBakShouldBeFound("indatetime.equals=" + DEFAULT_INDATETIME);

        // Get all the checkinBakList where indatetime equals to UPDATED_INDATETIME
        defaultCheckinBakShouldNotBeFound("indatetime.equals=" + UPDATED_INDATETIME);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIndatetimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where indatetime not equals to DEFAULT_INDATETIME
        defaultCheckinBakShouldNotBeFound("indatetime.notEquals=" + DEFAULT_INDATETIME);

        // Get all the checkinBakList where indatetime not equals to UPDATED_INDATETIME
        defaultCheckinBakShouldBeFound("indatetime.notEquals=" + UPDATED_INDATETIME);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIndatetimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where indatetime in DEFAULT_INDATETIME or UPDATED_INDATETIME
        defaultCheckinBakShouldBeFound("indatetime.in=" + DEFAULT_INDATETIME + "," + UPDATED_INDATETIME);

        // Get all the checkinBakList where indatetime equals to UPDATED_INDATETIME
        defaultCheckinBakShouldNotBeFound("indatetime.in=" + UPDATED_INDATETIME);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIndatetimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where indatetime is not null
        defaultCheckinBakShouldBeFound("indatetime.specified=true");

        // Get all the checkinBakList where indatetime is null
        defaultCheckinBakShouldNotBeFound("indatetime.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByResidefateIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where residefate equals to DEFAULT_RESIDEFATE
        defaultCheckinBakShouldBeFound("residefate.equals=" + DEFAULT_RESIDEFATE);

        // Get all the checkinBakList where residefate equals to UPDATED_RESIDEFATE
        defaultCheckinBakShouldNotBeFound("residefate.equals=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByResidefateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where residefate not equals to DEFAULT_RESIDEFATE
        defaultCheckinBakShouldNotBeFound("residefate.notEquals=" + DEFAULT_RESIDEFATE);

        // Get all the checkinBakList where residefate not equals to UPDATED_RESIDEFATE
        defaultCheckinBakShouldBeFound("residefate.notEquals=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByResidefateIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where residefate in DEFAULT_RESIDEFATE or UPDATED_RESIDEFATE
        defaultCheckinBakShouldBeFound("residefate.in=" + DEFAULT_RESIDEFATE + "," + UPDATED_RESIDEFATE);

        // Get all the checkinBakList where residefate equals to UPDATED_RESIDEFATE
        defaultCheckinBakShouldNotBeFound("residefate.in=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByResidefateIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where residefate is not null
        defaultCheckinBakShouldBeFound("residefate.specified=true");

        // Get all the checkinBakList where residefate is null
        defaultCheckinBakShouldNotBeFound("residefate.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByResidefateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where residefate is greater than or equal to DEFAULT_RESIDEFATE
        defaultCheckinBakShouldBeFound("residefate.greaterThanOrEqual=" + DEFAULT_RESIDEFATE);

        // Get all the checkinBakList where residefate is greater than or equal to UPDATED_RESIDEFATE
        defaultCheckinBakShouldNotBeFound("residefate.greaterThanOrEqual=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByResidefateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where residefate is less than or equal to DEFAULT_RESIDEFATE
        defaultCheckinBakShouldBeFound("residefate.lessThanOrEqual=" + DEFAULT_RESIDEFATE);

        // Get all the checkinBakList where residefate is less than or equal to SMALLER_RESIDEFATE
        defaultCheckinBakShouldNotBeFound("residefate.lessThanOrEqual=" + SMALLER_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByResidefateIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where residefate is less than DEFAULT_RESIDEFATE
        defaultCheckinBakShouldNotBeFound("residefate.lessThan=" + DEFAULT_RESIDEFATE);

        // Get all the checkinBakList where residefate is less than UPDATED_RESIDEFATE
        defaultCheckinBakShouldBeFound("residefate.lessThan=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByResidefateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where residefate is greater than DEFAULT_RESIDEFATE
        defaultCheckinBakShouldNotBeFound("residefate.greaterThan=" + DEFAULT_RESIDEFATE);

        // Get all the checkinBakList where residefate is greater than SMALLER_RESIDEFATE
        defaultCheckinBakShouldBeFound("residefate.greaterThan=" + SMALLER_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGotimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where gotime equals to DEFAULT_GOTIME
        defaultCheckinBakShouldBeFound("gotime.equals=" + DEFAULT_GOTIME);

        // Get all the checkinBakList where gotime equals to UPDATED_GOTIME
        defaultCheckinBakShouldNotBeFound("gotime.equals=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGotimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where gotime not equals to DEFAULT_GOTIME
        defaultCheckinBakShouldNotBeFound("gotime.notEquals=" + DEFAULT_GOTIME);

        // Get all the checkinBakList where gotime not equals to UPDATED_GOTIME
        defaultCheckinBakShouldBeFound("gotime.notEquals=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGotimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where gotime in DEFAULT_GOTIME or UPDATED_GOTIME
        defaultCheckinBakShouldBeFound("gotime.in=" + DEFAULT_GOTIME + "," + UPDATED_GOTIME);

        // Get all the checkinBakList where gotime equals to UPDATED_GOTIME
        defaultCheckinBakShouldNotBeFound("gotime.in=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGotimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where gotime is not null
        defaultCheckinBakShouldBeFound("gotime.specified=true");

        // Get all the checkinBakList where gotime is null
        defaultCheckinBakShouldNotBeFound("gotime.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where empn equals to DEFAULT_EMPN
        defaultCheckinBakShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the checkinBakList where empn equals to UPDATED_EMPN
        defaultCheckinBakShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where empn not equals to DEFAULT_EMPN
        defaultCheckinBakShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the checkinBakList where empn not equals to UPDATED_EMPN
        defaultCheckinBakShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultCheckinBakShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the checkinBakList where empn equals to UPDATED_EMPN
        defaultCheckinBakShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where empn is not null
        defaultCheckinBakShouldBeFound("empn.specified=true");

        // Get all the checkinBakList where empn is null
        defaultCheckinBakShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByEmpnContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where empn contains DEFAULT_EMPN
        defaultCheckinBakShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the checkinBakList where empn contains UPDATED_EMPN
        defaultCheckinBakShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where empn does not contain DEFAULT_EMPN
        defaultCheckinBakShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the checkinBakList where empn does not contain UPDATED_EMPN
        defaultCheckinBakShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where roomn equals to DEFAULT_ROOMN
        defaultCheckinBakShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the checkinBakList where roomn equals to UPDATED_ROOMN
        defaultCheckinBakShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where roomn not equals to DEFAULT_ROOMN
        defaultCheckinBakShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the checkinBakList where roomn not equals to UPDATED_ROOMN
        defaultCheckinBakShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultCheckinBakShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the checkinBakList where roomn equals to UPDATED_ROOMN
        defaultCheckinBakShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where roomn is not null
        defaultCheckinBakShouldBeFound("roomn.specified=true");

        // Get all the checkinBakList where roomn is null
        defaultCheckinBakShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRoomnContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where roomn contains DEFAULT_ROOMN
        defaultCheckinBakShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the checkinBakList where roomn contains UPDATED_ROOMN
        defaultCheckinBakShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where roomn does not contain DEFAULT_ROOMN
        defaultCheckinBakShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the checkinBakList where roomn does not contain UPDATED_ROOMN
        defaultCheckinBakShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByUnameIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where uname equals to DEFAULT_UNAME
        defaultCheckinBakShouldBeFound("uname.equals=" + DEFAULT_UNAME);

        // Get all the checkinBakList where uname equals to UPDATED_UNAME
        defaultCheckinBakShouldNotBeFound("uname.equals=" + UPDATED_UNAME);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByUnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where uname not equals to DEFAULT_UNAME
        defaultCheckinBakShouldNotBeFound("uname.notEquals=" + DEFAULT_UNAME);

        // Get all the checkinBakList where uname not equals to UPDATED_UNAME
        defaultCheckinBakShouldBeFound("uname.notEquals=" + UPDATED_UNAME);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByUnameIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where uname in DEFAULT_UNAME or UPDATED_UNAME
        defaultCheckinBakShouldBeFound("uname.in=" + DEFAULT_UNAME + "," + UPDATED_UNAME);

        // Get all the checkinBakList where uname equals to UPDATED_UNAME
        defaultCheckinBakShouldNotBeFound("uname.in=" + UPDATED_UNAME);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByUnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where uname is not null
        defaultCheckinBakShouldBeFound("uname.specified=true");

        // Get all the checkinBakList where uname is null
        defaultCheckinBakShouldNotBeFound("uname.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByUnameContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where uname contains DEFAULT_UNAME
        defaultCheckinBakShouldBeFound("uname.contains=" + DEFAULT_UNAME);

        // Get all the checkinBakList where uname contains UPDATED_UNAME
        defaultCheckinBakShouldNotBeFound("uname.contains=" + UPDATED_UNAME);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByUnameNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where uname does not contain DEFAULT_UNAME
        defaultCheckinBakShouldNotBeFound("uname.doesNotContain=" + DEFAULT_UNAME);

        // Get all the checkinBakList where uname does not contain UPDATED_UNAME
        defaultCheckinBakShouldBeFound("uname.doesNotContain=" + UPDATED_UNAME);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRentpIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where rentp equals to DEFAULT_RENTP
        defaultCheckinBakShouldBeFound("rentp.equals=" + DEFAULT_RENTP);

        // Get all the checkinBakList where rentp equals to UPDATED_RENTP
        defaultCheckinBakShouldNotBeFound("rentp.equals=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRentpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where rentp not equals to DEFAULT_RENTP
        defaultCheckinBakShouldNotBeFound("rentp.notEquals=" + DEFAULT_RENTP);

        // Get all the checkinBakList where rentp not equals to UPDATED_RENTP
        defaultCheckinBakShouldBeFound("rentp.notEquals=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRentpIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where rentp in DEFAULT_RENTP or UPDATED_RENTP
        defaultCheckinBakShouldBeFound("rentp.in=" + DEFAULT_RENTP + "," + UPDATED_RENTP);

        // Get all the checkinBakList where rentp equals to UPDATED_RENTP
        defaultCheckinBakShouldNotBeFound("rentp.in=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRentpIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where rentp is not null
        defaultCheckinBakShouldBeFound("rentp.specified=true");

        // Get all the checkinBakList where rentp is null
        defaultCheckinBakShouldNotBeFound("rentp.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRentpContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where rentp contains DEFAULT_RENTP
        defaultCheckinBakShouldBeFound("rentp.contains=" + DEFAULT_RENTP);

        // Get all the checkinBakList where rentp contains UPDATED_RENTP
        defaultCheckinBakShouldNotBeFound("rentp.contains=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRentpNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where rentp does not contain DEFAULT_RENTP
        defaultCheckinBakShouldNotBeFound("rentp.doesNotContain=" + DEFAULT_RENTP);

        // Get all the checkinBakList where rentp does not contain UPDATED_RENTP
        defaultCheckinBakShouldBeFound("rentp.doesNotContain=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByProtocolrentIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where protocolrent equals to DEFAULT_PROTOCOLRENT
        defaultCheckinBakShouldBeFound("protocolrent.equals=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinBakList where protocolrent equals to UPDATED_PROTOCOLRENT
        defaultCheckinBakShouldNotBeFound("protocolrent.equals=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByProtocolrentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where protocolrent not equals to DEFAULT_PROTOCOLRENT
        defaultCheckinBakShouldNotBeFound("protocolrent.notEquals=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinBakList where protocolrent not equals to UPDATED_PROTOCOLRENT
        defaultCheckinBakShouldBeFound("protocolrent.notEquals=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByProtocolrentIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where protocolrent in DEFAULT_PROTOCOLRENT or UPDATED_PROTOCOLRENT
        defaultCheckinBakShouldBeFound("protocolrent.in=" + DEFAULT_PROTOCOLRENT + "," + UPDATED_PROTOCOLRENT);

        // Get all the checkinBakList where protocolrent equals to UPDATED_PROTOCOLRENT
        defaultCheckinBakShouldNotBeFound("protocolrent.in=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByProtocolrentIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where protocolrent is not null
        defaultCheckinBakShouldBeFound("protocolrent.specified=true");

        // Get all the checkinBakList where protocolrent is null
        defaultCheckinBakShouldNotBeFound("protocolrent.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByProtocolrentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where protocolrent is greater than or equal to DEFAULT_PROTOCOLRENT
        defaultCheckinBakShouldBeFound("protocolrent.greaterThanOrEqual=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinBakList where protocolrent is greater than or equal to UPDATED_PROTOCOLRENT
        defaultCheckinBakShouldNotBeFound("protocolrent.greaterThanOrEqual=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByProtocolrentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where protocolrent is less than or equal to DEFAULT_PROTOCOLRENT
        defaultCheckinBakShouldBeFound("protocolrent.lessThanOrEqual=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinBakList where protocolrent is less than or equal to SMALLER_PROTOCOLRENT
        defaultCheckinBakShouldNotBeFound("protocolrent.lessThanOrEqual=" + SMALLER_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByProtocolrentIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where protocolrent is less than DEFAULT_PROTOCOLRENT
        defaultCheckinBakShouldNotBeFound("protocolrent.lessThan=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinBakList where protocolrent is less than UPDATED_PROTOCOLRENT
        defaultCheckinBakShouldBeFound("protocolrent.lessThan=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByProtocolrentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where protocolrent is greater than DEFAULT_PROTOCOLRENT
        defaultCheckinBakShouldNotBeFound("protocolrent.greaterThan=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinBakList where protocolrent is greater than SMALLER_PROTOCOLRENT
        defaultCheckinBakShouldBeFound("protocolrent.greaterThan=" + SMALLER_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where remark equals to DEFAULT_REMARK
        defaultCheckinBakShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the checkinBakList where remark equals to UPDATED_REMARK
        defaultCheckinBakShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where remark not equals to DEFAULT_REMARK
        defaultCheckinBakShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the checkinBakList where remark not equals to UPDATED_REMARK
        defaultCheckinBakShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultCheckinBakShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the checkinBakList where remark equals to UPDATED_REMARK
        defaultCheckinBakShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where remark is not null
        defaultCheckinBakShouldBeFound("remark.specified=true");

        // Get all the checkinBakList where remark is null
        defaultCheckinBakShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRemarkContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where remark contains DEFAULT_REMARK
        defaultCheckinBakShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the checkinBakList where remark contains UPDATED_REMARK
        defaultCheckinBakShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where remark does not contain DEFAULT_REMARK
        defaultCheckinBakShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the checkinBakList where remark does not contain UPDATED_REMARK
        defaultCheckinBakShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByComeinfoIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where comeinfo equals to DEFAULT_COMEINFO
        defaultCheckinBakShouldBeFound("comeinfo.equals=" + DEFAULT_COMEINFO);

        // Get all the checkinBakList where comeinfo equals to UPDATED_COMEINFO
        defaultCheckinBakShouldNotBeFound("comeinfo.equals=" + UPDATED_COMEINFO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByComeinfoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where comeinfo not equals to DEFAULT_COMEINFO
        defaultCheckinBakShouldNotBeFound("comeinfo.notEquals=" + DEFAULT_COMEINFO);

        // Get all the checkinBakList where comeinfo not equals to UPDATED_COMEINFO
        defaultCheckinBakShouldBeFound("comeinfo.notEquals=" + UPDATED_COMEINFO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByComeinfoIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where comeinfo in DEFAULT_COMEINFO or UPDATED_COMEINFO
        defaultCheckinBakShouldBeFound("comeinfo.in=" + DEFAULT_COMEINFO + "," + UPDATED_COMEINFO);

        // Get all the checkinBakList where comeinfo equals to UPDATED_COMEINFO
        defaultCheckinBakShouldNotBeFound("comeinfo.in=" + UPDATED_COMEINFO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByComeinfoIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where comeinfo is not null
        defaultCheckinBakShouldBeFound("comeinfo.specified=true");

        // Get all the checkinBakList where comeinfo is null
        defaultCheckinBakShouldNotBeFound("comeinfo.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByComeinfoContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where comeinfo contains DEFAULT_COMEINFO
        defaultCheckinBakShouldBeFound("comeinfo.contains=" + DEFAULT_COMEINFO);

        // Get all the checkinBakList where comeinfo contains UPDATED_COMEINFO
        defaultCheckinBakShouldNotBeFound("comeinfo.contains=" + UPDATED_COMEINFO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByComeinfoNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where comeinfo does not contain DEFAULT_COMEINFO
        defaultCheckinBakShouldNotBeFound("comeinfo.doesNotContain=" + DEFAULT_COMEINFO);

        // Get all the checkinBakList where comeinfo does not contain UPDATED_COMEINFO
        defaultCheckinBakShouldBeFound("comeinfo.doesNotContain=" + UPDATED_COMEINFO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGoinfoIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where goinfo equals to DEFAULT_GOINFO
        defaultCheckinBakShouldBeFound("goinfo.equals=" + DEFAULT_GOINFO);

        // Get all the checkinBakList where goinfo equals to UPDATED_GOINFO
        defaultCheckinBakShouldNotBeFound("goinfo.equals=" + UPDATED_GOINFO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGoinfoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where goinfo not equals to DEFAULT_GOINFO
        defaultCheckinBakShouldNotBeFound("goinfo.notEquals=" + DEFAULT_GOINFO);

        // Get all the checkinBakList where goinfo not equals to UPDATED_GOINFO
        defaultCheckinBakShouldBeFound("goinfo.notEquals=" + UPDATED_GOINFO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGoinfoIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where goinfo in DEFAULT_GOINFO or UPDATED_GOINFO
        defaultCheckinBakShouldBeFound("goinfo.in=" + DEFAULT_GOINFO + "," + UPDATED_GOINFO);

        // Get all the checkinBakList where goinfo equals to UPDATED_GOINFO
        defaultCheckinBakShouldNotBeFound("goinfo.in=" + UPDATED_GOINFO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGoinfoIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where goinfo is not null
        defaultCheckinBakShouldBeFound("goinfo.specified=true");

        // Get all the checkinBakList where goinfo is null
        defaultCheckinBakShouldNotBeFound("goinfo.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGoinfoContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where goinfo contains DEFAULT_GOINFO
        defaultCheckinBakShouldBeFound("goinfo.contains=" + DEFAULT_GOINFO);

        // Get all the checkinBakList where goinfo contains UPDATED_GOINFO
        defaultCheckinBakShouldNotBeFound("goinfo.contains=" + UPDATED_GOINFO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGoinfoNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where goinfo does not contain DEFAULT_GOINFO
        defaultCheckinBakShouldNotBeFound("goinfo.doesNotContain=" + DEFAULT_GOINFO);

        // Get all the checkinBakList where goinfo does not contain UPDATED_GOINFO
        defaultCheckinBakShouldBeFound("goinfo.doesNotContain=" + UPDATED_GOINFO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByPhonenIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where phonen equals to DEFAULT_PHONEN
        defaultCheckinBakShouldBeFound("phonen.equals=" + DEFAULT_PHONEN);

        // Get all the checkinBakList where phonen equals to UPDATED_PHONEN
        defaultCheckinBakShouldNotBeFound("phonen.equals=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByPhonenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where phonen not equals to DEFAULT_PHONEN
        defaultCheckinBakShouldNotBeFound("phonen.notEquals=" + DEFAULT_PHONEN);

        // Get all the checkinBakList where phonen not equals to UPDATED_PHONEN
        defaultCheckinBakShouldBeFound("phonen.notEquals=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByPhonenIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where phonen in DEFAULT_PHONEN or UPDATED_PHONEN
        defaultCheckinBakShouldBeFound("phonen.in=" + DEFAULT_PHONEN + "," + UPDATED_PHONEN);

        // Get all the checkinBakList where phonen equals to UPDATED_PHONEN
        defaultCheckinBakShouldNotBeFound("phonen.in=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByPhonenIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where phonen is not null
        defaultCheckinBakShouldBeFound("phonen.specified=true");

        // Get all the checkinBakList where phonen is null
        defaultCheckinBakShouldNotBeFound("phonen.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByPhonenContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where phonen contains DEFAULT_PHONEN
        defaultCheckinBakShouldBeFound("phonen.contains=" + DEFAULT_PHONEN);

        // Get all the checkinBakList where phonen contains UPDATED_PHONEN
        defaultCheckinBakShouldNotBeFound("phonen.contains=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByPhonenNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where phonen does not contain DEFAULT_PHONEN
        defaultCheckinBakShouldNotBeFound("phonen.doesNotContain=" + DEFAULT_PHONEN);

        // Get all the checkinBakList where phonen does not contain UPDATED_PHONEN
        defaultCheckinBakShouldBeFound("phonen.doesNotContain=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByEmpn2IsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where empn2 equals to DEFAULT_EMPN_2
        defaultCheckinBakShouldBeFound("empn2.equals=" + DEFAULT_EMPN_2);

        // Get all the checkinBakList where empn2 equals to UPDATED_EMPN_2
        defaultCheckinBakShouldNotBeFound("empn2.equals=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByEmpn2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where empn2 not equals to DEFAULT_EMPN_2
        defaultCheckinBakShouldNotBeFound("empn2.notEquals=" + DEFAULT_EMPN_2);

        // Get all the checkinBakList where empn2 not equals to UPDATED_EMPN_2
        defaultCheckinBakShouldBeFound("empn2.notEquals=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByEmpn2IsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where empn2 in DEFAULT_EMPN_2 or UPDATED_EMPN_2
        defaultCheckinBakShouldBeFound("empn2.in=" + DEFAULT_EMPN_2 + "," + UPDATED_EMPN_2);

        // Get all the checkinBakList where empn2 equals to UPDATED_EMPN_2
        defaultCheckinBakShouldNotBeFound("empn2.in=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByEmpn2IsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where empn2 is not null
        defaultCheckinBakShouldBeFound("empn2.specified=true");

        // Get all the checkinBakList where empn2 is null
        defaultCheckinBakShouldNotBeFound("empn2.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByEmpn2ContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where empn2 contains DEFAULT_EMPN_2
        defaultCheckinBakShouldBeFound("empn2.contains=" + DEFAULT_EMPN_2);

        // Get all the checkinBakList where empn2 contains UPDATED_EMPN_2
        defaultCheckinBakShouldNotBeFound("empn2.contains=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByEmpn2NotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where empn2 does not contain DEFAULT_EMPN_2
        defaultCheckinBakShouldNotBeFound("empn2.doesNotContain=" + DEFAULT_EMPN_2);

        // Get all the checkinBakList where empn2 does not contain UPDATED_EMPN_2
        defaultCheckinBakShouldBeFound("empn2.doesNotContain=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAdhocIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where adhoc equals to DEFAULT_ADHOC
        defaultCheckinBakShouldBeFound("adhoc.equals=" + DEFAULT_ADHOC);

        // Get all the checkinBakList where adhoc equals to UPDATED_ADHOC
        defaultCheckinBakShouldNotBeFound("adhoc.equals=" + UPDATED_ADHOC);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAdhocIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where adhoc not equals to DEFAULT_ADHOC
        defaultCheckinBakShouldNotBeFound("adhoc.notEquals=" + DEFAULT_ADHOC);

        // Get all the checkinBakList where adhoc not equals to UPDATED_ADHOC
        defaultCheckinBakShouldBeFound("adhoc.notEquals=" + UPDATED_ADHOC);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAdhocIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where adhoc in DEFAULT_ADHOC or UPDATED_ADHOC
        defaultCheckinBakShouldBeFound("adhoc.in=" + DEFAULT_ADHOC + "," + UPDATED_ADHOC);

        // Get all the checkinBakList where adhoc equals to UPDATED_ADHOC
        defaultCheckinBakShouldNotBeFound("adhoc.in=" + UPDATED_ADHOC);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAdhocIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where adhoc is not null
        defaultCheckinBakShouldBeFound("adhoc.specified=true");

        // Get all the checkinBakList where adhoc is null
        defaultCheckinBakShouldNotBeFound("adhoc.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAdhocContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where adhoc contains DEFAULT_ADHOC
        defaultCheckinBakShouldBeFound("adhoc.contains=" + DEFAULT_ADHOC);

        // Get all the checkinBakList where adhoc contains UPDATED_ADHOC
        defaultCheckinBakShouldNotBeFound("adhoc.contains=" + UPDATED_ADHOC);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAdhocNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where adhoc does not contain DEFAULT_ADHOC
        defaultCheckinBakShouldNotBeFound("adhoc.doesNotContain=" + DEFAULT_ADHOC);

        // Get all the checkinBakList where adhoc does not contain UPDATED_ADHOC
        defaultCheckinBakShouldBeFound("adhoc.doesNotContain=" + UPDATED_ADHOC);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAuditflagIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where auditflag equals to DEFAULT_AUDITFLAG
        defaultCheckinBakShouldBeFound("auditflag.equals=" + DEFAULT_AUDITFLAG);

        // Get all the checkinBakList where auditflag equals to UPDATED_AUDITFLAG
        defaultCheckinBakShouldNotBeFound("auditflag.equals=" + UPDATED_AUDITFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAuditflagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where auditflag not equals to DEFAULT_AUDITFLAG
        defaultCheckinBakShouldNotBeFound("auditflag.notEquals=" + DEFAULT_AUDITFLAG);

        // Get all the checkinBakList where auditflag not equals to UPDATED_AUDITFLAG
        defaultCheckinBakShouldBeFound("auditflag.notEquals=" + UPDATED_AUDITFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAuditflagIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where auditflag in DEFAULT_AUDITFLAG or UPDATED_AUDITFLAG
        defaultCheckinBakShouldBeFound("auditflag.in=" + DEFAULT_AUDITFLAG + "," + UPDATED_AUDITFLAG);

        // Get all the checkinBakList where auditflag equals to UPDATED_AUDITFLAG
        defaultCheckinBakShouldNotBeFound("auditflag.in=" + UPDATED_AUDITFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAuditflagIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where auditflag is not null
        defaultCheckinBakShouldBeFound("auditflag.specified=true");

        // Get all the checkinBakList where auditflag is null
        defaultCheckinBakShouldNotBeFound("auditflag.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAuditflagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where auditflag is greater than or equal to DEFAULT_AUDITFLAG
        defaultCheckinBakShouldBeFound("auditflag.greaterThanOrEqual=" + DEFAULT_AUDITFLAG);

        // Get all the checkinBakList where auditflag is greater than or equal to UPDATED_AUDITFLAG
        defaultCheckinBakShouldNotBeFound("auditflag.greaterThanOrEqual=" + UPDATED_AUDITFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAuditflagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where auditflag is less than or equal to DEFAULT_AUDITFLAG
        defaultCheckinBakShouldBeFound("auditflag.lessThanOrEqual=" + DEFAULT_AUDITFLAG);

        // Get all the checkinBakList where auditflag is less than or equal to SMALLER_AUDITFLAG
        defaultCheckinBakShouldNotBeFound("auditflag.lessThanOrEqual=" + SMALLER_AUDITFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAuditflagIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where auditflag is less than DEFAULT_AUDITFLAG
        defaultCheckinBakShouldNotBeFound("auditflag.lessThan=" + DEFAULT_AUDITFLAG);

        // Get all the checkinBakList where auditflag is less than UPDATED_AUDITFLAG
        defaultCheckinBakShouldBeFound("auditflag.lessThan=" + UPDATED_AUDITFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByAuditflagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where auditflag is greater than DEFAULT_AUDITFLAG
        defaultCheckinBakShouldNotBeFound("auditflag.greaterThan=" + DEFAULT_AUDITFLAG);

        // Get all the checkinBakList where auditflag is greater than SMALLER_AUDITFLAG
        defaultCheckinBakShouldBeFound("auditflag.greaterThan=" + SMALLER_AUDITFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGroupnIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where groupn equals to DEFAULT_GROUPN
        defaultCheckinBakShouldBeFound("groupn.equals=" + DEFAULT_GROUPN);

        // Get all the checkinBakList where groupn equals to UPDATED_GROUPN
        defaultCheckinBakShouldNotBeFound("groupn.equals=" + UPDATED_GROUPN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGroupnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where groupn not equals to DEFAULT_GROUPN
        defaultCheckinBakShouldNotBeFound("groupn.notEquals=" + DEFAULT_GROUPN);

        // Get all the checkinBakList where groupn not equals to UPDATED_GROUPN
        defaultCheckinBakShouldBeFound("groupn.notEquals=" + UPDATED_GROUPN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGroupnIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where groupn in DEFAULT_GROUPN or UPDATED_GROUPN
        defaultCheckinBakShouldBeFound("groupn.in=" + DEFAULT_GROUPN + "," + UPDATED_GROUPN);

        // Get all the checkinBakList where groupn equals to UPDATED_GROUPN
        defaultCheckinBakShouldNotBeFound("groupn.in=" + UPDATED_GROUPN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGroupnIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where groupn is not null
        defaultCheckinBakShouldBeFound("groupn.specified=true");

        // Get all the checkinBakList where groupn is null
        defaultCheckinBakShouldNotBeFound("groupn.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGroupnContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where groupn contains DEFAULT_GROUPN
        defaultCheckinBakShouldBeFound("groupn.contains=" + DEFAULT_GROUPN);

        // Get all the checkinBakList where groupn contains UPDATED_GROUPN
        defaultCheckinBakShouldNotBeFound("groupn.contains=" + UPDATED_GROUPN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGroupnNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where groupn does not contain DEFAULT_GROUPN
        defaultCheckinBakShouldNotBeFound("groupn.doesNotContain=" + DEFAULT_GROUPN);

        // Get all the checkinBakList where groupn does not contain UPDATED_GROUPN
        defaultCheckinBakShouldBeFound("groupn.doesNotContain=" + UPDATED_GROUPN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where payment equals to DEFAULT_PAYMENT
        defaultCheckinBakShouldBeFound("payment.equals=" + DEFAULT_PAYMENT);

        // Get all the checkinBakList where payment equals to UPDATED_PAYMENT
        defaultCheckinBakShouldNotBeFound("payment.equals=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByPaymentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where payment not equals to DEFAULT_PAYMENT
        defaultCheckinBakShouldNotBeFound("payment.notEquals=" + DEFAULT_PAYMENT);

        // Get all the checkinBakList where payment not equals to UPDATED_PAYMENT
        defaultCheckinBakShouldBeFound("payment.notEquals=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByPaymentIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where payment in DEFAULT_PAYMENT or UPDATED_PAYMENT
        defaultCheckinBakShouldBeFound("payment.in=" + DEFAULT_PAYMENT + "," + UPDATED_PAYMENT);

        // Get all the checkinBakList where payment equals to UPDATED_PAYMENT
        defaultCheckinBakShouldNotBeFound("payment.in=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByPaymentIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where payment is not null
        defaultCheckinBakShouldBeFound("payment.specified=true");

        // Get all the checkinBakList where payment is null
        defaultCheckinBakShouldNotBeFound("payment.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByPaymentContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where payment contains DEFAULT_PAYMENT
        defaultCheckinBakShouldBeFound("payment.contains=" + DEFAULT_PAYMENT);

        // Get all the checkinBakList where payment contains UPDATED_PAYMENT
        defaultCheckinBakShouldNotBeFound("payment.contains=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByPaymentNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where payment does not contain DEFAULT_PAYMENT
        defaultCheckinBakShouldNotBeFound("payment.doesNotContain=" + DEFAULT_PAYMENT);

        // Get all the checkinBakList where payment does not contain UPDATED_PAYMENT
        defaultCheckinBakShouldBeFound("payment.doesNotContain=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMtypeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where mtype equals to DEFAULT_MTYPE
        defaultCheckinBakShouldBeFound("mtype.equals=" + DEFAULT_MTYPE);

        // Get all the checkinBakList where mtype equals to UPDATED_MTYPE
        defaultCheckinBakShouldNotBeFound("mtype.equals=" + UPDATED_MTYPE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMtypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where mtype not equals to DEFAULT_MTYPE
        defaultCheckinBakShouldNotBeFound("mtype.notEquals=" + DEFAULT_MTYPE);

        // Get all the checkinBakList where mtype not equals to UPDATED_MTYPE
        defaultCheckinBakShouldBeFound("mtype.notEquals=" + UPDATED_MTYPE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMtypeIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where mtype in DEFAULT_MTYPE or UPDATED_MTYPE
        defaultCheckinBakShouldBeFound("mtype.in=" + DEFAULT_MTYPE + "," + UPDATED_MTYPE);

        // Get all the checkinBakList where mtype equals to UPDATED_MTYPE
        defaultCheckinBakShouldNotBeFound("mtype.in=" + UPDATED_MTYPE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMtypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where mtype is not null
        defaultCheckinBakShouldBeFound("mtype.specified=true");

        // Get all the checkinBakList where mtype is null
        defaultCheckinBakShouldNotBeFound("mtype.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMtypeContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where mtype contains DEFAULT_MTYPE
        defaultCheckinBakShouldBeFound("mtype.contains=" + DEFAULT_MTYPE);

        // Get all the checkinBakList where mtype contains UPDATED_MTYPE
        defaultCheckinBakShouldNotBeFound("mtype.contains=" + UPDATED_MTYPE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMtypeNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where mtype does not contain DEFAULT_MTYPE
        defaultCheckinBakShouldNotBeFound("mtype.doesNotContain=" + DEFAULT_MTYPE);

        // Get all the checkinBakList where mtype does not contain UPDATED_MTYPE
        defaultCheckinBakShouldBeFound("mtype.doesNotContain=" + UPDATED_MTYPE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMemoIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where memo equals to DEFAULT_MEMO
        defaultCheckinBakShouldBeFound("memo.equals=" + DEFAULT_MEMO);

        // Get all the checkinBakList where memo equals to UPDATED_MEMO
        defaultCheckinBakShouldNotBeFound("memo.equals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where memo not equals to DEFAULT_MEMO
        defaultCheckinBakShouldNotBeFound("memo.notEquals=" + DEFAULT_MEMO);

        // Get all the checkinBakList where memo not equals to UPDATED_MEMO
        defaultCheckinBakShouldBeFound("memo.notEquals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMemoIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where memo in DEFAULT_MEMO or UPDATED_MEMO
        defaultCheckinBakShouldBeFound("memo.in=" + DEFAULT_MEMO + "," + UPDATED_MEMO);

        // Get all the checkinBakList where memo equals to UPDATED_MEMO
        defaultCheckinBakShouldNotBeFound("memo.in=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where memo is not null
        defaultCheckinBakShouldBeFound("memo.specified=true");

        // Get all the checkinBakList where memo is null
        defaultCheckinBakShouldNotBeFound("memo.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMemoContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where memo contains DEFAULT_MEMO
        defaultCheckinBakShouldBeFound("memo.contains=" + DEFAULT_MEMO);

        // Get all the checkinBakList where memo contains UPDATED_MEMO
        defaultCheckinBakShouldNotBeFound("memo.contains=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMemoNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where memo does not contain DEFAULT_MEMO
        defaultCheckinBakShouldNotBeFound("memo.doesNotContain=" + DEFAULT_MEMO);

        // Get all the checkinBakList where memo does not contain UPDATED_MEMO
        defaultCheckinBakShouldBeFound("memo.doesNotContain=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByFlightIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where flight equals to DEFAULT_FLIGHT
        defaultCheckinBakShouldBeFound("flight.equals=" + DEFAULT_FLIGHT);

        // Get all the checkinBakList where flight equals to UPDATED_FLIGHT
        defaultCheckinBakShouldNotBeFound("flight.equals=" + UPDATED_FLIGHT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByFlightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where flight not equals to DEFAULT_FLIGHT
        defaultCheckinBakShouldNotBeFound("flight.notEquals=" + DEFAULT_FLIGHT);

        // Get all the checkinBakList where flight not equals to UPDATED_FLIGHT
        defaultCheckinBakShouldBeFound("flight.notEquals=" + UPDATED_FLIGHT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByFlightIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where flight in DEFAULT_FLIGHT or UPDATED_FLIGHT
        defaultCheckinBakShouldBeFound("flight.in=" + DEFAULT_FLIGHT + "," + UPDATED_FLIGHT);

        // Get all the checkinBakList where flight equals to UPDATED_FLIGHT
        defaultCheckinBakShouldNotBeFound("flight.in=" + UPDATED_FLIGHT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByFlightIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where flight is not null
        defaultCheckinBakShouldBeFound("flight.specified=true");

        // Get all the checkinBakList where flight is null
        defaultCheckinBakShouldNotBeFound("flight.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByFlightContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where flight contains DEFAULT_FLIGHT
        defaultCheckinBakShouldBeFound("flight.contains=" + DEFAULT_FLIGHT);

        // Get all the checkinBakList where flight contains UPDATED_FLIGHT
        defaultCheckinBakShouldNotBeFound("flight.contains=" + UPDATED_FLIGHT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByFlightNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where flight does not contain DEFAULT_FLIGHT
        defaultCheckinBakShouldNotBeFound("flight.doesNotContain=" + DEFAULT_FLIGHT);

        // Get all the checkinBakList where flight does not contain UPDATED_FLIGHT
        defaultCheckinBakShouldBeFound("flight.doesNotContain=" + UPDATED_FLIGHT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByCreditIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where credit equals to DEFAULT_CREDIT
        defaultCheckinBakShouldBeFound("credit.equals=" + DEFAULT_CREDIT);

        // Get all the checkinBakList where credit equals to UPDATED_CREDIT
        defaultCheckinBakShouldNotBeFound("credit.equals=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByCreditIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where credit not equals to DEFAULT_CREDIT
        defaultCheckinBakShouldNotBeFound("credit.notEquals=" + DEFAULT_CREDIT);

        // Get all the checkinBakList where credit not equals to UPDATED_CREDIT
        defaultCheckinBakShouldBeFound("credit.notEquals=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByCreditIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where credit in DEFAULT_CREDIT or UPDATED_CREDIT
        defaultCheckinBakShouldBeFound("credit.in=" + DEFAULT_CREDIT + "," + UPDATED_CREDIT);

        // Get all the checkinBakList where credit equals to UPDATED_CREDIT
        defaultCheckinBakShouldNotBeFound("credit.in=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByCreditIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where credit is not null
        defaultCheckinBakShouldBeFound("credit.specified=true");

        // Get all the checkinBakList where credit is null
        defaultCheckinBakShouldNotBeFound("credit.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByCreditIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where credit is greater than or equal to DEFAULT_CREDIT
        defaultCheckinBakShouldBeFound("credit.greaterThanOrEqual=" + DEFAULT_CREDIT);

        // Get all the checkinBakList where credit is greater than or equal to UPDATED_CREDIT
        defaultCheckinBakShouldNotBeFound("credit.greaterThanOrEqual=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByCreditIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where credit is less than or equal to DEFAULT_CREDIT
        defaultCheckinBakShouldBeFound("credit.lessThanOrEqual=" + DEFAULT_CREDIT);

        // Get all the checkinBakList where credit is less than or equal to SMALLER_CREDIT
        defaultCheckinBakShouldNotBeFound("credit.lessThanOrEqual=" + SMALLER_CREDIT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByCreditIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where credit is less than DEFAULT_CREDIT
        defaultCheckinBakShouldNotBeFound("credit.lessThan=" + DEFAULT_CREDIT);

        // Get all the checkinBakList where credit is less than UPDATED_CREDIT
        defaultCheckinBakShouldBeFound("credit.lessThan=" + UPDATED_CREDIT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByCreditIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where credit is greater than DEFAULT_CREDIT
        defaultCheckinBakShouldNotBeFound("credit.greaterThan=" + DEFAULT_CREDIT);

        // Get all the checkinBakList where credit is greater than SMALLER_CREDIT
        defaultCheckinBakShouldBeFound("credit.greaterThan=" + SMALLER_CREDIT);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByTalklevelIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where talklevel equals to DEFAULT_TALKLEVEL
        defaultCheckinBakShouldBeFound("talklevel.equals=" + DEFAULT_TALKLEVEL);

        // Get all the checkinBakList where talklevel equals to UPDATED_TALKLEVEL
        defaultCheckinBakShouldNotBeFound("talklevel.equals=" + UPDATED_TALKLEVEL);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByTalklevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where talklevel not equals to DEFAULT_TALKLEVEL
        defaultCheckinBakShouldNotBeFound("talklevel.notEquals=" + DEFAULT_TALKLEVEL);

        // Get all the checkinBakList where talklevel not equals to UPDATED_TALKLEVEL
        defaultCheckinBakShouldBeFound("talklevel.notEquals=" + UPDATED_TALKLEVEL);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByTalklevelIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where talklevel in DEFAULT_TALKLEVEL or UPDATED_TALKLEVEL
        defaultCheckinBakShouldBeFound("talklevel.in=" + DEFAULT_TALKLEVEL + "," + UPDATED_TALKLEVEL);

        // Get all the checkinBakList where talklevel equals to UPDATED_TALKLEVEL
        defaultCheckinBakShouldNotBeFound("talklevel.in=" + UPDATED_TALKLEVEL);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByTalklevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where talklevel is not null
        defaultCheckinBakShouldBeFound("talklevel.specified=true");

        // Get all the checkinBakList where talklevel is null
        defaultCheckinBakShouldNotBeFound("talklevel.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByTalklevelContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where talklevel contains DEFAULT_TALKLEVEL
        defaultCheckinBakShouldBeFound("talklevel.contains=" + DEFAULT_TALKLEVEL);

        // Get all the checkinBakList where talklevel contains UPDATED_TALKLEVEL
        defaultCheckinBakShouldNotBeFound("talklevel.contains=" + UPDATED_TALKLEVEL);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByTalklevelNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where talklevel does not contain DEFAULT_TALKLEVEL
        defaultCheckinBakShouldNotBeFound("talklevel.doesNotContain=" + DEFAULT_TALKLEVEL);

        // Get all the checkinBakList where talklevel does not contain UPDATED_TALKLEVEL
        defaultCheckinBakShouldBeFound("talklevel.doesNotContain=" + UPDATED_TALKLEVEL);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByLfSignIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where lfSign equals to DEFAULT_LF_SIGN
        defaultCheckinBakShouldBeFound("lfSign.equals=" + DEFAULT_LF_SIGN);

        // Get all the checkinBakList where lfSign equals to UPDATED_LF_SIGN
        defaultCheckinBakShouldNotBeFound("lfSign.equals=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByLfSignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where lfSign not equals to DEFAULT_LF_SIGN
        defaultCheckinBakShouldNotBeFound("lfSign.notEquals=" + DEFAULT_LF_SIGN);

        // Get all the checkinBakList where lfSign not equals to UPDATED_LF_SIGN
        defaultCheckinBakShouldBeFound("lfSign.notEquals=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByLfSignIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where lfSign in DEFAULT_LF_SIGN or UPDATED_LF_SIGN
        defaultCheckinBakShouldBeFound("lfSign.in=" + DEFAULT_LF_SIGN + "," + UPDATED_LF_SIGN);

        // Get all the checkinBakList where lfSign equals to UPDATED_LF_SIGN
        defaultCheckinBakShouldNotBeFound("lfSign.in=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByLfSignIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where lfSign is not null
        defaultCheckinBakShouldBeFound("lfSign.specified=true");

        // Get all the checkinBakList where lfSign is null
        defaultCheckinBakShouldNotBeFound("lfSign.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByLfSignContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where lfSign contains DEFAULT_LF_SIGN
        defaultCheckinBakShouldBeFound("lfSign.contains=" + DEFAULT_LF_SIGN);

        // Get all the checkinBakList where lfSign contains UPDATED_LF_SIGN
        defaultCheckinBakShouldNotBeFound("lfSign.contains=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByLfSignNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where lfSign does not contain DEFAULT_LF_SIGN
        defaultCheckinBakShouldNotBeFound("lfSign.doesNotContain=" + DEFAULT_LF_SIGN);

        // Get all the checkinBakList where lfSign does not contain UPDATED_LF_SIGN
        defaultCheckinBakShouldBeFound("lfSign.doesNotContain=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByKeynumIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where keynum equals to DEFAULT_KEYNUM
        defaultCheckinBakShouldBeFound("keynum.equals=" + DEFAULT_KEYNUM);

        // Get all the checkinBakList where keynum equals to UPDATED_KEYNUM
        defaultCheckinBakShouldNotBeFound("keynum.equals=" + UPDATED_KEYNUM);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByKeynumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where keynum not equals to DEFAULT_KEYNUM
        defaultCheckinBakShouldNotBeFound("keynum.notEquals=" + DEFAULT_KEYNUM);

        // Get all the checkinBakList where keynum not equals to UPDATED_KEYNUM
        defaultCheckinBakShouldBeFound("keynum.notEquals=" + UPDATED_KEYNUM);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByKeynumIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where keynum in DEFAULT_KEYNUM or UPDATED_KEYNUM
        defaultCheckinBakShouldBeFound("keynum.in=" + DEFAULT_KEYNUM + "," + UPDATED_KEYNUM);

        // Get all the checkinBakList where keynum equals to UPDATED_KEYNUM
        defaultCheckinBakShouldNotBeFound("keynum.in=" + UPDATED_KEYNUM);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByKeynumIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where keynum is not null
        defaultCheckinBakShouldBeFound("keynum.specified=true");

        // Get all the checkinBakList where keynum is null
        defaultCheckinBakShouldNotBeFound("keynum.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByKeynumContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where keynum contains DEFAULT_KEYNUM
        defaultCheckinBakShouldBeFound("keynum.contains=" + DEFAULT_KEYNUM);

        // Get all the checkinBakList where keynum contains UPDATED_KEYNUM
        defaultCheckinBakShouldNotBeFound("keynum.contains=" + UPDATED_KEYNUM);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByKeynumNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where keynum does not contain DEFAULT_KEYNUM
        defaultCheckinBakShouldNotBeFound("keynum.doesNotContain=" + DEFAULT_KEYNUM);

        // Get all the checkinBakList where keynum does not contain UPDATED_KEYNUM
        defaultCheckinBakShouldBeFound("keynum.doesNotContain=" + UPDATED_KEYNUM);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIcNumIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where icNum equals to DEFAULT_IC_NUM
        defaultCheckinBakShouldBeFound("icNum.equals=" + DEFAULT_IC_NUM);

        // Get all the checkinBakList where icNum equals to UPDATED_IC_NUM
        defaultCheckinBakShouldNotBeFound("icNum.equals=" + UPDATED_IC_NUM);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIcNumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where icNum not equals to DEFAULT_IC_NUM
        defaultCheckinBakShouldNotBeFound("icNum.notEquals=" + DEFAULT_IC_NUM);

        // Get all the checkinBakList where icNum not equals to UPDATED_IC_NUM
        defaultCheckinBakShouldBeFound("icNum.notEquals=" + UPDATED_IC_NUM);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIcNumIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where icNum in DEFAULT_IC_NUM or UPDATED_IC_NUM
        defaultCheckinBakShouldBeFound("icNum.in=" + DEFAULT_IC_NUM + "," + UPDATED_IC_NUM);

        // Get all the checkinBakList where icNum equals to UPDATED_IC_NUM
        defaultCheckinBakShouldNotBeFound("icNum.in=" + UPDATED_IC_NUM);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIcNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where icNum is not null
        defaultCheckinBakShouldBeFound("icNum.specified=true");

        // Get all the checkinBakList where icNum is null
        defaultCheckinBakShouldNotBeFound("icNum.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIcNumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where icNum is greater than or equal to DEFAULT_IC_NUM
        defaultCheckinBakShouldBeFound("icNum.greaterThanOrEqual=" + DEFAULT_IC_NUM);

        // Get all the checkinBakList where icNum is greater than or equal to UPDATED_IC_NUM
        defaultCheckinBakShouldNotBeFound("icNum.greaterThanOrEqual=" + UPDATED_IC_NUM);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIcNumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where icNum is less than or equal to DEFAULT_IC_NUM
        defaultCheckinBakShouldBeFound("icNum.lessThanOrEqual=" + DEFAULT_IC_NUM);

        // Get all the checkinBakList where icNum is less than or equal to SMALLER_IC_NUM
        defaultCheckinBakShouldNotBeFound("icNum.lessThanOrEqual=" + SMALLER_IC_NUM);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIcNumIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where icNum is less than DEFAULT_IC_NUM
        defaultCheckinBakShouldNotBeFound("icNum.lessThan=" + DEFAULT_IC_NUM);

        // Get all the checkinBakList where icNum is less than UPDATED_IC_NUM
        defaultCheckinBakShouldBeFound("icNum.lessThan=" + UPDATED_IC_NUM);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIcNumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where icNum is greater than DEFAULT_IC_NUM
        defaultCheckinBakShouldNotBeFound("icNum.greaterThan=" + DEFAULT_IC_NUM);

        // Get all the checkinBakList where icNum is greater than SMALLER_IC_NUM
        defaultCheckinBakShouldBeFound("icNum.greaterThan=" + SMALLER_IC_NUM);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByBhIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where bh equals to DEFAULT_BH
        defaultCheckinBakShouldBeFound("bh.equals=" + DEFAULT_BH);

        // Get all the checkinBakList where bh equals to UPDATED_BH
        defaultCheckinBakShouldNotBeFound("bh.equals=" + UPDATED_BH);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByBhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where bh not equals to DEFAULT_BH
        defaultCheckinBakShouldNotBeFound("bh.notEquals=" + DEFAULT_BH);

        // Get all the checkinBakList where bh not equals to UPDATED_BH
        defaultCheckinBakShouldBeFound("bh.notEquals=" + UPDATED_BH);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByBhIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where bh in DEFAULT_BH or UPDATED_BH
        defaultCheckinBakShouldBeFound("bh.in=" + DEFAULT_BH + "," + UPDATED_BH);

        // Get all the checkinBakList where bh equals to UPDATED_BH
        defaultCheckinBakShouldNotBeFound("bh.in=" + UPDATED_BH);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByBhIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where bh is not null
        defaultCheckinBakShouldBeFound("bh.specified=true");

        // Get all the checkinBakList where bh is null
        defaultCheckinBakShouldNotBeFound("bh.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByBhIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where bh is greater than or equal to DEFAULT_BH
        defaultCheckinBakShouldBeFound("bh.greaterThanOrEqual=" + DEFAULT_BH);

        // Get all the checkinBakList where bh is greater than or equal to UPDATED_BH
        defaultCheckinBakShouldNotBeFound("bh.greaterThanOrEqual=" + UPDATED_BH);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByBhIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where bh is less than or equal to DEFAULT_BH
        defaultCheckinBakShouldBeFound("bh.lessThanOrEqual=" + DEFAULT_BH);

        // Get all the checkinBakList where bh is less than or equal to SMALLER_BH
        defaultCheckinBakShouldNotBeFound("bh.lessThanOrEqual=" + SMALLER_BH);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByBhIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where bh is less than DEFAULT_BH
        defaultCheckinBakShouldNotBeFound("bh.lessThan=" + DEFAULT_BH);

        // Get all the checkinBakList where bh is less than UPDATED_BH
        defaultCheckinBakShouldBeFound("bh.lessThan=" + UPDATED_BH);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByBhIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where bh is greater than DEFAULT_BH
        defaultCheckinBakShouldNotBeFound("bh.greaterThan=" + DEFAULT_BH);

        // Get all the checkinBakList where bh is greater than SMALLER_BH
        defaultCheckinBakShouldBeFound("bh.greaterThan=" + SMALLER_BH);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIcOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where icOwner equals to DEFAULT_IC_OWNER
        defaultCheckinBakShouldBeFound("icOwner.equals=" + DEFAULT_IC_OWNER);

        // Get all the checkinBakList where icOwner equals to UPDATED_IC_OWNER
        defaultCheckinBakShouldNotBeFound("icOwner.equals=" + UPDATED_IC_OWNER);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIcOwnerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where icOwner not equals to DEFAULT_IC_OWNER
        defaultCheckinBakShouldNotBeFound("icOwner.notEquals=" + DEFAULT_IC_OWNER);

        // Get all the checkinBakList where icOwner not equals to UPDATED_IC_OWNER
        defaultCheckinBakShouldBeFound("icOwner.notEquals=" + UPDATED_IC_OWNER);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIcOwnerIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where icOwner in DEFAULT_IC_OWNER or UPDATED_IC_OWNER
        defaultCheckinBakShouldBeFound("icOwner.in=" + DEFAULT_IC_OWNER + "," + UPDATED_IC_OWNER);

        // Get all the checkinBakList where icOwner equals to UPDATED_IC_OWNER
        defaultCheckinBakShouldNotBeFound("icOwner.in=" + UPDATED_IC_OWNER);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIcOwnerIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where icOwner is not null
        defaultCheckinBakShouldBeFound("icOwner.specified=true");

        // Get all the checkinBakList where icOwner is null
        defaultCheckinBakShouldNotBeFound("icOwner.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIcOwnerContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where icOwner contains DEFAULT_IC_OWNER
        defaultCheckinBakShouldBeFound("icOwner.contains=" + DEFAULT_IC_OWNER);

        // Get all the checkinBakList where icOwner contains UPDATED_IC_OWNER
        defaultCheckinBakShouldNotBeFound("icOwner.contains=" + UPDATED_IC_OWNER);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByIcOwnerNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where icOwner does not contain DEFAULT_IC_OWNER
        defaultCheckinBakShouldNotBeFound("icOwner.doesNotContain=" + DEFAULT_IC_OWNER);

        // Get all the checkinBakList where icOwner does not contain UPDATED_IC_OWNER
        defaultCheckinBakShouldBeFound("icOwner.doesNotContain=" + UPDATED_IC_OWNER);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMarkIdIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where markId equals to DEFAULT_MARK_ID
        defaultCheckinBakShouldBeFound("markId.equals=" + DEFAULT_MARK_ID);

        // Get all the checkinBakList where markId equals to UPDATED_MARK_ID
        defaultCheckinBakShouldNotBeFound("markId.equals=" + UPDATED_MARK_ID);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMarkIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where markId not equals to DEFAULT_MARK_ID
        defaultCheckinBakShouldNotBeFound("markId.notEquals=" + DEFAULT_MARK_ID);

        // Get all the checkinBakList where markId not equals to UPDATED_MARK_ID
        defaultCheckinBakShouldBeFound("markId.notEquals=" + UPDATED_MARK_ID);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMarkIdIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where markId in DEFAULT_MARK_ID or UPDATED_MARK_ID
        defaultCheckinBakShouldBeFound("markId.in=" + DEFAULT_MARK_ID + "," + UPDATED_MARK_ID);

        // Get all the checkinBakList where markId equals to UPDATED_MARK_ID
        defaultCheckinBakShouldNotBeFound("markId.in=" + UPDATED_MARK_ID);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMarkIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where markId is not null
        defaultCheckinBakShouldBeFound("markId.specified=true");

        // Get all the checkinBakList where markId is null
        defaultCheckinBakShouldNotBeFound("markId.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMarkIdContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where markId contains DEFAULT_MARK_ID
        defaultCheckinBakShouldBeFound("markId.contains=" + DEFAULT_MARK_ID);

        // Get all the checkinBakList where markId contains UPDATED_MARK_ID
        defaultCheckinBakShouldNotBeFound("markId.contains=" + UPDATED_MARK_ID);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByMarkIdNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where markId does not contain DEFAULT_MARK_ID
        defaultCheckinBakShouldNotBeFound("markId.doesNotContain=" + DEFAULT_MARK_ID);

        // Get all the checkinBakList where markId does not contain UPDATED_MARK_ID
        defaultCheckinBakShouldBeFound("markId.doesNotContain=" + UPDATED_MARK_ID);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGjIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where gj equals to DEFAULT_GJ
        defaultCheckinBakShouldBeFound("gj.equals=" + DEFAULT_GJ);

        // Get all the checkinBakList where gj equals to UPDATED_GJ
        defaultCheckinBakShouldNotBeFound("gj.equals=" + UPDATED_GJ);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where gj not equals to DEFAULT_GJ
        defaultCheckinBakShouldNotBeFound("gj.notEquals=" + DEFAULT_GJ);

        // Get all the checkinBakList where gj not equals to UPDATED_GJ
        defaultCheckinBakShouldBeFound("gj.notEquals=" + UPDATED_GJ);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGjIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where gj in DEFAULT_GJ or UPDATED_GJ
        defaultCheckinBakShouldBeFound("gj.in=" + DEFAULT_GJ + "," + UPDATED_GJ);

        // Get all the checkinBakList where gj equals to UPDATED_GJ
        defaultCheckinBakShouldNotBeFound("gj.in=" + UPDATED_GJ);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGjIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where gj is not null
        defaultCheckinBakShouldBeFound("gj.specified=true");

        // Get all the checkinBakList where gj is null
        defaultCheckinBakShouldNotBeFound("gj.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGjContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where gj contains DEFAULT_GJ
        defaultCheckinBakShouldBeFound("gj.contains=" + DEFAULT_GJ);

        // Get all the checkinBakList where gj contains UPDATED_GJ
        defaultCheckinBakShouldNotBeFound("gj.contains=" + UPDATED_GJ);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByGjNotContainsSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where gj does not contain DEFAULT_GJ
        defaultCheckinBakShouldNotBeFound("gj.doesNotContain=" + DEFAULT_GJ);

        // Get all the checkinBakList where gj does not contain UPDATED_GJ
        defaultCheckinBakShouldBeFound("gj.doesNotContain=" + UPDATED_GJ);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByYfjIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where yfj equals to DEFAULT_YFJ
        defaultCheckinBakShouldBeFound("yfj.equals=" + DEFAULT_YFJ);

        // Get all the checkinBakList where yfj equals to UPDATED_YFJ
        defaultCheckinBakShouldNotBeFound("yfj.equals=" + UPDATED_YFJ);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByYfjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where yfj not equals to DEFAULT_YFJ
        defaultCheckinBakShouldNotBeFound("yfj.notEquals=" + DEFAULT_YFJ);

        // Get all the checkinBakList where yfj not equals to UPDATED_YFJ
        defaultCheckinBakShouldBeFound("yfj.notEquals=" + UPDATED_YFJ);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByYfjIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where yfj in DEFAULT_YFJ or UPDATED_YFJ
        defaultCheckinBakShouldBeFound("yfj.in=" + DEFAULT_YFJ + "," + UPDATED_YFJ);

        // Get all the checkinBakList where yfj equals to UPDATED_YFJ
        defaultCheckinBakShouldNotBeFound("yfj.in=" + UPDATED_YFJ);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByYfjIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where yfj is not null
        defaultCheckinBakShouldBeFound("yfj.specified=true");

        // Get all the checkinBakList where yfj is null
        defaultCheckinBakShouldNotBeFound("yfj.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinBaksByYfjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where yfj is greater than or equal to DEFAULT_YFJ
        defaultCheckinBakShouldBeFound("yfj.greaterThanOrEqual=" + DEFAULT_YFJ);

        // Get all the checkinBakList where yfj is greater than or equal to UPDATED_YFJ
        defaultCheckinBakShouldNotBeFound("yfj.greaterThanOrEqual=" + UPDATED_YFJ);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByYfjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where yfj is less than or equal to DEFAULT_YFJ
        defaultCheckinBakShouldBeFound("yfj.lessThanOrEqual=" + DEFAULT_YFJ);

        // Get all the checkinBakList where yfj is less than or equal to SMALLER_YFJ
        defaultCheckinBakShouldNotBeFound("yfj.lessThanOrEqual=" + SMALLER_YFJ);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByYfjIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where yfj is less than DEFAULT_YFJ
        defaultCheckinBakShouldNotBeFound("yfj.lessThan=" + DEFAULT_YFJ);

        // Get all the checkinBakList where yfj is less than UPDATED_YFJ
        defaultCheckinBakShouldBeFound("yfj.lessThan=" + UPDATED_YFJ);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByYfjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where yfj is greater than DEFAULT_YFJ
        defaultCheckinBakShouldNotBeFound("yfj.greaterThan=" + DEFAULT_YFJ);

        // Get all the checkinBakList where yfj is greater than SMALLER_YFJ
        defaultCheckinBakShouldBeFound("yfj.greaterThan=" + SMALLER_YFJ);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByHoteldateIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where hoteldate equals to DEFAULT_HOTELDATE
        defaultCheckinBakShouldBeFound("hoteldate.equals=" + DEFAULT_HOTELDATE);

        // Get all the checkinBakList where hoteldate equals to UPDATED_HOTELDATE
        defaultCheckinBakShouldNotBeFound("hoteldate.equals=" + UPDATED_HOTELDATE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByHoteldateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where hoteldate not equals to DEFAULT_HOTELDATE
        defaultCheckinBakShouldNotBeFound("hoteldate.notEquals=" + DEFAULT_HOTELDATE);

        // Get all the checkinBakList where hoteldate not equals to UPDATED_HOTELDATE
        defaultCheckinBakShouldBeFound("hoteldate.notEquals=" + UPDATED_HOTELDATE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByHoteldateIsInShouldWork() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where hoteldate in DEFAULT_HOTELDATE or UPDATED_HOTELDATE
        defaultCheckinBakShouldBeFound("hoteldate.in=" + DEFAULT_HOTELDATE + "," + UPDATED_HOTELDATE);

        // Get all the checkinBakList where hoteldate equals to UPDATED_HOTELDATE
        defaultCheckinBakShouldNotBeFound("hoteldate.in=" + UPDATED_HOTELDATE);
    }

    @Test
    @Transactional
    void getAllCheckinBaksByHoteldateIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        // Get all the checkinBakList where hoteldate is not null
        defaultCheckinBakShouldBeFound("hoteldate.specified=true");

        // Get all the checkinBakList where hoteldate is null
        defaultCheckinBakShouldNotBeFound("hoteldate.specified=false");
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckinBakShouldBeFound(String filter) throws Exception {
        restCheckinBakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkinBak.getId().intValue())))
            .andExpect(jsonPath("$.[*].guestId").value(hasItem(DEFAULT_GUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].indatetime").value(hasItem(DEFAULT_INDATETIME.toString())))
            .andExpect(jsonPath("$.[*].residefate").value(hasItem(DEFAULT_RESIDEFATE.intValue())))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].uname").value(hasItem(DEFAULT_UNAME)))
            .andExpect(jsonPath("$.[*].rentp").value(hasItem(DEFAULT_RENTP)))
            .andExpect(jsonPath("$.[*].protocolrent").value(hasItem(sameNumber(DEFAULT_PROTOCOLRENT))))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].comeinfo").value(hasItem(DEFAULT_COMEINFO)))
            .andExpect(jsonPath("$.[*].goinfo").value(hasItem(DEFAULT_GOINFO)))
            .andExpect(jsonPath("$.[*].phonen").value(hasItem(DEFAULT_PHONEN)))
            .andExpect(jsonPath("$.[*].empn2").value(hasItem(DEFAULT_EMPN_2)))
            .andExpect(jsonPath("$.[*].adhoc").value(hasItem(DEFAULT_ADHOC)))
            .andExpect(jsonPath("$.[*].auditflag").value(hasItem(DEFAULT_AUDITFLAG.intValue())))
            .andExpect(jsonPath("$.[*].groupn").value(hasItem(DEFAULT_GROUPN)))
            .andExpect(jsonPath("$.[*].payment").value(hasItem(DEFAULT_PAYMENT)))
            .andExpect(jsonPath("$.[*].mtype").value(hasItem(DEFAULT_MTYPE)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].flight").value(hasItem(DEFAULT_FLIGHT)))
            .andExpect(jsonPath("$.[*].credit").value(hasItem(sameNumber(DEFAULT_CREDIT))))
            .andExpect(jsonPath("$.[*].talklevel").value(hasItem(DEFAULT_TALKLEVEL)))
            .andExpect(jsonPath("$.[*].lfSign").value(hasItem(DEFAULT_LF_SIGN)))
            .andExpect(jsonPath("$.[*].keynum").value(hasItem(DEFAULT_KEYNUM)))
            .andExpect(jsonPath("$.[*].icNum").value(hasItem(DEFAULT_IC_NUM.intValue())))
            .andExpect(jsonPath("$.[*].bh").value(hasItem(DEFAULT_BH.intValue())))
            .andExpect(jsonPath("$.[*].icOwner").value(hasItem(DEFAULT_IC_OWNER)))
            .andExpect(jsonPath("$.[*].markId").value(hasItem(DEFAULT_MARK_ID)))
            .andExpect(jsonPath("$.[*].gj").value(hasItem(DEFAULT_GJ)))
            .andExpect(jsonPath("$.[*].yfj").value(hasItem(sameNumber(DEFAULT_YFJ))))
            .andExpect(jsonPath("$.[*].hoteldate").value(hasItem(DEFAULT_HOTELDATE.toString())));

        // Check, that the count call also returns 1
        restCheckinBakMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckinBakShouldNotBeFound(String filter) throws Exception {
        restCheckinBakMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckinBakMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCheckinBak() throws Exception {
        // Get the checkinBak
        restCheckinBakMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCheckinBak() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        int databaseSizeBeforeUpdate = checkinBakRepository.findAll().size();

        // Update the checkinBak
        CheckinBak updatedCheckinBak = checkinBakRepository.findById(checkinBak.getId()).get();
        // Disconnect from session so that the updates on updatedCheckinBak are not directly saved in db
        em.detach(updatedCheckinBak);
        updatedCheckinBak
            .guestId(UPDATED_GUEST_ID)
            .account(UPDATED_ACCOUNT)
            .hoteltime(UPDATED_HOTELTIME)
            .indatetime(UPDATED_INDATETIME)
            .residefate(UPDATED_RESIDEFATE)
            .gotime(UPDATED_GOTIME)
            .empn(UPDATED_EMPN)
            .roomn(UPDATED_ROOMN)
            .uname(UPDATED_UNAME)
            .rentp(UPDATED_RENTP)
            .protocolrent(UPDATED_PROTOCOLRENT)
            .remark(UPDATED_REMARK)
            .comeinfo(UPDATED_COMEINFO)
            .goinfo(UPDATED_GOINFO)
            .phonen(UPDATED_PHONEN)
            .empn2(UPDATED_EMPN_2)
            .adhoc(UPDATED_ADHOC)
            .auditflag(UPDATED_AUDITFLAG)
            .groupn(UPDATED_GROUPN)
            .payment(UPDATED_PAYMENT)
            .mtype(UPDATED_MTYPE)
            .memo(UPDATED_MEMO)
            .flight(UPDATED_FLIGHT)
            .credit(UPDATED_CREDIT)
            .talklevel(UPDATED_TALKLEVEL)
            .lfSign(UPDATED_LF_SIGN)
            .keynum(UPDATED_KEYNUM)
            .icNum(UPDATED_IC_NUM)
            .bh(UPDATED_BH)
            .icOwner(UPDATED_IC_OWNER)
            .markId(UPDATED_MARK_ID)
            .gj(UPDATED_GJ)
            .yfj(UPDATED_YFJ)
            .hoteldate(UPDATED_HOTELDATE);
        CheckinBakDTO checkinBakDTO = checkinBakMapper.toDto(updatedCheckinBak);

        restCheckinBakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkinBakDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkinBakDTO))
            )
            .andExpect(status().isOk());

        // Validate the CheckinBak in the database
        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeUpdate);
        CheckinBak testCheckinBak = checkinBakList.get(checkinBakList.size() - 1);
        assertThat(testCheckinBak.getGuestId()).isEqualTo(UPDATED_GUEST_ID);
        assertThat(testCheckinBak.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCheckinBak.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCheckinBak.getIndatetime()).isEqualTo(UPDATED_INDATETIME);
        assertThat(testCheckinBak.getResidefate()).isEqualTo(UPDATED_RESIDEFATE);
        assertThat(testCheckinBak.getGotime()).isEqualTo(UPDATED_GOTIME);
        assertThat(testCheckinBak.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckinBak.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCheckinBak.getUname()).isEqualTo(UPDATED_UNAME);
        assertThat(testCheckinBak.getRentp()).isEqualTo(UPDATED_RENTP);
        assertThat(testCheckinBak.getProtocolrent()).isEqualTo(UPDATED_PROTOCOLRENT);
        assertThat(testCheckinBak.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testCheckinBak.getComeinfo()).isEqualTo(UPDATED_COMEINFO);
        assertThat(testCheckinBak.getGoinfo()).isEqualTo(UPDATED_GOINFO);
        assertThat(testCheckinBak.getPhonen()).isEqualTo(UPDATED_PHONEN);
        assertThat(testCheckinBak.getEmpn2()).isEqualTo(UPDATED_EMPN_2);
        assertThat(testCheckinBak.getAdhoc()).isEqualTo(UPDATED_ADHOC);
        assertThat(testCheckinBak.getAuditflag()).isEqualTo(UPDATED_AUDITFLAG);
        assertThat(testCheckinBak.getGroupn()).isEqualTo(UPDATED_GROUPN);
        assertThat(testCheckinBak.getPayment()).isEqualTo(UPDATED_PAYMENT);
        assertThat(testCheckinBak.getMtype()).isEqualTo(UPDATED_MTYPE);
        assertThat(testCheckinBak.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testCheckinBak.getFlight()).isEqualTo(UPDATED_FLIGHT);
        assertThat(testCheckinBak.getCredit()).isEqualTo(UPDATED_CREDIT);
        assertThat(testCheckinBak.getTalklevel()).isEqualTo(UPDATED_TALKLEVEL);
        assertThat(testCheckinBak.getLfSign()).isEqualTo(UPDATED_LF_SIGN);
        assertThat(testCheckinBak.getKeynum()).isEqualTo(UPDATED_KEYNUM);
        assertThat(testCheckinBak.getIcNum()).isEqualTo(UPDATED_IC_NUM);
        assertThat(testCheckinBak.getBh()).isEqualTo(UPDATED_BH);
        assertThat(testCheckinBak.getIcOwner()).isEqualTo(UPDATED_IC_OWNER);
        assertThat(testCheckinBak.getMarkId()).isEqualTo(UPDATED_MARK_ID);
        assertThat(testCheckinBak.getGj()).isEqualTo(UPDATED_GJ);
        assertThat(testCheckinBak.getYfj()).isEqualTo(UPDATED_YFJ);
        assertThat(testCheckinBak.getHoteldate()).isEqualTo(UPDATED_HOTELDATE);

        // Validate the CheckinBak in Elasticsearch
        verify(mockCheckinBakSearchRepository).save(testCheckinBak);
    }

    @Test
    @Transactional
    void putNonExistingCheckinBak() throws Exception {
        int databaseSizeBeforeUpdate = checkinBakRepository.findAll().size();
        checkinBak.setId(count.incrementAndGet());

        // Create the CheckinBak
        CheckinBakDTO checkinBakDTO = checkinBakMapper.toDto(checkinBak);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckinBakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkinBakDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkinBakDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckinBak in the database
        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinBak in Elasticsearch
        verify(mockCheckinBakSearchRepository, times(0)).save(checkinBak);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckinBak() throws Exception {
        int databaseSizeBeforeUpdate = checkinBakRepository.findAll().size();
        checkinBak.setId(count.incrementAndGet());

        // Create the CheckinBak
        CheckinBakDTO checkinBakDTO = checkinBakMapper.toDto(checkinBak);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinBakMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkinBakDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckinBak in the database
        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinBak in Elasticsearch
        verify(mockCheckinBakSearchRepository, times(0)).save(checkinBak);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckinBak() throws Exception {
        int databaseSizeBeforeUpdate = checkinBakRepository.findAll().size();
        checkinBak.setId(count.incrementAndGet());

        // Create the CheckinBak
        CheckinBakDTO checkinBakDTO = checkinBakMapper.toDto(checkinBak);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinBakMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinBakDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckinBak in the database
        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinBak in Elasticsearch
        verify(mockCheckinBakSearchRepository, times(0)).save(checkinBak);
    }

    @Test
    @Transactional
    void partialUpdateCheckinBakWithPatch() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        int databaseSizeBeforeUpdate = checkinBakRepository.findAll().size();

        // Update the checkinBak using partial update
        CheckinBak partialUpdatedCheckinBak = new CheckinBak();
        partialUpdatedCheckinBak.setId(checkinBak.getId());

        partialUpdatedCheckinBak
            .indatetime(UPDATED_INDATETIME)
            .empn(UPDATED_EMPN)
            .rentp(UPDATED_RENTP)
            .comeinfo(UPDATED_COMEINFO)
            .phonen(UPDATED_PHONEN)
            .flight(UPDATED_FLIGHT)
            .talklevel(UPDATED_TALKLEVEL)
            .lfSign(UPDATED_LF_SIGN)
            .keynum(UPDATED_KEYNUM)
            .bh(UPDATED_BH)
            .yfj(UPDATED_YFJ)
            .hoteldate(UPDATED_HOTELDATE);

        restCheckinBakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckinBak.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckinBak))
            )
            .andExpect(status().isOk());

        // Validate the CheckinBak in the database
        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeUpdate);
        CheckinBak testCheckinBak = checkinBakList.get(checkinBakList.size() - 1);
        assertThat(testCheckinBak.getGuestId()).isEqualTo(DEFAULT_GUEST_ID);
        assertThat(testCheckinBak.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testCheckinBak.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testCheckinBak.getIndatetime()).isEqualTo(UPDATED_INDATETIME);
        assertThat(testCheckinBak.getResidefate()).isEqualTo(DEFAULT_RESIDEFATE);
        assertThat(testCheckinBak.getGotime()).isEqualTo(DEFAULT_GOTIME);
        assertThat(testCheckinBak.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckinBak.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testCheckinBak.getUname()).isEqualTo(DEFAULT_UNAME);
        assertThat(testCheckinBak.getRentp()).isEqualTo(UPDATED_RENTP);
        assertThat(testCheckinBak.getProtocolrent()).isEqualByComparingTo(DEFAULT_PROTOCOLRENT);
        assertThat(testCheckinBak.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testCheckinBak.getComeinfo()).isEqualTo(UPDATED_COMEINFO);
        assertThat(testCheckinBak.getGoinfo()).isEqualTo(DEFAULT_GOINFO);
        assertThat(testCheckinBak.getPhonen()).isEqualTo(UPDATED_PHONEN);
        assertThat(testCheckinBak.getEmpn2()).isEqualTo(DEFAULT_EMPN_2);
        assertThat(testCheckinBak.getAdhoc()).isEqualTo(DEFAULT_ADHOC);
        assertThat(testCheckinBak.getAuditflag()).isEqualTo(DEFAULT_AUDITFLAG);
        assertThat(testCheckinBak.getGroupn()).isEqualTo(DEFAULT_GROUPN);
        assertThat(testCheckinBak.getPayment()).isEqualTo(DEFAULT_PAYMENT);
        assertThat(testCheckinBak.getMtype()).isEqualTo(DEFAULT_MTYPE);
        assertThat(testCheckinBak.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testCheckinBak.getFlight()).isEqualTo(UPDATED_FLIGHT);
        assertThat(testCheckinBak.getCredit()).isEqualByComparingTo(DEFAULT_CREDIT);
        assertThat(testCheckinBak.getTalklevel()).isEqualTo(UPDATED_TALKLEVEL);
        assertThat(testCheckinBak.getLfSign()).isEqualTo(UPDATED_LF_SIGN);
        assertThat(testCheckinBak.getKeynum()).isEqualTo(UPDATED_KEYNUM);
        assertThat(testCheckinBak.getIcNum()).isEqualTo(DEFAULT_IC_NUM);
        assertThat(testCheckinBak.getBh()).isEqualTo(UPDATED_BH);
        assertThat(testCheckinBak.getIcOwner()).isEqualTo(DEFAULT_IC_OWNER);
        assertThat(testCheckinBak.getMarkId()).isEqualTo(DEFAULT_MARK_ID);
        assertThat(testCheckinBak.getGj()).isEqualTo(DEFAULT_GJ);
        assertThat(testCheckinBak.getYfj()).isEqualByComparingTo(UPDATED_YFJ);
        assertThat(testCheckinBak.getHoteldate()).isEqualTo(UPDATED_HOTELDATE);
    }

    @Test
    @Transactional
    void fullUpdateCheckinBakWithPatch() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        int databaseSizeBeforeUpdate = checkinBakRepository.findAll().size();

        // Update the checkinBak using partial update
        CheckinBak partialUpdatedCheckinBak = new CheckinBak();
        partialUpdatedCheckinBak.setId(checkinBak.getId());

        partialUpdatedCheckinBak
            .guestId(UPDATED_GUEST_ID)
            .account(UPDATED_ACCOUNT)
            .hoteltime(UPDATED_HOTELTIME)
            .indatetime(UPDATED_INDATETIME)
            .residefate(UPDATED_RESIDEFATE)
            .gotime(UPDATED_GOTIME)
            .empn(UPDATED_EMPN)
            .roomn(UPDATED_ROOMN)
            .uname(UPDATED_UNAME)
            .rentp(UPDATED_RENTP)
            .protocolrent(UPDATED_PROTOCOLRENT)
            .remark(UPDATED_REMARK)
            .comeinfo(UPDATED_COMEINFO)
            .goinfo(UPDATED_GOINFO)
            .phonen(UPDATED_PHONEN)
            .empn2(UPDATED_EMPN_2)
            .adhoc(UPDATED_ADHOC)
            .auditflag(UPDATED_AUDITFLAG)
            .groupn(UPDATED_GROUPN)
            .payment(UPDATED_PAYMENT)
            .mtype(UPDATED_MTYPE)
            .memo(UPDATED_MEMO)
            .flight(UPDATED_FLIGHT)
            .credit(UPDATED_CREDIT)
            .talklevel(UPDATED_TALKLEVEL)
            .lfSign(UPDATED_LF_SIGN)
            .keynum(UPDATED_KEYNUM)
            .icNum(UPDATED_IC_NUM)
            .bh(UPDATED_BH)
            .icOwner(UPDATED_IC_OWNER)
            .markId(UPDATED_MARK_ID)
            .gj(UPDATED_GJ)
            .yfj(UPDATED_YFJ)
            .hoteldate(UPDATED_HOTELDATE);

        restCheckinBakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckinBak.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckinBak))
            )
            .andExpect(status().isOk());

        // Validate the CheckinBak in the database
        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeUpdate);
        CheckinBak testCheckinBak = checkinBakList.get(checkinBakList.size() - 1);
        assertThat(testCheckinBak.getGuestId()).isEqualTo(UPDATED_GUEST_ID);
        assertThat(testCheckinBak.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCheckinBak.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCheckinBak.getIndatetime()).isEqualTo(UPDATED_INDATETIME);
        assertThat(testCheckinBak.getResidefate()).isEqualTo(UPDATED_RESIDEFATE);
        assertThat(testCheckinBak.getGotime()).isEqualTo(UPDATED_GOTIME);
        assertThat(testCheckinBak.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckinBak.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCheckinBak.getUname()).isEqualTo(UPDATED_UNAME);
        assertThat(testCheckinBak.getRentp()).isEqualTo(UPDATED_RENTP);
        assertThat(testCheckinBak.getProtocolrent()).isEqualByComparingTo(UPDATED_PROTOCOLRENT);
        assertThat(testCheckinBak.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testCheckinBak.getComeinfo()).isEqualTo(UPDATED_COMEINFO);
        assertThat(testCheckinBak.getGoinfo()).isEqualTo(UPDATED_GOINFO);
        assertThat(testCheckinBak.getPhonen()).isEqualTo(UPDATED_PHONEN);
        assertThat(testCheckinBak.getEmpn2()).isEqualTo(UPDATED_EMPN_2);
        assertThat(testCheckinBak.getAdhoc()).isEqualTo(UPDATED_ADHOC);
        assertThat(testCheckinBak.getAuditflag()).isEqualTo(UPDATED_AUDITFLAG);
        assertThat(testCheckinBak.getGroupn()).isEqualTo(UPDATED_GROUPN);
        assertThat(testCheckinBak.getPayment()).isEqualTo(UPDATED_PAYMENT);
        assertThat(testCheckinBak.getMtype()).isEqualTo(UPDATED_MTYPE);
        assertThat(testCheckinBak.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testCheckinBak.getFlight()).isEqualTo(UPDATED_FLIGHT);
        assertThat(testCheckinBak.getCredit()).isEqualByComparingTo(UPDATED_CREDIT);
        assertThat(testCheckinBak.getTalklevel()).isEqualTo(UPDATED_TALKLEVEL);
        assertThat(testCheckinBak.getLfSign()).isEqualTo(UPDATED_LF_SIGN);
        assertThat(testCheckinBak.getKeynum()).isEqualTo(UPDATED_KEYNUM);
        assertThat(testCheckinBak.getIcNum()).isEqualTo(UPDATED_IC_NUM);
        assertThat(testCheckinBak.getBh()).isEqualTo(UPDATED_BH);
        assertThat(testCheckinBak.getIcOwner()).isEqualTo(UPDATED_IC_OWNER);
        assertThat(testCheckinBak.getMarkId()).isEqualTo(UPDATED_MARK_ID);
        assertThat(testCheckinBak.getGj()).isEqualTo(UPDATED_GJ);
        assertThat(testCheckinBak.getYfj()).isEqualByComparingTo(UPDATED_YFJ);
        assertThat(testCheckinBak.getHoteldate()).isEqualTo(UPDATED_HOTELDATE);
    }

    @Test
    @Transactional
    void patchNonExistingCheckinBak() throws Exception {
        int databaseSizeBeforeUpdate = checkinBakRepository.findAll().size();
        checkinBak.setId(count.incrementAndGet());

        // Create the CheckinBak
        CheckinBakDTO checkinBakDTO = checkinBakMapper.toDto(checkinBak);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckinBakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkinBakDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkinBakDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckinBak in the database
        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinBak in Elasticsearch
        verify(mockCheckinBakSearchRepository, times(0)).save(checkinBak);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckinBak() throws Exception {
        int databaseSizeBeforeUpdate = checkinBakRepository.findAll().size();
        checkinBak.setId(count.incrementAndGet());

        // Create the CheckinBak
        CheckinBakDTO checkinBakDTO = checkinBakMapper.toDto(checkinBak);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinBakMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkinBakDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CheckinBak in the database
        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinBak in Elasticsearch
        verify(mockCheckinBakSearchRepository, times(0)).save(checkinBak);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckinBak() throws Exception {
        int databaseSizeBeforeUpdate = checkinBakRepository.findAll().size();
        checkinBak.setId(count.incrementAndGet());

        // Create the CheckinBak
        CheckinBakDTO checkinBakDTO = checkinBakMapper.toDto(checkinBak);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinBakMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(checkinBakDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CheckinBak in the database
        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CheckinBak in Elasticsearch
        verify(mockCheckinBakSearchRepository, times(0)).save(checkinBak);
    }

    @Test
    @Transactional
    void deleteCheckinBak() throws Exception {
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);

        int databaseSizeBeforeDelete = checkinBakRepository.findAll().size();

        // Delete the checkinBak
        restCheckinBakMockMvc
            .perform(delete(ENTITY_API_URL_ID, checkinBak.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CheckinBak> checkinBakList = checkinBakRepository.findAll();
        assertThat(checkinBakList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CheckinBak in Elasticsearch
        verify(mockCheckinBakSearchRepository, times(1)).deleteById(checkinBak.getId());
    }

    @Test
    @Transactional
    void searchCheckinBak() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        checkinBakRepository.saveAndFlush(checkinBak);
        when(mockCheckinBakSearchRepository.search(queryStringQuery("id:" + checkinBak.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(checkinBak), PageRequest.of(0, 1), 1));

        // Search the checkinBak
        restCheckinBakMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + checkinBak.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkinBak.getId().intValue())))
            .andExpect(jsonPath("$.[*].guestId").value(hasItem(DEFAULT_GUEST_ID.intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].indatetime").value(hasItem(DEFAULT_INDATETIME.toString())))
            .andExpect(jsonPath("$.[*].residefate").value(hasItem(DEFAULT_RESIDEFATE.intValue())))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].uname").value(hasItem(DEFAULT_UNAME)))
            .andExpect(jsonPath("$.[*].rentp").value(hasItem(DEFAULT_RENTP)))
            .andExpect(jsonPath("$.[*].protocolrent").value(hasItem(sameNumber(DEFAULT_PROTOCOLRENT))))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].comeinfo").value(hasItem(DEFAULT_COMEINFO)))
            .andExpect(jsonPath("$.[*].goinfo").value(hasItem(DEFAULT_GOINFO)))
            .andExpect(jsonPath("$.[*].phonen").value(hasItem(DEFAULT_PHONEN)))
            .andExpect(jsonPath("$.[*].empn2").value(hasItem(DEFAULT_EMPN_2)))
            .andExpect(jsonPath("$.[*].adhoc").value(hasItem(DEFAULT_ADHOC)))
            .andExpect(jsonPath("$.[*].auditflag").value(hasItem(DEFAULT_AUDITFLAG.intValue())))
            .andExpect(jsonPath("$.[*].groupn").value(hasItem(DEFAULT_GROUPN)))
            .andExpect(jsonPath("$.[*].payment").value(hasItem(DEFAULT_PAYMENT)))
            .andExpect(jsonPath("$.[*].mtype").value(hasItem(DEFAULT_MTYPE)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].flight").value(hasItem(DEFAULT_FLIGHT)))
            .andExpect(jsonPath("$.[*].credit").value(hasItem(sameNumber(DEFAULT_CREDIT))))
            .andExpect(jsonPath("$.[*].talklevel").value(hasItem(DEFAULT_TALKLEVEL)))
            .andExpect(jsonPath("$.[*].lfSign").value(hasItem(DEFAULT_LF_SIGN)))
            .andExpect(jsonPath("$.[*].keynum").value(hasItem(DEFAULT_KEYNUM)))
            .andExpect(jsonPath("$.[*].icNum").value(hasItem(DEFAULT_IC_NUM.intValue())))
            .andExpect(jsonPath("$.[*].bh").value(hasItem(DEFAULT_BH.intValue())))
            .andExpect(jsonPath("$.[*].icOwner").value(hasItem(DEFAULT_IC_OWNER)))
            .andExpect(jsonPath("$.[*].markId").value(hasItem(DEFAULT_MARK_ID)))
            .andExpect(jsonPath("$.[*].gj").value(hasItem(DEFAULT_GJ)))
            .andExpect(jsonPath("$.[*].yfj").value(hasItem(sameNumber(DEFAULT_YFJ))))
            .andExpect(jsonPath("$.[*].hoteldate").value(hasItem(DEFAULT_HOTELDATE.toString())));
    }
}
