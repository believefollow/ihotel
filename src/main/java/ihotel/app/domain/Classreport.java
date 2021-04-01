package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Classreport.
 */
@Entity
@Table(name = "classreport")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "classreport")
public class Classreport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 10)
    @Column(name = "empn", length = 10, nullable = false)
    private String empn;

    @NotNull
    @Column(name = "dt", nullable = false)
    private Instant dt;

    @Column(name = "xj_up", precision = 21, scale = 2)
    private BigDecimal xjUp;

    @Column(name = "yfj_a", precision = 21, scale = 2)
    private BigDecimal yfjA;

    @Column(name = "yfj_d", precision = 21, scale = 2)
    private BigDecimal yfjD;

    @Column(name = "gz", precision = 21, scale = 2)
    private BigDecimal gz;

    @Column(name = "zz", precision = 21, scale = 2)
    private BigDecimal zz;

    @Column(name = "zz_yj", precision = 21, scale = 2)
    private BigDecimal zzYj;

    @Column(name = "zz_js", precision = 21, scale = 2)
    private BigDecimal zzJs;

    @Column(name = "zz_tc", precision = 21, scale = 2)
    private BigDecimal zzTc;

    @Column(name = "ff", precision = 21, scale = 2)
    private BigDecimal ff;

    @Column(name = "minibar", precision = 21, scale = 2)
    private BigDecimal minibar;

    @Column(name = "phone", precision = 21, scale = 2)
    private BigDecimal phone;

    @Column(name = "other", precision = 21, scale = 2)
    private BigDecimal other;

    @Column(name = "pc", precision = 21, scale = 2)
    private BigDecimal pc;

    @Column(name = "cz", precision = 21, scale = 2)
    private BigDecimal cz;

    @Column(name = "cy", precision = 21, scale = 2)
    private BigDecimal cy;

    @Column(name = "md", precision = 21, scale = 2)
    private BigDecimal md;

    @Column(name = "huiy", precision = 21, scale = 2)
    private BigDecimal huiy;

    @Column(name = "dtb", precision = 21, scale = 2)
    private BigDecimal dtb;

    @Column(name = "sszx", precision = 21, scale = 2)
    private BigDecimal sszx;

    @Column(name = "cyz", precision = 21, scale = 2)
    private BigDecimal cyz;

    @Size(max = 20)
    @Column(name = "hoteldm", length = 20)
    private String hoteldm;

    @Column(name = "gzxj", precision = 21, scale = 2)
    private BigDecimal gzxj;

    @Column(name = "isnew")
    private Long isnew;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Classreport id(Long id) {
        this.id = id;
        return this;
    }

    public String getEmpn() {
        return this.empn;
    }

    public Classreport empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Instant getDt() {
        return this.dt;
    }

    public Classreport dt(Instant dt) {
        this.dt = dt;
        return this;
    }

    public void setDt(Instant dt) {
        this.dt = dt;
    }

    public BigDecimal getXjUp() {
        return this.xjUp;
    }

    public Classreport xjUp(BigDecimal xjUp) {
        this.xjUp = xjUp;
        return this;
    }

    public void setXjUp(BigDecimal xjUp) {
        this.xjUp = xjUp;
    }

    public BigDecimal getYfjA() {
        return this.yfjA;
    }

    public Classreport yfjA(BigDecimal yfjA) {
        this.yfjA = yfjA;
        return this;
    }

    public void setYfjA(BigDecimal yfjA) {
        this.yfjA = yfjA;
    }

    public BigDecimal getYfjD() {
        return this.yfjD;
    }

    public Classreport yfjD(BigDecimal yfjD) {
        this.yfjD = yfjD;
        return this;
    }

    public void setYfjD(BigDecimal yfjD) {
        this.yfjD = yfjD;
    }

    public BigDecimal getGz() {
        return this.gz;
    }

    public Classreport gz(BigDecimal gz) {
        this.gz = gz;
        return this;
    }

    public void setGz(BigDecimal gz) {
        this.gz = gz;
    }

    public BigDecimal getZz() {
        return this.zz;
    }

    public Classreport zz(BigDecimal zz) {
        this.zz = zz;
        return this;
    }

    public void setZz(BigDecimal zz) {
        this.zz = zz;
    }

    public BigDecimal getZzYj() {
        return this.zzYj;
    }

    public Classreport zzYj(BigDecimal zzYj) {
        this.zzYj = zzYj;
        return this;
    }

    public void setZzYj(BigDecimal zzYj) {
        this.zzYj = zzYj;
    }

    public BigDecimal getZzJs() {
        return this.zzJs;
    }

    public Classreport zzJs(BigDecimal zzJs) {
        this.zzJs = zzJs;
        return this;
    }

    public void setZzJs(BigDecimal zzJs) {
        this.zzJs = zzJs;
    }

    public BigDecimal getZzTc() {
        return this.zzTc;
    }

    public Classreport zzTc(BigDecimal zzTc) {
        this.zzTc = zzTc;
        return this;
    }

    public void setZzTc(BigDecimal zzTc) {
        this.zzTc = zzTc;
    }

    public BigDecimal getFf() {
        return this.ff;
    }

    public Classreport ff(BigDecimal ff) {
        this.ff = ff;
        return this;
    }

    public void setFf(BigDecimal ff) {
        this.ff = ff;
    }

    public BigDecimal getMinibar() {
        return this.minibar;
    }

    public Classreport minibar(BigDecimal minibar) {
        this.minibar = minibar;
        return this;
    }

    public void setMinibar(BigDecimal minibar) {
        this.minibar = minibar;
    }

    public BigDecimal getPhone() {
        return this.phone;
    }

    public Classreport phone(BigDecimal phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(BigDecimal phone) {
        this.phone = phone;
    }

    public BigDecimal getOther() {
        return this.other;
    }

    public Classreport other(BigDecimal other) {
        this.other = other;
        return this;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }

    public BigDecimal getPc() {
        return this.pc;
    }

    public Classreport pc(BigDecimal pc) {
        this.pc = pc;
        return this;
    }

    public void setPc(BigDecimal pc) {
        this.pc = pc;
    }

    public BigDecimal getCz() {
        return this.cz;
    }

    public Classreport cz(BigDecimal cz) {
        this.cz = cz;
        return this;
    }

    public void setCz(BigDecimal cz) {
        this.cz = cz;
    }

    public BigDecimal getCy() {
        return this.cy;
    }

    public Classreport cy(BigDecimal cy) {
        this.cy = cy;
        return this;
    }

    public void setCy(BigDecimal cy) {
        this.cy = cy;
    }

    public BigDecimal getMd() {
        return this.md;
    }

    public Classreport md(BigDecimal md) {
        this.md = md;
        return this;
    }

    public void setMd(BigDecimal md) {
        this.md = md;
    }

    public BigDecimal getHuiy() {
        return this.huiy;
    }

    public Classreport huiy(BigDecimal huiy) {
        this.huiy = huiy;
        return this;
    }

    public void setHuiy(BigDecimal huiy) {
        this.huiy = huiy;
    }

    public BigDecimal getDtb() {
        return this.dtb;
    }

    public Classreport dtb(BigDecimal dtb) {
        this.dtb = dtb;
        return this;
    }

    public void setDtb(BigDecimal dtb) {
        this.dtb = dtb;
    }

    public BigDecimal getSszx() {
        return this.sszx;
    }

    public Classreport sszx(BigDecimal sszx) {
        this.sszx = sszx;
        return this;
    }

    public void setSszx(BigDecimal sszx) {
        this.sszx = sszx;
    }

    public BigDecimal getCyz() {
        return this.cyz;
    }

    public Classreport cyz(BigDecimal cyz) {
        this.cyz = cyz;
        return this;
    }

    public void setCyz(BigDecimal cyz) {
        this.cyz = cyz;
    }

    public String getHoteldm() {
        return this.hoteldm;
    }

    public Classreport hoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
        return this;
    }

    public void setHoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
    }

    public BigDecimal getGzxj() {
        return this.gzxj;
    }

    public Classreport gzxj(BigDecimal gzxj) {
        this.gzxj = gzxj;
        return this;
    }

    public void setGzxj(BigDecimal gzxj) {
        this.gzxj = gzxj;
    }

    public Long getIsnew() {
        return this.isnew;
    }

    public Classreport isnew(Long isnew) {
        this.isnew = isnew;
        return this;
    }

    public void setIsnew(Long isnew) {
        this.isnew = isnew;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Classreport)) {
            return false;
        }
        return id != null && id.equals(((Classreport) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Classreport{" +
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
