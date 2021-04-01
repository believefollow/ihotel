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
 * Criteria class for the {@link ihotel.app.domain.Clog} entity. This class is used
 * in {@link ihotel.app.web.rest.ClogResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clogs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClogCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter empn;

    private InstantFilter begindate;

    private InstantFilter enddate;

    private InstantFilter dqrq;

    public ClogCriteria() {}

    public ClogCriteria(ClogCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.begindate = other.begindate == null ? null : other.begindate.copy();
        this.enddate = other.enddate == null ? null : other.enddate.copy();
        this.dqrq = other.dqrq == null ? null : other.dqrq.copy();
    }

    @Override
    public ClogCriteria copy() {
        return new ClogCriteria(this);
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

    public InstantFilter getBegindate() {
        return begindate;
    }

    public InstantFilter begindate() {
        if (begindate == null) {
            begindate = new InstantFilter();
        }
        return begindate;
    }

    public void setBegindate(InstantFilter begindate) {
        this.begindate = begindate;
    }

    public InstantFilter getEnddate() {
        return enddate;
    }

    public InstantFilter enddate() {
        if (enddate == null) {
            enddate = new InstantFilter();
        }
        return enddate;
    }

    public void setEnddate(InstantFilter enddate) {
        this.enddate = enddate;
    }

    public InstantFilter getDqrq() {
        return dqrq;
    }

    public InstantFilter dqrq() {
        if (dqrq == null) {
            dqrq = new InstantFilter();
        }
        return dqrq;
    }

    public void setDqrq(InstantFilter dqrq) {
        this.dqrq = dqrq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClogCriteria that = (ClogCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(begindate, that.begindate) &&
            Objects.equals(enddate, that.enddate) &&
            Objects.equals(dqrq, that.dqrq)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, empn, begindate, enddate, dqrq);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClogCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (begindate != null ? "begindate=" + begindate + ", " : "") +
            (enddate != null ? "enddate=" + enddate + ", " : "") +
            (dqrq != null ? "dqrq=" + dqrq + ", " : "") +
            "}";
    }
}
