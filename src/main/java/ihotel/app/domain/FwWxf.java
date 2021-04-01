package ihotel.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A FwWxf.
 */
@Entity
@Table(name = "fw_wxf")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fwwxf")
public class FwWxf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "roomn", length = 100)
    private String roomn;

    @Size(max = 200)
    @Column(name = "memo", length = 200)
    private String memo;

    @Column(name = "djrq")
    private Instant djrq;

    @Size(max = 100)
    @Column(name = "wxr", length = 100)
    private String wxr;

    @Column(name = "wcrq")
    private Instant wcrq;

    @Size(max = 100)
    @Column(name = "djr", length = 100)
    private String djr;

    @Size(max = 10)
    @Column(name = "flag", length = 10)
    private String flag;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FwWxf id(Long id) {
        this.id = id;
        return this;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public FwWxf roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public String getMemo() {
        return this.memo;
    }

    public FwWxf memo(String memo) {
        this.memo = memo;
        return this;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Instant getDjrq() {
        return this.djrq;
    }

    public FwWxf djrq(Instant djrq) {
        this.djrq = djrq;
        return this;
    }

    public void setDjrq(Instant djrq) {
        this.djrq = djrq;
    }

    public String getWxr() {
        return this.wxr;
    }

    public FwWxf wxr(String wxr) {
        this.wxr = wxr;
        return this;
    }

    public void setWxr(String wxr) {
        this.wxr = wxr;
    }

    public Instant getWcrq() {
        return this.wcrq;
    }

    public FwWxf wcrq(Instant wcrq) {
        this.wcrq = wcrq;
        return this;
    }

    public void setWcrq(Instant wcrq) {
        this.wcrq = wcrq;
    }

    public String getDjr() {
        return this.djr;
    }

    public FwWxf djr(String djr) {
        this.djr = djr;
        return this;
    }

    public void setDjr(String djr) {
        this.djr = djr;
    }

    public String getFlag() {
        return this.flag;
    }

    public FwWxf flag(String flag) {
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
        if (!(o instanceof FwWxf)) {
            return false;
        }
        return id != null && id.equals(((FwWxf) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FwWxf{" +
            "id=" + getId() +
            ", roomn='" + getRoomn() + "'" +
            ", memo='" + getMemo() + "'" +
            ", djrq='" + getDjrq() + "'" +
            ", wxr='" + getWxr() + "'" +
            ", wcrq='" + getWcrq() + "'" +
            ", djr='" + getDjr() + "'" +
            ", flag='" + getFlag() + "'" +
            "}";
    }
}
