package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Classreport} entity.
 */
public class ClassreportDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String empn;

    @NotNull
    private Instant dt;

    private BigDecimal xjUp;

    private BigDecimal yfjA;

    private BigDecimal yfjD;

    private BigDecimal gz;

    private BigDecimal zz;

    private BigDecimal zzYj;

    private BigDecimal zzJs;

    private BigDecimal zzTc;

    private BigDecimal ff;

    private BigDecimal minibar;

    private BigDecimal phone;

    private BigDecimal other;

    private BigDecimal pc;

    private BigDecimal cz;

    private BigDecimal cy;

    private BigDecimal md;

    private BigDecimal huiy;

    private BigDecimal dtb;

    private BigDecimal sszx;

    private BigDecimal cyz;

    @Size(max = 20)
    private String hoteldm;

    private BigDecimal gzxj;

    private Long isnew;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Instant getDt() {
        return dt;
    }

    public void setDt(Instant dt) {
        this.dt = dt;
    }

    public BigDecimal getXjUp() {
        return xjUp;
    }

    public void setXjUp(BigDecimal xjUp) {
        this.xjUp = xjUp;
    }

    public BigDecimal getYfjA() {
        return yfjA;
    }

    public void setYfjA(BigDecimal yfjA) {
        this.yfjA = yfjA;
    }

    public BigDecimal getYfjD() {
        return yfjD;
    }

    public void setYfjD(BigDecimal yfjD) {
        this.yfjD = yfjD;
    }

    public BigDecimal getGz() {
        return gz;
    }

    public void setGz(BigDecimal gz) {
        this.gz = gz;
    }

    public BigDecimal getZz() {
        return zz;
    }

    public void setZz(BigDecimal zz) {
        this.zz = zz;
    }

    public BigDecimal getZzYj() {
        return zzYj;
    }

    public void setZzYj(BigDecimal zzYj) {
        this.zzYj = zzYj;
    }

    public BigDecimal getZzJs() {
        return zzJs;
    }

    public void setZzJs(BigDecimal zzJs) {
        this.zzJs = zzJs;
    }

    public BigDecimal getZzTc() {
        return zzTc;
    }

    public void setZzTc(BigDecimal zzTc) {
        this.zzTc = zzTc;
    }

    public BigDecimal getFf() {
        return ff;
    }

    public void setFf(BigDecimal ff) {
        this.ff = ff;
    }

    public BigDecimal getMinibar() {
        return minibar;
    }

    public void setMinibar(BigDecimal minibar) {
        this.minibar = minibar;
    }

    public BigDecimal getPhone() {
        return phone;
    }

    public void setPhone(BigDecimal phone) {
        this.phone = phone;
    }

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }

    public BigDecimal getPc() {
        return pc;
    }

    public void setPc(BigDecimal pc) {
        this.pc = pc;
    }

    public BigDecimal getCz() {
        return cz;
    }

    public void setCz(BigDecimal cz) {
        this.cz = cz;
    }

    public BigDecimal getCy() {
        return cy;
    }

    public void setCy(BigDecimal cy) {
        this.cy = cy;
    }

    public BigDecimal getMd() {
        return md;
    }

    public void setMd(BigDecimal md) {
        this.md = md;
    }

    public BigDecimal getHuiy() {
        return huiy;
    }

    public void setHuiy(BigDecimal huiy) {
        this.huiy = huiy;
    }

    public BigDecimal getDtb() {
        return dtb;
    }

    public void setDtb(BigDecimal dtb) {
        this.dtb = dtb;
    }

    public BigDecimal getSszx() {
        return sszx;
    }

    public void setSszx(BigDecimal sszx) {
        this.sszx = sszx;
    }

    public BigDecimal getCyz() {
        return cyz;
    }

    public void setCyz(BigDecimal cyz) {
        this.cyz = cyz;
    }

    public String getHoteldm() {
        return hoteldm;
    }

    public void setHoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
    }

    public BigDecimal getGzxj() {
        return gzxj;
    }

    public void setGzxj(BigDecimal gzxj) {
        this.gzxj = gzxj;
    }

    public Long getIsnew() {
        return isnew;
    }

    public void setIsnew(Long isnew) {
        this.isnew = isnew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassreportDTO)) {
            return false;
        }

        ClassreportDTO classreportDTO = (ClassreportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, classreportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassreportDTO{" +
            "id=" + getId() +
            ", empn='" + getEmpn() + "'" +
            ", dt='" + getDt() + "'" +
            ", xjUp=" + getXjUp() +
            ", yfjA=" + getYfjA() +
            ", yfjD=" + getYfjD() +
            ", gz=" + getGz() +
            ", zz=" + getZz() +
            ", zzYj=" + getZzYj() +
            ", zzJs=" + getZzJs() +
            ", zzTc=" + getZzTc() +
            ", ff=" + getFf() +
            ", minibar=" + getMinibar() +
            ", phone=" + getPhone() +
            ", other=" + getOther() +
            ", pc=" + getPc() +
            ", cz=" + getCz() +
            ", cy=" + getCy() +
            ", md=" + getMd() +
            ", huiy=" + getHuiy() +
            ", dtb=" + getDtb() +
            ", sszx=" + getSszx() +
            ", cyz=" + getCyz() +
            ", hoteldm='" + getHoteldm() + "'" +
            ", gzxj=" + getGzxj() +
            ", isnew=" + getIsnew() +
            "}";
    }
}
