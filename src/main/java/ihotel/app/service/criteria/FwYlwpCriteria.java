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
 * Criteria class for the {@link ihotel.app.domain.FwYlwp} entity. This class is used
 * in {@link ihotel.app.web.rest.FwYlwpResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fw-ylwps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FwYlwpCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter roomn;

    private StringFilter guestname;

    private StringFilter memo;

    private StringFilter sdr;

    private InstantFilter sdrq;

    private StringFilter rlr;

    private InstantFilter rlrq;

    private StringFilter remark;

    private StringFilter empn;

    private InstantFilter czrq;

    private StringFilter flag;

    public FwYlwpCriteria() {}

    public FwYlwpCriteria(FwYlwpCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.roomn = other.roomn == null ? null : other.roomn.copy();
        this.guestname = other.guestname == null ? null : other.guestname.copy();
        this.memo = other.memo == null ? null : other.memo.copy();
        this.sdr = other.sdr == null ? null : other.sdr.copy();
        this.sdrq = other.sdrq == null ? null : other.sdrq.copy();
        this.rlr = other.rlr == null ? null : other.rlr.copy();
        this.rlrq = other.rlrq == null ? null : other.rlrq.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.czrq = other.czrq == null ? null : other.czrq.copy();
        this.flag = other.flag == null ? null : other.flag.copy();
    }

    @Override
    public FwYlwpCriteria copy() {
        return new FwYlwpCriteria(this);
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

    public StringFilter getGuestname() {
        return guestname;
    }

    public StringFilter guestname() {
        if (guestname == null) {
            guestname = new StringFilter();
        }
        return guestname;
    }

    public void setGuestname(StringFilter guestname) {
        this.guestname = guestname;
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

    public StringFilter getSdr() {
        return sdr;
    }

    public StringFilter sdr() {
        if (sdr == null) {
            sdr = new StringFilter();
        }
        return sdr;
    }

    public void setSdr(StringFilter sdr) {
        this.sdr = sdr;
    }

    public InstantFilter getSdrq() {
        return sdrq;
    }

    public InstantFilter sdrq() {
        if (sdrq == null) {
            sdrq = new InstantFilter();
        }
        return sdrq;
    }

    public void setSdrq(InstantFilter sdrq) {
        this.sdrq = sdrq;
    }

    public StringFilter getRlr() {
        return rlr;
    }

    public StringFilter rlr() {
        if (rlr == null) {
            rlr = new StringFilter();
        }
        return rlr;
    }

    public void setRlr(StringFilter rlr) {
        this.rlr = rlr;
    }

    public InstantFilter getRlrq() {
        return rlrq;
    }

    public InstantFilter rlrq() {
        if (rlrq == null) {
            rlrq = new InstantFilter();
        }
        return rlrq;
    }

    public void setRlrq(InstantFilter rlrq) {
        this.rlrq = rlrq;
    }

    public StringFilter getRemark() {
        return remark;
    }

    public StringFilter remark() {
        if (remark == null) {
            remark = new StringFilter();
        }
        return remark;
    }

    public void setRemark(StringFilter remark) {
        this.remark = remark;
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

    public StringFilter getFlag() {
        return flag;
    }

    public StringFilter flag() {
        if (flag == null) {
            flag = new StringFilter();
        }
        return flag;
    }

    public void setFlag(StringFilter flag) {
        this.flag = flag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FwYlwpCriteria that = (FwYlwpCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(roomn, that.roomn) &&
            Objects.equals(guestname, that.guestname) &&
            Objects.equals(memo, that.memo) &&
            Objects.equals(sdr, that.sdr) &&
            Objects.equals(sdrq, that.sdrq) &&
            Objects.equals(rlr, that.rlr) &&
            Objects.equals(rlrq, that.rlrq) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(czrq, that.czrq) &&
            Objects.equals(flag, that.flag)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomn, guestname, memo, sdr, sdrq, rlr, rlrq, remark, empn, czrq, flag);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FwYlwpCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (roomn != null ? "roomn=" + roomn + ", " : "") +
            (guestname != null ? "guestname=" + guestname + ", " : "") +
            (memo != null ? "memo=" + memo + ", " : "") +
            (sdr != null ? "sdr=" + sdr + ", " : "") +
            (sdrq != null ? "sdrq=" + sdrq + ", " : "") +
            (rlr != null ? "rlr=" + rlr + ", " : "") +
            (rlrq != null ? "rlrq=" + rlrq + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (czrq != null ? "czrq=" + czrq + ", " : "") +
            (flag != null ? "flag=" + flag + ", " : "") +
            "}";
    }
}
