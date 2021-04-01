package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Feetype} entity.
 */
public class FeetypeDTO implements Serializable {

    private Long id;

    @NotNull
    private Long feenum;

    @NotNull
    @Size(max = 45)
    private String feename;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Long sign;

    @Size(max = 45)
    private String beizhu;

    @Size(max = 45)
    private String pym;

    @NotNull
    private Long salespotn;

    @Size(max = 50)
    private String depot;

    private Long cbsign;

    private Long ordersign;

    @Size(max = 20)
    private String hoteldm;

    private Long isnew;

    private BigDecimal ygj;

    @Size(max = 2)
    private String autosign;

    private BigDecimal jj;

    private BigDecimal hyj;

    private BigDecimal dqkc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFeenum() {
        return feenum;
    }

    public void setFeenum(Long feenum) {
        this.feenum = feenum;
    }

    public String getFeename() {
        return feename;
    }

    public void setFeename(String feename) {
        this.feename = feename;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getSign() {
        return sign;
    }

    public void setSign(Long sign) {
        this.sign = sign;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getPym() {
        return pym;
    }

    public void setPym(String pym) {
        this.pym = pym;
    }

    public Long getSalespotn() {
        return salespotn;
    }

    public void setSalespotn(Long salespotn) {
        this.salespotn = salespotn;
    }

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public Long getCbsign() {
        return cbsign;
    }

    public void setCbsign(Long cbsign) {
        this.cbsign = cbsign;
    }

    public Long getOrdersign() {
        return ordersign;
    }

    public void setOrdersign(Long ordersign) {
        this.ordersign = ordersign;
    }

    public String getHoteldm() {
        return hoteldm;
    }

    public void setHoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
    }

    public Long getIsnew() {
        return isnew;
    }

    public void setIsnew(Long isnew) {
        this.isnew = isnew;
    }

    public BigDecimal getYgj() {
        return ygj;
    }

    public void setYgj(BigDecimal ygj) {
        this.ygj = ygj;
    }

    public String getAutosign() {
        return autosign;
    }

    public void setAutosign(String autosign) {
        this.autosign = autosign;
    }

    public BigDecimal getJj() {
        return jj;
    }

    public void setJj(BigDecimal jj) {
        this.jj = jj;
    }

    public BigDecimal getHyj() {
        return hyj;
    }

    public void setHyj(BigDecimal hyj) {
        this.hyj = hyj;
    }

    public BigDecimal getDqkc() {
        return dqkc;
    }

    public void setDqkc(BigDecimal dqkc) {
        this.dqkc = dqkc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeetypeDTO)) {
            return false;
        }

        FeetypeDTO feetypeDTO = (FeetypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, feetypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeetypeDTO{" +
            "id=" + getId() +
            ", feenum=" + getFeenum() +
            ", feename='" + getFeename() + "'" +
            ", price=" + getPrice() +
            ", sign=" + getSign() +
            ", beizhu='" + getBeizhu() + "'" +
            ", pym='" + getPym() + "'" +
            ", salespotn=" + getSalespotn() +
            ", depot='" + getDepot() + "'" +
            ", cbsign=" + getCbsign() +
            ", ordersign=" + getOrdersign() +
            ", hoteldm='" + getHoteldm() + "'" +
            ", isnew=" + getIsnew() +
            ", ygj=" + getYgj() +
            ", autosign='" + getAutosign() + "'" +
            ", jj=" + getJj() +
            ", hyj=" + getHyj() +
            ", dqkc=" + getDqkc() +
            "}";
    }
}
