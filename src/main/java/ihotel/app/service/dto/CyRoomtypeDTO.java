package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.CyRoomtype} entity.
 */
public class CyRoomtypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String rtdm;

    private BigDecimal minc;

    private BigDecimal servicerate;

    @Size(max = 100)
    private String printer;

    private Long printnum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRtdm() {
        return rtdm;
    }

    public void setRtdm(String rtdm) {
        this.rtdm = rtdm;
    }

    public BigDecimal getMinc() {
        return minc;
    }

    public void setMinc(BigDecimal minc) {
        this.minc = minc;
    }

    public BigDecimal getServicerate() {
        return servicerate;
    }

    public void setServicerate(BigDecimal servicerate) {
        this.servicerate = servicerate;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public Long getPrintnum() {
        return printnum;
    }

    public void setPrintnum(Long printnum) {
        this.printnum = printnum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CyRoomtypeDTO)) {
            return false;
        }

        CyRoomtypeDTO cyRoomtypeDTO = (CyRoomtypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, cyRoomtypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CyRoomtypeDTO{" +
            "id=" + getId() +
            ", rtdm='" + getRtdm() + "'" +
            ", minc=" + getMinc() +
            ", servicerate=" + getServicerate() +
            ", printer='" + getPrinter() + "'" +
            ", printnum=" + getPrintnum() +
            "}";
    }
}
