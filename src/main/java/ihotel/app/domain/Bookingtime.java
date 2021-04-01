package ihotel.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Bookingtime.
 */
@Entity
@Table(name = "bookingtime")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "bookingtime")
public class Bookingtime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(name = "bookid", length = 50)
    private String bookid;

    @Size(max = 20)
    @Column(name = "roomn", length = 20)
    private String roomn;

    @Column(name = "booktime")
    private Instant booktime;

    @Size(max = 50)
    @Column(name = "rtype", length = 50)
    private String rtype;

    @Column(name = "sl")
    private Long sl;

    @Size(max = 100)
    @Column(name = "remark", length = 100)
    private String remark;

    @Column(name = "sign")
    private Long sign;

    @Column(name = "rzsign")
    private Long rzsign;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bookingtime id(Long id) {
        this.id = id;
        return this;
    }

    public String getBookid() {
        return this.bookid;
    }

    public Bookingtime bookid(String bookid) {
        this.bookid = bookid;
        return this;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public Bookingtime roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public Instant getBooktime() {
        return this.booktime;
    }

    public Bookingtime booktime(Instant booktime) {
        this.booktime = booktime;
        return this;
    }

    public void setBooktime(Instant booktime) {
        this.booktime = booktime;
    }

    public String getRtype() {
        return this.rtype;
    }

    public Bookingtime rtype(String rtype) {
        this.rtype = rtype;
        return this;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public Long getSl() {
        return this.sl;
    }

    public Bookingtime sl(Long sl) {
        this.sl = sl;
        return this;
    }

    public void setSl(Long sl) {
        this.sl = sl;
    }

    public String getRemark() {
        return this.remark;
    }

    public Bookingtime remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getSign() {
        return this.sign;
    }

    public Bookingtime sign(Long sign) {
        this.sign = sign;
        return this;
    }

    public void setSign(Long sign) {
        this.sign = sign;
    }

    public Long getRzsign() {
        return this.rzsign;
    }

    public Bookingtime rzsign(Long rzsign) {
        this.rzsign = rzsign;
        return this;
    }

    public void setRzsign(Long rzsign) {
        this.rzsign = rzsign;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bookingtime)) {
            return false;
        }
        return id != null && id.equals(((Bookingtime) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bookingtime{" +
            "id=" + getId() +
            ", bookid='" + getBookid() + "'" +
            ", roomn='" + getRoomn() + "'" +
            ", booktime='" + getBooktime() + "'" +
            ", rtype='" + getRtype() + "'" +
            ", sl=" + getSl() +
            ", remark='" + getRemark() + "'" +
            ", sign=" + getSign() +
            ", rzsign=" + getRzsign() +
            "}";
    }
}
