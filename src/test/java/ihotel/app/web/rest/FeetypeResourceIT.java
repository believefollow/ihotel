package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.Feetype;
import ihotel.app.repository.FeetypeRepository;
import ihotel.app.repository.search.FeetypeSearchRepository;
import ihotel.app.service.criteria.FeetypeCriteria;
import ihotel.app.service.dto.FeetypeDTO;
import ihotel.app.service.mapper.FeetypeMapper;
import java.math.BigDecimal;
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
 * Integration tests for the {@link FeetypeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class FeetypeResourceIT {

    private static final Long DEFAULT_FEENUM = 1L;
    private static final Long UPDATED_FEENUM = 2L;
    private static final Long SMALLER_FEENUM = 1L - 1L;

    private static final String DEFAULT_FEENAME = "AAAAAAAAAA";
    private static final String UPDATED_FEENAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final Long DEFAULT_SIGN = 1L;
    private static final Long UPDATED_SIGN = 2L;
    private static final Long SMALLER_SIGN = 1L - 1L;

    private static final String DEFAULT_BEIZHU = "AAAAAAAAAA";
    private static final String UPDATED_BEIZHU = "BBBBBBBBBB";

    private static final String DEFAULT_PYM = "AAAAAAAAAA";
    private static final String UPDATED_PYM = "BBBBBBBBBB";

    private static final Long DEFAULT_SALESPOTN = 1L;
    private static final Long UPDATED_SALESPOTN = 2L;
    private static final Long SMALLER_SALESPOTN = 1L - 1L;

    private static final String DEFAULT_DEPOT = "AAAAAAAAAA";
    private static final String UPDATED_DEPOT = "BBBBBBBBBB";

    private static final Long DEFAULT_CBSIGN = 1L;
    private static final Long UPDATED_CBSIGN = 2L;
    private static final Long SMALLER_CBSIGN = 1L - 1L;

    private static final Long DEFAULT_ORDERSIGN = 1L;
    private static final Long UPDATED_ORDERSIGN = 2L;
    private static final Long SMALLER_ORDERSIGN = 1L - 1L;

    private static final String DEFAULT_HOTELDM = "AAAAAAAAAA";
    private static final String UPDATED_HOTELDM = "BBBBBBBBBB";

    private static final Long DEFAULT_ISNEW = 1L;
    private static final Long UPDATED_ISNEW = 2L;
    private static final Long SMALLER_ISNEW = 1L - 1L;

    private static final BigDecimal DEFAULT_YGJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_YGJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_YGJ = new BigDecimal(1 - 1);

    private static final String DEFAULT_AUTOSIGN = "AA";
    private static final String UPDATED_AUTOSIGN = "BB";

    private static final BigDecimal DEFAULT_JJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_JJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_JJ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HYJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_HYJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_HYJ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DQKC = new BigDecimal(1);
    private static final BigDecimal UPDATED_DQKC = new BigDecimal(2);
    private static final BigDecimal SMALLER_DQKC = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/feetypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/feetypes";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FeetypeRepository feetypeRepository;

    @Autowired
    private FeetypeMapper feetypeMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.FeetypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private FeetypeSearchRepository mockFeetypeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeetypeMockMvc;

    private Feetype feetype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feetype createEntity(EntityManager em) {
        Feetype feetype = new Feetype()
            .feenum(DEFAULT_FEENUM)
            .feename(DEFAULT_FEENAME)
            .price(DEFAULT_PRICE)
            .sign(DEFAULT_SIGN)
            .beizhu(DEFAULT_BEIZHU)
            .pym(DEFAULT_PYM)
            .salespotn(DEFAULT_SALESPOTN)
            .depot(DEFAULT_DEPOT)
            .cbsign(DEFAULT_CBSIGN)
            .ordersign(DEFAULT_ORDERSIGN)
            .hoteldm(DEFAULT_HOTELDM)
            .isnew(DEFAULT_ISNEW)
            .ygj(DEFAULT_YGJ)
            .autosign(DEFAULT_AUTOSIGN)
            .jj(DEFAULT_JJ)
            .hyj(DEFAULT_HYJ)
            .dqkc(DEFAULT_DQKC);
        return feetype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feetype createUpdatedEntity(EntityManager em) {
        Feetype feetype = new Feetype()
            .feenum(UPDATED_FEENUM)
            .feename(UPDATED_FEENAME)
            .price(UPDATED_PRICE)
            .sign(UPDATED_SIGN)
            .beizhu(UPDATED_BEIZHU)
            .pym(UPDATED_PYM)
            .salespotn(UPDATED_SALESPOTN)
            .depot(UPDATED_DEPOT)
            .cbsign(UPDATED_CBSIGN)
            .ordersign(UPDATED_ORDERSIGN)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW)
            .ygj(UPDATED_YGJ)
            .autosign(UPDATED_AUTOSIGN)
            .jj(UPDATED_JJ)
            .hyj(UPDATED_HYJ)
            .dqkc(UPDATED_DQKC);
        return feetype;
    }

    @BeforeEach
    public void initTest() {
        feetype = createEntity(em);
    }

    @Test
    @Transactional
    void createFeetype() throws Exception {
        int databaseSizeBeforeCreate = feetypeRepository.findAll().size();
        // Create the Feetype
        FeetypeDTO feetypeDTO = feetypeMapper.toDto(feetype);
        restFeetypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feetypeDTO)))
            .andExpect(status().isCreated());

        // Validate the Feetype in the database
        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeCreate + 1);
        Feetype testFeetype = feetypeList.get(feetypeList.size() - 1);
        assertThat(testFeetype.getFeenum()).isEqualTo(DEFAULT_FEENUM);
        assertThat(testFeetype.getFeename()).isEqualTo(DEFAULT_FEENAME);
        assertThat(testFeetype.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testFeetype.getSign()).isEqualTo(DEFAULT_SIGN);
        assertThat(testFeetype.getBeizhu()).isEqualTo(DEFAULT_BEIZHU);
        assertThat(testFeetype.getPym()).isEqualTo(DEFAULT_PYM);
        assertThat(testFeetype.getSalespotn()).isEqualTo(DEFAULT_SALESPOTN);
        assertThat(testFeetype.getDepot()).isEqualTo(DEFAULT_DEPOT);
        assertThat(testFeetype.getCbsign()).isEqualTo(DEFAULT_CBSIGN);
        assertThat(testFeetype.getOrdersign()).isEqualTo(DEFAULT_ORDERSIGN);
        assertThat(testFeetype.getHoteldm()).isEqualTo(DEFAULT_HOTELDM);
        assertThat(testFeetype.getIsnew()).isEqualTo(DEFAULT_ISNEW);
        assertThat(testFeetype.getYgj()).isEqualByComparingTo(DEFAULT_YGJ);
        assertThat(testFeetype.getAutosign()).isEqualTo(DEFAULT_AUTOSIGN);
        assertThat(testFeetype.getJj()).isEqualByComparingTo(DEFAULT_JJ);
        assertThat(testFeetype.getHyj()).isEqualByComparingTo(DEFAULT_HYJ);
        assertThat(testFeetype.getDqkc()).isEqualByComparingTo(DEFAULT_DQKC);

        // Validate the Feetype in Elasticsearch
        verify(mockFeetypeSearchRepository, times(1)).save(testFeetype);
    }

    @Test
    @Transactional
    void createFeetypeWithExistingId() throws Exception {
        // Create the Feetype with an existing ID
        feetype.setId(1L);
        FeetypeDTO feetypeDTO = feetypeMapper.toDto(feetype);

        int databaseSizeBeforeCreate = feetypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeetypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feetypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Feetype in the database
        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Feetype in Elasticsearch
        verify(mockFeetypeSearchRepository, times(0)).save(feetype);
    }

    @Test
    @Transactional
    void checkFeenumIsRequired() throws Exception {
        int databaseSizeBeforeTest = feetypeRepository.findAll().size();
        // set the field null
        feetype.setFeenum(null);

        // Create the Feetype, which fails.
        FeetypeDTO feetypeDTO = feetypeMapper.toDto(feetype);

        restFeetypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feetypeDTO)))
            .andExpect(status().isBadRequest());

        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFeenameIsRequired() throws Exception {
        int databaseSizeBeforeTest = feetypeRepository.findAll().size();
        // set the field null
        feetype.setFeename(null);

        // Create the Feetype, which fails.
        FeetypeDTO feetypeDTO = feetypeMapper.toDto(feetype);

        restFeetypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feetypeDTO)))
            .andExpect(status().isBadRequest());

        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = feetypeRepository.findAll().size();
        // set the field null
        feetype.setPrice(null);

        // Create the Feetype, which fails.
        FeetypeDTO feetypeDTO = feetypeMapper.toDto(feetype);

        restFeetypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feetypeDTO)))
            .andExpect(status().isBadRequest());

        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSignIsRequired() throws Exception {
        int databaseSizeBeforeTest = feetypeRepository.findAll().size();
        // set the field null
        feetype.setSign(null);

        // Create the Feetype, which fails.
        FeetypeDTO feetypeDTO = feetypeMapper.toDto(feetype);

        restFeetypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feetypeDTO)))
            .andExpect(status().isBadRequest());

        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkSalespotnIsRequired() throws Exception {
        int databaseSizeBeforeTest = feetypeRepository.findAll().size();
        // set the field null
        feetype.setSalespotn(null);

        // Create the Feetype, which fails.
        FeetypeDTO feetypeDTO = feetypeMapper.toDto(feetype);

        restFeetypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feetypeDTO)))
            .andExpect(status().isBadRequest());

        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFeetypes() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList
        restFeetypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feetype.getId().intValue())))
            .andExpect(jsonPath("$.[*].feenum").value(hasItem(DEFAULT_FEENUM.intValue())))
            .andExpect(jsonPath("$.[*].feename").value(hasItem(DEFAULT_FEENAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].beizhu").value(hasItem(DEFAULT_BEIZHU)))
            .andExpect(jsonPath("$.[*].pym").value(hasItem(DEFAULT_PYM)))
            .andExpect(jsonPath("$.[*].salespotn").value(hasItem(DEFAULT_SALESPOTN.intValue())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].cbsign").value(hasItem(DEFAULT_CBSIGN.intValue())))
            .andExpect(jsonPath("$.[*].ordersign").value(hasItem(DEFAULT_ORDERSIGN.intValue())))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())))
            .andExpect(jsonPath("$.[*].ygj").value(hasItem(sameNumber(DEFAULT_YGJ))))
            .andExpect(jsonPath("$.[*].autosign").value(hasItem(DEFAULT_AUTOSIGN)))
            .andExpect(jsonPath("$.[*].jj").value(hasItem(sameNumber(DEFAULT_JJ))))
            .andExpect(jsonPath("$.[*].hyj").value(hasItem(sameNumber(DEFAULT_HYJ))))
            .andExpect(jsonPath("$.[*].dqkc").value(hasItem(sameNumber(DEFAULT_DQKC))));
    }

    @Test
    @Transactional
    void getFeetype() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get the feetype
        restFeetypeMockMvc
            .perform(get(ENTITY_API_URL_ID, feetype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feetype.getId().intValue()))
            .andExpect(jsonPath("$.feenum").value(DEFAULT_FEENUM.intValue()))
            .andExpect(jsonPath("$.feename").value(DEFAULT_FEENAME))
            .andExpect(jsonPath("$.price").value(sameNumber(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.sign").value(DEFAULT_SIGN.intValue()))
            .andExpect(jsonPath("$.beizhu").value(DEFAULT_BEIZHU))
            .andExpect(jsonPath("$.pym").value(DEFAULT_PYM))
            .andExpect(jsonPath("$.salespotn").value(DEFAULT_SALESPOTN.intValue()))
            .andExpect(jsonPath("$.depot").value(DEFAULT_DEPOT))
            .andExpect(jsonPath("$.cbsign").value(DEFAULT_CBSIGN.intValue()))
            .andExpect(jsonPath("$.ordersign").value(DEFAULT_ORDERSIGN.intValue()))
            .andExpect(jsonPath("$.hoteldm").value(DEFAULT_HOTELDM))
            .andExpect(jsonPath("$.isnew").value(DEFAULT_ISNEW.intValue()))
            .andExpect(jsonPath("$.ygj").value(sameNumber(DEFAULT_YGJ)))
            .andExpect(jsonPath("$.autosign").value(DEFAULT_AUTOSIGN))
            .andExpect(jsonPath("$.jj").value(sameNumber(DEFAULT_JJ)))
            .andExpect(jsonPath("$.hyj").value(sameNumber(DEFAULT_HYJ)))
            .andExpect(jsonPath("$.dqkc").value(sameNumber(DEFAULT_DQKC)));
    }

    @Test
    @Transactional
    void getFeetypesByIdFiltering() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        Long id = feetype.getId();

        defaultFeetypeShouldBeFound("id.equals=" + id);
        defaultFeetypeShouldNotBeFound("id.notEquals=" + id);

        defaultFeetypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFeetypeShouldNotBeFound("id.greaterThan=" + id);

        defaultFeetypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFeetypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllFeetypesByFeenumIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where feenum equals to DEFAULT_FEENUM
        defaultFeetypeShouldBeFound("feenum.equals=" + DEFAULT_FEENUM);

        // Get all the feetypeList where feenum equals to UPDATED_FEENUM
        defaultFeetypeShouldNotBeFound("feenum.equals=" + UPDATED_FEENUM);
    }

    @Test
    @Transactional
    void getAllFeetypesByFeenumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where feenum not equals to DEFAULT_FEENUM
        defaultFeetypeShouldNotBeFound("feenum.notEquals=" + DEFAULT_FEENUM);

        // Get all the feetypeList where feenum not equals to UPDATED_FEENUM
        defaultFeetypeShouldBeFound("feenum.notEquals=" + UPDATED_FEENUM);
    }

    @Test
    @Transactional
    void getAllFeetypesByFeenumIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where feenum in DEFAULT_FEENUM or UPDATED_FEENUM
        defaultFeetypeShouldBeFound("feenum.in=" + DEFAULT_FEENUM + "," + UPDATED_FEENUM);

        // Get all the feetypeList where feenum equals to UPDATED_FEENUM
        defaultFeetypeShouldNotBeFound("feenum.in=" + UPDATED_FEENUM);
    }

    @Test
    @Transactional
    void getAllFeetypesByFeenumIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where feenum is not null
        defaultFeetypeShouldBeFound("feenum.specified=true");

        // Get all the feetypeList where feenum is null
        defaultFeetypeShouldNotBeFound("feenum.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByFeenumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where feenum is greater than or equal to DEFAULT_FEENUM
        defaultFeetypeShouldBeFound("feenum.greaterThanOrEqual=" + DEFAULT_FEENUM);

        // Get all the feetypeList where feenum is greater than or equal to UPDATED_FEENUM
        defaultFeetypeShouldNotBeFound("feenum.greaterThanOrEqual=" + UPDATED_FEENUM);
    }

    @Test
    @Transactional
    void getAllFeetypesByFeenumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where feenum is less than or equal to DEFAULT_FEENUM
        defaultFeetypeShouldBeFound("feenum.lessThanOrEqual=" + DEFAULT_FEENUM);

        // Get all the feetypeList where feenum is less than or equal to SMALLER_FEENUM
        defaultFeetypeShouldNotBeFound("feenum.lessThanOrEqual=" + SMALLER_FEENUM);
    }

    @Test
    @Transactional
    void getAllFeetypesByFeenumIsLessThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where feenum is less than DEFAULT_FEENUM
        defaultFeetypeShouldNotBeFound("feenum.lessThan=" + DEFAULT_FEENUM);

        // Get all the feetypeList where feenum is less than UPDATED_FEENUM
        defaultFeetypeShouldBeFound("feenum.lessThan=" + UPDATED_FEENUM);
    }

    @Test
    @Transactional
    void getAllFeetypesByFeenumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where feenum is greater than DEFAULT_FEENUM
        defaultFeetypeShouldNotBeFound("feenum.greaterThan=" + DEFAULT_FEENUM);

        // Get all the feetypeList where feenum is greater than SMALLER_FEENUM
        defaultFeetypeShouldBeFound("feenum.greaterThan=" + SMALLER_FEENUM);
    }

    @Test
    @Transactional
    void getAllFeetypesByFeenameIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where feename equals to DEFAULT_FEENAME
        defaultFeetypeShouldBeFound("feename.equals=" + DEFAULT_FEENAME);

        // Get all the feetypeList where feename equals to UPDATED_FEENAME
        defaultFeetypeShouldNotBeFound("feename.equals=" + UPDATED_FEENAME);
    }

    @Test
    @Transactional
    void getAllFeetypesByFeenameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where feename not equals to DEFAULT_FEENAME
        defaultFeetypeShouldNotBeFound("feename.notEquals=" + DEFAULT_FEENAME);

        // Get all the feetypeList where feename not equals to UPDATED_FEENAME
        defaultFeetypeShouldBeFound("feename.notEquals=" + UPDATED_FEENAME);
    }

    @Test
    @Transactional
    void getAllFeetypesByFeenameIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where feename in DEFAULT_FEENAME or UPDATED_FEENAME
        defaultFeetypeShouldBeFound("feename.in=" + DEFAULT_FEENAME + "," + UPDATED_FEENAME);

        // Get all the feetypeList where feename equals to UPDATED_FEENAME
        defaultFeetypeShouldNotBeFound("feename.in=" + UPDATED_FEENAME);
    }

    @Test
    @Transactional
    void getAllFeetypesByFeenameIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where feename is not null
        defaultFeetypeShouldBeFound("feename.specified=true");

        // Get all the feetypeList where feename is null
        defaultFeetypeShouldNotBeFound("feename.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByFeenameContainsSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where feename contains DEFAULT_FEENAME
        defaultFeetypeShouldBeFound("feename.contains=" + DEFAULT_FEENAME);

        // Get all the feetypeList where feename contains UPDATED_FEENAME
        defaultFeetypeShouldNotBeFound("feename.contains=" + UPDATED_FEENAME);
    }

    @Test
    @Transactional
    void getAllFeetypesByFeenameNotContainsSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where feename does not contain DEFAULT_FEENAME
        defaultFeetypeShouldNotBeFound("feename.doesNotContain=" + DEFAULT_FEENAME);

        // Get all the feetypeList where feename does not contain UPDATED_FEENAME
        defaultFeetypeShouldBeFound("feename.doesNotContain=" + UPDATED_FEENAME);
    }

    @Test
    @Transactional
    void getAllFeetypesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where price equals to DEFAULT_PRICE
        defaultFeetypeShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the feetypeList where price equals to UPDATED_PRICE
        defaultFeetypeShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllFeetypesByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where price not equals to DEFAULT_PRICE
        defaultFeetypeShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the feetypeList where price not equals to UPDATED_PRICE
        defaultFeetypeShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllFeetypesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultFeetypeShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the feetypeList where price equals to UPDATED_PRICE
        defaultFeetypeShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllFeetypesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where price is not null
        defaultFeetypeShouldBeFound("price.specified=true");

        // Get all the feetypeList where price is null
        defaultFeetypeShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where price is greater than or equal to DEFAULT_PRICE
        defaultFeetypeShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the feetypeList where price is greater than or equal to UPDATED_PRICE
        defaultFeetypeShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllFeetypesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where price is less than or equal to DEFAULT_PRICE
        defaultFeetypeShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the feetypeList where price is less than or equal to SMALLER_PRICE
        defaultFeetypeShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllFeetypesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where price is less than DEFAULT_PRICE
        defaultFeetypeShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the feetypeList where price is less than UPDATED_PRICE
        defaultFeetypeShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllFeetypesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where price is greater than DEFAULT_PRICE
        defaultFeetypeShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the feetypeList where price is greater than SMALLER_PRICE
        defaultFeetypeShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllFeetypesBySignIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where sign equals to DEFAULT_SIGN
        defaultFeetypeShouldBeFound("sign.equals=" + DEFAULT_SIGN);

        // Get all the feetypeList where sign equals to UPDATED_SIGN
        defaultFeetypeShouldNotBeFound("sign.equals=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesBySignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where sign not equals to DEFAULT_SIGN
        defaultFeetypeShouldNotBeFound("sign.notEquals=" + DEFAULT_SIGN);

        // Get all the feetypeList where sign not equals to UPDATED_SIGN
        defaultFeetypeShouldBeFound("sign.notEquals=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesBySignIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where sign in DEFAULT_SIGN or UPDATED_SIGN
        defaultFeetypeShouldBeFound("sign.in=" + DEFAULT_SIGN + "," + UPDATED_SIGN);

        // Get all the feetypeList where sign equals to UPDATED_SIGN
        defaultFeetypeShouldNotBeFound("sign.in=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesBySignIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where sign is not null
        defaultFeetypeShouldBeFound("sign.specified=true");

        // Get all the feetypeList where sign is null
        defaultFeetypeShouldNotBeFound("sign.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesBySignIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where sign is greater than or equal to DEFAULT_SIGN
        defaultFeetypeShouldBeFound("sign.greaterThanOrEqual=" + DEFAULT_SIGN);

        // Get all the feetypeList where sign is greater than or equal to UPDATED_SIGN
        defaultFeetypeShouldNotBeFound("sign.greaterThanOrEqual=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesBySignIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where sign is less than or equal to DEFAULT_SIGN
        defaultFeetypeShouldBeFound("sign.lessThanOrEqual=" + DEFAULT_SIGN);

        // Get all the feetypeList where sign is less than or equal to SMALLER_SIGN
        defaultFeetypeShouldNotBeFound("sign.lessThanOrEqual=" + SMALLER_SIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesBySignIsLessThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where sign is less than DEFAULT_SIGN
        defaultFeetypeShouldNotBeFound("sign.lessThan=" + DEFAULT_SIGN);

        // Get all the feetypeList where sign is less than UPDATED_SIGN
        defaultFeetypeShouldBeFound("sign.lessThan=" + UPDATED_SIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesBySignIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where sign is greater than DEFAULT_SIGN
        defaultFeetypeShouldNotBeFound("sign.greaterThan=" + DEFAULT_SIGN);

        // Get all the feetypeList where sign is greater than SMALLER_SIGN
        defaultFeetypeShouldBeFound("sign.greaterThan=" + SMALLER_SIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByBeizhuIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where beizhu equals to DEFAULT_BEIZHU
        defaultFeetypeShouldBeFound("beizhu.equals=" + DEFAULT_BEIZHU);

        // Get all the feetypeList where beizhu equals to UPDATED_BEIZHU
        defaultFeetypeShouldNotBeFound("beizhu.equals=" + UPDATED_BEIZHU);
    }

    @Test
    @Transactional
    void getAllFeetypesByBeizhuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where beizhu not equals to DEFAULT_BEIZHU
        defaultFeetypeShouldNotBeFound("beizhu.notEquals=" + DEFAULT_BEIZHU);

        // Get all the feetypeList where beizhu not equals to UPDATED_BEIZHU
        defaultFeetypeShouldBeFound("beizhu.notEquals=" + UPDATED_BEIZHU);
    }

    @Test
    @Transactional
    void getAllFeetypesByBeizhuIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where beizhu in DEFAULT_BEIZHU or UPDATED_BEIZHU
        defaultFeetypeShouldBeFound("beizhu.in=" + DEFAULT_BEIZHU + "," + UPDATED_BEIZHU);

        // Get all the feetypeList where beizhu equals to UPDATED_BEIZHU
        defaultFeetypeShouldNotBeFound("beizhu.in=" + UPDATED_BEIZHU);
    }

    @Test
    @Transactional
    void getAllFeetypesByBeizhuIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where beizhu is not null
        defaultFeetypeShouldBeFound("beizhu.specified=true");

        // Get all the feetypeList where beizhu is null
        defaultFeetypeShouldNotBeFound("beizhu.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByBeizhuContainsSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where beizhu contains DEFAULT_BEIZHU
        defaultFeetypeShouldBeFound("beizhu.contains=" + DEFAULT_BEIZHU);

        // Get all the feetypeList where beizhu contains UPDATED_BEIZHU
        defaultFeetypeShouldNotBeFound("beizhu.contains=" + UPDATED_BEIZHU);
    }

    @Test
    @Transactional
    void getAllFeetypesByBeizhuNotContainsSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where beizhu does not contain DEFAULT_BEIZHU
        defaultFeetypeShouldNotBeFound("beizhu.doesNotContain=" + DEFAULT_BEIZHU);

        // Get all the feetypeList where beizhu does not contain UPDATED_BEIZHU
        defaultFeetypeShouldBeFound("beizhu.doesNotContain=" + UPDATED_BEIZHU);
    }

    @Test
    @Transactional
    void getAllFeetypesByPymIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where pym equals to DEFAULT_PYM
        defaultFeetypeShouldBeFound("pym.equals=" + DEFAULT_PYM);

        // Get all the feetypeList where pym equals to UPDATED_PYM
        defaultFeetypeShouldNotBeFound("pym.equals=" + UPDATED_PYM);
    }

    @Test
    @Transactional
    void getAllFeetypesByPymIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where pym not equals to DEFAULT_PYM
        defaultFeetypeShouldNotBeFound("pym.notEquals=" + DEFAULT_PYM);

        // Get all the feetypeList where pym not equals to UPDATED_PYM
        defaultFeetypeShouldBeFound("pym.notEquals=" + UPDATED_PYM);
    }

    @Test
    @Transactional
    void getAllFeetypesByPymIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where pym in DEFAULT_PYM or UPDATED_PYM
        defaultFeetypeShouldBeFound("pym.in=" + DEFAULT_PYM + "," + UPDATED_PYM);

        // Get all the feetypeList where pym equals to UPDATED_PYM
        defaultFeetypeShouldNotBeFound("pym.in=" + UPDATED_PYM);
    }

    @Test
    @Transactional
    void getAllFeetypesByPymIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where pym is not null
        defaultFeetypeShouldBeFound("pym.specified=true");

        // Get all the feetypeList where pym is null
        defaultFeetypeShouldNotBeFound("pym.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByPymContainsSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where pym contains DEFAULT_PYM
        defaultFeetypeShouldBeFound("pym.contains=" + DEFAULT_PYM);

        // Get all the feetypeList where pym contains UPDATED_PYM
        defaultFeetypeShouldNotBeFound("pym.contains=" + UPDATED_PYM);
    }

    @Test
    @Transactional
    void getAllFeetypesByPymNotContainsSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where pym does not contain DEFAULT_PYM
        defaultFeetypeShouldNotBeFound("pym.doesNotContain=" + DEFAULT_PYM);

        // Get all the feetypeList where pym does not contain UPDATED_PYM
        defaultFeetypeShouldBeFound("pym.doesNotContain=" + UPDATED_PYM);
    }

    @Test
    @Transactional
    void getAllFeetypesBySalespotnIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where salespotn equals to DEFAULT_SALESPOTN
        defaultFeetypeShouldBeFound("salespotn.equals=" + DEFAULT_SALESPOTN);

        // Get all the feetypeList where salespotn equals to UPDATED_SALESPOTN
        defaultFeetypeShouldNotBeFound("salespotn.equals=" + UPDATED_SALESPOTN);
    }

    @Test
    @Transactional
    void getAllFeetypesBySalespotnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where salespotn not equals to DEFAULT_SALESPOTN
        defaultFeetypeShouldNotBeFound("salespotn.notEquals=" + DEFAULT_SALESPOTN);

        // Get all the feetypeList where salespotn not equals to UPDATED_SALESPOTN
        defaultFeetypeShouldBeFound("salespotn.notEquals=" + UPDATED_SALESPOTN);
    }

    @Test
    @Transactional
    void getAllFeetypesBySalespotnIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where salespotn in DEFAULT_SALESPOTN or UPDATED_SALESPOTN
        defaultFeetypeShouldBeFound("salespotn.in=" + DEFAULT_SALESPOTN + "," + UPDATED_SALESPOTN);

        // Get all the feetypeList where salespotn equals to UPDATED_SALESPOTN
        defaultFeetypeShouldNotBeFound("salespotn.in=" + UPDATED_SALESPOTN);
    }

    @Test
    @Transactional
    void getAllFeetypesBySalespotnIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where salespotn is not null
        defaultFeetypeShouldBeFound("salespotn.specified=true");

        // Get all the feetypeList where salespotn is null
        defaultFeetypeShouldNotBeFound("salespotn.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesBySalespotnIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where salespotn is greater than or equal to DEFAULT_SALESPOTN
        defaultFeetypeShouldBeFound("salespotn.greaterThanOrEqual=" + DEFAULT_SALESPOTN);

        // Get all the feetypeList where salespotn is greater than or equal to UPDATED_SALESPOTN
        defaultFeetypeShouldNotBeFound("salespotn.greaterThanOrEqual=" + UPDATED_SALESPOTN);
    }

    @Test
    @Transactional
    void getAllFeetypesBySalespotnIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where salespotn is less than or equal to DEFAULT_SALESPOTN
        defaultFeetypeShouldBeFound("salespotn.lessThanOrEqual=" + DEFAULT_SALESPOTN);

        // Get all the feetypeList where salespotn is less than or equal to SMALLER_SALESPOTN
        defaultFeetypeShouldNotBeFound("salespotn.lessThanOrEqual=" + SMALLER_SALESPOTN);
    }

    @Test
    @Transactional
    void getAllFeetypesBySalespotnIsLessThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where salespotn is less than DEFAULT_SALESPOTN
        defaultFeetypeShouldNotBeFound("salespotn.lessThan=" + DEFAULT_SALESPOTN);

        // Get all the feetypeList where salespotn is less than UPDATED_SALESPOTN
        defaultFeetypeShouldBeFound("salespotn.lessThan=" + UPDATED_SALESPOTN);
    }

    @Test
    @Transactional
    void getAllFeetypesBySalespotnIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where salespotn is greater than DEFAULT_SALESPOTN
        defaultFeetypeShouldNotBeFound("salespotn.greaterThan=" + DEFAULT_SALESPOTN);

        // Get all the feetypeList where salespotn is greater than SMALLER_SALESPOTN
        defaultFeetypeShouldBeFound("salespotn.greaterThan=" + SMALLER_SALESPOTN);
    }

    @Test
    @Transactional
    void getAllFeetypesByDepotIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where depot equals to DEFAULT_DEPOT
        defaultFeetypeShouldBeFound("depot.equals=" + DEFAULT_DEPOT);

        // Get all the feetypeList where depot equals to UPDATED_DEPOT
        defaultFeetypeShouldNotBeFound("depot.equals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllFeetypesByDepotIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where depot not equals to DEFAULT_DEPOT
        defaultFeetypeShouldNotBeFound("depot.notEquals=" + DEFAULT_DEPOT);

        // Get all the feetypeList where depot not equals to UPDATED_DEPOT
        defaultFeetypeShouldBeFound("depot.notEquals=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllFeetypesByDepotIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where depot in DEFAULT_DEPOT or UPDATED_DEPOT
        defaultFeetypeShouldBeFound("depot.in=" + DEFAULT_DEPOT + "," + UPDATED_DEPOT);

        // Get all the feetypeList where depot equals to UPDATED_DEPOT
        defaultFeetypeShouldNotBeFound("depot.in=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllFeetypesByDepotIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where depot is not null
        defaultFeetypeShouldBeFound("depot.specified=true");

        // Get all the feetypeList where depot is null
        defaultFeetypeShouldNotBeFound("depot.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByDepotContainsSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where depot contains DEFAULT_DEPOT
        defaultFeetypeShouldBeFound("depot.contains=" + DEFAULT_DEPOT);

        // Get all the feetypeList where depot contains UPDATED_DEPOT
        defaultFeetypeShouldNotBeFound("depot.contains=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllFeetypesByDepotNotContainsSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where depot does not contain DEFAULT_DEPOT
        defaultFeetypeShouldNotBeFound("depot.doesNotContain=" + DEFAULT_DEPOT);

        // Get all the feetypeList where depot does not contain UPDATED_DEPOT
        defaultFeetypeShouldBeFound("depot.doesNotContain=" + UPDATED_DEPOT);
    }

    @Test
    @Transactional
    void getAllFeetypesByCbsignIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where cbsign equals to DEFAULT_CBSIGN
        defaultFeetypeShouldBeFound("cbsign.equals=" + DEFAULT_CBSIGN);

        // Get all the feetypeList where cbsign equals to UPDATED_CBSIGN
        defaultFeetypeShouldNotBeFound("cbsign.equals=" + UPDATED_CBSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByCbsignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where cbsign not equals to DEFAULT_CBSIGN
        defaultFeetypeShouldNotBeFound("cbsign.notEquals=" + DEFAULT_CBSIGN);

        // Get all the feetypeList where cbsign not equals to UPDATED_CBSIGN
        defaultFeetypeShouldBeFound("cbsign.notEquals=" + UPDATED_CBSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByCbsignIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where cbsign in DEFAULT_CBSIGN or UPDATED_CBSIGN
        defaultFeetypeShouldBeFound("cbsign.in=" + DEFAULT_CBSIGN + "," + UPDATED_CBSIGN);

        // Get all the feetypeList where cbsign equals to UPDATED_CBSIGN
        defaultFeetypeShouldNotBeFound("cbsign.in=" + UPDATED_CBSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByCbsignIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where cbsign is not null
        defaultFeetypeShouldBeFound("cbsign.specified=true");

        // Get all the feetypeList where cbsign is null
        defaultFeetypeShouldNotBeFound("cbsign.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByCbsignIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where cbsign is greater than or equal to DEFAULT_CBSIGN
        defaultFeetypeShouldBeFound("cbsign.greaterThanOrEqual=" + DEFAULT_CBSIGN);

        // Get all the feetypeList where cbsign is greater than or equal to UPDATED_CBSIGN
        defaultFeetypeShouldNotBeFound("cbsign.greaterThanOrEqual=" + UPDATED_CBSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByCbsignIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where cbsign is less than or equal to DEFAULT_CBSIGN
        defaultFeetypeShouldBeFound("cbsign.lessThanOrEqual=" + DEFAULT_CBSIGN);

        // Get all the feetypeList where cbsign is less than or equal to SMALLER_CBSIGN
        defaultFeetypeShouldNotBeFound("cbsign.lessThanOrEqual=" + SMALLER_CBSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByCbsignIsLessThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where cbsign is less than DEFAULT_CBSIGN
        defaultFeetypeShouldNotBeFound("cbsign.lessThan=" + DEFAULT_CBSIGN);

        // Get all the feetypeList where cbsign is less than UPDATED_CBSIGN
        defaultFeetypeShouldBeFound("cbsign.lessThan=" + UPDATED_CBSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByCbsignIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where cbsign is greater than DEFAULT_CBSIGN
        defaultFeetypeShouldNotBeFound("cbsign.greaterThan=" + DEFAULT_CBSIGN);

        // Get all the feetypeList where cbsign is greater than SMALLER_CBSIGN
        defaultFeetypeShouldBeFound("cbsign.greaterThan=" + SMALLER_CBSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByOrdersignIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ordersign equals to DEFAULT_ORDERSIGN
        defaultFeetypeShouldBeFound("ordersign.equals=" + DEFAULT_ORDERSIGN);

        // Get all the feetypeList where ordersign equals to UPDATED_ORDERSIGN
        defaultFeetypeShouldNotBeFound("ordersign.equals=" + UPDATED_ORDERSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByOrdersignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ordersign not equals to DEFAULT_ORDERSIGN
        defaultFeetypeShouldNotBeFound("ordersign.notEquals=" + DEFAULT_ORDERSIGN);

        // Get all the feetypeList where ordersign not equals to UPDATED_ORDERSIGN
        defaultFeetypeShouldBeFound("ordersign.notEquals=" + UPDATED_ORDERSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByOrdersignIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ordersign in DEFAULT_ORDERSIGN or UPDATED_ORDERSIGN
        defaultFeetypeShouldBeFound("ordersign.in=" + DEFAULT_ORDERSIGN + "," + UPDATED_ORDERSIGN);

        // Get all the feetypeList where ordersign equals to UPDATED_ORDERSIGN
        defaultFeetypeShouldNotBeFound("ordersign.in=" + UPDATED_ORDERSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByOrdersignIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ordersign is not null
        defaultFeetypeShouldBeFound("ordersign.specified=true");

        // Get all the feetypeList where ordersign is null
        defaultFeetypeShouldNotBeFound("ordersign.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByOrdersignIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ordersign is greater than or equal to DEFAULT_ORDERSIGN
        defaultFeetypeShouldBeFound("ordersign.greaterThanOrEqual=" + DEFAULT_ORDERSIGN);

        // Get all the feetypeList where ordersign is greater than or equal to UPDATED_ORDERSIGN
        defaultFeetypeShouldNotBeFound("ordersign.greaterThanOrEqual=" + UPDATED_ORDERSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByOrdersignIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ordersign is less than or equal to DEFAULT_ORDERSIGN
        defaultFeetypeShouldBeFound("ordersign.lessThanOrEqual=" + DEFAULT_ORDERSIGN);

        // Get all the feetypeList where ordersign is less than or equal to SMALLER_ORDERSIGN
        defaultFeetypeShouldNotBeFound("ordersign.lessThanOrEqual=" + SMALLER_ORDERSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByOrdersignIsLessThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ordersign is less than DEFAULT_ORDERSIGN
        defaultFeetypeShouldNotBeFound("ordersign.lessThan=" + DEFAULT_ORDERSIGN);

        // Get all the feetypeList where ordersign is less than UPDATED_ORDERSIGN
        defaultFeetypeShouldBeFound("ordersign.lessThan=" + UPDATED_ORDERSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByOrdersignIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ordersign is greater than DEFAULT_ORDERSIGN
        defaultFeetypeShouldNotBeFound("ordersign.greaterThan=" + DEFAULT_ORDERSIGN);

        // Get all the feetypeList where ordersign is greater than SMALLER_ORDERSIGN
        defaultFeetypeShouldBeFound("ordersign.greaterThan=" + SMALLER_ORDERSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByHoteldmIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where hoteldm equals to DEFAULT_HOTELDM
        defaultFeetypeShouldBeFound("hoteldm.equals=" + DEFAULT_HOTELDM);

        // Get all the feetypeList where hoteldm equals to UPDATED_HOTELDM
        defaultFeetypeShouldNotBeFound("hoteldm.equals=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllFeetypesByHoteldmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where hoteldm not equals to DEFAULT_HOTELDM
        defaultFeetypeShouldNotBeFound("hoteldm.notEquals=" + DEFAULT_HOTELDM);

        // Get all the feetypeList where hoteldm not equals to UPDATED_HOTELDM
        defaultFeetypeShouldBeFound("hoteldm.notEquals=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllFeetypesByHoteldmIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where hoteldm in DEFAULT_HOTELDM or UPDATED_HOTELDM
        defaultFeetypeShouldBeFound("hoteldm.in=" + DEFAULT_HOTELDM + "," + UPDATED_HOTELDM);

        // Get all the feetypeList where hoteldm equals to UPDATED_HOTELDM
        defaultFeetypeShouldNotBeFound("hoteldm.in=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllFeetypesByHoteldmIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where hoteldm is not null
        defaultFeetypeShouldBeFound("hoteldm.specified=true");

        // Get all the feetypeList where hoteldm is null
        defaultFeetypeShouldNotBeFound("hoteldm.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByHoteldmContainsSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where hoteldm contains DEFAULT_HOTELDM
        defaultFeetypeShouldBeFound("hoteldm.contains=" + DEFAULT_HOTELDM);

        // Get all the feetypeList where hoteldm contains UPDATED_HOTELDM
        defaultFeetypeShouldNotBeFound("hoteldm.contains=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllFeetypesByHoteldmNotContainsSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where hoteldm does not contain DEFAULT_HOTELDM
        defaultFeetypeShouldNotBeFound("hoteldm.doesNotContain=" + DEFAULT_HOTELDM);

        // Get all the feetypeList where hoteldm does not contain UPDATED_HOTELDM
        defaultFeetypeShouldBeFound("hoteldm.doesNotContain=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllFeetypesByIsnewIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where isnew equals to DEFAULT_ISNEW
        defaultFeetypeShouldBeFound("isnew.equals=" + DEFAULT_ISNEW);

        // Get all the feetypeList where isnew equals to UPDATED_ISNEW
        defaultFeetypeShouldNotBeFound("isnew.equals=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllFeetypesByIsnewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where isnew not equals to DEFAULT_ISNEW
        defaultFeetypeShouldNotBeFound("isnew.notEquals=" + DEFAULT_ISNEW);

        // Get all the feetypeList where isnew not equals to UPDATED_ISNEW
        defaultFeetypeShouldBeFound("isnew.notEquals=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllFeetypesByIsnewIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where isnew in DEFAULT_ISNEW or UPDATED_ISNEW
        defaultFeetypeShouldBeFound("isnew.in=" + DEFAULT_ISNEW + "," + UPDATED_ISNEW);

        // Get all the feetypeList where isnew equals to UPDATED_ISNEW
        defaultFeetypeShouldNotBeFound("isnew.in=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllFeetypesByIsnewIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where isnew is not null
        defaultFeetypeShouldBeFound("isnew.specified=true");

        // Get all the feetypeList where isnew is null
        defaultFeetypeShouldNotBeFound("isnew.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByIsnewIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where isnew is greater than or equal to DEFAULT_ISNEW
        defaultFeetypeShouldBeFound("isnew.greaterThanOrEqual=" + DEFAULT_ISNEW);

        // Get all the feetypeList where isnew is greater than or equal to UPDATED_ISNEW
        defaultFeetypeShouldNotBeFound("isnew.greaterThanOrEqual=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllFeetypesByIsnewIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where isnew is less than or equal to DEFAULT_ISNEW
        defaultFeetypeShouldBeFound("isnew.lessThanOrEqual=" + DEFAULT_ISNEW);

        // Get all the feetypeList where isnew is less than or equal to SMALLER_ISNEW
        defaultFeetypeShouldNotBeFound("isnew.lessThanOrEqual=" + SMALLER_ISNEW);
    }

    @Test
    @Transactional
    void getAllFeetypesByIsnewIsLessThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where isnew is less than DEFAULT_ISNEW
        defaultFeetypeShouldNotBeFound("isnew.lessThan=" + DEFAULT_ISNEW);

        // Get all the feetypeList where isnew is less than UPDATED_ISNEW
        defaultFeetypeShouldBeFound("isnew.lessThan=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllFeetypesByIsnewIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where isnew is greater than DEFAULT_ISNEW
        defaultFeetypeShouldNotBeFound("isnew.greaterThan=" + DEFAULT_ISNEW);

        // Get all the feetypeList where isnew is greater than SMALLER_ISNEW
        defaultFeetypeShouldBeFound("isnew.greaterThan=" + SMALLER_ISNEW);
    }

    @Test
    @Transactional
    void getAllFeetypesByYgjIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ygj equals to DEFAULT_YGJ
        defaultFeetypeShouldBeFound("ygj.equals=" + DEFAULT_YGJ);

        // Get all the feetypeList where ygj equals to UPDATED_YGJ
        defaultFeetypeShouldNotBeFound("ygj.equals=" + UPDATED_YGJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByYgjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ygj not equals to DEFAULT_YGJ
        defaultFeetypeShouldNotBeFound("ygj.notEquals=" + DEFAULT_YGJ);

        // Get all the feetypeList where ygj not equals to UPDATED_YGJ
        defaultFeetypeShouldBeFound("ygj.notEquals=" + UPDATED_YGJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByYgjIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ygj in DEFAULT_YGJ or UPDATED_YGJ
        defaultFeetypeShouldBeFound("ygj.in=" + DEFAULT_YGJ + "," + UPDATED_YGJ);

        // Get all the feetypeList where ygj equals to UPDATED_YGJ
        defaultFeetypeShouldNotBeFound("ygj.in=" + UPDATED_YGJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByYgjIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ygj is not null
        defaultFeetypeShouldBeFound("ygj.specified=true");

        // Get all the feetypeList where ygj is null
        defaultFeetypeShouldNotBeFound("ygj.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByYgjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ygj is greater than or equal to DEFAULT_YGJ
        defaultFeetypeShouldBeFound("ygj.greaterThanOrEqual=" + DEFAULT_YGJ);

        // Get all the feetypeList where ygj is greater than or equal to UPDATED_YGJ
        defaultFeetypeShouldNotBeFound("ygj.greaterThanOrEqual=" + UPDATED_YGJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByYgjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ygj is less than or equal to DEFAULT_YGJ
        defaultFeetypeShouldBeFound("ygj.lessThanOrEqual=" + DEFAULT_YGJ);

        // Get all the feetypeList where ygj is less than or equal to SMALLER_YGJ
        defaultFeetypeShouldNotBeFound("ygj.lessThanOrEqual=" + SMALLER_YGJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByYgjIsLessThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ygj is less than DEFAULT_YGJ
        defaultFeetypeShouldNotBeFound("ygj.lessThan=" + DEFAULT_YGJ);

        // Get all the feetypeList where ygj is less than UPDATED_YGJ
        defaultFeetypeShouldBeFound("ygj.lessThan=" + UPDATED_YGJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByYgjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where ygj is greater than DEFAULT_YGJ
        defaultFeetypeShouldNotBeFound("ygj.greaterThan=" + DEFAULT_YGJ);

        // Get all the feetypeList where ygj is greater than SMALLER_YGJ
        defaultFeetypeShouldBeFound("ygj.greaterThan=" + SMALLER_YGJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByAutosignIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where autosign equals to DEFAULT_AUTOSIGN
        defaultFeetypeShouldBeFound("autosign.equals=" + DEFAULT_AUTOSIGN);

        // Get all the feetypeList where autosign equals to UPDATED_AUTOSIGN
        defaultFeetypeShouldNotBeFound("autosign.equals=" + UPDATED_AUTOSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByAutosignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where autosign not equals to DEFAULT_AUTOSIGN
        defaultFeetypeShouldNotBeFound("autosign.notEquals=" + DEFAULT_AUTOSIGN);

        // Get all the feetypeList where autosign not equals to UPDATED_AUTOSIGN
        defaultFeetypeShouldBeFound("autosign.notEquals=" + UPDATED_AUTOSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByAutosignIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where autosign in DEFAULT_AUTOSIGN or UPDATED_AUTOSIGN
        defaultFeetypeShouldBeFound("autosign.in=" + DEFAULT_AUTOSIGN + "," + UPDATED_AUTOSIGN);

        // Get all the feetypeList where autosign equals to UPDATED_AUTOSIGN
        defaultFeetypeShouldNotBeFound("autosign.in=" + UPDATED_AUTOSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByAutosignIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where autosign is not null
        defaultFeetypeShouldBeFound("autosign.specified=true");

        // Get all the feetypeList where autosign is null
        defaultFeetypeShouldNotBeFound("autosign.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByAutosignContainsSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where autosign contains DEFAULT_AUTOSIGN
        defaultFeetypeShouldBeFound("autosign.contains=" + DEFAULT_AUTOSIGN);

        // Get all the feetypeList where autosign contains UPDATED_AUTOSIGN
        defaultFeetypeShouldNotBeFound("autosign.contains=" + UPDATED_AUTOSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByAutosignNotContainsSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where autosign does not contain DEFAULT_AUTOSIGN
        defaultFeetypeShouldNotBeFound("autosign.doesNotContain=" + DEFAULT_AUTOSIGN);

        // Get all the feetypeList where autosign does not contain UPDATED_AUTOSIGN
        defaultFeetypeShouldBeFound("autosign.doesNotContain=" + UPDATED_AUTOSIGN);
    }

    @Test
    @Transactional
    void getAllFeetypesByJjIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where jj equals to DEFAULT_JJ
        defaultFeetypeShouldBeFound("jj.equals=" + DEFAULT_JJ);

        // Get all the feetypeList where jj equals to UPDATED_JJ
        defaultFeetypeShouldNotBeFound("jj.equals=" + UPDATED_JJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByJjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where jj not equals to DEFAULT_JJ
        defaultFeetypeShouldNotBeFound("jj.notEquals=" + DEFAULT_JJ);

        // Get all the feetypeList where jj not equals to UPDATED_JJ
        defaultFeetypeShouldBeFound("jj.notEquals=" + UPDATED_JJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByJjIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where jj in DEFAULT_JJ or UPDATED_JJ
        defaultFeetypeShouldBeFound("jj.in=" + DEFAULT_JJ + "," + UPDATED_JJ);

        // Get all the feetypeList where jj equals to UPDATED_JJ
        defaultFeetypeShouldNotBeFound("jj.in=" + UPDATED_JJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByJjIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where jj is not null
        defaultFeetypeShouldBeFound("jj.specified=true");

        // Get all the feetypeList where jj is null
        defaultFeetypeShouldNotBeFound("jj.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByJjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where jj is greater than or equal to DEFAULT_JJ
        defaultFeetypeShouldBeFound("jj.greaterThanOrEqual=" + DEFAULT_JJ);

        // Get all the feetypeList where jj is greater than or equal to UPDATED_JJ
        defaultFeetypeShouldNotBeFound("jj.greaterThanOrEqual=" + UPDATED_JJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByJjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where jj is less than or equal to DEFAULT_JJ
        defaultFeetypeShouldBeFound("jj.lessThanOrEqual=" + DEFAULT_JJ);

        // Get all the feetypeList where jj is less than or equal to SMALLER_JJ
        defaultFeetypeShouldNotBeFound("jj.lessThanOrEqual=" + SMALLER_JJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByJjIsLessThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where jj is less than DEFAULT_JJ
        defaultFeetypeShouldNotBeFound("jj.lessThan=" + DEFAULT_JJ);

        // Get all the feetypeList where jj is less than UPDATED_JJ
        defaultFeetypeShouldBeFound("jj.lessThan=" + UPDATED_JJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByJjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where jj is greater than DEFAULT_JJ
        defaultFeetypeShouldNotBeFound("jj.greaterThan=" + DEFAULT_JJ);

        // Get all the feetypeList where jj is greater than SMALLER_JJ
        defaultFeetypeShouldBeFound("jj.greaterThan=" + SMALLER_JJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByHyjIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where hyj equals to DEFAULT_HYJ
        defaultFeetypeShouldBeFound("hyj.equals=" + DEFAULT_HYJ);

        // Get all the feetypeList where hyj equals to UPDATED_HYJ
        defaultFeetypeShouldNotBeFound("hyj.equals=" + UPDATED_HYJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByHyjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where hyj not equals to DEFAULT_HYJ
        defaultFeetypeShouldNotBeFound("hyj.notEquals=" + DEFAULT_HYJ);

        // Get all the feetypeList where hyj not equals to UPDATED_HYJ
        defaultFeetypeShouldBeFound("hyj.notEquals=" + UPDATED_HYJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByHyjIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where hyj in DEFAULT_HYJ or UPDATED_HYJ
        defaultFeetypeShouldBeFound("hyj.in=" + DEFAULT_HYJ + "," + UPDATED_HYJ);

        // Get all the feetypeList where hyj equals to UPDATED_HYJ
        defaultFeetypeShouldNotBeFound("hyj.in=" + UPDATED_HYJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByHyjIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where hyj is not null
        defaultFeetypeShouldBeFound("hyj.specified=true");

        // Get all the feetypeList where hyj is null
        defaultFeetypeShouldNotBeFound("hyj.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByHyjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where hyj is greater than or equal to DEFAULT_HYJ
        defaultFeetypeShouldBeFound("hyj.greaterThanOrEqual=" + DEFAULT_HYJ);

        // Get all the feetypeList where hyj is greater than or equal to UPDATED_HYJ
        defaultFeetypeShouldNotBeFound("hyj.greaterThanOrEqual=" + UPDATED_HYJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByHyjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where hyj is less than or equal to DEFAULT_HYJ
        defaultFeetypeShouldBeFound("hyj.lessThanOrEqual=" + DEFAULT_HYJ);

        // Get all the feetypeList where hyj is less than or equal to SMALLER_HYJ
        defaultFeetypeShouldNotBeFound("hyj.lessThanOrEqual=" + SMALLER_HYJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByHyjIsLessThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where hyj is less than DEFAULT_HYJ
        defaultFeetypeShouldNotBeFound("hyj.lessThan=" + DEFAULT_HYJ);

        // Get all the feetypeList where hyj is less than UPDATED_HYJ
        defaultFeetypeShouldBeFound("hyj.lessThan=" + UPDATED_HYJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByHyjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where hyj is greater than DEFAULT_HYJ
        defaultFeetypeShouldNotBeFound("hyj.greaterThan=" + DEFAULT_HYJ);

        // Get all the feetypeList where hyj is greater than SMALLER_HYJ
        defaultFeetypeShouldBeFound("hyj.greaterThan=" + SMALLER_HYJ);
    }

    @Test
    @Transactional
    void getAllFeetypesByDqkcIsEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where dqkc equals to DEFAULT_DQKC
        defaultFeetypeShouldBeFound("dqkc.equals=" + DEFAULT_DQKC);

        // Get all the feetypeList where dqkc equals to UPDATED_DQKC
        defaultFeetypeShouldNotBeFound("dqkc.equals=" + UPDATED_DQKC);
    }

    @Test
    @Transactional
    void getAllFeetypesByDqkcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where dqkc not equals to DEFAULT_DQKC
        defaultFeetypeShouldNotBeFound("dqkc.notEquals=" + DEFAULT_DQKC);

        // Get all the feetypeList where dqkc not equals to UPDATED_DQKC
        defaultFeetypeShouldBeFound("dqkc.notEquals=" + UPDATED_DQKC);
    }

    @Test
    @Transactional
    void getAllFeetypesByDqkcIsInShouldWork() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where dqkc in DEFAULT_DQKC or UPDATED_DQKC
        defaultFeetypeShouldBeFound("dqkc.in=" + DEFAULT_DQKC + "," + UPDATED_DQKC);

        // Get all the feetypeList where dqkc equals to UPDATED_DQKC
        defaultFeetypeShouldNotBeFound("dqkc.in=" + UPDATED_DQKC);
    }

    @Test
    @Transactional
    void getAllFeetypesByDqkcIsNullOrNotNull() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where dqkc is not null
        defaultFeetypeShouldBeFound("dqkc.specified=true");

        // Get all the feetypeList where dqkc is null
        defaultFeetypeShouldNotBeFound("dqkc.specified=false");
    }

    @Test
    @Transactional
    void getAllFeetypesByDqkcIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where dqkc is greater than or equal to DEFAULT_DQKC
        defaultFeetypeShouldBeFound("dqkc.greaterThanOrEqual=" + DEFAULT_DQKC);

        // Get all the feetypeList where dqkc is greater than or equal to UPDATED_DQKC
        defaultFeetypeShouldNotBeFound("dqkc.greaterThanOrEqual=" + UPDATED_DQKC);
    }

    @Test
    @Transactional
    void getAllFeetypesByDqkcIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where dqkc is less than or equal to DEFAULT_DQKC
        defaultFeetypeShouldBeFound("dqkc.lessThanOrEqual=" + DEFAULT_DQKC);

        // Get all the feetypeList where dqkc is less than or equal to SMALLER_DQKC
        defaultFeetypeShouldNotBeFound("dqkc.lessThanOrEqual=" + SMALLER_DQKC);
    }

    @Test
    @Transactional
    void getAllFeetypesByDqkcIsLessThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where dqkc is less than DEFAULT_DQKC
        defaultFeetypeShouldNotBeFound("dqkc.lessThan=" + DEFAULT_DQKC);

        // Get all the feetypeList where dqkc is less than UPDATED_DQKC
        defaultFeetypeShouldBeFound("dqkc.lessThan=" + UPDATED_DQKC);
    }

    @Test
    @Transactional
    void getAllFeetypesByDqkcIsGreaterThanSomething() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        // Get all the feetypeList where dqkc is greater than DEFAULT_DQKC
        defaultFeetypeShouldNotBeFound("dqkc.greaterThan=" + DEFAULT_DQKC);

        // Get all the feetypeList where dqkc is greater than SMALLER_DQKC
        defaultFeetypeShouldBeFound("dqkc.greaterThan=" + SMALLER_DQKC);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFeetypeShouldBeFound(String filter) throws Exception {
        restFeetypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feetype.getId().intValue())))
            .andExpect(jsonPath("$.[*].feenum").value(hasItem(DEFAULT_FEENUM.intValue())))
            .andExpect(jsonPath("$.[*].feename").value(hasItem(DEFAULT_FEENAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].beizhu").value(hasItem(DEFAULT_BEIZHU)))
            .andExpect(jsonPath("$.[*].pym").value(hasItem(DEFAULT_PYM)))
            .andExpect(jsonPath("$.[*].salespotn").value(hasItem(DEFAULT_SALESPOTN.intValue())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].cbsign").value(hasItem(DEFAULT_CBSIGN.intValue())))
            .andExpect(jsonPath("$.[*].ordersign").value(hasItem(DEFAULT_ORDERSIGN.intValue())))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())))
            .andExpect(jsonPath("$.[*].ygj").value(hasItem(sameNumber(DEFAULT_YGJ))))
            .andExpect(jsonPath("$.[*].autosign").value(hasItem(DEFAULT_AUTOSIGN)))
            .andExpect(jsonPath("$.[*].jj").value(hasItem(sameNumber(DEFAULT_JJ))))
            .andExpect(jsonPath("$.[*].hyj").value(hasItem(sameNumber(DEFAULT_HYJ))))
            .andExpect(jsonPath("$.[*].dqkc").value(hasItem(sameNumber(DEFAULT_DQKC))));

        // Check, that the count call also returns 1
        restFeetypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFeetypeShouldNotBeFound(String filter) throws Exception {
        restFeetypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFeetypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingFeetype() throws Exception {
        // Get the feetype
        restFeetypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFeetype() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        int databaseSizeBeforeUpdate = feetypeRepository.findAll().size();

        // Update the feetype
        Feetype updatedFeetype = feetypeRepository.findById(feetype.getId()).get();
        // Disconnect from session so that the updates on updatedFeetype are not directly saved in db
        em.detach(updatedFeetype);
        updatedFeetype
            .feenum(UPDATED_FEENUM)
            .feename(UPDATED_FEENAME)
            .price(UPDATED_PRICE)
            .sign(UPDATED_SIGN)
            .beizhu(UPDATED_BEIZHU)
            .pym(UPDATED_PYM)
            .salespotn(UPDATED_SALESPOTN)
            .depot(UPDATED_DEPOT)
            .cbsign(UPDATED_CBSIGN)
            .ordersign(UPDATED_ORDERSIGN)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW)
            .ygj(UPDATED_YGJ)
            .autosign(UPDATED_AUTOSIGN)
            .jj(UPDATED_JJ)
            .hyj(UPDATED_HYJ)
            .dqkc(UPDATED_DQKC);
        FeetypeDTO feetypeDTO = feetypeMapper.toDto(updatedFeetype);

        restFeetypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feetypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feetypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Feetype in the database
        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeUpdate);
        Feetype testFeetype = feetypeList.get(feetypeList.size() - 1);
        assertThat(testFeetype.getFeenum()).isEqualTo(UPDATED_FEENUM);
        assertThat(testFeetype.getFeename()).isEqualTo(UPDATED_FEENAME);
        assertThat(testFeetype.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFeetype.getSign()).isEqualTo(UPDATED_SIGN);
        assertThat(testFeetype.getBeizhu()).isEqualTo(UPDATED_BEIZHU);
        assertThat(testFeetype.getPym()).isEqualTo(UPDATED_PYM);
        assertThat(testFeetype.getSalespotn()).isEqualTo(UPDATED_SALESPOTN);
        assertThat(testFeetype.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testFeetype.getCbsign()).isEqualTo(UPDATED_CBSIGN);
        assertThat(testFeetype.getOrdersign()).isEqualTo(UPDATED_ORDERSIGN);
        assertThat(testFeetype.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testFeetype.getIsnew()).isEqualTo(UPDATED_ISNEW);
        assertThat(testFeetype.getYgj()).isEqualTo(UPDATED_YGJ);
        assertThat(testFeetype.getAutosign()).isEqualTo(UPDATED_AUTOSIGN);
        assertThat(testFeetype.getJj()).isEqualTo(UPDATED_JJ);
        assertThat(testFeetype.getHyj()).isEqualTo(UPDATED_HYJ);
        assertThat(testFeetype.getDqkc()).isEqualTo(UPDATED_DQKC);

        // Validate the Feetype in Elasticsearch
        verify(mockFeetypeSearchRepository).save(testFeetype);
    }

    @Test
    @Transactional
    void putNonExistingFeetype() throws Exception {
        int databaseSizeBeforeUpdate = feetypeRepository.findAll().size();
        feetype.setId(count.incrementAndGet());

        // Create the Feetype
        FeetypeDTO feetypeDTO = feetypeMapper.toDto(feetype);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeetypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, feetypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feetypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feetype in the database
        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Feetype in Elasticsearch
        verify(mockFeetypeSearchRepository, times(0)).save(feetype);
    }

    @Test
    @Transactional
    void putWithIdMismatchFeetype() throws Exception {
        int databaseSizeBeforeUpdate = feetypeRepository.findAll().size();
        feetype.setId(count.incrementAndGet());

        // Create the Feetype
        FeetypeDTO feetypeDTO = feetypeMapper.toDto(feetype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeetypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(feetypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feetype in the database
        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Feetype in Elasticsearch
        verify(mockFeetypeSearchRepository, times(0)).save(feetype);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFeetype() throws Exception {
        int databaseSizeBeforeUpdate = feetypeRepository.findAll().size();
        feetype.setId(count.incrementAndGet());

        // Create the Feetype
        FeetypeDTO feetypeDTO = feetypeMapper.toDto(feetype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeetypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(feetypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Feetype in the database
        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Feetype in Elasticsearch
        verify(mockFeetypeSearchRepository, times(0)).save(feetype);
    }

    @Test
    @Transactional
    void partialUpdateFeetypeWithPatch() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        int databaseSizeBeforeUpdate = feetypeRepository.findAll().size();

        // Update the feetype using partial update
        Feetype partialUpdatedFeetype = new Feetype();
        partialUpdatedFeetype.setId(feetype.getId());

        partialUpdatedFeetype
            .feenum(UPDATED_FEENUM)
            .beizhu(UPDATED_BEIZHU)
            .pym(UPDATED_PYM)
            .isnew(UPDATED_ISNEW)
            .ygj(UPDATED_YGJ)
            .autosign(UPDATED_AUTOSIGN)
            .dqkc(UPDATED_DQKC);

        restFeetypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeetype.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeetype))
            )
            .andExpect(status().isOk());

        // Validate the Feetype in the database
        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeUpdate);
        Feetype testFeetype = feetypeList.get(feetypeList.size() - 1);
        assertThat(testFeetype.getFeenum()).isEqualTo(UPDATED_FEENUM);
        assertThat(testFeetype.getFeename()).isEqualTo(DEFAULT_FEENAME);
        assertThat(testFeetype.getPrice()).isEqualByComparingTo(DEFAULT_PRICE);
        assertThat(testFeetype.getSign()).isEqualTo(DEFAULT_SIGN);
        assertThat(testFeetype.getBeizhu()).isEqualTo(UPDATED_BEIZHU);
        assertThat(testFeetype.getPym()).isEqualTo(UPDATED_PYM);
        assertThat(testFeetype.getSalespotn()).isEqualTo(DEFAULT_SALESPOTN);
        assertThat(testFeetype.getDepot()).isEqualTo(DEFAULT_DEPOT);
        assertThat(testFeetype.getCbsign()).isEqualTo(DEFAULT_CBSIGN);
        assertThat(testFeetype.getOrdersign()).isEqualTo(DEFAULT_ORDERSIGN);
        assertThat(testFeetype.getHoteldm()).isEqualTo(DEFAULT_HOTELDM);
        assertThat(testFeetype.getIsnew()).isEqualTo(UPDATED_ISNEW);
        assertThat(testFeetype.getYgj()).isEqualByComparingTo(UPDATED_YGJ);
        assertThat(testFeetype.getAutosign()).isEqualTo(UPDATED_AUTOSIGN);
        assertThat(testFeetype.getJj()).isEqualByComparingTo(DEFAULT_JJ);
        assertThat(testFeetype.getHyj()).isEqualByComparingTo(DEFAULT_HYJ);
        assertThat(testFeetype.getDqkc()).isEqualByComparingTo(UPDATED_DQKC);
    }

    @Test
    @Transactional
    void fullUpdateFeetypeWithPatch() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        int databaseSizeBeforeUpdate = feetypeRepository.findAll().size();

        // Update the feetype using partial update
        Feetype partialUpdatedFeetype = new Feetype();
        partialUpdatedFeetype.setId(feetype.getId());

        partialUpdatedFeetype
            .feenum(UPDATED_FEENUM)
            .feename(UPDATED_FEENAME)
            .price(UPDATED_PRICE)
            .sign(UPDATED_SIGN)
            .beizhu(UPDATED_BEIZHU)
            .pym(UPDATED_PYM)
            .salespotn(UPDATED_SALESPOTN)
            .depot(UPDATED_DEPOT)
            .cbsign(UPDATED_CBSIGN)
            .ordersign(UPDATED_ORDERSIGN)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW)
            .ygj(UPDATED_YGJ)
            .autosign(UPDATED_AUTOSIGN)
            .jj(UPDATED_JJ)
            .hyj(UPDATED_HYJ)
            .dqkc(UPDATED_DQKC);

        restFeetypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFeetype.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFeetype))
            )
            .andExpect(status().isOk());

        // Validate the Feetype in the database
        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeUpdate);
        Feetype testFeetype = feetypeList.get(feetypeList.size() - 1);
        assertThat(testFeetype.getFeenum()).isEqualTo(UPDATED_FEENUM);
        assertThat(testFeetype.getFeename()).isEqualTo(UPDATED_FEENAME);
        assertThat(testFeetype.getPrice()).isEqualByComparingTo(UPDATED_PRICE);
        assertThat(testFeetype.getSign()).isEqualTo(UPDATED_SIGN);
        assertThat(testFeetype.getBeizhu()).isEqualTo(UPDATED_BEIZHU);
        assertThat(testFeetype.getPym()).isEqualTo(UPDATED_PYM);
        assertThat(testFeetype.getSalespotn()).isEqualTo(UPDATED_SALESPOTN);
        assertThat(testFeetype.getDepot()).isEqualTo(UPDATED_DEPOT);
        assertThat(testFeetype.getCbsign()).isEqualTo(UPDATED_CBSIGN);
        assertThat(testFeetype.getOrdersign()).isEqualTo(UPDATED_ORDERSIGN);
        assertThat(testFeetype.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testFeetype.getIsnew()).isEqualTo(UPDATED_ISNEW);
        assertThat(testFeetype.getYgj()).isEqualByComparingTo(UPDATED_YGJ);
        assertThat(testFeetype.getAutosign()).isEqualTo(UPDATED_AUTOSIGN);
        assertThat(testFeetype.getJj()).isEqualByComparingTo(UPDATED_JJ);
        assertThat(testFeetype.getHyj()).isEqualByComparingTo(UPDATED_HYJ);
        assertThat(testFeetype.getDqkc()).isEqualByComparingTo(UPDATED_DQKC);
    }

    @Test
    @Transactional
    void patchNonExistingFeetype() throws Exception {
        int databaseSizeBeforeUpdate = feetypeRepository.findAll().size();
        feetype.setId(count.incrementAndGet());

        // Create the Feetype
        FeetypeDTO feetypeDTO = feetypeMapper.toDto(feetype);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeetypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, feetypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feetypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feetype in the database
        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Feetype in Elasticsearch
        verify(mockFeetypeSearchRepository, times(0)).save(feetype);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFeetype() throws Exception {
        int databaseSizeBeforeUpdate = feetypeRepository.findAll().size();
        feetype.setId(count.incrementAndGet());

        // Create the Feetype
        FeetypeDTO feetypeDTO = feetypeMapper.toDto(feetype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeetypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(feetypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Feetype in the database
        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Feetype in Elasticsearch
        verify(mockFeetypeSearchRepository, times(0)).save(feetype);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFeetype() throws Exception {
        int databaseSizeBeforeUpdate = feetypeRepository.findAll().size();
        feetype.setId(count.incrementAndGet());

        // Create the Feetype
        FeetypeDTO feetypeDTO = feetypeMapper.toDto(feetype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFeetypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(feetypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Feetype in the database
        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Feetype in Elasticsearch
        verify(mockFeetypeSearchRepository, times(0)).save(feetype);
    }

    @Test
    @Transactional
    void deleteFeetype() throws Exception {
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);

        int databaseSizeBeforeDelete = feetypeRepository.findAll().size();

        // Delete the feetype
        restFeetypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, feetype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Feetype> feetypeList = feetypeRepository.findAll();
        assertThat(feetypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Feetype in Elasticsearch
        verify(mockFeetypeSearchRepository, times(1)).deleteById(feetype.getId());
    }

    @Test
    @Transactional
    void searchFeetype() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        feetypeRepository.saveAndFlush(feetype);
        when(mockFeetypeSearchRepository.search(queryStringQuery("id:" + feetype.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(feetype), PageRequest.of(0, 1), 1));

        // Search the feetype
        restFeetypeMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + feetype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feetype.getId().intValue())))
            .andExpect(jsonPath("$.[*].feenum").value(hasItem(DEFAULT_FEENUM.intValue())))
            .andExpect(jsonPath("$.[*].feename").value(hasItem(DEFAULT_FEENAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(sameNumber(DEFAULT_PRICE))))
            .andExpect(jsonPath("$.[*].sign").value(hasItem(DEFAULT_SIGN.intValue())))
            .andExpect(jsonPath("$.[*].beizhu").value(hasItem(DEFAULT_BEIZHU)))
            .andExpect(jsonPath("$.[*].pym").value(hasItem(DEFAULT_PYM)))
            .andExpect(jsonPath("$.[*].salespotn").value(hasItem(DEFAULT_SALESPOTN.intValue())))
            .andExpect(jsonPath("$.[*].depot").value(hasItem(DEFAULT_DEPOT)))
            .andExpect(jsonPath("$.[*].cbsign").value(hasItem(DEFAULT_CBSIGN.intValue())))
            .andExpect(jsonPath("$.[*].ordersign").value(hasItem(DEFAULT_ORDERSIGN.intValue())))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())))
            .andExpect(jsonPath("$.[*].ygj").value(hasItem(sameNumber(DEFAULT_YGJ))))
            .andExpect(jsonPath("$.[*].autosign").value(hasItem(DEFAULT_AUTOSIGN)))
            .andExpect(jsonPath("$.[*].jj").value(hasItem(sameNumber(DEFAULT_JJ))))
            .andExpect(jsonPath("$.[*].hyj").value(hasItem(sameNumber(DEFAULT_HYJ))))
            .andExpect(jsonPath("$.[*].dqkc").value(hasItem(sameNumber(DEFAULT_DQKC))));
    }
}
