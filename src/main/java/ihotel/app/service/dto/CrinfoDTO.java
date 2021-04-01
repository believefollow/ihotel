package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Crinfo} entity.
 */
public class CrinfoDTO implements Serializable {

    @NotNull
    private Long id;

    private Instant operatetime;

    private BigDecimal oldrent;

    private BigDecimal newrent;

    @Size(max = 10)
    private String oldroomn;

    @Size(max = 10)
    private String newroomn;

    @Size(max = 30)
    private String account;

    @Size(max = 10)
    private String empn;

    private Long oldday;

    private Long newday;

    private Instant hoteltime;

    @Size(max = 10)
    private String roomn;

    @NotNull
    @Size(max = 100)
    private String memo;

    @Size(max = 50)
    private String realname;

    private Long isup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getOperatetime() {
        return operatetime;
    }

    public void setOperatetime(Instant operatetime) {
        this.operatetime = operatetime;
    }

    public BigDecimal getOldrent() {
        return oldrent;
    }

    public void setOldrent(BigDecimal oldrent) {
        this.oldrent = oldrent;
    }

    public BigDecimal getNewrent() {
        return newrent;
    }

    public void setNewrent(BigDecimal newrent) {
        this.newrent = newrent;
    }

    public String getOldroomn() {
        return oldroomn;
    }

    public void setOldroomn(String oldroomn) {
        this.oldroomn = oldroomn;
    }

    public String getNewroomn() {
        return newroomn;
    }

    public void setNewroomn(String newroomn) {
        this.newroomn = newroomn;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Long getOldday() {
        return oldday;
    }

    public void setOldday(Long oldday) {
        this.oldday = oldday;
    }

    public Long getNewday() {
        return newday;
    }

    public void setNewday(Long newday) {
        this.newday = newday;
    }

    public Instant getHoteltime() {
        return hoteltime;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
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

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Long getIsup() {
        return isup;
    }

    public void setIsup(Long isup) {
        this.isup = isup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CrinfoDTO)) {
            return false;
        }

        CrinfoDTO crinfoDTO = (CrinfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, crinfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CrinfoDTO{" +
            "id=" + getId() +
            ", operatetime='" + getOperatetime() + "'" +
            ", oldrent=" + getOldrent() +
            ", newrent=" + getNewrent() +
            ", oldroomn='" + getOldroomn() + "'" +
            ", newroomn='" + getNewroomn() + "'" +
            ", account='" + getAccount() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", oldday=" + getOldday() +
            ", newday=" + getNewday() +
            ", hoteltime='" + getHoteltime() + "'" +
            ", roomn='" + getRoomn() + "'" +
            ", memo='" + getMemo() + "'" +
            ", realname='" + getRealname() + "'" +
            ", isup=" + getIsup() +
            "}";
    }
}
