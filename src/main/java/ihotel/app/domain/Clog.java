package ihotel.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Clog.
 */
@Entity
@Table(name = "clog")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "clog")
public class Clog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    @Column(name = "empn", length = 20)
    private String empn;

    @Column(name = "begindate")
    private Instant begindate;

    @Column(name = "enddate")
    private Instant enddate;

    @Column(name = "dqrq")
    private Instant dqrq;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Clog id(Long id) {
        this.id = id;
        return this;
    }

    public String getEmpn() {
        return this.empn;
    }

    public Clog empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Instant getBegindate() {
        return this.begindate;
    }

    public Clog begindate(Instant begindate) {
        this.begindate = begindate;
        return this;
    }

    public void setBegindate(Instant begindate) {
        this.begindate = begindate;
    }

    public Instant getEnddate() {
        return this.enddate;
    }

    public Clog enddate(Instant enddate) {
        this.enddate = enddate;
        return this;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public Instant getDqrq() {
        return this.dqrq;
    }

    public Clog dqrq(Instant dqrq) {
        this.dqrq = dqrq;
        return this;
    }

    public void setDqrq(Instant dqrq) {
        this.dqrq = dqrq;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Clog)) {
            return false;
        }
        return id != null && id.equals(((Clog) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Clog{" +
            "id=" + getId() +
            ", empn='" + getEmpn() + "'" +
            ", begindate='" + getBegindate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", dqrq='" + getDqrq() + "'" +
            "}";
    }
}
