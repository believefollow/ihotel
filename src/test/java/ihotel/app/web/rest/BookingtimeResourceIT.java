package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Bookingtime;
import ihotel.app.repository.BookingtimeRepository;
import ihotel.app.repository.search.BookingtimeSearchRepository;
import ihotel.app.service.criteria.BookingtimeCriteria;
import ihotel.app.service.dto.BookingtimeDTO;
import ihotel.app.service.mapper.BookingtimeMapper;
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
 * Integration tests for the {@link BookingtimeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class BookingtimeResourceIT {

    private static final String DEFAULT_BOOKID = "AAAAAAAAAA";
    private static final String UPDATED_BOOKID = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMN = "AAAAAAAAAA";
    private static final String UPDATED_ROOMN = "BBBBBBBBBB";

    private static final Instant DEFAULT_BOOKTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BOOKTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_RTYPE = "AAAAAAAAAA";
    private static final String UPDATED_RTYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_SL = 1L;
    private static final Long UPDATED_SL = 2L;
    private static final Long SMALLER_SL = 1L - 1L;

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final Long DEFAULT_SIGN = 1L;
    private static final Long UPDATED_SIGN = 2L;
    private static final Long SMALLER_SIGN = 1L - 1L;

    private static final Long DEFAULT_RZSIGN = 1L;
    private static final Long UPDATED_RZSIGN = 2L;
    private static final Long SMALLER_RZSIGN = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/bookingtimes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/bookingtimes";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BookingtimeRepository bookingtimeRepository;

    @Autowired
    private BookingtimeMapper bookingtimeMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.BookingtimeSearchRepositoryMockConfiguration
     */
    @Autowired
    private BookingtimeSearchRepository mockBookingtimeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookingtimeMockMvc;

    private Bookingtime bookingtime;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bookingtime createEntity(EntityManager em) {
        Bookingtime bookingtime = new Bookingtime()
            .bookid(DEFAULT_BOOKID)
            .roomn(DEFAULT_ROOMN)
            .booktime(DEFAULT_BOOKTIME)
            .rtype(DEFAULT_RTYPE)
            .sl(DEFAULT_SL)
            .remark(DEFAULT_REMARK)
            .sign(DEFAULT_SIGN)
            .rzsign(DEFAULT_RZSIGN);
        return bookingtime;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Bookingtime createUpdatedEntity(EntityManager em) {
        Bookingtime bookingtime = new Bookingtime()
            .bookid(UPDATED_BOOKID)
            .roomn(UPDATED_ROOMN)
            .booktime(UPDATED_BOOKTIME)
            .rtype(UPDATED_RTYPE)
            .sl(UPDATED_SL)
            .remark(UPDATED_REMARK)
            .sign(UPDATED_SIGN)
            .rzsign(UPDATED_RZSIGN);
        return bookingtime;
    }

    @BeforeEach
    public void initTest() {
        bookingtime = createEntity(em);
    }

    @Test
    @Transactional
    void createBookingtime() throws Exception {
        int databaseSizeBeforeCreate = bookingtimeRepository.findAll().size();
        // Create the Bookingtime
        BookingtimeDTO bookingtimeDTO = bookingtimeMapper.toDto(bookingtime);
        restBookingtimeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookingtimeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Bookingtime in the database
        List<Bookingtime> bookingtimeList = bookingtimeRepository.findAll();
        assertThat(bookingtimeList).hasSize(databaseSizeBeforeCreate + 1);
        Bookingtime testBookingtime = bookingtimeList.get(bookingtimeList.size() - 1);
        assertThat(testBookingtime.getBookid()).isEqualTo(DEFAULT_BOOKID);
        assertThat(testBookingtime.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testBookingtime.getBooktime()).isEqualTo(DEFAULT_BOOKTIME);
        assertThat(testBookingtime.getRtype()).isEqualTo(DEFAULT_RTYPE);
        assertThat(testBookingtime.getSl()).isEqualTo(DEFAULT_SL);
        assertThat(testBookingtime.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testBookingtime.getSign()).isEqualTo(DEFAULT_SIGN);
        assertThat(testBookingtime.getRzsign()).isEqualTo(DEFAULT_RZSIGN);

        // Validate the Bookingtime in Elasticsearch
        verify(mockBookingtimeSearchRepository, times(1)).save(testBookingtime);
    }

    @Test
    @Transactional
    void createBookingtimeWithExistingId() throws Exception {
        // Create the Bookingtime with an existing ID
        bookingtime.setId(1L);
        BookingtimeDTO bookingtimeDTO = bookingtimeMapper.toDto(bookingtime);

        int databaseSizeBeforeCreate = bookingtimeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingtimeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookingtimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bookingtime in the database
        List<Bookingtime> bookingtimeList = bookingtimeRepository.findAll();
        assertThat(bookingtimeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Bookingtime in Elasticsearch
        verify(mockBookingtimeSearchRepository, times(0)).save(bookingtime);
    }

    @Test
    @Transactional
    void getAllBookingtimes() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList
        restBookingtimeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookingtime.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookid").value(hasItem(DEFAULT_BOOKID)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].booktime").value(hasItem(DEFAULT_BOOKTIME.toString())))
            .andExpect(jsonPath("$.[*].rtype").value(hasItem(DEFAULT_RTYPE)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(DEFAULT_SL.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].rzsign").value(hasItem(DEFAULT_RZSIGN.intValue())));
    }

    @Test
    @Transactional
    void getBookingtime() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get the bookingtime
        restBookingtimeMockMvc
            .perform(get(ENTITY_API_URL_ID, bookingtime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(bookingtime.getId().intValue()))
            .andExpect(jsonPath("$.bookid").value(DEFAULT_BOOKID))
            .andExpect(jsonPath("$.roomn").value(DEFAULT_ROOMN))
            .andExpect(jsonPath("$.booktime").value(DEFAULT_BOOKTIME.toString()))
            .andExpect(jsonPath("$.rtype").value(DEFAULT_RTYPE))
            .andExpect(jsonPath("$.sl").value(DEFAULT_SL.intValue()))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.sign").value(DEFAULT_SIGN.intValue()))
            .andExpect(jsonPath("$.rzsign").value(DEFAULT_RZSIGN.intValue()));
    }

    @Test
    @Transactional
    void getBookingtimesByIdFiltering() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        Long id = bookingtime.getId();

        defaultBookingtimeShouldBeFound("id.equals=" + id);
        defaultBookingtimeShouldNotBeFound("id.notEquals=" + id);

        defaultBookingtimeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBookingtimeShouldNotBeFound("id.greaterThan=" + id);

        defaultBookingtimeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBookingtimeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBookingtimesByBookidIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where bookid equals to DEFAULT_BOOKID
        defaultBookingtimeShouldBeFound("bookid.equals=" + DEFAULT_BOOKID);

        // Get all the bookingtimeList where bookid equals to UPDATED_BOOKID
        defaultBookingtimeShouldNotBeFound("bookid.equals=" + UPDATED_BOOKID);
    }

    @Test
    @Transactional
    void getAllBookingtimesByBookidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where bookid not equals to DEFAULT_BOOKID
        defaultBookingtimeShouldNotBeFound("bookid.notEquals=" + DEFAULT_BOOKID);

        // Get all the bookingtimeList where bookid not equals to UPDATED_BOOKID
        defaultBookingtimeShouldBeFound("bookid.notEquals=" + UPDATED_BOOKID);
    }

    @Test
    @Transactional
    void getAllBookingtimesByBookidIsInShouldWork() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where bookid in DEFAULT_BOOKID or UPDATED_BOOKID
        defaultBookingtimeShouldBeFound("bookid.in=" + DEFAULT_BOOKID + "," + UPDATED_BOOKID);

        // Get all the bookingtimeList where bookid equals to UPDATED_BOOKID
        defaultBookingtimeShouldNotBeFound("bookid.in=" + UPDATED_BOOKID);
    }

    @Test
    @Transactional
    void getAllBookingtimesByBookidIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where bookid is not null
        defaultBookingtimeShouldBeFound("bookid.specified=true");

        // Get all the bookingtimeList where bookid is null
        defaultBookingtimeShouldNotBeFound("bookid.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingtimesByBookidContainsSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where bookid contains DEFAULT_BOOKID
        defaultBookingtimeShouldBeFound("bookid.contains=" + DEFAULT_BOOKID);

        // Get all the bookingtimeList where bookid contains UPDATED_BOOKID
        defaultBookingtimeShouldNotBeFound("bookid.contains=" + UPDATED_BOOKID);
    }

    @Test
    @Transactional
    void getAllBookingtimesByBookidNotContainsSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where bookid does not contain DEFAULT_BOOKID
        defaultBookingtimeShouldNotBeFound("bookid.doesNotContain=" + DEFAULT_BOOKID);

        // Get all the bookingtimeList where bookid does not contain UPDATED_BOOKID
        defaultBookingtimeShouldBeFound("bookid.doesNotContain=" + UPDATED_BOOKID);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where roomn equals to DEFAULT_ROOMN
        defaultBookingtimeShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the bookingtimeList where roomn equals to UPDATED_ROOMN
        defaultBookingtimeShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where roomn not equals to DEFAULT_ROOMN
        defaultBookingtimeShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the bookingtimeList where roomn not equals to UPDATED_ROOMN
        defaultBookingtimeShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultBookingtimeShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the bookingtimeList where roomn equals to UPDATED_ROOMN
        defaultBookingtimeShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where roomn is not null
        defaultBookingtimeShouldBeFound("roomn.specified=true");

        // Get all the bookingtimeList where roomn is null
        defaultBookingtimeShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingtimesByRoomnContainsSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where roomn contains DEFAULT_ROOMN
        defaultBookingtimeShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the bookingtimeList where roomn contains UPDATED_ROOMN
        defaultBookingtimeShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where roomn does not contain DEFAULT_ROOMN
        defaultBookingtimeShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the bookingtimeList where roomn does not contain UPDATED_ROOMN
        defaultBookingtimeShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllBookingtimesByBooktimeIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where booktime equals to DEFAULT_BOOKTIME
        defaultBookingtimeShouldBeFound("booktime.equals=" + DEFAULT_BOOKTIME);

        // Get all the bookingtimeList where booktime equals to UPDATED_BOOKTIME
        defaultBookingtimeShouldNotBeFound("booktime.equals=" + UPDATED_BOOKTIME);
    }

    @Test
    @Transactional
    void getAllBookingtimesByBooktimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where booktime not equals to DEFAULT_BOOKTIME
        defaultBookingtimeShouldNotBeFound("booktime.notEquals=" + DEFAULT_BOOKTIME);

        // Get all the bookingtimeList where booktime not equals to UPDATED_BOOKTIME
        defaultBookingtimeShouldBeFound("booktime.notEquals=" + UPDATED_BOOKTIME);
    }

    @Test
    @Transactional
    void getAllBookingtimesByBooktimeIsInShouldWork() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where booktime in DEFAULT_BOOKTIME or UPDATED_BOOKTIME
        defaultBookingtimeShouldBeFound("booktime.in=" + DEFAULT_BOOKTIME + "," + UPDATED_BOOKTIME);

        // Get all the bookingtimeList where booktime equals to UPDATED_BOOKTIME
        defaultBookingtimeShouldNotBeFound("booktime.in=" + UPDATED_BOOKTIME);
    }

    @Test
    @Transactional
    void getAllBookingtimesByBooktimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where booktime is not null
        defaultBookingtimeShouldBeFound("booktime.specified=true");

        // Get all the bookingtimeList where booktime is null
        defaultBookingtimeShouldNotBeFound("booktime.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingtimesByRtypeIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where rtype equals to DEFAULT_RTYPE
        defaultBookingtimeShouldBeFound("rtype.equals=" + DEFAULT_RTYPE);

        // Get all the bookingtimeList where rtype equals to UPDATED_RTYPE
        defaultBookingtimeShouldNotBeFound("rtype.equals=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRtypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where rtype not equals to DEFAULT_RTYPE
        defaultBookingtimeShouldNotBeFound("rtype.notEquals=" + DEFAULT_RTYPE);

        // Get all the bookingtimeList where rtype not equals to UPDATED_RTYPE
        defaultBookingtimeShouldBeFound("rtype.notEquals=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRtypeIsInShouldWork() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where rtype in DEFAULT_RTYPE or UPDATED_RTYPE
        defaultBookingtimeShouldBeFound("rtype.in=" + DEFAULT_RTYPE + "," + UPDATED_RTYPE);

        // Get all the bookingtimeList where rtype equals to UPDATED_RTYPE
        defaultBookingtimeShouldNotBeFound("rtype.in=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRtypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where rtype is not null
        defaultBookingtimeShouldBeFound("rtype.specified=true");

        // Get all the bookingtimeList where rtype is null
        defaultBookingtimeShouldNotBeFound("rtype.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingtimesByRtypeContainsSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where rtype contains DEFAULT_RTYPE
        defaultBookingtimeShouldBeFound("rtype.contains=" + DEFAULT_RTYPE);

        // Get all the bookingtimeList where rtype contains UPDATED_RTYPE
        defaultBookingtimeShouldNotBeFound("rtype.contains=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRtypeNotContainsSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where rtype does not contain DEFAULT_RTYPE
        defaultBookingtimeShouldNotBeFound("rtype.doesNotContain=" + DEFAULT_RTYPE);

        // Get all the bookingtimeList where rtype does not contain UPDATED_RTYPE
        defaultBookingtimeShouldBeFound("rtype.doesNotContain=" + UPDATED_RTYPE);
    }

    @Test
    @Transactional
    void getAllBookingtimesBySlIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sl equals to DEFAULT_SL
        defaultBookingtimeShouldBeFound("sl.equals=" + DEFAULT_SL);

        // Get all the bookingtimeList where sl equals to UPDATED_SL
        defaultBookingtimeShouldNotBeFound("sl.equals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllBookingtimesBySlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sl not equals to DEFAULT_SL
        defaultBookingtimeShouldNotBeFound("sl.notEquals=" + DEFAULT_SL);

        // Get all the bookingtimeList where sl not equals to UPDATED_SL
        defaultBookingtimeShouldBeFound("sl.notEquals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllBookingtimesBySlIsInShouldWork() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sl in DEFAULT_SL or UPDATED_SL
        defaultBookingtimeShouldBeFound("sl.in=" + DEFAULT_SL + "," + UPDATED_SL);

        // Get all the bookingtimeList where sl equals to UPDATED_SL
        defaultBookingtimeShouldNotBeFound("sl.in=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllBookingtimesBySlIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sl is not null
        defaultBookingtimeShouldBeFound("sl.specified=true");

        // Get all the bookingtimeList where sl is null
        defaultBookingtimeShouldNotBeFound("sl.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingtimesBySlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sl is greater than or equal to DEFAULT_SL
        defaultBookingtimeShouldBeFound("sl.greaterThanOrEqual=" + DEFAULT_SL);

        // Get all the bookingtimeList where sl is greater than or equal to UPDATED_SL
        defaultBookingtimeShouldNotBeFound("sl.greaterThanOrEqual=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllBookingtimesBySlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sl is less than or equal to DEFAULT_SL
        defaultBookingtimeShouldBeFound("sl.lessThanOrEqual=" + DEFAULT_SL);

        // Get all the bookingtimeList where sl is less than or equal to SMALLER_SL
        defaultBookingtimeShouldNotBeFound("sl.lessThanOrEqual=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllBookingtimesBySlIsLessThanSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sl is less than DEFAULT_SL
        defaultBookingtimeShouldNotBeFound("sl.lessThan=" + DEFAULT_SL);

        // Get all the bookingtimeList where sl is less than UPDATED_SL
        defaultBookingtimeShouldBeFound("sl.lessThan=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllBookingtimesBySlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sl is greater than DEFAULT_SL
        defaultBookingtimeShouldNotBeFound("sl.greaterThan=" + DEFAULT_SL);

        // Get all the bookingtimeList where sl is greater than SMALLER_SL
        defaultBookingtimeShouldBeFound("sl.greaterThan=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where remark equals to DEFAULT_REMARK
        defaultBookingtimeShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the bookingtimeList where remark equals to UPDATED_REMARK
        defaultBookingtimeShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where remark not equals to DEFAULT_REMARK
        defaultBookingtimeShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the bookingtimeList where remark not equals to UPDATED_REMARK
        defaultBookingtimeShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultBookingtimeShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the bookingtimeList where remark equals to UPDATED_REMARK
        defaultBookingtimeShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where remark is not null
        defaultBookingtimeShouldBeFound("remark.specified=true");

        // Get all the bookingtimeList where remark is null
        defaultBookingtimeShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingtimesByRemarkContainsSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where remark contains DEFAULT_REMARK
        defaultBookingtimeShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the bookingtimeList where remark contains UPDATED_REMARK
        defaultBookingtimeShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where remark does not contain DEFAULT_REMARK
        defaultBookingtimeShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the bookingtimeList where remark does not contain UPDATED_REMARK
        defaultBookingtimeShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllBookingtimesBySignIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sign equals to DEFAULT_SIGN
        defaultBookingtimeShouldBeFound("sign.equals=" + DEFAULT_SIGN);

        // Get all the bookingtimeList where sign equals to UPDATED_SIGN
        defaultBookingtimeShouldNotBeFound("sign.equals=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllBookingtimesBySignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sign not equals to DEFAULT_SIGN
        defaultBookingtimeShouldNotBeFound("sign.notEquals=" + DEFAULT_SIGN);

        // Get all the bookingtimeList where sign not equals to UPDATED_SIGN
        defaultBookingtimeShouldBeFound("sign.notEquals=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllBookingtimesBySignIsInShouldWork() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sign in DEFAULT_SIGN or UPDATED_SIGN
        defaultBookingtimeShouldBeFound("sign.in=" + DEFAULT_SIGN + "," + UPDATED_SIGN);

        // Get all the bookingtimeList where sign equals to UPDATED_SIGN
        defaultBookingtimeShouldNotBeFound("sign.in=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllBookingtimesBySignIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sign is not null
        defaultBookingtimeShouldBeFound("sign.specified=true");

        // Get all the bookingtimeList where sign is null
        defaultBookingtimeShouldNotBeFound("sign.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingtimesBySignIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sign is greater than or equal to DEFAULT_SIGN
        defaultBookingtimeShouldBeFound("sign.greaterThanOrEqual=" + DEFAULT_SIGN);

        // Get all the bookingtimeList where sign is greater than or equal to UPDATED_SIGN
        defaultBookingtimeShouldNotBeFound("sign.greaterThanOrEqual=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllBookingtimesBySignIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sign is less than or equal to DEFAULT_SIGN
        defaultBookingtimeShouldBeFound("sign.lessThanOrEqual=" + DEFAULT_SIGN);

        // Get all the bookingtimeList where sign is less than or equal to SMALLER_SIGN
        defaultBookingtimeShouldNotBeFound("sign.lessThanOrEqual=" + SMALLER_SIGN);
    }

    @Test
    @Transactional
    void getAllBookingtimesBySignIsLessThanSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sign is less than DEFAULT_SIGN
        defaultBookingtimeShouldNotBeFound("sign.lessThan=" + DEFAULT_SIGN);

        // Get all the bookingtimeList where sign is less than UPDATED_SIGN
        defaultBookingtimeShouldBeFound("sign.lessThan=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllBookingtimesBySignIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where sign is greater than DEFAULT_SIGN
        defaultBookingtimeShouldNotBeFound("sign.greaterThan=" + DEFAULT_SIGN);

        // Get all the bookingtimeList where sign is greater than SMALLER_SIGN
        defaultBookingtimeShouldBeFound("sign.greaterThan=" + SMALLER_SIGN);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRzsignIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where rzsign equals to DEFAULT_RZSIGN
        defaultBookingtimeShouldBeFound("rzsign.equals=" + DEFAULT_RZSIGN);

        // Get all the bookingtimeList where rzsign equals to UPDATED_RZSIGN
        defaultBookingtimeShouldNotBeFound("rzsign.equals=" + UPDATED_RZSIGN);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRzsignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where rzsign not equals to DEFAULT_RZSIGN
        defaultBookingtimeShouldNotBeFound("rzsign.notEquals=" + DEFAULT_RZSIGN);

        // Get all the bookingtimeList where rzsign not equals to UPDATED_RZSIGN
        defaultBookingtimeShouldBeFound("rzsign.notEquals=" + UPDATED_RZSIGN);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRzsignIsInShouldWork() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where rzsign in DEFAULT_RZSIGN or UPDATED_RZSIGN
        defaultBookingtimeShouldBeFound("rzsign.in=" + DEFAULT_RZSIGN + "," + UPDATED_RZSIGN);

        // Get all the bookingtimeList where rzsign equals to UPDATED_RZSIGN
        defaultBookingtimeShouldNotBeFound("rzsign.in=" + UPDATED_RZSIGN);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRzsignIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where rzsign is not null
        defaultBookingtimeShouldBeFound("rzsign.specified=true");

        // Get all the bookingtimeList where rzsign is null
        defaultBookingtimeShouldNotBeFound("rzsign.specified=false");
    }

    @Test
    @Transactional
    void getAllBookingtimesByRzsignIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where rzsign is greater than or equal to DEFAULT_RZSIGN
        defaultBookingtimeShouldBeFound("rzsign.greaterThanOrEqual=" + DEFAULT_RZSIGN);

        // Get all the bookingtimeList where rzsign is greater than or equal to UPDATED_RZSIGN
        defaultBookingtimeShouldNotBeFound("rzsign.greaterThanOrEqual=" + UPDATED_RZSIGN);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRzsignIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where rzsign is less than or equal to DEFAULT_RZSIGN
        defaultBookingtimeShouldBeFound("rzsign.lessThanOrEqual=" + DEFAULT_RZSIGN);

        // Get all the bookingtimeList where rzsign is less than or equal to SMALLER_RZSIGN
        defaultBookingtimeShouldNotBeFound("rzsign.lessThanOrEqual=" + SMALLER_RZSIGN);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRzsignIsLessThanSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where rzsign is less than DEFAULT_RZSIGN
        defaultBookingtimeShouldNotBeFound("rzsign.lessThan=" + DEFAULT_RZSIGN);

        // Get all the bookingtimeList where rzsign is less than UPDATED_RZSIGN
        defaultBookingtimeShouldBeFound("rzsign.lessThan=" + UPDATED_RZSIGN);
    }

    @Test
    @Transactional
    void getAllBookingtimesByRzsignIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        // Get all the bookingtimeList where rzsign is greater than DEFAULT_RZSIGN
        defaultBookingtimeShouldNotBeFound("rzsign.greaterThan=" + DEFAULT_RZSIGN);

        // Get all the bookingtimeList where rzsign is greater than SMALLER_RZSIGN
        defaultBookingtimeShouldBeFound("rzsign.greaterThan=" + SMALLER_RZSIGN);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBookingtimeShouldBeFound(String filter) throws Exception {
        restBookingtimeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookingtime.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookid").value(hasItem(DEFAULT_BOOKID)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].booktime").value(hasItem(DEFAULT_BOOKTIME.toString())))
            .andExpect(jsonPath("$.[*].rtype").value(hasItem(DEFAULT_RTYPE)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(DEFAULT_SL.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].rzsign").value(hasItem(DEFAULT_RZSIGN.intValue())));

        // Check, that the count call also returns 1
        restBookingtimeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBookingtimeShouldNotBeFound(String filter) throws Exception {
        restBookingtimeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBookingtimeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBookingtime() throws Exception {
        // Get the bookingtime
        restBookingtimeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBookingtime() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        int databaseSizeBeforeUpdate = bookingtimeRepository.findAll().size();

        // Update the bookingtime
        Bookingtime updatedBookingtime = bookingtimeRepository.findById(bookingtime.getId()).get();
        // Disconnect from session so that the updates on updatedBookingtime are not directly saved in db
        em.detach(updatedBookingtime);
        updatedBookingtime
            .bookid(UPDATED_BOOKID)
            .roomn(UPDATED_ROOMN)
            .booktime(UPDATED_BOOKTIME)
            .rtype(UPDATED_RTYPE)
            .sl(UPDATED_SL)
            .remark(UPDATED_REMARK)
            .sign(UPDATED_SIGN)
            .rzsign(UPDATED_RZSIGN);
        BookingtimeDTO bookingtimeDTO = bookingtimeMapper.toDto(updatedBookingtime);

        restBookingtimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookingtimeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookingtimeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Bookingtime in the database
        List<Bookingtime> bookingtimeList = bookingtimeRepository.findAll();
        assertThat(bookingtimeList).hasSize(databaseSizeBeforeUpdate);
        Bookingtime testBookingtime = bookingtimeList.get(bookingtimeList.size() - 1);
        assertThat(testBookingtime.getBookid()).isEqualTo(UPDATED_BOOKID);
        assertThat(testBookingtime.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testBookingtime.getBooktime()).isEqualTo(UPDATED_BOOKTIME);
        assertThat(testBookingtime.getRtype()).isEqualTo(UPDATED_RTYPE);
        assertThat(testBookingtime.getSl()).isEqualTo(UPDATED_SL);
        assertThat(testBookingtime.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testBookingtime.getSign()).isEqualTo(UPDATED_SIGN);
        assertThat(testBookingtime.getRzsign()).isEqualTo(UPDATED_RZSIGN);

        // Validate the Bookingtime in Elasticsearch
        verify(mockBookingtimeSearchRepository).save(testBookingtime);
    }

    @Test
    @Transactional
    void putNonExistingBookingtime() throws Exception {
        int databaseSizeBeforeUpdate = bookingtimeRepository.findAll().size();
        bookingtime.setId(count.incrementAndGet());

        // Create the Bookingtime
        BookingtimeDTO bookingtimeDTO = bookingtimeMapper.toDto(bookingtime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingtimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, bookingtimeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookingtimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bookingtime in the database
        List<Bookingtime> bookingtimeList = bookingtimeRepository.findAll();
        assertThat(bookingtimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Bookingtime in Elasticsearch
        verify(mockBookingtimeSearchRepository, times(0)).save(bookingtime);
    }

    @Test
    @Transactional
    void putWithIdMismatchBookingtime() throws Exception {
        int databaseSizeBeforeUpdate = bookingtimeRepository.findAll().size();
        bookingtime.setId(count.incrementAndGet());

        // Create the Bookingtime
        BookingtimeDTO bookingtimeDTO = bookingtimeMapper.toDto(bookingtime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingtimeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(bookingtimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bookingtime in the database
        List<Bookingtime> bookingtimeList = bookingtimeRepository.findAll();
        assertThat(bookingtimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Bookingtime in Elasticsearch
        verify(mockBookingtimeSearchRepository, times(0)).save(bookingtime);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBookingtime() throws Exception {
        int databaseSizeBeforeUpdate = bookingtimeRepository.findAll().size();
        bookingtime.setId(count.incrementAndGet());

        // Create the Bookingtime
        BookingtimeDTO bookingtimeDTO = bookingtimeMapper.toDto(bookingtime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingtimeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(bookingtimeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bookingtime in the database
        List<Bookingtime> bookingtimeList = bookingtimeRepository.findAll();
        assertThat(bookingtimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Bookingtime in Elasticsearch
        verify(mockBookingtimeSearchRepository, times(0)).save(bookingtime);
    }

    @Test
    @Transactional
    void partialUpdateBookingtimeWithPatch() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        int databaseSizeBeforeUpdate = bookingtimeRepository.findAll().size();

        // Update the bookingtime using partial update
        Bookingtime partialUpdatedBookingtime = new Bookingtime();
        partialUpdatedBookingtime.setId(bookingtime.getId());

        partialUpdatedBookingtime.bookid(UPDATED_BOOKID).sl(UPDATED_SL).remark(UPDATED_REMARK);

        restBookingtimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookingtime.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookingtime))
            )
            .andExpect(status().isOk());

        // Validate the Bookingtime in the database
        List<Bookingtime> bookingtimeList = bookingtimeRepository.findAll();
        assertThat(bookingtimeList).hasSize(databaseSizeBeforeUpdate);
        Bookingtime testBookingtime = bookingtimeList.get(bookingtimeList.size() - 1);
        assertThat(testBookingtime.getBookid()).isEqualTo(UPDATED_BOOKID);
        assertThat(testBookingtime.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testBookingtime.getBooktime()).isEqualTo(DEFAULT_BOOKTIME);
        assertThat(testBookingtime.getRtype()).isEqualTo(DEFAULT_RTYPE);
        assertThat(testBookingtime.getSl()).isEqualTo(UPDATED_SL);
        assertThat(testBookingtime.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testBookingtime.getSign()).isEqualTo(DEFAULT_SIGN);
        assertThat(testBookingtime.getRzsign()).isEqualTo(DEFAULT_RZSIGN);
    }

    @Test
    @Transactional
    void fullUpdateBookingtimeWithPatch() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        int databaseSizeBeforeUpdate = bookingtimeRepository.findAll().size();

        // Update the bookingtime using partial update
        Bookingtime partialUpdatedBookingtime = new Bookingtime();
        partialUpdatedBookingtime.setId(bookingtime.getId());

        partialUpdatedBookingtime
            .bookid(UPDATED_BOOKID)
            .roomn(UPDATED_ROOMN)
            .booktime(UPDATED_BOOKTIME)
            .rtype(UPDATED_RTYPE)
            .sl(UPDATED_SL)
            .remark(UPDATED_REMARK)
            .sign(UPDATED_SIGN)
            .rzsign(UPDATED_RZSIGN);

        restBookingtimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBookingtime.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBookingtime))
            )
            .andExpect(status().isOk());

        // Validate the Bookingtime in the database
        List<Bookingtime> bookingtimeList = bookingtimeRepository.findAll();
        assertThat(bookingtimeList).hasSize(databaseSizeBeforeUpdate);
        Bookingtime testBookingtime = bookingtimeList.get(bookingtimeList.size() - 1);
        assertThat(testBookingtime.getBookid()).isEqualTo(UPDATED_BOOKID);
        assertThat(testBookingtime.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testBookingtime.getBooktime()).isEqualTo(UPDATED_BOOKTIME);
        assertThat(testBookingtime.getRtype()).isEqualTo(UPDATED_RTYPE);
        assertThat(testBookingtime.getSl()).isEqualTo(UPDATED_SL);
        assertThat(testBookingtime.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testBookingtime.getSign()).isEqualTo(UPDATED_SIGN);
        assertThat(testBookingtime.getRzsign()).isEqualTo(UPDATED_RZSIGN);
    }

    @Test
    @Transactional
    void patchNonExistingBookingtime() throws Exception {
        int databaseSizeBeforeUpdate = bookingtimeRepository.findAll().size();
        bookingtime.setId(count.incrementAndGet());

        // Create the Bookingtime
        BookingtimeDTO bookingtimeDTO = bookingtimeMapper.toDto(bookingtime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingtimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, bookingtimeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookingtimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bookingtime in the database
        List<Bookingtime> bookingtimeList = bookingtimeRepository.findAll();
        assertThat(bookingtimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Bookingtime in Elasticsearch
        verify(mockBookingtimeSearchRepository, times(0)).save(bookingtime);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBookingtime() throws Exception {
        int databaseSizeBeforeUpdate = bookingtimeRepository.findAll().size();
        bookingtime.setId(count.incrementAndGet());

        // Create the Bookingtime
        BookingtimeDTO bookingtimeDTO = bookingtimeMapper.toDto(bookingtime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingtimeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(bookingtimeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Bookingtime in the database
        List<Bookingtime> bookingtimeList = bookingtimeRepository.findAll();
        assertThat(bookingtimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Bookingtime in Elasticsearch
        verify(mockBookingtimeSearchRepository, times(0)).save(bookingtime);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBookingtime() throws Exception {
        int databaseSizeBeforeUpdate = bookingtimeRepository.findAll().size();
        bookingtime.setId(count.incrementAndGet());

        // Create the Bookingtime
        BookingtimeDTO bookingtimeDTO = bookingtimeMapper.toDto(bookingtime);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBookingtimeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(bookingtimeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Bookingtime in the database
        List<Bookingtime> bookingtimeList = bookingtimeRepository.findAll();
        assertThat(bookingtimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Bookingtime in Elasticsearch
        verify(mockBookingtimeSearchRepository, times(0)).save(bookingtime);
    }

    @Test
    @Transactional
    void deleteBookingtime() throws Exception {
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);

        int databaseSizeBeforeDelete = bookingtimeRepository.findAll().size();

        // Delete the bookingtime
        restBookingtimeMockMvc
            .perform(delete(ENTITY_API_URL_ID, bookingtime.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Bookingtime> bookingtimeList = bookingtimeRepository.findAll();
        assertThat(bookingtimeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Bookingtime in Elasticsearch
        verify(mockBookingtimeSearchRepository, times(1)).deleteById(bookingtime.getId());
    }

    @Test
    @Transactional
    void searchBookingtime() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        bookingtimeRepository.saveAndFlush(bookingtime);
        when(mockBookingtimeSearchRepository.search(queryStringQuery("id:" + bookingtime.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(bookingtime), PageRequest.of(0, 1), 1));

        // Search the bookingtime
        restBookingtimeMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + bookingtime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bookingtime.getId().intValue())))
            .andExpect(jsonPath("$.[*].bookid").value(hasItem(DEFAULT_BOOKID)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].booktime").value(hasItem(DEFAULT_BOOKTIME.toString())))
            .andExpect(jsonPath("$.[*].rtype").value(hasItem(DEFAULT_RTYPE)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(DEFAULT_SL.intValue())))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].rzsign").value(hasItem(DEFAULT_RZSIGN.intValue())));
    }
}
