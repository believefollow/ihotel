package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.CzCzl2;
import ihotel.app.repository.CzCzl2Repository;
import ihotel.app.repository.search.CzCzl2SearchRepository;
import ihotel.app.service.criteria.CzCzl2Criteria;
import ihotel.app.service.dto.CzCzl2DTO;
import ihotel.app.service.mapper.CzCzl2Mapper;
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
 * Integration tests for the {@link CzCzl2Resource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CzCzl2ResourceIT {

    private static final Instant DEFAULT_DR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_FS = 1L;
    private static final Long UPDATED_FS = 2L;
    private static final Long SMALLER_FS = 1L - 1L;

    private static final BigDecimal DEFAULT_KFL = new BigDecimal(1);
    private static final BigDecimal UPDATED_KFL = new BigDecimal(2);
    private static final BigDecimal SMALLER_KFL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FZSR = new BigDecimal(1);
    private static final BigDecimal UPDATED_FZSR = new BigDecimal(2);
    private static final BigDecimal SMALLER_FZSR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PJZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_PJZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_PJZ = new BigDecimal(1 - 1);

    private static final Long DEFAULT_FS_M = 1L;
    private static final Long UPDATED_FS_M = 2L;
    private static final Long SMALLER_FS_M = 1L - 1L;

    private static final BigDecimal DEFAULT_KFL_M = new BigDecimal(1);
    private static final BigDecimal UPDATED_KFL_M = new BigDecimal(2);
    private static final BigDecimal SMALLER_KFL_M = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FZSR_M = new BigDecimal(1);
    private static final BigDecimal UPDATED_FZSR_M = new BigDecimal(2);
    private static final BigDecimal SMALLER_FZSR_M = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PJZ_M = new BigDecimal(1);
    private static final BigDecimal UPDATED_PJZ_M = new BigDecimal(2);
    private static final BigDecimal SMALLER_PJZ_M = new BigDecimal(1 - 1);

    private static final Long DEFAULT_FS_Y = 1L;
    private static final Long UPDATED_FS_Y = 2L;
    private static final Long SMALLER_FS_Y = 1L - 1L;

    private static final BigDecimal DEFAULT_KFL_Y = new BigDecimal(1);
    private static final BigDecimal UPDATED_KFL_Y = new BigDecimal(2);
    private static final BigDecimal SMALLER_KFL_Y = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FZSR_Y = new BigDecimal(1);
    private static final BigDecimal UPDATED_FZSR_Y = new BigDecimal(2);
    private static final BigDecimal SMALLER_FZSR_Y = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PJZ_Y = new BigDecimal(1);
    private static final BigDecimal UPDATED_PJZ_Y = new BigDecimal(2);
    private static final BigDecimal SMALLER_PJZ_Y = new BigDecimal(1 - 1);

    private static final Long DEFAULT_FS_Q = 1L;
    private static final Long UPDATED_FS_Q = 2L;
    private static final Long SMALLER_FS_Q = 1L - 1L;

    private static final BigDecimal DEFAULT_KFL_Q = new BigDecimal(1);
    private static final BigDecimal UPDATED_KFL_Q = new BigDecimal(2);
    private static final BigDecimal SMALLER_KFL_Q = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FZSR_Q = new BigDecimal(1);
    private static final BigDecimal UPDATED_FZSR_Q = new BigDecimal(2);
    private static final BigDecimal SMALLER_FZSR_Q = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PJZ_Q = new BigDecimal(1);
    private static final BigDecimal UPDATED_PJZ_Q = new BigDecimal(2);
    private static final BigDecimal SMALLER_PJZ_Q = new BigDecimal(1 - 1);

    private static final String DEFAULT_DATE_Y = "AAAAAAAAAA";
    private static final String UPDATED_DATE_Y = "BBBBBBBBBB";

    private static final Instant DEFAULT_DQDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DQDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final Long DEFAULT_NUMBER = 1L;
    private static final Long UPDATED_NUMBER = 2L;
    private static final Long SMALLER_NUMBER = 1L - 1L;

    private static final Long DEFAULT_NUMBER_M = 1L;
    private static final Long UPDATED_NUMBER_M = 2L;
    private static final Long SMALLER_NUMBER_M = 1L - 1L;

    private static final Long DEFAULT_NUMBER_Y = 1L;
    private static final Long UPDATED_NUMBER_Y = 2L;
    private static final Long SMALLER_NUMBER_Y = 1L - 1L;

    private static final String DEFAULT_HOTELDM = "AAAAAAAAAA";
    private static final String UPDATED_HOTELDM = "BBBBBBBBBB";

    private static final Long DEFAULT_ISNEW = 1L;
    private static final Long UPDATED_ISNEW = 2L;
    private static final Long SMALLER_ISNEW = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cz-czl-2-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/cz-czl-2-s";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CzCzl2Repository czCzl2Repository;

    @Autowired
    private CzCzl2Mapper czCzl2Mapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CzCzl2SearchRepositoryMockConfiguration
     */
    @Autowired
    private CzCzl2SearchRepository mockCzCzl2SearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCzCzl2MockMvc;

    private CzCzl2 czCzl2;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CzCzl2 createEntity(EntityManager em) {
        CzCzl2 czCzl2 = new CzCzl2()
            .dr(DEFAULT_DR)
            .type(DEFAULT_TYPE)
            .fs(DEFAULT_FS)
            .kfl(DEFAULT_KFL)
            .fzsr(DEFAULT_FZSR)
            .pjz(DEFAULT_PJZ)
            .fsM(DEFAULT_FS_M)
            .kflM(DEFAULT_KFL_M)
            .fzsrM(DEFAULT_FZSR_M)
            .pjzM(DEFAULT_PJZ_M)
            .fsY(DEFAULT_FS_Y)
            .kflY(DEFAULT_KFL_Y)
            .fzsrY(DEFAULT_FZSR_Y)
            .pjzY(DEFAULT_PJZ_Y)
            .fsQ(DEFAULT_FS_Q)
            .kflQ(DEFAULT_KFL_Q)
            .fzsrQ(DEFAULT_FZSR_Q)
            .pjzQ(DEFAULT_PJZ_Q)
            .dateY(DEFAULT_DATE_Y)
            .dqdate(DEFAULT_DQDATE)
            .empn(DEFAULT_EMPN)
            .number(DEFAULT_NUMBER)
            .numberM(DEFAULT_NUMBER_M)
            .numberY(DEFAULT_NUMBER_Y)
            .hoteldm(DEFAULT_HOTELDM)
            .isnew(DEFAULT_ISNEW);
        return czCzl2;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CzCzl2 createUpdatedEntity(EntityManager em) {
        CzCzl2 czCzl2 = new CzCzl2()
            .dr(UPDATED_DR)
            .type(UPDATED_TYPE)
            .fs(UPDATED_FS)
            .kfl(UPDATED_KFL)
            .fzsr(UPDATED_FZSR)
            .pjz(UPDATED_PJZ)
            .fsM(UPDATED_FS_M)
            .kflM(UPDATED_KFL_M)
            .fzsrM(UPDATED_FZSR_M)
            .pjzM(UPDATED_PJZ_M)
            .fsY(UPDATED_FS_Y)
            .kflY(UPDATED_KFL_Y)
            .fzsrY(UPDATED_FZSR_Y)
            .pjzY(UPDATED_PJZ_Y)
            .fsQ(UPDATED_FS_Q)
            .kflQ(UPDATED_KFL_Q)
            .fzsrQ(UPDATED_FZSR_Q)
            .pjzQ(UPDATED_PJZ_Q)
            .dateY(UPDATED_DATE_Y)
            .dqdate(UPDATED_DQDATE)
            .empn(UPDATED_EMPN)
            .number(UPDATED_NUMBER)
            .numberM(UPDATED_NUMBER_M)
            .numberY(UPDATED_NUMBER_Y)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW);
        return czCzl2;
    }

    @BeforeEach
    public void initTest() {
        czCzl2 = createEntity(em);
    }

    @Test
    @Transactional
    void createCzCzl2() throws Exception {
        int databaseSizeBeforeCreate = czCzl2Repository.findAll().size();
        // Create the CzCzl2
        CzCzl2DTO czCzl2DTO = czCzl2Mapper.toDto(czCzl2);
        restCzCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czCzl2DTO)))
            .andExpect(status().isCreated());

        // Validate the CzCzl2 in the database
        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeCreate + 1);
        CzCzl2 testCzCzl2 = czCzl2List.get(czCzl2List.size() - 1);
        assertThat(testCzCzl2.getDr()).isEqualTo(DEFAULT_DR);
        assertThat(testCzCzl2.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCzCzl2.getFs()).isEqualTo(DEFAULT_FS);
        assertThat(testCzCzl2.getKfl()).isEqualByComparingTo(DEFAULT_KFL);
        assertThat(testCzCzl2.getFzsr()).isEqualByComparingTo(DEFAULT_FZSR);
        assertThat(testCzCzl2.getPjz()).isEqualByComparingTo(DEFAULT_PJZ);
        assertThat(testCzCzl2.getFsM()).isEqualTo(DEFAULT_FS_M);
        assertThat(testCzCzl2.getKflM()).isEqualByComparingTo(DEFAULT_KFL_M);
        assertThat(testCzCzl2.getFzsrM()).isEqualByComparingTo(DEFAULT_FZSR_M);
        assertThat(testCzCzl2.getPjzM()).isEqualByComparingTo(DEFAULT_PJZ_M);
        assertThat(testCzCzl2.getFsY()).isEqualTo(DEFAULT_FS_Y);
        assertThat(testCzCzl2.getKflY()).isEqualByComparingTo(DEFAULT_KFL_Y);
        assertThat(testCzCzl2.getFzsrY()).isEqualByComparingTo(DEFAULT_FZSR_Y);
        assertThat(testCzCzl2.getPjzY()).isEqualByComparingTo(DEFAULT_PJZ_Y);
        assertThat(testCzCzl2.getFsQ()).isEqualTo(DEFAULT_FS_Q);
        assertThat(testCzCzl2.getKflQ()).isEqualByComparingTo(DEFAULT_KFL_Q);
        assertThat(testCzCzl2.getFzsrQ()).isEqualByComparingTo(DEFAULT_FZSR_Q);
        assertThat(testCzCzl2.getPjzQ()).isEqualByComparingTo(DEFAULT_PJZ_Q);
        assertThat(testCzCzl2.getDateY()).isEqualTo(DEFAULT_DATE_Y);
        assertThat(testCzCzl2.getDqdate()).isEqualTo(DEFAULT_DQDATE);
        assertThat(testCzCzl2.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testCzCzl2.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testCzCzl2.getNumberM()).isEqualTo(DEFAULT_NUMBER_M);
        assertThat(testCzCzl2.getNumberY()).isEqualTo(DEFAULT_NUMBER_Y);
        assertThat(testCzCzl2.getHoteldm()).isEqualTo(DEFAULT_HOTELDM);
        assertThat(testCzCzl2.getIsnew()).isEqualTo(DEFAULT_ISNEW);

        // Validate the CzCzl2 in Elasticsearch
        verify(mockCzCzl2SearchRepository, times(1)).save(testCzCzl2);
    }

    @Test
    @Transactional
    void createCzCzl2WithExistingId() throws Exception {
        // Create the CzCzl2 with an existing ID
        czCzl2.setId(1L);
        CzCzl2DTO czCzl2DTO = czCzl2Mapper.toDto(czCzl2);

        int databaseSizeBeforeCreate = czCzl2Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCzCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czCzl2DTO)))
            .andExpect(status().isBadRequest());

        // Validate the CzCzl2 in the database
        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeCreate);

        // Validate the CzCzl2 in Elasticsearch
        verify(mockCzCzl2SearchRepository, times(0)).save(czCzl2);
    }

    @Test
    @Transactional
    void checkNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = czCzl2Repository.findAll().size();
        // set the field null
        czCzl2.setNumber(null);

        // Create the CzCzl2, which fails.
        CzCzl2DTO czCzl2DTO = czCzl2Mapper.toDto(czCzl2);

        restCzCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czCzl2DTO)))
            .andExpect(status().isBadRequest());

        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumberMIsRequired() throws Exception {
        int databaseSizeBeforeTest = czCzl2Repository.findAll().size();
        // set the field null
        czCzl2.setNumberM(null);

        // Create the CzCzl2, which fails.
        CzCzl2DTO czCzl2DTO = czCzl2Mapper.toDto(czCzl2);

        restCzCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czCzl2DTO)))
            .andExpect(status().isBadRequest());

        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNumberYIsRequired() throws Exception {
        int databaseSizeBeforeTest = czCzl2Repository.findAll().size();
        // set the field null
        czCzl2.setNumberY(null);

        // Create the CzCzl2, which fails.
        CzCzl2DTO czCzl2DTO = czCzl2Mapper.toDto(czCzl2);

        restCzCzl2MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czCzl2DTO)))
            .andExpect(status().isBadRequest());

        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCzCzl2s() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List
        restCzCzl2MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(czCzl2.getId().intValue())))
            .andExpect(jsonPath("$.[*].dr").value(hasItem(DEFAULT_DR.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].fs").value(hasItem(DEFAULT_FS.intValue())))
            .andExpect(jsonPath("$.[*].kfl").value(hasItem(sameNumber(DEFAULT_KFL))))
            .andExpect(jsonPath("$.[*].fzsr").value(hasItem(sameNumber(DEFAULT_FZSR))))
            .andExpect(jsonPath("$.[*].pjz").value(hasItem(sameNumber(DEFAULT_PJZ))))
            .andExpect(jsonPath("$.[*].fsM").value(hasItem(DEFAULT_FS_M.intValue())))
            .andExpect(jsonPath("$.[*].kflM").value(hasItem(sameNumber(DEFAULT_KFL_M))))
            .andExpect(jsonPath("$.[*].fzsrM").value(hasItem(sameNumber(DEFAULT_FZSR_M))))
            .andExpect(jsonPath("$.[*].pjzM").value(hasItem(sameNumber(DEFAULT_PJZ_M))))
            .andExpect(jsonPath("$.[*].fsY").value(hasItem(DEFAULT_FS_Y.intValue())))
            .andExpect(jsonPath("$.[*].kflY").value(hasItem(sameNumber(DEFAULT_KFL_Y))))
            .andExpect(jsonPath("$.[*].fzsrY").value(hasItem(sameNumber(DEFAULT_FZSR_Y))))
            .andExpect(jsonPath("$.[*].pjzY").value(hasItem(sameNumber(DEFAULT_PJZ_Y))))
            .andExpect(jsonPath("$.[*].fsQ").value(hasItem(DEFAULT_FS_Q.intValue())))
            .andExpect(jsonPath("$.[*].kflQ").value(hasItem(sameNumber(DEFAULT_KFL_Q))))
            .andExpect(jsonPath("$.[*].fzsrQ").value(hasItem(sameNumber(DEFAULT_FZSR_Q))))
            .andExpect(jsonPath("$.[*].pjzQ").value(hasItem(sameNumber(DEFAULT_PJZ_Q))))
            .andExpect(jsonPath("$.[*].dateY").value(hasItem(DEFAULT_DATE_Y)))
            .andExpect(jsonPath("$.[*].dqdate").value(hasItem(DEFAULT_DQDATE.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].numberM").value(hasItem(DEFAULT_NUMBER_M.intValue())))
            .andExpect(jsonPath("$.[*].numberY").value(hasItem(DEFAULT_NUMBER_Y.intValue())))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())));
    }

    @Test
    @Transactional
    void getCzCzl2() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get the czCzl2
        restCzCzl2MockMvc
            .perform(get(ENTITY_API_URL_ID, czCzl2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(czCzl2.getId().intValue()))
            .andExpect(jsonPath("$.dr").value(DEFAULT_DR.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.fs").value(DEFAULT_FS.intValue()))
            .andExpect(jsonPath("$.kfl").value(sameNumber(DEFAULT_KFL)))
            .andExpect(jsonPath("$.fzsr").value(sameNumber(DEFAULT_FZSR)))
            .andExpect(jsonPath("$.pjz").value(sameNumber(DEFAULT_PJZ)))
            .andExpect(jsonPath("$.fsM").value(DEFAULT_FS_M.intValue()))
            .andExpect(jsonPath("$.kflM").value(sameNumber(DEFAULT_KFL_M)))
            .andExpect(jsonPath("$.fzsrM").value(sameNumber(DEFAULT_FZSR_M)))
            .andExpect(jsonPath("$.pjzM").value(sameNumber(DEFAULT_PJZ_M)))
            .andExpect(jsonPath("$.fsY").value(DEFAULT_FS_Y.intValue()))
            .andExpect(jsonPath("$.kflY").value(sameNumber(DEFAULT_KFL_Y)))
            .andExpect(jsonPath("$.fzsrY").value(sameNumber(DEFAULT_FZSR_Y)))
            .andExpect(jsonPath("$.pjzY").value(sameNumber(DEFAULT_PJZ_Y)))
            .andExpect(jsonPath("$.fsQ").value(DEFAULT_FS_Q.intValue()))
            .andExpect(jsonPath("$.kflQ").value(sameNumber(DEFAULT_KFL_Q)))
            .andExpect(jsonPath("$.fzsrQ").value(sameNumber(DEFAULT_FZSR_Q)))
            .andExpect(jsonPath("$.pjzQ").value(sameNumber(DEFAULT_PJZ_Q)))
            .andExpect(jsonPath("$.dateY").value(DEFAULT_DATE_Y))
            .andExpect(jsonPath("$.dqdate").value(DEFAULT_DQDATE.toString()))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER.intValue()))
            .andExpect(jsonPath("$.numberM").value(DEFAULT_NUMBER_M.intValue()))
            .andExpect(jsonPath("$.numberY").value(DEFAULT_NUMBER_Y.intValue()))
            .andExpect(jsonPath("$.hoteldm").value(DEFAULT_HOTELDM))
            .andExpect(jsonPath("$.isnew").value(DEFAULT_ISNEW.intValue()));
    }

    @Test
    @Transactional
    void getCzCzl2sByIdFiltering() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        Long id = czCzl2.getId();

        defaultCzCzl2ShouldBeFound("id.equals=" + id);
        defaultCzCzl2ShouldNotBeFound("id.notEquals=" + id);

        defaultCzCzl2ShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCzCzl2ShouldNotBeFound("id.greaterThan=" + id);

        defaultCzCzl2ShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCzCzl2ShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByDrIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where dr equals to DEFAULT_DR
        defaultCzCzl2ShouldBeFound("dr.equals=" + DEFAULT_DR);

        // Get all the czCzl2List where dr equals to UPDATED_DR
        defaultCzCzl2ShouldNotBeFound("dr.equals=" + UPDATED_DR);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByDrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where dr not equals to DEFAULT_DR
        defaultCzCzl2ShouldNotBeFound("dr.notEquals=" + DEFAULT_DR);

        // Get all the czCzl2List where dr not equals to UPDATED_DR
        defaultCzCzl2ShouldBeFound("dr.notEquals=" + UPDATED_DR);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByDrIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where dr in DEFAULT_DR or UPDATED_DR
        defaultCzCzl2ShouldBeFound("dr.in=" + DEFAULT_DR + "," + UPDATED_DR);

        // Get all the czCzl2List where dr equals to UPDATED_DR
        defaultCzCzl2ShouldNotBeFound("dr.in=" + UPDATED_DR);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByDrIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where dr is not null
        defaultCzCzl2ShouldBeFound("dr.specified=true");

        // Get all the czCzl2List where dr is null
        defaultCzCzl2ShouldNotBeFound("dr.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where type equals to DEFAULT_TYPE
        defaultCzCzl2ShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the czCzl2List where type equals to UPDATED_TYPE
        defaultCzCzl2ShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where type not equals to DEFAULT_TYPE
        defaultCzCzl2ShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the czCzl2List where type not equals to UPDATED_TYPE
        defaultCzCzl2ShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultCzCzl2ShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the czCzl2List where type equals to UPDATED_TYPE
        defaultCzCzl2ShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where type is not null
        defaultCzCzl2ShouldBeFound("type.specified=true");

        // Get all the czCzl2List where type is null
        defaultCzCzl2ShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByTypeContainsSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where type contains DEFAULT_TYPE
        defaultCzCzl2ShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the czCzl2List where type contains UPDATED_TYPE
        defaultCzCzl2ShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where type does not contain DEFAULT_TYPE
        defaultCzCzl2ShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the czCzl2List where type does not contain UPDATED_TYPE
        defaultCzCzl2ShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fs equals to DEFAULT_FS
        defaultCzCzl2ShouldBeFound("fs.equals=" + DEFAULT_FS);

        // Get all the czCzl2List where fs equals to UPDATED_FS
        defaultCzCzl2ShouldNotBeFound("fs.equals=" + UPDATED_FS);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fs not equals to DEFAULT_FS
        defaultCzCzl2ShouldNotBeFound("fs.notEquals=" + DEFAULT_FS);

        // Get all the czCzl2List where fs not equals to UPDATED_FS
        defaultCzCzl2ShouldBeFound("fs.notEquals=" + UPDATED_FS);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fs in DEFAULT_FS or UPDATED_FS
        defaultCzCzl2ShouldBeFound("fs.in=" + DEFAULT_FS + "," + UPDATED_FS);

        // Get all the czCzl2List where fs equals to UPDATED_FS
        defaultCzCzl2ShouldNotBeFound("fs.in=" + UPDATED_FS);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fs is not null
        defaultCzCzl2ShouldBeFound("fs.specified=true");

        // Get all the czCzl2List where fs is null
        defaultCzCzl2ShouldNotBeFound("fs.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fs is greater than or equal to DEFAULT_FS
        defaultCzCzl2ShouldBeFound("fs.greaterThanOrEqual=" + DEFAULT_FS);

        // Get all the czCzl2List where fs is greater than or equal to UPDATED_FS
        defaultCzCzl2ShouldNotBeFound("fs.greaterThanOrEqual=" + UPDATED_FS);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fs is less than or equal to DEFAULT_FS
        defaultCzCzl2ShouldBeFound("fs.lessThanOrEqual=" + DEFAULT_FS);

        // Get all the czCzl2List where fs is less than or equal to SMALLER_FS
        defaultCzCzl2ShouldNotBeFound("fs.lessThanOrEqual=" + SMALLER_FS);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fs is less than DEFAULT_FS
        defaultCzCzl2ShouldNotBeFound("fs.lessThan=" + DEFAULT_FS);

        // Get all the czCzl2List where fs is less than UPDATED_FS
        defaultCzCzl2ShouldBeFound("fs.lessThan=" + UPDATED_FS);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fs is greater than DEFAULT_FS
        defaultCzCzl2ShouldNotBeFound("fs.greaterThan=" + DEFAULT_FS);

        // Get all the czCzl2List where fs is greater than SMALLER_FS
        defaultCzCzl2ShouldBeFound("fs.greaterThan=" + SMALLER_FS);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kfl equals to DEFAULT_KFL
        defaultCzCzl2ShouldBeFound("kfl.equals=" + DEFAULT_KFL);

        // Get all the czCzl2List where kfl equals to UPDATED_KFL
        defaultCzCzl2ShouldNotBeFound("kfl.equals=" + UPDATED_KFL);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kfl not equals to DEFAULT_KFL
        defaultCzCzl2ShouldNotBeFound("kfl.notEquals=" + DEFAULT_KFL);

        // Get all the czCzl2List where kfl not equals to UPDATED_KFL
        defaultCzCzl2ShouldBeFound("kfl.notEquals=" + UPDATED_KFL);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kfl in DEFAULT_KFL or UPDATED_KFL
        defaultCzCzl2ShouldBeFound("kfl.in=" + DEFAULT_KFL + "," + UPDATED_KFL);

        // Get all the czCzl2List where kfl equals to UPDATED_KFL
        defaultCzCzl2ShouldNotBeFound("kfl.in=" + UPDATED_KFL);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kfl is not null
        defaultCzCzl2ShouldBeFound("kfl.specified=true");

        // Get all the czCzl2List where kfl is null
        defaultCzCzl2ShouldNotBeFound("kfl.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kfl is greater than or equal to DEFAULT_KFL
        defaultCzCzl2ShouldBeFound("kfl.greaterThanOrEqual=" + DEFAULT_KFL);

        // Get all the czCzl2List where kfl is greater than or equal to UPDATED_KFL
        defaultCzCzl2ShouldNotBeFound("kfl.greaterThanOrEqual=" + UPDATED_KFL);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kfl is less than or equal to DEFAULT_KFL
        defaultCzCzl2ShouldBeFound("kfl.lessThanOrEqual=" + DEFAULT_KFL);

        // Get all the czCzl2List where kfl is less than or equal to SMALLER_KFL
        defaultCzCzl2ShouldNotBeFound("kfl.lessThanOrEqual=" + SMALLER_KFL);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kfl is less than DEFAULT_KFL
        defaultCzCzl2ShouldNotBeFound("kfl.lessThan=" + DEFAULT_KFL);

        // Get all the czCzl2List where kfl is less than UPDATED_KFL
        defaultCzCzl2ShouldBeFound("kfl.lessThan=" + UPDATED_KFL);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kfl is greater than DEFAULT_KFL
        defaultCzCzl2ShouldNotBeFound("kfl.greaterThan=" + DEFAULT_KFL);

        // Get all the czCzl2List where kfl is greater than SMALLER_KFL
        defaultCzCzl2ShouldBeFound("kfl.greaterThan=" + SMALLER_KFL);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsr equals to DEFAULT_FZSR
        defaultCzCzl2ShouldBeFound("fzsr.equals=" + DEFAULT_FZSR);

        // Get all the czCzl2List where fzsr equals to UPDATED_FZSR
        defaultCzCzl2ShouldNotBeFound("fzsr.equals=" + UPDATED_FZSR);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsr not equals to DEFAULT_FZSR
        defaultCzCzl2ShouldNotBeFound("fzsr.notEquals=" + DEFAULT_FZSR);

        // Get all the czCzl2List where fzsr not equals to UPDATED_FZSR
        defaultCzCzl2ShouldBeFound("fzsr.notEquals=" + UPDATED_FZSR);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsr in DEFAULT_FZSR or UPDATED_FZSR
        defaultCzCzl2ShouldBeFound("fzsr.in=" + DEFAULT_FZSR + "," + UPDATED_FZSR);

        // Get all the czCzl2List where fzsr equals to UPDATED_FZSR
        defaultCzCzl2ShouldNotBeFound("fzsr.in=" + UPDATED_FZSR);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsr is not null
        defaultCzCzl2ShouldBeFound("fzsr.specified=true");

        // Get all the czCzl2List where fzsr is null
        defaultCzCzl2ShouldNotBeFound("fzsr.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsr is greater than or equal to DEFAULT_FZSR
        defaultCzCzl2ShouldBeFound("fzsr.greaterThanOrEqual=" + DEFAULT_FZSR);

        // Get all the czCzl2List where fzsr is greater than or equal to UPDATED_FZSR
        defaultCzCzl2ShouldNotBeFound("fzsr.greaterThanOrEqual=" + UPDATED_FZSR);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsr is less than or equal to DEFAULT_FZSR
        defaultCzCzl2ShouldBeFound("fzsr.lessThanOrEqual=" + DEFAULT_FZSR);

        // Get all the czCzl2List where fzsr is less than or equal to SMALLER_FZSR
        defaultCzCzl2ShouldNotBeFound("fzsr.lessThanOrEqual=" + SMALLER_FZSR);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsr is less than DEFAULT_FZSR
        defaultCzCzl2ShouldNotBeFound("fzsr.lessThan=" + DEFAULT_FZSR);

        // Get all the czCzl2List where fzsr is less than UPDATED_FZSR
        defaultCzCzl2ShouldBeFound("fzsr.lessThan=" + UPDATED_FZSR);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsr is greater than DEFAULT_FZSR
        defaultCzCzl2ShouldNotBeFound("fzsr.greaterThan=" + DEFAULT_FZSR);

        // Get all the czCzl2List where fzsr is greater than SMALLER_FZSR
        defaultCzCzl2ShouldBeFound("fzsr.greaterThan=" + SMALLER_FZSR);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjz equals to DEFAULT_PJZ
        defaultCzCzl2ShouldBeFound("pjz.equals=" + DEFAULT_PJZ);

        // Get all the czCzl2List where pjz equals to UPDATED_PJZ
        defaultCzCzl2ShouldNotBeFound("pjz.equals=" + UPDATED_PJZ);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjz not equals to DEFAULT_PJZ
        defaultCzCzl2ShouldNotBeFound("pjz.notEquals=" + DEFAULT_PJZ);

        // Get all the czCzl2List where pjz not equals to UPDATED_PJZ
        defaultCzCzl2ShouldBeFound("pjz.notEquals=" + UPDATED_PJZ);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjz in DEFAULT_PJZ or UPDATED_PJZ
        defaultCzCzl2ShouldBeFound("pjz.in=" + DEFAULT_PJZ + "," + UPDATED_PJZ);

        // Get all the czCzl2List where pjz equals to UPDATED_PJZ
        defaultCzCzl2ShouldNotBeFound("pjz.in=" + UPDATED_PJZ);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjz is not null
        defaultCzCzl2ShouldBeFound("pjz.specified=true");

        // Get all the czCzl2List where pjz is null
        defaultCzCzl2ShouldNotBeFound("pjz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjz is greater than or equal to DEFAULT_PJZ
        defaultCzCzl2ShouldBeFound("pjz.greaterThanOrEqual=" + DEFAULT_PJZ);

        // Get all the czCzl2List where pjz is greater than or equal to UPDATED_PJZ
        defaultCzCzl2ShouldNotBeFound("pjz.greaterThanOrEqual=" + UPDATED_PJZ);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjz is less than or equal to DEFAULT_PJZ
        defaultCzCzl2ShouldBeFound("pjz.lessThanOrEqual=" + DEFAULT_PJZ);

        // Get all the czCzl2List where pjz is less than or equal to SMALLER_PJZ
        defaultCzCzl2ShouldNotBeFound("pjz.lessThanOrEqual=" + SMALLER_PJZ);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjz is less than DEFAULT_PJZ
        defaultCzCzl2ShouldNotBeFound("pjz.lessThan=" + DEFAULT_PJZ);

        // Get all the czCzl2List where pjz is less than UPDATED_PJZ
        defaultCzCzl2ShouldBeFound("pjz.lessThan=" + UPDATED_PJZ);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjz is greater than DEFAULT_PJZ
        defaultCzCzl2ShouldNotBeFound("pjz.greaterThan=" + DEFAULT_PJZ);

        // Get all the czCzl2List where pjz is greater than SMALLER_PJZ
        defaultCzCzl2ShouldBeFound("pjz.greaterThan=" + SMALLER_PJZ);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsMIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsM equals to DEFAULT_FS_M
        defaultCzCzl2ShouldBeFound("fsM.equals=" + DEFAULT_FS_M);

        // Get all the czCzl2List where fsM equals to UPDATED_FS_M
        defaultCzCzl2ShouldNotBeFound("fsM.equals=" + UPDATED_FS_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsMIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsM not equals to DEFAULT_FS_M
        defaultCzCzl2ShouldNotBeFound("fsM.notEquals=" + DEFAULT_FS_M);

        // Get all the czCzl2List where fsM not equals to UPDATED_FS_M
        defaultCzCzl2ShouldBeFound("fsM.notEquals=" + UPDATED_FS_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsMIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsM in DEFAULT_FS_M or UPDATED_FS_M
        defaultCzCzl2ShouldBeFound("fsM.in=" + DEFAULT_FS_M + "," + UPDATED_FS_M);

        // Get all the czCzl2List where fsM equals to UPDATED_FS_M
        defaultCzCzl2ShouldNotBeFound("fsM.in=" + UPDATED_FS_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsMIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsM is not null
        defaultCzCzl2ShouldBeFound("fsM.specified=true");

        // Get all the czCzl2List where fsM is null
        defaultCzCzl2ShouldNotBeFound("fsM.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsMIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsM is greater than or equal to DEFAULT_FS_M
        defaultCzCzl2ShouldBeFound("fsM.greaterThanOrEqual=" + DEFAULT_FS_M);

        // Get all the czCzl2List where fsM is greater than or equal to UPDATED_FS_M
        defaultCzCzl2ShouldNotBeFound("fsM.greaterThanOrEqual=" + UPDATED_FS_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsMIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsM is less than or equal to DEFAULT_FS_M
        defaultCzCzl2ShouldBeFound("fsM.lessThanOrEqual=" + DEFAULT_FS_M);

        // Get all the czCzl2List where fsM is less than or equal to SMALLER_FS_M
        defaultCzCzl2ShouldNotBeFound("fsM.lessThanOrEqual=" + SMALLER_FS_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsMIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsM is less than DEFAULT_FS_M
        defaultCzCzl2ShouldNotBeFound("fsM.lessThan=" + DEFAULT_FS_M);

        // Get all the czCzl2List where fsM is less than UPDATED_FS_M
        defaultCzCzl2ShouldBeFound("fsM.lessThan=" + UPDATED_FS_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsMIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsM is greater than DEFAULT_FS_M
        defaultCzCzl2ShouldNotBeFound("fsM.greaterThan=" + DEFAULT_FS_M);

        // Get all the czCzl2List where fsM is greater than SMALLER_FS_M
        defaultCzCzl2ShouldBeFound("fsM.greaterThan=" + SMALLER_FS_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflMIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflM equals to DEFAULT_KFL_M
        defaultCzCzl2ShouldBeFound("kflM.equals=" + DEFAULT_KFL_M);

        // Get all the czCzl2List where kflM equals to UPDATED_KFL_M
        defaultCzCzl2ShouldNotBeFound("kflM.equals=" + UPDATED_KFL_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflMIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflM not equals to DEFAULT_KFL_M
        defaultCzCzl2ShouldNotBeFound("kflM.notEquals=" + DEFAULT_KFL_M);

        // Get all the czCzl2List where kflM not equals to UPDATED_KFL_M
        defaultCzCzl2ShouldBeFound("kflM.notEquals=" + UPDATED_KFL_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflMIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflM in DEFAULT_KFL_M or UPDATED_KFL_M
        defaultCzCzl2ShouldBeFound("kflM.in=" + DEFAULT_KFL_M + "," + UPDATED_KFL_M);

        // Get all the czCzl2List where kflM equals to UPDATED_KFL_M
        defaultCzCzl2ShouldNotBeFound("kflM.in=" + UPDATED_KFL_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflMIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflM is not null
        defaultCzCzl2ShouldBeFound("kflM.specified=true");

        // Get all the czCzl2List where kflM is null
        defaultCzCzl2ShouldNotBeFound("kflM.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflMIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflM is greater than or equal to DEFAULT_KFL_M
        defaultCzCzl2ShouldBeFound("kflM.greaterThanOrEqual=" + DEFAULT_KFL_M);

        // Get all the czCzl2List where kflM is greater than or equal to UPDATED_KFL_M
        defaultCzCzl2ShouldNotBeFound("kflM.greaterThanOrEqual=" + UPDATED_KFL_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflMIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflM is less than or equal to DEFAULT_KFL_M
        defaultCzCzl2ShouldBeFound("kflM.lessThanOrEqual=" + DEFAULT_KFL_M);

        // Get all the czCzl2List where kflM is less than or equal to SMALLER_KFL_M
        defaultCzCzl2ShouldNotBeFound("kflM.lessThanOrEqual=" + SMALLER_KFL_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflMIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflM is less than DEFAULT_KFL_M
        defaultCzCzl2ShouldNotBeFound("kflM.lessThan=" + DEFAULT_KFL_M);

        // Get all the czCzl2List where kflM is less than UPDATED_KFL_M
        defaultCzCzl2ShouldBeFound("kflM.lessThan=" + UPDATED_KFL_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflMIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflM is greater than DEFAULT_KFL_M
        defaultCzCzl2ShouldNotBeFound("kflM.greaterThan=" + DEFAULT_KFL_M);

        // Get all the czCzl2List where kflM is greater than SMALLER_KFL_M
        defaultCzCzl2ShouldBeFound("kflM.greaterThan=" + SMALLER_KFL_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrMIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrM equals to DEFAULT_FZSR_M
        defaultCzCzl2ShouldBeFound("fzsrM.equals=" + DEFAULT_FZSR_M);

        // Get all the czCzl2List where fzsrM equals to UPDATED_FZSR_M
        defaultCzCzl2ShouldNotBeFound("fzsrM.equals=" + UPDATED_FZSR_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrMIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrM not equals to DEFAULT_FZSR_M
        defaultCzCzl2ShouldNotBeFound("fzsrM.notEquals=" + DEFAULT_FZSR_M);

        // Get all the czCzl2List where fzsrM not equals to UPDATED_FZSR_M
        defaultCzCzl2ShouldBeFound("fzsrM.notEquals=" + UPDATED_FZSR_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrMIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrM in DEFAULT_FZSR_M or UPDATED_FZSR_M
        defaultCzCzl2ShouldBeFound("fzsrM.in=" + DEFAULT_FZSR_M + "," + UPDATED_FZSR_M);

        // Get all the czCzl2List where fzsrM equals to UPDATED_FZSR_M
        defaultCzCzl2ShouldNotBeFound("fzsrM.in=" + UPDATED_FZSR_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrMIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrM is not null
        defaultCzCzl2ShouldBeFound("fzsrM.specified=true");

        // Get all the czCzl2List where fzsrM is null
        defaultCzCzl2ShouldNotBeFound("fzsrM.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrMIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrM is greater than or equal to DEFAULT_FZSR_M
        defaultCzCzl2ShouldBeFound("fzsrM.greaterThanOrEqual=" + DEFAULT_FZSR_M);

        // Get all the czCzl2List where fzsrM is greater than or equal to UPDATED_FZSR_M
        defaultCzCzl2ShouldNotBeFound("fzsrM.greaterThanOrEqual=" + UPDATED_FZSR_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrMIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrM is less than or equal to DEFAULT_FZSR_M
        defaultCzCzl2ShouldBeFound("fzsrM.lessThanOrEqual=" + DEFAULT_FZSR_M);

        // Get all the czCzl2List where fzsrM is less than or equal to SMALLER_FZSR_M
        defaultCzCzl2ShouldNotBeFound("fzsrM.lessThanOrEqual=" + SMALLER_FZSR_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrMIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrM is less than DEFAULT_FZSR_M
        defaultCzCzl2ShouldNotBeFound("fzsrM.lessThan=" + DEFAULT_FZSR_M);

        // Get all the czCzl2List where fzsrM is less than UPDATED_FZSR_M
        defaultCzCzl2ShouldBeFound("fzsrM.lessThan=" + UPDATED_FZSR_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrMIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrM is greater than DEFAULT_FZSR_M
        defaultCzCzl2ShouldNotBeFound("fzsrM.greaterThan=" + DEFAULT_FZSR_M);

        // Get all the czCzl2List where fzsrM is greater than SMALLER_FZSR_M
        defaultCzCzl2ShouldBeFound("fzsrM.greaterThan=" + SMALLER_FZSR_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzMIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzM equals to DEFAULT_PJZ_M
        defaultCzCzl2ShouldBeFound("pjzM.equals=" + DEFAULT_PJZ_M);

        // Get all the czCzl2List where pjzM equals to UPDATED_PJZ_M
        defaultCzCzl2ShouldNotBeFound("pjzM.equals=" + UPDATED_PJZ_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzMIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzM not equals to DEFAULT_PJZ_M
        defaultCzCzl2ShouldNotBeFound("pjzM.notEquals=" + DEFAULT_PJZ_M);

        // Get all the czCzl2List where pjzM not equals to UPDATED_PJZ_M
        defaultCzCzl2ShouldBeFound("pjzM.notEquals=" + UPDATED_PJZ_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzMIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzM in DEFAULT_PJZ_M or UPDATED_PJZ_M
        defaultCzCzl2ShouldBeFound("pjzM.in=" + DEFAULT_PJZ_M + "," + UPDATED_PJZ_M);

        // Get all the czCzl2List where pjzM equals to UPDATED_PJZ_M
        defaultCzCzl2ShouldNotBeFound("pjzM.in=" + UPDATED_PJZ_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzMIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzM is not null
        defaultCzCzl2ShouldBeFound("pjzM.specified=true");

        // Get all the czCzl2List where pjzM is null
        defaultCzCzl2ShouldNotBeFound("pjzM.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzMIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzM is greater than or equal to DEFAULT_PJZ_M
        defaultCzCzl2ShouldBeFound("pjzM.greaterThanOrEqual=" + DEFAULT_PJZ_M);

        // Get all the czCzl2List where pjzM is greater than or equal to UPDATED_PJZ_M
        defaultCzCzl2ShouldNotBeFound("pjzM.greaterThanOrEqual=" + UPDATED_PJZ_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzMIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzM is less than or equal to DEFAULT_PJZ_M
        defaultCzCzl2ShouldBeFound("pjzM.lessThanOrEqual=" + DEFAULT_PJZ_M);

        // Get all the czCzl2List where pjzM is less than or equal to SMALLER_PJZ_M
        defaultCzCzl2ShouldNotBeFound("pjzM.lessThanOrEqual=" + SMALLER_PJZ_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzMIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzM is less than DEFAULT_PJZ_M
        defaultCzCzl2ShouldNotBeFound("pjzM.lessThan=" + DEFAULT_PJZ_M);

        // Get all the czCzl2List where pjzM is less than UPDATED_PJZ_M
        defaultCzCzl2ShouldBeFound("pjzM.lessThan=" + UPDATED_PJZ_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzMIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzM is greater than DEFAULT_PJZ_M
        defaultCzCzl2ShouldNotBeFound("pjzM.greaterThan=" + DEFAULT_PJZ_M);

        // Get all the czCzl2List where pjzM is greater than SMALLER_PJZ_M
        defaultCzCzl2ShouldBeFound("pjzM.greaterThan=" + SMALLER_PJZ_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsYIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsY equals to DEFAULT_FS_Y
        defaultCzCzl2ShouldBeFound("fsY.equals=" + DEFAULT_FS_Y);

        // Get all the czCzl2List where fsY equals to UPDATED_FS_Y
        defaultCzCzl2ShouldNotBeFound("fsY.equals=" + UPDATED_FS_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsYIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsY not equals to DEFAULT_FS_Y
        defaultCzCzl2ShouldNotBeFound("fsY.notEquals=" + DEFAULT_FS_Y);

        // Get all the czCzl2List where fsY not equals to UPDATED_FS_Y
        defaultCzCzl2ShouldBeFound("fsY.notEquals=" + UPDATED_FS_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsYIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsY in DEFAULT_FS_Y or UPDATED_FS_Y
        defaultCzCzl2ShouldBeFound("fsY.in=" + DEFAULT_FS_Y + "," + UPDATED_FS_Y);

        // Get all the czCzl2List where fsY equals to UPDATED_FS_Y
        defaultCzCzl2ShouldNotBeFound("fsY.in=" + UPDATED_FS_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsYIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsY is not null
        defaultCzCzl2ShouldBeFound("fsY.specified=true");

        // Get all the czCzl2List where fsY is null
        defaultCzCzl2ShouldNotBeFound("fsY.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsYIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsY is greater than or equal to DEFAULT_FS_Y
        defaultCzCzl2ShouldBeFound("fsY.greaterThanOrEqual=" + DEFAULT_FS_Y);

        // Get all the czCzl2List where fsY is greater than or equal to UPDATED_FS_Y
        defaultCzCzl2ShouldNotBeFound("fsY.greaterThanOrEqual=" + UPDATED_FS_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsYIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsY is less than or equal to DEFAULT_FS_Y
        defaultCzCzl2ShouldBeFound("fsY.lessThanOrEqual=" + DEFAULT_FS_Y);

        // Get all the czCzl2List where fsY is less than or equal to SMALLER_FS_Y
        defaultCzCzl2ShouldNotBeFound("fsY.lessThanOrEqual=" + SMALLER_FS_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsYIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsY is less than DEFAULT_FS_Y
        defaultCzCzl2ShouldNotBeFound("fsY.lessThan=" + DEFAULT_FS_Y);

        // Get all the czCzl2List where fsY is less than UPDATED_FS_Y
        defaultCzCzl2ShouldBeFound("fsY.lessThan=" + UPDATED_FS_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsYIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsY is greater than DEFAULT_FS_Y
        defaultCzCzl2ShouldNotBeFound("fsY.greaterThan=" + DEFAULT_FS_Y);

        // Get all the czCzl2List where fsY is greater than SMALLER_FS_Y
        defaultCzCzl2ShouldBeFound("fsY.greaterThan=" + SMALLER_FS_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflYIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflY equals to DEFAULT_KFL_Y
        defaultCzCzl2ShouldBeFound("kflY.equals=" + DEFAULT_KFL_Y);

        // Get all the czCzl2List where kflY equals to UPDATED_KFL_Y
        defaultCzCzl2ShouldNotBeFound("kflY.equals=" + UPDATED_KFL_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflYIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflY not equals to DEFAULT_KFL_Y
        defaultCzCzl2ShouldNotBeFound("kflY.notEquals=" + DEFAULT_KFL_Y);

        // Get all the czCzl2List where kflY not equals to UPDATED_KFL_Y
        defaultCzCzl2ShouldBeFound("kflY.notEquals=" + UPDATED_KFL_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflYIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflY in DEFAULT_KFL_Y or UPDATED_KFL_Y
        defaultCzCzl2ShouldBeFound("kflY.in=" + DEFAULT_KFL_Y + "," + UPDATED_KFL_Y);

        // Get all the czCzl2List where kflY equals to UPDATED_KFL_Y
        defaultCzCzl2ShouldNotBeFound("kflY.in=" + UPDATED_KFL_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflYIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflY is not null
        defaultCzCzl2ShouldBeFound("kflY.specified=true");

        // Get all the czCzl2List where kflY is null
        defaultCzCzl2ShouldNotBeFound("kflY.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflYIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflY is greater than or equal to DEFAULT_KFL_Y
        defaultCzCzl2ShouldBeFound("kflY.greaterThanOrEqual=" + DEFAULT_KFL_Y);

        // Get all the czCzl2List where kflY is greater than or equal to UPDATED_KFL_Y
        defaultCzCzl2ShouldNotBeFound("kflY.greaterThanOrEqual=" + UPDATED_KFL_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflYIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflY is less than or equal to DEFAULT_KFL_Y
        defaultCzCzl2ShouldBeFound("kflY.lessThanOrEqual=" + DEFAULT_KFL_Y);

        // Get all the czCzl2List where kflY is less than or equal to SMALLER_KFL_Y
        defaultCzCzl2ShouldNotBeFound("kflY.lessThanOrEqual=" + SMALLER_KFL_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflYIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflY is less than DEFAULT_KFL_Y
        defaultCzCzl2ShouldNotBeFound("kflY.lessThan=" + DEFAULT_KFL_Y);

        // Get all the czCzl2List where kflY is less than UPDATED_KFL_Y
        defaultCzCzl2ShouldBeFound("kflY.lessThan=" + UPDATED_KFL_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflYIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflY is greater than DEFAULT_KFL_Y
        defaultCzCzl2ShouldNotBeFound("kflY.greaterThan=" + DEFAULT_KFL_Y);

        // Get all the czCzl2List where kflY is greater than SMALLER_KFL_Y
        defaultCzCzl2ShouldBeFound("kflY.greaterThan=" + SMALLER_KFL_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrYIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrY equals to DEFAULT_FZSR_Y
        defaultCzCzl2ShouldBeFound("fzsrY.equals=" + DEFAULT_FZSR_Y);

        // Get all the czCzl2List where fzsrY equals to UPDATED_FZSR_Y
        defaultCzCzl2ShouldNotBeFound("fzsrY.equals=" + UPDATED_FZSR_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrYIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrY not equals to DEFAULT_FZSR_Y
        defaultCzCzl2ShouldNotBeFound("fzsrY.notEquals=" + DEFAULT_FZSR_Y);

        // Get all the czCzl2List where fzsrY not equals to UPDATED_FZSR_Y
        defaultCzCzl2ShouldBeFound("fzsrY.notEquals=" + UPDATED_FZSR_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrYIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrY in DEFAULT_FZSR_Y or UPDATED_FZSR_Y
        defaultCzCzl2ShouldBeFound("fzsrY.in=" + DEFAULT_FZSR_Y + "," + UPDATED_FZSR_Y);

        // Get all the czCzl2List where fzsrY equals to UPDATED_FZSR_Y
        defaultCzCzl2ShouldNotBeFound("fzsrY.in=" + UPDATED_FZSR_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrYIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrY is not null
        defaultCzCzl2ShouldBeFound("fzsrY.specified=true");

        // Get all the czCzl2List where fzsrY is null
        defaultCzCzl2ShouldNotBeFound("fzsrY.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrYIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrY is greater than or equal to DEFAULT_FZSR_Y
        defaultCzCzl2ShouldBeFound("fzsrY.greaterThanOrEqual=" + DEFAULT_FZSR_Y);

        // Get all the czCzl2List where fzsrY is greater than or equal to UPDATED_FZSR_Y
        defaultCzCzl2ShouldNotBeFound("fzsrY.greaterThanOrEqual=" + UPDATED_FZSR_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrYIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrY is less than or equal to DEFAULT_FZSR_Y
        defaultCzCzl2ShouldBeFound("fzsrY.lessThanOrEqual=" + DEFAULT_FZSR_Y);

        // Get all the czCzl2List where fzsrY is less than or equal to SMALLER_FZSR_Y
        defaultCzCzl2ShouldNotBeFound("fzsrY.lessThanOrEqual=" + SMALLER_FZSR_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrYIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrY is less than DEFAULT_FZSR_Y
        defaultCzCzl2ShouldNotBeFound("fzsrY.lessThan=" + DEFAULT_FZSR_Y);

        // Get all the czCzl2List where fzsrY is less than UPDATED_FZSR_Y
        defaultCzCzl2ShouldBeFound("fzsrY.lessThan=" + UPDATED_FZSR_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrYIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrY is greater than DEFAULT_FZSR_Y
        defaultCzCzl2ShouldNotBeFound("fzsrY.greaterThan=" + DEFAULT_FZSR_Y);

        // Get all the czCzl2List where fzsrY is greater than SMALLER_FZSR_Y
        defaultCzCzl2ShouldBeFound("fzsrY.greaterThan=" + SMALLER_FZSR_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzYIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzY equals to DEFAULT_PJZ_Y
        defaultCzCzl2ShouldBeFound("pjzY.equals=" + DEFAULT_PJZ_Y);

        // Get all the czCzl2List where pjzY equals to UPDATED_PJZ_Y
        defaultCzCzl2ShouldNotBeFound("pjzY.equals=" + UPDATED_PJZ_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzYIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzY not equals to DEFAULT_PJZ_Y
        defaultCzCzl2ShouldNotBeFound("pjzY.notEquals=" + DEFAULT_PJZ_Y);

        // Get all the czCzl2List where pjzY not equals to UPDATED_PJZ_Y
        defaultCzCzl2ShouldBeFound("pjzY.notEquals=" + UPDATED_PJZ_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzYIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzY in DEFAULT_PJZ_Y or UPDATED_PJZ_Y
        defaultCzCzl2ShouldBeFound("pjzY.in=" + DEFAULT_PJZ_Y + "," + UPDATED_PJZ_Y);

        // Get all the czCzl2List where pjzY equals to UPDATED_PJZ_Y
        defaultCzCzl2ShouldNotBeFound("pjzY.in=" + UPDATED_PJZ_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzYIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzY is not null
        defaultCzCzl2ShouldBeFound("pjzY.specified=true");

        // Get all the czCzl2List where pjzY is null
        defaultCzCzl2ShouldNotBeFound("pjzY.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzYIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzY is greater than or equal to DEFAULT_PJZ_Y
        defaultCzCzl2ShouldBeFound("pjzY.greaterThanOrEqual=" + DEFAULT_PJZ_Y);

        // Get all the czCzl2List where pjzY is greater than or equal to UPDATED_PJZ_Y
        defaultCzCzl2ShouldNotBeFound("pjzY.greaterThanOrEqual=" + UPDATED_PJZ_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzYIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzY is less than or equal to DEFAULT_PJZ_Y
        defaultCzCzl2ShouldBeFound("pjzY.lessThanOrEqual=" + DEFAULT_PJZ_Y);

        // Get all the czCzl2List where pjzY is less than or equal to SMALLER_PJZ_Y
        defaultCzCzl2ShouldNotBeFound("pjzY.lessThanOrEqual=" + SMALLER_PJZ_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzYIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzY is less than DEFAULT_PJZ_Y
        defaultCzCzl2ShouldNotBeFound("pjzY.lessThan=" + DEFAULT_PJZ_Y);

        // Get all the czCzl2List where pjzY is less than UPDATED_PJZ_Y
        defaultCzCzl2ShouldBeFound("pjzY.lessThan=" + UPDATED_PJZ_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzYIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzY is greater than DEFAULT_PJZ_Y
        defaultCzCzl2ShouldNotBeFound("pjzY.greaterThan=" + DEFAULT_PJZ_Y);

        // Get all the czCzl2List where pjzY is greater than SMALLER_PJZ_Y
        defaultCzCzl2ShouldBeFound("pjzY.greaterThan=" + SMALLER_PJZ_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsQIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsQ equals to DEFAULT_FS_Q
        defaultCzCzl2ShouldBeFound("fsQ.equals=" + DEFAULT_FS_Q);

        // Get all the czCzl2List where fsQ equals to UPDATED_FS_Q
        defaultCzCzl2ShouldNotBeFound("fsQ.equals=" + UPDATED_FS_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsQIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsQ not equals to DEFAULT_FS_Q
        defaultCzCzl2ShouldNotBeFound("fsQ.notEquals=" + DEFAULT_FS_Q);

        // Get all the czCzl2List where fsQ not equals to UPDATED_FS_Q
        defaultCzCzl2ShouldBeFound("fsQ.notEquals=" + UPDATED_FS_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsQIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsQ in DEFAULT_FS_Q or UPDATED_FS_Q
        defaultCzCzl2ShouldBeFound("fsQ.in=" + DEFAULT_FS_Q + "," + UPDATED_FS_Q);

        // Get all the czCzl2List where fsQ equals to UPDATED_FS_Q
        defaultCzCzl2ShouldNotBeFound("fsQ.in=" + UPDATED_FS_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsQIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsQ is not null
        defaultCzCzl2ShouldBeFound("fsQ.specified=true");

        // Get all the czCzl2List where fsQ is null
        defaultCzCzl2ShouldNotBeFound("fsQ.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsQIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsQ is greater than or equal to DEFAULT_FS_Q
        defaultCzCzl2ShouldBeFound("fsQ.greaterThanOrEqual=" + DEFAULT_FS_Q);

        // Get all the czCzl2List where fsQ is greater than or equal to UPDATED_FS_Q
        defaultCzCzl2ShouldNotBeFound("fsQ.greaterThanOrEqual=" + UPDATED_FS_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsQIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsQ is less than or equal to DEFAULT_FS_Q
        defaultCzCzl2ShouldBeFound("fsQ.lessThanOrEqual=" + DEFAULT_FS_Q);

        // Get all the czCzl2List where fsQ is less than or equal to SMALLER_FS_Q
        defaultCzCzl2ShouldNotBeFound("fsQ.lessThanOrEqual=" + SMALLER_FS_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsQIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsQ is less than DEFAULT_FS_Q
        defaultCzCzl2ShouldNotBeFound("fsQ.lessThan=" + DEFAULT_FS_Q);

        // Get all the czCzl2List where fsQ is less than UPDATED_FS_Q
        defaultCzCzl2ShouldBeFound("fsQ.lessThan=" + UPDATED_FS_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFsQIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fsQ is greater than DEFAULT_FS_Q
        defaultCzCzl2ShouldNotBeFound("fsQ.greaterThan=" + DEFAULT_FS_Q);

        // Get all the czCzl2List where fsQ is greater than SMALLER_FS_Q
        defaultCzCzl2ShouldBeFound("fsQ.greaterThan=" + SMALLER_FS_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflQIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflQ equals to DEFAULT_KFL_Q
        defaultCzCzl2ShouldBeFound("kflQ.equals=" + DEFAULT_KFL_Q);

        // Get all the czCzl2List where kflQ equals to UPDATED_KFL_Q
        defaultCzCzl2ShouldNotBeFound("kflQ.equals=" + UPDATED_KFL_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflQIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflQ not equals to DEFAULT_KFL_Q
        defaultCzCzl2ShouldNotBeFound("kflQ.notEquals=" + DEFAULT_KFL_Q);

        // Get all the czCzl2List where kflQ not equals to UPDATED_KFL_Q
        defaultCzCzl2ShouldBeFound("kflQ.notEquals=" + UPDATED_KFL_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflQIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflQ in DEFAULT_KFL_Q or UPDATED_KFL_Q
        defaultCzCzl2ShouldBeFound("kflQ.in=" + DEFAULT_KFL_Q + "," + UPDATED_KFL_Q);

        // Get all the czCzl2List where kflQ equals to UPDATED_KFL_Q
        defaultCzCzl2ShouldNotBeFound("kflQ.in=" + UPDATED_KFL_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflQIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflQ is not null
        defaultCzCzl2ShouldBeFound("kflQ.specified=true");

        // Get all the czCzl2List where kflQ is null
        defaultCzCzl2ShouldNotBeFound("kflQ.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflQIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflQ is greater than or equal to DEFAULT_KFL_Q
        defaultCzCzl2ShouldBeFound("kflQ.greaterThanOrEqual=" + DEFAULT_KFL_Q);

        // Get all the czCzl2List where kflQ is greater than or equal to UPDATED_KFL_Q
        defaultCzCzl2ShouldNotBeFound("kflQ.greaterThanOrEqual=" + UPDATED_KFL_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflQIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflQ is less than or equal to DEFAULT_KFL_Q
        defaultCzCzl2ShouldBeFound("kflQ.lessThanOrEqual=" + DEFAULT_KFL_Q);

        // Get all the czCzl2List where kflQ is less than or equal to SMALLER_KFL_Q
        defaultCzCzl2ShouldNotBeFound("kflQ.lessThanOrEqual=" + SMALLER_KFL_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflQIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflQ is less than DEFAULT_KFL_Q
        defaultCzCzl2ShouldNotBeFound("kflQ.lessThan=" + DEFAULT_KFL_Q);

        // Get all the czCzl2List where kflQ is less than UPDATED_KFL_Q
        defaultCzCzl2ShouldBeFound("kflQ.lessThan=" + UPDATED_KFL_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByKflQIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where kflQ is greater than DEFAULT_KFL_Q
        defaultCzCzl2ShouldNotBeFound("kflQ.greaterThan=" + DEFAULT_KFL_Q);

        // Get all the czCzl2List where kflQ is greater than SMALLER_KFL_Q
        defaultCzCzl2ShouldBeFound("kflQ.greaterThan=" + SMALLER_KFL_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrQIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrQ equals to DEFAULT_FZSR_Q
        defaultCzCzl2ShouldBeFound("fzsrQ.equals=" + DEFAULT_FZSR_Q);

        // Get all the czCzl2List where fzsrQ equals to UPDATED_FZSR_Q
        defaultCzCzl2ShouldNotBeFound("fzsrQ.equals=" + UPDATED_FZSR_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrQIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrQ not equals to DEFAULT_FZSR_Q
        defaultCzCzl2ShouldNotBeFound("fzsrQ.notEquals=" + DEFAULT_FZSR_Q);

        // Get all the czCzl2List where fzsrQ not equals to UPDATED_FZSR_Q
        defaultCzCzl2ShouldBeFound("fzsrQ.notEquals=" + UPDATED_FZSR_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrQIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrQ in DEFAULT_FZSR_Q or UPDATED_FZSR_Q
        defaultCzCzl2ShouldBeFound("fzsrQ.in=" + DEFAULT_FZSR_Q + "," + UPDATED_FZSR_Q);

        // Get all the czCzl2List where fzsrQ equals to UPDATED_FZSR_Q
        defaultCzCzl2ShouldNotBeFound("fzsrQ.in=" + UPDATED_FZSR_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrQIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrQ is not null
        defaultCzCzl2ShouldBeFound("fzsrQ.specified=true");

        // Get all the czCzl2List where fzsrQ is null
        defaultCzCzl2ShouldNotBeFound("fzsrQ.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrQIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrQ is greater than or equal to DEFAULT_FZSR_Q
        defaultCzCzl2ShouldBeFound("fzsrQ.greaterThanOrEqual=" + DEFAULT_FZSR_Q);

        // Get all the czCzl2List where fzsrQ is greater than or equal to UPDATED_FZSR_Q
        defaultCzCzl2ShouldNotBeFound("fzsrQ.greaterThanOrEqual=" + UPDATED_FZSR_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrQIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrQ is less than or equal to DEFAULT_FZSR_Q
        defaultCzCzl2ShouldBeFound("fzsrQ.lessThanOrEqual=" + DEFAULT_FZSR_Q);

        // Get all the czCzl2List where fzsrQ is less than or equal to SMALLER_FZSR_Q
        defaultCzCzl2ShouldNotBeFound("fzsrQ.lessThanOrEqual=" + SMALLER_FZSR_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrQIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrQ is less than DEFAULT_FZSR_Q
        defaultCzCzl2ShouldNotBeFound("fzsrQ.lessThan=" + DEFAULT_FZSR_Q);

        // Get all the czCzl2List where fzsrQ is less than UPDATED_FZSR_Q
        defaultCzCzl2ShouldBeFound("fzsrQ.lessThan=" + UPDATED_FZSR_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByFzsrQIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where fzsrQ is greater than DEFAULT_FZSR_Q
        defaultCzCzl2ShouldNotBeFound("fzsrQ.greaterThan=" + DEFAULT_FZSR_Q);

        // Get all the czCzl2List where fzsrQ is greater than SMALLER_FZSR_Q
        defaultCzCzl2ShouldBeFound("fzsrQ.greaterThan=" + SMALLER_FZSR_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzQIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzQ equals to DEFAULT_PJZ_Q
        defaultCzCzl2ShouldBeFound("pjzQ.equals=" + DEFAULT_PJZ_Q);

        // Get all the czCzl2List where pjzQ equals to UPDATED_PJZ_Q
        defaultCzCzl2ShouldNotBeFound("pjzQ.equals=" + UPDATED_PJZ_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzQIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzQ not equals to DEFAULT_PJZ_Q
        defaultCzCzl2ShouldNotBeFound("pjzQ.notEquals=" + DEFAULT_PJZ_Q);

        // Get all the czCzl2List where pjzQ not equals to UPDATED_PJZ_Q
        defaultCzCzl2ShouldBeFound("pjzQ.notEquals=" + UPDATED_PJZ_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzQIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzQ in DEFAULT_PJZ_Q or UPDATED_PJZ_Q
        defaultCzCzl2ShouldBeFound("pjzQ.in=" + DEFAULT_PJZ_Q + "," + UPDATED_PJZ_Q);

        // Get all the czCzl2List where pjzQ equals to UPDATED_PJZ_Q
        defaultCzCzl2ShouldNotBeFound("pjzQ.in=" + UPDATED_PJZ_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzQIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzQ is not null
        defaultCzCzl2ShouldBeFound("pjzQ.specified=true");

        // Get all the czCzl2List where pjzQ is null
        defaultCzCzl2ShouldNotBeFound("pjzQ.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzQIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzQ is greater than or equal to DEFAULT_PJZ_Q
        defaultCzCzl2ShouldBeFound("pjzQ.greaterThanOrEqual=" + DEFAULT_PJZ_Q);

        // Get all the czCzl2List where pjzQ is greater than or equal to UPDATED_PJZ_Q
        defaultCzCzl2ShouldNotBeFound("pjzQ.greaterThanOrEqual=" + UPDATED_PJZ_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzQIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzQ is less than or equal to DEFAULT_PJZ_Q
        defaultCzCzl2ShouldBeFound("pjzQ.lessThanOrEqual=" + DEFAULT_PJZ_Q);

        // Get all the czCzl2List where pjzQ is less than or equal to SMALLER_PJZ_Q
        defaultCzCzl2ShouldNotBeFound("pjzQ.lessThanOrEqual=" + SMALLER_PJZ_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzQIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzQ is less than DEFAULT_PJZ_Q
        defaultCzCzl2ShouldNotBeFound("pjzQ.lessThan=" + DEFAULT_PJZ_Q);

        // Get all the czCzl2List where pjzQ is less than UPDATED_PJZ_Q
        defaultCzCzl2ShouldBeFound("pjzQ.lessThan=" + UPDATED_PJZ_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByPjzQIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where pjzQ is greater than DEFAULT_PJZ_Q
        defaultCzCzl2ShouldNotBeFound("pjzQ.greaterThan=" + DEFAULT_PJZ_Q);

        // Get all the czCzl2List where pjzQ is greater than SMALLER_PJZ_Q
        defaultCzCzl2ShouldBeFound("pjzQ.greaterThan=" + SMALLER_PJZ_Q);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByDateYIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where dateY equals to DEFAULT_DATE_Y
        defaultCzCzl2ShouldBeFound("dateY.equals=" + DEFAULT_DATE_Y);

        // Get all the czCzl2List where dateY equals to UPDATED_DATE_Y
        defaultCzCzl2ShouldNotBeFound("dateY.equals=" + UPDATED_DATE_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByDateYIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where dateY not equals to DEFAULT_DATE_Y
        defaultCzCzl2ShouldNotBeFound("dateY.notEquals=" + DEFAULT_DATE_Y);

        // Get all the czCzl2List where dateY not equals to UPDATED_DATE_Y
        defaultCzCzl2ShouldBeFound("dateY.notEquals=" + UPDATED_DATE_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByDateYIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where dateY in DEFAULT_DATE_Y or UPDATED_DATE_Y
        defaultCzCzl2ShouldBeFound("dateY.in=" + DEFAULT_DATE_Y + "," + UPDATED_DATE_Y);

        // Get all the czCzl2List where dateY equals to UPDATED_DATE_Y
        defaultCzCzl2ShouldNotBeFound("dateY.in=" + UPDATED_DATE_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByDateYIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where dateY is not null
        defaultCzCzl2ShouldBeFound("dateY.specified=true");

        // Get all the czCzl2List where dateY is null
        defaultCzCzl2ShouldNotBeFound("dateY.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByDateYContainsSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where dateY contains DEFAULT_DATE_Y
        defaultCzCzl2ShouldBeFound("dateY.contains=" + DEFAULT_DATE_Y);

        // Get all the czCzl2List where dateY contains UPDATED_DATE_Y
        defaultCzCzl2ShouldNotBeFound("dateY.contains=" + UPDATED_DATE_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByDateYNotContainsSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where dateY does not contain DEFAULT_DATE_Y
        defaultCzCzl2ShouldNotBeFound("dateY.doesNotContain=" + DEFAULT_DATE_Y);

        // Get all the czCzl2List where dateY does not contain UPDATED_DATE_Y
        defaultCzCzl2ShouldBeFound("dateY.doesNotContain=" + UPDATED_DATE_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByDqdateIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where dqdate equals to DEFAULT_DQDATE
        defaultCzCzl2ShouldBeFound("dqdate.equals=" + DEFAULT_DQDATE);

        // Get all the czCzl2List where dqdate equals to UPDATED_DQDATE
        defaultCzCzl2ShouldNotBeFound("dqdate.equals=" + UPDATED_DQDATE);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByDqdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where dqdate not equals to DEFAULT_DQDATE
        defaultCzCzl2ShouldNotBeFound("dqdate.notEquals=" + DEFAULT_DQDATE);

        // Get all the czCzl2List where dqdate not equals to UPDATED_DQDATE
        defaultCzCzl2ShouldBeFound("dqdate.notEquals=" + UPDATED_DQDATE);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByDqdateIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where dqdate in DEFAULT_DQDATE or UPDATED_DQDATE
        defaultCzCzl2ShouldBeFound("dqdate.in=" + DEFAULT_DQDATE + "," + UPDATED_DQDATE);

        // Get all the czCzl2List where dqdate equals to UPDATED_DQDATE
        defaultCzCzl2ShouldNotBeFound("dqdate.in=" + UPDATED_DQDATE);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByDqdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where dqdate is not null
        defaultCzCzl2ShouldBeFound("dqdate.specified=true");

        // Get all the czCzl2List where dqdate is null
        defaultCzCzl2ShouldNotBeFound("dqdate.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where empn equals to DEFAULT_EMPN
        defaultCzCzl2ShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the czCzl2List where empn equals to UPDATED_EMPN
        defaultCzCzl2ShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where empn not equals to DEFAULT_EMPN
        defaultCzCzl2ShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the czCzl2List where empn not equals to UPDATED_EMPN
        defaultCzCzl2ShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultCzCzl2ShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the czCzl2List where empn equals to UPDATED_EMPN
        defaultCzCzl2ShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where empn is not null
        defaultCzCzl2ShouldBeFound("empn.specified=true");

        // Get all the czCzl2List where empn is null
        defaultCzCzl2ShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByEmpnContainsSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where empn contains DEFAULT_EMPN
        defaultCzCzl2ShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the czCzl2List where empn contains UPDATED_EMPN
        defaultCzCzl2ShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where empn does not contain DEFAULT_EMPN
        defaultCzCzl2ShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the czCzl2List where empn does not contain UPDATED_EMPN
        defaultCzCzl2ShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where number equals to DEFAULT_NUMBER
        defaultCzCzl2ShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the czCzl2List where number equals to UPDATED_NUMBER
        defaultCzCzl2ShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where number not equals to DEFAULT_NUMBER
        defaultCzCzl2ShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the czCzl2List where number not equals to UPDATED_NUMBER
        defaultCzCzl2ShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultCzCzl2ShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the czCzl2List where number equals to UPDATED_NUMBER
        defaultCzCzl2ShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where number is not null
        defaultCzCzl2ShouldBeFound("number.specified=true");

        // Get all the czCzl2List where number is null
        defaultCzCzl2ShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where number is greater than or equal to DEFAULT_NUMBER
        defaultCzCzl2ShouldBeFound("number.greaterThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the czCzl2List where number is greater than or equal to UPDATED_NUMBER
        defaultCzCzl2ShouldNotBeFound("number.greaterThanOrEqual=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where number is less than or equal to DEFAULT_NUMBER
        defaultCzCzl2ShouldBeFound("number.lessThanOrEqual=" + DEFAULT_NUMBER);

        // Get all the czCzl2List where number is less than or equal to SMALLER_NUMBER
        defaultCzCzl2ShouldNotBeFound("number.lessThanOrEqual=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where number is less than DEFAULT_NUMBER
        defaultCzCzl2ShouldNotBeFound("number.lessThan=" + DEFAULT_NUMBER);

        // Get all the czCzl2List where number is less than UPDATED_NUMBER
        defaultCzCzl2ShouldBeFound("number.lessThan=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where number is greater than DEFAULT_NUMBER
        defaultCzCzl2ShouldNotBeFound("number.greaterThan=" + DEFAULT_NUMBER);

        // Get all the czCzl2List where number is greater than SMALLER_NUMBER
        defaultCzCzl2ShouldBeFound("number.greaterThan=" + SMALLER_NUMBER);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberMIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberM equals to DEFAULT_NUMBER_M
        defaultCzCzl2ShouldBeFound("numberM.equals=" + DEFAULT_NUMBER_M);

        // Get all the czCzl2List where numberM equals to UPDATED_NUMBER_M
        defaultCzCzl2ShouldNotBeFound("numberM.equals=" + UPDATED_NUMBER_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberMIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberM not equals to DEFAULT_NUMBER_M
        defaultCzCzl2ShouldNotBeFound("numberM.notEquals=" + DEFAULT_NUMBER_M);

        // Get all the czCzl2List where numberM not equals to UPDATED_NUMBER_M
        defaultCzCzl2ShouldBeFound("numberM.notEquals=" + UPDATED_NUMBER_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberMIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberM in DEFAULT_NUMBER_M or UPDATED_NUMBER_M
        defaultCzCzl2ShouldBeFound("numberM.in=" + DEFAULT_NUMBER_M + "," + UPDATED_NUMBER_M);

        // Get all the czCzl2List where numberM equals to UPDATED_NUMBER_M
        defaultCzCzl2ShouldNotBeFound("numberM.in=" + UPDATED_NUMBER_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberMIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberM is not null
        defaultCzCzl2ShouldBeFound("numberM.specified=true");

        // Get all the czCzl2List where numberM is null
        defaultCzCzl2ShouldNotBeFound("numberM.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberMIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberM is greater than or equal to DEFAULT_NUMBER_M
        defaultCzCzl2ShouldBeFound("numberM.greaterThanOrEqual=" + DEFAULT_NUMBER_M);

        // Get all the czCzl2List where numberM is greater than or equal to UPDATED_NUMBER_M
        defaultCzCzl2ShouldNotBeFound("numberM.greaterThanOrEqual=" + UPDATED_NUMBER_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberMIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberM is less than or equal to DEFAULT_NUMBER_M
        defaultCzCzl2ShouldBeFound("numberM.lessThanOrEqual=" + DEFAULT_NUMBER_M);

        // Get all the czCzl2List where numberM is less than or equal to SMALLER_NUMBER_M
        defaultCzCzl2ShouldNotBeFound("numberM.lessThanOrEqual=" + SMALLER_NUMBER_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberMIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberM is less than DEFAULT_NUMBER_M
        defaultCzCzl2ShouldNotBeFound("numberM.lessThan=" + DEFAULT_NUMBER_M);

        // Get all the czCzl2List where numberM is less than UPDATED_NUMBER_M
        defaultCzCzl2ShouldBeFound("numberM.lessThan=" + UPDATED_NUMBER_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberMIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberM is greater than DEFAULT_NUMBER_M
        defaultCzCzl2ShouldNotBeFound("numberM.greaterThan=" + DEFAULT_NUMBER_M);

        // Get all the czCzl2List where numberM is greater than SMALLER_NUMBER_M
        defaultCzCzl2ShouldBeFound("numberM.greaterThan=" + SMALLER_NUMBER_M);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberYIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberY equals to DEFAULT_NUMBER_Y
        defaultCzCzl2ShouldBeFound("numberY.equals=" + DEFAULT_NUMBER_Y);

        // Get all the czCzl2List where numberY equals to UPDATED_NUMBER_Y
        defaultCzCzl2ShouldNotBeFound("numberY.equals=" + UPDATED_NUMBER_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberYIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberY not equals to DEFAULT_NUMBER_Y
        defaultCzCzl2ShouldNotBeFound("numberY.notEquals=" + DEFAULT_NUMBER_Y);

        // Get all the czCzl2List where numberY not equals to UPDATED_NUMBER_Y
        defaultCzCzl2ShouldBeFound("numberY.notEquals=" + UPDATED_NUMBER_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberYIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberY in DEFAULT_NUMBER_Y or UPDATED_NUMBER_Y
        defaultCzCzl2ShouldBeFound("numberY.in=" + DEFAULT_NUMBER_Y + "," + UPDATED_NUMBER_Y);

        // Get all the czCzl2List where numberY equals to UPDATED_NUMBER_Y
        defaultCzCzl2ShouldNotBeFound("numberY.in=" + UPDATED_NUMBER_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberYIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberY is not null
        defaultCzCzl2ShouldBeFound("numberY.specified=true");

        // Get all the czCzl2List where numberY is null
        defaultCzCzl2ShouldNotBeFound("numberY.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberYIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberY is greater than or equal to DEFAULT_NUMBER_Y
        defaultCzCzl2ShouldBeFound("numberY.greaterThanOrEqual=" + DEFAULT_NUMBER_Y);

        // Get all the czCzl2List where numberY is greater than or equal to UPDATED_NUMBER_Y
        defaultCzCzl2ShouldNotBeFound("numberY.greaterThanOrEqual=" + UPDATED_NUMBER_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberYIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberY is less than or equal to DEFAULT_NUMBER_Y
        defaultCzCzl2ShouldBeFound("numberY.lessThanOrEqual=" + DEFAULT_NUMBER_Y);

        // Get all the czCzl2List where numberY is less than or equal to SMALLER_NUMBER_Y
        defaultCzCzl2ShouldNotBeFound("numberY.lessThanOrEqual=" + SMALLER_NUMBER_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberYIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberY is less than DEFAULT_NUMBER_Y
        defaultCzCzl2ShouldNotBeFound("numberY.lessThan=" + DEFAULT_NUMBER_Y);

        // Get all the czCzl2List where numberY is less than UPDATED_NUMBER_Y
        defaultCzCzl2ShouldBeFound("numberY.lessThan=" + UPDATED_NUMBER_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByNumberYIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where numberY is greater than DEFAULT_NUMBER_Y
        defaultCzCzl2ShouldNotBeFound("numberY.greaterThan=" + DEFAULT_NUMBER_Y);

        // Get all the czCzl2List where numberY is greater than SMALLER_NUMBER_Y
        defaultCzCzl2ShouldBeFound("numberY.greaterThan=" + SMALLER_NUMBER_Y);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByHoteldmIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where hoteldm equals to DEFAULT_HOTELDM
        defaultCzCzl2ShouldBeFound("hoteldm.equals=" + DEFAULT_HOTELDM);

        // Get all the czCzl2List where hoteldm equals to UPDATED_HOTELDM
        defaultCzCzl2ShouldNotBeFound("hoteldm.equals=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByHoteldmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where hoteldm not equals to DEFAULT_HOTELDM
        defaultCzCzl2ShouldNotBeFound("hoteldm.notEquals=" + DEFAULT_HOTELDM);

        // Get all the czCzl2List where hoteldm not equals to UPDATED_HOTELDM
        defaultCzCzl2ShouldBeFound("hoteldm.notEquals=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByHoteldmIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where hoteldm in DEFAULT_HOTELDM or UPDATED_HOTELDM
        defaultCzCzl2ShouldBeFound("hoteldm.in=" + DEFAULT_HOTELDM + "," + UPDATED_HOTELDM);

        // Get all the czCzl2List where hoteldm equals to UPDATED_HOTELDM
        defaultCzCzl2ShouldNotBeFound("hoteldm.in=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByHoteldmIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where hoteldm is not null
        defaultCzCzl2ShouldBeFound("hoteldm.specified=true");

        // Get all the czCzl2List where hoteldm is null
        defaultCzCzl2ShouldNotBeFound("hoteldm.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByHoteldmContainsSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where hoteldm contains DEFAULT_HOTELDM
        defaultCzCzl2ShouldBeFound("hoteldm.contains=" + DEFAULT_HOTELDM);

        // Get all the czCzl2List where hoteldm contains UPDATED_HOTELDM
        defaultCzCzl2ShouldNotBeFound("hoteldm.contains=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByHoteldmNotContainsSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where hoteldm does not contain DEFAULT_HOTELDM
        defaultCzCzl2ShouldNotBeFound("hoteldm.doesNotContain=" + DEFAULT_HOTELDM);

        // Get all the czCzl2List where hoteldm does not contain UPDATED_HOTELDM
        defaultCzCzl2ShouldBeFound("hoteldm.doesNotContain=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByIsnewIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where isnew equals to DEFAULT_ISNEW
        defaultCzCzl2ShouldBeFound("isnew.equals=" + DEFAULT_ISNEW);

        // Get all the czCzl2List where isnew equals to UPDATED_ISNEW
        defaultCzCzl2ShouldNotBeFound("isnew.equals=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByIsnewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where isnew not equals to DEFAULT_ISNEW
        defaultCzCzl2ShouldNotBeFound("isnew.notEquals=" + DEFAULT_ISNEW);

        // Get all the czCzl2List where isnew not equals to UPDATED_ISNEW
        defaultCzCzl2ShouldBeFound("isnew.notEquals=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByIsnewIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where isnew in DEFAULT_ISNEW or UPDATED_ISNEW
        defaultCzCzl2ShouldBeFound("isnew.in=" + DEFAULT_ISNEW + "," + UPDATED_ISNEW);

        // Get all the czCzl2List where isnew equals to UPDATED_ISNEW
        defaultCzCzl2ShouldNotBeFound("isnew.in=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByIsnewIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where isnew is not null
        defaultCzCzl2ShouldBeFound("isnew.specified=true");

        // Get all the czCzl2List where isnew is null
        defaultCzCzl2ShouldNotBeFound("isnew.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl2sByIsnewIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where isnew is greater than or equal to DEFAULT_ISNEW
        defaultCzCzl2ShouldBeFound("isnew.greaterThanOrEqual=" + DEFAULT_ISNEW);

        // Get all the czCzl2List where isnew is greater than or equal to UPDATED_ISNEW
        defaultCzCzl2ShouldNotBeFound("isnew.greaterThanOrEqual=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByIsnewIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where isnew is less than or equal to DEFAULT_ISNEW
        defaultCzCzl2ShouldBeFound("isnew.lessThanOrEqual=" + DEFAULT_ISNEW);

        // Get all the czCzl2List where isnew is less than or equal to SMALLER_ISNEW
        defaultCzCzl2ShouldNotBeFound("isnew.lessThanOrEqual=" + SMALLER_ISNEW);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByIsnewIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where isnew is less than DEFAULT_ISNEW
        defaultCzCzl2ShouldNotBeFound("isnew.lessThan=" + DEFAULT_ISNEW);

        // Get all the czCzl2List where isnew is less than UPDATED_ISNEW
        defaultCzCzl2ShouldBeFound("isnew.lessThan=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllCzCzl2sByIsnewIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        // Get all the czCzl2List where isnew is greater than DEFAULT_ISNEW
        defaultCzCzl2ShouldNotBeFound("isnew.greaterThan=" + DEFAULT_ISNEW);

        // Get all the czCzl2List where isnew is greater than SMALLER_ISNEW
        defaultCzCzl2ShouldBeFound("isnew.greaterThan=" + SMALLER_ISNEW);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCzCzl2ShouldBeFound(String filter) throws Exception {
        restCzCzl2MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(czCzl2.getId().intValue())))
            .andExpect(jsonPath("$.[*].dr").value(hasItem(DEFAULT_DR.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].fs").value(hasItem(DEFAULT_FS.intValue())))
            .andExpect(jsonPath("$.[*].kfl").value(hasItem(sameNumber(DEFAULT_KFL))))
            .andExpect(jsonPath("$.[*].fzsr").value(hasItem(sameNumber(DEFAULT_FZSR))))
            .andExpect(jsonPath("$.[*].pjz").value(hasItem(sameNumber(DEFAULT_PJZ))))
            .andExpect(jsonPath("$.[*].fsM").value(hasItem(DEFAULT_FS_M.intValue())))
            .andExpect(jsonPath("$.[*].kflM").value(hasItem(sameNumber(DEFAULT_KFL_M))))
            .andExpect(jsonPath("$.[*].fzsrM").value(hasItem(sameNumber(DEFAULT_FZSR_M))))
            .andExpect(jsonPath("$.[*].pjzM").value(hasItem(sameNumber(DEFAULT_PJZ_M))))
            .andExpect(jsonPath("$.[*].fsY").value(hasItem(DEFAULT_FS_Y.intValue())))
            .andExpect(jsonPath("$.[*].kflY").value(hasItem(sameNumber(DEFAULT_KFL_Y))))
            .andExpect(jsonPath("$.[*].fzsrY").value(hasItem(sameNumber(DEFAULT_FZSR_Y))))
            .andExpect(jsonPath("$.[*].pjzY").value(hasItem(sameNumber(DEFAULT_PJZ_Y))))
            .andExpect(jsonPath("$.[*].fsQ").value(hasItem(DEFAULT_FS_Q.intValue())))
            .andExpect(jsonPath("$.[*].kflQ").value(hasItem(sameNumber(DEFAULT_KFL_Q))))
            .andExpect(jsonPath("$.[*].fzsrQ").value(hasItem(sameNumber(DEFAULT_FZSR_Q))))
            .andExpect(jsonPath("$.[*].pjzQ").value(hasItem(sameNumber(DEFAULT_PJZ_Q))))
            .andExpect(jsonPath("$.[*].dateY").value(hasItem(DEFAULT_DATE_Y)))
            .andExpect(jsonPath("$.[*].dqdate").value(hasItem(DEFAULT_DQDATE.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].numberM").value(hasItem(DEFAULT_NUMBER_M.intValue())))
            .andExpect(jsonPath("$.[*].numberY").value(hasItem(DEFAULT_NUMBER_Y.intValue())))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())));

        // Check, that the count call also returns 1
        restCzCzl2MockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCzCzl2ShouldNotBeFound(String filter) throws Exception {
        restCzCzl2MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCzCzl2MockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCzCzl2() throws Exception {
        // Get the czCzl2
        restCzCzl2MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCzCzl2() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        int databaseSizeBeforeUpdate = czCzl2Repository.findAll().size();

        // Update the czCzl2
        CzCzl2 updatedCzCzl2 = czCzl2Repository.findById(czCzl2.getId()).get();
        // Disconnect from session so that the updates on updatedCzCzl2 are not directly saved in db
        em.detach(updatedCzCzl2);
        updatedCzCzl2
            .dr(UPDATED_DR)
            .type(UPDATED_TYPE)
            .fs(UPDATED_FS)
            .kfl(UPDATED_KFL)
            .fzsr(UPDATED_FZSR)
            .pjz(UPDATED_PJZ)
            .fsM(UPDATED_FS_M)
            .kflM(UPDATED_KFL_M)
            .fzsrM(UPDATED_FZSR_M)
            .pjzM(UPDATED_PJZ_M)
            .fsY(UPDATED_FS_Y)
            .kflY(UPDATED_KFL_Y)
            .fzsrY(UPDATED_FZSR_Y)
            .pjzY(UPDATED_PJZ_Y)
            .fsQ(UPDATED_FS_Q)
            .kflQ(UPDATED_KFL_Q)
            .fzsrQ(UPDATED_FZSR_Q)
            .pjzQ(UPDATED_PJZ_Q)
            .dateY(UPDATED_DATE_Y)
            .dqdate(UPDATED_DQDATE)
            .empn(UPDATED_EMPN)
            .number(UPDATED_NUMBER)
            .numberM(UPDATED_NUMBER_M)
            .numberY(UPDATED_NUMBER_Y)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW);
        CzCzl2DTO czCzl2DTO = czCzl2Mapper.toDto(updatedCzCzl2);

        restCzCzl2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, czCzl2DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(czCzl2DTO))
            )
            .andExpect(status().isOk());

        // Validate the CzCzl2 in the database
        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeUpdate);
        CzCzl2 testCzCzl2 = czCzl2List.get(czCzl2List.size() - 1);
        assertThat(testCzCzl2.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testCzCzl2.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCzCzl2.getFs()).isEqualTo(UPDATED_FS);
        assertThat(testCzCzl2.getKfl()).isEqualTo(UPDATED_KFL);
        assertThat(testCzCzl2.getFzsr()).isEqualTo(UPDATED_FZSR);
        assertThat(testCzCzl2.getPjz()).isEqualTo(UPDATED_PJZ);
        assertThat(testCzCzl2.getFsM()).isEqualTo(UPDATED_FS_M);
        assertThat(testCzCzl2.getKflM()).isEqualTo(UPDATED_KFL_M);
        assertThat(testCzCzl2.getFzsrM()).isEqualTo(UPDATED_FZSR_M);
        assertThat(testCzCzl2.getPjzM()).isEqualTo(UPDATED_PJZ_M);
        assertThat(testCzCzl2.getFsY()).isEqualTo(UPDATED_FS_Y);
        assertThat(testCzCzl2.getKflY()).isEqualTo(UPDATED_KFL_Y);
        assertThat(testCzCzl2.getFzsrY()).isEqualTo(UPDATED_FZSR_Y);
        assertThat(testCzCzl2.getPjzY()).isEqualTo(UPDATED_PJZ_Y);
        assertThat(testCzCzl2.getFsQ()).isEqualTo(UPDATED_FS_Q);
        assertThat(testCzCzl2.getKflQ()).isEqualTo(UPDATED_KFL_Q);
        assertThat(testCzCzl2.getFzsrQ()).isEqualTo(UPDATED_FZSR_Q);
        assertThat(testCzCzl2.getPjzQ()).isEqualTo(UPDATED_PJZ_Q);
        assertThat(testCzCzl2.getDateY()).isEqualTo(UPDATED_DATE_Y);
        assertThat(testCzCzl2.getDqdate()).isEqualTo(UPDATED_DQDATE);
        assertThat(testCzCzl2.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCzCzl2.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCzCzl2.getNumberM()).isEqualTo(UPDATED_NUMBER_M);
        assertThat(testCzCzl2.getNumberY()).isEqualTo(UPDATED_NUMBER_Y);
        assertThat(testCzCzl2.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testCzCzl2.getIsnew()).isEqualTo(UPDATED_ISNEW);

        // Validate the CzCzl2 in Elasticsearch
        verify(mockCzCzl2SearchRepository).save(testCzCzl2);
    }

    @Test
    @Transactional
    void putNonExistingCzCzl2() throws Exception {
        int databaseSizeBeforeUpdate = czCzl2Repository.findAll().size();
        czCzl2.setId(count.incrementAndGet());

        // Create the CzCzl2
        CzCzl2DTO czCzl2DTO = czCzl2Mapper.toDto(czCzl2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCzCzl2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, czCzl2DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(czCzl2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzCzl2 in the database
        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzCzl2 in Elasticsearch
        verify(mockCzCzl2SearchRepository, times(0)).save(czCzl2);
    }

    @Test
    @Transactional
    void putWithIdMismatchCzCzl2() throws Exception {
        int databaseSizeBeforeUpdate = czCzl2Repository.findAll().size();
        czCzl2.setId(count.incrementAndGet());

        // Create the CzCzl2
        CzCzl2DTO czCzl2DTO = czCzl2Mapper.toDto(czCzl2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzCzl2MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(czCzl2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzCzl2 in the database
        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzCzl2 in Elasticsearch
        verify(mockCzCzl2SearchRepository, times(0)).save(czCzl2);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCzCzl2() throws Exception {
        int databaseSizeBeforeUpdate = czCzl2Repository.findAll().size();
        czCzl2.setId(count.incrementAndGet());

        // Create the CzCzl2
        CzCzl2DTO czCzl2DTO = czCzl2Mapper.toDto(czCzl2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzCzl2MockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czCzl2DTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CzCzl2 in the database
        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzCzl2 in Elasticsearch
        verify(mockCzCzl2SearchRepository, times(0)).save(czCzl2);
    }

    @Test
    @Transactional
    void partialUpdateCzCzl2WithPatch() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        int databaseSizeBeforeUpdate = czCzl2Repository.findAll().size();

        // Update the czCzl2 using partial update
        CzCzl2 partialUpdatedCzCzl2 = new CzCzl2();
        partialUpdatedCzCzl2.setId(czCzl2.getId());

        partialUpdatedCzCzl2
            .dr(UPDATED_DR)
            .fs(UPDATED_FS)
            .pjz(UPDATED_PJZ)
            .fsM(UPDATED_FS_M)
            .fzsrM(UPDATED_FZSR_M)
            .fzsrY(UPDATED_FZSR_Y)
            .pjzY(UPDATED_PJZ_Y)
            .fsQ(UPDATED_FS_Q)
            .fzsrQ(UPDATED_FZSR_Q)
            .pjzQ(UPDATED_PJZ_Q)
            .dqdate(UPDATED_DQDATE)
            .number(UPDATED_NUMBER)
            .numberM(UPDATED_NUMBER_M)
            .isnew(UPDATED_ISNEW);

        restCzCzl2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCzCzl2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCzCzl2))
            )
            .andExpect(status().isOk());

        // Validate the CzCzl2 in the database
        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeUpdate);
        CzCzl2 testCzCzl2 = czCzl2List.get(czCzl2List.size() - 1);
        assertThat(testCzCzl2.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testCzCzl2.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testCzCzl2.getFs()).isEqualTo(UPDATED_FS);
        assertThat(testCzCzl2.getKfl()).isEqualByComparingTo(DEFAULT_KFL);
        assertThat(testCzCzl2.getFzsr()).isEqualByComparingTo(DEFAULT_FZSR);
        assertThat(testCzCzl2.getPjz()).isEqualByComparingTo(UPDATED_PJZ);
        assertThat(testCzCzl2.getFsM()).isEqualTo(UPDATED_FS_M);
        assertThat(testCzCzl2.getKflM()).isEqualByComparingTo(DEFAULT_KFL_M);
        assertThat(testCzCzl2.getFzsrM()).isEqualByComparingTo(UPDATED_FZSR_M);
        assertThat(testCzCzl2.getPjzM()).isEqualByComparingTo(DEFAULT_PJZ_M);
        assertThat(testCzCzl2.getFsY()).isEqualTo(DEFAULT_FS_Y);
        assertThat(testCzCzl2.getKflY()).isEqualByComparingTo(DEFAULT_KFL_Y);
        assertThat(testCzCzl2.getFzsrY()).isEqualByComparingTo(UPDATED_FZSR_Y);
        assertThat(testCzCzl2.getPjzY()).isEqualByComparingTo(UPDATED_PJZ_Y);
        assertThat(testCzCzl2.getFsQ()).isEqualTo(UPDATED_FS_Q);
        assertThat(testCzCzl2.getKflQ()).isEqualByComparingTo(DEFAULT_KFL_Q);
        assertThat(testCzCzl2.getFzsrQ()).isEqualByComparingTo(UPDATED_FZSR_Q);
        assertThat(testCzCzl2.getPjzQ()).isEqualByComparingTo(UPDATED_PJZ_Q);
        assertThat(testCzCzl2.getDateY()).isEqualTo(DEFAULT_DATE_Y);
        assertThat(testCzCzl2.getDqdate()).isEqualTo(UPDATED_DQDATE);
        assertThat(testCzCzl2.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testCzCzl2.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCzCzl2.getNumberM()).isEqualTo(UPDATED_NUMBER_M);
        assertThat(testCzCzl2.getNumberY()).isEqualTo(DEFAULT_NUMBER_Y);
        assertThat(testCzCzl2.getHoteldm()).isEqualTo(DEFAULT_HOTELDM);
        assertThat(testCzCzl2.getIsnew()).isEqualTo(UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void fullUpdateCzCzl2WithPatch() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        int databaseSizeBeforeUpdate = czCzl2Repository.findAll().size();

        // Update the czCzl2 using partial update
        CzCzl2 partialUpdatedCzCzl2 = new CzCzl2();
        partialUpdatedCzCzl2.setId(czCzl2.getId());

        partialUpdatedCzCzl2
            .dr(UPDATED_DR)
            .type(UPDATED_TYPE)
            .fs(UPDATED_FS)
            .kfl(UPDATED_KFL)
            .fzsr(UPDATED_FZSR)
            .pjz(UPDATED_PJZ)
            .fsM(UPDATED_FS_M)
            .kflM(UPDATED_KFL_M)
            .fzsrM(UPDATED_FZSR_M)
            .pjzM(UPDATED_PJZ_M)
            .fsY(UPDATED_FS_Y)
            .kflY(UPDATED_KFL_Y)
            .fzsrY(UPDATED_FZSR_Y)
            .pjzY(UPDATED_PJZ_Y)
            .fsQ(UPDATED_FS_Q)
            .kflQ(UPDATED_KFL_Q)
            .fzsrQ(UPDATED_FZSR_Q)
            .pjzQ(UPDATED_PJZ_Q)
            .dateY(UPDATED_DATE_Y)
            .dqdate(UPDATED_DQDATE)
            .empn(UPDATED_EMPN)
            .number(UPDATED_NUMBER)
            .numberM(UPDATED_NUMBER_M)
            .numberY(UPDATED_NUMBER_Y)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW);

        restCzCzl2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCzCzl2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCzCzl2))
            )
            .andExpect(status().isOk());

        // Validate the CzCzl2 in the database
        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeUpdate);
        CzCzl2 testCzCzl2 = czCzl2List.get(czCzl2List.size() - 1);
        assertThat(testCzCzl2.getDr()).isEqualTo(UPDATED_DR);
        assertThat(testCzCzl2.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testCzCzl2.getFs()).isEqualTo(UPDATED_FS);
        assertThat(testCzCzl2.getKfl()).isEqualByComparingTo(UPDATED_KFL);
        assertThat(testCzCzl2.getFzsr()).isEqualByComparingTo(UPDATED_FZSR);
        assertThat(testCzCzl2.getPjz()).isEqualByComparingTo(UPDATED_PJZ);
        assertThat(testCzCzl2.getFsM()).isEqualTo(UPDATED_FS_M);
        assertThat(testCzCzl2.getKflM()).isEqualByComparingTo(UPDATED_KFL_M);
        assertThat(testCzCzl2.getFzsrM()).isEqualByComparingTo(UPDATED_FZSR_M);
        assertThat(testCzCzl2.getPjzM()).isEqualByComparingTo(UPDATED_PJZ_M);
        assertThat(testCzCzl2.getFsY()).isEqualTo(UPDATED_FS_Y);
        assertThat(testCzCzl2.getKflY()).isEqualByComparingTo(UPDATED_KFL_Y);
        assertThat(testCzCzl2.getFzsrY()).isEqualByComparingTo(UPDATED_FZSR_Y);
        assertThat(testCzCzl2.getPjzY()).isEqualByComparingTo(UPDATED_PJZ_Y);
        assertThat(testCzCzl2.getFsQ()).isEqualTo(UPDATED_FS_Q);
        assertThat(testCzCzl2.getKflQ()).isEqualByComparingTo(UPDATED_KFL_Q);
        assertThat(testCzCzl2.getFzsrQ()).isEqualByComparingTo(UPDATED_FZSR_Q);
        assertThat(testCzCzl2.getPjzQ()).isEqualByComparingTo(UPDATED_PJZ_Q);
        assertThat(testCzCzl2.getDateY()).isEqualTo(UPDATED_DATE_Y);
        assertThat(testCzCzl2.getDqdate()).isEqualTo(UPDATED_DQDATE);
        assertThat(testCzCzl2.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testCzCzl2.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testCzCzl2.getNumberM()).isEqualTo(UPDATED_NUMBER_M);
        assertThat(testCzCzl2.getNumberY()).isEqualTo(UPDATED_NUMBER_Y);
        assertThat(testCzCzl2.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testCzCzl2.getIsnew()).isEqualTo(UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void patchNonExistingCzCzl2() throws Exception {
        int databaseSizeBeforeUpdate = czCzl2Repository.findAll().size();
        czCzl2.setId(count.incrementAndGet());

        // Create the CzCzl2
        CzCzl2DTO czCzl2DTO = czCzl2Mapper.toDto(czCzl2);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCzCzl2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, czCzl2DTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(czCzl2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzCzl2 in the database
        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzCzl2 in Elasticsearch
        verify(mockCzCzl2SearchRepository, times(0)).save(czCzl2);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCzCzl2() throws Exception {
        int databaseSizeBeforeUpdate = czCzl2Repository.findAll().size();
        czCzl2.setId(count.incrementAndGet());

        // Create the CzCzl2
        CzCzl2DTO czCzl2DTO = czCzl2Mapper.toDto(czCzl2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzCzl2MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(czCzl2DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzCzl2 in the database
        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzCzl2 in Elasticsearch
        verify(mockCzCzl2SearchRepository, times(0)).save(czCzl2);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCzCzl2() throws Exception {
        int databaseSizeBeforeUpdate = czCzl2Repository.findAll().size();
        czCzl2.setId(count.incrementAndGet());

        // Create the CzCzl2
        CzCzl2DTO czCzl2DTO = czCzl2Mapper.toDto(czCzl2);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzCzl2MockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(czCzl2DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CzCzl2 in the database
        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzCzl2 in Elasticsearch
        verify(mockCzCzl2SearchRepository, times(0)).save(czCzl2);
    }

    @Test
    @Transactional
    void deleteCzCzl2() throws Exception {
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);

        int databaseSizeBeforeDelete = czCzl2Repository.findAll().size();

        // Delete the czCzl2
        restCzCzl2MockMvc
            .perform(delete(ENTITY_API_URL_ID, czCzl2.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CzCzl2> czCzl2List = czCzl2Repository.findAll();
        assertThat(czCzl2List).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CzCzl2 in Elasticsearch
        verify(mockCzCzl2SearchRepository, times(1)).deleteById(czCzl2.getId());
    }

    @Test
    @Transactional
    void searchCzCzl2() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        czCzl2Repository.saveAndFlush(czCzl2);
        when(mockCzCzl2SearchRepository.search(queryStringQuery("id:" + czCzl2.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(czCzl2), PageRequest.of(0, 1), 1));

        // Search the czCzl2
        restCzCzl2MockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + czCzl2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(czCzl2.getId().intValue())))
            .andExpect(jsonPath("$.[*].dr").value(hasItem(DEFAULT_DR.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].fs").value(hasItem(DEFAULT_FS.intValue())))
            .andExpect(jsonPath("$.[*].kfl").value(hasItem(sameNumber(DEFAULT_KFL))))
            .andExpect(jsonPath("$.[*].fzsr").value(hasItem(sameNumber(DEFAULT_FZSR))))
            .andExpect(jsonPath("$.[*].pjz").value(hasItem(sameNumber(DEFAULT_PJZ))))
            .andExpect(jsonPath("$.[*].fsM").value(hasItem(DEFAULT_FS_M.intValue())))
            .andExpect(jsonPath("$.[*].kflM").value(hasItem(sameNumber(DEFAULT_KFL_M))))
            .andExpect(jsonPath("$.[*].fzsrM").value(hasItem(sameNumber(DEFAULT_FZSR_M))))
            .andExpect(jsonPath("$.[*].pjzM").value(hasItem(sameNumber(DEFAULT_PJZ_M))))
            .andExpect(jsonPath("$.[*].fsY").value(hasItem(DEFAULT_FS_Y.intValue())))
            .andExpect(jsonPath("$.[*].kflY").value(hasItem(sameNumber(DEFAULT_KFL_Y))))
            .andExpect(jsonPath("$.[*].fzsrY").value(hasItem(sameNumber(DEFAULT_FZSR_Y))))
            .andExpect(jsonPath("$.[*].pjzY").value(hasItem(sameNumber(DEFAULT_PJZ_Y))))
            .andExpect(jsonPath("$.[*].fsQ").value(hasItem(DEFAULT_FS_Q.intValue())))
            .andExpect(jsonPath("$.[*].kflQ").value(hasItem(sameNumber(DEFAULT_KFL_Q))))
            .andExpect(jsonPath("$.[*].fzsrQ").value(hasItem(sameNumber(DEFAULT_FZSR_Q))))
            .andExpect(jsonPath("$.[*].pjzQ").value(hasItem(sameNumber(DEFAULT_PJZ_Q))))
            .andExpect(jsonPath("$.[*].dateY").value(hasItem(DEFAULT_DATE_Y)))
            .andExpect(jsonPath("$.[*].dqdate").value(hasItem(DEFAULT_DQDATE.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER.intValue())))
            .andExpect(jsonPath("$.[*].numberM").value(hasItem(DEFAULT_NUMBER_M.intValue())))
            .andExpect(jsonPath("$.[*].numberY").value(hasItem(DEFAULT_NUMBER_Y.intValue())))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())));
    }
}
