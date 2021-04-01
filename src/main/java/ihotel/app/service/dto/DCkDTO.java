package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DCk} entity.
 */
public class DCkDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @Size(max = 20)
    private String depot;

    @NotNull
    private Instant ckdate;

    @NotNull
    @Size(max = 20)
    private String ckbillno;

    @Size(max = 20)
    private String deptname;

    @Size(max = 20)
    private String jbr;

    @Size(max = 100)
    private String remark;

    @NotNull
    @Size(max = 20)
    private String spbm;

    @NotNull
    @Size(max = 50)
    private String spmc;

    @Size(max = 20)
    private String unit;

    private BigDecimal price;

    private BigDecimal sl;

    private BigDecimal je;

    @Size(max = 100)
    private String memo;

    private Long flag;

    private Long dbSign;

    @Size(max = 30)
    private String cktype;

    @Size(max = 20)
    private String empn;

    private Instant lrdate;

    private Double kcid;

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

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public Instant getCkdate() {
        return ckdate;
    }

    public void setCkdate(Instant ckdate) {
        this.ckdate = ckdate;
    }

    public String getCkbillno() {
        return ckbillno;
    }

    public void setCkbillno(String ckbillno) {
        this.ckbillno = ckbillno;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public Long getDbSign() {
        return dbSign;
    }

    public void setDbSign(Long dbSign) {
        this.dbSign = dbSign;
    }

    public String getCktype() {
        return cktype;
    }

    public void setCktype(String cktype) {
        this.cktype = cktype;
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

    public Double getKcid() {
        return kcid;
    }

    public void setKcid(Double kcid) {
        this.kcid = kcid;
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
        if (!(o instanceof DCkDTO)) {
            return false;
        }

        DCkDTO dCkDTO = (DCkDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dCkDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DCkDTO{" +
            "id=" + getId() +
            ", depot='" + getDepot() + "'" +
            ", ckdate='" + getCkdate() + "'" +
            ", ckbillno='" + getCkbillno() + "'" +
            ", deptname='" + getDeptname() + "'" +
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
            ", dbSign=" + getDbSign() +
            ", cktype='" + getCktype() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", lrdate='" + getLrdate() + "'" +
            ", kcid=" + getKcid() +
            ", f1='" + getf1() + "'" +
            ", f2='" + getf2() + "'" +
            ", f1empn='" + getf1empn() + "'" +
            ", f2empn='" + getf2empn() + "'" +
            ", f1sj='" + getf1sj() + "'" +
            ", f2sj='" + getf2sj() + "'" +
            "}";
    }
}
