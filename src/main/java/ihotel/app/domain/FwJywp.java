package ihotel.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A FwJywp.
 */
@Entity
@Table(name = "fw_jywp")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fwjywp")
public class FwJywp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jyrq")
    private Instant jyrq;

    @Size(max = 50)
    @Column(name = "roomn", length = 50)
    private String roomn;

    @Size(max = 100)
    @Column(name = "guestname", length = 100)
    private String guestname;

    @Size(max = 200)
    @Column(name = "jywp", length = 200)
    private String jywp;

    @Size(max = 100)
    @Column(name = "fwy", length = 100)
    private String fwy;

    @Size(max = 100)
    @Column(name = "djr", length = 100)
    private String djr;

    /**
     * 是否归还
     */
    @Size(max = 2)
    @Column(name = "flag", length = 2)
    private String flag;

    @Column(name = "ghrq")
    private Instant ghrq;

    @Column(name = "djrq")
    private Instant djrq;

    @Size(max = 300)
    @Column(name = "remark", length = 300)
    private String remark;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FwJywp id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getJyrq() {
        return this.jyrq;
    }

    public FwJywp jyrq(Instant jyrq) {
        this.jyrq = jyrq;
        return this;
    }

    public void setJyrq(Instant jyrq) {
        this.jyrq = jyrq;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public FwJywp roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getGuestname() {
        return this.guestname;
    }

    public FwJywp guestname(String guestname) {
        this.guestname = guestname;
        return this;
    }

    public void setGuestname(String guestname) {
        this.guestname = guestname;
    }

    public String getJywp() {
        return this.jywp;
    }

    public FwJywp jywp(String jywp) {
        this.jywp = jywp;
        return this;
    }

    public void setJywp(String jywp) {
        this.jywp = jywp;
    }

    public String getFwy() {
        return this.fwy;
    }

    public FwJywp fwy(String fwy) {
        this.fwy = fwy;
        return this;
    }

    public void setFwy(String fwy) {
        this.fwy = fwy;
    }

    public String getDjr() {
        return this.djr;
    }

    public FwJywp djr(String djr) {
        this.djr = djr;
        return this;
    }

    public void setDjr(String djr) {
        this.djr = djr;
    }

    public String getFlag() {
        return this.flag;
    }

    public FwJywp flag(String flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Instant getGhrq() {
        return this.ghrq;
    }

    public FwJywp ghrq(Instant ghrq) {
        this.ghrq = ghrq;
        return this;
    }

    public void setGhrq(Instant ghrq) {
        this.ghrq = ghrq;
    }

    public Instant getDjrq() {
        return this.djrq;
    }

    public FwJywp djrq(Instant djrq) {
        this.djrq = djrq;
        return this;
    }

    public void setDjrq(Instant djrq) {
        this.djrq = djrq;
    }

    public String getRemark() {
        return this.remark;
    }

    public FwJywp remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FwJywp)) {
            return false;
        }
        return id != null && id.equals(((FwJywp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FwJywp{" +
            "id=" + getId() +
            ", jyrq='" + getJyrq() + "'" +
            ", roomn='" + getRoomn() + "'" +
            ", guestname='" + getGuestname() + "'" +
            ", jywp='" + getJywp() + "'" +
            ", fwy='" + getFwy() + "'" +
            ", djr='" + getDjr() + "'" +
            ", flag='" + getFlag() + "'" +
            ", ghrq='" + getGhrq() + "'" +
            ", djrq='" + getDjrq() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
