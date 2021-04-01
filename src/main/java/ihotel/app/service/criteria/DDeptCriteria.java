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
 * Criteria class for the {@link ihotel.app.domain.DDept} entity. This class is used
 * in {@link ihotel.app.web.rest.DDeptResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /d-depts?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DDeptCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter deptid;

    private StringFilter deptname;

    public DDeptCriteria() {}

    public DDeptCriteria(DDeptCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.deptid = other.deptid == null ? null : other.deptid.copy();
        this.deptname = other.deptname == null ? null : other.deptname.copy();
    }

    @Override
    public DDeptCriteria copy() {
        return new DDeptCriteria(this);
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

    public LongFilter getDeptid() {
        return deptid;
    }

    public LongFilter deptid() {
        if (deptid == null) {
            deptid = new LongFilter();
        }
        return deptid;
    }

    public void setDeptid(LongFilter deptid) {
        this.deptid = deptid;
    }

    public StringFilter getDeptname() {
        return deptname;
    }

    public StringFilter deptname() {
        if (deptname == null) {
            deptname = new StringFilter();
        }
        return deptname;
    }

    public void setDeptname(StringFilter deptname) {
        this.deptname = deptname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DDeptCriteria that = (DDeptCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(deptid, that.deptid) && Objects.equals(deptname, that.deptname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, deptid, deptname);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DDeptCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (deptid != null ? "deptid=" + deptid + ", " : "") +
            (deptname != null ? "deptname=" + deptname + ", " : "") +
            "}";
    }
}
