package ihotel.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.FwWxf} entity.
 */
public class FwWxfDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(max = 100)
    private String roomn;

    @Size(max = 200)
    private String memo;

    private Instant djrq;

    @Size(max = 100)
    private String wxr;

    private Instant wcrq;

    @Size(max = 100)
    private String djr;

    @Size(max = 10)
    private String flag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomn() {
        return roomn;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Instant getDjrq() {
        return djrq;
    }

    public void setDjrq(Instant djrq) {
        this.djrq = djrq;
    }

    public String getWxr() {
        return wxr;
    }

    public void setWxr(String wxr) {
        this.wxr = wxr;
    }

    public Instant getWcrq() {
        return wcrq;
    }

    public void setWcrq(Instant wcrq) {
        this.wcrq = wcrq;
    }

    public String getDjr() {
        return djr;
    }

    public void setDjr(String djr) {
        this.djr = djr;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FwWxfDTO)) {
            return false;
        }

        FwWxfDTO fwWxfDTO = (FwWxfDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fwWxfDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FwWxfDTO{" +
            "id=" + getId() +
            ", roomn='" + getRoomn() + "'" +
            ", memo='" + getMemo() + "'" +
            ", djrq='" + getDjrq() + "'" +
            ", wxr='" + getWxr() + "'" +
            ", wcrq='" + getWcrq() + "'" +
            ", djr='" + getDjr() + "'" +
            ", flag='" + getFlag() + "'" +
            "}";
    }
}
