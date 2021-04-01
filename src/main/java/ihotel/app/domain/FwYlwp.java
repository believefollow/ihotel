package ihotel.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A FwYlwp.
 */
@Entity
@Table(name = "fw_ylwp")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fwylwp")
public class FwYlwp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 50)
    @Column(name = "roomn", length = 50)
    private String roomn;

    @Size(max = 100)
    @Column(name = "guestname", length = 100)
    private String guestname;

    @Size(max = 300)
    @Column(name = "memo", length = 300)
    private String memo;

    @Size(max = 100)
    @Column(name = "sdr", length = 100)
    private String sdr;

    @Column(name = "sdrq")
    private Instant sdrq;

    @Size(max = 100)
    @Column(name = "rlr", length = 100)
    private String rlr;

    @Column(name = "rlrq")
    private Instant rlrq;

    @Size(max = 300)
    @Column(name = "remark", length = 300)
    private String remark;

    @Size(max = 100)
    @Column(name = "empn", length = 100)
    private String empn;

    @Column(name = "czrq")
    private Instant czrq;

    /**
     * 0未认领
     */
    @Size(max = 2)
    @Column(name = "flag", length = 2)
    private String flag;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FwYlwp id(Long id) {
        this.id = id;
        return this;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public FwYlwp roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getGuestname() {
        return this.guestname;
    }

    public FwYlwp guestname(String guestname) {
        this.guestname = guestname;
        return this;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public String getMemo() {
        return this.memo;
    }

    public FwYlwp memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSdr() {
        return this.sdr;
    }

    public FwYlwp sdr(String sdr) {
        this.sdr = sdr;
        return this;
    }

    public void setSdr(String sdr) {
        this.sdr = sdr;
    }

    public Instant getSdrq() {
        return this.sdrq;
    }

    public FwYlwp sdrq(Instant sdrq) {
        this.sdrq = sdrq;
        return this;
    }

    public void setSdrq(Instant sdrq) {
        this.sdrq = sdrq;
    }

    public String getRlr() {
        return this.rlr;
    }

    public FwYlwp rlr(String rlr) {
        this.rlr = rlr;
        return this;
    }

    public void setRlr(String rlr) {
        this.rlr = rlr;
    }

    public Instant getRlrq() {
        return this.rlrq;
    }

    public FwYlwp rlrq(Instant rlrq) {
        this.rlrq = rlrq;
        return this;
    }

    public void setRlrq(Instant rlrq) {
        this.rlrq = rlrq;
    }

    public String getRemark() {
        return this.remark;
    }

    public FwYlwp remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEmpn() {
        return this.empn;
    }

    public FwYlwp empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Instant getCzrq() {
        return this.czrq;
    }

    public FwYlwp czrq(Instant czrq) {
        this.czrq = czrq;
        return this;
    }

    public void setCzrq(Instant czrq) {
        this.czrq = czrq;
    }

    public String getFlag() {
        return this.flag;
    }

    public FwYlwp flag(String flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FwYlwp)) {
            return false;
        }
        return id != null && id.equals(((FwYlwp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FwYlwp{" +
            "id=" + getId() +
            ", roomn='" + getRoomn() + "'" +
            ", guestname='" + getGuestname() + "'" +
            ", memo='" + getMemo() + "'" +
            ", sdr='" + getSdr() + "'" +
            ", sdrq='" + getSdrq() + "'" +
            ", rlr='" + getRlr() + "'" +
            ", rlrq='" + getRlrq() + "'" +
            ", remark='" + getRemark() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", czrq='" + getCzrq() + "'" +
            ", flag='" + getFlag() + "'" +
            "}";
    }
}
