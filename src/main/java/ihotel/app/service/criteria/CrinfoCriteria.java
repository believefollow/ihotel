package ihotel.app.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BigDecimalFilter;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ihotel.app.domain.Crinfo} entity. This class is used
 * in {@link ihotel.app.web.rest.CrinfoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /crinfos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CrinfoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter operatetime;

    private BigDecimalFilter oldrent;

    private BigDecimalFilter newrent;

    private StringFilter oldroomn;

    private StringFilter newroomn;

    private StringFilter account;

    private StringFilter empn;

    private LongFilter oldday;

    private LongFilter newday;

    private InstantFilter hoteltime;

    private StringFilter roomn;

    private StringFilter memo;

    private StringFilter realname;

    private LongFilter isup;

    public CrinfoCriteria() {}

    public CrinfoCriteria(CrinfoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.operatetime = other.operatetime == null ? null : other.operatetime.copy();
        this.oldrent = other.oldrent == null ? null : other.oldrent.copy();
        this.newrent = other.newrent == null ? null : other.newrent.copy();
        this.oldroomn = other.oldroomn == null ? null : other.oldroomn.copy();
        this.newroomn = other.newroomn == null ? null : other.newroomn.copy();
        this.account = other.account == null ? null : other.account.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.oldday = other.oldday == null ? null : other.oldday.copy();
        this.newday = other.newday == null ? null : other.newday.copy();
        this.hoteltime = other.hoteltime == null ? null : other.hoteltime.copy();
        this.roomn = other.roomn == null ? null : other.roomn.copy();
        this.memo = other.memo == null ? null : other.memo.copy();
        this.realname = other.realname == null ? null : other.realname.copy();
        this.isup = other.isup == null ? null : other.isup.copy();
    }

    @Override
    public CrinfoCriteria copy() {
        return new CrinfoCriteria(this);
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

    public InstantFilter getOperatetime() {
        return operatetime;
    }

    public InstantFilter operatetime() {
        if (operatetime == null) {
            operatetime = new InstantFilter();
        }
        return operatetime;
    }

    public void setOperatetime(InstantFilter operatetime) {
        this.operatetime = operatetime;
    }

    public BigDecimalFilter getOldrent() {
        return oldrent;
    }

    public BigDecimalFilter oldrent() {
        if (oldrent == null) {
            oldrent = new BigDecimalFilter();
        }
        return oldrent;
    }

    public void setOldrent(BigDecimalFilter oldrent) {
        this.oldrent = oldrent;
    }

    public BigDecimalFilter getNewrent() {
        return newrent;
    }

    public BigDecimalFilter newrent() {
        if (newrent == null) {
            newrent = new BigDecimalFilter();
        }
        return newrent;
    }

    public void setNewrent(BigDecimalFilter newrent) {
        this.newrent = newrent;
    }

    public StringFilter getOldroomn() {
        return oldroomn;
    }

    public StringFilter oldroomn() {
        if (oldroomn == null) {
            oldroomn = new StringFilter();
        }
        return oldroomn;
    }

    public void setOldroomn(StringFilter oldroomn) {
        this.oldroomn = oldroomn;
    }

    public StringFilter getNewroomn() {
        return newroomn;
    }

    public StringFilter newroomn() {
        if (newroomn == null) {
            newroomn = new StringFilter();
        }
        return newroomn;
    }

    public void setNewroomn(StringFilter newroomn) {
        this.newroomn = newroomn;
    }

    public StringFilter getAccount() {
        return account;
    }

    public StringFilter account() {
        if (account == null) {
            account = new StringFilter();
        }
        return account;
    }

    public void setAccount(StringFilter account) {
        this.account = account;
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

    public LongFilter getOldday() {
        return oldday;
    }

    public LongFilter oldday() {
        if (oldday == null) {
            oldday = new LongFilter();
        }
        return oldday;
    }

    public void setOldday(LongFilter oldday) {
        this.oldday = oldday;
    }

    public LongFilter getNewday() {
        return newday;
    }

    public LongFilter newday() {
        if (newday == null) {
            newday = new LongFilter();
        }
        return newday;
    }

    public void setNewday(LongFilter newday) {
        this.newday = newday;
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

    public StringFilter getRealname() {
        return realname;
    }

    public StringFilter realname() {
        if (realname == null) {
            realname = new StringFilter();
        }
        return realname;
    }

    public void setRealname(StringFilter realname) {
        this.realname = realname;
    }

    public LongFilter getIsup() {
        return isup;
    }

    public LongFilter isup() {
        if (isup == null) {
            isup = new LongFilter();
        }
        return isup;
    }

    public void setIsup(LongFilter isup) {
        this.isup = isup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CrinfoCriteria that = (CrinfoCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(operatetime, that.operatetime) &&
            Objects.equals(oldrent, that.oldrent) &&
            Objects.equals(newrent, that.newrent) &&
            Objects.equals(oldroomn, that.oldroomn) &&
            Objects.equals(newroomn, that.newroomn) &&
            Objects.equals(account, that.account) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(oldday, that.oldday) &&
            Objects.equals(newday, that.newday) &&
            Objects.equals(hoteltime, that.hoteltime) &&
            Objects.equals(roomn, that.roomn) &&
            Objects.equals(memo, that.memo) &&
            Objects.equals(realname, that.realname) &&
            Objects.equals(isup, that.isup)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            operatetime,
            oldrent,
            newrent,
            oldroomn,
            newroomn,
            account,
            empn,
            oldday,
            newday,
            hoteltime,
            roomn,
            memo,
            realname,
            isup
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CrinfoCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (operatetime != null ? "operatetime=" + operatetime + ", " : "") +
            (oldrent != null ? "oldrent=" + oldrent + ", " : "") +
            (newrent != null ? "newrent=" + newrent + ", " : "") +
            (oldroomn != null ? "oldroomn=" + oldroomn + ", " : "") +
            (newroomn != null ? "newroomn=" + newroomn + ", " : "") +
            (account != null ? "account=" + account + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (oldday != null ? "oldday=" + oldday + ", " : "") +
            (newday != null ? "newday=" + newday + ", " : "") +
            (hoteltime != null ? "hoteltime=" + hoteltime + ", " : "") +
            (roomn != null ? "roomn=" + roomn + ", " : "") +
            (memo != null ? "memo=" + memo + ", " : "") +
            (realname != null ? "realname=" + realname + ", " : "") +
            (isup != null ? "isup=" + isup + ", " : "") +
            "}";
    }
}
