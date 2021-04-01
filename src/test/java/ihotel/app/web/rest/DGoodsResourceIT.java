package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DGoods;
import ihotel.app.repository.DGoodsRepository;
import ihotel.app.repository.search.DGoodsSearchRepository;
import ihotel.app.service.criteria.DGoodsCriteria;
import ihotel.app.service.dto.DGoodsDTO;
import ihotel.app.service.mapper.DGoodsMapper;
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
 * Integration tests for the {@link DGoodsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DGoodsResourceIT {

    private static final Long DEFAULT_TYPEID = 1L;
    private static final Long UPDATED_TYPEID = 2L;
    private static final Long SMALLER_TYPEID = 1L - 1L;

    private static final String DEFAULT_GOODSNAME = "AAAAAAAAAA";
    private static final String UPDATED_GOODSNAME = "BBBBBBBBBB";

    private static final String DEFAULT_GOODSID = "AAAAAAAAAA";
    private static final String UPDATED_GOODSID = "BBBBBBBBBB";

    private static final String DEFAULT_GGXH = "AAAAAAAAAA";
    private static final String UPDATED_GGXH = "BBBBBBBBBB";

    private static final String DEFAULT_PYSJ = "AAAAAAAAAA";
    private static final String UPDATED_PYSJ = "BBBBBBBBBB";

    private static final String DEFAULT_WBSJ = "AAAAAAAAAA";
    private static final String UPDATED_WBSJ = "BBBBBBBBBB";

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_GCSL = new BigDecimal(1);
    private static final BigDecimal UPDATED_GCSL = new BigDecimal(2);
    private static final BigDecimal SMALLER_GCSL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DCSL = new BigDecimal(1);
    private static final BigDecimal UPDATED_DCSL = new BigDecimal(2);
    private static final BigDecimal SMALLER_DCSL = new BigDecimal(1 - 1);

    private static final String DEFAULT_REMARK = "AAAAAAAAAA";
    private static final String UPDATED_REMARK = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/d-goods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/d-goods";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DGoodsRepository dGoodsRepository;

    @Autowired
    private DGoodsMapper dGoodsMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DGoodsSearchRepositoryMockConfiguration
     */
    @Autowired
    private DGoodsSearchRepository mockDGoodsSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDGoodsMockMvc;

    private DGoods dGoods;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DGoods createEntity(EntityManager em) {
        DGoods dGoods = new DGoods()
            .typeid(DEFAULT_TYPEID)
            .goodsname(DEFAULT_GOODSNAME)
            .goodsid(DEFAULT_GOODSID)
            .ggxh(DEFAULT_GGXH)
            .pysj(DEFAULT_PYSJ)
            .wbsj(DEFAULT_WBSJ)
            .unit(DEFAULT_UNIT)
            .gcsl(DEFAULT_GCSL)
            .dcsl(DEFAULT_DCSL)
            .remark(DEFAULT_REMARK);
        return dGoods;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DGoods createUpdatedEntity(EntityManager em) {
        DGoods dGoods = new DGoods()
            .typeid(UPDATED_TYPEID)
            .goodsname(UPDATED_GOODSNAME)
            .goodsid(UPDATED_GOODSID)
            .ggxh(UPDATED_GGXH)
            .pysj(UPDATED_PYSJ)
            .wbsj(UPDATED_WBSJ)
            .unit(UPDATED_UNIT)
            .gcsl(UPDATED_GCSL)
            .dcsl(UPDATED_DCSL)
            .remark(UPDATED_REMARK);
        return dGoods;
    }

    @BeforeEach
    public void initTest() {
        dGoods = createEntity(em);
    }

    @Test
    @Transactional
    void createDGoods() throws Exception {
        int databaseSizeBeforeCreate = dGoodsRepository.findAll().size();
        // Create the DGoods
        DGoodsDTO dGoodsDTO = dGoodsMapper.toDto(dGoods);
        restDGoodsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dGoodsDTO)))
            .andExpect(status().isCreated());

        // Validate the DGoods in the database
        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeCreate + 1);
        DGoods testDGoods = dGoodsList.get(dGoodsList.size() - 1);
        assertThat(testDGoods.getTypeid()).isEqualTo(DEFAULT_TYPEID);
        assertThat(testDGoods.getGoodsname()).isEqualTo(DEFAULT_GOODSNAME);
        assertThat(testDGoods.getGoodsid()).isEqualTo(DEFAULT_GOODSID);
        assertThat(testDGoods.getGgxh()).isEqualTo(DEFAULT_GGXH);
        assertThat(testDGoods.getPysj()).isEqualTo(DEFAULT_PYSJ);
        assertThat(testDGoods.getWbsj()).isEqualTo(DEFAULT_WBSJ);
        assertThat(testDGoods.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testDGoods.getGcsl()).isEqualByComparingTo(DEFAULT_GCSL);
        assertThat(testDGoods.getDcsl()).isEqualByComparingTo(DEFAULT_DCSL);
        assertThat(testDGoods.getRemark()).isEqualTo(DEFAULT_REMARK);

        // Validate the DGoods in Elasticsearch
        verify(mockDGoodsSearchRepository, times(1)).save(testDGoods);
    }

    @Test
    @Transactional
    void createDGoodsWithExistingId() throws Exception {
        // Create the DGoods with an existing ID
        dGoods.setId(1L);
        DGoodsDTO dGoodsDTO = dGoodsMapper.toDto(dGoods);

        int databaseSizeBeforeCreate = dGoodsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDGoodsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dGoodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DGoods in the database
        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeCreate);

        // Validate the DGoods in Elasticsearch
        verify(mockDGoodsSearchRepository, times(0)).save(dGoods);
    }

    @Test
    @Transactional
    void checkTypeidIsRequired() throws Exception {
        int databaseSizeBeforeTest = dGoodsRepository.findAll().size();
        // set the field null
        dGoods.setTypeid(null);

        // Create the DGoods, which fails.
        DGoodsDTO dGoodsDTO = dGoodsMapper.toDto(dGoods);

        restDGoodsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dGoodsDTO)))
            .andExpect(status().isBadRequest());

        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGoodsnameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dGoodsRepository.findAll().size();
        // set the field null
        dGoods.setGoodsname(null);

        // Create the DGoods, which fails.
        DGoodsDTO dGoodsDTO = dGoodsMapper.toDto(dGoods);

        restDGoodsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dGoodsDTO)))
            .andExpect(status().isBadRequest());

        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkGoodsidIsRequired() throws Exception {
        int databaseSizeBeforeTest = dGoodsRepository.findAll().size();
        // set the field null
        dGoods.setGoodsid(null);

        // Create the DGoods, which fails.
        DGoodsDTO dGoodsDTO = dGoodsMapper.toDto(dGoods);

        restDGoodsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dGoodsDTO)))
            .andExpect(status().isBadRequest());

        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDGoods() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList
        restDGoodsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dGoods.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeid").value(hasItem(DEFAULT_TYPEID.intValue())))
            .andExpect(jsonPath("$.[*].goodsname").value(hasItem(DEFAULT_GOODSNAME)))
            .andExpect(jsonPath("$.[*].goodsid").value(hasItem(DEFAULT_GOODSID)))
            .andExpect(jsonPath("$.[*].ggxh").value(hasItem(DEFAULT_GGXH)))
            .andExpect(jsonPath("$.[*].pysj").value(hasItem(DEFAULT_PYSJ)))
            .andExpect(jsonPath("$.[*].wbsj").value(hasItem(DEFAULT_WBSJ)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].gcsl").value(hasItem(sameNumber(DEFAULT_GCSL))))
            .andExpect(jsonPath("$.[*].dcsl").value(hasItem(sameNumber(DEFAULT_DCSL))))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));
    }

    @Test
    @Transactional
    void getDGoods() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get the dGoods
        restDGoodsMockMvc
            .perform(get(ENTITY_API_URL_ID, dGoods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dGoods.getId().intValue()))
            .andExpect(jsonPath("$.typeid").value(DEFAULT_TYPEID.intValue()))
            .andExpect(jsonPath("$.goodsname").value(DEFAULT_GOODSNAME))
            .andExpect(jsonPath("$.goodsid").value(DEFAULT_GOODSID))
            .andExpect(jsonPath("$.ggxh").value(DEFAULT_GGXH))
            .andExpect(jsonPath("$.pysj").value(DEFAULT_PYSJ))
            .andExpect(jsonPath("$.wbsj").value(DEFAULT_WBSJ))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT))
            .andExpect(jsonPath("$.gcsl").value(sameNumber(DEFAULT_GCSL)))
            .andExpect(jsonPath("$.dcsl").value(sameNumber(DEFAULT_DCSL)))
            .andExpect(jsonPath("$.remark").value(DEFAULT_REMARK));
    }

    @Test
    @Transactional
    void getDGoodsByIdFiltering() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        Long id = dGoods.getId();

        defaultDGoodsShouldBeFound("id.equals=" + id);
        defaultDGoodsShouldNotBeFound("id.notEquals=" + id);

        defaultDGoodsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDGoodsShouldNotBeFound("id.greaterThan=" + id);

        defaultDGoodsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDGoodsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDGoodsByTypeidIsEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where typeid equals to DEFAULT_TYPEID
        defaultDGoodsShouldBeFound("typeid.equals=" + DEFAULT_TYPEID);

        // Get all the dGoodsList where typeid equals to UPDATED_TYPEID
        defaultDGoodsShouldNotBeFound("typeid.equals=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllDGoodsByTypeidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where typeid not equals to DEFAULT_TYPEID
        defaultDGoodsShouldNotBeFound("typeid.notEquals=" + DEFAULT_TYPEID);

        // Get all the dGoodsList where typeid not equals to UPDATED_TYPEID
        defaultDGoodsShouldBeFound("typeid.notEquals=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllDGoodsByTypeidIsInShouldWork() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where typeid in DEFAULT_TYPEID or UPDATED_TYPEID
        defaultDGoodsShouldBeFound("typeid.in=" + DEFAULT_TYPEID + "," + UPDATED_TYPEID);

        // Get all the dGoodsList where typeid equals to UPDATED_TYPEID
        defaultDGoodsShouldNotBeFound("typeid.in=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllDGoodsByTypeidIsNullOrNotNull() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where typeid is not null
        defaultDGoodsShouldBeFound("typeid.specified=true");

        // Get all the dGoodsList where typeid is null
        defaultDGoodsShouldNotBeFound("typeid.specified=false");
    }

    @Test
    @Transactional
    void getAllDGoodsByTypeidIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where typeid is greater than or equal to DEFAULT_TYPEID
        defaultDGoodsShouldBeFound("typeid.greaterThanOrEqual=" + DEFAULT_TYPEID);

        // Get all the dGoodsList where typeid is greater than or equal to UPDATED_TYPEID
        defaultDGoodsShouldNotBeFound("typeid.greaterThanOrEqual=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllDGoodsByTypeidIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where typeid is less than or equal to DEFAULT_TYPEID
        defaultDGoodsShouldBeFound("typeid.lessThanOrEqual=" + DEFAULT_TYPEID);

        // Get all the dGoodsList where typeid is less than or equal to SMALLER_TYPEID
        defaultDGoodsShouldNotBeFound("typeid.lessThanOrEqual=" + SMALLER_TYPEID);
    }

    @Test
    @Transactional
    void getAllDGoodsByTypeidIsLessThanSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where typeid is less than DEFAULT_TYPEID
        defaultDGoodsShouldNotBeFound("typeid.lessThan=" + DEFAULT_TYPEID);

        // Get all the dGoodsList where typeid is less than UPDATED_TYPEID
        defaultDGoodsShouldBeFound("typeid.lessThan=" + UPDATED_TYPEID);
    }

    @Test
    @Transactional
    void getAllDGoodsByTypeidIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where typeid is greater than DEFAULT_TYPEID
        defaultDGoodsShouldNotBeFound("typeid.greaterThan=" + DEFAULT_TYPEID);

        // Get all the dGoodsList where typeid is greater than SMALLER_TYPEID
        defaultDGoodsShouldBeFound("typeid.greaterThan=" + SMALLER_TYPEID);
    }

    @Test
    @Transactional
    void getAllDGoodsByGoodsnameIsEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where goodsname equals to DEFAULT_GOODSNAME
        defaultDGoodsShouldBeFound("goodsname.equals=" + DEFAULT_GOODSNAME);

        // Get all the dGoodsList where goodsname equals to UPDATED_GOODSNAME
        defaultDGoodsShouldNotBeFound("goodsname.equals=" + UPDATED_GOODSNAME);
    }

    @Test
    @Transactional
    void getAllDGoodsByGoodsnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where goodsname not equals to DEFAULT_GOODSNAME
        defaultDGoodsShouldNotBeFound("goodsname.notEquals=" + DEFAULT_GOODSNAME);

        // Get all the dGoodsList where goodsname not equals to UPDATED_GOODSNAME
        defaultDGoodsShouldBeFound("goodsname.notEquals=" + UPDATED_GOODSNAME);
    }

    @Test
    @Transactional
    void getAllDGoodsByGoodsnameIsInShouldWork() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where goodsname in DEFAULT_GOODSNAME or UPDATED_GOODSNAME
        defaultDGoodsShouldBeFound("goodsname.in=" + DEFAULT_GOODSNAME + "," + UPDATED_GOODSNAME);

        // Get all the dGoodsList where goodsname equals to UPDATED_GOODSNAME
        defaultDGoodsShouldNotBeFound("goodsname.in=" + UPDATED_GOODSNAME);
    }

    @Test
    @Transactional
    void getAllDGoodsByGoodsnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where goodsname is not null
        defaultDGoodsShouldBeFound("goodsname.specified=true");

        // Get all the dGoodsList where goodsname is null
        defaultDGoodsShouldNotBeFound("goodsname.specified=false");
    }

    @Test
    @Transactional
    void getAllDGoodsByGoodsnameContainsSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where goodsname contains DEFAULT_GOODSNAME
        defaultDGoodsShouldBeFound("goodsname.contains=" + DEFAULT_GOODSNAME);

        // Get all the dGoodsList where goodsname contains UPDATED_GOODSNAME
        defaultDGoodsShouldNotBeFound("goodsname.contains=" + UPDATED_GOODSNAME);
    }

    @Test
    @Transactional
    void getAllDGoodsByGoodsnameNotContainsSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where goodsname does not contain DEFAULT_GOODSNAME
        defaultDGoodsShouldNotBeFound("goodsname.doesNotContain=" + DEFAULT_GOODSNAME);

        // Get all the dGoodsList where goodsname does not contain UPDATED_GOODSNAME
        defaultDGoodsShouldBeFound("goodsname.doesNotContain=" + UPDATED_GOODSNAME);
    }

    @Test
    @Transactional
    void getAllDGoodsByGoodsidIsEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where goodsid equals to DEFAULT_GOODSID
        defaultDGoodsShouldBeFound("goodsid.equals=" + DEFAULT_GOODSID);

        // Get all the dGoodsList where goodsid equals to UPDATED_GOODSID
        defaultDGoodsShouldNotBeFound("goodsid.equals=" + UPDATED_GOODSID);
    }

    @Test
    @Transactional
    void getAllDGoodsByGoodsidIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where goodsid not equals to DEFAULT_GOODSID
        defaultDGoodsShouldNotBeFound("goodsid.notEquals=" + DEFAULT_GOODSID);

        // Get all the dGoodsList where goodsid not equals to UPDATED_GOODSID
        defaultDGoodsShouldBeFound("goodsid.notEquals=" + UPDATED_GOODSID);
    }

    @Test
    @Transactional
    void getAllDGoodsByGoodsidIsInShouldWork() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where goodsid in DEFAULT_GOODSID or UPDATED_GOODSID
        defaultDGoodsShouldBeFound("goodsid.in=" + DEFAULT_GOODSID + "," + UPDATED_GOODSID);

        // Get all the dGoodsList where goodsid equals to UPDATED_GOODSID
        defaultDGoodsShouldNotBeFound("goodsid.in=" + UPDATED_GOODSID);
    }

    @Test
    @Transactional
    void getAllDGoodsByGoodsidIsNullOrNotNull() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where goodsid is not null
        defaultDGoodsShouldBeFound("goodsid.specified=true");

        // Get all the dGoodsList where goodsid is null
        defaultDGoodsShouldNotBeFound("goodsid.specified=false");
    }

    @Test
    @Transactional
    void getAllDGoodsByGoodsidContainsSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where goodsid contains DEFAULT_GOODSID
        defaultDGoodsShouldBeFound("goodsid.contains=" + DEFAULT_GOODSID);

        // Get all the dGoodsList where goodsid contains UPDATED_GOODSID
        defaultDGoodsShouldNotBeFound("goodsid.contains=" + UPDATED_GOODSID);
    }

    @Test
    @Transactional
    void getAllDGoodsByGoodsidNotContainsSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where goodsid does not contain DEFAULT_GOODSID
        defaultDGoodsShouldNotBeFound("goodsid.doesNotContain=" + DEFAULT_GOODSID);

        // Get all the dGoodsList where goodsid does not contain UPDATED_GOODSID
        defaultDGoodsShouldBeFound("goodsid.doesNotContain=" + UPDATED_GOODSID);
    }

    @Test
    @Transactional
    void getAllDGoodsByGgxhIsEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where ggxh equals to DEFAULT_GGXH
        defaultDGoodsShouldBeFound("ggxh.equals=" + DEFAULT_GGXH);

        // Get all the dGoodsList where ggxh equals to UPDATED_GGXH
        defaultDGoodsShouldNotBeFound("ggxh.equals=" + UPDATED_GGXH);
    }

    @Test
    @Transactional
    void getAllDGoodsByGgxhIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where ggxh not equals to DEFAULT_GGXH
        defaultDGoodsShouldNotBeFound("ggxh.notEquals=" + DEFAULT_GGXH);

        // Get all the dGoodsList where ggxh not equals to UPDATED_GGXH
        defaultDGoodsShouldBeFound("ggxh.notEquals=" + UPDATED_GGXH);
    }

    @Test
    @Transactional
    void getAllDGoodsByGgxhIsInShouldWork() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where ggxh in DEFAULT_GGXH or UPDATED_GGXH
        defaultDGoodsShouldBeFound("ggxh.in=" + DEFAULT_GGXH + "," + UPDATED_GGXH);

        // Get all the dGoodsList where ggxh equals to UPDATED_GGXH
        defaultDGoodsShouldNotBeFound("ggxh.in=" + UPDATED_GGXH);
    }

    @Test
    @Transactional
    void getAllDGoodsByGgxhIsNullOrNotNull() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where ggxh is not null
        defaultDGoodsShouldBeFound("ggxh.specified=true");

        // Get all the dGoodsList where ggxh is null
        defaultDGoodsShouldNotBeFound("ggxh.specified=false");
    }

    @Test
    @Transactional
    void getAllDGoodsByGgxhContainsSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where ggxh contains DEFAULT_GGXH
        defaultDGoodsShouldBeFound("ggxh.contains=" + DEFAULT_GGXH);

        // Get all the dGoodsList where ggxh contains UPDATED_GGXH
        defaultDGoodsShouldNotBeFound("ggxh.contains=" + UPDATED_GGXH);
    }

    @Test
    @Transactional
    void getAllDGoodsByGgxhNotContainsSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where ggxh does not contain DEFAULT_GGXH
        defaultDGoodsShouldNotBeFound("ggxh.doesNotContain=" + DEFAULT_GGXH);

        // Get all the dGoodsList where ggxh does not contain UPDATED_GGXH
        defaultDGoodsShouldBeFound("ggxh.doesNotContain=" + UPDATED_GGXH);
    }

    @Test
    @Transactional
    void getAllDGoodsByPysjIsEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where pysj equals to DEFAULT_PYSJ
        defaultDGoodsShouldBeFound("pysj.equals=" + DEFAULT_PYSJ);

        // Get all the dGoodsList where pysj equals to UPDATED_PYSJ
        defaultDGoodsShouldNotBeFound("pysj.equals=" + UPDATED_PYSJ);
    }

    @Test
    @Transactional
    void getAllDGoodsByPysjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where pysj not equals to DEFAULT_PYSJ
        defaultDGoodsShouldNotBeFound("pysj.notEquals=" + DEFAULT_PYSJ);

        // Get all the dGoodsList where pysj not equals to UPDATED_PYSJ
        defaultDGoodsShouldBeFound("pysj.notEquals=" + UPDATED_PYSJ);
    }

    @Test
    @Transactional
    void getAllDGoodsByPysjIsInShouldWork() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where pysj in DEFAULT_PYSJ or UPDATED_PYSJ
        defaultDGoodsShouldBeFound("pysj.in=" + DEFAULT_PYSJ + "," + UPDATED_PYSJ);

        // Get all the dGoodsList where pysj equals to UPDATED_PYSJ
        defaultDGoodsShouldNotBeFound("pysj.in=" + UPDATED_PYSJ);
    }

    @Test
    @Transactional
    void getAllDGoodsByPysjIsNullOrNotNull() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where pysj is not null
        defaultDGoodsShouldBeFound("pysj.specified=true");

        // Get all the dGoodsList where pysj is null
        defaultDGoodsShouldNotBeFound("pysj.specified=false");
    }

    @Test
    @Transactional
    void getAllDGoodsByPysjContainsSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where pysj contains DEFAULT_PYSJ
        defaultDGoodsShouldBeFound("pysj.contains=" + DEFAULT_PYSJ);

        // Get all the dGoodsList where pysj contains UPDATED_PYSJ
        defaultDGoodsShouldNotBeFound("pysj.contains=" + UPDATED_PYSJ);
    }

    @Test
    @Transactional
    void getAllDGoodsByPysjNotContainsSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where pysj does not contain DEFAULT_PYSJ
        defaultDGoodsShouldNotBeFound("pysj.doesNotContain=" + DEFAULT_PYSJ);

        // Get all the dGoodsList where pysj does not contain UPDATED_PYSJ
        defaultDGoodsShouldBeFound("pysj.doesNotContain=" + UPDATED_PYSJ);
    }

    @Test
    @Transactional
    void getAllDGoodsByWbsjIsEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where wbsj equals to DEFAULT_WBSJ
        defaultDGoodsShouldBeFound("wbsj.equals=" + DEFAULT_WBSJ);

        // Get all the dGoodsList where wbsj equals to UPDATED_WBSJ
        defaultDGoodsShouldNotBeFound("wbsj.equals=" + UPDATED_WBSJ);
    }

    @Test
    @Transactional
    void getAllDGoodsByWbsjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where wbsj not equals to DEFAULT_WBSJ
        defaultDGoodsShouldNotBeFound("wbsj.notEquals=" + DEFAULT_WBSJ);

        // Get all the dGoodsList where wbsj not equals to UPDATED_WBSJ
        defaultDGoodsShouldBeFound("wbsj.notEquals=" + UPDATED_WBSJ);
    }

    @Test
    @Transactional
    void getAllDGoodsByWbsjIsInShouldWork() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where wbsj in DEFAULT_WBSJ or UPDATED_WBSJ
        defaultDGoodsShouldBeFound("wbsj.in=" + DEFAULT_WBSJ + "," + UPDATED_WBSJ);

        // Get all the dGoodsList where wbsj equals to UPDATED_WBSJ
        defaultDGoodsShouldNotBeFound("wbsj.in=" + UPDATED_WBSJ);
    }

    @Test
    @Transactional
    void getAllDGoodsByWbsjIsNullOrNotNull() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where wbsj is not null
        defaultDGoodsShouldBeFound("wbsj.specified=true");

        // Get all the dGoodsList where wbsj is null
        defaultDGoodsShouldNotBeFound("wbsj.specified=false");
    }

    @Test
    @Transactional
    void getAllDGoodsByWbsjContainsSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where wbsj contains DEFAULT_WBSJ
        defaultDGoodsShouldBeFound("wbsj.contains=" + DEFAULT_WBSJ);

        // Get all the dGoodsList where wbsj contains UPDATED_WBSJ
        defaultDGoodsShouldNotBeFound("wbsj.contains=" + UPDATED_WBSJ);
    }

    @Test
    @Transactional
    void getAllDGoodsByWbsjNotContainsSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where wbsj does not contain DEFAULT_WBSJ
        defaultDGoodsShouldNotBeFound("wbsj.doesNotContain=" + DEFAULT_WBSJ);

        // Get all the dGoodsList where wbsj does not contain UPDATED_WBSJ
        defaultDGoodsShouldBeFound("wbsj.doesNotContain=" + UPDATED_WBSJ);
    }

    @Test
    @Transactional
    void getAllDGoodsByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where unit equals to DEFAULT_UNIT
        defaultDGoodsShouldBeFound("unit.equals=" + DEFAULT_UNIT);

        // Get all the dGoodsList where unit equals to UPDATED_UNIT
        defaultDGoodsShouldNotBeFound("unit.equals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDGoodsByUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where unit not equals to DEFAULT_UNIT
        defaultDGoodsShouldNotBeFound("unit.notEquals=" + DEFAULT_UNIT);

        // Get all the dGoodsList where unit not equals to UPDATED_UNIT
        defaultDGoodsShouldBeFound("unit.notEquals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDGoodsByUnitIsInShouldWork() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where unit in DEFAULT_UNIT or UPDATED_UNIT
        defaultDGoodsShouldBeFound("unit.in=" + DEFAULT_UNIT + "," + UPDATED_UNIT);

        // Get all the dGoodsList where unit equals to UPDATED_UNIT
        defaultDGoodsShouldNotBeFound("unit.in=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDGoodsByUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where unit is not null
        defaultDGoodsShouldBeFound("unit.specified=true");

        // Get all the dGoodsList where unit is null
        defaultDGoodsShouldNotBeFound("unit.specified=false");
    }

    @Test
    @Transactional
    void getAllDGoodsByUnitContainsSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where unit contains DEFAULT_UNIT
        defaultDGoodsShouldBeFound("unit.contains=" + DEFAULT_UNIT);

        // Get all the dGoodsList where unit contains UPDATED_UNIT
        defaultDGoodsShouldNotBeFound("unit.contains=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDGoodsByUnitNotContainsSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where unit does not contain DEFAULT_UNIT
        defaultDGoodsShouldNotBeFound("unit.doesNotContain=" + DEFAULT_UNIT);

        // Get all the dGoodsList where unit does not contain UPDATED_UNIT
        defaultDGoodsShouldBeFound("unit.doesNotContain=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    void getAllDGoodsByGcslIsEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where gcsl equals to DEFAULT_GCSL
        defaultDGoodsShouldBeFound("gcsl.equals=" + DEFAULT_GCSL);

        // Get all the dGoodsList where gcsl equals to UPDATED_GCSL
        defaultDGoodsShouldNotBeFound("gcsl.equals=" + UPDATED_GCSL);
    }

    @Test
    @Transactional
    void getAllDGoodsByGcslIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where gcsl not equals to DEFAULT_GCSL
        defaultDGoodsShouldNotBeFound("gcsl.notEquals=" + DEFAULT_GCSL);

        // Get all the dGoodsList where gcsl not equals to UPDATED_GCSL
        defaultDGoodsShouldBeFound("gcsl.notEquals=" + UPDATED_GCSL);
    }

    @Test
    @Transactional
    void getAllDGoodsByGcslIsInShouldWork() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where gcsl in DEFAULT_GCSL or UPDATED_GCSL
        defaultDGoodsShouldBeFound("gcsl.in=" + DEFAULT_GCSL + "," + UPDATED_GCSL);

        // Get all the dGoodsList where gcsl equals to UPDATED_GCSL
        defaultDGoodsShouldNotBeFound("gcsl.in=" + UPDATED_GCSL);
    }

    @Test
    @Transactional
    void getAllDGoodsByGcslIsNullOrNotNull() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where gcsl is not null
        defaultDGoodsShouldBeFound("gcsl.specified=true");

        // Get all the dGoodsList where gcsl is null
        defaultDGoodsShouldNotBeFound("gcsl.specified=false");
    }

    @Test
    @Transactional
    void getAllDGoodsByGcslIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where gcsl is greater than or equal to DEFAULT_GCSL
        defaultDGoodsShouldBeFound("gcsl.greaterThanOrEqual=" + DEFAULT_GCSL);

        // Get all the dGoodsList where gcsl is greater than or equal to UPDATED_GCSL
        defaultDGoodsShouldNotBeFound("gcsl.greaterThanOrEqual=" + UPDATED_GCSL);
    }

    @Test
    @Transactional
    void getAllDGoodsByGcslIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where gcsl is less than or equal to DEFAULT_GCSL
        defaultDGoodsShouldBeFound("gcsl.lessThanOrEqual=" + DEFAULT_GCSL);

        // Get all the dGoodsList where gcsl is less than or equal to SMALLER_GCSL
        defaultDGoodsShouldNotBeFound("gcsl.lessThanOrEqual=" + SMALLER_GCSL);
    }

    @Test
    @Transactional
    void getAllDGoodsByGcslIsLessThanSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where gcsl is less than DEFAULT_GCSL
        defaultDGoodsShouldNotBeFound("gcsl.lessThan=" + DEFAULT_GCSL);

        // Get all the dGoodsList where gcsl is less than UPDATED_GCSL
        defaultDGoodsShouldBeFound("gcsl.lessThan=" + UPDATED_GCSL);
    }

    @Test
    @Transactional
    void getAllDGoodsByGcslIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where gcsl is greater than DEFAULT_GCSL
        defaultDGoodsShouldNotBeFound("gcsl.greaterThan=" + DEFAULT_GCSL);

        // Get all the dGoodsList where gcsl is greater than SMALLER_GCSL
        defaultDGoodsShouldBeFound("gcsl.greaterThan=" + SMALLER_GCSL);
    }

    @Test
    @Transactional
    void getAllDGoodsByDcslIsEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where dcsl equals to DEFAULT_DCSL
        defaultDGoodsShouldBeFound("dcsl.equals=" + DEFAULT_DCSL);

        // Get all the dGoodsList where dcsl equals to UPDATED_DCSL
        defaultDGoodsShouldNotBeFound("dcsl.equals=" + UPDATED_DCSL);
    }

    @Test
    @Transactional
    void getAllDGoodsByDcslIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where dcsl not equals to DEFAULT_DCSL
        defaultDGoodsShouldNotBeFound("dcsl.notEquals=" + DEFAULT_DCSL);

        // Get all the dGoodsList where dcsl not equals to UPDATED_DCSL
        defaultDGoodsShouldBeFound("dcsl.notEquals=" + UPDATED_DCSL);
    }

    @Test
    @Transactional
    void getAllDGoodsByDcslIsInShouldWork() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where dcsl in DEFAULT_DCSL or UPDATED_DCSL
        defaultDGoodsShouldBeFound("dcsl.in=" + DEFAULT_DCSL + "," + UPDATED_DCSL);

        // Get all the dGoodsList where dcsl equals to UPDATED_DCSL
        defaultDGoodsShouldNotBeFound("dcsl.in=" + UPDATED_DCSL);
    }

    @Test
    @Transactional
    void getAllDGoodsByDcslIsNullOrNotNull() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where dcsl is not null
        defaultDGoodsShouldBeFound("dcsl.specified=true");

        // Get all the dGoodsList where dcsl is null
        defaultDGoodsShouldNotBeFound("dcsl.specified=false");
    }

    @Test
    @Transactional
    void getAllDGoodsByDcslIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where dcsl is greater than or equal to DEFAULT_DCSL
        defaultDGoodsShouldBeFound("dcsl.greaterThanOrEqual=" + DEFAULT_DCSL);

        // Get all the dGoodsList where dcsl is greater than or equal to UPDATED_DCSL
        defaultDGoodsShouldNotBeFound("dcsl.greaterThanOrEqual=" + UPDATED_DCSL);
    }

    @Test
    @Transactional
    void getAllDGoodsByDcslIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where dcsl is less than or equal to DEFAULT_DCSL
        defaultDGoodsShouldBeFound("dcsl.lessThanOrEqual=" + DEFAULT_DCSL);

        // Get all the dGoodsList where dcsl is less than or equal to SMALLER_DCSL
        defaultDGoodsShouldNotBeFound("dcsl.lessThanOrEqual=" + SMALLER_DCSL);
    }

    @Test
    @Transactional
    void getAllDGoodsByDcslIsLessThanSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where dcsl is less than DEFAULT_DCSL
        defaultDGoodsShouldNotBeFound("dcsl.lessThan=" + DEFAULT_DCSL);

        // Get all the dGoodsList where dcsl is less than UPDATED_DCSL
        defaultDGoodsShouldBeFound("dcsl.lessThan=" + UPDATED_DCSL);
    }

    @Test
    @Transactional
    void getAllDGoodsByDcslIsGreaterThanSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where dcsl is greater than DEFAULT_DCSL
        defaultDGoodsShouldNotBeFound("dcsl.greaterThan=" + DEFAULT_DCSL);

        // Get all the dGoodsList where dcsl is greater than SMALLER_DCSL
        defaultDGoodsShouldBeFound("dcsl.greaterThan=" + SMALLER_DCSL);
    }

    @Test
    @Transactional
    void getAllDGoodsByRemarkIsEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where remark equals to DEFAULT_REMARK
        defaultDGoodsShouldBeFound("remark.equals=" + DEFAULT_REMARK);

        // Get all the dGoodsList where remark equals to UPDATED_REMARK
        defaultDGoodsShouldNotBeFound("remark.equals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDGoodsByRemarkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where remark not equals to DEFAULT_REMARK
        defaultDGoodsShouldNotBeFound("remark.notEquals=" + DEFAULT_REMARK);

        // Get all the dGoodsList where remark not equals to UPDATED_REMARK
        defaultDGoodsShouldBeFound("remark.notEquals=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDGoodsByRemarkIsInShouldWork() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where remark in DEFAULT_REMARK or UPDATED_REMARK
        defaultDGoodsShouldBeFound("remark.in=" + DEFAULT_REMARK + "," + UPDATED_REMARK);

        // Get all the dGoodsList where remark equals to UPDATED_REMARK
        defaultDGoodsShouldNotBeFound("remark.in=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDGoodsByRemarkIsNullOrNotNull() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where remark is not null
        defaultDGoodsShouldBeFound("remark.specified=true");

        // Get all the dGoodsList where remark is null
        defaultDGoodsShouldNotBeFound("remark.specified=false");
    }

    @Test
    @Transactional
    void getAllDGoodsByRemarkContainsSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where remark contains DEFAULT_REMARK
        defaultDGoodsShouldBeFound("remark.contains=" + DEFAULT_REMARK);

        // Get all the dGoodsList where remark contains UPDATED_REMARK
        defaultDGoodsShouldNotBeFound("remark.contains=" + UPDATED_REMARK);
    }

    @Test
    @Transactional
    void getAllDGoodsByRemarkNotContainsSomething() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        // Get all the dGoodsList where remark does not contain DEFAULT_REMARK
        defaultDGoodsShouldNotBeFound("remark.doesNotContain=" + DEFAULT_REMARK);

        // Get all the dGoodsList where remark does not contain UPDATED_REMARK
        defaultDGoodsShouldBeFound("remark.doesNotContain=" + UPDATED_REMARK);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDGoodsShouldBeFound(String filter) throws Exception {
        restDGoodsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dGoods.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeid").value(hasItem(DEFAULT_TYPEID.intValue())))
            .andExpect(jsonPath("$.[*].goodsname").value(hasItem(DEFAULT_GOODSNAME)))
            .andExpect(jsonPath("$.[*].goodsid").value(hasItem(DEFAULT_GOODSID)))
            .andExpect(jsonPath("$.[*].ggxh").value(hasItem(DEFAULT_GGXH)))
            .andExpect(jsonPath("$.[*].pysj").value(hasItem(DEFAULT_PYSJ)))
            .andExpect(jsonPath("$.[*].wbsj").value(hasItem(DEFAULT_WBSJ)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].gcsl").value(hasItem(sameNumber(DEFAULT_GCSL))))
            .andExpect(jsonPath("$.[*].dcsl").value(hasItem(sameNumber(DEFAULT_DCSL))))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));

        // Check, that the count call also returns 1
        restDGoodsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDGoodsShouldNotBeFound(String filter) throws Exception {
        restDGoodsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDGoodsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDGoods() throws Exception {
        // Get the dGoods
        restDGoodsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDGoods() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        int databaseSizeBeforeUpdate = dGoodsRepository.findAll().size();

        // Update the dGoods
        DGoods updatedDGoods = dGoodsRepository.findById(dGoods.getId()).get();
        // Disconnect from session so that the updates on updatedDGoods are not directly saved in db
        em.detach(updatedDGoods);
        updatedDGoods
            .typeid(UPDATED_TYPEID)
            .goodsname(UPDATED_GOODSNAME)
            .goodsid(UPDATED_GOODSID)
            .ggxh(UPDATED_GGXH)
            .pysj(UPDATED_PYSJ)
            .wbsj(UPDATED_WBSJ)
            .unit(UPDATED_UNIT)
            .gcsl(UPDATED_GCSL)
            .dcsl(UPDATED_DCSL)
            .remark(UPDATED_REMARK);
        DGoodsDTO dGoodsDTO = dGoodsMapper.toDto(updatedDGoods);

        restDGoodsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dGoodsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dGoodsDTO))
            )
            .andExpect(status().isOk());

        // Validate the DGoods in the database
        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeUpdate);
        DGoods testDGoods = dGoodsList.get(dGoodsList.size() - 1);
        assertThat(testDGoods.getTypeid()).isEqualTo(UPDATED_TYPEID);
        assertThat(testDGoods.getGoodsname()).isEqualTo(UPDATED_GOODSNAME);
        assertThat(testDGoods.getGoodsid()).isEqualTo(UPDATED_GOODSID);
        assertThat(testDGoods.getGgxh()).isEqualTo(UPDATED_GGXH);
        assertThat(testDGoods.getPysj()).isEqualTo(UPDATED_PYSJ);
        assertThat(testDGoods.getWbsj()).isEqualTo(UPDATED_WBSJ);
        assertThat(testDGoods.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testDGoods.getGcsl()).isEqualTo(UPDATED_GCSL);
        assertThat(testDGoods.getDcsl()).isEqualTo(UPDATED_DCSL);
        assertThat(testDGoods.getRemark()).isEqualTo(UPDATED_REMARK);

        // Validate the DGoods in Elasticsearch
        verify(mockDGoodsSearchRepository).save(testDGoods);
    }

    @Test
    @Transactional
    void putNonExistingDGoods() throws Exception {
        int databaseSizeBeforeUpdate = dGoodsRepository.findAll().size();
        dGoods.setId(count.incrementAndGet());

        // Create the DGoods
        DGoodsDTO dGoodsDTO = dGoodsMapper.toDto(dGoods);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDGoodsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dGoodsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dGoodsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DGoods in the database
        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DGoods in Elasticsearch
        verify(mockDGoodsSearchRepository, times(0)).save(dGoods);
    }

    @Test
    @Transactional
    void putWithIdMismatchDGoods() throws Exception {
        int databaseSizeBeforeUpdate = dGoodsRepository.findAll().size();
        dGoods.setId(count.incrementAndGet());

        // Create the DGoods
        DGoodsDTO dGoodsDTO = dGoodsMapper.toDto(dGoods);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDGoodsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dGoodsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DGoods in the database
        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DGoods in Elasticsearch
        verify(mockDGoodsSearchRepository, times(0)).save(dGoods);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDGoods() throws Exception {
        int databaseSizeBeforeUpdate = dGoodsRepository.findAll().size();
        dGoods.setId(count.incrementAndGet());

        // Create the DGoods
        DGoodsDTO dGoodsDTO = dGoodsMapper.toDto(dGoods);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDGoodsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dGoodsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DGoods in the database
        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DGoods in Elasticsearch
        verify(mockDGoodsSearchRepository, times(0)).save(dGoods);
    }

    @Test
    @Transactional
    void partialUpdateDGoodsWithPatch() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        int databaseSizeBeforeUpdate = dGoodsRepository.findAll().size();

        // Update the dGoods using partial update
        DGoods partialUpdatedDGoods = new DGoods();
        partialUpdatedDGoods.setId(dGoods.getId());

        partialUpdatedDGoods
            .goodsname(UPDATED_GOODSNAME)
            .goodsid(UPDATED_GOODSID)
            .ggxh(UPDATED_GGXH)
            .unit(UPDATED_UNIT)
            .dcsl(UPDATED_DCSL)
            .remark(UPDATED_REMARK);

        restDGoodsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDGoods.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDGoods))
            )
            .andExpect(status().isOk());

        // Validate the DGoods in the database
        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeUpdate);
        DGoods testDGoods = dGoodsList.get(dGoodsList.size() - 1);
        assertThat(testDGoods.getTypeid()).isEqualTo(DEFAULT_TYPEID);
        assertThat(testDGoods.getGoodsname()).isEqualTo(UPDATED_GOODSNAME);
        assertThat(testDGoods.getGoodsid()).isEqualTo(UPDATED_GOODSID);
        assertThat(testDGoods.getGgxh()).isEqualTo(UPDATED_GGXH);
        assertThat(testDGoods.getPysj()).isEqualTo(DEFAULT_PYSJ);
        assertThat(testDGoods.getWbsj()).isEqualTo(DEFAULT_WBSJ);
        assertThat(testDGoods.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testDGoods.getGcsl()).isEqualByComparingTo(DEFAULT_GCSL);
        assertThat(testDGoods.getDcsl()).isEqualByComparingTo(UPDATED_DCSL);
        assertThat(testDGoods.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    void fullUpdateDGoodsWithPatch() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        int databaseSizeBeforeUpdate = dGoodsRepository.findAll().size();

        // Update the dGoods using partial update
        DGoods partialUpdatedDGoods = new DGoods();
        partialUpdatedDGoods.setId(dGoods.getId());

        partialUpdatedDGoods
            .typeid(UPDATED_TYPEID)
            .goodsname(UPDATED_GOODSNAME)
            .goodsid(UPDATED_GOODSID)
            .ggxh(UPDATED_GGXH)
            .pysj(UPDATED_PYSJ)
            .wbsj(UPDATED_WBSJ)
            .unit(UPDATED_UNIT)
            .gcsl(UPDATED_GCSL)
            .dcsl(UPDATED_DCSL)
            .remark(UPDATED_REMARK);

        restDGoodsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDGoods.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDGoods))
            )
            .andExpect(status().isOk());

        // Validate the DGoods in the database
        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeUpdate);
        DGoods testDGoods = dGoodsList.get(dGoodsList.size() - 1);
        assertThat(testDGoods.getTypeid()).isEqualTo(UPDATED_TYPEID);
        assertThat(testDGoods.getGoodsname()).isEqualTo(UPDATED_GOODSNAME);
        assertThat(testDGoods.getGoodsid()).isEqualTo(UPDATED_GOODSID);
        assertThat(testDGoods.getGgxh()).isEqualTo(UPDATED_GGXH);
        assertThat(testDGoods.getPysj()).isEqualTo(UPDATED_PYSJ);
        assertThat(testDGoods.getWbsj()).isEqualTo(UPDATED_WBSJ);
        assertThat(testDGoods.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testDGoods.getGcsl()).isEqualByComparingTo(UPDATED_GCSL);
        assertThat(testDGoods.getDcsl()).isEqualByComparingTo(UPDATED_DCSL);
        assertThat(testDGoods.getRemark()).isEqualTo(UPDATED_REMARK);
    }

    @Test
    @Transactional
    void patchNonExistingDGoods() throws Exception {
        int databaseSizeBeforeUpdate = dGoodsRepository.findAll().size();
        dGoods.setId(count.incrementAndGet());

        // Create the DGoods
        DGoodsDTO dGoodsDTO = dGoodsMapper.toDto(dGoods);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDGoodsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dGoodsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dGoodsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DGoods in the database
        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DGoods in Elasticsearch
        verify(mockDGoodsSearchRepository, times(0)).save(dGoods);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDGoods() throws Exception {
        int databaseSizeBeforeUpdate = dGoodsRepository.findAll().size();
        dGoods.setId(count.incrementAndGet());

        // Create the DGoods
        DGoodsDTO dGoodsDTO = dGoodsMapper.toDto(dGoods);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDGoodsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dGoodsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DGoods in the database
        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DGoods in Elasticsearch
        verify(mockDGoodsSearchRepository, times(0)).save(dGoods);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDGoods() throws Exception {
        int databaseSizeBeforeUpdate = dGoodsRepository.findAll().size();
        dGoods.setId(count.incrementAndGet());

        // Create the DGoods
        DGoodsDTO dGoodsDTO = dGoodsMapper.toDto(dGoods);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDGoodsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dGoodsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DGoods in the database
        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DGoods in Elasticsearch
        verify(mockDGoodsSearchRepository, times(0)).save(dGoods);
    }

    @Test
    @Transactional
    void deleteDGoods() throws Exception {
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);

        int databaseSizeBeforeDelete = dGoodsRepository.findAll().size();

        // Delete the dGoods
        restDGoodsMockMvc
            .perform(delete(ENTITY_API_URL_ID, dGoods.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DGoods> dGoodsList = dGoodsRepository.findAll();
        assertThat(dGoodsList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DGoods in Elasticsearch
        verify(mockDGoodsSearchRepository, times(1)).deleteById(dGoods.getId());
    }

    @Test
    @Transactional
    void searchDGoods() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dGoodsRepository.saveAndFlush(dGoods);
        when(mockDGoodsSearchRepository.search(queryStringQuery("id:" + dGoods.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dGoods), PageRequest.of(0, 1), 1));

        // Search the dGoods
        restDGoodsMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dGoods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dGoods.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeid").value(hasItem(DEFAULT_TYPEID.intValue())))
            .andExpect(jsonPath("$.[*].goodsname").value(hasItem(DEFAULT_GOODSNAME)))
            .andExpect(jsonPath("$.[*].goodsid").value(hasItem(DEFAULT_GOODSID)))
            .andExpect(jsonPath("$.[*].ggxh").value(hasItem(DEFAULT_GGXH)))
            .andExpect(jsonPath("$.[*].pysj").value(hasItem(DEFAULT_PYSJ)))
            .andExpect(jsonPath("$.[*].wbsj").value(hasItem(DEFAULT_WBSJ)))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT)))
            .andExpect(jsonPath("$.[*].gcsl").value(hasItem(sameNumber(DEFAULT_GCSL))))
            .andExpect(jsonPath("$.[*].dcsl").value(hasItem(sameNumber(DEFAULT_DCSL))))
            .andExpect(jsonPath("$.[*].remark").value(hasItem(DEFAULT_REMARK)));
    }
}
