package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A ClassRename.
 */
@Entity
@Table(name = "class_rename")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "classrename")
public class ClassRename implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dt", nullable = false)
    private Instant dt;

    @NotNull
    @Size(max = 10)
    @Column(name = "empn", length = 10, nullable = false)
    private String empn;

    @Column(name = "oldmoney", precision = 21, scale = 2)
    private BigDecimal oldmoney;

    @Column(name = "getmoney", precision = 21, scale = 2)
    private BigDecimal getmoney;

    @Column(name = "toup", precision = 21, scale = 2)
    private BigDecimal toup;

    @Size(max = 10)
    @Column(name = "downempn", length = 10)
    private String downempn;

    @Column(name = "todown", precision = 21, scale = 2)
    private BigDecimal todown;

    @Column(name = "flag")
    private Long flag;

    @Column(name = "old_2", precision = 21, scale = 2)
    private BigDecimal old2;

    @Column(name = "get_2", precision = 21, scale = 2)
    private BigDecimal get2;

    @Column(name = "toup_2", precision = 21, scale = 2)
    private BigDecimal toup2;

    @Column(name = "todown_2", precision = 21, scale = 2)
    private BigDecimal todown2;

    @Size(max = 10)
    @Column(name = "upempn_2", length = 10)
    private String upempn2;

    /**
     * 收现金押金
     */
    @Column(name = "im_9008", precision = 21, scale = 2)
    private BigDecimal im9008;

    /**
     * 收刷卡押金
     */
    @Column(name = "im_9009", precision = 21, scale = 2)
    private BigDecimal im9009;

    /**
     * 现金收款
     */
    @Column(name = "co_9991", precision = 21, scale = 2)
    private BigDecimal co9991;

    /**
     * 刷卡收款
     */
    @Column(name = "co_9992", precision = 21, scale = 2)
    private BigDecimal co9992;

    /**
     * 免单收款
     */
    @Column(name = "co_9993", precision = 21, scale = 2)
    private BigDecimal co9993;

    /**
     * 会员卡收款
     */
    @Column(name = "co_9994", precision = 21, scale = 2)
    private BigDecimal co9994;

    /**
     * 挂账
     */
    @Column(name = "co_9995", precision = 21, scale = 2)
    private BigDecimal co9995;

    @Column(name = "co_9998", precision = 21, scale = 2)
    private BigDecimal co9998;

    @Column(name = "im_9007", precision = 21, scale = 2)
    private BigDecimal im9007;

    @Column(name = "gotime")
    private Instant gotime;

    @Column(name = "co_9999", precision = 21, scale = 2)
    private BigDecimal co9999;

    /**
     * 退现押金
     */
    @Column(name = "cm_9008", precision = 21, scale = 2)
    private BigDecimal cm9008;

    /**
     * 退刷卡押金
     */
    @Column(name = "cm_9009", precision = 21, scale = 2)
    private BigDecimal cm9009;

    @Column(name = "co_99910", precision = 21, scale = 2)
    private BigDecimal co99910;

    @Size(max = 2)
    @Column(name = "check_sign", length = 2)
    private String checkSign;

    @Size(max = 10)
    @Column(name = "class_pb", length = 10)
    private String classPb;

    @Column(name = "ck", precision = 21, scale = 2)
    private BigDecimal ck;

    @Column(name = "dk", precision = 21, scale = 2)
    private BigDecimal dk;

    @Column(name = "sjrq")
    private Instant sjrq;

    @Column(name = "qsjrq")
    private Instant qsjrq;

    @Column(name = "byje", precision = 21, scale = 2)
    private BigDecimal byje;

    @Size(max = 30)
    @Column(name = "xfcw", length = 30)
    private String xfcw;

    @Size(max = 20)
    @Column(name = "hoteldm", length = 20)
    private String hoteldm;

    @Column(name = "isnew")
    private Long isnew;

    @Column(name = "co_99912", precision = 21, scale = 2)
    private BigDecimal co99912;

    @Column(name = "xj", precision = 21, scale = 2)
    private BigDecimal xj;

    @Size(max = 50)
    @Column(name = "classname", length = 50)
    private String classname;

    @Column(name = "co_9010", precision = 21, scale = 2)
    private BigDecimal co9010;

    @Column(name = "co_9012", precision = 21, scale = 2)
    private BigDecimal co9012;

    @Column(name = "co_9013", precision = 21, scale = 2)
    private BigDecimal co9013;

    @Column(name = "co_9014", precision = 21, scale = 2)
    private BigDecimal co9014;

    @Column(name = "co_99915", precision = 21, scale = 2)
    private BigDecimal co99915;

    @Column(name = "hyxj", precision = 21, scale = 2)
    private BigDecimal hyxj;

    @Column(name = "hysk", precision = 21, scale = 2)
    private BigDecimal hysk;

    @Column(name = "hyqt", precision = 21, scale = 2)
    private BigDecimal hyqt;

    @Column(name = "hkxj", precision = 21, scale = 2)
    private BigDecimal hkxj;

    @Column(name = "hksk", precision = 21, scale = 2)
    private BigDecimal hksk;

    @Column(name = "hkqt", precision = 21, scale = 2)
    private BigDecimal hkqt;

    @Column(name = "qtwt", precision = 21, scale = 2)
    private BigDecimal qtwt;

    @Column(name = "qtysq", precision = 21, scale = 2)
    private BigDecimal qtysq;

    @Column(name = "bbysj", precision = 21, scale = 2)
    private BigDecimal bbysj;

    @Column(name = "zfbje", precision = 21, scale = 2)
    private BigDecimal zfbje;

    @Column(name = "wxje", precision = 21, scale = 2)
    private BigDecimal wxje;

    @Column(name = "w_99920", precision = 21, scale = 2)
    private BigDecimal w99920;

    @Column(name = "z_99921", precision = 21, scale = 2)
    private BigDecimal z99921;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassRename id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getDt() {
        return this.dt;
    }

    public ClassRename dt(Instant dt) {
        this.dt = dt;
        return this;
    }

    public void setDt(Instant dt) {
        this.dt = dt;
    }

    public String getEmpn() {
        return this.empn;
    }

    public ClassRename empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public BigDecimal getOldmoney() {
        return this.oldmoney;
    }

    public ClassRename oldmoney(BigDecimal oldmoney) {
        this.oldmoney = oldmoney;
        return this;
    }

    public void setOldmoney(BigDecimal oldmoney) {
        this.oldmoney = oldmoney;
    }

    public BigDecimal getGetmoney() {
        return this.getmoney;
    }

    public ClassRename getmoney(BigDecimal getmoney) {
        this.getmoney = getmoney;
        return this;
    }

    public void setGetmoney(BigDecimal getmoney) {
        this.getmoney = getmoney;
    }

    public BigDecimal getToup() {
        return this.toup;
    }

    public ClassRename toup(BigDecimal toup) {
        this.toup = toup;
        return this;
    }

    public void setToup(BigDecimal toup) {
        this.toup = toup;
    }

    public String getDownempn() {
        return this.downempn;
    }

    public ClassRename downempn(String downempn) {
        this.downempn = downempn;
        return this;
    }

    public void setDownempn(String downempn) {
        this.downempn = downempn;
    }

    public BigDecimal getTodown() {
        return this.todown;
    }

    public ClassRename todown(BigDecimal todown) {
        this.todown = todown;
        return this;
    }

    public void setTodown(BigDecimal todown) {
        this.todown = todown;
    }

    public Long getFlag() {
        return this.flag;
    }

    public ClassRename flag(Long flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }

    public BigDecimal getOld2() {
        return this.old2;
    }

    public ClassRename old2(BigDecimal old2) {
        this.old2 = old2;
        return this;
    }

    public void setOld2(BigDecimal old2) {
        this.old2 = old2;
    }

    public BigDecimal getGet2() {
        return this.get2;
    }

    public ClassRename get2(BigDecimal get2) {
        this.get2 = get2;
        return this;
    }

    public void setGet2(BigDecimal get2) {
        this.get2 = get2;
    }

    public BigDecimal getToup2() {
        return this.toup2;
    }

    public ClassRename toup2(BigDecimal toup2) {
        this.toup2 = toup2;
        return this;
    }

    public void setToup2(BigDecimal toup2) {
        this.toup2 = toup2;
    }

    public BigDecimal getTodown2() {
        return this.todown2;
    }

    public ClassRename todown2(BigDecimal todown2) {
        this.todown2 = todown2;
        return this;
    }

    public void setTodown2(BigDecimal todown2) {
        this.todown2 = todown2;
    }

    public String getUpempn2() {
        return this.upempn2;
    }

    public ClassRename upempn2(String upempn2) {
        this.upempn2 = upempn2;
        return this;
    }

    public void setUpempn2(String upempn2) {
        this.upempn2 = upempn2;
    }

    public BigDecimal getIm9008() {
        return this.im9008;
    }

    public ClassRename im9008(BigDecimal im9008) {
        this.im9008 = im9008;
        return this;
    }

    public void setIm9008(BigDecimal im9008) {
        this.im9008 = im9008;
    }

    public BigDecimal getIm9009() {
        return this.im9009;
    }

    public ClassRename im9009(BigDecimal im9009) {
        this.im9009 = im9009;
        return this;
    }

    public void setIm9009(BigDecimal im9009) {
        this.im9009 = im9009;
    }

    public BigDecimal getCo9991() {
        return this.co9991;
    }

    public ClassRename co9991(BigDecimal co9991) {
        this.co9991 = co9991;
        return this;
    }

    public void setCo9991(BigDecimal co9991) {
        this.co9991 = co9991;
    }

    public BigDecimal getCo9992() {
        return this.co9992;
    }

    public ClassRename co9992(BigDecimal co9992) {
        this.co9992 = co9992;
        return this;
    }

    public void setCo9992(BigDecimal co9992) {
        this.co9992 = co9992;
    }

    public BigDecimal getCo9993() {
        return this.co9993;
    }

    public ClassRename co9993(BigDecimal co9993) {
        this.co9993 = co9993;
        return this;
    }

    public void setCo9993(BigDecimal co9993) {
        this.co9993 = co9993;
    }

    public BigDecimal getCo9994() {
        return this.co9994;
    }

    public ClassRename co9994(BigDecimal co9994) {
        this.co9994 = co9994;
        return this;
    }

    public void setCo9994(BigDecimal co9994) {
        this.co9994 = co9994;
    }

    public BigDecimal getCo9995() {
        return this.co9995;
    }

    public ClassRename co9995(BigDecimal co9995) {
        this.co9995 = co9995;
        return this;
    }

    public void setCo9995(BigDecimal co9995) {
        this.co9995 = co9995;
    }

    public BigDecimal getCo9998() {
        return this.co9998;
    }

    public ClassRename co9998(BigDecimal co9998) {
        this.co9998 = co9998;
        return this;
    }

    public void setCo9998(BigDecimal co9998) {
        this.co9998 = co9998;
    }

    public BigDecimal getIm9007() {
        return this.im9007;
    }

    public ClassRename im9007(BigDecimal im9007) {
        this.im9007 = im9007;
        return this;
    }

    public void setIm9007(BigDecimal im9007) {
        this.im9007 = im9007;
    }

    public Instant getGotime() {
        return this.gotime;
    }

    public ClassRename gotime(Instant gotime) {
        this.gotime = gotime;
        return this;
    }

    public void setGotime(Instant gotime) {
        this.gotime = gotime;
    }

    public BigDecimal getCo9999() {
        return this.co9999;
    }

    public ClassRename co9999(BigDecimal co9999) {
        this.co9999 = co9999;
        return this;
    }

    public void setCo9999(BigDecimal co9999) {
        this.co9999 = co9999;
    }

    public BigDecimal getCm9008() {
        return this.cm9008;
    }

    public ClassRename cm9008(BigDecimal cm9008) {
        this.cm9008 = cm9008;
        return this;
    }

    public void setCm9008(BigDecimal cm9008) {
        this.cm9008 = cm9008;
    }

    public BigDecimal getCm9009() {
        return this.cm9009;
    }

    public ClassRename cm9009(BigDecimal cm9009) {
        this.cm9009 = cm9009;
        return this;
    }

    public void setCm9009(BigDecimal cm9009) {
        this.cm9009 = cm9009;
    }

    public BigDecimal getCo99910() {
        return this.co99910;
    }

    public ClassRename co99910(BigDecimal co99910) {
        this.co99910 = co99910;
        return this;
    }

    public void setCo99910(BigDecimal co99910) {
        this.co99910 = co99910;
    }

    public String getCheckSign() {
        return this.checkSign;
    }

    public ClassRename checkSign(String checkSign) {
        this.checkSign = checkSign;
        return this;
    }

    public void setCheckSign(String checkSign) {
        this.checkSign = checkSign;
    }

    public String getClassPb() {
        return this.classPb;
    }

    public ClassRename classPb(String classPb) {
        this.classPb = classPb;
        return this;
    }

    public void setClassPb(String classPb) {
        this.classPb = classPb;
    }

    public BigDecimal getCk() {
        return this.ck;
    }

    public ClassRename ck(BigDecimal ck) {
        this.ck = ck;
        return this;
    }

    public void setCk(BigDecimal ck) {
        this.ck = ck;
    }

    public BigDecimal getDk() {
        return this.dk;
    }

    public ClassRename dk(BigDecimal dk) {
        this.dk = dk;
        return this;
    }

    public void setDk(BigDecimal dk) {
        this.dk = dk;
    }

    public Instant getSjrq() {
        return this.sjrq;
    }

    public ClassRename sjrq(Instant sjrq) {
        this.sjrq = sjrq;
        return this;
    }

    public void setSjrq(Instant sjrq) {
        this.sjrq = sjrq;
    }

    public Instant getQsjrq() {
        return this.qsjrq;
    }

    public ClassRename qsjrq(Instant qsjrq) {
        this.qsjrq = qsjrq;
        return this;
    }

    public void setQsjrq(Instant qsjrq) {
        this.qsjrq = qsjrq;
    }

    public BigDecimal getByje() {
        return this.byje;
    }

    public ClassRename byje(BigDecimal byje) {
        this.byje = byje;
        return this;
    }

    public void setByje(BigDecimal byje) {
        this.byje = byje;
    }

    public String getXfcw() {
        return this.xfcw;
    }

    public ClassRename xfcw(String xfcw) {
        this.xfcw = xfcw;
        return this;
    }

    public void setXfcw(String xfcw) {
        this.xfcw = xfcw;
    }

    public String getHoteldm() {
        return this.hoteldm;
    }

    public ClassRename hoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
        return this;
    }

    public void setHoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
    }

    public Long getIsnew() {
        return this.isnew;
    }

    public ClassRename isnew(Long isnew) {
        this.isnew = isnew;
        return this;
    }

    public void setIsnew(Long isnew) {
        this.isnew = isnew;
    }

    public BigDecimal getCo99912() {
        return this.co99912;
    }

    public ClassRename co99912(BigDecimal co99912) {
        this.co99912 = co99912;
        return this;
    }

    public void setCo99912(BigDecimal co99912) {
        this.co99912 = co99912;
    }

    public BigDecimal getXj() {
        return this.xj;
    }

    public ClassRename xj(BigDecimal xj) {
        this.xj = xj;
        return this;
    }

    public void setXj(BigDecimal xj) {
        this.xj = xj;
    }

    public String getClassname() {
        return this.classname;
    }

    public ClassRename classname(String classname) {
        this.classname = classname;
        return this;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public BigDecimal getCo9010() {
        return this.co9010;
    }

    public ClassRename co9010(BigDecimal co9010) {
        this.co9010 = co9010;
        return this;
    }

    public void setCo9010(BigDecimal co9010) {
        this.co9010 = co9010;
    }

    public BigDecimal getCo9012() {
        return this.co9012;
    }

    public ClassRename co9012(BigDecimal co9012) {
        this.co9012 = co9012;
        return this;
    }

    public void setCo9012(BigDecimal co9012) {
        this.co9012 = co9012;
    }

    public BigDecimal getCo9013() {
        return this.co9013;
    }

    public ClassRename co9013(BigDecimal co9013) {
        this.co9013 = co9013;
        return this;
    }

    public void setCo9013(BigDecimal co9013) {
        this.co9013 = co9013;
    }

    public BigDecimal getCo9014() {
        return this.co9014;
    }

    public ClassRename co9014(BigDecimal co9014) {
        this.co9014 = co9014;
        return this;
    }

    public void setCo9014(BigDecimal co9014) {
        this.co9014 = co9014;
    }

    public BigDecimal getCo99915() {
        return this.co99915;
    }

    public ClassRename co99915(BigDecimal co99915) {
        this.co99915 = co99915;
        return this;
    }

    public void setCo99915(BigDecimal co99915) {
        this.co99915 = co99915;
    }

    public BigDecimal getHyxj() {
        return this.hyxj;
    }

    public ClassRename hyxj(BigDecimal hyxj) {
        this.hyxj = hyxj;
        return this;
    }

    public void setHyxj(BigDecimal hyxj) {
        this.hyxj = hyxj;
    }

    public BigDecimal getHysk() {
        return this.hysk;
    }

    public ClassRename hysk(BigDecimal hysk) {
        this.hysk = hysk;
        return this;
    }

    public void setHysk(BigDecimal hysk) {
        this.hysk = hysk;
    }

    public BigDecimal getHyqt() {
        return this.hyqt;
    }

    public ClassRename hyqt(BigDecimal hyqt) {
        this.hyqt = hyqt;
        return this;
    }

    public void setHyqt(BigDecimal hyqt) {
        this.hyqt = hyqt;
    }

    public BigDecimal getHkxj() {
        return this.hkxj;
    }

    public ClassRename hkxj(BigDecimal hkxj) {
        this.hkxj = hkxj;
        return this;
    }

    public void setHkxj(BigDecimal hkxj) {
        this.hkxj = hkxj;
    }

    public BigDecimal getHksk() {
        return this.hksk;
    }

    public ClassRename hksk(BigDecimal hksk) {
        this.hksk = hksk;
        return this;
    }

    public void setHksk(BigDecimal hksk) {
        this.hksk = hksk;
    }

    public BigDecimal getHkqt() {
        return this.hkqt;
    }

    public ClassRename hkqt(BigDecimal hkqt) {
        this.hkqt = hkqt;
        return this;
    }

    public void setHkqt(BigDecimal hkqt) {
        this.hkqt = hkqt;
    }

    public BigDecimal getQtwt() {
        return this.qtwt;
    }

    public ClassRename qtwt(BigDecimal qtwt) {
        this.qtwt = qtwt;
        return this;
    }

    public void setQtwt(BigDecimal qtwt) {
        this.qtwt = qtwt;
    }

    public BigDecimal getQtysq() {
        return this.qtysq;
    }

    public ClassRename qtysq(BigDecimal qtysq) {
        this.qtysq = qtysq;
        return this;
    }

    public void setQtysq(BigDecimal qtysq) {
        this.qtysq = qtysq;
    }

    public BigDecimal getBbysj() {
        return this.bbysj;
    }

    public ClassRename bbysj(BigDecimal bbysj) {
        this.bbysj = bbysj;
        return this;
    }

    public void setBbysj(BigDecimal bbysj) {
        this.bbysj = bbysj;
    }

    public BigDecimal getZfbje() {
        return this.zfbje;
    }

    public ClassRename zfbje(BigDecimal zfbje) {
        this.zfbje = zfbje;
        return this;
    }

    public void setZfbje(BigDecimal zfbje) {
        this.zfbje = zfbje;
    }

    public BigDecimal getWxje() {
        return this.wxje;
    }

    public ClassRename wxje(BigDecimal wxje) {
        this.wxje = wxje;
        return this;
    }

    public void setWxje(BigDecimal wxje) {
        this.wxje = wxje;
    }

    public BigDecimal getw99920() {
        return this.w99920;
    }

    public ClassRename w99920(BigDecimal w99920) {
        this.w99920 = w99920;
        return this;
    }

    public void setw99920(BigDecimal w99920) {
        this.w99920 = w99920;
    }

    public BigDecimal getz99921() {
        return this.z99921;
    }

    public ClassRename z99921(BigDecimal z99921) {
        this.z99921 = z99921;
        return this;
    }

    public void setz99921(BigDecimal z99921) {
        this.z99921 = z99921;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassRename)) {
            return false;
        }
        return id != null && id.equals(((ClassRename) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassRename{" +
            "id=" + getId() +
            ", dt='" + getDt() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", oldmoney=" + getOldmoney() +
            ", getmoney=" + getGetmoney() +
            ", toup=" + getToup() +
            ", downempn='" + getDownempn() + "'" +
            ", todown=" + getTodown() +
            ", flag=" + getFlag() +
            ", old2=" + getOld2() +
            ", get2=" + getGet2() +
            ", toup2=" + getToup2() +
            ", todown2=" + getTodown2() +
            ", upempn2='" + getUpempn2() + "'" +
            ", im9008=" + getIm9008() +
            ", im9009=" + getIm9009() +
            ", co9991=" + getCo9991() +
            ", co9992=" + getCo9992() +
            ", co9993=" + getCo9993() +
            ", co9994=" + getCo9994() +
            ", co9995=" + getCo9995() +
            ", co9998=" + getCo9998() +
            ", im9007=" + getIm9007() +
            ", gotime='" + getGotime() + "'" +
            ", co9999=" + getCo9999() +
            ", cm9008=" + getCm9008() +
            ", cm9009=" + getCm9009() +
            ", co99910=" + getCo99910() +
            ", checkSign='" + getCheckSign() + "'" +
            ", classPb='" + getClassPb() + "'" +
            ", ck=" + getCk() +
            ", dk=" + getDk() +
            ", sjrq='" + getSjrq() + "'" +
            ", qsjrq='" + getQsjrq() + "'" +
            ", byje=" + getByje() +
            ", xfcw='" + getXfcw() + "'" +
            ", hoteldm='" + getHoteldm() + "'" +
            ", isnew=" + getIsnew() +
            ", co99912=" + getCo99912() +
            ", xj=" + getXj() +
            ", classname='" + getClassname() + "'" +
            ", co9010=" + getCo9010() +
            ", co9012=" + getCo9012() +
            ", co9013=" + getCo9013() +
            ", co9014=" + getCo9014() +
            ", co99915=" + getCo99915() +
            ", hyxj=" + getHyxj() +
            ", hysk=" + getHysk() +
            ", hyqt=" + getHyqt() +
            ", hkxj=" + getHkxj() +
            ", hksk=" + getHksk() +
            ", hkqt=" + getHkqt() +
            ", qtwt=" + getQtwt() +
            ", qtysq=" + getQtysq() +
            ", bbysj=" + getBbysj() +
            ", zfbje=" + getZfbje() +
            ", wxje=" + getWxje() +
            ", w99920=" + getw99920() +
            ", z99921=" + getz99921() +
            "}";
    }
}
