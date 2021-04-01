package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DKc} entity.
 */
public class DKcDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    @Size(max = 20)
    private String depot;

    @NotNull
    @Size(max = 20)
    private String spbm;

    @NotNull
    @Size(max = 50)
    private String spmc;

    @Size(max = 50)
    private String xh;

    @Size(max = 20)
    private String dw;

    private BigDecimal price;

    private BigDecimal sl;

    private BigDecimal je;

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

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DKcDTO)) {
            return false;
        }

        DKcDTO dKcDTO = (DKcDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dKcDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DKcDTO{" +
            "id=" + getId() +
            ", depot='" + getDepot() + "'" +
            ", spbm='" + getSpbm() + "'" +
            ", spmc='" + getSpmc() + "'" +
            ", xh='" + getXh() + "'" +
            ", dw='" + getDw() + "'" +
            ", price=" + getPrice() +
            ", sl=" + getSl() +
            ", je=" + getJe() +
            "}";
    }
}
