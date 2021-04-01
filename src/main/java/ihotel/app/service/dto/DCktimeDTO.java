package ihotel.app.service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DCktime} entity.
 */
public class DCktimeDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant begintime;

    @NotNull
    private Instant endtime;

    @NotNull
    @Size(max = 20)
    private String depot;

    @Size(max = 30)
    private String ckbillno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getBegintime() {
        return begintime;
    }

    public void setBegintime(Instant begintime) {
        this.begintime = begintime;
    }

    public Instant getEndtime() {
        return endtime;
    }

    public void setEndtime(Instant endtime) {
        this.endtime = endtime;
    }

    public String getDepot() {
        return depot;
    }

    public void setDepot(String depot) {
        this.depot = depot;
    }

    public String getCkbillno() {
        return ckbillno;
    }

    public void setCkbillno(String ckbillno) {
        this.ckbillno = ckbillno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DCktimeDTO)) {
            return false;
        }

        DCktimeDTO dCktimeDTO = (DCktimeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dCktimeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DCktimeDTO{" +
            "id=" + getId() +
            ", begintime='" + getBegintime() + "'" +
            ", endtime='" + getEndtime() + "'" +
            ", depot='" + getDepot() + "'" +
            ", ckbillno='" + getCkbillno() + "'" +
            "}";
    }
}
