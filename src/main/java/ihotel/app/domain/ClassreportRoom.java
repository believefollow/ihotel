package ihotel.app.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * A ClassreportRoom.
 */
@Entity
@Table(name = "classreport_room")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "classreportroom")
public class ClassreportRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 30)
    @Column(name = "account", length = 30, nullable = false)
    private String account;

    @Size(max = 10)
    @Column(name = "roomn", length = 10)
    private String roomn;

    @Column(name = "yfj", precision = 21, scale = 2)
    private BigDecimal yfj;

    @Column(name = "yfj_9008", precision = 21, scale = 2)
    private BigDecimal yfj9008;

    @Column(name = "yfj_9009", precision = 21, scale = 2)
    private BigDecimal yfj9009;

    @Column(name = "yfj_9007", precision = 21, scale = 2)
    private BigDecimal yfj9007;

    @Column(name = "gz", precision = 21, scale = 2)
    private BigDecimal gz;

    @Column(name = "ff", precision = 21, scale = 2)
    private BigDecimal ff;

    @Column(name = "minibar", precision = 21, scale = 2)
    private BigDecimal minibar;

    @Column(name = "phone", precision = 21, scale = 2)
    private BigDecimal phone;

    @Column(name = "other", precision = 21, scale = 2)
    private BigDecimal other;

    @Column(name = "pc", precision = 21, scale = 2)
    private BigDecimal pc;

    @Column(name = "cz", precision = 21, scale = 2)
    private BigDecimal cz;

    @Column(name = "cy", precision = 21, scale = 2)
    private BigDecimal cy;

    @Column(name = "md", precision = 21, scale = 2)
    private BigDecimal md;

    @Column(name = "huiy", precision = 21, scale = 2)
    private BigDecimal huiy;

    @Column(name = "dtb", precision = 21, scale = 2)
    private BigDecimal dtb;

    @Column(name = "sszx", precision = 21, scale = 2)
    private BigDecimal sszx;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassreportRoom id(Long id) {
        this.id = id;
        return this;
    }

    public String getAccount() {
        return this.account;
    }

    public ClassreportRoom account(String account) {
        this.account = account;
        return this;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRoomn() {
        return this.roomn;
    }

    public ClassreportRoom roomn(String roomn) {
        this.roomn = roomn;
        return this;
    }

    public void setRoomn(String roomn) {
        this.roomn = roomn;
    }

    public BigDecimal getYfj() {
        return this.yfj;
    }

    public ClassreportRoom yfj(BigDecimal yfj) {
        this.yfj = yfj;
        return this;
    }

    public void setYfj(BigDecimal yfj) {
        this.yfj = yfj;
    }

    public BigDecimal getYfj9008() {
        return this.yfj9008;
    }

    public ClassreportRoom yfj9008(BigDecimal yfj9008) {
        this.yfj9008 = yfj9008;
        return this;
    }

    public void setYfj9008(BigDecimal yfj9008) {
        this.yfj9008 = yfj9008;
    }

    public BigDecimal getYfj9009() {
        return this.yfj9009;
    }

    public ClassreportRoom yfj9009(BigDecimal yfj9009) {
        this.yfj9009 = yfj9009;
        return this;
    }

    public void setYfj9009(BigDecimal yfj9009) {
        this.yfj9009 = yfj9009;
    }

    public BigDecimal getYfj9007() {
        return this.yfj9007;
    }

    public ClassreportRoom yfj9007(BigDecimal yfj9007) {
        this.yfj9007 = yfj9007;
        return this;
    }

    public void setYfj9007(BigDecimal yfj9007) {
        this.yfj9007 = yfj9007;
    }

    public BigDecimal getGz() {
        return this.gz;
    }

    public ClassreportRoom gz(BigDecimal gz) {
        this.gz = gz;
        return this;
    }

    public void setGz(BigDecimal gz) {
        this.gz = gz;
    }

    public BigDecimal getFf() {
        return this.ff;
    }

    public ClassreportRoom ff(BigDecimal ff) {
        this.ff = ff;
        return this;
    }

    public void setFf(BigDecimal ff) {
        this.ff = ff;
    }

    public BigDecimal getMinibar() {
        return this.minibar;
    }

    public ClassreportRoom minibar(BigDecimal minibar) {
        this.minibar = minibar;
        return this;
    }

    public void setMinibar(BigDecimal minibar) {
        this.minibar = minibar;
    }

    public BigDecimal getPhone() {
        return this.phone;
    }

    public ClassreportRoom phone(BigDecimal phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(BigDecimal phone) {
        this.phone = phone;
    }

    public BigDecimal getOther() {
        return this.other;
    }

    public ClassreportRoom other(BigDecimal other) {
        this.other = other;
        return this;
    }

    public void setOther(BigDecimal other) {
        this.other = other;
    }

    public BigDecimal getPc() {
        return this.pc;
    }

    public ClassreportRoom pc(BigDecimal pc) {
        this.pc = pc;
        return this;
    }

    public void setPc(BigDecimal pc) {
        this.pc = pc;
    }

    public BigDecimal getCz() {
        return this.cz;
    }

    public ClassreportRoom cz(BigDecimal cz) {
        this.cz = cz;
        return this;
    }

    public void setCz(BigDecimal cz) {
        this.cz = cz;
    }

    public BigDecimal getCy() {
        return this.cy;
    }

    public ClassreportRoom cy(BigDecimal cy) {
        this.cy = cy;
        return this;
    }

    public void setCy(BigDecimal cy) {
        this.cy = cy;
    }

    public BigDecimal getMd() {
        return this.md;
    }

    public ClassreportRoom md(BigDecimal md) {
        this.md = md;
        return this;
    }

    public void setMd(BigDecimal md) {
        this.md = md;
    }

    public BigDecimal getHuiy() {
        return this.huiy;
    }

    public ClassreportRoom huiy(BigDecimal huiy) {
        this.huiy = huiy;
        return this;
    }

    public void setHuiy(BigDecimal huiy) {
        this.huiy = huiy;
    }

    public BigDecimal getDtb() {
        return this.dtb;
    }

    public ClassreportRoom dtb(BigDecimal dtb) {
        this.dtb = dtb;
        return this;
    }

    public void setDtb(BigDecimal dtb) {
        this.dtb = dtb;
    }

    public BigDecimal getSszx() {
        return this.sszx;
    }

    public ClassreportRoom sszx(BigDecimal sszx) {
        this.sszx = sszx;
        return this;
    }

    public void setSszx(BigDecimal sszx) {
        this.sszx = sszx;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClassreportRoom)) {
            return false;
        }
        return id != null && id.equals(((ClassreportRoom) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassreportRoom{" +
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
