package ihotel.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Ck2xsy} entity.
 */
public class Ck2xsyDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant rq;

    @NotNull
    @Size(max = 12)
    private String cpbh;

    @NotNull
    private Long sl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getRq() {
        return rq;
    }

    public void setRq(Instant rq) {
        this.rq = rq;
    }

    public String getCpbh() {
        return cpbh;
    }

    public void setCpbh(String cpbh) {
        this.cpbh = cpbh;
    }

    public Long getSl() {
        return sl;
    }

    public void setSl(Long sl) {
        this.sl = sl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ck2xsyDTO)) {
            return false;
        }

        Ck2xsyDTO ck2xsyDTO = (Ck2xsyDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, ck2xsyDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ck2xsyDTO{" +
            "id=" + getId() +
            ", rq='" + getRq() + "'" +
            ", cpbh='" + getCpbh() + "'" +
            ", sl=" + getSl() +
            "}";
    }
}
