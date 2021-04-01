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
 * Criteria class for the {@link ihotel.app.domain.Auditinfo} entity. This class is used
 * in {@link ihotel.app.web.rest.AuditinfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /auditinfos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AuditinfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter auditdate;

    private InstantFilter audittime;

    private StringFilter empn;

    private StringFilter aidentify;

    public AuditinfoCriteria() {}

    public AuditinfoCriteria(AuditinfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.auditdate = other.auditdate == null ? null : other.auditdate.copy();
        this.audittime = other.audittime == null ? null : other.audittime.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.aidentify = other.aidentify == null ? null : other.aidentify.copy();
    }

    @Override
    public AuditinfoCriteria copy() {
        return new AuditinfoCriteria(this);
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

    public InstantFilter getAuditdate() {
        return auditdate;
    }

    public InstantFilter auditdate() {
        if (auditdate == null) {
            auditdate = new InstantFilter();
        }
        return auditdate;
    }

    public void setAuditdate(InstantFilter auditdate) {
        this.auditdate = auditdate;
    }

    public InstantFilter getAudittime() {
        return audittime;
    }

    public InstantFilter audittime() {
        if (audittime == null) {
            audittime = new InstantFilter();
        }
        return audittime;
    }

    public void setAudittime(InstantFilter audittime) {
        this.audittime = audittime;
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

    public StringFilter getAidentify() {
        return aidentify;
    }

    public StringFilter aidentify() {
        if (aidentify == null) {
            aidentify = new StringFilter();
        }
        return aidentify;
    }

    public void setAidentify(StringFilter aidentify) {
        this.aidentify = aidentify;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AuditinfoCriteria that = (AuditinfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(auditdate, that.auditdate) &&
            Objects.equals(audittime, that.audittime) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(aidentify, that.aidentify)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, auditdate, audittime, empn, aidentify);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditinfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (auditdate != null ? "auditdate=" + auditdate + ", " : "") +
            (audittime != null ? "audittime=" + audittime + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (aidentify != null ? "aidentify=" + aidentify + ", " : "") +
            "}";
    }
}
