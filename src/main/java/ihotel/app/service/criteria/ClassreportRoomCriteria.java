package ihotel.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ihotel.app.domain.ClassreportRoom} entity. This class is used
 * in {@link ihotel.app.web.rest.ClassreportRoomResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /classreport-rooms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClassreportRoomCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter account;

    private StringFilter roomn;

    private BigDecimalFilter yfj;

    private BigDecimalFilter yfj9008;

    private BigDecimalFilter yfj9009;

    private BigDecimalFilter yfj9007;

    private BigDecimalFilter gz;

    private BigDecimalFilter ff;

    private BigDecimalFilter minibar;

    private BigDecimalFilter phone;

    private BigDecimalFilter other;

    private BigDecimalFilter pc;

    private BigDecimalFilter cz;

    private BigDecimalFilter cy;

    private BigDecimalFilter md;

    private BigDecimalFilter huiy;

    private BigDecimalFilter dtb;

    private BigDecimalFilter sszx;

    public ClassreportRoomCriteria() {}

    public ClassreportRoomCriteria(ClassreportRoomCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.account = other.account == null ? null : other.account.copy();
        this.roomn = other.roomn == null ? null : other.roomn.copy();
        this.yfj = other.yfj == null ? null : other.yfj.copy();
        this.yfj9008 = other.yfj9008 == null ? null : other.yfj9008.copy();
        this.yfj9009 = other.yfj9009 == null ? null : other.yfj9009.copy();
        this.yfj9007 = other.yfj9007 == null ? null : other.yfj9007.copy();
        this.gz = other.gz == null ? null : other.gz.copy();
        this.ff = other.ff == null ? null : other.ff.copy();
        this.minibar = other.minibar == null ? null : other.minibar.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.other = other.other == null ? null : other.other.copy();
        this.pc = other.pc == null ? null : other.pc.copy();
        this.cz = other.cz == null ? null : other.cz.copy();
        this.cy = other.cy == null ? null : other.cy.copy();
        this.md = other.md == null ? null : other.md.copy();
        this.huiy = other.huiy == null ? null : other.huiy.copy();
        this.dtb = other.dtb == null ? null : other.dtb.copy();
        this.sszx = other.sszx == null ? null : other.sszx.copy();
    }

    @Override
    public ClassreportRoomCriteria copy() {
        return new ClassreportRoomCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAccount() {
        return account;
    }

    public StringFilter account() {
        if (account == null) {
            account = new StringFilter();
        }
        return account;
    }

    public void setAccount(StringFilter account) {
        this.account = account;
    }

    public StringFilter getRoomn() {
        return roomn;
    }

    public StringFilter roomn() {
        if (roomn == null) {
            roomn = new StringFilter();
        }
        return roomn;
    }

    public void setRoomn(StringFilter roomn) {
        this.roomn = roomn;
    }

    public BigDecimalFilter getYfj() {
        return yfj;
    }

    public BigDecimalFilter yfj() {
        if (yfj == null) {
            yfj = new BigDecimalFilter();
        }
        return yfj;
    }

    public void setYfj(BigDecimalFilter yfj) {
        this.yfj = yfj;
    }

    public BigDecimalFilter getYfj9008() {
        return yfj9008;
    }

    public BigDecimalFilter yfj9008() {
        if (yfj9008 == null) {
            yfj9008 = new BigDecimalFilter();
        }
        return yfj9008;
    }

    public void setYfj9008(BigDecimalFilter yfj9008) {
        this.yfj9008 = yfj9008;
    }

    public BigDecimalFilter getYfj9009() {
        return yfj9009;
    }

    public BigDecimalFilter yfj9009() {
        if (yfj9009 == null) {
            yfj9009 = new BigDecimalFilter();
        }
        return yfj9009;
    }

    public void setYfj9009(BigDecimalFilter yfj9009) {
        this.yfj9009 = yfj9009;
    }

    public BigDecimalFilter getYfj9007() {
        return yfj9007;
    }

    public BigDecimalFilter yfj9007() {
        if (yfj9007 == null) {
            yfj9007 = new BigDecimalFilter();
        }
        return yfj9007;
    }

    public void setYfj9007(BigDecimalFilter yfj9007) {
        this.yfj9007 = yfj9007;
    }

    public BigDecimalFilter getGz() {
        return gz;
    }

    public BigDecimalFilter gz() {
        if (gz == null) {
            gz = new BigDecimalFilter();
        }
        return gz;
    }

    public void setGz(BigDecimalFilter gz) {
        this.gz = gz;
    }

    public BigDecimalFilter getFf() {
        return ff;
    }

    public BigDecimalFilter ff() {
        if (ff == null) {
            ff = new BigDecimalFilter();
        }
        return ff;
    }

    public void setFf(BigDecimalFilter ff) {
        this.ff = ff;
    }

    public BigDecimalFilter getMinibar() {
        return minibar;
    }

    public BigDecimalFilter minibar() {
        if (minibar == null) {
            minibar = new BigDecimalFilter();
        }
        return minibar;
    }

    public void setMinibar(BigDecimalFilter minibar) {
        this.minibar = minibar;
    }

    public BigDecimalFilter getPhone() {
        return phone;
    }

    public BigDecimalFilter phone() {
        if (phone == null) {
            phone = new BigDecimalFilter();
        }
        return phone;
    }

    public void setPhone(BigDecimalFilter phone) {
        this.phone = phone;
    }

    public BigDecimalFilter getOther() {
        return other;
    }

    public BigDecimalFilter other() {
        if (other == null) {
            other = new BigDecimalFilter();
        }
        return other;
    }

    public void setOther(BigDecimalFilter other) {
        this.other = other;
    }

    public BigDecimalFilter getPc() {
        return pc;
    }

    public BigDecimalFilter pc() {
        if (pc == null) {
            pc = new BigDecimalFilter();
        }
        return pc;
    }

    public void setPc(BigDecimalFilter pc) {
        this.pc = pc;
    }

    public BigDecimalFilter getCz() {
        return cz;
    }

    public BigDecimalFilter cz() {
        if (cz == null) {
            cz = new BigDecimalFilter();
        }
        return cz;
    }

    public void setCz(BigDecimalFilter cz) {
        this.cz = cz;
    }

    public BigDecimalFilter getCy() {
        return cy;
    }

    public BigDecimalFilter cy() {
        if (cy == null) {
            cy = new BigDecimalFilter();
        }
        return cy;
    }

    public void setCy(BigDecimalFilter cy) {
        this.cy = cy;
    }

    public BigDecimalFilter getMd() {
        return md;
    }

    public BigDecimalFilter md() {
        if (md == null) {
            md = new BigDecimalFilter();
        }
        return md;
    }

    public void setMd(BigDecimalFilter md) {
        this.md = md;
    }

    public BigDecimalFilter getHuiy() {
        return huiy;
    }

    public BigDecimalFilter huiy() {
        if (huiy == null) {
            huiy = new BigDecimalFilter();
        }
        return huiy;
    }

    public void setHuiy(BigDecimalFilter huiy) {
        this.huiy = huiy;
    }

    public BigDecimalFilter getDtb() {
        return dtb;
    }

    public BigDecimalFilter dtb() {
        if (dtb == null) {
            dtb = new BigDecimalFilter();
        }
        return dtb;
    }

    public void setDtb(BigDecimalFilter dtb) {
        this.dtb = dtb;
    }

    public BigDecimalFilter getSszx() {
        return sszx;
    }

    public BigDecimalFilter sszx() {
        if (sszx == null) {
            sszx = new BigDecimalFilter();
        }
        return sszx;
    }

    public void setSszx(BigDecimalFilter sszx) {
        this.sszx = sszx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClassreportRoomCriteria that = (ClassreportRoomCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(account, that.account) &&
            Objects.equals(roomn, that.roomn) &&
            Objects.equals(yfj, that.yfj) &&
            Objects.equals(yfj9008, that.yfj9008) &&
            Objects.equals(yfj9009, that.yfj9009) &&
            Objects.equals(yfj9007, that.yfj9007) &&
            Objects.equals(gz, that.gz) &&
            Objects.equals(ff, that.ff) &&
            Objects.equals(minibar, that.minibar) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(other, that.other) &&
            Objects.equals(pc, that.pc) &&
            Objects.equals(cz, that.cz) &&
            Objects.equals(cy, that.cy) &&
            Objects.equals(md, that.md) &&
            Objects.equals(huiy, that.huiy) &&
            Objects.equals(dtb, that.dtb) &&
            Objects.equals(sszx, that.sszx)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            account,
            roomn,
            yfj,
            yfj9008,
            yfj9009,
            yfj9007,
            gz,
            ff,
            minibar,
            phone,
            other,
            pc,
            cz,
            cy,
            md,
            huiy,
            dtb,
            sszx
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassreportRoomCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (account != null ? "account=" + account + ", " : "") +
            (roomn != null ? "roomn=" + roomn + ", " : "") +
            (yfj != null ? "yfj=" + yfj + ", " : "") +
            (yfj9008 != null ? "yfj9008=" + yfj9008 + ", " : "") +
            (yfj9009 != null ? "yfj9009=" + yfj9009 + ", " : "") +
            (yfj9007 != null ? "yfj9007=" + yfj9007 + ", " : "") +
            (gz != null ? "gz=" + gz + ", " : "") +
            (ff != null ? "ff=" + ff + ", " : "") +
            (minibar != null ? "minibar=" + minibar + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (other != null ? "other=" + other + ", " : "") +
            (pc != null ? "pc=" + pc + ", " : "") +
            (cz != null ? "cz=" + cz + ", " : "") +
            (cy != null ? "cy=" + cy + ", " : "") +
            (md != null ? "md=" + md + ", " : "") +
            (huiy != null ? "huiy=" + huiy + ", " : "") +
            (dtb != null ? "dtb=" + dtb + ", " : "") +
            (sszx != null ? "sszx=" + sszx + ", " : "") +
            "}";
    }
}
