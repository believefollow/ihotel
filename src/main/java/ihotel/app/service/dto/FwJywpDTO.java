package ihotel.app.service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.FwJywp} entity.
 */
public class FwJywpDTO implements Serializable {

    @NotNull
    private Long id;

    private Instant jyrq;

    @Size(max = 50)
    private String roomn;

    @Size(max = 100)
    private String guestname;

    @Size(max = 200)
    private String jywp;

    @Size(max = 100)
    private String fwy;

    @Size(max = 100)
    private String djr;

    /**
     * 是否归还
     */
    @Size(max = 2)
    @ApiModelProperty(value = "是否归还")
    private String flag;

    private Instant ghrq;

    private Instant djrq;

    @Size(max = 300)
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getJyrq() {
        return jyrq;
    }

    public void setJyrq(Instant jyrq) {
        this.jyrq = jyrq;
    }

    public String getRoomn() {
        return roomn;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getGuestname() {
        return guestname;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public String getJywp() {
        return jywp;
    }

    public void setJywp(String jywp) {
        this.jywp = jywp;
    }

    public String getFwy() {
        return fwy;
    }

    public void setFwy(String fwy) {
        this.fwy = fwy;
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

    public Instant getGhrq() {
        return ghrq;
    }

    public void setGhrq(Instant ghrq) {
        this.ghrq = ghrq;
    }

    public Instant getDjrq() {
        return djrq;
    }

    public void setDjrq(Instant djrq) {
        this.djrq = djrq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FwJywpDTO)) {
            return false;
        }

        FwJywpDTO fwJywpDTO = (FwJywpDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fwJywpDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FwJywpDTO{" +
            "id=" + getId() +
            ", jyrq='" + getJyrq() + "'" +
            ", roomn='" + getRoomn() + "'" +
            ", guestname='" + getGuestname() + "'" +
            ", jywp='" + getJywp() + "'" +
            ", fwy='" + getFwy() + "'" +
            ", djr='" + getDjr() + "'" +
            ", flag='" + getFlag() + "'" +
            ", ghrq='" + getGhrq() + "'" +
            ", djrq='" + getDjrq() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
