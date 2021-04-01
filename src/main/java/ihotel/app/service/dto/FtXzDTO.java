package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.FtXz} entity.
 */
public class FtXzDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(max = 50)
    private String roomn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomn() {
        return roomn;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FtXzDTO)) {
            return false;
        }

        FtXzDTO ftXzDTO = (FtXzDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ftXzDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FtXzDTO{" +
            "id=" + getId() +
            ", roomn='" + getRoomn() + "'" +
            "}";
    }
}
