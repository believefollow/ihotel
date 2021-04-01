package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Dayearndetail} entity.
 */
public class DayearndetailDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant earndate;

    @NotNull
    private Long salespotn;

    private BigDecimal money;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getEarndate() {
        return earndate;
    }

    public void setEarndate(Instant earndate) {
        this.earndate = earndate;
    }

    public Long getSalespotn() {
        return salespotn;
    }

    public void setSalespotn(Long salespotn) {
        this.salespotn = salespotn;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DayearndetailDTO)) {
            return false;
        }

        DayearndetailDTO dayearndetailDTO = (DayearndetailDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dayearndetailDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DayearndetailDTO{" +
            "id=" + getId() +
            ", earndate='" + getEarndate() + "'" +
            ", salespotn=" + getSalespotn() +
            ", money=" + getMoney() +
            "}";
    }
}
