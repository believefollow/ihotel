package ihotel.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.FwDs} entity.
 */
public class FwDsDTO implements Serializable {

    @NotNull
    private Long id;

    private Instant hoteltime;

    private Instant rq;

    private Long xz;

    @Size(max = 50)
    private String memo;

    @Size(max = 50)
    private String fwy;

    @Size(max = 50)
    private String roomn;

    @Size(max = 100)
    private String rtype;

    @Size(max = 100)
    private String empn;

    private Long sl;

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

    public Instant getRq() {
        return rq;
    }

    public void setRq(Instant rq) {
        this.rq = rq;
    }

    public Long getXz() {
        return xz;
    }

    public void setXz(Long xz) {
        this.xz = xz;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFwy() {
        return fwy;
    }

    public void setFwy(String fwy) {
        this.fwy = fwy;
    }

    public String getRoomn() {
        return roomn;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getRtype() {
        return rtype;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Long getSl() {
        return sl;
    }

    public void setSl(Long sl) {
        this.sl = sl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FwDsDTO)) {
            return false;
        }

        FwDsDTO fwDsDTO = (FwDsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fwDsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FwDsDTO{" +
            "id=" + getId() +
            ", hoteltime='" + getHoteltime() + "'" +
            ", rq='" + getRq() + "'" +
            ", xz=" + getXz() +
            ", memo='" + getMemo() + "'" +
            ", fwy='" + getFwy() + "'" +
            ", roomn='" + getRoomn() + "'" +
            ", rtype='" + getRtype() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", sl=" + getSl() +
            "}";
    }
}
