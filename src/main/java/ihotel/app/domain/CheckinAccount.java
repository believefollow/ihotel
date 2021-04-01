package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A CheckinAccount.
 */
@Entity
@Table(name = "checkin_account")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "checkinaccount")
public class CheckinAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "account", length = 30, nullable = false)
    private String account;

    @Size(max = 10)
    @Column(name = "roomn", length = 10)
    private String roomn;

    @Column(name = "indatetime")
    private Instant indatetime;

    @Column(name = "gotime")
    private Instant gotime;

    @Column(name = "kfang", precision = 21, scale = 2)
    private BigDecimal kfang;

    @Column(name = "dhua", precision = 21, scale = 2)
    private BigDecimal dhua;

    @Column(name = "minin", precision = 21, scale = 2)
    private BigDecimal minin;

    @Column(name = "peich", precision = 21, scale = 2)
    private BigDecimal peich;

    @Column(name = "qit", precision = 21, scale = 2)
    private BigDecimal qit;

    @Column(name = "total", precision = 21, scale = 2)
    private BigDecimal total;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CheckinAccount id(Long id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return this.account;
    }

    public CheckinAccount account(String account) {
        this.account = account;
        return this;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public CheckinAccount roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public Instant getIndatetime() {
        return this.indatetime;
    }

    public CheckinAccount indatetime(Instant indatetime) {
        this.indatetime = indatetime;
        return this;
    }

    public void setIndatetime(Instant indatetime) {
        this.indatetime = indatetime;
    }

    public Instant getGotime() {
        return this.gotime;
    }

    public CheckinAccount gotime(Instant gotime) {
        this.gotime = gotime;
        return this;
    }

    public void setGotime(Instant gotime) {
        this.gotime = gotime;
    }

    public BigDecimal getKfang() {
        return this.kfang;
    }

    public CheckinAccount kfang(BigDecimal kfang) {
        this.kfang = kfang;
        return this;
    }

    public void setKfang(BigDecimal kfang) {
        this.kfang = kfang;
    }

    public BigDecimal getDhua() {
        return this.dhua;
    }

    public CheckinAccount dhua(BigDecimal dhua) {
        this.dhua = dhua;
        return this;
    }

    public void setDhua(BigDecimal dhua) {
        this.dhua = dhua;
    }

    public BigDecimal getMinin() {
        return this.minin;
    }

    public CheckinAccount minin(BigDecimal minin) {
        this.minin = minin;
        return this;
    }

    public void setMinin(BigDecimal minin) {
        this.minin = minin;
    }

    public BigDecimal getPeich() {
        return this.peich;
    }

    public CheckinAccount peich(BigDecimal peich) {
        this.peich = peich;
        return this;
    }

    public void setPeich(BigDecimal peich) {
        this.peich = peich;
    }

    public BigDecimal getQit() {
        return this.qit;
    }

    public CheckinAccount qit(BigDecimal qit) {
        this.qit = qit;
        return this;
    }

    public void setQit(BigDecimal qit) {
        this.qit = qit;
    }

    public BigDecimal getTotal() {
        return this.total;
    }

    public CheckinAccount total(BigDecimal total) {
        this.total = total;
        return this;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckinAccount)) {
            return false;
        }
        return id != null && id.equals(((CheckinAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckinAccount{" +
            "id=" + getId() +
            ", account='" + getAccount() + "'" +
            ", roomn='" + getRoomn() + "'" +
            ", indatetime='" + getIndatetime() + "'" +
            ", gotime='" + getGotime() + "'" +
            ", kfang=" + getKfang() +
            ", dhua=" + getDhua() +
            ", minin=" + getMinin() +
            ", peich=" + getPeich() +
            ", qit=" + getQit() +
            ", total=" + getTotal() +
            "}";
    }
}
