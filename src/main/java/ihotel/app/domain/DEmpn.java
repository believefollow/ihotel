package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DEmpn.
 */
@Entity
@Table(name = "d_empn")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dempn")
public class DEmpn implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "empnid", nullable = false)
    private Long empnid;

    @NotNull
    @Size(max = 50)
    @Column(name = "empn", length = 50, nullable = false)
    private String empn;

    @Column(name = "deptid")
    private Long deptid;

    @Size(max = 50)
    @Column(name = "phone", length = 50)
    private String phone;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DEmpn id(Long id) {
        this.id = id;
        return this;
    }

    public Long getEmpnid() {
        return this.empnid;
    }

    public DEmpn empnid(Long empnid) {
        this.empnid = empnid;
        return this;
    }

    public void setEmpnid(Long empnid) {
        this.empnid = empnid;
    }

    public String getEmpn() {
        return this.empn;
    }

    public DEmpn empn(String empn) {
        this.empn = empn;
        return this;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Long getDeptid() {
        return this.deptid;
    }

    public DEmpn deptid(Long deptid) {
        this.deptid = deptid;
        return this;
    }

    public void setDeptid(Long deptid) {
        this.deptid = deptid;
    }

    public String getPhone() {
        return this.phone;
    }

    public DEmpn phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DEmpn)) {
            return false;
        }
        return id != null && id.equals(((DEmpn) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DEmpn{" +
            "id=" + getId() +
            ", empnid=" + getEmpnid() +
            ", empn='" + getEmpn() + "'" +
            ", deptid=" + getDeptid() +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
