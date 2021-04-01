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
 * Criteria class for the {@link ihotel.app.domain.FwDs} entity. This class is used
 * in {@link ihotel.app.web.rest.FwDsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fw-ds?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FwDsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter hoteltime;

    private InstantFilter rq;

    private LongFilter xz;

    private StringFilter memo;

    private StringFilter fwy;

    private StringFilter roomn;

    private StringFilter rtype;

    private StringFilter empn;

    private LongFilter sl;

    public FwDsCriteria() {}

    public FwDsCriteria(FwDsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.hoteltime = other.hoteltime == null ? null : other.hoteltime.copy();
        this.rq = other.rq == null ? null : other.rq.copy();
        this.xz = other.xz == null ? null : other.xz.copy();
        this.memo = other.memo == null ? null : other.memo.copy();
        this.fwy = other.fwy == null ? null : other.fwy.copy();
        this.roomn = other.roomn == null ? null : other.roomn.copy();
        this.rtype = other.rtype == null ? null : other.rtype.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.sl = other.sl == null ? null : other.sl.copy();
    }

    @Override
    public FwDsCriteria copy() {
        return new FwDsCriteria(this);
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

    public InstantFilter getHoteltime() {
        return hoteltime;
    }

    public InstantFilter hoteltime() {
        if (hoteltime == null) {
            hoteltime = new InstantFilter();
        }
        return hoteltime;
    }

    public void setHoteltime(InstantFilter hoteltime) {
        this.hoteltime = hoteltime;
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

    public LongFilter getXz() {
        return xz;
    }

    public LongFilter xz() {
        if (xz == null) {
            xz = new LongFilter();
        }
        return xz;
    }

    public void setXz(LongFilter xz) {
        this.xz = xz;
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

    public StringFilter getFwy() {
        return fwy;
    }

    public StringFilter fwy() {
        if (fwy == null) {
            fwy = new StringFilter();
        }
        return fwy;
    }

    public void setFwy(StringFilter fwy) {
        this.fwy = fwy;
    }

    public StringFilter getRoomn() {
        return roomn;
    }

    public StringFilter roomn() {
        if (roomn == null) {
            roomn = new StringFilter();
        }
        return roomn;
    }

    public void setRoomn(StringFilter roomn) {
        this.roomn = roomn;
    }

    public StringFilter getRtype() {
        return rtype;
    }

    public StringFilter rtype() {
        if (rtype == null) {
            rtype = new StringFilter();
        }
        return rtype;
    }

    public void setRtype(StringFilter rtype) {
        this.rtype = rtype;
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
        final FwDsCriteria that = (FwDsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(hoteltime, that.hoteltime) &&
            Objects.equals(rq, that.rq) &&
            Objects.equals(xz, that.xz) &&
            Objects.equals(memo, that.memo) &&
            Objects.equals(fwy, that.fwy) &&
            Objects.equals(roomn, that.roomn) &&
            Objects.equals(rtype, that.rtype) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(sl, that.sl)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hoteltime, rq, xz, memo, fwy, roomn, rtype, empn, sl);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FwDsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (hoteltime != null ? "hoteltime=" + hoteltime + ", " : "") +
            (rq != null ? "rq=" + rq + ", " : "") +
            (xz != null ? "xz=" + xz + ", " : "") +
            (memo != null ? "memo=" + memo + ", " : "") +
            (fwy != null ? "fwy=" + fwy + ", " : "") +
            (roomn != null ? "roomn=" + roomn + ", " : "") +
            (rtype != null ? "rtype=" + rtype + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (sl != null ? "sl=" + sl + ", " : "") +
            "}";
    }
}
