package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DCompany} entity.
 */
public class DCompanyDTO implements Serializable {

    @NotNull
    @Size(max = 100)
    private String company;

    @Size(max = 50)
    private String linkman;

    @Size(max = 100)
    private String phone;

    @Size(max = 100)
    private String address;

    @Size(max = 100)
    private String remark;

    @Size(max = 100)
    private String fax;

    @NotNull
    private Long id;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DCompanyDTO)) {
            return false;
        }

        DCompanyDTO dCompanyDTO = (DCompanyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dCompanyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DCompanyDTO{" +
            "company='" + getCompany() + "'" +
            ", linkman='" + getLinkman() + "'" +
            ", phone='" + getPhone() + "'" +
            ", address='" + getAddress() + "'" +
            ", remark='" + getRemark() + "'" +
            ", fax='" + getFax() + "'" +
            ", id=" + getId() +
            "}";
    }
}
