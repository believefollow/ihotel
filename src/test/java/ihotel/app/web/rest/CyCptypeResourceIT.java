package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.CyCptype;
import ihotel.app.repository.CyCptypeRepository;
import ihotel.app.repository.search.CyCptypeSearchRepository;
import ihotel.app.service.criteria.CyCptypeCriteria;
import ihotel.app.service.dto.CyCptypeDTO;
import ihotel.app.service.mapper.CyCptypeMapper;
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
 * Integration tests for the {@link CyCptypeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CyCptypeResourceIT {

    private static final String DEFAULT_CPTDM = "AAAA";
    private static final String UPDATED_CPTDM = "BBBB";

    private static final String DEFAULT_CPTNAME = "AAAAAAAAAA";
    private static final String UPDATED_CPTNAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRINTSIGN = false;
    private static final Boolean UPDATED_PRINTSIGN = true;

    private static final String DEFAULT_PRINTER = "AAAAAAAAAA";
    private static final String UPDATED_PRINTER = "BBBBBBBBBB";

    private static final Long DEFAULT_PRINTNUM = 1L;
    private static final Long UPDATED_PRINTNUM = 2L;
    private static final Long SMALLER_PRINTNUM = 1L - 1L;

    private static final Long DEFAULT_PRINTCUT = 1L;
    private static final Long UPDATED_PRINTCUT = 2L;
    private static final Long SMALLER_PRINTCUT = 1L - 1L;

    private static final Boolean DEFAULT_SYSSIGN = false;
    private static final Boolean UPDATED_SYSSIGN = true;

    private static final String DEFAULT_TYPESIGN = "AAAAAAAAAA";
    private static final String UPDATED_TYPESIGN = "BBBBBBBBBB";

    private static final String DEFAULT_QY = "AAAAAAAAAA";
    private static final String UPDATED_QY = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/cy-cptypes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/cy-cptypes";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CyCptypeRepository cyCptypeRepository;

    @Autowired
    private CyCptypeMapper cyCptypeMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CyCptypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private CyCptypeSearchRepository mockCyCptypeSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCyCptypeMockMvc;

    private CyCptype cyCptype;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CyCptype createEntity(EntityManager em) {
        CyCptype cyCptype = new CyCptype()
            .cptdm(DEFAULT_CPTDM)
            .cptname(DEFAULT_CPTNAME)
            .printsign(DEFAULT_PRINTSIGN)
            .printer(DEFAULT_PRINTER)
            .printnum(DEFAULT_PRINTNUM)
            .printcut(DEFAULT_PRINTCUT)
            .syssign(DEFAULT_SYSSIGN)
            .typesign(DEFAULT_TYPESIGN)
            .qy(DEFAULT_QY);
        return cyCptype;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CyCptype createUpdatedEntity(EntityManager em) {
        CyCptype cyCptype = new CyCptype()
            .cptdm(UPDATED_CPTDM)
            .cptname(UPDATED_CPTNAME)
            .printsign(UPDATED_PRINTSIGN)
            .printer(UPDATED_PRINTER)
            .printnum(UPDATED_PRINTNUM)
            .printcut(UPDATED_PRINTCUT)
            .syssign(UPDATED_SYSSIGN)
            .typesign(UPDATED_TYPESIGN)
            .qy(UPDATED_QY);
        return cyCptype;
    }

    @BeforeEach
    public void initTest() {
        cyCptype = createEntity(em);
    }

    @Test
    @Transactional
    void createCyCptype() throws Exception {
        int databaseSizeBeforeCreate = cyCptypeRepository.findAll().size();
        // Create the CyCptype
        CyCptypeDTO cyCptypeDTO = cyCptypeMapper.toDto(cyCptype);
        restCyCptypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cyCptypeDTO)))
            .andExpect(status().isCreated());

        // Validate the CyCptype in the database
        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeCreate + 1);
        CyCptype testCyCptype = cyCptypeList.get(cyCptypeList.size() - 1);
        assertThat(testCyCptype.getCptdm()).isEqualTo(DEFAULT_CPTDM);
        assertThat(testCyCptype.getCptname()).isEqualTo(DEFAULT_CPTNAME);
        assertThat(testCyCptype.getPrintsign()).isEqualTo(DEFAULT_PRINTSIGN);
        assertThat(testCyCptype.getPrinter()).isEqualTo(DEFAULT_PRINTER);
        assertThat(testCyCptype.getPrintnum()).isEqualTo(DEFAULT_PRINTNUM);
        assertThat(testCyCptype.getPrintcut()).isEqualTo(DEFAULT_PRINTCUT);
        assertThat(testCyCptype.getSyssign()).isEqualTo(DEFAULT_SYSSIGN);
        assertThat(testCyCptype.getTypesign()).isEqualTo(DEFAULT_TYPESIGN);
        assertThat(testCyCptype.getQy()).isEqualTo(DEFAULT_QY);

        // Validate the CyCptype in Elasticsearch
        verify(mockCyCptypeSearchRepository, times(1)).save(testCyCptype);
    }

    @Test
    @Transactional
    void createCyCptypeWithExistingId() throws Exception {
        // Create the CyCptype with an existing ID
        cyCptype.setId(1L);
        CyCptypeDTO cyCptypeDTO = cyCptypeMapper.toDto(cyCptype);

        int databaseSizeBeforeCreate = cyCptypeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCyCptypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cyCptypeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CyCptype in the database
        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the CyCptype in Elasticsearch
        verify(mockCyCptypeSearchRepository, times(0)).save(cyCptype);
    }

    @Test
    @Transactional
    void checkCptdmIsRequired() throws Exception {
        int databaseSizeBeforeTest = cyCptypeRepository.findAll().size();
        // set the field null
        cyCptype.setCptdm(null);

        // Create the CyCptype, which fails.
        CyCptypeDTO cyCptypeDTO = cyCptypeMapper.toDto(cyCptype);

        restCyCptypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cyCptypeDTO)))
            .andExpect(status().isBadRequest());

        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCptnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = cyCptypeRepository.findAll().size();
        // set the field null
        cyCptype.setCptname(null);

        // Create the CyCptype, which fails.
        CyCptypeDTO cyCptypeDTO = cyCptypeMapper.toDto(cyCptype);

        restCyCptypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cyCptypeDTO)))
            .andExpect(status().isBadRequest());

        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPrintsignIsRequired() throws Exception {
        int databaseSizeBeforeTest = cyCptypeRepository.findAll().size();
        // set the field null
        cyCptype.setPrintsign(null);

        // Create the CyCptype, which fails.
        CyCptypeDTO cyCptypeDTO = cyCptypeMapper.toDto(cyCptype);

        restCyCptypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cyCptypeDTO)))
            .andExpect(status().isBadRequest());

        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCyCptypes() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList
        restCyCptypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cyCptype.getId().intValue())))
            .andExpect(jsonPath("$.[*].cptdm").value(hasItem(DEFAULT_CPTDM)))
            .andExpect(jsonPath("$.[*].cptname").value(hasItem(DEFAULT_CPTNAME)))
            .andExpect(jsonPath("$.[*].printsign").value(hasItem(DEFAULT_PRINTSIGN.booleanValue())))
            .andExpect(jsonPath("$.[*].printer").value(hasItem(DEFAULT_PRINTER)))
            .andExpect(jsonPath("$.[*].printnum").value(hasItem(DEFAULT_PRINTNUM.intValue())))
            .andExpect(jsonPath("$.[*].printcut").value(hasItem(DEFAULT_PRINTCUT.intValue())))
            .andExpect(jsonPath("$.[*].syssign").value(hasItem(DEFAULT_SYSSIGN.booleanValue())))
            .andExpect(jsonPath("$.[*].typesign").value(hasItem(DEFAULT_TYPESIGN)))
            .andExpect(jsonPath("$.[*].qy").value(hasItem(DEFAULT_QY)));
    }

    @Test
    @Transactional
    void getCyCptype() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get the cyCptype
        restCyCptypeMockMvc
            .perform(get(ENTITY_API_URL_ID, cyCptype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cyCptype.getId().intValue()))
            .andExpect(jsonPath("$.cptdm").value(DEFAULT_CPTDM))
            .andExpect(jsonPath("$.cptname").value(DEFAULT_CPTNAME))
            .andExpect(jsonPath("$.printsign").value(DEFAULT_PRINTSIGN.booleanValue()))
            .andExpect(jsonPath("$.printer").value(DEFAULT_PRINTER))
            .andExpect(jsonPath("$.printnum").value(DEFAULT_PRINTNUM.intValue()))
            .andExpect(jsonPath("$.printcut").value(DEFAULT_PRINTCUT.intValue()))
            .andExpect(jsonPath("$.syssign").value(DEFAULT_SYSSIGN.booleanValue()))
            .andExpect(jsonPath("$.typesign").value(DEFAULT_TYPESIGN))
            .andExpect(jsonPath("$.qy").value(DEFAULT_QY));
    }

    @Test
    @Transactional
    void getCyCptypesByIdFiltering() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        Long id = cyCptype.getId();

        defaultCyCptypeShouldBeFound("id.equals=" + id);
        defaultCyCptypeShouldNotBeFound("id.notEquals=" + id);

        defaultCyCptypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCyCptypeShouldNotBeFound("id.greaterThan=" + id);

        defaultCyCptypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCyCptypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCyCptypesByCptdmIsEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where cptdm equals to DEFAULT_CPTDM
        defaultCyCptypeShouldBeFound("cptdm.equals=" + DEFAULT_CPTDM);

        // Get all the cyCptypeList where cptdm equals to UPDATED_CPTDM
        defaultCyCptypeShouldNotBeFound("cptdm.equals=" + UPDATED_CPTDM);
    }

    @Test
    @Transactional
    void getAllCyCptypesByCptdmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where cptdm not equals to DEFAULT_CPTDM
        defaultCyCptypeShouldNotBeFound("cptdm.notEquals=" + DEFAULT_CPTDM);

        // Get all the cyCptypeList where cptdm not equals to UPDATED_CPTDM
        defaultCyCptypeShouldBeFound("cptdm.notEquals=" + UPDATED_CPTDM);
    }

    @Test
    @Transactional
    void getAllCyCptypesByCptdmIsInShouldWork() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where cptdm in DEFAULT_CPTDM or UPDATED_CPTDM
        defaultCyCptypeShouldBeFound("cptdm.in=" + DEFAULT_CPTDM + "," + UPDATED_CPTDM);

        // Get all the cyCptypeList where cptdm equals to UPDATED_CPTDM
        defaultCyCptypeShouldNotBeFound("cptdm.in=" + UPDATED_CPTDM);
    }

    @Test
    @Transactional
    void getAllCyCptypesByCptdmIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where cptdm is not null
        defaultCyCptypeShouldBeFound("cptdm.specified=true");

        // Get all the cyCptypeList where cptdm is null
        defaultCyCptypeShouldNotBeFound("cptdm.specified=false");
    }

    @Test
    @Transactional
    void getAllCyCptypesByCptdmContainsSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where cptdm contains DEFAULT_CPTDM
        defaultCyCptypeShouldBeFound("cptdm.contains=" + DEFAULT_CPTDM);

        // Get all the cyCptypeList where cptdm contains UPDATED_CPTDM
        defaultCyCptypeShouldNotBeFound("cptdm.contains=" + UPDATED_CPTDM);
    }

    @Test
    @Transactional
    void getAllCyCptypesByCptdmNotContainsSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where cptdm does not contain DEFAULT_CPTDM
        defaultCyCptypeShouldNotBeFound("cptdm.doesNotContain=" + DEFAULT_CPTDM);

        // Get all the cyCptypeList where cptdm does not contain UPDATED_CPTDM
        defaultCyCptypeShouldBeFound("cptdm.doesNotContain=" + UPDATED_CPTDM);
    }

    @Test
    @Transactional
    void getAllCyCptypesByCptnameIsEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where cptname equals to DEFAULT_CPTNAME
        defaultCyCptypeShouldBeFound("cptname.equals=" + DEFAULT_CPTNAME);

        // Get all the cyCptypeList where cptname equals to UPDATED_CPTNAME
        defaultCyCptypeShouldNotBeFound("cptname.equals=" + UPDATED_CPTNAME);
    }

    @Test
    @Transactional
    void getAllCyCptypesByCptnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where cptname not equals to DEFAULT_CPTNAME
        defaultCyCptypeShouldNotBeFound("cptname.notEquals=" + DEFAULT_CPTNAME);

        // Get all the cyCptypeList where cptname not equals to UPDATED_CPTNAME
        defaultCyCptypeShouldBeFound("cptname.notEquals=" + UPDATED_CPTNAME);
    }

    @Test
    @Transactional
    void getAllCyCptypesByCptnameIsInShouldWork() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where cptname in DEFAULT_CPTNAME or UPDATED_CPTNAME
        defaultCyCptypeShouldBeFound("cptname.in=" + DEFAULT_CPTNAME + "," + UPDATED_CPTNAME);

        // Get all the cyCptypeList where cptname equals to UPDATED_CPTNAME
        defaultCyCptypeShouldNotBeFound("cptname.in=" + UPDATED_CPTNAME);
    }

    @Test
    @Transactional
    void getAllCyCptypesByCptnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where cptname is not null
        defaultCyCptypeShouldBeFound("cptname.specified=true");

        // Get all the cyCptypeList where cptname is null
        defaultCyCptypeShouldNotBeFound("cptname.specified=false");
    }

    @Test
    @Transactional
    void getAllCyCptypesByCptnameContainsSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where cptname contains DEFAULT_CPTNAME
        defaultCyCptypeShouldBeFound("cptname.contains=" + DEFAULT_CPTNAME);

        // Get all the cyCptypeList where cptname contains UPDATED_CPTNAME
        defaultCyCptypeShouldNotBeFound("cptname.contains=" + UPDATED_CPTNAME);
    }

    @Test
    @Transactional
    void getAllCyCptypesByCptnameNotContainsSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where cptname does not contain DEFAULT_CPTNAME
        defaultCyCptypeShouldNotBeFound("cptname.doesNotContain=" + DEFAULT_CPTNAME);

        // Get all the cyCptypeList where cptname does not contain UPDATED_CPTNAME
        defaultCyCptypeShouldBeFound("cptname.doesNotContain=" + UPDATED_CPTNAME);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintsignIsEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printsign equals to DEFAULT_PRINTSIGN
        defaultCyCptypeShouldBeFound("printsign.equals=" + DEFAULT_PRINTSIGN);

        // Get all the cyCptypeList where printsign equals to UPDATED_PRINTSIGN
        defaultCyCptypeShouldNotBeFound("printsign.equals=" + UPDATED_PRINTSIGN);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintsignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printsign not equals to DEFAULT_PRINTSIGN
        defaultCyCptypeShouldNotBeFound("printsign.notEquals=" + DEFAULT_PRINTSIGN);

        // Get all the cyCptypeList where printsign not equals to UPDATED_PRINTSIGN
        defaultCyCptypeShouldBeFound("printsign.notEquals=" + UPDATED_PRINTSIGN);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintsignIsInShouldWork() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printsign in DEFAULT_PRINTSIGN or UPDATED_PRINTSIGN
        defaultCyCptypeShouldBeFound("printsign.in=" + DEFAULT_PRINTSIGN + "," + UPDATED_PRINTSIGN);

        // Get all the cyCptypeList where printsign equals to UPDATED_PRINTSIGN
        defaultCyCptypeShouldNotBeFound("printsign.in=" + UPDATED_PRINTSIGN);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintsignIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printsign is not null
        defaultCyCptypeShouldBeFound("printsign.specified=true");

        // Get all the cyCptypeList where printsign is null
        defaultCyCptypeShouldNotBeFound("printsign.specified=false");
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrinterIsEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printer equals to DEFAULT_PRINTER
        defaultCyCptypeShouldBeFound("printer.equals=" + DEFAULT_PRINTER);

        // Get all the cyCptypeList where printer equals to UPDATED_PRINTER
        defaultCyCptypeShouldNotBeFound("printer.equals=" + UPDATED_PRINTER);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrinterIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printer not equals to DEFAULT_PRINTER
        defaultCyCptypeShouldNotBeFound("printer.notEquals=" + DEFAULT_PRINTER);

        // Get all the cyCptypeList where printer not equals to UPDATED_PRINTER
        defaultCyCptypeShouldBeFound("printer.notEquals=" + UPDATED_PRINTER);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrinterIsInShouldWork() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printer in DEFAULT_PRINTER or UPDATED_PRINTER
        defaultCyCptypeShouldBeFound("printer.in=" + DEFAULT_PRINTER + "," + UPDATED_PRINTER);

        // Get all the cyCptypeList where printer equals to UPDATED_PRINTER
        defaultCyCptypeShouldNotBeFound("printer.in=" + UPDATED_PRINTER);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrinterIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printer is not null
        defaultCyCptypeShouldBeFound("printer.specified=true");

        // Get all the cyCptypeList where printer is null
        defaultCyCptypeShouldNotBeFound("printer.specified=false");
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrinterContainsSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printer contains DEFAULT_PRINTER
        defaultCyCptypeShouldBeFound("printer.contains=" + DEFAULT_PRINTER);

        // Get all the cyCptypeList where printer contains UPDATED_PRINTER
        defaultCyCptypeShouldNotBeFound("printer.contains=" + UPDATED_PRINTER);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrinterNotContainsSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printer does not contain DEFAULT_PRINTER
        defaultCyCptypeShouldNotBeFound("printer.doesNotContain=" + DEFAULT_PRINTER);

        // Get all the cyCptypeList where printer does not contain UPDATED_PRINTER
        defaultCyCptypeShouldBeFound("printer.doesNotContain=" + UPDATED_PRINTER);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintnumIsEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printnum equals to DEFAULT_PRINTNUM
        defaultCyCptypeShouldBeFound("printnum.equals=" + DEFAULT_PRINTNUM);

        // Get all the cyCptypeList where printnum equals to UPDATED_PRINTNUM
        defaultCyCptypeShouldNotBeFound("printnum.equals=" + UPDATED_PRINTNUM);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintnumIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printnum not equals to DEFAULT_PRINTNUM
        defaultCyCptypeShouldNotBeFound("printnum.notEquals=" + DEFAULT_PRINTNUM);

        // Get all the cyCptypeList where printnum not equals to UPDATED_PRINTNUM
        defaultCyCptypeShouldBeFound("printnum.notEquals=" + UPDATED_PRINTNUM);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintnumIsInShouldWork() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printnum in DEFAULT_PRINTNUM or UPDATED_PRINTNUM
        defaultCyCptypeShouldBeFound("printnum.in=" + DEFAULT_PRINTNUM + "," + UPDATED_PRINTNUM);

        // Get all the cyCptypeList where printnum equals to UPDATED_PRINTNUM
        defaultCyCptypeShouldNotBeFound("printnum.in=" + UPDATED_PRINTNUM);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintnumIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printnum is not null
        defaultCyCptypeShouldBeFound("printnum.specified=true");

        // Get all the cyCptypeList where printnum is null
        defaultCyCptypeShouldNotBeFound("printnum.specified=false");
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintnumIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printnum is greater than or equal to DEFAULT_PRINTNUM
        defaultCyCptypeShouldBeFound("printnum.greaterThanOrEqual=" + DEFAULT_PRINTNUM);

        // Get all the cyCptypeList where printnum is greater than or equal to UPDATED_PRINTNUM
        defaultCyCptypeShouldNotBeFound("printnum.greaterThanOrEqual=" + UPDATED_PRINTNUM);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintnumIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printnum is less than or equal to DEFAULT_PRINTNUM
        defaultCyCptypeShouldBeFound("printnum.lessThanOrEqual=" + DEFAULT_PRINTNUM);

        // Get all the cyCptypeList where printnum is less than or equal to SMALLER_PRINTNUM
        defaultCyCptypeShouldNotBeFound("printnum.lessThanOrEqual=" + SMALLER_PRINTNUM);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintnumIsLessThanSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printnum is less than DEFAULT_PRINTNUM
        defaultCyCptypeShouldNotBeFound("printnum.lessThan=" + DEFAULT_PRINTNUM);

        // Get all the cyCptypeList where printnum is less than UPDATED_PRINTNUM
        defaultCyCptypeShouldBeFound("printnum.lessThan=" + UPDATED_PRINTNUM);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintnumIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printnum is greater than DEFAULT_PRINTNUM
        defaultCyCptypeShouldNotBeFound("printnum.greaterThan=" + DEFAULT_PRINTNUM);

        // Get all the cyCptypeList where printnum is greater than SMALLER_PRINTNUM
        defaultCyCptypeShouldBeFound("printnum.greaterThan=" + SMALLER_PRINTNUM);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintcutIsEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printcut equals to DEFAULT_PRINTCUT
        defaultCyCptypeShouldBeFound("printcut.equals=" + DEFAULT_PRINTCUT);

        // Get all the cyCptypeList where printcut equals to UPDATED_PRINTCUT
        defaultCyCptypeShouldNotBeFound("printcut.equals=" + UPDATED_PRINTCUT);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintcutIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printcut not equals to DEFAULT_PRINTCUT
        defaultCyCptypeShouldNotBeFound("printcut.notEquals=" + DEFAULT_PRINTCUT);

        // Get all the cyCptypeList where printcut not equals to UPDATED_PRINTCUT
        defaultCyCptypeShouldBeFound("printcut.notEquals=" + UPDATED_PRINTCUT);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintcutIsInShouldWork() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printcut in DEFAULT_PRINTCUT or UPDATED_PRINTCUT
        defaultCyCptypeShouldBeFound("printcut.in=" + DEFAULT_PRINTCUT + "," + UPDATED_PRINTCUT);

        // Get all the cyCptypeList where printcut equals to UPDATED_PRINTCUT
        defaultCyCptypeShouldNotBeFound("printcut.in=" + UPDATED_PRINTCUT);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintcutIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printcut is not null
        defaultCyCptypeShouldBeFound("printcut.specified=true");

        // Get all the cyCptypeList where printcut is null
        defaultCyCptypeShouldNotBeFound("printcut.specified=false");
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintcutIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printcut is greater than or equal to DEFAULT_PRINTCUT
        defaultCyCptypeShouldBeFound("printcut.greaterThanOrEqual=" + DEFAULT_PRINTCUT);

        // Get all the cyCptypeList where printcut is greater than or equal to UPDATED_PRINTCUT
        defaultCyCptypeShouldNotBeFound("printcut.greaterThanOrEqual=" + UPDATED_PRINTCUT);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintcutIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printcut is less than or equal to DEFAULT_PRINTCUT
        defaultCyCptypeShouldBeFound("printcut.lessThanOrEqual=" + DEFAULT_PRINTCUT);

        // Get all the cyCptypeList where printcut is less than or equal to SMALLER_PRINTCUT
        defaultCyCptypeShouldNotBeFound("printcut.lessThanOrEqual=" + SMALLER_PRINTCUT);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintcutIsLessThanSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printcut is less than DEFAULT_PRINTCUT
        defaultCyCptypeShouldNotBeFound("printcut.lessThan=" + DEFAULT_PRINTCUT);

        // Get all the cyCptypeList where printcut is less than UPDATED_PRINTCUT
        defaultCyCptypeShouldBeFound("printcut.lessThan=" + UPDATED_PRINTCUT);
    }

    @Test
    @Transactional
    void getAllCyCptypesByPrintcutIsGreaterThanSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where printcut is greater than DEFAULT_PRINTCUT
        defaultCyCptypeShouldNotBeFound("printcut.greaterThan=" + DEFAULT_PRINTCUT);

        // Get all the cyCptypeList where printcut is greater than SMALLER_PRINTCUT
        defaultCyCptypeShouldBeFound("printcut.greaterThan=" + SMALLER_PRINTCUT);
    }

    @Test
    @Transactional
    void getAllCyCptypesBySyssignIsEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where syssign equals to DEFAULT_SYSSIGN
        defaultCyCptypeShouldBeFound("syssign.equals=" + DEFAULT_SYSSIGN);

        // Get all the cyCptypeList where syssign equals to UPDATED_SYSSIGN
        defaultCyCptypeShouldNotBeFound("syssign.equals=" + UPDATED_SYSSIGN);
    }

    @Test
    @Transactional
    void getAllCyCptypesBySyssignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where syssign not equals to DEFAULT_SYSSIGN
        defaultCyCptypeShouldNotBeFound("syssign.notEquals=" + DEFAULT_SYSSIGN);

        // Get all the cyCptypeList where syssign not equals to UPDATED_SYSSIGN
        defaultCyCptypeShouldBeFound("syssign.notEquals=" + UPDATED_SYSSIGN);
    }

    @Test
    @Transactional
    void getAllCyCptypesBySyssignIsInShouldWork() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where syssign in DEFAULT_SYSSIGN or UPDATED_SYSSIGN
        defaultCyCptypeShouldBeFound("syssign.in=" + DEFAULT_SYSSIGN + "," + UPDATED_SYSSIGN);

        // Get all the cyCptypeList where syssign equals to UPDATED_SYSSIGN
        defaultCyCptypeShouldNotBeFound("syssign.in=" + UPDATED_SYSSIGN);
    }

    @Test
    @Transactional
    void getAllCyCptypesBySyssignIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where syssign is not null
        defaultCyCptypeShouldBeFound("syssign.specified=true");

        // Get all the cyCptypeList where syssign is null
        defaultCyCptypeShouldNotBeFound("syssign.specified=false");
    }

    @Test
    @Transactional
    void getAllCyCptypesByTypesignIsEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where typesign equals to DEFAULT_TYPESIGN
        defaultCyCptypeShouldBeFound("typesign.equals=" + DEFAULT_TYPESIGN);

        // Get all the cyCptypeList where typesign equals to UPDATED_TYPESIGN
        defaultCyCptypeShouldNotBeFound("typesign.equals=" + UPDATED_TYPESIGN);
    }

    @Test
    @Transactional
    void getAllCyCptypesByTypesignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where typesign not equals to DEFAULT_TYPESIGN
        defaultCyCptypeShouldNotBeFound("typesign.notEquals=" + DEFAULT_TYPESIGN);

        // Get all the cyCptypeList where typesign not equals to UPDATED_TYPESIGN
        defaultCyCptypeShouldBeFound("typesign.notEquals=" + UPDATED_TYPESIGN);
    }

    @Test
    @Transactional
    void getAllCyCptypesByTypesignIsInShouldWork() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where typesign in DEFAULT_TYPESIGN or UPDATED_TYPESIGN
        defaultCyCptypeShouldBeFound("typesign.in=" + DEFAULT_TYPESIGN + "," + UPDATED_TYPESIGN);

        // Get all the cyCptypeList where typesign equals to UPDATED_TYPESIGN
        defaultCyCptypeShouldNotBeFound("typesign.in=" + UPDATED_TYPESIGN);
    }

    @Test
    @Transactional
    void getAllCyCptypesByTypesignIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where typesign is not null
        defaultCyCptypeShouldBeFound("typesign.specified=true");

        // Get all the cyCptypeList where typesign is null
        defaultCyCptypeShouldNotBeFound("typesign.specified=false");
    }

    @Test
    @Transactional
    void getAllCyCptypesByTypesignContainsSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where typesign contains DEFAULT_TYPESIGN
        defaultCyCptypeShouldBeFound("typesign.contains=" + DEFAULT_TYPESIGN);

        // Get all the cyCptypeList where typesign contains UPDATED_TYPESIGN
        defaultCyCptypeShouldNotBeFound("typesign.contains=" + UPDATED_TYPESIGN);
    }

    @Test
    @Transactional
    void getAllCyCptypesByTypesignNotContainsSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where typesign does not contain DEFAULT_TYPESIGN
        defaultCyCptypeShouldNotBeFound("typesign.doesNotContain=" + DEFAULT_TYPESIGN);

        // Get all the cyCptypeList where typesign does not contain UPDATED_TYPESIGN
        defaultCyCptypeShouldBeFound("typesign.doesNotContain=" + UPDATED_TYPESIGN);
    }

    @Test
    @Transactional
    void getAllCyCptypesByQyIsEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where qy equals to DEFAULT_QY
        defaultCyCptypeShouldBeFound("qy.equals=" + DEFAULT_QY);

        // Get all the cyCptypeList where qy equals to UPDATED_QY
        defaultCyCptypeShouldNotBeFound("qy.equals=" + UPDATED_QY);
    }

    @Test
    @Transactional
    void getAllCyCptypesByQyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where qy not equals to DEFAULT_QY
        defaultCyCptypeShouldNotBeFound("qy.notEquals=" + DEFAULT_QY);

        // Get all the cyCptypeList where qy not equals to UPDATED_QY
        defaultCyCptypeShouldBeFound("qy.notEquals=" + UPDATED_QY);
    }

    @Test
    @Transactional
    void getAllCyCptypesByQyIsInShouldWork() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where qy in DEFAULT_QY or UPDATED_QY
        defaultCyCptypeShouldBeFound("qy.in=" + DEFAULT_QY + "," + UPDATED_QY);

        // Get all the cyCptypeList where qy equals to UPDATED_QY
        defaultCyCptypeShouldNotBeFound("qy.in=" + UPDATED_QY);
    }

    @Test
    @Transactional
    void getAllCyCptypesByQyIsNullOrNotNull() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where qy is not null
        defaultCyCptypeShouldBeFound("qy.specified=true");

        // Get all the cyCptypeList where qy is null
        defaultCyCptypeShouldNotBeFound("qy.specified=false");
    }

    @Test
    @Transactional
    void getAllCyCptypesByQyContainsSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where qy contains DEFAULT_QY
        defaultCyCptypeShouldBeFound("qy.contains=" + DEFAULT_QY);

        // Get all the cyCptypeList where qy contains UPDATED_QY
        defaultCyCptypeShouldNotBeFound("qy.contains=" + UPDATED_QY);
    }

    @Test
    @Transactional
    void getAllCyCptypesByQyNotContainsSomething() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        // Get all the cyCptypeList where qy does not contain DEFAULT_QY
        defaultCyCptypeShouldNotBeFound("qy.doesNotContain=" + DEFAULT_QY);

        // Get all the cyCptypeList where qy does not contain UPDATED_QY
        defaultCyCptypeShouldBeFound("qy.doesNotContain=" + UPDATED_QY);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCyCptypeShouldBeFound(String filter) throws Exception {
        restCyCptypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cyCptype.getId().intValue())))
            .andExpect(jsonPath("$.[*].cptdm").value(hasItem(DEFAULT_CPTDM)))
            .andExpect(jsonPath("$.[*].cptname").value(hasItem(DEFAULT_CPTNAME)))
            .andExpect(jsonPath("$.[*].printsign").value(hasItem(DEFAULT_PRINTSIGN.booleanValue())))
            .andExpect(jsonPath("$.[*].printer").value(hasItem(DEFAULT_PRINTER)))
            .andExpect(jsonPath("$.[*].printnum").value(hasItem(DEFAULT_PRINTNUM.intValue())))
            .andExpect(jsonPath("$.[*].printcut").value(hasItem(DEFAULT_PRINTCUT.intValue())))
            .andExpect(jsonPath("$.[*].syssign").value(hasItem(DEFAULT_SYSSIGN.booleanValue())))
            .andExpect(jsonPath("$.[*].typesign").value(hasItem(DEFAULT_TYPESIGN)))
            .andExpect(jsonPath("$.[*].qy").value(hasItem(DEFAULT_QY)));

        // Check, that the count call also returns 1
        restCyCptypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCyCptypeShouldNotBeFound(String filter) throws Exception {
        restCyCptypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCyCptypeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCyCptype() throws Exception {
        // Get the cyCptype
        restCyCptypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCyCptype() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        int databaseSizeBeforeUpdate = cyCptypeRepository.findAll().size();

        // Update the cyCptype
        CyCptype updatedCyCptype = cyCptypeRepository.findById(cyCptype.getId()).get();
        // Disconnect from session so that the updates on updatedCyCptype are not directly saved in db
        em.detach(updatedCyCptype);
        updatedCyCptype
            .cptdm(UPDATED_CPTDM)
            .cptname(UPDATED_CPTNAME)
            .printsign(UPDATED_PRINTSIGN)
            .printer(UPDATED_PRINTER)
            .printnum(UPDATED_PRINTNUM)
            .printcut(UPDATED_PRINTCUT)
            .syssign(UPDATED_SYSSIGN)
            .typesign(UPDATED_TYPESIGN)
            .qy(UPDATED_QY);
        CyCptypeDTO cyCptypeDTO = cyCptypeMapper.toDto(updatedCyCptype);

        restCyCptypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cyCptypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cyCptypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CyCptype in the database
        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeUpdate);
        CyCptype testCyCptype = cyCptypeList.get(cyCptypeList.size() - 1);
        assertThat(testCyCptype.getCptdm()).isEqualTo(UPDATED_CPTDM);
        assertThat(testCyCptype.getCptname()).isEqualTo(UPDATED_CPTNAME);
        assertThat(testCyCptype.getPrintsign()).isEqualTo(UPDATED_PRINTSIGN);
        assertThat(testCyCptype.getPrinter()).isEqualTo(UPDATED_PRINTER);
        assertThat(testCyCptype.getPrintnum()).isEqualTo(UPDATED_PRINTNUM);
        assertThat(testCyCptype.getPrintcut()).isEqualTo(UPDATED_PRINTCUT);
        assertThat(testCyCptype.getSyssign()).isEqualTo(UPDATED_SYSSIGN);
        assertThat(testCyCptype.getTypesign()).isEqualTo(UPDATED_TYPESIGN);
        assertThat(testCyCptype.getQy()).isEqualTo(UPDATED_QY);

        // Validate the CyCptype in Elasticsearch
        verify(mockCyCptypeSearchRepository).save(testCyCptype);
    }

    @Test
    @Transactional
    void putNonExistingCyCptype() throws Exception {
        int databaseSizeBeforeUpdate = cyCptypeRepository.findAll().size();
        cyCptype.setId(count.incrementAndGet());

        // Create the CyCptype
        CyCptypeDTO cyCptypeDTO = cyCptypeMapper.toDto(cyCptype);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCyCptypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, cyCptypeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cyCptypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CyCptype in the database
        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CyCptype in Elasticsearch
        verify(mockCyCptypeSearchRepository, times(0)).save(cyCptype);
    }

    @Test
    @Transactional
    void putWithIdMismatchCyCptype() throws Exception {
        int databaseSizeBeforeUpdate = cyCptypeRepository.findAll().size();
        cyCptype.setId(count.incrementAndGet());

        // Create the CyCptype
        CyCptypeDTO cyCptypeDTO = cyCptypeMapper.toDto(cyCptype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCyCptypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(cyCptypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CyCptype in the database
        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CyCptype in Elasticsearch
        verify(mockCyCptypeSearchRepository, times(0)).save(cyCptype);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCyCptype() throws Exception {
        int databaseSizeBeforeUpdate = cyCptypeRepository.findAll().size();
        cyCptype.setId(count.incrementAndGet());

        // Create the CyCptype
        CyCptypeDTO cyCptypeDTO = cyCptypeMapper.toDto(cyCptype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCyCptypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(cyCptypeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CyCptype in the database
        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CyCptype in Elasticsearch
        verify(mockCyCptypeSearchRepository, times(0)).save(cyCptype);
    }

    @Test
    @Transactional
    void partialUpdateCyCptypeWithPatch() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        int databaseSizeBeforeUpdate = cyCptypeRepository.findAll().size();

        // Update the cyCptype using partial update
        CyCptype partialUpdatedCyCptype = new CyCptype();
        partialUpdatedCyCptype.setId(cyCptype.getId());

        partialUpdatedCyCptype.cptdm(UPDATED_CPTDM).printsign(UPDATED_PRINTSIGN).printcut(UPDATED_PRINTCUT).typesign(UPDATED_TYPESIGN);

        restCyCptypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCyCptype.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCyCptype))
            )
            .andExpect(status().isOk());

        // Validate the CyCptype in the database
        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeUpdate);
        CyCptype testCyCptype = cyCptypeList.get(cyCptypeList.size() - 1);
        assertThat(testCyCptype.getCptdm()).isEqualTo(UPDATED_CPTDM);
        assertThat(testCyCptype.getCptname()).isEqualTo(DEFAULT_CPTNAME);
        assertThat(testCyCptype.getPrintsign()).isEqualTo(UPDATED_PRINTSIGN);
        assertThat(testCyCptype.getPrinter()).isEqualTo(DEFAULT_PRINTER);
        assertThat(testCyCptype.getPrintnum()).isEqualTo(DEFAULT_PRINTNUM);
        assertThat(testCyCptype.getPrintcut()).isEqualTo(UPDATED_PRINTCUT);
        assertThat(testCyCptype.getSyssign()).isEqualTo(DEFAULT_SYSSIGN);
        assertThat(testCyCptype.getTypesign()).isEqualTo(UPDATED_TYPESIGN);
        assertThat(testCyCptype.getQy()).isEqualTo(DEFAULT_QY);
    }

    @Test
    @Transactional
    void fullUpdateCyCptypeWithPatch() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        int databaseSizeBeforeUpdate = cyCptypeRepository.findAll().size();

        // Update the cyCptype using partial update
        CyCptype partialUpdatedCyCptype = new CyCptype();
        partialUpdatedCyCptype.setId(cyCptype.getId());

        partialUpdatedCyCptype
            .cptdm(UPDATED_CPTDM)
            .cptname(UPDATED_CPTNAME)
            .printsign(UPDATED_PRINTSIGN)
            .printer(UPDATED_PRINTER)
            .printnum(UPDATED_PRINTNUM)
            .printcut(UPDATED_PRINTCUT)
            .syssign(UPDATED_SYSSIGN)
            .typesign(UPDATED_TYPESIGN)
            .qy(UPDATED_QY);

        restCyCptypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCyCptype.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCyCptype))
            )
            .andExpect(status().isOk());

        // Validate the CyCptype in the database
        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeUpdate);
        CyCptype testCyCptype = cyCptypeList.get(cyCptypeList.size() - 1);
        assertThat(testCyCptype.getCptdm()).isEqualTo(UPDATED_CPTDM);
        assertThat(testCyCptype.getCptname()).isEqualTo(UPDATED_CPTNAME);
        assertThat(testCyCptype.getPrintsign()).isEqualTo(UPDATED_PRINTSIGN);
        assertThat(testCyCptype.getPrinter()).isEqualTo(UPDATED_PRINTER);
        assertThat(testCyCptype.getPrintnum()).isEqualTo(UPDATED_PRINTNUM);
        assertThat(testCyCptype.getPrintcut()).isEqualTo(UPDATED_PRINTCUT);
        assertThat(testCyCptype.getSyssign()).isEqualTo(UPDATED_SYSSIGN);
        assertThat(testCyCptype.getTypesign()).isEqualTo(UPDATED_TYPESIGN);
        assertThat(testCyCptype.getQy()).isEqualTo(UPDATED_QY);
    }

    @Test
    @Transactional
    void patchNonExistingCyCptype() throws Exception {
        int databaseSizeBeforeUpdate = cyCptypeRepository.findAll().size();
        cyCptype.setId(count.incrementAndGet());

        // Create the CyCptype
        CyCptypeDTO cyCptypeDTO = cyCptypeMapper.toDto(cyCptype);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCyCptypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, cyCptypeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cyCptypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CyCptype in the database
        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CyCptype in Elasticsearch
        verify(mockCyCptypeSearchRepository, times(0)).save(cyCptype);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCyCptype() throws Exception {
        int databaseSizeBeforeUpdate = cyCptypeRepository.findAll().size();
        cyCptype.setId(count.incrementAndGet());

        // Create the CyCptype
        CyCptypeDTO cyCptypeDTO = cyCptypeMapper.toDto(cyCptype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCyCptypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(cyCptypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CyCptype in the database
        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CyCptype in Elasticsearch
        verify(mockCyCptypeSearchRepository, times(0)).save(cyCptype);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCyCptype() throws Exception {
        int databaseSizeBeforeUpdate = cyCptypeRepository.findAll().size();
        cyCptype.setId(count.incrementAndGet());

        // Create the CyCptype
        CyCptypeDTO cyCptypeDTO = cyCptypeMapper.toDto(cyCptype);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCyCptypeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(cyCptypeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CyCptype in the database
        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CyCptype in Elasticsearch
        verify(mockCyCptypeSearchRepository, times(0)).save(cyCptype);
    }

    @Test
    @Transactional
    void deleteCyCptype() throws Exception {
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);

        int databaseSizeBeforeDelete = cyCptypeRepository.findAll().size();

        // Delete the cyCptype
        restCyCptypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, cyCptype.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CyCptype> cyCptypeList = cyCptypeRepository.findAll();
        assertThat(cyCptypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CyCptype in Elasticsearch
        verify(mockCyCptypeSearchRepository, times(1)).deleteById(cyCptype.getId());
    }

    @Test
    @Transactional
    void searchCyCptype() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        cyCptypeRepository.saveAndFlush(cyCptype);
        when(mockCyCptypeSearchRepository.search(queryStringQuery("id:" + cyCptype.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(cyCptype), PageRequest.of(0, 1), 1));

        // Search the cyCptype
        restCyCptypeMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + cyCptype.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cyCptype.getId().intValue())))
            .andExpect(jsonPath("$.[*].cptdm").value(hasItem(DEFAULT_CPTDM)))
            .andExpect(jsonPath("$.[*].cptname").value(hasItem(DEFAULT_CPTNAME)))
            .andExpect(jsonPath("$.[*].printsign").value(hasItem(DEFAULT_PRINTSIGN.booleanValue())))
            .andExpect(jsonPath("$.[*].printer").value(hasItem(DEFAULT_PRINTER)))
            .andExpect(jsonPath("$.[*].printnum").value(hasItem(DEFAULT_PRINTNUM.intValue())))
            .andExpect(jsonPath("$.[*].printcut").value(hasItem(DEFAULT_PRINTCUT.intValue())))
            .andExpect(jsonPath("$.[*].syssign").value(hasItem(DEFAULT_SYSSIGN.booleanValue())))
            .andExpect(jsonPath("$.[*].typesign").value(hasItem(DEFAULT_TYPESIGN)))
            .andExpect(jsonPath("$.[*].qy").value(hasItem(DEFAULT_QY)));
    }
}
