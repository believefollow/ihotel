package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DDept.
 */
@Entity
@Table(name = "d_dept")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "ddept")
public class DDept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "deptid", nullable = false)
    private Long deptid;

    @NotNull
    @Size(max = 50)
    @Column(name = "deptname", length = 50, nullable = false)
    private String deptname;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DDept id(Long id) {
        this.id = id;
        return this;
    }

    public Long getDeptid() {
        return this.deptid;
    }

    public DDept deptid(Long deptid) {
        this.deptid = deptid;
        return this;
    }

    public void setDeptid(Long deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return this.deptname;
    }

    public DDept deptname(String deptname) {
        this.deptname = deptname;
        return this;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DDept)) {
            return false;
        }
        return id != null && id.equals(((DDept) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DDept{" +
            "id=" + getId() +
            ", deptid=" + getDeptid() +
            ", deptname='" + getDeptname() + "'" +
            "}";
    }
}
