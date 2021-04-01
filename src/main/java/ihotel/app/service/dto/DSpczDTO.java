package ihotel.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link ihotel.app.domain.DSpcz} entity.
 */
public class DSpczDTO implements Serializable {

    private Long id;

    private Instant rq;

    private Instant czrq;

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

    public Instant getCzrq() {
        return czrq;
    }

    public void setCzrq(Instant czrq) {
        this.czrq = czrq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DSpczDTO)) {
            return false;
        }

        DSpczDTO dSpczDTO = (DSpczDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dSpczDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DSpczDTO{" +
            "id=" + getId() +
            ", rq='" + getRq() + "'" +
            ", czrq='" + getCzrq() + "'" +
            "}";
    }
}
