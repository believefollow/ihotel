package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Errlog;
import ihotel.app.repository.ErrlogRepository;
import ihotel.app.repository.search.ErrlogSearchRepository;
import ihotel.app.service.criteria.ErrlogCriteria;
import ihotel.app.service.dto.ErrlogDTO;
import ihotel.app.service.mapper.ErrlogMapper;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ErrlogResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ErrlogResourceIT {

    private static final Long DEFAULT_IDERRLOG = 1L;
    private static final Long UPDATED_IDERRLOG = 2L;
    private static final Long SMALLER_IDERRLOG = 1L - 1L;

    private static final Long DEFAULT_ERRNUMBER = 1L;
    private static final Long UPDATED_ERRNUMBER = 2L;
    private static final Long SMALLER_ERRNUMBER = 1L - 1L;

    private static final String DEFAULT_ERRTEXT = "AAAAAAAAAA";
    private static final String UPDATED_ERRTEXT = "BBBBBBBBBB";

    private static final String DEFAULT_ERRWINDOWMENU = "AAAAAAAAAA";
    private static final String UPDATED_ERRWINDOWMENU = "BBBBBBBBBB";

    private static final String DEFAULT_ERROBJECT = "AAAAAAAAAA";
    private static final String UPDATED_ERROBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_ERREVENT = "AAAAAAAAAA";
    private static final String UPDATED_ERREVENT = "BBBBBBBBBB";

    private static final Long DEFAULT_ERRLINE = 1L;
    private static final Long UPDATED_ERRLINE = 2L;
    private static final Long SMALLER_ERRLINE = 1L - 1L;

    private static final Instant DEFAULT_ERRTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ERRTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_SUMBITSIGN = false;
    private static final Boolean UPDATED_SUMBITSIGN = true;

    private static final String DEFAULT_BMPFILE = "AAAAAAAAAA";
    private static final String UPDATED_BMPFILE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_BMPBLOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_BMPBLOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_BMPBLOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_BMPBLOB_CONTENT_TYPE = "image/png";

    private static final String ENTITY_API_URL = "/api/errlogs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/errlogs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ErrlogRepository errlogRepository;

    @Autowired
    private ErrlogMapper errlogMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.ErrlogSearchRepositoryMockConfiguration
     */
    @Autowired
    private ErrlogSearchRepository mockErrlogSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restErrlogMockMvc;

    private Errlog errlog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Errlog createEntity(EntityManager em) {
        Errlog errlog = new Errlog()
            .iderrlog(DEFAULT_IDERRLOG)
            .errnumber(DEFAULT_ERRNUMBER)
            .errtext(DEFAULT_ERRTEXT)
            .errwindowmenu(DEFAULT_ERRWINDOWMENU)
            .errobject(DEFAULT_ERROBJECT)
            .errevent(DEFAULT_ERREVENT)
            .errline(DEFAULT_ERRLINE)
            .errtime(DEFAULT_ERRTIME)
            .sumbitsign(DEFAULT_SUMBITSIGN)
            .bmpfile(DEFAULT_BMPFILE)
            .bmpblob(DEFAULT_BMPBLOB)
            .bmpblobContentType(DEFAULT_BMPBLOB_CONTENT_TYPE);
        return errlog;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Errlog createUpdatedEntity(EntityManager em) {
        Errlog errlog = new Errlog()
            .iderrlog(UPDATED_IDERRLOG)
            .errnumber(UPDATED_ERRNUMBER)
            .errtext(UPDATED_ERRTEXT)
            .errwindowmenu(UPDATED_ERRWINDOWMENU)
            .errobject(UPDATED_ERROBJECT)
            .errevent(UPDATED_ERREVENT)
            .errline(UPDATED_ERRLINE)
            .errtime(UPDATED_ERRTIME)
            .sumbitsign(UPDATED_SUMBITSIGN)
            .bmpfile(UPDATED_BMPFILE)
            .bmpblob(UPDATED_BMPBLOB)
            .bmpblobContentType(UPDATED_BMPBLOB_CONTENT_TYPE);
        return errlog;
    }

    @BeforeEach
    public void initTest() {
        errlog = createEntity(em);
    }

    @Test
    @Transactional
    void createErrlog() throws Exception {
        int databaseSizeBeforeCreate = errlogRepository.findAll().size();
        // Create the Errlog
        ErrlogDTO errlogDTO = errlogMapper.toDto(errlog);
        restErrlogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(errlogDTO)))
            .andExpect(status().isCreated());

        // Validate the Errlog in the database
        List<Errlog> errlogList = errlogRepository.findAll();
        assertThat(errlogList).hasSize(databaseSizeBeforeCreate + 1);
        Errlog testErrlog = errlogList.get(errlogList.size() - 1);
        assertThat(testErrlog.getIderrlog()).isEqualTo(DEFAULT_IDERRLOG);
        assertThat(testErrlog.getErrnumber()).isEqualTo(DEFAULT_ERRNUMBER);
        assertThat(testErrlog.getErrtext()).isEqualTo(DEFAULT_ERRTEXT);
        assertThat(testErrlog.getErrwindowmenu()).isEqualTo(DEFAULT_ERRWINDOWMENU);
        assertThat(testErrlog.getErrobject()).isEqualTo(DEFAULT_ERROBJECT);
        assertThat(testErrlog.getErrevent()).isEqualTo(DEFAULT_ERREVENT);
        assertThat(testErrlog.getErrline()).isEqualTo(DEFAULT_ERRLINE);
        assertThat(testErrlog.getErrtime()).isEqualTo(DEFAULT_ERRTIME);
        assertThat(testErrlog.getSumbitsign()).isEqualTo(DEFAULT_SUMBITSIGN);
        assertThat(testErrlog.getBmpfile()).isEqualTo(DEFAULT_BMPFILE);
        assertThat(testErrlog.getBmpblob()).isEqualTo(DEFAULT_BMPBLOB);
        assertThat(testErrlog.getBmpblobContentType()).isEqualTo(DEFAULT_BMPBLOB_CONTENT_TYPE);

        // Validate the Errlog in Elasticsearch
        verify(mockErrlogSearchRepository, times(1)).save(testErrlog);
    }

    @Test
    @Transactional
    void createErrlogWithExistingId() throws Exception {
        // Create the Errlog with an existing ID
        errlog.setId(1L);
        ErrlogDTO errlogDTO = errlogMapper.toDto(errlog);

        int databaseSizeBeforeCreate = errlogRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restErrlogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(errlogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Errlog in the database
        List<Errlog> errlogList = errlogRepository.findAll();
        assertThat(errlogList).hasSize(databaseSizeBeforeCreate);

        // Validate the Errlog in Elasticsearch
        verify(mockErrlogSearchRepository, times(0)).save(errlog);
    }

    @Test
    @Transactional
    void checkIderrlogIsRequired() throws Exception {
        int databaseSizeBeforeTest = errlogRepository.findAll().size();
        // set the field null
        errlog.setIderrlog(null);

        // Create the Errlog, which fails.
        ErrlogDTO errlogDTO = errlogMapper.toDto(errlog);

        restErrlogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(errlogDTO)))
            .andExpect(status().isBadRequest());

        List<Errlog> errlogList = errlogRepository.findAll();
        assertThat(errlogList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllErrlogs() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList
        restErrlogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(errlog.getId().intValue())))
            .andExpect(jsonPath("$.[*].iderrlog").value(hasItem(DEFAULT_IDERRLOG.intValue())))
            .andExpect(jsonPath("$.[*].errnumber").value(hasItem(DEFAULT_ERRNUMBER.intValue())))
            .andExpect(jsonPath("$.[*].errtext").value(hasItem(DEFAULT_ERRTEXT)))
            .andExpect(jsonPath("$.[*].errwindowmenu").value(hasItem(DEFAULT_ERRWINDOWMENU)))
            .andExpect(jsonPath("$.[*].errobject").value(hasItem(DEFAULT_ERROBJECT)))
            .andExpect(jsonPath("$.[*].errevent").value(hasItem(DEFAULT_ERREVENT)))
            .andExpect(jsonPath("$.[*].errline").value(hasItem(DEFAULT_ERRLINE.intValue())))
            .andExpect(jsonPath("$.[*].errtime").value(hasItem(DEFAULT_ERRTIME.toString())))
            .andExpect(jsonPath("$.[*].sumbitsign").value(hasItem(DEFAULT_SUMBITSIGN.booleanValue())))
            .andExpect(jsonPath("$.[*].bmpfile").value(hasItem(DEFAULT_BMPFILE)))
            .andExpect(jsonPath("$.[*].bmpblobContentType").value(hasItem(DEFAULT_BMPBLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bmpblob").value(hasItem(Base64Utils.encodeToString(DEFAULT_BMPBLOB))));
    }

    @Test
    @Transactional
    void getErrlog() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get the errlog
        restErrlogMockMvc
            .perform(get(ENTITY_API_URL_ID, errlog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(errlog.getId().intValue()))
            .andExpect(jsonPath("$.iderrlog").value(DEFAULT_IDERRLOG.intValue()))
            .andExpect(jsonPath("$.errnumber").value(DEFAULT_ERRNUMBER.intValue()))
            .andExpect(jsonPath("$.errtext").value(DEFAULT_ERRTEXT))
            .andExpect(jsonPath("$.errwindowmenu").value(DEFAULT_ERRWINDOWMENU))
            .andExpect(jsonPath("$.errobject").value(DEFAULT_ERROBJECT))
            .andExpect(jsonPath("$.errevent").value(DEFAULT_ERREVENT))
            .andExpect(jsonPath("$.errline").value(DEFAULT_ERRLINE.intValue()))
            .andExpect(jsonPath("$.errtime").value(DEFAULT_ERRTIME.toString()))
            .andExpect(jsonPath("$.sumbitsign").value(DEFAULT_SUMBITSIGN.booleanValue()))
            .andExpect(jsonPath("$.bmpfile").value(DEFAULT_BMPFILE))
            .andExpect(jsonPath("$.bmpblobContentType").value(DEFAULT_BMPBLOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.bmpblob").value(Base64Utils.encodeToString(DEFAULT_BMPBLOB)));
    }

    @Test
    @Transactional
    void getErrlogsByIdFiltering() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        Long id = errlog.getId();

        defaultErrlogShouldBeFound("id.equals=" + id);
        defaultErrlogShouldNotBeFound("id.notEquals=" + id);

        defaultErrlogShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultErrlogShouldNotBeFound("id.greaterThan=" + id);

        defaultErrlogShouldBeFound("id.lessThanOrEqual=" + id);
        defaultErrlogShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllErrlogsByIderrlogIsEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where iderrlog equals to DEFAULT_IDERRLOG
        defaultErrlogShouldBeFound("iderrlog.equals=" + DEFAULT_IDERRLOG);

        // Get all the errlogList where iderrlog equals to UPDATED_IDERRLOG
        defaultErrlogShouldNotBeFound("iderrlog.equals=" + UPDATED_IDERRLOG);
    }

    @Test
    @Transactional
    void getAllErrlogsByIderrlogIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where iderrlog not equals to DEFAULT_IDERRLOG
        defaultErrlogShouldNotBeFound("iderrlog.notEquals=" + DEFAULT_IDERRLOG);

        // Get all the errlogList where iderrlog not equals to UPDATED_IDERRLOG
        defaultErrlogShouldBeFound("iderrlog.notEquals=" + UPDATED_IDERRLOG);
    }

    @Test
    @Transactional
    void getAllErrlogsByIderrlogIsInShouldWork() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where iderrlog in DEFAULT_IDERRLOG or UPDATED_IDERRLOG
        defaultErrlogShouldBeFound("iderrlog.in=" + DEFAULT_IDERRLOG + "," + UPDATED_IDERRLOG);

        // Get all the errlogList where iderrlog equals to UPDATED_IDERRLOG
        defaultErrlogShouldNotBeFound("iderrlog.in=" + UPDATED_IDERRLOG);
    }

    @Test
    @Transactional
    void getAllErrlogsByIderrlogIsNullOrNotNull() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where iderrlog is not null
        defaultErrlogShouldBeFound("iderrlog.specified=true");

        // Get all the errlogList where iderrlog is null
        defaultErrlogShouldNotBeFound("iderrlog.specified=false");
    }

    @Test
    @Transactional
    void getAllErrlogsByIderrlogIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where iderrlog is greater than or equal to DEFAULT_IDERRLOG
        defaultErrlogShouldBeFound("iderrlog.greaterThanOrEqual=" + DEFAULT_IDERRLOG);

        // Get all the errlogList where iderrlog is greater than or equal to UPDATED_IDERRLOG
        defaultErrlogShouldNotBeFound("iderrlog.greaterThanOrEqual=" + UPDATED_IDERRLOG);
    }

    @Test
    @Transactional
    void getAllErrlogsByIderrlogIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where iderrlog is less than or equal to DEFAULT_IDERRLOG
        defaultErrlogShouldBeFound("iderrlog.lessThanOrEqual=" + DEFAULT_IDERRLOG);

        // Get all the errlogList where iderrlog is less than or equal to SMALLER_IDERRLOG
        defaultErrlogShouldNotBeFound("iderrlog.lessThanOrEqual=" + SMALLER_IDERRLOG);
    }

    @Test
    @Transactional
    void getAllErrlogsByIderrlogIsLessThanSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where iderrlog is less than DEFAULT_IDERRLOG
        defaultErrlogShouldNotBeFound("iderrlog.lessThan=" + DEFAULT_IDERRLOG);

        // Get all the errlogList where iderrlog is less than UPDATED_IDERRLOG
        defaultErrlogShouldBeFound("iderrlog.lessThan=" + UPDATED_IDERRLOG);
    }

    @Test
    @Transactional
    void getAllErrlogsByIderrlogIsGreaterThanSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where iderrlog is greater than DEFAULT_IDERRLOG
        defaultErrlogShouldNotBeFound("iderrlog.greaterThan=" + DEFAULT_IDERRLOG);

        // Get all the errlogList where iderrlog is greater than SMALLER_IDERRLOG
        defaultErrlogShouldBeFound("iderrlog.greaterThan=" + SMALLER_IDERRLOG);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrnumberIsEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errnumber equals to DEFAULT_ERRNUMBER
        defaultErrlogShouldBeFound("errnumber.equals=" + DEFAULT_ERRNUMBER);

        // Get all the errlogList where errnumber equals to UPDATED_ERRNUMBER
        defaultErrlogShouldNotBeFound("errnumber.equals=" + UPDATED_ERRNUMBER);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrnumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errnumber not equals to DEFAULT_ERRNUMBER
        defaultErrlogShouldNotBeFound("errnumber.notEquals=" + DEFAULT_ERRNUMBER);

        // Get all the errlogList where errnumber not equals to UPDATED_ERRNUMBER
        defaultErrlogShouldBeFound("errnumber.notEquals=" + UPDATED_ERRNUMBER);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrnumberIsInShouldWork() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errnumber in DEFAULT_ERRNUMBER or UPDATED_ERRNUMBER
        defaultErrlogShouldBeFound("errnumber.in=" + DEFAULT_ERRNUMBER + "," + UPDATED_ERRNUMBER);

        // Get all the errlogList where errnumber equals to UPDATED_ERRNUMBER
        defaultErrlogShouldNotBeFound("errnumber.in=" + UPDATED_ERRNUMBER);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrnumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errnumber is not null
        defaultErrlogShouldBeFound("errnumber.specified=true");

        // Get all the errlogList where errnumber is null
        defaultErrlogShouldNotBeFound("errnumber.specified=false");
    }

    @Test
    @Transactional
    void getAllErrlogsByErrnumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errnumber is greater than or equal to DEFAULT_ERRNUMBER
        defaultErrlogShouldBeFound("errnumber.greaterThanOrEqual=" + DEFAULT_ERRNUMBER);

        // Get all the errlogList where errnumber is greater than or equal to UPDATED_ERRNUMBER
        defaultErrlogShouldNotBeFound("errnumber.greaterThanOrEqual=" + UPDATED_ERRNUMBER);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrnumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errnumber is less than or equal to DEFAULT_ERRNUMBER
        defaultErrlogShouldBeFound("errnumber.lessThanOrEqual=" + DEFAULT_ERRNUMBER);

        // Get all the errlogList where errnumber is less than or equal to SMALLER_ERRNUMBER
        defaultErrlogShouldNotBeFound("errnumber.lessThanOrEqual=" + SMALLER_ERRNUMBER);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrnumberIsLessThanSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errnumber is less than DEFAULT_ERRNUMBER
        defaultErrlogShouldNotBeFound("errnumber.lessThan=" + DEFAULT_ERRNUMBER);

        // Get all the errlogList where errnumber is less than UPDATED_ERRNUMBER
        defaultErrlogShouldBeFound("errnumber.lessThan=" + UPDATED_ERRNUMBER);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrnumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errnumber is greater than DEFAULT_ERRNUMBER
        defaultErrlogShouldNotBeFound("errnumber.greaterThan=" + DEFAULT_ERRNUMBER);

        // Get all the errlogList where errnumber is greater than SMALLER_ERRNUMBER
        defaultErrlogShouldBeFound("errnumber.greaterThan=" + SMALLER_ERRNUMBER);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrtextIsEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errtext equals to DEFAULT_ERRTEXT
        defaultErrlogShouldBeFound("errtext.equals=" + DEFAULT_ERRTEXT);

        // Get all the errlogList where errtext equals to UPDATED_ERRTEXT
        defaultErrlogShouldNotBeFound("errtext.equals=" + UPDATED_ERRTEXT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrtextIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errtext not equals to DEFAULT_ERRTEXT
        defaultErrlogShouldNotBeFound("errtext.notEquals=" + DEFAULT_ERRTEXT);

        // Get all the errlogList where errtext not equals to UPDATED_ERRTEXT
        defaultErrlogShouldBeFound("errtext.notEquals=" + UPDATED_ERRTEXT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrtextIsInShouldWork() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errtext in DEFAULT_ERRTEXT or UPDATED_ERRTEXT
        defaultErrlogShouldBeFound("errtext.in=" + DEFAULT_ERRTEXT + "," + UPDATED_ERRTEXT);

        // Get all the errlogList where errtext equals to UPDATED_ERRTEXT
        defaultErrlogShouldNotBeFound("errtext.in=" + UPDATED_ERRTEXT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrtextIsNullOrNotNull() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errtext is not null
        defaultErrlogShouldBeFound("errtext.specified=true");

        // Get all the errlogList where errtext is null
        defaultErrlogShouldNotBeFound("errtext.specified=false");
    }

    @Test
    @Transactional
    void getAllErrlogsByErrtextContainsSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errtext contains DEFAULT_ERRTEXT
        defaultErrlogShouldBeFound("errtext.contains=" + DEFAULT_ERRTEXT);

        // Get all the errlogList where errtext contains UPDATED_ERRTEXT
        defaultErrlogShouldNotBeFound("errtext.contains=" + UPDATED_ERRTEXT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrtextNotContainsSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errtext does not contain DEFAULT_ERRTEXT
        defaultErrlogShouldNotBeFound("errtext.doesNotContain=" + DEFAULT_ERRTEXT);

        // Get all the errlogList where errtext does not contain UPDATED_ERRTEXT
        defaultErrlogShouldBeFound("errtext.doesNotContain=" + UPDATED_ERRTEXT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrwindowmenuIsEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errwindowmenu equals to DEFAULT_ERRWINDOWMENU
        defaultErrlogShouldBeFound("errwindowmenu.equals=" + DEFAULT_ERRWINDOWMENU);

        // Get all the errlogList where errwindowmenu equals to UPDATED_ERRWINDOWMENU
        defaultErrlogShouldNotBeFound("errwindowmenu.equals=" + UPDATED_ERRWINDOWMENU);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrwindowmenuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errwindowmenu not equals to DEFAULT_ERRWINDOWMENU
        defaultErrlogShouldNotBeFound("errwindowmenu.notEquals=" + DEFAULT_ERRWINDOWMENU);

        // Get all the errlogList where errwindowmenu not equals to UPDATED_ERRWINDOWMENU
        defaultErrlogShouldBeFound("errwindowmenu.notEquals=" + UPDATED_ERRWINDOWMENU);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrwindowmenuIsInShouldWork() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errwindowmenu in DEFAULT_ERRWINDOWMENU or UPDATED_ERRWINDOWMENU
        defaultErrlogShouldBeFound("errwindowmenu.in=" + DEFAULT_ERRWINDOWMENU + "," + UPDATED_ERRWINDOWMENU);

        // Get all the errlogList where errwindowmenu equals to UPDATED_ERRWINDOWMENU
        defaultErrlogShouldNotBeFound("errwindowmenu.in=" + UPDATED_ERRWINDOWMENU);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrwindowmenuIsNullOrNotNull() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errwindowmenu is not null
        defaultErrlogShouldBeFound("errwindowmenu.specified=true");

        // Get all the errlogList where errwindowmenu is null
        defaultErrlogShouldNotBeFound("errwindowmenu.specified=false");
    }

    @Test
    @Transactional
    void getAllErrlogsByErrwindowmenuContainsSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errwindowmenu contains DEFAULT_ERRWINDOWMENU
        defaultErrlogShouldBeFound("errwindowmenu.contains=" + DEFAULT_ERRWINDOWMENU);

        // Get all the errlogList where errwindowmenu contains UPDATED_ERRWINDOWMENU
        defaultErrlogShouldNotBeFound("errwindowmenu.contains=" + UPDATED_ERRWINDOWMENU);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrwindowmenuNotContainsSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errwindowmenu does not contain DEFAULT_ERRWINDOWMENU
        defaultErrlogShouldNotBeFound("errwindowmenu.doesNotContain=" + DEFAULT_ERRWINDOWMENU);

        // Get all the errlogList where errwindowmenu does not contain UPDATED_ERRWINDOWMENU
        defaultErrlogShouldBeFound("errwindowmenu.doesNotContain=" + UPDATED_ERRWINDOWMENU);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrobjectIsEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errobject equals to DEFAULT_ERROBJECT
        defaultErrlogShouldBeFound("errobject.equals=" + DEFAULT_ERROBJECT);

        // Get all the errlogList where errobject equals to UPDATED_ERROBJECT
        defaultErrlogShouldNotBeFound("errobject.equals=" + UPDATED_ERROBJECT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrobjectIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errobject not equals to DEFAULT_ERROBJECT
        defaultErrlogShouldNotBeFound("errobject.notEquals=" + DEFAULT_ERROBJECT);

        // Get all the errlogList where errobject not equals to UPDATED_ERROBJECT
        defaultErrlogShouldBeFound("errobject.notEquals=" + UPDATED_ERROBJECT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrobjectIsInShouldWork() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errobject in DEFAULT_ERROBJECT or UPDATED_ERROBJECT
        defaultErrlogShouldBeFound("errobject.in=" + DEFAULT_ERROBJECT + "," + UPDATED_ERROBJECT);

        // Get all the errlogList where errobject equals to UPDATED_ERROBJECT
        defaultErrlogShouldNotBeFound("errobject.in=" + UPDATED_ERROBJECT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrobjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errobject is not null
        defaultErrlogShouldBeFound("errobject.specified=true");

        // Get all the errlogList where errobject is null
        defaultErrlogShouldNotBeFound("errobject.specified=false");
    }

    @Test
    @Transactional
    void getAllErrlogsByErrobjectContainsSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errobject contains DEFAULT_ERROBJECT
        defaultErrlogShouldBeFound("errobject.contains=" + DEFAULT_ERROBJECT);

        // Get all the errlogList where errobject contains UPDATED_ERROBJECT
        defaultErrlogShouldNotBeFound("errobject.contains=" + UPDATED_ERROBJECT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrobjectNotContainsSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errobject does not contain DEFAULT_ERROBJECT
        defaultErrlogShouldNotBeFound("errobject.doesNotContain=" + DEFAULT_ERROBJECT);

        // Get all the errlogList where errobject does not contain UPDATED_ERROBJECT
        defaultErrlogShouldBeFound("errobject.doesNotContain=" + UPDATED_ERROBJECT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErreventIsEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errevent equals to DEFAULT_ERREVENT
        defaultErrlogShouldBeFound("errevent.equals=" + DEFAULT_ERREVENT);

        // Get all the errlogList where errevent equals to UPDATED_ERREVENT
        defaultErrlogShouldNotBeFound("errevent.equals=" + UPDATED_ERREVENT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErreventIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errevent not equals to DEFAULT_ERREVENT
        defaultErrlogShouldNotBeFound("errevent.notEquals=" + DEFAULT_ERREVENT);

        // Get all the errlogList where errevent not equals to UPDATED_ERREVENT
        defaultErrlogShouldBeFound("errevent.notEquals=" + UPDATED_ERREVENT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErreventIsInShouldWork() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errevent in DEFAULT_ERREVENT or UPDATED_ERREVENT
        defaultErrlogShouldBeFound("errevent.in=" + DEFAULT_ERREVENT + "," + UPDATED_ERREVENT);

        // Get all the errlogList where errevent equals to UPDATED_ERREVENT
        defaultErrlogShouldNotBeFound("errevent.in=" + UPDATED_ERREVENT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErreventIsNullOrNotNull() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errevent is not null
        defaultErrlogShouldBeFound("errevent.specified=true");

        // Get all the errlogList where errevent is null
        defaultErrlogShouldNotBeFound("errevent.specified=false");
    }

    @Test
    @Transactional
    void getAllErrlogsByErreventContainsSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errevent contains DEFAULT_ERREVENT
        defaultErrlogShouldBeFound("errevent.contains=" + DEFAULT_ERREVENT);

        // Get all the errlogList where errevent contains UPDATED_ERREVENT
        defaultErrlogShouldNotBeFound("errevent.contains=" + UPDATED_ERREVENT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErreventNotContainsSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errevent does not contain DEFAULT_ERREVENT
        defaultErrlogShouldNotBeFound("errevent.doesNotContain=" + DEFAULT_ERREVENT);

        // Get all the errlogList where errevent does not contain UPDATED_ERREVENT
        defaultErrlogShouldBeFound("errevent.doesNotContain=" + UPDATED_ERREVENT);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrlineIsEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errline equals to DEFAULT_ERRLINE
        defaultErrlogShouldBeFound("errline.equals=" + DEFAULT_ERRLINE);

        // Get all the errlogList where errline equals to UPDATED_ERRLINE
        defaultErrlogShouldNotBeFound("errline.equals=" + UPDATED_ERRLINE);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrlineIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errline not equals to DEFAULT_ERRLINE
        defaultErrlogShouldNotBeFound("errline.notEquals=" + DEFAULT_ERRLINE);

        // Get all the errlogList where errline not equals to UPDATED_ERRLINE
        defaultErrlogShouldBeFound("errline.notEquals=" + UPDATED_ERRLINE);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrlineIsInShouldWork() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errline in DEFAULT_ERRLINE or UPDATED_ERRLINE
        defaultErrlogShouldBeFound("errline.in=" + DEFAULT_ERRLINE + "," + UPDATED_ERRLINE);

        // Get all the errlogList where errline equals to UPDATED_ERRLINE
        defaultErrlogShouldNotBeFound("errline.in=" + UPDATED_ERRLINE);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrlineIsNullOrNotNull() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errline is not null
        defaultErrlogShouldBeFound("errline.specified=true");

        // Get all the errlogList where errline is null
        defaultErrlogShouldNotBeFound("errline.specified=false");
    }

    @Test
    @Transactional
    void getAllErrlogsByErrlineIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errline is greater than or equal to DEFAULT_ERRLINE
        defaultErrlogShouldBeFound("errline.greaterThanOrEqual=" + DEFAULT_ERRLINE);

        // Get all the errlogList where errline is greater than or equal to UPDATED_ERRLINE
        defaultErrlogShouldNotBeFound("errline.greaterThanOrEqual=" + UPDATED_ERRLINE);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrlineIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errline is less than or equal to DEFAULT_ERRLINE
        defaultErrlogShouldBeFound("errline.lessThanOrEqual=" + DEFAULT_ERRLINE);

        // Get all the errlogList where errline is less than or equal to SMALLER_ERRLINE
        defaultErrlogShouldNotBeFound("errline.lessThanOrEqual=" + SMALLER_ERRLINE);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrlineIsLessThanSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errline is less than DEFAULT_ERRLINE
        defaultErrlogShouldNotBeFound("errline.lessThan=" + DEFAULT_ERRLINE);

        // Get all the errlogList where errline is less than UPDATED_ERRLINE
        defaultErrlogShouldBeFound("errline.lessThan=" + UPDATED_ERRLINE);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrlineIsGreaterThanSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errline is greater than DEFAULT_ERRLINE
        defaultErrlogShouldNotBeFound("errline.greaterThan=" + DEFAULT_ERRLINE);

        // Get all the errlogList where errline is greater than SMALLER_ERRLINE
        defaultErrlogShouldBeFound("errline.greaterThan=" + SMALLER_ERRLINE);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrtimeIsEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errtime equals to DEFAULT_ERRTIME
        defaultErrlogShouldBeFound("errtime.equals=" + DEFAULT_ERRTIME);

        // Get all the errlogList where errtime equals to UPDATED_ERRTIME
        defaultErrlogShouldNotBeFound("errtime.equals=" + UPDATED_ERRTIME);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrtimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errtime not equals to DEFAULT_ERRTIME
        defaultErrlogShouldNotBeFound("errtime.notEquals=" + DEFAULT_ERRTIME);

        // Get all the errlogList where errtime not equals to UPDATED_ERRTIME
        defaultErrlogShouldBeFound("errtime.notEquals=" + UPDATED_ERRTIME);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrtimeIsInShouldWork() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errtime in DEFAULT_ERRTIME or UPDATED_ERRTIME
        defaultErrlogShouldBeFound("errtime.in=" + DEFAULT_ERRTIME + "," + UPDATED_ERRTIME);

        // Get all the errlogList where errtime equals to UPDATED_ERRTIME
        defaultErrlogShouldNotBeFound("errtime.in=" + UPDATED_ERRTIME);
    }

    @Test
    @Transactional
    void getAllErrlogsByErrtimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where errtime is not null
        defaultErrlogShouldBeFound("errtime.specified=true");

        // Get all the errlogList where errtime is null
        defaultErrlogShouldNotBeFound("errtime.specified=false");
    }

    @Test
    @Transactional
    void getAllErrlogsBySumbitsignIsEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where sumbitsign equals to DEFAULT_SUMBITSIGN
        defaultErrlogShouldBeFound("sumbitsign.equals=" + DEFAULT_SUMBITSIGN);

        // Get all the errlogList where sumbitsign equals to UPDATED_SUMBITSIGN
        defaultErrlogShouldNotBeFound("sumbitsign.equals=" + UPDATED_SUMBITSIGN);
    }

    @Test
    @Transactional
    void getAllErrlogsBySumbitsignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where sumbitsign not equals to DEFAULT_SUMBITSIGN
        defaultErrlogShouldNotBeFound("sumbitsign.notEquals=" + DEFAULT_SUMBITSIGN);

        // Get all the errlogList where sumbitsign not equals to UPDATED_SUMBITSIGN
        defaultErrlogShouldBeFound("sumbitsign.notEquals=" + UPDATED_SUMBITSIGN);
    }

    @Test
    @Transactional
    void getAllErrlogsBySumbitsignIsInShouldWork() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where sumbitsign in DEFAULT_SUMBITSIGN or UPDATED_SUMBITSIGN
        defaultErrlogShouldBeFound("sumbitsign.in=" + DEFAULT_SUMBITSIGN + "," + UPDATED_SUMBITSIGN);

        // Get all the errlogList where sumbitsign equals to UPDATED_SUMBITSIGN
        defaultErrlogShouldNotBeFound("sumbitsign.in=" + UPDATED_SUMBITSIGN);
    }

    @Test
    @Transactional
    void getAllErrlogsBySumbitsignIsNullOrNotNull() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where sumbitsign is not null
        defaultErrlogShouldBeFound("sumbitsign.specified=true");

        // Get all the errlogList where sumbitsign is null
        defaultErrlogShouldNotBeFound("sumbitsign.specified=false");
    }

    @Test
    @Transactional
    void getAllErrlogsByBmpfileIsEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where bmpfile equals to DEFAULT_BMPFILE
        defaultErrlogShouldBeFound("bmpfile.equals=" + DEFAULT_BMPFILE);

        // Get all the errlogList where bmpfile equals to UPDATED_BMPFILE
        defaultErrlogShouldNotBeFound("bmpfile.equals=" + UPDATED_BMPFILE);
    }

    @Test
    @Transactional
    void getAllErrlogsByBmpfileIsNotEqualToSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where bmpfile not equals to DEFAULT_BMPFILE
        defaultErrlogShouldNotBeFound("bmpfile.notEquals=" + DEFAULT_BMPFILE);

        // Get all the errlogList where bmpfile not equals to UPDATED_BMPFILE
        defaultErrlogShouldBeFound("bmpfile.notEquals=" + UPDATED_BMPFILE);
    }

    @Test
    @Transactional
    void getAllErrlogsByBmpfileIsInShouldWork() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where bmpfile in DEFAULT_BMPFILE or UPDATED_BMPFILE
        defaultErrlogShouldBeFound("bmpfile.in=" + DEFAULT_BMPFILE + "," + UPDATED_BMPFILE);

        // Get all the errlogList where bmpfile equals to UPDATED_BMPFILE
        defaultErrlogShouldNotBeFound("bmpfile.in=" + UPDATED_BMPFILE);
    }

    @Test
    @Transactional
    void getAllErrlogsByBmpfileIsNullOrNotNull() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where bmpfile is not null
        defaultErrlogShouldBeFound("bmpfile.specified=true");

        // Get all the errlogList where bmpfile is null
        defaultErrlogShouldNotBeFound("bmpfile.specified=false");
    }

    @Test
    @Transactional
    void getAllErrlogsByBmpfileContainsSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where bmpfile contains DEFAULT_BMPFILE
        defaultErrlogShouldBeFound("bmpfile.contains=" + DEFAULT_BMPFILE);

        // Get all the errlogList where bmpfile contains UPDATED_BMPFILE
        defaultErrlogShouldNotBeFound("bmpfile.contains=" + UPDATED_BMPFILE);
    }

    @Test
    @Transactional
    void getAllErrlogsByBmpfileNotContainsSomething() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        // Get all the errlogList where bmpfile does not contain DEFAULT_BMPFILE
        defaultErrlogShouldNotBeFound("bmpfile.doesNotContain=" + DEFAULT_BMPFILE);

        // Get all the errlogList where bmpfile does not contain UPDATED_BMPFILE
        defaultErrlogShouldBeFound("bmpfile.doesNotContain=" + UPDATED_BMPFILE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultErrlogShouldBeFound(String filter) throws Exception {
        restErrlogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(errlog.getId().intValue())))
            .andExpect(jsonPath("$.[*].iderrlog").value(hasItem(DEFAULT_IDERRLOG.intValue())))
            .andExpect(jsonPath("$.[*].errnumber").value(hasItem(DEFAULT_ERRNUMBER.intValue())))
            .andExpect(jsonPath("$.[*].errtext").value(hasItem(DEFAULT_ERRTEXT)))
            .andExpect(jsonPath("$.[*].errwindowmenu").value(hasItem(DEFAULT_ERRWINDOWMENU)))
            .andExpect(jsonPath("$.[*].errobject").value(hasItem(DEFAULT_ERROBJECT)))
            .andExpect(jsonPath("$.[*].errevent").value(hasItem(DEFAULT_ERREVENT)))
            .andExpect(jsonPath("$.[*].errline").value(hasItem(DEFAULT_ERRLINE.intValue())))
            .andExpect(jsonPath("$.[*].errtime").value(hasItem(DEFAULT_ERRTIME.toString())))
            .andExpect(jsonPath("$.[*].sumbitsign").value(hasItem(DEFAULT_SUMBITSIGN.booleanValue())))
            .andExpect(jsonPath("$.[*].bmpfile").value(hasItem(DEFAULT_BMPFILE)))
            .andExpect(jsonPath("$.[*].bmpblobContentType").value(hasItem(DEFAULT_BMPBLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bmpblob").value(hasItem(Base64Utils.encodeToString(DEFAULT_BMPBLOB))));

        // Check, that the count call also returns 1
        restErrlogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultErrlogShouldNotBeFound(String filter) throws Exception {
        restErrlogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restErrlogMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingErrlog() throws Exception {
        // Get the errlog
        restErrlogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewErrlog() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        int databaseSizeBeforeUpdate = errlogRepository.findAll().size();

        // Update the errlog
        Errlog updatedErrlog = errlogRepository.findById(errlog.getId()).get();
        // Disconnect from session so that the updates on updatedErrlog are not directly saved in db
        em.detach(updatedErrlog);
        updatedErrlog
            .iderrlog(UPDATED_IDERRLOG)
            .errnumber(UPDATED_ERRNUMBER)
            .errtext(UPDATED_ERRTEXT)
            .errwindowmenu(UPDATED_ERRWINDOWMENU)
            .errobject(UPDATED_ERROBJECT)
            .errevent(UPDATED_ERREVENT)
            .errline(UPDATED_ERRLINE)
            .errtime(UPDATED_ERRTIME)
            .sumbitsign(UPDATED_SUMBITSIGN)
            .bmpfile(UPDATED_BMPFILE)
            .bmpblob(UPDATED_BMPBLOB)
            .bmpblobContentType(UPDATED_BMPBLOB_CONTENT_TYPE);
        ErrlogDTO errlogDTO = errlogMapper.toDto(updatedErrlog);

        restErrlogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, errlogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(errlogDTO))
            )
            .andExpect(status().isOk());

        // Validate the Errlog in the database
        List<Errlog> errlogList = errlogRepository.findAll();
        assertThat(errlogList).hasSize(databaseSizeBeforeUpdate);
        Errlog testErrlog = errlogList.get(errlogList.size() - 1);
        assertThat(testErrlog.getIderrlog()).isEqualTo(UPDATED_IDERRLOG);
        assertThat(testErrlog.getErrnumber()).isEqualTo(UPDATED_ERRNUMBER);
        assertThat(testErrlog.getErrtext()).isEqualTo(UPDATED_ERRTEXT);
        assertThat(testErrlog.getErrwindowmenu()).isEqualTo(UPDATED_ERRWINDOWMENU);
        assertThat(testErrlog.getErrobject()).isEqualTo(UPDATED_ERROBJECT);
        assertThat(testErrlog.getErrevent()).isEqualTo(UPDATED_ERREVENT);
        assertThat(testErrlog.getErrline()).isEqualTo(UPDATED_ERRLINE);
        assertThat(testErrlog.getErrtime()).isEqualTo(UPDATED_ERRTIME);
        assertThat(testErrlog.getSumbitsign()).isEqualTo(UPDATED_SUMBITSIGN);
        assertThat(testErrlog.getBmpfile()).isEqualTo(UPDATED_BMPFILE);
        assertThat(testErrlog.getBmpblob()).isEqualTo(UPDATED_BMPBLOB);
        assertThat(testErrlog.getBmpblobContentType()).isEqualTo(UPDATED_BMPBLOB_CONTENT_TYPE);

        // Validate the Errlog in Elasticsearch
        verify(mockErrlogSearchRepository).save(testErrlog);
    }

    @Test
    @Transactional
    void putNonExistingErrlog() throws Exception {
        int databaseSizeBeforeUpdate = errlogRepository.findAll().size();
        errlog.setId(count.incrementAndGet());

        // Create the Errlog
        ErrlogDTO errlogDTO = errlogMapper.toDto(errlog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restErrlogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, errlogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(errlogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Errlog in the database
        List<Errlog> errlogList = errlogRepository.findAll();
        assertThat(errlogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Errlog in Elasticsearch
        verify(mockErrlogSearchRepository, times(0)).save(errlog);
    }

    @Test
    @Transactional
    void putWithIdMismatchErrlog() throws Exception {
        int databaseSizeBeforeUpdate = errlogRepository.findAll().size();
        errlog.setId(count.incrementAndGet());

        // Create the Errlog
        ErrlogDTO errlogDTO = errlogMapper.toDto(errlog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErrlogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(errlogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Errlog in the database
        List<Errlog> errlogList = errlogRepository.findAll();
        assertThat(errlogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Errlog in Elasticsearch
        verify(mockErrlogSearchRepository, times(0)).save(errlog);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamErrlog() throws Exception {
        int databaseSizeBeforeUpdate = errlogRepository.findAll().size();
        errlog.setId(count.incrementAndGet());

        // Create the Errlog
        ErrlogDTO errlogDTO = errlogMapper.toDto(errlog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErrlogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(errlogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Errlog in the database
        List<Errlog> errlogList = errlogRepository.findAll();
        assertThat(errlogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Errlog in Elasticsearch
        verify(mockErrlogSearchRepository, times(0)).save(errlog);
    }

    @Test
    @Transactional
    void partialUpdateErrlogWithPatch() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        int databaseSizeBeforeUpdate = errlogRepository.findAll().size();

        // Update the errlog using partial update
        Errlog partialUpdatedErrlog = new Errlog();
        partialUpdatedErrlog.setId(errlog.getId());

        partialUpdatedErrlog
            .iderrlog(UPDATED_IDERRLOG)
            .errtext(UPDATED_ERRTEXT)
            .errwindowmenu(UPDATED_ERRWINDOWMENU)
            .errline(UPDATED_ERRLINE)
            .bmpblob(UPDATED_BMPBLOB)
            .bmpblobContentType(UPDATED_BMPBLOB_CONTENT_TYPE);

        restErrlogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedErrlog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedErrlog))
            )
            .andExpect(status().isOk());

        // Validate the Errlog in the database
        List<Errlog> errlogList = errlogRepository.findAll();
        assertThat(errlogList).hasSize(databaseSizeBeforeUpdate);
        Errlog testErrlog = errlogList.get(errlogList.size() - 1);
        assertThat(testErrlog.getIderrlog()).isEqualTo(UPDATED_IDERRLOG);
        assertThat(testErrlog.getErrnumber()).isEqualTo(DEFAULT_ERRNUMBER);
        assertThat(testErrlog.getErrtext()).isEqualTo(UPDATED_ERRTEXT);
        assertThat(testErrlog.getErrwindowmenu()).isEqualTo(UPDATED_ERRWINDOWMENU);
        assertThat(testErrlog.getErrobject()).isEqualTo(DEFAULT_ERROBJECT);
        assertThat(testErrlog.getErrevent()).isEqualTo(DEFAULT_ERREVENT);
        assertThat(testErrlog.getErrline()).isEqualTo(UPDATED_ERRLINE);
        assertThat(testErrlog.getErrtime()).isEqualTo(DEFAULT_ERRTIME);
        assertThat(testErrlog.getSumbitsign()).isEqualTo(DEFAULT_SUMBITSIGN);
        assertThat(testErrlog.getBmpfile()).isEqualTo(DEFAULT_BMPFILE);
        assertThat(testErrlog.getBmpblob()).isEqualTo(UPDATED_BMPBLOB);
        assertThat(testErrlog.getBmpblobContentType()).isEqualTo(UPDATED_BMPBLOB_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateErrlogWithPatch() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        int databaseSizeBeforeUpdate = errlogRepository.findAll().size();

        // Update the errlog using partial update
        Errlog partialUpdatedErrlog = new Errlog();
        partialUpdatedErrlog.setId(errlog.getId());

        partialUpdatedErrlog
            .iderrlog(UPDATED_IDERRLOG)
            .errnumber(UPDATED_ERRNUMBER)
            .errtext(UPDATED_ERRTEXT)
            .errwindowmenu(UPDATED_ERRWINDOWMENU)
            .errobject(UPDATED_ERROBJECT)
            .errevent(UPDATED_ERREVENT)
            .errline(UPDATED_ERRLINE)
            .errtime(UPDATED_ERRTIME)
            .sumbitsign(UPDATED_SUMBITSIGN)
            .bmpfile(UPDATED_BMPFILE)
            .bmpblob(UPDATED_BMPBLOB)
            .bmpblobContentType(UPDATED_BMPBLOB_CONTENT_TYPE);

        restErrlogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedErrlog.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedErrlog))
            )
            .andExpect(status().isOk());

        // Validate the Errlog in the database
        List<Errlog> errlogList = errlogRepository.findAll();
        assertThat(errlogList).hasSize(databaseSizeBeforeUpdate);
        Errlog testErrlog = errlogList.get(errlogList.size() - 1);
        assertThat(testErrlog.getIderrlog()).isEqualTo(UPDATED_IDERRLOG);
        assertThat(testErrlog.getErrnumber()).isEqualTo(UPDATED_ERRNUMBER);
        assertThat(testErrlog.getErrtext()).isEqualTo(UPDATED_ERRTEXT);
        assertThat(testErrlog.getErrwindowmenu()).isEqualTo(UPDATED_ERRWINDOWMENU);
        assertThat(testErrlog.getErrobject()).isEqualTo(UPDATED_ERROBJECT);
        assertThat(testErrlog.getErrevent()).isEqualTo(UPDATED_ERREVENT);
        assertThat(testErrlog.getErrline()).isEqualTo(UPDATED_ERRLINE);
        assertThat(testErrlog.getErrtime()).isEqualTo(UPDATED_ERRTIME);
        assertThat(testErrlog.getSumbitsign()).isEqualTo(UPDATED_SUMBITSIGN);
        assertThat(testErrlog.getBmpfile()).isEqualTo(UPDATED_BMPFILE);
        assertThat(testErrlog.getBmpblob()).isEqualTo(UPDATED_BMPBLOB);
        assertThat(testErrlog.getBmpblobContentType()).isEqualTo(UPDATED_BMPBLOB_CONTENT_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingErrlog() throws Exception {
        int databaseSizeBeforeUpdate = errlogRepository.findAll().size();
        errlog.setId(count.incrementAndGet());

        // Create the Errlog
        ErrlogDTO errlogDTO = errlogMapper.toDto(errlog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restErrlogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, errlogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(errlogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Errlog in the database
        List<Errlog> errlogList = errlogRepository.findAll();
        assertThat(errlogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Errlog in Elasticsearch
        verify(mockErrlogSearchRepository, times(0)).save(errlog);
    }

    @Test
    @Transactional
    void patchWithIdMismatchErrlog() throws Exception {
        int databaseSizeBeforeUpdate = errlogRepository.findAll().size();
        errlog.setId(count.incrementAndGet());

        // Create the Errlog
        ErrlogDTO errlogDTO = errlogMapper.toDto(errlog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErrlogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(errlogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Errlog in the database
        List<Errlog> errlogList = errlogRepository.findAll();
        assertThat(errlogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Errlog in Elasticsearch
        verify(mockErrlogSearchRepository, times(0)).save(errlog);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamErrlog() throws Exception {
        int databaseSizeBeforeUpdate = errlogRepository.findAll().size();
        errlog.setId(count.incrementAndGet());

        // Create the Errlog
        ErrlogDTO errlogDTO = errlogMapper.toDto(errlog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restErrlogMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(errlogDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Errlog in the database
        List<Errlog> errlogList = errlogRepository.findAll();
        assertThat(errlogList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Errlog in Elasticsearch
        verify(mockErrlogSearchRepository, times(0)).save(errlog);
    }

    @Test
    @Transactional
    void deleteErrlog() throws Exception {
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);

        int databaseSizeBeforeDelete = errlogRepository.findAll().size();

        // Delete the errlog
        restErrlogMockMvc
            .perform(delete(ENTITY_API_URL_ID, errlog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Errlog> errlogList = errlogRepository.findAll();
        assertThat(errlogList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Errlog in Elasticsearch
        verify(mockErrlogSearchRepository, times(1)).deleteById(errlog.getId());
    }

    @Test
    @Transactional
    void searchErrlog() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        errlogRepository.saveAndFlush(errlog);
        when(mockErrlogSearchRepository.search(queryStringQuery("id:" + errlog.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(errlog), PageRequest.of(0, 1), 1));

        // Search the errlog
        restErrlogMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + errlog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(errlog.getId().intValue())))
            .andExpect(jsonPath("$.[*].iderrlog").value(hasItem(DEFAULT_IDERRLOG.intValue())))
            .andExpect(jsonPath("$.[*].errnumber").value(hasItem(DEFAULT_ERRNUMBER.intValue())))
            .andExpect(jsonPath("$.[*].errtext").value(hasItem(DEFAULT_ERRTEXT)))
            .andExpect(jsonPath("$.[*].errwindowmenu").value(hasItem(DEFAULT_ERRWINDOWMENU)))
            .andExpect(jsonPath("$.[*].errobject").value(hasItem(DEFAULT_ERROBJECT)))
            .andExpect(jsonPath("$.[*].errevent").value(hasItem(DEFAULT_ERREVENT)))
            .andExpect(jsonPath("$.[*].errline").value(hasItem(DEFAULT_ERRLINE.intValue())))
            .andExpect(jsonPath("$.[*].errtime").value(hasItem(DEFAULT_ERRTIME.toString())))
            .andExpect(jsonPath("$.[*].sumbitsign").value(hasItem(DEFAULT_SUMBITSIGN.booleanValue())))
            .andExpect(jsonPath("$.[*].bmpfile").value(hasItem(DEFAULT_BMPFILE)))
            .andExpect(jsonPath("$.[*].bmpblobContentType").value(hasItem(DEFAULT_BMPBLOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].bmpblob").value(hasItem(Base64Utils.encodeToString(DEFAULT_BMPBLOB))));
    }
}
