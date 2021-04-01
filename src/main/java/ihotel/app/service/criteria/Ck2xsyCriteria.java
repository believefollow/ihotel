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
 * Criteria class for the {@link ihotel.app.domain.Ck2xsy} entity. This class is used
 * in {@link ihotel.app.web.rest.Ck2xsyResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ck-2-xsies?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class Ck2xsyCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter rq;

    private StringFilter cpbh;

    private LongFilter sl;

    public Ck2xsyCriteria() {}

    public Ck2xsyCriteria(Ck2xsyCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rq = other.rq == null ? null : other.rq.copy();
        this.cpbh = other.cpbh == null ? null : other.cpbh.copy();
        this.sl = other.sl == null ? null : other.sl.copy();
    }

    @Override
    public Ck2xsyCriteria copy() {
        return new Ck2xsyCriteria(this);
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

    public InstantFilter getRq() {
        return rq;
    }

    public InstantFilter rq() {
        if (rq == null) {
            rq = new InstantFilter();
        }
        return rq;
    }

    public void setRq(InstantFilter rq) {
        this.rq = rq;
    }

    public StringFilter getCpbh() {
        return cpbh;
    }

    public StringFilter cpbh() {
        if (cpbh == null) {
            cpbh = new StringFilter();
        }
        return cpbh;
    }

    public void setCpbh(StringFilter cpbh) {
        this.cpbh = cpbh;
    }

    public LongFilter getSl() {
        return sl;
    }

    public LongFilter sl() {
        if (sl == null) {
            sl = new LongFilter();
        }
        return sl;
    }

    public void setSl(LongFilter sl) {
        this.sl = sl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Ck2xsyCriteria that = (Ck2xsyCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(rq, that.rq) && Objects.equals(cpbh, that.cpbh) && Objects.equals(sl, that.sl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rq, cpbh, sl);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ck2xsyCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (rq != null ? "rq=" + rq + ", " : "") +
            (cpbh != null ? "cpbh=" + cpbh + ", " : "") +
            (sl != null ? "sl=" + sl + ", " : "") +
            "}";
    }
}
