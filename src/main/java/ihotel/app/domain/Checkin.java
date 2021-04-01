package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Checkin.
 */
@Entity
@Table(name = "checkin")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "checkin")
public class Checkin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 主键
     */
    @NotNull
    @Column(name = "bkid", nullable = false)
    private Long bkid;

    @NotNull
    @Column(name = "guest_id", nullable = false)
    private Long guestId;

    @NotNull
    @Size(max = 30)
    @Column(name = "account", length = 30, nullable = false)
    private String account;

    @Column(name = "hoteltime")
    private Instant hoteltime;

    @Column(name = "indatetime")
    private Instant indatetime;

    @Column(name = "residefate")
    private Long residefate;

    @Column(name = "gotime")
    private Instant gotime;

    @Size(max = 10)
    @Column(name = "empn", length = 10)
    private String empn;

    @NotNull
    @Size(max = 10)
    @Column(name = "roomn", length = 10, nullable = false)
    private String roomn;

    @Size(max = 50)
    @Column(name = "uname", length = 50)
    private String uname;

    @NotNull
    @Size(max = 10)
    @Column(name = "rentp", length = 10, nullable = false)
    private String rentp;

    @Column(name = "protocolrent", precision = 21, scale = 2)
    private BigDecimal protocolrent;

    @Size(max = 8000)
    @Column(name = "remark", length = 8000)
    private String remark;

    @Size(max = 10)
    @Column(name = "phonen", length = 10)
    private String phonen;

    @Size(max = 10)
    @Column(name = "empn_2", length = 10)
    private String empn2;

    @Size(max = 40)
    @Column(name = "adhoc", length = 40)
    private String adhoc;

    @Column(name = "auditflag")
    private Long auditflag;

    @Size(max = 20)
    @Column(name = "groupn", length = 20)
    private String groupn;

    @Size(max = 500)
    @Column(name = "memo", length = 500)
    private String memo;

    @Size(max = 1)
    @Column(name = "lf_sign", length = 1)
    private String lfSign;

    @Size(max = 3)
    @Column(name = "keynum", length = 3)
    private String keynum;

    @Size(max = 50)
    @Column(name = "hykh", length = 50)
    private String hykh;

    @Size(max = 10)
    @Column(name = "bm", length = 10)
    private String bm;

    @Column(name = "flag")
    private Long flag;

    @Column(name = "jxtime")
    private Instant jxtime;

    @Column(name = "jxflag")
    private Long jxflag;

    @Column(name = "checkf")
    private Long checkf;

    @NotNull
    @Size(max = 45)
    @Column(name = "guestname", length = 45, nullable = false)
    private String guestname;

    @NotNull
    @Column(name = "fgf", nullable = false)
    private Long fgf;

    @Size(max = 200)
    @Column(name = "fgxx", length = 200)
    private String fgxx;

    @Column(name = "hour_sign")
    private Long hourSign;

    @Size(max = 20)
    @Column(name = "xsy", length = 20)
    private String xsy;

    @Column(name = "rzsign")
    private Long rzsign;

    @Column(name = "jf")
    private Long jf;

    @Size(max = 50)
    @Column(name = "gname", length = 50)
    private String gname;

    @Column(name = "zcsign")
    private Long zcsign;

    @Column(name = "cqsl")
    private Long cqsl;

    @Column(name = "sfjf")
    private Long sfjf;

    @Size(max = 20)
    @Column(name = "ywly", length = 20)
    private String ywly;

    @Size(max = 2)
    @Column(name = "fk", length = 2)
    private String fk;

    @Column(name = "fkrq")
    private Instant fkrq;

    @Size(max = 50)
    @Column(name = "bc", length = 50)
    private String bc;

    @Size(max = 100)
    @Column(name = "jxremark", length = 100)
    private String jxremark;

    @Column(name = "txid")
    private Double txid;

    @Size(max = 50)
    @Column(name = "cfr", length = 50)
    private String cfr;

    @Size(max = 2)
    @Column(name = "fjbm", length = 2)
    private String fjbm;

    @Size(max = 100)
    @Column(name = "djlx", length = 100)
    private String djlx;

    @Size(max = 100)
    @Column(name = "wlddh", length = 100)
    private String wlddh;

    @Column(name = "fksl", precision = 21, scale = 2)
    private BigDecimal fksl;

    @Size(max = 1)
    @Column(name = "dqtx", length = 1)
    private String dqtx;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Checkin id(Long id) {
        this.id = id;
        return this;
    }

    public Long getBkid() {
        return this.bkid;
    }

    public Checkin bkid(Long bkid) {
        this.bkid = bkid;
        return this;
    }

    public void setBkid(Long bkid) {
        this.bkid = bkid;
    }

    public Long getGuestId() {
        return this.guestId;
    }

    public Checkin guestId(Long guestId) {
        this.guestId = guestId;
        return this;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public String getAccount() {
        return this.account;
    }

    public Checkin account(String account) {
        this.account = account;
        return this;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Instant getHoteltime() {
        return this.hoteltime;
    }

    public Checkin hoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
        return this;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public Instant getIndatetime() {
        return this.indatetime;
    }

    public Checkin indatetime(Instant indatetime) {
        this.indatetime = indatetime;
        return this;
    }

    public void setIndatetime(Instant indatetime) {
        this.indatetime = indatetime;
    }

    public Long getResidefate() {
        return this.residefate;
    }

    public Checkin residefate(Long residefate) {
        this.residefate = residefate;
        return this;
    }

    public void setResidefate(Long residefate) {
        this.residefate = residefate;
    }

    public Instant getGotime() {
        return this.gotime;
    }

    public Checkin gotime(Instant gotime) {
        this.gotime = gotime;
        return this;
    }

    public void setGotime(Instant gotime) {
        this.gotime = gotime;
    }

    public String getEmpn() {
        return this.empn;
    }

    public Checkin empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public Checkin roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getUname() {
        return this.uname;
    }

    public Checkin uname(String uname) {
        this.uname = uname;
        return this;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getRentp() {
        return this.rentp;
    }

    public Checkin rentp(String rentp) {
        this.rentp = rentp;
        return this;
    }

    public void setRentp(String rentp) {
        this.rentp = rentp;
    }

    public BigDecimal getProtocolrent() {
        return this.protocolrent;
    }

    public Checkin protocolrent(BigDecimal protocolrent) {
        this.protocolrent = protocolrent;
        return this;
    }

    public void setProtocolrent(BigDecimal protocolrent) {
        this.protocolrent = protocolrent;
    }

    public String getRemark() {
        return this.remark;
    }

    public Checkin remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhonen() {
        return this.phonen;
    }

    public Checkin phonen(String phonen) {
        this.phonen = phonen;
        return this;
    }

    public void setPhonen(String phonen) {
        this.phonen = phonen;
    }

    public String getEmpn2() {
        return this.empn2;
    }

    public Checkin empn2(String empn2) {
        this.empn2 = empn2;
        return this;
    }

    public void setEmpn2(String empn2) {
        this.empn2 = empn2;
    }

    public String getAdhoc() {
        return this.adhoc;
    }

    public Checkin adhoc(String adhoc) {
        this.adhoc = adhoc;
        return this;
    }

    public void setAdhoc(String adhoc) {
        this.adhoc = adhoc;
    }

    public Long getAuditflag() {
        return this.auditflag;
    }

    public Checkin auditflag(Long auditflag) {
        this.auditflag = auditflag;
        return this;
    }

    public void setAuditflag(Long auditflag) {
        this.auditflag = auditflag;
    }

    public String getGroupn() {
        return this.groupn;
    }

    public Checkin groupn(String groupn) {
        this.groupn = groupn;
        return this;
    }

    public void setGroupn(String groupn) {
        this.groupn = groupn;
    }

    public String getMemo() {
        return this.memo;
    }

    public Checkin memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLfSign() {
        return this.lfSign;
    }

    public Checkin lfSign(String lfSign) {
        this.lfSign = lfSign;
        return this;
    }

    public void setLfSign(String lfSign) {
        this.lfSign = lfSign;
    }

    public String getKeynum() {
        return this.keynum;
    }

    public Checkin keynum(String keynum) {
        this.keynum = keynum;
        return this;
    }

    public void setKeynum(String keynum) {
        this.keynum = keynum;
    }

    public String getHykh() {
        return this.hykh;
    }

    public Checkin hykh(String hykh) {
        this.hykh = hykh;
        return this;
    }

    public void setHykh(String hykh) {
        this.hykh = hykh;
    }

    public String getBm() {
        return this.bm;
    }

    public Checkin bm(String bm) {
        this.bm = bm;
        return this;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public Long getFlag() {
        return this.flag;
    }

    public Checkin flag(Long flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }

    public Instant getJxtime() {
        return this.jxtime;
    }

    public Checkin jxtime(Instant jxtime) {
        this.jxtime = jxtime;
        return this;
    }

    public void setJxtime(Instant jxtime) {
        this.jxtime = jxtime;
    }

    public Long getJxflag() {
        return this.jxflag;
    }

    public Checkin jxflag(Long jxflag) {
        this.jxflag = jxflag;
        return this;
    }

    public void setJxflag(Long jxflag) {
        this.jxflag = jxflag;
    }

    public Long getCheckf() {
        return this.checkf;
    }

    public Checkin checkf(Long checkf) {
        this.checkf = checkf;
        return this;
    }

    public void setCheckf(Long checkf) {
        this.checkf = checkf;
    }

    public String getGuestname() {
        return this.guestname;
    }

    public Checkin guestname(String guestname) {
        this.guestname = guestname;
        return this;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public Long getFgf() {
        return this.fgf;
    }

    public Checkin fgf(Long fgf) {
        this.fgf = fgf;
        return this;
    }

    public void setFgf(Long fgf) {
        this.fgf = fgf;
    }

    public String getFgxx() {
        return this.fgxx;
    }

    public Checkin fgxx(String fgxx) {
        this.fgxx = fgxx;
        return this;
    }

    public void setFgxx(String fgxx) {
        this.fgxx = fgxx;
    }

    public Long getHourSign() {
        return this.hourSign;
    }

    public Checkin hourSign(Long hourSign) {
        this.hourSign = hourSign;
        return this;
    }

    public void setHourSign(Long hourSign) {
        this.hourSign = hourSign;
    }

    public String getXsy() {
        return this.xsy;
    }

    public Checkin xsy(String xsy) {
        this.xsy = xsy;
        return this;
    }

    public void setXsy(String xsy) {
        this.xsy = xsy;
    }

    public Long getRzsign() {
        return this.rzsign;
    }

    public Checkin rzsign(Long rzsign) {
        this.rzsign = rzsign;
        return this;
    }

    public void setRzsign(Long rzsign) {
        this.rzsign = rzsign;
    }

    public Long getJf() {
        return this.jf;
    }

    public Checkin jf(Long jf) {
        this.jf = jf;
        return this;
    }

    public void setJf(Long jf) {
        this.jf = jf;
    }

    public String getGname() {
        return this.gname;
    }

    public Checkin gname(String gname) {
        this.gname = gname;
        return this;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public Long getZcsign() {
        return this.zcsign;
    }

    public Checkin zcsign(Long zcsign) {
        this.zcsign = zcsign;
        return this;
    }

    public void setZcsign(Long zcsign) {
        this.zcsign = zcsign;
    }

    public Long getCqsl() {
        return this.cqsl;
    }

    public Checkin cqsl(Long cqsl) {
        this.cqsl = cqsl;
        return this;
    }

    public void setCqsl(Long cqsl) {
        this.cqsl = cqsl;
    }

    public Long getSfjf() {
        return this.sfjf;
    }

    public Checkin sfjf(Long sfjf) {
        this.sfjf = sfjf;
        return this;
    }

    public void setSfjf(Long sfjf) {
        this.sfjf = sfjf;
    }

    public String getYwly() {
        return this.ywly;
    }

    public Checkin ywly(String ywly) {
        this.ywly = ywly;
        return this;
    }

    public void setYwly(String ywly) {
        this.ywly = ywly;
    }

    public String getFk() {
        return this.fk;
    }

    public Checkin fk(String fk) {
        this.fk = fk;
        return this;
    }

    public void setFk(String fk) {
        this.fk = fk;
    }

    public Instant getFkrq() {
        return this.fkrq;
    }

    public Checkin fkrq(Instant fkrq) {
        this.fkrq = fkrq;
        return this;
    }

    public void setFkrq(Instant fkrq) {
        this.fkrq = fkrq;
    }

    public String getBc() {
        return this.bc;
    }

    public Checkin bc(String bc) {
        this.bc = bc;
        return this;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    public String getJxremark() {
        return this.jxremark;
    }

    public Checkin jxremark(String jxremark) {
        this.jxremark = jxremark;
        return this;
    }

    public void setJxremark(String jxremark) {
        this.jxremark = jxremark;
    }

    public Double getTxid() {
        return this.txid;
    }

    public Checkin txid(Double txid) {
        this.txid = txid;
        return this;
    }

    public void setTxid(Double txid) {
        this.txid = txid;
    }

    public String getCfr() {
        return this.cfr;
    }

    public Checkin cfr(String cfr) {
        this.cfr = cfr;
        return this;
    }

    public void setCfr(String cfr) {
        this.cfr = cfr;
    }

    public String getFjbm() {
        return this.fjbm;
    }

    public Checkin fjbm(String fjbm) {
        this.fjbm = fjbm;
        return this;
    }

    public void setFjbm(String fjbm) {
        this.fjbm = fjbm;
    }

    public String getDjlx() {
        return this.djlx;
    }

    public Checkin djlx(String djlx) {
        this.djlx = djlx;
        return this;
    }

    public void setDjlx(String djlx) {
        this.djlx = djlx;
    }

    public String getWlddh() {
        return this.wlddh;
    }

    public Checkin wlddh(String wlddh) {
        this.wlddh = wlddh;
        return this;
    }

    public void setWlddh(String wlddh) {
        this.wlddh = wlddh;
    }

    public BigDecimal getFksl() {
        return this.fksl;
    }

    public Checkin fksl(BigDecimal fksl) {
        this.fksl = fksl;
        return this;
    }

    public void setFksl(BigDecimal fksl) {
        this.fksl = fksl;
    }

    public String getDqtx() {
        return this.dqtx;
    }

    public Checkin dqtx(String dqtx) {
        this.dqtx = dqtx;
        return this;
    }

    public void setDqtx(String dqtx) {
        this.dqtx = dqtx;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Checkin)) {
            return false;
        }
        return id != null && id.equals(((Checkin) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Checkin{" +
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
