package ihotel.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A ClassBak.
 */
@Entity
@Table(name = "class_bak")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "classbak")
public class ClassBak implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @Column(name = "empn", length = 100)
    private String empn;

    @Column(name = "dt")
    private Instant dt;

    @Column(name = "rq")
    private Instant rq;

    @Size(max = 100)
    @Column(name = "ghname", length = 100)
    private String ghname;

    @Size(max = 100)
    @Column(name = "bak", length = 100)
    private String bak;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassBak id(Long id) {
        this.id = id;
        return this;
    }

    public String getEmpn() {
        return this.empn;
    }

    public ClassBak empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Instant getDt() {
        return this.dt;
    }

    public ClassBak dt(Instant dt) {
        this.dt = dt;
        return this;
    }

    public void setDt(Instant dt) {
        this.dt = dt;
    }

    public Instant getRq() {
        return this.rq;
    }

    public ClassBak rq(Instant rq) {
        this.rq = rq;
        return this;
    }

    public void setRq(Instant rq) {
        this.rq = rq;
    }

    public String getGhname() {
        return this.ghname;
    }

    public ClassBak ghname(String ghname) {
        this.ghname = ghname;
        return this;
    }

    public void setGhname(String ghname) {
        this.ghname = ghname;
    }

    public String getBak() {
        return this.bak;
    }

    public ClassBak bak(String bak) {
        this.bak = bak;
        return this;
    }

    public void setBak(String bak) {
        this.bak = bak;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassBak)) {
            return false;
        }
        return id != null && id.equals(((ClassBak) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassBak{" +
            "id=" + getId() +
            ", empn='" + getEmpn() + "'" +
            ", dt='" + getDt() + "'" +
            ", rq='" + getRq() + "'" +
            ", ghname='" + getGhname() + "'" +
            ", bak='" + getBak() + "'" +
            "}";
    }
}
