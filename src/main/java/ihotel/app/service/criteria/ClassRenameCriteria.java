package ihotel.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ihotel.app.domain.ClassRename} entity. This class is used
 * in {@link ihotel.app.web.rest.ClassRenameResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /class-renames?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClassRenameCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dt;

    private StringFilter empn;

    private BigDecimalFilter oldmoney;

    private BigDecimalFilter getmoney;

    private BigDecimalFilter toup;

    private StringFilter downempn;

    private BigDecimalFilter todown;

    private LongFilter flag;

    private BigDecimalFilter old2;

    private BigDecimalFilter get2;

    private BigDecimalFilter toup2;

    private BigDecimalFilter todown2;

    private StringFilter upempn2;

    private BigDecimalFilter im9008;

    private BigDecimalFilter im9009;

    private BigDecimalFilter co9991;

    private BigDecimalFilter co9992;

    private BigDecimalFilter co9993;

    private BigDecimalFilter co9994;

    private BigDecimalFilter co9995;

    private BigDecimalFilter co9998;

    private BigDecimalFilter im9007;

    private InstantFilter gotime;

    private BigDecimalFilter co9999;

    private BigDecimalFilter cm9008;

    private BigDecimalFilter cm9009;

    private BigDecimalFilter co99910;

    private StringFilter checkSign;

    private StringFilter classPb;

    private BigDecimalFilter ck;

    private BigDecimalFilter dk;

    private InstantFilter sjrq;

    private InstantFilter qsjrq;

    private BigDecimalFilter byje;

    private StringFilter xfcw;

    private StringFilter hoteldm;

    private LongFilter isnew;

    private BigDecimalFilter co99912;

    private BigDecimalFilter xj;

    private StringFilter classname;

    private BigDecimalFilter co9010;

    private BigDecimalFilter co9012;

    private BigDecimalFilter co9013;

    private BigDecimalFilter co9014;

    private BigDecimalFilter co99915;

    private BigDecimalFilter hyxj;

    private BigDecimalFilter hysk;

    private BigDecimalFilter hyqt;

    private BigDecimalFilter hkxj;

    private BigDecimalFilter hksk;

    private BigDecimalFilter hkqt;

    private BigDecimalFilter qtwt;

    private BigDecimalFilter qtysq;

    private BigDecimalFilter bbysj;

    private BigDecimalFilter zfbje;

    private BigDecimalFilter wxje;

    private BigDecimalFilter w99920;

    private BigDecimalFilter z99921;

    public ClassRenameCriteria() {}

    public ClassRenameCriteria(ClassRenameCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dt = other.dt == null ? null : other.dt.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.oldmoney = other.oldmoney == null ? null : other.oldmoney.copy();
        this.getmoney = other.getmoney == null ? null : other.getmoney.copy();
        this.toup = other.toup == null ? null : other.toup.copy();
        this.downempn = other.downempn == null ? null : other.downempn.copy();
        this.todown = other.todown == null ? null : other.todown.copy();
        this.flag = other.flag == null ? null : other.flag.copy();
        this.old2 = other.old2 == null ? null : other.old2.copy();
        this.get2 = other.get2 == null ? null : other.get2.copy();
        this.toup2 = other.toup2 == null ? null : other.toup2.copy();
        this.todown2 = other.todown2 == null ? null : other.todown2.copy();
        this.upempn2 = other.upempn2 == null ? null : other.upempn2.copy();
        this.im9008 = other.im9008 == null ? null : other.im9008.copy();
        this.im9009 = other.im9009 == null ? null : other.im9009.copy();
        this.co9991 = other.co9991 == null ? null : other.co9991.copy();
        this.co9992 = other.co9992 == null ? null : other.co9992.copy();
        this.co9993 = other.co9993 == null ? null : other.co9993.copy();
        this.co9994 = other.co9994 == null ? null : other.co9994.copy();
        this.co9995 = other.co9995 == null ? null : other.co9995.copy();
        this.co9998 = other.co9998 == null ? null : other.co9998.copy();
        this.im9007 = other.im9007 == null ? null : other.im9007.copy();
        this.gotime = other.gotime == null ? null : other.gotime.copy();
        this.co9999 = other.co9999 == null ? null : other.co9999.copy();
        this.cm9008 = other.cm9008 == null ? null : other.cm9008.copy();
        this.cm9009 = other.cm9009 == null ? null : other.cm9009.copy();
        this.co99910 = other.co99910 == null ? null : other.co99910.copy();
        this.checkSign = other.checkSign == null ? null : other.checkSign.copy();
        this.classPb = other.classPb == null ? null : other.classPb.copy();
        this.ck = other.ck == null ? null : other.ck.copy();
        this.dk = other.dk == null ? null : other.dk.copy();
        this.sjrq = other.sjrq == null ? null : other.sjrq.copy();
        this.qsjrq = other.qsjrq == null ? null : other.qsjrq.copy();
        this.byje = other.byje == null ? null : other.byje.copy();
        this.xfcw = other.xfcw == null ? null : other.xfcw.copy();
        this.hoteldm = other.hoteldm == null ? null : other.hoteldm.copy();
        this.isnew = other.isnew == null ? null : other.isnew.copy();
        this.co99912 = other.co99912 == null ? null : other.co99912.copy();
        this.xj = other.xj == null ? null : other.xj.copy();
        this.classname = other.classname == null ? null : other.classname.copy();
        this.co9010 = other.co9010 == null ? null : other.co9010.copy();
        this.co9012 = other.co9012 == null ? null : other.co9012.copy();
        this.co9013 = other.co9013 == null ? null : other.co9013.copy();
        this.co9014 = other.co9014 == null ? null : other.co9014.copy();
        this.co99915 = other.co99915 == null ? null : other.co99915.copy();
        this.hyxj = other.hyxj == null ? null : other.hyxj.copy();
        this.hysk = other.hysk == null ? null : other.hysk.copy();
        this.hyqt = other.hyqt == null ? null : other.hyqt.copy();
        this.hkxj = other.hkxj == null ? null : other.hkxj.copy();
        this.hksk = other.hksk == null ? null : other.hksk.copy();
        this.hkqt = other.hkqt == null ? null : other.hkqt.copy();
        this.qtwt = other.qtwt == null ? null : other.qtwt.copy();
        this.qtysq = other.qtysq == null ? null : other.qtysq.copy();
        this.bbysj = other.bbysj == null ? null : other.bbysj.copy();
        this.zfbje = other.zfbje == null ? null : other.zfbje.copy();
        this.wxje = other.wxje == null ? null : other.wxje.copy();
        this.w99920 = other.w99920 == null ? null : other.w99920.copy();
        this.z99921 = other.z99921 == null ? null : other.z99921.copy();
    }

    @Override
    public ClassRenameCriteria copy() {
        return new ClassRenameCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getDt() {
        return dt;
    }

    public InstantFilter dt() {
        if (dt == null) {
            dt = new InstantFilter();
        }
        return dt;
    }

    public void setDt(InstantFilter dt) {
        this.dt = dt;
    }

    public StringFilter getEmpn() {
        return empn;
    }

    public StringFilter empn() {
        if (empn == null) {
            empn = new StringFilter();
        }
        return empn;
    }

    public void setEmpn(StringFilter empn) {
        this.empn = empn;
    }

    public BigDecimalFilter getOldmoney() {
        return oldmoney;
    }

    public BigDecimalFilter oldmoney() {
        if (oldmoney == null) {
            oldmoney = new BigDecimalFilter();
        }
        return oldmoney;
    }

    public void setOldmoney(BigDecimalFilter oldmoney) {
        this.oldmoney = oldmoney;
    }

    public BigDecimalFilter getGetmoney() {
        return getmoney;
    }

    public BigDecimalFilter getmoney() {
        if (getmoney == null) {
            getmoney = new BigDecimalFilter();
        }
        return getmoney;
    }

    public void setGetmoney(BigDecimalFilter getmoney) {
        this.getmoney = getmoney;
    }

    public BigDecimalFilter getToup() {
        return toup;
    }

    public BigDecimalFilter toup() {
        if (toup == null) {
            toup = new BigDecimalFilter();
        }
        return toup;
    }

    public void setToup(BigDecimalFilter toup) {
        this.toup = toup;
    }

    public StringFilter getDownempn() {
        return downempn;
    }

    public StringFilter downempn() {
        if (downempn == null) {
            downempn = new StringFilter();
        }
        return downempn;
    }

    public void setDownempn(StringFilter downempn) {
        this.downempn = downempn;
    }

    public BigDecimalFilter getTodown() {
        return todown;
    }

    public BigDecimalFilter todown() {
        if (todown == null) {
            todown = new BigDecimalFilter();
        }
        return todown;
    }

    public void setTodown(BigDecimalFilter todown) {
        this.todown = todown;
    }

    public LongFilter getFlag() {
        return flag;
    }

    public LongFilter flag() {
        if (flag == null) {
            flag = new LongFilter();
        }
        return flag;
    }

    public void setFlag(LongFilter flag) {
        this.flag = flag;
    }

    public BigDecimalFilter getOld2() {
        return old2;
    }

    public BigDecimalFilter old2() {
        if (old2 == null) {
            old2 = new BigDecimalFilter();
        }
        return old2;
    }

    public void setOld2(BigDecimalFilter old2) {
        this.old2 = old2;
    }

    public BigDecimalFilter getGet2() {
        return get2;
    }

    public BigDecimalFilter get2() {
        if (get2 == null) {
            get2 = new BigDecimalFilter();
        }
        return get2;
    }

    public void setGet2(BigDecimalFilter get2) {
        this.get2 = get2;
    }

    public BigDecimalFilter getToup2() {
        return toup2;
    }

    public BigDecimalFilter toup2() {
        if (toup2 == null) {
            toup2 = new BigDecimalFilter();
        }
        return toup2;
    }

    public void setToup2(BigDecimalFilter toup2) {
        this.toup2 = toup2;
    }

    public BigDecimalFilter getTodown2() {
        return todown2;
    }

    public BigDecimalFilter todown2() {
        if (todown2 == null) {
            todown2 = new BigDecimalFilter();
        }
        return todown2;
    }

    public void setTodown2(BigDecimalFilter todown2) {
        this.todown2 = todown2;
    }

    public StringFilter getUpempn2() {
        return upempn2;
    }

    public StringFilter upempn2() {
        if (upempn2 == null) {
            upempn2 = new StringFilter();
        }
        return upempn2;
    }

    public void setUpempn2(StringFilter upempn2) {
        this.upempn2 = upempn2;
    }

    public BigDecimalFilter getIm9008() {
        return im9008;
    }

    public BigDecimalFilter im9008() {
        if (im9008 == null) {
            im9008 = new BigDecimalFilter();
        }
        return im9008;
    }

    public void setIm9008(BigDecimalFilter im9008) {
        this.im9008 = im9008;
    }

    public BigDecimalFilter getIm9009() {
        return im9009;
    }

    public BigDecimalFilter im9009() {
        if (im9009 == null) {
            im9009 = new BigDecimalFilter();
        }
        return im9009;
    }

    public void setIm9009(BigDecimalFilter im9009) {
        this.im9009 = im9009;
    }

    public BigDecimalFilter getCo9991() {
        return co9991;
    }

    public BigDecimalFilter co9991() {
        if (co9991 == null) {
            co9991 = new BigDecimalFilter();
        }
        return co9991;
    }

    public void setCo9991(BigDecimalFilter co9991) {
        this.co9991 = co9991;
    }

    public BigDecimalFilter getCo9992() {
        return co9992;
    }

    public BigDecimalFilter co9992() {
        if (co9992 == null) {
            co9992 = new BigDecimalFilter();
        }
        return co9992;
    }

    public void setCo9992(BigDecimalFilter co9992) {
        this.co9992 = co9992;
    }

    public BigDecimalFilter getCo9993() {
        return co9993;
    }

    public BigDecimalFilter co9993() {
        if (co9993 == null) {
            co9993 = new BigDecimalFilter();
        }
        return co9993;
    }

    public void setCo9993(BigDecimalFilter co9993) {
        this.co9993 = co9993;
    }

    public BigDecimalFilter getCo9994() {
        return co9994;
    }

    public BigDecimalFilter co9994() {
        if (co9994 == null) {
            co9994 = new BigDecimalFilter();
        }
        return co9994;
    }

    public void setCo9994(BigDecimalFilter co9994) {
        this.co9994 = co9994;
    }

    public BigDecimalFilter getCo9995() {
        return co9995;
    }

    public BigDecimalFilter co9995() {
        if (co9995 == null) {
            co9995 = new BigDecimalFilter();
        }
        return co9995;
    }

    public void setCo9995(BigDecimalFilter co9995) {
        this.co9995 = co9995;
    }

    public BigDecimalFilter getCo9998() {
        return co9998;
    }

    public BigDecimalFilter co9998() {
        if (co9998 == null) {
            co9998 = new BigDecimalFilter();
        }
        return co9998;
    }

    public void setCo9998(BigDecimalFilter co9998) {
        this.co9998 = co9998;
    }

    public BigDecimalFilter getIm9007() {
        return im9007;
    }

    public BigDecimalFilter im9007() {
        if (im9007 == null) {
            im9007 = new BigDecimalFilter();
        }
        return im9007;
    }

    public void setIm9007(BigDecimalFilter im9007) {
        this.im9007 = im9007;
    }

    public InstantFilter getGotime() {
        return gotime;
    }

    public InstantFilter gotime() {
        if (gotime == null) {
            gotime = new InstantFilter();
        }
        return gotime;
    }

    public void setGotime(InstantFilter gotime) {
        this.gotime = gotime;
    }

    public BigDecimalFilter getCo9999() {
        return co9999;
    }

    public BigDecimalFilter co9999() {
        if (co9999 == null) {
            co9999 = new BigDecimalFilter();
        }
        return co9999;
    }

    public void setCo9999(BigDecimalFilter co9999) {
        this.co9999 = co9999;
    }

    public BigDecimalFilter getCm9008() {
        return cm9008;
    }

    public BigDecimalFilter cm9008() {
        if (cm9008 == null) {
            cm9008 = new BigDecimalFilter();
        }
        return cm9008;
    }

    public void setCm9008(BigDecimalFilter cm9008) {
        this.cm9008 = cm9008;
    }

    public BigDecimalFilter getCm9009() {
        return cm9009;
    }

    public BigDecimalFilter cm9009() {
        if (cm9009 == null) {
            cm9009 = new BigDecimalFilter();
        }
        return cm9009;
    }

    public void setCm9009(BigDecimalFilter cm9009) {
        this.cm9009 = cm9009;
    }

    public BigDecimalFilter getCo99910() {
        return co99910;
    }

    public BigDecimalFilter co99910() {
        if (co99910 == null) {
            co99910 = new BigDecimalFilter();
        }
        return co99910;
    }

    public void setCo99910(BigDecimalFilter co99910) {
        this.co99910 = co99910;
    }

    public StringFilter getCheckSign() {
        return checkSign;
    }

    public StringFilter checkSign() {
        if (checkSign == null) {
            checkSign = new StringFilter();
        }
        return checkSign;
    }

    public void setCheckSign(StringFilter checkSign) {
        this.checkSign = checkSign;
    }

    public StringFilter getClassPb() {
        return classPb;
    }

    public StringFilter classPb() {
        if (classPb == null) {
            classPb = new StringFilter();
        }
        return classPb;
    }

    public void setClassPb(StringFilter classPb) {
        this.classPb = classPb;
    }

    public BigDecimalFilter getCk() {
        return ck;
    }

    public BigDecimalFilter ck() {
        if (ck == null) {
            ck = new BigDecimalFilter();
        }
        return ck;
    }

    public void setCk(BigDecimalFilter ck) {
        this.ck = ck;
    }

    public BigDecimalFilter getDk() {
        return dk;
    }

    public BigDecimalFilter dk() {
        if (dk == null) {
            dk = new BigDecimalFilter();
        }
        return dk;
    }

    public void setDk(BigDecimalFilter dk) {
        this.dk = dk;
    }

    public InstantFilter getSjrq() {
        return sjrq;
    }

    public InstantFilter sjrq() {
        if (sjrq == null) {
            sjrq = new InstantFilter();
        }
        return sjrq;
    }

    public void setSjrq(InstantFilter sjrq) {
        this.sjrq = sjrq;
    }

    public InstantFilter getQsjrq() {
        return qsjrq;
    }

    public InstantFilter qsjrq() {
        if (qsjrq == null) {
            qsjrq = new InstantFilter();
        }
        return qsjrq;
    }

    public void setQsjrq(InstantFilter qsjrq) {
        this.qsjrq = qsjrq;
    }

    public BigDecimalFilter getByje() {
        return byje;
    }

    public BigDecimalFilter byje() {
        if (byje == null) {
            byje = new BigDecimalFilter();
        }
        return byje;
    }

    public void setByje(BigDecimalFilter byje) {
        this.byje = byje;
    }

    public StringFilter getXfcw() {
        return xfcw;
    }

    public StringFilter xfcw() {
        if (xfcw == null) {
            xfcw = new StringFilter();
        }
        return xfcw;
    }

    public void setXfcw(StringFilter xfcw) {
        this.xfcw = xfcw;
    }

    public StringFilter getHoteldm() {
        return hoteldm;
    }

    public StringFilter hoteldm() {
        if (hoteldm == null) {
            hoteldm = new StringFilter();
        }
        return hoteldm;
    }

    public void setHoteldm(StringFilter hoteldm) {
        this.hoteldm = hoteldm;
    }

    public LongFilter getIsnew() {
        return isnew;
    }

    public LongFilter isnew() {
        if (isnew == null) {
            isnew = new LongFilter();
        }
        return isnew;
    }

    public void setIsnew(LongFilter isnew) {
        this.isnew = isnew;
    }

    public BigDecimalFilter getCo99912() {
        return co99912;
    }

    public BigDecimalFilter co99912() {
        if (co99912 == null) {
            co99912 = new BigDecimalFilter();
        }
        return co99912;
    }

    public void setCo99912(BigDecimalFilter co99912) {
        this.co99912 = co99912;
    }

    public BigDecimalFilter getXj() {
        return xj;
    }

    public BigDecimalFilter xj() {
        if (xj == null) {
            xj = new BigDecimalFilter();
        }
        return xj;
    }

    public void setXj(BigDecimalFilter xj) {
        this.xj = xj;
    }

    public StringFilter getClassname() {
        return classname;
    }

    public StringFilter classname() {
        if (classname == null) {
            classname = new StringFilter();
        }
        return classname;
    }

    public void setClassname(StringFilter classname) {
        this.classname = classname;
    }

    public BigDecimalFilter getCo9010() {
        return co9010;
    }

    public BigDecimalFilter co9010() {
        if (co9010 == null) {
            co9010 = new BigDecimalFilter();
        }
        return co9010;
    }

    public void setCo9010(BigDecimalFilter co9010) {
        this.co9010 = co9010;
    }

    public BigDecimalFilter getCo9012() {
        return co9012;
    }

    public BigDecimalFilter co9012() {
        if (co9012 == null) {
            co9012 = new BigDecimalFilter();
        }
        return co9012;
    }

    public void setCo9012(BigDecimalFilter co9012) {
        this.co9012 = co9012;
    }

    public BigDecimalFilter getCo9013() {
        return co9013;
    }

    public BigDecimalFilter co9013() {
        if (co9013 == null) {
            co9013 = new BigDecimalFilter();
        }
        return co9013;
    }

    public void setCo9013(BigDecimalFilter co9013) {
        this.co9013 = co9013;
    }

    public BigDecimalFilter getCo9014() {
        return co9014;
    }

    public BigDecimalFilter co9014() {
        if (co9014 == null) {
            co9014 = new BigDecimalFilter();
        }
        return co9014;
    }

    public void setCo9014(BigDecimalFilter co9014) {
        this.co9014 = co9014;
    }

    public BigDecimalFilter getCo99915() {
        return co99915;
    }

    public BigDecimalFilter co99915() {
        if (co99915 == null) {
            co99915 = new BigDecimalFilter();
        }
        return co99915;
    }

    public void setCo99915(BigDecimalFilter co99915) {
        this.co99915 = co99915;
    }

    public BigDecimalFilter getHyxj() {
        return hyxj;
    }

    public BigDecimalFilter hyxj() {
        if (hyxj == null) {
            hyxj = new BigDecimalFilter();
        }
        return hyxj;
    }

    public void setHyxj(BigDecimalFilter hyxj) {
        this.hyxj = hyxj;
    }

    public BigDecimalFilter getHysk() {
        return hysk;
    }

    public BigDecimalFilter hysk() {
        if (hysk == null) {
            hysk = new BigDecimalFilter();
        }
        return hysk;
    }

    public void setHysk(BigDecimalFilter hysk) {
        this.hysk = hysk;
    }

    public BigDecimalFilter getHyqt() {
        return hyqt;
    }

    public BigDecimalFilter hyqt() {
        if (hyqt == null) {
            hyqt = new BigDecimalFilter();
        }
        return hyqt;
    }

    public void setHyqt(BigDecimalFilter hyqt) {
        this.hyqt = hyqt;
    }

    public BigDecimalFilter getHkxj() {
        return hkxj;
    }

    public BigDecimalFilter hkxj() {
        if (hkxj == null) {
            hkxj = new BigDecimalFilter();
        }
        return hkxj;
    }

    public void setHkxj(BigDecimalFilter hkxj) {
        this.hkxj = hkxj;
    }

    public BigDecimalFilter getHksk() {
        return hksk;
    }

    public BigDecimalFilter hksk() {
        if (hksk == null) {
            hksk = new BigDecimalFilter();
        }
        return hksk;
    }

    public void setHksk(BigDecimalFilter hksk) {
        this.hksk = hksk;
    }

    public BigDecimalFilter getHkqt() {
        return hkqt;
    }

    public BigDecimalFilter hkqt() {
        if (hkqt == null) {
            hkqt = new BigDecimalFilter();
        }
        return hkqt;
    }

    public void setHkqt(BigDecimalFilter hkqt) {
        this.hkqt = hkqt;
    }

    public BigDecimalFilter getQtwt() {
        return qtwt;
    }

    public BigDecimalFilter qtwt() {
        if (qtwt == null) {
            qtwt = new BigDecimalFilter();
        }
        return qtwt;
    }

    public void setQtwt(BigDecimalFilter qtwt) {
        this.qtwt = qtwt;
    }

    public BigDecimalFilter getQtysq() {
        return qtysq;
    }

    public BigDecimalFilter qtysq() {
        if (qtysq == null) {
            qtysq = new BigDecimalFilter();
        }
        return qtysq;
    }

    public void setQtysq(BigDecimalFilter qtysq) {
        this.qtysq = qtysq;
    }

    public BigDecimalFilter getBbysj() {
        return bbysj;
    }

    public BigDecimalFilter bbysj() {
        if (bbysj == null) {
            bbysj = new BigDecimalFilter();
        }
        return bbysj;
    }

    public void setBbysj(BigDecimalFilter bbysj) {
        this.bbysj = bbysj;
    }

    public BigDecimalFilter getZfbje() {
        return zfbje;
    }

    public BigDecimalFilter zfbje() {
        if (zfbje == null) {
            zfbje = new BigDecimalFilter();
        }
        return zfbje;
    }

    public void setZfbje(BigDecimalFilter zfbje) {
        this.zfbje = zfbje;
    }

    public BigDecimalFilter getWxje() {
        return wxje;
    }

    public BigDecimalFilter wxje() {
        if (wxje == null) {
            wxje = new BigDecimalFilter();
        }
        return wxje;
    }

    public void setWxje(BigDecimalFilter wxje) {
        this.wxje = wxje;
    }

    public BigDecimalFilter getw99920() {
        return w99920;
    }

    public BigDecimalFilter w99920() {
        if (w99920 == null) {
            w99920 = new BigDecimalFilter();
        }
        return w99920;
    }

    public void setw99920(BigDecimalFilter w99920) {
        this.w99920 = w99920;
    }

    public BigDecimalFilter getz99921() {
        return z99921;
    }

    public BigDecimalFilter z99921() {
        if (z99921 == null) {
            z99921 = new BigDecimalFilter();
        }
        return z99921;
    }

    public void setz99921(BigDecimalFilter z99921) {
        this.z99921 = z99921;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClassRenameCriteria that = (ClassRenameCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dt, that.dt) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(oldmoney, that.oldmoney) &&
            Objects.equals(getmoney, that.getmoney) &&
            Objects.equals(toup, that.toup) &&
            Objects.equals(downempn, that.downempn) &&
            Objects.equals(todown, that.todown) &&
            Objects.equals(flag, that.flag) &&
            Objects.equals(old2, that.old2) &&
            Objects.equals(get2, that.get2) &&
            Objects.equals(toup2, that.toup2) &&
            Objects.equals(todown2, that.todown2) &&
            Objects.equals(upempn2, that.upempn2) &&
            Objects.equals(im9008, that.im9008) &&
            Objects.equals(im9009, that.im9009) &&
            Objects.equals(co9991, that.co9991) &&
            Objects.equals(co9992, that.co9992) &&
            Objects.equals(co9993, that.co9993) &&
            Objects.equals(co9994, that.co9994) &&
            Objects.equals(co9995, that.co9995) &&
            Objects.equals(co9998, that.co9998) &&
            Objects.equals(im9007, that.im9007) &&
            Objects.equals(gotime, that.gotime) &&
            Objects.equals(co9999, that.co9999) &&
            Objects.equals(cm9008, that.cm9008) &&
            Objects.equals(cm9009, that.cm9009) &&
            Objects.equals(co99910, that.co99910) &&
            Objects.equals(checkSign, that.checkSign) &&
            Objects.equals(classPb, that.classPb) &&
            Objects.equals(ck, that.ck) &&
            Objects.equals(dk, that.dk) &&
            Objects.equals(sjrq, that.sjrq) &&
            Objects.equals(qsjrq, that.qsjrq) &&
            Objects.equals(byje, that.byje) &&
            Objects.equals(xfcw, that.xfcw) &&
            Objects.equals(hoteldm, that.hoteldm) &&
            Objects.equals(isnew, that.isnew) &&
            Objects.equals(co99912, that.co99912) &&
            Objects.equals(xj, that.xj) &&
            Objects.equals(classname, that.classname) &&
            Objects.equals(co9010, that.co9010) &&
            Objects.equals(co9012, that.co9012) &&
            Objects.equals(co9013, that.co9013) &&
            Objects.equals(co9014, that.co9014) &&
            Objects.equals(co99915, that.co99915) &&
            Objects.equals(hyxj, that.hyxj) &&
            Objects.equals(hysk, that.hysk) &&
            Objects.equals(hyqt, that.hyqt) &&
            Objects.equals(hkxj, that.hkxj) &&
            Objects.equals(hksk, that.hksk) &&
            Objects.equals(hkqt, that.hkqt) &&
            Objects.equals(qtwt, that.qtwt) &&
            Objects.equals(qtysq, that.qtysq) &&
            Objects.equals(bbysj, that.bbysj) &&
            Objects.equals(zfbje, that.zfbje) &&
            Objects.equals(wxje, that.wxje) &&
            Objects.equals(w99920, that.w99920) &&
            Objects.equals(z99921, that.z99921)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            dt,
            empn,
            oldmoney,
            getmoney,
            toup,
            downempn,
            todown,
            flag,
            old2,
            get2,
            toup2,
            todown2,
            upempn2,
            im9008,
            im9009,
            co9991,
            co9992,
            co9993,
            co9994,
            co9995,
            co9998,
            im9007,
            gotime,
            co9999,
            cm9008,
            cm9009,
            co99910,
            checkSign,
            classPb,
            ck,
            dk,
            sjrq,
            qsjrq,
            byje,
            xfcw,
            hoteldm,
            isnew,
            co99912,
            xj,
            classname,
            co9010,
            co9012,
            co9013,
            co9014,
            co99915,
            hyxj,
            hysk,
            hyqt,
            hkxj,
            hksk,
            hkqt,
            qtwt,
            qtysq,
            bbysj,
            zfbje,
            wxje,
            w99920,
            z99921
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassRenameCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dt != null ? "dt=" + dt + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (oldmoney != null ? "oldmoney=" + oldmoney + ", " : "") +
            (getmoney != null ? "getmoney=" + getmoney + ", " : "") +
            (toup != null ? "toup=" + toup + ", " : "") +
            (downempn != null ? "downempn=" + downempn + ", " : "") +
            (todown != null ? "todown=" + todown + ", " : "") +
            (flag != null ? "flag=" + flag + ", " : "") +
            (old2 != null ? "old2=" + old2 + ", " : "") +
            (get2 != null ? "get2=" + get2 + ", " : "") +
            (toup2 != null ? "toup2=" + toup2 + ", " : "") +
            (todown2 != null ? "todown2=" + todown2 + ", " : "") +
            (upempn2 != null ? "upempn2=" + upempn2 + ", " : "") +
            (im9008 != null ? "im9008=" + im9008 + ", " : "") +
            (im9009 != null ? "im9009=" + im9009 + ", " : "") +
            (co9991 != null ? "co9991=" + co9991 + ", " : "") +
            (co9992 != null ? "co9992=" + co9992 + ", " : "") +
            (co9993 != null ? "co9993=" + co9993 + ", " : "") +
            (co9994 != null ? "co9994=" + co9994 + ", " : "") +
            (co9995 != null ? "co9995=" + co9995 + ", " : "") +
            (co9998 != null ? "co9998=" + co9998 + ", " : "") +
            (im9007 != null ? "im9007=" + im9007 + ", " : "") +
            (gotime != null ? "gotime=" + gotime + ", " : "") +
            (co9999 != null ? "co9999=" + co9999 + ", " : "") +
            (cm9008 != null ? "cm9008=" + cm9008 + ", " : "") +
            (cm9009 != null ? "cm9009=" + cm9009 + ", " : "") +
            (co99910 != null ? "co99910=" + co99910 + ", " : "") +
            (checkSign != null ? "checkSign=" + checkSign + ", " : "") +
            (classPb != null ? "classPb=" + classPb + ", " : "") +
            (ck != null ? "ck=" + ck + ", " : "") +
            (dk != null ? "dk=" + dk + ", " : "") +
            (sjrq != null ? "sjrq=" + sjrq + ", " : "") +
            (qsjrq != null ? "qsjrq=" + qsjrq + ", " : "") +
            (byje != null ? "byje=" + byje + ", " : "") +
            (xfcw != null ? "xfcw=" + xfcw + ", " : "") +
            (hoteldm != null ? "hoteldm=" + hoteldm + ", " : "") +
            (isnew != null ? "isnew=" + isnew + ", " : "") +
            (co99912 != null ? "co99912=" + co99912 + ", " : "") +
            (xj != null ? "xj=" + xj + ", " : "") +
            (classname != null ? "classname=" + classname + ", " : "") +
            (co9010 != null ? "co9010=" + co9010 + ", " : "") +
            (co9012 != null ? "co9012=" + co9012 + ", " : "") +
            (co9013 != null ? "co9013=" + co9013 + ", " : "") +
            (co9014 != null ? "co9014=" + co9014 + ", " : "") +
            (co99915 != null ? "co99915=" + co99915 + ", " : "") +
            (hyxj != null ? "hyxj=" + hyxj + ", " : "") +
            (hysk != null ? "hysk=" + hysk + ", " : "") +
            (hyqt != null ? "hyqt=" + hyqt + ", " : "") +
            (hkxj != null ? "hkxj=" + hkxj + ", " : "") +
            (hksk != null ? "hksk=" + hksk + ", " : "") +
            (hkqt != null ? "hkqt=" + hkqt + ", " : "") +
            (qtwt != null ? "qtwt=" + qtwt + ", " : "") +
            (qtysq != null ? "qtysq=" + qtysq + ", " : "") +
            (bbysj != null ? "bbysj=" + bbysj + ", " : "") +
            (zfbje != null ? "zfbje=" + zfbje + ", " : "") +
            (wxje != null ? "wxje=" + wxje + ", " : "") +
            (w99920 != null ? "w99920=" + w99920 + ", " : "") +
            (z99921 != null ? "z99921=" + z99921 + ", " : "") +
            "}";
    }
}
