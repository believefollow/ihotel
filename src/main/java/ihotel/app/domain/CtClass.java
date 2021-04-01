package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A CtClass.
 */
@Entity
@Table(name = "ct_class")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ctclass")
public class CtClass implements Serializable {

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

    @Column(name = "flag")
    private Long flag;

    @Size(max = 20)
    @Column(name = "jbempn", length = 20)
    private String jbempn;

    @Column(name = "gotime")
    private Instant gotime;

    @Column(name = "xj", precision = 21, scale = 2)
    private BigDecimal xj;

    @Column(name = "zp", precision = 21, scale = 2)
    private BigDecimal zp;

    @Column(name = "sk", precision = 21, scale = 2)
    private BigDecimal sk;

    @Column(name = "hyk", precision = 21, scale = 2)
    private BigDecimal hyk;

    @Column(name = "cq", precision = 21, scale = 2)
    private BigDecimal cq;

    @Column(name = "gz", precision = 21, scale = 2)
    private BigDecimal gz;

    @Column(name = "gfz", precision = 21, scale = 2)
    private BigDecimal gfz;

    @Column(name = "yq", precision = 21, scale = 2)
    private BigDecimal yq;

    @Column(name = "yh", precision = 21, scale = 2)
    private BigDecimal yh;

    @Column(name = "zzh", precision = 21, scale = 2)
    private BigDecimal zzh;

    @Size(max = 2)
    @Column(name = "check_sign", length = 2)
    private String checkSign;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CtClass id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getDt() {
        return this.dt;
    }

    public CtClass dt(Instant dt) {
        this.dt = dt;
        return this;
    }

    public void setDt(Instant dt) {
        this.dt = dt;
    }

    public String getEmpn() {
        return this.empn;
    }

    public CtClass empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Long getFlag() {
        return this.flag;
    }

    public CtClass flag(Long flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }

    public String getJbempn() {
        return this.jbempn;
    }

    public CtClass jbempn(String jbempn) {
        this.jbempn = jbempn;
        return this;
    }

    public void setJbempn(String jbempn) {
        this.jbempn = jbempn;
    }

    public Instant getGotime() {
        return this.gotime;
    }

    public CtClass gotime(Instant gotime) {
        this.gotime = gotime;
        return this;
    }

    public void setGotime(Instant gotime) {
        this.gotime = gotime;
    }

    public BigDecimal getXj() {
        return this.xj;
    }

    public CtClass xj(BigDecimal xj) {
        this.xj = xj;
        return this;
    }

    public void setXj(BigDecimal xj) {
        this.xj = xj;
    }

    public BigDecimal getZp() {
        return this.zp;
    }

    public CtClass zp(BigDecimal zp) {
        this.zp = zp;
        return this;
    }

    public void setZp(BigDecimal zp) {
        this.zp = zp;
    }

    public BigDecimal getSk() {
        return this.sk;
    }

    public CtClass sk(BigDecimal sk) {
        this.sk = sk;
        return this;
    }

    public void setSk(BigDecimal sk) {
        this.sk = sk;
    }

    public BigDecimal getHyk() {
        return this.hyk;
    }

    public CtClass hyk(BigDecimal hyk) {
        this.hyk = hyk;
        return this;
    }

    public void setHyk(BigDecimal hyk) {
        this.hyk = hyk;
    }

    public BigDecimal getCq() {
        return this.cq;
    }

    public CtClass cq(BigDecimal cq) {
        this.cq = cq;
        return this;
    }

    public void setCq(BigDecimal cq) {
        this.cq = cq;
    }

    public BigDecimal getGz() {
        return this.gz;
    }

    public CtClass gz(BigDecimal gz) {
        this.gz = gz;
        return this;
    }

    public void setGz(BigDecimal gz) {
        this.gz = gz;
    }

    public BigDecimal getGfz() {
        return this.gfz;
    }

    public CtClass gfz(BigDecimal gfz) {
        this.gfz = gfz;
        return this;
    }

    public void setGfz(BigDecimal gfz) {
        this.gfz = gfz;
    }

    public BigDecimal getYq() {
        return this.yq;
    }

    public CtClass yq(BigDecimal yq) {
        this.yq = yq;
        return this;
    }

    public void setYq(BigDecimal yq) {
        this.yq = yq;
    }

    public BigDecimal getYh() {
        return this.yh;
    }

    public CtClass yh(BigDecimal yh) {
        this.yh = yh;
        return this;
    }

    public void setYh(BigDecimal yh) {
        this.yh = yh;
    }

    public BigDecimal getZzh() {
        return this.zzh;
    }

    public CtClass zzh(BigDecimal zzh) {
        this.zzh = zzh;
        return this;
    }

    public void setZzh(BigDecimal zzh) {
        this.zzh = zzh;
    }

    public String getCheckSign() {
        return this.checkSign;
    }

    public CtClass checkSign(String checkSign) {
        this.checkSign = checkSign;
        return this;
    }

    public void setCheckSign(String checkSign) {
        this.checkSign = checkSign;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CtClass)) {
            return false;
        }
        return id != null && id.equals(((CtClass) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CtClass{" +
            "id=" + getId() +
            ", dt='" + getDt() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", flag=" + getFlag() +
            ", jbempn='" + getJbempn() + "'" +
            ", gotime='" + getGotime() + "'" +
            ", xj=" + getXj() +
            ", zp=" + getZp() +
            ", sk=" + getSk() +
            ", hyk=" + getHyk() +
            ", cq=" + getCq() +
            ", gz=" + getGz() +
            ", gfz=" + getGfz() +
            ", yq=" + getYq() +
            ", yh=" + getYh() +
            ", zzh=" + getZzh() +
            ", checkSign='" + getCheckSign() + "'" +
            "}";
    }
}
