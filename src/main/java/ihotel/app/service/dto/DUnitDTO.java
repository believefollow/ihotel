package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DUnit} entity.
 */
public class DUnitDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DUnitDTO)) {
            return false;
        }

        DUnitDTO dUnitDTO = (DUnitDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dUnitDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DUnitDTO{" +
            "id=" + getId() +
            ", unit='" + getUnit() + "'" +
            "}";
    }
}
