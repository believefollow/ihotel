package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A Adhoc.
 */
@Entity
@Table(name = "adhoc")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "adhoc")
public class Adhoc implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 40)
    @Column(name = "remark", length = 40, nullable = false)
    private String remark;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Adhoc id(String id) {
        this.id = id;
        return this;
    }

    public String getRemark() {
        return this.remark;
    }

    public Adhoc remark(String remark) {
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
        if (!(o instanceof Adhoc)) {
            return false;
        }
        return id != null && id.equals(((Adhoc) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Adhoc{" +
            "id=" + getId() +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
