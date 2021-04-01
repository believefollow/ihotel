package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.CzlCz} entity.
 */
public class CzlCzDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant tjrq;

    private Long typeid;

    @NotNull
    @Size(max = 50)
    private String type;

    private Long fjsl;

    private BigDecimal kfl;

    private BigDecimal pjz;

    private BigDecimal ysfz;

    private BigDecimal sjfz;

    private BigDecimal fzcz;

    private BigDecimal pjzcj;

    private BigDecimal kfsM;

    private BigDecimal kflM;

    private BigDecimal pjzM;

    private BigDecimal fzsr;

    private BigDecimal dayz;

    private Instant hoteltime;

    @Size(max = 45)
    private String empn;

    private BigDecimal monthz;

    @Size(max = 50)
    private String hoteldm;

    private Long isnew;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getTjrq() {
        return tjrq;
    }

    public void setTjrq(Instant tjrq) {
        this.tjrq = tjrq;
    }

    public Long getTypeid() {
        return typeid;
    }

    public void setTypeid(Long typeid) {
        this.typeid = typeid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getFjsl() {
        return fjsl;
    }

    public void setFjsl(Long fjsl) {
        this.fjsl = fjsl;
    }

    public BigDecimal getKfl() {
        return kfl;
    }

    public void setKfl(BigDecimal kfl) {
        this.kfl = kfl;
    }

    public BigDecimal getPjz() {
        return pjz;
    }

    public void setPjz(BigDecimal pjz) {
        this.pjz = pjz;
    }

    public BigDecimal getYsfz() {
        return ysfz;
    }

    public void setYsfz(BigDecimal ysfz) {
        this.ysfz = ysfz;
    }

    public BigDecimal getSjfz() {
        return sjfz;
    }

    public void setSjfz(BigDecimal sjfz) {
        this.sjfz = sjfz;
    }

    public BigDecimal getFzcz() {
        return fzcz;
    }

    public void setFzcz(BigDecimal fzcz) {
        this.fzcz = fzcz;
    }

    public BigDecimal getPjzcj() {
        return pjzcj;
    }

    public void setPjzcj(BigDecimal pjzcj) {
        this.pjzcj = pjzcj;
    }

    public BigDecimal getKfsM() {
        return kfsM;
    }

    public void setKfsM(BigDecimal kfsM) {
        this.kfsM = kfsM;
    }

    public BigDecimal getKflM() {
        return kflM;
    }

    public void setKflM(BigDecimal kflM) {
        this.kflM = kflM;
    }

    public BigDecimal getPjzM() {
        return pjzM;
    }

    public void setPjzM(BigDecimal pjzM) {
        this.pjzM = pjzM;
    }

    public BigDecimal getFzsr() {
        return fzsr;
    }

    public void setFzsr(BigDecimal fzsr) {
        this.fzsr = fzsr;
    }

    public BigDecimal getDayz() {
        return dayz;
    }

    public void setDayz(BigDecimal dayz) {
        this.dayz = dayz;
    }

    public Instant getHoteltime() {
        return hoteltime;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public String getEmpn() {
        return empn;
    }

    public void setEmpn(String empn) {
        this.empn = empn;
    }

    public BigDecimal getMonthz() {
        return monthz;
    }

    public void setMonthz(BigDecimal monthz) {
        this.monthz = monthz;
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
        if (!(o instanceof CzlCzDTO)) {
            return false;
        }

        CzlCzDTO czlCzDTO = (CzlCzDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, czlCzDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CzlCzDTO{" +
            "id=" + getId() +
            ", tjrq='" + getTjrq() + "'" +
            ", typeid=" + getTypeid() +
            ", type='" + getType() + "'" +
            ", fjsl=" + getFjsl() +
            ", kfl=" + getKfl() +
            ", pjz=" + getPjz() +
            ", ysfz=" + getYsfz() +
            ", sjfz=" + getSjfz() +
            ", fzcz=" + getFzcz() +
            ", pjzcj=" + getPjzcj() +
            ", kfsM=" + getKfsM() +
            ", kflM=" + getKflM() +
            ", pjzM=" + getPjzM() +
            ", fzsr=" + getFzsr() +
            ", dayz=" + getDayz() +
            ", hoteltime='" + getHoteltime() + "'" +
            ", empn='" + getEmpn() + "'" +
            ", monthz=" + getMonthz() +
            ", hoteldm='" + getHoteldm() + "'" +
            ", isnew=" + getIsnew() +
            "}";
    }
}
