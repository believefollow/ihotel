package ihotel.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ihotel.app.domain.DType} entity. This class is used
 * in {@link ihotel.app.web.rest.DTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /d-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter typeid;

    private StringFilter typename;

    private LongFilter fatherid;

    private LongFilter disabled;

    public DTypeCriteria() {}

    public DTypeCriteria(DTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.typeid = other.typeid == null ? null : other.typeid.copy();
        this.typename = other.typename == null ? null : other.typename.copy();
        this.fatherid = other.fatherid == null ? null : other.fatherid.copy();
        this.disabled = other.disabled == null ? null : other.disabled.copy();
    }

    @Override
    public DTypeCriteria copy() {
        return new DTypeCriteria(this);
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

    public LongFilter getTypeid() {
        return typeid;
    }

    public LongFilter typeid() {
        if (typeid == null) {
            typeid = new LongFilter();
        }
        return typeid;
    }

    public void setTypeid(LongFilter typeid) {
        this.typeid = typeid;
    }

    public StringFilter getTypename() {
        return typename;
    }

    public StringFilter typename() {
        if (typename == null) {
            typename = new StringFilter();
        }
        return typename;
    }

    public void setTypename(StringFilter typename) {
        this.typename = typename;
    }

    public LongFilter getFatherid() {
        return fatherid;
    }

    public LongFilter fatherid() {
        if (fatherid == null) {
            fatherid = new LongFilter();
        }
        return fatherid;
    }

    public void setFatherid(LongFilter fatherid) {
        this.fatherid = fatherid;
    }

    public LongFilter getDisabled() {
        return disabled;
    }

    public LongFilter disabled() {
        if (disabled == null) {
            disabled = new LongFilter();
        }
        return disabled;
    }

    public void setDisabled(LongFilter disabled) {
        this.disabled = disabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DTypeCriteria that = (DTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(typeid, that.typeid) &&
            Objects.equals(typename, that.typename) &&
            Objects.equals(fatherid, that.fatherid) &&
            Objects.equals(disabled, that.disabled)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, typeid, typename, fatherid, disabled);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (typeid != null ? "typeid=" + typeid + ", " : "") +
            (typename != null ? "typename=" + typename + ", " : "") +
            (fatherid != null ? "fatherid=" + fatherid + ", " : "") +
            (disabled != null ? "disabled=" + disabled + ", " : "") +
            "}";
    }
}
