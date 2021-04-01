package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.CzCzl2} entity.
 */
public class CzCzl2DTO implements Serializable {

    private Long id;

    private Instant dr;

    @Size(max = 50)
    private String type;

    private Long fs;

    private BigDecimal kfl;

    private BigDecimal fzsr;

    private BigDecimal pjz;

    private Long fsM;

    private BigDecimal kflM;

    private BigDecimal fzsrM;

    private BigDecimal pjzM;

    private Long fsY;

    private BigDecimal kflY;

    private BigDecimal fzsrY;

    private BigDecimal pjzY;

    private Long fsQ;

    private BigDecimal kflQ;

    private BigDecimal fzsrQ;

    private BigDecimal pjzQ;

    @Size(max = 50)
    private String dateY;

    private Instant dqdate;

    @Size(max = 45)
    private String empn;

    @NotNull
    private Long number;

    @NotNull
    private Long numberM;

    @NotNull
    private Long numberY;

    @Size(max = 50)
    private String hoteldm;

    private Long isnew;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDr() {
        return dr;
    }

    public void setDr(Instant dr) {
        this.dr = dr;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getFs() {
        return fs;
    }

    public void setFs(Long fs) {
        this.fs = fs;
    }

    public BigDecimal getKfl() {
        return kfl;
    }

    public void setKfl(BigDecimal kfl) {
        this.kfl = kfl;
    }

    public BigDecimal getFzsr() {
        return fzsr;
    }

    public void setFzsr(BigDecimal fzsr) {
        this.fzsr = fzsr;
    }

    public BigDecimal getPjz() {
        return pjz;
    }

    public void setPjz(BigDecimal pjz) {
        this.pjz = pjz;
    }

    public Long getFsM() {
        return fsM;
    }

    public void setFsM(Long fsM) {
        this.fsM = fsM;
    }

    public BigDecimal getKflM() {
        return kflM;
    }

    public void setKflM(BigDecimal kflM) {
        this.kflM = kflM;
    }

    public BigDecimal getFzsrM() {
        return fzsrM;
    }

    public void setFzsrM(BigDecimal fzsrM) {
        this.fzsrM = fzsrM;
    }

    public BigDecimal getPjzM() {
        return pjzM;
    }

    public void setPjzM(BigDecimal pjzM) {
        this.pjzM = pjzM;
    }

    public Long getFsY() {
        return fsY;
    }

    public void setFsY(Long fsY) {
        this.fsY = fsY;
    }

    public BigDecimal getKflY() {
        return kflY;
    }

    public void setKflY(BigDecimal kflY) {
        this.kflY = kflY;
    }

    public BigDecimal getFzsrY() {
        return fzsrY;
    }

    public void setFzsrY(BigDecimal fzsrY) {
        this.fzsrY = fzsrY;
    }

    public BigDecimal getPjzY() {
        return pjzY;
    }

    public void setPjzY(BigDecimal pjzY) {
        this.pjzY = pjzY;
    }

    public Long getFsQ() {
        return fsQ;
    }

    public void setFsQ(Long fsQ) {
        this.fsQ = fsQ;
    }

    public BigDecimal getKflQ() {
        return kflQ;
    }

    public void setKflQ(BigDecimal kflQ) {
        this.kflQ = kflQ;
    }

    public BigDecimal getFzsrQ() {
        return fzsrQ;
    }

    public void setFzsrQ(BigDecimal fzsrQ) {
        this.fzsrQ = fzsrQ;
    }

    public BigDecimal getPjzQ() {
        return pjzQ;
    }

    public void setPjzQ(BigDecimal pjzQ) {
        this.pjzQ = pjzQ;
    }

    public String getDateY() {
        return dateY;
    }

    public void setDateY(String dateY) {
        this.dateY = dateY;
    }

    public Instant getDqdate() {
        return dqdate;
    }

    public void setDqdate(Instant dqdate) {
        this.dqdate = dqdate;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getNumberM() {
        return numberM;
    }

    public void setNumberM(Long numberM) {
        this.numberM = numberM;
    }

    public Long getNumberY() {
        return numberY;
    }

    public void setNumberY(Long numberY) {
        this.numberY = numberY;
    }

    public String getHoteldm() {
        return hoteldm;
    }

    public void setHoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
    }

    public Long getIsnew() {
        return isnew;
    }

    public void setIsnew(Long isnew) {
        this.isnew = isnew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CzCzl2DTO)) {
            return false;
        }

        CzCzl2DTO czCzl2DTO = (CzCzl2DTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, czCzl2DTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CzCzl2DTO{" +
            "id=" + getId() +
            ", dr='" + getDr() + "'" +
            ", type='" + getType() + "'" +
            ", fs=" + getFs() +
            ", kfl=" + getKfl() +
            ", fzsr=" + getFzsr() +
            ", pjz=" + getPjz() +
            ", fsM=" + getFsM() +
            ", kflM=" + getKflM() +
            ", fzsrM=" + getFzsrM() +
            ", pjzM=" + getPjzM() +
            ", fsY=" + getFsY() +
            ", kflY=" + getKflY() +
            ", fzsrY=" + getFzsrY() +
            ", pjzY=" + getPjzY() +
            ", fsQ=" + getFsQ() +
            ", kflQ=" + getKflQ() +
            ", fzsrQ=" + getFzsrQ() +
            ", pjzQ=" + getPjzQ() +
            ", dateY='" + getDateY() + "'" +
            ", dqdate='" + getDqdate() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", number=" + getNumber() +
            ", numberM=" + getNumberM() +
            ", numberY=" + getNumberY() +
            ", hoteldm='" + getHoteldm() + "'" +
            ", isnew=" + getIsnew() +
            "}";
    }
}
