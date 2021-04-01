package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Accounts;
import ihotel.app.repository.AccountsRepository;
import ihotel.app.repository.search.AccountsSearchRepository;
import ihotel.app.service.criteria.AccountsCriteria;
import ihotel.app.service.dto.AccountsDTO;
import ihotel.app.service.mapper.AccountsMapper;
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
 * Integration tests for the {@link AccountsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AccountsResourceIT {

    private static final String DEFAULT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CONSUMETIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CONSUMETIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_HOTELTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_HOTELTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_FEENUM = 1L;
    private static final Long UPDATED_FEENUM = 2L;
    private static final Long SMALLER_FEENUM = 1L - 1L;

    private static final BigDecimal DEFAULT_MONEY = new BigDecimal(1);
    private static final BigDecimal UPDATED_MONEY = new BigDecimal(2);
    private static final BigDecimal SMALLER_MONEY = new BigDecimal(1 - 1);

    private static final String DEFAULT_MEMO = "AAAAAAAAAA";
    private static final String UPDATED_MEMO = "BBBBBBBBBB";

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_IMPREST = new BigDecimal(1);
    private static final BigDecimal UPDATED_IMPREST = new BigDecimal(2);
    private static final BigDecimal SMALLER_IMPREST = new BigDecimal(1 - 1);

    private static final String DEFAULT_PROPERTIY = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTIY = "BBBBBBBBBB";

    private static final Long DEFAULT_EARNTYPEN = 1L;
    private static final Long UPDATED_EARNTYPEN = 2L;
    private static final Long SMALLER_EARNTYPEN = 1L - 1L;

    private static final Long DEFAULT_PAYMENT = 1L;
    private static final Long UPDATED_PAYMENT = 2L;
    private static final Long SMALLER_PAYMENT = 1L - 1L;

    private static final String DEFAULT_ROOMN = "AAAAAAAAAA";
    private static final String UPDATED_ROOMN = "BBBBBBBBBB";

    private static final String DEFAULT_ULOGOGRAM = "AAAAAAAAAA";
    private static final String UPDATED_ULOGOGRAM = "BBBBBBBBBB";

    private static final Long DEFAULT_LK = 1L;
    private static final Long UPDATED_LK = 2L;
    private static final Long SMALLER_LK = 1L - 1L;

    private static final String DEFAULT_ACC = "AAAAAAAAAA";
    private static final String UPDATED_ACC = "BBBBBBBBBB";

    private static final Long DEFAULT_JZ_SIGN = 1L;
    private static final Long UPDATED_JZ_SIGN = 2L;
    private static final Long SMALLER_JZ_SIGN = 1L - 1L;

    private static final Long DEFAULT_JZFLAG = 1L;
    private static final Long UPDATED_JZFLAG = 2L;
    private static final Long SMALLER_JZFLAG = 1L - 1L;

    private static final String DEFAULT_SIGN = "AAAA";
    private static final String UPDATED_SIGN = "BBBB";

    private static final Long DEFAULT_BS = 1L;
    private static final Long UPDATED_BS = 2L;
    private static final Long SMALLER_BS = 1L - 1L;

    private static final Instant DEFAULT_JZHOTEL = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JZHOTEL = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_JZEMPN = "AAAAAAAAAA";
    private static final String UPDATED_JZEMPN = "BBBBBBBBBB";

    private static final Instant DEFAULT_JZTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JZTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CHONGHONG = 1L;
    private static final Long UPDATED_CHONGHONG = 2L;
    private static final Long SMALLER_CHONGHONG = 1L - 1L;

    private static final String DEFAULT_BILLNO = "AAAAAAAAAA";
    private static final String UPDATED_BILLNO = "BBBBBBBBBB";

    private static final Long DEFAULT_PRINTCOUNT = 1L;
    private static final Long UPDATED_PRINTCOUNT = 2L;
    private static final Long SMALLER_PRINTCOUNT = 1L - 1L;

    private static final BigDecimal DEFAULT_VIPJF = new BigDecimal(1);
    private static final BigDecimal UPDATED_VIPJF = new BigDecimal(2);
    private static final BigDecimal SMALLER_VIPJF = new BigDecimal(1 - 1);

    private static final String DEFAULT_HYKH = "AAAAAAAAAA";
    private static final String UPDATED_HYKH = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_SL = new BigDecimal(1);
    private static final BigDecimal UPDATED_SL = new BigDecimal(2);
    private static final BigDecimal SMALLER_SL = new BigDecimal(1 - 1);

    private static final String DEFAULT_SGDJH = "AAAAAAAAAA";
    private static final String UPDATED_SGDJH = "BBBBBBBBBB";

    private static final String DEFAULT_HOTELDM = "AAAAAAAAAA";
    private static final String UPDATED_HOTELDM = "BBBBBBBBBB";

    private static final Long DEFAULT_ISNEW = 1L;
    private static final Long UPDATED_ISNEW = 2L;
    private static final Long SMALLER_ISNEW = 1L - 1L;

    private static final Double DEFAULT_GUEST_ID = 1D;
    private static final Double UPDATED_GUEST_ID = 2D;
    private static final Double SMALLER_GUEST_ID = 1D - 1D;

    private static final String DEFAULT_YHKH = "AAAAAAAAAA";
    private static final String UPDATED_YHKH = "BBBBBBBBBB";

    private static final String DEFAULT_DJQ = "AAAAAAAAAA";
    private static final String UPDATED_DJQ = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_YSJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_YSJE = new BigDecimal(2);
    private static final BigDecimal SMALLER_YSJE = new BigDecimal(1 - 1);

    private static final String DEFAULT_BJ = "AA";
    private static final String UPDATED_BJ = "BB";

    private static final String DEFAULT_BJEMPN = "AAAAAAAAAA";
    private static final String UPDATED_BJEMPN = "BBBBBBBBBB";

    private static final Instant DEFAULT_BJTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BJTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PAPER_2 = "AAAAAAAAAA";
    private static final String UPDATED_PAPER_2 = "BBBBBBBBBB";

    private static final String DEFAULT_BC = "AAAAAAAAAA";
    private static final String UPDATED_BC = "BBBBBBBBBB";

    private static final String DEFAULT_AUTO = "AA";
    private static final String UPDATED_AUTO = "BB";

    private static final String DEFAULT_XSY = "AAAAAAAAAA";
    private static final String UPDATED_XSY = "BBBBBBBBBB";

    private static final String DEFAULT_DJKH = "AAAAAAAAAA";
    private static final String UPDATED_DJKH = "BBBBBBBBBB";

    private static final String DEFAULT_DJSIGN = "AA";
    private static final String UPDATED_DJSIGN = "BB";

    private static final String DEFAULT_CLASSNAME = "AAAAAAAAAA";
    private static final String UPDATED_CLASSNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ISCY = "AAAAAAAAAA";
    private static final String UPDATED_ISCY = "BBBBBBBBBB";

    private static final String DEFAULT_BSIGN = "AA";
    private static final String UPDATED_BSIGN = "BB";

    private static final String DEFAULT_FX = "AA";
    private static final String UPDATED_FX = "BB";

    private static final String DEFAULT_DJLX = "AAAAAAAAAA";
    private static final String UPDATED_DJLX = "BBBBBBBBBB";

    private static final Long DEFAULT_ISUP = 1L;
    private static final Long UPDATED_ISUP = 2L;
    private static final Long SMALLER_ISUP = 1L - 1L;

    private static final BigDecimal DEFAULT_YONGJIN = new BigDecimal(1);
    private static final BigDecimal UPDATED_YONGJIN = new BigDecimal(2);
    private static final BigDecimal SMALLER_YONGJIN = new BigDecimal(1 - 1);

    private static final String DEFAULT_CZPC = "AAAAAAAAAA";
    private static final String UPDATED_CZPC = "BBBBBBBBBB";

    private static final Long DEFAULT_CXFLAG = 1L;
    private static final Long UPDATED_CXFLAG = 2L;
    private static final Long SMALLER_CXFLAG = 1L - 1L;

    private static final String DEFAULT_PMEMO = "AAAAAAAAAA";
    private static final String UPDATED_PMEMO = "BBBBBBBBBB";

    private static final String DEFAULT_CZBILLNO = "AAAAAAAAAA";
    private static final String UPDATED_CZBILLNO = "BBBBBBBBBB";

    private static final String DEFAULT_DJQBZ = "AAAAAAAAAA";
    private static final String UPDATED_DJQBZ = "BBBBBBBBBB";

    private static final String DEFAULT_YSQMEMO = "AAAAAAAAAA";
    private static final String UPDATED_YSQMEMO = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSACTION_ID = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_ID = "BBBBBBBBBB";

    private static final String DEFAULT_OUT_TRADE_NO = "AAAAAAAAAA";
    private static final String UPDATED_OUT_TRADE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_GSNAME = "AAAAAAAAAA";
    private static final String UPDATED_GSNAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_RZ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RZ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_GZ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GZ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_TS = 1L;
    private static final Long UPDATED_TS = 2L;
    private static final Long SMALLER_TS = 1L - 1L;

    private static final String DEFAULT_KY = "AAAAAAAAAA";
    private static final String UPDATED_KY = "BBBBBBBBBB";

    private static final String DEFAULT_XY = "AAAAAAAAAA";
    private static final String UPDATED_XY = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMTYPE = "AAAAAAAAAA";
    private static final String UPDATED_ROOMTYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_BKID = 1L;
    private static final Long UPDATED_BKID = 2L;
    private static final Long SMALLER_BKID = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/accounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/accounts";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private AccountsMapper accountsMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.AccountsSearchRepositoryMockConfiguration
     */
    @Autowired
    private AccountsSearchRepository mockAccountsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAccountsMockMvc;

    private Accounts accounts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounts createEntity(EntityManager em) {
        Accounts accounts = new Accounts()
            .account(DEFAULT_ACCOUNT)
            .consumetime(DEFAULT_CONSUMETIME)
            .hoteltime(DEFAULT_HOTELTIME)
            .feenum(DEFAULT_FEENUM)
            .money(DEFAULT_MONEY)
            .memo(DEFAULT_MEMO)
            .empn(DEFAULT_EMPN)
            .imprest(DEFAULT_IMPREST)
            .propertiy(DEFAULT_PROPERTIY)
            .earntypen(DEFAULT_EARNTYPEN)
            .payment(DEFAULT_PAYMENT)
            .roomn(DEFAULT_ROOMN)
            .ulogogram(DEFAULT_ULOGOGRAM)
            .lk(DEFAULT_LK)
            .acc(DEFAULT_ACC)
            .jzSign(DEFAULT_JZ_SIGN)
            .jzflag(DEFAULT_JZFLAG)
            .sign(DEFAULT_SIGN)
            .bs(DEFAULT_BS)
            .jzhotel(DEFAULT_JZHOTEL)
            .jzempn(DEFAULT_JZEMPN)
            .jztime(DEFAULT_JZTIME)
            .chonghong(DEFAULT_CHONGHONG)
            .billno(DEFAULT_BILLNO)
            .printcount(DEFAULT_PRINTCOUNT)
            .vipjf(DEFAULT_VIPJF)
            .hykh(DEFAULT_HYKH)
            .sl(DEFAULT_SL)
            .sgdjh(DEFAULT_SGDJH)
            .hoteldm(DEFAULT_HOTELDM)
            .isnew(DEFAULT_ISNEW)
            .guestId(DEFAULT_GUEST_ID)
            .yhkh(DEFAULT_YHKH)
            .djq(DEFAULT_DJQ)
            .ysje(DEFAULT_YSJE)
            .bj(DEFAULT_BJ)
            .bjempn(DEFAULT_BJEMPN)
            .bjtime(DEFAULT_BJTIME)
            .paper2(DEFAULT_PAPER_2)
            .bc(DEFAULT_BC)
            .auto(DEFAULT_AUTO)
            .xsy(DEFAULT_XSY)
            .djkh(DEFAULT_DJKH)
            .djsign(DEFAULT_DJSIGN)
            .classname(DEFAULT_CLASSNAME)
            .iscy(DEFAULT_ISCY)
            .bsign(DEFAULT_BSIGN)
            .fx(DEFAULT_FX)
            .djlx(DEFAULT_DJLX)
            .isup(DEFAULT_ISUP)
            .yongjin(DEFAULT_YONGJIN)
            .czpc(DEFAULT_CZPC)
            .cxflag(DEFAULT_CXFLAG)
            .pmemo(DEFAULT_PMEMO)
            .czbillno(DEFAULT_CZBILLNO)
            .djqbz(DEFAULT_DJQBZ)
            .ysqmemo(DEFAULT_YSQMEMO)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .outTradeNo(DEFAULT_OUT_TRADE_NO)
            .gsname(DEFAULT_GSNAME)
            .rz(DEFAULT_RZ)
            .gz(DEFAULT_GZ)
            .ts(DEFAULT_TS)
            .ky(DEFAULT_KY)
            .xy(DEFAULT_XY)
            .roomtype(DEFAULT_ROOMTYPE)
            .bkid(DEFAULT_BKID);
        return accounts;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Accounts createUpdatedEntity(EntityManager em) {
        Accounts accounts = new Accounts()
            .account(UPDATED_ACCOUNT)
            .consumetime(UPDATED_CONSUMETIME)
            .hoteltime(UPDATED_HOTELTIME)
            .feenum(UPDATED_FEENUM)
            .money(UPDATED_MONEY)
            .memo(UPDATED_MEMO)
            .empn(UPDATED_EMPN)
            .imprest(UPDATED_IMPREST)
            .propertiy(UPDATED_PROPERTIY)
            .earntypen(UPDATED_EARNTYPEN)
            .payment(UPDATED_PAYMENT)
            .roomn(UPDATED_ROOMN)
            .ulogogram(UPDATED_ULOGOGRAM)
            .lk(UPDATED_LK)
            .acc(UPDATED_ACC)
            .jzSign(UPDATED_JZ_SIGN)
            .jzflag(UPDATED_JZFLAG)
            .sign(UPDATED_SIGN)
            .bs(UPDATED_BS)
            .jzhotel(UPDATED_JZHOTEL)
            .jzempn(UPDATED_JZEMPN)
            .jztime(UPDATED_JZTIME)
            .chonghong(UPDATED_CHONGHONG)
            .billno(UPDATED_BILLNO)
            .printcount(UPDATED_PRINTCOUNT)
            .vipjf(UPDATED_VIPJF)
            .hykh(UPDATED_HYKH)
            .sl(UPDATED_SL)
            .sgdjh(UPDATED_SGDJH)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW)
            .guestId(UPDATED_GUEST_ID)
            .yhkh(UPDATED_YHKH)
            .djq(UPDATED_DJQ)
            .ysje(UPDATED_YSJE)
            .bj(UPDATED_BJ)
            .bjempn(UPDATED_BJEMPN)
            .bjtime(UPDATED_BJTIME)
            .paper2(UPDATED_PAPER_2)
            .bc(UPDATED_BC)
            .auto(UPDATED_AUTO)
            .xsy(UPDATED_XSY)
            .djkh(UPDATED_DJKH)
            .djsign(UPDATED_DJSIGN)
            .classname(UPDATED_CLASSNAME)
            .iscy(UPDATED_ISCY)
            .bsign(UPDATED_BSIGN)
            .fx(UPDATED_FX)
            .djlx(UPDATED_DJLX)
            .isup(UPDATED_ISUP)
            .yongjin(UPDATED_YONGJIN)
            .czpc(UPDATED_CZPC)
            .cxflag(UPDATED_CXFLAG)
            .pmemo(UPDATED_PMEMO)
            .czbillno(UPDATED_CZBILLNO)
            .djqbz(UPDATED_DJQBZ)
            .ysqmemo(UPDATED_YSQMEMO)
            .transactionId(UPDATED_TRANSACTION_ID)
            .outTradeNo(UPDATED_OUT_TRADE_NO)
            .gsname(UPDATED_GSNAME)
            .rz(UPDATED_RZ)
            .gz(UPDATED_GZ)
            .ts(UPDATED_TS)
            .ky(UPDATED_KY)
            .xy(UPDATED_XY)
            .roomtype(UPDATED_ROOMTYPE)
            .bkid(UPDATED_BKID);
        return accounts;
    }

    @BeforeEach
    public void initTest() {
        accounts = createEntity(em);
    }

    @Test
    @Transactional
    void createAccounts() throws Exception {
        int databaseSizeBeforeCreate = accountsRepository.findAll().size();
        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);
        restAccountsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsDTO)))
            .andExpect(status().isCreated());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeCreate + 1);
        Accounts testAccounts = accountsList.get(accountsList.size() - 1);
        assertThat(testAccounts.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testAccounts.getConsumetime()).isEqualTo(DEFAULT_CONSUMETIME);
        assertThat(testAccounts.getHoteltime()).isEqualTo(DEFAULT_HOTELTIME);
        assertThat(testAccounts.getFeenum()).isEqualTo(DEFAULT_FEENUM);
        assertThat(testAccounts.getMoney()).isEqualByComparingTo(DEFAULT_MONEY);
        assertThat(testAccounts.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testAccounts.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testAccounts.getImprest()).isEqualByComparingTo(DEFAULT_IMPREST);
        assertThat(testAccounts.getPropertiy()).isEqualTo(DEFAULT_PROPERTIY);
        assertThat(testAccounts.getEarntypen()).isEqualTo(DEFAULT_EARNTYPEN);
        assertThat(testAccounts.getPayment()).isEqualTo(DEFAULT_PAYMENT);
        assertThat(testAccounts.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testAccounts.getUlogogram()).isEqualTo(DEFAULT_ULOGOGRAM);
        assertThat(testAccounts.getLk()).isEqualTo(DEFAULT_LK);
        assertThat(testAccounts.getAcc()).isEqualTo(DEFAULT_ACC);
        assertThat(testAccounts.getJzSign()).isEqualTo(DEFAULT_JZ_SIGN);
        assertThat(testAccounts.getJzflag()).isEqualTo(DEFAULT_JZFLAG);
        assertThat(testAccounts.getSign()).isEqualTo(DEFAULT_SIGN);
        assertThat(testAccounts.getBs()).isEqualTo(DEFAULT_BS);
        assertThat(testAccounts.getJzhotel()).isEqualTo(DEFAULT_JZHOTEL);
        assertThat(testAccounts.getJzempn()).isEqualTo(DEFAULT_JZEMPN);
        assertThat(testAccounts.getJztime()).isEqualTo(DEFAULT_JZTIME);
        assertThat(testAccounts.getChonghong()).isEqualTo(DEFAULT_CHONGHONG);
        assertThat(testAccounts.getBillno()).isEqualTo(DEFAULT_BILLNO);
        assertThat(testAccounts.getPrintcount()).isEqualTo(DEFAULT_PRINTCOUNT);
        assertThat(testAccounts.getVipjf()).isEqualByComparingTo(DEFAULT_VIPJF);
        assertThat(testAccounts.getHykh()).isEqualTo(DEFAULT_HYKH);
        assertThat(testAccounts.getSl()).isEqualByComparingTo(DEFAULT_SL);
        assertThat(testAccounts.getSgdjh()).isEqualTo(DEFAULT_SGDJH);
        assertThat(testAccounts.getHoteldm()).isEqualTo(DEFAULT_HOTELDM);
        assertThat(testAccounts.getIsnew()).isEqualTo(DEFAULT_ISNEW);
        assertThat(testAccounts.getGuestId()).isEqualTo(DEFAULT_GUEST_ID);
        assertThat(testAccounts.getYhkh()).isEqualTo(DEFAULT_YHKH);
        assertThat(testAccounts.getDjq()).isEqualTo(DEFAULT_DJQ);
        assertThat(testAccounts.getYsje()).isEqualByComparingTo(DEFAULT_YSJE);
        assertThat(testAccounts.getBj()).isEqualTo(DEFAULT_BJ);
        assertThat(testAccounts.getBjempn()).isEqualTo(DEFAULT_BJEMPN);
        assertThat(testAccounts.getBjtime()).isEqualTo(DEFAULT_BJTIME);
        assertThat(testAccounts.getPaper2()).isEqualTo(DEFAULT_PAPER_2);
        assertThat(testAccounts.getBc()).isEqualTo(DEFAULT_BC);
        assertThat(testAccounts.getAuto()).isEqualTo(DEFAULT_AUTO);
        assertThat(testAccounts.getXsy()).isEqualTo(DEFAULT_XSY);
        assertThat(testAccounts.getDjkh()).isEqualTo(DEFAULT_DJKH);
        assertThat(testAccounts.getDjsign()).isEqualTo(DEFAULT_DJSIGN);
        assertThat(testAccounts.getClassname()).isEqualTo(DEFAULT_CLASSNAME);
        assertThat(testAccounts.getIscy()).isEqualTo(DEFAULT_ISCY);
        assertThat(testAccounts.getBsign()).isEqualTo(DEFAULT_BSIGN);
        assertThat(testAccounts.getFx()).isEqualTo(DEFAULT_FX);
        assertThat(testAccounts.getDjlx()).isEqualTo(DEFAULT_DJLX);
        assertThat(testAccounts.getIsup()).isEqualTo(DEFAULT_ISUP);
        assertThat(testAccounts.getYongjin()).isEqualByComparingTo(DEFAULT_YONGJIN);
        assertThat(testAccounts.getCzpc()).isEqualTo(DEFAULT_CZPC);
        assertThat(testAccounts.getCxflag()).isEqualTo(DEFAULT_CXFLAG);
        assertThat(testAccounts.getPmemo()).isEqualTo(DEFAULT_PMEMO);
        assertThat(testAccounts.getCzbillno()).isEqualTo(DEFAULT_CZBILLNO);
        assertThat(testAccounts.getDjqbz()).isEqualTo(DEFAULT_DJQBZ);
        assertThat(testAccounts.getYsqmemo()).isEqualTo(DEFAULT_YSQMEMO);
        assertThat(testAccounts.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testAccounts.getOutTradeNo()).isEqualTo(DEFAULT_OUT_TRADE_NO);
        assertThat(testAccounts.getGsname()).isEqualTo(DEFAULT_GSNAME);
        assertThat(testAccounts.getRz()).isEqualTo(DEFAULT_RZ);
        assertThat(testAccounts.getGz()).isEqualTo(DEFAULT_GZ);
        assertThat(testAccounts.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testAccounts.getKy()).isEqualTo(DEFAULT_KY);
        assertThat(testAccounts.getXy()).isEqualTo(DEFAULT_XY);
        assertThat(testAccounts.getRoomtype()).isEqualTo(DEFAULT_ROOMTYPE);
        assertThat(testAccounts.getBkid()).isEqualTo(DEFAULT_BKID);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository, times(1)).save(testAccounts);
    }

    @Test
    @Transactional
    void createAccountsWithExistingId() throws Exception {
        // Create the Accounts with an existing ID
        accounts.setId(1L);
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        int databaseSizeBeforeCreate = accountsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccountsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeCreate);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository, times(0)).save(accounts);
    }

    @Test
    @Transactional
    void checkAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountsRepository.findAll().size();
        // set the field null
        accounts.setAccount(null);

        // Create the Accounts, which fails.
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        restAccountsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsDTO)))
            .andExpect(status().isBadRequest());

        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkConsumetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = accountsRepository.findAll().size();
        // set the field null
        accounts.setConsumetime(null);

        // Create the Accounts, which fails.
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        restAccountsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsDTO)))
            .andExpect(status().isBadRequest());

        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].consumetime").value(hasItem(DEFAULT_CONSUMETIME.toString())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].feenum").value(hasItem(DEFAULT_FEENUM.intValue())))
            .andExpect(jsonPath("$.[*].money").value(hasItem(sameNumber(DEFAULT_MONEY))))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].imprest").value(hasItem(sameNumber(DEFAULT_IMPREST))))
            .andExpect(jsonPath("$.[*].propertiy").value(hasItem(DEFAULT_PROPERTIY)))
            .andExpect(jsonPath("$.[*].earntypen").value(hasItem(DEFAULT_EARNTYPEN.intValue())))
            .andExpect(jsonPath("$.[*].payment").value(hasItem(DEFAULT_PAYMENT.intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].ulogogram").value(hasItem(DEFAULT_ULOGOGRAM)))
            .andExpect(jsonPath("$.[*].lk").value(hasItem(DEFAULT_LK.intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)))
            .andExpect(jsonPath("$.[*].jzSign").value(hasItem(DEFAULT_JZ_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].jzflag").value(hasItem(DEFAULT_JZFLAG.intValue())))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN)))
            .andExpect(jsonPath("$.[*].bs").value(hasItem(DEFAULT_BS.intValue())))
            .andExpect(jsonPath("$.[*].jzhotel").value(hasItem(DEFAULT_JZHOTEL.toString())))
            .andExpect(jsonPath("$.[*].jzempn").value(hasItem(DEFAULT_JZEMPN)))
            .andExpect(jsonPath("$.[*].jztime").value(hasItem(DEFAULT_JZTIME.toString())))
            .andExpect(jsonPath("$.[*].chonghong").value(hasItem(DEFAULT_CHONGHONG.intValue())))
            .andExpect(jsonPath("$.[*].billno").value(hasItem(DEFAULT_BILLNO)))
            .andExpect(jsonPath("$.[*].printcount").value(hasItem(DEFAULT_PRINTCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].vipjf").value(hasItem(sameNumber(DEFAULT_VIPJF))))
            .andExpect(jsonPath("$.[*].hykh").value(hasItem(DEFAULT_HYKH)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].sgdjh").value(hasItem(DEFAULT_SGDJH)))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())))
            .andExpect(jsonPath("$.[*].guestId").value(hasItem(DEFAULT_GUEST_ID.doubleValue())))
            .andExpect(jsonPath("$.[*].yhkh").value(hasItem(DEFAULT_YHKH)))
            .andExpect(jsonPath("$.[*].djq").value(hasItem(DEFAULT_DJQ)))
            .andExpect(jsonPath("$.[*].ysje").value(hasItem(sameNumber(DEFAULT_YSJE))))
            .andExpect(jsonPath("$.[*].bj").value(hasItem(DEFAULT_BJ)))
            .andExpect(jsonPath("$.[*].bjempn").value(hasItem(DEFAULT_BJEMPN)))
            .andExpect(jsonPath("$.[*].bjtime").value(hasItem(DEFAULT_BJTIME.toString())))
            .andExpect(jsonPath("$.[*].paper2").value(hasItem(DEFAULT_PAPER_2)))
            .andExpect(jsonPath("$.[*].bc").value(hasItem(DEFAULT_BC)))
            .andExpect(jsonPath("$.[*].auto").value(hasItem(DEFAULT_AUTO)))
            .andExpect(jsonPath("$.[*].xsy").value(hasItem(DEFAULT_XSY)))
            .andExpect(jsonPath("$.[*].djkh").value(hasItem(DEFAULT_DJKH)))
            .andExpect(jsonPath("$.[*].djsign").value(hasItem(DEFAULT_DJSIGN)))
            .andExpect(jsonPath("$.[*].classname").value(hasItem(DEFAULT_CLASSNAME)))
            .andExpect(jsonPath("$.[*].iscy").value(hasItem(DEFAULT_ISCY)))
            .andExpect(jsonPath("$.[*].bsign").value(hasItem(DEFAULT_BSIGN)))
            .andExpect(jsonPath("$.[*].fx").value(hasItem(DEFAULT_FX)))
            .andExpect(jsonPath("$.[*].djlx").value(hasItem(DEFAULT_DJLX)))
            .andExpect(jsonPath("$.[*].isup").value(hasItem(DEFAULT_ISUP.intValue())))
            .andExpect(jsonPath("$.[*].yongjin").value(hasItem(sameNumber(DEFAULT_YONGJIN))))
            .andExpect(jsonPath("$.[*].czpc").value(hasItem(DEFAULT_CZPC)))
            .andExpect(jsonPath("$.[*].cxflag").value(hasItem(DEFAULT_CXFLAG.intValue())))
            .andExpect(jsonPath("$.[*].pmemo").value(hasItem(DEFAULT_PMEMO)))
            .andExpect(jsonPath("$.[*].czbillno").value(hasItem(DEFAULT_CZBILLNO)))
            .andExpect(jsonPath("$.[*].djqbz").value(hasItem(DEFAULT_DJQBZ)))
            .andExpect(jsonPath("$.[*].ysqmemo").value(hasItem(DEFAULT_YSQMEMO)))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].outTradeNo").value(hasItem(DEFAULT_OUT_TRADE_NO)))
            .andExpect(jsonPath("$.[*].gsname").value(hasItem(DEFAULT_GSNAME)))
            .andExpect(jsonPath("$.[*].rz").value(hasItem(DEFAULT_RZ.toString())))
            .andExpect(jsonPath("$.[*].gz").value(hasItem(DEFAULT_GZ.toString())))
            .andExpect(jsonPath("$.[*].ts").value(hasItem(DEFAULT_TS.intValue())))
            .andExpect(jsonPath("$.[*].ky").value(hasItem(DEFAULT_KY)))
            .andExpect(jsonPath("$.[*].xy").value(hasItem(DEFAULT_XY)))
            .andExpect(jsonPath("$.[*].roomtype").value(hasItem(DEFAULT_ROOMTYPE)))
            .andExpect(jsonPath("$.[*].bkid").value(hasItem(DEFAULT_BKID.intValue())));
    }

    @Test
    @Transactional
    void getAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get the accounts
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL_ID, accounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(accounts.getId().intValue()))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT))
            .andExpect(jsonPath("$.consumetime").value(DEFAULT_CONSUMETIME.toString()))
            .andExpect(jsonPath("$.hoteltime").value(DEFAULT_HOTELTIME.toString()))
            .andExpect(jsonPath("$.feenum").value(DEFAULT_FEENUM.intValue()))
            .andExpect(jsonPath("$.money").value(sameNumber(DEFAULT_MONEY)))
            .andExpect(jsonPath("$.memo").value(DEFAULT_MEMO))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.imprest").value(sameNumber(DEFAULT_IMPREST)))
            .andExpect(jsonPath("$.propertiy").value(DEFAULT_PROPERTIY))
            .andExpect(jsonPath("$.earntypen").value(DEFAULT_EARNTYPEN.intValue()))
            .andExpect(jsonPath("$.payment").value(DEFAULT_PAYMENT.intValue()))
            .andExpect(jsonPath("$.roomn").value(DEFAULT_ROOMN))
            .andExpect(jsonPath("$.ulogogram").value(DEFAULT_ULOGOGRAM))
            .andExpect(jsonPath("$.lk").value(DEFAULT_LK.intValue()))
            .andExpect(jsonPath("$.acc").value(DEFAULT_ACC))
            .andExpect(jsonPath("$.jzSign").value(DEFAULT_JZ_SIGN.intValue()))
            .andExpect(jsonPath("$.jzflag").value(DEFAULT_JZFLAG.intValue()))
            .andExpect(jsonPath("$.sign").value(DEFAULT_SIGN))
            .andExpect(jsonPath("$.bs").value(DEFAULT_BS.intValue()))
            .andExpect(jsonPath("$.jzhotel").value(DEFAULT_JZHOTEL.toString()))
            .andExpect(jsonPath("$.jzempn").value(DEFAULT_JZEMPN))
            .andExpect(jsonPath("$.jztime").value(DEFAULT_JZTIME.toString()))
            .andExpect(jsonPath("$.chonghong").value(DEFAULT_CHONGHONG.intValue()))
            .andExpect(jsonPath("$.billno").value(DEFAULT_BILLNO))
            .andExpect(jsonPath("$.printcount").value(DEFAULT_PRINTCOUNT.intValue()))
            .andExpect(jsonPath("$.vipjf").value(sameNumber(DEFAULT_VIPJF)))
            .andExpect(jsonPath("$.hykh").value(DEFAULT_HYKH))
            .andExpect(jsonPath("$.sl").value(sameNumber(DEFAULT_SL)))
            .andExpect(jsonPath("$.sgdjh").value(DEFAULT_SGDJH))
            .andExpect(jsonPath("$.hoteldm").value(DEFAULT_HOTELDM))
            .andExpect(jsonPath("$.isnew").value(DEFAULT_ISNEW.intValue()))
            .andExpect(jsonPath("$.guestId").value(DEFAULT_GUEST_ID.doubleValue()))
            .andExpect(jsonPath("$.yhkh").value(DEFAULT_YHKH))
            .andExpect(jsonPath("$.djq").value(DEFAULT_DJQ))
            .andExpect(jsonPath("$.ysje").value(sameNumber(DEFAULT_YSJE)))
            .andExpect(jsonPath("$.bj").value(DEFAULT_BJ))
            .andExpect(jsonPath("$.bjempn").value(DEFAULT_BJEMPN))
            .andExpect(jsonPath("$.bjtime").value(DEFAULT_BJTIME.toString()))
            .andExpect(jsonPath("$.paper2").value(DEFAULT_PAPER_2))
            .andExpect(jsonPath("$.bc").value(DEFAULT_BC))
            .andExpect(jsonPath("$.auto").value(DEFAULT_AUTO))
            .andExpect(jsonPath("$.xsy").value(DEFAULT_XSY))
            .andExpect(jsonPath("$.djkh").value(DEFAULT_DJKH))
            .andExpect(jsonPath("$.djsign").value(DEFAULT_DJSIGN))
            .andExpect(jsonPath("$.classname").value(DEFAULT_CLASSNAME))
            .andExpect(jsonPath("$.iscy").value(DEFAULT_ISCY))
            .andExpect(jsonPath("$.bsign").value(DEFAULT_BSIGN))
            .andExpect(jsonPath("$.fx").value(DEFAULT_FX))
            .andExpect(jsonPath("$.djlx").value(DEFAULT_DJLX))
            .andExpect(jsonPath("$.isup").value(DEFAULT_ISUP.intValue()))
            .andExpect(jsonPath("$.yongjin").value(sameNumber(DEFAULT_YONGJIN)))
            .andExpect(jsonPath("$.czpc").value(DEFAULT_CZPC))
            .andExpect(jsonPath("$.cxflag").value(DEFAULT_CXFLAG.intValue()))
            .andExpect(jsonPath("$.pmemo").value(DEFAULT_PMEMO))
            .andExpect(jsonPath("$.czbillno").value(DEFAULT_CZBILLNO))
            .andExpect(jsonPath("$.djqbz").value(DEFAULT_DJQBZ))
            .andExpect(jsonPath("$.ysqmemo").value(DEFAULT_YSQMEMO))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID))
            .andExpect(jsonPath("$.outTradeNo").value(DEFAULT_OUT_TRADE_NO))
            .andExpect(jsonPath("$.gsname").value(DEFAULT_GSNAME))
            .andExpect(jsonPath("$.rz").value(DEFAULT_RZ.toString()))
            .andExpect(jsonPath("$.gz").value(DEFAULT_GZ.toString()))
            .andExpect(jsonPath("$.ts").value(DEFAULT_TS.intValue()))
            .andExpect(jsonPath("$.ky").value(DEFAULT_KY))
            .andExpect(jsonPath("$.xy").value(DEFAULT_XY))
            .andExpect(jsonPath("$.roomtype").value(DEFAULT_ROOMTYPE))
            .andExpect(jsonPath("$.bkid").value(DEFAULT_BKID.intValue()));
    }

    @Test
    @Transactional
    void getAccountsByIdFiltering() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        Long id = accounts.getId();

        defaultAccountsShouldBeFound("id.equals=" + id);
        defaultAccountsShouldNotBeFound("id.notEquals=" + id);

        defaultAccountsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAccountsShouldNotBeFound("id.greaterThan=" + id);

        defaultAccountsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAccountsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllAccountsByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where account equals to DEFAULT_ACCOUNT
        defaultAccountsShouldBeFound("account.equals=" + DEFAULT_ACCOUNT);

        // Get all the accountsList where account equals to UPDATED_ACCOUNT
        defaultAccountsShouldNotBeFound("account.equals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where account not equals to DEFAULT_ACCOUNT
        defaultAccountsShouldNotBeFound("account.notEquals=" + DEFAULT_ACCOUNT);

        // Get all the accountsList where account not equals to UPDATED_ACCOUNT
        defaultAccountsShouldBeFound("account.notEquals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByAccountIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where account in DEFAULT_ACCOUNT or UPDATED_ACCOUNT
        defaultAccountsShouldBeFound("account.in=" + DEFAULT_ACCOUNT + "," + UPDATED_ACCOUNT);

        // Get all the accountsList where account equals to UPDATED_ACCOUNT
        defaultAccountsShouldNotBeFound("account.in=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where account is not null
        defaultAccountsShouldBeFound("account.specified=true");

        // Get all the accountsList where account is null
        defaultAccountsShouldNotBeFound("account.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByAccountContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where account contains DEFAULT_ACCOUNT
        defaultAccountsShouldBeFound("account.contains=" + DEFAULT_ACCOUNT);

        // Get all the accountsList where account contains UPDATED_ACCOUNT
        defaultAccountsShouldNotBeFound("account.contains=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByAccountNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where account does not contain DEFAULT_ACCOUNT
        defaultAccountsShouldNotBeFound("account.doesNotContain=" + DEFAULT_ACCOUNT);

        // Get all the accountsList where account does not contain UPDATED_ACCOUNT
        defaultAccountsShouldBeFound("account.doesNotContain=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByConsumetimeIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where consumetime equals to DEFAULT_CONSUMETIME
        defaultAccountsShouldBeFound("consumetime.equals=" + DEFAULT_CONSUMETIME);

        // Get all the accountsList where consumetime equals to UPDATED_CONSUMETIME
        defaultAccountsShouldNotBeFound("consumetime.equals=" + UPDATED_CONSUMETIME);
    }

    @Test
    @Transactional
    void getAllAccountsByConsumetimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where consumetime not equals to DEFAULT_CONSUMETIME
        defaultAccountsShouldNotBeFound("consumetime.notEquals=" + DEFAULT_CONSUMETIME);

        // Get all the accountsList where consumetime not equals to UPDATED_CONSUMETIME
        defaultAccountsShouldBeFound("consumetime.notEquals=" + UPDATED_CONSUMETIME);
    }

    @Test
    @Transactional
    void getAllAccountsByConsumetimeIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where consumetime in DEFAULT_CONSUMETIME or UPDATED_CONSUMETIME
        defaultAccountsShouldBeFound("consumetime.in=" + DEFAULT_CONSUMETIME + "," + UPDATED_CONSUMETIME);

        // Get all the accountsList where consumetime equals to UPDATED_CONSUMETIME
        defaultAccountsShouldNotBeFound("consumetime.in=" + UPDATED_CONSUMETIME);
    }

    @Test
    @Transactional
    void getAllAccountsByConsumetimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where consumetime is not null
        defaultAccountsShouldBeFound("consumetime.specified=true");

        // Get all the accountsList where consumetime is null
        defaultAccountsShouldNotBeFound("consumetime.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByHoteltimeIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hoteltime equals to DEFAULT_HOTELTIME
        defaultAccountsShouldBeFound("hoteltime.equals=" + DEFAULT_HOTELTIME);

        // Get all the accountsList where hoteltime equals to UPDATED_HOTELTIME
        defaultAccountsShouldNotBeFound("hoteltime.equals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllAccountsByHoteltimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hoteltime not equals to DEFAULT_HOTELTIME
        defaultAccountsShouldNotBeFound("hoteltime.notEquals=" + DEFAULT_HOTELTIME);

        // Get all the accountsList where hoteltime not equals to UPDATED_HOTELTIME
        defaultAccountsShouldBeFound("hoteltime.notEquals=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllAccountsByHoteltimeIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hoteltime in DEFAULT_HOTELTIME or UPDATED_HOTELTIME
        defaultAccountsShouldBeFound("hoteltime.in=" + DEFAULT_HOTELTIME + "," + UPDATED_HOTELTIME);

        // Get all the accountsList where hoteltime equals to UPDATED_HOTELTIME
        defaultAccountsShouldNotBeFound("hoteltime.in=" + UPDATED_HOTELTIME);
    }

    @Test
    @Transactional
    void getAllAccountsByHoteltimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hoteltime is not null
        defaultAccountsShouldBeFound("hoteltime.specified=true");

        // Get all the accountsList where hoteltime is null
        defaultAccountsShouldNotBeFound("hoteltime.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByFeenumIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where feenum equals to DEFAULT_FEENUM
        defaultAccountsShouldBeFound("feenum.equals=" + DEFAULT_FEENUM);

        // Get all the accountsList where feenum equals to UPDATED_FEENUM
        defaultAccountsShouldNotBeFound("feenum.equals=" + UPDATED_FEENUM);
    }

    @Test
    @Transactional
    void getAllAccountsByFeenumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where feenum not equals to DEFAULT_FEENUM
        defaultAccountsShouldNotBeFound("feenum.notEquals=" + DEFAULT_FEENUM);

        // Get all the accountsList where feenum not equals to UPDATED_FEENUM
        defaultAccountsShouldBeFound("feenum.notEquals=" + UPDATED_FEENUM);
    }

    @Test
    @Transactional
    void getAllAccountsByFeenumIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where feenum in DEFAULT_FEENUM or UPDATED_FEENUM
        defaultAccountsShouldBeFound("feenum.in=" + DEFAULT_FEENUM + "," + UPDATED_FEENUM);

        // Get all the accountsList where feenum equals to UPDATED_FEENUM
        defaultAccountsShouldNotBeFound("feenum.in=" + UPDATED_FEENUM);
    }

    @Test
    @Transactional
    void getAllAccountsByFeenumIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where feenum is not null
        defaultAccountsShouldBeFound("feenum.specified=true");

        // Get all the accountsList where feenum is null
        defaultAccountsShouldNotBeFound("feenum.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByFeenumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where feenum is greater than or equal to DEFAULT_FEENUM
        defaultAccountsShouldBeFound("feenum.greaterThanOrEqual=" + DEFAULT_FEENUM);

        // Get all the accountsList where feenum is greater than or equal to UPDATED_FEENUM
        defaultAccountsShouldNotBeFound("feenum.greaterThanOrEqual=" + UPDATED_FEENUM);
    }

    @Test
    @Transactional
    void getAllAccountsByFeenumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where feenum is less than or equal to DEFAULT_FEENUM
        defaultAccountsShouldBeFound("feenum.lessThanOrEqual=" + DEFAULT_FEENUM);

        // Get all the accountsList where feenum is less than or equal to SMALLER_FEENUM
        defaultAccountsShouldNotBeFound("feenum.lessThanOrEqual=" + SMALLER_FEENUM);
    }

    @Test
    @Transactional
    void getAllAccountsByFeenumIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where feenum is less than DEFAULT_FEENUM
        defaultAccountsShouldNotBeFound("feenum.lessThan=" + DEFAULT_FEENUM);

        // Get all the accountsList where feenum is less than UPDATED_FEENUM
        defaultAccountsShouldBeFound("feenum.lessThan=" + UPDATED_FEENUM);
    }

    @Test
    @Transactional
    void getAllAccountsByFeenumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where feenum is greater than DEFAULT_FEENUM
        defaultAccountsShouldNotBeFound("feenum.greaterThan=" + DEFAULT_FEENUM);

        // Get all the accountsList where feenum is greater than SMALLER_FEENUM
        defaultAccountsShouldBeFound("feenum.greaterThan=" + SMALLER_FEENUM);
    }

    @Test
    @Transactional
    void getAllAccountsByMoneyIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where money equals to DEFAULT_MONEY
        defaultAccountsShouldBeFound("money.equals=" + DEFAULT_MONEY);

        // Get all the accountsList where money equals to UPDATED_MONEY
        defaultAccountsShouldNotBeFound("money.equals=" + UPDATED_MONEY);
    }

    @Test
    @Transactional
    void getAllAccountsByMoneyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where money not equals to DEFAULT_MONEY
        defaultAccountsShouldNotBeFound("money.notEquals=" + DEFAULT_MONEY);

        // Get all the accountsList where money not equals to UPDATED_MONEY
        defaultAccountsShouldBeFound("money.notEquals=" + UPDATED_MONEY);
    }

    @Test
    @Transactional
    void getAllAccountsByMoneyIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where money in DEFAULT_MONEY or UPDATED_MONEY
        defaultAccountsShouldBeFound("money.in=" + DEFAULT_MONEY + "," + UPDATED_MONEY);

        // Get all the accountsList where money equals to UPDATED_MONEY
        defaultAccountsShouldNotBeFound("money.in=" + UPDATED_MONEY);
    }

    @Test
    @Transactional
    void getAllAccountsByMoneyIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where money is not null
        defaultAccountsShouldBeFound("money.specified=true");

        // Get all the accountsList where money is null
        defaultAccountsShouldNotBeFound("money.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByMoneyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where money is greater than or equal to DEFAULT_MONEY
        defaultAccountsShouldBeFound("money.greaterThanOrEqual=" + DEFAULT_MONEY);

        // Get all the accountsList where money is greater than or equal to UPDATED_MONEY
        defaultAccountsShouldNotBeFound("money.greaterThanOrEqual=" + UPDATED_MONEY);
    }

    @Test
    @Transactional
    void getAllAccountsByMoneyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where money is less than or equal to DEFAULT_MONEY
        defaultAccountsShouldBeFound("money.lessThanOrEqual=" + DEFAULT_MONEY);

        // Get all the accountsList where money is less than or equal to SMALLER_MONEY
        defaultAccountsShouldNotBeFound("money.lessThanOrEqual=" + SMALLER_MONEY);
    }

    @Test
    @Transactional
    void getAllAccountsByMoneyIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where money is less than DEFAULT_MONEY
        defaultAccountsShouldNotBeFound("money.lessThan=" + DEFAULT_MONEY);

        // Get all the accountsList where money is less than UPDATED_MONEY
        defaultAccountsShouldBeFound("money.lessThan=" + UPDATED_MONEY);
    }

    @Test
    @Transactional
    void getAllAccountsByMoneyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where money is greater than DEFAULT_MONEY
        defaultAccountsShouldNotBeFound("money.greaterThan=" + DEFAULT_MONEY);

        // Get all the accountsList where money is greater than SMALLER_MONEY
        defaultAccountsShouldBeFound("money.greaterThan=" + SMALLER_MONEY);
    }

    @Test
    @Transactional
    void getAllAccountsByMemoIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where memo equals to DEFAULT_MEMO
        defaultAccountsShouldBeFound("memo.equals=" + DEFAULT_MEMO);

        // Get all the accountsList where memo equals to UPDATED_MEMO
        defaultAccountsShouldNotBeFound("memo.equals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByMemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where memo not equals to DEFAULT_MEMO
        defaultAccountsShouldNotBeFound("memo.notEquals=" + DEFAULT_MEMO);

        // Get all the accountsList where memo not equals to UPDATED_MEMO
        defaultAccountsShouldBeFound("memo.notEquals=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByMemoIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where memo in DEFAULT_MEMO or UPDATED_MEMO
        defaultAccountsShouldBeFound("memo.in=" + DEFAULT_MEMO + "," + UPDATED_MEMO);

        // Get all the accountsList where memo equals to UPDATED_MEMO
        defaultAccountsShouldNotBeFound("memo.in=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByMemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where memo is not null
        defaultAccountsShouldBeFound("memo.specified=true");

        // Get all the accountsList where memo is null
        defaultAccountsShouldNotBeFound("memo.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByMemoContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where memo contains DEFAULT_MEMO
        defaultAccountsShouldBeFound("memo.contains=" + DEFAULT_MEMO);

        // Get all the accountsList where memo contains UPDATED_MEMO
        defaultAccountsShouldNotBeFound("memo.contains=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByMemoNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where memo does not contain DEFAULT_MEMO
        defaultAccountsShouldNotBeFound("memo.doesNotContain=" + DEFAULT_MEMO);

        // Get all the accountsList where memo does not contain UPDATED_MEMO
        defaultAccountsShouldBeFound("memo.doesNotContain=" + UPDATED_MEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where empn equals to DEFAULT_EMPN
        defaultAccountsShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the accountsList where empn equals to UPDATED_EMPN
        defaultAccountsShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where empn not equals to DEFAULT_EMPN
        defaultAccountsShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the accountsList where empn not equals to UPDATED_EMPN
        defaultAccountsShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultAccountsShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the accountsList where empn equals to UPDATED_EMPN
        defaultAccountsShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where empn is not null
        defaultAccountsShouldBeFound("empn.specified=true");

        // Get all the accountsList where empn is null
        defaultAccountsShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByEmpnContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where empn contains DEFAULT_EMPN
        defaultAccountsShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the accountsList where empn contains UPDATED_EMPN
        defaultAccountsShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where empn does not contain DEFAULT_EMPN
        defaultAccountsShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the accountsList where empn does not contain UPDATED_EMPN
        defaultAccountsShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByImprestIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where imprest equals to DEFAULT_IMPREST
        defaultAccountsShouldBeFound("imprest.equals=" + DEFAULT_IMPREST);

        // Get all the accountsList where imprest equals to UPDATED_IMPREST
        defaultAccountsShouldNotBeFound("imprest.equals=" + UPDATED_IMPREST);
    }

    @Test
    @Transactional
    void getAllAccountsByImprestIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where imprest not equals to DEFAULT_IMPREST
        defaultAccountsShouldNotBeFound("imprest.notEquals=" + DEFAULT_IMPREST);

        // Get all the accountsList where imprest not equals to UPDATED_IMPREST
        defaultAccountsShouldBeFound("imprest.notEquals=" + UPDATED_IMPREST);
    }

    @Test
    @Transactional
    void getAllAccountsByImprestIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where imprest in DEFAULT_IMPREST or UPDATED_IMPREST
        defaultAccountsShouldBeFound("imprest.in=" + DEFAULT_IMPREST + "," + UPDATED_IMPREST);

        // Get all the accountsList where imprest equals to UPDATED_IMPREST
        defaultAccountsShouldNotBeFound("imprest.in=" + UPDATED_IMPREST);
    }

    @Test
    @Transactional
    void getAllAccountsByImprestIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where imprest is not null
        defaultAccountsShouldBeFound("imprest.specified=true");

        // Get all the accountsList where imprest is null
        defaultAccountsShouldNotBeFound("imprest.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByImprestIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where imprest is greater than or equal to DEFAULT_IMPREST
        defaultAccountsShouldBeFound("imprest.greaterThanOrEqual=" + DEFAULT_IMPREST);

        // Get all the accountsList where imprest is greater than or equal to UPDATED_IMPREST
        defaultAccountsShouldNotBeFound("imprest.greaterThanOrEqual=" + UPDATED_IMPREST);
    }

    @Test
    @Transactional
    void getAllAccountsByImprestIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where imprest is less than or equal to DEFAULT_IMPREST
        defaultAccountsShouldBeFound("imprest.lessThanOrEqual=" + DEFAULT_IMPREST);

        // Get all the accountsList where imprest is less than or equal to SMALLER_IMPREST
        defaultAccountsShouldNotBeFound("imprest.lessThanOrEqual=" + SMALLER_IMPREST);
    }

    @Test
    @Transactional
    void getAllAccountsByImprestIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where imprest is less than DEFAULT_IMPREST
        defaultAccountsShouldNotBeFound("imprest.lessThan=" + DEFAULT_IMPREST);

        // Get all the accountsList where imprest is less than UPDATED_IMPREST
        defaultAccountsShouldBeFound("imprest.lessThan=" + UPDATED_IMPREST);
    }

    @Test
    @Transactional
    void getAllAccountsByImprestIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where imprest is greater than DEFAULT_IMPREST
        defaultAccountsShouldNotBeFound("imprest.greaterThan=" + DEFAULT_IMPREST);

        // Get all the accountsList where imprest is greater than SMALLER_IMPREST
        defaultAccountsShouldBeFound("imprest.greaterThan=" + SMALLER_IMPREST);
    }

    @Test
    @Transactional
    void getAllAccountsByPropertiyIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where propertiy equals to DEFAULT_PROPERTIY
        defaultAccountsShouldBeFound("propertiy.equals=" + DEFAULT_PROPERTIY);

        // Get all the accountsList where propertiy equals to UPDATED_PROPERTIY
        defaultAccountsShouldNotBeFound("propertiy.equals=" + UPDATED_PROPERTIY);
    }

    @Test
    @Transactional
    void getAllAccountsByPropertiyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where propertiy not equals to DEFAULT_PROPERTIY
        defaultAccountsShouldNotBeFound("propertiy.notEquals=" + DEFAULT_PROPERTIY);

        // Get all the accountsList where propertiy not equals to UPDATED_PROPERTIY
        defaultAccountsShouldBeFound("propertiy.notEquals=" + UPDATED_PROPERTIY);
    }

    @Test
    @Transactional
    void getAllAccountsByPropertiyIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where propertiy in DEFAULT_PROPERTIY or UPDATED_PROPERTIY
        defaultAccountsShouldBeFound("propertiy.in=" + DEFAULT_PROPERTIY + "," + UPDATED_PROPERTIY);

        // Get all the accountsList where propertiy equals to UPDATED_PROPERTIY
        defaultAccountsShouldNotBeFound("propertiy.in=" + UPDATED_PROPERTIY);
    }

    @Test
    @Transactional
    void getAllAccountsByPropertiyIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where propertiy is not null
        defaultAccountsShouldBeFound("propertiy.specified=true");

        // Get all the accountsList where propertiy is null
        defaultAccountsShouldNotBeFound("propertiy.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByPropertiyContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where propertiy contains DEFAULT_PROPERTIY
        defaultAccountsShouldBeFound("propertiy.contains=" + DEFAULT_PROPERTIY);

        // Get all the accountsList where propertiy contains UPDATED_PROPERTIY
        defaultAccountsShouldNotBeFound("propertiy.contains=" + UPDATED_PROPERTIY);
    }

    @Test
    @Transactional
    void getAllAccountsByPropertiyNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where propertiy does not contain DEFAULT_PROPERTIY
        defaultAccountsShouldNotBeFound("propertiy.doesNotContain=" + DEFAULT_PROPERTIY);

        // Get all the accountsList where propertiy does not contain UPDATED_PROPERTIY
        defaultAccountsShouldBeFound("propertiy.doesNotContain=" + UPDATED_PROPERTIY);
    }

    @Test
    @Transactional
    void getAllAccountsByEarntypenIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where earntypen equals to DEFAULT_EARNTYPEN
        defaultAccountsShouldBeFound("earntypen.equals=" + DEFAULT_EARNTYPEN);

        // Get all the accountsList where earntypen equals to UPDATED_EARNTYPEN
        defaultAccountsShouldNotBeFound("earntypen.equals=" + UPDATED_EARNTYPEN);
    }

    @Test
    @Transactional
    void getAllAccountsByEarntypenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where earntypen not equals to DEFAULT_EARNTYPEN
        defaultAccountsShouldNotBeFound("earntypen.notEquals=" + DEFAULT_EARNTYPEN);

        // Get all the accountsList where earntypen not equals to UPDATED_EARNTYPEN
        defaultAccountsShouldBeFound("earntypen.notEquals=" + UPDATED_EARNTYPEN);
    }

    @Test
    @Transactional
    void getAllAccountsByEarntypenIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where earntypen in DEFAULT_EARNTYPEN or UPDATED_EARNTYPEN
        defaultAccountsShouldBeFound("earntypen.in=" + DEFAULT_EARNTYPEN + "," + UPDATED_EARNTYPEN);

        // Get all the accountsList where earntypen equals to UPDATED_EARNTYPEN
        defaultAccountsShouldNotBeFound("earntypen.in=" + UPDATED_EARNTYPEN);
    }

    @Test
    @Transactional
    void getAllAccountsByEarntypenIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where earntypen is not null
        defaultAccountsShouldBeFound("earntypen.specified=true");

        // Get all the accountsList where earntypen is null
        defaultAccountsShouldNotBeFound("earntypen.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByEarntypenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where earntypen is greater than or equal to DEFAULT_EARNTYPEN
        defaultAccountsShouldBeFound("earntypen.greaterThanOrEqual=" + DEFAULT_EARNTYPEN);

        // Get all the accountsList where earntypen is greater than or equal to UPDATED_EARNTYPEN
        defaultAccountsShouldNotBeFound("earntypen.greaterThanOrEqual=" + UPDATED_EARNTYPEN);
    }

    @Test
    @Transactional
    void getAllAccountsByEarntypenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where earntypen is less than or equal to DEFAULT_EARNTYPEN
        defaultAccountsShouldBeFound("earntypen.lessThanOrEqual=" + DEFAULT_EARNTYPEN);

        // Get all the accountsList where earntypen is less than or equal to SMALLER_EARNTYPEN
        defaultAccountsShouldNotBeFound("earntypen.lessThanOrEqual=" + SMALLER_EARNTYPEN);
    }

    @Test
    @Transactional
    void getAllAccountsByEarntypenIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where earntypen is less than DEFAULT_EARNTYPEN
        defaultAccountsShouldNotBeFound("earntypen.lessThan=" + DEFAULT_EARNTYPEN);

        // Get all the accountsList where earntypen is less than UPDATED_EARNTYPEN
        defaultAccountsShouldBeFound("earntypen.lessThan=" + UPDATED_EARNTYPEN);
    }

    @Test
    @Transactional
    void getAllAccountsByEarntypenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where earntypen is greater than DEFAULT_EARNTYPEN
        defaultAccountsShouldNotBeFound("earntypen.greaterThan=" + DEFAULT_EARNTYPEN);

        // Get all the accountsList where earntypen is greater than SMALLER_EARNTYPEN
        defaultAccountsShouldBeFound("earntypen.greaterThan=" + SMALLER_EARNTYPEN);
    }

    @Test
    @Transactional
    void getAllAccountsByPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where payment equals to DEFAULT_PAYMENT
        defaultAccountsShouldBeFound("payment.equals=" + DEFAULT_PAYMENT);

        // Get all the accountsList where payment equals to UPDATED_PAYMENT
        defaultAccountsShouldNotBeFound("payment.equals=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void getAllAccountsByPaymentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where payment not equals to DEFAULT_PAYMENT
        defaultAccountsShouldNotBeFound("payment.notEquals=" + DEFAULT_PAYMENT);

        // Get all the accountsList where payment not equals to UPDATED_PAYMENT
        defaultAccountsShouldBeFound("payment.notEquals=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void getAllAccountsByPaymentIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where payment in DEFAULT_PAYMENT or UPDATED_PAYMENT
        defaultAccountsShouldBeFound("payment.in=" + DEFAULT_PAYMENT + "," + UPDATED_PAYMENT);

        // Get all the accountsList where payment equals to UPDATED_PAYMENT
        defaultAccountsShouldNotBeFound("payment.in=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void getAllAccountsByPaymentIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where payment is not null
        defaultAccountsShouldBeFound("payment.specified=true");

        // Get all the accountsList where payment is null
        defaultAccountsShouldNotBeFound("payment.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByPaymentIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where payment is greater than or equal to DEFAULT_PAYMENT
        defaultAccountsShouldBeFound("payment.greaterThanOrEqual=" + DEFAULT_PAYMENT);

        // Get all the accountsList where payment is greater than or equal to UPDATED_PAYMENT
        defaultAccountsShouldNotBeFound("payment.greaterThanOrEqual=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void getAllAccountsByPaymentIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where payment is less than or equal to DEFAULT_PAYMENT
        defaultAccountsShouldBeFound("payment.lessThanOrEqual=" + DEFAULT_PAYMENT);

        // Get all the accountsList where payment is less than or equal to SMALLER_PAYMENT
        defaultAccountsShouldNotBeFound("payment.lessThanOrEqual=" + SMALLER_PAYMENT);
    }

    @Test
    @Transactional
    void getAllAccountsByPaymentIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where payment is less than DEFAULT_PAYMENT
        defaultAccountsShouldNotBeFound("payment.lessThan=" + DEFAULT_PAYMENT);

        // Get all the accountsList where payment is less than UPDATED_PAYMENT
        defaultAccountsShouldBeFound("payment.lessThan=" + UPDATED_PAYMENT);
    }

    @Test
    @Transactional
    void getAllAccountsByPaymentIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where payment is greater than DEFAULT_PAYMENT
        defaultAccountsShouldNotBeFound("payment.greaterThan=" + DEFAULT_PAYMENT);

        // Get all the accountsList where payment is greater than SMALLER_PAYMENT
        defaultAccountsShouldBeFound("payment.greaterThan=" + SMALLER_PAYMENT);
    }

    @Test
    @Transactional
    void getAllAccountsByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where roomn equals to DEFAULT_ROOMN
        defaultAccountsShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the accountsList where roomn equals to UPDATED_ROOMN
        defaultAccountsShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllAccountsByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where roomn not equals to DEFAULT_ROOMN
        defaultAccountsShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the accountsList where roomn not equals to UPDATED_ROOMN
        defaultAccountsShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllAccountsByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultAccountsShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the accountsList where roomn equals to UPDATED_ROOMN
        defaultAccountsShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllAccountsByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where roomn is not null
        defaultAccountsShouldBeFound("roomn.specified=true");

        // Get all the accountsList where roomn is null
        defaultAccountsShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByRoomnContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where roomn contains DEFAULT_ROOMN
        defaultAccountsShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the accountsList where roomn contains UPDATED_ROOMN
        defaultAccountsShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllAccountsByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where roomn does not contain DEFAULT_ROOMN
        defaultAccountsShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the accountsList where roomn does not contain UPDATED_ROOMN
        defaultAccountsShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllAccountsByUlogogramIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ulogogram equals to DEFAULT_ULOGOGRAM
        defaultAccountsShouldBeFound("ulogogram.equals=" + DEFAULT_ULOGOGRAM);

        // Get all the accountsList where ulogogram equals to UPDATED_ULOGOGRAM
        defaultAccountsShouldNotBeFound("ulogogram.equals=" + UPDATED_ULOGOGRAM);
    }

    @Test
    @Transactional
    void getAllAccountsByUlogogramIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ulogogram not equals to DEFAULT_ULOGOGRAM
        defaultAccountsShouldNotBeFound("ulogogram.notEquals=" + DEFAULT_ULOGOGRAM);

        // Get all the accountsList where ulogogram not equals to UPDATED_ULOGOGRAM
        defaultAccountsShouldBeFound("ulogogram.notEquals=" + UPDATED_ULOGOGRAM);
    }

    @Test
    @Transactional
    void getAllAccountsByUlogogramIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ulogogram in DEFAULT_ULOGOGRAM or UPDATED_ULOGOGRAM
        defaultAccountsShouldBeFound("ulogogram.in=" + DEFAULT_ULOGOGRAM + "," + UPDATED_ULOGOGRAM);

        // Get all the accountsList where ulogogram equals to UPDATED_ULOGOGRAM
        defaultAccountsShouldNotBeFound("ulogogram.in=" + UPDATED_ULOGOGRAM);
    }

    @Test
    @Transactional
    void getAllAccountsByUlogogramIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ulogogram is not null
        defaultAccountsShouldBeFound("ulogogram.specified=true");

        // Get all the accountsList where ulogogram is null
        defaultAccountsShouldNotBeFound("ulogogram.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByUlogogramContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ulogogram contains DEFAULT_ULOGOGRAM
        defaultAccountsShouldBeFound("ulogogram.contains=" + DEFAULT_ULOGOGRAM);

        // Get all the accountsList where ulogogram contains UPDATED_ULOGOGRAM
        defaultAccountsShouldNotBeFound("ulogogram.contains=" + UPDATED_ULOGOGRAM);
    }

    @Test
    @Transactional
    void getAllAccountsByUlogogramNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ulogogram does not contain DEFAULT_ULOGOGRAM
        defaultAccountsShouldNotBeFound("ulogogram.doesNotContain=" + DEFAULT_ULOGOGRAM);

        // Get all the accountsList where ulogogram does not contain UPDATED_ULOGOGRAM
        defaultAccountsShouldBeFound("ulogogram.doesNotContain=" + UPDATED_ULOGOGRAM);
    }

    @Test
    @Transactional
    void getAllAccountsByLkIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lk equals to DEFAULT_LK
        defaultAccountsShouldBeFound("lk.equals=" + DEFAULT_LK);

        // Get all the accountsList where lk equals to UPDATED_LK
        defaultAccountsShouldNotBeFound("lk.equals=" + UPDATED_LK);
    }

    @Test
    @Transactional
    void getAllAccountsByLkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lk not equals to DEFAULT_LK
        defaultAccountsShouldNotBeFound("lk.notEquals=" + DEFAULT_LK);

        // Get all the accountsList where lk not equals to UPDATED_LK
        defaultAccountsShouldBeFound("lk.notEquals=" + UPDATED_LK);
    }

    @Test
    @Transactional
    void getAllAccountsByLkIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lk in DEFAULT_LK or UPDATED_LK
        defaultAccountsShouldBeFound("lk.in=" + DEFAULT_LK + "," + UPDATED_LK);

        // Get all the accountsList where lk equals to UPDATED_LK
        defaultAccountsShouldNotBeFound("lk.in=" + UPDATED_LK);
    }

    @Test
    @Transactional
    void getAllAccountsByLkIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lk is not null
        defaultAccountsShouldBeFound("lk.specified=true");

        // Get all the accountsList where lk is null
        defaultAccountsShouldNotBeFound("lk.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByLkIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lk is greater than or equal to DEFAULT_LK
        defaultAccountsShouldBeFound("lk.greaterThanOrEqual=" + DEFAULT_LK);

        // Get all the accountsList where lk is greater than or equal to UPDATED_LK
        defaultAccountsShouldNotBeFound("lk.greaterThanOrEqual=" + UPDATED_LK);
    }

    @Test
    @Transactional
    void getAllAccountsByLkIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lk is less than or equal to DEFAULT_LK
        defaultAccountsShouldBeFound("lk.lessThanOrEqual=" + DEFAULT_LK);

        // Get all the accountsList where lk is less than or equal to SMALLER_LK
        defaultAccountsShouldNotBeFound("lk.lessThanOrEqual=" + SMALLER_LK);
    }

    @Test
    @Transactional
    void getAllAccountsByLkIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lk is less than DEFAULT_LK
        defaultAccountsShouldNotBeFound("lk.lessThan=" + DEFAULT_LK);

        // Get all the accountsList where lk is less than UPDATED_LK
        defaultAccountsShouldBeFound("lk.lessThan=" + UPDATED_LK);
    }

    @Test
    @Transactional
    void getAllAccountsByLkIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where lk is greater than DEFAULT_LK
        defaultAccountsShouldNotBeFound("lk.greaterThan=" + DEFAULT_LK);

        // Get all the accountsList where lk is greater than SMALLER_LK
        defaultAccountsShouldBeFound("lk.greaterThan=" + SMALLER_LK);
    }

    @Test
    @Transactional
    void getAllAccountsByAccIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where acc equals to DEFAULT_ACC
        defaultAccountsShouldBeFound("acc.equals=" + DEFAULT_ACC);

        // Get all the accountsList where acc equals to UPDATED_ACC
        defaultAccountsShouldNotBeFound("acc.equals=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccountsByAccIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where acc not equals to DEFAULT_ACC
        defaultAccountsShouldNotBeFound("acc.notEquals=" + DEFAULT_ACC);

        // Get all the accountsList where acc not equals to UPDATED_ACC
        defaultAccountsShouldBeFound("acc.notEquals=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccountsByAccIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where acc in DEFAULT_ACC or UPDATED_ACC
        defaultAccountsShouldBeFound("acc.in=" + DEFAULT_ACC + "," + UPDATED_ACC);

        // Get all the accountsList where acc equals to UPDATED_ACC
        defaultAccountsShouldNotBeFound("acc.in=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccountsByAccIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where acc is not null
        defaultAccountsShouldBeFound("acc.specified=true");

        // Get all the accountsList where acc is null
        defaultAccountsShouldNotBeFound("acc.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByAccContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where acc contains DEFAULT_ACC
        defaultAccountsShouldBeFound("acc.contains=" + DEFAULT_ACC);

        // Get all the accountsList where acc contains UPDATED_ACC
        defaultAccountsShouldNotBeFound("acc.contains=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccountsByAccNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where acc does not contain DEFAULT_ACC
        defaultAccountsShouldNotBeFound("acc.doesNotContain=" + DEFAULT_ACC);

        // Get all the accountsList where acc does not contain UPDATED_ACC
        defaultAccountsShouldBeFound("acc.doesNotContain=" + UPDATED_ACC);
    }

    @Test
    @Transactional
    void getAllAccountsByJzSignIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzSign equals to DEFAULT_JZ_SIGN
        defaultAccountsShouldBeFound("jzSign.equals=" + DEFAULT_JZ_SIGN);

        // Get all the accountsList where jzSign equals to UPDATED_JZ_SIGN
        defaultAccountsShouldNotBeFound("jzSign.equals=" + UPDATED_JZ_SIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByJzSignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzSign not equals to DEFAULT_JZ_SIGN
        defaultAccountsShouldNotBeFound("jzSign.notEquals=" + DEFAULT_JZ_SIGN);

        // Get all the accountsList where jzSign not equals to UPDATED_JZ_SIGN
        defaultAccountsShouldBeFound("jzSign.notEquals=" + UPDATED_JZ_SIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByJzSignIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzSign in DEFAULT_JZ_SIGN or UPDATED_JZ_SIGN
        defaultAccountsShouldBeFound("jzSign.in=" + DEFAULT_JZ_SIGN + "," + UPDATED_JZ_SIGN);

        // Get all the accountsList where jzSign equals to UPDATED_JZ_SIGN
        defaultAccountsShouldNotBeFound("jzSign.in=" + UPDATED_JZ_SIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByJzSignIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzSign is not null
        defaultAccountsShouldBeFound("jzSign.specified=true");

        // Get all the accountsList where jzSign is null
        defaultAccountsShouldNotBeFound("jzSign.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByJzSignIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzSign is greater than or equal to DEFAULT_JZ_SIGN
        defaultAccountsShouldBeFound("jzSign.greaterThanOrEqual=" + DEFAULT_JZ_SIGN);

        // Get all the accountsList where jzSign is greater than or equal to UPDATED_JZ_SIGN
        defaultAccountsShouldNotBeFound("jzSign.greaterThanOrEqual=" + UPDATED_JZ_SIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByJzSignIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzSign is less than or equal to DEFAULT_JZ_SIGN
        defaultAccountsShouldBeFound("jzSign.lessThanOrEqual=" + DEFAULT_JZ_SIGN);

        // Get all the accountsList where jzSign is less than or equal to SMALLER_JZ_SIGN
        defaultAccountsShouldNotBeFound("jzSign.lessThanOrEqual=" + SMALLER_JZ_SIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByJzSignIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzSign is less than DEFAULT_JZ_SIGN
        defaultAccountsShouldNotBeFound("jzSign.lessThan=" + DEFAULT_JZ_SIGN);

        // Get all the accountsList where jzSign is less than UPDATED_JZ_SIGN
        defaultAccountsShouldBeFound("jzSign.lessThan=" + UPDATED_JZ_SIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByJzSignIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzSign is greater than DEFAULT_JZ_SIGN
        defaultAccountsShouldNotBeFound("jzSign.greaterThan=" + DEFAULT_JZ_SIGN);

        // Get all the accountsList where jzSign is greater than SMALLER_JZ_SIGN
        defaultAccountsShouldBeFound("jzSign.greaterThan=" + SMALLER_JZ_SIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByJzflagIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzflag equals to DEFAULT_JZFLAG
        defaultAccountsShouldBeFound("jzflag.equals=" + DEFAULT_JZFLAG);

        // Get all the accountsList where jzflag equals to UPDATED_JZFLAG
        defaultAccountsShouldNotBeFound("jzflag.equals=" + UPDATED_JZFLAG);
    }

    @Test
    @Transactional
    void getAllAccountsByJzflagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzflag not equals to DEFAULT_JZFLAG
        defaultAccountsShouldNotBeFound("jzflag.notEquals=" + DEFAULT_JZFLAG);

        // Get all the accountsList where jzflag not equals to UPDATED_JZFLAG
        defaultAccountsShouldBeFound("jzflag.notEquals=" + UPDATED_JZFLAG);
    }

    @Test
    @Transactional
    void getAllAccountsByJzflagIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzflag in DEFAULT_JZFLAG or UPDATED_JZFLAG
        defaultAccountsShouldBeFound("jzflag.in=" + DEFAULT_JZFLAG + "," + UPDATED_JZFLAG);

        // Get all the accountsList where jzflag equals to UPDATED_JZFLAG
        defaultAccountsShouldNotBeFound("jzflag.in=" + UPDATED_JZFLAG);
    }

    @Test
    @Transactional
    void getAllAccountsByJzflagIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzflag is not null
        defaultAccountsShouldBeFound("jzflag.specified=true");

        // Get all the accountsList where jzflag is null
        defaultAccountsShouldNotBeFound("jzflag.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByJzflagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzflag is greater than or equal to DEFAULT_JZFLAG
        defaultAccountsShouldBeFound("jzflag.greaterThanOrEqual=" + DEFAULT_JZFLAG);

        // Get all the accountsList where jzflag is greater than or equal to UPDATED_JZFLAG
        defaultAccountsShouldNotBeFound("jzflag.greaterThanOrEqual=" + UPDATED_JZFLAG);
    }

    @Test
    @Transactional
    void getAllAccountsByJzflagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzflag is less than or equal to DEFAULT_JZFLAG
        defaultAccountsShouldBeFound("jzflag.lessThanOrEqual=" + DEFAULT_JZFLAG);

        // Get all the accountsList where jzflag is less than or equal to SMALLER_JZFLAG
        defaultAccountsShouldNotBeFound("jzflag.lessThanOrEqual=" + SMALLER_JZFLAG);
    }

    @Test
    @Transactional
    void getAllAccountsByJzflagIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzflag is less than DEFAULT_JZFLAG
        defaultAccountsShouldNotBeFound("jzflag.lessThan=" + DEFAULT_JZFLAG);

        // Get all the accountsList where jzflag is less than UPDATED_JZFLAG
        defaultAccountsShouldBeFound("jzflag.lessThan=" + UPDATED_JZFLAG);
    }

    @Test
    @Transactional
    void getAllAccountsByJzflagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzflag is greater than DEFAULT_JZFLAG
        defaultAccountsShouldNotBeFound("jzflag.greaterThan=" + DEFAULT_JZFLAG);

        // Get all the accountsList where jzflag is greater than SMALLER_JZFLAG
        defaultAccountsShouldBeFound("jzflag.greaterThan=" + SMALLER_JZFLAG);
    }

    @Test
    @Transactional
    void getAllAccountsBySignIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sign equals to DEFAULT_SIGN
        defaultAccountsShouldBeFound("sign.equals=" + DEFAULT_SIGN);

        // Get all the accountsList where sign equals to UPDATED_SIGN
        defaultAccountsShouldNotBeFound("sign.equals=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllAccountsBySignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sign not equals to DEFAULT_SIGN
        defaultAccountsShouldNotBeFound("sign.notEquals=" + DEFAULT_SIGN);

        // Get all the accountsList where sign not equals to UPDATED_SIGN
        defaultAccountsShouldBeFound("sign.notEquals=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllAccountsBySignIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sign in DEFAULT_SIGN or UPDATED_SIGN
        defaultAccountsShouldBeFound("sign.in=" + DEFAULT_SIGN + "," + UPDATED_SIGN);

        // Get all the accountsList where sign equals to UPDATED_SIGN
        defaultAccountsShouldNotBeFound("sign.in=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllAccountsBySignIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sign is not null
        defaultAccountsShouldBeFound("sign.specified=true");

        // Get all the accountsList where sign is null
        defaultAccountsShouldNotBeFound("sign.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsBySignContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sign contains DEFAULT_SIGN
        defaultAccountsShouldBeFound("sign.contains=" + DEFAULT_SIGN);

        // Get all the accountsList where sign contains UPDATED_SIGN
        defaultAccountsShouldNotBeFound("sign.contains=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllAccountsBySignNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sign does not contain DEFAULT_SIGN
        defaultAccountsShouldNotBeFound("sign.doesNotContain=" + DEFAULT_SIGN);

        // Get all the accountsList where sign does not contain UPDATED_SIGN
        defaultAccountsShouldBeFound("sign.doesNotContain=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByBsIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bs equals to DEFAULT_BS
        defaultAccountsShouldBeFound("bs.equals=" + DEFAULT_BS);

        // Get all the accountsList where bs equals to UPDATED_BS
        defaultAccountsShouldNotBeFound("bs.equals=" + UPDATED_BS);
    }

    @Test
    @Transactional
    void getAllAccountsByBsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bs not equals to DEFAULT_BS
        defaultAccountsShouldNotBeFound("bs.notEquals=" + DEFAULT_BS);

        // Get all the accountsList where bs not equals to UPDATED_BS
        defaultAccountsShouldBeFound("bs.notEquals=" + UPDATED_BS);
    }

    @Test
    @Transactional
    void getAllAccountsByBsIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bs in DEFAULT_BS or UPDATED_BS
        defaultAccountsShouldBeFound("bs.in=" + DEFAULT_BS + "," + UPDATED_BS);

        // Get all the accountsList where bs equals to UPDATED_BS
        defaultAccountsShouldNotBeFound("bs.in=" + UPDATED_BS);
    }

    @Test
    @Transactional
    void getAllAccountsByBsIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bs is not null
        defaultAccountsShouldBeFound("bs.specified=true");

        // Get all the accountsList where bs is null
        defaultAccountsShouldNotBeFound("bs.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByBsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bs is greater than or equal to DEFAULT_BS
        defaultAccountsShouldBeFound("bs.greaterThanOrEqual=" + DEFAULT_BS);

        // Get all the accountsList where bs is greater than or equal to UPDATED_BS
        defaultAccountsShouldNotBeFound("bs.greaterThanOrEqual=" + UPDATED_BS);
    }

    @Test
    @Transactional
    void getAllAccountsByBsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bs is less than or equal to DEFAULT_BS
        defaultAccountsShouldBeFound("bs.lessThanOrEqual=" + DEFAULT_BS);

        // Get all the accountsList where bs is less than or equal to SMALLER_BS
        defaultAccountsShouldNotBeFound("bs.lessThanOrEqual=" + SMALLER_BS);
    }

    @Test
    @Transactional
    void getAllAccountsByBsIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bs is less than DEFAULT_BS
        defaultAccountsShouldNotBeFound("bs.lessThan=" + DEFAULT_BS);

        // Get all the accountsList where bs is less than UPDATED_BS
        defaultAccountsShouldBeFound("bs.lessThan=" + UPDATED_BS);
    }

    @Test
    @Transactional
    void getAllAccountsByBsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bs is greater than DEFAULT_BS
        defaultAccountsShouldNotBeFound("bs.greaterThan=" + DEFAULT_BS);

        // Get all the accountsList where bs is greater than SMALLER_BS
        defaultAccountsShouldBeFound("bs.greaterThan=" + SMALLER_BS);
    }

    @Test
    @Transactional
    void getAllAccountsByJzhotelIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzhotel equals to DEFAULT_JZHOTEL
        defaultAccountsShouldBeFound("jzhotel.equals=" + DEFAULT_JZHOTEL);

        // Get all the accountsList where jzhotel equals to UPDATED_JZHOTEL
        defaultAccountsShouldNotBeFound("jzhotel.equals=" + UPDATED_JZHOTEL);
    }

    @Test
    @Transactional
    void getAllAccountsByJzhotelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzhotel not equals to DEFAULT_JZHOTEL
        defaultAccountsShouldNotBeFound("jzhotel.notEquals=" + DEFAULT_JZHOTEL);

        // Get all the accountsList where jzhotel not equals to UPDATED_JZHOTEL
        defaultAccountsShouldBeFound("jzhotel.notEquals=" + UPDATED_JZHOTEL);
    }

    @Test
    @Transactional
    void getAllAccountsByJzhotelIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzhotel in DEFAULT_JZHOTEL or UPDATED_JZHOTEL
        defaultAccountsShouldBeFound("jzhotel.in=" + DEFAULT_JZHOTEL + "," + UPDATED_JZHOTEL);

        // Get all the accountsList where jzhotel equals to UPDATED_JZHOTEL
        defaultAccountsShouldNotBeFound("jzhotel.in=" + UPDATED_JZHOTEL);
    }

    @Test
    @Transactional
    void getAllAccountsByJzhotelIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzhotel is not null
        defaultAccountsShouldBeFound("jzhotel.specified=true");

        // Get all the accountsList where jzhotel is null
        defaultAccountsShouldNotBeFound("jzhotel.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByJzempnIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzempn equals to DEFAULT_JZEMPN
        defaultAccountsShouldBeFound("jzempn.equals=" + DEFAULT_JZEMPN);

        // Get all the accountsList where jzempn equals to UPDATED_JZEMPN
        defaultAccountsShouldNotBeFound("jzempn.equals=" + UPDATED_JZEMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByJzempnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzempn not equals to DEFAULT_JZEMPN
        defaultAccountsShouldNotBeFound("jzempn.notEquals=" + DEFAULT_JZEMPN);

        // Get all the accountsList where jzempn not equals to UPDATED_JZEMPN
        defaultAccountsShouldBeFound("jzempn.notEquals=" + UPDATED_JZEMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByJzempnIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzempn in DEFAULT_JZEMPN or UPDATED_JZEMPN
        defaultAccountsShouldBeFound("jzempn.in=" + DEFAULT_JZEMPN + "," + UPDATED_JZEMPN);

        // Get all the accountsList where jzempn equals to UPDATED_JZEMPN
        defaultAccountsShouldNotBeFound("jzempn.in=" + UPDATED_JZEMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByJzempnIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzempn is not null
        defaultAccountsShouldBeFound("jzempn.specified=true");

        // Get all the accountsList where jzempn is null
        defaultAccountsShouldNotBeFound("jzempn.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByJzempnContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzempn contains DEFAULT_JZEMPN
        defaultAccountsShouldBeFound("jzempn.contains=" + DEFAULT_JZEMPN);

        // Get all the accountsList where jzempn contains UPDATED_JZEMPN
        defaultAccountsShouldNotBeFound("jzempn.contains=" + UPDATED_JZEMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByJzempnNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jzempn does not contain DEFAULT_JZEMPN
        defaultAccountsShouldNotBeFound("jzempn.doesNotContain=" + DEFAULT_JZEMPN);

        // Get all the accountsList where jzempn does not contain UPDATED_JZEMPN
        defaultAccountsShouldBeFound("jzempn.doesNotContain=" + UPDATED_JZEMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByJztimeIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jztime equals to DEFAULT_JZTIME
        defaultAccountsShouldBeFound("jztime.equals=" + DEFAULT_JZTIME);

        // Get all the accountsList where jztime equals to UPDATED_JZTIME
        defaultAccountsShouldNotBeFound("jztime.equals=" + UPDATED_JZTIME);
    }

    @Test
    @Transactional
    void getAllAccountsByJztimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jztime not equals to DEFAULT_JZTIME
        defaultAccountsShouldNotBeFound("jztime.notEquals=" + DEFAULT_JZTIME);

        // Get all the accountsList where jztime not equals to UPDATED_JZTIME
        defaultAccountsShouldBeFound("jztime.notEquals=" + UPDATED_JZTIME);
    }

    @Test
    @Transactional
    void getAllAccountsByJztimeIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jztime in DEFAULT_JZTIME or UPDATED_JZTIME
        defaultAccountsShouldBeFound("jztime.in=" + DEFAULT_JZTIME + "," + UPDATED_JZTIME);

        // Get all the accountsList where jztime equals to UPDATED_JZTIME
        defaultAccountsShouldNotBeFound("jztime.in=" + UPDATED_JZTIME);
    }

    @Test
    @Transactional
    void getAllAccountsByJztimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where jztime is not null
        defaultAccountsShouldBeFound("jztime.specified=true");

        // Get all the accountsList where jztime is null
        defaultAccountsShouldNotBeFound("jztime.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByChonghongIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where chonghong equals to DEFAULT_CHONGHONG
        defaultAccountsShouldBeFound("chonghong.equals=" + DEFAULT_CHONGHONG);

        // Get all the accountsList where chonghong equals to UPDATED_CHONGHONG
        defaultAccountsShouldNotBeFound("chonghong.equals=" + UPDATED_CHONGHONG);
    }

    @Test
    @Transactional
    void getAllAccountsByChonghongIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where chonghong not equals to DEFAULT_CHONGHONG
        defaultAccountsShouldNotBeFound("chonghong.notEquals=" + DEFAULT_CHONGHONG);

        // Get all the accountsList where chonghong not equals to UPDATED_CHONGHONG
        defaultAccountsShouldBeFound("chonghong.notEquals=" + UPDATED_CHONGHONG);
    }

    @Test
    @Transactional
    void getAllAccountsByChonghongIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where chonghong in DEFAULT_CHONGHONG or UPDATED_CHONGHONG
        defaultAccountsShouldBeFound("chonghong.in=" + DEFAULT_CHONGHONG + "," + UPDATED_CHONGHONG);

        // Get all the accountsList where chonghong equals to UPDATED_CHONGHONG
        defaultAccountsShouldNotBeFound("chonghong.in=" + UPDATED_CHONGHONG);
    }

    @Test
    @Transactional
    void getAllAccountsByChonghongIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where chonghong is not null
        defaultAccountsShouldBeFound("chonghong.specified=true");

        // Get all the accountsList where chonghong is null
        defaultAccountsShouldNotBeFound("chonghong.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByChonghongIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where chonghong is greater than or equal to DEFAULT_CHONGHONG
        defaultAccountsShouldBeFound("chonghong.greaterThanOrEqual=" + DEFAULT_CHONGHONG);

        // Get all the accountsList where chonghong is greater than or equal to UPDATED_CHONGHONG
        defaultAccountsShouldNotBeFound("chonghong.greaterThanOrEqual=" + UPDATED_CHONGHONG);
    }

    @Test
    @Transactional
    void getAllAccountsByChonghongIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where chonghong is less than or equal to DEFAULT_CHONGHONG
        defaultAccountsShouldBeFound("chonghong.lessThanOrEqual=" + DEFAULT_CHONGHONG);

        // Get all the accountsList where chonghong is less than or equal to SMALLER_CHONGHONG
        defaultAccountsShouldNotBeFound("chonghong.lessThanOrEqual=" + SMALLER_CHONGHONG);
    }

    @Test
    @Transactional
    void getAllAccountsByChonghongIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where chonghong is less than DEFAULT_CHONGHONG
        defaultAccountsShouldNotBeFound("chonghong.lessThan=" + DEFAULT_CHONGHONG);

        // Get all the accountsList where chonghong is less than UPDATED_CHONGHONG
        defaultAccountsShouldBeFound("chonghong.lessThan=" + UPDATED_CHONGHONG);
    }

    @Test
    @Transactional
    void getAllAccountsByChonghongIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where chonghong is greater than DEFAULT_CHONGHONG
        defaultAccountsShouldNotBeFound("chonghong.greaterThan=" + DEFAULT_CHONGHONG);

        // Get all the accountsList where chonghong is greater than SMALLER_CHONGHONG
        defaultAccountsShouldBeFound("chonghong.greaterThan=" + SMALLER_CHONGHONG);
    }

    @Test
    @Transactional
    void getAllAccountsByBillnoIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where billno equals to DEFAULT_BILLNO
        defaultAccountsShouldBeFound("billno.equals=" + DEFAULT_BILLNO);

        // Get all the accountsList where billno equals to UPDATED_BILLNO
        defaultAccountsShouldNotBeFound("billno.equals=" + UPDATED_BILLNO);
    }

    @Test
    @Transactional
    void getAllAccountsByBillnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where billno not equals to DEFAULT_BILLNO
        defaultAccountsShouldNotBeFound("billno.notEquals=" + DEFAULT_BILLNO);

        // Get all the accountsList where billno not equals to UPDATED_BILLNO
        defaultAccountsShouldBeFound("billno.notEquals=" + UPDATED_BILLNO);
    }

    @Test
    @Transactional
    void getAllAccountsByBillnoIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where billno in DEFAULT_BILLNO or UPDATED_BILLNO
        defaultAccountsShouldBeFound("billno.in=" + DEFAULT_BILLNO + "," + UPDATED_BILLNO);

        // Get all the accountsList where billno equals to UPDATED_BILLNO
        defaultAccountsShouldNotBeFound("billno.in=" + UPDATED_BILLNO);
    }

    @Test
    @Transactional
    void getAllAccountsByBillnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where billno is not null
        defaultAccountsShouldBeFound("billno.specified=true");

        // Get all the accountsList where billno is null
        defaultAccountsShouldNotBeFound("billno.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByBillnoContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where billno contains DEFAULT_BILLNO
        defaultAccountsShouldBeFound("billno.contains=" + DEFAULT_BILLNO);

        // Get all the accountsList where billno contains UPDATED_BILLNO
        defaultAccountsShouldNotBeFound("billno.contains=" + UPDATED_BILLNO);
    }

    @Test
    @Transactional
    void getAllAccountsByBillnoNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where billno does not contain DEFAULT_BILLNO
        defaultAccountsShouldNotBeFound("billno.doesNotContain=" + DEFAULT_BILLNO);

        // Get all the accountsList where billno does not contain UPDATED_BILLNO
        defaultAccountsShouldBeFound("billno.doesNotContain=" + UPDATED_BILLNO);
    }

    @Test
    @Transactional
    void getAllAccountsByPrintcountIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where printcount equals to DEFAULT_PRINTCOUNT
        defaultAccountsShouldBeFound("printcount.equals=" + DEFAULT_PRINTCOUNT);

        // Get all the accountsList where printcount equals to UPDATED_PRINTCOUNT
        defaultAccountsShouldNotBeFound("printcount.equals=" + UPDATED_PRINTCOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByPrintcountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where printcount not equals to DEFAULT_PRINTCOUNT
        defaultAccountsShouldNotBeFound("printcount.notEquals=" + DEFAULT_PRINTCOUNT);

        // Get all the accountsList where printcount not equals to UPDATED_PRINTCOUNT
        defaultAccountsShouldBeFound("printcount.notEquals=" + UPDATED_PRINTCOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByPrintcountIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where printcount in DEFAULT_PRINTCOUNT or UPDATED_PRINTCOUNT
        defaultAccountsShouldBeFound("printcount.in=" + DEFAULT_PRINTCOUNT + "," + UPDATED_PRINTCOUNT);

        // Get all the accountsList where printcount equals to UPDATED_PRINTCOUNT
        defaultAccountsShouldNotBeFound("printcount.in=" + UPDATED_PRINTCOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByPrintcountIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where printcount is not null
        defaultAccountsShouldBeFound("printcount.specified=true");

        // Get all the accountsList where printcount is null
        defaultAccountsShouldNotBeFound("printcount.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByPrintcountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where printcount is greater than or equal to DEFAULT_PRINTCOUNT
        defaultAccountsShouldBeFound("printcount.greaterThanOrEqual=" + DEFAULT_PRINTCOUNT);

        // Get all the accountsList where printcount is greater than or equal to UPDATED_PRINTCOUNT
        defaultAccountsShouldNotBeFound("printcount.greaterThanOrEqual=" + UPDATED_PRINTCOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByPrintcountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where printcount is less than or equal to DEFAULT_PRINTCOUNT
        defaultAccountsShouldBeFound("printcount.lessThanOrEqual=" + DEFAULT_PRINTCOUNT);

        // Get all the accountsList where printcount is less than or equal to SMALLER_PRINTCOUNT
        defaultAccountsShouldNotBeFound("printcount.lessThanOrEqual=" + SMALLER_PRINTCOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByPrintcountIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where printcount is less than DEFAULT_PRINTCOUNT
        defaultAccountsShouldNotBeFound("printcount.lessThan=" + DEFAULT_PRINTCOUNT);

        // Get all the accountsList where printcount is less than UPDATED_PRINTCOUNT
        defaultAccountsShouldBeFound("printcount.lessThan=" + UPDATED_PRINTCOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByPrintcountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where printcount is greater than DEFAULT_PRINTCOUNT
        defaultAccountsShouldNotBeFound("printcount.greaterThan=" + DEFAULT_PRINTCOUNT);

        // Get all the accountsList where printcount is greater than SMALLER_PRINTCOUNT
        defaultAccountsShouldBeFound("printcount.greaterThan=" + SMALLER_PRINTCOUNT);
    }

    @Test
    @Transactional
    void getAllAccountsByVipjfIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where vipjf equals to DEFAULT_VIPJF
        defaultAccountsShouldBeFound("vipjf.equals=" + DEFAULT_VIPJF);

        // Get all the accountsList where vipjf equals to UPDATED_VIPJF
        defaultAccountsShouldNotBeFound("vipjf.equals=" + UPDATED_VIPJF);
    }

    @Test
    @Transactional
    void getAllAccountsByVipjfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where vipjf not equals to DEFAULT_VIPJF
        defaultAccountsShouldNotBeFound("vipjf.notEquals=" + DEFAULT_VIPJF);

        // Get all the accountsList where vipjf not equals to UPDATED_VIPJF
        defaultAccountsShouldBeFound("vipjf.notEquals=" + UPDATED_VIPJF);
    }

    @Test
    @Transactional
    void getAllAccountsByVipjfIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where vipjf in DEFAULT_VIPJF or UPDATED_VIPJF
        defaultAccountsShouldBeFound("vipjf.in=" + DEFAULT_VIPJF + "," + UPDATED_VIPJF);

        // Get all the accountsList where vipjf equals to UPDATED_VIPJF
        defaultAccountsShouldNotBeFound("vipjf.in=" + UPDATED_VIPJF);
    }

    @Test
    @Transactional
    void getAllAccountsByVipjfIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where vipjf is not null
        defaultAccountsShouldBeFound("vipjf.specified=true");

        // Get all the accountsList where vipjf is null
        defaultAccountsShouldNotBeFound("vipjf.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByVipjfIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where vipjf is greater than or equal to DEFAULT_VIPJF
        defaultAccountsShouldBeFound("vipjf.greaterThanOrEqual=" + DEFAULT_VIPJF);

        // Get all the accountsList where vipjf is greater than or equal to UPDATED_VIPJF
        defaultAccountsShouldNotBeFound("vipjf.greaterThanOrEqual=" + UPDATED_VIPJF);
    }

    @Test
    @Transactional
    void getAllAccountsByVipjfIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where vipjf is less than or equal to DEFAULT_VIPJF
        defaultAccountsShouldBeFound("vipjf.lessThanOrEqual=" + DEFAULT_VIPJF);

        // Get all the accountsList where vipjf is less than or equal to SMALLER_VIPJF
        defaultAccountsShouldNotBeFound("vipjf.lessThanOrEqual=" + SMALLER_VIPJF);
    }

    @Test
    @Transactional
    void getAllAccountsByVipjfIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where vipjf is less than DEFAULT_VIPJF
        defaultAccountsShouldNotBeFound("vipjf.lessThan=" + DEFAULT_VIPJF);

        // Get all the accountsList where vipjf is less than UPDATED_VIPJF
        defaultAccountsShouldBeFound("vipjf.lessThan=" + UPDATED_VIPJF);
    }

    @Test
    @Transactional
    void getAllAccountsByVipjfIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where vipjf is greater than DEFAULT_VIPJF
        defaultAccountsShouldNotBeFound("vipjf.greaterThan=" + DEFAULT_VIPJF);

        // Get all the accountsList where vipjf is greater than SMALLER_VIPJF
        defaultAccountsShouldBeFound("vipjf.greaterThan=" + SMALLER_VIPJF);
    }

    @Test
    @Transactional
    void getAllAccountsByHykhIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hykh equals to DEFAULT_HYKH
        defaultAccountsShouldBeFound("hykh.equals=" + DEFAULT_HYKH);

        // Get all the accountsList where hykh equals to UPDATED_HYKH
        defaultAccountsShouldNotBeFound("hykh.equals=" + UPDATED_HYKH);
    }

    @Test
    @Transactional
    void getAllAccountsByHykhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hykh not equals to DEFAULT_HYKH
        defaultAccountsShouldNotBeFound("hykh.notEquals=" + DEFAULT_HYKH);

        // Get all the accountsList where hykh not equals to UPDATED_HYKH
        defaultAccountsShouldBeFound("hykh.notEquals=" + UPDATED_HYKH);
    }

    @Test
    @Transactional
    void getAllAccountsByHykhIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hykh in DEFAULT_HYKH or UPDATED_HYKH
        defaultAccountsShouldBeFound("hykh.in=" + DEFAULT_HYKH + "," + UPDATED_HYKH);

        // Get all the accountsList where hykh equals to UPDATED_HYKH
        defaultAccountsShouldNotBeFound("hykh.in=" + UPDATED_HYKH);
    }

    @Test
    @Transactional
    void getAllAccountsByHykhIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hykh is not null
        defaultAccountsShouldBeFound("hykh.specified=true");

        // Get all the accountsList where hykh is null
        defaultAccountsShouldNotBeFound("hykh.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByHykhContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hykh contains DEFAULT_HYKH
        defaultAccountsShouldBeFound("hykh.contains=" + DEFAULT_HYKH);

        // Get all the accountsList where hykh contains UPDATED_HYKH
        defaultAccountsShouldNotBeFound("hykh.contains=" + UPDATED_HYKH);
    }

    @Test
    @Transactional
    void getAllAccountsByHykhNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hykh does not contain DEFAULT_HYKH
        defaultAccountsShouldNotBeFound("hykh.doesNotContain=" + DEFAULT_HYKH);

        // Get all the accountsList where hykh does not contain UPDATED_HYKH
        defaultAccountsShouldBeFound("hykh.doesNotContain=" + UPDATED_HYKH);
    }

    @Test
    @Transactional
    void getAllAccountsBySlIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sl equals to DEFAULT_SL
        defaultAccountsShouldBeFound("sl.equals=" + DEFAULT_SL);

        // Get all the accountsList where sl equals to UPDATED_SL
        defaultAccountsShouldNotBeFound("sl.equals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllAccountsBySlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sl not equals to DEFAULT_SL
        defaultAccountsShouldNotBeFound("sl.notEquals=" + DEFAULT_SL);

        // Get all the accountsList where sl not equals to UPDATED_SL
        defaultAccountsShouldBeFound("sl.notEquals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllAccountsBySlIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sl in DEFAULT_SL or UPDATED_SL
        defaultAccountsShouldBeFound("sl.in=" + DEFAULT_SL + "," + UPDATED_SL);

        // Get all the accountsList where sl equals to UPDATED_SL
        defaultAccountsShouldNotBeFound("sl.in=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllAccountsBySlIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sl is not null
        defaultAccountsShouldBeFound("sl.specified=true");

        // Get all the accountsList where sl is null
        defaultAccountsShouldNotBeFound("sl.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsBySlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sl is greater than or equal to DEFAULT_SL
        defaultAccountsShouldBeFound("sl.greaterThanOrEqual=" + DEFAULT_SL);

        // Get all the accountsList where sl is greater than or equal to UPDATED_SL
        defaultAccountsShouldNotBeFound("sl.greaterThanOrEqual=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllAccountsBySlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sl is less than or equal to DEFAULT_SL
        defaultAccountsShouldBeFound("sl.lessThanOrEqual=" + DEFAULT_SL);

        // Get all the accountsList where sl is less than or equal to SMALLER_SL
        defaultAccountsShouldNotBeFound("sl.lessThanOrEqual=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllAccountsBySlIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sl is less than DEFAULT_SL
        defaultAccountsShouldNotBeFound("sl.lessThan=" + DEFAULT_SL);

        // Get all the accountsList where sl is less than UPDATED_SL
        defaultAccountsShouldBeFound("sl.lessThan=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllAccountsBySlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sl is greater than DEFAULT_SL
        defaultAccountsShouldNotBeFound("sl.greaterThan=" + DEFAULT_SL);

        // Get all the accountsList where sl is greater than SMALLER_SL
        defaultAccountsShouldBeFound("sl.greaterThan=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllAccountsBySgdjhIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sgdjh equals to DEFAULT_SGDJH
        defaultAccountsShouldBeFound("sgdjh.equals=" + DEFAULT_SGDJH);

        // Get all the accountsList where sgdjh equals to UPDATED_SGDJH
        defaultAccountsShouldNotBeFound("sgdjh.equals=" + UPDATED_SGDJH);
    }

    @Test
    @Transactional
    void getAllAccountsBySgdjhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sgdjh not equals to DEFAULT_SGDJH
        defaultAccountsShouldNotBeFound("sgdjh.notEquals=" + DEFAULT_SGDJH);

        // Get all the accountsList where sgdjh not equals to UPDATED_SGDJH
        defaultAccountsShouldBeFound("sgdjh.notEquals=" + UPDATED_SGDJH);
    }

    @Test
    @Transactional
    void getAllAccountsBySgdjhIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sgdjh in DEFAULT_SGDJH or UPDATED_SGDJH
        defaultAccountsShouldBeFound("sgdjh.in=" + DEFAULT_SGDJH + "," + UPDATED_SGDJH);

        // Get all the accountsList where sgdjh equals to UPDATED_SGDJH
        defaultAccountsShouldNotBeFound("sgdjh.in=" + UPDATED_SGDJH);
    }

    @Test
    @Transactional
    void getAllAccountsBySgdjhIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sgdjh is not null
        defaultAccountsShouldBeFound("sgdjh.specified=true");

        // Get all the accountsList where sgdjh is null
        defaultAccountsShouldNotBeFound("sgdjh.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsBySgdjhContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sgdjh contains DEFAULT_SGDJH
        defaultAccountsShouldBeFound("sgdjh.contains=" + DEFAULT_SGDJH);

        // Get all the accountsList where sgdjh contains UPDATED_SGDJH
        defaultAccountsShouldNotBeFound("sgdjh.contains=" + UPDATED_SGDJH);
    }

    @Test
    @Transactional
    void getAllAccountsBySgdjhNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where sgdjh does not contain DEFAULT_SGDJH
        defaultAccountsShouldNotBeFound("sgdjh.doesNotContain=" + DEFAULT_SGDJH);

        // Get all the accountsList where sgdjh does not contain UPDATED_SGDJH
        defaultAccountsShouldBeFound("sgdjh.doesNotContain=" + UPDATED_SGDJH);
    }

    @Test
    @Transactional
    void getAllAccountsByHoteldmIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hoteldm equals to DEFAULT_HOTELDM
        defaultAccountsShouldBeFound("hoteldm.equals=" + DEFAULT_HOTELDM);

        // Get all the accountsList where hoteldm equals to UPDATED_HOTELDM
        defaultAccountsShouldNotBeFound("hoteldm.equals=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllAccountsByHoteldmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hoteldm not equals to DEFAULT_HOTELDM
        defaultAccountsShouldNotBeFound("hoteldm.notEquals=" + DEFAULT_HOTELDM);

        // Get all the accountsList where hoteldm not equals to UPDATED_HOTELDM
        defaultAccountsShouldBeFound("hoteldm.notEquals=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllAccountsByHoteldmIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hoteldm in DEFAULT_HOTELDM or UPDATED_HOTELDM
        defaultAccountsShouldBeFound("hoteldm.in=" + DEFAULT_HOTELDM + "," + UPDATED_HOTELDM);

        // Get all the accountsList where hoteldm equals to UPDATED_HOTELDM
        defaultAccountsShouldNotBeFound("hoteldm.in=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllAccountsByHoteldmIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hoteldm is not null
        defaultAccountsShouldBeFound("hoteldm.specified=true");

        // Get all the accountsList where hoteldm is null
        defaultAccountsShouldNotBeFound("hoteldm.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByHoteldmContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hoteldm contains DEFAULT_HOTELDM
        defaultAccountsShouldBeFound("hoteldm.contains=" + DEFAULT_HOTELDM);

        // Get all the accountsList where hoteldm contains UPDATED_HOTELDM
        defaultAccountsShouldNotBeFound("hoteldm.contains=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllAccountsByHoteldmNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where hoteldm does not contain DEFAULT_HOTELDM
        defaultAccountsShouldNotBeFound("hoteldm.doesNotContain=" + DEFAULT_HOTELDM);

        // Get all the accountsList where hoteldm does not contain UPDATED_HOTELDM
        defaultAccountsShouldBeFound("hoteldm.doesNotContain=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllAccountsByIsnewIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isnew equals to DEFAULT_ISNEW
        defaultAccountsShouldBeFound("isnew.equals=" + DEFAULT_ISNEW);

        // Get all the accountsList where isnew equals to UPDATED_ISNEW
        defaultAccountsShouldNotBeFound("isnew.equals=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllAccountsByIsnewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isnew not equals to DEFAULT_ISNEW
        defaultAccountsShouldNotBeFound("isnew.notEquals=" + DEFAULT_ISNEW);

        // Get all the accountsList where isnew not equals to UPDATED_ISNEW
        defaultAccountsShouldBeFound("isnew.notEquals=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllAccountsByIsnewIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isnew in DEFAULT_ISNEW or UPDATED_ISNEW
        defaultAccountsShouldBeFound("isnew.in=" + DEFAULT_ISNEW + "," + UPDATED_ISNEW);

        // Get all the accountsList where isnew equals to UPDATED_ISNEW
        defaultAccountsShouldNotBeFound("isnew.in=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllAccountsByIsnewIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isnew is not null
        defaultAccountsShouldBeFound("isnew.specified=true");

        // Get all the accountsList where isnew is null
        defaultAccountsShouldNotBeFound("isnew.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByIsnewIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isnew is greater than or equal to DEFAULT_ISNEW
        defaultAccountsShouldBeFound("isnew.greaterThanOrEqual=" + DEFAULT_ISNEW);

        // Get all the accountsList where isnew is greater than or equal to UPDATED_ISNEW
        defaultAccountsShouldNotBeFound("isnew.greaterThanOrEqual=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllAccountsByIsnewIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isnew is less than or equal to DEFAULT_ISNEW
        defaultAccountsShouldBeFound("isnew.lessThanOrEqual=" + DEFAULT_ISNEW);

        // Get all the accountsList where isnew is less than or equal to SMALLER_ISNEW
        defaultAccountsShouldNotBeFound("isnew.lessThanOrEqual=" + SMALLER_ISNEW);
    }

    @Test
    @Transactional
    void getAllAccountsByIsnewIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isnew is less than DEFAULT_ISNEW
        defaultAccountsShouldNotBeFound("isnew.lessThan=" + DEFAULT_ISNEW);

        // Get all the accountsList where isnew is less than UPDATED_ISNEW
        defaultAccountsShouldBeFound("isnew.lessThan=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllAccountsByIsnewIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isnew is greater than DEFAULT_ISNEW
        defaultAccountsShouldNotBeFound("isnew.greaterThan=" + DEFAULT_ISNEW);

        // Get all the accountsList where isnew is greater than SMALLER_ISNEW
        defaultAccountsShouldBeFound("isnew.greaterThan=" + SMALLER_ISNEW);
    }

    @Test
    @Transactional
    void getAllAccountsByGuestIdIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where guestId equals to DEFAULT_GUEST_ID
        defaultAccountsShouldBeFound("guestId.equals=" + DEFAULT_GUEST_ID);

        // Get all the accountsList where guestId equals to UPDATED_GUEST_ID
        defaultAccountsShouldNotBeFound("guestId.equals=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllAccountsByGuestIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where guestId not equals to DEFAULT_GUEST_ID
        defaultAccountsShouldNotBeFound("guestId.notEquals=" + DEFAULT_GUEST_ID);

        // Get all the accountsList where guestId not equals to UPDATED_GUEST_ID
        defaultAccountsShouldBeFound("guestId.notEquals=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllAccountsByGuestIdIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where guestId in DEFAULT_GUEST_ID or UPDATED_GUEST_ID
        defaultAccountsShouldBeFound("guestId.in=" + DEFAULT_GUEST_ID + "," + UPDATED_GUEST_ID);

        // Get all the accountsList where guestId equals to UPDATED_GUEST_ID
        defaultAccountsShouldNotBeFound("guestId.in=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllAccountsByGuestIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where guestId is not null
        defaultAccountsShouldBeFound("guestId.specified=true");

        // Get all the accountsList where guestId is null
        defaultAccountsShouldNotBeFound("guestId.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByGuestIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where guestId is greater than or equal to DEFAULT_GUEST_ID
        defaultAccountsShouldBeFound("guestId.greaterThanOrEqual=" + DEFAULT_GUEST_ID);

        // Get all the accountsList where guestId is greater than or equal to UPDATED_GUEST_ID
        defaultAccountsShouldNotBeFound("guestId.greaterThanOrEqual=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllAccountsByGuestIdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where guestId is less than or equal to DEFAULT_GUEST_ID
        defaultAccountsShouldBeFound("guestId.lessThanOrEqual=" + DEFAULT_GUEST_ID);

        // Get all the accountsList where guestId is less than or equal to SMALLER_GUEST_ID
        defaultAccountsShouldNotBeFound("guestId.lessThanOrEqual=" + SMALLER_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllAccountsByGuestIdIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where guestId is less than DEFAULT_GUEST_ID
        defaultAccountsShouldNotBeFound("guestId.lessThan=" + DEFAULT_GUEST_ID);

        // Get all the accountsList where guestId is less than UPDATED_GUEST_ID
        defaultAccountsShouldBeFound("guestId.lessThan=" + UPDATED_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllAccountsByGuestIdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where guestId is greater than DEFAULT_GUEST_ID
        defaultAccountsShouldNotBeFound("guestId.greaterThan=" + DEFAULT_GUEST_ID);

        // Get all the accountsList where guestId is greater than SMALLER_GUEST_ID
        defaultAccountsShouldBeFound("guestId.greaterThan=" + SMALLER_GUEST_ID);
    }

    @Test
    @Transactional
    void getAllAccountsByYhkhIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where yhkh equals to DEFAULT_YHKH
        defaultAccountsShouldBeFound("yhkh.equals=" + DEFAULT_YHKH);

        // Get all the accountsList where yhkh equals to UPDATED_YHKH
        defaultAccountsShouldNotBeFound("yhkh.equals=" + UPDATED_YHKH);
    }

    @Test
    @Transactional
    void getAllAccountsByYhkhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where yhkh not equals to DEFAULT_YHKH
        defaultAccountsShouldNotBeFound("yhkh.notEquals=" + DEFAULT_YHKH);

        // Get all the accountsList where yhkh not equals to UPDATED_YHKH
        defaultAccountsShouldBeFound("yhkh.notEquals=" + UPDATED_YHKH);
    }

    @Test
    @Transactional
    void getAllAccountsByYhkhIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where yhkh in DEFAULT_YHKH or UPDATED_YHKH
        defaultAccountsShouldBeFound("yhkh.in=" + DEFAULT_YHKH + "," + UPDATED_YHKH);

        // Get all the accountsList where yhkh equals to UPDATED_YHKH
        defaultAccountsShouldNotBeFound("yhkh.in=" + UPDATED_YHKH);
    }

    @Test
    @Transactional
    void getAllAccountsByYhkhIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where yhkh is not null
        defaultAccountsShouldBeFound("yhkh.specified=true");

        // Get all the accountsList where yhkh is null
        defaultAccountsShouldNotBeFound("yhkh.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByYhkhContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where yhkh contains DEFAULT_YHKH
        defaultAccountsShouldBeFound("yhkh.contains=" + DEFAULT_YHKH);

        // Get all the accountsList where yhkh contains UPDATED_YHKH
        defaultAccountsShouldNotBeFound("yhkh.contains=" + UPDATED_YHKH);
    }

    @Test
    @Transactional
    void getAllAccountsByYhkhNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where yhkh does not contain DEFAULT_YHKH
        defaultAccountsShouldNotBeFound("yhkh.doesNotContain=" + DEFAULT_YHKH);

        // Get all the accountsList where yhkh does not contain UPDATED_YHKH
        defaultAccountsShouldBeFound("yhkh.doesNotContain=" + UPDATED_YHKH);
    }

    @Test
    @Transactional
    void getAllAccountsByDjqIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djq equals to DEFAULT_DJQ
        defaultAccountsShouldBeFound("djq.equals=" + DEFAULT_DJQ);

        // Get all the accountsList where djq equals to UPDATED_DJQ
        defaultAccountsShouldNotBeFound("djq.equals=" + UPDATED_DJQ);
    }

    @Test
    @Transactional
    void getAllAccountsByDjqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djq not equals to DEFAULT_DJQ
        defaultAccountsShouldNotBeFound("djq.notEquals=" + DEFAULT_DJQ);

        // Get all the accountsList where djq not equals to UPDATED_DJQ
        defaultAccountsShouldBeFound("djq.notEquals=" + UPDATED_DJQ);
    }

    @Test
    @Transactional
    void getAllAccountsByDjqIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djq in DEFAULT_DJQ or UPDATED_DJQ
        defaultAccountsShouldBeFound("djq.in=" + DEFAULT_DJQ + "," + UPDATED_DJQ);

        // Get all the accountsList where djq equals to UPDATED_DJQ
        defaultAccountsShouldNotBeFound("djq.in=" + UPDATED_DJQ);
    }

    @Test
    @Transactional
    void getAllAccountsByDjqIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djq is not null
        defaultAccountsShouldBeFound("djq.specified=true");

        // Get all the accountsList where djq is null
        defaultAccountsShouldNotBeFound("djq.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByDjqContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djq contains DEFAULT_DJQ
        defaultAccountsShouldBeFound("djq.contains=" + DEFAULT_DJQ);

        // Get all the accountsList where djq contains UPDATED_DJQ
        defaultAccountsShouldNotBeFound("djq.contains=" + UPDATED_DJQ);
    }

    @Test
    @Transactional
    void getAllAccountsByDjqNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djq does not contain DEFAULT_DJQ
        defaultAccountsShouldNotBeFound("djq.doesNotContain=" + DEFAULT_DJQ);

        // Get all the accountsList where djq does not contain UPDATED_DJQ
        defaultAccountsShouldBeFound("djq.doesNotContain=" + UPDATED_DJQ);
    }

    @Test
    @Transactional
    void getAllAccountsByYsjeIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ysje equals to DEFAULT_YSJE
        defaultAccountsShouldBeFound("ysje.equals=" + DEFAULT_YSJE);

        // Get all the accountsList where ysje equals to UPDATED_YSJE
        defaultAccountsShouldNotBeFound("ysje.equals=" + UPDATED_YSJE);
    }

    @Test
    @Transactional
    void getAllAccountsByYsjeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ysje not equals to DEFAULT_YSJE
        defaultAccountsShouldNotBeFound("ysje.notEquals=" + DEFAULT_YSJE);

        // Get all the accountsList where ysje not equals to UPDATED_YSJE
        defaultAccountsShouldBeFound("ysje.notEquals=" + UPDATED_YSJE);
    }

    @Test
    @Transactional
    void getAllAccountsByYsjeIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ysje in DEFAULT_YSJE or UPDATED_YSJE
        defaultAccountsShouldBeFound("ysje.in=" + DEFAULT_YSJE + "," + UPDATED_YSJE);

        // Get all the accountsList where ysje equals to UPDATED_YSJE
        defaultAccountsShouldNotBeFound("ysje.in=" + UPDATED_YSJE);
    }

    @Test
    @Transactional
    void getAllAccountsByYsjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ysje is not null
        defaultAccountsShouldBeFound("ysje.specified=true");

        // Get all the accountsList where ysje is null
        defaultAccountsShouldNotBeFound("ysje.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByYsjeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ysje is greater than or equal to DEFAULT_YSJE
        defaultAccountsShouldBeFound("ysje.greaterThanOrEqual=" + DEFAULT_YSJE);

        // Get all the accountsList where ysje is greater than or equal to UPDATED_YSJE
        defaultAccountsShouldNotBeFound("ysje.greaterThanOrEqual=" + UPDATED_YSJE);
    }

    @Test
    @Transactional
    void getAllAccountsByYsjeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ysje is less than or equal to DEFAULT_YSJE
        defaultAccountsShouldBeFound("ysje.lessThanOrEqual=" + DEFAULT_YSJE);

        // Get all the accountsList where ysje is less than or equal to SMALLER_YSJE
        defaultAccountsShouldNotBeFound("ysje.lessThanOrEqual=" + SMALLER_YSJE);
    }

    @Test
    @Transactional
    void getAllAccountsByYsjeIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ysje is less than DEFAULT_YSJE
        defaultAccountsShouldNotBeFound("ysje.lessThan=" + DEFAULT_YSJE);

        // Get all the accountsList where ysje is less than UPDATED_YSJE
        defaultAccountsShouldBeFound("ysje.lessThan=" + UPDATED_YSJE);
    }

    @Test
    @Transactional
    void getAllAccountsByYsjeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ysje is greater than DEFAULT_YSJE
        defaultAccountsShouldNotBeFound("ysje.greaterThan=" + DEFAULT_YSJE);

        // Get all the accountsList where ysje is greater than SMALLER_YSJE
        defaultAccountsShouldBeFound("ysje.greaterThan=" + SMALLER_YSJE);
    }

    @Test
    @Transactional
    void getAllAccountsByBjIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bj equals to DEFAULT_BJ
        defaultAccountsShouldBeFound("bj.equals=" + DEFAULT_BJ);

        // Get all the accountsList where bj equals to UPDATED_BJ
        defaultAccountsShouldNotBeFound("bj.equals=" + UPDATED_BJ);
    }

    @Test
    @Transactional
    void getAllAccountsByBjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bj not equals to DEFAULT_BJ
        defaultAccountsShouldNotBeFound("bj.notEquals=" + DEFAULT_BJ);

        // Get all the accountsList where bj not equals to UPDATED_BJ
        defaultAccountsShouldBeFound("bj.notEquals=" + UPDATED_BJ);
    }

    @Test
    @Transactional
    void getAllAccountsByBjIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bj in DEFAULT_BJ or UPDATED_BJ
        defaultAccountsShouldBeFound("bj.in=" + DEFAULT_BJ + "," + UPDATED_BJ);

        // Get all the accountsList where bj equals to UPDATED_BJ
        defaultAccountsShouldNotBeFound("bj.in=" + UPDATED_BJ);
    }

    @Test
    @Transactional
    void getAllAccountsByBjIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bj is not null
        defaultAccountsShouldBeFound("bj.specified=true");

        // Get all the accountsList where bj is null
        defaultAccountsShouldNotBeFound("bj.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByBjContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bj contains DEFAULT_BJ
        defaultAccountsShouldBeFound("bj.contains=" + DEFAULT_BJ);

        // Get all the accountsList where bj contains UPDATED_BJ
        defaultAccountsShouldNotBeFound("bj.contains=" + UPDATED_BJ);
    }

    @Test
    @Transactional
    void getAllAccountsByBjNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bj does not contain DEFAULT_BJ
        defaultAccountsShouldNotBeFound("bj.doesNotContain=" + DEFAULT_BJ);

        // Get all the accountsList where bj does not contain UPDATED_BJ
        defaultAccountsShouldBeFound("bj.doesNotContain=" + UPDATED_BJ);
    }

    @Test
    @Transactional
    void getAllAccountsByBjempnIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bjempn equals to DEFAULT_BJEMPN
        defaultAccountsShouldBeFound("bjempn.equals=" + DEFAULT_BJEMPN);

        // Get all the accountsList where bjempn equals to UPDATED_BJEMPN
        defaultAccountsShouldNotBeFound("bjempn.equals=" + UPDATED_BJEMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByBjempnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bjempn not equals to DEFAULT_BJEMPN
        defaultAccountsShouldNotBeFound("bjempn.notEquals=" + DEFAULT_BJEMPN);

        // Get all the accountsList where bjempn not equals to UPDATED_BJEMPN
        defaultAccountsShouldBeFound("bjempn.notEquals=" + UPDATED_BJEMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByBjempnIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bjempn in DEFAULT_BJEMPN or UPDATED_BJEMPN
        defaultAccountsShouldBeFound("bjempn.in=" + DEFAULT_BJEMPN + "," + UPDATED_BJEMPN);

        // Get all the accountsList where bjempn equals to UPDATED_BJEMPN
        defaultAccountsShouldNotBeFound("bjempn.in=" + UPDATED_BJEMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByBjempnIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bjempn is not null
        defaultAccountsShouldBeFound("bjempn.specified=true");

        // Get all the accountsList where bjempn is null
        defaultAccountsShouldNotBeFound("bjempn.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByBjempnContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bjempn contains DEFAULT_BJEMPN
        defaultAccountsShouldBeFound("bjempn.contains=" + DEFAULT_BJEMPN);

        // Get all the accountsList where bjempn contains UPDATED_BJEMPN
        defaultAccountsShouldNotBeFound("bjempn.contains=" + UPDATED_BJEMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByBjempnNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bjempn does not contain DEFAULT_BJEMPN
        defaultAccountsShouldNotBeFound("bjempn.doesNotContain=" + DEFAULT_BJEMPN);

        // Get all the accountsList where bjempn does not contain UPDATED_BJEMPN
        defaultAccountsShouldBeFound("bjempn.doesNotContain=" + UPDATED_BJEMPN);
    }

    @Test
    @Transactional
    void getAllAccountsByBjtimeIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bjtime equals to DEFAULT_BJTIME
        defaultAccountsShouldBeFound("bjtime.equals=" + DEFAULT_BJTIME);

        // Get all the accountsList where bjtime equals to UPDATED_BJTIME
        defaultAccountsShouldNotBeFound("bjtime.equals=" + UPDATED_BJTIME);
    }

    @Test
    @Transactional
    void getAllAccountsByBjtimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bjtime not equals to DEFAULT_BJTIME
        defaultAccountsShouldNotBeFound("bjtime.notEquals=" + DEFAULT_BJTIME);

        // Get all the accountsList where bjtime not equals to UPDATED_BJTIME
        defaultAccountsShouldBeFound("bjtime.notEquals=" + UPDATED_BJTIME);
    }

    @Test
    @Transactional
    void getAllAccountsByBjtimeIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bjtime in DEFAULT_BJTIME or UPDATED_BJTIME
        defaultAccountsShouldBeFound("bjtime.in=" + DEFAULT_BJTIME + "," + UPDATED_BJTIME);

        // Get all the accountsList where bjtime equals to UPDATED_BJTIME
        defaultAccountsShouldNotBeFound("bjtime.in=" + UPDATED_BJTIME);
    }

    @Test
    @Transactional
    void getAllAccountsByBjtimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bjtime is not null
        defaultAccountsShouldBeFound("bjtime.specified=true");

        // Get all the accountsList where bjtime is null
        defaultAccountsShouldNotBeFound("bjtime.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByPaper2IsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where paper2 equals to DEFAULT_PAPER_2
        defaultAccountsShouldBeFound("paper2.equals=" + DEFAULT_PAPER_2);

        // Get all the accountsList where paper2 equals to UPDATED_PAPER_2
        defaultAccountsShouldNotBeFound("paper2.equals=" + UPDATED_PAPER_2);
    }

    @Test
    @Transactional
    void getAllAccountsByPaper2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where paper2 not equals to DEFAULT_PAPER_2
        defaultAccountsShouldNotBeFound("paper2.notEquals=" + DEFAULT_PAPER_2);

        // Get all the accountsList where paper2 not equals to UPDATED_PAPER_2
        defaultAccountsShouldBeFound("paper2.notEquals=" + UPDATED_PAPER_2);
    }

    @Test
    @Transactional
    void getAllAccountsByPaper2IsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where paper2 in DEFAULT_PAPER_2 or UPDATED_PAPER_2
        defaultAccountsShouldBeFound("paper2.in=" + DEFAULT_PAPER_2 + "," + UPDATED_PAPER_2);

        // Get all the accountsList where paper2 equals to UPDATED_PAPER_2
        defaultAccountsShouldNotBeFound("paper2.in=" + UPDATED_PAPER_2);
    }

    @Test
    @Transactional
    void getAllAccountsByPaper2IsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where paper2 is not null
        defaultAccountsShouldBeFound("paper2.specified=true");

        // Get all the accountsList where paper2 is null
        defaultAccountsShouldNotBeFound("paper2.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByPaper2ContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where paper2 contains DEFAULT_PAPER_2
        defaultAccountsShouldBeFound("paper2.contains=" + DEFAULT_PAPER_2);

        // Get all the accountsList where paper2 contains UPDATED_PAPER_2
        defaultAccountsShouldNotBeFound("paper2.contains=" + UPDATED_PAPER_2);
    }

    @Test
    @Transactional
    void getAllAccountsByPaper2NotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where paper2 does not contain DEFAULT_PAPER_2
        defaultAccountsShouldNotBeFound("paper2.doesNotContain=" + DEFAULT_PAPER_2);

        // Get all the accountsList where paper2 does not contain UPDATED_PAPER_2
        defaultAccountsShouldBeFound("paper2.doesNotContain=" + UPDATED_PAPER_2);
    }

    @Test
    @Transactional
    void getAllAccountsByBcIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bc equals to DEFAULT_BC
        defaultAccountsShouldBeFound("bc.equals=" + DEFAULT_BC);

        // Get all the accountsList where bc equals to UPDATED_BC
        defaultAccountsShouldNotBeFound("bc.equals=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllAccountsByBcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bc not equals to DEFAULT_BC
        defaultAccountsShouldNotBeFound("bc.notEquals=" + DEFAULT_BC);

        // Get all the accountsList where bc not equals to UPDATED_BC
        defaultAccountsShouldBeFound("bc.notEquals=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllAccountsByBcIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bc in DEFAULT_BC or UPDATED_BC
        defaultAccountsShouldBeFound("bc.in=" + DEFAULT_BC + "," + UPDATED_BC);

        // Get all the accountsList where bc equals to UPDATED_BC
        defaultAccountsShouldNotBeFound("bc.in=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllAccountsByBcIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bc is not null
        defaultAccountsShouldBeFound("bc.specified=true");

        // Get all the accountsList where bc is null
        defaultAccountsShouldNotBeFound("bc.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByBcContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bc contains DEFAULT_BC
        defaultAccountsShouldBeFound("bc.contains=" + DEFAULT_BC);

        // Get all the accountsList where bc contains UPDATED_BC
        defaultAccountsShouldNotBeFound("bc.contains=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllAccountsByBcNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bc does not contain DEFAULT_BC
        defaultAccountsShouldNotBeFound("bc.doesNotContain=" + DEFAULT_BC);

        // Get all the accountsList where bc does not contain UPDATED_BC
        defaultAccountsShouldBeFound("bc.doesNotContain=" + UPDATED_BC);
    }

    @Test
    @Transactional
    void getAllAccountsByAutoIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where auto equals to DEFAULT_AUTO
        defaultAccountsShouldBeFound("auto.equals=" + DEFAULT_AUTO);

        // Get all the accountsList where auto equals to UPDATED_AUTO
        defaultAccountsShouldNotBeFound("auto.equals=" + UPDATED_AUTO);
    }

    @Test
    @Transactional
    void getAllAccountsByAutoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where auto not equals to DEFAULT_AUTO
        defaultAccountsShouldNotBeFound("auto.notEquals=" + DEFAULT_AUTO);

        // Get all the accountsList where auto not equals to UPDATED_AUTO
        defaultAccountsShouldBeFound("auto.notEquals=" + UPDATED_AUTO);
    }

    @Test
    @Transactional
    void getAllAccountsByAutoIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where auto in DEFAULT_AUTO or UPDATED_AUTO
        defaultAccountsShouldBeFound("auto.in=" + DEFAULT_AUTO + "," + UPDATED_AUTO);

        // Get all the accountsList where auto equals to UPDATED_AUTO
        defaultAccountsShouldNotBeFound("auto.in=" + UPDATED_AUTO);
    }

    @Test
    @Transactional
    void getAllAccountsByAutoIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where auto is not null
        defaultAccountsShouldBeFound("auto.specified=true");

        // Get all the accountsList where auto is null
        defaultAccountsShouldNotBeFound("auto.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByAutoContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where auto contains DEFAULT_AUTO
        defaultAccountsShouldBeFound("auto.contains=" + DEFAULT_AUTO);

        // Get all the accountsList where auto contains UPDATED_AUTO
        defaultAccountsShouldNotBeFound("auto.contains=" + UPDATED_AUTO);
    }

    @Test
    @Transactional
    void getAllAccountsByAutoNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where auto does not contain DEFAULT_AUTO
        defaultAccountsShouldNotBeFound("auto.doesNotContain=" + DEFAULT_AUTO);

        // Get all the accountsList where auto does not contain UPDATED_AUTO
        defaultAccountsShouldBeFound("auto.doesNotContain=" + UPDATED_AUTO);
    }

    @Test
    @Transactional
    void getAllAccountsByXsyIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where xsy equals to DEFAULT_XSY
        defaultAccountsShouldBeFound("xsy.equals=" + DEFAULT_XSY);

        // Get all the accountsList where xsy equals to UPDATED_XSY
        defaultAccountsShouldNotBeFound("xsy.equals=" + UPDATED_XSY);
    }

    @Test
    @Transactional
    void getAllAccountsByXsyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where xsy not equals to DEFAULT_XSY
        defaultAccountsShouldNotBeFound("xsy.notEquals=" + DEFAULT_XSY);

        // Get all the accountsList where xsy not equals to UPDATED_XSY
        defaultAccountsShouldBeFound("xsy.notEquals=" + UPDATED_XSY);
    }

    @Test
    @Transactional
    void getAllAccountsByXsyIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where xsy in DEFAULT_XSY or UPDATED_XSY
        defaultAccountsShouldBeFound("xsy.in=" + DEFAULT_XSY + "," + UPDATED_XSY);

        // Get all the accountsList where xsy equals to UPDATED_XSY
        defaultAccountsShouldNotBeFound("xsy.in=" + UPDATED_XSY);
    }

    @Test
    @Transactional
    void getAllAccountsByXsyIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where xsy is not null
        defaultAccountsShouldBeFound("xsy.specified=true");

        // Get all the accountsList where xsy is null
        defaultAccountsShouldNotBeFound("xsy.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByXsyContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where xsy contains DEFAULT_XSY
        defaultAccountsShouldBeFound("xsy.contains=" + DEFAULT_XSY);

        // Get all the accountsList where xsy contains UPDATED_XSY
        defaultAccountsShouldNotBeFound("xsy.contains=" + UPDATED_XSY);
    }

    @Test
    @Transactional
    void getAllAccountsByXsyNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where xsy does not contain DEFAULT_XSY
        defaultAccountsShouldNotBeFound("xsy.doesNotContain=" + DEFAULT_XSY);

        // Get all the accountsList where xsy does not contain UPDATED_XSY
        defaultAccountsShouldBeFound("xsy.doesNotContain=" + UPDATED_XSY);
    }

    @Test
    @Transactional
    void getAllAccountsByDjkhIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djkh equals to DEFAULT_DJKH
        defaultAccountsShouldBeFound("djkh.equals=" + DEFAULT_DJKH);

        // Get all the accountsList where djkh equals to UPDATED_DJKH
        defaultAccountsShouldNotBeFound("djkh.equals=" + UPDATED_DJKH);
    }

    @Test
    @Transactional
    void getAllAccountsByDjkhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djkh not equals to DEFAULT_DJKH
        defaultAccountsShouldNotBeFound("djkh.notEquals=" + DEFAULT_DJKH);

        // Get all the accountsList where djkh not equals to UPDATED_DJKH
        defaultAccountsShouldBeFound("djkh.notEquals=" + UPDATED_DJKH);
    }

    @Test
    @Transactional
    void getAllAccountsByDjkhIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djkh in DEFAULT_DJKH or UPDATED_DJKH
        defaultAccountsShouldBeFound("djkh.in=" + DEFAULT_DJKH + "," + UPDATED_DJKH);

        // Get all the accountsList where djkh equals to UPDATED_DJKH
        defaultAccountsShouldNotBeFound("djkh.in=" + UPDATED_DJKH);
    }

    @Test
    @Transactional
    void getAllAccountsByDjkhIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djkh is not null
        defaultAccountsShouldBeFound("djkh.specified=true");

        // Get all the accountsList where djkh is null
        defaultAccountsShouldNotBeFound("djkh.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByDjkhContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djkh contains DEFAULT_DJKH
        defaultAccountsShouldBeFound("djkh.contains=" + DEFAULT_DJKH);

        // Get all the accountsList where djkh contains UPDATED_DJKH
        defaultAccountsShouldNotBeFound("djkh.contains=" + UPDATED_DJKH);
    }

    @Test
    @Transactional
    void getAllAccountsByDjkhNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djkh does not contain DEFAULT_DJKH
        defaultAccountsShouldNotBeFound("djkh.doesNotContain=" + DEFAULT_DJKH);

        // Get all the accountsList where djkh does not contain UPDATED_DJKH
        defaultAccountsShouldBeFound("djkh.doesNotContain=" + UPDATED_DJKH);
    }

    @Test
    @Transactional
    void getAllAccountsByDjsignIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djsign equals to DEFAULT_DJSIGN
        defaultAccountsShouldBeFound("djsign.equals=" + DEFAULT_DJSIGN);

        // Get all the accountsList where djsign equals to UPDATED_DJSIGN
        defaultAccountsShouldNotBeFound("djsign.equals=" + UPDATED_DJSIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByDjsignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djsign not equals to DEFAULT_DJSIGN
        defaultAccountsShouldNotBeFound("djsign.notEquals=" + DEFAULT_DJSIGN);

        // Get all the accountsList where djsign not equals to UPDATED_DJSIGN
        defaultAccountsShouldBeFound("djsign.notEquals=" + UPDATED_DJSIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByDjsignIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djsign in DEFAULT_DJSIGN or UPDATED_DJSIGN
        defaultAccountsShouldBeFound("djsign.in=" + DEFAULT_DJSIGN + "," + UPDATED_DJSIGN);

        // Get all the accountsList where djsign equals to UPDATED_DJSIGN
        defaultAccountsShouldNotBeFound("djsign.in=" + UPDATED_DJSIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByDjsignIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djsign is not null
        defaultAccountsShouldBeFound("djsign.specified=true");

        // Get all the accountsList where djsign is null
        defaultAccountsShouldNotBeFound("djsign.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByDjsignContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djsign contains DEFAULT_DJSIGN
        defaultAccountsShouldBeFound("djsign.contains=" + DEFAULT_DJSIGN);

        // Get all the accountsList where djsign contains UPDATED_DJSIGN
        defaultAccountsShouldNotBeFound("djsign.contains=" + UPDATED_DJSIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByDjsignNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djsign does not contain DEFAULT_DJSIGN
        defaultAccountsShouldNotBeFound("djsign.doesNotContain=" + DEFAULT_DJSIGN);

        // Get all the accountsList where djsign does not contain UPDATED_DJSIGN
        defaultAccountsShouldBeFound("djsign.doesNotContain=" + UPDATED_DJSIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByClassnameIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where classname equals to DEFAULT_CLASSNAME
        defaultAccountsShouldBeFound("classname.equals=" + DEFAULT_CLASSNAME);

        // Get all the accountsList where classname equals to UPDATED_CLASSNAME
        defaultAccountsShouldNotBeFound("classname.equals=" + UPDATED_CLASSNAME);
    }

    @Test
    @Transactional
    void getAllAccountsByClassnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where classname not equals to DEFAULT_CLASSNAME
        defaultAccountsShouldNotBeFound("classname.notEquals=" + DEFAULT_CLASSNAME);

        // Get all the accountsList where classname not equals to UPDATED_CLASSNAME
        defaultAccountsShouldBeFound("classname.notEquals=" + UPDATED_CLASSNAME);
    }

    @Test
    @Transactional
    void getAllAccountsByClassnameIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where classname in DEFAULT_CLASSNAME or UPDATED_CLASSNAME
        defaultAccountsShouldBeFound("classname.in=" + DEFAULT_CLASSNAME + "," + UPDATED_CLASSNAME);

        // Get all the accountsList where classname equals to UPDATED_CLASSNAME
        defaultAccountsShouldNotBeFound("classname.in=" + UPDATED_CLASSNAME);
    }

    @Test
    @Transactional
    void getAllAccountsByClassnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where classname is not null
        defaultAccountsShouldBeFound("classname.specified=true");

        // Get all the accountsList where classname is null
        defaultAccountsShouldNotBeFound("classname.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByClassnameContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where classname contains DEFAULT_CLASSNAME
        defaultAccountsShouldBeFound("classname.contains=" + DEFAULT_CLASSNAME);

        // Get all the accountsList where classname contains UPDATED_CLASSNAME
        defaultAccountsShouldNotBeFound("classname.contains=" + UPDATED_CLASSNAME);
    }

    @Test
    @Transactional
    void getAllAccountsByClassnameNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where classname does not contain DEFAULT_CLASSNAME
        defaultAccountsShouldNotBeFound("classname.doesNotContain=" + DEFAULT_CLASSNAME);

        // Get all the accountsList where classname does not contain UPDATED_CLASSNAME
        defaultAccountsShouldBeFound("classname.doesNotContain=" + UPDATED_CLASSNAME);
    }

    @Test
    @Transactional
    void getAllAccountsByIscyIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where iscy equals to DEFAULT_ISCY
        defaultAccountsShouldBeFound("iscy.equals=" + DEFAULT_ISCY);

        // Get all the accountsList where iscy equals to UPDATED_ISCY
        defaultAccountsShouldNotBeFound("iscy.equals=" + UPDATED_ISCY);
    }

    @Test
    @Transactional
    void getAllAccountsByIscyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where iscy not equals to DEFAULT_ISCY
        defaultAccountsShouldNotBeFound("iscy.notEquals=" + DEFAULT_ISCY);

        // Get all the accountsList where iscy not equals to UPDATED_ISCY
        defaultAccountsShouldBeFound("iscy.notEquals=" + UPDATED_ISCY);
    }

    @Test
    @Transactional
    void getAllAccountsByIscyIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where iscy in DEFAULT_ISCY or UPDATED_ISCY
        defaultAccountsShouldBeFound("iscy.in=" + DEFAULT_ISCY + "," + UPDATED_ISCY);

        // Get all the accountsList where iscy equals to UPDATED_ISCY
        defaultAccountsShouldNotBeFound("iscy.in=" + UPDATED_ISCY);
    }

    @Test
    @Transactional
    void getAllAccountsByIscyIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where iscy is not null
        defaultAccountsShouldBeFound("iscy.specified=true");

        // Get all the accountsList where iscy is null
        defaultAccountsShouldNotBeFound("iscy.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByIscyContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where iscy contains DEFAULT_ISCY
        defaultAccountsShouldBeFound("iscy.contains=" + DEFAULT_ISCY);

        // Get all the accountsList where iscy contains UPDATED_ISCY
        defaultAccountsShouldNotBeFound("iscy.contains=" + UPDATED_ISCY);
    }

    @Test
    @Transactional
    void getAllAccountsByIscyNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where iscy does not contain DEFAULT_ISCY
        defaultAccountsShouldNotBeFound("iscy.doesNotContain=" + DEFAULT_ISCY);

        // Get all the accountsList where iscy does not contain UPDATED_ISCY
        defaultAccountsShouldBeFound("iscy.doesNotContain=" + UPDATED_ISCY);
    }

    @Test
    @Transactional
    void getAllAccountsByBsignIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bsign equals to DEFAULT_BSIGN
        defaultAccountsShouldBeFound("bsign.equals=" + DEFAULT_BSIGN);

        // Get all the accountsList where bsign equals to UPDATED_BSIGN
        defaultAccountsShouldNotBeFound("bsign.equals=" + UPDATED_BSIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByBsignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bsign not equals to DEFAULT_BSIGN
        defaultAccountsShouldNotBeFound("bsign.notEquals=" + DEFAULT_BSIGN);

        // Get all the accountsList where bsign not equals to UPDATED_BSIGN
        defaultAccountsShouldBeFound("bsign.notEquals=" + UPDATED_BSIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByBsignIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bsign in DEFAULT_BSIGN or UPDATED_BSIGN
        defaultAccountsShouldBeFound("bsign.in=" + DEFAULT_BSIGN + "," + UPDATED_BSIGN);

        // Get all the accountsList where bsign equals to UPDATED_BSIGN
        defaultAccountsShouldNotBeFound("bsign.in=" + UPDATED_BSIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByBsignIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bsign is not null
        defaultAccountsShouldBeFound("bsign.specified=true");

        // Get all the accountsList where bsign is null
        defaultAccountsShouldNotBeFound("bsign.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByBsignContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bsign contains DEFAULT_BSIGN
        defaultAccountsShouldBeFound("bsign.contains=" + DEFAULT_BSIGN);

        // Get all the accountsList where bsign contains UPDATED_BSIGN
        defaultAccountsShouldNotBeFound("bsign.contains=" + UPDATED_BSIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByBsignNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bsign does not contain DEFAULT_BSIGN
        defaultAccountsShouldNotBeFound("bsign.doesNotContain=" + DEFAULT_BSIGN);

        // Get all the accountsList where bsign does not contain UPDATED_BSIGN
        defaultAccountsShouldBeFound("bsign.doesNotContain=" + UPDATED_BSIGN);
    }

    @Test
    @Transactional
    void getAllAccountsByFxIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where fx equals to DEFAULT_FX
        defaultAccountsShouldBeFound("fx.equals=" + DEFAULT_FX);

        // Get all the accountsList where fx equals to UPDATED_FX
        defaultAccountsShouldNotBeFound("fx.equals=" + UPDATED_FX);
    }

    @Test
    @Transactional
    void getAllAccountsByFxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where fx not equals to DEFAULT_FX
        defaultAccountsShouldNotBeFound("fx.notEquals=" + DEFAULT_FX);

        // Get all the accountsList where fx not equals to UPDATED_FX
        defaultAccountsShouldBeFound("fx.notEquals=" + UPDATED_FX);
    }

    @Test
    @Transactional
    void getAllAccountsByFxIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where fx in DEFAULT_FX or UPDATED_FX
        defaultAccountsShouldBeFound("fx.in=" + DEFAULT_FX + "," + UPDATED_FX);

        // Get all the accountsList where fx equals to UPDATED_FX
        defaultAccountsShouldNotBeFound("fx.in=" + UPDATED_FX);
    }

    @Test
    @Transactional
    void getAllAccountsByFxIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where fx is not null
        defaultAccountsShouldBeFound("fx.specified=true");

        // Get all the accountsList where fx is null
        defaultAccountsShouldNotBeFound("fx.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByFxContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where fx contains DEFAULT_FX
        defaultAccountsShouldBeFound("fx.contains=" + DEFAULT_FX);

        // Get all the accountsList where fx contains UPDATED_FX
        defaultAccountsShouldNotBeFound("fx.contains=" + UPDATED_FX);
    }

    @Test
    @Transactional
    void getAllAccountsByFxNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where fx does not contain DEFAULT_FX
        defaultAccountsShouldNotBeFound("fx.doesNotContain=" + DEFAULT_FX);

        // Get all the accountsList where fx does not contain UPDATED_FX
        defaultAccountsShouldBeFound("fx.doesNotContain=" + UPDATED_FX);
    }

    @Test
    @Transactional
    void getAllAccountsByDjlxIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djlx equals to DEFAULT_DJLX
        defaultAccountsShouldBeFound("djlx.equals=" + DEFAULT_DJLX);

        // Get all the accountsList where djlx equals to UPDATED_DJLX
        defaultAccountsShouldNotBeFound("djlx.equals=" + UPDATED_DJLX);
    }

    @Test
    @Transactional
    void getAllAccountsByDjlxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djlx not equals to DEFAULT_DJLX
        defaultAccountsShouldNotBeFound("djlx.notEquals=" + DEFAULT_DJLX);

        // Get all the accountsList where djlx not equals to UPDATED_DJLX
        defaultAccountsShouldBeFound("djlx.notEquals=" + UPDATED_DJLX);
    }

    @Test
    @Transactional
    void getAllAccountsByDjlxIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djlx in DEFAULT_DJLX or UPDATED_DJLX
        defaultAccountsShouldBeFound("djlx.in=" + DEFAULT_DJLX + "," + UPDATED_DJLX);

        // Get all the accountsList where djlx equals to UPDATED_DJLX
        defaultAccountsShouldNotBeFound("djlx.in=" + UPDATED_DJLX);
    }

    @Test
    @Transactional
    void getAllAccountsByDjlxIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djlx is not null
        defaultAccountsShouldBeFound("djlx.specified=true");

        // Get all the accountsList where djlx is null
        defaultAccountsShouldNotBeFound("djlx.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByDjlxContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djlx contains DEFAULT_DJLX
        defaultAccountsShouldBeFound("djlx.contains=" + DEFAULT_DJLX);

        // Get all the accountsList where djlx contains UPDATED_DJLX
        defaultAccountsShouldNotBeFound("djlx.contains=" + UPDATED_DJLX);
    }

    @Test
    @Transactional
    void getAllAccountsByDjlxNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djlx does not contain DEFAULT_DJLX
        defaultAccountsShouldNotBeFound("djlx.doesNotContain=" + DEFAULT_DJLX);

        // Get all the accountsList where djlx does not contain UPDATED_DJLX
        defaultAccountsShouldBeFound("djlx.doesNotContain=" + UPDATED_DJLX);
    }

    @Test
    @Transactional
    void getAllAccountsByIsupIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isup equals to DEFAULT_ISUP
        defaultAccountsShouldBeFound("isup.equals=" + DEFAULT_ISUP);

        // Get all the accountsList where isup equals to UPDATED_ISUP
        defaultAccountsShouldNotBeFound("isup.equals=" + UPDATED_ISUP);
    }

    @Test
    @Transactional
    void getAllAccountsByIsupIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isup not equals to DEFAULT_ISUP
        defaultAccountsShouldNotBeFound("isup.notEquals=" + DEFAULT_ISUP);

        // Get all the accountsList where isup not equals to UPDATED_ISUP
        defaultAccountsShouldBeFound("isup.notEquals=" + UPDATED_ISUP);
    }

    @Test
    @Transactional
    void getAllAccountsByIsupIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isup in DEFAULT_ISUP or UPDATED_ISUP
        defaultAccountsShouldBeFound("isup.in=" + DEFAULT_ISUP + "," + UPDATED_ISUP);

        // Get all the accountsList where isup equals to UPDATED_ISUP
        defaultAccountsShouldNotBeFound("isup.in=" + UPDATED_ISUP);
    }

    @Test
    @Transactional
    void getAllAccountsByIsupIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isup is not null
        defaultAccountsShouldBeFound("isup.specified=true");

        // Get all the accountsList where isup is null
        defaultAccountsShouldNotBeFound("isup.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByIsupIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isup is greater than or equal to DEFAULT_ISUP
        defaultAccountsShouldBeFound("isup.greaterThanOrEqual=" + DEFAULT_ISUP);

        // Get all the accountsList where isup is greater than or equal to UPDATED_ISUP
        defaultAccountsShouldNotBeFound("isup.greaterThanOrEqual=" + UPDATED_ISUP);
    }

    @Test
    @Transactional
    void getAllAccountsByIsupIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isup is less than or equal to DEFAULT_ISUP
        defaultAccountsShouldBeFound("isup.lessThanOrEqual=" + DEFAULT_ISUP);

        // Get all the accountsList where isup is less than or equal to SMALLER_ISUP
        defaultAccountsShouldNotBeFound("isup.lessThanOrEqual=" + SMALLER_ISUP);
    }

    @Test
    @Transactional
    void getAllAccountsByIsupIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isup is less than DEFAULT_ISUP
        defaultAccountsShouldNotBeFound("isup.lessThan=" + DEFAULT_ISUP);

        // Get all the accountsList where isup is less than UPDATED_ISUP
        defaultAccountsShouldBeFound("isup.lessThan=" + UPDATED_ISUP);
    }

    @Test
    @Transactional
    void getAllAccountsByIsupIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where isup is greater than DEFAULT_ISUP
        defaultAccountsShouldNotBeFound("isup.greaterThan=" + DEFAULT_ISUP);

        // Get all the accountsList where isup is greater than SMALLER_ISUP
        defaultAccountsShouldBeFound("isup.greaterThan=" + SMALLER_ISUP);
    }

    @Test
    @Transactional
    void getAllAccountsByYongjinIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where yongjin equals to DEFAULT_YONGJIN
        defaultAccountsShouldBeFound("yongjin.equals=" + DEFAULT_YONGJIN);

        // Get all the accountsList where yongjin equals to UPDATED_YONGJIN
        defaultAccountsShouldNotBeFound("yongjin.equals=" + UPDATED_YONGJIN);
    }

    @Test
    @Transactional
    void getAllAccountsByYongjinIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where yongjin not equals to DEFAULT_YONGJIN
        defaultAccountsShouldNotBeFound("yongjin.notEquals=" + DEFAULT_YONGJIN);

        // Get all the accountsList where yongjin not equals to UPDATED_YONGJIN
        defaultAccountsShouldBeFound("yongjin.notEquals=" + UPDATED_YONGJIN);
    }

    @Test
    @Transactional
    void getAllAccountsByYongjinIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where yongjin in DEFAULT_YONGJIN or UPDATED_YONGJIN
        defaultAccountsShouldBeFound("yongjin.in=" + DEFAULT_YONGJIN + "," + UPDATED_YONGJIN);

        // Get all the accountsList where yongjin equals to UPDATED_YONGJIN
        defaultAccountsShouldNotBeFound("yongjin.in=" + UPDATED_YONGJIN);
    }

    @Test
    @Transactional
    void getAllAccountsByYongjinIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where yongjin is not null
        defaultAccountsShouldBeFound("yongjin.specified=true");

        // Get all the accountsList where yongjin is null
        defaultAccountsShouldNotBeFound("yongjin.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByYongjinIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where yongjin is greater than or equal to DEFAULT_YONGJIN
        defaultAccountsShouldBeFound("yongjin.greaterThanOrEqual=" + DEFAULT_YONGJIN);

        // Get all the accountsList where yongjin is greater than or equal to UPDATED_YONGJIN
        defaultAccountsShouldNotBeFound("yongjin.greaterThanOrEqual=" + UPDATED_YONGJIN);
    }

    @Test
    @Transactional
    void getAllAccountsByYongjinIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where yongjin is less than or equal to DEFAULT_YONGJIN
        defaultAccountsShouldBeFound("yongjin.lessThanOrEqual=" + DEFAULT_YONGJIN);

        // Get all the accountsList where yongjin is less than or equal to SMALLER_YONGJIN
        defaultAccountsShouldNotBeFound("yongjin.lessThanOrEqual=" + SMALLER_YONGJIN);
    }

    @Test
    @Transactional
    void getAllAccountsByYongjinIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where yongjin is less than DEFAULT_YONGJIN
        defaultAccountsShouldNotBeFound("yongjin.lessThan=" + DEFAULT_YONGJIN);

        // Get all the accountsList where yongjin is less than UPDATED_YONGJIN
        defaultAccountsShouldBeFound("yongjin.lessThan=" + UPDATED_YONGJIN);
    }

    @Test
    @Transactional
    void getAllAccountsByYongjinIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where yongjin is greater than DEFAULT_YONGJIN
        defaultAccountsShouldNotBeFound("yongjin.greaterThan=" + DEFAULT_YONGJIN);

        // Get all the accountsList where yongjin is greater than SMALLER_YONGJIN
        defaultAccountsShouldBeFound("yongjin.greaterThan=" + SMALLER_YONGJIN);
    }

    @Test
    @Transactional
    void getAllAccountsByCzpcIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where czpc equals to DEFAULT_CZPC
        defaultAccountsShouldBeFound("czpc.equals=" + DEFAULT_CZPC);

        // Get all the accountsList where czpc equals to UPDATED_CZPC
        defaultAccountsShouldNotBeFound("czpc.equals=" + UPDATED_CZPC);
    }

    @Test
    @Transactional
    void getAllAccountsByCzpcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where czpc not equals to DEFAULT_CZPC
        defaultAccountsShouldNotBeFound("czpc.notEquals=" + DEFAULT_CZPC);

        // Get all the accountsList where czpc not equals to UPDATED_CZPC
        defaultAccountsShouldBeFound("czpc.notEquals=" + UPDATED_CZPC);
    }

    @Test
    @Transactional
    void getAllAccountsByCzpcIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where czpc in DEFAULT_CZPC or UPDATED_CZPC
        defaultAccountsShouldBeFound("czpc.in=" + DEFAULT_CZPC + "," + UPDATED_CZPC);

        // Get all the accountsList where czpc equals to UPDATED_CZPC
        defaultAccountsShouldNotBeFound("czpc.in=" + UPDATED_CZPC);
    }

    @Test
    @Transactional
    void getAllAccountsByCzpcIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where czpc is not null
        defaultAccountsShouldBeFound("czpc.specified=true");

        // Get all the accountsList where czpc is null
        defaultAccountsShouldNotBeFound("czpc.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByCzpcContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where czpc contains DEFAULT_CZPC
        defaultAccountsShouldBeFound("czpc.contains=" + DEFAULT_CZPC);

        // Get all the accountsList where czpc contains UPDATED_CZPC
        defaultAccountsShouldNotBeFound("czpc.contains=" + UPDATED_CZPC);
    }

    @Test
    @Transactional
    void getAllAccountsByCzpcNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where czpc does not contain DEFAULT_CZPC
        defaultAccountsShouldNotBeFound("czpc.doesNotContain=" + DEFAULT_CZPC);

        // Get all the accountsList where czpc does not contain UPDATED_CZPC
        defaultAccountsShouldBeFound("czpc.doesNotContain=" + UPDATED_CZPC);
    }

    @Test
    @Transactional
    void getAllAccountsByCxflagIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where cxflag equals to DEFAULT_CXFLAG
        defaultAccountsShouldBeFound("cxflag.equals=" + DEFAULT_CXFLAG);

        // Get all the accountsList where cxflag equals to UPDATED_CXFLAG
        defaultAccountsShouldNotBeFound("cxflag.equals=" + UPDATED_CXFLAG);
    }

    @Test
    @Transactional
    void getAllAccountsByCxflagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where cxflag not equals to DEFAULT_CXFLAG
        defaultAccountsShouldNotBeFound("cxflag.notEquals=" + DEFAULT_CXFLAG);

        // Get all the accountsList where cxflag not equals to UPDATED_CXFLAG
        defaultAccountsShouldBeFound("cxflag.notEquals=" + UPDATED_CXFLAG);
    }

    @Test
    @Transactional
    void getAllAccountsByCxflagIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where cxflag in DEFAULT_CXFLAG or UPDATED_CXFLAG
        defaultAccountsShouldBeFound("cxflag.in=" + DEFAULT_CXFLAG + "," + UPDATED_CXFLAG);

        // Get all the accountsList where cxflag equals to UPDATED_CXFLAG
        defaultAccountsShouldNotBeFound("cxflag.in=" + UPDATED_CXFLAG);
    }

    @Test
    @Transactional
    void getAllAccountsByCxflagIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where cxflag is not null
        defaultAccountsShouldBeFound("cxflag.specified=true");

        // Get all the accountsList where cxflag is null
        defaultAccountsShouldNotBeFound("cxflag.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByCxflagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where cxflag is greater than or equal to DEFAULT_CXFLAG
        defaultAccountsShouldBeFound("cxflag.greaterThanOrEqual=" + DEFAULT_CXFLAG);

        // Get all the accountsList where cxflag is greater than or equal to UPDATED_CXFLAG
        defaultAccountsShouldNotBeFound("cxflag.greaterThanOrEqual=" + UPDATED_CXFLAG);
    }

    @Test
    @Transactional
    void getAllAccountsByCxflagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where cxflag is less than or equal to DEFAULT_CXFLAG
        defaultAccountsShouldBeFound("cxflag.lessThanOrEqual=" + DEFAULT_CXFLAG);

        // Get all the accountsList where cxflag is less than or equal to SMALLER_CXFLAG
        defaultAccountsShouldNotBeFound("cxflag.lessThanOrEqual=" + SMALLER_CXFLAG);
    }

    @Test
    @Transactional
    void getAllAccountsByCxflagIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where cxflag is less than DEFAULT_CXFLAG
        defaultAccountsShouldNotBeFound("cxflag.lessThan=" + DEFAULT_CXFLAG);

        // Get all the accountsList where cxflag is less than UPDATED_CXFLAG
        defaultAccountsShouldBeFound("cxflag.lessThan=" + UPDATED_CXFLAG);
    }

    @Test
    @Transactional
    void getAllAccountsByCxflagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where cxflag is greater than DEFAULT_CXFLAG
        defaultAccountsShouldNotBeFound("cxflag.greaterThan=" + DEFAULT_CXFLAG);

        // Get all the accountsList where cxflag is greater than SMALLER_CXFLAG
        defaultAccountsShouldBeFound("cxflag.greaterThan=" + SMALLER_CXFLAG);
    }

    @Test
    @Transactional
    void getAllAccountsByPmemoIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where pmemo equals to DEFAULT_PMEMO
        defaultAccountsShouldBeFound("pmemo.equals=" + DEFAULT_PMEMO);

        // Get all the accountsList where pmemo equals to UPDATED_PMEMO
        defaultAccountsShouldNotBeFound("pmemo.equals=" + UPDATED_PMEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByPmemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where pmemo not equals to DEFAULT_PMEMO
        defaultAccountsShouldNotBeFound("pmemo.notEquals=" + DEFAULT_PMEMO);

        // Get all the accountsList where pmemo not equals to UPDATED_PMEMO
        defaultAccountsShouldBeFound("pmemo.notEquals=" + UPDATED_PMEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByPmemoIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where pmemo in DEFAULT_PMEMO or UPDATED_PMEMO
        defaultAccountsShouldBeFound("pmemo.in=" + DEFAULT_PMEMO + "," + UPDATED_PMEMO);

        // Get all the accountsList where pmemo equals to UPDATED_PMEMO
        defaultAccountsShouldNotBeFound("pmemo.in=" + UPDATED_PMEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByPmemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where pmemo is not null
        defaultAccountsShouldBeFound("pmemo.specified=true");

        // Get all the accountsList where pmemo is null
        defaultAccountsShouldNotBeFound("pmemo.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByPmemoContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where pmemo contains DEFAULT_PMEMO
        defaultAccountsShouldBeFound("pmemo.contains=" + DEFAULT_PMEMO);

        // Get all the accountsList where pmemo contains UPDATED_PMEMO
        defaultAccountsShouldNotBeFound("pmemo.contains=" + UPDATED_PMEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByPmemoNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where pmemo does not contain DEFAULT_PMEMO
        defaultAccountsShouldNotBeFound("pmemo.doesNotContain=" + DEFAULT_PMEMO);

        // Get all the accountsList where pmemo does not contain UPDATED_PMEMO
        defaultAccountsShouldBeFound("pmemo.doesNotContain=" + UPDATED_PMEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByCzbillnoIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where czbillno equals to DEFAULT_CZBILLNO
        defaultAccountsShouldBeFound("czbillno.equals=" + DEFAULT_CZBILLNO);

        // Get all the accountsList where czbillno equals to UPDATED_CZBILLNO
        defaultAccountsShouldNotBeFound("czbillno.equals=" + UPDATED_CZBILLNO);
    }

    @Test
    @Transactional
    void getAllAccountsByCzbillnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where czbillno not equals to DEFAULT_CZBILLNO
        defaultAccountsShouldNotBeFound("czbillno.notEquals=" + DEFAULT_CZBILLNO);

        // Get all the accountsList where czbillno not equals to UPDATED_CZBILLNO
        defaultAccountsShouldBeFound("czbillno.notEquals=" + UPDATED_CZBILLNO);
    }

    @Test
    @Transactional
    void getAllAccountsByCzbillnoIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where czbillno in DEFAULT_CZBILLNO or UPDATED_CZBILLNO
        defaultAccountsShouldBeFound("czbillno.in=" + DEFAULT_CZBILLNO + "," + UPDATED_CZBILLNO);

        // Get all the accountsList where czbillno equals to UPDATED_CZBILLNO
        defaultAccountsShouldNotBeFound("czbillno.in=" + UPDATED_CZBILLNO);
    }

    @Test
    @Transactional
    void getAllAccountsByCzbillnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where czbillno is not null
        defaultAccountsShouldBeFound("czbillno.specified=true");

        // Get all the accountsList where czbillno is null
        defaultAccountsShouldNotBeFound("czbillno.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByCzbillnoContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where czbillno contains DEFAULT_CZBILLNO
        defaultAccountsShouldBeFound("czbillno.contains=" + DEFAULT_CZBILLNO);

        // Get all the accountsList where czbillno contains UPDATED_CZBILLNO
        defaultAccountsShouldNotBeFound("czbillno.contains=" + UPDATED_CZBILLNO);
    }

    @Test
    @Transactional
    void getAllAccountsByCzbillnoNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where czbillno does not contain DEFAULT_CZBILLNO
        defaultAccountsShouldNotBeFound("czbillno.doesNotContain=" + DEFAULT_CZBILLNO);

        // Get all the accountsList where czbillno does not contain UPDATED_CZBILLNO
        defaultAccountsShouldBeFound("czbillno.doesNotContain=" + UPDATED_CZBILLNO);
    }

    @Test
    @Transactional
    void getAllAccountsByDjqbzIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djqbz equals to DEFAULT_DJQBZ
        defaultAccountsShouldBeFound("djqbz.equals=" + DEFAULT_DJQBZ);

        // Get all the accountsList where djqbz equals to UPDATED_DJQBZ
        defaultAccountsShouldNotBeFound("djqbz.equals=" + UPDATED_DJQBZ);
    }

    @Test
    @Transactional
    void getAllAccountsByDjqbzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djqbz not equals to DEFAULT_DJQBZ
        defaultAccountsShouldNotBeFound("djqbz.notEquals=" + DEFAULT_DJQBZ);

        // Get all the accountsList where djqbz not equals to UPDATED_DJQBZ
        defaultAccountsShouldBeFound("djqbz.notEquals=" + UPDATED_DJQBZ);
    }

    @Test
    @Transactional
    void getAllAccountsByDjqbzIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djqbz in DEFAULT_DJQBZ or UPDATED_DJQBZ
        defaultAccountsShouldBeFound("djqbz.in=" + DEFAULT_DJQBZ + "," + UPDATED_DJQBZ);

        // Get all the accountsList where djqbz equals to UPDATED_DJQBZ
        defaultAccountsShouldNotBeFound("djqbz.in=" + UPDATED_DJQBZ);
    }

    @Test
    @Transactional
    void getAllAccountsByDjqbzIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djqbz is not null
        defaultAccountsShouldBeFound("djqbz.specified=true");

        // Get all the accountsList where djqbz is null
        defaultAccountsShouldNotBeFound("djqbz.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByDjqbzContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djqbz contains DEFAULT_DJQBZ
        defaultAccountsShouldBeFound("djqbz.contains=" + DEFAULT_DJQBZ);

        // Get all the accountsList where djqbz contains UPDATED_DJQBZ
        defaultAccountsShouldNotBeFound("djqbz.contains=" + UPDATED_DJQBZ);
    }

    @Test
    @Transactional
    void getAllAccountsByDjqbzNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where djqbz does not contain DEFAULT_DJQBZ
        defaultAccountsShouldNotBeFound("djqbz.doesNotContain=" + DEFAULT_DJQBZ);

        // Get all the accountsList where djqbz does not contain UPDATED_DJQBZ
        defaultAccountsShouldBeFound("djqbz.doesNotContain=" + UPDATED_DJQBZ);
    }

    @Test
    @Transactional
    void getAllAccountsByYsqmemoIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ysqmemo equals to DEFAULT_YSQMEMO
        defaultAccountsShouldBeFound("ysqmemo.equals=" + DEFAULT_YSQMEMO);

        // Get all the accountsList where ysqmemo equals to UPDATED_YSQMEMO
        defaultAccountsShouldNotBeFound("ysqmemo.equals=" + UPDATED_YSQMEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByYsqmemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ysqmemo not equals to DEFAULT_YSQMEMO
        defaultAccountsShouldNotBeFound("ysqmemo.notEquals=" + DEFAULT_YSQMEMO);

        // Get all the accountsList where ysqmemo not equals to UPDATED_YSQMEMO
        defaultAccountsShouldBeFound("ysqmemo.notEquals=" + UPDATED_YSQMEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByYsqmemoIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ysqmemo in DEFAULT_YSQMEMO or UPDATED_YSQMEMO
        defaultAccountsShouldBeFound("ysqmemo.in=" + DEFAULT_YSQMEMO + "," + UPDATED_YSQMEMO);

        // Get all the accountsList where ysqmemo equals to UPDATED_YSQMEMO
        defaultAccountsShouldNotBeFound("ysqmemo.in=" + UPDATED_YSQMEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByYsqmemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ysqmemo is not null
        defaultAccountsShouldBeFound("ysqmemo.specified=true");

        // Get all the accountsList where ysqmemo is null
        defaultAccountsShouldNotBeFound("ysqmemo.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByYsqmemoContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ysqmemo contains DEFAULT_YSQMEMO
        defaultAccountsShouldBeFound("ysqmemo.contains=" + DEFAULT_YSQMEMO);

        // Get all the accountsList where ysqmemo contains UPDATED_YSQMEMO
        defaultAccountsShouldNotBeFound("ysqmemo.contains=" + UPDATED_YSQMEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByYsqmemoNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ysqmemo does not contain DEFAULT_YSQMEMO
        defaultAccountsShouldNotBeFound("ysqmemo.doesNotContain=" + DEFAULT_YSQMEMO);

        // Get all the accountsList where ysqmemo does not contain UPDATED_YSQMEMO
        defaultAccountsShouldBeFound("ysqmemo.doesNotContain=" + UPDATED_YSQMEMO);
    }

    @Test
    @Transactional
    void getAllAccountsByTransactionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where transactionId equals to DEFAULT_TRANSACTION_ID
        defaultAccountsShouldBeFound("transactionId.equals=" + DEFAULT_TRANSACTION_ID);

        // Get all the accountsList where transactionId equals to UPDATED_TRANSACTION_ID
        defaultAccountsShouldNotBeFound("transactionId.equals=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllAccountsByTransactionIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where transactionId not equals to DEFAULT_TRANSACTION_ID
        defaultAccountsShouldNotBeFound("transactionId.notEquals=" + DEFAULT_TRANSACTION_ID);

        // Get all the accountsList where transactionId not equals to UPDATED_TRANSACTION_ID
        defaultAccountsShouldBeFound("transactionId.notEquals=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllAccountsByTransactionIdIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where transactionId in DEFAULT_TRANSACTION_ID or UPDATED_TRANSACTION_ID
        defaultAccountsShouldBeFound("transactionId.in=" + DEFAULT_TRANSACTION_ID + "," + UPDATED_TRANSACTION_ID);

        // Get all the accountsList where transactionId equals to UPDATED_TRANSACTION_ID
        defaultAccountsShouldNotBeFound("transactionId.in=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllAccountsByTransactionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where transactionId is not null
        defaultAccountsShouldBeFound("transactionId.specified=true");

        // Get all the accountsList where transactionId is null
        defaultAccountsShouldNotBeFound("transactionId.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByTransactionIdContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where transactionId contains DEFAULT_TRANSACTION_ID
        defaultAccountsShouldBeFound("transactionId.contains=" + DEFAULT_TRANSACTION_ID);

        // Get all the accountsList where transactionId contains UPDATED_TRANSACTION_ID
        defaultAccountsShouldNotBeFound("transactionId.contains=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllAccountsByTransactionIdNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where transactionId does not contain DEFAULT_TRANSACTION_ID
        defaultAccountsShouldNotBeFound("transactionId.doesNotContain=" + DEFAULT_TRANSACTION_ID);

        // Get all the accountsList where transactionId does not contain UPDATED_TRANSACTION_ID
        defaultAccountsShouldBeFound("transactionId.doesNotContain=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    void getAllAccountsByOutTradeNoIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where outTradeNo equals to DEFAULT_OUT_TRADE_NO
        defaultAccountsShouldBeFound("outTradeNo.equals=" + DEFAULT_OUT_TRADE_NO);

        // Get all the accountsList where outTradeNo equals to UPDATED_OUT_TRADE_NO
        defaultAccountsShouldNotBeFound("outTradeNo.equals=" + UPDATED_OUT_TRADE_NO);
    }

    @Test
    @Transactional
    void getAllAccountsByOutTradeNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where outTradeNo not equals to DEFAULT_OUT_TRADE_NO
        defaultAccountsShouldNotBeFound("outTradeNo.notEquals=" + DEFAULT_OUT_TRADE_NO);

        // Get all the accountsList where outTradeNo not equals to UPDATED_OUT_TRADE_NO
        defaultAccountsShouldBeFound("outTradeNo.notEquals=" + UPDATED_OUT_TRADE_NO);
    }

    @Test
    @Transactional
    void getAllAccountsByOutTradeNoIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where outTradeNo in DEFAULT_OUT_TRADE_NO or UPDATED_OUT_TRADE_NO
        defaultAccountsShouldBeFound("outTradeNo.in=" + DEFAULT_OUT_TRADE_NO + "," + UPDATED_OUT_TRADE_NO);

        // Get all the accountsList where outTradeNo equals to UPDATED_OUT_TRADE_NO
        defaultAccountsShouldNotBeFound("outTradeNo.in=" + UPDATED_OUT_TRADE_NO);
    }

    @Test
    @Transactional
    void getAllAccountsByOutTradeNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where outTradeNo is not null
        defaultAccountsShouldBeFound("outTradeNo.specified=true");

        // Get all the accountsList where outTradeNo is null
        defaultAccountsShouldNotBeFound("outTradeNo.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByOutTradeNoContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where outTradeNo contains DEFAULT_OUT_TRADE_NO
        defaultAccountsShouldBeFound("outTradeNo.contains=" + DEFAULT_OUT_TRADE_NO);

        // Get all the accountsList where outTradeNo contains UPDATED_OUT_TRADE_NO
        defaultAccountsShouldNotBeFound("outTradeNo.contains=" + UPDATED_OUT_TRADE_NO);
    }

    @Test
    @Transactional
    void getAllAccountsByOutTradeNoNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where outTradeNo does not contain DEFAULT_OUT_TRADE_NO
        defaultAccountsShouldNotBeFound("outTradeNo.doesNotContain=" + DEFAULT_OUT_TRADE_NO);

        // Get all the accountsList where outTradeNo does not contain UPDATED_OUT_TRADE_NO
        defaultAccountsShouldBeFound("outTradeNo.doesNotContain=" + UPDATED_OUT_TRADE_NO);
    }

    @Test
    @Transactional
    void getAllAccountsByGsnameIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where gsname equals to DEFAULT_GSNAME
        defaultAccountsShouldBeFound("gsname.equals=" + DEFAULT_GSNAME);

        // Get all the accountsList where gsname equals to UPDATED_GSNAME
        defaultAccountsShouldNotBeFound("gsname.equals=" + UPDATED_GSNAME);
    }

    @Test
    @Transactional
    void getAllAccountsByGsnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where gsname not equals to DEFAULT_GSNAME
        defaultAccountsShouldNotBeFound("gsname.notEquals=" + DEFAULT_GSNAME);

        // Get all the accountsList where gsname not equals to UPDATED_GSNAME
        defaultAccountsShouldBeFound("gsname.notEquals=" + UPDATED_GSNAME);
    }

    @Test
    @Transactional
    void getAllAccountsByGsnameIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where gsname in DEFAULT_GSNAME or UPDATED_GSNAME
        defaultAccountsShouldBeFound("gsname.in=" + DEFAULT_GSNAME + "," + UPDATED_GSNAME);

        // Get all the accountsList where gsname equals to UPDATED_GSNAME
        defaultAccountsShouldNotBeFound("gsname.in=" + UPDATED_GSNAME);
    }

    @Test
    @Transactional
    void getAllAccountsByGsnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where gsname is not null
        defaultAccountsShouldBeFound("gsname.specified=true");

        // Get all the accountsList where gsname is null
        defaultAccountsShouldNotBeFound("gsname.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByGsnameContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where gsname contains DEFAULT_GSNAME
        defaultAccountsShouldBeFound("gsname.contains=" + DEFAULT_GSNAME);

        // Get all the accountsList where gsname contains UPDATED_GSNAME
        defaultAccountsShouldNotBeFound("gsname.contains=" + UPDATED_GSNAME);
    }

    @Test
    @Transactional
    void getAllAccountsByGsnameNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where gsname does not contain DEFAULT_GSNAME
        defaultAccountsShouldNotBeFound("gsname.doesNotContain=" + DEFAULT_GSNAME);

        // Get all the accountsList where gsname does not contain UPDATED_GSNAME
        defaultAccountsShouldBeFound("gsname.doesNotContain=" + UPDATED_GSNAME);
    }

    @Test
    @Transactional
    void getAllAccountsByRzIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where rz equals to DEFAULT_RZ
        defaultAccountsShouldBeFound("rz.equals=" + DEFAULT_RZ);

        // Get all the accountsList where rz equals to UPDATED_RZ
        defaultAccountsShouldNotBeFound("rz.equals=" + UPDATED_RZ);
    }

    @Test
    @Transactional
    void getAllAccountsByRzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where rz not equals to DEFAULT_RZ
        defaultAccountsShouldNotBeFound("rz.notEquals=" + DEFAULT_RZ);

        // Get all the accountsList where rz not equals to UPDATED_RZ
        defaultAccountsShouldBeFound("rz.notEquals=" + UPDATED_RZ);
    }

    @Test
    @Transactional
    void getAllAccountsByRzIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where rz in DEFAULT_RZ or UPDATED_RZ
        defaultAccountsShouldBeFound("rz.in=" + DEFAULT_RZ + "," + UPDATED_RZ);

        // Get all the accountsList where rz equals to UPDATED_RZ
        defaultAccountsShouldNotBeFound("rz.in=" + UPDATED_RZ);
    }

    @Test
    @Transactional
    void getAllAccountsByRzIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where rz is not null
        defaultAccountsShouldBeFound("rz.specified=true");

        // Get all the accountsList where rz is null
        defaultAccountsShouldNotBeFound("rz.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByGzIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where gz equals to DEFAULT_GZ
        defaultAccountsShouldBeFound("gz.equals=" + DEFAULT_GZ);

        // Get all the accountsList where gz equals to UPDATED_GZ
        defaultAccountsShouldNotBeFound("gz.equals=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllAccountsByGzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where gz not equals to DEFAULT_GZ
        defaultAccountsShouldNotBeFound("gz.notEquals=" + DEFAULT_GZ);

        // Get all the accountsList where gz not equals to UPDATED_GZ
        defaultAccountsShouldBeFound("gz.notEquals=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllAccountsByGzIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where gz in DEFAULT_GZ or UPDATED_GZ
        defaultAccountsShouldBeFound("gz.in=" + DEFAULT_GZ + "," + UPDATED_GZ);

        // Get all the accountsList where gz equals to UPDATED_GZ
        defaultAccountsShouldNotBeFound("gz.in=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllAccountsByGzIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where gz is not null
        defaultAccountsShouldBeFound("gz.specified=true");

        // Get all the accountsList where gz is null
        defaultAccountsShouldNotBeFound("gz.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByTsIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ts equals to DEFAULT_TS
        defaultAccountsShouldBeFound("ts.equals=" + DEFAULT_TS);

        // Get all the accountsList where ts equals to UPDATED_TS
        defaultAccountsShouldNotBeFound("ts.equals=" + UPDATED_TS);
    }

    @Test
    @Transactional
    void getAllAccountsByTsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ts not equals to DEFAULT_TS
        defaultAccountsShouldNotBeFound("ts.notEquals=" + DEFAULT_TS);

        // Get all the accountsList where ts not equals to UPDATED_TS
        defaultAccountsShouldBeFound("ts.notEquals=" + UPDATED_TS);
    }

    @Test
    @Transactional
    void getAllAccountsByTsIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ts in DEFAULT_TS or UPDATED_TS
        defaultAccountsShouldBeFound("ts.in=" + DEFAULT_TS + "," + UPDATED_TS);

        // Get all the accountsList where ts equals to UPDATED_TS
        defaultAccountsShouldNotBeFound("ts.in=" + UPDATED_TS);
    }

    @Test
    @Transactional
    void getAllAccountsByTsIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ts is not null
        defaultAccountsShouldBeFound("ts.specified=true");

        // Get all the accountsList where ts is null
        defaultAccountsShouldNotBeFound("ts.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByTsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ts is greater than or equal to DEFAULT_TS
        defaultAccountsShouldBeFound("ts.greaterThanOrEqual=" + DEFAULT_TS);

        // Get all the accountsList where ts is greater than or equal to UPDATED_TS
        defaultAccountsShouldNotBeFound("ts.greaterThanOrEqual=" + UPDATED_TS);
    }

    @Test
    @Transactional
    void getAllAccountsByTsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ts is less than or equal to DEFAULT_TS
        defaultAccountsShouldBeFound("ts.lessThanOrEqual=" + DEFAULT_TS);

        // Get all the accountsList where ts is less than or equal to SMALLER_TS
        defaultAccountsShouldNotBeFound("ts.lessThanOrEqual=" + SMALLER_TS);
    }

    @Test
    @Transactional
    void getAllAccountsByTsIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ts is less than DEFAULT_TS
        defaultAccountsShouldNotBeFound("ts.lessThan=" + DEFAULT_TS);

        // Get all the accountsList where ts is less than UPDATED_TS
        defaultAccountsShouldBeFound("ts.lessThan=" + UPDATED_TS);
    }

    @Test
    @Transactional
    void getAllAccountsByTsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ts is greater than DEFAULT_TS
        defaultAccountsShouldNotBeFound("ts.greaterThan=" + DEFAULT_TS);

        // Get all the accountsList where ts is greater than SMALLER_TS
        defaultAccountsShouldBeFound("ts.greaterThan=" + SMALLER_TS);
    }

    @Test
    @Transactional
    void getAllAccountsByKyIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ky equals to DEFAULT_KY
        defaultAccountsShouldBeFound("ky.equals=" + DEFAULT_KY);

        // Get all the accountsList where ky equals to UPDATED_KY
        defaultAccountsShouldNotBeFound("ky.equals=" + UPDATED_KY);
    }

    @Test
    @Transactional
    void getAllAccountsByKyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ky not equals to DEFAULT_KY
        defaultAccountsShouldNotBeFound("ky.notEquals=" + DEFAULT_KY);

        // Get all the accountsList where ky not equals to UPDATED_KY
        defaultAccountsShouldBeFound("ky.notEquals=" + UPDATED_KY);
    }

    @Test
    @Transactional
    void getAllAccountsByKyIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ky in DEFAULT_KY or UPDATED_KY
        defaultAccountsShouldBeFound("ky.in=" + DEFAULT_KY + "," + UPDATED_KY);

        // Get all the accountsList where ky equals to UPDATED_KY
        defaultAccountsShouldNotBeFound("ky.in=" + UPDATED_KY);
    }

    @Test
    @Transactional
    void getAllAccountsByKyIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ky is not null
        defaultAccountsShouldBeFound("ky.specified=true");

        // Get all the accountsList where ky is null
        defaultAccountsShouldNotBeFound("ky.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByKyContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ky contains DEFAULT_KY
        defaultAccountsShouldBeFound("ky.contains=" + DEFAULT_KY);

        // Get all the accountsList where ky contains UPDATED_KY
        defaultAccountsShouldNotBeFound("ky.contains=" + UPDATED_KY);
    }

    @Test
    @Transactional
    void getAllAccountsByKyNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where ky does not contain DEFAULT_KY
        defaultAccountsShouldNotBeFound("ky.doesNotContain=" + DEFAULT_KY);

        // Get all the accountsList where ky does not contain UPDATED_KY
        defaultAccountsShouldBeFound("ky.doesNotContain=" + UPDATED_KY);
    }

    @Test
    @Transactional
    void getAllAccountsByXyIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where xy equals to DEFAULT_XY
        defaultAccountsShouldBeFound("xy.equals=" + DEFAULT_XY);

        // Get all the accountsList where xy equals to UPDATED_XY
        defaultAccountsShouldNotBeFound("xy.equals=" + UPDATED_XY);
    }

    @Test
    @Transactional
    void getAllAccountsByXyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where xy not equals to DEFAULT_XY
        defaultAccountsShouldNotBeFound("xy.notEquals=" + DEFAULT_XY);

        // Get all the accountsList where xy not equals to UPDATED_XY
        defaultAccountsShouldBeFound("xy.notEquals=" + UPDATED_XY);
    }

    @Test
    @Transactional
    void getAllAccountsByXyIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where xy in DEFAULT_XY or UPDATED_XY
        defaultAccountsShouldBeFound("xy.in=" + DEFAULT_XY + "," + UPDATED_XY);

        // Get all the accountsList where xy equals to UPDATED_XY
        defaultAccountsShouldNotBeFound("xy.in=" + UPDATED_XY);
    }

    @Test
    @Transactional
    void getAllAccountsByXyIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where xy is not null
        defaultAccountsShouldBeFound("xy.specified=true");

        // Get all the accountsList where xy is null
        defaultAccountsShouldNotBeFound("xy.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByXyContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where xy contains DEFAULT_XY
        defaultAccountsShouldBeFound("xy.contains=" + DEFAULT_XY);

        // Get all the accountsList where xy contains UPDATED_XY
        defaultAccountsShouldNotBeFound("xy.contains=" + UPDATED_XY);
    }

    @Test
    @Transactional
    void getAllAccountsByXyNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where xy does not contain DEFAULT_XY
        defaultAccountsShouldNotBeFound("xy.doesNotContain=" + DEFAULT_XY);

        // Get all the accountsList where xy does not contain UPDATED_XY
        defaultAccountsShouldBeFound("xy.doesNotContain=" + UPDATED_XY);
    }

    @Test
    @Transactional
    void getAllAccountsByRoomtypeIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where roomtype equals to DEFAULT_ROOMTYPE
        defaultAccountsShouldBeFound("roomtype.equals=" + DEFAULT_ROOMTYPE);

        // Get all the accountsList where roomtype equals to UPDATED_ROOMTYPE
        defaultAccountsShouldNotBeFound("roomtype.equals=" + UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByRoomtypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where roomtype not equals to DEFAULT_ROOMTYPE
        defaultAccountsShouldNotBeFound("roomtype.notEquals=" + DEFAULT_ROOMTYPE);

        // Get all the accountsList where roomtype not equals to UPDATED_ROOMTYPE
        defaultAccountsShouldBeFound("roomtype.notEquals=" + UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByRoomtypeIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where roomtype in DEFAULT_ROOMTYPE or UPDATED_ROOMTYPE
        defaultAccountsShouldBeFound("roomtype.in=" + DEFAULT_ROOMTYPE + "," + UPDATED_ROOMTYPE);

        // Get all the accountsList where roomtype equals to UPDATED_ROOMTYPE
        defaultAccountsShouldNotBeFound("roomtype.in=" + UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByRoomtypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where roomtype is not null
        defaultAccountsShouldBeFound("roomtype.specified=true");

        // Get all the accountsList where roomtype is null
        defaultAccountsShouldNotBeFound("roomtype.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByRoomtypeContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where roomtype contains DEFAULT_ROOMTYPE
        defaultAccountsShouldBeFound("roomtype.contains=" + DEFAULT_ROOMTYPE);

        // Get all the accountsList where roomtype contains UPDATED_ROOMTYPE
        defaultAccountsShouldNotBeFound("roomtype.contains=" + UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByRoomtypeNotContainsSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where roomtype does not contain DEFAULT_ROOMTYPE
        defaultAccountsShouldNotBeFound("roomtype.doesNotContain=" + DEFAULT_ROOMTYPE);

        // Get all the accountsList where roomtype does not contain UPDATED_ROOMTYPE
        defaultAccountsShouldBeFound("roomtype.doesNotContain=" + UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void getAllAccountsByBkidIsEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bkid equals to DEFAULT_BKID
        defaultAccountsShouldBeFound("bkid.equals=" + DEFAULT_BKID);

        // Get all the accountsList where bkid equals to UPDATED_BKID
        defaultAccountsShouldNotBeFound("bkid.equals=" + UPDATED_BKID);
    }

    @Test
    @Transactional
    void getAllAccountsByBkidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bkid not equals to DEFAULT_BKID
        defaultAccountsShouldNotBeFound("bkid.notEquals=" + DEFAULT_BKID);

        // Get all the accountsList where bkid not equals to UPDATED_BKID
        defaultAccountsShouldBeFound("bkid.notEquals=" + UPDATED_BKID);
    }

    @Test
    @Transactional
    void getAllAccountsByBkidIsInShouldWork() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bkid in DEFAULT_BKID or UPDATED_BKID
        defaultAccountsShouldBeFound("bkid.in=" + DEFAULT_BKID + "," + UPDATED_BKID);

        // Get all the accountsList where bkid equals to UPDATED_BKID
        defaultAccountsShouldNotBeFound("bkid.in=" + UPDATED_BKID);
    }

    @Test
    @Transactional
    void getAllAccountsByBkidIsNullOrNotNull() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bkid is not null
        defaultAccountsShouldBeFound("bkid.specified=true");

        // Get all the accountsList where bkid is null
        defaultAccountsShouldNotBeFound("bkid.specified=false");
    }

    @Test
    @Transactional
    void getAllAccountsByBkidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bkid is greater than or equal to DEFAULT_BKID
        defaultAccountsShouldBeFound("bkid.greaterThanOrEqual=" + DEFAULT_BKID);

        // Get all the accountsList where bkid is greater than or equal to UPDATED_BKID
        defaultAccountsShouldNotBeFound("bkid.greaterThanOrEqual=" + UPDATED_BKID);
    }

    @Test
    @Transactional
    void getAllAccountsByBkidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bkid is less than or equal to DEFAULT_BKID
        defaultAccountsShouldBeFound("bkid.lessThanOrEqual=" + DEFAULT_BKID);

        // Get all the accountsList where bkid is less than or equal to SMALLER_BKID
        defaultAccountsShouldNotBeFound("bkid.lessThanOrEqual=" + SMALLER_BKID);
    }

    @Test
    @Transactional
    void getAllAccountsByBkidIsLessThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bkid is less than DEFAULT_BKID
        defaultAccountsShouldNotBeFound("bkid.lessThan=" + DEFAULT_BKID);

        // Get all the accountsList where bkid is less than UPDATED_BKID
        defaultAccountsShouldBeFound("bkid.lessThan=" + UPDATED_BKID);
    }

    @Test
    @Transactional
    void getAllAccountsByBkidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        // Get all the accountsList where bkid is greater than DEFAULT_BKID
        defaultAccountsShouldNotBeFound("bkid.greaterThan=" + DEFAULT_BKID);

        // Get all the accountsList where bkid is greater than SMALLER_BKID
        defaultAccountsShouldBeFound("bkid.greaterThan=" + SMALLER_BKID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAccountsShouldBeFound(String filter) throws Exception {
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].consumetime").value(hasItem(DEFAULT_CONSUMETIME.toString())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].feenum").value(hasItem(DEFAULT_FEENUM.intValue())))
            .andExpect(jsonPath("$.[*].money").value(hasItem(sameNumber(DEFAULT_MONEY))))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].imprest").value(hasItem(sameNumber(DEFAULT_IMPREST))))
            .andExpect(jsonPath("$.[*].propertiy").value(hasItem(DEFAULT_PROPERTIY)))
            .andExpect(jsonPath("$.[*].earntypen").value(hasItem(DEFAULT_EARNTYPEN.intValue())))
            .andExpect(jsonPath("$.[*].payment").value(hasItem(DEFAULT_PAYMENT.intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].ulogogram").value(hasItem(DEFAULT_ULOGOGRAM)))
            .andExpect(jsonPath("$.[*].lk").value(hasItem(DEFAULT_LK.intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)))
            .andExpect(jsonPath("$.[*].jzSign").value(hasItem(DEFAULT_JZ_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].jzflag").value(hasItem(DEFAULT_JZFLAG.intValue())))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN)))
            .andExpect(jsonPath("$.[*].bs").value(hasItem(DEFAULT_BS.intValue())))
            .andExpect(jsonPath("$.[*].jzhotel").value(hasItem(DEFAULT_JZHOTEL.toString())))
            .andExpect(jsonPath("$.[*].jzempn").value(hasItem(DEFAULT_JZEMPN)))
            .andExpect(jsonPath("$.[*].jztime").value(hasItem(DEFAULT_JZTIME.toString())))
            .andExpect(jsonPath("$.[*].chonghong").value(hasItem(DEFAULT_CHONGHONG.intValue())))
            .andExpect(jsonPath("$.[*].billno").value(hasItem(DEFAULT_BILLNO)))
            .andExpect(jsonPath("$.[*].printcount").value(hasItem(DEFAULT_PRINTCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].vipjf").value(hasItem(sameNumber(DEFAULT_VIPJF))))
            .andExpect(jsonPath("$.[*].hykh").value(hasItem(DEFAULT_HYKH)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].sgdjh").value(hasItem(DEFAULT_SGDJH)))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())))
            .andExpect(jsonPath("$.[*].guestId").value(hasItem(DEFAULT_GUEST_ID.doubleValue())))
            .andExpect(jsonPath("$.[*].yhkh").value(hasItem(DEFAULT_YHKH)))
            .andExpect(jsonPath("$.[*].djq").value(hasItem(DEFAULT_DJQ)))
            .andExpect(jsonPath("$.[*].ysje").value(hasItem(sameNumber(DEFAULT_YSJE))))
            .andExpect(jsonPath("$.[*].bj").value(hasItem(DEFAULT_BJ)))
            .andExpect(jsonPath("$.[*].bjempn").value(hasItem(DEFAULT_BJEMPN)))
            .andExpect(jsonPath("$.[*].bjtime").value(hasItem(DEFAULT_BJTIME.toString())))
            .andExpect(jsonPath("$.[*].paper2").value(hasItem(DEFAULT_PAPER_2)))
            .andExpect(jsonPath("$.[*].bc").value(hasItem(DEFAULT_BC)))
            .andExpect(jsonPath("$.[*].auto").value(hasItem(DEFAULT_AUTO)))
            .andExpect(jsonPath("$.[*].xsy").value(hasItem(DEFAULT_XSY)))
            .andExpect(jsonPath("$.[*].djkh").value(hasItem(DEFAULT_DJKH)))
            .andExpect(jsonPath("$.[*].djsign").value(hasItem(DEFAULT_DJSIGN)))
            .andExpect(jsonPath("$.[*].classname").value(hasItem(DEFAULT_CLASSNAME)))
            .andExpect(jsonPath("$.[*].iscy").value(hasItem(DEFAULT_ISCY)))
            .andExpect(jsonPath("$.[*].bsign").value(hasItem(DEFAULT_BSIGN)))
            .andExpect(jsonPath("$.[*].fx").value(hasItem(DEFAULT_FX)))
            .andExpect(jsonPath("$.[*].djlx").value(hasItem(DEFAULT_DJLX)))
            .andExpect(jsonPath("$.[*].isup").value(hasItem(DEFAULT_ISUP.intValue())))
            .andExpect(jsonPath("$.[*].yongjin").value(hasItem(sameNumber(DEFAULT_YONGJIN))))
            .andExpect(jsonPath("$.[*].czpc").value(hasItem(DEFAULT_CZPC)))
            .andExpect(jsonPath("$.[*].cxflag").value(hasItem(DEFAULT_CXFLAG.intValue())))
            .andExpect(jsonPath("$.[*].pmemo").value(hasItem(DEFAULT_PMEMO)))
            .andExpect(jsonPath("$.[*].czbillno").value(hasItem(DEFAULT_CZBILLNO)))
            .andExpect(jsonPath("$.[*].djqbz").value(hasItem(DEFAULT_DJQBZ)))
            .andExpect(jsonPath("$.[*].ysqmemo").value(hasItem(DEFAULT_YSQMEMO)))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].outTradeNo").value(hasItem(DEFAULT_OUT_TRADE_NO)))
            .andExpect(jsonPath("$.[*].gsname").value(hasItem(DEFAULT_GSNAME)))
            .andExpect(jsonPath("$.[*].rz").value(hasItem(DEFAULT_RZ.toString())))
            .andExpect(jsonPath("$.[*].gz").value(hasItem(DEFAULT_GZ.toString())))
            .andExpect(jsonPath("$.[*].ts").value(hasItem(DEFAULT_TS.intValue())))
            .andExpect(jsonPath("$.[*].ky").value(hasItem(DEFAULT_KY)))
            .andExpect(jsonPath("$.[*].xy").value(hasItem(DEFAULT_XY)))
            .andExpect(jsonPath("$.[*].roomtype").value(hasItem(DEFAULT_ROOMTYPE)))
            .andExpect(jsonPath("$.[*].bkid").value(hasItem(DEFAULT_BKID.intValue())));

        // Check, that the count call also returns 1
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAccountsShouldNotBeFound(String filter) throws Exception {
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAccountsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingAccounts() throws Exception {
        // Get the accounts
        restAccountsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();

        // Update the accounts
        Accounts updatedAccounts = accountsRepository.findById(accounts.getId()).get();
        // Disconnect from session so that the updates on updatedAccounts are not directly saved in db
        em.detach(updatedAccounts);
        updatedAccounts
            .account(UPDATED_ACCOUNT)
            .consumetime(UPDATED_CONSUMETIME)
            .hoteltime(UPDATED_HOTELTIME)
            .feenum(UPDATED_FEENUM)
            .money(UPDATED_MONEY)
            .memo(UPDATED_MEMO)
            .empn(UPDATED_EMPN)
            .imprest(UPDATED_IMPREST)
            .propertiy(UPDATED_PROPERTIY)
            .earntypen(UPDATED_EARNTYPEN)
            .payment(UPDATED_PAYMENT)
            .roomn(UPDATED_ROOMN)
            .ulogogram(UPDATED_ULOGOGRAM)
            .lk(UPDATED_LK)
            .acc(UPDATED_ACC)
            .jzSign(UPDATED_JZ_SIGN)
            .jzflag(UPDATED_JZFLAG)
            .sign(UPDATED_SIGN)
            .bs(UPDATED_BS)
            .jzhotel(UPDATED_JZHOTEL)
            .jzempn(UPDATED_JZEMPN)
            .jztime(UPDATED_JZTIME)
            .chonghong(UPDATED_CHONGHONG)
            .billno(UPDATED_BILLNO)
            .printcount(UPDATED_PRINTCOUNT)
            .vipjf(UPDATED_VIPJF)
            .hykh(UPDATED_HYKH)
            .sl(UPDATED_SL)
            .sgdjh(UPDATED_SGDJH)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW)
            .guestId(UPDATED_GUEST_ID)
            .yhkh(UPDATED_YHKH)
            .djq(UPDATED_DJQ)
            .ysje(UPDATED_YSJE)
            .bj(UPDATED_BJ)
            .bjempn(UPDATED_BJEMPN)
            .bjtime(UPDATED_BJTIME)
            .paper2(UPDATED_PAPER_2)
            .bc(UPDATED_BC)
            .auto(UPDATED_AUTO)
            .xsy(UPDATED_XSY)
            .djkh(UPDATED_DJKH)
            .djsign(UPDATED_DJSIGN)
            .classname(UPDATED_CLASSNAME)
            .iscy(UPDATED_ISCY)
            .bsign(UPDATED_BSIGN)
            .fx(UPDATED_FX)
            .djlx(UPDATED_DJLX)
            .isup(UPDATED_ISUP)
            .yongjin(UPDATED_YONGJIN)
            .czpc(UPDATED_CZPC)
            .cxflag(UPDATED_CXFLAG)
            .pmemo(UPDATED_PMEMO)
            .czbillno(UPDATED_CZBILLNO)
            .djqbz(UPDATED_DJQBZ)
            .ysqmemo(UPDATED_YSQMEMO)
            .transactionId(UPDATED_TRANSACTION_ID)
            .outTradeNo(UPDATED_OUT_TRADE_NO)
            .gsname(UPDATED_GSNAME)
            .rz(UPDATED_RZ)
            .gz(UPDATED_GZ)
            .ts(UPDATED_TS)
            .ky(UPDATED_KY)
            .xy(UPDATED_XY)
            .roomtype(UPDATED_ROOMTYPE)
            .bkid(UPDATED_BKID);
        AccountsDTO accountsDTO = accountsMapper.toDto(updatedAccounts);

        restAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
        Accounts testAccounts = accountsList.get(accountsList.size() - 1);
        assertThat(testAccounts.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testAccounts.getConsumetime()).isEqualTo(UPDATED_CONSUMETIME);
        assertThat(testAccounts.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testAccounts.getFeenum()).isEqualTo(UPDATED_FEENUM);
        assertThat(testAccounts.getMoney()).isEqualTo(UPDATED_MONEY);
        assertThat(testAccounts.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testAccounts.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testAccounts.getImprest()).isEqualTo(UPDATED_IMPREST);
        assertThat(testAccounts.getPropertiy()).isEqualTo(UPDATED_PROPERTIY);
        assertThat(testAccounts.getEarntypen()).isEqualTo(UPDATED_EARNTYPEN);
        assertThat(testAccounts.getPayment()).isEqualTo(UPDATED_PAYMENT);
        assertThat(testAccounts.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testAccounts.getUlogogram()).isEqualTo(UPDATED_ULOGOGRAM);
        assertThat(testAccounts.getLk()).isEqualTo(UPDATED_LK);
        assertThat(testAccounts.getAcc()).isEqualTo(UPDATED_ACC);
        assertThat(testAccounts.getJzSign()).isEqualTo(UPDATED_JZ_SIGN);
        assertThat(testAccounts.getJzflag()).isEqualTo(UPDATED_JZFLAG);
        assertThat(testAccounts.getSign()).isEqualTo(UPDATED_SIGN);
        assertThat(testAccounts.getBs()).isEqualTo(UPDATED_BS);
        assertThat(testAccounts.getJzhotel()).isEqualTo(UPDATED_JZHOTEL);
        assertThat(testAccounts.getJzempn()).isEqualTo(UPDATED_JZEMPN);
        assertThat(testAccounts.getJztime()).isEqualTo(UPDATED_JZTIME);
        assertThat(testAccounts.getChonghong()).isEqualTo(UPDATED_CHONGHONG);
        assertThat(testAccounts.getBillno()).isEqualTo(UPDATED_BILLNO);
        assertThat(testAccounts.getPrintcount()).isEqualTo(UPDATED_PRINTCOUNT);
        assertThat(testAccounts.getVipjf()).isEqualTo(UPDATED_VIPJF);
        assertThat(testAccounts.getHykh()).isEqualTo(UPDATED_HYKH);
        assertThat(testAccounts.getSl()).isEqualTo(UPDATED_SL);
        assertThat(testAccounts.getSgdjh()).isEqualTo(UPDATED_SGDJH);
        assertThat(testAccounts.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testAccounts.getIsnew()).isEqualTo(UPDATED_ISNEW);
        assertThat(testAccounts.getGuestId()).isEqualTo(UPDATED_GUEST_ID);
        assertThat(testAccounts.getYhkh()).isEqualTo(UPDATED_YHKH);
        assertThat(testAccounts.getDjq()).isEqualTo(UPDATED_DJQ);
        assertThat(testAccounts.getYsje()).isEqualTo(UPDATED_YSJE);
        assertThat(testAccounts.getBj()).isEqualTo(UPDATED_BJ);
        assertThat(testAccounts.getBjempn()).isEqualTo(UPDATED_BJEMPN);
        assertThat(testAccounts.getBjtime()).isEqualTo(UPDATED_BJTIME);
        assertThat(testAccounts.getPaper2()).isEqualTo(UPDATED_PAPER_2);
        assertThat(testAccounts.getBc()).isEqualTo(UPDATED_BC);
        assertThat(testAccounts.getAuto()).isEqualTo(UPDATED_AUTO);
        assertThat(testAccounts.getXsy()).isEqualTo(UPDATED_XSY);
        assertThat(testAccounts.getDjkh()).isEqualTo(UPDATED_DJKH);
        assertThat(testAccounts.getDjsign()).isEqualTo(UPDATED_DJSIGN);
        assertThat(testAccounts.getClassname()).isEqualTo(UPDATED_CLASSNAME);
        assertThat(testAccounts.getIscy()).isEqualTo(UPDATED_ISCY);
        assertThat(testAccounts.getBsign()).isEqualTo(UPDATED_BSIGN);
        assertThat(testAccounts.getFx()).isEqualTo(UPDATED_FX);
        assertThat(testAccounts.getDjlx()).isEqualTo(UPDATED_DJLX);
        assertThat(testAccounts.getIsup()).isEqualTo(UPDATED_ISUP);
        assertThat(testAccounts.getYongjin()).isEqualTo(UPDATED_YONGJIN);
        assertThat(testAccounts.getCzpc()).isEqualTo(UPDATED_CZPC);
        assertThat(testAccounts.getCxflag()).isEqualTo(UPDATED_CXFLAG);
        assertThat(testAccounts.getPmemo()).isEqualTo(UPDATED_PMEMO);
        assertThat(testAccounts.getCzbillno()).isEqualTo(UPDATED_CZBILLNO);
        assertThat(testAccounts.getDjqbz()).isEqualTo(UPDATED_DJQBZ);
        assertThat(testAccounts.getYsqmemo()).isEqualTo(UPDATED_YSQMEMO);
        assertThat(testAccounts.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testAccounts.getOutTradeNo()).isEqualTo(UPDATED_OUT_TRADE_NO);
        assertThat(testAccounts.getGsname()).isEqualTo(UPDATED_GSNAME);
        assertThat(testAccounts.getRz()).isEqualTo(UPDATED_RZ);
        assertThat(testAccounts.getGz()).isEqualTo(UPDATED_GZ);
        assertThat(testAccounts.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testAccounts.getKy()).isEqualTo(UPDATED_KY);
        assertThat(testAccounts.getXy()).isEqualTo(UPDATED_XY);
        assertThat(testAccounts.getRoomtype()).isEqualTo(UPDATED_ROOMTYPE);
        assertThat(testAccounts.getBkid()).isEqualTo(UPDATED_BKID);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository).save(testAccounts);
    }

    @Test
    @Transactional
    void putNonExistingAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();
        accounts.setId(count.incrementAndGet());

        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, accountsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository, times(0)).save(accounts);
    }

    @Test
    @Transactional
    void putWithIdMismatchAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();
        accounts.setId(count.incrementAndGet());

        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(accountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository, times(0)).save(accounts);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();
        accounts.setId(count.incrementAndGet());

        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(accountsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository, times(0)).save(accounts);
    }

    @Test
    @Transactional
    void partialUpdateAccountsWithPatch() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();

        // Update the accounts using partial update
        Accounts partialUpdatedAccounts = new Accounts();
        partialUpdatedAccounts.setId(accounts.getId());

        partialUpdatedAccounts
            .account(UPDATED_ACCOUNT)
            .consumetime(UPDATED_CONSUMETIME)
            .hoteltime(UPDATED_HOTELTIME)
            .money(UPDATED_MONEY)
            .imprest(UPDATED_IMPREST)
            .ulogogram(UPDATED_ULOGOGRAM)
            .acc(UPDATED_ACC)
            .jzSign(UPDATED_JZ_SIGN)
            .bs(UPDATED_BS)
            .jzhotel(UPDATED_JZHOTEL)
            .jzempn(UPDATED_JZEMPN)
            .jztime(UPDATED_JZTIME)
            .chonghong(UPDATED_CHONGHONG)
            .billno(UPDATED_BILLNO)
            .printcount(UPDATED_PRINTCOUNT)
            .vipjf(UPDATED_VIPJF)
            .sl(UPDATED_SL)
            .sgdjh(UPDATED_SGDJH)
            .isnew(UPDATED_ISNEW)
            .guestId(UPDATED_GUEST_ID)
            .yhkh(UPDATED_YHKH)
            .djq(UPDATED_DJQ)
            .ysje(UPDATED_YSJE)
            .bjtime(UPDATED_BJTIME)
            .xsy(UPDATED_XSY)
            .iscy(UPDATED_ISCY)
            .fx(UPDATED_FX)
            .isup(UPDATED_ISUP)
            .yongjin(UPDATED_YONGJIN)
            .pmemo(UPDATED_PMEMO)
            .djqbz(UPDATED_DJQBZ)
            .transactionId(UPDATED_TRANSACTION_ID)
            .outTradeNo(UPDATED_OUT_TRADE_NO)
            .gsname(UPDATED_GSNAME)
            .rz(UPDATED_RZ)
            .gz(UPDATED_GZ)
            .xy(UPDATED_XY);

        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccounts))
            )
            .andExpect(status().isOk());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
        Accounts testAccounts = accountsList.get(accountsList.size() - 1);
        assertThat(testAccounts.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testAccounts.getConsumetime()).isEqualTo(UPDATED_CONSUMETIME);
        assertThat(testAccounts.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testAccounts.getFeenum()).isEqualTo(DEFAULT_FEENUM);
        assertThat(testAccounts.getMoney()).isEqualByComparingTo(UPDATED_MONEY);
        assertThat(testAccounts.getMemo()).isEqualTo(DEFAULT_MEMO);
        assertThat(testAccounts.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testAccounts.getImprest()).isEqualByComparingTo(UPDATED_IMPREST);
        assertThat(testAccounts.getPropertiy()).isEqualTo(DEFAULT_PROPERTIY);
        assertThat(testAccounts.getEarntypen()).isEqualTo(DEFAULT_EARNTYPEN);
        assertThat(testAccounts.getPayment()).isEqualTo(DEFAULT_PAYMENT);
        assertThat(testAccounts.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testAccounts.getUlogogram()).isEqualTo(UPDATED_ULOGOGRAM);
        assertThat(testAccounts.getLk()).isEqualTo(DEFAULT_LK);
        assertThat(testAccounts.getAcc()).isEqualTo(UPDATED_ACC);
        assertThat(testAccounts.getJzSign()).isEqualTo(UPDATED_JZ_SIGN);
        assertThat(testAccounts.getJzflag()).isEqualTo(DEFAULT_JZFLAG);
        assertThat(testAccounts.getSign()).isEqualTo(DEFAULT_SIGN);
        assertThat(testAccounts.getBs()).isEqualTo(UPDATED_BS);
        assertThat(testAccounts.getJzhotel()).isEqualTo(UPDATED_JZHOTEL);
        assertThat(testAccounts.getJzempn()).isEqualTo(UPDATED_JZEMPN);
        assertThat(testAccounts.getJztime()).isEqualTo(UPDATED_JZTIME);
        assertThat(testAccounts.getChonghong()).isEqualTo(UPDATED_CHONGHONG);
        assertThat(testAccounts.getBillno()).isEqualTo(UPDATED_BILLNO);
        assertThat(testAccounts.getPrintcount()).isEqualTo(UPDATED_PRINTCOUNT);
        assertThat(testAccounts.getVipjf()).isEqualByComparingTo(UPDATED_VIPJF);
        assertThat(testAccounts.getHykh()).isEqualTo(DEFAULT_HYKH);
        assertThat(testAccounts.getSl()).isEqualByComparingTo(UPDATED_SL);
        assertThat(testAccounts.getSgdjh()).isEqualTo(UPDATED_SGDJH);
        assertThat(testAccounts.getHoteldm()).isEqualTo(DEFAULT_HOTELDM);
        assertThat(testAccounts.getIsnew()).isEqualTo(UPDATED_ISNEW);
        assertThat(testAccounts.getGuestId()).isEqualTo(UPDATED_GUEST_ID);
        assertThat(testAccounts.getYhkh()).isEqualTo(UPDATED_YHKH);
        assertThat(testAccounts.getDjq()).isEqualTo(UPDATED_DJQ);
        assertThat(testAccounts.getYsje()).isEqualByComparingTo(UPDATED_YSJE);
        assertThat(testAccounts.getBj()).isEqualTo(DEFAULT_BJ);
        assertThat(testAccounts.getBjempn()).isEqualTo(DEFAULT_BJEMPN);
        assertThat(testAccounts.getBjtime()).isEqualTo(UPDATED_BJTIME);
        assertThat(testAccounts.getPaper2()).isEqualTo(DEFAULT_PAPER_2);
        assertThat(testAccounts.getBc()).isEqualTo(DEFAULT_BC);
        assertThat(testAccounts.getAuto()).isEqualTo(DEFAULT_AUTO);
        assertThat(testAccounts.getXsy()).isEqualTo(UPDATED_XSY);
        assertThat(testAccounts.getDjkh()).isEqualTo(DEFAULT_DJKH);
        assertThat(testAccounts.getDjsign()).isEqualTo(DEFAULT_DJSIGN);
        assertThat(testAccounts.getClassname()).isEqualTo(DEFAULT_CLASSNAME);
        assertThat(testAccounts.getIscy()).isEqualTo(UPDATED_ISCY);
        assertThat(testAccounts.getBsign()).isEqualTo(DEFAULT_BSIGN);
        assertThat(testAccounts.getFx()).isEqualTo(UPDATED_FX);
        assertThat(testAccounts.getDjlx()).isEqualTo(DEFAULT_DJLX);
        assertThat(testAccounts.getIsup()).isEqualTo(UPDATED_ISUP);
        assertThat(testAccounts.getYongjin()).isEqualByComparingTo(UPDATED_YONGJIN);
        assertThat(testAccounts.getCzpc()).isEqualTo(DEFAULT_CZPC);
        assertThat(testAccounts.getCxflag()).isEqualTo(DEFAULT_CXFLAG);
        assertThat(testAccounts.getPmemo()).isEqualTo(UPDATED_PMEMO);
        assertThat(testAccounts.getCzbillno()).isEqualTo(DEFAULT_CZBILLNO);
        assertThat(testAccounts.getDjqbz()).isEqualTo(UPDATED_DJQBZ);
        assertThat(testAccounts.getYsqmemo()).isEqualTo(DEFAULT_YSQMEMO);
        assertThat(testAccounts.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testAccounts.getOutTradeNo()).isEqualTo(UPDATED_OUT_TRADE_NO);
        assertThat(testAccounts.getGsname()).isEqualTo(UPDATED_GSNAME);
        assertThat(testAccounts.getRz()).isEqualTo(UPDATED_RZ);
        assertThat(testAccounts.getGz()).isEqualTo(UPDATED_GZ);
        assertThat(testAccounts.getTs()).isEqualTo(DEFAULT_TS);
        assertThat(testAccounts.getKy()).isEqualTo(DEFAULT_KY);
        assertThat(testAccounts.getXy()).isEqualTo(UPDATED_XY);
        assertThat(testAccounts.getRoomtype()).isEqualTo(DEFAULT_ROOMTYPE);
        assertThat(testAccounts.getBkid()).isEqualTo(DEFAULT_BKID);
    }

    @Test
    @Transactional
    void fullUpdateAccountsWithPatch() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();

        // Update the accounts using partial update
        Accounts partialUpdatedAccounts = new Accounts();
        partialUpdatedAccounts.setId(accounts.getId());

        partialUpdatedAccounts
            .account(UPDATED_ACCOUNT)
            .consumetime(UPDATED_CONSUMETIME)
            .hoteltime(UPDATED_HOTELTIME)
            .feenum(UPDATED_FEENUM)
            .money(UPDATED_MONEY)
            .memo(UPDATED_MEMO)
            .empn(UPDATED_EMPN)
            .imprest(UPDATED_IMPREST)
            .propertiy(UPDATED_PROPERTIY)
            .earntypen(UPDATED_EARNTYPEN)
            .payment(UPDATED_PAYMENT)
            .roomn(UPDATED_ROOMN)
            .ulogogram(UPDATED_ULOGOGRAM)
            .lk(UPDATED_LK)
            .acc(UPDATED_ACC)
            .jzSign(UPDATED_JZ_SIGN)
            .jzflag(UPDATED_JZFLAG)
            .sign(UPDATED_SIGN)
            .bs(UPDATED_BS)
            .jzhotel(UPDATED_JZHOTEL)
            .jzempn(UPDATED_JZEMPN)
            .jztime(UPDATED_JZTIME)
            .chonghong(UPDATED_CHONGHONG)
            .billno(UPDATED_BILLNO)
            .printcount(UPDATED_PRINTCOUNT)
            .vipjf(UPDATED_VIPJF)
            .hykh(UPDATED_HYKH)
            .sl(UPDATED_SL)
            .sgdjh(UPDATED_SGDJH)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW)
            .guestId(UPDATED_GUEST_ID)
            .yhkh(UPDATED_YHKH)
            .djq(UPDATED_DJQ)
            .ysje(UPDATED_YSJE)
            .bj(UPDATED_BJ)
            .bjempn(UPDATED_BJEMPN)
            .bjtime(UPDATED_BJTIME)
            .paper2(UPDATED_PAPER_2)
            .bc(UPDATED_BC)
            .auto(UPDATED_AUTO)
            .xsy(UPDATED_XSY)
            .djkh(UPDATED_DJKH)
            .djsign(UPDATED_DJSIGN)
            .classname(UPDATED_CLASSNAME)
            .iscy(UPDATED_ISCY)
            .bsign(UPDATED_BSIGN)
            .fx(UPDATED_FX)
            .djlx(UPDATED_DJLX)
            .isup(UPDATED_ISUP)
            .yongjin(UPDATED_YONGJIN)
            .czpc(UPDATED_CZPC)
            .cxflag(UPDATED_CXFLAG)
            .pmemo(UPDATED_PMEMO)
            .czbillno(UPDATED_CZBILLNO)
            .djqbz(UPDATED_DJQBZ)
            .ysqmemo(UPDATED_YSQMEMO)
            .transactionId(UPDATED_TRANSACTION_ID)
            .outTradeNo(UPDATED_OUT_TRADE_NO)
            .gsname(UPDATED_GSNAME)
            .rz(UPDATED_RZ)
            .gz(UPDATED_GZ)
            .ts(UPDATED_TS)
            .ky(UPDATED_KY)
            .xy(UPDATED_XY)
            .roomtype(UPDATED_ROOMTYPE)
            .bkid(UPDATED_BKID);

        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAccounts.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAccounts))
            )
            .andExpect(status().isOk());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);
        Accounts testAccounts = accountsList.get(accountsList.size() - 1);
        assertThat(testAccounts.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testAccounts.getConsumetime()).isEqualTo(UPDATED_CONSUMETIME);
        assertThat(testAccounts.getHoteltime()).isEqualTo(UPDATED_HOTELTIME);
        assertThat(testAccounts.getFeenum()).isEqualTo(UPDATED_FEENUM);
        assertThat(testAccounts.getMoney()).isEqualByComparingTo(UPDATED_MONEY);
        assertThat(testAccounts.getMemo()).isEqualTo(UPDATED_MEMO);
        assertThat(testAccounts.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testAccounts.getImprest()).isEqualByComparingTo(UPDATED_IMPREST);
        assertThat(testAccounts.getPropertiy()).isEqualTo(UPDATED_PROPERTIY);
        assertThat(testAccounts.getEarntypen()).isEqualTo(UPDATED_EARNTYPEN);
        assertThat(testAccounts.getPayment()).isEqualTo(UPDATED_PAYMENT);
        assertThat(testAccounts.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testAccounts.getUlogogram()).isEqualTo(UPDATED_ULOGOGRAM);
        assertThat(testAccounts.getLk()).isEqualTo(UPDATED_LK);
        assertThat(testAccounts.getAcc()).isEqualTo(UPDATED_ACC);
        assertThat(testAccounts.getJzSign()).isEqualTo(UPDATED_JZ_SIGN);
        assertThat(testAccounts.getJzflag()).isEqualTo(UPDATED_JZFLAG);
        assertThat(testAccounts.getSign()).isEqualTo(UPDATED_SIGN);
        assertThat(testAccounts.getBs()).isEqualTo(UPDATED_BS);
        assertThat(testAccounts.getJzhotel()).isEqualTo(UPDATED_JZHOTEL);
        assertThat(testAccounts.getJzempn()).isEqualTo(UPDATED_JZEMPN);
        assertThat(testAccounts.getJztime()).isEqualTo(UPDATED_JZTIME);
        assertThat(testAccounts.getChonghong()).isEqualTo(UPDATED_CHONGHONG);
        assertThat(testAccounts.getBillno()).isEqualTo(UPDATED_BILLNO);
        assertThat(testAccounts.getPrintcount()).isEqualTo(UPDATED_PRINTCOUNT);
        assertThat(testAccounts.getVipjf()).isEqualByComparingTo(UPDATED_VIPJF);
        assertThat(testAccounts.getHykh()).isEqualTo(UPDATED_HYKH);
        assertThat(testAccounts.getSl()).isEqualByComparingTo(UPDATED_SL);
        assertThat(testAccounts.getSgdjh()).isEqualTo(UPDATED_SGDJH);
        assertThat(testAccounts.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testAccounts.getIsnew()).isEqualTo(UPDATED_ISNEW);
        assertThat(testAccounts.getGuestId()).isEqualTo(UPDATED_GUEST_ID);
        assertThat(testAccounts.getYhkh()).isEqualTo(UPDATED_YHKH);
        assertThat(testAccounts.getDjq()).isEqualTo(UPDATED_DJQ);
        assertThat(testAccounts.getYsje()).isEqualByComparingTo(UPDATED_YSJE);
        assertThat(testAccounts.getBj()).isEqualTo(UPDATED_BJ);
        assertThat(testAccounts.getBjempn()).isEqualTo(UPDATED_BJEMPN);
        assertThat(testAccounts.getBjtime()).isEqualTo(UPDATED_BJTIME);
        assertThat(testAccounts.getPaper2()).isEqualTo(UPDATED_PAPER_2);
        assertThat(testAccounts.getBc()).isEqualTo(UPDATED_BC);
        assertThat(testAccounts.getAuto()).isEqualTo(UPDATED_AUTO);
        assertThat(testAccounts.getXsy()).isEqualTo(UPDATED_XSY);
        assertThat(testAccounts.getDjkh()).isEqualTo(UPDATED_DJKH);
        assertThat(testAccounts.getDjsign()).isEqualTo(UPDATED_DJSIGN);
        assertThat(testAccounts.getClassname()).isEqualTo(UPDATED_CLASSNAME);
        assertThat(testAccounts.getIscy()).isEqualTo(UPDATED_ISCY);
        assertThat(testAccounts.getBsign()).isEqualTo(UPDATED_BSIGN);
        assertThat(testAccounts.getFx()).isEqualTo(UPDATED_FX);
        assertThat(testAccounts.getDjlx()).isEqualTo(UPDATED_DJLX);
        assertThat(testAccounts.getIsup()).isEqualTo(UPDATED_ISUP);
        assertThat(testAccounts.getYongjin()).isEqualByComparingTo(UPDATED_YONGJIN);
        assertThat(testAccounts.getCzpc()).isEqualTo(UPDATED_CZPC);
        assertThat(testAccounts.getCxflag()).isEqualTo(UPDATED_CXFLAG);
        assertThat(testAccounts.getPmemo()).isEqualTo(UPDATED_PMEMO);
        assertThat(testAccounts.getCzbillno()).isEqualTo(UPDATED_CZBILLNO);
        assertThat(testAccounts.getDjqbz()).isEqualTo(UPDATED_DJQBZ);
        assertThat(testAccounts.getYsqmemo()).isEqualTo(UPDATED_YSQMEMO);
        assertThat(testAccounts.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testAccounts.getOutTradeNo()).isEqualTo(UPDATED_OUT_TRADE_NO);
        assertThat(testAccounts.getGsname()).isEqualTo(UPDATED_GSNAME);
        assertThat(testAccounts.getRz()).isEqualTo(UPDATED_RZ);
        assertThat(testAccounts.getGz()).isEqualTo(UPDATED_GZ);
        assertThat(testAccounts.getTs()).isEqualTo(UPDATED_TS);
        assertThat(testAccounts.getKy()).isEqualTo(UPDATED_KY);
        assertThat(testAccounts.getXy()).isEqualTo(UPDATED_XY);
        assertThat(testAccounts.getRoomtype()).isEqualTo(UPDATED_ROOMTYPE);
        assertThat(testAccounts.getBkid()).isEqualTo(UPDATED_BKID);
    }

    @Test
    @Transactional
    void patchNonExistingAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();
        accounts.setId(count.incrementAndGet());

        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, accountsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository, times(0)).save(accounts);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();
        accounts.setId(count.incrementAndGet());

        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(accountsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository, times(0)).save(accounts);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAccounts() throws Exception {
        int databaseSizeBeforeUpdate = accountsRepository.findAll().size();
        accounts.setId(count.incrementAndGet());

        // Create the Accounts
        AccountsDTO accountsDTO = accountsMapper.toDto(accounts);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAccountsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(accountsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Accounts in the database
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository, times(0)).save(accounts);
    }

    @Test
    @Transactional
    void deleteAccounts() throws Exception {
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);

        int databaseSizeBeforeDelete = accountsRepository.findAll().size();

        // Delete the accounts
        restAccountsMockMvc
            .perform(delete(ENTITY_API_URL_ID, accounts.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Accounts> accountsList = accountsRepository.findAll();
        assertThat(accountsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Accounts in Elasticsearch
        verify(mockAccountsSearchRepository, times(1)).deleteById(accounts.getId());
    }

    @Test
    @Transactional
    void searchAccounts() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        accountsRepository.saveAndFlush(accounts);
        when(mockAccountsSearchRepository.search(queryStringQuery("id:" + accounts.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(accounts), PageRequest.of(0, 1), 1));

        // Search the accounts
        restAccountsMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + accounts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accounts.getId().intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].consumetime").value(hasItem(DEFAULT_CONSUMETIME.toString())))
            .andExpect(jsonPath("$.[*].hoteltime").value(hasItem(DEFAULT_HOTELTIME.toString())))
            .andExpect(jsonPath("$.[*].feenum").value(hasItem(DEFAULT_FEENUM.intValue())))
            .andExpect(jsonPath("$.[*].money").value(hasItem(sameNumber(DEFAULT_MONEY))))
            .andExpect(jsonPath("$.[*].memo").value(hasItem(DEFAULT_MEMO)))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].imprest").value(hasItem(sameNumber(DEFAULT_IMPREST))))
            .andExpect(jsonPath("$.[*].propertiy").value(hasItem(DEFAULT_PROPERTIY)))
            .andExpect(jsonPath("$.[*].earntypen").value(hasItem(DEFAULT_EARNTYPEN.intValue())))
            .andExpect(jsonPath("$.[*].payment").value(hasItem(DEFAULT_PAYMENT.intValue())))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].ulogogram").value(hasItem(DEFAULT_ULOGOGRAM)))
            .andExpect(jsonPath("$.[*].lk").value(hasItem(DEFAULT_LK.intValue())))
            .andExpect(jsonPath("$.[*].acc").value(hasItem(DEFAULT_ACC)))
            .andExpect(jsonPath("$.[*].jzSign").value(hasItem(DEFAULT_JZ_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].jzflag").value(hasItem(DEFAULT_JZFLAG.intValue())))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN)))
            .andExpect(jsonPath("$.[*].bs").value(hasItem(DEFAULT_BS.intValue())))
            .andExpect(jsonPath("$.[*].jzhotel").value(hasItem(DEFAULT_JZHOTEL.toString())))
            .andExpect(jsonPath("$.[*].jzempn").value(hasItem(DEFAULT_JZEMPN)))
            .andExpect(jsonPath("$.[*].jztime").value(hasItem(DEFAULT_JZTIME.toString())))
            .andExpect(jsonPath("$.[*].chonghong").value(hasItem(DEFAULT_CHONGHONG.intValue())))
            .andExpect(jsonPath("$.[*].billno").value(hasItem(DEFAULT_BILLNO)))
            .andExpect(jsonPath("$.[*].printcount").value(hasItem(DEFAULT_PRINTCOUNT.intValue())))
            .andExpect(jsonPath("$.[*].vipjf").value(hasItem(sameNumber(DEFAULT_VIPJF))))
            .andExpect(jsonPath("$.[*].hykh").value(hasItem(DEFAULT_HYKH)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(sameNumber(DEFAULT_SL))))
            .andExpect(jsonPath("$.[*].sgdjh").value(hasItem(DEFAULT_SGDJH)))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())))
            .andExpect(jsonPath("$.[*].guestId").value(hasItem(DEFAULT_GUEST_ID.doubleValue())))
            .andExpect(jsonPath("$.[*].yhkh").value(hasItem(DEFAULT_YHKH)))
            .andExpect(jsonPath("$.[*].djq").value(hasItem(DEFAULT_DJQ)))
            .andExpect(jsonPath("$.[*].ysje").value(hasItem(sameNumber(DEFAULT_YSJE))))
            .andExpect(jsonPath("$.[*].bj").value(hasItem(DEFAULT_BJ)))
            .andExpect(jsonPath("$.[*].bjempn").value(hasItem(DEFAULT_BJEMPN)))
            .andExpect(jsonPath("$.[*].bjtime").value(hasItem(DEFAULT_BJTIME.toString())))
            .andExpect(jsonPath("$.[*].paper2").value(hasItem(DEFAULT_PAPER_2)))
            .andExpect(jsonPath("$.[*].bc").value(hasItem(DEFAULT_BC)))
            .andExpect(jsonPath("$.[*].auto").value(hasItem(DEFAULT_AUTO)))
            .andExpect(jsonPath("$.[*].xsy").value(hasItem(DEFAULT_XSY)))
            .andExpect(jsonPath("$.[*].djkh").value(hasItem(DEFAULT_DJKH)))
            .andExpect(jsonPath("$.[*].djsign").value(hasItem(DEFAULT_DJSIGN)))
            .andExpect(jsonPath("$.[*].classname").value(hasItem(DEFAULT_CLASSNAME)))
            .andExpect(jsonPath("$.[*].iscy").value(hasItem(DEFAULT_ISCY)))
            .andExpect(jsonPath("$.[*].bsign").value(hasItem(DEFAULT_BSIGN)))
            .andExpect(jsonPath("$.[*].fx").value(hasItem(DEFAULT_FX)))
            .andExpect(jsonPath("$.[*].djlx").value(hasItem(DEFAULT_DJLX)))
            .andExpect(jsonPath("$.[*].isup").value(hasItem(DEFAULT_ISUP.intValue())))
            .andExpect(jsonPath("$.[*].yongjin").value(hasItem(sameNumber(DEFAULT_YONGJIN))))
            .andExpect(jsonPath("$.[*].czpc").value(hasItem(DEFAULT_CZPC)))
            .andExpect(jsonPath("$.[*].cxflag").value(hasItem(DEFAULT_CXFLAG.intValue())))
            .andExpect(jsonPath("$.[*].pmemo").value(hasItem(DEFAULT_PMEMO)))
            .andExpect(jsonPath("$.[*].czbillno").value(hasItem(DEFAULT_CZBILLNO)))
            .andExpect(jsonPath("$.[*].djqbz").value(hasItem(DEFAULT_DJQBZ)))
            .andExpect(jsonPath("$.[*].ysqmemo").value(hasItem(DEFAULT_YSQMEMO)))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID)))
            .andExpect(jsonPath("$.[*].outTradeNo").value(hasItem(DEFAULT_OUT_TRADE_NO)))
            .andExpect(jsonPath("$.[*].gsname").value(hasItem(DEFAULT_GSNAME)))
            .andExpect(jsonPath("$.[*].rz").value(hasItem(DEFAULT_RZ.toString())))
            .andExpect(jsonPath("$.[*].gz").value(hasItem(DEFAULT_GZ.toString())))
            .andExpect(jsonPath("$.[*].ts").value(hasItem(DEFAULT_TS.intValue())))
            .andExpect(jsonPath("$.[*].ky").value(hasItem(DEFAULT_KY)))
            .andExpect(jsonPath("$.[*].xy").value(hasItem(DEFAULT_XY)))
            .andExpect(jsonPath("$.[*].roomtype").value(hasItem(DEFAULT_ROOMTYPE)))
            .andExpect(jsonPath("$.[*].bkid").value(hasItem(DEFAULT_BKID.intValue())));
    }
}
