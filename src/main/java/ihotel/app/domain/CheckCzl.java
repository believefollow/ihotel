package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 夜审房类出租率表
 */
@Entity
@Table(name = "check_czl")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "checkczl")
public class CheckCzl implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "hoteltime", nullable = false)
    private Instant hoteltime;

    @NotNull
    @Size(max = 45)
    @Column(name = "rtype", length = 45, nullable = false)
    private String rtype;

    @NotNull
    @Column(name = "rnum", nullable = false)
    private Long rnum;

    @NotNull
    @Column(name = "r_outnum", nullable = false)
    private Long rOutnum;

    @NotNull
    @Column(name = "czl", precision = 21, scale = 2, nullable = false)
    private BigDecimal czl;

    @NotNull
    @Column(name = "chagrge", precision = 21, scale = 2, nullable = false)
    private BigDecimal chagrge;

    @NotNull
    @Column(name = "chagrge_avg", precision = 21, scale = 2, nullable = false)
    private BigDecimal chagrgeAvg;

    @NotNull
    @Size(max = 45)
    @Column(name = "empn", length = 45, nullable = false)
    private String empn;

    @NotNull
    @Column(name = "entertime", nullable = false)
    private Instant entertime;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CheckCzl id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getHoteltime() {
        return this.hoteltime;
    }

    public CheckCzl hoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
        return this;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public String getRtype() {
        return this.rtype;
    }

    public CheckCzl rtype(String rtype) {
        this.rtype = rtype;
        return this;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public Long getRnum() {
        return this.rnum;
    }

    public CheckCzl rnum(Long rnum) {
        this.rnum = rnum;
        return this;
    }

    public void setRnum(Long rnum) {
        this.rnum = rnum;
    }

    public Long getrOutnum() {
        return this.rOutnum;
    }

    public CheckCzl rOutnum(Long rOutnum) {
        this.rOutnum = rOutnum;
        return this;
    }

    public void setrOutnum(Long rOutnum) {
        this.rOutnum = rOutnum;
    }

    public BigDecimal getCzl() {
        return this.czl;
    }

    public CheckCzl czl(BigDecimal czl) {
        this.czl = czl;
        return this;
    }

    public void setCzl(BigDecimal czl) {
        this.czl = czl;
    }

    public BigDecimal getChagrge() {
        return this.chagrge;
    }

    public CheckCzl chagrge(BigDecimal chagrge) {
        this.chagrge = chagrge;
        return this;
    }

    public void setChagrge(BigDecimal chagrge) {
        this.chagrge = chagrge;
    }

    public BigDecimal getChagrgeAvg() {
        return this.chagrgeAvg;
    }

    public CheckCzl chagrgeAvg(BigDecimal chagrgeAvg) {
        this.chagrgeAvg = chagrgeAvg;
        return this;
    }

    public void setChagrgeAvg(BigDecimal chagrgeAvg) {
        this.chagrgeAvg = chagrgeAvg;
    }

    public String getEmpn() {
        return this.empn;
    }

    public CheckCzl empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Instant getEntertime() {
        return this.entertime;
    }

    public CheckCzl entertime(Instant entertime) {
        this.entertime = entertime;
        return this;
    }

    public void setEntertime(Instant entertime) {
        this.entertime = entertime;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckCzl)) {
            return false;
        }
        return id != null && id.equals(((CheckCzl) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckCzl{" +
            "id=" + getId() +
            ", hoteltime='" + getHoteltime() + "'" +
            ", rtype='" + getRtype() + "'" +
            ", rnum=" + getRnum() +
            ", rOutnum=" + getrOutnum() +
            ", czl=" + getCzl() +
            ", chagrge=" + getChagrge() +
            ", chagrgeAvg=" + getChagrgeAvg() +
            ", empn='" + getEmpn() + "'" +
            ", entertime='" + getEntertime() + "'" +
            "}";
    }
}
