package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Accbillno} entity.
 */
public class AccbillnoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String account;

    @Size(max = 30)
    private String accbillno;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAccbillno() {
        return accbillno;
    }

    public void setAccbillno(String accbillno) {
        this.accbillno = accbillno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccbillnoDTO)) {
            return false;
        }

        AccbillnoDTO accbillnoDTO = (AccbillnoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, accbillnoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccbillnoDTO{" +
            "id=" + getId() +
            ", account='" + getAccount() + "'" +
            ", accbillno='" + getAccbillno() + "'" +
            "}";
    }
}
