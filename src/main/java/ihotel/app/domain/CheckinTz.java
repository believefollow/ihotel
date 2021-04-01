package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A CheckinTz.
 */
@Entity
@Table(name = "checkin_tz")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "checkintz")
public class CheckinTz implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guest_id")
    private Long guestId;

    @Size(max = 30)
    @Column(name = "account", length = 30)
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

    @Size(max = 500)
    @Column(name = "memo", length = 500)
    private String memo;

    @Size(max = 1)
    @Column(name = "lf_sign", length = 1)
    private String lfSign;

    @Size(max = 45)
    @Column(name = "guestname", length = 45)
    private String guestname;

    @Size(max = 50)
    @Column(name = "bc", length = 50)
    private String bc;

    @Size(max = 50)
    @Column(name = "roomtype", length = 50)
    private String roomtype;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CheckinTz id(Long id) {
        this.id = id;
        return this;
    }

    public Long getGuestId() {
        return this.guestId;
    }

    public CheckinTz guestId(Long guestId) {
        this.guestId = guestId;
        return this;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public String getAccount() {
        return this.account;
    }

    public CheckinTz account(String account) {
        this.account = account;
        return this;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Instant getHoteltime() {
        return this.hoteltime;
    }

    public CheckinTz hoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
        return this;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public Instant getIndatetime() {
        return this.indatetime;
    }

    public CheckinTz indatetime(Instant indatetime) {
        this.indatetime = indatetime;
        return this;
    }

    public void setIndatetime(Instant indatetime) {
        this.indatetime = indatetime;
    }

    public Long getResidefate() {
        return this.residefate;
    }

    public CheckinTz residefate(Long residefate) {
        this.residefate = residefate;
        return this;
    }

    public void setResidefate(Long residefate) {
        this.residefate = residefate;
    }

    public Instant getGotime() {
        return this.gotime;
    }

    public CheckinTz gotime(Instant gotime) {
        this.gotime = gotime;
        return this;
    }

    public void setGotime(Instant gotime) {
        this.gotime = gotime;
    }

    public String getEmpn() {
        return this.empn;
    }

    public CheckinTz empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public CheckinTz roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getRentp() {
        return this.rentp;
    }

    public CheckinTz rentp(String rentp) {
        this.rentp = rentp;
        return this;
    }

    public void setRentp(String rentp) {
        this.rentp = rentp;
    }

    public BigDecimal getProtocolrent() {
        return this.protocolrent;
    }

    public CheckinTz protocolrent(BigDecimal protocolrent) {
        this.protocolrent = protocolrent;
        return this;
    }

    public void setProtocolrent(BigDecimal protocolrent) {
        this.protocolrent = protocolrent;
    }

    public String getRemark() {
        return this.remark;
    }

    public CheckinTz remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPhonen() {
        return this.phonen;
    }

    public CheckinTz phonen(String phonen) {
        this.phonen = phonen;
        return this;
    }

    public void setPhonen(String phonen) {
        this.phonen = phonen;
    }

    public String getEmpn2() {
        return this.empn2;
    }

    public CheckinTz empn2(String empn2) {
        this.empn2 = empn2;
        return this;
    }

    public void setEmpn2(String empn2) {
        this.empn2 = empn2;
    }

    public String getMemo() {
        return this.memo;
    }

    public CheckinTz memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getLfSign() {
        return this.lfSign;
    }

    public CheckinTz lfSign(String lfSign) {
        this.lfSign = lfSign;
        return this;
    }

    public void setLfSign(String lfSign) {
        this.lfSign = lfSign;
    }

    public String getGuestname() {
        return this.guestname;
    }

    public CheckinTz guestname(String guestname) {
        this.guestname = guestname;
        return this;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public String getBc() {
        return this.bc;
    }

    public CheckinTz bc(String bc) {
        this.bc = bc;
        return this;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    public String getRoomtype() {
        return this.roomtype;
    }

    public CheckinTz roomtype(String roomtype) {
        this.roomtype = roomtype;
        return this;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckinTz)) {
            return false;
        }
        return id != null && id.equals(((CheckinTz) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckinTz{" +
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
