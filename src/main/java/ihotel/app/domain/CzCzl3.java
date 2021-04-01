package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A CzCzl3.
 */
@Entity
@Table(name = "cz_czl_3")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "czczl3")
public class CzCzl3 implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "zfs")
    private Long zfs;

    @Column(name = "kfs", precision = 21, scale = 2)
    private BigDecimal kfs;

    @Size(max = 50)
    @Column(name = "protocoln", length = 50)
    private String protocoln;

    @Size(max = 50)
    @Column(name = "roomtype", length = 50)
    private String roomtype;

    @Column(name = "sl")
    private Long sl;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CzCzl3 id(Long id) {
        this.id = id;
        return this;
    }

    public Long getZfs() {
        return this.zfs;
    }

    public CzCzl3 zfs(Long zfs) {
        this.zfs = zfs;
        return this;
    }

    public void setZfs(Long zfs) {
        this.zfs = zfs;
    }

    public BigDecimal getKfs() {
        return this.kfs;
    }

    public CzCzl3 kfs(BigDecimal kfs) {
        this.kfs = kfs;
        return this;
    }

    public void setKfs(BigDecimal kfs) {
        this.kfs = kfs;
    }

    public String getProtocoln() {
        return this.protocoln;
    }

    public CzCzl3 protocoln(String protocoln) {
        this.protocoln = protocoln;
        return this;
    }

    public void setProtocoln(String protocoln) {
        this.protocoln = protocoln;
    }

    public String getRoomtype() {
        return this.roomtype;
    }

    public CzCzl3 roomtype(String roomtype) {
        this.roomtype = roomtype;
        return this;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public Long getSl() {
        return this.sl;
    }

    public CzCzl3 sl(Long sl) {
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
        if (!(o instanceof CzCzl3)) {
            return false;
        }
        return id != null && id.equals(((CzCzl3) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CzCzl3{" +
            "id=" + getId() +
            ", zfs=" + getZfs() +
            ", kfs=" + getKfs() +
            ", protocoln='" + getProtocoln() + "'" +
            ", roomtype='" + getRoomtype() + "'" +
            ", sl=" + getSl() +
            "}";
    }
}
