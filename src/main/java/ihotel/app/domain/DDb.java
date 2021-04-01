package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DDb.
 */
@Entity
@Table(name = "d_db")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ddb")
public class DDb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "dbdate", nullable = false)
    private Instant dbdate;

    @NotNull
    @Size(max = 20)
    @Column(name = "dbbillno", length = 20, nullable = false)
    private String dbbillno;

    @NotNull
    @Size(max = 20)
    @Column(name = "rdepot", length = 20, nullable = false)
    private String rdepot;

    @NotNull
    @Size(max = 20)
    @Column(name = "cdepot", length = 20, nullable = false)
    private String cdepot;

    @Size(max = 50)
    @Column(name = "jbr", length = 50)
    private String jbr;

    @Size(max = 50)
    @Column(name = "remark", length = 50)
    private String remark;

    @NotNull
    @Size(max = 20)
    @Column(name = "spbm", length = 20, nullable = false)
    private String spbm;

    @NotNull
    @Size(max = 20)
    @Column(name = "spmc", length = 20, nullable = false)
    private String spmc;

    @Size(max = 20)
    @Column(name = "unit", length = 20)
    private String unit;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "sl", precision = 21, scale = 2)
    private BigDecimal sl;

    @Column(name = "je", precision = 21, scale = 2)
    private BigDecimal je;

    @Size(max = 100)
    @Column(name = "memo", length = 100)
    private String memo;

    @Column(name = "flag")
    private Long flag;

    @Column(name = "kcid")
    private Long kcid;

    @Size(max = 20)
    @Column(name = "empn", length = 20)
    private String empn;

    @Column(name = "lrdate")
    private Instant lrdate;

    @Size(max = 30)
    @Column(name = "ckbillno", length = 30)
    private String ckbillno;

    @Size(max = 10)
    @Column(name = "f_1", length = 10)
    private String f1;

    @Size(max = 10)
    @Column(name = "f_2", length = 10)
    private String f2;

    @Size(max = 50)
    @Column(name = "f_1_empn", length = 50)
    private String f1empn;

    @Size(max = 50)
    @Column(name = "f_2_empn", length = 50)
    private String f2empn;

    @Column(name = "f_1_sj")
    private Instant f1sj;

    @Column(name = "f_2_sj")
    private Instant f2sj;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DDb id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getDbdate() {
        return this.dbdate;
    }

    public DDb dbdate(Instant dbdate) {
        this.dbdate = dbdate;
        return this;
    }

    public void setDbdate(Instant dbdate) {
        this.dbdate = dbdate;
    }

    public String getDbbillno() {
        return this.dbbillno;
    }

    public DDb dbbillno(String dbbillno) {
        this.dbbillno = dbbillno;
        return this;
    }

    public void setDbbillno(String dbbillno) {
        this.dbbillno = dbbillno;
    }

    public String getRdepot() {
        return this.rdepot;
    }

    public DDb rdepot(String rdepot) {
        this.rdepot = rdepot;
        return this;
    }

    public void setRdepot(String rdepot) {
        this.rdepot = rdepot;
    }

    public String getCdepot() {
        return this.cdepot;
    }

    public DDb cdepot(String cdepot) {
        this.cdepot = cdepot;
        return this;
    }

    public void setCdepot(String cdepot) {
        this.cdepot = cdepot;
    }

    public String getJbr() {
        return this.jbr;
    }

    public DDb jbr(String jbr) {
        this.jbr = jbr;
        return this;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public String getRemark() {
        return this.remark;
    }

    public DDb remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSpbm() {
        return this.spbm;
    }

    public DDb spbm(String spbm) {
        this.spbm = spbm;
        return this;
    }

    public void setSpbm(String spbm) {
        this.spbm = spbm;
    }

    public String getSpmc() {
        return this.spmc;
    }

    public DDb spmc(String spmc) {
        this.spmc = spmc;
        return this;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getUnit() {
        return this.unit;
    }

    public DDb unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public DDb price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSl() {
        return this.sl;
    }

    public DDb sl(BigDecimal sl) {
        this.sl = sl;
        return this;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public BigDecimal getJe() {
        return this.je;
    }

    public DDb je(BigDecimal je) {
        this.je = je;
        return this;
    }

    public void setJe(BigDecimal je) {
        this.je = je;
    }

    public String getMemo() {
        return this.memo;
    }

    public DDb memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getFlag() {
        return this.flag;
    }

    public DDb flag(Long flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }

    public Long getKcid() {
        return this.kcid;
    }

    public DDb kcid(Long kcid) {
        this.kcid = kcid;
        return this;
    }

    public void setKcid(Long kcid) {
        this.kcid = kcid;
    }

    public String getEmpn() {
        return this.empn;
    }

    public DDb empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Instant getLrdate() {
        return this.lrdate;
    }

    public DDb lrdate(Instant lrdate) {
        this.lrdate = lrdate;
        return this;
    }

    public void setLrdate(Instant lrdate) {
        this.lrdate = lrdate;
    }

    public String getCkbillno() {
        return this.ckbillno;
    }

    public DDb ckbillno(String ckbillno) {
        this.ckbillno = ckbillno;
        return this;
    }

    public void setCkbillno(String ckbillno) {
        this.ckbillno = ckbillno;
    }

    public String getf1() {
        return this.f1;
    }

    public DDb f1(String f1) {
        this.f1 = f1;
        return this;
    }

    public void setf1(String f1) {
        this.f1 = f1;
    }

    public String getf2() {
        return this.f2;
    }

    public DDb f2(String f2) {
        this.f2 = f2;
        return this;
    }

    public void setf2(String f2) {
        this.f2 = f2;
    }

    public String getf1empn() {
        return this.f1empn;
    }

    public DDb f1empn(String f1empn) {
        this.f1empn = f1empn;
        return this;
    }

    public void setf1empn(String f1empn) {
        this.f1empn = f1empn;
    }

    public String getf2empn() {
        return this.f2empn;
    }

    public DDb f2empn(String f2empn) {
        this.f2empn = f2empn;
        return this;
    }

    public void setf2empn(String f2empn) {
        this.f2empn = f2empn;
    }

    public Instant getf1sj() {
        return this.f1sj;
    }

    public DDb f1sj(Instant f1sj) {
        this.f1sj = f1sj;
        return this;
    }

    public void setf1sj(Instant f1sj) {
        this.f1sj = f1sj;
    }

    public Instant getf2sj() {
        return this.f2sj;
    }

    public DDb f2sj(Instant f2sj) {
        this.f2sj = f2sj;
        return this;
    }

    public void setf2sj(Instant f2sj) {
        this.f2sj = f2sj;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DDb)) {
            return false;
        }
        return id != null && id.equals(((DDb) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DDb{" +
            "id=" + getId() +
            ", dbdate='" + getDbdate() + "'" +
            ", dbbillno='" + getDbbillno() + "'" +
            ", rdepot='" + getRdepot() + "'" +
            ", cdepot='" + getCdepot() + "'" +
            ", jbr='" + getJbr() + "'" +
            ", remark='" + getRemark() + "'" +
            ", spbm='" + getSpbm() + "'" +
            ", spmc='" + getSpmc() + "'" +
            ", unit='" + getUnit() + "'" +
            ", price=" + getPrice() +
            ", sl=" + getSl() +
            ", je=" + getJe() +
            ", memo='" + getMemo() + "'" +
            ", flag=" + getFlag() +
            ", kcid=" + getKcid() +
            ", empn='" + getEmpn() + "'" +
            ", lrdate='" + getLrdate() + "'" +
            ", ckbillno='" + getCkbillno() + "'" +
            ", f1='" + getf1() + "'" +
            ", f2='" + getf2() + "'" +
            ", f1empn='" + getf1empn() + "'" +
            ", f2empn='" + getf2empn() + "'" +
            ", f1sj='" + getf1sj() + "'" +
            ", f2sj='" + getf2sj() + "'" +
            "}";
    }
}
