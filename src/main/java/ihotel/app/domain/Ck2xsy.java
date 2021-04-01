package ihotel.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Ck2xsy.
 */
@Entity
@Table(name = "ck_2_xsy")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ck2xsy")
public class Ck2xsy implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "rq", nullable = false)
    private Instant rq;

    @NotNull
    @Size(max = 12)
    @Column(name = "cpbh", length = 12, nullable = false)
    private String cpbh;

    @NotNull
    @Column(name = "sl", nullable = false)
    private Long sl;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ck2xsy id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getRq() {
        return this.rq;
    }

    public Ck2xsy rq(Instant rq) {
        this.rq = rq;
        return this;
    }

    public void setRq(Instant rq) {
        this.rq = rq;
    }

    public String getCpbh() {
        return this.cpbh;
    }

    public Ck2xsy cpbh(String cpbh) {
        this.cpbh = cpbh;
        return this;
    }

    public void setCpbh(String cpbh) {
        this.cpbh = cpbh;
    }

    public Long getSl() {
        return this.sl;
    }

    public Ck2xsy sl(Long sl) {
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
        if (!(o instanceof Ck2xsy)) {
            return false;
        }
        return id != null && id.equals(((Ck2xsy) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ck2xsy{" +
            "id=" + getId() +
            ", rq='" + getRq() + "'" +
            ", cpbh='" + getCpbh() + "'" +
            ", sl=" + getSl() +
            "}";
    }
}
