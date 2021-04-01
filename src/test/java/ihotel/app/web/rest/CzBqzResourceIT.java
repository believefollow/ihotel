package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.CzBqz;
import ihotel.app.repository.CzBqzRepository;
import ihotel.app.repository.search.CzBqzSearchRepository;
import ihotel.app.service.criteria.CzBqzCriteria;
import ihotel.app.service.dto.CzBqzDTO;
import ihotel.app.service.mapper.CzBqzMapper;
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
 * Integration tests for the {@link CzBqzResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CzBqzResourceIT {

    private static final Instant DEFAULT_RQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_Q_SL = 1L;
    private static final Long UPDATED_Q_SL = 2L;
    private static final Long SMALLER_Q_SL = 1L - 1L;

    private static final BigDecimal DEFAULT_Q_KFL = new BigDecimal(1);
    private static final BigDecimal UPDATED_Q_KFL = new BigDecimal(2);
    private static final BigDecimal SMALLER_Q_KFL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_Q_PJZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_Q_PJZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_Q_PJZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_Q_YSFZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_Q_YSFZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_Q_YSFZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_Q_SJFZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_Q_SJFZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_Q_SJFZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_Q_FZCZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_Q_FZCZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_Q_FZCZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_Q_PJZCZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_Q_PJZCZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_Q_PJZCZ = new BigDecimal(1 - 1);

    private static final Long DEFAULT_B_SL = 1L;
    private static final Long UPDATED_B_SL = 2L;
    private static final Long SMALLER_B_SL = 1L - 1L;

    private static final BigDecimal DEFAULT_B_KFL = new BigDecimal(1);
    private static final BigDecimal UPDATED_B_KFL = new BigDecimal(2);
    private static final BigDecimal SMALLER_B_KFL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_B_PJZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_B_PJZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_B_PJZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_B_YSFZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_B_YSFZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_B_YSFZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_B_SJFZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_B_SJFZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_B_SJFZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_B_FZCZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_B_FZCZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_B_FZCZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_B_PJZCZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_B_PJZCZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_B_PJZCZ = new BigDecimal(1 - 1);

    private static final Long DEFAULT_Z_SL = 1L;
    private static final Long UPDATED_Z_SL = 2L;
    private static final Long SMALLER_Z_SL = 1L - 1L;

    private static final BigDecimal DEFAULT_Z_KFL = new BigDecimal(1);
    private static final BigDecimal UPDATED_Z_KFL = new BigDecimal(2);
    private static final BigDecimal SMALLER_Z_KFL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_Z_PJZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_Z_PJZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_Z_PJZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_Z_YSFZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_Z_YSFZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_Z_YSFZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_Z_SJFZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_Z_SJFZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_Z_SJFZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_Z_FZCZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_Z_FZCZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_Z_FZCZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_Z_PJZCZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_Z_PJZCZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_Z_PJZCZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ZK = new BigDecimal(1);
    private static final BigDecimal UPDATED_ZK = new BigDecimal(2);
    private static final BigDecimal SMALLER_ZK = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/cz-bqzs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/cz-bqzs";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CzBqzRepository czBqzRepository;

    @Autowired
    private CzBqzMapper czBqzMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CzBqzSearchRepositoryMockConfiguration
     */
    @Autowired
    private CzBqzSearchRepository mockCzBqzSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCzBqzMockMvc;

    private CzBqz czBqz;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CzBqz createEntity(EntityManager em) {
        CzBqz czBqz = new CzBqz()
            .rq(DEFAULT_RQ)
            .qSl(DEFAULT_Q_SL)
            .qKfl(DEFAULT_Q_KFL)
            .qPjz(DEFAULT_Q_PJZ)
            .qYsfz(DEFAULT_Q_YSFZ)
            .qSjfz(DEFAULT_Q_SJFZ)
            .qFzcz(DEFAULT_Q_FZCZ)
            .qPjzcz(DEFAULT_Q_PJZCZ)
            .bSl(DEFAULT_B_SL)
            .bKfl(DEFAULT_B_KFL)
            .bPjz(DEFAULT_B_PJZ)
            .bYsfz(DEFAULT_B_YSFZ)
            .bSjfz(DEFAULT_B_SJFZ)
            .bFzcz(DEFAULT_B_FZCZ)
            .bPjzcz(DEFAULT_B_PJZCZ)
            .zSl(DEFAULT_Z_SL)
            .zKfl(DEFAULT_Z_KFL)
            .zPjz(DEFAULT_Z_PJZ)
            .zYsfz(DEFAULT_Z_YSFZ)
            .zSjfz(DEFAULT_Z_SJFZ)
            .zFzcz(DEFAULT_Z_FZCZ)
            .zPjzcz(DEFAULT_Z_PJZCZ)
            .zk(DEFAULT_ZK);
        return czBqz;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CzBqz createUpdatedEntity(EntityManager em) {
        CzBqz czBqz = new CzBqz()
            .rq(UPDATED_RQ)
            .qSl(UPDATED_Q_SL)
            .qKfl(UPDATED_Q_KFL)
            .qPjz(UPDATED_Q_PJZ)
            .qYsfz(UPDATED_Q_YSFZ)
            .qSjfz(UPDATED_Q_SJFZ)
            .qFzcz(UPDATED_Q_FZCZ)
            .qPjzcz(UPDATED_Q_PJZCZ)
            .bSl(UPDATED_B_SL)
            .bKfl(UPDATED_B_KFL)
            .bPjz(UPDATED_B_PJZ)
            .bYsfz(UPDATED_B_YSFZ)
            .bSjfz(UPDATED_B_SJFZ)
            .bFzcz(UPDATED_B_FZCZ)
            .bPjzcz(UPDATED_B_PJZCZ)
            .zSl(UPDATED_Z_SL)
            .zKfl(UPDATED_Z_KFL)
            .zPjz(UPDATED_Z_PJZ)
            .zYsfz(UPDATED_Z_YSFZ)
            .zSjfz(UPDATED_Z_SJFZ)
            .zFzcz(UPDATED_Z_FZCZ)
            .zPjzcz(UPDATED_Z_PJZCZ)
            .zk(UPDATED_ZK);
        return czBqz;
    }

    @BeforeEach
    public void initTest() {
        czBqz = createEntity(em);
    }

    @Test
    @Transactional
    void createCzBqz() throws Exception {
        int databaseSizeBeforeCreate = czBqzRepository.findAll().size();
        // Create the CzBqz
        CzBqzDTO czBqzDTO = czBqzMapper.toDto(czBqz);
        restCzBqzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czBqzDTO)))
            .andExpect(status().isCreated());

        // Validate the CzBqz in the database
        List<CzBqz> czBqzList = czBqzRepository.findAll();
        assertThat(czBqzList).hasSize(databaseSizeBeforeCreate + 1);
        CzBqz testCzBqz = czBqzList.get(czBqzList.size() - 1);
        assertThat(testCzBqz.getRq()).isEqualTo(DEFAULT_RQ);
        assertThat(testCzBqz.getqSl()).isEqualTo(DEFAULT_Q_SL);
        assertThat(testCzBqz.getqKfl()).isEqualByComparingTo(DEFAULT_Q_KFL);
        assertThat(testCzBqz.getqPjz()).isEqualByComparingTo(DEFAULT_Q_PJZ);
        assertThat(testCzBqz.getqYsfz()).isEqualByComparingTo(DEFAULT_Q_YSFZ);
        assertThat(testCzBqz.getqSjfz()).isEqualByComparingTo(DEFAULT_Q_SJFZ);
        assertThat(testCzBqz.getqFzcz()).isEqualByComparingTo(DEFAULT_Q_FZCZ);
        assertThat(testCzBqz.getqPjzcz()).isEqualByComparingTo(DEFAULT_Q_PJZCZ);
        assertThat(testCzBqz.getbSl()).isEqualTo(DEFAULT_B_SL);
        assertThat(testCzBqz.getbKfl()).isEqualByComparingTo(DEFAULT_B_KFL);
        assertThat(testCzBqz.getbPjz()).isEqualByComparingTo(DEFAULT_B_PJZ);
        assertThat(testCzBqz.getbYsfz()).isEqualByComparingTo(DEFAULT_B_YSFZ);
        assertThat(testCzBqz.getbSjfz()).isEqualByComparingTo(DEFAULT_B_SJFZ);
        assertThat(testCzBqz.getbFzcz()).isEqualByComparingTo(DEFAULT_B_FZCZ);
        assertThat(testCzBqz.getbPjzcz()).isEqualByComparingTo(DEFAULT_B_PJZCZ);
        assertThat(testCzBqz.getzSl()).isEqualTo(DEFAULT_Z_SL);
        assertThat(testCzBqz.getzKfl()).isEqualByComparingTo(DEFAULT_Z_KFL);
        assertThat(testCzBqz.getzPjz()).isEqualByComparingTo(DEFAULT_Z_PJZ);
        assertThat(testCzBqz.getzYsfz()).isEqualByComparingTo(DEFAULT_Z_YSFZ);
        assertThat(testCzBqz.getzSjfz()).isEqualByComparingTo(DEFAULT_Z_SJFZ);
        assertThat(testCzBqz.getzFzcz()).isEqualByComparingTo(DEFAULT_Z_FZCZ);
        assertThat(testCzBqz.getzPjzcz()).isEqualByComparingTo(DEFAULT_Z_PJZCZ);
        assertThat(testCzBqz.getZk()).isEqualByComparingTo(DEFAULT_ZK);

        // Validate the CzBqz in Elasticsearch
        verify(mockCzBqzSearchRepository, times(1)).save(testCzBqz);
    }

    @Test
    @Transactional
    void createCzBqzWithExistingId() throws Exception {
        // Create the CzBqz with an existing ID
        czBqz.setId(1L);
        CzBqzDTO czBqzDTO = czBqzMapper.toDto(czBqz);

        int databaseSizeBeforeCreate = czBqzRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCzBqzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czBqzDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CzBqz in the database
        List<CzBqz> czBqzList = czBqzRepository.findAll();
        assertThat(czBqzList).hasSize(databaseSizeBeforeCreate);

        // Validate the CzBqz in Elasticsearch
        verify(mockCzBqzSearchRepository, times(0)).save(czBqz);
    }

    @Test
    @Transactional
    void getAllCzBqzs() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList
        restCzBqzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(czBqz.getId().intValue())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].qSl").value(hasItem(DEFAULT_Q_SL.intValue())))
            .andExpect(jsonPath("$.[*].qKfl").value(hasItem(sameNumber(DEFAULT_Q_KFL))))
            .andExpect(jsonPath("$.[*].qPjz").value(hasItem(sameNumber(DEFAULT_Q_PJZ))))
            .andExpect(jsonPath("$.[*].qYsfz").value(hasItem(sameNumber(DEFAULT_Q_YSFZ))))
            .andExpect(jsonPath("$.[*].qSjfz").value(hasItem(sameNumber(DEFAULT_Q_SJFZ))))
            .andExpect(jsonPath("$.[*].qFzcz").value(hasItem(sameNumber(DEFAULT_Q_FZCZ))))
            .andExpect(jsonPath("$.[*].qPjzcz").value(hasItem(sameNumber(DEFAULT_Q_PJZCZ))))
            .andExpect(jsonPath("$.[*].bSl").value(hasItem(DEFAULT_B_SL.intValue())))
            .andExpect(jsonPath("$.[*].bKfl").value(hasItem(sameNumber(DEFAULT_B_KFL))))
            .andExpect(jsonPath("$.[*].bPjz").value(hasItem(sameNumber(DEFAULT_B_PJZ))))
            .andExpect(jsonPath("$.[*].bYsfz").value(hasItem(sameNumber(DEFAULT_B_YSFZ))))
            .andExpect(jsonPath("$.[*].bSjfz").value(hasItem(sameNumber(DEFAULT_B_SJFZ))))
            .andExpect(jsonPath("$.[*].bFzcz").value(hasItem(sameNumber(DEFAULT_B_FZCZ))))
            .andExpect(jsonPath("$.[*].bPjzcz").value(hasItem(sameNumber(DEFAULT_B_PJZCZ))))
            .andExpect(jsonPath("$.[*].zSl").value(hasItem(DEFAULT_Z_SL.intValue())))
            .andExpect(jsonPath("$.[*].zKfl").value(hasItem(sameNumber(DEFAULT_Z_KFL))))
            .andExpect(jsonPath("$.[*].zPjz").value(hasItem(sameNumber(DEFAULT_Z_PJZ))))
            .andExpect(jsonPath("$.[*].zYsfz").value(hasItem(sameNumber(DEFAULT_Z_YSFZ))))
            .andExpect(jsonPath("$.[*].zSjfz").value(hasItem(sameNumber(DEFAULT_Z_SJFZ))))
            .andExpect(jsonPath("$.[*].zFzcz").value(hasItem(sameNumber(DEFAULT_Z_FZCZ))))
            .andExpect(jsonPath("$.[*].zPjzcz").value(hasItem(sameNumber(DEFAULT_Z_PJZCZ))))
            .andExpect(jsonPath("$.[*].zk").value(hasItem(sameNumber(DEFAULT_ZK))));
    }

    @Test
    @Transactional
    void getCzBqz() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get the czBqz
        restCzBqzMockMvc
            .perform(get(ENTITY_API_URL_ID, czBqz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(czBqz.getId().intValue()))
            .andExpect(jsonPath("$.rq").value(DEFAULT_RQ.toString()))
            .andExpect(jsonPath("$.qSl").value(DEFAULT_Q_SL.intValue()))
            .andExpect(jsonPath("$.qKfl").value(sameNumber(DEFAULT_Q_KFL)))
            .andExpect(jsonPath("$.qPjz").value(sameNumber(DEFAULT_Q_PJZ)))
            .andExpect(jsonPath("$.qYsfz").value(sameNumber(DEFAULT_Q_YSFZ)))
            .andExpect(jsonPath("$.qSjfz").value(sameNumber(DEFAULT_Q_SJFZ)))
            .andExpect(jsonPath("$.qFzcz").value(sameNumber(DEFAULT_Q_FZCZ)))
            .andExpect(jsonPath("$.qPjzcz").value(sameNumber(DEFAULT_Q_PJZCZ)))
            .andExpect(jsonPath("$.bSl").value(DEFAULT_B_SL.intValue()))
            .andExpect(jsonPath("$.bKfl").value(sameNumber(DEFAULT_B_KFL)))
            .andExpect(jsonPath("$.bPjz").value(sameNumber(DEFAULT_B_PJZ)))
            .andExpect(jsonPath("$.bYsfz").value(sameNumber(DEFAULT_B_YSFZ)))
            .andExpect(jsonPath("$.bSjfz").value(sameNumber(DEFAULT_B_SJFZ)))
            .andExpect(jsonPath("$.bFzcz").value(sameNumber(DEFAULT_B_FZCZ)))
            .andExpect(jsonPath("$.bPjzcz").value(sameNumber(DEFAULT_B_PJZCZ)))
            .andExpect(jsonPath("$.zSl").value(DEFAULT_Z_SL.intValue()))
            .andExpect(jsonPath("$.zKfl").value(sameNumber(DEFAULT_Z_KFL)))
            .andExpect(jsonPath("$.zPjz").value(sameNumber(DEFAULT_Z_PJZ)))
            .andExpect(jsonPath("$.zYsfz").value(sameNumber(DEFAULT_Z_YSFZ)))
            .andExpect(jsonPath("$.zSjfz").value(sameNumber(DEFAULT_Z_SJFZ)))
            .andExpect(jsonPath("$.zFzcz").value(sameNumber(DEFAULT_Z_FZCZ)))
            .andExpect(jsonPath("$.zPjzcz").value(sameNumber(DEFAULT_Z_PJZCZ)))
            .andExpect(jsonPath("$.zk").value(sameNumber(DEFAULT_ZK)));
    }

    @Test
    @Transactional
    void getCzBqzsByIdFiltering() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        Long id = czBqz.getId();

        defaultCzBqzShouldBeFound("id.equals=" + id);
        defaultCzBqzShouldNotBeFound("id.notEquals=" + id);

        defaultCzBqzShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCzBqzShouldNotBeFound("id.greaterThan=" + id);

        defaultCzBqzShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCzBqzShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCzBqzsByRqIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where rq equals to DEFAULT_RQ
        defaultCzBqzShouldBeFound("rq.equals=" + DEFAULT_RQ);

        // Get all the czBqzList where rq equals to UPDATED_RQ
        defaultCzBqzShouldNotBeFound("rq.equals=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByRqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where rq not equals to DEFAULT_RQ
        defaultCzBqzShouldNotBeFound("rq.notEquals=" + DEFAULT_RQ);

        // Get all the czBqzList where rq not equals to UPDATED_RQ
        defaultCzBqzShouldBeFound("rq.notEquals=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByRqIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where rq in DEFAULT_RQ or UPDATED_RQ
        defaultCzBqzShouldBeFound("rq.in=" + DEFAULT_RQ + "," + UPDATED_RQ);

        // Get all the czBqzList where rq equals to UPDATED_RQ
        defaultCzBqzShouldNotBeFound("rq.in=" + UPDATED_RQ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByRqIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where rq is not null
        defaultCzBqzShouldBeFound("rq.specified=true");

        // Get all the czBqzList where rq is null
        defaultCzBqzShouldNotBeFound("rq.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSlIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSl equals to DEFAULT_Q_SL
        defaultCzBqzShouldBeFound("qSl.equals=" + DEFAULT_Q_SL);

        // Get all the czBqzList where qSl equals to UPDATED_Q_SL
        defaultCzBqzShouldNotBeFound("qSl.equals=" + UPDATED_Q_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSl not equals to DEFAULT_Q_SL
        defaultCzBqzShouldNotBeFound("qSl.notEquals=" + DEFAULT_Q_SL);

        // Get all the czBqzList where qSl not equals to UPDATED_Q_SL
        defaultCzBqzShouldBeFound("qSl.notEquals=" + UPDATED_Q_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSlIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSl in DEFAULT_Q_SL or UPDATED_Q_SL
        defaultCzBqzShouldBeFound("qSl.in=" + DEFAULT_Q_SL + "," + UPDATED_Q_SL);

        // Get all the czBqzList where qSl equals to UPDATED_Q_SL
        defaultCzBqzShouldNotBeFound("qSl.in=" + UPDATED_Q_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSlIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSl is not null
        defaultCzBqzShouldBeFound("qSl.specified=true");

        // Get all the czBqzList where qSl is null
        defaultCzBqzShouldNotBeFound("qSl.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSl is greater than or equal to DEFAULT_Q_SL
        defaultCzBqzShouldBeFound("qSl.greaterThanOrEqual=" + DEFAULT_Q_SL);

        // Get all the czBqzList where qSl is greater than or equal to UPDATED_Q_SL
        defaultCzBqzShouldNotBeFound("qSl.greaterThanOrEqual=" + UPDATED_Q_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSl is less than or equal to DEFAULT_Q_SL
        defaultCzBqzShouldBeFound("qSl.lessThanOrEqual=" + DEFAULT_Q_SL);

        // Get all the czBqzList where qSl is less than or equal to SMALLER_Q_SL
        defaultCzBqzShouldNotBeFound("qSl.lessThanOrEqual=" + SMALLER_Q_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSlIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSl is less than DEFAULT_Q_SL
        defaultCzBqzShouldNotBeFound("qSl.lessThan=" + DEFAULT_Q_SL);

        // Get all the czBqzList where qSl is less than UPDATED_Q_SL
        defaultCzBqzShouldBeFound("qSl.lessThan=" + UPDATED_Q_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSl is greater than DEFAULT_Q_SL
        defaultCzBqzShouldNotBeFound("qSl.greaterThan=" + DEFAULT_Q_SL);

        // Get all the czBqzList where qSl is greater than SMALLER_Q_SL
        defaultCzBqzShouldBeFound("qSl.greaterThan=" + SMALLER_Q_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqKflIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qKfl equals to DEFAULT_Q_KFL
        defaultCzBqzShouldBeFound("qKfl.equals=" + DEFAULT_Q_KFL);

        // Get all the czBqzList where qKfl equals to UPDATED_Q_KFL
        defaultCzBqzShouldNotBeFound("qKfl.equals=" + UPDATED_Q_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqKflIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qKfl not equals to DEFAULT_Q_KFL
        defaultCzBqzShouldNotBeFound("qKfl.notEquals=" + DEFAULT_Q_KFL);

        // Get all the czBqzList where qKfl not equals to UPDATED_Q_KFL
        defaultCzBqzShouldBeFound("qKfl.notEquals=" + UPDATED_Q_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqKflIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qKfl in DEFAULT_Q_KFL or UPDATED_Q_KFL
        defaultCzBqzShouldBeFound("qKfl.in=" + DEFAULT_Q_KFL + "," + UPDATED_Q_KFL);

        // Get all the czBqzList where qKfl equals to UPDATED_Q_KFL
        defaultCzBqzShouldNotBeFound("qKfl.in=" + UPDATED_Q_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqKflIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qKfl is not null
        defaultCzBqzShouldBeFound("qKfl.specified=true");

        // Get all the czBqzList where qKfl is null
        defaultCzBqzShouldNotBeFound("qKfl.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByqKflIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qKfl is greater than or equal to DEFAULT_Q_KFL
        defaultCzBqzShouldBeFound("qKfl.greaterThanOrEqual=" + DEFAULT_Q_KFL);

        // Get all the czBqzList where qKfl is greater than or equal to UPDATED_Q_KFL
        defaultCzBqzShouldNotBeFound("qKfl.greaterThanOrEqual=" + UPDATED_Q_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqKflIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qKfl is less than or equal to DEFAULT_Q_KFL
        defaultCzBqzShouldBeFound("qKfl.lessThanOrEqual=" + DEFAULT_Q_KFL);

        // Get all the czBqzList where qKfl is less than or equal to SMALLER_Q_KFL
        defaultCzBqzShouldNotBeFound("qKfl.lessThanOrEqual=" + SMALLER_Q_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqKflIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qKfl is less than DEFAULT_Q_KFL
        defaultCzBqzShouldNotBeFound("qKfl.lessThan=" + DEFAULT_Q_KFL);

        // Get all the czBqzList where qKfl is less than UPDATED_Q_KFL
        defaultCzBqzShouldBeFound("qKfl.lessThan=" + UPDATED_Q_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqKflIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qKfl is greater than DEFAULT_Q_KFL
        defaultCzBqzShouldNotBeFound("qKfl.greaterThan=" + DEFAULT_Q_KFL);

        // Get all the czBqzList where qKfl is greater than SMALLER_Q_KFL
        defaultCzBqzShouldBeFound("qKfl.greaterThan=" + SMALLER_Q_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjz equals to DEFAULT_Q_PJZ
        defaultCzBqzShouldBeFound("qPjz.equals=" + DEFAULT_Q_PJZ);

        // Get all the czBqzList where qPjz equals to UPDATED_Q_PJZ
        defaultCzBqzShouldNotBeFound("qPjz.equals=" + UPDATED_Q_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjz not equals to DEFAULT_Q_PJZ
        defaultCzBqzShouldNotBeFound("qPjz.notEquals=" + DEFAULT_Q_PJZ);

        // Get all the czBqzList where qPjz not equals to UPDATED_Q_PJZ
        defaultCzBqzShouldBeFound("qPjz.notEquals=" + UPDATED_Q_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjz in DEFAULT_Q_PJZ or UPDATED_Q_PJZ
        defaultCzBqzShouldBeFound("qPjz.in=" + DEFAULT_Q_PJZ + "," + UPDATED_Q_PJZ);

        // Get all the czBqzList where qPjz equals to UPDATED_Q_PJZ
        defaultCzBqzShouldNotBeFound("qPjz.in=" + UPDATED_Q_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjz is not null
        defaultCzBqzShouldBeFound("qPjz.specified=true");

        // Get all the czBqzList where qPjz is null
        defaultCzBqzShouldNotBeFound("qPjz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjz is greater than or equal to DEFAULT_Q_PJZ
        defaultCzBqzShouldBeFound("qPjz.greaterThanOrEqual=" + DEFAULT_Q_PJZ);

        // Get all the czBqzList where qPjz is greater than or equal to UPDATED_Q_PJZ
        defaultCzBqzShouldNotBeFound("qPjz.greaterThanOrEqual=" + UPDATED_Q_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjz is less than or equal to DEFAULT_Q_PJZ
        defaultCzBqzShouldBeFound("qPjz.lessThanOrEqual=" + DEFAULT_Q_PJZ);

        // Get all the czBqzList where qPjz is less than or equal to SMALLER_Q_PJZ
        defaultCzBqzShouldNotBeFound("qPjz.lessThanOrEqual=" + SMALLER_Q_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjz is less than DEFAULT_Q_PJZ
        defaultCzBqzShouldNotBeFound("qPjz.lessThan=" + DEFAULT_Q_PJZ);

        // Get all the czBqzList where qPjz is less than UPDATED_Q_PJZ
        defaultCzBqzShouldBeFound("qPjz.lessThan=" + UPDATED_Q_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjz is greater than DEFAULT_Q_PJZ
        defaultCzBqzShouldNotBeFound("qPjz.greaterThan=" + DEFAULT_Q_PJZ);

        // Get all the czBqzList where qPjz is greater than SMALLER_Q_PJZ
        defaultCzBqzShouldBeFound("qPjz.greaterThan=" + SMALLER_Q_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqYsfzIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qYsfz equals to DEFAULT_Q_YSFZ
        defaultCzBqzShouldBeFound("qYsfz.equals=" + DEFAULT_Q_YSFZ);

        // Get all the czBqzList where qYsfz equals to UPDATED_Q_YSFZ
        defaultCzBqzShouldNotBeFound("qYsfz.equals=" + UPDATED_Q_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqYsfzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qYsfz not equals to DEFAULT_Q_YSFZ
        defaultCzBqzShouldNotBeFound("qYsfz.notEquals=" + DEFAULT_Q_YSFZ);

        // Get all the czBqzList where qYsfz not equals to UPDATED_Q_YSFZ
        defaultCzBqzShouldBeFound("qYsfz.notEquals=" + UPDATED_Q_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqYsfzIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qYsfz in DEFAULT_Q_YSFZ or UPDATED_Q_YSFZ
        defaultCzBqzShouldBeFound("qYsfz.in=" + DEFAULT_Q_YSFZ + "," + UPDATED_Q_YSFZ);

        // Get all the czBqzList where qYsfz equals to UPDATED_Q_YSFZ
        defaultCzBqzShouldNotBeFound("qYsfz.in=" + UPDATED_Q_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqYsfzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qYsfz is not null
        defaultCzBqzShouldBeFound("qYsfz.specified=true");

        // Get all the czBqzList where qYsfz is null
        defaultCzBqzShouldNotBeFound("qYsfz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByqYsfzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qYsfz is greater than or equal to DEFAULT_Q_YSFZ
        defaultCzBqzShouldBeFound("qYsfz.greaterThanOrEqual=" + DEFAULT_Q_YSFZ);

        // Get all the czBqzList where qYsfz is greater than or equal to UPDATED_Q_YSFZ
        defaultCzBqzShouldNotBeFound("qYsfz.greaterThanOrEqual=" + UPDATED_Q_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqYsfzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qYsfz is less than or equal to DEFAULT_Q_YSFZ
        defaultCzBqzShouldBeFound("qYsfz.lessThanOrEqual=" + DEFAULT_Q_YSFZ);

        // Get all the czBqzList where qYsfz is less than or equal to SMALLER_Q_YSFZ
        defaultCzBqzShouldNotBeFound("qYsfz.lessThanOrEqual=" + SMALLER_Q_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqYsfzIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qYsfz is less than DEFAULT_Q_YSFZ
        defaultCzBqzShouldNotBeFound("qYsfz.lessThan=" + DEFAULT_Q_YSFZ);

        // Get all the czBqzList where qYsfz is less than UPDATED_Q_YSFZ
        defaultCzBqzShouldBeFound("qYsfz.lessThan=" + UPDATED_Q_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqYsfzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qYsfz is greater than DEFAULT_Q_YSFZ
        defaultCzBqzShouldNotBeFound("qYsfz.greaterThan=" + DEFAULT_Q_YSFZ);

        // Get all the czBqzList where qYsfz is greater than SMALLER_Q_YSFZ
        defaultCzBqzShouldBeFound("qYsfz.greaterThan=" + SMALLER_Q_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSjfzIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSjfz equals to DEFAULT_Q_SJFZ
        defaultCzBqzShouldBeFound("qSjfz.equals=" + DEFAULT_Q_SJFZ);

        // Get all the czBqzList where qSjfz equals to UPDATED_Q_SJFZ
        defaultCzBqzShouldNotBeFound("qSjfz.equals=" + UPDATED_Q_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSjfzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSjfz not equals to DEFAULT_Q_SJFZ
        defaultCzBqzShouldNotBeFound("qSjfz.notEquals=" + DEFAULT_Q_SJFZ);

        // Get all the czBqzList where qSjfz not equals to UPDATED_Q_SJFZ
        defaultCzBqzShouldBeFound("qSjfz.notEquals=" + UPDATED_Q_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSjfzIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSjfz in DEFAULT_Q_SJFZ or UPDATED_Q_SJFZ
        defaultCzBqzShouldBeFound("qSjfz.in=" + DEFAULT_Q_SJFZ + "," + UPDATED_Q_SJFZ);

        // Get all the czBqzList where qSjfz equals to UPDATED_Q_SJFZ
        defaultCzBqzShouldNotBeFound("qSjfz.in=" + UPDATED_Q_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSjfzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSjfz is not null
        defaultCzBqzShouldBeFound("qSjfz.specified=true");

        // Get all the czBqzList where qSjfz is null
        defaultCzBqzShouldNotBeFound("qSjfz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSjfzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSjfz is greater than or equal to DEFAULT_Q_SJFZ
        defaultCzBqzShouldBeFound("qSjfz.greaterThanOrEqual=" + DEFAULT_Q_SJFZ);

        // Get all the czBqzList where qSjfz is greater than or equal to UPDATED_Q_SJFZ
        defaultCzBqzShouldNotBeFound("qSjfz.greaterThanOrEqual=" + UPDATED_Q_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSjfzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSjfz is less than or equal to DEFAULT_Q_SJFZ
        defaultCzBqzShouldBeFound("qSjfz.lessThanOrEqual=" + DEFAULT_Q_SJFZ);

        // Get all the czBqzList where qSjfz is less than or equal to SMALLER_Q_SJFZ
        defaultCzBqzShouldNotBeFound("qSjfz.lessThanOrEqual=" + SMALLER_Q_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSjfzIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSjfz is less than DEFAULT_Q_SJFZ
        defaultCzBqzShouldNotBeFound("qSjfz.lessThan=" + DEFAULT_Q_SJFZ);

        // Get all the czBqzList where qSjfz is less than UPDATED_Q_SJFZ
        defaultCzBqzShouldBeFound("qSjfz.lessThan=" + UPDATED_Q_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqSjfzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qSjfz is greater than DEFAULT_Q_SJFZ
        defaultCzBqzShouldNotBeFound("qSjfz.greaterThan=" + DEFAULT_Q_SJFZ);

        // Get all the czBqzList where qSjfz is greater than SMALLER_Q_SJFZ
        defaultCzBqzShouldBeFound("qSjfz.greaterThan=" + SMALLER_Q_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqFzczIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qFzcz equals to DEFAULT_Q_FZCZ
        defaultCzBqzShouldBeFound("qFzcz.equals=" + DEFAULT_Q_FZCZ);

        // Get all the czBqzList where qFzcz equals to UPDATED_Q_FZCZ
        defaultCzBqzShouldNotBeFound("qFzcz.equals=" + UPDATED_Q_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqFzczIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qFzcz not equals to DEFAULT_Q_FZCZ
        defaultCzBqzShouldNotBeFound("qFzcz.notEquals=" + DEFAULT_Q_FZCZ);

        // Get all the czBqzList where qFzcz not equals to UPDATED_Q_FZCZ
        defaultCzBqzShouldBeFound("qFzcz.notEquals=" + UPDATED_Q_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqFzczIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qFzcz in DEFAULT_Q_FZCZ or UPDATED_Q_FZCZ
        defaultCzBqzShouldBeFound("qFzcz.in=" + DEFAULT_Q_FZCZ + "," + UPDATED_Q_FZCZ);

        // Get all the czBqzList where qFzcz equals to UPDATED_Q_FZCZ
        defaultCzBqzShouldNotBeFound("qFzcz.in=" + UPDATED_Q_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqFzczIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qFzcz is not null
        defaultCzBqzShouldBeFound("qFzcz.specified=true");

        // Get all the czBqzList where qFzcz is null
        defaultCzBqzShouldNotBeFound("qFzcz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByqFzczIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qFzcz is greater than or equal to DEFAULT_Q_FZCZ
        defaultCzBqzShouldBeFound("qFzcz.greaterThanOrEqual=" + DEFAULT_Q_FZCZ);

        // Get all the czBqzList where qFzcz is greater than or equal to UPDATED_Q_FZCZ
        defaultCzBqzShouldNotBeFound("qFzcz.greaterThanOrEqual=" + UPDATED_Q_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqFzczIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qFzcz is less than or equal to DEFAULT_Q_FZCZ
        defaultCzBqzShouldBeFound("qFzcz.lessThanOrEqual=" + DEFAULT_Q_FZCZ);

        // Get all the czBqzList where qFzcz is less than or equal to SMALLER_Q_FZCZ
        defaultCzBqzShouldNotBeFound("qFzcz.lessThanOrEqual=" + SMALLER_Q_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqFzczIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qFzcz is less than DEFAULT_Q_FZCZ
        defaultCzBqzShouldNotBeFound("qFzcz.lessThan=" + DEFAULT_Q_FZCZ);

        // Get all the czBqzList where qFzcz is less than UPDATED_Q_FZCZ
        defaultCzBqzShouldBeFound("qFzcz.lessThan=" + UPDATED_Q_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqFzczIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qFzcz is greater than DEFAULT_Q_FZCZ
        defaultCzBqzShouldNotBeFound("qFzcz.greaterThan=" + DEFAULT_Q_FZCZ);

        // Get all the czBqzList where qFzcz is greater than SMALLER_Q_FZCZ
        defaultCzBqzShouldBeFound("qFzcz.greaterThan=" + SMALLER_Q_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzczIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjzcz equals to DEFAULT_Q_PJZCZ
        defaultCzBqzShouldBeFound("qPjzcz.equals=" + DEFAULT_Q_PJZCZ);

        // Get all the czBqzList where qPjzcz equals to UPDATED_Q_PJZCZ
        defaultCzBqzShouldNotBeFound("qPjzcz.equals=" + UPDATED_Q_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzczIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjzcz not equals to DEFAULT_Q_PJZCZ
        defaultCzBqzShouldNotBeFound("qPjzcz.notEquals=" + DEFAULT_Q_PJZCZ);

        // Get all the czBqzList where qPjzcz not equals to UPDATED_Q_PJZCZ
        defaultCzBqzShouldBeFound("qPjzcz.notEquals=" + UPDATED_Q_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzczIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjzcz in DEFAULT_Q_PJZCZ or UPDATED_Q_PJZCZ
        defaultCzBqzShouldBeFound("qPjzcz.in=" + DEFAULT_Q_PJZCZ + "," + UPDATED_Q_PJZCZ);

        // Get all the czBqzList where qPjzcz equals to UPDATED_Q_PJZCZ
        defaultCzBqzShouldNotBeFound("qPjzcz.in=" + UPDATED_Q_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzczIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjzcz is not null
        defaultCzBqzShouldBeFound("qPjzcz.specified=true");

        // Get all the czBqzList where qPjzcz is null
        defaultCzBqzShouldNotBeFound("qPjzcz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzczIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjzcz is greater than or equal to DEFAULT_Q_PJZCZ
        defaultCzBqzShouldBeFound("qPjzcz.greaterThanOrEqual=" + DEFAULT_Q_PJZCZ);

        // Get all the czBqzList where qPjzcz is greater than or equal to UPDATED_Q_PJZCZ
        defaultCzBqzShouldNotBeFound("qPjzcz.greaterThanOrEqual=" + UPDATED_Q_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzczIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjzcz is less than or equal to DEFAULT_Q_PJZCZ
        defaultCzBqzShouldBeFound("qPjzcz.lessThanOrEqual=" + DEFAULT_Q_PJZCZ);

        // Get all the czBqzList where qPjzcz is less than or equal to SMALLER_Q_PJZCZ
        defaultCzBqzShouldNotBeFound("qPjzcz.lessThanOrEqual=" + SMALLER_Q_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzczIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjzcz is less than DEFAULT_Q_PJZCZ
        defaultCzBqzShouldNotBeFound("qPjzcz.lessThan=" + DEFAULT_Q_PJZCZ);

        // Get all the czBqzList where qPjzcz is less than UPDATED_Q_PJZCZ
        defaultCzBqzShouldBeFound("qPjzcz.lessThan=" + UPDATED_Q_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByqPjzczIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where qPjzcz is greater than DEFAULT_Q_PJZCZ
        defaultCzBqzShouldNotBeFound("qPjzcz.greaterThan=" + DEFAULT_Q_PJZCZ);

        // Get all the czBqzList where qPjzcz is greater than SMALLER_Q_PJZCZ
        defaultCzBqzShouldBeFound("qPjzcz.greaterThan=" + SMALLER_Q_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSlIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSl equals to DEFAULT_B_SL
        defaultCzBqzShouldBeFound("bSl.equals=" + DEFAULT_B_SL);

        // Get all the czBqzList where bSl equals to UPDATED_B_SL
        defaultCzBqzShouldNotBeFound("bSl.equals=" + UPDATED_B_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSl not equals to DEFAULT_B_SL
        defaultCzBqzShouldNotBeFound("bSl.notEquals=" + DEFAULT_B_SL);

        // Get all the czBqzList where bSl not equals to UPDATED_B_SL
        defaultCzBqzShouldBeFound("bSl.notEquals=" + UPDATED_B_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSlIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSl in DEFAULT_B_SL or UPDATED_B_SL
        defaultCzBqzShouldBeFound("bSl.in=" + DEFAULT_B_SL + "," + UPDATED_B_SL);

        // Get all the czBqzList where bSl equals to UPDATED_B_SL
        defaultCzBqzShouldNotBeFound("bSl.in=" + UPDATED_B_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSlIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSl is not null
        defaultCzBqzShouldBeFound("bSl.specified=true");

        // Get all the czBqzList where bSl is null
        defaultCzBqzShouldNotBeFound("bSl.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSl is greater than or equal to DEFAULT_B_SL
        defaultCzBqzShouldBeFound("bSl.greaterThanOrEqual=" + DEFAULT_B_SL);

        // Get all the czBqzList where bSl is greater than or equal to UPDATED_B_SL
        defaultCzBqzShouldNotBeFound("bSl.greaterThanOrEqual=" + UPDATED_B_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSl is less than or equal to DEFAULT_B_SL
        defaultCzBqzShouldBeFound("bSl.lessThanOrEqual=" + DEFAULT_B_SL);

        // Get all the czBqzList where bSl is less than or equal to SMALLER_B_SL
        defaultCzBqzShouldNotBeFound("bSl.lessThanOrEqual=" + SMALLER_B_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSlIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSl is less than DEFAULT_B_SL
        defaultCzBqzShouldNotBeFound("bSl.lessThan=" + DEFAULT_B_SL);

        // Get all the czBqzList where bSl is less than UPDATED_B_SL
        defaultCzBqzShouldBeFound("bSl.lessThan=" + UPDATED_B_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSl is greater than DEFAULT_B_SL
        defaultCzBqzShouldNotBeFound("bSl.greaterThan=" + DEFAULT_B_SL);

        // Get all the czBqzList where bSl is greater than SMALLER_B_SL
        defaultCzBqzShouldBeFound("bSl.greaterThan=" + SMALLER_B_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybKflIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bKfl equals to DEFAULT_B_KFL
        defaultCzBqzShouldBeFound("bKfl.equals=" + DEFAULT_B_KFL);

        // Get all the czBqzList where bKfl equals to UPDATED_B_KFL
        defaultCzBqzShouldNotBeFound("bKfl.equals=" + UPDATED_B_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybKflIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bKfl not equals to DEFAULT_B_KFL
        defaultCzBqzShouldNotBeFound("bKfl.notEquals=" + DEFAULT_B_KFL);

        // Get all the czBqzList where bKfl not equals to UPDATED_B_KFL
        defaultCzBqzShouldBeFound("bKfl.notEquals=" + UPDATED_B_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybKflIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bKfl in DEFAULT_B_KFL or UPDATED_B_KFL
        defaultCzBqzShouldBeFound("bKfl.in=" + DEFAULT_B_KFL + "," + UPDATED_B_KFL);

        // Get all the czBqzList where bKfl equals to UPDATED_B_KFL
        defaultCzBqzShouldNotBeFound("bKfl.in=" + UPDATED_B_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybKflIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bKfl is not null
        defaultCzBqzShouldBeFound("bKfl.specified=true");

        // Get all the czBqzList where bKfl is null
        defaultCzBqzShouldNotBeFound("bKfl.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsBybKflIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bKfl is greater than or equal to DEFAULT_B_KFL
        defaultCzBqzShouldBeFound("bKfl.greaterThanOrEqual=" + DEFAULT_B_KFL);

        // Get all the czBqzList where bKfl is greater than or equal to UPDATED_B_KFL
        defaultCzBqzShouldNotBeFound("bKfl.greaterThanOrEqual=" + UPDATED_B_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybKflIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bKfl is less than or equal to DEFAULT_B_KFL
        defaultCzBqzShouldBeFound("bKfl.lessThanOrEqual=" + DEFAULT_B_KFL);

        // Get all the czBqzList where bKfl is less than or equal to SMALLER_B_KFL
        defaultCzBqzShouldNotBeFound("bKfl.lessThanOrEqual=" + SMALLER_B_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybKflIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bKfl is less than DEFAULT_B_KFL
        defaultCzBqzShouldNotBeFound("bKfl.lessThan=" + DEFAULT_B_KFL);

        // Get all the czBqzList where bKfl is less than UPDATED_B_KFL
        defaultCzBqzShouldBeFound("bKfl.lessThan=" + UPDATED_B_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybKflIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bKfl is greater than DEFAULT_B_KFL
        defaultCzBqzShouldNotBeFound("bKfl.greaterThan=" + DEFAULT_B_KFL);

        // Get all the czBqzList where bKfl is greater than SMALLER_B_KFL
        defaultCzBqzShouldBeFound("bKfl.greaterThan=" + SMALLER_B_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjz equals to DEFAULT_B_PJZ
        defaultCzBqzShouldBeFound("bPjz.equals=" + DEFAULT_B_PJZ);

        // Get all the czBqzList where bPjz equals to UPDATED_B_PJZ
        defaultCzBqzShouldNotBeFound("bPjz.equals=" + UPDATED_B_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjz not equals to DEFAULT_B_PJZ
        defaultCzBqzShouldNotBeFound("bPjz.notEquals=" + DEFAULT_B_PJZ);

        // Get all the czBqzList where bPjz not equals to UPDATED_B_PJZ
        defaultCzBqzShouldBeFound("bPjz.notEquals=" + UPDATED_B_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjz in DEFAULT_B_PJZ or UPDATED_B_PJZ
        defaultCzBqzShouldBeFound("bPjz.in=" + DEFAULT_B_PJZ + "," + UPDATED_B_PJZ);

        // Get all the czBqzList where bPjz equals to UPDATED_B_PJZ
        defaultCzBqzShouldNotBeFound("bPjz.in=" + UPDATED_B_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjz is not null
        defaultCzBqzShouldBeFound("bPjz.specified=true");

        // Get all the czBqzList where bPjz is null
        defaultCzBqzShouldNotBeFound("bPjz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjz is greater than or equal to DEFAULT_B_PJZ
        defaultCzBqzShouldBeFound("bPjz.greaterThanOrEqual=" + DEFAULT_B_PJZ);

        // Get all the czBqzList where bPjz is greater than or equal to UPDATED_B_PJZ
        defaultCzBqzShouldNotBeFound("bPjz.greaterThanOrEqual=" + UPDATED_B_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjz is less than or equal to DEFAULT_B_PJZ
        defaultCzBqzShouldBeFound("bPjz.lessThanOrEqual=" + DEFAULT_B_PJZ);

        // Get all the czBqzList where bPjz is less than or equal to SMALLER_B_PJZ
        defaultCzBqzShouldNotBeFound("bPjz.lessThanOrEqual=" + SMALLER_B_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjz is less than DEFAULT_B_PJZ
        defaultCzBqzShouldNotBeFound("bPjz.lessThan=" + DEFAULT_B_PJZ);

        // Get all the czBqzList where bPjz is less than UPDATED_B_PJZ
        defaultCzBqzShouldBeFound("bPjz.lessThan=" + UPDATED_B_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjz is greater than DEFAULT_B_PJZ
        defaultCzBqzShouldNotBeFound("bPjz.greaterThan=" + DEFAULT_B_PJZ);

        // Get all the czBqzList where bPjz is greater than SMALLER_B_PJZ
        defaultCzBqzShouldBeFound("bPjz.greaterThan=" + SMALLER_B_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybYsfzIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bYsfz equals to DEFAULT_B_YSFZ
        defaultCzBqzShouldBeFound("bYsfz.equals=" + DEFAULT_B_YSFZ);

        // Get all the czBqzList where bYsfz equals to UPDATED_B_YSFZ
        defaultCzBqzShouldNotBeFound("bYsfz.equals=" + UPDATED_B_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybYsfzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bYsfz not equals to DEFAULT_B_YSFZ
        defaultCzBqzShouldNotBeFound("bYsfz.notEquals=" + DEFAULT_B_YSFZ);

        // Get all the czBqzList where bYsfz not equals to UPDATED_B_YSFZ
        defaultCzBqzShouldBeFound("bYsfz.notEquals=" + UPDATED_B_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybYsfzIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bYsfz in DEFAULT_B_YSFZ or UPDATED_B_YSFZ
        defaultCzBqzShouldBeFound("bYsfz.in=" + DEFAULT_B_YSFZ + "," + UPDATED_B_YSFZ);

        // Get all the czBqzList where bYsfz equals to UPDATED_B_YSFZ
        defaultCzBqzShouldNotBeFound("bYsfz.in=" + UPDATED_B_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybYsfzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bYsfz is not null
        defaultCzBqzShouldBeFound("bYsfz.specified=true");

        // Get all the czBqzList where bYsfz is null
        defaultCzBqzShouldNotBeFound("bYsfz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsBybYsfzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bYsfz is greater than or equal to DEFAULT_B_YSFZ
        defaultCzBqzShouldBeFound("bYsfz.greaterThanOrEqual=" + DEFAULT_B_YSFZ);

        // Get all the czBqzList where bYsfz is greater than or equal to UPDATED_B_YSFZ
        defaultCzBqzShouldNotBeFound("bYsfz.greaterThanOrEqual=" + UPDATED_B_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybYsfzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bYsfz is less than or equal to DEFAULT_B_YSFZ
        defaultCzBqzShouldBeFound("bYsfz.lessThanOrEqual=" + DEFAULT_B_YSFZ);

        // Get all the czBqzList where bYsfz is less than or equal to SMALLER_B_YSFZ
        defaultCzBqzShouldNotBeFound("bYsfz.lessThanOrEqual=" + SMALLER_B_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybYsfzIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bYsfz is less than DEFAULT_B_YSFZ
        defaultCzBqzShouldNotBeFound("bYsfz.lessThan=" + DEFAULT_B_YSFZ);

        // Get all the czBqzList where bYsfz is less than UPDATED_B_YSFZ
        defaultCzBqzShouldBeFound("bYsfz.lessThan=" + UPDATED_B_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybYsfzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bYsfz is greater than DEFAULT_B_YSFZ
        defaultCzBqzShouldNotBeFound("bYsfz.greaterThan=" + DEFAULT_B_YSFZ);

        // Get all the czBqzList where bYsfz is greater than SMALLER_B_YSFZ
        defaultCzBqzShouldBeFound("bYsfz.greaterThan=" + SMALLER_B_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSjfzIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSjfz equals to DEFAULT_B_SJFZ
        defaultCzBqzShouldBeFound("bSjfz.equals=" + DEFAULT_B_SJFZ);

        // Get all the czBqzList where bSjfz equals to UPDATED_B_SJFZ
        defaultCzBqzShouldNotBeFound("bSjfz.equals=" + UPDATED_B_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSjfzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSjfz not equals to DEFAULT_B_SJFZ
        defaultCzBqzShouldNotBeFound("bSjfz.notEquals=" + DEFAULT_B_SJFZ);

        // Get all the czBqzList where bSjfz not equals to UPDATED_B_SJFZ
        defaultCzBqzShouldBeFound("bSjfz.notEquals=" + UPDATED_B_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSjfzIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSjfz in DEFAULT_B_SJFZ or UPDATED_B_SJFZ
        defaultCzBqzShouldBeFound("bSjfz.in=" + DEFAULT_B_SJFZ + "," + UPDATED_B_SJFZ);

        // Get all the czBqzList where bSjfz equals to UPDATED_B_SJFZ
        defaultCzBqzShouldNotBeFound("bSjfz.in=" + UPDATED_B_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSjfzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSjfz is not null
        defaultCzBqzShouldBeFound("bSjfz.specified=true");

        // Get all the czBqzList where bSjfz is null
        defaultCzBqzShouldNotBeFound("bSjfz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSjfzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSjfz is greater than or equal to DEFAULT_B_SJFZ
        defaultCzBqzShouldBeFound("bSjfz.greaterThanOrEqual=" + DEFAULT_B_SJFZ);

        // Get all the czBqzList where bSjfz is greater than or equal to UPDATED_B_SJFZ
        defaultCzBqzShouldNotBeFound("bSjfz.greaterThanOrEqual=" + UPDATED_B_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSjfzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSjfz is less than or equal to DEFAULT_B_SJFZ
        defaultCzBqzShouldBeFound("bSjfz.lessThanOrEqual=" + DEFAULT_B_SJFZ);

        // Get all the czBqzList where bSjfz is less than or equal to SMALLER_B_SJFZ
        defaultCzBqzShouldNotBeFound("bSjfz.lessThanOrEqual=" + SMALLER_B_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSjfzIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSjfz is less than DEFAULT_B_SJFZ
        defaultCzBqzShouldNotBeFound("bSjfz.lessThan=" + DEFAULT_B_SJFZ);

        // Get all the czBqzList where bSjfz is less than UPDATED_B_SJFZ
        defaultCzBqzShouldBeFound("bSjfz.lessThan=" + UPDATED_B_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybSjfzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bSjfz is greater than DEFAULT_B_SJFZ
        defaultCzBqzShouldNotBeFound("bSjfz.greaterThan=" + DEFAULT_B_SJFZ);

        // Get all the czBqzList where bSjfz is greater than SMALLER_B_SJFZ
        defaultCzBqzShouldBeFound("bSjfz.greaterThan=" + SMALLER_B_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybFzczIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bFzcz equals to DEFAULT_B_FZCZ
        defaultCzBqzShouldBeFound("bFzcz.equals=" + DEFAULT_B_FZCZ);

        // Get all the czBqzList where bFzcz equals to UPDATED_B_FZCZ
        defaultCzBqzShouldNotBeFound("bFzcz.equals=" + UPDATED_B_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybFzczIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bFzcz not equals to DEFAULT_B_FZCZ
        defaultCzBqzShouldNotBeFound("bFzcz.notEquals=" + DEFAULT_B_FZCZ);

        // Get all the czBqzList where bFzcz not equals to UPDATED_B_FZCZ
        defaultCzBqzShouldBeFound("bFzcz.notEquals=" + UPDATED_B_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybFzczIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bFzcz in DEFAULT_B_FZCZ or UPDATED_B_FZCZ
        defaultCzBqzShouldBeFound("bFzcz.in=" + DEFAULT_B_FZCZ + "," + UPDATED_B_FZCZ);

        // Get all the czBqzList where bFzcz equals to UPDATED_B_FZCZ
        defaultCzBqzShouldNotBeFound("bFzcz.in=" + UPDATED_B_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybFzczIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bFzcz is not null
        defaultCzBqzShouldBeFound("bFzcz.specified=true");

        // Get all the czBqzList where bFzcz is null
        defaultCzBqzShouldNotBeFound("bFzcz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsBybFzczIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bFzcz is greater than or equal to DEFAULT_B_FZCZ
        defaultCzBqzShouldBeFound("bFzcz.greaterThanOrEqual=" + DEFAULT_B_FZCZ);

        // Get all the czBqzList where bFzcz is greater than or equal to UPDATED_B_FZCZ
        defaultCzBqzShouldNotBeFound("bFzcz.greaterThanOrEqual=" + UPDATED_B_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybFzczIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bFzcz is less than or equal to DEFAULT_B_FZCZ
        defaultCzBqzShouldBeFound("bFzcz.lessThanOrEqual=" + DEFAULT_B_FZCZ);

        // Get all the czBqzList where bFzcz is less than or equal to SMALLER_B_FZCZ
        defaultCzBqzShouldNotBeFound("bFzcz.lessThanOrEqual=" + SMALLER_B_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybFzczIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bFzcz is less than DEFAULT_B_FZCZ
        defaultCzBqzShouldNotBeFound("bFzcz.lessThan=" + DEFAULT_B_FZCZ);

        // Get all the czBqzList where bFzcz is less than UPDATED_B_FZCZ
        defaultCzBqzShouldBeFound("bFzcz.lessThan=" + UPDATED_B_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybFzczIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bFzcz is greater than DEFAULT_B_FZCZ
        defaultCzBqzShouldNotBeFound("bFzcz.greaterThan=" + DEFAULT_B_FZCZ);

        // Get all the czBqzList where bFzcz is greater than SMALLER_B_FZCZ
        defaultCzBqzShouldBeFound("bFzcz.greaterThan=" + SMALLER_B_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzczIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjzcz equals to DEFAULT_B_PJZCZ
        defaultCzBqzShouldBeFound("bPjzcz.equals=" + DEFAULT_B_PJZCZ);

        // Get all the czBqzList where bPjzcz equals to UPDATED_B_PJZCZ
        defaultCzBqzShouldNotBeFound("bPjzcz.equals=" + UPDATED_B_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzczIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjzcz not equals to DEFAULT_B_PJZCZ
        defaultCzBqzShouldNotBeFound("bPjzcz.notEquals=" + DEFAULT_B_PJZCZ);

        // Get all the czBqzList where bPjzcz not equals to UPDATED_B_PJZCZ
        defaultCzBqzShouldBeFound("bPjzcz.notEquals=" + UPDATED_B_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzczIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjzcz in DEFAULT_B_PJZCZ or UPDATED_B_PJZCZ
        defaultCzBqzShouldBeFound("bPjzcz.in=" + DEFAULT_B_PJZCZ + "," + UPDATED_B_PJZCZ);

        // Get all the czBqzList where bPjzcz equals to UPDATED_B_PJZCZ
        defaultCzBqzShouldNotBeFound("bPjzcz.in=" + UPDATED_B_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzczIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjzcz is not null
        defaultCzBqzShouldBeFound("bPjzcz.specified=true");

        // Get all the czBqzList where bPjzcz is null
        defaultCzBqzShouldNotBeFound("bPjzcz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzczIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjzcz is greater than or equal to DEFAULT_B_PJZCZ
        defaultCzBqzShouldBeFound("bPjzcz.greaterThanOrEqual=" + DEFAULT_B_PJZCZ);

        // Get all the czBqzList where bPjzcz is greater than or equal to UPDATED_B_PJZCZ
        defaultCzBqzShouldNotBeFound("bPjzcz.greaterThanOrEqual=" + UPDATED_B_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzczIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjzcz is less than or equal to DEFAULT_B_PJZCZ
        defaultCzBqzShouldBeFound("bPjzcz.lessThanOrEqual=" + DEFAULT_B_PJZCZ);

        // Get all the czBqzList where bPjzcz is less than or equal to SMALLER_B_PJZCZ
        defaultCzBqzShouldNotBeFound("bPjzcz.lessThanOrEqual=" + SMALLER_B_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzczIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjzcz is less than DEFAULT_B_PJZCZ
        defaultCzBqzShouldNotBeFound("bPjzcz.lessThan=" + DEFAULT_B_PJZCZ);

        // Get all the czBqzList where bPjzcz is less than UPDATED_B_PJZCZ
        defaultCzBqzShouldBeFound("bPjzcz.lessThan=" + UPDATED_B_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsBybPjzczIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where bPjzcz is greater than DEFAULT_B_PJZCZ
        defaultCzBqzShouldNotBeFound("bPjzcz.greaterThan=" + DEFAULT_B_PJZCZ);

        // Get all the czBqzList where bPjzcz is greater than SMALLER_B_PJZCZ
        defaultCzBqzShouldBeFound("bPjzcz.greaterThan=" + SMALLER_B_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSlIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSl equals to DEFAULT_Z_SL
        defaultCzBqzShouldBeFound("zSl.equals=" + DEFAULT_Z_SL);

        // Get all the czBqzList where zSl equals to UPDATED_Z_SL
        defaultCzBqzShouldNotBeFound("zSl.equals=" + UPDATED_Z_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSl not equals to DEFAULT_Z_SL
        defaultCzBqzShouldNotBeFound("zSl.notEquals=" + DEFAULT_Z_SL);

        // Get all the czBqzList where zSl not equals to UPDATED_Z_SL
        defaultCzBqzShouldBeFound("zSl.notEquals=" + UPDATED_Z_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSlIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSl in DEFAULT_Z_SL or UPDATED_Z_SL
        defaultCzBqzShouldBeFound("zSl.in=" + DEFAULT_Z_SL + "," + UPDATED_Z_SL);

        // Get all the czBqzList where zSl equals to UPDATED_Z_SL
        defaultCzBqzShouldNotBeFound("zSl.in=" + UPDATED_Z_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSlIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSl is not null
        defaultCzBqzShouldBeFound("zSl.specified=true");

        // Get all the czBqzList where zSl is null
        defaultCzBqzShouldNotBeFound("zSl.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSl is greater than or equal to DEFAULT_Z_SL
        defaultCzBqzShouldBeFound("zSl.greaterThanOrEqual=" + DEFAULT_Z_SL);

        // Get all the czBqzList where zSl is greater than or equal to UPDATED_Z_SL
        defaultCzBqzShouldNotBeFound("zSl.greaterThanOrEqual=" + UPDATED_Z_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSl is less than or equal to DEFAULT_Z_SL
        defaultCzBqzShouldBeFound("zSl.lessThanOrEqual=" + DEFAULT_Z_SL);

        // Get all the czBqzList where zSl is less than or equal to SMALLER_Z_SL
        defaultCzBqzShouldNotBeFound("zSl.lessThanOrEqual=" + SMALLER_Z_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSlIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSl is less than DEFAULT_Z_SL
        defaultCzBqzShouldNotBeFound("zSl.lessThan=" + DEFAULT_Z_SL);

        // Get all the czBqzList where zSl is less than UPDATED_Z_SL
        defaultCzBqzShouldBeFound("zSl.lessThan=" + UPDATED_Z_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSl is greater than DEFAULT_Z_SL
        defaultCzBqzShouldNotBeFound("zSl.greaterThan=" + DEFAULT_Z_SL);

        // Get all the czBqzList where zSl is greater than SMALLER_Z_SL
        defaultCzBqzShouldBeFound("zSl.greaterThan=" + SMALLER_Z_SL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzKflIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zKfl equals to DEFAULT_Z_KFL
        defaultCzBqzShouldBeFound("zKfl.equals=" + DEFAULT_Z_KFL);

        // Get all the czBqzList where zKfl equals to UPDATED_Z_KFL
        defaultCzBqzShouldNotBeFound("zKfl.equals=" + UPDATED_Z_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzKflIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zKfl not equals to DEFAULT_Z_KFL
        defaultCzBqzShouldNotBeFound("zKfl.notEquals=" + DEFAULT_Z_KFL);

        // Get all the czBqzList where zKfl not equals to UPDATED_Z_KFL
        defaultCzBqzShouldBeFound("zKfl.notEquals=" + UPDATED_Z_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzKflIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zKfl in DEFAULT_Z_KFL or UPDATED_Z_KFL
        defaultCzBqzShouldBeFound("zKfl.in=" + DEFAULT_Z_KFL + "," + UPDATED_Z_KFL);

        // Get all the czBqzList where zKfl equals to UPDATED_Z_KFL
        defaultCzBqzShouldNotBeFound("zKfl.in=" + UPDATED_Z_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzKflIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zKfl is not null
        defaultCzBqzShouldBeFound("zKfl.specified=true");

        // Get all the czBqzList where zKfl is null
        defaultCzBqzShouldNotBeFound("zKfl.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByzKflIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zKfl is greater than or equal to DEFAULT_Z_KFL
        defaultCzBqzShouldBeFound("zKfl.greaterThanOrEqual=" + DEFAULT_Z_KFL);

        // Get all the czBqzList where zKfl is greater than or equal to UPDATED_Z_KFL
        defaultCzBqzShouldNotBeFound("zKfl.greaterThanOrEqual=" + UPDATED_Z_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzKflIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zKfl is less than or equal to DEFAULT_Z_KFL
        defaultCzBqzShouldBeFound("zKfl.lessThanOrEqual=" + DEFAULT_Z_KFL);

        // Get all the czBqzList where zKfl is less than or equal to SMALLER_Z_KFL
        defaultCzBqzShouldNotBeFound("zKfl.lessThanOrEqual=" + SMALLER_Z_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzKflIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zKfl is less than DEFAULT_Z_KFL
        defaultCzBqzShouldNotBeFound("zKfl.lessThan=" + DEFAULT_Z_KFL);

        // Get all the czBqzList where zKfl is less than UPDATED_Z_KFL
        defaultCzBqzShouldBeFound("zKfl.lessThan=" + UPDATED_Z_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzKflIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zKfl is greater than DEFAULT_Z_KFL
        defaultCzBqzShouldNotBeFound("zKfl.greaterThan=" + DEFAULT_Z_KFL);

        // Get all the czBqzList where zKfl is greater than SMALLER_Z_KFL
        defaultCzBqzShouldBeFound("zKfl.greaterThan=" + SMALLER_Z_KFL);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjz equals to DEFAULT_Z_PJZ
        defaultCzBqzShouldBeFound("zPjz.equals=" + DEFAULT_Z_PJZ);

        // Get all the czBqzList where zPjz equals to UPDATED_Z_PJZ
        defaultCzBqzShouldNotBeFound("zPjz.equals=" + UPDATED_Z_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjz not equals to DEFAULT_Z_PJZ
        defaultCzBqzShouldNotBeFound("zPjz.notEquals=" + DEFAULT_Z_PJZ);

        // Get all the czBqzList where zPjz not equals to UPDATED_Z_PJZ
        defaultCzBqzShouldBeFound("zPjz.notEquals=" + UPDATED_Z_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjz in DEFAULT_Z_PJZ or UPDATED_Z_PJZ
        defaultCzBqzShouldBeFound("zPjz.in=" + DEFAULT_Z_PJZ + "," + UPDATED_Z_PJZ);

        // Get all the czBqzList where zPjz equals to UPDATED_Z_PJZ
        defaultCzBqzShouldNotBeFound("zPjz.in=" + UPDATED_Z_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjz is not null
        defaultCzBqzShouldBeFound("zPjz.specified=true");

        // Get all the czBqzList where zPjz is null
        defaultCzBqzShouldNotBeFound("zPjz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjz is greater than or equal to DEFAULT_Z_PJZ
        defaultCzBqzShouldBeFound("zPjz.greaterThanOrEqual=" + DEFAULT_Z_PJZ);

        // Get all the czBqzList where zPjz is greater than or equal to UPDATED_Z_PJZ
        defaultCzBqzShouldNotBeFound("zPjz.greaterThanOrEqual=" + UPDATED_Z_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjz is less than or equal to DEFAULT_Z_PJZ
        defaultCzBqzShouldBeFound("zPjz.lessThanOrEqual=" + DEFAULT_Z_PJZ);

        // Get all the czBqzList where zPjz is less than or equal to SMALLER_Z_PJZ
        defaultCzBqzShouldNotBeFound("zPjz.lessThanOrEqual=" + SMALLER_Z_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjz is less than DEFAULT_Z_PJZ
        defaultCzBqzShouldNotBeFound("zPjz.lessThan=" + DEFAULT_Z_PJZ);

        // Get all the czBqzList where zPjz is less than UPDATED_Z_PJZ
        defaultCzBqzShouldBeFound("zPjz.lessThan=" + UPDATED_Z_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjz is greater than DEFAULT_Z_PJZ
        defaultCzBqzShouldNotBeFound("zPjz.greaterThan=" + DEFAULT_Z_PJZ);

        // Get all the czBqzList where zPjz is greater than SMALLER_Z_PJZ
        defaultCzBqzShouldBeFound("zPjz.greaterThan=" + SMALLER_Z_PJZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzYsfzIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zYsfz equals to DEFAULT_Z_YSFZ
        defaultCzBqzShouldBeFound("zYsfz.equals=" + DEFAULT_Z_YSFZ);

        // Get all the czBqzList where zYsfz equals to UPDATED_Z_YSFZ
        defaultCzBqzShouldNotBeFound("zYsfz.equals=" + UPDATED_Z_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzYsfzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zYsfz not equals to DEFAULT_Z_YSFZ
        defaultCzBqzShouldNotBeFound("zYsfz.notEquals=" + DEFAULT_Z_YSFZ);

        // Get all the czBqzList where zYsfz not equals to UPDATED_Z_YSFZ
        defaultCzBqzShouldBeFound("zYsfz.notEquals=" + UPDATED_Z_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzYsfzIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zYsfz in DEFAULT_Z_YSFZ or UPDATED_Z_YSFZ
        defaultCzBqzShouldBeFound("zYsfz.in=" + DEFAULT_Z_YSFZ + "," + UPDATED_Z_YSFZ);

        // Get all the czBqzList where zYsfz equals to UPDATED_Z_YSFZ
        defaultCzBqzShouldNotBeFound("zYsfz.in=" + UPDATED_Z_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzYsfzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zYsfz is not null
        defaultCzBqzShouldBeFound("zYsfz.specified=true");

        // Get all the czBqzList where zYsfz is null
        defaultCzBqzShouldNotBeFound("zYsfz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByzYsfzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zYsfz is greater than or equal to DEFAULT_Z_YSFZ
        defaultCzBqzShouldBeFound("zYsfz.greaterThanOrEqual=" + DEFAULT_Z_YSFZ);

        // Get all the czBqzList where zYsfz is greater than or equal to UPDATED_Z_YSFZ
        defaultCzBqzShouldNotBeFound("zYsfz.greaterThanOrEqual=" + UPDATED_Z_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzYsfzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zYsfz is less than or equal to DEFAULT_Z_YSFZ
        defaultCzBqzShouldBeFound("zYsfz.lessThanOrEqual=" + DEFAULT_Z_YSFZ);

        // Get all the czBqzList where zYsfz is less than or equal to SMALLER_Z_YSFZ
        defaultCzBqzShouldNotBeFound("zYsfz.lessThanOrEqual=" + SMALLER_Z_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzYsfzIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zYsfz is less than DEFAULT_Z_YSFZ
        defaultCzBqzShouldNotBeFound("zYsfz.lessThan=" + DEFAULT_Z_YSFZ);

        // Get all the czBqzList where zYsfz is less than UPDATED_Z_YSFZ
        defaultCzBqzShouldBeFound("zYsfz.lessThan=" + UPDATED_Z_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzYsfzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zYsfz is greater than DEFAULT_Z_YSFZ
        defaultCzBqzShouldNotBeFound("zYsfz.greaterThan=" + DEFAULT_Z_YSFZ);

        // Get all the czBqzList where zYsfz is greater than SMALLER_Z_YSFZ
        defaultCzBqzShouldBeFound("zYsfz.greaterThan=" + SMALLER_Z_YSFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSjfzIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSjfz equals to DEFAULT_Z_SJFZ
        defaultCzBqzShouldBeFound("zSjfz.equals=" + DEFAULT_Z_SJFZ);

        // Get all the czBqzList where zSjfz equals to UPDATED_Z_SJFZ
        defaultCzBqzShouldNotBeFound("zSjfz.equals=" + UPDATED_Z_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSjfzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSjfz not equals to DEFAULT_Z_SJFZ
        defaultCzBqzShouldNotBeFound("zSjfz.notEquals=" + DEFAULT_Z_SJFZ);

        // Get all the czBqzList where zSjfz not equals to UPDATED_Z_SJFZ
        defaultCzBqzShouldBeFound("zSjfz.notEquals=" + UPDATED_Z_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSjfzIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSjfz in DEFAULT_Z_SJFZ or UPDATED_Z_SJFZ
        defaultCzBqzShouldBeFound("zSjfz.in=" + DEFAULT_Z_SJFZ + "," + UPDATED_Z_SJFZ);

        // Get all the czBqzList where zSjfz equals to UPDATED_Z_SJFZ
        defaultCzBqzShouldNotBeFound("zSjfz.in=" + UPDATED_Z_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSjfzIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSjfz is not null
        defaultCzBqzShouldBeFound("zSjfz.specified=true");

        // Get all the czBqzList where zSjfz is null
        defaultCzBqzShouldNotBeFound("zSjfz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSjfzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSjfz is greater than or equal to DEFAULT_Z_SJFZ
        defaultCzBqzShouldBeFound("zSjfz.greaterThanOrEqual=" + DEFAULT_Z_SJFZ);

        // Get all the czBqzList where zSjfz is greater than or equal to UPDATED_Z_SJFZ
        defaultCzBqzShouldNotBeFound("zSjfz.greaterThanOrEqual=" + UPDATED_Z_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSjfzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSjfz is less than or equal to DEFAULT_Z_SJFZ
        defaultCzBqzShouldBeFound("zSjfz.lessThanOrEqual=" + DEFAULT_Z_SJFZ);

        // Get all the czBqzList where zSjfz is less than or equal to SMALLER_Z_SJFZ
        defaultCzBqzShouldNotBeFound("zSjfz.lessThanOrEqual=" + SMALLER_Z_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSjfzIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSjfz is less than DEFAULT_Z_SJFZ
        defaultCzBqzShouldNotBeFound("zSjfz.lessThan=" + DEFAULT_Z_SJFZ);

        // Get all the czBqzList where zSjfz is less than UPDATED_Z_SJFZ
        defaultCzBqzShouldBeFound("zSjfz.lessThan=" + UPDATED_Z_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzSjfzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zSjfz is greater than DEFAULT_Z_SJFZ
        defaultCzBqzShouldNotBeFound("zSjfz.greaterThan=" + DEFAULT_Z_SJFZ);

        // Get all the czBqzList where zSjfz is greater than SMALLER_Z_SJFZ
        defaultCzBqzShouldBeFound("zSjfz.greaterThan=" + SMALLER_Z_SJFZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzFzczIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zFzcz equals to DEFAULT_Z_FZCZ
        defaultCzBqzShouldBeFound("zFzcz.equals=" + DEFAULT_Z_FZCZ);

        // Get all the czBqzList where zFzcz equals to UPDATED_Z_FZCZ
        defaultCzBqzShouldNotBeFound("zFzcz.equals=" + UPDATED_Z_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzFzczIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zFzcz not equals to DEFAULT_Z_FZCZ
        defaultCzBqzShouldNotBeFound("zFzcz.notEquals=" + DEFAULT_Z_FZCZ);

        // Get all the czBqzList where zFzcz not equals to UPDATED_Z_FZCZ
        defaultCzBqzShouldBeFound("zFzcz.notEquals=" + UPDATED_Z_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzFzczIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zFzcz in DEFAULT_Z_FZCZ or UPDATED_Z_FZCZ
        defaultCzBqzShouldBeFound("zFzcz.in=" + DEFAULT_Z_FZCZ + "," + UPDATED_Z_FZCZ);

        // Get all the czBqzList where zFzcz equals to UPDATED_Z_FZCZ
        defaultCzBqzShouldNotBeFound("zFzcz.in=" + UPDATED_Z_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzFzczIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zFzcz is not null
        defaultCzBqzShouldBeFound("zFzcz.specified=true");

        // Get all the czBqzList where zFzcz is null
        defaultCzBqzShouldNotBeFound("zFzcz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByzFzczIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zFzcz is greater than or equal to DEFAULT_Z_FZCZ
        defaultCzBqzShouldBeFound("zFzcz.greaterThanOrEqual=" + DEFAULT_Z_FZCZ);

        // Get all the czBqzList where zFzcz is greater than or equal to UPDATED_Z_FZCZ
        defaultCzBqzShouldNotBeFound("zFzcz.greaterThanOrEqual=" + UPDATED_Z_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzFzczIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zFzcz is less than or equal to DEFAULT_Z_FZCZ
        defaultCzBqzShouldBeFound("zFzcz.lessThanOrEqual=" + DEFAULT_Z_FZCZ);

        // Get all the czBqzList where zFzcz is less than or equal to SMALLER_Z_FZCZ
        defaultCzBqzShouldNotBeFound("zFzcz.lessThanOrEqual=" + SMALLER_Z_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzFzczIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zFzcz is less than DEFAULT_Z_FZCZ
        defaultCzBqzShouldNotBeFound("zFzcz.lessThan=" + DEFAULT_Z_FZCZ);

        // Get all the czBqzList where zFzcz is less than UPDATED_Z_FZCZ
        defaultCzBqzShouldBeFound("zFzcz.lessThan=" + UPDATED_Z_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzFzczIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zFzcz is greater than DEFAULT_Z_FZCZ
        defaultCzBqzShouldNotBeFound("zFzcz.greaterThan=" + DEFAULT_Z_FZCZ);

        // Get all the czBqzList where zFzcz is greater than SMALLER_Z_FZCZ
        defaultCzBqzShouldBeFound("zFzcz.greaterThan=" + SMALLER_Z_FZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzczIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjzcz equals to DEFAULT_Z_PJZCZ
        defaultCzBqzShouldBeFound("zPjzcz.equals=" + DEFAULT_Z_PJZCZ);

        // Get all the czBqzList where zPjzcz equals to UPDATED_Z_PJZCZ
        defaultCzBqzShouldNotBeFound("zPjzcz.equals=" + UPDATED_Z_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzczIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjzcz not equals to DEFAULT_Z_PJZCZ
        defaultCzBqzShouldNotBeFound("zPjzcz.notEquals=" + DEFAULT_Z_PJZCZ);

        // Get all the czBqzList where zPjzcz not equals to UPDATED_Z_PJZCZ
        defaultCzBqzShouldBeFound("zPjzcz.notEquals=" + UPDATED_Z_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzczIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjzcz in DEFAULT_Z_PJZCZ or UPDATED_Z_PJZCZ
        defaultCzBqzShouldBeFound("zPjzcz.in=" + DEFAULT_Z_PJZCZ + "," + UPDATED_Z_PJZCZ);

        // Get all the czBqzList where zPjzcz equals to UPDATED_Z_PJZCZ
        defaultCzBqzShouldNotBeFound("zPjzcz.in=" + UPDATED_Z_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzczIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjzcz is not null
        defaultCzBqzShouldBeFound("zPjzcz.specified=true");

        // Get all the czBqzList where zPjzcz is null
        defaultCzBqzShouldNotBeFound("zPjzcz.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzczIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjzcz is greater than or equal to DEFAULT_Z_PJZCZ
        defaultCzBqzShouldBeFound("zPjzcz.greaterThanOrEqual=" + DEFAULT_Z_PJZCZ);

        // Get all the czBqzList where zPjzcz is greater than or equal to UPDATED_Z_PJZCZ
        defaultCzBqzShouldNotBeFound("zPjzcz.greaterThanOrEqual=" + UPDATED_Z_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzczIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjzcz is less than or equal to DEFAULT_Z_PJZCZ
        defaultCzBqzShouldBeFound("zPjzcz.lessThanOrEqual=" + DEFAULT_Z_PJZCZ);

        // Get all the czBqzList where zPjzcz is less than or equal to SMALLER_Z_PJZCZ
        defaultCzBqzShouldNotBeFound("zPjzcz.lessThanOrEqual=" + SMALLER_Z_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzczIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjzcz is less than DEFAULT_Z_PJZCZ
        defaultCzBqzShouldNotBeFound("zPjzcz.lessThan=" + DEFAULT_Z_PJZCZ);

        // Get all the czBqzList where zPjzcz is less than UPDATED_Z_PJZCZ
        defaultCzBqzShouldBeFound("zPjzcz.lessThan=" + UPDATED_Z_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByzPjzczIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zPjzcz is greater than DEFAULT_Z_PJZCZ
        defaultCzBqzShouldNotBeFound("zPjzcz.greaterThan=" + DEFAULT_Z_PJZCZ);

        // Get all the czBqzList where zPjzcz is greater than SMALLER_Z_PJZCZ
        defaultCzBqzShouldBeFound("zPjzcz.greaterThan=" + SMALLER_Z_PJZCZ);
    }

    @Test
    @Transactional
    void getAllCzBqzsByZkIsEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zk equals to DEFAULT_ZK
        defaultCzBqzShouldBeFound("zk.equals=" + DEFAULT_ZK);

        // Get all the czBqzList where zk equals to UPDATED_ZK
        defaultCzBqzShouldNotBeFound("zk.equals=" + UPDATED_ZK);
    }

    @Test
    @Transactional
    void getAllCzBqzsByZkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zk not equals to DEFAULT_ZK
        defaultCzBqzShouldNotBeFound("zk.notEquals=" + DEFAULT_ZK);

        // Get all the czBqzList where zk not equals to UPDATED_ZK
        defaultCzBqzShouldBeFound("zk.notEquals=" + UPDATED_ZK);
    }

    @Test
    @Transactional
    void getAllCzBqzsByZkIsInShouldWork() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zk in DEFAULT_ZK or UPDATED_ZK
        defaultCzBqzShouldBeFound("zk.in=" + DEFAULT_ZK + "," + UPDATED_ZK);

        // Get all the czBqzList where zk equals to UPDATED_ZK
        defaultCzBqzShouldNotBeFound("zk.in=" + UPDATED_ZK);
    }

    @Test
    @Transactional
    void getAllCzBqzsByZkIsNullOrNotNull() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zk is not null
        defaultCzBqzShouldBeFound("zk.specified=true");

        // Get all the czBqzList where zk is null
        defaultCzBqzShouldNotBeFound("zk.specified=false");
    }

    @Test
    @Transactional
    void getAllCzBqzsByZkIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zk is greater than or equal to DEFAULT_ZK
        defaultCzBqzShouldBeFound("zk.greaterThanOrEqual=" + DEFAULT_ZK);

        // Get all the czBqzList where zk is greater than or equal to UPDATED_ZK
        defaultCzBqzShouldNotBeFound("zk.greaterThanOrEqual=" + UPDATED_ZK);
    }

    @Test
    @Transactional
    void getAllCzBqzsByZkIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zk is less than or equal to DEFAULT_ZK
        defaultCzBqzShouldBeFound("zk.lessThanOrEqual=" + DEFAULT_ZK);

        // Get all the czBqzList where zk is less than or equal to SMALLER_ZK
        defaultCzBqzShouldNotBeFound("zk.lessThanOrEqual=" + SMALLER_ZK);
    }

    @Test
    @Transactional
    void getAllCzBqzsByZkIsLessThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zk is less than DEFAULT_ZK
        defaultCzBqzShouldNotBeFound("zk.lessThan=" + DEFAULT_ZK);

        // Get all the czBqzList where zk is less than UPDATED_ZK
        defaultCzBqzShouldBeFound("zk.lessThan=" + UPDATED_ZK);
    }

    @Test
    @Transactional
    void getAllCzBqzsByZkIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        // Get all the czBqzList where zk is greater than DEFAULT_ZK
        defaultCzBqzShouldNotBeFound("zk.greaterThan=" + DEFAULT_ZK);

        // Get all the czBqzList where zk is greater than SMALLER_ZK
        defaultCzBqzShouldBeFound("zk.greaterThan=" + SMALLER_ZK);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCzBqzShouldBeFound(String filter) throws Exception {
        restCzBqzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(czBqz.getId().intValue())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].qSl").value(hasItem(DEFAULT_Q_SL.intValue())))
            .andExpect(jsonPath("$.[*].qKfl").value(hasItem(sameNumber(DEFAULT_Q_KFL))))
            .andExpect(jsonPath("$.[*].qPjz").value(hasItem(sameNumber(DEFAULT_Q_PJZ))))
            .andExpect(jsonPath("$.[*].qYsfz").value(hasItem(sameNumber(DEFAULT_Q_YSFZ))))
            .andExpect(jsonPath("$.[*].qSjfz").value(hasItem(sameNumber(DEFAULT_Q_SJFZ))))
            .andExpect(jsonPath("$.[*].qFzcz").value(hasItem(sameNumber(DEFAULT_Q_FZCZ))))
            .andExpect(jsonPath("$.[*].qPjzcz").value(hasItem(sameNumber(DEFAULT_Q_PJZCZ))))
            .andExpect(jsonPath("$.[*].bSl").value(hasItem(DEFAULT_B_SL.intValue())))
            .andExpect(jsonPath("$.[*].bKfl").value(hasItem(sameNumber(DEFAULT_B_KFL))))
            .andExpect(jsonPath("$.[*].bPjz").value(hasItem(sameNumber(DEFAULT_B_PJZ))))
            .andExpect(jsonPath("$.[*].bYsfz").value(hasItem(sameNumber(DEFAULT_B_YSFZ))))
            .andExpect(jsonPath("$.[*].bSjfz").value(hasItem(sameNumber(DEFAULT_B_SJFZ))))
            .andExpect(jsonPath("$.[*].bFzcz").value(hasItem(sameNumber(DEFAULT_B_FZCZ))))
            .andExpect(jsonPath("$.[*].bPjzcz").value(hasItem(sameNumber(DEFAULT_B_PJZCZ))))
            .andExpect(jsonPath("$.[*].zSl").value(hasItem(DEFAULT_Z_SL.intValue())))
            .andExpect(jsonPath("$.[*].zKfl").value(hasItem(sameNumber(DEFAULT_Z_KFL))))
            .andExpect(jsonPath("$.[*].zPjz").value(hasItem(sameNumber(DEFAULT_Z_PJZ))))
            .andExpect(jsonPath("$.[*].zYsfz").value(hasItem(sameNumber(DEFAULT_Z_YSFZ))))
            .andExpect(jsonPath("$.[*].zSjfz").value(hasItem(sameNumber(DEFAULT_Z_SJFZ))))
            .andExpect(jsonPath("$.[*].zFzcz").value(hasItem(sameNumber(DEFAULT_Z_FZCZ))))
            .andExpect(jsonPath("$.[*].zPjzcz").value(hasItem(sameNumber(DEFAULT_Z_PJZCZ))))
            .andExpect(jsonPath("$.[*].zk").value(hasItem(sameNumber(DEFAULT_ZK))));

        // Check, that the count call also returns 1
        restCzBqzMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCzBqzShouldNotBeFound(String filter) throws Exception {
        restCzBqzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCzBqzMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCzBqz() throws Exception {
        // Get the czBqz
        restCzBqzMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCzBqz() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        int databaseSizeBeforeUpdate = czBqzRepository.findAll().size();

        // Update the czBqz
        CzBqz updatedCzBqz = czBqzRepository.findById(czBqz.getId()).get();
        // Disconnect from session so that the updates on updatedCzBqz are not directly saved in db
        em.detach(updatedCzBqz);
        updatedCzBqz
            .rq(UPDATED_RQ)
            .qSl(UPDATED_Q_SL)
            .qKfl(UPDATED_Q_KFL)
            .qPjz(UPDATED_Q_PJZ)
            .qYsfz(UPDATED_Q_YSFZ)
            .qSjfz(UPDATED_Q_SJFZ)
            .qFzcz(UPDATED_Q_FZCZ)
            .qPjzcz(UPDATED_Q_PJZCZ)
            .bSl(UPDATED_B_SL)
            .bKfl(UPDATED_B_KFL)
            .bPjz(UPDATED_B_PJZ)
            .bYsfz(UPDATED_B_YSFZ)
            .bSjfz(UPDATED_B_SJFZ)
            .bFzcz(UPDATED_B_FZCZ)
            .bPjzcz(UPDATED_B_PJZCZ)
            .zSl(UPDATED_Z_SL)
            .zKfl(UPDATED_Z_KFL)
            .zPjz(UPDATED_Z_PJZ)
            .zYsfz(UPDATED_Z_YSFZ)
            .zSjfz(UPDATED_Z_SJFZ)
            .zFzcz(UPDATED_Z_FZCZ)
            .zPjzcz(UPDATED_Z_PJZCZ)
            .zk(UPDATED_ZK);
        CzBqzDTO czBqzDTO = czBqzMapper.toDto(updatedCzBqz);

        restCzBqzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, czBqzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(czBqzDTO))
            )
            .andExpect(status().isOk());

        // Validate the CzBqz in the database
        List<CzBqz> czBqzList = czBqzRepository.findAll();
        assertThat(czBqzList).hasSize(databaseSizeBeforeUpdate);
        CzBqz testCzBqz = czBqzList.get(czBqzList.size() - 1);
        assertThat(testCzBqz.getRq()).isEqualTo(UPDATED_RQ);
        assertThat(testCzBqz.getqSl()).isEqualTo(UPDATED_Q_SL);
        assertThat(testCzBqz.getqKfl()).isEqualTo(UPDATED_Q_KFL);
        assertThat(testCzBqz.getqPjz()).isEqualTo(UPDATED_Q_PJZ);
        assertThat(testCzBqz.getqYsfz()).isEqualTo(UPDATED_Q_YSFZ);
        assertThat(testCzBqz.getqSjfz()).isEqualTo(UPDATED_Q_SJFZ);
        assertThat(testCzBqz.getqFzcz()).isEqualTo(UPDATED_Q_FZCZ);
        assertThat(testCzBqz.getqPjzcz()).isEqualTo(UPDATED_Q_PJZCZ);
        assertThat(testCzBqz.getbSl()).isEqualTo(UPDATED_B_SL);
        assertThat(testCzBqz.getbKfl()).isEqualTo(UPDATED_B_KFL);
        assertThat(testCzBqz.getbPjz()).isEqualTo(UPDATED_B_PJZ);
        assertThat(testCzBqz.getbYsfz()).isEqualTo(UPDATED_B_YSFZ);
        assertThat(testCzBqz.getbSjfz()).isEqualTo(UPDATED_B_SJFZ);
        assertThat(testCzBqz.getbFzcz()).isEqualTo(UPDATED_B_FZCZ);
        assertThat(testCzBqz.getbPjzcz()).isEqualTo(UPDATED_B_PJZCZ);
        assertThat(testCzBqz.getzSl()).isEqualTo(UPDATED_Z_SL);
        assertThat(testCzBqz.getzKfl()).isEqualTo(UPDATED_Z_KFL);
        assertThat(testCzBqz.getzPjz()).isEqualTo(UPDATED_Z_PJZ);
        assertThat(testCzBqz.getzYsfz()).isEqualTo(UPDATED_Z_YSFZ);
        assertThat(testCzBqz.getzSjfz()).isEqualTo(UPDATED_Z_SJFZ);
        assertThat(testCzBqz.getzFzcz()).isEqualTo(UPDATED_Z_FZCZ);
        assertThat(testCzBqz.getzPjzcz()).isEqualTo(UPDATED_Z_PJZCZ);
        assertThat(testCzBqz.getZk()).isEqualTo(UPDATED_ZK);

        // Validate the CzBqz in Elasticsearch
        verify(mockCzBqzSearchRepository).save(testCzBqz);
    }

    @Test
    @Transactional
    void putNonExistingCzBqz() throws Exception {
        int databaseSizeBeforeUpdate = czBqzRepository.findAll().size();
        czBqz.setId(count.incrementAndGet());

        // Create the CzBqz
        CzBqzDTO czBqzDTO = czBqzMapper.toDto(czBqz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCzBqzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, czBqzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(czBqzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzBqz in the database
        List<CzBqz> czBqzList = czBqzRepository.findAll();
        assertThat(czBqzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzBqz in Elasticsearch
        verify(mockCzBqzSearchRepository, times(0)).save(czBqz);
    }

    @Test
    @Transactional
    void putWithIdMismatchCzBqz() throws Exception {
        int databaseSizeBeforeUpdate = czBqzRepository.findAll().size();
        czBqz.setId(count.incrementAndGet());

        // Create the CzBqz
        CzBqzDTO czBqzDTO = czBqzMapper.toDto(czBqz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzBqzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(czBqzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzBqz in the database
        List<CzBqz> czBqzList = czBqzRepository.findAll();
        assertThat(czBqzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzBqz in Elasticsearch
        verify(mockCzBqzSearchRepository, times(0)).save(czBqz);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCzBqz() throws Exception {
        int databaseSizeBeforeUpdate = czBqzRepository.findAll().size();
        czBqz.setId(count.incrementAndGet());

        // Create the CzBqz
        CzBqzDTO czBqzDTO = czBqzMapper.toDto(czBqz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzBqzMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czBqzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CzBqz in the database
        List<CzBqz> czBqzList = czBqzRepository.findAll();
        assertThat(czBqzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzBqz in Elasticsearch
        verify(mockCzBqzSearchRepository, times(0)).save(czBqz);
    }

    @Test
    @Transactional
    void partialUpdateCzBqzWithPatch() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        int databaseSizeBeforeUpdate = czBqzRepository.findAll().size();

        // Update the czBqz using partial update
        CzBqz partialUpdatedCzBqz = new CzBqz();
        partialUpdatedCzBqz.setId(czBqz.getId());

        partialUpdatedCzBqz
            .qKfl(UPDATED_Q_KFL)
            .qSjfz(UPDATED_Q_SJFZ)
            .qFzcz(UPDATED_Q_FZCZ)
            .qPjzcz(UPDATED_Q_PJZCZ)
            .bSl(UPDATED_B_SL)
            .bKfl(UPDATED_B_KFL)
            .bYsfz(UPDATED_B_YSFZ)
            .bSjfz(UPDATED_B_SJFZ)
            .bFzcz(UPDATED_B_FZCZ)
            .zSl(UPDATED_Z_SL)
            .zPjz(UPDATED_Z_PJZ)
            .zYsfz(UPDATED_Z_YSFZ)
            .zSjfz(UPDATED_Z_SJFZ)
            .zPjzcz(UPDATED_Z_PJZCZ);

        restCzBqzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCzBqz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCzBqz))
            )
            .andExpect(status().isOk());

        // Validate the CzBqz in the database
        List<CzBqz> czBqzList = czBqzRepository.findAll();
        assertThat(czBqzList).hasSize(databaseSizeBeforeUpdate);
        CzBqz testCzBqz = czBqzList.get(czBqzList.size() - 1);
        assertThat(testCzBqz.getRq()).isEqualTo(DEFAULT_RQ);
        assertThat(testCzBqz.getqSl()).isEqualTo(DEFAULT_Q_SL);
        assertThat(testCzBqz.getqKfl()).isEqualByComparingTo(UPDATED_Q_KFL);
        assertThat(testCzBqz.getqPjz()).isEqualByComparingTo(DEFAULT_Q_PJZ);
        assertThat(testCzBqz.getqYsfz()).isEqualByComparingTo(DEFAULT_Q_YSFZ);
        assertThat(testCzBqz.getqSjfz()).isEqualByComparingTo(UPDATED_Q_SJFZ);
        assertThat(testCzBqz.getqFzcz()).isEqualByComparingTo(UPDATED_Q_FZCZ);
        assertThat(testCzBqz.getqPjzcz()).isEqualByComparingTo(UPDATED_Q_PJZCZ);
        assertThat(testCzBqz.getbSl()).isEqualTo(UPDATED_B_SL);
        assertThat(testCzBqz.getbKfl()).isEqualByComparingTo(UPDATED_B_KFL);
        assertThat(testCzBqz.getbPjz()).isEqualByComparingTo(DEFAULT_B_PJZ);
        assertThat(testCzBqz.getbYsfz()).isEqualByComparingTo(UPDATED_B_YSFZ);
        assertThat(testCzBqz.getbSjfz()).isEqualByComparingTo(UPDATED_B_SJFZ);
        assertThat(testCzBqz.getbFzcz()).isEqualByComparingTo(UPDATED_B_FZCZ);
        assertThat(testCzBqz.getbPjzcz()).isEqualByComparingTo(DEFAULT_B_PJZCZ);
        assertThat(testCzBqz.getzSl()).isEqualTo(UPDATED_Z_SL);
        assertThat(testCzBqz.getzKfl()).isEqualByComparingTo(DEFAULT_Z_KFL);
        assertThat(testCzBqz.getzPjz()).isEqualByComparingTo(UPDATED_Z_PJZ);
        assertThat(testCzBqz.getzYsfz()).isEqualByComparingTo(UPDATED_Z_YSFZ);
        assertThat(testCzBqz.getzSjfz()).isEqualByComparingTo(UPDATED_Z_SJFZ);
        assertThat(testCzBqz.getzFzcz()).isEqualByComparingTo(DEFAULT_Z_FZCZ);
        assertThat(testCzBqz.getzPjzcz()).isEqualByComparingTo(UPDATED_Z_PJZCZ);
        assertThat(testCzBqz.getZk()).isEqualByComparingTo(DEFAULT_ZK);
    }

    @Test
    @Transactional
    void fullUpdateCzBqzWithPatch() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        int databaseSizeBeforeUpdate = czBqzRepository.findAll().size();

        // Update the czBqz using partial update
        CzBqz partialUpdatedCzBqz = new CzBqz();
        partialUpdatedCzBqz.setId(czBqz.getId());

        partialUpdatedCzBqz
            .rq(UPDATED_RQ)
            .qSl(UPDATED_Q_SL)
            .qKfl(UPDATED_Q_KFL)
            .qPjz(UPDATED_Q_PJZ)
            .qYsfz(UPDATED_Q_YSFZ)
            .qSjfz(UPDATED_Q_SJFZ)
            .qFzcz(UPDATED_Q_FZCZ)
            .qPjzcz(UPDATED_Q_PJZCZ)
            .bSl(UPDATED_B_SL)
            .bKfl(UPDATED_B_KFL)
            .bPjz(UPDATED_B_PJZ)
            .bYsfz(UPDATED_B_YSFZ)
            .bSjfz(UPDATED_B_SJFZ)
            .bFzcz(UPDATED_B_FZCZ)
            .bPjzcz(UPDATED_B_PJZCZ)
            .zSl(UPDATED_Z_SL)
            .zKfl(UPDATED_Z_KFL)
            .zPjz(UPDATED_Z_PJZ)
            .zYsfz(UPDATED_Z_YSFZ)
            .zSjfz(UPDATED_Z_SJFZ)
            .zFzcz(UPDATED_Z_FZCZ)
            .zPjzcz(UPDATED_Z_PJZCZ)
            .zk(UPDATED_ZK);

        restCzBqzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCzBqz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCzBqz))
            )
            .andExpect(status().isOk());

        // Validate the CzBqz in the database
        List<CzBqz> czBqzList = czBqzRepository.findAll();
        assertThat(czBqzList).hasSize(databaseSizeBeforeUpdate);
        CzBqz testCzBqz = czBqzList.get(czBqzList.size() - 1);
        assertThat(testCzBqz.getRq()).isEqualTo(UPDATED_RQ);
        assertThat(testCzBqz.getqSl()).isEqualTo(UPDATED_Q_SL);
        assertThat(testCzBqz.getqKfl()).isEqualByComparingTo(UPDATED_Q_KFL);
        assertThat(testCzBqz.getqPjz()).isEqualByComparingTo(UPDATED_Q_PJZ);
        assertThat(testCzBqz.getqYsfz()).isEqualByComparingTo(UPDATED_Q_YSFZ);
        assertThat(testCzBqz.getqSjfz()).isEqualByComparingTo(UPDATED_Q_SJFZ);
        assertThat(testCzBqz.getqFzcz()).isEqualByComparingTo(UPDATED_Q_FZCZ);
        assertThat(testCzBqz.getqPjzcz()).isEqualByComparingTo(UPDATED_Q_PJZCZ);
        assertThat(testCzBqz.getbSl()).isEqualTo(UPDATED_B_SL);
        assertThat(testCzBqz.getbKfl()).isEqualByComparingTo(UPDATED_B_KFL);
        assertThat(testCzBqz.getbPjz()).isEqualByComparingTo(UPDATED_B_PJZ);
        assertThat(testCzBqz.getbYsfz()).isEqualByComparingTo(UPDATED_B_YSFZ);
        assertThat(testCzBqz.getbSjfz()).isEqualByComparingTo(UPDATED_B_SJFZ);
        assertThat(testCzBqz.getbFzcz()).isEqualByComparingTo(UPDATED_B_FZCZ);
        assertThat(testCzBqz.getbPjzcz()).isEqualByComparingTo(UPDATED_B_PJZCZ);
        assertThat(testCzBqz.getzSl()).isEqualTo(UPDATED_Z_SL);
        assertThat(testCzBqz.getzKfl()).isEqualByComparingTo(UPDATED_Z_KFL);
        assertThat(testCzBqz.getzPjz()).isEqualByComparingTo(UPDATED_Z_PJZ);
        assertThat(testCzBqz.getzYsfz()).isEqualByComparingTo(UPDATED_Z_YSFZ);
        assertThat(testCzBqz.getzSjfz()).isEqualByComparingTo(UPDATED_Z_SJFZ);
        assertThat(testCzBqz.getzFzcz()).isEqualByComparingTo(UPDATED_Z_FZCZ);
        assertThat(testCzBqz.getzPjzcz()).isEqualByComparingTo(UPDATED_Z_PJZCZ);
        assertThat(testCzBqz.getZk()).isEqualByComparingTo(UPDATED_ZK);
    }

    @Test
    @Transactional
    void patchNonExistingCzBqz() throws Exception {
        int databaseSizeBeforeUpdate = czBqzRepository.findAll().size();
        czBqz.setId(count.incrementAndGet());

        // Create the CzBqz
        CzBqzDTO czBqzDTO = czBqzMapper.toDto(czBqz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCzBqzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, czBqzDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(czBqzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzBqz in the database
        List<CzBqz> czBqzList = czBqzRepository.findAll();
        assertThat(czBqzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzBqz in Elasticsearch
        verify(mockCzBqzSearchRepository, times(0)).save(czBqz);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCzBqz() throws Exception {
        int databaseSizeBeforeUpdate = czBqzRepository.findAll().size();
        czBqz.setId(count.incrementAndGet());

        // Create the CzBqz
        CzBqzDTO czBqzDTO = czBqzMapper.toDto(czBqz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzBqzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(czBqzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzBqz in the database
        List<CzBqz> czBqzList = czBqzRepository.findAll();
        assertThat(czBqzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzBqz in Elasticsearch
        verify(mockCzBqzSearchRepository, times(0)).save(czBqz);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCzBqz() throws Exception {
        int databaseSizeBeforeUpdate = czBqzRepository.findAll().size();
        czBqz.setId(count.incrementAndGet());

        // Create the CzBqz
        CzBqzDTO czBqzDTO = czBqzMapper.toDto(czBqz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzBqzMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(czBqzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CzBqz in the database
        List<CzBqz> czBqzList = czBqzRepository.findAll();
        assertThat(czBqzList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzBqz in Elasticsearch
        verify(mockCzBqzSearchRepository, times(0)).save(czBqz);
    }

    @Test
    @Transactional
    void deleteCzBqz() throws Exception {
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);

        int databaseSizeBeforeDelete = czBqzRepository.findAll().size();

        // Delete the czBqz
        restCzBqzMockMvc
            .perform(delete(ENTITY_API_URL_ID, czBqz.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CzBqz> czBqzList = czBqzRepository.findAll();
        assertThat(czBqzList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CzBqz in Elasticsearch
        verify(mockCzBqzSearchRepository, times(1)).deleteById(czBqz.getId());
    }

    @Test
    @Transactional
    void searchCzBqz() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        czBqzRepository.saveAndFlush(czBqz);
        when(mockCzBqzSearchRepository.search(queryStringQuery("id:" + czBqz.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(czBqz), PageRequest.of(0, 1), 1));

        // Search the czBqz
        restCzBqzMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + czBqz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(czBqz.getId().intValue())))
            .andExpect(jsonPath("$.[*].rq").value(hasItem(DEFAULT_RQ.toString())))
            .andExpect(jsonPath("$.[*].qSl").value(hasItem(DEFAULT_Q_SL.intValue())))
            .andExpect(jsonPath("$.[*].qKfl").value(hasItem(sameNumber(DEFAULT_Q_KFL))))
            .andExpect(jsonPath("$.[*].qPjz").value(hasItem(sameNumber(DEFAULT_Q_PJZ))))
            .andExpect(jsonPath("$.[*].qYsfz").value(hasItem(sameNumber(DEFAULT_Q_YSFZ))))
            .andExpect(jsonPath("$.[*].qSjfz").value(hasItem(sameNumber(DEFAULT_Q_SJFZ))))
            .andExpect(jsonPath("$.[*].qFzcz").value(hasItem(sameNumber(DEFAULT_Q_FZCZ))))
            .andExpect(jsonPath("$.[*].qPjzcz").value(hasItem(sameNumber(DEFAULT_Q_PJZCZ))))
            .andExpect(jsonPath("$.[*].bSl").value(hasItem(DEFAULT_B_SL.intValue())))
            .andExpect(jsonPath("$.[*].bKfl").value(hasItem(sameNumber(DEFAULT_B_KFL))))
            .andExpect(jsonPath("$.[*].bPjz").value(hasItem(sameNumber(DEFAULT_B_PJZ))))
            .andExpect(jsonPath("$.[*].bYsfz").value(hasItem(sameNumber(DEFAULT_B_YSFZ))))
            .andExpect(jsonPath("$.[*].bSjfz").value(hasItem(sameNumber(DEFAULT_B_SJFZ))))
            .andExpect(jsonPath("$.[*].bFzcz").value(hasItem(sameNumber(DEFAULT_B_FZCZ))))
            .andExpect(jsonPath("$.[*].bPjzcz").value(hasItem(sameNumber(DEFAULT_B_PJZCZ))))
            .andExpect(jsonPath("$.[*].zSl").value(hasItem(DEFAULT_Z_SL.intValue())))
            .andExpect(jsonPath("$.[*].zKfl").value(hasItem(sameNumber(DEFAULT_Z_KFL))))
            .andExpect(jsonPath("$.[*].zPjz").value(hasItem(sameNumber(DEFAULT_Z_PJZ))))
            .andExpect(jsonPath("$.[*].zYsfz").value(hasItem(sameNumber(DEFAULT_Z_YSFZ))))
            .andExpect(jsonPath("$.[*].zSjfz").value(hasItem(sameNumber(DEFAULT_Z_SJFZ))))
            .andExpect(jsonPath("$.[*].zFzcz").value(hasItem(sameNumber(DEFAULT_Z_FZCZ))))
            .andExpect(jsonPath("$.[*].zPjzcz").value(hasItem(sameNumber(DEFAULT_Z_PJZCZ))))
            .andExpect(jsonPath("$.[*].zk").value(hasItem(sameNumber(DEFAULT_ZK))));
    }
}
