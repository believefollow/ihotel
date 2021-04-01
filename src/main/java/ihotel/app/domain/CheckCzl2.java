package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 协议出租率
 */
@Entity
@Table(name = "check_czl_2")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "checkczl2")
public class CheckCzl2 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "hoteltime", nullable = false)
    private Instant hoteltime;

    @NotNull
    @Size(max = 45)
    @Column(name = "protocol", length = 45, nullable = false)
    private String protocol;

    @NotNull
    @Column(name = "rnum", nullable = false)
    private Long rnum;

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

    public CheckCzl2 id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getHoteltime() {
        return this.hoteltime;
    }

    public CheckCzl2 hoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
        return this;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public String getProtocol() {
        return this.protocol;
    }

    public CheckCzl2 protocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Long getRnum() {
        return this.rnum;
    }

    public CheckCzl2 rnum(Long rnum) {
        this.rnum = rnum;
        return this;
    }

    public void setRnum(Long rnum) {
        this.rnum = rnum;
    }

    public BigDecimal getCzl() {
        return this.czl;
    }

    public CheckCzl2 czl(BigDecimal czl) {
        this.czl = czl;
        return this;
    }

    public void setCzl(BigDecimal czl) {
        this.czl = czl;
    }

    public BigDecimal getChagrge() {
        return this.chagrge;
    }

    public CheckCzl2 chagrge(BigDecimal chagrge) {
        this.chagrge = chagrge;
        return this;
    }

    public void setChagrge(BigDecimal chagrge) {
        this.chagrge = chagrge;
    }

    public BigDecimal getChagrgeAvg() {
        return this.chagrgeAvg;
    }

    public CheckCzl2 chagrgeAvg(BigDecimal chagrgeAvg) {
        this.chagrgeAvg = chagrgeAvg;
        return this;
    }

    public void setChagrgeAvg(BigDecimal chagrgeAvg) {
        this.chagrgeAvg = chagrgeAvg;
    }

    public String getEmpn() {
        return this.empn;
    }

    public CheckCzl2 empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Instant getEntertime() {
        return this.entertime;
    }

    public CheckCzl2 entertime(Instant entertime) {
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
        if (!(o instanceof CheckCzl2)) {
            return false;
        }
        return id != null && id.equals(((CheckCzl2) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckCzl2{" +
            "id=" + getId() +
            ", hoteltime='" + getHoteltime() + "'" +
            ", protocol='" + getProtocol() + "'" +
            ", rnum=" + getRnum() +
            ", czl=" + getCzl() +
            ", chagrge=" + getChagrge() +
            ", chagrgeAvg=" + getChagrgeAvg() +
            ", empn='" + getEmpn() + "'" +
            ", entertime='" + getEntertime() + "'" +
            "}";
    }
}
