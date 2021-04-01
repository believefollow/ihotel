package ihotel.app.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.Comset} entity.
 */
public class ComsetDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 10)
    private String comNum;

    @NotNull
    @Size(max = 18)
    private String comBytes;

    @NotNull
    @Size(max = 10)
    private String comDatabit;

    @NotNull
    @Size(max = 10)
    private String comParitycheck;

    @NotNull
    @Size(max = 18)
    private String comStopbit;

    @NotNull
    private Long comFunction;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComNum() {
        return comNum;
    }

    public void setComNum(String comNum) {
        this.comNum = comNum;
    }

    public String getComBytes() {
        return comBytes;
    }

    public void setComBytes(String comBytes) {
        this.comBytes = comBytes;
    }

    public String getComDatabit() {
        return comDatabit;
    }

    public void setComDatabit(String comDatabit) {
        this.comDatabit = comDatabit;
    }

    public String getComParitycheck() {
        return comParitycheck;
    }

    public void setComParitycheck(String comParitycheck) {
        this.comParitycheck = comParitycheck;
    }

    public String getComStopbit() {
        return comStopbit;
    }

    public void setComStopbit(String comStopbit) {
        this.comStopbit = comStopbit;
    }

    public Long getComFunction() {
        return comFunction;
    }

    public void setComFunction(Long comFunction) {
        this.comFunction = comFunction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ComsetDTO)) {
            return false;
        }

        ComsetDTO comsetDTO = (ComsetDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, comsetDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ComsetDTO{" +
            "id=" + getId() +
            ", comNum='" + getComNum() + "'" +
            ", comBytes='" + getComBytes() + "'" +
            ", comDatabit='" + getComDatabit() + "'" +
            ", comParitycheck='" + getComParitycheck() + "'" +
            ", comStopbit='" + getComStopbit() + "'" +
            ", comFunction=" + getComFunction() +
            "}";
    }
}
