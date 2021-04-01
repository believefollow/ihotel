package ihotel.app.service.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link ihotel.app.domain.ClassreportRoom} entity.
 */
public class ClassreportRoomDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 30)
    private String account;

    @Size(max = 10)
    private String roomn;

    private BigDecimal yfj;

    private BigDecimal yfj9008;

    private BigDecimal yfj9009;

    private BigDecimal yfj9007;

    private BigDecimal gz;

    private BigDecimal ff;

    private BigDecimal minibar;

    private BigDecimal phone;

    private BigDecimal other;

    private BigDecimal pc;

    private BigDecimal cz;

    private BigDecimal cy;

    private BigDecimal md;

    private BigDecimal huiy;

    private BigDecimal dtb;

    private BigDecimal sszx;

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

    public String getRoomn() {
        return roomn;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public BigDecimal getYfj() {
        return yfj;
    }

    public void setYfj(BigDecimal yfj) {
        this.yfj = yfj;
    }

    public BigDecimal getYfj9008() {
        return yfj9008;
    }

    public void setYfj9008(BigDecimal yfj9008) {
        this.yfj9008 = yfj9008;
    }

    public BigDecimal getYfj9009() {
        return yfj9009;
    }

    public void setYfj9009(BigDecimal yfj9009) {
        this.yfj9009 = yfj9009;
    }

    public BigDecimal getYfj9007() {
        return yfj9007;
    }

    public void setYfj9007(BigDecimal yfj9007) {
        this.yfj9007 = yfj9007;
    }

    public BigDecimal getGz() {
        return gz;
    }

    public void setGz(BigDecimal gz) {
        this.gz = gz;
    }

    public BigDecimal getFf() {
        return ff;
    }

    public void setFf(BigDecimal ff) {
        this.ff = ff;
    }

    public BigDecimal getMinibar() {
        return minibar;
    }

    public void setMinibar(BigDecimal minibar) {
        this.minibar = minibar;
    }

    public BigDecimal getPhone() {
        return phone;
    }

    public void setPhone(BigDecimal phone) {
        this.phone = phone;
    }

    public BigDecimal getOther() {
        return other;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }

    public BigDecimal getPc() {
        return pc;
    }

    public void setPc(BigDecimal pc) {
        this.pc = pc;
    }

    public BigDecimal getCz() {
        return cz;
    }

    public void setCz(BigDecimal cz) {
        this.cz = cz;
    }

    public BigDecimal getCy() {
        return cy;
    }

    public void setCy(BigDecimal cy) {
        this.cy = cy;
    }

    public BigDecimal getMd() {
        return md;
    }

    public void setMd(BigDecimal md) {
        this.md = md;
    }

    public BigDecimal getHuiy() {
        return huiy;
    }

    public void setHuiy(BigDecimal huiy) {
        this.huiy = huiy;
    }

    public BigDecimal getDtb() {
        return dtb;
    }

    public void setDtb(BigDecimal dtb) {
        this.dtb = dtb;
    }

    public BigDecimal getSszx() {
        return sszx;
    }

    public void setSszx(BigDecimal sszx) {
        this.sszx = sszx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassreportRoomDTO)) {
            return false;
        }

        ClassreportRoomDTO classreportRoomDTO = (ClassreportRoomDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, classreportRoomDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassreportRoomDTO{" +
            "id=" + getId() +
            ", account='" + getAccount() + "'" +
            ", roomn='" + getRoomn() + "'" +
            ", yfj=" + getYfj() +
            ", yfj9008=" + getYfj9008() +
            ", yfj9009=" + getYfj9009() +
            ", yfj9007=" + getYfj9007() +
            ", gz=" + getGz() +
            ", ff=" + getFf() +
            ", minibar=" + getMinibar() +
            ", phone=" + getPhone() +
            ", other=" + getOther() +
            ", pc=" + getPc() +
            ", cz=" + getCz() +
            ", cy=" + getCy() +
            ", md=" + getMd() +
            ", huiy=" + getHuiy() +
            ", dtb=" + getDtb() +
            ", sszx=" + getSszx() +
            "}";
    }
}
