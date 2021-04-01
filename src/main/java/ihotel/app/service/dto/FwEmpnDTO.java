package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.FwEmpn} entity.
 */
public class FwEmpnDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String empnid;

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

    public String getEmpnid() {
        return empnid;
    }

    public void setEmpnid(String empnid) {
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
        if (!(o instanceof FwEmpnDTO)) {
            return false;
        }

        FwEmpnDTO fwEmpnDTO = (FwEmpnDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fwEmpnDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FwEmpnDTO{" +
            "id=" + getId() +
            ", empnid='" + getEmpnid() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", deptid=" + getDeptid() +
            ", phone='" + getPhone() + "'" +
            "}";
    }
}
