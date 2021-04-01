package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.BookYst;
import ihotel.app.repository.BookYstRepository;
import ihotel.app.repository.search.BookYstSearchRepository;
import ihotel.app.service.criteria.BookYstCriteria;
import ihotel.app.service.dto.BookYstDTO;
import ihotel.app.service.mapper.BookYstMapper;
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
 * Integration tests for the {@link BookYstResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BookYstResourceIT {

    private static final String DEFAULT_ROOMCODE = "AAAAAAAAAA";
    private static final String UPDATED_ROOMCODE = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMNAME = "AAAAAAAAAA";
    private static final String UPDATED_ROOMNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMNUM = "AAAAAAAAAA";
    private static final String UPDATED_ROOMNUM = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMSEPARATENUM = "AAAAAAAAAA";
    private static final String UPDATED_ROOMSEPARATENUM = "BBBBBBBBBB";

    private static final String DEFAULT_BEDIDS = "AAAAAAAAAA";
    private static final String UPDATED_BEDIDS = "BBBBBBBBBB";

    private static final String DEFAULT_BEDSIMPLEDESC = "AAAAAAAAAA";
    private static final String UPDATED_BEDSIMPLEDESC = "BBBBBBBBBB";

    private static final String DEFAULT_BEDNUM = "AAAAAAAAAA";
    private static final String UPDATED_BEDNUM = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMSIZE = "AAAAAAAAAA";
    private static final String UPDATED_ROOMSIZE = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMFLOOR = "AAAAAAAAAA";
    private static final String UPDATED_ROOMFLOOR = "BBBBBBBBBB";

    private static final String DEFAULT_NETSERVICE = "AAAAAAAAAA";
    private static final String UPDATED_NETSERVICE = "BBBBBBBBBB";

    private static final String DEFAULT_NETTYPE = "AAAAAAAAAA";
    private static final String UPDATED_NETTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ISWINDOW = "AAAAAAAAAA";
    private static final String UPDATED_ISWINDOW = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_SORTID = "AAAAAAAAAA";
    private static final String UPDATED_SORTID = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMSTATE = "AAAAAAAAAA";
    private static final String UPDATED_ROOMSTATE = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMAMENITIES = "AAAAAAAAAA";
    private static final String UPDATED_ROOMAMENITIES = "BBBBBBBBBB";

    private static final String DEFAULT_MAXGUESTNUMS = "AAAAAAAAAA";
    private static final String UPDATED_MAXGUESTNUMS = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMDISTRIBUTION = "AAAAAAAAAA";
    private static final String UPDATED_ROOMDISTRIBUTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITIONBEFOREDAYS = "AAAAAAAAAA";
    private static final String UPDATED_CONDITIONBEFOREDAYS = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITIONLEASTDAYS = "AAAAAAAAAA";
    private static final String UPDATED_CONDITIONLEASTDAYS = "BBBBBBBBBB";

    private static final String DEFAULT_CONDITIONLEASTROOMNUM = "AAAAAAAAAA";
    private static final String UPDATED_CONDITIONLEASTROOMNUM = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENTYPE = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENTYPE = "BBBBBBBBBB";

    private static final String DEFAULT_RATEPLANDESC = "AAAAAAAAAA";
    private static final String UPDATED_RATEPLANDESC = "BBBBBBBBBB";

    private static final String DEFAULT_RATEPLANNAME = "AAAAAAAAAA";
    private static final String UPDATED_RATEPLANNAME = "BBBBBBBBBB";

    private static final String DEFAULT_RATEPLANSTATE = "AAAAAAAAAA";
    private static final String UPDATED_RATEPLANSTATE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDVALUEBEDNUM = "AAAAAAAAAA";
    private static final String UPDATED_ADDVALUEBEDNUM = "BBBBBBBBBB";

    private static final String DEFAULT_ADDVALUEBEDPRICE = "AAAAAAAAAA";
    private static final String UPDATED_ADDVALUEBEDPRICE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDVALUEBREAKFASTNUM = "AAAAAAAAAA";
    private static final String UPDATED_ADDVALUEBREAKFASTNUM = "BBBBBBBBBB";

    private static final String DEFAULT_ADDVALUEBREAKFASTPRICE = "AAAAAAAAAA";
    private static final String UPDATED_ADDVALUEBREAKFASTPRICE = "BBBBBBBBBB";

    private static final String DEFAULT_BASEPRICE = "AAAAAAAAAA";
    private static final String UPDATED_BASEPRICE = "BBBBBBBBBB";

    private static final String DEFAULT_SALEPRICE = "AAAAAAAAAA";
    private static final String UPDATED_SALEPRICE = "BBBBBBBBBB";

    private static final String DEFAULT_MARKETPRICE = "AAAAAAAAAA";
    private static final String UPDATED_MARKETPRICE = "BBBBBBBBBB";

    private static final String DEFAULT_HOTELPRODUCTSERVICE = "AAAAAAAAAA";
    private static final String UPDATED_HOTELPRODUCTSERVICE = "BBBBBBBBBB";

    private static final String DEFAULT_HOTELPRODUCTSERVICEDESC = "AAAAAAAAAA";
    private static final String UPDATED_HOTELPRODUCTSERVICEDESC = "BBBBBBBBBB";

    private static final String DEFAULT_HOTELPRODUCTID = "AAAAAAAAAA";
    private static final String UPDATED_HOTELPRODUCTID = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMID = "AAAAAAAAAA";
    private static final String UPDATED_ROOMID = "BBBBBBBBBB";

    private static final String DEFAULT_HOTELID = "AAAAAAAAAA";
    private static final String UPDATED_HOTELID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/book-ysts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/book-ysts";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookYstRepository bookYstRepository;

    @Autowired
    private BookYstMapper bookYstMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.BookYstSearchRepositoryMockConfiguration
     */
    @Autowired
    private BookYstSearchRepository mockBookYstSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookYstMockMvc;

    private BookYst bookYst;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookYst createEntity(EntityManager em) {
        BookYst bookYst = new BookYst()
            .roomcode(DEFAULT_ROOMCODE)
            .roomname(DEFAULT_ROOMNAME)
            .roomnum(DEFAULT_ROOMNUM)
            .roomseparatenum(DEFAULT_ROOMSEPARATENUM)
            .bedids(DEFAULT_BEDIDS)
            .bedsimpledesc(DEFAULT_BEDSIMPLEDESC)
            .bednum(DEFAULT_BEDNUM)
            .roomsize(DEFAULT_ROOMSIZE)
            .roomfloor(DEFAULT_ROOMFLOOR)
            .netservice(DEFAULT_NETSERVICE)
            .nettype(DEFAULT_NETTYPE)
            .iswindow(DEFAULT_ISWINDOW)
            .remark(DEFAULT_REMARK)
            .sortid(DEFAULT_SORTID)
            .roomstate(DEFAULT_ROOMSTATE)
            .source(DEFAULT_SOURCE)
            .roomamenities(DEFAULT_ROOMAMENITIES)
            .maxguestnums(DEFAULT_MAXGUESTNUMS)
            .roomdistribution(DEFAULT_ROOMDISTRIBUTION)
            .conditionbeforedays(DEFAULT_CONDITIONBEFOREDAYS)
            .conditionleastdays(DEFAULT_CONDITIONLEASTDAYS)
            .conditionleastroomnum(DEFAULT_CONDITIONLEASTROOMNUM)
            .paymentype(DEFAULT_PAYMENTYPE)
            .rateplandesc(DEFAULT_RATEPLANDESC)
            .rateplanname(DEFAULT_RATEPLANNAME)
            .rateplanstate(DEFAULT_RATEPLANSTATE)
            .addvaluebednum(DEFAULT_ADDVALUEBEDNUM)
            .addvaluebedprice(DEFAULT_ADDVALUEBEDPRICE)
            .addvaluebreakfastnum(DEFAULT_ADDVALUEBREAKFASTNUM)
            .addvaluebreakfastprice(DEFAULT_ADDVALUEBREAKFASTPRICE)
            .baseprice(DEFAULT_BASEPRICE)
            .saleprice(DEFAULT_SALEPRICE)
            .marketprice(DEFAULT_MARKETPRICE)
            .hotelproductservice(DEFAULT_HOTELPRODUCTSERVICE)
            .hotelproductservicedesc(DEFAULT_HOTELPRODUCTSERVICEDESC)
            .hotelproductid(DEFAULT_HOTELPRODUCTID)
            .roomid(DEFAULT_ROOMID)
            .hotelid(DEFAULT_HOTELID);
        return bookYst;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BookYst createUpdatedEntity(EntityManager em) {
        BookYst bookYst = new BookYst()
            .roomcode(UPDATED_ROOMCODE)
            .roomname(UPDATED_ROOMNAME)
            .roomnum(UPDATED_ROOMNUM)
            .roomseparatenum(UPDATED_ROOMSEPARATENUM)
            .bedids(UPDATED_BEDIDS)
            .bedsimpledesc(UPDATED_BEDSIMPLEDESC)
            .bednum(UPDATED_BEDNUM)
            .roomsize(UPDATED_ROOMSIZE)
            .roomfloor(UPDATED_ROOMFLOOR)
            .netservice(UPDATED_NETSERVICE)
            .nettype(UPDATED_NETTYPE)
            .iswindow(UPDATED_ISWINDOW)
            .remark(UPDATED_REMARK)
            .sortid(UPDATED_SORTID)
            .roomstate(UPDATED_ROOMSTATE)
            .source(UPDATED_SOURCE)
            .roomamenities(UPDATED_ROOMAMENITIES)
            .maxguestnums(UPDATED_MAXGUESTNUMS)
            .roomdistribution(UPDATED_ROOMDISTRIBUTION)
            .conditionbeforedays(UPDATED_CONDITIONBEFOREDAYS)
            .conditionleastdays(UPDATED_CONDITIONLEASTDAYS)
            .conditionleastroomnum(UPDATED_CONDITIONLEASTROOMNUM)
            .paymentype(UPDATED_PAYMENTYPE)
            .rateplandesc(UPDATED_RATEPLANDESC)
            .rateplanname(UPDATED_RATEPLANNAME)
            .rateplanstate(UPDATED_RATEPLANSTATE)
            .addvaluebednum(UPDATED_ADDVALUEBEDNUM)
            .addvaluebedprice(UPDATED_ADDVALUEBEDPRICE)
            .addvaluebreakfastnum(UPDATED_ADDVALUEBREAKFASTNUM)
            .addvaluebreakfastprice(UPDATED_ADDVALUEBREAKFASTPRICE)
            .baseprice(UPDATED_BASEPRICE)
            .saleprice(UPDATED_SALEPRICE)
            .marketprice(UPDATED_MARKETPRICE)
            .hotelproductservice(UPDATED_HOTELPRODUCTSERVICE)
            .hotelproductservicedesc(UPDATED_HOTELPRODUCTSERVICEDESC)
            .hotelproductid(UPDATED_HOTELPRODUCTID)
            .roomid(UPDATED_ROOMID)
            .hotelid(UPDATED_HOTELID);
        return bookYst;
    }

    @BeforeEach
    public void initTest() {
        bookYst = createEntity(em);
    }

    @Test
    @Transactional
    void createBookYst() throws Exception {
        int databaseSizeBeforeCreate = bookYstRepository.findAll().size();
        // Create the BookYst
        BookYstDTO bookYstDTO = bookYstMapper.toDto(bookYst);
        restBookYstMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookYstDTO)))
            .andExpect(status().isCreated());

        // Validate the BookYst in the database
        List<BookYst> bookYstList = bookYstRepository.findAll();
        assertThat(bookYstList).hasSize(databaseSizeBeforeCreate + 1);
        BookYst testBookYst = bookYstList.get(bookYstList.size() - 1);
        assertThat(testBookYst.getRoomcode()).isEqualTo(DEFAULT_ROOMCODE);
        assertThat(testBookYst.getRoomname()).isEqualTo(DEFAULT_ROOMNAME);
        assertThat(testBookYst.getRoomnum()).isEqualTo(DEFAULT_ROOMNUM);
        assertThat(testBookYst.getRoomseparatenum()).isEqualTo(DEFAULT_ROOMSEPARATENUM);
        assertThat(testBookYst.getBedids()).isEqualTo(DEFAULT_BEDIDS);
        assertThat(testBookYst.getBedsimpledesc()).isEqualTo(DEFAULT_BEDSIMPLEDESC);
        assertThat(testBookYst.getBednum()).isEqualTo(DEFAULT_BEDNUM);
        assertThat(testBookYst.getRoomsize()).isEqualTo(DEFAULT_ROOMSIZE);
        assertThat(testBookYst.getRoomfloor()).isEqualTo(DEFAULT_ROOMFLOOR);
        assertThat(testBookYst.getNetservice()).isEqualTo(DEFAULT_NETSERVICE);
        assertThat(testBookYst.getNettype()).isEqualTo(DEFAULT_NETTYPE);
        assertThat(testBookYst.getIswindow()).isEqualTo(DEFAULT_ISWINDOW);
        assertThat(testBookYst.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testBookYst.getSortid()).isEqualTo(DEFAULT_SORTID);
        assertThat(testBookYst.getRoomstate()).isEqualTo(DEFAULT_ROOMSTATE);
        assertThat(testBookYst.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testBookYst.getRoomamenities()).isEqualTo(DEFAULT_ROOMAMENITIES);
        assertThat(testBookYst.getMaxguestnums()).isEqualTo(DEFAULT_MAXGUESTNUMS);
        assertThat(testBookYst.getRoomdistribution()).isEqualTo(DEFAULT_ROOMDISTRIBUTION);
        assertThat(testBookYst.getConditionbeforedays()).isEqualTo(DEFAULT_CONDITIONBEFOREDAYS);
        assertThat(testBookYst.getConditionleastdays()).isEqualTo(DEFAULT_CONDITIONLEASTDAYS);
        assertThat(testBookYst.getConditionleastroomnum()).isEqualTo(DEFAULT_CONDITIONLEASTROOMNUM);
        assertThat(testBookYst.getPaymentype()).isEqualTo(DEFAULT_PAYMENTYPE);
        assertThat(testBookYst.getRateplandesc()).isEqualTo(DEFAULT_RATEPLANDESC);
        assertThat(testBookYst.getRateplanname()).isEqualTo(DEFAULT_RATEPLANNAME);
        assertThat(testBookYst.getRateplanstate()).isEqualTo(DEFAULT_RATEPLANSTATE);
        assertThat(testBookYst.getAddvaluebednum()).isEqualTo(DEFAULT_ADDVALUEBEDNUM);
        assertThat(testBookYst.getAddvaluebedprice()).isEqualTo(DEFAULT_ADDVALUEBEDPRICE);
        assertThat(testBookYst.getAddvaluebreakfastnum()).isEqualTo(DEFAULT_ADDVALUEBREAKFASTNUM);
        assertThat(testBookYst.getAddvaluebreakfastprice()).isEqualTo(DEFAULT_ADDVALUEBREAKFASTPRICE);
        assertThat(testBookYst.getBaseprice()).isEqualTo(DEFAULT_BASEPRICE);
        assertThat(testBookYst.getSaleprice()).isEqualTo(DEFAULT_SALEPRICE);
        assertThat(testBookYst.getMarketprice()).isEqualTo(DEFAULT_MARKETPRICE);
        assertThat(testBookYst.getHotelproductservice()).isEqualTo(DEFAULT_HOTELPRODUCTSERVICE);
        assertThat(testBookYst.getHotelproductservicedesc()).isEqualTo(DEFAULT_HOTELPRODUCTSERVICEDESC);
        assertThat(testBookYst.getHotelproductid()).isEqualTo(DEFAULT_HOTELPRODUCTID);
        assertThat(testBookYst.getRoomid()).isEqualTo(DEFAULT_ROOMID);
        assertThat(testBookYst.getHotelid()).isEqualTo(DEFAULT_HOTELID);

        // Validate the BookYst in Elasticsearch
        verify(mockBookYstSearchRepository, times(1)).save(testBookYst);
    }

    @Test
    @Transactional
    void createBookYstWithExistingId() throws Exception {
        // Create the BookYst with an existing ID
        bookYst.setId(1L);
        BookYstDTO bookYstDTO = bookYstMapper.toDto(bookYst);

        int databaseSizeBeforeCreate = bookYstRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookYstMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookYstDTO)))
            .andExpect(status().isBadRequest());

        // Validate the BookYst in the database
        List<BookYst> bookYstList = bookYstRepository.findAll();
        assertThat(bookYstList).hasSize(databaseSizeBeforeCreate);

        // Validate the BookYst in Elasticsearch
        verify(mockBookYstSearchRepository, times(0)).save(bookYst);
    }

    @Test
    @Transactional
    void checkRoomnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = bookYstRepository.findAll().size();
        // set the field null
        bookYst.setRoomname(null);

        // Create the BookYst, which fails.
        BookYstDTO bookYstDTO = bookYstMapper.toDto(bookYst);

        restBookYstMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookYstDTO)))
            .andExpect(status().isBadRequest());

        List<BookYst> bookYstList = bookYstRepository.findAll();
        assertThat(bookYstList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBookYsts() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList
        restBookYstMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookYst.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomcode").value(hasItem(DEFAULT_ROOMCODE)))
            .andExpect(jsonPath("$.[*].roomname").value(hasItem(DEFAULT_ROOMNAME)))
            .andExpect(jsonPath("$.[*].roomnum").value(hasItem(DEFAULT_ROOMNUM)))
            .andExpect(jsonPath("$.[*].roomseparatenum").value(hasItem(DEFAULT_ROOMSEPARATENUM)))
            .andExpect(jsonPath("$.[*].bedids").value(hasItem(DEFAULT_BEDIDS)))
            .andExpect(jsonPath("$.[*].bedsimpledesc").value(hasItem(DEFAULT_BEDSIMPLEDESC)))
            .andExpect(jsonPath("$.[*].bednum").value(hasItem(DEFAULT_BEDNUM)))
            .andExpect(jsonPath("$.[*].roomsize").value(hasItem(DEFAULT_ROOMSIZE)))
            .andExpect(jsonPath("$.[*].roomfloor").value(hasItem(DEFAULT_ROOMFLOOR)))
            .andExpect(jsonPath("$.[*].netservice").value(hasItem(DEFAULT_NETSERVICE)))
            .andExpect(jsonPath("$.[*].nettype").value(hasItem(DEFAULT_NETTYPE)))
            .andExpect(jsonPath("$.[*].iswindow").value(hasItem(DEFAULT_ISWINDOW)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].sortid").value(hasItem(DEFAULT_SORTID)))
            .andExpect(jsonPath("$.[*].roomstate").value(hasItem(DEFAULT_ROOMSTATE)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].roomamenities").value(hasItem(DEFAULT_ROOMAMENITIES)))
            .andExpect(jsonPath("$.[*].maxguestnums").value(hasItem(DEFAULT_MAXGUESTNUMS)))
            .andExpect(jsonPath("$.[*].roomdistribution").value(hasItem(DEFAULT_ROOMDISTRIBUTION)))
            .andExpect(jsonPath("$.[*].conditionbeforedays").value(hasItem(DEFAULT_CONDITIONBEFOREDAYS)))
            .andExpect(jsonPath("$.[*].conditionleastdays").value(hasItem(DEFAULT_CONDITIONLEASTDAYS)))
            .andExpect(jsonPath("$.[*].conditionleastroomnum").value(hasItem(DEFAULT_CONDITIONLEASTROOMNUM)))
            .andExpect(jsonPath("$.[*].paymentype").value(hasItem(DEFAULT_PAYMENTYPE)))
            .andExpect(jsonPath("$.[*].rateplandesc").value(hasItem(DEFAULT_RATEPLANDESC)))
            .andExpect(jsonPath("$.[*].rateplanname").value(hasItem(DEFAULT_RATEPLANNAME)))
            .andExpect(jsonPath("$.[*].rateplanstate").value(hasItem(DEFAULT_RATEPLANSTATE)))
            .andExpect(jsonPath("$.[*].addvaluebednum").value(hasItem(DEFAULT_ADDVALUEBEDNUM)))
            .andExpect(jsonPath("$.[*].addvaluebedprice").value(hasItem(DEFAULT_ADDVALUEBEDPRICE)))
            .andExpect(jsonPath("$.[*].addvaluebreakfastnum").value(hasItem(DEFAULT_ADDVALUEBREAKFASTNUM)))
            .andExpect(jsonPath("$.[*].addvaluebreakfastprice").value(hasItem(DEFAULT_ADDVALUEBREAKFASTPRICE)))
            .andExpect(jsonPath("$.[*].baseprice").value(hasItem(DEFAULT_BASEPRICE)))
            .andExpect(jsonPath("$.[*].saleprice").value(hasItem(DEFAULT_SALEPRICE)))
            .andExpect(jsonPath("$.[*].marketprice").value(hasItem(DEFAULT_MARKETPRICE)))
            .andExpect(jsonPath("$.[*].hotelproductservice").value(hasItem(DEFAULT_HOTELPRODUCTSERVICE)))
            .andExpect(jsonPath("$.[*].hotelproductservicedesc").value(hasItem(DEFAULT_HOTELPRODUCTSERVICEDESC)))
            .andExpect(jsonPath("$.[*].hotelproductid").value(hasItem(DEFAULT_HOTELPRODUCTID)))
            .andExpect(jsonPath("$.[*].roomid").value(hasItem(DEFAULT_ROOMID)))
            .andExpect(jsonPath("$.[*].hotelid").value(hasItem(DEFAULT_HOTELID)));
    }

    @Test
    @Transactional
    void getBookYst() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get the bookYst
        restBookYstMockMvc
            .perform(get(ENTITY_API_URL_ID, bookYst.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookYst.getId().intValue()))
            .andExpect(jsonPath("$.roomcode").value(DEFAULT_ROOMCODE))
            .andExpect(jsonPath("$.roomname").value(DEFAULT_ROOMNAME))
            .andExpect(jsonPath("$.roomnum").value(DEFAULT_ROOMNUM))
            .andExpect(jsonPath("$.roomseparatenum").value(DEFAULT_ROOMSEPARATENUM))
            .andExpect(jsonPath("$.bedids").value(DEFAULT_BEDIDS))
            .andExpect(jsonPath("$.bedsimpledesc").value(DEFAULT_BEDSIMPLEDESC))
            .andExpect(jsonPath("$.bednum").value(DEFAULT_BEDNUM))
            .andExpect(jsonPath("$.roomsize").value(DEFAULT_ROOMSIZE))
            .andExpect(jsonPath("$.roomfloor").value(DEFAULT_ROOMFLOOR))
            .andExpect(jsonPath("$.netservice").value(DEFAULT_NETSERVICE))
            .andExpect(jsonPath("$.nettype").value(DEFAULT_NETTYPE))
            .andExpect(jsonPath("$.iswindow").value(DEFAULT_ISWINDOW))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.sortid").value(DEFAULT_SORTID))
            .andExpect(jsonPath("$.roomstate").value(DEFAULT_ROOMSTATE))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.roomamenities").value(DEFAULT_ROOMAMENITIES))
            .andExpect(jsonPath("$.maxguestnums").value(DEFAULT_MAXGUESTNUMS))
            .andExpect(jsonPath("$.roomdistribution").value(DEFAULT_ROOMDISTRIBUTION))
            .andExpect(jsonPath("$.conditionbeforedays").value(DEFAULT_CONDITIONBEFOREDAYS))
            .andExpect(jsonPath("$.conditionleastdays").value(DEFAULT_CONDITIONLEASTDAYS))
            .andExpect(jsonPath("$.conditionleastroomnum").value(DEFAULT_CONDITIONLEASTROOMNUM))
            .andExpect(jsonPath("$.paymentype").value(DEFAULT_PAYMENTYPE))
            .andExpect(jsonPath("$.rateplandesc").value(DEFAULT_RATEPLANDESC))
            .andExpect(jsonPath("$.rateplanname").value(DEFAULT_RATEPLANNAME))
            .andExpect(jsonPath("$.rateplanstate").value(DEFAULT_RATEPLANSTATE))
            .andExpect(jsonPath("$.addvaluebednum").value(DEFAULT_ADDVALUEBEDNUM))
            .andExpect(jsonPath("$.addvaluebedprice").value(DEFAULT_ADDVALUEBEDPRICE))
            .andExpect(jsonPath("$.addvaluebreakfastnum").value(DEFAULT_ADDVALUEBREAKFASTNUM))
            .andExpect(jsonPath("$.addvaluebreakfastprice").value(DEFAULT_ADDVALUEBREAKFASTPRICE))
            .andExpect(jsonPath("$.baseprice").value(DEFAULT_BASEPRICE))
            .andExpect(jsonPath("$.saleprice").value(DEFAULT_SALEPRICE))
            .andExpect(jsonPath("$.marketprice").value(DEFAULT_MARKETPRICE))
            .andExpect(jsonPath("$.hotelproductservice").value(DEFAULT_HOTELPRODUCTSERVICE))
            .andExpect(jsonPath("$.hotelproductservicedesc").value(DEFAULT_HOTELPRODUCTSERVICEDESC))
            .andExpect(jsonPath("$.hotelproductid").value(DEFAULT_HOTELPRODUCTID))
            .andExpect(jsonPath("$.roomid").value(DEFAULT_ROOMID))
            .andExpect(jsonPath("$.hotelid").value(DEFAULT_HOTELID));
    }

    @Test
    @Transactional
    void getBookYstsByIdFiltering() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        Long id = bookYst.getId();

        defaultBookYstShouldBeFound("id.equals=" + id);
        defaultBookYstShouldNotBeFound("id.notEquals=" + id);

        defaultBookYstShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBookYstShouldNotBeFound("id.greaterThan=" + id);

        defaultBookYstShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBookYstShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomcodeIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomcode equals to DEFAULT_ROOMCODE
        defaultBookYstShouldBeFound("roomcode.equals=" + DEFAULT_ROOMCODE);

        // Get all the bookYstList where roomcode equals to UPDATED_ROOMCODE
        defaultBookYstShouldNotBeFound("roomcode.equals=" + UPDATED_ROOMCODE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomcodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomcode not equals to DEFAULT_ROOMCODE
        defaultBookYstShouldNotBeFound("roomcode.notEquals=" + DEFAULT_ROOMCODE);

        // Get all the bookYstList where roomcode not equals to UPDATED_ROOMCODE
        defaultBookYstShouldBeFound("roomcode.notEquals=" + UPDATED_ROOMCODE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomcodeIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomcode in DEFAULT_ROOMCODE or UPDATED_ROOMCODE
        defaultBookYstShouldBeFound("roomcode.in=" + DEFAULT_ROOMCODE + "," + UPDATED_ROOMCODE);

        // Get all the bookYstList where roomcode equals to UPDATED_ROOMCODE
        defaultBookYstShouldNotBeFound("roomcode.in=" + UPDATED_ROOMCODE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomcodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomcode is not null
        defaultBookYstShouldBeFound("roomcode.specified=true");

        // Get all the bookYstList where roomcode is null
        defaultBookYstShouldNotBeFound("roomcode.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomcodeContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomcode contains DEFAULT_ROOMCODE
        defaultBookYstShouldBeFound("roomcode.contains=" + DEFAULT_ROOMCODE);

        // Get all the bookYstList where roomcode contains UPDATED_ROOMCODE
        defaultBookYstShouldNotBeFound("roomcode.contains=" + UPDATED_ROOMCODE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomcodeNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomcode does not contain DEFAULT_ROOMCODE
        defaultBookYstShouldNotBeFound("roomcode.doesNotContain=" + DEFAULT_ROOMCODE);

        // Get all the bookYstList where roomcode does not contain UPDATED_ROOMCODE
        defaultBookYstShouldBeFound("roomcode.doesNotContain=" + UPDATED_ROOMCODE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomnameIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomname equals to DEFAULT_ROOMNAME
        defaultBookYstShouldBeFound("roomname.equals=" + DEFAULT_ROOMNAME);

        // Get all the bookYstList where roomname equals to UPDATED_ROOMNAME
        defaultBookYstShouldNotBeFound("roomname.equals=" + UPDATED_ROOMNAME);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomname not equals to DEFAULT_ROOMNAME
        defaultBookYstShouldNotBeFound("roomname.notEquals=" + DEFAULT_ROOMNAME);

        // Get all the bookYstList where roomname not equals to UPDATED_ROOMNAME
        defaultBookYstShouldBeFound("roomname.notEquals=" + UPDATED_ROOMNAME);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomnameIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomname in DEFAULT_ROOMNAME or UPDATED_ROOMNAME
        defaultBookYstShouldBeFound("roomname.in=" + DEFAULT_ROOMNAME + "," + UPDATED_ROOMNAME);

        // Get all the bookYstList where roomname equals to UPDATED_ROOMNAME
        defaultBookYstShouldNotBeFound("roomname.in=" + UPDATED_ROOMNAME);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomname is not null
        defaultBookYstShouldBeFound("roomname.specified=true");

        // Get all the bookYstList where roomname is null
        defaultBookYstShouldNotBeFound("roomname.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomnameContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomname contains DEFAULT_ROOMNAME
        defaultBookYstShouldBeFound("roomname.contains=" + DEFAULT_ROOMNAME);

        // Get all the bookYstList where roomname contains UPDATED_ROOMNAME
        defaultBookYstShouldNotBeFound("roomname.contains=" + UPDATED_ROOMNAME);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomnameNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomname does not contain DEFAULT_ROOMNAME
        defaultBookYstShouldNotBeFound("roomname.doesNotContain=" + DEFAULT_ROOMNAME);

        // Get all the bookYstList where roomname does not contain UPDATED_ROOMNAME
        defaultBookYstShouldBeFound("roomname.doesNotContain=" + UPDATED_ROOMNAME);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomnumIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomnum equals to DEFAULT_ROOMNUM
        defaultBookYstShouldBeFound("roomnum.equals=" + DEFAULT_ROOMNUM);

        // Get all the bookYstList where roomnum equals to UPDATED_ROOMNUM
        defaultBookYstShouldNotBeFound("roomnum.equals=" + UPDATED_ROOMNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomnumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomnum not equals to DEFAULT_ROOMNUM
        defaultBookYstShouldNotBeFound("roomnum.notEquals=" + DEFAULT_ROOMNUM);

        // Get all the bookYstList where roomnum not equals to UPDATED_ROOMNUM
        defaultBookYstShouldBeFound("roomnum.notEquals=" + UPDATED_ROOMNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomnumIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomnum in DEFAULT_ROOMNUM or UPDATED_ROOMNUM
        defaultBookYstShouldBeFound("roomnum.in=" + DEFAULT_ROOMNUM + "," + UPDATED_ROOMNUM);

        // Get all the bookYstList where roomnum equals to UPDATED_ROOMNUM
        defaultBookYstShouldNotBeFound("roomnum.in=" + UPDATED_ROOMNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomnumIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomnum is not null
        defaultBookYstShouldBeFound("roomnum.specified=true");

        // Get all the bookYstList where roomnum is null
        defaultBookYstShouldNotBeFound("roomnum.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomnumContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomnum contains DEFAULT_ROOMNUM
        defaultBookYstShouldBeFound("roomnum.contains=" + DEFAULT_ROOMNUM);

        // Get all the bookYstList where roomnum contains UPDATED_ROOMNUM
        defaultBookYstShouldNotBeFound("roomnum.contains=" + UPDATED_ROOMNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomnumNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomnum does not contain DEFAULT_ROOMNUM
        defaultBookYstShouldNotBeFound("roomnum.doesNotContain=" + DEFAULT_ROOMNUM);

        // Get all the bookYstList where roomnum does not contain UPDATED_ROOMNUM
        defaultBookYstShouldBeFound("roomnum.doesNotContain=" + UPDATED_ROOMNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomseparatenumIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomseparatenum equals to DEFAULT_ROOMSEPARATENUM
        defaultBookYstShouldBeFound("roomseparatenum.equals=" + DEFAULT_ROOMSEPARATENUM);

        // Get all the bookYstList where roomseparatenum equals to UPDATED_ROOMSEPARATENUM
        defaultBookYstShouldNotBeFound("roomseparatenum.equals=" + UPDATED_ROOMSEPARATENUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomseparatenumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomseparatenum not equals to DEFAULT_ROOMSEPARATENUM
        defaultBookYstShouldNotBeFound("roomseparatenum.notEquals=" + DEFAULT_ROOMSEPARATENUM);

        // Get all the bookYstList where roomseparatenum not equals to UPDATED_ROOMSEPARATENUM
        defaultBookYstShouldBeFound("roomseparatenum.notEquals=" + UPDATED_ROOMSEPARATENUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomseparatenumIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomseparatenum in DEFAULT_ROOMSEPARATENUM or UPDATED_ROOMSEPARATENUM
        defaultBookYstShouldBeFound("roomseparatenum.in=" + DEFAULT_ROOMSEPARATENUM + "," + UPDATED_ROOMSEPARATENUM);

        // Get all the bookYstList where roomseparatenum equals to UPDATED_ROOMSEPARATENUM
        defaultBookYstShouldNotBeFound("roomseparatenum.in=" + UPDATED_ROOMSEPARATENUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomseparatenumIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomseparatenum is not null
        defaultBookYstShouldBeFound("roomseparatenum.specified=true");

        // Get all the bookYstList where roomseparatenum is null
        defaultBookYstShouldNotBeFound("roomseparatenum.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomseparatenumContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomseparatenum contains DEFAULT_ROOMSEPARATENUM
        defaultBookYstShouldBeFound("roomseparatenum.contains=" + DEFAULT_ROOMSEPARATENUM);

        // Get all the bookYstList where roomseparatenum contains UPDATED_ROOMSEPARATENUM
        defaultBookYstShouldNotBeFound("roomseparatenum.contains=" + UPDATED_ROOMSEPARATENUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomseparatenumNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomseparatenum does not contain DEFAULT_ROOMSEPARATENUM
        defaultBookYstShouldNotBeFound("roomseparatenum.doesNotContain=" + DEFAULT_ROOMSEPARATENUM);

        // Get all the bookYstList where roomseparatenum does not contain UPDATED_ROOMSEPARATENUM
        defaultBookYstShouldBeFound("roomseparatenum.doesNotContain=" + UPDATED_ROOMSEPARATENUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByBedidsIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bedids equals to DEFAULT_BEDIDS
        defaultBookYstShouldBeFound("bedids.equals=" + DEFAULT_BEDIDS);

        // Get all the bookYstList where bedids equals to UPDATED_BEDIDS
        defaultBookYstShouldNotBeFound("bedids.equals=" + UPDATED_BEDIDS);
    }

    @Test
    @Transactional
    void getAllBookYstsByBedidsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bedids not equals to DEFAULT_BEDIDS
        defaultBookYstShouldNotBeFound("bedids.notEquals=" + DEFAULT_BEDIDS);

        // Get all the bookYstList where bedids not equals to UPDATED_BEDIDS
        defaultBookYstShouldBeFound("bedids.notEquals=" + UPDATED_BEDIDS);
    }

    @Test
    @Transactional
    void getAllBookYstsByBedidsIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bedids in DEFAULT_BEDIDS or UPDATED_BEDIDS
        defaultBookYstShouldBeFound("bedids.in=" + DEFAULT_BEDIDS + "," + UPDATED_BEDIDS);

        // Get all the bookYstList where bedids equals to UPDATED_BEDIDS
        defaultBookYstShouldNotBeFound("bedids.in=" + UPDATED_BEDIDS);
    }

    @Test
    @Transactional
    void getAllBookYstsByBedidsIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bedids is not null
        defaultBookYstShouldBeFound("bedids.specified=true");

        // Get all the bookYstList where bedids is null
        defaultBookYstShouldNotBeFound("bedids.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByBedidsContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bedids contains DEFAULT_BEDIDS
        defaultBookYstShouldBeFound("bedids.contains=" + DEFAULT_BEDIDS);

        // Get all the bookYstList where bedids contains UPDATED_BEDIDS
        defaultBookYstShouldNotBeFound("bedids.contains=" + UPDATED_BEDIDS);
    }

    @Test
    @Transactional
    void getAllBookYstsByBedidsNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bedids does not contain DEFAULT_BEDIDS
        defaultBookYstShouldNotBeFound("bedids.doesNotContain=" + DEFAULT_BEDIDS);

        // Get all the bookYstList where bedids does not contain UPDATED_BEDIDS
        defaultBookYstShouldBeFound("bedids.doesNotContain=" + UPDATED_BEDIDS);
    }

    @Test
    @Transactional
    void getAllBookYstsByBedsimpledescIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bedsimpledesc equals to DEFAULT_BEDSIMPLEDESC
        defaultBookYstShouldBeFound("bedsimpledesc.equals=" + DEFAULT_BEDSIMPLEDESC);

        // Get all the bookYstList where bedsimpledesc equals to UPDATED_BEDSIMPLEDESC
        defaultBookYstShouldNotBeFound("bedsimpledesc.equals=" + UPDATED_BEDSIMPLEDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByBedsimpledescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bedsimpledesc not equals to DEFAULT_BEDSIMPLEDESC
        defaultBookYstShouldNotBeFound("bedsimpledesc.notEquals=" + DEFAULT_BEDSIMPLEDESC);

        // Get all the bookYstList where bedsimpledesc not equals to UPDATED_BEDSIMPLEDESC
        defaultBookYstShouldBeFound("bedsimpledesc.notEquals=" + UPDATED_BEDSIMPLEDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByBedsimpledescIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bedsimpledesc in DEFAULT_BEDSIMPLEDESC or UPDATED_BEDSIMPLEDESC
        defaultBookYstShouldBeFound("bedsimpledesc.in=" + DEFAULT_BEDSIMPLEDESC + "," + UPDATED_BEDSIMPLEDESC);

        // Get all the bookYstList where bedsimpledesc equals to UPDATED_BEDSIMPLEDESC
        defaultBookYstShouldNotBeFound("bedsimpledesc.in=" + UPDATED_BEDSIMPLEDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByBedsimpledescIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bedsimpledesc is not null
        defaultBookYstShouldBeFound("bedsimpledesc.specified=true");

        // Get all the bookYstList where bedsimpledesc is null
        defaultBookYstShouldNotBeFound("bedsimpledesc.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByBedsimpledescContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bedsimpledesc contains DEFAULT_BEDSIMPLEDESC
        defaultBookYstShouldBeFound("bedsimpledesc.contains=" + DEFAULT_BEDSIMPLEDESC);

        // Get all the bookYstList where bedsimpledesc contains UPDATED_BEDSIMPLEDESC
        defaultBookYstShouldNotBeFound("bedsimpledesc.contains=" + UPDATED_BEDSIMPLEDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByBedsimpledescNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bedsimpledesc does not contain DEFAULT_BEDSIMPLEDESC
        defaultBookYstShouldNotBeFound("bedsimpledesc.doesNotContain=" + DEFAULT_BEDSIMPLEDESC);

        // Get all the bookYstList where bedsimpledesc does not contain UPDATED_BEDSIMPLEDESC
        defaultBookYstShouldBeFound("bedsimpledesc.doesNotContain=" + UPDATED_BEDSIMPLEDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByBednumIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bednum equals to DEFAULT_BEDNUM
        defaultBookYstShouldBeFound("bednum.equals=" + DEFAULT_BEDNUM);

        // Get all the bookYstList where bednum equals to UPDATED_BEDNUM
        defaultBookYstShouldNotBeFound("bednum.equals=" + UPDATED_BEDNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByBednumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bednum not equals to DEFAULT_BEDNUM
        defaultBookYstShouldNotBeFound("bednum.notEquals=" + DEFAULT_BEDNUM);

        // Get all the bookYstList where bednum not equals to UPDATED_BEDNUM
        defaultBookYstShouldBeFound("bednum.notEquals=" + UPDATED_BEDNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByBednumIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bednum in DEFAULT_BEDNUM or UPDATED_BEDNUM
        defaultBookYstShouldBeFound("bednum.in=" + DEFAULT_BEDNUM + "," + UPDATED_BEDNUM);

        // Get all the bookYstList where bednum equals to UPDATED_BEDNUM
        defaultBookYstShouldNotBeFound("bednum.in=" + UPDATED_BEDNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByBednumIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bednum is not null
        defaultBookYstShouldBeFound("bednum.specified=true");

        // Get all the bookYstList where bednum is null
        defaultBookYstShouldNotBeFound("bednum.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByBednumContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bednum contains DEFAULT_BEDNUM
        defaultBookYstShouldBeFound("bednum.contains=" + DEFAULT_BEDNUM);

        // Get all the bookYstList where bednum contains UPDATED_BEDNUM
        defaultBookYstShouldNotBeFound("bednum.contains=" + UPDATED_BEDNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByBednumNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where bednum does not contain DEFAULT_BEDNUM
        defaultBookYstShouldNotBeFound("bednum.doesNotContain=" + DEFAULT_BEDNUM);

        // Get all the bookYstList where bednum does not contain UPDATED_BEDNUM
        defaultBookYstShouldBeFound("bednum.doesNotContain=" + UPDATED_BEDNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomsizeIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomsize equals to DEFAULT_ROOMSIZE
        defaultBookYstShouldBeFound("roomsize.equals=" + DEFAULT_ROOMSIZE);

        // Get all the bookYstList where roomsize equals to UPDATED_ROOMSIZE
        defaultBookYstShouldNotBeFound("roomsize.equals=" + UPDATED_ROOMSIZE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomsizeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomsize not equals to DEFAULT_ROOMSIZE
        defaultBookYstShouldNotBeFound("roomsize.notEquals=" + DEFAULT_ROOMSIZE);

        // Get all the bookYstList where roomsize not equals to UPDATED_ROOMSIZE
        defaultBookYstShouldBeFound("roomsize.notEquals=" + UPDATED_ROOMSIZE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomsizeIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomsize in DEFAULT_ROOMSIZE or UPDATED_ROOMSIZE
        defaultBookYstShouldBeFound("roomsize.in=" + DEFAULT_ROOMSIZE + "," + UPDATED_ROOMSIZE);

        // Get all the bookYstList where roomsize equals to UPDATED_ROOMSIZE
        defaultBookYstShouldNotBeFound("roomsize.in=" + UPDATED_ROOMSIZE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomsizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomsize is not null
        defaultBookYstShouldBeFound("roomsize.specified=true");

        // Get all the bookYstList where roomsize is null
        defaultBookYstShouldNotBeFound("roomsize.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomsizeContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomsize contains DEFAULT_ROOMSIZE
        defaultBookYstShouldBeFound("roomsize.contains=" + DEFAULT_ROOMSIZE);

        // Get all the bookYstList where roomsize contains UPDATED_ROOMSIZE
        defaultBookYstShouldNotBeFound("roomsize.contains=" + UPDATED_ROOMSIZE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomsizeNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomsize does not contain DEFAULT_ROOMSIZE
        defaultBookYstShouldNotBeFound("roomsize.doesNotContain=" + DEFAULT_ROOMSIZE);

        // Get all the bookYstList where roomsize does not contain UPDATED_ROOMSIZE
        defaultBookYstShouldBeFound("roomsize.doesNotContain=" + UPDATED_ROOMSIZE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomfloorIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomfloor equals to DEFAULT_ROOMFLOOR
        defaultBookYstShouldBeFound("roomfloor.equals=" + DEFAULT_ROOMFLOOR);

        // Get all the bookYstList where roomfloor equals to UPDATED_ROOMFLOOR
        defaultBookYstShouldNotBeFound("roomfloor.equals=" + UPDATED_ROOMFLOOR);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomfloorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomfloor not equals to DEFAULT_ROOMFLOOR
        defaultBookYstShouldNotBeFound("roomfloor.notEquals=" + DEFAULT_ROOMFLOOR);

        // Get all the bookYstList where roomfloor not equals to UPDATED_ROOMFLOOR
        defaultBookYstShouldBeFound("roomfloor.notEquals=" + UPDATED_ROOMFLOOR);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomfloorIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomfloor in DEFAULT_ROOMFLOOR or UPDATED_ROOMFLOOR
        defaultBookYstShouldBeFound("roomfloor.in=" + DEFAULT_ROOMFLOOR + "," + UPDATED_ROOMFLOOR);

        // Get all the bookYstList where roomfloor equals to UPDATED_ROOMFLOOR
        defaultBookYstShouldNotBeFound("roomfloor.in=" + UPDATED_ROOMFLOOR);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomfloorIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomfloor is not null
        defaultBookYstShouldBeFound("roomfloor.specified=true");

        // Get all the bookYstList where roomfloor is null
        defaultBookYstShouldNotBeFound("roomfloor.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomfloorContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomfloor contains DEFAULT_ROOMFLOOR
        defaultBookYstShouldBeFound("roomfloor.contains=" + DEFAULT_ROOMFLOOR);

        // Get all the bookYstList where roomfloor contains UPDATED_ROOMFLOOR
        defaultBookYstShouldNotBeFound("roomfloor.contains=" + UPDATED_ROOMFLOOR);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomfloorNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomfloor does not contain DEFAULT_ROOMFLOOR
        defaultBookYstShouldNotBeFound("roomfloor.doesNotContain=" + DEFAULT_ROOMFLOOR);

        // Get all the bookYstList where roomfloor does not contain UPDATED_ROOMFLOOR
        defaultBookYstShouldBeFound("roomfloor.doesNotContain=" + UPDATED_ROOMFLOOR);
    }

    @Test
    @Transactional
    void getAllBookYstsByNetserviceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where netservice equals to DEFAULT_NETSERVICE
        defaultBookYstShouldBeFound("netservice.equals=" + DEFAULT_NETSERVICE);

        // Get all the bookYstList where netservice equals to UPDATED_NETSERVICE
        defaultBookYstShouldNotBeFound("netservice.equals=" + UPDATED_NETSERVICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByNetserviceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where netservice not equals to DEFAULT_NETSERVICE
        defaultBookYstShouldNotBeFound("netservice.notEquals=" + DEFAULT_NETSERVICE);

        // Get all the bookYstList where netservice not equals to UPDATED_NETSERVICE
        defaultBookYstShouldBeFound("netservice.notEquals=" + UPDATED_NETSERVICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByNetserviceIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where netservice in DEFAULT_NETSERVICE or UPDATED_NETSERVICE
        defaultBookYstShouldBeFound("netservice.in=" + DEFAULT_NETSERVICE + "," + UPDATED_NETSERVICE);

        // Get all the bookYstList where netservice equals to UPDATED_NETSERVICE
        defaultBookYstShouldNotBeFound("netservice.in=" + UPDATED_NETSERVICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByNetserviceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where netservice is not null
        defaultBookYstShouldBeFound("netservice.specified=true");

        // Get all the bookYstList where netservice is null
        defaultBookYstShouldNotBeFound("netservice.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByNetserviceContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where netservice contains DEFAULT_NETSERVICE
        defaultBookYstShouldBeFound("netservice.contains=" + DEFAULT_NETSERVICE);

        // Get all the bookYstList where netservice contains UPDATED_NETSERVICE
        defaultBookYstShouldNotBeFound("netservice.contains=" + UPDATED_NETSERVICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByNetserviceNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where netservice does not contain DEFAULT_NETSERVICE
        defaultBookYstShouldNotBeFound("netservice.doesNotContain=" + DEFAULT_NETSERVICE);

        // Get all the bookYstList where netservice does not contain UPDATED_NETSERVICE
        defaultBookYstShouldBeFound("netservice.doesNotContain=" + UPDATED_NETSERVICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByNettypeIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where nettype equals to DEFAULT_NETTYPE
        defaultBookYstShouldBeFound("nettype.equals=" + DEFAULT_NETTYPE);

        // Get all the bookYstList where nettype equals to UPDATED_NETTYPE
        defaultBookYstShouldNotBeFound("nettype.equals=" + UPDATED_NETTYPE);
    }

    @Test
    @Transactional
    void getAllBookYstsByNettypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where nettype not equals to DEFAULT_NETTYPE
        defaultBookYstShouldNotBeFound("nettype.notEquals=" + DEFAULT_NETTYPE);

        // Get all the bookYstList where nettype not equals to UPDATED_NETTYPE
        defaultBookYstShouldBeFound("nettype.notEquals=" + UPDATED_NETTYPE);
    }

    @Test
    @Transactional
    void getAllBookYstsByNettypeIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where nettype in DEFAULT_NETTYPE or UPDATED_NETTYPE
        defaultBookYstShouldBeFound("nettype.in=" + DEFAULT_NETTYPE + "," + UPDATED_NETTYPE);

        // Get all the bookYstList where nettype equals to UPDATED_NETTYPE
        defaultBookYstShouldNotBeFound("nettype.in=" + UPDATED_NETTYPE);
    }

    @Test
    @Transactional
    void getAllBookYstsByNettypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where nettype is not null
        defaultBookYstShouldBeFound("nettype.specified=true");

        // Get all the bookYstList where nettype is null
        defaultBookYstShouldNotBeFound("nettype.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByNettypeContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where nettype contains DEFAULT_NETTYPE
        defaultBookYstShouldBeFound("nettype.contains=" + DEFAULT_NETTYPE);

        // Get all the bookYstList where nettype contains UPDATED_NETTYPE
        defaultBookYstShouldNotBeFound("nettype.contains=" + UPDATED_NETTYPE);
    }

    @Test
    @Transactional
    void getAllBookYstsByNettypeNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where nettype does not contain DEFAULT_NETTYPE
        defaultBookYstShouldNotBeFound("nettype.doesNotContain=" + DEFAULT_NETTYPE);

        // Get all the bookYstList where nettype does not contain UPDATED_NETTYPE
        defaultBookYstShouldBeFound("nettype.doesNotContain=" + UPDATED_NETTYPE);
    }

    @Test
    @Transactional
    void getAllBookYstsByIswindowIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where iswindow equals to DEFAULT_ISWINDOW
        defaultBookYstShouldBeFound("iswindow.equals=" + DEFAULT_ISWINDOW);

        // Get all the bookYstList where iswindow equals to UPDATED_ISWINDOW
        defaultBookYstShouldNotBeFound("iswindow.equals=" + UPDATED_ISWINDOW);
    }

    @Test
    @Transactional
    void getAllBookYstsByIswindowIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where iswindow not equals to DEFAULT_ISWINDOW
        defaultBookYstShouldNotBeFound("iswindow.notEquals=" + DEFAULT_ISWINDOW);

        // Get all the bookYstList where iswindow not equals to UPDATED_ISWINDOW
        defaultBookYstShouldBeFound("iswindow.notEquals=" + UPDATED_ISWINDOW);
    }

    @Test
    @Transactional
    void getAllBookYstsByIswindowIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where iswindow in DEFAULT_ISWINDOW or UPDATED_ISWINDOW
        defaultBookYstShouldBeFound("iswindow.in=" + DEFAULT_ISWINDOW + "," + UPDATED_ISWINDOW);

        // Get all the bookYstList where iswindow equals to UPDATED_ISWINDOW
        defaultBookYstShouldNotBeFound("iswindow.in=" + UPDATED_ISWINDOW);
    }

    @Test
    @Transactional
    void getAllBookYstsByIswindowIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where iswindow is not null
        defaultBookYstShouldBeFound("iswindow.specified=true");

        // Get all the bookYstList where iswindow is null
        defaultBookYstShouldNotBeFound("iswindow.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByIswindowContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where iswindow contains DEFAULT_ISWINDOW
        defaultBookYstShouldBeFound("iswindow.contains=" + DEFAULT_ISWINDOW);

        // Get all the bookYstList where iswindow contains UPDATED_ISWINDOW
        defaultBookYstShouldNotBeFound("iswindow.contains=" + UPDATED_ISWINDOW);
    }

    @Test
    @Transactional
    void getAllBookYstsByIswindowNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where iswindow does not contain DEFAULT_ISWINDOW
        defaultBookYstShouldNotBeFound("iswindow.doesNotContain=" + DEFAULT_ISWINDOW);

        // Get all the bookYstList where iswindow does not contain UPDATED_ISWINDOW
        defaultBookYstShouldBeFound("iswindow.doesNotContain=" + UPDATED_ISWINDOW);
    }

    @Test
    @Transactional
    void getAllBookYstsByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where remark equals to DEFAULT_REMARK
        defaultBookYstShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the bookYstList where remark equals to UPDATED_REMARK
        defaultBookYstShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllBookYstsByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where remark not equals to DEFAULT_REMARK
        defaultBookYstShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the bookYstList where remark not equals to UPDATED_REMARK
        defaultBookYstShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllBookYstsByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultBookYstShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the bookYstList where remark equals to UPDATED_REMARK
        defaultBookYstShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllBookYstsByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where remark is not null
        defaultBookYstShouldBeFound("remark.specified=true");

        // Get all the bookYstList where remark is null
        defaultBookYstShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByRemarkContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where remark contains DEFAULT_REMARK
        defaultBookYstShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the bookYstList where remark contains UPDATED_REMARK
        defaultBookYstShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllBookYstsByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where remark does not contain DEFAULT_REMARK
        defaultBookYstShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the bookYstList where remark does not contain UPDATED_REMARK
        defaultBookYstShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllBookYstsBySortidIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where sortid equals to DEFAULT_SORTID
        defaultBookYstShouldBeFound("sortid.equals=" + DEFAULT_SORTID);

        // Get all the bookYstList where sortid equals to UPDATED_SORTID
        defaultBookYstShouldNotBeFound("sortid.equals=" + UPDATED_SORTID);
    }

    @Test
    @Transactional
    void getAllBookYstsBySortidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where sortid not equals to DEFAULT_SORTID
        defaultBookYstShouldNotBeFound("sortid.notEquals=" + DEFAULT_SORTID);

        // Get all the bookYstList where sortid not equals to UPDATED_SORTID
        defaultBookYstShouldBeFound("sortid.notEquals=" + UPDATED_SORTID);
    }

    @Test
    @Transactional
    void getAllBookYstsBySortidIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where sortid in DEFAULT_SORTID or UPDATED_SORTID
        defaultBookYstShouldBeFound("sortid.in=" + DEFAULT_SORTID + "," + UPDATED_SORTID);

        // Get all the bookYstList where sortid equals to UPDATED_SORTID
        defaultBookYstShouldNotBeFound("sortid.in=" + UPDATED_SORTID);
    }

    @Test
    @Transactional
    void getAllBookYstsBySortidIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where sortid is not null
        defaultBookYstShouldBeFound("sortid.specified=true");

        // Get all the bookYstList where sortid is null
        defaultBookYstShouldNotBeFound("sortid.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsBySortidContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where sortid contains DEFAULT_SORTID
        defaultBookYstShouldBeFound("sortid.contains=" + DEFAULT_SORTID);

        // Get all the bookYstList where sortid contains UPDATED_SORTID
        defaultBookYstShouldNotBeFound("sortid.contains=" + UPDATED_SORTID);
    }

    @Test
    @Transactional
    void getAllBookYstsBySortidNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where sortid does not contain DEFAULT_SORTID
        defaultBookYstShouldNotBeFound("sortid.doesNotContain=" + DEFAULT_SORTID);

        // Get all the bookYstList where sortid does not contain UPDATED_SORTID
        defaultBookYstShouldBeFound("sortid.doesNotContain=" + UPDATED_SORTID);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomstateIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomstate equals to DEFAULT_ROOMSTATE
        defaultBookYstShouldBeFound("roomstate.equals=" + DEFAULT_ROOMSTATE);

        // Get all the bookYstList where roomstate equals to UPDATED_ROOMSTATE
        defaultBookYstShouldNotBeFound("roomstate.equals=" + UPDATED_ROOMSTATE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomstateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomstate not equals to DEFAULT_ROOMSTATE
        defaultBookYstShouldNotBeFound("roomstate.notEquals=" + DEFAULT_ROOMSTATE);

        // Get all the bookYstList where roomstate not equals to UPDATED_ROOMSTATE
        defaultBookYstShouldBeFound("roomstate.notEquals=" + UPDATED_ROOMSTATE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomstateIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomstate in DEFAULT_ROOMSTATE or UPDATED_ROOMSTATE
        defaultBookYstShouldBeFound("roomstate.in=" + DEFAULT_ROOMSTATE + "," + UPDATED_ROOMSTATE);

        // Get all the bookYstList where roomstate equals to UPDATED_ROOMSTATE
        defaultBookYstShouldNotBeFound("roomstate.in=" + UPDATED_ROOMSTATE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomstateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomstate is not null
        defaultBookYstShouldBeFound("roomstate.specified=true");

        // Get all the bookYstList where roomstate is null
        defaultBookYstShouldNotBeFound("roomstate.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomstateContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomstate contains DEFAULT_ROOMSTATE
        defaultBookYstShouldBeFound("roomstate.contains=" + DEFAULT_ROOMSTATE);

        // Get all the bookYstList where roomstate contains UPDATED_ROOMSTATE
        defaultBookYstShouldNotBeFound("roomstate.contains=" + UPDATED_ROOMSTATE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomstateNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomstate does not contain DEFAULT_ROOMSTATE
        defaultBookYstShouldNotBeFound("roomstate.doesNotContain=" + DEFAULT_ROOMSTATE);

        // Get all the bookYstList where roomstate does not contain UPDATED_ROOMSTATE
        defaultBookYstShouldBeFound("roomstate.doesNotContain=" + UPDATED_ROOMSTATE);
    }

    @Test
    @Transactional
    void getAllBookYstsBySourceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where source equals to DEFAULT_SOURCE
        defaultBookYstShouldBeFound("source.equals=" + DEFAULT_SOURCE);

        // Get all the bookYstList where source equals to UPDATED_SOURCE
        defaultBookYstShouldNotBeFound("source.equals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllBookYstsBySourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where source not equals to DEFAULT_SOURCE
        defaultBookYstShouldNotBeFound("source.notEquals=" + DEFAULT_SOURCE);

        // Get all the bookYstList where source not equals to UPDATED_SOURCE
        defaultBookYstShouldBeFound("source.notEquals=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllBookYstsBySourceIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where source in DEFAULT_SOURCE or UPDATED_SOURCE
        defaultBookYstShouldBeFound("source.in=" + DEFAULT_SOURCE + "," + UPDATED_SOURCE);

        // Get all the bookYstList where source equals to UPDATED_SOURCE
        defaultBookYstShouldNotBeFound("source.in=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllBookYstsBySourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where source is not null
        defaultBookYstShouldBeFound("source.specified=true");

        // Get all the bookYstList where source is null
        defaultBookYstShouldNotBeFound("source.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsBySourceContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where source contains DEFAULT_SOURCE
        defaultBookYstShouldBeFound("source.contains=" + DEFAULT_SOURCE);

        // Get all the bookYstList where source contains UPDATED_SOURCE
        defaultBookYstShouldNotBeFound("source.contains=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllBookYstsBySourceNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where source does not contain DEFAULT_SOURCE
        defaultBookYstShouldNotBeFound("source.doesNotContain=" + DEFAULT_SOURCE);

        // Get all the bookYstList where source does not contain UPDATED_SOURCE
        defaultBookYstShouldBeFound("source.doesNotContain=" + UPDATED_SOURCE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomamenitiesIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomamenities equals to DEFAULT_ROOMAMENITIES
        defaultBookYstShouldBeFound("roomamenities.equals=" + DEFAULT_ROOMAMENITIES);

        // Get all the bookYstList where roomamenities equals to UPDATED_ROOMAMENITIES
        defaultBookYstShouldNotBeFound("roomamenities.equals=" + UPDATED_ROOMAMENITIES);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomamenitiesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomamenities not equals to DEFAULT_ROOMAMENITIES
        defaultBookYstShouldNotBeFound("roomamenities.notEquals=" + DEFAULT_ROOMAMENITIES);

        // Get all the bookYstList where roomamenities not equals to UPDATED_ROOMAMENITIES
        defaultBookYstShouldBeFound("roomamenities.notEquals=" + UPDATED_ROOMAMENITIES);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomamenitiesIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomamenities in DEFAULT_ROOMAMENITIES or UPDATED_ROOMAMENITIES
        defaultBookYstShouldBeFound("roomamenities.in=" + DEFAULT_ROOMAMENITIES + "," + UPDATED_ROOMAMENITIES);

        // Get all the bookYstList where roomamenities equals to UPDATED_ROOMAMENITIES
        defaultBookYstShouldNotBeFound("roomamenities.in=" + UPDATED_ROOMAMENITIES);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomamenitiesIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomamenities is not null
        defaultBookYstShouldBeFound("roomamenities.specified=true");

        // Get all the bookYstList where roomamenities is null
        defaultBookYstShouldNotBeFound("roomamenities.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomamenitiesContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomamenities contains DEFAULT_ROOMAMENITIES
        defaultBookYstShouldBeFound("roomamenities.contains=" + DEFAULT_ROOMAMENITIES);

        // Get all the bookYstList where roomamenities contains UPDATED_ROOMAMENITIES
        defaultBookYstShouldNotBeFound("roomamenities.contains=" + UPDATED_ROOMAMENITIES);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomamenitiesNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomamenities does not contain DEFAULT_ROOMAMENITIES
        defaultBookYstShouldNotBeFound("roomamenities.doesNotContain=" + DEFAULT_ROOMAMENITIES);

        // Get all the bookYstList where roomamenities does not contain UPDATED_ROOMAMENITIES
        defaultBookYstShouldBeFound("roomamenities.doesNotContain=" + UPDATED_ROOMAMENITIES);
    }

    @Test
    @Transactional
    void getAllBookYstsByMaxguestnumsIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where maxguestnums equals to DEFAULT_MAXGUESTNUMS
        defaultBookYstShouldBeFound("maxguestnums.equals=" + DEFAULT_MAXGUESTNUMS);

        // Get all the bookYstList where maxguestnums equals to UPDATED_MAXGUESTNUMS
        defaultBookYstShouldNotBeFound("maxguestnums.equals=" + UPDATED_MAXGUESTNUMS);
    }

    @Test
    @Transactional
    void getAllBookYstsByMaxguestnumsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where maxguestnums not equals to DEFAULT_MAXGUESTNUMS
        defaultBookYstShouldNotBeFound("maxguestnums.notEquals=" + DEFAULT_MAXGUESTNUMS);

        // Get all the bookYstList where maxguestnums not equals to UPDATED_MAXGUESTNUMS
        defaultBookYstShouldBeFound("maxguestnums.notEquals=" + UPDATED_MAXGUESTNUMS);
    }

    @Test
    @Transactional
    void getAllBookYstsByMaxguestnumsIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where maxguestnums in DEFAULT_MAXGUESTNUMS or UPDATED_MAXGUESTNUMS
        defaultBookYstShouldBeFound("maxguestnums.in=" + DEFAULT_MAXGUESTNUMS + "," + UPDATED_MAXGUESTNUMS);

        // Get all the bookYstList where maxguestnums equals to UPDATED_MAXGUESTNUMS
        defaultBookYstShouldNotBeFound("maxguestnums.in=" + UPDATED_MAXGUESTNUMS);
    }

    @Test
    @Transactional
    void getAllBookYstsByMaxguestnumsIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where maxguestnums is not null
        defaultBookYstShouldBeFound("maxguestnums.specified=true");

        // Get all the bookYstList where maxguestnums is null
        defaultBookYstShouldNotBeFound("maxguestnums.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByMaxguestnumsContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where maxguestnums contains DEFAULT_MAXGUESTNUMS
        defaultBookYstShouldBeFound("maxguestnums.contains=" + DEFAULT_MAXGUESTNUMS);

        // Get all the bookYstList where maxguestnums contains UPDATED_MAXGUESTNUMS
        defaultBookYstShouldNotBeFound("maxguestnums.contains=" + UPDATED_MAXGUESTNUMS);
    }

    @Test
    @Transactional
    void getAllBookYstsByMaxguestnumsNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where maxguestnums does not contain DEFAULT_MAXGUESTNUMS
        defaultBookYstShouldNotBeFound("maxguestnums.doesNotContain=" + DEFAULT_MAXGUESTNUMS);

        // Get all the bookYstList where maxguestnums does not contain UPDATED_MAXGUESTNUMS
        defaultBookYstShouldBeFound("maxguestnums.doesNotContain=" + UPDATED_MAXGUESTNUMS);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomdistributionIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomdistribution equals to DEFAULT_ROOMDISTRIBUTION
        defaultBookYstShouldBeFound("roomdistribution.equals=" + DEFAULT_ROOMDISTRIBUTION);

        // Get all the bookYstList where roomdistribution equals to UPDATED_ROOMDISTRIBUTION
        defaultBookYstShouldNotBeFound("roomdistribution.equals=" + UPDATED_ROOMDISTRIBUTION);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomdistributionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomdistribution not equals to DEFAULT_ROOMDISTRIBUTION
        defaultBookYstShouldNotBeFound("roomdistribution.notEquals=" + DEFAULT_ROOMDISTRIBUTION);

        // Get all the bookYstList where roomdistribution not equals to UPDATED_ROOMDISTRIBUTION
        defaultBookYstShouldBeFound("roomdistribution.notEquals=" + UPDATED_ROOMDISTRIBUTION);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomdistributionIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomdistribution in DEFAULT_ROOMDISTRIBUTION or UPDATED_ROOMDISTRIBUTION
        defaultBookYstShouldBeFound("roomdistribution.in=" + DEFAULT_ROOMDISTRIBUTION + "," + UPDATED_ROOMDISTRIBUTION);

        // Get all the bookYstList where roomdistribution equals to UPDATED_ROOMDISTRIBUTION
        defaultBookYstShouldNotBeFound("roomdistribution.in=" + UPDATED_ROOMDISTRIBUTION);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomdistributionIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomdistribution is not null
        defaultBookYstShouldBeFound("roomdistribution.specified=true");

        // Get all the bookYstList where roomdistribution is null
        defaultBookYstShouldNotBeFound("roomdistribution.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomdistributionContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomdistribution contains DEFAULT_ROOMDISTRIBUTION
        defaultBookYstShouldBeFound("roomdistribution.contains=" + DEFAULT_ROOMDISTRIBUTION);

        // Get all the bookYstList where roomdistribution contains UPDATED_ROOMDISTRIBUTION
        defaultBookYstShouldNotBeFound("roomdistribution.contains=" + UPDATED_ROOMDISTRIBUTION);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomdistributionNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomdistribution does not contain DEFAULT_ROOMDISTRIBUTION
        defaultBookYstShouldNotBeFound("roomdistribution.doesNotContain=" + DEFAULT_ROOMDISTRIBUTION);

        // Get all the bookYstList where roomdistribution does not contain UPDATED_ROOMDISTRIBUTION
        defaultBookYstShouldBeFound("roomdistribution.doesNotContain=" + UPDATED_ROOMDISTRIBUTION);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionbeforedaysIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionbeforedays equals to DEFAULT_CONDITIONBEFOREDAYS
        defaultBookYstShouldBeFound("conditionbeforedays.equals=" + DEFAULT_CONDITIONBEFOREDAYS);

        // Get all the bookYstList where conditionbeforedays equals to UPDATED_CONDITIONBEFOREDAYS
        defaultBookYstShouldNotBeFound("conditionbeforedays.equals=" + UPDATED_CONDITIONBEFOREDAYS);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionbeforedaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionbeforedays not equals to DEFAULT_CONDITIONBEFOREDAYS
        defaultBookYstShouldNotBeFound("conditionbeforedays.notEquals=" + DEFAULT_CONDITIONBEFOREDAYS);

        // Get all the bookYstList where conditionbeforedays not equals to UPDATED_CONDITIONBEFOREDAYS
        defaultBookYstShouldBeFound("conditionbeforedays.notEquals=" + UPDATED_CONDITIONBEFOREDAYS);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionbeforedaysIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionbeforedays in DEFAULT_CONDITIONBEFOREDAYS or UPDATED_CONDITIONBEFOREDAYS
        defaultBookYstShouldBeFound("conditionbeforedays.in=" + DEFAULT_CONDITIONBEFOREDAYS + "," + UPDATED_CONDITIONBEFOREDAYS);

        // Get all the bookYstList where conditionbeforedays equals to UPDATED_CONDITIONBEFOREDAYS
        defaultBookYstShouldNotBeFound("conditionbeforedays.in=" + UPDATED_CONDITIONBEFOREDAYS);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionbeforedaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionbeforedays is not null
        defaultBookYstShouldBeFound("conditionbeforedays.specified=true");

        // Get all the bookYstList where conditionbeforedays is null
        defaultBookYstShouldNotBeFound("conditionbeforedays.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionbeforedaysContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionbeforedays contains DEFAULT_CONDITIONBEFOREDAYS
        defaultBookYstShouldBeFound("conditionbeforedays.contains=" + DEFAULT_CONDITIONBEFOREDAYS);

        // Get all the bookYstList where conditionbeforedays contains UPDATED_CONDITIONBEFOREDAYS
        defaultBookYstShouldNotBeFound("conditionbeforedays.contains=" + UPDATED_CONDITIONBEFOREDAYS);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionbeforedaysNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionbeforedays does not contain DEFAULT_CONDITIONBEFOREDAYS
        defaultBookYstShouldNotBeFound("conditionbeforedays.doesNotContain=" + DEFAULT_CONDITIONBEFOREDAYS);

        // Get all the bookYstList where conditionbeforedays does not contain UPDATED_CONDITIONBEFOREDAYS
        defaultBookYstShouldBeFound("conditionbeforedays.doesNotContain=" + UPDATED_CONDITIONBEFOREDAYS);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionleastdaysIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionleastdays equals to DEFAULT_CONDITIONLEASTDAYS
        defaultBookYstShouldBeFound("conditionleastdays.equals=" + DEFAULT_CONDITIONLEASTDAYS);

        // Get all the bookYstList where conditionleastdays equals to UPDATED_CONDITIONLEASTDAYS
        defaultBookYstShouldNotBeFound("conditionleastdays.equals=" + UPDATED_CONDITIONLEASTDAYS);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionleastdaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionleastdays not equals to DEFAULT_CONDITIONLEASTDAYS
        defaultBookYstShouldNotBeFound("conditionleastdays.notEquals=" + DEFAULT_CONDITIONLEASTDAYS);

        // Get all the bookYstList where conditionleastdays not equals to UPDATED_CONDITIONLEASTDAYS
        defaultBookYstShouldBeFound("conditionleastdays.notEquals=" + UPDATED_CONDITIONLEASTDAYS);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionleastdaysIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionleastdays in DEFAULT_CONDITIONLEASTDAYS or UPDATED_CONDITIONLEASTDAYS
        defaultBookYstShouldBeFound("conditionleastdays.in=" + DEFAULT_CONDITIONLEASTDAYS + "," + UPDATED_CONDITIONLEASTDAYS);

        // Get all the bookYstList where conditionleastdays equals to UPDATED_CONDITIONLEASTDAYS
        defaultBookYstShouldNotBeFound("conditionleastdays.in=" + UPDATED_CONDITIONLEASTDAYS);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionleastdaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionleastdays is not null
        defaultBookYstShouldBeFound("conditionleastdays.specified=true");

        // Get all the bookYstList where conditionleastdays is null
        defaultBookYstShouldNotBeFound("conditionleastdays.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionleastdaysContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionleastdays contains DEFAULT_CONDITIONLEASTDAYS
        defaultBookYstShouldBeFound("conditionleastdays.contains=" + DEFAULT_CONDITIONLEASTDAYS);

        // Get all the bookYstList where conditionleastdays contains UPDATED_CONDITIONLEASTDAYS
        defaultBookYstShouldNotBeFound("conditionleastdays.contains=" + UPDATED_CONDITIONLEASTDAYS);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionleastdaysNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionleastdays does not contain DEFAULT_CONDITIONLEASTDAYS
        defaultBookYstShouldNotBeFound("conditionleastdays.doesNotContain=" + DEFAULT_CONDITIONLEASTDAYS);

        // Get all the bookYstList where conditionleastdays does not contain UPDATED_CONDITIONLEASTDAYS
        defaultBookYstShouldBeFound("conditionleastdays.doesNotContain=" + UPDATED_CONDITIONLEASTDAYS);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionleastroomnumIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionleastroomnum equals to DEFAULT_CONDITIONLEASTROOMNUM
        defaultBookYstShouldBeFound("conditionleastroomnum.equals=" + DEFAULT_CONDITIONLEASTROOMNUM);

        // Get all the bookYstList where conditionleastroomnum equals to UPDATED_CONDITIONLEASTROOMNUM
        defaultBookYstShouldNotBeFound("conditionleastroomnum.equals=" + UPDATED_CONDITIONLEASTROOMNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionleastroomnumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionleastroomnum not equals to DEFAULT_CONDITIONLEASTROOMNUM
        defaultBookYstShouldNotBeFound("conditionleastroomnum.notEquals=" + DEFAULT_CONDITIONLEASTROOMNUM);

        // Get all the bookYstList where conditionleastroomnum not equals to UPDATED_CONDITIONLEASTROOMNUM
        defaultBookYstShouldBeFound("conditionleastroomnum.notEquals=" + UPDATED_CONDITIONLEASTROOMNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionleastroomnumIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionleastroomnum in DEFAULT_CONDITIONLEASTROOMNUM or UPDATED_CONDITIONLEASTROOMNUM
        defaultBookYstShouldBeFound("conditionleastroomnum.in=" + DEFAULT_CONDITIONLEASTROOMNUM + "," + UPDATED_CONDITIONLEASTROOMNUM);

        // Get all the bookYstList where conditionleastroomnum equals to UPDATED_CONDITIONLEASTROOMNUM
        defaultBookYstShouldNotBeFound("conditionleastroomnum.in=" + UPDATED_CONDITIONLEASTROOMNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionleastroomnumIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionleastroomnum is not null
        defaultBookYstShouldBeFound("conditionleastroomnum.specified=true");

        // Get all the bookYstList where conditionleastroomnum is null
        defaultBookYstShouldNotBeFound("conditionleastroomnum.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionleastroomnumContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionleastroomnum contains DEFAULT_CONDITIONLEASTROOMNUM
        defaultBookYstShouldBeFound("conditionleastroomnum.contains=" + DEFAULT_CONDITIONLEASTROOMNUM);

        // Get all the bookYstList where conditionleastroomnum contains UPDATED_CONDITIONLEASTROOMNUM
        defaultBookYstShouldNotBeFound("conditionleastroomnum.contains=" + UPDATED_CONDITIONLEASTROOMNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByConditionleastroomnumNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where conditionleastroomnum does not contain DEFAULT_CONDITIONLEASTROOMNUM
        defaultBookYstShouldNotBeFound("conditionleastroomnum.doesNotContain=" + DEFAULT_CONDITIONLEASTROOMNUM);

        // Get all the bookYstList where conditionleastroomnum does not contain UPDATED_CONDITIONLEASTROOMNUM
        defaultBookYstShouldBeFound("conditionleastroomnum.doesNotContain=" + UPDATED_CONDITIONLEASTROOMNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByPaymentypeIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where paymentype equals to DEFAULT_PAYMENTYPE
        defaultBookYstShouldBeFound("paymentype.equals=" + DEFAULT_PAYMENTYPE);

        // Get all the bookYstList where paymentype equals to UPDATED_PAYMENTYPE
        defaultBookYstShouldNotBeFound("paymentype.equals=" + UPDATED_PAYMENTYPE);
    }

    @Test
    @Transactional
    void getAllBookYstsByPaymentypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where paymentype not equals to DEFAULT_PAYMENTYPE
        defaultBookYstShouldNotBeFound("paymentype.notEquals=" + DEFAULT_PAYMENTYPE);

        // Get all the bookYstList where paymentype not equals to UPDATED_PAYMENTYPE
        defaultBookYstShouldBeFound("paymentype.notEquals=" + UPDATED_PAYMENTYPE);
    }

    @Test
    @Transactional
    void getAllBookYstsByPaymentypeIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where paymentype in DEFAULT_PAYMENTYPE or UPDATED_PAYMENTYPE
        defaultBookYstShouldBeFound("paymentype.in=" + DEFAULT_PAYMENTYPE + "," + UPDATED_PAYMENTYPE);

        // Get all the bookYstList where paymentype equals to UPDATED_PAYMENTYPE
        defaultBookYstShouldNotBeFound("paymentype.in=" + UPDATED_PAYMENTYPE);
    }

    @Test
    @Transactional
    void getAllBookYstsByPaymentypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where paymentype is not null
        defaultBookYstShouldBeFound("paymentype.specified=true");

        // Get all the bookYstList where paymentype is null
        defaultBookYstShouldNotBeFound("paymentype.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByPaymentypeContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where paymentype contains DEFAULT_PAYMENTYPE
        defaultBookYstShouldBeFound("paymentype.contains=" + DEFAULT_PAYMENTYPE);

        // Get all the bookYstList where paymentype contains UPDATED_PAYMENTYPE
        defaultBookYstShouldNotBeFound("paymentype.contains=" + UPDATED_PAYMENTYPE);
    }

    @Test
    @Transactional
    void getAllBookYstsByPaymentypeNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where paymentype does not contain DEFAULT_PAYMENTYPE
        defaultBookYstShouldNotBeFound("paymentype.doesNotContain=" + DEFAULT_PAYMENTYPE);

        // Get all the bookYstList where paymentype does not contain UPDATED_PAYMENTYPE
        defaultBookYstShouldBeFound("paymentype.doesNotContain=" + UPDATED_PAYMENTYPE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplandescIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplandesc equals to DEFAULT_RATEPLANDESC
        defaultBookYstShouldBeFound("rateplandesc.equals=" + DEFAULT_RATEPLANDESC);

        // Get all the bookYstList where rateplandesc equals to UPDATED_RATEPLANDESC
        defaultBookYstShouldNotBeFound("rateplandesc.equals=" + UPDATED_RATEPLANDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplandescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplandesc not equals to DEFAULT_RATEPLANDESC
        defaultBookYstShouldNotBeFound("rateplandesc.notEquals=" + DEFAULT_RATEPLANDESC);

        // Get all the bookYstList where rateplandesc not equals to UPDATED_RATEPLANDESC
        defaultBookYstShouldBeFound("rateplandesc.notEquals=" + UPDATED_RATEPLANDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplandescIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplandesc in DEFAULT_RATEPLANDESC or UPDATED_RATEPLANDESC
        defaultBookYstShouldBeFound("rateplandesc.in=" + DEFAULT_RATEPLANDESC + "," + UPDATED_RATEPLANDESC);

        // Get all the bookYstList where rateplandesc equals to UPDATED_RATEPLANDESC
        defaultBookYstShouldNotBeFound("rateplandesc.in=" + UPDATED_RATEPLANDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplandescIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplandesc is not null
        defaultBookYstShouldBeFound("rateplandesc.specified=true");

        // Get all the bookYstList where rateplandesc is null
        defaultBookYstShouldNotBeFound("rateplandesc.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplandescContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplandesc contains DEFAULT_RATEPLANDESC
        defaultBookYstShouldBeFound("rateplandesc.contains=" + DEFAULT_RATEPLANDESC);

        // Get all the bookYstList where rateplandesc contains UPDATED_RATEPLANDESC
        defaultBookYstShouldNotBeFound("rateplandesc.contains=" + UPDATED_RATEPLANDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplandescNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplandesc does not contain DEFAULT_RATEPLANDESC
        defaultBookYstShouldNotBeFound("rateplandesc.doesNotContain=" + DEFAULT_RATEPLANDESC);

        // Get all the bookYstList where rateplandesc does not contain UPDATED_RATEPLANDESC
        defaultBookYstShouldBeFound("rateplandesc.doesNotContain=" + UPDATED_RATEPLANDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplannameIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplanname equals to DEFAULT_RATEPLANNAME
        defaultBookYstShouldBeFound("rateplanname.equals=" + DEFAULT_RATEPLANNAME);

        // Get all the bookYstList where rateplanname equals to UPDATED_RATEPLANNAME
        defaultBookYstShouldNotBeFound("rateplanname.equals=" + UPDATED_RATEPLANNAME);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplannameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplanname not equals to DEFAULT_RATEPLANNAME
        defaultBookYstShouldNotBeFound("rateplanname.notEquals=" + DEFAULT_RATEPLANNAME);

        // Get all the bookYstList where rateplanname not equals to UPDATED_RATEPLANNAME
        defaultBookYstShouldBeFound("rateplanname.notEquals=" + UPDATED_RATEPLANNAME);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplannameIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplanname in DEFAULT_RATEPLANNAME or UPDATED_RATEPLANNAME
        defaultBookYstShouldBeFound("rateplanname.in=" + DEFAULT_RATEPLANNAME + "," + UPDATED_RATEPLANNAME);

        // Get all the bookYstList where rateplanname equals to UPDATED_RATEPLANNAME
        defaultBookYstShouldNotBeFound("rateplanname.in=" + UPDATED_RATEPLANNAME);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplannameIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplanname is not null
        defaultBookYstShouldBeFound("rateplanname.specified=true");

        // Get all the bookYstList where rateplanname is null
        defaultBookYstShouldNotBeFound("rateplanname.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplannameContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplanname contains DEFAULT_RATEPLANNAME
        defaultBookYstShouldBeFound("rateplanname.contains=" + DEFAULT_RATEPLANNAME);

        // Get all the bookYstList where rateplanname contains UPDATED_RATEPLANNAME
        defaultBookYstShouldNotBeFound("rateplanname.contains=" + UPDATED_RATEPLANNAME);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplannameNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplanname does not contain DEFAULT_RATEPLANNAME
        defaultBookYstShouldNotBeFound("rateplanname.doesNotContain=" + DEFAULT_RATEPLANNAME);

        // Get all the bookYstList where rateplanname does not contain UPDATED_RATEPLANNAME
        defaultBookYstShouldBeFound("rateplanname.doesNotContain=" + UPDATED_RATEPLANNAME);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplanstateIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplanstate equals to DEFAULT_RATEPLANSTATE
        defaultBookYstShouldBeFound("rateplanstate.equals=" + DEFAULT_RATEPLANSTATE);

        // Get all the bookYstList where rateplanstate equals to UPDATED_RATEPLANSTATE
        defaultBookYstShouldNotBeFound("rateplanstate.equals=" + UPDATED_RATEPLANSTATE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplanstateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplanstate not equals to DEFAULT_RATEPLANSTATE
        defaultBookYstShouldNotBeFound("rateplanstate.notEquals=" + DEFAULT_RATEPLANSTATE);

        // Get all the bookYstList where rateplanstate not equals to UPDATED_RATEPLANSTATE
        defaultBookYstShouldBeFound("rateplanstate.notEquals=" + UPDATED_RATEPLANSTATE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplanstateIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplanstate in DEFAULT_RATEPLANSTATE or UPDATED_RATEPLANSTATE
        defaultBookYstShouldBeFound("rateplanstate.in=" + DEFAULT_RATEPLANSTATE + "," + UPDATED_RATEPLANSTATE);

        // Get all the bookYstList where rateplanstate equals to UPDATED_RATEPLANSTATE
        defaultBookYstShouldNotBeFound("rateplanstate.in=" + UPDATED_RATEPLANSTATE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplanstateIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplanstate is not null
        defaultBookYstShouldBeFound("rateplanstate.specified=true");

        // Get all the bookYstList where rateplanstate is null
        defaultBookYstShouldNotBeFound("rateplanstate.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplanstateContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplanstate contains DEFAULT_RATEPLANSTATE
        defaultBookYstShouldBeFound("rateplanstate.contains=" + DEFAULT_RATEPLANSTATE);

        // Get all the bookYstList where rateplanstate contains UPDATED_RATEPLANSTATE
        defaultBookYstShouldNotBeFound("rateplanstate.contains=" + UPDATED_RATEPLANSTATE);
    }

    @Test
    @Transactional
    void getAllBookYstsByRateplanstateNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where rateplanstate does not contain DEFAULT_RATEPLANSTATE
        defaultBookYstShouldNotBeFound("rateplanstate.doesNotContain=" + DEFAULT_RATEPLANSTATE);

        // Get all the bookYstList where rateplanstate does not contain UPDATED_RATEPLANSTATE
        defaultBookYstShouldBeFound("rateplanstate.doesNotContain=" + UPDATED_RATEPLANSTATE);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebednumIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebednum equals to DEFAULT_ADDVALUEBEDNUM
        defaultBookYstShouldBeFound("addvaluebednum.equals=" + DEFAULT_ADDVALUEBEDNUM);

        // Get all the bookYstList where addvaluebednum equals to UPDATED_ADDVALUEBEDNUM
        defaultBookYstShouldNotBeFound("addvaluebednum.equals=" + UPDATED_ADDVALUEBEDNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebednumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebednum not equals to DEFAULT_ADDVALUEBEDNUM
        defaultBookYstShouldNotBeFound("addvaluebednum.notEquals=" + DEFAULT_ADDVALUEBEDNUM);

        // Get all the bookYstList where addvaluebednum not equals to UPDATED_ADDVALUEBEDNUM
        defaultBookYstShouldBeFound("addvaluebednum.notEquals=" + UPDATED_ADDVALUEBEDNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebednumIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebednum in DEFAULT_ADDVALUEBEDNUM or UPDATED_ADDVALUEBEDNUM
        defaultBookYstShouldBeFound("addvaluebednum.in=" + DEFAULT_ADDVALUEBEDNUM + "," + UPDATED_ADDVALUEBEDNUM);

        // Get all the bookYstList where addvaluebednum equals to UPDATED_ADDVALUEBEDNUM
        defaultBookYstShouldNotBeFound("addvaluebednum.in=" + UPDATED_ADDVALUEBEDNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebednumIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebednum is not null
        defaultBookYstShouldBeFound("addvaluebednum.specified=true");

        // Get all the bookYstList where addvaluebednum is null
        defaultBookYstShouldNotBeFound("addvaluebednum.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebednumContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebednum contains DEFAULT_ADDVALUEBEDNUM
        defaultBookYstShouldBeFound("addvaluebednum.contains=" + DEFAULT_ADDVALUEBEDNUM);

        // Get all the bookYstList where addvaluebednum contains UPDATED_ADDVALUEBEDNUM
        defaultBookYstShouldNotBeFound("addvaluebednum.contains=" + UPDATED_ADDVALUEBEDNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebednumNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebednum does not contain DEFAULT_ADDVALUEBEDNUM
        defaultBookYstShouldNotBeFound("addvaluebednum.doesNotContain=" + DEFAULT_ADDVALUEBEDNUM);

        // Get all the bookYstList where addvaluebednum does not contain UPDATED_ADDVALUEBEDNUM
        defaultBookYstShouldBeFound("addvaluebednum.doesNotContain=" + UPDATED_ADDVALUEBEDNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebedpriceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebedprice equals to DEFAULT_ADDVALUEBEDPRICE
        defaultBookYstShouldBeFound("addvaluebedprice.equals=" + DEFAULT_ADDVALUEBEDPRICE);

        // Get all the bookYstList where addvaluebedprice equals to UPDATED_ADDVALUEBEDPRICE
        defaultBookYstShouldNotBeFound("addvaluebedprice.equals=" + UPDATED_ADDVALUEBEDPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebedpriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebedprice not equals to DEFAULT_ADDVALUEBEDPRICE
        defaultBookYstShouldNotBeFound("addvaluebedprice.notEquals=" + DEFAULT_ADDVALUEBEDPRICE);

        // Get all the bookYstList where addvaluebedprice not equals to UPDATED_ADDVALUEBEDPRICE
        defaultBookYstShouldBeFound("addvaluebedprice.notEquals=" + UPDATED_ADDVALUEBEDPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebedpriceIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebedprice in DEFAULT_ADDVALUEBEDPRICE or UPDATED_ADDVALUEBEDPRICE
        defaultBookYstShouldBeFound("addvaluebedprice.in=" + DEFAULT_ADDVALUEBEDPRICE + "," + UPDATED_ADDVALUEBEDPRICE);

        // Get all the bookYstList where addvaluebedprice equals to UPDATED_ADDVALUEBEDPRICE
        defaultBookYstShouldNotBeFound("addvaluebedprice.in=" + UPDATED_ADDVALUEBEDPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebedpriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebedprice is not null
        defaultBookYstShouldBeFound("addvaluebedprice.specified=true");

        // Get all the bookYstList where addvaluebedprice is null
        defaultBookYstShouldNotBeFound("addvaluebedprice.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebedpriceContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebedprice contains DEFAULT_ADDVALUEBEDPRICE
        defaultBookYstShouldBeFound("addvaluebedprice.contains=" + DEFAULT_ADDVALUEBEDPRICE);

        // Get all the bookYstList where addvaluebedprice contains UPDATED_ADDVALUEBEDPRICE
        defaultBookYstShouldNotBeFound("addvaluebedprice.contains=" + UPDATED_ADDVALUEBEDPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebedpriceNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebedprice does not contain DEFAULT_ADDVALUEBEDPRICE
        defaultBookYstShouldNotBeFound("addvaluebedprice.doesNotContain=" + DEFAULT_ADDVALUEBEDPRICE);

        // Get all the bookYstList where addvaluebedprice does not contain UPDATED_ADDVALUEBEDPRICE
        defaultBookYstShouldBeFound("addvaluebedprice.doesNotContain=" + UPDATED_ADDVALUEBEDPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebreakfastnumIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebreakfastnum equals to DEFAULT_ADDVALUEBREAKFASTNUM
        defaultBookYstShouldBeFound("addvaluebreakfastnum.equals=" + DEFAULT_ADDVALUEBREAKFASTNUM);

        // Get all the bookYstList where addvaluebreakfastnum equals to UPDATED_ADDVALUEBREAKFASTNUM
        defaultBookYstShouldNotBeFound("addvaluebreakfastnum.equals=" + UPDATED_ADDVALUEBREAKFASTNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebreakfastnumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebreakfastnum not equals to DEFAULT_ADDVALUEBREAKFASTNUM
        defaultBookYstShouldNotBeFound("addvaluebreakfastnum.notEquals=" + DEFAULT_ADDVALUEBREAKFASTNUM);

        // Get all the bookYstList where addvaluebreakfastnum not equals to UPDATED_ADDVALUEBREAKFASTNUM
        defaultBookYstShouldBeFound("addvaluebreakfastnum.notEquals=" + UPDATED_ADDVALUEBREAKFASTNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebreakfastnumIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebreakfastnum in DEFAULT_ADDVALUEBREAKFASTNUM or UPDATED_ADDVALUEBREAKFASTNUM
        defaultBookYstShouldBeFound("addvaluebreakfastnum.in=" + DEFAULT_ADDVALUEBREAKFASTNUM + "," + UPDATED_ADDVALUEBREAKFASTNUM);

        // Get all the bookYstList where addvaluebreakfastnum equals to UPDATED_ADDVALUEBREAKFASTNUM
        defaultBookYstShouldNotBeFound("addvaluebreakfastnum.in=" + UPDATED_ADDVALUEBREAKFASTNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebreakfastnumIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebreakfastnum is not null
        defaultBookYstShouldBeFound("addvaluebreakfastnum.specified=true");

        // Get all the bookYstList where addvaluebreakfastnum is null
        defaultBookYstShouldNotBeFound("addvaluebreakfastnum.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebreakfastnumContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebreakfastnum contains DEFAULT_ADDVALUEBREAKFASTNUM
        defaultBookYstShouldBeFound("addvaluebreakfastnum.contains=" + DEFAULT_ADDVALUEBREAKFASTNUM);

        // Get all the bookYstList where addvaluebreakfastnum contains UPDATED_ADDVALUEBREAKFASTNUM
        defaultBookYstShouldNotBeFound("addvaluebreakfastnum.contains=" + UPDATED_ADDVALUEBREAKFASTNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebreakfastnumNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebreakfastnum does not contain DEFAULT_ADDVALUEBREAKFASTNUM
        defaultBookYstShouldNotBeFound("addvaluebreakfastnum.doesNotContain=" + DEFAULT_ADDVALUEBREAKFASTNUM);

        // Get all the bookYstList where addvaluebreakfastnum does not contain UPDATED_ADDVALUEBREAKFASTNUM
        defaultBookYstShouldBeFound("addvaluebreakfastnum.doesNotContain=" + UPDATED_ADDVALUEBREAKFASTNUM);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebreakfastpriceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebreakfastprice equals to DEFAULT_ADDVALUEBREAKFASTPRICE
        defaultBookYstShouldBeFound("addvaluebreakfastprice.equals=" + DEFAULT_ADDVALUEBREAKFASTPRICE);

        // Get all the bookYstList where addvaluebreakfastprice equals to UPDATED_ADDVALUEBREAKFASTPRICE
        defaultBookYstShouldNotBeFound("addvaluebreakfastprice.equals=" + UPDATED_ADDVALUEBREAKFASTPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebreakfastpriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebreakfastprice not equals to DEFAULT_ADDVALUEBREAKFASTPRICE
        defaultBookYstShouldNotBeFound("addvaluebreakfastprice.notEquals=" + DEFAULT_ADDVALUEBREAKFASTPRICE);

        // Get all the bookYstList where addvaluebreakfastprice not equals to UPDATED_ADDVALUEBREAKFASTPRICE
        defaultBookYstShouldBeFound("addvaluebreakfastprice.notEquals=" + UPDATED_ADDVALUEBREAKFASTPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebreakfastpriceIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebreakfastprice in DEFAULT_ADDVALUEBREAKFASTPRICE or UPDATED_ADDVALUEBREAKFASTPRICE
        defaultBookYstShouldBeFound("addvaluebreakfastprice.in=" + DEFAULT_ADDVALUEBREAKFASTPRICE + "," + UPDATED_ADDVALUEBREAKFASTPRICE);

        // Get all the bookYstList where addvaluebreakfastprice equals to UPDATED_ADDVALUEBREAKFASTPRICE
        defaultBookYstShouldNotBeFound("addvaluebreakfastprice.in=" + UPDATED_ADDVALUEBREAKFASTPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebreakfastpriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebreakfastprice is not null
        defaultBookYstShouldBeFound("addvaluebreakfastprice.specified=true");

        // Get all the bookYstList where addvaluebreakfastprice is null
        defaultBookYstShouldNotBeFound("addvaluebreakfastprice.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebreakfastpriceContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebreakfastprice contains DEFAULT_ADDVALUEBREAKFASTPRICE
        defaultBookYstShouldBeFound("addvaluebreakfastprice.contains=" + DEFAULT_ADDVALUEBREAKFASTPRICE);

        // Get all the bookYstList where addvaluebreakfastprice contains UPDATED_ADDVALUEBREAKFASTPRICE
        defaultBookYstShouldNotBeFound("addvaluebreakfastprice.contains=" + UPDATED_ADDVALUEBREAKFASTPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByAddvaluebreakfastpriceNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where addvaluebreakfastprice does not contain DEFAULT_ADDVALUEBREAKFASTPRICE
        defaultBookYstShouldNotBeFound("addvaluebreakfastprice.doesNotContain=" + DEFAULT_ADDVALUEBREAKFASTPRICE);

        // Get all the bookYstList where addvaluebreakfastprice does not contain UPDATED_ADDVALUEBREAKFASTPRICE
        defaultBookYstShouldBeFound("addvaluebreakfastprice.doesNotContain=" + UPDATED_ADDVALUEBREAKFASTPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByBasepriceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where baseprice equals to DEFAULT_BASEPRICE
        defaultBookYstShouldBeFound("baseprice.equals=" + DEFAULT_BASEPRICE);

        // Get all the bookYstList where baseprice equals to UPDATED_BASEPRICE
        defaultBookYstShouldNotBeFound("baseprice.equals=" + UPDATED_BASEPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByBasepriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where baseprice not equals to DEFAULT_BASEPRICE
        defaultBookYstShouldNotBeFound("baseprice.notEquals=" + DEFAULT_BASEPRICE);

        // Get all the bookYstList where baseprice not equals to UPDATED_BASEPRICE
        defaultBookYstShouldBeFound("baseprice.notEquals=" + UPDATED_BASEPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByBasepriceIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where baseprice in DEFAULT_BASEPRICE or UPDATED_BASEPRICE
        defaultBookYstShouldBeFound("baseprice.in=" + DEFAULT_BASEPRICE + "," + UPDATED_BASEPRICE);

        // Get all the bookYstList where baseprice equals to UPDATED_BASEPRICE
        defaultBookYstShouldNotBeFound("baseprice.in=" + UPDATED_BASEPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByBasepriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where baseprice is not null
        defaultBookYstShouldBeFound("baseprice.specified=true");

        // Get all the bookYstList where baseprice is null
        defaultBookYstShouldNotBeFound("baseprice.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByBasepriceContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where baseprice contains DEFAULT_BASEPRICE
        defaultBookYstShouldBeFound("baseprice.contains=" + DEFAULT_BASEPRICE);

        // Get all the bookYstList where baseprice contains UPDATED_BASEPRICE
        defaultBookYstShouldNotBeFound("baseprice.contains=" + UPDATED_BASEPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByBasepriceNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where baseprice does not contain DEFAULT_BASEPRICE
        defaultBookYstShouldNotBeFound("baseprice.doesNotContain=" + DEFAULT_BASEPRICE);

        // Get all the bookYstList where baseprice does not contain UPDATED_BASEPRICE
        defaultBookYstShouldBeFound("baseprice.doesNotContain=" + UPDATED_BASEPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsBySalepriceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where saleprice equals to DEFAULT_SALEPRICE
        defaultBookYstShouldBeFound("saleprice.equals=" + DEFAULT_SALEPRICE);

        // Get all the bookYstList where saleprice equals to UPDATED_SALEPRICE
        defaultBookYstShouldNotBeFound("saleprice.equals=" + UPDATED_SALEPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsBySalepriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where saleprice not equals to DEFAULT_SALEPRICE
        defaultBookYstShouldNotBeFound("saleprice.notEquals=" + DEFAULT_SALEPRICE);

        // Get all the bookYstList where saleprice not equals to UPDATED_SALEPRICE
        defaultBookYstShouldBeFound("saleprice.notEquals=" + UPDATED_SALEPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsBySalepriceIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where saleprice in DEFAULT_SALEPRICE or UPDATED_SALEPRICE
        defaultBookYstShouldBeFound("saleprice.in=" + DEFAULT_SALEPRICE + "," + UPDATED_SALEPRICE);

        // Get all the bookYstList where saleprice equals to UPDATED_SALEPRICE
        defaultBookYstShouldNotBeFound("saleprice.in=" + UPDATED_SALEPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsBySalepriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where saleprice is not null
        defaultBookYstShouldBeFound("saleprice.specified=true");

        // Get all the bookYstList where saleprice is null
        defaultBookYstShouldNotBeFound("saleprice.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsBySalepriceContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where saleprice contains DEFAULT_SALEPRICE
        defaultBookYstShouldBeFound("saleprice.contains=" + DEFAULT_SALEPRICE);

        // Get all the bookYstList where saleprice contains UPDATED_SALEPRICE
        defaultBookYstShouldNotBeFound("saleprice.contains=" + UPDATED_SALEPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsBySalepriceNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where saleprice does not contain DEFAULT_SALEPRICE
        defaultBookYstShouldNotBeFound("saleprice.doesNotContain=" + DEFAULT_SALEPRICE);

        // Get all the bookYstList where saleprice does not contain UPDATED_SALEPRICE
        defaultBookYstShouldBeFound("saleprice.doesNotContain=" + UPDATED_SALEPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByMarketpriceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where marketprice equals to DEFAULT_MARKETPRICE
        defaultBookYstShouldBeFound("marketprice.equals=" + DEFAULT_MARKETPRICE);

        // Get all the bookYstList where marketprice equals to UPDATED_MARKETPRICE
        defaultBookYstShouldNotBeFound("marketprice.equals=" + UPDATED_MARKETPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByMarketpriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where marketprice not equals to DEFAULT_MARKETPRICE
        defaultBookYstShouldNotBeFound("marketprice.notEquals=" + DEFAULT_MARKETPRICE);

        // Get all the bookYstList where marketprice not equals to UPDATED_MARKETPRICE
        defaultBookYstShouldBeFound("marketprice.notEquals=" + UPDATED_MARKETPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByMarketpriceIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where marketprice in DEFAULT_MARKETPRICE or UPDATED_MARKETPRICE
        defaultBookYstShouldBeFound("marketprice.in=" + DEFAULT_MARKETPRICE + "," + UPDATED_MARKETPRICE);

        // Get all the bookYstList where marketprice equals to UPDATED_MARKETPRICE
        defaultBookYstShouldNotBeFound("marketprice.in=" + UPDATED_MARKETPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByMarketpriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where marketprice is not null
        defaultBookYstShouldBeFound("marketprice.specified=true");

        // Get all the bookYstList where marketprice is null
        defaultBookYstShouldNotBeFound("marketprice.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByMarketpriceContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where marketprice contains DEFAULT_MARKETPRICE
        defaultBookYstShouldBeFound("marketprice.contains=" + DEFAULT_MARKETPRICE);

        // Get all the bookYstList where marketprice contains UPDATED_MARKETPRICE
        defaultBookYstShouldNotBeFound("marketprice.contains=" + UPDATED_MARKETPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByMarketpriceNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where marketprice does not contain DEFAULT_MARKETPRICE
        defaultBookYstShouldNotBeFound("marketprice.doesNotContain=" + DEFAULT_MARKETPRICE);

        // Get all the bookYstList where marketprice does not contain UPDATED_MARKETPRICE
        defaultBookYstShouldBeFound("marketprice.doesNotContain=" + UPDATED_MARKETPRICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductserviceIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductservice equals to DEFAULT_HOTELPRODUCTSERVICE
        defaultBookYstShouldBeFound("hotelproductservice.equals=" + DEFAULT_HOTELPRODUCTSERVICE);

        // Get all the bookYstList where hotelproductservice equals to UPDATED_HOTELPRODUCTSERVICE
        defaultBookYstShouldNotBeFound("hotelproductservice.equals=" + UPDATED_HOTELPRODUCTSERVICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductserviceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductservice not equals to DEFAULT_HOTELPRODUCTSERVICE
        defaultBookYstShouldNotBeFound("hotelproductservice.notEquals=" + DEFAULT_HOTELPRODUCTSERVICE);

        // Get all the bookYstList where hotelproductservice not equals to UPDATED_HOTELPRODUCTSERVICE
        defaultBookYstShouldBeFound("hotelproductservice.notEquals=" + UPDATED_HOTELPRODUCTSERVICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductserviceIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductservice in DEFAULT_HOTELPRODUCTSERVICE or UPDATED_HOTELPRODUCTSERVICE
        defaultBookYstShouldBeFound("hotelproductservice.in=" + DEFAULT_HOTELPRODUCTSERVICE + "," + UPDATED_HOTELPRODUCTSERVICE);

        // Get all the bookYstList where hotelproductservice equals to UPDATED_HOTELPRODUCTSERVICE
        defaultBookYstShouldNotBeFound("hotelproductservice.in=" + UPDATED_HOTELPRODUCTSERVICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductserviceIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductservice is not null
        defaultBookYstShouldBeFound("hotelproductservice.specified=true");

        // Get all the bookYstList where hotelproductservice is null
        defaultBookYstShouldNotBeFound("hotelproductservice.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductserviceContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductservice contains DEFAULT_HOTELPRODUCTSERVICE
        defaultBookYstShouldBeFound("hotelproductservice.contains=" + DEFAULT_HOTELPRODUCTSERVICE);

        // Get all the bookYstList where hotelproductservice contains UPDATED_HOTELPRODUCTSERVICE
        defaultBookYstShouldNotBeFound("hotelproductservice.contains=" + UPDATED_HOTELPRODUCTSERVICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductserviceNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductservice does not contain DEFAULT_HOTELPRODUCTSERVICE
        defaultBookYstShouldNotBeFound("hotelproductservice.doesNotContain=" + DEFAULT_HOTELPRODUCTSERVICE);

        // Get all the bookYstList where hotelproductservice does not contain UPDATED_HOTELPRODUCTSERVICE
        defaultBookYstShouldBeFound("hotelproductservice.doesNotContain=" + UPDATED_HOTELPRODUCTSERVICE);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductservicedescIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductservicedesc equals to DEFAULT_HOTELPRODUCTSERVICEDESC
        defaultBookYstShouldBeFound("hotelproductservicedesc.equals=" + DEFAULT_HOTELPRODUCTSERVICEDESC);

        // Get all the bookYstList where hotelproductservicedesc equals to UPDATED_HOTELPRODUCTSERVICEDESC
        defaultBookYstShouldNotBeFound("hotelproductservicedesc.equals=" + UPDATED_HOTELPRODUCTSERVICEDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductservicedescIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductservicedesc not equals to DEFAULT_HOTELPRODUCTSERVICEDESC
        defaultBookYstShouldNotBeFound("hotelproductservicedesc.notEquals=" + DEFAULT_HOTELPRODUCTSERVICEDESC);

        // Get all the bookYstList where hotelproductservicedesc not equals to UPDATED_HOTELPRODUCTSERVICEDESC
        defaultBookYstShouldBeFound("hotelproductservicedesc.notEquals=" + UPDATED_HOTELPRODUCTSERVICEDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductservicedescIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductservicedesc in DEFAULT_HOTELPRODUCTSERVICEDESC or UPDATED_HOTELPRODUCTSERVICEDESC
        defaultBookYstShouldBeFound(
            "hotelproductservicedesc.in=" + DEFAULT_HOTELPRODUCTSERVICEDESC + "," + UPDATED_HOTELPRODUCTSERVICEDESC
        );

        // Get all the bookYstList where hotelproductservicedesc equals to UPDATED_HOTELPRODUCTSERVICEDESC
        defaultBookYstShouldNotBeFound("hotelproductservicedesc.in=" + UPDATED_HOTELPRODUCTSERVICEDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductservicedescIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductservicedesc is not null
        defaultBookYstShouldBeFound("hotelproductservicedesc.specified=true");

        // Get all the bookYstList where hotelproductservicedesc is null
        defaultBookYstShouldNotBeFound("hotelproductservicedesc.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductservicedescContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductservicedesc contains DEFAULT_HOTELPRODUCTSERVICEDESC
        defaultBookYstShouldBeFound("hotelproductservicedesc.contains=" + DEFAULT_HOTELPRODUCTSERVICEDESC);

        // Get all the bookYstList where hotelproductservicedesc contains UPDATED_HOTELPRODUCTSERVICEDESC
        defaultBookYstShouldNotBeFound("hotelproductservicedesc.contains=" + UPDATED_HOTELPRODUCTSERVICEDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductservicedescNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductservicedesc does not contain DEFAULT_HOTELPRODUCTSERVICEDESC
        defaultBookYstShouldNotBeFound("hotelproductservicedesc.doesNotContain=" + DEFAULT_HOTELPRODUCTSERVICEDESC);

        // Get all the bookYstList where hotelproductservicedesc does not contain UPDATED_HOTELPRODUCTSERVICEDESC
        defaultBookYstShouldBeFound("hotelproductservicedesc.doesNotContain=" + UPDATED_HOTELPRODUCTSERVICEDESC);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductidIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductid equals to DEFAULT_HOTELPRODUCTID
        defaultBookYstShouldBeFound("hotelproductid.equals=" + DEFAULT_HOTELPRODUCTID);

        // Get all the bookYstList where hotelproductid equals to UPDATED_HOTELPRODUCTID
        defaultBookYstShouldNotBeFound("hotelproductid.equals=" + UPDATED_HOTELPRODUCTID);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductid not equals to DEFAULT_HOTELPRODUCTID
        defaultBookYstShouldNotBeFound("hotelproductid.notEquals=" + DEFAULT_HOTELPRODUCTID);

        // Get all the bookYstList where hotelproductid not equals to UPDATED_HOTELPRODUCTID
        defaultBookYstShouldBeFound("hotelproductid.notEquals=" + UPDATED_HOTELPRODUCTID);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductidIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductid in DEFAULT_HOTELPRODUCTID or UPDATED_HOTELPRODUCTID
        defaultBookYstShouldBeFound("hotelproductid.in=" + DEFAULT_HOTELPRODUCTID + "," + UPDATED_HOTELPRODUCTID);

        // Get all the bookYstList where hotelproductid equals to UPDATED_HOTELPRODUCTID
        defaultBookYstShouldNotBeFound("hotelproductid.in=" + UPDATED_HOTELPRODUCTID);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductidIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductid is not null
        defaultBookYstShouldBeFound("hotelproductid.specified=true");

        // Get all the bookYstList where hotelproductid is null
        defaultBookYstShouldNotBeFound("hotelproductid.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductidContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductid contains DEFAULT_HOTELPRODUCTID
        defaultBookYstShouldBeFound("hotelproductid.contains=" + DEFAULT_HOTELPRODUCTID);

        // Get all the bookYstList where hotelproductid contains UPDATED_HOTELPRODUCTID
        defaultBookYstShouldNotBeFound("hotelproductid.contains=" + UPDATED_HOTELPRODUCTID);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelproductidNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelproductid does not contain DEFAULT_HOTELPRODUCTID
        defaultBookYstShouldNotBeFound("hotelproductid.doesNotContain=" + DEFAULT_HOTELPRODUCTID);

        // Get all the bookYstList where hotelproductid does not contain UPDATED_HOTELPRODUCTID
        defaultBookYstShouldBeFound("hotelproductid.doesNotContain=" + UPDATED_HOTELPRODUCTID);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomidIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomid equals to DEFAULT_ROOMID
        defaultBookYstShouldBeFound("roomid.equals=" + DEFAULT_ROOMID);

        // Get all the bookYstList where roomid equals to UPDATED_ROOMID
        defaultBookYstShouldNotBeFound("roomid.equals=" + UPDATED_ROOMID);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomid not equals to DEFAULT_ROOMID
        defaultBookYstShouldNotBeFound("roomid.notEquals=" + DEFAULT_ROOMID);

        // Get all the bookYstList where roomid not equals to UPDATED_ROOMID
        defaultBookYstShouldBeFound("roomid.notEquals=" + UPDATED_ROOMID);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomidIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomid in DEFAULT_ROOMID or UPDATED_ROOMID
        defaultBookYstShouldBeFound("roomid.in=" + DEFAULT_ROOMID + "," + UPDATED_ROOMID);

        // Get all the bookYstList where roomid equals to UPDATED_ROOMID
        defaultBookYstShouldNotBeFound("roomid.in=" + UPDATED_ROOMID);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomidIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomid is not null
        defaultBookYstShouldBeFound("roomid.specified=true");

        // Get all the bookYstList where roomid is null
        defaultBookYstShouldNotBeFound("roomid.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomidContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomid contains DEFAULT_ROOMID
        defaultBookYstShouldBeFound("roomid.contains=" + DEFAULT_ROOMID);

        // Get all the bookYstList where roomid contains UPDATED_ROOMID
        defaultBookYstShouldNotBeFound("roomid.contains=" + UPDATED_ROOMID);
    }

    @Test
    @Transactional
    void getAllBookYstsByRoomidNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where roomid does not contain DEFAULT_ROOMID
        defaultBookYstShouldNotBeFound("roomid.doesNotContain=" + DEFAULT_ROOMID);

        // Get all the bookYstList where roomid does not contain UPDATED_ROOMID
        defaultBookYstShouldBeFound("roomid.doesNotContain=" + UPDATED_ROOMID);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelidIsEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelid equals to DEFAULT_HOTELID
        defaultBookYstShouldBeFound("hotelid.equals=" + DEFAULT_HOTELID);

        // Get all the bookYstList where hotelid equals to UPDATED_HOTELID
        defaultBookYstShouldNotBeFound("hotelid.equals=" + UPDATED_HOTELID);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelid not equals to DEFAULT_HOTELID
        defaultBookYstShouldNotBeFound("hotelid.notEquals=" + DEFAULT_HOTELID);

        // Get all the bookYstList where hotelid not equals to UPDATED_HOTELID
        defaultBookYstShouldBeFound("hotelid.notEquals=" + UPDATED_HOTELID);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelidIsInShouldWork() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelid in DEFAULT_HOTELID or UPDATED_HOTELID
        defaultBookYstShouldBeFound("hotelid.in=" + DEFAULT_HOTELID + "," + UPDATED_HOTELID);

        // Get all the bookYstList where hotelid equals to UPDATED_HOTELID
        defaultBookYstShouldNotBeFound("hotelid.in=" + UPDATED_HOTELID);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelidIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelid is not null
        defaultBookYstShouldBeFound("hotelid.specified=true");

        // Get all the bookYstList where hotelid is null
        defaultBookYstShouldNotBeFound("hotelid.specified=false");
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelidContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelid contains DEFAULT_HOTELID
        defaultBookYstShouldBeFound("hotelid.contains=" + DEFAULT_HOTELID);

        // Get all the bookYstList where hotelid contains UPDATED_HOTELID
        defaultBookYstShouldNotBeFound("hotelid.contains=" + UPDATED_HOTELID);
    }

    @Test
    @Transactional
    void getAllBookYstsByHotelidNotContainsSomething() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        // Get all the bookYstList where hotelid does not contain DEFAULT_HOTELID
        defaultBookYstShouldNotBeFound("hotelid.doesNotContain=" + DEFAULT_HOTELID);

        // Get all the bookYstList where hotelid does not contain UPDATED_HOTELID
        defaultBookYstShouldBeFound("hotelid.doesNotContain=" + UPDATED_HOTELID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBookYstShouldBeFound(String filter) throws Exception {
        restBookYstMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookYst.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomcode").value(hasItem(DEFAULT_ROOMCODE)))
            .andExpect(jsonPath("$.[*].roomname").value(hasItem(DEFAULT_ROOMNAME)))
            .andExpect(jsonPath("$.[*].roomnum").value(hasItem(DEFAULT_ROOMNUM)))
            .andExpect(jsonPath("$.[*].roomseparatenum").value(hasItem(DEFAULT_ROOMSEPARATENUM)))
            .andExpect(jsonPath("$.[*].bedids").value(hasItem(DEFAULT_BEDIDS)))
            .andExpect(jsonPath("$.[*].bedsimpledesc").value(hasItem(DEFAULT_BEDSIMPLEDESC)))
            .andExpect(jsonPath("$.[*].bednum").value(hasItem(DEFAULT_BEDNUM)))
            .andExpect(jsonPath("$.[*].roomsize").value(hasItem(DEFAULT_ROOMSIZE)))
            .andExpect(jsonPath("$.[*].roomfloor").value(hasItem(DEFAULT_ROOMFLOOR)))
            .andExpect(jsonPath("$.[*].netservice").value(hasItem(DEFAULT_NETSERVICE)))
            .andExpect(jsonPath("$.[*].nettype").value(hasItem(DEFAULT_NETTYPE)))
            .andExpect(jsonPath("$.[*].iswindow").value(hasItem(DEFAULT_ISWINDOW)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].sortid").value(hasItem(DEFAULT_SORTID)))
            .andExpect(jsonPath("$.[*].roomstate").value(hasItem(DEFAULT_ROOMSTATE)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].roomamenities").value(hasItem(DEFAULT_ROOMAMENITIES)))
            .andExpect(jsonPath("$.[*].maxguestnums").value(hasItem(DEFAULT_MAXGUESTNUMS)))
            .andExpect(jsonPath("$.[*].roomdistribution").value(hasItem(DEFAULT_ROOMDISTRIBUTION)))
            .andExpect(jsonPath("$.[*].conditionbeforedays").value(hasItem(DEFAULT_CONDITIONBEFOREDAYS)))
            .andExpect(jsonPath("$.[*].conditionleastdays").value(hasItem(DEFAULT_CONDITIONLEASTDAYS)))
            .andExpect(jsonPath("$.[*].conditionleastroomnum").value(hasItem(DEFAULT_CONDITIONLEASTROOMNUM)))
            .andExpect(jsonPath("$.[*].paymentype").value(hasItem(DEFAULT_PAYMENTYPE)))
            .andExpect(jsonPath("$.[*].rateplandesc").value(hasItem(DEFAULT_RATEPLANDESC)))
            .andExpect(jsonPath("$.[*].rateplanname").value(hasItem(DEFAULT_RATEPLANNAME)))
            .andExpect(jsonPath("$.[*].rateplanstate").value(hasItem(DEFAULT_RATEPLANSTATE)))
            .andExpect(jsonPath("$.[*].addvaluebednum").value(hasItem(DEFAULT_ADDVALUEBEDNUM)))
            .andExpect(jsonPath("$.[*].addvaluebedprice").value(hasItem(DEFAULT_ADDVALUEBEDPRICE)))
            .andExpect(jsonPath("$.[*].addvaluebreakfastnum").value(hasItem(DEFAULT_ADDVALUEBREAKFASTNUM)))
            .andExpect(jsonPath("$.[*].addvaluebreakfastprice").value(hasItem(DEFAULT_ADDVALUEBREAKFASTPRICE)))
            .andExpect(jsonPath("$.[*].baseprice").value(hasItem(DEFAULT_BASEPRICE)))
            .andExpect(jsonPath("$.[*].saleprice").value(hasItem(DEFAULT_SALEPRICE)))
            .andExpect(jsonPath("$.[*].marketprice").value(hasItem(DEFAULT_MARKETPRICE)))
            .andExpect(jsonPath("$.[*].hotelproductservice").value(hasItem(DEFAULT_HOTELPRODUCTSERVICE)))
            .andExpect(jsonPath("$.[*].hotelproductservicedesc").value(hasItem(DEFAULT_HOTELPRODUCTSERVICEDESC)))
            .andExpect(jsonPath("$.[*].hotelproductid").value(hasItem(DEFAULT_HOTELPRODUCTID)))
            .andExpect(jsonPath("$.[*].roomid").value(hasItem(DEFAULT_ROOMID)))
            .andExpect(jsonPath("$.[*].hotelid").value(hasItem(DEFAULT_HOTELID)));

        // Check, that the count call also returns 1
        restBookYstMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBookYstShouldNotBeFound(String filter) throws Exception {
        restBookYstMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBookYstMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBookYst() throws Exception {
        // Get the bookYst
        restBookYstMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBookYst() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        int databaseSizeBeforeUpdate = bookYstRepository.findAll().size();

        // Update the bookYst
        BookYst updatedBookYst = bookYstRepository.findById(bookYst.getId()).get();
        // Disconnect from session so that the updates on updatedBookYst are not directly saved in db
        em.detach(updatedBookYst);
        updatedBookYst
            .roomcode(UPDATED_ROOMCODE)
            .roomname(UPDATED_ROOMNAME)
            .roomnum(UPDATED_ROOMNUM)
            .roomseparatenum(UPDATED_ROOMSEPARATENUM)
            .bedids(UPDATED_BEDIDS)
            .bedsimpledesc(UPDATED_BEDSIMPLEDESC)
            .bednum(UPDATED_BEDNUM)
            .roomsize(UPDATED_ROOMSIZE)
            .roomfloor(UPDATED_ROOMFLOOR)
            .netservice(UPDATED_NETSERVICE)
            .nettype(UPDATED_NETTYPE)
            .iswindow(UPDATED_ISWINDOW)
            .remark(UPDATED_REMARK)
            .sortid(UPDATED_SORTID)
            .roomstate(UPDATED_ROOMSTATE)
            .source(UPDATED_SOURCE)
            .roomamenities(UPDATED_ROOMAMENITIES)
            .maxguestnums(UPDATED_MAXGUESTNUMS)
            .roomdistribution(UPDATED_ROOMDISTRIBUTION)
            .conditionbeforedays(UPDATED_CONDITIONBEFOREDAYS)
            .conditionleastdays(UPDATED_CONDITIONLEASTDAYS)
            .conditionleastroomnum(UPDATED_CONDITIONLEASTROOMNUM)
            .paymentype(UPDATED_PAYMENTYPE)
            .rateplandesc(UPDATED_RATEPLANDESC)
            .rateplanname(UPDATED_RATEPLANNAME)
            .rateplanstate(UPDATED_RATEPLANSTATE)
            .addvaluebednum(UPDATED_ADDVALUEBEDNUM)
            .addvaluebedprice(UPDATED_ADDVALUEBEDPRICE)
            .addvaluebreakfastnum(UPDATED_ADDVALUEBREAKFASTNUM)
            .addvaluebreakfastprice(UPDATED_ADDVALUEBREAKFASTPRICE)
            .baseprice(UPDATED_BASEPRICE)
            .saleprice(UPDATED_SALEPRICE)
            .marketprice(UPDATED_MARKETPRICE)
            .hotelproductservice(UPDATED_HOTELPRODUCTSERVICE)
            .hotelproductservicedesc(UPDATED_HOTELPRODUCTSERVICEDESC)
            .hotelproductid(UPDATED_HOTELPRODUCTID)
            .roomid(UPDATED_ROOMID)
            .hotelid(UPDATED_HOTELID);
        BookYstDTO bookYstDTO = bookYstMapper.toDto(updatedBookYst);

        restBookYstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookYstDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookYstDTO))
            )
            .andExpect(status().isOk());

        // Validate the BookYst in the database
        List<BookYst> bookYstList = bookYstRepository.findAll();
        assertThat(bookYstList).hasSize(databaseSizeBeforeUpdate);
        BookYst testBookYst = bookYstList.get(bookYstList.size() - 1);
        assertThat(testBookYst.getRoomcode()).isEqualTo(UPDATED_ROOMCODE);
        assertThat(testBookYst.getRoomname()).isEqualTo(UPDATED_ROOMNAME);
        assertThat(testBookYst.getRoomnum()).isEqualTo(UPDATED_ROOMNUM);
        assertThat(testBookYst.getRoomseparatenum()).isEqualTo(UPDATED_ROOMSEPARATENUM);
        assertThat(testBookYst.getBedids()).isEqualTo(UPDATED_BEDIDS);
        assertThat(testBookYst.getBedsimpledesc()).isEqualTo(UPDATED_BEDSIMPLEDESC);
        assertThat(testBookYst.getBednum()).isEqualTo(UPDATED_BEDNUM);
        assertThat(testBookYst.getRoomsize()).isEqualTo(UPDATED_ROOMSIZE);
        assertThat(testBookYst.getRoomfloor()).isEqualTo(UPDATED_ROOMFLOOR);
        assertThat(testBookYst.getNetservice()).isEqualTo(UPDATED_NETSERVICE);
        assertThat(testBookYst.getNettype()).isEqualTo(UPDATED_NETTYPE);
        assertThat(testBookYst.getIswindow()).isEqualTo(UPDATED_ISWINDOW);
        assertThat(testBookYst.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testBookYst.getSortid()).isEqualTo(UPDATED_SORTID);
        assertThat(testBookYst.getRoomstate()).isEqualTo(UPDATED_ROOMSTATE);
        assertThat(testBookYst.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testBookYst.getRoomamenities()).isEqualTo(UPDATED_ROOMAMENITIES);
        assertThat(testBookYst.getMaxguestnums()).isEqualTo(UPDATED_MAXGUESTNUMS);
        assertThat(testBookYst.getRoomdistribution()).isEqualTo(UPDATED_ROOMDISTRIBUTION);
        assertThat(testBookYst.getConditionbeforedays()).isEqualTo(UPDATED_CONDITIONBEFOREDAYS);
        assertThat(testBookYst.getConditionleastdays()).isEqualTo(UPDATED_CONDITIONLEASTDAYS);
        assertThat(testBookYst.getConditionleastroomnum()).isEqualTo(UPDATED_CONDITIONLEASTROOMNUM);
        assertThat(testBookYst.getPaymentype()).isEqualTo(UPDATED_PAYMENTYPE);
        assertThat(testBookYst.getRateplandesc()).isEqualTo(UPDATED_RATEPLANDESC);
        assertThat(testBookYst.getRateplanname()).isEqualTo(UPDATED_RATEPLANNAME);
        assertThat(testBookYst.getRateplanstate()).isEqualTo(UPDATED_RATEPLANSTATE);
        assertThat(testBookYst.getAddvaluebednum()).isEqualTo(UPDATED_ADDVALUEBEDNUM);
        assertThat(testBookYst.getAddvaluebedprice()).isEqualTo(UPDATED_ADDVALUEBEDPRICE);
        assertThat(testBookYst.getAddvaluebreakfastnum()).isEqualTo(UPDATED_ADDVALUEBREAKFASTNUM);
        assertThat(testBookYst.getAddvaluebreakfastprice()).isEqualTo(UPDATED_ADDVALUEBREAKFASTPRICE);
        assertThat(testBookYst.getBaseprice()).isEqualTo(UPDATED_BASEPRICE);
        assertThat(testBookYst.getSaleprice()).isEqualTo(UPDATED_SALEPRICE);
        assertThat(testBookYst.getMarketprice()).isEqualTo(UPDATED_MARKETPRICE);
        assertThat(testBookYst.getHotelproductservice()).isEqualTo(UPDATED_HOTELPRODUCTSERVICE);
        assertThat(testBookYst.getHotelproductservicedesc()).isEqualTo(UPDATED_HOTELPRODUCTSERVICEDESC);
        assertThat(testBookYst.getHotelproductid()).isEqualTo(UPDATED_HOTELPRODUCTID);
        assertThat(testBookYst.getRoomid()).isEqualTo(UPDATED_ROOMID);
        assertThat(testBookYst.getHotelid()).isEqualTo(UPDATED_HOTELID);

        // Validate the BookYst in Elasticsearch
        verify(mockBookYstSearchRepository).save(testBookYst);
    }

    @Test
    @Transactional
    void putNonExistingBookYst() throws Exception {
        int databaseSizeBeforeUpdate = bookYstRepository.findAll().size();
        bookYst.setId(count.incrementAndGet());

        // Create the BookYst
        BookYstDTO bookYstDTO = bookYstMapper.toDto(bookYst);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookYstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookYstDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookYstDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookYst in the database
        List<BookYst> bookYstList = bookYstRepository.findAll();
        assertThat(bookYstList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BookYst in Elasticsearch
        verify(mockBookYstSearchRepository, times(0)).save(bookYst);
    }

    @Test
    @Transactional
    void putWithIdMismatchBookYst() throws Exception {
        int databaseSizeBeforeUpdate = bookYstRepository.findAll().size();
        bookYst.setId(count.incrementAndGet());

        // Create the BookYst
        BookYstDTO bookYstDTO = bookYstMapper.toDto(bookYst);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookYstMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookYstDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookYst in the database
        List<BookYst> bookYstList = bookYstRepository.findAll();
        assertThat(bookYstList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BookYst in Elasticsearch
        verify(mockBookYstSearchRepository, times(0)).save(bookYst);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBookYst() throws Exception {
        int databaseSizeBeforeUpdate = bookYstRepository.findAll().size();
        bookYst.setId(count.incrementAndGet());

        // Create the BookYst
        BookYstDTO bookYstDTO = bookYstMapper.toDto(bookYst);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookYstMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookYstDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookYst in the database
        List<BookYst> bookYstList = bookYstRepository.findAll();
        assertThat(bookYstList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BookYst in Elasticsearch
        verify(mockBookYstSearchRepository, times(0)).save(bookYst);
    }

    @Test
    @Transactional
    void partialUpdateBookYstWithPatch() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        int databaseSizeBeforeUpdate = bookYstRepository.findAll().size();

        // Update the bookYst using partial update
        BookYst partialUpdatedBookYst = new BookYst();
        partialUpdatedBookYst.setId(bookYst.getId());

        partialUpdatedBookYst
            .roomcode(UPDATED_ROOMCODE)
            .roomname(UPDATED_ROOMNAME)
            .roomnum(UPDATED_ROOMNUM)
            .bedids(UPDATED_BEDIDS)
            .roomsize(UPDATED_ROOMSIZE)
            .roomfloor(UPDATED_ROOMFLOOR)
            .nettype(UPDATED_NETTYPE)
            .iswindow(UPDATED_ISWINDOW)
            .remark(UPDATED_REMARK)
            .sortid(UPDATED_SORTID)
            .roomstate(UPDATED_ROOMSTATE)
            .source(UPDATED_SOURCE)
            .roomdistribution(UPDATED_ROOMDISTRIBUTION)
            .conditionbeforedays(UPDATED_CONDITIONBEFOREDAYS)
            .conditionleastdays(UPDATED_CONDITIONLEASTDAYS)
            .conditionleastroomnum(UPDATED_CONDITIONLEASTROOMNUM)
            .paymentype(UPDATED_PAYMENTYPE)
            .rateplandesc(UPDATED_RATEPLANDESC)
            .rateplanname(UPDATED_RATEPLANNAME)
            .addvaluebednum(UPDATED_ADDVALUEBEDNUM)
            .addvaluebreakfastprice(UPDATED_ADDVALUEBREAKFASTPRICE)
            .baseprice(UPDATED_BASEPRICE)
            .saleprice(UPDATED_SALEPRICE)
            .hotelproductservicedesc(UPDATED_HOTELPRODUCTSERVICEDESC)
            .roomid(UPDATED_ROOMID);

        restBookYstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookYst.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookYst))
            )
            .andExpect(status().isOk());

        // Validate the BookYst in the database
        List<BookYst> bookYstList = bookYstRepository.findAll();
        assertThat(bookYstList).hasSize(databaseSizeBeforeUpdate);
        BookYst testBookYst = bookYstList.get(bookYstList.size() - 1);
        assertThat(testBookYst.getRoomcode()).isEqualTo(UPDATED_ROOMCODE);
        assertThat(testBookYst.getRoomname()).isEqualTo(UPDATED_ROOMNAME);
        assertThat(testBookYst.getRoomnum()).isEqualTo(UPDATED_ROOMNUM);
        assertThat(testBookYst.getRoomseparatenum()).isEqualTo(DEFAULT_ROOMSEPARATENUM);
        assertThat(testBookYst.getBedids()).isEqualTo(UPDATED_BEDIDS);
        assertThat(testBookYst.getBedsimpledesc()).isEqualTo(DEFAULT_BEDSIMPLEDESC);
        assertThat(testBookYst.getBednum()).isEqualTo(DEFAULT_BEDNUM);
        assertThat(testBookYst.getRoomsize()).isEqualTo(UPDATED_ROOMSIZE);
        assertThat(testBookYst.getRoomfloor()).isEqualTo(UPDATED_ROOMFLOOR);
        assertThat(testBookYst.getNetservice()).isEqualTo(DEFAULT_NETSERVICE);
        assertThat(testBookYst.getNettype()).isEqualTo(UPDATED_NETTYPE);
        assertThat(testBookYst.getIswindow()).isEqualTo(UPDATED_ISWINDOW);
        assertThat(testBookYst.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testBookYst.getSortid()).isEqualTo(UPDATED_SORTID);
        assertThat(testBookYst.getRoomstate()).isEqualTo(UPDATED_ROOMSTATE);
        assertThat(testBookYst.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testBookYst.getRoomamenities()).isEqualTo(DEFAULT_ROOMAMENITIES);
        assertThat(testBookYst.getMaxguestnums()).isEqualTo(DEFAULT_MAXGUESTNUMS);
        assertThat(testBookYst.getRoomdistribution()).isEqualTo(UPDATED_ROOMDISTRIBUTION);
        assertThat(testBookYst.getConditionbeforedays()).isEqualTo(UPDATED_CONDITIONBEFOREDAYS);
        assertThat(testBookYst.getConditionleastdays()).isEqualTo(UPDATED_CONDITIONLEASTDAYS);
        assertThat(testBookYst.getConditionleastroomnum()).isEqualTo(UPDATED_CONDITIONLEASTROOMNUM);
        assertThat(testBookYst.getPaymentype()).isEqualTo(UPDATED_PAYMENTYPE);
        assertThat(testBookYst.getRateplandesc()).isEqualTo(UPDATED_RATEPLANDESC);
        assertThat(testBookYst.getRateplanname()).isEqualTo(UPDATED_RATEPLANNAME);
        assertThat(testBookYst.getRateplanstate()).isEqualTo(DEFAULT_RATEPLANSTATE);
        assertThat(testBookYst.getAddvaluebednum()).isEqualTo(UPDATED_ADDVALUEBEDNUM);
        assertThat(testBookYst.getAddvaluebedprice()).isEqualTo(DEFAULT_ADDVALUEBEDPRICE);
        assertThat(testBookYst.getAddvaluebreakfastnum()).isEqualTo(DEFAULT_ADDVALUEBREAKFASTNUM);
        assertThat(testBookYst.getAddvaluebreakfastprice()).isEqualTo(UPDATED_ADDVALUEBREAKFASTPRICE);
        assertThat(testBookYst.getBaseprice()).isEqualTo(UPDATED_BASEPRICE);
        assertThat(testBookYst.getSaleprice()).isEqualTo(UPDATED_SALEPRICE);
        assertThat(testBookYst.getMarketprice()).isEqualTo(DEFAULT_MARKETPRICE);
        assertThat(testBookYst.getHotelproductservice()).isEqualTo(DEFAULT_HOTELPRODUCTSERVICE);
        assertThat(testBookYst.getHotelproductservicedesc()).isEqualTo(UPDATED_HOTELPRODUCTSERVICEDESC);
        assertThat(testBookYst.getHotelproductid()).isEqualTo(DEFAULT_HOTELPRODUCTID);
        assertThat(testBookYst.getRoomid()).isEqualTo(UPDATED_ROOMID);
        assertThat(testBookYst.getHotelid()).isEqualTo(DEFAULT_HOTELID);
    }

    @Test
    @Transactional
    void fullUpdateBookYstWithPatch() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        int databaseSizeBeforeUpdate = bookYstRepository.findAll().size();

        // Update the bookYst using partial update
        BookYst partialUpdatedBookYst = new BookYst();
        partialUpdatedBookYst.setId(bookYst.getId());

        partialUpdatedBookYst
            .roomcode(UPDATED_ROOMCODE)
            .roomname(UPDATED_ROOMNAME)
            .roomnum(UPDATED_ROOMNUM)
            .roomseparatenum(UPDATED_ROOMSEPARATENUM)
            .bedids(UPDATED_BEDIDS)
            .bedsimpledesc(UPDATED_BEDSIMPLEDESC)
            .bednum(UPDATED_BEDNUM)
            .roomsize(UPDATED_ROOMSIZE)
            .roomfloor(UPDATED_ROOMFLOOR)
            .netservice(UPDATED_NETSERVICE)
            .nettype(UPDATED_NETTYPE)
            .iswindow(UPDATED_ISWINDOW)
            .remark(UPDATED_REMARK)
            .sortid(UPDATED_SORTID)
            .roomstate(UPDATED_ROOMSTATE)
            .source(UPDATED_SOURCE)
            .roomamenities(UPDATED_ROOMAMENITIES)
            .maxguestnums(UPDATED_MAXGUESTNUMS)
            .roomdistribution(UPDATED_ROOMDISTRIBUTION)
            .conditionbeforedays(UPDATED_CONDITIONBEFOREDAYS)
            .conditionleastdays(UPDATED_CONDITIONLEASTDAYS)
            .conditionleastroomnum(UPDATED_CONDITIONLEASTROOMNUM)
            .paymentype(UPDATED_PAYMENTYPE)
            .rateplandesc(UPDATED_RATEPLANDESC)
            .rateplanname(UPDATED_RATEPLANNAME)
            .rateplanstate(UPDATED_RATEPLANSTATE)
            .addvaluebednum(UPDATED_ADDVALUEBEDNUM)
            .addvaluebedprice(UPDATED_ADDVALUEBEDPRICE)
            .addvaluebreakfastnum(UPDATED_ADDVALUEBREAKFASTNUM)
            .addvaluebreakfastprice(UPDATED_ADDVALUEBREAKFASTPRICE)
            .baseprice(UPDATED_BASEPRICE)
            .saleprice(UPDATED_SALEPRICE)
            .marketprice(UPDATED_MARKETPRICE)
            .hotelproductservice(UPDATED_HOTELPRODUCTSERVICE)
            .hotelproductservicedesc(UPDATED_HOTELPRODUCTSERVICEDESC)
            .hotelproductid(UPDATED_HOTELPRODUCTID)
            .roomid(UPDATED_ROOMID)
            .hotelid(UPDATED_HOTELID);

        restBookYstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookYst.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookYst))
            )
            .andExpect(status().isOk());

        // Validate the BookYst in the database
        List<BookYst> bookYstList = bookYstRepository.findAll();
        assertThat(bookYstList).hasSize(databaseSizeBeforeUpdate);
        BookYst testBookYst = bookYstList.get(bookYstList.size() - 1);
        assertThat(testBookYst.getRoomcode()).isEqualTo(UPDATED_ROOMCODE);
        assertThat(testBookYst.getRoomname()).isEqualTo(UPDATED_ROOMNAME);
        assertThat(testBookYst.getRoomnum()).isEqualTo(UPDATED_ROOMNUM);
        assertThat(testBookYst.getRoomseparatenum()).isEqualTo(UPDATED_ROOMSEPARATENUM);
        assertThat(testBookYst.getBedids()).isEqualTo(UPDATED_BEDIDS);
        assertThat(testBookYst.getBedsimpledesc()).isEqualTo(UPDATED_BEDSIMPLEDESC);
        assertThat(testBookYst.getBednum()).isEqualTo(UPDATED_BEDNUM);
        assertThat(testBookYst.getRoomsize()).isEqualTo(UPDATED_ROOMSIZE);
        assertThat(testBookYst.getRoomfloor()).isEqualTo(UPDATED_ROOMFLOOR);
        assertThat(testBookYst.getNetservice()).isEqualTo(UPDATED_NETSERVICE);
        assertThat(testBookYst.getNettype()).isEqualTo(UPDATED_NETTYPE);
        assertThat(testBookYst.getIswindow()).isEqualTo(UPDATED_ISWINDOW);
        assertThat(testBookYst.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testBookYst.getSortid()).isEqualTo(UPDATED_SORTID);
        assertThat(testBookYst.getRoomstate()).isEqualTo(UPDATED_ROOMSTATE);
        assertThat(testBookYst.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testBookYst.getRoomamenities()).isEqualTo(UPDATED_ROOMAMENITIES);
        assertThat(testBookYst.getMaxguestnums()).isEqualTo(UPDATED_MAXGUESTNUMS);
        assertThat(testBookYst.getRoomdistribution()).isEqualTo(UPDATED_ROOMDISTRIBUTION);
        assertThat(testBookYst.getConditionbeforedays()).isEqualTo(UPDATED_CONDITIONBEFOREDAYS);
        assertThat(testBookYst.getConditionleastdays()).isEqualTo(UPDATED_CONDITIONLEASTDAYS);
        assertThat(testBookYst.getConditionleastroomnum()).isEqualTo(UPDATED_CONDITIONLEASTROOMNUM);
        assertThat(testBookYst.getPaymentype()).isEqualTo(UPDATED_PAYMENTYPE);
        assertThat(testBookYst.getRateplandesc()).isEqualTo(UPDATED_RATEPLANDESC);
        assertThat(testBookYst.getRateplanname()).isEqualTo(UPDATED_RATEPLANNAME);
        assertThat(testBookYst.getRateplanstate()).isEqualTo(UPDATED_RATEPLANSTATE);
        assertThat(testBookYst.getAddvaluebednum()).isEqualTo(UPDATED_ADDVALUEBEDNUM);
        assertThat(testBookYst.getAddvaluebedprice()).isEqualTo(UPDATED_ADDVALUEBEDPRICE);
        assertThat(testBookYst.getAddvaluebreakfastnum()).isEqualTo(UPDATED_ADDVALUEBREAKFASTNUM);
        assertThat(testBookYst.getAddvaluebreakfastprice()).isEqualTo(UPDATED_ADDVALUEBREAKFASTPRICE);
        assertThat(testBookYst.getBaseprice()).isEqualTo(UPDATED_BASEPRICE);
        assertThat(testBookYst.getSaleprice()).isEqualTo(UPDATED_SALEPRICE);
        assertThat(testBookYst.getMarketprice()).isEqualTo(UPDATED_MARKETPRICE);
        assertThat(testBookYst.getHotelproductservice()).isEqualTo(UPDATED_HOTELPRODUCTSERVICE);
        assertThat(testBookYst.getHotelproductservicedesc()).isEqualTo(UPDATED_HOTELPRODUCTSERVICEDESC);
        assertThat(testBookYst.getHotelproductid()).isEqualTo(UPDATED_HOTELPRODUCTID);
        assertThat(testBookYst.getRoomid()).isEqualTo(UPDATED_ROOMID);
        assertThat(testBookYst.getHotelid()).isEqualTo(UPDATED_HOTELID);
    }

    @Test
    @Transactional
    void patchNonExistingBookYst() throws Exception {
        int databaseSizeBeforeUpdate = bookYstRepository.findAll().size();
        bookYst.setId(count.incrementAndGet());

        // Create the BookYst
        BookYstDTO bookYstDTO = bookYstMapper.toDto(bookYst);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookYstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bookYstDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookYstDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookYst in the database
        List<BookYst> bookYstList = bookYstRepository.findAll();
        assertThat(bookYstList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BookYst in Elasticsearch
        verify(mockBookYstSearchRepository, times(0)).save(bookYst);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBookYst() throws Exception {
        int databaseSizeBeforeUpdate = bookYstRepository.findAll().size();
        bookYst.setId(count.incrementAndGet());

        // Create the BookYst
        BookYstDTO bookYstDTO = bookYstMapper.toDto(bookYst);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookYstMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookYstDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the BookYst in the database
        List<BookYst> bookYstList = bookYstRepository.findAll();
        assertThat(bookYstList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BookYst in Elasticsearch
        verify(mockBookYstSearchRepository, times(0)).save(bookYst);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBookYst() throws Exception {
        int databaseSizeBeforeUpdate = bookYstRepository.findAll().size();
        bookYst.setId(count.incrementAndGet());

        // Create the BookYst
        BookYstDTO bookYstDTO = bookYstMapper.toDto(bookYst);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookYstMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bookYstDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BookYst in the database
        List<BookYst> bookYstList = bookYstRepository.findAll();
        assertThat(bookYstList).hasSize(databaseSizeBeforeUpdate);

        // Validate the BookYst in Elasticsearch
        verify(mockBookYstSearchRepository, times(0)).save(bookYst);
    }

    @Test
    @Transactional
    void deleteBookYst() throws Exception {
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);

        int databaseSizeBeforeDelete = bookYstRepository.findAll().size();

        // Delete the bookYst
        restBookYstMockMvc
            .perform(delete(ENTITY_API_URL_ID, bookYst.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BookYst> bookYstList = bookYstRepository.findAll();
        assertThat(bookYstList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the BookYst in Elasticsearch
        verify(mockBookYstSearchRepository, times(1)).deleteById(bookYst.getId());
    }

    @Test
    @Transactional
    void searchBookYst() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        bookYstRepository.saveAndFlush(bookYst);
        when(mockBookYstSearchRepository.search(queryStringQuery("id:" + bookYst.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(bookYst), PageRequest.of(0, 1), 1));

        // Search the bookYst
        restBookYstMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + bookYst.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookYst.getId().intValue())))
            .andExpect(jsonPath("$.[*].roomcode").value(hasItem(DEFAULT_ROOMCODE)))
            .andExpect(jsonPath("$.[*].roomname").value(hasItem(DEFAULT_ROOMNAME)))
            .andExpect(jsonPath("$.[*].roomnum").value(hasItem(DEFAULT_ROOMNUM)))
            .andExpect(jsonPath("$.[*].roomseparatenum").value(hasItem(DEFAULT_ROOMSEPARATENUM)))
            .andExpect(jsonPath("$.[*].bedids").value(hasItem(DEFAULT_BEDIDS)))
            .andExpect(jsonPath("$.[*].bedsimpledesc").value(hasItem(DEFAULT_BEDSIMPLEDESC)))
            .andExpect(jsonPath("$.[*].bednum").value(hasItem(DEFAULT_BEDNUM)))
            .andExpect(jsonPath("$.[*].roomsize").value(hasItem(DEFAULT_ROOMSIZE)))
            .andExpect(jsonPath("$.[*].roomfloor").value(hasItem(DEFAULT_ROOMFLOOR)))
            .andExpect(jsonPath("$.[*].netservice").value(hasItem(DEFAULT_NETSERVICE)))
            .andExpect(jsonPath("$.[*].nettype").value(hasItem(DEFAULT_NETTYPE)))
            .andExpect(jsonPath("$.[*].iswindow").value(hasItem(DEFAULT_ISWINDOW)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].sortid").value(hasItem(DEFAULT_SORTID)))
            .andExpect(jsonPath("$.[*].roomstate").value(hasItem(DEFAULT_ROOMSTATE)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].roomamenities").value(hasItem(DEFAULT_ROOMAMENITIES)))
            .andExpect(jsonPath("$.[*].maxguestnums").value(hasItem(DEFAULT_MAXGUESTNUMS)))
            .andExpect(jsonPath("$.[*].roomdistribution").value(hasItem(DEFAULT_ROOMDISTRIBUTION)))
            .andExpect(jsonPath("$.[*].conditionbeforedays").value(hasItem(DEFAULT_CONDITIONBEFOREDAYS)))
            .andExpect(jsonPath("$.[*].conditionleastdays").value(hasItem(DEFAULT_CONDITIONLEASTDAYS)))
            .andExpect(jsonPath("$.[*].conditionleastroomnum").value(hasItem(DEFAULT_CONDITIONLEASTROOMNUM)))
            .andExpect(jsonPath("$.[*].paymentype").value(hasItem(DEFAULT_PAYMENTYPE)))
            .andExpect(jsonPath("$.[*].rateplandesc").value(hasItem(DEFAULT_RATEPLANDESC)))
            .andExpect(jsonPath("$.[*].rateplanname").value(hasItem(DEFAULT_RATEPLANNAME)))
            .andExpect(jsonPath("$.[*].rateplanstate").value(hasItem(DEFAULT_RATEPLANSTATE)))
            .andExpect(jsonPath("$.[*].addvaluebednum").value(hasItem(DEFAULT_ADDVALUEBEDNUM)))
            .andExpect(jsonPath("$.[*].addvaluebedprice").value(hasItem(DEFAULT_ADDVALUEBEDPRICE)))
            .andExpect(jsonPath("$.[*].addvaluebreakfastnum").value(hasItem(DEFAULT_ADDVALUEBREAKFASTNUM)))
            .andExpect(jsonPath("$.[*].addvaluebreakfastprice").value(hasItem(DEFAULT_ADDVALUEBREAKFASTPRICE)))
            .andExpect(jsonPath("$.[*].baseprice").value(hasItem(DEFAULT_BASEPRICE)))
            .andExpect(jsonPath("$.[*].saleprice").value(hasItem(DEFAULT_SALEPRICE)))
            .andExpect(jsonPath("$.[*].marketprice").value(hasItem(DEFAULT_MARKETPRICE)))
            .andExpect(jsonPath("$.[*].hotelproductservice").value(hasItem(DEFAULT_HOTELPRODUCTSERVICE)))
            .andExpect(jsonPath("$.[*].hotelproductservicedesc").value(hasItem(DEFAULT_HOTELPRODUCTSERVICEDESC)))
            .andExpect(jsonPath("$.[*].hotelproductid").value(hasItem(DEFAULT_HOTELPRODUCTID)))
            .andExpect(jsonPath("$.[*].roomid").value(hasItem(DEFAULT_ROOMID)))
            .andExpect(jsonPath("$.[*].hotelid").value(hasItem(DEFAULT_HOTELID)));
    }
}
