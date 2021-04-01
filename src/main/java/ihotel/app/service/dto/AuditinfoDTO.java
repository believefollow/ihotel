package ihotel.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Auditinfo} entity.
 */
public class AuditinfoDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant auditdate;

    private Instant audittime;

    @Size(max = 10)
    private String empn;

    @Size(max = 100)
    private String aidentify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getAuditdate() {
        return auditdate;
    }

    public void setAuditdate(Instant auditdate) {
        this.auditdate = auditdate;
    }

    public Instant getAudittime() {
        return audittime;
    }

    public void setAudittime(Instant audittime) {
        this.audittime = audittime;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public String getAidentify() {
        return aidentify;
    }

    public void setAidentify(String aidentify) {
        this.aidentify = aidentify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuditinfoDTO)) {
            return false;
        }

        AuditinfoDTO auditinfoDTO = (AuditinfoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, auditinfoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditinfoDTO{" +
            "id=" + getId() +
            ", auditdate='" + getAuditdate() + "'" +
            ", audittime='" + getAudittime() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", aidentify='" + getAidentify() + "'" +
            "}";
    }
}
