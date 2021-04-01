package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.CheckinTz} entity.
 */
public class CheckinTzDTO implements Serializable {

    private Long id;

    private Long guestId;

    @Size(max = 30)
    private String account;

    private Instant hoteltime;

    private Instant indatetime;

    private Long residefate;

    private Instant gotime;

    @Size(max = 10)
    private String empn;

    @NotNull
    @Size(max = 10)
    private String roomn;

    @NotNull
    @Size(max = 10)
    private String rentp;

    private BigDecimal protocolrent;

    @Size(max = 8000)
    private String remark;

    @Size(max = 10)
    private String phonen;

    @Size(max = 10)
    private String empn2;

    @Size(max = 500)
    private String memo;

    @Size(max = 1)
    private String lfSign;

    @Size(max = 45)
    private String guestname;

    @Size(max = 50)
    private String bc;

    @Size(max = 50)
    private String roomtype;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Instant getHoteltime() {
        return hoteltime;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public Instant getIndatetime() {
        return indatetime;
    }

    public void setIndatetime(Instant indatetime) {
        this.indatetime = indatetime;
    }

    public Long getResidefate() {
        return residefate;
    }

    public void setResidefate(Long residefate) {
        this.residefate = residefate;
    }

    public Instant getGotime() {
        return gotime;
    }

    public void setGotime(Instant gotime) {
        this.gotime = gotime;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public String getRoomn() {
        return roomn;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getRentp() {
        return rentp;
    }

    public void setRentp(String rentp) {
        this.rentp = rentp;
    }

    public BigDecimal getProtocolrent() {
        return protocolrent;
    }

    public void setProtocolrent(BigDecimal protocolrent) {
        this.protocolrent = protocolrent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhonen() {
        return phonen;
    }

    public void setPhonen(String phonen) {
        this.phonen = phonen;
    }

    public String getEmpn2() {
        return empn2;
    }

    public void setEmpn2(String empn2) {
        this.empn2 = empn2;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLfSign() {
        return lfSign;
    }

    public void setLfSign(String lfSign) {
        this.lfSign = lfSign;
    }

    public String getGuestname() {
        return guestname;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public String getBc() {
        return bc;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckinTzDTO)) {
            return false;
        }

        CheckinTzDTO checkinTzDTO = (CheckinTzDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, checkinTzDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckinTzDTO{" +
            "id=" + getId() +
            ", guestId=" + getGuestId() +
            ", account='" + getAccount() + "'" +
            ", hoteltime='" + getHoteltime() + "'" +
            ", indatetime='" + getIndatetime() + "'" +
            ", residefate=" + getResidefate() +
            ", gotime='" + getGotime() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", roomn='" + getRoomn() + "'" +
            ", rentp='" + getRentp() + "'" +
            ", protocolrent=" + getProtocolrent() +
            ", remark='" + getRemark() + "'" +
            ", phonen='" + getPhonen() + "'" +
            ", empn2='" + getEmpn2() + "'" +
            ", memo='" + getMemo() + "'" +
            ", lfSign='" + getLfSign() + "'" +
            ", guestname='" + getGuestname() + "'" +
            ", bc='" + getBc() + "'" +
            ", roomtype='" + getRoomtype() + "'" +
            "}";
    }
}
