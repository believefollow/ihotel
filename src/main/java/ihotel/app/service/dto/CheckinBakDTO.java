package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.CheckinBak} entity.
 */
public class CheckinBakDTO implements Serializable {

    @NotNull
    private Long guestId;

    @NotNull
    @Size(max = 30)
    private String account;

    @NotNull
    private Instant hoteltime;

    private Instant indatetime;

    private Long residefate;

    private Instant gotime;

    @Size(max = 10)
    private String empn;

    @Size(max = 300)
    private String roomn;

    @Size(max = 50)
    private String uname;

    @NotNull
    @Size(max = 10)
    private String rentp;

    private BigDecimal protocolrent;

    @Size(max = 8000)
    private String remark;

    @Size(max = 40)
    private String comeinfo;

    @Size(max = 40)
    private String goinfo;

    @Size(max = 10)
    private String phonen;

    @Size(max = 10)
    private String empn2;

    @Size(max = 40)
    private String adhoc;

    private Long auditflag;

    @Size(max = 20)
    private String groupn;

    @Size(max = 10)
    private String payment;

    @Size(max = 10)
    private String mtype;

    @Size(max = 8000)
    private String memo;

    @Size(max = 20)
    private String flight;

    private BigDecimal credit;

    @Size(max = 10)
    private String talklevel;

    @Size(max = 1)
    private String lfSign;

    @Size(max = 3)
    private String keynum;

    private Long icNum;

    private Long bh;

    @Size(max = 50)
    private String icOwner;

    @Size(max = 50)
    private String markId;

    @Size(max = 50)
    private String gj;

    private BigDecimal yfj;

    private Instant hoteldate;

    @NotNull
    private Long id;

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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
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

    public String getComeinfo() {
        return comeinfo;
    }

    public void setComeinfo(String comeinfo) {
        this.comeinfo = comeinfo;
    }

    public String getGoinfo() {
        return goinfo;
    }

    public void setGoinfo(String goinfo) {
        this.goinfo = goinfo;
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

    public String getAdhoc() {
        return adhoc;
    }

    public void setAdhoc(String adhoc) {
        this.adhoc = adhoc;
    }

    public Long getAuditflag() {
        return auditflag;
    }

    public void setAuditflag(Long auditflag) {
        this.auditflag = auditflag;
    }

    public String getGroupn() {
        return groupn;
    }

    public void setGroupn(String groupn) {
        this.groupn = groupn;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFlight() {
        return flight;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getTalklevel() {
        return talklevel;
    }

    public void setTalklevel(String talklevel) {
        this.talklevel = talklevel;
    }

    public String getLfSign() {
        return lfSign;
    }

    public void setLfSign(String lfSign) {
        this.lfSign = lfSign;
    }

    public String getKeynum() {
        return keynum;
    }

    public void setKeynum(String keynum) {
        this.keynum = keynum;
    }

    public Long getIcNum() {
        return icNum;
    }

    public void setIcNum(Long icNum) {
        this.icNum = icNum;
    }

    public Long getBh() {
        return bh;
    }

    public void setBh(Long bh) {
        this.bh = bh;
    }

    public String getIcOwner() {
        return icOwner;
    }

    public void setIcOwner(String icOwner) {
        this.icOwner = icOwner;
    }

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public String getGj() {
        return gj;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public BigDecimal getYfj() {
        return yfj;
    }

    public void setYfj(BigDecimal yfj) {
        this.yfj = yfj;
    }

    public Instant getHoteldate() {
        return hoteldate;
    }

    public void setHoteldate(Instant hoteldate) {
        this.hoteldate = hoteldate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckinBakDTO)) {
            return false;
        }

        CheckinBakDTO checkinBakDTO = (CheckinBakDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, checkinBakDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckinBakDTO{" +
            "guestId=" + getGuestId() +
            ", account='" + getAccount() + "'" +
            ", hoteltime='" + getHoteltime() + "'" +
            ", indatetime='" + getIndatetime() + "'" +
            ", residefate=" + getResidefate() +
            ", gotime='" + getGotime() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", roomn='" + getRoomn() + "'" +
            ", uname='" + getUname() + "'" +
            ", rentp='" + getRentp() + "'" +
            ", protocolrent=" + getProtocolrent() +
            ", remark='" + getRemark() + "'" +
            ", comeinfo='" + getComeinfo() + "'" +
            ", goinfo='" + getGoinfo() + "'" +
            ", phonen='" + getPhonen() + "'" +
            ", empn2='" + getEmpn2() + "'" +
            ", adhoc='" + getAdhoc() + "'" +
            ", auditflag=" + getAuditflag() +
            ", groupn='" + getGroupn() + "'" +
            ", payment='" + getPayment() + "'" +
            ", mtype='" + getMtype() + "'" +
            ", memo='" + getMemo() + "'" +
            ", flight='" + getFlight() + "'" +
            ", credit=" + getCredit() +
            ", talklevel='" + getTalklevel() + "'" +
            ", lfSign='" + getLfSign() + "'" +
            ", keynum='" + getKeynum() + "'" +
            ", icNum=" + getIcNum() +
            ", bh=" + getBh() +
            ", icOwner='" + getIcOwner() + "'" +
            ", markId='" + getMarkId() + "'" +
            ", gj='" + getGj() + "'" +
            ", yfj=" + getYfj() +
            ", hoteldate='" + getHoteldate() + "'" +
            ", id=" + getId() +
            "}";
    }
}
