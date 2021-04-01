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
 * Criteria class for the {@link ihotel.app.domain.FwJywp} entity. This class is used
 * in {@link ihotel.app.web.rest.FwJywpResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fw-jywps?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FwJywpCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter jyrq;

    private StringFilter roomn;

    private StringFilter guestname;

    private StringFilter jywp;

    private StringFilter fwy;

    private StringFilter djr;

    private StringFilter flag;

    private InstantFilter ghrq;

    private InstantFilter djrq;

    private StringFilter remark;

    public FwJywpCriteria() {}

    public FwJywpCriteria(FwJywpCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.jyrq = other.jyrq == null ? null : other.jyrq.copy();
        this.roomn = other.roomn == null ? null : other.roomn.copy();
        this.guestname = other.guestname == null ? null : other.guestname.copy();
        this.jywp = other.jywp == null ? null : other.jywp.copy();
        this.fwy = other.fwy == null ? null : other.fwy.copy();
        this.djr = other.djr == null ? null : other.djr.copy();
        this.flag = other.flag == null ? null : other.flag.copy();
        this.ghrq = other.ghrq == null ? null : other.ghrq.copy();
        this.djrq = other.djrq == null ? null : other.djrq.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
    }

    @Override
    public FwJywpCriteria copy() {
        return new FwJywpCriteria(this);
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

    public InstantFilter getJyrq() {
        return jyrq;
    }

    public InstantFilter jyrq() {
        if (jyrq == null) {
            jyrq = new InstantFilter();
        }
        return jyrq;
    }

    public void setJyrq(InstantFilter jyrq) {
        this.jyrq = jyrq;
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

    public StringFilter getJywp() {
        return jywp;
    }

    public StringFilter jywp() {
        if (jywp == null) {
            jywp = new StringFilter();
        }
        return jywp;
    }

    public void setJywp(StringFilter jywp) {
        this.jywp = jywp;
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

    public InstantFilter getGhrq() {
        return ghrq;
    }

    public InstantFilter ghrq() {
        if (ghrq == null) {
            ghrq = new InstantFilter();
        }
        return ghrq;
    }

    public void setGhrq(InstantFilter ghrq) {
        this.ghrq = ghrq;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FwJywpCriteria that = (FwJywpCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(jyrq, that.jyrq) &&
            Objects.equals(roomn, that.roomn) &&
            Objects.equals(guestname, that.guestname) &&
            Objects.equals(jywp, that.jywp) &&
            Objects.equals(fwy, that.fwy) &&
            Objects.equals(djr, that.djr) &&
            Objects.equals(flag, that.flag) &&
            Objects.equals(ghrq, that.ghrq) &&
            Objects.equals(djrq, that.djrq) &&
            Objects.equals(remark, that.remark)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, jyrq, roomn, guestname, jywp, fwy, djr, flag, ghrq, djrq, remark);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FwJywpCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (jyrq != null ? "jyrq=" + jyrq + ", " : "") +
            (roomn != null ? "roomn=" + roomn + ", " : "") +
            (guestname != null ? "guestname=" + guestname + ", " : "") +
            (jywp != null ? "jywp=" + jywp + ", " : "") +
            (fwy != null ? "fwy=" + fwy + ", " : "") +
            (djr != null ? "djr=" + djr + ", " : "") +
            (flag != null ? "flag=" + flag + ", " : "") +
            (ghrq != null ? "ghrq=" + ghrq + ", " : "") +
            (djrq != null ? "djrq=" + djrq + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            "}";
    }
}
