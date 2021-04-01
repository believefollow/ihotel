package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DType.
 */
@Entity
@Table(name = "d_type")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dtype")
public class DType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "typeid", nullable = false)
    private Long typeid;

    @NotNull
    @Size(max = 50)
    @Column(name = "typename", length = 50, nullable = false)
    private String typename;

    @NotNull
    @Column(name = "fatherid", nullable = false)
    private Long fatherid;

    @Column(name = "disabled")
    private Long disabled;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DType id(Long id) {
        this.id = id;
        return this;
    }

    public Long getTypeid() {
        return this.typeid;
    }

    public DType typeid(Long typeid) {
        this.typeid = typeid;
        return this;
    }

    public void setTypeid(Long typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return this.typename;
    }

    public DType typename(String typename) {
        this.typename = typename;
        return this;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Long getFatherid() {
        return this.fatherid;
    }

    public DType fatherid(Long fatherid) {
        this.fatherid = fatherid;
        return this;
    }

    public void setFatherid(Long fatherid) {
        this.fatherid = fatherid;
    }

    public Long getDisabled() {
        return this.disabled;
    }

    public DType disabled(Long disabled) {
        this.disabled = disabled;
        return this;
    }

    public void setDisabled(Long disabled) {
        this.disabled = disabled;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DType)) {
            return false;
        }
        return id != null && id.equals(((DType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DType{" +
            "id=" + getId() +
            ", typeid=" + getTypeid() +
            ", typename='" + getTypename() + "'" +
            ", fatherid=" + getFatherid() +
            ", disabled=" + getDisabled() +
            "}";
    }
}
