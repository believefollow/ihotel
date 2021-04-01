package ihotel.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Auditinfo.
 */
@Entity
@Table(name = "auditinfo")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "auditinfo")
public class Auditinfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "auditdate", nullable = false)
    private Instant auditdate;

    @Column(name = "audittime")
    private Instant audittime;

    @Size(max = 10)
    @Column(name = "empn", length = 10)
    private String empn;

    @Size(max = 100)
    @Column(name = "aidentify", length = 100)
    private String aidentify;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Auditinfo id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getAuditdate() {
        return this.auditdate;
    }

    public Auditinfo auditdate(Instant auditdate) {
        this.auditdate = auditdate;
        return this;
    }

    public void setAuditdate(Instant auditdate) {
        this.auditdate = auditdate;
    }

    public Instant getAudittime() {
        return this.audittime;
    }

    public Auditinfo audittime(Instant audittime) {
        this.audittime = audittime;
        return this;
    }

    public void setAudittime(Instant audittime) {
        this.audittime = audittime;
    }

    public String getEmpn() {
        return this.empn;
    }

    public Auditinfo empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public String getAidentify() {
        return this.aidentify;
    }

    public Auditinfo aidentify(String aidentify) {
        this.aidentify = aidentify;
        return this;
    }

    public void setAidentify(String aidentify) {
        this.aidentify = aidentify;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Auditinfo)) {
            return false;
        }
        return id != null && id.equals(((Auditinfo) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Auditinfo{" +
            "id=" + getId() +
            ", auditdate='" + getAuditdate() + "'" +
            ", audittime='" + getAudittime() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", aidentify='" + getAidentify() + "'" +
            "}";
    }
}
