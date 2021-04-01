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
 * Criteria class for the {@link ihotel.app.domain.DCktime} entity. This class is used
 * in {@link ihotel.app.web.rest.DCktimeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /d-cktimes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DCktimeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter begintime;

    private InstantFilter endtime;

    private StringFilter depot;

    private StringFilter ckbillno;

    public DCktimeCriteria() {}

    public DCktimeCriteria(DCktimeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.begintime = other.begintime == null ? null : other.begintime.copy();
        this.endtime = other.endtime == null ? null : other.endtime.copy();
        this.depot = other.depot == null ? null : other.depot.copy();
        this.ckbillno = other.ckbillno == null ? null : other.ckbillno.copy();
    }

    @Override
    public DCktimeCriteria copy() {
        return new DCktimeCriteria(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DCktimeCriteria that = (DCktimeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(begintime, that.begintime) &&
            Objects.equals(endtime, that.endtime) &&
            Objects.equals(depot, that.depot) &&
            Objects.equals(ckbillno, that.ckbillno)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, begintime, endtime, depot, ckbillno);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DCktimeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (begintime != null ? "begintime=" + begintime + ", " : "") +
            (endtime != null ? "endtime=" + endtime + ", " : "") +
            (depot != null ? "depot=" + depot + ", " : "") +
            (ckbillno != null ? "ckbillno=" + ckbillno + ", " : "") +
            "}";
    }
}
