package ihotel.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.DxSedinfo;
import ihotel.app.repository.DxSedinfoRepository;
import ihotel.app.repository.search.DxSedinfoSearchRepository;
import ihotel.app.service.criteria.DxSedinfoCriteria;
import ihotel.app.service.dto.DxSedinfoDTO;
import ihotel.app.service.mapper.DxSedinfoMapper;
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
 * Integration tests for the {@link DxSedinfoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class DxSedinfoResourceIT {

    private static final String DEFAULT_YDDX = "AA";
    private static final String UPDATED_YDDX = "BB";

    private static final String DEFAULT_YDDXMEMO = "AAAAAAAAAA";
    private static final String UPDATED_YDDXMEMO = "BBBBBBBBBB";

    private static final String DEFAULT_QXYDDX = "AA";
    private static final String UPDATED_QXYDDX = "BB";

    private static final String DEFAULT_QXYDMEMO = "AAAAAAAAAA";
    private static final String UPDATED_QXYDMEMO = "BBBBBBBBBB";

    private static final String DEFAULT_CZDX = "AA";
    private static final String UPDATED_CZDX = "BB";

    private static final String DEFAULT_CZMEMO = "AAAAAAAAAA";
    private static final String UPDATED_CZMEMO = "BBBBBBBBBB";

    private static final String DEFAULT_QXCZDX = "AAAAAAAAAA";
    private static final String UPDATED_QXCZDX = "BBBBBBBBBB";

    private static final String DEFAULT_QXCZMEMO = "AAAAAAAAAA";
    private static final String UPDATED_QXCZMEMO = "BBBBBBBBBB";

    private static final String DEFAULT_YYEDX = "AA";
    private static final String UPDATED_YYEDX = "BB";

    private static final String DEFAULT_YYEMEMO = "AAAAAAAAAA";
    private static final String UPDATED_YYEMEMO = "BBBBBBBBBB";

    private static final String DEFAULT_FSTIME = "AAAAAAAAAA";
    private static final String UPDATED_FSTIME = "BBBBBBBBBB";

    private static final String DEFAULT_SFFSHM = "AA";
    private static final String UPDATED_SFFSHM = "BB";

    private static final String DEFAULT_RZDX = "AAAAAAAAAA";
    private static final String UPDATED_RZDX = "BBBBBBBBBB";

    private static final String DEFAULT_RZDXROOMN = "AAAAAAAAAA";
    private static final String UPDATED_RZDXROOMN = "BBBBBBBBBB";

    private static final String DEFAULT_JFDZ = "AAAAAAAAAA";
    private static final String UPDATED_JFDZ = "BBBBBBBBBB";

    private static final String DEFAULT_BLHY = "AA";
    private static final String UPDATED_BLHY = "BB";

    private static final String DEFAULT_RZMEMO = "AAAAAAAAAA";
    private static final String UPDATED_RZMEMO = "BBBBBBBBBB";

    private static final String DEFAULT_BLHYMEMO = "AAAAAAAAAA";
    private static final String UPDATED_BLHYMEMO = "BBBBBBBBBB";

    private static final String DEFAULT_TFDX = "AA";
    private static final String UPDATED_TFDX = "BB";

    private static final String DEFAULT_TFDXMEMO = "AAAAAAAAAA";
    private static final String UPDATED_TFDXMEMO = "BBBBBBBBBB";

    private static final String DEFAULT_FSLB = "AA";
    private static final String UPDATED_FSLB = "BB";

    private static final String DEFAULT_FSLBMEMO = "AAAAAAAAAA";
    private static final String UPDATED_FSLBMEMO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/dx-sedinfos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/dx-sedinfos";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DxSedinfoRepository dxSedinfoRepository;

    @Autowired
    private DxSedinfoMapper dxSedinfoMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.DxSedinfoSearchRepositoryMockConfiguration
     */
    @Autowired
    private DxSedinfoSearchRepository mockDxSedinfoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDxSedinfoMockMvc;

    private DxSedinfo dxSedinfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DxSedinfo createEntity(EntityManager em) {
        DxSedinfo dxSedinfo = new DxSedinfo()
            .yddx(DEFAULT_YDDX)
            .yddxmemo(DEFAULT_YDDXMEMO)
            .qxyddx(DEFAULT_QXYDDX)
            .qxydmemo(DEFAULT_QXYDMEMO)
            .czdx(DEFAULT_CZDX)
            .czmemo(DEFAULT_CZMEMO)
            .qxczdx(DEFAULT_QXCZDX)
            .qxczmemo(DEFAULT_QXCZMEMO)
            .yyedx(DEFAULT_YYEDX)
            .yyememo(DEFAULT_YYEMEMO)
            .fstime(DEFAULT_FSTIME)
            .sffshm(DEFAULT_SFFSHM)
            .rzdx(DEFAULT_RZDX)
            .rzdxroomn(DEFAULT_RZDXROOMN)
            .jfdz(DEFAULT_JFDZ)
            .blhy(DEFAULT_BLHY)
            .rzmemo(DEFAULT_RZMEMO)
            .blhymemo(DEFAULT_BLHYMEMO)
            .tfdx(DEFAULT_TFDX)
            .tfdxmemo(DEFAULT_TFDXMEMO)
            .fslb(DEFAULT_FSLB)
            .fslbmemo(DEFAULT_FSLBMEMO);
        return dxSedinfo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DxSedinfo createUpdatedEntity(EntityManager em) {
        DxSedinfo dxSedinfo = new DxSedinfo()
            .yddx(UPDATED_YDDX)
            .yddxmemo(UPDATED_YDDXMEMO)
            .qxyddx(UPDATED_QXYDDX)
            .qxydmemo(UPDATED_QXYDMEMO)
            .czdx(UPDATED_CZDX)
            .czmemo(UPDATED_CZMEMO)
            .qxczdx(UPDATED_QXCZDX)
            .qxczmemo(UPDATED_QXCZMEMO)
            .yyedx(UPDATED_YYEDX)
            .yyememo(UPDATED_YYEMEMO)
            .fstime(UPDATED_FSTIME)
            .sffshm(UPDATED_SFFSHM)
            .rzdx(UPDATED_RZDX)
            .rzdxroomn(UPDATED_RZDXROOMN)
            .jfdz(UPDATED_JFDZ)
            .blhy(UPDATED_BLHY)
            .rzmemo(UPDATED_RZMEMO)
            .blhymemo(UPDATED_BLHYMEMO)
            .tfdx(UPDATED_TFDX)
            .tfdxmemo(UPDATED_TFDXMEMO)
            .fslb(UPDATED_FSLB)
            .fslbmemo(UPDATED_FSLBMEMO);
        return dxSedinfo;
    }

    @BeforeEach
    public void initTest() {
        dxSedinfo = createEntity(em);
    }

    @Test
    @Transactional
    void createDxSedinfo() throws Exception {
        int databaseSizeBeforeCreate = dxSedinfoRepository.findAll().size();
        // Create the DxSedinfo
        DxSedinfoDTO dxSedinfoDTO = dxSedinfoMapper.toDto(dxSedinfo);
        restDxSedinfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dxSedinfoDTO)))
            .andExpect(status().isCreated());

        // Validate the DxSedinfo in the database
        List<DxSedinfo> dxSedinfoList = dxSedinfoRepository.findAll();
        assertThat(dxSedinfoList).hasSize(databaseSizeBeforeCreate + 1);
        DxSedinfo testDxSedinfo = dxSedinfoList.get(dxSedinfoList.size() - 1);
        assertThat(testDxSedinfo.getYddx()).isEqualTo(DEFAULT_YDDX);
        assertThat(testDxSedinfo.getYddxmemo()).isEqualTo(DEFAULT_YDDXMEMO);
        assertThat(testDxSedinfo.getQxyddx()).isEqualTo(DEFAULT_QXYDDX);
        assertThat(testDxSedinfo.getQxydmemo()).isEqualTo(DEFAULT_QXYDMEMO);
        assertThat(testDxSedinfo.getCzdx()).isEqualTo(DEFAULT_CZDX);
        assertThat(testDxSedinfo.getCzmemo()).isEqualTo(DEFAULT_CZMEMO);
        assertThat(testDxSedinfo.getQxczdx()).isEqualTo(DEFAULT_QXCZDX);
        assertThat(testDxSedinfo.getQxczmemo()).isEqualTo(DEFAULT_QXCZMEMO);
        assertThat(testDxSedinfo.getYyedx()).isEqualTo(DEFAULT_YYEDX);
        assertThat(testDxSedinfo.getYyememo()).isEqualTo(DEFAULT_YYEMEMO);
        assertThat(testDxSedinfo.getFstime()).isEqualTo(DEFAULT_FSTIME);
        assertThat(testDxSedinfo.getSffshm()).isEqualTo(DEFAULT_SFFSHM);
        assertThat(testDxSedinfo.getRzdx()).isEqualTo(DEFAULT_RZDX);
        assertThat(testDxSedinfo.getRzdxroomn()).isEqualTo(DEFAULT_RZDXROOMN);
        assertThat(testDxSedinfo.getJfdz()).isEqualTo(DEFAULT_JFDZ);
        assertThat(testDxSedinfo.getBlhy()).isEqualTo(DEFAULT_BLHY);
        assertThat(testDxSedinfo.getRzmemo()).isEqualTo(DEFAULT_RZMEMO);
        assertThat(testDxSedinfo.getBlhymemo()).isEqualTo(DEFAULT_BLHYMEMO);
        assertThat(testDxSedinfo.getTfdx()).isEqualTo(DEFAULT_TFDX);
        assertThat(testDxSedinfo.getTfdxmemo()).isEqualTo(DEFAULT_TFDXMEMO);
        assertThat(testDxSedinfo.getFslb()).isEqualTo(DEFAULT_FSLB);
        assertThat(testDxSedinfo.getFslbmemo()).isEqualTo(DEFAULT_FSLBMEMO);

        // Validate the DxSedinfo in Elasticsearch
        verify(mockDxSedinfoSearchRepository, times(1)).save(testDxSedinfo);
    }

    @Test
    @Transactional
    void createDxSedinfoWithExistingId() throws Exception {
        // Create the DxSedinfo with an existing ID
        dxSedinfo.setId(1L);
        DxSedinfoDTO dxSedinfoDTO = dxSedinfoMapper.toDto(dxSedinfo);

        int databaseSizeBeforeCreate = dxSedinfoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDxSedinfoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dxSedinfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DxSedinfo in the database
        List<DxSedinfo> dxSedinfoList = dxSedinfoRepository.findAll();
        assertThat(dxSedinfoList).hasSize(databaseSizeBeforeCreate);

        // Validate the DxSedinfo in Elasticsearch
        verify(mockDxSedinfoSearchRepository, times(0)).save(dxSedinfo);
    }

    @Test
    @Transactional
    void getAllDxSedinfos() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList
        restDxSedinfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dxSedinfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].yddx").value(hasItem(DEFAULT_YDDX)))
            .andExpect(jsonPath("$.[*].yddxmemo").value(hasItem(DEFAULT_YDDXMEMO)))
            .andExpect(jsonPath("$.[*].qxyddx").value(hasItem(DEFAULT_QXYDDX)))
            .andExpect(jsonPath("$.[*].qxydmemo").value(hasItem(DEFAULT_QXYDMEMO)))
            .andExpect(jsonPath("$.[*].czdx").value(hasItem(DEFAULT_CZDX)))
            .andExpect(jsonPath("$.[*].czmemo").value(hasItem(DEFAULT_CZMEMO)))
            .andExpect(jsonPath("$.[*].qxczdx").value(hasItem(DEFAULT_QXCZDX)))
            .andExpect(jsonPath("$.[*].qxczmemo").value(hasItem(DEFAULT_QXCZMEMO)))
            .andExpect(jsonPath("$.[*].yyedx").value(hasItem(DEFAULT_YYEDX)))
            .andExpect(jsonPath("$.[*].yyememo").value(hasItem(DEFAULT_YYEMEMO)))
            .andExpect(jsonPath("$.[*].fstime").value(hasItem(DEFAULT_FSTIME)))
            .andExpect(jsonPath("$.[*].sffshm").value(hasItem(DEFAULT_SFFSHM)))
            .andExpect(jsonPath("$.[*].rzdx").value(hasItem(DEFAULT_RZDX)))
            .andExpect(jsonPath("$.[*].rzdxroomn").value(hasItem(DEFAULT_RZDXROOMN)))
            .andExpect(jsonPath("$.[*].jfdz").value(hasItem(DEFAULT_JFDZ)))
            .andExpect(jsonPath("$.[*].blhy").value(hasItem(DEFAULT_BLHY)))
            .andExpect(jsonPath("$.[*].rzmemo").value(hasItem(DEFAULT_RZMEMO)))
            .andExpect(jsonPath("$.[*].blhymemo").value(hasItem(DEFAULT_BLHYMEMO)))
            .andExpect(jsonPath("$.[*].tfdx").value(hasItem(DEFAULT_TFDX)))
            .andExpect(jsonPath("$.[*].tfdxmemo").value(hasItem(DEFAULT_TFDXMEMO)))
            .andExpect(jsonPath("$.[*].fslb").value(hasItem(DEFAULT_FSLB)))
            .andExpect(jsonPath("$.[*].fslbmemo").value(hasItem(DEFAULT_FSLBMEMO)));
    }

    @Test
    @Transactional
    void getDxSedinfo() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get the dxSedinfo
        restDxSedinfoMockMvc
            .perform(get(ENTITY_API_URL_ID, dxSedinfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(dxSedinfo.getId().intValue()))
            .andExpect(jsonPath("$.yddx").value(DEFAULT_YDDX))
            .andExpect(jsonPath("$.yddxmemo").value(DEFAULT_YDDXMEMO))
            .andExpect(jsonPath("$.qxyddx").value(DEFAULT_QXYDDX))
            .andExpect(jsonPath("$.qxydmemo").value(DEFAULT_QXYDMEMO))
            .andExpect(jsonPath("$.czdx").value(DEFAULT_CZDX))
            .andExpect(jsonPath("$.czmemo").value(DEFAULT_CZMEMO))
            .andExpect(jsonPath("$.qxczdx").value(DEFAULT_QXCZDX))
            .andExpect(jsonPath("$.qxczmemo").value(DEFAULT_QXCZMEMO))
            .andExpect(jsonPath("$.yyedx").value(DEFAULT_YYEDX))
            .andExpect(jsonPath("$.yyememo").value(DEFAULT_YYEMEMO))
            .andExpect(jsonPath("$.fstime").value(DEFAULT_FSTIME))
            .andExpect(jsonPath("$.sffshm").value(DEFAULT_SFFSHM))
            .andExpect(jsonPath("$.rzdx").value(DEFAULT_RZDX))
            .andExpect(jsonPath("$.rzdxroomn").value(DEFAULT_RZDXROOMN))
            .andExpect(jsonPath("$.jfdz").value(DEFAULT_JFDZ))
            .andExpect(jsonPath("$.blhy").value(DEFAULT_BLHY))
            .andExpect(jsonPath("$.rzmemo").value(DEFAULT_RZMEMO))
            .andExpect(jsonPath("$.blhymemo").value(DEFAULT_BLHYMEMO))
            .andExpect(jsonPath("$.tfdx").value(DEFAULT_TFDX))
            .andExpect(jsonPath("$.tfdxmemo").value(DEFAULT_TFDXMEMO))
            .andExpect(jsonPath("$.fslb").value(DEFAULT_FSLB))
            .andExpect(jsonPath("$.fslbmemo").value(DEFAULT_FSLBMEMO));
    }

    @Test
    @Transactional
    void getDxSedinfosByIdFiltering() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        Long id = dxSedinfo.getId();

        defaultDxSedinfoShouldBeFound("id.equals=" + id);
        defaultDxSedinfoShouldNotBeFound("id.notEquals=" + id);

        defaultDxSedinfoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDxSedinfoShouldNotBeFound("id.greaterThan=" + id);

        defaultDxSedinfoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDxSedinfoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYddxIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yddx equals to DEFAULT_YDDX
        defaultDxSedinfoShouldBeFound("yddx.equals=" + DEFAULT_YDDX);

        // Get all the dxSedinfoList where yddx equals to UPDATED_YDDX
        defaultDxSedinfoShouldNotBeFound("yddx.equals=" + UPDATED_YDDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYddxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yddx not equals to DEFAULT_YDDX
        defaultDxSedinfoShouldNotBeFound("yddx.notEquals=" + DEFAULT_YDDX);

        // Get all the dxSedinfoList where yddx not equals to UPDATED_YDDX
        defaultDxSedinfoShouldBeFound("yddx.notEquals=" + UPDATED_YDDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYddxIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yddx in DEFAULT_YDDX or UPDATED_YDDX
        defaultDxSedinfoShouldBeFound("yddx.in=" + DEFAULT_YDDX + "," + UPDATED_YDDX);

        // Get all the dxSedinfoList where yddx equals to UPDATED_YDDX
        defaultDxSedinfoShouldNotBeFound("yddx.in=" + UPDATED_YDDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYddxIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yddx is not null
        defaultDxSedinfoShouldBeFound("yddx.specified=true");

        // Get all the dxSedinfoList where yddx is null
        defaultDxSedinfoShouldNotBeFound("yddx.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYddxContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yddx contains DEFAULT_YDDX
        defaultDxSedinfoShouldBeFound("yddx.contains=" + DEFAULT_YDDX);

        // Get all the dxSedinfoList where yddx contains UPDATED_YDDX
        defaultDxSedinfoShouldNotBeFound("yddx.contains=" + UPDATED_YDDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYddxNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yddx does not contain DEFAULT_YDDX
        defaultDxSedinfoShouldNotBeFound("yddx.doesNotContain=" + DEFAULT_YDDX);

        // Get all the dxSedinfoList where yddx does not contain UPDATED_YDDX
        defaultDxSedinfoShouldBeFound("yddx.doesNotContain=" + UPDATED_YDDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYddxmemoIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yddxmemo equals to DEFAULT_YDDXMEMO
        defaultDxSedinfoShouldBeFound("yddxmemo.equals=" + DEFAULT_YDDXMEMO);

        // Get all the dxSedinfoList where yddxmemo equals to UPDATED_YDDXMEMO
        defaultDxSedinfoShouldNotBeFound("yddxmemo.equals=" + UPDATED_YDDXMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYddxmemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yddxmemo not equals to DEFAULT_YDDXMEMO
        defaultDxSedinfoShouldNotBeFound("yddxmemo.notEquals=" + DEFAULT_YDDXMEMO);

        // Get all the dxSedinfoList where yddxmemo not equals to UPDATED_YDDXMEMO
        defaultDxSedinfoShouldBeFound("yddxmemo.notEquals=" + UPDATED_YDDXMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYddxmemoIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yddxmemo in DEFAULT_YDDXMEMO or UPDATED_YDDXMEMO
        defaultDxSedinfoShouldBeFound("yddxmemo.in=" + DEFAULT_YDDXMEMO + "," + UPDATED_YDDXMEMO);

        // Get all the dxSedinfoList where yddxmemo equals to UPDATED_YDDXMEMO
        defaultDxSedinfoShouldNotBeFound("yddxmemo.in=" + UPDATED_YDDXMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYddxmemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yddxmemo is not null
        defaultDxSedinfoShouldBeFound("yddxmemo.specified=true");

        // Get all the dxSedinfoList where yddxmemo is null
        defaultDxSedinfoShouldNotBeFound("yddxmemo.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYddxmemoContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yddxmemo contains DEFAULT_YDDXMEMO
        defaultDxSedinfoShouldBeFound("yddxmemo.contains=" + DEFAULT_YDDXMEMO);

        // Get all the dxSedinfoList where yddxmemo contains UPDATED_YDDXMEMO
        defaultDxSedinfoShouldNotBeFound("yddxmemo.contains=" + UPDATED_YDDXMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYddxmemoNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yddxmemo does not contain DEFAULT_YDDXMEMO
        defaultDxSedinfoShouldNotBeFound("yddxmemo.doesNotContain=" + DEFAULT_YDDXMEMO);

        // Get all the dxSedinfoList where yddxmemo does not contain UPDATED_YDDXMEMO
        defaultDxSedinfoShouldBeFound("yddxmemo.doesNotContain=" + UPDATED_YDDXMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxyddxIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxyddx equals to DEFAULT_QXYDDX
        defaultDxSedinfoShouldBeFound("qxyddx.equals=" + DEFAULT_QXYDDX);

        // Get all the dxSedinfoList where qxyddx equals to UPDATED_QXYDDX
        defaultDxSedinfoShouldNotBeFound("qxyddx.equals=" + UPDATED_QXYDDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxyddxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxyddx not equals to DEFAULT_QXYDDX
        defaultDxSedinfoShouldNotBeFound("qxyddx.notEquals=" + DEFAULT_QXYDDX);

        // Get all the dxSedinfoList where qxyddx not equals to UPDATED_QXYDDX
        defaultDxSedinfoShouldBeFound("qxyddx.notEquals=" + UPDATED_QXYDDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxyddxIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxyddx in DEFAULT_QXYDDX or UPDATED_QXYDDX
        defaultDxSedinfoShouldBeFound("qxyddx.in=" + DEFAULT_QXYDDX + "," + UPDATED_QXYDDX);

        // Get all the dxSedinfoList where qxyddx equals to UPDATED_QXYDDX
        defaultDxSedinfoShouldNotBeFound("qxyddx.in=" + UPDATED_QXYDDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxyddxIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxyddx is not null
        defaultDxSedinfoShouldBeFound("qxyddx.specified=true");

        // Get all the dxSedinfoList where qxyddx is null
        defaultDxSedinfoShouldNotBeFound("qxyddx.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxyddxContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxyddx contains DEFAULT_QXYDDX
        defaultDxSedinfoShouldBeFound("qxyddx.contains=" + DEFAULT_QXYDDX);

        // Get all the dxSedinfoList where qxyddx contains UPDATED_QXYDDX
        defaultDxSedinfoShouldNotBeFound("qxyddx.contains=" + UPDATED_QXYDDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxyddxNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxyddx does not contain DEFAULT_QXYDDX
        defaultDxSedinfoShouldNotBeFound("qxyddx.doesNotContain=" + DEFAULT_QXYDDX);

        // Get all the dxSedinfoList where qxyddx does not contain UPDATED_QXYDDX
        defaultDxSedinfoShouldBeFound("qxyddx.doesNotContain=" + UPDATED_QXYDDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxydmemoIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxydmemo equals to DEFAULT_QXYDMEMO
        defaultDxSedinfoShouldBeFound("qxydmemo.equals=" + DEFAULT_QXYDMEMO);

        // Get all the dxSedinfoList where qxydmemo equals to UPDATED_QXYDMEMO
        defaultDxSedinfoShouldNotBeFound("qxydmemo.equals=" + UPDATED_QXYDMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxydmemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxydmemo not equals to DEFAULT_QXYDMEMO
        defaultDxSedinfoShouldNotBeFound("qxydmemo.notEquals=" + DEFAULT_QXYDMEMO);

        // Get all the dxSedinfoList where qxydmemo not equals to UPDATED_QXYDMEMO
        defaultDxSedinfoShouldBeFound("qxydmemo.notEquals=" + UPDATED_QXYDMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxydmemoIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxydmemo in DEFAULT_QXYDMEMO or UPDATED_QXYDMEMO
        defaultDxSedinfoShouldBeFound("qxydmemo.in=" + DEFAULT_QXYDMEMO + "," + UPDATED_QXYDMEMO);

        // Get all the dxSedinfoList where qxydmemo equals to UPDATED_QXYDMEMO
        defaultDxSedinfoShouldNotBeFound("qxydmemo.in=" + UPDATED_QXYDMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxydmemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxydmemo is not null
        defaultDxSedinfoShouldBeFound("qxydmemo.specified=true");

        // Get all the dxSedinfoList where qxydmemo is null
        defaultDxSedinfoShouldNotBeFound("qxydmemo.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxydmemoContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxydmemo contains DEFAULT_QXYDMEMO
        defaultDxSedinfoShouldBeFound("qxydmemo.contains=" + DEFAULT_QXYDMEMO);

        // Get all the dxSedinfoList where qxydmemo contains UPDATED_QXYDMEMO
        defaultDxSedinfoShouldNotBeFound("qxydmemo.contains=" + UPDATED_QXYDMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxydmemoNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxydmemo does not contain DEFAULT_QXYDMEMO
        defaultDxSedinfoShouldNotBeFound("qxydmemo.doesNotContain=" + DEFAULT_QXYDMEMO);

        // Get all the dxSedinfoList where qxydmemo does not contain UPDATED_QXYDMEMO
        defaultDxSedinfoShouldBeFound("qxydmemo.doesNotContain=" + UPDATED_QXYDMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByCzdxIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where czdx equals to DEFAULT_CZDX
        defaultDxSedinfoShouldBeFound("czdx.equals=" + DEFAULT_CZDX);

        // Get all the dxSedinfoList where czdx equals to UPDATED_CZDX
        defaultDxSedinfoShouldNotBeFound("czdx.equals=" + UPDATED_CZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByCzdxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where czdx not equals to DEFAULT_CZDX
        defaultDxSedinfoShouldNotBeFound("czdx.notEquals=" + DEFAULT_CZDX);

        // Get all the dxSedinfoList where czdx not equals to UPDATED_CZDX
        defaultDxSedinfoShouldBeFound("czdx.notEquals=" + UPDATED_CZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByCzdxIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where czdx in DEFAULT_CZDX or UPDATED_CZDX
        defaultDxSedinfoShouldBeFound("czdx.in=" + DEFAULT_CZDX + "," + UPDATED_CZDX);

        // Get all the dxSedinfoList where czdx equals to UPDATED_CZDX
        defaultDxSedinfoShouldNotBeFound("czdx.in=" + UPDATED_CZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByCzdxIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where czdx is not null
        defaultDxSedinfoShouldBeFound("czdx.specified=true");

        // Get all the dxSedinfoList where czdx is null
        defaultDxSedinfoShouldNotBeFound("czdx.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByCzdxContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where czdx contains DEFAULT_CZDX
        defaultDxSedinfoShouldBeFound("czdx.contains=" + DEFAULT_CZDX);

        // Get all the dxSedinfoList where czdx contains UPDATED_CZDX
        defaultDxSedinfoShouldNotBeFound("czdx.contains=" + UPDATED_CZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByCzdxNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where czdx does not contain DEFAULT_CZDX
        defaultDxSedinfoShouldNotBeFound("czdx.doesNotContain=" + DEFAULT_CZDX);

        // Get all the dxSedinfoList where czdx does not contain UPDATED_CZDX
        defaultDxSedinfoShouldBeFound("czdx.doesNotContain=" + UPDATED_CZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByCzmemoIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where czmemo equals to DEFAULT_CZMEMO
        defaultDxSedinfoShouldBeFound("czmemo.equals=" + DEFAULT_CZMEMO);

        // Get all the dxSedinfoList where czmemo equals to UPDATED_CZMEMO
        defaultDxSedinfoShouldNotBeFound("czmemo.equals=" + UPDATED_CZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByCzmemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where czmemo not equals to DEFAULT_CZMEMO
        defaultDxSedinfoShouldNotBeFound("czmemo.notEquals=" + DEFAULT_CZMEMO);

        // Get all the dxSedinfoList where czmemo not equals to UPDATED_CZMEMO
        defaultDxSedinfoShouldBeFound("czmemo.notEquals=" + UPDATED_CZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByCzmemoIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where czmemo in DEFAULT_CZMEMO or UPDATED_CZMEMO
        defaultDxSedinfoShouldBeFound("czmemo.in=" + DEFAULT_CZMEMO + "," + UPDATED_CZMEMO);

        // Get all the dxSedinfoList where czmemo equals to UPDATED_CZMEMO
        defaultDxSedinfoShouldNotBeFound("czmemo.in=" + UPDATED_CZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByCzmemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where czmemo is not null
        defaultDxSedinfoShouldBeFound("czmemo.specified=true");

        // Get all the dxSedinfoList where czmemo is null
        defaultDxSedinfoShouldNotBeFound("czmemo.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByCzmemoContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where czmemo contains DEFAULT_CZMEMO
        defaultDxSedinfoShouldBeFound("czmemo.contains=" + DEFAULT_CZMEMO);

        // Get all the dxSedinfoList where czmemo contains UPDATED_CZMEMO
        defaultDxSedinfoShouldNotBeFound("czmemo.contains=" + UPDATED_CZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByCzmemoNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where czmemo does not contain DEFAULT_CZMEMO
        defaultDxSedinfoShouldNotBeFound("czmemo.doesNotContain=" + DEFAULT_CZMEMO);

        // Get all the dxSedinfoList where czmemo does not contain UPDATED_CZMEMO
        defaultDxSedinfoShouldBeFound("czmemo.doesNotContain=" + UPDATED_CZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxczdxIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxczdx equals to DEFAULT_QXCZDX
        defaultDxSedinfoShouldBeFound("qxczdx.equals=" + DEFAULT_QXCZDX);

        // Get all the dxSedinfoList where qxczdx equals to UPDATED_QXCZDX
        defaultDxSedinfoShouldNotBeFound("qxczdx.equals=" + UPDATED_QXCZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxczdxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxczdx not equals to DEFAULT_QXCZDX
        defaultDxSedinfoShouldNotBeFound("qxczdx.notEquals=" + DEFAULT_QXCZDX);

        // Get all the dxSedinfoList where qxczdx not equals to UPDATED_QXCZDX
        defaultDxSedinfoShouldBeFound("qxczdx.notEquals=" + UPDATED_QXCZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxczdxIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxczdx in DEFAULT_QXCZDX or UPDATED_QXCZDX
        defaultDxSedinfoShouldBeFound("qxczdx.in=" + DEFAULT_QXCZDX + "," + UPDATED_QXCZDX);

        // Get all the dxSedinfoList where qxczdx equals to UPDATED_QXCZDX
        defaultDxSedinfoShouldNotBeFound("qxczdx.in=" + UPDATED_QXCZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxczdxIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxczdx is not null
        defaultDxSedinfoShouldBeFound("qxczdx.specified=true");

        // Get all the dxSedinfoList where qxczdx is null
        defaultDxSedinfoShouldNotBeFound("qxczdx.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxczdxContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxczdx contains DEFAULT_QXCZDX
        defaultDxSedinfoShouldBeFound("qxczdx.contains=" + DEFAULT_QXCZDX);

        // Get all the dxSedinfoList where qxczdx contains UPDATED_QXCZDX
        defaultDxSedinfoShouldNotBeFound("qxczdx.contains=" + UPDATED_QXCZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxczdxNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxczdx does not contain DEFAULT_QXCZDX
        defaultDxSedinfoShouldNotBeFound("qxczdx.doesNotContain=" + DEFAULT_QXCZDX);

        // Get all the dxSedinfoList where qxczdx does not contain UPDATED_QXCZDX
        defaultDxSedinfoShouldBeFound("qxczdx.doesNotContain=" + UPDATED_QXCZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxczmemoIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxczmemo equals to DEFAULT_QXCZMEMO
        defaultDxSedinfoShouldBeFound("qxczmemo.equals=" + DEFAULT_QXCZMEMO);

        // Get all the dxSedinfoList where qxczmemo equals to UPDATED_QXCZMEMO
        defaultDxSedinfoShouldNotBeFound("qxczmemo.equals=" + UPDATED_QXCZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxczmemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxczmemo not equals to DEFAULT_QXCZMEMO
        defaultDxSedinfoShouldNotBeFound("qxczmemo.notEquals=" + DEFAULT_QXCZMEMO);

        // Get all the dxSedinfoList where qxczmemo not equals to UPDATED_QXCZMEMO
        defaultDxSedinfoShouldBeFound("qxczmemo.notEquals=" + UPDATED_QXCZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxczmemoIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxczmemo in DEFAULT_QXCZMEMO or UPDATED_QXCZMEMO
        defaultDxSedinfoShouldBeFound("qxczmemo.in=" + DEFAULT_QXCZMEMO + "," + UPDATED_QXCZMEMO);

        // Get all the dxSedinfoList where qxczmemo equals to UPDATED_QXCZMEMO
        defaultDxSedinfoShouldNotBeFound("qxczmemo.in=" + UPDATED_QXCZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxczmemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxczmemo is not null
        defaultDxSedinfoShouldBeFound("qxczmemo.specified=true");

        // Get all the dxSedinfoList where qxczmemo is null
        defaultDxSedinfoShouldNotBeFound("qxczmemo.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxczmemoContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxczmemo contains DEFAULT_QXCZMEMO
        defaultDxSedinfoShouldBeFound("qxczmemo.contains=" + DEFAULT_QXCZMEMO);

        // Get all the dxSedinfoList where qxczmemo contains UPDATED_QXCZMEMO
        defaultDxSedinfoShouldNotBeFound("qxczmemo.contains=" + UPDATED_QXCZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByQxczmemoNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where qxczmemo does not contain DEFAULT_QXCZMEMO
        defaultDxSedinfoShouldNotBeFound("qxczmemo.doesNotContain=" + DEFAULT_QXCZMEMO);

        // Get all the dxSedinfoList where qxczmemo does not contain UPDATED_QXCZMEMO
        defaultDxSedinfoShouldBeFound("qxczmemo.doesNotContain=" + UPDATED_QXCZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYyedxIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yyedx equals to DEFAULT_YYEDX
        defaultDxSedinfoShouldBeFound("yyedx.equals=" + DEFAULT_YYEDX);

        // Get all the dxSedinfoList where yyedx equals to UPDATED_YYEDX
        defaultDxSedinfoShouldNotBeFound("yyedx.equals=" + UPDATED_YYEDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYyedxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yyedx not equals to DEFAULT_YYEDX
        defaultDxSedinfoShouldNotBeFound("yyedx.notEquals=" + DEFAULT_YYEDX);

        // Get all the dxSedinfoList where yyedx not equals to UPDATED_YYEDX
        defaultDxSedinfoShouldBeFound("yyedx.notEquals=" + UPDATED_YYEDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYyedxIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yyedx in DEFAULT_YYEDX or UPDATED_YYEDX
        defaultDxSedinfoShouldBeFound("yyedx.in=" + DEFAULT_YYEDX + "," + UPDATED_YYEDX);

        // Get all the dxSedinfoList where yyedx equals to UPDATED_YYEDX
        defaultDxSedinfoShouldNotBeFound("yyedx.in=" + UPDATED_YYEDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYyedxIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yyedx is not null
        defaultDxSedinfoShouldBeFound("yyedx.specified=true");

        // Get all the dxSedinfoList where yyedx is null
        defaultDxSedinfoShouldNotBeFound("yyedx.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYyedxContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yyedx contains DEFAULT_YYEDX
        defaultDxSedinfoShouldBeFound("yyedx.contains=" + DEFAULT_YYEDX);

        // Get all the dxSedinfoList where yyedx contains UPDATED_YYEDX
        defaultDxSedinfoShouldNotBeFound("yyedx.contains=" + UPDATED_YYEDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYyedxNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yyedx does not contain DEFAULT_YYEDX
        defaultDxSedinfoShouldNotBeFound("yyedx.doesNotContain=" + DEFAULT_YYEDX);

        // Get all the dxSedinfoList where yyedx does not contain UPDATED_YYEDX
        defaultDxSedinfoShouldBeFound("yyedx.doesNotContain=" + UPDATED_YYEDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYyememoIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yyememo equals to DEFAULT_YYEMEMO
        defaultDxSedinfoShouldBeFound("yyememo.equals=" + DEFAULT_YYEMEMO);

        // Get all the dxSedinfoList where yyememo equals to UPDATED_YYEMEMO
        defaultDxSedinfoShouldNotBeFound("yyememo.equals=" + UPDATED_YYEMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYyememoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yyememo not equals to DEFAULT_YYEMEMO
        defaultDxSedinfoShouldNotBeFound("yyememo.notEquals=" + DEFAULT_YYEMEMO);

        // Get all the dxSedinfoList where yyememo not equals to UPDATED_YYEMEMO
        defaultDxSedinfoShouldBeFound("yyememo.notEquals=" + UPDATED_YYEMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYyememoIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yyememo in DEFAULT_YYEMEMO or UPDATED_YYEMEMO
        defaultDxSedinfoShouldBeFound("yyememo.in=" + DEFAULT_YYEMEMO + "," + UPDATED_YYEMEMO);

        // Get all the dxSedinfoList where yyememo equals to UPDATED_YYEMEMO
        defaultDxSedinfoShouldNotBeFound("yyememo.in=" + UPDATED_YYEMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYyememoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yyememo is not null
        defaultDxSedinfoShouldBeFound("yyememo.specified=true");

        // Get all the dxSedinfoList where yyememo is null
        defaultDxSedinfoShouldNotBeFound("yyememo.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYyememoContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yyememo contains DEFAULT_YYEMEMO
        defaultDxSedinfoShouldBeFound("yyememo.contains=" + DEFAULT_YYEMEMO);

        // Get all the dxSedinfoList where yyememo contains UPDATED_YYEMEMO
        defaultDxSedinfoShouldNotBeFound("yyememo.contains=" + UPDATED_YYEMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByYyememoNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where yyememo does not contain DEFAULT_YYEMEMO
        defaultDxSedinfoShouldNotBeFound("yyememo.doesNotContain=" + DEFAULT_YYEMEMO);

        // Get all the dxSedinfoList where yyememo does not contain UPDATED_YYEMEMO
        defaultDxSedinfoShouldBeFound("yyememo.doesNotContain=" + UPDATED_YYEMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFstimeIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fstime equals to DEFAULT_FSTIME
        defaultDxSedinfoShouldBeFound("fstime.equals=" + DEFAULT_FSTIME);

        // Get all the dxSedinfoList where fstime equals to UPDATED_FSTIME
        defaultDxSedinfoShouldNotBeFound("fstime.equals=" + UPDATED_FSTIME);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFstimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fstime not equals to DEFAULT_FSTIME
        defaultDxSedinfoShouldNotBeFound("fstime.notEquals=" + DEFAULT_FSTIME);

        // Get all the dxSedinfoList where fstime not equals to UPDATED_FSTIME
        defaultDxSedinfoShouldBeFound("fstime.notEquals=" + UPDATED_FSTIME);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFstimeIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fstime in DEFAULT_FSTIME or UPDATED_FSTIME
        defaultDxSedinfoShouldBeFound("fstime.in=" + DEFAULT_FSTIME + "," + UPDATED_FSTIME);

        // Get all the dxSedinfoList where fstime equals to UPDATED_FSTIME
        defaultDxSedinfoShouldNotBeFound("fstime.in=" + UPDATED_FSTIME);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFstimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fstime is not null
        defaultDxSedinfoShouldBeFound("fstime.specified=true");

        // Get all the dxSedinfoList where fstime is null
        defaultDxSedinfoShouldNotBeFound("fstime.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFstimeContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fstime contains DEFAULT_FSTIME
        defaultDxSedinfoShouldBeFound("fstime.contains=" + DEFAULT_FSTIME);

        // Get all the dxSedinfoList where fstime contains UPDATED_FSTIME
        defaultDxSedinfoShouldNotBeFound("fstime.contains=" + UPDATED_FSTIME);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFstimeNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fstime does not contain DEFAULT_FSTIME
        defaultDxSedinfoShouldNotBeFound("fstime.doesNotContain=" + DEFAULT_FSTIME);

        // Get all the dxSedinfoList where fstime does not contain UPDATED_FSTIME
        defaultDxSedinfoShouldBeFound("fstime.doesNotContain=" + UPDATED_FSTIME);
    }

    @Test
    @Transactional
    void getAllDxSedinfosBySffshmIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where sffshm equals to DEFAULT_SFFSHM
        defaultDxSedinfoShouldBeFound("sffshm.equals=" + DEFAULT_SFFSHM);

        // Get all the dxSedinfoList where sffshm equals to UPDATED_SFFSHM
        defaultDxSedinfoShouldNotBeFound("sffshm.equals=" + UPDATED_SFFSHM);
    }

    @Test
    @Transactional
    void getAllDxSedinfosBySffshmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where sffshm not equals to DEFAULT_SFFSHM
        defaultDxSedinfoShouldNotBeFound("sffshm.notEquals=" + DEFAULT_SFFSHM);

        // Get all the dxSedinfoList where sffshm not equals to UPDATED_SFFSHM
        defaultDxSedinfoShouldBeFound("sffshm.notEquals=" + UPDATED_SFFSHM);
    }

    @Test
    @Transactional
    void getAllDxSedinfosBySffshmIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where sffshm in DEFAULT_SFFSHM or UPDATED_SFFSHM
        defaultDxSedinfoShouldBeFound("sffshm.in=" + DEFAULT_SFFSHM + "," + UPDATED_SFFSHM);

        // Get all the dxSedinfoList where sffshm equals to UPDATED_SFFSHM
        defaultDxSedinfoShouldNotBeFound("sffshm.in=" + UPDATED_SFFSHM);
    }

    @Test
    @Transactional
    void getAllDxSedinfosBySffshmIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where sffshm is not null
        defaultDxSedinfoShouldBeFound("sffshm.specified=true");

        // Get all the dxSedinfoList where sffshm is null
        defaultDxSedinfoShouldNotBeFound("sffshm.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosBySffshmContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where sffshm contains DEFAULT_SFFSHM
        defaultDxSedinfoShouldBeFound("sffshm.contains=" + DEFAULT_SFFSHM);

        // Get all the dxSedinfoList where sffshm contains UPDATED_SFFSHM
        defaultDxSedinfoShouldNotBeFound("sffshm.contains=" + UPDATED_SFFSHM);
    }

    @Test
    @Transactional
    void getAllDxSedinfosBySffshmNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where sffshm does not contain DEFAULT_SFFSHM
        defaultDxSedinfoShouldNotBeFound("sffshm.doesNotContain=" + DEFAULT_SFFSHM);

        // Get all the dxSedinfoList where sffshm does not contain UPDATED_SFFSHM
        defaultDxSedinfoShouldBeFound("sffshm.doesNotContain=" + UPDATED_SFFSHM);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzdxIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzdx equals to DEFAULT_RZDX
        defaultDxSedinfoShouldBeFound("rzdx.equals=" + DEFAULT_RZDX);

        // Get all the dxSedinfoList where rzdx equals to UPDATED_RZDX
        defaultDxSedinfoShouldNotBeFound("rzdx.equals=" + UPDATED_RZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzdxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzdx not equals to DEFAULT_RZDX
        defaultDxSedinfoShouldNotBeFound("rzdx.notEquals=" + DEFAULT_RZDX);

        // Get all the dxSedinfoList where rzdx not equals to UPDATED_RZDX
        defaultDxSedinfoShouldBeFound("rzdx.notEquals=" + UPDATED_RZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzdxIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzdx in DEFAULT_RZDX or UPDATED_RZDX
        defaultDxSedinfoShouldBeFound("rzdx.in=" + DEFAULT_RZDX + "," + UPDATED_RZDX);

        // Get all the dxSedinfoList where rzdx equals to UPDATED_RZDX
        defaultDxSedinfoShouldNotBeFound("rzdx.in=" + UPDATED_RZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzdxIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzdx is not null
        defaultDxSedinfoShouldBeFound("rzdx.specified=true");

        // Get all the dxSedinfoList where rzdx is null
        defaultDxSedinfoShouldNotBeFound("rzdx.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzdxContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzdx contains DEFAULT_RZDX
        defaultDxSedinfoShouldBeFound("rzdx.contains=" + DEFAULT_RZDX);

        // Get all the dxSedinfoList where rzdx contains UPDATED_RZDX
        defaultDxSedinfoShouldNotBeFound("rzdx.contains=" + UPDATED_RZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzdxNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzdx does not contain DEFAULT_RZDX
        defaultDxSedinfoShouldNotBeFound("rzdx.doesNotContain=" + DEFAULT_RZDX);

        // Get all the dxSedinfoList where rzdx does not contain UPDATED_RZDX
        defaultDxSedinfoShouldBeFound("rzdx.doesNotContain=" + UPDATED_RZDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzdxroomnIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzdxroomn equals to DEFAULT_RZDXROOMN
        defaultDxSedinfoShouldBeFound("rzdxroomn.equals=" + DEFAULT_RZDXROOMN);

        // Get all the dxSedinfoList where rzdxroomn equals to UPDATED_RZDXROOMN
        defaultDxSedinfoShouldNotBeFound("rzdxroomn.equals=" + UPDATED_RZDXROOMN);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzdxroomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzdxroomn not equals to DEFAULT_RZDXROOMN
        defaultDxSedinfoShouldNotBeFound("rzdxroomn.notEquals=" + DEFAULT_RZDXROOMN);

        // Get all the dxSedinfoList where rzdxroomn not equals to UPDATED_RZDXROOMN
        defaultDxSedinfoShouldBeFound("rzdxroomn.notEquals=" + UPDATED_RZDXROOMN);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzdxroomnIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzdxroomn in DEFAULT_RZDXROOMN or UPDATED_RZDXROOMN
        defaultDxSedinfoShouldBeFound("rzdxroomn.in=" + DEFAULT_RZDXROOMN + "," + UPDATED_RZDXROOMN);

        // Get all the dxSedinfoList where rzdxroomn equals to UPDATED_RZDXROOMN
        defaultDxSedinfoShouldNotBeFound("rzdxroomn.in=" + UPDATED_RZDXROOMN);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzdxroomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzdxroomn is not null
        defaultDxSedinfoShouldBeFound("rzdxroomn.specified=true");

        // Get all the dxSedinfoList where rzdxroomn is null
        defaultDxSedinfoShouldNotBeFound("rzdxroomn.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzdxroomnContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzdxroomn contains DEFAULT_RZDXROOMN
        defaultDxSedinfoShouldBeFound("rzdxroomn.contains=" + DEFAULT_RZDXROOMN);

        // Get all the dxSedinfoList where rzdxroomn contains UPDATED_RZDXROOMN
        defaultDxSedinfoShouldNotBeFound("rzdxroomn.contains=" + UPDATED_RZDXROOMN);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzdxroomnNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzdxroomn does not contain DEFAULT_RZDXROOMN
        defaultDxSedinfoShouldNotBeFound("rzdxroomn.doesNotContain=" + DEFAULT_RZDXROOMN);

        // Get all the dxSedinfoList where rzdxroomn does not contain UPDATED_RZDXROOMN
        defaultDxSedinfoShouldBeFound("rzdxroomn.doesNotContain=" + UPDATED_RZDXROOMN);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByJfdzIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where jfdz equals to DEFAULT_JFDZ
        defaultDxSedinfoShouldBeFound("jfdz.equals=" + DEFAULT_JFDZ);

        // Get all the dxSedinfoList where jfdz equals to UPDATED_JFDZ
        defaultDxSedinfoShouldNotBeFound("jfdz.equals=" + UPDATED_JFDZ);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByJfdzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where jfdz not equals to DEFAULT_JFDZ
        defaultDxSedinfoShouldNotBeFound("jfdz.notEquals=" + DEFAULT_JFDZ);

        // Get all the dxSedinfoList where jfdz not equals to UPDATED_JFDZ
        defaultDxSedinfoShouldBeFound("jfdz.notEquals=" + UPDATED_JFDZ);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByJfdzIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where jfdz in DEFAULT_JFDZ or UPDATED_JFDZ
        defaultDxSedinfoShouldBeFound("jfdz.in=" + DEFAULT_JFDZ + "," + UPDATED_JFDZ);

        // Get all the dxSedinfoList where jfdz equals to UPDATED_JFDZ
        defaultDxSedinfoShouldNotBeFound("jfdz.in=" + UPDATED_JFDZ);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByJfdzIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where jfdz is not null
        defaultDxSedinfoShouldBeFound("jfdz.specified=true");

        // Get all the dxSedinfoList where jfdz is null
        defaultDxSedinfoShouldNotBeFound("jfdz.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByJfdzContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where jfdz contains DEFAULT_JFDZ
        defaultDxSedinfoShouldBeFound("jfdz.contains=" + DEFAULT_JFDZ);

        // Get all the dxSedinfoList where jfdz contains UPDATED_JFDZ
        defaultDxSedinfoShouldNotBeFound("jfdz.contains=" + UPDATED_JFDZ);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByJfdzNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where jfdz does not contain DEFAULT_JFDZ
        defaultDxSedinfoShouldNotBeFound("jfdz.doesNotContain=" + DEFAULT_JFDZ);

        // Get all the dxSedinfoList where jfdz does not contain UPDATED_JFDZ
        defaultDxSedinfoShouldBeFound("jfdz.doesNotContain=" + UPDATED_JFDZ);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByBlhyIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where blhy equals to DEFAULT_BLHY
        defaultDxSedinfoShouldBeFound("blhy.equals=" + DEFAULT_BLHY);

        // Get all the dxSedinfoList where blhy equals to UPDATED_BLHY
        defaultDxSedinfoShouldNotBeFound("blhy.equals=" + UPDATED_BLHY);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByBlhyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where blhy not equals to DEFAULT_BLHY
        defaultDxSedinfoShouldNotBeFound("blhy.notEquals=" + DEFAULT_BLHY);

        // Get all the dxSedinfoList where blhy not equals to UPDATED_BLHY
        defaultDxSedinfoShouldBeFound("blhy.notEquals=" + UPDATED_BLHY);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByBlhyIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where blhy in DEFAULT_BLHY or UPDATED_BLHY
        defaultDxSedinfoShouldBeFound("blhy.in=" + DEFAULT_BLHY + "," + UPDATED_BLHY);

        // Get all the dxSedinfoList where blhy equals to UPDATED_BLHY
        defaultDxSedinfoShouldNotBeFound("blhy.in=" + UPDATED_BLHY);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByBlhyIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where blhy is not null
        defaultDxSedinfoShouldBeFound("blhy.specified=true");

        // Get all the dxSedinfoList where blhy is null
        defaultDxSedinfoShouldNotBeFound("blhy.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByBlhyContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where blhy contains DEFAULT_BLHY
        defaultDxSedinfoShouldBeFound("blhy.contains=" + DEFAULT_BLHY);

        // Get all the dxSedinfoList where blhy contains UPDATED_BLHY
        defaultDxSedinfoShouldNotBeFound("blhy.contains=" + UPDATED_BLHY);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByBlhyNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where blhy does not contain DEFAULT_BLHY
        defaultDxSedinfoShouldNotBeFound("blhy.doesNotContain=" + DEFAULT_BLHY);

        // Get all the dxSedinfoList where blhy does not contain UPDATED_BLHY
        defaultDxSedinfoShouldBeFound("blhy.doesNotContain=" + UPDATED_BLHY);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzmemoIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzmemo equals to DEFAULT_RZMEMO
        defaultDxSedinfoShouldBeFound("rzmemo.equals=" + DEFAULT_RZMEMO);

        // Get all the dxSedinfoList where rzmemo equals to UPDATED_RZMEMO
        defaultDxSedinfoShouldNotBeFound("rzmemo.equals=" + UPDATED_RZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzmemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzmemo not equals to DEFAULT_RZMEMO
        defaultDxSedinfoShouldNotBeFound("rzmemo.notEquals=" + DEFAULT_RZMEMO);

        // Get all the dxSedinfoList where rzmemo not equals to UPDATED_RZMEMO
        defaultDxSedinfoShouldBeFound("rzmemo.notEquals=" + UPDATED_RZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzmemoIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzmemo in DEFAULT_RZMEMO or UPDATED_RZMEMO
        defaultDxSedinfoShouldBeFound("rzmemo.in=" + DEFAULT_RZMEMO + "," + UPDATED_RZMEMO);

        // Get all the dxSedinfoList where rzmemo equals to UPDATED_RZMEMO
        defaultDxSedinfoShouldNotBeFound("rzmemo.in=" + UPDATED_RZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzmemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzmemo is not null
        defaultDxSedinfoShouldBeFound("rzmemo.specified=true");

        // Get all the dxSedinfoList where rzmemo is null
        defaultDxSedinfoShouldNotBeFound("rzmemo.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzmemoContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzmemo contains DEFAULT_RZMEMO
        defaultDxSedinfoShouldBeFound("rzmemo.contains=" + DEFAULT_RZMEMO);

        // Get all the dxSedinfoList where rzmemo contains UPDATED_RZMEMO
        defaultDxSedinfoShouldNotBeFound("rzmemo.contains=" + UPDATED_RZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByRzmemoNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where rzmemo does not contain DEFAULT_RZMEMO
        defaultDxSedinfoShouldNotBeFound("rzmemo.doesNotContain=" + DEFAULT_RZMEMO);

        // Get all the dxSedinfoList where rzmemo does not contain UPDATED_RZMEMO
        defaultDxSedinfoShouldBeFound("rzmemo.doesNotContain=" + UPDATED_RZMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByBlhymemoIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where blhymemo equals to DEFAULT_BLHYMEMO
        defaultDxSedinfoShouldBeFound("blhymemo.equals=" + DEFAULT_BLHYMEMO);

        // Get all the dxSedinfoList where blhymemo equals to UPDATED_BLHYMEMO
        defaultDxSedinfoShouldNotBeFound("blhymemo.equals=" + UPDATED_BLHYMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByBlhymemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where blhymemo not equals to DEFAULT_BLHYMEMO
        defaultDxSedinfoShouldNotBeFound("blhymemo.notEquals=" + DEFAULT_BLHYMEMO);

        // Get all the dxSedinfoList where blhymemo not equals to UPDATED_BLHYMEMO
        defaultDxSedinfoShouldBeFound("blhymemo.notEquals=" + UPDATED_BLHYMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByBlhymemoIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where blhymemo in DEFAULT_BLHYMEMO or UPDATED_BLHYMEMO
        defaultDxSedinfoShouldBeFound("blhymemo.in=" + DEFAULT_BLHYMEMO + "," + UPDATED_BLHYMEMO);

        // Get all the dxSedinfoList where blhymemo equals to UPDATED_BLHYMEMO
        defaultDxSedinfoShouldNotBeFound("blhymemo.in=" + UPDATED_BLHYMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByBlhymemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where blhymemo is not null
        defaultDxSedinfoShouldBeFound("blhymemo.specified=true");

        // Get all the dxSedinfoList where blhymemo is null
        defaultDxSedinfoShouldNotBeFound("blhymemo.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByBlhymemoContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where blhymemo contains DEFAULT_BLHYMEMO
        defaultDxSedinfoShouldBeFound("blhymemo.contains=" + DEFAULT_BLHYMEMO);

        // Get all the dxSedinfoList where blhymemo contains UPDATED_BLHYMEMO
        defaultDxSedinfoShouldNotBeFound("blhymemo.contains=" + UPDATED_BLHYMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByBlhymemoNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where blhymemo does not contain DEFAULT_BLHYMEMO
        defaultDxSedinfoShouldNotBeFound("blhymemo.doesNotContain=" + DEFAULT_BLHYMEMO);

        // Get all the dxSedinfoList where blhymemo does not contain UPDATED_BLHYMEMO
        defaultDxSedinfoShouldBeFound("blhymemo.doesNotContain=" + UPDATED_BLHYMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByTfdxIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where tfdx equals to DEFAULT_TFDX
        defaultDxSedinfoShouldBeFound("tfdx.equals=" + DEFAULT_TFDX);

        // Get all the dxSedinfoList where tfdx equals to UPDATED_TFDX
        defaultDxSedinfoShouldNotBeFound("tfdx.equals=" + UPDATED_TFDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByTfdxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where tfdx not equals to DEFAULT_TFDX
        defaultDxSedinfoShouldNotBeFound("tfdx.notEquals=" + DEFAULT_TFDX);

        // Get all the dxSedinfoList where tfdx not equals to UPDATED_TFDX
        defaultDxSedinfoShouldBeFound("tfdx.notEquals=" + UPDATED_TFDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByTfdxIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where tfdx in DEFAULT_TFDX or UPDATED_TFDX
        defaultDxSedinfoShouldBeFound("tfdx.in=" + DEFAULT_TFDX + "," + UPDATED_TFDX);

        // Get all the dxSedinfoList where tfdx equals to UPDATED_TFDX
        defaultDxSedinfoShouldNotBeFound("tfdx.in=" + UPDATED_TFDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByTfdxIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where tfdx is not null
        defaultDxSedinfoShouldBeFound("tfdx.specified=true");

        // Get all the dxSedinfoList where tfdx is null
        defaultDxSedinfoShouldNotBeFound("tfdx.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByTfdxContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where tfdx contains DEFAULT_TFDX
        defaultDxSedinfoShouldBeFound("tfdx.contains=" + DEFAULT_TFDX);

        // Get all the dxSedinfoList where tfdx contains UPDATED_TFDX
        defaultDxSedinfoShouldNotBeFound("tfdx.contains=" + UPDATED_TFDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByTfdxNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where tfdx does not contain DEFAULT_TFDX
        defaultDxSedinfoShouldNotBeFound("tfdx.doesNotContain=" + DEFAULT_TFDX);

        // Get all the dxSedinfoList where tfdx does not contain UPDATED_TFDX
        defaultDxSedinfoShouldBeFound("tfdx.doesNotContain=" + UPDATED_TFDX);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByTfdxmemoIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where tfdxmemo equals to DEFAULT_TFDXMEMO
        defaultDxSedinfoShouldBeFound("tfdxmemo.equals=" + DEFAULT_TFDXMEMO);

        // Get all the dxSedinfoList where tfdxmemo equals to UPDATED_TFDXMEMO
        defaultDxSedinfoShouldNotBeFound("tfdxmemo.equals=" + UPDATED_TFDXMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByTfdxmemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where tfdxmemo not equals to DEFAULT_TFDXMEMO
        defaultDxSedinfoShouldNotBeFound("tfdxmemo.notEquals=" + DEFAULT_TFDXMEMO);

        // Get all the dxSedinfoList where tfdxmemo not equals to UPDATED_TFDXMEMO
        defaultDxSedinfoShouldBeFound("tfdxmemo.notEquals=" + UPDATED_TFDXMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByTfdxmemoIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where tfdxmemo in DEFAULT_TFDXMEMO or UPDATED_TFDXMEMO
        defaultDxSedinfoShouldBeFound("tfdxmemo.in=" + DEFAULT_TFDXMEMO + "," + UPDATED_TFDXMEMO);

        // Get all the dxSedinfoList where tfdxmemo equals to UPDATED_TFDXMEMO
        defaultDxSedinfoShouldNotBeFound("tfdxmemo.in=" + UPDATED_TFDXMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByTfdxmemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where tfdxmemo is not null
        defaultDxSedinfoShouldBeFound("tfdxmemo.specified=true");

        // Get all the dxSedinfoList where tfdxmemo is null
        defaultDxSedinfoShouldNotBeFound("tfdxmemo.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByTfdxmemoContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where tfdxmemo contains DEFAULT_TFDXMEMO
        defaultDxSedinfoShouldBeFound("tfdxmemo.contains=" + DEFAULT_TFDXMEMO);

        // Get all the dxSedinfoList where tfdxmemo contains UPDATED_TFDXMEMO
        defaultDxSedinfoShouldNotBeFound("tfdxmemo.contains=" + UPDATED_TFDXMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByTfdxmemoNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where tfdxmemo does not contain DEFAULT_TFDXMEMO
        defaultDxSedinfoShouldNotBeFound("tfdxmemo.doesNotContain=" + DEFAULT_TFDXMEMO);

        // Get all the dxSedinfoList where tfdxmemo does not contain UPDATED_TFDXMEMO
        defaultDxSedinfoShouldBeFound("tfdxmemo.doesNotContain=" + UPDATED_TFDXMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFslbIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fslb equals to DEFAULT_FSLB
        defaultDxSedinfoShouldBeFound("fslb.equals=" + DEFAULT_FSLB);

        // Get all the dxSedinfoList where fslb equals to UPDATED_FSLB
        defaultDxSedinfoShouldNotBeFound("fslb.equals=" + UPDATED_FSLB);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFslbIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fslb not equals to DEFAULT_FSLB
        defaultDxSedinfoShouldNotBeFound("fslb.notEquals=" + DEFAULT_FSLB);

        // Get all the dxSedinfoList where fslb not equals to UPDATED_FSLB
        defaultDxSedinfoShouldBeFound("fslb.notEquals=" + UPDATED_FSLB);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFslbIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fslb in DEFAULT_FSLB or UPDATED_FSLB
        defaultDxSedinfoShouldBeFound("fslb.in=" + DEFAULT_FSLB + "," + UPDATED_FSLB);

        // Get all the dxSedinfoList where fslb equals to UPDATED_FSLB
        defaultDxSedinfoShouldNotBeFound("fslb.in=" + UPDATED_FSLB);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFslbIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fslb is not null
        defaultDxSedinfoShouldBeFound("fslb.specified=true");

        // Get all the dxSedinfoList where fslb is null
        defaultDxSedinfoShouldNotBeFound("fslb.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFslbContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fslb contains DEFAULT_FSLB
        defaultDxSedinfoShouldBeFound("fslb.contains=" + DEFAULT_FSLB);

        // Get all the dxSedinfoList where fslb contains UPDATED_FSLB
        defaultDxSedinfoShouldNotBeFound("fslb.contains=" + UPDATED_FSLB);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFslbNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fslb does not contain DEFAULT_FSLB
        defaultDxSedinfoShouldNotBeFound("fslb.doesNotContain=" + DEFAULT_FSLB);

        // Get all the dxSedinfoList where fslb does not contain UPDATED_FSLB
        defaultDxSedinfoShouldBeFound("fslb.doesNotContain=" + UPDATED_FSLB);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFslbmemoIsEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fslbmemo equals to DEFAULT_FSLBMEMO
        defaultDxSedinfoShouldBeFound("fslbmemo.equals=" + DEFAULT_FSLBMEMO);

        // Get all the dxSedinfoList where fslbmemo equals to UPDATED_FSLBMEMO
        defaultDxSedinfoShouldNotBeFound("fslbmemo.equals=" + UPDATED_FSLBMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFslbmemoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fslbmemo not equals to DEFAULT_FSLBMEMO
        defaultDxSedinfoShouldNotBeFound("fslbmemo.notEquals=" + DEFAULT_FSLBMEMO);

        // Get all the dxSedinfoList where fslbmemo not equals to UPDATED_FSLBMEMO
        defaultDxSedinfoShouldBeFound("fslbmemo.notEquals=" + UPDATED_FSLBMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFslbmemoIsInShouldWork() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fslbmemo in DEFAULT_FSLBMEMO or UPDATED_FSLBMEMO
        defaultDxSedinfoShouldBeFound("fslbmemo.in=" + DEFAULT_FSLBMEMO + "," + UPDATED_FSLBMEMO);

        // Get all the dxSedinfoList where fslbmemo equals to UPDATED_FSLBMEMO
        defaultDxSedinfoShouldNotBeFound("fslbmemo.in=" + UPDATED_FSLBMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFslbmemoIsNullOrNotNull() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fslbmemo is not null
        defaultDxSedinfoShouldBeFound("fslbmemo.specified=true");

        // Get all the dxSedinfoList where fslbmemo is null
        defaultDxSedinfoShouldNotBeFound("fslbmemo.specified=false");
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFslbmemoContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fslbmemo contains DEFAULT_FSLBMEMO
        defaultDxSedinfoShouldBeFound("fslbmemo.contains=" + DEFAULT_FSLBMEMO);

        // Get all the dxSedinfoList where fslbmemo contains UPDATED_FSLBMEMO
        defaultDxSedinfoShouldNotBeFound("fslbmemo.contains=" + UPDATED_FSLBMEMO);
    }

    @Test
    @Transactional
    void getAllDxSedinfosByFslbmemoNotContainsSomething() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        // Get all the dxSedinfoList where fslbmemo does not contain DEFAULT_FSLBMEMO
        defaultDxSedinfoShouldNotBeFound("fslbmemo.doesNotContain=" + DEFAULT_FSLBMEMO);

        // Get all the dxSedinfoList where fslbmemo does not contain UPDATED_FSLBMEMO
        defaultDxSedinfoShouldBeFound("fslbmemo.doesNotContain=" + UPDATED_FSLBMEMO);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDxSedinfoShouldBeFound(String filter) throws Exception {
        restDxSedinfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dxSedinfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].yddx").value(hasItem(DEFAULT_YDDX)))
            .andExpect(jsonPath("$.[*].yddxmemo").value(hasItem(DEFAULT_YDDXMEMO)))
            .andExpect(jsonPath("$.[*].qxyddx").value(hasItem(DEFAULT_QXYDDX)))
            .andExpect(jsonPath("$.[*].qxydmemo").value(hasItem(DEFAULT_QXYDMEMO)))
            .andExpect(jsonPath("$.[*].czdx").value(hasItem(DEFAULT_CZDX)))
            .andExpect(jsonPath("$.[*].czmemo").value(hasItem(DEFAULT_CZMEMO)))
            .andExpect(jsonPath("$.[*].qxczdx").value(hasItem(DEFAULT_QXCZDX)))
            .andExpect(jsonPath("$.[*].qxczmemo").value(hasItem(DEFAULT_QXCZMEMO)))
            .andExpect(jsonPath("$.[*].yyedx").value(hasItem(DEFAULT_YYEDX)))
            .andExpect(jsonPath("$.[*].yyememo").value(hasItem(DEFAULT_YYEMEMO)))
            .andExpect(jsonPath("$.[*].fstime").value(hasItem(DEFAULT_FSTIME)))
            .andExpect(jsonPath("$.[*].sffshm").value(hasItem(DEFAULT_SFFSHM)))
            .andExpect(jsonPath("$.[*].rzdx").value(hasItem(DEFAULT_RZDX)))
            .andExpect(jsonPath("$.[*].rzdxroomn").value(hasItem(DEFAULT_RZDXROOMN)))
            .andExpect(jsonPath("$.[*].jfdz").value(hasItem(DEFAULT_JFDZ)))
            .andExpect(jsonPath("$.[*].blhy").value(hasItem(DEFAULT_BLHY)))
            .andExpect(jsonPath("$.[*].rzmemo").value(hasItem(DEFAULT_RZMEMO)))
            .andExpect(jsonPath("$.[*].blhymemo").value(hasItem(DEFAULT_BLHYMEMO)))
            .andExpect(jsonPath("$.[*].tfdx").value(hasItem(DEFAULT_TFDX)))
            .andExpect(jsonPath("$.[*].tfdxmemo").value(hasItem(DEFAULT_TFDXMEMO)))
            .andExpect(jsonPath("$.[*].fslb").value(hasItem(DEFAULT_FSLB)))
            .andExpect(jsonPath("$.[*].fslbmemo").value(hasItem(DEFAULT_FSLBMEMO)));

        // Check, that the count call also returns 1
        restDxSedinfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDxSedinfoShouldNotBeFound(String filter) throws Exception {
        restDxSedinfoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDxSedinfoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingDxSedinfo() throws Exception {
        // Get the dxSedinfo
        restDxSedinfoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDxSedinfo() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        int databaseSizeBeforeUpdate = dxSedinfoRepository.findAll().size();

        // Update the dxSedinfo
        DxSedinfo updatedDxSedinfo = dxSedinfoRepository.findById(dxSedinfo.getId()).get();
        // Disconnect from session so that the updates on updatedDxSedinfo are not directly saved in db
        em.detach(updatedDxSedinfo);
        updatedDxSedinfo
            .yddx(UPDATED_YDDX)
            .yddxmemo(UPDATED_YDDXMEMO)
            .qxyddx(UPDATED_QXYDDX)
            .qxydmemo(UPDATED_QXYDMEMO)
            .czdx(UPDATED_CZDX)
            .czmemo(UPDATED_CZMEMO)
            .qxczdx(UPDATED_QXCZDX)
            .qxczmemo(UPDATED_QXCZMEMO)
            .yyedx(UPDATED_YYEDX)
            .yyememo(UPDATED_YYEMEMO)
            .fstime(UPDATED_FSTIME)
            .sffshm(UPDATED_SFFSHM)
            .rzdx(UPDATED_RZDX)
            .rzdxroomn(UPDATED_RZDXROOMN)
            .jfdz(UPDATED_JFDZ)
            .blhy(UPDATED_BLHY)
            .rzmemo(UPDATED_RZMEMO)
            .blhymemo(UPDATED_BLHYMEMO)
            .tfdx(UPDATED_TFDX)
            .tfdxmemo(UPDATED_TFDXMEMO)
            .fslb(UPDATED_FSLB)
            .fslbmemo(UPDATED_FSLBMEMO);
        DxSedinfoDTO dxSedinfoDTO = dxSedinfoMapper.toDto(updatedDxSedinfo);

        restDxSedinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dxSedinfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dxSedinfoDTO))
            )
            .andExpect(status().isOk());

        // Validate the DxSedinfo in the database
        List<DxSedinfo> dxSedinfoList = dxSedinfoRepository.findAll();
        assertThat(dxSedinfoList).hasSize(databaseSizeBeforeUpdate);
        DxSedinfo testDxSedinfo = dxSedinfoList.get(dxSedinfoList.size() - 1);
        assertThat(testDxSedinfo.getYddx()).isEqualTo(UPDATED_YDDX);
        assertThat(testDxSedinfo.getYddxmemo()).isEqualTo(UPDATED_YDDXMEMO);
        assertThat(testDxSedinfo.getQxyddx()).isEqualTo(UPDATED_QXYDDX);
        assertThat(testDxSedinfo.getQxydmemo()).isEqualTo(UPDATED_QXYDMEMO);
        assertThat(testDxSedinfo.getCzdx()).isEqualTo(UPDATED_CZDX);
        assertThat(testDxSedinfo.getCzmemo()).isEqualTo(UPDATED_CZMEMO);
        assertThat(testDxSedinfo.getQxczdx()).isEqualTo(UPDATED_QXCZDX);
        assertThat(testDxSedinfo.getQxczmemo()).isEqualTo(UPDATED_QXCZMEMO);
        assertThat(testDxSedinfo.getYyedx()).isEqualTo(UPDATED_YYEDX);
        assertThat(testDxSedinfo.getYyememo()).isEqualTo(UPDATED_YYEMEMO);
        assertThat(testDxSedinfo.getFstime()).isEqualTo(UPDATED_FSTIME);
        assertThat(testDxSedinfo.getSffshm()).isEqualTo(UPDATED_SFFSHM);
        assertThat(testDxSedinfo.getRzdx()).isEqualTo(UPDATED_RZDX);
        assertThat(testDxSedinfo.getRzdxroomn()).isEqualTo(UPDATED_RZDXROOMN);
        assertThat(testDxSedinfo.getJfdz()).isEqualTo(UPDATED_JFDZ);
        assertThat(testDxSedinfo.getBlhy()).isEqualTo(UPDATED_BLHY);
        assertThat(testDxSedinfo.getRzmemo()).isEqualTo(UPDATED_RZMEMO);
        assertThat(testDxSedinfo.getBlhymemo()).isEqualTo(UPDATED_BLHYMEMO);
        assertThat(testDxSedinfo.getTfdx()).isEqualTo(UPDATED_TFDX);
        assertThat(testDxSedinfo.getTfdxmemo()).isEqualTo(UPDATED_TFDXMEMO);
        assertThat(testDxSedinfo.getFslb()).isEqualTo(UPDATED_FSLB);
        assertThat(testDxSedinfo.getFslbmemo()).isEqualTo(UPDATED_FSLBMEMO);

        // Validate the DxSedinfo in Elasticsearch
        verify(mockDxSedinfoSearchRepository).save(testDxSedinfo);
    }

    @Test
    @Transactional
    void putNonExistingDxSedinfo() throws Exception {
        int databaseSizeBeforeUpdate = dxSedinfoRepository.findAll().size();
        dxSedinfo.setId(count.incrementAndGet());

        // Create the DxSedinfo
        DxSedinfoDTO dxSedinfoDTO = dxSedinfoMapper.toDto(dxSedinfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDxSedinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, dxSedinfoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dxSedinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DxSedinfo in the database
        List<DxSedinfo> dxSedinfoList = dxSedinfoRepository.findAll();
        assertThat(dxSedinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DxSedinfo in Elasticsearch
        verify(mockDxSedinfoSearchRepository, times(0)).save(dxSedinfo);
    }

    @Test
    @Transactional
    void putWithIdMismatchDxSedinfo() throws Exception {
        int databaseSizeBeforeUpdate = dxSedinfoRepository.findAll().size();
        dxSedinfo.setId(count.incrementAndGet());

        // Create the DxSedinfo
        DxSedinfoDTO dxSedinfoDTO = dxSedinfoMapper.toDto(dxSedinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDxSedinfoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(dxSedinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DxSedinfo in the database
        List<DxSedinfo> dxSedinfoList = dxSedinfoRepository.findAll();
        assertThat(dxSedinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DxSedinfo in Elasticsearch
        verify(mockDxSedinfoSearchRepository, times(0)).save(dxSedinfo);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDxSedinfo() throws Exception {
        int databaseSizeBeforeUpdate = dxSedinfoRepository.findAll().size();
        dxSedinfo.setId(count.incrementAndGet());

        // Create the DxSedinfo
        DxSedinfoDTO dxSedinfoDTO = dxSedinfoMapper.toDto(dxSedinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDxSedinfoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(dxSedinfoDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the DxSedinfo in the database
        List<DxSedinfo> dxSedinfoList = dxSedinfoRepository.findAll();
        assertThat(dxSedinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DxSedinfo in Elasticsearch
        verify(mockDxSedinfoSearchRepository, times(0)).save(dxSedinfo);
    }

    @Test
    @Transactional
    void partialUpdateDxSedinfoWithPatch() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        int databaseSizeBeforeUpdate = dxSedinfoRepository.findAll().size();

        // Update the dxSedinfo using partial update
        DxSedinfo partialUpdatedDxSedinfo = new DxSedinfo();
        partialUpdatedDxSedinfo.setId(dxSedinfo.getId());

        partialUpdatedDxSedinfo
            .yddx(UPDATED_YDDX)
            .yddxmemo(UPDATED_YDDXMEMO)
            .czdx(UPDATED_CZDX)
            .czmemo(UPDATED_CZMEMO)
            .qxczdx(UPDATED_QXCZDX)
            .qxczmemo(UPDATED_QXCZMEMO)
            .yyememo(UPDATED_YYEMEMO)
            .fstime(UPDATED_FSTIME)
            .rzdxroomn(UPDATED_RZDXROOMN)
            .jfdz(UPDATED_JFDZ)
            .rzmemo(UPDATED_RZMEMO)
            .tfdxmemo(UPDATED_TFDXMEMO)
            .fslbmemo(UPDATED_FSLBMEMO);

        restDxSedinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDxSedinfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDxSedinfo))
            )
            .andExpect(status().isOk());

        // Validate the DxSedinfo in the database
        List<DxSedinfo> dxSedinfoList = dxSedinfoRepository.findAll();
        assertThat(dxSedinfoList).hasSize(databaseSizeBeforeUpdate);
        DxSedinfo testDxSedinfo = dxSedinfoList.get(dxSedinfoList.size() - 1);
        assertThat(testDxSedinfo.getYddx()).isEqualTo(UPDATED_YDDX);
        assertThat(testDxSedinfo.getYddxmemo()).isEqualTo(UPDATED_YDDXMEMO);
        assertThat(testDxSedinfo.getQxyddx()).isEqualTo(DEFAULT_QXYDDX);
        assertThat(testDxSedinfo.getQxydmemo()).isEqualTo(DEFAULT_QXYDMEMO);
        assertThat(testDxSedinfo.getCzdx()).isEqualTo(UPDATED_CZDX);
        assertThat(testDxSedinfo.getCzmemo()).isEqualTo(UPDATED_CZMEMO);
        assertThat(testDxSedinfo.getQxczdx()).isEqualTo(UPDATED_QXCZDX);
        assertThat(testDxSedinfo.getQxczmemo()).isEqualTo(UPDATED_QXCZMEMO);
        assertThat(testDxSedinfo.getYyedx()).isEqualTo(DEFAULT_YYEDX);
        assertThat(testDxSedinfo.getYyememo()).isEqualTo(UPDATED_YYEMEMO);
        assertThat(testDxSedinfo.getFstime()).isEqualTo(UPDATED_FSTIME);
        assertThat(testDxSedinfo.getSffshm()).isEqualTo(DEFAULT_SFFSHM);
        assertThat(testDxSedinfo.getRzdx()).isEqualTo(DEFAULT_RZDX);
        assertThat(testDxSedinfo.getRzdxroomn()).isEqualTo(UPDATED_RZDXROOMN);
        assertThat(testDxSedinfo.getJfdz()).isEqualTo(UPDATED_JFDZ);
        assertThat(testDxSedinfo.getBlhy()).isEqualTo(DEFAULT_BLHY);
        assertThat(testDxSedinfo.getRzmemo()).isEqualTo(UPDATED_RZMEMO);
        assertThat(testDxSedinfo.getBlhymemo()).isEqualTo(DEFAULT_BLHYMEMO);
        assertThat(testDxSedinfo.getTfdx()).isEqualTo(DEFAULT_TFDX);
        assertThat(testDxSedinfo.getTfdxmemo()).isEqualTo(UPDATED_TFDXMEMO);
        assertThat(testDxSedinfo.getFslb()).isEqualTo(DEFAULT_FSLB);
        assertThat(testDxSedinfo.getFslbmemo()).isEqualTo(UPDATED_FSLBMEMO);
    }

    @Test
    @Transactional
    void fullUpdateDxSedinfoWithPatch() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        int databaseSizeBeforeUpdate = dxSedinfoRepository.findAll().size();

        // Update the dxSedinfo using partial update
        DxSedinfo partialUpdatedDxSedinfo = new DxSedinfo();
        partialUpdatedDxSedinfo.setId(dxSedinfo.getId());

        partialUpdatedDxSedinfo
            .yddx(UPDATED_YDDX)
            .yddxmemo(UPDATED_YDDXMEMO)
            .qxyddx(UPDATED_QXYDDX)
            .qxydmemo(UPDATED_QXYDMEMO)
            .czdx(UPDATED_CZDX)
            .czmemo(UPDATED_CZMEMO)
            .qxczdx(UPDATED_QXCZDX)
            .qxczmemo(UPDATED_QXCZMEMO)
            .yyedx(UPDATED_YYEDX)
            .yyememo(UPDATED_YYEMEMO)
            .fstime(UPDATED_FSTIME)
            .sffshm(UPDATED_SFFSHM)
            .rzdx(UPDATED_RZDX)
            .rzdxroomn(UPDATED_RZDXROOMN)
            .jfdz(UPDATED_JFDZ)
            .blhy(UPDATED_BLHY)
            .rzmemo(UPDATED_RZMEMO)
            .blhymemo(UPDATED_BLHYMEMO)
            .tfdx(UPDATED_TFDX)
            .tfdxmemo(UPDATED_TFDXMEMO)
            .fslb(UPDATED_FSLB)
            .fslbmemo(UPDATED_FSLBMEMO);

        restDxSedinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDxSedinfo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDxSedinfo))
            )
            .andExpect(status().isOk());

        // Validate the DxSedinfo in the database
        List<DxSedinfo> dxSedinfoList = dxSedinfoRepository.findAll();
        assertThat(dxSedinfoList).hasSize(databaseSizeBeforeUpdate);
        DxSedinfo testDxSedinfo = dxSedinfoList.get(dxSedinfoList.size() - 1);
        assertThat(testDxSedinfo.getYddx()).isEqualTo(UPDATED_YDDX);
        assertThat(testDxSedinfo.getYddxmemo()).isEqualTo(UPDATED_YDDXMEMO);
        assertThat(testDxSedinfo.getQxyddx()).isEqualTo(UPDATED_QXYDDX);
        assertThat(testDxSedinfo.getQxydmemo()).isEqualTo(UPDATED_QXYDMEMO);
        assertThat(testDxSedinfo.getCzdx()).isEqualTo(UPDATED_CZDX);
        assertThat(testDxSedinfo.getCzmemo()).isEqualTo(UPDATED_CZMEMO);
        assertThat(testDxSedinfo.getQxczdx()).isEqualTo(UPDATED_QXCZDX);
        assertThat(testDxSedinfo.getQxczmemo()).isEqualTo(UPDATED_QXCZMEMO);
        assertThat(testDxSedinfo.getYyedx()).isEqualTo(UPDATED_YYEDX);
        assertThat(testDxSedinfo.getYyememo()).isEqualTo(UPDATED_YYEMEMO);
        assertThat(testDxSedinfo.getFstime()).isEqualTo(UPDATED_FSTIME);
        assertThat(testDxSedinfo.getSffshm()).isEqualTo(UPDATED_SFFSHM);
        assertThat(testDxSedinfo.getRzdx()).isEqualTo(UPDATED_RZDX);
        assertThat(testDxSedinfo.getRzdxroomn()).isEqualTo(UPDATED_RZDXROOMN);
        assertThat(testDxSedinfo.getJfdz()).isEqualTo(UPDATED_JFDZ);
        assertThat(testDxSedinfo.getBlhy()).isEqualTo(UPDATED_BLHY);
        assertThat(testDxSedinfo.getRzmemo()).isEqualTo(UPDATED_RZMEMO);
        assertThat(testDxSedinfo.getBlhymemo()).isEqualTo(UPDATED_BLHYMEMO);
        assertThat(testDxSedinfo.getTfdx()).isEqualTo(UPDATED_TFDX);
        assertThat(testDxSedinfo.getTfdxmemo()).isEqualTo(UPDATED_TFDXMEMO);
        assertThat(testDxSedinfo.getFslb()).isEqualTo(UPDATED_FSLB);
        assertThat(testDxSedinfo.getFslbmemo()).isEqualTo(UPDATED_FSLBMEMO);
    }

    @Test
    @Transactional
    void patchNonExistingDxSedinfo() throws Exception {
        int databaseSizeBeforeUpdate = dxSedinfoRepository.findAll().size();
        dxSedinfo.setId(count.incrementAndGet());

        // Create the DxSedinfo
        DxSedinfoDTO dxSedinfoDTO = dxSedinfoMapper.toDto(dxSedinfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDxSedinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, dxSedinfoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dxSedinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DxSedinfo in the database
        List<DxSedinfo> dxSedinfoList = dxSedinfoRepository.findAll();
        assertThat(dxSedinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DxSedinfo in Elasticsearch
        verify(mockDxSedinfoSearchRepository, times(0)).save(dxSedinfo);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDxSedinfo() throws Exception {
        int databaseSizeBeforeUpdate = dxSedinfoRepository.findAll().size();
        dxSedinfo.setId(count.incrementAndGet());

        // Create the DxSedinfo
        DxSedinfoDTO dxSedinfoDTO = dxSedinfoMapper.toDto(dxSedinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDxSedinfoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(dxSedinfoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the DxSedinfo in the database
        List<DxSedinfo> dxSedinfoList = dxSedinfoRepository.findAll();
        assertThat(dxSedinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DxSedinfo in Elasticsearch
        verify(mockDxSedinfoSearchRepository, times(0)).save(dxSedinfo);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDxSedinfo() throws Exception {
        int databaseSizeBeforeUpdate = dxSedinfoRepository.findAll().size();
        dxSedinfo.setId(count.incrementAndGet());

        // Create the DxSedinfo
        DxSedinfoDTO dxSedinfoDTO = dxSedinfoMapper.toDto(dxSedinfo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDxSedinfoMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(dxSedinfoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the DxSedinfo in the database
        List<DxSedinfo> dxSedinfoList = dxSedinfoRepository.findAll();
        assertThat(dxSedinfoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DxSedinfo in Elasticsearch
        verify(mockDxSedinfoSearchRepository, times(0)).save(dxSedinfo);
    }

    @Test
    @Transactional
    void deleteDxSedinfo() throws Exception {
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);

        int databaseSizeBeforeDelete = dxSedinfoRepository.findAll().size();

        // Delete the dxSedinfo
        restDxSedinfoMockMvc
            .perform(delete(ENTITY_API_URL_ID, dxSedinfo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DxSedinfo> dxSedinfoList = dxSedinfoRepository.findAll();
        assertThat(dxSedinfoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DxSedinfo in Elasticsearch
        verify(mockDxSedinfoSearchRepository, times(1)).deleteById(dxSedinfo.getId());
    }

    @Test
    @Transactional
    void searchDxSedinfo() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        dxSedinfoRepository.saveAndFlush(dxSedinfo);
        when(mockDxSedinfoSearchRepository.search(queryStringQuery("id:" + dxSedinfo.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(dxSedinfo), PageRequest.of(0, 1), 1));

        // Search the dxSedinfo
        restDxSedinfoMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + dxSedinfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dxSedinfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].yddx").value(hasItem(DEFAULT_YDDX)))
            .andExpect(jsonPath("$.[*].yddxmemo").value(hasItem(DEFAULT_YDDXMEMO)))
            .andExpect(jsonPath("$.[*].qxyddx").value(hasItem(DEFAULT_QXYDDX)))
            .andExpect(jsonPath("$.[*].qxydmemo").value(hasItem(DEFAULT_QXYDMEMO)))
            .andExpect(jsonPath("$.[*].czdx").value(hasItem(DEFAULT_CZDX)))
            .andExpect(jsonPath("$.[*].czmemo").value(hasItem(DEFAULT_CZMEMO)))
            .andExpect(jsonPath("$.[*].qxczdx").value(hasItem(DEFAULT_QXCZDX)))
            .andExpect(jsonPath("$.[*].qxczmemo").value(hasItem(DEFAULT_QXCZMEMO)))
            .andExpect(jsonPath("$.[*].yyedx").value(hasItem(DEFAULT_YYEDX)))
            .andExpect(jsonPath("$.[*].yyememo").value(hasItem(DEFAULT_YYEMEMO)))
            .andExpect(jsonPath("$.[*].fstime").value(hasItem(DEFAULT_FSTIME)))
            .andExpect(jsonPath("$.[*].sffshm").value(hasItem(DEFAULT_SFFSHM)))
            .andExpect(jsonPath("$.[*].rzdx").value(hasItem(DEFAULT_RZDX)))
            .andExpect(jsonPath("$.[*].rzdxroomn").value(hasItem(DEFAULT_RZDXROOMN)))
            .andExpect(jsonPath("$.[*].jfdz").value(hasItem(DEFAULT_JFDZ)))
            .andExpect(jsonPath("$.[*].blhy").value(hasItem(DEFAULT_BLHY)))
            .andExpect(jsonPath("$.[*].rzmemo").value(hasItem(DEFAULT_RZMEMO)))
            .andExpect(jsonPath("$.[*].blhymemo").value(hasItem(DEFAULT_BLHYMEMO)))
            .andExpect(jsonPath("$.[*].tfdx").value(hasItem(DEFAULT_TFDX)))
            .andExpect(jsonPath("$.[*].tfdxmemo").value(hasItem(DEFAULT_TFDXMEMO)))
            .andExpect(jsonPath("$.[*].fslb").value(hasItem(DEFAULT_FSLB)))
            .andExpect(jsonPath("$.[*].fslbmemo").value(hasItem(DEFAULT_FSLBMEMO)));
    }
}
