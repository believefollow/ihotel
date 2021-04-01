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
 * Criteria class for the {@link ihotel.app.domain.DSpcz} entity. This class is used
 * in {@link ihotel.app.web.rest.DSpczResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /d-spczs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DSpczCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter rq;

    private InstantFilter czrq;

    public DSpczCriteria() {}

    public DSpczCriteria(DSpczCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.rq = other.rq == null ? null : other.rq.copy();
        this.czrq = other.czrq == null ? null : other.czrq.copy();
    }

    @Override
    public DSpczCriteria copy() {
        return new DSpczCriteria(this);
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

    public InstantFilter getCzrq() {
        return czrq;
    }

    public InstantFilter czrq() {
        if (czrq == null) {
            czrq = new InstantFilter();
        }
        return czrq;
    }

    public void setCzrq(InstantFilter czrq) {
        this.czrq = czrq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DSpczCriteria that = (DSpczCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(rq, that.rq) && Objects.equals(czrq, that.czrq);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rq, czrq);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DSpczCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (rq != null ? "rq=" + rq + ", " : "") +
            (czrq != null ? "czrq=" + czrq + ", " : "") +
            "}";
    }
}
