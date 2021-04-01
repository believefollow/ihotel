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
 * Criteria class for the {@link ihotel.app.domain.Bookingtime} entity. This class is used
 * in {@link ihotel.app.web.rest.BookingtimeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /bookingtimes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class BookingtimeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter bookid;

    private StringFilter roomn;

    private InstantFilter booktime;

    private StringFilter rtype;

    private LongFilter sl;

    private StringFilter remark;

    private LongFilter sign;

    private LongFilter rzsign;

    public BookingtimeCriteria() {}

    public BookingtimeCriteria(BookingtimeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.bookid = other.bookid == null ? null : other.bookid.copy();
        this.roomn = other.roomn == null ? null : other.roomn.copy();
        this.booktime = other.booktime == null ? null : other.booktime.copy();
        this.rtype = other.rtype == null ? null : other.rtype.copy();
        this.sl = other.sl == null ? null : other.sl.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.sign = other.sign == null ? null : other.sign.copy();
        this.rzsign = other.rzsign == null ? null : other.rzsign.copy();
    }

    @Override
    public BookingtimeCriteria copy() {
        return new BookingtimeCriteria(this);
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

    public StringFilter getBookid() {
        return bookid;
    }

    public StringFilter bookid() {
        if (bookid == null) {
            bookid = new StringFilter();
        }
        return bookid;
    }

    public void setBookid(StringFilter bookid) {
        this.bookid = bookid;
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

    public InstantFilter getBooktime() {
        return booktime;
    }

    public InstantFilter booktime() {
        if (booktime == null) {
            booktime = new InstantFilter();
        }
        return booktime;
    }

    public void setBooktime(InstantFilter booktime) {
        this.booktime = booktime;
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

    public LongFilter getSign() {
        return sign;
    }

    public LongFilter sign() {
        if (sign == null) {
            sign = new LongFilter();
        }
        return sign;
    }

    public void setSign(LongFilter sign) {
        this.sign = sign;
    }

    public LongFilter getRzsign() {
        return rzsign;
    }

    public LongFilter rzsign() {
        if (rzsign == null) {
            rzsign = new LongFilter();
        }
        return rzsign;
    }

    public void setRzsign(LongFilter rzsign) {
        this.rzsign = rzsign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final BookingtimeCriteria that = (BookingtimeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(bookid, that.bookid) &&
            Objects.equals(roomn, that.roomn) &&
            Objects.equals(booktime, that.booktime) &&
            Objects.equals(rtype, that.rtype) &&
            Objects.equals(sl, that.sl) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(sign, that.sign) &&
            Objects.equals(rzsign, that.rzsign)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookid, roomn, booktime, rtype, sl, remark, sign, rzsign);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BookingtimeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (bookid != null ? "bookid=" + bookid + ", " : "") +
            (roomn != null ? "roomn=" + roomn + ", " : "") +
            (booktime != null ? "booktime=" + booktime + ", " : "") +
            (rtype != null ? "rtype=" + rtype + ", " : "") +
            (sl != null ? "sl=" + sl + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            (sign != null ? "sign=" + sign + ", " : "") +
            (rzsign != null ? "rzsign=" + rzsign + ", " : "") +
            "}";
    }
}
