package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A CzCzl2.
 */
@Entity
@Table(name = "cz_czl_2")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "czczl2")
public class CzCzl2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dr")
    private Instant dr;

    @Size(max = 50)
    @Column(name = "type", length = 50)
    private String type;

    @Column(name = "fs")
    private Long fs;

    @Column(name = "kfl", precision = 21, scale = 2)
    private BigDecimal kfl;

    @Column(name = "fzsr", precision = 21, scale = 2)
    private BigDecimal fzsr;

    @Column(name = "pjz", precision = 21, scale = 2)
    private BigDecimal pjz;

    @Column(name = "fs_m")
    private Long fsM;

    @Column(name = "kfl_m", precision = 21, scale = 2)
    private BigDecimal kflM;

    @Column(name = "fzsr_m", precision = 21, scale = 2)
    private BigDecimal fzsrM;

    @Column(name = "pjz_m", precision = 21, scale = 2)
    private BigDecimal pjzM;

    @Column(name = "fs_y")
    private Long fsY;

    @Column(name = "kfl_y", precision = 21, scale = 2)
    private BigDecimal kflY;

    @Column(name = "fzsr_y", precision = 21, scale = 2)
    private BigDecimal fzsrY;

    @Column(name = "pjz_y", precision = 21, scale = 2)
    private BigDecimal pjzY;

    @Column(name = "fs_q")
    private Long fsQ;

    @Column(name = "kfl_q", precision = 21, scale = 2)
    private BigDecimal kflQ;

    @Column(name = "fzsr_q", precision = 21, scale = 2)
    private BigDecimal fzsrQ;

    @Column(name = "pjz_q", precision = 21, scale = 2)
    private BigDecimal pjzQ;

    @Size(max = 50)
    @Column(name = "date_y", length = 50)
    private String dateY;

    @Column(name = "dqdate")
    private Instant dqdate;

    @Size(max = 45)
    @Column(name = "empn", length = 45)
    private String empn;

    @NotNull
    @Column(name = "number", nullable = false)
    private Long number;

    @NotNull
    @Column(name = "number_m", nullable = false)
    private Long numberM;

    @NotNull
    @Column(name = "number_y", nullable = false)
    private Long numberY;

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

    public CzCzl2 id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getDr() {
        return this.dr;
    }

    public CzCzl2 dr(Instant dr) {
        this.dr = dr;
        return this;
    }

    public void setDr(Instant dr) {
        this.dr = dr;
    }

    public String getType() {
        return this.type;
    }

    public CzCzl2 type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getFs() {
        return this.fs;
    }

    public CzCzl2 fs(Long fs) {
        this.fs = fs;
        return this;
    }

    public void setFs(Long fs) {
        this.fs = fs;
    }

    public BigDecimal getKfl() {
        return this.kfl;
    }

    public CzCzl2 kfl(BigDecimal kfl) {
        this.kfl = kfl;
        return this;
    }

    public void setKfl(BigDecimal kfl) {
        this.kfl = kfl;
    }

    public BigDecimal getFzsr() {
        return this.fzsr;
    }

    public CzCzl2 fzsr(BigDecimal fzsr) {
        this.fzsr = fzsr;
        return this;
    }

    public void setFzsr(BigDecimal fzsr) {
        this.fzsr = fzsr;
    }

    public BigDecimal getPjz() {
        return this.pjz;
    }

    public CzCzl2 pjz(BigDecimal pjz) {
        this.pjz = pjz;
        return this;
    }

    public void setPjz(BigDecimal pjz) {
        this.pjz = pjz;
    }

    public Long getFsM() {
        return this.fsM;
    }

    public CzCzl2 fsM(Long fsM) {
        this.fsM = fsM;
        return this;
    }

    public void setFsM(Long fsM) {
        this.fsM = fsM;
    }

    public BigDecimal getKflM() {
        return this.kflM;
    }

    public CzCzl2 kflM(BigDecimal kflM) {
        this.kflM = kflM;
        return this;
    }

    public void setKflM(BigDecimal kflM) {
        this.kflM = kflM;
    }

    public BigDecimal getFzsrM() {
        return this.fzsrM;
    }

    public CzCzl2 fzsrM(BigDecimal fzsrM) {
        this.fzsrM = fzsrM;
        return this;
    }

    public void setFzsrM(BigDecimal fzsrM) {
        this.fzsrM = fzsrM;
    }

    public BigDecimal getPjzM() {
        return this.pjzM;
    }

    public CzCzl2 pjzM(BigDecimal pjzM) {
        this.pjzM = pjzM;
        return this;
    }

    public void setPjzM(BigDecimal pjzM) {
        this.pjzM = pjzM;
    }

    public Long getFsY() {
        return this.fsY;
    }

    public CzCzl2 fsY(Long fsY) {
        this.fsY = fsY;
        return this;
    }

    public void setFsY(Long fsY) {
        this.fsY = fsY;
    }

    public BigDecimal getKflY() {
        return this.kflY;
    }

    public CzCzl2 kflY(BigDecimal kflY) {
        this.kflY = kflY;
        return this;
    }

    public void setKflY(BigDecimal kflY) {
        this.kflY = kflY;
    }

    public BigDecimal getFzsrY() {
        return this.fzsrY;
    }

    public CzCzl2 fzsrY(BigDecimal fzsrY) {
        this.fzsrY = fzsrY;
        return this;
    }

    public void setFzsrY(BigDecimal fzsrY) {
        this.fzsrY = fzsrY;
    }

    public BigDecimal getPjzY() {
        return this.pjzY;
    }

    public CzCzl2 pjzY(BigDecimal pjzY) {
        this.pjzY = pjzY;
        return this;
    }

    public void setPjzY(BigDecimal pjzY) {
        this.pjzY = pjzY;
    }

    public Long getFsQ() {
        return this.fsQ;
    }

    public CzCzl2 fsQ(Long fsQ) {
        this.fsQ = fsQ;
        return this;
    }

    public void setFsQ(Long fsQ) {
        this.fsQ = fsQ;
    }

    public BigDecimal getKflQ() {
        return this.kflQ;
    }

    public CzCzl2 kflQ(BigDecimal kflQ) {
        this.kflQ = kflQ;
        return this;
    }

    public void setKflQ(BigDecimal kflQ) {
        this.kflQ = kflQ;
    }

    public BigDecimal getFzsrQ() {
        return this.fzsrQ;
    }

    public CzCzl2 fzsrQ(BigDecimal fzsrQ) {
        this.fzsrQ = fzsrQ;
        return this;
    }

    public void setFzsrQ(BigDecimal fzsrQ) {
        this.fzsrQ = fzsrQ;
    }

    public BigDecimal getPjzQ() {
        return this.pjzQ;
    }

    public CzCzl2 pjzQ(BigDecimal pjzQ) {
        this.pjzQ = pjzQ;
        return this;
    }

    public void setPjzQ(BigDecimal pjzQ) {
        this.pjzQ = pjzQ;
    }

    public String getDateY() {
        return this.dateY;
    }

    public CzCzl2 dateY(String dateY) {
        this.dateY = dateY;
        return this;
    }

    public void setDateY(String dateY) {
        this.dateY = dateY;
    }

    public Instant getDqdate() {
        return this.dqdate;
    }

    public CzCzl2 dqdate(Instant dqdate) {
        this.dqdate = dqdate;
        return this;
    }

    public void setDqdate(Instant dqdate) {
        this.dqdate = dqdate;
    }

    public String getEmpn() {
        return this.empn;
    }

    public CzCzl2 empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Long getNumber() {
        return this.number;
    }

    public CzCzl2 number(Long number) {
        this.number = number;
        return this;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getNumberM() {
        return this.numberM;
    }

    public CzCzl2 numberM(Long numberM) {
        this.numberM = numberM;
        return this;
    }

    public void setNumberM(Long numberM) {
        this.numberM = numberM;
    }

    public Long getNumberY() {
        return this.numberY;
    }

    public CzCzl2 numberY(Long numberY) {
        this.numberY = numberY;
        return this;
    }

    public void setNumberY(Long numberY) {
        this.numberY = numberY;
    }

    public String getHoteldm() {
        return this.hoteldm;
    }

    public CzCzl2 hoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
        return this;
    }

    public void setHoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
    }

    public Long getIsnew() {
        return this.isnew;
    }

    public CzCzl2 isnew(Long isnew) {
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
        if (!(o instanceof CzCzl2)) {
            return false;
        }
        return id != null && id.equals(((CzCzl2) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CzCzl2{" +
            "id=" + getId() +
            ", dr='" + getDr() + "'" +
            ", type='" + getType() + "'" +
            ", fs=" + getFs() +
            ", kfl=" + getKfl() +
            ", fzsr=" + getFzsr() +
            ", pjz=" + getPjz() +
            ", fsM=" + getFsM() +
            ", kflM=" + getKflM() +
            ", fzsrM=" + getFzsrM() +
            ", pjzM=" + getPjzM() +
            ", fsY=" + getFsY() +
            ", kflY=" + getKflY() +
            ", fzsrY=" + getFzsrY() +
            ", pjzY=" + getPjzY() +
            ", fsQ=" + getFsQ() +
            ", kflQ=" + getKflQ() +
            ", fzsrQ=" + getFzsrQ() +
            ", pjzQ=" + getPjzQ() +
            ", dateY='" + getDateY() + "'" +
            ", dqdate='" + getDqdate() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", number=" + getNumber() +
            ", numberM=" + getNumberM() +
            ", numberY=" + getNumberY() +
            ", hoteldm='" + getHoteldm() + "'" +
            ", isnew=" + getIsnew() +
            "}";
    }
}
