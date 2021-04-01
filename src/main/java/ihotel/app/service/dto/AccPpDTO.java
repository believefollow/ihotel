package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.AccPp} entity.
 */
public class AccPpDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(max = 50)
    private String acc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccPpDTO)) {
            return false;
        }

        AccPpDTO accPpDTO = (AccPpDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, accPpDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccPpDTO{" +
            "id=" + getId() +
            ", acc='" + getAcc() + "'" +
            "}";
    }
}
