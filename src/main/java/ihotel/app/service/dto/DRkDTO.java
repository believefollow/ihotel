package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DRk} entity.
 */
public class DRkDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Instant rkdate;

    @NotNull
    @Size(max = 20)
    private String depot;

    @Size(max = 50)
    private String rklx;

    @NotNull
    @Size(max = 20)
    private String rkbillno;

    private Long company;

    @Size(max = 50)
    private String deptname;

    @Size(max = 50)
    private String jbr;

    @Size(max = 100)
    private String remark;

    @Size(max = 50)
    private String empn;

    private Instant lrdate;

    @NotNull
    @Size(max = 20)
    private String spbm;

    @NotNull
    @Size(max = 50)
    private String spmc;

    @Size(max = 20)
    private String ggxh;

    @Size(max = 20)
    private String dw;

    private BigDecimal price;

    private BigDecimal sl;

    private BigDecimal je;

    private Long sign;

    @Size(max = 100)
    private String memo;

    private Long flag;

    @Size(max = 10)
    private String f1;

    @Size(max = 10)
    private String f2;

    @Size(max = 50)
    private String f1empn;

    @Size(max = 50)
    private String f2empn;

    private Instant f1sj;

    private Instant f2sj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRkdate() {
        return rkdate;
    }

    public void setRkdate(Instant rkdate) {
        this.rkdate = rkdate;
    }

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public String getRklx() {
        return rklx;
    }

    public void setRklx(String rklx) {
        this.rklx = rklx;
    }

    public String getRkbillno() {
        return rkbillno;
    }

    public void setRkbillno(String rkbillno) {
        this.rkbillno = rkbillno;
    }

    public Long getCompany() {
        return company;
    }

    public void setCompany(Long company) {
        this.company = company;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    public String getJbr() {
        return jbr;
    }

    public void setJbr(String jbr) {
        this.jbr = jbr;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Instant getLrdate() {
        return lrdate;
    }

    public void setLrdate(Instant lrdate) {
        this.lrdate = lrdate;
    }

    public String getSpbm() {
        return spbm;
    }

    public void setSpbm(String spbm) {
        this.spbm = spbm;
    }

    public String getSpmc() {
        return spmc;
    }

    public void setSpmc(String spmc) {
        this.spmc = spmc;
    }

    public String getGgxh() {
        return ggxh;
    }

    public void setGgxh(String ggxh) {
        this.ggxh = ggxh;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSl() {
        return sl;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public BigDecimal getJe() {
        return je;
    }

    public void setJe(BigDecimal je) {
        this.je = je;
    }

    public Long getSign() {
        return sign;
    }

    public void setSign(Long sign) {
        this.sign = sign;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getFlag() {
        return flag;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }

    public String getf1() {
        return f1;
    }

    public void setf1(String f1) {
        this.f1 = f1;
    }

    public String getf2() {
        return f2;
    }

    public void setf2(String f2) {
        this.f2 = f2;
    }

    public String getf1empn() {
        return f1empn;
    }

    public void setf1empn(String f1empn) {
        this.f1empn = f1empn;
    }

    public String getf2empn() {
        return f2empn;
    }

    public void setf2empn(String f2empn) {
        this.f2empn = f2empn;
    }

    public Instant getf1sj() {
        return f1sj;
    }

    public void setf1sj(Instant f1sj) {
        this.f1sj = f1sj;
    }

    public Instant getf2sj() {
        return f2sj;
    }

    public void setf2sj(Instant f2sj) {
        this.f2sj = f2sj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DRkDTO)) {
            return false;
        }

        DRkDTO dRkDTO = (DRkDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dRkDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DRkDTO{" +
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
