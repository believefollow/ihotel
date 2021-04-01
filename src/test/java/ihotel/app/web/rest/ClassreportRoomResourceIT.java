package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.ClassreportRoom;
import ihotel.app.repository.ClassreportRoomRepository;
import ihotel.app.repository.search.ClassreportRoomSearchRepository;
import ihotel.app.service.criteria.ClassreportRoomCriteria;
import ihotel.app.service.dto.ClassreportRoomDTO;
import ihotel.app.service.mapper.ClassreportRoomMapper;
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
 * Integration tests for the {@link ClassreportRoomResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClassreportRoomResourceIT {

    private static final String DEFAULT_ACCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_ROOMN = "AAAAAAAAAA";
    private static final String UPDATED_ROOMN = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_YFJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_YFJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_YFJ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_YFJ_9008 = new BigDecimal(1);
    private static final BigDecimal UPDATED_YFJ_9008 = new BigDecimal(2);
    private static final BigDecimal SMALLER_YFJ_9008 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_YFJ_9009 = new BigDecimal(1);
    private static final BigDecimal UPDATED_YFJ_9009 = new BigDecimal(2);
    private static final BigDecimal SMALLER_YFJ_9009 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_YFJ_9007 = new BigDecimal(1);
    private static final BigDecimal UPDATED_YFJ_9007 = new BigDecimal(2);
    private static final BigDecimal SMALLER_YFJ_9007 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_GZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_GZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_GZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_FF = new BigDecimal(1);
    private static final BigDecimal UPDATED_FF = new BigDecimal(2);
    private static final BigDecimal SMALLER_FF = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_MINIBAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_MINIBAR = new BigDecimal(2);
    private static final BigDecimal SMALLER_MINIBAR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PHONE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PHONE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PHONE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_OTHER = new BigDecimal(1);
    private static final BigDecimal UPDATED_OTHER = new BigDecimal(2);
    private static final BigDecimal SMALLER_OTHER = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PC = new BigDecimal(1);
    private static final BigDecimal UPDATED_PC = new BigDecimal(2);
    private static final BigDecimal SMALLER_PC = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CZ = new BigDecimal(1);
    private static final BigDecimal UPDATED_CZ = new BigDecimal(2);
    private static final BigDecimal SMALLER_CZ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CY = new BigDecimal(2);
    private static final BigDecimal SMALLER_CY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_MD = new BigDecimal(1);
    private static final BigDecimal UPDATED_MD = new BigDecimal(2);
    private static final BigDecimal SMALLER_MD = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HUIY = new BigDecimal(1);
    private static final BigDecimal UPDATED_HUIY = new BigDecimal(2);
    private static final BigDecimal SMALLER_HUIY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DTB = new BigDecimal(1);
    private static final BigDecimal UPDATED_DTB = new BigDecimal(2);
    private static final BigDecimal SMALLER_DTB = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SSZX = new BigDecimal(1);
    private static final BigDecimal UPDATED_SSZX = new BigDecimal(2);
    private static final BigDecimal SMALLER_SSZX = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/classreport-rooms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/classreport-rooms";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClassreportRoomRepository classreportRoomRepository;

    @Autowired
    private ClassreportRoomMapper classreportRoomMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.ClassreportRoomSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClassreportRoomSearchRepository mockClassreportRoomSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassreportRoomMockMvc;

    private ClassreportRoom classreportRoom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassreportRoom createEntity(EntityManager em) {
        ClassreportRoom classreportRoom = new ClassreportRoom()
            .account(DEFAULT_ACCOUNT)
            .roomn(DEFAULT_ROOMN)
            .yfj(DEFAULT_YFJ)
            .yfj9008(DEFAULT_YFJ_9008)
            .yfj9009(DEFAULT_YFJ_9009)
            .yfj9007(DEFAULT_YFJ_9007)
            .gz(DEFAULT_GZ)
            .ff(DEFAULT_FF)
            .minibar(DEFAULT_MINIBAR)
            .phone(DEFAULT_PHONE)
            .other(DEFAULT_OTHER)
            .pc(DEFAULT_PC)
            .cz(DEFAULT_CZ)
            .cy(DEFAULT_CY)
            .md(DEFAULT_MD)
            .huiy(DEFAULT_HUIY)
            .dtb(DEFAULT_DTB)
            .sszx(DEFAULT_SSZX);
        return classreportRoom;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassreportRoom createUpdatedEntity(EntityManager em) {
        ClassreportRoom classreportRoom = new ClassreportRoom()
            .account(UPDATED_ACCOUNT)
            .roomn(UPDATED_ROOMN)
            .yfj(UPDATED_YFJ)
            .yfj9008(UPDATED_YFJ_9008)
            .yfj9009(UPDATED_YFJ_9009)
            .yfj9007(UPDATED_YFJ_9007)
            .gz(UPDATED_GZ)
            .ff(UPDATED_FF)
            .minibar(UPDATED_MINIBAR)
            .phone(UPDATED_PHONE)
            .other(UPDATED_OTHER)
            .pc(UPDATED_PC)
            .cz(UPDATED_CZ)
            .cy(UPDATED_CY)
            .md(UPDATED_MD)
            .huiy(UPDATED_HUIY)
            .dtb(UPDATED_DTB)
            .sszx(UPDATED_SSZX);
        return classreportRoom;
    }

    @BeforeEach
    public void initTest() {
        classreportRoom = createEntity(em);
    }

    @Test
    @Transactional
    void createClassreportRoom() throws Exception {
        int databaseSizeBeforeCreate = classreportRoomRepository.findAll().size();
        // Create the ClassreportRoom
        ClassreportRoomDTO classreportRoomDTO = classreportRoomMapper.toDto(classreportRoom);
        restClassreportRoomMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classreportRoomDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ClassreportRoom in the database
        List<ClassreportRoom> classreportRoomList = classreportRoomRepository.findAll();
        assertThat(classreportRoomList).hasSize(databaseSizeBeforeCreate + 1);
        ClassreportRoom testClassreportRoom = classreportRoomList.get(classreportRoomList.size() - 1);
        assertThat(testClassreportRoom.getAccount()).isEqualTo(DEFAULT_ACCOUNT);
        assertThat(testClassreportRoom.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testClassreportRoom.getYfj()).isEqualByComparingTo(DEFAULT_YFJ);
        assertThat(testClassreportRoom.getYfj9008()).isEqualByComparingTo(DEFAULT_YFJ_9008);
        assertThat(testClassreportRoom.getYfj9009()).isEqualByComparingTo(DEFAULT_YFJ_9009);
        assertThat(testClassreportRoom.getYfj9007()).isEqualByComparingTo(DEFAULT_YFJ_9007);
        assertThat(testClassreportRoom.getGz()).isEqualByComparingTo(DEFAULT_GZ);
        assertThat(testClassreportRoom.getFf()).isEqualByComparingTo(DEFAULT_FF);
        assertThat(testClassreportRoom.getMinibar()).isEqualByComparingTo(DEFAULT_MINIBAR);
        assertThat(testClassreportRoom.getPhone()).isEqualByComparingTo(DEFAULT_PHONE);
        assertThat(testClassreportRoom.getOther()).isEqualByComparingTo(DEFAULT_OTHER);
        assertThat(testClassreportRoom.getPc()).isEqualByComparingTo(DEFAULT_PC);
        assertThat(testClassreportRoom.getCz()).isEqualByComparingTo(DEFAULT_CZ);
        assertThat(testClassreportRoom.getCy()).isEqualByComparingTo(DEFAULT_CY);
        assertThat(testClassreportRoom.getMd()).isEqualByComparingTo(DEFAULT_MD);
        assertThat(testClassreportRoom.getHuiy()).isEqualByComparingTo(DEFAULT_HUIY);
        assertThat(testClassreportRoom.getDtb()).isEqualByComparingTo(DEFAULT_DTB);
        assertThat(testClassreportRoom.getSszx()).isEqualByComparingTo(DEFAULT_SSZX);

        // Validate the ClassreportRoom in Elasticsearch
        verify(mockClassreportRoomSearchRepository, times(1)).save(testClassreportRoom);
    }

    @Test
    @Transactional
    void createClassreportRoomWithExistingId() throws Exception {
        // Create the ClassreportRoom with an existing ID
        classreportRoom.setId(1L);
        ClassreportRoomDTO classreportRoomDTO = classreportRoomMapper.toDto(classreportRoom);

        int databaseSizeBeforeCreate = classreportRoomRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassreportRoomMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classreportRoomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassreportRoom in the database
        List<ClassreportRoom> classreportRoomList = classreportRoomRepository.findAll();
        assertThat(classreportRoomList).hasSize(databaseSizeBeforeCreate);

        // Validate the ClassreportRoom in Elasticsearch
        verify(mockClassreportRoomSearchRepository, times(0)).save(classreportRoom);
    }

    @Test
    @Transactional
    void checkAccountIsRequired() throws Exception {
        int databaseSizeBeforeTest = classreportRoomRepository.findAll().size();
        // set the field null
        classreportRoom.setAccount(null);

        // Create the ClassreportRoom, which fails.
        ClassreportRoomDTO classreportRoomDTO = classreportRoomMapper.toDto(classreportRoom);

        restClassreportRoomMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classreportRoomDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClassreportRoom> classreportRoomList = classreportRoomRepository.findAll();
        assertThat(classreportRoomList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClassreportRooms() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList
        restClassreportRoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classreportRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].yfj").value(hasItem(sameNumber(DEFAULT_YFJ))))
            .andExpect(jsonPath("$.[*].yfj9008").value(hasItem(sameNumber(DEFAULT_YFJ_9008))))
            .andExpect(jsonPath("$.[*].yfj9009").value(hasItem(sameNumber(DEFAULT_YFJ_9009))))
            .andExpect(jsonPath("$.[*].yfj9007").value(hasItem(sameNumber(DEFAULT_YFJ_9007))))
            .andExpect(jsonPath("$.[*].gz").value(hasItem(sameNumber(DEFAULT_GZ))))
            .andExpect(jsonPath("$.[*].ff").value(hasItem(sameNumber(DEFAULT_FF))))
            .andExpect(jsonPath("$.[*].minibar").value(hasItem(sameNumber(DEFAULT_MINIBAR))))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(sameNumber(DEFAULT_PHONE))))
            .andExpect(jsonPath("$.[*].other").value(hasItem(sameNumber(DEFAULT_OTHER))))
            .andExpect(jsonPath("$.[*].pc").value(hasItem(sameNumber(DEFAULT_PC))))
            .andExpect(jsonPath("$.[*].cz").value(hasItem(sameNumber(DEFAULT_CZ))))
            .andExpect(jsonPath("$.[*].cy").value(hasItem(sameNumber(DEFAULT_CY))))
            .andExpect(jsonPath("$.[*].md").value(hasItem(sameNumber(DEFAULT_MD))))
            .andExpect(jsonPath("$.[*].huiy").value(hasItem(sameNumber(DEFAULT_HUIY))))
            .andExpect(jsonPath("$.[*].dtb").value(hasItem(sameNumber(DEFAULT_DTB))))
            .andExpect(jsonPath("$.[*].sszx").value(hasItem(sameNumber(DEFAULT_SSZX))));
    }

    @Test
    @Transactional
    void getClassreportRoom() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get the classreportRoom
        restClassreportRoomMockMvc
            .perform(get(ENTITY_API_URL_ID, classreportRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classreportRoom.getId().intValue()))
            .andExpect(jsonPath("$.account").value(DEFAULT_ACCOUNT))
            .andExpect(jsonPath("$.roomn").value(DEFAULT_ROOMN))
            .andExpect(jsonPath("$.yfj").value(sameNumber(DEFAULT_YFJ)))
            .andExpect(jsonPath("$.yfj9008").value(sameNumber(DEFAULT_YFJ_9008)))
            .andExpect(jsonPath("$.yfj9009").value(sameNumber(DEFAULT_YFJ_9009)))
            .andExpect(jsonPath("$.yfj9007").value(sameNumber(DEFAULT_YFJ_9007)))
            .andExpect(jsonPath("$.gz").value(sameNumber(DEFAULT_GZ)))
            .andExpect(jsonPath("$.ff").value(sameNumber(DEFAULT_FF)))
            .andExpect(jsonPath("$.minibar").value(sameNumber(DEFAULT_MINIBAR)))
            .andExpect(jsonPath("$.phone").value(sameNumber(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.other").value(sameNumber(DEFAULT_OTHER)))
            .andExpect(jsonPath("$.pc").value(sameNumber(DEFAULT_PC)))
            .andExpect(jsonPath("$.cz").value(sameNumber(DEFAULT_CZ)))
            .andExpect(jsonPath("$.cy").value(sameNumber(DEFAULT_CY)))
            .andExpect(jsonPath("$.md").value(sameNumber(DEFAULT_MD)))
            .andExpect(jsonPath("$.huiy").value(sameNumber(DEFAULT_HUIY)))
            .andExpect(jsonPath("$.dtb").value(sameNumber(DEFAULT_DTB)))
            .andExpect(jsonPath("$.sszx").value(sameNumber(DEFAULT_SSZX)));
    }

    @Test
    @Transactional
    void getClassreportRoomsByIdFiltering() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        Long id = classreportRoom.getId();

        defaultClassreportRoomShouldBeFound("id.equals=" + id);
        defaultClassreportRoomShouldNotBeFound("id.notEquals=" + id);

        defaultClassreportRoomShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClassreportRoomShouldNotBeFound("id.greaterThan=" + id);

        defaultClassreportRoomShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClassreportRoomShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where account equals to DEFAULT_ACCOUNT
        defaultClassreportRoomShouldBeFound("account.equals=" + DEFAULT_ACCOUNT);

        // Get all the classreportRoomList where account equals to UPDATED_ACCOUNT
        defaultClassreportRoomShouldNotBeFound("account.equals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByAccountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where account not equals to DEFAULT_ACCOUNT
        defaultClassreportRoomShouldNotBeFound("account.notEquals=" + DEFAULT_ACCOUNT);

        // Get all the classreportRoomList where account not equals to UPDATED_ACCOUNT
        defaultClassreportRoomShouldBeFound("account.notEquals=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByAccountIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where account in DEFAULT_ACCOUNT or UPDATED_ACCOUNT
        defaultClassreportRoomShouldBeFound("account.in=" + DEFAULT_ACCOUNT + "," + UPDATED_ACCOUNT);

        // Get all the classreportRoomList where account equals to UPDATED_ACCOUNT
        defaultClassreportRoomShouldNotBeFound("account.in=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByAccountIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where account is not null
        defaultClassreportRoomShouldBeFound("account.specified=true");

        // Get all the classreportRoomList where account is null
        defaultClassreportRoomShouldNotBeFound("account.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByAccountContainsSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where account contains DEFAULT_ACCOUNT
        defaultClassreportRoomShouldBeFound("account.contains=" + DEFAULT_ACCOUNT);

        // Get all the classreportRoomList where account contains UPDATED_ACCOUNT
        defaultClassreportRoomShouldNotBeFound("account.contains=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByAccountNotContainsSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where account does not contain DEFAULT_ACCOUNT
        defaultClassreportRoomShouldNotBeFound("account.doesNotContain=" + DEFAULT_ACCOUNT);

        // Get all the classreportRoomList where account does not contain UPDATED_ACCOUNT
        defaultClassreportRoomShouldBeFound("account.doesNotContain=" + UPDATED_ACCOUNT);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByRoomnIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where roomn equals to DEFAULT_ROOMN
        defaultClassreportRoomShouldBeFound("roomn.equals=" + DEFAULT_ROOMN);

        // Get all the classreportRoomList where roomn equals to UPDATED_ROOMN
        defaultClassreportRoomShouldNotBeFound("roomn.equals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByRoomnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where roomn not equals to DEFAULT_ROOMN
        defaultClassreportRoomShouldNotBeFound("roomn.notEquals=" + DEFAULT_ROOMN);

        // Get all the classreportRoomList where roomn not equals to UPDATED_ROOMN
        defaultClassreportRoomShouldBeFound("roomn.notEquals=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByRoomnIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where roomn in DEFAULT_ROOMN or UPDATED_ROOMN
        defaultClassreportRoomShouldBeFound("roomn.in=" + DEFAULT_ROOMN + "," + UPDATED_ROOMN);

        // Get all the classreportRoomList where roomn equals to UPDATED_ROOMN
        defaultClassreportRoomShouldNotBeFound("roomn.in=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByRoomnIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where roomn is not null
        defaultClassreportRoomShouldBeFound("roomn.specified=true");

        // Get all the classreportRoomList where roomn is null
        defaultClassreportRoomShouldNotBeFound("roomn.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByRoomnContainsSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where roomn contains DEFAULT_ROOMN
        defaultClassreportRoomShouldBeFound("roomn.contains=" + DEFAULT_ROOMN);

        // Get all the classreportRoomList where roomn contains UPDATED_ROOMN
        defaultClassreportRoomShouldNotBeFound("roomn.contains=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByRoomnNotContainsSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where roomn does not contain DEFAULT_ROOMN
        defaultClassreportRoomShouldNotBeFound("roomn.doesNotContain=" + DEFAULT_ROOMN);

        // Get all the classreportRoomList where roomn does not contain UPDATED_ROOMN
        defaultClassreportRoomShouldBeFound("roomn.doesNotContain=" + UPDATED_ROOMN);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfjIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj equals to DEFAULT_YFJ
        defaultClassreportRoomShouldBeFound("yfj.equals=" + DEFAULT_YFJ);

        // Get all the classreportRoomList where yfj equals to UPDATED_YFJ
        defaultClassreportRoomShouldNotBeFound("yfj.equals=" + UPDATED_YFJ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj not equals to DEFAULT_YFJ
        defaultClassreportRoomShouldNotBeFound("yfj.notEquals=" + DEFAULT_YFJ);

        // Get all the classreportRoomList where yfj not equals to UPDATED_YFJ
        defaultClassreportRoomShouldBeFound("yfj.notEquals=" + UPDATED_YFJ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfjIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj in DEFAULT_YFJ or UPDATED_YFJ
        defaultClassreportRoomShouldBeFound("yfj.in=" + DEFAULT_YFJ + "," + UPDATED_YFJ);

        // Get all the classreportRoomList where yfj equals to UPDATED_YFJ
        defaultClassreportRoomShouldNotBeFound("yfj.in=" + UPDATED_YFJ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfjIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj is not null
        defaultClassreportRoomShouldBeFound("yfj.specified=true");

        // Get all the classreportRoomList where yfj is null
        defaultClassreportRoomShouldNotBeFound("yfj.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj is greater than or equal to DEFAULT_YFJ
        defaultClassreportRoomShouldBeFound("yfj.greaterThanOrEqual=" + DEFAULT_YFJ);

        // Get all the classreportRoomList where yfj is greater than or equal to UPDATED_YFJ
        defaultClassreportRoomShouldNotBeFound("yfj.greaterThanOrEqual=" + UPDATED_YFJ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj is less than or equal to DEFAULT_YFJ
        defaultClassreportRoomShouldBeFound("yfj.lessThanOrEqual=" + DEFAULT_YFJ);

        // Get all the classreportRoomList where yfj is less than or equal to SMALLER_YFJ
        defaultClassreportRoomShouldNotBeFound("yfj.lessThanOrEqual=" + SMALLER_YFJ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfjIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj is less than DEFAULT_YFJ
        defaultClassreportRoomShouldNotBeFound("yfj.lessThan=" + DEFAULT_YFJ);

        // Get all the classreportRoomList where yfj is less than UPDATED_YFJ
        defaultClassreportRoomShouldBeFound("yfj.lessThan=" + UPDATED_YFJ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj is greater than DEFAULT_YFJ
        defaultClassreportRoomShouldNotBeFound("yfj.greaterThan=" + DEFAULT_YFJ);

        // Get all the classreportRoomList where yfj is greater than SMALLER_YFJ
        defaultClassreportRoomShouldBeFound("yfj.greaterThan=" + SMALLER_YFJ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9008IsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9008 equals to DEFAULT_YFJ_9008
        defaultClassreportRoomShouldBeFound("yfj9008.equals=" + DEFAULT_YFJ_9008);

        // Get all the classreportRoomList where yfj9008 equals to UPDATED_YFJ_9008
        defaultClassreportRoomShouldNotBeFound("yfj9008.equals=" + UPDATED_YFJ_9008);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9008IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9008 not equals to DEFAULT_YFJ_9008
        defaultClassreportRoomShouldNotBeFound("yfj9008.notEquals=" + DEFAULT_YFJ_9008);

        // Get all the classreportRoomList where yfj9008 not equals to UPDATED_YFJ_9008
        defaultClassreportRoomShouldBeFound("yfj9008.notEquals=" + UPDATED_YFJ_9008);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9008IsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9008 in DEFAULT_YFJ_9008 or UPDATED_YFJ_9008
        defaultClassreportRoomShouldBeFound("yfj9008.in=" + DEFAULT_YFJ_9008 + "," + UPDATED_YFJ_9008);

        // Get all the classreportRoomList where yfj9008 equals to UPDATED_YFJ_9008
        defaultClassreportRoomShouldNotBeFound("yfj9008.in=" + UPDATED_YFJ_9008);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9008IsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9008 is not null
        defaultClassreportRoomShouldBeFound("yfj9008.specified=true");

        // Get all the classreportRoomList where yfj9008 is null
        defaultClassreportRoomShouldNotBeFound("yfj9008.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9008IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9008 is greater than or equal to DEFAULT_YFJ_9008
        defaultClassreportRoomShouldBeFound("yfj9008.greaterThanOrEqual=" + DEFAULT_YFJ_9008);

        // Get all the classreportRoomList where yfj9008 is greater than or equal to UPDATED_YFJ_9008
        defaultClassreportRoomShouldNotBeFound("yfj9008.greaterThanOrEqual=" + UPDATED_YFJ_9008);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9008IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9008 is less than or equal to DEFAULT_YFJ_9008
        defaultClassreportRoomShouldBeFound("yfj9008.lessThanOrEqual=" + DEFAULT_YFJ_9008);

        // Get all the classreportRoomList where yfj9008 is less than or equal to SMALLER_YFJ_9008
        defaultClassreportRoomShouldNotBeFound("yfj9008.lessThanOrEqual=" + SMALLER_YFJ_9008);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9008IsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9008 is less than DEFAULT_YFJ_9008
        defaultClassreportRoomShouldNotBeFound("yfj9008.lessThan=" + DEFAULT_YFJ_9008);

        // Get all the classreportRoomList where yfj9008 is less than UPDATED_YFJ_9008
        defaultClassreportRoomShouldBeFound("yfj9008.lessThan=" + UPDATED_YFJ_9008);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9008IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9008 is greater than DEFAULT_YFJ_9008
        defaultClassreportRoomShouldNotBeFound("yfj9008.greaterThan=" + DEFAULT_YFJ_9008);

        // Get all the classreportRoomList where yfj9008 is greater than SMALLER_YFJ_9008
        defaultClassreportRoomShouldBeFound("yfj9008.greaterThan=" + SMALLER_YFJ_9008);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9009IsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9009 equals to DEFAULT_YFJ_9009
        defaultClassreportRoomShouldBeFound("yfj9009.equals=" + DEFAULT_YFJ_9009);

        // Get all the classreportRoomList where yfj9009 equals to UPDATED_YFJ_9009
        defaultClassreportRoomShouldNotBeFound("yfj9009.equals=" + UPDATED_YFJ_9009);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9009IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9009 not equals to DEFAULT_YFJ_9009
        defaultClassreportRoomShouldNotBeFound("yfj9009.notEquals=" + DEFAULT_YFJ_9009);

        // Get all the classreportRoomList where yfj9009 not equals to UPDATED_YFJ_9009
        defaultClassreportRoomShouldBeFound("yfj9009.notEquals=" + UPDATED_YFJ_9009);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9009IsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9009 in DEFAULT_YFJ_9009 or UPDATED_YFJ_9009
        defaultClassreportRoomShouldBeFound("yfj9009.in=" + DEFAULT_YFJ_9009 + "," + UPDATED_YFJ_9009);

        // Get all the classreportRoomList where yfj9009 equals to UPDATED_YFJ_9009
        defaultClassreportRoomShouldNotBeFound("yfj9009.in=" + UPDATED_YFJ_9009);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9009IsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9009 is not null
        defaultClassreportRoomShouldBeFound("yfj9009.specified=true");

        // Get all the classreportRoomList where yfj9009 is null
        defaultClassreportRoomShouldNotBeFound("yfj9009.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9009IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9009 is greater than or equal to DEFAULT_YFJ_9009
        defaultClassreportRoomShouldBeFound("yfj9009.greaterThanOrEqual=" + DEFAULT_YFJ_9009);

        // Get all the classreportRoomList where yfj9009 is greater than or equal to UPDATED_YFJ_9009
        defaultClassreportRoomShouldNotBeFound("yfj9009.greaterThanOrEqual=" + UPDATED_YFJ_9009);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9009IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9009 is less than or equal to DEFAULT_YFJ_9009
        defaultClassreportRoomShouldBeFound("yfj9009.lessThanOrEqual=" + DEFAULT_YFJ_9009);

        // Get all the classreportRoomList where yfj9009 is less than or equal to SMALLER_YFJ_9009
        defaultClassreportRoomShouldNotBeFound("yfj9009.lessThanOrEqual=" + SMALLER_YFJ_9009);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9009IsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9009 is less than DEFAULT_YFJ_9009
        defaultClassreportRoomShouldNotBeFound("yfj9009.lessThan=" + DEFAULT_YFJ_9009);

        // Get all the classreportRoomList where yfj9009 is less than UPDATED_YFJ_9009
        defaultClassreportRoomShouldBeFound("yfj9009.lessThan=" + UPDATED_YFJ_9009);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9009IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9009 is greater than DEFAULT_YFJ_9009
        defaultClassreportRoomShouldNotBeFound("yfj9009.greaterThan=" + DEFAULT_YFJ_9009);

        // Get all the classreportRoomList where yfj9009 is greater than SMALLER_YFJ_9009
        defaultClassreportRoomShouldBeFound("yfj9009.greaterThan=" + SMALLER_YFJ_9009);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9007IsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9007 equals to DEFAULT_YFJ_9007
        defaultClassreportRoomShouldBeFound("yfj9007.equals=" + DEFAULT_YFJ_9007);

        // Get all the classreportRoomList where yfj9007 equals to UPDATED_YFJ_9007
        defaultClassreportRoomShouldNotBeFound("yfj9007.equals=" + UPDATED_YFJ_9007);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9007IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9007 not equals to DEFAULT_YFJ_9007
        defaultClassreportRoomShouldNotBeFound("yfj9007.notEquals=" + DEFAULT_YFJ_9007);

        // Get all the classreportRoomList where yfj9007 not equals to UPDATED_YFJ_9007
        defaultClassreportRoomShouldBeFound("yfj9007.notEquals=" + UPDATED_YFJ_9007);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9007IsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9007 in DEFAULT_YFJ_9007 or UPDATED_YFJ_9007
        defaultClassreportRoomShouldBeFound("yfj9007.in=" + DEFAULT_YFJ_9007 + "," + UPDATED_YFJ_9007);

        // Get all the classreportRoomList where yfj9007 equals to UPDATED_YFJ_9007
        defaultClassreportRoomShouldNotBeFound("yfj9007.in=" + UPDATED_YFJ_9007);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9007IsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9007 is not null
        defaultClassreportRoomShouldBeFound("yfj9007.specified=true");

        // Get all the classreportRoomList where yfj9007 is null
        defaultClassreportRoomShouldNotBeFound("yfj9007.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9007IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9007 is greater than or equal to DEFAULT_YFJ_9007
        defaultClassreportRoomShouldBeFound("yfj9007.greaterThanOrEqual=" + DEFAULT_YFJ_9007);

        // Get all the classreportRoomList where yfj9007 is greater than or equal to UPDATED_YFJ_9007
        defaultClassreportRoomShouldNotBeFound("yfj9007.greaterThanOrEqual=" + UPDATED_YFJ_9007);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9007IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9007 is less than or equal to DEFAULT_YFJ_9007
        defaultClassreportRoomShouldBeFound("yfj9007.lessThanOrEqual=" + DEFAULT_YFJ_9007);

        // Get all the classreportRoomList where yfj9007 is less than or equal to SMALLER_YFJ_9007
        defaultClassreportRoomShouldNotBeFound("yfj9007.lessThanOrEqual=" + SMALLER_YFJ_9007);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9007IsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9007 is less than DEFAULT_YFJ_9007
        defaultClassreportRoomShouldNotBeFound("yfj9007.lessThan=" + DEFAULT_YFJ_9007);

        // Get all the classreportRoomList where yfj9007 is less than UPDATED_YFJ_9007
        defaultClassreportRoomShouldBeFound("yfj9007.lessThan=" + UPDATED_YFJ_9007);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByYfj9007IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where yfj9007 is greater than DEFAULT_YFJ_9007
        defaultClassreportRoomShouldNotBeFound("yfj9007.greaterThan=" + DEFAULT_YFJ_9007);

        // Get all the classreportRoomList where yfj9007 is greater than SMALLER_YFJ_9007
        defaultClassreportRoomShouldBeFound("yfj9007.greaterThan=" + SMALLER_YFJ_9007);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByGzIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where gz equals to DEFAULT_GZ
        defaultClassreportRoomShouldBeFound("gz.equals=" + DEFAULT_GZ);

        // Get all the classreportRoomList where gz equals to UPDATED_GZ
        defaultClassreportRoomShouldNotBeFound("gz.equals=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByGzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where gz not equals to DEFAULT_GZ
        defaultClassreportRoomShouldNotBeFound("gz.notEquals=" + DEFAULT_GZ);

        // Get all the classreportRoomList where gz not equals to UPDATED_GZ
        defaultClassreportRoomShouldBeFound("gz.notEquals=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByGzIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where gz in DEFAULT_GZ or UPDATED_GZ
        defaultClassreportRoomShouldBeFound("gz.in=" + DEFAULT_GZ + "," + UPDATED_GZ);

        // Get all the classreportRoomList where gz equals to UPDATED_GZ
        defaultClassreportRoomShouldNotBeFound("gz.in=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByGzIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where gz is not null
        defaultClassreportRoomShouldBeFound("gz.specified=true");

        // Get all the classreportRoomList where gz is null
        defaultClassreportRoomShouldNotBeFound("gz.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByGzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where gz is greater than or equal to DEFAULT_GZ
        defaultClassreportRoomShouldBeFound("gz.greaterThanOrEqual=" + DEFAULT_GZ);

        // Get all the classreportRoomList where gz is greater than or equal to UPDATED_GZ
        defaultClassreportRoomShouldNotBeFound("gz.greaterThanOrEqual=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByGzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where gz is less than or equal to DEFAULT_GZ
        defaultClassreportRoomShouldBeFound("gz.lessThanOrEqual=" + DEFAULT_GZ);

        // Get all the classreportRoomList where gz is less than or equal to SMALLER_GZ
        defaultClassreportRoomShouldNotBeFound("gz.lessThanOrEqual=" + SMALLER_GZ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByGzIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where gz is less than DEFAULT_GZ
        defaultClassreportRoomShouldNotBeFound("gz.lessThan=" + DEFAULT_GZ);

        // Get all the classreportRoomList where gz is less than UPDATED_GZ
        defaultClassreportRoomShouldBeFound("gz.lessThan=" + UPDATED_GZ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByGzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where gz is greater than DEFAULT_GZ
        defaultClassreportRoomShouldNotBeFound("gz.greaterThan=" + DEFAULT_GZ);

        // Get all the classreportRoomList where gz is greater than SMALLER_GZ
        defaultClassreportRoomShouldBeFound("gz.greaterThan=" + SMALLER_GZ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByFfIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where ff equals to DEFAULT_FF
        defaultClassreportRoomShouldBeFound("ff.equals=" + DEFAULT_FF);

        // Get all the classreportRoomList where ff equals to UPDATED_FF
        defaultClassreportRoomShouldNotBeFound("ff.equals=" + UPDATED_FF);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByFfIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where ff not equals to DEFAULT_FF
        defaultClassreportRoomShouldNotBeFound("ff.notEquals=" + DEFAULT_FF);

        // Get all the classreportRoomList where ff not equals to UPDATED_FF
        defaultClassreportRoomShouldBeFound("ff.notEquals=" + UPDATED_FF);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByFfIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where ff in DEFAULT_FF or UPDATED_FF
        defaultClassreportRoomShouldBeFound("ff.in=" + DEFAULT_FF + "," + UPDATED_FF);

        // Get all the classreportRoomList where ff equals to UPDATED_FF
        defaultClassreportRoomShouldNotBeFound("ff.in=" + UPDATED_FF);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByFfIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where ff is not null
        defaultClassreportRoomShouldBeFound("ff.specified=true");

        // Get all the classreportRoomList where ff is null
        defaultClassreportRoomShouldNotBeFound("ff.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByFfIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where ff is greater than or equal to DEFAULT_FF
        defaultClassreportRoomShouldBeFound("ff.greaterThanOrEqual=" + DEFAULT_FF);

        // Get all the classreportRoomList where ff is greater than or equal to UPDATED_FF
        defaultClassreportRoomShouldNotBeFound("ff.greaterThanOrEqual=" + UPDATED_FF);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByFfIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where ff is less than or equal to DEFAULT_FF
        defaultClassreportRoomShouldBeFound("ff.lessThanOrEqual=" + DEFAULT_FF);

        // Get all the classreportRoomList where ff is less than or equal to SMALLER_FF
        defaultClassreportRoomShouldNotBeFound("ff.lessThanOrEqual=" + SMALLER_FF);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByFfIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where ff is less than DEFAULT_FF
        defaultClassreportRoomShouldNotBeFound("ff.lessThan=" + DEFAULT_FF);

        // Get all the classreportRoomList where ff is less than UPDATED_FF
        defaultClassreportRoomShouldBeFound("ff.lessThan=" + UPDATED_FF);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByFfIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where ff is greater than DEFAULT_FF
        defaultClassreportRoomShouldNotBeFound("ff.greaterThan=" + DEFAULT_FF);

        // Get all the classreportRoomList where ff is greater than SMALLER_FF
        defaultClassreportRoomShouldBeFound("ff.greaterThan=" + SMALLER_FF);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMinibarIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where minibar equals to DEFAULT_MINIBAR
        defaultClassreportRoomShouldBeFound("minibar.equals=" + DEFAULT_MINIBAR);

        // Get all the classreportRoomList where minibar equals to UPDATED_MINIBAR
        defaultClassreportRoomShouldNotBeFound("minibar.equals=" + UPDATED_MINIBAR);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMinibarIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where minibar not equals to DEFAULT_MINIBAR
        defaultClassreportRoomShouldNotBeFound("minibar.notEquals=" + DEFAULT_MINIBAR);

        // Get all the classreportRoomList where minibar not equals to UPDATED_MINIBAR
        defaultClassreportRoomShouldBeFound("minibar.notEquals=" + UPDATED_MINIBAR);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMinibarIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where minibar in DEFAULT_MINIBAR or UPDATED_MINIBAR
        defaultClassreportRoomShouldBeFound("minibar.in=" + DEFAULT_MINIBAR + "," + UPDATED_MINIBAR);

        // Get all the classreportRoomList where minibar equals to UPDATED_MINIBAR
        defaultClassreportRoomShouldNotBeFound("minibar.in=" + UPDATED_MINIBAR);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMinibarIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where minibar is not null
        defaultClassreportRoomShouldBeFound("minibar.specified=true");

        // Get all the classreportRoomList where minibar is null
        defaultClassreportRoomShouldNotBeFound("minibar.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMinibarIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where minibar is greater than or equal to DEFAULT_MINIBAR
        defaultClassreportRoomShouldBeFound("minibar.greaterThanOrEqual=" + DEFAULT_MINIBAR);

        // Get all the classreportRoomList where minibar is greater than or equal to UPDATED_MINIBAR
        defaultClassreportRoomShouldNotBeFound("minibar.greaterThanOrEqual=" + UPDATED_MINIBAR);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMinibarIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where minibar is less than or equal to DEFAULT_MINIBAR
        defaultClassreportRoomShouldBeFound("minibar.lessThanOrEqual=" + DEFAULT_MINIBAR);

        // Get all the classreportRoomList where minibar is less than or equal to SMALLER_MINIBAR
        defaultClassreportRoomShouldNotBeFound("minibar.lessThanOrEqual=" + SMALLER_MINIBAR);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMinibarIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where minibar is less than DEFAULT_MINIBAR
        defaultClassreportRoomShouldNotBeFound("minibar.lessThan=" + DEFAULT_MINIBAR);

        // Get all the classreportRoomList where minibar is less than UPDATED_MINIBAR
        defaultClassreportRoomShouldBeFound("minibar.lessThan=" + UPDATED_MINIBAR);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMinibarIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where minibar is greater than DEFAULT_MINIBAR
        defaultClassreportRoomShouldNotBeFound("minibar.greaterThan=" + DEFAULT_MINIBAR);

        // Get all the classreportRoomList where minibar is greater than SMALLER_MINIBAR
        defaultClassreportRoomShouldBeFound("minibar.greaterThan=" + SMALLER_MINIBAR);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where phone equals to DEFAULT_PHONE
        defaultClassreportRoomShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the classreportRoomList where phone equals to UPDATED_PHONE
        defaultClassreportRoomShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where phone not equals to DEFAULT_PHONE
        defaultClassreportRoomShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the classreportRoomList where phone not equals to UPDATED_PHONE
        defaultClassreportRoomShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultClassreportRoomShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the classreportRoomList where phone equals to UPDATED_PHONE
        defaultClassreportRoomShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where phone is not null
        defaultClassreportRoomShouldBeFound("phone.specified=true");

        // Get all the classreportRoomList where phone is null
        defaultClassreportRoomShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPhoneIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where phone is greater than or equal to DEFAULT_PHONE
        defaultClassreportRoomShouldBeFound("phone.greaterThanOrEqual=" + DEFAULT_PHONE);

        // Get all the classreportRoomList where phone is greater than or equal to UPDATED_PHONE
        defaultClassreportRoomShouldNotBeFound("phone.greaterThanOrEqual=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPhoneIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where phone is less than or equal to DEFAULT_PHONE
        defaultClassreportRoomShouldBeFound("phone.lessThanOrEqual=" + DEFAULT_PHONE);

        // Get all the classreportRoomList where phone is less than or equal to SMALLER_PHONE
        defaultClassreportRoomShouldNotBeFound("phone.lessThanOrEqual=" + SMALLER_PHONE);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPhoneIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where phone is less than DEFAULT_PHONE
        defaultClassreportRoomShouldNotBeFound("phone.lessThan=" + DEFAULT_PHONE);

        // Get all the classreportRoomList where phone is less than UPDATED_PHONE
        defaultClassreportRoomShouldBeFound("phone.lessThan=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPhoneIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where phone is greater than DEFAULT_PHONE
        defaultClassreportRoomShouldNotBeFound("phone.greaterThan=" + DEFAULT_PHONE);

        // Get all the classreportRoomList where phone is greater than SMALLER_PHONE
        defaultClassreportRoomShouldBeFound("phone.greaterThan=" + SMALLER_PHONE);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByOtherIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where other equals to DEFAULT_OTHER
        defaultClassreportRoomShouldBeFound("other.equals=" + DEFAULT_OTHER);

        // Get all the classreportRoomList where other equals to UPDATED_OTHER
        defaultClassreportRoomShouldNotBeFound("other.equals=" + UPDATED_OTHER);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByOtherIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where other not equals to DEFAULT_OTHER
        defaultClassreportRoomShouldNotBeFound("other.notEquals=" + DEFAULT_OTHER);

        // Get all the classreportRoomList where other not equals to UPDATED_OTHER
        defaultClassreportRoomShouldBeFound("other.notEquals=" + UPDATED_OTHER);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByOtherIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where other in DEFAULT_OTHER or UPDATED_OTHER
        defaultClassreportRoomShouldBeFound("other.in=" + DEFAULT_OTHER + "," + UPDATED_OTHER);

        // Get all the classreportRoomList where other equals to UPDATED_OTHER
        defaultClassreportRoomShouldNotBeFound("other.in=" + UPDATED_OTHER);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByOtherIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where other is not null
        defaultClassreportRoomShouldBeFound("other.specified=true");

        // Get all the classreportRoomList where other is null
        defaultClassreportRoomShouldNotBeFound("other.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByOtherIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where other is greater than or equal to DEFAULT_OTHER
        defaultClassreportRoomShouldBeFound("other.greaterThanOrEqual=" + DEFAULT_OTHER);

        // Get all the classreportRoomList where other is greater than or equal to UPDATED_OTHER
        defaultClassreportRoomShouldNotBeFound("other.greaterThanOrEqual=" + UPDATED_OTHER);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByOtherIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where other is less than or equal to DEFAULT_OTHER
        defaultClassreportRoomShouldBeFound("other.lessThanOrEqual=" + DEFAULT_OTHER);

        // Get all the classreportRoomList where other is less than or equal to SMALLER_OTHER
        defaultClassreportRoomShouldNotBeFound("other.lessThanOrEqual=" + SMALLER_OTHER);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByOtherIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where other is less than DEFAULT_OTHER
        defaultClassreportRoomShouldNotBeFound("other.lessThan=" + DEFAULT_OTHER);

        // Get all the classreportRoomList where other is less than UPDATED_OTHER
        defaultClassreportRoomShouldBeFound("other.lessThan=" + UPDATED_OTHER);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByOtherIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where other is greater than DEFAULT_OTHER
        defaultClassreportRoomShouldNotBeFound("other.greaterThan=" + DEFAULT_OTHER);

        // Get all the classreportRoomList where other is greater than SMALLER_OTHER
        defaultClassreportRoomShouldBeFound("other.greaterThan=" + SMALLER_OTHER);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPcIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where pc equals to DEFAULT_PC
        defaultClassreportRoomShouldBeFound("pc.equals=" + DEFAULT_PC);

        // Get all the classreportRoomList where pc equals to UPDATED_PC
        defaultClassreportRoomShouldNotBeFound("pc.equals=" + UPDATED_PC);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPcIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where pc not equals to DEFAULT_PC
        defaultClassreportRoomShouldNotBeFound("pc.notEquals=" + DEFAULT_PC);

        // Get all the classreportRoomList where pc not equals to UPDATED_PC
        defaultClassreportRoomShouldBeFound("pc.notEquals=" + UPDATED_PC);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPcIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where pc in DEFAULT_PC or UPDATED_PC
        defaultClassreportRoomShouldBeFound("pc.in=" + DEFAULT_PC + "," + UPDATED_PC);

        // Get all the classreportRoomList where pc equals to UPDATED_PC
        defaultClassreportRoomShouldNotBeFound("pc.in=" + UPDATED_PC);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPcIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where pc is not null
        defaultClassreportRoomShouldBeFound("pc.specified=true");

        // Get all the classreportRoomList where pc is null
        defaultClassreportRoomShouldNotBeFound("pc.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPcIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where pc is greater than or equal to DEFAULT_PC
        defaultClassreportRoomShouldBeFound("pc.greaterThanOrEqual=" + DEFAULT_PC);

        // Get all the classreportRoomList where pc is greater than or equal to UPDATED_PC
        defaultClassreportRoomShouldNotBeFound("pc.greaterThanOrEqual=" + UPDATED_PC);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPcIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where pc is less than or equal to DEFAULT_PC
        defaultClassreportRoomShouldBeFound("pc.lessThanOrEqual=" + DEFAULT_PC);

        // Get all the classreportRoomList where pc is less than or equal to SMALLER_PC
        defaultClassreportRoomShouldNotBeFound("pc.lessThanOrEqual=" + SMALLER_PC);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPcIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where pc is less than DEFAULT_PC
        defaultClassreportRoomShouldNotBeFound("pc.lessThan=" + DEFAULT_PC);

        // Get all the classreportRoomList where pc is less than UPDATED_PC
        defaultClassreportRoomShouldBeFound("pc.lessThan=" + UPDATED_PC);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByPcIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where pc is greater than DEFAULT_PC
        defaultClassreportRoomShouldNotBeFound("pc.greaterThan=" + DEFAULT_PC);

        // Get all the classreportRoomList where pc is greater than SMALLER_PC
        defaultClassreportRoomShouldBeFound("pc.greaterThan=" + SMALLER_PC);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCzIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cz equals to DEFAULT_CZ
        defaultClassreportRoomShouldBeFound("cz.equals=" + DEFAULT_CZ);

        // Get all the classreportRoomList where cz equals to UPDATED_CZ
        defaultClassreportRoomShouldNotBeFound("cz.equals=" + UPDATED_CZ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCzIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cz not equals to DEFAULT_CZ
        defaultClassreportRoomShouldNotBeFound("cz.notEquals=" + DEFAULT_CZ);

        // Get all the classreportRoomList where cz not equals to UPDATED_CZ
        defaultClassreportRoomShouldBeFound("cz.notEquals=" + UPDATED_CZ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCzIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cz in DEFAULT_CZ or UPDATED_CZ
        defaultClassreportRoomShouldBeFound("cz.in=" + DEFAULT_CZ + "," + UPDATED_CZ);

        // Get all the classreportRoomList where cz equals to UPDATED_CZ
        defaultClassreportRoomShouldNotBeFound("cz.in=" + UPDATED_CZ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCzIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cz is not null
        defaultClassreportRoomShouldBeFound("cz.specified=true");

        // Get all the classreportRoomList where cz is null
        defaultClassreportRoomShouldNotBeFound("cz.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCzIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cz is greater than or equal to DEFAULT_CZ
        defaultClassreportRoomShouldBeFound("cz.greaterThanOrEqual=" + DEFAULT_CZ);

        // Get all the classreportRoomList where cz is greater than or equal to UPDATED_CZ
        defaultClassreportRoomShouldNotBeFound("cz.greaterThanOrEqual=" + UPDATED_CZ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCzIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cz is less than or equal to DEFAULT_CZ
        defaultClassreportRoomShouldBeFound("cz.lessThanOrEqual=" + DEFAULT_CZ);

        // Get all the classreportRoomList where cz is less than or equal to SMALLER_CZ
        defaultClassreportRoomShouldNotBeFound("cz.lessThanOrEqual=" + SMALLER_CZ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCzIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cz is less than DEFAULT_CZ
        defaultClassreportRoomShouldNotBeFound("cz.lessThan=" + DEFAULT_CZ);

        // Get all the classreportRoomList where cz is less than UPDATED_CZ
        defaultClassreportRoomShouldBeFound("cz.lessThan=" + UPDATED_CZ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCzIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cz is greater than DEFAULT_CZ
        defaultClassreportRoomShouldNotBeFound("cz.greaterThan=" + DEFAULT_CZ);

        // Get all the classreportRoomList where cz is greater than SMALLER_CZ
        defaultClassreportRoomShouldBeFound("cz.greaterThan=" + SMALLER_CZ);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCyIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cy equals to DEFAULT_CY
        defaultClassreportRoomShouldBeFound("cy.equals=" + DEFAULT_CY);

        // Get all the classreportRoomList where cy equals to UPDATED_CY
        defaultClassreportRoomShouldNotBeFound("cy.equals=" + UPDATED_CY);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cy not equals to DEFAULT_CY
        defaultClassreportRoomShouldNotBeFound("cy.notEquals=" + DEFAULT_CY);

        // Get all the classreportRoomList where cy not equals to UPDATED_CY
        defaultClassreportRoomShouldBeFound("cy.notEquals=" + UPDATED_CY);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCyIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cy in DEFAULT_CY or UPDATED_CY
        defaultClassreportRoomShouldBeFound("cy.in=" + DEFAULT_CY + "," + UPDATED_CY);

        // Get all the classreportRoomList where cy equals to UPDATED_CY
        defaultClassreportRoomShouldNotBeFound("cy.in=" + UPDATED_CY);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCyIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cy is not null
        defaultClassreportRoomShouldBeFound("cy.specified=true");

        // Get all the classreportRoomList where cy is null
        defaultClassreportRoomShouldNotBeFound("cy.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cy is greater than or equal to DEFAULT_CY
        defaultClassreportRoomShouldBeFound("cy.greaterThanOrEqual=" + DEFAULT_CY);

        // Get all the classreportRoomList where cy is greater than or equal to UPDATED_CY
        defaultClassreportRoomShouldNotBeFound("cy.greaterThanOrEqual=" + UPDATED_CY);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cy is less than or equal to DEFAULT_CY
        defaultClassreportRoomShouldBeFound("cy.lessThanOrEqual=" + DEFAULT_CY);

        // Get all the classreportRoomList where cy is less than or equal to SMALLER_CY
        defaultClassreportRoomShouldNotBeFound("cy.lessThanOrEqual=" + SMALLER_CY);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCyIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cy is less than DEFAULT_CY
        defaultClassreportRoomShouldNotBeFound("cy.lessThan=" + DEFAULT_CY);

        // Get all the classreportRoomList where cy is less than UPDATED_CY
        defaultClassreportRoomShouldBeFound("cy.lessThan=" + UPDATED_CY);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByCyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where cy is greater than DEFAULT_CY
        defaultClassreportRoomShouldNotBeFound("cy.greaterThan=" + DEFAULT_CY);

        // Get all the classreportRoomList where cy is greater than SMALLER_CY
        defaultClassreportRoomShouldBeFound("cy.greaterThan=" + SMALLER_CY);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMdIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where md equals to DEFAULT_MD
        defaultClassreportRoomShouldBeFound("md.equals=" + DEFAULT_MD);

        // Get all the classreportRoomList where md equals to UPDATED_MD
        defaultClassreportRoomShouldNotBeFound("md.equals=" + UPDATED_MD);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where md not equals to DEFAULT_MD
        defaultClassreportRoomShouldNotBeFound("md.notEquals=" + DEFAULT_MD);

        // Get all the classreportRoomList where md not equals to UPDATED_MD
        defaultClassreportRoomShouldBeFound("md.notEquals=" + UPDATED_MD);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMdIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where md in DEFAULT_MD or UPDATED_MD
        defaultClassreportRoomShouldBeFound("md.in=" + DEFAULT_MD + "," + UPDATED_MD);

        // Get all the classreportRoomList where md equals to UPDATED_MD
        defaultClassreportRoomShouldNotBeFound("md.in=" + UPDATED_MD);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMdIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where md is not null
        defaultClassreportRoomShouldBeFound("md.specified=true");

        // Get all the classreportRoomList where md is null
        defaultClassreportRoomShouldNotBeFound("md.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where md is greater than or equal to DEFAULT_MD
        defaultClassreportRoomShouldBeFound("md.greaterThanOrEqual=" + DEFAULT_MD);

        // Get all the classreportRoomList where md is greater than or equal to UPDATED_MD
        defaultClassreportRoomShouldNotBeFound("md.greaterThanOrEqual=" + UPDATED_MD);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMdIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where md is less than or equal to DEFAULT_MD
        defaultClassreportRoomShouldBeFound("md.lessThanOrEqual=" + DEFAULT_MD);

        // Get all the classreportRoomList where md is less than or equal to SMALLER_MD
        defaultClassreportRoomShouldNotBeFound("md.lessThanOrEqual=" + SMALLER_MD);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMdIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where md is less than DEFAULT_MD
        defaultClassreportRoomShouldNotBeFound("md.lessThan=" + DEFAULT_MD);

        // Get all the classreportRoomList where md is less than UPDATED_MD
        defaultClassreportRoomShouldBeFound("md.lessThan=" + UPDATED_MD);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByMdIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where md is greater than DEFAULT_MD
        defaultClassreportRoomShouldNotBeFound("md.greaterThan=" + DEFAULT_MD);

        // Get all the classreportRoomList where md is greater than SMALLER_MD
        defaultClassreportRoomShouldBeFound("md.greaterThan=" + SMALLER_MD);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByHuiyIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where huiy equals to DEFAULT_HUIY
        defaultClassreportRoomShouldBeFound("huiy.equals=" + DEFAULT_HUIY);

        // Get all the classreportRoomList where huiy equals to UPDATED_HUIY
        defaultClassreportRoomShouldNotBeFound("huiy.equals=" + UPDATED_HUIY);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByHuiyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where huiy not equals to DEFAULT_HUIY
        defaultClassreportRoomShouldNotBeFound("huiy.notEquals=" + DEFAULT_HUIY);

        // Get all the classreportRoomList where huiy not equals to UPDATED_HUIY
        defaultClassreportRoomShouldBeFound("huiy.notEquals=" + UPDATED_HUIY);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByHuiyIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where huiy in DEFAULT_HUIY or UPDATED_HUIY
        defaultClassreportRoomShouldBeFound("huiy.in=" + DEFAULT_HUIY + "," + UPDATED_HUIY);

        // Get all the classreportRoomList where huiy equals to UPDATED_HUIY
        defaultClassreportRoomShouldNotBeFound("huiy.in=" + UPDATED_HUIY);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByHuiyIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where huiy is not null
        defaultClassreportRoomShouldBeFound("huiy.specified=true");

        // Get all the classreportRoomList where huiy is null
        defaultClassreportRoomShouldNotBeFound("huiy.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByHuiyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where huiy is greater than or equal to DEFAULT_HUIY
        defaultClassreportRoomShouldBeFound("huiy.greaterThanOrEqual=" + DEFAULT_HUIY);

        // Get all the classreportRoomList where huiy is greater than or equal to UPDATED_HUIY
        defaultClassreportRoomShouldNotBeFound("huiy.greaterThanOrEqual=" + UPDATED_HUIY);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByHuiyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where huiy is less than or equal to DEFAULT_HUIY
        defaultClassreportRoomShouldBeFound("huiy.lessThanOrEqual=" + DEFAULT_HUIY);

        // Get all the classreportRoomList where huiy is less than or equal to SMALLER_HUIY
        defaultClassreportRoomShouldNotBeFound("huiy.lessThanOrEqual=" + SMALLER_HUIY);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByHuiyIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where huiy is less than DEFAULT_HUIY
        defaultClassreportRoomShouldNotBeFound("huiy.lessThan=" + DEFAULT_HUIY);

        // Get all the classreportRoomList where huiy is less than UPDATED_HUIY
        defaultClassreportRoomShouldBeFound("huiy.lessThan=" + UPDATED_HUIY);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByHuiyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where huiy is greater than DEFAULT_HUIY
        defaultClassreportRoomShouldNotBeFound("huiy.greaterThan=" + DEFAULT_HUIY);

        // Get all the classreportRoomList where huiy is greater than SMALLER_HUIY
        defaultClassreportRoomShouldBeFound("huiy.greaterThan=" + SMALLER_HUIY);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByDtbIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where dtb equals to DEFAULT_DTB
        defaultClassreportRoomShouldBeFound("dtb.equals=" + DEFAULT_DTB);

        // Get all the classreportRoomList where dtb equals to UPDATED_DTB
        defaultClassreportRoomShouldNotBeFound("dtb.equals=" + UPDATED_DTB);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByDtbIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where dtb not equals to DEFAULT_DTB
        defaultClassreportRoomShouldNotBeFound("dtb.notEquals=" + DEFAULT_DTB);

        // Get all the classreportRoomList where dtb not equals to UPDATED_DTB
        defaultClassreportRoomShouldBeFound("dtb.notEquals=" + UPDATED_DTB);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByDtbIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where dtb in DEFAULT_DTB or UPDATED_DTB
        defaultClassreportRoomShouldBeFound("dtb.in=" + DEFAULT_DTB + "," + UPDATED_DTB);

        // Get all the classreportRoomList where dtb equals to UPDATED_DTB
        defaultClassreportRoomShouldNotBeFound("dtb.in=" + UPDATED_DTB);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByDtbIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where dtb is not null
        defaultClassreportRoomShouldBeFound("dtb.specified=true");

        // Get all the classreportRoomList where dtb is null
        defaultClassreportRoomShouldNotBeFound("dtb.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByDtbIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where dtb is greater than or equal to DEFAULT_DTB
        defaultClassreportRoomShouldBeFound("dtb.greaterThanOrEqual=" + DEFAULT_DTB);

        // Get all the classreportRoomList where dtb is greater than or equal to UPDATED_DTB
        defaultClassreportRoomShouldNotBeFound("dtb.greaterThanOrEqual=" + UPDATED_DTB);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByDtbIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where dtb is less than or equal to DEFAULT_DTB
        defaultClassreportRoomShouldBeFound("dtb.lessThanOrEqual=" + DEFAULT_DTB);

        // Get all the classreportRoomList where dtb is less than or equal to SMALLER_DTB
        defaultClassreportRoomShouldNotBeFound("dtb.lessThanOrEqual=" + SMALLER_DTB);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByDtbIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where dtb is less than DEFAULT_DTB
        defaultClassreportRoomShouldNotBeFound("dtb.lessThan=" + DEFAULT_DTB);

        // Get all the classreportRoomList where dtb is less than UPDATED_DTB
        defaultClassreportRoomShouldBeFound("dtb.lessThan=" + UPDATED_DTB);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsByDtbIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where dtb is greater than DEFAULT_DTB
        defaultClassreportRoomShouldNotBeFound("dtb.greaterThan=" + DEFAULT_DTB);

        // Get all the classreportRoomList where dtb is greater than SMALLER_DTB
        defaultClassreportRoomShouldBeFound("dtb.greaterThan=" + SMALLER_DTB);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsBySszxIsEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where sszx equals to DEFAULT_SSZX
        defaultClassreportRoomShouldBeFound("sszx.equals=" + DEFAULT_SSZX);

        // Get all the classreportRoomList where sszx equals to UPDATED_SSZX
        defaultClassreportRoomShouldNotBeFound("sszx.equals=" + UPDATED_SSZX);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsBySszxIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where sszx not equals to DEFAULT_SSZX
        defaultClassreportRoomShouldNotBeFound("sszx.notEquals=" + DEFAULT_SSZX);

        // Get all the classreportRoomList where sszx not equals to UPDATED_SSZX
        defaultClassreportRoomShouldBeFound("sszx.notEquals=" + UPDATED_SSZX);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsBySszxIsInShouldWork() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where sszx in DEFAULT_SSZX or UPDATED_SSZX
        defaultClassreportRoomShouldBeFound("sszx.in=" + DEFAULT_SSZX + "," + UPDATED_SSZX);

        // Get all the classreportRoomList where sszx equals to UPDATED_SSZX
        defaultClassreportRoomShouldNotBeFound("sszx.in=" + UPDATED_SSZX);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsBySszxIsNullOrNotNull() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where sszx is not null
        defaultClassreportRoomShouldBeFound("sszx.specified=true");

        // Get all the classreportRoomList where sszx is null
        defaultClassreportRoomShouldNotBeFound("sszx.specified=false");
    }

    @Test
    @Transactional
    void getAllClassreportRoomsBySszxIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where sszx is greater than or equal to DEFAULT_SSZX
        defaultClassreportRoomShouldBeFound("sszx.greaterThanOrEqual=" + DEFAULT_SSZX);

        // Get all the classreportRoomList where sszx is greater than or equal to UPDATED_SSZX
        defaultClassreportRoomShouldNotBeFound("sszx.greaterThanOrEqual=" + UPDATED_SSZX);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsBySszxIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where sszx is less than or equal to DEFAULT_SSZX
        defaultClassreportRoomShouldBeFound("sszx.lessThanOrEqual=" + DEFAULT_SSZX);

        // Get all the classreportRoomList where sszx is less than or equal to SMALLER_SSZX
        defaultClassreportRoomShouldNotBeFound("sszx.lessThanOrEqual=" + SMALLER_SSZX);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsBySszxIsLessThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where sszx is less than DEFAULT_SSZX
        defaultClassreportRoomShouldNotBeFound("sszx.lessThan=" + DEFAULT_SSZX);

        // Get all the classreportRoomList where sszx is less than UPDATED_SSZX
        defaultClassreportRoomShouldBeFound("sszx.lessThan=" + UPDATED_SSZX);
    }

    @Test
    @Transactional
    void getAllClassreportRoomsBySszxIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        // Get all the classreportRoomList where sszx is greater than DEFAULT_SSZX
        defaultClassreportRoomShouldNotBeFound("sszx.greaterThan=" + DEFAULT_SSZX);

        // Get all the classreportRoomList where sszx is greater than SMALLER_SSZX
        defaultClassreportRoomShouldBeFound("sszx.greaterThan=" + SMALLER_SSZX);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClassreportRoomShouldBeFound(String filter) throws Exception {
        restClassreportRoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classreportRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].yfj").value(hasItem(sameNumber(DEFAULT_YFJ))))
            .andExpect(jsonPath("$.[*].yfj9008").value(hasItem(sameNumber(DEFAULT_YFJ_9008))))
            .andExpect(jsonPath("$.[*].yfj9009").value(hasItem(sameNumber(DEFAULT_YFJ_9009))))
            .andExpect(jsonPath("$.[*].yfj9007").value(hasItem(sameNumber(DEFAULT_YFJ_9007))))
            .andExpect(jsonPath("$.[*].gz").value(hasItem(sameNumber(DEFAULT_GZ))))
            .andExpect(jsonPath("$.[*].ff").value(hasItem(sameNumber(DEFAULT_FF))))
            .andExpect(jsonPath("$.[*].minibar").value(hasItem(sameNumber(DEFAULT_MINIBAR))))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(sameNumber(DEFAULT_PHONE))))
            .andExpect(jsonPath("$.[*].other").value(hasItem(sameNumber(DEFAULT_OTHER))))
            .andExpect(jsonPath("$.[*].pc").value(hasItem(sameNumber(DEFAULT_PC))))
            .andExpect(jsonPath("$.[*].cz").value(hasItem(sameNumber(DEFAULT_CZ))))
            .andExpect(jsonPath("$.[*].cy").value(hasItem(sameNumber(DEFAULT_CY))))
            .andExpect(jsonPath("$.[*].md").value(hasItem(sameNumber(DEFAULT_MD))))
            .andExpect(jsonPath("$.[*].huiy").value(hasItem(sameNumber(DEFAULT_HUIY))))
            .andExpect(jsonPath("$.[*].dtb").value(hasItem(sameNumber(DEFAULT_DTB))))
            .andExpect(jsonPath("$.[*].sszx").value(hasItem(sameNumber(DEFAULT_SSZX))));

        // Check, that the count call also returns 1
        restClassreportRoomMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClassreportRoomShouldNotBeFound(String filter) throws Exception {
        restClassreportRoomMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClassreportRoomMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClassreportRoom() throws Exception {
        // Get the classreportRoom
        restClassreportRoomMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClassreportRoom() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        int databaseSizeBeforeUpdate = classreportRoomRepository.findAll().size();

        // Update the classreportRoom
        ClassreportRoom updatedClassreportRoom = classreportRoomRepository.findById(classreportRoom.getId()).get();
        // Disconnect from session so that the updates on updatedClassreportRoom are not directly saved in db
        em.detach(updatedClassreportRoom);
        updatedClassreportRoom
            .account(UPDATED_ACCOUNT)
            .roomn(UPDATED_ROOMN)
            .yfj(UPDATED_YFJ)
            .yfj9008(UPDATED_YFJ_9008)
            .yfj9009(UPDATED_YFJ_9009)
            .yfj9007(UPDATED_YFJ_9007)
            .gz(UPDATED_GZ)
            .ff(UPDATED_FF)
            .minibar(UPDATED_MINIBAR)
            .phone(UPDATED_PHONE)
            .other(UPDATED_OTHER)
            .pc(UPDATED_PC)
            .cz(UPDATED_CZ)
            .cy(UPDATED_CY)
            .md(UPDATED_MD)
            .huiy(UPDATED_HUIY)
            .dtb(UPDATED_DTB)
            .sszx(UPDATED_SSZX);
        ClassreportRoomDTO classreportRoomDTO = classreportRoomMapper.toDto(updatedClassreportRoom);

        restClassreportRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classreportRoomDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classreportRoomDTO))
            )
            .andExpect(status().isOk());

        // Validate the ClassreportRoom in the database
        List<ClassreportRoom> classreportRoomList = classreportRoomRepository.findAll();
        assertThat(classreportRoomList).hasSize(databaseSizeBeforeUpdate);
        ClassreportRoom testClassreportRoom = classreportRoomList.get(classreportRoomList.size() - 1);
        assertThat(testClassreportRoom.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testClassreportRoom.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testClassreportRoom.getYfj()).isEqualTo(UPDATED_YFJ);
        assertThat(testClassreportRoom.getYfj9008()).isEqualTo(UPDATED_YFJ_9008);
        assertThat(testClassreportRoom.getYfj9009()).isEqualTo(UPDATED_YFJ_9009);
        assertThat(testClassreportRoom.getYfj9007()).isEqualTo(UPDATED_YFJ_9007);
        assertThat(testClassreportRoom.getGz()).isEqualTo(UPDATED_GZ);
        assertThat(testClassreportRoom.getFf()).isEqualTo(UPDATED_FF);
        assertThat(testClassreportRoom.getMinibar()).isEqualTo(UPDATED_MINIBAR);
        assertThat(testClassreportRoom.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testClassreportRoom.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testClassreportRoom.getPc()).isEqualTo(UPDATED_PC);
        assertThat(testClassreportRoom.getCz()).isEqualTo(UPDATED_CZ);
        assertThat(testClassreportRoom.getCy()).isEqualTo(UPDATED_CY);
        assertThat(testClassreportRoom.getMd()).isEqualTo(UPDATED_MD);
        assertThat(testClassreportRoom.getHuiy()).isEqualTo(UPDATED_HUIY);
        assertThat(testClassreportRoom.getDtb()).isEqualTo(UPDATED_DTB);
        assertThat(testClassreportRoom.getSszx()).isEqualTo(UPDATED_SSZX);

        // Validate the ClassreportRoom in Elasticsearch
        verify(mockClassreportRoomSearchRepository).save(testClassreportRoom);
    }

    @Test
    @Transactional
    void putNonExistingClassreportRoom() throws Exception {
        int databaseSizeBeforeUpdate = classreportRoomRepository.findAll().size();
        classreportRoom.setId(count.incrementAndGet());

        // Create the ClassreportRoom
        ClassreportRoomDTO classreportRoomDTO = classreportRoomMapper.toDto(classreportRoom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassreportRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classreportRoomDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classreportRoomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassreportRoom in the database
        List<ClassreportRoom> classreportRoomList = classreportRoomRepository.findAll();
        assertThat(classreportRoomList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassreportRoom in Elasticsearch
        verify(mockClassreportRoomSearchRepository, times(0)).save(classreportRoom);
    }

    @Test
    @Transactional
    void putWithIdMismatchClassreportRoom() throws Exception {
        int databaseSizeBeforeUpdate = classreportRoomRepository.findAll().size();
        classreportRoom.setId(count.incrementAndGet());

        // Create the ClassreportRoom
        ClassreportRoomDTO classreportRoomDTO = classreportRoomMapper.toDto(classreportRoom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassreportRoomMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classreportRoomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassreportRoom in the database
        List<ClassreportRoom> classreportRoomList = classreportRoomRepository.findAll();
        assertThat(classreportRoomList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassreportRoom in Elasticsearch
        verify(mockClassreportRoomSearchRepository, times(0)).save(classreportRoom);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClassreportRoom() throws Exception {
        int databaseSizeBeforeUpdate = classreportRoomRepository.findAll().size();
        classreportRoom.setId(count.incrementAndGet());

        // Create the ClassreportRoom
        ClassreportRoomDTO classreportRoomDTO = classreportRoomMapper.toDto(classreportRoom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassreportRoomMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classreportRoomDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClassreportRoom in the database
        List<ClassreportRoom> classreportRoomList = classreportRoomRepository.findAll();
        assertThat(classreportRoomList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassreportRoom in Elasticsearch
        verify(mockClassreportRoomSearchRepository, times(0)).save(classreportRoom);
    }

    @Test
    @Transactional
    void partialUpdateClassreportRoomWithPatch() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        int databaseSizeBeforeUpdate = classreportRoomRepository.findAll().size();

        // Update the classreportRoom using partial update
        ClassreportRoom partialUpdatedClassreportRoom = new ClassreportRoom();
        partialUpdatedClassreportRoom.setId(classreportRoom.getId());

        partialUpdatedClassreportRoom
            .account(UPDATED_ACCOUNT)
            .yfj(UPDATED_YFJ)
            .yfj9008(UPDATED_YFJ_9008)
            .gz(UPDATED_GZ)
            .ff(UPDATED_FF)
            .phone(UPDATED_PHONE)
            .dtb(UPDATED_DTB);

        restClassreportRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassreportRoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClassreportRoom))
            )
            .andExpect(status().isOk());

        // Validate the ClassreportRoom in the database
        List<ClassreportRoom> classreportRoomList = classreportRoomRepository.findAll();
        assertThat(classreportRoomList).hasSize(databaseSizeBeforeUpdate);
        ClassreportRoom testClassreportRoom = classreportRoomList.get(classreportRoomList.size() - 1);
        assertThat(testClassreportRoom.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testClassreportRoom.getRoomn()).isEqualTo(DEFAULT_ROOMN);
        assertThat(testClassreportRoom.getYfj()).isEqualByComparingTo(UPDATED_YFJ);
        assertThat(testClassreportRoom.getYfj9008()).isEqualByComparingTo(UPDATED_YFJ_9008);
        assertThat(testClassreportRoom.getYfj9009()).isEqualByComparingTo(DEFAULT_YFJ_9009);
        assertThat(testClassreportRoom.getYfj9007()).isEqualByComparingTo(DEFAULT_YFJ_9007);
        assertThat(testClassreportRoom.getGz()).isEqualByComparingTo(UPDATED_GZ);
        assertThat(testClassreportRoom.getFf()).isEqualByComparingTo(UPDATED_FF);
        assertThat(testClassreportRoom.getMinibar()).isEqualByComparingTo(DEFAULT_MINIBAR);
        assertThat(testClassreportRoom.getPhone()).isEqualByComparingTo(UPDATED_PHONE);
        assertThat(testClassreportRoom.getOther()).isEqualByComparingTo(DEFAULT_OTHER);
        assertThat(testClassreportRoom.getPc()).isEqualByComparingTo(DEFAULT_PC);
        assertThat(testClassreportRoom.getCz()).isEqualByComparingTo(DEFAULT_CZ);
        assertThat(testClassreportRoom.getCy()).isEqualByComparingTo(DEFAULT_CY);
        assertThat(testClassreportRoom.getMd()).isEqualByComparingTo(DEFAULT_MD);
        assertThat(testClassreportRoom.getHuiy()).isEqualByComparingTo(DEFAULT_HUIY);
        assertThat(testClassreportRoom.getDtb()).isEqualByComparingTo(UPDATED_DTB);
        assertThat(testClassreportRoom.getSszx()).isEqualByComparingTo(DEFAULT_SSZX);
    }

    @Test
    @Transactional
    void fullUpdateClassreportRoomWithPatch() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        int databaseSizeBeforeUpdate = classreportRoomRepository.findAll().size();

        // Update the classreportRoom using partial update
        ClassreportRoom partialUpdatedClassreportRoom = new ClassreportRoom();
        partialUpdatedClassreportRoom.setId(classreportRoom.getId());

        partialUpdatedClassreportRoom
            .account(UPDATED_ACCOUNT)
            .roomn(UPDATED_ROOMN)
            .yfj(UPDATED_YFJ)
            .yfj9008(UPDATED_YFJ_9008)
            .yfj9009(UPDATED_YFJ_9009)
            .yfj9007(UPDATED_YFJ_9007)
            .gz(UPDATED_GZ)
            .ff(UPDATED_FF)
            .minibar(UPDATED_MINIBAR)
            .phone(UPDATED_PHONE)
            .other(UPDATED_OTHER)
            .pc(UPDATED_PC)
            .cz(UPDATED_CZ)
            .cy(UPDATED_CY)
            .md(UPDATED_MD)
            .huiy(UPDATED_HUIY)
            .dtb(UPDATED_DTB)
            .sszx(UPDATED_SSZX);

        restClassreportRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassreportRoom.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClassreportRoom))
            )
            .andExpect(status().isOk());

        // Validate the ClassreportRoom in the database
        List<ClassreportRoom> classreportRoomList = classreportRoomRepository.findAll();
        assertThat(classreportRoomList).hasSize(databaseSizeBeforeUpdate);
        ClassreportRoom testClassreportRoom = classreportRoomList.get(classreportRoomList.size() - 1);
        assertThat(testClassreportRoom.getAccount()).isEqualTo(UPDATED_ACCOUNT);
        assertThat(testClassreportRoom.getRoomn()).isEqualTo(UPDATED_ROOMN);
        assertThat(testClassreportRoom.getYfj()).isEqualByComparingTo(UPDATED_YFJ);
        assertThat(testClassreportRoom.getYfj9008()).isEqualByComparingTo(UPDATED_YFJ_9008);
        assertThat(testClassreportRoom.getYfj9009()).isEqualByComparingTo(UPDATED_YFJ_9009);
        assertThat(testClassreportRoom.getYfj9007()).isEqualByComparingTo(UPDATED_YFJ_9007);
        assertThat(testClassreportRoom.getGz()).isEqualByComparingTo(UPDATED_GZ);
        assertThat(testClassreportRoom.getFf()).isEqualByComparingTo(UPDATED_FF);
        assertThat(testClassreportRoom.getMinibar()).isEqualByComparingTo(UPDATED_MINIBAR);
        assertThat(testClassreportRoom.getPhone()).isEqualByComparingTo(UPDATED_PHONE);
        assertThat(testClassreportRoom.getOther()).isEqualByComparingTo(UPDATED_OTHER);
        assertThat(testClassreportRoom.getPc()).isEqualByComparingTo(UPDATED_PC);
        assertThat(testClassreportRoom.getCz()).isEqualByComparingTo(UPDATED_CZ);
        assertThat(testClassreportRoom.getCy()).isEqualByComparingTo(UPDATED_CY);
        assertThat(testClassreportRoom.getMd()).isEqualByComparingTo(UPDATED_MD);
        assertThat(testClassreportRoom.getHuiy()).isEqualByComparingTo(UPDATED_HUIY);
        assertThat(testClassreportRoom.getDtb()).isEqualByComparingTo(UPDATED_DTB);
        assertThat(testClassreportRoom.getSszx()).isEqualByComparingTo(UPDATED_SSZX);
    }

    @Test
    @Transactional
    void patchNonExistingClassreportRoom() throws Exception {
        int databaseSizeBeforeUpdate = classreportRoomRepository.findAll().size();
        classreportRoom.setId(count.incrementAndGet());

        // Create the ClassreportRoom
        ClassreportRoomDTO classreportRoomDTO = classreportRoomMapper.toDto(classreportRoom);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassreportRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classreportRoomDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classreportRoomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassreportRoom in the database
        List<ClassreportRoom> classreportRoomList = classreportRoomRepository.findAll();
        assertThat(classreportRoomList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassreportRoom in Elasticsearch
        verify(mockClassreportRoomSearchRepository, times(0)).save(classreportRoom);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClassreportRoom() throws Exception {
        int databaseSizeBeforeUpdate = classreportRoomRepository.findAll().size();
        classreportRoom.setId(count.incrementAndGet());

        // Create the ClassreportRoom
        ClassreportRoomDTO classreportRoomDTO = classreportRoomMapper.toDto(classreportRoom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassreportRoomMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classreportRoomDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassreportRoom in the database
        List<ClassreportRoom> classreportRoomList = classreportRoomRepository.findAll();
        assertThat(classreportRoomList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassreportRoom in Elasticsearch
        verify(mockClassreportRoomSearchRepository, times(0)).save(classreportRoom);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClassreportRoom() throws Exception {
        int databaseSizeBeforeUpdate = classreportRoomRepository.findAll().size();
        classreportRoom.setId(count.incrementAndGet());

        // Create the ClassreportRoom
        ClassreportRoomDTO classreportRoomDTO = classreportRoomMapper.toDto(classreportRoom);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassreportRoomMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classreportRoomDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClassreportRoom in the database
        List<ClassreportRoom> classreportRoomList = classreportRoomRepository.findAll();
        assertThat(classreportRoomList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassreportRoom in Elasticsearch
        verify(mockClassreportRoomSearchRepository, times(0)).save(classreportRoom);
    }

    @Test
    @Transactional
    void deleteClassreportRoom() throws Exception {
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);

        int databaseSizeBeforeDelete = classreportRoomRepository.findAll().size();

        // Delete the classreportRoom
        restClassreportRoomMockMvc
            .perform(delete(ENTITY_API_URL_ID, classreportRoom.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassreportRoom> classreportRoomList = classreportRoomRepository.findAll();
        assertThat(classreportRoomList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ClassreportRoom in Elasticsearch
        verify(mockClassreportRoomSearchRepository, times(1)).deleteById(classreportRoom.getId());
    }

    @Test
    @Transactional
    void searchClassreportRoom() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        classreportRoomRepository.saveAndFlush(classreportRoom);
        when(mockClassreportRoomSearchRepository.search(queryStringQuery("id:" + classreportRoom.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(classreportRoom), PageRequest.of(0, 1), 1));

        // Search the classreportRoom
        restClassreportRoomMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + classreportRoom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classreportRoom.getId().intValue())))
            .andExpect(jsonPath("$.[*].account").value(hasItem(DEFAULT_ACCOUNT)))
            .andExpect(jsonPath("$.[*].roomn").value(hasItem(DEFAULT_ROOMN)))
            .andExpect(jsonPath("$.[*].yfj").value(hasItem(sameNumber(DEFAULT_YFJ))))
            .andExpect(jsonPath("$.[*].yfj9008").value(hasItem(sameNumber(DEFAULT_YFJ_9008))))
            .andExpect(jsonPath("$.[*].yfj9009").value(hasItem(sameNumber(DEFAULT_YFJ_9009))))
            .andExpect(jsonPath("$.[*].yfj9007").value(hasItem(sameNumber(DEFAULT_YFJ_9007))))
            .andExpect(jsonPath("$.[*].gz").value(hasItem(sameNumber(DEFAULT_GZ))))
            .andExpect(jsonPath("$.[*].ff").value(hasItem(sameNumber(DEFAULT_FF))))
            .andExpect(jsonPath("$.[*].minibar").value(hasItem(sameNumber(DEFAULT_MINIBAR))))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(sameNumber(DEFAULT_PHONE))))
            .andExpect(jsonPath("$.[*].other").value(hasItem(sameNumber(DEFAULT_OTHER))))
            .andExpect(jsonPath("$.[*].pc").value(hasItem(sameNumber(DEFAULT_PC))))
            .andExpect(jsonPath("$.[*].cz").value(hasItem(sameNumber(DEFAULT_CZ))))
            .andExpect(jsonPath("$.[*].cy").value(hasItem(sameNumber(DEFAULT_CY))))
            .andExpect(jsonPath("$.[*].md").value(hasItem(sameNumber(DEFAULT_MD))))
            .andExpect(jsonPath("$.[*].huiy").value(hasItem(sameNumber(DEFAULT_HUIY))))
            .andExpect(jsonPath("$.[*].dtb").value(hasItem(sameNumber(DEFAULT_DTB))))
            .andExpect(jsonPath("$.[*].sszx").value(hasItem(sameNumber(DEFAULT_SSZX))));
    }
}
