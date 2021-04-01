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
 * Criteria class for the {@link ihotel.app.domain.Checkin} entity. This class is used
 * in {@link ihotel.app.web.rest.CheckinResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /checkins?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CheckinCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter bkid;

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

    private StringFilter phonen;

    private StringFilter empn2;

    private StringFilter adhoc;

    private LongFilter auditflag;

    private StringFilter groupn;

    private StringFilter memo;

    private StringFilter lfSign;

    private StringFilter keynum;

    private StringFilter hykh;

    private StringFilter bm;

    private LongFilter flag;

    private InstantFilter jxtime;

    private LongFilter jxflag;

    private LongFilter checkf;

    private StringFilter guestname;

    private LongFilter fgf;

    private StringFilter fgxx;

    private LongFilter hourSign;

    private StringFilter xsy;

    private LongFilter rzsign;

    private LongFilter jf;

    private StringFilter gname;

    private LongFilter zcsign;

    private LongFilter cqsl;

    private LongFilter sfjf;

    private StringFilter ywly;

    private StringFilter fk;

    private InstantFilter fkrq;

    private StringFilter bc;

    private StringFilter jxremark;

    private DoubleFilter txid;

    private StringFilter cfr;

    private StringFilter fjbm;

    private StringFilter djlx;

    private StringFilter wlddh;

    private BigDecimalFilter fksl;

    private StringFilter dqtx;

    public CheckinCriteria() {}

    public CheckinCriteria(CheckinCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.bkid = other.bkid == null ? null : other.bkid.copy();
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
        this.phonen = other.phonen == null ? null : other.phonen.copy();
        this.empn2 = other.empn2 == null ? null : other.empn2.copy();
        this.adhoc = other.adhoc == null ? null : other.adhoc.copy();
        this.auditflag = other.auditflag == null ? null : other.auditflag.copy();
        this.groupn = other.groupn == null ? null : other.groupn.copy();
        this.memo = other.memo == null ? null : other.memo.copy();
        this.lfSign = other.lfSign == null ? null : other.lfSign.copy();
        this.keynum = other.keynum == null ? null : other.keynum.copy();
        this.hykh = other.hykh == null ? null : other.hykh.copy();
        this.bm = other.bm == null ? null : other.bm.copy();
        this.flag = other.flag == null ? null : other.flag.copy();
        this.jxtime = other.jxtime == null ? null : other.jxtime.copy();
        this.jxflag = other.jxflag == null ? null : other.jxflag.copy();
        this.checkf = other.checkf == null ? null : other.checkf.copy();
        this.guestname = other.guestname == null ? null : other.guestname.copy();
        this.fgf = other.fgf == null ? null : other.fgf.copy();
        this.fgxx = other.fgxx == null ? null : other.fgxx.copy();
        this.hourSign = other.hourSign == null ? null : other.hourSign.copy();
        this.xsy = other.xsy == null ? null : other.xsy.copy();
        this.rzsign = other.rzsign == null ? null : other.rzsign.copy();
        this.jf = other.jf == null ? null : other.jf.copy();
        this.gname = other.gname == null ? null : other.gname.copy();
        this.zcsign = other.zcsign == null ? null : other.zcsign.copy();
        this.cqsl = other.cqsl == null ? null : other.cqsl.copy();
        this.sfjf = other.sfjf == null ? null : other.sfjf.copy();
        this.ywly = other.ywly == null ? null : other.ywly.copy();
        this.fk = other.fk == null ? null : other.fk.copy();
        this.fkrq = other.fkrq == null ? null : other.fkrq.copy();
        this.bc = other.bc == null ? null : other.bc.copy();
        this.jxremark = other.jxremark == null ? null : other.jxremark.copy();
        this.txid = other.txid == null ? null : other.txid.copy();
        this.cfr = other.cfr == null ? null : other.cfr.copy();
        this.fjbm = other.fjbm == null ? null : other.fjbm.copy();
        this.djlx = other.djlx == null ? null : other.djlx.copy();
        this.wlddh = other.wlddh == null ? null : other.wlddh.copy();
        this.fksl = other.fksl == null ? null : other.fksl.copy();
        this.dqtx = other.dqtx == null ? null : other.dqtx.copy();
    }

    @Override
    public CheckinCriteria copy() {
        return new CheckinCriteria(this);
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

    public LongFilter getBkid() {
        return bkid;
    }

    public LongFilter bkid() {
        if (bkid == null) {
            bkid = new LongFilter();
        }
        return bkid;
    }

    public void setBkid(LongFilter bkid) {
        this.bkid = bkid;
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

    public StringFilter getHykh() {
        return hykh;
    }

    public StringFilter hykh() {
        if (hykh == null) {
            hykh = new StringFilter();
        }
        return hykh;
    }

    public void setHykh(StringFilter hykh) {
        this.hykh = hykh;
    }

    public StringFilter getBm() {
        return bm;
    }

    public StringFilter bm() {
        if (bm == null) {
            bm = new StringFilter();
        }
        return bm;
    }

    public void setBm(StringFilter bm) {
        this.bm = bm;
    }

    public LongFilter getFlag() {
        return flag;
    }

    public LongFilter flag() {
        if (flag == null) {
            flag = new LongFilter();
        }
        return flag;
    }

    public void setFlag(LongFilter flag) {
        this.flag = flag;
    }

    public InstantFilter getJxtime() {
        return jxtime;
    }

    public InstantFilter jxtime() {
        if (jxtime == null) {
            jxtime = new InstantFilter();
        }
        return jxtime;
    }

    public void setJxtime(InstantFilter jxtime) {
        this.jxtime = jxtime;
    }

    public LongFilter getJxflag() {
        return jxflag;
    }

    public LongFilter jxflag() {
        if (jxflag == null) {
            jxflag = new LongFilter();
        }
        return jxflag;
    }

    public void setJxflag(LongFilter jxflag) {
        this.jxflag = jxflag;
    }

    public LongFilter getCheckf() {
        return checkf;
    }

    public LongFilter checkf() {
        if (checkf == null) {
            checkf = new LongFilter();
        }
        return checkf;
    }

    public void setCheckf(LongFilter checkf) {
        this.checkf = checkf;
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

    public LongFilter getFgf() {
        return fgf;
    }

    public LongFilter fgf() {
        if (fgf == null) {
            fgf = new LongFilter();
        }
        return fgf;
    }

    public void setFgf(LongFilter fgf) {
        this.fgf = fgf;
    }

    public StringFilter getFgxx() {
        return fgxx;
    }

    public StringFilter fgxx() {
        if (fgxx == null) {
            fgxx = new StringFilter();
        }
        return fgxx;
    }

    public void setFgxx(StringFilter fgxx) {
        this.fgxx = fgxx;
    }

    public LongFilter getHourSign() {
        return hourSign;
    }

    public LongFilter hourSign() {
        if (hourSign == null) {
            hourSign = new LongFilter();
        }
        return hourSign;
    }

    public void setHourSign(LongFilter hourSign) {
        this.hourSign = hourSign;
    }

    public StringFilter getXsy() {
        return xsy;
    }

    public StringFilter xsy() {
        if (xsy == null) {
            xsy = new StringFilter();
        }
        return xsy;
    }

    public void setXsy(StringFilter xsy) {
        this.xsy = xsy;
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

    public LongFilter getJf() {
        return jf;
    }

    public LongFilter jf() {
        if (jf == null) {
            jf = new LongFilter();
        }
        return jf;
    }

    public void setJf(LongFilter jf) {
        this.jf = jf;
    }

    public StringFilter getGname() {
        return gname;
    }

    public StringFilter gname() {
        if (gname == null) {
            gname = new StringFilter();
        }
        return gname;
    }

    public void setGname(StringFilter gname) {
        this.gname = gname;
    }

    public LongFilter getZcsign() {
        return zcsign;
    }

    public LongFilter zcsign() {
        if (zcsign == null) {
            zcsign = new LongFilter();
        }
        return zcsign;
    }

    public void setZcsign(LongFilter zcsign) {
        this.zcsign = zcsign;
    }

    public LongFilter getCqsl() {
        return cqsl;
    }

    public LongFilter cqsl() {
        if (cqsl == null) {
            cqsl = new LongFilter();
        }
        return cqsl;
    }

    public void setCqsl(LongFilter cqsl) {
        this.cqsl = cqsl;
    }

    public LongFilter getSfjf() {
        return sfjf;
    }

    public LongFilter sfjf() {
        if (sfjf == null) {
            sfjf = new LongFilter();
        }
        return sfjf;
    }

    public void setSfjf(LongFilter sfjf) {
        this.sfjf = sfjf;
    }

    public StringFilter getYwly() {
        return ywly;
    }

    public StringFilter ywly() {
        if (ywly == null) {
            ywly = new StringFilter();
        }
        return ywly;
    }

    public void setYwly(StringFilter ywly) {
        this.ywly = ywly;
    }

    public StringFilter getFk() {
        return fk;
    }

    public StringFilter fk() {
        if (fk == null) {
            fk = new StringFilter();
        }
        return fk;
    }

    public void setFk(StringFilter fk) {
        this.fk = fk;
    }

    public InstantFilter getFkrq() {
        return fkrq;
    }

    public InstantFilter fkrq() {
        if (fkrq == null) {
            fkrq = new InstantFilter();
        }
        return fkrq;
    }

    public void setFkrq(InstantFilter fkrq) {
        this.fkrq = fkrq;
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

    public StringFilter getJxremark() {
        return jxremark;
    }

    public StringFilter jxremark() {
        if (jxremark == null) {
            jxremark = new StringFilter();
        }
        return jxremark;
    }

    public void setJxremark(StringFilter jxremark) {
        this.jxremark = jxremark;
    }

    public DoubleFilter getTxid() {
        return txid;
    }

    public DoubleFilter txid() {
        if (txid == null) {
            txid = new DoubleFilter();
        }
        return txid;
    }

    public void setTxid(DoubleFilter txid) {
        this.txid = txid;
    }

    public StringFilter getCfr() {
        return cfr;
    }

    public StringFilter cfr() {
        if (cfr == null) {
            cfr = new StringFilter();
        }
        return cfr;
    }

    public void setCfr(StringFilter cfr) {
        this.cfr = cfr;
    }

    public StringFilter getFjbm() {
        return fjbm;
    }

    public StringFilter fjbm() {
        if (fjbm == null) {
            fjbm = new StringFilter();
        }
        return fjbm;
    }

    public void setFjbm(StringFilter fjbm) {
        this.fjbm = fjbm;
    }

    public StringFilter getDjlx() {
        return djlx;
    }

    public StringFilter djlx() {
        if (djlx == null) {
            djlx = new StringFilter();
        }
        return djlx;
    }

    public void setDjlx(StringFilter djlx) {
        this.djlx = djlx;
    }

    public StringFilter getWlddh() {
        return wlddh;
    }

    public StringFilter wlddh() {
        if (wlddh == null) {
            wlddh = new StringFilter();
        }
        return wlddh;
    }

    public void setWlddh(StringFilter wlddh) {
        this.wlddh = wlddh;
    }

    public BigDecimalFilter getFksl() {
        return fksl;
    }

    public BigDecimalFilter fksl() {
        if (fksl == null) {
            fksl = new BigDecimalFilter();
        }
        return fksl;
    }

    public void setFksl(BigDecimalFilter fksl) {
        this.fksl = fksl;
    }

    public StringFilter getDqtx() {
        return dqtx;
    }

    public StringFilter dqtx() {
        if (dqtx == null) {
            dqtx = new StringFilter();
        }
        return dqtx;
    }

    public void setDqtx(StringFilter dqtx) {
        this.dqtx = dqtx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CheckinCriteria that = (CheckinCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(bkid, that.bkid) &&
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
            Objects.equals(phonen, that.phonen) &&
            Objects.equals(empn2, that.empn2) &&
            Objects.equals(adhoc, that.adhoc) &&
            Objects.equals(auditflag, that.auditflag) &&
            Objects.equals(groupn, that.groupn) &&
            Objects.equals(memo, that.memo) &&
            Objects.equals(lfSign, that.lfSign) &&
            Objects.equals(keynum, that.keynum) &&
            Objects.equals(hykh, that.hykh) &&
            Objects.equals(bm, that.bm) &&
            Objects.equals(flag, that.flag) &&
            Objects.equals(jxtime, that.jxtime) &&
            Objects.equals(jxflag, that.jxflag) &&
            Objects.equals(checkf, that.checkf) &&
            Objects.equals(guestname, that.guestname) &&
            Objects.equals(fgf, that.fgf) &&
            Objects.equals(fgxx, that.fgxx) &&
            Objects.equals(hourSign, that.hourSign) &&
            Objects.equals(xsy, that.xsy) &&
            Objects.equals(rzsign, that.rzsign) &&
            Objects.equals(jf, that.jf) &&
            Objects.equals(gname, that.gname) &&
            Objects.equals(zcsign, that.zcsign) &&
            Objects.equals(cqsl, that.cqsl) &&
            Objects.equals(sfjf, that.sfjf) &&
            Objects.equals(ywly, that.ywly) &&
            Objects.equals(fk, that.fk) &&
            Objects.equals(fkrq, that.fkrq) &&
            Objects.equals(bc, that.bc) &&
            Objects.equals(jxremark, that.jxremark) &&
            Objects.equals(txid, that.txid) &&
            Objects.equals(cfr, that.cfr) &&
            Objects.equals(fjbm, that.fjbm) &&
            Objects.equals(djlx, that.djlx) &&
            Objects.equals(wlddh, that.wlddh) &&
            Objects.equals(fksl, that.fksl) &&
            Objects.equals(dqtx, that.dqtx)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            bkid,
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
            phonen,
            empn2,
            adhoc,
            auditflag,
            groupn,
            memo,
            lfSign,
            keynum,
            hykh,
            bm,
            flag,
            jxtime,
            jxflag,
            checkf,
            guestname,
            fgf,
            fgxx,
            hourSign,
            xsy,
            rzsign,
            jf,
            gname,
            zcsign,
            cqsl,
            sfjf,
            ywly,
            fk,
            fkrq,
            bc,
            jxremark,
            txid,
            cfr,
            fjbm,
            djlx,
            wlddh,
            fksl,
            dqtx
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CheckinCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (bkid != null ? "bkid=" + bkid + ", " : "") +
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
            (phonen != null ? "phonen=" + phonen + ", " : "") +
            (empn2 != null ? "empn2=" + empn2 + ", " : "") +
            (adhoc != null ? "adhoc=" + adhoc + ", " : "") +
            (auditflag != null ? "auditflag=" + auditflag + ", " : "") +
            (groupn != null ? "groupn=" + groupn + ", " : "") +
            (memo != null ? "memo=" + memo + ", " : "") +
            (lfSign != null ? "lfSign=" + lfSign + ", " : "") +
            (keynum != null ? "keynum=" + keynum + ", " : "") +
            (hykh != null ? "hykh=" + hykh + ", " : "") +
            (bm != null ? "bm=" + bm + ", " : "") +
            (flag != null ? "flag=" + flag + ", " : "") +
            (jxtime != null ? "jxtime=" + jxtime + ", " : "") +
            (jxflag != null ? "jxflag=" + jxflag + ", " : "") +
            (checkf != null ? "checkf=" + checkf + ", " : "") +
            (guestname != null ? "guestname=" + guestname + ", " : "") +
            (fgf != null ? "fgf=" + fgf + ", " : "") +
            (fgxx != null ? "fgxx=" + fgxx + ", " : "") +
            (hourSign != null ? "hourSign=" + hourSign + ", " : "") +
            (xsy != null ? "xsy=" + xsy + ", " : "") +
            (rzsign != null ? "rzsign=" + rzsign + ", " : "") +
            (jf != null ? "jf=" + jf + ", " : "") +
            (gname != null ? "gname=" + gname + ", " : "") +
            (zcsign != null ? "zcsign=" + zcsign + ", " : "") +
            (cqsl != null ? "cqsl=" + cqsl + ", " : "") +
            (sfjf != null ? "sfjf=" + sfjf + ", " : "") +
            (ywly != null ? "ywly=" + ywly + ", " : "") +
            (fk != null ? "fk=" + fk + ", " : "") +
            (fkrq != null ? "fkrq=" + fkrq + ", " : "") +
            (bc != null ? "bc=" + bc + ", " : "") +
            (jxremark != null ? "jxremark=" + jxremark + ", " : "") +
            (txid != null ? "txid=" + txid + ", " : "") +
            (cfr != null ? "cfr=" + cfr + ", " : "") +
            (fjbm != null ? "fjbm=" + fjbm + ", " : "") +
            (djlx != null ? "djlx=" + djlx + ", " : "") +
            (wlddh != null ? "wlddh=" + wlddh + ", " : "") +
            (fksl != null ? "fksl=" + fksl + ", " : "") +
            (dqtx != null ? "dqtx=" + dqtx + ", " : "") +
            "}";
    }
}
