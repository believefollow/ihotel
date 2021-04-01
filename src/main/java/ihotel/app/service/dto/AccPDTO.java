package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.AccP} entity.
 */
public class AccPDTO implements Serializable {

    private Long id;

    @NotNull
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
        if (!(o instanceof AccPDTO)) {
            return false;
        }

        AccPDTO accPDTO = (AccPDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, accPDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccPDTO{" +
            "id=" + getId() +
            ", acc='" + getAcc() + "'" +
            "}";
    }
}
