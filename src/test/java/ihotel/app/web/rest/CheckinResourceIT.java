package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Checkin;
import ihotel.app.repository.CheckinRepository;
import ihotel.app.repository.search.CheckinSearchRepository;
import ihotel.app.service.criteria.CheckinCriteria;
import ihotel.app.service.dto.CheckinDTO;
import ihotel.app.service.mapper.CheckinMapper;
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
 * Integration tests for the {@link CheckinResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CheckinResourceIT {

    private static final Long DEFAULT_BKID = 1L;
    private static final Long UPDATED_BKID = 2L;
    private static final Long SMALLER_BKID = 1L - 1L;

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

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final String DEFAULT_LF_SIGN = "A";
    private static final String UPDATED_LF_SIGN = "B";

    private static final String DEFAULT_KEYNUM = "AAA";
    private static final String UPDATED_KEYNUM = "BBB";

    private static final String DEFAULT_HYKH = "AAAAAAAAAA";
    private static final String UPDATED_HYKH = "BBBBBBBBBB";

    private static final String DEFAULT_BM = "AAAAAAAAAA";
    private static final String UPDATED_BM = "BBBBBBBBBB";

    private static final Long DEFAULT_FLAG = 1L;
    private static final Long UPDATED_FLAG = 2L;
    private static final Long SMALLER_FLAG = 1L - 1L;

    private static final Instant DEFAULT_JXTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JXTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_JXFLAG = 1L;
    private static final Long UPDATED_JXFLAG = 2L;
    private static final Long SMALLER_JXFLAG = 1L - 1L;

    private static final Long DEFAULT_CHECKF = 1L;
    private static final Long UPDATED_CHECKF = 2L;
    private static final Long SMALLER_CHECKF = 1L - 1L;

    private static final String DEFAULT_GUESTNAME = "AAAAAAAAAA";
    private static final String UPDATED_GUESTNAME = "BBBBBBBBBB";

    private static final Long DEFAULT_FGF = 1L;
    private static final Long UPDATED_FGF = 2L;
    private static final Long SMALLER_FGF = 1L - 1L;

    private static final String DEFAULT_FGXX = "AAAAAAAAAA";
    private static final String UPDATED_FGXX = "BBBBBBBBBB";

    private static final Long DEFAULT_HOUR_SIGN = 1L;
    private static final Long UPDATED_HOUR_SIGN = 2L;
    private static final Long SMALLER_HOUR_SIGN = 1L - 1L;

    private static final String DEFAULT_XSY = "AAAAAAAAAA";
    private static final String UPDATED_XSY = "BBBBBBBBBB";

    private static final Long DEFAULT_RZSIGN = 1L;
    private static final Long UPDATED_RZSIGN = 2L;
    private static final Long SMALLER_RZSIGN = 1L - 1L;

    private static final Long DEFAULT_JF = 1L;
    private static final Long UPDATED_JF = 2L;
    private static final Long SMALLER_JF = 1L - 1L;

    private static final String DEFAULT_GNAME = "AAAAAAAAAA";
    private static final String UPDATED_GNAME = "BBBBBBBBBB";

    private static final Long DEFAULT_ZCSIGN = 1L;
    private static final Long UPDATED_ZCSIGN = 2L;
    private static final Long SMALLER_ZCSIGN = 1L - 1L;

    private static final Long DEFAULT_CQSL = 1L;
    private static final Long UPDATED_CQSL = 2L;
    private static final Long SMALLER_CQSL = 1L - 1L;

    private static final Long DEFAULT_SFJF = 1L;
    private static final Long UPDATED_SFJF = 2L;
    private static final Long SMALLER_SFJF = 1L - 1L;

    private static final String DEFAULT_YWLY = "AAAAAAAAAA";
    private static final String UPDATED_YWLY = "BBBBBBBBBB";

    private static final String DEFAULT_FK = "AA";
    private static final String UPDATED_FK = "BB";

    private static final Instant DEFAULT_FKRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FKRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_BC = "AAAAAAAAAA";
    private static final String UPDATED_BC = "BBBBBBBBBB";

    private static final String DEFAULT_JXREMARK = "AAAAAAAAAA";
    private static final String UPDATED_JXREMARK = "BBBBBBBBBB";

    private static final Double DEFAULT_TXID = 1D;
    private static final Double UPDATED_TXID = 2D;
    private static final Double SMALLER_TXID = 1D - 1D;

    private static final String DEFAULT_CFR = "AAAAAAAAAA";
    private static final String UPDATED_CFR = "BBBBBBBBBB";

    private static final String DEFAULT_FJBM = "AA";
    private static final String UPDATED_FJBM = "BB";

    private static final String DEFAULT_DJLX = "AAAAAAAAAA";
    private static final String UPDATED_DJLX = "BBBBBBBBBB";

    private static final String DEFAULT_WLDDH = "AAAAAAAAAA";
    private static final String UPDATED_WLDDH = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_FKSL = new BigDecimal(1);
    private static final BigDecimal UPDATED_FKSL = new BigDecimal(2);
    private static final BigDecimal SMALLER_FKSL = new BigDecimal(1 - 1);

    private static final String DEFAULT_DQTX = "A";
    private static final String UPDATED_DQTX = "B";

    private static final String ENTITY_API_URL = "/api/checkins";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/checkins";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CheckinRepository checkinRepository;

    @Autowired
    private CheckinMapper checkinMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CheckinSearchRepositoryMockConfiguration
     */
    @Autowired
    private CheckinSearchRepository mockCheckinSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCheckinMockMvc;

    private Checkin checkin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Checkin createEntity(EntityManager em) {
        Checkin checkin = new Checkin()
            .bkid(DEFAULT_BKID)
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
            .phonen(DEFAULT_PHONEN)
            .empn2(DEFAULT_EMPN_2)
            .adhoc(DEFAULT_ADHOC)
            .auditflag(DEFAULT_AUDITFLAG)
            .groupn(DEFAULT_GROUPN)
            .memo(DEFAULT_MEMO)
            .lfSign(DEFAULT_LF_SIGN)
            .keynum(DEFAULT_KEYNUM)
            .hykh(DEFAULT_HYKH)
            .bm(DEFAULT_BM)
            .flag(DEFAULT_FLAG)
            .jxtime(DEFAULT_JXTIME)
            .jxflag(DEFAULT_JXFLAG)
            .checkf(DEFAULT_CHECKF)
            .guestname(DEFAULT_GUESTNAME)
            .fgf(DEFAULT_FGF)
            .fgxx(DEFAULT_FGXX)
            .hourSign(DEFAULT_HOUR_SIGN)
            .xsy(DEFAULT_XSY)
            .rzsign(DEFAULT_RZSIGN)
            .jf(DEFAULT_JF)
            .gname(DEFAULT_GNAME)
            .zcsign(DEFAULT_ZCSIGN)
            .cqsl(DEFAULT_CQSL)
            .sfjf(DEFAULT_SFJF)
            .ywly(DEFAULT_YWLY)
            .fk(DEFAULT_FK)
            .fkrq(DEFAULT_FKRQ)
            .bc(DEFAULT_BC)
            .jxremark(DEFAULT_JXREMARK)
            .txid(DEFAULT_TXID)
            .cfr(DEFAULT_CFR)
            .fjbm(DEFAULT_FJBM)
            .djlx(DEFAULT_DJLX)
            .wlddh(DEFAULT_WLDDH)
            .fksl(DEFAULT_FKSL)
            .dqtx(DEFAULT_DQTX);
        return checkin;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Checkin createUpdatedEntity(EntityManager em) {
        Checkin checkin = new Checkin()
            .bkid(UPDATED_BKID)
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
            .phonen(UPDATED_PHONEN)
            .empn2(UPDATED_EMPN_2)
            .adhoc(UPDATED_ADHOC)
            .auditflag(UPDATED_AUDITFLAG)
            .groupn(UPDATED_GROUPN)
            .memo(UPDATED_MEMO)
            .lfSign(UPDATED_LF_SIGN)
            .keynum(UPDATED_KEYNUM)
            .hykh(UPDATED_HYKH)
            .bm(UPDATED_BM)
            .flag(UPDATED_FLAG)
            .jxtime(UPDATED_JXTIME)
            .jxflag(UPDATED_JXFLAG)
            .checkf(UPDATED_CHECKF)
            .guestname(UPDATED_GUESTNAME)
            .fgf(UPDATED_FGF)
            .fgxx(UPDATED_FGXX)
            .hourSign(UPDATED_HOUR_SIGN)
            .xsy(UPDATED_XSY)
            .rzsign(UPDATED_RZSIGN)
            .jf(UPDATED_JF)
            .gname(UPDATED_GNAME)
            .zcsign(UPDATED_ZCSIGN)
            .cqsl(UPDATED_CQSL)
            .sfjf(UPDATED_SFJF)
            .ywly(UPDATED_YWLY)
            .fk(UPDATED_FK)
            .fkrq(UPDATED_FKRQ)
            .bc(UPDATED_BC)
            .jxremark(UPDATED_JXREMARK)
            .txid(UPDATED_TXID)
            .cfr(UPDATED_CFR)
            .fjbm(UPDATED_FJBM)
            .djlx(UPDATED_DJLX)
            .wlddh(UPDATED_WLDDH)
            .fksl(UPDATED_FKSL)
            .dqtx(UPDATED_DQTX);
        return checkin;
    }

    @BeforeEach
    public void initTest() {
        checkin = createEntity(em);
    }

    @Test
    @Transactional
    void createCheckin() throws Exception {
        int databaseSizeBeforeCreate = checkinRepository.findAll().size();
        // Create the Checkin
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);
        restCheckinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinDTO)))
            .andExpect(status().isCreated());

        // Validate the Checkin in the database
        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeCreate + 1);
        Checkin testCheckin = checkinList.get(checkinList.size() - 1);
        assertThat(testCheckin.getBkid()).isEqualTo(DEFAULT_BKID);
        assertThat(testCheckin.getGuestId()).isEqualTo(DEFAULT_GUEST_ID);
        assertThat(testCheckin.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testCheckin.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testCheckin.getIndatetime()).isEqualTo(DEFAULT_INDATETIME);
        assertThat(testCheckin.getResidefate()).isEqualTo(DEFAULT_RESIDEFATE);
        assertThat(testCheckin.getGotime()).isEqualTo(DEFAULT_GOTIME);
        assertThat(testCheckin.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testCheckin.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testCheckin.getUname()).isEqualTo(DEFAULT_UNAME);
        assertThat(testCheckin.getRentp()).isEqualTo(DEFAULT_RENTP);
        assertThat(testCheckin.getProtocolrent()).isEqualByComparingTo(DEFAULT_PROTOCOLRENT);
        assertThat(testCheckin.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testCheckin.getPhonen()).isEqualTo(DEFAULT_PHONEN);
        assertThat(testCheckin.getEmpn2()).isEqualTo(DEFAULT_EMPN_2);
        assertThat(testCheckin.getAdhoc()).isEqualTo(DEFAULT_ADHOC);
        assertThat(testCheckin.getAuditflag()).isEqualTo(DEFAULT_AUDITFLAG);
        assertThat(testCheckin.getGroupn()).isEqualTo(DEFAULT_GROUPN);
        assertThat(testCheckin.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testCheckin.getLfSign()).isEqualTo(DEFAULT_LF_SIGN);
        assertThat(testCheckin.getKeynum()).isEqualTo(DEFAULT_KEYNUM);
        assertThat(testCheckin.getHykh()).isEqualTo(DEFAULT_HYKH);
        assertThat(testCheckin.getBm()).isEqualTo(DEFAULT_BM);
        assertThat(testCheckin.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testCheckin.getJxtime()).isEqualTo(DEFAULT_JXTIME);
        assertThat(testCheckin.getJxflag()).isEqualTo(DEFAULT_JXFLAG);
        assertThat(testCheckin.getCheckf()).isEqualTo(DEFAULT_CHECKF);
        assertThat(testCheckin.getGuestname()).isEqualTo(DEFAULT_GUESTNAME);
        assertThat(testCheckin.getFgf()).isEqualTo(DEFAULT_FGF);
        assertThat(testCheckin.getFgxx()).isEqualTo(DEFAULT_FGXX);
        assertThat(testCheckin.getHourSign()).isEqualTo(DEFAULT_HOUR_SIGN);
        assertThat(testCheckin.getXsy()).isEqualTo(DEFAULT_XSY);
        assertThat(testCheckin.getRzsign()).isEqualTo(DEFAULT_RZSIGN);
        assertThat(testCheckin.getJf()).isEqualTo(DEFAULT_JF);
        assertThat(testCheckin.getGname()).isEqualTo(DEFAULT_GNAME);
        assertThat(testCheckin.getZcsign()).isEqualTo(DEFAULT_ZCSIGN);
        assertThat(testCheckin.getCqsl()).isEqualTo(DEFAULT_CQSL);
        assertThat(testCheckin.getSfjf()).isEqualTo(DEFAULT_SFJF);
        assertThat(testCheckin.getYwly()).isEqualTo(DEFAULT_YWLY);
        assertThat(testCheckin.getFk()).isEqualTo(DEFAULT_FK);
        assertThat(testCheckin.getFkrq()).isEqualTo(DEFAULT_FKRQ);
        assertThat(testCheckin.getBc()).isEqualTo(DEFAULT_BC);
        assertThat(testCheckin.getJxremark()).isEqualTo(DEFAULT_JXREMARK);
        assertThat(testCheckin.getTxid()).isEqualTo(DEFAULT_TXID);
        assertThat(testCheckin.getCfr()).isEqualTo(DEFAULT_CFR);
        assertThat(testCheckin.getFjbm()).isEqualTo(DEFAULT_FJBM);
        assertThat(testCheckin.getDjlx()).isEqualTo(DEFAULT_DJLX);
        assertThat(testCheckin.getWlddh()).isEqualTo(DEFAULT_WLDDH);
        assertThat(testCheckin.getFksl()).isEqualByComparingTo(DEFAULT_FKSL);
        assertThat(testCheckin.getDqtx()).isEqualTo(DEFAULT_DQTX);

        // Validate the Checkin in Elasticsearch
        verify(mockCheckinSearchRepository, times(1)).save(testCheckin);
    }

    @Test
    @Transactional
    void createCheckinWithExistingId() throws Exception {
        // Create the Checkin with an existing ID
        checkin.setId(1L);
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);

        int databaseSizeBeforeCreate = checkinRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Checkin in the database
        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeCreate);

        // Validate the Checkin in Elasticsearch
        verify(mockCheckinSearchRepository, times(0)).save(checkin);
    }

    @Test
    @Transactional
    void checkBkidIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkinRepository.findAll().size();
        // set the field null
        checkin.setBkid(null);

        // Create the Checkin, which fails.
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);

        restCheckinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinDTO)))
            .andExpect(status().isBadRequest());

        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGuestIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkinRepository.findAll().size();
        // set the field null
        checkin.setGuestId(null);

        // Create the Checkin, which fails.
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);

        restCheckinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinDTO)))
            .andExpect(status().isBadRequest());

        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkinRepository.findAll().size();
        // set the field null
        checkin.setAccount(null);

        // Create the Checkin, which fails.
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);

        restCheckinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinDTO)))
            .andExpect(status().isBadRequest());

        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRoomnIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkinRepository.findAll().size();
        // set the field null
        checkin.setRoomn(null);

        // Create the Checkin, which fails.
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);

        restCheckinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinDTO)))
            .andExpect(status().isBadRequest());

        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkRentpIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkinRepository.findAll().size();
        // set the field null
        checkin.setRentp(null);

        // Create the Checkin, which fails.
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);

        restCheckinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinDTO)))
            .andExpect(status().isBadRequest());

        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGuestnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkinRepository.findAll().size();
        // set the field null
        checkin.setGuestname(null);

        // Create the Checkin, which fails.
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);

        restCheckinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinDTO)))
            .andExpect(status().isBadRequest());

        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFgfIsRequired() throws Exception {
        int databaseSizeBeforeTest = checkinRepository.findAll().size();
        // set the field null
        checkin.setFgf(null);

        // Create the Checkin, which fails.
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);

        restCheckinMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinDTO)))
            .andExpect(status().isBadRequest());

        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCheckins() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList
        restCheckinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkin.getId().intValue())))
            .andExpect(jsonPath("$.[*].bkid").value(hasItem(DEFAULT_BKID.intValue())))
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
            .andExpect(jsonPath("$.[*].phonen").value(hasItem(DEFAULT_PHONEN)))
            .andExpect(jsonPath("$.[*].empn2").value(hasItem(DEFAULT_EMPN_2)))
            .andExpect(jsonPath("$.[*].adhoc").value(hasItem(DEFAULT_ADHOC)))
            .andExpect(jsonPath("$.[*].auditflag").value(hasItem(DEFAULT_AUDITFLAG.intValue())))
            .andExpect(jsonPath("$.[*].groupn").value(hasItem(DEFAULT_GROUPN)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].lfSign").value(hasItem(DEFAULT_LF_SIGN)))
            .andExpect(jsonPath("$.[*].keynum").value(hasItem(DEFAULT_KEYNUM)))
            .andExpect(jsonPath("$.[*].hykh").value(hasItem(DEFAULT_HYKH)))
            .andExpect(jsonPath("$.[*].bm").value(hasItem(DEFAULT_BM)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].jxtime").value(hasItem(DEFAULT_JXTIME.toString())))
            .andExpect(jsonPath("$.[*].jxflag").value(hasItem(DEFAULT_JXFLAG.intValue())))
            .andExpect(jsonPath("$.[*].checkf").value(hasItem(DEFAULT_CHECKF.intValue())))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].fgf").value(hasItem(DEFAULT_FGF.intValue())))
            .andExpect(jsonPath("$.[*].fgxx").value(hasItem(DEFAULT_FGXX)))
            .andExpect(jsonPath("$.[*].hourSign").value(hasItem(DEFAULT_HOUR_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].xsy").value(hasItem(DEFAULT_XSY)))
            .andExpect(jsonPath("$.[*].rzsign").value(hasItem(DEFAULT_RZSIGN.intValue())))
            .andExpect(jsonPath("$.[*].jf").value(hasItem(DEFAULT_JF.intValue())))
            .andExpect(jsonPath("$.[*].gname").value(hasItem(DEFAULT_GNAME)))
            .andExpect(jsonPath("$.[*].zcsign").value(hasItem(DEFAULT_ZCSIGN.intValue())))
            .andExpect(jsonPath("$.[*].cqsl").value(hasItem(DEFAULT_CQSL.intValue())))
            .andExpect(jsonPath("$.[*].sfjf").value(hasItem(DEFAULT_SFJF.intValue())))
            .andExpect(jsonPath("$.[*].ywly").value(hasItem(DEFAULT_YWLY)))
            .andExpect(jsonPath("$.[*].fk").value(hasItem(DEFAULT_FK)))
            .andExpect(jsonPath("$.[*].fkrq").value(hasItem(DEFAULT_FKRQ.toString())))
            .andExpect(jsonPath("$.[*].bc").value(hasItem(DEFAULT_BC)))
            .andExpect(jsonPath("$.[*].jxremark").value(hasItem(DEFAULT_JXREMARK)))
            .andExpect(jsonPath("$.[*].txid").value(hasItem(DEFAULT_TXID.doubleValue())))
            .andExpect(jsonPath("$.[*].cfr").value(hasItem(DEFAULT_CFR)))
            .andExpect(jsonPath("$.[*].fjbm").value(hasItem(DEFAULT_FJBM)))
            .andExpect(jsonPath("$.[*].djlx").value(hasItem(DEFAULT_DJLX)))
            .andExpect(jsonPath("$.[*].wlddh").value(hasItem(DEFAULT_WLDDH)))
            .andExpect(jsonPath("$.[*].fksl").value(hasItem(sameNumber(DEFAULT_FKSL))))
            .andExpect(jsonPath("$.[*].dqtx").value(hasItem(DEFAULT_DQTX)));
    }

    @Test
    @Transactional
    void getCheckin() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get the checkin
        restCheckinMockMvc
            .perform(get(ENTITY_API_URL_ID, checkin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(checkin.getId().intValue()))
            .andExpect(jsonPath("$.bkid").value(DEFAULT_BKID.intValue()))
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
            .andExpect(jsonPath("$.phonen").value(DEFAULT_PHONEN))
            .andExpect(jsonPath("$.empn2").value(DEFAULT_EMPN_2))
            .andExpect(jsonPath("$.adhoc").value(DEFAULT_ADHOC))
            .andExpect(jsonPath("$.auditflag").value(DEFAULT_AUDITFLAG.intValue()))
            .andExpect(jsonPath("$.groupn").value(DEFAULT_GROUPN))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO))
            .andExpect(jsonPath("$.lfSign").value(DEFAULT_LF_SIGN))
            .andExpect(jsonPath("$.keynum").value(DEFAULT_KEYNUM))
            .andExpect(jsonPath("$.hykh").value(DEFAULT_HYKH))
            .andExpect(jsonPath("$.bm").value(DEFAULT_BM))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG.intValue()))
            .andExpect(jsonPath("$.jxtime").value(DEFAULT_JXTIME.toString()))
            .andExpect(jsonPath("$.jxflag").value(DEFAULT_JXFLAG.intValue()))
            .andExpect(jsonPath("$.checkf").value(DEFAULT_CHECKF.intValue()))
            .andExpect(jsonPath("$.guestname").value(DEFAULT_GUESTNAME))
            .andExpect(jsonPath("$.fgf").value(DEFAULT_FGF.intValue()))
            .andExpect(jsonPath("$.fgxx").value(DEFAULT_FGXX))
            .andExpect(jsonPath("$.hourSign").value(DEFAULT_HOUR_SIGN.intValue()))
            .andExpect(jsonPath("$.xsy").value(DEFAULT_XSY))
            .andExpect(jsonPath("$.rzsign").value(DEFAULT_RZSIGN.intValue()))
            .andExpect(jsonPath("$.jf").value(DEFAULT_JF.intValue()))
            .andExpect(jsonPath("$.gname").value(DEFAULT_GNAME))
            .andExpect(jsonPath("$.zcsign").value(DEFAULT_ZCSIGN.intValue()))
            .andExpect(jsonPath("$.cqsl").value(DEFAULT_CQSL.intValue()))
            .andExpect(jsonPath("$.sfjf").value(DEFAULT_SFJF.intValue()))
            .andExpect(jsonPath("$.ywly").value(DEFAULT_YWLY))
            .andExpect(jsonPath("$.fk").value(DEFAULT_FK))
            .andExpect(jsonPath("$.fkrq").value(DEFAULT_FKRQ.toString()))
            .andExpect(jsonPath("$.bc").value(DEFAULT_BC))
            .andExpect(jsonPath("$.jxremark").value(DEFAULT_JXREMARK))
            .andExpect(jsonPath("$.txid").value(DEFAULT_TXID.doubleValue()))
            .andExpect(jsonPath("$.cfr").value(DEFAULT_CFR))
            .andExpect(jsonPath("$.fjbm").value(DEFAULT_FJBM))
            .andExpect(jsonPath("$.djlx").value(DEFAULT_DJLX))
            .andExpect(jsonPath("$.wlddh").value(DEFAULT_WLDDH))
            .andExpect(jsonPath("$.fksl").value(sameNumber(DEFAULT_FKSL)))
            .andExpect(jsonPath("$.dqtx").value(DEFAULT_DQTX));
    }

    @Test
    @Transactional
    void getCheckinsByIdFiltering() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        Long id = checkin.getId();

        defaultCheckinShouldBeFound("id.equals=" + id);
        defaultCheckinShouldNotBeFound("id.notEquals=" + id);

        defaultCheckinShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCheckinShouldNotBeFound("id.greaterThan=" + id);

        defaultCheckinShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCheckinShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCheckinsByBkidIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bkid equals to DEFAULT_BKID
        defaultCheckinShouldBeFound("bkid.equals=" + DEFAULT_BKID);

        // Get all the checkinList where bkid equals to UPDATED_BKID
        defaultCheckinShouldNotBeFound("bkid.equals=" + UPDATED_BKID);
    }

    @Test
    @Transactional
    void getAllCheckinsByBkidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bkid not equals to DEFAULT_BKID
        defaultCheckinShouldNotBeFound("bkid.notEquals=" + DEFAULT_BKID);

        // Get all the checkinList where bkid not equals to UPDATED_BKID
        defaultCheckinShouldBeFound("bkid.notEquals=" + UPDATED_BKID);
    }

    @Test
    @Transactional
    void getAllCheckinsByBkidIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bkid in DEFAULT_BKID or UPDATED_BKID
        defaultCheckinShouldBeFound("bkid.in=" + DEFAULT_BKID + "," + UPDATED_BKID);

        // Get all the checkinList where bkid equals to UPDATED_BKID
        defaultCheckinShouldNotBeFound("bkid.in=" + UPDATED_BKID);
    }

    @Test
    @Transactional
    void getAllCheckinsByBkidIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bkid is not null
        defaultCheckinShouldBeFound("bkid.specified=true");

        // Get all the checkinList where bkid is null
        defaultCheckinShouldNotBeFound("bkid.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByBkidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bkid is greater than or equal to DEFAULT_BKID
        defaultCheckinShouldBeFound("bkid.greaterThanOrEqual=" + DEFAULT_BKID);

        // Get all the checkinList where bkid is greater than or equal to UPDATED_BKID
        defaultCheckinShouldNotBeFound("bkid.greaterThanOrEqual=" + UPDATED_BKID);
    }

    @Test
    @Transactional
    void getAllCheckinsByBkidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bkid is less than or equal to DEFAULT_BKID
        defaultCheckinShouldBeFound("bkid.lessThanOrEqual=" + DEFAULT_BKID);

        // Get all the checkinList where bkid is less than or equal to SMALLER_BKID
        defaultCheckinShouldNotBeFound("bkid.lessThanOrEqual=" + SMALLER_BKID);
    }

    @Test
    @Transactional
    void getAllCheckinsByBkidIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bkid is less than DEFAULT_BKID
        defaultCheckinShouldNotBeFound("bkid.lessThan=" + DEFAULT_BKID);

        // Get all the checkinList where bkid is less than UPDATED_BKID
        defaultCheckinShouldBeFound("bkid.lessThan=" + UPDATED_BKID);
    }

    @Test
    @Transactional
    void getAllCheckinsByBkidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bkid is greater than DEFAULT_BKID
        defaultCheckinShouldNotBeFound("bkid.greaterThan=" + DEFAULT_BKID);

        // Get all the checkinList where bkid is greater than SMALLER_BKID
        defaultCheckinShouldBeFound("bkid.greaterThan=" + SMALLER_BKID);
    }

    @Test
    @Transactional
    void getAllCheckinsByGuestIdIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where guestId equals to DEFAULT_GUEST_ID
        defaultCheckinShouldBeFound("guestId.equals=" + DEFAULT_GUEST_ID);

        // Get all the checkinList where guestId equals to UPDATED_GUEST_ID
        defaultCheckinShouldNotBeFound("guestId.equals=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinsByGuestIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where guestId not equals to DEFAULT_GUEST_ID
        defaultCheckinShouldNotBeFound("guestId.notEquals=" + DEFAULT_GUEST_ID);

        // Get all the checkinList where guestId not equals to UPDATED_GUEST_ID
        defaultCheckinShouldBeFound("guestId.notEquals=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinsByGuestIdIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where guestId in DEFAULT_GUEST_ID or UPDATED_GUEST_ID
        defaultCheckinShouldBeFound("guestId.in=" + DEFAULT_GUEST_ID + "," + UPDATED_GUEST_ID);

        // Get all the checkinList where guestId equals to UPDATED_GUEST_ID
        defaultCheckinShouldNotBeFound("guestId.in=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinsByGuestIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where guestId is not null
        defaultCheckinShouldBeFound("guestId.specified=true");

        // Get all the checkinList where guestId is null
        defaultCheckinShouldNotBeFound("guestId.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByGuestIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where guestId is greater than or equal to DEFAULT_GUEST_ID
        defaultCheckinShouldBeFound("guestId.greaterThanOrEqual=" + DEFAULT_GUEST_ID);

        // Get all the checkinList where guestId is greater than or equal to UPDATED_GUEST_ID
        defaultCheckinShouldNotBeFound("guestId.greaterThanOrEqual=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinsByGuestIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where guestId is less than or equal to DEFAULT_GUEST_ID
        defaultCheckinShouldBeFound("guestId.lessThanOrEqual=" + DEFAULT_GUEST_ID);

        // Get all the checkinList where guestId is less than or equal to SMALLER_GUEST_ID
        defaultCheckinShouldNotBeFound("guestId.lessThanOrEqual=" + SMALLER_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinsByGuestIdIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where guestId is less than DEFAULT_GUEST_ID
        defaultCheckinShouldNotBeFound("guestId.lessThan=" + DEFAULT_GUEST_ID);

        // Get all the checkinList where guestId is less than UPDATED_GUEST_ID
        defaultCheckinShouldBeFound("guestId.lessThan=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinsByGuestIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where guestId is greater than DEFAULT_GUEST_ID
        defaultCheckinShouldNotBeFound("guestId.greaterThan=" + DEFAULT_GUEST_ID);

        // Get all the checkinList where guestId is greater than SMALLER_GUEST_ID
        defaultCheckinShouldBeFound("guestId.greaterThan=" + SMALLER_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllCheckinsByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where account equals to DEFAULT_ACCOUNT
        defaultCheckinShouldBeFound("account.equals=" + DEFAULT_ACCOUNT);

        // Get all the checkinList where account equals to UPDATED_ACCOUNT
        defaultCheckinShouldNotBeFound("account.equals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinsByAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where account not equals to DEFAULT_ACCOUNT
        defaultCheckinShouldNotBeFound("account.notEquals=" + DEFAULT_ACCOUNT);

        // Get all the checkinList where account not equals to UPDATED_ACCOUNT
        defaultCheckinShouldBeFound("account.notEquals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinsByAccountIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where account in DEFAULT_ACCOUNT or UPDATED_ACCOUNT
        defaultCheckinShouldBeFound("account.in=" + DEFAULT_ACCOUNT + "," + UPDATED_ACCOUNT);

        // Get all the checkinList where account equals to UPDATED_ACCOUNT
        defaultCheckinShouldNotBeFound("account.in=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinsByAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where account is not null
        defaultCheckinShouldBeFound("account.specified=true");

        // Get all the checkinList where account is null
        defaultCheckinShouldNotBeFound("account.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByAccountContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where account contains DEFAULT_ACCOUNT
        defaultCheckinShouldBeFound("account.contains=" + DEFAULT_ACCOUNT);

        // Get all the checkinList where account contains UPDATED_ACCOUNT
        defaultCheckinShouldNotBeFound("account.contains=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinsByAccountNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where account does not contain DEFAULT_ACCOUNT
        defaultCheckinShouldNotBeFound("account.doesNotContain=" + DEFAULT_ACCOUNT);

        // Get all the checkinList where account does not contain UPDATED_ACCOUNT
        defaultCheckinShouldBeFound("account.doesNotContain=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllCheckinsByHoteltimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hoteltime equals to DEFAULT_HOTELTIME
        defaultCheckinShouldBeFound("hoteltime.equals=" + DEFAULT_HOTELTIME);

        // Get all the checkinList where hoteltime equals to UPDATED_HOTELTIME
        defaultCheckinShouldNotBeFound("hoteltime.equals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckinsByHoteltimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hoteltime not equals to DEFAULT_HOTELTIME
        defaultCheckinShouldNotBeFound("hoteltime.notEquals=" + DEFAULT_HOTELTIME);

        // Get all the checkinList where hoteltime not equals to UPDATED_HOTELTIME
        defaultCheckinShouldBeFound("hoteltime.notEquals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckinsByHoteltimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hoteltime in DEFAULT_HOTELTIME or UPDATED_HOTELTIME
        defaultCheckinShouldBeFound("hoteltime.in=" + DEFAULT_HOTELTIME + "," + UPDATED_HOTELTIME);

        // Get all the checkinList where hoteltime equals to UPDATED_HOTELTIME
        defaultCheckinShouldNotBeFound("hoteltime.in=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllCheckinsByHoteltimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hoteltime is not null
        defaultCheckinShouldBeFound("hoteltime.specified=true");

        // Get all the checkinList where hoteltime is null
        defaultCheckinShouldNotBeFound("hoteltime.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByIndatetimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where indatetime equals to DEFAULT_INDATETIME
        defaultCheckinShouldBeFound("indatetime.equals=" + DEFAULT_INDATETIME);

        // Get all the checkinList where indatetime equals to UPDATED_INDATETIME
        defaultCheckinShouldNotBeFound("indatetime.equals=" + UPDATED_INDATETIME);
    }

    @Test
    @Transactional
    void getAllCheckinsByIndatetimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where indatetime not equals to DEFAULT_INDATETIME
        defaultCheckinShouldNotBeFound("indatetime.notEquals=" + DEFAULT_INDATETIME);

        // Get all the checkinList where indatetime not equals to UPDATED_INDATETIME
        defaultCheckinShouldBeFound("indatetime.notEquals=" + UPDATED_INDATETIME);
    }

    @Test
    @Transactional
    void getAllCheckinsByIndatetimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where indatetime in DEFAULT_INDATETIME or UPDATED_INDATETIME
        defaultCheckinShouldBeFound("indatetime.in=" + DEFAULT_INDATETIME + "," + UPDATED_INDATETIME);

        // Get all the checkinList where indatetime equals to UPDATED_INDATETIME
        defaultCheckinShouldNotBeFound("indatetime.in=" + UPDATED_INDATETIME);
    }

    @Test
    @Transactional
    void getAllCheckinsByIndatetimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where indatetime is not null
        defaultCheckinShouldBeFound("indatetime.specified=true");

        // Get all the checkinList where indatetime is null
        defaultCheckinShouldNotBeFound("indatetime.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByResidefateIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where residefate equals to DEFAULT_RESIDEFATE
        defaultCheckinShouldBeFound("residefate.equals=" + DEFAULT_RESIDEFATE);

        // Get all the checkinList where residefate equals to UPDATED_RESIDEFATE
        defaultCheckinShouldNotBeFound("residefate.equals=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinsByResidefateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where residefate not equals to DEFAULT_RESIDEFATE
        defaultCheckinShouldNotBeFound("residefate.notEquals=" + DEFAULT_RESIDEFATE);

        // Get all the checkinList where residefate not equals to UPDATED_RESIDEFATE
        defaultCheckinShouldBeFound("residefate.notEquals=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinsByResidefateIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where residefate in DEFAULT_RESIDEFATE or UPDATED_RESIDEFATE
        defaultCheckinShouldBeFound("residefate.in=" + DEFAULT_RESIDEFATE + "," + UPDATED_RESIDEFATE);

        // Get all the checkinList where residefate equals to UPDATED_RESIDEFATE
        defaultCheckinShouldNotBeFound("residefate.in=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinsByResidefateIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where residefate is not null
        defaultCheckinShouldBeFound("residefate.specified=true");

        // Get all the checkinList where residefate is null
        defaultCheckinShouldNotBeFound("residefate.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByResidefateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where residefate is greater than or equal to DEFAULT_RESIDEFATE
        defaultCheckinShouldBeFound("residefate.greaterThanOrEqual=" + DEFAULT_RESIDEFATE);

        // Get all the checkinList where residefate is greater than or equal to UPDATED_RESIDEFATE
        defaultCheckinShouldNotBeFound("residefate.greaterThanOrEqual=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinsByResidefateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where residefate is less than or equal to DEFAULT_RESIDEFATE
        defaultCheckinShouldBeFound("residefate.lessThanOrEqual=" + DEFAULT_RESIDEFATE);

        // Get all the checkinList where residefate is less than or equal to SMALLER_RESIDEFATE
        defaultCheckinShouldNotBeFound("residefate.lessThanOrEqual=" + SMALLER_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinsByResidefateIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where residefate is less than DEFAULT_RESIDEFATE
        defaultCheckinShouldNotBeFound("residefate.lessThan=" + DEFAULT_RESIDEFATE);

        // Get all the checkinList where residefate is less than UPDATED_RESIDEFATE
        defaultCheckinShouldBeFound("residefate.lessThan=" + UPDATED_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinsByResidefateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where residefate is greater than DEFAULT_RESIDEFATE
        defaultCheckinShouldNotBeFound("residefate.greaterThan=" + DEFAULT_RESIDEFATE);

        // Get all the checkinList where residefate is greater than SMALLER_RESIDEFATE
        defaultCheckinShouldBeFound("residefate.greaterThan=" + SMALLER_RESIDEFATE);
    }

    @Test
    @Transactional
    void getAllCheckinsByGotimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where gotime equals to DEFAULT_GOTIME
        defaultCheckinShouldBeFound("gotime.equals=" + DEFAULT_GOTIME);

        // Get all the checkinList where gotime equals to UPDATED_GOTIME
        defaultCheckinShouldNotBeFound("gotime.equals=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCheckinsByGotimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where gotime not equals to DEFAULT_GOTIME
        defaultCheckinShouldNotBeFound("gotime.notEquals=" + DEFAULT_GOTIME);

        // Get all the checkinList where gotime not equals to UPDATED_GOTIME
        defaultCheckinShouldBeFound("gotime.notEquals=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCheckinsByGotimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where gotime in DEFAULT_GOTIME or UPDATED_GOTIME
        defaultCheckinShouldBeFound("gotime.in=" + DEFAULT_GOTIME + "," + UPDATED_GOTIME);

        // Get all the checkinList where gotime equals to UPDATED_GOTIME
        defaultCheckinShouldNotBeFound("gotime.in=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllCheckinsByGotimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where gotime is not null
        defaultCheckinShouldBeFound("gotime.specified=true");

        // Get all the checkinList where gotime is null
        defaultCheckinShouldNotBeFound("gotime.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where empn equals to DEFAULT_EMPN
        defaultCheckinShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the checkinList where empn equals to UPDATED_EMPN
        defaultCheckinShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinsByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where empn not equals to DEFAULT_EMPN
        defaultCheckinShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the checkinList where empn not equals to UPDATED_EMPN
        defaultCheckinShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinsByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultCheckinShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the checkinList where empn equals to UPDATED_EMPN
        defaultCheckinShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinsByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where empn is not null
        defaultCheckinShouldBeFound("empn.specified=true");

        // Get all the checkinList where empn is null
        defaultCheckinShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByEmpnContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where empn contains DEFAULT_EMPN
        defaultCheckinShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the checkinList where empn contains UPDATED_EMPN
        defaultCheckinShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinsByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where empn does not contain DEFAULT_EMPN
        defaultCheckinShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the checkinList where empn does not contain UPDATED_EMPN
        defaultCheckinShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCheckinsByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where roomn equals to DEFAULT_ROOMN
        defaultCheckinShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the checkinList where roomn equals to UPDATED_ROOMN
        defaultCheckinShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinsByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where roomn not equals to DEFAULT_ROOMN
        defaultCheckinShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the checkinList where roomn not equals to UPDATED_ROOMN
        defaultCheckinShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinsByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultCheckinShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the checkinList where roomn equals to UPDATED_ROOMN
        defaultCheckinShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinsByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where roomn is not null
        defaultCheckinShouldBeFound("roomn.specified=true");

        // Get all the checkinList where roomn is null
        defaultCheckinShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByRoomnContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where roomn contains DEFAULT_ROOMN
        defaultCheckinShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the checkinList where roomn contains UPDATED_ROOMN
        defaultCheckinShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinsByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where roomn does not contain DEFAULT_ROOMN
        defaultCheckinShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the checkinList where roomn does not contain UPDATED_ROOMN
        defaultCheckinShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllCheckinsByUnameIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where uname equals to DEFAULT_UNAME
        defaultCheckinShouldBeFound("uname.equals=" + DEFAULT_UNAME);

        // Get all the checkinList where uname equals to UPDATED_UNAME
        defaultCheckinShouldNotBeFound("uname.equals=" + UPDATED_UNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByUnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where uname not equals to DEFAULT_UNAME
        defaultCheckinShouldNotBeFound("uname.notEquals=" + DEFAULT_UNAME);

        // Get all the checkinList where uname not equals to UPDATED_UNAME
        defaultCheckinShouldBeFound("uname.notEquals=" + UPDATED_UNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByUnameIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where uname in DEFAULT_UNAME or UPDATED_UNAME
        defaultCheckinShouldBeFound("uname.in=" + DEFAULT_UNAME + "," + UPDATED_UNAME);

        // Get all the checkinList where uname equals to UPDATED_UNAME
        defaultCheckinShouldNotBeFound("uname.in=" + UPDATED_UNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByUnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where uname is not null
        defaultCheckinShouldBeFound("uname.specified=true");

        // Get all the checkinList where uname is null
        defaultCheckinShouldNotBeFound("uname.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByUnameContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where uname contains DEFAULT_UNAME
        defaultCheckinShouldBeFound("uname.contains=" + DEFAULT_UNAME);

        // Get all the checkinList where uname contains UPDATED_UNAME
        defaultCheckinShouldNotBeFound("uname.contains=" + UPDATED_UNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByUnameNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where uname does not contain DEFAULT_UNAME
        defaultCheckinShouldNotBeFound("uname.doesNotContain=" + DEFAULT_UNAME);

        // Get all the checkinList where uname does not contain UPDATED_UNAME
        defaultCheckinShouldBeFound("uname.doesNotContain=" + UPDATED_UNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByRentpIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where rentp equals to DEFAULT_RENTP
        defaultCheckinShouldBeFound("rentp.equals=" + DEFAULT_RENTP);

        // Get all the checkinList where rentp equals to UPDATED_RENTP
        defaultCheckinShouldNotBeFound("rentp.equals=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinsByRentpIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where rentp not equals to DEFAULT_RENTP
        defaultCheckinShouldNotBeFound("rentp.notEquals=" + DEFAULT_RENTP);

        // Get all the checkinList where rentp not equals to UPDATED_RENTP
        defaultCheckinShouldBeFound("rentp.notEquals=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinsByRentpIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where rentp in DEFAULT_RENTP or UPDATED_RENTP
        defaultCheckinShouldBeFound("rentp.in=" + DEFAULT_RENTP + "," + UPDATED_RENTP);

        // Get all the checkinList where rentp equals to UPDATED_RENTP
        defaultCheckinShouldNotBeFound("rentp.in=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinsByRentpIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where rentp is not null
        defaultCheckinShouldBeFound("rentp.specified=true");

        // Get all the checkinList where rentp is null
        defaultCheckinShouldNotBeFound("rentp.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByRentpContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where rentp contains DEFAULT_RENTP
        defaultCheckinShouldBeFound("rentp.contains=" + DEFAULT_RENTP);

        // Get all the checkinList where rentp contains UPDATED_RENTP
        defaultCheckinShouldNotBeFound("rentp.contains=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinsByRentpNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where rentp does not contain DEFAULT_RENTP
        defaultCheckinShouldNotBeFound("rentp.doesNotContain=" + DEFAULT_RENTP);

        // Get all the checkinList where rentp does not contain UPDATED_RENTP
        defaultCheckinShouldBeFound("rentp.doesNotContain=" + UPDATED_RENTP);
    }

    @Test
    @Transactional
    void getAllCheckinsByProtocolrentIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where protocolrent equals to DEFAULT_PROTOCOLRENT
        defaultCheckinShouldBeFound("protocolrent.equals=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinList where protocolrent equals to UPDATED_PROTOCOLRENT
        defaultCheckinShouldNotBeFound("protocolrent.equals=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinsByProtocolrentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where protocolrent not equals to DEFAULT_PROTOCOLRENT
        defaultCheckinShouldNotBeFound("protocolrent.notEquals=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinList where protocolrent not equals to UPDATED_PROTOCOLRENT
        defaultCheckinShouldBeFound("protocolrent.notEquals=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinsByProtocolrentIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where protocolrent in DEFAULT_PROTOCOLRENT or UPDATED_PROTOCOLRENT
        defaultCheckinShouldBeFound("protocolrent.in=" + DEFAULT_PROTOCOLRENT + "," + UPDATED_PROTOCOLRENT);

        // Get all the checkinList where protocolrent equals to UPDATED_PROTOCOLRENT
        defaultCheckinShouldNotBeFound("protocolrent.in=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinsByProtocolrentIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where protocolrent is not null
        defaultCheckinShouldBeFound("protocolrent.specified=true");

        // Get all the checkinList where protocolrent is null
        defaultCheckinShouldNotBeFound("protocolrent.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByProtocolrentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where protocolrent is greater than or equal to DEFAULT_PROTOCOLRENT
        defaultCheckinShouldBeFound("protocolrent.greaterThanOrEqual=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinList where protocolrent is greater than or equal to UPDATED_PROTOCOLRENT
        defaultCheckinShouldNotBeFound("protocolrent.greaterThanOrEqual=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinsByProtocolrentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where protocolrent is less than or equal to DEFAULT_PROTOCOLRENT
        defaultCheckinShouldBeFound("protocolrent.lessThanOrEqual=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinList where protocolrent is less than or equal to SMALLER_PROTOCOLRENT
        defaultCheckinShouldNotBeFound("protocolrent.lessThanOrEqual=" + SMALLER_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinsByProtocolrentIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where protocolrent is less than DEFAULT_PROTOCOLRENT
        defaultCheckinShouldNotBeFound("protocolrent.lessThan=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinList where protocolrent is less than UPDATED_PROTOCOLRENT
        defaultCheckinShouldBeFound("protocolrent.lessThan=" + UPDATED_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinsByProtocolrentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where protocolrent is greater than DEFAULT_PROTOCOLRENT
        defaultCheckinShouldNotBeFound("protocolrent.greaterThan=" + DEFAULT_PROTOCOLRENT);

        // Get all the checkinList where protocolrent is greater than SMALLER_PROTOCOLRENT
        defaultCheckinShouldBeFound("protocolrent.greaterThan=" + SMALLER_PROTOCOLRENT);
    }

    @Test
    @Transactional
    void getAllCheckinsByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where remark equals to DEFAULT_REMARK
        defaultCheckinShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the checkinList where remark equals to UPDATED_REMARK
        defaultCheckinShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinsByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where remark not equals to DEFAULT_REMARK
        defaultCheckinShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the checkinList where remark not equals to UPDATED_REMARK
        defaultCheckinShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinsByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultCheckinShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the checkinList where remark equals to UPDATED_REMARK
        defaultCheckinShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinsByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where remark is not null
        defaultCheckinShouldBeFound("remark.specified=true");

        // Get all the checkinList where remark is null
        defaultCheckinShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByRemarkContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where remark contains DEFAULT_REMARK
        defaultCheckinShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the checkinList where remark contains UPDATED_REMARK
        defaultCheckinShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinsByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where remark does not contain DEFAULT_REMARK
        defaultCheckinShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the checkinList where remark does not contain UPDATED_REMARK
        defaultCheckinShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllCheckinsByPhonenIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where phonen equals to DEFAULT_PHONEN
        defaultCheckinShouldBeFound("phonen.equals=" + DEFAULT_PHONEN);

        // Get all the checkinList where phonen equals to UPDATED_PHONEN
        defaultCheckinShouldNotBeFound("phonen.equals=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinsByPhonenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where phonen not equals to DEFAULT_PHONEN
        defaultCheckinShouldNotBeFound("phonen.notEquals=" + DEFAULT_PHONEN);

        // Get all the checkinList where phonen not equals to UPDATED_PHONEN
        defaultCheckinShouldBeFound("phonen.notEquals=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinsByPhonenIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where phonen in DEFAULT_PHONEN or UPDATED_PHONEN
        defaultCheckinShouldBeFound("phonen.in=" + DEFAULT_PHONEN + "," + UPDATED_PHONEN);

        // Get all the checkinList where phonen equals to UPDATED_PHONEN
        defaultCheckinShouldNotBeFound("phonen.in=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinsByPhonenIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where phonen is not null
        defaultCheckinShouldBeFound("phonen.specified=true");

        // Get all the checkinList where phonen is null
        defaultCheckinShouldNotBeFound("phonen.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByPhonenContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where phonen contains DEFAULT_PHONEN
        defaultCheckinShouldBeFound("phonen.contains=" + DEFAULT_PHONEN);

        // Get all the checkinList where phonen contains UPDATED_PHONEN
        defaultCheckinShouldNotBeFound("phonen.contains=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinsByPhonenNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where phonen does not contain DEFAULT_PHONEN
        defaultCheckinShouldNotBeFound("phonen.doesNotContain=" + DEFAULT_PHONEN);

        // Get all the checkinList where phonen does not contain UPDATED_PHONEN
        defaultCheckinShouldBeFound("phonen.doesNotContain=" + UPDATED_PHONEN);
    }

    @Test
    @Transactional
    void getAllCheckinsByEmpn2IsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where empn2 equals to DEFAULT_EMPN_2
        defaultCheckinShouldBeFound("empn2.equals=" + DEFAULT_EMPN_2);

        // Get all the checkinList where empn2 equals to UPDATED_EMPN_2
        defaultCheckinShouldNotBeFound("empn2.equals=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinsByEmpn2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where empn2 not equals to DEFAULT_EMPN_2
        defaultCheckinShouldNotBeFound("empn2.notEquals=" + DEFAULT_EMPN_2);

        // Get all the checkinList where empn2 not equals to UPDATED_EMPN_2
        defaultCheckinShouldBeFound("empn2.notEquals=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinsByEmpn2IsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where empn2 in DEFAULT_EMPN_2 or UPDATED_EMPN_2
        defaultCheckinShouldBeFound("empn2.in=" + DEFAULT_EMPN_2 + "," + UPDATED_EMPN_2);

        // Get all the checkinList where empn2 equals to UPDATED_EMPN_2
        defaultCheckinShouldNotBeFound("empn2.in=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinsByEmpn2IsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where empn2 is not null
        defaultCheckinShouldBeFound("empn2.specified=true");

        // Get all the checkinList where empn2 is null
        defaultCheckinShouldNotBeFound("empn2.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByEmpn2ContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where empn2 contains DEFAULT_EMPN_2
        defaultCheckinShouldBeFound("empn2.contains=" + DEFAULT_EMPN_2);

        // Get all the checkinList where empn2 contains UPDATED_EMPN_2
        defaultCheckinShouldNotBeFound("empn2.contains=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinsByEmpn2NotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where empn2 does not contain DEFAULT_EMPN_2
        defaultCheckinShouldNotBeFound("empn2.doesNotContain=" + DEFAULT_EMPN_2);

        // Get all the checkinList where empn2 does not contain UPDATED_EMPN_2
        defaultCheckinShouldBeFound("empn2.doesNotContain=" + UPDATED_EMPN_2);
    }

    @Test
    @Transactional
    void getAllCheckinsByAdhocIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where adhoc equals to DEFAULT_ADHOC
        defaultCheckinShouldBeFound("adhoc.equals=" + DEFAULT_ADHOC);

        // Get all the checkinList where adhoc equals to UPDATED_ADHOC
        defaultCheckinShouldNotBeFound("adhoc.equals=" + UPDATED_ADHOC);
    }

    @Test
    @Transactional
    void getAllCheckinsByAdhocIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where adhoc not equals to DEFAULT_ADHOC
        defaultCheckinShouldNotBeFound("adhoc.notEquals=" + DEFAULT_ADHOC);

        // Get all the checkinList where adhoc not equals to UPDATED_ADHOC
        defaultCheckinShouldBeFound("adhoc.notEquals=" + UPDATED_ADHOC);
    }

    @Test
    @Transactional
    void getAllCheckinsByAdhocIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where adhoc in DEFAULT_ADHOC or UPDATED_ADHOC
        defaultCheckinShouldBeFound("adhoc.in=" + DEFAULT_ADHOC + "," + UPDATED_ADHOC);

        // Get all the checkinList where adhoc equals to UPDATED_ADHOC
        defaultCheckinShouldNotBeFound("adhoc.in=" + UPDATED_ADHOC);
    }

    @Test
    @Transactional
    void getAllCheckinsByAdhocIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where adhoc is not null
        defaultCheckinShouldBeFound("adhoc.specified=true");

        // Get all the checkinList where adhoc is null
        defaultCheckinShouldNotBeFound("adhoc.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByAdhocContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where adhoc contains DEFAULT_ADHOC
        defaultCheckinShouldBeFound("adhoc.contains=" + DEFAULT_ADHOC);

        // Get all the checkinList where adhoc contains UPDATED_ADHOC
        defaultCheckinShouldNotBeFound("adhoc.contains=" + UPDATED_ADHOC);
    }

    @Test
    @Transactional
    void getAllCheckinsByAdhocNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where adhoc does not contain DEFAULT_ADHOC
        defaultCheckinShouldNotBeFound("adhoc.doesNotContain=" + DEFAULT_ADHOC);

        // Get all the checkinList where adhoc does not contain UPDATED_ADHOC
        defaultCheckinShouldBeFound("adhoc.doesNotContain=" + UPDATED_ADHOC);
    }

    @Test
    @Transactional
    void getAllCheckinsByAuditflagIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where auditflag equals to DEFAULT_AUDITFLAG
        defaultCheckinShouldBeFound("auditflag.equals=" + DEFAULT_AUDITFLAG);

        // Get all the checkinList where auditflag equals to UPDATED_AUDITFLAG
        defaultCheckinShouldNotBeFound("auditflag.equals=" + UPDATED_AUDITFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByAuditflagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where auditflag not equals to DEFAULT_AUDITFLAG
        defaultCheckinShouldNotBeFound("auditflag.notEquals=" + DEFAULT_AUDITFLAG);

        // Get all the checkinList where auditflag not equals to UPDATED_AUDITFLAG
        defaultCheckinShouldBeFound("auditflag.notEquals=" + UPDATED_AUDITFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByAuditflagIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where auditflag in DEFAULT_AUDITFLAG or UPDATED_AUDITFLAG
        defaultCheckinShouldBeFound("auditflag.in=" + DEFAULT_AUDITFLAG + "," + UPDATED_AUDITFLAG);

        // Get all the checkinList where auditflag equals to UPDATED_AUDITFLAG
        defaultCheckinShouldNotBeFound("auditflag.in=" + UPDATED_AUDITFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByAuditflagIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where auditflag is not null
        defaultCheckinShouldBeFound("auditflag.specified=true");

        // Get all the checkinList where auditflag is null
        defaultCheckinShouldNotBeFound("auditflag.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByAuditflagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where auditflag is greater than or equal to DEFAULT_AUDITFLAG
        defaultCheckinShouldBeFound("auditflag.greaterThanOrEqual=" + DEFAULT_AUDITFLAG);

        // Get all the checkinList where auditflag is greater than or equal to UPDATED_AUDITFLAG
        defaultCheckinShouldNotBeFound("auditflag.greaterThanOrEqual=" + UPDATED_AUDITFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByAuditflagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where auditflag is less than or equal to DEFAULT_AUDITFLAG
        defaultCheckinShouldBeFound("auditflag.lessThanOrEqual=" + DEFAULT_AUDITFLAG);

        // Get all the checkinList where auditflag is less than or equal to SMALLER_AUDITFLAG
        defaultCheckinShouldNotBeFound("auditflag.lessThanOrEqual=" + SMALLER_AUDITFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByAuditflagIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where auditflag is less than DEFAULT_AUDITFLAG
        defaultCheckinShouldNotBeFound("auditflag.lessThan=" + DEFAULT_AUDITFLAG);

        // Get all the checkinList where auditflag is less than UPDATED_AUDITFLAG
        defaultCheckinShouldBeFound("auditflag.lessThan=" + UPDATED_AUDITFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByAuditflagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where auditflag is greater than DEFAULT_AUDITFLAG
        defaultCheckinShouldNotBeFound("auditflag.greaterThan=" + DEFAULT_AUDITFLAG);

        // Get all the checkinList where auditflag is greater than SMALLER_AUDITFLAG
        defaultCheckinShouldBeFound("auditflag.greaterThan=" + SMALLER_AUDITFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByGroupnIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where groupn equals to DEFAULT_GROUPN
        defaultCheckinShouldBeFound("groupn.equals=" + DEFAULT_GROUPN);

        // Get all the checkinList where groupn equals to UPDATED_GROUPN
        defaultCheckinShouldNotBeFound("groupn.equals=" + UPDATED_GROUPN);
    }

    @Test
    @Transactional
    void getAllCheckinsByGroupnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where groupn not equals to DEFAULT_GROUPN
        defaultCheckinShouldNotBeFound("groupn.notEquals=" + DEFAULT_GROUPN);

        // Get all the checkinList where groupn not equals to UPDATED_GROUPN
        defaultCheckinShouldBeFound("groupn.notEquals=" + UPDATED_GROUPN);
    }

    @Test
    @Transactional
    void getAllCheckinsByGroupnIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where groupn in DEFAULT_GROUPN or UPDATED_GROUPN
        defaultCheckinShouldBeFound("groupn.in=" + DEFAULT_GROUPN + "," + UPDATED_GROUPN);

        // Get all the checkinList where groupn equals to UPDATED_GROUPN
        defaultCheckinShouldNotBeFound("groupn.in=" + UPDATED_GROUPN);
    }

    @Test
    @Transactional
    void getAllCheckinsByGroupnIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where groupn is not null
        defaultCheckinShouldBeFound("groupn.specified=true");

        // Get all the checkinList where groupn is null
        defaultCheckinShouldNotBeFound("groupn.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByGroupnContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where groupn contains DEFAULT_GROUPN
        defaultCheckinShouldBeFound("groupn.contains=" + DEFAULT_GROUPN);

        // Get all the checkinList where groupn contains UPDATED_GROUPN
        defaultCheckinShouldNotBeFound("groupn.contains=" + UPDATED_GROUPN);
    }

    @Test
    @Transactional
    void getAllCheckinsByGroupnNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where groupn does not contain DEFAULT_GROUPN
        defaultCheckinShouldNotBeFound("groupn.doesNotContain=" + DEFAULT_GROUPN);

        // Get all the checkinList where groupn does not contain UPDATED_GROUPN
        defaultCheckinShouldBeFound("groupn.doesNotContain=" + UPDATED_GROUPN);
    }

    @Test
    @Transactional
    void getAllCheckinsByMemoIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where memo equals to DEFAULT_MEMO
        defaultCheckinShouldBeFound("memo.equals=" + DEFAULT_MEMO);

        // Get all the checkinList where memo equals to UPDATED_MEMO
        defaultCheckinShouldNotBeFound("memo.equals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinsByMemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where memo not equals to DEFAULT_MEMO
        defaultCheckinShouldNotBeFound("memo.notEquals=" + DEFAULT_MEMO);

        // Get all the checkinList where memo not equals to UPDATED_MEMO
        defaultCheckinShouldBeFound("memo.notEquals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinsByMemoIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where memo in DEFAULT_MEMO or UPDATED_MEMO
        defaultCheckinShouldBeFound("memo.in=" + DEFAULT_MEMO + "," + UPDATED_MEMO);

        // Get all the checkinList where memo equals to UPDATED_MEMO
        defaultCheckinShouldNotBeFound("memo.in=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinsByMemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where memo is not null
        defaultCheckinShouldBeFound("memo.specified=true");

        // Get all the checkinList where memo is null
        defaultCheckinShouldNotBeFound("memo.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByMemoContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where memo contains DEFAULT_MEMO
        defaultCheckinShouldBeFound("memo.contains=" + DEFAULT_MEMO);

        // Get all the checkinList where memo contains UPDATED_MEMO
        defaultCheckinShouldNotBeFound("memo.contains=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinsByMemoNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where memo does not contain DEFAULT_MEMO
        defaultCheckinShouldNotBeFound("memo.doesNotContain=" + DEFAULT_MEMO);

        // Get all the checkinList where memo does not contain UPDATED_MEMO
        defaultCheckinShouldBeFound("memo.doesNotContain=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllCheckinsByLfSignIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where lfSign equals to DEFAULT_LF_SIGN
        defaultCheckinShouldBeFound("lfSign.equals=" + DEFAULT_LF_SIGN);

        // Get all the checkinList where lfSign equals to UPDATED_LF_SIGN
        defaultCheckinShouldNotBeFound("lfSign.equals=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByLfSignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where lfSign not equals to DEFAULT_LF_SIGN
        defaultCheckinShouldNotBeFound("lfSign.notEquals=" + DEFAULT_LF_SIGN);

        // Get all the checkinList where lfSign not equals to UPDATED_LF_SIGN
        defaultCheckinShouldBeFound("lfSign.notEquals=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByLfSignIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where lfSign in DEFAULT_LF_SIGN or UPDATED_LF_SIGN
        defaultCheckinShouldBeFound("lfSign.in=" + DEFAULT_LF_SIGN + "," + UPDATED_LF_SIGN);

        // Get all the checkinList where lfSign equals to UPDATED_LF_SIGN
        defaultCheckinShouldNotBeFound("lfSign.in=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByLfSignIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where lfSign is not null
        defaultCheckinShouldBeFound("lfSign.specified=true");

        // Get all the checkinList where lfSign is null
        defaultCheckinShouldNotBeFound("lfSign.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByLfSignContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where lfSign contains DEFAULT_LF_SIGN
        defaultCheckinShouldBeFound("lfSign.contains=" + DEFAULT_LF_SIGN);

        // Get all the checkinList where lfSign contains UPDATED_LF_SIGN
        defaultCheckinShouldNotBeFound("lfSign.contains=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByLfSignNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where lfSign does not contain DEFAULT_LF_SIGN
        defaultCheckinShouldNotBeFound("lfSign.doesNotContain=" + DEFAULT_LF_SIGN);

        // Get all the checkinList where lfSign does not contain UPDATED_LF_SIGN
        defaultCheckinShouldBeFound("lfSign.doesNotContain=" + UPDATED_LF_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByKeynumIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where keynum equals to DEFAULT_KEYNUM
        defaultCheckinShouldBeFound("keynum.equals=" + DEFAULT_KEYNUM);

        // Get all the checkinList where keynum equals to UPDATED_KEYNUM
        defaultCheckinShouldNotBeFound("keynum.equals=" + UPDATED_KEYNUM);
    }

    @Test
    @Transactional
    void getAllCheckinsByKeynumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where keynum not equals to DEFAULT_KEYNUM
        defaultCheckinShouldNotBeFound("keynum.notEquals=" + DEFAULT_KEYNUM);

        // Get all the checkinList where keynum not equals to UPDATED_KEYNUM
        defaultCheckinShouldBeFound("keynum.notEquals=" + UPDATED_KEYNUM);
    }

    @Test
    @Transactional
    void getAllCheckinsByKeynumIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where keynum in DEFAULT_KEYNUM or UPDATED_KEYNUM
        defaultCheckinShouldBeFound("keynum.in=" + DEFAULT_KEYNUM + "," + UPDATED_KEYNUM);

        // Get all the checkinList where keynum equals to UPDATED_KEYNUM
        defaultCheckinShouldNotBeFound("keynum.in=" + UPDATED_KEYNUM);
    }

    @Test
    @Transactional
    void getAllCheckinsByKeynumIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where keynum is not null
        defaultCheckinShouldBeFound("keynum.specified=true");

        // Get all the checkinList where keynum is null
        defaultCheckinShouldNotBeFound("keynum.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByKeynumContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where keynum contains DEFAULT_KEYNUM
        defaultCheckinShouldBeFound("keynum.contains=" + DEFAULT_KEYNUM);

        // Get all the checkinList where keynum contains UPDATED_KEYNUM
        defaultCheckinShouldNotBeFound("keynum.contains=" + UPDATED_KEYNUM);
    }

    @Test
    @Transactional
    void getAllCheckinsByKeynumNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where keynum does not contain DEFAULT_KEYNUM
        defaultCheckinShouldNotBeFound("keynum.doesNotContain=" + DEFAULT_KEYNUM);

        // Get all the checkinList where keynum does not contain UPDATED_KEYNUM
        defaultCheckinShouldBeFound("keynum.doesNotContain=" + UPDATED_KEYNUM);
    }

    @Test
    @Transactional
    void getAllCheckinsByHykhIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hykh equals to DEFAULT_HYKH
        defaultCheckinShouldBeFound("hykh.equals=" + DEFAULT_HYKH);

        // Get all the checkinList where hykh equals to UPDATED_HYKH
        defaultCheckinShouldNotBeFound("hykh.equals=" + UPDATED_HYKH);
    }

    @Test
    @Transactional
    void getAllCheckinsByHykhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hykh not equals to DEFAULT_HYKH
        defaultCheckinShouldNotBeFound("hykh.notEquals=" + DEFAULT_HYKH);

        // Get all the checkinList where hykh not equals to UPDATED_HYKH
        defaultCheckinShouldBeFound("hykh.notEquals=" + UPDATED_HYKH);
    }

    @Test
    @Transactional
    void getAllCheckinsByHykhIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hykh in DEFAULT_HYKH or UPDATED_HYKH
        defaultCheckinShouldBeFound("hykh.in=" + DEFAULT_HYKH + "," + UPDATED_HYKH);

        // Get all the checkinList where hykh equals to UPDATED_HYKH
        defaultCheckinShouldNotBeFound("hykh.in=" + UPDATED_HYKH);
    }

    @Test
    @Transactional
    void getAllCheckinsByHykhIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hykh is not null
        defaultCheckinShouldBeFound("hykh.specified=true");

        // Get all the checkinList where hykh is null
        defaultCheckinShouldNotBeFound("hykh.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByHykhContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hykh contains DEFAULT_HYKH
        defaultCheckinShouldBeFound("hykh.contains=" + DEFAULT_HYKH);

        // Get all the checkinList where hykh contains UPDATED_HYKH
        defaultCheckinShouldNotBeFound("hykh.contains=" + UPDATED_HYKH);
    }

    @Test
    @Transactional
    void getAllCheckinsByHykhNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hykh does not contain DEFAULT_HYKH
        defaultCheckinShouldNotBeFound("hykh.doesNotContain=" + DEFAULT_HYKH);

        // Get all the checkinList where hykh does not contain UPDATED_HYKH
        defaultCheckinShouldBeFound("hykh.doesNotContain=" + UPDATED_HYKH);
    }

    @Test
    @Transactional
    void getAllCheckinsByBmIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bm equals to DEFAULT_BM
        defaultCheckinShouldBeFound("bm.equals=" + DEFAULT_BM);

        // Get all the checkinList where bm equals to UPDATED_BM
        defaultCheckinShouldNotBeFound("bm.equals=" + UPDATED_BM);
    }

    @Test
    @Transactional
    void getAllCheckinsByBmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bm not equals to DEFAULT_BM
        defaultCheckinShouldNotBeFound("bm.notEquals=" + DEFAULT_BM);

        // Get all the checkinList where bm not equals to UPDATED_BM
        defaultCheckinShouldBeFound("bm.notEquals=" + UPDATED_BM);
    }

    @Test
    @Transactional
    void getAllCheckinsByBmIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bm in DEFAULT_BM or UPDATED_BM
        defaultCheckinShouldBeFound("bm.in=" + DEFAULT_BM + "," + UPDATED_BM);

        // Get all the checkinList where bm equals to UPDATED_BM
        defaultCheckinShouldNotBeFound("bm.in=" + UPDATED_BM);
    }

    @Test
    @Transactional
    void getAllCheckinsByBmIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bm is not null
        defaultCheckinShouldBeFound("bm.specified=true");

        // Get all the checkinList where bm is null
        defaultCheckinShouldNotBeFound("bm.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByBmContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bm contains DEFAULT_BM
        defaultCheckinShouldBeFound("bm.contains=" + DEFAULT_BM);

        // Get all the checkinList where bm contains UPDATED_BM
        defaultCheckinShouldNotBeFound("bm.contains=" + UPDATED_BM);
    }

    @Test
    @Transactional
    void getAllCheckinsByBmNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bm does not contain DEFAULT_BM
        defaultCheckinShouldNotBeFound("bm.doesNotContain=" + DEFAULT_BM);

        // Get all the checkinList where bm does not contain UPDATED_BM
        defaultCheckinShouldBeFound("bm.doesNotContain=" + UPDATED_BM);
    }

    @Test
    @Transactional
    void getAllCheckinsByFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where flag equals to DEFAULT_FLAG
        defaultCheckinShouldBeFound("flag.equals=" + DEFAULT_FLAG);

        // Get all the checkinList where flag equals to UPDATED_FLAG
        defaultCheckinShouldNotBeFound("flag.equals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where flag not equals to DEFAULT_FLAG
        defaultCheckinShouldNotBeFound("flag.notEquals=" + DEFAULT_FLAG);

        // Get all the checkinList where flag not equals to UPDATED_FLAG
        defaultCheckinShouldBeFound("flag.notEquals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByFlagIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where flag in DEFAULT_FLAG or UPDATED_FLAG
        defaultCheckinShouldBeFound("flag.in=" + DEFAULT_FLAG + "," + UPDATED_FLAG);

        // Get all the checkinList where flag equals to UPDATED_FLAG
        defaultCheckinShouldNotBeFound("flag.in=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where flag is not null
        defaultCheckinShouldBeFound("flag.specified=true");

        // Get all the checkinList where flag is null
        defaultCheckinShouldNotBeFound("flag.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByFlagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where flag is greater than or equal to DEFAULT_FLAG
        defaultCheckinShouldBeFound("flag.greaterThanOrEqual=" + DEFAULT_FLAG);

        // Get all the checkinList where flag is greater than or equal to UPDATED_FLAG
        defaultCheckinShouldNotBeFound("flag.greaterThanOrEqual=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByFlagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where flag is less than or equal to DEFAULT_FLAG
        defaultCheckinShouldBeFound("flag.lessThanOrEqual=" + DEFAULT_FLAG);

        // Get all the checkinList where flag is less than or equal to SMALLER_FLAG
        defaultCheckinShouldNotBeFound("flag.lessThanOrEqual=" + SMALLER_FLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByFlagIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where flag is less than DEFAULT_FLAG
        defaultCheckinShouldNotBeFound("flag.lessThan=" + DEFAULT_FLAG);

        // Get all the checkinList where flag is less than UPDATED_FLAG
        defaultCheckinShouldBeFound("flag.lessThan=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByFlagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where flag is greater than DEFAULT_FLAG
        defaultCheckinShouldNotBeFound("flag.greaterThan=" + DEFAULT_FLAG);

        // Get all the checkinList where flag is greater than SMALLER_FLAG
        defaultCheckinShouldBeFound("flag.greaterThan=" + SMALLER_FLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxtimeIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxtime equals to DEFAULT_JXTIME
        defaultCheckinShouldBeFound("jxtime.equals=" + DEFAULT_JXTIME);

        // Get all the checkinList where jxtime equals to UPDATED_JXTIME
        defaultCheckinShouldNotBeFound("jxtime.equals=" + UPDATED_JXTIME);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxtimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxtime not equals to DEFAULT_JXTIME
        defaultCheckinShouldNotBeFound("jxtime.notEquals=" + DEFAULT_JXTIME);

        // Get all the checkinList where jxtime not equals to UPDATED_JXTIME
        defaultCheckinShouldBeFound("jxtime.notEquals=" + UPDATED_JXTIME);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxtimeIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxtime in DEFAULT_JXTIME or UPDATED_JXTIME
        defaultCheckinShouldBeFound("jxtime.in=" + DEFAULT_JXTIME + "," + UPDATED_JXTIME);

        // Get all the checkinList where jxtime equals to UPDATED_JXTIME
        defaultCheckinShouldNotBeFound("jxtime.in=" + UPDATED_JXTIME);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxtimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxtime is not null
        defaultCheckinShouldBeFound("jxtime.specified=true");

        // Get all the checkinList where jxtime is null
        defaultCheckinShouldNotBeFound("jxtime.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByJxflagIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxflag equals to DEFAULT_JXFLAG
        defaultCheckinShouldBeFound("jxflag.equals=" + DEFAULT_JXFLAG);

        // Get all the checkinList where jxflag equals to UPDATED_JXFLAG
        defaultCheckinShouldNotBeFound("jxflag.equals=" + UPDATED_JXFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxflagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxflag not equals to DEFAULT_JXFLAG
        defaultCheckinShouldNotBeFound("jxflag.notEquals=" + DEFAULT_JXFLAG);

        // Get all the checkinList where jxflag not equals to UPDATED_JXFLAG
        defaultCheckinShouldBeFound("jxflag.notEquals=" + UPDATED_JXFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxflagIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxflag in DEFAULT_JXFLAG or UPDATED_JXFLAG
        defaultCheckinShouldBeFound("jxflag.in=" + DEFAULT_JXFLAG + "," + UPDATED_JXFLAG);

        // Get all the checkinList where jxflag equals to UPDATED_JXFLAG
        defaultCheckinShouldNotBeFound("jxflag.in=" + UPDATED_JXFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxflagIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxflag is not null
        defaultCheckinShouldBeFound("jxflag.specified=true");

        // Get all the checkinList where jxflag is null
        defaultCheckinShouldNotBeFound("jxflag.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByJxflagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxflag is greater than or equal to DEFAULT_JXFLAG
        defaultCheckinShouldBeFound("jxflag.greaterThanOrEqual=" + DEFAULT_JXFLAG);

        // Get all the checkinList where jxflag is greater than or equal to UPDATED_JXFLAG
        defaultCheckinShouldNotBeFound("jxflag.greaterThanOrEqual=" + UPDATED_JXFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxflagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxflag is less than or equal to DEFAULT_JXFLAG
        defaultCheckinShouldBeFound("jxflag.lessThanOrEqual=" + DEFAULT_JXFLAG);

        // Get all the checkinList where jxflag is less than or equal to SMALLER_JXFLAG
        defaultCheckinShouldNotBeFound("jxflag.lessThanOrEqual=" + SMALLER_JXFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxflagIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxflag is less than DEFAULT_JXFLAG
        defaultCheckinShouldNotBeFound("jxflag.lessThan=" + DEFAULT_JXFLAG);

        // Get all the checkinList where jxflag is less than UPDATED_JXFLAG
        defaultCheckinShouldBeFound("jxflag.lessThan=" + UPDATED_JXFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxflagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxflag is greater than DEFAULT_JXFLAG
        defaultCheckinShouldNotBeFound("jxflag.greaterThan=" + DEFAULT_JXFLAG);

        // Get all the checkinList where jxflag is greater than SMALLER_JXFLAG
        defaultCheckinShouldBeFound("jxflag.greaterThan=" + SMALLER_JXFLAG);
    }

    @Test
    @Transactional
    void getAllCheckinsByCheckfIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where checkf equals to DEFAULT_CHECKF
        defaultCheckinShouldBeFound("checkf.equals=" + DEFAULT_CHECKF);

        // Get all the checkinList where checkf equals to UPDATED_CHECKF
        defaultCheckinShouldNotBeFound("checkf.equals=" + UPDATED_CHECKF);
    }

    @Test
    @Transactional
    void getAllCheckinsByCheckfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where checkf not equals to DEFAULT_CHECKF
        defaultCheckinShouldNotBeFound("checkf.notEquals=" + DEFAULT_CHECKF);

        // Get all the checkinList where checkf not equals to UPDATED_CHECKF
        defaultCheckinShouldBeFound("checkf.notEquals=" + UPDATED_CHECKF);
    }

    @Test
    @Transactional
    void getAllCheckinsByCheckfIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where checkf in DEFAULT_CHECKF or UPDATED_CHECKF
        defaultCheckinShouldBeFound("checkf.in=" + DEFAULT_CHECKF + "," + UPDATED_CHECKF);

        // Get all the checkinList where checkf equals to UPDATED_CHECKF
        defaultCheckinShouldNotBeFound("checkf.in=" + UPDATED_CHECKF);
    }

    @Test
    @Transactional
    void getAllCheckinsByCheckfIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where checkf is not null
        defaultCheckinShouldBeFound("checkf.specified=true");

        // Get all the checkinList where checkf is null
        defaultCheckinShouldNotBeFound("checkf.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByCheckfIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where checkf is greater than or equal to DEFAULT_CHECKF
        defaultCheckinShouldBeFound("checkf.greaterThanOrEqual=" + DEFAULT_CHECKF);

        // Get all the checkinList where checkf is greater than or equal to UPDATED_CHECKF
        defaultCheckinShouldNotBeFound("checkf.greaterThanOrEqual=" + UPDATED_CHECKF);
    }

    @Test
    @Transactional
    void getAllCheckinsByCheckfIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where checkf is less than or equal to DEFAULT_CHECKF
        defaultCheckinShouldBeFound("checkf.lessThanOrEqual=" + DEFAULT_CHECKF);

        // Get all the checkinList where checkf is less than or equal to SMALLER_CHECKF
        defaultCheckinShouldNotBeFound("checkf.lessThanOrEqual=" + SMALLER_CHECKF);
    }

    @Test
    @Transactional
    void getAllCheckinsByCheckfIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where checkf is less than DEFAULT_CHECKF
        defaultCheckinShouldNotBeFound("checkf.lessThan=" + DEFAULT_CHECKF);

        // Get all the checkinList where checkf is less than UPDATED_CHECKF
        defaultCheckinShouldBeFound("checkf.lessThan=" + UPDATED_CHECKF);
    }

    @Test
    @Transactional
    void getAllCheckinsByCheckfIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where checkf is greater than DEFAULT_CHECKF
        defaultCheckinShouldNotBeFound("checkf.greaterThan=" + DEFAULT_CHECKF);

        // Get all the checkinList where checkf is greater than SMALLER_CHECKF
        defaultCheckinShouldBeFound("checkf.greaterThan=" + SMALLER_CHECKF);
    }

    @Test
    @Transactional
    void getAllCheckinsByGuestnameIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where guestname equals to DEFAULT_GUESTNAME
        defaultCheckinShouldBeFound("guestname.equals=" + DEFAULT_GUESTNAME);

        // Get all the checkinList where guestname equals to UPDATED_GUESTNAME
        defaultCheckinShouldNotBeFound("guestname.equals=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByGuestnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where guestname not equals to DEFAULT_GUESTNAME
        defaultCheckinShouldNotBeFound("guestname.notEquals=" + DEFAULT_GUESTNAME);

        // Get all the checkinList where guestname not equals to UPDATED_GUESTNAME
        defaultCheckinShouldBeFound("guestname.notEquals=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByGuestnameIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where guestname in DEFAULT_GUESTNAME or UPDATED_GUESTNAME
        defaultCheckinShouldBeFound("guestname.in=" + DEFAULT_GUESTNAME + "," + UPDATED_GUESTNAME);

        // Get all the checkinList where guestname equals to UPDATED_GUESTNAME
        defaultCheckinShouldNotBeFound("guestname.in=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByGuestnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where guestname is not null
        defaultCheckinShouldBeFound("guestname.specified=true");

        // Get all the checkinList where guestname is null
        defaultCheckinShouldNotBeFound("guestname.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByGuestnameContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where guestname contains DEFAULT_GUESTNAME
        defaultCheckinShouldBeFound("guestname.contains=" + DEFAULT_GUESTNAME);

        // Get all the checkinList where guestname contains UPDATED_GUESTNAME
        defaultCheckinShouldNotBeFound("guestname.contains=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByGuestnameNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where guestname does not contain DEFAULT_GUESTNAME
        defaultCheckinShouldNotBeFound("guestname.doesNotContain=" + DEFAULT_GUESTNAME);

        // Get all the checkinList where guestname does not contain UPDATED_GUESTNAME
        defaultCheckinShouldBeFound("guestname.doesNotContain=" + UPDATED_GUESTNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByFgfIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fgf equals to DEFAULT_FGF
        defaultCheckinShouldBeFound("fgf.equals=" + DEFAULT_FGF);

        // Get all the checkinList where fgf equals to UPDATED_FGF
        defaultCheckinShouldNotBeFound("fgf.equals=" + UPDATED_FGF);
    }

    @Test
    @Transactional
    void getAllCheckinsByFgfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fgf not equals to DEFAULT_FGF
        defaultCheckinShouldNotBeFound("fgf.notEquals=" + DEFAULT_FGF);

        // Get all the checkinList where fgf not equals to UPDATED_FGF
        defaultCheckinShouldBeFound("fgf.notEquals=" + UPDATED_FGF);
    }

    @Test
    @Transactional
    void getAllCheckinsByFgfIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fgf in DEFAULT_FGF or UPDATED_FGF
        defaultCheckinShouldBeFound("fgf.in=" + DEFAULT_FGF + "," + UPDATED_FGF);

        // Get all the checkinList where fgf equals to UPDATED_FGF
        defaultCheckinShouldNotBeFound("fgf.in=" + UPDATED_FGF);
    }

    @Test
    @Transactional
    void getAllCheckinsByFgfIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fgf is not null
        defaultCheckinShouldBeFound("fgf.specified=true");

        // Get all the checkinList where fgf is null
        defaultCheckinShouldNotBeFound("fgf.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByFgfIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fgf is greater than or equal to DEFAULT_FGF
        defaultCheckinShouldBeFound("fgf.greaterThanOrEqual=" + DEFAULT_FGF);

        // Get all the checkinList where fgf is greater than or equal to UPDATED_FGF
        defaultCheckinShouldNotBeFound("fgf.greaterThanOrEqual=" + UPDATED_FGF);
    }

    @Test
    @Transactional
    void getAllCheckinsByFgfIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fgf is less than or equal to DEFAULT_FGF
        defaultCheckinShouldBeFound("fgf.lessThanOrEqual=" + DEFAULT_FGF);

        // Get all the checkinList where fgf is less than or equal to SMALLER_FGF
        defaultCheckinShouldNotBeFound("fgf.lessThanOrEqual=" + SMALLER_FGF);
    }

    @Test
    @Transactional
    void getAllCheckinsByFgfIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fgf is less than DEFAULT_FGF
        defaultCheckinShouldNotBeFound("fgf.lessThan=" + DEFAULT_FGF);

        // Get all the checkinList where fgf is less than UPDATED_FGF
        defaultCheckinShouldBeFound("fgf.lessThan=" + UPDATED_FGF);
    }

    @Test
    @Transactional
    void getAllCheckinsByFgfIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fgf is greater than DEFAULT_FGF
        defaultCheckinShouldNotBeFound("fgf.greaterThan=" + DEFAULT_FGF);

        // Get all the checkinList where fgf is greater than SMALLER_FGF
        defaultCheckinShouldBeFound("fgf.greaterThan=" + SMALLER_FGF);
    }

    @Test
    @Transactional
    void getAllCheckinsByFgxxIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fgxx equals to DEFAULT_FGXX
        defaultCheckinShouldBeFound("fgxx.equals=" + DEFAULT_FGXX);

        // Get all the checkinList where fgxx equals to UPDATED_FGXX
        defaultCheckinShouldNotBeFound("fgxx.equals=" + UPDATED_FGXX);
    }

    @Test
    @Transactional
    void getAllCheckinsByFgxxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fgxx not equals to DEFAULT_FGXX
        defaultCheckinShouldNotBeFound("fgxx.notEquals=" + DEFAULT_FGXX);

        // Get all the checkinList where fgxx not equals to UPDATED_FGXX
        defaultCheckinShouldBeFound("fgxx.notEquals=" + UPDATED_FGXX);
    }

    @Test
    @Transactional
    void getAllCheckinsByFgxxIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fgxx in DEFAULT_FGXX or UPDATED_FGXX
        defaultCheckinShouldBeFound("fgxx.in=" + DEFAULT_FGXX + "," + UPDATED_FGXX);

        // Get all the checkinList where fgxx equals to UPDATED_FGXX
        defaultCheckinShouldNotBeFound("fgxx.in=" + UPDATED_FGXX);
    }

    @Test
    @Transactional
    void getAllCheckinsByFgxxIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fgxx is not null
        defaultCheckinShouldBeFound("fgxx.specified=true");

        // Get all the checkinList where fgxx is null
        defaultCheckinShouldNotBeFound("fgxx.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByFgxxContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fgxx contains DEFAULT_FGXX
        defaultCheckinShouldBeFound("fgxx.contains=" + DEFAULT_FGXX);

        // Get all the checkinList where fgxx contains UPDATED_FGXX
        defaultCheckinShouldNotBeFound("fgxx.contains=" + UPDATED_FGXX);
    }

    @Test
    @Transactional
    void getAllCheckinsByFgxxNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fgxx does not contain DEFAULT_FGXX
        defaultCheckinShouldNotBeFound("fgxx.doesNotContain=" + DEFAULT_FGXX);

        // Get all the checkinList where fgxx does not contain UPDATED_FGXX
        defaultCheckinShouldBeFound("fgxx.doesNotContain=" + UPDATED_FGXX);
    }

    @Test
    @Transactional
    void getAllCheckinsByHourSignIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hourSign equals to DEFAULT_HOUR_SIGN
        defaultCheckinShouldBeFound("hourSign.equals=" + DEFAULT_HOUR_SIGN);

        // Get all the checkinList where hourSign equals to UPDATED_HOUR_SIGN
        defaultCheckinShouldNotBeFound("hourSign.equals=" + UPDATED_HOUR_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByHourSignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hourSign not equals to DEFAULT_HOUR_SIGN
        defaultCheckinShouldNotBeFound("hourSign.notEquals=" + DEFAULT_HOUR_SIGN);

        // Get all the checkinList where hourSign not equals to UPDATED_HOUR_SIGN
        defaultCheckinShouldBeFound("hourSign.notEquals=" + UPDATED_HOUR_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByHourSignIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hourSign in DEFAULT_HOUR_SIGN or UPDATED_HOUR_SIGN
        defaultCheckinShouldBeFound("hourSign.in=" + DEFAULT_HOUR_SIGN + "," + UPDATED_HOUR_SIGN);

        // Get all the checkinList where hourSign equals to UPDATED_HOUR_SIGN
        defaultCheckinShouldNotBeFound("hourSign.in=" + UPDATED_HOUR_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByHourSignIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hourSign is not null
        defaultCheckinShouldBeFound("hourSign.specified=true");

        // Get all the checkinList where hourSign is null
        defaultCheckinShouldNotBeFound("hourSign.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByHourSignIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hourSign is greater than or equal to DEFAULT_HOUR_SIGN
        defaultCheckinShouldBeFound("hourSign.greaterThanOrEqual=" + DEFAULT_HOUR_SIGN);

        // Get all the checkinList where hourSign is greater than or equal to UPDATED_HOUR_SIGN
        defaultCheckinShouldNotBeFound("hourSign.greaterThanOrEqual=" + UPDATED_HOUR_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByHourSignIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hourSign is less than or equal to DEFAULT_HOUR_SIGN
        defaultCheckinShouldBeFound("hourSign.lessThanOrEqual=" + DEFAULT_HOUR_SIGN);

        // Get all the checkinList where hourSign is less than or equal to SMALLER_HOUR_SIGN
        defaultCheckinShouldNotBeFound("hourSign.lessThanOrEqual=" + SMALLER_HOUR_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByHourSignIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hourSign is less than DEFAULT_HOUR_SIGN
        defaultCheckinShouldNotBeFound("hourSign.lessThan=" + DEFAULT_HOUR_SIGN);

        // Get all the checkinList where hourSign is less than UPDATED_HOUR_SIGN
        defaultCheckinShouldBeFound("hourSign.lessThan=" + UPDATED_HOUR_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByHourSignIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where hourSign is greater than DEFAULT_HOUR_SIGN
        defaultCheckinShouldNotBeFound("hourSign.greaterThan=" + DEFAULT_HOUR_SIGN);

        // Get all the checkinList where hourSign is greater than SMALLER_HOUR_SIGN
        defaultCheckinShouldBeFound("hourSign.greaterThan=" + SMALLER_HOUR_SIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByXsyIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where xsy equals to DEFAULT_XSY
        defaultCheckinShouldBeFound("xsy.equals=" + DEFAULT_XSY);

        // Get all the checkinList where xsy equals to UPDATED_XSY
        defaultCheckinShouldNotBeFound("xsy.equals=" + UPDATED_XSY);
    }

    @Test
    @Transactional
    void getAllCheckinsByXsyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where xsy not equals to DEFAULT_XSY
        defaultCheckinShouldNotBeFound("xsy.notEquals=" + DEFAULT_XSY);

        // Get all the checkinList where xsy not equals to UPDATED_XSY
        defaultCheckinShouldBeFound("xsy.notEquals=" + UPDATED_XSY);
    }

    @Test
    @Transactional
    void getAllCheckinsByXsyIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where xsy in DEFAULT_XSY or UPDATED_XSY
        defaultCheckinShouldBeFound("xsy.in=" + DEFAULT_XSY + "," + UPDATED_XSY);

        // Get all the checkinList where xsy equals to UPDATED_XSY
        defaultCheckinShouldNotBeFound("xsy.in=" + UPDATED_XSY);
    }

    @Test
    @Transactional
    void getAllCheckinsByXsyIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where xsy is not null
        defaultCheckinShouldBeFound("xsy.specified=true");

        // Get all the checkinList where xsy is null
        defaultCheckinShouldNotBeFound("xsy.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByXsyContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where xsy contains DEFAULT_XSY
        defaultCheckinShouldBeFound("xsy.contains=" + DEFAULT_XSY);

        // Get all the checkinList where xsy contains UPDATED_XSY
        defaultCheckinShouldNotBeFound("xsy.contains=" + UPDATED_XSY);
    }

    @Test
    @Transactional
    void getAllCheckinsByXsyNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where xsy does not contain DEFAULT_XSY
        defaultCheckinShouldNotBeFound("xsy.doesNotContain=" + DEFAULT_XSY);

        // Get all the checkinList where xsy does not contain UPDATED_XSY
        defaultCheckinShouldBeFound("xsy.doesNotContain=" + UPDATED_XSY);
    }

    @Test
    @Transactional
    void getAllCheckinsByRzsignIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where rzsign equals to DEFAULT_RZSIGN
        defaultCheckinShouldBeFound("rzsign.equals=" + DEFAULT_RZSIGN);

        // Get all the checkinList where rzsign equals to UPDATED_RZSIGN
        defaultCheckinShouldNotBeFound("rzsign.equals=" + UPDATED_RZSIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByRzsignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where rzsign not equals to DEFAULT_RZSIGN
        defaultCheckinShouldNotBeFound("rzsign.notEquals=" + DEFAULT_RZSIGN);

        // Get all the checkinList where rzsign not equals to UPDATED_RZSIGN
        defaultCheckinShouldBeFound("rzsign.notEquals=" + UPDATED_RZSIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByRzsignIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where rzsign in DEFAULT_RZSIGN or UPDATED_RZSIGN
        defaultCheckinShouldBeFound("rzsign.in=" + DEFAULT_RZSIGN + "," + UPDATED_RZSIGN);

        // Get all the checkinList where rzsign equals to UPDATED_RZSIGN
        defaultCheckinShouldNotBeFound("rzsign.in=" + UPDATED_RZSIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByRzsignIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where rzsign is not null
        defaultCheckinShouldBeFound("rzsign.specified=true");

        // Get all the checkinList where rzsign is null
        defaultCheckinShouldNotBeFound("rzsign.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByRzsignIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where rzsign is greater than or equal to DEFAULT_RZSIGN
        defaultCheckinShouldBeFound("rzsign.greaterThanOrEqual=" + DEFAULT_RZSIGN);

        // Get all the checkinList where rzsign is greater than or equal to UPDATED_RZSIGN
        defaultCheckinShouldNotBeFound("rzsign.greaterThanOrEqual=" + UPDATED_RZSIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByRzsignIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where rzsign is less than or equal to DEFAULT_RZSIGN
        defaultCheckinShouldBeFound("rzsign.lessThanOrEqual=" + DEFAULT_RZSIGN);

        // Get all the checkinList where rzsign is less than or equal to SMALLER_RZSIGN
        defaultCheckinShouldNotBeFound("rzsign.lessThanOrEqual=" + SMALLER_RZSIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByRzsignIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where rzsign is less than DEFAULT_RZSIGN
        defaultCheckinShouldNotBeFound("rzsign.lessThan=" + DEFAULT_RZSIGN);

        // Get all the checkinList where rzsign is less than UPDATED_RZSIGN
        defaultCheckinShouldBeFound("rzsign.lessThan=" + UPDATED_RZSIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByRzsignIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where rzsign is greater than DEFAULT_RZSIGN
        defaultCheckinShouldNotBeFound("rzsign.greaterThan=" + DEFAULT_RZSIGN);

        // Get all the checkinList where rzsign is greater than SMALLER_RZSIGN
        defaultCheckinShouldBeFound("rzsign.greaterThan=" + SMALLER_RZSIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByJfIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jf equals to DEFAULT_JF
        defaultCheckinShouldBeFound("jf.equals=" + DEFAULT_JF);

        // Get all the checkinList where jf equals to UPDATED_JF
        defaultCheckinShouldNotBeFound("jf.equals=" + UPDATED_JF);
    }

    @Test
    @Transactional
    void getAllCheckinsByJfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jf not equals to DEFAULT_JF
        defaultCheckinShouldNotBeFound("jf.notEquals=" + DEFAULT_JF);

        // Get all the checkinList where jf not equals to UPDATED_JF
        defaultCheckinShouldBeFound("jf.notEquals=" + UPDATED_JF);
    }

    @Test
    @Transactional
    void getAllCheckinsByJfIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jf in DEFAULT_JF or UPDATED_JF
        defaultCheckinShouldBeFound("jf.in=" + DEFAULT_JF + "," + UPDATED_JF);

        // Get all the checkinList where jf equals to UPDATED_JF
        defaultCheckinShouldNotBeFound("jf.in=" + UPDATED_JF);
    }

    @Test
    @Transactional
    void getAllCheckinsByJfIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jf is not null
        defaultCheckinShouldBeFound("jf.specified=true");

        // Get all the checkinList where jf is null
        defaultCheckinShouldNotBeFound("jf.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByJfIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jf is greater than or equal to DEFAULT_JF
        defaultCheckinShouldBeFound("jf.greaterThanOrEqual=" + DEFAULT_JF);

        // Get all the checkinList where jf is greater than or equal to UPDATED_JF
        defaultCheckinShouldNotBeFound("jf.greaterThanOrEqual=" + UPDATED_JF);
    }

    @Test
    @Transactional
    void getAllCheckinsByJfIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jf is less than or equal to DEFAULT_JF
        defaultCheckinShouldBeFound("jf.lessThanOrEqual=" + DEFAULT_JF);

        // Get all the checkinList where jf is less than or equal to SMALLER_JF
        defaultCheckinShouldNotBeFound("jf.lessThanOrEqual=" + SMALLER_JF);
    }

    @Test
    @Transactional
    void getAllCheckinsByJfIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jf is less than DEFAULT_JF
        defaultCheckinShouldNotBeFound("jf.lessThan=" + DEFAULT_JF);

        // Get all the checkinList where jf is less than UPDATED_JF
        defaultCheckinShouldBeFound("jf.lessThan=" + UPDATED_JF);
    }

    @Test
    @Transactional
    void getAllCheckinsByJfIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jf is greater than DEFAULT_JF
        defaultCheckinShouldNotBeFound("jf.greaterThan=" + DEFAULT_JF);

        // Get all the checkinList where jf is greater than SMALLER_JF
        defaultCheckinShouldBeFound("jf.greaterThan=" + SMALLER_JF);
    }

    @Test
    @Transactional
    void getAllCheckinsByGnameIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where gname equals to DEFAULT_GNAME
        defaultCheckinShouldBeFound("gname.equals=" + DEFAULT_GNAME);

        // Get all the checkinList where gname equals to UPDATED_GNAME
        defaultCheckinShouldNotBeFound("gname.equals=" + UPDATED_GNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByGnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where gname not equals to DEFAULT_GNAME
        defaultCheckinShouldNotBeFound("gname.notEquals=" + DEFAULT_GNAME);

        // Get all the checkinList where gname not equals to UPDATED_GNAME
        defaultCheckinShouldBeFound("gname.notEquals=" + UPDATED_GNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByGnameIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where gname in DEFAULT_GNAME or UPDATED_GNAME
        defaultCheckinShouldBeFound("gname.in=" + DEFAULT_GNAME + "," + UPDATED_GNAME);

        // Get all the checkinList where gname equals to UPDATED_GNAME
        defaultCheckinShouldNotBeFound("gname.in=" + UPDATED_GNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByGnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where gname is not null
        defaultCheckinShouldBeFound("gname.specified=true");

        // Get all the checkinList where gname is null
        defaultCheckinShouldNotBeFound("gname.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByGnameContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where gname contains DEFAULT_GNAME
        defaultCheckinShouldBeFound("gname.contains=" + DEFAULT_GNAME);

        // Get all the checkinList where gname contains UPDATED_GNAME
        defaultCheckinShouldNotBeFound("gname.contains=" + UPDATED_GNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByGnameNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where gname does not contain DEFAULT_GNAME
        defaultCheckinShouldNotBeFound("gname.doesNotContain=" + DEFAULT_GNAME);

        // Get all the checkinList where gname does not contain UPDATED_GNAME
        defaultCheckinShouldBeFound("gname.doesNotContain=" + UPDATED_GNAME);
    }

    @Test
    @Transactional
    void getAllCheckinsByZcsignIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where zcsign equals to DEFAULT_ZCSIGN
        defaultCheckinShouldBeFound("zcsign.equals=" + DEFAULT_ZCSIGN);

        // Get all the checkinList where zcsign equals to UPDATED_ZCSIGN
        defaultCheckinShouldNotBeFound("zcsign.equals=" + UPDATED_ZCSIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByZcsignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where zcsign not equals to DEFAULT_ZCSIGN
        defaultCheckinShouldNotBeFound("zcsign.notEquals=" + DEFAULT_ZCSIGN);

        // Get all the checkinList where zcsign not equals to UPDATED_ZCSIGN
        defaultCheckinShouldBeFound("zcsign.notEquals=" + UPDATED_ZCSIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByZcsignIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where zcsign in DEFAULT_ZCSIGN or UPDATED_ZCSIGN
        defaultCheckinShouldBeFound("zcsign.in=" + DEFAULT_ZCSIGN + "," + UPDATED_ZCSIGN);

        // Get all the checkinList where zcsign equals to UPDATED_ZCSIGN
        defaultCheckinShouldNotBeFound("zcsign.in=" + UPDATED_ZCSIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByZcsignIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where zcsign is not null
        defaultCheckinShouldBeFound("zcsign.specified=true");

        // Get all the checkinList where zcsign is null
        defaultCheckinShouldNotBeFound("zcsign.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByZcsignIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where zcsign is greater than or equal to DEFAULT_ZCSIGN
        defaultCheckinShouldBeFound("zcsign.greaterThanOrEqual=" + DEFAULT_ZCSIGN);

        // Get all the checkinList where zcsign is greater than or equal to UPDATED_ZCSIGN
        defaultCheckinShouldNotBeFound("zcsign.greaterThanOrEqual=" + UPDATED_ZCSIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByZcsignIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where zcsign is less than or equal to DEFAULT_ZCSIGN
        defaultCheckinShouldBeFound("zcsign.lessThanOrEqual=" + DEFAULT_ZCSIGN);

        // Get all the checkinList where zcsign is less than or equal to SMALLER_ZCSIGN
        defaultCheckinShouldNotBeFound("zcsign.lessThanOrEqual=" + SMALLER_ZCSIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByZcsignIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where zcsign is less than DEFAULT_ZCSIGN
        defaultCheckinShouldNotBeFound("zcsign.lessThan=" + DEFAULT_ZCSIGN);

        // Get all the checkinList where zcsign is less than UPDATED_ZCSIGN
        defaultCheckinShouldBeFound("zcsign.lessThan=" + UPDATED_ZCSIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByZcsignIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where zcsign is greater than DEFAULT_ZCSIGN
        defaultCheckinShouldNotBeFound("zcsign.greaterThan=" + DEFAULT_ZCSIGN);

        // Get all the checkinList where zcsign is greater than SMALLER_ZCSIGN
        defaultCheckinShouldBeFound("zcsign.greaterThan=" + SMALLER_ZCSIGN);
    }

    @Test
    @Transactional
    void getAllCheckinsByCqslIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where cqsl equals to DEFAULT_CQSL
        defaultCheckinShouldBeFound("cqsl.equals=" + DEFAULT_CQSL);

        // Get all the checkinList where cqsl equals to UPDATED_CQSL
        defaultCheckinShouldNotBeFound("cqsl.equals=" + UPDATED_CQSL);
    }

    @Test
    @Transactional
    void getAllCheckinsByCqslIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where cqsl not equals to DEFAULT_CQSL
        defaultCheckinShouldNotBeFound("cqsl.notEquals=" + DEFAULT_CQSL);

        // Get all the checkinList where cqsl not equals to UPDATED_CQSL
        defaultCheckinShouldBeFound("cqsl.notEquals=" + UPDATED_CQSL);
    }

    @Test
    @Transactional
    void getAllCheckinsByCqslIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where cqsl in DEFAULT_CQSL or UPDATED_CQSL
        defaultCheckinShouldBeFound("cqsl.in=" + DEFAULT_CQSL + "," + UPDATED_CQSL);

        // Get all the checkinList where cqsl equals to UPDATED_CQSL
        defaultCheckinShouldNotBeFound("cqsl.in=" + UPDATED_CQSL);
    }

    @Test
    @Transactional
    void getAllCheckinsByCqslIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where cqsl is not null
        defaultCheckinShouldBeFound("cqsl.specified=true");

        // Get all the checkinList where cqsl is null
        defaultCheckinShouldNotBeFound("cqsl.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByCqslIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where cqsl is greater than or equal to DEFAULT_CQSL
        defaultCheckinShouldBeFound("cqsl.greaterThanOrEqual=" + DEFAULT_CQSL);

        // Get all the checkinList where cqsl is greater than or equal to UPDATED_CQSL
        defaultCheckinShouldNotBeFound("cqsl.greaterThanOrEqual=" + UPDATED_CQSL);
    }

    @Test
    @Transactional
    void getAllCheckinsByCqslIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where cqsl is less than or equal to DEFAULT_CQSL
        defaultCheckinShouldBeFound("cqsl.lessThanOrEqual=" + DEFAULT_CQSL);

        // Get all the checkinList where cqsl is less than or equal to SMALLER_CQSL
        defaultCheckinShouldNotBeFound("cqsl.lessThanOrEqual=" + SMALLER_CQSL);
    }

    @Test
    @Transactional
    void getAllCheckinsByCqslIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where cqsl is less than DEFAULT_CQSL
        defaultCheckinShouldNotBeFound("cqsl.lessThan=" + DEFAULT_CQSL);

        // Get all the checkinList where cqsl is less than UPDATED_CQSL
        defaultCheckinShouldBeFound("cqsl.lessThan=" + UPDATED_CQSL);
    }

    @Test
    @Transactional
    void getAllCheckinsByCqslIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where cqsl is greater than DEFAULT_CQSL
        defaultCheckinShouldNotBeFound("cqsl.greaterThan=" + DEFAULT_CQSL);

        // Get all the checkinList where cqsl is greater than SMALLER_CQSL
        defaultCheckinShouldBeFound("cqsl.greaterThan=" + SMALLER_CQSL);
    }

    @Test
    @Transactional
    void getAllCheckinsBySfjfIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where sfjf equals to DEFAULT_SFJF
        defaultCheckinShouldBeFound("sfjf.equals=" + DEFAULT_SFJF);

        // Get all the checkinList where sfjf equals to UPDATED_SFJF
        defaultCheckinShouldNotBeFound("sfjf.equals=" + UPDATED_SFJF);
    }

    @Test
    @Transactional
    void getAllCheckinsBySfjfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where sfjf not equals to DEFAULT_SFJF
        defaultCheckinShouldNotBeFound("sfjf.notEquals=" + DEFAULT_SFJF);

        // Get all the checkinList where sfjf not equals to UPDATED_SFJF
        defaultCheckinShouldBeFound("sfjf.notEquals=" + UPDATED_SFJF);
    }

    @Test
    @Transactional
    void getAllCheckinsBySfjfIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where sfjf in DEFAULT_SFJF or UPDATED_SFJF
        defaultCheckinShouldBeFound("sfjf.in=" + DEFAULT_SFJF + "," + UPDATED_SFJF);

        // Get all the checkinList where sfjf equals to UPDATED_SFJF
        defaultCheckinShouldNotBeFound("sfjf.in=" + UPDATED_SFJF);
    }

    @Test
    @Transactional
    void getAllCheckinsBySfjfIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where sfjf is not null
        defaultCheckinShouldBeFound("sfjf.specified=true");

        // Get all the checkinList where sfjf is null
        defaultCheckinShouldNotBeFound("sfjf.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsBySfjfIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where sfjf is greater than or equal to DEFAULT_SFJF
        defaultCheckinShouldBeFound("sfjf.greaterThanOrEqual=" + DEFAULT_SFJF);

        // Get all the checkinList where sfjf is greater than or equal to UPDATED_SFJF
        defaultCheckinShouldNotBeFound("sfjf.greaterThanOrEqual=" + UPDATED_SFJF);
    }

    @Test
    @Transactional
    void getAllCheckinsBySfjfIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where sfjf is less than or equal to DEFAULT_SFJF
        defaultCheckinShouldBeFound("sfjf.lessThanOrEqual=" + DEFAULT_SFJF);

        // Get all the checkinList where sfjf is less than or equal to SMALLER_SFJF
        defaultCheckinShouldNotBeFound("sfjf.lessThanOrEqual=" + SMALLER_SFJF);
    }

    @Test
    @Transactional
    void getAllCheckinsBySfjfIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where sfjf is less than DEFAULT_SFJF
        defaultCheckinShouldNotBeFound("sfjf.lessThan=" + DEFAULT_SFJF);

        // Get all the checkinList where sfjf is less than UPDATED_SFJF
        defaultCheckinShouldBeFound("sfjf.lessThan=" + UPDATED_SFJF);
    }

    @Test
    @Transactional
    void getAllCheckinsBySfjfIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where sfjf is greater than DEFAULT_SFJF
        defaultCheckinShouldNotBeFound("sfjf.greaterThan=" + DEFAULT_SFJF);

        // Get all the checkinList where sfjf is greater than SMALLER_SFJF
        defaultCheckinShouldBeFound("sfjf.greaterThan=" + SMALLER_SFJF);
    }

    @Test
    @Transactional
    void getAllCheckinsByYwlyIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where ywly equals to DEFAULT_YWLY
        defaultCheckinShouldBeFound("ywly.equals=" + DEFAULT_YWLY);

        // Get all the checkinList where ywly equals to UPDATED_YWLY
        defaultCheckinShouldNotBeFound("ywly.equals=" + UPDATED_YWLY);
    }

    @Test
    @Transactional
    void getAllCheckinsByYwlyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where ywly not equals to DEFAULT_YWLY
        defaultCheckinShouldNotBeFound("ywly.notEquals=" + DEFAULT_YWLY);

        // Get all the checkinList where ywly not equals to UPDATED_YWLY
        defaultCheckinShouldBeFound("ywly.notEquals=" + UPDATED_YWLY);
    }

    @Test
    @Transactional
    void getAllCheckinsByYwlyIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where ywly in DEFAULT_YWLY or UPDATED_YWLY
        defaultCheckinShouldBeFound("ywly.in=" + DEFAULT_YWLY + "," + UPDATED_YWLY);

        // Get all the checkinList where ywly equals to UPDATED_YWLY
        defaultCheckinShouldNotBeFound("ywly.in=" + UPDATED_YWLY);
    }

    @Test
    @Transactional
    void getAllCheckinsByYwlyIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where ywly is not null
        defaultCheckinShouldBeFound("ywly.specified=true");

        // Get all the checkinList where ywly is null
        defaultCheckinShouldNotBeFound("ywly.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByYwlyContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where ywly contains DEFAULT_YWLY
        defaultCheckinShouldBeFound("ywly.contains=" + DEFAULT_YWLY);

        // Get all the checkinList where ywly contains UPDATED_YWLY
        defaultCheckinShouldNotBeFound("ywly.contains=" + UPDATED_YWLY);
    }

    @Test
    @Transactional
    void getAllCheckinsByYwlyNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where ywly does not contain DEFAULT_YWLY
        defaultCheckinShouldNotBeFound("ywly.doesNotContain=" + DEFAULT_YWLY);

        // Get all the checkinList where ywly does not contain UPDATED_YWLY
        defaultCheckinShouldBeFound("ywly.doesNotContain=" + UPDATED_YWLY);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fk equals to DEFAULT_FK
        defaultCheckinShouldBeFound("fk.equals=" + DEFAULT_FK);

        // Get all the checkinList where fk equals to UPDATED_FK
        defaultCheckinShouldNotBeFound("fk.equals=" + UPDATED_FK);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fk not equals to DEFAULT_FK
        defaultCheckinShouldNotBeFound("fk.notEquals=" + DEFAULT_FK);

        // Get all the checkinList where fk not equals to UPDATED_FK
        defaultCheckinShouldBeFound("fk.notEquals=" + UPDATED_FK);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fk in DEFAULT_FK or UPDATED_FK
        defaultCheckinShouldBeFound("fk.in=" + DEFAULT_FK + "," + UPDATED_FK);

        // Get all the checkinList where fk equals to UPDATED_FK
        defaultCheckinShouldNotBeFound("fk.in=" + UPDATED_FK);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fk is not null
        defaultCheckinShouldBeFound("fk.specified=true");

        // Get all the checkinList where fk is null
        defaultCheckinShouldNotBeFound("fk.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByFkContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fk contains DEFAULT_FK
        defaultCheckinShouldBeFound("fk.contains=" + DEFAULT_FK);

        // Get all the checkinList where fk contains UPDATED_FK
        defaultCheckinShouldNotBeFound("fk.contains=" + UPDATED_FK);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fk does not contain DEFAULT_FK
        defaultCheckinShouldNotBeFound("fk.doesNotContain=" + DEFAULT_FK);

        // Get all the checkinList where fk does not contain UPDATED_FK
        defaultCheckinShouldBeFound("fk.doesNotContain=" + UPDATED_FK);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkrqIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fkrq equals to DEFAULT_FKRQ
        defaultCheckinShouldBeFound("fkrq.equals=" + DEFAULT_FKRQ);

        // Get all the checkinList where fkrq equals to UPDATED_FKRQ
        defaultCheckinShouldNotBeFound("fkrq.equals=" + UPDATED_FKRQ);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fkrq not equals to DEFAULT_FKRQ
        defaultCheckinShouldNotBeFound("fkrq.notEquals=" + DEFAULT_FKRQ);

        // Get all the checkinList where fkrq not equals to UPDATED_FKRQ
        defaultCheckinShouldBeFound("fkrq.notEquals=" + UPDATED_FKRQ);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkrqIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fkrq in DEFAULT_FKRQ or UPDATED_FKRQ
        defaultCheckinShouldBeFound("fkrq.in=" + DEFAULT_FKRQ + "," + UPDATED_FKRQ);

        // Get all the checkinList where fkrq equals to UPDATED_FKRQ
        defaultCheckinShouldNotBeFound("fkrq.in=" + UPDATED_FKRQ);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fkrq is not null
        defaultCheckinShouldBeFound("fkrq.specified=true");

        // Get all the checkinList where fkrq is null
        defaultCheckinShouldNotBeFound("fkrq.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByBcIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bc equals to DEFAULT_BC
        defaultCheckinShouldBeFound("bc.equals=" + DEFAULT_BC);

        // Get all the checkinList where bc equals to UPDATED_BC
        defaultCheckinShouldNotBeFound("bc.equals=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllCheckinsByBcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bc not equals to DEFAULT_BC
        defaultCheckinShouldNotBeFound("bc.notEquals=" + DEFAULT_BC);

        // Get all the checkinList where bc not equals to UPDATED_BC
        defaultCheckinShouldBeFound("bc.notEquals=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllCheckinsByBcIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bc in DEFAULT_BC or UPDATED_BC
        defaultCheckinShouldBeFound("bc.in=" + DEFAULT_BC + "," + UPDATED_BC);

        // Get all the checkinList where bc equals to UPDATED_BC
        defaultCheckinShouldNotBeFound("bc.in=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllCheckinsByBcIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bc is not null
        defaultCheckinShouldBeFound("bc.specified=true");

        // Get all the checkinList where bc is null
        defaultCheckinShouldNotBeFound("bc.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByBcContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bc contains DEFAULT_BC
        defaultCheckinShouldBeFound("bc.contains=" + DEFAULT_BC);

        // Get all the checkinList where bc contains UPDATED_BC
        defaultCheckinShouldNotBeFound("bc.contains=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllCheckinsByBcNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where bc does not contain DEFAULT_BC
        defaultCheckinShouldNotBeFound("bc.doesNotContain=" + DEFAULT_BC);

        // Get all the checkinList where bc does not contain UPDATED_BC
        defaultCheckinShouldBeFound("bc.doesNotContain=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxremarkIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxremark equals to DEFAULT_JXREMARK
        defaultCheckinShouldBeFound("jxremark.equals=" + DEFAULT_JXREMARK);

        // Get all the checkinList where jxremark equals to UPDATED_JXREMARK
        defaultCheckinShouldNotBeFound("jxremark.equals=" + UPDATED_JXREMARK);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxremarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxremark not equals to DEFAULT_JXREMARK
        defaultCheckinShouldNotBeFound("jxremark.notEquals=" + DEFAULT_JXREMARK);

        // Get all the checkinList where jxremark not equals to UPDATED_JXREMARK
        defaultCheckinShouldBeFound("jxremark.notEquals=" + UPDATED_JXREMARK);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxremarkIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxremark in DEFAULT_JXREMARK or UPDATED_JXREMARK
        defaultCheckinShouldBeFound("jxremark.in=" + DEFAULT_JXREMARK + "," + UPDATED_JXREMARK);

        // Get all the checkinList where jxremark equals to UPDATED_JXREMARK
        defaultCheckinShouldNotBeFound("jxremark.in=" + UPDATED_JXREMARK);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxremarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxremark is not null
        defaultCheckinShouldBeFound("jxremark.specified=true");

        // Get all the checkinList where jxremark is null
        defaultCheckinShouldNotBeFound("jxremark.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByJxremarkContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxremark contains DEFAULT_JXREMARK
        defaultCheckinShouldBeFound("jxremark.contains=" + DEFAULT_JXREMARK);

        // Get all the checkinList where jxremark contains UPDATED_JXREMARK
        defaultCheckinShouldNotBeFound("jxremark.contains=" + UPDATED_JXREMARK);
    }

    @Test
    @Transactional
    void getAllCheckinsByJxremarkNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where jxremark does not contain DEFAULT_JXREMARK
        defaultCheckinShouldNotBeFound("jxremark.doesNotContain=" + DEFAULT_JXREMARK);

        // Get all the checkinList where jxremark does not contain UPDATED_JXREMARK
        defaultCheckinShouldBeFound("jxremark.doesNotContain=" + UPDATED_JXREMARK);
    }

    @Test
    @Transactional
    void getAllCheckinsByTxidIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where txid equals to DEFAULT_TXID
        defaultCheckinShouldBeFound("txid.equals=" + DEFAULT_TXID);

        // Get all the checkinList where txid equals to UPDATED_TXID
        defaultCheckinShouldNotBeFound("txid.equals=" + UPDATED_TXID);
    }

    @Test
    @Transactional
    void getAllCheckinsByTxidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where txid not equals to DEFAULT_TXID
        defaultCheckinShouldNotBeFound("txid.notEquals=" + DEFAULT_TXID);

        // Get all the checkinList where txid not equals to UPDATED_TXID
        defaultCheckinShouldBeFound("txid.notEquals=" + UPDATED_TXID);
    }

    @Test
    @Transactional
    void getAllCheckinsByTxidIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where txid in DEFAULT_TXID or UPDATED_TXID
        defaultCheckinShouldBeFound("txid.in=" + DEFAULT_TXID + "," + UPDATED_TXID);

        // Get all the checkinList where txid equals to UPDATED_TXID
        defaultCheckinShouldNotBeFound("txid.in=" + UPDATED_TXID);
    }

    @Test
    @Transactional
    void getAllCheckinsByTxidIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where txid is not null
        defaultCheckinShouldBeFound("txid.specified=true");

        // Get all the checkinList where txid is null
        defaultCheckinShouldNotBeFound("txid.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByTxidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where txid is greater than or equal to DEFAULT_TXID
        defaultCheckinShouldBeFound("txid.greaterThanOrEqual=" + DEFAULT_TXID);

        // Get all the checkinList where txid is greater than or equal to UPDATED_TXID
        defaultCheckinShouldNotBeFound("txid.greaterThanOrEqual=" + UPDATED_TXID);
    }

    @Test
    @Transactional
    void getAllCheckinsByTxidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where txid is less than or equal to DEFAULT_TXID
        defaultCheckinShouldBeFound("txid.lessThanOrEqual=" + DEFAULT_TXID);

        // Get all the checkinList where txid is less than or equal to SMALLER_TXID
        defaultCheckinShouldNotBeFound("txid.lessThanOrEqual=" + SMALLER_TXID);
    }

    @Test
    @Transactional
    void getAllCheckinsByTxidIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where txid is less than DEFAULT_TXID
        defaultCheckinShouldNotBeFound("txid.lessThan=" + DEFAULT_TXID);

        // Get all the checkinList where txid is less than UPDATED_TXID
        defaultCheckinShouldBeFound("txid.lessThan=" + UPDATED_TXID);
    }

    @Test
    @Transactional
    void getAllCheckinsByTxidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where txid is greater than DEFAULT_TXID
        defaultCheckinShouldNotBeFound("txid.greaterThan=" + DEFAULT_TXID);

        // Get all the checkinList where txid is greater than SMALLER_TXID
        defaultCheckinShouldBeFound("txid.greaterThan=" + SMALLER_TXID);
    }

    @Test
    @Transactional
    void getAllCheckinsByCfrIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where cfr equals to DEFAULT_CFR
        defaultCheckinShouldBeFound("cfr.equals=" + DEFAULT_CFR);

        // Get all the checkinList where cfr equals to UPDATED_CFR
        defaultCheckinShouldNotBeFound("cfr.equals=" + UPDATED_CFR);
    }

    @Test
    @Transactional
    void getAllCheckinsByCfrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where cfr not equals to DEFAULT_CFR
        defaultCheckinShouldNotBeFound("cfr.notEquals=" + DEFAULT_CFR);

        // Get all the checkinList where cfr not equals to UPDATED_CFR
        defaultCheckinShouldBeFound("cfr.notEquals=" + UPDATED_CFR);
    }

    @Test
    @Transactional
    void getAllCheckinsByCfrIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where cfr in DEFAULT_CFR or UPDATED_CFR
        defaultCheckinShouldBeFound("cfr.in=" + DEFAULT_CFR + "," + UPDATED_CFR);

        // Get all the checkinList where cfr equals to UPDATED_CFR
        defaultCheckinShouldNotBeFound("cfr.in=" + UPDATED_CFR);
    }

    @Test
    @Transactional
    void getAllCheckinsByCfrIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where cfr is not null
        defaultCheckinShouldBeFound("cfr.specified=true");

        // Get all the checkinList where cfr is null
        defaultCheckinShouldNotBeFound("cfr.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByCfrContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where cfr contains DEFAULT_CFR
        defaultCheckinShouldBeFound("cfr.contains=" + DEFAULT_CFR);

        // Get all the checkinList where cfr contains UPDATED_CFR
        defaultCheckinShouldNotBeFound("cfr.contains=" + UPDATED_CFR);
    }

    @Test
    @Transactional
    void getAllCheckinsByCfrNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where cfr does not contain DEFAULT_CFR
        defaultCheckinShouldNotBeFound("cfr.doesNotContain=" + DEFAULT_CFR);

        // Get all the checkinList where cfr does not contain UPDATED_CFR
        defaultCheckinShouldBeFound("cfr.doesNotContain=" + UPDATED_CFR);
    }

    @Test
    @Transactional
    void getAllCheckinsByFjbmIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fjbm equals to DEFAULT_FJBM
        defaultCheckinShouldBeFound("fjbm.equals=" + DEFAULT_FJBM);

        // Get all the checkinList where fjbm equals to UPDATED_FJBM
        defaultCheckinShouldNotBeFound("fjbm.equals=" + UPDATED_FJBM);
    }

    @Test
    @Transactional
    void getAllCheckinsByFjbmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fjbm not equals to DEFAULT_FJBM
        defaultCheckinShouldNotBeFound("fjbm.notEquals=" + DEFAULT_FJBM);

        // Get all the checkinList where fjbm not equals to UPDATED_FJBM
        defaultCheckinShouldBeFound("fjbm.notEquals=" + UPDATED_FJBM);
    }

    @Test
    @Transactional
    void getAllCheckinsByFjbmIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fjbm in DEFAULT_FJBM or UPDATED_FJBM
        defaultCheckinShouldBeFound("fjbm.in=" + DEFAULT_FJBM + "," + UPDATED_FJBM);

        // Get all the checkinList where fjbm equals to UPDATED_FJBM
        defaultCheckinShouldNotBeFound("fjbm.in=" + UPDATED_FJBM);
    }

    @Test
    @Transactional
    void getAllCheckinsByFjbmIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fjbm is not null
        defaultCheckinShouldBeFound("fjbm.specified=true");

        // Get all the checkinList where fjbm is null
        defaultCheckinShouldNotBeFound("fjbm.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByFjbmContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fjbm contains DEFAULT_FJBM
        defaultCheckinShouldBeFound("fjbm.contains=" + DEFAULT_FJBM);

        // Get all the checkinList where fjbm contains UPDATED_FJBM
        defaultCheckinShouldNotBeFound("fjbm.contains=" + UPDATED_FJBM);
    }

    @Test
    @Transactional
    void getAllCheckinsByFjbmNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fjbm does not contain DEFAULT_FJBM
        defaultCheckinShouldNotBeFound("fjbm.doesNotContain=" + DEFAULT_FJBM);

        // Get all the checkinList where fjbm does not contain UPDATED_FJBM
        defaultCheckinShouldBeFound("fjbm.doesNotContain=" + UPDATED_FJBM);
    }

    @Test
    @Transactional
    void getAllCheckinsByDjlxIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where djlx equals to DEFAULT_DJLX
        defaultCheckinShouldBeFound("djlx.equals=" + DEFAULT_DJLX);

        // Get all the checkinList where djlx equals to UPDATED_DJLX
        defaultCheckinShouldNotBeFound("djlx.equals=" + UPDATED_DJLX);
    }

    @Test
    @Transactional
    void getAllCheckinsByDjlxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where djlx not equals to DEFAULT_DJLX
        defaultCheckinShouldNotBeFound("djlx.notEquals=" + DEFAULT_DJLX);

        // Get all the checkinList where djlx not equals to UPDATED_DJLX
        defaultCheckinShouldBeFound("djlx.notEquals=" + UPDATED_DJLX);
    }

    @Test
    @Transactional
    void getAllCheckinsByDjlxIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where djlx in DEFAULT_DJLX or UPDATED_DJLX
        defaultCheckinShouldBeFound("djlx.in=" + DEFAULT_DJLX + "," + UPDATED_DJLX);

        // Get all the checkinList where djlx equals to UPDATED_DJLX
        defaultCheckinShouldNotBeFound("djlx.in=" + UPDATED_DJLX);
    }

    @Test
    @Transactional
    void getAllCheckinsByDjlxIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where djlx is not null
        defaultCheckinShouldBeFound("djlx.specified=true");

        // Get all the checkinList where djlx is null
        defaultCheckinShouldNotBeFound("djlx.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByDjlxContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where djlx contains DEFAULT_DJLX
        defaultCheckinShouldBeFound("djlx.contains=" + DEFAULT_DJLX);

        // Get all the checkinList where djlx contains UPDATED_DJLX
        defaultCheckinShouldNotBeFound("djlx.contains=" + UPDATED_DJLX);
    }

    @Test
    @Transactional
    void getAllCheckinsByDjlxNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where djlx does not contain DEFAULT_DJLX
        defaultCheckinShouldNotBeFound("djlx.doesNotContain=" + DEFAULT_DJLX);

        // Get all the checkinList where djlx does not contain UPDATED_DJLX
        defaultCheckinShouldBeFound("djlx.doesNotContain=" + UPDATED_DJLX);
    }

    @Test
    @Transactional
    void getAllCheckinsByWlddhIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where wlddh equals to DEFAULT_WLDDH
        defaultCheckinShouldBeFound("wlddh.equals=" + DEFAULT_WLDDH);

        // Get all the checkinList where wlddh equals to UPDATED_WLDDH
        defaultCheckinShouldNotBeFound("wlddh.equals=" + UPDATED_WLDDH);
    }

    @Test
    @Transactional
    void getAllCheckinsByWlddhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where wlddh not equals to DEFAULT_WLDDH
        defaultCheckinShouldNotBeFound("wlddh.notEquals=" + DEFAULT_WLDDH);

        // Get all the checkinList where wlddh not equals to UPDATED_WLDDH
        defaultCheckinShouldBeFound("wlddh.notEquals=" + UPDATED_WLDDH);
    }

    @Test
    @Transactional
    void getAllCheckinsByWlddhIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where wlddh in DEFAULT_WLDDH or UPDATED_WLDDH
        defaultCheckinShouldBeFound("wlddh.in=" + DEFAULT_WLDDH + "," + UPDATED_WLDDH);

        // Get all the checkinList where wlddh equals to UPDATED_WLDDH
        defaultCheckinShouldNotBeFound("wlddh.in=" + UPDATED_WLDDH);
    }

    @Test
    @Transactional
    void getAllCheckinsByWlddhIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where wlddh is not null
        defaultCheckinShouldBeFound("wlddh.specified=true");

        // Get all the checkinList where wlddh is null
        defaultCheckinShouldNotBeFound("wlddh.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByWlddhContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where wlddh contains DEFAULT_WLDDH
        defaultCheckinShouldBeFound("wlddh.contains=" + DEFAULT_WLDDH);

        // Get all the checkinList where wlddh contains UPDATED_WLDDH
        defaultCheckinShouldNotBeFound("wlddh.contains=" + UPDATED_WLDDH);
    }

    @Test
    @Transactional
    void getAllCheckinsByWlddhNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where wlddh does not contain DEFAULT_WLDDH
        defaultCheckinShouldNotBeFound("wlddh.doesNotContain=" + DEFAULT_WLDDH);

        // Get all the checkinList where wlddh does not contain UPDATED_WLDDH
        defaultCheckinShouldBeFound("wlddh.doesNotContain=" + UPDATED_WLDDH);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkslIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fksl equals to DEFAULT_FKSL
        defaultCheckinShouldBeFound("fksl.equals=" + DEFAULT_FKSL);

        // Get all the checkinList where fksl equals to UPDATED_FKSL
        defaultCheckinShouldNotBeFound("fksl.equals=" + UPDATED_FKSL);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkslIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fksl not equals to DEFAULT_FKSL
        defaultCheckinShouldNotBeFound("fksl.notEquals=" + DEFAULT_FKSL);

        // Get all the checkinList where fksl not equals to UPDATED_FKSL
        defaultCheckinShouldBeFound("fksl.notEquals=" + UPDATED_FKSL);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkslIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fksl in DEFAULT_FKSL or UPDATED_FKSL
        defaultCheckinShouldBeFound("fksl.in=" + DEFAULT_FKSL + "," + UPDATED_FKSL);

        // Get all the checkinList where fksl equals to UPDATED_FKSL
        defaultCheckinShouldNotBeFound("fksl.in=" + UPDATED_FKSL);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkslIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fksl is not null
        defaultCheckinShouldBeFound("fksl.specified=true");

        // Get all the checkinList where fksl is null
        defaultCheckinShouldNotBeFound("fksl.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByFkslIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fksl is greater than or equal to DEFAULT_FKSL
        defaultCheckinShouldBeFound("fksl.greaterThanOrEqual=" + DEFAULT_FKSL);

        // Get all the checkinList where fksl is greater than or equal to UPDATED_FKSL
        defaultCheckinShouldNotBeFound("fksl.greaterThanOrEqual=" + UPDATED_FKSL);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkslIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fksl is less than or equal to DEFAULT_FKSL
        defaultCheckinShouldBeFound("fksl.lessThanOrEqual=" + DEFAULT_FKSL);

        // Get all the checkinList where fksl is less than or equal to SMALLER_FKSL
        defaultCheckinShouldNotBeFound("fksl.lessThanOrEqual=" + SMALLER_FKSL);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkslIsLessThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fksl is less than DEFAULT_FKSL
        defaultCheckinShouldNotBeFound("fksl.lessThan=" + DEFAULT_FKSL);

        // Get all the checkinList where fksl is less than UPDATED_FKSL
        defaultCheckinShouldBeFound("fksl.lessThan=" + UPDATED_FKSL);
    }

    @Test
    @Transactional
    void getAllCheckinsByFkslIsGreaterThanSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where fksl is greater than DEFAULT_FKSL
        defaultCheckinShouldNotBeFound("fksl.greaterThan=" + DEFAULT_FKSL);

        // Get all the checkinList where fksl is greater than SMALLER_FKSL
        defaultCheckinShouldBeFound("fksl.greaterThan=" + SMALLER_FKSL);
    }

    @Test
    @Transactional
    void getAllCheckinsByDqtxIsEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where dqtx equals to DEFAULT_DQTX
        defaultCheckinShouldBeFound("dqtx.equals=" + DEFAULT_DQTX);

        // Get all the checkinList where dqtx equals to UPDATED_DQTX
        defaultCheckinShouldNotBeFound("dqtx.equals=" + UPDATED_DQTX);
    }

    @Test
    @Transactional
    void getAllCheckinsByDqtxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where dqtx not equals to DEFAULT_DQTX
        defaultCheckinShouldNotBeFound("dqtx.notEquals=" + DEFAULT_DQTX);

        // Get all the checkinList where dqtx not equals to UPDATED_DQTX
        defaultCheckinShouldBeFound("dqtx.notEquals=" + UPDATED_DQTX);
    }

    @Test
    @Transactional
    void getAllCheckinsByDqtxIsInShouldWork() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where dqtx in DEFAULT_DQTX or UPDATED_DQTX
        defaultCheckinShouldBeFound("dqtx.in=" + DEFAULT_DQTX + "," + UPDATED_DQTX);

        // Get all the checkinList where dqtx equals to UPDATED_DQTX
        defaultCheckinShouldNotBeFound("dqtx.in=" + UPDATED_DQTX);
    }

    @Test
    @Transactional
    void getAllCheckinsByDqtxIsNullOrNotNull() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where dqtx is not null
        defaultCheckinShouldBeFound("dqtx.specified=true");

        // Get all the checkinList where dqtx is null
        defaultCheckinShouldNotBeFound("dqtx.specified=false");
    }

    @Test
    @Transactional
    void getAllCheckinsByDqtxContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where dqtx contains DEFAULT_DQTX
        defaultCheckinShouldBeFound("dqtx.contains=" + DEFAULT_DQTX);

        // Get all the checkinList where dqtx contains UPDATED_DQTX
        defaultCheckinShouldNotBeFound("dqtx.contains=" + UPDATED_DQTX);
    }

    @Test
    @Transactional
    void getAllCheckinsByDqtxNotContainsSomething() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        // Get all the checkinList where dqtx does not contain DEFAULT_DQTX
        defaultCheckinShouldNotBeFound("dqtx.doesNotContain=" + DEFAULT_DQTX);

        // Get all the checkinList where dqtx does not contain UPDATED_DQTX
        defaultCheckinShouldBeFound("dqtx.doesNotContain=" + UPDATED_DQTX);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCheckinShouldBeFound(String filter) throws Exception {
        restCheckinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkin.getId().intValue())))
            .andExpect(jsonPath("$.[*].bkid").value(hasItem(DEFAULT_BKID.intValue())))
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
            .andExpect(jsonPath("$.[*].phonen").value(hasItem(DEFAULT_PHONEN)))
            .andExpect(jsonPath("$.[*].empn2").value(hasItem(DEFAULT_EMPN_2)))
            .andExpect(jsonPath("$.[*].adhoc").value(hasItem(DEFAULT_ADHOC)))
            .andExpect(jsonPath("$.[*].auditflag").value(hasItem(DEFAULT_AUDITFLAG.intValue())))
            .andExpect(jsonPath("$.[*].groupn").value(hasItem(DEFAULT_GROUPN)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].lfSign").value(hasItem(DEFAULT_LF_SIGN)))
            .andExpect(jsonPath("$.[*].keynum").value(hasItem(DEFAULT_KEYNUM)))
            .andExpect(jsonPath("$.[*].hykh").value(hasItem(DEFAULT_HYKH)))
            .andExpect(jsonPath("$.[*].bm").value(hasItem(DEFAULT_BM)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].jxtime").value(hasItem(DEFAULT_JXTIME.toString())))
            .andExpect(jsonPath("$.[*].jxflag").value(hasItem(DEFAULT_JXFLAG.intValue())))
            .andExpect(jsonPath("$.[*].checkf").value(hasItem(DEFAULT_CHECKF.intValue())))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].fgf").value(hasItem(DEFAULT_FGF.intValue())))
            .andExpect(jsonPath("$.[*].fgxx").value(hasItem(DEFAULT_FGXX)))
            .andExpect(jsonPath("$.[*].hourSign").value(hasItem(DEFAULT_HOUR_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].xsy").value(hasItem(DEFAULT_XSY)))
            .andExpect(jsonPath("$.[*].rzsign").value(hasItem(DEFAULT_RZSIGN.intValue())))
            .andExpect(jsonPath("$.[*].jf").value(hasItem(DEFAULT_JF.intValue())))
            .andExpect(jsonPath("$.[*].gname").value(hasItem(DEFAULT_GNAME)))
            .andExpect(jsonPath("$.[*].zcsign").value(hasItem(DEFAULT_ZCSIGN.intValue())))
            .andExpect(jsonPath("$.[*].cqsl").value(hasItem(DEFAULT_CQSL.intValue())))
            .andExpect(jsonPath("$.[*].sfjf").value(hasItem(DEFAULT_SFJF.intValue())))
            .andExpect(jsonPath("$.[*].ywly").value(hasItem(DEFAULT_YWLY)))
            .andExpect(jsonPath("$.[*].fk").value(hasItem(DEFAULT_FK)))
            .andExpect(jsonPath("$.[*].fkrq").value(hasItem(DEFAULT_FKRQ.toString())))
            .andExpect(jsonPath("$.[*].bc").value(hasItem(DEFAULT_BC)))
            .andExpect(jsonPath("$.[*].jxremark").value(hasItem(DEFAULT_JXREMARK)))
            .andExpect(jsonPath("$.[*].txid").value(hasItem(DEFAULT_TXID.doubleValue())))
            .andExpect(jsonPath("$.[*].cfr").value(hasItem(DEFAULT_CFR)))
            .andExpect(jsonPath("$.[*].fjbm").value(hasItem(DEFAULT_FJBM)))
            .andExpect(jsonPath("$.[*].djlx").value(hasItem(DEFAULT_DJLX)))
            .andExpect(jsonPath("$.[*].wlddh").value(hasItem(DEFAULT_WLDDH)))
            .andExpect(jsonPath("$.[*].fksl").value(hasItem(sameNumber(DEFAULT_FKSL))))
            .andExpect(jsonPath("$.[*].dqtx").value(hasItem(DEFAULT_DQTX)));

        // Check, that the count call also returns 1
        restCheckinMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCheckinShouldNotBeFound(String filter) throws Exception {
        restCheckinMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCheckinMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCheckin() throws Exception {
        // Get the checkin
        restCheckinMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCheckin() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        int databaseSizeBeforeUpdate = checkinRepository.findAll().size();

        // Update the checkin
        Checkin updatedCheckin = checkinRepository.findById(checkin.getId()).get();
        // Disconnect from session so that the updates on updatedCheckin are not directly saved in db
        em.detach(updatedCheckin);
        updatedCheckin
            .bkid(UPDATED_BKID)
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
            .phonen(UPDATED_PHONEN)
            .empn2(UPDATED_EMPN_2)
            .adhoc(UPDATED_ADHOC)
            .auditflag(UPDATED_AUDITFLAG)
            .groupn(UPDATED_GROUPN)
            .memo(UPDATED_MEMO)
            .lfSign(UPDATED_LF_SIGN)
            .keynum(UPDATED_KEYNUM)
            .hykh(UPDATED_HYKH)
            .bm(UPDATED_BM)
            .flag(UPDATED_FLAG)
            .jxtime(UPDATED_JXTIME)
            .jxflag(UPDATED_JXFLAG)
            .checkf(UPDATED_CHECKF)
            .guestname(UPDATED_GUESTNAME)
            .fgf(UPDATED_FGF)
            .fgxx(UPDATED_FGXX)
            .hourSign(UPDATED_HOUR_SIGN)
            .xsy(UPDATED_XSY)
            .rzsign(UPDATED_RZSIGN)
            .jf(UPDATED_JF)
            .gname(UPDATED_GNAME)
            .zcsign(UPDATED_ZCSIGN)
            .cqsl(UPDATED_CQSL)
            .sfjf(UPDATED_SFJF)
            .ywly(UPDATED_YWLY)
            .fk(UPDATED_FK)
            .fkrq(UPDATED_FKRQ)
            .bc(UPDATED_BC)
            .jxremark(UPDATED_JXREMARK)
            .txid(UPDATED_TXID)
            .cfr(UPDATED_CFR)
            .fjbm(UPDATED_FJBM)
            .djlx(UPDATED_DJLX)
            .wlddh(UPDATED_WLDDH)
            .fksl(UPDATED_FKSL)
            .dqtx(UPDATED_DQTX);
        CheckinDTO checkinDTO = checkinMapper.toDto(updatedCheckin);

        restCheckinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkinDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkinDTO))
            )
            .andExpect(status().isOk());

        // Validate the Checkin in the database
        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeUpdate);
        Checkin testCheckin = checkinList.get(checkinList.size() - 1);
        assertThat(testCheckin.getBkid()).isEqualTo(UPDATED_BKID);
        assertThat(testCheckin.getGuestId()).isEqualTo(UPDATED_GUEST_ID);
        assertThat(testCheckin.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCheckin.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCheckin.getIndatetime()).isEqualTo(UPDATED_INDATETIME);
        assertThat(testCheckin.getResidefate()).isEqualTo(UPDATED_RESIDEFATE);
        assertThat(testCheckin.getGotime()).isEqualTo(UPDATED_GOTIME);
        assertThat(testCheckin.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckin.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCheckin.getUname()).isEqualTo(UPDATED_UNAME);
        assertThat(testCheckin.getRentp()).isEqualTo(UPDATED_RENTP);
        assertThat(testCheckin.getProtocolrent()).isEqualTo(UPDATED_PROTOCOLRENT);
        assertThat(testCheckin.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testCheckin.getPhonen()).isEqualTo(UPDATED_PHONEN);
        assertThat(testCheckin.getEmpn2()).isEqualTo(UPDATED_EMPN_2);
        assertThat(testCheckin.getAdhoc()).isEqualTo(UPDATED_ADHOC);
        assertThat(testCheckin.getAuditflag()).isEqualTo(UPDATED_AUDITFLAG);
        assertThat(testCheckin.getGroupn()).isEqualTo(UPDATED_GROUPN);
        assertThat(testCheckin.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testCheckin.getLfSign()).isEqualTo(UPDATED_LF_SIGN);
        assertThat(testCheckin.getKeynum()).isEqualTo(UPDATED_KEYNUM);
        assertThat(testCheckin.getHykh()).isEqualTo(UPDATED_HYKH);
        assertThat(testCheckin.getBm()).isEqualTo(UPDATED_BM);
        assertThat(testCheckin.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testCheckin.getJxtime()).isEqualTo(UPDATED_JXTIME);
        assertThat(testCheckin.getJxflag()).isEqualTo(UPDATED_JXFLAG);
        assertThat(testCheckin.getCheckf()).isEqualTo(UPDATED_CHECKF);
        assertThat(testCheckin.getGuestname()).isEqualTo(UPDATED_GUESTNAME);
        assertThat(testCheckin.getFgf()).isEqualTo(UPDATED_FGF);
        assertThat(testCheckin.getFgxx()).isEqualTo(UPDATED_FGXX);
        assertThat(testCheckin.getHourSign()).isEqualTo(UPDATED_HOUR_SIGN);
        assertThat(testCheckin.getXsy()).isEqualTo(UPDATED_XSY);
        assertThat(testCheckin.getRzsign()).isEqualTo(UPDATED_RZSIGN);
        assertThat(testCheckin.getJf()).isEqualTo(UPDATED_JF);
        assertThat(testCheckin.getGname()).isEqualTo(UPDATED_GNAME);
        assertThat(testCheckin.getZcsign()).isEqualTo(UPDATED_ZCSIGN);
        assertThat(testCheckin.getCqsl()).isEqualTo(UPDATED_CQSL);
        assertThat(testCheckin.getSfjf()).isEqualTo(UPDATED_SFJF);
        assertThat(testCheckin.getYwly()).isEqualTo(UPDATED_YWLY);
        assertThat(testCheckin.getFk()).isEqualTo(UPDATED_FK);
        assertThat(testCheckin.getFkrq()).isEqualTo(UPDATED_FKRQ);
        assertThat(testCheckin.getBc()).isEqualTo(UPDATED_BC);
        assertThat(testCheckin.getJxremark()).isEqualTo(UPDATED_JXREMARK);
        assertThat(testCheckin.getTxid()).isEqualTo(UPDATED_TXID);
        assertThat(testCheckin.getCfr()).isEqualTo(UPDATED_CFR);
        assertThat(testCheckin.getFjbm()).isEqualTo(UPDATED_FJBM);
        assertThat(testCheckin.getDjlx()).isEqualTo(UPDATED_DJLX);
        assertThat(testCheckin.getWlddh()).isEqualTo(UPDATED_WLDDH);
        assertThat(testCheckin.getFksl()).isEqualTo(UPDATED_FKSL);
        assertThat(testCheckin.getDqtx()).isEqualTo(UPDATED_DQTX);

        // Validate the Checkin in Elasticsearch
        verify(mockCheckinSearchRepository).save(testCheckin);
    }

    @Test
    @Transactional
    void putNonExistingCheckin() throws Exception {
        int databaseSizeBeforeUpdate = checkinRepository.findAll().size();
        checkin.setId(count.incrementAndGet());

        // Create the Checkin
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, checkinDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Checkin in the database
        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Checkin in Elasticsearch
        verify(mockCheckinSearchRepository, times(0)).save(checkin);
    }

    @Test
    @Transactional
    void putWithIdMismatchCheckin() throws Exception {
        int databaseSizeBeforeUpdate = checkinRepository.findAll().size();
        checkin.setId(count.incrementAndGet());

        // Create the Checkin
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(checkinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Checkin in the database
        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Checkin in Elasticsearch
        verify(mockCheckinSearchRepository, times(0)).save(checkin);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCheckin() throws Exception {
        int databaseSizeBeforeUpdate = checkinRepository.findAll().size();
        checkin.setId(count.incrementAndGet());

        // Create the Checkin
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(checkinDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Checkin in the database
        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Checkin in Elasticsearch
        verify(mockCheckinSearchRepository, times(0)).save(checkin);
    }

    @Test
    @Transactional
    void partialUpdateCheckinWithPatch() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        int databaseSizeBeforeUpdate = checkinRepository.findAll().size();

        // Update the checkin using partial update
        Checkin partialUpdatedCheckin = new Checkin();
        partialUpdatedCheckin.setId(checkin.getId());

        partialUpdatedCheckin
            .bkid(UPDATED_BKID)
            .guestId(UPDATED_GUEST_ID)
            .account(UPDATED_ACCOUNT)
            .empn(UPDATED_EMPN)
            .roomn(UPDATED_ROOMN)
            .rentp(UPDATED_RENTP)
            .protocolrent(UPDATED_PROTOCOLRENT)
            .adhoc(UPDATED_ADHOC)
            .lfSign(UPDATED_LF_SIGN)
            .bm(UPDATED_BM)
            .flag(UPDATED_FLAG)
            .jxtime(UPDATED_JXTIME)
            .hourSign(UPDATED_HOUR_SIGN)
            .rzsign(UPDATED_RZSIGN)
            .jf(UPDATED_JF)
            .cqsl(UPDATED_CQSL)
            .ywly(UPDATED_YWLY)
            .fkrq(UPDATED_FKRQ)
            .bc(UPDATED_BC)
            .jxremark(UPDATED_JXREMARK)
            .wlddh(UPDATED_WLDDH);

        restCheckinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckin))
            )
            .andExpect(status().isOk());

        // Validate the Checkin in the database
        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeUpdate);
        Checkin testCheckin = checkinList.get(checkinList.size() - 1);
        assertThat(testCheckin.getBkid()).isEqualTo(UPDATED_BKID);
        assertThat(testCheckin.getGuestId()).isEqualTo(UPDATED_GUEST_ID);
        assertThat(testCheckin.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCheckin.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testCheckin.getIndatetime()).isEqualTo(DEFAULT_INDATETIME);
        assertThat(testCheckin.getResidefate()).isEqualTo(DEFAULT_RESIDEFATE);
        assertThat(testCheckin.getGotime()).isEqualTo(DEFAULT_GOTIME);
        assertThat(testCheckin.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckin.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCheckin.getUname()).isEqualTo(DEFAULT_UNAME);
        assertThat(testCheckin.getRentp()).isEqualTo(UPDATED_RENTP);
        assertThat(testCheckin.getProtocolrent()).isEqualByComparingTo(UPDATED_PROTOCOLRENT);
        assertThat(testCheckin.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testCheckin.getPhonen()).isEqualTo(DEFAULT_PHONEN);
        assertThat(testCheckin.getEmpn2()).isEqualTo(DEFAULT_EMPN_2);
        assertThat(testCheckin.getAdhoc()).isEqualTo(UPDATED_ADHOC);
        assertThat(testCheckin.getAuditflag()).isEqualTo(DEFAULT_AUDITFLAG);
        assertThat(testCheckin.getGroupn()).isEqualTo(DEFAULT_GROUPN);
        assertThat(testCheckin.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testCheckin.getLfSign()).isEqualTo(UPDATED_LF_SIGN);
        assertThat(testCheckin.getKeynum()).isEqualTo(DEFAULT_KEYNUM);
        assertThat(testCheckin.getHykh()).isEqualTo(DEFAULT_HYKH);
        assertThat(testCheckin.getBm()).isEqualTo(UPDATED_BM);
        assertThat(testCheckin.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testCheckin.getJxtime()).isEqualTo(UPDATED_JXTIME);
        assertThat(testCheckin.getJxflag()).isEqualTo(DEFAULT_JXFLAG);
        assertThat(testCheckin.getCheckf()).isEqualTo(DEFAULT_CHECKF);
        assertThat(testCheckin.getGuestname()).isEqualTo(DEFAULT_GUESTNAME);
        assertThat(testCheckin.getFgf()).isEqualTo(DEFAULT_FGF);
        assertThat(testCheckin.getFgxx()).isEqualTo(DEFAULT_FGXX);
        assertThat(testCheckin.getHourSign()).isEqualTo(UPDATED_HOUR_SIGN);
        assertThat(testCheckin.getXsy()).isEqualTo(DEFAULT_XSY);
        assertThat(testCheckin.getRzsign()).isEqualTo(UPDATED_RZSIGN);
        assertThat(testCheckin.getJf()).isEqualTo(UPDATED_JF);
        assertThat(testCheckin.getGname()).isEqualTo(DEFAULT_GNAME);
        assertThat(testCheckin.getZcsign()).isEqualTo(DEFAULT_ZCSIGN);
        assertThat(testCheckin.getCqsl()).isEqualTo(UPDATED_CQSL);
        assertThat(testCheckin.getSfjf()).isEqualTo(DEFAULT_SFJF);
        assertThat(testCheckin.getYwly()).isEqualTo(UPDATED_YWLY);
        assertThat(testCheckin.getFk()).isEqualTo(DEFAULT_FK);
        assertThat(testCheckin.getFkrq()).isEqualTo(UPDATED_FKRQ);
        assertThat(testCheckin.getBc()).isEqualTo(UPDATED_BC);
        assertThat(testCheckin.getJxremark()).isEqualTo(UPDATED_JXREMARK);
        assertThat(testCheckin.getTxid()).isEqualTo(DEFAULT_TXID);
        assertThat(testCheckin.getCfr()).isEqualTo(DEFAULT_CFR);
        assertThat(testCheckin.getFjbm()).isEqualTo(DEFAULT_FJBM);
        assertThat(testCheckin.getDjlx()).isEqualTo(DEFAULT_DJLX);
        assertThat(testCheckin.getWlddh()).isEqualTo(UPDATED_WLDDH);
        assertThat(testCheckin.getFksl()).isEqualByComparingTo(DEFAULT_FKSL);
        assertThat(testCheckin.getDqtx()).isEqualTo(DEFAULT_DQTX);
    }

    @Test
    @Transactional
    void fullUpdateCheckinWithPatch() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        int databaseSizeBeforeUpdate = checkinRepository.findAll().size();

        // Update the checkin using partial update
        Checkin partialUpdatedCheckin = new Checkin();
        partialUpdatedCheckin.setId(checkin.getId());

        partialUpdatedCheckin
            .bkid(UPDATED_BKID)
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
            .phonen(UPDATED_PHONEN)
            .empn2(UPDATED_EMPN_2)
            .adhoc(UPDATED_ADHOC)
            .auditflag(UPDATED_AUDITFLAG)
            .groupn(UPDATED_GROUPN)
            .memo(UPDATED_MEMO)
            .lfSign(UPDATED_LF_SIGN)
            .keynum(UPDATED_KEYNUM)
            .hykh(UPDATED_HYKH)
            .bm(UPDATED_BM)
            .flag(UPDATED_FLAG)
            .jxtime(UPDATED_JXTIME)
            .jxflag(UPDATED_JXFLAG)
            .checkf(UPDATED_CHECKF)
            .guestname(UPDATED_GUESTNAME)
            .fgf(UPDATED_FGF)
            .fgxx(UPDATED_FGXX)
            .hourSign(UPDATED_HOUR_SIGN)
            .xsy(UPDATED_XSY)
            .rzsign(UPDATED_RZSIGN)
            .jf(UPDATED_JF)
            .gname(UPDATED_GNAME)
            .zcsign(UPDATED_ZCSIGN)
            .cqsl(UPDATED_CQSL)
            .sfjf(UPDATED_SFJF)
            .ywly(UPDATED_YWLY)
            .fk(UPDATED_FK)
            .fkrq(UPDATED_FKRQ)
            .bc(UPDATED_BC)
            .jxremark(UPDATED_JXREMARK)
            .txid(UPDATED_TXID)
            .cfr(UPDATED_CFR)
            .fjbm(UPDATED_FJBM)
            .djlx(UPDATED_DJLX)
            .wlddh(UPDATED_WLDDH)
            .fksl(UPDATED_FKSL)
            .dqtx(UPDATED_DQTX);

        restCheckinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCheckin.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCheckin))
            )
            .andExpect(status().isOk());

        // Validate the Checkin in the database
        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeUpdate);
        Checkin testCheckin = checkinList.get(checkinList.size() - 1);
        assertThat(testCheckin.getBkid()).isEqualTo(UPDATED_BKID);
        assertThat(testCheckin.getGuestId()).isEqualTo(UPDATED_GUEST_ID);
        assertThat(testCheckin.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testCheckin.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testCheckin.getIndatetime()).isEqualTo(UPDATED_INDATETIME);
        assertThat(testCheckin.getResidefate()).isEqualTo(UPDATED_RESIDEFATE);
        assertThat(testCheckin.getGotime()).isEqualTo(UPDATED_GOTIME);
        assertThat(testCheckin.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCheckin.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testCheckin.getUname()).isEqualTo(UPDATED_UNAME);
        assertThat(testCheckin.getRentp()).isEqualTo(UPDATED_RENTP);
        assertThat(testCheckin.getProtocolrent()).isEqualByComparingTo(UPDATED_PROTOCOLRENT);
        assertThat(testCheckin.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testCheckin.getPhonen()).isEqualTo(UPDATED_PHONEN);
        assertThat(testCheckin.getEmpn2()).isEqualTo(UPDATED_EMPN_2);
        assertThat(testCheckin.getAdhoc()).isEqualTo(UPDATED_ADHOC);
        assertThat(testCheckin.getAuditflag()).isEqualTo(UPDATED_AUDITFLAG);
        assertThat(testCheckin.getGroupn()).isEqualTo(UPDATED_GROUPN);
        assertThat(testCheckin.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testCheckin.getLfSign()).isEqualTo(UPDATED_LF_SIGN);
        assertThat(testCheckin.getKeynum()).isEqualTo(UPDATED_KEYNUM);
        assertThat(testCheckin.getHykh()).isEqualTo(UPDATED_HYKH);
        assertThat(testCheckin.getBm()).isEqualTo(UPDATED_BM);
        assertThat(testCheckin.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testCheckin.getJxtime()).isEqualTo(UPDATED_JXTIME);
        assertThat(testCheckin.getJxflag()).isEqualTo(UPDATED_JXFLAG);
        assertThat(testCheckin.getCheckf()).isEqualTo(UPDATED_CHECKF);
        assertThat(testCheckin.getGuestname()).isEqualTo(UPDATED_GUESTNAME);
        assertThat(testCheckin.getFgf()).isEqualTo(UPDATED_FGF);
        assertThat(testCheckin.getFgxx()).isEqualTo(UPDATED_FGXX);
        assertThat(testCheckin.getHourSign()).isEqualTo(UPDATED_HOUR_SIGN);
        assertThat(testCheckin.getXsy()).isEqualTo(UPDATED_XSY);
        assertThat(testCheckin.getRzsign()).isEqualTo(UPDATED_RZSIGN);
        assertThat(testCheckin.getJf()).isEqualTo(UPDATED_JF);
        assertThat(testCheckin.getGname()).isEqualTo(UPDATED_GNAME);
        assertThat(testCheckin.getZcsign()).isEqualTo(UPDATED_ZCSIGN);
        assertThat(testCheckin.getCqsl()).isEqualTo(UPDATED_CQSL);
        assertThat(testCheckin.getSfjf()).isEqualTo(UPDATED_SFJF);
        assertThat(testCheckin.getYwly()).isEqualTo(UPDATED_YWLY);
        assertThat(testCheckin.getFk()).isEqualTo(UPDATED_FK);
        assertThat(testCheckin.getFkrq()).isEqualTo(UPDATED_FKRQ);
        assertThat(testCheckin.getBc()).isEqualTo(UPDATED_BC);
        assertThat(testCheckin.getJxremark()).isEqualTo(UPDATED_JXREMARK);
        assertThat(testCheckin.getTxid()).isEqualTo(UPDATED_TXID);
        assertThat(testCheckin.getCfr()).isEqualTo(UPDATED_CFR);
        assertThat(testCheckin.getFjbm()).isEqualTo(UPDATED_FJBM);
        assertThat(testCheckin.getDjlx()).isEqualTo(UPDATED_DJLX);
        assertThat(testCheckin.getWlddh()).isEqualTo(UPDATED_WLDDH);
        assertThat(testCheckin.getFksl()).isEqualByComparingTo(UPDATED_FKSL);
        assertThat(testCheckin.getDqtx()).isEqualTo(UPDATED_DQTX);
    }

    @Test
    @Transactional
    void patchNonExistingCheckin() throws Exception {
        int databaseSizeBeforeUpdate = checkinRepository.findAll().size();
        checkin.setId(count.incrementAndGet());

        // Create the Checkin
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCheckinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, checkinDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Checkin in the database
        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Checkin in Elasticsearch
        verify(mockCheckinSearchRepository, times(0)).save(checkin);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCheckin() throws Exception {
        int databaseSizeBeforeUpdate = checkinRepository.findAll().size();
        checkin.setId(count.incrementAndGet());

        // Create the Checkin
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(checkinDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Checkin in the database
        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Checkin in Elasticsearch
        verify(mockCheckinSearchRepository, times(0)).save(checkin);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCheckin() throws Exception {
        int databaseSizeBeforeUpdate = checkinRepository.findAll().size();
        checkin.setId(count.incrementAndGet());

        // Create the Checkin
        CheckinDTO checkinDTO = checkinMapper.toDto(checkin);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCheckinMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(checkinDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Checkin in the database
        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Checkin in Elasticsearch
        verify(mockCheckinSearchRepository, times(0)).save(checkin);
    }

    @Test
    @Transactional
    void deleteCheckin() throws Exception {
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);

        int databaseSizeBeforeDelete = checkinRepository.findAll().size();

        // Delete the checkin
        restCheckinMockMvc
            .perform(delete(ENTITY_API_URL_ID, checkin.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Checkin> checkinList = checkinRepository.findAll();
        assertThat(checkinList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Checkin in Elasticsearch
        verify(mockCheckinSearchRepository, times(1)).deleteById(checkin.getId());
    }

    @Test
    @Transactional
    void searchCheckin() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        checkinRepository.saveAndFlush(checkin);
        when(mockCheckinSearchRepository.search(queryStringQuery("id:" + checkin.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(checkin), PageRequest.of(0, 1), 1));

        // Search the checkin
        restCheckinMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + checkin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkin.getId().intValue())))
            .andExpect(jsonPath("$.[*].bkid").value(hasItem(DEFAULT_BKID.intValue())))
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
            .andExpect(jsonPath("$.[*].phonen").value(hasItem(DEFAULT_PHONEN)))
            .andExpect(jsonPath("$.[*].empn2").value(hasItem(DEFAULT_EMPN_2)))
            .andExpect(jsonPath("$.[*].adhoc").value(hasItem(DEFAULT_ADHOC)))
            .andExpect(jsonPath("$.[*].auditflag").value(hasItem(DEFAULT_AUDITFLAG.intValue())))
            .andExpect(jsonPath("$.[*].groupn").value(hasItem(DEFAULT_GROUPN)))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].lfSign").value(hasItem(DEFAULT_LF_SIGN)))
            .andExpect(jsonPath("$.[*].keynum").value(hasItem(DEFAULT_KEYNUM)))
            .andExpect(jsonPath("$.[*].hykh").value(hasItem(DEFAULT_HYKH)))
            .andExpect(jsonPath("$.[*].bm").value(hasItem(DEFAULT_BM)))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].jxtime").value(hasItem(DEFAULT_JXTIME.toString())))
            .andExpect(jsonPath("$.[*].jxflag").value(hasItem(DEFAULT_JXFLAG.intValue())))
            .andExpect(jsonPath("$.[*].checkf").value(hasItem(DEFAULT_CHECKF.intValue())))
            .andExpect(jsonPath("$.[*].guestname").value(hasItem(DEFAULT_GUESTNAME)))
            .andExpect(jsonPath("$.[*].fgf").value(hasItem(DEFAULT_FGF.intValue())))
            .andExpect(jsonPath("$.[*].fgxx").value(hasItem(DEFAULT_FGXX)))
            .andExpect(jsonPath("$.[*].hourSign").value(hasItem(DEFAULT_HOUR_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].xsy").value(hasItem(DEFAULT_XSY)))
            .andExpect(jsonPath("$.[*].rzsign").value(hasItem(DEFAULT_RZSIGN.intValue())))
            .andExpect(jsonPath("$.[*].jf").value(hasItem(DEFAULT_JF.intValue())))
            .andExpect(jsonPath("$.[*].gname").value(hasItem(DEFAULT_GNAME)))
            .andExpect(jsonPath("$.[*].zcsign").value(hasItem(DEFAULT_ZCSIGN.intValue())))
            .andExpect(jsonPath("$.[*].cqsl").value(hasItem(DEFAULT_CQSL.intValue())))
            .andExpect(jsonPath("$.[*].sfjf").value(hasItem(DEFAULT_SFJF.intValue())))
            .andExpect(jsonPath("$.[*].ywly").value(hasItem(DEFAULT_YWLY)))
            .andExpect(jsonPath("$.[*].fk").value(hasItem(DEFAULT_FK)))
            .andExpect(jsonPath("$.[*].fkrq").value(hasItem(DEFAULT_FKRQ.toString())))
            .andExpect(jsonPath("$.[*].bc").value(hasItem(DEFAULT_BC)))
            .andExpect(jsonPath("$.[*].jxremark").value(hasItem(DEFAULT_JXREMARK)))
            .andExpect(jsonPath("$.[*].txid").value(hasItem(DEFAULT_TXID.doubleValue())))
            .andExpect(jsonPath("$.[*].cfr").value(hasItem(DEFAULT_CFR)))
            .andExpect(jsonPath("$.[*].fjbm").value(hasItem(DEFAULT_FJBM)))
            .andExpect(jsonPath("$.[*].djlx").value(hasItem(DEFAULT_DJLX)))
            .andExpect(jsonPath("$.[*].wlddh").value(hasItem(DEFAULT_WLDDH)))
            .andExpect(jsonPath("$.[*].fksl").value(hasItem(sameNumber(DEFAULT_FKSL))))
            .andExpect(jsonPath("$.[*].dqtx").value(hasItem(DEFAULT_DQTX)));
    }
}
