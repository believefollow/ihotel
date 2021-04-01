package ihotel.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.ClassBak} entity.
 */
public class ClassBakDTO implements Serializable {

    @NotNull
    private Long id;

    @Size(max = 100)
    private String empn;

    private Instant dt;

    private Instant rq;

    @Size(max = 100)
    private String ghname;

    @Size(max = 100)
    private String bak;

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

    public Instant getDt() {
        return dt;
    }

    public void setDt(Instant dt) {
        this.dt = dt;
    }

    public Instant getRq() {
        return rq;
    }

    public void setRq(Instant rq) {
        this.rq = rq;
    }

    public String getGhname() {
        return ghname;
    }

    public void setGhname(String ghname) {
        this.ghname = ghname;
    }

    public String getBak() {
        return bak;
    }

    public void setBak(String bak) {
        this.bak = bak;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassBakDTO)) {
            return false;
        }

        ClassBakDTO classBakDTO = (ClassBakDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, classBakDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassBakDTO{" +
            "id=" + getId() +
            ", empn='" + getEmpn() + "'" +
            ", dt='" + getDt() + "'" +
            ", rq='" + getRq() + "'" +
            ", ghname='" + getGhname() + "'" +
            ", bak='" + getBak() + "'" +
            "}";
    }
}
