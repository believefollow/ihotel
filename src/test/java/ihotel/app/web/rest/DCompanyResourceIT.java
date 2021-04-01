package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DCompany;
import ihotel.app.repository.DCompanyRepository;
import ihotel.app.repository.search.DCompanySearchRepository;
import ihotel.app.service.criteria.DCompanyCriteria;
import ihotel.app.service.dto.DCompanyDTO;
import ihotel.app.service.mapper.DCompanyMapper;
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
 * Integration tests for the {@link DCompanyResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DCompanyResourceIT {

    private static final String DEFAULT_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY = "BBBBBBBBBB";

    private static final String DEFAULT_LINKMAN = "AAAAAAAAAA";
    private static final String UPDATED_LINKMAN = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/d-companies";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-companies";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DCompanyRepository dCompanyRepository;

    @Autowired
    private DCompanyMapper dCompanyMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DCompanySearchRepositoryMockConfiguration
     */
    @Autowired
    private DCompanySearchRepository mockDCompanySearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDCompanyMockMvc;

    private DCompany dCompany;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DCompany createEntity(EntityManager em) {
        DCompany dCompany = new DCompany()
            .company(DEFAULT_COMPANY)
            .linkman(DEFAULT_LINKMAN)
            .phone(DEFAULT_PHONE)
            .address(DEFAULT_ADDRESS)
            .remark(DEFAULT_REMARK)
            .fax(DEFAULT_FAX);
        return dCompany;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DCompany createUpdatedEntity(EntityManager em) {
        DCompany dCompany = new DCompany()
            .company(UPDATED_COMPANY)
            .linkman(UPDATED_LINKMAN)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS)
            .remark(UPDATED_REMARK)
            .fax(UPDATED_FAX);
        return dCompany;
    }

    @BeforeEach
    public void initTest() {
        dCompany = createEntity(em);
    }

    @Test
    @Transactional
    void createDCompany() throws Exception {
        int databaseSizeBeforeCreate = dCompanyRepository.findAll().size();
        // Create the DCompany
        DCompanyDTO dCompanyDTO = dCompanyMapper.toDto(dCompany);
        restDCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCompanyDTO)))
            .andExpect(status().isCreated());

        // Validate the DCompany in the database
        List<DCompany> dCompanyList = dCompanyRepository.findAll();
        assertThat(dCompanyList).hasSize(databaseSizeBeforeCreate + 1);
        DCompany testDCompany = dCompanyList.get(dCompanyList.size() - 1);
        assertThat(testDCompany.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testDCompany.getLinkman()).isEqualTo(DEFAULT_LINKMAN);
        assertThat(testDCompany.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testDCompany.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testDCompany.getRemark()).isEqualTo(DEFAULT_REMARK);
        assertThat(testDCompany.getFax()).isEqualTo(DEFAULT_FAX);

        // Validate the DCompany in Elasticsearch
        verify(mockDCompanySearchRepository, times(1)).save(testDCompany);
    }

    @Test
    @Transactional
    void createDCompanyWithExistingId() throws Exception {
        // Create the DCompany with an existing ID
        dCompany.setId(1L);
        DCompanyDTO dCompanyDTO = dCompanyMapper.toDto(dCompany);

        int databaseSizeBeforeCreate = dCompanyRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCompanyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DCompany in the database
        List<DCompany> dCompanyList = dCompanyRepository.findAll();
        assertThat(dCompanyList).hasSize(databaseSizeBeforeCreate);

        // Validate the DCompany in Elasticsearch
        verify(mockDCompanySearchRepository, times(0)).save(dCompany);
    }

    @Test
    @Transactional
    void checkCompanyIsRequired() throws Exception {
        int databaseSizeBeforeTest = dCompanyRepository.findAll().size();
        // set the field null
        dCompany.setCompany(null);

        // Create the DCompany, which fails.
        DCompanyDTO dCompanyDTO = dCompanyMapper.toDto(dCompany);

        restDCompanyMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCompanyDTO)))
            .andExpect(status().isBadRequest());

        List<DCompany> dCompanyList = dCompanyRepository.findAll();
        assertThat(dCompanyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDCompanies() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList
        restDCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].linkman").value(hasItem(DEFAULT_LINKMAN)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)));
    }

    @Test
    @Transactional
    void getDCompany() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get the dCompany
        restDCompanyMockMvc
            .perform(get(ENTITY_API_URL_ID, dCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dCompany.getId().intValue()))
            .andExpect(jsonPath("$.company").value(DEFAULT_COMPANY))
            .andExpect(jsonPath("$.linkman").value(DEFAULT_LINKMAN))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX));
    }

    @Test
    @Transactional
    void getDCompaniesByIdFiltering() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        Long id = dCompany.getId();

        defaultDCompanyShouldBeFound("id.equals=" + id);
        defaultDCompanyShouldNotBeFound("id.notEquals=" + id);

        defaultDCompanyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDCompanyShouldNotBeFound("id.greaterThan=" + id);

        defaultDCompanyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDCompanyShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDCompaniesByCompanyIsEqualToSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where company equals to DEFAULT_COMPANY
        defaultDCompanyShouldBeFound("company.equals=" + DEFAULT_COMPANY);

        // Get all the dCompanyList where company equals to UPDATED_COMPANY
        defaultDCompanyShouldNotBeFound("company.equals=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllDCompaniesByCompanyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where company not equals to DEFAULT_COMPANY
        defaultDCompanyShouldNotBeFound("company.notEquals=" + DEFAULT_COMPANY);

        // Get all the dCompanyList where company not equals to UPDATED_COMPANY
        defaultDCompanyShouldBeFound("company.notEquals=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllDCompaniesByCompanyIsInShouldWork() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where company in DEFAULT_COMPANY or UPDATED_COMPANY
        defaultDCompanyShouldBeFound("company.in=" + DEFAULT_COMPANY + "," + UPDATED_COMPANY);

        // Get all the dCompanyList where company equals to UPDATED_COMPANY
        defaultDCompanyShouldNotBeFound("company.in=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllDCompaniesByCompanyIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where company is not null
        defaultDCompanyShouldBeFound("company.specified=true");

        // Get all the dCompanyList where company is null
        defaultDCompanyShouldNotBeFound("company.specified=false");
    }

    @Test
    @Transactional
    void getAllDCompaniesByCompanyContainsSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where company contains DEFAULT_COMPANY
        defaultDCompanyShouldBeFound("company.contains=" + DEFAULT_COMPANY);

        // Get all the dCompanyList where company contains UPDATED_COMPANY
        defaultDCompanyShouldNotBeFound("company.contains=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllDCompaniesByCompanyNotContainsSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where company does not contain DEFAULT_COMPANY
        defaultDCompanyShouldNotBeFound("company.doesNotContain=" + DEFAULT_COMPANY);

        // Get all the dCompanyList where company does not contain UPDATED_COMPANY
        defaultDCompanyShouldBeFound("company.doesNotContain=" + UPDATED_COMPANY);
    }

    @Test
    @Transactional
    void getAllDCompaniesByLinkmanIsEqualToSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where linkman equals to DEFAULT_LINKMAN
        defaultDCompanyShouldBeFound("linkman.equals=" + DEFAULT_LINKMAN);

        // Get all the dCompanyList where linkman equals to UPDATED_LINKMAN
        defaultDCompanyShouldNotBeFound("linkman.equals=" + UPDATED_LINKMAN);
    }

    @Test
    @Transactional
    void getAllDCompaniesByLinkmanIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where linkman not equals to DEFAULT_LINKMAN
        defaultDCompanyShouldNotBeFound("linkman.notEquals=" + DEFAULT_LINKMAN);

        // Get all the dCompanyList where linkman not equals to UPDATED_LINKMAN
        defaultDCompanyShouldBeFound("linkman.notEquals=" + UPDATED_LINKMAN);
    }

    @Test
    @Transactional
    void getAllDCompaniesByLinkmanIsInShouldWork() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where linkman in DEFAULT_LINKMAN or UPDATED_LINKMAN
        defaultDCompanyShouldBeFound("linkman.in=" + DEFAULT_LINKMAN + "," + UPDATED_LINKMAN);

        // Get all the dCompanyList where linkman equals to UPDATED_LINKMAN
        defaultDCompanyShouldNotBeFound("linkman.in=" + UPDATED_LINKMAN);
    }

    @Test
    @Transactional
    void getAllDCompaniesByLinkmanIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where linkman is not null
        defaultDCompanyShouldBeFound("linkman.specified=true");

        // Get all the dCompanyList where linkman is null
        defaultDCompanyShouldNotBeFound("linkman.specified=false");
    }

    @Test
    @Transactional
    void getAllDCompaniesByLinkmanContainsSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where linkman contains DEFAULT_LINKMAN
        defaultDCompanyShouldBeFound("linkman.contains=" + DEFAULT_LINKMAN);

        // Get all the dCompanyList where linkman contains UPDATED_LINKMAN
        defaultDCompanyShouldNotBeFound("linkman.contains=" + UPDATED_LINKMAN);
    }

    @Test
    @Transactional
    void getAllDCompaniesByLinkmanNotContainsSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where linkman does not contain DEFAULT_LINKMAN
        defaultDCompanyShouldNotBeFound("linkman.doesNotContain=" + DEFAULT_LINKMAN);

        // Get all the dCompanyList where linkman does not contain UPDATED_LINKMAN
        defaultDCompanyShouldBeFound("linkman.doesNotContain=" + UPDATED_LINKMAN);
    }

    @Test
    @Transactional
    void getAllDCompaniesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where phone equals to DEFAULT_PHONE
        defaultDCompanyShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the dCompanyList where phone equals to UPDATED_PHONE
        defaultDCompanyShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllDCompaniesByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where phone not equals to DEFAULT_PHONE
        defaultDCompanyShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the dCompanyList where phone not equals to UPDATED_PHONE
        defaultDCompanyShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllDCompaniesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultDCompanyShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the dCompanyList where phone equals to UPDATED_PHONE
        defaultDCompanyShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllDCompaniesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where phone is not null
        defaultDCompanyShouldBeFound("phone.specified=true");

        // Get all the dCompanyList where phone is null
        defaultDCompanyShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllDCompaniesByPhoneContainsSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where phone contains DEFAULT_PHONE
        defaultDCompanyShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the dCompanyList where phone contains UPDATED_PHONE
        defaultDCompanyShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllDCompaniesByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where phone does not contain DEFAULT_PHONE
        defaultDCompanyShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the dCompanyList where phone does not contain UPDATED_PHONE
        defaultDCompanyShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllDCompaniesByAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where address equals to DEFAULT_ADDRESS
        defaultDCompanyShouldBeFound("address.equals=" + DEFAULT_ADDRESS);

        // Get all the dCompanyList where address equals to UPDATED_ADDRESS
        defaultDCompanyShouldNotBeFound("address.equals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllDCompaniesByAddressIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where address not equals to DEFAULT_ADDRESS
        defaultDCompanyShouldNotBeFound("address.notEquals=" + DEFAULT_ADDRESS);

        // Get all the dCompanyList where address not equals to UPDATED_ADDRESS
        defaultDCompanyShouldBeFound("address.notEquals=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllDCompaniesByAddressIsInShouldWork() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where address in DEFAULT_ADDRESS or UPDATED_ADDRESS
        defaultDCompanyShouldBeFound("address.in=" + DEFAULT_ADDRESS + "," + UPDATED_ADDRESS);

        // Get all the dCompanyList where address equals to UPDATED_ADDRESS
        defaultDCompanyShouldNotBeFound("address.in=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllDCompaniesByAddressIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where address is not null
        defaultDCompanyShouldBeFound("address.specified=true");

        // Get all the dCompanyList where address is null
        defaultDCompanyShouldNotBeFound("address.specified=false");
    }

    @Test
    @Transactional
    void getAllDCompaniesByAddressContainsSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where address contains DEFAULT_ADDRESS
        defaultDCompanyShouldBeFound("address.contains=" + DEFAULT_ADDRESS);

        // Get all the dCompanyList where address contains UPDATED_ADDRESS
        defaultDCompanyShouldNotBeFound("address.contains=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllDCompaniesByAddressNotContainsSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where address does not contain DEFAULT_ADDRESS
        defaultDCompanyShouldNotBeFound("address.doesNotContain=" + DEFAULT_ADDRESS);

        // Get all the dCompanyList where address does not contain UPDATED_ADDRESS
        defaultDCompanyShouldBeFound("address.doesNotContain=" + UPDATED_ADDRESS);
    }

    @Test
    @Transactional
    void getAllDCompaniesByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where remark equals to DEFAULT_REMARK
        defaultDCompanyShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the dCompanyList where remark equals to UPDATED_REMARK
        defaultDCompanyShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDCompaniesByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where remark not equals to DEFAULT_REMARK
        defaultDCompanyShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the dCompanyList where remark not equals to UPDATED_REMARK
        defaultDCompanyShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDCompaniesByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultDCompanyShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the dCompanyList where remark equals to UPDATED_REMARK
        defaultDCompanyShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDCompaniesByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where remark is not null
        defaultDCompanyShouldBeFound("remark.specified=true");

        // Get all the dCompanyList where remark is null
        defaultDCompanyShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllDCompaniesByRemarkContainsSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where remark contains DEFAULT_REMARK
        defaultDCompanyShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the dCompanyList where remark contains UPDATED_REMARK
        defaultDCompanyShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDCompaniesByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where remark does not contain DEFAULT_REMARK
        defaultDCompanyShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the dCompanyList where remark does not contain UPDATED_REMARK
        defaultDCompanyShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDCompaniesByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where fax equals to DEFAULT_FAX
        defaultDCompanyShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the dCompanyList where fax equals to UPDATED_FAX
        defaultDCompanyShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllDCompaniesByFaxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where fax not equals to DEFAULT_FAX
        defaultDCompanyShouldNotBeFound("fax.notEquals=" + DEFAULT_FAX);

        // Get all the dCompanyList where fax not equals to UPDATED_FAX
        defaultDCompanyShouldBeFound("fax.notEquals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllDCompaniesByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultDCompanyShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the dCompanyList where fax equals to UPDATED_FAX
        defaultDCompanyShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllDCompaniesByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where fax is not null
        defaultDCompanyShouldBeFound("fax.specified=true");

        // Get all the dCompanyList where fax is null
        defaultDCompanyShouldNotBeFound("fax.specified=false");
    }

    @Test
    @Transactional
    void getAllDCompaniesByFaxContainsSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where fax contains DEFAULT_FAX
        defaultDCompanyShouldBeFound("fax.contains=" + DEFAULT_FAX);

        // Get all the dCompanyList where fax contains UPDATED_FAX
        defaultDCompanyShouldNotBeFound("fax.contains=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    void getAllDCompaniesByFaxNotContainsSomething() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        // Get all the dCompanyList where fax does not contain DEFAULT_FAX
        defaultDCompanyShouldNotBeFound("fax.doesNotContain=" + DEFAULT_FAX);

        // Get all the dCompanyList where fax does not contain UPDATED_FAX
        defaultDCompanyShouldBeFound("fax.doesNotContain=" + UPDATED_FAX);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDCompanyShouldBeFound(String filter) throws Exception {
        restDCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].linkman").value(hasItem(DEFAULT_LINKMAN)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)));

        // Check, that the count call also returns 1
        restDCompanyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDCompanyShouldNotBeFound(String filter) throws Exception {
        restDCompanyMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDCompanyMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDCompany() throws Exception {
        // Get the dCompany
        restDCompanyMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDCompany() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        int databaseSizeBeforeUpdate = dCompanyRepository.findAll().size();

        // Update the dCompany
        DCompany updatedDCompany = dCompanyRepository.findById(dCompany.getId()).get();
        // Disconnect from session so that the updates on updatedDCompany are not directly saved in db
        em.detach(updatedDCompany);
        updatedDCompany
            .company(UPDATED_COMPANY)
            .linkman(UPDATED_LINKMAN)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS)
            .remark(UPDATED_REMARK)
            .fax(UPDATED_FAX);
        DCompanyDTO dCompanyDTO = dCompanyMapper.toDto(updatedDCompany);

        restDCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dCompanyDTO))
            )
            .andExpect(status().isOk());

        // Validate the DCompany in the database
        List<DCompany> dCompanyList = dCompanyRepository.findAll();
        assertThat(dCompanyList).hasSize(databaseSizeBeforeUpdate);
        DCompany testDCompany = dCompanyList.get(dCompanyList.size() - 1);
        assertThat(testDCompany.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testDCompany.getLinkman()).isEqualTo(UPDATED_LINKMAN);
        assertThat(testDCompany.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testDCompany.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDCompany.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testDCompany.getFax()).isEqualTo(UPDATED_FAX);

        // Validate the DCompany in Elasticsearch
        verify(mockDCompanySearchRepository).save(testDCompany);
    }

    @Test
    @Transactional
    void putNonExistingDCompany() throws Exception {
        int databaseSizeBeforeUpdate = dCompanyRepository.findAll().size();
        dCompany.setId(count.incrementAndGet());

        // Create the DCompany
        DCompanyDTO dCompanyDTO = dCompanyMapper.toDto(dCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dCompanyDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCompany in the database
        List<DCompany> dCompanyList = dCompanyRepository.findAll();
        assertThat(dCompanyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCompany in Elasticsearch
        verify(mockDCompanySearchRepository, times(0)).save(dCompany);
    }

    @Test
    @Transactional
    void putWithIdMismatchDCompany() throws Exception {
        int databaseSizeBeforeUpdate = dCompanyRepository.findAll().size();
        dCompany.setId(count.incrementAndGet());

        // Create the DCompany
        DCompanyDTO dCompanyDTO = dCompanyMapper.toDto(dCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCompanyMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCompany in the database
        List<DCompany> dCompanyList = dCompanyRepository.findAll();
        assertThat(dCompanyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCompany in Elasticsearch
        verify(mockDCompanySearchRepository, times(0)).save(dCompany);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDCompany() throws Exception {
        int databaseSizeBeforeUpdate = dCompanyRepository.findAll().size();
        dCompany.setId(count.incrementAndGet());

        // Create the DCompany
        DCompanyDTO dCompanyDTO = dCompanyMapper.toDto(dCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCompanyMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dCompanyDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DCompany in the database
        List<DCompany> dCompanyList = dCompanyRepository.findAll();
        assertThat(dCompanyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCompany in Elasticsearch
        verify(mockDCompanySearchRepository, times(0)).save(dCompany);
    }

    @Test
    @Transactional
    void partialUpdateDCompanyWithPatch() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        int databaseSizeBeforeUpdate = dCompanyRepository.findAll().size();

        // Update the dCompany using partial update
        DCompany partialUpdatedDCompany = new DCompany();
        partialUpdatedDCompany.setId(dCompany.getId());

        partialUpdatedDCompany.linkman(UPDATED_LINKMAN).address(UPDATED_ADDRESS).remark(UPDATED_REMARK);

        restDCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDCompany))
            )
            .andExpect(status().isOk());

        // Validate the DCompany in the database
        List<DCompany> dCompanyList = dCompanyRepository.findAll();
        assertThat(dCompanyList).hasSize(databaseSizeBeforeUpdate);
        DCompany testDCompany = dCompanyList.get(dCompanyList.size() - 1);
        assertThat(testDCompany.getCompany()).isEqualTo(DEFAULT_COMPANY);
        assertThat(testDCompany.getLinkman()).isEqualTo(UPDATED_LINKMAN);
        assertThat(testDCompany.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testDCompany.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDCompany.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testDCompany.getFax()).isEqualTo(DEFAULT_FAX);
    }

    @Test
    @Transactional
    void fullUpdateDCompanyWithPatch() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        int databaseSizeBeforeUpdate = dCompanyRepository.findAll().size();

        // Update the dCompany using partial update
        DCompany partialUpdatedDCompany = new DCompany();
        partialUpdatedDCompany.setId(dCompany.getId());

        partialUpdatedDCompany
            .company(UPDATED_COMPANY)
            .linkman(UPDATED_LINKMAN)
            .phone(UPDATED_PHONE)
            .address(UPDATED_ADDRESS)
            .remark(UPDATED_REMARK)
            .fax(UPDATED_FAX);

        restDCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDCompany.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDCompany))
            )
            .andExpect(status().isOk());

        // Validate the DCompany in the database
        List<DCompany> dCompanyList = dCompanyRepository.findAll();
        assertThat(dCompanyList).hasSize(databaseSizeBeforeUpdate);
        DCompany testDCompany = dCompanyList.get(dCompanyList.size() - 1);
        assertThat(testDCompany.getCompany()).isEqualTo(UPDATED_COMPANY);
        assertThat(testDCompany.getLinkman()).isEqualTo(UPDATED_LINKMAN);
        assertThat(testDCompany.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testDCompany.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testDCompany.getRemark()).isEqualTo(UPDATED_REMARK);
        assertThat(testDCompany.getFax()).isEqualTo(UPDATED_FAX);
    }

    @Test
    @Transactional
    void patchNonExistingDCompany() throws Exception {
        int databaseSizeBeforeUpdate = dCompanyRepository.findAll().size();
        dCompany.setId(count.incrementAndGet());

        // Create the DCompany
        DCompanyDTO dCompanyDTO = dCompanyMapper.toDto(dCompany);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dCompanyDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCompany in the database
        List<DCompany> dCompanyList = dCompanyRepository.findAll();
        assertThat(dCompanyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCompany in Elasticsearch
        verify(mockDCompanySearchRepository, times(0)).save(dCompany);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDCompany() throws Exception {
        int databaseSizeBeforeUpdate = dCompanyRepository.findAll().size();
        dCompany.setId(count.incrementAndGet());

        // Create the DCompany
        DCompanyDTO dCompanyDTO = dCompanyMapper.toDto(dCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dCompanyDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DCompany in the database
        List<DCompany> dCompanyList = dCompanyRepository.findAll();
        assertThat(dCompanyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCompany in Elasticsearch
        verify(mockDCompanySearchRepository, times(0)).save(dCompany);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDCompany() throws Exception {
        int databaseSizeBeforeUpdate = dCompanyRepository.findAll().size();
        dCompany.setId(count.incrementAndGet());

        // Create the DCompany
        DCompanyDTO dCompanyDTO = dCompanyMapper.toDto(dCompany);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDCompanyMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dCompanyDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DCompany in the database
        List<DCompany> dCompanyList = dCompanyRepository.findAll();
        assertThat(dCompanyList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DCompany in Elasticsearch
        verify(mockDCompanySearchRepository, times(0)).save(dCompany);
    }

    @Test
    @Transactional
    void deleteDCompany() throws Exception {
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);

        int databaseSizeBeforeDelete = dCompanyRepository.findAll().size();

        // Delete the dCompany
        restDCompanyMockMvc
            .perform(delete(ENTITY_API_URL_ID, dCompany.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DCompany> dCompanyList = dCompanyRepository.findAll();
        assertThat(dCompanyList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DCompany in Elasticsearch
        verify(mockDCompanySearchRepository, times(1)).deleteById(dCompany.getId());
    }

    @Test
    @Transactional
    void searchDCompany() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dCompanyRepository.saveAndFlush(dCompany);
        when(mockDCompanySearchRepository.search(queryStringQuery("id:" + dCompany.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dCompany), PageRequest.of(0, 1), 1));

        // Search the dCompany
        restDCompanyMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dCompany.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dCompany.getId().intValue())))
            .andExpect(jsonPath("$.[*].company").value(hasItem(DEFAULT_COMPANY)))
            .andExpect(jsonPath("$.[*].linkman").value(hasItem(DEFAULT_LINKMAN)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)));
    }
}
