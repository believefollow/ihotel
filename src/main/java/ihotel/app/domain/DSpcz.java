package ihotel.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DSpcz.
 */
@Entity
@Table(name = "d_spcz")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dspcz")
public class DSpcz implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rq")
    private Instant rq;

    @Column(name = "czrq")
    private Instant czrq;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DSpcz id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getRq() {
        return this.rq;
    }

    public DSpcz rq(Instant rq) {
        this.rq = rq;
        return this;
    }

    public void setRq(Instant rq) {
        this.rq = rq;
    }

    public Instant getCzrq() {
        return this.czrq;
    }

    public DSpcz czrq(Instant czrq) {
        this.czrq = czrq;
        return this;
    }

    public void setCzrq(Instant czrq) {
        this.czrq = czrq;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DSpcz)) {
            return false;
        }
        return id != null && id.equals(((DSpcz) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DSpcz{" +
            "id=" + getId() +
            ", rq='" + getRq() + "'" +
            ", czrq='" + getCzrq() + "'" +
            "}";
    }
}
