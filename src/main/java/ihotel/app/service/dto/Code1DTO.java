package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Code1} entity.
 */
public class Code1DTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 20)
    private String code1;

    @NotNull
    @Size(max = 20)
    private String code2;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Code1DTO)) {
            return false;
        }

        Code1DTO code1DTO = (Code1DTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, code1DTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Code1DTO{" +
            "id=" + getId() +
            ", code1='" + getCode1() + "'" +
            ", code2='" + getCode2() + "'" +
            "}";
    }
}
