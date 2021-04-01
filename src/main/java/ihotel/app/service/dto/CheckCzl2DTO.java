package ihotel.app.service.dto;

import io.swagger.annotations.ApiModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.CheckCzl2} entity.
 */
@ApiModel(description = "协议出租率")
public class CheckCzl2DTO implements Serializable {

    private Long id;

    @NotNull
    private Instant hoteltime;

    @NotNull
    @Size(max = 45)
    private String protocol;

    @NotNull
    private Long rnum;

    @NotNull
    private BigDecimal czl;

    @NotNull
    private BigDecimal chagrge;

    @NotNull
    private BigDecimal chagrgeAvg;

    @NotNull
    @Size(max = 45)
    private String empn;

    @NotNull
    private Instant entertime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getHoteltime() {
        return hoteltime;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Long getRnum() {
        return rnum;
    }

    public void setRnum(Long rnum) {
        this.rnum = rnum;
    }

    public BigDecimal getCzl() {
        return czl;
    }

    public void setCzl(BigDecimal czl) {
        this.czl = czl;
    }

    public BigDecimal getChagrge() {
        return chagrge;
    }

    public void setChagrge(BigDecimal chagrge) {
        this.chagrge = chagrge;
    }

    public BigDecimal getChagrgeAvg() {
        return chagrgeAvg;
    }

    public void setChagrgeAvg(BigDecimal chagrgeAvg) {
        this.chagrgeAvg = chagrgeAvg;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Instant getEntertime() {
        return entertime;
    }

    public void setEntertime(Instant entertime) {
        this.entertime = entertime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckCzl2DTO)) {
            return false;
        }

        CheckCzl2DTO checkCzl2DTO = (CheckCzl2DTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, checkCzl2DTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckCzl2DTO{" +
            "id=" + getId() +
            ", hoteltime='" + getHoteltime() + "'" +
            ", protocol='" + getProtocol() + "'" +
            ", rnum=" + getRnum() +
            ", czl=" + getCzl() +
            ", chagrge=" + getChagrge() +
            ", chagrgeAvg=" + getChagrgeAvg() +
            ", empn='" + getEmpn() + "'" +
            ", entertime='" + getEntertime() + "'" +
            "}";
    }
}
