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
 * Criteria class for the {@link ihotel.app.domain.DXs} entity. This class is used
 * in {@link ihotel.app.web.rest.DXsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /d-xs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DXsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter begintime;

    private InstantFilter endtime;

    private StringFilter ckbillno;

    private StringFilter depot;

    private LongFilter kcid;

    private LongFilter ckid;

    private StringFilter spbm;

    private StringFilter spmc;

    private StringFilter unit;

    private BigDecimalFilter rkprice;

    private BigDecimalFilter xsprice;

    private BigDecimalFilter sl;

    private BigDecimalFilter rkje;

    private BigDecimalFilter xsje;

    public DXsCriteria() {}

    public DXsCriteria(DXsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.begintime = other.begintime == null ? null : other.begintime.copy();
        this.endtime = other.endtime == null ? null : other.endtime.copy();
        this.ckbillno = other.ckbillno == null ? null : other.ckbillno.copy();
        this.depot = other.depot == null ? null : other.depot.copy();
        this.kcid = other.kcid == null ? null : other.kcid.copy();
        this.ckid = other.ckid == null ? null : other.ckid.copy();
        this.spbm = other.spbm == null ? null : other.spbm.copy();
        this.spmc = other.spmc == null ? null : other.spmc.copy();
        this.unit = other.unit == null ? null : other.unit.copy();
        this.rkprice = other.rkprice == null ? null : other.rkprice.copy();
        this.xsprice = other.xsprice == null ? null : other.xsprice.copy();
        this.sl = other.sl == null ? null : other.sl.copy();
        this.rkje = other.rkje == null ? null : other.rkje.copy();
        this.xsje = other.xsje == null ? null : other.xsje.copy();
    }

    @Override
    public DXsCriteria copy() {
        return new DXsCriteria(this);
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

    public InstantFilter getBegintime() {
        return begintime;
    }

    public InstantFilter begintime() {
        if (begintime == null) {
            begintime = new InstantFilter();
        }
        return begintime;
    }

    public void setBegintime(InstantFilter begintime) {
        this.begintime = begintime;
    }

    public InstantFilter getEndtime() {
        return endtime;
    }

    public InstantFilter endtime() {
        if (endtime == null) {
            endtime = new InstantFilter();
        }
        return endtime;
    }

    public void setEndtime(InstantFilter endtime) {
        this.endtime = endtime;
    }

    public StringFilter getCkbillno() {
        return ckbillno;
    }

    public StringFilter ckbillno() {
        if (ckbillno == null) {
            ckbillno = new StringFilter();
        }
        return ckbillno;
    }

    public void setCkbillno(StringFilter ckbillno) {
        this.ckbillno = ckbillno;
    }

    public StringFilter getDepot() {
        return depot;
    }

    public StringFilter depot() {
        if (depot == null) {
            depot = new StringFilter();
        }
        return depot;
    }

    public void setDepot(StringFilter depot) {
        this.depot = depot;
    }

    public LongFilter getKcid() {
        return kcid;
    }

    public LongFilter kcid() {
        if (kcid == null) {
            kcid = new LongFilter();
        }
        return kcid;
    }

    public void setKcid(LongFilter kcid) {
        this.kcid = kcid;
    }

    public LongFilter getCkid() {
        return ckid;
    }

    public LongFilter ckid() {
        if (ckid == null) {
            ckid = new LongFilter();
        }
        return ckid;
    }

    public void setCkid(LongFilter ckid) {
        this.ckid = ckid;
    }

    public StringFilter getSpbm() {
        return spbm;
    }

    public StringFilter spbm() {
        if (spbm == null) {
            spbm = new StringFilter();
        }
        return spbm;
    }

    public void setSpbm(StringFilter spbm) {
        this.spbm = spbm;
    }

    public StringFilter getSpmc() {
        return spmc;
    }

    public StringFilter spmc() {
        if (spmc == null) {
            spmc = new StringFilter();
        }
        return spmc;
    }

    public void setSpmc(StringFilter spmc) {
        this.spmc = spmc;
    }

    public StringFilter getUnit() {
        return unit;
    }

    public StringFilter unit() {
        if (unit == null) {
            unit = new StringFilter();
        }
        return unit;
    }

    public void setUnit(StringFilter unit) {
        this.unit = unit;
    }

    public BigDecimalFilter getRkprice() {
        return rkprice;
    }

    public BigDecimalFilter rkprice() {
        if (rkprice == null) {
            rkprice = new BigDecimalFilter();
        }
        return rkprice;
    }

    public void setRkprice(BigDecimalFilter rkprice) {
        this.rkprice = rkprice;
    }

    public BigDecimalFilter getXsprice() {
        return xsprice;
    }

    public BigDecimalFilter xsprice() {
        if (xsprice == null) {
            xsprice = new BigDecimalFilter();
        }
        return xsprice;
    }

    public void setXsprice(BigDecimalFilter xsprice) {
        this.xsprice = xsprice;
    }

    public BigDecimalFilter getSl() {
        return sl;
    }

    public BigDecimalFilter sl() {
        if (sl == null) {
            sl = new BigDecimalFilter();
        }
        return sl;
    }

    public void setSl(BigDecimalFilter sl) {
        this.sl = sl;
    }

    public BigDecimalFilter getRkje() {
        return rkje;
    }

    public BigDecimalFilter rkje() {
        if (rkje == null) {
            rkje = new BigDecimalFilter();
        }
        return rkje;
    }

    public void setRkje(BigDecimalFilter rkje) {
        this.rkje = rkje;
    }

    public BigDecimalFilter getXsje() {
        return xsje;
    }

    public BigDecimalFilter xsje() {
        if (xsje == null) {
            xsje = new BigDecimalFilter();
        }
        return xsje;
    }

    public void setXsje(BigDecimalFilter xsje) {
        this.xsje = xsje;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DXsCriteria that = (DXsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(begintime, that.begintime) &&
            Objects.equals(endtime, that.endtime) &&
            Objects.equals(ckbillno, that.ckbillno) &&
            Objects.equals(depot, that.depot) &&
            Objects.equals(kcid, that.kcid) &&
            Objects.equals(ckid, that.ckid) &&
            Objects.equals(spbm, that.spbm) &&
            Objects.equals(spmc, that.spmc) &&
            Objects.equals(unit, that.unit) &&
            Objects.equals(rkprice, that.rkprice) &&
            Objects.equals(xsprice, that.xsprice) &&
            Objects.equals(sl, that.sl) &&
            Objects.equals(rkje, that.rkje) &&
            Objects.equals(xsje, that.xsje)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, begintime, endtime, ckbillno, depot, kcid, ckid, spbm, spmc, unit, rkprice, xsprice, sl, rkje, xsje);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DXsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (begintime != null ? "begintime=" + begintime + ", " : "") +
            (endtime != null ? "endtime=" + endtime + ", " : "") +
            (ckbillno != null ? "ckbillno=" + ckbillno + ", " : "") +
            (depot != null ? "depot=" + depot + ", " : "") +
            (kcid != null ? "kcid=" + kcid + ", " : "") +
            (ckid != null ? "ckid=" + ckid + ", " : "") +
            (spbm != null ? "spbm=" + spbm + ", " : "") +
            (spmc != null ? "spmc=" + spmc + ", " : "") +
            (unit != null ? "unit=" + unit + ", " : "") +
            (rkprice != null ? "rkprice=" + rkprice + ", " : "") +
            (xsprice != null ? "xsprice=" + xsprice + ", " : "") +
            (sl != null ? "sl=" + sl + ", " : "") +
            (rkje != null ? "rkje=" + rkje + ", " : "") +
            (xsje != null ? "xsje=" + xsje + ", " : "") +
            "}";
    }
}
