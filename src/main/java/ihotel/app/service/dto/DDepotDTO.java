package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DDepot} entity.
 */
public class DDepotDTO implements Serializable {

    private Long id;

    @NotNull
    private Boolean depotid;

    @NotNull
    @Size(max = 20)
    private String depot;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getDepotid() {
        return depotid;
    }

    public void setDepotid(Boolean depotid) {
        this.depotid = depotid;
    }

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DDepotDTO)) {
            return false;
        }

        DDepotDTO dDepotDTO = (DDepotDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dDepotDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DDepotDTO{" +
            "id=" + getId() +
            ", depotid='" + getDepotid() + "'" +
            ", depot='" + getDepot() + "'" +
            "}";
    }
}
