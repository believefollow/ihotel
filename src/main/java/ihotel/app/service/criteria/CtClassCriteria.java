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
 * Criteria class for the {@link ihotel.app.domain.CtClass} entity. This class is used
 * in {@link ihotel.app.web.rest.CtClassResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ct-classes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CtClassCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dt;

    private StringFilter empn;

    private LongFilter flag;

    private StringFilter jbempn;

    private InstantFilter gotime;

    private BigDecimalFilter xj;

    private BigDecimalFilter zp;

    private BigDecimalFilter sk;

    private BigDecimalFilter hyk;

    private BigDecimalFilter cq;

    private BigDecimalFilter gz;

    private BigDecimalFilter gfz;

    private BigDecimalFilter yq;

    private BigDecimalFilter yh;

    private BigDecimalFilter zzh;

    private StringFilter checkSign;

    public CtClassCriteria() {}

    public CtClassCriteria(CtClassCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dt = other.dt == null ? null : other.dt.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.flag = other.flag == null ? null : other.flag.copy();
        this.jbempn = other.jbempn == null ? null : other.jbempn.copy();
        this.gotime = other.gotime == null ? null : other.gotime.copy();
        this.xj = other.xj == null ? null : other.xj.copy();
        this.zp = other.zp == null ? null : other.zp.copy();
        this.sk = other.sk == null ? null : other.sk.copy();
        this.hyk = other.hyk == null ? null : other.hyk.copy();
        this.cq = other.cq == null ? null : other.cq.copy();
        this.gz = other.gz == null ? null : other.gz.copy();
        this.gfz = other.gfz == null ? null : other.gfz.copy();
        this.yq = other.yq == null ? null : other.yq.copy();
        this.yh = other.yh == null ? null : other.yh.copy();
        this.zzh = other.zzh == null ? null : other.zzh.copy();
        this.checkSign = other.checkSign == null ? null : other.checkSign.copy();
    }

    @Override
    public CtClassCriteria copy() {
        return new CtClassCriteria(this);
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

    public LongFilter getFlag() {
        return flag;
    }

    public LongFilter flag() {
        if (flag == null) {
            flag = new LongFilter();
        }
        return flag;
    }

    public void setFlag(LongFilter flag) {
        this.flag = flag;
    }

    public StringFilter getJbempn() {
        return jbempn;
    }

    public StringFilter jbempn() {
        if (jbempn == null) {
            jbempn = new StringFilter();
        }
        return jbempn;
    }

    public void setJbempn(StringFilter jbempn) {
        this.jbempn = jbempn;
    }

    public InstantFilter getGotime() {
        return gotime;
    }

    public InstantFilter gotime() {
        if (gotime == null) {
            gotime = new InstantFilter();
        }
        return gotime;
    }

    public void setGotime(InstantFilter gotime) {
        this.gotime = gotime;
    }

    public BigDecimalFilter getXj() {
        return xj;
    }

    public BigDecimalFilter xj() {
        if (xj == null) {
            xj = new BigDecimalFilter();
        }
        return xj;
    }

    public void setXj(BigDecimalFilter xj) {
        this.xj = xj;
    }

    public BigDecimalFilter getZp() {
        return zp;
    }

    public BigDecimalFilter zp() {
        if (zp == null) {
            zp = new BigDecimalFilter();
        }
        return zp;
    }

    public void setZp(BigDecimalFilter zp) {
        this.zp = zp;
    }

    public BigDecimalFilter getSk() {
        return sk;
    }

    public BigDecimalFilter sk() {
        if (sk == null) {
            sk = new BigDecimalFilter();
        }
        return sk;
    }

    public void setSk(BigDecimalFilter sk) {
        this.sk = sk;
    }

    public BigDecimalFilter getHyk() {
        return hyk;
    }

    public BigDecimalFilter hyk() {
        if (hyk == null) {
            hyk = new BigDecimalFilter();
        }
        return hyk;
    }

    public void setHyk(BigDecimalFilter hyk) {
        this.hyk = hyk;
    }

    public BigDecimalFilter getCq() {
        return cq;
    }

    public BigDecimalFilter cq() {
        if (cq == null) {
            cq = new BigDecimalFilter();
        }
        return cq;
    }

    public void setCq(BigDecimalFilter cq) {
        this.cq = cq;
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

    public BigDecimalFilter getGfz() {
        return gfz;
    }

    public BigDecimalFilter gfz() {
        if (gfz == null) {
            gfz = new BigDecimalFilter();
        }
        return gfz;
    }

    public void setGfz(BigDecimalFilter gfz) {
        this.gfz = gfz;
    }

    public BigDecimalFilter getYq() {
        return yq;
    }

    public BigDecimalFilter yq() {
        if (yq == null) {
            yq = new BigDecimalFilter();
        }
        return yq;
    }

    public void setYq(BigDecimalFilter yq) {
        this.yq = yq;
    }

    public BigDecimalFilter getYh() {
        return yh;
    }

    public BigDecimalFilter yh() {
        if (yh == null) {
            yh = new BigDecimalFilter();
        }
        return yh;
    }

    public void setYh(BigDecimalFilter yh) {
        this.yh = yh;
    }

    public BigDecimalFilter getZzh() {
        return zzh;
    }

    public BigDecimalFilter zzh() {
        if (zzh == null) {
            zzh = new BigDecimalFilter();
        }
        return zzh;
    }

    public void setZzh(BigDecimalFilter zzh) {
        this.zzh = zzh;
    }

    public StringFilter getCheckSign() {
        return checkSign;
    }

    public StringFilter checkSign() {
        if (checkSign == null) {
            checkSign = new StringFilter();
        }
        return checkSign;
    }

    public void setCheckSign(StringFilter checkSign) {
        this.checkSign = checkSign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CtClassCriteria that = (CtClassCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dt, that.dt) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(flag, that.flag) &&
            Objects.equals(jbempn, that.jbempn) &&
            Objects.equals(gotime, that.gotime) &&
            Objects.equals(xj, that.xj) &&
            Objects.equals(zp, that.zp) &&
            Objects.equals(sk, that.sk) &&
            Objects.equals(hyk, that.hyk) &&
            Objects.equals(cq, that.cq) &&
            Objects.equals(gz, that.gz) &&
            Objects.equals(gfz, that.gfz) &&
            Objects.equals(yq, that.yq) &&
            Objects.equals(yh, that.yh) &&
            Objects.equals(zzh, that.zzh) &&
            Objects.equals(checkSign, that.checkSign)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dt, empn, flag, jbempn, gotime, xj, zp, sk, hyk, cq, gz, gfz, yq, yh, zzh, checkSign);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CtClassCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dt != null ? "dt=" + dt + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (flag != null ? "flag=" + flag + ", " : "") +
            (jbempn != null ? "jbempn=" + jbempn + ", " : "") +
            (gotime != null ? "gotime=" + gotime + ", " : "") +
            (xj != null ? "xj=" + xj + ", " : "") +
            (zp != null ? "zp=" + zp + ", " : "") +
            (sk != null ? "sk=" + sk + ", " : "") +
            (hyk != null ? "hyk=" + hyk + ", " : "") +
            (cq != null ? "cq=" + cq + ", " : "") +
            (gz != null ? "gz=" + gz + ", " : "") +
            (gfz != null ? "gfz=" + gfz + ", " : "") +
            (yq != null ? "yq=" + yq + ", " : "") +
            (yh != null ? "yh=" + yh + ", " : "") +
            (zzh != null ? "zzh=" + zzh + ", " : "") +
            (checkSign != null ? "checkSign=" + checkSign + ", " : "") +
            "}";
    }
}
