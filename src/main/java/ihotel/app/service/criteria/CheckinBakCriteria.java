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
 * Criteria class for the {@link ihotel.app.domain.CheckinBak} entity. This class is used
 * in {@link ihotel.app.web.rest.CheckinBakResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /checkin-baks?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CheckinBakCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter guestId;

    private StringFilter account;

    private InstantFilter hoteltime;

    private InstantFilter indatetime;

    private LongFilter residefate;

    private InstantFilter gotime;

    private StringFilter empn;

    private StringFilter roomn;

    private StringFilter uname;

    private StringFilter rentp;

    private BigDecimalFilter protocolrent;

    private StringFilter remark;

    private StringFilter comeinfo;

    private StringFilter goinfo;

    private StringFilter phonen;

    private StringFilter empn2;

    private StringFilter adhoc;

    private LongFilter auditflag;

    private StringFilter groupn;

    private StringFilter payment;

    private StringFilter mtype;

    private StringFilter memo;

    private StringFilter flight;

    private BigDecimalFilter credit;

    private StringFilter talklevel;

    private StringFilter lfSign;

    private StringFilter keynum;

    private LongFilter icNum;

    private LongFilter bh;

    private StringFilter icOwner;

    private StringFilter markId;

    private StringFilter gj;

    private BigDecimalFilter yfj;

    private InstantFilter hoteldate;

    private LongFilter id;

    public CheckinBakCriteria() {}

    public CheckinBakCriteria(CheckinBakCriteria other) {
        this.guestId = other.guestId == null ? null : other.guestId.copy();
        this.account = other.account == null ? null : other.account.copy();
        this.hoteltime = other.hoteltime == null ? null : other.hoteltime.copy();
        this.indatetime = other.indatetime == null ? null : other.indatetime.copy();
        this.residefate = other.residefate == null ? null : other.residefate.copy();
        this.gotime = other.gotime == null ? null : other.gotime.copy();
        this.empn = other.empn == null ? null : other.empn.copy();
        this.roomn = other.roomn == null ? null : other.roomn.copy();
        this.uname = other.uname == null ? null : other.uname.copy();
        this.rentp = other.rentp == null ? null : other.rentp.copy();
        this.protocolrent = other.protocolrent == null ? null : other.protocolrent.copy();
        this.remark = other.remark == null ? null : other.remark.copy();
        this.comeinfo = other.comeinfo == null ? null : other.comeinfo.copy();
        this.goinfo = other.goinfo == null ? null : other.goinfo.copy();
        this.phonen = other.phonen == null ? null : other.phonen.copy();
        this.empn2 = other.empn2 == null ? null : other.empn2.copy();
        this.adhoc = other.adhoc == null ? null : other.adhoc.copy();
        this.auditflag = other.auditflag == null ? null : other.auditflag.copy();
        this.groupn = other.groupn == null ? null : other.groupn.copy();
        this.payment = other.payment == null ? null : other.payment.copy();
        this.mtype = other.mtype == null ? null : other.mtype.copy();
        this.memo = other.memo == null ? null : other.memo.copy();
        this.flight = other.flight == null ? null : other.flight.copy();
        this.credit = other.credit == null ? null : other.credit.copy();
        this.talklevel = other.talklevel == null ? null : other.talklevel.copy();
        this.lfSign = other.lfSign == null ? null : other.lfSign.copy();
        this.keynum = other.keynum == null ? null : other.keynum.copy();
        this.icNum = other.icNum == null ? null : other.icNum.copy();
        this.bh = other.bh == null ? null : other.bh.copy();
        this.icOwner = other.icOwner == null ? null : other.icOwner.copy();
        this.markId = other.markId == null ? null : other.markId.copy();
        this.gj = other.gj == null ? null : other.gj.copy();
        this.yfj = other.yfj == null ? null : other.yfj.copy();
        this.hoteldate = other.hoteldate == null ? null : other.hoteldate.copy();
        this.id = other.id == null ? null : other.id.copy();
    }

    @Override
    public CheckinBakCriteria copy() {
        return new CheckinBakCriteria(this);
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

    public StringFilter getUname() {
        return uname;
    }

    public StringFilter uname() {
        if (uname == null) {
            uname = new StringFilter();
        }
        return uname;
    }

    public void setUname(StringFilter uname) {
        this.uname = uname;
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

    public StringFilter getComeinfo() {
        return comeinfo;
    }

    public StringFilter comeinfo() {
        if (comeinfo == null) {
            comeinfo = new StringFilter();
        }
        return comeinfo;
    }

    public void setComeinfo(StringFilter comeinfo) {
        this.comeinfo = comeinfo;
    }

    public StringFilter getGoinfo() {
        return goinfo;
    }

    public StringFilter goinfo() {
        if (goinfo == null) {
            goinfo = new StringFilter();
        }
        return goinfo;
    }

    public void setGoinfo(StringFilter goinfo) {
        this.goinfo = goinfo;
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

    public StringFilter getAdhoc() {
        return adhoc;
    }

    public StringFilter adhoc() {
        if (adhoc == null) {
            adhoc = new StringFilter();
        }
        return adhoc;
    }

    public void setAdhoc(StringFilter adhoc) {
        this.adhoc = adhoc;
    }

    public LongFilter getAuditflag() {
        return auditflag;
    }

    public LongFilter auditflag() {
        if (auditflag == null) {
            auditflag = new LongFilter();
        }
        return auditflag;
    }

    public void setAuditflag(LongFilter auditflag) {
        this.auditflag = auditflag;
    }

    public StringFilter getGroupn() {
        return groupn;
    }

    public StringFilter groupn() {
        if (groupn == null) {
            groupn = new StringFilter();
        }
        return groupn;
    }

    public void setGroupn(StringFilter groupn) {
        this.groupn = groupn;
    }

    public StringFilter getPayment() {
        return payment;
    }

    public StringFilter payment() {
        if (payment == null) {
            payment = new StringFilter();
        }
        return payment;
    }

    public void setPayment(StringFilter payment) {
        this.payment = payment;
    }

    public StringFilter getMtype() {
        return mtype;
    }

    public StringFilter mtype() {
        if (mtype == null) {
            mtype = new StringFilter();
        }
        return mtype;
    }

    public void setMtype(StringFilter mtype) {
        this.mtype = mtype;
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

    public StringFilter getFlight() {
        return flight;
    }

    public StringFilter flight() {
        if (flight == null) {
            flight = new StringFilter();
        }
        return flight;
    }

    public void setFlight(StringFilter flight) {
        this.flight = flight;
    }

    public BigDecimalFilter getCredit() {
        return credit;
    }

    public BigDecimalFilter credit() {
        if (credit == null) {
            credit = new BigDecimalFilter();
        }
        return credit;
    }

    public void setCredit(BigDecimalFilter credit) {
        this.credit = credit;
    }

    public StringFilter getTalklevel() {
        return talklevel;
    }

    public StringFilter talklevel() {
        if (talklevel == null) {
            talklevel = new StringFilter();
        }
        return talklevel;
    }

    public void setTalklevel(StringFilter talklevel) {
        this.talklevel = talklevel;
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

    public StringFilter getKeynum() {
        return keynum;
    }

    public StringFilter keynum() {
        if (keynum == null) {
            keynum = new StringFilter();
        }
        return keynum;
    }

    public void setKeynum(StringFilter keynum) {
        this.keynum = keynum;
    }

    public LongFilter getIcNum() {
        return icNum;
    }

    public LongFilter icNum() {
        if (icNum == null) {
            icNum = new LongFilter();
        }
        return icNum;
    }

    public void setIcNum(LongFilter icNum) {
        this.icNum = icNum;
    }

    public LongFilter getBh() {
        return bh;
    }

    public LongFilter bh() {
        if (bh == null) {
            bh = new LongFilter();
        }
        return bh;
    }

    public void setBh(LongFilter bh) {
        this.bh = bh;
    }

    public StringFilter getIcOwner() {
        return icOwner;
    }

    public StringFilter icOwner() {
        if (icOwner == null) {
            icOwner = new StringFilter();
        }
        return icOwner;
    }

    public void setIcOwner(StringFilter icOwner) {
        this.icOwner = icOwner;
    }

    public StringFilter getMarkId() {
        return markId;
    }

    public StringFilter markId() {
        if (markId == null) {
            markId = new StringFilter();
        }
        return markId;
    }

    public void setMarkId(StringFilter markId) {
        this.markId = markId;
    }

    public StringFilter getGj() {
        return gj;
    }

    public StringFilter gj() {
        if (gj == null) {
            gj = new StringFilter();
        }
        return gj;
    }

    public void setGj(StringFilter gj) {
        this.gj = gj;
    }

    public BigDecimalFilter getYfj() {
        return yfj;
    }

    public BigDecimalFilter yfj() {
        if (yfj == null) {
            yfj = new BigDecimalFilter();
        }
        return yfj;
    }

    public void setYfj(BigDecimalFilter yfj) {
        this.yfj = yfj;
    }

    public InstantFilter getHoteldate() {
        return hoteldate;
    }

    public InstantFilter hoteldate() {
        if (hoteldate == null) {
            hoteldate = new InstantFilter();
        }
        return hoteldate;
    }

    public void setHoteldate(InstantFilter hoteldate) {
        this.hoteldate = hoteldate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CheckinBakCriteria that = (CheckinBakCriteria) o;
        return (
            Objects.equals(guestId, that.guestId) &&
            Objects.equals(account, that.account) &&
            Objects.equals(hoteltime, that.hoteltime) &&
            Objects.equals(indatetime, that.indatetime) &&
            Objects.equals(residefate, that.residefate) &&
            Objects.equals(gotime, that.gotime) &&
            Objects.equals(empn, that.empn) &&
            Objects.equals(roomn, that.roomn) &&
            Objects.equals(uname, that.uname) &&
            Objects.equals(rentp, that.rentp) &&
            Objects.equals(protocolrent, that.protocolrent) &&
            Objects.equals(remark, that.remark) &&
            Objects.equals(comeinfo, that.comeinfo) &&
            Objects.equals(goinfo, that.goinfo) &&
            Objects.equals(phonen, that.phonen) &&
            Objects.equals(empn2, that.empn2) &&
            Objects.equals(adhoc, that.adhoc) &&
            Objects.equals(auditflag, that.auditflag) &&
            Objects.equals(groupn, that.groupn) &&
            Objects.equals(payment, that.payment) &&
            Objects.equals(mtype, that.mtype) &&
            Objects.equals(memo, that.memo) &&
            Objects.equals(flight, that.flight) &&
            Objects.equals(credit, that.credit) &&
            Objects.equals(talklevel, that.talklevel) &&
            Objects.equals(lfSign, that.lfSign) &&
            Objects.equals(keynum, that.keynum) &&
            Objects.equals(icNum, that.icNum) &&
            Objects.equals(bh, that.bh) &&
            Objects.equals(icOwner, that.icOwner) &&
            Objects.equals(markId, that.markId) &&
            Objects.equals(gj, that.gj) &&
            Objects.equals(yfj, that.yfj) &&
            Objects.equals(hoteldate, that.hoteldate) &&
            Objects.equals(id, that.id)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            guestId,
            account,
            hoteltime,
            indatetime,
            residefate,
            gotime,
            empn,
            roomn,
            uname,
            rentp,
            protocolrent,
            remark,
            comeinfo,
            goinfo,
            phonen,
            empn2,
            adhoc,
            auditflag,
            groupn,
            payment,
            mtype,
            memo,
            flight,
            credit,
            talklevel,
            lfSign,
            keynum,
            icNum,
            bh,
            icOwner,
            markId,
            gj,
            yfj,
            hoteldate,
            id
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckinBakCriteria{" +
            (guestId != null ? "guestId=" + guestId + ", " : "") +
            (account != null ? "account=" + account + ", " : "") +
            (hoteltime != null ? "hoteltime=" + hoteltime + ", " : "") +
            (indatetime != null ? "indatetime=" + indatetime + ", " : "") +
            (residefate != null ? "residefate=" + residefate + ", " : "") +
            (gotime != null ? "gotime=" + gotime + ", " : "") +
            (empn != null ? "empn=" + empn + ", " : "") +
            (roomn != null ? "roomn=" + roomn + ", " : "") +
            (uname != null ? "uname=" + uname + ", " : "") +
            (rentp != null ? "rentp=" + rentp + ", " : "") +
            (protocolrent != null ? "protocolrent=" + protocolrent + ", " : "") +
            (remark != null ? "remark=" + remark + ", " : "") +
            (comeinfo != null ? "comeinfo=" + comeinfo + ", " : "") +
            (goinfo != null ? "goinfo=" + goinfo + ", " : "") +
            (phonen != null ? "phonen=" + phonen + ", " : "") +
            (empn2 != null ? "empn2=" + empn2 + ", " : "") +
            (adhoc != null ? "adhoc=" + adhoc + ", " : "") +
            (auditflag != null ? "auditflag=" + auditflag + ", " : "") +
            (groupn != null ? "groupn=" + groupn + ", " : "") +
            (payment != null ? "payment=" + payment + ", " : "") +
            (mtype != null ? "mtype=" + mtype + ", " : "") +
            (memo != null ? "memo=" + memo + ", " : "") +
            (flight != null ? "flight=" + flight + ", " : "") +
            (credit != null ? "credit=" + credit + ", " : "") +
            (talklevel != null ? "talklevel=" + talklevel + ", " : "") +
            (lfSign != null ? "lfSign=" + lfSign + ", " : "") +
            (keynum != null ? "keynum=" + keynum + ", " : "") +
            (icNum != null ? "icNum=" + icNum + ", " : "") +
            (bh != null ? "bh=" + bh + ", " : "") +
            (icOwner != null ? "icOwner=" + icOwner + ", " : "") +
            (markId != null ? "markId=" + markId + ", " : "") +
            (gj != null ? "gj=" + gj + ", " : "") +
            (yfj != null ? "yfj=" + yfj + ", " : "") +
            (hoteldate != null ? "hoteldate=" + hoteldate + ", " : "") +
            (id != null ? "id=" + id + ", " : "") +
            "}";
    }
}
