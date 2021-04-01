package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DXs} entity.
 */
public class DXsDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Instant begintime;

    @NotNull
    private Instant endtime;

    @NotNull
    @Size(max = 30)
    private String ckbillno;

    @Size(max = 50)
    private String depot;

    @NotNull
    private Long kcid;

    @NotNull
    private Long ckid;

    @NotNull
    @Size(max = 30)
    private String spbm;

    @NotNull
    @Size(max = 50)
    private String spmc;

    @Size(max = 20)
    private String unit;

    private BigDecimal rkprice;

    private BigDecimal xsprice;

    private BigDecimal sl;

    private BigDecimal rkje;

    private BigDecimal xsje;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBegintime() {
        return begintime;
    }

    public void setBegintime(Instant begintime) {
        this.begintime = begintime;
    }

    public Instant getEndtime() {
        return endtime;
    }

    public void setEndtime(Instant endtime) {
        this.endtime = endtime;
    }

    public String getCkbillno() {
        return ckbillno;
    }

    public void setCkbillno(String ckbillno) {
        this.ckbillno = ckbillno;
    }

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public Long getKcid() {
        return kcid;
    }

    public void setKcid(Long kcid) {
        this.kcid = kcid;
    }

    public Long getCkid() {
        return ckid;
    }

    public void setCkid(Long ckid) {
        this.ckid = ckid;
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

    public BigDecimal getRkprice() {
        return rkprice;
    }

    public void setRkprice(BigDecimal rkprice) {
        this.rkprice = rkprice;
    }

    public BigDecimal getXsprice() {
        return xsprice;
    }

    public void setXsprice(BigDecimal xsprice) {
        this.xsprice = xsprice;
    }

    public BigDecimal getSl() {
        return sl;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public BigDecimal getRkje() {
        return rkje;
    }

    public void setRkje(BigDecimal rkje) {
        this.rkje = rkje;
    }

    public BigDecimal getXsje() {
        return xsje;
    }

    public void setXsje(BigDecimal xsje) {
        this.xsje = xsje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DXsDTO)) {
            return false;
        }

        DXsDTO dXsDTO = (DXsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dXsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DXsDTO{" +
            "id=" + getId() +
            ", begintime='" + getBegintime() + "'" +
            ", endtime='" + getEndtime() + "'" +
            ", ckbillno='" + getCkbillno() + "'" +
            ", depot='" + getDepot() + "'" +
            ", kcid=" + getKcid() +
            ", ckid=" + getCkid() +
            ", spbm='" + getSpbm() + "'" +
            ", spmc='" + getSpmc() + "'" +
            ", unit='" + getUnit() + "'" +
            ", rkprice=" + getRkprice() +
            ", xsprice=" + getXsprice() +
            ", sl=" + getSl() +
            ", rkje=" + getRkje() +
            ", xsje=" + getXsje() +
            "}";
    }
}
