package ihotel.app.service.dto;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Checkin} entity.
 */
public class CheckinDTO implements Serializable {

    private Long id;

    /**
     * 主键
     */
    @NotNull
    @ApiModelProperty(value = "主键", required = true)
    private Long bkid;

    @NotNull
    private Long guestId;

    @NotNull
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

    @Size(max = 50)
    private String uname;

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

    @Size(max = 40)
    private String adhoc;

    private Long auditflag;

    @Size(max = 20)
    private String groupn;

    @Size(max = 500)
    private String memo;

    @Size(max = 1)
    private String lfSign;

    @Size(max = 3)
    private String keynum;

    @Size(max = 50)
    private String hykh;

    @Size(max = 10)
    private String bm;

    private Long flag;

    private Instant jxtime;

    private Long jxflag;

    private Long checkf;

    @NotNull
    @Size(max = 45)
    private String guestname;

    @NotNull
    private Long fgf;

    @Size(max = 200)
    private String fgxx;

    private Long hourSign;

    @Size(max = 20)
    private String xsy;

    private Long rzsign;

    private Long jf;

    @Size(max = 50)
    private String gname;

    private Long zcsign;

    private Long cqsl;

    private Long sfjf;

    @Size(max = 20)
    private String ywly;

    @Size(max = 2)
    private String fk;

    private Instant fkrq;

    @Size(max = 50)
    private String bc;

    @Size(max = 100)
    private String jxremark;

    private Double txid;

    @Size(max = 50)
    private String cfr;

    @Size(max = 2)
    private String fjbm;

    @Size(max = 100)
    private String djlx;

    @Size(max = 100)
    private String wlddh;

    private BigDecimal fksl;

    @Size(max = 1)
    private String dqtx;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBkid() {
        return bkid;
    }

    public void setBkid(Long bkid) {
        this.bkid = bkid;
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

    public String getKeynum() {
        return keynum;
    }

    public void setKeynum(String keynum) {
        this.keynum = keynum;
    }

    public String getHykh() {
        return hykh;
    }

    public void setHykh(String hykh) {
        this.hykh = hykh;
    }

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public Long getFlag() {
        return flag;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }

    public Instant getJxtime() {
        return jxtime;
    }

    public void setJxtime(Instant jxtime) {
        this.jxtime = jxtime;
    }

    public Long getJxflag() {
        return jxflag;
    }

    public void setJxflag(Long jxflag) {
        this.jxflag = jxflag;
    }

    public Long getCheckf() {
        return checkf;
    }

    public void setCheckf(Long checkf) {
        this.checkf = checkf;
    }

    public String getGuestname() {
        return guestname;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public Long getFgf() {
        return fgf;
    }

    public void setFgf(Long fgf) {
        this.fgf = fgf;
    }

    public String getFgxx() {
        return fgxx;
    }

    public void setFgxx(String fgxx) {
        this.fgxx = fgxx;
    }

    public Long getHourSign() {
        return hourSign;
    }

    public void setHourSign(Long hourSign) {
        this.hourSign = hourSign;
    }

    public String getXsy() {
        return xsy;
    }

    public void setXsy(String xsy) {
        this.xsy = xsy;
    }

    public Long getRzsign() {
        return rzsign;
    }

    public void setRzsign(Long rzsign) {
        this.rzsign = rzsign;
    }

    public Long getJf() {
        return jf;
    }

    public void setJf(Long jf) {
        this.jf = jf;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public Long getZcsign() {
        return zcsign;
    }

    public void setZcsign(Long zcsign) {
        this.zcsign = zcsign;
    }

    public Long getCqsl() {
        return cqsl;
    }

    public void setCqsl(Long cqsl) {
        this.cqsl = cqsl;
    }

    public Long getSfjf() {
        return sfjf;
    }

    public void setSfjf(Long sfjf) {
        this.sfjf = sfjf;
    }

    public String getYwly() {
        return ywly;
    }

    public void setYwly(String ywly) {
        this.ywly = ywly;
    }

    public String getFk() {
        return fk;
    }

    public void setFk(String fk) {
        this.fk = fk;
    }

    public Instant getFkrq() {
        return fkrq;
    }

    public void setFkrq(Instant fkrq) {
        this.fkrq = fkrq;
    }

    public String getBc() {
        return bc;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    public String getJxremark() {
        return jxremark;
    }

    public void setJxremark(String jxremark) {
        this.jxremark = jxremark;
    }

    public Double getTxid() {
        return txid;
    }

    public void setTxid(Double txid) {
        this.txid = txid;
    }

    public String getCfr() {
        return cfr;
    }

    public void setCfr(String cfr) {
        this.cfr = cfr;
    }

    public String getFjbm() {
        return fjbm;
    }

    public void setFjbm(String fjbm) {
        this.fjbm = fjbm;
    }

    public String getDjlx() {
        return djlx;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getWlddh() {
        return wlddh;
    }

    public void setWlddh(String wlddh) {
        this.wlddh = wlddh;
    }

    public BigDecimal getFksl() {
        return fksl;
    }

    public void setFksl(BigDecimal fksl) {
        this.fksl = fksl;
    }

    public String getDqtx() {
        return dqtx;
    }

    public void setDqtx(String dqtx) {
        this.dqtx = dqtx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckinDTO)) {
            return false;
        }

        CheckinDTO checkinDTO = (CheckinDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, checkinDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckinDTO{" +
            "id=" + getId() +
            ", bkid=" + getBkid() +
            ", guestId=" + getGuestId() +
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
            ", phonen='" + getPhonen() + "'" +
            ", empn2='" + getEmpn2() + "'" +
            ", adhoc='" + getAdhoc() + "'" +
            ", auditflag=" + getAuditflag() +
            ", groupn='" + getGroupn() + "'" +
            ", memo='" + getMemo() + "'" +
            ", lfSign='" + getLfSign() + "'" +
            ", keynum='" + getKeynum() + "'" +
            ", hykh='" + getHykh() + "'" +
            ", bm='" + getBm() + "'" +
            ", flag=" + getFlag() +
            ", jxtime='" + getJxtime() + "'" +
            ", jxflag=" + getJxflag() +
            ", checkf=" + getCheckf() +
            ", guestname='" + getGuestname() + "'" +
            ", fgf=" + getFgf() +
            ", fgxx='" + getFgxx() + "'" +
            ", hourSign=" + getHourSign() +
            ", xsy='" + getXsy() + "'" +
            ", rzsign=" + getRzsign() +
            ", jf=" + getJf() +
            ", gname='" + getGname() + "'" +
            ", zcsign=" + getZcsign() +
            ", cqsl=" + getCqsl() +
            ", sfjf=" + getSfjf() +
            ", ywly='" + getYwly() + "'" +
            ", fk='" + getFk() + "'" +
            ", fkrq='" + getFkrq() + "'" +
            ", bc='" + getBc() + "'" +
            ", jxremark='" + getJxremark() + "'" +
            ", txid=" + getTxid() +
            ", cfr='" + getCfr() + "'" +
            ", fjbm='" + getFjbm() + "'" +
            ", djlx='" + getDjlx() + "'" +
            ", wlddh='" + getWlddh() + "'" +
            ", fksl=" + getFksl() +
            ", dqtx='" + getDqtx() + "'" +
            "}";
    }
}
