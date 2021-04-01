package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.CzCzl3;
import ihotel.app.repository.CzCzl3Repository;
import ihotel.app.repository.search.CzCzl3SearchRepository;
import ihotel.app.service.criteria.CzCzl3Criteria;
import ihotel.app.service.dto.CzCzl3DTO;
import ihotel.app.service.mapper.CzCzl3Mapper;
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
 * Integration tests for the {@link CzCzl3Resource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CzCzl3ResourceIT {

    private static final Long DEFAULT_ZFS = 1L;
    private static final Long UPDATED_ZFS = 2L;
    private static final Long SMALLER_ZFS = 1L - 1L;

    private static final BigDecimal DEFAULT_KFS = new BigDecimal(1);
    private static final BigDecimal UPDATED_KFS = new BigDecimal(2);
    private static final BigDecimal SMALLER_KFS = new BigDecimal(1 - 1);

    private static final String DEFAULT_PROTOCOLN = "AAAAAAAAAA";
    private static final String UPDATED_PROTOCOLN = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMTYPE = "AAAAAAAAAA";
    private static final String UPDATED_ROOMTYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_SL = 1L;
    private static final Long UPDATED_SL = 2L;
    private static final Long SMALLER_SL = 1L - 1L;

    private static final String ENTITY_API_URL = "/api/cz-czl-3-s";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/cz-czl-3-s";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CzCzl3Repository czCzl3Repository;

    @Autowired
    private CzCzl3Mapper czCzl3Mapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.CzCzl3SearchRepositoryMockConfiguration
     */
    @Autowired
    private CzCzl3SearchRepository mockCzCzl3SearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCzCzl3MockMvc;

    private CzCzl3 czCzl3;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CzCzl3 createEntity(EntityManager em) {
        CzCzl3 czCzl3 = new CzCzl3()
            .zfs(DEFAULT_ZFS)
            .kfs(DEFAULT_KFS)
            .protocoln(DEFAULT_PROTOCOLN)
            .roomtype(DEFAULT_ROOMTYPE)
            .sl(DEFAULT_SL);
        return czCzl3;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CzCzl3 createUpdatedEntity(EntityManager em) {
        CzCzl3 czCzl3 = new CzCzl3()
            .zfs(UPDATED_ZFS)
            .kfs(UPDATED_KFS)
            .protocoln(UPDATED_PROTOCOLN)
            .roomtype(UPDATED_ROOMTYPE)
            .sl(UPDATED_SL);
        return czCzl3;
    }

    @BeforeEach
    public void initTest() {
        czCzl3 = createEntity(em);
    }

    @Test
    @Transactional
    void createCzCzl3() throws Exception {
        int databaseSizeBeforeCreate = czCzl3Repository.findAll().size();
        // Create the CzCzl3
        CzCzl3DTO czCzl3DTO = czCzl3Mapper.toDto(czCzl3);
        restCzCzl3MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czCzl3DTO)))
            .andExpect(status().isCreated());

        // Validate the CzCzl3 in the database
        List<CzCzl3> czCzl3List = czCzl3Repository.findAll();
        assertThat(czCzl3List).hasSize(databaseSizeBeforeCreate + 1);
        CzCzl3 testCzCzl3 = czCzl3List.get(czCzl3List.size() - 1);
        assertThat(testCzCzl3.getZfs()).isEqualTo(DEFAULT_ZFS);
        assertThat(testCzCzl3.getKfs()).isEqualByComparingTo(DEFAULT_KFS);
        assertThat(testCzCzl3.getProtocoln()).isEqualTo(DEFAULT_PROTOCOLN);
        assertThat(testCzCzl3.getRoomtype()).isEqualTo(DEFAULT_ROOMTYPE);
        assertThat(testCzCzl3.getSl()).isEqualTo(DEFAULT_SL);

        // Validate the CzCzl3 in Elasticsearch
        verify(mockCzCzl3SearchRepository, times(1)).save(testCzCzl3);
    }

    @Test
    @Transactional
    void createCzCzl3WithExistingId() throws Exception {
        // Create the CzCzl3 with an existing ID
        czCzl3.setId(1L);
        CzCzl3DTO czCzl3DTO = czCzl3Mapper.toDto(czCzl3);

        int databaseSizeBeforeCreate = czCzl3Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCzCzl3MockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czCzl3DTO)))
            .andExpect(status().isBadRequest());

        // Validate the CzCzl3 in the database
        List<CzCzl3> czCzl3List = czCzl3Repository.findAll();
        assertThat(czCzl3List).hasSize(databaseSizeBeforeCreate);

        // Validate the CzCzl3 in Elasticsearch
        verify(mockCzCzl3SearchRepository, times(0)).save(czCzl3);
    }

    @Test
    @Transactional
    void getAllCzCzl3s() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List
        restCzCzl3MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(czCzl3.getId().intValue())))
            .andExpect(jsonPath("$.[*].zfs").value(hasItem(DEFAULT_ZFS.intValue())))
            .andExpect(jsonPath("$.[*].kfs").value(hasItem(sameNumber(DEFAULT_KFS))))
            .andExpect(jsonPath("$.[*].protocoln").value(hasItem(DEFAULT_PROTOCOLN)))
            .andExpect(jsonPath("$.[*].roomtype").value(hasItem(DEFAULT_ROOMTYPE)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(DEFAULT_SL.intValue())));
    }

    @Test
    @Transactional
    void getCzCzl3() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get the czCzl3
        restCzCzl3MockMvc
            .perform(get(ENTITY_API_URL_ID, czCzl3.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(czCzl3.getId().intValue()))
            .andExpect(jsonPath("$.zfs").value(DEFAULT_ZFS.intValue()))
            .andExpect(jsonPath("$.kfs").value(sameNumber(DEFAULT_KFS)))
            .andExpect(jsonPath("$.protocoln").value(DEFAULT_PROTOCOLN))
            .andExpect(jsonPath("$.roomtype").value(DEFAULT_ROOMTYPE))
            .andExpect(jsonPath("$.sl").value(DEFAULT_SL.intValue()));
    }

    @Test
    @Transactional
    void getCzCzl3sByIdFiltering() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        Long id = czCzl3.getId();

        defaultCzCzl3ShouldBeFound("id.equals=" + id);
        defaultCzCzl3ShouldNotBeFound("id.notEquals=" + id);

        defaultCzCzl3ShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCzCzl3ShouldNotBeFound("id.greaterThan=" + id);

        defaultCzCzl3ShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCzCzl3ShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByZfsIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where zfs equals to DEFAULT_ZFS
        defaultCzCzl3ShouldBeFound("zfs.equals=" + DEFAULT_ZFS);

        // Get all the czCzl3List where zfs equals to UPDATED_ZFS
        defaultCzCzl3ShouldNotBeFound("zfs.equals=" + UPDATED_ZFS);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByZfsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where zfs not equals to DEFAULT_ZFS
        defaultCzCzl3ShouldNotBeFound("zfs.notEquals=" + DEFAULT_ZFS);

        // Get all the czCzl3List where zfs not equals to UPDATED_ZFS
        defaultCzCzl3ShouldBeFound("zfs.notEquals=" + UPDATED_ZFS);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByZfsIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where zfs in DEFAULT_ZFS or UPDATED_ZFS
        defaultCzCzl3ShouldBeFound("zfs.in=" + DEFAULT_ZFS + "," + UPDATED_ZFS);

        // Get all the czCzl3List where zfs equals to UPDATED_ZFS
        defaultCzCzl3ShouldNotBeFound("zfs.in=" + UPDATED_ZFS);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByZfsIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where zfs is not null
        defaultCzCzl3ShouldBeFound("zfs.specified=true");

        // Get all the czCzl3List where zfs is null
        defaultCzCzl3ShouldNotBeFound("zfs.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl3sByZfsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where zfs is greater than or equal to DEFAULT_ZFS
        defaultCzCzl3ShouldBeFound("zfs.greaterThanOrEqual=" + DEFAULT_ZFS);

        // Get all the czCzl3List where zfs is greater than or equal to UPDATED_ZFS
        defaultCzCzl3ShouldNotBeFound("zfs.greaterThanOrEqual=" + UPDATED_ZFS);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByZfsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where zfs is less than or equal to DEFAULT_ZFS
        defaultCzCzl3ShouldBeFound("zfs.lessThanOrEqual=" + DEFAULT_ZFS);

        // Get all the czCzl3List where zfs is less than or equal to SMALLER_ZFS
        defaultCzCzl3ShouldNotBeFound("zfs.lessThanOrEqual=" + SMALLER_ZFS);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByZfsIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where zfs is less than DEFAULT_ZFS
        defaultCzCzl3ShouldNotBeFound("zfs.lessThan=" + DEFAULT_ZFS);

        // Get all the czCzl3List where zfs is less than UPDATED_ZFS
        defaultCzCzl3ShouldBeFound("zfs.lessThan=" + UPDATED_ZFS);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByZfsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where zfs is greater than DEFAULT_ZFS
        defaultCzCzl3ShouldNotBeFound("zfs.greaterThan=" + DEFAULT_ZFS);

        // Get all the czCzl3List where zfs is greater than SMALLER_ZFS
        defaultCzCzl3ShouldBeFound("zfs.greaterThan=" + SMALLER_ZFS);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByKfsIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where kfs equals to DEFAULT_KFS
        defaultCzCzl3ShouldBeFound("kfs.equals=" + DEFAULT_KFS);

        // Get all the czCzl3List where kfs equals to UPDATED_KFS
        defaultCzCzl3ShouldNotBeFound("kfs.equals=" + UPDATED_KFS);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByKfsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where kfs not equals to DEFAULT_KFS
        defaultCzCzl3ShouldNotBeFound("kfs.notEquals=" + DEFAULT_KFS);

        // Get all the czCzl3List where kfs not equals to UPDATED_KFS
        defaultCzCzl3ShouldBeFound("kfs.notEquals=" + UPDATED_KFS);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByKfsIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where kfs in DEFAULT_KFS or UPDATED_KFS
        defaultCzCzl3ShouldBeFound("kfs.in=" + DEFAULT_KFS + "," + UPDATED_KFS);

        // Get all the czCzl3List where kfs equals to UPDATED_KFS
        defaultCzCzl3ShouldNotBeFound("kfs.in=" + UPDATED_KFS);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByKfsIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where kfs is not null
        defaultCzCzl3ShouldBeFound("kfs.specified=true");

        // Get all the czCzl3List where kfs is null
        defaultCzCzl3ShouldNotBeFound("kfs.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl3sByKfsIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where kfs is greater than or equal to DEFAULT_KFS
        defaultCzCzl3ShouldBeFound("kfs.greaterThanOrEqual=" + DEFAULT_KFS);

        // Get all the czCzl3List where kfs is greater than or equal to UPDATED_KFS
        defaultCzCzl3ShouldNotBeFound("kfs.greaterThanOrEqual=" + UPDATED_KFS);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByKfsIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where kfs is less than or equal to DEFAULT_KFS
        defaultCzCzl3ShouldBeFound("kfs.lessThanOrEqual=" + DEFAULT_KFS);

        // Get all the czCzl3List where kfs is less than or equal to SMALLER_KFS
        defaultCzCzl3ShouldNotBeFound("kfs.lessThanOrEqual=" + SMALLER_KFS);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByKfsIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where kfs is less than DEFAULT_KFS
        defaultCzCzl3ShouldNotBeFound("kfs.lessThan=" + DEFAULT_KFS);

        // Get all the czCzl3List where kfs is less than UPDATED_KFS
        defaultCzCzl3ShouldBeFound("kfs.lessThan=" + UPDATED_KFS);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByKfsIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where kfs is greater than DEFAULT_KFS
        defaultCzCzl3ShouldNotBeFound("kfs.greaterThan=" + DEFAULT_KFS);

        // Get all the czCzl3List where kfs is greater than SMALLER_KFS
        defaultCzCzl3ShouldBeFound("kfs.greaterThan=" + SMALLER_KFS);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByProtocolnIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where protocoln equals to DEFAULT_PROTOCOLN
        defaultCzCzl3ShouldBeFound("protocoln.equals=" + DEFAULT_PROTOCOLN);

        // Get all the czCzl3List where protocoln equals to UPDATED_PROTOCOLN
        defaultCzCzl3ShouldNotBeFound("protocoln.equals=" + UPDATED_PROTOCOLN);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByProtocolnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where protocoln not equals to DEFAULT_PROTOCOLN
        defaultCzCzl3ShouldNotBeFound("protocoln.notEquals=" + DEFAULT_PROTOCOLN);

        // Get all the czCzl3List where protocoln not equals to UPDATED_PROTOCOLN
        defaultCzCzl3ShouldBeFound("protocoln.notEquals=" + UPDATED_PROTOCOLN);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByProtocolnIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where protocoln in DEFAULT_PROTOCOLN or UPDATED_PROTOCOLN
        defaultCzCzl3ShouldBeFound("protocoln.in=" + DEFAULT_PROTOCOLN + "," + UPDATED_PROTOCOLN);

        // Get all the czCzl3List where protocoln equals to UPDATED_PROTOCOLN
        defaultCzCzl3ShouldNotBeFound("protocoln.in=" + UPDATED_PROTOCOLN);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByProtocolnIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where protocoln is not null
        defaultCzCzl3ShouldBeFound("protocoln.specified=true");

        // Get all the czCzl3List where protocoln is null
        defaultCzCzl3ShouldNotBeFound("protocoln.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl3sByProtocolnContainsSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where protocoln contains DEFAULT_PROTOCOLN
        defaultCzCzl3ShouldBeFound("protocoln.contains=" + DEFAULT_PROTOCOLN);

        // Get all the czCzl3List where protocoln contains UPDATED_PROTOCOLN
        defaultCzCzl3ShouldNotBeFound("protocoln.contains=" + UPDATED_PROTOCOLN);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByProtocolnNotContainsSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where protocoln does not contain DEFAULT_PROTOCOLN
        defaultCzCzl3ShouldNotBeFound("protocoln.doesNotContain=" + DEFAULT_PROTOCOLN);

        // Get all the czCzl3List where protocoln does not contain UPDATED_PROTOCOLN
        defaultCzCzl3ShouldBeFound("protocoln.doesNotContain=" + UPDATED_PROTOCOLN);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByRoomtypeIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where roomtype equals to DEFAULT_ROOMTYPE
        defaultCzCzl3ShouldBeFound("roomtype.equals=" + DEFAULT_ROOMTYPE);

        // Get all the czCzl3List where roomtype equals to UPDATED_ROOMTYPE
        defaultCzCzl3ShouldNotBeFound("roomtype.equals=" + UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByRoomtypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where roomtype not equals to DEFAULT_ROOMTYPE
        defaultCzCzl3ShouldNotBeFound("roomtype.notEquals=" + DEFAULT_ROOMTYPE);

        // Get all the czCzl3List where roomtype not equals to UPDATED_ROOMTYPE
        defaultCzCzl3ShouldBeFound("roomtype.notEquals=" + UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByRoomtypeIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where roomtype in DEFAULT_ROOMTYPE or UPDATED_ROOMTYPE
        defaultCzCzl3ShouldBeFound("roomtype.in=" + DEFAULT_ROOMTYPE + "," + UPDATED_ROOMTYPE);

        // Get all the czCzl3List where roomtype equals to UPDATED_ROOMTYPE
        defaultCzCzl3ShouldNotBeFound("roomtype.in=" + UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByRoomtypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where roomtype is not null
        defaultCzCzl3ShouldBeFound("roomtype.specified=true");

        // Get all the czCzl3List where roomtype is null
        defaultCzCzl3ShouldNotBeFound("roomtype.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl3sByRoomtypeContainsSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where roomtype contains DEFAULT_ROOMTYPE
        defaultCzCzl3ShouldBeFound("roomtype.contains=" + DEFAULT_ROOMTYPE);

        // Get all the czCzl3List where roomtype contains UPDATED_ROOMTYPE
        defaultCzCzl3ShouldNotBeFound("roomtype.contains=" + UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void getAllCzCzl3sByRoomtypeNotContainsSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where roomtype does not contain DEFAULT_ROOMTYPE
        defaultCzCzl3ShouldNotBeFound("roomtype.doesNotContain=" + DEFAULT_ROOMTYPE);

        // Get all the czCzl3List where roomtype does not contain UPDATED_ROOMTYPE
        defaultCzCzl3ShouldBeFound("roomtype.doesNotContain=" + UPDATED_ROOMTYPE);
    }

    @Test
    @Transactional
    void getAllCzCzl3sBySlIsEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where sl equals to DEFAULT_SL
        defaultCzCzl3ShouldBeFound("sl.equals=" + DEFAULT_SL);

        // Get all the czCzl3List where sl equals to UPDATED_SL
        defaultCzCzl3ShouldNotBeFound("sl.equals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllCzCzl3sBySlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where sl not equals to DEFAULT_SL
        defaultCzCzl3ShouldNotBeFound("sl.notEquals=" + DEFAULT_SL);

        // Get all the czCzl3List where sl not equals to UPDATED_SL
        defaultCzCzl3ShouldBeFound("sl.notEquals=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllCzCzl3sBySlIsInShouldWork() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where sl in DEFAULT_SL or UPDATED_SL
        defaultCzCzl3ShouldBeFound("sl.in=" + DEFAULT_SL + "," + UPDATED_SL);

        // Get all the czCzl3List where sl equals to UPDATED_SL
        defaultCzCzl3ShouldNotBeFound("sl.in=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllCzCzl3sBySlIsNullOrNotNull() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where sl is not null
        defaultCzCzl3ShouldBeFound("sl.specified=true");

        // Get all the czCzl3List where sl is null
        defaultCzCzl3ShouldNotBeFound("sl.specified=false");
    }

    @Test
    @Transactional
    void getAllCzCzl3sBySlIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where sl is greater than or equal to DEFAULT_SL
        defaultCzCzl3ShouldBeFound("sl.greaterThanOrEqual=" + DEFAULT_SL);

        // Get all the czCzl3List where sl is greater than or equal to UPDATED_SL
        defaultCzCzl3ShouldNotBeFound("sl.greaterThanOrEqual=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllCzCzl3sBySlIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where sl is less than or equal to DEFAULT_SL
        defaultCzCzl3ShouldBeFound("sl.lessThanOrEqual=" + DEFAULT_SL);

        // Get all the czCzl3List where sl is less than or equal to SMALLER_SL
        defaultCzCzl3ShouldNotBeFound("sl.lessThanOrEqual=" + SMALLER_SL);
    }

    @Test
    @Transactional
    void getAllCzCzl3sBySlIsLessThanSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where sl is less than DEFAULT_SL
        defaultCzCzl3ShouldNotBeFound("sl.lessThan=" + DEFAULT_SL);

        // Get all the czCzl3List where sl is less than UPDATED_SL
        defaultCzCzl3ShouldBeFound("sl.lessThan=" + UPDATED_SL);
    }

    @Test
    @Transactional
    void getAllCzCzl3sBySlIsGreaterThanSomething() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        // Get all the czCzl3List where sl is greater than DEFAULT_SL
        defaultCzCzl3ShouldNotBeFound("sl.greaterThan=" + DEFAULT_SL);

        // Get all the czCzl3List where sl is greater than SMALLER_SL
        defaultCzCzl3ShouldBeFound("sl.greaterThan=" + SMALLER_SL);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCzCzl3ShouldBeFound(String filter) throws Exception {
        restCzCzl3MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(czCzl3.getId().intValue())))
            .andExpect(jsonPath("$.[*].zfs").value(hasItem(DEFAULT_ZFS.intValue())))
            .andExpect(jsonPath("$.[*].kfs").value(hasItem(sameNumber(DEFAULT_KFS))))
            .andExpect(jsonPath("$.[*].protocoln").value(hasItem(DEFAULT_PROTOCOLN)))
            .andExpect(jsonPath("$.[*].roomtype").value(hasItem(DEFAULT_ROOMTYPE)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(DEFAULT_SL.intValue())));

        // Check, that the count call also returns 1
        restCzCzl3MockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCzCzl3ShouldNotBeFound(String filter) throws Exception {
        restCzCzl3MockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCzCzl3MockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCzCzl3() throws Exception {
        // Get the czCzl3
        restCzCzl3MockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCzCzl3() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        int databaseSizeBeforeUpdate = czCzl3Repository.findAll().size();

        // Update the czCzl3
        CzCzl3 updatedCzCzl3 = czCzl3Repository.findById(czCzl3.getId()).get();
        // Disconnect from session so that the updates on updatedCzCzl3 are not directly saved in db
        em.detach(updatedCzCzl3);
        updatedCzCzl3.zfs(UPDATED_ZFS).kfs(UPDATED_KFS).protocoln(UPDATED_PROTOCOLN).roomtype(UPDATED_ROOMTYPE).sl(UPDATED_SL);
        CzCzl3DTO czCzl3DTO = czCzl3Mapper.toDto(updatedCzCzl3);

        restCzCzl3MockMvc
            .perform(
                put(ENTITY_API_URL_ID, czCzl3DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(czCzl3DTO))
            )
            .andExpect(status().isOk());

        // Validate the CzCzl3 in the database
        List<CzCzl3> czCzl3List = czCzl3Repository.findAll();
        assertThat(czCzl3List).hasSize(databaseSizeBeforeUpdate);
        CzCzl3 testCzCzl3 = czCzl3List.get(czCzl3List.size() - 1);
        assertThat(testCzCzl3.getZfs()).isEqualTo(UPDATED_ZFS);
        assertThat(testCzCzl3.getKfs()).isEqualTo(UPDATED_KFS);
        assertThat(testCzCzl3.getProtocoln()).isEqualTo(UPDATED_PROTOCOLN);
        assertThat(testCzCzl3.getRoomtype()).isEqualTo(UPDATED_ROOMTYPE);
        assertThat(testCzCzl3.getSl()).isEqualTo(UPDATED_SL);

        // Validate the CzCzl3 in Elasticsearch
        verify(mockCzCzl3SearchRepository).save(testCzCzl3);
    }

    @Test
    @Transactional
    void putNonExistingCzCzl3() throws Exception {
        int databaseSizeBeforeUpdate = czCzl3Repository.findAll().size();
        czCzl3.setId(count.incrementAndGet());

        // Create the CzCzl3
        CzCzl3DTO czCzl3DTO = czCzl3Mapper.toDto(czCzl3);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCzCzl3MockMvc
            .perform(
                put(ENTITY_API_URL_ID, czCzl3DTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(czCzl3DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzCzl3 in the database
        List<CzCzl3> czCzl3List = czCzl3Repository.findAll();
        assertThat(czCzl3List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzCzl3 in Elasticsearch
        verify(mockCzCzl3SearchRepository, times(0)).save(czCzl3);
    }

    @Test
    @Transactional
    void putWithIdMismatchCzCzl3() throws Exception {
        int databaseSizeBeforeUpdate = czCzl3Repository.findAll().size();
        czCzl3.setId(count.incrementAndGet());

        // Create the CzCzl3
        CzCzl3DTO czCzl3DTO = czCzl3Mapper.toDto(czCzl3);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzCzl3MockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(czCzl3DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzCzl3 in the database
        List<CzCzl3> czCzl3List = czCzl3Repository.findAll();
        assertThat(czCzl3List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzCzl3 in Elasticsearch
        verify(mockCzCzl3SearchRepository, times(0)).save(czCzl3);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCzCzl3() throws Exception {
        int databaseSizeBeforeUpdate = czCzl3Repository.findAll().size();
        czCzl3.setId(count.incrementAndGet());

        // Create the CzCzl3
        CzCzl3DTO czCzl3DTO = czCzl3Mapper.toDto(czCzl3);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzCzl3MockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(czCzl3DTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CzCzl3 in the database
        List<CzCzl3> czCzl3List = czCzl3Repository.findAll();
        assertThat(czCzl3List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzCzl3 in Elasticsearch
        verify(mockCzCzl3SearchRepository, times(0)).save(czCzl3);
    }

    @Test
    @Transactional
    void partialUpdateCzCzl3WithPatch() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        int databaseSizeBeforeUpdate = czCzl3Repository.findAll().size();

        // Update the czCzl3 using partial update
        CzCzl3 partialUpdatedCzCzl3 = new CzCzl3();
        partialUpdatedCzCzl3.setId(czCzl3.getId());

        partialUpdatedCzCzl3.zfs(UPDATED_ZFS).kfs(UPDATED_KFS);

        restCzCzl3MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCzCzl3.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCzCzl3))
            )
            .andExpect(status().isOk());

        // Validate the CzCzl3 in the database
        List<CzCzl3> czCzl3List = czCzl3Repository.findAll();
        assertThat(czCzl3List).hasSize(databaseSizeBeforeUpdate);
        CzCzl3 testCzCzl3 = czCzl3List.get(czCzl3List.size() - 1);
        assertThat(testCzCzl3.getZfs()).isEqualTo(UPDATED_ZFS);
        assertThat(testCzCzl3.getKfs()).isEqualByComparingTo(UPDATED_KFS);
        assertThat(testCzCzl3.getProtocoln()).isEqualTo(DEFAULT_PROTOCOLN);
        assertThat(testCzCzl3.getRoomtype()).isEqualTo(DEFAULT_ROOMTYPE);
        assertThat(testCzCzl3.getSl()).isEqualTo(DEFAULT_SL);
    }

    @Test
    @Transactional
    void fullUpdateCzCzl3WithPatch() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        int databaseSizeBeforeUpdate = czCzl3Repository.findAll().size();

        // Update the czCzl3 using partial update
        CzCzl3 partialUpdatedCzCzl3 = new CzCzl3();
        partialUpdatedCzCzl3.setId(czCzl3.getId());

        partialUpdatedCzCzl3.zfs(UPDATED_ZFS).kfs(UPDATED_KFS).protocoln(UPDATED_PROTOCOLN).roomtype(UPDATED_ROOMTYPE).sl(UPDATED_SL);

        restCzCzl3MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCzCzl3.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCzCzl3))
            )
            .andExpect(status().isOk());

        // Validate the CzCzl3 in the database
        List<CzCzl3> czCzl3List = czCzl3Repository.findAll();
        assertThat(czCzl3List).hasSize(databaseSizeBeforeUpdate);
        CzCzl3 testCzCzl3 = czCzl3List.get(czCzl3List.size() - 1);
        assertThat(testCzCzl3.getZfs()).isEqualTo(UPDATED_ZFS);
        assertThat(testCzCzl3.getKfs()).isEqualByComparingTo(UPDATED_KFS);
        assertThat(testCzCzl3.getProtocoln()).isEqualTo(UPDATED_PROTOCOLN);
        assertThat(testCzCzl3.getRoomtype()).isEqualTo(UPDATED_ROOMTYPE);
        assertThat(testCzCzl3.getSl()).isEqualTo(UPDATED_SL);
    }

    @Test
    @Transactional
    void patchNonExistingCzCzl3() throws Exception {
        int databaseSizeBeforeUpdate = czCzl3Repository.findAll().size();
        czCzl3.setId(count.incrementAndGet());

        // Create the CzCzl3
        CzCzl3DTO czCzl3DTO = czCzl3Mapper.toDto(czCzl3);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCzCzl3MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, czCzl3DTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(czCzl3DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzCzl3 in the database
        List<CzCzl3> czCzl3List = czCzl3Repository.findAll();
        assertThat(czCzl3List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzCzl3 in Elasticsearch
        verify(mockCzCzl3SearchRepository, times(0)).save(czCzl3);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCzCzl3() throws Exception {
        int databaseSizeBeforeUpdate = czCzl3Repository.findAll().size();
        czCzl3.setId(count.incrementAndGet());

        // Create the CzCzl3
        CzCzl3DTO czCzl3DTO = czCzl3Mapper.toDto(czCzl3);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzCzl3MockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(czCzl3DTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CzCzl3 in the database
        List<CzCzl3> czCzl3List = czCzl3Repository.findAll();
        assertThat(czCzl3List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzCzl3 in Elasticsearch
        verify(mockCzCzl3SearchRepository, times(0)).save(czCzl3);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCzCzl3() throws Exception {
        int databaseSizeBeforeUpdate = czCzl3Repository.findAll().size();
        czCzl3.setId(count.incrementAndGet());

        // Create the CzCzl3
        CzCzl3DTO czCzl3DTO = czCzl3Mapper.toDto(czCzl3);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCzCzl3MockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(czCzl3DTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CzCzl3 in the database
        List<CzCzl3> czCzl3List = czCzl3Repository.findAll();
        assertThat(czCzl3List).hasSize(databaseSizeBeforeUpdate);

        // Validate the CzCzl3 in Elasticsearch
        verify(mockCzCzl3SearchRepository, times(0)).save(czCzl3);
    }

    @Test
    @Transactional
    void deleteCzCzl3() throws Exception {
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);

        int databaseSizeBeforeDelete = czCzl3Repository.findAll().size();

        // Delete the czCzl3
        restCzCzl3MockMvc
            .perform(delete(ENTITY_API_URL_ID, czCzl3.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CzCzl3> czCzl3List = czCzl3Repository.findAll();
        assertThat(czCzl3List).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CzCzl3 in Elasticsearch
        verify(mockCzCzl3SearchRepository, times(1)).deleteById(czCzl3.getId());
    }

    @Test
    @Transactional
    void searchCzCzl3() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        czCzl3Repository.saveAndFlush(czCzl3);
        when(mockCzCzl3SearchRepository.search(queryStringQuery("id:" + czCzl3.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(czCzl3), PageRequest.of(0, 1), 1));

        // Search the czCzl3
        restCzCzl3MockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + czCzl3.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(czCzl3.getId().intValue())))
            .andExpect(jsonPath("$.[*].zfs").value(hasItem(DEFAULT_ZFS.intValue())))
            .andExpect(jsonPath("$.[*].kfs").value(hasItem(sameNumber(DEFAULT_KFS))))
            .andExpect(jsonPath("$.[*].protocoln").value(hasItem(DEFAULT_PROTOCOLN)))
            .andExpect(jsonPath("$.[*].roomtype").value(hasItem(DEFAULT_ROOMTYPE)))
            .andExpect(jsonPath("$.[*].sl").value(hasItem(DEFAULT_SL.intValue())));
    }
}
