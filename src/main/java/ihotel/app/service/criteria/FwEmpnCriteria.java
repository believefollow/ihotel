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
 * Criteria class for the {@link ihotel.app.domain.FwEmpn} entity. This class is used
 * in {@link ihotel.app.web.rest.FwEmpnResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fw-empns?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FwEmpnCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter empnid;

    private StringFilter empn;

    private LongFilter deptid;

    private StringFilter phone;

    public FwEmpnCriteria() {}

    public FwEmpnCriteria(FwEmpnCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.empnid = other.empnid == null ? null : other.empnid.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.deptid = other.deptid == null ? null : other.deptid.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
    }

    @Override
    public FwEmpnCriteria copy() {
        return new FwEmpnCriteria(this);
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

    public StringFilter getEmpnid() {
        return empnid;
    }

    public StringFilter empnid() {
        if (empnid == null) {
            empnid = new StringFilter();
        }
        return empnid;
    }

    public void setEmpnid(StringFilter empnid) {
        this.empnid = empnid;
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

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FwEmpnCriteria that = (FwEmpnCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(empnid, that.empnid) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(deptid, that.deptid) &&
            Objects.equals(phone, that.phone)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, empnid, empn, deptid, phone);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FwEmpnCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (empnid != null ? "empnid=" + empnid + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (deptid != null ? "deptid=" + deptid + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            "}";
    }
}
