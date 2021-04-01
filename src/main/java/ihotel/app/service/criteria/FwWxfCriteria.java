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
 * Criteria class for the {@link ihotel.app.domain.FwWxf} entity. This class is used
 * in {@link ihotel.app.web.rest.FwWxfResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fw-wxfs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FwWxfCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter roomn;

    private StringFilter memo;

    private InstantFilter djrq;

    private StringFilter wxr;

    private InstantFilter wcrq;

    private StringFilter djr;

    private StringFilter flag;

    public FwWxfCriteria() {}

    public FwWxfCriteria(FwWxfCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.roomn = other.roomn == null ? null : other.roomn.copy();
        this.memo = other.memo == null ? null : other.memo.copy();
        this.djrq = other.djrq == null ? null : other.djrq.copy();
        this.wxr = other.wxr == null ? null : other.wxr.copy();
        this.wcrq = other.wcrq == null ? null : other.wcrq.copy();
        this.djr = other.djr == null ? null : other.djr.copy();
        this.flag = other.flag == null ? null : other.flag.copy();
    }

    @Override
    public FwWxfCriteria copy() {
        return new FwWxfCriteria(this);
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

    public InstantFilter getDjrq() {
        return djrq;
    }

    public InstantFilter djrq() {
        if (djrq == null) {
            djrq = new InstantFilter();
        }
        return djrq;
    }

    public void setDjrq(InstantFilter djrq) {
        this.djrq = djrq;
    }

    public StringFilter getWxr() {
        return wxr;
    }

    public StringFilter wxr() {
        if (wxr == null) {
            wxr = new StringFilter();
        }
        return wxr;
    }

    public void setWxr(StringFilter wxr) {
        this.wxr = wxr;
    }

    public InstantFilter getWcrq() {
        return wcrq;
    }

    public InstantFilter wcrq() {
        if (wcrq == null) {
            wcrq = new InstantFilter();
        }
        return wcrq;
    }

    public void setWcrq(InstantFilter wcrq) {
        this.wcrq = wcrq;
    }

    public StringFilter getDjr() {
        return djr;
    }

    public StringFilter djr() {
        if (djr == null) {
            djr = new StringFilter();
        }
        return djr;
    }

    public void setDjr(StringFilter djr) {
        this.djr = djr;
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
        final FwWxfCriteria that = (FwWxfCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(roomn, that.roomn) &&
            Objects.equals(memo, that.memo) &&
            Objects.equals(djrq, that.djrq) &&
            Objects.equals(wxr, that.wxr) &&
            Objects.equals(wcrq, that.wcrq) &&
            Objects.equals(djr, that.djr) &&
            Objects.equals(flag, that.flag)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomn, memo, djrq, wxr, wcrq, djr, flag);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FwWxfCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (roomn != null ? "roomn=" + roomn + ", " : "") +
            (memo != null ? "memo=" + memo + ", " : "") +
            (djrq != null ? "djrq=" + djrq + ", " : "") +
            (wxr != null ? "wxr=" + wxr + ", " : "") +
            (wcrq != null ? "wcrq=" + wcrq + ", " : "") +
            (djr != null ? "djr=" + djr + ", " : "") +
            (flag != null ? "flag=" + flag + ", " : "") +
            "}";
    }
}
