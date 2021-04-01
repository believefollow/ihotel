package ihotel.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A FwDs.
 */
@Entity
@Table(name = "fw_ds")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fwds")
public class FwDs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "hoteltime")
    private Instant hoteltime;

    @Column(name = "rq")
    private Instant rq;

    @Column(name = "xz")
    private Long xz;

    @Size(max = 50)
    @Column(name = "memo", length = 50)
    private String memo;

    @Size(max = 50)
    @Column(name = "fwy", length = 50)
    private String fwy;

    @Size(max = 50)
    @Column(name = "roomn", length = 50)
    private String roomn;

    @Size(max = 100)
    @Column(name = "rtype", length = 100)
    private String rtype;

    @Size(max = 100)
    @Column(name = "empn", length = 100)
    private String empn;

    @Column(name = "sl")
    private Long sl;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FwDs id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getHoteltime() {
        return this.hoteltime;
    }

    public FwDs hoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
        return this;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public Instant getRq() {
        return this.rq;
    }

    public FwDs rq(Instant rq) {
        this.rq = rq;
        return this;
    }

    public void setRq(Instant rq) {
        this.rq = rq;
    }

    public Long getXz() {
        return this.xz;
    }

    public FwDs xz(Long xz) {
        this.xz = xz;
        return this;
    }

    public void setXz(Long xz) {
        this.xz = xz;
    }

    public String getMemo() {
        return this.memo;
    }

    public FwDs memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getFwy() {
        return this.fwy;
    }

    public FwDs fwy(String fwy) {
        this.fwy = fwy;
        return this;
    }

    public void setFwy(String fwy) {
        this.fwy = fwy;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public FwDs roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getRtype() {
        return this.rtype;
    }

    public FwDs rtype(String rtype) {
        this.rtype = rtype;
        return this;
    }

    public void setRtype(String rtype) {
        this.rtype = rtype;
    }

    public String getEmpn() {
        return this.empn;
    }

    public FwDs empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Long getSl() {
        return this.sl;
    }

    public FwDs sl(Long sl) {
        this.sl = sl;
        return this;
    }

    public void setSl(Long sl) {
        this.sl = sl;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FwDs)) {
            return false;
        }
        return id != null && id.equals(((FwDs) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FwDs{" +
            "id=" + getId() +
            ", hoteltime='" + getHoteltime() + "'" +
            ", rq='" + getRq() + "'" +
            ", xz=" + getXz() +
            ", memo='" + getMemo() + "'" +
            ", fwy='" + getFwy() + "'" +
            ", roomn='" + getRoomn() + "'" +
            ", rtype='" + getRtype() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", sl=" + getSl() +
            "}";
    }
}
