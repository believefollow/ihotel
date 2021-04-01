package ihotel.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ihotel.app.domain.DxSed} entity. This class is used
 * in {@link ihotel.app.web.rest.DxSedResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dx-seds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DxSedCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter dxRq;

    private StringFilter dxZt;

    private InstantFilter fsSj;

    public DxSedCriteria() {}

    public DxSedCriteria(DxSedCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dxRq = other.dxRq == null ? null : other.dxRq.copy();
        this.dxZt = other.dxZt == null ? null : other.dxZt.copy();
        this.fsSj = other.fsSj == null ? null : other.fsSj.copy();
    }

    @Override
    public DxSedCriteria copy() {
        return new DxSedCriteria(this);
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

    public InstantFilter getDxRq() {
        return dxRq;
    }

    public InstantFilter dxRq() {
        if (dxRq == null) {
            dxRq = new InstantFilter();
        }
        return dxRq;
    }

    public void setDxRq(InstantFilter dxRq) {
        this.dxRq = dxRq;
    }

    public StringFilter getDxZt() {
        return dxZt;
    }

    public StringFilter dxZt() {
        if (dxZt == null) {
            dxZt = new StringFilter();
        }
        return dxZt;
    }

    public void setDxZt(StringFilter dxZt) {
        this.dxZt = dxZt;
    }

    public InstantFilter getFsSj() {
        return fsSj;
    }

    public InstantFilter fsSj() {
        if (fsSj == null) {
            fsSj = new InstantFilter();
        }
        return fsSj;
    }

    public void setFsSj(InstantFilter fsSj) {
        this.fsSj = fsSj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DxSedCriteria that = (DxSedCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(dxRq, that.dxRq) &&
            Objects.equals(dxZt, that.dxZt) &&
            Objects.equals(fsSj, that.fsSj)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dxRq, dxZt, fsSj);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DxSedCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (dxRq != null ? "dxRq=" + dxRq + ", " : "") +
            (dxZt != null ? "dxZt=" + dxZt + ", " : "") +
            (fsSj != null ? "fsSj=" + fsSj + ", " : "") +
            "}";
    }
}
