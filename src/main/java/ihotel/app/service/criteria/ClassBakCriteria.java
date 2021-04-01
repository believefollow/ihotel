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
 * Criteria class for the {@link ihotel.app.domain.ClassBak} entity. This class is used
 * in {@link ihotel.app.web.rest.ClassBakResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /class-baks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClassBakCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter empn;

    private InstantFilter dt;

    private InstantFilter rq;

    private StringFilter ghname;

    private StringFilter bak;

    public ClassBakCriteria() {}

    public ClassBakCriteria(ClassBakCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.dt = other.dt == null ? null : other.dt.copy();
        this.rq = other.rq == null ? null : other.rq.copy();
        this.ghname = other.ghname == null ? null : other.ghname.copy();
        this.bak = other.bak == null ? null : other.bak.copy();
    }

    @Override
    public ClassBakCriteria copy() {
        return new ClassBakCriteria(this);
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

    public StringFilter getGhname() {
        return ghname;
    }

    public StringFilter ghname() {
        if (ghname == null) {
            ghname = new StringFilter();
        }
        return ghname;
    }

    public void setGhname(StringFilter ghname) {
        this.ghname = ghname;
    }

    public StringFilter getBak() {
        return bak;
    }

    public StringFilter bak() {
        if (bak == null) {
            bak = new StringFilter();
        }
        return bak;
    }

    public void setBak(StringFilter bak) {
        this.bak = bak;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClassBakCriteria that = (ClassBakCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(dt, that.dt) &&
            Objects.equals(rq, that.rq) &&
            Objects.equals(ghname, that.ghname) &&
            Objects.equals(bak, that.bak)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, empn, dt, rq, ghname, bak);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClassBakCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (dt != null ? "dt=" + dt + ", " : "") +
            (rq != null ? "rq=" + rq + ", " : "") +
            (ghname != null ? "ghname=" + ghname + ", " : "") +
            (bak != null ? "bak=" + bak + ", " : "") +
            "}";
    }
}
