package ihotel.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Clog} entity.
 */
public class ClogDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(max = 20)
    private String empn;

    private Instant begindate;

    private Instant enddate;

    private Instant dqrq;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Instant getBegindate() {
        return begindate;
    }

    public void setBegindate(Instant begindate) {
        this.begindate = begindate;
    }

    public Instant getEnddate() {
        return enddate;
    }

    public void setEnddate(Instant enddate) {
        this.enddate = enddate;
    }

    public Instant getDqrq() {
        return dqrq;
    }

    public void setDqrq(Instant dqrq) {
        this.dqrq = dqrq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClogDTO)) {
            return false;
        }

        ClogDTO clogDTO = (ClogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, clogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClogDTO{" +
            "id=" + getId() +
            ", empn='" + getEmpn() + "'" +
            ", begindate='" + getBegindate() + "'" +
            ", enddate='" + getEnddate() + "'" +
            ", dqrq='" + getDqrq() + "'" +
            "}";
    }
}
