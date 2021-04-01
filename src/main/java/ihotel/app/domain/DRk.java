package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DRk.
 */
@Entity
@Table(name = "d_rk")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "drk")
public class DRk implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "rkdate", nullable = false)
    private Instant rkdate;

    @NotNull
    @Size(max = 20)
    @Column(name = "depot", length = 20, nullable = false)
    private String depot;

    @Size(max = 50)
    @Column(name = "rklx", length = 50)
    private String rklx;

    @NotNull
    @Size(max = 20)
    @Column(name = "rkbillno", length = 20, nullable = false)
    private String rkbillno;

    @Column(name = "company")
    private Long company;

    @Size(max = 50)
    @Column(name = "deptname", length = 50)
    private String deptname;

    @Size(max = 50)
    @Column(name = "jbr", length = 50)
    private String jbr;

    @Size(max = 100)
    @Column(name = "remark", length = 100)
    private String remark;

    @Size(max = 50)
    @Column(name = "empn", length = 50)
    private String empn;

    @Column(name = "lrdate")
    private Instant lrdate;

    @NotNull
    @Size(max = 20)
    @Column(name = "spbm", length = 20, nullable = false)
    private String spbm;

    @NotNull
    @Size(max = 50)
    @Column(name = "spmc", length = 50, nullable = false)
    private String spmc;

    @Size(max = 20)
    @Column(name = "ggxh", length = 20)
    private String ggxh;

    @Size(max = 20)
    @Column(name = "dw", length = 20)
    private String dw;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    @Column(name = "sl", precision = 21, scale = 2)
    private BigDecimal sl;

    @Column(name = "je", precision = 21, scale = 2)
    private BigDecimal je;

    @Column(name = "sign")
    private Long sign;

    @Size(max = 100)
    @Column(name = "memo", length = 100)
    private String memo;

    @Column(name = "flag")
    private Long flag;

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

    public DRk id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getRkdate() {
        return this.rkdate;
    }

    public DRk rkdate(Instant rkdate) {
        this.rkdate = rkdate;
        return this;
    }

    public void setRkdate(Instant rkdate) {
        this.rkdate = rkdate;
    }

    public String getDepot() {
        return this.depot;
    }

    public DRk depot(String depot) {
        this.depot = depot;
        return this;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public String getRklx() {
        return this.rklx;
    }

    public DRk rklx(String rklx) {
        this.rklx = rklx;
        return this;
    }

    public void setRklx(String rklx) {
        this.rklx = rklx;
    }

    public String getRkbillno() {
        return this.rkbillno;
    }

    public DRk rkbillno(String rkbillno) {
        this.rkbillno = rkbillno;
        return this;
    }

    public void setRkbillno(String rkbillno) {
        this.rkbillno = rkbillno;
    }

    public Long getCompany() {
        return this.company;
    }

    public DRk company(Long company) {
        this.company = company;
        return this;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    public String getDeptname() {
        return this.deptname;
    }

    public DRk deptname(String deptname) {
        this.deptname = deptname;
        return this;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getJbr() {
        return this.jbr;
    }

    public DRk jbr(String jbr) {
        this.jbr = jbr;
        return this;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public String getRemark() {
        return this.remark;
    }

    public DRk remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmpn() {
        return this.empn;
    }

    public DRk empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Instant getLrdate() {
        return this.lrdate;
    }

    public DRk lrdate(Instant lrdate) {
        this.lrdate = lrdate;
        return this;
    }

    public void setLrdate(Instant lrdate) {
        this.lrdate = lrdate;
    }

    public String getSpbm() {
        return this.spbm;
    }

    public DRk spbm(String spbm) {
        this.spbm = spbm;
        return this;
    }

    public void setSpbm(String spbm) {
        this.spbm = spbm;
    }

    public String getSpmc() {
        return this.spmc;
    }

    public DRk spmc(String spmc) {
        this.spmc = spmc;
        return this;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getGgxh() {
        return this.ggxh;
    }

    public DRk ggxh(String ggxh) {
        this.ggxh = ggxh;
        return this;
    }

    public void setGgxh(String ggxh) {
        this.ggxh = ggxh;
    }

    public String getDw() {
        return this.dw;
    }

    public DRk dw(String dw) {
        this.dw = dw;
        return this;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public DRk price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSl() {
        return this.sl;
    }

    public DRk sl(BigDecimal sl) {
        this.sl = sl;
        return this;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public BigDecimal getJe() {
        return this.je;
    }

    public DRk je(BigDecimal je) {
        this.je = je;
        return this;
    }

    public void setJe(BigDecimal je) {
        this.je = je;
    }

    public Long getSign() {
        return this.sign;
    }

    public DRk sign(Long sign) {
        this.sign = sign;
        return this;
    }

    public void setSign(Long sign) {
        this.sign = sign;
    }

    public String getMemo() {
        return this.memo;
    }

    public DRk memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getFlag() {
        return this.flag;
    }

    public DRk flag(Long flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }

    public String getf1() {
        return this.f1;
    }

    public DRk f1(String f1) {
        this.f1 = f1;
        return this;
    }

    public void setf1(String f1) {
        this.f1 = f1;
    }

    public String getf2() {
        return this.f2;
    }

    public DRk f2(String f2) {
        this.f2 = f2;
        return this;
    }

    public void setf2(String f2) {
        this.f2 = f2;
    }

    public String getf1empn() {
        return this.f1empn;
    }

    public DRk f1empn(String f1empn) {
        this.f1empn = f1empn;
        return this;
    }

    public void setf1empn(String f1empn) {
        this.f1empn = f1empn;
    }

    public String getf2empn() {
        return this.f2empn;
    }

    public DRk f2empn(String f2empn) {
        this.f2empn = f2empn;
        return this;
    }

    public void setf2empn(String f2empn) {
        this.f2empn = f2empn;
    }

    public Instant getf1sj() {
        return this.f1sj;
    }

    public DRk f1sj(Instant f1sj) {
        this.f1sj = f1sj;
        return this;
    }

    public void setf1sj(Instant f1sj) {
        this.f1sj = f1sj;
    }

    public Instant getf2sj() {
        return this.f2sj;
    }

    public DRk f2sj(Instant f2sj) {
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
        if (!(o instanceof DRk)) {
            return false;
        }
        return id != null && id.equals(((DRk) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DRk{" +
            "id=" + getId() +
            ", rkdate='" + getRkdate() + "'" +
            ", depot='" + getDepot() + "'" +
            ", rklx='" + getRklx() + "'" +
            ", rkbillno='" + getRkbillno() + "'" +
            ", company=" + getCompany() +
            ", deptname='" + getDeptname() + "'" +
            ", jbr='" + getJbr() + "'" +
            ", remark='" + getRemark() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", lrdate='" + getLrdate() + "'" +
            ", spbm='" + getSpbm() + "'" +
            ", spmc='" + getSpmc() + "'" +
            ", ggxh='" + getGgxh() + "'" +
            ", dw='" + getDw() + "'" +
            ", price=" + getPrice() +
            ", sl=" + getSl() +
            ", je=" + getJe() +
            ", sign=" + getSign() +
            ", memo='" + getMemo() + "'" +
            ", flag=" + getFlag() +
            ", f1='" + getf1() + "'" +
            ", f2='" + getf2() + "'" +
            ", f1empn='" + getf1empn() + "'" +
            ", f2empn='" + getf2empn() + "'" +
            ", f1sj='" + getf1sj() + "'" +
            ", f2sj='" + getf2sj() + "'" +
            "}";
    }
}
