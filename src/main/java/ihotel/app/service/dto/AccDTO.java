package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Acc} entity.
 */
public class AccDTO implements Serializable {

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
        if (!(o instanceof AccDTO)) {
            return false;
        }

        AccDTO accDTO = (AccDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, accDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccDTO{" +
            "id=" + getId() +
            ", acc='" + getAcc() + "'" +
            "}";
    }
}
