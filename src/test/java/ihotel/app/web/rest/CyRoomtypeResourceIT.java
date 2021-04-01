package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.CyRoomtype;
import ihotel.app.repository.CyRoomtypeRepository;
import ihotel.app.repository.search.CyRoomtypeSearchRepository;
import ihotel.app.service.criteria.CyRoomtypeCriteria;
import ihotel.app.service.dto.CyRoomtypeDTO;
import ihotel.app.service.mapper.CyRoomtypeMapper;
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
 * Integration tests for the {@link CyRoomtypeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CyRoomtypeResourceIT {

    private static final String DEFAULT_RTDM = "AAAAAAAAAA";
    private static final String UPDATED_RTDM = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_MINC = new BigDecimal(1);
    private static final BigDecimal UPDATED_MINC = new BigDecimal(2);
    private static final BigDecimal SMALLER_MINC = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SERVICERATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SERVICERATE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SERVICERATE = new BigDecimal(1 - 1);

    private static final String DEFAULT_PRINTER = "AAAAAAAAAA";
    private static final String UPDATED_PRINTER = "BBBBBBBBBB";

    private static final Long DEFAULT_PRINTNUM = 1L;
    private static final Long UPDATED_PRINTNUM = 2L;
    private static final Long SMALLER_PRINTNUM = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cy-roomtypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/cy-roomtypes";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CyRoomtypeRepository cyRoomtypeRepository;

    @Autowired
    private CyRoomtypeMapper cyRoomtypeMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CyRoomtypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private CyRoomtypeSearchRepository mockCyRoomtypeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCyRoomtypeMockMvc;

    private CyRoomtype cyRoomtype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CyRoomtype createEntity(EntityManager em) {
        CyRoomtype cyRoomtype = new CyRoomtype()
            .rtdm(DEFAULT_RTDM)
            .minc(DEFAULT_MINC)
            .servicerate(DEFAULT_SERVICERATE)
            .printer(DEFAULT_PRINTER)
            .printnum(DEFAULT_PRINTNUM);
        return cyRoomtype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CyRoomtype createUpdatedEntity(EntityManager em) {
        CyRoomtype cyRoomtype = new CyRoomtype()
            .rtdm(UPDATED_RTDM)
            .minc(UPDATED_MINC)
            .servicerate(UPDATED_SERVICERATE)
            .printer(UPDATED_PRINTER)
            .printnum(UPDATED_PRINTNUM);
        return cyRoomtype;
    }

    @BeforeEach
    public void initTest() {
        cyRoomtype = createEntity(em);
    }

    @Test
    @Transactional
    void createCyRoomtype() throws Exception {
        int databaseSizeBeforeCreate = cyRoomtypeRepository.findAll().size();
        // Create the CyRoomtype
        CyRoomtypeDTO cyRoomtypeDTO = cyRoomtypeMapper.toDto(cyRoomtype);
        restCyRoomtypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cyRoomtypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CyRoomtype in the database
        List<CyRoomtype> cyRoomtypeList = cyRoomtypeRepository.findAll();
        assertThat(cyRoomtypeList).hasSize(databaseSizeBeforeCreate + 1);
        CyRoomtype testCyRoomtype = cyRoomtypeList.get(cyRoomtypeList.size() - 1);
        assertThat(testCyRoomtype.getRtdm()).isEqualTo(DEFAULT_RTDM);
        assertThat(testCyRoomtype.getMinc()).isEqualByComparingTo(DEFAULT_MINC);
        assertThat(testCyRoomtype.getServicerate()).isEqualByComparingTo(DEFAULT_SERVICERATE);
        assertThat(testCyRoomtype.getPrinter()).isEqualTo(DEFAULT_PRINTER);
        assertThat(testCyRoomtype.getPrintnum()).isEqualTo(DEFAULT_PRINTNUM);

        // Validate the CyRoomtype in Elasticsearch
        verify(mockCyRoomtypeSearchRepository, times(1)).save(testCyRoomtype);
    }

    @Test
    @Transactional
    void createCyRoomtypeWithExistingId() throws Exception {
        // Create the CyRoomtype with an existing ID
        cyRoomtype.setId(1L);
        CyRoomtypeDTO cyRoomtypeDTO = cyRoomtypeMapper.toDto(cyRoomtype);

        int databaseSizeBeforeCreate = cyRoomtypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCyRoomtypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cyRoomtypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CyRoomtype in the database
        List<CyRoomtype> cyRoomtypeList = cyRoomtypeRepository.findAll();
        assertThat(cyRoomtypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the CyRoomtype in Elasticsearch
        verify(mockCyRoomtypeSearchRepository, times(0)).save(cyRoomtype);
    }

    @Test
    @Transactional
    void checkRtdmIsRequired() throws Exception {
        int databaseSizeBeforeTest = cyRoomtypeRepository.findAll().size();
        // set the field null
        cyRoomtype.setRtdm(null);

        // Create the CyRoomtype, which fails.
        CyRoomtypeDTO cyRoomtypeDTO = cyRoomtypeMapper.toDto(cyRoomtype);

        restCyRoomtypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cyRoomtypeDTO)))
            .andExpect(status().isBadRequest());

        List<CyRoomtype> cyRoomtypeList = cyRoomtypeRepository.findAll();
        assertThat(cyRoomtypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCyRoomtypes() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList
        restCyRoomtypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cyRoomtype.getId().intValue())))
            .andExpect(jsonPath("$.[*].rtdm").value(hasItem(DEFAULT_RTDM)))
            .andExpect(jsonPath("$.[*].minc").value(hasItem(sameNumber(DEFAULT_MINC))))
            .andExpect(jsonPath("$.[*].servicerate").value(hasItem(sameNumber(DEFAULT_SERVICERATE))))
            .andExpect(jsonPath("$.[*].printer").value(hasItem(DEFAULT_PRINTER)))
            .andExpect(jsonPath("$.[*].printnum").value(hasItem(DEFAULT_PRINTNUM.intValue())));
    }

    @Test
    @Transactional
    void getCyRoomtype() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get the cyRoomtype
        restCyRoomtypeMockMvc
            .perform(get(ENTITY_API_URL_ID, cyRoomtype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cyRoomtype.getId().intValue()))
            .andExpect(jsonPath("$.rtdm").value(DEFAULT_RTDM))
            .andExpect(jsonPath("$.minc").value(sameNumber(DEFAULT_MINC)))
            .andExpect(jsonPath("$.servicerate").value(sameNumber(DEFAULT_SERVICERATE)))
            .andExpect(jsonPath("$.printer").value(DEFAULT_PRINTER))
            .andExpect(jsonPath("$.printnum").value(DEFAULT_PRINTNUM.intValue()));
    }

    @Test
    @Transactional
    void getCyRoomtypesByIdFiltering() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        Long id = cyRoomtype.getId();

        defaultCyRoomtypeShouldBeFound("id.equals=" + id);
        defaultCyRoomtypeShouldNotBeFound("id.notEquals=" + id);

        defaultCyRoomtypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCyRoomtypeShouldNotBeFound("id.greaterThan=" + id);

        defaultCyRoomtypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCyRoomtypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByRtdmIsEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where rtdm equals to DEFAULT_RTDM
        defaultCyRoomtypeShouldBeFound("rtdm.equals=" + DEFAULT_RTDM);

        // Get all the cyRoomtypeList where rtdm equals to UPDATED_RTDM
        defaultCyRoomtypeShouldNotBeFound("rtdm.equals=" + UPDATED_RTDM);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByRtdmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where rtdm not equals to DEFAULT_RTDM
        defaultCyRoomtypeShouldNotBeFound("rtdm.notEquals=" + DEFAULT_RTDM);

        // Get all the cyRoomtypeList where rtdm not equals to UPDATED_RTDM
        defaultCyRoomtypeShouldBeFound("rtdm.notEquals=" + UPDATED_RTDM);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByRtdmIsInShouldWork() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where rtdm in DEFAULT_RTDM or UPDATED_RTDM
        defaultCyRoomtypeShouldBeFound("rtdm.in=" + DEFAULT_RTDM + "," + UPDATED_RTDM);

        // Get all the cyRoomtypeList where rtdm equals to UPDATED_RTDM
        defaultCyRoomtypeShouldNotBeFound("rtdm.in=" + UPDATED_RTDM);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByRtdmIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where rtdm is not null
        defaultCyRoomtypeShouldBeFound("rtdm.specified=true");

        // Get all the cyRoomtypeList where rtdm is null
        defaultCyRoomtypeShouldNotBeFound("rtdm.specified=false");
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByRtdmContainsSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where rtdm contains DEFAULT_RTDM
        defaultCyRoomtypeShouldBeFound("rtdm.contains=" + DEFAULT_RTDM);

        // Get all the cyRoomtypeList where rtdm contains UPDATED_RTDM
        defaultCyRoomtypeShouldNotBeFound("rtdm.contains=" + UPDATED_RTDM);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByRtdmNotContainsSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where rtdm does not contain DEFAULT_RTDM
        defaultCyRoomtypeShouldNotBeFound("rtdm.doesNotContain=" + DEFAULT_RTDM);

        // Get all the cyRoomtypeList where rtdm does not contain UPDATED_RTDM
        defaultCyRoomtypeShouldBeFound("rtdm.doesNotContain=" + UPDATED_RTDM);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByMincIsEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where minc equals to DEFAULT_MINC
        defaultCyRoomtypeShouldBeFound("minc.equals=" + DEFAULT_MINC);

        // Get all the cyRoomtypeList where minc equals to UPDATED_MINC
        defaultCyRoomtypeShouldNotBeFound("minc.equals=" + UPDATED_MINC);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByMincIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where minc not equals to DEFAULT_MINC
        defaultCyRoomtypeShouldNotBeFound("minc.notEquals=" + DEFAULT_MINC);

        // Get all the cyRoomtypeList where minc not equals to UPDATED_MINC
        defaultCyRoomtypeShouldBeFound("minc.notEquals=" + UPDATED_MINC);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByMincIsInShouldWork() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where minc in DEFAULT_MINC or UPDATED_MINC
        defaultCyRoomtypeShouldBeFound("minc.in=" + DEFAULT_MINC + "," + UPDATED_MINC);

        // Get all the cyRoomtypeList where minc equals to UPDATED_MINC
        defaultCyRoomtypeShouldNotBeFound("minc.in=" + UPDATED_MINC);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByMincIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where minc is not null
        defaultCyRoomtypeShouldBeFound("minc.specified=true");

        // Get all the cyRoomtypeList where minc is null
        defaultCyRoomtypeShouldNotBeFound("minc.specified=false");
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByMincIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where minc is greater than or equal to DEFAULT_MINC
        defaultCyRoomtypeShouldBeFound("minc.greaterThanOrEqual=" + DEFAULT_MINC);

        // Get all the cyRoomtypeList where minc is greater than or equal to UPDATED_MINC
        defaultCyRoomtypeShouldNotBeFound("minc.greaterThanOrEqual=" + UPDATED_MINC);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByMincIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where minc is less than or equal to DEFAULT_MINC
        defaultCyRoomtypeShouldBeFound("minc.lessThanOrEqual=" + DEFAULT_MINC);

        // Get all the cyRoomtypeList where minc is less than or equal to SMALLER_MINC
        defaultCyRoomtypeShouldNotBeFound("minc.lessThanOrEqual=" + SMALLER_MINC);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByMincIsLessThanSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where minc is less than DEFAULT_MINC
        defaultCyRoomtypeShouldNotBeFound("minc.lessThan=" + DEFAULT_MINC);

        // Get all the cyRoomtypeList where minc is less than UPDATED_MINC
        defaultCyRoomtypeShouldBeFound("minc.lessThan=" + UPDATED_MINC);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByMincIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where minc is greater than DEFAULT_MINC
        defaultCyRoomtypeShouldNotBeFound("minc.greaterThan=" + DEFAULT_MINC);

        // Get all the cyRoomtypeList where minc is greater than SMALLER_MINC
        defaultCyRoomtypeShouldBeFound("minc.greaterThan=" + SMALLER_MINC);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByServicerateIsEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where servicerate equals to DEFAULT_SERVICERATE
        defaultCyRoomtypeShouldBeFound("servicerate.equals=" + DEFAULT_SERVICERATE);

        // Get all the cyRoomtypeList where servicerate equals to UPDATED_SERVICERATE
        defaultCyRoomtypeShouldNotBeFound("servicerate.equals=" + UPDATED_SERVICERATE);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByServicerateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where servicerate not equals to DEFAULT_SERVICERATE
        defaultCyRoomtypeShouldNotBeFound("servicerate.notEquals=" + DEFAULT_SERVICERATE);

        // Get all the cyRoomtypeList where servicerate not equals to UPDATED_SERVICERATE
        defaultCyRoomtypeShouldBeFound("servicerate.notEquals=" + UPDATED_SERVICERATE);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByServicerateIsInShouldWork() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where servicerate in DEFAULT_SERVICERATE or UPDATED_SERVICERATE
        defaultCyRoomtypeShouldBeFound("servicerate.in=" + DEFAULT_SERVICERATE + "," + UPDATED_SERVICERATE);

        // Get all the cyRoomtypeList where servicerate equals to UPDATED_SERVICERATE
        defaultCyRoomtypeShouldNotBeFound("servicerate.in=" + UPDATED_SERVICERATE);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByServicerateIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where servicerate is not null
        defaultCyRoomtypeShouldBeFound("servicerate.specified=true");

        // Get all the cyRoomtypeList where servicerate is null
        defaultCyRoomtypeShouldNotBeFound("servicerate.specified=false");
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByServicerateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where servicerate is greater than or equal to DEFAULT_SERVICERATE
        defaultCyRoomtypeShouldBeFound("servicerate.greaterThanOrEqual=" + DEFAULT_SERVICERATE);

        // Get all the cyRoomtypeList where servicerate is greater than or equal to UPDATED_SERVICERATE
        defaultCyRoomtypeShouldNotBeFound("servicerate.greaterThanOrEqual=" + UPDATED_SERVICERATE);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByServicerateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where servicerate is less than or equal to DEFAULT_SERVICERATE
        defaultCyRoomtypeShouldBeFound("servicerate.lessThanOrEqual=" + DEFAULT_SERVICERATE);

        // Get all the cyRoomtypeList where servicerate is less than or equal to SMALLER_SERVICERATE
        defaultCyRoomtypeShouldNotBeFound("servicerate.lessThanOrEqual=" + SMALLER_SERVICERATE);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByServicerateIsLessThanSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where servicerate is less than DEFAULT_SERVICERATE
        defaultCyRoomtypeShouldNotBeFound("servicerate.lessThan=" + DEFAULT_SERVICERATE);

        // Get all the cyRoomtypeList where servicerate is less than UPDATED_SERVICERATE
        defaultCyRoomtypeShouldBeFound("servicerate.lessThan=" + UPDATED_SERVICERATE);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByServicerateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where servicerate is greater than DEFAULT_SERVICERATE
        defaultCyRoomtypeShouldNotBeFound("servicerate.greaterThan=" + DEFAULT_SERVICERATE);

        // Get all the cyRoomtypeList where servicerate is greater than SMALLER_SERVICERATE
        defaultCyRoomtypeShouldBeFound("servicerate.greaterThan=" + SMALLER_SERVICERATE);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByPrinterIsEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where printer equals to DEFAULT_PRINTER
        defaultCyRoomtypeShouldBeFound("printer.equals=" + DEFAULT_PRINTER);

        // Get all the cyRoomtypeList where printer equals to UPDATED_PRINTER
        defaultCyRoomtypeShouldNotBeFound("printer.equals=" + UPDATED_PRINTER);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByPrinterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where printer not equals to DEFAULT_PRINTER
        defaultCyRoomtypeShouldNotBeFound("printer.notEquals=" + DEFAULT_PRINTER);

        // Get all the cyRoomtypeList where printer not equals to UPDATED_PRINTER
        defaultCyRoomtypeShouldBeFound("printer.notEquals=" + UPDATED_PRINTER);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByPrinterIsInShouldWork() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where printer in DEFAULT_PRINTER or UPDATED_PRINTER
        defaultCyRoomtypeShouldBeFound("printer.in=" + DEFAULT_PRINTER + "," + UPDATED_PRINTER);

        // Get all the cyRoomtypeList where printer equals to UPDATED_PRINTER
        defaultCyRoomtypeShouldNotBeFound("printer.in=" + UPDATED_PRINTER);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByPrinterIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where printer is not null
        defaultCyRoomtypeShouldBeFound("printer.specified=true");

        // Get all the cyRoomtypeList where printer is null
        defaultCyRoomtypeShouldNotBeFound("printer.specified=false");
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByPrinterContainsSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where printer contains DEFAULT_PRINTER
        defaultCyRoomtypeShouldBeFound("printer.contains=" + DEFAULT_PRINTER);

        // Get all the cyRoomtypeList where printer contains UPDATED_PRINTER
        defaultCyRoomtypeShouldNotBeFound("printer.contains=" + UPDATED_PRINTER);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByPrinterNotContainsSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where printer does not contain DEFAULT_PRINTER
        defaultCyRoomtypeShouldNotBeFound("printer.doesNotContain=" + DEFAULT_PRINTER);

        // Get all the cyRoomtypeList where printer does not contain UPDATED_PRINTER
        defaultCyRoomtypeShouldBeFound("printer.doesNotContain=" + UPDATED_PRINTER);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByPrintnumIsEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where printnum equals to DEFAULT_PRINTNUM
        defaultCyRoomtypeShouldBeFound("printnum.equals=" + DEFAULT_PRINTNUM);

        // Get all the cyRoomtypeList where printnum equals to UPDATED_PRINTNUM
        defaultCyRoomtypeShouldNotBeFound("printnum.equals=" + UPDATED_PRINTNUM);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByPrintnumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where printnum not equals to DEFAULT_PRINTNUM
        defaultCyRoomtypeShouldNotBeFound("printnum.notEquals=" + DEFAULT_PRINTNUM);

        // Get all the cyRoomtypeList where printnum not equals to UPDATED_PRINTNUM
        defaultCyRoomtypeShouldBeFound("printnum.notEquals=" + UPDATED_PRINTNUM);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByPrintnumIsInShouldWork() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where printnum in DEFAULT_PRINTNUM or UPDATED_PRINTNUM
        defaultCyRoomtypeShouldBeFound("printnum.in=" + DEFAULT_PRINTNUM + "," + UPDATED_PRINTNUM);

        // Get all the cyRoomtypeList where printnum equals to UPDATED_PRINTNUM
        defaultCyRoomtypeShouldNotBeFound("printnum.in=" + UPDATED_PRINTNUM);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByPrintnumIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where printnum is not null
        defaultCyRoomtypeShouldBeFound("printnum.specified=true");

        // Get all the cyRoomtypeList where printnum is null
        defaultCyRoomtypeShouldNotBeFound("printnum.specified=false");
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByPrintnumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where printnum is greater than or equal to DEFAULT_PRINTNUM
        defaultCyRoomtypeShouldBeFound("printnum.greaterThanOrEqual=" + DEFAULT_PRINTNUM);

        // Get all the cyRoomtypeList where printnum is greater than or equal to UPDATED_PRINTNUM
        defaultCyRoomtypeShouldNotBeFound("printnum.greaterThanOrEqual=" + UPDATED_PRINTNUM);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByPrintnumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where printnum is less than or equal to DEFAULT_PRINTNUM
        defaultCyRoomtypeShouldBeFound("printnum.lessThanOrEqual=" + DEFAULT_PRINTNUM);

        // Get all the cyRoomtypeList where printnum is less than or equal to SMALLER_PRINTNUM
        defaultCyRoomtypeShouldNotBeFound("printnum.lessThanOrEqual=" + SMALLER_PRINTNUM);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByPrintnumIsLessThanSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where printnum is less than DEFAULT_PRINTNUM
        defaultCyRoomtypeShouldNotBeFound("printnum.lessThan=" + DEFAULT_PRINTNUM);

        // Get all the cyRoomtypeList where printnum is less than UPDATED_PRINTNUM
        defaultCyRoomtypeShouldBeFound("printnum.lessThan=" + UPDATED_PRINTNUM);
    }

    @Test
    @Transactional
    void getAllCyRoomtypesByPrintnumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        // Get all the cyRoomtypeList where printnum is greater than DEFAULT_PRINTNUM
        defaultCyRoomtypeShouldNotBeFound("printnum.greaterThan=" + DEFAULT_PRINTNUM);

        // Get all the cyRoomtypeList where printnum is greater than SMALLER_PRINTNUM
        defaultCyRoomtypeShouldBeFound("printnum.greaterThan=" + SMALLER_PRINTNUM);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCyRoomtypeShouldBeFound(String filter) throws Exception {
        restCyRoomtypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cyRoomtype.getId().intValue())))
            .andExpect(jsonPath("$.[*].rtdm").value(hasItem(DEFAULT_RTDM)))
            .andExpect(jsonPath("$.[*].minc").value(hasItem(sameNumber(DEFAULT_MINC))))
            .andExpect(jsonPath("$.[*].servicerate").value(hasItem(sameNumber(DEFAULT_SERVICERATE))))
            .andExpect(jsonPath("$.[*].printer").value(hasItem(DEFAULT_PRINTER)))
            .andExpect(jsonPath("$.[*].printnum").value(hasItem(DEFAULT_PRINTNUM.intValue())));

        // Check, that the count call also returns 1
        restCyRoomtypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCyRoomtypeShouldNotBeFound(String filter) throws Exception {
        restCyRoomtypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCyRoomtypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCyRoomtype() throws Exception {
        // Get the cyRoomtype
        restCyRoomtypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCyRoomtype() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        int databaseSizeBeforeUpdate = cyRoomtypeRepository.findAll().size();

        // Update the cyRoomtype
        CyRoomtype updatedCyRoomtype = cyRoomtypeRepository.findById(cyRoomtype.getId()).get();
        // Disconnect from session so that the updates on updatedCyRoomtype are not directly saved in db
        em.detach(updatedCyRoomtype);
        updatedCyRoomtype
            .rtdm(UPDATED_RTDM)
            .minc(UPDATED_MINC)
            .servicerate(UPDATED_SERVICERATE)
            .printer(UPDATED_PRINTER)
            .printnum(UPDATED_PRINTNUM);
        CyRoomtypeDTO cyRoomtypeDTO = cyRoomtypeMapper.toDto(updatedCyRoomtype);

        restCyRoomtypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cyRoomtypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cyRoomtypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CyRoomtype in the database
        List<CyRoomtype> cyRoomtypeList = cyRoomtypeRepository.findAll();
        assertThat(cyRoomtypeList).hasSize(databaseSizeBeforeUpdate);
        CyRoomtype testCyRoomtype = cyRoomtypeList.get(cyRoomtypeList.size() - 1);
        assertThat(testCyRoomtype.getRtdm()).isEqualTo(UPDATED_RTDM);
        assertThat(testCyRoomtype.getMinc()).isEqualTo(UPDATED_MINC);
        assertThat(testCyRoomtype.getServicerate()).isEqualTo(UPDATED_SERVICERATE);
        assertThat(testCyRoomtype.getPrinter()).isEqualTo(UPDATED_PRINTER);
        assertThat(testCyRoomtype.getPrintnum()).isEqualTo(UPDATED_PRINTNUM);

        // Validate the CyRoomtype in Elasticsearch
        verify(mockCyRoomtypeSearchRepository).save(testCyRoomtype);
    }

    @Test
    @Transactional
    void putNonExistingCyRoomtype() throws Exception {
        int databaseSizeBeforeUpdate = cyRoomtypeRepository.findAll().size();
        cyRoomtype.setId(count.incrementAndGet());

        // Create the CyRoomtype
        CyRoomtypeDTO cyRoomtypeDTO = cyRoomtypeMapper.toDto(cyRoomtype);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCyRoomtypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cyRoomtypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cyRoomtypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CyRoomtype in the database
        List<CyRoomtype> cyRoomtypeList = cyRoomtypeRepository.findAll();
        assertThat(cyRoomtypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CyRoomtype in Elasticsearch
        verify(mockCyRoomtypeSearchRepository, times(0)).save(cyRoomtype);
    }

    @Test
    @Transactional
    void putWithIdMismatchCyRoomtype() throws Exception {
        int databaseSizeBeforeUpdate = cyRoomtypeRepository.findAll().size();
        cyRoomtype.setId(count.incrementAndGet());

        // Create the CyRoomtype
        CyRoomtypeDTO cyRoomtypeDTO = cyRoomtypeMapper.toDto(cyRoomtype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCyRoomtypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cyRoomtypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CyRoomtype in the database
        List<CyRoomtype> cyRoomtypeList = cyRoomtypeRepository.findAll();
        assertThat(cyRoomtypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CyRoomtype in Elasticsearch
        verify(mockCyRoomtypeSearchRepository, times(0)).save(cyRoomtype);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCyRoomtype() throws Exception {
        int databaseSizeBeforeUpdate = cyRoomtypeRepository.findAll().size();
        cyRoomtype.setId(count.incrementAndGet());

        // Create the CyRoomtype
        CyRoomtypeDTO cyRoomtypeDTO = cyRoomtypeMapper.toDto(cyRoomtype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCyRoomtypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cyRoomtypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CyRoomtype in the database
        List<CyRoomtype> cyRoomtypeList = cyRoomtypeRepository.findAll();
        assertThat(cyRoomtypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CyRoomtype in Elasticsearch
        verify(mockCyRoomtypeSearchRepository, times(0)).save(cyRoomtype);
    }

    @Test
    @Transactional
    void partialUpdateCyRoomtypeWithPatch() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        int databaseSizeBeforeUpdate = cyRoomtypeRepository.findAll().size();

        // Update the cyRoomtype using partial update
        CyRoomtype partialUpdatedCyRoomtype = new CyRoomtype();
        partialUpdatedCyRoomtype.setId(cyRoomtype.getId());

        partialUpdatedCyRoomtype.minc(UPDATED_MINC).printer(UPDATED_PRINTER).printnum(UPDATED_PRINTNUM);

        restCyRoomtypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCyRoomtype.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCyRoomtype))
            )
            .andExpect(status().isOk());

        // Validate the CyRoomtype in the database
        List<CyRoomtype> cyRoomtypeList = cyRoomtypeRepository.findAll();
        assertThat(cyRoomtypeList).hasSize(databaseSizeBeforeUpdate);
        CyRoomtype testCyRoomtype = cyRoomtypeList.get(cyRoomtypeList.size() - 1);
        assertThat(testCyRoomtype.getRtdm()).isEqualTo(DEFAULT_RTDM);
        assertThat(testCyRoomtype.getMinc()).isEqualByComparingTo(UPDATED_MINC);
        assertThat(testCyRoomtype.getServicerate()).isEqualByComparingTo(DEFAULT_SERVICERATE);
        assertThat(testCyRoomtype.getPrinter()).isEqualTo(UPDATED_PRINTER);
        assertThat(testCyRoomtype.getPrintnum()).isEqualTo(UPDATED_PRINTNUM);
    }

    @Test
    @Transactional
    void fullUpdateCyRoomtypeWithPatch() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        int databaseSizeBeforeUpdate = cyRoomtypeRepository.findAll().size();

        // Update the cyRoomtype using partial update
        CyRoomtype partialUpdatedCyRoomtype = new CyRoomtype();
        partialUpdatedCyRoomtype.setId(cyRoomtype.getId());

        partialUpdatedCyRoomtype
            .rtdm(UPDATED_RTDM)
            .minc(UPDATED_MINC)
            .servicerate(UPDATED_SERVICERATE)
            .printer(UPDATED_PRINTER)
            .printnum(UPDATED_PRINTNUM);

        restCyRoomtypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCyRoomtype.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCyRoomtype))
            )
            .andExpect(status().isOk());

        // Validate the CyRoomtype in the database
        List<CyRoomtype> cyRoomtypeList = cyRoomtypeRepository.findAll();
        assertThat(cyRoomtypeList).hasSize(databaseSizeBeforeUpdate);
        CyRoomtype testCyRoomtype = cyRoomtypeList.get(cyRoomtypeList.size() - 1);
        assertThat(testCyRoomtype.getRtdm()).isEqualTo(UPDATED_RTDM);
        assertThat(testCyRoomtype.getMinc()).isEqualByComparingTo(UPDATED_MINC);
        assertThat(testCyRoomtype.getServicerate()).isEqualByComparingTo(UPDATED_SERVICERATE);
        assertThat(testCyRoomtype.getPrinter()).isEqualTo(UPDATED_PRINTER);
        assertThat(testCyRoomtype.getPrintnum()).isEqualTo(UPDATED_PRINTNUM);
    }

    @Test
    @Transactional
    void patchNonExistingCyRoomtype() throws Exception {
        int databaseSizeBeforeUpdate = cyRoomtypeRepository.findAll().size();
        cyRoomtype.setId(count.incrementAndGet());

        // Create the CyRoomtype
        CyRoomtypeDTO cyRoomtypeDTO = cyRoomtypeMapper.toDto(cyRoomtype);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCyRoomtypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cyRoomtypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cyRoomtypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CyRoomtype in the database
        List<CyRoomtype> cyRoomtypeList = cyRoomtypeRepository.findAll();
        assertThat(cyRoomtypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CyRoomtype in Elasticsearch
        verify(mockCyRoomtypeSearchRepository, times(0)).save(cyRoomtype);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCyRoomtype() throws Exception {
        int databaseSizeBeforeUpdate = cyRoomtypeRepository.findAll().size();
        cyRoomtype.setId(count.incrementAndGet());

        // Create the CyRoomtype
        CyRoomtypeDTO cyRoomtypeDTO = cyRoomtypeMapper.toDto(cyRoomtype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCyRoomtypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cyRoomtypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CyRoomtype in the database
        List<CyRoomtype> cyRoomtypeList = cyRoomtypeRepository.findAll();
        assertThat(cyRoomtypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CyRoomtype in Elasticsearch
        verify(mockCyRoomtypeSearchRepository, times(0)).save(cyRoomtype);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCyRoomtype() throws Exception {
        int databaseSizeBeforeUpdate = cyRoomtypeRepository.findAll().size();
        cyRoomtype.setId(count.incrementAndGet());

        // Create the CyRoomtype
        CyRoomtypeDTO cyRoomtypeDTO = cyRoomtypeMapper.toDto(cyRoomtype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCyRoomtypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cyRoomtypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CyRoomtype in the database
        List<CyRoomtype> cyRoomtypeList = cyRoomtypeRepository.findAll();
        assertThat(cyRoomtypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CyRoomtype in Elasticsearch
        verify(mockCyRoomtypeSearchRepository, times(0)).save(cyRoomtype);
    }

    @Test
    @Transactional
    void deleteCyRoomtype() throws Exception {
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);

        int databaseSizeBeforeDelete = cyRoomtypeRepository.findAll().size();

        // Delete the cyRoomtype
        restCyRoomtypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, cyRoomtype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CyRoomtype> cyRoomtypeList = cyRoomtypeRepository.findAll();
        assertThat(cyRoomtypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CyRoomtype in Elasticsearch
        verify(mockCyRoomtypeSearchRepository, times(1)).deleteById(cyRoomtype.getId());
    }

    @Test
    @Transactional
    void searchCyRoomtype() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        cyRoomtypeRepository.saveAndFlush(cyRoomtype);
        when(mockCyRoomtypeSearchRepository.search(queryStringQuery("id:" + cyRoomtype.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cyRoomtype), PageRequest.of(0, 1), 1));

        // Search the cyRoomtype
        restCyRoomtypeMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + cyRoomtype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cyRoomtype.getId().intValue())))
            .andExpect(jsonPath("$.[*].rtdm").value(hasItem(DEFAULT_RTDM)))
            .andExpect(jsonPath("$.[*].minc").value(hasItem(sameNumber(DEFAULT_MINC))))
            .andExpect(jsonPath("$.[*].servicerate").value(hasItem(sameNumber(DEFAULT_SERVICERATE))))
            .andExpect(jsonPath("$.[*].printer").value(hasItem(DEFAULT_PRINTER)))
            .andExpect(jsonPath("$.[*].printnum").value(hasItem(DEFAULT_PRINTNUM.intValue())));
    }
}
