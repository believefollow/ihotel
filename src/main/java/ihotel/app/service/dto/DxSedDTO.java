package ihotel.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DxSed} entity.
 */
public class DxSedDTO implements Serializable {

    @NotNull
    private Long id;

    private Instant dxRq;

    @Size(max = 2)
    private String dxZt;

    private Instant fsSj;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDxRq() {
        return dxRq;
    }

    public void setDxRq(Instant dxRq) {
        this.dxRq = dxRq;
    }

    public String getDxZt() {
        return dxZt;
    }

    public void setDxZt(String dxZt) {
        this.dxZt = dxZt;
    }

    public Instant getFsSj() {
        return fsSj;
    }

    public void setFsSj(Instant fsSj) {
        this.fsSj = fsSj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DxSedDTO)) {
            return false;
        }

        DxSedDTO dxSedDTO = (DxSedDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dxSedDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DxSedDTO{" +
            "id=" + getId() +
            ", dxRq='" + getDxRq() + "'" +
            ", dxZt='" + getDxZt() + "'" +
            ", fsSj='" + getFsSj() + "'" +
            "}";
    }
}
