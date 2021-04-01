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
 * Criteria class for the {@link ihotel.app.domain.DKc} entity. This class is used
 * in {@link ihotel.app.web.rest.DKcResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /d-kcs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DKcCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter depot;

    private StringFilter spbm;

    private StringFilter spmc;

    private StringFilter xh;

    private StringFilter dw;

    private BigDecimalFilter price;

    private BigDecimalFilter sl;

    private BigDecimalFilter je;

    public DKcCriteria() {}

    public DKcCriteria(DKcCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.depot = other.depot == null ? null : other.depot.copy();
        this.spbm = other.spbm == null ? null : other.spbm.copy();
        this.spmc = other.spmc == null ? null : other.spmc.copy();
        this.xh = other.xh == null ? null : other.xh.copy();
        this.dw = other.dw == null ? null : other.dw.copy();
        this.price = other.price == null ? null : other.price.copy();
        this.sl = other.sl == null ? null : other.sl.copy();
        this.je = other.je == null ? null : other.je.copy();
    }

    @Override
    public DKcCriteria copy() {
        return new DKcCriteria(this);
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

    public StringFilter getXh() {
        return xh;
    }

    public StringFilter xh() {
        if (xh == null) {
            xh = new StringFilter();
        }
        return xh;
    }

    public void setXh(StringFilter xh) {
        this.xh = xh;
    }

    public StringFilter getDw() {
        return dw;
    }

    public StringFilter dw() {
        if (dw == null) {
            dw = new StringFilter();
        }
        return dw;
    }

    public void setDw(StringFilter dw) {
        this.dw = dw;
    }

    public BigDecimalFilter getPrice() {
        return price;
    }

    public BigDecimalFilter price() {
        if (price == null) {
            price = new BigDecimalFilter();
        }
        return price;
    }

    public void setPrice(BigDecimalFilter price) {
        this.price = price;
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

    public BigDecimalFilter getJe() {
        return je;
    }

    public BigDecimalFilter je() {
        if (je == null) {
            je = new BigDecimalFilter();
        }
        return je;
    }

    public void setJe(BigDecimalFilter je) {
        this.je = je;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DKcCriteria that = (DKcCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(depot, that.depot) &&
            Objects.equals(spbm, that.spbm) &&
            Objects.equals(spmc, that.spmc) &&
            Objects.equals(xh, that.xh) &&
            Objects.equals(dw, that.dw) &&
            Objects.equals(price, that.price) &&
            Objects.equals(sl, that.sl) &&
            Objects.equals(je, that.je)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, depot, spbm, spmc, xh, dw, price, sl, je);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DKcCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (depot != null ? "depot=" + depot + ", " : "") +
            (spbm != null ? "spbm=" + spbm + ", " : "") +
            (spmc != null ? "spmc=" + spmc + ", " : "") +
            (xh != null ? "xh=" + xh + ", " : "") +
            (dw != null ? "dw=" + dw + ", " : "") +
            (price != null ? "price=" + price + ", " : "") +
            (sl != null ? "sl=" + sl + ", " : "") +
            (je != null ? "je=" + je + ", " : "") +
            "}";
    }
}
