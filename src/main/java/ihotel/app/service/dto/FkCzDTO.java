package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.FkCz} entity.
 */
public class FkCzDTO implements Serializable {

    @NotNull
    private Long id;

    @NotNull
    private Instant hoteltime;

    private Long wxf;

    private Long ksf;

    private Long kf;

    private Long zfs;

    private Long groupyd;

    private Long skyd;

    private Long ydwd;

    private Long qxyd;

    private Long isnew;

    @Size(max = 50)
    private String hoteldm;

    private BigDecimal hys;

    private BigDecimal khys;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getHoteltime() {
        return hoteltime;
    }

    public void setHoteltime(Instant hoteltime) {
        this.hoteltime = hoteltime;
    }

    public Long getWxf() {
        return wxf;
    }

    public void setWxf(Long wxf) {
        this.wxf = wxf;
    }

    public Long getKsf() {
        return ksf;
    }

    public void setKsf(Long ksf) {
        this.ksf = ksf;
    }

    public Long getKf() {
        return kf;
    }

    public void setKf(Long kf) {
        this.kf = kf;
    }

    public Long getZfs() {
        return zfs;
    }

    public void setZfs(Long zfs) {
        this.zfs = zfs;
    }

    public Long getGroupyd() {
        return groupyd;
    }

    public void setGroupyd(Long groupyd) {
        this.groupyd = groupyd;
    }

    public Long getSkyd() {
        return skyd;
    }

    public void setSkyd(Long skyd) {
        this.skyd = skyd;
    }

    public Long getYdwd() {
        return ydwd;
    }

    public void setYdwd(Long ydwd) {
        this.ydwd = ydwd;
    }

    public Long getQxyd() {
        return qxyd;
    }

    public void setQxyd(Long qxyd) {
        this.qxyd = qxyd;
    }

    public Long getIsnew() {
        return isnew;
    }

    public void setIsnew(Long isnew) {
        this.isnew = isnew;
    }

    public String getHoteldm() {
        return hoteldm;
    }

    public void setHoteldm(String hoteldm) {
        this.hoteldm = hoteldm;
    }

    public BigDecimal getHys() {
        return hys;
    }

    public void setHys(BigDecimal hys) {
        this.hys = hys;
    }

    public BigDecimal getKhys() {
        return khys;
    }

    public void setKhys(BigDecimal khys) {
        this.khys = khys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FkCzDTO)) {
            return false;
        }

        FkCzDTO fkCzDTO = (FkCzDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fkCzDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FkCzDTO{" +
            "id=" + getId() +
            ", hoteltime='" + getHoteltime() + "'" +
            ", wxf=" + getWxf() +
            ", ksf=" + getKsf() +
            ", kf=" + getKf() +
            ", zfs=" + getZfs() +
            ", groupyd=" + getGroupyd() +
            ", skyd=" + getSkyd() +
            ", ydwd=" + getYdwd() +
            ", qxyd=" + getQxyd() +
            ", isnew=" + getIsnew() +
            ", hoteldm='" + getHoteldm() + "'" +
            ", hys=" + getHys() +
            ", khys=" + getKhys() +
            "}";
    }
}
