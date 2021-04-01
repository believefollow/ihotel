package ihotel.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Fwcheck.
 */
@Entity
@Table(name = "fwcheck")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fwcheck")
public class Fwcheck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "roomn", length = 50, nullable = false)
    private String roomn;

    @Column(name = "zlrq")
    private Instant zlrq;

    @Size(max = 50)
    @Column(name = "zlempn", length = 50)
    private String zlempn;

    @Column(name = "okrq")
    private Instant okrq;

    @Size(max = 30)
    @Column(name = "okempn", length = 30)
    private String okempn;

    @Column(name = "flag")
    private Long flag;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fwcheck id(Long id) {
        this.id = id;
        return this;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public Fwcheck roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public Instant getZlrq() {
        return this.zlrq;
    }

    public Fwcheck zlrq(Instant zlrq) {
        this.zlrq = zlrq;
        return this;
    }

    public void setZlrq(Instant zlrq) {
        this.zlrq = zlrq;
    }

    public String getZlempn() {
        return this.zlempn;
    }

    public Fwcheck zlempn(String zlempn) {
        this.zlempn = zlempn;
        return this;
    }

    public void setZlempn(String zlempn) {
        this.zlempn = zlempn;
    }

    public Instant getOkrq() {
        return this.okrq;
    }

    public Fwcheck okrq(Instant okrq) {
        this.okrq = okrq;
        return this;
    }

    public void setOkrq(Instant okrq) {
        this.okrq = okrq;
    }

    public String getOkempn() {
        return this.okempn;
    }

    public Fwcheck okempn(String okempn) {
        this.okempn = okempn;
        return this;
    }

    public void setOkempn(String okempn) {
        this.okempn = okempn;
    }

    public Long getFlag() {
        return this.flag;
    }

    public Fwcheck flag(Long flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(Long flag) {
        this.flag = flag;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fwcheck)) {
            return false;
        }
        return id != null && id.equals(((Fwcheck) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Fwcheck{" +
            "id=" + getId() +
            ", roomn='" + getRoomn() + "'" +
            ", zlrq='" + getZlrq() + "'" +
            ", zlempn='" + getZlempn() + "'" +
            ", okrq='" + getOkrq() + "'" +
            ", okempn='" + getOkempn() + "'" +
            ", flag=" + getFlag() +
            "}";
    }
}
