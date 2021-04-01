package ihotel.app.web.rest;

import static ihotel.app.web.rest.TestUtil.sameNumber;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ihotel.app.IntegrationTest;
import ihotel.app.domain.ClassRename;
import ihotel.app.repository.ClassRenameRepository;
import ihotel.app.repository.search.ClassRenameSearchRepository;
import ihotel.app.service.criteria.ClassRenameCriteria;
import ihotel.app.service.dto.ClassRenameDTO;
import ihotel.app.service.mapper.ClassRenameMapper;
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
 * Integration tests for the {@link ClassRenameResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClassRenameResourceIT {

    private static final Instant DEFAULT_DT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMPN = "AAAAAAAAAA";
    private static final String UPDATED_EMPN = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_OLDMONEY = new BigDecimal(1);
    private static final BigDecimal UPDATED_OLDMONEY = new BigDecimal(2);
    private static final BigDecimal SMALLER_OLDMONEY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_GETMONEY = new BigDecimal(1);
    private static final BigDecimal UPDATED_GETMONEY = new BigDecimal(2);
    private static final BigDecimal SMALLER_GETMONEY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOUP = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOUP = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOUP = new BigDecimal(1 - 1);

    private static final String DEFAULT_DOWNEMPN = "AAAAAAAAAA";
    private static final String UPDATED_DOWNEMPN = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TODOWN = new BigDecimal(1);
    private static final BigDecimal UPDATED_TODOWN = new BigDecimal(2);
    private static final BigDecimal SMALLER_TODOWN = new BigDecimal(1 - 1);

    private static final Long DEFAULT_FLAG = 1L;
    private static final Long UPDATED_FLAG = 2L;
    private static final Long SMALLER_FLAG = 1L - 1L;

    private static final BigDecimal DEFAULT_OLD_2 = new BigDecimal(1);
    private static final BigDecimal UPDATED_OLD_2 = new BigDecimal(2);
    private static final BigDecimal SMALLER_OLD_2 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_GET_2 = new BigDecimal(1);
    private static final BigDecimal UPDATED_GET_2 = new BigDecimal(2);
    private static final BigDecimal SMALLER_GET_2 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOUP_2 = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOUP_2 = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOUP_2 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TODOWN_2 = new BigDecimal(1);
    private static final BigDecimal UPDATED_TODOWN_2 = new BigDecimal(2);
    private static final BigDecimal SMALLER_TODOWN_2 = new BigDecimal(1 - 1);

    private static final String DEFAULT_UPEMPN_2 = "AAAAAAAAAA";
    private static final String UPDATED_UPEMPN_2 = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_IM_9008 = new BigDecimal(1);
    private static final BigDecimal UPDATED_IM_9008 = new BigDecimal(2);
    private static final BigDecimal SMALLER_IM_9008 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_IM_9009 = new BigDecimal(1);
    private static final BigDecimal UPDATED_IM_9009 = new BigDecimal(2);
    private static final BigDecimal SMALLER_IM_9009 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CO_9991 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_9991 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CO_9991 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CO_9992 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_9992 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CO_9992 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CO_9993 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_9993 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CO_9993 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CO_9994 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_9994 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CO_9994 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CO_9995 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_9995 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CO_9995 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CO_9998 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_9998 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CO_9998 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_IM_9007 = new BigDecimal(1);
    private static final BigDecimal UPDATED_IM_9007 = new BigDecimal(2);
    private static final BigDecimal SMALLER_IM_9007 = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_GOTIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_GOTIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_CO_9999 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_9999 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CO_9999 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CM_9008 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CM_9008 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CM_9008 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CM_9009 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CM_9009 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CM_9009 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CO_99910 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_99910 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CO_99910 = new BigDecimal(1 - 1);

    private static final String DEFAULT_CHECK_SIGN = "AA";
    private static final String UPDATED_CHECK_SIGN = "BB";

    private static final String DEFAULT_CLASS_PB = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_PB = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CK = new BigDecimal(1);
    private static final BigDecimal UPDATED_CK = new BigDecimal(2);
    private static final BigDecimal SMALLER_CK = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DK = new BigDecimal(1);
    private static final BigDecimal UPDATED_DK = new BigDecimal(2);
    private static final BigDecimal SMALLER_DK = new BigDecimal(1 - 1);

    private static final Instant DEFAULT_SJRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SJRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_QSJRQ = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_QSJRQ = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final BigDecimal DEFAULT_BYJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_BYJE = new BigDecimal(2);
    private static final BigDecimal SMALLER_BYJE = new BigDecimal(1 - 1);

    private static final String DEFAULT_XFCW = "AAAAAAAAAA";
    private static final String UPDATED_XFCW = "BBBBBBBBBB";

    private static final String DEFAULT_HOTELDM = "AAAAAAAAAA";
    private static final String UPDATED_HOTELDM = "BBBBBBBBBB";

    private static final Long DEFAULT_ISNEW = 1L;
    private static final Long UPDATED_ISNEW = 2L;
    private static final Long SMALLER_ISNEW = 1L - 1L;

    private static final BigDecimal DEFAULT_CO_99912 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_99912 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CO_99912 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_XJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_XJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_XJ = new BigDecimal(1 - 1);

    private static final String DEFAULT_CLASSNAME = "AAAAAAAAAA";
    private static final String UPDATED_CLASSNAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CO_9010 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_9010 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CO_9010 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CO_9012 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_9012 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CO_9012 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CO_9013 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_9013 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CO_9013 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CO_9014 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_9014 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CO_9014 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CO_99915 = new BigDecimal(1);
    private static final BigDecimal UPDATED_CO_99915 = new BigDecimal(2);
    private static final BigDecimal SMALLER_CO_99915 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HYXJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_HYXJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_HYXJ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HYSK = new BigDecimal(1);
    private static final BigDecimal UPDATED_HYSK = new BigDecimal(2);
    private static final BigDecimal SMALLER_HYSK = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HYQT = new BigDecimal(1);
    private static final BigDecimal UPDATED_HYQT = new BigDecimal(2);
    private static final BigDecimal SMALLER_HYQT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HKXJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_HKXJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_HKXJ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HKSK = new BigDecimal(1);
    private static final BigDecimal UPDATED_HKSK = new BigDecimal(2);
    private static final BigDecimal SMALLER_HKSK = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HKQT = new BigDecimal(1);
    private static final BigDecimal UPDATED_HKQT = new BigDecimal(2);
    private static final BigDecimal SMALLER_HKQT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_QTWT = new BigDecimal(1);
    private static final BigDecimal UPDATED_QTWT = new BigDecimal(2);
    private static final BigDecimal SMALLER_QTWT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_QTYSQ = new BigDecimal(1);
    private static final BigDecimal UPDATED_QTYSQ = new BigDecimal(2);
    private static final BigDecimal SMALLER_QTYSQ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_BBYSJ = new BigDecimal(1);
    private static final BigDecimal UPDATED_BBYSJ = new BigDecimal(2);
    private static final BigDecimal SMALLER_BBYSJ = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ZFBJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_ZFBJE = new BigDecimal(2);
    private static final BigDecimal SMALLER_ZFBJE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_WXJE = new BigDecimal(1);
    private static final BigDecimal UPDATED_WXJE = new BigDecimal(2);
    private static final BigDecimal SMALLER_WXJE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_W_99920 = new BigDecimal(1);
    private static final BigDecimal UPDATED_W_99920 = new BigDecimal(2);
    private static final BigDecimal SMALLER_W_99920 = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_Z_99921 = new BigDecimal(1);
    private static final BigDecimal UPDATED_Z_99921 = new BigDecimal(2);
    private static final BigDecimal SMALLER_Z_99921 = new BigDecimal(1 - 1);

    private static final String ENTITY_API_URL = "/api/class-renames";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    private static final String ENTITY_SEARCH_API_URL = "/api/_search/class-renames";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClassRenameRepository classRenameRepository;

    @Autowired
    private ClassRenameMapper classRenameMapper;

    /**
     * This repository is mocked in the ihotel.app.repository.search test package.
     *
     * @see ihotel.app.repository.search.ClassRenameSearchRepositoryMockConfiguration
     */
    @Autowired
    private ClassRenameSearchRepository mockClassRenameSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClassRenameMockMvc;

    private ClassRename classRename;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassRename createEntity(EntityManager em) {
        ClassRename classRename = new ClassRename()
            .dt(DEFAULT_DT)
            .empn(DEFAULT_EMPN)
            .oldmoney(DEFAULT_OLDMONEY)
            .getmoney(DEFAULT_GETMONEY)
            .toup(DEFAULT_TOUP)
            .downempn(DEFAULT_DOWNEMPN)
            .todown(DEFAULT_TODOWN)
            .flag(DEFAULT_FLAG)
            .old2(DEFAULT_OLD_2)
            .get2(DEFAULT_GET_2)
            .toup2(DEFAULT_TOUP_2)
            .todown2(DEFAULT_TODOWN_2)
            .upempn2(DEFAULT_UPEMPN_2)
            .im9008(DEFAULT_IM_9008)
            .im9009(DEFAULT_IM_9009)
            .co9991(DEFAULT_CO_9991)
            .co9992(DEFAULT_CO_9992)
            .co9993(DEFAULT_CO_9993)
            .co9994(DEFAULT_CO_9994)
            .co9995(DEFAULT_CO_9995)
            .co9998(DEFAULT_CO_9998)
            .im9007(DEFAULT_IM_9007)
            .gotime(DEFAULT_GOTIME)
            .co9999(DEFAULT_CO_9999)
            .cm9008(DEFAULT_CM_9008)
            .cm9009(DEFAULT_CM_9009)
            .co99910(DEFAULT_CO_99910)
            .checkSign(DEFAULT_CHECK_SIGN)
            .classPb(DEFAULT_CLASS_PB)
            .ck(DEFAULT_CK)
            .dk(DEFAULT_DK)
            .sjrq(DEFAULT_SJRQ)
            .qsjrq(DEFAULT_QSJRQ)
            .byje(DEFAULT_BYJE)
            .xfcw(DEFAULT_XFCW)
            .hoteldm(DEFAULT_HOTELDM)
            .isnew(DEFAULT_ISNEW)
            .co99912(DEFAULT_CO_99912)
            .xj(DEFAULT_XJ)
            .classname(DEFAULT_CLASSNAME)
            .co9010(DEFAULT_CO_9010)
            .co9012(DEFAULT_CO_9012)
            .co9013(DEFAULT_CO_9013)
            .co9014(DEFAULT_CO_9014)
            .co99915(DEFAULT_CO_99915)
            .hyxj(DEFAULT_HYXJ)
            .hysk(DEFAULT_HYSK)
            .hyqt(DEFAULT_HYQT)
            .hkxj(DEFAULT_HKXJ)
            .hksk(DEFAULT_HKSK)
            .hkqt(DEFAULT_HKQT)
            .qtwt(DEFAULT_QTWT)
            .qtysq(DEFAULT_QTYSQ)
            .bbysj(DEFAULT_BBYSJ)
            .zfbje(DEFAULT_ZFBJE)
            .wxje(DEFAULT_WXJE)
            .w99920(DEFAULT_W_99920)
            .z99921(DEFAULT_Z_99921);
        return classRename;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClassRename createUpdatedEntity(EntityManager em) {
        ClassRename classRename = new ClassRename()
            .dt(UPDATED_DT)
            .empn(UPDATED_EMPN)
            .oldmoney(UPDATED_OLDMONEY)
            .getmoney(UPDATED_GETMONEY)
            .toup(UPDATED_TOUP)
            .downempn(UPDATED_DOWNEMPN)
            .todown(UPDATED_TODOWN)
            .flag(UPDATED_FLAG)
            .old2(UPDATED_OLD_2)
            .get2(UPDATED_GET_2)
            .toup2(UPDATED_TOUP_2)
            .todown2(UPDATED_TODOWN_2)
            .upempn2(UPDATED_UPEMPN_2)
            .im9008(UPDATED_IM_9008)
            .im9009(UPDATED_IM_9009)
            .co9991(UPDATED_CO_9991)
            .co9992(UPDATED_CO_9992)
            .co9993(UPDATED_CO_9993)
            .co9994(UPDATED_CO_9994)
            .co9995(UPDATED_CO_9995)
            .co9998(UPDATED_CO_9998)
            .im9007(UPDATED_IM_9007)
            .gotime(UPDATED_GOTIME)
            .co9999(UPDATED_CO_9999)
            .cm9008(UPDATED_CM_9008)
            .cm9009(UPDATED_CM_9009)
            .co99910(UPDATED_CO_99910)
            .checkSign(UPDATED_CHECK_SIGN)
            .classPb(UPDATED_CLASS_PB)
            .ck(UPDATED_CK)
            .dk(UPDATED_DK)
            .sjrq(UPDATED_SJRQ)
            .qsjrq(UPDATED_QSJRQ)
            .byje(UPDATED_BYJE)
            .xfcw(UPDATED_XFCW)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW)
            .co99912(UPDATED_CO_99912)
            .xj(UPDATED_XJ)
            .classname(UPDATED_CLASSNAME)
            .co9010(UPDATED_CO_9010)
            .co9012(UPDATED_CO_9012)
            .co9013(UPDATED_CO_9013)
            .co9014(UPDATED_CO_9014)
            .co99915(UPDATED_CO_99915)
            .hyxj(UPDATED_HYXJ)
            .hysk(UPDATED_HYSK)
            .hyqt(UPDATED_HYQT)
            .hkxj(UPDATED_HKXJ)
            .hksk(UPDATED_HKSK)
            .hkqt(UPDATED_HKQT)
            .qtwt(UPDATED_QTWT)
            .qtysq(UPDATED_QTYSQ)
            .bbysj(UPDATED_BBYSJ)
            .zfbje(UPDATED_ZFBJE)
            .wxje(UPDATED_WXJE)
            .w99920(UPDATED_W_99920)
            .z99921(UPDATED_Z_99921);
        return classRename;
    }

    @BeforeEach
    public void initTest() {
        classRename = createEntity(em);
    }

    @Test
    @Transactional
    void createClassRename() throws Exception {
        int databaseSizeBeforeCreate = classRenameRepository.findAll().size();
        // Create the ClassRename
        ClassRenameDTO classRenameDTO = classRenameMapper.toDto(classRename);
        restClassRenameMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classRenameDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ClassRename in the database
        List<ClassRename> classRenameList = classRenameRepository.findAll();
        assertThat(classRenameList).hasSize(databaseSizeBeforeCreate + 1);
        ClassRename testClassRename = classRenameList.get(classRenameList.size() - 1);
        assertThat(testClassRename.getDt()).isEqualTo(DEFAULT_DT);
        assertThat(testClassRename.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testClassRename.getOldmoney()).isEqualByComparingTo(DEFAULT_OLDMONEY);
        assertThat(testClassRename.getGetmoney()).isEqualByComparingTo(DEFAULT_GETMONEY);
        assertThat(testClassRename.getToup()).isEqualByComparingTo(DEFAULT_TOUP);
        assertThat(testClassRename.getDownempn()).isEqualTo(DEFAULT_DOWNEMPN);
        assertThat(testClassRename.getTodown()).isEqualByComparingTo(DEFAULT_TODOWN);
        assertThat(testClassRename.getFlag()).isEqualTo(DEFAULT_FLAG);
        assertThat(testClassRename.getOld2()).isEqualByComparingTo(DEFAULT_OLD_2);
        assertThat(testClassRename.getGet2()).isEqualByComparingTo(DEFAULT_GET_2);
        assertThat(testClassRename.getToup2()).isEqualByComparingTo(DEFAULT_TOUP_2);
        assertThat(testClassRename.getTodown2()).isEqualByComparingTo(DEFAULT_TODOWN_2);
        assertThat(testClassRename.getUpempn2()).isEqualTo(DEFAULT_UPEMPN_2);
        assertThat(testClassRename.getIm9008()).isEqualByComparingTo(DEFAULT_IM_9008);
        assertThat(testClassRename.getIm9009()).isEqualByComparingTo(DEFAULT_IM_9009);
        assertThat(testClassRename.getCo9991()).isEqualByComparingTo(DEFAULT_CO_9991);
        assertThat(testClassRename.getCo9992()).isEqualByComparingTo(DEFAULT_CO_9992);
        assertThat(testClassRename.getCo9993()).isEqualByComparingTo(DEFAULT_CO_9993);
        assertThat(testClassRename.getCo9994()).isEqualByComparingTo(DEFAULT_CO_9994);
        assertThat(testClassRename.getCo9995()).isEqualByComparingTo(DEFAULT_CO_9995);
        assertThat(testClassRename.getCo9998()).isEqualByComparingTo(DEFAULT_CO_9998);
        assertThat(testClassRename.getIm9007()).isEqualByComparingTo(DEFAULT_IM_9007);
        assertThat(testClassRename.getGotime()).isEqualTo(DEFAULT_GOTIME);
        assertThat(testClassRename.getCo9999()).isEqualByComparingTo(DEFAULT_CO_9999);
        assertThat(testClassRename.getCm9008()).isEqualByComparingTo(DEFAULT_CM_9008);
        assertThat(testClassRename.getCm9009()).isEqualByComparingTo(DEFAULT_CM_9009);
        assertThat(testClassRename.getCo99910()).isEqualByComparingTo(DEFAULT_CO_99910);
        assertThat(testClassRename.getCheckSign()).isEqualTo(DEFAULT_CHECK_SIGN);
        assertThat(testClassRename.getClassPb()).isEqualTo(DEFAULT_CLASS_PB);
        assertThat(testClassRename.getCk()).isEqualByComparingTo(DEFAULT_CK);
        assertThat(testClassRename.getDk()).isEqualByComparingTo(DEFAULT_DK);
        assertThat(testClassRename.getSjrq()).isEqualTo(DEFAULT_SJRQ);
        assertThat(testClassRename.getQsjrq()).isEqualTo(DEFAULT_QSJRQ);
        assertThat(testClassRename.getByje()).isEqualByComparingTo(DEFAULT_BYJE);
        assertThat(testClassRename.getXfcw()).isEqualTo(DEFAULT_XFCW);
        assertThat(testClassRename.getHoteldm()).isEqualTo(DEFAULT_HOTELDM);
        assertThat(testClassRename.getIsnew()).isEqualTo(DEFAULT_ISNEW);
        assertThat(testClassRename.getCo99912()).isEqualByComparingTo(DEFAULT_CO_99912);
        assertThat(testClassRename.getXj()).isEqualByComparingTo(DEFAULT_XJ);
        assertThat(testClassRename.getClassname()).isEqualTo(DEFAULT_CLASSNAME);
        assertThat(testClassRename.getCo9010()).isEqualByComparingTo(DEFAULT_CO_9010);
        assertThat(testClassRename.getCo9012()).isEqualByComparingTo(DEFAULT_CO_9012);
        assertThat(testClassRename.getCo9013()).isEqualByComparingTo(DEFAULT_CO_9013);
        assertThat(testClassRename.getCo9014()).isEqualByComparingTo(DEFAULT_CO_9014);
        assertThat(testClassRename.getCo99915()).isEqualByComparingTo(DEFAULT_CO_99915);
        assertThat(testClassRename.getHyxj()).isEqualByComparingTo(DEFAULT_HYXJ);
        assertThat(testClassRename.getHysk()).isEqualByComparingTo(DEFAULT_HYSK);
        assertThat(testClassRename.getHyqt()).isEqualByComparingTo(DEFAULT_HYQT);
        assertThat(testClassRename.getHkxj()).isEqualByComparingTo(DEFAULT_HKXJ);
        assertThat(testClassRename.getHksk()).isEqualByComparingTo(DEFAULT_HKSK);
        assertThat(testClassRename.getHkqt()).isEqualByComparingTo(DEFAULT_HKQT);
        assertThat(testClassRename.getQtwt()).isEqualByComparingTo(DEFAULT_QTWT);
        assertThat(testClassRename.getQtysq()).isEqualByComparingTo(DEFAULT_QTYSQ);
        assertThat(testClassRename.getBbysj()).isEqualByComparingTo(DEFAULT_BBYSJ);
        assertThat(testClassRename.getZfbje()).isEqualByComparingTo(DEFAULT_ZFBJE);
        assertThat(testClassRename.getWxje()).isEqualByComparingTo(DEFAULT_WXJE);
        assertThat(testClassRename.getw99920()).isEqualByComparingTo(DEFAULT_W_99920);
        assertThat(testClassRename.getz99921()).isEqualByComparingTo(DEFAULT_Z_99921);

        // Validate the ClassRename in Elasticsearch
        verify(mockClassRenameSearchRepository, times(1)).save(testClassRename);
    }

    @Test
    @Transactional
    void createClassRenameWithExistingId() throws Exception {
        // Create the ClassRename with an existing ID
        classRename.setId(1L);
        ClassRenameDTO classRenameDTO = classRenameMapper.toDto(classRename);

        int databaseSizeBeforeCreate = classRenameRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClassRenameMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classRenameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassRename in the database
        List<ClassRename> classRenameList = classRenameRepository.findAll();
        assertThat(classRenameList).hasSize(databaseSizeBeforeCreate);

        // Validate the ClassRename in Elasticsearch
        verify(mockClassRenameSearchRepository, times(0)).save(classRename);
    }

    @Test
    @Transactional
    void checkDtIsRequired() throws Exception {
        int databaseSizeBeforeTest = classRenameRepository.findAll().size();
        // set the field null
        classRename.setDt(null);

        // Create the ClassRename, which fails.
        ClassRenameDTO classRenameDTO = classRenameMapper.toDto(classRename);

        restClassRenameMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classRenameDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClassRename> classRenameList = classRenameRepository.findAll();
        assertThat(classRenameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkEmpnIsRequired() throws Exception {
        int databaseSizeBeforeTest = classRenameRepository.findAll().size();
        // set the field null
        classRename.setEmpn(null);

        // Create the ClassRename, which fails.
        ClassRenameDTO classRenameDTO = classRenameMapper.toDto(classRename);

        restClassRenameMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classRenameDTO))
            )
            .andExpect(status().isBadRequest());

        List<ClassRename> classRenameList = classRenameRepository.findAll();
        assertThat(classRenameList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllClassRenames() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList
        restClassRenameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classRename.getId().intValue())))
            .andExpect(jsonPath("$.[*].dt").value(hasItem(DEFAULT_DT.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].oldmoney").value(hasItem(sameNumber(DEFAULT_OLDMONEY))))
            .andExpect(jsonPath("$.[*].getmoney").value(hasItem(sameNumber(DEFAULT_GETMONEY))))
            .andExpect(jsonPath("$.[*].toup").value(hasItem(sameNumber(DEFAULT_TOUP))))
            .andExpect(jsonPath("$.[*].downempn").value(hasItem(DEFAULT_DOWNEMPN)))
            .andExpect(jsonPath("$.[*].todown").value(hasItem(sameNumber(DEFAULT_TODOWN))))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].old2").value(hasItem(sameNumber(DEFAULT_OLD_2))))
            .andExpect(jsonPath("$.[*].get2").value(hasItem(sameNumber(DEFAULT_GET_2))))
            .andExpect(jsonPath("$.[*].toup2").value(hasItem(sameNumber(DEFAULT_TOUP_2))))
            .andExpect(jsonPath("$.[*].todown2").value(hasItem(sameNumber(DEFAULT_TODOWN_2))))
            .andExpect(jsonPath("$.[*].upempn2").value(hasItem(DEFAULT_UPEMPN_2)))
            .andExpect(jsonPath("$.[*].im9008").value(hasItem(sameNumber(DEFAULT_IM_9008))))
            .andExpect(jsonPath("$.[*].im9009").value(hasItem(sameNumber(DEFAULT_IM_9009))))
            .andExpect(jsonPath("$.[*].co9991").value(hasItem(sameNumber(DEFAULT_CO_9991))))
            .andExpect(jsonPath("$.[*].co9992").value(hasItem(sameNumber(DEFAULT_CO_9992))))
            .andExpect(jsonPath("$.[*].co9993").value(hasItem(sameNumber(DEFAULT_CO_9993))))
            .andExpect(jsonPath("$.[*].co9994").value(hasItem(sameNumber(DEFAULT_CO_9994))))
            .andExpect(jsonPath("$.[*].co9995").value(hasItem(sameNumber(DEFAULT_CO_9995))))
            .andExpect(jsonPath("$.[*].co9998").value(hasItem(sameNumber(DEFAULT_CO_9998))))
            .andExpect(jsonPath("$.[*].im9007").value(hasItem(sameNumber(DEFAULT_IM_9007))))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].co9999").value(hasItem(sameNumber(DEFAULT_CO_9999))))
            .andExpect(jsonPath("$.[*].cm9008").value(hasItem(sameNumber(DEFAULT_CM_9008))))
            .andExpect(jsonPath("$.[*].cm9009").value(hasItem(sameNumber(DEFAULT_CM_9009))))
            .andExpect(jsonPath("$.[*].co99910").value(hasItem(sameNumber(DEFAULT_CO_99910))))
            .andExpect(jsonPath("$.[*].checkSign").value(hasItem(DEFAULT_CHECK_SIGN)))
            .andExpect(jsonPath("$.[*].classPb").value(hasItem(DEFAULT_CLASS_PB)))
            .andExpect(jsonPath("$.[*].ck").value(hasItem(sameNumber(DEFAULT_CK))))
            .andExpect(jsonPath("$.[*].dk").value(hasItem(sameNumber(DEFAULT_DK))))
            .andExpect(jsonPath("$.[*].sjrq").value(hasItem(DEFAULT_SJRQ.toString())))
            .andExpect(jsonPath("$.[*].qsjrq").value(hasItem(DEFAULT_QSJRQ.toString())))
            .andExpect(jsonPath("$.[*].byje").value(hasItem(sameNumber(DEFAULT_BYJE))))
            .andExpect(jsonPath("$.[*].xfcw").value(hasItem(DEFAULT_XFCW)))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())))
            .andExpect(jsonPath("$.[*].co99912").value(hasItem(sameNumber(DEFAULT_CO_99912))))
            .andExpect(jsonPath("$.[*].xj").value(hasItem(sameNumber(DEFAULT_XJ))))
            .andExpect(jsonPath("$.[*].classname").value(hasItem(DEFAULT_CLASSNAME)))
            .andExpect(jsonPath("$.[*].co9010").value(hasItem(sameNumber(DEFAULT_CO_9010))))
            .andExpect(jsonPath("$.[*].co9012").value(hasItem(sameNumber(DEFAULT_CO_9012))))
            .andExpect(jsonPath("$.[*].co9013").value(hasItem(sameNumber(DEFAULT_CO_9013))))
            .andExpect(jsonPath("$.[*].co9014").value(hasItem(sameNumber(DEFAULT_CO_9014))))
            .andExpect(jsonPath("$.[*].co99915").value(hasItem(sameNumber(DEFAULT_CO_99915))))
            .andExpect(jsonPath("$.[*].hyxj").value(hasItem(sameNumber(DEFAULT_HYXJ))))
            .andExpect(jsonPath("$.[*].hysk").value(hasItem(sameNumber(DEFAULT_HYSK))))
            .andExpect(jsonPath("$.[*].hyqt").value(hasItem(sameNumber(DEFAULT_HYQT))))
            .andExpect(jsonPath("$.[*].hkxj").value(hasItem(sameNumber(DEFAULT_HKXJ))))
            .andExpect(jsonPath("$.[*].hksk").value(hasItem(sameNumber(DEFAULT_HKSK))))
            .andExpect(jsonPath("$.[*].hkqt").value(hasItem(sameNumber(DEFAULT_HKQT))))
            .andExpect(jsonPath("$.[*].qtwt").value(hasItem(sameNumber(DEFAULT_QTWT))))
            .andExpect(jsonPath("$.[*].qtysq").value(hasItem(sameNumber(DEFAULT_QTYSQ))))
            .andExpect(jsonPath("$.[*].bbysj").value(hasItem(sameNumber(DEFAULT_BBYSJ))))
            .andExpect(jsonPath("$.[*].zfbje").value(hasItem(sameNumber(DEFAULT_ZFBJE))))
            .andExpect(jsonPath("$.[*].wxje").value(hasItem(sameNumber(DEFAULT_WXJE))))
            .andExpect(jsonPath("$.[*].w99920").value(hasItem(sameNumber(DEFAULT_W_99920))))
            .andExpect(jsonPath("$.[*].z99921").value(hasItem(sameNumber(DEFAULT_Z_99921))));
    }

    @Test
    @Transactional
    void getClassRename() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get the classRename
        restClassRenameMockMvc
            .perform(get(ENTITY_API_URL_ID, classRename.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(classRename.getId().intValue()))
            .andExpect(jsonPath("$.dt").value(DEFAULT_DT.toString()))
            .andExpect(jsonPath("$.empn").value(DEFAULT_EMPN))
            .andExpect(jsonPath("$.oldmoney").value(sameNumber(DEFAULT_OLDMONEY)))
            .andExpect(jsonPath("$.getmoney").value(sameNumber(DEFAULT_GETMONEY)))
            .andExpect(jsonPath("$.toup").value(sameNumber(DEFAULT_TOUP)))
            .andExpect(jsonPath("$.downempn").value(DEFAULT_DOWNEMPN))
            .andExpect(jsonPath("$.todown").value(sameNumber(DEFAULT_TODOWN)))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG.intValue()))
            .andExpect(jsonPath("$.old2").value(sameNumber(DEFAULT_OLD_2)))
            .andExpect(jsonPath("$.get2").value(sameNumber(DEFAULT_GET_2)))
            .andExpect(jsonPath("$.toup2").value(sameNumber(DEFAULT_TOUP_2)))
            .andExpect(jsonPath("$.todown2").value(sameNumber(DEFAULT_TODOWN_2)))
            .andExpect(jsonPath("$.upempn2").value(DEFAULT_UPEMPN_2))
            .andExpect(jsonPath("$.im9008").value(sameNumber(DEFAULT_IM_9008)))
            .andExpect(jsonPath("$.im9009").value(sameNumber(DEFAULT_IM_9009)))
            .andExpect(jsonPath("$.co9991").value(sameNumber(DEFAULT_CO_9991)))
            .andExpect(jsonPath("$.co9992").value(sameNumber(DEFAULT_CO_9992)))
            .andExpect(jsonPath("$.co9993").value(sameNumber(DEFAULT_CO_9993)))
            .andExpect(jsonPath("$.co9994").value(sameNumber(DEFAULT_CO_9994)))
            .andExpect(jsonPath("$.co9995").value(sameNumber(DEFAULT_CO_9995)))
            .andExpect(jsonPath("$.co9998").value(sameNumber(DEFAULT_CO_9998)))
            .andExpect(jsonPath("$.im9007").value(sameNumber(DEFAULT_IM_9007)))
            .andExpect(jsonPath("$.gotime").value(DEFAULT_GOTIME.toString()))
            .andExpect(jsonPath("$.co9999").value(sameNumber(DEFAULT_CO_9999)))
            .andExpect(jsonPath("$.cm9008").value(sameNumber(DEFAULT_CM_9008)))
            .andExpect(jsonPath("$.cm9009").value(sameNumber(DEFAULT_CM_9009)))
            .andExpect(jsonPath("$.co99910").value(sameNumber(DEFAULT_CO_99910)))
            .andExpect(jsonPath("$.checkSign").value(DEFAULT_CHECK_SIGN))
            .andExpect(jsonPath("$.classPb").value(DEFAULT_CLASS_PB))
            .andExpect(jsonPath("$.ck").value(sameNumber(DEFAULT_CK)))
            .andExpect(jsonPath("$.dk").value(sameNumber(DEFAULT_DK)))
            .andExpect(jsonPath("$.sjrq").value(DEFAULT_SJRQ.toString()))
            .andExpect(jsonPath("$.qsjrq").value(DEFAULT_QSJRQ.toString()))
            .andExpect(jsonPath("$.byje").value(sameNumber(DEFAULT_BYJE)))
            .andExpect(jsonPath("$.xfcw").value(DEFAULT_XFCW))
            .andExpect(jsonPath("$.hoteldm").value(DEFAULT_HOTELDM))
            .andExpect(jsonPath("$.isnew").value(DEFAULT_ISNEW.intValue()))
            .andExpect(jsonPath("$.co99912").value(sameNumber(DEFAULT_CO_99912)))
            .andExpect(jsonPath("$.xj").value(sameNumber(DEFAULT_XJ)))
            .andExpect(jsonPath("$.classname").value(DEFAULT_CLASSNAME))
            .andExpect(jsonPath("$.co9010").value(sameNumber(DEFAULT_CO_9010)))
            .andExpect(jsonPath("$.co9012").value(sameNumber(DEFAULT_CO_9012)))
            .andExpect(jsonPath("$.co9013").value(sameNumber(DEFAULT_CO_9013)))
            .andExpect(jsonPath("$.co9014").value(sameNumber(DEFAULT_CO_9014)))
            .andExpect(jsonPath("$.co99915").value(sameNumber(DEFAULT_CO_99915)))
            .andExpect(jsonPath("$.hyxj").value(sameNumber(DEFAULT_HYXJ)))
            .andExpect(jsonPath("$.hysk").value(sameNumber(DEFAULT_HYSK)))
            .andExpect(jsonPath("$.hyqt").value(sameNumber(DEFAULT_HYQT)))
            .andExpect(jsonPath("$.hkxj").value(sameNumber(DEFAULT_HKXJ)))
            .andExpect(jsonPath("$.hksk").value(sameNumber(DEFAULT_HKSK)))
            .andExpect(jsonPath("$.hkqt").value(sameNumber(DEFAULT_HKQT)))
            .andExpect(jsonPath("$.qtwt").value(sameNumber(DEFAULT_QTWT)))
            .andExpect(jsonPath("$.qtysq").value(sameNumber(DEFAULT_QTYSQ)))
            .andExpect(jsonPath("$.bbysj").value(sameNumber(DEFAULT_BBYSJ)))
            .andExpect(jsonPath("$.zfbje").value(sameNumber(DEFAULT_ZFBJE)))
            .andExpect(jsonPath("$.wxje").value(sameNumber(DEFAULT_WXJE)))
            .andExpect(jsonPath("$.w99920").value(sameNumber(DEFAULT_W_99920)))
            .andExpect(jsonPath("$.z99921").value(sameNumber(DEFAULT_Z_99921)));
    }

    @Test
    @Transactional
    void getClassRenamesByIdFiltering() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        Long id = classRename.getId();

        defaultClassRenameShouldBeFound("id.equals=" + id);
        defaultClassRenameShouldNotBeFound("id.notEquals=" + id);

        defaultClassRenameShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClassRenameShouldNotBeFound("id.greaterThan=" + id);

        defaultClassRenameShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClassRenameShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDtIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where dt equals to DEFAULT_DT
        defaultClassRenameShouldBeFound("dt.equals=" + DEFAULT_DT);

        // Get all the classRenameList where dt equals to UPDATED_DT
        defaultClassRenameShouldNotBeFound("dt.equals=" + UPDATED_DT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where dt not equals to DEFAULT_DT
        defaultClassRenameShouldNotBeFound("dt.notEquals=" + DEFAULT_DT);

        // Get all the classRenameList where dt not equals to UPDATED_DT
        defaultClassRenameShouldBeFound("dt.notEquals=" + UPDATED_DT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDtIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where dt in DEFAULT_DT or UPDATED_DT
        defaultClassRenameShouldBeFound("dt.in=" + DEFAULT_DT + "," + UPDATED_DT);

        // Get all the classRenameList where dt equals to UPDATED_DT
        defaultClassRenameShouldNotBeFound("dt.in=" + UPDATED_DT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDtIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where dt is not null
        defaultClassRenameShouldBeFound("dt.specified=true");

        // Get all the classRenameList where dt is null
        defaultClassRenameShouldNotBeFound("dt.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByEmpnIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where empn equals to DEFAULT_EMPN
        defaultClassRenameShouldBeFound("empn.equals=" + DEFAULT_EMPN);

        // Get all the classRenameList where empn equals to UPDATED_EMPN
        defaultClassRenameShouldNotBeFound("empn.equals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByEmpnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where empn not equals to DEFAULT_EMPN
        defaultClassRenameShouldNotBeFound("empn.notEquals=" + DEFAULT_EMPN);

        // Get all the classRenameList where empn not equals to UPDATED_EMPN
        defaultClassRenameShouldBeFound("empn.notEquals=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByEmpnIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where empn in DEFAULT_EMPN or UPDATED_EMPN
        defaultClassRenameShouldBeFound("empn.in=" + DEFAULT_EMPN + "," + UPDATED_EMPN);

        // Get all the classRenameList where empn equals to UPDATED_EMPN
        defaultClassRenameShouldNotBeFound("empn.in=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByEmpnIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where empn is not null
        defaultClassRenameShouldBeFound("empn.specified=true");

        // Get all the classRenameList where empn is null
        defaultClassRenameShouldNotBeFound("empn.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByEmpnContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where empn contains DEFAULT_EMPN
        defaultClassRenameShouldBeFound("empn.contains=" + DEFAULT_EMPN);

        // Get all the classRenameList where empn contains UPDATED_EMPN
        defaultClassRenameShouldNotBeFound("empn.contains=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByEmpnNotContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where empn does not contain DEFAULT_EMPN
        defaultClassRenameShouldNotBeFound("empn.doesNotContain=" + DEFAULT_EMPN);

        // Get all the classRenameList where empn does not contain UPDATED_EMPN
        defaultClassRenameShouldBeFound("empn.doesNotContain=" + UPDATED_EMPN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByOldmoneyIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where oldmoney equals to DEFAULT_OLDMONEY
        defaultClassRenameShouldBeFound("oldmoney.equals=" + DEFAULT_OLDMONEY);

        // Get all the classRenameList where oldmoney equals to UPDATED_OLDMONEY
        defaultClassRenameShouldNotBeFound("oldmoney.equals=" + UPDATED_OLDMONEY);
    }

    @Test
    @Transactional
    void getAllClassRenamesByOldmoneyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where oldmoney not equals to DEFAULT_OLDMONEY
        defaultClassRenameShouldNotBeFound("oldmoney.notEquals=" + DEFAULT_OLDMONEY);

        // Get all the classRenameList where oldmoney not equals to UPDATED_OLDMONEY
        defaultClassRenameShouldBeFound("oldmoney.notEquals=" + UPDATED_OLDMONEY);
    }

    @Test
    @Transactional
    void getAllClassRenamesByOldmoneyIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where oldmoney in DEFAULT_OLDMONEY or UPDATED_OLDMONEY
        defaultClassRenameShouldBeFound("oldmoney.in=" + DEFAULT_OLDMONEY + "," + UPDATED_OLDMONEY);

        // Get all the classRenameList where oldmoney equals to UPDATED_OLDMONEY
        defaultClassRenameShouldNotBeFound("oldmoney.in=" + UPDATED_OLDMONEY);
    }

    @Test
    @Transactional
    void getAllClassRenamesByOldmoneyIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where oldmoney is not null
        defaultClassRenameShouldBeFound("oldmoney.specified=true");

        // Get all the classRenameList where oldmoney is null
        defaultClassRenameShouldNotBeFound("oldmoney.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByOldmoneyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where oldmoney is greater than or equal to DEFAULT_OLDMONEY
        defaultClassRenameShouldBeFound("oldmoney.greaterThanOrEqual=" + DEFAULT_OLDMONEY);

        // Get all the classRenameList where oldmoney is greater than or equal to UPDATED_OLDMONEY
        defaultClassRenameShouldNotBeFound("oldmoney.greaterThanOrEqual=" + UPDATED_OLDMONEY);
    }

    @Test
    @Transactional
    void getAllClassRenamesByOldmoneyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where oldmoney is less than or equal to DEFAULT_OLDMONEY
        defaultClassRenameShouldBeFound("oldmoney.lessThanOrEqual=" + DEFAULT_OLDMONEY);

        // Get all the classRenameList where oldmoney is less than or equal to SMALLER_OLDMONEY
        defaultClassRenameShouldNotBeFound("oldmoney.lessThanOrEqual=" + SMALLER_OLDMONEY);
    }

    @Test
    @Transactional
    void getAllClassRenamesByOldmoneyIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where oldmoney is less than DEFAULT_OLDMONEY
        defaultClassRenameShouldNotBeFound("oldmoney.lessThan=" + DEFAULT_OLDMONEY);

        // Get all the classRenameList where oldmoney is less than UPDATED_OLDMONEY
        defaultClassRenameShouldBeFound("oldmoney.lessThan=" + UPDATED_OLDMONEY);
    }

    @Test
    @Transactional
    void getAllClassRenamesByOldmoneyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where oldmoney is greater than DEFAULT_OLDMONEY
        defaultClassRenameShouldNotBeFound("oldmoney.greaterThan=" + DEFAULT_OLDMONEY);

        // Get all the classRenameList where oldmoney is greater than SMALLER_OLDMONEY
        defaultClassRenameShouldBeFound("oldmoney.greaterThan=" + SMALLER_OLDMONEY);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGetmoneyIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where getmoney equals to DEFAULT_GETMONEY
        defaultClassRenameShouldBeFound("getmoney.equals=" + DEFAULT_GETMONEY);

        // Get all the classRenameList where getmoney equals to UPDATED_GETMONEY
        defaultClassRenameShouldNotBeFound("getmoney.equals=" + UPDATED_GETMONEY);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGetmoneyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where getmoney not equals to DEFAULT_GETMONEY
        defaultClassRenameShouldNotBeFound("getmoney.notEquals=" + DEFAULT_GETMONEY);

        // Get all the classRenameList where getmoney not equals to UPDATED_GETMONEY
        defaultClassRenameShouldBeFound("getmoney.notEquals=" + UPDATED_GETMONEY);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGetmoneyIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where getmoney in DEFAULT_GETMONEY or UPDATED_GETMONEY
        defaultClassRenameShouldBeFound("getmoney.in=" + DEFAULT_GETMONEY + "," + UPDATED_GETMONEY);

        // Get all the classRenameList where getmoney equals to UPDATED_GETMONEY
        defaultClassRenameShouldNotBeFound("getmoney.in=" + UPDATED_GETMONEY);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGetmoneyIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where getmoney is not null
        defaultClassRenameShouldBeFound("getmoney.specified=true");

        // Get all the classRenameList where getmoney is null
        defaultClassRenameShouldNotBeFound("getmoney.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByGetmoneyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where getmoney is greater than or equal to DEFAULT_GETMONEY
        defaultClassRenameShouldBeFound("getmoney.greaterThanOrEqual=" + DEFAULT_GETMONEY);

        // Get all the classRenameList where getmoney is greater than or equal to UPDATED_GETMONEY
        defaultClassRenameShouldNotBeFound("getmoney.greaterThanOrEqual=" + UPDATED_GETMONEY);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGetmoneyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where getmoney is less than or equal to DEFAULT_GETMONEY
        defaultClassRenameShouldBeFound("getmoney.lessThanOrEqual=" + DEFAULT_GETMONEY);

        // Get all the classRenameList where getmoney is less than or equal to SMALLER_GETMONEY
        defaultClassRenameShouldNotBeFound("getmoney.lessThanOrEqual=" + SMALLER_GETMONEY);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGetmoneyIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where getmoney is less than DEFAULT_GETMONEY
        defaultClassRenameShouldNotBeFound("getmoney.lessThan=" + DEFAULT_GETMONEY);

        // Get all the classRenameList where getmoney is less than UPDATED_GETMONEY
        defaultClassRenameShouldBeFound("getmoney.lessThan=" + UPDATED_GETMONEY);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGetmoneyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where getmoney is greater than DEFAULT_GETMONEY
        defaultClassRenameShouldNotBeFound("getmoney.greaterThan=" + DEFAULT_GETMONEY);

        // Get all the classRenameList where getmoney is greater than SMALLER_GETMONEY
        defaultClassRenameShouldBeFound("getmoney.greaterThan=" + SMALLER_GETMONEY);
    }

    @Test
    @Transactional
    void getAllClassRenamesByToupIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup equals to DEFAULT_TOUP
        defaultClassRenameShouldBeFound("toup.equals=" + DEFAULT_TOUP);

        // Get all the classRenameList where toup equals to UPDATED_TOUP
        defaultClassRenameShouldNotBeFound("toup.equals=" + UPDATED_TOUP);
    }

    @Test
    @Transactional
    void getAllClassRenamesByToupIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup not equals to DEFAULT_TOUP
        defaultClassRenameShouldNotBeFound("toup.notEquals=" + DEFAULT_TOUP);

        // Get all the classRenameList where toup not equals to UPDATED_TOUP
        defaultClassRenameShouldBeFound("toup.notEquals=" + UPDATED_TOUP);
    }

    @Test
    @Transactional
    void getAllClassRenamesByToupIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup in DEFAULT_TOUP or UPDATED_TOUP
        defaultClassRenameShouldBeFound("toup.in=" + DEFAULT_TOUP + "," + UPDATED_TOUP);

        // Get all the classRenameList where toup equals to UPDATED_TOUP
        defaultClassRenameShouldNotBeFound("toup.in=" + UPDATED_TOUP);
    }

    @Test
    @Transactional
    void getAllClassRenamesByToupIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup is not null
        defaultClassRenameShouldBeFound("toup.specified=true");

        // Get all the classRenameList where toup is null
        defaultClassRenameShouldNotBeFound("toup.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByToupIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup is greater than or equal to DEFAULT_TOUP
        defaultClassRenameShouldBeFound("toup.greaterThanOrEqual=" + DEFAULT_TOUP);

        // Get all the classRenameList where toup is greater than or equal to UPDATED_TOUP
        defaultClassRenameShouldNotBeFound("toup.greaterThanOrEqual=" + UPDATED_TOUP);
    }

    @Test
    @Transactional
    void getAllClassRenamesByToupIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup is less than or equal to DEFAULT_TOUP
        defaultClassRenameShouldBeFound("toup.lessThanOrEqual=" + DEFAULT_TOUP);

        // Get all the classRenameList where toup is less than or equal to SMALLER_TOUP
        defaultClassRenameShouldNotBeFound("toup.lessThanOrEqual=" + SMALLER_TOUP);
    }

    @Test
    @Transactional
    void getAllClassRenamesByToupIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup is less than DEFAULT_TOUP
        defaultClassRenameShouldNotBeFound("toup.lessThan=" + DEFAULT_TOUP);

        // Get all the classRenameList where toup is less than UPDATED_TOUP
        defaultClassRenameShouldBeFound("toup.lessThan=" + UPDATED_TOUP);
    }

    @Test
    @Transactional
    void getAllClassRenamesByToupIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup is greater than DEFAULT_TOUP
        defaultClassRenameShouldNotBeFound("toup.greaterThan=" + DEFAULT_TOUP);

        // Get all the classRenameList where toup is greater than SMALLER_TOUP
        defaultClassRenameShouldBeFound("toup.greaterThan=" + SMALLER_TOUP);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDownempnIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where downempn equals to DEFAULT_DOWNEMPN
        defaultClassRenameShouldBeFound("downempn.equals=" + DEFAULT_DOWNEMPN);

        // Get all the classRenameList where downempn equals to UPDATED_DOWNEMPN
        defaultClassRenameShouldNotBeFound("downempn.equals=" + UPDATED_DOWNEMPN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDownempnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where downempn not equals to DEFAULT_DOWNEMPN
        defaultClassRenameShouldNotBeFound("downempn.notEquals=" + DEFAULT_DOWNEMPN);

        // Get all the classRenameList where downempn not equals to UPDATED_DOWNEMPN
        defaultClassRenameShouldBeFound("downempn.notEquals=" + UPDATED_DOWNEMPN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDownempnIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where downempn in DEFAULT_DOWNEMPN or UPDATED_DOWNEMPN
        defaultClassRenameShouldBeFound("downempn.in=" + DEFAULT_DOWNEMPN + "," + UPDATED_DOWNEMPN);

        // Get all the classRenameList where downempn equals to UPDATED_DOWNEMPN
        defaultClassRenameShouldNotBeFound("downempn.in=" + UPDATED_DOWNEMPN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDownempnIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where downempn is not null
        defaultClassRenameShouldBeFound("downempn.specified=true");

        // Get all the classRenameList where downempn is null
        defaultClassRenameShouldNotBeFound("downempn.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByDownempnContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where downempn contains DEFAULT_DOWNEMPN
        defaultClassRenameShouldBeFound("downempn.contains=" + DEFAULT_DOWNEMPN);

        // Get all the classRenameList where downempn contains UPDATED_DOWNEMPN
        defaultClassRenameShouldNotBeFound("downempn.contains=" + UPDATED_DOWNEMPN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDownempnNotContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where downempn does not contain DEFAULT_DOWNEMPN
        defaultClassRenameShouldNotBeFound("downempn.doesNotContain=" + DEFAULT_DOWNEMPN);

        // Get all the classRenameList where downempn does not contain UPDATED_DOWNEMPN
        defaultClassRenameShouldBeFound("downempn.doesNotContain=" + UPDATED_DOWNEMPN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodownIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown equals to DEFAULT_TODOWN
        defaultClassRenameShouldBeFound("todown.equals=" + DEFAULT_TODOWN);

        // Get all the classRenameList where todown equals to UPDATED_TODOWN
        defaultClassRenameShouldNotBeFound("todown.equals=" + UPDATED_TODOWN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodownIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown not equals to DEFAULT_TODOWN
        defaultClassRenameShouldNotBeFound("todown.notEquals=" + DEFAULT_TODOWN);

        // Get all the classRenameList where todown not equals to UPDATED_TODOWN
        defaultClassRenameShouldBeFound("todown.notEquals=" + UPDATED_TODOWN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodownIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown in DEFAULT_TODOWN or UPDATED_TODOWN
        defaultClassRenameShouldBeFound("todown.in=" + DEFAULT_TODOWN + "," + UPDATED_TODOWN);

        // Get all the classRenameList where todown equals to UPDATED_TODOWN
        defaultClassRenameShouldNotBeFound("todown.in=" + UPDATED_TODOWN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodownIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown is not null
        defaultClassRenameShouldBeFound("todown.specified=true");

        // Get all the classRenameList where todown is null
        defaultClassRenameShouldNotBeFound("todown.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodownIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown is greater than or equal to DEFAULT_TODOWN
        defaultClassRenameShouldBeFound("todown.greaterThanOrEqual=" + DEFAULT_TODOWN);

        // Get all the classRenameList where todown is greater than or equal to UPDATED_TODOWN
        defaultClassRenameShouldNotBeFound("todown.greaterThanOrEqual=" + UPDATED_TODOWN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodownIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown is less than or equal to DEFAULT_TODOWN
        defaultClassRenameShouldBeFound("todown.lessThanOrEqual=" + DEFAULT_TODOWN);

        // Get all the classRenameList where todown is less than or equal to SMALLER_TODOWN
        defaultClassRenameShouldNotBeFound("todown.lessThanOrEqual=" + SMALLER_TODOWN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodownIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown is less than DEFAULT_TODOWN
        defaultClassRenameShouldNotBeFound("todown.lessThan=" + DEFAULT_TODOWN);

        // Get all the classRenameList where todown is less than UPDATED_TODOWN
        defaultClassRenameShouldBeFound("todown.lessThan=" + UPDATED_TODOWN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodownIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown is greater than DEFAULT_TODOWN
        defaultClassRenameShouldNotBeFound("todown.greaterThan=" + DEFAULT_TODOWN);

        // Get all the classRenameList where todown is greater than SMALLER_TODOWN
        defaultClassRenameShouldBeFound("todown.greaterThan=" + SMALLER_TODOWN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where flag equals to DEFAULT_FLAG
        defaultClassRenameShouldBeFound("flag.equals=" + DEFAULT_FLAG);

        // Get all the classRenameList where flag equals to UPDATED_FLAG
        defaultClassRenameShouldNotBeFound("flag.equals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllClassRenamesByFlagIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where flag not equals to DEFAULT_FLAG
        defaultClassRenameShouldNotBeFound("flag.notEquals=" + DEFAULT_FLAG);

        // Get all the classRenameList where flag not equals to UPDATED_FLAG
        defaultClassRenameShouldBeFound("flag.notEquals=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllClassRenamesByFlagIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where flag in DEFAULT_FLAG or UPDATED_FLAG
        defaultClassRenameShouldBeFound("flag.in=" + DEFAULT_FLAG + "," + UPDATED_FLAG);

        // Get all the classRenameList where flag equals to UPDATED_FLAG
        defaultClassRenameShouldNotBeFound("flag.in=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllClassRenamesByFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where flag is not null
        defaultClassRenameShouldBeFound("flag.specified=true");

        // Get all the classRenameList where flag is null
        defaultClassRenameShouldNotBeFound("flag.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByFlagIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where flag is greater than or equal to DEFAULT_FLAG
        defaultClassRenameShouldBeFound("flag.greaterThanOrEqual=" + DEFAULT_FLAG);

        // Get all the classRenameList where flag is greater than or equal to UPDATED_FLAG
        defaultClassRenameShouldNotBeFound("flag.greaterThanOrEqual=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllClassRenamesByFlagIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where flag is less than or equal to DEFAULT_FLAG
        defaultClassRenameShouldBeFound("flag.lessThanOrEqual=" + DEFAULT_FLAG);

        // Get all the classRenameList where flag is less than or equal to SMALLER_FLAG
        defaultClassRenameShouldNotBeFound("flag.lessThanOrEqual=" + SMALLER_FLAG);
    }

    @Test
    @Transactional
    void getAllClassRenamesByFlagIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where flag is less than DEFAULT_FLAG
        defaultClassRenameShouldNotBeFound("flag.lessThan=" + DEFAULT_FLAG);

        // Get all the classRenameList where flag is less than UPDATED_FLAG
        defaultClassRenameShouldBeFound("flag.lessThan=" + UPDATED_FLAG);
    }

    @Test
    @Transactional
    void getAllClassRenamesByFlagIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where flag is greater than DEFAULT_FLAG
        defaultClassRenameShouldNotBeFound("flag.greaterThan=" + DEFAULT_FLAG);

        // Get all the classRenameList where flag is greater than SMALLER_FLAG
        defaultClassRenameShouldBeFound("flag.greaterThan=" + SMALLER_FLAG);
    }

    @Test
    @Transactional
    void getAllClassRenamesByOld2IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where old2 equals to DEFAULT_OLD_2
        defaultClassRenameShouldBeFound("old2.equals=" + DEFAULT_OLD_2);

        // Get all the classRenameList where old2 equals to UPDATED_OLD_2
        defaultClassRenameShouldNotBeFound("old2.equals=" + UPDATED_OLD_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByOld2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where old2 not equals to DEFAULT_OLD_2
        defaultClassRenameShouldNotBeFound("old2.notEquals=" + DEFAULT_OLD_2);

        // Get all the classRenameList where old2 not equals to UPDATED_OLD_2
        defaultClassRenameShouldBeFound("old2.notEquals=" + UPDATED_OLD_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByOld2IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where old2 in DEFAULT_OLD_2 or UPDATED_OLD_2
        defaultClassRenameShouldBeFound("old2.in=" + DEFAULT_OLD_2 + "," + UPDATED_OLD_2);

        // Get all the classRenameList where old2 equals to UPDATED_OLD_2
        defaultClassRenameShouldNotBeFound("old2.in=" + UPDATED_OLD_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByOld2IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where old2 is not null
        defaultClassRenameShouldBeFound("old2.specified=true");

        // Get all the classRenameList where old2 is null
        defaultClassRenameShouldNotBeFound("old2.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByOld2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where old2 is greater than or equal to DEFAULT_OLD_2
        defaultClassRenameShouldBeFound("old2.greaterThanOrEqual=" + DEFAULT_OLD_2);

        // Get all the classRenameList where old2 is greater than or equal to UPDATED_OLD_2
        defaultClassRenameShouldNotBeFound("old2.greaterThanOrEqual=" + UPDATED_OLD_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByOld2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where old2 is less than or equal to DEFAULT_OLD_2
        defaultClassRenameShouldBeFound("old2.lessThanOrEqual=" + DEFAULT_OLD_2);

        // Get all the classRenameList where old2 is less than or equal to SMALLER_OLD_2
        defaultClassRenameShouldNotBeFound("old2.lessThanOrEqual=" + SMALLER_OLD_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByOld2IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where old2 is less than DEFAULT_OLD_2
        defaultClassRenameShouldNotBeFound("old2.lessThan=" + DEFAULT_OLD_2);

        // Get all the classRenameList where old2 is less than UPDATED_OLD_2
        defaultClassRenameShouldBeFound("old2.lessThan=" + UPDATED_OLD_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByOld2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where old2 is greater than DEFAULT_OLD_2
        defaultClassRenameShouldNotBeFound("old2.greaterThan=" + DEFAULT_OLD_2);

        // Get all the classRenameList where old2 is greater than SMALLER_OLD_2
        defaultClassRenameShouldBeFound("old2.greaterThan=" + SMALLER_OLD_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGet2IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where get2 equals to DEFAULT_GET_2
        defaultClassRenameShouldBeFound("get2.equals=" + DEFAULT_GET_2);

        // Get all the classRenameList where get2 equals to UPDATED_GET_2
        defaultClassRenameShouldNotBeFound("get2.equals=" + UPDATED_GET_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGet2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where get2 not equals to DEFAULT_GET_2
        defaultClassRenameShouldNotBeFound("get2.notEquals=" + DEFAULT_GET_2);

        // Get all the classRenameList where get2 not equals to UPDATED_GET_2
        defaultClassRenameShouldBeFound("get2.notEquals=" + UPDATED_GET_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGet2IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where get2 in DEFAULT_GET_2 or UPDATED_GET_2
        defaultClassRenameShouldBeFound("get2.in=" + DEFAULT_GET_2 + "," + UPDATED_GET_2);

        // Get all the classRenameList where get2 equals to UPDATED_GET_2
        defaultClassRenameShouldNotBeFound("get2.in=" + UPDATED_GET_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGet2IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where get2 is not null
        defaultClassRenameShouldBeFound("get2.specified=true");

        // Get all the classRenameList where get2 is null
        defaultClassRenameShouldNotBeFound("get2.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByGet2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where get2 is greater than or equal to DEFAULT_GET_2
        defaultClassRenameShouldBeFound("get2.greaterThanOrEqual=" + DEFAULT_GET_2);

        // Get all the classRenameList where get2 is greater than or equal to UPDATED_GET_2
        defaultClassRenameShouldNotBeFound("get2.greaterThanOrEqual=" + UPDATED_GET_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGet2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where get2 is less than or equal to DEFAULT_GET_2
        defaultClassRenameShouldBeFound("get2.lessThanOrEqual=" + DEFAULT_GET_2);

        // Get all the classRenameList where get2 is less than or equal to SMALLER_GET_2
        defaultClassRenameShouldNotBeFound("get2.lessThanOrEqual=" + SMALLER_GET_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGet2IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where get2 is less than DEFAULT_GET_2
        defaultClassRenameShouldNotBeFound("get2.lessThan=" + DEFAULT_GET_2);

        // Get all the classRenameList where get2 is less than UPDATED_GET_2
        defaultClassRenameShouldBeFound("get2.lessThan=" + UPDATED_GET_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGet2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where get2 is greater than DEFAULT_GET_2
        defaultClassRenameShouldNotBeFound("get2.greaterThan=" + DEFAULT_GET_2);

        // Get all the classRenameList where get2 is greater than SMALLER_GET_2
        defaultClassRenameShouldBeFound("get2.greaterThan=" + SMALLER_GET_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByToup2IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup2 equals to DEFAULT_TOUP_2
        defaultClassRenameShouldBeFound("toup2.equals=" + DEFAULT_TOUP_2);

        // Get all the classRenameList where toup2 equals to UPDATED_TOUP_2
        defaultClassRenameShouldNotBeFound("toup2.equals=" + UPDATED_TOUP_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByToup2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup2 not equals to DEFAULT_TOUP_2
        defaultClassRenameShouldNotBeFound("toup2.notEquals=" + DEFAULT_TOUP_2);

        // Get all the classRenameList where toup2 not equals to UPDATED_TOUP_2
        defaultClassRenameShouldBeFound("toup2.notEquals=" + UPDATED_TOUP_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByToup2IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup2 in DEFAULT_TOUP_2 or UPDATED_TOUP_2
        defaultClassRenameShouldBeFound("toup2.in=" + DEFAULT_TOUP_2 + "," + UPDATED_TOUP_2);

        // Get all the classRenameList where toup2 equals to UPDATED_TOUP_2
        defaultClassRenameShouldNotBeFound("toup2.in=" + UPDATED_TOUP_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByToup2IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup2 is not null
        defaultClassRenameShouldBeFound("toup2.specified=true");

        // Get all the classRenameList where toup2 is null
        defaultClassRenameShouldNotBeFound("toup2.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByToup2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup2 is greater than or equal to DEFAULT_TOUP_2
        defaultClassRenameShouldBeFound("toup2.greaterThanOrEqual=" + DEFAULT_TOUP_2);

        // Get all the classRenameList where toup2 is greater than or equal to UPDATED_TOUP_2
        defaultClassRenameShouldNotBeFound("toup2.greaterThanOrEqual=" + UPDATED_TOUP_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByToup2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup2 is less than or equal to DEFAULT_TOUP_2
        defaultClassRenameShouldBeFound("toup2.lessThanOrEqual=" + DEFAULT_TOUP_2);

        // Get all the classRenameList where toup2 is less than or equal to SMALLER_TOUP_2
        defaultClassRenameShouldNotBeFound("toup2.lessThanOrEqual=" + SMALLER_TOUP_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByToup2IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup2 is less than DEFAULT_TOUP_2
        defaultClassRenameShouldNotBeFound("toup2.lessThan=" + DEFAULT_TOUP_2);

        // Get all the classRenameList where toup2 is less than UPDATED_TOUP_2
        defaultClassRenameShouldBeFound("toup2.lessThan=" + UPDATED_TOUP_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByToup2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where toup2 is greater than DEFAULT_TOUP_2
        defaultClassRenameShouldNotBeFound("toup2.greaterThan=" + DEFAULT_TOUP_2);

        // Get all the classRenameList where toup2 is greater than SMALLER_TOUP_2
        defaultClassRenameShouldBeFound("toup2.greaterThan=" + SMALLER_TOUP_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodown2IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown2 equals to DEFAULT_TODOWN_2
        defaultClassRenameShouldBeFound("todown2.equals=" + DEFAULT_TODOWN_2);

        // Get all the classRenameList where todown2 equals to UPDATED_TODOWN_2
        defaultClassRenameShouldNotBeFound("todown2.equals=" + UPDATED_TODOWN_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodown2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown2 not equals to DEFAULT_TODOWN_2
        defaultClassRenameShouldNotBeFound("todown2.notEquals=" + DEFAULT_TODOWN_2);

        // Get all the classRenameList where todown2 not equals to UPDATED_TODOWN_2
        defaultClassRenameShouldBeFound("todown2.notEquals=" + UPDATED_TODOWN_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodown2IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown2 in DEFAULT_TODOWN_2 or UPDATED_TODOWN_2
        defaultClassRenameShouldBeFound("todown2.in=" + DEFAULT_TODOWN_2 + "," + UPDATED_TODOWN_2);

        // Get all the classRenameList where todown2 equals to UPDATED_TODOWN_2
        defaultClassRenameShouldNotBeFound("todown2.in=" + UPDATED_TODOWN_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodown2IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown2 is not null
        defaultClassRenameShouldBeFound("todown2.specified=true");

        // Get all the classRenameList where todown2 is null
        defaultClassRenameShouldNotBeFound("todown2.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodown2IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown2 is greater than or equal to DEFAULT_TODOWN_2
        defaultClassRenameShouldBeFound("todown2.greaterThanOrEqual=" + DEFAULT_TODOWN_2);

        // Get all the classRenameList where todown2 is greater than or equal to UPDATED_TODOWN_2
        defaultClassRenameShouldNotBeFound("todown2.greaterThanOrEqual=" + UPDATED_TODOWN_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodown2IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown2 is less than or equal to DEFAULT_TODOWN_2
        defaultClassRenameShouldBeFound("todown2.lessThanOrEqual=" + DEFAULT_TODOWN_2);

        // Get all the classRenameList where todown2 is less than or equal to SMALLER_TODOWN_2
        defaultClassRenameShouldNotBeFound("todown2.lessThanOrEqual=" + SMALLER_TODOWN_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodown2IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown2 is less than DEFAULT_TODOWN_2
        defaultClassRenameShouldNotBeFound("todown2.lessThan=" + DEFAULT_TODOWN_2);

        // Get all the classRenameList where todown2 is less than UPDATED_TODOWN_2
        defaultClassRenameShouldBeFound("todown2.lessThan=" + UPDATED_TODOWN_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByTodown2IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where todown2 is greater than DEFAULT_TODOWN_2
        defaultClassRenameShouldNotBeFound("todown2.greaterThan=" + DEFAULT_TODOWN_2);

        // Get all the classRenameList where todown2 is greater than SMALLER_TODOWN_2
        defaultClassRenameShouldBeFound("todown2.greaterThan=" + SMALLER_TODOWN_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByUpempn2IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where upempn2 equals to DEFAULT_UPEMPN_2
        defaultClassRenameShouldBeFound("upempn2.equals=" + DEFAULT_UPEMPN_2);

        // Get all the classRenameList where upempn2 equals to UPDATED_UPEMPN_2
        defaultClassRenameShouldNotBeFound("upempn2.equals=" + UPDATED_UPEMPN_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByUpempn2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where upempn2 not equals to DEFAULT_UPEMPN_2
        defaultClassRenameShouldNotBeFound("upempn2.notEquals=" + DEFAULT_UPEMPN_2);

        // Get all the classRenameList where upempn2 not equals to UPDATED_UPEMPN_2
        defaultClassRenameShouldBeFound("upempn2.notEquals=" + UPDATED_UPEMPN_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByUpempn2IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where upempn2 in DEFAULT_UPEMPN_2 or UPDATED_UPEMPN_2
        defaultClassRenameShouldBeFound("upempn2.in=" + DEFAULT_UPEMPN_2 + "," + UPDATED_UPEMPN_2);

        // Get all the classRenameList where upempn2 equals to UPDATED_UPEMPN_2
        defaultClassRenameShouldNotBeFound("upempn2.in=" + UPDATED_UPEMPN_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByUpempn2IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where upempn2 is not null
        defaultClassRenameShouldBeFound("upempn2.specified=true");

        // Get all the classRenameList where upempn2 is null
        defaultClassRenameShouldNotBeFound("upempn2.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByUpempn2ContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where upempn2 contains DEFAULT_UPEMPN_2
        defaultClassRenameShouldBeFound("upempn2.contains=" + DEFAULT_UPEMPN_2);

        // Get all the classRenameList where upempn2 contains UPDATED_UPEMPN_2
        defaultClassRenameShouldNotBeFound("upempn2.contains=" + UPDATED_UPEMPN_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByUpempn2NotContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where upempn2 does not contain DEFAULT_UPEMPN_2
        defaultClassRenameShouldNotBeFound("upempn2.doesNotContain=" + DEFAULT_UPEMPN_2);

        // Get all the classRenameList where upempn2 does not contain UPDATED_UPEMPN_2
        defaultClassRenameShouldBeFound("upempn2.doesNotContain=" + UPDATED_UPEMPN_2);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9008IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9008 equals to DEFAULT_IM_9008
        defaultClassRenameShouldBeFound("im9008.equals=" + DEFAULT_IM_9008);

        // Get all the classRenameList where im9008 equals to UPDATED_IM_9008
        defaultClassRenameShouldNotBeFound("im9008.equals=" + UPDATED_IM_9008);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9008IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9008 not equals to DEFAULT_IM_9008
        defaultClassRenameShouldNotBeFound("im9008.notEquals=" + DEFAULT_IM_9008);

        // Get all the classRenameList where im9008 not equals to UPDATED_IM_9008
        defaultClassRenameShouldBeFound("im9008.notEquals=" + UPDATED_IM_9008);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9008IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9008 in DEFAULT_IM_9008 or UPDATED_IM_9008
        defaultClassRenameShouldBeFound("im9008.in=" + DEFAULT_IM_9008 + "," + UPDATED_IM_9008);

        // Get all the classRenameList where im9008 equals to UPDATED_IM_9008
        defaultClassRenameShouldNotBeFound("im9008.in=" + UPDATED_IM_9008);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9008IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9008 is not null
        defaultClassRenameShouldBeFound("im9008.specified=true");

        // Get all the classRenameList where im9008 is null
        defaultClassRenameShouldNotBeFound("im9008.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9008IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9008 is greater than or equal to DEFAULT_IM_9008
        defaultClassRenameShouldBeFound("im9008.greaterThanOrEqual=" + DEFAULT_IM_9008);

        // Get all the classRenameList where im9008 is greater than or equal to UPDATED_IM_9008
        defaultClassRenameShouldNotBeFound("im9008.greaterThanOrEqual=" + UPDATED_IM_9008);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9008IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9008 is less than or equal to DEFAULT_IM_9008
        defaultClassRenameShouldBeFound("im9008.lessThanOrEqual=" + DEFAULT_IM_9008);

        // Get all the classRenameList where im9008 is less than or equal to SMALLER_IM_9008
        defaultClassRenameShouldNotBeFound("im9008.lessThanOrEqual=" + SMALLER_IM_9008);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9008IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9008 is less than DEFAULT_IM_9008
        defaultClassRenameShouldNotBeFound("im9008.lessThan=" + DEFAULT_IM_9008);

        // Get all the classRenameList where im9008 is less than UPDATED_IM_9008
        defaultClassRenameShouldBeFound("im9008.lessThan=" + UPDATED_IM_9008);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9008IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9008 is greater than DEFAULT_IM_9008
        defaultClassRenameShouldNotBeFound("im9008.greaterThan=" + DEFAULT_IM_9008);

        // Get all the classRenameList where im9008 is greater than SMALLER_IM_9008
        defaultClassRenameShouldBeFound("im9008.greaterThan=" + SMALLER_IM_9008);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9009IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9009 equals to DEFAULT_IM_9009
        defaultClassRenameShouldBeFound("im9009.equals=" + DEFAULT_IM_9009);

        // Get all the classRenameList where im9009 equals to UPDATED_IM_9009
        defaultClassRenameShouldNotBeFound("im9009.equals=" + UPDATED_IM_9009);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9009IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9009 not equals to DEFAULT_IM_9009
        defaultClassRenameShouldNotBeFound("im9009.notEquals=" + DEFAULT_IM_9009);

        // Get all the classRenameList where im9009 not equals to UPDATED_IM_9009
        defaultClassRenameShouldBeFound("im9009.notEquals=" + UPDATED_IM_9009);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9009IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9009 in DEFAULT_IM_9009 or UPDATED_IM_9009
        defaultClassRenameShouldBeFound("im9009.in=" + DEFAULT_IM_9009 + "," + UPDATED_IM_9009);

        // Get all the classRenameList where im9009 equals to UPDATED_IM_9009
        defaultClassRenameShouldNotBeFound("im9009.in=" + UPDATED_IM_9009);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9009IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9009 is not null
        defaultClassRenameShouldBeFound("im9009.specified=true");

        // Get all the classRenameList where im9009 is null
        defaultClassRenameShouldNotBeFound("im9009.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9009IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9009 is greater than or equal to DEFAULT_IM_9009
        defaultClassRenameShouldBeFound("im9009.greaterThanOrEqual=" + DEFAULT_IM_9009);

        // Get all the classRenameList where im9009 is greater than or equal to UPDATED_IM_9009
        defaultClassRenameShouldNotBeFound("im9009.greaterThanOrEqual=" + UPDATED_IM_9009);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9009IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9009 is less than or equal to DEFAULT_IM_9009
        defaultClassRenameShouldBeFound("im9009.lessThanOrEqual=" + DEFAULT_IM_9009);

        // Get all the classRenameList where im9009 is less than or equal to SMALLER_IM_9009
        defaultClassRenameShouldNotBeFound("im9009.lessThanOrEqual=" + SMALLER_IM_9009);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9009IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9009 is less than DEFAULT_IM_9009
        defaultClassRenameShouldNotBeFound("im9009.lessThan=" + DEFAULT_IM_9009);

        // Get all the classRenameList where im9009 is less than UPDATED_IM_9009
        defaultClassRenameShouldBeFound("im9009.lessThan=" + UPDATED_IM_9009);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9009IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9009 is greater than DEFAULT_IM_9009
        defaultClassRenameShouldNotBeFound("im9009.greaterThan=" + DEFAULT_IM_9009);

        // Get all the classRenameList where im9009 is greater than SMALLER_IM_9009
        defaultClassRenameShouldBeFound("im9009.greaterThan=" + SMALLER_IM_9009);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9991IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9991 equals to DEFAULT_CO_9991
        defaultClassRenameShouldBeFound("co9991.equals=" + DEFAULT_CO_9991);

        // Get all the classRenameList where co9991 equals to UPDATED_CO_9991
        defaultClassRenameShouldNotBeFound("co9991.equals=" + UPDATED_CO_9991);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9991IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9991 not equals to DEFAULT_CO_9991
        defaultClassRenameShouldNotBeFound("co9991.notEquals=" + DEFAULT_CO_9991);

        // Get all the classRenameList where co9991 not equals to UPDATED_CO_9991
        defaultClassRenameShouldBeFound("co9991.notEquals=" + UPDATED_CO_9991);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9991IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9991 in DEFAULT_CO_9991 or UPDATED_CO_9991
        defaultClassRenameShouldBeFound("co9991.in=" + DEFAULT_CO_9991 + "," + UPDATED_CO_9991);

        // Get all the classRenameList where co9991 equals to UPDATED_CO_9991
        defaultClassRenameShouldNotBeFound("co9991.in=" + UPDATED_CO_9991);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9991IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9991 is not null
        defaultClassRenameShouldBeFound("co9991.specified=true");

        // Get all the classRenameList where co9991 is null
        defaultClassRenameShouldNotBeFound("co9991.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9991IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9991 is greater than or equal to DEFAULT_CO_9991
        defaultClassRenameShouldBeFound("co9991.greaterThanOrEqual=" + DEFAULT_CO_9991);

        // Get all the classRenameList where co9991 is greater than or equal to UPDATED_CO_9991
        defaultClassRenameShouldNotBeFound("co9991.greaterThanOrEqual=" + UPDATED_CO_9991);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9991IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9991 is less than or equal to DEFAULT_CO_9991
        defaultClassRenameShouldBeFound("co9991.lessThanOrEqual=" + DEFAULT_CO_9991);

        // Get all the classRenameList where co9991 is less than or equal to SMALLER_CO_9991
        defaultClassRenameShouldNotBeFound("co9991.lessThanOrEqual=" + SMALLER_CO_9991);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9991IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9991 is less than DEFAULT_CO_9991
        defaultClassRenameShouldNotBeFound("co9991.lessThan=" + DEFAULT_CO_9991);

        // Get all the classRenameList where co9991 is less than UPDATED_CO_9991
        defaultClassRenameShouldBeFound("co9991.lessThan=" + UPDATED_CO_9991);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9991IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9991 is greater than DEFAULT_CO_9991
        defaultClassRenameShouldNotBeFound("co9991.greaterThan=" + DEFAULT_CO_9991);

        // Get all the classRenameList where co9991 is greater than SMALLER_CO_9991
        defaultClassRenameShouldBeFound("co9991.greaterThan=" + SMALLER_CO_9991);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9992IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9992 equals to DEFAULT_CO_9992
        defaultClassRenameShouldBeFound("co9992.equals=" + DEFAULT_CO_9992);

        // Get all the classRenameList where co9992 equals to UPDATED_CO_9992
        defaultClassRenameShouldNotBeFound("co9992.equals=" + UPDATED_CO_9992);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9992IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9992 not equals to DEFAULT_CO_9992
        defaultClassRenameShouldNotBeFound("co9992.notEquals=" + DEFAULT_CO_9992);

        // Get all the classRenameList where co9992 not equals to UPDATED_CO_9992
        defaultClassRenameShouldBeFound("co9992.notEquals=" + UPDATED_CO_9992);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9992IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9992 in DEFAULT_CO_9992 or UPDATED_CO_9992
        defaultClassRenameShouldBeFound("co9992.in=" + DEFAULT_CO_9992 + "," + UPDATED_CO_9992);

        // Get all the classRenameList where co9992 equals to UPDATED_CO_9992
        defaultClassRenameShouldNotBeFound("co9992.in=" + UPDATED_CO_9992);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9992IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9992 is not null
        defaultClassRenameShouldBeFound("co9992.specified=true");

        // Get all the classRenameList where co9992 is null
        defaultClassRenameShouldNotBeFound("co9992.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9992IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9992 is greater than or equal to DEFAULT_CO_9992
        defaultClassRenameShouldBeFound("co9992.greaterThanOrEqual=" + DEFAULT_CO_9992);

        // Get all the classRenameList where co9992 is greater than or equal to UPDATED_CO_9992
        defaultClassRenameShouldNotBeFound("co9992.greaterThanOrEqual=" + UPDATED_CO_9992);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9992IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9992 is less than or equal to DEFAULT_CO_9992
        defaultClassRenameShouldBeFound("co9992.lessThanOrEqual=" + DEFAULT_CO_9992);

        // Get all the classRenameList where co9992 is less than or equal to SMALLER_CO_9992
        defaultClassRenameShouldNotBeFound("co9992.lessThanOrEqual=" + SMALLER_CO_9992);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9992IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9992 is less than DEFAULT_CO_9992
        defaultClassRenameShouldNotBeFound("co9992.lessThan=" + DEFAULT_CO_9992);

        // Get all the classRenameList where co9992 is less than UPDATED_CO_9992
        defaultClassRenameShouldBeFound("co9992.lessThan=" + UPDATED_CO_9992);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9992IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9992 is greater than DEFAULT_CO_9992
        defaultClassRenameShouldNotBeFound("co9992.greaterThan=" + DEFAULT_CO_9992);

        // Get all the classRenameList where co9992 is greater than SMALLER_CO_9992
        defaultClassRenameShouldBeFound("co9992.greaterThan=" + SMALLER_CO_9992);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9993IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9993 equals to DEFAULT_CO_9993
        defaultClassRenameShouldBeFound("co9993.equals=" + DEFAULT_CO_9993);

        // Get all the classRenameList where co9993 equals to UPDATED_CO_9993
        defaultClassRenameShouldNotBeFound("co9993.equals=" + UPDATED_CO_9993);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9993IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9993 not equals to DEFAULT_CO_9993
        defaultClassRenameShouldNotBeFound("co9993.notEquals=" + DEFAULT_CO_9993);

        // Get all the classRenameList where co9993 not equals to UPDATED_CO_9993
        defaultClassRenameShouldBeFound("co9993.notEquals=" + UPDATED_CO_9993);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9993IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9993 in DEFAULT_CO_9993 or UPDATED_CO_9993
        defaultClassRenameShouldBeFound("co9993.in=" + DEFAULT_CO_9993 + "," + UPDATED_CO_9993);

        // Get all the classRenameList where co9993 equals to UPDATED_CO_9993
        defaultClassRenameShouldNotBeFound("co9993.in=" + UPDATED_CO_9993);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9993IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9993 is not null
        defaultClassRenameShouldBeFound("co9993.specified=true");

        // Get all the classRenameList where co9993 is null
        defaultClassRenameShouldNotBeFound("co9993.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9993IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9993 is greater than or equal to DEFAULT_CO_9993
        defaultClassRenameShouldBeFound("co9993.greaterThanOrEqual=" + DEFAULT_CO_9993);

        // Get all the classRenameList where co9993 is greater than or equal to UPDATED_CO_9993
        defaultClassRenameShouldNotBeFound("co9993.greaterThanOrEqual=" + UPDATED_CO_9993);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9993IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9993 is less than or equal to DEFAULT_CO_9993
        defaultClassRenameShouldBeFound("co9993.lessThanOrEqual=" + DEFAULT_CO_9993);

        // Get all the classRenameList where co9993 is less than or equal to SMALLER_CO_9993
        defaultClassRenameShouldNotBeFound("co9993.lessThanOrEqual=" + SMALLER_CO_9993);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9993IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9993 is less than DEFAULT_CO_9993
        defaultClassRenameShouldNotBeFound("co9993.lessThan=" + DEFAULT_CO_9993);

        // Get all the classRenameList where co9993 is less than UPDATED_CO_9993
        defaultClassRenameShouldBeFound("co9993.lessThan=" + UPDATED_CO_9993);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9993IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9993 is greater than DEFAULT_CO_9993
        defaultClassRenameShouldNotBeFound("co9993.greaterThan=" + DEFAULT_CO_9993);

        // Get all the classRenameList where co9993 is greater than SMALLER_CO_9993
        defaultClassRenameShouldBeFound("co9993.greaterThan=" + SMALLER_CO_9993);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9994IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9994 equals to DEFAULT_CO_9994
        defaultClassRenameShouldBeFound("co9994.equals=" + DEFAULT_CO_9994);

        // Get all the classRenameList where co9994 equals to UPDATED_CO_9994
        defaultClassRenameShouldNotBeFound("co9994.equals=" + UPDATED_CO_9994);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9994IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9994 not equals to DEFAULT_CO_9994
        defaultClassRenameShouldNotBeFound("co9994.notEquals=" + DEFAULT_CO_9994);

        // Get all the classRenameList where co9994 not equals to UPDATED_CO_9994
        defaultClassRenameShouldBeFound("co9994.notEquals=" + UPDATED_CO_9994);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9994IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9994 in DEFAULT_CO_9994 or UPDATED_CO_9994
        defaultClassRenameShouldBeFound("co9994.in=" + DEFAULT_CO_9994 + "," + UPDATED_CO_9994);

        // Get all the classRenameList where co9994 equals to UPDATED_CO_9994
        defaultClassRenameShouldNotBeFound("co9994.in=" + UPDATED_CO_9994);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9994IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9994 is not null
        defaultClassRenameShouldBeFound("co9994.specified=true");

        // Get all the classRenameList where co9994 is null
        defaultClassRenameShouldNotBeFound("co9994.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9994IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9994 is greater than or equal to DEFAULT_CO_9994
        defaultClassRenameShouldBeFound("co9994.greaterThanOrEqual=" + DEFAULT_CO_9994);

        // Get all the classRenameList where co9994 is greater than or equal to UPDATED_CO_9994
        defaultClassRenameShouldNotBeFound("co9994.greaterThanOrEqual=" + UPDATED_CO_9994);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9994IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9994 is less than or equal to DEFAULT_CO_9994
        defaultClassRenameShouldBeFound("co9994.lessThanOrEqual=" + DEFAULT_CO_9994);

        // Get all the classRenameList where co9994 is less than or equal to SMALLER_CO_9994
        defaultClassRenameShouldNotBeFound("co9994.lessThanOrEqual=" + SMALLER_CO_9994);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9994IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9994 is less than DEFAULT_CO_9994
        defaultClassRenameShouldNotBeFound("co9994.lessThan=" + DEFAULT_CO_9994);

        // Get all the classRenameList where co9994 is less than UPDATED_CO_9994
        defaultClassRenameShouldBeFound("co9994.lessThan=" + UPDATED_CO_9994);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9994IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9994 is greater than DEFAULT_CO_9994
        defaultClassRenameShouldNotBeFound("co9994.greaterThan=" + DEFAULT_CO_9994);

        // Get all the classRenameList where co9994 is greater than SMALLER_CO_9994
        defaultClassRenameShouldBeFound("co9994.greaterThan=" + SMALLER_CO_9994);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9995IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9995 equals to DEFAULT_CO_9995
        defaultClassRenameShouldBeFound("co9995.equals=" + DEFAULT_CO_9995);

        // Get all the classRenameList where co9995 equals to UPDATED_CO_9995
        defaultClassRenameShouldNotBeFound("co9995.equals=" + UPDATED_CO_9995);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9995IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9995 not equals to DEFAULT_CO_9995
        defaultClassRenameShouldNotBeFound("co9995.notEquals=" + DEFAULT_CO_9995);

        // Get all the classRenameList where co9995 not equals to UPDATED_CO_9995
        defaultClassRenameShouldBeFound("co9995.notEquals=" + UPDATED_CO_9995);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9995IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9995 in DEFAULT_CO_9995 or UPDATED_CO_9995
        defaultClassRenameShouldBeFound("co9995.in=" + DEFAULT_CO_9995 + "," + UPDATED_CO_9995);

        // Get all the classRenameList where co9995 equals to UPDATED_CO_9995
        defaultClassRenameShouldNotBeFound("co9995.in=" + UPDATED_CO_9995);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9995IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9995 is not null
        defaultClassRenameShouldBeFound("co9995.specified=true");

        // Get all the classRenameList where co9995 is null
        defaultClassRenameShouldNotBeFound("co9995.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9995IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9995 is greater than or equal to DEFAULT_CO_9995
        defaultClassRenameShouldBeFound("co9995.greaterThanOrEqual=" + DEFAULT_CO_9995);

        // Get all the classRenameList where co9995 is greater than or equal to UPDATED_CO_9995
        defaultClassRenameShouldNotBeFound("co9995.greaterThanOrEqual=" + UPDATED_CO_9995);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9995IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9995 is less than or equal to DEFAULT_CO_9995
        defaultClassRenameShouldBeFound("co9995.lessThanOrEqual=" + DEFAULT_CO_9995);

        // Get all the classRenameList where co9995 is less than or equal to SMALLER_CO_9995
        defaultClassRenameShouldNotBeFound("co9995.lessThanOrEqual=" + SMALLER_CO_9995);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9995IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9995 is less than DEFAULT_CO_9995
        defaultClassRenameShouldNotBeFound("co9995.lessThan=" + DEFAULT_CO_9995);

        // Get all the classRenameList where co9995 is less than UPDATED_CO_9995
        defaultClassRenameShouldBeFound("co9995.lessThan=" + UPDATED_CO_9995);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9995IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9995 is greater than DEFAULT_CO_9995
        defaultClassRenameShouldNotBeFound("co9995.greaterThan=" + DEFAULT_CO_9995);

        // Get all the classRenameList where co9995 is greater than SMALLER_CO_9995
        defaultClassRenameShouldBeFound("co9995.greaterThan=" + SMALLER_CO_9995);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9998IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9998 equals to DEFAULT_CO_9998
        defaultClassRenameShouldBeFound("co9998.equals=" + DEFAULT_CO_9998);

        // Get all the classRenameList where co9998 equals to UPDATED_CO_9998
        defaultClassRenameShouldNotBeFound("co9998.equals=" + UPDATED_CO_9998);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9998IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9998 not equals to DEFAULT_CO_9998
        defaultClassRenameShouldNotBeFound("co9998.notEquals=" + DEFAULT_CO_9998);

        // Get all the classRenameList where co9998 not equals to UPDATED_CO_9998
        defaultClassRenameShouldBeFound("co9998.notEquals=" + UPDATED_CO_9998);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9998IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9998 in DEFAULT_CO_9998 or UPDATED_CO_9998
        defaultClassRenameShouldBeFound("co9998.in=" + DEFAULT_CO_9998 + "," + UPDATED_CO_9998);

        // Get all the classRenameList where co9998 equals to UPDATED_CO_9998
        defaultClassRenameShouldNotBeFound("co9998.in=" + UPDATED_CO_9998);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9998IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9998 is not null
        defaultClassRenameShouldBeFound("co9998.specified=true");

        // Get all the classRenameList where co9998 is null
        defaultClassRenameShouldNotBeFound("co9998.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9998IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9998 is greater than or equal to DEFAULT_CO_9998
        defaultClassRenameShouldBeFound("co9998.greaterThanOrEqual=" + DEFAULT_CO_9998);

        // Get all the classRenameList where co9998 is greater than or equal to UPDATED_CO_9998
        defaultClassRenameShouldNotBeFound("co9998.greaterThanOrEqual=" + UPDATED_CO_9998);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9998IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9998 is less than or equal to DEFAULT_CO_9998
        defaultClassRenameShouldBeFound("co9998.lessThanOrEqual=" + DEFAULT_CO_9998);

        // Get all the classRenameList where co9998 is less than or equal to SMALLER_CO_9998
        defaultClassRenameShouldNotBeFound("co9998.lessThanOrEqual=" + SMALLER_CO_9998);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9998IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9998 is less than DEFAULT_CO_9998
        defaultClassRenameShouldNotBeFound("co9998.lessThan=" + DEFAULT_CO_9998);

        // Get all the classRenameList where co9998 is less than UPDATED_CO_9998
        defaultClassRenameShouldBeFound("co9998.lessThan=" + UPDATED_CO_9998);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9998IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9998 is greater than DEFAULT_CO_9998
        defaultClassRenameShouldNotBeFound("co9998.greaterThan=" + DEFAULT_CO_9998);

        // Get all the classRenameList where co9998 is greater than SMALLER_CO_9998
        defaultClassRenameShouldBeFound("co9998.greaterThan=" + SMALLER_CO_9998);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9007IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9007 equals to DEFAULT_IM_9007
        defaultClassRenameShouldBeFound("im9007.equals=" + DEFAULT_IM_9007);

        // Get all the classRenameList where im9007 equals to UPDATED_IM_9007
        defaultClassRenameShouldNotBeFound("im9007.equals=" + UPDATED_IM_9007);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9007IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9007 not equals to DEFAULT_IM_9007
        defaultClassRenameShouldNotBeFound("im9007.notEquals=" + DEFAULT_IM_9007);

        // Get all the classRenameList where im9007 not equals to UPDATED_IM_9007
        defaultClassRenameShouldBeFound("im9007.notEquals=" + UPDATED_IM_9007);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9007IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9007 in DEFAULT_IM_9007 or UPDATED_IM_9007
        defaultClassRenameShouldBeFound("im9007.in=" + DEFAULT_IM_9007 + "," + UPDATED_IM_9007);

        // Get all the classRenameList where im9007 equals to UPDATED_IM_9007
        defaultClassRenameShouldNotBeFound("im9007.in=" + UPDATED_IM_9007);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9007IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9007 is not null
        defaultClassRenameShouldBeFound("im9007.specified=true");

        // Get all the classRenameList where im9007 is null
        defaultClassRenameShouldNotBeFound("im9007.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9007IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9007 is greater than or equal to DEFAULT_IM_9007
        defaultClassRenameShouldBeFound("im9007.greaterThanOrEqual=" + DEFAULT_IM_9007);

        // Get all the classRenameList where im9007 is greater than or equal to UPDATED_IM_9007
        defaultClassRenameShouldNotBeFound("im9007.greaterThanOrEqual=" + UPDATED_IM_9007);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9007IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9007 is less than or equal to DEFAULT_IM_9007
        defaultClassRenameShouldBeFound("im9007.lessThanOrEqual=" + DEFAULT_IM_9007);

        // Get all the classRenameList where im9007 is less than or equal to SMALLER_IM_9007
        defaultClassRenameShouldNotBeFound("im9007.lessThanOrEqual=" + SMALLER_IM_9007);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9007IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9007 is less than DEFAULT_IM_9007
        defaultClassRenameShouldNotBeFound("im9007.lessThan=" + DEFAULT_IM_9007);

        // Get all the classRenameList where im9007 is less than UPDATED_IM_9007
        defaultClassRenameShouldBeFound("im9007.lessThan=" + UPDATED_IM_9007);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIm9007IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where im9007 is greater than DEFAULT_IM_9007
        defaultClassRenameShouldNotBeFound("im9007.greaterThan=" + DEFAULT_IM_9007);

        // Get all the classRenameList where im9007 is greater than SMALLER_IM_9007
        defaultClassRenameShouldBeFound("im9007.greaterThan=" + SMALLER_IM_9007);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGotimeIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where gotime equals to DEFAULT_GOTIME
        defaultClassRenameShouldBeFound("gotime.equals=" + DEFAULT_GOTIME);

        // Get all the classRenameList where gotime equals to UPDATED_GOTIME
        defaultClassRenameShouldNotBeFound("gotime.equals=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGotimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where gotime not equals to DEFAULT_GOTIME
        defaultClassRenameShouldNotBeFound("gotime.notEquals=" + DEFAULT_GOTIME);

        // Get all the classRenameList where gotime not equals to UPDATED_GOTIME
        defaultClassRenameShouldBeFound("gotime.notEquals=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGotimeIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where gotime in DEFAULT_GOTIME or UPDATED_GOTIME
        defaultClassRenameShouldBeFound("gotime.in=" + DEFAULT_GOTIME + "," + UPDATED_GOTIME);

        // Get all the classRenameList where gotime equals to UPDATED_GOTIME
        defaultClassRenameShouldNotBeFound("gotime.in=" + UPDATED_GOTIME);
    }

    @Test
    @Transactional
    void getAllClassRenamesByGotimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where gotime is not null
        defaultClassRenameShouldBeFound("gotime.specified=true");

        // Get all the classRenameList where gotime is null
        defaultClassRenameShouldNotBeFound("gotime.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9999IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9999 equals to DEFAULT_CO_9999
        defaultClassRenameShouldBeFound("co9999.equals=" + DEFAULT_CO_9999);

        // Get all the classRenameList where co9999 equals to UPDATED_CO_9999
        defaultClassRenameShouldNotBeFound("co9999.equals=" + UPDATED_CO_9999);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9999IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9999 not equals to DEFAULT_CO_9999
        defaultClassRenameShouldNotBeFound("co9999.notEquals=" + DEFAULT_CO_9999);

        // Get all the classRenameList where co9999 not equals to UPDATED_CO_9999
        defaultClassRenameShouldBeFound("co9999.notEquals=" + UPDATED_CO_9999);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9999IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9999 in DEFAULT_CO_9999 or UPDATED_CO_9999
        defaultClassRenameShouldBeFound("co9999.in=" + DEFAULT_CO_9999 + "," + UPDATED_CO_9999);

        // Get all the classRenameList where co9999 equals to UPDATED_CO_9999
        defaultClassRenameShouldNotBeFound("co9999.in=" + UPDATED_CO_9999);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9999IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9999 is not null
        defaultClassRenameShouldBeFound("co9999.specified=true");

        // Get all the classRenameList where co9999 is null
        defaultClassRenameShouldNotBeFound("co9999.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9999IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9999 is greater than or equal to DEFAULT_CO_9999
        defaultClassRenameShouldBeFound("co9999.greaterThanOrEqual=" + DEFAULT_CO_9999);

        // Get all the classRenameList where co9999 is greater than or equal to UPDATED_CO_9999
        defaultClassRenameShouldNotBeFound("co9999.greaterThanOrEqual=" + UPDATED_CO_9999);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9999IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9999 is less than or equal to DEFAULT_CO_9999
        defaultClassRenameShouldBeFound("co9999.lessThanOrEqual=" + DEFAULT_CO_9999);

        // Get all the classRenameList where co9999 is less than or equal to SMALLER_CO_9999
        defaultClassRenameShouldNotBeFound("co9999.lessThanOrEqual=" + SMALLER_CO_9999);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9999IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9999 is less than DEFAULT_CO_9999
        defaultClassRenameShouldNotBeFound("co9999.lessThan=" + DEFAULT_CO_9999);

        // Get all the classRenameList where co9999 is less than UPDATED_CO_9999
        defaultClassRenameShouldBeFound("co9999.lessThan=" + UPDATED_CO_9999);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9999IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9999 is greater than DEFAULT_CO_9999
        defaultClassRenameShouldNotBeFound("co9999.greaterThan=" + DEFAULT_CO_9999);

        // Get all the classRenameList where co9999 is greater than SMALLER_CO_9999
        defaultClassRenameShouldBeFound("co9999.greaterThan=" + SMALLER_CO_9999);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9008IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9008 equals to DEFAULT_CM_9008
        defaultClassRenameShouldBeFound("cm9008.equals=" + DEFAULT_CM_9008);

        // Get all the classRenameList where cm9008 equals to UPDATED_CM_9008
        defaultClassRenameShouldNotBeFound("cm9008.equals=" + UPDATED_CM_9008);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9008IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9008 not equals to DEFAULT_CM_9008
        defaultClassRenameShouldNotBeFound("cm9008.notEquals=" + DEFAULT_CM_9008);

        // Get all the classRenameList where cm9008 not equals to UPDATED_CM_9008
        defaultClassRenameShouldBeFound("cm9008.notEquals=" + UPDATED_CM_9008);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9008IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9008 in DEFAULT_CM_9008 or UPDATED_CM_9008
        defaultClassRenameShouldBeFound("cm9008.in=" + DEFAULT_CM_9008 + "," + UPDATED_CM_9008);

        // Get all the classRenameList where cm9008 equals to UPDATED_CM_9008
        defaultClassRenameShouldNotBeFound("cm9008.in=" + UPDATED_CM_9008);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9008IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9008 is not null
        defaultClassRenameShouldBeFound("cm9008.specified=true");

        // Get all the classRenameList where cm9008 is null
        defaultClassRenameShouldNotBeFound("cm9008.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9008IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9008 is greater than or equal to DEFAULT_CM_9008
        defaultClassRenameShouldBeFound("cm9008.greaterThanOrEqual=" + DEFAULT_CM_9008);

        // Get all the classRenameList where cm9008 is greater than or equal to UPDATED_CM_9008
        defaultClassRenameShouldNotBeFound("cm9008.greaterThanOrEqual=" + UPDATED_CM_9008);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9008IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9008 is less than or equal to DEFAULT_CM_9008
        defaultClassRenameShouldBeFound("cm9008.lessThanOrEqual=" + DEFAULT_CM_9008);

        // Get all the classRenameList where cm9008 is less than or equal to SMALLER_CM_9008
        defaultClassRenameShouldNotBeFound("cm9008.lessThanOrEqual=" + SMALLER_CM_9008);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9008IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9008 is less than DEFAULT_CM_9008
        defaultClassRenameShouldNotBeFound("cm9008.lessThan=" + DEFAULT_CM_9008);

        // Get all the classRenameList where cm9008 is less than UPDATED_CM_9008
        defaultClassRenameShouldBeFound("cm9008.lessThan=" + UPDATED_CM_9008);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9008IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9008 is greater than DEFAULT_CM_9008
        defaultClassRenameShouldNotBeFound("cm9008.greaterThan=" + DEFAULT_CM_9008);

        // Get all the classRenameList where cm9008 is greater than SMALLER_CM_9008
        defaultClassRenameShouldBeFound("cm9008.greaterThan=" + SMALLER_CM_9008);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9009IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9009 equals to DEFAULT_CM_9009
        defaultClassRenameShouldBeFound("cm9009.equals=" + DEFAULT_CM_9009);

        // Get all the classRenameList where cm9009 equals to UPDATED_CM_9009
        defaultClassRenameShouldNotBeFound("cm9009.equals=" + UPDATED_CM_9009);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9009IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9009 not equals to DEFAULT_CM_9009
        defaultClassRenameShouldNotBeFound("cm9009.notEquals=" + DEFAULT_CM_9009);

        // Get all the classRenameList where cm9009 not equals to UPDATED_CM_9009
        defaultClassRenameShouldBeFound("cm9009.notEquals=" + UPDATED_CM_9009);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9009IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9009 in DEFAULT_CM_9009 or UPDATED_CM_9009
        defaultClassRenameShouldBeFound("cm9009.in=" + DEFAULT_CM_9009 + "," + UPDATED_CM_9009);

        // Get all the classRenameList where cm9009 equals to UPDATED_CM_9009
        defaultClassRenameShouldNotBeFound("cm9009.in=" + UPDATED_CM_9009);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9009IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9009 is not null
        defaultClassRenameShouldBeFound("cm9009.specified=true");

        // Get all the classRenameList where cm9009 is null
        defaultClassRenameShouldNotBeFound("cm9009.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9009IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9009 is greater than or equal to DEFAULT_CM_9009
        defaultClassRenameShouldBeFound("cm9009.greaterThanOrEqual=" + DEFAULT_CM_9009);

        // Get all the classRenameList where cm9009 is greater than or equal to UPDATED_CM_9009
        defaultClassRenameShouldNotBeFound("cm9009.greaterThanOrEqual=" + UPDATED_CM_9009);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9009IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9009 is less than or equal to DEFAULT_CM_9009
        defaultClassRenameShouldBeFound("cm9009.lessThanOrEqual=" + DEFAULT_CM_9009);

        // Get all the classRenameList where cm9009 is less than or equal to SMALLER_CM_9009
        defaultClassRenameShouldNotBeFound("cm9009.lessThanOrEqual=" + SMALLER_CM_9009);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9009IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9009 is less than DEFAULT_CM_9009
        defaultClassRenameShouldNotBeFound("cm9009.lessThan=" + DEFAULT_CM_9009);

        // Get all the classRenameList where cm9009 is less than UPDATED_CM_9009
        defaultClassRenameShouldBeFound("cm9009.lessThan=" + UPDATED_CM_9009);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCm9009IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where cm9009 is greater than DEFAULT_CM_9009
        defaultClassRenameShouldNotBeFound("cm9009.greaterThan=" + DEFAULT_CM_9009);

        // Get all the classRenameList where cm9009 is greater than SMALLER_CM_9009
        defaultClassRenameShouldBeFound("cm9009.greaterThan=" + SMALLER_CM_9009);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99910IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99910 equals to DEFAULT_CO_99910
        defaultClassRenameShouldBeFound("co99910.equals=" + DEFAULT_CO_99910);

        // Get all the classRenameList where co99910 equals to UPDATED_CO_99910
        defaultClassRenameShouldNotBeFound("co99910.equals=" + UPDATED_CO_99910);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99910IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99910 not equals to DEFAULT_CO_99910
        defaultClassRenameShouldNotBeFound("co99910.notEquals=" + DEFAULT_CO_99910);

        // Get all the classRenameList where co99910 not equals to UPDATED_CO_99910
        defaultClassRenameShouldBeFound("co99910.notEquals=" + UPDATED_CO_99910);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99910IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99910 in DEFAULT_CO_99910 or UPDATED_CO_99910
        defaultClassRenameShouldBeFound("co99910.in=" + DEFAULT_CO_99910 + "," + UPDATED_CO_99910);

        // Get all the classRenameList where co99910 equals to UPDATED_CO_99910
        defaultClassRenameShouldNotBeFound("co99910.in=" + UPDATED_CO_99910);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99910IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99910 is not null
        defaultClassRenameShouldBeFound("co99910.specified=true");

        // Get all the classRenameList where co99910 is null
        defaultClassRenameShouldNotBeFound("co99910.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99910IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99910 is greater than or equal to DEFAULT_CO_99910
        defaultClassRenameShouldBeFound("co99910.greaterThanOrEqual=" + DEFAULT_CO_99910);

        // Get all the classRenameList where co99910 is greater than or equal to UPDATED_CO_99910
        defaultClassRenameShouldNotBeFound("co99910.greaterThanOrEqual=" + UPDATED_CO_99910);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99910IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99910 is less than or equal to DEFAULT_CO_99910
        defaultClassRenameShouldBeFound("co99910.lessThanOrEqual=" + DEFAULT_CO_99910);

        // Get all the classRenameList where co99910 is less than or equal to SMALLER_CO_99910
        defaultClassRenameShouldNotBeFound("co99910.lessThanOrEqual=" + SMALLER_CO_99910);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99910IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99910 is less than DEFAULT_CO_99910
        defaultClassRenameShouldNotBeFound("co99910.lessThan=" + DEFAULT_CO_99910);

        // Get all the classRenameList where co99910 is less than UPDATED_CO_99910
        defaultClassRenameShouldBeFound("co99910.lessThan=" + UPDATED_CO_99910);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99910IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99910 is greater than DEFAULT_CO_99910
        defaultClassRenameShouldNotBeFound("co99910.greaterThan=" + DEFAULT_CO_99910);

        // Get all the classRenameList where co99910 is greater than SMALLER_CO_99910
        defaultClassRenameShouldBeFound("co99910.greaterThan=" + SMALLER_CO_99910);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCheckSignIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where checkSign equals to DEFAULT_CHECK_SIGN
        defaultClassRenameShouldBeFound("checkSign.equals=" + DEFAULT_CHECK_SIGN);

        // Get all the classRenameList where checkSign equals to UPDATED_CHECK_SIGN
        defaultClassRenameShouldNotBeFound("checkSign.equals=" + UPDATED_CHECK_SIGN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCheckSignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where checkSign not equals to DEFAULT_CHECK_SIGN
        defaultClassRenameShouldNotBeFound("checkSign.notEquals=" + DEFAULT_CHECK_SIGN);

        // Get all the classRenameList where checkSign not equals to UPDATED_CHECK_SIGN
        defaultClassRenameShouldBeFound("checkSign.notEquals=" + UPDATED_CHECK_SIGN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCheckSignIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where checkSign in DEFAULT_CHECK_SIGN or UPDATED_CHECK_SIGN
        defaultClassRenameShouldBeFound("checkSign.in=" + DEFAULT_CHECK_SIGN + "," + UPDATED_CHECK_SIGN);

        // Get all the classRenameList where checkSign equals to UPDATED_CHECK_SIGN
        defaultClassRenameShouldNotBeFound("checkSign.in=" + UPDATED_CHECK_SIGN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCheckSignIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where checkSign is not null
        defaultClassRenameShouldBeFound("checkSign.specified=true");

        // Get all the classRenameList where checkSign is null
        defaultClassRenameShouldNotBeFound("checkSign.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCheckSignContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where checkSign contains DEFAULT_CHECK_SIGN
        defaultClassRenameShouldBeFound("checkSign.contains=" + DEFAULT_CHECK_SIGN);

        // Get all the classRenameList where checkSign contains UPDATED_CHECK_SIGN
        defaultClassRenameShouldNotBeFound("checkSign.contains=" + UPDATED_CHECK_SIGN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCheckSignNotContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where checkSign does not contain DEFAULT_CHECK_SIGN
        defaultClassRenameShouldNotBeFound("checkSign.doesNotContain=" + DEFAULT_CHECK_SIGN);

        // Get all the classRenameList where checkSign does not contain UPDATED_CHECK_SIGN
        defaultClassRenameShouldBeFound("checkSign.doesNotContain=" + UPDATED_CHECK_SIGN);
    }

    @Test
    @Transactional
    void getAllClassRenamesByClassPbIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where classPb equals to DEFAULT_CLASS_PB
        defaultClassRenameShouldBeFound("classPb.equals=" + DEFAULT_CLASS_PB);

        // Get all the classRenameList where classPb equals to UPDATED_CLASS_PB
        defaultClassRenameShouldNotBeFound("classPb.equals=" + UPDATED_CLASS_PB);
    }

    @Test
    @Transactional
    void getAllClassRenamesByClassPbIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where classPb not equals to DEFAULT_CLASS_PB
        defaultClassRenameShouldNotBeFound("classPb.notEquals=" + DEFAULT_CLASS_PB);

        // Get all the classRenameList where classPb not equals to UPDATED_CLASS_PB
        defaultClassRenameShouldBeFound("classPb.notEquals=" + UPDATED_CLASS_PB);
    }

    @Test
    @Transactional
    void getAllClassRenamesByClassPbIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where classPb in DEFAULT_CLASS_PB or UPDATED_CLASS_PB
        defaultClassRenameShouldBeFound("classPb.in=" + DEFAULT_CLASS_PB + "," + UPDATED_CLASS_PB);

        // Get all the classRenameList where classPb equals to UPDATED_CLASS_PB
        defaultClassRenameShouldNotBeFound("classPb.in=" + UPDATED_CLASS_PB);
    }

    @Test
    @Transactional
    void getAllClassRenamesByClassPbIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where classPb is not null
        defaultClassRenameShouldBeFound("classPb.specified=true");

        // Get all the classRenameList where classPb is null
        defaultClassRenameShouldNotBeFound("classPb.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByClassPbContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where classPb contains DEFAULT_CLASS_PB
        defaultClassRenameShouldBeFound("classPb.contains=" + DEFAULT_CLASS_PB);

        // Get all the classRenameList where classPb contains UPDATED_CLASS_PB
        defaultClassRenameShouldNotBeFound("classPb.contains=" + UPDATED_CLASS_PB);
    }

    @Test
    @Transactional
    void getAllClassRenamesByClassPbNotContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where classPb does not contain DEFAULT_CLASS_PB
        defaultClassRenameShouldNotBeFound("classPb.doesNotContain=" + DEFAULT_CLASS_PB);

        // Get all the classRenameList where classPb does not contain UPDATED_CLASS_PB
        defaultClassRenameShouldBeFound("classPb.doesNotContain=" + UPDATED_CLASS_PB);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCkIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where ck equals to DEFAULT_CK
        defaultClassRenameShouldBeFound("ck.equals=" + DEFAULT_CK);

        // Get all the classRenameList where ck equals to UPDATED_CK
        defaultClassRenameShouldNotBeFound("ck.equals=" + UPDATED_CK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where ck not equals to DEFAULT_CK
        defaultClassRenameShouldNotBeFound("ck.notEquals=" + DEFAULT_CK);

        // Get all the classRenameList where ck not equals to UPDATED_CK
        defaultClassRenameShouldBeFound("ck.notEquals=" + UPDATED_CK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCkIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where ck in DEFAULT_CK or UPDATED_CK
        defaultClassRenameShouldBeFound("ck.in=" + DEFAULT_CK + "," + UPDATED_CK);

        // Get all the classRenameList where ck equals to UPDATED_CK
        defaultClassRenameShouldNotBeFound("ck.in=" + UPDATED_CK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCkIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where ck is not null
        defaultClassRenameShouldBeFound("ck.specified=true");

        // Get all the classRenameList where ck is null
        defaultClassRenameShouldNotBeFound("ck.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCkIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where ck is greater than or equal to DEFAULT_CK
        defaultClassRenameShouldBeFound("ck.greaterThanOrEqual=" + DEFAULT_CK);

        // Get all the classRenameList where ck is greater than or equal to UPDATED_CK
        defaultClassRenameShouldNotBeFound("ck.greaterThanOrEqual=" + UPDATED_CK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCkIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where ck is less than or equal to DEFAULT_CK
        defaultClassRenameShouldBeFound("ck.lessThanOrEqual=" + DEFAULT_CK);

        // Get all the classRenameList where ck is less than or equal to SMALLER_CK
        defaultClassRenameShouldNotBeFound("ck.lessThanOrEqual=" + SMALLER_CK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCkIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where ck is less than DEFAULT_CK
        defaultClassRenameShouldNotBeFound("ck.lessThan=" + DEFAULT_CK);

        // Get all the classRenameList where ck is less than UPDATED_CK
        defaultClassRenameShouldBeFound("ck.lessThan=" + UPDATED_CK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCkIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where ck is greater than DEFAULT_CK
        defaultClassRenameShouldNotBeFound("ck.greaterThan=" + DEFAULT_CK);

        // Get all the classRenameList where ck is greater than SMALLER_CK
        defaultClassRenameShouldBeFound("ck.greaterThan=" + SMALLER_CK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDkIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where dk equals to DEFAULT_DK
        defaultClassRenameShouldBeFound("dk.equals=" + DEFAULT_DK);

        // Get all the classRenameList where dk equals to UPDATED_DK
        defaultClassRenameShouldNotBeFound("dk.equals=" + UPDATED_DK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDkIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where dk not equals to DEFAULT_DK
        defaultClassRenameShouldNotBeFound("dk.notEquals=" + DEFAULT_DK);

        // Get all the classRenameList where dk not equals to UPDATED_DK
        defaultClassRenameShouldBeFound("dk.notEquals=" + UPDATED_DK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDkIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where dk in DEFAULT_DK or UPDATED_DK
        defaultClassRenameShouldBeFound("dk.in=" + DEFAULT_DK + "," + UPDATED_DK);

        // Get all the classRenameList where dk equals to UPDATED_DK
        defaultClassRenameShouldNotBeFound("dk.in=" + UPDATED_DK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDkIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where dk is not null
        defaultClassRenameShouldBeFound("dk.specified=true");

        // Get all the classRenameList where dk is null
        defaultClassRenameShouldNotBeFound("dk.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByDkIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where dk is greater than or equal to DEFAULT_DK
        defaultClassRenameShouldBeFound("dk.greaterThanOrEqual=" + DEFAULT_DK);

        // Get all the classRenameList where dk is greater than or equal to UPDATED_DK
        defaultClassRenameShouldNotBeFound("dk.greaterThanOrEqual=" + UPDATED_DK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDkIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where dk is less than or equal to DEFAULT_DK
        defaultClassRenameShouldBeFound("dk.lessThanOrEqual=" + DEFAULT_DK);

        // Get all the classRenameList where dk is less than or equal to SMALLER_DK
        defaultClassRenameShouldNotBeFound("dk.lessThanOrEqual=" + SMALLER_DK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDkIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where dk is less than DEFAULT_DK
        defaultClassRenameShouldNotBeFound("dk.lessThan=" + DEFAULT_DK);

        // Get all the classRenameList where dk is less than UPDATED_DK
        defaultClassRenameShouldBeFound("dk.lessThan=" + UPDATED_DK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByDkIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where dk is greater than DEFAULT_DK
        defaultClassRenameShouldNotBeFound("dk.greaterThan=" + DEFAULT_DK);

        // Get all the classRenameList where dk is greater than SMALLER_DK
        defaultClassRenameShouldBeFound("dk.greaterThan=" + SMALLER_DK);
    }

    @Test
    @Transactional
    void getAllClassRenamesBySjrqIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where sjrq equals to DEFAULT_SJRQ
        defaultClassRenameShouldBeFound("sjrq.equals=" + DEFAULT_SJRQ);

        // Get all the classRenameList where sjrq equals to UPDATED_SJRQ
        defaultClassRenameShouldNotBeFound("sjrq.equals=" + UPDATED_SJRQ);
    }

    @Test
    @Transactional
    void getAllClassRenamesBySjrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where sjrq not equals to DEFAULT_SJRQ
        defaultClassRenameShouldNotBeFound("sjrq.notEquals=" + DEFAULT_SJRQ);

        // Get all the classRenameList where sjrq not equals to UPDATED_SJRQ
        defaultClassRenameShouldBeFound("sjrq.notEquals=" + UPDATED_SJRQ);
    }

    @Test
    @Transactional
    void getAllClassRenamesBySjrqIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where sjrq in DEFAULT_SJRQ or UPDATED_SJRQ
        defaultClassRenameShouldBeFound("sjrq.in=" + DEFAULT_SJRQ + "," + UPDATED_SJRQ);

        // Get all the classRenameList where sjrq equals to UPDATED_SJRQ
        defaultClassRenameShouldNotBeFound("sjrq.in=" + UPDATED_SJRQ);
    }

    @Test
    @Transactional
    void getAllClassRenamesBySjrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where sjrq is not null
        defaultClassRenameShouldBeFound("sjrq.specified=true");

        // Get all the classRenameList where sjrq is null
        defaultClassRenameShouldNotBeFound("sjrq.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByQsjrqIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qsjrq equals to DEFAULT_QSJRQ
        defaultClassRenameShouldBeFound("qsjrq.equals=" + DEFAULT_QSJRQ);

        // Get all the classRenameList where qsjrq equals to UPDATED_QSJRQ
        defaultClassRenameShouldNotBeFound("qsjrq.equals=" + UPDATED_QSJRQ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQsjrqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qsjrq not equals to DEFAULT_QSJRQ
        defaultClassRenameShouldNotBeFound("qsjrq.notEquals=" + DEFAULT_QSJRQ);

        // Get all the classRenameList where qsjrq not equals to UPDATED_QSJRQ
        defaultClassRenameShouldBeFound("qsjrq.notEquals=" + UPDATED_QSJRQ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQsjrqIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qsjrq in DEFAULT_QSJRQ or UPDATED_QSJRQ
        defaultClassRenameShouldBeFound("qsjrq.in=" + DEFAULT_QSJRQ + "," + UPDATED_QSJRQ);

        // Get all the classRenameList where qsjrq equals to UPDATED_QSJRQ
        defaultClassRenameShouldNotBeFound("qsjrq.in=" + UPDATED_QSJRQ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQsjrqIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qsjrq is not null
        defaultClassRenameShouldBeFound("qsjrq.specified=true");

        // Get all the classRenameList where qsjrq is null
        defaultClassRenameShouldNotBeFound("qsjrq.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByByjeIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where byje equals to DEFAULT_BYJE
        defaultClassRenameShouldBeFound("byje.equals=" + DEFAULT_BYJE);

        // Get all the classRenameList where byje equals to UPDATED_BYJE
        defaultClassRenameShouldNotBeFound("byje.equals=" + UPDATED_BYJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByByjeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where byje not equals to DEFAULT_BYJE
        defaultClassRenameShouldNotBeFound("byje.notEquals=" + DEFAULT_BYJE);

        // Get all the classRenameList where byje not equals to UPDATED_BYJE
        defaultClassRenameShouldBeFound("byje.notEquals=" + UPDATED_BYJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByByjeIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where byje in DEFAULT_BYJE or UPDATED_BYJE
        defaultClassRenameShouldBeFound("byje.in=" + DEFAULT_BYJE + "," + UPDATED_BYJE);

        // Get all the classRenameList where byje equals to UPDATED_BYJE
        defaultClassRenameShouldNotBeFound("byje.in=" + UPDATED_BYJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByByjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where byje is not null
        defaultClassRenameShouldBeFound("byje.specified=true");

        // Get all the classRenameList where byje is null
        defaultClassRenameShouldNotBeFound("byje.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByByjeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where byje is greater than or equal to DEFAULT_BYJE
        defaultClassRenameShouldBeFound("byje.greaterThanOrEqual=" + DEFAULT_BYJE);

        // Get all the classRenameList where byje is greater than or equal to UPDATED_BYJE
        defaultClassRenameShouldNotBeFound("byje.greaterThanOrEqual=" + UPDATED_BYJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByByjeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where byje is less than or equal to DEFAULT_BYJE
        defaultClassRenameShouldBeFound("byje.lessThanOrEqual=" + DEFAULT_BYJE);

        // Get all the classRenameList where byje is less than or equal to SMALLER_BYJE
        defaultClassRenameShouldNotBeFound("byje.lessThanOrEqual=" + SMALLER_BYJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByByjeIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where byje is less than DEFAULT_BYJE
        defaultClassRenameShouldNotBeFound("byje.lessThan=" + DEFAULT_BYJE);

        // Get all the classRenameList where byje is less than UPDATED_BYJE
        defaultClassRenameShouldBeFound("byje.lessThan=" + UPDATED_BYJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByByjeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where byje is greater than DEFAULT_BYJE
        defaultClassRenameShouldNotBeFound("byje.greaterThan=" + DEFAULT_BYJE);

        // Get all the classRenameList where byje is greater than SMALLER_BYJE
        defaultClassRenameShouldBeFound("byje.greaterThan=" + SMALLER_BYJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByXfcwIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where xfcw equals to DEFAULT_XFCW
        defaultClassRenameShouldBeFound("xfcw.equals=" + DEFAULT_XFCW);

        // Get all the classRenameList where xfcw equals to UPDATED_XFCW
        defaultClassRenameShouldNotBeFound("xfcw.equals=" + UPDATED_XFCW);
    }

    @Test
    @Transactional
    void getAllClassRenamesByXfcwIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where xfcw not equals to DEFAULT_XFCW
        defaultClassRenameShouldNotBeFound("xfcw.notEquals=" + DEFAULT_XFCW);

        // Get all the classRenameList where xfcw not equals to UPDATED_XFCW
        defaultClassRenameShouldBeFound("xfcw.notEquals=" + UPDATED_XFCW);
    }

    @Test
    @Transactional
    void getAllClassRenamesByXfcwIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where xfcw in DEFAULT_XFCW or UPDATED_XFCW
        defaultClassRenameShouldBeFound("xfcw.in=" + DEFAULT_XFCW + "," + UPDATED_XFCW);

        // Get all the classRenameList where xfcw equals to UPDATED_XFCW
        defaultClassRenameShouldNotBeFound("xfcw.in=" + UPDATED_XFCW);
    }

    @Test
    @Transactional
    void getAllClassRenamesByXfcwIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where xfcw is not null
        defaultClassRenameShouldBeFound("xfcw.specified=true");

        // Get all the classRenameList where xfcw is null
        defaultClassRenameShouldNotBeFound("xfcw.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByXfcwContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where xfcw contains DEFAULT_XFCW
        defaultClassRenameShouldBeFound("xfcw.contains=" + DEFAULT_XFCW);

        // Get all the classRenameList where xfcw contains UPDATED_XFCW
        defaultClassRenameShouldNotBeFound("xfcw.contains=" + UPDATED_XFCW);
    }

    @Test
    @Transactional
    void getAllClassRenamesByXfcwNotContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where xfcw does not contain DEFAULT_XFCW
        defaultClassRenameShouldNotBeFound("xfcw.doesNotContain=" + DEFAULT_XFCW);

        // Get all the classRenameList where xfcw does not contain UPDATED_XFCW
        defaultClassRenameShouldBeFound("xfcw.doesNotContain=" + UPDATED_XFCW);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHoteldmIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hoteldm equals to DEFAULT_HOTELDM
        defaultClassRenameShouldBeFound("hoteldm.equals=" + DEFAULT_HOTELDM);

        // Get all the classRenameList where hoteldm equals to UPDATED_HOTELDM
        defaultClassRenameShouldNotBeFound("hoteldm.equals=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHoteldmIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hoteldm not equals to DEFAULT_HOTELDM
        defaultClassRenameShouldNotBeFound("hoteldm.notEquals=" + DEFAULT_HOTELDM);

        // Get all the classRenameList where hoteldm not equals to UPDATED_HOTELDM
        defaultClassRenameShouldBeFound("hoteldm.notEquals=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHoteldmIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hoteldm in DEFAULT_HOTELDM or UPDATED_HOTELDM
        defaultClassRenameShouldBeFound("hoteldm.in=" + DEFAULT_HOTELDM + "," + UPDATED_HOTELDM);

        // Get all the classRenameList where hoteldm equals to UPDATED_HOTELDM
        defaultClassRenameShouldNotBeFound("hoteldm.in=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHoteldmIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hoteldm is not null
        defaultClassRenameShouldBeFound("hoteldm.specified=true");

        // Get all the classRenameList where hoteldm is null
        defaultClassRenameShouldNotBeFound("hoteldm.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByHoteldmContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hoteldm contains DEFAULT_HOTELDM
        defaultClassRenameShouldBeFound("hoteldm.contains=" + DEFAULT_HOTELDM);

        // Get all the classRenameList where hoteldm contains UPDATED_HOTELDM
        defaultClassRenameShouldNotBeFound("hoteldm.contains=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHoteldmNotContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hoteldm does not contain DEFAULT_HOTELDM
        defaultClassRenameShouldNotBeFound("hoteldm.doesNotContain=" + DEFAULT_HOTELDM);

        // Get all the classRenameList where hoteldm does not contain UPDATED_HOTELDM
        defaultClassRenameShouldBeFound("hoteldm.doesNotContain=" + UPDATED_HOTELDM);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIsnewIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where isnew equals to DEFAULT_ISNEW
        defaultClassRenameShouldBeFound("isnew.equals=" + DEFAULT_ISNEW);

        // Get all the classRenameList where isnew equals to UPDATED_ISNEW
        defaultClassRenameShouldNotBeFound("isnew.equals=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIsnewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where isnew not equals to DEFAULT_ISNEW
        defaultClassRenameShouldNotBeFound("isnew.notEquals=" + DEFAULT_ISNEW);

        // Get all the classRenameList where isnew not equals to UPDATED_ISNEW
        defaultClassRenameShouldBeFound("isnew.notEquals=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIsnewIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where isnew in DEFAULT_ISNEW or UPDATED_ISNEW
        defaultClassRenameShouldBeFound("isnew.in=" + DEFAULT_ISNEW + "," + UPDATED_ISNEW);

        // Get all the classRenameList where isnew equals to UPDATED_ISNEW
        defaultClassRenameShouldNotBeFound("isnew.in=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIsnewIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where isnew is not null
        defaultClassRenameShouldBeFound("isnew.specified=true");

        // Get all the classRenameList where isnew is null
        defaultClassRenameShouldNotBeFound("isnew.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByIsnewIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where isnew is greater than or equal to DEFAULT_ISNEW
        defaultClassRenameShouldBeFound("isnew.greaterThanOrEqual=" + DEFAULT_ISNEW);

        // Get all the classRenameList where isnew is greater than or equal to UPDATED_ISNEW
        defaultClassRenameShouldNotBeFound("isnew.greaterThanOrEqual=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIsnewIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where isnew is less than or equal to DEFAULT_ISNEW
        defaultClassRenameShouldBeFound("isnew.lessThanOrEqual=" + DEFAULT_ISNEW);

        // Get all the classRenameList where isnew is less than or equal to SMALLER_ISNEW
        defaultClassRenameShouldNotBeFound("isnew.lessThanOrEqual=" + SMALLER_ISNEW);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIsnewIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where isnew is less than DEFAULT_ISNEW
        defaultClassRenameShouldNotBeFound("isnew.lessThan=" + DEFAULT_ISNEW);

        // Get all the classRenameList where isnew is less than UPDATED_ISNEW
        defaultClassRenameShouldBeFound("isnew.lessThan=" + UPDATED_ISNEW);
    }

    @Test
    @Transactional
    void getAllClassRenamesByIsnewIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where isnew is greater than DEFAULT_ISNEW
        defaultClassRenameShouldNotBeFound("isnew.greaterThan=" + DEFAULT_ISNEW);

        // Get all the classRenameList where isnew is greater than SMALLER_ISNEW
        defaultClassRenameShouldBeFound("isnew.greaterThan=" + SMALLER_ISNEW);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99912IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99912 equals to DEFAULT_CO_99912
        defaultClassRenameShouldBeFound("co99912.equals=" + DEFAULT_CO_99912);

        // Get all the classRenameList where co99912 equals to UPDATED_CO_99912
        defaultClassRenameShouldNotBeFound("co99912.equals=" + UPDATED_CO_99912);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99912IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99912 not equals to DEFAULT_CO_99912
        defaultClassRenameShouldNotBeFound("co99912.notEquals=" + DEFAULT_CO_99912);

        // Get all the classRenameList where co99912 not equals to UPDATED_CO_99912
        defaultClassRenameShouldBeFound("co99912.notEquals=" + UPDATED_CO_99912);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99912IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99912 in DEFAULT_CO_99912 or UPDATED_CO_99912
        defaultClassRenameShouldBeFound("co99912.in=" + DEFAULT_CO_99912 + "," + UPDATED_CO_99912);

        // Get all the classRenameList where co99912 equals to UPDATED_CO_99912
        defaultClassRenameShouldNotBeFound("co99912.in=" + UPDATED_CO_99912);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99912IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99912 is not null
        defaultClassRenameShouldBeFound("co99912.specified=true");

        // Get all the classRenameList where co99912 is null
        defaultClassRenameShouldNotBeFound("co99912.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99912IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99912 is greater than or equal to DEFAULT_CO_99912
        defaultClassRenameShouldBeFound("co99912.greaterThanOrEqual=" + DEFAULT_CO_99912);

        // Get all the classRenameList where co99912 is greater than or equal to UPDATED_CO_99912
        defaultClassRenameShouldNotBeFound("co99912.greaterThanOrEqual=" + UPDATED_CO_99912);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99912IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99912 is less than or equal to DEFAULT_CO_99912
        defaultClassRenameShouldBeFound("co99912.lessThanOrEqual=" + DEFAULT_CO_99912);

        // Get all the classRenameList where co99912 is less than or equal to SMALLER_CO_99912
        defaultClassRenameShouldNotBeFound("co99912.lessThanOrEqual=" + SMALLER_CO_99912);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99912IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99912 is less than DEFAULT_CO_99912
        defaultClassRenameShouldNotBeFound("co99912.lessThan=" + DEFAULT_CO_99912);

        // Get all the classRenameList where co99912 is less than UPDATED_CO_99912
        defaultClassRenameShouldBeFound("co99912.lessThan=" + UPDATED_CO_99912);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99912IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99912 is greater than DEFAULT_CO_99912
        defaultClassRenameShouldNotBeFound("co99912.greaterThan=" + DEFAULT_CO_99912);

        // Get all the classRenameList where co99912 is greater than SMALLER_CO_99912
        defaultClassRenameShouldBeFound("co99912.greaterThan=" + SMALLER_CO_99912);
    }

    @Test
    @Transactional
    void getAllClassRenamesByXjIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where xj equals to DEFAULT_XJ
        defaultClassRenameShouldBeFound("xj.equals=" + DEFAULT_XJ);

        // Get all the classRenameList where xj equals to UPDATED_XJ
        defaultClassRenameShouldNotBeFound("xj.equals=" + UPDATED_XJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByXjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where xj not equals to DEFAULT_XJ
        defaultClassRenameShouldNotBeFound("xj.notEquals=" + DEFAULT_XJ);

        // Get all the classRenameList where xj not equals to UPDATED_XJ
        defaultClassRenameShouldBeFound("xj.notEquals=" + UPDATED_XJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByXjIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where xj in DEFAULT_XJ or UPDATED_XJ
        defaultClassRenameShouldBeFound("xj.in=" + DEFAULT_XJ + "," + UPDATED_XJ);

        // Get all the classRenameList where xj equals to UPDATED_XJ
        defaultClassRenameShouldNotBeFound("xj.in=" + UPDATED_XJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByXjIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where xj is not null
        defaultClassRenameShouldBeFound("xj.specified=true");

        // Get all the classRenameList where xj is null
        defaultClassRenameShouldNotBeFound("xj.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByXjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where xj is greater than or equal to DEFAULT_XJ
        defaultClassRenameShouldBeFound("xj.greaterThanOrEqual=" + DEFAULT_XJ);

        // Get all the classRenameList where xj is greater than or equal to UPDATED_XJ
        defaultClassRenameShouldNotBeFound("xj.greaterThanOrEqual=" + UPDATED_XJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByXjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where xj is less than or equal to DEFAULT_XJ
        defaultClassRenameShouldBeFound("xj.lessThanOrEqual=" + DEFAULT_XJ);

        // Get all the classRenameList where xj is less than or equal to SMALLER_XJ
        defaultClassRenameShouldNotBeFound("xj.lessThanOrEqual=" + SMALLER_XJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByXjIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where xj is less than DEFAULT_XJ
        defaultClassRenameShouldNotBeFound("xj.lessThan=" + DEFAULT_XJ);

        // Get all the classRenameList where xj is less than UPDATED_XJ
        defaultClassRenameShouldBeFound("xj.lessThan=" + UPDATED_XJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByXjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where xj is greater than DEFAULT_XJ
        defaultClassRenameShouldNotBeFound("xj.greaterThan=" + DEFAULT_XJ);

        // Get all the classRenameList where xj is greater than SMALLER_XJ
        defaultClassRenameShouldBeFound("xj.greaterThan=" + SMALLER_XJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByClassnameIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where classname equals to DEFAULT_CLASSNAME
        defaultClassRenameShouldBeFound("classname.equals=" + DEFAULT_CLASSNAME);

        // Get all the classRenameList where classname equals to UPDATED_CLASSNAME
        defaultClassRenameShouldNotBeFound("classname.equals=" + UPDATED_CLASSNAME);
    }

    @Test
    @Transactional
    void getAllClassRenamesByClassnameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where classname not equals to DEFAULT_CLASSNAME
        defaultClassRenameShouldNotBeFound("classname.notEquals=" + DEFAULT_CLASSNAME);

        // Get all the classRenameList where classname not equals to UPDATED_CLASSNAME
        defaultClassRenameShouldBeFound("classname.notEquals=" + UPDATED_CLASSNAME);
    }

    @Test
    @Transactional
    void getAllClassRenamesByClassnameIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where classname in DEFAULT_CLASSNAME or UPDATED_CLASSNAME
        defaultClassRenameShouldBeFound("classname.in=" + DEFAULT_CLASSNAME + "," + UPDATED_CLASSNAME);

        // Get all the classRenameList where classname equals to UPDATED_CLASSNAME
        defaultClassRenameShouldNotBeFound("classname.in=" + UPDATED_CLASSNAME);
    }

    @Test
    @Transactional
    void getAllClassRenamesByClassnameIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where classname is not null
        defaultClassRenameShouldBeFound("classname.specified=true");

        // Get all the classRenameList where classname is null
        defaultClassRenameShouldNotBeFound("classname.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByClassnameContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where classname contains DEFAULT_CLASSNAME
        defaultClassRenameShouldBeFound("classname.contains=" + DEFAULT_CLASSNAME);

        // Get all the classRenameList where classname contains UPDATED_CLASSNAME
        defaultClassRenameShouldNotBeFound("classname.contains=" + UPDATED_CLASSNAME);
    }

    @Test
    @Transactional
    void getAllClassRenamesByClassnameNotContainsSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where classname does not contain DEFAULT_CLASSNAME
        defaultClassRenameShouldNotBeFound("classname.doesNotContain=" + DEFAULT_CLASSNAME);

        // Get all the classRenameList where classname does not contain UPDATED_CLASSNAME
        defaultClassRenameShouldBeFound("classname.doesNotContain=" + UPDATED_CLASSNAME);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9010IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9010 equals to DEFAULT_CO_9010
        defaultClassRenameShouldBeFound("co9010.equals=" + DEFAULT_CO_9010);

        // Get all the classRenameList where co9010 equals to UPDATED_CO_9010
        defaultClassRenameShouldNotBeFound("co9010.equals=" + UPDATED_CO_9010);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9010IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9010 not equals to DEFAULT_CO_9010
        defaultClassRenameShouldNotBeFound("co9010.notEquals=" + DEFAULT_CO_9010);

        // Get all the classRenameList where co9010 not equals to UPDATED_CO_9010
        defaultClassRenameShouldBeFound("co9010.notEquals=" + UPDATED_CO_9010);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9010IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9010 in DEFAULT_CO_9010 or UPDATED_CO_9010
        defaultClassRenameShouldBeFound("co9010.in=" + DEFAULT_CO_9010 + "," + UPDATED_CO_9010);

        // Get all the classRenameList where co9010 equals to UPDATED_CO_9010
        defaultClassRenameShouldNotBeFound("co9010.in=" + UPDATED_CO_9010);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9010IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9010 is not null
        defaultClassRenameShouldBeFound("co9010.specified=true");

        // Get all the classRenameList where co9010 is null
        defaultClassRenameShouldNotBeFound("co9010.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9010IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9010 is greater than or equal to DEFAULT_CO_9010
        defaultClassRenameShouldBeFound("co9010.greaterThanOrEqual=" + DEFAULT_CO_9010);

        // Get all the classRenameList where co9010 is greater than or equal to UPDATED_CO_9010
        defaultClassRenameShouldNotBeFound("co9010.greaterThanOrEqual=" + UPDATED_CO_9010);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9010IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9010 is less than or equal to DEFAULT_CO_9010
        defaultClassRenameShouldBeFound("co9010.lessThanOrEqual=" + DEFAULT_CO_9010);

        // Get all the classRenameList where co9010 is less than or equal to SMALLER_CO_9010
        defaultClassRenameShouldNotBeFound("co9010.lessThanOrEqual=" + SMALLER_CO_9010);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9010IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9010 is less than DEFAULT_CO_9010
        defaultClassRenameShouldNotBeFound("co9010.lessThan=" + DEFAULT_CO_9010);

        // Get all the classRenameList where co9010 is less than UPDATED_CO_9010
        defaultClassRenameShouldBeFound("co9010.lessThan=" + UPDATED_CO_9010);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9010IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9010 is greater than DEFAULT_CO_9010
        defaultClassRenameShouldNotBeFound("co9010.greaterThan=" + DEFAULT_CO_9010);

        // Get all the classRenameList where co9010 is greater than SMALLER_CO_9010
        defaultClassRenameShouldBeFound("co9010.greaterThan=" + SMALLER_CO_9010);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9012IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9012 equals to DEFAULT_CO_9012
        defaultClassRenameShouldBeFound("co9012.equals=" + DEFAULT_CO_9012);

        // Get all the classRenameList where co9012 equals to UPDATED_CO_9012
        defaultClassRenameShouldNotBeFound("co9012.equals=" + UPDATED_CO_9012);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9012IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9012 not equals to DEFAULT_CO_9012
        defaultClassRenameShouldNotBeFound("co9012.notEquals=" + DEFAULT_CO_9012);

        // Get all the classRenameList where co9012 not equals to UPDATED_CO_9012
        defaultClassRenameShouldBeFound("co9012.notEquals=" + UPDATED_CO_9012);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9012IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9012 in DEFAULT_CO_9012 or UPDATED_CO_9012
        defaultClassRenameShouldBeFound("co9012.in=" + DEFAULT_CO_9012 + "," + UPDATED_CO_9012);

        // Get all the classRenameList where co9012 equals to UPDATED_CO_9012
        defaultClassRenameShouldNotBeFound("co9012.in=" + UPDATED_CO_9012);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9012IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9012 is not null
        defaultClassRenameShouldBeFound("co9012.specified=true");

        // Get all the classRenameList where co9012 is null
        defaultClassRenameShouldNotBeFound("co9012.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9012IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9012 is greater than or equal to DEFAULT_CO_9012
        defaultClassRenameShouldBeFound("co9012.greaterThanOrEqual=" + DEFAULT_CO_9012);

        // Get all the classRenameList where co9012 is greater than or equal to UPDATED_CO_9012
        defaultClassRenameShouldNotBeFound("co9012.greaterThanOrEqual=" + UPDATED_CO_9012);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9012IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9012 is less than or equal to DEFAULT_CO_9012
        defaultClassRenameShouldBeFound("co9012.lessThanOrEqual=" + DEFAULT_CO_9012);

        // Get all the classRenameList where co9012 is less than or equal to SMALLER_CO_9012
        defaultClassRenameShouldNotBeFound("co9012.lessThanOrEqual=" + SMALLER_CO_9012);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9012IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9012 is less than DEFAULT_CO_9012
        defaultClassRenameShouldNotBeFound("co9012.lessThan=" + DEFAULT_CO_9012);

        // Get all the classRenameList where co9012 is less than UPDATED_CO_9012
        defaultClassRenameShouldBeFound("co9012.lessThan=" + UPDATED_CO_9012);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9012IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9012 is greater than DEFAULT_CO_9012
        defaultClassRenameShouldNotBeFound("co9012.greaterThan=" + DEFAULT_CO_9012);

        // Get all the classRenameList where co9012 is greater than SMALLER_CO_9012
        defaultClassRenameShouldBeFound("co9012.greaterThan=" + SMALLER_CO_9012);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9013IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9013 equals to DEFAULT_CO_9013
        defaultClassRenameShouldBeFound("co9013.equals=" + DEFAULT_CO_9013);

        // Get all the classRenameList where co9013 equals to UPDATED_CO_9013
        defaultClassRenameShouldNotBeFound("co9013.equals=" + UPDATED_CO_9013);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9013IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9013 not equals to DEFAULT_CO_9013
        defaultClassRenameShouldNotBeFound("co9013.notEquals=" + DEFAULT_CO_9013);

        // Get all the classRenameList where co9013 not equals to UPDATED_CO_9013
        defaultClassRenameShouldBeFound("co9013.notEquals=" + UPDATED_CO_9013);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9013IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9013 in DEFAULT_CO_9013 or UPDATED_CO_9013
        defaultClassRenameShouldBeFound("co9013.in=" + DEFAULT_CO_9013 + "," + UPDATED_CO_9013);

        // Get all the classRenameList where co9013 equals to UPDATED_CO_9013
        defaultClassRenameShouldNotBeFound("co9013.in=" + UPDATED_CO_9013);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9013IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9013 is not null
        defaultClassRenameShouldBeFound("co9013.specified=true");

        // Get all the classRenameList where co9013 is null
        defaultClassRenameShouldNotBeFound("co9013.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9013IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9013 is greater than or equal to DEFAULT_CO_9013
        defaultClassRenameShouldBeFound("co9013.greaterThanOrEqual=" + DEFAULT_CO_9013);

        // Get all the classRenameList where co9013 is greater than or equal to UPDATED_CO_9013
        defaultClassRenameShouldNotBeFound("co9013.greaterThanOrEqual=" + UPDATED_CO_9013);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9013IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9013 is less than or equal to DEFAULT_CO_9013
        defaultClassRenameShouldBeFound("co9013.lessThanOrEqual=" + DEFAULT_CO_9013);

        // Get all the classRenameList where co9013 is less than or equal to SMALLER_CO_9013
        defaultClassRenameShouldNotBeFound("co9013.lessThanOrEqual=" + SMALLER_CO_9013);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9013IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9013 is less than DEFAULT_CO_9013
        defaultClassRenameShouldNotBeFound("co9013.lessThan=" + DEFAULT_CO_9013);

        // Get all the classRenameList where co9013 is less than UPDATED_CO_9013
        defaultClassRenameShouldBeFound("co9013.lessThan=" + UPDATED_CO_9013);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9013IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9013 is greater than DEFAULT_CO_9013
        defaultClassRenameShouldNotBeFound("co9013.greaterThan=" + DEFAULT_CO_9013);

        // Get all the classRenameList where co9013 is greater than SMALLER_CO_9013
        defaultClassRenameShouldBeFound("co9013.greaterThan=" + SMALLER_CO_9013);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9014IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9014 equals to DEFAULT_CO_9014
        defaultClassRenameShouldBeFound("co9014.equals=" + DEFAULT_CO_9014);

        // Get all the classRenameList where co9014 equals to UPDATED_CO_9014
        defaultClassRenameShouldNotBeFound("co9014.equals=" + UPDATED_CO_9014);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9014IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9014 not equals to DEFAULT_CO_9014
        defaultClassRenameShouldNotBeFound("co9014.notEquals=" + DEFAULT_CO_9014);

        // Get all the classRenameList where co9014 not equals to UPDATED_CO_9014
        defaultClassRenameShouldBeFound("co9014.notEquals=" + UPDATED_CO_9014);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9014IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9014 in DEFAULT_CO_9014 or UPDATED_CO_9014
        defaultClassRenameShouldBeFound("co9014.in=" + DEFAULT_CO_9014 + "," + UPDATED_CO_9014);

        // Get all the classRenameList where co9014 equals to UPDATED_CO_9014
        defaultClassRenameShouldNotBeFound("co9014.in=" + UPDATED_CO_9014);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9014IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9014 is not null
        defaultClassRenameShouldBeFound("co9014.specified=true");

        // Get all the classRenameList where co9014 is null
        defaultClassRenameShouldNotBeFound("co9014.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9014IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9014 is greater than or equal to DEFAULT_CO_9014
        defaultClassRenameShouldBeFound("co9014.greaterThanOrEqual=" + DEFAULT_CO_9014);

        // Get all the classRenameList where co9014 is greater than or equal to UPDATED_CO_9014
        defaultClassRenameShouldNotBeFound("co9014.greaterThanOrEqual=" + UPDATED_CO_9014);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9014IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9014 is less than or equal to DEFAULT_CO_9014
        defaultClassRenameShouldBeFound("co9014.lessThanOrEqual=" + DEFAULT_CO_9014);

        // Get all the classRenameList where co9014 is less than or equal to SMALLER_CO_9014
        defaultClassRenameShouldNotBeFound("co9014.lessThanOrEqual=" + SMALLER_CO_9014);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9014IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9014 is less than DEFAULT_CO_9014
        defaultClassRenameShouldNotBeFound("co9014.lessThan=" + DEFAULT_CO_9014);

        // Get all the classRenameList where co9014 is less than UPDATED_CO_9014
        defaultClassRenameShouldBeFound("co9014.lessThan=" + UPDATED_CO_9014);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo9014IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co9014 is greater than DEFAULT_CO_9014
        defaultClassRenameShouldNotBeFound("co9014.greaterThan=" + DEFAULT_CO_9014);

        // Get all the classRenameList where co9014 is greater than SMALLER_CO_9014
        defaultClassRenameShouldBeFound("co9014.greaterThan=" + SMALLER_CO_9014);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99915IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99915 equals to DEFAULT_CO_99915
        defaultClassRenameShouldBeFound("co99915.equals=" + DEFAULT_CO_99915);

        // Get all the classRenameList where co99915 equals to UPDATED_CO_99915
        defaultClassRenameShouldNotBeFound("co99915.equals=" + UPDATED_CO_99915);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99915IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99915 not equals to DEFAULT_CO_99915
        defaultClassRenameShouldNotBeFound("co99915.notEquals=" + DEFAULT_CO_99915);

        // Get all the classRenameList where co99915 not equals to UPDATED_CO_99915
        defaultClassRenameShouldBeFound("co99915.notEquals=" + UPDATED_CO_99915);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99915IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99915 in DEFAULT_CO_99915 or UPDATED_CO_99915
        defaultClassRenameShouldBeFound("co99915.in=" + DEFAULT_CO_99915 + "," + UPDATED_CO_99915);

        // Get all the classRenameList where co99915 equals to UPDATED_CO_99915
        defaultClassRenameShouldNotBeFound("co99915.in=" + UPDATED_CO_99915);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99915IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99915 is not null
        defaultClassRenameShouldBeFound("co99915.specified=true");

        // Get all the classRenameList where co99915 is null
        defaultClassRenameShouldNotBeFound("co99915.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99915IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99915 is greater than or equal to DEFAULT_CO_99915
        defaultClassRenameShouldBeFound("co99915.greaterThanOrEqual=" + DEFAULT_CO_99915);

        // Get all the classRenameList where co99915 is greater than or equal to UPDATED_CO_99915
        defaultClassRenameShouldNotBeFound("co99915.greaterThanOrEqual=" + UPDATED_CO_99915);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99915IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99915 is less than or equal to DEFAULT_CO_99915
        defaultClassRenameShouldBeFound("co99915.lessThanOrEqual=" + DEFAULT_CO_99915);

        // Get all the classRenameList where co99915 is less than or equal to SMALLER_CO_99915
        defaultClassRenameShouldNotBeFound("co99915.lessThanOrEqual=" + SMALLER_CO_99915);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99915IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99915 is less than DEFAULT_CO_99915
        defaultClassRenameShouldNotBeFound("co99915.lessThan=" + DEFAULT_CO_99915);

        // Get all the classRenameList where co99915 is less than UPDATED_CO_99915
        defaultClassRenameShouldBeFound("co99915.lessThan=" + UPDATED_CO_99915);
    }

    @Test
    @Transactional
    void getAllClassRenamesByCo99915IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where co99915 is greater than DEFAULT_CO_99915
        defaultClassRenameShouldNotBeFound("co99915.greaterThan=" + DEFAULT_CO_99915);

        // Get all the classRenameList where co99915 is greater than SMALLER_CO_99915
        defaultClassRenameShouldBeFound("co99915.greaterThan=" + SMALLER_CO_99915);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyxjIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyxj equals to DEFAULT_HYXJ
        defaultClassRenameShouldBeFound("hyxj.equals=" + DEFAULT_HYXJ);

        // Get all the classRenameList where hyxj equals to UPDATED_HYXJ
        defaultClassRenameShouldNotBeFound("hyxj.equals=" + UPDATED_HYXJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyxjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyxj not equals to DEFAULT_HYXJ
        defaultClassRenameShouldNotBeFound("hyxj.notEquals=" + DEFAULT_HYXJ);

        // Get all the classRenameList where hyxj not equals to UPDATED_HYXJ
        defaultClassRenameShouldBeFound("hyxj.notEquals=" + UPDATED_HYXJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyxjIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyxj in DEFAULT_HYXJ or UPDATED_HYXJ
        defaultClassRenameShouldBeFound("hyxj.in=" + DEFAULT_HYXJ + "," + UPDATED_HYXJ);

        // Get all the classRenameList where hyxj equals to UPDATED_HYXJ
        defaultClassRenameShouldNotBeFound("hyxj.in=" + UPDATED_HYXJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyxjIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyxj is not null
        defaultClassRenameShouldBeFound("hyxj.specified=true");

        // Get all the classRenameList where hyxj is null
        defaultClassRenameShouldNotBeFound("hyxj.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyxjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyxj is greater than or equal to DEFAULT_HYXJ
        defaultClassRenameShouldBeFound("hyxj.greaterThanOrEqual=" + DEFAULT_HYXJ);

        // Get all the classRenameList where hyxj is greater than or equal to UPDATED_HYXJ
        defaultClassRenameShouldNotBeFound("hyxj.greaterThanOrEqual=" + UPDATED_HYXJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyxjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyxj is less than or equal to DEFAULT_HYXJ
        defaultClassRenameShouldBeFound("hyxj.lessThanOrEqual=" + DEFAULT_HYXJ);

        // Get all the classRenameList where hyxj is less than or equal to SMALLER_HYXJ
        defaultClassRenameShouldNotBeFound("hyxj.lessThanOrEqual=" + SMALLER_HYXJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyxjIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyxj is less than DEFAULT_HYXJ
        defaultClassRenameShouldNotBeFound("hyxj.lessThan=" + DEFAULT_HYXJ);

        // Get all the classRenameList where hyxj is less than UPDATED_HYXJ
        defaultClassRenameShouldBeFound("hyxj.lessThan=" + UPDATED_HYXJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyxjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyxj is greater than DEFAULT_HYXJ
        defaultClassRenameShouldNotBeFound("hyxj.greaterThan=" + DEFAULT_HYXJ);

        // Get all the classRenameList where hyxj is greater than SMALLER_HYXJ
        defaultClassRenameShouldBeFound("hyxj.greaterThan=" + SMALLER_HYXJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyskIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hysk equals to DEFAULT_HYSK
        defaultClassRenameShouldBeFound("hysk.equals=" + DEFAULT_HYSK);

        // Get all the classRenameList where hysk equals to UPDATED_HYSK
        defaultClassRenameShouldNotBeFound("hysk.equals=" + UPDATED_HYSK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyskIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hysk not equals to DEFAULT_HYSK
        defaultClassRenameShouldNotBeFound("hysk.notEquals=" + DEFAULT_HYSK);

        // Get all the classRenameList where hysk not equals to UPDATED_HYSK
        defaultClassRenameShouldBeFound("hysk.notEquals=" + UPDATED_HYSK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyskIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hysk in DEFAULT_HYSK or UPDATED_HYSK
        defaultClassRenameShouldBeFound("hysk.in=" + DEFAULT_HYSK + "," + UPDATED_HYSK);

        // Get all the classRenameList where hysk equals to UPDATED_HYSK
        defaultClassRenameShouldNotBeFound("hysk.in=" + UPDATED_HYSK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyskIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hysk is not null
        defaultClassRenameShouldBeFound("hysk.specified=true");

        // Get all the classRenameList where hysk is null
        defaultClassRenameShouldNotBeFound("hysk.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyskIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hysk is greater than or equal to DEFAULT_HYSK
        defaultClassRenameShouldBeFound("hysk.greaterThanOrEqual=" + DEFAULT_HYSK);

        // Get all the classRenameList where hysk is greater than or equal to UPDATED_HYSK
        defaultClassRenameShouldNotBeFound("hysk.greaterThanOrEqual=" + UPDATED_HYSK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyskIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hysk is less than or equal to DEFAULT_HYSK
        defaultClassRenameShouldBeFound("hysk.lessThanOrEqual=" + DEFAULT_HYSK);

        // Get all the classRenameList where hysk is less than or equal to SMALLER_HYSK
        defaultClassRenameShouldNotBeFound("hysk.lessThanOrEqual=" + SMALLER_HYSK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyskIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hysk is less than DEFAULT_HYSK
        defaultClassRenameShouldNotBeFound("hysk.lessThan=" + DEFAULT_HYSK);

        // Get all the classRenameList where hysk is less than UPDATED_HYSK
        defaultClassRenameShouldBeFound("hysk.lessThan=" + UPDATED_HYSK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyskIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hysk is greater than DEFAULT_HYSK
        defaultClassRenameShouldNotBeFound("hysk.greaterThan=" + DEFAULT_HYSK);

        // Get all the classRenameList where hysk is greater than SMALLER_HYSK
        defaultClassRenameShouldBeFound("hysk.greaterThan=" + SMALLER_HYSK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyqtIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyqt equals to DEFAULT_HYQT
        defaultClassRenameShouldBeFound("hyqt.equals=" + DEFAULT_HYQT);

        // Get all the classRenameList where hyqt equals to UPDATED_HYQT
        defaultClassRenameShouldNotBeFound("hyqt.equals=" + UPDATED_HYQT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyqtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyqt not equals to DEFAULT_HYQT
        defaultClassRenameShouldNotBeFound("hyqt.notEquals=" + DEFAULT_HYQT);

        // Get all the classRenameList where hyqt not equals to UPDATED_HYQT
        defaultClassRenameShouldBeFound("hyqt.notEquals=" + UPDATED_HYQT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyqtIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyqt in DEFAULT_HYQT or UPDATED_HYQT
        defaultClassRenameShouldBeFound("hyqt.in=" + DEFAULT_HYQT + "," + UPDATED_HYQT);

        // Get all the classRenameList where hyqt equals to UPDATED_HYQT
        defaultClassRenameShouldNotBeFound("hyqt.in=" + UPDATED_HYQT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyqtIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyqt is not null
        defaultClassRenameShouldBeFound("hyqt.specified=true");

        // Get all the classRenameList where hyqt is null
        defaultClassRenameShouldNotBeFound("hyqt.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyqtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyqt is greater than or equal to DEFAULT_HYQT
        defaultClassRenameShouldBeFound("hyqt.greaterThanOrEqual=" + DEFAULT_HYQT);

        // Get all the classRenameList where hyqt is greater than or equal to UPDATED_HYQT
        defaultClassRenameShouldNotBeFound("hyqt.greaterThanOrEqual=" + UPDATED_HYQT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyqtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyqt is less than or equal to DEFAULT_HYQT
        defaultClassRenameShouldBeFound("hyqt.lessThanOrEqual=" + DEFAULT_HYQT);

        // Get all the classRenameList where hyqt is less than or equal to SMALLER_HYQT
        defaultClassRenameShouldNotBeFound("hyqt.lessThanOrEqual=" + SMALLER_HYQT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyqtIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyqt is less than DEFAULT_HYQT
        defaultClassRenameShouldNotBeFound("hyqt.lessThan=" + DEFAULT_HYQT);

        // Get all the classRenameList where hyqt is less than UPDATED_HYQT
        defaultClassRenameShouldBeFound("hyqt.lessThan=" + UPDATED_HYQT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHyqtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hyqt is greater than DEFAULT_HYQT
        defaultClassRenameShouldNotBeFound("hyqt.greaterThan=" + DEFAULT_HYQT);

        // Get all the classRenameList where hyqt is greater than SMALLER_HYQT
        defaultClassRenameShouldBeFound("hyqt.greaterThan=" + SMALLER_HYQT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkxjIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkxj equals to DEFAULT_HKXJ
        defaultClassRenameShouldBeFound("hkxj.equals=" + DEFAULT_HKXJ);

        // Get all the classRenameList where hkxj equals to UPDATED_HKXJ
        defaultClassRenameShouldNotBeFound("hkxj.equals=" + UPDATED_HKXJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkxjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkxj not equals to DEFAULT_HKXJ
        defaultClassRenameShouldNotBeFound("hkxj.notEquals=" + DEFAULT_HKXJ);

        // Get all the classRenameList where hkxj not equals to UPDATED_HKXJ
        defaultClassRenameShouldBeFound("hkxj.notEquals=" + UPDATED_HKXJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkxjIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkxj in DEFAULT_HKXJ or UPDATED_HKXJ
        defaultClassRenameShouldBeFound("hkxj.in=" + DEFAULT_HKXJ + "," + UPDATED_HKXJ);

        // Get all the classRenameList where hkxj equals to UPDATED_HKXJ
        defaultClassRenameShouldNotBeFound("hkxj.in=" + UPDATED_HKXJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkxjIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkxj is not null
        defaultClassRenameShouldBeFound("hkxj.specified=true");

        // Get all the classRenameList where hkxj is null
        defaultClassRenameShouldNotBeFound("hkxj.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkxjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkxj is greater than or equal to DEFAULT_HKXJ
        defaultClassRenameShouldBeFound("hkxj.greaterThanOrEqual=" + DEFAULT_HKXJ);

        // Get all the classRenameList where hkxj is greater than or equal to UPDATED_HKXJ
        defaultClassRenameShouldNotBeFound("hkxj.greaterThanOrEqual=" + UPDATED_HKXJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkxjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkxj is less than or equal to DEFAULT_HKXJ
        defaultClassRenameShouldBeFound("hkxj.lessThanOrEqual=" + DEFAULT_HKXJ);

        // Get all the classRenameList where hkxj is less than or equal to SMALLER_HKXJ
        defaultClassRenameShouldNotBeFound("hkxj.lessThanOrEqual=" + SMALLER_HKXJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkxjIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkxj is less than DEFAULT_HKXJ
        defaultClassRenameShouldNotBeFound("hkxj.lessThan=" + DEFAULT_HKXJ);

        // Get all the classRenameList where hkxj is less than UPDATED_HKXJ
        defaultClassRenameShouldBeFound("hkxj.lessThan=" + UPDATED_HKXJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkxjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkxj is greater than DEFAULT_HKXJ
        defaultClassRenameShouldNotBeFound("hkxj.greaterThan=" + DEFAULT_HKXJ);

        // Get all the classRenameList where hkxj is greater than SMALLER_HKXJ
        defaultClassRenameShouldBeFound("hkxj.greaterThan=" + SMALLER_HKXJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkskIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hksk equals to DEFAULT_HKSK
        defaultClassRenameShouldBeFound("hksk.equals=" + DEFAULT_HKSK);

        // Get all the classRenameList where hksk equals to UPDATED_HKSK
        defaultClassRenameShouldNotBeFound("hksk.equals=" + UPDATED_HKSK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkskIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hksk not equals to DEFAULT_HKSK
        defaultClassRenameShouldNotBeFound("hksk.notEquals=" + DEFAULT_HKSK);

        // Get all the classRenameList where hksk not equals to UPDATED_HKSK
        defaultClassRenameShouldBeFound("hksk.notEquals=" + UPDATED_HKSK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkskIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hksk in DEFAULT_HKSK or UPDATED_HKSK
        defaultClassRenameShouldBeFound("hksk.in=" + DEFAULT_HKSK + "," + UPDATED_HKSK);

        // Get all the classRenameList where hksk equals to UPDATED_HKSK
        defaultClassRenameShouldNotBeFound("hksk.in=" + UPDATED_HKSK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkskIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hksk is not null
        defaultClassRenameShouldBeFound("hksk.specified=true");

        // Get all the classRenameList where hksk is null
        defaultClassRenameShouldNotBeFound("hksk.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkskIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hksk is greater than or equal to DEFAULT_HKSK
        defaultClassRenameShouldBeFound("hksk.greaterThanOrEqual=" + DEFAULT_HKSK);

        // Get all the classRenameList where hksk is greater than or equal to UPDATED_HKSK
        defaultClassRenameShouldNotBeFound("hksk.greaterThanOrEqual=" + UPDATED_HKSK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkskIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hksk is less than or equal to DEFAULT_HKSK
        defaultClassRenameShouldBeFound("hksk.lessThanOrEqual=" + DEFAULT_HKSK);

        // Get all the classRenameList where hksk is less than or equal to SMALLER_HKSK
        defaultClassRenameShouldNotBeFound("hksk.lessThanOrEqual=" + SMALLER_HKSK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkskIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hksk is less than DEFAULT_HKSK
        defaultClassRenameShouldNotBeFound("hksk.lessThan=" + DEFAULT_HKSK);

        // Get all the classRenameList where hksk is less than UPDATED_HKSK
        defaultClassRenameShouldBeFound("hksk.lessThan=" + UPDATED_HKSK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkskIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hksk is greater than DEFAULT_HKSK
        defaultClassRenameShouldNotBeFound("hksk.greaterThan=" + DEFAULT_HKSK);

        // Get all the classRenameList where hksk is greater than SMALLER_HKSK
        defaultClassRenameShouldBeFound("hksk.greaterThan=" + SMALLER_HKSK);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkqtIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkqt equals to DEFAULT_HKQT
        defaultClassRenameShouldBeFound("hkqt.equals=" + DEFAULT_HKQT);

        // Get all the classRenameList where hkqt equals to UPDATED_HKQT
        defaultClassRenameShouldNotBeFound("hkqt.equals=" + UPDATED_HKQT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkqtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkqt not equals to DEFAULT_HKQT
        defaultClassRenameShouldNotBeFound("hkqt.notEquals=" + DEFAULT_HKQT);

        // Get all the classRenameList where hkqt not equals to UPDATED_HKQT
        defaultClassRenameShouldBeFound("hkqt.notEquals=" + UPDATED_HKQT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkqtIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkqt in DEFAULT_HKQT or UPDATED_HKQT
        defaultClassRenameShouldBeFound("hkqt.in=" + DEFAULT_HKQT + "," + UPDATED_HKQT);

        // Get all the classRenameList where hkqt equals to UPDATED_HKQT
        defaultClassRenameShouldNotBeFound("hkqt.in=" + UPDATED_HKQT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkqtIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkqt is not null
        defaultClassRenameShouldBeFound("hkqt.specified=true");

        // Get all the classRenameList where hkqt is null
        defaultClassRenameShouldNotBeFound("hkqt.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkqtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkqt is greater than or equal to DEFAULT_HKQT
        defaultClassRenameShouldBeFound("hkqt.greaterThanOrEqual=" + DEFAULT_HKQT);

        // Get all the classRenameList where hkqt is greater than or equal to UPDATED_HKQT
        defaultClassRenameShouldNotBeFound("hkqt.greaterThanOrEqual=" + UPDATED_HKQT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkqtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkqt is less than or equal to DEFAULT_HKQT
        defaultClassRenameShouldBeFound("hkqt.lessThanOrEqual=" + DEFAULT_HKQT);

        // Get all the classRenameList where hkqt is less than or equal to SMALLER_HKQT
        defaultClassRenameShouldNotBeFound("hkqt.lessThanOrEqual=" + SMALLER_HKQT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkqtIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkqt is less than DEFAULT_HKQT
        defaultClassRenameShouldNotBeFound("hkqt.lessThan=" + DEFAULT_HKQT);

        // Get all the classRenameList where hkqt is less than UPDATED_HKQT
        defaultClassRenameShouldBeFound("hkqt.lessThan=" + UPDATED_HKQT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByHkqtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where hkqt is greater than DEFAULT_HKQT
        defaultClassRenameShouldNotBeFound("hkqt.greaterThan=" + DEFAULT_HKQT);

        // Get all the classRenameList where hkqt is greater than SMALLER_HKQT
        defaultClassRenameShouldBeFound("hkqt.greaterThan=" + SMALLER_HKQT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtwtIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtwt equals to DEFAULT_QTWT
        defaultClassRenameShouldBeFound("qtwt.equals=" + DEFAULT_QTWT);

        // Get all the classRenameList where qtwt equals to UPDATED_QTWT
        defaultClassRenameShouldNotBeFound("qtwt.equals=" + UPDATED_QTWT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtwtIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtwt not equals to DEFAULT_QTWT
        defaultClassRenameShouldNotBeFound("qtwt.notEquals=" + DEFAULT_QTWT);

        // Get all the classRenameList where qtwt not equals to UPDATED_QTWT
        defaultClassRenameShouldBeFound("qtwt.notEquals=" + UPDATED_QTWT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtwtIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtwt in DEFAULT_QTWT or UPDATED_QTWT
        defaultClassRenameShouldBeFound("qtwt.in=" + DEFAULT_QTWT + "," + UPDATED_QTWT);

        // Get all the classRenameList where qtwt equals to UPDATED_QTWT
        defaultClassRenameShouldNotBeFound("qtwt.in=" + UPDATED_QTWT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtwtIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtwt is not null
        defaultClassRenameShouldBeFound("qtwt.specified=true");

        // Get all the classRenameList where qtwt is null
        defaultClassRenameShouldNotBeFound("qtwt.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtwtIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtwt is greater than or equal to DEFAULT_QTWT
        defaultClassRenameShouldBeFound("qtwt.greaterThanOrEqual=" + DEFAULT_QTWT);

        // Get all the classRenameList where qtwt is greater than or equal to UPDATED_QTWT
        defaultClassRenameShouldNotBeFound("qtwt.greaterThanOrEqual=" + UPDATED_QTWT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtwtIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtwt is less than or equal to DEFAULT_QTWT
        defaultClassRenameShouldBeFound("qtwt.lessThanOrEqual=" + DEFAULT_QTWT);

        // Get all the classRenameList where qtwt is less than or equal to SMALLER_QTWT
        defaultClassRenameShouldNotBeFound("qtwt.lessThanOrEqual=" + SMALLER_QTWT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtwtIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtwt is less than DEFAULT_QTWT
        defaultClassRenameShouldNotBeFound("qtwt.lessThan=" + DEFAULT_QTWT);

        // Get all the classRenameList where qtwt is less than UPDATED_QTWT
        defaultClassRenameShouldBeFound("qtwt.lessThan=" + UPDATED_QTWT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtwtIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtwt is greater than DEFAULT_QTWT
        defaultClassRenameShouldNotBeFound("qtwt.greaterThan=" + DEFAULT_QTWT);

        // Get all the classRenameList where qtwt is greater than SMALLER_QTWT
        defaultClassRenameShouldBeFound("qtwt.greaterThan=" + SMALLER_QTWT);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtysqIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtysq equals to DEFAULT_QTYSQ
        defaultClassRenameShouldBeFound("qtysq.equals=" + DEFAULT_QTYSQ);

        // Get all the classRenameList where qtysq equals to UPDATED_QTYSQ
        defaultClassRenameShouldNotBeFound("qtysq.equals=" + UPDATED_QTYSQ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtysqIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtysq not equals to DEFAULT_QTYSQ
        defaultClassRenameShouldNotBeFound("qtysq.notEquals=" + DEFAULT_QTYSQ);

        // Get all the classRenameList where qtysq not equals to UPDATED_QTYSQ
        defaultClassRenameShouldBeFound("qtysq.notEquals=" + UPDATED_QTYSQ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtysqIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtysq in DEFAULT_QTYSQ or UPDATED_QTYSQ
        defaultClassRenameShouldBeFound("qtysq.in=" + DEFAULT_QTYSQ + "," + UPDATED_QTYSQ);

        // Get all the classRenameList where qtysq equals to UPDATED_QTYSQ
        defaultClassRenameShouldNotBeFound("qtysq.in=" + UPDATED_QTYSQ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtysqIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtysq is not null
        defaultClassRenameShouldBeFound("qtysq.specified=true");

        // Get all the classRenameList where qtysq is null
        defaultClassRenameShouldNotBeFound("qtysq.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtysqIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtysq is greater than or equal to DEFAULT_QTYSQ
        defaultClassRenameShouldBeFound("qtysq.greaterThanOrEqual=" + DEFAULT_QTYSQ);

        // Get all the classRenameList where qtysq is greater than or equal to UPDATED_QTYSQ
        defaultClassRenameShouldNotBeFound("qtysq.greaterThanOrEqual=" + UPDATED_QTYSQ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtysqIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtysq is less than or equal to DEFAULT_QTYSQ
        defaultClassRenameShouldBeFound("qtysq.lessThanOrEqual=" + DEFAULT_QTYSQ);

        // Get all the classRenameList where qtysq is less than or equal to SMALLER_QTYSQ
        defaultClassRenameShouldNotBeFound("qtysq.lessThanOrEqual=" + SMALLER_QTYSQ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtysqIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtysq is less than DEFAULT_QTYSQ
        defaultClassRenameShouldNotBeFound("qtysq.lessThan=" + DEFAULT_QTYSQ);

        // Get all the classRenameList where qtysq is less than UPDATED_QTYSQ
        defaultClassRenameShouldBeFound("qtysq.lessThan=" + UPDATED_QTYSQ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByQtysqIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where qtysq is greater than DEFAULT_QTYSQ
        defaultClassRenameShouldNotBeFound("qtysq.greaterThan=" + DEFAULT_QTYSQ);

        // Get all the classRenameList where qtysq is greater than SMALLER_QTYSQ
        defaultClassRenameShouldBeFound("qtysq.greaterThan=" + SMALLER_QTYSQ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByBbysjIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where bbysj equals to DEFAULT_BBYSJ
        defaultClassRenameShouldBeFound("bbysj.equals=" + DEFAULT_BBYSJ);

        // Get all the classRenameList where bbysj equals to UPDATED_BBYSJ
        defaultClassRenameShouldNotBeFound("bbysj.equals=" + UPDATED_BBYSJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByBbysjIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where bbysj not equals to DEFAULT_BBYSJ
        defaultClassRenameShouldNotBeFound("bbysj.notEquals=" + DEFAULT_BBYSJ);

        // Get all the classRenameList where bbysj not equals to UPDATED_BBYSJ
        defaultClassRenameShouldBeFound("bbysj.notEquals=" + UPDATED_BBYSJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByBbysjIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where bbysj in DEFAULT_BBYSJ or UPDATED_BBYSJ
        defaultClassRenameShouldBeFound("bbysj.in=" + DEFAULT_BBYSJ + "," + UPDATED_BBYSJ);

        // Get all the classRenameList where bbysj equals to UPDATED_BBYSJ
        defaultClassRenameShouldNotBeFound("bbysj.in=" + UPDATED_BBYSJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByBbysjIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where bbysj is not null
        defaultClassRenameShouldBeFound("bbysj.specified=true");

        // Get all the classRenameList where bbysj is null
        defaultClassRenameShouldNotBeFound("bbysj.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByBbysjIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where bbysj is greater than or equal to DEFAULT_BBYSJ
        defaultClassRenameShouldBeFound("bbysj.greaterThanOrEqual=" + DEFAULT_BBYSJ);

        // Get all the classRenameList where bbysj is greater than or equal to UPDATED_BBYSJ
        defaultClassRenameShouldNotBeFound("bbysj.greaterThanOrEqual=" + UPDATED_BBYSJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByBbysjIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where bbysj is less than or equal to DEFAULT_BBYSJ
        defaultClassRenameShouldBeFound("bbysj.lessThanOrEqual=" + DEFAULT_BBYSJ);

        // Get all the classRenameList where bbysj is less than or equal to SMALLER_BBYSJ
        defaultClassRenameShouldNotBeFound("bbysj.lessThanOrEqual=" + SMALLER_BBYSJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByBbysjIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where bbysj is less than DEFAULT_BBYSJ
        defaultClassRenameShouldNotBeFound("bbysj.lessThan=" + DEFAULT_BBYSJ);

        // Get all the classRenameList where bbysj is less than UPDATED_BBYSJ
        defaultClassRenameShouldBeFound("bbysj.lessThan=" + UPDATED_BBYSJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByBbysjIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where bbysj is greater than DEFAULT_BBYSJ
        defaultClassRenameShouldNotBeFound("bbysj.greaterThan=" + DEFAULT_BBYSJ);

        // Get all the classRenameList where bbysj is greater than SMALLER_BBYSJ
        defaultClassRenameShouldBeFound("bbysj.greaterThan=" + SMALLER_BBYSJ);
    }

    @Test
    @Transactional
    void getAllClassRenamesByZfbjeIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where zfbje equals to DEFAULT_ZFBJE
        defaultClassRenameShouldBeFound("zfbje.equals=" + DEFAULT_ZFBJE);

        // Get all the classRenameList where zfbje equals to UPDATED_ZFBJE
        defaultClassRenameShouldNotBeFound("zfbje.equals=" + UPDATED_ZFBJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByZfbjeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where zfbje not equals to DEFAULT_ZFBJE
        defaultClassRenameShouldNotBeFound("zfbje.notEquals=" + DEFAULT_ZFBJE);

        // Get all the classRenameList where zfbje not equals to UPDATED_ZFBJE
        defaultClassRenameShouldBeFound("zfbje.notEquals=" + UPDATED_ZFBJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByZfbjeIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where zfbje in DEFAULT_ZFBJE or UPDATED_ZFBJE
        defaultClassRenameShouldBeFound("zfbje.in=" + DEFAULT_ZFBJE + "," + UPDATED_ZFBJE);

        // Get all the classRenameList where zfbje equals to UPDATED_ZFBJE
        defaultClassRenameShouldNotBeFound("zfbje.in=" + UPDATED_ZFBJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByZfbjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where zfbje is not null
        defaultClassRenameShouldBeFound("zfbje.specified=true");

        // Get all the classRenameList where zfbje is null
        defaultClassRenameShouldNotBeFound("zfbje.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByZfbjeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where zfbje is greater than or equal to DEFAULT_ZFBJE
        defaultClassRenameShouldBeFound("zfbje.greaterThanOrEqual=" + DEFAULT_ZFBJE);

        // Get all the classRenameList where zfbje is greater than or equal to UPDATED_ZFBJE
        defaultClassRenameShouldNotBeFound("zfbje.greaterThanOrEqual=" + UPDATED_ZFBJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByZfbjeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where zfbje is less than or equal to DEFAULT_ZFBJE
        defaultClassRenameShouldBeFound("zfbje.lessThanOrEqual=" + DEFAULT_ZFBJE);

        // Get all the classRenameList where zfbje is less than or equal to SMALLER_ZFBJE
        defaultClassRenameShouldNotBeFound("zfbje.lessThanOrEqual=" + SMALLER_ZFBJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByZfbjeIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where zfbje is less than DEFAULT_ZFBJE
        defaultClassRenameShouldNotBeFound("zfbje.lessThan=" + DEFAULT_ZFBJE);

        // Get all the classRenameList where zfbje is less than UPDATED_ZFBJE
        defaultClassRenameShouldBeFound("zfbje.lessThan=" + UPDATED_ZFBJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByZfbjeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where zfbje is greater than DEFAULT_ZFBJE
        defaultClassRenameShouldNotBeFound("zfbje.greaterThan=" + DEFAULT_ZFBJE);

        // Get all the classRenameList where zfbje is greater than SMALLER_ZFBJE
        defaultClassRenameShouldBeFound("zfbje.greaterThan=" + SMALLER_ZFBJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByWxjeIsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where wxje equals to DEFAULT_WXJE
        defaultClassRenameShouldBeFound("wxje.equals=" + DEFAULT_WXJE);

        // Get all the classRenameList where wxje equals to UPDATED_WXJE
        defaultClassRenameShouldNotBeFound("wxje.equals=" + UPDATED_WXJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByWxjeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where wxje not equals to DEFAULT_WXJE
        defaultClassRenameShouldNotBeFound("wxje.notEquals=" + DEFAULT_WXJE);

        // Get all the classRenameList where wxje not equals to UPDATED_WXJE
        defaultClassRenameShouldBeFound("wxje.notEquals=" + UPDATED_WXJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByWxjeIsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where wxje in DEFAULT_WXJE or UPDATED_WXJE
        defaultClassRenameShouldBeFound("wxje.in=" + DEFAULT_WXJE + "," + UPDATED_WXJE);

        // Get all the classRenameList where wxje equals to UPDATED_WXJE
        defaultClassRenameShouldNotBeFound("wxje.in=" + UPDATED_WXJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByWxjeIsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where wxje is not null
        defaultClassRenameShouldBeFound("wxje.specified=true");

        // Get all the classRenameList where wxje is null
        defaultClassRenameShouldNotBeFound("wxje.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByWxjeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where wxje is greater than or equal to DEFAULT_WXJE
        defaultClassRenameShouldBeFound("wxje.greaterThanOrEqual=" + DEFAULT_WXJE);

        // Get all the classRenameList where wxje is greater than or equal to UPDATED_WXJE
        defaultClassRenameShouldNotBeFound("wxje.greaterThanOrEqual=" + UPDATED_WXJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByWxjeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where wxje is less than or equal to DEFAULT_WXJE
        defaultClassRenameShouldBeFound("wxje.lessThanOrEqual=" + DEFAULT_WXJE);

        // Get all the classRenameList where wxje is less than or equal to SMALLER_WXJE
        defaultClassRenameShouldNotBeFound("wxje.lessThanOrEqual=" + SMALLER_WXJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByWxjeIsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where wxje is less than DEFAULT_WXJE
        defaultClassRenameShouldNotBeFound("wxje.lessThan=" + DEFAULT_WXJE);

        // Get all the classRenameList where wxje is less than UPDATED_WXJE
        defaultClassRenameShouldBeFound("wxje.lessThan=" + UPDATED_WXJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByWxjeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where wxje is greater than DEFAULT_WXJE
        defaultClassRenameShouldNotBeFound("wxje.greaterThan=" + DEFAULT_WXJE);

        // Get all the classRenameList where wxje is greater than SMALLER_WXJE
        defaultClassRenameShouldBeFound("wxje.greaterThan=" + SMALLER_WXJE);
    }

    @Test
    @Transactional
    void getAllClassRenamesByw99920IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where w99920 equals to DEFAULT_W_99920
        defaultClassRenameShouldBeFound("w99920.equals=" + DEFAULT_W_99920);

        // Get all the classRenameList where w99920 equals to UPDATED_W_99920
        defaultClassRenameShouldNotBeFound("w99920.equals=" + UPDATED_W_99920);
    }

    @Test
    @Transactional
    void getAllClassRenamesByw99920IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where w99920 not equals to DEFAULT_W_99920
        defaultClassRenameShouldNotBeFound("w99920.notEquals=" + DEFAULT_W_99920);

        // Get all the classRenameList where w99920 not equals to UPDATED_W_99920
        defaultClassRenameShouldBeFound("w99920.notEquals=" + UPDATED_W_99920);
    }

    @Test
    @Transactional
    void getAllClassRenamesByw99920IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where w99920 in DEFAULT_W_99920 or UPDATED_W_99920
        defaultClassRenameShouldBeFound("w99920.in=" + DEFAULT_W_99920 + "," + UPDATED_W_99920);

        // Get all the classRenameList where w99920 equals to UPDATED_W_99920
        defaultClassRenameShouldNotBeFound("w99920.in=" + UPDATED_W_99920);
    }

    @Test
    @Transactional
    void getAllClassRenamesByw99920IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where w99920 is not null
        defaultClassRenameShouldBeFound("w99920.specified=true");

        // Get all the classRenameList where w99920 is null
        defaultClassRenameShouldNotBeFound("w99920.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByw99920IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where w99920 is greater than or equal to DEFAULT_W_99920
        defaultClassRenameShouldBeFound("w99920.greaterThanOrEqual=" + DEFAULT_W_99920);

        // Get all the classRenameList where w99920 is greater than or equal to UPDATED_W_99920
        defaultClassRenameShouldNotBeFound("w99920.greaterThanOrEqual=" + UPDATED_W_99920);
    }

    @Test
    @Transactional
    void getAllClassRenamesByw99920IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where w99920 is less than or equal to DEFAULT_W_99920
        defaultClassRenameShouldBeFound("w99920.lessThanOrEqual=" + DEFAULT_W_99920);

        // Get all the classRenameList where w99920 is less than or equal to SMALLER_W_99920
        defaultClassRenameShouldNotBeFound("w99920.lessThanOrEqual=" + SMALLER_W_99920);
    }

    @Test
    @Transactional
    void getAllClassRenamesByw99920IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where w99920 is less than DEFAULT_W_99920
        defaultClassRenameShouldNotBeFound("w99920.lessThan=" + DEFAULT_W_99920);

        // Get all the classRenameList where w99920 is less than UPDATED_W_99920
        defaultClassRenameShouldBeFound("w99920.lessThan=" + UPDATED_W_99920);
    }

    @Test
    @Transactional
    void getAllClassRenamesByw99920IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where w99920 is greater than DEFAULT_W_99920
        defaultClassRenameShouldNotBeFound("w99920.greaterThan=" + DEFAULT_W_99920);

        // Get all the classRenameList where w99920 is greater than SMALLER_W_99920
        defaultClassRenameShouldBeFound("w99920.greaterThan=" + SMALLER_W_99920);
    }

    @Test
    @Transactional
    void getAllClassRenamesByz99921IsEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where z99921 equals to DEFAULT_Z_99921
        defaultClassRenameShouldBeFound("z99921.equals=" + DEFAULT_Z_99921);

        // Get all the classRenameList where z99921 equals to UPDATED_Z_99921
        defaultClassRenameShouldNotBeFound("z99921.equals=" + UPDATED_Z_99921);
    }

    @Test
    @Transactional
    void getAllClassRenamesByz99921IsNotEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where z99921 not equals to DEFAULT_Z_99921
        defaultClassRenameShouldNotBeFound("z99921.notEquals=" + DEFAULT_Z_99921);

        // Get all the classRenameList where z99921 not equals to UPDATED_Z_99921
        defaultClassRenameShouldBeFound("z99921.notEquals=" + UPDATED_Z_99921);
    }

    @Test
    @Transactional
    void getAllClassRenamesByz99921IsInShouldWork() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where z99921 in DEFAULT_Z_99921 or UPDATED_Z_99921
        defaultClassRenameShouldBeFound("z99921.in=" + DEFAULT_Z_99921 + "," + UPDATED_Z_99921);

        // Get all the classRenameList where z99921 equals to UPDATED_Z_99921
        defaultClassRenameShouldNotBeFound("z99921.in=" + UPDATED_Z_99921);
    }

    @Test
    @Transactional
    void getAllClassRenamesByz99921IsNullOrNotNull() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where z99921 is not null
        defaultClassRenameShouldBeFound("z99921.specified=true");

        // Get all the classRenameList where z99921 is null
        defaultClassRenameShouldNotBeFound("z99921.specified=false");
    }

    @Test
    @Transactional
    void getAllClassRenamesByz99921IsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where z99921 is greater than or equal to DEFAULT_Z_99921
        defaultClassRenameShouldBeFound("z99921.greaterThanOrEqual=" + DEFAULT_Z_99921);

        // Get all the classRenameList where z99921 is greater than or equal to UPDATED_Z_99921
        defaultClassRenameShouldNotBeFound("z99921.greaterThanOrEqual=" + UPDATED_Z_99921);
    }

    @Test
    @Transactional
    void getAllClassRenamesByz99921IsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where z99921 is less than or equal to DEFAULT_Z_99921
        defaultClassRenameShouldBeFound("z99921.lessThanOrEqual=" + DEFAULT_Z_99921);

        // Get all the classRenameList where z99921 is less than or equal to SMALLER_Z_99921
        defaultClassRenameShouldNotBeFound("z99921.lessThanOrEqual=" + SMALLER_Z_99921);
    }

    @Test
    @Transactional
    void getAllClassRenamesByz99921IsLessThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where z99921 is less than DEFAULT_Z_99921
        defaultClassRenameShouldNotBeFound("z99921.lessThan=" + DEFAULT_Z_99921);

        // Get all the classRenameList where z99921 is less than UPDATED_Z_99921
        defaultClassRenameShouldBeFound("z99921.lessThan=" + UPDATED_Z_99921);
    }

    @Test
    @Transactional
    void getAllClassRenamesByz99921IsGreaterThanSomething() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        // Get all the classRenameList where z99921 is greater than DEFAULT_Z_99921
        defaultClassRenameShouldNotBeFound("z99921.greaterThan=" + DEFAULT_Z_99921);

        // Get all the classRenameList where z99921 is greater than SMALLER_Z_99921
        defaultClassRenameShouldBeFound("z99921.greaterThan=" + SMALLER_Z_99921);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClassRenameShouldBeFound(String filter) throws Exception {
        restClassRenameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classRename.getId().intValue())))
            .andExpect(jsonPath("$.[*].dt").value(hasItem(DEFAULT_DT.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].oldmoney").value(hasItem(sameNumber(DEFAULT_OLDMONEY))))
            .andExpect(jsonPath("$.[*].getmoney").value(hasItem(sameNumber(DEFAULT_GETMONEY))))
            .andExpect(jsonPath("$.[*].toup").value(hasItem(sameNumber(DEFAULT_TOUP))))
            .andExpect(jsonPath("$.[*].downempn").value(hasItem(DEFAULT_DOWNEMPN)))
            .andExpect(jsonPath("$.[*].todown").value(hasItem(sameNumber(DEFAULT_TODOWN))))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].old2").value(hasItem(sameNumber(DEFAULT_OLD_2))))
            .andExpect(jsonPath("$.[*].get2").value(hasItem(sameNumber(DEFAULT_GET_2))))
            .andExpect(jsonPath("$.[*].toup2").value(hasItem(sameNumber(DEFAULT_TOUP_2))))
            .andExpect(jsonPath("$.[*].todown2").value(hasItem(sameNumber(DEFAULT_TODOWN_2))))
            .andExpect(jsonPath("$.[*].upempn2").value(hasItem(DEFAULT_UPEMPN_2)))
            .andExpect(jsonPath("$.[*].im9008").value(hasItem(sameNumber(DEFAULT_IM_9008))))
            .andExpect(jsonPath("$.[*].im9009").value(hasItem(sameNumber(DEFAULT_IM_9009))))
            .andExpect(jsonPath("$.[*].co9991").value(hasItem(sameNumber(DEFAULT_CO_9991))))
            .andExpect(jsonPath("$.[*].co9992").value(hasItem(sameNumber(DEFAULT_CO_9992))))
            .andExpect(jsonPath("$.[*].co9993").value(hasItem(sameNumber(DEFAULT_CO_9993))))
            .andExpect(jsonPath("$.[*].co9994").value(hasItem(sameNumber(DEFAULT_CO_9994))))
            .andExpect(jsonPath("$.[*].co9995").value(hasItem(sameNumber(DEFAULT_CO_9995))))
            .andExpect(jsonPath("$.[*].co9998").value(hasItem(sameNumber(DEFAULT_CO_9998))))
            .andExpect(jsonPath("$.[*].im9007").value(hasItem(sameNumber(DEFAULT_IM_9007))))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].co9999").value(hasItem(sameNumber(DEFAULT_CO_9999))))
            .andExpect(jsonPath("$.[*].cm9008").value(hasItem(sameNumber(DEFAULT_CM_9008))))
            .andExpect(jsonPath("$.[*].cm9009").value(hasItem(sameNumber(DEFAULT_CM_9009))))
            .andExpect(jsonPath("$.[*].co99910").value(hasItem(sameNumber(DEFAULT_CO_99910))))
            .andExpect(jsonPath("$.[*].checkSign").value(hasItem(DEFAULT_CHECK_SIGN)))
            .andExpect(jsonPath("$.[*].classPb").value(hasItem(DEFAULT_CLASS_PB)))
            .andExpect(jsonPath("$.[*].ck").value(hasItem(sameNumber(DEFAULT_CK))))
            .andExpect(jsonPath("$.[*].dk").value(hasItem(sameNumber(DEFAULT_DK))))
            .andExpect(jsonPath("$.[*].sjrq").value(hasItem(DEFAULT_SJRQ.toString())))
            .andExpect(jsonPath("$.[*].qsjrq").value(hasItem(DEFAULT_QSJRQ.toString())))
            .andExpect(jsonPath("$.[*].byje").value(hasItem(sameNumber(DEFAULT_BYJE))))
            .andExpect(jsonPath("$.[*].xfcw").value(hasItem(DEFAULT_XFCW)))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())))
            .andExpect(jsonPath("$.[*].co99912").value(hasItem(sameNumber(DEFAULT_CO_99912))))
            .andExpect(jsonPath("$.[*].xj").value(hasItem(sameNumber(DEFAULT_XJ))))
            .andExpect(jsonPath("$.[*].classname").value(hasItem(DEFAULT_CLASSNAME)))
            .andExpect(jsonPath("$.[*].co9010").value(hasItem(sameNumber(DEFAULT_CO_9010))))
            .andExpect(jsonPath("$.[*].co9012").value(hasItem(sameNumber(DEFAULT_CO_9012))))
            .andExpect(jsonPath("$.[*].co9013").value(hasItem(sameNumber(DEFAULT_CO_9013))))
            .andExpect(jsonPath("$.[*].co9014").value(hasItem(sameNumber(DEFAULT_CO_9014))))
            .andExpect(jsonPath("$.[*].co99915").value(hasItem(sameNumber(DEFAULT_CO_99915))))
            .andExpect(jsonPath("$.[*].hyxj").value(hasItem(sameNumber(DEFAULT_HYXJ))))
            .andExpect(jsonPath("$.[*].hysk").value(hasItem(sameNumber(DEFAULT_HYSK))))
            .andExpect(jsonPath("$.[*].hyqt").value(hasItem(sameNumber(DEFAULT_HYQT))))
            .andExpect(jsonPath("$.[*].hkxj").value(hasItem(sameNumber(DEFAULT_HKXJ))))
            .andExpect(jsonPath("$.[*].hksk").value(hasItem(sameNumber(DEFAULT_HKSK))))
            .andExpect(jsonPath("$.[*].hkqt").value(hasItem(sameNumber(DEFAULT_HKQT))))
            .andExpect(jsonPath("$.[*].qtwt").value(hasItem(sameNumber(DEFAULT_QTWT))))
            .andExpect(jsonPath("$.[*].qtysq").value(hasItem(sameNumber(DEFAULT_QTYSQ))))
            .andExpect(jsonPath("$.[*].bbysj").value(hasItem(sameNumber(DEFAULT_BBYSJ))))
            .andExpect(jsonPath("$.[*].zfbje").value(hasItem(sameNumber(DEFAULT_ZFBJE))))
            .andExpect(jsonPath("$.[*].wxje").value(hasItem(sameNumber(DEFAULT_WXJE))))
            .andExpect(jsonPath("$.[*].w99920").value(hasItem(sameNumber(DEFAULT_W_99920))))
            .andExpect(jsonPath("$.[*].z99921").value(hasItem(sameNumber(DEFAULT_Z_99921))));

        // Check, that the count call also returns 1
        restClassRenameMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClassRenameShouldNotBeFound(String filter) throws Exception {
        restClassRenameMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClassRenameMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClassRename() throws Exception {
        // Get the classRename
        restClassRenameMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClassRename() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        int databaseSizeBeforeUpdate = classRenameRepository.findAll().size();

        // Update the classRename
        ClassRename updatedClassRename = classRenameRepository.findById(classRename.getId()).get();
        // Disconnect from session so that the updates on updatedClassRename are not directly saved in db
        em.detach(updatedClassRename);
        updatedClassRename
            .dt(UPDATED_DT)
            .empn(UPDATED_EMPN)
            .oldmoney(UPDATED_OLDMONEY)
            .getmoney(UPDATED_GETMONEY)
            .toup(UPDATED_TOUP)
            .downempn(UPDATED_DOWNEMPN)
            .todown(UPDATED_TODOWN)
            .flag(UPDATED_FLAG)
            .old2(UPDATED_OLD_2)
            .get2(UPDATED_GET_2)
            .toup2(UPDATED_TOUP_2)
            .todown2(UPDATED_TODOWN_2)
            .upempn2(UPDATED_UPEMPN_2)
            .im9008(UPDATED_IM_9008)
            .im9009(UPDATED_IM_9009)
            .co9991(UPDATED_CO_9991)
            .co9992(UPDATED_CO_9992)
            .co9993(UPDATED_CO_9993)
            .co9994(UPDATED_CO_9994)
            .co9995(UPDATED_CO_9995)
            .co9998(UPDATED_CO_9998)
            .im9007(UPDATED_IM_9007)
            .gotime(UPDATED_GOTIME)
            .co9999(UPDATED_CO_9999)
            .cm9008(UPDATED_CM_9008)
            .cm9009(UPDATED_CM_9009)
            .co99910(UPDATED_CO_99910)
            .checkSign(UPDATED_CHECK_SIGN)
            .classPb(UPDATED_CLASS_PB)
            .ck(UPDATED_CK)
            .dk(UPDATED_DK)
            .sjrq(UPDATED_SJRQ)
            .qsjrq(UPDATED_QSJRQ)
            .byje(UPDATED_BYJE)
            .xfcw(UPDATED_XFCW)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW)
            .co99912(UPDATED_CO_99912)
            .xj(UPDATED_XJ)
            .classname(UPDATED_CLASSNAME)
            .co9010(UPDATED_CO_9010)
            .co9012(UPDATED_CO_9012)
            .co9013(UPDATED_CO_9013)
            .co9014(UPDATED_CO_9014)
            .co99915(UPDATED_CO_99915)
            .hyxj(UPDATED_HYXJ)
            .hysk(UPDATED_HYSK)
            .hyqt(UPDATED_HYQT)
            .hkxj(UPDATED_HKXJ)
            .hksk(UPDATED_HKSK)
            .hkqt(UPDATED_HKQT)
            .qtwt(UPDATED_QTWT)
            .qtysq(UPDATED_QTYSQ)
            .bbysj(UPDATED_BBYSJ)
            .zfbje(UPDATED_ZFBJE)
            .wxje(UPDATED_WXJE)
            .w99920(UPDATED_W_99920)
            .z99921(UPDATED_Z_99921);
        ClassRenameDTO classRenameDTO = classRenameMapper.toDto(updatedClassRename);

        restClassRenameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classRenameDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classRenameDTO))
            )
            .andExpect(status().isOk());

        // Validate the ClassRename in the database
        List<ClassRename> classRenameList = classRenameRepository.findAll();
        assertThat(classRenameList).hasSize(databaseSizeBeforeUpdate);
        ClassRename testClassRename = classRenameList.get(classRenameList.size() - 1);
        assertThat(testClassRename.getDt()).isEqualTo(UPDATED_DT);
        assertThat(testClassRename.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testClassRename.getOldmoney()).isEqualTo(UPDATED_OLDMONEY);
        assertThat(testClassRename.getGetmoney()).isEqualTo(UPDATED_GETMONEY);
        assertThat(testClassRename.getToup()).isEqualTo(UPDATED_TOUP);
        assertThat(testClassRename.getDownempn()).isEqualTo(UPDATED_DOWNEMPN);
        assertThat(testClassRename.getTodown()).isEqualTo(UPDATED_TODOWN);
        assertThat(testClassRename.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testClassRename.getOld2()).isEqualTo(UPDATED_OLD_2);
        assertThat(testClassRename.getGet2()).isEqualTo(UPDATED_GET_2);
        assertThat(testClassRename.getToup2()).isEqualTo(UPDATED_TOUP_2);
        assertThat(testClassRename.getTodown2()).isEqualTo(UPDATED_TODOWN_2);
        assertThat(testClassRename.getUpempn2()).isEqualTo(UPDATED_UPEMPN_2);
        assertThat(testClassRename.getIm9008()).isEqualTo(UPDATED_IM_9008);
        assertThat(testClassRename.getIm9009()).isEqualTo(UPDATED_IM_9009);
        assertThat(testClassRename.getCo9991()).isEqualTo(UPDATED_CO_9991);
        assertThat(testClassRename.getCo9992()).isEqualTo(UPDATED_CO_9992);
        assertThat(testClassRename.getCo9993()).isEqualTo(UPDATED_CO_9993);
        assertThat(testClassRename.getCo9994()).isEqualTo(UPDATED_CO_9994);
        assertThat(testClassRename.getCo9995()).isEqualTo(UPDATED_CO_9995);
        assertThat(testClassRename.getCo9998()).isEqualTo(UPDATED_CO_9998);
        assertThat(testClassRename.getIm9007()).isEqualTo(UPDATED_IM_9007);
        assertThat(testClassRename.getGotime()).isEqualTo(UPDATED_GOTIME);
        assertThat(testClassRename.getCo9999()).isEqualTo(UPDATED_CO_9999);
        assertThat(testClassRename.getCm9008()).isEqualTo(UPDATED_CM_9008);
        assertThat(testClassRename.getCm9009()).isEqualTo(UPDATED_CM_9009);
        assertThat(testClassRename.getCo99910()).isEqualTo(UPDATED_CO_99910);
        assertThat(testClassRename.getCheckSign()).isEqualTo(UPDATED_CHECK_SIGN);
        assertThat(testClassRename.getClassPb()).isEqualTo(UPDATED_CLASS_PB);
        assertThat(testClassRename.getCk()).isEqualTo(UPDATED_CK);
        assertThat(testClassRename.getDk()).isEqualTo(UPDATED_DK);
        assertThat(testClassRename.getSjrq()).isEqualTo(UPDATED_SJRQ);
        assertThat(testClassRename.getQsjrq()).isEqualTo(UPDATED_QSJRQ);
        assertThat(testClassRename.getByje()).isEqualTo(UPDATED_BYJE);
        assertThat(testClassRename.getXfcw()).isEqualTo(UPDATED_XFCW);
        assertThat(testClassRename.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testClassRename.getIsnew()).isEqualTo(UPDATED_ISNEW);
        assertThat(testClassRename.getCo99912()).isEqualTo(UPDATED_CO_99912);
        assertThat(testClassRename.getXj()).isEqualTo(UPDATED_XJ);
        assertThat(testClassRename.getClassname()).isEqualTo(UPDATED_CLASSNAME);
        assertThat(testClassRename.getCo9010()).isEqualTo(UPDATED_CO_9010);
        assertThat(testClassRename.getCo9012()).isEqualTo(UPDATED_CO_9012);
        assertThat(testClassRename.getCo9013()).isEqualTo(UPDATED_CO_9013);
        assertThat(testClassRename.getCo9014()).isEqualTo(UPDATED_CO_9014);
        assertThat(testClassRename.getCo99915()).isEqualTo(UPDATED_CO_99915);
        assertThat(testClassRename.getHyxj()).isEqualTo(UPDATED_HYXJ);
        assertThat(testClassRename.getHysk()).isEqualTo(UPDATED_HYSK);
        assertThat(testClassRename.getHyqt()).isEqualTo(UPDATED_HYQT);
        assertThat(testClassRename.getHkxj()).isEqualTo(UPDATED_HKXJ);
        assertThat(testClassRename.getHksk()).isEqualTo(UPDATED_HKSK);
        assertThat(testClassRename.getHkqt()).isEqualTo(UPDATED_HKQT);
        assertThat(testClassRename.getQtwt()).isEqualTo(UPDATED_QTWT);
        assertThat(testClassRename.getQtysq()).isEqualTo(UPDATED_QTYSQ);
        assertThat(testClassRename.getBbysj()).isEqualTo(UPDATED_BBYSJ);
        assertThat(testClassRename.getZfbje()).isEqualTo(UPDATED_ZFBJE);
        assertThat(testClassRename.getWxje()).isEqualTo(UPDATED_WXJE);
        assertThat(testClassRename.getw99920()).isEqualTo(UPDATED_W_99920);
        assertThat(testClassRename.getz99921()).isEqualTo(UPDATED_Z_99921);

        // Validate the ClassRename in Elasticsearch
        verify(mockClassRenameSearchRepository).save(testClassRename);
    }

    @Test
    @Transactional
    void putNonExistingClassRename() throws Exception {
        int databaseSizeBeforeUpdate = classRenameRepository.findAll().size();
        classRename.setId(count.incrementAndGet());

        // Create the ClassRename
        ClassRenameDTO classRenameDTO = classRenameMapper.toDto(classRename);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassRenameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, classRenameDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classRenameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassRename in the database
        List<ClassRename> classRenameList = classRenameRepository.findAll();
        assertThat(classRenameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassRename in Elasticsearch
        verify(mockClassRenameSearchRepository, times(0)).save(classRename);
    }

    @Test
    @Transactional
    void putWithIdMismatchClassRename() throws Exception {
        int databaseSizeBeforeUpdate = classRenameRepository.findAll().size();
        classRename.setId(count.incrementAndGet());

        // Create the ClassRename
        ClassRenameDTO classRenameDTO = classRenameMapper.toDto(classRename);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassRenameMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(classRenameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassRename in the database
        List<ClassRename> classRenameList = classRenameRepository.findAll();
        assertThat(classRenameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassRename in Elasticsearch
        verify(mockClassRenameSearchRepository, times(0)).save(classRename);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClassRename() throws Exception {
        int databaseSizeBeforeUpdate = classRenameRepository.findAll().size();
        classRename.setId(count.incrementAndGet());

        // Create the ClassRename
        ClassRenameDTO classRenameDTO = classRenameMapper.toDto(classRename);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassRenameMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(classRenameDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClassRename in the database
        List<ClassRename> classRenameList = classRenameRepository.findAll();
        assertThat(classRenameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassRename in Elasticsearch
        verify(mockClassRenameSearchRepository, times(0)).save(classRename);
    }

    @Test
    @Transactional
    void partialUpdateClassRenameWithPatch() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        int databaseSizeBeforeUpdate = classRenameRepository.findAll().size();

        // Update the classRename using partial update
        ClassRename partialUpdatedClassRename = new ClassRename();
        partialUpdatedClassRename.setId(classRename.getId());

        partialUpdatedClassRename
            .dt(UPDATED_DT)
            .flag(UPDATED_FLAG)
            .get2(UPDATED_GET_2)
            .todown2(UPDATED_TODOWN_2)
            .im9008(UPDATED_IM_9008)
            .co9993(UPDATED_CO_9993)
            .co9994(UPDATED_CO_9994)
            .co9995(UPDATED_CO_9995)
            .co9998(UPDATED_CO_9998)
            .co9999(UPDATED_CO_9999)
            .checkSign(UPDATED_CHECK_SIGN)
            .ck(UPDATED_CK)
            .dk(UPDATED_DK)
            .isnew(UPDATED_ISNEW)
            .co99912(UPDATED_CO_99912)
            .co9010(UPDATED_CO_9010)
            .co9012(UPDATED_CO_9012)
            .co9014(UPDATED_CO_9014)
            .co99915(UPDATED_CO_99915)
            .hyxj(UPDATED_HYXJ)
            .hyqt(UPDATED_HYQT)
            .hksk(UPDATED_HKSK)
            .qtysq(UPDATED_QTYSQ)
            .zfbje(UPDATED_ZFBJE)
            .w99920(UPDATED_W_99920)
            .z99921(UPDATED_Z_99921);

        restClassRenameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassRename.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClassRename))
            )
            .andExpect(status().isOk());

        // Validate the ClassRename in the database
        List<ClassRename> classRenameList = classRenameRepository.findAll();
        assertThat(classRenameList).hasSize(databaseSizeBeforeUpdate);
        ClassRename testClassRename = classRenameList.get(classRenameList.size() - 1);
        assertThat(testClassRename.getDt()).isEqualTo(UPDATED_DT);
        assertThat(testClassRename.getEmpn()).isEqualTo(DEFAULT_EMPN);
        assertThat(testClassRename.getOldmoney()).isEqualByComparingTo(DEFAULT_OLDMONEY);
        assertThat(testClassRename.getGetmoney()).isEqualByComparingTo(DEFAULT_GETMONEY);
        assertThat(testClassRename.getToup()).isEqualByComparingTo(DEFAULT_TOUP);
        assertThat(testClassRename.getDownempn()).isEqualTo(DEFAULT_DOWNEMPN);
        assertThat(testClassRename.getTodown()).isEqualByComparingTo(DEFAULT_TODOWN);
        assertThat(testClassRename.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testClassRename.getOld2()).isEqualByComparingTo(DEFAULT_OLD_2);
        assertThat(testClassRename.getGet2()).isEqualByComparingTo(UPDATED_GET_2);
        assertThat(testClassRename.getToup2()).isEqualByComparingTo(DEFAULT_TOUP_2);
        assertThat(testClassRename.getTodown2()).isEqualByComparingTo(UPDATED_TODOWN_2);
        assertThat(testClassRename.getUpempn2()).isEqualTo(DEFAULT_UPEMPN_2);
        assertThat(testClassRename.getIm9008()).isEqualByComparingTo(UPDATED_IM_9008);
        assertThat(testClassRename.getIm9009()).isEqualByComparingTo(DEFAULT_IM_9009);
        assertThat(testClassRename.getCo9991()).isEqualByComparingTo(DEFAULT_CO_9991);
        assertThat(testClassRename.getCo9992()).isEqualByComparingTo(DEFAULT_CO_9992);
        assertThat(testClassRename.getCo9993()).isEqualByComparingTo(UPDATED_CO_9993);
        assertThat(testClassRename.getCo9994()).isEqualByComparingTo(UPDATED_CO_9994);
        assertThat(testClassRename.getCo9995()).isEqualByComparingTo(UPDATED_CO_9995);
        assertThat(testClassRename.getCo9998()).isEqualByComparingTo(UPDATED_CO_9998);
        assertThat(testClassRename.getIm9007()).isEqualByComparingTo(DEFAULT_IM_9007);
        assertThat(testClassRename.getGotime()).isEqualTo(DEFAULT_GOTIME);
        assertThat(testClassRename.getCo9999()).isEqualByComparingTo(UPDATED_CO_9999);
        assertThat(testClassRename.getCm9008()).isEqualByComparingTo(DEFAULT_CM_9008);
        assertThat(testClassRename.getCm9009()).isEqualByComparingTo(DEFAULT_CM_9009);
        assertThat(testClassRename.getCo99910()).isEqualByComparingTo(DEFAULT_CO_99910);
        assertThat(testClassRename.getCheckSign()).isEqualTo(UPDATED_CHECK_SIGN);
        assertThat(testClassRename.getClassPb()).isEqualTo(DEFAULT_CLASS_PB);
        assertThat(testClassRename.getCk()).isEqualByComparingTo(UPDATED_CK);
        assertThat(testClassRename.getDk()).isEqualByComparingTo(UPDATED_DK);
        assertThat(testClassRename.getSjrq()).isEqualTo(DEFAULT_SJRQ);
        assertThat(testClassRename.getQsjrq()).isEqualTo(DEFAULT_QSJRQ);
        assertThat(testClassRename.getByje()).isEqualByComparingTo(DEFAULT_BYJE);
        assertThat(testClassRename.getXfcw()).isEqualTo(DEFAULT_XFCW);
        assertThat(testClassRename.getHoteldm()).isEqualTo(DEFAULT_HOTELDM);
        assertThat(testClassRename.getIsnew()).isEqualTo(UPDATED_ISNEW);
        assertThat(testClassRename.getCo99912()).isEqualByComparingTo(UPDATED_CO_99912);
        assertThat(testClassRename.getXj()).isEqualByComparingTo(DEFAULT_XJ);
        assertThat(testClassRename.getClassname()).isEqualTo(DEFAULT_CLASSNAME);
        assertThat(testClassRename.getCo9010()).isEqualByComparingTo(UPDATED_CO_9010);
        assertThat(testClassRename.getCo9012()).isEqualByComparingTo(UPDATED_CO_9012);
        assertThat(testClassRename.getCo9013()).isEqualByComparingTo(DEFAULT_CO_9013);
        assertThat(testClassRename.getCo9014()).isEqualByComparingTo(UPDATED_CO_9014);
        assertThat(testClassRename.getCo99915()).isEqualByComparingTo(UPDATED_CO_99915);
        assertThat(testClassRename.getHyxj()).isEqualByComparingTo(UPDATED_HYXJ);
        assertThat(testClassRename.getHysk()).isEqualByComparingTo(DEFAULT_HYSK);
        assertThat(testClassRename.getHyqt()).isEqualByComparingTo(UPDATED_HYQT);
        assertThat(testClassRename.getHkxj()).isEqualByComparingTo(DEFAULT_HKXJ);
        assertThat(testClassRename.getHksk()).isEqualByComparingTo(UPDATED_HKSK);
        assertThat(testClassRename.getHkqt()).isEqualByComparingTo(DEFAULT_HKQT);
        assertThat(testClassRename.getQtwt()).isEqualByComparingTo(DEFAULT_QTWT);
        assertThat(testClassRename.getQtysq()).isEqualByComparingTo(UPDATED_QTYSQ);
        assertThat(testClassRename.getBbysj()).isEqualByComparingTo(DEFAULT_BBYSJ);
        assertThat(testClassRename.getZfbje()).isEqualByComparingTo(UPDATED_ZFBJE);
        assertThat(testClassRename.getWxje()).isEqualByComparingTo(DEFAULT_WXJE);
        assertThat(testClassRename.getw99920()).isEqualByComparingTo(UPDATED_W_99920);
        assertThat(testClassRename.getz99921()).isEqualByComparingTo(UPDATED_Z_99921);
    }

    @Test
    @Transactional
    void fullUpdateClassRenameWithPatch() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        int databaseSizeBeforeUpdate = classRenameRepository.findAll().size();

        // Update the classRename using partial update
        ClassRename partialUpdatedClassRename = new ClassRename();
        partialUpdatedClassRename.setId(classRename.getId());

        partialUpdatedClassRename
            .dt(UPDATED_DT)
            .empn(UPDATED_EMPN)
            .oldmoney(UPDATED_OLDMONEY)
            .getmoney(UPDATED_GETMONEY)
            .toup(UPDATED_TOUP)
            .downempn(UPDATED_DOWNEMPN)
            .todown(UPDATED_TODOWN)
            .flag(UPDATED_FLAG)
            .old2(UPDATED_OLD_2)
            .get2(UPDATED_GET_2)
            .toup2(UPDATED_TOUP_2)
            .todown2(UPDATED_TODOWN_2)
            .upempn2(UPDATED_UPEMPN_2)
            .im9008(UPDATED_IM_9008)
            .im9009(UPDATED_IM_9009)
            .co9991(UPDATED_CO_9991)
            .co9992(UPDATED_CO_9992)
            .co9993(UPDATED_CO_9993)
            .co9994(UPDATED_CO_9994)
            .co9995(UPDATED_CO_9995)
            .co9998(UPDATED_CO_9998)
            .im9007(UPDATED_IM_9007)
            .gotime(UPDATED_GOTIME)
            .co9999(UPDATED_CO_9999)
            .cm9008(UPDATED_CM_9008)
            .cm9009(UPDATED_CM_9009)
            .co99910(UPDATED_CO_99910)
            .checkSign(UPDATED_CHECK_SIGN)
            .classPb(UPDATED_CLASS_PB)
            .ck(UPDATED_CK)
            .dk(UPDATED_DK)
            .sjrq(UPDATED_SJRQ)
            .qsjrq(UPDATED_QSJRQ)
            .byje(UPDATED_BYJE)
            .xfcw(UPDATED_XFCW)
            .hoteldm(UPDATED_HOTELDM)
            .isnew(UPDATED_ISNEW)
            .co99912(UPDATED_CO_99912)
            .xj(UPDATED_XJ)
            .classname(UPDATED_CLASSNAME)
            .co9010(UPDATED_CO_9010)
            .co9012(UPDATED_CO_9012)
            .co9013(UPDATED_CO_9013)
            .co9014(UPDATED_CO_9014)
            .co99915(UPDATED_CO_99915)
            .hyxj(UPDATED_HYXJ)
            .hysk(UPDATED_HYSK)
            .hyqt(UPDATED_HYQT)
            .hkxj(UPDATED_HKXJ)
            .hksk(UPDATED_HKSK)
            .hkqt(UPDATED_HKQT)
            .qtwt(UPDATED_QTWT)
            .qtysq(UPDATED_QTYSQ)
            .bbysj(UPDATED_BBYSJ)
            .zfbje(UPDATED_ZFBJE)
            .wxje(UPDATED_WXJE)
            .w99920(UPDATED_W_99920)
            .z99921(UPDATED_Z_99921);

        restClassRenameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClassRename.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClassRename))
            )
            .andExpect(status().isOk());

        // Validate the ClassRename in the database
        List<ClassRename> classRenameList = classRenameRepository.findAll();
        assertThat(classRenameList).hasSize(databaseSizeBeforeUpdate);
        ClassRename testClassRename = classRenameList.get(classRenameList.size() - 1);
        assertThat(testClassRename.getDt()).isEqualTo(UPDATED_DT);
        assertThat(testClassRename.getEmpn()).isEqualTo(UPDATED_EMPN);
        assertThat(testClassRename.getOldmoney()).isEqualByComparingTo(UPDATED_OLDMONEY);
        assertThat(testClassRename.getGetmoney()).isEqualByComparingTo(UPDATED_GETMONEY);
        assertThat(testClassRename.getToup()).isEqualByComparingTo(UPDATED_TOUP);
        assertThat(testClassRename.getDownempn()).isEqualTo(UPDATED_DOWNEMPN);
        assertThat(testClassRename.getTodown()).isEqualByComparingTo(UPDATED_TODOWN);
        assertThat(testClassRename.getFlag()).isEqualTo(UPDATED_FLAG);
        assertThat(testClassRename.getOld2()).isEqualByComparingTo(UPDATED_OLD_2);
        assertThat(testClassRename.getGet2()).isEqualByComparingTo(UPDATED_GET_2);
        assertThat(testClassRename.getToup2()).isEqualByComparingTo(UPDATED_TOUP_2);
        assertThat(testClassRename.getTodown2()).isEqualByComparingTo(UPDATED_TODOWN_2);
        assertThat(testClassRename.getUpempn2()).isEqualTo(UPDATED_UPEMPN_2);
        assertThat(testClassRename.getIm9008()).isEqualByComparingTo(UPDATED_IM_9008);
        assertThat(testClassRename.getIm9009()).isEqualByComparingTo(UPDATED_IM_9009);
        assertThat(testClassRename.getCo9991()).isEqualByComparingTo(UPDATED_CO_9991);
        assertThat(testClassRename.getCo9992()).isEqualByComparingTo(UPDATED_CO_9992);
        assertThat(testClassRename.getCo9993()).isEqualByComparingTo(UPDATED_CO_9993);
        assertThat(testClassRename.getCo9994()).isEqualByComparingTo(UPDATED_CO_9994);
        assertThat(testClassRename.getCo9995()).isEqualByComparingTo(UPDATED_CO_9995);
        assertThat(testClassRename.getCo9998()).isEqualByComparingTo(UPDATED_CO_9998);
        assertThat(testClassRename.getIm9007()).isEqualByComparingTo(UPDATED_IM_9007);
        assertThat(testClassRename.getGotime()).isEqualTo(UPDATED_GOTIME);
        assertThat(testClassRename.getCo9999()).isEqualByComparingTo(UPDATED_CO_9999);
        assertThat(testClassRename.getCm9008()).isEqualByComparingTo(UPDATED_CM_9008);
        assertThat(testClassRename.getCm9009()).isEqualByComparingTo(UPDATED_CM_9009);
        assertThat(testClassRename.getCo99910()).isEqualByComparingTo(UPDATED_CO_99910);
        assertThat(testClassRename.getCheckSign()).isEqualTo(UPDATED_CHECK_SIGN);
        assertThat(testClassRename.getClassPb()).isEqualTo(UPDATED_CLASS_PB);
        assertThat(testClassRename.getCk()).isEqualByComparingTo(UPDATED_CK);
        assertThat(testClassRename.getDk()).isEqualByComparingTo(UPDATED_DK);
        assertThat(testClassRename.getSjrq()).isEqualTo(UPDATED_SJRQ);
        assertThat(testClassRename.getQsjrq()).isEqualTo(UPDATED_QSJRQ);
        assertThat(testClassRename.getByje()).isEqualByComparingTo(UPDATED_BYJE);
        assertThat(testClassRename.getXfcw()).isEqualTo(UPDATED_XFCW);
        assertThat(testClassRename.getHoteldm()).isEqualTo(UPDATED_HOTELDM);
        assertThat(testClassRename.getIsnew()).isEqualTo(UPDATED_ISNEW);
        assertThat(testClassRename.getCo99912()).isEqualByComparingTo(UPDATED_CO_99912);
        assertThat(testClassRename.getXj()).isEqualByComparingTo(UPDATED_XJ);
        assertThat(testClassRename.getClassname()).isEqualTo(UPDATED_CLASSNAME);
        assertThat(testClassRename.getCo9010()).isEqualByComparingTo(UPDATED_CO_9010);
        assertThat(testClassRename.getCo9012()).isEqualByComparingTo(UPDATED_CO_9012);
        assertThat(testClassRename.getCo9013()).isEqualByComparingTo(UPDATED_CO_9013);
        assertThat(testClassRename.getCo9014()).isEqualByComparingTo(UPDATED_CO_9014);
        assertThat(testClassRename.getCo99915()).isEqualByComparingTo(UPDATED_CO_99915);
        assertThat(testClassRename.getHyxj()).isEqualByComparingTo(UPDATED_HYXJ);
        assertThat(testClassRename.getHysk()).isEqualByComparingTo(UPDATED_HYSK);
        assertThat(testClassRename.getHyqt()).isEqualByComparingTo(UPDATED_HYQT);
        assertThat(testClassRename.getHkxj()).isEqualByComparingTo(UPDATED_HKXJ);
        assertThat(testClassRename.getHksk()).isEqualByComparingTo(UPDATED_HKSK);
        assertThat(testClassRename.getHkqt()).isEqualByComparingTo(UPDATED_HKQT);
        assertThat(testClassRename.getQtwt()).isEqualByComparingTo(UPDATED_QTWT);
        assertThat(testClassRename.getQtysq()).isEqualByComparingTo(UPDATED_QTYSQ);
        assertThat(testClassRename.getBbysj()).isEqualByComparingTo(UPDATED_BBYSJ);
        assertThat(testClassRename.getZfbje()).isEqualByComparingTo(UPDATED_ZFBJE);
        assertThat(testClassRename.getWxje()).isEqualByComparingTo(UPDATED_WXJE);
        assertThat(testClassRename.getw99920()).isEqualByComparingTo(UPDATED_W_99920);
        assertThat(testClassRename.getz99921()).isEqualByComparingTo(UPDATED_Z_99921);
    }

    @Test
    @Transactional
    void patchNonExistingClassRename() throws Exception {
        int databaseSizeBeforeUpdate = classRenameRepository.findAll().size();
        classRename.setId(count.incrementAndGet());

        // Create the ClassRename
        ClassRenameDTO classRenameDTO = classRenameMapper.toDto(classRename);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClassRenameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, classRenameDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classRenameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassRename in the database
        List<ClassRename> classRenameList = classRenameRepository.findAll();
        assertThat(classRenameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassRename in Elasticsearch
        verify(mockClassRenameSearchRepository, times(0)).save(classRename);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClassRename() throws Exception {
        int databaseSizeBeforeUpdate = classRenameRepository.findAll().size();
        classRename.setId(count.incrementAndGet());

        // Create the ClassRename
        ClassRenameDTO classRenameDTO = classRenameMapper.toDto(classRename);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassRenameMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(classRenameDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClassRename in the database
        List<ClassRename> classRenameList = classRenameRepository.findAll();
        assertThat(classRenameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassRename in Elasticsearch
        verify(mockClassRenameSearchRepository, times(0)).save(classRename);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClassRename() throws Exception {
        int databaseSizeBeforeUpdate = classRenameRepository.findAll().size();
        classRename.setId(count.incrementAndGet());

        // Create the ClassRename
        ClassRenameDTO classRenameDTO = classRenameMapper.toDto(classRename);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClassRenameMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(classRenameDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClassRename in the database
        List<ClassRename> classRenameList = classRenameRepository.findAll();
        assertThat(classRenameList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ClassRename in Elasticsearch
        verify(mockClassRenameSearchRepository, times(0)).save(classRename);
    }

    @Test
    @Transactional
    void deleteClassRename() throws Exception {
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);

        int databaseSizeBeforeDelete = classRenameRepository.findAll().size();

        // Delete the classRename
        restClassRenameMockMvc
            .perform(delete(ENTITY_API_URL_ID, classRename.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClassRename> classRenameList = classRenameRepository.findAll();
        assertThat(classRenameList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ClassRename in Elasticsearch
        verify(mockClassRenameSearchRepository, times(1)).deleteById(classRename.getId());
    }

    @Test
    @Transactional
    void searchClassRename() throws Exception {
        // Configure the mock search repository
        // Initialize the database
        classRenameRepository.saveAndFlush(classRename);
        when(mockClassRenameSearchRepository.search(queryStringQuery("id:" + classRename.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(classRename), PageRequest.of(0, 1), 1));

        // Search the classRename
        restClassRenameMockMvc
            .perform(get(ENTITY_SEARCH_API_URL + "?query=id:" + classRename.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(classRename.getId().intValue())))
            .andExpect(jsonPath("$.[*].dt").value(hasItem(DEFAULT_DT.toString())))
            .andExpect(jsonPath("$.[*].empn").value(hasItem(DEFAULT_EMPN)))
            .andExpect(jsonPath("$.[*].oldmoney").value(hasItem(sameNumber(DEFAULT_OLDMONEY))))
            .andExpect(jsonPath("$.[*].getmoney").value(hasItem(sameNumber(DEFAULT_GETMONEY))))
            .andExpect(jsonPath("$.[*].toup").value(hasItem(sameNumber(DEFAULT_TOUP))))
            .andExpect(jsonPath("$.[*].downempn").value(hasItem(DEFAULT_DOWNEMPN)))
            .andExpect(jsonPath("$.[*].todown").value(hasItem(sameNumber(DEFAULT_TODOWN))))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.intValue())))
            .andExpect(jsonPath("$.[*].old2").value(hasItem(sameNumber(DEFAULT_OLD_2))))
            .andExpect(jsonPath("$.[*].get2").value(hasItem(sameNumber(DEFAULT_GET_2))))
            .andExpect(jsonPath("$.[*].toup2").value(hasItem(sameNumber(DEFAULT_TOUP_2))))
            .andExpect(jsonPath("$.[*].todown2").value(hasItem(sameNumber(DEFAULT_TODOWN_2))))
            .andExpect(jsonPath("$.[*].upempn2").value(hasItem(DEFAULT_UPEMPN_2)))
            .andExpect(jsonPath("$.[*].im9008").value(hasItem(sameNumber(DEFAULT_IM_9008))))
            .andExpect(jsonPath("$.[*].im9009").value(hasItem(sameNumber(DEFAULT_IM_9009))))
            .andExpect(jsonPath("$.[*].co9991").value(hasItem(sameNumber(DEFAULT_CO_9991))))
            .andExpect(jsonPath("$.[*].co9992").value(hasItem(sameNumber(DEFAULT_CO_9992))))
            .andExpect(jsonPath("$.[*].co9993").value(hasItem(sameNumber(DEFAULT_CO_9993))))
            .andExpect(jsonPath("$.[*].co9994").value(hasItem(sameNumber(DEFAULT_CO_9994))))
            .andExpect(jsonPath("$.[*].co9995").value(hasItem(sameNumber(DEFAULT_CO_9995))))
            .andExpect(jsonPath("$.[*].co9998").value(hasItem(sameNumber(DEFAULT_CO_9998))))
            .andExpect(jsonPath("$.[*].im9007").value(hasItem(sameNumber(DEFAULT_IM_9007))))
            .andExpect(jsonPath("$.[*].gotime").value(hasItem(DEFAULT_GOTIME.toString())))
            .andExpect(jsonPath("$.[*].co9999").value(hasItem(sameNumber(DEFAULT_CO_9999))))
            .andExpect(jsonPath("$.[*].cm9008").value(hasItem(sameNumber(DEFAULT_CM_9008))))
            .andExpect(jsonPath("$.[*].cm9009").value(hasItem(sameNumber(DEFAULT_CM_9009))))
            .andExpect(jsonPath("$.[*].co99910").value(hasItem(sameNumber(DEFAULT_CO_99910))))
            .andExpect(jsonPath("$.[*].checkSign").value(hasItem(DEFAULT_CHECK_SIGN)))
            .andExpect(jsonPath("$.[*].classPb").value(hasItem(DEFAULT_CLASS_PB)))
            .andExpect(jsonPath("$.[*].ck").value(hasItem(sameNumber(DEFAULT_CK))))
            .andExpect(jsonPath("$.[*].dk").value(hasItem(sameNumber(DEFAULT_DK))))
            .andExpect(jsonPath("$.[*].sjrq").value(hasItem(DEFAULT_SJRQ.toString())))
            .andExpect(jsonPath("$.[*].qsjrq").value(hasItem(DEFAULT_QSJRQ.toString())))
            .andExpect(jsonPath("$.[*].byje").value(hasItem(sameNumber(DEFAULT_BYJE))))
            .andExpect(jsonPath("$.[*].xfcw").value(hasItem(DEFAULT_XFCW)))
            .andExpect(jsonPath("$.[*].hoteldm").value(hasItem(DEFAULT_HOTELDM)))
            .andExpect(jsonPath("$.[*].isnew").value(hasItem(DEFAULT_ISNEW.intValue())))
            .andExpect(jsonPath("$.[*].co99912").value(hasItem(sameNumber(DEFAULT_CO_99912))))
            .andExpect(jsonPath("$.[*].xj").value(hasItem(sameNumber(DEFAULT_XJ))))
            .andExpect(jsonPath("$.[*].classname").value(hasItem(DEFAULT_CLASSNAME)))
            .andExpect(jsonPath("$.[*].co9010").value(hasItem(sameNumber(DEFAULT_CO_9010))))
            .andExpect(jsonPath("$.[*].co9012").value(hasItem(sameNumber(DEFAULT_CO_9012))))
            .andExpect(jsonPath("$.[*].co9013").value(hasItem(sameNumber(DEFAULT_CO_9013))))
            .andExpect(jsonPath("$.[*].co9014").value(hasItem(sameNumber(DEFAULT_CO_9014))))
            .andExpect(jsonPath("$.[*].co99915").value(hasItem(sameNumber(DEFAULT_CO_99915))))
            .andExpect(jsonPath("$.[*].hyxj").value(hasItem(sameNumber(DEFAULT_HYXJ))))
            .andExpect(jsonPath("$.[*].hysk").value(hasItem(sameNumber(DEFAULT_HYSK))))
            .andExpect(jsonPath("$.[*].hyqt").value(hasItem(sameNumber(DEFAULT_HYQT))))
            .andExpect(jsonPath("$.[*].hkxj").value(hasItem(sameNumber(DEFAULT_HKXJ))))
            .andExpect(jsonPath("$.[*].hksk").value(hasItem(sameNumber(DEFAULT_HKSK))))
            .andExpect(jsonPath("$.[*].hkqt").value(hasItem(sameNumber(DEFAULT_HKQT))))
            .andExpect(jsonPath("$.[*].qtwt").value(hasItem(sameNumber(DEFAULT_QTWT))))
            .andExpect(jsonPath("$.[*].qtysq").value(hasItem(sameNumber(DEFAULT_QTYSQ))))
            .andExpect(jsonPath("$.[*].bbysj").value(hasItem(sameNumber(DEFAULT_BBYSJ))))
            .andExpect(jsonPath("$.[*].zfbje").value(hasItem(sameNumber(DEFAULT_ZFBJE))))
            .andExpect(jsonPath("$.[*].wxje").value(hasItem(sameNumber(DEFAULT_WXJE))))
            .andExpect(jsonPath("$.[*].w99920").value(hasItem(sameNumber(DEFAULT_W_99920))))
            .andExpect(jsonPath("$.[*].z99921").value(hasItem(sameNumber(DEFAULT_Z_99921))));
    }
}
