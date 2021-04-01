package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A CzlCz.
 */
@Entity
@Table(name = "czl_cz")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "czlcz")
public class CzlCz implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "tjrq", nullable = false)
    private Instant tjrq;

    @Column(name = "typeid")
    private Long typeid;

    @NotNull
    @Size(max = 50)
    @Column(name = "type", length = 50, nullable = false)
    private String type;

    @Column(name = "fjsl")
    private Long fjsl;

    @Column(name = "kfl", precision = 21, scale = 2)
    private BigDecimal kfl;

    @Column(name = "pjz", precision = 21, scale = 2)
    private BigDecimal pjz;

    @Column(name = "ysfz", precision = 21, scale = 2)
    private BigDecimal ysfz;

    @Column(name = "sjfz", precision = 21, scale = 2)
    private BigDecimal sjfz;

    @Column(name = "fzcz", precision = 21, scale = 2)
    private BigDecimal fzcz;

    @Column(name = "pjzcj", precision = 21, scale = 2)
    private BigDecimal pjzcj;

    @Column(name = "kfs_m", precision = 21, scale = 2)
    private BigDecimal kfsM;

    @Column(name = "kfl_m", precision = 21, scale = 2)
    private BigDecimal kflM;

    @Column(name = "pjz_m", precision = 21, scale = 2)
    private BigDecimal pjzM;

    @Column(name = "fzsr", precision = 21, scale = 2)
    private BigDecimal fzsr;

    @Column(name = "dayz", precision = 21, scale = 2)
    private BigDecimal dayz;

    @Column(name = "hoteltime")
    private Instant hoteltime;

    @Size(max = 45)
    @Column(name = "empn", length = 45)
    private String empn;

    @Column(name = "monthz", precision = 21, scale = 2)
    private BigDecimal monthz;

    @Size(max = 50)
    @Column(name = "hoteldm", length = 50)
    private String hoteldm;

    @Column(name = "isnew")
    private Long isnew;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CzlCz id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getTjrq() {
        return this.tjrq;
    }

    public CzlCz tjrq(Instant tjrq) {
        this.tjrq = tjrq;
        return this;
    }

    public void setTjrq(Instant tjrq) {
        this.tjrq = tjrq;
    }

    public Long getTypeid() {
        return this.typeid;
    }

    public CzlCz typeid(Long typeid) {
        this.typeid = typeid;
        return this;
    }

    public void setTypeid(Long typeid) {
        this.typeid = typeid;
    }

    public String getType() {
        return this.type;
    }

    public CzlCz type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getFjsl() {
        return this.fjsl;
    }

    public CzlCz fjsl(Long fjsl) {
        this.fjsl = fjsl;
        return this;
    }

    public void setFjsl(Long fjsl) {
        this.fjsl = fjsl;
    }

    public BigDecimal getKfl() {
        return this.kfl;
    }

    public CzlCz kfl(BigDecimal kfl) {
        this.kfl = kfl;
        return this;
    }

    public void setKfl(BigDecimal kfl) {
        this.kfl = kfl;
    }

    public BigDecimal getPjz() {
        return this.pjz;
    }

    public CzlCz pjz(BigDecimal pjz) {
        this.pjz = pjz;
        return this;
    }

    public void setPjz(BigDecimal pjz) {
        this.pjz = pjz;
    }

    public BigDecimal getYsfz() {
        return this.ysfz;
    }

    public CzlCz ysfz(BigDecimal ysfz) {
        this.ysfz = ysfz;
        return this;
    }

    public void setYsfz(BigDecimal ysfz) {
        this.ysfz = ysfz;
    }

    public BigDecimal getSjfz() {
        return this.sjfz;
    }

    public CzlCz sjfz(BigDecimal sjfz) {
        this.sjfz = sjfz;
        return this;
    }

    public void setSjfz(BigDecimal sjfz) {
        this.sjfz = sjfz;
    }

    public BigDecimal getFzcz() {
        return this.fzcz;
    }

    public CzlCz fzcz(BigDecimal fzcz) {
        this.fzcz = fzcz;
        return this;
    }

    public void setFzcz(BigDecimal fzcz) {
        this.fzcz = fzcz;
    }

    public BigDecimal getPjzcj() {
        return this.pjzcj;
    }

    public CzlCz pjzcj(BigDecimal pjzcj) {
        this.pjzcj = pjzcj;
        return this;
    }

    public void setPjzcj(BigDecimal pjzcj) {
        this.pjzcj = pjzcj;
    }

    public BigDecimal getKfsM() {
        return this.kfsM;
    }

    public CzlCz kfsM(BigDecimal kfsM) {
        this.kfsM = kfsM;
        return this;
    }

    public void setKfsM(BigDecimal kfsM) {
        this.kfsM = kfsM;
    }

    public BigDecimal getKflM() {
        return this.kflM;
    }

    public CzlCz kflM(BigDecimal kflM) {
        this.kflM = kflM;
        return this;
    }

    public void setKflM(BigDecimal kflM) {
        this.kflM = kflM;
    }

    public BigDecimal getPjzM() {
        return this.pjzM;
    }

    public CzlCz pjzM(BigDecimal pjzM) {
        this.pjzM = pjzM;
        return this;
    }

    public void setPjzM(BigDecimal pjzM) {
        this.pjzM = pjzM;
    }

    public BigDecimal getFzsr() {
        return this.fzsr;
    }

    public CzlCz fzsr(BigDecimal fzsr) {
        this.fzsr = fzsr;
        return this;
    }

    public void setFzsr(BigDecimal fzsr) {
        this.fzsr = fzsr;
    }

    public BigDecimal getDayz() {
        return this.dayz;
    }

    public CzlCz dayz(BigDecimal dayz) {
        this.dayz = dayz;
        return this;
    }

    public void setDayz(BigDecimal dayz) {
        this.dayz = dayz;
    }

    public Instant getHoteltime() {
        return this.hoteltime;
    }

    public CzlCz hoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
        return this;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public String getEmpn() {
        return this.empn;
    }

    public CzlCz empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public BigDecimal getMonthz() {
        return this.monthz;
    }

    public CzlCz monthz(BigDecimal monthz) {
        this.monthz = monthz;
        return this;
    }

    public void setMonthz(BigDecimal monthz) {
        this.monthz = monthz;
    }

    public String getHoteldm() {
        return this.hoteldm;
    }

    public CzlCz hoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
        return this;
    }

    public void setHoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
    }

    public Long getIsnew() {
        return this.isnew;
    }

    public CzlCz isnew(Long isnew) {
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
        if (!(o instanceof CzlCz)) {
            return false;
        }
        return id != null && id.equals(((CzlCz) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CzlCz{" +
            "id=" + getId() +
            ", tjrq='" + getTjrq() + "'" +
            ", typeid=" + getTypeid() +
            ", type='" + getType() + "'" +
            ", fjsl=" + getFjsl() +
            ", kfl=" + getKfl() +
            ", pjz=" + getPjz() +
            ", ysfz=" + getYsfz() +
            ", sjfz=" + getSjfz() +
            ", fzcz=" + getFzcz() +
            ", pjzcj=" + getPjzcj() +
            ", kfsM=" + getKfsM() +
            ", kflM=" + getKflM() +
            ", pjzM=" + getPjzM() +
            ", fzsr=" + getFzsr() +
            ", dayz=" + getDayz() +
            ", hoteltime='" + getHoteltime() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", monthz=" + getMonthz() +
            ", hoteldm='" + getHoteldm() + "'" +
            ", isnew=" + getIsnew() +
            "}";
    }
}
