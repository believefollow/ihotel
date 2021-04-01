package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Adhoc} entity.
 */
public class AdhocDTO implements Serializable {

    @NotNull
    @Size(max = 10)
    private String id;

    @NotNull
    @Size(max = 40)
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AdhocDTO)) {
            return false;
        }

        AdhocDTO adhocDTO = (AdhocDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, adhocDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AdhocDTO{" +
            "id='" + getId() + "'" +
            ", remark='" + getRemark() + "'" +
            "}";
    }
}
