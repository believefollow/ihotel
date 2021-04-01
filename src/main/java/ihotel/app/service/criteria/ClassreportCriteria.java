package ihotel.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ihotel.app.domain.Classreport} entity. This class is used
 * in {@link ihotel.app.web.rest.ClassreportResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /classreports?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClassreportCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter empn;

    private InstantFilter dt;

    private BigDecimalFilter xjUp;

    private BigDecimalFilter yfjA;

    private BigDecimalFilter yfjD;

    private BigDecimalFilter gz;

    private BigDecimalFilter zz;

    private BigDecimalFilter zzYj;

    private BigDecimalFilter zzJs;

    private BigDecimalFilter zzTc;

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

    private BigDecimalFilter cyz;

    private StringFilter hoteldm;

    private BigDecimalFilter gzxj;

    private LongFilter isnew;

    public ClassreportCriteria() {}

    public ClassreportCriteria(ClassreportCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.dt = other.dt == null ? null : other.dt.copy();
        this.xjUp = other.xjUp == null ? null : other.xjUp.copy();
        this.yfjA = other.yfjA == null ? null : other.yfjA.copy();
        this.yfjD = other.yfjD == null ? null : other.yfjD.copy();
        this.gz = other.gz == null ? null : other.gz.copy();
        this.zz = other.zz == null ? null : other.zz.copy();
        this.zzYj = other.zzYj == null ? null : other.zzYj.copy();
        this.zzJs = other.zzJs == null ? null : other.zzJs.copy();
        this.zzTc = other.zzTc == null ? null : other.zzTc.copy();
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
        this.cyz = other.cyz == null ? null : other.cyz.copy();
        this.hoteldm = other.hoteldm == null ? null : other.hoteldm.copy();
        this.gzxj = other.gzxj == null ? null : other.gzxj.copy();
        this.isnew = other.isnew == null ? null : other.isnew.copy();
    }

    @Override
    public ClassreportCriteria copy() {
        return new ClassreportCriteria(this);
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

    public StringFilter getEmpn() {
        return empn;
    }

    public StringFilter empn() {
        if (empn == null) {
            empn = new StringFilter();
        }
        return empn;
    }

    public void setEmpn(StringFilter empn) {
        this.empn = empn;
    }

    public InstantFilter getDt() {
        return dt;
    }

    public InstantFilter dt() {
        if (dt == null) {
            dt = new InstantFilter();
        }
        return dt;
    }

    public void setDt(InstantFilter dt) {
        this.dt = dt;
    }

    public BigDecimalFilter getXjUp() {
        return xjUp;
    }

    public BigDecimalFilter xjUp() {
        if (xjUp == null) {
            xjUp = new BigDecimalFilter();
        }
        return xjUp;
    }

    public void setXjUp(BigDecimalFilter xjUp) {
        this.xjUp = xjUp;
    }

    public BigDecimalFilter getYfjA() {
        return yfjA;
    }

    public BigDecimalFilter yfjA() {
        if (yfjA == null) {
            yfjA = new BigDecimalFilter();
        }
        return yfjA;
    }

    public void setYfjA(BigDecimalFilter yfjA) {
        this.yfjA = yfjA;
    }

    public BigDecimalFilter getYfjD() {
        return yfjD;
    }

    public BigDecimalFilter yfjD() {
        if (yfjD == null) {
            yfjD = new BigDecimalFilter();
        }
        return yfjD;
    }

    public void setYfjD(BigDecimalFilter yfjD) {
        this.yfjD = yfjD;
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

    public BigDecimalFilter getZz() {
        return zz;
    }

    public BigDecimalFilter zz() {
        if (zz == null) {
            zz = new BigDecimalFilter();
        }
        return zz;
    }

    public void setZz(BigDecimalFilter zz) {
        this.zz = zz;
    }

    public BigDecimalFilter getZzYj() {
        return zzYj;
    }

    public BigDecimalFilter zzYj() {
        if (zzYj == null) {
            zzYj = new BigDecimalFilter();
        }
        return zzYj;
    }

    public void setZzYj(BigDecimalFilter zzYj) {
        this.zzYj = zzYj;
    }

    public BigDecimalFilter getZzJs() {
        return zzJs;
    }

    public BigDecimalFilter zzJs() {
        if (zzJs == null) {
            zzJs = new BigDecimalFilter();
        }
        return zzJs;
    }

    public void setZzJs(BigDecimalFilter zzJs) {
        this.zzJs = zzJs;
    }

    public BigDecimalFilter getZzTc() {
        return zzTc;
    }

    public BigDecimalFilter zzTc() {
        if (zzTc == null) {
            zzTc = new BigDecimalFilter();
        }
        return zzTc;
    }

    public void setZzTc(BigDecimalFilter zzTc) {
        this.zzTc = zzTc;
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

    public BigDecimalFilter getCyz() {
        return cyz;
    }

    public BigDecimalFilter cyz() {
        if (cyz == null) {
            cyz = new BigDecimalFilter();
        }
        return cyz;
    }

    public void setCyz(BigDecimalFilter cyz) {
        this.cyz = cyz;
    }

    public StringFilter getHoteldm() {
        return hoteldm;
    }

    public StringFilter hoteldm() {
        if (hoteldm == null) {
            hoteldm = new StringFilter();
        }
        return hoteldm;
    }

    public void setHoteldm(StringFilter hoteldm) {
        this.hoteldm = hoteldm;
    }

    public BigDecimalFilter getGzxj() {
        return gzxj;
    }

    public BigDecimalFilter gzxj() {
        if (gzxj == null) {
            gzxj = new BigDecimalFilter();
        }
        return gzxj;
    }

    public void setGzxj(BigDecimalFilter gzxj) {
        this.gzxj = gzxj;
    }

    public LongFilter getIsnew() {
        return isnew;
    }

    public LongFilter isnew() {
        if (isnew == null) {
            isnew = new LongFilter();
        }
        return isnew;
    }

    public void setIsnew(LongFilter isnew) {
        this.isnew = isnew;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClassreportCriteria that = (ClassreportCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(dt, that.dt) &&
            Objects.equals(xjUp, that.xjUp) &&
            Objects.equals(yfjA, that.yfjA) &&
            Objects.equals(yfjD, that.yfjD) &&
            Objects.equals(gz, that.gz) &&
            Objects.equals(zz, that.zz) &&
            Objects.equals(zzYj, that.zzYj) &&
            Objects.equals(zzJs, that.zzJs) &&
            Objects.equals(zzTc, that.zzTc) &&
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
            Objects.equals(sszx, that.sszx) &&
            Objects.equals(cyz, that.cyz) &&
            Objects.equals(hoteldm, that.hoteldm) &&
            Objects.equals(gzxj, that.gzxj) &&
            Objects.equals(isnew, that.isnew)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            empn,
            dt,
            xjUp,
            yfjA,
            yfjD,
            gz,
            zz,
            zzYj,
            zzJs,
            zzTc,
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
            sszx,
            cyz,
            hoteldm,
            gzxj,
            isnew
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassreportCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (dt != null ? "dt=" + dt + ", " : "") +
            (xjUp != null ? "xjUp=" + xjUp + ", " : "") +
            (yfjA != null ? "yfjA=" + yfjA + ", " : "") +
            (yfjD != null ? "yfjD=" + yfjD + ", " : "") +
            (gz != null ? "gz=" + gz + ", " : "") +
            (zz != null ? "zz=" + zz + ", " : "") +
            (zzYj != null ? "zzYj=" + zzYj + ", " : "") +
            (zzJs != null ? "zzJs=" + zzJs + ", " : "") +
            (zzTc != null ? "zzTc=" + zzTc + ", " : "") +
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
            (cyz != null ? "cyz=" + cyz + ", " : "") +
            (hoteldm != null ? "hoteldm=" + hoteldm + ", " : "") +
            (gzxj != null ? "gzxj=" + gzxj + ", " : "") +
            (isnew != null ? "isnew=" + isnew + ", " : "") +
            "}";
    }
}
