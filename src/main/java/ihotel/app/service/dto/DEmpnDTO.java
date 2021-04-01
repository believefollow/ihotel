package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DEmpn} entity.
 */
public class DEmpnDTO implements Serializable {

    private Long id;

    @NotNull
    private Long empnid;

    @NotNull
    @Size(max = 50)
    private String empn;

    private Long deptid;

    @Size(max = 50)
    private String phone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmpnid() {
        return empnid;
    }

    public void setEmpnid(Long empnid) {
        this.empnid = empnid;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Long getDeptid() {
        return deptid;
    }

    public void setDeptid(Long deptid) {
        this.deptid = deptid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DEmpnDTO)) {
            return false;
        }

        DEmpnDTO dEmpnDTO = (DEmpnDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dEmpnDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DEmpnDTO{" +
            "id=" + getId() +
            ", empnid=" + getEmpnid() +
            ", empn='" + getEmpn() + "'" +
            ", deptid=" + getDeptid() +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
