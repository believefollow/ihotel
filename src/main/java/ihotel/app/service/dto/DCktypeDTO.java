package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.DCktype} entity.
 */
public class DCktypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String cktype;

    @Size(max = 100)
    private String memo;

    @NotNull
    @Size(max = 20)
    private String sign;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCktype() {
        return cktype;
    }

    public void setCktype(String cktype) {
        this.cktype = cktype;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DCktypeDTO)) {
            return false;
        }

        DCktypeDTO dCktypeDTO = (DCktypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dCktypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DCktypeDTO{" +
            "id=" + getId() +
            ", cktype='" + getCktype() + "'" +
            ", memo='" + getMemo() + "'" +
            ", sign='" + getSign() + "'" +
            "}";
    }
}
