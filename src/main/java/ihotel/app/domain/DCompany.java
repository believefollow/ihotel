package ihotel.app.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A DCompany.
 */
@Entity
@Table(name = "d_company")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dcompany")
public class DCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "company", length = 100, nullable = false)
    private String company;

    @Size(max = 50)
    @Column(name = "linkman", length = 50)
    private String linkman;

    @Size(max = 100)
    @Column(name = "phone", length = 100)
    private String phone;

    @Size(max = 100)
    @Column(name = "address", length = 100)
    private String address;

    @Size(max = 100)
    @Column(name = "remark", length = 100)
    private String remark;

    @Size(max = 100)
    @Column(name = "fax", length = 100)
    private String fax;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DCompany id(Long id) {
        this.id = id;
        return this;
    }

    public String getCompany() {
        return this.company;
    }

    public DCompany company(String company) {
        this.company = company;
        return this;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLinkman() {
        return this.linkman;
    }

    public DCompany linkman(String linkman) {
        this.linkman = linkman;
        return this;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return this.phone;
    }

    public DCompany phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return this.address;
    }

    public DCompany address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return this.remark;
    }

    public DCompany remark(String remark) {
        this.remark = remark;
        return this;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFax() {
        return this.fax;
    }

    public DCompany fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DCompany)) {
            return false;
        }
        return id != null && id.equals(((DCompany) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DCompany{" +
            "id=" + getId() +
            ", company='" + getCompany() + "'" +
            ", linkman='" + getLinkman() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            ", remark='" + getRemark() + "'" +
            ", fax='" + getFax() + "'" +
            "}";
    }
}
