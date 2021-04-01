package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DDept} entity.
 */
public class DDeptDTO implements Serializable {

    private Long id;

    @NotNull
    private Long deptid;

    @NotNull
    @Size(max = 50)
    private String deptname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeptid() {
        return deptid;
    }

    public void setDeptid(Long deptid) {
        this.deptid = deptid;
    }

    public String getDeptname() {
        return deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DDeptDTO)) {
            return false;
        }

        DDeptDTO dDeptDTO = (DDeptDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dDeptDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DDeptDTO{" +
            "id=" + getId() +
            ", deptid=" + getDeptid() +
            ", deptname='" + getDeptname() + "'" +
            "}";
    }
}
