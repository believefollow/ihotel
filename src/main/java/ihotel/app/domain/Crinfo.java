package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Crinfo.
 */
@Entity
@Table(name = "crinfo")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "crinfo")
public class Crinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operatetime")
    private Instant operatetime;

    @Column(name = "oldrent", precision = 21, scale = 2)
    private BigDecimal oldrent;

    @Column(name = "newrent", precision = 21, scale = 2)
    private BigDecimal newrent;

    @Size(max = 10)
    @Column(name = "oldroomn", length = 10)
    private String oldroomn;

    @Size(max = 10)
    @Column(name = "newroomn", length = 10)
    private String newroomn;

    @Size(max = 30)
    @Column(name = "account", length = 30)
    private String account;

    @Size(max = 10)
    @Column(name = "empn", length = 10)
    private String empn;

    @Column(name = "oldday")
    private Long oldday;

    @Column(name = "newday")
    private Long newday;

    @Column(name = "hoteltime")
    private Instant hoteltime;

    @Size(max = 10)
    @Column(name = "roomn", length = 10)
    private String roomn;

    @NotNull
    @Size(max = 100)
    @Column(name = "memo", length = 100, nullable = false)
    private String memo;

    @Size(max = 50)
    @Column(name = "realname", length = 50)
    private String realname;

    @Column(name = "isup")
    private Long isup;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Crinfo id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getOperatetime() {
        return this.operatetime;
    }

    public Crinfo operatetime(Instant operatetime) {
        this.operatetime = operatetime;
        return this;
    }

    public void setOperatetime(Instant operatetime) {
        this.operatetime = operatetime;
    }

    public BigDecimal getOldrent() {
        return this.oldrent;
    }

    public Crinfo oldrent(BigDecimal oldrent) {
        this.oldrent = oldrent;
        return this;
    }

    public void setOldrent(BigDecimal oldrent) {
        this.oldrent = oldrent;
    }

    public BigDecimal getNewrent() {
        return this.newrent;
    }

    public Crinfo newrent(BigDecimal newrent) {
        this.newrent = newrent;
        return this;
    }

    public void setNewrent(BigDecimal newrent) {
        this.newrent = newrent;
    }

    public String getOldroomn() {
        return this.oldroomn;
    }

    public Crinfo oldroomn(String oldroomn) {
        this.oldroomn = oldroomn;
        return this;
    }

    public void setOldroomn(String oldroomn) {
        this.oldroomn = oldroomn;
    }

    public String getNewroomn() {
        return this.newroomn;
    }

    public Crinfo newroomn(String newroomn) {
        this.newroomn = newroomn;
        return this;
    }

    public void setNewroomn(String newroomn) {
        this.newroomn = newroomn;
    }

    public String getAccount() {
        return this.account;
    }

    public Crinfo account(String account) {
        this.account = account;
        return this;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmpn() {
        return this.empn;
    }

    public Crinfo empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Long getOldday() {
        return this.oldday;
    }

    public Crinfo oldday(Long oldday) {
        this.oldday = oldday;
        return this;
    }

    public void setOldday(Long oldday) {
        this.oldday = oldday;
    }

    public Long getNewday() {
        return this.newday;
    }

    public Crinfo newday(Long newday) {
        this.newday = newday;
        return this;
    }

    public void setNewday(Long newday) {
        this.newday = newday;
    }

    public Instant getHoteltime() {
        return this.hoteltime;
    }

    public Crinfo hoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
        return this;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public Crinfo roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getMemo() {
        return this.memo;
    }

    public Crinfo memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getRealname() {
        return this.realname;
    }

    public Crinfo realname(String realname) {
        this.realname = realname;
        return this;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Long getIsup() {
        return this.isup;
    }

    public Crinfo isup(Long isup) {
        this.isup = isup;
        return this;
    }

    public void setIsup(Long isup) {
        this.isup = isup;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Crinfo)) {
            return false;
        }
        return id != null && id.equals(((Crinfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Crinfo{" +
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
