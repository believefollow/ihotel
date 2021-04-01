package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DDepot.
 */
@Entity
@Table(name = "d_depot")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ddepot")
public class DDepot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "depotid", nullable = false)
    private Boolean depotid;

    @NotNull
    @Size(max = 20)
    @Column(name = "depot", length = 20, nullable = false)
    private String depot;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DDepot id(Long id) {
        this.id = id;
        return this;
    }

    public Boolean getDepotid() {
        return this.depotid;
    }

    public DDepot depotid(Boolean depotid) {
        this.depotid = depotid;
        return this;
    }

    public void setDepotid(Boolean depotid) {
        this.depotid = depotid;
    }

    public String getDepot() {
        return this.depot;
    }

    public DDepot depot(String depot) {
        this.depot = depot;
        return this;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DDepot)) {
            return false;
        }
        return id != null && id.equals(((DDepot) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DDepot{" +
            "id=" + getId() +
            ", depotid='" + getDepotid() + "'" +
            ", depot='" + getDepot() + "'" +
            "}";
    }
}
