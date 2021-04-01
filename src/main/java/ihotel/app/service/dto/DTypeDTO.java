package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DType} entity.
 */
public class DTypeDTO implements Serializable {

    private Long id;

    @NotNull
    private Long typeid;

    @NotNull
    @Size(max = 50)
    private String typename;

    @NotNull
    private Long fatherid;

    private Long disabled;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeid() {
        return typeid;
    }

    public void setTypeid(Long typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public Long getFatherid() {
        return fatherid;
    }

    public void setFatherid(Long fatherid) {
        this.fatherid = fatherid;
    }

    public Long getDisabled() {
        return disabled;
    }

    public void setDisabled(Long disabled) {
        this.disabled = disabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DTypeDTO)) {
            return false;
        }

        DTypeDTO dTypeDTO = (DTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DTypeDTO{" +
            "id=" + getId() +
            ", typeid=" + getTypeid() +
            ", typename='" + getTypename() + "'" +
            ", fatherid=" + getFatherid() +
            ", disabled=" + getDisabled() +
            "}";
    }
}
