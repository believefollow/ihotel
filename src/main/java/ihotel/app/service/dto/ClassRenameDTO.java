package ihotel.app.service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.ClassRename} entity.
 */
public class ClassRenameDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant dt;

    @NotNull
    @Size(max = 10)
    private String empn;

    private BigDecimal oldmoney;

    private BigDecimal getmoney;

    private BigDecimal toup;

    @Size(max = 10)
    private String downempn;

    private BigDecimal todown;

    private Long flag;

    private BigDecimal old2;

    private BigDecimal get2;

    private BigDecimal toup2;

    private BigDecimal todown2;

    @Size(max = 10)
    private String upempn2;

    /**
     * 收现金押金
     */
    @ApiModelProperty(value = "收现金押金")
    private BigDecimal im9008;

    /**
     * 收刷卡押金
     */
    @ApiModelProperty(value = "收刷卡押金")
    private BigDecimal im9009;

    /**
     * 现金收款
     */
    @ApiModelProperty(value = "现金收款")
    private BigDecimal co9991;

    /**
     * 刷卡收款
     */
    @ApiModelProperty(value = "刷卡收款")
    private BigDecimal co9992;

    /**
     * 免单收款
     */
    @ApiModelProperty(value = "免单收款")
    private BigDecimal co9993;

    /**
     * 会员卡收款
     */
    @ApiModelProperty(value = "会员卡收款")
    private BigDecimal co9994;

    /**
     * 挂账
     */
    @ApiModelProperty(value = "挂账")
    private BigDecimal co9995;

    private BigDecimal co9998;

    private BigDecimal im9007;

    private Instant gotime;

    private BigDecimal co9999;

    /**
     * 退现押金
     */
    @ApiModelProperty(value = "退现押金")
    private BigDecimal cm9008;

    /**
     * 退刷卡押金
     */
    @ApiModelProperty(value = "退刷卡押金")
    private BigDecimal cm9009;

    private BigDecimal co99910;

    @Size(max = 2)
    private String checkSign;

    @Size(max = 10)
    private String classPb;

    private BigDecimal ck;

    private BigDecimal dk;

    private Instant sjrq;

    private Instant qsjrq;

    private BigDecimal byje;

    @Size(max = 30)
    private String xfcw;

    @Size(max = 20)
    private String hoteldm;

    private Long isnew;

    private BigDecimal co99912;

    private BigDecimal xj;

    @Size(max = 50)
    private String classname;

    private BigDecimal co9010;

    private BigDecimal co9012;

    private BigDecimal co9013;

    private BigDecimal co9014;

    private BigDecimal co99915;

    private BigDecimal hyxj;

    private BigDecimal hysk;

    private BigDecimal hyqt;

    private BigDecimal hkxj;

    private BigDecimal hksk;

    private BigDecimal hkqt;

    private BigDecimal qtwt;

    private BigDecimal qtysq;

    private BigDecimal bbysj;

    private BigDecimal zfbje;

    private BigDecimal wxje;

    private BigDecimal w99920;

    private BigDecimal z99921;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDt() {
        return dt;
    }

    public void setDt(Instant dt) {
        this.dt = dt;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public BigDecimal getOldmoney() {
        return oldmoney;
    }

    public void setOldmoney(BigDecimal oldmoney) {
        this.oldmoney = oldmoney;
    }

    public BigDecimal getGetmoney() {
        return getmoney;
    }

    public void setGetmoney(BigDecimal getmoney) {
        this.getmoney = getmoney;
    }

    public BigDecimal getToup() {
        return toup;
    }

    public void setToup(BigDecimal toup) {
        this.toup = toup;
    }

    public String getDownempn() {
        return downempn;
    }

    public void setDownempn(String downempn) {
        this.downempn = downempn;
    }

    public BigDecimal getTodown() {
        return todown;
    }

    public void setTodown(BigDecimal todown) {
        this.todown = todown;
    }

    public Long getFlag() {
        return flag;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }

    public BigDecimal getOld2() {
        return old2;
    }

    public void setOld2(BigDecimal old2) {
        this.old2 = old2;
    }

    public BigDecimal getGet2() {
        return get2;
    }

    public void setGet2(BigDecimal get2) {
        this.get2 = get2;
    }

    public BigDecimal getToup2() {
        return toup2;
    }

    public void setToup2(BigDecimal toup2) {
        this.toup2 = toup2;
    }

    public BigDecimal getTodown2() {
        return todown2;
    }

    public void setTodown2(BigDecimal todown2) {
        this.todown2 = todown2;
    }

    public String getUpempn2() {
        return upempn2;
    }

    public void setUpempn2(String upempn2) {
        this.upempn2 = upempn2;
    }

    public BigDecimal getIm9008() {
        return im9008;
    }

    public void setIm9008(BigDecimal im9008) {
        this.im9008 = im9008;
    }

    public BigDecimal getIm9009() {
        return im9009;
    }

    public void setIm9009(BigDecimal im9009) {
        this.im9009 = im9009;
    }

    public BigDecimal getCo9991() {
        return co9991;
    }

    public void setCo9991(BigDecimal co9991) {
        this.co9991 = co9991;
    }

    public BigDecimal getCo9992() {
        return co9992;
    }

    public void setCo9992(BigDecimal co9992) {
        this.co9992 = co9992;
    }

    public BigDecimal getCo9993() {
        return co9993;
    }

    public void setCo9993(BigDecimal co9993) {
        this.co9993 = co9993;
    }

    public BigDecimal getCo9994() {
        return co9994;
    }

    public void setCo9994(BigDecimal co9994) {
        this.co9994 = co9994;
    }

    public BigDecimal getCo9995() {
        return co9995;
    }

    public void setCo9995(BigDecimal co9995) {
        this.co9995 = co9995;
    }

    public BigDecimal getCo9998() {
        return co9998;
    }

    public void setCo9998(BigDecimal co9998) {
        this.co9998 = co9998;
    }

    public BigDecimal getIm9007() {
        return im9007;
    }

    public void setIm9007(BigDecimal im9007) {
        this.im9007 = im9007;
    }

    public Instant getGotime() {
        return gotime;
    }

    public void setGotime(Instant gotime) {
        this.gotime = gotime;
    }

    public BigDecimal getCo9999() {
        return co9999;
    }

    public void setCo9999(BigDecimal co9999) {
        this.co9999 = co9999;
    }

    public BigDecimal getCm9008() {
        return cm9008;
    }

    public void setCm9008(BigDecimal cm9008) {
        this.cm9008 = cm9008;
    }

    public BigDecimal getCm9009() {
        return cm9009;
    }

    public void setCm9009(BigDecimal cm9009) {
        this.cm9009 = cm9009;
    }

    public BigDecimal getCo99910() {
        return co99910;
    }

    public void setCo99910(BigDecimal co99910) {
        this.co99910 = co99910;
    }

    public String getCheckSign() {
        return checkSign;
    }

    public void setCheckSign(String checkSign) {
        this.checkSign = checkSign;
    }

    public String getClassPb() {
        return classPb;
    }

    public void setClassPb(String classPb) {
        this.classPb = classPb;
    }

    public BigDecimal getCk() {
        return ck;
    }

    public void setCk(BigDecimal ck) {
        this.ck = ck;
    }

    public BigDecimal getDk() {
        return dk;
    }

    public void setDk(BigDecimal dk) {
        this.dk = dk;
    }

    public Instant getSjrq() {
        return sjrq;
    }

    public void setSjrq(Instant sjrq) {
        this.sjrq = sjrq;
    }

    public Instant getQsjrq() {
        return qsjrq;
    }

    public void setQsjrq(Instant qsjrq) {
        this.qsjrq = qsjrq;
    }

    public BigDecimal getByje() {
        return byje;
    }

    public void setByje(BigDecimal byje) {
        this.byje = byje;
    }

    public String getXfcw() {
        return xfcw;
    }

    public void setXfcw(String xfcw) {
        this.xfcw = xfcw;
    }

    public String getHoteldm() {
        return hoteldm;
    }

    public void setHoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
    }

    public Long getIsnew() {
        return isnew;
    }

    public void setIsnew(Long isnew) {
        this.isnew = isnew;
    }

    public BigDecimal getCo99912() {
        return co99912;
    }

    public void setCo99912(BigDecimal co99912) {
        this.co99912 = co99912;
    }

    public BigDecimal getXj() {
        return xj;
    }

    public void setXj(BigDecimal xj) {
        this.xj = xj;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public BigDecimal getCo9010() {
        return co9010;
    }

    public void setCo9010(BigDecimal co9010) {
        this.co9010 = co9010;
    }

    public BigDecimal getCo9012() {
        return co9012;
    }

    public void setCo9012(BigDecimal co9012) {
        this.co9012 = co9012;
    }

    public BigDecimal getCo9013() {
        return co9013;
    }

    public void setCo9013(BigDecimal co9013) {
        this.co9013 = co9013;
    }

    public BigDecimal getCo9014() {
        return co9014;
    }

    public void setCo9014(BigDecimal co9014) {
        this.co9014 = co9014;
    }

    public BigDecimal getCo99915() {
        return co99915;
    }

    public void setCo99915(BigDecimal co99915) {
        this.co99915 = co99915;
    }

    public BigDecimal getHyxj() {
        return hyxj;
    }

    public void setHyxj(BigDecimal hyxj) {
        this.hyxj = hyxj;
    }

    public BigDecimal getHysk() {
        return hysk;
    }

    public void setHysk(BigDecimal hysk) {
        this.hysk = hysk;
    }

    public BigDecimal getHyqt() {
        return hyqt;
    }

    public void setHyqt(BigDecimal hyqt) {
        this.hyqt = hyqt;
    }

    public BigDecimal getHkxj() {
        return hkxj;
    }

    public void setHkxj(BigDecimal hkxj) {
        this.hkxj = hkxj;
    }

    public BigDecimal getHksk() {
        return hksk;
    }

    public void setHksk(BigDecimal hksk) {
        this.hksk = hksk;
    }

    public BigDecimal getHkqt() {
        return hkqt;
    }

    public void setHkqt(BigDecimal hkqt) {
        this.hkqt = hkqt;
    }

    public BigDecimal getQtwt() {
        return qtwt;
    }

    public void setQtwt(BigDecimal qtwt) {
        this.qtwt = qtwt;
    }

    public BigDecimal getQtysq() {
        return qtysq;
    }

    public void setQtysq(BigDecimal qtysq) {
        this.qtysq = qtysq;
    }

    public BigDecimal getBbysj() {
        return bbysj;
    }

    public void setBbysj(BigDecimal bbysj) {
        this.bbysj = bbysj;
    }

    public BigDecimal getZfbje() {
        return zfbje;
    }

    public void setZfbje(BigDecimal zfbje) {
        this.zfbje = zfbje;
    }

    public BigDecimal getWxje() {
        return wxje;
    }

    public void setWxje(BigDecimal wxje) {
        this.wxje = wxje;
    }

    public BigDecimal getw99920() {
        return w99920;
    }

    public void setw99920(BigDecimal w99920) {
        this.w99920 = w99920;
    }

    public BigDecimal getz99921() {
        return z99921;
    }

    public void setz99921(BigDecimal z99921) {
        this.z99921 = z99921;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassRenameDTO)) {
            return false;
        }

        ClassRenameDTO classRenameDTO = (ClassRenameDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, classRenameDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassRenameDTO{" +
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
