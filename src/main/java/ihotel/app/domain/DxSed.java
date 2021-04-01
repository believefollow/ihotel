package ihotel.app.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DxSed.
 */
@Entity
@Table(name = "dx_sed")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dxsed")
public class DxSed implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dx_rq")
    private Instant dxRq;

    @Size(max = 2)
    @Column(name = "dx_zt", length = 2)
    private String dxZt;

    @Column(name = "fs_sj")
    private Instant fsSj;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DxSed id(Long id) {
        this.id = id;
        return this;
    }

    public Instant getDxRq() {
        return this.dxRq;
    }

    public DxSed dxRq(Instant dxRq) {
        this.dxRq = dxRq;
        return this;
    }

    public void setDxRq(Instant dxRq) {
        this.dxRq = dxRq;
    }

    public String getDxZt() {
        return this.dxZt;
    }

    public DxSed dxZt(String dxZt) {
        this.dxZt = dxZt;
        return this;
    }

    public void setDxZt(String dxZt) {
        this.dxZt = dxZt;
    }

    public Instant getFsSj() {
        return this.fsSj;
    }

    public DxSed fsSj(Instant fsSj) {
        this.fsSj = fsSj;
        return this;
    }

    public void setFsSj(Instant fsSj) {
        this.fsSj = fsSj;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DxSed)) {
            return false;
        }
        return id != null && id.equals(((DxSed) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DxSed{" +
            "id=" + getId() +
            ", dxRq='" + getDxRq() + "'" +
            ", dxZt='" + getDxZt() + "'" +
            ", fsSj='" + getFsSj() + "'" +
            "}";
    }
}
