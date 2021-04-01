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
 * Criteria class for the {@link ihotel.app.domain.CheckinTz} entity. This class is used
 * in {@link ihotel.app.web.rest.CheckinTzResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /checkin-tzs?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CheckinTzCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter guestId;

    private StringFilter account;

    private InstantFilter hoteltime;

    private InstantFilter indatetime;

    private LongFilter residefate;

    private InstantFilter gotime;

    private StringFilter empn;

    private StringFilter roomn;

    private StringFilter rentp;

    private BigDecimalFilter protocolrent;

    private StringFilter remark;

    private StringFilter phonen;

    private StringFilter empn2;

    private StringFilter memo;

    private StringFilter lfSign;

    private StringFilter guestname;

    private StringFilter bc;

    private StringFilter roomtype;

    public CheckinTzCriteria() {}

    public CheckinTzCriteria(CheckinTzCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.guestId = other.guestId == null ? null : other.guestId.copy();
        this.account = other.account == null ? null : other.account.copy();
        this.hoteltime = other.hoteltime == null ? null : other.hoteltime.copy();
        this.indatetime = other.indatetime == null ? null : other.indatetime.copy();
        this.residefate = other.residefate == null ? null : other.residefate.copy();
        this.gotime = other.gotime == null ? null : other.gotime.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.roomn = other.roomn == null ? null : other.roomn.copy();
        this.rentp = other.rentp == null ? null : other.rentp.copy();
        this.protocolrent = other.protocolrent == null ? null : other.protocolrent.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.phonen = other.phonen == null ? null : other.phonen.copy();
        this.empn2 = other.empn2 == null ? null : other.empn2.copy();
        this.memo = other.memo == null ? null : other.memo.copy();
        this.lfSign = other.lfSign == null ? null : other.lfSign.copy();
        this.guestname = other.guestname == null ? null : other.guestname.copy();
        this.bc = other.bc == null ? null : other.bc.copy();
        this.roomtype = other.roomtype == null ? null : other.roomtype.copy();
    }

    @Override
    public CheckinTzCriteria copy() {
        return new CheckinTzCriteria(this);
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

    public LongFilter getGuestId() {
        return guestId;
    }

    public LongFilter guestId() {
        if (guestId == null) {
            guestId = new LongFilter();
        }
        return guestId;
    }

    public void setGuestId(LongFilter guestId) {
        this.guestId = guestId;
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

    public InstantFilter getIndatetime() {
        return indatetime;
    }

    public InstantFilter indatetime() {
        if (indatetime == null) {
            indatetime = new InstantFilter();
        }
        return indatetime;
    }

    public void setIndatetime(InstantFilter indatetime) {
        this.indatetime = indatetime;
    }

    public LongFilter getResidefate() {
        return residefate;
    }

    public LongFilter residefate() {
        if (residefate == null) {
            residefate = new LongFilter();
        }
        return residefate;
    }

    public void setResidefate(LongFilter residefate) {
        this.residefate = residefate;
    }

    public InstantFilter getGotime() {
        return gotime;
    }

    public InstantFilter gotime() {
        if (gotime == null) {
            gotime = new InstantFilter();
        }
        return gotime;
    }

    public void setGotime(InstantFilter gotime) {
        this.gotime = gotime;
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

    public StringFilter getRentp() {
        return rentp;
    }

    public StringFilter rentp() {
        if (rentp == null) {
            rentp = new StringFilter();
        }
        return rentp;
    }

    public void setRentp(StringFilter rentp) {
        this.rentp = rentp;
    }

    public BigDecimalFilter getProtocolrent() {
        return protocolrent;
    }

    public BigDecimalFilter protocolrent() {
        if (protocolrent == null) {
            protocolrent = new BigDecimalFilter();
        }
        return protocolrent;
    }

    public void setProtocolrent(BigDecimalFilter protocolrent) {
        this.protocolrent = protocolrent;
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

    public StringFilter getPhonen() {
        return phonen;
    }

    public StringFilter phonen() {
        if (phonen == null) {
            phonen = new StringFilter();
        }
        return phonen;
    }

    public void setPhonen(StringFilter phonen) {
        this.phonen = phonen;
    }

    public StringFilter getEmpn2() {
        return empn2;
    }

    public StringFilter empn2() {
        if (empn2 == null) {
            empn2 = new StringFilter();
        }
        return empn2;
    }

    public void setEmpn2(StringFilter empn2) {
        this.empn2 = empn2;
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

    public StringFilter getLfSign() {
        return lfSign;
    }

    public StringFilter lfSign() {
        if (lfSign == null) {
            lfSign = new StringFilter();
        }
        return lfSign;
    }

    public void setLfSign(StringFilter lfSign) {
        this.lfSign = lfSign;
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

    public StringFilter getBc() {
        return bc;
    }

    public StringFilter bc() {
        if (bc == null) {
            bc = new StringFilter();
        }
        return bc;
    }

    public void setBc(StringFilter bc) {
        this.bc = bc;
    }

    public StringFilter getRoomtype() {
        return roomtype;
    }

    public StringFilter roomtype() {
        if (roomtype == null) {
            roomtype = new StringFilter();
        }
        return roomtype;
    }

    public void setRoomtype(StringFilter roomtype) {
        this.roomtype = roomtype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CheckinTzCriteria that = (CheckinTzCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(guestId, that.guestId) &&
            Objects.equals(account, that.account) &&
            Objects.equals(hoteltime, that.hoteltime) &&
            Objects.equals(indatetime, that.indatetime) &&
            Objects.equals(residefate, that.residefate) &&
            Objects.equals(gotime, that.gotime) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(roomn, that.roomn) &&
            Objects.equals(rentp, that.rentp) &&
            Objects.equals(protocolrent, that.protocolrent) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(phonen, that.phonen) &&
            Objects.equals(empn2, that.empn2) &&
            Objects.equals(memo, that.memo) &&
            Objects.equals(lfSign, that.lfSign) &&
            Objects.equals(guestname, that.guestname) &&
            Objects.equals(bc, that.bc) &&
            Objects.equals(roomtype, that.roomtype)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            guestId,
            account,
            hoteltime,
            indatetime,
            residefate,
            gotime,
            empn,
            roomn,
            rentp,
            protocolrent,
            remark,
            phonen,
            empn2,
            memo,
            lfSign,
            guestname,
            bc,
            roomtype
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckinTzCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (guestId != null ? "guestId=" + guestId + ", " : "") +
            (account != null ? "account=" + account + ", " : "") +
            (hoteltime != null ? "hoteltime=" + hoteltime + ", " : "") +
            (indatetime != null ? "indatetime=" + indatetime + ", " : "") +
            (residefate != null ? "residefate=" + residefate + ", " : "") +
            (gotime != null ? "gotime=" + gotime + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (roomn != null ? "roomn=" + roomn + ", " : "") +
            (rentp != null ? "rentp=" + rentp + ", " : "") +
            (protocolrent != null ? "protocolrent=" + protocolrent + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            (phonen != null ? "phonen=" + phonen + ", " : "") +
            (empn2 != null ? "empn2=" + empn2 + ", " : "") +
            (memo != null ? "memo=" + memo + ", " : "") +
            (lfSign != null ? "lfSign=" + lfSign + ", " : "") +
            (guestname != null ? "guestname=" + guestname + ", " : "") +
            (bc != null ? "bc=" + bc + ", " : "") +
            (roomtype != null ? "roomtype=" + roomtype + ", " : "") +
            "}";
    }
}
