package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Cardysq.
 */
@Entity
@Table(name = "cardysq")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cardysq")
public class Cardysq implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 30)
    @Column(name = "roomn", length = 30)
    private String roomn;

    @Size(max = 50)
    @Column(name = "guestname", length = 50)
    private String guestname;

    @Size(max = 50)
    @Column(name = "account", length = 50)
    private String account;

    @Column(name = "rq")
    private Instant rq;

    @Size(max = 100)
    @Column(name = "cardid", length = 100)
    private String cardid;

    @Size(max = 100)
    @Column(name = "djh", length = 100)
    private String djh;

    @Size(max = 100)
    @Column(name = "sqh", length = 100)
    private String sqh;

    @Size(max = 50)
    @Column(name = "empn", length = 50)
    private String empn;

    @Size(max = 2)
    @Column(name = "sign", length = 2)
    private String sign;

    @Column(name = "hoteltime")
    private Instant hoteltime;

    @Column(name = "yxrq")
    private Instant yxrq;

    @Column(name = "je", precision = 21, scale = 2)
    private BigDecimal je;

    @Size(max = 100)
    @Column(name = "ysqmemo", length = 100)
    private String ysqmemo;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cardysq id(Long id) {
        this.id = id;
        return this;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public Cardysq roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getGuestname() {
        return this.guestname;
    }

    public Cardysq guestname(String guestname) {
        this.guestname = guestname;
        return this;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public String getAccount() {
        return this.account;
    }

    public Cardysq account(String account) {
        this.account = account;
        return this;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Instant getRq() {
        return this.rq;
    }

    public Cardysq rq(Instant rq) {
        this.rq = rq;
        return this;
    }

    public void setRq(Instant rq) {
        this.rq = rq;
    }

    public String getCardid() {
        return this.cardid;
    }

    public Cardysq cardid(String cardid) {
        this.cardid = cardid;
        return this;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getDjh() {
        return this.djh;
    }

    public Cardysq djh(String djh) {
        this.djh = djh;
        return this;
    }

    public void setDjh(String djh) {
        this.djh = djh;
    }

    public String getSqh() {
        return this.sqh;
    }

    public Cardysq sqh(String sqh) {
        this.sqh = sqh;
        return this;
    }

    public void setSqh(String sqh) {
        this.sqh = sqh;
    }

    public String getEmpn() {
        return this.empn;
    }

    public Cardysq empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public String getSign() {
        return this.sign;
    }

    public Cardysq sign(String sign) {
        this.sign = sign;
        return this;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Instant getHoteltime() {
        return this.hoteltime;
    }

    public Cardysq hoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
        return this;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public Instant getYxrq() {
        return this.yxrq;
    }

    public Cardysq yxrq(Instant yxrq) {
        this.yxrq = yxrq;
        return this;
    }

    public void setYxrq(Instant yxrq) {
        this.yxrq = yxrq;
    }

    public BigDecimal getJe() {
        return this.je;
    }

    public Cardysq je(BigDecimal je) {
        this.je = je;
        return this;
    }

    public void setJe(BigDecimal je) {
        this.je = je;
    }

    public String getYsqmemo() {
        return this.ysqmemo;
    }

    public Cardysq ysqmemo(String ysqmemo) {
        this.ysqmemo = ysqmemo;
        return this;
    }

    public void setYsqmemo(String ysqmemo) {
        this.ysqmemo = ysqmemo;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cardysq)) {
            return false;
        }
        return id != null && id.equals(((Cardysq) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Cardysq{" +
            "id=" + getId() +
            ", roomn='" + getRoomn() + "'" +
            ", guestname='" + getGuestname() + "'" +
            ", account='" + getAccount() + "'" +
            ", rq='" + getRq() + "'" +
            ", cardid='" + getCardid() + "'" +
            ", djh='" + getDjh() + "'" +
            ", sqh='" + getSqh() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", sign='" + getSign() + "'" +
            ", hoteltime='" + getHoteltime() + "'" +
            ", yxrq='" + getYxrq() + "'" +
            ", je=" + getJe() +
            ", ysqmemo='" + getYsqmemo() + "'" +
            "}";
    }
}
