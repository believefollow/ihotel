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
 * Criteria class for the {@link ihotel.app.domain.DCktype} entity. This class is used
 * in {@link ihotel.app.web.rest.DCktypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /d-cktypes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DCktypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter cktype;

    private StringFilter memo;

    private StringFilter sign;

    public DCktypeCriteria() {}

    public DCktypeCriteria(DCktypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.cktype = other.cktype == null ? null : other.cktype.copy();
        this.memo = other.memo == null ? null : other.memo.copy();
        this.sign = other.sign == null ? null : other.sign.copy();
    }

    @Override
    public DCktypeCriteria copy() {
        return new DCktypeCriteria(this);
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

    public StringFilter getCktype() {
        return cktype;
    }

    public StringFilter cktype() {
        if (cktype == null) {
            cktype = new StringFilter();
        }
        return cktype;
    }

    public void setCktype(StringFilter cktype) {
        this.cktype = cktype;
    }

    public StringFilter getMemo() {
        return memo;
    }

    public StringFilter memo() {
        if (memo == null) {
            memo = new StringFilter();
        }
        return memo;
    }

    public void setMemo(StringFilter memo) {
        this.memo = memo;
    }

    public StringFilter getSign() {
        return sign;
    }

    public StringFilter sign() {
        if (sign == null) {
            sign = new StringFilter();
        }
        return sign;
    }

    public void setSign(StringFilter sign) {
        this.sign = sign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DCktypeCriteria that = (DCktypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(cktype, that.cktype) &&
            Objects.equals(memo, that.memo) &&
            Objects.equals(sign, that.sign)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cktype, memo, sign);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DCktypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (cktype != null ? "cktype=" + cktype + ", " : "") +
            (memo != null ? "memo=" + memo + ", " : "") +
            (sign != null ? "sign=" + sign + ", " : "") +
            "}";
    }
}
