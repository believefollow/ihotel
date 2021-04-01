package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.CzCzl3} entity.
 */
public class CzCzl3DTO implements Serializable {

    private Long id;

    private Long zfs;

    private BigDecimal kfs;

    @Size(max = 50)
    private String protocoln;

    @Size(max = 50)
    private String roomtype;

    private Long sl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getZfs() {
        return zfs;
    }

    public void setZfs(Long zfs) {
        this.zfs = zfs;
    }

    public BigDecimal getKfs() {
        return kfs;
    }

    public void setKfs(BigDecimal kfs) {
        this.kfs = kfs;
    }

    public String getProtocoln() {
        return protocoln;
    }

    public void setProtocoln(String protocoln) {
        this.protocoln = protocoln;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
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
        if (!(o instanceof CzCzl3DTO)) {
            return false;
        }

        CzCzl3DTO czCzl3DTO = (CzCzl3DTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, czCzl3DTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CzCzl3DTO{" +
            "id=" + getId() +
            ", zfs=" + getZfs() +
            ", kfs=" + getKfs() +
            ", protocoln='" + getProtocoln() + "'" +
            ", roomtype='" + getRoomtype() + "'" +
            ", sl=" + getSl() +
            "}";
    }
}
