package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A CheckinBak.
 */
@Entity
@Table(name = "checkin_bak")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "checkinbak")
public class CheckinBak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "guest_id", nullable = false)
    private Long guestId;

    @NotNull
    @Size(max = 30)
    @Column(name = "account", length = 30, nullable = false)
    private String account;

    @NotNull
    @Column(name = "hoteltime", nullable = false)
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

    @Size(max = 300)
    @Column(name = "roomn", length = 300)
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

    @Size(max = 40)
    @Column(name = "comeinfo", length = 40)
    private String comeinfo;

    @Size(max = 40)
    @Column(name = "goinfo", length = 40)
    private String goinfo;

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

    @Size(max = 10)
    @Column(name = "payment", length = 10)
    private String payment;

    @Size(max = 10)
    @Column(name = "mtype", length = 10)
    private String mtype;

    @Size(max = 8000)
    @Column(name = "memo", length = 8000)
    private String memo;

    @Size(max = 20)
    @Column(name = "flight", length = 20)
    private String flight;

    @Column(name = "credit", precision = 21, scale = 2)
    private BigDecimal credit;

    @Size(max = 10)
    @Column(name = "talklevel", length = 10)
    private String talklevel;

    @Size(max = 1)
    @Column(name = "lf_sign", length = 1)
    private String lfSign;

    @Size(max = 3)
    @Column(name = "keynum", length = 3)
    private String keynum;

    @Column(name = "ic_num")
    private Long icNum;

    @Column(name = "bh")
    private Long bh;

    @Size(max = 50)
    @Column(name = "ic_owner", length = 50)
    private String icOwner;

    @Size(max = 50)
    @Column(name = "mark_id", length = 50)
    private String markId;

    @Size(max = 50)
    @Column(name = "gj", length = 50)
    private String gj;

    @Column(name = "yfj", precision = 21, scale = 2)
    private BigDecimal yfj;

    @Column(name = "hoteldate")
    private Instant hoteldate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CheckinBak id(Long id) {
        this.id = id;
        return this;
    }

    public Long getGuestId() {
        return this.guestId;
    }

    public CheckinBak guestId(Long guestId) {
        this.guestId = guestId;
        return this;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public String getAccount() {
        return this.account;
    }

    public CheckinBak account(String account) {
        this.account = account;
        return this;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Instant getHoteltime() {
        return this.hoteltime;
    }

    public CheckinBak hoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
        return this;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public Instant getIndatetime() {
        return this.indatetime;
    }

    public CheckinBak indatetime(Instant indatetime) {
        this.indatetime = indatetime;
        return this;
    }

    public void setIndatetime(Instant indatetime) {
        this.indatetime = indatetime;
    }

    public Long getResidefate() {
        return this.residefate;
    }

    public CheckinBak residefate(Long residefate) {
        this.residefate = residefate;
        return this;
    }

    public void setResidefate(Long residefate) {
        this.residefate = residefate;
    }

    public Instant getGotime() {
        return this.gotime;
    }

    public CheckinBak gotime(Instant gotime) {
        this.gotime = gotime;
        return this;
    }

    public void setGotime(Instant gotime) {
        this.gotime = gotime;
    }

    public String getEmpn() {
        return this.empn;
    }

    public CheckinBak empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public CheckinBak roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getUname() {
        return this.uname;
    }

    public CheckinBak uname(String uname) {
        this.uname = uname;
        return this;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getRentp() {
        return this.rentp;
    }

    public CheckinBak rentp(String rentp) {
        this.rentp = rentp;
        return this;
    }

    public void setRentp(String rentp) {
        this.rentp = rentp;
    }

    public BigDecimal getProtocolrent() {
        return this.protocolrent;
    }

    public CheckinBak protocolrent(BigDecimal protocolrent) {
        this.protocolrent = protocolrent;
        return this;
    }

    public void setProtocolrent(BigDecimal protocolrent) {
        this.protocolrent = protocolrent;
    }

    public String getRemark() {
        return this.remark;
    }

    public CheckinBak remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getComeinfo() {
        return this.comeinfo;
    }

    public CheckinBak comeinfo(String comeinfo) {
        this.comeinfo = comeinfo;
        return this;
    }

    public void setComeinfo(String comeinfo) {
        this.comeinfo = comeinfo;
    }

    public String getGoinfo() {
        return this.goinfo;
    }

    public CheckinBak goinfo(String goinfo) {
        this.goinfo = goinfo;
        return this;
    }

    public void setGoinfo(String goinfo) {
        this.goinfo = goinfo;
    }

    public String getPhonen() {
        return this.phonen;
    }

    public CheckinBak phonen(String phonen) {
        this.phonen = phonen;
        return this;
    }

    public void setPhonen(String phonen) {
        this.phonen = phonen;
    }

    public String getEmpn2() {
        return this.empn2;
    }

    public CheckinBak empn2(String empn2) {
        this.empn2 = empn2;
        return this;
    }

    public void setEmpn2(String empn2) {
        this.empn2 = empn2;
    }

    public String getAdhoc() {
        return this.adhoc;
    }

    public CheckinBak adhoc(String adhoc) {
        this.adhoc = adhoc;
        return this;
    }

    public void setAdhoc(String adhoc) {
        this.adhoc = adhoc;
    }

    public Long getAuditflag() {
        return this.auditflag;
    }

    public CheckinBak auditflag(Long auditflag) {
        this.auditflag = auditflag;
        return this;
    }

    public void setAuditflag(Long auditflag) {
        this.auditflag = auditflag;
    }

    public String getGroupn() {
        return this.groupn;
    }

    public CheckinBak groupn(String groupn) {
        this.groupn = groupn;
        return this;
    }

    public void setGroupn(String groupn) {
        this.groupn = groupn;
    }

    public String getPayment() {
        return this.payment;
    }

    public CheckinBak payment(String payment) {
        this.payment = payment;
        return this;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getMtype() {
        return this.mtype;
    }

    public CheckinBak mtype(String mtype) {
        this.mtype = mtype;
        return this;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getMemo() {
        return this.memo;
    }

    public CheckinBak memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFlight() {
        return this.flight;
    }

    public CheckinBak flight(String flight) {
        this.flight = flight;
        return this;
    }

    public void setFlight(String flight) {
        this.flight = flight;
    }

    public BigDecimal getCredit() {
        return this.credit;
    }

    public CheckinBak credit(BigDecimal credit) {
        this.credit = credit;
        return this;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public String getTalklevel() {
        return this.talklevel;
    }

    public CheckinBak talklevel(String talklevel) {
        this.talklevel = talklevel;
        return this;
    }

    public void setTalklevel(String talklevel) {
        this.talklevel = talklevel;
    }

    public String getLfSign() {
        return this.lfSign;
    }

    public CheckinBak lfSign(String lfSign) {
        this.lfSign = lfSign;
        return this;
    }

    public void setLfSign(String lfSign) {
        this.lfSign = lfSign;
    }

    public String getKeynum() {
        return this.keynum;
    }

    public CheckinBak keynum(String keynum) {
        this.keynum = keynum;
        return this;
    }

    public void setKeynum(String keynum) {
        this.keynum = keynum;
    }

    public Long getIcNum() {
        return this.icNum;
    }

    public CheckinBak icNum(Long icNum) {
        this.icNum = icNum;
        return this;
    }

    public void setIcNum(Long icNum) {
        this.icNum = icNum;
    }

    public Long getBh() {
        return this.bh;
    }

    public CheckinBak bh(Long bh) {
        this.bh = bh;
        return this;
    }

    public void setBh(Long bh) {
        this.bh = bh;
    }

    public String getIcOwner() {
        return this.icOwner;
    }

    public CheckinBak icOwner(String icOwner) {
        this.icOwner = icOwner;
        return this;
    }

    public void setIcOwner(String icOwner) {
        this.icOwner = icOwner;
    }

    public String getMarkId() {
        return this.markId;
    }

    public CheckinBak markId(String markId) {
        this.markId = markId;
        return this;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public String getGj() {
        return this.gj;
    }

    public CheckinBak gj(String gj) {
        this.gj = gj;
        return this;
    }

    public void setGj(String gj) {
        this.gj = gj;
    }

    public BigDecimal getYfj() {
        return this.yfj;
    }

    public CheckinBak yfj(BigDecimal yfj) {
        this.yfj = yfj;
        return this;
    }

    public void setYfj(BigDecimal yfj) {
        this.yfj = yfj;
    }

    public Instant getHoteldate() {
        return this.hoteldate;
    }

    public CheckinBak hoteldate(Instant hoteldate) {
        this.hoteldate = hoteldate;
        return this;
    }

    public void setHoteldate(Instant hoteldate) {
        this.hoteldate = hoteldate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckinBak)) {
            return false;
        }
        return id != null && id.equals(((CheckinBak) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckinBak{" +
            "id=" + getId() +
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
            "}";
    }
}
