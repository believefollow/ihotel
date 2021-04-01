package ihotel.app.service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.FwYlwp} entity.
 */
public class FwYlwpDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(max = 50)
    private String roomn;

    @Size(max = 100)
    private String guestname;

    @Size(max = 300)
    private String memo;

    @Size(max = 100)
    private String sdr;

    private Instant sdrq;

    @Size(max = 100)
    private String rlr;

    private Instant rlrq;

    @Size(max = 300)
    private String remark;

    @Size(max = 100)
    private String empn;

    private Instant czrq;

    /**
     * 0未认领
     */
    @Size(max = 2)
    @ApiModelProperty(value = "0未认领")
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

    public String getGuestname() {
        return guestname;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSdr() {
        return sdr;
    }

    public void setSdr(String sdr) {
        this.sdr = sdr;
    }

    public Instant getSdrq() {
        return sdrq;
    }

    public void setSdrq(Instant sdrq) {
        this.sdrq = sdrq;
    }

    public String getRlr() {
        return rlr;
    }

    public void setRlr(String rlr) {
        this.rlr = rlr;
    }

    public Instant getRlrq() {
        return rlrq;
    }

    public void setRlrq(Instant rlrq) {
        this.rlrq = rlrq;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Instant getCzrq() {
        return czrq;
    }

    public void setCzrq(Instant czrq) {
        this.czrq = czrq;
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
        if (!(o instanceof FwYlwpDTO)) {
            return false;
        }

        FwYlwpDTO fwYlwpDTO = (FwYlwpDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fwYlwpDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FwYlwpDTO{" +
            "id=" + getId() +
            ", roomn='" + getRoomn() + "'" +
            ", guestname='" + getGuestname() + "'" +
            ", memo='" + getMemo() + "'" +
            ", sdr='" + getSdr() + "'" +
            ", sdrq='" + getSdrq() + "'" +
            ", rlr='" + getRlr() + "'" +
            ", rlrq='" + getRlrq() + "'" +
            ", remark='" + getRemark() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", czrq='" + getCzrq() + "'" +
            ", flag='" + getFlag() + "'" +
            "}";
    }
}
